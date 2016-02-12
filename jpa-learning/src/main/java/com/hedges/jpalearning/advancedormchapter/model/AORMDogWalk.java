package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rowland-hall on 12/02/16
 */
@Entity
@Table( name="dog_walk")
public class AORMDogWalk
{
    @EmbeddedId
    private AORMDogWalkId dogWalkId;

    @MapsId("dogId")
    //NOTE: with this stuff we are telling it how to map the AORMDogId object up that is housed inside AORMDogWalkId.
    @ManyToOne
    @JoinColumns({
                         @JoinColumn(name="dog_firstName", referencedColumnName="firstName"),
                         @JoinColumn(name="dog_secondName", referencedColumnName="lastName")})
    private AORMDog dog;

    private Date date;

    private String notes;

    public AORMDogWalkId getDogWalkId()
    {
        return dogWalkId;
    }

    public void setDogWalkId( AORMDogWalkId dogWalkId )
    {
        this.dogWalkId = dogWalkId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes( String notes )
    {
        this.notes = notes;
    }

    public AORMDog getDog()
    {
        return dog;
    }

    public void setDog( AORMDog dog )
    {
        this.dog = dog;
    }
}
