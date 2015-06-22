package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ControllerWithRequestMappingInXml 
{
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet( Model model )
	{
		model.addAttribute("message",this.getClass()+" EXECUTED!");
		
		return "printMessage";
	}
	
}