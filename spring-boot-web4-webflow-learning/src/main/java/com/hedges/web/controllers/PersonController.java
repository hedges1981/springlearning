package com.hedges.web.controllers;

import com.hedges.persistence.model.Person;
import com.hedges.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by rowland-hall on 08/01/16
 */
@Controller
@RequestMapping("/person")
public class PersonController
{
    @Autowired
    private PersonService personService;

    @RequestMapping("/listAll")
    public ModelAndView listAll()
    {

        ModelAndView mav = new ModelAndView( "allPersons" );
        List<Person> allPersons = personService.findAllPersons();
        mav.addObject( "allPersons", allPersons );

        return mav;
    }

    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping("/addPerson")
    public void addPerson( @RequestParam("name") String name )
    {
        personService.addPerson( name );
    }

}
