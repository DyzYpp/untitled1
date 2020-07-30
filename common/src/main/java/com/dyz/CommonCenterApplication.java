package com.dyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName OrderCenterApplication
 * @Author
 * @Date 2020/7/28
 * @description
 */
@SpringBootApplication
public class CommonCenterApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommonCenterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CommonCenterApplication.class,args);
        logger.info("------登录系统启动成功-----");
    }
}
