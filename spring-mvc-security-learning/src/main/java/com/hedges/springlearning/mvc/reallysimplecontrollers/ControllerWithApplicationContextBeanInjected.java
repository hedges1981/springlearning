package com.hedges.springlearning.mvc.reallysimplecontrollers;

import com.hedges.springlearning.mvc.applicationcontextbeans.BeanDefinedInApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This bean shows that the 'web application context', i.e. the one fed into the dispatcher servlet can see beans in the application context.
 *
 * It also show that a bean defined in the 'web application context' overrides one defined in the application context. (bean2)
 *
 * url: http://localhost:2702/springmvclearning/test/controllerWithApplicationContextBeanInjected
 */
@Controller
public class ControllerWithApplicationContextBeanInjected
{
    @Autowired
    @Qualifier("beanDefinedInApplicationContext")
    private BeanDefinedInApplicationContext beanDefinedInApplicationContext;

    @Autowired
    @Qualifier("beanDefinedInApplicationContext2")
    private BeanDefinedInApplicationContext beanDefinedInApplicationContext2;

    @RequestMapping( method = RequestMethod.GET )
    public String doGet( Model model )
    {
        model.addAttribute( "message", "beanDefinedInApplicationContext says:"
                + beanDefinedInApplicationContext.getMessage() + ",beanDefinedInApplicationContext2 says:"
                + beanDefinedInApplicationContext2.getMessage() );

        return "printMessage";
    }
}
