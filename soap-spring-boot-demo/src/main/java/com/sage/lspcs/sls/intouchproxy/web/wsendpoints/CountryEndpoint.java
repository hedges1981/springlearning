package com.sage.lspcs.sls.intouchproxy.web.wsendpoints;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by rowland-hall on 13/01/16
 */
@Endpoint
public class CountryEndpoint
{
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request ) {
        GetCountryResponse response = new GetCountryResponse();

        System.out.println( "request has come in with data : " + request.getName() );

        Country country = new Country();
        country.setName( "Spain" );
        response.setCountry( country );

        //OR!!!!!! could have got a raw string xml out of the db and marshalled it to an object, then returned it.

        return response;
    }
}
