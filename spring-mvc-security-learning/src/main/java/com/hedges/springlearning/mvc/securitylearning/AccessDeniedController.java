package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rowland-hall on 08/07/15
 */
@Controller
@RequestMapping(value="/accessdenied")
public class AccessDeniedController
{
    @RequestMapping(method = RequestMethod.GET)
    public String ok( ModelMap model )
    {
        model.addAttribute( "message","ACCESS DENIED!" );

        return "printMessage";
    }
}
