package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

public class KinesisStreamServiceImpl {

    //public static Logger LOG = LoggerFactory.getLogger(KinesisStreamServiceImpl.class);

    private AmazonKinesis kinesisClient;
    private String partitionKey;
    private String streamName;


    /**
     * Responsible for initializing the service class
     * @param streamName
     * @param partitionKey
     * @param kinesisFactory
     */
    public KinesisStreamServiceImpl(String streamName, String partitionKey, AmazonKinesisFactory kinesisFactory ){

        this.kinesisClient = kinesisFactory.getAmazonKinesis();

//        LOG.info("Intialising Kiensis Stream Service, client:" + kinesisClient +
//                " Stream Name: " + streamName +
//                " partitionKey: " + partitionKey);

        this.kinesisClient = kinesisFactory.getAmazonKinesis();
        this.streamName = streamName;
        this.partitionKey = partitionKey;

    }

    /**
     * Pushes a record to a Kinesis stream
     * @param toPush the record to push
     */
    public void pushToKinesisStream(String toPush){

        //LOG.debug("Putting record to kinesis stream : " + toPush);

        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setPartitionKey(partitionKey);
        putRecord.setStreamName(streamName);
        putRecord.setData( ByteBuffer.wrap( toPush.getBytes() ));
        PutRecordResult result = kinesisClient.putRecord(putRecord);

        //LOG.debug("Put result Seq: " + result.getSequenceNumber() +
          //      " Shard ID: " + result.getShardId());

    }

    public List<String> pullListOfStringsFromKinesisStream() throws CharacterCodingException
    {
        //LOG.debug("Pulling from Kinesis Stream");

        String shardIterator;
        GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest();
        getShardIteratorRequest.setStreamName(streamName);
        getShardIteratorRequest.setShardId("shardId-000000000000");
        getShardIteratorRequest.setShardIteratorType("TRIM_HORIZON");

        GetShardIteratorResult getShardIteratorResult = kinesisClient.getShardIterator(getShardIteratorRequest);
        shardIterator = getShardIteratorResult.getShardIterator();

        GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
        getRecordsRequest.setShardIterator(shardIterator);
        getRecordsRequest.setLimit(25);

        GetRecordsResult getRecordsResult = kinesisClient.getRecords(getRecordsRequest);
        List<Record> records = getRecordsResult.getRecords();

        List<String> listToReturn = new ArrayList<String>();

        //LOG.debug("Retrieving Records From Stream:");

        for (Record rec:records){

            Charset charset = Charset.defaultCharset();
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(rec.getData());

           // LOG.debug(charBuffer.toString());
            listToReturn.add(charBuffer.toString());

        }

        return listToReturn;
    }
}
