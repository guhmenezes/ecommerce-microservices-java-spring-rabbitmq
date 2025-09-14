package br.com.ghmenezes.warehouse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customerAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API do Estoque do E-commerce")
                        .version("1.0")
                        .description("Documentação da API do estoque do e-commerce."));
    }
}
