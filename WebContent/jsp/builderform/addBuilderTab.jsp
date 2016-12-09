
<%@page import="com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "com.appnetix.app.portal.forms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.database.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import = "com.home.FormCustomization.*"%>
<%String pathString="/static"+Constants.STATIC_KEY + "/javascript/string.js";
//System.out.println("pathString " + pathString);%>
<jsp:include page = "<%=pathString%>"/>

<script language="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>
<%=LanguageUtil.getLanguageContent(LanguageUtil.getUserLangForJs((String)session.getAttribute("userLanguage")),(String)session.getAttribute("menuName"),MultiTenancyUtil.getTenantName()) %><%-- MT_PHASE_III_I18n_CHANGES --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/multiple.select.js"></script>
<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="session"/>
<%
try 
	{
		String sTabModuleName		  				= request.getParameter(FieldNames.MODULE);
		String tabCount								=request.getParameter("tabCount");//BB-20150203-259(Builder for tab repositioning ) changes
		request.setAttribute("tabCount",tabCount);//BB-20150203-259(Builder for tab repositioning ) changes
		String tabDisplayName		  				= null;
		String displayName = null;
		String isExportable							= null;
		String isRoleBase							= "false";//P_B_70344
		String tabName 		 		  				= request.getParameter(BuilderFormFieldNames.TAB_NAME);
		String tabAction 	  		  				= request.getParameter(BuilderFormFieldNames.TAB_ACTION);
		String isCustom								= request.getParameter(FieldNames.IS_CUSTOM);
		SequenceMap tabGeneratorMap   				= null;
		String cssClass								= "";
		ArrayList<String> allExistingTabsName	  	= null;
		String subModuleName 						= request.getParameter("subModuleName"); ////PP_changes
		if(!StringUtil.isValidNew(subModuleName)){
			subModuleName=request.getParameter("submoduleName");
		}
		String isReturn 	  		  				= request.getParameter("isReturnModify");
		String canWriteRolesValue="";
		String canViewRolesValue="";
		String headerLabel = "Tab"; //PP_changes starts
		if("captivate".equals(subModuleName)) {
			headerLabel = "Virtual Brochure Form";
		} //PP_changes ebds
		if("yes".equals(isReturn))
		{	
			/* request.setAttribute(BuilderFormFieldNames.TAB_DISPLAY, tabDisplay);
			request.setAttribute(BuilderFormFieldNames.TAB_NAME, tabDisplay);
			request.setAttribute(FieldNames.MODULE, sTabModuleName);
			request.setAttribute(BuilderFormFieldNames.TAB_ACTION,tabAction);
			 */
			 request.setAttribute(FieldNames.MODULE,sTabModuleName);
			builderForm.processFormGeneratorTabData(request);
		}
		else
		{
			tabGeneratorMap 	= (SequenceMap)session.getAttribute("tabGeneratorMap");
			allExistingTabsName = (ArrayList<String>)session.getAttribute("allExistingTabsName");
			
		}
	request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR,"builderModules");
	request.setAttribute(BuilderFormFieldNames.XML_ELEMENT,"module");
	request.setAttribute(BuilderFormFieldNames.XML_KEY,"module-name");
	Object mMap1 = builderForm.processBuilderXmlFileAttributes(request);
	SequenceMap mMap = null;
	if(mMap1 instanceof SequenceMap) 
	{
		mMap = (SequenceMap)mMap1;
	}
	
	String submoduleCombo=null;
	if(!"yes".equals(isReturn))
	{
		//ENH_MODULE_CUSTOM_TABS starts
		
			Info submoduleList = null;
			if("captivate".equals(subModuleName)) { //PP_changes starts
				submoduleList = builderForm.getSubmoduleList("captivate");
			}else if("fim".equals(sTabModuleName)){
				submoduleList = builderForm.getSubmoduleList(sTabModuleName,subModuleName);
			}else{
				submoduleList = builderForm.getSubmoduleList(sTabModuleName);
			}
			
			if("cm".equals(sTabModuleName) || "opportunity".equals(sTabModuleName) || "account".equals(sTabModuleName) || "lead".equals(sTabModuleName)){
				submoduleCombo="<input type='hidden' name='submoduleName'  value='"+subModuleName+"'> <span class='bText12'> "+submoduleList.getString(sTabModuleName)+"</span>";
			}else{
				submoduleCombo=InfoUtil.getComboString("submoduleName",null,submoduleList,"multiList","submoduleName",request.getParameter("submoduleName"));
			}
			
			Info sectionNameList = builderForm.getCaptivateSectionList();
			String sectionCombo = InfoUtil.getComboString("sectionName",null,sectionNameList,"multiList","sectionName",request.getParameter("sectionName")); //PP_changes
			
	//ENH_MODULE_CUSTOM_TABS ends
	String userLevel =  (String)session.getAttribute("user_level");
	String franchiseeNo = (String)session.getAttribute("franchisee_no");
	String getRolesForView="";
	String getRolesForWrite="";
	%>
		
	<script language ="javaScript">

		var tabsNameArr = new Array();
		<%
		/*
			Iterating tab map for:
			1. Name of modified tab.
			2. create array of tab names in javascript.
		*/
		Iterator tabIterator = tabGeneratorMap.keys().iterator();
		Info tabInfo		 = null;
		int countTab 		 = 0;
		while(tabIterator.hasNext())
		{
			tabInfo = (Info)tabGeneratorMap.get(tabIterator.next());
			
			// if condition to get the tab name in modify case
			if(tabInfo != null && tabInfo.getString(BuilderFormFieldNames.TAB_NAME).equals(tabName))
			{
				tabDisplayName 	=  tabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY);
				displayName = tabInfo.getString(BuilderFormFieldNames.DISPLAY_NAME);
				isExportable	=  tabInfo.getString(BuilderFormFieldNames.TAB_IS_EXPORTABLE);
				canViewRolesValue	=  tabInfo.getString(BuilderFormFieldNames.CAN_VIEW_ROLES);
				canWriteRolesValue	=  tabInfo.getString(BuilderFormFieldNames.CAN_WRITE_ROLES);
				//P_B_70344 starts
				if(StringUtil.isValidNew(canViewRolesValue) && StringUtil.isValidNew(canWriteRolesValue)){
					isRoleBase = "true";
				}
				//P_B_70344 ends
			}
			
		%>
			tabsNameArr[<%=countTab%>] = '<%=StringEscapeUtils.escapeJavaScript(tabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY))%>';
		<%
			countTab++;
		}
		%>
				
		window.onload = function()
		{
			document.fieldModify.<%=BuilderFormFieldNames.TAB_DISPLAY_NAME%>.focus();
			var height = document.getElementById("containerDiv").offsetHeight;
		}
		function validate() 
		{
			var f		   = document.fieldModify;
			var tabNameObj = f.<%=BuilderFormFieldNames.TAB_DISPLAY_NAME%>;
	        if (isEmpty(trim(tabNameObj.value))) 
	        {
	        	FCI.Alerts(FCLang.DISPLAY_NAME);
	        	tabNameObj.focus();
			    return false;
			}

	        <%
	        //ENH_MODULE_CUSTOM_TABS starts
	        if("create".equals(tabAction)){%>
		        if (f.submoduleName.value==-1) 
		        {	
		        	//FCI.Alerts("Sub Module Name");   // CM_B_35386
                    FCI.Messages(FCLang.PLEASE_SELECT_SUB_MODULE_NAME);
		        	//alert("Please select Sub Module Name.");  //P_B_45313
		        	f.submoduleName.focus();
				    return false;
				}

				if(f.sectionName && f.sectionName.value == -1) { //PP_changes starts
					alert("Please select Section Name");
					f.sectionName.focus();
				    return false;
				} //PP_changes ends
			<%}
			//ENH_MODULE_CUSTOM_TABS ends
			%>

			if (trim(tabNameObj.value) != "") 
			{
				var firstChar = trim(tabNameObj.value).substring(0, 1);
				var regExp = new RegExp("[a-zA-Z]");
				if (!firstChar.match(regExp)) 
				{
					FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
					tabNameObj.focus();
					return false;
				}
			}
	       	if (trim(tabNameObj.value) != "") 
	       	{
				var fieldChar = trim(tabNameObj.value);
				var regExp = new RegExp("[!|@%#$^&*_:,;<>\\\\]");
				if (fieldChar.match(regExp)) {
					
					FCI.Messages(FCLang.PLEASE_REMOVE_THE_SPECIAL_CHARACTER);    // CM_B_35380

					tabNameObj.focus();
					return false;
				}
			}
	<%-- 		var spacefix = / /gi;
			var value=toTitleCase(dpname).replace(spacefix,"");
			var dbValue=replaceAllSpecialCharWith(trim(dpname),"").replace(/ +/g,"_");
			var fldSname = "_"+revCapitalize(value);  	//value.toLowerCase();
			var mailmergekeyword = "<%=val%>"+"_"+value.toLowerCase(); --%>

			var isDuplicate;
			for(tabNameCount = 0; tabNameCount < tabsNameArr.length; tabNameCount++)
			{
				if(tabsNameArr[tabNameCount] == tabNameObj.value )
				{
					<%
						if(!(tabDisplayName == null))// for modify case
						{
					%>
							if(!(tabNameObj.value == '<%=StringEscapeUtils.escapeJavaScript(tabDisplayName)%>'))	<%-- CM_B_37550 : 05/05/2014 : Veerpal Singh --%>
							{
								isDuplicate = true;
							}
					<%
						}
						else		// for create case
						{
					%>
							isDuplicate = true;
					<%
						}
					%>
					if(isDuplicate)
					{
						alert("Tab already exist for this module.");
						tabNameObj.focus();
						return false;
					}
				}
			}
			 var isExportable="";
			 var selected = $("input[type='radio'][name='<%=BuilderFormFieldNames.TAB_IS_EXPORTABLE%>']:checked");
			 if (selected.length > 0) {
				 isExportable = selected.val();
			 }
			 //P_B_70344 starts
			 <%if(!StringUtil.isValid(isCustom) && ("fs".equals(sTabModuleName) || "fim".equals(sTabModuleName)  || "cm".equals(sTabModuleName) || "lead".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName))){%>
			 if(f.roleBase && f.roleBase[0].checked){
				 if(f.viewRoles){
				 f.viewRoles.value="";
				 }
				 if(f.writeRoles){
				 f.writeRoles.value="";
				 }
			 }else{
				 if(f.viewRoles){
				 f.viewRoles.value=$('input[name="selectItemviewRoles1"]:checked').map(function () {return this.value;}).get().join(",");
				 }
				 if(f.writeRoles){
				 f.writeRoles.value=$('input[name="selectItemwriteRoles1"]:checked').map(function () {return this.value;}).get().join(",");
				 }
			 }
		<%}%>
			 //P_B_70344 ends
		//	if((tabNameObj.value == '<StringEscapeUtils.escapeJavaScript(tabDisplayName)%>') && (isExportable=='<StringEscapeUtils.escapeJavaScript(isExportable)%>'))
			//	{
			//			closeBox();
			//			return false;
			//	}
			<%
			//ENH_MODULE_CUSTOM_TABS starts
			if("create".equals(tabAction)){%>
				f.tabAnchor.value = getFormAnchor(tabNameObj.value,55);
				f.tableName.value = getFormTable(tabNameObj.value,55);
			<%}
			//ENH_MODULE_CUSTOM_TABS ends
			%>
	        f.submitButton.disabled=true;
	        return true;
	        //f.submit();
	    }
	
		function toTitleCase(str)
		{
		    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
		}
		
		function revCapitalize(text) {
		    return text.charAt(0).toLowerCase() + text.slice(1);
		}
	</script>
	<%
	
	getRolesForView=CommonUtil.getRolesForViewAndWrite(sTabModuleName,userLevel,franchiseeNo,"viewRoles1",canViewRolesValue);//P_B_70344
	getRolesForWrite=CommonUtil.getRolesForViewAndWrite(sTabModuleName,userLevel,franchiseeNo,"writeRoles1",canWriteRolesValue);//P_B_70344
	%>
	<html>
	<body topmargin="0" leftmargin="0">
	<title><%=LanguageUtil.getString("Form Tab Configuration", (String) session.getAttribute("userLanguage"))%>
	</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String) session.getAttribute("userTheme")%>/style.css" type="text/css">
	<%@ include file = "builderScriptCss.jsp" %>
	<div id="containerDiv">
	<form name="fieldModify" onsubmit="return validate()" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="<%=BuilderFormFieldNames.TAB_ACTION%>" value="<%=tabAction%>">
	<input type="hidden" name="<%=BuilderFormFieldNames.TAB_NAME%>" value="<%=tabName%>">
	<input type="hidden" name="oldTabDisplayVal" value="<%=PortalUtils.filterValue(tabDisplayName)%>">
	<input type="hidden" name="originalDisplayName" value="<%=PortalUtils.filterValue(displayName) %>">
	<input type="hidden" name="<%=FieldNames.MODULE%>" value="<%=sTabModuleName%>">
	<input type="hidden" name="isReturnModify" value="yes">
	<input type="hidden" name="<%=FieldNames.IS_CUSTOM%>" value="<%=isCustom%>">
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>"> <%--//PP_changes --%>
	<input type="hidden" name="tabCount" value="<%=tabCount%>"><!--BB-20150203-259(Builder for tab repositioning ) changes  -->
	<%
	//ENH_MODULE_CUSTOM_TABS starts
	if("create".equals(tabAction)){%>
		<input type="hidden" name="tabAnchor" value="">
		<input type="hidden" name="tableName" value="">
	<%}
	//ENH_MODULE_CUSTOM_TABS ends
	%>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
	   			 <td  width="100%"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="30"></td>
	   			 <td width="200%" height="20" align="right" class="bText11gr" style="padding-right:25px">
							<%=LanguageUtil.getString("Fields marked with", (String) session.getAttribute("userLanguage"))%>
							<span class="urgent_fields"> *</span> <%=LanguageUtil.getString("are mandatory.", (String) session.getAttribute("userLanguage"))%>
						</td>
    		</tr>
			<tr>
			<td colspan="2" width="100%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						
						
						
					</tr>
				</table>
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<%-- <td width="10" valign="top">
							<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl-fill.png" width="10" height="12" />
						</td>
						<td width="475">
						</td>
						<td width="15" align="right" valign="top">
							<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png" width="16" height="12" />
						</td> --%>
					</tr>
					
					<tr>
						<!-- <td class="crvBox2-left"></td> -->
						<td>
							<table width="100%" border="0" cellspacing="1" cellpadding="4">
			                    <tr>
			                        <td colspan="2" class="fline hText18theme pt pb5">
                                        <%="modify".equals(tabAction)?LanguageUtil.getString("Modify"):LanguageUtil.getString("Add")%> <%=LanguageUtil.getString(headerLabel)%> <%--//PP_changes --%>
			                        </td>
			                    </tr>   
			                    <tr>
									<td class="bText12b" align="right" width="50%"><%=LanguageUtil.getString("Module")%> :</td>
									<td class="text" width="50%">
											<%
											if(mMap != null) 
											{
												Iterator it = mMap.keys().iterator();
												while(it.hasNext()) 
												{
													String key = (String)it.next();
													SequenceMap sMap = (SequenceMap)mMap.get(key);
													String module = (String)sMap.get("module-name");
													String moduleD = (String)sMap.get("module-display");
													moduleD = LanguageUtil.getString(moduleD,null,NewPortalUtils.getModuleKey(moduleD));
													if("mu".equals(sTabModuleName) || "area".equals(sTabModuleName)){
														sTabModuleName="fim";
													}
											%>
													<%=module.equals(sTabModuleName)?moduleD:""%>
											<% 	} 
											}
											%>
									</td>
								</tr>
					             <tr >
									<td class="bText12b" align="right" width="50%">
					                     <span class="urgent_fields">*</span><%=LanguageUtil.getString("Display Name")%> :<%--P_B_CM_35418 --%>
									</td>
									<td width="50%">
										<input maxlength="255" id="<%=BuilderFormFieldNames.TAB_DISPLAY_NAME%>" name="<%=BuilderFormFieldNames.TAB_DISPLAY_NAME%>" value="<%="modify".equals(tabAction)?PortalUtils.filterValue(tabDisplayName):""%>" class="fTextBox" style="width: 250px;padding:2px; clear: both;" type="text">
									</td>
								</tr>
								<%
									if("create".equals(tabAction))
									{
								%>
								<%--ENH_MODULE_CUSTOM_TABS starts--%>
								<tr>
									<td class="bText12b" align="right" width="50%">
					                     <span class="urgent_fields">*</span><%=LanguageUtil.getString("Sub Module Name")%> :<%--P_B_CM_35418 --%>
									</td>
									<td width="50%" class="bText12">
										<%=submoduleCombo%>
									</td>
								</tr>
								<% if("captivate".equals(subModuleName)) { //PP_changes starts%>
									<tr>
										<td class="bText12b" align="right" width="50%">
					                    	 <span class="urgent_fields">*</span><%=LanguageUtil.getString("Section Name")%> :<%--P_B_CM_35418 --%>
										</td>
										<td width="50%" class="bText12">
											<%=sectionCombo%>
										</td>
									</tr>
								<% } //PP_changes ends%>
								<%--ENH_MODULE_CUSTOM_TABS ends--%>
										<tr>
											<td class="bText12b" align="right" width="50%">
							                 <%--   <span class="urgent_fields">*</span><%=LanguageUtil.getString("Is Active")%>: --%>    <%--   P_B_ADMIN_CM_35384 --%>
							                     <%=LanguageUtil.getString("Is Active")%> :<%--P_B_CM_35418 --%>
											</td>
											<td class="text" width="50%">
												<table border="0">
													<tr>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_ACTIVE%>" type="radio" value="Y" checked="checked">&nbsp;<%=LanguageUtil.getString("Yes")%>
														</td>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_ACTIVE%>" type="radio" value="N">&nbsp;<%=LanguageUtil.getString("No")%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
								<%
									}
								if(!StringUtil.isValid(isCustom) && !"captivate".equals(subModuleName)) //PP_changes
								{
								%>
									<tr>
										<td class="bText12b" align="right" width="50%">
						                 <%--    <span class="urgent_fields">*</span><%=LanguageUtil.getString("Is Exportable")%>: --%>      <%--   P_B_ADMIN_CM_35384 --%>
										     <%=LanguageUtil.getString("Is Exportable and Searchable")%> :<%--P_B_CM_35418 --%>
										</td>
										<td class="text" width="50%">
											<table border="0">
												<tr>
													
													<%
													if(!StringUtil.isValid(isExportable) || "true".equals(isExportable))
													{
													%>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_EXPORTABLE%>" type="radio" value="true" checked="checked">&nbsp;<%=LanguageUtil.getString("Yes")%>
														</td>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_EXPORTABLE%>" type="radio" value="false">&nbsp;<%=LanguageUtil.getString("No")%>
														</td>
													<%
													}
													else if("false".equals(isExportable))
													{
													%>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_EXPORTABLE%>" type="radio" value="true">&nbsp;<%=LanguageUtil.getString("Yes")%>
														</td>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.TAB_IS_EXPORTABLE%>" type="radio" value="false" checked="checked">&nbsp;<%=LanguageUtil.getString("No")%>
														</td>
													<%
													}
													%>
												</tr>
											</table>
										</td>
									</tr>
							<%
							}
							if("create".equals(tabAction) && !"captivate".equals(subModuleName)) //PP_changes
							{
							%>
									<tr >
										<td class="bText12b" align="right" width="50%">
						              <%--        <span class="urgent_fields">*</span> <%=LanguageUtil.getString("Accept multiple form inputs")%>: --%>      <%--   P_B_ADMIN_CM_35384 --%>
										  <%=LanguageUtil.getString("Accept multiple form inputs")%> :<%--P_B_CM_35418 --%>
										</td>
										<td class="text" width="50%">
											<table border="0">
												<tr>
														<td class="text">
                                                                                                                    <input name="<%=BuilderFormFieldNames.ADD_MORE%>" type="radio" value="true" <%=((!StringUtil.isValid(isExportable) || "true".equals(isExportable))?"checked=\"checked\"":"")%>>&nbsp;<%=LanguageUtil.getString("Yes")%>
														</td>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.ADD_MORE%>" type="radio" value="false" <%=(("false".equals(isExportable))?"checked=\"checked\"":"")%>>&nbsp;<%=LanguageUtil.getString("No")%>
														</td>
												</tr>
											</table>
										</td>
									</tr>
							  <%
							}if(!StringUtil.isValid(isCustom) && ("fs".equals(sTabModuleName) || "fim".equals(sTabModuleName) || "cm".equals(sTabModuleName) || "lead".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName))){
							  %>
							  <%--P_B_70344 starts--%>
							  <tr>
										<td class="bText12b" align="right" width="50%"><%=LanguageUtil.getString("Do you want to make Tab accessible to all")%> :
										</td>
										<td class="text" width="50%">
											<table border="0">
												<tr>
														<td class="text">
                                                        	<input name="<%=BuilderFormFieldNames.ROLE_BASE%>" onclick='javascript:showHideRoles()' type="radio" value="true" <%="false".equals(isRoleBase)?"checked=\"checked\"":""%>>&nbsp;<%=LanguageUtil.getString("Yes")%>
														</td>
														<td class="text">
															<input name="<%=BuilderFormFieldNames.ROLE_BASE%>" onclick='javascript:showHideRoles()' type="radio" value="false" <%=!"false".equals(isRoleBase)?"checked=\"checked\"":""%>>&nbsp;<%=LanguageUtil.getString("No")%>
														</td>
												</tr>
											</table>
										</td>
									</tr>
							  <tr id ="rolesTd" <%="false".equals(isRoleBase)?"style=\"display:none\"":""%>>
							  <%--P_B_70344 ends--%>
							  <td class="bText12b" align="right" width="50%">
							  <%=LanguageUtil.getString("Can View") %> :
							  </td>
							  <td>
							  <%=getRolesForView%>
							  </td>
							  </tr>
							  <tr id ="rolesTd" <%="false".equals(isRoleBase)?"style=\"display:none\"":""%>><%--P_B_70344--%>
							  <td class="bText12b" align="right" width="50%">
							  <%=LanguageUtil.getString("Can Write") %> :
							  </td>
							  <td>
							  <%=getRolesForWrite%>
							  </td>
							  </tr>
							  <%}%>
							  <tr <%=(("create".equals(tabAction))?"":"")%>>
					            <% String sCharVal = "**"+LanguageUtil.getString("Avoid using special characters like")+" ! @ % # $ ^ & * _ : , ; < > \\"; %>
							    <td class="TextLbl_b" colspan="2"><font color="#ff6600"><i><%=sCharVal%></i></font>
							    </td>
							 </tr>
						</table>
				
					</td>
					<!-- <td class="crvBox2-right"></td> -->
				</tr>
				<tr height="20">
				<td></td>
					
				</tr>
			</table>
		
			<table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
				<tr>
					<td colspan="2" align="left" class="text">
						<input id="submitButton" name="submitButton" value="<%=LanguageUtil.getString("Save")%>" class="cm_new_button" type="submit" style="margin-right:5px;"/>
						<input id="Button" name="Button" value="<%=LanguageUtil.getString("Close")%>" class="cm_new_button" type="reset" style="margin-right:5px;" onclick="javascript:closeBox()" />
					</td>
				</tr>
			</table>
		</table>
		<input type="hidden" name="cft" value="<%=(String)session.getAttribute("csrfToken")%>">
		<input type="hidden" name="viewRoles" value=""><%--P_B_70344--%>
		<input type="hidden" name="writeRoles" value=""><%--P_B_70344--%>
	</form>
	</div>
	</body>
	</html>
<%
	}
%>
	<script language="javascript">
	function closeBox()
	{
		if(parent.$.fn.colorbox)
		{
			parent.$.fn.colorbox.close();
		}
		else
		{
			window.close();
		}
	}
		<%
		if("yes".equals(isReturn))
		{
		%>
				function closeWindow() 	
				{
					if (parent && parent.document.blankForm) 	
					{
						parent.document.blankForm.<%=FieldNames.MODULE%>.value = "<%=request.getParameter(FieldNames.MODULE)%>";
						parent.document.blankForm.subModuleName.value = "<%=subModuleName%>";
						parent.document.blankForm.submit();
					}
				}
				closeWindow();
		<%
		}
		//ENH_MODULE_CUSTOM_TABS starts
		%>
		var getSelectAllValOnly = false;
		var getSelectAllWithAllVal = false;
		var getSelectAllBlankValue = new Array();
		$(document).ready(function(){ 
			parent.$('#cboxLoadingGraphic').hide();
			parent.$.fn.colorbox.vkResize($(document).width(), $(document).height()+240); //PP_changes
			jQuery('.form-control').multipleSelect({ 
		  	filter: true,
		    		placeholder:"<%=LanguageUtil.getString("Select")%>"
		   		});
			
		});
		<%if("create".equals(tabAction)){%>
			function getFormAnchor(tabName,maxLength)
			{
				var value=toTitleCase(string_to_slug(trim(tabName))).replace(/[^a-zA-Z0-9_]/g, "");
				if(value)
				{
					value = value.replace(new RegExp("[!@%#$^&*_:,;<>\\\\]",'g'),"");
				}
				var fldSname = revCapitalize(value);
				fldSname = replaceAllSpecialCharWith(fldSname,"").substring(0,maxLength-11);
				return fldSname;
			}
	
			function getFormTable(tabName,maxLength)
			{			
				var dbValue=string_to_slug(trim(tabName)).replace(/ +/g,"_").replace(/[^a-zA-Z0-9_]/g, "");
				if(dbValue)
				{
					dbValue = dbValue.replace(new RegExp("[!@%#$^&*_:,;<>\\\\]",'g'),"");
				}
				var fldDname = dbValue.toUpperCase();
				fldDname = replaceAllSpecialCharWith(fldDname,"").substring(0,maxLength-11);
				return fldDname;
			}
			function string_to_slug(str) 
			{
				  str = str.toLowerCase();
				
				// remove accents, swap � for n, etc
				  var from = "��������������������������������";
				  var to   = "aaaaaaaeeeeiiiiooooooouuuuncyyop";
				  for (var i=0, l=from.length ; i<l ; i++) {
				    str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
				  }
				  
				  return str;
			}
		<%}
		//ENH_MODULE_CUSTOM_TABS ends
		%>
		<%--P_B_70344 starts--%>
		function showHideRoles(){
			
			var f = document.fieldModify;
			if(f.roleBase[0].checked){
				$("[id^=rolesTd]").hide();
			}else{
				$("[id^=rolesTd]").show();
			}
		}
		<%--P_B_70344 ends--%>
	</script>
	
<%
} 
catch(Exception e) 
{
	e.printStackTrace();
}
%>
