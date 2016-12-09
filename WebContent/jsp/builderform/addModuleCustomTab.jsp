<!--
* JSP created for ENH_MODULE_CUSTOM_TABS
-->
<%@page import="com.home.BuilderFormFieldNames"%>
<%@ page import = "com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%try{
/*  
  ----------------------------------------------------------------------------------------------------------
Version No.		Date		By			Against		Function Changed	Comments
-----------------------------------------------------------------------------------------------------------
P_FIM_E_1234       10/10/2007      Suchita             Enhancement done for modify event, as its unnecessary to update all tab for color
P_FIM_E_2222	    24/10/07     Suchita Agrawal	edited privileges add,view,modify,print,delete       execute() 
P_FIM_E_56789        12/06/08   Suchita Agrawal	         Modified trigger form name 
P_FIM_B_68307        15/12/2010 Sachin Shukla	         Changed the name of the form
P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
P_B_CM_40321         26June 2014		Chhaya Pathania				GUI of new tab in CM(fixed)
-----------------------------------------------------------------------------------------------------------
 */
%>
<%@ page import = "com.appnetix.app.portal.role.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import = "com.appnetix.app.util.database.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "com.appnetix.app.struts.EventHandler.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "com.appnetix.app.util.ModuleUtil.MODULE_NAME"%>

<%@ taglib uri="/artags" prefix="ar" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/Blue/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/pop.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/dateUtility.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/ajaxforcombos.js"></script>
<script type="text/javaScript" src="<%=request.getContextPath()%>/javascript/jqueryPopUp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/multiple.select.js"></script>
<link type="text/css" media="screen" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/smartconnect/colorbox1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script><%--Bug_48592 8/1/2015%-->
<%--ZC_CM_B_44056 :starts--%>

<script>
$(document).ready(function (){
	$('#mytable').closest('td').attr('colspan',4);		
});
</script>

<script>
$(document).ready(function() {
	  $(window).keydown(function(event){
	    if(!$('textarea').is(':focus') && event.keyCode == 13) {//Enter_Not_Working_On_TextArea
	      event.preventDefault();
	      return false;
	    }
	  });
	  $("#groupId").multipleSelect({
          filter: true,
          placeholder:"<%=LanguageUtil.getString("Select")%>"
      });
	  
	  if(parent.$.fn.colorbox) { // PP_changes starts
		  parent.$.fn.colorbox.vkResize($(document).width(), $(document).height());
	  } //PP_changes ends
	  isValidFranchisee(<%=request.getParameter("entityFranchisee")%>);
	});
</script>
<%--ZC_CM_B_44056 :ends--%>
<%

	String activityId = (String)request.getParameter("activityId"); //PP_changes starts
	String activityName = SQLUtil.getColumnValue("FRANCHISE_GRANTING_ACTIVITY", "ACTIVITY_NAME", "ACTIVITY_ID", activityId); //PP_changes ends
	String userTheme1 = (String) session.getAttribute("userTheme");
	Info infoEntryDetail 	= (Info)request.getAttribute("INFOENTRYDETAIL");
	String formName = (String)request.getAttribute("formName");
	FieldMappings mappings 	= (FieldMappings)request.getAttribute("mappings");
	String tableAnchor 		= (String)request.getAttribute(BuilderFormFieldNames.TABLE_ANCHOR);
	String tabName1			= (String)request.getAttribute("tabName1");
	String button 			= (String)request.getAttribute("button");
	//retreiving ActionType from request and if ActionType is null then setting its value to create
	String ActionType 		= (request.getAttribute("ActionType")!=null)?(String)request.getAttribute("ActionType"):"create";
	//retreiving franchiseeNo from session to set the foreign key for d table
	String fimFranchiseeNo	= (String)session.getAttribute("fimFranchiseeNo");
	String moduleName 		= (String)request.getAttribute("moduleName");
	Info currentModuleInfo = (Info)request.getAttribute("currentModuleInfo");
	String mandatoryFlag = (String)request.getAttribute("regular_mandatory");      //P_B_80593
	if(!StringUtil.isValidNew(formName) && "fsLeadDetails".equals(tableAnchor)) {
		formName = "leadPrimaryInfoForm";
	}
	String subModuleName	= (String)session.getAttribute("subModuleName");
	String headerAsTitle = FieldNames.EMPTY_STRING;
	String buttonClass = "cm_new_button";
	String tabreturnpage = "tabreturnpage";
	if(MODULE_NAME.NAME_CM.equals(moduleName)){
		buttonClass = "cm_new_button";
		headerAsTitle = FieldNames.YES;
		tabreturnpage = "cmtabreturnpage";
	}else if(MODULE_NAME.NAME_FS.equals(moduleName)){
		tabreturnpage = "fstabreturnpage";
	}
	if(currentModuleInfo == null){
		currentModuleInfo = new Info();
	}
	String sectionDetailString = (String)request.getAttribute("sectionDetailString");
	//signal-20150601-011 starts
		String moduleNameCustom=moduleName;
		if(MODULE_NAME.NAME_FIM.equals(moduleName)){
			if("fimInDevelopment".equals((String)session.getAttribute("subMenuName"))) {
				moduleNameCustom="fim_inDev";
			}else if("fimArea".equals((String)session.getAttribute("subMenuName"))) {
				moduleNameCustom="fim_area";
			}else if("fimMu".equals((String)session.getAttribute("subMenuName")) || "fimMU".equals((String)session.getAttribute("subMenuName"))) {
				moduleNameCustom="fim_mu";
			}else{
				moduleNameCustom="fim_franchiseewithoutsc";
			}
		}//signal-20150601-011 ends
	
		String isTabularFieldsConfigured = (String)session.getAttribute("isTabularFieldsConfigured");				//P_Enh_Multiple_Input_Tabular_Summary starts
		String idFieldName = (String)request.getAttribute("idFieldName");
		String idVal = request.getParameter(idFieldName);															//P_Enh_Multiple_Input_Tabular_Summary ends
		Info addMoreDataViewInfo = (Info) session.getAttribute("addMoreDataView");
%>
<%@ include file = "/jsp/builderform/fsbuilderValidation.jsp"%>

  <%if("false".equals(request.getParameter("leadViewAuthentication"))) {
  %>
  
	<table width="100%" cellspacing="0" cellpadding="3" border="0" class="summaryTblex">
	<tbody>
	<tr class="bText12">
	<tr>
	<td class="noRecord pd" colspan="4">No records found.</td>
	</tr>
	</tr>
	</tbody>
	</table>
	
 <%} else { %> 
		<%
		if(infoEntryDetail != null && infoEntryDetail.size()==0)
		{
		%>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<%-- START : P_B_ADMIN_CM_35359 : 24/03/2014 : Veerpal Singh --%>
		<%-- <tr>
			<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
			<td height="25" class="tb_hdr_b" width="98%">&nbsp;<%=sectionDetailString%></td>
			<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>--%>
		<tr>
			<td height="5" colspan="3"></td>
		</tr>
		<%-- END : P_B_ADMIN_CM_35359 : 24/03/2014 : Veerpal Singh --%>
		<tr>
		  <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		  <td class="crvBox2-bg" width="100%">
			  <table style="margin-bottom:0px;" border="0" cellpadding="0" cellspacing="0" width="100%">
			<% if("captivate".equals(subModuleName)) { //PP_changes starts
			%>
			<tr>
				<td colspan="3">
					<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10" height="5px">
				</td>
			</tr>
			<tr>
				<td height="20" colspan="3"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
			</tr>
			<%} %>
			  	<tr>
	  			  	 <td width="10" valign="top"><img width="10" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl.png"></td>
	  			  	 <td width="475" class="crvBox2-top"></td>
	  			  	 <td width="10" valign="top"><img width="16" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png"></td>
			  	</tr>
				<tr>
	  			  	<td class="crvBox2-left"></td>
	  			  	<td class="crvBox2-bg text" style="text-align:center;">
			         	<%=LanguageUtil.getString("No Form Fields found")%>
			        </td>
			        <td class="crvBox2-right"></td>
			    </tr>
				<tr>
                     <td valign="bottom" align="left"><img width="10" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png"></td>
                     <td class="crvBox2-botom"></td>
                     <td valign="bottom" align="right"><img width="16" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png"></td>
               </tr>
			  </table>
			  <%
			if("yes".equals(request.getParameter("fromPreview")) || "yes".equals(request.getParameter("fromSectionPreview"))) //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
			{
			%>
			   <table>
			   <tr> <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td></tr>
			   <tr><td>
				<input type="button" name="Close" onClick="javascript:window.close();" value=<%=LanguageUtil.getString("Close")%> class="<%=buttonClass%>">
	           </td></tr>
	          </table>
			<%
			}%>
		  </td>
		  <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>
		</table>
		<%	
		}
		else
		{
		%>
		<!--P_FIM_B_68307 Starts -->
		<form action="genericHandler" method="POST" name="<%=formName%>" encType="multipart/form-data" <%=Constants.FORM_ENCODING%>>
		<!--P_FIM_B_68307 Ends -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<%if(MODULE_NAME.NAME_CM.equals(moduleName) || MODULE_NAME.NAME_ACCOUNT.equals(moduleName) || MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName) || MODULE_NAME.NAME_LEAD.equals(moduleName)){ %>
		<%if(StringUtil.isValid(sectionDetailString)){ %>
		<tr> 
		  <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
		 
		  <!-- P_B_CM_40321 starts-->
		  <td width="98%">
                <div class="btm-menu pd5">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
		 					<td height="22"  class="pry-info">&nbsp;<%=LanguageUtil.getString(sectionDetailString,(String)request.getSession().getAttribute("userLanguage"))%></td>      
	 	 				</tr>
	 	 			</table>
	 	 		</div>
	 	 	<!-- P_B_CM_40321 ends -->
	 	  <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
		</tr>
		<tr>
            <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
            <td>
                <table cellspacing="1" cellpadding="1" width="100%" border="0">
                    <tr>
                        <td align="right" valign="bottom" nowrap class="bText11gr"><%=LanguageUtil.getString("Fields marked with",(String)request.getSession().getAttribute("userLanguage"))%> <span class="urgent_fields">*</span> <%=LanguageUtil.getString("are mandatory",(String)request.getSession().getAttribute("userLanguage"))%>.</td>
                    </tr>
                </table>
            </td>
            <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
        </tr>
        <%} %>
		<tr id="start">
		    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
			<td colspan="4">
		<%-- 	<ar:Newcustomforms  noOfColumns="2" fieldsInfo='<%=infoEntryDetail%>'  cssClassKey="tb_data" cellspacing="0" cellpadding="0" i18n="true" styleClass="textbox" > 
				</ar:Newcustomforms> --%>
			 <ar:Newcustomforms  noOfColumns="2" fieldsInfo='<%=infoEntryDetail%>'  cssClassKey="tb_data" cellspacing="0" cellpadding="0" i18n="true"  > <!-- P_B_CM_40321  -->
				</ar:Newcustomforms> 	
			</td>
		    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>
		<%}else{%>
			<%if(!"yes".equals((String)request.getSession().getAttribute("forArchived"))) { %>
			<% if("captivate".equals(subModuleName)) { //PP_changes starts
			%>
			<tr>
				<td colspan="3">
					<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10" height="5px">
				</td>
			</tr>
			<tr>
				<td height="15" colspan="3"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
			</tr>
    		<tr>
				<td class="text" width="6"><img src="<%=request.getContextPath()%>images/spacer.gif" height="1" width="10"></td>
				<td align="left" class="hText18themeNew vp5"><%=activityName%></td>
			</tr>
			<%} //PP_changes ends %>
			<%if("true".equals(mandatoryFlag)){     //P_B_80593%>
			<tr>
            	<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
	            <td>
	                <table cellspacing="1" cellpadding="1" width="98%" border="0">
	                    <tr>
	                        <td align="right" valign="bottom" nowrap class="bText11gr"><%=LanguageUtil.getString("Fields marked with",(String)request.getSession().getAttribute("userLanguage"))%> <span class="urgent_fields">*</span> <%=LanguageUtil.getString("are mandatory",(String)request.getSession().getAttribute("userLanguage"))%>.</td>
	                    </tr>
	                </table>
	            </td>
            	<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width= "10"></td>
        	</tr>
        	<%}} %>
        	<%
        	StringBuffer backToSearch = new StringBuffer();
    		backToSearch.append("<a href=\"javascript:void(0);\" onclick=\"javascript:backToSearch();\" class=\"yBtn fltRight\" style=\"text-decoration:none\" >");
    		backToSearch.append("<img width=\"13\" height=\"15\" border=\"0\" class=\"fltRight pl5\" src=\"").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/btsicon.png\">").append(LanguageUtil.getString("Back to Search Results")).append("</a>");
    		backToSearch.append("</a>");
    		%>
    		<%if(MODULE_NAME.NAME_FS.equals(moduleName) && StringUtil.isValidNew((String)session.getAttribute("sPaggingUrlBack"))) { %>
    		<%if("yes".equals((String)request.getSession().getAttribute("forArchived"))) { %>
    		<tr>
				<td height="10" colspan="3"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
			</tr>
    		<%} %>
    		<% if(!"captivate".equals(subModuleName)) { //PP_changes%>
    		<tr>
				<td class="text" width="6"><img src="<%=request.getContextPath()%>images/spacer.gif" height="1" width="10"></td>
				<td align="right" class="bText12"><%=backToSearch.toString()%></td>
			</tr>
			<% } %>
    			
    		<%} %>
			<tr id="start">
			    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
				<td colspan="4">
				<%if(MODULE_NAME.NAME_FS.equals(moduleName)){ %>
					<%if("yes".equals((String)request.getSession().getAttribute("forArchived"))) { %>
						<jsp:include page="/jsp/franchise_sales/lead/noRecordFound.jsp">
	  	   					<jsp:param name="headerFrom" value="<%=tabName1%>"></jsp:param>
      					</jsp:include>
					<%} else { %>
						<ar:Newcustomforms  noOfColumns="2" fieldsInfo='<%=infoEntryDetail%>'  cssClassKey="tb_data" cellspacing="0" cellpadding="0" i18n="true"  ></ar:Newcustomforms>
					<%} %>
				<%} else { %>
					<ar:builderforms  noOfColumns="2" fieldsInfo='<%=infoEntryDetail%>' sectionDetailString="<%=sectionDetailString%>" headerAsTitle="<%=headerAsTitle%>" cssClassKey="tb_data"  cssClassValue="tb_data" tableWidth="100%" tableAllign="center" formHeaderClass="header_contrast">
					</ar:builderforms>
				<%} %>
				</td>
			    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
			</tr>
		<%} %>
		<tr>
		    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
			<td>
				<table width="100%" border="0" cellspacing="1" cellpadding="4">
				<tr>
				<td width="75%" align="left" colspan="2">&nbsp;
				    <!--P_FIM_B_68307 Starts -->
			<%
			if("yes".equals(request.getParameter("fromPreview")) || "yes".equals(request.getParameter("fromSectionPreview"))) //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
			{
			%>
				<input type="button" name="Close" onClick="javascript:window.close();" value=<%=LanguageUtil.getString("Close")%> class="<%=buttonClass%>">
			<%
			}
			else
			{
			%>
			<%if("yes".equals((String)request.getSession().getAttribute("forArchived")) && MODULE_NAME.NAME_FS.equals(moduleName)) { %>
			<%} else { %>
					<input type="button" name="Submit" value="<%=button%>" onClick="return fimValidation('customTabForm')"  class="<%=buttonClass%>">&nbsp;<%--P_FIM_BUG_14934 --%>
					<!--P_FIM_B_68307 Ends -->
					<%--<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="window.location='cmLeadPrimaryInfo'"><%--P_CM_BUG_42781 --%><%--P_FIM_BUG_14934 --%>
					<% if("captivate".equals(subModuleName)) { //PP_changes starts %>
						<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="parent.$.fn.colorbox.close();"><%--P_FIM_BUG_14934 --%>
					<% } else { %>
						<%
						if("cm".equals(moduleName) && (EventType.CREATE).equals(ActionType)){ %>
							<!--<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="window.location.href = 'cmLeadPrimaryInfocft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>'; return false">-->
						<%}else if("fs".equals(moduleName)){ %>
							<%if((EventType.CREATE).equals(ActionType)) { %>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('leadPrimaryInfoForm','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%} else {
								if("true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>									<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
									<input type="button" name="Cancel" value=<%=LanguageUtil.getString("Cancel",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="javascript:openTabularCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>','<%=idFieldName%>','<%=idVal%>')"><%--P_FIM_BUG_14934 --%>
								<%}else{%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
									<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
								<%}}
						}else if("entity".equals((String) session.getAttribute("showEntityTabs"))) {%>
						<%if((EventType.CREATE).equals(ActionType)) { %>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('fimEntityDisplayDetail','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%} else {
								if("true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>									<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
								<input type="button" name="Cancel" value=<%=LanguageUtil.getString("Cancel",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="javascript:openTabularCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>','<%=idFieldName%>','<%=idVal%>')"><%--P_FIM_BUG_14934 --%>
							<%}else{%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
							<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%}
							}%>
						<%} else if("mu".equals((String) session.getAttribute("showEntityTabs"))) {%>
						<%if((EventType.CREATE).equals(ActionType)) { %>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('fimMUInfo','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%} else {
								if("true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>									<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
									<input type="button" name="Cancel" value=<%=LanguageUtil.getString("Cancel",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="javascript:openTabularCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>','<%=idFieldName%>','<%=idVal%>')"><%--P_FIM_BUG_14934 --%>
								<%}else{%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
									<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
								<%}
							}%>
						<%} else if("area".equals(subModuleName)) {%>
						<%if((EventType.CREATE).equals(ActionType)) {%>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('fimARAreaInfo','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<% }else{ 
								if("true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>									<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
								<input type="button" name="Cancel" value=<%=LanguageUtil.getString("Cancel",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="javascript:openTabularCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>','<%=idFieldName%>','<%=idVal%>')"><%--P_FIM_BUG_14934 --%>
							<%}else{%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%} 
							}%>
						<%}else if("fim".equals(moduleName) ) { %>
							<%if((EventType.CREATE).equals(ActionType) && (addMoreDataViewInfo != null && addMoreDataViewInfo.size() == 0)) { %>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('fimCenterInfo','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%} else {
							if("true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>									<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
								<input type="button" name="Cancel" value=<%=LanguageUtil.getString("Cancel",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="javascript:openTabularCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>','<%=idFieldName%>','<%=idVal%>')"><%--P_FIM_BUG_14934 --%>
							<%}else{%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%}
							}%>
						<%} else { %>
								<input type="button" name="Cancel" value="<%=LanguageUtil.getString("Cancel")%>" class="<%=buttonClass%>" onClick="javascript:openCustomTab('moduleCustomTab','<%=moduleNameCustom%>','<%=tableAnchor%>'); return false"><%--P_FIM_BUG_14934 --%><%--signal-20150601-011 --%>
							<%}
						 %>
					<% } //PP_changes ends %>
					<%} %>
					
			<%
			}
			%>
					<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=tableAnchor%>">
           <%
            if((EventType.CREATE).equals(ActionType))
            {
           %>
           			<%if("captivate".equals(subModuleName)) { //PP_changes starts %>
                    <input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="tabreturnpage?showPopUp=Y&fromCaptivate=Y&fromOuterPortal=<%=request.getParameter("fromOuterPortal") %>&returnTabPageNew=moduleCustomTab&customTabKey=<%=tableAnchor%>&activityId=<%=activityId%>">     <%--P_B_55170--%>
             			<input type="hidden" name="<%=FieldNames.RETURN_PAGE_TAB%>" value="moduleCustomTab?showPopUp=Y&fromCaptivate=Y&customTabKey=<%=tableAnchor%>&activityId=<%=activityId%>">
           			
           			<%} else {%>
						<input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="tabreturnpage?returnTabPageNew=moduleCustomTab&customTabKey=<%=tableAnchor%>">     <%--P_B_55170--%>
             			<input type="hidden" name="<%=FieldNames.RETURN_PAGE_TAB%>" value="moduleCustomTab?customTabKey=<%=tableAnchor%>">
             		<%}//PP_changes ends %>
           <%
            }
            else
            {
           %>
           			<%if("captivate".equals(subModuleName)) { //PP_changes starts%>
                    <input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="moduleCustomTab?showPopUp=Y&fromCaptivate=Y&fromOuterPortal=<%=request.getParameter("fromOuterPortal") %>&customTabKey=<%=tableAnchor%>&activityId=<%=activityId%>">
            		<%} else { %>
            			<input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="moduleCustomTab?customTabKey=<%=tableAnchor%>">
            		<%} //PP_changes ends%>
           <%
            }
			if((EventType.MODIFY).equals(ActionType))
			{
				if( "fim".equals(moduleName) && "true".equals(isTabularFieldsConfigured) && !"tabularModify".equals(request.getParameter(FieldNames.FROM_WHERE))){%>				<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
					<input type="hidden" name="<%=FieldNames.FROM_WHERE%>" value="tabularFieldsPage">
					<input type="hidden" name="idVal" value="<%=idVal%>">
        		<%}%>																																				<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
					<input type="hidden" name="updateDocumentOnly" value="1">
		   <% }%>
		   
					<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=ActionType%>">
			<%
				if("fim".equals(moduleName))
				{
			%>
					<input type="hidden" name="triggerFormName" value="<%=PortalUtils.filterValue(tabName1)%>">
					<input type="hidden" name="allManipulator" value="com.appnetix.app.portal.FimThirdPartyUpload">
					<input type="hidden" name="allManipulator" value="com.appnetix.app.portal.fim.FimCampaignCheckBoxManipulator">
					<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="fim-fimmanipulator">
			<%
				} else {//P_Enh_Sync_Fields starts %>
					<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="common-commonSyncFieldManipulator">
				<%} //P_Enh_Sync_Fields ends
			%>
				<%
				if("entity".equals((String) session.getAttribute("showEntityTabs")) && "yes".equals((String)request.getAttribute("isMuEntityCustomTab"))){%>
				<input type="hidden" name="<%=currentModuleInfo.get("field-name")%>" value="<%=(String) session.getAttribute("entityID")%>">
				<%}else{ %>
				<input type="hidden" name="<%=currentModuleInfo.get("field-name")%>" value="<%=(String)session.getAttribute(currentModuleInfo.get("entityField"))%>">
				<%} %>
				<%-- P_SCH_ENH_008 Starts --%>
				<% if(MODULE_NAME.NAME_SCHEDULER.equals(moduleName)) {
					String contactID=NewPortalUtils.getColumnFromTable("JOBS","CONTACT_ID","JOB_ID",(String)session.getAttribute(currentModuleInfo.get("entityField"))); %>
					<input type="hidden" name="contactID" value="<%=contactID%>">
				<% }%>
				<%-- P_SCH_ENH_008 Ends --%>
			
				</td>
				</tr>
				</table>
			</td>
		    <td width="6" class="text">
		     <%if("cmContactDetails".equals(tableAnchor) || "cmLeadDetails".equals(tableAnchor)){%>
		    
		    <iframe name="inFrame" frameborder="0" id="inFrame" src="cmAddLeadIframe?cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>&fromPreview=yes&formName=<%=formName%>" width="0" height="0"></iframe>
			<%} %>
		    <img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>
		</table>
		</form>
		<%
		}
		%>
		<% } %>
		
		<% if("captivate".equals(subModuleName)) { //PP_changes starst%>
		<form name='customTabsForm' method='post'>
			<input type='hidden' name='moduleName' value="<%=request.getParameter("moduleName")%>">
			<input type='hidden' name='customTabKey' value="<%=request.getParameter("customTabKey")%>">
		</form>
		<% } //PP_changes ebds%>
		
		<form action="genericHandler" method="POST" name="deleteDocForm" <%=Constants.FORM_ENCODING%>>
			<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=currentModuleInfo.get("DocumentTableAnchor")%>">
			<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="modify">
			<input type="hidden" name="<%=currentModuleInfo.get("documentId")%>" value=""><%--ENH_MODULE_CUSTOM_TABS--%>
			<input type="hidden" name="<%=currentModuleInfo.get("DOCUMENT_ATTACHMENT")%>" value=" ">
			<input type="hidden" name="DocumentName" value=" " >
			<input type="hidden" name="<%=FieldNames.PROCESSING_CLASS%>" value="<%=currentModuleInfo.get("PROCESSING_CLASS")%>">
			<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="common-documentmanipulator">
			<input type="hidden" name="<%=BuilderFormFieldNames.ID_FIELD%>" value="<%=request.getParameter(BuilderFormFieldNames.ID_FIELD)%>">
			<input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="tabreturnpage">
            <%if("captivate".equals(subModuleName)) {%>
            <input type="hidden" name="<%=FieldNames.RETURN_PAGE_TAB%>" value="addModuleCustomTab?tableAnchor=<%=tableAnchor%>&showPopUp=Y&fromCaptivate=Y&fromOuterPortal=<%=request.getParameter("fromOuterPortal") %>">
            <input type="hidden" name="moduleName" value="fs_captivate">
            <%} else { %>
            <input type="hidden" name="<%=FieldNames.RETURN_PAGE_TAB%>" value="addModuleCustomTab?tableAnchor=<%=tableAnchor%>">
            <input type="hidden" name="moduleName" value="<%=moduleNameCustom%>"><%--signal-20150601-011 --%>
            <%} %>
            <input type="hidden" name="from" value="<%=request.getParameter("from")%>">
            <%if("fim".equals(moduleName) && "true".equals((String)session.getAttribute("isTabularFieldsConfigured"))) {%>					<!-- P_Enh_Multiple_Input_Tabular_Summary starts -->
				<input type="hidden" name="idVal" value="<%=idVal%>">
			<%} %>																															<!-- P_Enh_Multiple_Input_Tabular_Summary ends -->
		</form>	
		<script src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/aaPreferredLocation.js"></script>
		<script>
			ajaxAnywherePefLoc.formName = "preferredLocationForm";
		</script>
		<form method="POST" name="preferredLocationForm" id="preferredLocationForm" action="preferredLocation" <%=Constants.FORM_ENCODING%>>
		</form>
		
		<script>
		//this script is for only franchise Sales > lead Primary info starts
		var flagAward = "true",hideSpan = "false";
		var pageLoad=true;
		var otherLeadSourceLabel = "<%=LanguageUtil.getString("Other Lead Sources")%>";
		var detailsLabel = "<%=LanguageUtil.getString("Details")%>";
		$(document).ready(function(){
			otherLeadSources();
		});
		$(window).bind("load",function(){
        if(flagAward == "true")
        {
            hideFranchiseAwarded();
        }
        else
        {
            showFranchiseAwarded();
        }
        if(hideSpan == "true")
        {
            hideFAwardCheckBox();
        }
        if(document.getElementById('coAppCheck')) {
            $('#coAppCheck').parent().parent().hide();
        }
    });

		function brokerSelect(Str)
		{
		    var f = document.leadPrimaryInfoForm;
		    var td = document.getElementById("otherLeadSources");
		    var selectedTextSource3 = f.leadSource3ID.options[f.leadSource3ID.selectedIndex].text;
		    var leadSource2=f.leadSource2ID.value;
		    var selectedName= f.leadSource2ID.options[f.leadSource2ID.selectedIndex].text;
		    if ((leadSource2=='7' || leadSource2.indexOf('B7_')!=-1) || f.leadSource3ID.value=='599' || f.leadSource3ID.value=='11' || f.leadSource3ID.value=='15' || f.leadSource3ID.value=='27')
		    {
		        td.innerHTML = selectedName +" Details";
		    } else
		    {
		        td.innerHTML = " ";
		        document.getElementsByName("otherLeadSourceDetail")[0].style.display="none";
		        document.getElementsByName("otherLeadSourceDetail")[0].disabled="disabled";
		        document.getElementById("brokerID").style.display = "none";
		    }
		    var BrokerSel = document.getElementById('BrokerSel');
		    var selectedValue = $('#leadSource2ID').find('option:selected').val();
		    if(selectedValue.indexOf('B7_') != -1) {
		        Str = 'Brokers';
		    }
		    switch (Str){
		        case 'Brokers':
		            BrokerSel.style.display='inline';
		            break;
		        case 'Select':
		            BrokerSel.style.display='none';
		            break;
		        default	:
		            BrokerSel.style.display='none';
		    }
		}

		function hideFranchiseAwarded()
		{
		    var td1 = document.getElementById("franAwardTd");
		    var td2 = document.getElementById("franAwardTd1");
		    if($('#franAwardDiv')) {
		        $('#franAwardDiv').parent().parent().hide();
		    }
		    if($('#franAwardDiv1')) {
		        $('#franAwardDiv1').parent().parent().hide();
		    }
		}
		function showFranchiseAwarded()
		{
		    var td1 = document.getElementById("franAwardTd");
		    var td2 = document.getElementById("franAwardTd1");
		    if(td1)
		    {
		        var tr1 = td1.parentNode;
		        tr1.style.display = '';
		        document.getElementById("franAwardDiv").style.display = "";
		    }
		    if(td2)
		    {
		        var tr2 = td2.parentNode;
		        tr2.style.display = '';
		        document.getElementById("franAwardDiv1").style.display = "";
		    }
		}
		function otherLeadSources()
		{
		    var f = document.leadPrimaryInfoForm;
		    if (f)
		    {
		        var leadSource2=f.leadSource2ID.value;
		        if (leadSource2=='7' || leadSource2.indexOf('B7_')!=-1)
		        {
		            showLeadSources('broker');
		        }
		        var selectedTextSource3 = f.leadSource3ID.options[f.leadSource3ID.selectedIndex].text;
		        if (selectedTextSource3.toLowerCase() == "other" || selectedTextSource3.toLowerCase() == "others")
		        {
		            showLeadSources('other');
		        }
		        var leadSource2=f.leadSource2ID.value;
		        if (!((selectedTextSource3.toLowerCase() == "other" || selectedTextSource3.toLowerCase() == "others") || (leadSource2=='7' || leadSource2.indexOf('B7_')!=-1)))
		        {
		            f.otherLeadSourceDetail.value = '';
		            brokerSelect("");
		        }
		    }
		    if (pageLoad)
		    {
		    	$('#firstName').focus();
		        pageLoad = false;
		    }
		}			
		function showLeadSources(type)
		{
		    var td = document.getElementById("otherLeadSources");
		    if (td)
		    {
		        var tr = td.parentNode;
		        var f = document.leadPrimaryInfoForm;
		        if (type == 'other')
		        {
		            td.innerHTML = "<b>"+otherLeadSourceLabel+"</b>&nbsp;:";
		            document.getElementsByName("otherLeadSourceDetail")[0].style.display = "";
		            document.getElementsByName("otherLeadSourceDetail")[0].disabled = "";
		            document.getElementById("brokerID").style.display = "none";
		        } else
		        {
		            var selectedName= f.leadSource2ID.options[f.leadSource2ID.selectedIndex].text;
		            td.innerHTML = "<b>"+selectedName +"&nbsp;"+detailsLabel+"</b>&nbsp;:";
		            document.getElementsByName("otherLeadSourceDetail")[0].style.display="none";
		            document.getElementsByName("otherLeadSourceDetail")[0].disabled="disabled";
		            document.getElementById("brokerID").style.display="";
		            getComboForBrokerDetails("broker", document.leadPrimaryInfoForm.leadSource3ID.value,document.leadPrimaryInfoForm.leadSource2ID.value, "brokerID", "");
		        }
		    }
		}
		function brokerDetails()
		{
		    var f = document.leadPrimaryInfoForm;
		    var lSourceID = f.leadSource3ID.options[f.leadSource3ID.selectedIndex].value;
		    if(lSourceID=="-1")
		    {
		        FCI.Alerts(FCLang.LEAD_SOURCE_DETAILS,'true');
		        f.leadSource3ID.focus();
		    }else
		    {
		        openJqueryPopUp("brokerAgencyDetailWithOutTiles?from=leadPrimaryInfo&bID="+lSourceID,650,400) ;
		    }
		}
		var isManuallyAddedFS = "<%=MultiTenancyUtil.getTenantConstants().IS_MANUALLY_ADDED_FS%>";
		function hideShowCheckBox(val) {
		    if(isManuallyAddedFS == "4") {
		        if(document.leadPrimaryInfoForm.sendAutomaticMail) {
		            if(val == doubleOptInCountryFS || val == -1) {
		                $('#sendAutomaticMail').attr('checked', "checked");
		                $('#sendAutomaticMail').attr('disabled', false);
		                $('#sendAutomaticMail').parent().parent().show();
		            } else {
		                $('#sendAutomaticMail').attr('disabled', true);
		                $('#sendAutomaticMail').parent().parent().hide();
		            }
		        }
		    }
		}
		function showMandatoryField()
		{
		    var target = document.leadPrimaryInfoForm.primaryPhoneToCall.value;
		    document.getElementById("Work Phone").style.visibility = 'hidden';
		    document.getElementById("Home Phone").style.visibility = 'hidden';
		    document.getElementById("Fax").style.visibility = 'hidden';
		    document.getElementById("Mobile").style.visibility = 'hidden';
		    document.getElementById("Email").style.visibility = 'hidden';
		    if(target)
		    {
		        if(target.value != '-1' && document.getElementById(target))
		        {
		            document.getElementById(target).style.visibility = 'visible';
		        }
		    }
		}
		function enableLeadOwner(check)
		{
		    if(check == "automatic")
		    {
		        document.getElementById('assignUser').checked=false;
		        document.getElementById('leadOwnerID').disabled=true;
		        document.getElementById('automatic').checked=true;
		        if(document.getElementById('assignSameUser'))
		            document.getElementById('assignSameUser').checked=false;
		    } else if(check == "assignSameUser")
		    {
		        document.getElementById('automatic').checked=false;
		        document.getElementById('assignUser').checked=false;
		        document.getElementById('leadOwnerID').disabled=true;
		        if(document.getElementById('assignSameUser'))
		            document.getElementById('assignSameUser').checked=true;
		    }
		    else
		    {
		        document.getElementById('automatic').checked=false;
		        document.getElementById('assignUser').checked=true;
		        document.getElementById('leadOwnerID').disabled=false;
		        if(document.getElementById('assignSameUser'))
		            document.getElementById('assignSameUser').checked=false;
		    }
		}

		var lib_minus = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme1%>/lib_minus.gif";
	    var lib_plus = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme1%>/lib_plus.gif";
	    var letGo=false;
		function showLocation()
		{
		    var targetImage=document.getElementById('locationImage');
		    if(targetImage.src.indexOf("plus") != -1)
		    {
		        targetImage.src = lib_minus;
		        var target = document.getElementById('preferredLocation');
		        target.style.display='inline';
		        if(letGo)
		        {
		        }
		        else
		        {
		            target.innerHTML="<div class='text'>Loading...</div>";
		            letGo=true;
		            ajaxAnywherePefLoc.submitAJAX();
		        }
		    }
		    else
		    {
		        targetImage.src = lib_plus;
		        var target=document.getElementById('preferredLocation');
		        target.style.display='none';
		    }
		}
		function preSubmit(str)
		{
		    var f = document.leadPrimaryInfoForm;
		    var param = "locationNew="+str;
		    if (document.getElementById("templocationId1").value!="-1" && str=="1")
		    {
		        param = param+"&locationId1="+document.getElementById("templocationId1").value;
		    }else if (document.getElementById("templocationId1").value=="-1" && str=="1")
		    {
		        FCI.Messages(FCLang.PLEASE_SELECT_A_LOCATION_FROM_NEW_AVAILABLE_LOCATIONS_TO_VIEW_ITS_DETAILS);
		        return;
		    }
		    if (document.getElementById("templocationId1b").value!="-1" && str=="11")
		    {
		        param = "locationNew=1";
		        param = param + "&locationId1="+document.getElementById("templocationId1b").value;
		    }else if (document.getElementById("templocationId1b").value=="-1" && str=="11")
		    {
		        FCI.Messages(FCLang.PLEASE_SELECT_A_LOCATION_FROM_EXISTING_LOCATIONS_TO_VIEW_ITS_DETAILS);
		        return;
		    }

		    if (document.getElementById("templocationId2").value!="-1" && str=="2")
		    {
		        param = param+"&locationId2="+document.getElementById("templocationId2").value;
		    }else if (document.getElementById("templocationId2").value=="-1" && str=="2")
		    {
		        FCI.Messages(FCLang.PLEASE_SELECT_A_LOCATION_FROM_RESALE_LOCATIONS_TO_VIEW_ITS_DETAILS);
		        return;
		    }
		    openJqueryPopUp("locationDetails?"+param,650,280);
		}
		
		//this script is for only franchise Sales > lead Primary info ends
		<%-- ZC_CM_B_48422 :starts--%>
		function showFranchisee(){
	           var value = get_radio_value('type');
	           if(value=='F'){
	               $('.franTable').show();
	               $('.regionTable').hide();
	           }else if(value=='R' || value=='RO'){
	               $('.franTable').hide();
	               $('.regionTable').show();
	               
	           }else{
	               $('.franTable').hide();
	               $('.regionTable').hide();
	           }

		}
		function get_radio_value(radioName) {
            var inputs = document.getElementsByName(radioName);
            for (var i = 0; i < inputs.length; i++) {
              if (inputs[i].checked) {
                return inputs[i].value;
              }
            }
        }
		<%-- ZC_CM_B_48422 :ends--%>
		function changeMandatoryField(fieldid)
		{
			var fieldValue = fieldid.value;	
			document.getElementById("698Row").style.visibility = 'hidden';
			
		    document.getElementById("699Row").style.visibility = 'hidden';
		    document.getElementById("700Row").style.visibility = 'hidden';
		    document.getElementById("701Row").style.visibility = 'hidden';
		    if(document.getElementById("cmSource3ID") && document.getElementById("cmSource2ID") && document.getElementById("cmSource3ID").value =="-1" && document.getElementById("cmSource2ID").value =="-1"){
		    	document.getElementById("795Row").style.display = "none";
		    }
			if(fieldValue)
			{
				var target = fieldValue+"Row";
				if(target.value != '-1' && document.getElementById(target))
				{
					document.getElementById(target).style.visibility = 'visible';
				}
			}
		}
		function enableGuarantor()
		{    
		 var f = document.<%=formName%>;
			if (f.ownerType[2] &&  f.ownerType[2].checked)
			{
				document.getElementById("inddiv").style.display='';
				document.getElementById("inddiv2").style.display='none';
				document.getElementById("entitydiv").style.display='none';
				f.<%=FieldNames.AREA_ID%>.value="-1";
				f.contactOwnerID3.value="-1";
				f.contactOwnerID2.value="-1";
				//refreshIt();
			}else if(f.ownerType[1] && f.ownerType[1].checked) 
			{
				document.getElementById("inddiv").style.display='none';
				document.getElementById("inddiv2").style.display='';
				document.getElementById("entitydiv").style.display='none';
				f.<%=FieldNames.FRANCHISEE_NO%>.value="-1";
				f.contactOwnerID1.value="-1";
				f.contactOwnerID2.value="-1";
				//refreshIt();
			}
			else
			{
				document.getElementById("inddiv").style.display='none';
				document.getElementById("inddiv2").style.display='none';
				document.getElementById("entitydiv").style.display='';
			}
			


		}
		
		function refreshIt()
		{
			var ifr = frames["inFrame"];
			
			ifr.document.changeForm.<%=FieldNames.CM_SOURCE_2_ID%>.value = document.<%=formName%>.<%=FieldNames.CM_SOURCE_2_ID%>.value;
			if(document.<%=formName%>.franchiseeNo){
				ifr.document.changeForm.franchiseeNo.value = document.<%=formName%>.<%=FieldNames.FRANCHISEE_NO%>.value;
			}
			
			if(document.<%=formName%>.areaID){
			ifr.document.changeForm.areaID.value = document.<%=formName%>.<%=FieldNames.AREA_ID%>.value;
			}
			if(document.<%=formName%>.cmSource3ID)
			{
			ifr.document.changeForm.cmSource3ID.value = document.<%=formName%>.cmSource3ID.value;
			}
			ifr.document.changeForm.fromPreview.value=="yes";
			ifr.document.changeForm.submit();

		}
		
		function refreshIt1(fromReset){   
        var ifr = frames["inFrame"];
                
		if(ifr.document.changeForm && ifr.document.changeForm.<%=FieldNames.CM_SOURCE_2_ID%>){	
		ifr.document.changeForm.<%=FieldNames.CM_SOURCE_2_ID%>.value = document.<%=formName%>.<%=FieldNames.CM_SOURCE_2_ID%>.value;
		}
		
		ifr.document.changeForm.franchiseeNo.value = document.<%=formName%>.<%=FieldNames.FRANCHISEE_NO%>.value;
		
        ifr.document.changeForm.areaID.value = '<%=request.getAttribute("areaID")==null?"":request.getAttribute("areaID")%>';
        if(document.<%=formName%>.cmSource2ID.value!="-1" && document.<%=formName%>.cmSource2ID.value!="22")
        {
	        document.getElementById("795Row").style.display = "";
	        <%if("Y".equals(MultiTenancyUtil.getTenantConstants().CM_PILOT_ENABLED)){%>
	        $("#marketingCodeId").val("");
	        $("#marketingCodeShowValue").val("");
	        <%}%>
        }
        else{
        	document.getElementById("795Row").style.display = "none";
        	<%if("Y".equals(MultiTenancyUtil.getTenantConstants().CM_PILOT_ENABLED)){%>
	        	document.getElementById("895Row").style.display = "none";
	            $("#marketingCodeId").val("");
	            $("#marketingCodeShowValue").val("");
	        <%}%>
        }
              
		ifr.document.changeForm.submit();


	}
		function getFranchiseeMarketingCode(){//pilotCode
	       if($("#cmSource3ID").val()!=-1){
	        	var strUser=$("#cmSource2ID").val()+"_"+$("#cmSource3ID").val();
	            getComboLocalSubSource('localsubsource',strUser,'marketingCodeId','-1','activate','1',$("#franchiseeNo").val());
			    
	    	}
	    }
		
		function getComboLocalSource(){
		       
	    	<%if ("Y".equals(MultiTenancyUtil.getTenantConstants().CM_PILOT_ENABLED)) {%>//pilotCode
	    	var e = document.getElementById("cmSource3ID");
	    	var strUser = e.options[e.selectedIndex].value;
	    	var e1 = document.getElementById("cmSource2ID");
	    	var source2 = e1.options[e1.selectedIndex].value;
	    	strUser=source2+"_"+strUser;
		   	 var f = document.<%=formName%>;
		   	if (!"cmLeadDetailsForm"==document.<%=formName%>){   	
		   	 	if (f.ownerType[2] && f.ownerType[2].checked)
		   	 	{
	               	getComboLocalSubSource('localsubsource',strUser,'marketingCodeId','-1','activate','1',$("#franchiseeNo").val());
		   		}else{
		   	 		getComboLocalSubSource('localsubsource',strUser,'marketingCodeId','-1','activate','0','-1');
		   	 	}
		   	}
			<%}%>
	    	
				}
        function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs)
        {
            openJqueryPopUp('checkRedAlertBuilder?FieldName='+fieldName+'&Status='+status+'&Value='+paramValue+'&paramValue='+privatevalue+'&moduleName='+moduleName+'&pageName='+pageName+'&leadID='+leadID+'&fromWhere='+fromWhere+'&tableAnchorForLogs='+tableAnchorForLogs,600,300);
        }
        function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs)
        {
        	return lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,'false','false');
        }
        function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField)
        {
            if(FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_LOCK_THIS_ENTRY))<%-- ZC_CM_B_43894 --%>
            {
                addHtmlBuilder(fieldName,"locked",privatevalue,paramValue,moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField);
            }
        }
        function addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs)
        {
        	return addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,'false','false')
        }
        function addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField)
        {
            var displayPrivateValue = privatevalue;
            var displayParamValue = paramValue;
            $.post("handleAjaxRequest",{"dataset": "piiPasswordDecrypt","key":displayParamValue,"canAcessSendMail":canAcessSendMail,"isEmailField":isEmailField,"moduleName":moduleName},
            		function(data)
            		{
            	displayParamValue = data;
            if(displayPrivateValue!=null)
            {
                displayPrivateValue = displayPrivateValue.replace(new RegExp("@@SINGLE@","g"),"\'");
                displayPrivateValue = displayPrivateValue.replace(new RegExp("@@DOUBLE@","g"),"\"");
                displayPrivateValue = displayPrivateValue.replace(new RegExp("@@AMP@","g"),"&");
            }
            if(displayParamValue!= null)
            {
                displayParamValue = displayParamValue.replace(new RegExp("@@SINGLE@","g"),"\'");
                displayParamValue = displayParamValue.replace(new RegExp("@@DOUBLE@","g"),"\"");
                displayParamValue = displayParamValue.replace(new RegExp("@@AMP@","g"),"&");
            }
            var displayPrivateValueText =  displayPrivateValue.replace(new RegExp("\"","g"),"&quot;");
            var displayParamValueText =  displayParamValue.replace(new RegExp("\'","g"),"&#39;");
            if(fromWhere == "view")
            {
                var fieldId = fieldName.replace("piiField","");
                if(status == "locked")
                {
                    var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view','"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";
                    document.getElementById(fieldName).innerHTML = displayPrivateValue+"&nbsp;"+displayAdditional;
                    if(unlockedBuilderFields.match(fieldId) == fieldId);
                    {
                        unlockedBuilderFields = unlockedBuilderFields.replace(fieldId,"");
                    }
                }
                else if(status == "unlocked")
                {
                    var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view','"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";
                    document.getElementById(fieldName).innerHTML = displayParamValue+"&nbsp;"+displayAdditional;
                    unlockedBuilderFields =  unlockedBuilderFields + "," + fieldId;
                }

                document.addModifyForm.UnlockedFields.value = unlockedBuilderFields;
            }
            else  if(fromWhere == "modify")
            {
                if(status == "locked")
                {
                    var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify','"+tableAnchorForLogs+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16' vspace='3'   border = '0'  ></a>";

                    var fieldId = fieldName.replace("piiField","");
                    document.getElementById(fieldId).disabled=true;
                    document.getElementById(fieldId).value=displayPrivateValueText;
                    document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
                }
                else if(status == "unlocked")
                {
                    var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify','"+tableAnchorForLogs+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'  vspace='3'  border = '0'  ></a>";
                    var fieldId = fieldName.replace("piiField","");
                    document.getElementById(fieldId).disabled=false;
                    document.getElementById(fieldId).value=displayParamValueText;
                    document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
                }
            }
            		});	
        }
		</script>
	
<%}catch(Exception e ){
	e.printStackTrace();
}%>	
