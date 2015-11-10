package com.hedges.jpalearning;

import com.hedges.jpalearning.model.*;
import com.hedges.jpalearning.service.EmployeeService;
import com.hedges.jpalearning.service.GeneralService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.*;

/**
 * Created by rowland-hall on 12/10/15
 */
public class Main
{
    public static void main( String[] args )
    {
        System.getProperty( "hibernate.hbm2ddl.auto" );

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "classpath:context.xml" );

//        chapter4LearningORM( context );
//
//        chapter5LearningCollectionMapping( context );
//
//        chapter6LearningEntityManager( context );

        chapter7LearningJPQL( context );

    }

    private static void chapter7LearningJPQL( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );
        //DEMOING USE OF JPQL.
        //Spring auto link up with repository and @NamedQuery
        List<Employee> employees = employeeService.getAllEmployees_NQ();
        U.print( employees );

        //Creation of query on the fly using EntityManager:
        EntityManager entityManager = context.getBean( EntityManager.class );

        String allEmpsViaQueryStr = " SELECT e FROM Employee e";

        employees = entityManager.createQuery( allEmpsViaQueryStr ).getResultList();
        U.print( employees);

        //Using the EM to find a named query:
        employees = entityManager.createNamedQuery( "Employee.getAllEmployees" ).getResultList();

        U.print( employees);

        employees = employeeService.getAllEmployeesByName_NQ( "smith3" );

        U.print( employees );



    }

    private static void chapter6LearningEntityManager( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );
        //Notice how this method causes the employee changes to get saved, even though no '.save' is explicitly called,
        // the committing of the txn causes the changes to the persistence context to get flushed out and persisted.
        employeeService.testUpdateEmployee();

        //working with an application mananged EntityManager:
        EntityManagerFactory entityManagerFactory = context.getBean( EntityManagerFactory.class );

        EntityManager em = entityManagerFactory.createEntityManager();

//        demoUseOfEntityManagerToMakeChanges( em );
//
//        demoUseOfEntityManagerRefresh( em );
//
//        demoUseOfEntityManagerPersist( em );
//
//        demoUseOfEntityManagerRemove( em );
        
        int empIdForCascadingDemo = demoCascadingPersist( context );

        demoCascadingRemove( context, empIdForCascadingDemo );

        demoUseOfEntityManagerDetachAndMerge( em );

        em.close(); //good practice to close an EntityManager when done.

        int x =0;

    }

    private static void demoUseOfEntityManagerDetachAndMerge( EntityManager em )
    {
        em.getTransaction().begin();

        Employee e1 = em.find( Employee.class, 1 );

        em.detach( e1 );

        e1.setLastName( "this will not get saved as employee is detached" );

        Employee e2 =  em.find( Employee.class, 2 );

        e2.setLastName( "this WILL get saved as employee is  not detached" );

        em.getTransaction().commit();


        em.getTransaction().begin();
        
        Employee e1merged = em.merge( e1 );

        e1.setLastName( "this will not get saved, as when merge is used, a different object actually enters the persistence context" );

        e1merged.setLastName( "This will get saved, as e1merged is now the managed object" );

        em.getTransaction().commit();
    }



    private static void demoCascadingRemove( ClassPathXmlApplicationContext context, int empIdForCascadingDemo )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );

        //this will remove all of the CascadeDemo objects from the db for the employee being deleted.
        employeeService.removeEmployeeById( empIdForCascadingDemo );



    }

    /**
     * NOTE: the way you need to set the employee object on the CascadeDemo object.
     */
    private static int demoCascadingPersist( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );

        //note the cascade-persist relation ship:

        Employee e = new Employee();

        e.setFirstName( "Mavis" );
        e.setLastName( "Dog" );

        List<CascadeDemo> cascadePersistList = new ArrayList<CascadeDemo>(  );

        CascadeDemo cp1 = new CascadeDemo();
        cp1.setName( "cp1" );

        //******************without next line, it doesn't know to set the emp_id column on the CascadeDemo table,
        //APPLICATON MUST MANAGE THE RELATIONSHIPS BETWEEN ENTITIES!

        cp1.setEmployee( e );
        cascadePersistList.add(cp1);

        CascadeDemo cp2 = new CascadeDemo();
        cp2.setName( "cp2" );
        cp2.setEmployee( e );
        cascadePersistList.add(cp2);

        e.setCascadeDemoList( cascadePersistList );

        employeeService.saveEmployee( e );

        return e.getId();


    }

    private static void demoUseOfEntityManagerRemove( EntityManager em )
    {
        em.getTransaction().begin();

        Employee e = new Employee();
        e.setFirstName( "mr" );
        e.setLastName( "Smith" );
        em.persist( e );
        em.getTransaction().commit();

        //emoloyee is now in the db.
        em.getTransaction().begin();
        //e is not in the presistence context, as it is a transaction scoped entity manager and it left the persitence context
        // when the last txn was committed, calling the merge gets it in there.
        em.merge( e );
        em.remove( e );
        em.getTransaction().commit(); //the Employee gets deleted from the DB on the commit of the txn.
    }

    //Demos how em.persist attaches an object to the persistence context, enabling it to be saved to the db:
    private static void demoUseOfEntityManagerPersist( EntityManager em )
    {
        Employee e = new Employee();
        e.setFirstName( "Neville" );
        e.setLastName( "Norriss" );

        em.getTransaction().begin();

        em.getTransaction().commit();

        //new employee will not have been persisted to the db,  as it is not 'attached'.



        em.getTransaction().begin();
        em.persist( e );  //looks like the persist needs to be inside the txn.
        //now it is attached to the EMs persitence context:
        em.getTransaction().commit();

        //after the end of the txn, new employee is in the db:

    }

    private static void demoUseOfEntityManagerRefresh( EntityManager em )
    {
        Employee e1 = em.find( Employee.class, 1 );

        U.print( "Comments are:" + e1.getComments() );

        //now change the comments in the db:

        //this call should cause them to get picked up again:
        em.refresh( e1 );

        U.print("Comments are now:"+e1.getComments());
    }

    private static void demoUseOfEntityManagerToMakeChanges( EntityManager em )
    {
        //can call various methods on the entity manager, e.g. to find by an Id, etc.
        List<Employee> allEmployees = em.createQuery( "SELECT e FROM Employee e" ).getResultList();

        allEmployees.get(5).setDogName( UUID.randomUUID().toString() );   //we have changed a field on a managed object, the next call persists it to the db:

        //next bit uses 'resource local transactions'. Apparently you can start/commit/rollback any number of these within a container managed
        //txn, without affecting the container managed txn.
        EntityTransaction txn =  em.getTransaction();
        txn.begin();

        em.flush(); //the flush pushes the change to the db:

        if( true)
        {
            //even though the changes have been flushed, they need the transaction commit to actually get made permenant.
            //hence this exception will ruin the thing!
            //throw new RuntimeException();
        }

        txn.commit();
    }

    private static void chapter5LearningCollectionMapping( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );

        Employee e = getEmployee();

        List<Holiday> holidays = new ArrayList<Holiday>(  );

        Holiday h1 = new Holiday();
        h1.setStartDate( new Date() );
        h1.setNumDays( 10 );

        holidays.add( h1 );

        Holiday h2 = new Holiday();
        h2.setStartDate( new Date() );
        h2.setNumDays( 11 );

        holidays.add( h2 );

        e.setHolidays( holidays );

        employeeService.saveEmployee( e );

        e = employeeService.findById( 1 );

        U.print(e.getHolidays());

        Map<String, String> relatives = e.getRelatives();

        relatives.put( UUID.randomUUID().toString().substring( 0,30 ), "aName" );
        relatives.put( "mother", "mother name updated" );

        employeeService.saveEmployee( e );

        Set<String> nicknames= new HashSet<String>();

        nicknames.add("dsdsdsoooo");
        nicknames.add("qwqwqwoooo");
        e.setNickNames( nicknames );

        employeeService.saveEmployee( e );

        GeneralService generalService = context.getBean( GeneralService.class );

        Department department = generalService.getDepartmentById( 1 );

        PrintQueue pq = generalService.getPrintQueueByName( "queue1" );

        List<PrintJob> pjs = pq.getPrintJobs();

        PrintJob pj1 = new PrintJob();

        generalService.addPrintJobToPrintQueue( pj1, pq );

        int x=0;

        Department d1 = generalService.getDepartmentById( 1 );

        U.print( d1.getEmployeesByDeskId() );
        U.print(d1.getEmployeesById());
        U.print(d1.getEmployeesByName());

    }

    private static void chapter4LearningORM( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );

        Employee e1 = employeeService.findById( 23 );

        Employee employee = getEmployee();

        GeneralService generalService = context.getBean( GeneralService.class );

        Department dept = generalService.getDepartmentById( 1 ) ;

        employee.setDepartment( dept );

        ParkingSpace ps = new ParkingSpace();
        ps.setLocation( UUID.randomUUID().toString() );

        employee.setParkingSpace( ps );

        employeeService.saveEmployee( employee );

        Employee e2 = employeeService.findById( employee.getId() );


        ParkingSpace ps2 = generalService.getParkingSpaceById( 1 );

        U.print( ps2.getEmployee() );

        Department dept2 = generalService.getDepartmentById( 1 ) ;
        U.print( dept2.getEmployees() );

        //project will have lots of employees attached to it:
        Project project = generalService.getProjectById( 1 );

        //see how this employee has > 1 project and >1 phone:
        Employee e3 = employeeService.findById( 1 );

        U.print( e1.getPhones());

        int x=0;
    }

    private static Employee getEmployee()
    {
        Employee employee = new Employee();
        employee.setPhoneNum( "761725" );
        employee.setPicture( "thisIsAMadeUpPicture".getBytes() );
        employee.setEmployeeType( EmployeeType.CONTRACT_EMPLOYEE );
        employee.setEmployeeSuspendedStatus( EmployeeSuspendedStatus.NOT_SUSPENDED );
        employee.setStartDate( new Date() );
        employee.setStartTime( new Date() );
        Address address = new Address();
        address.setCity( "Chester" );
        address.setState( "Cheshire" );
        address.setStreet( "High Street" );
        address.setZipCode( "CH40JY" );
        employee.setAddress( address );
        return employee;
    }
}
