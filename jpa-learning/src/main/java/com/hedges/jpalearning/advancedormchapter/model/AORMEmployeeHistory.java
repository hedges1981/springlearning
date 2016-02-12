package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 12/02/16
 */
@Entity
@Table(name="emp_history")
public class AORMEmployeeHistory
{
    @Id
    int empId;

    @MapsId//tells it that the pk of this object is the same as the employee object.
    //NOTE: there is another example in the book where it doesn't use @MapsId. That seemed to require the AORMEMployeeObject to be serializable, this way did not. better?
    @OneToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    //NOTE: this lot basically tells it that the pk of this object is the pk of this employee object.
    private AORMEmployee employee;

    private String history;

    public AORMEmployee getEmployee()
    {
        return employee;
    }

    public void setEmployee( AORMEmployee employee )
    {
        this.employee = employee;
    }

    public String getHistory()
    {
        return history;
    }

    public void setHistory( String history )
    {
        this.history = history;
    }

    public int getEmpId()
    {
        return empId;
    }

    public void setEmpId( int empId )
    {
        this.empId = empId;
    }
}
