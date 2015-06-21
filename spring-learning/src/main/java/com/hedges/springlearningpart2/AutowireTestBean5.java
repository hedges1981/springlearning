package com.hedges.springlearningpart2;

/**
 * Created by rowland-hall on 22/01/15
 */
public class AutowireTestBean5
{
    private AutowireTestBean4 autowireTestBean4;

    public AutowireTestBean4 getAutowireTestBean4()
    {
        return autowireTestBean4;
    }

    public void setAutowireTestBean4( AutowireTestBean4 autowireTestBean4 )
    {
        this.autowireTestBean4 = autowireTestBean4;
    }
}
