package org.example.sb3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.sb3.pojo.springdoc.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zong
 */
@Slf4j
@RestController
@RequestMapping("/springdoc")
@Tag(name = "SpringDoc", description = "SpringDoc测试类")
public class SpringDocController {

    @Operation(summary = "查询用户信息", description = "查询用户信息")
    @PostMapping("/test")
    public String test(@RequestBody User user) {
        return user.toString();
    }

    @Operation(summary = "获取用户详细信息", description = "根据用户的id来获取用户详细信息")
    @Parameter(name = "id", description = "用户ID", required = true)
    @GetMapping(value = "/findById")
    public User findById(@RequestParam(value = "id") Long id) {
        return new User(id, "dalaoyang", "123");
    }

    @Operation(summary = "保存用户", description = "保存用户")
    @PostMapping(value = "/saveUser")
    public String saveUser(@RequestBody @Parameter(name = "用户对象", description = "传入json格式", required = true) User user) {
        return user.toString();
    }

    @Operation(summary = "修改用户", description = "修改用户")
    @Parameters({
            @Parameter(name = "id", description = "主键id", required = true),
            @Parameter(name = "username", description = "用户名称", required = true),
            @Parameter(name = "password", description = "用户密码", required = true)
    })
    @GetMapping(value = "/updateUser")
    public String updateUser(@RequestParam(value = "id") Long id, @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password) {
        User user = new User(id, username, password);
        return user.toString();
    }

    @Operation(summary = "删除用户", description = "根据用户的id来删除用户")
    @Parameter(name = "id", description = "用户ID", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功！"),
            @ApiResponse(responseCode = "401", description = "未授权！"),
            @ApiResponse(responseCode = "404", description = "页面未找到！"),
            @ApiResponse(responseCode = "403", description = "出错了！")
    })
    @DeleteMapping(value = "/deleteUserById")
    public String deleteUserById(@RequestParam(value = "id") Integer id) {
        return "success!";
    }

}
