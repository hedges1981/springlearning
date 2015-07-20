package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by rowland-hall on 07/07/15
 *
 * url is localhost:2702/springmvclearning/test/securitylearning/user
 * localhost:2702/springmvclearning/test/securitylearning/user/callsAdminMethod
 * localhost:2702/springmvclearning/test/securitylearning/adminOrUser
 * localhost:2702/springmvclearning/test/securitylearning/admin    localhost:2702/springmvclearning/test/securitylearning/adminAndUser
 * localhost:2702/springmvclearning/test/securitylearning/printUserRoles
 * localhost:2702/springmvclearning/test/securitylearning/roleDependentJsp
 */
@Controller
@RequestMapping(value="/securitylearning")
public class SecurityLearningController
{
    @Autowired
    private BeanWithMethodLevelSecurity beanWithMethodLevelSecurity;
    
    @RequestMapping(method = RequestMethod.GET)
    public String ok( ModelMap model )
    {
        model.addAttribute( "message","ok" );

        return "printMessage";
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public String userRequest( ModelMap model )
    {
        model.addAttribute( "message","in role_user area");

        return "printMessageWithLogOut";
    }
    
    @RequestMapping(value="/user/callsAdminMethod", method = RequestMethod.GET)
    public String userRequestToProhibitedMethodCall( ModelMap model )
    {
        String message = "In role user area.";
        
        //this one ok for user or admin role:
        beanWithMethodLevelSecurity.canOnlyBeDoneByUser();
        
        try
        {
            beanWithMethodLevelSecurity.adminOnlyViaAnnotation();
            message += "Got past the annotated admin role check!";
        }
        catch( Exception e)
        {
            message+="Didnt get past the annotated admin role check, exception is"+e+" "+e.getMessage();
        }
        
        try
        {
            beanWithMethodLevelSecurity.adminOnlyViaGlobalDeclaration();
            message += "Got past the global admin role check!";
        }
        catch( Exception e)
        {
            message+="Didnt get past the global admin role check, exception is"+e+" "+e.getMessage();
        }
        
        model.addAttribute("message", message);
        
        return "printMessageWithLogOut";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminRequest( ModelMap model )
    {
        model.addAttribute( "message","in admin area");
        return "printMessageWithLogOut";
    }

    @RequestMapping(value = "/adminAndUser", method = RequestMethod.GET)
    public String adminAndUserRequest( ModelMap model )
    {
        model.addAttribute( "message","in admin and User area");
        return "printMessageWithLogOut";
    }

    @RequestMapping(value = "/adminOrUser", method = RequestMethod.GET)
    public String adminOrUserRequest( ModelMap model )
    {
        model.addAttribute( "message","in admin OR User area");
        return "printMessageWithLogOut";
    }

    @RequestMapping(value = "/printUserRoles", method = RequestMethod.GET)
    public String printUserRoles( ModelMap model )
    {
        List<String> userRolesList = SpringSecurityUtil.getUserRoles();

        model.addAttribute( "message","User roles are: "+ userRolesList );

        return "printMessage";
    }

    @RequestMapping(value = "/roleDependentJsp", method = RequestMethod.GET)
    public String roleDependentJsp( ModelMap model )
    {
        return "roleDependentJsp";
    }


}
