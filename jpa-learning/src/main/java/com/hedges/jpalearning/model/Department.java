package com.hedges.jpalearning.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    @OrderBy("dogName,phone desc") //note the order by, when it fetches the employees, they are ordered as specified.
    //note that it uses the db column name, not the field/property name on the Employee entity.
    //note how List is used, should be as it is an ordered collection.
    private List<Employee> employees;

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

    public void setEmployees( List<Employee> employees )
    {
        this.employees = employees;
    }
}
