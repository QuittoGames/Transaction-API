package quitto.FinaceSysthen.Controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import quitto.FinaceSysthen.DTOs.Category.CategoryResponseDTO;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionResponseDTO;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionSenderDTO;
import quitto.FinaceSysthen.Enums.Role;
import quitto.FinaceSysthen.Models.Payment;
import quitto.FinaceSysthen.Services.PaymentService;
import quitto.FinaceSysthen.Services.UserService;
import quitto.FinaceSysthen.Services.Auth.AuthService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService users;

    @Autowired
    private AuthService authService;

    //Create mini DB for tests
    @PostMapping("/seed")
    public String seed() {
        users.createUser("teste", new BigDecimal("100"), Role.USER);
        users.createUser("teste1", new BigDecimal("100"), Role.USER);
        return "ok";
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponseDTO> transaction(@RequestBody TransactionSenderDTO data) throws RuntimeException {
        try {
            if (data == null) {
                throw new RuntimeException("Invalid operation");
            }
        
            UUID IndepotetencyKey = data.getInterpotecyKey();
            if (IndepotetencyKey == null) {
                throw new RuntimeException("Invalid operation");
            }
            
            if (!(authService.addArpoveadKeys(IndepotetencyKey))){
                throw new RuntimeException("Duplicate Request");
            }
            
            return ResponseEntity.ok(paymentService.transaction(data));

        } catch (OperationNotSupportedException e) {
            LocalDateTime transactionTime = LocalDateTime.now();
            return ResponseEntity
                    .status(400)
                    .body(new TransactionResponseDTO(
                            "[ERROR] Operation not allowed: " + e.getMessage(),
                            data != null ? data.getTransactionValue() : null,
                            data != null ? data.getSenderId() : null,
                            data != null ? data.getReceiverId() : null,
                            transactionTime,
                            data != null ? data.getInterpotecyKey() : null,
                            data != null ? data.getCatorgory() : null));

        } catch (RuntimeException e) {
            LocalDateTime transactionTime = LocalDateTime.now();
            return ResponseEntity
                    .status(400)
                    .body(new TransactionResponseDTO(
                            "[ERROR] Runtime error: " + e.getMessage(),
                            data != null ? data.getTransactionValue() : null,
                            data != null ? data.getSenderId() : null,
                            data != null ? data.getReceiverId() : null,
                            transactionTime,
                            data != null ? data.getInterpotecyKey() : null,
                            data != null ? data.getCatorgory() : null));

        } catch (Exception e) {
            LocalDateTime transactionTime = LocalDateTime.now();
            return ResponseEntity
                    .status(500)
                    .body(new TransactionResponseDTO(
                            "[ERROR] Internal error: " + e.getMessage(),
                            data != null ? data.getTransactionValue() : null,
                            data != null ? data.getSenderId() : null,
                            data != null ? data.getReceiverId() : null,
                            transactionTime,
                            data != null ? data.getInterpotecyKey() : null,
                            data != null ? data.getCatorgory() : null));
        }
    }

    @PostMapping("/list")
    public ResponseEntity<CategoryResponseDTO> listForCategory(@RequestBody String catgoryString){
        try {
            if (catgoryString == null){
                throw new RuntimeException("Invalid operation");
            }
            
            List<Payment> result = paymentService.listPayments(catgoryString);

            CategoryResponseDTO responseDTO = new CategoryResponseDTO(result,catgoryString);

            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
