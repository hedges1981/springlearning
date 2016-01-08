package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("accounts.web")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter  {

	/**
	 * 	TODO-04: Add an InternalResourceViewResolver bean definition. 
	 * 	Set the prefix and suffix properties, and refactor your controller 
	 * 	methods to return only the logical name of the view. 
	 * 	Modify your controller test class to account for the revised view names.
	 * 	Re-run your tests, they should pass.
	 * 	Save all work, restart the server.  You should be able to click 
	 * 	any of the account links and reach the detail page.
	 */

	/**
	*	Map URL /resources/* to serve static resources from classpath:/static/*
	*	This allows us to store and distribute css, images, etc. in JAR file.
	*	This is the equivalent of <mvc:resources/>
	*/
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/resources/**")
	           .addResourceLocations("classpath:/static/");
	 }    	
}
