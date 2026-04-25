package quitto.FinaceSysthen.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import quitto.FinaceSysthen.DTOs.Category.CategoryResponseDTO;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionResponseDTO;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionSenderDTO;
import quitto.FinaceSysthen.Models.Payment;
import quitto.FinaceSysthen.Services.PaymentService;
import quitto.FinaceSysthen.Services.Auth.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Operation(
        tags = {"Transaction"}, 
        summary = "Create transaction"
    )
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
            logger.error("Error in transaction controller", e);
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
            logger.error("Runtime error in transaction controller", e);
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
            logger.error("Unexpected error in transaction controller", e);
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
    @Operation(
        tags = {"Transaction"},
        description = "List Payments for category "
    )
    public ResponseEntity<CategoryResponseDTO> listForCategory(@RequestBody String catgoryString){
        try {
            if (catgoryString == null){
                throw new RuntimeException("Invalid operation");
            }
            
            List<Payment> result = paymentService.listPayments(catgoryString);

            CategoryResponseDTO responseDTO = new CategoryResponseDTO(result,catgoryString);

            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            logger.error("Error listing payments by category", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
