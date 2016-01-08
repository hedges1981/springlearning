package com.hedges.aoplearning;

import com.hedges.springlearning.U;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by rowland-hall on 04/02/15
 */

@Aspect
public class AnAspect
{
    /*
    These two methods have exactly the same point cut, in this case the order in which they execute is undefined.
     */
    @Before("doThingPointCut()")
    public void pointCutHook1( JoinPoint jp )
    {
        System.out.println("in pointCutHook1()");
    }

    @Before("doThingPointCut()")
    public void beforeDoThingPointCut()
    {
        System.out.println("in before doThingPointCut()");
    }


    // this method will execute when a public String getName() is called on any spring bean.
    @Before("execution(public String getName())") //the execution..... is called a pointCut, defines the point at which it cuts in."
    public void beforeAnyGetNameAdvice()   //this gets executed before pointCutHook1(), as it defines the 'pointcut' explicitly rather than indirectly.
    {
        System.out.println("Executing beforeAnyGetNameAdvice()");
    }

    //NOTE that in an execution expression, only Ret type, method name and args are mandatory to br dysyrf.
    // this method will execute when a public String getName() is called on any spring bean.
    @After("execution(public String getName())") //the execution..... is called a pointCut, defines the point at which it cuts in."
    public void afterAnyGetNameAdvice()   //this gets executed before pointCutHook1(), as it defines the 'pointcut' explicitly rather than indirectly.
    {
        System.out.println("Executing afterAnyGetNameAdvice()");
    }

    //Only gets executed before the AOPTargetObject2.getName() method.
    //expression means a method called getName() (no args) on the specific class, with ANY return type.
    @Before("execution(* com.hedges.aoplearning.AOPTargetObject2.getName())")
    public void beforeAOPTargetObject2GetName()
    {
        System.out.println("Executing beforeAOPTargetObject2GetName()");
    }

    @After("doThingPointCut()")  //this will get executed even if the method it wraps throws an exception.
    public void afterDoThingPointCut()
    {
        System.out.println("in after doThingPointCut()");
    }

    @AfterReturning("doThingPointCut()")  //this will get executed only if it returns without exception.
    //could have added e.g.  returning="retVal" and declaring reVal as a parameter to the method to get the retVal.
    public void afterReturningDoThingPointCut()
    {
        System.out.println("in afterReturning doThingPointCut()");
    }

    @AfterThrowing("doThingPointCut()")  //this will get executed only if the exception throws an exception.
    //could have added e.g. , throwing = "e" and added e as a method parameter, to get hold of the exception object.
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
            Object o = proceedingJoinPoint.proceed();
            return o;
        }
        catch ( Throwable throwable )
        {
            throw new RuntimeException( throwable );
        }

    }

    //specific class and method name, any return type, any number of arguments,
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
    
    @Before("execution(* com.hedges.aoplearning.AOPTargetObject2.doThingWithArguments(..)) && args(s1,..)")
    //note the ".." in the method (..), allows it to match the signature with any args.
    // the &args(s1,..) means any method that has as string as the first argument, 0 or more other arguments.
    public void beforeAspectWithArgs( String s1 )
    {
        U.print("fetched string argument using args:"+s1);
    }
    
    @Before("args(s1,s2)")
    //using the args has 2 effects, 1. it enforces a rule on the arguments to be of the type declared for this method, 
    // 2. Allows them to be passed into the advice method.
    public void beforeAnyMethodWithTwoStringArguments( String s1, String s2 )
    {
        U.print("matched method with two String arguments:"+s1+","+s2);
    }

    //Any method where there is 1 argument and it is annotated with @Interceptable
    @Before("@args(com.hedges.aoplearning.Interceptable)")
    public void beforeAnyMethodWithAnnotatedArgument(  )
    {
        U.print("Intercepted method call, as argument has desired annotation, note it is the class of the argument that is checked, not the"
                + "argument syntax in the method code.");
    }
    
    @Before("@within(com.hedges.aoplearning.Interceptable)")
    public void beforeAnyMethodWithAnnotationInPackage(  )
    {
        U.print("Intercepted method call, as target class has annotation within desired class, note that in Spring this exactly the same as using @Target.");
    }


    @Around("@annotation(com.hedges.aoplearning.Interceptable)")
    public Object aroundAnnotationAspect( ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        U.print("Aspect intercepting @Interceptable annotation");
        return proceedingJoinPoint.proceed();
    }
    
    @Before("this(com.hedges.aoplearning.AOPTargetObject2)")
    public void beforeWithThis()
    {
        U.print("In beforeWithThis(), uses the 'this' pointcut language to intercept all methods on a given class");
    }
    
    @Before("target(com.hedges.aoplearning.AOPTargetObject2)")
    public void beforeWithTarget()
    {
        U.print("In beforeWithTarget(), because AOPTargetObjet2 is a 'class', i.e. CGLIB proxy, not an interface based proxy, target and this"
                + "are the same, as the proxy and the target object are both instance of AOPTargetObject2");
    }
    
     @Before("this(java.lang.Object)")
    public void beforeWithThisOnJavaObjectClass()
    {
        U.print("In beforeWithThisOnJavaObjectClass, shows that the this picks sub classes as well, i.e. it uses instance of");
    }
    
     @Before("this(com.hedges.aoplearning.AnInterfaceImpl)")
    public void beforeWithThisOnInterfaceImplClass()
    {
        U.print("This method should not get called, as this refers to the proxy class, which, because we are dealing with an Interfaced class here,"
                + "is instanceof AnInterface, but not AnInterfaceImpl");
    }
    
    @Before("target(com.hedges.aoplearning.AnInterfaceImpl)")
    public void beforeWithTargetOnInterfaceImplClass()
    {
        U.print("This method should not get called, as this refers to the proxy class, which, because we are dealing with an Interfaced class here,"
                + "is instanceof AnInterface, but not AnInterfaceImpl");
    }
    
     @Before("this(com.hedges.aoplearning.AnInterface)")
    public void beforeWithThisOnInterface()
    {
        U.print("This method should get called, as this refers to the proxy class, which, because we are dealing with an Interfaced class here,"
                + "is instanceof AnInterface, but not AnInterfaceImpl");
    }
    

            
    @Before("@target(com.hedges.aoplearning.Interceptable)")
    public void beforeWithTargetAnnotation()
    {
        U.print("This method should get called, note how we are targeting classes annotated with the Interceptable annotation");
    }


    //Presumably this allows you to effectively reference the point cut as a variable, rather than having to copy and paste a given execution string
    //into loads of places.
    @Pointcut("execution(public void doThing())")   //note the way this @Pointcut is used to indirectly imply a point cut.
    public void doThingPointCut()
    {} //this method must have an empty body, else it is not a valid pointCut.



}
