package amazonwebservices.kinesis;

/**
 * Created by rowland-hall on 04/04/16
 */
public interface KinesisRecordProcessingTimeStatsLogger
{
    void logRecordProcessingTimeStats( int numberOfRecordsProcessed, long timeProcessingStarted );
}
