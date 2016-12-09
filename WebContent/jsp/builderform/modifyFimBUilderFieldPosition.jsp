<%@ include file="/jsp/util/import.jsp"%>
<%@ page import="com.appnetix.app.util.information.*"%>
<%@ page import="com.appnetix.app.util.*"%>

<%--
 - $Header: modifyFimBUilderFieldPosition.jsp
 - $Author: Abhishek Gupta
 - $Version:  $1.1
 - $Date: 09/01/2012
--%>

<%
	String classStyle = "";
	SequenceMap positionInfo = (SequenceMap) request.getAttribute("POSITIONINFO");
	String setup = (String) request.getAttribute("setup");
	String setup1 = (String) request.getParameter("setup");
	StringBuffer htmlType = (StringBuffer)request.getAttribute("htmlType");
	// removing info objects from request
	request.removeAttribute("POSITIONINFO");
%>

<%@ page import="com.appnetix.app.util.*"%>
<%@ page language="java" import="java.sql.*"%>
<%@ page import="java.util.*"%>
<html>
<head>
<STYLE type="text/css">
OPTION.yellow {
	background-color: yellow;
	color: black
}

OPTION.green {
	background-color: green;
	color: black
}

OPTION.gray {
	background-color: gray;
	color: black
}

OPTION.orange {
	background-color: orange;
	color: black
}
</STYLE>

<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title") != null ? MultiTenancyUtil.getTenantContext().getAttribute("title") : "Franchise System"%></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/jquery/jquery.ui.all.css">
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.4.4.js"></script>
<script	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.mouse.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.sortable.js"></script>
<style>
#sortable {
	float: left;
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 600px;
}

#sortable li {
	float: left;
	margin: 5px;
	padding: 2px;
	font-size: 1.2em;
	width: 200px;
}
</style>
<script>
	$(function() {
		$( "#sortable" ).sortable(
			{
					out: function (event, ui) {
						document.getElementById("displayMsg").style.display="block";
						setTimeout("hidemessage()",2000);
					}
			}
		
		);
		$( "#sortable" ).disableSelection();
		
	});
	
	
	function hidemessage() {
		document.getElementById("displayMsg").style.display="none";
	}
	setTimeout("hidemessage()",1000);
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/position/demos.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/StringUtil.js"></script>
<script language="javascript">
function showProcessingAlert() {
	var flag;
	$.ajax({
        type: "POST",
        url: "handleAjaxRequest", 
        data:"dataset=checkFormBuilderThreadProcess",
        async:false,
        success:function(data) {
        	if(data.trim() == 'true') {
        		alert(FCLang.ANOTHER_FIELD_IN_REQUEST);
        		flag = "true";
        	} else {
        		flag = "false";
        	}       	  
         }
    });
	return flag;
}


	function submitForm() {
		
		if(showProcessingAlert() == 'true') {
			return false;		
		}

		var f = document.changeForm;
<%for (int i = 0; i < positionInfo.size(); i++) {%>		
			document.changeForm.<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>_<%=i%><%=FieldNames.FIELD_ID%>.value =  $('li').get(<%=i%>).title; 
			document.changeForm.<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>_<%=i%><%=FieldNames.ORDER_NO%>.value= <%=i%>;
		<%}%>
		f.action = "genericHandler";
		f.submit();

	}
	function goCancel() {
		JavaScript: location.href = 'administration?cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>'
	 }

	 function getFields()
	 {
		 document.modifyFieldPositions.sectionID.value=	document.getElementById("sectionID").value;
		 document.modifyFieldPositions.submit();
		 //alert(document.getElementById("sectionID").value);
	 }	 
</script>
<%
	String userTheme = (String) session.getAttribute("userTheme");
%>
</head>
<!--body onload="javascript: reloadMessage();"-->
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<form method="post" name="changeForm"><input type="hidden" <%=Constants.FORM_ENCODING%>
		name="<%=FieldNames.EVENT_TYPE%>" value="<%=EventType.MODIFY%>">
	<input type="hidden" name="<%=FieldNames.LEAD_STATUS_ID%>list">
	<%
		for (int i = 0; i < positionInfo.size(); i++) {
	%> <input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>"
		value="<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>_<%=i%>"> <input
		type="hidden"
		name="<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>_<%=i%><%=FieldNames.FIELD_ID%>">
	<input type="hidden"
		name="<%=TableAnchors.FIM_CUSTOM_TAB_FIELDS%>_<%=i%><%=FieldNames.ORDER_NO%>">
	<%
		}
	%> <input type="hidden" name="<%=FieldNames.ERROR_PAGE%>"
		value="errorPage"> <input type="hidden"
		name="<%=FieldNames.RETURN_PAGE%>"
		value="modifyFimCustomTabFieldPosition">
		<input type="hidden" action="save"/>
	<tr>
		<td width="10"><img
			src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10"
			height="12"></td>
		<td height="19" valign="bottom" class="text"><a
			href="administration"><%=LanguageUtil.getString("Admin",
					(String) session.getAttribute("userLanguage"))%></a>
		&gt; <a linkindex="19" href="administration#FIM"><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, Constants.KEYVAL_FIM, "FIM")%></a>
		&gt; <a href="customizeFimCustomTab"><%=LanguageUtil.getString("Manage Custom Tab",
					(String) session.getAttribute("userLanguage"))%></a>
		&gt; <span class="text_b"><%=LanguageUtil.getString("Modify Fields Position",
					(String) session.getAttribute("userLanguage"))%></span></td>
	</tr>
	<tr>
		<td width="6" colspan="3"><img
			src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1"
			height="6"></td>
	</tr>
	<%
		//String addDepartmentHtml="&nbsp;&nbsp;[&nbsp;<a href='customizeFimCustomTab' class='text_b'><u>"+LanguageUtil.getString("Customize Custom Tab Fields",(String)session.getAttribute("userLanguage"))+ "</u></a>&nbsp;]";
	%>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="100%" height="1" bgcolor="#bbbbbb"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="100%" height="10" align = "right" class = "small_txt"><span class = "urgent_fields">*</span> <%=LanguageUtil.getString("Use Drag and Drop pattern to change any Field's  Sequence") %></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	
	<tr height="20">
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="100%"  align = "center" valign = "top" class="header">&nbsp;<span style="display: none;" id="displayMsg" align="center" class="header"><%=LanguageUtil.getString("Changes made in this sequence will not be saved until the form is submitted") %>.</span><div class="urgent_field"></div></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	
	
	<tr>
    <td>&nbsp;</td>
    <td class="tb_data">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="tb_data">
            <td class="tb_data" nowrap height="25">&nbsp;<%=LanguageUtil.getString("Section Name",
					(String) session.getAttribute("userLanguage"))%>&nbsp;:&nbsp;</td>
            <td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
            <td>
            <%=htmlType.toString()%>
            </td>
            <td width="2"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
            <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
            <td width="100%" align="right">
            </td>
        </tr>
        </table>
    </td>
</tr>
	
	
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="100%" height="1" ><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="100%" height="1" ><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="100%" height="1" ><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
		<td>&nbsp;</td>

		<td>
		<table border="0" cellpadding="0" cellspacing="0" width="98%">
			<tbody>
				<tr>
					<td><img
						src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/hdr-left.gif'
						height="23" width="10"></td>
					<td class='theme_hdr_summary' nowrap="nowrap"><%=LanguageUtil.getString("Modify Fields Position") %></td>

					<td><img
						src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/hdr-right.gif'
						height="23" width="8"></td>
					<td class=text_b align=right width='100%'>&nbsp;&nbsp;</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="10" height="10"><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-left-top-fill.gif'
					width="10" height="10" /></td>
				<td class='theme_top_bg' width="100%"><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
				<td width="10" height="10"><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-right-top.gif'
					width="10" height="10" /></td>
			</tr>
			<tr>
				<td class='theme_left_bg'><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
				<td class='theme_bg'>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="4">
							<%
								if (positionInfo.size() != 0) {
							%>
							<tr class="tb_data" align="center">

								<td valign="top" colspan="2"
									align="center">
								<div class="demo" id="abhi">
								<ul id="sortable">
									<%
										boolean finalflag = true;

											Iterator idSetIterator = positionInfo.values().iterator();
											String orderNo = "";
											String displayName = "";
											String fieldId = "";
											
											while (idSetIterator.hasNext()) {
												Info currentInfo = (Info) idSetIterator.next();
												orderNo = currentInfo.getString(FieldNames.ORDER_NO);
												displayName = currentInfo
														.getString(FieldNames.DISPLAY_NAME);
												fieldId = currentInfo.getString(FieldNames.FIELD_ID);
									%>
									<li class="ui-state-default" title="<%=fieldId%>"
										id="<%=orderNo%>"><%=displayName%></li>

									<%
										}
									%>
								</ul>
								</div>
								</td>
							</tr>
							<%
								} else {
							%>

							<tr class="tb_data" align="center">
								<td class="text" valign="top" colspan="2" align="left">
								No records found.</td>
							</tr>


							<%
								}
							%>
						</table>
						</td>
					</tr>

				</table>
				</td>
				<td class='theme_right_bg'><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
			</tr>
			<tr>
				<td width="10" height="10"><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-left-bot.gif'
					width="10" height="10" /></td>
				<td class='theme_bot_bg' width='100%'><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
				<td><img
					src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-right-bot.gif'
					width="10" height="10" /></td>
			</tr>
		</table>

		</td>
		<td></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="100%" height="10" align = "right">&nbsp;</td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
		<td></td>

		<td height="30">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<%
				if (positionInfo.size() != 0) {
			%>
			<tr>
				<td><input type="button"
					value="<%=LanguageUtil.getString(
						"Save",
						(String) session.getAttribute("userLanguage"))%>"
					onClick="submitForm()" class="button" name="button"></td>
				<td width="5"><img
					src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5"
					height="1"></td>
				<td width="100%"><input type="button" class="button"
					name="cancel"
					value="<%=LanguageUtil.getString("Cancel",
						(String) session.getAttribute("userLanguage"))%>"
					onClick="javascript:history.back(-1)"></td>
				<td width="5"><img
					src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5"
					height="1"></td>
			</tr>
			<%
				}
			%>
		</table>
		</td>
		<td></td>
	</tr>

	</form>
	<form name="modifyFieldPositions" action="modifyFimCustomTabFieldPosition" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="sectionID" value=""/>
	</form>
</table>
</body>
</html>