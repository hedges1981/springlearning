package utils;

import org.junit.Test;
import utils.SizeLimitedHashMap;

/**
 * Created by rowland-hall on 22/04/15
 */
public class SizeLimitedHashMapTest
{
    @Test
    public void shouldLimitSize()
    {
        //----------------------------given--------------------------------------------------
        int limit = 10;
        SizeLimitedHashMap<String,String> sizeLimitedHashMap = new SizeLimitedHashMap( limit );

        //----------------------------when--------------------------------------------------
        for( int i = 0; i<(limit*2); i++ )
        {
            sizeLimitedHashMap.put(""+i, ""+i );
        }

        //----------------------------then--------------------------------------------------
        assert(sizeLimitedHashMap.size() == limit );
    }
}
