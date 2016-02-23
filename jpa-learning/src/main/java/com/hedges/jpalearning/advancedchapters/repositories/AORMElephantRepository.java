package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMElephant;
import com.hedges.jpalearning.advancedchapters.model.AORMElephantId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMElephantRepository extends JpaRepository<AORMElephant, AORMElephantId>
{
}
