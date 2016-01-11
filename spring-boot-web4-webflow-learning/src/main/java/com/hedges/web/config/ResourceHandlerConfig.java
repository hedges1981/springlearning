package com.hedges.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;




/**
 * Created by rowland-hall on 11/01/16
 */
@Configuration
public class ResourceHandlerConfig extends WebMvcConfigurerAdapter
{
    // NOTE: the below allows for the mapping of static resources to URLs, check the classpath example also.
    // NOTE: this is the equivalent in XML of having e.g. <mvc:resources mapping="/resources/**" location="/resources/theme1/"/>
    // NOTE: with static content it looks like it defaults to using the 304- not modified 'handshake', see e.g. http://stackoverflow.com/questions/20978189/how-304-not-modified-works
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //test this with url: http://localhost:8080/staticcontent/astaticfile.txt
        registry.addResourceHandler("/staticcontent/**")
                .addResourceLocations("WEB-INF/staticcontent/");

        //test this with url: http://localhost:8080/classpathresources/classpathfile.txt
        registry.addResourceHandler("/classpathresources/**")
                .addResourceLocations("classpath:/classpathresources/").setCachePeriod( 3600 );
        //NOTE: how a cache period of 3600 seconds has been set, this means that spring will set the cache-control header on the response.
    }
}
