package com.hedges.springlearning.mvc.httpinvokerexample;

/**
 * Created by rowland-hall on 21/07/15
 */
public interface GreetingService
{
    static final long serialVersionUID = 1L;

    String getGreeting( String name );
}
