package rewards.internal;

import common.money.MonetaryAmount;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

/**
 * Rewards an Account for Dining at a Restaurant.
 * 
 * The sole Reward Network implementation. This object is an application-layer service responsible for coordinating with
 * the domain-layer to carry out the process of rewarding benefits to accounts for dining.
 * 
 * Said in other words, this class implements the "reward account for dining" use case.
 */
public class RewardNetworkImpl implements RewardNetwork {

	private AccountRepository accountRepository;

	private RestaurantRepository restaurantRepository;

	private RewardRepository rewardRepository;

	/**
	 * Creates a new reward network.
	 * @param accountRepository the repository for loading accounts to reward
	 * @param restaurantRepository the repository for loading restaurants that determine how much to reward
	 * @param rewardRepository the repository for recording a record of successful reward transactions
	 */
	public RewardNetworkImpl(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
			RewardRepository rewardRepository) {
		this.accountRepository = accountRepository;
		this.restaurantRepository = restaurantRepository;
		this.rewardRepository = rewardRepository;
	}

	//	DONE-06: Add the annotation to cause the rewardAccountFor method to listen to JMS messages.
	//	Set the destination to the dining queue name defined earlier.
	//	Use a separate annotation to cause the return value to be sent to the confirmation queue.
	//	(Hint: Use the queue names you provided, not the IDs of the beans).	
	
	@Transactional
    //NOTE: how this method is defined to listen to the Dining queue, and send its return value to the confirmation queue.
    //NOTE: the @JmsListener also allows you to define a message selector, e.g. as you can with @MessageDriven in EJB.
    @JmsListener(destination="diningQueue")  //NOTE: look in JmsInfrastructureConfig where we have defined a queue in the context
    //with this name, so spring can do all the wiring.
    @SendTo(value="confirmationsQueue")
	public RewardConfirmation rewardAccountFor(Dining dining) {
		Account account = accountRepository.findByCreditCard(dining.getCreditCardNumber());
		Restaurant restaurant = restaurantRepository.findByMerchantNumber(dining.getMerchantNumber());
		MonetaryAmount amount = restaurant.calculateBenefitFor(account, dining);
		AccountContribution contribution = account.makeContribution(amount);
		return rewardRepository.confirmReward(contribution, dining);
	}
}