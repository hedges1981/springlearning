package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table(name="orange")
@DiscriminatorValue( "orange" )
public class AORMOrange  extends AORMColouredFruit
{
    @Column( name="orange_type")
    private String orangeType;

    public String getOrangeType()
    {
        return orangeType;
    }

    public void setOrangeType( String orangeType )
    {
        this.orangeType = orangeType;
    }
}
