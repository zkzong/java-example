package org.example.sb3.ss.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExampleServiceTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    public void run() throws SQLException {
        exampleService.run();
    }

}
