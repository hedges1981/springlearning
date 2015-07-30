package com.hedges.jmxexample;

/**
 * Created by rowland-hall on 30/07/15
 */
public interface AnnotatedMBean
{
    String getStatus();

    void setStatus( String status );


    void doSomething( String a, String b );
}
