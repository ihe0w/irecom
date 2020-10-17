package com.example.business_server.config;

import com.example.business_server.utils.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean("dbExcutor")
    public Executor dbExcutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(Constant.INIT_THREAD_NUM);
        executor.setMaxPoolSize(Constant.MAX_THREAD_NUM);
        executor.setQueueCapacity(Constant.QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(Constant.ALIVED_SECONDS);
        executor.setThreadNamePrefix("db-async");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();

        return executor;
    }
}
