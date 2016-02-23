package com.hedges.jpalearning.advancedchapters.model;


import javax.persistence.*;

/**
 * Created by rowland-hall on 17/02/16
 */
@Entity
@Table( name="emp_child1")
public class AORMEmpChild1
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @ManyToOne
    @JoinColumn( name = "emp_id", referencedColumnName = "id")
    private AORMEmployee employee;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public AORMEmployee getEmployee()
    {
        return employee;
    }

    public void setEmployee( AORMEmployee employee )
    {
        this.employee = employee;
    }
}
