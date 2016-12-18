package com.home.builderforms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.home.builderforms.StringUtil;

/**
 * This is response wrapper for providing some extra features for response class
 * @author Naman Jain
 *
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

	HttpServletRequest request;
	public ResponseWrapper(HttpServletResponse response, HttpServletRequest request) {
		super(response);
		this.request = request;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Override method sendRedirect.
	 * It is used to append the Token for preventing the CSRF. 
	 */
	@SuppressWarnings("deprecation")
	public void sendRedirect(String location) {
		HttpSession session = request.getSession();
		if(session != null) {
			if(StringUtil.isValidNew((String)session.getAttribute("csrfToken"))) {
				if(!location.contains("cft")) {
					if(location.contains("?")) {
						location = location + "&cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"));
					} else {
						location = location + "?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"));
					}
				}
			}
		}
		
		if(StringUtil.isValidNew(location) && location.contains("&#61;")) {
			location = location.replaceAll("&#61;", "=");
		}
		try {
			if(StringUtil.isValidNew(location) && location.startsWith("http") &&  !location.contains("skipUrl") && !location.contains("vendastaSSOURL") && !location.contains("whWhatsNew") && !location.contains("alerts") && !location.contains("messages") && !location.contains("troubleTickets")) { //checking for URL out of the box P_B_LL_65323 
				super.sendRedirect(request.getContextPath()+"/ErrorPage.jsp");
			} else {
				super.sendRedirect(location);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
