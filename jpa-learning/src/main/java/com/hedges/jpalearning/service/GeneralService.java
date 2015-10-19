package com.hedges.jpalearning.service;

import com.hedges.jpalearning.model.Department;
import com.hedges.jpalearning.model.ParkingSpace;
import com.hedges.jpalearning.model.PrintQueue;
import com.hedges.jpalearning.model.Project;
import com.hedges.jpalearning.repositories.ParkingSpaceRepository;
import com.hedges.jpalearning.repositories.PrintQueueRepository;
import com.hedges.jpalearning.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rowland-hall on 14/10/15
 */
@Service
@Transactional
public class GeneralService
{
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired //Note the nice way this can be done, no need to extend the interface
    private JpaRepository<Department,Integer> departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PrintQueueRepository printQueueRepository;

    public PrintQueue getPrintQueueById( int id)
    {
        return printQueueRepository.findOne( id );
    }

    public void savePrintQueue( PrintQueue pq )
    {
        printQueueRepository.saveAndFlush( pq );
    }

    public ParkingSpace getParkingSpaceById( int id )
    {
        return parkingSpaceRepository.findOne( id );
    }

    public Department getDepartmentById( int id )
    {
        Department d= departmentRepository.findOne( id );
        d.getEmployees().size();

        return d;
    }

    public Project getProjectById( int id )
    {
        return projectRepository.findOne( id );
    }
}
