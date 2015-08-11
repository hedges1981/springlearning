package com.hedges.javaconfigurationtesting;

import com.hedges.springlearning.refandinnerbean.AGeneralBean1;
import com.hedges.springlearning.refandinnerbean.AGeneralBean2;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;

/**
 * Created by rowland-hall on 28/01/15
 */
@Configuration
//imports the configuration, like the import on the top of an XML file.
//this will make any beans and other stuff it defines available in this configuration.
@Import(ImportedConfiguration.class)
public class TestConfiguration
{
    @Bean
    public AGeneralBean1 getAGeneralBean1()
    {
        AGeneralBean1 aGeneralBean1 = new AGeneralBean1();
        aGeneralBean1.setaString( "this is a string" );

        return aGeneralBean1;
    }

    @Bean(name="aGeneralBean2", autowire= Autowire.BY_TYPE ) //could also have on here initMethod=".." and destroyMethod="....."
    @Scope(value="singleton", proxyMode = ScopedProxyMode.TARGET_CLASS )
    @Primary    //equivalent of saying autowire-candidate="primary" in the xml.
    @Profile( "testProfile" )
    public AGeneralBean2 getAGeneralBean2()
    {
       return new AGeneralBean2();
    }

}
