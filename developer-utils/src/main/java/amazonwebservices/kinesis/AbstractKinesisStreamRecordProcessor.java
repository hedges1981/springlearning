package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * Note that the kinesis specification says that a record is guaranteed to be delivered "at least once", implying that
 * the same record can be delivered > 1 time. Hence this class has measures in place to prevent processing of the same record
 * > 1 time, it uses the record's sequence number (which is unique for a record for a given shard) to do this.
 *
 * Note that this class has sensible default values for control parameters, as an implementing application may or may not
 * care about setting them.
 * <p/>
 * The decisions made in the development of this class are entirely driven by the peculiarities of Amaozon kinesis streams and
 * the Amazon API that has been made use of. See: See core-common/documentation/kinesis-dg.pdf for more info.
 *
 * Created by rowland-hall on 11/03/15
 */
public abstract class AbstractKinesisStreamRecordProcessor implements IRecordProcessor
{
    private String shardId;
    //private static final Logger LOGGER = Logger.getLogger( AbstractKinesisStreamRecordProcessor.class );
    static final int DEFAULT_NUMBER_OF_CHECKPOINT_PROCESSING_ATTEMPTS = 1;
    private int numberOfCheckpointAttempts = DEFAULT_NUMBER_OF_CHECKPOINT_PROCESSING_ATTEMPTS;
    static final int DEFAULT_NUMBER_OF_RECORD_PROCESSING_ATTEMPTS = 1;
    private int numberOfRecordProcessingAttempts = DEFAULT_NUMBER_OF_RECORD_PROCESSING_ATTEMPTS;
    private long checkpointRetryWaitTimeMilliSeconds = 3000L;
    private long recordProcessingRetryWaitTimeMilliSeconds = 3000L;
    /*
        Size limit of 10000 will occupy not a lot of memory, and the kinesis stream hopefully shouldn't spit out the same
        record (i.e. the same sequence number ) 10000 records apart.
     */
    private final Queue<String> sequenceNumbersAlreadyProcessed = new CircularFifoQueue<String>( 10000 );

    //defaults to this one, but can be overridden with the setter:
    private KinesisRecordProcessingTimeStatsLogger timeStatsLogger = new KinesisRecordProcessingTimeStatsLoggerImpl();

    @Override
    public void initialize( String shardId )
    {
        this.shardId = shardId;
        //LOGGER.info( this + " initialised with shardId: " + shardId );
    }

    @Override
    public void processRecords( List<Record> records, IRecordProcessorCheckpointer checkpointer )
    {
       doProcessRecords( records );

        if ( shouldCheckPoint() )
        {
            doCheckPointWithRetries( checkpointer, numberOfCheckpointAttempts );
        }
    }

    protected void doProcessRecords( List<Record> records )
    {
        //LOGGER.info( "Processing "+records.size()+" records" );

        preProcessBatchOfRecordsActions( records );

        long startTime = System.currentTimeMillis();

        Date startDate = new Date();

        for ( Record record : records )
        {
             processRecordWithRetries( record, numberOfRecordProcessingAttempts );
        }

        timeStatsLogger.logRecordProcessingTimeStats( records.size(), startTime );
        postProcessBatchOfRecordsActions( records , startDate );
    }

    protected abstract void preProcessBatchOfRecordsActions( List<Record> records );
    protected abstract void postProcessBatchOfRecordsActions( List<Record> records, Date timeStatedBatch );

    private void processRecordWithRetries( Record record, int attemptsRemaining )
    {
        try
        {
            attemptToProcessRecord( record, attemptsRemaining );
        }
        catch ( Exception e )
        {
            handleExceptionFromRecordProcessingAttempt( record, attemptsRemaining, e );
        }
    }

    private void attemptToProcessRecord( Record record, int attemptsRemaining )
    {
        boolean recordAlreadyProcessed = sequenceNumbersAlreadyProcessed.contains( record.getSequenceNumber() );

        if( recordAlreadyProcessed )
        {
           // LOGGER.debug("Record: "+record+" has already been processed, this is ok as kinesis can push out the same record more than once. Will not be processed again.");
        }
        else if( attemptsRemaining <1 )
        {
            throw new RuntimeException( "Run out of attempts processing record: "+record+". Check logs for previous exceptions." );
        }
        else
        {
            //LOGGER.debug( "Record processor for shardId: " + shardId + " processing record, attempts remaining: " + attemptsRemaining );
            processRecord( record );
        }
    }

    private void handleExceptionFromRecordProcessingAttempt( Record record, int attemptsRemaining, Exception e )
    {
        if( attemptsRemaining > 0 )
        {
            //LOGGER.error( "Exception processing record: "+record+", will sleep for " + recordProcessingRetryWaitTimeMilliSeconds + " then retry", e );
            sleepForRetryWaitTime( recordProcessingRetryWaitTimeMilliSeconds );
            processRecordWithRetries( record, attemptsRemaining - 1 );
        }
        else
        {
            throw new RuntimeException("Ran out of attempts to process record:"+ record +", check logs for previous exceptions");
        }
    }

    private void processRecord( Record record  )
    {
        processSingleRecord( record );

        if(record.getSequenceNumber() != null )
        {
            sequenceNumbersAlreadyProcessed.add( record.getSequenceNumber() );
        }
    }

    protected abstract void processSingleRecord( Record record );

    /**
     * Allows an implementation to decide on checkPoint frequency, could be every 10 mins, every n records processed, etc, or
     * implementation could prevent checkpointing if it is in an errored state.
     */
    protected abstract boolean shouldCheckPoint();



    private void doCheckPointWithRetries( IRecordProcessorCheckpointer checkpointer, int retriesRemaining )
    {
        try
        {
            if ( retriesRemaining > 0 )
            {
               // LOGGER.info( "Record processor for shardId: " + shardId + " checkPointing, retries remaining: " + retriesRemaining );
                checkpointer.checkpoint();
               // LOGGER.info( "Record processor for shardId: " + shardId + " checkPointed successfully at:"+new Date() );
            }
            else
            {
                throw new IllegalStateException( "Ran out of attempts to checkpoint, see logs for previous exceptions" );
            }
        }
        catch ( ThrottlingException e )
        {
            /**
             * This means that Amazon has rejected the checkPoint attempt due to an 'overloaded' system, wait a bit and retry.
             */
            //LOGGER.info( "Received Throttling Exception from Amazon, going to sleep for " + checkpointRetryWaitTimeMilliSeconds + " ms, then retrying", e );
            sleepForRetryWaitTime( checkpointRetryWaitTimeMilliSeconds );

            //have another go:
            doCheckPointWithRetries( checkpointer, retriesRemaining - 1 );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        //LOGGER.info( "Record processor for shardId: " + shardId + " successfully checkPointed" );
    }

    private void sleepForRetryWaitTime( long waitTime )
    {
        try
        {
           // LOGGER.info( "Thread:" + Thread.currentThread().getName() + " sleeping for " + waitTime + " ms" );
            Thread.sleep( waitTime );
        }
        catch ( InterruptedException e )
        {
           // LOGGER.error( e );
        }
    }

    /**
     * Kinesis API is a bit confusing here, this does NOT get called as a result of calling Worker.stop() but when we have actually reached the end of a
     * shard,e.g to a re-sharding.
     */
    @Override
    public void shutdown( IRecordProcessorCheckpointer checkpointer, ShutdownReason reason )
    {
        if ( ShutdownReason.TERMINATE.equals( reason ) )
        {
           // LOGGER.info( "Record processor for shard: " + shardId +
                            //     " is being shutdown as shard has been terminated. Now needs to checkPoint to allow processing from child shards" );

            try
            {
                doCheckPointWithRetries( checkpointer, numberOfCheckpointAttempts );
            }
            catch( Exception e )
            {
               // LOGGER.error( "Exception occured when checpointing as part of shutdown. Swallowing it, to avoid interfering with the shutdown process.", e );
            }

        }
    }

    protected String getShardId()
    {
        return shardId;
    }

    public void setNumberOfCheckpointAttempts( int numberOfCheckpointAttempts )
    {
        this.numberOfCheckpointAttempts = numberOfCheckpointAttempts;
    }

    public void setCheckpointRetryWaitTimeMilliSeconds( long checkpointRetryWaitTimeMilliSeconds )
    {
        this.checkpointRetryWaitTimeMilliSeconds = checkpointRetryWaitTimeMilliSeconds;
    }

    public void setNumberOfRecordProcessingAttempts( int numberOfRecordProcessingAttempts )
    {
        this.numberOfRecordProcessingAttempts = numberOfRecordProcessingAttempts;
    }

    public void setRecordProcessingRetryWaitTimeMilliSeconds( long recordProcessingRetryWaitTimeMilliSeconds )
    {
        this.recordProcessingRetryWaitTimeMilliSeconds = recordProcessingRetryWaitTimeMilliSeconds;
    }

    public void setTimeStatsLogger( KinesisRecordProcessingTimeStatsLogger timeStatsLogger )
    {
        this.timeStatsLogger = timeStatsLogger;
    }
}
