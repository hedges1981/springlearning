package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


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
	
	
	//	DONE-07: Define a LocalContainerEntityManagerFactoryBean with the name entityManagerFactory
	//	Be sure to set the dataSource and jpaVendorAdaptor properties appropriately.

    @Bean
    //NOTE: pay attention to this method, lots of stuff in here:
    //NOTE: using this bean replaces the need for a persistenceContext.xml
    public AbstractEntityManagerFactoryBean entityManagerFactory()
    {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabase( Database.HSQL); //note that this matches the datasource above, the default datasource
        //it creates is HSQL.

        Properties props = new Properties();
        props.setProperty("hibernate.format_sql", "true");

        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());

        //NOTE: here telling it to scan the package for JPA entities.
        emfb.setPackagesToScan("rewards.internal");
        emfb.setJpaProperties(props);
        emfb.setJpaVendorAdapter(adapter);

        return emfb;

    }


	//	DONE-08: Define a JpaTransactionManager bean with the name transactionManager.
	//	The @Bean method should accept a parameter of type EntityManagerFactory.
	//	Use this parameter when instantiating the JpaTransactionManager.
	//	Run the RewardNetworkTests, it should pass.

    @Bean
    //  NOTE: because we are using a factory bean to create the EntityManagerFactory, we must pass a bean of type
    //EntityManagerFactory in to this method, it would not be possible to pass in e.g. entityManagerFactory() to the con.
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf)
    {
        return new JpaTransactionManager( emf );
    }
		
}
