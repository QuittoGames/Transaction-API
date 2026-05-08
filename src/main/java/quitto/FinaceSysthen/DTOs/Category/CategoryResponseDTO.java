package quitto.FinaceSysthen.DTOs.Category;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import quitto.FinaceSysthen.DTOs.Trasactions.PaymentResponseDTO;


@Schema(name = "CategoryResponseDTO", description = "Response payload for category-based payment queries")
public class CategoryResponseDTO {

    @Schema(description = "List of payments returned by the query")
    private List<PaymentResponseDTO> payments;
    
    @Schema(description = "Selected category used to filter payments", example = "FOOD")
    private String categorySelect;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(List<PaymentResponseDTO> payments, String categorySelect) {
        this.payments = payments;
        this.categorySelect = categorySelect;
    }

    public List<PaymentResponseDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponseDTO> payments) {
        this.payments = payments;
    }

    public String getCategorySelect() {
        return categorySelect;
    }

    public void setCategorySelect(String categorySelect) {
        this.categorySelect = categorySelect;
    }
}
