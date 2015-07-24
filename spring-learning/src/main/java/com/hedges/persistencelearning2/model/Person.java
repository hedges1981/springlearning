package com.hedges.persistencelearning2.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rowland-hall on 22/07/15
 */
@Entity
@Table( name="person")
public class Person
{
    private int id;

    private String name;

    private String sex;

    private Date dateOfBirth;

    private String carMake;


    @Id
    //NOTE: this generation strategy set up means that it takes advantage of the mysql auto increment. When a new
    //Person is created, the generated ID from the mysql AI system is automatically set on the object persisted.
    @GeneratedValue( strategy =  GenerationType.AUTO )
    @Column
    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    @Column
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Column
    public String getSex()
    {
        return sex;
    }

    public void setSex( String sex )
    {
        this.sex = sex;
    }

    @Column( name="dob")
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name="car_make")
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
