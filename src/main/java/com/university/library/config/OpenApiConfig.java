package com.university.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Cloud Library Manager API")
                .version("1.0")
                .description("API REST pour la gestion d'une bibliothèque universitaire (livres, étudiants, emprunts)"));
    }
}
