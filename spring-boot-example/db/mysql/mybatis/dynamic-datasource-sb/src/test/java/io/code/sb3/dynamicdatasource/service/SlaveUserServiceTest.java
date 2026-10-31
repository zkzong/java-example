package io.code.sb3.dynamicdatasource.service;

import io.code.sb3.dynamicdatasource.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @Author: zongz
 * @Date: 2024/10/26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SlaveUserServiceTest {

    @Autowired
    private SlaveUserService slaveUserService;

    /**
     * list
     */
    @Test
    public void list() {
        List<User> list = slaveUserService.list();
        System.out.println(list);
    }

}
