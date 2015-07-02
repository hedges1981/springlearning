package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dong")
public class ControllerThatDoesARedirect 
{
	   @RequestMapping(method = RequestMethod.GET)
	   public String printDong(ModelMap model) 
	   {
		 //puts Dong as the message attribute for EL and tells it to raise dong.jsp, see the context file for InternalResourceViewResolver
		//for how that gets sorted out.
	      model.addAttribute("message", "Dong!");  
	      
	      //Note the use of the redirect here, note that is considered good practice to do a redirect after form data has been posted,
	      //in order to prevent resubmission, issues with user pressing back button, etc.
	      //NOTE this will put it into a redirect loop, as redirecting to same page as this one!
	      return "redirect:dong";
	   }
}


