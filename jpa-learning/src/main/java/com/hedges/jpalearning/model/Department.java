package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 13/10/15
 */
@Entity
@Table(name="department")
public class Department
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private String name;

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
}
