package com.sage.lspcs.sls.intouchproxy.web.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by rowland-hall on 08/01/16
 */
public class ServletInitialiser extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application )
    {
        return application.sources( WebConfig.class );
    }
}

