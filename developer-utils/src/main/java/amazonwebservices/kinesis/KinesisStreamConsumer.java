package amazonwebservices.kinesis;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import org.springframework.context.Lifecycle;

import java.net.InetAddress;
import java.util.UUID;

/**
 * This is based on the sample Amazon Kinesis Application code found at:
 * https://github.com/aws/aws-sdk-java/tree/master/src/samples/AmazonKinesis
 * <p/>
 * Created by rowland-hall on 10/03/15
 */
public class KinesisStreamConsumer implements Lifecycle
{
    private String kinesisStreamName;
    private String kinesisStreamEndpoint;
    private String applicationName;
    private String region;
    private InitialPositionInStream initialPositionInStream;
    private IRecordProcessorFactory recordProcessorFactory;
    private WorkerRunner workerRunner;
    private int maxRecordsToFetchInOneGo = 10000; //default provided to relieve burden of setting it explicitly in config.
    private long waitTimeBetweenReadsFromStreamInMs=1;//lowest value that is accepted, to be tweaked via setter according to performance requirements and limitations.

    private Worker worker;

    //private static final Logger LOGGER = Logger.getLogger( KinesisStreamConsumer.class );

    boolean isRunning;

    public KinesisStreamConsumer( String kinesisStreamName,
                                  String kinesisStreamEndpoint,
                                  String applicationName,
                                  String initialPositionInStream,
                                  String region,
                                  IRecordProcessorFactory recordsProcessorFactory,
                                  WorkerRunner workerRunner )
    {
        this.kinesisStreamName = kinesisStreamName;
        this.kinesisStreamEndpoint = kinesisStreamEndpoint;
        this.applicationName = applicationName;
        this.initialPositionInStream = InitialPositionInStream.valueOf( initialPositionInStream );
        this.recordProcessorFactory = recordsProcessorFactory;
        this.workerRunner = workerRunner;
        this.region = region;
    }

    public KinesisStreamConsumer()
    {
    }

    @Override
    public void start()
    {
        //LOGGER.info( "Intitialising KinesisStreamConsumer: " + this );

        worker = getWorker();

        workerRunner.runWorker( worker );

        isRunning = true;

       // LOGGER.info( "Worker: " + worker+" is now running" );
    }

    private Worker getWorker()
    {
        String workerId = getWorkerId();
        KinesisClientLibConfiguration kinesisClientLibConfiguration = getKinesisClientLibConfiguration( workerId );

        return new Worker( recordProcessorFactory, kinesisClientLibConfiguration );
    }

    KinesisClientLibConfiguration getKinesisClientLibConfiguration( String workerId )
    {
        /**
         * Kinesis doesn't like it if you change stream without changing the application name, so we include the
         * stream name as part of the application name to get round that, i.e. if the stream name changes, the application name
         * here will always change to keep up.
         */
        String applicationNameToUse = applicationName +"_for_stream_"+kinesisStreamName;

        KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration( applicationNameToUse,
                                                   kinesisStreamName,
                                                   new DefaultAWSCredentialsProviderChain(),
                                                   workerId );

        kinesisClientLibConfiguration.withKinesisEndpoint( kinesisStreamEndpoint );
        kinesisClientLibConfiguration.withInitialPositionInStream( initialPositionInStream );
        kinesisClientLibConfiguration.withMaxRecords( maxRecordsToFetchInOneGo );
        kinesisClientLibConfiguration.withIdleTimeBetweenReadsInMillis( waitTimeBetweenReadsFromStreamInMs );
        kinesisClientLibConfiguration.withRegionName(region);

        return kinesisClientLibConfiguration;
    }

    private String getWorkerId()
    {
        try
        {
            String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID();

            //LOGGER.info( "Kinesis Stream Consumer: " + this + " assigned workerId: " + workerId );

            return workerId;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void stop()
    {
        //LOGGER.info( "Kinesis Stream consumer :" + this + " shutting down worker " + worker );
        worker.shutdown();

        isRunning=false;
    }

    @Override
    public boolean isRunning()
    {
        return isRunning;
    }

    @Override
    public String toString()
    {
        return "KinesisStreamConsumer{" +
                "kinesisStreamName='" + kinesisStreamName + '\'' +
                ", kinesisStreamEndpoint='" + kinesisStreamEndpoint + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", initialPositionInStream=" + initialPositionInStream +
                ", recordProcessorFactory=" + recordProcessorFactory +
                ", workerRunner=" + workerRunner +
                ", maxRecordsToFetchInOneGo=" + maxRecordsToFetchInOneGo +
                ", waitTimeBetweenReadsFromStreamInMs=" + waitTimeBetweenReadsFromStreamInMs +
                ", worker=" + worker +
                ", isRunning=" + isRunning +
                '}';
    }

    public void setWorkerRunner( WorkerRunner workerRunner )
    {
        this.workerRunner = workerRunner;
    }

    public void setKinesisStreamName( String kinesisStreamName )
    {
        this.kinesisStreamName = kinesisStreamName;
    }

    public void setKinesisStreamEndpoint( String kinesisStreamEndpoint )
    {
        this.kinesisStreamEndpoint = kinesisStreamEndpoint;
    }

    public void setApplicationName( String applicationName )
    {
        this.applicationName = applicationName;
    }

    public void setInitialPositionInStream( InitialPositionInStream initialPositionInStream )
    {
        this.initialPositionInStream = initialPositionInStream;
    }

    public void setRecordProcessorFactory( IRecordProcessorFactory recordProcessorFactory )
    {
        this.recordProcessorFactory = recordProcessorFactory;
    }

    public void setMaxRecordsToFetchInOneGo( int maxRecordsToFetchInOneGo )
    {
        this.maxRecordsToFetchInOneGo = maxRecordsToFetchInOneGo;
    }

    public void setWaitTimeBetweenReadsFromStreamInMs( long waitTimeBetweenReadsFromStreamInMs )
    {
        this.waitTimeBetweenReadsFromStreamInMs = waitTimeBetweenReadsFromStreamInMs;
    }

    public void setRegion(String region) {
      this.region = region;
    }

}