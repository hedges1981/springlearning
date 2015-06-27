/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.hedges.springlearning.mvc.restexamples.Dog;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author christine
 */
public class DogControllerTest
{
    @Test
    @Ignore
    public void testDogController()
    {
        RestTemplate restTemplate = new RestTemplate();

        Dog dog = restTemplate.getForObject( "http://localhost:2702/springmvclearning/test/getDog?name=mavis", Dog.class );
    }
    
}
