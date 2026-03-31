package quitto.FinaceSysthen.DTOs.Trasactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import quitto.FinaceSysthen.Enums.Category;

public class TransactionResponseDTO {
    private String response;
    private BigDecimal value;
    private Long sender;
    private Long receiver;
    private LocalDateTime transactionTime;
    private UUID idempotencyKey;
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
