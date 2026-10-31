package org.example.sb3.nacos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/provider")
@Tag(name = "提供者接口", description = "Provider 服务相关接口")
public class ProviderController {

    @Operation(summary = "处理请求", description = "接收并处理请求")
    @PostMapping("/post")
    public String post(@Parameter(description = "名称参数") @RequestParam String name) {
        log.info("name = {}", name);
        return name;
    }

}
