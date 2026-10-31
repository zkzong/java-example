package org.example.sb3.apollo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.example.sb3.apollo.entity.ConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * apollo获取配置的5种方式
 *
 * @Author: zong
 * @Date: 2021/10/28
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${config.username}")
    private String configUsername;

    @Autowired
    private Environment env;

    // 不支持热更新，需要使用@RefreshScope才能热更新
    @Autowired
    private ConfigInfo configInfo;

    @ApolloConfig
    private Config config1;

    private Config config2 = ConfigService.getAppConfig();

    @GetMapping("/get")
    public String get(String param) {
        System.out.println("@Value = " + configUsername);
        System.out.println("env = " + env.getProperty(param));
        System.out.println("propConfig = " + configInfo.getUsername());
        System.out.println("@ApolloConfig = " + config1.getProperty(param, ""));
        System.out.println("ConfigService = " + config2.getProperty(param, ""));
        return null;
    }
}
