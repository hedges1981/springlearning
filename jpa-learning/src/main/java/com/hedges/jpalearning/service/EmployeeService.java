package com.hedges.jpalearning.service;

import com.hedges.jpalearning.U;
import com.hedges.jpalearning.model.Department;
import com.hedges.jpalearning.model.Employee;
import com.hedges.jpalearning.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
    @Autowired
    private EntityManager entityManager;

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

    public List<Employee> getAllEmployees_NQ()
    {
        return employeeRepository.getAllEmployees();
    }


    public List<Employee> getAllEmployeesByName_NQ( String name )
    {
        return employeeRepository.getAllEmployeesByName( name );
    }

    public void demoABUlkUpdate( String str )
    {
        String updateQueryStr = "UPDATE Employee e set e.comments = '"+str+"'";//" where e.employeeType='CONTRACT_EMPLOYEE'";

        entityManager.createQuery( updateQueryStr ).executeUpdate();
    }

    /**
     * Only includes the parameters in the query if they are not null, typical example of this would be a search form where you can search
     * based on different criteria.
     *
     * What a load of fucking hassle just to do something dead simple.
     */
    public List<Employee> getEmployeesDynamicQuery( String firstName, String lastName, String departmentName )
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> c = cb.createQuery( Employee.class );
        Root<Employee> emp = c.from( Employee.class );   //NOTE that the query root is efectively the thing to the right of from,
        //e.g. here: 'from Employee e'

        c.select( emp );
        c.distinct( true );

        Join<Employee, Department> deptJoin = emp.join("department", JoinType.LEFT);

        List<Predicate> criteria = new ArrayList<Predicate>();

        if( firstName != null )
        {
            ParameterExpression<String>  p =
                    cb.parameter( String.class, "firstName" );
            criteria.add(cb.equal(emp.get("firstName"),p));  //note that firstName here effectively refers to a named parameter, see below for further:
        }

        if( lastName != null )
        {
            ParameterExpression<String>  p =
                    cb.parameter( String.class, "lastName" );
            criteria.add(cb.equal(emp.get("lastName"),p));
        }

        if( departmentName != null )
        {
            ParameterExpression<String>  p =
                    cb.parameter( String.class, "departmentName" );

            //NOTE: this bit here demos the use of a path expression.
            criteria.add(cb.equal(emp.get("department").get("name"),p));
        }

        //now put the predicates into the where clause:
        c.where( cb.and(criteria.toArray( new Predicate[criteria.size()] )));

        //now set the parameters:
        TypedQuery<Employee> q = entityManager.createQuery( c );

        if( firstName !=null)
        {
            q.setParameter( "firstName",firstName );
        }

        if( lastName !=null)
        {
            q.setParameter( "lastName",lastName );
        }

        if( departmentName !=null)
        {
            q.setParameter( "departmentName",departmentName );
        }

        return q.getResultList();
    }

}
