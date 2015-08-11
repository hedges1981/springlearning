package com.hedges.javaconfigurationtesting;

import com.hedges.springlearning.U;
import com.hedges.springlearning.refandinnerbean.AGeneralBean1;
import com.hedges.springlearning.refandinnerbean.AGeneralBean2;
import com.hedges.springlearning.refandinnerbean.AGeneralBean3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by rowland-hall on 28/01/15
 */
public class JavaConfigurationMain
{
    public static void main(String[] args)
    {
        //NOTE how this line causes the bean annotated with @Profile("testProfile") to get loaded.
        System.setProperty("spring.profiles.active","testProfile");
                                                                      //could put >1 class in here, like you can with XML files.
        ApplicationContext appContext = new AnnotationConfigApplicationContext( TestConfiguration.class );

        //note that you can get the bean by class.
        AGeneralBean1 aGeneralBean1 = (AGeneralBean1)appContext.getBean( AGeneralBean1.class );

        U.print( aGeneralBean1 );

        //note here that full name of the method can be used to get the bean out.
        aGeneralBean1 = (AGeneralBean1)appContext.getBean( "getAGeneralBean1");

        //this will give a NoSuchBeanDefinitionException, as it is only made available via the method name, not the 'property name'.
        //Object aGeneralBean1pt5 = appContext.getBean( "aGeneralBean1");

        //Note here that we can get it using the explicitly defined name.
        AGeneralBean2 aGeneralBean2 = ( AGeneralBean2 ) appContext.getBean( "aGeneralBean2" );

        //this will not work as the explicitly defined name replaces the default method name thing.
        //AGeneralBean2 aGeneralBean2pt5 = ( AGeneralBean2 ) appContext.getBean( "getAGeneralBean2" );

        U.print(aGeneralBean2);

        //see how this bean has been brought in through the @Import annotation.
        AGeneralBean3 aGeneralBean3 =(AGeneralBean3 ) appContext.getBean( "aBeanFromAnImportedContext" );

        U.print(aGeneralBean3);
    }
}
