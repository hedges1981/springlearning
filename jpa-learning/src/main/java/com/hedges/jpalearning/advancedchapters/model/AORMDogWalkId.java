package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * Created by rowland-hall on 12/02/16
 */
@Embeddable
public class AORMDogWalkId implements Serializable
{
    @Embedded
    private AORMDogId dogId;

    private int walkNumber;

    public AORMDogId getDogId()
    {
        return dogId;
    }

    public void setDogId( AORMDogId dogId )
    {
        this.dogId = dogId;
    }

    public int getWalkNumber()
    {
        return walkNumber;
    }

    public void setWalkNumber( int walkNumber )
    {
        this.walkNumber = walkNumber;
    }
}
