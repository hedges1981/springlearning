package com.hedges.jpalearning;

import com.hedges.jpalearning.model.*;
import com.hedges.jpalearning.otherobjs.PhoneAndDog;
import com.hedges.jpalearning.service.EmployeeService;
import com.hedges.jpalearning.service.GeneralService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.*;
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

        //chapter7LearningJPQL( context );

        chapter8LearningJPQL( context );
    }

    private static void chapter8LearningJPQL( ClassPathXmlApplicationContext context )
    {
        EntityManager em = context.getBean( EntityManager.class );
        List<Employee> emps;
        //DEMO use of path expressions
        String query = "SELECT e from Employee e where e.department.name='sales'";

        emps = em.createQuery( query ).getResultList();

        U.print(emps);

        //type of list given by whats in it, e.g.:
        query = "SELECT e.firstName from Employee e";
        List<String> names = em.createQuery( query ).getResultList();
        U.print(names);

        //when > 1 'column' selected, u get an Object [] back, e.g.
        query = "SELECT e.firstName,e.lastName from Employee e";
        List<Object[]> results = em.createQuery( query ).getResultList();

        for(Object[] objArr : results)
        {
            U.print("Fname:"+objArr[0]+" LName:"+objArr[1]);
        }

        //INHERITANCE and PLOYMORPHISM
        //see book page 216, note couldn't get this to work, revisit when covered inheritance in the advanced ORM chapter.
//        query = "Select p from Project p";
//        List<Project> projects = em.createQuery( query ).getResultList();
//
//        int x =0;

        //******************JOINS **************************************

        //INNER join example:
        query = "select p from Employee e JOIN e.phones p";//select the phones object from employee join phones, using the
        //relationship defined on the Employee entity.
        List<Phone> phones = em.createQuery( query ).getResultList();

        int x =0;

        //working with maps in joins,
        //NOTE the use of KEY and VALUE in the statement
        //find the names of all of the employees mothers:
        query = "select KEY(r), VALUE(r) from Employee e join e.relatives r where KEY(r) in ('mother')";

        U.print(em.createQuery( query ).getResultList()) ;

        //OUTER joins vs INNER joins example:
        //Inner join, only gets emps with a department:
        query = "SELECT e from Employee e JOIN e.department d";
        emps = em.createQuery( query ).getResultList();

        U.print("Only "+emps.size()+" emps assigned toa department");

        //LEFT join, gets those with and without a department:
        query = "SELECT e from Employee e LEFT JOIN e.department d";
        emps = em.createQuery( query ).getResultList();
        U.print("TOTAL of "+emps.size()+" emps with and without a department");

        //DEMO USE OF FETCH JOIN, ALONG WITH A COLLECTION EXPRESSION
        query = "select distinct e from Employee e left join fetch e.holidays";
        emps = em.createQuery( query ).getResultList();

        for( Employee e : emps )
        {
            U.print(e.getHolidays().size());//holidays already there due to the fetch in the query.
            //note some will come out as 0 size, due to the left join
        }

        //DEMO of things U can do with the where clause:
        //between and like:
        query = "Select  e from Employee e where e.salary between 1 and 10 and e.lastName like'__hn%'";
        //note : here __ in the like means any 2 characters, then hn%, etc.
        emps = em.createQuery( query ).getResultList();

        //DEMO use of nested query:
        //Note, you can also do EXISTS with nested queries.
        query = "select e from Employee e where e.salary =(select max(e.salary) from Employee e )";
        Employee e = em.createQuery( query, Employee.class ).getSingleResult();
        U.print(e);

        //DEMO use of collection expressions:
        //IS NOT EMPTY:
        query =" select e from Employee e where e.phones IS NOT EMPTY";
        emps = em.createQuery( query ).getResultList();

        U.print(emps.size());
        //IS EMPTY:
        query =" select e from Employee e where e.phones IS EMPTY";
        emps = em.createQuery( query ).getResultList();

        U.print(emps.size());

        //MEMBER OF:
        GeneralService gs = context.getBean( GeneralService.class);
        Project pj1 =     gs.getProjectById( 1 );
        //FIND all emps that are    on that project:
        query = "select e from Employee e where :project member of e.projects";
        Query q = em.createQuery( query );
        q.setParameter( "project", pj1 );

        emps = q.getResultList();

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

        employees = entityManager.createNamedQuery( "Employee.getEmployeesInSalesDept" ).getResultList();

        U.print( employees );

        //THIS one demonstrates a simple join
        List<Department> departments = entityManager.createNamedQuery( "Department.getDepartmentsWithContractEmployee" ).getResultList();

        U.print( departments );

        //demos a count, with a join
        List<Object[]> resultsArr = entityManager.createNamedQuery( "Department.getDepartmentEmployeeCount" ).getResultList();

        for( Object[] objArr: resultsArr )
        {
            Department d = (Department)objArr[0];
            Long l = (Long)objArr[1];

            U.print(d.getName()+":"+l);
        }

        //demos max and avg, with a join
        resultsArr = entityManager.createNamedQuery( "Department.getDepartmentSalaryStats" ).getResultList();

        for( Object[] objArr: resultsArr )
        {
            U.print("Dept:max sal:avg sal");
            Department d = (Department)objArr[0];
            Integer max = (Integer)objArr[1];
            Double avg = (Double)objArr[2];

            U.print(d.getName()+":"+max+":"+avg);
        }

        //this one demos the use of a constructor expression
        List<PhoneAndDog> phoneAndDogs = entityManager.createNamedQuery( "Employee.getPhoneAndDogConstructorExp" ).getResultList();

        U.print(phoneAndDogs);

        //DEMO the use of pagination, note that in reality page size etc would be a lot bigger:
        int currentPage = 0;  //page start counting from 0:
        int pageSize =5;
        //get the first page:
        employees = entityManager.createQuery( allEmpsViaQueryStr ).setFirstResult( currentPage * pageSize ).setMaxResults( pageSize ).getResultList();
        U.print( "employees on first page"+employees );

        pageSize ++;
        employees = entityManager.createQuery( allEmpsViaQueryStr ).setFirstResult( currentPage * pageSize ).setMaxResults( pageSize ).getResultList();
        U.print( "employees on second page"+employees );
        //etc etc

        //STUFF TDW FLUSH MODE:
        U.print( "defaultFlushMode" + entityManager.getFlushMode() );//Note that the default flush mode be auto here, good idea.
        //state of the transaction.
        entityManager.setFlushMode( FlushModeType.COMMIT );//only flushes on a COMMIT, can cause the EMs transactional context to be different to the db,
        //but perhaps good idea if queries aren't going to clash and u want to optimies performance.
        //CAN be set on the EM, which makes it the default across all queries: 2 types of flush mode:
        entityManager.setFlushMode( FlushModeType.AUTO );//will flush before a query, so the query from the db represents the


        //CAN set the flush Mode on a specific query to tailor it to circumstances, best bet: have auto on the EM, the do COMMIT on a given query if u need to:
        Query query = entityManager.createQuery( allEmpsViaQueryStr );
        query.setFlushMode( FlushModeType.COMMIT );

        //CAN set a timeout on a query, but is only a hint, not guaranteed to be obeyed by all providers:
        //Note on hints, looks like you can set all kinds of vendor specific stuff, e.g. to control going to a cache.
        //Note also that you can also use @NamedQuery.hints to set that stuff.
        query.setHint( "javax.persistence.query.timeout", 0 );

        try
        {
            query.getResultList();
        }
        catch( QueryTimeoutException e )
        {
            //this be what would get thrown if it timed out,not sure why it isn't getting thrown here as time out is set to 0...
            //perhaps a good idea to catch timeout exceptions, to avoid them rolling back the current txn.
        }

        /**BULK UPDATES / DELETES
         *
         * NOTE there is a lot of discussion in the book about how bulk operations relate to the presistence context.
         * U basically need to remember that they are done as direct SQL, so don't expect things like e.g. cascaeType.REMOVE to work,
         * the persistence unit and the relationships it defines are bypassed by the bulk stuff.
         */

        //DEMOS the use of the entity manger to do a bulk update.
        //note, had to be a method on the service as reqiured a container managed transaction.
        employeeService.demoABUlkUpdate( "Is a clown" );
        /*
        NOTE, the same idea can be used to do a bulk delete.
        NOTE, the persistence context does not automatically refresh when a bulk delete/update is done, so it can get out of sync
        with what is actually in the DB.

        RECOMMENDATION then is to either i) always do the bulk stuff at the start of the transaction, i.e. before anything is actually read
        into the persistence context, or do a bulk update/delete always in its own isolated txn.
         */




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
