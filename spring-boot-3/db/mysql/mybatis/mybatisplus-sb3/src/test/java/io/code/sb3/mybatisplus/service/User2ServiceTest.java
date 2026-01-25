package io.code.sb3.mybatisplus.service;

import io.code.sb3.mybatisplus.domain.User;
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
public class User2ServiceTest {

    @Autowired
    private User2Service user2Service;

    /**
     * list
     */
    @Test
    public void list() {
        List<User> list = user2Service.list();
        System.out.println(list);
    }

}
