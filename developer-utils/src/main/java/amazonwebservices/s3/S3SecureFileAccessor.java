package amazonwebservices.s3;

import java.io.File;
import java.io.IOException;


public interface S3SecureFileAccessor
{
    S3Coordinates putFileInBucketSecurely( String filePath );

    S3Coordinates putFileInBucketSecurelyUsingBucketUri( String uri, File file );

    void getFilesWithRegexInBucketSecurely( String filePath ) throws IOException;

    void getFileInBucketSecurelyUsingMachinePrefix( String machines3prefix ) throws IOException;

    String getBucketUri();

    String getRegion();

    String getKeyId();

    void close();
}
