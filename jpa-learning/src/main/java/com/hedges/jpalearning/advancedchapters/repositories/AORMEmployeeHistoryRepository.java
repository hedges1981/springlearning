package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMEmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMEmployeeHistoryRepository extends JpaRepository<AORMEmployeeHistory, Integer >
{
}
