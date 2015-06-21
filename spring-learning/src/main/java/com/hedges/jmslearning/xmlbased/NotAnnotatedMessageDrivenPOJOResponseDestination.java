package com.hedges.jmslearning.xmlbased;

import com.hedges.springlearning.U;

/**
 * Created by rowland-hall on 09/06/15
 */
public class NotAnnotatedMessageDrivenPOJOResponseDestination
{
    public void handleMessage( String message )
    {
        U.print(this.getClass()+" received response message");
    }
}
