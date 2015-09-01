package com.hedges.jmxexample;

import com.hedges.springlearning.U;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by rowland-hall on 30/07/15
 */
@Component
/**
 * the @ManagedResource tells spring to register an instance with the MBean server.
 */
@ManagedResource()
public class AnnotatedMBeanImpl2
{
    private String status = "ok";

    public AnnotatedMBeanImpl2()
    {
        int x=0;
    }

    /**
     * @ManagedAttribute tells it to expose the method as a jmx attribute
     */
    @ManagedAttribute()
    public String getStatus()
    {
        return status;
    }

    /**
     * Note that when working on a getter or setter you have to use @ManagedAttribute, else @ManagedOperation must be used
     */
    @ManagedAttribute()
    public void setStatus( String status )
    {
        this.status = status;
        U.print("status set to: "+status );
    }

    @ManagedOperation( )
    /**
     *   Note that the @ManagedOperationParameters is just for defining metadata, presumably this can be used in some kind of way
     *   by a client app, e.g.in a GUI.
     */
    @ManagedOperationParameters({
                                        @ManagedOperationParameter(name="a", description= "Test Parameter a."),
                                        @ManagedOperationParameter(name="b", description= "Test Parameter b.")})
    public void doSomething( String a, String b )
    {
        U.print( "Doing something, a="+a+" and b="+b );
    }





}

