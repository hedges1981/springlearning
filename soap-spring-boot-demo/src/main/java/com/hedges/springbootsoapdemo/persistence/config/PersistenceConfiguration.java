package com.hedges.springbootsoapdemo.persistence.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by rowland-hall on 08/01/16
 */
@EntityScan("com.hedges.persistence.model")
//NOTE: again the entity scan defaults to the same package if nothing given here.
@EnableJpaRepositories( "com.hedges.persistence.repositories" )
@Configuration
public class PersistenceConfiguration
{
}
