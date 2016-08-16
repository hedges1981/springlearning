package com.hedges.springbootsoapdemo.web.controllers;


import com.hedges.springbootsoapdemo.web.wsclients.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by rowland-hall on 08/01/16
 */
@Controller
@RequestMapping("itp")
public class IntouchProxyController
{
    @Autowired
    private WeatherClient weatherClient;

    @RequestMapping(value = "/ok", method = {RequestMethod.GET} )
    public String hi()
    {
        return "ok";
    }

    @RequestMapping(value = "/logWeather", method = {RequestMethod.GET} )
    public String getAndLogRawSoapResponse( @RequestParam("zipCode") String zipCode, Model model )
    {
        String rawSoapResponseBody = weatherClient.getCityForecastByZipRawResponseBody( zipCode );
        System.out.println("**************Raw SOAP Response Body from WS call is:  ");
        System.out.println( rawSoapResponseBody );

        String message ="Raw SOAP response received back was:<br/><br/>";
        message += rawSoapResponseBody;

        model.addAttribute( "message", message );

        return "message";
    }



}
