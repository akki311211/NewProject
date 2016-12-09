<%--
 - $Author						:  $Abhishek gupta
 - $Version						:  $1.1
 - $Date						:  $Nov 14,2011
 BUILDER_DECIMAL_ISSUE    28-NOV-2013  Mohit Sharma    Entering decimal will lead to corruption
 P_FS_Enh_BuilderForm	  16-DEC-2013  Naman Jain	   Form Builder In Franchise Sales		
 CM_B_35374		14 march 2014    chetan rawat            removed the js error and probelm of submiting form.
 P_B_ADMIN_CM_35382 24 march 2014 chetan rawat   addind urgent fields in options fields.
 CM_B_35380     25 march 2014           chetan rawat           changed the alert message. 	
  P_B_CM_35418   26th march 2014		    Nancy Goyal				GUI issue	
  P_B_CM_070714  7th July 2014            Swati Garg              GUI issue		 
   P_CM_B_41640   15th july 2014          Udit Soni               GUI issue while adding a radio field
   P_CM_B_41639   15th july 2014          Udit Soni                maximum length denoting numerical values different font.
   P_B_ADMIN_CM_41813    18 July 2014     Rashmi Shakya            Note to avoid special characters is of different font
   //P_ADMIN_B_41816	19july2014		Rajat Gupta				Full stop provided after please enter option.
    P_B_ADMIN_43851		11 August 2014		Nancy Goyal				GUI issue
 --%>

<%@page import="com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="com.home.builderforms.forms.*"%>
<%@ page import="com.appnetix.app.util.*"%>
<%@ page import="com.appnetix.app.util.database.*"%>
<%@ page import="com.appnetix.app.util.information.*"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="com.home.builderforms.*"%>
<%String pathString="/static"+Constants.STATIC_KEY + "/javascript/string.js";
System.out.println("pathString " + pathString);%>
<jsp:include page="<%=pathString%>" />

<%@ include file="/SessionManager.jsp"%>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/ajaxforcombos.js"> </script>
<script language="javascript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>

<jsp:useBean id="builderForm"
	class="com.home.builderforms.BuilderFormWebImpl"
	scope="session" />
<jsp:useBean id="builderWebForm"
	class="com.home.builderforms.BuilderWebFormBean"
	scope="session" /><%--BB_Naming_Convention --%>

<%
try {
List<String> specialFields = new ArrayList(Arrays.asList("leadSource3ID", "franchiseAwarded", "leadOwnerID", "leadStatusID", "campaignID", "countyID","cmLeadSubStatusID","cmSource3ID","ownerType","franchiseeNo","areaID","groupId","unsubscribe","copyAddress","locationID","cmContactMappinggroupId","type","address_0country","address_0state","cmCompanyAddress_0country","cmCompanyAddress_0state", "birthDate","spouseBirthDate","birthMonth","spouseBirthMonth","isParent","assignTo","timelessTask","calendarTask","taskEmailReminder")); //fields not included in dependent response////P_CM_B_73147
List<String> typeList = Arrays.asList("varchar","int","text","Date","combo","radio","numeric","checkbox","multiselect","file","existing"); //P_Enh_Sync_Fields
String sAction = request.getParameter("action");
String disabledFieldType = ""; //BB-20150203-259 (Dynamic Response based on parent field response)
int option = 1;
String disableFieldTypeCombo = "";
boolean canDisplayParentFieldName = false;
String sFormID = request.getParameter("formID");
String sHtmlType = request.getParameter("dataType");
String sFieldNo = request.getParameter("fieldID");
String sSection = request.getParameter("section");
String isTabularSection=request.getParameter("isTabularSection");
String tabularSectionTableName=request.getParameter("tabularSectionTableName");
String tabularSectionTableDBName=request.getParameter("tabularSectionTableDBName");
String checkValue = request.getParameter("checkValue");
String formButtonTag = "";
String isBuildField = request.getParameter(BuilderFormFieldNames.IS_BUILD_FIELD);
boolean isBuildFld = Boolean.parseBoolean(isBuildField);

String dropdownOpt = request.getParameter("dropdownOpt");
String radioOpt = request.getParameter("radioOption");
String checkboxOpt = request.getParameter("checkboxOption");
String fldType="";//P_CM_B_61858
int optionCount = 1;//SMC-20140213-378
int deactivatedCount = 0;//SMC-20140213-378
boolean hasOptions = false;//SMC-20140213-378
boolean isPiiEnabled = false;
boolean isInputVal =Boolean.parseBoolean(checkValue); 
String val = null;
String moduleId = (String)request.getParameter("moduleId"); //P_FS_Enh_BuilderForm
String subModuleName = (String)request.getParameter("subModuleName"); //PP_changes
String moduleURL = "";
HashMap<String, String> acrossModuleMap = new HashMap<String, String>(); //can add more modules when required.
if("3".equals(moduleId)) { //FIM module
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM;
	if(ModuleUtil.fsImplemented()) {
		acrossModuleMap.put("fs", ModuleUtil.MODULE_DISPLAY.DISPLAY_FS);
	}
} else if("2".equals(moduleId)) { //FS module
	moduleURL = ModuleUtil.MODULE_DISPLAY.DISPLAY_FS;
	if(ModuleUtil.fimImplemented()) {
		acrossModuleMap.put("fim", ModuleUtil.MODULE_DISPLAY.DISPLAY_FIM);
	}
}
String mainModule = LanguageUtil.getString(moduleURL, (String) session.getAttribute("userLanguage"), NewPortalUtils.getModuleKey(moduleURL)); 

boolean checkForCM = false;
boolean checkForContactHistory = false;
if(ModuleUtil.MODULE_ID.ID_CM == Integer.parseInt(moduleId) || "5".equals(moduleId) || "6".equals(moduleId) || "7".equals(moduleId)) { //check for CM module
	checkForCM = true;
	//P_CM_B_73147 start	
	specialFields.add("callStatus");
	specialFields.add("callType");
	specialFields.add("schduleTime");
	specialFields.add("status");
	specialFields.add("priority");
	specialFields.add("cmLeadQuickCampaignAssociationcampaignID");
	specialFields.add("cmQuickCampaignAssociationcampaignID");
	//P_CM_B_73147 End
}

if("3".equals(moduleId) && ("43".equals(sFormID)|| "45".equals(sFormID) || "46".equals(sFormID))) {
	checkForContactHistory = true;
}
val = request.getParameter("tableName");
if(val == null) {
	val = (String)request.getAttribute("tableName");
}
if(StringUtil.isValidNew(isTabularSection) && "true".equals(isTabularSection)){
		val=tabularSectionTableName;
}

String multiSubmitForm = SQLUtil.getColumnValue("BUILDER_WEB_FORMS","ADD_MORE","BUILDER_FORM_ID", sFormID); //P_Enh_Sync_Fields
FieldMappings fMapping = builderForm.getBuilderFieldsMapping(val);
Field[] flds = null;
Field[] activeFields = null;
if(fMapping != null) {
	flds = fMapping.getSectionTableAllActiveDeactiveFieldsWithOrderBy(sSection);
	activeFields = fMapping.getSectionTableFieldsWithOrderBy(sSection);
}


//SMC-20140213-378 Starts
String pageHeader = FieldNames.EMPTY_STRING;
Field currField =  null;
//BB-20150203-259 (Dynamic Response based on parent field response) starts
boolean showOptDivOnModify = false;
boolean showRadioDivOnModify = false;
boolean showcheckBoxDivOnModify = false;
boolean showComboDivOnModify = false;
String dependentParentValue = "-1";
String dependentParentDisplayName = "";
String currentFieldName = "";
boolean isFieldIsInReport = "no".equals((String)request.getParameter("isFieldIsInReport"))?false:true;  //P_B_81880
String syncWith = ""; //P_Enh_Sync_Fields
SequenceMap syncTotalaMap = null;
//BB-20150203-259 (Dynamic Response based on parent field response) ends
String isOtherTableField = request.getParameter(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD);
String fieldNameNew = request.getParameter(BuilderFormFieldNames.FIELD_NAME);
String displayType = "";
String validationType = "";
if("modify".equals(sAction)){
	pageHeader = LanguageUtil.getString("Modify Field");
	currField = fMapping!=null?fMapping.getField(request.getParameter("fieldName")):null;
	if("true".equals(request.getParameter("isOtherTableField"))) {//P_CM_B_61858 start
		currField = fMapping.getOtherTableField(request.getParameter("fieldName")); //if true
		if(currField !=null){
			fldType=currField.getDisplayTypeField();
		}
    }//P_CM_B_61858 End
    
    displayType = currField.getDisplayTypeField(); //P_Enh_Sync_Fields
    validationType = currField.getValidationType();
    if("Mandatory".equals(validationType) || "None".equals(validationType)) {
    	validationType = null;
    }
    syncTotalaMap = currField.getSyncTotalMap();
    session.setAttribute("syncTotalaMap", syncTotalaMap);
    
	disabledFieldType = "disabled"; //BB-20150203-259 (Dynamic Response based on parent field response)
	if(currField!=null && !currField.isBuildField()){
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		currentFieldName = currField.getFieldName();
		showOptDivOnModify = ("1".equals(currField.getFieldDropdownOpt()) || "2".equals(currField.getFieldDropdownOpt()) ) ? false:true;
		showComboDivOnModify = ("3".equals(currField.getFieldDropdownOpt())) ? true:false;
		showRadioDivOnModify = ("1".equals(currField.getFieldRadioOption())) ? true:false;
		showcheckBoxDivOnModify = ("1".equals(currField.getFieldCheckboxOption())) ? true:false;
		dependentParentValue = currField.getDependentParentField();
		if(StringUtil.isValidNew(dependentParentValue)) {
			dependentParentDisplayName = fMapping.getField(dependentParentValue.split("##")[0]).getDisplayName();
			canDisplayParentFieldName = true;
			disableFieldTypeCombo = "disabled";
		}
		
		SequenceMap dependentChildMap = currField.getDependentChildTotalMap();
		if(dependentChildMap != null && dependentChildMap.size() > 0) {
			disableFieldTypeCombo = "disabled";
		}
	}
	//P_Enh_Sync_Fields starts
	syncWith = currField.getSyncWithField();
	SequenceMap syncData = currField.getSyncTotalMap();
	
	if(StringUtil.isValidNew(syncWith) || (syncData != null && syncData.size() > 0)) {
		isInputVal = true;
		checkValue = "true";
	}
	//P_Enh_Sync_Fields ends
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
}else if("add".equals(sAction)){
	pageHeader = LanguageUtil.getString("Add New Field");
}		
//SMC-20140213-378 Ends
%>
<script language="javaScript">
var w=screen.width;
var h=screen.height;
var inc = 50*(h/768);
var sActionScript = '<%=sAction%>';<%--SMC-20140213-378--%>
$.browser.chrome = /chrome/.test(navigator.userAgent.toLowerCase());
if ( ($.browser.msie) && ($.browser.version <= '7.0') ) {
	inc = inc + 60;
}else if ( ($.browser.msie) && ($.browser.version == '8.0') ) {
	inc = inc + 50;
}else if ( ($.browser.msie) && ($.browser.version == '9.0')) {
	inc = inc + 35;
}else if ( $.browser.chrome ) {
	inc = inc + 25;
}else if ( $.browser.safari ) {
	inc = inc + 5;
}else if ( $.browser.mozilla ) {
	inc = inc + 40;
}
/*window.onload = function(){
	document.fieldModify.displayName.focus();
	var height = document.getElementById("containerDiv").offsetHeight;
	resizeTo(630,height+inc);
}*/


$(document).ready(function(){
	//P_Enh_Sync_Fields starts
	jQuery.noConflict();  
	jQuery('.form-control-single').multipleSelect({ 
		filter: true,
 		placeholder:"Select",
 		single:true,
 		multipleWidth: 101,
 	});
	$("#ms-parentfieldNamesForTabNew").css("width",'300px');
	//P_Enh_Sync_Fields ends
	document.fieldModify.displayName.focus();
	//BB-20150203-259 (Dynamic Response based on parent field response) starts
	if(<%=showRadioDivOnModify%>) {
		if(document.fieldModify.radioOption) {
			showDependentSection("1");
			document.fieldModify.radioOption[1].checked = true;
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		} else if(sActionScript.trim() == "modify" && <%=canDisplayParentFieldName%>) {
			showDependentSection("1");
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		}
	} else if(<%=showcheckBoxDivOnModify%>) {
		if(document.fieldModify.checkboxOption) {
			showDependentSection("1");
			document.fieldModify.checkboxOption[1].checked = true;
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		} else if(sActionScript.trim() == "modify" && <%=canDisplayParentFieldName%>) {
			showDependentSection("1");
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		}
	} else if(<%=showComboDivOnModify%>) {
		if(document.fieldModify.dropdownoption) {
			showDependentSection("1");
			document.fieldModify.dropdownoption[3].checked = true;
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		} else if(sActionScript.trim() == "modify" && <%=canDisplayParentFieldName%>) {
			showDependentSection("1");
			getDependentConditionAndValue('<%=dependentParentValue%>', true);
		}
	}
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
	selectDiv();
	var height = document.getElementById("containerDiv").offsetHeight;
	if(parent.$.fn.colorbox){ //BB-20150203-259 (Dynamic Response based on parent field response) starts
		var iH= $(document).height()+50;
		parent.$.fn.colorbox.vkResize(1050, iH);
	} //BB-20150203-259 (Dynamic Response based on parent field response) ends
});

    function hideLayer(whichLayer) {
    	if (document.getElementById && document.getElementById(whichLayer)) {
    		document.getElementById(whichLayer).style.display = "none";
		} else if (document.all && document.all[whichLayer]) {//For Bug 37330
			document.all[whichLayer].style.display = "none";
		} else if (document.layers) {
			document.layers[whichLayer].display = "none";
		}
	}

    function showLayer(whichLayer) {
    	 if (document.getElementById && document.getElementById(whichLayer)) {
        	document.getElementById(whichLayer).style.display = "block";
		} else if (document.all && document.all[whichLayer]) {//For Bug 37330
			document.all[whichLayer].style.display = "block";
			} else if (document.layers) {
			document.layers[whichLayer].display = "block";
		}
	}

    function selectDiv() {
		var div = document.fieldModify.divSelect.value;
		var type = document.fieldModify.dbColumnType.value;
		var dropdowntype = document.fieldModify.dropdownoption;
		<%--SMC-20140213-378 starts--%>
		if(type == "String"){
			type = document.fieldModify.displayType.value;
		}
		<%--SMC-20140213-378 ends--%>
        if (type == "text") {
			showLayer("textAreaSize");
			hideLayer("optionsDiv");
			hideLayer("textNumber");
			hideLayer("textNumber1");
			hideLayer("optionView"); // OPTION_VIEW_H_V
			hideLayer("dropDownOption");
			hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("filetypeOption");
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none";
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			hideLayer("display1");
			showHide();
		} else if (type == "varchar" || type == "int") {
		    document.fieldModify.divSelect.value="hide";
			if(type == "varchar") {
				showLayer("stext");
				hideLayer("snumber");
			} else {
				hideLayer("stext");
				showLayer("snumber");
			}
			hideLayer("optionsDiv");
			hideLayer("textAreaSize");
			showLayer("textNumber");
			hideLayer("textNumber1");
			hideLayer("optionView"); // OPTION_VIEW_H_V
			hideLayer("dropDownOption");
			hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("filetypeOption");
			hideLayer("display1");
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			showHide();
		} else if (type == "numeric") {
		    hideLayer("textAreaSize");
		    hideLayer("optionsDiv");
		    hideLayer("textNumber");
		    showLayer("textNumber1");
		    hideLayer("optionView"); // OPTION_VIEW_H_V
		    hideLayer("dropDownOption");
		    hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
		    hideLayer("filetypeOption");
			hideLayer("display1");
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			showHide();
		} else if (type == "combo" || type == "multiselect") {
			showLayer("dropDownOption");
			if(type == "multiselect") {
				if(document.getElementById("dropDownOption1")) {
					document.getElementById("dropDownOption1").style.display = "none";
					$('#dropDownOption1').find("input[type=radio]").attr('checked', false);
				}
				if(document.getElementById("dropDownOption2")) {
					document.getElementById("dropDownOption2").style.display = "none";
					$('#dropDownOption2').find("input[type=radio]").attr('checked', false);
				}
				if(document.getElementById("dropDownOption0")) {
					$('#dropDownOption0').find("input[type=radio]").attr('checked', true);
				}
			} else {
				if(document.getElementById("dropDownOption1")) {
					document.getElementById("dropDownOption1").style.display = "";
				}
				if(document.getElementById("dropDownOption2")) {
					document.getElementById("dropDownOption2").style.display = "";
				}
			}
			<%--SMC-20140213-378 starts--%>
			if('modify'==sActionScript && <%=showOptDivOnModify%>){
				showLayer("optionsDiv");
			}else if('add'==sActionScript && dropdowntype[0].checked){
				showLayer("optionsDiv");
			}else{
				hideLayer("optionsDiv");
			}
			<%--SMC-20140213-378 ends--%>
			hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("textAreaSize");
		    hideLayer("textNumber");
		    hideLayer("textNumber1");
		    hideLayer("optionView"); // OPTION_VIEW_H_V
			hideLayer("filetypeOption");
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			showHide();
		} else if (type == "file") {
		    hideLayer("textAreaSize");
		    hideLayer("optionsDiv");
		    hideLayer("textNumber");
		    hideLayer("textNumber1");
		    hideLayer("optionView"); // OPTION_VIEW_H_V
		    hideLayer("dropDownOption");
		    hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			<%if(checkForCM){%>////ZCUB-20150505-144 -CM_Document Field Changes
			hideLayer("searchExport");
			<%}%>
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
		    //showLayer("filetypeOption");//BB-20150203-259 (Add Document as field for positioning)
		    showHide();
		} else if (type == "radio" || type == "checkbox") {
		    hideLayer("textAreaSize");
		    showLayer("optionsDiv");
		    hideLayer("textNumber");
		    hideLayer("textNumber1");
		    hideLayer("dropDownOption");
		    hideLayer("filetypeOption");
			hideLayer("display1");
			showLayer("optionView"); // OPTION_VIEW_H_V
			if(type == "radio") { //BB-20150203-259 (Dynamic Response based on parent field response) starts
				showLayer("radioOption");
				hideLayer("checkboxOption");;
			} else {
				showLayer("checkboxOption");
				hideLayer("radioOption")
			} ////BB-20150203-259 (Dynamic Response based on parent field response) ends
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			showHide();
		} else if(type == "existing") { //P_Enh_Sync_Fields starts
			hideLayer("textAreaSize");
			hideLayer("optionsDiv");
			hideLayer("textNumber");
			hideLayer("textNumber1");
			hideLayer("optionView");
			hideLayer("dropDownOption");
			hideLayer("radioOption");
			hideLayer("checkboxOption");
			<%if(checkForCM){%>
				hideLayer("searchExport");
			<%}%>
			document.getElementById("existingOption").style.display = "";
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "";
				showHideModuleFields('', 'create');
			}
			showHide();
		} //P_Enh_Sync_Fields ends
		else {
		    hideLayer("textAreaSize");
		    hideLayer("optionsDiv");
		    hideLayer("textNumber");
		    hideLayer("textNumber1");
		    hideLayer("optionView"); // OPTION_VIEW_H_V
		    hideLayer("dropDownOption");
		    hideLayer("radioOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
			hideLayer("checkboxOption"); //BB-20150203-259 (Dynamic Response based on parent field response)
		    hideLayer("filetypeOption");
			hideLayer("display1");
			if(document.getElementById("existingOption")) {
				document.getElementById("existingOption").style.display = "none"; //P_Enh_Sync_Fields
			}
			if(document.getElementById("existingRadioTr")) {
				document.getElementById("existingRadioTr").style.display = "none";
			}
			showHide();
	    }
        var height= $(document).height()+50;
        parent.$.fn.colorbox.vkResize(1050, height); //BB-20150203-259 (Dynamic Response based on parent field response)
        //resizeTo(630,height+inc);
   }

   function showHide() {
		var type = document.fieldModify.dbColumnType.value;
		//var dropdowntype = document.getElementsByName("dropdownoption");
		var dropdowntype = document.fieldModify.dropdownoption;
		var sAction = '<%=sAction%>';
		var filetype = document.getElementsByName("filetypeoption");
		//document.getElementById("columnLabelTr").style.display = "none"; //BB-20150203-259 (Dynamic Response based on parent field response)
		if (type == "combo"|| type == "multiselect") {
			if (dropdowntype[1].checked) {
				hideLayer("optionsDiv");
				hideLayer("display1");
				showLayer("displayC");
				hideLayer("displayD");
				hideLayer("displayS");
				hideLayer("displayT");
				showDependentSection("0"); //BB-20150203-259 (Dynamic Response based on parent field response)
				document.getElementById("dispName").innerHTML="<span class=\"urgent_fields\">*</span><%=LanguageUtil.getString("Display Name",(String)session.getAttribute("userLanguage"))%>:";
	        } else if(dropdowntype[2].checked) {
	        	hideLayer("optionsDiv");
	        	showLayer("display1");
				showLayer("displayC");
				hideLayer("displayD");
				showLayer("displayS");
				hideLayer("displayT");
				showDependentSection("0"); //BB-20150203-259 (Dynamic Response based on parent field response)
				document.getElementById("dispName").innerHTML="<span class=\"urgent_fields\">*</span><%=LanguageUtil.getString("Display Name",(String)session.getAttribute("userLanguage"))%>:";
	        } else {
	        	hideLayer("display1");
				hideLayer("displayC");
				hideLayer("displayD");
				hideLayer("displayS");
				hideLayer("displayT");
				<%--SMC-20140213-378 starts--%>
				if('modify'==sActionScript && <%=showOptDivOnModify%>){
					showLayer("optionsDiv"); ////BB-20150203-259 (Dynamic Response based on parent field response) starts
					<%if(StringUtil.isValidNew(dependentParentValue)) { %>
						showDependentSection("1");
						getDependentConditionAndValue('<%=dependentParentValue%>', true);
					<%} else {%>
						showDependentSection("0");
					<%}%>
				}else if('add'==sActionScript && (dropdowntype[0].checked || dropdowntype[3].checked)){
					showLayer("optionsDiv");
					if(dropdowntype) {
						if(dropdowntype[3].checked) {
							showDependentSection("1");
						} else {
							showDependentSection("0");
						} //BB-20150203-259 (Dynamic Response based on parent field response)
					}
				}else{
					hideLayer("optionsDiv");
				}
				<%--SMC-20140213-378 Ends--%>
				/*if('modify'==sAction){ //because options are added into it. //BB-20150203-259 (Dynamic Response based on parent field response)
					hideLayer("dropDownOption");
				}*/
				document.getElementById("dispName").innerHTML="<span class=\"urgent_fields\">*</span><%=LanguageUtil.getString("Display Name",(String)session.getAttribute("userLanguage"))%>:";
	        }
		} /*else if (type == "file") { //BB-20150203-259 (Add Document as field for positioning)
			 if (filetype[0].checked) {
				showLayer("display1");
				hideLayer("displayC");
				showLayer("displayD");
				hideLayer("displayS");
				showLayer("displayT");
			} else if (filetype[1].checked) {
				hideLayer("display1");
				hideLayer("displayC");
				showLayer("displayD");
				hideLayer("displayS");
				hideLayer("displayT");
			} 
		} */else {
			hideLayer("display1");
			hideLayer("displayC");
			hideLayer("displayD");
			hideLayer("displayS");
			hideLayer("displayT");
			if(type == "radio") { //BB-20150203-259 (Dynamic Response based on parent field response) starts
				if(document.fieldModify.radioOption) {
					var radioOption = document.fieldModify.radioOption;
					if(radioOption[1].checked) {
						showDependentSection("1");
						<%if(StringUtil.isValidNew(dependentParentValue)) { %>
							getDependentConditionAndValue('<%=dependentParentValue%>', true);
						<%}%>
					} else {
						showDependentSection("0");
					}
				}
			} else if(type == "checkbox"){
				if(document.fieldModify.checkboxOption) {
					var checkboxOption = document.fieldModify.checkboxOption;
					if(checkboxOption[1].checked) {
						showDependentSection("1");
						<%if(StringUtil.isValidNew(dependentParentValue)) { %>
							getDependentConditionAndValue('<%=dependentParentValue%>', true);
						<%}%>
					} else {
						showDependentSection("0");
					}
				}
			} //BB-20150203-259 (Dynamic Response based on parent field response) ends
	    }
		//P_Enh_Sync_Fields starts
		var height = document.getElementById("containerDiv").offsetHeight+50;
		if(type == "existing") {
			var height = 450;
		}
		//P_Enh_Sync_Fields ends
        //resizeTo(630,height+inc);
		parent.$.fn.colorbox.vkResize(1050, height); //BB-20150203-259 (Dynamic Response based on parent field response)
	}
   
   /*
   //P_Enh_Sync_Fields
   */
   
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
   
	<%--SMC-20140213-378 starts--%>
	function addToForm(){
		var form = document.fieldModify;
		var optionName = $("*[name='optionNameTemp']");
		for(var i = 1;i<=optionName.length;i++){
			input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('name', 'optionName');
			input.setAttribute('value', optionName[i-1].value);
			form.appendChild(input);
		}
		optionName = $("*[name='optionActivated']");
		for(var i = 1;i<=optionName.length;i++){
			input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('name', 'optionActivatedOrdered');
			input.setAttribute('value', optionName[i-1].value);
			form.appendChild(input);
		}
		var dependentFieldName = $("*[id='fieldDependentValue']");
		for(var i = 1;i<=dependentFieldName.length;i++) { ////BB-20150203-259 (Dynamic Response based on parent field response) starts
			//dependentFieldName[i-1].disabled = false;
			input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('name', 'fieldDependentValueHidden');
			if(dependentFieldName[i-1].disabled) {
				//input.setAttribute('value', dependentFieldName[i-1].value);
				input.setAttribute('value', document.getElementById('fieldDependentValue_disabled'+(i-1)).value);
			} else {
				input.setAttribute('value', dependentFieldName[i-1].value);
			}
			form.appendChild(input);
		} //BB-20150203-259 (Dynamic Response based on parent field response) ends
	}
	<%--SMC-20140213-378 ends--%>
	function toTitleCase(str)
	{
	    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
	}
	
	function revCapitalize(text) {
	    return text.charAt(0).toLowerCase() + text.slice(1);
	}

	function string_to_slug(str) {
		  str = str.toLowerCase();
		
		  // remove accents, swap � for n, etc
		  var from = "��������������������������������";
		  var to   = "aaaaaaaeeeeiiiiooooooouuuuncyyop";
		  for (var i=0, l=from.length ; i<l ; i++) {
		    str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
		  }

		  //str = str.replace(/[^a-z0-9 _]/g, '') // remove invalid chars
		  //  .replace(/\s+/g, '_') // collapse whitespace and replace by -
		  //  .replace(/_+/g, '_'); // collapse dashes

		  return str;
	}
	    
</script>
<html>
<body topmargin="0" leftmargin="0">
<title><%=LanguageUtil.getString("Field Configuration",(String)session.getAttribute("userLanguage"))%></title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css"
	type="text/css">
<%@ include file="builderScriptCss.jsp"%>
<div id="containerDiv">
<table border="0" cellpadding="0" cellspacing="0" width="100%"
	id="builderForm">
	<tr>
		<td colspan="2" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!--<td width="18%" valign="bottom">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="9" class="crvBox2-ltBg"></td>
                <td  nowrap="nowrap" class="crvBox2-ttBg">FIM Form</td>
                <td width="33" class="crvBox2-rtBg"></td>
              </tr>
            </table></td>
            
	 
	 	-->
				<td width="59%" align="right" class="bText11gr" height="30"
					style="padding-right: 25px"><%=LanguageUtil.getString("Fields marked with",(String)session.getAttribute("userLanguage"))%>
				<span class="urgent_fields"> *</span> <%=LanguageUtil.getString("are mandatory.",(String)session.getAttribute("userLanguage"))%></td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="clear: both;">
			<tr>
				<td height="10" colspan="4" />
			</tr>
			<tr>
				<%-- <td width="10" valign="top"><img width="10" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl.png"></td>    			
				<td class="crvBox2-top"></td>		
				<td width="16" valign="top"><img width="16" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png"></td> --%>
			</tr>
			<tr>
				<!-- <td class="crvBox2-left"  width="100%"/>
				<td class="crvBox2-bg">
 -->



				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="1"></td>
						<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="4">
							<%
    if (sAction.equals("delete")) {
%>
							<tr>
								<td class="tb_hdr_b" height="25" colspan="2"><%=LanguageUtil.getString("Confirm Deletion",(String)session.getAttribute("userLanguage"))%></td>
							</tr>
							<tr class="tb_data">
								<td colspan="2" class="urgent_fields"><%=LanguageUtil.getString("Do you really want to delete this? If yes, All the previous data will be lost",(String)session.getAttribute("userLanguage"))%>.</td>
							</tr>
							<tr>
								<form action=customFormActionsPage method=post
									<%=Constants.FORM_ENCODING%>><input type=hidden
									name=formID value=<%=sFormID%>> <input type=hidden
									name=fieldID value=<%=sFieldNo%>> <input type=hidden
									name=action value=<%=sAction%>>
								<td width="10"></td>
								<td><input type="submit"
									value=<%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%>
									class="cm_new_button">&nbsp; <input type="button"
									name="Button2"
									value=<%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%>
									onClick="javascript:window.close();" class="new_button">
								</td>
								</form>
							</tr>
							<%
	} else {
		Info fieldvalues = builderWebForm.builderFieldValues(request);//BB_Naming_Convention
		String data[] = null;
		String fieldId = null;
		String fldValidationType = null;
        boolean includeInCampaign = false;                          //BB-20150319-268(FIM Campaign Center) starts
        String display = "display: none;";
        String checked = "checked";
        String oldIncludeInCampaign = ""; 								//BB-20150319-268(FIM Campaign Center) ends
		String fldViewType = "0"; // OPTION_VIEW_H_V
		String title = ""+LanguageUtil.getString("Add New Field",(String)session.getAttribute("userLanguage"))+"";
		formButtonTag = ""+LanguageUtil.getString("Add",(String)session.getAttribute("userLanguage"))+"";
		SequenceMap smap = null;//SMC-20140213-378
		String canDelete = FieldNames.EMPTY_STRING;//SMC-20140213-378
		String className = "text";
		if (sAction.equals("modify")) {
			title = LanguageUtil.getString("Modify Field",(String)session.getAttribute("userLanguage"));
			formButtonTag = LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"));
			fieldId = fieldvalues.getString(BuilderFormFieldNames.FIELD_ID);
			fldViewType = fieldvalues.getString(BuilderFormFieldNames.OPTION_VIEW_TYPE);// OPTION_VIEW_H_V
			fldValidationType = fieldvalues.getString(BuilderFormFieldNames.FLD_VALIDATION_TYPE);
			String isMultiSelect=fieldvalues.getString(BuilderFormFieldNames.IS_MULTISELECT);
			String dType = fieldvalues.getString(BuilderFormFieldNames.DISPLAY_TYPE);
			if(!isBuildFld){
				isPiiEnabled = fieldvalues.getBoolean(BuilderFormFieldNames.IS_PII_ENABLED);
				if(isPiiEnabled)
				{
					checkValue = "true";
				}
			}
			if("3".equals(moduleId) && !"2".equals(sFormID) && !checkForContactHistory)			//BB-20150319-268(FIM Campaign Center) starts
        	{
            	if(fieldvalues != null) {
                	includeInCampaign = fieldvalues.getBoolean(FieldNames.INCLUDE_IN_CAMPAIGN);
            	}
            	if("Email".equals(fldValidationType)) {
                	display = "";
            	}
            	if(includeInCampaign) {
                	checked = "checked";
                	oldIncludeInCampaign = "0";
            	} else {
                	checked = "";
                	oldIncludeInCampaign = "1";
            	}							 //BB-20150319-268(FIM Campaign Center) ends
        	}
			if (dType.equals("TextArea")) {
				 option = 3;
			} else if (dType.equals("Date")) {
                option = 4;
        	} else if (dType.equals("Text")) {
        		option = 1;
        	} else if (dType.equals("Number")) {
        		option = 2;
        	} else if (dType.equals("Combo")&&!"true".equals(isMultiSelect)) {//BB-20150203-259 (Form Builder MultiSelect Combo Changes)
        		option = 5;
        	} else if (dType.equals("Radio")) {
        		option = 6;
        	} else if (dType.equals("Numeric")) {
        		option = 7;
        	} else if (dType.equals("Checkbox")) {
        		option = 8;
        	} else if (dType.equals("Combo")&&"true".equals(isMultiSelect)){//BB-20150203-259 (Form Builder MultiSelect Combo Changes)
        		option = 9;
        	} else if (dType.equals("File")) { //BB-20150203-259 (Add Document as field for positioning) starts
        		option = 10;
        	} //BB-20150203-259 (Add Document as field for positioning) ends
			
			if (option==5 || option==6 || option==8||option==9) {//BB-20150203-259 (Form Builder MultiSelect Combo Changes)
				smap = builderForm.getRadioOrComboOptionsMap(request.getParameter(BuilderFormFieldNames.FIELD_NAME), val);
				if(smap.size() > 0)
				{
					optionCount = smap.size();
					hasOptions = true;//SMC-20140213-378
				}else {
					optionCount = 1;
					hasOptions = false;//SMC-20140213-378
				}
			}
		}
		/*
		if (sAction.equals("add"))
        {
			sAction =LanguageUtil.getString("modify",(String)session.getAttribute("userLanguage"));
		}
		*/
%>
							<form name="fieldModify" action="builderFormActionsPage"
								method=post <%=Constants.FORM_ENCODING%>><input
								type="hidden" name="moduleId" value="<%=moduleId%>"> <!-- P_FS_Enh_BuilderForm -->
								<input
								type="hidden" name="isTabularSection" value="<%=isTabularSection%>">
								<input
								type="hidden" name="tabularSectionTableDBName" value="<%=tabularSectionTableDBName%>">
								<input
								type="hidden" name="tabularSectionTableName" value="<%=tabularSectionTableName%>">
								
								<input type="hidden" name="fieldNamesForTab" value="">
							<tr>
								<td width="100%" colspan="3" valign="top" height="20"
									class="fline hText18theme pt pb5" nowrap="nowrap" align="left">
								<%=title%></td>
								<% if(!isBuildFld && !isFieldIsInReport && !isInputVal  && !StringUtil.isValid(dropdownOpt) && !isPiiEnabled) { ////P_B_81880 
			%>
								<input type="hidden"
									name="<%=BuilderFormFieldNames.DB_COLUMN_NAME%>"
									value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_NAME)%>'>
								<input type="hidden"
									name="<%=BuilderFormFieldNames.DB_COLUMN_NAME%>1" value=''>
								<input type="hidden" name="<%=BuilderFormFieldNames.SECTION%>"
									value='<%=fieldvalues.getString(BuilderFormFieldNames.SECTION)%>'>
								<%-- <input type="hidden" name="<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH)%>' >--%>
								<% }else{ 
								%>
								<input type="hidden"
									name="<%=BuilderFormFieldNames.DB_COLUMN_NAME%>"
									value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_NAME)%>'>
								<input type="hidden"
									name="<%=BuilderFormFieldNames.DB_COLUMN_TYPE%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE)%>">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.DISPLAY_TYPE%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.DISPLAY_TYPE).toLowerCase()%>">
								<%--SMC-20140213-378--%>
								<input type="hidden" name="hasOptions" value="<%=hasOptions%>">
								<%--SMC-20140213-378--%>
								<%} %>
							
							<tr class="text_b">
								<input type="hidden" name="divSelect" value="hide">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.FIELD_NAME%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.FIELD_NAME)%>">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.FIELD_NAME%>1" value="">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.IS_BUILD_FIELD%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.IS_BUILD_FIELD)%>">
								<input type="hidden" name="<%=BuilderFormFieldNames.DATA_TYPE%>"
									value="String">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.BUILDER_FORM_ID%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.BUILDER_FORM_ID)%>">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.IS_OTHER_TABLE_FIELD%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD)%>">
								<input type="hidden"
									name="<%=BuilderFormFieldNames.OTHER_TABLE_NAME%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.OTHER_TABLE_NAME)%>">
								<input type="hidden" name="tableName" value="<%=val%>">
								<input type="hidden" name="mailmergekeyword" value="">
								<input type="hidden" name="mailmergekeyword1" value="">
								<input type="hidden" name="checkValue" value="<%=checkValue%>">
								<% if(StringUtil.isValid(dropdownOpt)) { %>
								<input type="hidden" name="dropdownOpt" value="<%=dropdownOpt%>">
								<% } %>
								<% if(StringUtil.isValid(radioOpt)) { %>
								<input type="hidden" name="radioOption" value="<%=radioOpt%>">
								<% } %>
								<% if(StringUtil.isValid(checkboxOpt)) { %>
								<input type="hidden" name="checkboxOption"
									value="<%=checkboxOpt%>">
								<% } %>
								<td width="30%" class="TextLbl_b" align="right">
								<div id="dispName"><span class="urgent_fields">*</span><%=LanguageUtil.getString("Display Name",(String)session.getAttribute("userLanguage"))%>
								:</div>
								<%--P_B_CM_35418 --%></td>
								<td width="70%">
								<div id="displayC" style="display: none; clear: both;"><span
									class="TextLbl"><%=LanguageUtil.getString("Country")%></span></div>
								<div id="displayD" style="display: none; clear: both;"><span
									class="small_txt"><%=LanguageUtil.getString("Document")%></span></div>
								<input maxlength="255" type="text"
									name="<%=BuilderFormFieldNames.DISPLAY_NAME%>"
									value="<%=StringEscapeUtils.escapeHtml(fieldvalues.getString(BuilderFormFieldNames.DISPLAY_NAME))%>"
									class="fTextBox" style="width: 250px; clear: both;">
								<div id="display1" style="display: none; clear: both;">
								<div id="displayS" style="display: none; clear: both;"><span
									class="TextLbl"><%=LanguageUtil.getString("State")%></span></div>
								<div id="displayT" style="display: none; clear: both;"><span
									class="small_txt"><%=LanguageUtil.getString("Title")%></span></div>
								<input maxlength="255" type="text"
									name="<%=BuilderFormFieldNames.DISPLAY_NAME%>1" value=''
									class="fTextBox" style="width: 250px;"></div>
								</td>
							</tr>
							
							<%if(!checkForCM &&  !("mu".equals(subModuleName) || "area".equals(subModuleName))) {%>
							<%
							if(StringUtil.isValidNew(displayType) && !"true".equals(multiSubmitForm) && !"true".equals(isTabularSection) && !isInputVal && (!"File".equals(currField.getDisplayTypeField()) && !"Combo".equals(currField.getDisplayTypeField()) && !"Radio".equals(currField.getDisplayTypeField()) && !"Checkbox".equals(currField.getDisplayTypeField()))) { //P_Enh_	_Fields starts %>
							<tr>
								<td width="30%" class="text_b" align="right"></td>
								<td class="text_b" align="left">
									<input type="checkbox"
									name="syncField" id="syncField" onclick="showDuplicateCombo(this)"><span class="bText12"><%=LanguageUtil.getString("Sync Field",(String)session.getAttribute("userLanguage"))%></span>
								</td>
							</tr>
							
							<tr id="existingRadioTrNew" style="display: none; margin-top: 10px;">
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Module")%> : </td>
								<td class="TextLbl">
									<input type="radio" name="moduleSpecification" onclick="showHideModuleFields(this, 'modify')" value="within" checked><%=mainModule%>
									<%if(acrossModuleMap != null && acrossModuleMap.size() > 0) {
										for (String key : acrossModuleMap.keySet()) {
											String value = acrossModuleMap.get(key);%>
											<input type="radio" name="moduleSpecification" onclick="showHideModuleFields(this, 'modify')" value="<%=key%>"><%=LanguageUtil.getString(value, (String) session.getAttribute("userLanguage"), NewPortalUtils.getModuleKey(value))%>											
									<%	}
									} %>
								</td>
							</tr>
							
							<tr id="existingOptionNew" style="display: none; margin-top: 10px;">
								<td width="30%" class="text_b" align="right"><%=LanguageUtil.getString("Field to sync",(String)session.getAttribute("userLanguage"))%> :</td>
								<td class="text_b" align="left">
									<div class="TextLbl_b" id="fieldNamesForTabDiv">
									</div>
								</td>
							</tr>
							<%} else if("modify".equals(sAction) && StringUtil.isValidNew(syncWith)) { %>
							<tr>
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Field to sync",(String)session.getAttribute("userLanguage"))%> : 
								<td class="TextLbl">
									<%=builderForm.getSyncFieldDisplayName(syncWith)%>
								</td>
							</tr>
						<%}//P_Enh_Sync_Fields ends %>
							<%} %>
							<%
                //BB-20150319-268(FIM Campaign Center) starts
            if("3".equals(moduleId) && !"2".equals(sFormID) && !checkForContactHistory)
        	{
                if((isBuildFld && "Email".equals(fldValidationType))  || (!isBuildFld && "Email".equals(fldValidationType) && (isInputVal || isPiiEnabled))) { %>
							<tr>
								<td width="30%" class="text_b" align="right"></td>
								<td class="text_b" align="left"><input type="checkbox"
									name="includeInCampaignCheck" id="includeInCampaignCheck"
									<%=checked%>><span class="bText12"><%=LanguageUtil.getString("Include in Campaign Email List",(String)session.getAttribute("userLanguage"))%></span>
								<input type="hidden" name="<%=FieldNames.INCLUDE_IN_CAMPAIGN%>"
									value=""> <input type="hidden"
									name="oldIncludeInCampaign" value="<%=oldIncludeInCampaign%>">
								<input type="hidden" name="fldValidationType"
									value="<%=fldValidationType%>"> <input type="hidden"
									name="<%=BuilderFormFieldNames.SECTION%>"
									value='<%=fieldvalues.getString(BuilderFormFieldNames.SECTION)%>'>
								</td>
							</tr>
							<%}
            	
           } 
			//BB-20150319-268(FIM Campaign Center) ends%> <input type="hidden"
								name="<%=BuilderFormFieldNames.PRE_DISPLAY_NAME%>"
								value="<%=StringEscapeUtils.escapeHtml(fieldvalues.getString(BuilderFormFieldNames.DISPLAY_NAME))%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.IS_ACTIVE%>"
								value='<%=fieldvalues.getString(BuilderFormFieldNames.IS_ACTIVE)%>'>
							<%
		if(!isBuildFld && !StringUtil.isValid(dropdownOpt) && !(checkForCM && "modify".equals(sAction) && "File".equals(fldType))) { %> <%if(!"captivate".equals(subModuleName)){ //P_CM_B_61858%>
							<tr id="searchExport">
								<td class="TextLbl_b" align="right" width="30%" nowrap><%=LanguageUtil.getString("Is Exportable and Searchable",(String)session.getAttribute("userLanguage"))%>?</td>
								<td width="70%" class="text">
								<%
		if (fieldvalues.getString(BuilderFormFieldNames.EXPORTABLE).equals("true") || fieldvalues.getString(BuilderFormFieldNames.EXPORTABLE).equals("0")) {
			
%> <input type="radio" name="exportable" value="0" checked> <%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%>
								<input type="radio" name="exportable" value="1"> <%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%>
								<%
			} else {
%> <input type="radio" name="exportable" value="0"> <%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%>
								<input type="radio" name="exportable" value="1" checked>
								<%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%>
								<%
			}
%>
								</td>
							</tr>
							<%} %> <%
		if(!isBuildFld && !isInputVal && !isFieldIsInReport && !StringUtil.isValid(dropdownOpt) && !(checkForCM && "modify".equals(sAction) && "File".equals(fldType)) && !isPiiEnabled) { //P_CM_B_61858 //P_B_81880 %>
							<tr>
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Field Type",(String)session.getAttribute("userLanguage"))%>
								:</td>
								<%--P_B_CM_35418 --%>
								<td width="70%"><select
									name="<%=BuilderFormFieldNames.DB_COLUMN_TYPE%>"
									class="multiList" onChange="javascript:selectDiv()"
									style="float: left;" <%=disableFieldTypeCombo%>>
									<option value="varchar" <%=(option == 1)?"selected":""%>><%=LanguageUtil.getString("Text",(String)session.getAttribute("userLanguage"))%></option>
									<%--<option value="int" <%=(option == 2)?"selected":""%>><%=LanguageUtil.getString("Number",(String)session.getAttribute("userLanguage"))%></option>--%>
									<option value="text" <%=(option == 3)?"selected":""%>><%=LanguageUtil.getString("Text Area",(String)session.getAttribute("userLanguage"))%></option>
									<option value="Date" <%=(option == 4)?"selected":""%>><%=LanguageUtil.getString("Date",(String)session.getAttribute("userLanguage"))%></option>
									<option value="combo" <%=(option == 5)?"selected":""%>><%=LanguageUtil.getString("Drop-down",(String)session.getAttribute("userLanguage"))%></option>
									<option value="radio" <%=(option == 6)?"selected":""%>><%=LanguageUtil.getString("Radio",(String)session.getAttribute("userLanguage"))%></option>
									<option value="numeric" <%=(option == 7)?"selected":""%>><%=LanguageUtil.getString("Numeric",(String)session.getAttribute("userLanguage"))%></option>
									<option value="checkbox" <%=(option == 8)?"selected":""%>><%=LanguageUtil.getString("Checkbox",(String)session.getAttribute("userLanguage"))%></option>
									<option value="multiselect" <%=(option == 9)?"selected":""%>><%=LanguageUtil.getString("Multi Select Drop-down",(String)session.getAttribute("userLanguage"))%></option>
									<option value="file" <%=(option == 10)?"selected":""%>><%=LanguageUtil.getString("Document",(String)session.getAttribute("userLanguage"))%></option>
									<%if(!(ModuleUtil.zcubatorImplemented() || "true".equals(multiSubmitForm) || StringUtil.isValidNew(displayType) || "true".equals(isTabularSection)) && !("mu".equals(subModuleName) || "area".equals(subModuleName))) { //P_Enh_Sync_Fields%>
										<option value="existing" <%=(option == 11)?"selected":""%>><%=LanguageUtil.getString("Sync",(String)session.getAttribute("userLanguage"))%></option>
									<%} %>
									<%--//BB-20150203-259 (Add Document as field for positioning) --%>
								</select>
								<div class="text" id="dropDownOption"
									style="display: none; float: left;" class="TextLbl_b"><%--P_B_ADMIN_43851 --%>
								<%--FB_DATATYPE_JS--%> <%-- BB-20150203-259 (Dynamic Response based on parent field response) starts --%>
								<label id="dropDownOption0"><input type="radio"
									name="dropdownoption" value="0" onClick="showHide()" checked
									<%=disabledFieldType%>><%=LanguageUtil.getString("New",(String)session.getAttribute("userLanguage"))%></label>
								<label id="dropDownOption1"><input type="radio"
									name="dropdownoption" value="1" onClick="showHide()"
									<%=disabledFieldType%>><%=LanguageUtil.getString("Country",(String)session.getAttribute("userLanguage"))%></label>
								<label id="dropDownOption2"><input type="radio"
									name="dropdownoption" value="2" onClick="showHide()"
									<%=disabledFieldType%>><%=LanguageUtil.getString("Country & State",(String)session.getAttribute("userLanguage"))%></label>
								<label id="dropDownOption3"><input type="radio"
									name="dropdownoption" value="3" onClick="showHide()"
									<%=disabledFieldType%>><%=LanguageUtil.getString("Dependent",(String)session.getAttribute("userLanguage"))%></label>
								<%-- BB-20150203-259 (Dynamic Response based on parent field response) ends --%>
								<%--FB_DATATYPE_JS--%></div>
								<%-- BB-20150203-259 (Dynamic Response based on parent field response) starts --%>
								<div class="text" id="radioOption"
									style="display: none; float: left;" class="TextLbl_b"><label
									id="radioOption0"><input type="radio"
									name="radioOption" value="0" onClick="showHide()" checked
									<%=disabledFieldType%>><%=LanguageUtil.getString("New",(String)session.getAttribute("userLanguage"))%></label>
								<label id="radioOption1"><input type="radio"
									name="radioOption" value="1" onClick="showHide()"
									<%=disabledFieldType%>><%=LanguageUtil.getString("Dependent",(String)session.getAttribute("userLanguage"))%></label>
								</div>
								<div class="text" id="checkboxOption"
									style="display: none; float: left;" class="TextLbl_b"><label
									id="checkboxOption0"><input type="radio"
									name="checkboxOption" value="0" onClick="showHide()" checked
									<%=disabledFieldType%>><%=LanguageUtil.getString("New",(String)session.getAttribute("userLanguage"))%></label>
								<label id="checkboxOption1"><input type="radio"
									name="checkboxOption" value="1" onClick="showHide()"
									<%=disabledFieldType%>><%=LanguageUtil.getString("Dependent",(String)session.getAttribute("userLanguage"))%></label>
								</div>
								<%-- BB-20150203-259 (Dynamic Response based on parent field response) ends --%>
								<div id="filetypeOption" style="display: none; float: left;">
								<input type="radio" name="filetypeoption" value="0"
									onClick="showHide()" checked><%=LanguageUtil.getString("Document & Title",(String)session.getAttribute("userLanguage"))%>
								<input type="radio" name="filetypeoption" value="1"
									onClick="showHide()"><%=LanguageUtil.getString("Document",(String)session.getAttribute("userLanguage"))%>
								</div>
								<%
				if(StringUtil.isValidNew(disabledFieldType)) { %> <input
									type="hidden" name="dropdownoption"
									value="<%=currField.getFieldDropdownOpt()%>"> <input
									type="hidden" name="radioOption"
									value="<%=currField.getFieldRadioOption()%>"> <input
									type="hidden" name="checkboxOption"
									value="<%=currField.getFieldCheckboxOption()%>"> <input
									type="hidden" name="dataTypeModify"
									value="<%=typeList.get(option-1)%>"> <%} %>
								</td>
							</tr>
							
							<%-- P_Enh_Sync_Fields starts --%>
							<%if(!(ModuleUtil.zcubatorImplemented() || "true".equals(multiSubmitForm) || StringUtil.isValidNew(displayType) || "true".equals(isTabularSection)) && !("mu".equals(subModuleName) || "area".equals(subModuleName))) { //P_Enh_Sync_Fields%>
							<tr id="existingRadioTr">
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Module")%> : </td>
								<td class="TextLbl">
									<input type="radio" name="moduleSpecification" onclick="showHideModuleFields(this, 'create')" value="within" checked><%=mainModule%>
									<%if(acrossModuleMap != null && acrossModuleMap.size() > 0) {
										for (String key : acrossModuleMap.keySet()) {
											String value = acrossModuleMap.get(key);%>
											<input type="radio" name="moduleSpecification" onclick="showHideModuleFields(this, 'create')" value="<%=key%>"><%=LanguageUtil.getString(value, (String) session.getAttribute("userLanguage"), NewPortalUtils.getModuleKey(value))%>											
									<%	}
									} %>
								</td>
							</tr>
							<tr id="existingOption">
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Field to sync",(String)session.getAttribute("userLanguage"))%> : </td> 
								<td>
								<div class="TextLbl_b" id="fieldNamesForTabDiv">
								</div>
								</td>
							</tr>
							<%} %>
								<%-- P_Enh_Sync_Fields ends --%>
							

							<% 			}
		}
	    ArrayList fieldOrderList = new ArrayList();
    	//fieldOrderList = builderWebForm.selectOrder(sFormID , sFieldNo);//BB_Naming_Convention
	    int size = 0;//fieldOrderList.size();
        if (size != 0 ) {
%>
							<tr>
								<td class="TextLbl_b" align="right" width="30%"><%=LanguageUtil.getString("Order",(String)session.getAttribute("userLanguage"))%>:</td>
								<td class="text" width="70%"><select name="orderNo"
									class="dropdown_list">
									<option value="0"><%=LanguageUtil.getString("Select",(String)session.getAttribute("userLanguage"))%></option>
									<%
		    String orderValue[] = null;
            for (int k=0;k<size;k++) {
			    orderValue = (String[])fieldOrderList.get(k);
%>
									<option value="<%=orderValue[0]%>"
										<%=(orderValue[0].equals(fieldvalues.getString(""))? "selected": "")%>><%=LanguageUtil.getString("After",(String)session.getAttribute("userLanguage"))%>
									<%=orderValue[1]%></option>
									<%	        }
%>
								</select></td>
							</tr>
							<%
	    } else {
	    	if (sAction.equals("add"))
	        {
%> <input type="hidden" name="orderNo"
								value="<%=fieldvalues.getString(BuilderFormFieldNames.ORDER_NO)%>">
							<%
			} else {
%> <!-- <input type="hidden" name="orderNo" value="0"> --> <%
			}
	    }
%>
							
						</table>
						</td>
						<td width="1"></td>
					</tr>
					<% if(!isBuildFld && !isInputVal&& !isPiiEnabled) {%>
					<tr>
						<td width="1"></td>
						<td>
						<%

	    if (option == 3) {
%>
						<div id="textAreaSize" style="display: show"><script
							language="javaScript">
                        //resizeTo(630,310);
                         if(parent.$.fn.colorbox) {
                    parent.$.fn.colorbox.vkResize(1050,450);//Bug_35153
                      }
                </script> <%	    } else {
%>
						<div id="textAreaSize" style="display: none">
						<%
	    }
%>
						<table width="100%" cellspacing="1" cellpadding="4" border="0">
							<tr>
								<td class="TextLbl_b" width="30%" height="25" align="right"><span
									class="urgent_fields">*</span><%=LanguageUtil.getString("No. of Columns",(String)session.getAttribute("userLanguage"))%>:</td>
								<td width="70%"><input type="text" name="noOfCols"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.NO_OF_COLUMN)%>"
									maxlength="10" size="10" class="fTextBox"
									onkeypress="checkInteger(this,this.value,true,event)"><%--BUILDER_DECIMAL_ISSUE--%>
								<span class="small_txt"><%=LanguageUtil.getString("Column value limit is ",(String)session.getAttribute("userLanguage"))%>
								60</span></td>
							</tr>
							<tr>
								<td class="TextLbl_b" align="right" width="30%"><span
									class="urgent_fields">*</span><%=LanguageUtil.getString("No. of Rows",(String)session.getAttribute("userLanguage"))%>:</td>
								<td width="70%"><input type="text" name="noOfRows"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.NO_OF_ROW)%>"
									maxlength="10" size="10" class="fTextBox"
									onkeypress="checkInteger(this,this.value,true,event)"><%--BUILDER_DECIMAL_ISSUE--%>
								<!-- P_CM_B_41639 --> <span class="small_txt"><%=LanguageUtil.getString("Rows value limit is ",(String)session.getAttribute("userLanguage"))%>
								5</span></td>
							</tr>
						</table>
						</div>
						</td>
						<td width="1"></td>
					</tr>
					<% } %>
					<%
if(!isBuildFld && !isInputVal && !isPiiEnabled) { %>
					<tr>
						<td width="1"></td>
						<td>
						<%
 
	    if (option == 1 || option == 2) {
%>
						<div id="textNumber" style="display: show">
						<%	    } else {
%>
						<div id="textNumber" style="display: none">
						<%
	    }
%>
						<table width="100%" cellspacing="1" cellpadding="4" border="0">
							<tr class="">
								<td class="TextLbl_b" width="30%" height="25" align="right"><span
									class="urgent_fields">*</span><%=LanguageUtil.getString("Maximum Length",(String)session.getAttribute("userLanguage"))%>
								:</td>
								<td width="70%"><input type="text" style="float: left;"
									name="<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>"
									value="<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH)%>"
									maxlength="10" size="10" class="fTextBox"
									onkeypress="checkInteger(this,this.value,true,event)"><%--BUILDER_DECIMAL_ISSUE--%>
								<div id="stext"
									style="display:<%=(option == 1)?"block":"none"%>;float:left;"><span
									class="small_txt">&nbsp;<%=LanguageUtil.getString("Maximum length is 255 characters",(String)session.getAttribute("userLanguage"))%></span></div>
								<div id="snumber" style="display: none; float: left;"><span
									class="small_txt"><%=LanguageUtil.getString("Value limit is 10",(String)session.getAttribute("userLanguage"))%></span></div>
								</td>
							</tr>
							<tr class="">
								<td class="TextLbl_b" width="30%" height="25" align="right"><%=LanguageUtil.getString("Validation",(String)session.getAttribute("userLanguage"))%>
								:</td>
								<td width="70%"><!--  <select name="fldValidationType" class="multiList" style="float:left;">  -->
								<%-- BB-20150319-268(FIM Campaign Center) starts --%> <% if("3".equals(moduleId) && !"2".equals(sFormID) && !checkForContactHistory)
        		{%> <select name="fldValidationType" class="multiList"
									style="float: left;" onchange="showHideCampaignCheckBox(this);">
									<%} else { %>
									<select name="fldValidationType" class="multiList"
										style="float: left;">
										<%} %>
										<%-- BB-20150319-268(FIM Campaign Center) Ends --%>
										<option value="None"
											<%=(fldValidationType == null || "None".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("None",(String)session.getAttribute("userLanguage"))%></option>
										<option value="Phone"
											<%=(fldValidationType != null && "Phone".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Phone",(String)session.getAttribute("userLanguage"))%></option>
										<%if(!"true".equals(isTabularSection)){ %>
										<option value="Email"
											<%=(fldValidationType != null && "Email".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Email",(String)session.getAttribute("userLanguage"))%></option>
										<%} %>
										<option value="Url"
											<%=(fldValidationType != null && "Url".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Url",(String)session.getAttribute("userLanguage"))%></option>
										<%--BB-20150203-259	(Url validation for Text display type of field.) --%>
										<%--<option value="Percentage" <%=(fldValidationType != null && "Percentage".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Percentage",(String)session.getAttribute("userLanguage"))%></option> --%>
										<%--
						<option value="Integer" <%=(fldValidationType != null && "Integer".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Integer",(String)session.getAttribute("userLanguage"))%></option>
						<option value="Double" <%=(fldValidationType != null && "Double".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Double",(String)session.getAttribute("userLanguage"))%></option>
						 --%>
									</select>
									<%-- BB-20150319-268(FIM Campaign Center) starts --%>

									<span id="campaignCheckBox" style="padding: 5px; <%=display%> ">
									<input type="checkbox" name="includeInCampaignCheck"
										id="includeInCampaignCheck" <%=checked%>><span
										class="bText12"><%=LanguageUtil.getString("Include in Campaign Email List",(String)session.getAttribute("userLanguage"))%></span>
									<input type="hidden" name="<%=FieldNames.INCLUDE_IN_CAMPAIGN%>"
										value=""> <input type="hidden"
										name="oldIncludeInCampaign" value="<%=oldIncludeInCampaign%>">
									</span>
									<%-- BB-20150319-268(FIM Campaign Center) ends --%></td>
							</tr>
						</table>
						</div>
						</td>
						<td width="1"></td>
					</tr>

					<tr>
						<td width="1"></td>
						<td>
						<%
 
	    if (option == 7) {
%>
						<div id="textNumber1" style="display: show">
						<%	    } else {
%>
						<div id="textNumber1" style="display: none">
						<%
	    }
%>
						<table width="100%" cellspacing="1" cellpadding="4" border="0">
							<!-- P_CM_B_41640 start -->
							<tr class="">
								<td class="TextLbl_b" width="30%" height="25" align="right"><%=LanguageUtil.getString("Validation",(String)session.getAttribute("userLanguage"))%>
								:</td>
								<td width="70%" class="TextLbl_b"><select
									name="fldValidationType1" class="multiList"
									style="float: left;">
									<!-- P_CM_B_41640 end -->
									<option value="Integer"
										<%=(fldValidationType != null || "Integer".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Integer",(String)session.getAttribute("userLanguage"))%></option>
									<option value="Double"
										<%=(fldValidationType != null && "Double".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Currency",(String)session.getAttribute("userLanguage"))%></option>
									<option value="Percentage"
										<%=(fldValidationType != null && "Percentage".equals(fldValidationType))?"selected":""%>><%=LanguageUtil.getString("Percentage",(String)session.getAttribute("userLanguage"))%></option>
								</select></td>
							</tr>
						</table>
						</div>
						</td>
						<td width="1"></td>
					</tr>
					<tr>
						<td width="1"></td>
						<td>
						<%
 		// OPTION_VIEW_H_V
	    if (option == 6 || option == 8) {
%>
						<div id="optionView" style="display: show">
						<%	    } else {
%>
						<div id="optionView" style="display: none">
						<%
	    }
%>
						<table width="100%" cellspacing="1" cellpadding="4" border="0">
							<tr class="">
								<!-- theme_tb_data_b -->
								<!-- P_CM_B_41640 start -->
								<td class="TextLbl_b" width="30%" height="25" align="right"><%=LanguageUtil.getString("Field View",(String)session.getAttribute("userLanguage"))%>
								:</td>
								<td width="70%" class="TextLbl_b"><input type="radio"
									name="optviewtype" value="0"
									<%="0".equals(fldViewType)?"checked":""%>><%=LanguageUtil.getString("Horizontal",(String)session.getAttribute("userLanguage"))%>
								<input type="radio" name="optviewtype" value="1"
									<%="1".equals(fldViewType)?"checked":""%>><%=LanguageUtil.getString("Vertical",(String)session.getAttribute("userLanguage"))%>
								</td>
							</tr>
							<!-- P_CM_B_41640 end -->
						</table>
						</div>
						</td>
						<td width="1"></td>
					</tr>
					<% }  %><%--SMC-20140213-378--%>
					<%--SMC-20140213-378 Starts--%>
					<tr>
						<td width="1"></td>
						<td>
						<%
	    if (option == 5 || option == 6 || option == 8||option == 9) {
	%>
						<div id="optionsDiv" style="display: show">
						<%	} else {
	%>
						<div id="optionsDiv" style="display: none">
						<%
	    }
	%>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="1"></td>
								<td height="22" width="100%">
								<table width="100%" border="0" cellspacing="0" cellpadding="4">
									<%-- BB-20150203-259 (Dynamic Response based on parent field response) starts --%>
									<%if(!isBuildFld) { %>
									<tr id="dependencyTr" style="display: none;">
										<td width="30%" class="TextLbl_b" align="right"
											nowrap="nowrap">
										<div id="dispName">
										<%if(!canDisplayParentFieldName) { %> <span
											class="urgent_fields">*</span><%=LanguageUtil.getString("Select Parent Field",(String)session.getAttribute("userLanguage"))%>
										: <%} else { %> <%=LanguageUtil.getString("Parent Field",(String)session.getAttribute("userLanguage"))%>
										: <%} %>
										</div>
										</td>
										<td width="70%" class="TextLbl">
										<%
				Info allFieldsInfo = new Info();
				if(activeFields != null) {
					for(Field field : activeFields) {
						if(field == null) {
							continue;
						}
						String displayName = field.getDisplayName();
						/*if(StringUtil.isValidNew(displayName) && displayName.length() > 25) {
							displayName = displayName.substring(0, 25) + "...";
						}*/
						/*if("leadSource3ID".equals(field.getFieldName()) || "franchiseAwarded".equals(field.getFieldName())) { //because it is dependent on other field i.e. Lead Source Category
							continue;
						}*/
						SequenceMap syncTotalMapField = field.getSyncTotalMap();
						String syncWithField = field.getSyncWithField();
						if(syncTotalMapField != null && syncTotalMapField.size() > 0) {
							continue;
						}
						
						if(StringUtil.isValidNew(syncWithField)) {
							continue;
						}
						
						if(specialFields.contains(field.getFieldName())) { //not the option for parent field
							continue;
						}
						
						if("Combo".equals(field.getDisplayTypeField()) || "Radio".equals(field.getDisplayTypeField()) || "Checkbox".equals(field.getDisplayTypeField())) {
							if(fieldvalues != null && !fieldvalues.getString(BuilderFormFieldNames.FIELD_NAME).equals(field.getFieldName())) {
								allFieldsInfo.put(field.getFieldName()+"##"+field.getDisplayTypeField()+"##"+field.isFieldOfOtherTable(), displayName);
							}
						}
					}
				}
				%> <% if(!canDisplayParentFieldName) { %> <%=InfoUtil.getComboString("dependentField", "getDependentConditionAndValue(this.value, false);", allFieldsInfo, "", "dependentField", dependentParentValue, "", LanguageUtil.getString("Select"), null)%>
										<% } else { %> <%=dependentParentDisplayName%>
										<input type="hidden" name="dependentField"
											value="<%=dependentParentValue%>" id="dependentField" /> <% } %>
										</td>
									</tr>
									<tr class="thead" id="columnLabelTr" style="display: none;">
										<td width="30%" class="TextLbl_b" align="right"
											nowrap="nowrap"></td>
										<td width="70%" class="TextLbl_b">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td width="40%" class="TextLbl_b"><%=LanguageUtil.getString("New Field Value")%></td>
												<td width="50%" class="TextLbl_b"><%=LanguageUtil.getString("Parent Field Value")%></td>
											</tr>
										</table>
										</td>
									</tr>
									<%} %>
									<tr>
										<td colspan="2">
										<table width="100%" border="0" cellspacing="1" cellpadding="4"
											id='table1'>
											
											<%if(!StringUtil.isValidNew(syncWith)) { %>
											<%if(!hasOptions) { %>
											<tr id="row_1">

												<!--  P_CM_B_41640 start-->
												<td width="30%" align="right" class="TextLbl_b"><span
													class='urgent_fields'>*</span><%=LanguageUtil.getString("Option") %>
												1 :</td>
												<%--P_B_ADMIN_CM_35382 --%>
												<td align="left" class="TextLbl_b" width="70%"
													nowrap="nowrap"><!--  P_CM_B_41640 end--> <input
													type="text" size='35' value="" name="optionNameTemp"
													class="fTextBox" onchange="checkDuplicate(this)"> <input
													type='hidden' id="optionActivated_1" value='Y'
													name='optionActivated'>
												<div style="display: inline;" id="fieldDependentValueDiv_1">
												</div>
												<%-- BB-20150203-259 (Dynamic Response based on parent field response) ends --%>
												<%-- 
						<span class = "<%="theme_tb_data_b"%>" id = "removeTD1">
						<a href = "javascript:void(0)" onclick = "addMore(1)"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png' border = '0' alt="Add More"></a>
						</span>
						--%></td>
											</tr>
											<%} else { 
						boolean showDeleteOpt = false;
						Info info = null;
						String activeText = FieldNames.EMPTY_STRING;
						String moverFunction = FieldNames.EMPTY_STRING;
						String optionActivated = FieldNames.EMPTY_STRING;
						String deactivateRowCls = FieldNames.EMPTY_STRING;
						for(int i = 1 ; i <= smap.size() ;i++) {
						info = (Info)smap.get(i-1);
						canDelete = info.getString(BuilderFormFieldNames.CAN_DELETE);
						optionActivated = info.getString(BuilderFormFieldNames.OPTION_ACTIVATED);
						if("N".equals(optionActivated)){
							deactivatedCount++;
						}
						showDeleteOpt = "true".equals(canDelete);
						deactivateRowCls = showDeleteOpt?"":"actval";
						if(i%2==0) {
							className = "TextLbl_b" ;  
						} else {
							className =  "TextLbl_b" ;   //P_CM_B_41639   
						}
						%>
											<tr id="row_<%=i %>"
												class="<%=className%> <%=deactivateRowCls%>">
												<td width="30%" align="right"><span
													class='urgent_fields'>*</span><%=LanguageUtil.getString("Option")+" "+i %>
												:</td>
												<%--P_B_ADMIN_CM_35382 --%>
												<td align="left" width="70%" nowrap="nowrap"><input
													type="text" size="35"
													value="<%=StringEscapeUtils.escapeHtml(info.getString(BuilderFormFieldNames.OPTION_VALUE))%>"
													name="optionNameTemp" class="fTextBox"
													onchange="checkDuplicate(this)"> <input
													type='hidden' id="optionActivated_<%=i %>"
													value='<%=optionActivated%>' name='optionActivated'>
													<input type='hidden' id="optionNameTempValue" value='<%=info.getString(BuilderFormFieldNames.OPTION_ID)%>' name='optionNameTempValue'><!-- Alleg-20160615-349 -->
												<%-- BB-20150203-259 (Dynamic Response based on parent field response) starts --%>
												<div style="display: inline;"
													id="fieldDependentValueDiv_<%=i%>"></div>
												<%-- BB-20150203-259 (Dynamic Response based on parent field response) ends --%>
												<span id="removeTD<%=i%>" class="pd5"> <%--
							<%if(i == smap.size()) { %>
							 		<a href = "javascript:void(0)" onclick = "addMore(<%=i%>)"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png' border = '0' alt="Add More"></a>
							<%} %>
							--%> <%if(showDeleteOpt){ %> <a href="javascript:void(0)"
													onclick="removeOption(<%=i%>)"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif" title="<%=LanguageUtil.getString("Delete")%>" border = '0'></a> <%}%> </span> <%if(!showDeleteOpt){ %> <span
													id="deactivateSpan_<%=i%>" style="margin-left: -4px">
												<% if("Y".equals(optionActivated)){
                                    moverFunction = "<a href = \"javascript:void(0)\" onClick =\"deActivateOpt("+i+")\" title=\""+LanguageUtil.getString("Click to Deactivate this Option")+"\" ><img src=\""+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/lang/"+session.getAttribute("userLanguage")+"/deactivate.png\" border = '0'></a>";
								}else{
                                    moverFunction = "<a href = \"javascript:void(0)\" onClick = \"activateOpt("+i+")\" title=\""+LanguageUtil.getString("Click to Activate this Option")+"\" ><img src=\""+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/lang/"+session.getAttribute("userLanguage")+"/activate.png\" border = '0'></a>";
                             	}%> <%=moverFunction%> </span> <script>
								 	setTimeout(function() {
								 		if(document.getElementsByName("fieldDependentValue")[<%=i-1%>]) {
								 			document.getElementsByName("fieldDependentValue")[<%=i-1%>].disabled = true;
								 			input = document.createElement('input');
											input.setAttribute('type', 'hidden');
											input.setAttribute('name', 'fieldDependentValue_disabled<%=i-1%>');
											input.setAttribute('id', 'fieldDependentValue_disabled<%=i-1%>');
											input.setAttribute('value', document.getElementsByName("fieldDependentValue")[<%=i-1%>].value);
											document.fieldModify.appendChild(input);
								 		}
								 	}, 500);
								</script> <% }%>
												</td>
											</tr>

											<%} %>
											<%} 
											 %>
										</table>
										</td>
									</tr>
									<tr>
										<td></td>
										<td align="right"><span id="removeTDForAddMore"> <!--  P_CM_B_41640 -->
										<a href="javascript:void(0)" onclick="addMore()"><span
											class="text"><%=LanguageUtil.getString("Add Option")%></span></a>
										</span></td>
									</tr>
									<%} %>
								</table>
								</td>
								<td width="1"></td>
							</tr>

						</table>
						</div>
						</td>
						<td width="1"></td>
					</tr>
					<%--SMC-20140213-378 Ends--%>
					<%--P_E_Mandate_Options ends--%>
					<tr>
						<td width="1"></td>
						<% 
		String sCharVal = "**"+LanguageUtil.getString("Avoid using special characters like")+" ! @ % # $ ^ & * _ : , ; < > \\";
		%>
						<td class="TextLbl_b"><font color="#ff6600"><i><%=sCharVal%>.</i></font>
						</td>
						<td width="1"></td>
					</tr>
					<%--SMC-20140213-378 --%>

					<input type=hidden name=formID value=<%=sFormID%>>
					<input type=hidden name=fieldId value=<%=fieldId%>>
					<input type=hidden name=action value=<%=sAction%>>

					<%	}


%>
				</table>

				</td>
				<!-- <td class="crvBox2-right" width="100%"></td> -->
			</tr>
			<tr>
				<%-- <td align="left" valign="bottom"><img width="10" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png"></td>				
				<!-- <td class="crvBox2-bl"></td> -->				
				<!-- <td class="crvBox2-botom"></td>	 -->
				<td align="right" valign="bottom"><img width="16" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png"></td>				 --%>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="20">
		<td></td>
	</tr>

	<tr>
		<td colspan="2" align="left" class="text"><input
			id="submitButton" name="submitButton" value="<%=formButtonTag%>"
			class="cm_new_button" type="button"
			style="margin-right: 6px; padding-left: 6px;"
			onClick="javascript:validate()" /><%-- P_B_CM_35418--%> <input
			id="Button" name="Button"
			value="<%=LanguageUtil.getString("Close")%>" class="cm_new_button"
			type="reset" style="margin-right: 5px;"
			onclick="if(parent.$.fn.colorbox){ parent.$.fn.colorbox.close() }else{window.close(); }" /></td>
	</tr>
	</form>

</table>
</div>



<%-- FB_DATATYPE_JS Starts--%>
<script type="text/javascript">

<%if (sAction.equals("modify")) {
	if(!isBuildFld && !isInputVal && !isFieldIsInReport && !StringUtil.isValid(dropdownOpt) && !(checkForCM && "modify".equals(sAction) && "File".equals(fldType)) && !isPiiEnabled) {//P_CM_B_61858  //P_B_81880
%>
	document.getElementById("dropDownOption1").style.display = "none";
	document.getElementById("dropDownOption2").style.display = "none";
<%}}%>
//SMC-20140213-378 Starts
var rowCount = <%=optionCount%>;
var deactCount = <%=deactivatedCount%>;
var rowNumCount = rowCount;
//BB-20150319-268(FIM Campaign Center) starts
function showHideCampaignCheckBox(f) {
    var value = f.value;
    if(value == 'Email') {
        document.getElementById("campaignCheckBox").style.display = "";
    } else {
        document.getElementById("campaignCheckBox").style.display = "none";
    }
}
//BB-20150319-268(FIM Campaign Center) ends
function addMore() {
	var classname =  "TextLbl_b"; /*  P_CM_B41640 start */
	var isOptUsed = $('#row_'+rowNumCount).hasClass('actval');
	if(!isOptUsed){
		$("#row_"+rowNumCount+" [id^='removeTD']").html("<a href = \"javascript:void(0)\" onclick = \"removeOption("+rowCount+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif\" title=\"<%=LanguageUtil.getString("Delete")%>\" border = '0'></a>");	
	}else{	
		$("#row_"+rowNumCount+" [id^='removeTD']").html("");
	}
	var x = document.getElementById('table1').insertRow(rowCount);
	rowCount++;
	x.id = "row_" +rowCount;
	var p = x.insertCell(0);
	var q = x.insertCell(1);
	var r = document.createElement("span");
	
	
	if(rowCount%2==0) {
		classname = "TextLbl_b"
	} else {
		classname = "TextLbl_b" 
	}//P_CM_B41640 start end.
	x.className = classname;
	
	p.align = "right";
	q.align = "left";
	
	p.width = "30%";
	q.width = "70%";
	//q.noWrap = true;
	
	p.innerHTML ="<span class='urgent_fields'>*</span>" + "<%=LanguageUtil.getString("Option")%> " + rowCount+" : ";      //   P_B_ADMIN_CM_35382
	//P_CM_B_41640
	var col2Html = "<input type = 'text' size='35' class = 'fTextBox' value = '' name = 'optionNameTemp' onchange = 'checkDuplicate(this)'>"; 
	col2Html = col2Html + "<input type='hidden' id = 'optionActivated_"+rowCount+"' value='Y' name = 'optionActivated'>";
	col2Html = col2Html + "<div style='display: inline;' id='fieldDependentValueDiv_"+rowCount+"'></div>";
	q.innerHTML = col2Html;
	r.id= "removeTD"+rowCount;
    r.className ="pd";
	r.innerHTML = "<a href = \"javascript:void(0)\" onclick = \"removeOption("+rowCount+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif\" title=\"<%=LanguageUtil.getString("Delete")%>\" border = '0'></a>";	
	//r.innerHTML = "  <a href = \"javascript:void(0)\" onclick = \"addMore()\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png\" border = '0'></a>"+"<a href = \"javascript:void(0)\" onclick = \"removeOption("+rowCount+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/lang/<%=session.getAttribute("userLanguage")%>/delete_icon.png\" border = '0'></a>";
	q.appendChild(r);
	rowNumCount++;
	//BB-20150203-259 (Dynamic Response based on parent field response) starts
	if(document.getElementById("dependentField") && document.getElementById("dependentField").value != "-1" && document.getElementById("dependentField").value != "") {
		var comboValue = document.getElementById("dependentField").value;
		createDependentValueDiv(comboValue, "fieldDependentValueDiv_"+rowCount, rowCount, false);
	}
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
	resizeWindow();
}
function removeOption(topRowNum) {
	
		var flag;
		<% if("modify".equals(sAction)) { %>
			//var option = document.getElementsByName("optionNameTemp")[topRowNum-1].value; //provides the option
			$.ajax({
		        type: "POST",
		        url: "handleAjaxRequest", 
		        data:"dataset=checkForParentField&fieldName=<%=fieldNameNew%>&tableAnchor=<%=val%>&otherTableField=<%=isOtherTableField%>&option="+topRowNum,
		        async:false,
		        success:function(data) {
		        	if(data.trim() == "true") {
		        		FCI.Messages(FCLang.OPTION_CANNOT_BE_DELETED_AS_IT_IS_ASSOCIATED_WITH_OPTION_OF_A_DEPENDENT_FIELD);
		        		flag = true;
		        	}
		        }
			});
			if(flag) {
				return false;
			}
		<% } %>
		if(rowCount-1 == deactCount)
		{
			FCI.Messages(FCLang.ATLEAST_ONE_OPTION_IS_REQUIRED);
			return false;
		}
		var thisRowId = "row_"+topRowNum;
		var lastRowId = $('#table1 tr:last').attr('id');
		var indexRow=document.getElementById(thisRowId).rowIndex;
		document.getElementById('table1').deleteRow(indexRow);
		updateRows();
		if(rowCount==2)
		{
			var table = document.getElementById("table1");
	    	var row = table.rows[0];
	    	if(row)
	    	{
	    		var rowId = trim(row.id);
                var id = rowId.substring(rowId.indexOf("_")+1,rowId.length);
                var columnId = "removeTD"+id;
               	$('#'+columnId).html("");
               	//$('#'+columnId).html("  <a href = \"javascript:void(0)\" onclick = \"addMore(1)\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png\" border = '0'></a>");
	    	}
		}else if(topRowNum==rowCount){
			var removeTD="removeTD"+(rowCount-1);
			var isOptUsed = $('#row_'+(rowCount-1)).hasClass('actval');
			if(isOptUsed){
				//$('#'+removeTD).html("  <a href = \"javascript:void(0)\" onclick = \"addMore("+(topRowNum-1)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png\" border = '0'></a>");
				$('#'+removeTD).html("	");
			}else{
				//$('#'+removeTD).html("  <a href = \"javascript:void(0)\" onclick = \"addMore("+(topRowNum-1)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/addmore.png\" border = '0'></a>"+"<a href = \"javascript:void(0)\" onclick = \"removeOption("+(topRowNum-1)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/lang/<%=session.getAttribute("userLanguage")%>/delete_icon.png\" border = '0'></a>");
				$('#'+removeTD).html("<a href = \"javascript:void(0)\" onclick = \"removeOption("+(topRowNum-1)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif\" title=\"<%=LanguageUtil.getString("Delete")%>\" border = '0'></a>");					
			}
		}
		rowCount--;
		rowNumCount--;
		resizeWindow();//Bug_35153
}

function updateRows(){
	$('#table1 tr').each(function(i) {
		$(this).attr("id","row_"+(++i));
		$(this).find('td:first').html("<span class='urgent_fields'>*</span>"+"<%=LanguageUtil.getString("Option")%> "+(i)+" : ");        // P_B_ADMIN_CM_35382
		$(this).find('td:first, td:nth-child(2)').find('input[id^=optionActivated]').attr("id","optionActivated_"+i);
		$(this).find('td:first, td:nth-child(2)').find('div[id^=fieldDependentValueDiv]').attr("id","fieldDependentValueDiv_"+i); //BB-20150203-259 (Dynamic Response based on parent field response) starts
		var linksSpan = $(this).find('td:last').find('span[id^=removeTD]');
		if(typeof linksSpan!='undefined' && linksSpan!=null){
			linksSpan.attr("id","removeTD"+i);
			linksSpan.find('a').each(function(){
				var onClickVal = $(this).attr("onclick");
				if(typeof onClickVal!='undefined' && onClickVal!=null && onClickVal.match("^removeOption")){
					$(this).attr("onclick","removeOption("+i+")");
				}
			});
		}
		if($(this).hasClass('actval')){
			var deactivateSpan = $(this).find('td:last').find('span[id^=deactivateSpan]');
			if(deactivateSpan!=null){
				deactivateSpan.attr("id","deactivateSpan_"+i);
				deactivateSpan.find('a').each(function(){
					var onClickFun = $(this).attr("onClick");
					if(typeof onClickFun!='undefined' && onClickFun!=null){
						if(onClickFun.match("^activate")){
							$(this).attr("onclick","activateOpt("+i+")");
						}else if(onClickFun.match("^deActivate")){
							$(this).attr("onclick","deActivateOpt("+i+")");
						}
					}
				});
			}
		}
		
		if(i%2==0) {
			/* P_CM_B_41640 */
			$(this).hasClass('actval')?$(this).attr("class","TextLbl_b actval"):$(this).attr("class","TextLbl_b"); 
		} else {
			$(this).hasClass('actval')?$(this).attr("class","TextLbl_b actval"):$(this).attr("class","TextLbl_b");
		}
	});
}
function activateOpt(rowNum){
	changeStatus(rowNum,"activateOpt");
}
function deActivateOpt(rowNum){
	changeStatus(rowNum,"deActivateOpt");
}
function changeStatus(rowNum,status)
{
	if("deActivateOpt" == status)
    {
		if(rowCount-1 == deactCount)
		{
			FCI.Messages(FCLang.ATLEAST_ONE_OPTION_IS_REQUIRED);
			return false;
		}
		deactCount++;
		$('#optionActivated_'+rowNum).attr("value", 'N');
		$('#deactivateSpan_'+rowNum).html("<a title=\"Click to Activate this Option\" href = \"javascript:void(0)\" onclick = \"activateOpt("+(rowNum)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/lang/<%=session.getAttribute("userLanguage")%>/activate.png\" border = '0'></a>");
    }
    else if("activateOpt" == status)
    {
    	deactCount--;
    	$('#optionActivated_'+rowNum).attr("value", 'Y');
    	$('#deactivateSpan_'+rowNum).html("<a title=\"Click to Deactivate this Option\" href = \"javascript:void(0)\" onclick = \"deActivateOpt("+(rowNum)+")\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/lang/<%=session.getAttribute("userLanguage")%>/deactivate.png\" border = '0'></a>");
    }
}

function checkDuplicate(obj) {
	var arr = document.getElementsByName("optionNameTemp");
	for(var i=0;i < arr.length;i++) {
		if(arr[i] != obj && trim(arr[i].value)==trim(obj.value)) {
			FCI.Messages(FCLang.THIS_OPTION_ALREADY_EXISTS);
			obj.value="";
			obj.focus();
			break;
		}
	}
}
function resizeWindow(){
	var height = document.getElementById("containerDiv").offsetHeight;
	//Bug_35153 Starts
	//if(height <= 400)
	{
		parent.$.fn.colorbox.vkResize(1050,height+inc); //BB-20150203-259 (Dynamic Response based on parent field response)
	}//Bug_35153 Ends
}
//SMC-20140213-378 Ends

function checkDuplicateName(s) 
{
	s=s.trim();
	var serviceID =s;
	var cName = "";
	var cNameArray = s.split(" ");
	for(var i=0;i<cNameArray.length;i++) {
		if(trim(cNameArray[i])!="") {
			cName = cName+" "+cNameArray[i];
		}
	}
	cName = trim(cName);
	return cName;
}

//BB-20150203-259 (Dynamic Response based on parent field response) starts
function showDependentSection(dependentVal) {
	var total = $('#table1 tr').length;
	if(dependentVal == "0") {
		//value is independent
		document.getElementById("dependencyTr").style.display = "none";
		document.getElementById("columnLabelTr").style.display = "none";
		for(var i=1; i<=total; i++) {
			$("#fieldDependentValueDiv_"+i).html('');
		}
		$('#dependentField').get(0).selectedIndex = 0;
	} else {
		//value is dependent
		document.getElementById("dependencyTr").style.display = "";
		document.getElementById("columnLabelTr").style.display = "";
	}
	resizeWindow();
}

function getDependentConditionAndValue(dependentValue, fromReady) {
	var total = $('#table1 tr').length;
	if(dependentValue == '-1') {
		document.getElementById("columnLabelTr").style.display = "none";
		for(var i=1; i<=total; i++) {
			$("#fieldDependentValueDiv_"+i).html('');
		}	
	} else {
		var fields = dependentValue.split('##')
		var tableAnchor='<%=val%>';
		var fieldName = fields[0];
		var displayType = fields[1];
		var otherTableField = fields[2];
		//both the dependent combo will populate on change of the fields.
		document.getElementById("columnLabelTr").style.display = "";
		for(var i=1; i<=total; i++) {
			createDependentValueDiv(dependentValue, "fieldDependentValueDiv_"+i, i, fromReady);
		}
	}
	resizeWindow()
}

function createDependentValueDiv(dependentValue, dependentValueId, optionID, fromReady) {
	var fields = dependentValue.split('##');
	var tableAnchor='<%=val%>';
	var fieldName = fields[0];
	var displayType = fields[1];
	var otherTableField = fields[2];
	if(displayType=='Numeric' ||  displayType=='Text'||  displayType=='Number'||  displayType=='Date'||  displayType=='TextArea' ) {
		$("#"+dependentValueId).html("<input type='text' class='fTextBox' size='10' value='' id='fieldDependentValue' name='fieldDependentValue'>");
	} else { //case for combo, radio and checkbox
		$.post( "handleAjaxRequest",{ dataset: "getFieldValueInCombo", fieldName:fieldName, tableAnchor:tableAnchor, otherTableField:otherTableField, optionID:optionID, currentFieldName:'<%=currentFieldName%>', fromReady:fromReady }).success(function(data) {
			$("#"+dependentValueId).html(data);
		});
	}
}
//BB-20150203-259 (Dynamic Response based on parent field response) ends

function showHideModuleFields(e, fromWhere) {
	if(e && e.checked) { //case when from changing the radio.
		$.post("handleAjaxRequest",{dataset: "populateFieldCombo", moduleName: e.value, displayType: "<%=displayType%>", val: "<%=val%>", validationType: "<%=validationType%>", fromWhere: fromWhere}).success(function(data) {
			$("#fieldNamesForTabDiv").html(data);
			jQuery.noConflict();  
			jQuery('.form-control-single').multipleSelect({ 
				filter: true,
		 		placeholder:"Select",
		 		single:true,
		 		multipleWidth: 101
		 	});
			$("#ms-parentfieldNamesForTabNew").css("width",'300px');
		});
	} else { //case when it directly comes on load of the page.
		$.post("handleAjaxRequest",{dataset: "populateFieldCombo", displayType: "<%=displayType%>", val: "<%=val%>", validationType: "<%=validationType%>", fromWhere: fromWhere}).success(function(data) {
			$("#fieldNamesForTabDiv").html(data);
			jQuery.noConflict();  
			jQuery('.form-control-single').multipleSelect({ 
				filter: true,
		 		placeholder:"Select",
		 		single:true,
		 		multipleWidth: 101
		 	});
	$("#ms-parentfieldNamesForTabNew").css("width",'300px');
		});
	}
	
}


   function showDuplicateCombo(e) {
		var selectedType = '<%=typeList.get(option-1)%>';
		document.fieldModify.dbColumnType.value = selectedType;
	   	if(e.checked) {
	   		document.getElementById("existingRadioTrNew").style.display = "";
	   		document.getElementById("existingOptionNew").style.display = "";
	   		document.fieldModify.dbColumnType.disabled = true;
	   		var height = 450;
	   		showHideModuleFields('', 'modify');
	   	} else {
	   		document.getElementById("existingRadioTrNew").style.display = "none";
	   		document.getElementById("existingOptionNew").style.display = "none";
	   		document.fieldModify.dbColumnType.disabled = false;
	   		var height = document.getElementById("containerDiv").offsetHeight+50;
	   	}
	   	selectDiv();
		parent.$.fn.colorbox.vkResize(1050, height); //BB-20150203-259 (Dynamic Response based on parent field response)
   }
   function validate() {
		if(showProcessingAlert() == 'true') {
			return false;		
		}
		var f = document.fieldModify;
		if(f.dbColumnType && (f.dbColumnType.value == "combo" || f.dbColumnType.value == "multiselect")) { //BB-20150203-259 (Add Document as field for positioning)
			var dropdowntype = document.fieldModify.dropdownoption;
			var filetype = document.getElementsByName("filetypeoption");
			
	        if (isEmpty(trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value))) {
		        if(f.dbColumnType.value == "combo" && (dropdowntype[0].checked || dropdowntype[3].checked)) { //BB-20150203-259 (Dynamic Response based on parent field response)
		        	FCI.Alerts(FCLang.DISPLAY_NAME);
		        } else if(f.dbColumnType.value == "combo"){
		        	FCI.Alerts(FCLang.COUNTRY+"'s "+FCLang.DISPLAY_NAME);
		        }else if(f.dbColumnType.value == "multiselect"){
		        	FCI.Alerts(FCLang.DISPLAY_NAME);
		        }else {
		        	FCI.Alerts(FCLang.DOCUMENT+" "+FCLang.DISPLAY_NAME);
		        }
	            f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
			    return false;
			}
	        if (trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value) != "") {
				var firstChar = trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value).substring(0, 1);
				var regExp = new RegExp("[a-zA-Z]");
				if (!firstChar.match(regExp)) {
			        if(f.dbColumnType.value == "combo" && dropdowntype[0].checked || dropdowntype[3].checked) {
						FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
			        } else if(f.dbColumnType.value == "combo"){
			        	FCI.Messages(FCLang.COUNTRY+"'s "+FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
			        }else if(f.dbColumnType.value == "multiselect"){
			        	FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
			        }else {
						FCI.Messages(FCLang.DOCUMENT+" "+FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
			        }
					f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
					return false;
				}
			}
	        if (trim(f.displayName.value) != "") {
				var fieldChar = trim(f.displayName.value);
				var regExp = new RegExp("[!|@%#$^&*_:,;<>\\\\]");
				if (fieldChar.match(regExp)) {
			        if(f.dbColumnType.value == "combo" && dropdowntype[0].checked || dropdowntype[3].checked) {
		     	  //	FCI.Messages(FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);  // CM_B_35380
						FCI.Messages(FCLang.PLEASE_REMOVE_THE_SPECIAL_CHARACTER);   
			        }else if(f.dbColumnType.value == "combo"){
			    
			        	FCI.Messages(FCLang.COUNTRY+"'s "+FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
			        } else if(f.dbColumnType.value == "multiselect"){
			        	FCI.Messages(FCLang.PLEASE_REMOVE_THE_SPECIAL_CHARACTER);
			        } else {
			       
						FCI.Messages(FCLang.DOCUMENT+" "+FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
			        }
					f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
					return false;
				}
			}
	        <%
			if (!sAction.equals("modify")) {
			%>
	        if((f.dbColumnType.value == "combo" && dropdowntype[2].checked) || (f.dbColumnType.value == "file" && filetype[0].checked)) {
		        if (isEmpty(trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.value))) {
			        if(f.dbColumnType.value == "combo" && dropdowntype[2].checked) {
			        	FCI.Alerts(FCLang.STATE+"'s "+ FCLang.DISPLAY_NAME);
			        } else {
			        	FCI.Alerts(FCLang.Title+" "+ FCLang.DISPLAY_NAME);
			        }   
		            f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.focus();
				    return false;
				}
		        if (trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.value) != "") {
					var firstChar = trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.value).substring(0, 1);
					var regExp = new RegExp("[a-zA-Z]");
					if (!firstChar.match(regExp)) {
				        if(f.dbColumnType.value == "combo" && dropdowntype[2].checked) {
							FCI.Messages(FCLang.STATE+"'s "+FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
				        } else {
							FCI.Messages(FCLang.Title+" "+ FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
				        }   
						f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.focus();
						return false;
					}
				}
		        if (trim(f.displayName1.value) != "") {
					var fieldChar = trim(f.displayName1.value);
					var regExp = new RegExp("[!|@%#$^&*_:,;<>\\\\]");
					if (fieldChar.match(regExp)) {
				        if(f.dbColumnType.value == "combo" && dropdowntype[2].checked) {
							FCI.Messages(FCLang.STATE+"'s "+ FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
				        } else {
							FCI.Messages(FCLang.Title+" "+ FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
				        }   
						f.<%=BuilderFormFieldNames.DISPLAY_NAME%>1.focus();
						return false;
					}
				}
	        }
	      <% } %>
		} else {
	        if (isEmpty(trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value))) {
	        	FCI.Alerts(FCLang.DISPLAY_NAME);
	            f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
			    return false;
			}
		if (trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value) != "") {
				var firstChar = trim(f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.value).substring(0, 1);
				var regExp = new RegExp("[a-zA-Z]");
				if (!firstChar.match(regExp)) {
					FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
					f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
					return false;
				}
			}
       if (trim(f.displayName.value) != "") {
				var fieldChar = trim(f.displayName.value);
				var regExp = new RegExp("[!|@%#$^&*_:,;<>\\\\]");
				if (fieldChar.match(regExp)) {
				//	FCI.Messages(FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);  // CM_B_35380
					FCI.Messages(FCLang.PLEASE_REMOVE_THE_SPECIAL_CHARACTER);   
					f.<%=BuilderFormFieldNames.DISPLAY_NAME%>.focus();
					return false;
				}
			}
		}
		var dpname = trim(f.displayName.value);
		var dpname1 = trim(f.displayName1.value);

		f.displayName.value = dpname;
		f.displayName1.value = dpname1;

		var spacefix = / /gi;
		//var value=dpname.replace(spacefix,"");
		//var value1=dpname1.replace(spacefix,"");
		//var value=toTitleCase(dpname).replace(spacefix,"");
		//var value1=toTitleCase(dpname1).replace(spacefix,"");
		
		var value=toTitleCase(string_to_slug(trim(dpname))).replace(/[^a-zA-Z0-9_]/g, "");
		var value1=toTitleCase(string_to_slug(trim(dpname1))).replace(/[^a-zA-Z0-9_]/g, "");
		
		//var dbValue=replaceAllSpecialCharWith(trim(dpname),"").replace(/ +/g,"_");
		//var dbValue1=replaceAllSpecialCharWith(trim(dpname1),"").replace(/ +/g,"_");
		
		var dbValue=string_to_slug(trim(dpname)).replace(/ +/g,"_").replace(/[^a-zA-Z0-9_]/g, "");
		var dbValue1=string_to_slug(trim(dpname1)).replace(/ +/g,"_").replace(/[^a-zA-Z0-9_]/g, "");
		
		<%-- 
		var fldSname = "b" + '<%=sSection%>' + "_fld_" + value.toLowerCase();
		var fldDname = "B" + '<%=sSection%>' + "_FLD_" + value.toUpperCase();
		var mailmergekeyword = "<%=val%>"+"_"+value.toLowerCase();
		--%>
		var fldSname = "_"+revCapitalize(value);  	//value.toLowerCase();
		var fldDname = "_"+dbValue.toUpperCase();  	//value.toUpperCase();
		var mailmergekeyword = "<%=val%>"+"_"+value.toLowerCase();

		var fldSname1;
		var fldDname1;
		var mailmergekeyword1;
		var randNum;
		var randNum1;

		<% if(!sAction.equalsIgnoreCase("modify")){ %>
			randNum = "<%=IDGenerator.getNextKey()%>";
			mailmergekeyword = replaceSpecialChar(mailmergekeyword);
			/*//OTF-20161115-244 Starts	Udai Agarwal
			fldSname = replaceAllSpecialCharWith(fldSname,"").substring(0,53) + randNum;
			fldDname = replaceAllSpecialCharWith(fldDname,"").substring(0,53) + "_" + randNum.replace(/_+/g, '_');
			*/
			if(f.includeInCampaign && f.includeInCampaignCheck.checked)
			{
				fldSname = replaceAllSpecialCharWith(fldSname,"").substring(0,43) + randNum;
				fldDname = replaceAllSpecialCharWith(fldDname,"").substring(0,43) + "_" + randNum.replace(/_+/g, '_');
			}else{
				fldSname = replaceAllSpecialCharWith(fldSname,"").substring(0,53) + randNum;
				fldDname = replaceAllSpecialCharWith(fldDname,"").substring(0,53) + "_" + randNum.replace(/_+/g, '_');
			}
			//OTF-20161115-244 Ends
			
			f.mailmergekeyword.value = mailmergekeyword;
			f.<%=BuilderFormFieldNames.FIELD_NAME%>.value = fldSname;
			f.<%=BuilderFormFieldNames.DB_COLUMN_NAME%>.value = fldDname;
			if(f.dbColumnType.value == "combo" && dropdowntype[2].checked) {
				randNum1 = "<%=IDGenerator.getNextKey()%>";
				fldSname1 = "_"+revCapitalize(value1);		//value1.toLowerCase();
				fldDname1 = "_"+dbValue1.toUpperCase(); 	//value1.toUpperCase();
				mailmergekeyword1 = "<%=val%>"+"_"+value1.toLowerCase();
	
				mailmergekeyword1 = replaceSpecialChar(mailmergekeyword1);
				fldSname1 = replaceAllSpecialCharWith(fldSname1,"").substring(0,53) + randNum1;
				fldDname1 = replaceAllSpecialCharWith(fldDname1,"").substring(0,53) + "_" + randNum1.replace(/_+/g, '_');
				
			    f.mailmergekeyword1.value = mailmergekeyword1;
			    f.<%=BuilderFormFieldNames.FIELD_NAME%>1.value = fldSname1;
				f.<%=BuilderFormFieldNames.DB_COLUMN_NAME%>1.value = fldDname1;
			}

   <%}%>
   <%-- Bug 26878 Starts--%>
   	if(f.dbColumnType && f.dbColumnType.value == "combo" && dropdowntype[2].checked && trim(f.displayName.value) == trim(f.displayName1.value))//P_CM_B_61858
   	{
   		FCI.Messages(FCLang.COUNTRY_AND_STATE_DISPLAY_NAMES_CANNOT_BE_SAME);
   		return false;
   	}
   <%-- Bug 26878 Ends--%>
   	<%
	if(flds != null) {
		String fldName = "";
		String fldDispName = "";
		String keyWord = "";
		for(int i =0; i < flds.length; i++) {
			fldName = flds[i].getFieldName();
			fldDispName = flds[i].getDisplayName();
			fldName=fldName.replaceAll("\'","\\\\\'");
			fldDispName=fldDispName.replaceAll("\'","\\\\\'");
			keyWord = flds[i].getMailMergeKeyword();
			 if(!sAction.equalsIgnoreCase("modify")){%>
				if("$"+trim(mailmergekeyword)+"$" == "<%=keyWord%>"){
					f.mailmergekeyword.value = mailmergekeyword.substring(0,29)+randNum;
				}
			<%}%>
			
			if(checkDuplicateName(f.displayName.value) == '<%=fldDispName.trim()%>' || fldSname == '<%=fldName%>') {
				if(!(<%=StringUtil.isValid(sAction)%> && <%=sAction.equalsIgnoreCase("modify")%> && trim(f.preDisplayName.value) == '<%=fldDispName%>')){
					FCI.Messages(FCLang.FIELD_NAME_ALREADY_EXIST);
					return false;
				}
			}
			if(f.dbColumnType && f.dbColumnType.value == "combo" && <%=!sAction.equalsIgnoreCase("modify")%> && dropdowntype[2].checked) {//P_CM_B_61858
				<%if(!sAction.equalsIgnoreCase("modify")){%>
					if("$"+trim(mailmergekeyword1)+"$" == "<%=keyWord%>"){
						f.mailmergekeyword1.value = mailmergekeyword1.substring(0,29)+randNum1;
					}
				<%}%>
				if(trim(f.displayName1.value) == '<%=fldDispName%>' || fldSname1 == '<%=fldName%>') {
					FCI.Messages(FCLang.STATE_FIELD_ALREADY_EXIST);
					return false;
					
				}
			}
	<% 	}} %> 
		//alert("upper care "+value.toUpperCase());
		//alert("lower care "+value.toLowerCase());
		<%if(!isBuildFld && !isInputVal && !StringUtil.isValid(dropdownOpt) && !(checkForCM && "modify".equals(sAction) && "File".equals(fldType)) && !isPiiEnabled) { //P_CM_B_61858%>
       if(f.dbColumnType.value == "text") {
       	if(isEmpty(trim(f.noOfCols.value))){
       		FCI.Alerts(FCLang.NO_OF_COLUMNS);
               f.noOfCols.focus();
               return false;
       	}
       	var length=f.noOfCols.value;
       	if((length<= 0) || (length > 60) ){
       		FCI.Alerts(FCLang.NO_OF_COLUMNS+" "+FCLang.BETWEEN+" 1 "+FCLang.AND+" 60");
       		f.noOfCols.focus();
               return false;
       	}
       	if(isEmpty(trim(f.noOfRows.value))){
       		FCI.Alerts(FCLang.NO_OF_ROWS);
               f.noOfRows.focus();
               return false;
       	}
       	var length=f.noOfRows.value;
       	if((length<= 0) || (length > 5) ){
       		FCI.Alerts(FCLang.NO_OF_ROWS+" "+FCLang.BETWEEN+" 1 "+FCLang.AND+" 5");
       		f.noOfRows.focus();
               return false;
       	}
       }
       
       if(f.dbColumnType.value == "varchar" || f.dbColumnType.value == "int") {
       	if(isEmpty(trim(f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.value))){
       		FCI.Alerts(FCLang.MAXIMUM_LENGTH);
               f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
               return false;
       	}
       	var fldVtype = f.fldValidationType.value;
       	var length=f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.value;
       	if(f.dbColumnType.value == "varchar" && (fldVtype == "None" || fldVtype == "Phone") && (length<= 0 || length > 255) ){
       		FCI.Alerts(FCLang.THE_LENGTH+" "+FCLang.BETWEEN+" 1 "+FCLang.AND+" 255");
       		f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
               return false;
       	}
       	if(f.dbColumnType.value == "varchar" && fldVtype == "Email" && (length<= 2 || length > 255) ){
       		FCI.Alerts(FCLang.THE_LENGTH+" "+FCLang.BETWEEN+" 3 "+FCLang.AND+" 255");
       		f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
               return false;
       	}
       	if(f.dbColumnType.value == "int" && (length<= 0 || length > 10) ){
       		FCI.Alerts(FCLang.THE_LENGTH+" "+FCLang.BETWEEN+" 1 "+FCLang.AND+" 10");
       		f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
               return false;
       	}
           if(f.dbColumnType.value == "") {
           	if(isEmpty(trim(f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.value))){
           		FCI.Alerts(FCLang.MINIMUM_LENGTH);
                   f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
                   return false;
           	}
           }
       }
          
       var flag = true;
       var exportableFields = document.getElementsByName("exportable");
       //var searchableFields = document.getElementsByName("searchable");
       //var reportableFields = document.getElementsByName("reportable");
       //if (exportableFields[1].checked) {
           //exportableFields[0].value = "1";
           //searchableFields[0].value = "1";
           //reportableFields[0].value = "1";
       //} else {
           //exportableFields[0].value = "0";
           //searchableFields[0].value = "0";
           //reportableFields[0].value = "0";
       //}
       <% } %>
       <%--SMC-20140213-378 starts--%>
       <%if(!(checkForCM && "modify".equals(sAction) && "File".equals(fldType))){//P_CM_B_61858%>
       var type = f.dbColumnType.value;
       if(f.dbColumnType.value == "String"){
			type = f.displayType.value;				
       }
       var addDropDownOpt = false;
     	
		if('modify'==sActionScript && <%=showOptDivOnModify%>){
			addDropDownOpt = true;
		}else if('add'==sActionScript && f.dbColumnType.value == "combo"){
			var dropdowntype = document.fieldModify.dropdownoption;
			if(dropdowntype[0].checked) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
				addDropDownOpt = dropdowntype[0].checked;
			} else if(dropdowntype[3].checked) {
				addDropDownOpt = dropdowntype[3].checked;
			}
		}else{
			addDropDownOpt = false;
		}
		
       
		if(((type == "combo" || type == "multiselect") && document.fieldModify.dropdownoption && document.fieldModify.dropdownoption[3].checked) || (type == "radio" && document.fieldModify.radioOption && document.fieldModify.radioOption[1].checked) || (type == "checkbox" && document.fieldModify.checkboxOption && document.fieldModify.checkboxOption[1].checked)) {
			if(document.getElementById("dependentField").value == "-1") {
				//alert("Please select Parent Field.");
				alert(FCLang.PLEASE_SELECT_PARENT_FIELD);
				document.getElementById("dependentField").focus();
				return false;
			}
		} //BB-20150203-259 (Dynamic Response based on parent field response) ends
       if((type == "combo" && addDropDownOpt) || type == "radio" || type == "checkbox" || type == "multiselect"){
   		var optionName = $("*[name='optionNameTemp']");
   		for(var i = 1;i<=optionName.length;i++){
   			if(trim(optionName[i-1].value) == "") {
   				FCI.Alerts(FCLang.OPTION+" "+i);
   				optionName[i-1].focus();
   				return false;
   			}
   		}
   		addToForm();
   	}
		
     //P_Enh_Sync_Fields starts
		if(type == "existing" || (document.fieldModify.syncField && document.fieldModify.syncField.checked)) {
			
			if(document.getElementById("fieldNamesForTabNew") && document.getElementById("fieldNamesForTabNew").value == '-1') { //for blank Duplicate Field.
				FCI.Messages(FCLang.PLEASE_SELECT_SYNC_FIELD);
				return false;
			}
			
			if(document.getElementById("fieldNamesForTabNew") && document.getElementById("fieldNamesForTabNew").value != "-1") {
			var addFieldFlag = "";
				$.ajax({
			    	type: "POST",
			        url: "handleAjaxRequest", 
			        data: "dataset=checkForExistingField&existingField="+document.getElementById("fieldNamesForTabNew").value+"&tableAnchor=<%=val%>",
			        async: false,
			        success:function(data) {
			        	addFieldFlag = data.trim();      
			        }
			    });				
	
				if(addFieldFlag == "no") {
					//alert("Field cannot be added, as the Sync field selected is associated with some other field in the same tab.");
					FCI.Messages(FCLang.FIELD_CANNOT_BE_ADDED_AS_A_SYNC_FIELD_SELECTED_IS_ASSOCIATED_WITH_SOME_OTHER_FIELD_IN_THE_SAME_TAB);
					return false;
				}
			
				var dependentFlag = "";
				$.ajax({
			    	type: "POST",
			        url: "handleAjaxRequest", 
			        data: "dataset=checkForParentFieldExisting&existingField="+document.getElementById("fieldNamesForTabNew").value,
			        async: false,
			        success:function(data) {
			        	dependentFlag = data.trim();      
			        }
			    });
				
				if(dependentFlag == "yes") {
					//if(confirm("This field will be created as a read only field as the sync field is dependent field, also this can't be exported. Do you want to continue?")) {		//Sync Fields in FS Form Generator
					if(FCI.Confirms(FCLang.THIS_FIELD_WILL_BE_CREATED_AS_A_READ_ONLY_FIELD_AS_A_SYNC_FIELD_IS_DEPENDENT_FIELD_ALSO_THIS_CANT_BE_EXPORTED_DO_YOU_WANT_TO_CONTINUE+"?")){
						var input = document.createElement('input');
						input.setAttribute('type', 'hidden');
						input.setAttribute('name', 'canReadOnly');
						input.setAttribute('value', "yes");
						if(document.getElementsByName("exportable")[1]){//bug 80132
							document.getElementsByName("exportable")[1].checked = true;				//Sync Fields in FS Form Generator
						}
						document.fieldModify.appendChild(input);
					} else {
						return false;
					}
				}
			}
			
			if(document.fieldModify.syncField && document.fieldModify.syncField.checked) {
				if(confirm("Data present inside the field may be lost. Do you want to continue?")) {
					//do nothing
				} else {
					return false;
				}
			}
			
			if(document.getElementById("fieldNamesForTabNew")) {
				document.fieldModify.fieldNamesForTab.value = document.getElementById("fieldNamesForTabNew").value;
			}
		}
		//P_Enh_Sync_Fields ends
   	if(f.dbColumnType.value == "String"){
			f.dbColumnType.value = f.displayType.value;				
       }<%--SMC-20140213-378 ends--%>
       <%}//P_CM_B_61858%>
     //BB-20150319-268(FIM Campaign Center) starts
       <% if("3".equals(moduleId) && !"2".equals(sFormID) && !checkForContactHistory)
       {%>
       	if(f.includeInCampaign) {
          	 if(f.includeInCampaignCheck.checked) {
              	if(f.fldValidationType.value == 'Email')
              	{
               	f.includeInCampaign.value = "0";
              	}else 
              	{
              		f.includeInCampaign.value = "1";
              	}
           } else {
               f.includeInCampaign.value = "1";
           }
       } 
           //else {
           //if(f.includeInCampaign) {
            // f.includeInCampaign.value = "1";
           //}
      // }
       <%}%>
       //BB-20150319-268(FIM Campaign Center) ends
       f.submitButton.disabled=true;
       f.submit();
   }


resizeWindow();//resize window to remove scrolling when field is modify.
</script>
<%-- FB_DATATYPE_JS Ends--%>
</body>
</html>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>
