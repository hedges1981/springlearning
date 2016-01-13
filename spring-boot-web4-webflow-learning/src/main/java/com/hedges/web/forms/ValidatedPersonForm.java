package com.hedges.web.forms;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by rowland-hall on 08/01/16
 */

public class ValidatedPersonForm
{

    @Size( min=1, max=30, message = "error.length.must.be.between")
    @NotNull
    private String name;

    //NOTE: could also use @Min and @Max here instead.
    @Min(value=1, message="error.must.be.between")
    @Max(value=120, message="error.must.be.between" )
    private Integer age;


    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge( Integer age )
    {
        this.age = age;
    }

}

