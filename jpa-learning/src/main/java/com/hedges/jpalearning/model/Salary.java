package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 19/11/15
 */
@Entity
@Table(name= "salary")
public class Salary
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column(name="job_type")
    private String jobType;

    private int salary;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getJobType()
    {
        return jobType;
    }

    public void setJobType( String jobType )
    {
        this.jobType = jobType;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary( int salary )
    {
        this.salary = salary;
    }
}
