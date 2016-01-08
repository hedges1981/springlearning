package com.hedges.services.impl;

import com.hedges.persistence.model.Person;
import com.hedges.persistence.repositories.PersonRepository;
import com.hedges.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rowland-hall on 08/01/16
 */
@Transactional
public class PersonServiceImpl implements PersonService
{
    public PersonServiceImpl()
    {
        int x=0;
    }

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAllPersons()
    {
        return personRepository.findAll();
    }

    @Override
    public void addPerson( String name )
    {
        Person p = new Person();
        p.setName( name );
        personRepository.save( p );

        if("hedges".equals( name ))
        {
            throw new RuntimeException("name not allowed");
        }
    }
}
