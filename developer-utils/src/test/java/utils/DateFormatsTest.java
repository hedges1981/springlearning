package utils;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import utils.DateFormats;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * Created by rowland-hall on 30/04/14
 */
public class DateFormatsTest
{
    Date TEST_DATE = getTestDate();// 2014-09-30-23-59-59-999

    Set<DateFormats> failures = new HashSet<DateFormats>();

    @Test
    public void testDateFormats()
    {
        runTest( DateFormats.date_time_secs_mSecs, "2014-10-30 23:59:59,999" );
        runTest( DateFormats.M_d_yyyy_slashes, "10/30/2014" );
        runTest( DateFormats.http_date_format, "Thu, 30 Oct 2014 23:59:59 GMT" );

        if ( !failures.isEmpty() )
        {
            fail( "The following DateFormats have issues:" + failures );
        }
    }

    void runTest( DateFormats dateFormat, String expectedResult )
    {
        try
        {
            shouldFormatDateOk( dateFormat, expectedResult );
            shouldParseDateWithoutException( dateFormat );
        }
        catch ( Exception e )
        {
            failures.add( dateFormat );
        }
    }

    private void shouldFormatDateOk( DateFormats dateFormat, String expectedResult )
    {
        String result = dateFormat.format( TEST_DATE );

        if ( !expectedResult.equals( result ) )
        {
            throw new RuntimeException();
        }
    }

    private void shouldParseDateWithoutException( DateFormats dateFormat )
    {
        //a DateFormats should be able to parse the result of its formatting of a date:
        String dateAsString = dateFormat.format( new Date() );
        try
        {
            dateFormat.parse( dateAsString );
        }
        catch ( ParseException e )
        {
            throw new RuntimeException( e );
        }
    }

    Date getTestDate()
    {
        Date date = new Date();
        date = DateUtils.setYears( date, 2014 );
        date = DateUtils.setMonths( date, 9 );
        date = DateUtils.setDays( date, 30 );
        date = DateUtils.setHours( date, 23 );
        date = DateUtils.setMinutes( date, 59 );
        date = DateUtils.setSeconds( date, 59 );
        date = DateUtils.setMilliseconds( date, 999 );

        return date;
    }


}
