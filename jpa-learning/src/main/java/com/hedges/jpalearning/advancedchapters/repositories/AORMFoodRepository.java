package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMFood;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 20/02/16
 */
public interface AORMFoodRepository extends JpaRepository<AORMFood, Integer>
{
}
