package quitto.FinaceSysthen.DTOs.Category;

import java.util.List;

import quitto.FinaceSysthen.Models.Payment;

public class CategoryResponseDTO {
    private List<Payment> payments;
    private String categorySelect;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(List<Payment> payments, String categorySelect) {
        this.payments = payments;
        this.categorySelect = categorySelect;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getCategorySelect() {
        return categorySelect;
    }

    public void setCategorySelect(String categorySelect) {
        this.categorySelect = categorySelect;
    }
}
