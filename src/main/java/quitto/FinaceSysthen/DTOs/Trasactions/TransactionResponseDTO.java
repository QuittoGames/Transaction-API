package quitto.FinaceSysthen.DTOs.Trasactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import quitto.FinaceSysthen.Enums.Category;

@Schema(name = "TransactionResponseDTO", description = "Response payload returned after processing a transaction")
public class TransactionResponseDTO {
    @Schema(description = "Human-readable response message", example = "Transaction completed")
    private String response;

    @Schema(description = "Transaction amount", example = "150.75")
    private BigDecimal value;

    @Schema(description = "Sender account ID", example = "1")
    private Long sender;

    @Schema(description = "Receiver account ID", example = "2")
    private Long receiver;

    @Schema(
        description = "Transaction timestamp in ISO 8601 format with nanosecond precision",
        example = "2026-04-24T23:14:37.617861053"
    )
    private LocalDateTime transactionTime;

    @Schema(description = "Idempotency key used to prevent duplicate processing", format = "uuid", example = "b3b0d2a1-2c3d-4e5f-9a01-2b3c4d5e6f70")
    private UUID idempotencyKey;

    @Schema(description = "Transaction category", example = "FOOD")
    private Category category;
    
    public TransactionResponseDTO(String response, BigDecimal value, Long sender, Long receiver, LocalDateTime transactionTime, UUID idempotencyKey,Category category) {
        this.response = response;
        this.value = value;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionTime = transactionTime;
        this.idempotencyKey = idempotencyKey;
        this.category = category;
    }
    
    public String getResponse() {
        return response;
    }
    public BigDecimal getValue() {
        return value;
    }
    public Long getSender() {
        return sender;
    }
    public Long getReceiver() {
        return receiver;
    }
    
    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    
    public UUID getIdempotencyKey() {
        return idempotencyKey;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
}
