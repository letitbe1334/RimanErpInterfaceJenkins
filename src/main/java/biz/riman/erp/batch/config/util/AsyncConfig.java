package biz.riman.erp.batch.config.util;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor getAsyncExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(10);
      executor.setMaxPoolSize(50);
      executor.setQueueCapacity(100);
      executor.setThreadNamePrefix("api-request-body-async-");
      executor.initialize();
      return executor;
    }
    
}
