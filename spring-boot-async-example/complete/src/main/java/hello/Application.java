package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class Application extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



    @Bean
    public Executor asyncExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);

        executor.setThreadNamePrefix("asyncExecutor1-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor asyncExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);

        executor.setThreadNamePrefix("asyncExecutor2-");

        executor.initialize();
        return executor;
    }




//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//
//        executor.setThreadNamePrefix("GithubLookup-");
//        executor.initialize();
//        return executor;
//    }

}
