package quitto.FinaceSysthen.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    @Column(nullable = false ,unique =  false)
    private String category;

    @Column(nullable = false , unique = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }
    public User getUser() {
        return user;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    } 


    @Override
    public String toString() {
        return "Payment [id=" + id + ", value=" + value + ", category=" + category + ", date=" + date + ", user=" + user + "]";
    }

}
