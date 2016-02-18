package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rowland-hall on 12/02/16
 */
@Entity
@Table( name="dog")
//NOTE: the use of @IdClass to allow the use of the compound pk.
@IdClass( AORMDogId.class )
public class AORMDog
{
    @Id
    private String firstName;
    @Id
    private String lastName;

    @OneToMany(mappedBy="dog")
    private List<AORMDogBed> dogBeds;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public List<AORMDogBed> getDogBeds()
    {
        return dogBeds;
    }

    public void setDogBeds( List<AORMDogBed> dogBeds )
    {
        this.dogBeds = dogBeds;
    }
}
