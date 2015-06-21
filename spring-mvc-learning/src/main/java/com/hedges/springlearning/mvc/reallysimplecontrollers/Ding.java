package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Ding 
{
	@RequestMapping( value="/ding", method=RequestMethod.GET )
	public String printDing( ModelMap model )
	{
		//puts Ding as the message attribute for EL and tells it to raise ding.jsp, see the context file for InternalResourceViewResolver
		//for how that gets sorted out.
		model.addAttribute("message", "Ding!");
		return "ding";
	}
}
