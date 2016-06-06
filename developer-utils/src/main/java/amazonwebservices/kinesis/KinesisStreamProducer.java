package amazonwebservices.kinesis;

/**
 * Created by rowland-hall on 10/02/15
 */
public interface KinesisStreamProducer
{
    void pushToKinesisStream( String s );
}
