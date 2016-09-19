package com.hedges.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by rhall on 10/08/2016.
 */
@SpringBootApplication()
@Import({ SecurityConfig.class})
@ComponentScan("tv.sis.dynamicracecards.controllers")
public class ApplicationRunner extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger( ApplicationRunner.class );

    public static void main(String[] args) {

        LOGGER.info("Starting Application");
        SpringApplication.run( ApplicationRunner.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/aview");

    }
}
