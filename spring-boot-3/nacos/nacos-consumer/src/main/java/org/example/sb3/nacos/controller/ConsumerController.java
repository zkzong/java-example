package org.example.sb3.nacos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.sb3.nacos.ProviderClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Slf4j
@Tag(name = "消费者接口", description = "Consumer 服务相关接口")
public class ConsumerController {

    @Resource
    private ProviderClient providerClient;

    @Operation(summary = "调用 Provider 服务", description = "通过 OpenFeign 调用 Provider 服务")
    @PostMapping("/post")
    public String post(@Parameter(description = "名称参数") @RequestParam String name) {
        log.info("name = {}", name);
        return providerClient.post(name);
    }

}
