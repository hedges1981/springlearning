package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 15/02/16
 */
@Entity
@Table(name="dog_bed")
public class AORMDogBed
{
    @Id
    private int id;

    @ManyToOne //NOTE that there are two join columns here that allow you to get to the dog
    @JoinColumns( {@JoinColumn(name = "dog_firstName", referencedColumnName = "firstName"),
                          @JoinColumn(name = "dog_lastName", referencedColumnName = "lastName") } )
    private AORMDog dog;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public AORMDog getDog()
    {
        return dog;
    }

    public void setDog( AORMDog dog )
    {
        this.dog = dog;
    }
}
