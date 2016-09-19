package com.hedges.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by rhall on 11/08/2016.
 */
@Controller
@RequestMapping(value="/login")
public class LoginController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class );

    @RequestMapping( method = RequestMethod.GET )
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpSession session )
    {
        LOGGER.info("Displaying login page");

        ModelAndView model = new ModelAndView();

        if (error != null)
        {
            Exception e = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            String message = getUserMessage(e);

            model.addObject("message", message);
        }
        else if( logout != null )
        {
            model.addObject("message", "Logout successful" );
        }

        model.setViewName("loginPage");

        return model;
    }

    private String getUserMessage(Exception e) {
        String message;

        if( e instanceof BadCredentialsException)
        {
            message = "Invalid username and / or password!";
        }
        else if( e instanceof AccountStatusException)
        {
            message = "There is a problem with your account, please contact support";
        }
        else
        {
            message = "An error has occurred";
        }
        return message;
    }
}


