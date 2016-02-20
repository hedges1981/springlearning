package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMFood;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 20/02/16
 */
public interface AORMFoodRepository extends JpaRepository<AORMFood, Integer>
{
}
