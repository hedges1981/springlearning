package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rowland-hall on 12/10/15
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{
    /**
     * This will execute the getAllEmployees named query
     */
    List<Employee>  getAllEmployees();

    /**
        note the use of @Param to bind the name parameter to the query.
     */
    List<Employee>  getAllEmployeesByName( @Param("name")String name );

}
