package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMDogWalk;
import com.hedges.jpalearning.advancedormchapter.model.AORMDogWalkId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMDogWalkRepository extends JpaRepository<AORMDogWalk, AORMDogWalkId>
{
}
