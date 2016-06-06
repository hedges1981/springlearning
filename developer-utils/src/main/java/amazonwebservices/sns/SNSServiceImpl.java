package amazonwebservices.sns;

import amazonwebservices.AWSRegionProvider;
import amazonwebservices.sqs.AmazonSQSConstants;
import amazonwebservices.sqs.SQSService;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import utils.GenUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by rowland-hall on 01/07/15
 */
public class SNSServiceImpl implements SNSService
{
    private AmazonSNS amazonSns;

    private String topicArn;

    private String region;

    private SQSService sqsService;

    private String sqsQueueSubscriptionArn;

    private boolean createdQueueAndSubscribedItToTopic;

    //private static final Logger LOGGER = LoggerFactory.getLogger( SNSServiceImpl.class );

    @Override
    public void publishToTopic( String message )
    {
      //  LOGGER.debug( "Publishing message: " + message + " to topic:" + topicArn );
        amazonSns.publish( topicArn, message );
       // LOGGER.debug( "Publised message: "+ message+" to topic:"+topicArn );
    }

    /**
     *
     * @param waitTimeSeconds : how long to wait for a message to come. Note this can be any integer, as opposed to the max of 20 imposed by
     * the AmazonSQS api.
     * @param timeBetweenPollsSeconds: how long to wait between polling the associated sqs queue, note that each request to SQS is charged, so
     *                               minimising the number of polls reduces costs.
     *
     * will block until a message is received
     */
    public List<String> blockingReceiveMessagesFromSnsTopic( int waitTimeSeconds, int timeBetweenPollsSeconds )
    {
        ensureSqsQueueCreatedAndSubscribedToTopic();

        Date timeStarted = new Date();
        List retList = new ArrayList();
        //LOGGER.info( "Receiving message from topic: "+topicArn+" using queue:"+sqsService.getSQSQueueUrl()+". Wait time is:"+waitTimeSeconds+" seconds" );

        while( retList.isEmpty() && !hasExceededMaxWaitTime( timeStarted, waitTimeSeconds  ) )
        {
            List<Message> messages = sqsService.getNextMessages( AmazonSQSConstants.SQS_MAX_MESSAGES_TO_FETCH_IN_ONE_GO, 0 );

            if( !messages.isEmpty() )
            {
                sqsService.deleteMessagesFromQueue( messages );

                retList = getMessageStringsFromMessages( messages );
             //   LOGGER.info( retList.size() + " messages received from topic" + topicArn );
            }
            else
            {
             //   LOGGER.debug( "No message received, will wait for:"+ timeBetweenPollsSeconds+" then poll again." );
                GenUtil.sleepForNMilliseconds( timeBetweenPollsSeconds * 1000 );
            }
        }

        return retList;
    }

    private boolean hasExceededMaxWaitTime( Date timeStarted, int waitTimeInSeconds )
    {
        long numberOfSecondsWaitingForMessage = GenUtil.getDateDiff( timeStarted, new Date(), TimeUnit.SECONDS );

        boolean retVal = false;

        if( numberOfSecondsWaitingForMessage > waitTimeInSeconds )
        {
            //LOGGER.debug( "Wait time exceeded." );
            retVal = true;
        }

        return retVal;
    }

    private void ensureSqsQueueCreatedAndSubscribedToTopic()
    {
        if ( !createdQueueAndSubscribedItToTopic )
        {
            synchronized ( this )
            {
                if ( !createdQueueAndSubscribedItToTopic )
                {
                    this.sqsQueueSubscriptionArn = createSQSQueueAndSubscribeItToThisTopic();
                    createdQueueAndSubscribedItToTopic = true;
                }
            }
        }
    }

    private String createSQSQueueAndSubscribeItToThisTopic()
    {
        String topicName = StringUtils.substringAfterLast( topicArn, ":" );

        String queueName = "tmpQ4Topic_"+topicName+"_"+ UUID.randomUUID();

        if(queueName.length() > AmazonSQSConstants.SQS_MAX_QUEUE_NAME_LENGTH  )
        {
            queueName = queueName.substring( 0,AmazonSQSConstants.SQS_MAX_QUEUE_NAME_LENGTH -1 );
        }

        sqsService.createAndUseNewSqsQueue( queueName );

        String retVal;

        try
        {
            retVal = sqsService.subscribeQueueToTopic( amazonSns, this.topicArn );
        }
        catch( RuntimeException e )
        {
            //LOGGER.error( "Exception subscribing queue to SNS topic, queue will be deleted:" );
            sqsService.deleteQueue();
            throw e;

        }

        return retVal;

    }

    /**
     * @param messages  a list of messages that contains a SNS notification as the body:
     */
    private List<String> getMessageStringsFromMessages( List<Message> messages )
    {
        List<String> retList = new ArrayList<String>();

        for(Message message: messages )
        {
            String messageBody = message.getBody();
            ObjectMapper mapper = new ObjectMapper();

            try
            {
                //convert JSON string to Map
                Map<String,String> map = mapper.readValue(messageBody, new TypeReference<HashMap<String,String>>(){});
                retList.add ( map.get("Message") );
            }
            catch( Exception e )
            {
                throw new RuntimeException( e );
            }
        }

        return retList;
    }

    /**
     * Set this as the init-method in Spring config:
     */
    public void init()
    {
        Region region = AWSRegionProvider.getCurrentRegion( this.region );
        amazonSns.setRegion( region );
    }

    /**
     * Set this as the destroy method in Spring config:
     */
    public void destroy()
    {
       amazonSns.unsubscribe( sqsQueueSubscriptionArn );
       //LOGGER.info("Unsubscribed:"+sqsQueueSubscriptionArn+" from topic:"+topicArn);

       sqsService.deleteQueue();
    }

    public void setAmazonSns( AmazonSNS amazonSns )
    {
        this.amazonSns = amazonSns;
    }

    public void setTopicArn( String topicArn )
    {
        this.topicArn = topicArn;
    }

    public void setRegion( String region )
    {
        this.region = region;
    }


    public void setSqsService( SQSService sqsService )
    {
        this.sqsService = sqsService;
    }
}
