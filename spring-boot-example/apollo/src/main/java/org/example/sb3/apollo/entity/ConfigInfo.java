package org.example.sb3.apollo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: zong
 * @Date: 2021/10/26
 */
@ConfigurationProperties(prefix = "config")
@Component
@RefreshScope
@Data
public class ConfigInfo {

    private String username;

}
