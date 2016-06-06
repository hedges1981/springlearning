package amazonwebservices.s3;

/**
 * Created by rowland-hall on 05/02/15
 *
 * A file held in s3 is uniquely defined by 3 things:
 *
 * bucket name, key and version.
 *
 */
public class S3Coordinates
{
    private String bucketName;

    private String key;

    private String version;

    public S3Coordinates( String bucketName, String key, String version )
    {
        this.bucketName = bucketName;
        this.key = key;
        this.version = version;
    }

    public String getBucketName()
    {
        return bucketName;
    }

    public String getKey()
    {
        return key;
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof S3Coordinates ) )
        {
            return false;
        }

        S3Coordinates that = ( S3Coordinates ) o;

        if ( bucketName != null ? !bucketName.equals( that.bucketName ) : that.bucketName != null )
        {
            return false;
        }
        if ( key != null ? !key.equals( that.key ) : that.key != null )
        {
            return false;
        }
        if ( version != null ? !version.equals( that.version ) : that.version != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = bucketName != null ? bucketName.hashCode() : 0;
        result = 31 * result + ( key != null ? key.hashCode() : 0 );
        result = 31 * result + ( version != null ? version.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "S3Coordinates{" +
                "bucketName='" + bucketName + '\'' +
                ", key='" + key + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
