<%--
 * Name 	- builderFimFieldOrdering.jsp
 * @author  - Abishek Gupta
 * @version 1.1.1.1
 * @date	- 09/01/2012
--%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import="com.appnetix.app.util.tagutils.*" %>
<%@ taglib uri="/artags" prefix="ar" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/site.css" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/popNew.js"></script>

<SCRIPT language=JavaScript>
<%=request.getAttribute( "menuBar")%>
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" cellpadding="0" cellspacing="0" valign="top" >
		<tr>
			<td><img src="../images/spacer.gif" width="10" height="1"></td>
			<td width = "100%" valign = top>
				<table width="100%" cellpadding="0" cellspacing="0" valign="top" align="left">
                	<tr>
                    	<td><img src="../images/spacer.gif" width="10" height="5"></td>
					</tr>
					<tr>
						<td  width="100%" height="19" class="text"><a href="administration"><u><%=LanguageUtil.getString("Admin",(String)session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="administration#FIM"><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, Constants.KEYVAL_FIM, "FIM")%></a> >&nbsp;<span class="text_b"><%=LanguageUtil.getString("Customize FIM Custom Tab",(String)session.getAttribute("userLanguage"))%></span></td>
					</tr>
                    <tr>
                    	<td><img src="../images/spacer.gif" width="10" height="5"></td>
	                </tr>
				</table>
			</td>
			<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
		</tr>
		<tr>
			<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="50%" height="1" bgcolor="#bbbbbb"><img src="../images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
		</tr>

		<tr>
	    <td width="10"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="10" height="1"></td>
	    <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
	    <td width="10"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="10" height="1"></td>
	    </tr>
		<%
             String addDepartmentHtml="&nbsp;&nbsp;[&nbsp;<a href='modifyFimCustomTabFieldPosition' class='text_b'><u>"+LanguageUtil.getString("Modify Fields Position",(String)session.getAttribute("userLanguage"))+ "</u></a>&nbsp;]";
             %> 
		<tr>
			<td>&nbsp;</td>
			<td>
				<ar:newdisplaytable width="100%" scope="request" name="fieldMap"  recordsPerPage="20"  tableSize="20" tableHeader="<%=LanguageUtil.getString("Custom Fields",(String)session.getAttribute("userLanguage"))%>" recordsHeader="<%=addDepartmentHtml%>" i18n="true" tableHeaderClass="summary_bg"> 
				<ar:newtableheader headerHtmlClass="tb_sub_hdr_b" cellSpacing="1" cellPadding="4"/>
				<ar:newcolumn colName="<%=FieldNames.LABEL_NAME%>" colDispName="No."  colHeaderWidth="10%"/>
				<ar:newcolumn colName="<%=FieldNames.DISPLAY_NAME%>" colDispName="Field Name"  colHeaderWidth="40%"/>
				<ar:newcolumn colName="<%=FieldNames.DATA_TYPE%>" colDispName="Field Type"   colHeaderWidth="10%"/>
				<ar:newcolumn colName="<%=FieldNames.VISIBLE%>" colDispName="Visible" colHeaderWidth="10%"/>
				<ar:newcolumn colName="<%=FieldNames.EXPORTABLE%>" colDispName="Exportable and Searchable" colHeaderWidth="20%"/>
                <ar:newcolumn colName="<%=FieldNames.ACTION%>"  colDispName="Action"   colHeaderWidth="20%"/>
				</ar:newdisplaytable>
			</td>
			<td>&nbsp;</td>
		</tr>
		
</table>
<form name = "blankForm" action = "customizeFimCustomTab" method="post" <%=Constants.FORM_ENCODING%>>
	<input type='hidden' name='action' value=''>
</form>
<form name ="deleteForm" action = "genericHandler" method="post" <%=Constants.FORM_ENCODING%>>
	<input type='hidden' name='<%=FieldNames.FIELD_ID%>' value=''>
	<input type='hidden' name='<%=FieldNames.DISPLAY_NAME%>' value=''>
	<input type='hidden' name='<%=FieldNames.DATA_TYPE%>' value=''>
	<input type='hidden' name='<%=FieldNames.FIELD_LENGTH%>' value='0'>
	<input type='hidden' name='<%=FieldNames.VISIBLE%>' value='0'>
	<input type='hidden' name='<%=FieldNames.EXPORTABLE%>' value='0'>
	<input type='hidden' name='<%=FieldNames.SEARCHABLE%>' value='0'>
	<input type='hidden' name='<%=FieldNames.AVAILABLE%>' value='1'>
	<input type='hidden' name='<%=FieldNames.TABLE_ANCHOR%>' value='<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>'>
	<input type='hidden' name='<%=FieldNames.RETURN_PAGE%>' value='customizeFimCustomTab'>
	<input type='hidden' name='<%=FieldNames.EVENT_TYPE%>' value='modify'>
	<input type='hidden' name='<%=FieldNames.ORDER_NO%>' value=''>
	<input type='hidden' name='<%=FieldNames.PROCESSING_CLASS%>' value='com.appnetix.app.portal.admin.AddFimCustomTabFieldManipulator'>
<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="fim-addfimcustomtabfieldmanipulator" >
	<input type='hidden' name='type' value='delete'>
	<input type='hidden' name='<%=FieldNames.TABLE_NAME%>' value=''>
	<input type='hidden' name='<%=FieldNames.FIELD_NO%>' value=''>
</form>

<script>
    function redirect(action,fieldID,fieldNo,orderNO){                        
  	if(action=="0")
  	{
  		window.open("addFimCustomTabField?fieldID="+fieldID+"&type=Modify",'AddDepartment','scrollbars=yes,menubar=no,location=no,toolbar=no,resizable=yes,status=no,width=480,height=230,top=130,left=130');
  	}else if(action=="2")
  	{
  		window.open("addFimCustomTabField?fieldID="+fieldID+"&&type=Add",'AddDepartment','scrollbars=yes,menubar=no,location=no,toolbar=no,resizable=yes,status=no,width=480,height=190,top=130,left=130');
  	}else if(action=="3")
  	{
  		window.open("addFimCustomTabCombo?fieldID="+fieldID+"&&type=Add",'AddDepartment','scrollbars=yes,menubar=no,location=no,toolbar=no,resizable=yes,status=no,width=480,height=190,top=130,left=130');
  	}else
  	{
		if(confirm("Are you sure you want to delete this Custom Field?"))
  		{
  			document.deleteForm.<%=FieldNames.FIELD_ID%>.value=fieldID;
  			document.deleteForm.<%=FieldNames.FIELD_NO%>.value=fieldNo;
  			document.deleteForm.<%=FieldNames.ORDER_NO%>.value=orderNO;
  			document.deleteForm.submit();
  		}
  	}
}
<%=request.getAttribute("methodBuffer")%>
</script>
