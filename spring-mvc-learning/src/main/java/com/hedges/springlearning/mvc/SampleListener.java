package com.hedges.springlearning.mvc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class SampleListener implements ServletContextListener
{
	private static final Logger LOGGER = Logger.getLogger(SampleListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		
		BasicConfigurator.configure();
		LOGGER.info("Context intialised");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		LOGGER.info("Context destroyed");
		
	}

}
