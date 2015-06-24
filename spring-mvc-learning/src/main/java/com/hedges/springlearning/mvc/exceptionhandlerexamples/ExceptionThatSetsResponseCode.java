package com.hedges.springlearning.mvc.exceptionhandlerexamples;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rowland-hall on 24/06/15
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Set to demo response status setting on exception")
public class ExceptionThatSetsResponseCode extends RuntimeException
{
    public ExceptionThatSetsResponseCode( String message )
    {
        super( message );
    }
}
