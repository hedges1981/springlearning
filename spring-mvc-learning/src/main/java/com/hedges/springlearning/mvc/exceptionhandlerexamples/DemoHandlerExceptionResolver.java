package com.hedges.springlearning.mvc.exceptionhandlerexamples;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rowland-hall on 25/06/15
 *
 * Note how this extends: AbstractHandlerExceptionResolver, allows us to do standard things such as set the order.
 */
public class DemoHandlerExceptionResolver extends AbstractHandlerExceptionResolver
{
    private static final Logger LOGGER = Logger.getLogger(DemoHandlerExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex )
    {
        LOGGER.error(ex) ;
        ModelAndView modelAndView = new ModelAndView( "printMessage" );
        modelAndView.addObject( "message", "Exception handled by:"+this.getClass()+", exception type is:"+ex.getClass());
        return modelAndView;
    }
}
