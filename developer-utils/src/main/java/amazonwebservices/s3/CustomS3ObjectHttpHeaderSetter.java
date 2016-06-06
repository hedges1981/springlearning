package amazonwebservices.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;

/**
 * Object allows for customisation of the 'standard' http headers on an S3 Object. Whatever this sets, the same value
 * for the header is seen when the s3 object is fetched by a http get request.
 *
 * Flesh this class out to work with more headers as and when needed.
 *
 * Created by rowland-hall on 10/02/16
 */
public class CustomS3ObjectHttpHeaderSetter
{
    private String contentDisposition;

    public void setHeadersOnS3ObjectMetadata( ObjectMetadata objectMetadata )
    {
        if( !StringUtils.isBlank( this.contentDisposition ))
        {
            objectMetadata.setContentDisposition( this.contentDisposition );
        }
    }

    public void setContentDisposition( String contentDisposition )
    {
        this.contentDisposition = contentDisposition;
    }
}
