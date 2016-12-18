package com.home.builderforms;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;
import com.home.builderforms.ResponseWrapper;
import com.home.builderforms.XSSRequestWrapper;
import com.opensymphony.xwork2.ActionContext;

public class StrutsUtil {
	static Logger logger = Logger.getLogger(StrutsUtil.class);

	/**
	 * Method is replacement of <code>mapping.getPath()</code> method of
	 * struts1.3, This method returns mapping path of registered action in
	 * struts.xml example <code> action name ="Demo" </code> it return Demo as a
	 * string *
	 * 
	 * */
	public static String getPath() {
		return "/"
				+ (String) ServletActionContext.getContext()
						.getActionInvocation().getProxy().getActionName();
	}

	/**
	 * Method is replacement of <code>mapping.findforrward().getPath()</code>
	 * method of struts1.3 , This is same as getPath() method but returns
	 * mapping path of results of registered action in struts.xml example action
	 * name ="Demo" result name="success" type="tiles"> DemoTiles , it return
	 * DemoTiles as a string *
	 * */
	public static String getPath(String result) {
		return (String) ServletActionContext.getContext().getActionInvocation()
				.getProxy().getConfig().getResults().get(result).getParams()
				.get("location");
	}

	/**
	 * Method is replacement of <code>ActionForward("path")</code> method of
	 * struts1.3 , This method dispatch request to path given in parameter *
	 * */
	public static String actionForward(String forwardPath) {
		return actionForward(forwardPath, false);
	}

	/**
	 * Method is replacement of <code>ActionForward("path",true/false)</code>
	 * method of struts1.3 , This method dispatch request to path given in
	 * parameter if second parameter is false , and send redirect if true is
	 * given in second parameter
	 * 
	 * */
	public static String actionForward(String forwardPath, boolean redirect) {
		if (!StringUtil.isValid(forwardPath)) {
			return "";
		}
		try {
			HttpServletResponse res = ServletActionContext.getResponse();
			HttpServletRequest req = ServletActionContext.getRequest();
			if (!redirect) {
				RequestDispatcher rd = ServletActionContext.getServletContext()
						.getRequestDispatcher(forwardPath);
				if (rd == null) {
					res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					logger.error("Error in processing RequestDispatcher  .. "
							+ forwardPath);
					return "";
				}
				rd.forward(req, res);
			} else {
				if (forwardPath.startsWith("/")) {
					forwardPath = req.getContextPath() + forwardPath;
				}
				res.sendRedirect(forwardPath);
			}
		} catch (Exception e) {
			logger.error("Error in processing ActionForward req .. "
					+ forwardPath, e);

		}
		return "";
	}

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(
				StrutsStatics.HTTP_REQUEST);
	}

	public static HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(
				StrutsStatics.HTTP_RESPONSE);
	}

	public static HttpServletRequest wrapHttpServletRequest(HttpServletRequest _request) {
		String _multiPartRequest = "_strutsMultipartRequest";

		String _contentType = _request.getContentType();

		if (_contentType != null
				&& _contentType.contains("multipart/form-data")) {
			_request.setAttribute(_multiPartRequest, Boolean.TRUE);
		} else {
			
			if(true) {
				_request = new XSSRequestWrapper(_request); 
			
			}
			else{
				_request.setAttribute(_multiPartRequest, Boolean.FALSE);
			_request = new StrutsRequestWrapper(_request, Boolean.TRUE);
		}}

		return _request;
	}
	/**
	 * 
	 * @param _request
	 * @param _response
	 * @return_response
	 * @description_wrap response in case of XSS Security.
	 */
	public static HttpServletResponse wrapHttpServletResponse(HttpServletRequest _request,HttpServletResponse _response) {
		
		_response = new ResponseWrapper(_response,_request);
		return _response;
	}
}