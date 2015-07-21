package com.hedges.springlearning.mvc.httpinvokerexample;

/**
 * Created by rowland-hall on 21/07/15
 */
public class GreetingServiceImpl implements GreetingService
{
    @Override
    public String getGreeting( String name )
    {
        return "Greetings "+name;
    }
}
