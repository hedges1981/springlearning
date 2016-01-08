package rewards;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration

//	DONE-04: Revise this Configuration class with one that imports XML file resources instead of Java.
//	The two XML files to import are "rewards-config.xml" and "test-infrastructure-config.xml"
//	You will need to specify the classpath locations of the files.  
//	Remove / comment-out the dataSource bean method; it will now be defined via XML.
//	Save your work and re-run the last test, it should pass.

//NOTE: see the use of the @ImportResource here. Note also how the classpath to the file is done
//with the different packages they are in
@ImportResource( {"classpath:config/rewards-config.xml", "classpath:rewards/test-infrastructure-config.xml"} )
public class TestInfrastructureConfig {


}
