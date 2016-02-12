package com.hedges.jpalearning.advancedormchapter.model;



import javax.persistence.*;

/**
 * Created by rowland-hall on 12/02/16
 */
@Entity
@Table( name = "customer"  )
@Access( AccessType.FIELD )
public class AORMCustomer
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Embedded
    //NOTE: here we are telling the embedded contactInfo object to use the customer_phone table to fetch up the phones instead of the default emp_phone
    @AssociationOverride( name="phones", joinTable = @JoinTable( name="customer_phone", joinColumns = @JoinColumn(name="customer_id"), inverseJoinColumns = @JoinColumn(name="phone_id") ) )
    //NOTE: how here the postcode column on the customer table is called post_code, so we need to override the default "postcode" one that is on the ContactInfo object.
    @AttributeOverride( name="residence.zipCode", column=@Column( name="post_code"))
    private AORMContactInfo contactInfo;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public AORMContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public void setContactInfo( AORMContactInfo contactInfo )
    {
        this.contactInfo = contactInfo;
    }
}
