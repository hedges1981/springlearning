import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by rowland-hall on 26/02/16
 */
public class ConvertsExcelColumnToCommaSeparatedListOfStrings
{
    public static void main( String[] args ) throws IOException
    {
        new ConvertsExcelColumnToCommaSeparatedListOfStrings().printCommaSeparatedListFromFile();
    }

    public void printCommaSeparatedListFromFile() throws IOException
    {
        String filePath = "/home/rowland-hall/Desktop/tedious.txt";

        List<String> strings = FileUtils.readLines( new File( filePath ) );

        String outputString = "'";

        for( String s: strings )
        {
            outputString =outputString+s+"','";
        }

        System.out.println(outputString);
    }
}
