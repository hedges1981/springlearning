package sahi;

import net.sf.sahi.test.TestRunner;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rowland-hall on 13/07/15
 */
public class SahiExampleTest
{

    Process sahiProcess;

    String sahiBinFolder = "/home/rowland-hall/sahi/bin";

    private void startUpSahi()
    {
        String startUpScriptFolder = System.getProperty( "startUpScriptFolder" );

        try
        {
            sahiProcess = Runtime.getRuntime().exec( sahiBinFolder+"/./sahi.sh", null, new File(sahiBinFolder) );
            //need to sleep a bit here to allow it to start up b4 running the test.
            Thread.currentThread().sleep(10000);
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Test
    public void runSahiExampleTest() throws IOException, InterruptedException
    {
        startUpSahi();

        try
        {
            //NOTE source code for sahi stuff found at: https://github.com/kevlened/Sahi/tree/master/sahi/src/net/sf/sahi
            //and: https://github.com/kevlened/Sahi/tree/master/sahi/test/net/sf/sahi

            //SAHI will need to be started from MAVEN, using ANT.

            //RUN A TEST:
            String suiteName = "/home/rowland-hall/personal_ws/springlearning-git/spring-mvc-security-learning/src/test/sahi/sahiExampleTest.sah";
            String browserType = "firefox";
            String base = "http://localhost:2702/springmvclearning/test/sahiexample/page1";
            String threads = "1";
            TestRunner testRunner =
                new TestRunner(suiteName, browserType, base, threads);
            HashMap<String, Object> variableHashMap = new HashMap<String, Object>();
            testRunner.setInitJS(variableHashMap);

            //Report report = new Report();
            //TODO: need to look at configuring the report, want to get detailed info about failures
            //testRunner.addReport( report );

            String status = testRunner.execute();
            System.out.println(status);
        }
        finally
        {
           sahiProcess.destroy();
        }
    }
}
