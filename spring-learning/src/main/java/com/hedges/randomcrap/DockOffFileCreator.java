package com.hedges.randomcrap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by rowland-hall on 24/02/15
 */
public class DockOffFileCreator
{
    static int numberOfWrites = 30000000;

    public static void main( String[] args ) throws IOException
    {
        File f = new File( "dockOffFileNew.txt");

        BufferedWriter writer = new BufferedWriter( new FileWriter(f) );

        for( int i =0; i< numberOfWrites; i++  )
        {
            String randomString = UUID.randomUUID().toString();

            writer.append( randomString );

            if( i % 100000 == 0 )
            {
                int numberOfWritesLeft = numberOfWrites - i;
                System.out.println(numberOfWritesLeft + " writes left to write");
            }
        }

        writer.flush();
        writer.close();
    }
}
