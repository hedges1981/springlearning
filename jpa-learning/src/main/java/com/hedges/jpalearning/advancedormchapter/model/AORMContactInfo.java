package com.hedges.jpalearning.advancedormchapter.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by rowland-hall on 11/02/16
 */
@Embeddable
@Access( AccessType.FIELD)
public class AORMContactInfo
{
    @Embedded
    @AttributeOverrides( {@AttributeOverride( name="state", column=@Column(name="county")), @AttributeOverride( name="zipCode", column=@Column(name="postcode") )} )
    private AORMAddress residence;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="emp_phone", joinColumns = @JoinColumn(name="emp_id"), inverseJoinColumns = @JoinColumn(name="phone_id") )

    private Collection<AORMPhone> phones;

    public AORMAddress getResidence()
    {
        return residence;
    }

    public void setResidence( AORMAddress residence )
    {
        this.residence = residence;
    }

    public Collection<AORMPhone> getPhones()
    {
        return phones;
    }

    public void setPhones( Collection<AORMPhone> phones )
    {
        this.phones = phones;
    }
}
