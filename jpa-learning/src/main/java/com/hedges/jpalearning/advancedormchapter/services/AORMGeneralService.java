package com.hedges.jpalearning.advancedormchapter.services;

import com.hedges.jpalearning.advancedormchapter.model.*;
import com.hedges.jpalearning.advancedormchapter.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private AORMEmployeeHistoryRepository employeeHistoryRepository;
    @Autowired
    private AORMDogWalkRepository dogWalkRepository;
    @Autowired
    private AORMReadOnlyRepository aormReadOnlyRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AORMKidRepository kidRepository;
    @Autowired
    private AORMBoyRepository boyRepository;
    @Autowired
    private AORMFruitRepository fruitRepository;
    @Autowired
    private AORMAppleRepository appleRepository;
    @Autowired
    private AORMOrangeRepository orangeRepository;
    @Autowired
    private AORMFoodRepository foodRepository;
    @Autowired
    private AORMCurryRepository curryRepository;
    @Autowired
    private AORMPizzaRepository pizzaRepository;
    @Autowired
    private AORMLifecycleCallbackRepository lifecycleCallbackRepository;
    @Autowired
    private ValidationExampleRepository validationExampleRepository;

    public void doAValidUpdateOnAPreExistingInvalidEntity()
    {
        ValidationExample valex = validationExampleRepository.findOne( 5 );
        //valex has an invalid 'lengthLessThan5String' field already there in the db.
        //lets not change that but change something else, so it will try to update the object:
        valex.setNotNullString( UUID.randomUUID().toString() );
    }

    public void saveValidationExample( ValidationExample validationExample )
    {
        validationExampleRepository.save( validationExample );
    }

    public ValidationExample findValidationExampleById( int id )
    {
        return validationExampleRepository.findOne( id );
    }

    public void createLifeCycleCallbackDemoWithString( String s )
    {
        AORMLifecycleCallbackDemo lifecycleCallbackDemo = new AORMLifecycleCallbackDemo();
        lifecycleCallbackDemo.setString( s );
        lifecycleCallbackRepository.save( lifecycleCallbackDemo );
    }

    public void updateAllLifecycleCallbackDemos()
    {
        List<AORMLifecycleCallbackDemo> list = lifecycleCallbackRepository.findAll();

        for( AORMLifecycleCallbackDemo lifecycleCallbackDemo: list )
        {
            lifecycleCallbackDemo.setString( UUID.randomUUID().toString() );
        }
    }

    public void create10LifeCycleCallBackDemos()
    {
        for( int i = 0; i<10; i++ )
        {
            AORMLifecycleCallbackDemo lifecycleCallbackDemo = new AORMLifecycleCallbackDemo();
            lifecycleCallbackDemo.setString( UUID.randomUUID().toString() );
            lifecycleCallbackRepository.save( lifecycleCallbackDemo );
        }
    }

    public void deleteAllLifecycleCallBackDemos()
    {
        List<AORMLifecycleCallbackDemo> list = lifecycleCallbackRepository.findAll();

        for( AORMLifecycleCallbackDemo lifecycleCallbackDemo : list )
        {
            lifecycleCallbackRepository.delete( lifecycleCallbackDemo );
        }
    }

    public void executeNativeSql( String sql )
    {
        entityManager.createNativeQuery( sql ).executeUpdate(); //note how execute update is used, this would be used for both an insert and an update.
    }

    public List<AORMCurry> getAllCurry()
    {
        return curryRepository.findAll();
    }

    public List<AORMPizza> getAllPizza()
    {
        return pizzaRepository.findAll();
    }

    public List<AORMFood> getAllFood()
    {
        return foodRepository.findAll();
    }

    public List<AORMOrange> getAllOranges()
    {
        return orangeRepository.findAll();
    }

    public AORMApple createAnApple()
    {
        //need to get the max id of the fruits:

        List<AORMFruit> fruits = getAllFruits();

        int maxId =0;

        for( AORMFruit fruit: fruits )
        {
            if( fruit.getId() > maxId )
            {
                maxId = fruit.getId();
            }
        }

        int newId = maxId + 1;

        AORMApple apple = new AORMApple();
        apple.setAppleType( "grannySmith" );
        apple.setColour( "green" );
        apple.setId( newId );
        apple.setWeight( 300 );

        appleRepository.save( apple );

        return apple;
    }


    public List<AORMFruit> getAllFruits()
    {
        return fruitRepository.findAll();
    }

    public List<AORMApple> getAllApples()
    {
        return appleRepository.findAll();
    }

    public List<AORMKid> getAllKids()
    {
        return kidRepository.findAll();
    }

    public List<AORMBoy> getAllBoys()
    {
        return boyRepository.findAll();
    }

    public void createBoyWithName( String name )
    {
        AORMBoy boy = new AORMBoy();
        boy.setName( name );
        boyRepository.save( boy );
    }

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

    public AORMEmployee createAnEmployeeWithSomeChildren()
    {
        AORMEmployee employee = new AORMEmployee();

        List<AORMEmpChild1>  children = new ArrayList<AORMEmpChild1>();

        for( int i =0; i<10; i++ )
        {
            AORMEmpChild1 child = new AORMEmpChild1();
            child.setEmployee( employee );
            children.add( child );
        }

        employee.setChild1s( children );

        employeeRepository.save( employee );

        return employee;
    }

    public void deleteEmployeeById( int id )
    {
        employeeRepository.delete( id );
    }

    public AORMEmployee findEmployeeById( int id )
    {
        AORMEmployee emp = employeeRepository.findOne( id );
        //shit necessary for getting round the lazy fetching:
        if(emp.getManager() != null) emp.getManager().getId();
        emp.getMinions().size();

        return emp;
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

    @Transactional(readOnly = true)    // had to put the read only on for some reason, as something about this caused an hibernate identifier of an instance of was altered from exception,
    //probably due to the fact that the dog-bed join columns make up the ID of the dog object.
    public AORMDog findDogById( AORMDogId dogId )
    {
        AORMDog dog= dogRepository.findOne( dogId );
        //shit necessary to get round lazy fetching:
        dog.getDogBeds().size();

        return dog;
    }

    public AORMElephant findElephantById( AORMElephantId elephantId )
    {
        return elephantRepository.findOne( elephantId );
    }

    public AORMEmployeeHistory findEmployeeHistoryById( int id )
    {
        return employeeHistoryRepository.findOne( id );
    }

    public List<AORMDogWalk> findAllDogWalks()
    {
        return dogWalkRepository.findAll();
    }

    public List<AORMReadOnly> findAllAORMReadOnly()
    {
        return aormReadOnlyRepository.findAll();
    }

    public void updateAStringOnAormReadOnly( int aormReadOnlyId , String newValue )
    {
        AORMReadOnly aormReadOnly = aormReadOnlyRepository.findOne( aormReadOnlyId );
        aormReadOnly.setaString( newValue );
    }

    public void tryToPersistReadOnly( AORMReadOnly aormReadOnly )
    {
        aormReadOnlyRepository.save( aormReadOnly );
    }


}
