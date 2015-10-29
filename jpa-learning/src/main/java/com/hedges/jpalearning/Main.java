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

       // chapter1Learning( context );

        //chapter2Learning (context );

        chapter3Learning( context );

    }

    private static void chapter3Learning( ClassPathXmlApplicationContext context )
    {
        EmployeeService employeeService = context.getBean( EmployeeService.class );
        //Notice how this method causes the employee changes to get saved, even though no '.save' is explicitly called,
        // the committing of the txn causes the changes to the persistence context to get flushed out and persisted.
        employeeService.testUpdateEmployee();

        //working with an application mananged EntityManager:
        EntityManagerFactory entityManagerFactory = context.getBean( EntityManagerFactory.class );

        EntityManager em = entityManagerFactory.createEntityManager();

        demoUseOfEntityManagerToMakeChanges( em );

        demoUseOfEntityManagerRefresh( em );

        em.close(); //good practice to close an EntityManager when done.

        int x =0;

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
            throw new RuntimeException();
        }

        txn.commit();
    }

    private static void chapter2Learning( ClassPathXmlApplicationContext context )
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

        employeeService.saveEmployeeAndFlush( e );

        e = employeeService.findById( 1 );

        U.print(e.getHolidays());

        Map<String, String> relatives = e.getRelatives();

        relatives.put( UUID.randomUUID().toString().substring( 0,30 ), "aName" );
        relatives.put( "mother", "mother name updated" );

        employeeService.saveEmployeeAndFlush( e );

        Set<String> nicknames= new HashSet<String>();

        nicknames.add("dsdsdsoooo");
        nicknames.add("qwqwqwoooo");
        e.setNickNames( nicknames );

        employeeService.saveEmployeeAndFlush( e );

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

    private static void chapter1Learning( ClassPathXmlApplicationContext context )
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

        employeeService.saveEmployeeAndFlush( employee );

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
