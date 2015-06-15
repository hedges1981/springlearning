package com.hedges.springlearning.refandinnerbean;

/**
 * Created by rowland-hall on 18/11/14
 */
public class AGeneralBean1
{
    private AGeneralBean2 aGeneralBean2;

    private AGeneralBean3 aGeneralBean3;

    private String aString;

    public String getaString()
    {
        return aString;
    }

    public void setaString( String aString )
    {
        this.aString = aString;
    }

    public AGeneralBean2 getaGeneralBean2()
    {
        return aGeneralBean2;
    }

    public void setaGeneralBean2( AGeneralBean2 aGeneralBean2 )
    {
        this.aGeneralBean2 = aGeneralBean2;
    }

    public AGeneralBean3 getaGeneralBean3()
    {
        return aGeneralBean3;
    }

    public void setaGeneralBean3( AGeneralBean3 aGeneralBean3 )
    {
        this.aGeneralBean3 = aGeneralBean3;
    }
}
