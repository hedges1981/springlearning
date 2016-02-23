package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMDogWalk;
import com.hedges.jpalearning.advancedchapters.model.AORMDogWalkId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMDogWalkRepository extends JpaRepository<AORMDogWalk, AORMDogWalkId>
{
}
