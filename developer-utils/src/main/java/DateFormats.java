import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  Enumerates and centralises date formatting.
 *
 *  Note the use of ThreadLocal for performance reasons, taking into account the lack of thread safety in
 *  SimpleDateFormat
 *
 * Created by rowland-hall on 30/04/14
 */
public enum DateFormats
{
    yyyy_MM_dd_dashes("yyyy-MM-dd"),
    M_d_yyyy_slashes( "M/d/yyyy" ),
    date_time("yyyy-MM-dd HH:mm"),
    date_time_secs_mSecs("yyy-MM-dd HH:mm:ss,S"),

    /**
     * Formats a date so that it can be used in http headers:
     */
    http_date_format("EEE, dd MMM yyyy HH:mm:ss zzz");

    private final String formatString;

    DateFormats( String formatString )
    {
        this.formatString = formatString;
    }

    public String format(Date date)
    {
        return threadLocal.get().format(date);
    }

    public Date parse( String s ) throws ParseException
    {
        return threadLocal.get().parse( s );
    }

    private ThreadLocal<DateFormat> threadLocal =  new ThreadLocal< DateFormat >()
    {
        @Override
        protected DateFormat initialValue()
        {
            return new SimpleDateFormat( formatString );
        }
    };

}
