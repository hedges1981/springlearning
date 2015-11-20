package com.hedges.jpalearning.model;

import com.hedges.jpalearning.U;

import javax.persistence.*;
import java.util.*;


/**
 * Created by rowland-hall on 12/10/15
 */
@Entity
@Table( name = "emp", schema = "jpalearning" ) //would try to map to a table called Employee without this.
//note the schema bit, it does default to jpalearning anyway, but this explicitly tells it to always talk in terms of table: jpalearning.emp
@Access( AccessType.FIELD )

//********************************** Named queries ************************************************
//note : good practice to prefix the query name with the entity name
@NamedQueries( {
@NamedQuery(name = "Employee.getAllEmployees", query=" SELECT e FROM Employee e"),
@NamedQuery(name = "Employee.getAllEmployeesByName", query=" SELECT e FROM Employee e where e.lastName= :name"),
//Note on this one, no need to put a join in to department, as it uses the object relation mapping to make the join.
@NamedQuery(name = "Employee.getEmployeesInSalesDept", query=" SELECT e FROM Employee e where e.department.name = 'sales'"),

        //NOTE the use of the constructor expression in this
@NamedQuery(name = "Employee.getPhoneAndDogConstructorExp",
            query=" SELECT new com.hedges.jpalearning.otherobjs.PhoneAndDog(e.phoneNumForDb, e.dogName) FROM Employee e",
hints={@QueryHint( name="aHint", value="aVAlue ")})

})
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

    @ManyToOne
    @JoinColumn( name = "dept_id" )
    private Department department;

    @OneToOne(cascade = CascadeType.PERSIST) //note how the one to one set up is backed up by a unique constraint in the emp table on parking_space_id
    @JoinColumn( name = "parking_space_id" ) // look in the ParkingSpace class for the reverse mapping.
    private ParkingSpace parkingSpace;

    @ManyToMany(fetch = FetchType.EAGER ) //note how we use the simple join table to map the many to many relation here:
    //note that the join table and columns will default if not specified, see book p.100 for more info.
    @JoinTable(name="emp_project", joinColumns = @JoinColumn(name="emp_id"), inverseJoinColumns = @JoinColumn(name="project_id"))
    private Collection<Project> projects;

    @OneToMany//( fetch = FetchType.EAGER) //this is a unidirectional one to many, note that there is no inverse mapping on the phone object
    //note issue using eager fetch for more than 1 collection on the same entity, see here: .
    @JoinTable( name="emp_phone", joinColumns = @JoinColumn(name="emp_id"), inverseJoinColumns = @JoinColumn(name="phone_id") )
    private Collection<Phone> phones;

    @Embedded
    @AttributeOverrides( {@AttributeOverride( name="state", column=@Column(name="county")), @AttributeOverride( name="zipCode", column=@Column(name="postcode") )} )
    //note the overrides, Address object deals in us terms (state, zip code), but columns in Emp table are uk (county, postcode)
    private Address address;

    //Collection mapping: note that the holilday class is an @Embedded, even though it has its own table. It is not really an entity
    //as it is really an embedded object of the Employee class, but needs its own table as there are > 1 of them!
    @ElementCollection( targetClass = Holiday.class )
    @CollectionTable( name="holiday", joinColumns = @JoinColumn(name="emp_id"))
    private Collection holidays;
    //****************note that with collection mappings,  cascade etc is always all, as
    //the tables that house the collections are always owned completely by their entity.
    @ElementCollection
    @CollectionTable( name="nicknames", joinColumns = @JoinColumn(name="emp_id"))
    @Column(name ="nickname")         //note how the specification of the single column allows it to be a set
    //of strings, no need for a nickname entity.
    private Set<String> nickNames;


    @ElementCollection
    @CollectionTable(name="emp_relative", joinColumns = @JoinColumn(name="emp_id"))
    @MapKeyColumn(name="relative_type")
    //use e.g. @MapKeyEnumerated( EnumType.STRING ) if you want the map keys converted into an ENUM.
    @Column(name="name")
    private Map<String, String> relatives;

    @OneToMany( targetEntity = CascadeDemo.class, mappedBy ="employee" , cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    private List<CascadeDemo> cascadeDemoList;

    private String firstName;

    private String lastName;

    private int salary;

    //******************************getters and setters go below:***********************************************


    public int getSalary()
    {
        return salary;
    }

    public void setSalary( int salary )
    {
        this.salary = salary;
    }

    public List<CascadeDemo> getCascadeDemoList()
    {
        return cascadeDemoList;
    }

    public void setCascadeDemoList( List<CascadeDemo> cascadeDemoList )
    {
        this.cascadeDemoList = cascadeDemoList;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public static String getLocalAreaCode()
    {
        return LOCAL_AREA_CODE;
    }

    public Set<String> getNickNames()
    {
        return nickNames;
    }

    public void setNickNames( Set<String> nickNames )
    {
        this.nickNames = nickNames;
    }

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

        if ( phoneNum!=null && phoneNum.startsWith( LOCAL_AREA_CODE ) )
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

    public ParkingSpace getParkingSpace()
    {
        return parkingSpace;
    }

    public void setParkingSpace( ParkingSpace parkingSpace )
    {
        this.parkingSpace = parkingSpace;
    }

    public Collection<Project> getProjects()
    {
        return projects;
    }

    public void setProjects( Collection<Project> projects )
    {
        this.projects = projects;
    }

    public Collection<Phone> getPhones()
    {
        return phones;
    }

    public void setPhones( Collection<Phone> phones )
    {
        this.phones = phones;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }

    public Collection getHolidays()
    {
        return holidays;
    }

    public void setHolidays( Collection holidays )
    {
        this.holidays = holidays;
    }

    public Map<String, String> getRelatives()
    {
        return relatives;
    }

    public void setRelatives( Map<String, String> relatives )
    {
        this.relatives = relatives;
    }

    public String toString()
    {
        return firstName+" "+lastName;
    }

}
