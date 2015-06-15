package com.hedges.springlearning;

import com.hedges.springlearning.constantstesting.BeanWithConstants;
import com.hedges.springlearning.nameidtesting.BeanWithManyNames;
import com.hedges.springlearning.nameidtesting.BeanWithNameAndId;
import com.hedges.springlearning.nameidtesting.BeanWithNameNoId;
import com.hedges.springlearning.nameidtesting.BeanWithNoNameOrId;
import com.hedges.springlearning.parentbeans.ChildBean;
import com.hedges.springlearning.parentbeans.ChildOfTemplateBean;
import com.hedges.springlearning.parentbeans.ParentBean;
import org.apache.commons.io.IOUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"dongContext.xml");//Note: this can have >1 file, get loaded in order of args.
    	
//    	Donger donger = (Donger) context.getBean("donger1");
//    	donger.printMessage();
//    	
//    	Donger donger2 = (Donger) context.getBean("donger2");   	
//    	donger2.printMessage();
//    	
//    	ServiceWithAutoWiring serviceWithAutoWiring 
//    	= (ServiceWithAutoWiring)context.getBean("aDifferentNameForServiceWithAutoWiring");
//    	
//    	serviceWithAutoWiring.doThing();
    	
    	AnotherServiceWithAutoWiring anotherServiceWithAutoWiring
    	  = (AnotherServiceWithAutoWiring) context.getBean("anotherServiceWithAutoWiring");
    	
    	anotherServiceWithAutoWiring.doThing();

        U.print("Calling async method, threadId="+Thread.currentThread().getId() );
        anotherServiceWithAutoWiring.doThingAsync();

        ValueBean valueBean = ( ValueBean ) context.getBean("valueBean");

        U.print("valueBean is"+ valueBean );

//        Expression expression = new SpelExpressionParser(  ).parseExpression( "@valueBean.getValue1()" );
//
//        U.print("SPEL EXPRESSION eval  of valueBean.value1="+ expression.getValue() );
//
//        U.print( "anotherServiceWithAutoWiring.getValueBeanValue1()="+anotherServiceWithAutoWiring.getValueBeanValue1() );

        CollectionTestingBean ctb = (CollectionTestingBean) context.getBean("collectionTestingBean");

        U.print( ctb.getInjectedList() );

        U.print( ctb.getInjectedSet() );

        U.print( ctb.getInjectedMap() );

        //Getting a bean by the implied type:
        //this will work, as only one bean of this class.
        CollectionTestingBean ctb2 = context.getBean( CollectionTestingBean.class );

        try
        {
            Donger d = context.getBean( Donger.class);
        }
        catch( Exception e )
        {
            U.print( "Exception thrown as more that one bean of class Donger" );
        }


        beanNameAndIdMessAbout( context );

//        try
//        {
//            //sleep for a bit to get the @Scheduled working:
//            Thread.sleep( 20000 );
//        }
//        catch ( InterruptedException e )
//        {
//            e.printStackTrace();
//        }


    	
    	
    	//this will make any destroy etc methods on the beans get fired off:
    	context.close();
    	
    	//this would also fire the destroys, registers a method to fire when the JVM closes:
    	context.registerShutdownHook();

        contextMessabout();

        parentAndChildBeanMessAbout();

        factoryBeanMessAbout();

        importedXMLFileMessAbout();

        constantsMessAbout();

        parentChildContextMessAbout();
    }

    private static void parentChildContextMessAbout()
    {
        AbstractApplicationContext parentContext = new ClassPathXmlApplicationContext(
                "parentContext.xml");

        String [] childContexts =  {"childContext.xml"};

        AbstractApplicationContext childContext = new ClassPathXmlApplicationContext( childContexts, parentContext );

        ValueBean valBean = childContext.getBean( ValueBean.class );

        //this one comes from the child context file:
        U.print( valBean.getValue1() );
        //this one comes from the parent context file:
        U.print( valBean.getValue2() );
    }

    private static void constantsMessAbout()
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "dongContext.xml");

        BeanWithConstants beanWithConstants = (BeanWithConstants)context.getBean( "beanWithConstants" );

        //constant done using util:constant static-field:
        U.print(beanWithConstants.getSomeCrap() );

        //set using a properties file:
        U.print(beanWithConstants.getMoreCrap());

    }

    private static void importedXMLFileMessAbout()
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "dongContext.xml");

        String importedBean = ( String ) context.getBean( "importedBean" );
        U.print("ImportedBean= "+importedBean );
    }

    private static void factoryBeanMessAbout()
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "dongContext.xml");

        Date factoryCreatedDate = (Date)context.getBean("dateCreatedByDateFactoryBean");

        //this will not work, as the factory bean does not implement FactoryBean interface
        //DateFactoryBean dfb = (DateFactoryBean)context.getBean("&dateCreatedByDateFactoryBean");

        //Note the way that a static class is used for the factory in this case:
        Date staticFactoryCreatedDate =  (Date)context.getBean("dateCreatedByStaticFactoryBean");

        int x=0;
    }

    private static void beanNameAndIdMessAbout( AbstractApplicationContext context )
    {
        U.print( "Beans in context are" );
        U.print(context.getBeanDefinitionNames());

        //Note the way a default ID has been given to the bean
        BeanWithNoNameOrId beanWithNoNameOrId = ( BeanWithNoNameOrId ) context.getBean( "com.hedges.springlearning.nameidtesting.BeanWithNoNameOrId#0" );
        U.print( "BeanWithNoNameOrId found with default name: "+beanWithNoNameOrId );

        //Bean with a name but no id still available, the name acts like the ID:
        BeanWithNameNoId beanWithNameNoId = (BeanWithNameNoId)context.getBean( "beanWithNameNoId" );
        U.print( beanWithNameNoId);

        //When an Id and a name are supplied, the Id wins for look ups.
        BeanWithNameAndId beanWithNameAndId = (BeanWithNameAndId)context.getBean( "beanWithNameAndId_id" );
        U.print( beanWithNameAndId );

        //Multiple names, can be gotten with any of them
        BeanWithManyNames beanWithManyNames1 = (BeanWithManyNames)context.getBean( "beanWithManyNames1" );
        BeanWithManyNames beanWithManyNames2 = (BeanWithManyNames)context.getBean( "beanWithManyNames2" );
        BeanWithManyNames beanWithManyNames3 = (BeanWithManyNames)context.getBean( "beanWithManyNames3" );
    }

    private static void parentAndChildBeanMessAbout()
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "dongContext.xml");

        try
        {
            ParentBean parentBean = (ParentBean)context.getBean( "parentBean" );
        }
        catch( Exception npe )
        {
            //npe will get thrown, as parentBean has not been instantiated, due to the fact that it has
            //abstract=true in its bean definition.
        }


        ChildBean childBean =  (ChildBean)context.getBean("childBean");
        U.print( childBean.getVal1() ); //same thing as is set to parentBean, due to the parent relationship.
        U.print( childBean.getVal2() );

        ChildOfTemplateBean childOfTemplateBean = (ChildOfTemplateBean) context.getBean( "childOfTemplateBean" );
        //value injected via the definition of TemplateBean
        U.print(childOfTemplateBean.getVal1());
    }

    private static void contextMessabout()
    {
        AbstractApplicationContext context;//context referencing a file in a package:
        context = new ClassPathXmlApplicationContext( "/com/hedges/test/packageContext.xml" );
        String s = ( String ) context.getBean( "aStringBean"  );
        U.print( s );
        //ClassPathXmlApplicationContext can be used for file System with file: prefix:
        context = new ClassPathXmlApplicationContext( "file:/home/rowland-hall/crap/fileContext.xml" );
        s = ( String ) context.getBean( "aStringBean"  );
        U.print(s);

        try
        {
        //FileSystemXmlApplicationContext defaults to file, path is relative:
        context= new FileSystemXmlApplicationContext( "/home/rowland-hall/crap/fileContext.xml" );
        s = ( String ) context.getBean( "aStringBean"  );
        U.print(s);
        }
        catch( Exception e)
        {
            //cos it defaults to relative file path, use file:prefix to get it to go to root:
            context= new FileSystemXmlApplicationContext( "file:/home/rowland-hall/crap/fileContext.xml" );
            s = ( String ) context.getBean( "aStringBean"  );
            U.print(s);

        }

        //but can be used for class path with classpath:prefix
        context= new FileSystemXmlApplicationContext( "classpath:/com/hedges/test/packageContext.xml" );
        s = ( String ) context.getBean( "aStringBean"  );
        U.print(s);

        try
        {
            //defaults to look for "applicationContext.xml" in WEB_INF
            context = new XmlWebApplicationContext();
        }
        catch( Exception e )
        {

        }

        //USE of http: prefix
        Resource resource = context.getResource( "http://www.getrussian.com/wp-content/uploads/2012/04/3968800811_05eb86b700_b-e1334088416488.jpg" );

        try
        {
            //how to read the resource to a file somewhere
            InputStream is = resource.getInputStream();
            OutputStream out =  new FileOutputStream( "testFile.jpg" );
            IOUtils.copy(is, out);
            out.flush();
            out.close();

        }
        catch ( IOException e )
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
