package com.hedges.jmslearning.xmlbased;

import com.hedges.springlearning.U;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by rowland-hall on 08/06/15
 */
@Component
public class AnnotatedMessageDrivenPOJO
{
    /*NOTE, this also takes a "selector" parameter, to use a message selector sql style thing, like you can with
    @MessageDriven */
    @JmsListener(destination="annotatedMessageListenerQueue")
    /* Note, you can specify
    /* NOTE, due to the string parameter, a TextMessage must be sent to the queue,
     if it was a Map, would need to be a Map message, etc.     */
    public void processStringMessage( String message )
    {
        U.print( "String Message received: "+message);
    }

    /*
    Note that a MapMessage sent to the same queue is routed here, a String message routed above.
    It is quite smart when picking the method to route the message to!-->
     */
    @JmsListener(destination="annotatedMessageListenerQueue")
    public void processMapMessage( Map mapMessage )
    {
        U.print( "Map Message received: "+ mapMessage);
    }



}
