package com.pp.zerocraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class ZeroCraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroCraftApplication.class, args);
    }

}
