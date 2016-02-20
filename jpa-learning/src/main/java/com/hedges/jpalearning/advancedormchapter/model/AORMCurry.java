package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="curry")
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