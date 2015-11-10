package com.hedges.integrationtestlearning;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
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
}
