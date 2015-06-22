package com.hedges.springlearning.mvc.controllermappingexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by rowland-hall on 22/06/15
 *
 * Should get picked up by the ControllerBeanNameHandlerMapping, url is localhost:2702/springmvclearning/test/controllerMappedByBeanName
 *
 * NOTE that the bean name starts with a lower case s!
 */
@Controller
public class ControllerMappedByBeanName
{
    @RequestMapping(method = RequestMethod.GET)
    public String doGet( Model model )
    {
        model.addAttribute("message",this.getClass()+" EXECUTED!");

        return "printMessage";
    }
}
