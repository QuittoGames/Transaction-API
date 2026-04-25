package quitto.FinaceSysthen.Controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import quitto.FinaceSysthen.Enums.Role;
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


    //Create mini DB for tests
    @Operation(
        tags = {"User"},
        description = "Create Users in DB for tests"
    )
    @PostMapping("/seed")
    public String seed() {
        try {
            usersService.createUser("teste", new BigDecimal("100"), Role.USER);
            logger.info("Usuário 'teste' criado com sucesso.");

            usersService.createUser("teste1", new BigDecimal("100"), Role.USER);
            logger.info("Usuário 'teste1' criado com sucesso.");

            logger.info("Seed finalizado com sucesso.");
            return "ok";
        } catch (Exception e) {
            logger.error("Erro ao executar seed de usuários.", e);
            return "erro";
        }
    }

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
