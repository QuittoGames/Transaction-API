package quitto.FinaceSysthen.Services;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.naming.OperationNotSupportedException;

import java.util.Optional;

import org.aspectj.weaver.patterns.ExactAnnotationFieldTypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quitto.FinaceSysthen.DTOs.Trasactions.TransactionResponseDTO;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionSenderDTO;
import quitto.FinaceSysthen.Enums.Category;
import quitto.FinaceSysthen.Models.Payment;
import quitto.FinaceSysthen.Models.User;
import quitto.FinaceSysthen.Repository.PaymentRepository;
import quitto.FinaceSysthen.Repository.UserRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired  
    private UserRepository userRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public TransactionResponseDTO transaction(TransactionSenderDTO data) throws RuntimeException, OperationNotSupportedException{
        try {
            Optional<User> senderOpt = userRepository.findByUserId(data.getSenderId());
            Optional<User> receiverOpt = userRepository.findByUserId(data.getReceiverId());
            BigDecimal transactionValue = data.getTransactionValue();
            Category category = data.getCatorgory() != null 
                ? data.getCatorgory() 
                : Category.OTHER;
            
            if (data.getReceiverId().equals(data.getSenderId())){
                throw new RuntimeException("Id of sender is the same for receiver");
            }
            if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            
            if (transactionValue == null) {
                throw new RuntimeException("Transaction value is required");
            }
            
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();
            
            if (isZeroOrNegative(transactionValue)) {
                throw new OperationNotSupportedException("Invalid transaction value");
            }

            if (sender.getAmount().compareTo(transactionValue) < 0) {
                throw new OperationNotSupportedException("Insufficient balance");
            }
            
            sender.setAmount(sender.getAmount().subtract(transactionValue));
            receiver.setAmount(receiver.getAmount().add(data.getTransactionValue()));
            
            LocalDateTime transactionTime = LocalDateTime.now();
            Payment payment = new Payment(transactionValue, category, transactionTime, receiver);
            
            userRepository.save(sender);
            userRepository.save(receiver);
            paymentRepository.save(payment);

            return new TransactionResponseDTO(
                "Transaction completed",
                transactionValue,
                sender.getUserId(),
                receiver.getUserId(),
                transactionTime,
                data.getInterpotecyKey(),
                category
            );
        } catch (RuntimeException runtimeException) {
            System.out.println("[ERROR] Runtime error: " + runtimeException.getMessage());
            throw runtimeException;
        } catch (OperationNotSupportedException operationNotSupportedException) {
            System.out.println("[ERROR] Operation not supported: " + operationNotSupportedException.getMessage());
            throw operationNotSupportedException;
        } catch (Exception exception) {
            System.out.println("[ERROR] Transaction error: " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public List<Payment> listPayments(String categoryString) throws RuntimeException{
        try {
            if (!(Category.exists(categoryString))){
                throw new RuntimeException("Category is not valid");
            }
            
            Category category = Category.valueOf(categoryString.toUpperCase());
            return paymentRepository.findByCategory(category);
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid category: " + categoryString);
            throw new RuntimeException(exception);
        }
    }

    public static boolean isZeroOrNegative(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) <= 0;
    }

    public List<Payment> getPaymentsByPayerId(Long id){
        return paymentRepository.findByPayerUserId(id);
    }

    public List<Payment> findByDate(LocalDate date){
        return paymentRepository.findByDate(date);
    }
}
