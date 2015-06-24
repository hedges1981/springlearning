package com.hedges.springlearning.mvc.exceptionhandlerexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rowland-hall on 24/06/15
 *
 * url= http://localhost:2702/springmvclearning/test/exceptionHandlerController
 */
@Controller
public class ExceptionHandlerController
{
    @RequestMapping(method = RequestMethod.GET)
    public String doGetThrowsException( Model model )
    {
        throw new ExceptionThatSetsResponseCode( "Hedges exception thrown!" );
    }

    /**
     * NOTE: this only handles exceptions thrown from this class, it does not handled the exception thrown by:
     * the ControllerThatThrowsException class.
     */
    @ExceptionHandler(ExceptionThatSetsResponseCode.class )
    public ModelAndView handleHedgesException( ExceptionThatSetsResponseCode he )
    {
        ModelAndView modelAndView = new ModelAndView("printMessage");
        modelAndView.addObject("message", ""+he+" handled");

        return modelAndView;
    }
}
