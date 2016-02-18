package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMApple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 18/02/16
 */
public interface AORMAppleRepository extends JpaRepository<AORMApple, Integer>
{
}
