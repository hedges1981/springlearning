package com.hedges.jpalearning.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by rowland-hall on 27/10/15
 */
@Embeddable
public class EmployeeName
{
    @Column( name="firstName", insertable = false, updatable = false )
    private String firstName;
    @Column( name="lastName", insertable = false, updatable = false )
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
