/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/loggedout")
public class LoggedOutController 
{
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String ok( ModelMap model )
    {
        model.addAttribute( "message","you have logged out" );

        return "printMessage";
    }
}
