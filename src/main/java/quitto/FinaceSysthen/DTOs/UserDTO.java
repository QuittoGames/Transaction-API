package quitto.FinaceSysthen.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;
import quitto.FinaceSysthen.Enums.Role;

@Schema(name = "UserDTO", description = "User payload used by the API")
public class UserDTO {

    @JsonAlias("user_id")
    @Schema(description = "User identifier", example = "1")
    private Long userId;

    @Schema(description = "User display name", example = "Ana")
    private String name;

    @JsonAlias({"amount", "value"})
    @Schema(description = "User balance/amount", example = "2500.00")
    private BigDecimal amount;

    @Schema(description = "User role", example = "USER")
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