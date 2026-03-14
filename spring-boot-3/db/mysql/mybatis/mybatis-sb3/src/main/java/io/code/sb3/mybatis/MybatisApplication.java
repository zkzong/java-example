package io.code.sb3.mybatis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class);
    }

    @Bean
    public CommandLineRunner showCacheManager(CacheManager cacheManager) {
        return args -> System.out.println("CacheManager = " + cacheManager.getClass());
    }

}
