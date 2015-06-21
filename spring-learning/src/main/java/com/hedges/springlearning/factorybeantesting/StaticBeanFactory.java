package com.hedges.springlearning.factorybeantesting;

import java.util.Date;

/**
 * Created by rowland-hall on 14/11/14
 */
public class StaticBeanFactory
{
    public static Date getDateStatic()
    {
        return new Date();
    }

}
