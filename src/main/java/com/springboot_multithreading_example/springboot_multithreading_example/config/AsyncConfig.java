package com.springboot_multithreading_example.springboot_multithreading_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Minimum number of threads that will always remain alive.
//        Example: If 3 tasks arrive, 3 threads will run (out of the core 5).
        executor.setMaxPoolSize(10); // Maximum number of threads allowed
//        Example: If 30 tasks arrive at same time → 5 core threads + 25 queued + then extra threads up to 10
        executor.setQueueCapacity(25); // When all core threads are busy, tasks are placed into a queue.
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }
}
