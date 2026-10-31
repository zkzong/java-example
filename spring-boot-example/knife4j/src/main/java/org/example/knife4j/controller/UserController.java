package org.example.knife4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 示例用户接口，用于展示 Knife4j 文档效果.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户接口", description = "用于演示 Knife4j 的用户相关接口")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户", description = "根据用户ID获取用户的基本信息")
    public Map<String, Object> getUserById(@PathVariable("id") Long id) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("name", "User-" + id);
        user.put("age", 18);
        return user;
    }

    @GetMapping("/hello")
    @Operation(summary = "问候接口", description = "简单返回一条问候语")
    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello Knife4j");
        return result;
    }
}

