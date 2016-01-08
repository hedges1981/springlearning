package com.hedges.services.config;

import com.hedges.services.PersonService;
import com.hedges.services.impl.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rowland-hall on 08/01/16
 */

@Configuration
public class ServicesConfiguration
{
    @Bean
    public PersonService personService()
    {
        return new PersonServiceImpl();
    }
}
