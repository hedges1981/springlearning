package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//	TODO-04: Set the login page (below) so it uses '/login.jsp' and the access denied page so it uses '/denied.jsp'.
		//	Check the username/password of the user defined in configureGlobal and use it to log into the application. 
		//	This user is not allowed to access the 'Account List' page yet. 
		//	After log in, you should expect to see the access denied page. 

		//	TODO-05: Try to log in using incorrect user/password such as 'foo', 'foo'. Note that the you are sent back to 
		//	the login page with a friendly message.  Take a look at /login.jsp to see if you understand how the page
		//	displays the error message on bad userid or password.  Go to next step when ready.
		
		http
			.formLogin()
				.loginPage("/TODO")
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/TODO")
				.and()
				
		//	TODO-06: As defined below, users with role EDITOR can already access '/accounts/account*'. 
		//	Update the configuration so users with  role VIEWER can also access that same URL pattern.
		//	After completing this task, re-deploy the web application. User 'vince' should now be
		//	able to access the account list and account details. 
				
			.authorizeRequests()
				.antMatchers("/accounts/resources/**").permitAll()
				.antMatchers("/accounts/edit*").hasRole("EDITOR")
				.antMatchers("/accounts/account*").hasAnyRole("EDITOR","VIEWER")
				.and()
				
		//	TODO-07: Log out by clicking on the 'log out' link. 
		//	Then try to access 'http://localhost:8080/security/accounts/hidden'.
		//	As you can see, this URL is currently not protected.
		//	Add an intercept-url with pattern /accounts/** to serve as a catch-all below all other intercept-url tags.	
		//	For this pattern, all users should be authenticated (no specific role required). 
		//	Save and try to access this URL again, you should now be redirected to the login page. 
				
				
			.logout()
				.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		//	TODO-08: Add a user edith/edith with role 'EDITOR' (hint, use the and() method).  
		//	Save and restart the web application. 
		//	If you don't see the login form, log out from the application by clicking on the "log out" link.
		//	You can now log in with the user 'edith'.  
		//	Once logged in, click one of the links to reach the  account details page, then click the "Edit account" link.  
		//	You will be able to edit account details. Try to log in again using 'vince' and double-check 
		//	that vince, who only has 'VIEWER' rights, is still not allowed to edit account information.
		
		auth
			.inMemoryAuthentication()
				.withUser("vince").password("vince").roles("VIEWER");
	}
	
	//	TODO-11: (Bonus) improve security by using standard encoding based on sha-256 hash. 
	//	For each of the users declared in the inMemoryAuthentication section above, replace the password with the
	//	sha-256 encoded versions of them below.  Afterwards save, restart, and try logging in again.
	//	
	//	Encoded version of vince is 08c461ad70fce6c74e12745931085508ccb2090f2eae3707f6b62089c634ddd2636f380f40109dfb
	//	Encoded version of edith is 4cfbf05e4493d17125c547fdba494033d7aceee9310f253f3e96c4f928333d2436d669d63a84fe4f
	//
	//	If you'd like to generate another password, use (new StandardPasswordEncoder()).encode("thePassword")
	
	
}
