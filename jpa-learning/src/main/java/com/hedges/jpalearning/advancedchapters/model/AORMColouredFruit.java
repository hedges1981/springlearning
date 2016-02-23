package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.MappedSuperclass;

/**
 * Created by rowland-hall on 18/02/16
 */
@MappedSuperclass//NOTE; the @MappedSuperclass means that its fields get mapped to table columns on the child tables, but there is no table relating to this
//class, i.e. it is for mapping purposes, it is not an entity.
public abstract class AORMColouredFruit  extends AORMFruit
{
    private String colour;

    public String getColour()
    {
        return colour;
    }

    public void setColour( String colour )
    {
        this.colour = colour;
    }
}
