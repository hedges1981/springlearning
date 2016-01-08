package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Import( RewardsConfig.class )
public class TestInfrastructureConfig {

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource(){

        //NOTE how here we programatically make an embedded database. See how we set the db type to use.
        //NOTE: the default db type is HSQL if not specified.

        return
			(new EmbeddedDatabaseBuilder()).setType( EmbeddedDatabaseType.HSQL ).setName("someDb")
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
}
