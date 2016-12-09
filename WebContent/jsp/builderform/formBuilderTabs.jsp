<%--
Jsp added for ENH_MODULE_CUSTOM_TABS to display  the currently configured tabs details.
P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
P_B_CM_070714  7th July 2014            Swati Garg              GUI issue
P_B_CM_41810   18 July 2014             Rashmi Shakya           Add New Tab link 
--%>
<%@ page import = "java.util.*"%>
<%@ page import = "com.appnetix.app.util.*"%>

<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import="com.appnetix.app.util.tagutils.*" %>
<%@ page import="com.appnetix.app.portal.role.*" %>
<%@ page import="com.home.builderforms.BuilderFormFieldNames" %>
<%@ page import="com.home.builderforms.BuilderConstants" %>
<%@ page import="com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil" %>
<%@ taglib uri="/artags" prefix="ar" %>
<%@ include file = "builderScriptCss.jsp" %> 

<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="session"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/site.css" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/popNew.js"></script>
<link  media="screen" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/audit/colorbox.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.8.2.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<style>
#cboxClose{position:absolute; top:0; right:0; background:url(../images/f-catalyst/controls.png) -25px 0px no-repeat; width:25px; height:25px; text-indent:-9999px;}
.transparent_class {
  /* IE 8 */
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=40)";

  /* IE 5-7 */
  filter: alpha(opacity=40);

  /* Netscape */
  -moz-opacity: 0.4;

  /* Safari 1.x */
  -khtml-opacity: 0.4;

  /* Good browsers */
  opacity: 0.4;
}
</style>
<%
try 
{
	
	UserRoleMap userRoleMap	= (UserRoleMap)session.getAttribute("userRoleMap");
	SequenceMap tabGeneratorMap = (SequenceMap)session.getAttribute("tabGeneratorMap");
	String sTabModuleName = request.getParameter(FieldNames.MODULE);
	int tabCount=(Integer)request.getAttribute("tabCount");
	String actionButtons="";
	if(sTabModuleName == null)
	{
		sTabModuleName = (String)session.getAttribute("sTabModuleName");
	}
	else
	{
		session.setAttribute("sTabModuleName", sTabModuleName);
	}
	String subModuleName = request.getParameter("subModuleName"); //PP_changes starts
	if("mu".equals(sTabModuleName) || "area".equals(sTabModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
		subModuleName=sTabModuleName;
		sTabModuleName="fim";
	}//P_Enh_Mu-Entity_FormGenerator ends
	String displayName = "Tab Names";
	String tabHeaderName = "Tab Name";
	String addDisplayName = "Add New Tab";
    String message="Tab";
	if("captivate".equals(subModuleName)) {
		displayName = "Virtual Brochure Form Names";
		tabHeaderName = "Virtual Brochure Form Name";
		addDisplayName = "Add New Form";
        message="Form";
	}
	
	boolean isCM=false;
	if("cm".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName) || "lead".equals(sTabModuleName)){
		isCM=true;
	}
	//P_Enh_Mu-Entity_FormGenerator starts
	boolean isFim=false;
	if("fim".equals(sTabModuleName) || "mu".equals(sTabModuleName) || "entity".equals(sTabModuleName) || "area".equals(sTabModuleName)){
		isFim=true;
		request.setAttribute("fim_subTabsFG","yes");
	}
	//P_Enh_Mu-Entity_FormGenerator  ends
	//PP_changes ends
	 if(( isCM && userRoleMap.isPrivilegeIDInMap("429"))||("fs".equals(sTabModuleName)&&userRoleMap.isPrivilegeIDInMap("424"))||(isFim && userRoleMap.isPrivilegeIDInMap("428")) ||("scheduler".equals(sTabModuleName)&&userRoleMap.isPrivilegeIDInMap("2922"))){
	//String actionButtons =  "&nbsp;&nbsp;<a rel='subcontent' onclick=\"addFormTab('"+sTabModuleName+"', 'create')\" id='addsectionlink' href='javascript:void(0)'><span>Add New Tab</span></a>&nbsp;&nbsp;";//P_B_CM_41810 
	 actionButtons ="&nbsp;<input class=\"cm_new_button_link\" type=\"button\" onclick=\"addFormTab('"+sTabModuleName+"', 'create')\" id='addsectionlink' value='"+LanguageUtil.getString(addDisplayName)+"'>&nbsp;";//P_Demo_Changes //PP_changes
	 }
	 if(("fs".equals(sTabModuleName)&&userRoleMap.isPrivilegeIDInMap("424"))||("fim".equals(sTabModuleName)) || "mu".equals(sTabModuleName) || "entity".equals(sTabModuleName) || "area".equals(sTabModuleName))//BB-20150203-259 (Tab Re-ordering changes) starts
		 actionButtons=actionButtons+"<input class=\"cm_new_button_link\" type=\"button\" onclick=\"modifyTabPosition('"+sTabModuleName+"')\" id='taborder' value='"+LanguageUtil.getString("Modify Tabs Position")+"'>&nbsp;";//BB-20150203-259 (Tab Re-ordering changes) ends
	String moduleId = ""+ModuleUtil.MODULE_ID.ID_FIM;
	String moduleName = ModuleUtil.MODULE_NAME.NAME_FIM;
	String moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	String moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	String href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#FIM";
	SequenceMap builderFormMap = (SequenceMap)request.getAttribute("builderFormMap");
	
	if("fim".equals(sTabModuleName))
	{
	    request.setAttribute(BuilderConstants.TABLE_ID,"1");
	    
	}
	else if("cm".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_CM;
		moduleName = ModuleUtil.MODULE_NAME.NAME_CM;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	    request.setAttribute(BuilderConstants.TABLE_ID,"11");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Contact_Manager";
	}
	else if("fs".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_FS;
		moduleName = ModuleUtil.MODULE_NAME.NAME_FS;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FS;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FS;
	    request.setAttribute(BuilderConstants.TABLE_ID,"10");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Franchise_Sales";
	}else if("account".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_ACCOUNT;
		moduleName = ModuleUtil.MODULE_NAME.NAME_ACCOUNT;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	    request.setAttribute(BuilderConstants.TABLE_ID,"16");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Contact_Manager";
	}else if("opportunity".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_OPPORTUNITY;
		moduleName = ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	    request.setAttribute(BuilderConstants.TABLE_ID,"17");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Contact_Manager";
	}else if("lead".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_LEAD;
		moduleName = ModuleUtil.MODULE_NAME.NAME_LEAD;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	    request.setAttribute(BuilderConstants.TABLE_ID,"19");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Contact_Manager";
	}
	//P_SCH_ENH_008 Starts
	else if("scheduler".equals(sTabModuleName))
	{
		moduleId = ""+ModuleUtil.MODULE_ID.ID_SCHEDULER;
		moduleName = ModuleUtil.MODULE_NAME.NAME_SCHEDULER;
		moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER;
		moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER;
	    request.setAttribute(BuilderConstants.TABLE_ID,"13");
	    href = "administration?cft="+java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))+"#Scheduler";
	}
	//P_SCH_ENH_008 Ends
	
	String tabDisplayName = request.getParameter(BuilderFormFieldNames.TAB_DISPLAY_NAME);
	int returnValue = -1;
	if("active".equals(request.getParameter(BuilderFormFieldNames.TAB_ACTION)))
	{
		returnValue = 1;
	}
	else if("deactive".equals(request.getParameter(BuilderFormFieldNames.TAB_ACTION)))
	{
		returnValue = 2;
	}
	else if("delete".equals(request.getParameter(BuilderFormFieldNames.TAB_ACTION)))
	{
		returnValue = 3;
	}
	boolean isModified = ((request.getAttribute("isModified")!=null) && ((Boolean)request.getAttribute("isModified")).booleanValue()); 
	
	String isTab=request.getParameter("isTab");
	String instructionDisplay="none";
	String detailDisplay="block";
	if(request.getParameter("fromWhere")!=null && "admin".equals(request.getParameter("fromWhere")) && !StringUtil.isValidNew(isTab))
	{
		instructionDisplay="block";	
		detailDisplay="none";
	}	
	
		%>
<script type="text/javascript">
	function addFormTab(module, action) 
	{
		var url;
		url = "addBuilderTab?<%=FieldNames.MODULE%>="+module+"&subModuleName=<%=subModuleName%>&isBuildTab=false&<%=BuilderFormFieldNames.TAB_ACTION%>=" + action+"&tabCount=<%=tabCount+1%>"; //PP_changes
		openLightBox(url,"650","435","no");
	}
	function modifyTabPosition(module)//BB-20150203-259 (Tab Re-ordering changes) starts
	{
		document.blankForm.<%=FieldNames.MODULE%>.value = module;
		document.blankForm.action = "modifyBuilderTabPosition?moduleId="+<%=moduleId%>;
		document.blankForm.submit();
	}//BB-20150203-259 (Tab Re-ordering changes) ends
	function openLightBox(url,width,height,scroll)
	{	 	
		if(url.indexOf("cft=") == -1) {
			if(url.indexOf("?") == -1) {
				url = url + "?" + cft;
			} else {
				url = url + "&" + cft;
			}
		}
	    $.fn.colorbox({width:width, height:height, iframe:true, href:url, scrolling:scroll,onComplete:function(){$('#cboxLoadingGraphic').show();},onClosed:function(){$('#cboxLoadingGraphic').hide();}}); //P_B_47287
	}

	function validate()
	{
		var formTemp = "";
		var formName = document.getElementById("formName");
		var moduleName = document.getElementById("moduleName").value;
		var formID = document.getElementById("formID").value;
		if(formName.value == null || trim(formName.value) == "")
		{
			//alert("Please enter Custom Tab Name.");
			    FCI.Alerts(FCLang.PLEASE_ENTER_CUSTOM_TAB_NAME);
			formName.focus();
			return false;
		}
		else
		{
			var fieldChar = trim(formName.value);
			var regExp = new RegExp("[!@#$^&*_:,;<>\\\\]");
			if (fieldChar.match(regExp)) 
			{
		        //FCI.Messages("Title " + FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
		     //   alert("Custom Tab Name cannot contain special characters.");
		         FCI.Alerts(FCLang.CUSTOM_TAB_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
		        formName.focus();
				return false;
			}
		}
	}

	function replaceAll( str, from, to ) 
	{
		 var idx = str.indexOf( from );
			while ( idx > -1 ) {
				str = str.replace( from, to );
				idx = str.indexOf( from );
			}
	
			return str;
	}
	function selectModuleForm() 
	{
	    var formName = document.builderForm ;
		formName.submit();
	}
	function showTabDetails()
	{
			$("#instructiondetail").hide();
			$("#tabdetails").show();
			<%if(isCM){%>
				$("#tabstable").show();
				$("html, body").animate({ scrollTop: 0 }, "fast");
			<%}%>
			<%if(isFim){%>
			$("#tabstablefim").show();
			$("html, body").animate({ scrollTop: 0 }, "fast");
		<%}%>
	}
	function showInstructionDetails()
	{
		$("#tabdetails").hide();
		$("#instructiondetail").show();
		<%if(isCM){%>
			$("#tabstable").hide();
		<%}%>
		<%if(isFim){%>
		$("#tabstablefim").hide();
		<%}%>
	}

	function nextReport(moduleName){
		document.blankForm.<%=FieldNames.MODULE%>.value=moduleName;
		if(moduleName=='cm'){
			document.blankForm.subModuleName.value='contact';
		}else{
			document.blankForm.subModuleName.value=moduleName;
		}
		document.blankForm.fromWhere.value='admin';
		document.blankForm.isTab.value='Y';
		document.blankForm.submit();		
	}
	function nextReportFim(moduleName,subModuleName){
		document.blankForm.<%=FieldNames.MODULE%>.value=moduleName;
		document.blankForm.subModuleName.value=subModuleName;
		document.blankForm.fromWhere.value='admin';
		document.blankForm.isTab.value='Y';
		document.blankForm.submit();		
	}
</script>
<form name = "blankForm" action = "builderWebForm" method="post" <%=Constants.FORM_ENCODING%>><%--BB_Naming_Convention --%>
	<input type='hidden' name='<%=FieldNames.MODULE%>' value=''>
	<input type='hidden' name='tabName' value=''>
	<input type='hidden' name='<%=BuilderFormFieldNames.TAB_ACTION%>' value=''>
	<input name="subModuleName" type="hidden" value="<%=subModuleName%>" /> <%-- PP_changes --%>
	<input type='hidden' name='isTab' value='<%=isTab%>'>
	<input type='hidden' name='fromWhere' value=''>
</form>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<form name="builderForm" action="builderWebForm" method="post" <%=Constants.FORM_ENCODING%>><%--BB_Naming_Convention --%>
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>"> <%-- PP_changes --%>
	<input type="hidden" name='<%=FieldNames.MODULE%>' value="<%=moduleName%>"><!--BB-20150203-259(Builder for tab repositioning ) changes  -->
	<table width="100%" cellpadding="0" cellspacing="0" valign="top" >
		<tr>
			<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width = "100%" valign = top>
				<table width="100%" cellpadding="0" cellspacing="0" valign="top" align="left">
                	<tr>
                    	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="0" height="5"></td>
					</tr>
					<tr>
						<% 
						if("captivate".equals(subModuleName)) { //PP_changes starts%>
							<td  width="100%" height="19" class="text"><a href="administration"><u><%=LanguageUtil.getString("Admin",(String)session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="<%=href%>"><u><%=LanguageUtil.getString(moduleURL,(String)session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="franchiseGrantingActivity"><u><%=LanguageUtil.getString("Manage Candidate Portal",(String)session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<span class="text_b"><%=LanguageUtil.getString("Manage Virtual Brochure Form",(String)session.getAttribute("userLanguage"))%></span></td>
						<% } else { 
						%>
							<td  width="100%" height="19" class="text"><a href="administration"><u><%=LanguageUtil.getString("Admin",(String)session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="<%=href%>"><u><%=LanguageUtil.getString(moduleURL,(String)session.getAttribute("userLanguage"),NewPortalUtils.getModuleKey(moduleURL))%></u></a> >&nbsp;<span class="text_b"><%=LanguageUtil.getString("Manage Form Generator",(String)session.getAttribute("userLanguage"))%></span></td>
						<% } //PP_changes ends %>
					</tr>
                    <tr>
                    	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="0" height="5"></td>
	                </tr>
				</table>
			</td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		</tr>
		<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td align="left" width="50%" height="1" bgcolor="#bbbbbb"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		</tr>
		<tr>
		    <td colspan="3" height="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="8"></td>
		</tr>
		<tr>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
			<td>
				<% if("captivate".equals(subModuleName)) { //PP_changes starts %>
					<%@ include file="virtualBrochureTopFormBuilder.jsp" %>
				<% } //PP_changes ends %>
			</td>
			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" valign="top" id="instructiondetail" style="display:<%=instructionDisplay%>">
		<%if(StringUtil.isValidNew(sTabModuleName)){%>
		<tr>
		    <td colspan="3" height="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="6"></td>
		</tr>
		
		<tr>
		    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		    <td class="text" height="19" valign="bottom" width="100%"> <%-- PP_changes  --%>
				<div id="instructiondiv" name="instructiondiv" style="position: relative; display: ;">
				<table class="newForm" border="0" cellpadding="0" cellspacing="0"
					width="100%" align="default">
					<tbody>
						<tr>
							<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	   
							<td >
		
							<div id="noteDiv" style="padding:10px 5px 10px 50px;">
		
							<div class="text_b"><%=LanguageUtil.getString("Form Generator Instructions")%> :</div>
							<ul class="bodyText" style="width: 100%;">
								<!-- <li style="color: red"><span class="text_b"><%=LanguageUtil.getString("WARNING")%>:</span>
									<ul class="bodyText" style="color: red;width: 100%;">
										<li><%=LanguageUtil.getString("Please do not make changes when users are logged into the system. Changes should be done in business off-hours.")%></li>
										<li><%=LanguageUtil.getString("Notify all users in advance to avoid conflicts when updates are made.")%></li>
									</ul>
								</li> --><!-- Commented for FIM Form Generator Udai Agarwal -->
								<li><span class="text_b"><%=LanguageUtil.getString("Tab Management")%></span>
									<ul class="bodyText" style="width: 100%;">
										<li><%=LanguageUtil.getString("Built-in or Tabs present in the default system can not be Deleted/Deactivated. But their Section/Field/Document management is allowed.")%></li>
										<li><%=LanguageUtil.getString("Custom Tabs can be Added/Modified/Deleted/Activated/Deactivated.")%></li>
										<li><%=LanguageUtil.getString("Custom Tabs containing Information, can not be Deleted.")%></li>
										<li><%=LanguageUtil.getString("Tabs associated with Reports cannot be Deactivated.")%></li>
									</ul>
								</li>
								<li><span class="text_b"><%=LanguageUtil.getString("Section Management")%></span>
									<ul class="bodyText" style="width: 100%;">
										<li><%=LanguageUtil.getString("Built-in or sections present in the default system cannot be Deleted/Deactivated.")%></li>
										<li><%=LanguageUtil.getString("New sections can be added or deleted.")%></li>
										<li><%=LanguageUtil.getString("A section without any active fields will not be visible to the users.")%></li>
										<li><%=LanguageUtil.getString("A section with at least two active fields will have the option to 'Modify Field Position'.")%></li>
										<li><%=LanguageUtil.getString("Tabular section will not be visible on Preview Form.")%></li>
									</ul>
								</li>
								<li><span class="text_b"><%=LanguageUtil.getString("Field Management")%></span>
									<ul class="bodyText" style="width: 100%;">
										<li><%=LanguageUtil.getString("Built-in or Fields present in the default system cannot be deleted.")%></li>
										<li><%=LanguageUtil.getString("Custom Fields can be Added/Modified/Deleted/Deactivated.")%></li>
										<li><%=LanguageUtil.getString("Field Display Labels can be modified (even for default built-in fields).")%></li>
										<!-- BUG_47133 30/12/2014 starts-->
										<%if("fim".equals(sTabModuleName)){ %>
										<li><%=LanguageUtil.getString("Fields cannot be deleted if they have already been used to store data, but their Display Label can be modified.")%></li>
										<%} else{%>
										<li><%=LanguageUtil.getString("Fields cannot be deleted if they have data, but their Display Label can be modified.")%></li>
										<%} %>
										<!-- BUG_47133 30/12/2014 ends-->
										<%--P_SCH_ENH_008 Starts --%>
										<%if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(sTabModuleName)){
											// Do not display PII in case of Jobs Form Generator
										}else{ %>
										<li><%=LanguageUtil.getString("Access to fields containing \"PII\" or Personally Identifiable Information requires a password.")%></li>
										
									    <li><%=LanguageUtil.getString("Even if a field is Active or Mandatory by default, its PII status or Display Label can be modified.")%></li>
									    <%} %>
									    <%--P_SCH_ENH_008 Starts --%>
									</ul>
								</li>
								
								<li><span class="text_b"><%=LanguageUtil.getString("Document Management")%></span>
									<ul class="bodyText" style="width: 100%;">
										<li><%=LanguageUtil.getString("Built-in document fields cannot be deleted but their Field Display Labels can be modified.")%></li>
										<li><%=LanguageUtil.getString("Custom document fields can be Added/Modified/Deleted.")%></li>
										<li><%=LanguageUtil.getString("Documents can have subject associated for collecting additional information.")%></li>									</ul>
								</li>
								
								
							</ul>
							</div>
							</td>
							<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	   
						</tr>
						
				</table>
				</div>
				</td>
		    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		</tr>
		<%
			}
		%>
		<tr>
	    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	    <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="5"></td>
	    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	    </tr>
		<tr>
	    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	    <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="5"></td>
	    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	    </tr>	
		<tr>
	    	<td width="10">
	    		<img width="10" height="1" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif">
	    	</td>
	   	 	<td width="">

				&nbsp;&nbsp;<input type="button" class="cm_new_button" onclick="showTabDetails()" value="<%=LanguageUtil.getString("Continue")%>">&nbsp;

	    	

				<input type="button" class="cm_new_button" onclick="location.href='<%=href%>'" value="<%=LanguageUtil.getString("Back")%>" >
	   	 	
	   	 	</td>
	    
	    </tr>
		</table>
		
	   <%if(isCM){%>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabstable" style="display:<%=detailDisplay%>">
	     <tr>
		  	  <td width="100%">
		  	    <div id="pettabs" class="cmIndentmenu">
			  <ul style="width:100%">
				<%if(MultiTenancyUtil.getTenantConstants().IS_LEAD_ENABLED){%>
			  	  <li class=""><a href="#" class="<%="lead".equals(sTabModuleName)?"selected":""%>" onclick="javascript:nextReport('lead');"><span style="font-size:12px"><%=LanguageUtil.getString("Lead")%></span></a></li>
			    <%}%>	
  		        <%if(userRoleMap.isPrivilegeInMap("/companySummary")){ %>  <%--P_CM_B_68658  --%>
				    <li class=" "><a href="#" class="<%="account".equals(sTabModuleName)?"selected":""%>" onclick="javascript:nextReport('account');"><span style="font-size:12px"><%=LanguageUtil.getString("Account")%></span></a></li>
			    <%} %>
			    <li class=""><a href="#" class="<%="cm".equals(sTabModuleName)?"selected":""%>" onclick="javascript:nextReport('cm');"><span style="font-size:12px"><%=LanguageUtil.getString("Contact")%></span></a></li>
			    <%if(MultiTenancyUtil.getTenantConstants().IS_OPPORTUNITY_ENABLED){%>
			    <li class=""><a href="#" class="<%="opportunity".equals(sTabModuleName)?"selected":""%>" onclick="javascript:nextReport('opportunity');"><span style="font-size:12px"><%=LanguageUtil.getString("Opportunity")%></span></a></li>
			    <%}%>	
			  </ul>
			  <div style="clear:both"></div>
			  </div>
			  </td>
			<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="12"></td>
        </tr>
	   </table>	
	   <%}%>
		<%
			if (isFim) {
		%>
			 <%@ include file="/jsp/builderform/formBuilderSubTabs.jsp"%> 
		<%
			}
		%>
		<div  id="tabdetails" style="display:<%=detailDisplay%>">
      	<table width="100%" cellpadding="0" cellspacing="0" valign="top" >
		<%
			SequenceMap mMap = BuilderFormUtil.getModuleMap();//ENH_MODULE_CUSTOM_TABS

			if (mMap == null) {
		%>
		<tr class="tb_data">
		    <td>&nbsp;</td>
		    <td class="urgent_fields">&nbsp;<%=LanguageUtil.getString("Data could not be retrieved", (String) session.getAttribute("userLanguage"))%>.<%=LanguageUtil.getString(" Please try again later",
									(String) session.getAttribute("userLanguage"))%>.</td>
		    <td>&nbsp;</td>
		</tr>
		<%
			} else {
		%>
		 <tr height="20px"></tr>
		<tr class="thead">
		    <td>&nbsp;</td>
		    <td >
		        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        
		        <tr class="thead hText16black">
		            <td width="2"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>

		            <td class="hText16black"  nowrap height="25" width="10%" >&nbsp;<strong><%=LanguageUtil.getString("Form Name",(String) session.getAttribute("userLanguage"))%> :</strong></td>
            		<td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
		            <td>
                			<select name="formNames"  id="formNames" class="multiList" onchange ="javascript:selectFimForm()">
                    <option value="null"><%=LanguageUtil.getString("All", (String) session
							.getAttribute("userLanguage"))%></option>
   
					<%
						//System.out.println("builderFormMap======>"+builderFormMap);
					   	Iterator builderFormIter = builderFormMap.values()
					   					.iterator();
					   			while (builderFormIter.hasNext()) {
					   				Info info = (Info) builderFormIter.next();
					   				String gFormId = info.getString(BuilderFormFieldNames.BUILDER_FORM_ID);
					   				String sFormName = info.get(BuilderFormFieldNames.FORM_NAME);
					   				String isCustom = info.get(BuilderFormFieldNames.IS_CUSTOM);
					   				if("bQual".equals(sFormName)) {//BUG_60439 Prateek Sharma
					   					continue;	
					   				}
					   			if("Y".equals(isCustom)){	
					   %>
					            <option value="<%=gFormId%>"><%=sFormName%></option>
					            <%}else{ %>
					            <option value="<%=gFormId%>"><%=LanguageUtil.getString(sFormName,(String) session.getAttribute("userLanguage"))%></option>
					<%			
						}
					   			}
					%>
				</select>
		
	          </td>
		             <td><%-- --<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
		            <%
		            if (StringUtil.isValidNew(sTabModuleName)) {
		            %>
		            
		            <td width="100%" align="right">
		            <%if((userRoleMap.isPrivilegeIDInMap("424") && "fs".equals(sTabModuleName)) || (userRoleMap.isPrivilegeIDInMap("428") && "fim".equals(sTabModuleName)) || (userRoleMap.isPrivilegeIDInMap("429") && "cm".equals(sTabModuleName)) || (userRoleMap.isPrivilegeIDInMap("2922") && "scheduler".equals(sTabModuleName))) {%>
		            <a rel="subcontent" onClick ="addFormTab('<%=sTabModuleName%>', 'create')" id="addsectionlink" class="rowText12 nBtn26 fltRight" href="javascript:void(0)"><span style="padding-right:8px;"><%=LanguageUtil.getString("Add New Tab")%></span></a>
		            <%} %>
		            </td>
		            <%
		            } else {
		            %>
		            <td width="100%" align="right">
		            </td>
		            <%
		            	}
		            %> --%>
		            </td>
		        </tr>
	</table>
    </td>
</tr>
		<% }
             //String headerButtonsHtml="&nbsp;&nbsp;[&nbsp;<a href='modifyFimCustomTabFieldPosition' class='text_b'><u>"+LanguageUtil.getString("Modify Fields Position",(String)session.getAttribute("userLanguage"))+ "</u></a>&nbsp;]";
             
        //String headerButtonsHtml="<b>[&nbsp;<a href=javascript:addFormTab('add') ><u>"+LanguageUtil.getString("Add Builder Tab",(String)session.getAttribute("userLanguage"))+"</u></a>&nbsp;]</b>";
    if(StringUtil.isValidNew(sTabModuleName)){
		if(returnValue!=-1 && isModified)
		{ 
		%>
        <tr>
        	<td colspan="3" align="center">
			  	<table>
					<tr id="deleteMessage">
						<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;">
							<%
							if(returnValue == 1)
							{
							%>
                                 <%=LanguageUtil.getString(message+" has been activated successfully",(String) session.getAttribute("userLanguage"))%>.
							<%
							}
							else if(returnValue == 2)
							{
							%>
                                 <%=LanguageUtil.getString(message+" has been deactivated successfully",(String) session.getAttribute("userLanguage"))%>.
							<%
							}
							else if(returnValue == 3)
							{
							%>
                                 <%=LanguageUtil.getString(message+" has been deleted successfully",(String) session.getAttribute("userLanguage"))%>.
							<%
							}
							%>
						</td>
					</tr>
				</table>
			</td>
        </tr>
     	<%
		} 
		%>
		<tr>
			<td>&nbsp;</td>
		<%
			if(tabGeneratorMap != null)
			{
		%>
			<td>
				<ar:newdisplaytable width="100%" scope="session" name="tabGeneratorMap"  recordsPerPage="20" recordsHeader="<%=actionButtons%>" tableSize="20" tableHeader="<%=LanguageUtil.getString(displayName,(String)session.getAttribute("userLanguage"))%>" i18n="true" tableHeaderClass="login_pg_hdrSmall"> 
				<ar:newtableheader headerHtmlClass="tb_sub_hdr_b" cellSpacing="1" cellPadding="4"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.NO%>" colDispName="No."  colHeaderWidth="3%"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.TAB_DISPLAY_LINK%>" colDispName="<%=tabHeaderName%>"  colHeaderWidth="65%"/>
				<%--
				<ar:newcolumn colName="<%=FieldNames.LABEL_NAME%>" colDispName=""  colHeaderWidth="2%"/>
				<ar:newcolumn colName="<%=FieldNames.SUB_MODULE_NAME%>" colDispName="Sub Module Name"  colHeaderWidth="15%"/>
				--%>
				<% if("captivate".equals(subModuleName)) { //PP_changes starts %>
					<ar:newcolumn colName="<%=FieldNames.SECTION_NAME%>" colDispName="Section Name"  colHeaderWidth="20%"/>
				<% } //PP_changes ends %>
				<ar:newcolumn colName="<%=FieldNames.LABEL_NAME%>" colDispName="Active" align="center"  colHeaderWidth="5%"/>
<%-- 				<ar:newcolumn colName="<%=FieldNames.MODULE%>" colDispName="Module"  colHeaderWidth="16%"/> --%>
				<ar:newcolumn colName="<%=FieldNames.ACTION%>"  colDispName="Action" align="center"  colHeaderWidth="5%"/>
				</ar:newdisplaytable>
			</td>
		<%
			}
		%>
		
			<td>&nbsp;</td>
		</tr>
		<%if(("fim".equals(sTabModuleName) || "fs".equals(sTabModuleName)) || "mu".equals(subModuleName) || "area".equals(subModuleName)){%>  <%-- P_B_72916--%>
		<tr>
			<td>&nbsp;</td>
				<td>
				<ar:newdisplaytable width="100%" scope="session" name="contactHistoryMap"  recordsPerPage="20" tableSize="20" tableHeader="<%=LanguageUtil.getString("Contact History")%>" i18n="true" tableHeaderClass="login_pg_hdrSmall"> 
				<ar:newtableheader headerHtmlClass="tb_sub_hdr_b" cellSpacing="1" cellPadding="4"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.NO%>" colDispName="No."  colHeaderWidth="3%"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.TAB_DISPLAY_LINK%>" colDispName="<%=tabHeaderName%>"  colHeaderWidth="65%"/>
				<% if("captivate".equals(subModuleName)) { //PP_changes starts %>
					<ar:newcolumn colName="<%=FieldNames.SECTION_NAME%>" colDispName="Section Name"  colHeaderWidth="20%"/>
				<% } //PP_changes ends %>
				<ar:newcolumn colName="<%=FieldNames.LABEL_NAME%>" colDispName="Active" align="center"  colHeaderWidth="5%"/>
				<ar:newcolumn colName="<%=FieldNames.ACTION%>"  colDispName="Action" align="center"  colHeaderWidth="5%"/>
				</ar:newdisplaytable>
			</td>
			<td>&nbsp;</td>
		</tr>
		<%} %>
		
		<%if(("cm".equals(sTabModuleName) || "lead".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName)) && !"mu".equals(subModuleName) && !"area".equals(subModuleName)){%>
		<tr>
			<td>&nbsp;</td>
				<td>
				<ar:newdisplaytable width="100%" scope="session" name="contactHistoryMap"  recordsPerPage="20" tableSize="20" tableHeader="<%=LanguageUtil.getString("Other Information")%>" i18n="true" tableHeaderClass="login_pg_hdrSmall"> 
				<ar:newtableheader headerHtmlClass="tb_sub_hdr_b" cellSpacing="1" cellPadding="4"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.NO%>" colDispName="No."  colHeaderWidth="3%"/>
				<ar:newcolumn colName="<%=BuilderFormFieldNames.TAB_DISPLAY_LINK%>" colDispName="<%=tabHeaderName%>"  colHeaderWidth="65%"/>
				<ar:newcolumn colName="<%=FieldNames.LABEL_NAME%>" colDispName="Active" align="center"  colHeaderWidth="5%"/>
				<ar:newcolumn colName="<%=FieldNames.ACTION%>"  colDispName="Action" align="center"  colHeaderWidth="5%"/>
				</ar:newdisplaytable>
			</td>
			<td>&nbsp;</td>
		</tr>
		<%} %>
		<tr>
	    	<td width="10">
	    		<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1">
	    	</td>
	   	 	<td>
<%-- 	   	 		<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="5"> --%>
				<%if(isCM){%>
					<input type="button" class="cm_new_button" onclick="javascript:history.back();" value="<%=LanguageUtil.getString("Back")%>">
				<%}else{%>
					<input type="button" class="cm_new_button" onclick="showInstructionDetails()" value="<%=LanguageUtil.getString("Back")%>">
				<%}%>				
	   	 	</td>
	    	<td width="10">
	    		<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1">
	    	</td>
	    </tr>
		<%}%>
</table>
</div>

<div id="popupDiv" style="display: none;">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/javascript/ajaxforcombos.js"> </script>
<html>
<head>
<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title")!= null ?MultiTenancyUtil.getTenantContext().getAttribute("title") : LanguageUtil.getString("Franchise System",(String)session.getAttribute("userLanguage"))%></title>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
		<td width="100%">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td class="pg_hdr" nowrap="nowrap">&nbsp;<%--=LanguageUtil.getString("Customize Custom Tab",(String)session.getAttribute("userLanguage"))--%></td>
				<td class="small_txt" width="100%" align="right"><%--=LanguageUtil.getString("Field marked with",(String)session.getAttribute("userLanguage"))%>
				<span class="urgent_fields">*</span> <%=LanguageUtil.getString("is mandatory.",(String)session.getAttribute("userLanguage"))--%></td>
			</tr>
		</table>
		</td>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
	</tr>
	<tr>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
		<td id="hdrLabel" class="TextLbl_b" width="100%" height="19">&nbsp;<%=LanguageUtil.getString("Activate Custom Tab",(String)session.getAttribute("userLanguage"))%>
		</td>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height=10 width=1 /></td>
	</tr>
	<tr>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
		<td width="100%">
		<table width="100%">
		<tr>
		<td align="right" width="30%" valign="center" height="" class="TextLbl_b" ><%--<span class="urgent_fields">*</span> --%><%=LanguageUtil.getString("Custom Tab Name",(String)session.getAttribute("userLanguage"))%> : </td>
		<td width="70%" valign="center" class="TextLbl" ><input type="text" class="fTextBox" value="" id="formName" name="formName" size="25" /></td>
		</tr>
		</table>
		</td>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
	</tr>
	<tr>
		<td colspan=3><img
			src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height=10
			width=10>
		</td>
	</tr>
	<tr>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
		<td width="100%">
		<table width="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td  height="19"><input name="sButton" id="sButton" type="submit"
					value="<%=LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"))%>"
					class="cm_new_button"  onClick="javascript:validate()">&nbsp; <input type="button"
					value="<%=LanguageUtil.getString("Close",(String)session.getAttribute("userLanguage"))%>"
					class="cm_new_button" onClick="javascript:window.close()"></td>
			</tr>
		</table>
		</td>
		<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
			height=10 width=10></td>
	</tr>
</table>

</body>
</html>

</div>
</form>
<form name="builderWebForm" action="builderWebForm"><%--BB_Naming_Convention --%>
	<input name="formNames" type="hidden"/>
	<input name="subModuleName" type="hidden" value="<%=subModuleName%>" /> <%--//PP_changes --%>
</form>
<script>

	function selectFimForm() {
	    var formName = document.builderForm ;
		formName.submit();
	}
    function redirect(action,tabName,moduleName,submoduleName,isCustom)
    {
	    if(action=="1" || action=="2")
	  	{
	    	var left=(screen.width)/2 - 200;
	    	var top=(screen.height)/2 - 70;
	    	var url;
	    	var popUpName;
		  	
	    	if(action=="2")
	    	{
	    		var confrm = "";
                <%if("captivate".equals(subModuleName)) {%>
                confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_ACTIVATE_THIS_FORM);
                <%}else {%>
                confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_ACTIVATE_THIS_TAB);
                <%}%>
	    		if(confrm)
	    		{
	    			var f = document.blankForm;
	    			f.tabName.value   = tabName;
	    			f.<%=FieldNames.MODULE%>.value = moduleName;
	    			f.<%=BuilderFormFieldNames.TAB_ACTION%>.value = "active";
	    			f.submit();
	    		}
	    		else
	    		{
	    			return false;
	    		}
<%-- 		  		document.getElementById("sButton").value="<%=LanguageUtil.getString("Activate",(String)session.getAttribute("userLanguage"))%>";
		  		document.getElementById("hdrLabel").innerHTML="&nbsp;<%=LanguageUtil.getString("Activate Custom Tab",(String)session.getAttribute("userLanguage"))%>";
		  		document.getElementById("eventType").value="create"; --%>
		  	}
	    	else
	    	{
<%-- 		  		document.getElementById("sButton").value="<%=LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"))%>";
		  		document.getElementById("hdrLabel").innerHTML="&nbsp;<%=LanguageUtil.getString("Modify Custom Tab",(String)session.getAttribute("userLanguage"))%>";
		  		document.getElementById("eventType").value="modify"; --%>
		  		if(!isCustom)
		  		{
		  			isCustom = "";
		  		}
		  		url = "addBuilderTab?<%=FieldNames.MODULE%>="+moduleName+"&<%=BuilderFormFieldNames.TAB_NAME%>="+tabName+"&<%=BuilderFormFieldNames.TAB_ACTION%>=modify&submoduleName="+submoduleName+"&<%=FieldNames.IS_CUSTOM%>="+isCustom;
		  		openLightBox(url,"650","315","no");			  	
		  	}
		  	

	  	}
	    else if(action == "3")
	  	{
	    	//BUG_42539_Starts
	    	var isFieldIsInReport="no";
			$.ajax({
			        type: "POST",
			        url: "handleAjaxRequest", 
			        data  :"dataset=checkTabFieldsforReports&tabName=" + tabName+"&moduleName="+moduleName,
			        async:false,
			        success:function(data) {
			        	isFieldIsInReport=trim(data);        	 
			        }
			});
			//EXTERNAL_FORM_BUILDER : START
			var isTabUsedInWebForm	=	"no";
				$.ajax({
				        type: "POST",
				        url: "handleAjaxRequest", 
				        data  :"dataset=checkTabForWebForm&tabName=" + tabName+"&moduleName="+moduleName,
				        async:false,
				        success:function(data) {
				        	isTabUsedInWebForm=trim(data);
				        }
				});
			if("yes"==isTabUsedInWebForm)
			{
				//alert("Some Field(s) of this Tab are assosiated with some of Web Form. It cannot be deactivated.");
				FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_WEB_FORM+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
				return false;
			}
			
			
			//EXTERNAL_FORM_BUILDER : END
			else if("yes"==isFieldIsInReport)
			{
				//alert("Some Field(s) of this Tab are assosiated with some of Custom Report. It cannot be deactivated.");
				FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
				return false;
			}
            else if("true" == isFieldIsInReport) {
                //alert("Some Field(s) of this Tab are assosiated with some of Smart Group. It cannot be deactivated.");
                FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_GROUP+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            } else if("both" == isFieldIsInReport){
                //alert("Some Field(s) of this Tab are assosiated with some of Custom Report & Smart Group. It cannot be deactivated.");
                FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_AND_SMART_GROUP+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            }//P_B_80437 Starts
            else if("all" == isFieldIsInReport){
            	//alert("Some Field(s) of this Tab are assosiated with some of Custom Report, Smart Group & Smart Questions. It cannot be deactivated.");
            	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_SMART_GROUP_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            } else if("smartQuestionGroup" == isFieldIsInReport){
            	//alert("Some Field(s) of this Tab are assosiated with some of Smart Group & Smart Questions. It cannot be deactivated.");
            	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_GROUP_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            } else if("smartQuestionReport" == isFieldIsInReport){
            	//alert("Some Field(s) of this Tab are assosiated with some of Custom Report & Smart Questions. It cannot be deactivated.");
            	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            } else if("smartQuestion" == isFieldIsInReport){
            	//alert("Some Field(s) of this Tab are assosiated with some of Smart Questions. It cannot be deactivated.");
            	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
                return false;
            }
			//P_B_80437 Ends
			//BUG_42539_Ends

            var confrm="";
            <%if("captivate".equals(subModuleName)) {%>
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DEACTIVATE_THIS_FORM);
            <%}else {%>
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DEACTIVATE_THIS_TAB);
            <%}%>
    		if(confrm)
    		{
    			var f = document.blankForm;
    			f.tabName.value   = tabName;
    			f.<%=FieldNames.MODULE%>.value = moduleName;
    			f.<%=BuilderFormFieldNames.TAB_ACTION%>.value = "deactive";
    			f.submit();
    		}
    		else
    		{
    			return false;
    		}
	  	}
	    else if(action == "4")
	    {
	    	<%--P_CM_B_38266 :starts--%>
	    	var containFields="no";
			$.ajax({
			        type: "POST",
			        url: "handleAjaxRequest", 
			        data  :"dataset=checkTabFields&tabName=" + tabName+"&moduleName="+moduleName,
			        async:false,
			        success:function(data) {
			        	containFields=trim(data);        	 
			        }
			});
			if("true"==containFields)
			{
				//alert("Field(s) is/are assosiated with this tab. It cannot be deleted.");
				FCI.Messages(FCLang.FIELD_IS_ARE_ASSOSIATED_WITH_THIS_TAB+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
				return false;
			}
			<%--P_CM_B_38266 :ends--%>
            var confrm;
            <%if("captivate".equals(subModuleName)) {%>
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_FORM);
            <%}else{%>
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_TAB);
            <%}%>
    		if(confrm)
    		{
    			var f = document.blankForm;
    			f.tabName.value   = tabName;
    			f.<%=FieldNames.MODULE%>.value = moduleName;
    			f.<%=BuilderFormFieldNames.TAB_ACTION%>.value = "delete";
    			f.submit();
    		}
    		else
    		{
    			return false;
    		}
	   	}
	}

    function callForm(formId)
    {
    	
   	    document.builderWebForm.formNames.value=formId;//BB_Naming_Convention
   	 	document.builderWebForm.submit();//BB_Naming_Convention
    }
    
    function doActivate(tabName,moduleName,action)
    {
    	//BUG_42539_Starts
    	var isFieldIsInReport="no";
		$.ajax({
		        type: "POST",
		        url: "handleAjaxRequest", 
		        data  :"dataset=checkTabFieldsforReports&tabName=" + tabName+"&moduleName="+moduleName,
		        async:false,
		        success:function(data) {
		        	isFieldIsInReport=trim(data);
		        }
		});
		//EXTERNAL_FORM_BUILDER : START
		var isTabUsedInWebForm	=	"no";
		 if("deactive" == action){
			$.ajax({
			        type: "POST",
			        url: "handleAjaxRequest", 
			        data  :"dataset=checkTabForWebForm&tabName=" + tabName+"&moduleName="+moduleName,
			        async:false,
			        success:function(data) {
			        	isTabUsedInWebForm=trim(data);
			        }
			});
		 }
		
		if("yes"==isTabUsedInWebForm)
		{
			//alert("Some Field(s) of this Tab are assosiated with some of Web Form. It cannot be deactivated.");
			FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_WEB_FORM+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
			return false;
		}
		//EXTERNAL_FORM_BUILDER : END
		else if("yes"==isFieldIsInReport)
		{
			//alert("Some Field(s) of this Tab are assosiated with some of Custom Report. It cannot be deactivated.");
			FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
			return false;
		} else if("true" == isFieldIsInReport) {
            //alert("Some Field(s) of this Tab are assosiated with some of Smart Group. It cannot be deactivated.");
            FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_GROUP+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        } else if("both" == isFieldIsInReport){
            //alert("Some Field(s) of this Tab are assosiated with some of Custom Report & Smart Group. It cannot be deactivated.");
            FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_AND_SMART_GROUP+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        }//P_B_80437 Starts
        else if("all" == isFieldIsInReport){
        	//alert("Some Field(s) of this Tab are assosiated with some of Custom Report, Smart Group & Smart Questions. It cannot be deactivated.");
            FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_SMART_GROUP_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        } else if("smartQuestionGroup" == isFieldIsInReport){
        	//alert("Some Field(s) of this Tab are assosiated with some of Smart Group & Smart Questions. It cannot be deactivated.");
        	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_GROUP_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        } else if("smartQuestionReport" == isFieldIsInReport){
        	//alert("Some Field(s) of this Tab are assosiated with some of Custom Report & Smart Questions. It cannot be deactivated.");
        	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_CUSTOM_REPORT_AND_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        } else if("smartQuestion" == isFieldIsInReport){
        	//alert("Some Field(s) of this Tab are assosiated with some of Smart Questions. It cannot be deactivated.");
        	FCI.Messages(FCLang.SOME_FIELD_OF_THIS_TAB_ARE_ASSOSIATED_WITH_SOME_OF_SMART_QUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED+".");
            return false;
        }
		//P_B_80437 Ends
		//BUG_42539_Ends
		var confrm;
        <%if("captivate".equals(subModuleName)) {%>
        if("active" == action)
        {
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_ACTIVATE_THIS_FORM);
        }
        else
        {
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DEACTIVATE_THIS_FORM);
        }
        <%}else{%>
        if("active" == action)
        {
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_ACTIVATE_THIS_TAB);
        }
        else
        {
            confrm = FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_DEACTIVATE_THIS_TAB);
        }
        <%}%>
	
		if(confrm)
		{
			var f = document.blankForm;
			f.tabName.value   			  				  = tabName;
			f.<%=FieldNames.MODULE%>.value 				  = moduleName;
			f.<%=BuilderFormFieldNames.TAB_ACTION%>.value = action;
			f.submit();
		}
		else
		{
			return false;
		}
    }
    
    
    
	<%
	if (returnValue != -1) 
	{
	%>
		var time;
		if (document.getElementById("deleteMessage")) 
		{
			time = setTimeout("hideMessage()", 4000);
		}
		function hideMessage()	
		{
			document.getElementById("deleteMessage").style.display = 'none';
			clearTimeout(time);
		}
	<%
	}
	%>
    <%=request.getAttribute("methodBuffer")%>
	
</script>
<% 
}
catch(Exception e) 
{
	e.printStackTrace();
}
%>
