package amazonwebservices.sns;

import java.util.List;

/**
 * Created by rowland-hall on 01/07/15
 *
 */
public interface SNSService
{
    void publishToTopic( String message );

    List<String> blockingReceiveMessagesFromSnsTopic( int waitTimeSeconds, int timeBetweenPollsSeconds );
}
