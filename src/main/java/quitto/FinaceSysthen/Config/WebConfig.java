package quitto.FinaceSysthen.Config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quitto.FinaceSysthen.Enums.Role;
import quitto.FinaceSysthen.Services.UserService;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserService usersService;

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve todos os arquivos estáticos de dentro do diretório /templates/ e /static/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redireciona a rota "/" para exibir o index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Bean
    public String seed() {
        try {
            usersService.createUser("teste", new BigDecimal("100"), Role.USER);
            logger.info("Usuário 'teste' criado com sucesso.");

            usersService.createUser("teste1", new BigDecimal("100"), Role.USER);
            logger.info("Usuário 'teste1' criado com sucesso.");

            logger.info("Seed finalizado com sucesso.");
            return "DB: ok";
        } catch (Exception e) {
            logger.error("Erro ao executar seed de usuários.", e);
            return "Erro ao executar seed de usuários";
        }
    }

}