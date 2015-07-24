package com.hedges.persistencelearning2.hibernateorm;

import com.hedges.persistencelearning2.model.Person;
import com.hedges.springlearning.U;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by rowland-hall on 24/07/15
 */
public class HibernateOrmLearningMain
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "hibernateOrmLearningContext.xml" );

        PersonServiceOrm personServiceOrm = context.getBean( PersonServiceOrm.class );

        //DEMO creating a new Person:
        Person person =  new Person();
        person.setName( "Terry" );
        person.setSex( "M" );
        person.setDateOfBirth( new Date() );

        personServiceOrm.createPerson( person );

        U.print( "Person created, id automatically generated is: "+person.getId() );

        //DEMO finding a person by ID:
        Person person2 = personServiceOrm.getPersonById( 1 );
        U.print( person2 );

        //DEMO how an exception rolls back a txn:
        Person person3 =  new Person();
        person3.setName( "Shouldn't get committed" );
        person3.setSex( "M" );
        person3.setDateOfBirth( new Date() );

        try
        {
            personServiceOrm.createPersonExceptionCausesRollback( person3 );
        }
        catch( Exception e )
        {
            U.print( "Exception should have caused txn to rollback" );
        }

        //DEMO the rollbackFor attribute of @Transactional
        try
        {
            person3.setName( "throw RollbackForException" );
            personServiceOrm.createPersonDemosRollbackFor( person3 );
        }
        catch( RollbackForException e )
        {
            U.print("ROllbackForException caught, txn should have been rolled back");
        }

        try
        {
            person3.setName( "throw RuntimeException" );
            personServiceOrm.createPersonDemosRollbackFor( person3 );
        }
        catch( RuntimeException e )
        {
            U.print("ROllbackForException caught, txn should not have been rolled back due to the explicit rollbackFor on the method called");
        }




    }
}
