package com.hedges.rmiexample;

import com.hedges.springlearning.U;
import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Starts a RMI server in one thread, then starts a client and makes calls to it.
 *
 * Note that this is a very artificial example, in reality the RMI client and server would be in seperate machines / JVMs.
 */
public class RMIExampleMain
{

    public static void main( String[] args ) throws InterruptedException
    {
        //gives us some logging output from the spring classes:
        BasicConfigurator.configure();

        startRMIServer();
        //wait a bit for it to have started:
        Thread.currentThread().sleep(10000);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rmiExampleClientContext.xml");

        //this gives us a proxy bean for the GreetingService over RMI.
        GreetingService greetingServiceOverRMI = (GreetingService) context.getBean( "greetingServiceOverRMI" );

        U.print("Calling the greeting service over RMI, greeting returned is:");
        U.print( greetingServiceOverRMI.getGreeting( "Hedges" ) );

        System.exit(0);
    }

    private static void startRMIServer()
    {
        //starts the RMI server in a new thread:
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                //by loading this context file, we deploy the RMI 'server' defined in it:
                new ClassPathXmlApplicationContext("rmiExampleServerContext.xml");
                U.print( "RMI Server started, thread is:"+Thread.currentThread().getId() );
            }
        };

        new Thread( runnable ).start();
    }
}
