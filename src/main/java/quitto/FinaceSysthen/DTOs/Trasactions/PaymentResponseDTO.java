package quitto.FinaceSysthen.DTOs.Trasactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import quitto.FinaceSysthen.Enums.Category;

public class PaymentResponseDTO {

    private Long paymentId;

    private BigDecimal transactionValue;

    private Category category;

    private LocalDateTime transactionDate;

    private Long payerId;

    private String payerName;

    public PaymentResponseDTO(
        Long paymentId,
        BigDecimal transactionValue,
        Category category,
        LocalDateTime transactionDate,
        Long payerId,
        String payerName
    ) {
        this.paymentId = paymentId;
        this.transactionValue = transactionValue;
        this.category = category;
        this.transactionDate = transactionDate;
        this.payerId = payerId;
        this.payerName = payerName;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Long getPayerId() {
        return payerId;
    }

    public String getPayerName() {
        return payerName;
    }
}