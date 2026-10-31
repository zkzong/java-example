package org.example.sb3.nacos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nacos Gateway API")
                        .version("1.0")
                        .description("Nacos Gateway 聚合 API 文档"));
    }

    /**
     * 聚合 nacos-consumer 服务 API
     */
    @Bean
    public GroupedOpenApi consumerApi() {
        return GroupedOpenApi.builder()
                .group("nacos-consumer")
                .pathsToMatch("/consumer/**")
                .build();
    }

    /**
     * 聚合 nacos-provider 服务 API
     */
    @Bean
    public GroupedOpenApi providerApi() {
        return GroupedOpenApi.builder()
                .group("nacos-provider")
                .pathsToMatch("/provider/**")
                .build();
    }
}