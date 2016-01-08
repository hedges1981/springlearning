package com.hedges.services;

import com.hedges.persistence.model.Person;

import java.util.List;

/**
 * Created by rowland-hall on 08/01/16
 */
public interface PersonService
{
    List<Person> findAllPersons();

    void addPerson( String name);
}
