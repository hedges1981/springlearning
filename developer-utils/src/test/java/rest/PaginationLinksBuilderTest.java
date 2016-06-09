package rest;

import org.junit.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;

/**
 * Created by rowland-hall on 08/06/16
 *
 * Very boiler plate, but couldn't find default way of doing it with Jersey....
 * Use of spring data rest for rest APIs does all this stuff automatically.
 */

public class PaginationLinksBuilderTest
{
    private static final String BASE_TEST_URL = "http://somewhere.com/test";
    private static final String UN_PAGINATED_LINK = "http://somewhere.com/test?queryParam2=queryParamValue2&queryParam2=queryParamValue2a&queryParam1=queryParamValue1";
    PaginationLinksBuilder paginationLinksBuilder = new PaginationLinksBuilder();

    MultivaluedMap<String,String> testQueryParams = new MultivaluedHashMap<String,String>();
    {
        testQueryParams.put( "queryParam1" , Arrays.asList( "queryParamValue1" ));
        testQueryParams.put( "queryParam2" , Arrays.asList( "queryParamValue2", "queryParamValue2a" ));
    }

    @Test
    public void shouldBuildFirstLink()
    {
        //limit less than total available:
        Link link = paginationLinksBuilder.getFirstLink( BASE_TEST_URL, 3, 4, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=3>; rel=\"first\"", link.toString()) ;

        //limit more than total available, limit in link = total available:
        link = paginationLinksBuilder.getFirstLink( "http://somewhere.com/test", 11, 10, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=10>; rel=\"first\"", link.toString() );

      //none available:
        link = paginationLinksBuilder.getFirstLink( "http://somewhere.com/test", 11, 0, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=0>; rel=\"first\"", link.toString());
    }

    @Test
    public void shouldBuildPreviousLink()
    {
        //initial offset = 0, so no prev link:
        Link link = paginationLinksBuilder.getPrevLink( "http://somewhere.com/test", 0, 10, testQueryParams );
        assertNull( link );

        //initial offset less than the limit, so previous link should be from 0 to initial offset:
        link = paginationLinksBuilder.getPrevLink( "http://somewhere.com/test", 5, 10, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=5>; rel=\"prev\"", link.toString() );

        //initial offset > limit, so link should say new offset = initialOffset - limit, limit = limit:
        link = paginationLinksBuilder.getPrevLink( "http://somewhere.com/test", 4, 2, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=2&limit=2>; rel=\"prev\"", link.toString() );
    }

    @Test
    public void shouldBuildNextLink()
    {
        //(initial offset + limit) <  total available, so new offset = initial offset + limit, limit  = limit
        Link link = paginationLinksBuilder.getNextLink( "http://somewhere.com/test", 5, 10, 1000, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=15&limit=10>; rel=\"next\"", link.toString()) ;

        //(initial offset + limit) > (total available+limit), so limit =  totalAvailable - ( initial offset + limit)
        link = paginationLinksBuilder.getNextLink( "http://somewhere.com/test", 1, 2, 4, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=3&limit=1>; rel=\"next\"", link.toString() );

        //(intial offset + limit) > totalAvailable, ie. this page contains the last results, so no next link.
        link = paginationLinksBuilder.getNextLink( "http://somewhere.com/test", 1, 4, 4, testQueryParams );
        assertNull(link);
   }

    @Test
    public void shouldBuildLastLink()
    {
        //none available, offset should = 0, limit should =0.
        Link link = paginationLinksBuilder.getLastLink( "http://somewhere.com/test", 12, 0, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=0>; rel=\"last\"", link.toString() );

        // limit < totalAvailable, so offset should = (total available pages * limit), limit = totalAvailable % limit
        link = paginationLinksBuilder.getLastLink( "http://somewhere.com/test", 5, 21, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=20&limit=1>; rel=\"last\"", link.toString() );

        //limit > totalAvailable , so offset should =0, limit should = totalAvailable:
        link = paginationLinksBuilder.getLastLink( "http://somewhere.com/test", 5, 3, testQueryParams );
        assertEquals( "<" + UN_PAGINATED_LINK + "&offset=0&limit=3>; rel=\"last\"", link.toString() );
   }

    @Test
    public void shouldBuildFullLinkHeaderValue()
    {
        //right in the middle of the results set, so expecting all 4 links ( first, last, next, prev)
        List<Link> links = paginationLinksBuilder.getPaginationLinks( "http://somewhere.com/test", 3, 3, 10, testQueryParams );
        assertEquals(4, links.size());

        //right at the start of the results set, so expecting no prev link:
        links = paginationLinksBuilder.getPaginationLinks( "http://somewhere.com/test", 0, 3, 10, testQueryParams );
        assertEquals( 3, links.size() );
        assertFalse( links.toString().contains( PaginationLinksBuilder.PREV ));

        //at the end of the results set, so expecting no next link:
        links = paginationLinksBuilder.getPaginationLinks( "http://somewhere.com/test", 9, 3, 10, testQueryParams );
        assertEquals( 3, links.size() );
        assertFalse( links.toString().contains( PaginationLinksBuilder.NEXT ));
    }

}

