package org.example.sb3.nacos.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.sb3.nacos.config.NacosConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@Slf4j
// @RefreshScope
public class ConfigController {

    @Value("${nacos.config.name}")
    private String name;

    @Autowired
    private NacosConfig nacosConfig;

    @GetMapping("/value")
    public String value() {
        log.info("name = {}", name);
        return name;
    }

    @GetMapping("/configurationproperties")
    public String configurationproperties() {
        return nacosConfig.getName();
    }

}
