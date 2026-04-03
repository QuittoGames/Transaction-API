package quitto.FinaceSysthen.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quitto.FinaceSysthen.Enums.Category;
import quitto.FinaceSysthen.Models.Payment;
import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    List<Payment> findByPayerUserId(Long userId);

    List<Payment> findByCategory(Category category);

    List<Payment> findByDate(LocalDate date);

    Payment findPaymentById(Long id);

}
