package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/10/15
 */
public interface AORMEmployeeRepository extends JpaRepository<AORMEmployee, Integer>
{

}
