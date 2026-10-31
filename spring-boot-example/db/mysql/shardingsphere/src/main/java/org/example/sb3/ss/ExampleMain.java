package org.example.sb3.ss;


import org.example.sb3.ss.service.ExampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class ExampleMain {

    public static void main(final String[] args) throws SQLException {
        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleMain.class, args)) {
            ExampleService exampleService = applicationContext.getBean(ExampleService.class);
            exampleService.run();
        }
    }
}
