package rest;

import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by rowland-hall on 08/06/16
 */
public class JerseyTestUtils
{
    public static Response getResponseForGet( String path, Map<String, String> queryParams, String accepts, JerseyTest jerseyTest )
    {
        WebTarget webTarget = jerseyTest.target().path( path );

        for( Map.Entry<String,String> entry : queryParams.entrySet() )
        {
            webTarget = webTarget.queryParam( entry.getKey(), entry.getValue() );
        }

        return webTarget.request( accepts ).get( Response.class );
    }

    public static Response getResponseForPost( Object obj, String path, String accepts , JerseyTest jerseyTest)
    {
        return getResponse( obj, path, accepts, jerseyTest);
    }

    public static Response getResponseForGet( String rawURI, String accepts , JerseyTest jerseyTest)
    {
        return jerseyTest.client().target( rawURI ).request( accepts ).get( Response.class);
    }

    public static Response getResponse( Object obj, String path, String accepts, JerseyTest jerseyTest )
    {
        return jerseyTest.target().path(path).request(accepts).
                post( Entity.xml( obj ), Response.class);
    }
}
