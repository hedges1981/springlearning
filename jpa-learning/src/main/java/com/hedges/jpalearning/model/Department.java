package com.hedges.jpalearning.model;

import javax.persistence.*;
import java.util.Collection;

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

    @OneToMany( targetEntity = Employee.class, mappedBy ="department", fetch = FetchType.EAGER)
    //allows for the department to fetch its employees, join column is in the emp table, so the targetEntity and
    //mappedBy allow it to resolve the relationship and build up the collection.
    //note the eager fetch explicitly set, default is LAZY for joined Entities
    private Collection<Employee> employees;

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

    public Collection<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees( Collection<Employee> employees )
    {
        this.employees = employees;
    }
}
