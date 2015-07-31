package com.hedges.jmxexample;

import com.hedges.springlearning.U;

/**
 * Created by rowland-hall on 31/07/15
 */
public class POJOMBeanImpl implements POJOMBean
{
    String info="some info";

    @Override
    public String getInfo()
    {
        return info;
    }

    @Override
    public void setInfo( String info )
    {
        this.info = info;
    }

    @Override
    public void doSomething()
    {
        U.print(" POJOMBeanImpl Doing something.");
    }
}
