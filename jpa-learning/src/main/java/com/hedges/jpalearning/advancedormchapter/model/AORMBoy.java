package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@DiscriminatorValue( "boy" )//note how via the superclass jpa will use the kidType column to decide what concrete class for a given row.
public class AORMBoy extends AORMKid
{
    private String boySpecificField;

    public String getBoySpecificField()
    {
        return boySpecificField;
    }

    public void setBoySpecificField( String boySpecificField )
    {
        this.boySpecificField = boySpecificField;
    }
}
