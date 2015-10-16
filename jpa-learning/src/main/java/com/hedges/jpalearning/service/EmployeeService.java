package com.hedges.jpalearning.service;

import com.hedges.jpalearning.U;
import com.hedges.jpalearning.model.Employee;
import com.hedges.jpalearning.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

        return e;
    }

    public void saveEmployeeAndFlush( Employee e )
    {
        employeeRepository.saveAndFlush( e );
    }

}
