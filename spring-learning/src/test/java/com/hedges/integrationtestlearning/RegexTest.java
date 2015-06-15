package com.hedges.integrationtestlearning;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rowland-hall on 03/02/15
 */
public class RegexTest
{
    @Test
    public void regExTest()
    {
         String regEx = ".*Sage\\.UK\\.AD\\.Dividends.*";

        Pattern pattern = Pattern.compile(regEx);

        Matcher m= pattern.matcher( "lklklklklklkkklk,Sage.UK.AD.Dividends,fgfgfgfgfgfgfgf" );

        System.out.println(m.matches());
    }
}
