package com.hedges.jmslearning.xmlbased;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 09/04/15
 */
public class XMLBasedJMSMain
{
    public static void main( String [] args ) throws Exception
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jmslearningXmlBasedContext.xml");

        JmsMessageProducerAndReceiver jmsMessageProducer = ( JmsMessageProducerAndReceiver ) context.getBean( "jmsMessageProducerAndReceiver" );

        jmsMessageProducer.generateMessages();

        jmsMessageProducer.publishSomeMessagesForHedgesMessageListener();

        jmsMessageProducer.publishSomeMessagesForVariousDifferentMessageListeners();

        jmsMessageProducer.receiveSomeJmsMessages();
    }
}
