package rest;

import javax.ws.rs.core.Link;
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
public class PaginationLinkHeaderBuilder
{
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";

    public String getLinkHeaderValue( String resourceUrl, int currentOffset, int limit , int totalAvailable, Map<String, String> queryParameters )
    {
        //get rid of any pagination params, as we will be changing them:
        queryParameters.remove( OFFSET );
        queryParameters.remove( LIMIT );

        List<String> links = new ArrayList<String>();

        links.add(getFirstLink( resourceUrl, limit, totalAvailable, queryParameters ));
        links.add(getPrevLink( resourceUrl, currentOffset, limit, queryParameters ));
        links.add(getNextLink( resourceUrl, currentOffset, limit, totalAvailable, queryParameters));
        links.add(getLastLink( resourceUrl, limit, totalAvailable, queryParameters ));

        String retVal = "";

        for( String link : links )
        {
            if( link != null )
            {
                retVal += link;
                retVal +=",";
            }
        }

        return retVal;
    }

    String getLastLink( String resourceUrl, int limit, int totalAvailable, Map<String, String> queryParameters )
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

        return getLinkString( resourceUrl, "last", newOffset, newLimit, queryParameters );
    }

    String getNextLink( String resourceUrl, int currentOffset, int limit, int totalAvailable, Map<String, String> queryParameters )
    {
        String retVal = null;

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

            retVal = getLinkString( resourceUrl, "next", newOffset, newLimit, queryParameters );
        }

        return retVal;
    }

    String getPrevLink( String resourceUrl, int currentOffset, int limit, Map<String, String> queryParameters )
    {
        String retVal = null;

        if( currentOffset > 0 )
        {
            int newOffset = Math.max( ( currentOffset - limit ), 0 );
            int newLimit = Math.min( currentOffset, limit );

            retVal = getLinkString( resourceUrl, "prev", newOffset, newLimit, queryParameters );
        }

        return retVal;
    }

    String getFirstLink( String resourceUrl, int limit, int totalAvailable, Map<String, String> queryParameters )
    {
        int limitToSet = Math.min( limit, totalAvailable );

        return getLinkString( resourceUrl,  "first", 0, limitToSet, queryParameters );
    }

    private String getLinkString( String resourceUrl, String relationship, int offset, int limit, Map<String, String> queryParameters )
    {
        UriBuilder uriBuilder = UriBuilder.fromUri( resourceUrl );

        for( Map.Entry<String,String> entry : queryParameters.entrySet() )
        {
            uriBuilder.queryParam( entry.getKey(), entry.getValue() );
        }

        uriBuilder.queryParam( OFFSET, offset );
        uriBuilder.queryParam( LIMIT, limit );

        return Link.fromUriBuilder( uriBuilder ).rel( relationship ).build().toString();
    }


}
