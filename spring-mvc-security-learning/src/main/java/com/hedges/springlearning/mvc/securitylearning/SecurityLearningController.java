package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rowland-hall on 07/07/15
 *
 * url is localhost:2702/springmvclearning/test/securitylearning/user
 *
 * localhost:2702/springmvclearning/test/securitylearning/admin
 */
@Controller
@RequestMapping(value="/securitylearning")
public class SecurityLearningController
{
    public SecurityLearningController()
    {
        int x=0;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String ok( ModelMap model )
    {
        model.addAttribute( "message","ok" );

        return "printMessage";
    }


    @RequestMapping(value="/user**", method = RequestMethod.GET)
    public String userRequest( ModelMap model )
    {
        model.addAttribute( "message","in role_user area");

        return "printMessage";
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public String adminRequest( ModelMap model )
    {
        model.addAttribute( "message","in admin area");
        return "printMessage";
    }

}
