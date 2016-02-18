package com.hedges.integrationtestlearning;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by rowland-hall on 03/11/15
 */
public class FileGenerator
{
    @Test
    public void generateFileOfDesiredSize() throws IOException
    {
        int fileSizeInMb = 200;

        String fileName = "aFile_"+fileSizeInMb+"_mb";

        PrintWriter pw = new PrintWriter( fileName );

        for( int i = 0; i<(fileSizeInMb*27675); i++ )  //tests shown that 27675 guids lines roughly comes to 1 mb
        {
            String s = UUID.randomUUID().toString();
            pw.println( s );
        }

        pw.flush();
        pw.close();
    }


    @Test
    public void dd()
    {
        String s = "1234824\n" +
                "1275353\n" +
                "1445088\n" +
                "1697O00\n" +
                "1RIFTOK\n" +
                "418Z00B\n" +
                "7H684DW\n" +
                "A05K1BV\n" +
                "DRIFTED\n" +
                "DTUR9TS\n" +
                "FHRI027\n" +
                "FS76396\n" +
                "FZ91225\n" +
                "JPA8Y2A\n" +
                "ME00102\n" +
                "ME00175\n" +
                "ME00221\n" +
                "ME00269\n" +
                "ME00343\n" +
                "ME00357\n" +
                "ME00361\n" +
                "ME00404\n" +
                "OF2A39E\n" +
                "QTM0001\n" +
                "QUANTUM\n" +
                "RIFT2K9\n" +
                "TEAMTBE\n" +
                "TG604B2\n" +
                "THERIFT\n" +
                "U622222\n" +
                "VB00091";

        String [] sa = s.split("\n");

        List<String> list1 = Arrays.asList( sa );

        String s2 = "1234567\n" +
                "7654321\n" +
                "A346238\n" +
                "DEVELOP\n" +
                "H817121\n" +
                "INVALID\n" +
                "KHTEST2\n" +
                "KHTEST3\n" +
                "KHTEST5\n" +
                "KHTEST7\n" +
                "R156ROU";

        String[] sa2 = s2.split("\n");

        List<String> list2 = Arrays.asList( sa2 );


        for( String so: list1 )
        {
            if( list2.contains( so ))
            {
                throw new RuntimeException(  );
            }
        }

        for( String so: list2 )
        {
            if( list1.contains( so ))
            {
                throw new RuntimeException(  );
            }
        }




    }
}
