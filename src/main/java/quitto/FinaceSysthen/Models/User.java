package quitto.FinaceSysthen.Models;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import quitto.FinaceSysthen.Enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",nullable=false,unique = true)
    private Long userId;

    @Column(nullable=false,unique = false)
    private String name;

    @DecimalMin("0.0")
    @Column(name = "amount", nullable = false, unique = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }
    
    public User(String name, @DecimalMin("0.0") BigDecimal amount, Role role) {
        this.name = name;
        this.amount = amount;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;

    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", amount=" + amount + "]";
    }

}
