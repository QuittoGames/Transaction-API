package quitto.FinaceSysthen.DTOs.Trasactions;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import quitto.FinaceSysthen.Enums.Category;

public class TransactionSenderDTO {
    @JsonAlias("senderId")
    private Long senderId;

    @JsonAlias("receiverId")
    private Long receiverId;

    @JsonAlias("value")
    private BigDecimal transactionValue;

    private UUID interpotecyKey; 

    private Category catorgory;
    
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
