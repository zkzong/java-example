package io.code.sb3.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.code.sb3.mybatisplus.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zongz
 * @Date: 2024/10/26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void page() {
        Page page = userService.page(new Page(1, 1), null);
        System.out.println(page);
    }

    /**
     * 根据主键更新
     */
    @Test
    public void saveOrUpdate1() {
        User user = new User();
        user.setId(1L);
        user.setAge(3);
        userService.saveOrUpdate(user);
    }

    /**
     * 根据指定字段更新
     */
    @Test
    public void saveOrUpdate2() {
        // 根据user_name更新数据
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", "java");

        User user = new User();
        // 设置id也不会更新表id
        user.setId(10L);
        user.setAge(3);
        userService.update(user, updateWrapper);
    }

    /***
     * 批量更新
     */
    @Test
    public void saveOrUpdateBatch() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUserName("John");
        user1.setAge(25);
        userList.add(user1);
        User user2 = new User();
        user2.setId(2L);
        user2.setUserName("Jane");
        user2.setAge(30);
        userList.add(user2);
        User user3 = new User();
        user3.setUserName("Mike");
        user3.setAge(35);
        userList.add(user3);
        userService.saveOrUpdateBatch(userList);
    }
}
