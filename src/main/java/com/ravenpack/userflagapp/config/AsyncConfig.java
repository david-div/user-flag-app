package com.ravenpack.userflagapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("messageScoreThreadPoolTaskExecutor")
    public Executor messageScoreThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("MessageScoreTaskExecutor-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("messageTranslationThreadPoolTaskExecutor")
    public Executor messageTranslationThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("MessageTranslationTaskExecutor-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
