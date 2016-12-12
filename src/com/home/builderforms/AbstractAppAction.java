/*
 *
 * Class Name	- AbstractAppAction
 * Location		- com.appnetix.app.action.generic
 * @author		- Amit Gokhru
 * @version		- 1.1.1.1, 
 * date			- 28/12/2003

 * 

 This is the abstract action class for the whole application . All other action class should extends this class.	 
   

 
 */
package com.home.builderforms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


public class AbstractAppAction implements ServletRequestAware,ServletResponseAware{ 
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	public String getRequestValue(HttpServletRequest request, String colName) {
    	String val = null;
    	try {
    		val = request.getParameter(colName);
    		if(val == null) {
    			val = (String)request.getAttribute(colName);
    		}
    	} catch(Exception e) {
    		return val;
    	}
		return val;
	}
	public Object getRequestObject(HttpServletRequest request, String colName) {
    	Object val = null;
    	try {
    		val = request.getParameter(colName);
    		if(val == null) {
    			val = (Object)request.getAttribute(colName);
    		}
    	} catch(Exception e) {
    		return val;
    	}
		return val;
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}
};