package com.hedges.springlearningpart2;

/**
 * Created by rowland-hall on 22/01/15
 */
public class AutoWiringTestBean2
{
    private AutoWiringTestBean1 autoWiringTestBean1;

    public void setAutoWiringTestBean1( AutoWiringTestBean1 autoWiringTestBean1 )
    {
        this.autoWiringTestBean1 = autoWiringTestBean1;
    }

    public AutoWiringTestBean1 getAutoWiringTestBean1()
    {
        return autoWiringTestBean1;
    }
}
