package com.hedges.integrationtestlearning;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by rowland-hall on 13/08/15
 */
public class ValidationScanTest
{
    @Test
    public void  readFile() throws IOException
    {
        String filePath = "/home/rowland-hall/Desktop/validation.log";

        List<String> lines = FileUtils.readLines( new File( filePath ) );

        for( String line : lines )
        {
            if( line != null && line.contains( "Invalid:" ) && !line.contains( "productId :: null" ) && !line.contains( "versionId :: null" ))
            {
                System.out.println( line );
            }
        }
    }
}
