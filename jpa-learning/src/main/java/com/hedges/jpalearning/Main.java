package com.hedges.jpalearning;

import com.hedges.jpalearning.model.Employee;
import com.hedges.jpalearning.model.EmployeeSuspendedStatus;
import com.hedges.jpalearning.model.EmployeeType;
import com.hedges.jpalearning.service.EmployeeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 12/10/15
 */
public class Main
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "classpath:context.xml" );

        EmployeeService employeeService = context.getBean( EmployeeService.class );

        Employee e1 = employeeService.findById( 1 );


        Employee employee = new Employee();
        employee.setPhoneNum( "761725" );
        employee.setPicture( "thisIsAMadeUpPicture".getBytes() );
        employee.setEmployeeType( EmployeeType.CONTRACT_EMPLOYEE );
        employee.setEmployeeSuspendedStatus( EmployeeSuspendedStatus.NOT_SUSPENDED );

        employeeService.saveEmployeeAndFlush( employee );



        Employee e2 = employeeService.findById( employee.getId() );
    }
}
