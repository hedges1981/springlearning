package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class AORMFood
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    //NOTE: how the countryOfOrigin colum on the concrete pizza and curry tables has different column names, and @AttributeOverride is used
    //to sort it out.
    private String countryOfOrigin;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getCountryOfOrigin()
    {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin( String countryOfOrigin )
    {
        this.countryOfOrigin = countryOfOrigin;
    }
}
