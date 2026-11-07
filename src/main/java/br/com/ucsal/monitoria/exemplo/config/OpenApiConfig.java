package br.com.ucsal.monitoria.exemplo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title("ms-exemplo")
                .description("Template dos microserviços do G5 – Sistema de Controle de Monitoria")
                .version("0.0.1"));
    }
}
