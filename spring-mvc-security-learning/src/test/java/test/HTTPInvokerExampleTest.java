package test;

import com.hedges.springlearning.mvc.httpinvokerexample.GreetingService;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 21/07/15
 */
public class HTTPInvokerExampleTest
{
    /**
     * NOTE, to get this to work, have: <sec:csrf disabled="true"/>, the default csrf checking of Spring security messes the call up!-->
     *
     *     NOTE, this currently doesn't work due to some strange object serialisation issue.....
     */
    @Test
    public void demoHttpInvokerExample()
    {
        BasicConfigurator.configure();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "httpInvokerClientContext.xml" );

        GreetingService greetingService = context.getBean( GreetingService.class );

        System.out.println("Got proxy for HTTP service: "+ greetingService );

        System.out.println("Greeting is: "+ greetingService.getGreeting( "Hedges" ) );


    }
}
