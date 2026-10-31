package org.example.sb3.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zongz
 * @Date: 2024-12-06
 */
@Data
@Component
@ConfigurationProperties(prefix = "nacos.config")
public class NacosConfig {

    private String name;

}
