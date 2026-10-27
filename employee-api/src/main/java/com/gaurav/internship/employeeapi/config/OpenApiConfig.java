package com.gaurav.internship.employeeapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Sets the title/description shown at the top of Swagger UI.
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeApiInfo() {
        return new OpenAPI().info(new Info()
                .title("Employee Management REST API")
                .version("1.0.0")
                .description("Spring Boot REST API with in-memory storage, DTO validation "
                        + "and centralised exception handling."));
    }
}
