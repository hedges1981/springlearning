package com.hedges.springlearning.mvc.restexamples;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rowland-hall on 26/06/15
 *
 * url:http://localhost:2702/springmvclearning/test/getDog
 * 
 * NOTE*** tried writing this using the @RestController but didn't work, so went for @Controller and
 * @ResponseBody combination instead.
 */
@Controller
/**
 * Note that prior to introduction of @RestController, you would do the same thing by putting @Controller
 * on the class, and @ResponseBody on the methods that returned objects 
 */
public class DogRestController
{
    @RequestMapping( value="/getDog", method = RequestMethod.GET, produces={"application/json","application/xml"} )
    @ResponseBody
    public Dog getDog(@RequestParam(value="name", defaultValue="mavis") String name )
    {
        Dog dog = new Dog();
        
        dog.setName( name );

        if( "mavis".equals( name ))
        {
            dog.setBreed( "Labrador" );
            dog.setAge( "2" );
        }
        else if("mrsDog".equals( name ))
        {
            dog.setBreed( "Labrador" );
            dog.setAge( "10" );
        }

        return dog;
    }
}
