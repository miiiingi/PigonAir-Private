package com.example.pigonair.global.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "asyncExecutor")
    public TaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(9); // Core Pool Size 설정
        executor.setMaxPoolSize(18); // Max Pool Size 설정
        executor.setQueueCapacity(50); // 큐 용량 설정
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}