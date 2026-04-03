package quitto.FinaceSysthen.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import quitto.FinaceSysthen.Enums.Category;

@Entity 
@Table(name = "payment")
public class Payment {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, unique = true)
    private Long id;

    @DecimalMin("0.0")
    @Column(name = "amount", nullable = false, unique = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,unique =  false)
    private Category category;

    @Column(nullable = false , unique = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User payer;

    public Payment(@DecimalMin("0.0") BigDecimal value, Category category, LocalDateTime date, User payer) {
        this.value = value;
        this.category = category;
        this.date = date;
        this.payer = payer;
    }

    public Payment() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public User getPayer() {
        return payer;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    } 


    @Override
    public String toString() {
        return "Payment [id=" + id + ", value=" + value + ", category=" + category + ", date=" + date + ", payer=" + payer + "]";
    }

}
