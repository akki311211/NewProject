<%--
 - $Author						:  $Abhishek Gupta
 - $Version						:  $1.1
 - $Date						:  $Nov 14,2011
 --%>
<%--
----------------------------------------------------------------------------------------------------------
Version No.		Date		By			Against		Function Changed	Comments
-----------------------------------------------------------------------------------------------------------
P_E_MoveToFim_AddlFDD      29Nov2012       Veerpal Singh           Patch 
PW_B_26145		12 August 2013			Anjali Pundir			manage form generator>any new added field/section/document>the delete should come second modify should come first.
Bug_26819  		30 Aug 2013		Anjali Pundir			basebuild>admin>manage form generator>add field with country/state combo box drop down> functionality is not working properly.
Bug_27528		3 Sept 2013		Anjali Pundir			basebuild>admin>fim>manage form generator>add new field in any form>no form field added.(win 7+ie8)
P_FS_Enh_BuilderForm		16Dec2013		Naman Jain		builderForm in Franchise Sales
P_Admin_CM_B_38111                12 May 2014     Ikramuddin    Add ? in alert message before delete document.     
P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
P_B_CM_070714  7th July 2014            Swati Garg              GUI issue
P_CM_B_41638   15th july 2014           Udit Soni                Seprator issue on add new field while adding form generator
P_B_CM_41814   18 July 2014             Rashmi Shakya          GUI issue
P_B_ADMIN_44180  13 August 2014         Rashmi Shakya          links are not visible as blue buttons
 BB-20141017-177               5 nov 2014          Nazampreet Kaur       code added for summary display of custom fields
 ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
-----------------------------------------------------------------------------------------------------------
--%>
<%@ page import = "com.appnetix.app.util.* " %>
<%@ page import = "com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="com.home.builderforms.BuilderFormFieldNames" %>
<%@ page import="com.home.builderforms.BuilderConstants" %>
<%@ page import="com.appnetix.app.util.information.*" %>
<%@ page import="com.appnetix.app.util.*" %>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import="java.util.*" %>
<%@ page import="com.appnetix.app.util.database.*" %>
<%@ page import = "com.appnetix.app.util.i18n.*"%>
<%@ page import = "com.appnetix.app.portal.role.UserRoleMap"%>
<%@include file="/jsp/ajax/ajax.jsp"%>
<%@ include file = "builderScriptCss.jsp" %>
 

<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="application"/>
<jsp:useBean id="builderWebForm"	class="com.appnetix.app.portal.FormCustomization.BuilderWebFormBean" scope="session"/><%-- BB_Naming_Convention--%>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/popNew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/balloonTooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<script type="text/javaScript" src="<%=request.getContextPath()%>/javascript/jqueryPopUp.js"></script>
<link type="text/css" media="screen" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/smartconnect/colorbox1.css" />

<style type="text/css">
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
String moduleId=request.getParameter("moduleId");//ENH_MODULE_CUSTOM_TABS
String formId = request.getParameter("formNames");//P_Enh_ContactHistory_FormBuilder
//ENH_MODULE_CUSTOM_TABS starts
////BB-20150203-259  (To make available all the fields of Form builder as keyword.) starts
HashMap fieldMap =  new HashMap();
fieldMap = (HashMap)request.getAttribute("fieldMap");

String moduleName = ModuleUtil.MODULE_NAME.NAME_FIM;
String moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
String moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
String moduleShortName = "fim";
String subModuleDisplayName="";
String moduleId1="3";
String moduleName1="admin";
String subModuleParam="fromWhere";
String href = "administration#FIM";
boolean isFim=false;
String formGenLabel="Manage Form Generator";
boolean showFormGenLabel = true; //P_Enh_Site_Clearance
String passwordFunctionality = (String)request.getAttribute("passwordFunctionality");
boolean dataPresent=true;

UserRoleMap userRoleMap = (UserRoleMap) session.getAttribute("userRoleMap");//P_CM_B_68658
if(request.getAttribute("dataPresent")!=null)
{
 	dataPresent = (Boolean)request.getAttribute("dataPresent");
}
String sTabModuleName 	= request.getParameter(FieldNames.MODULE);
String centerInfoLabel = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", new String[]{"MODULE","BUILDER_FORM_ID"}, new String[]{"fim","1"});		//P_B_65450

if(sTabModuleName == null)
{
	sTabModuleName = (String)session.getAttribute("sTabModuleName");
}
else
{
	session.setAttribute("sTabModuleName", sTabModuleName);
}
//PP_changes starts
String subModuleName = request.getParameter("subModuleName");
if(subModuleName == null) {
	subModuleName = (String)session.getAttribute("subModuleName");
} else {
	session.setAttribute("subModuleName", subModuleName);
}
//PP_changes ends
//Disable keyword starts
HashMap deactiveKeyWordMap =null;
if("cm".equals(sTabModuleName) || "lead".equals(sTabModuleName)){
	
	deactiveKeyWordMap=(HashMap)request.getAttribute("deactiveKeyWordMap");
}
//Disable keyword End

if("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_FIM;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_FIM;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	subModuleDisplayName=ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM_FRANCHISEE;
    request.setAttribute(BuilderConstants.TABLE_ID,"1");
    if("43".equals(formId)|| "45".equals(formId) || "46".equals(formId)){//P_Enh_ContactHistory_FormBuilder
    	moduleShortName="contactHistory";
    }

}
else if("cm".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_CM;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_CM;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
    request.setAttribute(BuilderConstants.TABLE_ID,"11");
    href = "administration#Contact_Manager";
    moduleShortName = "cm";
}
else if("fs".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_FS;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_FS;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FS;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FS;
    request.setAttribute(BuilderConstants.TABLE_ID,"10");
    href = "administration#Franchise_Sales";
    moduleShortName = "fs";
    if("48".equals(formId)|| "44".equals(formId) || "47".equals(formId)){//P_Enh_ContactHistory_FormBuilder
    	moduleShortName="contactHistory";
    }
}
else if("account".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_ACCOUNT;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_ACCOUNT;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
    request.setAttribute(BuilderConstants.TABLE_ID,"16");
    href = "administration#Contact_Manager";
    moduleShortName = "account";
}
else if("opportunity".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_OPPORTUNITY;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
    request.setAttribute(BuilderConstants.TABLE_ID,"17");
    href = "administration#Contact_Manager";
    moduleShortName = "opportunity";
}
else if("lead".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_LEAD;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_LEAD;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_CM;
    request.setAttribute(BuilderConstants.TABLE_ID,"19");
    href = "administration#Contact_Manager";
    moduleShortName = "lead";
}
//P_SCH_ENH_008 Starts
else if("scheduler".equals(sTabModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_SCHEDULER;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_SCHEDULER;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_SCHEDULER;
    request.setAttribute(BuilderConstants.TABLE_ID,"13");
    href = "administration#Scheduler";
    moduleShortName = "scheduler";
    formGenLabel="Manage "+moduleURL+" Form";
//P_SCH_ENH_008 Ends
} else if("site".equals(sTabModuleName)) { //P_Enh_Site_Clearance starts
	showFormGenLabel = false;
	moduleName = ModuleUtil.MODULE_NAME.NAME_SITE;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_SITE;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_SITE;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_SITE;
    request.setAttribute(BuilderConstants.TABLE_ID,"18");
    href = "administration#Site_Clearance";
    moduleShortName = "site";
}//P_Enh_Site_Clearance ends
else if("fim".equals(sTabModuleName) && "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
	moduleName = ModuleUtil.MODULE_NAME.NAME_FIM_MU;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_FIM_MU;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM_MU;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	subModuleDisplayName=ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM_MU;
    request.setAttribute(BuilderConstants.TABLE_ID,"23");
    moduleShortName = "fimMu";
    if("93".equals(formId)|| "94".equals(formId) || "95".equals(formId)){        //P_B_72916 
    	moduleShortName = "contactHistory";	
    }
    
}else if("fim".equals(sTabModuleName) && "area".equals(subModuleName))
{
	moduleName = ModuleUtil.MODULE_NAME.NAME_FIM_AREA;
	moduleId = ""+ModuleUtil.MODULE_ID.ID_FIM_AREA;
	moduleDisplayName = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM_AREA;
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	subModuleDisplayName=ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM_AREA;
    request.setAttribute(BuilderConstants.TABLE_ID,"21");
    moduleShortName="area";
    if("96".equals(formId)|| "97".equals(formId) || "98".equals(formId)){         //P_B_72916
    	moduleShortName="contactHistory";
    }
    
}
//P_Enh_Mu-Entity_FormGenerator ends
if(!"Manage Form Generator".equals(formGenLabel)){
	moduleId1=moduleId;
	moduleName1=subModuleName;
	subModuleParam="subModuleName";
	
}
SequenceMap builderFormMap = (SequenceMap)request.getAttribute("builderFormMap");

String smartGroupMesasge = "Smart Group";
if("fs".equals(sTabModuleName)) {
	smartGroupMesasge = "Smart Group / Field Status Criteria";
}

int regularSectionCount=0;
int tabularSectionCount=0;
if (StringUtil.isValidNew(formId)) {
request.setAttribute(BuilderConstants.TABLE_ID, "2");
request.setAttribute("form-id", formId);
request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,
	"yes");
Info sectionCountInfo=builderForm.getBuilderSectionCount(request);
regularSectionCount=Integer.parseInt(sectionCountInfo.get("regularSectionCount"));
tabularSectionCount=Integer.parseInt(sectionCountInfo.get("tabularSectionCount"));
}
//ENH_MODULE_CUSTOM_TABS ends

final List<String> validationList = Arrays.asList("buildingSize", "buildingDimentionsX", "buildingDimentionsY", "buildingDimentionsZ");
%>
<script language="javascript">
<%--Bug_26819 Starts--%>
var isActiveArr= new Array();
var dispNameArr= new Array();


document.onmousemove = getMouseXY;

var tempX = 0;
var tempY = 0;
var pageHeight = 0;

function getMouseXY(e) {
    if (document.all) { // grab the x-y pos.s if browser is IE
        tempX = event.clientX + document.body.scrollLeft;
        tempY = event.clientY + document.body.scrollTop + document.documentElement.scrollTop;//P_FS_B_4031
        pageHeight = document.body.offsetHeight + document.body.scrollTop + document.documentElement.scrollTop;//P_FS_B_4031
    } else {  // grab the x-y pos.s if browser is NS
        tempX = e.pageX;
        tempY = e.pageY
        pageHeight = document.body.clientHeight + document.body.scrollTop;//P_FS_B_4031
    }
    // catch possible negative values in NS4
    if (tempX < 0) {
        tempX = 0;
    }
    if (tempY < 0) {
        tempY = 0;
    }

    return true;
}

function saveActive(dispName, fldName, isActive)
{
	isActiveArr[fldName]=new Array();
	dispNameArr[fldName]=new Array();
	isActiveArr[fldName]=isActive;
	dispNameArr[fldName]=dispName;
}

function onFieldCall(tableName, fldName, isActive, section, isOTableFld, otherTableName, isParent, dependentFld,sDisplayName,isCenterInfoField,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}

//CUSTOM_REPORT_DEACTIVE_FILED_ISSUE
	var isFieldIsInReport="no";
	var isFieldIsInSmartQuestion="no";
	var isFieldIsInHeatMeter = "no";
	var isFieldInSmartGroup = "no";
	var isFieldInWorkflow = "no";
	if(isActive=="true")
		{
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=getFieldReportInfo&fldName=" + fldName+"&tableName="+tableName+"&moduleName=<%=sTabModuleName%>",
	        //FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
	        async:false,
	        success:function(data) {
	        	isFieldIsInReport=trim(data);        	 
	          }
	    });
	 
	 //smart group and status trigger starts
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=checkForFieldInSmartGroup&fldName="+fldName+"&tableName="+tableName+"&moduleName=<%=sTabModuleName%>",
	        //FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
	        async:false,
	        success:function(data) {
	        	isFieldInSmartGroup=trim(data);        	 
	          }
	    });
	 
	 //workflow : check whether the filed is used in any workflow or not
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=checkForFieldInWorkflow&fldName="+fldName+"&tableName="+tableName+"&moduleName=<%=sTabModuleName%>",
	        async:false,
	        success:function(data) {
	        	isFieldInWorkflow=trim(data);        	 
	          }
	    });
	 
	 
	 //ENH_PW_SMART_QUESTIONS Starts
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=getSmartQuestionField&fldName=" + fldName,
	        async:false,
	        success:function(data) {
	        	isFieldIsInSmartQuestion = trim(data);        	 
	        }
	 });
	//ENH_PW_SMART_QUESTIONS Ends 
	 
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=getHeatMeterConfiguration&fldName=" + fldName,
	        async:false,
	        success:function(data) {
		 isFieldIsInHeatMeter=trim(data);        	 
	          }
	    });
	    
		}
		if("yes"==isFieldIsInReport)
		{
		//alert("Field is assosiated with some of Custom Report. It cannot be deactivated."); //P_CM_Enh_BuilderForm
		FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF_CUSTOM_REPORT+". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
		return false;
		}
		
		if("yes" == isFieldInSmartGroup) {
			//alert("Field is assosiated with some of <%=smartGroupMesasge%>. It cannot be deactivated.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF+ " <%=smartGroupMesasge%>. "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		if("yes" == isFieldInWorkflow) {
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF+ " <%=LanguageUtil.getString("Workflows")%>. "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		
        
		//ENH_PW_SMART_QUESTIONS Starts
		if("yes" == isFieldIsInSmartQuestion) {
			//alert("Field is assosiated with some of the SmartQuestions. It cannot be deactivated.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF_THE_SMARTQUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		//ENH_PW_SMART_QUESTIONS Ends
		
		if("yes" == isFieldIsInHeatMeter) {
			//alert("Field is assosiated with Heat Meter. It cannot be deactivated.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF_HEAT_METER+". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		
	//BB-20150203-259 (Dynamic Response based on parent field response) starts
	if(isActive == "true") { //deactivate for child field
		var isActivate = "no";
		
		$.ajax({
	    	type: "POST",
	        url: "handleAjaxRequest", 
	        data: "dataset=checkChildFieldDependency&fldName="+fldName+"&tableAnchor="+tableName+"&isOtherTableFld="+isOTableFld,
	        async: false,
	        success:function(data) {
	        	isActivate = trim(data);        	 
	        }
	    });
		if("yes" == isActivate) {
			//alert("Please deactivate its child field first to proceed.");
			FCI.Messages(FCLang.PLEASE_DEACTIVATE_ITS_CHILD_FIELD_FIRST_TO_PROCEED);
			return false;
		}
	} else { //activate for parent field
		var isDeactivate = "yes";
		$.ajax({
	    	type: "POST",
	        url: "handleAjaxRequest", 
	        data: "dataset=canActivateParent&fldName="+fldName+"&tableAnchor="+tableName+"&isOtherTableFld="+isOTableFld,
	        async: false,
	        success:function(data) {
	        	isDeactivate = trim(data);
	        }
	    });
		if("no" == isDeactivate) {
			FCI.Messages(FCLang.PLEASE_ACTIVATE_ITS_PARENT_FIELD_FIRST_TO_PROCEED);
			//alert("Please activate its parent Field first to proceed");
			return false;
		}
	}	 
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
	
	//CUSTOM_REPORT_DEACTIVE_FILED_ISSUE
	if(isParent == "true" && dependentFld && isActive == "true")// It is a dependent parent field and is Active at present 
	{
		if(isActiveArr[dependentFld] == "true")	// If State is Active 
		{
			FCI.Messages(FCLang.PLEASE_DEACTIVATE_ITS_STATE_FIELD+" \""+dispNameArr[dependentFld]+"\" "+FCLang.FIRST_TO_PROCEED);
			//alert("Please deactivate its state field \""+dispNameArr[dependentFld]+"\" first to proceed.");
			return false;
		}
	}
	if(isParent == "false" && dependentFld && isActive == "false")// It is a dependent field and is deactive at present 
	{
		if(isActiveArr[dependentFld] == "false")	// If Country is Not Active 
		{
			FCI.Messages(FCLang.PLEASE_ACTIVATE_ITS_COUNTRY_FIELD+" \""+dispNameArr[dependentFld]+"\" "+FCLang.FIRST_TO_PROCEED);
			//alert("Please activate its country field \""+dispNameArr[dependentFld]+"\" first to proceed.");
			return false;
		}
	}
	<%--Bug_26819 Ends--%>
	document.builderForm.action = "builderWebForm";//BB_Naming_Convention
	document.builderForm.modifyFld.value='y';
	document.builderForm.tableAnchor.value=tableName;
	document.builderForm.fieldName.value=fldName;
	document.builderForm.isActive.value=isActive;
	document.builderForm.section.value=section;
	document.builderForm.isOtherTableField.value=isOTableFld;
	document.builderForm.otherTableName.value=otherTableName;

	document.builderForm.isCenterInfoField.value=isCenterInfoField;
	document.builderForm.isTabularSection.value = isTabularSection;
	document.builderForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.builderForm.tabularSectionTableName.value = tabularSectionTableName;
	//document.builderForm.sdisplayName.value=sDisplayName; // BB-20141017-177
	document.builderForm.displayName.value=sDisplayName;
	document.builderForm.statuschange.value="true"; // BB-20141017-177
	document.builderForm.performingOperation.value = "yes";

	document.builderForm.submit();
}

function addToCenterInfo(fldName,tableName,isOTableFld,formName,isCenterInfoField,otherTableName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	document.builderForm.isCenterInfoField.value=isCenterInfoField;
	document.builderForm.modifyFld.value='y';
	document.builderForm.tableAnchor.value=tableName;
	document.builderForm.fieldName.value=fldName;
	document.builderForm.fieldFormName.value=formName;
	document.builderForm.isOtherTableField.value=isOTableFld;
	document.builderForm.otherTableName.value=otherTableName;
	document.builderForm.action = "builderWebForm";
	document.builderForm.performingOperation.value = "yes";
	document.builderForm.submit();
	
}

function onFieldCall1(tableName, fldName, isMandatory, isOTableFld, otherTableName,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	document.builderForm.action = "builderWebForm";//BB_Naming_Convention
	document.builderForm.modifyFld.value='y';
	document.builderForm.tableAnchor.value=tableName;
	document.builderForm.fieldName.value=fldName;
	document.builderForm.isMandatory.value=isMandatory;
	document.builderForm.isOtherTableField.value=isOTableFld;
	document.builderForm.otherTableName.value=otherTableName;
	document.builderForm.performingOperation.value = "yes";
	document.builderForm.isTabularSection.value = isTabularSection;
	document.builderForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.builderForm.tabularSectionTableName.value = tabularSectionTableName;
	document.builderForm.submit();
}
function onFieldCall2(tableName, fldName, isPiiEnabled, section, isOTableFld, otherTableName,isCenterInfoField,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}

	document.builderForm.action = "builderWebForm";//BB_Naming_Convention
	document.builderForm.modifyFld.value='y';
	document.builderForm.tableAnchor.value=tableName;
	document.builderForm.fieldName.value=fldName;
	document.builderForm.section.value=section;
	document.builderForm.isPiiEnabled.value=isPiiEnabled;
	document.builderForm.isOtherTableField.value=isOTableFld;
	document.builderForm.otherTableName.value=otherTableName;
	document.builderForm.isCenterInfoField.value=isCenterInfoField;
	document.builderForm.performingOperation.value = "yes";
	document.builderForm.isTabularSection.value = isTabularSection;
	document.builderForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.builderForm.tabularSectionTableName.value = tabularSectionTableName;
	document.builderForm.submit();
}
//BB-20150203-259  (To make available all the fields of Form builder as keyword.) starts
function onKeywordActivate(fieldName,tableAnchor,isOtherTableField,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	document.builderForm.insertFld.value='insert';
	document.builderForm.tableAnchor.value=tableAnchor;
	document.builderForm.fieldName.value=fieldName;
	//document.builderForm.fieldFormName.value=formName;
	document.builderForm.isOtherTableField.value=isOtherTableField;
	document.builderForm.isTabularSection.value = isTabularSection;
	document.builderForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.builderForm.tabularSectionTableName.value = tabularSectionTableName;
	document.builderForm.action = "builderKeywordWebForm";
	document.builderForm.submit();
}
function onKeywordDeactivate(fieldName,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	document.builderForm.insertFld.value='delete';
	document.builderForm.fieldName.value=fieldName;
	document.builderForm.isTabularSection.value = isTabularSection;
	document.builderForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.builderForm.tabularSectionTableName.value = tabularSectionTableName;
	document.builderForm.action = "builderKeywordWebForm";
	document.builderForm.submit();
}
//BB-20150203-259  (To make available all the fields of Form builder as keyword.) ends
function refresh() {
	document.builderForm.action = "builderWebForm";//BB_Naming_Convention
	document.builderForm.submit();
}
function selectFimForm() {
    var formName = document.builderForm ;
	formName.submit();
}
function go(formID, fieldName, tableName, buildFld, fieldSize, mapSection, checkValue, otherTblFld, dropdownOpt,isParent,dependentFld, action,sDisplayName,isTabularSection,tabularSectionTableName, tabularSectionTableDBName,regularSectionCount,tabularSectionCount,sActive) {
	if(showProcessingAlert() == 'true') {
		return false;		
	} else {
	var x1 = (screen.width/2)-250;
	var y1 = (screen.height/2)-150;
	var x2 = (screen.width/2)-150;
	var y2 = (screen.height/2)-100;
	if("true"==isTabularSection){
		tableName=tabularSectionTableName;
	}
	if (action == 'delete' || action == 'Delete') {
		/*if(dropdownOpt != null && dropdownOpt == '2' && isParent == 'true') {
			if (FCI.Confirms(FCLangAdmin.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_CUSTOM_FIELD)) {
				document.deleteForm.formID.value = formID;
				document.deleteForm.fieldName.value = fieldName;
				document.deleteForm.tableName.value = tableName;
				document.deleteForm.section.value = mapSection;
				document.deleteForm.action.value = action;
				document.deleteForm.dropdownOpt.value = dropdownOpt;
				document.deleteForm.isParent.value = isParent;
				document.deleteForm.dependentFld.value = dependentFld;
				//document.deleteForm.submit();
			}
		}
		return;
		*/
		//P_CM_Enh_BuilderForm starts
		var isFieldIsInReport="no";
		var isFieldIsInSmartQuestion = "no";
		var isFieldInSmartGroup = "no";
		
		$.ajax({
		        type: "POST",
		          url: "handleAjaxRequest", 					//P_B_81880 custom report deletion bug
		          data  :"dataset=getFieldReportInfo&fldName=" + fieldName+"&tableName="+tableName+"&moduleName=<%=sTabModuleName%>",  //P_B_81880
		        async:false,
		        success:function(data) {
		        	isFieldIsInReport=trim(data);        	 
		          }
		});
		
		$.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=checkForFieldInSmartGroup&fldName="+fieldName+"&moduleName=<%=sTabModuleName%>",
	        async:false,
	        success:function(data) {
	        	isFieldInSmartGroup=trim(data);        	 
	          }
	    });

		 //workflow : check whether the filed is used in any workflow or not
		 $.ajax({
		        type: "POST",
		        url: "handleAjaxRequest", 
		        data  :"dataset=checkForFieldInWorkflow&fldName="+fieldName+"&tableName="+tableName+"&moduleName=<%=sTabModuleName%>",
		        async:false,
		        success:function(data) {
		        	isFieldInWorkflow=trim(data);        	 
		          }
		    });
		
		//ENH_PW_SMART_QUESTIONS Starts
		 $.ajax({
		        type: "POST",
		        url: "handleAjaxRequest", 
		        data  :"dataset=getSmartQuestionField&fldName=" + fieldName,
		        async:false,
		        success:function(data) {
		        	isFieldIsInSmartQuestion = trim(data);        	 
		        }
		 });
		
		if("yes"==isFieldIsInSmartQuestion)
		{
			//alert("Field is assosiated with some of the Smart Questions. It cannot be deleted.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF_THE_SMARTQUESTIONS+". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		//ENH_PW_SMART_QUESTIONS Ends 

		
		if("yes"==isFieldIsInReport)
		{
			//alert("Field is assosiated with some of Custom Report. It cannot be deleted.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF_CUSTOM_REPORT+". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		
		if("yes" == isFieldInSmartGroup) {
			//alert("Field is assosiated with some of <%=smartGroupMesasge%>. It cannot be deactivated.");
			FCI.Messages(FCLang.FIELD_IS_ASSOCIATED_WITH_SOME_OF+ " <%=smartGroupMesasge%>. "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		var isActivate = "no";
		$.ajax({
			type: "POST",
		    url: "handleAjaxRequest", 
		    data: "dataset=checkChildFieldDependency&fldName="+fieldName+"&tableAnchor="+tableName+"&isOtherTableFld="+otherTblFld,
		    async: false,
		    success:function(data) {
		    isActivate = trim(data);        	 
		    }
		});
		if("yes" == isActivate) {
			//alert("Field Dependency is maintained with other Fields. It cannot be deleted.");
			FCI.Messages(FCLang.FIELD_DEPENDENCY_IS_MAINTAINED_WITH_OTHER_FIELDS+ ". "+FCLang.IT_CANNOT_BE_DEACTIVATED);
			return false;
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		
		var isSync = "no";
		var alertMessage = "";
		$.ajax({
			type: "POST",
		    url: "handleAjaxRequest", 
		    data: "dataset=checkSyncFields&fldName="+fieldName+"&tableAnchor="+tableName+"&isOtherTableFld="+otherTblFld,
		    async: false,
		    success:function(data) {
			    if(data.trim() != "") {
			    	alertMessage = data.trim();
			    	isSync = "yes";
			    }
		    }
		});
		
		if(isSync == "yes") {
			alert(alertMessage);
			return false;
		}
		
		//P_CM_Enh_BuilderForm ends
		/*Bug_26819 Starts*/
		if(isParent == "true" && dependentFld && isActiveArr[dependentFld])// In case dependent State field Exists for a country
		{
				alert("Please delete its state field \""+dispNameArr[dependentFld]+"\" first to proceed.");
				return false;
		}
		/* $.ajax({
			type: "POST",
		    url: "handleAjaxRequest", 
		    data: "dataset=checkForTabularSection&tableAnchor="+tableName,
		    async: false,
		    success:function(data) {
		    	tabularSectionCount = trim(data);        	 
		    }
		});
		if(fieldSize==1 && regularSectionCount==1 && tabularSectionCount>0 && isTabularSection!="true"){
			alert("This field cannot be deleted as there is a dependent tabular section present.");
			return false;
		} */
		/*Bug_26819 Ends*/
		if (FCI.Confirms(FCLangAdmin.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_FORM_GENERATOR_FIELD)) {
			//Issue with restoring Deleted Leads starts
			<%if("fs".equals(sTabModuleName)){%>
			 $.ajax({
				type: "POST",
			    url: "handleAjaxRequest", 
			    data: "dataset=updateDeletedLeadLogs&fldName="+fieldName+"&tableAnchor="+tableName+"&moduleName=<%=sTabModuleName%>",
			    async: false,
			 });
			<%}%>//Issue with restoring Deleted Leads
			document.deleteForm.formID.value = formID;
			document.deleteForm.fieldName.value = fieldName;
			document.deleteForm.tableName.value = tableName;
			document.deleteForm.section.value = mapSection;
			document.deleteForm.action.value = action;
			document.deleteForm.deleteKeyword.value = "delete";
			document.deleteForm.sdisplayName.value = sDisplayName;// BB-20141017-177  
			document.deleteForm.isTabularSection.value = isTabularSection;
			document.deleteForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
			document.deleteForm.tabularSectionTableName.value = tabularSectionTableName;
			document.deleteForm.submit();
		}
	} else if(action == 'sequence') {     //P_Enh_Form_Builder_Option_Sequence Starts
		url = "changeOptionSequence?formID=" + formID +"&fieldName=" + fieldName + "&tableAnchor="+tableName+"&fieldSize="+fieldSize+"&action=" + action+"&checkValue="+checkValue+"&section=" + mapSection+"&isOtherTableField="+otherTblFld+ "&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>"; 
		openJqueryPopUp(url,700,400);			//P_Enh_Form_Builder_Option_Sequence ends				
	}else if(action == 'password') { //P_Enh_FC-76 starts
			url = "changePiiFieldPassword?formID=" + formID +"&fieldName=" + fieldName + "&tableAnchor="+tableName+"&moduleId=<%=moduleId%>"; 
			openJqueryPopUp(url,600,300);
	}else if(action == 'keyword' && formID=="33"){			//P_Enh_Custom_Parsing_Keywords_FC-286
		url = "changePiiFieldPassword?formID=" +formID +"&fromWhere=ChangeKeyword"+"&action=" + action+"&fieldName=" + fieldName +"&sActive="+sActive+ "&tableAnchor="+tableName+"&moduleId=<%=moduleId%>"; 
		openJqueryPopUp(url,700,400);
		
	}else if(action == 'deletePassword') {     					//P_Enh_FC-76 starts
		if (FCI.Confirms(FCLangAdmin.ARE_YOU_SURE_YOU_WANT_TO_REMOVE_FIELD_PII_PASSWORD)) { 
			$.ajax({
				type: "POST",
			    url: "handleAjaxRequest", 
			    data: "dataset=deletePiiFieldPassword&fldName="+fieldName+"&tableAnchor="+tableName,
			    async: false,
			 });
			document.blankForm.formID.value = formID;
			document.blankForm.formNames.value = formID;
			document.blankForm.moduleId.value = "<%=moduleId%>"; 
			document.blankForm.submit(); 
		}							
	}														//P_Enh_FC-76 ends
	else
	{
		var isFieldIsInReport="no";			//P_B_81880
		
		isFieldIsInReport = isUsedInCR(fieldName,tableName,'<%=sTabModuleName%>');
		window.name = 'parent';
		var url;
		if(dropdownOpt != null && (dropdownOpt == '2' || dropdownOpt == '1')) {
            url = "builderFormModificationPopUp?formID=" + formID +"&fieldName=" + fieldName + "&isBuildField="+buildFld+"&tableName="+tableName+"&fieldSize="+fieldSize+"&action=" + action+"&checkValue="+checkValue+"&section=" + mapSection+"&isOtherTableField="+otherTblFld+"&dropdownOpt="+dropdownOpt+"&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>&isTabularSection="+isTabularSection+"&tabularSectionTableDBName="+tabularSectionTableDBName+"&tabularSectionTableName="+tabularSectionTableName+"&isFieldIsInReport="+isFieldIsInReport; //P_FS_Enh_BuilderForm
		} else {
            url = "builderFormModificationPopUp?formID=" + formID +"&fieldName=" + fieldName + "&isBuildField="+buildFld+"&tableName="+tableName+"&fieldSize="+fieldSize+"&action=" + action+"&checkValue="+checkValue+"&section=" + mapSection+"&isOtherTableField="+otherTblFld+"&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>&isTabularSection="+isTabularSection+"&tabularSectionTableDBName="+tabularSectionTableDBName+"&tabularSectionTableName="+tabularSectionTableName+"&isFieldIsInReport="+isFieldIsInReport; //P_FS_Enh_BuilderForm
		}
		//	var newwindow=window.open(url,'changeFormField','scrollbars=yes,menubar=no,location=no,toolbar=no,resizable= yes,status=no,width=630,height=250,top='+y1+',left='+x1);
	  //  if (window.focus) {
		//    newwindow.focus()
		//}

		openJqueryPopUp(url,700,400);
	}
	}
}
/*
 *  P_Enh_Multiple_Input_Tabular_Summary
 */
function configureTabularField(formId)								
{
	var url = "configureTabularFields?formID=" + formId + "&moduleId=<%=moduleId%> + &moduleName=<%=moduleName%>"; 
	openJqueryPopUp(url,700,400);
}

function addSection(formID, action, orderNo) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	var x1 = (screen.width/2)-250;
	var y1 = (screen.height/2)-150;
	window.name = 'section';
	var url = "builderFormSection?formID=" + formID + "&action=" + action +"&moduleId=<%=moduleId%>&module=<%=moduleName%>&order="+orderNo+"&regularSectionCount=<%=regularSectionCount%>"; //P_FS_Enh_BuilderForm
	//var newwindow=window.open(url,'changeFormField','scrollbars=auto,menubar=no,location=no,toolbar=no,resizable= yes,status=no,width=430,height=200,top='+y1+',left='+x1);
    //if (window.focus) {
	  //  newwindow.focus()
	  openJqueryPopUp(url,550,250);
	//}
}
//added  by akash kumar for formbuilder modify section name
function modifySection(formID,sectionName ,action) {//BB-20150203-259 (Section Rename changes)
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	var x1 = (screen.width/2)-250;
	var y1 = (screen.height/2)-150;
	window.name = 'section';
	var url = "builderFormSection?formID=" + formID + "&action=" + action+"&sectionName="+sectionName+"&moduleId=<%=moduleId%>"; //P_FS_Enh_BuilderForm
	  openJqueryPopUp(url,550,250);
}
function configureTabularSectionDisplayColumns(formID,sectionName,isTabularSection,tabularSectionTableName, tabularSectionTableDBName, sectionDisplayName) {//BB-20150203-259 (Section Rename changes)
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	var x1 = (screen.width/2)-250;
	var y1 = (screen.height/2)-150;
	window.name = 'section';
	var url = "configureTabularSectionColumns?formID=" + formID +"&sectionName="+sectionName+"&moduleId=<%=moduleId%>&isTabularSection="+isTabularSection+"&tabularSectionTableDBName="+tabularSectionTableDBName+"&tabularSectionTableName="+tabularSectionTableName+"&sectionDisplayName="+sectionDisplayName; //P_FS_Enh_BuilderForm
	openJqueryPopUp(url,750,450);
	document.tabularSectionForm.formID.value = formID;
	document.tabularSectionForm.sectionName.value = sectionName;
	document.tabularSectionForm.isTabularSection.value = isTabularSection;
	document.tabularSectionForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
	document.tabularSectionForm.tabularSectionTableName.value = tabularSectionTableName;
	//document.tabularSectionForm.submit();
}
function addDocument(formID, table, section, action,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	var x1 = (screen.width/2)-250;
	var y1 = (screen.height/2)-150;
	window.name = 'document';
	var url = "builderFormDocument?formID=" + formID + "&tableName=" + table + "&section=" + section + "&action=" + action+"&moduleShortName=<%=moduleShortName%>&isTabularSection="+isTabularSection+"&tabularSectionTableDBName="+tabularSectionTableDBName+"&tabularSectionTableName="+tabularSectionTableName; //P_FS_Enh_BuilderForm
	//var newwindow=window.open(url,'changeFormField','scrollbars=auto,menubar=no,location=no,toolbar=no,resizable= yes,status=no,width=460,height=200,top='+y1+',left='+x1);
    //if (window.focus) {
	  //  newwindow.focus()
	  
	  openJqueryPopUp(url,550,350);
	  
	//}
}

function go2(formID, name, section, tableName, action,isTabularSection,tabularSectionTableName, tabularSectionTableDBName) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	if (action == 'delete' || action == 'Delete') {
	//if (confirm("Are you sure to delete this Document?")) {//P_Admin_CM_B_38111
	if (FCI.Confirms(FCLangAdmin.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_DOCUMENT+"?")) {
		/* if("true"==isTabularSection){
			tableName=tabularSectionTableName;
		} */
		document.deleteForm.formID.value = formID;
		document.deleteForm.docName.value = name;
		document.deleteForm.section.value = section;
		document.deleteForm.tableName.value = tableName;
		document.deleteForm.action.value = 'delete';
		document.deleteForm.isTabularSection.value = isTabularSection;
		document.deleteForm.tabularSectionTableDBName.value = tabularSectionTableDBName;
		document.deleteForm.tabularSectionTableName.value = tabularSectionTableName;
		document.deleteForm.submit();
	}
	}else{
		var x1 = (screen.width/2)-250;
		var y1 = (screen.height/2)-150;
		window.name = 'document';
		var url = "builderFormDocument?formID=" + formID + "&<%=BuilderFormFieldNames.FIELD_NAME%>=" + name + "&tableName=" + tableName + "&section=" + section + "&action=" + action+"&moduleShortName=<%=moduleShortName%>&isTabularSection="+isTabularSection+"&tabularSectionTableDBName="+tabularSectionTableDBName+"&tabularSectionTableName="+tabularSectionTableName; //P_FS_Enh_BuilderForm
		//var newwindow=window.open(url,'changeFormField','scrollbars=auto,menubar=no,location=no,toolbar=no,resizable= yes,status=no,width=460,height=200,top='+y1+',left='+x1);
	    //if (window.focus) {
		  //  newwindow.focus()
		//}
		openJqueryPopUp(url,550,350);
	}
}
function deleteSection(formID, secname, action) {
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	if (action == 'delete' || action == 'Delete') {
		if (FCI.Confirms(FCLangAdmin.ARE_YOU_SURE_TO_DELETE_THIS_SECTION)) {
			document.deleteForm.formID.value = formID;
			document.deleteForm.secName.value = secname;
			document.deleteForm.action.value = action;
			document.deleteForm.submit();
		}
	}
}
function go1(formID,section,isCustomTab,sectionName) { //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	document.builderForm.fimFormId.value = formID;
	document.builderForm.section.value = section;
	document.builderForm.action = "modifyFimBuilderFieldPosition?isCustomTab="+isCustomTab+"&sectionName="+sectionName; //BB-20150203-259 (Add Preview Button in Modify Field Management Section)
	document.builderForm.submit();

}
//BB-20150203-259 (Section Re-ordering changes) starts
function modifySectionPosition(formID)
{
	document.builderForm.fimFormId.value = formID;
	document.builderForm.action = "modifyBuilderSectionPosition";
	document.builderForm.submit();
}
// BB-20150203-259 (Section Re-ordering changes) ends
</script>
<%@ include file= "/jsp/util/import.jsp"%>
<%
	request.setCharacterEncoding("UTF-8");
	String tableAnchorMain = null;//ENH_MODULE_CUSTOM_TABS
	String isCustomTab = null;//ENH_MODULE_CUSTOM_TABS
	String langUsed = LanguageUtil.getUserLanguage();
	if (langUsed == null || ("test").equalsIgnoreCase(langUsed))
		langUsed = "en";
	String fimSection = request.getParameter("fimSection");
	String fimDocument = request.getParameter("fimDocument");
	String returnAddValue = "";
	String returnTempAddValue = "";
	returnTempAddValue = (String) session.getAttribute("returnVal");

	if (returnTempAddValue != null
			&& returnTempAddValue.equalsIgnoreCase("returnAddValue")) {
		returnAddValue = returnTempAddValue;
		session.removeAttribute("returnVal");
	} else if (returnTempAddValue != null
			&& returnTempAddValue.equalsIgnoreCase("returnmodifyValue")) {
		returnAddValue = returnTempAddValue;
		session.removeAttribute("returnVal");
	} else if (returnTempAddValue != null
            && returnTempAddValue.equalsIgnoreCase("willTakeTime")) {
        returnAddValue = returnTempAddValue;
        session.removeAttribute("returnVal");
    }
	String sWhichFile = "";
	String sUseragent = request.getHeader("User-Agent");
	String action = request.getParameter(BuilderFormFieldNames.ACTION);
	String formID = request.getParameter(BuilderFormFieldNames.FORM_ID);
	String fieldID = request.getParameter("fieldID");
	String fieldName = request.getParameter("fieldName");
	
	boolean builderUpdate = false;
	int returnValue = -1;
	//Bug_27528 Start
	String duplicateReq;
	String randomNo = (String)session.getAttribute("randomNumber");
	String tokenNo = request.getParameter("token");
	
	if(StringUtil.isValid(randomNo) && StringUtil.isValid(tokenNo) && !randomNo.equals(tokenNo))
	{
		duplicateReq= "true";//Duplicate Request
	}
	else
	{
		duplicateReq=null;
	}
	//Bug_27528 Ends

	if (StringUtil.isValidNew(request.getParameter("modifyFld")) && "y".equals(request.getParameter("modifyFld")) && !StringUtil.isValid(duplicateReq)) { //Bug_27528
		MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = true;
		builderForm.setBuilderFormAddOrUpdate(request);
		/*if (builderUpdate) {
			String isActive = request
					.getParameter(BuilderFormFieldNames.IS_ACTIVE);
			String isMandatory = request
					.getParameter(BuilderFormFieldNames.IS_MANDATORY);
			String isPiiEnabled = request.getParameter("isPiiEnabled");
			String isCenterInfoField = request.getParameter("isCenterInfoField");

			if (StringUtil.isValidNew(isActive)
					&& "true".equals(isActive)) {
				returnValue = 2;
			} else if (StringUtil.isValidNew(isActive)
					&& "false".equals(isActive)) {
				returnValue = 3;
			} else if (StringUtil.isValidNew(isMandatory)
					&& "true".equals(isMandatory)) {
				returnValue = 4;
			} else if (StringUtil.isValidNew(isMandatory)
					&& "false".equals(isMandatory)) {
				returnValue = 5;
			} else if (StringUtil.isValidNew(isPiiEnabled)
					&& "true".equals(isPiiEnabled)) {
				returnValue = 7;
			} else if (StringUtil.isValidNew(isPiiEnabled)
					&& "false".equals(isPiiEnabled)) {
				returnValue = 6;
			}else if (StringUtil.isValidNew(isCenterInfoField)
					&& "true".equals(isCenterInfoField)) {
				returnValue = 11;
			} else if (StringUtil.isValidNew(isCenterInfoField)
					&& "false".equals(isCenterInfoField)) {
				returnValue = 12;
			}
		}*/
	}
	
	
	String fromTabularFields = (String)session.getAttribute("fromTabularFields");			//P_Enh_Multiple_Input_Tabular_Summary starts
	if("yes".equals(fromTabularFields))
	{
		returnValue = 13;
	}
	request.getSession().removeAttribute("fromTabularFields");								//P_Enh_Multiple_Input_Tabular_Summary ends
	
	//BB-20150203-259  (To make available all the fields of Form builder as keyword.) starts
	
	String isKeywordActivated = (String)request.getSession().getAttribute("isKeywordActivated");
	if(StringUtil.isValidNew(isKeywordActivated))
	{
		if ( "true".equals(isKeywordActivated) ) 
		{
			returnValue = 9;
		} else  {
			//DO NOTHING
		}
		request.getSession().removeAttribute("isKeywordActivated");
	}
	
		String isKeywordDeactivated = (String)request.getSession().getAttribute("isKeywordDeactivated");
		if(StringUtil.isValidNew(isKeywordDeactivated))
		{
		if ( "true".equals(isKeywordDeactivated)) 
		{
			returnValue = 10;
		}else	{
			//DO NOTHING
		}
		request.getSession().removeAttribute("isKeywordDeactivated");
	}
		//BB-20150203-259  (To make available all the fields of Form builder as keyword.) Ends
		
	String userTheme = (String) session.getAttribute("userTheme");
	if (sUseragent.indexOf("IE") != -1) {
		sWhichFile = "IE";
	} else {
		sWhichFile = "Nav";
	}
	String language = UserLanguageLocal.getUserLanguage();
	String tagKey1 = "helpfim"
			+ LanguageUtil.getUserLangForJs(language);
	String tagKey = "helpfim";
	if(MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING == true) {
        long i = 0;
        while(true) {
            i++;
            //Thread thread = (Thread)session.getAttribute("formGeneratorFieldAddThread");        // For Clustering Issue
            if(MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING == false) {
                //session.removeAttribute("formGeneratorFieldAddThread");
                break;
            }
        }
    }
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<%
	try {
		String addMoreFormFlag = "false";				//P_Enh_Multiple_Input_Tabular_Summary
		String sFimFormId = request.getParameter("formNames");
		sFimFormId = sFimFormId.trim();
		if((ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_MU.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_AREA.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName)) && !"43".equals(sFimFormId) && !"44".equals(sFimFormId) && !"45".equals(sFimFormId) && !"46".equals(sFimFormId) && !"47".equals(sFimFormId) && !"48".equals(sFimFormId) && !"5".equals(sFimFormId) && !"7".equals(sFimFormId) && !"93".equals(sFimFormId) && !"94".equals(sFimFormId) && !"95".equals(sFimFormId) && !"96".equals(sFimFormId) && !"97".equals(sFimFormId) && !"98".equals(sFimFormId))			//P_Enh_Multiple_Input_Tabular_Summary starts
		{
			addMoreFormFlag = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "ADD_MORE", "BUILDER_FORM_ID", sFimFormId);
		}																																								//P_Enh_Multiple_Input_Tabular_Summary ends
		List fieldList =  builderForm.getPiiFieldPasswordMap(sFimFormId);				//P_Enh_FC-76
		String secName = null;
		String docName = null;
		String formName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS","FORM_NAME","BUILDER_FORM_ID",sFimFormId);
		List specialFieldListForQualificationDetails = Arrays.asList("cashOnHand", "mortgages", "marketableSecurities", "accountsPayable", "accountsReceivable", "notesPayable", "retirementPlans", "loansOnLifeInsurance", "realEstate", "creditCardBalance", "personalProperty", "unpaidTaxes", "businessHoldings", "lifeInsurance", "otherAssets", "otherLiabilities"); 
		if (action != null && action.trim().equalsIgnoreCase("delete")) {
			//returnValue = builderForm.removeBuilderFormField(request);
			secName = request.getParameter("secName");
			docName = request.getParameter("docName");
			if (StringUtil.isValid(secName)
					|| StringUtil.isValid(docName)) {
				boolean fllg = builderForm
						.removeBuilderFormSection(request);
				returnValue = 1;
				sFimFormId = formID;
			} else {
				boolean fllg = builderForm
						.removeBuilderFormField(request);
				if (fllg) {
					//builderWebForm.removeColumnInfo("FIM_BUILDER_FIELD",new String[]{"FIELD_NAME","BUILDER_FORM_ID"},new String[]{fieldName,formID});//BB_Naming_Convention
					//builderWebForm.updateColumnInfo("CUSTOM_FIELD_DATA",new String[]{"IS_AVAILABLE"},new String[]{"Y"},new String[]{"FIELD_NAME","FORM_ID"},new String[]{fieldName,formID});//BB_Naming_Convention
				}
				returnValue = 1;
				sFimFormId = formID;
			}
		}
		if (sFimFormId == null || sFimFormId.equals("null")) {
			sFimFormId = "";
		}
		StringBuffer method = null;
		StringBuffer listOfActions = null;
		StringBuffer listOfLinks = null;
		StringBuffer windowStatus = null;
		StringBuffer complete = new StringBuffer();

		String addDepartmentHtml = "&nbsp;&nbsp;&nbsp;<a href='modifyFimBuilderFieldPosition' class='link-btn-blue'>"
				+ LanguageUtil.getString("Modify Fields Position",
						(String) session.getAttribute("userLanguage"))
				+ "</a>&nbsp;";
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="builderForm" action="builderWebForm" method="post" <%=Constants.FORM_ENCODING%>><%--BB_Naming_Convention --%>
<input type="hidden" name="tableAnchor" value="">
<input type="hidden" name="fieldName" value="">
<input type="hidden" name="isActive" value="">
<input type="hidden" name="section" value="">
<input type="hidden" name="modifyFld" value="">
<input type="hidden" name="isMandatory" value="">
<input type="hidden" name="performingOperation" value="">
<input type="hidden" name="insertFld" value="">
<input type="hidden" name="dbTableName" value="">
<input type="hidden" name="keywordName" value="">
<input type="hidden" name="fieldFormName" value="<%=PortalUtils.filterValue(formName)%>">
<input type="hidden" name="isKeyword" value="">
<input type="hidden" name="isPiiEnabled" value="">
<input type="hidden" name="isCenterInfoField" value="">
<input type="hidden" name="isOtherTableField" value="">
<input type="hidden" name="otherTableName" value="">
<input type="hidden" name="sdisplayName" value=""> <!--  BB-20141017-177  -->
<input type="hidden" name="displayName" value="">
<input type="hidden" name="statuschange" value=""> <!--  BB-20141017-177  -->
<input type="hidden" name="moduleId" value="<%=moduleId%>"><%--ENH_MODULE_CUSTOM_TABS--%>
<input type="hidden" name="subModuleName" value="<%=subModuleName%>" > <%--//PP_changes --%>
<input type="hidden" name="isTabularSection" value="" >
<input type="hidden" name="tabularSectionTableDBName" value="" >
<input type="hidden" name="tabularSectionTableName" value="" >
<%--Bug_27528 Starts--%>
<% 	
	String randomNumber	= IDGenerator.getNextKey();
	session.setAttribute("randomNumber", randomNumber);		
    boolean emptyFlag=false;
    SequenceMap builderFormFieldMap =null;
    FieldMappings mappings	= null;
    String gFormId = "";
%>
<input type="hidden" name="token" value="<%=randomNumber%>">
<%--Bug_27528 Ends--%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <input type="hidden" name ="fimFormId" value="<%=sFimFormId%>">
<tr>
    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
    <td class="text" height="19" valign="bottom">
    <% if("captivate".equals(subModuleName)) { //PP_changes starts %>
    <a href="administration"><u><%=LanguageUtil.getString("Admin", (String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="<%=href%>"><u><%=LanguageUtil.getString(moduleURL, (String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="franchiseGrantingActivity"><u><%=LanguageUtil.getString("Manage Candidate Portal",(String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=moduleName%>&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString("Manage Virtual Brochure Form",(String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<span class="text_b"><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></span>
    <% } else { %>
    <a href="administration"><u><%=LanguageUtil.getString("Admin", (String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="<%=href%>"><u><%=com.appnetix.app.util.base.MenuUtils.getMenuDisplayName(null, NewPortalUtils.getModuleKey(moduleURL), moduleURL)%></u></a>
    <%if(showFormGenLabel) { //P_Enh_Site_Clearance starts
    if("mu".equals(moduleName) || "area".equals(moduleName) || "fim".equals(sTabModuleName)){
    isFim=true;
    %>
    	&nbsp;&gt;&nbsp;<a href="builderWebForm?module=fim&moduleId=<%=moduleId1%>&<%=subModuleParam%>=<%=moduleName1%>"><u><%=LanguageUtil.getString(formGenLabel,(String) session.getAttribute("userLanguage"))%></u></a>&nbsp;&gt;&nbsp;<a href="builderWebForm?module=fim&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>"><u><%=LanguageUtil.getString(subModuleDisplayName,(String) session.getAttribute("userLanguage"))%></u></a>
    	<%}else{ %>
    	&nbsp;&gt;&nbsp;<a href="builderWebForm?module=<%=moduleName%>&moduleId=<%=moduleId%>"><u><%=LanguageUtil.getString(formGenLabel,(String) session.getAttribute("userLanguage"))%></u></a>
    <%}} //P_Enh_Site_Clearance ends%>
    &nbsp;&gt;&nbsp;<span class="text_b"><%=LanguageUtil.getString(formName, (String) session.getAttribute("userLanguage"))%></span>
    <% } //PP_changes ends %>
   
    </td>
    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
</tr>
<%--P_FIM_B_13738 starts line added after breadcrum --%>
<tr>
	<td></td>
	<td>
		<img height="6" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif">
	</td>
	<td></td>
</tr>
<tr>
	<td width="6"></td>
	<td bgcolor="#BBBBBB" style=""><img height="1" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"></td> 
	<td width="8"></td>
</tr>
<tr>
	<td></td>
	<td>
		<img height="6" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif">
	</td>
	<td></td>
</tr>
<tr>
	<td width="6"></td>
	<td>
		<% if("captivate".equals(subModuleName)) { //PP_changes starst %>
			<%@ include file="virtualBrochureTopFormBuilder.jsp" %>
		<% } //PP_changes ends %>
	</td>
	<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
</tr>


<%--P_FIM_B_13738 ends--%>
<%if(!StringUtil.isValidNew(sFimFormId)){ %>
<tr>
    <td colspan="3" height="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="6"></td>
</tr>
<tr>
    <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
    <td class="text" height="19" valign="bottom">
		<div id="instructiondiv" name="instructiondiv"
			style="position: relative; display: ;">
		<table class="newForm" border="0" cellpadding="0" cellspacing="0"
			width="100%" align="default">
			<tbody>
				<tr>
					<td valign="top" width="10"><img
						src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl.png"
						height="12" width="10"></td>
					<td class="crvBox2-top" width="475"></td>
					<td valign="top" width="15" align="right"><img
						src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png"
						height="12" width="16"></td>
				</tr>
				<tr>
					<td class="crvBox2-left"></td>
					<td class="crvBox2-bg" valign="top">

					<div id="noteDiv" style="padding:10px 5px 10px 50px;">

					<div class="text_b"><%=LanguageUtil
							.getString("Form Generator Instructions")%>
					:</div>
					<ul class="bodyText" style="width: 100%;">
						<li style="color: red"><span class="text_b"><%=LanguageUtil.getString("WARNING")%>:</span> <%=LanguageUtil.getString("Do not make changes to Forms when users are logged into the System")%></li>
						<li><span class="text_b"><%=LanguageUtil.getString("Section Management")%></span>
							<ul class="bodyText" style="width: 100%;">
								<li><%=LanguageUtil.getString("Built-in sections cannot be deleted, but new sections can be added or deleted.")%></li>
								<li><%=LanguageUtil.getString("Any section containing no active fields will be hidden from view.")%></li>
								<li><%=LanguageUtil.getString("Any section with at least two active fields will have the option to 'Modify Field Position'.")%></li>
							</ul>
						</li>
						<li><span class="text_b"><%=LanguageUtil.getString("Field Management")%></span>
							<ul class="bodyText" style="width: 100%;">
								<li><%=LanguageUtil.getString("Field labels can be modified (even for standard built-in fields)")%></li>
								<li><%=LanguageUtil.getString("Fields cannot be deleted if they have already been used to store data, but they can be modified.")%></li>
								<li><span class="text_b">"<%=LanguageUtil.getString("PII")%>" </span><%=LanguageUtil.getString("refers to <span class=\"text_b\">P</span>ersonally <span class=\"text_b\">I</span>dentifiable <span class=\"text_b\">I</span>nformation.  Access to fields containing PII requires a password defined in Admin > Configuration > PII Configuration")%></li>
								<li><%=LanguageUtil.getString("Even if a field is Active or Mandatory by default,  you can still change the PII status or modify the field label.")%></li>	
							</ul>
						</li>
						<li><span class="text_b"><%=LanguageUtil.getString("Document Management")%></span>
							<ul class="bodyText" style="width: 100%;">
								<li><%=LanguageUtil.getString("Built-in documents cannot be deleted, but custom documents can be added, modified, or deleted.")%></li>
							</ul>
						</li>
					</ul>
					</div>
					</td>
					<td class="crvBox2-right"></td>
				</tr>
				<tr>
					<td valign="bottom" align="left"><img
						src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png"
						height="17" width="10"></td>
					<td class="crvBox2-botom"></td>
					<td valign="bottom" align="right"><img
						src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png"
						height="17" width="16"></td>
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
    <td colspan="3" height="6"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="1" height="6"></td>
</tr>
<tr>
		<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
	<%-- 	<td align="left" width="100%" height="1" bgcolor="#bbbbbb"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td> --%>
		<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="1"></td>
</tr>
<%--
<tr>
    <td>&nbsp;</td>
    <td height="25" class="tb_hdr_b">&nbsp;<%=LanguageUtil.getString("Builder FIM Form",(String)session.getAttribute("userLanguage"))%></td>
    <td>&nbsp;</td>
</tr> --%>
<%
		if (builderFormMap == null) {
%>
<tr class="tb_data">
    <td>&nbsp;</td>
    <td class="urgent_fields">&nbsp;<%=LanguageUtil.getString(
							"Data could not be retrieved", (String) session
									.getAttribute("userLanguage"))%>.<%=LanguageUtil.getString(" Please try again later",
							(String) session.getAttribute("userLanguage"))%>.</td>
    <td>&nbsp;</td>
</tr>
<%
	} else {
%>
 <tr height="20px"></tr>
<tr>
    <td>&nbsp;</td>
    <td class="tb_data">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
       
        <tr class="thead hText16black">
        <%-- P_B_ADMIN_47194 : 11/09/2014 : SAATVIKA DHUMEJA --%>
            <td class="hText16black" nowrap height="25" width="10%">&nbsp;<%=LanguageUtil.getString("Form Name",
							(String) session.getAttribute("userLanguage"))%> :</td>
            <td width="5"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="5" height="1"></td>
             <!--P_CM_B_41638-->
            	<%
           		Iterator builderFormIter = builderFormMap.values().iterator();
            	if("site".equals(sTabModuleName)) { //P_Enh_Site_Clearance starts
   					if(builderFormIter.hasNext()) {
   						Info info = (Info) builderFormIter.next();
   						String sFormName = info.get(BuilderFormFieldNames.FORM_NAME);
            	%>
            	<td class="TextLbl" nowrap="nowrap">
            		<input type="hidden" name="formNames" id="formNames" value="<%=info.getString(BuilderFormFieldNames.BUILDER_FORM_ID)%>"> 
            		<%=LanguageUtil.getString(sFormName.toString(), (String) session.getAttribute("userLanguage")) %>
            	
            	<%}
            	} else { %>
            <td class="TextLbl_b">
              <div id="select" style:display="none">
                <select name="formNames"  id="formNames" class="multiList" onchange ="javascript:selectFimForm()">
                    <option value="null"><%=LanguageUtil.getString("All", (String) session
							.getAttribute("userLanguage"))%></option>
   
			<%
   			while (builderFormIter.hasNext()) {
   				Info info = (Info) builderFormIter.next();
   				gFormId = info
   						.getString(BuilderFormFieldNames.BUILDER_FORM_ID);
   				String sFormName = info
   						.get(BuilderFormFieldNames.FORM_NAME);
  				//ENH_MODULE_CUSTOM_TABS starts
   				if(gFormId.toString().equals(sFimFormId))
   				{
   					tableAnchorMain = info.get(FieldNames.TABLE_ANCHOR);
   					isCustomTab = info.get(FieldNames.IS_CUSTOM);
   				}
   				if("bQual".equals(sFormName)) {//BUG_60439 Prateek Sharma
   					continue;	
   				}
   				//ENH_MODULE_CUSTOM_TABS ends
   				
   %>
            <option value="<%=gFormId%>"<%if (gFormId.toString().equals(sFimFormId)) {%>     selected<%}%>><%=LanguageUtil.getString(sFormName,
								(String) session.getAttribute("userLanguage"))%></option>
<%
	}
%>
			</select> </div>
			<%} //P_Enh_Site_Clearance ends %>
            </td>
            <td width="2"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
            <td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="2" height="1"></td>
            <%
         
            	if (StringUtil.isValidNew(sFimFormId)) {
            %>

             <!--P_CM_B_41638 start-->
            <td width="50%" align="right" nowrap>
            <%
            request.setAttribute(BuilderConstants.TABLE_ID, "2");
			request.setAttribute("form-id", sFimFormId);
			request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,
					"yes");
			builderFormFieldMap = builderForm.getBuilderFieldsAllTablesMap(request); //P_Enh_Sync_Fields
					//P_Enh_Multiple_Input_Tabular_Summary starts
			int countActive = 0;
			System.out.println("moduleName>>> "+moduleName);
            if((ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_MU.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_AREA.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName) || "cm".equals(moduleName) || "lead".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName)) ) {
            	Boolean isTabularSection = false;
           	 Iterator mapIter = builderFormFieldMap.keys().iterator();
				 while (mapIter.hasNext()) {
					int key = Integer.parseInt("" + mapIter.next());
					SequenceMap mapFields = (SequenceMap) builderFormFieldMap.get(key);   
					isTabularSection = (Boolean) mapFields.get("isTabularSection");
					if(!isTabularSection){
						countActive = Integer.parseInt((String) mapFields.get("mapActiveFldSize"));
						if(countActive > 0){
							addMoreFormFlag = "true";
							break;
						}else{
							addMoreFormFlag = "false";
						}
						/*if(countActive < 0){
							addMoreFormFlag = "false";
						}*/
					}else{
						addMoreFormFlag = "false";
					}
				 }
            }
			int sectionOrder = 0;
			if (builderFormFieldMap != null && builderFormFieldMap.size() > 0){
				sectionOrder = builderFormFieldMap.size();
			}
			if (builderFormFieldMap == null || builderFormFieldMap.size() == 0){
            	emptyFlag=true;
            	addMoreFormFlag = "false";											//P_Enh_Multiple_Input_Tabular_Summary
            }%>	<!-- <a rel="subcontent" onClick ="FCI.Messages(FCLang.NO_FORM_FIELD_FOUND);" id="previewlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:1px;"><%=LanguageUtil.getString("Preview Form")%></span></a> -->
            <% if (!dataPresent){%>	
            <a rel="subcontent" onClick ="FCI.Messages(FCLang.NO_FORM_FIELD_FOUND);" id="previewlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:1px;"><%=LanguageUtil.getString("Preview Form")%></span></a>
            <%} else{ %>
            <%if((ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_MU.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FIM_AREA.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName) || "cm".equals(moduleName)  || "lead".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName))) {%>
            <%if(countActive > 0){ %>
            <a rel="subcontent" onClick ="previewFormFunc('<%=sFimFormId%>','<%=isCustomTab%>','<%=moduleName%>')" id="previewlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:1px;"><%=LanguageUtil.getString("Preview Form")%></span></a>
            </td>
            <td class="hp5" style="padding-right:5px">
			<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/wSep.png" height="20" width="5"></td>
            <%}
            }else{ %>
            <a rel="subcontent" onClick ="previewFormFunc('<%=sFimFormId%>','<%=isCustomTab%>','<%=moduleName%>')" id="previewlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:1px;"><%=LanguageUtil.getString("Preview Form")%></span></a>
            </td>
            <td class="hp5" style="padding-right:5px">
			<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/wSep.png" height="20" width="5"></td>
            <%}} %>
            <td nowrap>
            <a rel="subcontent" onClick ="addSection('<%=sFimFormId%>', 'add', '<%=sectionOrder%>')" id="addsectionlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:8px;"><%=LanguageUtil.getString("Add Section")%></span></a> </td>
             <!--P_CM_B_41638 end-->
             <%if(builderFormFieldMap != null && builderFormFieldMap.size() > 1) { %>
             <td class="hp5" style="padding-right:5px">
			 <img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/wSep.png" height="20" width="5"></td>
             <td nowrap>
             <a rel="subcontent" onClick ="modifySectionPosition('<%=sFimFormId%>')" id="modifysectionpositionlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:8px;"><%=LanguageUtil.getString("Modify Sections Position")%></span></a> </td>  <!-- P_ENH_FORMBUILDER_SECTION_ORDER  -->
             <%} %>
             <%if("true".equals(addMoreFormFlag)){%>
 			<td class="hp5" style="padding-right:5px">
 			<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/wSep.png" height="20" width="5"></td>
             <td nowrap>
             <a rel="subcontent" onClick ="configureTabularField('<%=sFimFormId%>')" id="tabularFieldlink" class="link-btn-blue" href="javascript:void(0)"><span style="padding-right:1px;"><%=LanguageUtil.getString("Configure Tabular View")%></span></a> </td>
             <%}								//P_Enh_Multiple_Input_Tabular_Summary ends
             } else {
            %>
            <td width="100%" align="right">
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
		if (!sFimFormId.equals("")) {
		
			if (emptyFlag) {//ENH_MODULE_CUSTOM_TABS
%>
<tr height="20"></tr>
<tr height="25">
<td></td>
    <td class="bText12 grAltRw3" colalign="left" width="8" nowrap="nowrap" >&nbsp;<%=LanguageUtil.getString("No Form Field Added",
								(String) session.getAttribute("userLanguage"))%></td>

</tr>
<tr height="20"></tr>
<%
	} else {
				int fieldSize = builderFormFieldMap.size();
%>
<tr>
			<td  height="10" colspan=3><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif' width=1></td>
		</tr>
<tr>
    <td>&nbsp;</td>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
	if (returnValue == 1) {
					String fldTtype = "";
					if (StringUtil.isValid(docName)) {
						fldTtype = "Document";
					} else if (StringUtil.isValid(secName)) {
						fldTtype = "Section";
					} else {
						fldTtype = "Field";
					}
%>
		<tr id="deleteMessage">
			<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(fldTtype
									+ " has been deleted successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
		</tr>
<%
	}
				if (returnValue == 2) {
%>
<tr id="deleteMessage">
	<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been De-activated successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
</tr>
<%
	}
				if (returnValue == 4) {
%>
			<tr id="deleteMessage">
				<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been made Mandatory",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
			</tr>
<%
	}
				if (returnValue == 5) {
%>
			<tr id="deleteMessage">
				<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been made Non Mandatory",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
			</tr>
<%
	}
				if (returnValue == 6) {
%>
						<tr id="deleteMessage">
							<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"PII Enabled for Field", (String) session
											.getAttribute("userLanguage"))%>.</td>
						</tr>
			<%
				}
							if (returnValue == 7) {
			%>
						<tr id="deleteMessage">
							<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"PII Disabled for Field", (String) session
											.getAttribute("userLanguage"))%>.</td>
						</tr>
			<%
				}
						if (returnValue == 11) {
							%>
									<tr id="deleteMessage">
										<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
												"Field is removed from "+centerInfoLabel, (String) session
														.getAttribute("userLanguage"))%>.</td>
									</tr>
						<%
							}
							if (returnValue == 12) {
						%>
									<tr id="deleteMessage">
										<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
												"Field is added to "+centerInfoLabel, (String) session
														.getAttribute("userLanguage"))%>.</td>
									</tr>
						<%
							}
							if (returnValue == 3) {
			%>
			<tr id="deleteMessage">
				<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been Activated successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
			</tr>
<%
	}         //BB-20150203-259 Starts (To make available all the fields of Form builder as keyword.)
							if (returnValue == 9) {                
								%>
											<tr id="deleteMessage">
												<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
																	"Field has been activated as keyword",
																	(String) session
																			.getAttribute("userLanguage"))%>.</td>
											</tr>
								<%
									}
							if (returnValue == 10) {
								%>
											<tr id="deleteMessage">
												<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
																	"Field has been deactivated as keyword",
																	(String) session
																			.getAttribute("userLanguage"))%>.</td>
											</tr>
								<%
									}
							if (returnValue == 13) {										//P_Enh_Multiple_Input_Tabular_Summary starts
								%>
											<tr id="deleteMessage">
												<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
																	"Tabular Summary Fields is successfully configured.",
																	(String) session
																			.getAttribute("userLanguage"))%></td>
											</tr>
								<%
									}																		// P_Enh_Multiple_Input_Tabular_Summary ends
							//BB-20150203-259 Ends (To make available all the fields of Form builder as keyword.)
				if (returnAddValue.equalsIgnoreCase("returnAddValue")) {
%>
		<tr id="addMessage">
			<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been added successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
		</tr>
<%
	}
    if (returnAddValue.equalsIgnoreCase("willTakeTime")) {
%>
<tr id="addMessage">
    <td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
            "Add / Modify field request is under process. It may take a couple of minutes, please check again after sometime",
            (String) session
                    .getAttribute("userLanguage"))%>.</td>
</tr>
<%
    }

    if (returnAddValue.equalsIgnoreCase("returnmodifyValue")) {
%>
		<tr id="modifyMessage">
			<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Field has been modified successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
		</tr>
<%
	}
				if (fimSection != null && "done".equals(fimSection)) {
%>
		<tr id="addMessage">
			<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Section has been added successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
		</tr>
<%
	}
				//BB-2015-02-24 starts
				if (fimSection != null && "modifydone".equals(fimSection)) {
					
					%>
			<tr id="modifyMessage">
			   <td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Section has been modified successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
		    </tr>
					
					<%
				}
				//P_ENH_FORMBUILDER_SECTION_RENAME ends
				if (fimDocument != null && "add".equals(fimDocument)) {
%>
			<tr id="addMessage">
				<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Document has been added successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
			</tr>
	<%
		}
					if (fimDocument != null && "modify".equals(fimDocument)) {
	%>
	
			<tr id="addMessage">
				<td colspan = "4" align = 'center' class = "avText" style="padding-top:4px;"><%=LanguageUtil.getString(
									"Document has been modified successfully",
									(String) session
											.getAttribute("userLanguage"))%>.</td>
			</tr>

<%
	}
				int i = 1;
				Iterator mapIter = builderFormFieldMap.keys()
						.iterator();
				while (mapIter.hasNext()) {
					int key = Integer.parseInt("" + mapIter.next());


					SequenceMap mapFields = (SequenceMap) builderFormFieldMap
							.get(key);
					Iterator sizeIter = mapFields.values().iterator();
					int countt = 0;
					int countActive = 0;
					while (sizeIter.hasNext()) {
						Object obj = sizeIter.next();
						if (obj instanceof Info) {
							++countt;
						} else {
							continue;
						}
					}
					fieldSize = countt;

					String mapName = (String) mapFields.get("mapName");
					//P_CM_BUG_35370 	STARTS
					if("cm".equals(sTabModuleName) && "smsSubscription".equals(mapName) )//P_B_CM_76509
					{
						continue;
					}
					if("lead".equals(sTabModuleName) && "smsSubscription".equals(mapName) )//P_B_CM_76509
					{
						continue;
					}
                    if("fs".equals(sTabModuleName) && "callInformation".equals(mapName))        //We will not show Call Information on Detail and Add Page. So will not be configurable from form builder.
                    {
                        continue;
                    }
                    if("fs".equals(sTabModuleName) && "userLevelMessage".equals(mapName))        //We will not show Boefly User so that no modification will be done from form builder.//BUG_60439 Prateek Sharma
                    {
                        continue;
                    
                    }if("cm".equals(sTabModuleName) && "accountInformation".equals(mapName) && !userRoleMap.isPrivilegeInMap("/companySummary")){//P_CM_B_68658
                    	
                    	continue;
                    }
                    if("cm".equals(sTabModuleName) && !MultiTenancyUtil.getTenantConstants().IS_MBO_INTEGRATION_ENABLED && "membershipInformation".equals(mapName)){//P_CM_B_68658
                    	continue;
                    }
					//P_CM_BUG_35370 	ends
					boolean isOptional = Boolean.parseBoolean(""+mapFields.get("isOptional"));//P_E_MoveToFim_AddlFDD
					String mapValue = (String) mapFields
							.get("mapValue");
					String mapSection = (String) mapFields
							.get("mapSection");
					String mapTableName = (String) mapFields
							.get("mapTableName");
					String mapTableDBName = (String) mapFields
							.get("mapTableDBName");
					//String mapActiveFldSize = (String)mapFields.get("mapActiveFldSize");
					countActive = Integer.parseInt((String) mapFields
							.get("mapActiveFldSize"));
					DocumentMap[] mapDocs = (DocumentMap[]) mapFields
							.get("mapDocs");
					Boolean isTabularSection=(Boolean) mapFields
							.get("isTabularSection");
					String parentTableName=(String) mapFields
							.get("parentTableName");
					String tabularSectionTableName=(String) mapFields
							.get("tabularSectionTableName");
					String tabularSectionTableDBName=(String) mapFields
							.get("tabularSectionTableDBName");
					//P_E_MoveToFim_AddlFDD
					if(isOptional){
						continue;
					}
%>
        <tr>
            <td>
            <table border=0 cellspacing=0 cellpadding=0 width=100%>
            
            <tr>
                <td>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="24%" valign="bottom" nowrap="nowrap">
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10" class="hText18themeNew vp5"><span class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="1" width="10"></span></td>
                                <%if(isTabularSection) { %>
                                <td  class="hText18themeNew vp5" nowrap="nowrap"><%=LanguageUtil.getString(mapValue)%>&nbsp;&nbsp;<img title="<%=LanguageUtil.getString("Tabular Section")%>" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/mailtemplate/icnTable.png" width="14" height="12" border="0" /></td>
                                <%} else { %>
                                	<td  class="hText18themeNew vp5" nowrap="nowrap"><%=LanguageUtil.getString(mapValue)%></td>
                                <%} %>
                                
                                <td></td>
                                <td class="hp5">
														<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/wSep.png" height="20" width="5"></td>
				<%
					if (countt == 0
											&& (mapDocs == null || mapDocs.length == 0)) {
				%>
                <td class="text_b" align="center" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<a class="link-btn-blue" href="javascript:deleteSection('<%=sFimFormId%>','<%=mapName%>','delete')"><%=LanguageUtil.getString(
										"Delete Section", (String) session
												.getAttribute("userLanguage"))%></a>&nbsp;</td>
				<%
					}
				%>
				<%if(!("fs".equals(sTabModuleName) && "preferredLocations".equals(mapName))) { %>
                <td  class="text_b"  align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:go('<%=sFimFormId%>','0','<%=mapTableName%>','no','<%=fieldSize%>','<%=mapSection%>','false','false','','true','','add','','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>')"><%=LanguageUtil.getString("Add New Field",
									(String) session
											.getAttribute("userLanguage"))%></a>&nbsp;</td><!-- P_B_ADMIN_44180 -->
				<%} %>
                <%
                	if (countActive > 1 && !isTabularSection) {
                %>
                <%--BB-20150203-259 (Add Preview Button in Modify Field Management Section) --%>
                <td class="text_b" align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:go1('<%=sFimFormId%>','<%=mapSection%>', '<%=isCustomTab%>', '<%=mapName%>')"><%=LanguageUtil.getString(
										"Modify Fields Position",
										(String) session
												.getAttribute("userLanguage"))%></a>&nbsp;</td>
                 <%
                 	}
                  if("franchisees".equals(mapTableName) && "1".equals(mapSection)) {
                  %>
                 <td class="text_b" align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:go1('40','<%=mapSection%>', '<%=isCustomTab%>')"><%=LanguageUtil.getString(
										"Modify Fields Position for "+centerInfoLabel+" Display",
										(String) session
												.getAttribute("userLanguage"))%></a>&nbsp;</td>
                 <%
                  }
                 	//if (mapSection.equals("1") && mapDocs != null) {
                 %>
                 <%if(!("fs".equals(sTabModuleName) && "preferredLocations".equals(mapName))) { %>
				<td class="text_b" align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:addDocument('<%=sFimFormId%>','<%=mapTableName%>','<%=mapSection%>','add','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>')"><%=LanguageUtil.getString("Add Document",(String) session.getAttribute("userLanguage"))%></a>&nbsp;</td>
				<%} %>
				
				<td class="text_b" align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:modifySection('<%=sFimFormId%>','<%=mapName%>','modify')"><%=LanguageUtil.getString("Modify Section",(String) session.getAttribute("userLanguage"))%></a>&nbsp;</td> <!-- P_ENH_FORMBUILDER_SECTION_RENAME -->
				<%if(isTabularSection && countActive>1){ %>
					<td class="text_b" align="center" nowrap="nowrap"><a class="link-btn-blue" href="javascript:configureTabularSectionDisplayColumns('<%=sFimFormId%>','<%=mapName%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>','<%=mapValue%>')"><%=LanguageUtil.getString("Configure Display Columns",(String) session.getAttribute("userLanguage"))%></a></td>
				<%} %> 
                
                              </tr>
                            </table>
                   </td>
                   
                 </tr>
                </table>
                
                </td>
            </tr>
            
            <tr>
                <td>
                    <table border=0 cellspacing=0 cellpadding=0 width=100% >

							<tr>
		                     <%--  <td width="10" valign="top"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/rTabOff-fill.gif" width="10" height="12" /></td>
		                      <td width="100%" class="crvBox2-top"></td>
		                      <td width="15" align="right" valign="top"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png" width="16" height="12" /></td> --%>
		                    </tr>
                            <tr>
                                <td ><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
                                <td>
                
                								<%
												//P_B_74104 Starts
												boolean showKeyWords=true;
												if("fim".equals(sTabModuleName) && ( "43".equals(formId) || "45".equals(formId) || "46".equals(formId) ))
												{
													showKeyWords =false; 
												}
												else if("fs".equals(sTabModuleName) && ( "44".equals(formId) || "47".equals(formId) || "48".equals(formId) ))
												{
													showKeyWords =false; 	
												}else if("42".equals(formId)){			//P_B_78236 site Clearence
													showKeyWords =false; 					
												}
												
												//P_B_74104 ends
												//P_B_77694 STARTS Keywords for multiple input tab will not come
												if("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName))
														{
															boolean multipleInputTab="true".equals(SQLUtil.getQueryResult("SELECT ADD_MORE FROM BUILDER_WEB_FORMS,USER_TAB_CONFIG WHERE USER_TAB_CONFIG.NAME=BUILDER_WEB_FORMS.FORM_NAME AND (SUB_MODULE='franchiseewithoutsc' OR SUB_MODULE='null') AND BUILDER_WEB_FORMS.MODULE='fim' AND BUILDER_WEB_FORMS.BUILDER_FORM_ID!='' AND IS_ACTIVE='Y' AND BUILDER_FORM_ID="+formId+" UNION ALL SELECT ADD_MORE FROM BUILDER_WEB_FORMS WHERE IS_CUSTOM='Y' AND MODULE='fim' AND IS_ACTIVE='Y' AND BUILDER_FORM_ID="+formId,"ADD_MORE"));
															
															if(multipleInputTab)
															{
																showKeyWords=false;
															}
															if(isTabularSection){
																showKeyWords=false;
															}
														}
												//P_B_77694  P_B_81951 ENDS
												%>
                
							        <table border=0 cellspacing=1 cellpadding=4 width=100% >
							        <tr width="100%">
									<td colspan="8" width="100%">
									<table width="100%" cellspacing=1 cellpadding=4>
							        <%
							        	if (countt == 0 && mapDocs == null) {
							        %>
							        <tr class="bText12 grAltRw3">
							            <td class="botBorder" align="left" nowrap="nowrap" width=8> <%=LanguageUtil.getString(
										"No Fields Found", (String) session
												.getAttribute("userLanguage"))%>.</td>
							        </tr>
							        <%
							        	} else if (countt > 0) {
							        %>
							        <tr class="bText12" width=100%>
							            <td  class="thead headText12b" width="1%" height="20" align="right" nowrap="nowrap"><%=LanguageUtil.getString("No",
										(String) session
												.getAttribute("userLanguage"))%>.</td>
							            <td  class="thead headText12b" width="88%" nowrap="nowrap"><%=LanguageUtil.getString("Field Name",
										(String) session
												.getAttribute("userLanguage"))%></td>
							            <td  class="thead headText12b" width="2%" style="min-width:80px" nowrap="nowrap"><%=LanguageUtil.getString(
										"Display Type", (String) session
												.getAttribute("userLanguage"))%></td>
							            <td  class="thead headText12b" width="2%" style="min-width:60px" nowrap="nowrap"><%=LanguageUtil.getString("Validation",
										(String) session
												.getAttribute("userLanguage"))%></td>
							            <td  class="thead headText12b" width="2%" style="min-width:60px" nowrap="nowrap" align="center"><%=LanguageUtil.getString("Mandatory",
										(String) session
												.getAttribute("userLanguage"))%></td>
							            <td  class="thead headText12b" width="2%" style="min-width:60px" nowrap="nowrap" align="center"><%=LanguageUtil.getString("Active",
										(String) session
												.getAttribute("userLanguage"))%></td>
												<%
												if("on".equals(passwordFunctionality)) {
												if(!"scheduler".equals(sTabModuleName) ){ //P_SCH_ENH_008 Do not show pii in case of jobs
												%>
							            <td class="thead headText12b" width="2%" style="min-width:60px" nowrap="nowrap" align="center"><%=LanguageUtil.getString("PII",
										(String) session
												.getAttribute("userLanguage"))%></td> 
												<%} } %>
												<%--BB-20150203-259  (To make available all the fields of Form builder as keyword.) --%>
												<%if(!"5".equals(moduleId) && !"6".equals(moduleId) && !"mu".equals(subModuleName) && !"area".equals(subModuleName) && showKeyWords )  { //P_B_74104 %>
												<td  class="thead headText12b" width="1%" align="left" nowrap="nowrap" align="center"><%=LanguageUtil.getString("Keyword")%></td>
												<%} %>	
												<%if("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName)) {%>
													<%if(!"43".equals(formId) && !"45".equals(formId) && !"46".equals(formId) && !isTabularSection) { //P_Enh_ContactHistory_FormBuilder %>	
														<td  class="thead headText12b" width="1%" align="left" nowrap="nowrap" align="center"><%=LanguageUtil.getString("Add to "+centerInfoLabel, (String) session.getAttribute("userLanguage"))%></td>
													<%} %>
												<%} %>
							            <td  class="thead headText12b" width="1%" align="left" nowrap="nowrap" align="center"><%=LanguageUtil.getString("Action",
										(String) session
												.getAttribute("userLanguage"))%></td>
							        </tr>
									<%
										}
														Iterator builderFormFieldIter = mapFields.values()
																.iterator();
														int j = 1, k = 10;
														Info info = null;
														while (builderFormFieldIter.hasNext()) {
															String className = "bText12";
															if (j % 2 == 0)
																className = "bText12 grAltRw3";
															Object obj = builderFormFieldIter.next();
															if (obj instanceof Info) {
																info = (Info) obj;
																if("cm".equals(sTabModuleName) && "hotActivity".equals((String)info.getObject(BuilderFormFieldNames.FIELD_NAME)) && !ModuleUtil.landingPageImplemented()){
																	continue;
																}
															} else {
																continue;
															}

															int gFieldId = Integer
																	.parseInt(info
																			.getString(BuilderFormFieldNames.FIELD_ID));
															//int gCustomFormId = Integer.parseInt(info.getString(BuilderFormFieldNames.BUILDER_FORM_ID)) ;
															String sDisplayName = info
																	.get(BuilderFormFieldNames.DISPLAY_NAME);
															//P_Enh_Sync_Fields starts
															boolean syncWith = false;
															boolean isSyncDataPresent =false;
															String syncFieldName = "";
															if(StringUtil.isValidNew((String)info.get("syncWith"))) {
																syncWith = true;
																String tableName = (String)info.get("syncWith").split("##")[0];
																String fieldName1 = (String)info.get("syncWith").split("##")[1];
																String otherField = (String)info.get("syncWith").split("##")[2];
																mappings = builderForm.getFimFormBuilderMapping(tableName);
																String tabName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", "TABLE_ANCHOR", tableName);
																Field field = mappings.getField(fieldName1);
																if("true".equals(otherField)) {
																	field = mappings.getOtherTableField(fieldName1);
																}
																syncFieldName = LanguageUtil.getString(tabName) + " > " + LanguageUtil.getString(field.getDisplayName());
															}
															isSyncDataPresent = "yes".equals((String)info.get("syncData"))? true:false ;//P_B_77472
															
															//P_Enh_Sync_Fields ends
															//P_CM_B_38405 :starts
															if (sDisplayName.indexOf("$") != -1) {
																sDisplayName=sDisplayName.replace("$", MultiTenancyUtil.getTenantConstants().USER_CURRENCY);
															}//P_CM_B_38405 :ends
															String sDataType = info
																	.get(BuilderFormFieldNames.DATA_TYPE);
															String gFieldName = (String) info
																	.getObject(BuilderFormFieldNames.FIELD_NAME);
															//int gOrder = Integer.parseInt(info.getString(BuilderFormFieldNames.ORDER)) ;
															String gNoOfRows = info
																	.get(BuilderFormFieldNames.NO_OF_ROWS);
															String gNoOfCols = info
																	.get(BuilderFormFieldNames.NO_OF_COLS);
															String tableAnchor = info
																	.get(BuilderFormFieldNames.TABLE_ANCHOR);
															boolean sActive = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_ACTIVE));
															boolean sBuildFld = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_BUILD_FIELD));
															boolean sMandatoryFld = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_MANDATORY));
															boolean isSourceField = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_SOURCE_FIELD));

															String displayType = info
																	.get(BuilderFormFieldNames.DISPLAY_TYPE);
															//added by akash kumar for formbuilder multiselect combo starts
															String isMultiSelect=info.get(BuilderFormFieldNames.IS_MULTISELECT);
															//added by akash kumar for formbuilder multiselect combo ends
															boolean isParsingKeywordConfigured =Boolean
															.parseBoolean(info.get(BuilderFormFieldNames.IS_PARSING_KEYWORD_CONFIGURED));		
															String validationType = info
																	.get(BuilderFormFieldNames.FLD_VALIDATION_TYPE);
															if ((displayType.equals("Text") || displayType
																	.equals("Numeric"))
																	&& StringUtil.isValid(validationType)) {
																if ("Mandatory".equals(validationType)) {
																	validationType = "--";
																} else if ("Double".equals(validationType)) {
																	validationType = "Currency";
																	if(validationList.contains(gFieldName)) {
																		validationType = "Numeric";
																	}
																}else if("phoneExt".equals(validationType)){//EXTERNAL_FORM_BUILDER : START
																	validationType = "--";
																} else {//EXTERNAL_FORM_BUILDER : END
																}
															} else if (displayType.equals("Date")) {
																validationType = "Date";
															} else {
																validationType = "--";
															}
															String section = info
																	.get(BuilderFormFieldNames.SECTION);
															boolean sMailMergeActive = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.MAIL_MERGE_ACTIVE));
															boolean sOtherTableField = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD));
															String sOtherTFld = info
																	.getString(BuilderFormFieldNames.OTHER_TABLE_NAME);
															boolean isPiiEnabled = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_PII_ENABLED));

															boolean isCenterInfoField = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_CENTERINFO_FIELD));
															

															boolean isDefaultPii = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_DEFAULT_PII)); // BB-20150525-360

															String sDropdownOpt = info
																	.getString(BuilderFormFieldNames.DROPDOWN_OPTION);
															boolean isParent = Boolean
																	.parseBoolean(info
																			.getString(BuilderFormFieldNames.IS_PARENT));
															String sDependentFld = info
																	.getString(BuilderFormFieldNames.DEPENDENT_FLD);

															String sActiveVal = "";
															String sMandatoryVal = "";
															boolean checkValue = false;
															boolean checkAuditValue = false;
															if (!sBuildFld) {
																checkValue = builderForm
																		.checkColumnValueInputInTable(
																				mapTableDBName,
																				info
																						.get(BuilderFormFieldNames.DB_FIELD));
																checkAuditValue = builderForm
																		.checkColumnValueInputInAuditTable(
																				mapTableName,
																				(String) info
																						.getObject(BuilderFormFieldNames.FIELD_NAME));
																
															if("cm".equals(sTabModuleName) && sOtherTableField && StringUtil.isValid(sOtherTFld) && sOtherTFld.startsWith("cmDocuments_")){//P_CM_B_60109
																
																checkValue=builderForm.checkColumnValueForCMFileField(sOtherTFld);
															}
															}
															if (sActive) {
																sActiveVal = "Active";
															} else {
																sActiveVal = "De-Active";
															}
															if (sMandatoryFld) {
																sMandatoryVal = "Yes";
															} else {
																sMandatoryVal = "No";
															}
															
															boolean isFieldAvailable = false;
															if (!sDataType.equals("")
																	&& !sDataType.equals("null")
																	&& sDataType != null)
																k--;
									%>
							        <tr class="<%=className%>">
							            <td class="botBorder" align="right" width=2 nowrap="nowrap"> <%=j%>
							            </td>
							            <td>
							            	<%=LanguageUtil.getString(sDisplayName)%>
							            	<%if(syncWith) { //P_Enh_Sync_Fields starts %>
							            		<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/syncIcn.png" border="0" class="appnetixToolTip" onmouseover="showAppnetixToolTip('<%=LanguageUtil.getString("Indicates the Field is sync with")%> <%=syncFieldName%>.')">
							            	<%} //P_Enh_Sync_Fields ends
							            	if("on".equals(passwordFunctionality) && isPiiEnabled && fieldList!=null && fieldList.contains(gFieldName))			//P_Enh_FC-76 starts
							            	{%>
							            	<img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/green.png" border="0" class="appnetixToolTip" onmouseover="showAppnetixToolTip('<%=LanguageUtil.getString("Indicates the Field Level Password is Configured")%>.')">
							            	<%} %>															<!-- P_Enh_FC-76 ends -->
							            </td>
							<%
								if (!"".equals(displayType)
															&& "TextArea".equals(displayType)) {
														displayType = "Text Area";
													} else if ("Combo".equals(displayType)) {
														//displayType = "Drop-down"; 
														//added by akash kumar for formbuilder multiselect combo starts
														if("true".equals(isMultiSelect))
														{
														displayType=LanguageUtil.getString("Multi Select Drop-down");
														}
														else if("2".equals(sDropdownOpt) || "1".equals(sDropdownOpt))   //BB-20150203-259  (To make available all the fields of Form builder as keyword.)
														{
															displayType = "State/Country";
														}else
														{
															displayType = "Drop-down";
														}
														//added by akash kumar for formbuilder multiselect combo ends
													} else if ("Numeric".equals(displayType)) {
														displayType = "Numeric";
													}
							boolean sequenceFlag = false;            //P_Enh_Form_Builder_Option_Sequence starts
							if("true".equals(isMultiSelect) || "Drop-down".equals(displayType) || "Checkbox".equals(displayType) || "Radio".equals(displayType))
							{
								sequenceFlag = true;
							}									//P_Enh_Form_Builder_Option_Sequence ends
							%>
							            <td class="botBorder"  nowrap="nowrap"><%=LanguageUtil.getString(displayType,
										(String) session
												.getAttribute("userLanguage"))%></td>
							            <td class="botBorder" nowrap="nowrap"><%=LanguageUtil.getString(
										validationType, (String) session
												.getAttribute("userLanguage"))%></td>
							<%
								if (isSourceField || (ModuleUtil.zcubatorImplemented() && "File".equals(displayType))) {//P_CM_B_73513: disable manadatory File type field 
														if (sMandatoryFld) {
							%>
					            		<td class="botBorder" nowrap="nowrap" align="center"><img class="transparent_class" src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0"></td>
							<%
								} else {
							%>
							            <td class="botBorder" nowrap="nowrap" align="center"><img class="transparent_class" src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0"></td>
							<%
								}
													} else if (sMandatoryFld) {
							%>
							            <td nowrap="nowrap" class="botBorder" align="center"><a href="javascript:void(0);" onClick="onFieldCall1('<%=tableAnchor%>', '<%=gFieldName%>','<%=!sMandatoryFld%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to make Non Mandatory")%>"></a></td>            
							<%
            								} else {
            							%>
							             <td nowrap="nowrap" align="center" class="botBorder" ><a href="javascript:void(0);" onClick="onFieldCall1('<%=tableAnchor%>', '<%=gFieldName%>','<%=!sMandatoryFld%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to make Mandatory")%>"></a></td>
							<%
								}
							%>
							<%
								if (isSourceField) {
														if (sActive) {
							%>
							            		<td nowrap="nowrap" class="botBorder" align="center"><img class="transparent_class" src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0"></td>
							<%
								} else {
							%>
							            <td class="botBorder" nowrap="nowrap" align="center"><img class="transparent_class" src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0"></td>
							<%
								}
													} else {
														if(specialFieldListForQualificationDetails.contains(gFieldName) && "fsLeadQualificationDetail".equals(tableAnchor)) {
															boolean checkValueForQualification = builderForm.checkColumnValueInputInTable(mapTableDBName, info.get(BuilderFormFieldNames.DB_FIELD));
															if(checkValueForQualification) {
															%>
																<td nowrap="nowrap" class="botBorder" align="center"><img class="transparent_class" src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0"></td>
															<%} else { 
																if (sActive) {
															%>
																<td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onFieldCall('<%=tableAnchor%>', '<%=gFieldName%>','<%=sActive%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isParent%>','<%=sDependentFld%>','','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to De-Activate this Field")%>"></a></td><!--Bug_26819-->
															<%} else {%>
																<td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onFieldCall('<%=tableAnchor%>', '<%=gFieldName%>','<%=sActive%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isParent%>','<%=sDependentFld%>','','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to Activate this Field")%>"></a></td><!--Bug_26819-->
															<%} 
															} 
														}else {
															if (sActive) {
							%>
							            <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onFieldCall('<%=tableAnchor%>', '<%=gFieldName%>','<%=sActive%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isParent%>','<%=sDependentFld%>','<%=StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(sDisplayName))%>','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to De-Activate this Field")%>"></a></td><!--Bug_26819-->							            
							<%
							            								} else {
							            							%>
							             <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onFieldCall('<%=tableAnchor%>', '<%=gFieldName%>','<%=sActive%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isParent%>','<%=sDependentFld%>','<%=StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(sDisplayName))%>','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to Activate this Field")%>"></a></td><!--Bug_26819-->
							<%
								}}}
							if("on".equals(passwordFunctionality)) {
							if(!"scheduler".equals(sTabModuleName)){ //P_SCH_ENH_008 Do not show pii in case of jobs
													if (((!"cm".equals(sTabModuleName) || !("facebook".equals(gFieldName) || "twitter".equals(gFieldName) || "Youtube".equals(gFieldName) || "linkedin".equals(gFieldName) || "google".equals(gFieldName))) && (displayType.equals("Text") || displayType.equals("Numeric") || "Date".equals(displayType)))&&isDefaultPii) { //P_CM_BUG_50412    // BB-20150525-360(added  isDefaultPii which is isBuildField&&defaultpiiEnabled)
														if(syncWith || isSyncDataPresent ){//P_B_77472
							%>								
							<td class="botBorder"  nowrap="nowrap" align="center">--</td>	
							<%		}else if (isPiiEnabled) {
							%>
		             					<td nowrap="nowrap" class="botBorder" align="center"><a href="javascript:void(0);" onClick="onFieldCall2('<%=tableAnchor%>', '<%=gFieldName%>','<%=isPiiEnabled%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to Disable PII")%>"></a></td>
							<%
								} else { 
							%>	
								             <td class="botBorder"  nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="onFieldCall2('<%=tableAnchor%>', '<%=gFieldName%>','<%=isPiiEnabled%>','<%=section%>','<%=sOtherTableField%>','<%=sOtherTFld%>','<%=isCenterInfoField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to Enable PII")%>"></a></td>
							<%
								}
														} else {
							%>			
							             <td nowrap="nowrap" class="botBorder" align="center"> --</td>
							<%
								}
							%>			
							<%} }
							//BB-20150203-259 Starts  (To make available all the fields of Form builder as keyword.)
							boolean isFieldNamePresent = fieldMap.containsValue(gFieldName);
							//Disable keyword starts
							boolean isKeyWorddisable=false;
							if(("cm".equals(sTabModuleName) || "lead".equals(sTabModuleName)) && deactiveKeyWordMap != null){
								isKeyWorddisable=deactiveKeyWordMap.containsValue(gFieldName);
							}
							if((formId !=null && Integer.parseInt(formId)>82 && Integer.parseInt(formId)<93) || (isTabularSection && ("cm".equals(sTabModuleName) || "lead".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName))) ){//P_CM_B_74104 //P_CM_B_77645
								isKeyWorddisable=true;
							}
							//Disable keyword End
							if(!"5".equals(moduleId) && !"6".equals(moduleId) && !"mu".equals(subModuleName) && !"area".equals(subModuleName) && showKeyWords) {//P_B_74104
								if(!"File".equals(displayType) && ! isKeyWorddisable) {	//Disable keyword starts						
												 if (isFieldNamePresent) {%>
							            <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onKeywordDeactivate('<%=gFieldName%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to De-Activate this Keyword")%>"></a></td>						            
							<%
							            								} else {
							            							%>
							             <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return onKeywordActivate('<%=gFieldName%>','<%=tableAnchor%>', '<%=sOtherTableField%>','<%=isTabularSection%>','<%=tabularSectionTableName%>','<%=tabularSectionTableDBName%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to Activate this Keyword")%>"></a></td>
							<%
								}
								} else { %>
									<td nowrap="nowrap" class="botBorder" align="center"> --</td>
								<%} }
												//BB-20150203-259  (To make available all the fields of Form builder as keyword.)
							%>
							<%			//boolean isCenterInfoField = false;
							
							if("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName) && !sOtherTableField) {////Martin-20160727-017 
								if(!"43".equals(formId) && !"45".equals(formId) && !"46".equals(formId) && !isTabularSection) { //P_Enh_ContactHistory_FormBuilder 
												 if (isCenterInfoField) {%>
							            <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return addToCenterInfo('<%=gFieldName%>', '<%=tableAnchor%>','<%=sOtherTableField%>','<%=formName%>','<%=isCenterInfoField%>','<%=sOtherTFld%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/active.png" border="0" title="<%=LanguageUtil.getString("Click to remove from "+centerInfoLabel)%>"></a></td>						            
							<%
							            								} else {
							            							%>
							             <td class="botBorder" nowrap="nowrap" align="center"><a href="javascript:void(0);" onClick="return addToCenterInfo('<%=gFieldName%>','<%=tableAnchor%>', '<%=sOtherTableField%>','<%=formName%>','<%=isCenterInfoField%>','<%=sOtherTFld%>');"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/customform/deactive.png" border="0" title="<%=LanguageUtil.getString("Click to add to "+centerInfoLabel)%>"></a></td>
							<%
							          }
								}
							}else if("fim".equals(sTabModuleName) && !"mu".equals(subModuleName) && !"area".equals(subModuleName) && sOtherTableField){//Martin-20160727-017 starts%>
								<td class="botBorder" nowrap="nowrap" align="center">--</td>
							<%}//Martin-20160727-017 ends
												//BB-20150203-259  (To make available all the fields of Form builder as keyword.)
							%>
						
							        <td align="center" nowrap="nowrap" class="botBorder" >
							<%
								method = new StringBuffer();
													listOfActions = new StringBuffer();
													listOfLinks = new StringBuffer();
													windowStatus = new StringBuffer();
							%>
										<div id="menuBar" >
										    <layer ID="Actions_dynamicmenu<%=i%>Bar" ><a href="javascript:void(0);" onMouseOver="openPulldownMenu(event,'Actions_dynamicmenu<%=i%>');" onMouseOut="closePulldownMenu('Actions_dynamicmenu<%=i%>');"> <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/edit.gif" border="0" <%if(StringUtil.isValid(sDependentFld)){%> onload="saveActive('<%=StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(sDisplayName))%>','<%=gFieldName%>','<%=sActive%>');"<%}%> ></a></layer><%--TB_FORM_DISTORTION --%><!--Bug_26819-->
										</div>
							<%
								method
															.append("prepareActionsMenu2(listOfActions,listOfLinks, windowStatus, ");
													listOfActions.append("listOfActions = [");
													listOfLinks.append("listOfLinks = [");
													windowStatus.append("windowStatus = [");		
													//PW_B_26145 Start
													listOfLinks.append("'modify', ");
													listOfActions
															.append(" '"
																	+ LanguageUtil
																			.getString(
																					"Modify",
																					(String) request
																							.getSession()
																							.getAttribute(
																									"userLanguage"))
																	+ "', ");
													windowStatus
															.append(" '"
																	+ LanguageUtil
																			.getString(
																					"Modify Field",
																					(String) request
																							.getSession()
																							.getAttribute(
																									"userLanguage"))
																	+ "', ");
												if("33".equals(sFimFormId) && isParsingKeywordConfigured && !"File".equals(displayType)){
													listOfLinks.append("'keyword', ");
										          	listOfActions.append(" '"+ LanguageUtil.getString("Configure Parsing Keywords",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
										          	windowStatus.append(" '"+ LanguageUtil.getString("Configure Parsing Keywords",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
												}
										          	
													if (!sBuildFld && !checkValue
															&& !checkAuditValue) {
														listOfLinks.append("'delete', ");
														listOfActions
																.append(" '"
																		+ LanguageUtil
																				.getString(
																						"Delete",
																						(String) request
																								.getSession()
																								.getAttribute(
																										"userLanguage"))
																		+ "', ");
														windowStatus
																.append(" '"
																		+ LanguageUtil
																				.getString(
																						"delete",
																						(String) request
																								.getSession()
																								.getAttribute(
																										"userLanguage"))
																		+ "', ");
													}
													if (!sBuildFld && sequenceFlag && !syncWith) {                                    //P_Enh_Form_Builder_Option_Sequence starts
														listOfLinks.append("'sequence', ");
														listOfActions
																.append(" '"
																		+ LanguageUtil
																				.getString(
																						"Change Option Sequence",
																						(String) request
																								.getSession()
																								.getAttribute(
																										"userLanguage"))
																		+ "', ");
														windowStatus
																.append(" '"
																		+ LanguageUtil
																				.getString(
																						"Change Option Sequence",
																						(String) request
																								.getSession()
																								.getAttribute(
																										"userLanguage"))
																		+ "', ");														//P_Enh_Form_Builder_Option_Sequence ends
													}
													if("on".equals(passwordFunctionality)) 							//P_Enh_FC-76 starts
													{
														if(!"scheduler".equals(sTabModuleName))
														{ 
															if ((((!"cm".equals(sTabModuleName) || !("facebook".equals(gFieldName) || "twitter".equals(gFieldName) || "Youtube".equals(gFieldName) || "linkedin".equals(gFieldName) || "google".equals(gFieldName))) && (displayType.equals("Text") || displayType.equals("Numeric") || "Date".equals(displayType))) && isDefaultPii) || ("38".equals(sFimFormId) && ("birthMonth".equals(gFieldName) || "spouseBirthMonth".equals(gFieldName))) || ("37".equals(sFimFormId) && "birthMonth".equals(gFieldName)))
															{ 
																if (isPiiEnabled) 
																{
																	listOfLinks.append("'password', ");
																	listOfActions
																			.append(" '"
																					+ LanguageUtil
																							.getString(
																									"Configure Field PII Password",
																									(String) request
																											.getSession()
																											.getAttribute(
																													"userLanguage"))
																					+ "', ");
																	windowStatus
																			.append(" '"
																					+ LanguageUtil
																							.getString(
																									"Configure Field PII Password",
																									(String) request
																											.getSession()
																											.getAttribute(
																													"userLanguage"))
																					+ "', ");	
																}
															}
														}
													}
													if("on".equals(passwordFunctionality) && isPiiEnabled) 							
													{
														if(fieldList!=null && fieldList.contains(gFieldName))
														{
																listOfLinks.append("'deletePassword', ");
																listOfActions
																		.append(" '"
																				+ LanguageUtil
																						.getString(
																								"Remove Field PII Password",
																								(String) request
																										.getSession()
																										.getAttribute(
																												"userLanguage"))
																				+ "', ");
																windowStatus
																		.append(" '"
																				+ LanguageUtil
																						.getString(
																								"Remove Field PII Password",
																								(String) request
																										.getSession()
																										.getAttribute(
																												"userLanguage"))
																				+ "', ");	
														}
													}
													//PW_B_26145 End
													String sGoSingleAction = "javascript:go('"
															+ sFimFormId + "','" + gFieldName
															+ "','" + tableAnchor + "','"
															+ sBuildFld + "','" + fieldSize + "','"
															+ section + "','" + checkValue + "','"
															+ sOtherTableField + "','"
															+ sDropdownOpt + "','" + isParent
															+ "','" + sDependentFld + "',?,'"+StringUtil.removeAppostrophiSpecial(sDisplayName)+"','"+isTabularSection+"','"+tabularSectionTableName+"','"+tabularSectionTableDBName+"','"+regularSectionCount+"','"+tabularSectionCount+"','"+sActive+"')";
													windowStatus.append("''];\n");
													listOfLinks.append("''];\n");
													listOfActions.append("''];\n");
													method.append(i + ", " + "\"" + sGoSingleAction
															+ "\"" + ",'dynamicmenu');\n");
													complete.append(listOfLinks.toString() + "\n");
													complete
															.append(listOfActions.toString() + "\n");
													complete.append(windowStatus.toString() + "\n");
													complete.append(method.toString() + "\n");
							%>
							            </td>
							        </tr>
							<%
								i++;
													j++;
												}
												%>
												</table></td>
												</tr>
												<%
												if (mapDocs != null && mapDocs.length > 0) {
													HashMap hMMap = DBUtil.getInstance()
															.getTableVsUrlMappings(mapTableName);

													if (countt > 0) {
							%>
									<tr >
									<td colspan="8"></td>
									</tr>
									<%
										}
									%>
									<tr width="100%">
									<td colspan="8" width="100%">
									<table width="100%" cellspacing=1 cellpadding=4>
									<tr class=""> <!-- P_B_CM_41814 --><%-- P_B_47295 starts --%>
									  <td align="left" width="2%" class="thead headText12b"><%=LanguageUtil.getString("No.")%></td>
							          <td align="left" width="16%" class="thead headText12b"><%=LanguageUtil.getString("Option")%></td>
							          <td align="left" width="40%" class="thead headText12b"><%=LanguageUtil.getString("Subject")%></td>
							          <td align="left" width="40%" class="thead headText12b"><%=LanguageUtil.getString("Document Label")%></td>
							          <td align="left" width="2%" class="thead headText12b"><%=LanguageUtil.getString("Action")%></td>
							        </tr><%-- P_B_47295 ends --%>
							        <%
							        	if (mapDocs.length == 0) {
							        %> 
							        <%
 							        	} else {
 							        		boolean checkDocValue = false;
 							        		String isBuild = "";
 							        		for (int ii = 0; ii < mapDocs.length; ii++) {
 							        			String className = "bText12 grAltRw3"; //P_B_47295
 							        			if (ii % 2 == 0)
 							        				className = "bText12"; //P_B_CM_41814

 							        			DocumentMap dMap = mapDocs[ii];
 							        			SequenceMap sDocMap = dMap.getDocumentFieldMap();
 							        			sDocMap.sortOnValueObjectKey("count",true);

 							        			String count = (String) sDocMap.get("count");
 							        			String docOption = (String) sDocMap.get("doc-option");
 							        			if(docOption == null || docOption.equals("0")) {
 							        				docOption = "Document with Subject";
 							        			} else {
 							        				docOption = "Document Only";
 							        			}
 							        			String docTitle = (String) sDocMap.get("title-display");
 							        			if (docTitle == null) {
 							        				docTitle = "";
 							        			}
 							        			String fileTitle = (String) sDocMap.get("doc-display");
 							        			String docName1 = (String) sDocMap.get("name");
 							        			isBuild = (String) sDocMap.get("isBuild");
 							        			// START : CM_B_37561 : 05/05/2014 <!-- P_B_CM_41814 -->: Veerpal Singh
 							        			if("cm".equals(sTabModuleName)){
 							        				checkDocValue = builderForm.isDataExistForCmDocument(docName1,true);
 							        			} else if("fs".equals(sTabModuleName)){ //signal-20150529-009 starts
 							        				checkDocValue = builderForm.isDataExistForFsDocument(docName1);
 							        			} else { //signal-20150529-009 ends
 							        				checkDocValue = builderForm.isDataExistForFimDocument(docName1, true);
 							        			}
 							        			// END : CM_B_37561 : 05/05/2014 : Veerpal Singh
 							        %>
									<tr class="<%=className%>">
									  <td class="botBorder" align="right" width="2%"> <%=ii + 1%></td> <!-- P_B_CM_41814 -->
							          <td class="botBorder" align="left" width="12%"> <%=LanguageUtil.getString(docOption)%></td>
									  <td class="botBorder" align="left" width="42%"> <%=LanguageUtil.getString(docTitle)%></td>
							          <td class="botBorder" align="left" width="42%"> <%=LanguageUtil.getString(fileTitle)%></td>
							          <td class="botBorder" align="left" width="2%">
							          <%
							          	method = new StringBuffer();
							          	listOfActions = new StringBuffer();
							          	listOfLinks = new StringBuffer();
							          	windowStatus = new StringBuffer();
							          %>
										<div id="menuBar" >
										    <layer ID="Actions_dynamicmenu<%=i%>Bar" ><a href="javascript:void(0);" onMouseOver="openPulldownMenu(event,'Actions_dynamicmenu<%=i%>');" onMouseOut="closePulldownMenu('Actions_dynamicmenu<%=i%>');"> <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/edit.gif" border="0"></a></layer>
										</div>
							          
							          <%
							          	method.append("prepareActionsMenu2(listOfActions,listOfLinks, windowStatus, ");
							          	listOfActions.append("listOfActions = [");
							          	listOfLinks.append("listOfLinks = [");
							          	windowStatus.append("windowStatus = [");
										//PW_B_26145 Starts
							          	listOfLinks.append("'modify', ");
							          	listOfActions.append(" '"+ LanguageUtil.getString("Modify",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
							          	windowStatus.append(" '"+ LanguageUtil.getString("modify",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
																				
							          	if (!checkDocValue && !"true".equals(isBuild)) {
							          		listOfLinks.append("'delete', ");
							          		listOfActions.append(" '"+ LanguageUtil.getString("Delete",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
							          		windowStatus.append(" '"+ LanguageUtil.getString("delete",(String) request.getSession().getAttribute("userLanguage"))+ "', ");
							          	}
							          	//PW_B_26145 Ends
							          	if(isTabularSection){
							          		mapTableName=parentTableName;
							          	}
							          	String sGoSingleAction = "javascript:go2('"+ sFimFormId+ "','"+ docName1+ "','"+ mapSection+ "','"+ mapTableName + "',?,'"+isTabularSection+"','"+tabularSectionTableName+"','"+tabularSectionTableDBName+"')";
							          	windowStatus.append("''];\n");
							          	listOfLinks.append("''];\n");
							          	listOfActions.append("''];\n");
							          	method.append(i + ", " + "\""+ sGoSingleAction + "\""+ ",'dynamicmenu');\n");
							          	complete.append(listOfLinks.toString()+ "\n");
							          	complete.append(listOfActions.toString()+ "\n");
							          	complete.append(windowStatus.toString()+ "\n");
							          	complete.append(method.toString()+ "\n");
							          	i++;
							          %>
							          
							           </td>
							        </tr>
							        <%
							        	}
							        }
							        %>
									</table></td>
									</tr>
									
									<%--
									<tr class="tb_sub_hdr_b">
									  <td align="left"  colspan="1"  width="1%"> <b>No.</b></td>
							          <td align="left"  colspan="1"> <b>Document Title</b></td>
							          <td align="left"  colspan="4"> <b>Type</b></td>
							          <td align="left"  colspan="2"> <b>Action</b></td>
							        </tr>
									 
									<%
										if (mapDocs.length == 0) {
									%> 
							        <%
							        	} else {

							        							boolean checkDocValue = false;
							        							String isBuild = "";
							        							for (int ii = 0; ii < mapDocs.length; ii++) {
							        								String className = "theme_text";
							        								if (ii % 2 == 0)
							        									className = "tb_data";

							        								DocumentMap dMap = mapDocs[ii];
							        								SequenceMap sDocMap = dMap
							        										.getDocumentFieldMap();
							        								sDocMap.sortOnValueObjectKey("count",
							        										true);

							        								String count = (String) sDocMap
							        										.get("count");
							        								String docTitle = (String) sDocMap
							        										.get("title-display");
							        								String fileTitle = (String) sDocMap
							        										.get("doc-display");
							        								String docName1 = (String) sDocMap
							        										.get("name");
							        								isBuild = (String) sDocMap
							        										.get("isBuild");
							        								checkDocValue = builderForm
							        										.isDataExistForFimDocument(docName1);
							        %>
									<tr class="<%=className%>">
									  <td align="right"  colspan="1"> <%=ii + 1%></td>
							          <td align="left"  colspan="1"> <%=docTitle%></td>
							          <td align="left"  colspan="4"> <%=fileTitle%></td>
							          <td align="left"  colspan="2">
							          <%
							          	method = new StringBuffer();
							          								listOfActions = new StringBuffer();
							          								listOfLinks = new StringBuffer();
							          								windowStatus = new StringBuffer();
							          %>
										<div id="menuBar" >
										    <layer ID="Actions_dynamicmenu<%=i%>Bar" ><a href="javascript:void(0);" onMouseOver="openPulldownMenu(event,'Actions_dynamicmenu<%=i%>');" onMouseOut="closePulldownMenu('Actions_dynamicmenu<%=i%>');"> <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/edit.gif" border="0"></a></layer>
										</div>
							          
							          <%
							          							          	method
							          							          										.append("prepareActionsMenu2(listOfActions,listOfLinks, windowStatus, ");
							          							          								listOfActions
							          							          										.append("listOfActions = [");
							          							          								listOfLinks.append("listOfLinks = [");
							          							          								windowStatus.append("windowStatus = [");

							          							          								if (!checkDocValue
							          							          										&& !"true".equals(isBuild)) {

							          							          									listOfLinks.append("'delete', ");
							          							          									listOfActions
							          							          											.append(" '"
							          							          													+ LanguageUtil
							          							          															.getString(
							          							          																	"Delete",
							          							          																	(String) request
							          							          																			.getSession()
							          							          																			.getAttribute(
							          							          																					"userLanguage"))
							          							          													+ "', ");
							          							          									windowStatus
							          							          											.append(" '"
							          							          													+ LanguageUtil
							          							          															.getString(
							          							          																	"delete",
							          							          																	(String) request
							          							          																			.getSession()
							          							          																			.getAttribute(
							          							          																					"userLanguage"))
							          							          													+ "', ");
							          							          								}

							          							          								listOfLinks.append("'modify', ");
							          							          								listOfActions
							          							          										.append(" '"
							          							          												+ LanguageUtil
							          							          														.getString(
							          							          																"Modify",
							          							          																(String) request
							          							          																		.getSession()
							          							          																		.getAttribute(
							          							          																				"userLanguage"))
							          							          												+ "', ");
							          							          								windowStatus
							          							          										.append(" '"
							          							          												+ LanguageUtil
							          							          														.getString(
							          							          																"modify",
							          							          																(String) request
							          							          																		.getSession()
							          							          																		.getAttribute(
							          							          																				"userLanguage"))
							          							          												+ "', ");

							          							          								String sGoSingleAction = "javascript:go2('"
							          							          										+ sFimFormId
							          							          										+ "','"
							          							          										+ docName1
							          							          										+ "','"
							          							          										+ mapSection
							          							          										+ "','"
							          							          										+ mapTableName + "',?)";
							          							          								windowStatus.append("''];\n");
							          							          								listOfLinks.append("''];\n");
							          							          								listOfActions.append("''];\n");
							          							          								method.append(i + ", " + "\""
							          							          										+ sGoSingleAction + "\""
							          							          										+ ",'dynamicmenu');\n");
							          							          								complete.append(listOfLinks.toString()
							          							          										+ "\n");
							          							          								complete.append(listOfActions
							          							          										.toString()
							          							          										+ "\n");
							          							          								complete.append(windowStatus.toString()
							          							          										+ "\n");
							          							          								complete.append(method.toString()
							          							          										+ "\n");

							          							          								i++;
							          							          %>
							          
							           </td>
							          <%
							          	//}
							          %>
							        </tr>
							        
							        <%
							        							        	}
							        							        						}
												
												
							        							        %>
							        							        --%>
							<%
								}
							%>
							        </table>
								</td>
                                <td ><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
                            </tr>
                            <tr>
						      <td align="left" valign="bottom"></td>
						      <td ></td>
						      <td align="right" valign="bottom"></td>
						    </tr>
                            <%--<tr>
                                <td width="10" height="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-left-bot.gif" width="10" height="10" /></td>
                                <td class=theme_bot_bg><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
                                <td width="10" height="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/<%=userTheme%>/cor-right-bot.gif" width="10" height="10" /></td>
                            </tr> --%>
                            <tr>
                                <td width="10" height="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="10" /></td>
                                <td ><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif'></td>
                                <td width="10" height="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="10" /></td>
                            </tr>
                    </table>
                </td>
            </tr>
            </table>
</td>
</tr>
                            
<%
                            	}
                            %> 

        </table>
    </td>
    <td>&nbsp;</td>
</tr>
<%
	}
		}
%>
<tr>
<td>&nbsp;</td>
<td>
<%if("site".equals(sTabModuleName)) { //P_Enh_Site_Clearance starts%>
	<input type="button" class="cm_new_button" onclick="location.href='administration?cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>#Site_Clearance'" value="<%=LanguageUtil.getString("Back",(String) request.getSession().getAttribute("userLanguage")) %>">
<%}else if("mu".equals(subModuleName) || "area".equals(subModuleName)){%>
	<input type="button" class="cm_new_button" onclick="location.href='builderWebForm?module=<%=sTabModuleName%>&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>&cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>'" value="<%=LanguageUtil.getString("Back",(String) request.getSession().getAttribute("userLanguage")) %>">	
<%} else { %>
	<input type="button" class="cm_new_button" onclick="location.href='builderWebForm?module=<%=moduleName%>&moduleId=<%=moduleId%>&subModuleName=<%=subModuleName%>&cft=<%=java.net.URLEncoder.encode((String)session.getAttribute("csrfToken"))%>'" value="<%=LanguageUtil.getString("Back",(String) request.getSession().getAttribute("userLanguage")) %>">
<%} //P_Enh_Site_Clearance ends%>
</td> <%--//PP_changes --%>
</tr>
</table>
</form>
<script language="javascript">
var listOfLinks;
var listOfActions;
var windowStatus;

   <%=complete.toString()%>
<%if (returnValue != -1) {%>
	var time;
	if (document.getElementById("deleteMessage")) {
		time = setTimeout("hideMessage()", 4000);
	}
	function hideMessage()	{
		document.getElementById("deleteMessage").style.display = 'none';
		clearTimeout(time);
	}
<%}%>
</script>
<script language="javascript">
<%if (returnAddValue.equalsIgnoreCase("returnAddValue")
						|| (fimSection != null && "done".equals(fimSection))
						|| (fimDocument != null && ("add".equals(fimDocument) || "modify"
								.equals(fimDocument)))) {%>	
	var time;
	if (document.getElementById("addMessage"))	{
		time = setTimeout("hideMessage()", 4000);
	}
	function hideMessage()	{
		document.getElementById("addMessage").style.display = 'none';
		clearTimeout(time);
	}
<%}%>
</script>
<script language="javascript">
<%if (returnAddValue.equalsIgnoreCase("returnmodifyValue")
		|| (fimSection != null && "modifydone".equals(fimSection)) ){ //P_ENH_FORMBUILDER_SECTION_RENAME %>	
	var time;
	if (document.getElementById("modifyMessage"))	{
		time = setTimeout("hideMessage()", 4000);
	}
	function hideMessage()	{
		document.getElementById("modifyMessage").style.display = 'none';
		clearTimeout(time);
	}
<%}%>
function showHelp(e, helpCode, tagKey1){
    
    ajaxAnywhere.popAJAXHelp(e, helpCode, tagKey1, '', '', 250, 400);
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

function isUsedInCR(fldName,tableName,moduleName){   //P_B_81880
	var isused="no";
	 $.ajax({
	        type: "POST",
	        url: "handleAjaxRequest", 
	        data  :"dataset=getFieldReportInfo&fldName=" + fldName+"&tableName="+tableName+"&moduleName="+moduleName,
	        //FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
	        async:false,
	        success:function(data) {
	        	isused=trim(data);    
	        	
	          }
	    });
	return isused;
}
<%--ENH_MODULE_CUSTOM_TABS starts--%>
function previewFormFunc(formId,isCustomTab,moduleName)
{
	if(showProcessingAlert() == 'true') {
		return false;		
	}
	document.previewForm.formID.value=formId;
	document.previewForm.isCustomTab.value=isCustomTab;
	//added by varsha for BUg_Id-35130 starts
	window.open('',"previewWindow");
	document.previewForm.target = "previewWindow";
	//added by varsha for BUg_Id-35130 ends
	document.previewForm.submit();
}
<%--ENH_MODULE_CUSTOM_TABS ends--%>
</script>
<%--ENH_MODULE_CUSTOM_TABS starts--%>
<form name="previewForm" action="previewBuilderForm" method="post" target="_blank" <%=Constants.FORM_ENCODING%>>
	<input type=hidden name='formID'>
	<input type=hidden name='fromPreview' value='yes'>
	<input type=hidden name='moduleName' value='<%=moduleName%>'>
	<input type="hidden" name="moduleId" value="<%=moduleId%>">
	<input type="hidden" name="isCustomTab" value="<%=isCustomTab%>">
	<input type="hidden" name="tableAnchor" value="<%=tableAnchorMain%>"> <%--//PP_changes --%>
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>">	<%--//PP_changes --%>
</form>
<%--ENH_MODULE_CUSTOM_TABS ends--%>
<form name = "deleteForm" action = "builderWebForm" method="post" <%=Constants.FORM_ENCODING%>><%--BB_Naming_Convention --%>
	<input type=hidden name='formID' value=''>
	<input type=hidden name='tableName' value=''>
	<input type=hidden name='fieldName' value=''>
	<input type=hidden name='secName' value=''>
	<input type=hidden name='section' value=''>
	<input type=hidden name='isParent' value=''>
	<input type=hidden name='dropdownOpt' value=''>
	<input type=hidden name='dependentFld' value=''>
	<input type=hidden name='docName' value=''>
	<input type=hidden name='action' value=''>
	<input type=hidden name='deleteKeyword' value=''>            <%--BB-20150203-259  (To make available all the fields of Form builder as keyword.) --%>
	<input type=hidden name='sdisplayName' value=''>  <!--BB-20141017-177  -->
	<!-- P_FS_Enh_BuilderForm starts -->
	<input type='hidden' name='moduleId' value='<%=moduleId%>'>
	<input type='hidden' name='moduleShortName' value='<%=moduleShortName%>'>
	<input type="hidden" name="formNames" value="<%=sFimFormId%>">
	<!-- P_FS_Enh_BuilderForm ends -->
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>"> <%--//PP_changes --%>
	<input type="hidden" name="isTabularSection" value="" >
	<input type="hidden" name="tabularSectionTableDBName" value="" >
	<input type="hidden" name="tabularSectionTableName" value="" >
	
</form>
<form name = "blankForm" action = "builderWebForm" method="post" <%=Constants.FORM_ENCODING%>><%--BB_Naming_Convention --%>
	<input type='hidden' name='formID' value=''>
	<input type='hidden' name='action' value=''>
	<input type='hidden' name='formNames' value=''>
	<input type='hidden' name='fimSection' value=''>
	<input type='hidden' name='fimDocument' value=''>
	<!-- P_FS_Enh_BuilderForm starts -->
	<input type='hidden' name='moduleId' value='<%=moduleId%>'>
	<input type='hidden' name='moduleShortName' value='<%=moduleShortName%>'>
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>"> 
	<!-- P_FS_Enh_BuilderForm ends -->
</form>
<form name="tabularSectionForm" action="configureTabularSectionColumns" method="post"  <%=Constants.FORM_ENCODING%>>
	<input type=hidden name='formID'>
	<input type=hidden name='moduleName' value='<%=moduleName%>'>
	<input type="hidden" name="moduleId" value="<%=moduleId%>">
	<input type="hidden" name="isCustomTab" value="<%=isCustomTab%>">
	<input type="hidden" name="tableAnchor" value="<%=tableAnchorMain%>">
	<input type="hidden" name="subModuleName" value="<%=subModuleName%>">
	<input type="hidden" name="sectionName" value="" >
	<input type="hidden" name="isTabularSection" value="" >
	<input type="hidden" name="tabularSectionTableDBName" value="" >
	<input type="hidden" name="tabularSectionTableName" value="" >
</form>
<div id='appnetixToolTipDiv' style='display:none;position:absolute'>
    <table cellpadding=0 cellspacing=0 style='width:auto;padding:0;margin:0;left:0;top:0;border-style:solid;border-color:#690002;border-width:1px'>
        <tr>
            <td class="theme_contrast_bg" style='position:relative;line-height:normal;padding:0px;width:auto'>&nbsp;</td>
            <td class="theme_contrast_bg" style='position:relative;line-height:normal;padding:4px;width:auto;' nowrap>
    <span class=text>
        <div id=contentDiv>
        </div> 
    </span>
            </td>
            <td class="theme_contrast_bg" style='position:relative;line-height:normal;padding:4px;width:auto'>&nbsp;</td>
        </tr>
    </table>
</div>
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
