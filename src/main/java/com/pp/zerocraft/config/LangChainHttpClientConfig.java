package com.pp.zerocraft.config;

import dev.langchain4j.http.client.HttpClientBuilder;
import dev.langchain4j.http.client.jdk.JdkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * LangChain4j HTTP 客户端配置
 * <p>
 * 默认情况下 langchain4j 的 OpenAI starter 使用 SpringRestClientBuilder，而它在构造 SpringRestClient 时
 * 会调用 ClientHttpRequestFactoryBuilder.detect() 强制覆盖请求工厂；由于 classpath 上存在
 * Apache HttpClient5（webdrivermanager 传递引入），会选中 HttpComponentsClientHttpRequestFactory，
 * 从而把上游返回的 chunked 响应体截断成 "{"，导致解析失败。
 * <p>
 * 这里显式提供一个基于 JDK HttpClient 的 HttpClientBuilder 作为主 Bean，绕开上述问题。
 */
@Configuration
public class LangChainHttpClientConfig {

    @Bean
    @Primary
    public HttpClientBuilder langChainHttpClientBuilder() {
        return JdkHttpClient.builder();
    }
}
