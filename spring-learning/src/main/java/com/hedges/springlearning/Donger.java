package com.hedges.springlearning;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Donger 
{
	private String preMessage;     ////
	
	private String message;
	
	public Donger( String arg1, String arg2 )
	{
		System.out.println( "arg1="+arg1+" arg2="+arg2);
	}
	
	@PostConstruct
	public void ssss()
	{
		preMessage = "you! ";
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}

	public void printMessage()
	{
		System.out.println(preMessage+message);
	}
	
	@PreDestroy
	public void tttt()
	{
		System.out.println("in PreDestroy");
	}

}
