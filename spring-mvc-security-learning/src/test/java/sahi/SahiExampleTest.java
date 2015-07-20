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

    /**
     - ensure fireFox installed on browser
     - follow: https://sahipro.com/docs/using-sahi/configuring-sahi-with-xvbf.html to get xvbf on box including configuring
     firefox to use it in the sahi browsers xml file.

     Note instructions are crap on there. basically:
     -download the file.
     -extract the Xvbf file from the tar.
     - move it to : /usr/X11R6/bin/Xvfb
     -chmod 777
     - create file in usr/bin with contents:

     #!/bin/sh
     mode=$1
     case "$mode" in
     'start')
     if [ -f /usr/X11R6/bin/Xvfb ]; then
     echo "<strong></strong><strong>Starting up the Virtual Frame Buffer on Screen 1</strong><strong></strong>"
     /usr/X11R6/bin/Xvfb :1 -screen 0 1152x900x8 &
     fi
     ;;
     *)
     echo " Usage: "
     echo " $0 start (start XVFB)"
     echo " $0 stop (stop XVFB - not supported)"
     exit 1
     ;;
     esac
     exit 0

     - to run it call Xvbf start.

     - to get sahi to use the headless firefox, put in browser_types.xml:

     <browserType>
     <name>firefox-xvfb</name>
     <displayName>Firefox</displayName>
     <icon>firefox.png</icon>
     <path>env DISPLAY=:1 firefox</path>
     <options>-profile "$userDir/browser/ff/profiles/sahi$threadNo" -no-remote</options>
     <processName>firefox</processName>
     <capacity>5</capacity>
     </browserType>


     Use browser type firefox-xvfb


     */
    Process sahiProcess;

    String sahiBinFolder = "/home/rowland-hall/sahi/bin";

    private void startUpSahi()
    {
        try
        {
            sahiProcess = Runtime.getRuntime().exec( sahiBinFolder+"/./sahi.sh", null, new File(sahiBinFolder) );
        }
        catch ( IOException e )
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
            String browserType = "firefox-xvfb";
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
