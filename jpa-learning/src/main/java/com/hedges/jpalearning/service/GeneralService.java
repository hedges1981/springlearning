package com.hedges.jpalearning.service;

import com.hedges.jpalearning.model.*;
import com.hedges.jpalearning.repositories.ParkingSpaceRepository;
import com.hedges.jpalearning.repositories.PrintQueueRepository;
import com.hedges.jpalearning.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public PrintQueue getPrintQueueByName( String name)
    {
        PrintQueue pq =  printQueueRepository.findOne( name );
        pq.getPrintJobs().size();

        return pq;
    }

    public void savePrintQueue( PrintQueue pq )
    {
        printQueueRepository.saveAndFlush( pq );
    }

    public void addPrintJobToPrintQueue( PrintJob pj, PrintQueue pq )
    {
        pj.setPrintQueue( pq );

        List<PrintJob> pjs = pq.getPrintJobs();

        int printOrderToSet = pjs.get( pjs.size()-1).getPrintOrder()+1;

        pj.setPrintOrder( printOrderToSet );

        pj.setPrintQueue( pq ); //THIS line is necessary to get the link to a PQ from a PJ, jpa doesn't automatically read the relationship and do the setting.

        pjs.add( pj);

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
        d.getEmployeesByDeskId().size();
        d.getEmployeesById().size();

        return d;
    }

    public Project getProjectById( int id )
    {
        return projectRepository.findOne( id );
    }
}
