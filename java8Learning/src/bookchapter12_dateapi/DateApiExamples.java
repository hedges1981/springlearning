package bookchapter12_dateapi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

/**
 * java 8 has new date time API, examples of it here:
 *
 * Created by rowland-hall on 20/07/16
 */
public class DateApiExamples
{
    static void p( Object o)
    {
        System.out.println(o);
    }

    public static void main( String[] args)
    {
       // new LocalDate class, just represents a Date, no faffing about with timeZones, etc. Which is what you really need in 99% of cases!
        //LocalDate with specific settings:
        LocalDate date = LocalDate.of( 1999, Month.DECEMBER, 2 );
        p(date);

        //get today always:
        date = LocalDate.now();
        p(date);

        //LocalTime: similar concept to the LocalDate
        LocalTime time = LocalTime.of( 13,45,20);  //13:45:20s
        p(time);
        time = LocalTime.now(); //always current time
        p(time);

        //LocalDateTime: a combo DateTime object:
        //different ways of combining them:
        //pre existing date time objs:
        LocalDateTime dateTime = LocalDateTime.of( date, time );
        p(dateTime);

        //can make either a date or a time object into a date-time, e.g.
        dateTime =  date.atTime( time );
        p(dateTime);

        //make a fresh one from scratch:
        dateTime = LocalDateTime.of( 2014, Month.MARCH, 18, 12, 20, 20 );     //18-03-2014:12:20:20
        p(dateTime);

        //Instant ---- represents the number of seconds since 01 Jan 1970 00:00:00:00 GMT
        //Instant 10 secs after 0:
        Instant instant = Instant.ofEpochSecond( 10 );
        p(instant);
        //now:
        instant = Instant.now();
        p(instant);

        //Durations and Periods, working with differences between dates, times and instances:
        //e.g. check if difference between two times is > 3 mins:
        Duration _3mins = Duration.ofMinutes( 3 );
        LocalTime t1 = LocalTime.of(13, 40,00);
        LocalTime t2 = LocalTime.of( 13,46,00);

        Duration betweenTimes = Duration.between( t1, t2 );
        p(betweenTimes.compareTo( _3mins )); //is 1 cos is bigger than 3 mins

        //u can combine durations, e.g.
        Duration _6mins = _3mins.plus( Duration.ofMinutes( 3 ) );
        p(_6mins);

        //u can also do durations between LocalDate and also Instant in the same way:

        //Periods:
        //basically a way of expressing a duration in terms of y,m,d
        Period _1yr6mnths3days = Period.of( 1,6,3 );
        p(_1yr6mnths3days );

        //New objects are immutable, good for functional programming, however you can get other dates etc from them:
        //e.g.
        time = LocalTime.of(20,22,22);
        LocalTime time2 = time.minusHours( 3 ); //just one example, lots and lots of helper methods for doing this stuff:
        p(time2);

        //e.g. adjust the year of a date:
        date = LocalDate.of( 2000,12,2);
        date = date.withYear( 1999 );    // switched it to 1999.
        p(date);

        //TemporalAdjusters: allow you to do standard adjustments, e.g. move a date to the first day of month:
        date = date.with( TemporalAdjusters.firstDayOfMonth());
        p(date);
        // note that the with takes a function (Temporal) -> some processed Temporal, so you could make ur own custom function.

        //PRINTING AND PARSING:
        //*************************NOTE THAT ALL THE PARSE AND FORMAT STUFF IS THREAD SAFE!!!***********************
        //e.g. formatting a date, note how the format methods are on the date object:
        String dateStr = date.format( DateTimeFormatter.BASIC_ISO_DATE );
        p( dateStr );

        //parsing example:
        date = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);      //note again parse method on LocalDate class.
        p(date);
        //parsing example with a custom DateTimeFormatter:
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        date = LocalDate.parse("21/02/1999", dtf);
        p(date);

        //is a new way of building up a DateTime formatter also, e.g. this is the  DateTimeFormatter.BASIC_ISO_DATE one:
         //   = new DateTimeFormatterBuilder()
         //   .parseCaseInsensitive()
         //   .appendValue(YEAR, 4)
         //   .appendValue(MONTH_OF_YEAR, 2)
         //   .appendValue(DAY_OF_MONTH, 2)
         //   .optionalStart()
         //   .appendOffset("+HHMMss", "Z")
         //   .toFormatter( ResolverStyle.STRICT, IsoChronology.INSTANCE);

        //Working with Time Zones:
        ZoneId romeZone = ZoneId.of( "Europe/Rome" ); // the list of time zones is from the IANA Time Zone database, u can use any of them,
        //from the timezone it knows the rules, It also does daylight saving time auto matically.

        //e.g. changing the time so that we are working Rome:
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime romeZoneTime = localDateTime.atZone( romeZone );
        p("hour local="+localDateTime.getHour());

        int numberOfHoursDiffence = romeZoneTime.get( ChronoField.OFFSET_SECONDS )/3600;

        p("number of hours difference between here and Rome is:"+numberOfHoursDiffence );


    }
}
