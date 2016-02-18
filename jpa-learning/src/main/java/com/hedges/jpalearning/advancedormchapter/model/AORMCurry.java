package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="curry")
@AttributeOverride( name="countryOfOrigin" , column = @Column( name="origin_country" ))
public class AORMCurry extends AORMFood
{
    private int hotnessLevel;

    public int getHotnessLevel()
    {
        return hotnessLevel;
    }

    public void setHotnessLevel( int hotnessLevel )
    {
        this.hotnessLevel = hotnessLevel;
    }
}