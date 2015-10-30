package com.hedges.jpalearning.service;

import com.hedges.jpalearning.U;
import com.hedges.jpalearning.model.Employee;
import com.hedges.jpalearning.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


/**
 * Created by rowland-hall on 12/10/15
 */
@Service
@Transactional
public class EmployeeService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    public Employee findById( Integer id )
    {
        Employee e= employeeRepository.findOne( id );
        U.print( e.getPhones() );
        U.print(e.getHolidays()); //note the reason why we have U.print instead of just calling the getter is because
        //hibernate doesn't actually do the fetching until some method is called on the thing, i.e. just getting it is not enough.
        e.getRelatives().size();

        return e;
    }

    public void saveEmployee( Employee e )
    {
        //presumably this is the same as calling entityManager.persist, then flush. The committing of the outer wrapping tx causes it
        //to eventually end up in the db.
        employeeRepository.saveAndFlush( e );
    }

    public void testUpdateEmployee()
    {
        //Notice how this method causes the employee changes to get saved, even though no '.save' is explicitly called,
        // the committing of the txn causes the changes to the persistence context to get flushed out and persisted.

        //this is because the Employee object is a managed object, i.e. is part of the persistence context, state changes in
        //it are detected and flushed to the db when the txn commits.
        Employee e= employeeRepository.findOne( 1 );
        e.setDogName( UUID.randomUUID().toString() );
    }

    public void removeEmployee( Employee e )
    {
        // this is the same as causing an EntityManager.remove(..);
        employeeRepository.delete( e );
    }

    public void removeEmployeeById( int id )
    {
        // this is the same as causing an EntityManager.remove(..);
        employeeRepository.delete( id );
    }

}
