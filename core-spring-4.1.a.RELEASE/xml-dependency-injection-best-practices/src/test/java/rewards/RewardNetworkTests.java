package rewards;

import common.money.MonetaryAmount;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A system test that verifies the components of the RewardNetwork application work together to reward for dining
 * successfully. Uses Spring to bootstrap the application for use in a test environment.
 */
public class RewardNetworkTests {

	/**
	 * The object being tested.
	 */
	private RewardNetwork rewardNetwork;

	@Before
	public void setUp() {
		
		//	DONE 01: Alter the configFiles array below, passing references
		//	to test-infrastructure-config.xml and application-config.xml when creating the application context.  
		//	Be sure to indicate the correct paths to these classpath resources.
		//	Run this test, it should pass.

        //NOTE: again the use of SpringApplication.run(...) this time with an array of strings pointing to the XML files.
		// commented out due to step 4: String[] configFiles = {"classpath:rewards/test-infrastructure-config.xml", "classpath:rewards/internal/application-config.xml"};
        String[] configFiles = {"classpath:rewards/test-infrastructure-config.xml"};
		String[] args = {};
		ApplicationContext context = SpringApplication.run(configFiles, args);		
		
		// Get the bean to use to invoke the application
		rewardNetwork = context.getBean(RewardNetwork.class);
	}

	@Test public void rewardForDining() {
		// create a new dining of 100.00 charged to credit card '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);

		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());

		// assert an account contribution was made
		AccountContribution contribution = confirmation.getAccountContribution();
		assertNotNull(contribution);

		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());

		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

		// the total contribution amount should have been split into 2 distributions
		assertEquals(2, contribution.getDistributions().size());

		// each distribution should be 4.00 (as both have a 50% allocation)
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount());
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount());
	}
}