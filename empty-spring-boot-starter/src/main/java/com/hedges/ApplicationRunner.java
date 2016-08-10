package com.hedges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rhall on 10/08/2016.
 */
@SpringBootApplication
@Import({DataSources.class, SecurityConfig.class})
public class ApplicationRunner extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run( ApplicationRunner.class, args);
    }

}
