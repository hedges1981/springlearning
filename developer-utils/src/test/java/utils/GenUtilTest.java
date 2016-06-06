package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.GenUtil;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rowland-hall on 25/04/14
 */
public class GenUtilTest
{
    @Test
    public void shouldReturnNullForNullArrayCopy()
    {
        Object [] nullArr = null;

        Object [] copyOfNullArr = GenUtil.safeCopyArray( nullArr );

        assertThat( copyOfNullArr).isEqualTo( null );
    }



    @Test
    public void shouldCopyArray()
    {
        Object o1 = new Object();
        Object o2 = new Object();

        Object[] inArr = {o1,o2};

        Object[] outArr = GenUtil.safeCopyArray( inArr );

        //check same contents:
        assertThat( inArr[0] ).isEqualTo( outArr[0] );
        assertThat( inArr[1] ).isEqualTo( outArr[1] );

        //check different object:
        assertThat( inArr != outArr );
    }

    @Test
    public void shouldHandleEmptyArray()
    {
        Object[] arr = {};
        Object[] arr2 = GenUtil.safeCopyArray( arr );

        assertThat( arr2.length).isEqualTo(0);
    }

    @Test
    public void shouldSuperimposeTwoMaps()
    {
        Map<String,String> bottomMap = new HashMap<String, String> ();
        bottomMap.put("A", "A1");
        bottomMap.put("B", "B1");

        Map<String,String> topMap = new HashMap<String, String> ();
        topMap.put("A", "A2");
        topMap.put("C", "C2");

        Map<String,String> retMap = GenUtil.superimposeMaps(bottomMap, topMap);

        assertThat(retMap.size()).isEqualTo(3);
        assertThat("A2").isEqualTo(retMap.get("A")); //shared key, so entry from mapToMergeIn entry gets written
        assertThat("B1").isEqualTo(retMap.get("B")); //only in lessImportantMap, so retained as is
        assertThat("C2").isEqualTo(retMap.get("C"));  // only in moreImportantMap, so retained as is.
    }

    @Test
    public void shouldReadFileFromClassPath() throws IOException
    {
        //-------------------------WHEN---------------------------------------
        File file = GenUtil.getFileFromClassPath( "apackage/testFile.txt" );
        String fileAsString = FileUtils.readFileToString( file );

        //------------------------THEN-----------------------------------------
        assertEquals("testFile", fileAsString );
    }

    @Test
    public void shouldConvertBytesToMegabytes()
    {
        double gb = GenUtil.convertBytesToMegabytes( 10486074572l );

        assertEquals( gb, 10000.3, 0.01 );
    }

    @Test
    public void testGetWrappedException()
    {
        //------------------GIVEN ----------------------------------------
        //Create a chain of wrapped exceptions:
        Exception rte = new RuntimeException();
        IllegalStateException ise = new IllegalStateException( rte );
        IllegalArgumentException iae = new IllegalArgumentException( ise );

        //---------------WHEN ---------------------------------------------
        IllegalArgumentException iae2 = GenUtil.getWrappedException( IllegalArgumentException.class, iae );
        //--------------THEN-------------------------------------------
        assert(iae2 == iae);

        //---------------WHEN ---------------------------------------------
        IllegalStateException ise2 = GenUtil.getWrappedException( IllegalStateException.class, iae );
        //--------------THEN-------------------------------------------
        assert(ise2==ise);

        //---------------WHEN ---------------------------------------------
        RuntimeException rte2 = GenUtil.getWrappedException( RuntimeException.class, iae );
        //--------------THEN-------------------------------------------
        assert(rte2== rte);

        //---------------WHEN ---------------------------------------------
        //no exception of this type wrapped, so should return null:
        IOException notThereException = GenUtil.getWrappedException( IOException.class, iae );
        //--------------THEN-------------------------------------------
        assert( notThereException == null );
    }

    @Test
    public void shouldGetRootCauseOfThrowable()
    {
        Exception rte = new RuntimeException(  ) ;

        //no cause, so should give same exception:
        assertTrue( rte == GenUtil.getRootCause( rte ));

        //1 level deep:
        IOException ioe = new IOException(  );
        rte = new RuntimeException( ioe );
        assertTrue( ioe == GenUtil.getRootCause( rte ) );

        //2 levels deep:
        Exception e1 = new RuntimeException();
        Exception e2 = new RuntimeException( e1 );
        Exception e3 = new RuntimeException( e2 );
        assertTrue( e1 == GenUtil.getRootCause( e3 ) );
    }

    @Test
    public void testResponseIndicatesSuccess( )
    {
        doTestResponseIndicatesSuccess( 200, true );
        doTestResponseIndicatesSuccess( 201, true );
        doTestResponseIndicatesSuccess( 202, true );
        doTestResponseIndicatesSuccess( 204, true );
        doTestResponseIndicatesSuccess( 207, true );
        doTestResponseIndicatesSuccess( 301, false );
        doTestResponseIndicatesSuccess( 400, false );
        doTestResponseIndicatesSuccess( 500, false );
    }
    void doTestResponseIndicatesSuccess( int statusCode, boolean expectedValue )
    {
        boolean retVal = GenUtil.responseCodeIndicatesSuccess( statusCode );
        assertEquals( retVal, expectedValue);
    }

    @Test
    public void testGetXMLGregorianCalendarFromTimestamp()
    {
        long _27_02_1981_12_55_23 = 352126523000l;  //timestamp in ms of 27-02-1981 12:55:23

        XMLGregorianCalendar cal = GenUtil.getXMLGregorianCalendarFromTimestamp( _27_02_1981_12_55_23 ) ;

        assertEquals( cal.getYear(), 1981 );
        assertEquals( cal.getMonth(), 2 );
        assertEquals( cal.getDay(), 27 );
        assertEquals( cal.getHour(), 12 );
        assertEquals( cal.getMinute(), 55 );
        assertEquals( cal.getSecond(), 23 );
        assertEquals( cal.getMillisecond(), 0 );
    }

    @Test
    public void testConvertXSDDateTimeToDate( )
    {
        //Run the same test with different formats of the same dateTime;
        doTestConvertXSDDateTimeToDate( "2014-01-13T15:32:37.9738133Z" );
        doTestConvertXSDDateTimeToDate( "2014-01-13T15:32:37.9738133" );
        doTestConvertXSDDateTimeToDate( "2014-01-13T15:32:37Z" );
        doTestConvertXSDDateTimeToDate( "2014-01-13T15:32:37" );
    }

    @Test
    public void testConvertXSDDateTimeToTimeStamp()
    {
        long ts = GenUtil.convertXMLTimestampToTimestamp( "1981-02-27T12:55:23" );
        assertEquals(ts, 352126523000l);
    }


    private void doTestConvertXSDDateTimeToDate( String xsdDateTime )
    {
        Date date = GenUtil.convertXMLTimestampToDate( xsdDateTime );

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals( cal.get( Calendar.YEAR ), 2014 );
        assertEquals(cal.get(Calendar.MONTH),0);//Remember, January is month 0.
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),13);
        assertEquals(cal.get(Calendar.HOUR_OF_DAY),15);
        assertEquals(cal.get(Calendar.MINUTE),32);
        assertEquals(cal.get(Calendar.SECOND),37);
    }

    @Test
    public void testConvertDateToXSDDateTimeStamp()
    {
        Calendar cal = Calendar.getInstance();
        cal.set( 2014,6,13,15,32,37 );
        cal.set(Calendar.MILLISECOND, 0);

        Date date = cal.getTime();

        String xmlTimeStamp = GenUtil.convertDateToXMLDateTimeStamp( date );

        assertEquals( "2014-07-13T15:32:37", xmlTimeStamp );

        //convert the xmlTimestamp back to a date, make sure it is valid:
        Date date2 = GenUtil.convertXMLTimestampToDate( xmlTimeStamp );

        assertTrue( date2.compareTo( date ) == 0 );
    }


    @Test
    public void testEncodeAsBase64()
    {
        String thingToEncode = "thingToEncode";

        String base64 = GenUtil.encodeAsBase64( thingToEncode.getBytes() );

        assertEquals( "dGhpbmdUb0VuY29kZQ==", base64 );
    }




}
