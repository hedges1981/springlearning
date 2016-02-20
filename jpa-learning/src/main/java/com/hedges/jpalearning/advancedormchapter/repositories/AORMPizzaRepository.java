package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMPizza;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 20/02/16
 */
public interface AORMPizzaRepository extends JpaRepository<AORMPizza, Integer>
{
}
