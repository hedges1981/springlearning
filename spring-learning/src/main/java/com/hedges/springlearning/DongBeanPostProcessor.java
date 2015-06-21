package com.hedges.springlearning;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DongBeanPostProcessor implements BeanPostProcessor
{

	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		
			System.out.println( "In postProcessAfterInitialisation, arg0="+arg0+" arg1="+arg1);
			return arg0;
	}

	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		
		System.out.println( "In postProcessBeforeInitialisation, arg0="+arg0+" arg1="+arg1);
		return arg0;
	}

}
