package com.hedges.quartzschedulingexample;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 12/05/15
 */
public class QuartzSchedulingExampleMain
{
    public static void main( String[] args )
    {
        /**
         * When it loads the context, the cron scheduling should fire.
         */
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "quartzSchedulingApplicationContext.xml");
    }
}
