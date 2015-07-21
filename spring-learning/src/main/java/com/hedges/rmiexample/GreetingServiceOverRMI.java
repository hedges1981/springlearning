package com.hedges.rmiexample;

/**
 * Created by rowland-hall on 21/07/15
 */
public class GreetingServiceOverRMI implements GreetingService
{
    @Override
    public String getGreeting( String name )
    {
        return "Greetings "+ name+" (note this has been called over RMI)";
    }
}
