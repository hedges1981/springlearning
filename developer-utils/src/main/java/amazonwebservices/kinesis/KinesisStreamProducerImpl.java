package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.AmazonKinesis;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Note, this class pushes one string at a time, this is inefficient with respect to network calls! At the time of writing
 * this class is just in use for testing, if eventually used in production code, some kind of batching should be done, either
 * on a time interval or every n pushes.
 *
 * Created by rowland-hall on 10/02/15
 */
public class KinesisStreamProducerImpl implements KinesisStreamProducer
{
    private String kinesisStreamName;

    AmazonKinesis amazonKinesisClient;

    public KinesisStreamProducerImpl( String kinesisStreamName, AmazonKinesisFactory amazonKinesisFactory )
    {
        this.kinesisStreamName = kinesisStreamName;
        amazonKinesisClient = amazonKinesisFactory.getAmazonKinesis();
    }

    @Override
    public void pushToKinesisStream( String s )
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap( s.getBytes() );

        //random partition key used, this is because whatever the partition key is, kinesis hashes it to pick a shard
        //to push to. Using a random partition key each time ensures that there will be a random distribution across
        //whichever shards are in use, even when there is a re-shard.

        String randomPartitionKey = UUID.randomUUID().toString();
        amazonKinesisClient.putRecord( kinesisStreamName, byteBuffer, randomPartitionKey );
    }

}
