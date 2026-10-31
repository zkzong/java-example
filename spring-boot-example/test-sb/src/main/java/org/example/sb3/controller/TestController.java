package org.example.sb3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zong
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * get请求
     *
     * @return
     */
    @GetMapping("/get")
    public String get() {
        log.info("get");
        return "success";
    }

    /**
     * post请求
     *
     * @return
     */
    @PostMapping("/post")
    public String post() {
        log.info("post");
        return "success";
    }

}
