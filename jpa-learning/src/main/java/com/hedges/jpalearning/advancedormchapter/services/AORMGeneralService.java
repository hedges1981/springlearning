package com.hedges.jpalearning.advancedormchapter.services;

import com.hedges.jpalearning.advancedormchapter.model.*;
import com.hedges.jpalearning.advancedormchapter.repositories.*;
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
public class AORMGeneralService
{
    @Autowired
    private AORMEmployeeRepository employeeRepository;
    @Autowired
    private AORMPhoneRepository phoneRepository;
    @Autowired
    private AORMCustomerRepository customerRepository;
    @Autowired
    private AORMDogRepository dogRepository;
    @Autowired
    private AORMElephantRepository elephantRepository;

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

    public List<AORMPhone> getAllPhones()
    {
        List<AORMPhone> phones = phoneRepository.findAll();

        //shit necessary to get round lazy fetching:
        for( AORMPhone phone: phones )
        {
            phone.getEmployees().size();
        }

        return phones;
    }

    public List<AORMCustomer> getAllCustomers()
    {
        List<AORMCustomer> customers = customerRepository.findAll();
        //shit necessary to get round lazy fetching:
        for( AORMCustomer customer : customers )
        {
            customer.getContactInfo().getPhones().size();
        }

        return customers;
    }

    public AORMDog findDogById( AORMDogId dogId )
    {
        return dogRepository.findOne( dogId );
    }

    public AORMElephant findElephantById( AORMElephantId elephantId )
    {
        return elephantRepository.findOne( elephantId );
    }



}
