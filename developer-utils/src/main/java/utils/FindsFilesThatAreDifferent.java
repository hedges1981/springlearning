package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by rowland-hall on 25/02/16
 */
public class FindsFilesThatAreDifferent
{
    /*
    Starting from each base folder, goes through and compares files that have the same relative path and name, to see if they are different.

    NOTE: just good for java classes, as it strips away all the crap lines at the top until it finds 'public class'
     */

    String folder1BasePath =   "/home/rowland-hall/workspaces/pp_dv/SLS/common";

    String folder2BasePath = "/home/rowland-hall/workspaces/trunk_dev_ws/SLS/common";




    public void run() throws IOException
    {
        //build up a map keyed by realtive path of the files in each folder:
        Map<String,List<String>> folder1Files = getFileMap( folder1BasePath );

        Map<String, List<String>> folder2Files = getFileMap( folder2BasePath );

        //now compare the files:
        Map<String, Set<Integer>> differentLines = new HashMap<String, Set<Integer>>(  );

        Set<String>  filesWithDifferentNumbersOfLines = new TreeSet<String>();

        for( String relativePath : folder1Files.keySet() )
        {
            List<String>  folder1Lines = folder1Files.get( relativePath );
            List<String>  folder2Lines = folder2Files.get( relativePath );
            Set<Integer>  differentLinesInFile = new TreeSet<Integer>();

            int count = Math.max( folder1Lines.size(), folder2Lines.size() );

            if( folder1Lines.size() !=  folder2Lines.size())
            {
                filesWithDifferentNumbersOfLines.add( relativePath ) ;
            }

            for( int i =0; i< count; i++ )
            {
                String folder1Line = "";
                if( folder1Lines.size() > i )
                {
                    folder1Line = folder1Lines.get( i );
                }

                String folder2Line = "";
                if( folder2Lines.size() > i )
                {
                    folder2Line = folder2Lines.get( i );
                }

                if( !folder1Line.equals( folder2Line  ))
                {
                    differentLinesInFile.add( i );
                }
            }

            differentLines.put( relativePath, differentLinesInFile );
        }

        //output the differences:
        System.out.println( "*******************DIFFERNCES**************************" );

        for( String relativePath: differentLines.keySet() )
        {
            if( differentLines.get( relativePath ).size() > 0 )
            {
                System.out.println( relativePath + ":::" + differentLines.get( relativePath ) );
            }
        }

        //output files with different nunmbers of lines:
        System.out.println( "*******************FILES WITH DIFFERENT NUMBERS OF LINES **************" );
        System.out.println( filesWithDifferentNumbersOfLines );

        //finally, output files that are not there in each folder:
        System.out.println( "************files in folder1 but not folder2" );
        for( String folder1RelativePath: folder1Files.keySet() )
        {
            if( ! folder2Files.keySet().contains( folder1RelativePath ))
            {
                System.out.println( folder1RelativePath );
            }
        }

        System.out.println( "************files in folder2 but not folder1" );
        for( String folder2RelativePath: folder2Files.keySet() )
        {
            if( ! folder1Files.keySet().contains( folder2RelativePath ))
            {
                System.out.println( folder2RelativePath );
            }
        }



    }

    private Map<String, List<String>> getFileMap( String folderBasePath ) throws IOException
    {
        Map<String,List<String>> fileLines = new HashMap<String, List<String>> ();

        Collection<File> files =  FileUtils.listFiles( new File( folderBasePath ), null, true );

        for( File file: files )
        {
            String realtivePath = file.getAbsolutePath().replace( folderBasePath, "" ) ;
            List<String> lines = FileUtils.readLines( file );

            //get rid of all the crap at the top, so we are just  left with actual java class code lines
            List<String> codeLines = new ArrayList<String>();
            boolean haveHitCode = false;
            for( String line: lines )
            {
                if( haveHitCode )
                {
                    codeLines.add( line );
                }
                else if( line.trim().contains( "public" ) && line.trim().contains( "class" ) )
                {
                    haveHitCode = true;
                }

            }

            fileLines.put( realtivePath, codeLines );
        }

        return fileLines;

    }

    public static void main( String[] args ) throws IOException
    {
        new FindsFilesThatAreDifferent().run();
    }
}
