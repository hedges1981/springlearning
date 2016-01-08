package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Java-based web configuration file, replacement for web.xml
 */
//NOTE: looks like this class allows you to skip using a web.xml and instead initialise a DispatcherServlet programaticaly.
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {

	/**
	 * One way to select an active profile, set a system property before startup:
	 */
	public WebInitializer() {
		super();
		System.setProperty("spring.profiles.active", "jpa");
	}

	/**
	 * Tell Spring what configuration class(es) 
	 * to use for the Root context:
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{ RootConfig.class };
	}

	/**
	 * Tell Spring what configuration class(es) 
	 * to use for the DispatcherServlet context:
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{ MvcConfig.class };
	}

	/**
	 * Tell Spring what mapping to use 
	 * for the DispatcherServlet:
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/accounts/*"};
	}

	//	TODO-01: Examine the web configuration entries above.  
	//	Deploy this web application to the server.
	//	Once deployed, navigate to http://localhost:8080/mvc . 
	//	Click the View Account List, you should see a list of accounts.
	//	Click any of the account links, you will get a 404.  
	//	This is what we will implement in the next few steps.
	
}

