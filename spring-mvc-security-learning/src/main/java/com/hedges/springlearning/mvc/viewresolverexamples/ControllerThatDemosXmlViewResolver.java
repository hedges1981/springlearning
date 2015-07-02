package com.hedges.springlearning.mvc.viewresolverexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rowland-hall on 25/06/15
 *
 * url: http://localhost:2702/springmvclearning/test/controllerThatDemosXmlViewResolver
 */
@Controller
public class ControllerThatDemosXmlViewResolver
{
    @RequestMapping(method = RequestMethod.GET)
    public String doGet( Model model )
    {
        //NOTE how the XMLViewResolver will use a mapping in spring-views.xml to map this return value to a view with the same name
        return "viewMappedByXmlViewResolver";
    }
}
