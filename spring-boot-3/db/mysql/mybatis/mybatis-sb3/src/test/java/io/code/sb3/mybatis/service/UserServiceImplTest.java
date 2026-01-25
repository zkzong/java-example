package io.code.sb3.mybatis.service;

import com.github.pagehelper.PageInfo;
import io.code.sb3.mybatis.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        List<User> result = userService.getAllUsers();
        System.out.println(result);
    }

    @Test
    public void getUserPage() {
        PageInfo<User> result = userService.getUserPage(1, 1);
        System.out.println(result);
    }

    @Test
    public void testFindByUserName() {
        User result = userService.findByUserName("John");
        System.out.println(result);
    }

    @Test
    public void testInsertOne() {
        userService.insertOne("John", 25);
    }

    @Test
    public void testInsertOneWithDate() {
        User user = new User();
        user.setUserName("John");
        user.setAge(25);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        userService.insertOneWithDate(user);
    }

    @Test
    public void testInsertBatch() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUserName("John");
        user1.setAge(25);
        userList.add(user1);
        User user2 = new User();
        user2.setUserName("Jane");
        user2.setAge(30);
        userList.add(user2);

        int result = userService.insertBatch(userList);
        System.out.println(result);
    }

    @Test
    public void testInsertAndGetId() {
        User user = new User();
        user.setUserName("John");
        user.setAge(25);

        int result = userService.insertAndGetId(user);
        System.out.println(result);
        System.out.println(user.getId());
    }

    @Test
    public void testFindByIndex() {
        User result = userService.findByIndex("John", 25);
        System.out.println(result);
    }

    @Test
    public void testFindByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "John");
        map.put("age", 25);

        User result = userService.findByMap(map);
        System.out.println(result);
    }

    @Test
    public void testFindByParam() {
        User result = userService.findByParam("John", 25);
        System.out.println(result);
    }

    @Test
    public void testNamein() {
        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Jane");

        List<User> result = userService.namein(names, "John");
        System.out.println(result);
    }

    @Test
    public void testUpdateBatch() {
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

        int result = userService.updateBatch(userList);
        System.out.println(result);
    }
}
