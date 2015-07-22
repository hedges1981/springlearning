package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;
import com.hedges.springlearning.U;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

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
    }
}
