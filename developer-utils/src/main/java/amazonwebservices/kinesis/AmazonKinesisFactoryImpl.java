package amazonwebservices.kinesis;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;

/**
 * Created by rowland-hall on 10/02/15
 */
public class AmazonKinesisFactoryImpl implements AmazonKinesisFactory
{
    //private Logger LOGGER = LoggerFactory.getLogger(AmazonKinesisFactoryImpl.class);

    private final String kinesisStreamEndpoint;

    public AmazonKinesisFactoryImpl( String kinesisStreamEndpoint )
    {
        this.kinesisStreamEndpoint = kinesisStreamEndpoint;
    }

    @Override
    public AmazonKinesis getAmazonKinesis()
    {
        AmazonKinesisClient amazonKinesisClient = new AmazonKinesisClient( new DefaultAWSCredentialsProviderChain() );
        amazonKinesisClient.setEndpoint( kinesisStreamEndpoint );

        return amazonKinesisClient;
    }

}
