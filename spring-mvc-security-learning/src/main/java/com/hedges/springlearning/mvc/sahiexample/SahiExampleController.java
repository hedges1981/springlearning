package com.hedges.springlearning.mvc.sahiexample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**  localhost:2702/springmvclearning/test/sahiexample/page1
 * Created by rowland-hall on 13/07/15
 */
@Controller
@RequestMapping("/sahiexample")
public class SahiExampleController
{
    @RequestMapping( value="page1", method=RequestMethod.GET )
    public String page1( ModelMap modelMap )
    {
        return "sahiExamplePage1";
    }

    @RequestMapping( value="page2", method=RequestMethod.GET )
    public String page2( ModelMap modelMap )
    {
        return "sahiExamplePage2";
    }
}
