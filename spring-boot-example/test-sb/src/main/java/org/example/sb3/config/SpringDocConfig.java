package org.example.sb3.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info())
                .externalDocs(new ExternalDocumentation()
                        .description("更多文档")
                        .url("https://springdoc.org"));
    }

    @Bean
    public Info info() {
        return new Info()
                .title("API文档")
                .description("Spring Boot 3 应用接口文档")
                .version("v1.0");
    }

}
