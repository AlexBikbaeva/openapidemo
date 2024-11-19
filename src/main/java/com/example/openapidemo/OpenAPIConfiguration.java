package com.example.openapidemo;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenAPI () {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Имя Фамилия");
        myContact.setEmail("my.email@example.com");

        Info info = new Info()
                .title("Системное API для управления сотрудниками")
                .version("1.0")
                .description("Это API предоставляет эндпоинты для управления сотрудниками.")
                .contact(myContact);
        return new OpenAPI().info(info).servers(List.of(server));
    }
}
