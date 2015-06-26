package com.hedges.springlearning.mvc.restexamples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rowland-hall on 26/06/15
 *
 * url= http://localhost:2702/springmvclearning/test/ControllerCallsGetDogRestService
 */
@Controller
public class ControllerCallsGetDogRestService
{
    @RequestMapping(method = RequestMethod.GET)
    public String doGet( Model model )
    {
        RestTemplate restTemplate = new RestTemplate();

        Dog dog = restTemplate.getForObject( "http://localhost:2702/springmvclearning/test/getDog?name=mavis", Dog.class );

        model.addAttribute( "message" , dog.toString() );

        return "printMessage";
    }
}
