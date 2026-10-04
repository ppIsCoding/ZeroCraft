package com.pp.zerocraft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.pp.zerocraft.mapper")
@ComponentScan("com.pp")
class ZeroCraftUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeroCraftUserApplication.class, args);
    }
}

