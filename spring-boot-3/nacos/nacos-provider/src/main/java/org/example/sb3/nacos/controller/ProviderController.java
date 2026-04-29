package org.example.sb3.nacos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @PostMapping("/post")
    public String post(@RequestParam String name) {
        log.info("name = {}", name);
        return name;
    }

}
