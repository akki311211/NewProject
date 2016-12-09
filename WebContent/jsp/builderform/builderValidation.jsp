<%@page import="com.appnetix.app.util.sqlqueries.SQLUtil"%>
<%@ page import="com.appnetix.app.util.Constants"%>
<%@ page import="com.appnetix.app.util.SequenceMap"%>
<%@ page import="com.appnetix.app.util.database.*" %>
<%@ page import="com.appnetix.app.util.information.Info" %>
<%@ page import="com.appnetix.app.util.CommonUtil"%>
<%@ page import="com.appnetix.app.util.ModuleUtil"%>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import="com.appnetix.app.util.*" %>
<%@ page import = "java.util.Iterator"%>
<%--
----------------------------------------------------------------------------------------------------------
Version No.		Date						By			Against		Function Changed	Comments
----------------------------------------------------------------------------------------------------------- 
PW_B_26146		10 August 2013			Anjali Pundir			basebuild>fim>any new filed added from form generator>validate that field with phone/email>the alert message is not proper.
CM_B_35373      14 march 2014           chetan rawat              the alert message is not proper.
P_CM_B_42150    22 july 2014            swati garg              save button of modify page not working
-----------------------------------------------------------------------------------------------------------
--%>


<%
	String path = "/static" + Constants.STATIC_KEY
			+ "/javascript/string.js";
%>
<jsp:include page="<%=path%>" />
<%
	String path1 = "/static" + Constants.STATIC_KEY
			+ "/javascript/phone.js";
%>
<jsp:include page="<%=path1%>" />
<%
	String path2 = "/static" + Constants.STATIC_KEY
			+ "/javascript/email.js";
%>
<jsp:include page="<%=path2%>" />
<%
	String path3 = "/static" + Constants.STATIC_KEY
			+ "/javascript/integer.js";
%>
<jsp:include page="<%=path3%>" />
<%
	String path4 = "/static" + Constants.STATIC_KEY
			+ "/javascript/double.js";
%>
<jsp:include page="<%=path4%>" />

<script language="javascript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/progressBar.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkDouble.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumber.js"></script>
	<%=LanguageUtil.getLanguageContent(LanguageUtil.getUserLangForJs((String)session.getAttribute("userLanguage")),(String)session.getAttribute("menuName"),MultiTenancyUtil.getTenantName()) %>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/progressBar.js"> </script><%--P_CM_B_73522 --%>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/StringUtil.js"></script><%--P_CM_B_73522 --%>
<script language="javascript">
var theForms="";
var forAgreement="false";
var submitform=true;//ZCUB-20140226-004
if(null!=document.getElementById("triggerFormName") && document.getElementById("triggerFormName").value=="Agreement"){
    theForms= document.getElementById("triggerFormName").value;  
    forAgreement="Y";
}
<%if (mappings != null) {
				String[] fieldsToBeValidated = null;
				HeaderMap[] hMap = mappings.getHeaderMap();
				boolean childFlag = false;
				SequenceMap headerMap = new SequenceMap();
				Info headerInfo = new Info();
				for (int ii = 0; ii < hMap.length; ii++) {
					headerInfo = new Info();
					HeaderMap h1 = hMap[ii];
					HeaderField hFld1 = h1.getHeaderFields();
					DependentTable[] dTablesTemp = hFld1.getDependentTables();
					if (!childFlag) {
						headerInfo.set("hField", hFld1);
						headerInfo.set("fMapping", mappings);
						headerInfo.set("hDisplayName", h1.getValue());
						headerInfo.set("tableAlias", "");
						headerMap.put(h1.getName(), headerInfo);
					}
				}
			
				if (headerMap != null) {%>	
		var k;
		var j;
		var d;
		var ct;

		function fimValidation(formName) {
			var f = eval("document."+formName);
			var flag	= true;
			var flagForPercentage	= true;
			var fields = new Array();
			var i = 0;
			if(f.entityFranchiseeCombo  && f.entityFranchiseeCombo.value == "-1") {
				alert(FCLang.PLEASE_SELECT_A_FRANCHISEE);
				f.entityFranchiseeCombo.focus();
				return false;
			}
			<%
					Iterator hMapItr = headerMap.keys().iterator();
					while (hMapItr.hasNext()) {
						String headerName = (String) hMapItr.next();
						Info hInfo = (Info) headerMap.get(headerName);
						HeaderField hFld = (HeaderField) hInfo
								.getObject("hField");
						FieldMappings mappings1 = (FieldMappings) hInfo
								.getObject("fMapping");
						String tableAlias = (String) hInfo.get("tableAlias");
						if ("additionalFranchiseLocationDetails".equals(hFld
								.getHeaderAttrMap().get("name"))) {
							continue;
						}
						Field[] flds = mappings1
								.getSectionTableFieldsWithOrderBy(hFld
										.getSectionField());
						if (flds == null) {
							continue;
						}
						
						for (int j = 0; j < flds.length; j++) {
							boolean isBuildfld = flds[j].isBuildField();
							boolean isMnadatFld = flds[j].isMandatoryField();
							String readOnly = flds[j].getReadOnly();
							String isMultiSelect=flds[j].getisMultiSelect();//Alleg-20160615-349
							String displayName = flds[j].getDisplayName();
							String jsValidationMethod=flds[j].getJsValidationMethod();
							//displayName=displayName.replaceAll("\'","\\\\\'");
							String tempName = tableAlias
									+ flds[j].getFieldName();
							String valType = flds[j].getValidationType();
							int colLength = flds[j].getDBFieldLength();
							String syncField = flds[j].getSyncWithField();
							%>
							
					var fieldMandat = '<%=isMnadatFld%>';
					var fieldName = '<%=tempName%>';
					<%--var fieldDisplayName = '<%=LanguageUtil.getString(displayName)%>';--%>
					var fieldDisplayName = "<%=StringUtil.encodeEscapeCharacter(
									StringUtil.encodeEscapeCharacter(
											LanguageUtil.getString(displayName),
											'\''), '\"')%>";
					var fieldValType = '<%=valType%>';
					var displayTypeField = '<%=flds[j].getDisplayTypeField()%>';
					var isBuildfldChk = '<%=isBuildfld%>';
					var dbBuildfldLength = '<%=colLength%>';
					var syncField = '<%=syncField%>';
					var readOnly='<%=readOnly%>';
					var isMultiSelect='<%=isMultiSelect%>';   //Alleg-20160615-349
					var fieldElement = eval("document."+formName+"."+fieldName);
					if(fieldElement && !fieldElement.disabled){
						
						
					<%if(StringUtil.isValid(jsValidationMethod)){ %> 
						<%=CommonUtil.getJSValidationMethod(flds[j],jsValidationMethod)%>
					<%}else{ %> 
						
						if(fieldMandat == 'true') {
							var fldenbld = eval("document."+formName+"."+fieldName);
							if(fldenbld.disabled == true) {
								// No need to check mandatory case
							} else {
								flag = validateRequired(eval("document."+formName+"."+fieldName));
								if (!flag){     
									//P_CM_B_42150
									var fields = fieldDisplayName + " " +   FCLang.IS_MANDATORY;
							    	focusField = document.getElementById(fieldName);
					                alert(fields);
					                focusField.focus();
				
					     		    return false;
								}
							}
						}
						if(displayTypeField == 'Text' && isBuildfldChk != null && isBuildfldChk == "false") {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								var colval = document.getElementById(fieldName).value;
								if(parseInt(dbBuildfldLength) < colval.length) {
									focusField = document.getElementById(fieldName);
                                    alert(FCLang.INPUT_LENGTH_CANT_BE_EXCEED_THAN+" "+dbBuildfldLength);	<%--FB_VALID_ALERT_20062013--%>
									focusField.focus();
									return false;
								}
							}
						}
						if(fieldValType == 'Phone') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=trim(document.getElementById(fieldName).value);
								var flag="true";
								flag=validatePhone(document.getElementById(fieldName).value);
								if(!flag){
									<%--	var fields = fieldDisplayName + FCLangFIM.IS_NOT_VALID + " Please input valid phone data for "+fieldDisplayName;PW_B_26146--%>
                                    var fields = FCLang.PLEASE_ENTER_VALID_PHONE_DATA_FOR_THE_FIELD+" "+'\"'+fieldDisplayName+'\"';<%-- CM_B_35373   --%>
								    
									focusField = document.getElementById(fieldName);
						    		alert(fields);
						    		focusField.focus();
						    		return false;
								}
							}	
						}
						if(fieldValType == 'Email') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=removeSpaces(document.getElementById(fieldName).value); //Martin-20160916-029 
								var flag2="true";
								flag2=commaSemicolnSepratedEmail(document.getElementById(fieldName).value);
								flag	= validateMail(document.getElementById(fieldName).value);
								if(!flag2){
								<%-- 	var fields = fieldDisplayName + FCLangFIM.IS_NOT_VALID + " Please input valid Email data for "+fieldDisplayName; PW_B_26146--%>
                                    var fields = FCLang.PLEASE_ENTER_VALID_EMAIL_DATA_FOR_THE_FIELD+" "+'\"'+fieldDisplayName+'\"'+FCLang.FULLSTOP;<%--  CM_B_35373  --%>
							    	
									focusField = document.getElementById(fieldName);
						    		alert(fields);
						    		focusField.focus();
						    		return false;
								}
							}	
						}
						<%--P_ENH_FORMBUILDER_URL_VALIDATION Starts--%> 
						<%--BB-20150203-259 Starts (Url validation for Text display type of field.)--%> 
						if(fieldValType == 'Url') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=trim(document.getElementById(fieldName).value);
								var flag="true";
								
								var urlValue = document.getElementById(fieldName).value;
								if(!urlValue.indexOf("http://") == 0 && !urlValue.indexOf("https://") == 0) {
									FCI.Messages(FCLang.THE_TARGET_URL_SHOULD_START_WITH_HTTP_OR_HTTPS_FOR_THE_FIELD+" \""+fieldDisplayName+"\"");
									document.getElementById(fieldName).focus();
									return false;
								}
								
								flag=validateUrl(document.getElementById(fieldName).value);
								if(!flag){
									//var fields = 'Please enter valid Url data for the field  '+'\"'+fieldDisplayName+'\"';   
									focusField = document.getElementById(fieldName);
									FCI.Alerts(FCLang.VALID_URL_DATA_FOR_THE_FIELD+" "+"\""+fieldDisplayName+"\"");
						    		//alert(fields);
						    		focusField.focus();
						    		return false;
								}
							}	
						}
						<%--BB-20150203-259 Ends (Url validation for Text display type of field.)--%> 
						if(fieldValType == 'Percentage') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=trim(document.getElementById(fieldName).value);
								var flag="true";
								flag=validatePercentage(document.getElementById(fieldName).value);
								if(!flag){
                                    var fields = fieldDisplayName +" "+ FCLang.HAS_TO_BE_NUMERICAL_VALUE_LESS_THAN_500;
						    		focusField = document.getElementById(fieldName);
						    		alert(fields);
						    		focusField.focus();
						    		return false;
								}
							}	
						}
						
						if(fieldValType == 'Integer') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=trim(document.getElementById(fieldName).value);
								var flag="true";
								flag=validateInteger(document.getElementById(fieldName).value);
								if(!flag){
                                    var fields = fieldDisplayName +" "+ FCLang.HAS_TO_BE_INTEGER_VALUE;
						    		focusField = document.getElementById(fieldName);
						    		alert(fields);
						    		focusField.focus();
						    		return false;
								}
								if(isBuildfldChk != null && isBuildfldChk == "false") {
									flag = validIntegerValue(document.getElementById(fieldName).value);
									if(!flag){
                                        var fields = fieldDisplayName+"'"+FCLang.S_VALUE_SHOULD_BE_LESS_OR_EQUAL_TO+" "+"2147483647.";	<%--FB_VALID_ALERT_20062013--%>
							    		focusField = document.getElementById(fieldName);
							    		alert(fields);
							    		focusField.focus();
							    		return false;
									}
								}
							}	
						}
						if(fieldValType == 'Double') {
							if(document.getElementById(fieldName) != null && !isEmpty(trim(document.getElementById(fieldName).value))) {
								document.getElementById(fieldName).value=trim(document.getElementById(fieldName).value);
								var flag="true";
								flag=validateFloat(document.getElementById(fieldName).value);
								if(!flag){
									var fields = fieldDisplayName +" "+FCLang.HAS_TO_BE_NUMERICAL_VALUE;
						    		focusField = document.getElementById(fieldName);
						    		alert(fields);
						    		focusField.focus();
						    		return false;
								}
							}	
						}
						<%} %>	
					}
					if((displayTypeField == 'Checkbox'||displayTypeField == 'Radio') && isBuildfldChk != null && isBuildfldChk == "false")
					{
						var total=0;
						var chkBox= eval("document."+formName+"."+fieldName);
						//CHECKBOX_NO_DATA (updating field data with blank if no checkbox is checked.)
						if(chkBox) {
							//FORM_BUILDER_ISSUE_1_SINGLE_CHECKBOX starts
							if(chkBox.value && chkBox.checked)
							{
								total = 1;
							}
							else
							{
							//FORM_BUILDER_ISSUE_1_SINGLE_CHECKBOX ends
								for(var i=0; i < chkBox.length; i++){
									if(chkBox[i].checked)
										   total++;
								}
							}//FORM_BUILDER_ISSUE_1_SINGLE_CHECKBOX
							if(total == 0 && readOnly!="yes")
							{
								var input = document.createElement("input");
								input.setAttribute("type", "hidden");
								input.setAttribute("name", fieldName);
								input.setAttribute("value", "");
								f.appendChild(input);
							}
						}
					}
					//Alleg-20160615-349 starts
					if(displayTypeField == 'Combo' && isMultiSelect=='true'){
						var combo= eval("document."+formName+"."+fieldName);
						var allCheckedItems="";
						if(combo) {
							allCheckedItems = $('input[id=selectItem'+fieldName+'][type="checkbox"]:checked').map(function() {
								return this.value;
							}).get().join(',');
							if(allCheckedItems==""){
								var input = document.createElement("input");
								input.setAttribute("type", "hidden");
								input.setAttribute("name", fieldName);
								input.setAttribute("value", "");
								f.appendChild(input);
							}
						}
					}//Alleg-20160615-349 ends
					<%if(ModuleUtil.fimImplemented()) {%>
					if(syncField == 'fimEntityDetail##fimTtEntityName##false') { //check for duplicate validation
						<%
							String entityTabFieldId = SQLUtil.getColumnValue("FIM_ENTITY_DETAIL", "FIM_ENTITY_ID", "ENTITY_ID", (String)session.getAttribute("fimFranchiseeNo"));
							String entityTabName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", "TABLE_ANCHOR", "fimEntityDetail");
							String entityDisplayName = "Entity Name";
							FieldMappings entityFieldMappings = DBUtil.getInstance().getFieldMappings("fimEntityDetail");
							if(entityFieldMappings != null) {
								Field entityField = entityFieldMappings.getField("fimTtEntityName");
								if(entityField != null) {
									entityDisplayName = entityField.getDisplayName();
								}
								entityFieldMappings = null;
								entityField = null;
							}
						%>
						var entityID = "<%=entityTabFieldId%>";
						var entityName = trim(document.getElementById(fieldName).value);
						if(entityID != null || entityID != "" || entityID != "null") {
							var colArray = new Array(2);
							colArray[0] = "FIM_TT_ENTITY_NAME";
							colArray[1] = "FIM_ENTITY_ID!";
							var valArray = new Array(2)
							var valArray = new Array(1)
							var regex = new RegExp('&', 'g');
							entityName = entityName.replace(regex, "#####");
							var regex1 = new RegExp('%', 'g'); 
							entityName = entityName.replace(regex1, "^^^**^");
							valArray[0] = entityName;
							valArray[1] = entityID;
							if (colArray.length = valArray.length) {
								checkDuplicate4("FIM_ENTITY_DETAIL", colArray, valArray);
							}
						} else {
							checkDuplicate("FIM_ENTITY_DETAIL",'FIM_TT_ENTITY_NAME', trim(document.getElementById(fieldName).value));
						}
						
						if(duplicateflag)
						{
							FCI.Messages("This is a sync field with <%=entityTabName%> > <%=entityDisplayName%> and an Entity with this name already exists in the system"); 
							
							document.getElementById(fieldName).value="";
							document.getElementById(fieldName).focus();
							return false;
						}
					}
			<%}
						}
					}%>
			/*
			for (var count = 0; count < fieldMandat.length; count++) {
				if(fieldMandat[count] == 'true') {
					flag = validateRequired(document.getElementById(fieldName[count]));
					if (!flag){     
						fields[i] = fieldDisplayName[count] +   FCLangFIM.IS_MANDATORY;
				    	focusField = document.getElementById(fieldName[count]);
		                focusField.focus();
		     		    alert(fields[0]);

		     		    return false;
					}
				}
			}*/
			 if(f.entityFranchiseeCombo) {
				 var eles = $(":input[name$='entityID']");
				 $.each( eles, function(index, inputElement) {
					 inputElement.value = document.getElementById("entityFranchiseeCombo").value;
					 });
				 if(f.entityID) {
					f.entityID.value = document.getElementById("entityFranchiseeCombo").value;
				 }
				 if(f.franchiseeNo) {
					 f.franchiseeNo.value = document.getElementById("entityFranchiseeCombo").value; 
				 }
				 if(f.owners_0franchiseeNo) {
					 f.owners_0franchiseeNo.value = document.getElementById("entityFranchiseeCombo").value;
				 }
				 if(f.fimEmployeesMapping_0franchiseeNo) {
					 f.fimEmployeesMapping_0franchiseeNo.value = document.getElementById("entityFranchiseeCombo").value;
				 }
			} 
			
			
			if (checkForProgressBar(f))
				showTransparentProgressBar();
			if("taskForm" == formName)
			{
				f.add.disabled=true;
			}
			
			if(submitform) {//ZCUB-20140226-004
				if(f.Submit){
					f.Submit.disabled = true;
				}
				if(f.Transfer){
					f.Transfer.disabled = true;
				}
				
				if(formName=="taskForm")
				{
					return true;
				}
				else
					{
				
				f.submit();
					}
			}//ends
			return true;
		}

<%} else {%>
		function fimValidation(formName) {
			var f = eval("document."+formName);
			if (checkForProgressBar(f))
				showTransparentProgressBar();
			if(f.Submit){
				f.Submit.disabled = true;
			}
			if(f.Transfer){
				f.Transfer.disabled = true;
			}
			f.submit();
			return true;
		}

<%}
			} else {%>
	function fimValidation(formName) {
		var f = eval("document."+formName);
		if (checkForProgressBar(f)){
			showTransparentProgressBar();
		} 
	
		if(f.Submit){
			f.Submit.disabled = true;
		}
		if(f.Transfer){
			f.Transfer.disabled = true;
		}
		f.submit();
		return true;
	}
<%}%>

function checkForProgressBar(f) {
	var showProBar = false;
	if (f.<%=FieldNames.SAVE_DIRECTORY%> && f.<%=FieldNames.SAVE_DIRECTORY%>.value != ''){
		var len		= f.elements.length;
		for(i=0;i<len;i++){
			if(f.elements[i].type=='file' && trim(f.elements[i].value) != ''){
				showProBar = true;
				break;
			}
		}
	}
	return showProBar;
}

function validateRequired(field) {
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	if(field!=null)	{
		if (field.type == 'text' || field.type == 'textarea' || field.type == 'file' || field.type == 'select-one' || field.type == 'password' || field.type == 'checkbox' || field.type == 'radio') {
			var value = '';
			if (field.type == "select-one") {
				var si = field.selectedIndex;
				if (si >= 0) {
					value = field.options[si].value;
				}
			} else if(field.type == "file") {
				if(document.getElementById(field.id+"_document")) { //in case when document exists				
					value = document.getElementById(field.id+"_document").value;
				} else {
					value = field.value;
				}
			} else {
				value = field.value;
			}
			if (trim(value).length == 0 || (field.type == 'select-one' && trim(value) == '-1')) {
				isValid = false;
			}else if(field.type == 'checkbox' || field.type == 'radio'){
				isValid = field.checked;
			}
		} else if(field.type == 'select-multiple') {
        	if (field.selectedIndex == 0) {
           		isValid = false;
        	}
        	else {
				for (var i=1; i<field.length; i++) {
					if(field.options[i].selected) {
						isValid = true;
						break;
					} else {
						isValid = false;
					}
				}
        	}
		} else if (field.length != undefined) {
			var chkd = false;
			for (var k=0; k < field.length; k++) {
				if (field[k].checked)
					chkd = true;
			}
			if (!chkd) {
				isValid = false;
			}
		}
	}
	return isValid;
}

function deleteDoc(DocumentID,DocumentName)
{
   var f  =document.deleteDocForm;
    if( FCI.Confirms(FCLang.DO_YOU_WANT_TO_DELETE_THIS_DOCUMENT))
	{
	   if(f.documentsID)
	   	f.documentsID.value= DocumentID;
    	   if(f.documentID)
	   	f.documentID.value= DocumentID;
	   f.DocumentName.value= DocumentName;
	   f.submit();
	}

}

//P_FORM_BUILDER_B_35397 STARTS
function checkNumberAlert(event,isAlert){
    if(!checkNumber(event)){
        alert(FCLang.PLEASE_ENTER_A_WHOLE_NUMBER_EX+": 0, 1, 2, 3, ...");
		return false;
	}
	return true;
}
function checkDoubleNewAlert(vNumericName, vNumericValue,value,event){
	if(checkDoubleNew(vNumericName, vNumericValue,value,event)==false){
        FCI.Alerts(FCLang.A_NUMERIC_VALUE);
		return false;
	}
	return true;
	
}
function fun_AllowOnlyNumberAndDotAlert(vNumericName, vNumericValue,value,event){
	if(fun_AllowOnlyNumberAndDot(vNumericName, vNumericValue,value,event)==false){
        FCI.Alerts(FCLang.A_NUMERIC_VALUE);
		return false;
	}
	return true;
	
}
//P_FORM_BUILDER_B_35397 ENDS
<%--BB-20150203-259 Starts (Url validation for Text display type of field.)--%> 
function validateUrl(urlToValidate)
{
	var myRegExp =/^(?:(?:https?|ftp):\/\/)(?:\S+(?::\S*)?@)?(?:(?!10(?:\.\d{1,3}){3})(?!127(?:\.\d{1,3}){3})(?!169\.254(?:\.\d{1,3}){2})(?!192\.168(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]+-?)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]+-?)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/[^\s]*)?$/i;
	if (!myRegExp.test(urlToValidate)){
	return false;
	}else{
	return true;
	}
}
<%--BB-20150203-259 Ends (Url validation for Text display type of field.)--%> 
</script>
