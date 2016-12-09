<!--
 - $Header: web\jsp\fim\modifyBuilderSectionPosition.jsp
 - $Author: deevanshu.verma $
 - $Date: 2016/11/17 18:54:20 $

 -
 - ====================================================================
 - Basic Description/Data Flow of JSP
 - Section reordering in formbuilder
 -->

<%@ include file="/jsp/util/import.jsp"%>
<%@ page import="com.appnetix.app.util.information.*"%>
<%@ page import="com.appnetix.app.util.database.*"%>
<%@ page import="com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import="com.home.builderforms.*"%>
<%@ page import="com.appnetix.app.util.sqlqueries.SQLUtil" %>
<%@ include file = "builderScriptCss.jsp" %> 
<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="session"/>

<%
try {
	String moduleId = request.getParameter("moduleId"); 
	String sFimFormId = request.getParameter("fimFormId");
	SequenceMap sectionMap = new SequenceMap();
	String formName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS","FORM_NAME","BUILDER_FORM_ID",sFimFormId);
	String subModuleName = request.getParameter("subModuleName");
	if(subModuleName == null) {
		subModuleName = (String)session.getAttribute("subModuleName");
	}
	String actionTaken = null;
	actionTaken = request.getParameter(FieldNames.EVENT_TYPE);
	request.setAttribute(BuilderConstants.TABLE_ID,"2");
	request.setAttribute("form-id",sFimFormId);
	boolean flag1 = false;
	if(StringUtil.isValidNew(actionTaken) && EventType.MODIFY.equals(actionTaken)) {
		flag1 = builderForm.addModifyBuilderSectionsOrder(request);
	}
	if(StringUtil.isValidNew(sFimFormId)){
		sectionMap=builderForm.getBuilderSectionMap(request);
	}
%>
<%@ page import="com.appnetix.app.util.*"%>
<%@ page language="java" import="java.sql.*"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title") != null ? MultiTenancyUtil.getTenantContext().getAttribute("title") : "Franchise System"%></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/jquery/jquery.ui.all.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.8.2.js"></script>
<script	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.mouse.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.position.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.sortable.js"></script>
<STYLE type="text/css">
ul.fldList{margin:0px; padding:0px; list-style:none;}
ul.fldList li{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/fldList-left.png);
	background-repeat: no-repeat;
	background-position: left top;
	height: 30px; padding-left:11px;
	
}

ul.fldList li span{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/fldList-right.png);
	background-repeat: no-repeat;
	background-position: right top;
	height: 30px; line-height:26px;
	display:block; padding-right:11px;
	font-family:Arial, Helvetica, sans-serif; font-size:12px;
	text-align:center;
}
ul.fldList li.d1{ width:917px; margin:8px 3px 0px 3px; clear:both;}
ul.fldList li.d2{width:450px; margin:2px 3px 7px 3px;}

.BlText12{font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#121212;}
.rowText12{font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#045ddf;}
.mRowClr{background:url(h25-Bg.gif) repeat-x top left; border-top:1px solid #f3f4f4;}
.mRowText{height:30px; font-family:Arial, Helvetica, sans-serif; font-size:17px; color:#08498b;}

.atlRw03{background-color:#d5e4f3;}
.atlRw04{background-color:#ECF5FF;}
.atlRw04a{background-color:#fafcfe;}
.atlRw05{background-color:#B0CAEC;}
.atlRw07{background-color:#6799e1;}

.crvBox2-ltBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-left.png);
	background-repeat: no-repeat;
	background-position: left top;
	height: 30px; width:10px;
	
}
.crvBox2-ttBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-bg.png);
	background-repeat: repeat-x;
	background-position: left top;
	height: 30px;
	font-family:Arial, Helvetica, sans-serif; font-size:15px; font-weight:normal;  color:#000000; line-height:25px;
}
.crvBox2-rtBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-right.png);
	background-repeat: no-repeat;
	background-position: right top;
	height: 30px; width:40px;
	
}
.crvBox2-ttBg img{float:left; margin-right:8px;}

.crvBox2-top {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tbg.png); background-repeat: repeat-x; background-position: left top; height: 10px; width:100%;	
}
.crvBox2-botom {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-btbg.png); background-repeat: repeat-x; background-position: left bottom; height: 17px; width:100%;	
}
.crvBox2-left {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-lbg.png); background-repeat: repeat-y; background-position: left top;  width:10px;	
}
.crvBox2-right {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-rbg.png); background-repeat: repeat-y; background-position: right top;  width:16px;	
}
.crvBox2-bg{background:#ffffff;}
.fltRight{float:right;}
.fltLeft{float:left;}

.avText{font-family:Tahoma; font-weight:normal; color:#166f0a; font-size:13px; padding:0px; margin:0px;}
.clearFix{margin:0px; padding:0px; clear:both;}

/*.cm_new_button{	 
	FONT-SIZE: 12px; FONT-FAMILY: Arial, Helvetica, sans-serif;
	 border:1px solid #333333;
	 color:#ffffff;
	background:url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/buttonN_bg.jpg) left top repeat-x;
	padding:0px .5em .1em;
	width:auto;
	overflow:visible;
	height:26px;
	 
}*/
.ActionMenuFontColor{
COLOR: #0E3892; TEXT-DECORATION: none

}

ul.flexmenu{ font: normal 12px Arial, Helvetica, sans-serif; margin: 0; padding: 0; position: absolute; left: 0; top: 0; list-style-type: none;  border-bottom-width: 0; visibility: hidden;  display: none; width: 160px;}
ul.flexmenu li{position: relative;}
ul.flexmenu li a{display: block; width: 160px; color: #000000; background-color:#ffffff; border-bottom:1px dotted #e8e8e8; text-decoration: none; padding: 4px 5px; font-family:Arial, Helvetica, sans-serif; font-size:12px;
}
ul.flexmenu li a.t32{border-bottom:none; text-decoration: none; padding: 0px; font-family:Arial, Helvetica, sans-serif; font-size:11px; background:url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/actionMenu_arrow.gif) no-repeat 4px 6px; padding:3px 0 3px 15px; }
ul.flexmenu li a.t32:hover{background-color:#e8f0fa;}

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

#sortable {
}

#sortable li {
}

</STYLE>
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

function hidemessage1() {
	document.getElementById("displayMsgOrder").style.display="none";
}

</script>
<script>
<%if(flag1) {%>
setTimeout("hidemessage1()",2000);
<% }  %>
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/position/demos.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/StringUtil.js"></script>
<script language="javascript">
	function submitForm() {
		var f = document.changeForm;
<%
		for (int i=0; i<sectionMap.size(); i++) {%>
 			document.changeForm.<%=BuilderFormFieldNames.SECTION_NAME%>_<%=i%>.value =  $('#sortable li').get(<%=i%>).lang; 
			document.changeForm.<%=BuilderFormFieldNames.SECTION_NAME%>_<%=i%>_<%=BuilderFormFieldNames.SECTION%>.value= <%=i%>;
<%}%>
		f.action = "modifyBuilderSectionPosition";
		f.submit();

	}

	 function historyback() {
			var f = document.changeForm;
			f.action = "builderWebForm";
			f.submit();
		 
	 }

</script>
<%
	String userTheme = (String) session.getAttribute("userTheme");
%>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="12"></td>
		<td height="19" valign="bottom" class="text"><a href="administration"><u><%=LanguageUtil.getString("Admin", (String) session.getAttribute("userLanguage"))%></u></a>

		<%if("2".equals(moduleId)) { %>
		&gt; <a linkindex="19" href="administration#Franchise_Sales"><u><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, com.appnetix.app.util.Constants.KEYVAL_FS, "Franchise Sales")%></u></a>
		&gt; <a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_FS%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString("Manage Form Generator",(String) session.getAttribute("userLanguage"))%></u></a>
		&gt; <a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
		<%}else if("4".equals(moduleId) || "5".equals(moduleId) || "6".equals(moduleId) || "7".equals(moduleId)) { %>
			&gt; <a linkindex="19" href="administration#Contact_Manager"><u><%=LanguageUtil.getString("Contact Manager",(String)request.getSession().getAttribute("userLanguage"),Constants.KEYVAL_CM)%></u></a>
			<%}else if("8".equals(moduleId)) {%>
			&gt; <a linkindex="19" href="administration#Scheduler"><u><%=LanguageUtil.getString(ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER, (String) session.getAttribute("userLanguage"))%></u></a>
			<%} else if("9".equals(moduleId)) { %>
				&gt; <a linkindex="19" href="administration#Site_Clearance"><u><%=LanguageUtil.getString(ModuleUtil.MODULE_DISPLAY.DISPLAY_SITE, (String) session.getAttribute("userLanguage"))%></u></a>
				&nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
			<%} else { %>
		&gt; <a linkindex="19" href="administration#FIM"><u><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, Constants.KEYVAL_FIM, "FIM")%></u></a>
		<%} %>
		&gt; <span class="text_b"><%=LanguageUtil.getString("Modify Sections Position", (String) session.getAttribute("userLanguage"))%></span></td>
	</tr>
	<tr>
		<td width="6" colspan="3"><img
			src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1"
			height="6"></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="100%" height="1" bgcolor="#bbbbbb"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="100%" height="10" align = "right" class = "bText11gr"><span class = "urgent_fields">*</span> <%=LanguageUtil.getString("Use Drag and Drop pattern to change any Section's Sequence")%></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	</tr>
	
	<tr height="10">
	</tr>
	<tr>
        <td colspan="3" align="center">
			  	<table>
					<tr id="displayMsg" style="display:none;">
						<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;">
						<%=LanguageUtil.getString("Changes made in this sequence will not be saved until the form is submitted") %>.
						</td>
					</tr>
				</table>
		</td>
	</tr>
	<tr>
        <td colspan="3" align="center">
			  	<table>
			  	<%if(flag1) { %>
					<tr id="displayMsgOrder" style="display:block;;">
				<%} else { %>
					<tr id="displayMsgOrder" style="display:none;">
				<%} %>
						<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;">
						<%=LanguageUtil.getString("Changes made in this sequence have been saved") %>.
						</td>
					</tr>
				</table>
		</td>
	</tr>	
	

	<form method="post" name="changeForm" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="moduleId" value="<%=moduleId%>"> 
	<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=EventType.MODIFY%>">
	<input type="hidden" name="fimFormId" value=<%=sFimFormId%>>
	<input type="hidden" name="subModuleName" value=<%=subModuleName%>>
	<input type="hidden" name="formNames" value=<%=sFimFormId%>>
	<input type="hidden" name="fimFormSize" value=<%=sectionMap.size()%>>
	<%
		for (int i = 0; i < sectionMap.size(); i++) { 
	%> 
	<input type="hidden" name="<%=BuilderFormFieldNames.SECTION_NAME%>_<%=i%>">
	<input type="hidden" name="<%=BuilderFormFieldNames.SECTION_NAME%>_<%=i%>_<%=BuilderFormFieldNames.SECTION%>">
	<%
		}
	%> <input type="hidden" name="<%=FieldNames.ERROR_PAGE%>" value="errorPage"> <input type="hidden" name="<%=FieldNames.RETURN_PAGE%>" value="modifyBuilderSectionPosition">
		<input type="hidden" action="save"/>
	
	<tr>
        <td class="text" width="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="8" width="10"></td>
        <td width="100%">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                <td colspan="2" width="100%"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr >
               <td class="thead hText16black"></td>
                    <td width="100%" nowrap="nowrap" class="thead hText16black"><%=LanguageUtil.getString("Available Sections")%> </td>
                    <td class="thead hText16black"></td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
                    <tr>
                      <td width="10" valign="top"></td>
                      <td width="475" class=""></td>
                      <td width="15" align="right" valign="top"></td>
                    </tr>
                    <tr height="10"></tr>
                    <%
						if (sectionMap.size() != 0) {
					%>
                    <tr>
                      <td></td>
                      <td style="width:930px;">
                      <div id="abhi" style="padding:0px 4px 4px 2px;">
                          <ul class="fldList" id="sortable">
                          <%
										boolean finalflag = true;

										Iterator idSetIterator = sectionMap.keys().iterator();
										String sectionNo = "";
										String displayName = "";
										String displayTitle = null;
										String sectionName = "";
										int fldSpan = 2;
										while (idSetIterator.hasNext()) {
											Object key = idSetIterator.next();
											Info currentInfo = (Info) sectionMap.get(key);
											sectionNo = currentInfo.getString(BuilderFormFieldNames.SECTION);
											displayName = currentInfo.getString(BuilderFormFieldNames.SECTION_VALUE);
											displayTitle = displayName;
											sectionName = currentInfo.getString(BuilderFormFieldNames.SECTION_NAME);
											
											if(displayName!=null && displayName.length()>40)
											{
												displayName = displayName.substring(0,40)+"...";
											}
											if(fldSpan == 2) {
												%>
					                            <li class="d1" lang="<%=sectionName%>" title="<%=PortalUtils.filterValue(displayTitle)%>" id="<%=sectionNo%>"><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li>
												<%
											} else {
												%>
					                            <li class="d2 fltLeft" lang="<%=sectionName%>" title="<%=PortalUtils.filterValue(displayTitle)%>" id="<%=sectionNo%>"><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li>
												<%
											}
										}
									%>
                          </ul>
                        <p class="clearFix"></p>
                      </div></td>
                      <td></td>
                    </tr>
                    <%
						} else {
					%>
					<tr>
                      <td class="noRecord pd" style="width:930px;"><%=LanguageUtil.getString("No records found.")%>
                   
                      </td>
                     
                    </tr>
					<%
						}
					%>
                    <tr>
                      <td align="left" valign="bottom"></td>
                      <td class=""></td>
                      <td align="right" valign="bottom"></td>
                    </tr>
                  </table>
                  </td>
                </tr>
                </table>
             </td>
        <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="8" width="10"></td>
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
				if (sectionMap.size() != 0) {
			%>
			<tr>
				<td><input type="button" value="<%=LanguageUtil.getString("Save", (String) session.getAttribute("userLanguage"))%>" onClick="submitForm()" class="cm_new_button" name="button"></td>
				<td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
				<td width="100%"><input type="button" class="cm_new_button" name="cancel" value="<%=LanguageUtil.getString("Back", (String) session.getAttribute("userLanguage"))%>"
					onClick="javascript:historyback()"></td>
				<td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
			</tr>
			<%
				}
			%>
		</table>
		</td>
		<td></td>
	</tr>

	</form>
</table>
</body>
</html>
<% } catch(Exception e) {
e.printStackTrace();
} %>
