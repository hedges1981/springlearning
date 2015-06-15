package com.hedges.jmslearning.xmlbased;

import com.hedges.springlearning.U;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by rowland-hall on 08/06/15
 */
public class MessageListenerImpl implements MessageListener
{
    @Override
    public void onMessage( Message message )
    {
        try
        {
            String text = ((TextMessage )message).getText();
            U.print(text);
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
}
