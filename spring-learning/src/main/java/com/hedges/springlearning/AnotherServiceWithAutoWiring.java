package com.hedges.springlearning;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class AnotherServiceWithAutoWiring
{
	//@Autowired
	private AutowiredComponent autowiredComponent;

    @Value("#{testProps['testProperty']}")
    private String injectedValue;

    @Value("#{valueBean.value1}")
    private String valueBeanValue1;

    public void setAutowiredComponent(AutowiredComponent autowiredComponent) {
		this.autowiredComponent = autowiredComponent;
	}

	public void doThing()
	{
		System.out.println("AnotherServiceWithAutoWiring doing something, injectedValue="+injectedValue);
		autowiredComponent.doThing();
	}

    @Async
    public void doThingAsync()
    {
        System.out.println("Doing async method, threadId="+Thread.currentThread().getId());
    }

    @Scheduled(fixedRate = 3000 )
    public void doScheduledTask()
    {
        System.out.println( "Doing Scheduled task at time: "+ new Date());
    }

    public String getValueBeanValue1()
    {
        return valueBeanValue1;
    }

    public void setValueBeanValue1( String valueBeanValue1 )
    {
        this.valueBeanValue1 = valueBeanValue1;
    }
}
