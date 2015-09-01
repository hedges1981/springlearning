package com.hedges.jmxexample;

import com.hedges.springlearning.U;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.*;
import java.io.IOException;

/**
 * Created by rowland-hall on 31/07/15
 */
public class JMXExampleMain
{
    /**
     * NOTE that this class is a bit shitty, it runs indefinitely, so be sure to kill the process before you run it again.
     */
    public static void main( String[] args ) throws MalformedObjectNameException, IntrospectionException, ReflectionException, InstanceNotFoundException, IOException
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "jmxLearningContext.xml" );

        MBeanServerConnection clientConnector = context.getBean( "clientConnector", MBeanServerConnection.class );

        MBeanInfo beanInfo = clientConnector.getMBeanInfo(new ObjectName("com.hedges.jmxexample:name=annotatedMBean"));

        /**
         * DEMOING THE USE OF A JMX M BEAN SORTED OUT USING ANNOTATIONS:
         */

        //the beanInfo contains amongst other things the info put into the metadata annotations.
        U.print( "Bean info for: com.hedges.jmxexample:name=AnnotatedMBean: "+ beanInfo );


        //Making calls on the mbean programatically via use of the org.springframework.jmx.access.MBeanProxyFactoryBean
        AnnotatedMBean annotatedMBean = (AnnotatedMBean)context.getBean( "annotatedMBeanProxy" );
       U.print("annotatedMbean status:"+annotatedMBean.getStatus());
       U.print("chaging status to:"+"dodgy");
       annotatedMBean.setStatus( "dodgy" );
       U.print("annotatedMbean status:"+annotatedMBean.getStatus());
       annotatedMBean.doSomething( "a","b" );

        /**
         * DEMOING THE USE OF A JMX M BEAN SORTED OUT USING A NON ANNOTATED POJO:
         */
        POJOMBean pojomBeanProxy = (POJOMBean)context.getBean( "pojoMBeanProxy" );
        pojomBeanProxy.doSomething();
        
        //****************
        clientConnector.getMBeanCount();
     

        System.exit( 1 );
    }
}
