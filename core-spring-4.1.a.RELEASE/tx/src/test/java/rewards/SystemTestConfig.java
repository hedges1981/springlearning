package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@Import(RewardsConfig.class)
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
	
	
	//	DONE-02: Define a bean named 'transactionManager' that configures a DataSourceTransactionManager

    @Bean
    //NOTE the use of the PlatformTransactionManager interface, this is the interface to use for a txn mgr,
    //there is no 'TransactionManager' interface in spring.
    public PlatformTransactionManager transactionManager()
    {
        return new DataSourceTransactionManager( dataSource() );
    }
	
}
