package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 18/02/16
 */
@Entity
@Table( name="fruit")
@Inheritance( strategy =  InheritanceType.JOINED )
@DiscriminatorColumn( name="fruit_type")
public abstract class AORMFruit
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private int weight;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight( int weight )
    {
        this.weight = weight;
    }
}
