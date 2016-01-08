package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("rewards")
//NOTE: the use of the @ComponentScan, equivalent to <context:component-scan base-package="rewards" if it was XML config
public class RewardsConfig {


	
}
