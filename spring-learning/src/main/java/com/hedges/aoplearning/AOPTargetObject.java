package com.hedges.aoplearning;

/**
 * Created by rowland-hall on 04/02/15
 */
public class AOPTargetObject
{
      public String getName()
      {
          return AOPTargetObject.class.getName();
      }


    public void doThing()
    {
        System.out.println(AOPTargetObject.class.getName()+" in doThing()");
    }






}
