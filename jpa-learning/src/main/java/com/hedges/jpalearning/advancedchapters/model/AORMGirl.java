package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@DiscriminatorValue( "girl" )
public class AORMGirl extends AORMKid
{
    private String girlSpecificField;

    public String getGirlSpecificField()
    {
        return girlSpecificField;
    }

    public void setGirlSpecificField( String girlSpecificField )
    {
        this.girlSpecificField = girlSpecificField;
    }
}
