package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 15/10/15
 */
@Entity
@Table( name="phone")
public class Phone
{
    public enum PhoneType
    {
        work,home,mobile;
    }

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private int id;

    @Enumerated( EnumType.STRING )
    private PhoneType phoneType;

    private String number;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public PhoneType getPhoneType()
    {
        return phoneType;
    }

    public void setPhoneType( PhoneType phoneType )
    {
        this.phoneType = phoneType;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }
}
