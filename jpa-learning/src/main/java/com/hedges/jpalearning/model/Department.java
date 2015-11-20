package com.hedges.jpalearning.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by rowland-hall on 13/10/15
 */
@Entity
@Table(name="department")

@NamedQueries(
        {
                @NamedQuery( name="Department.getDepartmentsWithContractEmployee",
                //note the way that you here join dept to the employee table, so that you can check each department-emp row for a match, note
                //need of distinct to stop the same results coming back twice.

                //note, where it is e.g. join d.employees, that effectively means, from dept table join employees table
                //using the relation ship between dept and employees defined on the department entity.

                query = "SELECT DISTINCT d from Department d JOIN d.employees e WHERE e.employeeType= 'CONTRACT_EMPLOYEE'"),

                /**
                 * equivalent sql for the below is:
                 * SELECT jpalearning.department.name, count(*) from jpalearning.department JOIN jpalearning.emp on
                 * jpalearning.department.id = jpalearning.emp.dept_id group by jpalearning.department.id,jpalearning.department.name
                 */
                //note that you can have count(e) or count(*) here and it will give the same result.
                @NamedQuery( name="Department.getDepartmentEmployeeCount",
                             query = "SELECT d, COUNT(e) from Department d JOIN d.employees e GROUP by d HAVING COUNT(e) > 0"),

                @NamedQuery( name="Department.getDepartmentSalaryStats",
                             query = "SELECT d, MAX(e.salary), AVG(e.salary) from Department d JOIN d.employees e GROUP by d")

        })
public class Department
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    private String name;

    @OneToMany( targetEntity = Employee.class, mappedBy ="department", fetch = FetchType.EAGER)
    //allows for the department to fetch its employees, join column is in the emp table, so the targetEntity and
    //mappedBy allow it to resolve the relationship and build up the collection.
    //note the eager fetch explicitly set, default is LAZY for joined Entities
    @OrderBy("dogName,phone desc") //note the order by, when it fetches the employees, they are ordered as specified.
    //note that it uses the db column name, not the field/property name on the Employee entity.
    //note how List is used, should be as it is an ordered collection.
    private List<Employee> employees;

    @OneToMany(targetEntity = Employee.class, mappedBy="department")
    @MapKeyColumn(name="desk_id")  //note here that we refer to using a db column for the map key:
    private Map<String,Employee> employeesByDeskId;

    @OneToMany( targetEntity = Employee.class, mappedBy="department" )
    @MapKey(name="id")   //note here that is be using the id attribute of the employee class, could use any of its attributes.
    private Map<Integer,Employee> employeesById;


    //Map key using embeddable type, note how the EmployeeName is not embedded as part of the EmployeeEntity, however it can still be used to
    //build up a Map key, due to the column mappings on the EmployeeName object.
    //NOTE::: it is not seen as good practice to use an @Embeddable as a map key, according to the book.,
    @OneToMany( targetEntity = Employee.class, mappedBy="department" )
    private Map<EmployeeName, Employee> employeesByName;

    public Map<EmployeeName, Employee> getEmployeesByName()
    {
        return employeesByName;
    }

    public void setEmployeesByName( Map<EmployeeName, Employee> employeesByName )
    {
        this.employeesByName = employeesByName;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Collection<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees( List<Employee> employees )
    {
        this.employees = employees;
    }

    public Map<String, Employee> getEmployeesByDeskId()
    {
        return employeesByDeskId;
    }

    public void setEmployeesByDeskId( Map<String, Employee> employeesByDeskId )
    {
        this.employeesByDeskId = employeesByDeskId;
    }

    public Map<Integer, Employee> getEmployeesById()
    {
        return employeesById;
    }

    public void setEmployeesById( Map<Integer, Employee> employeesById )
    {
        this.employeesById = employeesById;
    }

    public String toString()
    {
        return name;
    }
}
