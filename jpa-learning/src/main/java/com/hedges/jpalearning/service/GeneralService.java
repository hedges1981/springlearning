package com.hedges.jpalearning.service;

import com.hedges.jpalearning.model.Department;
import com.hedges.jpalearning.model.ParkingSpace;
import com.hedges.jpalearning.repositories.ParkingSpaceRepository;
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

    public ParkingSpace getParkingSpaceById( int id )
    {
        return parkingSpaceRepository.findOne( id );
    }

    public Department getDepartmentById( int id )
    {
        Department d= departmentRepository.findOne( id );
        d.getEmployees();

        return d;
    }


}
