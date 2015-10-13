package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 13/10/15
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer>
{
}
