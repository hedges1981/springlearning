package amazonwebservices;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.apache.commons.lang3.StringUtils;


public class AWSRegionProvider {

    /**
     * If blank region passed then the current region of the running instance is used
     * @param regionOverride
     * @return The region object to set as part of the client
     */
    public static Region getCurrentRegion(String regionOverride){

        if ( StringUtils.isBlank( regionOverride ) )
        {
            return Regions.getCurrentRegion();
        }
        else
        {
            return Region.getRegion( Regions.fromName( regionOverride ) );
        }
    }
}
