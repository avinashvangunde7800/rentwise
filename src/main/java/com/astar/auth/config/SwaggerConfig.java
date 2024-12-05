package com.astar.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PG Rent Management API")
                        .description("API documentation for managing PG rents")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@pgrent.com")));
    }
}

