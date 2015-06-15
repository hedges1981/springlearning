package com.hedges.jmslearning.xmlbased;

import com.hedges.springlearning.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.*;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by rowland-hall on 09/04/15
 */
@Component
public class JmsMessageProducerAndReceiver
{
    protected static final String MESSAGE_COUNT = "messageCount";

    @Autowired
    @Qualifier( "dynamicDestinationJMSTemplate" )
    private JmsTemplate dynamicDestinationJMSTemplate;

    @Autowired
    @Qualifier("mustBeBeanDestinationJMSTemplate")
    private JmsTemplate mustBeBeanDestinationJMSTemplate;

    private int messageCount = 100;

    /**
     * Generates JMS messages
     */
    public void generateMessages() throws JMSException
    {
        U.print( dynamicDestinationJMSTemplate + "sending messages to default destination of:" + dynamicDestinationJMSTemplate.getDefaultDestination() );
        for ( int i = 0; i < messageCount; i++ )
        {
            final int index = i;
            final String text = "Message number is " + i + ".";


            /**
             * this will send it to the default-destination as specified in the XML file.
             */
            dynamicDestinationJMSTemplate.send( new MessageCreator()
            {
                public Message createMessage( Session session ) throws JMSException
                {
                    TextMessage message = session.createTextMessage( text );
                    message.setIntProperty( MESSAGE_COUNT, index );

                    U.print( "Sending message: " + text );

                    return ( Message ) message;
                }
            } );
        }

        /**
         * Can also send it to a different destination as specified:
         */
        U.print( dynamicDestinationJMSTemplate +"sending message to different destination of: destinationTopic");

        /*
        Doesn't matter what the destination name is here as the JMSTemplates standard DestinationResolver is a DynamicDestinationResolver·
        This will create the destination if it doesn't already exist.
        Note that if jmsTemplate.isPubSubDomain() returns true, then a topic will be created, esle it will create a queue.
        Default is for it to create a queue.
         */
        dynamicDestinationJMSTemplate.send( "anyOldRubbish",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {
                                                    String text = "message sent by template one to destinationTopic";
                                                    TextMessage message = session.createTextMessage( text );

                                                    U.print( "Sending message: " + text );

                                                    return ( Message ) message;
                                                }
                                            }
        );

        try
        {
            mustBeBeanDestinationJMSTemplate.send( "anyOldRubbish",
            new MessageCreator()
            {
                public Message createMessage( Session session ) throws JMSException
                {
                    String text = "message";
                    TextMessage message = session.createTextMessage( text );
                    U.print( "Sending message: " + text );
                    return ( Message ) message;
                }
            }
            );
        }
        catch(Exception e)
        {
            U.print("Exception thrown here, as destination not a bean");
        }


        /**
         * This will send it ok, as "anotherQueue" is defined in the xml file that is referenced by the DestinationResolver attached to the jms template:
         */
        mustBeBeanDestinationJMSTemplate.send( "anotherQueue",
           new MessageCreator()
           {
               public Message createMessage( Session session ) throws JMSException
               {
                   String text = "message";
                   TextMessage message = session.createTextMessage( text );
                   U.print( "Sending message: " + text );
                   return ( Message ) message;
               }
           }
        );

        /**
         * use of JmsTemplate.convertAndSend(......)
         */
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor()
        {
            @Override
            public Message postProcessMessage( Message message ) throws JMSException
            {
                message.setIntProperty( "someIntProperty",999 );
                return message;
            }
        };
        //Note that in this case the "messageText" String is automatically converted to a TextMessage, messagePostProcessor
        //is applied at the end, after it has been converted and just before it is sent.
        mustBeBeanDestinationJMSTemplate.convertAndSend( "anotherQueue", "messageText", messagePostProcessor );

        //Note other "auto-conversion" methods to make life easier, e.g., which sends it to the default destination of the
        //JmsTemplate, see where this is set up in the Spring config file that we are working with.
        dynamicDestinationJMSTemplate.convertAndSend( "someTextThatWill be auto converted to a text message" );

        /**
         * use of JmsTemplate.execute(...), WITH ProducerCallback
         * note how it allows you to intercept the creation of the message itself and faff about with it
         */
        ProducerCallback<String> producerCallback = new ProducerCallback<String>()
        {
            @Override
            public String doInJms( Session session, MessageProducer producer ) throws JMSException
            {
               // Message message = getSomeKindOfMessage()
               // producer.send( producer.getDestination(), message );

                return "no message sent as big faff to send one this way";
            }
        };

        String string = dynamicDestinationJMSTemplate.execute( "anotherQueue", producerCallback  );

        /**
         * Use of JmsTemplate.execute(...) with  SessionCallback
         */

        SessionCallback<String> sessionCallback = new SessionCallback<String>()
        {
            @Override
            public String doInJms( Session session ) throws JMSException
            {
                //allows you to work directly with the session here
                //e.g.
                Message m = session.createTextMessage();
                Queue queue = session.createQueue( "aQueue" );
                MessageProducer mp = session.createProducer( queue );
                m.setJMSDestination( queue );
                mp.send( queue, m );
                return "message sent to made up queue";
                //in real life you would do more complicated stuff.
            }
        };

        string = dynamicDestinationJMSTemplate.execute( sessionCallback ) ;
    }

    public void receiveSomeJmsMessages()
    {
        //note that the receive method will block, as the receiving is synchronous.
        Message message = dynamicDestinationJMSTemplate.receive();
        U.print( "message received on default destination: "+dynamicDestinationJMSTemplate.getDefaultDestination()+" ="+ message );

        //can specify the destination it receives on:
        message = mustBeBeanDestinationJMSTemplate.receive("anotherQueue");


        /**
         * Use of a MessageConverter to convert the message when you receive it:
         */
        MessageConverter messageConverter = new MessageConverter()
        {
            @Override
            public Message toMessage( Object object, Session session ) throws JMSException, MessageConversionException
            {
                //in here you could override the default message converter the JmsTemplate comes supplied with, to e.g. convert a String to the right
                //kind of message.
                return null;
            }

            @Override
            public Object fromMessage( Message message ) throws JMSException, MessageConversionException
            {
                return "Message:" +message+"converted to String:";
            }
        };

        dynamicDestinationJMSTemplate.setMessageConverter( messageConverter );

        U.print( dynamicDestinationJMSTemplate.receiveAndConvert( ) );//could also specify a destination name here:
    }

    public void publishSomeMessagesForHedgesMessageListener()
    {
        dynamicDestinationJMSTemplate.send( "hedgesMessageListenerQueue",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {
                                                    String text = "message sent to messageListenerQueue";
                                                    TextMessage message = session.createTextMessage( text );

                                                    U.print( "Sending message: " + text );

                                                    return ( Message ) message;
                                                }
                                            }
        );

    }

    public void publishSomeMessagesForVariousDifferentMessageListeners()
    {
        //Text message, gets picked up by method with String parameter on the @JmsListener
        dynamicDestinationJMSTemplate.send( "annotatedMessageListenerQueue",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {
                                                    String text = "message sent to annotatedMessageListenerQueue";
                                                    TextMessage message = session.createTextMessage( text );

                                                    U.print( "Sending message: " + text );

                                                    return ( Message ) message;
                                                }
                                            }
        );

        //Text message, gets picked up by method with String parameter on the @JmsListener
        dynamicDestinationJMSTemplate.send( "annotatedMessageListenerQueue",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {

                                                    MapMessage message = session.createMapMessage();

                                                    message.setString("MapKey","MapValue");

                                                    U.print( "Sending message: " + message );

                                                    return ( Message ) message;
                                                }
                                            }
        );

        dynamicDestinationJMSTemplate.send( "notAnnotatedMessageDrivenPOJOQueue",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {
                                                    TextMessage message = session.createTextMessage( "Message sent to notAnnotatedMessageDrivenPOJOQueue" );
                                                    return ( Message ) message;
                                                }
                                            }
        );

        dynamicDestinationJMSTemplate.send( "MessageListenerAdapterQueue",
                                            new MessageCreator()
                                            {
                                                public Message createMessage( Session session ) throws JMSException
                                                {
                                                    TextMessage message = session.createTextMessage( "Message sent to MessageListenerAdapterQueue" );
                                                    return ( Message ) message;
                                                }
                                            }
        );



    }
}