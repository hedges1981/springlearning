package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({AppConfig.class,DbConfig.class,SecurityConfig.class})
@EnableTransactionManagement
public class RootConfig {

}
