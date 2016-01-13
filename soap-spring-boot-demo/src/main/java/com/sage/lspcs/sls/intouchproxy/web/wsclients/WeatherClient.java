package com.sage.lspcs.sls.intouchproxy.web.wsclients;

import hello.wsdl.GetCityForecastByZIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.support.MarshallingSource;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class WeatherClient extends WebServiceGatewaySupport
{

    private static final Logger log = LoggerFactory.getLogger( WeatherClient.class );

    public String getCityForecastByZipRawResponseBody( String zipCode )
    {
        GetCityForecastByZIP request = new GetCityForecastByZIP();
        request.setZIP( zipCode );

        log.info( "Making call to web service at: http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP" );

        MarshallingSource marshallingSource = new MarshallingSource( getMarshaller(), request );

        StringWriter stringWriter = new StringWriter();
        StreamResult streamResult = new StreamResult( stringWriter );

        //NOTE: the way that you pass in the wdsl url (the .asmx here) and the service URL in the
        //Soap action callback object.
        getWebServiceTemplate().sendSourceAndReceiveToResult( "http://wsf.cdyne.com/WeatherWS/Weather.asmx", marshallingSource, new SoapActionCallback( "http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP" ), streamResult );

        return streamResult.getWriter().toString();
    }


}