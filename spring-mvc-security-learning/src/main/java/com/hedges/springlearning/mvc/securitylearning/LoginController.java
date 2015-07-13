package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rowland-hall on 09/07/15
 */
@Controller
@RequestMapping(value="/login")
public class LoginController
{
    @RequestMapping( method = RequestMethod.GET )
    public ModelAndView login( @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout  )
    {
        ModelAndView model = new ModelAndView();

        if (error != null)
        {
            model.addObject("error", "Invalid username and password!");
        }

        model.setViewName("login");

        return model;
    }
}
