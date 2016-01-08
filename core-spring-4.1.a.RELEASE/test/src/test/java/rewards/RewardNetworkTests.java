package rewards;

import common.money.MonetaryAmount;
import config.RewardsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 */

/* DONE 01: - Remove setUp() and tearDown() methods and use spring-test annotations instead such as @ContextConfiguration.
 * 			- Remove the attribute 'context' which is not needed anymore.
 * 			- Use @Autowire to populate the rewardNetwork instance.
 *			- Re-run the current test, it should pass.
 */

/* DONE 02: In the package rewards/internal, annotate all 'Stub*Repository' classes with the @Repository annotation
 * 			(without specifying any profile yet).
 * 			Rerun the current test, it should fail.  Why?
 * 		Fails because the addition of the @Repository causes them to get picked up by a component scan, there are now 2 matching beans
 	* 	so can't do the injection.
 */

/* DONE 03: - Using the @Profile annotation, assign the 'jdbc' profile to all Jdbc*Repository classes
 * 			(such as JdbcAccountRepository).  (Be sure to annotate the actual repository classes in
 * 			src/main/java, not the test classes in src/main/test!)
 * 			- In the same way, assign the 'stub' profile to all Stub*Repository classes 
 * 			(such as StubAccountRepository)
 * 			- Add @ActiveProfiles to this test class (below) and specify the "stub" profile.
 * 			- Run the current test, it should pass. 
 * 			  Examine the logs, they should indicate "stub" repositories were used.
 */

/* DONE 04: Change active-profile to "jdbc". Rerun the test, it should pass.
 * 			Which repository implementations are being used now?   - the JDBC ones
 */

/* DONE 05: See corresponding step in TestInfrastructureDevConfig.
 */

/* DONE 06: Now that the bean 'dataSource' is specific to the jdbc-dev profile, should we expect
 * 			this test to be successful?   - no as now need to specify the jdbc-dev profile to get a dataSource
 * 			Make the appropriate changes so the current test uses 2 profiles ('jdbc' and 'jdbc-dev').
 * 			Rerun the test, it should pass.
 */

/* DONE 07: Open TestInfrastructureProductionConfig and note the different datasource that will be
 * 			used if the profile = 'jdbc-production'.
 * 			Now update the current test so it uses profiles 'jdbc' and 'jdbc-production'. 
 * 			Rerun the test, it should pass.
 */

/* DONE 08: Bonus question: see the 'Optional Step' inside the Detailed Instructions.
 */


@RunWith( SpringJUnit4ClassRunner.class)
//NOTE: the use of @ContextConfiguration with a class, or list of classes.
@ContextConfiguration()//classes=TestInfrastructureConfig.class)
@ActiveProfiles({"jdbc","jdbc-production"})    //NOTE;the use of the @ActiveProfiles to specify the profile(s) for the test.
public class RewardNetworkTests {

	
	/**
	 * The object being tested.
	 */
    @Autowired
	private RewardNetwork rewardNetwork;

	@Test
	public void testRewardForDining() {
		// create a new dining of 100.00 charged to credit card
		// '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234",
				"1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		RewardConfirmation confirmation = rewardNetwork
				.rewardAccountFor(dining);

		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());

		// assert an account contribution was made
		AccountContribution contribution = confirmation
				.getAccountContribution();
		assertNotNull(contribution);

		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());

		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

		// the total contribution amount should have been split into 2
		// distributions
		assertEquals(2, contribution.getDistributions().size());

		// each distribution should be 4.00 (as both have a 50% allocation)
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution
				.getDistribution("Annabelle").getAmount());
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution
				.getDistribution("Corgan").getAmount());
	}


    //NOTE: with the @ContextCOnfiguration at the top of the class, with nothing in it, it will pick up this inner class for the test config.
    //NOTE: if this is a private class, it DOESN'T work.
    @Configuration
    @Import({
                    TestInfrastructureDevConfig.class,
                    TestInfrastructureProductionConfig.class,
                    RewardsConfig.class })
    static class TestInfrastructureConfig {

        public LoggingBeanPostProcessor loggingBean(){
            return new LoggingBeanPostProcessor();
        }
    }

}