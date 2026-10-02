package com.pp.zerocraft.config;

import com.pp.zerocraft.monitor.AiModelMonitorListener;
import dev.langchain4j.http.client.HttpClientBuilder;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "langchain4j.open-ai.streaming-chat-model")
@Data
public class StreamingChatModelConfig {

    @Resource
    private HttpClientBuilder httpClientBuilder;

    private String baseUrl;

    private String apiKey;

    private String modelName;

    private Double temperature;

    private boolean logRequests;

    private boolean logResponses;

    private Map<String, String> customHeaders;

    @Resource
    private AiModelMonitorListener  aiModelMonitorListener;

    @Bean
    @Scope("prototype")
    public StreamingChatModel streamingChatModelPrototype() {
        return OpenAiStreamingChatModel.builder()
                .httpClientBuilder(httpClientBuilder)
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(temperature)
                .customHeaders(customHeaders)
                .logRequests(logRequests)
                .logResponses(logResponses)
                .listeners(List.of(aiModelMonitorListener))
                .build();
    }
}
