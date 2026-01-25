package io.code.sb3.mybatis.controller;

import io.code.sb3.mybatis.domain.User;
import io.code.sb3.mybatis.req.NameInReq;
import io.code.sb3.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public String insert(@RequestBody User user) {
        userService.insertAndGetId(user);
        return "success";
    }

    @PostMapping("/insertwithdate")
    public String insertWithDate(@RequestBody User user) {
        userService.insertOneWithDate(user);
        return "success";
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/namein")
    public List<User> namein(@RequestBody NameInReq nameInReq) {
        return userService.namein(nameInReq.getNames(), nameInReq.getName());
    }
}
