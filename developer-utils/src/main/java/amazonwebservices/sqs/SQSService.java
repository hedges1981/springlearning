package amazonwebservices.sqs;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.model.Message;

import java.util.List;

/**
 * Allows for messages to be pushed to and received from a given queue
 *
 * Created by rowland-hall on 09/10/15
 */
public interface SQSService
{
    List<Message> getNextMessagesNoWait( int maxNumMessagesToGet );

    List<Message> getNextMessages( int maxNumMessagesToGet, int secondsToWaitForAMessage );

    List<Message> blockingReceiveNextMessage( int maxNumMessagesToGet, int secondsToWaitBetweenPollingQueue );

    String getSQSQueueUrl();

    void sendMessage( String message );

    void deleteMessageFromQueue( Message message );

    void deleteMessagesFromQueue( List<Message> messages );

    void createAndUseNewSqsQueue( String queueName );

    String subscribeQueueToTopic( AmazonSNS amazonSNS, String topicArn );

    int getTotalNumberOfVisibleAndNotVisibleMessagesOnQueue();

    void deleteQueue();
}
