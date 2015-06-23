/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *access on url:
 * http://localhost:2702/springmvclearning/test/controllerThatReadsPathParameters/param1/param2
 */
@Controller
public class ControllerThatReadsPathParameters {
    
 
    @RequestMapping(value="controllerThatReadsPathParameters/{param1}/{param2}",method = RequestMethod.GET)
    public String doGet( @PathVariable("param1") String param1, @PathVariable("param2") String param2, Model model )
    {
        String message= "Param1 is "+param1+ "param2 is"+param2;
        model.addAttribute( "message",message);

        return "printMessage";
    }
    
}
