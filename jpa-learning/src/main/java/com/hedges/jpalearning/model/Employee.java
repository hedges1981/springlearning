package com.hedges.jpalearning.model;

import com.hedges.jpalearning.U;

import javax.persistence.*;


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
    @GeneratedValue( strategy = GenerationType.AUTO )//this allows the mysql auto increment stuff to do its business
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
}
