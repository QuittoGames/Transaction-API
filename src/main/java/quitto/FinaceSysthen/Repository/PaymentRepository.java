package quitto.FinaceSysthen.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import quitto.FinaceSysthen.Enums.Category;
import quitto.FinaceSysthen.Models.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    List<Payment> findByPayerUserId(Long userId);

    List<Payment> findByCategory(Category category);

    List<Payment> findByDate(LocalDate date);

    Payment findPaymentById(Long id);


    @Modifying
    @Query("""
        UPDATE User u
        SET u.amount = u.amount - :value
        WHERE u.userId = :userId
        AND u.amount >= :value
    """)
    int withdraw(Long userId, BigDecimal value);


    @Modifying
    @Query("""
        UPDATE User u
        SET u.amount = u.amount + :value
        WHERE u.userId = :userId
    """)
    int deposit(Long userId, BigDecimal value);
}
