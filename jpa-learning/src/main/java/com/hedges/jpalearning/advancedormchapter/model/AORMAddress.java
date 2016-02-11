package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.Embeddable;

/**
 * Created by rowland-hall on 16/10/15
 */
@Embeddable
public class AORMAddress
{
    private String street;
    private String city;
    private String state;
    private String zipCode;

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState( String state )
    {
        this.state = state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode( String zipCode )
    {
        this.zipCode = zipCode;
    }

    @Override
    public String toString()
    {
        return "AORMAddress{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
