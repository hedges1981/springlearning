package com.hedges.springlearning.mvc.restexamples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by rowland-hall on 26/06/15
 *
 * 
 * 
 * NOTE*** tried writing this using the @RestController but didn't work, so went for @Controller and
 * @ResponseBody combination instead.
 * 
 * See context xml file for the rigging up of the message converters.
 * 
 * See DogRestWebServiceTest for examples of using the RestTemplate to test this web-service.
 * 
 * See eg here for example of how to test controller using e.g. MockMVC.
 * http://h30499.www3.hp.com/t5/HP-Software-Developers-Blog/A-Comprehensive-Example-of-a-Spring-MVC-Application-Part-4/ba-p/6135463#.VY_rjPlVhBc
 */
@Controller

@RequestMapping( value="/dogRestWebService")
public class DogRestWebService
{
    private static final Logger LOGGER = Logger.getLogger(DogRestWebService.class );
    
    Map<String,Dog> dogs = new HashMap<String,Dog>();
    
    {
       Dog dog = new Dog();
        
        dog.setName( "mavis" );
        dog.setBreed( "Labrador" );
        dog.setAge( "2" );
        dogs.put("mavis", dog);
        
        Dog dog2 = new Dog();
        dog2.setName("mrsDog");
        dog2.setBreed( "Labrador" );
        dog2.setAge( "10" );
        dogs.put("mrsDog",dog2);
    }
    
    /**
     * EXAMPLE of using a webService to get a single object.
     * If called from a web browser, the marshallingHttpMessageConverter converts it to dog-xml before sending out the response.
     * 
     * url:http://localhost:2702/springmvclearning/test/dogRestWebService/getDog
     * 
     * NOTE, it seems that the produces is meaningless here.
     * 
     * It looks like in this case that the list of message converters defined 
     */
    @RequestMapping( value="/getDog", method = RequestMethod.GET,produces={"application/xml"} )
    @ResponseBody
    public Dog getDog(@RequestParam(value="name", defaultValue="mavis") String name )
    {
        return dogs.get(name);
    }
    
    @RequestMapping( value="/getAllDogs", method = RequestMethod.GET, produces={"application/json","application/xml"} )
    @ResponseBody
    /**
     * NOTE, this works in the browser due to the xml produces type. Note, with a return type of Collection, this method was not mapped to the
     * URL.
     */
    public List<Dog> getAllDogs()
    {
        List<Dog> dogsList = new ArrayList<Dog>();
        dogsList.addAll(dogs.values());
        
        return dogsList;
    }
    
    /**
     * EXAMPLE OF using web service to create an object(should the method be PUT here?)
     * A call is made to this service with some xml or json that represents a dog.
     * The right message converter is used to convert it into a dog on its way here.
     * 
     * NOTE the response status of Created... good practice
     */
    @RequestMapping(value = "/createDog", produces = {"application/json","application/xml"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createDogByPost(@RequestBody Dog dog)
    {
        dogs.put(dog.getName(), dog);
        LOGGER.info( "Dog created:"+dog );
    }
    
    @RequestMapping(value = "/createDog", produces = {"application/json","application/xml"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void createDogByPut(@RequestBody Dog dog)
    {
        dogs.put(dog.getName(), dog);
        LOGGER.info( "Dog created:"+dog );
    }
    
    /**
     * Example of using webservice to delete an object.
     * Note convention to return OK status, (no Deleted status available).
     * Note the HTTP methods it accepts
     * 
     * NOTE, looks like convention is for delete methods to just work off an Id, path variable, for example the Spring rest tempalte doesn't send a content type with a delete() method,
     * so any object passed can't be marshalled.
     * 
     * U could probably work a way of getting it to pass an object, by setting the content type in some way on the delete request. 
     */
    @RequestMapping(value = "/deleteDog", method = {RequestMethod.DELETE } )
    @ResponseStatus(HttpStatus.OK)
    public void deleteDog( @RequestParam(value="name") String name )
    {
        if( dogs.containsKey(name))
        {
            dogs.remove( name );
            LOGGER.info(name+" deleted.");
        }
        else
        {
            throw new IllegalArgumentException("Dog not found:"+name);
        }
    }
    
    @ExceptionHandler( Exception.class )
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException( Exception e )
    {
        return e.getMessage();
    }
    
}
