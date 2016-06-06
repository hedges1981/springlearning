package amazonwebservices.sqs;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import utils.GenUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rowland-hall on 09/10/15
 */
public class SQSServiceImpl implements SQSService
{
    private AmazonSQS amazonSqs;

    private String amazonSqsQueueUrl;

    //private static final Logger LOGGER = LoggerFactory.getLogger( SQSServiceImpl.class );

    @Override
    public List<Message> getNextMessagesNoWait( int maxNumMessagesToGet )
    {
        return getNextMessages( maxNumMessagesToGet, 0 );
    }

    @Override
    public List<Message> getNextMessages( int maxNumMessagesToGet, int secondsToWaitForAMessage )
    {
      //  LOGGER.debug( "Receiving a max of "+maxNumMessagesToGet+" from queue: "+amazonSqsQueueUrl+" ,will wait a max of: "+secondsToWaitForAMessage+" seconds" );

        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest();
        receiveMessageRequest.setQueueUrl( amazonSqsQueueUrl );
        receiveMessageRequest.setWaitTimeSeconds( secondsToWaitForAMessage );
        receiveMessageRequest.setMaxNumberOfMessages( maxNumMessagesToGet );

        ReceiveMessageResult receiveMessageResult = amazonSqs.receiveMessage( receiveMessageRequest );

        List<Message> retList;

        if( receiveMessageResult == null )
        {
            retList = Collections.EMPTY_LIST;
        }
        else
        {
            retList = receiveMessageResult.getMessages();
        }

       // LOGGER.debug( "Received "+retList.size()+" messages" );

        return retList;
    }

    @Override
    public List<Message> blockingReceiveNextMessage( int maxNumMessagesToGet, int secondsToWaitBetweenPollingQueue )
    {
        long timeToWaitMs = secondsToWaitBetweenPollingQueue * 1000;

        List<Message> messages;

        while( true )
        {
            messages = getNextMessagesNoWait( maxNumMessagesToGet );

            if( messages.isEmpty() )
            {
                GenUtil.sleepForNMilliseconds( timeToWaitMs );
            }
            else
            {
               break;
            }
        }
        return messages;
    }

    @Override
    public void deleteMessagesFromQueue( List<Message> messages )
    {
       // LOGGER.debug( "deleting "+messages.size()+ " messages from queue: "+amazonSqsQueueUrl );

        List<DeleteMessageBatchRequestEntry> deleteMessageBatchRequestEntries = new ArrayList<DeleteMessageBatchRequestEntry>();

        for( Message message : messages )
        {
            DeleteMessageBatchRequestEntry deleteMessageBatchRequestEntry
                    = new DeleteMessageBatchRequestEntry( message.getMessageId(), message.getReceiptHandle() );

            deleteMessageBatchRequestEntries.add( deleteMessageBatchRequestEntry );
        }
        DeleteMessageBatchRequest deleteMessageBatchRequest = new DeleteMessageBatchRequest( amazonSqsQueueUrl, deleteMessageBatchRequestEntries );

        amazonSqs.deleteMessageBatch( deleteMessageBatchRequest );

       // LOGGER.debug( "deleted "+messages.size()+ " messages from queue: "+amazonSqsQueueUrl );
    }

    @Override
    public void deleteMessageFromQueue( Message message )
    {
        List<Message> list = new ArrayList<Message>();
        list.add( message );
        deleteMessagesFromQueue( list );
    }

    @Override
    public void createAndUseNewSqsQueue( String queueName )
    {
       // LOGGER.info( "Creating queue with name:"+queueName );
        CreateQueueResult createQueueResult = amazonSqs.createQueue( queueName );
        String sqsQueueUrl = createQueueResult.getQueueUrl();
       // LOGGER.info("Queue:"+queueName+"has been created, URL is:"+sqsQueueUrl );

        this.setAmazonSqsQueueUrl( sqsQueueUrl );
    }

    @Override
    public String subscribeQueueToTopic( AmazonSNS amazonSNS, String topicArn )
    {
       // LOGGER.info( "Subscribing queue  to topic:"+ topicArn );
        String sqsQueueSubscriptionArn = Topics.subscribeQueue( amazonSNS, amazonSqs, topicArn, this.amazonSqsQueueUrl );
       // LOGGER.info( "Subscribed queue to topic:"+ topicArn+" , sqsQueueSubscriptionArn = "+ sqsQueueSubscriptionArn );

        return sqsQueueSubscriptionArn;
    }

    @Override
    public int getTotalNumberOfVisibleAndNotVisibleMessagesOnQueue()
    {
        GetQueueAttributesRequest request = new GetQueueAttributesRequest( amazonSqsQueueUrl );

        request.withAttributeNames( QueueAttributeName.ApproximateNumberOfMessages, QueueAttributeName.ApproximateNumberOfMessagesNotVisible );

        GetQueueAttributesResult result = amazonSqs.getQueueAttributes( request );

        int numVisibleMessagesOnQueue = Integer.valueOf( result.getAttributes().get( QueueAttributeName.ApproximateNumberOfMessages.toString() ) );

        int numNotVisibleMessagesOnQueue = Integer.valueOf( result.getAttributes().get( QueueAttributeName.ApproximateNumberOfMessagesNotVisible.toString() ) );

        return numVisibleMessagesOnQueue + numNotVisibleMessagesOnQueue;
    }

    @Override
    public void deleteQueue()
    {
       // LOGGER.info("Deleting queue: "+this.amazonSqsQueueUrl );

        amazonSqs.deleteQueue( this.amazonSqsQueueUrl );
    }

    @Override
    public String getSQSQueueUrl()
    {
        return amazonSqsQueueUrl;
    }

    @Override
    public void sendMessage( String message )
    {
       // LOGGER.debug("sending message to queue: "+amazonSqsQueueUrl );

        amazonSqs.sendMessage( amazonSqsQueueUrl, message );

       // LOGGER.debug("sent message to queue: "+amazonSqsQueueUrl );
    }

    public void setAmazonSqs( AmazonSQS amazonSqs )
    {
        this.amazonSqs = amazonSqs;
    }

    public void setAmazonSqsQueueUrl( String amazonSqsQueueUrl )
    {
        this.amazonSqsQueueUrl = amazonSqsQueueUrl;
    }
}
