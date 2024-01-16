package com.teste.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(buildInfo());
    }

    private Info buildInfo() {
        return new Info()
                .title("PEDIDOS - TESTE53")
                .description("API para o PEDIDOS - TESTE53")
                .version("0.0.1")
                .contact(new Contact()
                        .name("PEDIDOS")
                        .url("#")
                )
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0")
                );
    }

}
