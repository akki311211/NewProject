package com.home.builderforms;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <code>SpringUtil<code> provides basic utility methods for using spring-framework. 
 * 
 * @author Nishant Arora
 *
 */

public class SpringUtil
{
	
	public static BeanFactory prepareBeanFactory(String[] configFiles) 
	{
		BeanFactory _beanFactory = null;
    	
		try
    	{
			_beanFactory = new ClassPathXmlApplicationContext(configFiles);
    	}
    	catch(Exception ex)
    	{
    	}
    	
        return _beanFactory;
		
	}
	
	public static <T> T getBean(BeanFactory beanFactory,String beanAlias,Class<T> classObj) 
	{
		T _beanObj = null;
		
		try
		{
			if(beanFactory!=null && beanFactory.containsBean(beanAlias))
			{
				_beanObj = beanFactory.getBean(beanAlias,classObj);	
			}
		}
		catch (Exception ex) 
		{
		}
		
		return _beanObj;
	}
}

