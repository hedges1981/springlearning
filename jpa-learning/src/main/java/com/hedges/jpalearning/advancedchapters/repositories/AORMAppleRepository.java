package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMApple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 18/02/16
 */
public interface AORMAppleRepository extends JpaRepository<AORMApple, Integer>
{
}
