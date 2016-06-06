package amazonwebservices.s3;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;
import utils.GenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rowland-hall on 05/02/15
 */
public class S3FileAccessorImpl implements S3FileAccessor
{
    private AmazonS3 amazonS3Client;

    private String bucketName;

   // private static final Logger LOGGER = LoggerFactory.getLogger( S3FileAccessorImpl.class );

    private static final int AWS_RECOMMENDED_SINGLE_PART_UPLOAD_FILE_SIZE_LIMIT_MB = 100;

    public S3FileAccessorImpl( String bucketName, String region )
    {
        this.amazonS3Client = new AmazonS3Client( new DefaultAWSCredentialsProviderChain() );

        validateBucketHasVersioningEnabled( bucketName );

        this.bucketName = bucketName;


        if( !StringUtils.isBlank( region ))
        {
            setRegionOnAmazonS3Client( amazonS3Client, region );
        }
    }

    public S3FileAccessorImpl(){}

    private void validateBucketHasVersioningEnabled( String bucketName )
    {
        BucketVersioningConfiguration bucketVersioningConfiguration = amazonS3Client.getBucketVersioningConfiguration( bucketName );

        if( !BucketVersioningConfiguration.ENABLED.equals( bucketVersioningConfiguration.getStatus() ) )
        {
            throw new IllegalArgumentException( "Bucket: "+bucketName+" does not have versioning enabled" );
        }
    }

    private void setRegionOnAmazonS3Client( AmazonS3 amazonS3Client, String region )
    {
        Regions regionEnum = Regions.fromName( region ) ;
        amazonS3Client.setRegion( Region.getRegion( regionEnum ) );
    }

    @Override
    public S3Coordinates putFileInBucket( String filePath, String key )
    {
        return doPutFileInBucket( filePath, key, null );
    }

    private S3Coordinates doPutFileInBucket( String filePath, String key, ObjectMetadata objectMetadata )
    {
        S3Coordinates s3Coordinates;

        long fileSizeInBytes = new File( filePath ).length();
        double fileSizeInMb = GenUtil.convertBytesToMegabytes( fileSizeInBytes );

        if( fileSizeInMb >= AWS_RECOMMENDED_SINGLE_PART_UPLOAD_FILE_SIZE_LIMIT_MB )
        {
            s3Coordinates = putFileInBucketMultiPartUpload( filePath, key, objectMetadata );
        }
        else
        {
            s3Coordinates = putFileInBucketSinglePartUpload( filePath, key, objectMetadata );
        }

        return s3Coordinates;
    }

    @Override
    public S3Coordinates putFileInBucketWithCustomHttpHeaders( String filePath, String key, CustomS3ObjectHttpHeaderSetter customS3ObjectHttpHeaderSetter )
    {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        customS3ObjectHttpHeaderSetter.setHeadersOnS3ObjectMetadata( objectMetadata );

        return doPutFileInBucket( filePath, key, objectMetadata );
    }

    private S3Coordinates putFileInBucketSinglePartUpload( String filePath, String key, ObjectMetadata objectMetadata )
    {
        //LOGGER.debug( "Uploading file: "+filePath+ " in single part upload" );

        File file = new File( filePath );

        PutObjectRequest putObjectRequest = new PutObjectRequest(  bucketName, key, file  );

        if( objectMetadata != null )
        {
            putObjectRequest.setMetadata( objectMetadata );
        }

        PutObjectResult putObjectResult = amazonS3Client.putObject( putObjectRequest );

        S3Coordinates s3Coordinates = new S3Coordinates( bucketName, key, putObjectResult.getVersionId() );

        //LOGGER.debug("Single part upload to s3 complete, details: "+s3Coordinates );

        return s3Coordinates;
    }

    /**
     * Does the upload as a multi part upload.
     *
     * Logic copied from example at:
     * http://docs.aws.amazon.com/AmazonS3/latest/dev/llJavaUploadFile.html
     */
    private S3Coordinates putFileInBucketMultiPartUpload( String filePath, String key, ObjectMetadata objectMetadata )
    {
        //LOGGER.debug( "Uploading file: "+filePath+ " in multi part upload" );

        S3Coordinates s3Coordinates;
        // Create a list of UploadPartResponse objects. You get one of these
        // for each part upload.
        List<PartETag> partETags = new ArrayList<PartETag>();

        // Step 1: Initialize.
        InitiateMultipartUploadRequest initRequest = new
                InitiateMultipartUploadRequest( bucketName, key );

        if( objectMetadata != null )
        {
            initRequest.setObjectMetadata( objectMetadata );
        }

        InitiateMultipartUploadResult initResponse =
                amazonS3Client.initiateMultipartUpload( initRequest );

        File file = new File( filePath );
        long contentLength = file.length();
        long partSize = 5242880; // Set part size to 5 MB.

        try
        {
            // Step 2: Upload parts.
            long filePosition = 0;

            for ( int i = 1; filePosition < contentLength; i++ )
            {
                // Last part can be less than 5 MB. Adjust part size.
                partSize = Math.min( partSize, ( contentLength - filePosition ) );

                filePosition = uploadFilePart( key, partETags, initResponse, file, partSize, filePosition, i );
            }

            // Step 3: Complete.
            CompleteMultipartUploadRequest compRequest = new
                    CompleteMultipartUploadRequest(
                    bucketName,
                    key,
                    initResponse.getUploadId(),
                    partETags );

            CompleteMultipartUploadResult completeMultipartUploadResult = amazonS3Client.completeMultipartUpload( compRequest );

            s3Coordinates = new S3Coordinates( completeMultipartUploadResult.getBucketName(),
                                               completeMultipartUploadResult.getKey(), completeMultipartUploadResult.getVersionId() );
        }
        catch ( Exception e )
        {
            amazonS3Client.abortMultipartUpload( new AbortMultipartUploadRequest(
                    bucketName, key, initResponse.getUploadId() ) );

            throw new RuntimeException( e );
        }

        //LOGGER.debug("Multiple part upload to s3 complete, details: "+s3Coordinates );

        return s3Coordinates;
    }

    private long uploadFilePart( String key, List<PartETag> partETags, InitiateMultipartUploadResult initResponse, File file, long partSize, long filePosition, int i )
    {
       // LOGGER.debug( "Uploading file part, position is: "+filePosition );
        // Create request to upload a part.
        UploadPartRequest uploadRequest = new UploadPartRequest()
                .withBucketName( bucketName ).withKey( key )
                .withUploadId( initResponse.getUploadId() ).withPartNumber( i )
                .withFileOffset( filePosition )
                .withFile( file )
                .withPartSize( partSize );

        // Upload part and add response to our list.
        partETags.add( amazonS3Client.uploadPart( uploadRequest ).getPartETag() );

        filePosition += partSize;

        //LOGGER.debug( "Uploaded file part, new position is: "+filePosition );

        return filePosition;
    }


    @Override
    public void deleteFileVersion( String key, String versionId )
    {
        deleteFileVersion( bucketName, key, versionId  );
    }

    @Override
    public void deleteFileVersion( String bucketName, String key, String versionId )
    {
        amazonS3Client.deleteVersion( bucketName, key, versionId );
    }

    @Override
    public void deleteFileAllVersions( String key )
    {
        List<String> versionIdsForKey = getAllVersionIdsForKey( key );

        for( String versionId : versionIdsForKey )
        {
            deleteFileVersion( key, versionId );
        }
    }

    @Override
    public List<String> getAllVersionIdsForKey( String key )
    {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName( bucketName );
        listVersionsRequest.setPrefix( key );

        VersionListing versionListing = amazonS3Client.listVersions( listVersionsRequest );

        List<String> versionIds = new ArrayList<String>();

        do
        {
            for ( S3VersionSummary s3VersionSummary : versionListing.getVersionSummaries() )
            {
                if ( key.equals( s3VersionSummary.getKey() ) )
                {
                    versionIds.add( s3VersionSummary.getVersionId() );
                }
            }
            versionListing = amazonS3Client.listNextBatchOfVersions( versionListing );
        }
        while ( versionListing.isTruncated() );

        return versionIds;
    }

    @Override
    public void deleteEntireContentsOfBucket()
    {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName( bucketName );

        VersionListing versionListing = amazonS3Client.listVersions( listVersionsRequest );

        do
        {
            deleteVersions( versionListing.getVersionSummaries() );
            versionListing = amazonS3Client.listNextBatchOfVersions( versionListing );
        }
        while( versionListing.isTruncated() );
    }

    private void deleteVersions( List<S3VersionSummary> s3VersionSummaries )
    {
        for(S3VersionSummary s3VersionSummary: s3VersionSummaries  )
        {
            deleteFileVersion( s3VersionSummary.getKey(), s3VersionSummary.getVersionId() );
        }
    }

    @Override
    public ObjectMetadata readObjectIntoFileByVersion( String key, String versionId, File file )
    {
        GetObjectRequest getObjectRequest = new GetObjectRequest( bucketName, key, versionId );
        return readFileFromS3( getObjectRequest, file );
    }

    @Override
    public ObjectMetadata readObjectIntoFileByKey( String key, File file )
    {
        GetObjectRequest getObjectRequest = new GetObjectRequest( bucketName, key );

        return readFileFromS3( getObjectRequest, file );
    }

    @Override
    public S3Coordinates copyLatestVersionOfFileToKey( String sourceKey, String destinationKey )
    {
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest( bucketName, sourceKey, bucketName, destinationKey );
        CopyObjectResult copyObjectResult = amazonS3Client.copyObject( copyObjectRequest );

        return new S3Coordinates( bucketName, destinationKey, copyObjectResult.getVersionId() );
    }

    @Override
    public List<String> getObjectKeysWithPrefix( String prefix )
    {
        List<String> retList = new ArrayList<String>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName( bucketName ).withPrefix( prefix );

        ObjectListing objectListing = amazonS3Client.listObjects( listObjectsRequest );

        do
        {
            List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

            for( S3ObjectSummary s3ObjectSummary : s3ObjectSummaries )
            {
                retList.add( s3ObjectSummary.getKey() );
            }

            objectListing = amazonS3Client.listNextBatchOfObjects( objectListing );
        }
        while( objectListing.isTruncated() );

        return retList;
    }

    @Override
    public List<String> getAllKeysInBucket()
    {
        return getObjectKeysWithPrefix( "" );
    }

    @Override
    public boolean fileExistsWithKey( String key )
    {
        ObjectListing objectListing = amazonS3Client.listObjects( bucketName, key );

        //AWS API is a bit strange for checking key existence, we actually get a list of objects who's key begins with the
        //specified key, so we need to do the below:
        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

        boolean keyFound = false;

        for( S3ObjectSummary s3ObjectSummary : s3ObjectSummaries )
        {
            if( key.equals(s3ObjectSummary.getKey()) )
            {
                keyFound = true;
                break;
            }
        }

        return keyFound;
    }

    @Override
    public boolean fileExistsWithKeyAndVersion( String key, String version )
    {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName( bucketName );
        listVersionsRequest.setPrefix( key );

        VersionListing versionListing = amazonS3Client.listVersions( listVersionsRequest );

        boolean result = false;

        do
        {
            for ( S3VersionSummary s3VersionSummary : versionListing.getVersionSummaries() )
            {
                if ( s3VersionSummary.getKey().equals( key ) && s3VersionSummary.getVersionId().equals( version ) )
                {
                    result = true;
                    break;
                }
            }
            if( !result )
            {
                versionListing = amazonS3Client.listNextBatchOfVersions( versionListing );
            }
        }
        while ( !result && versionListing.isTruncated() );

        return result;
    }

    @Override
    public String getBucketName()
    {
        return bucketName;
    }

    private ObjectMetadata readFileFromS3( GetObjectRequest getObjectRequest, File file )
    {
        try
        {
            return amazonS3Client.getObject( getObjectRequest, file );
        }
        catch( AmazonS3Exception e )
        {
            throw new IllegalArgumentException( "Problem fetching file with GetObjectRequest: "+getObjectRequest, e );
        }
    }

    public void setAmazonS3Client( AmazonS3 amazonS3Client )
    {
        this.amazonS3Client = amazonS3Client;
    }

    public void setBucketName( String bucketName )
    {
        this.bucketName = bucketName;
    }

}
