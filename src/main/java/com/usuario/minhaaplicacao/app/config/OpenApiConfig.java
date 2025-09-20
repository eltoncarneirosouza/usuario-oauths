package com.usuario.minhaaplicacao.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
      info = @Info(
            title = "API de Clientes",
            version = "1.0",
            description = "API para gerenciamento de clientes com autenticação JWT",
            contact = @Contact(
                  name = "Suporte",
                  email = "suporte@example.com"
            )
      ),
      servers = {
            @Server(
                  description = "Local ENV",
                  url = "http://localhost:8080"
            )
      }
)
@SecurityScheme(
      name = "access_token",
      type = SecuritySchemeType.HTTP,
      scheme = "bearer",
      bearerFormat = "JWT",
      description = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbHRvbkBnbWFpbC5jb20iLCJpYXQiOjE3NTgzODY4MDIsImV4cCI6MTc1ODQ3MzIwMn0.Fn-OCTE4OBlCIQvp0RHZsgnxVGjJ37aUTjKzLrcnQf0"
)
public class OpenApiConfig {
}
