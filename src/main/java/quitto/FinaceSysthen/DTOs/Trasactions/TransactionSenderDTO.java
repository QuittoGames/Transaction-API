package quitto.FinaceSysthen.DTOs.Trasactions;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;
import quitto.FinaceSysthen.Enums.Category;

@Schema(name = "TransactionSenderDTO", description = "Request payload used to send a transaction")
public class TransactionSenderDTO {
    @JsonAlias("senderId")
    @Schema(description = "Sender account ID", example = "1")
    private Long senderId;

    @JsonAlias("receiverId")
    @Schema(description = "Receiver account ID", example = "2")
    private Long receiverId;

    @JsonAlias("value")
    @Schema(description = "Transaction amount", example = "150.75")
    private BigDecimal transactionValue;

    @Schema(description = "Idempotency key used to prevent duplicate processing", format = "uuid", example = "b3b0d2a1-2c3d-4e5f-9a01-2b3c4d5e6f70")
    private UUID interpotecyKey; 

    @Schema(description = "Transaction category", example = "FOOD")
    private Category catorgory;
    
    public TransactionSenderDTO() {
    }

    public TransactionSenderDTO(Long senderId, Long receiverId, BigDecimal transactionValue, UUID interpotecyKey,
            Category catorgory) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionValue = transactionValue;
        this.interpotecyKey = interpotecyKey;
        this.catorgory = catorgory;
    }

    public Category getCatorgory() {
        return catorgory;
    }
    
    public Long getSenderId() {
        return senderId;
    }
    
    public Long getReceiverId() {
        return receiverId;
    }

    public void setCatorgory(Category catorgory) {
        this.catorgory = catorgory;
    }
    
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }
    public void setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
    }

    public UUID getInterpotecyKey() {
        return interpotecyKey;
    }
}
