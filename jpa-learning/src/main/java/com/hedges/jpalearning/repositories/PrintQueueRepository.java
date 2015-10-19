package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.PrintQueue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 19/10/15
 */
public interface PrintQueueRepository extends JpaRepository<PrintQueue,Integer>
{
}
