package com.hedges.javaconfigurationtesting;

import com.hedges.springlearning.refandinnerbean.AGeneralBean1;
import com.hedges.springlearning.refandinnerbean.AGeneralBean2;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by rowland-hall on 28/01/15
 */
@Configuration
@Import(ImportedConfiguration.class)   //imports the configuration, like the import on the top of an XML file.
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
    public AGeneralBean2 getAGeneralBean2()
    {
       return new AGeneralBean2();
    }

}
