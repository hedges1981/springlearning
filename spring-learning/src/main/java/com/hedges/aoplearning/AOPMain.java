package com.hedges.aoplearning;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by rowland-hall on 04/02/15
 */
public class AOPMain
{

    public static void main( String [] args )
    {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "aopLearningContext.xml");

        AOPTargetObject aopTargetObject =(AOPTargetObject)context.getBean( "aopTargetObject" );

        //Will trigger the beforeAnyGetNameAdvice()  method on the @Aspect object anAspect
        aopTargetObject.getName();

        AOPTargetObject2 aopTargetObject2 =(AOPTargetObject2)context.getBean( "aopTargetObject2" );

        //Will trigger beforeAOPTargetObject2GetName() on the @Aspect object anAspect, this one is called first, as it is
        // the most specific one.
        //Will trigger the beforeAnyGetNameAdvice()  method on the @Aspect object anAspect
        aopTargetObject2.getName();

        aopTargetObject.doThing();

        try
        {
            //this one is set to throw an exception to test @AfterThrowing:
            aopTargetObject2.doThing();

        }
        catch(Exception e)
        {

        }

        //demos the changing of arguments with the @Around annotation.
        aopTargetObject2.doThingWithArguments( "A", "B");

        //Demonstrates the use of a custom annotation @Interceptable to trigger an aspect.
        aopTargetObject2.doInterceptableThing();


    }

}
