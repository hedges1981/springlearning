package rest;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rowland-hall on 07/06/16
 *
 * Builds the string to go into the link header , for use when returning a paged resource.
 *
 * Note: assumes that we are using offset and limit as the paging params, where the first element is offset =0;
 *
 * Very boiler plate, but couldn't find default way of doing it with Jersey....
 * Use of spring data rest for rest APIs does all this stuff automatically.
 */
public class PaginationLinksBuilder
{
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String FIRST = "first";
    public static final String PREV = "prev";
    public static final String NEXT = "next";
    public static final String LAST = "last";

    public List<Link> getPaginationLinks( String resourceUrl, int currentOffset, int limit, int totalAvailable, MultivaluedMap<String, String> queryParameters )
    {
        queryParameters = getQueryParametersNoOffsetAndLimit( queryParameters );

        List<Link> links = new ArrayList<Link>();

        links.add(getFirstLink( resourceUrl, limit, totalAvailable, queryParameters ));
        links.add(getPrevLink( resourceUrl, currentOffset, limit, queryParameters ));
        links.add(getNextLink( resourceUrl, currentOffset, limit, totalAvailable, queryParameters));
        links.add(getLastLink( resourceUrl, limit, totalAvailable, queryParameters ));

        List<Link> retList = new ArrayList<Link>();

        for( Link link: links )
        {
            if( link !=null)
            {
                retList.add( link );
            }
        }

        return retList;
    }

    private MultivaluedMap<String, String> getQueryParametersNoOffsetAndLimit( MultivaluedMap<String, String> queryParameters )
    {
        MultivaluedMap<String, String> retMap = new MultivaluedHashMap<String, String>( queryParameters );
        retMap.remove( OFFSET );
        retMap.remove( LIMIT );

        return retMap;
    }

    Link getLastLink( String resourceUrl, int limit, int totalAvailable, MultivaluedMap<String, String> queryParameters )
    {
        int newOffset;
        int newLimit;

        if( limit > totalAvailable )
        {
           newOffset = 0;
           newLimit = totalAvailable;
        }
        else
        {
            newOffset = (totalAvailable / limit) * limit;
            newLimit = totalAvailable % limit;
        }

        return getLink( resourceUrl, LAST, newOffset, newLimit, queryParameters );
    }

    Link getNextLink( String resourceUrl, int currentOffset, int limit, int totalAvailable, MultivaluedMap<String, String> queryParameters )
    {
        Link retVal = null;

        if( currentOffset + limit < totalAvailable )
        {
            int newOffset = currentOffset + limit;

            int newLimit;

            if( (newOffset + limit) > totalAvailable )
            {
               newLimit = totalAvailable - newOffset;
            }
            else
            {
                newLimit = limit;
            }

            retVal = getLink( resourceUrl, NEXT, newOffset, newLimit, queryParameters );
        }

        return retVal;
    }

    Link getPrevLink( String resourceUrl, int currentOffset, int limit, MultivaluedMap<String, String> queryParameters )
    {
        Link retVal = null;

        if( currentOffset > 0 )
        {
            int newOffset = Math.max( ( currentOffset - limit ), 0 );
            int newLimit = Math.min( currentOffset, limit );

            retVal = getLink( resourceUrl, PREV, newOffset, newLimit, queryParameters );
        }

        return retVal;
    }

    Link getFirstLink( String resourceUrl, int limit, int totalAvailable, MultivaluedMap<String, String> queryParameters )
    {
        int limitToSet = Math.min( limit, totalAvailable );

        return getLink( resourceUrl, FIRST, 0, limitToSet, queryParameters );
    }

    private Link getLink( String resourceUrl, String relationship, int offset, int limit, MultivaluedMap<String, String> queryParameters )
    {
        UriBuilder uriBuilder = UriBuilder.fromUri( resourceUrl );

        for( Map.Entry<String,List<String>> entry : queryParameters.entrySet() )
        {
            String[] sa = entry.getValue().toArray( new String[entry.getValue().size()] );
            uriBuilder.queryParam( entry.getKey(), sa );
        }

        uriBuilder.queryParam( OFFSET, offset );
        uriBuilder.queryParam( LIMIT, limit );

        return Link.fromUriBuilder( uriBuilder ).rel( relationship ).build();
    }


}
