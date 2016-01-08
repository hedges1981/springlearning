package rewards;

import config.AspectsConfig;
import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;


/** 
 * DONE-05: Plug in the aspect configuration.
 * Save all work, run the LoggingAspectTest.  It should pass, 
 * and you should see logging output in the console.	 
 */

@Configuration
//NOTE: again using one config class to import some more.
@Import({RewardsConfig.class, AspectsConfig.class})
public class SystemTestConfig {

	
	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource(){
		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
	
}
