package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="pizza")
@AttributeOverride( name="countryOfOrigin" , column = @Column( name="country_of_origin" ))
public class AORMPizza extends AORMFood
{
    private String cheeseType;

    public String getCheeseType()
    {
        return cheeseType;
    }

    public void setCheeseType( String cheeseType )
    {
        this.cheeseType = cheeseType;
    }
}
