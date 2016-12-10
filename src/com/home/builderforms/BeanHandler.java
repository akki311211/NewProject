package com.home.builderforms;

import org.springframework.beans.factory.BeanFactory;

import com.appnetix.app.control.web.FCInitHandlerServlet;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.home.builderforms.SpringUtil;

/**
 * <code>BeanHandler<code> is a helper, which avails bean from the corresponding <code>BeanFactory</code> of spring framework.
 * <code>BeanHanlder</code> also responsible for lazy factory initialization.
 *
 * @author Nishant Arora
 */

public class BeanHandler
{
	private static final String NOT_AVAILABLE = "not-available";
	private static final String BEAN_FACTORY = "bean-factory";
	private static final String CONFIG_PATH = "/xml/spring/";
	private static final String CONFIG_FILE = "spring-config.xml";
	
	public static <T> T getBean(String beanAlias,Class<T> classObj)
	{
		Object _bean = null;

		Object _beanFactory = MultiTenancyUtil.getTenantContext().getAttribute(BEAN_FACTORY);

		if(_beanFactory == null)
		{
			String springConfigFileName = CONFIG_PATH + CONFIG_FILE.replace("config",MultiTenancyUtil.getTenantName().toLowerCase());
			java.io.File springFile = new java.io.File(springConfigFileName);
			if(springFile.exists()) {
				_beanFactory = SpringUtil.prepareBeanFactory(new String[] { CONFIG_PATH + CONFIG_FILE.replace("config",MultiTenancyUtil.getTenantName().toLowerCase())});
			}
			MultiTenancyUtil.getTenantContext().setAttribute(BEAN_FACTORY,_beanFactory == null?NOT_AVAILABLE:_beanFactory);
		}
		
		if(!NOT_AVAILABLE.equals(_beanFactory))
		{
			 _bean = SpringUtil.getBean((BeanFactory)_beanFactory, beanAlias,classObj);
		}

		if(_bean == null)
		{

			_beanFactory = FCInitHandlerServlet.getControllerServletContext().getAttribute(BEAN_FACTORY);

			if(_beanFactory == null)
			{
				_beanFactory = SpringUtil.prepareBeanFactory(new String[] { CONFIG_PATH + CONFIG_FILE});
				
				FCInitHandlerServlet.getControllerServletContext().setAttribute(BEAN_FACTORY,_beanFactory == null?NOT_AVAILABLE:_beanFactory);
				
			}
	
			if(!NOT_AVAILABLE.equals(_beanFactory))
			{
				 _bean = SpringUtil.getBean((BeanFactory)_beanFactory, beanAlias,classObj);
			}
		}

		return (T) _bean;
	}

}
