package com.hedges.jpalearning.advancedormchapter.services;

import com.hedges.jpalearning.advancedormchapter.model.AORMEmployee;
import com.hedges.jpalearning.advancedormchapter.repositories.AORMEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by rowland-hall on 11/02/16
 */
@Transactional
@Service
public class AORMEmployeeService
{
    @Autowired
    private AORMEmployeeRepository employeeRepository;
    @Autowired
    private EntityManager entityManager;

    public List<AORMEmployee> getAllEmployees()
    {
        List<AORMEmployee> emps = employeeRepository.findAll();


        //shit necessary for getting round the lazy fetching:
        for( AORMEmployee emp:emps)
        {
            if(!emp.getContactInfo().getPhones().isEmpty())
            {
                emp.getContactInfo().getPhones().iterator().next();
            }
        }

        return emps;
    }
}
