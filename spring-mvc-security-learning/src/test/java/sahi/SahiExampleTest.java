package sahi;

import net.sf.sahi.test.TestRunner;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rowland-hall on 13/07/15
 */
public class SahiExampleTest
{
    @Test
    public void runSahiExampleTest() throws IOException, InterruptedException
    {
        String suiteName = "scripts/demo/integration.sah";
        String browserType = "firefox";
        String base = "http://sahi.co.in/demo/training/";
        String threads = "1";

        TestRunner testRunner =
            new TestRunner(suiteName, browserType, base, threads);
        HashMap<String, Object> variableHashMap = new HashMap<String, Object>();
        variableHashMap.put("$user", "test");
        variableHashMap.put("$pwd", "secret");
        testRunner.setInitJS(variableHashMap);
        String status = testRunner.execute();
        System.out.println(status);
    }
}
