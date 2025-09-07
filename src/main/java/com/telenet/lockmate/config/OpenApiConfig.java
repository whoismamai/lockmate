package com.telenet.lockmate.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI lockmateOpenAPI() {
        return new OpenAPI()
                // 🔒 JWT Security
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // 📑 API Info
                .info(new Info()
                        .title("LockMate API")
                        .description("Authentication & Lock Management API documentation")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Dev")
                                .email("support@lockmate.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                // 📂 External Docs
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repo")
                        .url("https://github.com/whoismamai/lockmate"));
    }
}
