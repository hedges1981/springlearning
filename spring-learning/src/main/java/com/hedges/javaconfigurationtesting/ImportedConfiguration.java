package com.hedges.javaconfigurationtesting;

import com.hedges.springlearning.refandinnerbean.AGeneralBean3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rowland-hall on 28/01/15
 */
@Configuration
public class ImportedConfiguration
{
    @Bean(name="aBeanFromAnImportedContext")
    public AGeneralBean3 getAGeneralBean3()
    {
        return new AGeneralBean3();
    }
}
