package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
}
