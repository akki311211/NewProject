
<%@ page import="com.appnetix.app.util.LanguageUtil"%>
<%@ page import="com.appnetix.app.util.FieldNames"%>
<%@ page import="com.appnetix.app.util.Constants"%>
<%@ page import="com.appnetix.app.util.information.*"%>
<%@ page import="com.appnetix.app.util.tagutils.*"%>
<%@ page import="com.appnetix.app.portal.role.*"%>
<%@ page
	import="com.home.builderforms.BuilderFormFieldNames"%>
<%@ page import="com.home.builderforms.BuilderConstants"%>
<%@ page
	import="com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title") != null ? MultiTenancyUtil
					.getTenantContext().getAttribute("title") : LanguageUtil
					.getString("Franchise System",
							(String) session.getAttribute("userLanguage"))%></title>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="tabstablefim" style="display:<%=detailDisplay%>">
		<tr>
			<td width="100%">
				<div id="pettabs" class="cmIndentmenu">
					<ul style="width: 100%">
						<li class=""><a href="#"
							class="<%=("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName)) ? "selected" : ""%>"
							onclick="javascript:nextReportFim('fim','franchisee');"><span
								style="font-size: 12px"><%=LanguageUtil.getString("Franchisee")%></span></a></li>
						<li class=" "><a href="#"
							class="<%=("mu".equals(subModuleName)) ? "selected" : ""%>"
							onclick="javascript:nextReportFim('fim','mu');"><span
								style="font-size: 12px"><%=LanguageUtil.getString("Multi-Unit / Entity")%></span></a></li>
						<li class=" "><a href="#"
							class="<%=("area".equals(subModuleName)) ? "selected" : ""%>"
							onclick="javascript:nextReportFim('fim','area');"><span
								style="font-size: 12px"><%=LanguageUtil.getString("Regional")%></span></a></li>
					</ul>
					<div style="clear: both"></div>
				</div>
			</td>
			<td><img
				src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
				width="1" height="12"></td>
		</tr>
	</table>
</body>
</html>