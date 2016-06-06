package amazonwebservices.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;
import java.util.List;

/**
 * Created by rowland-hall on 05/02/15
 *
 * Facilitates file operations on a versioned S3 bucket.
 */
public interface S3FileAccessor
{
    S3Coordinates putFileInBucket( String filePath, String key );

    S3Coordinates putFileInBucketWithCustomHttpHeaders( String filePath, String key, CustomS3ObjectHttpHeaderSetter customS3ObjectHttpHeaderSetter );

    void deleteFileVersion( String key, String versionId );

    void deleteFileVersion( String bucketName, String key, String versionId );

    void deleteFileAllVersions( String key );

    void deleteEntireContentsOfBucket();

    ObjectMetadata readObjectIntoFileByVersion( String key, String versionId, File file );

    /**
     * If there is > 1 version for the key, the latest one is read into the file and returned.
     */
    public ObjectMetadata readObjectIntoFileByKey( String key, File file );

    S3Coordinates copyLatestVersionOfFileToKey( String sourceKey, String destinationKey );

    List<String> getObjectKeysWithPrefix( String prefix );

    List<String> getAllKeysInBucket();

    List<String> getAllVersionIdsForKey( String key );

    boolean fileExistsWithKey( String key );

    boolean fileExistsWithKeyAndVersion( String key, String version );

    String getBucketName();
}
