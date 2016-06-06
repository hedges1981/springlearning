package amazonwebservices.ec2;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.autoscaling.model.TerminateInstanceInAutoScalingGroupRequest;
import com.amazonaws.services.autoscaling.model.TerminateInstanceInAutoScalingGroupResult;


/**
 * Created by rowland-hall on 06/05/15
 */
public class EC2AutoScalingInstanceManagerImpl implements EC2AutoScalingInstanceManager
{
    private AmazonAutoScaling amazonAutoScaling = new AmazonAutoScalingClient();

    private EC2MetaDataFetcher ec2MetaDataFetcher;

    //private static final Logger LOGGER = Logger.getLogger( EC2AutoScalingInstanceManagerImpl.class );

    @Override
    public void terminateThisAutoScalingInstance( boolean decrementDesiredCapacity )
    {
        String ec2InstanceIdOfThisInstance = ec2MetaDataFetcher.getThisEC2InstanceId();

        if( ec2InstanceIdOfThisInstance == null )
        {
            throw new IllegalStateException( "No ec2InstanceId found, is this running on an EC2 instance?" );
        }
        else
        {
            terminateAutoScalingInstance( ec2InstanceIdOfThisInstance, decrementDesiredCapacity );
        }
    }

    @Override
    public void terminateAutoScalingInstance( String ec2InstanceId, boolean decrementDesiredCapacity )
    {
        TerminateInstanceInAutoScalingGroupRequest request = new TerminateInstanceInAutoScalingGroupRequest()
                .withInstanceId( ec2InstanceId )
                .withShouldDecrementDesiredCapacity( decrementDesiredCapacity );

        //LOGGER.info( "Requesting termination of ec2Instance: "+ec2InstanceId+" with decrementDesiredCapacity= "+decrementDesiredCapacity );

        TerminateInstanceInAutoScalingGroupResult result =
                amazonAutoScaling.terminateInstanceInAutoScalingGroup( request );

        //LOGGER.info( "Requested termination of EC2Instance:" + ec2InstanceId + ". Activity =" + result.getActivity() );
    }

    public void setAmazonAutoScaling( AmazonAutoScaling amazonAutoScaling )
    {
        this.amazonAutoScaling = amazonAutoScaling;
    }

    public void setEc2MetaDataFetcher( EC2MetaDataFetcher ec2MetaDataFetcher )
    {
        this.ec2MetaDataFetcher = ec2MetaDataFetcher;
    }

    /**
     * Use this as the init method in a spring context:
     */
    public void init()
    {
        try
        {
            Region region = Regions.getCurrentRegion();
            amazonAutoScaling.setRegion( region );
        }
        catch( Exception e )
        {
            //LOGGER.warn( "couldn't determine and set AWS region, are we operating on an EC2 instance?",e );
        }
    }
}
