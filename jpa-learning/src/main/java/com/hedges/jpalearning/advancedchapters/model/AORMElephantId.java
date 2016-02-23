package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by rowland-hall on 12/02/16
 */
@Embeddable
public class AORMElephantId implements Serializable
{
    private String firstName;
    private String lastName;

    public AORMElephantId( String firstName, String lastName )
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AORMElephantId(){}

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
