package com.pp.zerocraft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.pp.zerocraft.mapper")
public class ZeroCraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroCraftApplication.class, args);
    }

}