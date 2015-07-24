package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;
import com.hedges.springlearning.U;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rowland-hall on 22/07/15
 */
public class JdbcLearningMain
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "jdbcLearningContext.xml" );

        //demo creation of a person:
        PersonService personService = context.getBean( PersonService.class );

        Person person = new Person();
        person.setName( "John" );
        person.setSex( "M" );
        person.setDateOfBirth( new Date() );
        person.setCarMake( "VW" );

        personService.CreatePerson( person );
        U.print( "Person created with ID= "+person.getId() );

        //demo find all persons:
        List<Person> allPersons = personService.getAllPersons();
        U.print(" All persons found: "+ allPersons );

        //demo find by car make:
        List<Person> personsVw = personService.getPersonsByCarMake( "VW" );
        U.print("Persons with car VW: "+ personsVw );

        //demos DataAccessException
        try
        {
            personService.runQueryThrowsException();
        }
        catch(DataAccessException e)
        {
            //Spring wraps any exceptions that come out of the JDBC stuff in a DataAccessException or subclass thereof.
            U.print("DataAccessException caught:"+e);
        }

        //Demo queryForObject:
        Person person1 = personService.getPerson( 1 );
        U.print("Person with ID=1: "+ person1);

        //Demos query for Map:
        Map<String,Object > personAsAMap = personService.getPersonAsAMap( 1 );
        U.print("Person as a map:"+personAsAMap );

        //Demos RowCallBackHandler, note the console output:
        personService.processAllPersonsWithRowCallBackHandler();

        //Demos ResultSetExtractor:
        U.print("Car make of person with ID=1 found using ResultSetExtractor= "+ personService.getCarMakeByIdDemosResultSetExtractor( 1 ));

    }
}
