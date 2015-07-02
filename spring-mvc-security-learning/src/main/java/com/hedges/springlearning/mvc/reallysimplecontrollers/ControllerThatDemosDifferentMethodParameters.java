/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.reallysimplecontrollers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * http://localhost:2702/springmvclearning/test/controllerThatDemosDifferentMethodParameters
 */
@Controller
@RequestMapping(value="/controllerThatDemosDifferentMethodParameters")
public class ControllerThatDemosDifferentMethodParameters {

    
    //NOTE how this request mapping is relative to the controller one, is reached on path
    ///controllerThatDemosDifferentMethodParameters/passHttpObjects
    @RequestMapping(value="passHttpObjects", method = RequestMethod.GET )
    public void doGet( HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws IOException 
    {
        String message="Session passed ="+session+" request passed="+request+" and response passed="+response;
	
        //NOTE that this method is a void, as we a writing directly to the response
        response.getWriter().print(message);
    }
}
