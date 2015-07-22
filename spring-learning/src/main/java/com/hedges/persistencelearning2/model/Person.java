package com.hedges.persistencelearning2.model;

import java.util.Date;

/**
 * Created by rowland-hall on 22/07/15
 */
public class Person
{
    private int id;

    private String name;

    private String sex;

    private Date dateOfBirth;

    private String carMake;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex( String sex )
    {
        this.sex = sex;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCarMake()
    {
        return carMake;
    }

    public void setCarMake( String carMake )
    {
        this.carMake = carMake;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", carMake='" + carMake + '\'' +
                '}';
    }
}
