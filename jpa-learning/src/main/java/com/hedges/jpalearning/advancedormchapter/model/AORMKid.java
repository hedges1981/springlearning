package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="kid")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="kidType")//NOTE: discriminator column use to decide what concrete class to make for a row, see @DiscriminatorValue on the child classes
public abstract class AORMKid
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private String name;

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
}
