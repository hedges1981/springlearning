package com.hedges.web.controllers;

import com.hedges.persistence.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by rowland-hall on 08/01/16
 */
@Controller
@SessionAttributes("person")   //NOTE: this puts the person object in the session scope,
public class GeneralController
{
    @RequestMapping("/hello")
    public String hi()
    {
        return "hello";
    }

    //NOTE: cos model is passed by reference, only need to return the string view name
    @RequestMapping(method = {RequestMethod.GET}, name="/springtagsdemo")
    public String springtagsdemo( Model model)
    {
        Person p = new Person(); p.setName("initial name");
        model.addAttribute( "person", p );
        return "springtagsdemo";
    }

    @RequestMapping(method = {RequestMethod.POST}, name="/springtagsdemo")
    public String postPersonObject( Model model, @ModelAttribute("person") Person person)
    {
        model.addAttribute("person", person );
        return "springtagsdemo";
    }
}
