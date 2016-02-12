package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMReadOnly;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMReadOnlyRepository extends JpaRepository<AORMReadOnly, Integer>
{
}
