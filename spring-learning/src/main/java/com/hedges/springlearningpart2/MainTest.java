package com.hedges.springlearningpart2;

import com.hedges.springlearning.U;
import com.hedges.springlearning.refandinnerbean.AGeneralBean1;
import com.hedges.springlearning.refandinnerbean.AGeneralBean2;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 22/01/15
 */
public class MainTest
{
    public static void main( String[] args )
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("springlearningpart2Context.xml");

        AutoWiringTestBean2 autoWiringTestBean2 =(AutoWiringTestBean2)context.getBean( "autoWiringTestBean2" );

        U.print("Autowired into bean by type: "+autoWiringTestBean2.getAutoWiringTestBean1().getName());

        AutowireTestBean5 autoWiringTestBean5 =(AutowireTestBean5)context.getBean( "autowireTestBean5" );

        U.print(autoWiringTestBean5.getAutowireTestBean4().getName());

        AGeneralBean1 beanWithInnerBean = ( AGeneralBean1 ) context.getBean( "generalBeanWithInnerBean" );

        //this is its inner bean:
        U.print("InnerBean hs been injected: "+beanWithInnerBean.getaGeneralBean2());

        AGeneralBean2 anInnerBean = ( AGeneralBean2 ) context.getBean( "anInnerBean" );

        U.print("See that anInnerBean is not the same as the actual inner bean: "+anInnerBean );




    }
}
