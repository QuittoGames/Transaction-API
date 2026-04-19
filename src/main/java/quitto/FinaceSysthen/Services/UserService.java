package quitto.FinaceSysthen.Services;

import java.math.BigDecimal;
import java.util.List;
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
        try {
            User user = new User(name,amount,role);
            return userRepository.save(user);
        } catch (RuntimeException runtimeException) {
            throw runtimeException;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<User> listUsers(){
        try {
            return userRepository.findAll();
        } catch (RuntimeException runtimeException) {
            throw runtimeException;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
