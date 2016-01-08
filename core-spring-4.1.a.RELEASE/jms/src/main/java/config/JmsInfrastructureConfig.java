package config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
@EnableJms
//NOTE how here we use @EnableJms to get it to do JMS stuff
public class JmsInfrastructureConfig {

	//	DONE-01: Define an ActiveMQConnectionFactory.
	//	Use brokerURL "vm://embedded?broker.persistent=false"
    @Bean
    public ConnectionFactory connectionFactory()
    {
        //NOTE: here the ConnectionFactory interface is used.
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( "vm://embedded?broker.persistent=false" );
        return connectionFactory;
    }

	//	DONE-02: Create two ActiveMQQueue beans, one for dining and one for confirmations.
	//	Use constructor injection to provide a unique name for each queue, use any names you like. 
	//	Remember the queue names you select, you will need them later. -->
    @Bean
    public Queue diningQueue()
    {
        return new ActiveMQQueue( "diningQueue" );
    }

    @Bean
    public Queue confirmationsQueue()
    {
        return new ActiveMQQueue( "confirmationsQueue" );
    }
	
	//	DONE-08: Create a JMS Listener Container Factory bean to support the @JmsListener annotations.
	//	The DefaultJmsListenerContainerFactory is a good class to instantiate for this.
	//	Inject its connection factory to the ActiveMQConnectionFactory bean you defined in an earlier step.


    @Bean
    //NOTE: this bean creates a listener container that houses all of e.g. the listeners caused by the @JmsListener methods,
    //and routes the messages to them.
    public JmsListenerContainerFactory jmsListenerContainerFactory()
    {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory  = new  DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory( connectionFactory() );

        return jmsListenerContainerFactory;
    }

	//	DONE-09: Mark this class with the annotation needed to enable asynchronous JMS processing.
}
