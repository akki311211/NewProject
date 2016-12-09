<%--
 - $Header						:  \web\jsp\builderForm\builderFormActionPage.jsp
 - $Author						:  $Abhishek Gupta
 - $Version						:  $1.1
 - $Date						:  $ Dec 29,2011
 -
 - ==============================================================================================
 - Basic Description of JSP		: This jsp controls the action of builderFormModificationPopUp.jsp depending on the action value add , modify , delete .
 --%>
 
<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "com.appnetix.app.portal.forms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import="com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil" %>

<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="session"/>
<jsp:useBean id="builderWebForm" class="com.home.builderforms.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>
<jsp:setProperty name = "builderWebForm" property = "*" /><%--BB_Naming_Convention --%>
<%
try {
	String sAction = request.getParameter("action");
	String sFieldId = request.getParameter("fieldID");
	String sFimFormId = request.getParameter("fimFormId");
	String sFormID = request.getParameter("formID");
	int returnValue = -1;
	String sMessage = "";
	String action = request.getParameter("action");
	System.out.println(" sFormID: " + sFormID + " sFimFormId: " + sFimFormId + " sAction: " + sAction);

	if (sAction != null && sAction.equals("modify")) {
		session.setAttribute("returnVal", "returnModifyValue");
		String rV = (String)session.getAttribute("returnVal");
		System.out.print("rV"+rV);
	}
	
	if (sAction != null && sAction.equals("add")) {
		session.setAttribute("returnVal", "returnAddValue");
		String rV = (String)session.getAttribute("returnVal");
		System.out.print("rV"+rV);
		//sAction = "modify";
	}	
	
	System.out.println("sAction.........>"+sAction);
%>
<link rel="stylesheet" href="style.css" type="text/css">
<%
	if (sAction.equals("delete")) {
		returnValue = builderWebForm.deleteBuilderField(sFieldId,sFormID);//BB_Naming_Convention
		if (returnValue != -1)
			sMessage = LanguageUtil.getString("Field Has Been deleted successfully.");
		else
			sMessage = LanguageUtil.getString("Internal Error: Could not delete the specified Field.");
	} else if (sAction.equals("add")) {
		MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = true;
		builderForm.setBuilderFormAddOrUpdate(request);
		/*System.out.println("builderUpdate========>>"+builderUpdate);
		if (builderUpdate)
			sMessage = LanguageUtil.getString("Field Has Been added successfully.");
		else
			sMessage = LanguageUtil.getString("Internal Error: Could not add the specified Field.");*/
	} else if (sAction.equals("modify")) {
		MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = true;
		builderForm.setBuilderFormAddOrUpdate(request);
		//returnValue = builderWebForm.modifyBuilderField(sFieldId, sFormID);//BB_Naming_Convention
		/*if (returnValue != -1 && action.equalsIgnoreCase("modify")) {
			sMessage = LanguageUtil.getString("The Custom Field has been modified successfully.");
		} else if (returnValue != -1 && action.equalsIgnoreCase("add")) {
			sMessage = LanguageUtil.getString("The Custom Field has been added successfully.");
		} else {
			sMessage = LanguageUtil.getString("Internal Error: Could not") +" " + action + " " + LanguageUtil.getString("the specified Custom Field.");
		}*/
	}
%>
<link rel=stylesheet href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<html>
<head>
	<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title")!= null ?MultiTenancyUtil.getTenantContext().getAttribute("title") : "Franchise System"%></title>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad=closeWindow()>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<%-- BUG_76360 Start
	<tr>
		<td width="10"><img src="/demo/images/spacer.gif" width="10" height="8"></td>
		<td class="pg_hdr" height="22" width="100%"> &nbsp;&nbsp;<%=LanguageUtil.getString("Customize FIM Form")%></td>
		<td width="10"><img src="/demo/images/spacer.gif" width="10" height="8"></td>
	</tr>
	<tr>
		<td width="10">&nbsp;</td>
		<td height="22" width="100%">
			<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<tr>
				<td colspan="2" height="25" align="left" class="tb_hdr_b" nowrap><%=LanguageUtil.getString("Confirmation")%></td>
			</tr>
			<tr>
				<td class="tb_data" align="left" width="100%"><%=sMessage%></td>
			</tr>
			<tr>
				<td class="tb_hdr" align="left"><input type = "button"  value="<%=LanguageUtil.getString("Close")%>" onClick="closeWindow()" class="new_button" name="button"></td>
			</tr>
			</table>
		</td>
		<td width="10">&nbsp;</td>
	</tr>
	BUG_76360 End--%>
	</table>
	
</body>
<%-- BUG_76360 Start--%>
<script language="javascript">
         //   window.resizeTo(600,300);
		function closeWindow() 	{
			if (window.opener && window.opener.document.blankForm) 	{
				window.opener.document.blankForm.action.value = "<%=sAction%>";
				window.opener.document.blankForm.formID.value = "<%=sFormID%>";
				window.opener.document.blankForm.formNames.value = "<%=sFormID%>";
				window.opener.document.blankForm.submit();
			}
			window.close();
		}
	</script>
<%-- BUG_76360 End--%>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>	
</html>