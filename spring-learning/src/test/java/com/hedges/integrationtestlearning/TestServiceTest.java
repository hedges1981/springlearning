package com.hedges.integrationtestlearning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rowland-hall on 29/01/15
 */
@RunWith( SpringJUnit4ClassRunner.class)
//note the inherit locations on this, default is true, ones declared here are loaded after the superclass ones.
@ContextConfiguration(locations={"classpath:jpa-context.xml","classpath:testService-context.xml"}, inheritLocations=true )

@TransactionConfiguration(defaultRollback = true )        //default for defaultRollback is true, means it will rollback any transaction that the test has started.
@Transactional     //this means that spring will start a txn for each test method, could also annotate at method level as well.
public class TestServiceTest extends TestWithTestDataSourceContext
{
    @Autowired
    TestService testService;

    @Autowired
    TestTableDAO testTableDAO;

    @Test
    @DirtiesContext
    @Rollback(false) //tells it to rollback this tests transaction. Default is true.
    public void testSaveTestTable()
    {
       testService.setTestTableDao( testTableDAO );

       testService.saveTestTable( 1,"name","value" );
    }

    @Test
    public void testException()
    {
        assert(testService.getTestTableDao() == null );//should be null, as @DirtiesContext should have cause all context and beans to get reinitialised.
        //testService.throwAnException();
    }

    //this will get executed before each test started transaction is started.
    @BeforeTransaction
    public void aaa()
    {
        System.out.println("in Before transaction");
    }

    //this will get executed after each test started transaction.
    @AfterTransaction
    public void bbb()
    {
        System.out.println("in after transaction");
    }

    @Before
    public void setEnvironment()
    {
        //note that this property setting triggers the @IfProfileValue annotated test below.
        System.setProperty( "environment", "ci" );
    }



    @IfProfileValue( name = "environment", value="ci")
    //could pick any property here, reads them from System.getProperty("....");
    //you would put the environment in perhaps as a command line arg for a specific build.
    @Test
    public void aTest()
    {
        System.out.println("Running conditional Profile value test");
    }

    @Repeat(1000) //causes it to repeat the specified amount of times.
    @Test
    public void aRepeatingTest()
    {
        System.out.println("Repeating test");
    }

    @Test(timeout=200L)//timeout is measured in ms.
    public void aTestThatTimesOut() throws InterruptedException
    {
       Thread.currentThread().sleep(300);    //test will fail due to the timeout restriction.
    }


    @Repeat(10)
    @Timed(millis=2000)//this one means that in this case the total time taken for the 10 executions cannot be >2000 ms.
    @Test
    public void testWithCumulativeTimeOut() throws InterruptedException
    {
        Thread.currentThread().sleep(201);//gonna fail cos 10X201 >2000!
    }





}
