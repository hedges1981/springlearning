package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;
import java.util.List;

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

    /**
     * NOTE; how in this example, the contact info object is used to house both an embedded address and the phones,
     * relates to example in book, p.275.
     */
    @Embedded
    private AORMContactInfo contactInfo;

    @ManyToOne
    @JoinColumn(name="manager_id", referencedColumnName = "id")
    //note how in the db, the manager_id column references the id of the employee who is the manager:
    private AORMEmployee manager;

    @OneToMany(mappedBy = "manager")
    private List<AORMEmployee> minions;

    public AORMContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public void setContactInfo( AORMContactInfo contactInfo )
    {
        this.contactInfo = contactInfo;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public AORMEmployee getManager()
    {
        return manager;
    }

    public void setManager( AORMEmployee manager )
    {
        this.manager = manager;
    }

    public List<AORMEmployee> getMinions()
    {
        return minions;
    }

    public void setMinions( List<AORMEmployee> minions )
    {
        this.minions = minions;
    }
}
