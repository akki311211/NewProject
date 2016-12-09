<!--
* @version 1.1
* date 25-09-2004
* Comments: Jsp Created for ENH_MODULE_CUSTOM_TABS

P_B_CM_40321				30/06/2014			Chhaya Pathania		bug			GUI issue
P_CM_B_40919                8th July            Ikramuddin                      There Should be no hyper linked or clickable content in the
P_CM_B_41166				25july2014			Rajat Gupta						Header of the tab not visible.
P_CM_B_43893				14Aug2014			Rajat Gupta						Header of the tab not visible.
P_B_CM_44064		20 August 2014		Anshika Jain		Back button added
BOEFLY_INTEGRATION			22/08/2014		Veerpal Singh		Enh				A third party integration with Boefly through REST-API for lead sync.
 -->
 

<%@page import="com.appnetix.app.portal.role.UserRoleMap"%>
<%@ page import = "com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%@page import="com.appnetix.app.util.ModuleUtil.MODULE_NAME"%>
<%@page import="com.home.builderforms.BuilderFormFieldNames"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "com.appnetix.app.util.database.*"%>


<%@ page import = "java.util.Iterator"%><%-- BOEFLY_INTEGRATION --%>
<%if("Y".equals(request.getParameter("showPopUp"))){ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<%} %>
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/jqueryPopUp.js"></script>
<link type="text/css" media="screen" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/smartconnect/colorbox1.css" />
<%--ZC_CM_B_44200 :starts --%>
<%if("Y".equals(request.getParameter("showPopUp"))){%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/smartconnect/colorbox.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/Blue/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">

<%} %>
<%--ZC_CM_B_44200 :ends --%>
<%@ taglib uri="/artags" prefix="ar" %>
<%String conxtname	= request.getContextPath(); //P_FS_Enh_BuilderForm%>
<% 
Boolean singleTabularSection=false;
if(StringUtil.isValidNew((String)request.getAttribute("singleTabularSection")) && "true".equals((String)request.getAttribute("singleTabularSection"))){
singleTabularSection=true;
}
Info dataInfo = (Info)request.getAttribute("dataInfo");
DocumentMap[] docMaps = (DocumentMap[])request.getAttribute("docMaps");
SequenceMap documentMap = (SequenceMap)request.getAttribute("documentMap");
String moduleName = (String)request.getAttribute("moduleName");
String subModuleName = (String)request.getAttribute("subModuleName");
String moduleNameCapital	= (moduleName!=null)?moduleName.toUpperCase():FieldNames.EMPTY_STRING;
Info currentModuleInfo = (Info)request.getAttribute("currentModuleInfo");
String buttonPriveleges = (String)request.getAttribute("buttonPrivelege");
String tabDisplay			= (String)request.getAttribute("tabDisplay");
String addModifyForm = (String)request.getAttribute("addModifyFormStr");
String fimFranchiseeNo	= (String)session.getAttribute("fimFranchiseeNo");
String tableAnchor = (String)request.getAttribute("tableAnchor");
String sectionDetailString = (String)request.getAttribute("sectionDetailString");
String headerAsTitle = (String)request.getAttribute("headerAsTitle");
String boeflyUserId = (String)request.getAttribute("BOEFLY_USER_ID");
String activityId = (String)request.getParameter("activityId"); //PP_changes
String activityName = "";
if(!ModuleUtil.zcubatorImplemented())
	activityName = SQLUtil.getColumnValue("FRANCHISE_GRANTING_ACTIVITY", "ACTIVITY_NAME", "ACTIVITY_ID", activityId); //PP_changes
	
boolean noActiveField=false;
if(request.getAttribute("noActiveField")!=null)
	noActiveField=(Boolean)request.getAttribute("noActiveField");

boolean isTabularSummary = false;															//P_Enh_Multiple_Input_Tabular_Summary starts
if(request.getAttribute("isTabularSummary")!=null){
	isTabularSummary=(Boolean)request.getAttribute("isTabularSummary");
}
String tabularPagingUrl = (String)request.getAttribute("tabularPagingUrl");
if(!StringUtil.isValidNew(tabularPagingUrl)) {
	tabularPagingUrl = "";
}
//P_Enh_Multiple_Input_Tabular_Summary ends

//For Bug 41302 starts
int outlookPageId = request.getParameter("outlookPageId") != null ? Integer.parseInt(request.getParameter("outlookPageId")) : 1 ;
String pagePath =  StrutsUtil.getPath();
//For Bug 41302 end

String tabName = "";
Info moduleInfo = null;

if(tableAnchor == null) {
	tableAnchor = request.getParameter("tableAnchor");
}
String bPrint=request.getParameter("bPrint");
String deleteFormName = "deleteTabRecord";
if(MODULE_NAME.NAME_FIM.equals(moduleName)){
	deleteFormName = "deleteForm";
}
String str = "printPageViewP()";//For Bug 41302
String buttons = "Print";
if("Y".equals(request.getParameter("showPopUp"))){
	str = "window.close()";
	buttons = "Close";
	if("Y".equals(request.getParameter("fromCaptivate"))) { //PP_changes starts
		buttons = "Modify,Close";
		str = "modifyVirtualBrochureForm(\""+tableAnchor+"\"),parent.$.fn.colorbox.close()";
		buttonPriveleges = "true,true";
	} //PP_changes ends
}
else if("yes".equals(request.getAttribute("enableAddMore")))
{
	if("yes".equals((String)request.getSession().getAttribute("forArchived")) && MODULE_NAME.NAME_FS.equals(moduleName)) {
		str = "printPageViewP()";//For Bug 41302
		buttons = "Print";	
	}
	//P_B_44064 starts
	/*else if(MODULE_NAME.NAME_CM.equals(moduleName)){//P_CM_B_70272
		str = "printPageViewP(),window.location=\"cmLeadPrimaryInfo?DetailsleadId=Details\"";//For Bug 41302 //P_CM_B_61785 
		buttons = "Print,Back";
	}else if(MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName)){
		str = "printPageViewP(),window.location=\"cmOpportunityDetail\""; 
		buttons = "Print,Back";
	}else if(MODULE_NAME.NAME_ACCOUNT.equals(moduleName)){
		str = "printPageViewP(),window.location=\"leadCompanyAction\""; 
		buttons = "Print,Back";
	}else if(MODULE_NAME.NAME_LEAD.equals(moduleName)){
		str = "printPageViewP(),window.location=\"cmLeadDetail\""; 
		buttons = "Print,Back";
	}else*/
	
	if(singleTabularSection){
		str = "printPageViewP()";
		buttons = "Print";		
	}
	//P_B_44064 ends
	else {
		str = "addMoreFormBuilder(\"" + tableAnchor + "\"), printPageViewP()";//For Bug 41302
		buttons = "Add More,Print";	
		if((MODULE_NAME.NAME_FIM.equals(moduleName) || MODULE_NAME.NAME_FS.equals(moduleName))&& "tabularFieldsPage".equals(request.getParameter("fromWhere"))) {						//P_Enh_Multiple_Input_Tabular_Summary starts
			buttons += ",Back";
			str += ",tabularBack()";
			buttonPriveleges += ",true";
		}																																	//P_Enh_Multiple_Input_Tabular_Summary ends
	}
}
if(!StringUtil.isValid(addModifyForm))
	addModifyForm="";

Info fimBaseDetailInfo = null;

if(MODULE_NAME.NAME_FIM.equals(moduleName)){
	fimBaseDetailInfo = new Info();
	fimBaseDetailInfo = (Info)session.getAttribute("fimBaseInfo");
	
	 tabName = (String) request.getParameter("tabName") != null ? (String) request.getParameter("tabName") : " ";
	 moduleInfo = (Info)request.getAttribute("currentModuleInfo");
	 
}
//BOEFLY_INTEGRATION : START
//BUG_53511 2/1/2015 starts
else if("fsBqual".equals(session.getAttribute("customTabKey"))){
	 fimBaseDetailInfo = new Info();
	 fimBaseDetailInfo = (Info)request.getAttribute("baseDetailNoStatus");
	 moduleInfo = (Info)request.getAttribute("currentModuleInfo");
}//BUG_53511 2/1/2015 ends
String showPopUp = request.getParameter("showPopUp");
String isArchive = (String)request.getAttribute("isArchive");
String isMuEntityCustomTab=(String)request.getAttribute("isMuEntityCustomTab");
//BOEFLY_INTEGRATION : END
%>
<script>
/**
 * PP_changes
 */
function modifyVirtualBrochureForm(tableAnchor) {
	var action = "addModuleCustomTab?moduleName=fs_captivate&customTabKey="+tableAnchor+"&showPopUp=Y&fromCaptivate=Y&activityId=<%=activityId%>";
	action = action + "&idField=<%=(String)request.getAttribute("idField")%>";
    action = action+"&fromOuterPortal=<%=(String)request.getParameter("fromOuterPortal")%>&cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>";
	window.location.href = action; 
} 


var  unlockedBuilderFields = "";
function addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs)
{
return addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,'false','false','false');
}
function addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField)
{
	var displayPrivateValue = privatevalue;
	var displayParamValue = paramValue;
	//BB-20150525-360 Starts
	$.post("handleAjaxRequest",{"dataset": "piiPasswordDecrypt","key":displayParamValue,"canAcessSendMail":canAcessSendMail,"isEmailField":isEmailField,"moduleName":moduleName,"isUrlField":isUrlField},
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
			var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view','"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"','"+isUrlField+"')\"><img src='<%=conxtname%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";
			document.getElementById(fieldName).innerHTML = displayPrivateValue+"&nbsp;"+displayAdditional; 
			if(unlockedBuilderFields.match(fieldId) == fieldId);
			{
				unlockedBuilderFields = unlockedBuilderFields.replace(fieldId,"");
			}
		}
		else if(status == "unlocked")
		{
			var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view','"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"','"+isUrlField+"')\"><img src='<%=conxtname%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";  
			document.getElementById(fieldName).innerHTML = displayParamValue+"&nbsp;"+displayAdditional; 
			unlockedBuilderFields =  unlockedBuilderFields + "," + fieldId;
		}
		
		document.addModifyForm.UnlockedFields.value = unlockedBuilderFields;
	}
	else  if(fromWhere == "modify")
	{
		if(status == "locked")
		{
			var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify','"+tableAnchorForLogs+"')\"><img src='<%=conxtname%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16' vspace='3'   border = '0'  ></a>";

			var fieldId = fieldName.replace("piiField","");
			document.getElementById(fieldId).disabled=true;
			document.getElementById(fieldId).value=displayPrivateValueText; 
			document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
		}
		else if(status == "unlocked")
		{
			var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify',,'"+tableAnchorForLogs+"')\"><img src='<%=conxtname%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'  vspace='3'  border = '0'  ></a>";  
			var fieldId = fieldName.replace("piiField","");
			document.getElementById(fieldId).disabled=false;
			document.getElementById(fieldId).value=displayParamValueText; 
			document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
		}
	}
	});	
}
//For Bug 41302 starts
function printPageViewP()
{
	printPageCustom(unlockedBuilderFields);
}
//For Bug 41302 ends
function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs)
{
	return unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,'false','false','false')
	//openJqueryPopUp('checkRedAlertBuilder?FieldName='+fieldName+'&Status='+status+'&Value='+paramValue+'&paramValue='+privatevalue+'&moduleName='+moduleName+'&pageName='+pageName+'&leadID='+leadID+'&fromWhere='+fromWhere+'&tableAnchorForLogs='+tableAnchorForLogs,600,300);
}
function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField)
{
	openJqueryPopUp('checkRedAlertBuilder?FieldName='+fieldName+'&Status='+status+'&Value='+paramValue+'&paramValue='+privatevalue+'&moduleName='+moduleName+'&pageName='+pageName+'&leadID='+leadID+'&fromWhere='+fromWhere+'&tableAnchorForLogs='+tableAnchorForLogs+'&canAcessSendMail='+canAcessSendMail+'&isEmailField='+isEmailField+'&isUrlField='+isUrlField,600,300);
}
function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs)
{
	return lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,'false','false','false');
}
function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField)
{
	if(FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_LOCK_THIS_ENTRY))<%-- ZC_CM_B_43894 --%>
	{
		addHtmlBuilder(fieldName,"locked",privatevalue,paramValue,moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField);
	}
}
</script>
<form action="genericHandler" method="POST" name="<%=deleteFormName%>" <%=Constants.FORM_ENCODING%>>
<%if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName)){ %>
	<input type="hidden" name="triggerFormName" value="<%=tabDisplay%>">
	<input type="hidden" name="allManipulator" value="com.appnetix.app.portal.FimThirdPartyUpload">
	<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="fim-fimmanipulator">
	<input type="hidden" name="isMuTab" value="<%=(String)request.getAttribute("isMuEntityCustomTab")%>">
<%}%>
	<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=tableAnchor%>">
	<input type="hidden" name="<%=FieldNames.RETURNPAGEKEY%>" value="tabreturnpage">
    <input type="hidden" name="<%=FieldNames.RETURN_PAGE_TAB%>" value="moduleCustomTab?customTabKey=<%=tableAnchor%>">
	<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=ActionNames.DELETE%>">
	<% 
		if(documentMap != null) 
		{
			String prevModuleName=moduleName;
			if("area".equals(subModuleName)){
				prevModuleName=subModuleName;
			}else if("yes".equals(isMuEntityCustomTab) && ("entity".equals(session.getAttribute("showEntityTabs")) || "mu".equals(session.getAttribute("showEntityTabs")))){
				prevModuleName="fimMu";
			}
			for(int i = 0; i < documentMap.size();i++) 
			{
				DocumentMap dMap = (DocumentMap)documentMap.get(i);
	%>
				<input type="hidden" name="<%=tableAnchor+FieldNames.CHILDREN%>" value="<%=prevModuleName+"Documents"%>_<%=dMap.getFieldName()%>">
				<input type="hidden" name="<%=currentModuleInfo.get("DocumentTableAnchor")%>_<%=dMap.getFieldName()%><%=currentModuleInfo.get("documentId")%>">
	<% 	
			}
		}  
	%>	
	<input type="hidden" name="<%=BuilderFormFieldNames.ID_FIELD%>">
</form>

	
	 	
<div id="frm" style="{position: absolute}">
	<%= addModifyForm%>
</div>
<table id="moduleCustomTab" width="100%" border="0" cellspacing="0" cellpadding="0" >
	<%--<tr> <td colspan="3" width="100%"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td></tr>--%>
	
	
	 <%--P_CM_B_40919 Start --%>
	 <%if("Y".equals(bPrint)){
		//TCBY_Form_Generator_issue commented for print button
		 %>
	     <%if(MODULE_NAME.NAME_FIM.equals(moduleName) && !("entity".equals(session.getAttribute("showEntityTabs")) || "mu".equals(session.getAttribute("showEntityTabs")) || "fimArea".equals(session.getAttribute("subMenuName")))){ %>
	     		<tr>
					    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
						<td>
							<%if(MODULE_NAME.NAME_FIM.equals(moduleName)){ %>
							<table width="100%" border="0" cellspacing="0" cellpadding="4" >
							  <tr >
									<td height="19" class="prTitle01" width="100%"><%=tabName%>&nbsp;<%=LanguageUtil.getString("Details for Franchise ID",(String)session.getAttribute("userLanguage"))%> : "<%=session.getAttribute(moduleInfo.get("entityName"))%>"</td>		
					          </tr>
					        </table>
					        <% }%>
						</td>
						<td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
 			 </tr>
	     <% } else if("fsBqual".equals(session.getAttribute("customTabKey"))){%><%--BUG_53511 2/1/2015 starts--%><%--BOEFLY_INTEGRATION : START --%>
	 <tr>
					    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="4" >
							  <tr >
									<td height="19" class="prTitle01" width="100%"><%=LanguageUtil.getString("bQual of",(String)session.getAttribute("userLanguage"))%>&nbsp;"<%=session.getAttribute(moduleInfo.get("entityName"))%>"</td>		
					          </tr>
					        </table>
						</td>
						<td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
 			 </tr>
	    <%} if(MODULE_NAME.NAME_FS.equals(moduleName)) { //TCBY_Form_Generator_issue starts
	    %><%--BUG_53511 2/1/2015 ends--%><%--BOEFLY_INTEGRATION : END --%>
	    <tr>
			<td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
			<td>
			    <ar:Printdetailstabletag noOfColumns="2"  fieldsInfo="<%=(Info)request.getAttribute("baseDetailNoStatus")%>"   cssClassKey="PRBlText12_b" cssClassValue="PRBlText12" cellspacing="5" cellpadding="2" >
				</ar:Printdetailstabletag>
			</td>
			<td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>
	     <%
	    } //TCBY_Form_Generator_issue ends
	     }%><%--P_CM_B_40919 End --%>
	    <%
        	StringBuffer backToSearch = new StringBuffer();
    		backToSearch.append("<a href=\"javascript:void(0);\" onclick=\"javascript:backToSearch();\" class=\"yBtn fltRight\" style=\"text-decoration:none\" >");
    		backToSearch.append("<img width=\"13\" height=\"15\" border=\"0\" class=\"fltRight pl5\" src=\"").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/btsicon.png\">").append(LanguageUtil.getString("Back to Search Results")).append("</a>");
    		backToSearch.append("</a>");
    		%>
    		<%if(MODULE_NAME.NAME_FS.equals(moduleName) && StringUtil.isValidNew((String)session.getAttribute("sPaggingUrlBack")) && !"Y".equals(bPrint) && !"Y".equals(request.getParameter("showPopUp"))) { %>
    		<tr>
				<td height="15" colspan="3"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
			</tr>
    		<tr>
				<td class="text" width="6"><img src="<%=request.getContextPath()%>images/spacer.gif" height="1" width="10"></td>
				<td align="right" class="bText12"><%=backToSearch.toString()%></td>
			</tr>
    			
    		<%} %>
    		<% if("Y".equals(request.getParameter("fromCaptivate"))) { //PP_changes starts %>
    		<tr>
				<td height="15" colspan="3"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
			</tr>
    		<tr>
				<td class="text" width="6"><img src="<%=request.getContextPath()%>images/spacer.gif" height="1" width="10"></td>
                <td align="left" class="hText18themeNew vp5"><%=activityName%></td>
			</tr>
    		<% } //PP_changes ends %>
	
	<tr>
	    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
		<td width="100%">
		
		<!-- P_B_CM_40321 -->
		
		<%--P_CM_B_40919 Start --%>
           <%  if("Y".equals(bPrint)){ %>
           <div id ="printDiv">
           <%if((MODULE_NAME.NAME_FIM.equals(moduleName) && ! ("entity".equals(session.getAttribute("showEntityTabs")) || "mu".equals(session.getAttribute("showEntityTabs")) || "fimArea".equals(session.getAttribute("subMenuName")))) || "fsBqual".equals(session.getAttribute("customTabKey")) ){ %>
           <ar:Printdetailstabletag noOfColumns="2"  fieldsInfo="<%=fimBaseDetailInfo%>"   cssClassKey="PRBlText12_b" cssClassValue="PRBlText12" cellspacing="0" cellpadding="0" >
			</ar:Printdetailstabletag>
			<%}%>
			<%--BOEFLY_INTEGRATION : START--%>
			<%Iterator itr = dataInfo.getKeySetIterator();
        	while (itr.hasNext()) {
        		String key = (String) itr.next();
                Object obj = (Object)dataInfo.getObject(key);
                if(obj instanceof Info && !singleTabularSection && !isTabularSummary){							//P_Enh_Multiple_Input_Tabular_Summary 
                	Info info = (Info)obj;
	           %>
	           			<ar:Printdetailstabletag noOfColumns="2" fieldsInfo='<%=info%>' cellspacing="0" cellpadding="0" cssClassKey="PRBlText12_b" cssClassValue="PRBlText12" ></ar:Printdetailstabletag>	<%--BUG_53511 2/1/2015 --%>	     	
	           <%     
                }else{
	           %>
	           			 <ar:Printdetailstabletag noOfColumns="2"  fieldsInfo="<%=dataInfo%>"   cssClassKey="PRBlText12_b" cssClassValue="PRBlText12" cellspacing="3" cellpadding="2" >
						</ar:Printdetailstabletag>     
			<%         break;
	                }
				
			 } %>
			<%--BOEFLY_INTEGRATION : END--%>
			</div> 
			<%--P_CM_B_40919 End --%>
           <%}else if(noActiveField && !singleTabularSection && !isTabularSummary){%>									<!--P_Enh_Multiple_Input_Tabular_Summary-->
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
						<tbody><tr><td height="5" colspan="3"></td>
						</tr>
						<tr>
						  <td><img width="10" height="8" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"></td>
						  <td width="100%" class="crvBox2-bg">
							  <table cellspacing="0" cellpadding="0" border="0" width="100%" style="margin-bottom:0px;">
							  	<tbody><tr>
					  			  	 <td width="10" valign="top"><img width="10" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl.png"></td>
					  			  	 <td width="475" class="crvBox2-top"></td>
					  			  	 <td width="10" valign="top"><img width="16" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png"></td>
							  	</tr>
								<tr>
					  			  	<td class="crvBox2-left"></td>
					  			  	<td style="text-align:center;" class="crvBox2-bg text">
							         	No Form Fields found
							        </td>
							        <td class="crvBox2-right"></td>
							    </tr>
								<tr>
				                     <td align="left" valign="bottom"><img width="10" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png"></td>
				                     <td class="crvBox2-botom"></td>
				                     <td align="right" valign="bottom"><img width="16" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png"></td>
				               </tr>
							  </tbody></table>
							  
						  </td>
						  <td><img width="10" height="8" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"></td>
						</tr>
						</tbody>
					</table>
			<%} else {%><%--P_CM_B_40919 End --%>
			<%--BOEFLY_INTEGRATION : START--%>
			<%
			boolean reportSection=true;
			Iterator itr = dataInfo.getKeySetIterator();
        	while (itr.hasNext()) {
        		String key = (String) itr.next();
                Object obj = (Object)dataInfo.getObject(key);
                if(obj instanceof Info && !singleTabularSection && !isTabularSummary){										//P_Enh_Multiple_Input_Tabular_Summary
                	Info info = (Info)obj;
	           %>
	           			
	           			<ar:Newcustomtables noOfColumns="2" fieldsInfo='<%=info%>' cellspacing="0" cellpadding="0" cssClassKey="pqs_que" cssClassValue="tb_data_mokup" ></ar:Newcustomtables>		     	
	           <%     
                }else{
                	reportSection=false;
	           %>
	           			 
	           			<ar:buildersummery noOfColumns="2" sectionDetailString="<%=sectionDetailString%>" headerAsTitle="<%=headerAsTitle%>" tableBorder="0" cssClassKey="pqs_que" cssClassValue="tb_data_mokup" fieldsInfo="<%=dataInfo%>" buttons="<%=buttons%>" functionForButtons="<%=str%>" buttonPriveleges="<%=buttonPriveleges%>" i18n="true" formHeaderClass="header_contrast" >
						</ar:buildersummery>           
			<%         break;
	                }
				
			 } if(reportSection){%>
			 <table>
				<tr>
					<td valign="bottom" colspan="3">
						&nbsp;<input type="button" onclick=" printPageViewP()" class="cm_new_button" value="Print" name="Button0">
					</td>
				</tr>
			</table>
			<%--BOEFLY_INTEGRATION : END--%>
			<%}}%>
			
			 <!-- P_B_CM_40321 -->
		</td>
	    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
		</tr>
</table>
<%@ include file="customTab.js" %> <!-- For Bug 41302 -->
<%--P_CM_B_40919 Start --%>
<%if("cm".equals((String)session.getAttribute("menuName"))){%>
<form name="blankFormForOptOutAction" id="blankFormForOptOutAction" method="post" <%=Constants.FORM_ENCODING%>>
        <input type="hidden" name="idField" value="">
</form>
<%} %>    
<script>
function tabSendMail(commonTabMail, emailId){
		document.tabMailForm.email.value = emailId;
		document.tabMailForm.submit();
	
}
</script>
<script>
$( document ).ready(function() {
	$("#hideprint").hide();
	<%if("cm".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName) || "lead".equals(moduleName)){%>//P_CM_B_43893
	$("#headerString").addClass("btm-menu").addClass("pd5");
	<%}%>
    if(parent.$.fn.colorbox) { //PP_changes starts
        parent.$.fn.colorbox.vkResize($(document).width()+60, $(document).height()+60);
    } //PP_changes ends
});

var oldData;
function editSpanData(dbFieldName){
	$('#'+dbFieldName+"_EDIT").hide();
	oldData = $.trim($('#'+dbFieldName+"_SPAN").html());
	$('#'+dbFieldName+"_SPAN").html("<input type='text' name='"+dbFieldName+"' id='"+dbFieldName+"' value='"+oldData+"' />&nbsp;&nbsp;<img id='"+dbFieldName+"Save' title='Save' style='cursor: pointer;' src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/save_small.gif' width='14' align='absmiddle' height='14' border='0' onClick=\"javascript:saveData('"+dbFieldName+"');\">&nbsp;<img id='"+dbFieldName+"Cancel' title='Cancel' style='cursor: pointer;' src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/cancel_small.gif' width='14' align='absmiddle' height='14' border='0' onClick=\"javascript:cancelEdit('"+dbFieldName+"','"+oldData+"');\">");
	$('#'+dbFieldName).focus();
}

function saveData(dbFieldName){
	$.ajax( {
		  async: true,
	      type: "POST",
	      url: "handleAjaxRequest",
	      data: {"dataset":"saveBoeflySceretData","fieldName":dbFieldName,"fieldValue":$.trim($('#'+dbFieldName).val()),"boeflyUserId":"<%=boeflyUserId%>"},
	      success: function( response ) {
		      $('#'+dbFieldName+"_SAVE").hide();
		      $('#'+dbFieldName+"_CANCEL").hide();
		      $('#'+dbFieldName+"_SPAN").html($.trim(response));
		      $('#'+dbFieldName+"_EDIT").show();
		  }
	});
}

function cancelEdit(dbFieldName,oldData){
	$('#'+dbFieldName+"_SAVE").hide();
	$('#'+dbFieldName+"_CANCEL").hide();
	$('#'+dbFieldName+"_SPAN").html($.trim(oldData));
	$('#'+dbFieldName+"_EDIT").show();
}


</script>
<%--P_CM_B_40919 End --%>
