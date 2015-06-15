package com.hedges.profilesexample;

import com.hedges.springlearning.U;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 13/05/15
 */
public class ProfilesExampleMain
{
    public static void main( String[] args)
    {
        /**
         * Only loads beans declared within the dong profile
         */
        System.setProperty( "spring.profiles.active","dong" ); //this could be a comma separated list, to bing in > 1 profile
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("profilesContext.xml");

        /**
         * Note that the id profileDependantBean is declared in both profiles,
         */
        U.print( context.getBean( "profileDependantBean" ) );

        /**
         * Only loads beans declared within the dong profile
         */
        System.setProperty( "spring.profiles.active","ding" ); //this could be a comma separated list, to bing in > 1 profile
        context = new ClassPathXmlApplicationContext("profilesContext.xml");

        /**
         * Note that the id profileDependantBean is declared in both profiles,
         */
        U.print( context.getBean( "profileDependantBean" ) );

    }
}
