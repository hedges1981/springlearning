package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMEmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMEmployeeHistoryRepository extends JpaRepository<AORMEmployeeHistory, Integer >
{
}
