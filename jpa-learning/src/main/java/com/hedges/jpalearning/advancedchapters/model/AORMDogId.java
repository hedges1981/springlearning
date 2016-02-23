package com.hedges.jpalearning.advancedchapters.model;

import java.io.Serializable;

/**
 * Created by rowland-hall on 12/02/16
 */
//NOTE: MUST implement serializable to be an ID class
public class AORMDogId implements Serializable
{
    private String firstName;

    private String lastName;

    public AORMDogId( String firstName, String lastName )
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //NOTE: default constructor needed else Hibernate complains.
    public AORMDogId(){}

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

    @Override
    //NOTE: OBVIOUSLY having an equals method is absolutely necessary for it to be used as an ID class.
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof AORMDogId ) )
        {
            return false;
        }

        AORMDogId aormDogId = ( AORMDogId ) o;

        if ( firstName != null ? !firstName.equals( aormDogId.firstName ) : aormDogId.firstName != null )
        {
            return false;
        }
        if ( lastName != null ? !lastName.equals( aormDogId.lastName ) : aormDogId.lastName != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + ( lastName != null ? lastName.hashCode() : 0 );
        return result;
    }
}
