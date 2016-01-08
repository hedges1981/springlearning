package com.hedges.web.config;

import com.hedges.persistence.config.PersistenceConfiguration;
import com.hedges.services.config.ServicesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by rowland-hall on 08/01/16
 */
@ComponentScan( "com.hedges.web.controllers" )
//NOTE: without specifying an @ComponentScan base package, the defaut behaviour from the @SpringBootApplication is to scan the same package
//as this class. Specifying the desired package above stops it from doing the default scan.
@Import( {PersistenceConfiguration.class, ServicesConfiguration.class} )
@SpringBootApplication
//NOTE: the @SpringBootApplication combines @Configuration, @ComponentScan and @EnableAutoConfiguration into one.
//NOTE; Don't need @EnableTransactionManagement, @Transactional stuff works by default.
public class WebConfig
{
    public static void main( String[] args )
    {
        SpringApplication.run( WebConfig.class, args );
    }
}
