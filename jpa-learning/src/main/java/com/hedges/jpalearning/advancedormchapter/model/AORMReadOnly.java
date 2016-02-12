package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 12/02/16
 *
 * NOTE on here the use of insertable and updatable = false, to stop i) new ones being created and ii) existing ones being updated.
 */
@Entity
@Table(name="read_only")
public class AORMReadOnly
{
    @Id
    @Column(insertable = false, updatable = false)
    private int id;
    @Column(insertable = false, updatable = false)
    private String aString;

    @ManyToOne
    @JoinColumn(name="emp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AORMEmployee employee;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getaString()
    {
        return aString;
    }

    public void setaString( String aString )
    {
        this.aString = aString;
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
