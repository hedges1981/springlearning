package com.hedges.jpalearning.advancedchapters.repositories;

import com.hedges.jpalearning.advancedchapters.model.AORMKid;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 18/02/16
 */
public interface AORMKidRepository  extends JpaRepository<AORMKid,Integer>
{
}
