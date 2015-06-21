package com.hedges.persistencelearning.jdbc;

/**
 * Created by rowland-hall on 17/02/15
 */
public class TestTable
{
    int id;
    String name;
    String value;

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

    @Override
    public String toString()
    {
        return "TestTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
