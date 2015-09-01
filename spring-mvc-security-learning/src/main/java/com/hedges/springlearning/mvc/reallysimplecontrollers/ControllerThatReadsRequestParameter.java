package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerThatReadsRequestParameter 
{
	@RequestMapping( value="/ding", method=RequestMethod.GET )
	public String printDing( @RequestParam("id") int id, @CookieValue ModelMap model )
	{
		//puts Ding as the message attribute for EL and tells it to raise ding.jsp, see the context file for InternalResourceViewResolver
		//for how that gets sorted out.
		model.addAttribute("message", "Ding!, ID PARAMETER PASSED WAS: "+id );
		return "ding";
	}
}
