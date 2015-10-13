package com.hedges.jpalearning.model;

import com.hedges.jpalearning.U;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by rowland-hall on 12/10/15
 */
@Entity
@Table( name = "emp", schema = "jpalearning" ) //would try to map to a table called Employee without this.
//note the schema bit, it does default to jpalearning anyway, but this explicitly tells it to always talk in terms of table: jpalearning.emp
@Access( AccessType.FIELD )
public class Employee
{
    public static final String LOCAL_AREA_CODE = "01978";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    //IDENTITY right one to use to get mysql auto increment, note see book p. 82,might be better to use a sequence table or db held sequence to make it db
    //independent and have control over the allocated values.
    private int id;

    /*
    this gets auto picked up and mapped to the right column. JPA will auto map simple types like numbers, strings, dates etc,
    see book page 74.
     */
    private String dogName;

    @Basic( fetch = FetchType.LAZY )
//Should only get fetched when comments is read from the object.   Note, seems like hibernate ignores this for
    // columns, see book pg. 77. TODO: test this with a joined table.
    private String comments;

    @Transient
    //this is marked as transient as the phone column has its access type overridden to Property, see below:
    private String phoneNum;

    @Lob //tells it to use 'LOB methods' in passing around this thing. Use this for large objects.
    private byte[] picture;

    @Enumerated( EnumType.STRING ) //causes the String value of the enum to get inserted in the DB.
    private EmployeeType employeeType;

    @Enumerated//uses type= ORDINAL as the default, will be a 0-x depending on the enum's position in its list.
    //This is dodgy as fuck, re ordering the enum/ adding new ones will cause major issues.
    private EmployeeSuspendedStatus employeeSuspendedStatus;

    @Temporal( TemporalType.DATE )//tells it to use the java.sql.date class when mapping it to the db:
    private Date startDate;
    @Temporal( TemporalType.TIME )
    //column is a time, so this tells it to convert the Date obj to a java.sql.time obj before persisting.
    private Date startTime;
    /*
    Note on the temporal annotation, hibernate and mysql seems smart enough to resolve it without the @, e.g. the above startTime
    still gets persisted to the DB as a time without the @Temporal, note however that it break with TemporalType.DATE, as the db column is a TIME.
     */

    @Transient
    //need @Transient,as no column in the db. Note: be ware of property: HibernateJpaVendorAdapter.generateDDL being set to true, see context xml file.
    private String notToBePersisted;

    @ManyToOne(cascade = CascadeType.PERSIST)//note the cascade type persist, means that we can create a new department, set it on the employee, then save
    //the whole lot. No need to save the department object first.
    @JoinColumn( name = "dept_id" )
    private Department department;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getDogName()
    {
        return dogName;
    }

    /*
    This does not get called when it is pulled out the db, instead, because the dogName column is set to field access, setting is done
    on the field direct.
     */
    public void setDogName( String dogName )
    {
        U.print( "setting dog name to:" + dogName );
        this.dogName = dogName;
    }

    public String getPhoneNum()
    {
        return phoneNum;

    }

    public void setPhoneNum( String phoneNum )
    {
        this.phoneNum = phoneNum;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments( String comments )
    {
        this.comments = comments;
    }

    @Access( AccessType.PROPERTY )
    //means that the below getter and setter will be used when working with the phone column
    @Column( name = "phone" )    //would default the column name to the name of the property if not set.
    public String getPhoneNumForDb()
    {
        /*
        This one gets called when it reads from the object to make a db insert.
         */

        if ( phoneNum.startsWith( LOCAL_AREA_CODE ) )
        {
            return phoneNum;
        }
        else
        {
            return LOCAL_AREA_CODE + phoneNum;
        }

    }

    /*
    This one gets called when it reads from the db to create an object.
     */
    public void setPhoneNumForDb( String phoneNum )
    {
        if ( phoneNum.startsWith( LOCAL_AREA_CODE ) )
        {
            this.phoneNum = phoneNum.substring( LOCAL_AREA_CODE.length() );
        }
        else
        {
            this.phoneNum = phoneNum;
        }

    }

    public byte[] getPicture()
    {
        return picture;
    }

    public void setPicture( byte[] picture )
    {
        this.picture = picture;
    }

    public EmployeeType getEmployeeType()
    {
        return employeeType;
    }

    public void setEmployeeType( EmployeeType employeeType )
    {
        this.employeeType = employeeType;
    }

    public EmployeeSuspendedStatus getEmployeeSuspendedStatus()
    {
        return employeeSuspendedStatus;
    }

    public void setEmployeeSuspendedStatus( EmployeeSuspendedStatus employeeSuspendedStatus )
    {
        this.employeeSuspendedStatus = employeeSuspendedStatus;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime( Date startTime )
    {
        this.startTime = startTime;
    }

    public String getNotToBePersisted()
    {
        return notToBePersisted;
    }

    public void setNotToBePersisted( String notToBePersisted )
    {
        this.notToBePersisted = notToBePersisted;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }
}
