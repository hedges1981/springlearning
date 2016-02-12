package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMDog;
import com.hedges.jpalearning.advancedormchapter.model.AORMDogId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMDogRepository  extends JpaRepository<AORMDog, AORMDogId>
{
}
