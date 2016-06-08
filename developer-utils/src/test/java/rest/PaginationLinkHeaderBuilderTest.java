package rest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by rowland-hall on 08/06/16
 *
 */
public class PaginationLinkHeaderBuilderTest
{
    PaginationLinkHeaderBuilder paginationLinkHeaderBuilder = new PaginationLinkHeaderBuilder();

    Map<String,String> testQueryParams = new HashMap<String,String>();
    {
        testQueryParams.put( "queryParam1" , "queryParamValue1");
        testQueryParams.put( "queryParam2" , "queryParamValue2");
    }

    @Test
    public void shouldBuildFirstLink()
    {
        //limit less than total available:
        String s = paginationLinkHeaderBuilder.getFirstLink( "http://somewhere.com/test", 3, 4, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"first\"",s);

        //limit more than total available, limit in link = total available:
        s = paginationLinkHeaderBuilder.getFirstLink( "http://somewhere.com/test", 11, 10, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=10>; rel=\"first\"",s);

        //none available:
        s = paginationLinkHeaderBuilder.getFirstLink( "http://somewhere.com/test", 11, 0, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=0>; rel=\"first\"",s);
    }

    @Test
    public void shouldBuildPreviousLink()
    {
        //initial offset = 0, so no prev link:
        String s = paginationLinkHeaderBuilder.getPrevLink( "http://somewhere.com/test", 0, 10, testQueryParams );
        assertNull(s);

        //initial offset less than the limit, so previous link should be from 0 to initial offset:
        s = paginationLinkHeaderBuilder.getPrevLink( "http://somewhere.com/test", 5, 10, testQueryParams );
        assertEquals("<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=5>; rel=\"prev\"", s );

        //initial offset > limit, so link should say new offset = initialOffset - limit, limit = limit:
        s = paginationLinkHeaderBuilder.getPrevLink( "http://somewhere.com/test", 4, 2, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=2&limit=2>; rel=\"prev\"",s);
    }

    @Test
    public void shouldBuildNextLink()
    {
        //(initial offset + limit) <  total available, so new offset = initial offset + limit, limit  = limit
        String s = paginationLinkHeaderBuilder.getNextLink( "http://somewhere.com/test", 5, 10, 1000, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=15&limit=10>; rel=\"next\"", s );

        //(initial offset + limit) > (total available+limit), so limit =  totalAvailable - ( initial offset + limit)
        s = paginationLinkHeaderBuilder.getNextLink( "http://somewhere.com/test", 1, 2, 4, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=3&limit=1>; rel=\"next\"",s);

        //(intial offset + limit) > totalAvailable, ie. this page contains the last results, so no next link.
        s = paginationLinkHeaderBuilder.getNextLink( "http://somewhere.com/test", 1, 4, 4, testQueryParams );
        assertNull(s);
    }

    @Test
    public void shouldBuildLastLink()
    {
        //none available, offset should = 0, limit should =0.
        String s = paginationLinkHeaderBuilder.getLastLink( "http://somewhere.com/test", 12, 0, testQueryParams );
        assertEquals("<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=0>; rel=\"last\"",s);

        // limit < totalAvailable, so offset should = (total available pages * limit), limit = totalAvailable % limit
        s = paginationLinkHeaderBuilder.getLastLink( "http://somewhere.com/test", 5, 21, testQueryParams );
        assertEquals("<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=20&limit=1>; rel=\"last\"",s);

        //limit > totalAvailable , so offset should =0, limit should = totalAvailable:
        s = paginationLinkHeaderBuilder.getLastLink( "http://somewhere.com/test", 5, 3, testQueryParams );
        assertEquals("<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"last\"",s);
    }

    @Test
    public void shouldBuildFullLinkHeaderValue()
    {
        //right in the middle of the results set, so expecting all 4 links ( first, last, next, prev)
        String linkHeader = paginationLinkHeaderBuilder.getLinkHeaderValue( "http://somewhere.com/test", 3, 3, 10, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"first\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"prev\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=6&limit=3>; rel=\"next\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=9&limit=1>; rel=\"last\",", linkHeader );
        //right at the start of the results set, so expecting no prev link:
        linkHeader = paginationLinkHeaderBuilder.getLinkHeaderValue( "http://somewhere.com/test", 0, 3, 10, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"first\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=3&limit=3>; rel=\"next\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=9&limit=1>; rel=\"last\",", linkHeader );
        //at the end of the results set, so expecting no next link:
        linkHeader = paginationLinkHeaderBuilder.getLinkHeaderValue( "http://somewhere.com/test", 9, 3, 10, testQueryParams );
        assertEquals( "<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=0&limit=3>; rel=\"first\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=6&limit=3>; rel=\"prev\",<http://somewhere.com/test?queryParam2=queryParamValue2&queryParam1=queryParamValue1&offset=9&limit=1>; rel=\"last\",", linkHeader );

    }

}

