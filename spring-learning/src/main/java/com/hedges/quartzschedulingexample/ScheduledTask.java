package com.hedges.quartzschedulingexample;

import com.hedges.springlearning.U;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by rowland-hall on 12/05/15
 */
public class ScheduledTask extends QuartzJobBean
{
    @Override
    protected void executeInternal( JobExecutionContext jobExecutionContext ) throws JobExecutionException
    {
       U.print( "Executing scheduled task at time:"+ new Date() );
    }

    /**
     * this allows you to get hold of the ApplicationContext, notice the use of key "applicationContextKey"
     * in the quartzSchedulingExampleContext.xml
     */
    private ApplicationContext getApplicationContext( JobExecutionContext context ) throws SchedulerException
    {

        ApplicationContext appCtx = ( ApplicationContext ) context.getScheduler().getContext().get( "applicationContextKey" );

        if ( appCtx == null )
        {
            throw new JobExecutionException( "No application context available in scheduler context for key \"" + "applicationContextKey" + "\"" );
        }
        return appCtx;
    }
}
