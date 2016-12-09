<%@ include file="/jsp/util/import.jsp"%>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import="com.home.builderforms.BuilderFormFieldNames"%>
<%@ page import="com.home.builderforms.BuilderConstants"%>
<%@ page import="com.appnetix.app.struts.EventHandler.EventType"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import="com.appnetix.app.util.database.Field"%>
<%@ page import="com.appnetix.app.util.database.FieldMappings"%>
<%@ include file = "builderScriptCss.jsp" %> 

<%--
 - $Header: modifyFimBuilderFieldPosition.jsp
 - $Author: Abhishek Gupta
 - $Version:  $1.1
 - $Date: 09/01/2012
 P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
--%>

<jsp:useBean id="builderForm"	class="com.home.builderforms.BuilderFormWebImpl" scope="session"/>
<jsp:useBean id="builderWebForm"	class="com.home.builderforms.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>

<%
try {
	String moduleId = request.getParameter("moduleId"); //P_FS_Enh_BuilderForm
	String moduleName = ""; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
	String sFimFormId = request.getParameter("fimFormId");
	String formName = "";
	//P_B_65450 STARTS
	if("40".equals(sFimFormId)){
		formName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS","FORM_NAME","BUILDER_FORM_ID","1");
	}else{
		formName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS","FORM_NAME","BUILDER_FORM_ID",sFimFormId);
	}
	//P_B_65450 ENDS 
	String subModuleName = request.getParameter("subModuleName");
	if(subModuleName == null) {
		subModuleName = (String)session.getAttribute("subModuleName");
	}
	/* if("40".equals(sFimFormId)) {
		sFimFormId = "1";
	} */
	SequenceMap positionInfo = new SequenceMap();
	String setup = "false";
	boolean flag1 = false;
	String actionTaken = null;
	actionTaken = request.getParameter(FieldNames.EVENT_TYPE);
	request.setAttribute(BuilderConstants.TABLE_ID,"2");
	request.setAttribute("form-id",sFimFormId);
	request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");

	if(StringUtil.isValidNew(actionTaken) && EventType.MODIFY.equals(actionTaken)) {
		flag1 = builderForm.addModifyBuilderFieldsOrder(request);
	}

	if (request.getParameter("setup") != null) // for setup wizard
		setup = request.getParameter("setup");
	
	String headerVal = "";
	String tableAnchor = "";
	String mapSection = "";
	FieldMappings fMapping = null;
	Field[] arrDeactive=null;
	if(request.getAttribute("POSITIONINFO") == null) {
		request.setAttribute(BuilderConstants.TABLE_ID,"2");
		request.setAttribute("form-id",sFimFormId);
		request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
		SequenceMap ppMap = builderForm.getBuilderFieldsMap(request);
		System.out.println("ppMap in modify position -- " + ppMap);
		SequenceMap ppMap1 = (SequenceMap)ppMap.get(0);
		//Martin-20160728-018 starts
		if(ppMap1!=null){
			headerVal = (String)ppMap1.get("mapValue");
			tableAnchor = (String)ppMap1.get("tableAnchor");
			mapSection  = (String)ppMap1.get("mapSection");
			Iterator sizeIter = ppMap1.keys().iterator();
			while (sizeIter.hasNext()) {
				String key = (String)sizeIter.next();
			    Object obj = ppMap1.get(key);
			    if(obj instanceof Info) {
			    	if("hotActivity".equals((String)((Info)obj).getObject(BuilderFormFieldNames.FIELD_NAME)) && !ModuleUtil.landingPageImplemented()){
						continue;
					}
			    	positionInfo.put(key, (Info)obj);
			   		 } else {
			    	continue;
			    	}
				}
			}else{
				tableAnchor = (String)request.getAttribute("tableAnchor");
				mapSection  = (String)request.getAttribute("mapSection");
			}//Martin-20160728-018 ends
	}
	
	System.out.println("table----->>"+tableAnchor);
	if(StringUtil.isValidNew(tableAnchor)) {//Martin-20160728-018
		fMapping = builderForm.getBuilderFieldsMapping(tableAnchor);
		arrDeactive = fMapping.getDeactiveFieldMap(mapSection);
	}
	request.setAttribute("setup", setup);

	String classStyle = "";
	String setup1 = (String) request.getParameter("setup");
	// removing info objects from request
	request.removeAttribute("POSITIONINFO");
%>

<%@ page import="com.appnetix.app.util.information.Info"%>
<%@ page language="java" import="com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%@ page import="java.util.Iterator"%>
<html>
<head>
<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title") != null ? MultiTenancyUtil.getTenantContext().getAttribute("title") : "Franchise System"%></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/jquery/jquery.ui.all.css">
<%--PW_B_26828 Start--%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.8.2.js"></script>
<script	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.mouse.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.position.js"></script>
<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.ui.sortable.js"></script>
<%--PW_B_26828 Ends--%>
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
function selectFimForm() {
    var formName = document.builderForm ;
	formName.submit();
}

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
<%
		for (int i=0; i<positionInfo.size(); i++) {//ENH_MODULE_CUSTOM_TABS //P_B_47578%>
 			document.changeForm.<%=FieldNames.FIELD_NAME%>_<%=i%>.value =  $('#sortable li').get(<%=i%>).lang; <%--ZC_CM_B_41673 --%>
			document.changeForm.<%=FieldNames.FIELD_NAME%>_<%=i%>_<%=FieldNames.ORDER_NO%>.value= <%=i%>;
<%}%>
		f.target = ""; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
		f.action = "modifyFimBuilderFieldPosition";
		f.submit();

	}
	function goCancel() {
		JavaScript: location.href = 'administration?cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>'
	 }

	 function historyback() {
		 
	  
			var f = document.changeForm;
			f.action = "builderWebForm";//BB_Naming_Convention
			f.target = ""; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
			if("centerInfoDisplay"=='<%=tableAnchor%>'){
				 f.fimFormId.value = "1";
				 f.formNames.value = "1";
				 
			  }
			f.submit();
	 
	 }

	//This javascript function is used to display the positions of fields before saving the actual xml.
	//BB-20150203-259 (Add Preview Button in Modify Field Management Section) starts
	function previewForm() {
		var f = document.changeForm;
		<% for (int i=0; i<positionInfo.size(); i++) { %>
			document.changeForm.<%=FieldNames.FIELD_NAME%>_<%=i%>.value =  $('#sortable li').get(<%=i%>).lang; <%--ZC_CM_B_41673 --%>
			document.changeForm.<%=FieldNames.FIELD_NAME%>_<%=i%>_<%=FieldNames.ORDER_NO%>.value= <%=i%>;
		<% } %>
		
		
		window.open("","previewWindow","width=1000,height=640,toolbar=0,scrollbars=yes,resizable=yes");
		f.target = "previewWindow";
		
		f.action = "previewBuilderFieldPosition?fromSectionPreview=yes&tableAnchor=<%=tableAnchor%>&isCustomTab=<%=request.getParameter("isCustomTab")%>";
		f.submit();
	 }
	//BB-20150203-259 (Add Preview Button in Modify Field Management Section) ends
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

		<%if("2".equals(moduleId)) { //P_FS_Enh_BuilderForm starts
			moduleName = ModuleUtil.MODULE_NAME.NAME_FS; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
		%>
		&gt; <a linkindex="19" href="administration#Franchise_Sales"><u><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, com.appnetix.app.util.Constants.KEYVAL_FS, "Franchise Sales")%></u></a>
            <% if("captivate".equals(subModuleName)) { //PP_changes starts %>
            &nbsp;&gt;&nbsp;<a href="franchiseGrantingActivity"><u><%=LanguageUtil.getString("Manage Candidate Portal",(String) session.getAttribute("userLanguage"))%></u></a>
            &nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_FS%>&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString("Manage Virtual Brochure Form",(String) session.getAttribute("userLanguage"))%></u></a>
            <%}else{ %>
            &nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_FS%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString("Manage Form Generator",(String) session.getAttribute("userLanguage"))%></u></a>
            <%} //PP_changes ends%>
            &nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
            <%}else if("4".equals(moduleId) || "5".equals(moduleId) || "6".equals(moduleId) || "7".equals(moduleId)) { //P_FS_Enh_BuilderForm starts%>
			&gt; <a linkindex="19" href="administration#Contact_Manager"><u><%=LanguageUtil.getString("Contact Manager",(String)request.getSession().getAttribute("userLanguage"),Constants.KEYVAL_CM)%></u></a>
			&nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_CM%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString("Manage Form Generator",(String) session.getAttribute("userLanguage"))%></u></a>
		&nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
			<%--P_SCH_ENH_008 Starts --%>
			<%}else if("8".equals(moduleId)) {%>
			&gt; <a linkindex="19" href="administration#Scheduler"><u><%=LanguageUtil.getString(ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER, (String) session.getAttribute("userLanguage"))%></u></a>
			<%--P_SCH_ENH_008 Ends --%>
			&nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_SCHEDULER%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString("Manage "+ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER+" Form",(String) session.getAttribute("userLanguage"))%></u></a>
		&nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
			<%} else if("9".equals(moduleId)) { %>
				&gt; <a linkindex="19" href="administration#Site_Clearance"><u><%=LanguageUtil.getString(ModuleUtil.MODULE_DISPLAY.DISPLAY_SITE, (String) session.getAttribute("userLanguage"))%></u></a>
				&nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=sFimFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
			<%} else { 
				moduleName = ModuleUtil.MODULE_NAME.NAME_FIM; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
	            String newFormId = sFimFormId;
	            if("40".equals(sFimFormId)) {
	            	newFormId = "1";
	            }
			%>
		&gt; <a linkindex="19" href="administration#FIM"><u><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(request, Constants.KEYVAL_FIM, "FIM")%></u></a>
		&nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=ModuleUtil.MODULE_NAME.NAME_FIM%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString("Manage Form Generator",(String) session.getAttribute("userLanguage"))%></u></a>
		&nbsp;&gt;&nbsp;<a href="builderWebForm?formNames=<%=newFormId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></u></a>
		<%} //P_FS_Enh_BuilderForm ends%>
		
		<%if("40".equals(sFimFormId)){ %>
			&gt; <span class="text_b"><%=LanguageUtil.getString("Modify Display Fields Position", (String) session.getAttribute("userLanguage"))%></span></td>
		<%} else { %>
			&gt; <span class="text_b"><%=LanguageUtil.getString("Modify Fields Position", (String) session.getAttribute("userLanguage"))%></span></td>
		<%} %>
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
	<%--<form name="builderForm" action="modifyFimBuilderFieldPosition" method="post">
	
	<tr>
    <td>&nbsp;</td>
    <td height="25" class="tb_hdr_b">&nbsp;<%=LanguageUtil.getString("Modify Fields Position",(String)session.getAttribute("userLanguage"))%></td>
    <td>&nbsp;</td>
	</tr>
	<%
	SequenceMap builderFormMap = builderForm.getBuilderForm(request);
	if (builderFormMap == null) {
	%>
	<tr class="tb_data">
	    <td>&nbsp;</td>
	    <td class="urgent_fields">&nbsp;<%=LanguageUtil.getString("Data could not be retrieved",(String)session.getAttribute("userLanguage"))%>.<%=LanguageUtil.getString(" Please try again later",(String)session.getAttribute("userLanguage"))%>.</td>
	    <td>&nbsp;</td>
	</tr>
	<%
	} else {
	%>
	<tr>
	    <td>&nbsp;</td>
	    <td class="tb_data">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr class="tb_data">
	            <td class="tb_data" nowrap height="25">&nbsp;<%=LanguageUtil.getString("Form Name",(String)session.getAttribute("userLanguage"))%>:</td>
	            <td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
	            <td>
	              <div id="select" style:display="none">
	                <select name="fimFormId"  id="fimFormId" class="dropdown_list" onchange ="javascript:selectFimForm()">
	                    <option value="null"><%=LanguageUtil.getString("Select",(String)session.getAttribute("userLanguage"))%></option>
	   
	<%
	    Iterator builderFormIter	= builderFormMap.values().iterator();
	    while (builderFormIter.hasNext()) {
	        Info info = (Info) builderFormIter.next();
	        Integer gFormId = Integer.parseInt(info.getString(BuilderFormFieldNames.BUILDER_FORM_ID));
	        String sFormName = info.get(BuilderFormFieldNames.FORM_NAME);
	%>
	            <option value="<%=gFormId%>"<%if(gFormId.toString().equals(sFimFormId)){%>     selected<%}%>><%=LanguageUtil.getString(sFormName,(String)session.getAttribute("userLanguage"))%></option> </div>
	<%
	    }
	%>
	            </td>
	            <td width="2"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
	            <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
	            <td width="100%" align="right">
	                <!--<a href="javascript:void(0);" onClick="window.open('<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/jsp/builderform/help/fim_cust_help.htm','HelpFile','scrollbars=yes,menubar=no,location=no,toolbar=no,resizable=yes,status=no,width=640,height=550,top=100,left=100')"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/jsp/CustomizationForm/help/images/help.gif" border="0">&nbsp;<u>Help</u></a>-->
	            </td>
	        </tr>
	        </table>
	    </td>
	</tr>
	<%
	}%>
	</form>--%>
	<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="100%" height="10" align = "right" class = "bText11gr"><span class = "urgent_fields">*</span> <%=LanguageUtil.getString("Use Drag and Drop pattern to change any Field's Sequence")%></td>
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
	<input type="hidden" name="moduleId" value="<%=moduleId%>"> <!-- P_FS_Enh_BuilderForm -->
	<input type="hidden" name="moduleName" value="<%=moduleName%>"> <!-- BB-20150203-259 (Add Preview Button in Modify Field Management Section) -->
	<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=EventType.MODIFY%>">
	<input type="hidden" name="fimFormId" value=<%=sFimFormId%>>
    <input type="hidden" name="subModuleName" value=<%=subModuleName%>><!-- PP_changes -->
	<input type="hidden" name="section" value=<%=mapSection%>>
	<input type="hidden" name="formNames" value=<%=sFimFormId%>>
	<input type="hidden" name="fimFormSize" value=<%=(((arrDeactive!=null)?arrDeactive.length:0)+positionInfo.size())%>><%--ENH_MODULE_CUSTOM_TABS--%>
	<input type="hidden" name="sectionName" value="<%=request.getParameter("sectionName")%>">
	<%
		for (int i = 0; i < ((arrDeactive!=null)?arrDeactive.length:0)+positionInfo.size(); i++) { //ENH_MODULE_CUSTOM_TABS
	%> 
	<input type="hidden" name="<%=FieldNames.FIELD_NAME%>_<%=i%>">
	<input type="hidden" name="<%=FieldNames.FIELD_NAME%>_<%=i%>_<%=FieldNames.ORDER_NO%>">
	<%
		}
	%> <input type="hidden" name="<%=FieldNames.ERROR_PAGE%>" value="errorPage"> <input type="hidden" name="<%=FieldNames.RETURN_PAGE%>" value="modifyFimBuilderFieldPosition">
		<input type="hidden" action="save"/>
	
	<tr>
        <td class="text" width="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="8" width="10"></td>
        <td width="100%">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                <td colspan="2" width="100%"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr ><!-- P_CM_B_41234 -->
               <td class="thead hText16black"></td>
                    <td width="100%" nowrap="nowrap" class="thead hText16black"><%=LanguageUtil.getString("Active Fields")%> </td>
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
						if (positionInfo.size() != 0) {
					%>
                    <tr>
                      <td></td>
                      <td style="width:930px;">
                      <div id="abhi" style="padding:0px 4px 4px 2px;">
                          <ul class="fldList" id="sortable">
                          <%
										boolean finalflag = true;
										Iterator idSetIterator = positionInfo.keys().iterator();
										String orderNo = "";
										String displayName = "";
										String displayTitle = null;//ENH_MODULE_CUSTOM_TABS
										String fieldName = "";
										int fldSpan = 1;
										while (idSetIterator.hasNext()) {
											Object key = idSetIterator.next();
											Info currentInfo = (Info) positionInfo.get(key);
											orderNo = currentInfo.getString(FieldNames.ORDER_NO);
											displayName = currentInfo.getString(FieldNames.DISPLAY_NAME);
											displayTitle = displayName;//ENH_MODULE_CUSTOM_TABS
											fieldName = currentInfo.getString(FieldNames.FIELD_NAME);
											fldSpan = Integer.parseInt(currentInfo.get("colspan"));
											//ENH_MODULE_CUSTOM_TABS starts
											if(displayName!=null && displayName.length()>40)
											{
												displayName = displayName.substring(0,40)+"...";
											}
											//P_CM_B_38405 :starts
											if (displayName.indexOf("$") != -1) {
												displayName=displayName.replace("$", MultiTenancyUtil.getTenantConstants().USER_CURRENCY);
											}
											//P_CM_B_38405 :ends
											//ENH_MODULE_CUSTOM_TABS ends
											if(fldSpan == 2) {
												%>
					                            <li class="d1" lang="<%=fieldName%>" title="<%=LanguageUtil.getString(PortalUtils.filterValue(displayTitle))%>" id="<%=orderNo%>"><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li><%--ENH_MODULE_CUSTOM_TABS--%>
												<%
											} else {
												%>
					                            <li class="d2 fltLeft" lang="<%=fieldName%>" title="<%=LanguageUtil.getString(PortalUtils.filterValue(displayTitle))%>" id="<%=orderNo%>"><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li><%--ENH_MODULE_CUSTOM_TABS--%>
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
                     <!-- P_CM_B_41234 -->
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
    
    <%if(!("2".equals(moduleId) && "preferredLocations".equals(request.getParameter("sectionName")))) { %>
    <tr>
        <td class="text" width="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="8" width="10"></td>
        <td width="100%">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr >
                <td colspan="2" width="100%"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr ><!-- P_CM_B_41234 -->
                    <td class="thead hText16black"></td>
                    <td width="100%" class="thead hText16black" nowrap="nowrap"><%=LanguageUtil.getString("De-Active Fields")%> </td>
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
						if (arrDeactive != null) {
					%>
                    <tr>
                      <td class=""></td>
                      <td class="" style="width:930px;">
                      <div id="abhi1" style="padding:0px 4px 4px 2px;">
                          <ul class="fldList">
                        <%
                        	int fldSpan = 1;
                        	//ENH_MODULE_CUSTOM_TABS starts
                        	String displayTitle = null;
                        	String displayName = null;
                        	//ENH_MODULE_CUSTOM_TABS ends
                            for(int i=0;i< arrDeactive.length;i++) {
                        	//for(Field fld:arrDeactive) {
                        		Field fld = arrDeactive[i];                    	
								//Field fld = fMapping.getField(s);	
								fldSpan = Integer.parseInt(fld.getColspan());
								//ENH_MODULE_CUSTOM_TABS starts
								displayName = fld.getDisplayName();
								if(displayName!=null && displayName.length()>40)
								{
									displayName = displayName.substring(0,40)+"...";
								}
								displayTitle = displayName;
								//ENH_MODULE_CUSTOM_TABS ends
								if(fldSpan == 2) {
								//ENH_MODULE_CUSTOM_TABS starts
						%>
									<li class="d1" lang="<%=fld.getFieldName()%>" title="<%=LanguageUtil.getString(PortalUtils.filterValue(displayTitle))%>" ><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li>
						<% } else { %>
									<li class="d2 fltLeft" lang="<%=fld.getFieldName()%>" title="<%=LanguageUtil.getString(PortalUtils.filterValue(displayTitle))%>" ><span><strong><%=LanguageUtil.getString(displayName)%></strong></span></li>
						<% }
							//ENH_MODULE_CUSTOM_TABS ends
						 } %>			
                          </ul>
                        <p class="clearFix"></p>
                      </div></td>
                      <td class=""></td>
                    </tr>
                    <%
						} else {
					%><!-- P_CM_B_41234 -->
					<tr>
                   
                      <td class="noRecord pd" style="width:930px;"><%=LanguageUtil.getString("No records found.")%>
                      
                      </td>
                  
                    </tr>
					<%
						}
					%>
                    <tr>
                      <td align="left" valign="bottom"></td>
                      <td></td>
                      <td align="right" valign="bottom"></td>
                    </tr>
                  </table>
                  </td>
                </tr>
                </table>
             </td>
        <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="8" width="10"></td>
    </tr>
    <%} %>
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
				<td><input type="button" value="<%=LanguageUtil.getString("Save", (String) session.getAttribute("userLanguage"))%>" onClick="submitForm()" class="cm_new_button" name="button"></td>
				<%if(!"40".equals(sFimFormId)) { %>
					<td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
					<td><input type="button" value="<%=LanguageUtil.getString("Preview", (String) session.getAttribute("userLanguage"))%>" onClick="previewForm()" class="cm_new_button" name="previewButton"></td>
				<%} %>
				
				<td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
				<td width="100%"><input type="button" class="cm_new_button" name="cancel" value="<%=LanguageUtil.getString("Back", (String) session.getAttribute("userLanguage"))%>"onClick="javascript:historyback()"></td>
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
	<form name="modifyFieldPositions" action="modifyFimBuilderFieldPosition" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="sectionID" value=""/>
	</form>
</table>
</body>
</html>
<% } catch(Exception e) {
e.printStackTrace();
} %>
