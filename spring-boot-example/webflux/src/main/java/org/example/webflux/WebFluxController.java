package org.example.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * WebFlux
 *
 * @author zong
 */
@Slf4j
@RestController
@RequestMapping("/webflux")
public class WebFluxController {

    /**
     * get请求
     *
     * @return
     */
    @GetMapping("/get")
    public Mono<String> get() {
        log.info("get");
        return Mono.just("get");
    }

    /**
     * post请求
     *
     * @return
     */
    @PostMapping("/post")
    public Mono<String> post() {
        log.info("post");
        return Mono.just("post");
    }

}
