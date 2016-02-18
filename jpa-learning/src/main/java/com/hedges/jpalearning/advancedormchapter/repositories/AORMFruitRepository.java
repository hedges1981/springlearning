package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMFruit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 18/02/16
 */
public interface AORMFruitRepository extends JpaRepository<AORMFruit, Integer>
{

}
