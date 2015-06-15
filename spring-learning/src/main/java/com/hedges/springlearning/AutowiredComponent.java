package com.hedges.springlearning;

import org.springframework.stereotype.Component;

@Component
public class AutowiredComponent 
{
	public AutowiredComponent()
	{
		int x=0;
	}
	
	public void doThing()
	{
		System.out.println("AutoWired component doing its thing");
	}
}
