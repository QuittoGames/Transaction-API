package quitto.FinaceSysthen.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quitto.FinaceSysthen.Models.User;
import quitto.FinaceSysthen.Enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUserId(Long userId);

    List<User> findAll();

    List<User> getByRole(Role role);   
    
}
