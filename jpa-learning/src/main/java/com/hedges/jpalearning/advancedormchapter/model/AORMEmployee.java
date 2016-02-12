package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 11/02/16
 */
@Entity
@Table( name = "emp", schema = "jpalearning" )
@Access( AccessType.FIELD )
public class AORMEmployee
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }


    /**
     * NOTE; how in this example, the contact info object is used to house both an embedded address and the phones,
     * relates to example in book, p.275.
     */
    @Embedded
    private AORMContactInfo contactInfo;

    public AORMContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public void setContactInfo( AORMContactInfo contactInfo )
    {
        this.contactInfo = contactInfo;
    }
}
