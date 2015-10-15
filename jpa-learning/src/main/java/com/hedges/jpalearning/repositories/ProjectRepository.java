package com.hedges.jpalearning.repositories;

import com.hedges.jpalearning.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 15/10/15
 */
public interface ProjectRepository extends JpaRepository<Project, Integer>
{
}
