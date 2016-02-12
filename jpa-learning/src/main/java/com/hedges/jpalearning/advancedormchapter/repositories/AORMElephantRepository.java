package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMElephant;
import com.hedges.jpalearning.advancedormchapter.model.AORMElephantId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMElephantRepository extends JpaRepository<AORMElephant, AORMElephantId>
{
}
