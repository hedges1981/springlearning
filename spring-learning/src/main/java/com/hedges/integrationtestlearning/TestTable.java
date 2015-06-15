package com.hedges.integrationtestlearning;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 29/01/15
 */
@Entity
@Table(name="test_table")
public class TestTable
{
    @Id
    private int id;

    private String name;

    private String value;

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

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }
}
