package amazonwebservices.sqs;

import amazonwebservices.AWSRegionProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by rowland-hall on 22/10/15
 */
public class AmazonSqsClientFactory implements FactoryBean<AmazonSQS>
{
    private AWSCredentialsProviderChain awsCredentialsProviderChain;

    private String region;

    @Override
    public AmazonSQS getObject() throws Exception
    {
        AmazonSQS amazonSqs = new AmazonSQSClient( awsCredentialsProviderChain );
        Region region = AWSRegionProvider.getCurrentRegion( this.region );
        amazonSqs.setRegion( region );

        return amazonSqs;
    }

    @Override
    public Class<?> getObjectType()
    {
        return AmazonSQS.class;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }

    public void setAwsCredentialsProviderChain( AWSCredentialsProviderChain awsCredentialsProviderChain )
    {
        this.awsCredentialsProviderChain = awsCredentialsProviderChain;
    }

    public void setRegion( String region )
    {
        this.region = region;
    }


}
