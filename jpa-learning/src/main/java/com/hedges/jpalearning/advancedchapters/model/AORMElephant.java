package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 12/02/16
 */
@Entity
@Table( name="elephant")
public class AORMElephant
{
    @EmbeddedId
    private AORMElephantId elephantId;

    public AORMElephantId getElephantId()
    {
        return elephantId;
    }

    public void setElephantId( AORMElephantId elephantId )
    {
        this.elephantId = elephantId;
    }

    private int weight;

    public int getWeight()
    {
        return weight;
    }

    public void setWeight( int weight )
    {
        this.weight = weight;
    }
}

