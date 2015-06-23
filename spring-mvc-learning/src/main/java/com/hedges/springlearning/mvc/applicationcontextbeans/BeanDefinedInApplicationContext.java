package com.hedges.springlearning.mvc.applicationcontextbeans;

/**
 * Created by rowland-hall on 23/06/15
 */
public class BeanDefinedInApplicationContext
{
    String message;

    public void setMessage( String message )
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
