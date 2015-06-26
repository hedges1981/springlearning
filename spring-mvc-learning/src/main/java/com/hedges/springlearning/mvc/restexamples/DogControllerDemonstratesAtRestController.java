package com.hedges.springlearning.mvc.restexamples;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rowland-hall on 26/06/15
 *
 * url:http://localhost:2702/springmvclearning/test/getDog
 */
@RestController
public class DogControllerDemonstratesAtRestController
{
    @RequestMapping( value="/getDog", method = RequestMethod.GET)
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
