package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/10/15
 */
public interface AORMEmployeeRepository extends JpaRepository<AORMEmployee, Integer>
{

}
