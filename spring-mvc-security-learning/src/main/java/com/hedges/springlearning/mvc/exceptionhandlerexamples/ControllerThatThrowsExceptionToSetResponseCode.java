package com.hedges.springlearning.mvc.exceptionhandlerexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rowland-hall on 24/06/15
 *
 * http://localhost:2702/springmvclearning/test/controllerThatThrowsExceptionToSetResponseCode
 */
@Controller
public class ControllerThatThrowsExceptionToSetResponseCode
{

    //NOTE how this method ends up with 404 set as the response code, due to the annotation on the Exception class.
    @RequestMapping(method = RequestMethod.GET)
    public String doGetThrowsException( Model model )
    {
        throw new ExceptionThatSetsResponseCode( "Hedges exception thrown from:"+this.getClass() );
    }
}
