package com.hedges.jmslearning.xmlbased;

/**
 * Created by rowland-hall on 09/06/15
 */
public class NotAnnotatedMessageDrivenPOJO
{
    public String handleMessage( String message )
    {
        return "Message received by:"+this.getClass();
    }
}
