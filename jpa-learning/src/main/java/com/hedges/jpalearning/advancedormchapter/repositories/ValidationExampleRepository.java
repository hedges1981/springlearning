package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.ValidationExample;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 23/02/16
 */
public interface ValidationExampleRepository  extends JpaRepository<ValidationExample, Integer >
{
}
