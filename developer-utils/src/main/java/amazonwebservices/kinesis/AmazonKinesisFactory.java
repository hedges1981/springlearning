package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.AmazonKinesis;

/**
 * Created by rowland-hall on 10/02/15
 */
public interface AmazonKinesisFactory
{
    AmazonKinesis getAmazonKinesis();
}
