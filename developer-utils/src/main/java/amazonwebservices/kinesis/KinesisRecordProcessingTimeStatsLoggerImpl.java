package amazonwebservices.kinesis;

/**
 * Created by rowland-hall on 04/04/16
 */
public class KinesisRecordProcessingTimeStatsLoggerImpl implements KinesisRecordProcessingTimeStatsLogger
{
   // private static final Logger LOGGER = Logger.getLogger( KinesisRecordProcessingTimeStatsLoggerImpl.class );

    @Override
    public void logRecordProcessingTimeStats( int numRecordsProcessed, long timeProcessingStarted )
    {
        long msPerRecord = calculateMsPerRecord( numRecordsProcessed, timeProcessingStarted );

       // LOGGER.info( "Processed "+numRecordsProcessed+ " kinesis stresam records, that ="+msPerRecord+" ms / record");

        additionalLogging( msPerRecord);
    }

    private long calculateMsPerRecord( int numRecordsProcessed, long timeProcessingStarted )
    {
        long timeTaken = System.currentTimeMillis()- timeProcessingStarted;

        long msPerRecord;
        if( numRecordsProcessed == 0 )
        {
            msPerRecord =0;
        }
        else
        {
            msPerRecord = timeTaken/numRecordsProcessed;
        }
        return msPerRecord;
    }

    protected void additionalLogging( long msPerRecord )
    {
        //default to do nothing
    }
}
