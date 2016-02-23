package com.hedges.jpalearning;

import com.hedges.jpalearning.advancedchapters.model.*;
import com.hedges.jpalearning.advancedchapters.services.AORMGeneralService;
import com.hedges.jpalearning.model.*;
import com.hedges.jpalearning.otherobjs.PhoneAndDog;
import com.hedges.jpalearning.service.EmployeeService;
import com.hedges.jpalearning.service.GeneralService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.validation.ConstraintViolationException;
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

      //  chapter8LearningJPQL( context );

      //  chapter9LearningCriteriaAPI( context );

        //chapter10AdvancedORM();

        chapter11AdvancedTopics();
    }

    private static void chapter11AdvancedTopics()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "classpath:advancedOrmChapterContext.xml" );

        AORMGeneralService generalService = context.getBean( AORMGeneralService.class );
        EntityManager em = context.getBean( EntityManager.class );

        //*********************NATIVE SQL QUERIES*******************************************
        //OPTION1, creating the sql in-line:

        String sql = "select * from dog";

        List<AORMDog> dogs = em.createNativeQuery( sql, AORMDog.class ).getResultList();
        //NOTE: how with this it goes to the dog object and works out from the column names of the returned results set, how to map the results to
        //an AORMDog object.
        U.print( dogs);
        //TODO: there is probably a way to use th SpringDataJPA repositories to run native sql also.

        //OPTION2: Using @NamedNativeQuery
        //see the AORMDog class where this named native query is defined,
        dogs = em.createNamedQuery( "findAllDogs", AORMDog.class ).getResultList();
        U.print(dogs);

        //NOTE: SEE  BOOK PAGE 319 regarding dangers of un expectedly modifying the db by using sql, basically if a native sql query gets mapped to an entity,
        // then the resulting entities are managed, therefore you need to be careful about changes to them getting persisted

        //NOTE: native sql can also be used to do inserts and updates, example of an insert:
        sql = "insert into curry  (origin_country, hotnessLevel) values ('UK' , '999')";
        generalService.executeNativeSql( sql ); //NOTE: had to put the executeUpdate(...) code in the service class as it requires a transaction.

        //*********************SQL RESULT SET MAPPING***************************************
        //SEE book pages 320-325 about this.
        //TODO: work through this section, ( looks very very dull )

        //*******************LIFECYCLE CALLBACKS *******************************************
        //JPA has the following lifecycle events, on which actions can be fired:
        //PrePersist, PostPersist, PreUpdate, PostUpdate, PreRemove, PostRemove, and PostLoad.

        /*
        Pre-persist : - is called when entityManager.persist(,,) is called on an entity. If an entity tree has persist: cascade on it the each entity
        in the cascade gets the event fired.

        Post-persist :- called when the entities insert statement is actually done, normally in the process of flushing the persisence context and committing the
        transaction. The firing of post-persist does not mean the txn has successfully committed, it can still get rolled back even if post-persist has been fired.

        PreRemove:- gets called when entityManager.remove(..) is called.

        PostRemove:- gets called when the delete statement is sent to the db. Again, it's calling doesn't indicate success, as the overall txn could still get rolled back.

        PreUpdate: - called at some point between an entity being updated and the change being pushed to the db. The point at which it is called is
        implementation specific, also the number of times it gets called if an entity is updated > 1 time in a txn is also vendor specific.

        PostUpdate: - called after the update statement is fired at the db. Again as with the others the enclosing transaction could be rolled back, so there is no guarantee that it
        means the change has been permanently made.

        PostLoad :- called after an entity has been read from the db and its object constructed. Will also fire as a result of a lazy initialisation, Can also fire if
        entityManager.refresh() is called.

        NOTE: with all the event firings, they also get fired if things like persist, remove, etc are cascaded down entity trees.

        NOTE: you can only annotate one method on an entity with a given callback annotation, if there is > 1, that application startup fails.
         */

        //NOTE: when on entities, callback methods are executed from within the context of the current transaction and entity manager.

        //NOTE: methods on entities get called AFTER those on an entity listener.

        //AORMLifecycleCallbackDemo is annotated with all of the above callback methods, see the output of this code to see them in action:

        //triggers the pre and post persist:
        generalService.create10LifeCycleCallBackDemos();
        //triggers the PostLoad, pre update and post update:
        generalService.updateAllLifecycleCallbackDemos();
        //triggers the pre-remove and post-remove methods:
        generalService.deleteAllLifecycleCallBackDemos();

        //*****************************ENTITY LISTENERS ***********************
        //--allow you to move the call-back code off the entity into another class.
        //check the logs for this operation to see i) that the entity listeners have been called and ii) that the order fired reflects the order declared on the class:
        generalService.createLifeCycleCallbackDemoWithString( "validString" );

        //NOTE: methods on entity listeners get called BEFORE those on an entity.

        //NOTE: you can only annotate one method on an entity listener with a given callback annotation, if there is > 1, that application startup fails.

        //NOTE: how this one causes AORMLifeCycleCallBackDemoEntityListener1 to throw an exception, demos how the entity listeners
        //can be used for validation, and how  an exception from the validation stops a txn getting committed.
        try
        {
            generalService.createLifeCycleCallbackDemoWithString( "invalidString" );
        }
        catch( Exception e )
        {
           U.print( "Expected exception occurred");
        }

        //***************DEFAULT ENTITY LISTENERS**************************
        /*
        See book p.330 about these,
        - can only be declared in an XML mapping file, not via annotations.
        - order rules are:
        i) default listeners executed in order declared in the xml mappings file
        ii) they fire before any of the more specific ones declared at the entity level

        USE: @ExcludeDefaultListeners to exclude an entity from all of the default listeners
         */

        //******************ORDER OF CALL BACK METHODS WHEN INHERITANCE IS INVOLVED*****************

        //see book pages: 331-332,
        /*
        CALLBACK METHODS ON ENTITY CLASSES: get called in the order of the inheritance hierachy, with the super class first
        THEY NEED TO BE IN A CLASS THAT IS ANNOTATED WITH @Entity or @MappedSuperClass

        CALLBACK METHODS ON ENTITY LISTENERS: the superclass' entity listeners get called before sub classes,

        BASIC RULES FOR ORDERING ARE:
        - default listeners in the order defined in the XML mapping file.
        - entity listeners in the order defined on each class, descending down the hierarchy.
        - entity callback methods, descending down the class hierarchy

        */


        //********************************VALIDATION************************************************
        //NOTE; see how the ValidationExample has various validations on it:

        //NOTE: to get the validation to work automatically, it was necessary to have: org.hibernate.validator.HibernateValidator on the class path,
        //see the pom.xml for the inclusion of: <artifactId>hibernate-validator</artifactId>

        ValidationExample valex = new ValidationExample();
        //these settings mean it will fail validation:
        valex.setLengthLessThan5String( "123456");
        valex.setNotNullString( null );
        try
        {
            generalService.saveValidationExample( valex );
        }
        catch( ConstraintViolationException e )
        {
            U.print( " validation failed: exception was: "+e.getMessage());
        }

        //so lets get it to pass the validation:
        valex.setLengthLessThan5String( "1234" );
        valex.setNotNullString( "notNULL" );
        Date yesterday = DateUtils.addHours( new Date(), -24 );
        valex.setDateInThePast( yesterday );

        generalService.saveValidationExample( valex );

        //NOTE: what happens if we pull an entity out of the db that it not valid, but has got in there some how?
        //see how the entity with id = 5 has a 'lengthLessThan5String' with a length > 5.
        ValidationExample valex2 = generalService.findValidationExampleById( 5 );

        //NOTE how it has come out of the db fine, but if we try to do a valid update on it, what happens?
        try
        {
            generalService.doAValidUpdateOnAPreExistingInvalidEntity();
        }
        catch( ConstraintViolationException e )
        {   //basically it revalidates it and fails it.
            U.print( " validation failed, it has revalidated the object and failed on the pre-existing invalid data, exception was: "+e.getMessage());
        }

        //*****************************VALIDATION GROUPS*******************************************
        //TODO: some examples of this, see book page 338-339.

        //**************************CREATING NEW CONSTRAINTS***************************************
        //book pages 340-341





    }

    private static void chapter10AdvancedORM( )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "classpath:advancedOrmChapterContext.xml" );

        AORMGeneralService generalService = context.getBean( AORMGeneralService.class );
        EntityManager em = context.getBean( EntityManager.class );

        List<AORMEmployee>  employees = generalService.getAllEmployees();

        //NOTE: DEMOS USE OF 'CHAINED' EMBEDABLES'
        //NOTE: how we have a bended object structure with contactInfo and address objects, same as in book page ~276.
        for( AORMEmployee employee: employees )
        {   //stuff in here just verifies that object link employee-> contactInfo->Address and phones is working ok.
            if( employee.getContactInfo().getResidence() != null )
            {
                U.print("Employee found with embedded contactInfo that is embedded with an address:"+  employee.getContactInfo().getResidence());
            }

            if( employee.getContactInfo().getPhones()!= null && !employee.getContactInfo().getPhones().isEmpty() )
            {
                U.print("Employee found with embedded contactInfo that houses the phones:"+ employee.getContactInfo().getPhones() );
            }
        }

        //NOTE: proves that the link from AORMPhone to its employees is working.

        List<AORMPhone> phones = generalService.getAllPhones();

        for( AORMPhone phone : phones )
        {
            if( !phone.getEmployees().isEmpty() )
            {
                U.print( phone.getEmployees() );
            }
        }

        //DEMO USE OF @AssociationOverride:
        List<AORMCustomer>  customers = generalService.getAllCustomers();

        for( AORMCustomer customer: customers)
        {
            //NOTE: this shows that the @AssociationOverride to use the customer_phone table to get the phones has worked.
            U.print( customer.getContactInfo().getPhones() );
            //NOTE; this shows that the @AttributeOverride for the post_code column on the AORMCustomer object has worked:
            U.print( customer.getContactInfo().getResidence().getZipCode() );
        }

        //DEMO USE OF COMPOUND PRIMARY KEY
        //NOTE: how the AORMDog object is set up so that the AORMDogId can be used as its pk, pk on the table is (firstName, lastName)
        //NOTE: this does not demo an embedded pk, as the dog class still has the firstName and lastName fields.
        AORMDog mavis = generalService.findDogById( new AORMDogId( "mavis", "hall" ) );
        U.print( mavis );

        //DEMO use of embedded Primary Key:
        AORMElephant elephant = generalService.findElephantById( new AORMElephantId( "brial","ele" ) );
        U.print(elephant);

        //NOTE: embedded primary keys require special treatment in queries, note how the id object must be traversed:
        String query = "Select e from AORMElephant e where e.elephantId.firstName=?1";
        U.print( em.createQuery( query ).setParameter( 1, "hhh" ).getResultList() );


        //********************DERIVED IDENTIFIERS****************************************
        //basically cases when the pk of one table is made up entirely or partially of the pk from one or more other tables,
        //e.g. think back to rental result where it was order has lines which have individual items. line pk must reference order pk, etc.

        //shared primary key:
        //look at the AORMEmployeeHistory class and see how it is set up with its @MapsId to share the same pk as its AORMEmployee object.
        AORMEmployeeHistory employeeHistory = generalService.findEmployeeHistoryById( 1 );
        U.print( employeeHistory );

        //Dependant identifier, look how AORMDogWalk has the same pk as AORMDog, but with the additional walk number:
        List<AORMDogWalk> dogWalks = generalService.findAllDogWalks();
        U.print(dogWalks);    //note how on the AORMDogWalk object the AORMDogWalkId object contains the nested embedded AORMDogId object.


        //**************************ADVANCED MAPPING ELEMENTS***************************
        //DEMO of how to make an object read only:
        List<AORMReadOnly> aormReadOnlies = generalService.findAllAORMReadOnly();

        //NOTE: how trying to update stuff causes exceptions:
        for( AORMReadOnly aormReadOnly:aormReadOnlies )
        {
            try
            {
                generalService.updateAStringOnAormReadOnly( aormReadOnly.getId(), " some rubbish" );
            }
            catch( Exception e )
            {
                //NOTE; no exception seems to be thrown, appears that it just doesnt carry the update through to the db when you
                //try to update a column that has: updatable= false on it.
            }
        }

        AORMReadOnly aormReadOnly = new AORMReadOnly();
        aormReadOnly.setId( 999 );
        aormReadOnly.setaString( "qwqwq" );

        generalService.tryToPersistReadOnly( aormReadOnly );
        //TODO: note the above object is still getting its ID persisted to the db, even though it shouldn't be, investigate.


        //*******************SELF REFERENCING RELATIONSHIPS ****************************
        //Employee has a manager, that is also an employee:
        //employee 1 has a few minions:
        AORMEmployee manager = generalService.findEmployeeById( 1 );
        U.print( manager.getMinions()) ;

        //Employee 2 is managed by employee 1:
        AORMEmployee minion = generalService.findEmployeeById( 2 );
        assert( minion.getManager().getId() ==1 );
        assert( minion.getMinions().isEmpty());

        //************************Compound Join Columns********************************
        //note how there are two join columns used to join dog_bed to dog.
        AORMDogId dogId = new AORMDogId( "mavis", "hall" );
        AORMDog mavis2 = generalService.findDogById( dogId );
        U.print(mavis2.getDogBeds());

        //*********************ORPHAN REMOVAL**********************************
        AORMEmployee employee = generalService.createAnEmployeeWithSomeChildren();
        //check the db now, some emp_child1 rows should have been made.

        generalService.deleteEmployeeById( employee.getId() );
        //Check the db now, the emp_child1 rows should have gone, due to the orphanRemoval = true on the realtionship in the AORMEmployee object.
        //NOTE the hibernate SQL logs, see how it has done a delete for every child row.


        //*********************MULTIPLE TABLES, note how the AORMEmployee entity also is set to bring in info from the emp_additional_info table:
        AORMEmployee emp = generalService.findEmployeeById( 1 );
        U.print( emp.getAdditionalInfo1() ) ;
        U.print( emp.getAdditionalInfo2() ) ;
        //NOTE: you can have @SecondaryTables to use > 1 sec table.  Seel book page 299 for an example of a more complicated secondary table situation.

        //***********************INHERITANCE*************************************************

        //TODO: p.302, the @MappedSuperClass stuff ?

        //*****************USING SUPERCLASS TO HOLD TRANSIENT STATE
        //NOTE: how the AORMEmployee class extends CachedEntity, to allow for the time it was made, and therefore cached to be recorded:
        List<AORMEmployee> employeeList = generalService.getAllEmployees();

        for( AORMEmployee empo: employeeList )
        {
            U.print("Emp with id="+empo.getId()+" has been in the cache for "+empo.getCacheAgeMs()+" ms");
        }

        //**************SINGLE TABLE STRATEGY*********************************************
        //NOTE how the kid table holds boy specific data and girl specific data, and that is reflected in the object model
        //NOTE how here we can use a repository for the abstract super class (AORMKid) to find all instances of the abstract class
        List<AORMKid> kids = generalService.getAllKids();

        for( AORMKid kid: kids )
        {
            if( kid instanceof AORMBoy )
            {
                U.print("Boy found: boyspecificDataIs:"+ ( ( AORMBoy ) kid ).getBoySpecificField() );
            }
            else if ( kid instanceof AORMGirl )
            {
                U.print("Girl found: girlspecificDataIs:"+ ( ( AORMGirl ) kid ).getGirlSpecificField() );
            }
        }

        //NOTE how we can also have a repository specific to one of the concrete sub classes and make queries on that:
        List<AORMBoy>  boys = generalService.getAllBoys();
        U.print( boys );

        //NOTE: when you create a boy how the discriminator column is automatically filled in with 'boy'
        generalService.createBoyWithName( "John" );


        //************************JOINED STRATEGY *********************
        //NOTE: how the ineritance Hierachy of the AORMApple class uses joins between tables  to add in the concrete
        //sub class stuff, note the use of the AORMColouredFruit as an @MappedSuperClass to hold the color field, that is common to both the apple and the oranges.
        //but see how there is no table for the AORMColouredFruit object, the colour column is on both the apple and orange tables.

        List<AORMFruit> fruits = generalService.getAllFruits();

        for( AORMFruit fruit: fruits )
        {
            if( fruit instanceof AORMOrange )
            {
                  U.print("Orange found:"+fruit);
            }
            else if( fruit instanceof AORMApple )
            {
                U.print( "Apple found:"+fruit );
            }
        }

        //NOTE: again we can make queries based on just the concrete sub classes:
        List<AORMApple> apples  = generalService.getAllApples();
        U.print( apples );

        //NOTE: how when you make an apple, you get two rows, one in the 'parent' table and one in the joined in 'child' table:
        AORMApple apple = generalService.createAnApple();
        U.print( apple );


        //******************TABLE PER CONCRETE CLASS STRATEGY***************************
        //NOTE: the example in the book uses @AttributeOverrides to override column names in the sub classes.
        //THIS DID NOT WORK, see the java doc for @AttributeOverrides, implies it must be used with @MappedSuperclass, not @Entity on the superclass.
        //Hence the origin_country column is the same on both pizza and curry classes.
         List<AORMFood> foodList = generalService.getAllFood();
         U.print(foodList);


        //NOTE: can also do queries against concrete classes
        List<AORMCurry> curries = generalService.getAllCurry();
        U.print(curries);

        List<AORMPizza> pizzas = generalService.getAllPizza();
        U.print(pizzas) ;

        //********************MIXED INHERITANCE ****************************
        //NOTE: NOT looked into this as apparently it is not in the JPA specification.
    }

    private static void chapter9LearningCriteriaAPI( ClassPathXmlApplicationContext context )
    {
        EmployeeService es = context.getBean( EmployeeService.class );
        EntityManager em = context.getBean( EntityManager.class );
        CriteriaBuilder cb = em.getCriteriaBuilder();

        //simple initial example, same as 'select e from Employee e where e.firstName='john1'
        CriteriaQuery<Employee> criteriaQuery = cb.createQuery( Employee.class );            //note that how here it is setting Employee as the result class.
        Root<Employee> emp = criteriaQuery.from( Employee.class );    //is basically the 'from Employee e' bit
        //note that the root is the equivalent of the e in 'select e from Employee e, see below where it is used to make the equivalent of e.firstName
        criteriaQuery.select( emp )   // select e from Employee e
                .where( cb.equal( emp.get( "firstName" ), "john1" ) );   // where e.firstName = 'john1'

        List<Employee> emps = em.createQuery( criteriaQuery ).getResultList();
        U.print(emps);

        //////////////////////////////////////////////////////

        //DEMO USE OF DYNAMIC QUERY:
        //should return the lot:
        emps = es.getEmployeesDynamicQuery( null,null,null );
        U.print( emps );

        //should only give those with name john1
        emps = es.getEmployeesDynamicQuery( "john1",null,null );
        U.print( emps );

        //should only give those with last name smith1, i.e. none
        emps = es.getEmployeesDynamicQuery( null, "smith1",null );
        U.print( emps );

        //should give any that have last name starting with smith,but note how we've used equal in the criteria building, so doesn't.
        emps = es.getEmployeesDynamicQuery( null, "smith%",null );
        U.print( emps );

        //demos the join to the department table:
        emps = es.getEmployeesDynamicQuery( null, null, "sales" );
        U.print( emps );

        //DEMO OF HAVING MULTIPLE QUERY ROOTS:
        //E.G. for doing joins in the non 'JOIN' way.
        // e.g. in JPQL: select distinct d from Department d, Employee e where d = e.dpartment  - all departments with employees.
        // note the 'Department d, Employee e' multiple root.
        CriteriaQuery<Department> cq = cb.createQuery( Department.class );
        Root<Department> deptRoot = cq.from( Department.class );
        Root<Employee> empRoot = cq.from( Employee.class );
        cq.select( deptRoot ).distinct( true ).where(cb.equal(deptRoot, empRoot.get("department")));

        List<Department> deps = em.createQuery( cq ).getResultList();
        U.print(deps);

        //DEMO OF VARIOUS THINGS U CAN DO WITH THE SELECT CLAUSE:
        //e.g. to select a list of Strings:
        CriteriaQuery<String> cqs = cb.createQuery( String.class ); //note how the type in CriteriaQuery<T> must match that in the
        //cqs.select(emp.<T>.get("...");... the line above always specifies the result type
        Root<Employee> empRoot2 = cqs.from( Employee.class );
        cqs.select( empRoot2.<String>get("firstName") ); //i.e. is same as select e.firstName .....
        //i.e. so rather than selecting the whole Employee object as per the previous queries, we are explicitly picking the name:
        U.print(em.createQuery( cqs ).getResultList());

        //SELECTING MULTIPLE EXPRESSIONS:
        CriteriaQuery<Object[]>  cq2 = cb.createQuery( Object[].class );
        Root<Employee> empRoot3 = cq2.from( Employee.class );
        cq2.multiselect( empRoot3.get("firstName"), empRoot3.get("lastName") );
        //each member of the result list is an array, like: [firstName,lastName]
        U.print(em.createQuery( cq2 ).getResultList());
        //the result of the above query is a list of String[], each String[] contains the dual-headed result of the query.

        //USE OF A CONSTRUCTOR EXPRESSION:
        CriteriaQuery<EmployeeName> cq4 = cb.createQuery( EmployeeName.class );
        Root<Employee> empRoot4 = cq4.from(Employee.class);
        //it is clever enough to figure out how to build the result objs from the returned list of stuff, else u coud use cb.construct(...) , see book p.251
        cq4.multiselect( empRoot4.get("firstName"), empRoot4.get("lastName") );
        //Result list contains EmployeeName objects:
        U.print(em.createQuery( cq4 ).getResultList());

        //USE OF ALIASES:
        //NOTE how in this query we alias lastName as 'lastNameO' and then use lastNameO to work on the results set:
        CriteriaQuery<Tuple> tupleCriteriaQuery  = cb.createTupleQuery();
        Root<Employee> empRoot5 = tupleCriteriaQuery.from( Employee.class );
        tupleCriteriaQuery.multiselect( empRoot3.get( "firstName" ), empRoot3.get( "lastName" ).alias( "lastNameO" ) );
        TypedQuery<Tuple> tupleTypedQuery= em.createQuery( tupleCriteriaQuery );

        for(Tuple t: tupleTypedQuery.getResultList())
        {
            U.print(t.get("lastNameO"));//note how the Tuple is keyed by the alias of lastNameO;
        }

        //DEMO of joins:  select d.name, e,firstName from department d inner join employee e:
        CriteriaQuery<Object[]> departmentCriteriaQuery = cb.createQuery( Object[].class );
        Root<Department> departmentRoot = departmentCriteriaQuery.from( Department.class );
        Join<Department, Employee>  employeesJoin = departmentRoot.join( "employees" ); //defaults to inner, could spcify e.g.: JoinType.LEFT as a parameter.
        //NOTE: you would write departmentRoot.fetch("employees") to make it a fetch join, i.e. ...from Department d join fetch employee e:

        departmentCriteriaQuery.multiselect( departmentRoot.get("name"), employeesJoin.get("firstName") ) ;    //note how we use the join object to get at data that is brought in by the join:
        List<Object[]> aList = em.createQuery( departmentCriteriaQuery ).getResultList();

        for( Object[] sa: aList)
        {
            U.print(sa[0]+":"+sa[1]);
        }

        //DEMO of using a fairly complicate where clause:
        //select d.name, e.first name from department d join employee e where (d.name=sales and e.firstName = john1) or (d.name=marketing and e.firstName = john13)
        CriteriaQuery<Object[]> cq1 = cb.createQuery( Object[].class );
        Root<Department> departmentRoot2 = cq1.from( Department.class );
        Join<Department, Employee>  employeesJoin2 = departmentRoot2.join( "employees" );
        cq1.multiselect( departmentRoot.get( "name" ), employeesJoin.get( "firstName" ) ) ;    //note how we use the join object to get at data that is brought in by the join:

        Predicate predicate1 = cb.equal( departmentRoot2.get( "name" ),"sales" );
        Predicate predicate2 = cb.equal( employeesJoin2.get( "firstName" ),"john1" );

        Predicate firstAnd = cb.and( predicate1, predicate2 );

        Predicate predicate3 = cb.equal( departmentRoot2.get( "name" ),"marketing" );
        Predicate predicate4 = cb.equal( employeesJoin2.get( "firstName" ),"john13" );

        Predicate secondAnd = cb.and( predicate3, predicate4 );

        cq1.where( cb.or( firstAnd, secondAnd ) );

        List<Object[]> aList2 = em.createQuery( cq1 ).getResultList();

        for( Object[] sa: aList2)
        {
            U.print(sa[0]+":"+sa[1]);
        }

        //NOTE: you can put loads of different functions and operators in the where clause, e.g. length(....) see book page 254-256.

        //TODO: DEMO nested queries

        //Use of IN
        //e.g. select e from Employee e where e.name in( "john1","john12")
        CriteriaQuery<Employee> cqIn = cb.createQuery( Employee.class );
        Root<Employee> empRootIn = cqIn.from( Employee.class );
        cqIn.select( emp ).where(emp.get("firstName").in("john1","john12"));   //NOTE the use of a comma separated list for the IN bit.

        U.print( em.createQuery( cqIn ).getResultList());

        //DEMO OF Case expression:  e.g.
        //"SELECT case " +
        //        "       when e.employeeType ='CONTRACT_EMPLOYEE' then 'CONTRACTOR' " +
         //       "       when e.employeeType is null then 'not known' ELSE 'need to have an else to make it work' " +
         //       "       end" +
         //       "       from Employee e";

        CriteriaQuery<String> cqCase = cb.createQuery( String.class );
        Root<Employee> empRootCase = cqCase.from( Employee.class );

        cqCase.select(

                cb.<String,String>selectCase( empRootCase.<String>get( "employeeType") )
                //note the <String,String> bit paramerising the method, is <C,R> C= type of thing being tested, R = type of result.
                        .when( "CONTRACT_EMPLOYEE", "CONTRACTOR" )
                        .when("null", "not known")
                        .otherwise( "result of else in case" )
        );

      // U.print( em.createQuery( cqCase ).getResultList());   // -- giving an exception for some stupid reason Hibernate bug?

        //DEMO use of ORDER BY:
//        SELECT d.name, e.name
//        FROM Employee e JOIN e.dept d
//        ORDER BY d.name DESC, e.name

        CriteriaQuery<Object[]> orderByQuery = cb.createQuery( Object[].class);
        Root<Employee> empRoot9 = orderByQuery.from(Employee.class);
        Join<Employee,Department> dept = empRoot9.join("department");
        orderByQuery.multiselect( dept.get( "name" ), empRoot9.get( "firstName" ) );
        orderByQuery.orderBy( cb.desc( dept.get( "name" ) ),
                              cb.asc( empRoot9.get( "firstName" ) ) );

        List<Object[]> stringarrays = em.createQuery( orderByQuery ).getResultList() ;

        for( Object[] sa: stringarrays )
        {
           U.print(sa[0]+","+sa[1]);
        }

        //DEMO USE OF GROUP BY AND HAVING:
//        SELECT d, COUNT(e)
//            FROM Department d JOIN d.employees e
//        GROUP BY d
//        HAVING COUNT(e) >= 2

        CriteriaQuery<Object[]> gbhQuery = cb.createQuery(Object[].class);
        Root<Department> deptRoot5 = gbhQuery.from( Department.class);
        Join<Department,Employee> deptEmpJoin = deptRoot5.join("employees");
        gbhQuery.multiselect( deptRoot5, cb.count( deptRoot5 ) )
                .groupBy(deptRoot5)
                .having(cb.ge(cb.count(deptEmpJoin),2));

        List<Object[]> gbhResults = em.createQuery( gbhQuery ).getResultList();

        for( Object[] objArr: gbhResults )
        {
            U.print( objArr[0]+","+objArr[1]);
        }

        //TODO: stuff from p.265 on? Stronlgy type queries and canonical metamodel?  Worth looking at?




    }

    private static void chapter8LearningJPQL( ClassPathXmlApplicationContext context )
    {
        EntityManager em = context.getBean( EntityManager.class );
        List<Employee> emps;
        //DEMO use of path expressions, AND also the order by clause.
        String query = "SELECT e from Employee e where e.department.name='sales' order by e.firstName";

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
        //FIND all emps that are on that project:
        query = "select e from Employee e where :project member of e.projects";
        Query q = em.createQuery( query );
        q.setParameter( "project", pj1 );

        emps = q.getResultList();

        //DEMO USE OF LITERALS
        //see book p.229, example of using a date literal
        //couldn't get this to work for some reason;;;;
//        query = "select e from Employee e where e.startDate =:eDate";
//        q= em.createQuery( query );
//        q.setParameter( "eDate", "{d {'2000-12-03'}" );
//        emps = em.createQuery( query ).getResultList();
//        U.print( emps );

        //DEMO USE OF ENUM IN QUERY, note how u need the fq class name:
        query = "select e from Employee e where e.employeeType = com.hedges.jpalearning.model.EmployeeType.CONTRACT_EMPLOYEE";
        emps = em.createQuery( query ).getResultList();
        U.print( emps );

        //DEMO USE OF ANY, ALL AND SOME:
        query = "select e from Employee e where e.salary < ALL (SELECT s.salary from Salary s)";
        emps = em.createQuery( query ).getResultList();

        query = "select e from Employee e where e.salary < ANY (SELECT s.salary from Salary s)";
        emps = em.createQuery( query ).getResultList();

        //NOTE: SOME is an alias for any, which is strange, as some implies > 1.....
        query = "select e from Employee e where e.salary < SOME (SELECT s.salary from Salary s)";
        emps = em.createQuery( query ).getResultList();

        //DEMO USE OF functions:
        //NOTE there are lots of functions, see p.230.
        query = "select e from Employee e where CONCAT(e.firstName,e.lastName) = 'john24smith24' ";
        emps = em.createQuery( query ).getResultList();

        //CASE EXPRESSION:
        query = "SELECT case " +
                "       when e.employeeType ='CONTRACT_EMPLOYEE' then 'CONTRACTOR' " +
                "       when e.employeeType is null then 'not known' ELSE 'need to have an else to make it work' " +
                "       end" +
                "       from Employee e";

        List<String> resultsOfCase  = em.createQuery( query ).getResultList();
        U.print( resultsOfCase );

        //COALESCE EXPRESSION, basically selects the first non null value in the list:
        query = "select coalesce(e.dogName,e.id) from Employee e";
        List<Object> resultsOfCoalesce  = em.createQuery( query ).getResultList();
        U.print( resultsOfCoalesce );

        //NULLIF EXPRESSION:
        //basically if they are both the same, it returns null, else the first is picked:
        query = "select nullif(e.firstName,'john1') from Employee e";
        List<Object> res  = em.createQuery( query ).getResultList();

        U.print(res);

        //EXAMPLE OF AGGREGATE STUFF..basically you can have avg,sum, max, min, count, group by and having, same as with SQL.
        query = "select AVG(e.salary) from Employee e";
        Double resDouble = em.createQuery( query, Double.class ).getSingleResult();

        U.print( resDouble );
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
        U.print( "employees on first page" + employees );

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

        /** NOTE: U can have a bulk delete as well, just like what you can do with normal SQL   **/




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
