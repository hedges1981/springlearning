/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.hedges.springlearning.mvc.restexamples.Dog;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christine
 */
@Ignore
public class DogRestWebServiceUsesRestTemplateTest
{
    
    private RestTemplate getRestTemplateWithJsonMessageConverters()
    {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        messageConverters.add(mappingJackson2HttpMessageConverter);
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters( messageConverters );
        
        return restTemplate;
    }
    
    /**
     * Tests the getting of a dog from the web service
     */
    @Test
    public void testGetDog()
    {
        RestTemplate restTemplate = getRestTemplateWithJsonMessageConverters();

        /**
         * NOTE, because the restTemplate only has the jackson message converter with it, it sends that it ONLY accepts "application/json".
         * The web service needs to have a message converter that can convert the Dog object to json, hence it needs the MappingJackson2HttpMessageConverter
         * registered also, see where that is done in web context xml.
         */
        Dog dog = restTemplate.getForObject( "http://localhost:2702/springmvclearning/test/dogRestWebService/getDog?name=mavis", Dog.class );
        
        System.out.println(dog);
    }
    
    /**
     * Tests the putting of a new dog to the web service
     */
    @Test
    public void testCreateDogByPut()
    {
       RestTemplate restTemplate = getRestTemplateWithJsonMessageConverters();
       
       Dog d = new Dog();
       
       d.setName("razzer");
       
       restTemplate.put( "http://localhost:2702/springmvclearning/test/dogRestWebService/createDog", d);
    }
    
    @Test(expected=HttpServerErrorException.class )
    public void testDeleteDog()
    {
       RestTemplate restTemplate = getRestTemplateWithJsonMessageConverters();
       
       restTemplate.delete("http://localhost:2702/springmvclearning/test/dogRestWebService/deleteDog?name=doesntexist");
    }
    
}
