package com.hedges.springlearning.mvc.formprocessing;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hedges.springlearning.mvc.model.Person;


@Controller
@RequestMapping(value = "/editPersonForm")
public class EditPersonFormController 
{
	Logger LOGGER = Logger.getLogger(EditPersonFormController.class );
	
	   @RequestMapping( method = RequestMethod.GET)
	   public ModelAndView person() 
	  {
		  //when using the form jsp tags, you need to put the pojo you want the form to populate in the model, under attribute command
	      ModelAndView modelAndView = new ModelAndView("editPersonForm", "command", new Person() );
	      
	      modelAndView.addObject("message", "Edit person, time is: "+new Date()+" Current person details are:" );
	      //NOTE how the person appears in the form, as on the form page, modelAttribute="person"
	      Person person = new Person();
	      person.setName("Dong");
	      person.setAddress("Dong house");
	      
	      /**
	       * Note that on the 
	       */
	      modelAndView.addObject("person", person );
	      
	      return modelAndView;
	   }
	  
	   @RequestMapping( method = RequestMethod.POST)
	   public String addPerson(@ModelAttribute("person")Person person, 
			   //note how we get the Person object from the form due to the form saying modelAttribute="person"
	   ModelMap model) 
	  {
		  model.addAttribute("message", "Person edited, new values are:");
		  
		  model.addAttribute("person",person);

			  return "editPersonForm";

	   }

}
