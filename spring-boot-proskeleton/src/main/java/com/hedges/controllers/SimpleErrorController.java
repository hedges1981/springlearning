package com.hedges.controllers;

import com.hedges.model.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by rhall on 19/09/2016.
 */
@Controller
public class SimpleErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    ModelAndView error(HttpServletRequest request, HttpServletResponse response, Exception e) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");

        Environment env = Environment.valueOf(System.getProperty("spring.profiles.active"));

        Map<String, Object> errorDetails = getErrorAttributes(request);

        String message = "An error has occurred, status code: " + errorDetails.get("status") + ", " + errorDetails.get("error");

        boolean includeStackTrace = (env == Environment.development) || (env == Environment.devenv);

        if (includeStackTrace) {
            message += "<br/><br/>"+getStackTraceAsString(e);
        }

        mav.getModelMap().addAttribute("message", message);

        return mav;
    }

    private String getStackTraceAsString(Exception e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }
}
