package amazonwebservices.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class S3SecureFileAccessorImpl implements S3SecureFileAccessor {

    private AmazonS3EncryptionClient s3SecureClient = null;
    private String bucketUri;
    private String region;
    private String keyId;
   // private static final Logger LOG = LoggerFactory.getLogger(S3SecureFileAccessorImpl.class);

    /**
     * Constructs secure s3 client, using passed credentials object
     * @param bucketUri The name of the bucket to associated with the client
     * @param region The region name to be associated with this client
     * @param creds The creds to use
     * @param keyId The key Id
     */
    public S3SecureFileAccessorImpl(String bucketUri, String region, AWSCredentials creds, String keyId){

        if (creds == null){
            AWSCredentialsProviderChain chain = new DefaultAWSCredentialsProviderChain();
            creds = chain.getCredentials();
        }

        if (creds == null){
            throw new IllegalArgumentException("Credentials unavailable cannot continue");
        }

        this.bucketUri = bucketUri;
        this.region = region;
        this.keyId = keyId;

        s3SecureClient = new AmazonS3EncryptionClient(
                creds,
                new KMSEncryptionMaterialsProvider(keyId),
                new CryptoConfiguration().withKmsRegion(Regions.fromName(region)))
                .withRegion(Regions.fromName(region));

    }

    /**
     * Puts a file to S3, associates the master key with the object meta data so that the symmetric key can be unencrypted
     * @param filePath The file path of the object to be put
     * @return S3 Coordinates to uniquely identify the object in S3
     */
    @Override
    public S3Coordinates putFileInBucketSecurely(String filePath) {

        try{
            File file = new File(filePath);
            String key = null;
            if (bucketUri.endsWith("/")){
                key = S3BucketRegExHelper.getFullKey(bucketUri)+file.getName();
            } else {
                //Then ending is assumed file, if we don't want assume this used *. regex
                key = S3BucketRegExHelper.getFullKey(bucketUri);
            }

            PutObjectResult putResult = s3SecureClient.putObject(S3BucketRegExHelper.getBucket(bucketUri), key , file);
            //LOG.info("Uploading bucket URI " +bucketUri+ " complete...");
            return new S3Coordinates( S3BucketRegExHelper.getBucket(bucketUri), key, putResult.getVersionId() );
        } finally{
            s3SecureClient.shutdown();
        }

    }



    /**
     * Puts a file to S3, associates the master key with the object meta data so that the symmetric key can be unencrypted
     * @param uri The s3 uri
     * @parm
     * @return S3 Coordinates to uniquely identify the object in S3
     */
    public S3Coordinates putFileInBucketSecurelyUsingBucketUri(String uri, File file) {

            String key = S3BucketRegExHelper.getFullKey(uri);

            PutObjectResult putResult = s3SecureClient.putObject(S3BucketRegExHelper.getBucket(uri), key , file);
            //LOG.info("Uploading bucket URI " +bucketUri+ " complete...");
            return new S3Coordinates( S3BucketRegExHelper.getBucket(uri), key, putResult.getVersionId() );
    }

    /**
     * Retrieves the file securely from S3, using the object meta data to decrypt and uses machine s3 prefix to
     * dictate where the files are downloaded to on the machine
     * @param machines3prefix The Machine S3 prefix
     * @throws java.io.IOException If a an IO errors with the download file location
     */
    @Override
    public void getFileInBucketSecurelyUsingMachinePrefix( String machines3prefix ) throws IOException
    {

        FileOutputStream fos = null;

        try
        {

            ObjectListing list = s3SecureClient.listObjects(
                    S3BucketRegExHelper.getBucket( bucketUri ),
                    S3BucketRegExHelper.getPrefix( bucketUri ) );


            String key = S3BucketRegExHelper.getEndOfKey( bucketUri );

            for ( S3ObjectSummary summary : list.getObjectSummaries() )
            {

                try
                {
                    //LOG.info( "processing s3ObjectSummary with key:"+summary.getKey() );

                    S3Object file = s3SecureClient.getObject( S3BucketRegExHelper.getBucket( bucketUri ), summary.getKey() );

                    String filePath = S3BucketRegExHelper.buildPathFromS3Key( file.getKey(), machines3prefix );

                    if ( key.equalsIgnoreCase( "" ) )
                    {

                        File fileToWrite = new File( filePath );
                        fos = new FileOutputStream( filePath );
                        IOUtils.copy( file.getObjectContent(), fos );
                        //LOG.info( "Download complete,and written to file: " + fileToWrite.getAbsolutePath() );

                    }
                    else if ( file.getKey().contains( key ) )
                    {

                        File fileToWrite = new File( filePath );
                        fos = new FileOutputStream( filePath );
                        IOUtils.copy( file.getObjectContent(), fos );
                        //LOG.info( "Download complete,and written to file: " + fileToWrite.getAbsolutePath() );

                    }
                }
                catch ( Exception e )
                {
                    //LOG.error( "Exception processing S3ObjectSummary with objectKey:" + summary.getKey(), e );
                }

            }


        }
        finally
        {

            if ( s3SecureClient != null )
            {
                s3SecureClient.shutdown();
            }


            if ( fos != null )
            {
                fos.close();
            }

        }

    }

    /**
     * Retrieves the file securely from S3, using the object meta data to decrypt
     * @param filePath The path to place the object once retrieved and decrypted
     * @throws java.io.IOException If a an IO errors with the download file location
     */
    @Override
    public void getFilesWithRegexInBucketSecurely(String filePath) throws IOException
    {

        FileOutputStream fos = null;

        try{

            ObjectListing list = s3SecureClient.listObjects(
                    S3BucketRegExHelper.getBucket(bucketUri),
                    S3BucketRegExHelper.getPrefix(bucketUri));

         for(S3ObjectSummary summary:list.getObjectSummaries()){


            String reconstructedBucketObject = "S3://"+summary.getBucketName()+"/"+summary.getKey();

            String bucketObject = S3BucketRegExHelper.getEndOfKey(reconstructedBucketObject);
            String requestedObject = S3BucketRegExHelper.getEndOfKey(bucketUri);

            if (S3BucketRegExHelper.doesBucketObjectMatch(requestedObject, bucketObject)){

                S3Object file = s3SecureClient.getObject(S3BucketRegExHelper.getBucket(bucketUri), summary.getKey());;
                File f = new File(filePath + "/" + bucketObject);
                IOUtils.copy(file.getObjectContent(), new FileOutputStream(f));
                //LOG.info("Download complete,and written to file: "+f.getAbsolutePath());

            }
          }

        } finally {

            if (s3SecureClient != null){
                s3SecureClient.shutdown();
            }


            if (fos != null){
                fos.close();
            }

        }

    }

    /**
     * Retrieve the bucket uri used by this object
     * @return the bucket uri
     */
    @Override
    public String getBucketUri() {
        return bucketUri;
    }

    /**
     * Retrieve the region used by this object
     * @return  The region
     */
    @Override
    public String getRegion() {
        return region;
    }

    /**
     * Retrieve the key id used by this object
     * @return The key id
     */
    @Override
    public String getKeyId() {
        return keyId;
    }

    @Override
    public void close(){
        s3SecureClient.shutdown();
    }
}
