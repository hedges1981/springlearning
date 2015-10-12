package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/10/15
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{
}
