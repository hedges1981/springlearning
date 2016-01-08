package rewards.internal.restaurant;

import common.money.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Tests the JDBC restaurant repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcRestaurantRepositoryTests {

	private JdbcRestaurantRepository repository;

	@Before
	public void setUp() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate( createTestDataSource() );
		repository = new JdbcRestaurantRepository(jdbcTemplate);
	}

	@Test
	public void testFindRestaurantByMerchantNumber() {
		Restaurant restaurant = repository.findByMerchantNumber("1234567890");
		assertNotNull("the restaurant should never be null", restaurant);
		assertEquals("the merchant number is wrong", "1234567890", restaurant.getNumber());
		assertEquals("the name is wrong", "AppleBees", restaurant.getName());
		assertEquals("the benefitPercentage is wrong", Percentage.valueOf("8%"), restaurant.getBenefitPercentage());
		assertEquals("the benefit availability policy is wrong", JdbcRestaurantRepository.AlwaysAvailable.INSTANCE,
				restaurant.getBenefitAvailabilityPolicy());
	}

	@Test
	public void testFindRestaurantByBogusMerchantNumber() {
		try {
			repository.findByMerchantNumber("bogus");
			fail("Should have thrown EmptyResultDataAccessException for a 'bogus' merchant number");
		} catch (EmptyResultDataAccessException e) {
			// expected
		}
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}
}
