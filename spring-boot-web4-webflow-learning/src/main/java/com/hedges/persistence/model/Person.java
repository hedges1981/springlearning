package com.hedges.persistence.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 08/01/16
 */
@Entity
@Table(name="PERSON")
public class Person
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column(name="NAME")
    private String name;

    @Column(name="AGE")
    private Integer age;

    @Override
    public String toString()
    {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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

    public Integer getAge()
    {
        return age;
    }

    public void setAge( Integer age )
    {
        this.age = age;
    }

}
