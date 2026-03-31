package quitto.FinaceSysthen.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

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
}