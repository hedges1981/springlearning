package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 14/10/15
 */
@Entity
@Table( name="parking_space" )
public class ParkingSpace
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private String location;

    @OneToOne( mappedBy = "parkingSpace")//tells it that the employee object has the join column, not this one,
    //and points it at the Employee.parkingSpace column to work out the mapping.
    private Employee employee;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
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
