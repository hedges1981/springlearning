package amazonwebservices.ec2;

/**
 * Created by rowland-hall on 06/05/15
 */
public interface EC2AutoScalingInstanceManager
{
    void terminateThisAutoScalingInstance( boolean decrementDesiredCapacity );

    void terminateAutoScalingInstance( String ec2InstanceId, boolean decrementDesiredCapacity );
}
