package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMDog;
import com.hedges.jpalearning.advancedchapters.model.AORMDogId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMDogRepository  extends JpaRepository<AORMDog, AORMDogId>
{
}
