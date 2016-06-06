package amazonwebservices.s3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class S3BucketRegExHelper {

    //private static final Logger LOG = LoggerFactory.getLogger(S3BucketRegExHelper.class);

    private S3BucketRegExHelper(){

    }


    public static String getMatched(String input, String thePattern){


        String patternString = thePattern;

        Pattern pattern = Pattern.compile( patternString );

        Matcher matcher = pattern.matcher(input);

        while(matcher.find()){
            return matcher.group();
        }

        return  null;

    }


    private static String getBucketAddress(String bucketURI){

        return getMatched(bucketURI, "([sS]?3://)[a-zA-Z0-9\\.]*(/)");

    }

    public static String getBucket(String bucketURI){

        String name = getBucketAddress(bucketURI);
        return name.replace("S3://","").replace("s3://","").replace("/","");

    }

    public static String getPrefix(String bucketURI){

        int index =  0;

        if (getEndOfKey(bucketURI).equalsIgnoreCase("")){
            index = bucketURI.length()-1; //Remove trailing '/'
        } else {
            index = bucketURI.lastIndexOf( getEndOfKey(bucketURI) );
        }

        return bucketURI.substring(getBucketAddress(bucketURI).length(),index);

    }

    /**
     * Return the last part of the key e.g. /test/test/test/testkeytobereturned
     * @param bucketURI
     * @return The last value after /
     */
    public static String getEndOfKey(String bucketURI){
        return bucketURI.substring(bucketURI.lastIndexOf("/")+1, bucketURI.length());
    }

    /**
     * Return the full
     * @param bucketURI
     * @return
     */
    public static String getFullKey(String bucketURI){

        String prefix = getPrefix(bucketURI);

        int index =  0;

        if (getEndOfKey(bucketURI).equalsIgnoreCase("")){
            index = bucketURI.length()-1;
        } else {
            index = bucketURI.lastIndexOf("/")+1;
        }


        if (prefix != null){
            return prefix + bucketURI.substring(index, bucketURI.length());
        }

        return bucketURI.substring(bucketURI.lastIndexOf("/")+1, bucketURI.length());
    }

    public static boolean doesBucketObjectMatch(String requestedBucketObject, String actualBucketObject){

        // If the bucketUri contain a wildcard
        if (requestedBucketObject.contains("*")){
            if (actualBucketObject.matches(requestedBucketObject.replace(".", "*").replaceFirst("\\*", "."))){

             //   LOG.info("Found a matching object using regex "+requestedBucketObject.replace(".", "*").replaceFirst("\\*", ".")+" in the bucket location, copying to file system");
                return true;
            }
        } else {
            if (actualBucketObject.matches(requestedBucketObject)){
              //  LOG.info("Found a matching object in the bucket location, copying to file system");
                return true;
            }
        }

        return false;
    }

    public static String buildPathFromS3Key(String s3Key, String machinePrefix){


            return s3Key.replaceFirst(machinePrefix, "");

    }


}
