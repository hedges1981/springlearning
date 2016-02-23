package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rowland-hall on 11/02/16
 */
@Entity
@Table( name = "emp", schema = "jpalearning" )
@SecondaryTable( name="emp_additional_info" , pkJoinColumns = @PrimaryKeyJoinColumn(name="emp_id"))
//NOTE: above tells it that emp_additional_info is the secondary table and that emp_id is the join column
//NOTE: to ensure a 1-1 for the link from emp to the secondary tables, make the pk of the sec table same as the main table.
//NOTE: you can have @SecondaryTables to use > 1 sec table.  Seel book page 299 for an example of a more complicated secondary table situation.
@Access( AccessType.FIELD )
public class AORMEmployee  extends CachedEntity
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

    @OneToMany( mappedBy = "employee", orphanRemoval = true, cascade = CascadeType.PERSIST ) //NOTE that orphan removal is effectively the same as cascade remove
    private List<AORMEmpChild1> child1s;


    //NOTE: how these two fields are set to get pulled from the secondary table:
    @Column(name = "additional_info_1",  table= "emp_additional_info")
    private String additionalInfo1;
    @Column(name = "additional_info_2",  table= "emp_additional_info")
    private String additionalInfo2;

    public List<AORMEmpChild1> getChild1s()
    {
        return child1s;
    }

    public void setChild1s( List<AORMEmpChild1> child1s )
    {
        this.child1s = child1s;
    }

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

    public String getAdditionalInfo1()
    {
        return additionalInfo1;
    }

    public void setAdditionalInfo1( String additionalInfo1 )
    {
        this.additionalInfo1 = additionalInfo1;
    }

    public String getAdditionalInfo2()
    {
        return additionalInfo2;
    }

    public void setAdditionalInfo2( String additionalInfo2 )
    {
        this.additionalInfo2 = additionalInfo2;
    }
}
