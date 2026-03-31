package quitto.FinaceSysthen.Services;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import quitto.FinaceSysthen.Enums.Role;
import quitto.FinaceSysthen.Models.User;
import quitto.FinaceSysthen.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name , BigDecimal amount , Role role){
        User user = new User(name,amount,role);
        return userRepository.save(user);
    }
}
