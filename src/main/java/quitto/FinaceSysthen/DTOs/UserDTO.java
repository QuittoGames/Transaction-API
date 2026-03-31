package quitto.FinaceSysthen.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import quitto.FinaceSysthen.Enums.Role;

public class UserDTO {

    @JsonAlias("user_id")
    private Long userId;
    private String name;
    @JsonAlias({"amount", "value"})
    private BigDecimal amount;
    private Role role;

    // GETTERS
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Role getRole() {
        return role;
    }

    // SETTERS
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}