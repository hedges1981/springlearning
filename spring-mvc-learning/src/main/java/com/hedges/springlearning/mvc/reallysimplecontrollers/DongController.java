package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dong")
public class DongController 
{
	   @RequestMapping(method = RequestMethod.GET)
	   public String printDong(ModelMap model) 
	   {
		 //puts Dong as the message attribute for EL and tells it to raise dong.jsp, see the context file for InternalResourceViewResolver
		//for how that gets sorted out.
	      model.addAttribute("message", "Dong!");  
	      return "dong";
	   }
}


