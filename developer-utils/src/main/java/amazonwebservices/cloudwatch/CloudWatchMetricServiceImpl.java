package amazonwebservices.cloudwatch;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class CloudWatchMetricServiceImpl implements CloudwatchMetricService {

    //private static final Logger LOG = LoggerFactory.getLogger(CloudWatchMetricServiceImpl.class);

    private String namespace;
    protected AmazonCloudWatchClient cloudWatchClient;

    /**
     * Initialises the cloud watch supplier
     * @param namespace a name space eg. "lspcs/autoupdate"
     * @param region to override the default region that is set, should be used for testing purposes
     */
    public CloudWatchMetricServiceImpl( String namespace, String region )
    {
        this.namespace = namespace;
        cloudWatchClient = new AmazonCloudWatchClient( new DefaultAWSCredentialsProviderChain() );
        cloudWatchClient.setRegion( Regions.fromName(region) );
    }

    //Default constructor for testing
    CloudWatchMetricServiceImpl( String namespace )
    {
        this.namespace = namespace;
    }

    @Override
    public void pushMetric( String metricName, double value )
    {
        Collection<Dimension> noDimensions = null;
        doPushMetric( metricName, noDimensions, value );
    }

    @Override
    public void pushMetricAsync( final String metricName, final double value )
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
               pushMetric( metricName, value );
            }
        };

        new Thread( runnable ).start();
    }

    @Override
    public void pushMetricWithDimensions( String metricName, Map<String, String> dimensionNames, double value )
    {
        List<Dimension> dimensions = new ArrayList<Dimension>(  );

        for( Map.Entry<String,String> entry : dimensionNames.entrySet())
        {
            Dimension dimension = new Dimension().withName( entry.getKey() ).withValue( entry.getValue().toString() );
            dimensions.add( dimension );
        }

        doPushMetric( metricName, dimensions, value );
    }

    @Override
    public void pushMetricWithDimensionsAsync( final String metricName, final Map<String, String> dimensionNames, final double value )
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                pushMetricWithDimensions( metricName, dimensionNames, value );
            }
        };

        new Thread( runnable).start();
    }

    private void doPushMetric( String metricName, Collection<Dimension> dimensions, Double value )
    {
        //LOG.info( "Pushing value of: "+value+ " to metric: "+ metricName );

        if ( StringUtils.isBlank( metricName ) )
        {
            throw new IllegalArgumentException( "Metric name not set, please set a valid metric name to post cloud watch metric" );
        }
        //Configure the data point to push
        PutMetricDataRequest dataRequest = new PutMetricDataRequest();

        MetricDatum data = new MetricDatum();
        data.setTimestamp( new Date() );
        data.setMetricName( metricName );
        data.setValue( value );
        data.setDimensions( dimensions );

        dataRequest.setNamespace( namespace );
        dataRequest.withMetricData( data );

        //Push the data point
        cloudWatchClient.putMetricData( dataRequest );

        //LOG.info( "Pushed value of: "+value+ " to metric: "+ metricName );
    }


}
