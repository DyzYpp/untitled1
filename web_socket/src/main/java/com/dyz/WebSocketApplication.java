package com.dyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName WebSocketApplication
 * @Author
 * @Date 2020/7/29
 * @description
 */
@SpringBootApplication
@EnableScheduling
public class WebSocketApplication {

    private static final Logger log = LoggerFactory.getLogger(WebSocketApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class,args);
        log.info("WebSocket服务启动成功");
    }
}
