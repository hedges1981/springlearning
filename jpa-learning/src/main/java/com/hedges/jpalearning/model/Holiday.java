package com.hedges.jpalearning.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * Created by rowland-hall on 16/10/15
 */
@Embeddable
public class Holiday
{
    @Column( name="start_date")
    private Date startDate;

    @Column(name="days")
    private int numDays;

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }

    public int getNumDays()
    {
        return numDays;
    }

    public void setNumDays( int numDays )
    {
        this.numDays = numDays;
    }
}
