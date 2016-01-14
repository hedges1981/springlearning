package com.hedges.web.forms;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Created by rowland-hall on 08/01/16
 */

public class ValidatedPersonForm
{
    //NOTE: for the internationalised error messages,it gets the codes from ValidationMessages.properties NOT
    //messages.properties
    //NOTE on the error message, if not supplied, spring gives a few defaults, e.g. person.name.notNull, which you
    //could resolve in the ValidationMessages.properties. Presumably if multiple in there, will pick the most specialised
    //for the field.
    //NOTE: see the ValidationMessages.properties file for how the e.g. min and max are put into the message.
    @Size( min=1, max=30, message = "{error.length.must.be.between}")
    @NotNull
    private String name;

    //NOTE: could also use @Min and @Max here instead.
    @Min(value=1, message="{error.must.be.more.than}")
    @Max(value=120, message="{error.must.be.less.than}" )
    private Integer age;

    @Min(10)
    @NotNull
    private Integer weightInKilos;

    @Past
    //NOTE: date is enforced to be in the past, u can also have @Future as well.
    //NOTE: here we have to tell it the date pattern that it will get the data in from the browser, else it can't convert it and throws
    //exception.
    @DateTimeFormat(pattern="yyyy-mm-dd")
    private Date birthDate;

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate( Date birthDate )
    {
        this.birthDate = birthDate;
    }

    public Integer getWeightInKilos()
    {
        return weightInKilos;
    }

    public void setWeightInKilos( Integer weightInKilos )
    {
        this.weightInKilos = weightInKilos;
    }

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

