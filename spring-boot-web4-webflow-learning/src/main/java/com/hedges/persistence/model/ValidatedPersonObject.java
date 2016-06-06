package com.hedges.persistence.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by rowland-hall on 08/01/16
 */
@Entity
@Table(name="PERSON")
public class ValidatedPersonObject
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column(name="NAME")
    @Size( min=1, max=30)
    @NotNull
    private String name;

    @Column(name="AGE")
    @Range( min =1 , max = 120 )
    private Integer age;

    @Override
    public String toString()
    {
        return "ValidatedPersonObject{" +
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
