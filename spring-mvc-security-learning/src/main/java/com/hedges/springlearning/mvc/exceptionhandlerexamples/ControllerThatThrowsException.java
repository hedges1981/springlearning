/*
http://localhost:2702/springmvclearning/test/controllerThatThrowsException
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
