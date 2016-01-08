package com.hedges.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rowland-hall on 08/01/16
 */
@Controller
public class HelloController
{
    @RequestMapping("/hello")
    public String hi()
    {
        return "hello";
    }
}
