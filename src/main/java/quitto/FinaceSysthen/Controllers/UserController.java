package quitto.FinaceSysthen.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import quitto.FinaceSysthen.Models.User;
import quitto.FinaceSysthen.Services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService usersService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/listUser")
    @Operation(
        tags = {"User"},
        description = "List Users in DB"
    )
    public List<User> listUsers() {
        try {
            List<User> users = usersService.listUsers();
            logger.info("Listagem concluída. Total de usuários: {}", users.size());
            return users;
        } catch (RuntimeException e) {
            logger.error("Erro ao listar usuários, Erro: {}", e);
            return List.of();
        }
    }

}
