package com.hedges.aoplearning;

import com.hedges.springlearning.U;

/**
 * Created by rowland-hall on 04/02/15
 */
public class AOPTargetObject2
{
    public String getName()
    {
        return AOPTargetObject2.class.getName();
    }

    public void doThing()
    {
        System.out.println(AOPTargetObject2.class.getName()+" in doThing()");
        System.out.println("Throwing an exception, should not say in after Returning doThingPointCut() next, should say in afterThrowing doThingPointCut() Instead::::");

        throw new RuntimeException(  );
    }


    public String doThingWithArguments( String s, String s1 )
    {
        U.print("doThingWithArguments called with args: "+s+":"+s1 );

        return "doThingWithArguments Args were:"+s+":"+s1;
    }

    @Interceptable
    public void doInterceptableThing()
    {

    }

}
