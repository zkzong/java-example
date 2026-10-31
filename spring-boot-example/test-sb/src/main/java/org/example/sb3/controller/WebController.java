package org.example.sb3.controller;

import org.example.sb3.pojo.UserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用WebMvcConfigurer
 *
 * @Author: zongz
 * @Date: 2024/10/27
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @RequestMapping("/user")
    public ResponseEntity<UserVo> user() {
        UserVo user = UserVo.builder().id(1L).userName("Java").build();
        return ResponseEntity.ok(user);
    }

}
