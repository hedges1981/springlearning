package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 29/10/15
 */
@Entity
public class CascadeDemo
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn( name = "emp_id" )
    private Employee employee;

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

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee( Employee employee )
    {
        this.employee = employee;
    }
}
