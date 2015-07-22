package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;

import java.util.List;

/**
 * Created by rowland-hall on 22/07/15
 */
public interface PersonService
{
    void CreatePerson( Person p );

    List<Person> getAllPersons();

    List<Person> getPersonsByCarMake( String carMake );

    Person getPerson( int id );

    void updatePerson( Person person );

    void deletePerson( int id );
}
