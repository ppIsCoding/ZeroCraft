package com.pp.zerocraft;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
class ZeroCraftScreenshot {

    public static void main(String[] args) {
        SpringApplication.run(ZeroCraftScreenshot.class, args);
    }

}
