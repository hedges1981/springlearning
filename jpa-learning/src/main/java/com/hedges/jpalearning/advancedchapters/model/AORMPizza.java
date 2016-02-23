package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="pizza")
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
