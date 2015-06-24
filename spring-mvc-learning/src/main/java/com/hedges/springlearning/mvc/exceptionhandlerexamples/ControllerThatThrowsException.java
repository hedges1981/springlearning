/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.exceptionhandlerexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ControllerThatThrowsException 
{
    @RequestMapping(method = RequestMethod.GET)
    public String doGetThrowsException( Model model )
    {
        throw new RuntimeException();
    }
}
