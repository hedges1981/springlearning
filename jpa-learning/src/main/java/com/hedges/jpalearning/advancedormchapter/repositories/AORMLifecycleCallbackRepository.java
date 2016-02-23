package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMLifecycleCallbackDemo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 23/02/16
 */
public interface AORMLifecycleCallbackRepository extends JpaRepository<AORMLifecycleCallbackDemo, Integer>
{
}
