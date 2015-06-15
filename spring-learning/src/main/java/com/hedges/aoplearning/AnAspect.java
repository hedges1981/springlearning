package com.hedges.aoplearning;

import com.hedges.springlearning.U;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by rowland-hall on 04/02/15
 */

@Aspect
public class AnAspect
{
    @Before("doThingPointCut()")
    public void pointCutHook1()
    {
        System.out.println("in pointCutHook1()");
    }


    // this method will execute when a public String getName() is called on any spring bean.
    @Before("execution(public String getName())") //the execution..... is called a pointCut, defines the point at which it cuts in."
    public void beforeAnyGetNameAdvice()   //this gets executed before pointCutHook1(), as it defines the 'pointcut' explicitly rather than indirectly.
    {
        System.out.println("Executing beforeAnyGetNameAdvice()");
    }

    //Only gets executed before the AOPTargetObject2.getName() method.
    @Before("execution(* com.hedges.aoplearning.AOPTargetObject2.getName())")
    public void beforeAOPTargetObject2GetName()
    {
        System.out.println("Executing beforeAOPTargetObject2GetName()");
    }

    @Before("doThingPointCut()")
    public void beforeDoThingPointCut()
    {
        System.out.println("in before doThingPointCut()");
    }

    @After("doThingPointCut()")  //this will get executed even if the method it wraps throws an exception.
    public void afterDoThingPointCut()
    {
        System.out.println("in after doThingPointCut()");
    }

    @AfterReturning("doThingPointCut()")  //this will get executed only if it returns without exception.
    public void afterReturningDoThingPointCut()
    {
        System.out.println("in afterReturning doThingPointCut()");
    }

    @AfterThrowing("doThingPointCut()")  //this will get executed only if the exception throws an exception.
    public void afterThrowingDoThingPointCut()
    {
        System.out.println("in afterThrowing doThingPointCut()");
    }


    @Around("doThingPointCut()")
    public Object aroundAspect( ProceedingJoinPoint proceedingJoinPoint )
    {
        System.out.println("in @Around aspect");

        try
        {
            //note here that the doThing() methods that this wraps are voids, in this case the pjp.proceed(); returns null/
            return proceedingJoinPoint.proceed();
        }
        catch ( Throwable throwable )
        {
            throw new RuntimeException( throwable );
        }

    }


    @Around("execution(* com.hedges.aoplearning.AOPTargetObject2.doThingWithArguments(..))")
    //note the ".." in the method (..), allows it to match the signature with any args.
    public Object aroundAspectWithArguments( ProceedingJoinPoint proceedingJoinPoint )
    {
        U.print("Called with arguments : "+proceedingJoinPoint.getArgs() );

        try
        {
            Object[] objArr =  new Object[]{"C","D"};
            U.print( "Changing arguments to:"+objArr );
            return proceedingJoinPoint.proceed( objArr ); //note here how the arguments can be fudged.
            //could also return something completely and utterly different.
        }
        catch ( Throwable throwable )
        {
            throw new RuntimeException( throwable );
        }
    }


    @Around("@annotation(com.hedges.aoplearning.Interceptable)")
    public Object aroundAnnotationAspect( ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        U.print("Aspect intercepting @Interceptable annotation");
        return proceedingJoinPoint.proceed();
    }




    @Pointcut("execution(public void doThing())")   //note the way this @Pointcut is used to indirectly imply a point cut.
    public void doThingPointCut()
    {} //this method must have an empty body, else it is not a valid pointCut.



}
