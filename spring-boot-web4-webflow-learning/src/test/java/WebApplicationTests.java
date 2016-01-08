import com.hedges.web.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by rowland-hall on 08/01/16
 */
@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = WebConfig.class )
@WebAppConfiguration
public class WebApplicationTests
{
    @Test
    public void contextLoads()
    {
    }
}