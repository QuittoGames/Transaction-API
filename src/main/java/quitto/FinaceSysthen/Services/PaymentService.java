package quitto.FinaceSysthen.Services;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.CascadeType;
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

    public TransactionResponseDTO transaction(TransactionSenderDTO data) throws RuntimeException, OperationNotSupportedException{
        try {
            Optional<User> senderOpt = userRepository.findByUserId(data.getSenderId());
            Optional<User> receiverOpt = userRepository.findByUserId(data.getReceiverId());
            BigDecimal transactionValue = data.getTransactionValue();
            Category category = data.getCatorgory() != null 
                ? data.getCatorgory() 
                : Category.OTHER;

            if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            if (transactionValue == null) {
                throw new RuntimeException("Transaction value is required");
            }
            
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();

            if (isZeroOrNegative(sender.getAmount()) || isZeroOrNegative(receiver.getAmount()) || isZeroOrNegative(transactionValue)) {
                throw new OperationNotSupportedException("Invalid operation");
            }

            sender.setAmount(sender.getAmount().subtract(transactionValue));
            receiver.setAmount(receiver.getAmount().add(data.getTransactionValue()));

            LocalDateTime transactionTime = LocalDateTime.now();

            userRepository.save(sender);
            userRepository.save(receiver);

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

    public static boolean isZeroOrNegative(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) <= 0;
    }

    public List<Payment> getPaymentsByUserId(Long id){
        return paymentRepository.findByUserUserId(id);
    }

    public List<Payment> findByCategory(String category){
        return paymentRepository.findByCategory(category);
    }

    public List<Payment> findByDate(LocalDate date){
        return paymentRepository.findByDate(date);
    }
}
