package com.hedges.jmslearning.xmlbased;

import com.hedges.springlearning.U;

/**
 * Created by rowland-hall on 09/06/15
 */
public class MessageListenerAdapterDelegate
{
    public void handleMessage( String message )
    {
        U.print(this+ " handling message:"+message );
    }
}
