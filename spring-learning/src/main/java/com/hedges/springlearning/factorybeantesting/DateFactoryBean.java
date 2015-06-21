package com.hedges.springlearning.factorybeantesting;

import com.hedges.springlearning.U;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rowland-hall on 07/11/14
 */
public class DateFactoryBean
{
    private int yearFac;

    public Date getADate() throws Exception
    {
        U.print("in date factory Bean");
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.YEAR, yearFac );

        return cal.getTime();
    }


    public void setYearFac( int year )
    {
        this.yearFac = year;
    }

}
