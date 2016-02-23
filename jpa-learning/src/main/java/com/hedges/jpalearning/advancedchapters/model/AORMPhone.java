package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rowland-hall on 15/10/15
 */
@Entity
@Table( name="phone")
public class AORMPhone
{
    public enum PhoneType
    {
        work,home,mobile;
    }

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private int id;

    @Enumerated( EnumType.STRING )
    @Column(name="type")
    private PhoneType phoneType;

    private String number;

    @ManyToMany(mappedBy="contactInfo.phones")
    List<AORMEmployee> employees;

    public List<AORMEmployee> getEmployees()
    {
        return employees;
    }

    public void setEmployees( List<AORMEmployee> employees )
    {
        this.employees = employees;
    }

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
