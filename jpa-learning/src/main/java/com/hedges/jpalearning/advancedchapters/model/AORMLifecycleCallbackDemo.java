package com.hedges.jpalearning.advancedchapters.model;

import com.hedges.jpalearning.U;
import com.hedges.jpalearning.advancedchapters.listeners.AORMLifeCycleCallBackDemoEntityListener1;
import com.hedges.jpalearning.advancedchapters.listeners.GeneralEntityListener;

import javax.persistence.*;

/**
 * Created by rowland-hall on 23/02/16
 */
@Entity
@EntityListeners({AORMLifeCycleCallBackDemoEntityListener1.class, GeneralEntityListener.class })
//NOTE: how the entity listeners get called in the order they are declared.
@Table( name = "lifecycle_callback_demo")
public class AORMLifecycleCallbackDemo
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    public String string;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getString()
    {
        return string;
    }

    public void setString( String string )
    {
        this.string = string;
    }

    //*************LIFECYCLE CALLBACK METHODS********************
    //NOTE the rules on the signatures of these methods, see book page:328.

    @PrePersist
    public void prePersistCallback()
    {
        U.print(this+" in pre persist");
    }


    //NOTE: you can only annotate one method on an entity with a given callback annotation, if there is > 1, that application startup fails.
//    @PrePersist
//    public void prePersistCallback2()
//    {
//        U.print(this+" in pre persist call back 2");
//    }

    @PostPersist
    public void postPersistCallback()
    {
        U.print(this+" in post persist");
    }

    @PreUpdate
    public void preUpdateCallback()
    {
        U.print(this+" in pre update");
    }

    @PostUpdate
    public void postUpdateCallback()
    {
        U.print(this+" in post update");
    }

    @PreRemove
    public void preRemoveCallback()
    {
        U.print(this+" in pre remove");
    }

    @PostRemove
    public void postRemoveCallback()
    {
        U.print(this+" in post remove");
    }

    @PostLoad
    public void postLoadCallback()
    {
        U.print(this+" in post load");
    }
}
