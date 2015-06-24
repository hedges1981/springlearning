package com.hedges.springlearning.mvc.reallysimplecontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rowland-hall on 24/06/15
 *
 * url = http://localhost:2702/springmvclearning/test/controllerThatSetsAResponseStatus
 *
 * This sets status of 226 on the response:
 */
@Controller
public class ControllerThatSetsAResponseStatus
{
    @ResponseStatus( HttpStatus.IM_USED )
    @RequestMapping(method = RequestMethod.GET)
    public String doGet( Model model )
    {
        String message= "Response status has been set to: "+HttpStatus.IM_USED;
        model.addAttribute( "message",message);

        return "printMessage";
    }
}
