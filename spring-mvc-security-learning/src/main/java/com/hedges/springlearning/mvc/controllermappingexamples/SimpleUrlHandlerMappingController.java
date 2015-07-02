/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.controllermappingexamples;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
public class SimpleUrlHandlerMappingController
{

    @RequestMapping(method = RequestMethod.GET)
    public String doGet( Model model )
    {
        model.addAttribute("message",this.getClass()+" EXECUTED!");

        return "printMessage";
    }
}
    

