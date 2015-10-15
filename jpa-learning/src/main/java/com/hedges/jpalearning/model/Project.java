package com.hedges.jpalearning.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by rowland-hall on 15/10/15
 */
@Entity
@Table(name="project")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    int id;

    String name;

    //the employee class here has been designated as the owner of the relationship, so we point it to the
    //projects field of the employee class, where it wil find the mapping info.
    //Could just as easily have been the other way round with the many to many set up.
    @ManyToMany( targetEntity = Employee.class, mappedBy = "projects", fetch = FetchType.EAGER )
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
