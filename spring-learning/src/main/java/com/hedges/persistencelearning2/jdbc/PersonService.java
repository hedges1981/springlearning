package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;

import java.util.List;
import java.util.Map;

/**
 * Created by rowland-hall on 22/07/15
 */
public interface PersonService
{
    int createPerson( Person p );

    List<Person> getAllPersons();

    void processAllPersonsWithRowCallBackHandler();

    String getCarMakeByIdDemosResultSetExtractor( int id );

    List<Person> getPersonsByCarMake( String carMake );

    Person getPerson( int id );

    Map<String,Object> getPersonAsAMap( int id );

    void updatePersonUsingTransactionTemplate( Person person );

    int createPersonUsingTransactionTemplate( Person person );

    void runQueryThrowsException();
}
