package com.pp.zerocraft;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.pp.zerocraft.mapper")
public class ZeroCraftApplication {

    public static void main(String[] args) {
        // classpath 上同时存在 SpringRestClient 和 JDK HttpClient 两个实现，需显式指定用 JDK 的实现，
        // 否则 LangChain4j 的 SPI 加载器会因"多个 HTTP 客户端冲突"而启动失败
        System.setProperty("langchain4j.http.clientBuilderFactory",
                "dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory");
        SpringApplication.run(ZeroCraftApplication.class, args);
    }

}