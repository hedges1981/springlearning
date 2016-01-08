package rewards.internal.restaurant;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import rewards.Dining;
import rewards.internal.account.Account;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * Unit tests for exercising the behavior of the Restaurant aggregate entity. A restaurant calculates a benefit to award
 * to an account for dining based on an availability policy and benefit percentage.
 */
public class RestaurantTests {

	private Restaurant restaurant;

	private Account account;

	private Dining dining;

	@Before
	public void setUp() {
		// configure the restaurant, the object being tested
		restaurant = new Restaurant("1234567890", "AppleBee's");
		restaurant.setBenefitPercentage(Percentage.valueOf("0.08"));

		// configure supporting objects needed by the restaurant
		account = new Account("123456789", "Keith and Keri Donald");
		account.addBeneficiary("Annabelle");
		dining = Dining.createDining("100.00", "1234123412341234", "1234567890");
	}

	@Test
	public void testCalcuateBenefitFor() {
		MonetaryAmount benefit = restaurant.calculateBenefitFor(account, dining);

		// assert 8.00 eligible for reward
		assertEquals(MonetaryAmount.valueOf("8.00"), benefit);
	}

	
}