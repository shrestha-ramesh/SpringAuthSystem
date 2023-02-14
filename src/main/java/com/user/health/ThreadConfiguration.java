package com.user.health;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadConfiguration {

    private int threadSize =1;

    @Bean
    public Executor healthExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(threadSize);
        threadPoolTaskExecutor.setCorePoolSize(threadSize);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
