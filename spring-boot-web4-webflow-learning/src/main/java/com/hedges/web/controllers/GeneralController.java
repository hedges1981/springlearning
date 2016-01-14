package com.hedges.web.controllers;

import com.hedges.web.forms.ValidatedPersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

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

    // NOTE: cos model is passed by reference, only need to return the string view name
    @RequestMapping(method = {RequestMethod.GET}, value="/springtagsdemo")
    public String springtagsdemo( Model model)
    {

        ValidatedPersonForm p = new ValidatedPersonForm(); p.setName("initial name");
        model.addAttribute( "person", p );
        return "springtagsdemo";
    }

    @RequestMapping(method = {RequestMethod.POST}, value="/springtagsdemo")
    //NOTE: the @Valid means that the validation will kick in, see the Person.class for the validation annotations.
    public String postPersonObject( Model model, @Valid @ModelAttribute("person") ValidatedPersonForm personForm, BindingResult bindingResult)
    {
        if( bindingResult.hasErrors() )
        {
            String formErrorNotes = "";
            //NOTE: code to deal with any special actions from the validation.
            for( ObjectError objectError:  bindingResult.getAllErrors())
            {
                formErrorNotes+="<br/><br/> Field:"+objectError.getObjectName()+" has the following auto generated error codes that could be resolved through settings in ValidationMessages.properties";

                for( String code: objectError.getCodes())
                {
                    formErrorNotes +=","+code;
                }
            }
            model.addAttribute( "formNotes", formErrorNotes );
        }

        if( false )//if some validation thing has failed:
        {
            //NOTE: this is what you would use to reject a value based on some kind of validation here or in a service.
            bindingResult.rejectValue( "fieldName", "aMessage" );

        }

        model.addAttribute("person", personForm );
        return "springtagsdemo";
    }
}
