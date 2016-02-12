package com.hedges.jpalearning.advancedormchapter.repositories;

import com.hedges.jpalearning.advancedormchapter.model.AORMCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 12/02/16
 */
public interface AORMCustomerRepository extends JpaRepository<AORMCustomer, Integer>
{
}
