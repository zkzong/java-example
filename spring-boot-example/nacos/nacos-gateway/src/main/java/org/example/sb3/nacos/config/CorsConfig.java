package org.example.sb3.nacos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 允许的请求来源
        corsConfig.addAllowedOriginPattern("*");

        // 允许的请求头
        corsConfig.addAllowedHeader("*");

        // 允许的请求方法
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // 允许携带凭证（如 Cookie）
        corsConfig.setAllowCredentials(true);

        // 预检请求的缓存时间（秒）
        corsConfig.setMaxAge(3600L);

        // 暴露的响应头
        corsConfig.setExposedHeaders(Arrays.asList("X-Requested-With", "Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}