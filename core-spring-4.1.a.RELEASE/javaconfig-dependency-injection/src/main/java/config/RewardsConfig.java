package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

/**
 * Created by rowland-hall on 15/12/15
 */
@Configuration
public class RewardsConfig
{
    @Autowired
    private DataSource dataSource;

    @Bean
    public RewardNetwork rewardNetwork()
    {
        //note how you use the @Bean method name to inject a predefined bean,
        //NOTE: because all of the calls to this config class are done through a proxy, it is smart enough to
        //only call the method once to make the bean, then just put in a reference to the bean the next time the method is
        //called....method names used as bean reference.
        return new RewardNetworkImpl( accountRepository(), restaurantRepository(), rewardRepository() );
    }

    @Bean
    public AccountRepository accountRepository()
    {
        JdbcAccountRepository accountRepository = new JdbcAccountRepository();
        accountRepository.setDataSource( dataSource );

        return accountRepository;
    }

    @Bean
    public RestaurantRepository restaurantRepository()
    {
        JdbcRestaurantRepository restaurantRepository = new JdbcRestaurantRepository();
        restaurantRepository.setDataSource( dataSource );

        return restaurantRepository;
    }

    @Bean
    public RewardRepository rewardRepository()
    {
        JdbcRewardRepository rewardRepository = new JdbcRewardRepository();
        rewardRepository.setDataSource( dataSource );

        return rewardRepository;
    }



}
