package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 14/10/15
 */
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer>
{
}
