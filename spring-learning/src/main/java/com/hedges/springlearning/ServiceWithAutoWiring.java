package com.hedges.springlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("aDifferentNameForServiceWithAutoWiring")
public class ServiceWithAutoWiring
{
	@Autowired
	@Qualifier("autowiredComponent")//use this @Qualifier to specify a specific bean name.
	private AutowiredComponent autowiredComponent;
	
	public ServiceWithAutoWiring()
	{
		int x=0;
	}
	
	public void doThing()
	{
		System.out.println("ServiceWithAutoWiring doing thing, calling autowiredComponent");
		autowiredComponent.doThing();
	}
}
