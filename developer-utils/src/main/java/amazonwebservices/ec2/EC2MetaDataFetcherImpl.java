package amazonwebservices.ec2;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by rowland-hall on 06/05/15
 */
public class EC2MetaDataFetcherImpl implements EC2MetaDataFetcher
{
    //private static final Logger LOGGER = Logger.getLogger( EC2MetaDataFetcherImpl.class );

    private String ec2metadataServiceHost;

    @Override
    public String getThisEC2InstanceId()
    {
        return getEC2MetaDatum( EC2MetadataPropertyNames.EC2_INSTANCE_ID_PROP_NAME, "instance-id" );
    }

    private String getEC2MetaDatum( String systemPropertyName, String pathToEc2MetadataServiceResource )
    {
        String ec2Metadatum = System.getProperty( systemPropertyName );

        if ( ec2Metadatum == null )
        {
           // LOGGER.warn( systemPropertyName+ " not found in system.properties, will attempt to retrieve it from the EC2MetaDataService and set it in system.properties" );

            ec2Metadatum = getEc2MetadataFromWebService( pathToEc2MetadataServiceResource );

            if( ec2Metadatum != null )
            {
                System.setProperty( systemPropertyName, ec2Metadatum );
            }
        }

        return ec2Metadatum;
    }

    private String getEc2MetadataFromWebService( String pathToResource )
    {
        pathToResource = StringUtils.removeStart( pathToResource, "/" );

        String retVal = null;

        try
        {
            URL ec2InstanceIdUrl = new URL( "http://"+ ec2metadataServiceHost +"/latest/meta-data/" + pathToResource );
            URLConnection connection = ec2InstanceIdUrl.openConnection();
            BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

            String inputLine;

            while ( (inputLine = in.readLine() ) != null )
            {
                retVal = inputLine;
            }
            in.close();
        }
        catch ( Exception e )
        {
            //LOGGER.warn("Exception occured fetching ec2MetaData: "+pathToResource+", returning NULL. are we on an ec2 instance?",e);
        }

        return retVal;
    }

    public void setEc2MetadataServiceHost( String metadataServiceHost )
    {
        this.ec2metadataServiceHost = metadataServiceHost;
    }
}
