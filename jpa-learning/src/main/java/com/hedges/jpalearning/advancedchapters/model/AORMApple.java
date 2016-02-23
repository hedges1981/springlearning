package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table(name="apple")
@DiscriminatorValue( "apple" )
public class AORMApple  extends AORMColouredFruit
{
    @Column( name="apple_type")
    private String appleType;

    public String getAppleType()
    {
        return appleType;
    }

    public void setAppleType( String appleType )
    {
        this.appleType = appleType;
    }
}
