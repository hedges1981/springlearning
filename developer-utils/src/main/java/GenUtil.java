import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * Created by rowland-hall on 25/04/14
 *
 * Class to house miscellaneous utility functions, consider splitting into specialised classes if themes develop across
 * the methods.
 */
public class GenUtil
{
    private static final Logger LOGGER = Logger.getLogger( GenUtil.class );

    /**
     * Null safe array copier, if the input array is null, null is returned, else a shallow copy of the array is
     * returned.
     */
    public static <T> T[] safeCopyArray( T[] arrayToCopy )
    {
        T[] retArray;

        if( arrayToCopy == null )
        {
            retArray = null;
        }
        else
        {
            retArray = Arrays.copyOf( arrayToCopy, arrayToCopy.length );
        }

        return retArray;
    }


    /**
     * Superimposes the topMap on top of the bottomMap, entries from the topMap overwrite those in the
     * bottomMap with the same key.
     * Entries from both maps with non shared keys get retained.
     *
     * E.g.
     * bottomMap has entries: X->X1, Y->Y1
     * topMap has entries X-X2, Z->Z2
     *
     * the resulting map is: X->X2, Y-Y1, Z->Z2
     */
    public static <A,B> Map<A,B> superimposeMaps( Map<A, B> bottomMap, Map<A, B> topMap )
    {
        Map<A,B>  retMap = new HashMap( bottomMap );

        for( Map.Entry<A,B> entry : topMap.entrySet() )
        {
            A key = entry.getKey();
            B value = entry.getValue();

            if (retMap.containsKey(key))
            {
                LOGGER.debug(" overwriting  " + key + " was:" + retMap.get(key) +
                                     " willbe:" + value );
            }

            retMap.put(key, value);
        }

        return retMap;
    }

    public static void sleepForNMilliseconds( long n )
    {
        try
        {
            Thread.sleep( n );
        }
        catch ( InterruptedException e )
        {
            throw new RuntimeException( e );
        }
    }

    public static File unzipFileToFile( File zippedFile, File unzippedFile ) throws IOException
    {
        GZIPInputStream gis = new GZIPInputStream( new FileInputStream( zippedFile ) );
        OutputStream out = new FileOutputStream( unzippedFile );

        // Transfer bytes from the compressed file to the output file
        byte[] buf = new byte[ 1024 ];
        int len;
        while ( ( len = gis.read( buf ) ) > 0 )
        {
            out.write( buf, 0, len );
        }
        // Close the file and stream
        gis.close();
        out.close();
        return unzippedFile;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit )
    {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static String getCurrentWorkingDirectory()
    {
        return System.getProperty("user.dir");
    }

    public static String getTempDirectory()
    {
        String tempDir = System.getProperty("java.io.tmpdir");
        tempDir = StringUtils.removeEnd( tempDir, File.separator );

        return tempDir;
    }

    /**

     * @param pathToFile e.g: com/sage/ssdp/utils/testFile.txt
     */
    public static File getFileFromClassPath( String pathToFile )
    {
        return new File( ClassLoader.getSystemResource( pathToFile ).getFile());
    }

    public static double convertBytesToMegabytes( long bytes )
    {
        double numberofBytesInAMb = 1048576;

        double numberofBytes = bytes;

        return numberofBytes / numberofBytesInAMb;
    }

    /**
     * If the supplied exception wraps an exception of the specified type, finds it and returns it, else returns null.
     */
    public static <T extends Throwable> T getWrappedException( Class<T> exceptionClass, Throwable e )
    {
        T retVal = null;

        do
        {
            if( e.getClass().getName().equals( exceptionClass.getName() ) )
            {
                retVal = (T)e;
            }
        }
        while((e = e.getCause()) != null);

        return retVal;
    }

    public static Throwable getRootCause( Throwable t )
    {
        Throwable rootCause = t;

        while( t.getCause() != null )
        {
            rootCause = t.getCause();
            t = rootCause;
        }

        return rootCause;
    }

    /**
     * Assumes that a 'success' status code begins with 2. If it doesn't begin with 2, it is not a success status code.
     */
    public static boolean responseCodeIndicatesSuccess( int statusCode )
    {
        String s = ""+statusCode;
        return s.startsWith( "2" );
    }

    /**
     * @param timestamp number of milliseconds since 01-01-1970
     */
    public static XMLGregorianCalendar getXMLGregorianCalendarFromTimestamp( long timestamp )
    {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime( new Date( timestamp ));
        try
        {
            return  DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        }
        catch ( DatatypeConfigurationException e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Converts a xml timestamp (e.g. 2014-06-26T10:21:14.3563208Z) to a milliseconds since 01-01-1970 timestamp
     */
    public static long convertXMLTimestampToTimestamp( String xsdDateTime )
    {
        return convertXMLTimestampToDate( xsdDateTime ).getTime();
    }

    /**
     Converts date to a xml timestamp (e.g. 2014-06-26T10:21:14.3563208Z)
     */
    public static String convertDateToXMLDateTimeStamp( Date date )
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return sdf.format( date );
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar( Date date )
    {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        try
        {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        }
        catch (DatatypeConfigurationException e)
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Converts a xml timestamp (e.g. 2014-06-26T10:21:14.3563208Z) to a Date
     */
    public static Date convertXMLTimestampToDate( String xsdDateTime )
    {
        return DatatypeConverter.parseDateTime( xsdDateTime ).getTime();
    }

    public static <T>  T getObjectFromXML( String xml, Class<T> desiredClass )
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance( desiredClass );

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader stringReader = new StringReader( xml );

            JAXBElement<T> jaxbElt = (JAXBElement<T>)jaxbUnmarshaller.unmarshal( stringReader );

            return jaxbElt.getValue();
        }
        catch ( JAXBException e )
        {
            throw new RuntimeException( e );
        }
    }

    public static byte[] getByteArrayFromClassPath( String classpathReference )
    {
        InputStream is = GenUtil.class.getClassLoader().getResourceAsStream( classpathReference );
        byte[] bytes;

        try
        {
            bytes = IOUtils.toByteArray( is );
        }
        catch ( IOException e )
        {
            throw new IllegalStateException( e );
        }

        return bytes;
    }

    public static String getDecodedBase64( String base64String )
    {
        byte[] ba = org.apache.commons.codec.binary.Base64.decodeBase64( base64String );

        return new String( ba);
    }

    public static String encodeAsBase64( byte[] thingToEncode )
    {
        return new String( org.apache.commons.codec.binary.Base64.encodeBase64( thingToEncode ));
    }

    public static Time getCurrentTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String timeStr = dateFormat.format(cal.getTime());

        return Time.valueOf( timeStr );
    }

}

