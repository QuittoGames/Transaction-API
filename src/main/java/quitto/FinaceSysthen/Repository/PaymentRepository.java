package quitto.FinaceSysthen.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quitto.FinaceSysthen.Models.Payment;
import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    List<Payment> findByUserUserId(Long userId);

    List<Payment> findByCategory(String category);

    List<Payment> findByDate(LocalDate date);

    Payment findPaymentById(Long id);

}
