package com.hedges.jpalearning.advancedormchapter.listeners;

import com.hedges.jpalearning.U;
import com.hedges.jpalearning.advancedormchapter.model.AORMLifecycleCallbackDemo;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

/**
 * Created by rowland-hall on 23/02/16
 *
 * NOTE: just uses @PrePersist and @PostPersist, but could also feature the other annotations also.
 */
public class AORMLifeCycleCallBackDemoEntityListener1
{
    @PrePersist //NOTE: how the method can take a  AORMLifecycleCallbackDemo, it can actually take any class (e.g. Object) that
    //is assignable to the entity that it is listening.
    public void validateString( AORMLifecycleCallbackDemo lifeCycleCallBackDemo)
    {
        U.print( "in pre persist of AORMLifeCycleCallBackDemoEntityListener1");

        if( lifeCycleCallBackDemo.getString().equals("invalidString"))
        {
            throw new IllegalArgumentException( "invalid string" );
        }
    }

    @PostPersist
    public void logPersisted( Object obj )
    {
        U.print( obj + " has been persisted" );
    }

    // NOTE: you can only annotate one method on an entity listener with a given callback annotation, if there is > 1, that application startup fails.
//    @PostPersist
//    public void logPersisted2( Object obj )
//    {
//        U.print( obj + " has been persisted 2" );
//    }
}
