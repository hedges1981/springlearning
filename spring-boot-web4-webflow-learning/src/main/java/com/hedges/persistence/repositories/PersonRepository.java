package com.hedges.persistence.repositories;

import com.hedges.persistence.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 08/01/16
 */
public interface PersonRepository extends JpaRepository<Person,Integer>
{
}
