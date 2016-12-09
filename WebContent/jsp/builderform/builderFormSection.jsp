<%--
 - $Author						:  $Abhishek gupta
 - $Version						:  $1.1
 - $Date						:  $Mar 12,2011
 P_FS_Enh_BuilderForm		16Dec2013		Naman Jain			Form Builder in Franchise Sales
 
 --%>

<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "com.home.builderforms.forms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "com.home.builderforms.*"%>
<%@ include file = "/SessionManager.jsp"%>
<%@ include file="/javascript/string.js"%>
<%@ include file="/javascript/builderCss.jsp"%>

<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>

<jsp:useBean id="builderForm"	class="com.home.builderforms.BuilderFormWebImpl" scope="session"/>
<jsp:useBean id="builderWebForm"	class="com.home.builderforms.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>
	
<%
try {
String moduleId = request.getParameter("moduleId");//P_FS_Enh_BuilderForm
String sAction = request.getParameter("action");
String sFormID = request.getParameter("formID");
String sHtmlType = request.getParameter("dataType");
String sFieldNo = request.getParameter("fieldID");
String sSection = request.getParameter("section");
String checkValue = request.getParameter("checkValue");
String formButtonTag = "";
String isBuildField = request.getParameter(BuilderFormFieldNames.IS_BUILD_FIELD);
boolean isBuildFld = Boolean.valueOf(isBuildField);

boolean isInputVal =Boolean.valueOf(checkValue); 
String val = null;
val = request.getParameter("tableName");
if(val == null) {
	val = (String)request.getAttribute("tableName");
}
System.out.println(" val    : " + val);

%>
<script language ="javaScript">
    function hideLayer(whichLayer) {
        self.resizeTo(630,400);
        if (document.getElementById) {
			document.getElementById(whichLayer).style.display = "none";
		} else if (document.all) {
			document.all[whichlayer].style.display = "none";
		} else if (document.layers) {
			document.layers[whichLayer].display = "none";
		}
	}

    function showLayer(whichLayer) {
        self.resizeTo(630,400);
        if (document.getElementById) {
			document.getElementById(whichLayer).style.display = "block";
		} else if (document.all) {
			document.all[whichlayer].style.display = "block";
		} else if (document.layers) {
			document.layers[whichLayer].display = "block";
		}
	}

    function selectDiv() {
		var div = document.fieldModify.divSelect.value;
		var type = document.fieldModify.dbColumnType.value;
        if (type == "text") {
			//if (div == "show") {
			  //  document.fieldModify.divSelect.value="hide";
			  //  hideLayer("textAreaSize");
			//} else {
				//document.fieldModify.divSelect.value = "show";
				showLayer("textAreaSize");
				hideLayer("textNumber");
			//}
		} else if (type == "varchar" || type == "int") {
		    document.fieldModify.divSelect.value="hide";
			hideLayer("textAreaSize");
			showLayer("textNumber");
	   } else {
		   //document.fieldModify.divSelect.value="hide";
		   hideLayer("textAreaSize");
		   hideLayer("textNumber");
	   }
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
    
	function validate() {
		if(showProcessingAlert() == 'true') {
			return false;		
		}

		var f = document.fieldModify;
        if (isEmpty(trim(f.displayName.value))) {
        	FCI.Alerts(FCLang.DISPLAY_NAME);
            f.displayName.focus();
		    return false;
		}
		if (trim(f.displayName.value) != "") {
			var firstChar = trim(f.displayName.value).substring(0, 1);
			var regExp = new RegExp("[a-zA-Z]");
			if (!firstChar.match(regExp)) {
				FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
				f.displayName.focus();
				return false;
			}
		}
        if (trim(f.displayName.value) != "") {
			var fieldChar = trim(f.displayName.value);
			var regExp = new RegExp("[!@#$%^&*]");
			if (fieldChar.match(regExp)) {
				FCI.Messages(FCLang.DISPLAY_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
				f.displayName.focus();
				return false;
			}
		}

		<% if(!isBuildFld && !isInputVal) { %>
        if(f.dbColumnType.value == "text") {
        	if(isEmpty(trim(f.noOfCols.value))){
        		alert("Please enter Maximum Length.");
                f.noOfCols.focus();
                return false;
        	}
        	var length=f.noOfCols.value;
        	if((length<= 0) || (length > 60) ){
        		alert("Please enter the length between 1 and 60.");
        		f.noOfCols.focus();
                return false;
        	}
        	if(isEmpty(trim(f.noOfRows.value))){
        		alert("Please enter Maximum Length.");
                f.noOfRows.focus();
                return false;
        	}
        	var length=f.noOfRows.value;
        	if((length<= 0) || (length > 5) ){
        		alert("Please enter the length between 1 and 5.");
        		f.noOfRows.focus();
                return false;
        	}
        }
        if(f.dbColumnType.value == "varchar" || f.dbColumnType.value == "int") {
        	if(isEmpty(trim(f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.value))){
        		alert("Please enter Maximum Length.");
                f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
                return false;
        	}
        	var length=f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.value;
        	if((length<= 0) || (length > 60) ){
        		alert("Please enter the length between 1 and 60.");
        		f.<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>.focus();
                return false;
        	}
        }
           
        var flag = true;
        
        var exportableFields = document.getElementsByName("exportable");
        //var searchableFields = document.getElementsByName("searchable");
        //var reportableFields = document.getElementsByName("reportable");
        if (exportableFields[1].checked) {
            exportableFields[0].value = "1";
            //searchableFields[0].value = "1";
            //reportableFields[0].value = "1";
        } else {
            exportableFields[0].value = "0";
            //searchableFields[0].value = "0";
            //reportableFields[0].value = "0";
        }
        <% } %>
        
        f.submitButton.disabled=true;
        f.submit();
    }
</script>
<html>
	<body topmargin="0" leftmargin="0">
		<title><%=LanguageUtil.getString("Field Configuration",(String)session.getAttribute("userLanguage"))%></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td colspan="2" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="18%" valign="bottom">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="9" class="crvBox2-ltBg"></td>
                <td  nowrap="nowrap" class="crvBox2-ttBg"><%=LanguageUtil.getString("FIM Form",(String)session.getAttribute("userLanguage"),Constants.KEYVAL_FIM)%></td>
                <td width="33" class="crvBox2-rtBg"></td>
              </tr>
            </table></td>
            
	 
	 	<td width="59%" align="right" class="bText11gr" style="padding-right:15px">Fields marked with <span class="urgent_fields">*</span> are mandatory.</td>
          </tr>
        </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
		  <tr>
		    <td width="10" valign="top"><img src="<%=request.getContextPath()%>/images/crvBox2-tl-fill.png" width="10" height="12" /></td>
		    <td width="475" class="crvBox2-top"></td>
		    <td width="15" align="right" valign="top"><img src="<%=request.getContextPath()%>/images/crvBox2-tr.png" width="16" height="12" /></td>
		  </tr>
		  <tr>
		    <td class="crvBox2-left"></td>
		    <td valign="top" class="crvBox2-bg"><div style="padding:0px 4px 4px 2px;">



    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
		<td class="pg_hdr">
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
				<form action=customFormActionsPage method=post <%=Constants.FORM_ENCODING%>>
					<input type=hidden name=formID value=<%=sFormID%>>
					<input type=hidden name=fieldID value=<%=sFieldNo%>>
					<input type=hidden name=action value=<%=sAction%>>
				<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
				<td>
                    <input type="submit" value=<%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%> class="new_button">&nbsp;
					<input type="button" name="Button2" value=<%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%> onClick="javascript:window.close()" class="new_button">
				</td>
				</form>
			</tr>
<%
	} else {
		Info fieldvalues = builderWebForm.builderFieldValues(request);//BB_Naming_Convention
		
		String data[] = null;
		String fieldId = null;
		int option = 1;
		String title = ""+LanguageUtil.getString("Add New Field",(String)session.getAttribute("userLanguage"))+"";
		formButtonTag = ""+LanguageUtil.getString("Add",(String)session.getAttribute("userLanguage"))+"";
        if (sAction.equals("modify")) {
			title = LanguageUtil.getString("Modify Field",(String)session.getAttribute("userLanguage"));
			formButtonTag = LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"));
			fieldId = fieldvalues.getString(BuilderFormFieldNames.FIELD_ID);
			if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("text")) {
				 option = 3;
			} else if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("Date")) {
                option = 4;
        	} else if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("varchar")) {
        		option = 1;
        	} else if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("int")) {
        		option = 2;
        	} else if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("combo")) {
        		option = 5;
        	} else if (fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).equals("radio")) {
        		option = 6;
        	} else 
        		option = 7;
		}
		/*
		if (sAction.equals("add"))
        {
			sAction =LanguageUtil.getString("modify",(String)session.getAttribute("userLanguage"));
		}
		*/
%>
			<tr>
				<td colspan="2" class="tb_hdr_b"><%=title%></td>
			</tr>
			<tr class="text">
				<form name="fieldModify" action="builderFormActionsPage" method=post <%=Constants.FORM_ENCODING%>>
				<input type="hidden" name="moduleId" value="<%=moduleId%>"> <!-- P_FS_Enh_BuilderForm -->
					<input type="hidden" name="divSelect" value="hide">
					<input type="hidden" name="<%=BuilderFormFieldNames.FIELD_NAME%>" value="<%=fieldvalues.getString(BuilderFormFieldNames.FIELD_NAME)%>">
					<input type="hidden" name="<%=BuilderFormFieldNames.IS_BUILD_FIELD%>" value="<%=fieldvalues.getString(BuilderFormFieldNames.IS_BUILD_FIELD)%>">
					<input type="hidden" name="<%=BuilderFormFieldNames.DATA_TYPE%>" value="String">
					<input type="hidden" name="<%=BuilderFormFieldNames.BUILDER_FORM_ID%>" value="<%=fieldvalues.getString(BuilderFormFieldNames.BUILDER_FORM_ID)%>">
					<input type="hidden" name="tableName" value="<%=val%>">
					<input type="hidden" name="checkValue" value="<%=checkValue%>">
					
				<td width="30%" align="right"><span class="urgent_fields">*</span><%=LanguageUtil.getString("Display Name",(String)session.getAttribute("userLanguage"))%>:</td>
				<td width="70%">
					<input type="text" name="<%=BuilderFormFieldNames.DISPLAY_NAME%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DISPLAY_NAME)%>' class="text">
				</td>
			</tr>
			<% if(!isBuildFld && !isInputVal) { %>
			<tr class="tb_data">
				<td align="right" width="30%"><%=LanguageUtil.getString("Field Type",(String)session.getAttribute("userLanguage"))%>:</td>
				<td width="70%">
                    <select name="<%=BuilderFormFieldNames.DB_COLUMN_TYPE%>" class="listN01" onChange="javascript:selectDiv()">
						<option value="varchar" <%=(option == 1)?"selected":""%>><%=LanguageUtil.getString("Text",(String)session.getAttribute("userLanguage"))%></option>
						<option value="int" <%=(option == 2)?"selected":""%>><%=LanguageUtil.getString("Number",(String)session.getAttribute("userLanguage"))%></option>
						<option value="text" <%=(option == 3)?"selected":""%>><%=LanguageUtil.getString("Text Area",(String)session.getAttribute("userLanguage"))%></option>
						<option value="Date" <%=(option == 4)?"selected":""%>><%=LanguageUtil.getString("Date",(String)session.getAttribute("userLanguage"))%></option>
						<option value="combo" <%=(option == 5)?"selected":""%>><%=LanguageUtil.getString("Combo",(String)session.getAttribute("userLanguage"))%></option>
						<option value="radio" <%=(option == 6)?"selected":""%>><%=LanguageUtil.getString("Radio",(String)session.getAttribute("userLanguage"))%></option>
						<option value="double" <%=(option == 7)?"selected":""%>><%=LanguageUtil.getString("Amount",(String)session.getAttribute("userLanguage"))%></option>
					</select>
				</td>
			</tr>
			
<%--			<tr class="text">
				<td width="30%" align="right"><span class="urgent_fields">*</span><%=LanguageUtil.getString("Field Size",(String)session.getAttribute("userLanguage"))%>:</td>
				<td width="70%">
					<input type="text" name="<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH)%>' class="text">
				</td>
			</tr> --%>		
				    <input type="hidden" name="<%=BuilderFormFieldNames.DB_COLUMN_SEQ%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_SEQ)%>' >
				    <input type="hidden" name="<%=BuilderFormFieldNames.DB_COLUMN_NAME%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_NAME)%>' >
				    <input type="hidden" name="<%=BuilderFormFieldNames.SECTION%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.SECTION)%>' >
				    <%-- <input type="hidden" name="<%=BuilderFormFieldNames.DB_COLUMN_LENGTH%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH)%>' >--%>
			<% } %>	
				    
				    <input type="hidden" name="<%=BuilderFormFieldNames.IS_ACTIVE%>" value='<%=fieldvalues.getString(BuilderFormFieldNames.IS_ACTIVE)%>'>
<%
if(!isBuildFld && !isInputVal) { %>
			<tr class="text">
				<td align="right" width="30%" nowrap><%=LanguageUtil.getString("Is Exportable and Reportable",(String)session.getAttribute("userLanguage"))%>?</td>
				<td width="70%">
<%
		if (fieldvalues.getString(BuilderFormFieldNames.EXPORTABLE).equals("1")) {
%>
					<input type="radio" name="exportable" value="1" checked >
						<%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%>
					<input type="radio" name="exportable" value="0" >
						<%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%>
<%
		} else {
%>
			        <input type="radio" name="exportable" value="1">
				        <%=LanguageUtil.getString("Yes",(String)session.getAttribute("userLanguage"))%>
					<input type="radio" name="exportable" value="0" checked>
					   <%=LanguageUtil.getString("No",(String)session.getAttribute("userLanguage"))%>
<%
		}
%>
				</td>
			</tr>

<% }
	    ArrayList fieldOrderList = new ArrayList();
    	//fieldOrderList = builderWebForm.selectOrder(sFormID , sFieldNo);//BB_Naming_Convention
	    int size = 0;//fieldOrderList.size();
        if (size != 0 ) {
%>
			<tr class="tb_data">
				<td align="right" width="30%"><%=LanguageUtil.getString("Order",(String)session.getAttribute("userLanguage"))%>:</td>
				<td class="text" width="70%">
		    		<select name="orderNo" class="dropdown_list" >
						<option value="0"><%=LanguageUtil.getString("Select",(String)session.getAttribute("userLanguage"))%></option>
<%
		    String orderValue[] = null;
            for (int k=0;k<size;k++) {
			    orderValue = (String[])fieldOrderList.get(k);
%>
						<option value="<%=orderValue[0]%>" <%=(orderValue[0].equals(fieldvalues.getString(""))? "selected": "")%> ><%=LanguageUtil.getString("After",(String)session.getAttribute("userLanguage"))%> <%=orderValue[1]%></option>
<%	        }
%>
					</select>
				</td>
			</tr>
<%
	    } else {
	    	if (sAction.equals("add"))
	        {
%>
				<input type="hidden" name="orderNo" value="<%=fieldvalues.getString(BuilderFormFieldNames.ORDER_NO)%>">
<%
			} else {
%>
				<!-- <input type="hidden" name="orderNo" value="0"> -->
<%
			}
	    }
%>
			</table>
		</td>
		<td width="10"><img src="../images/spacer.gif" width="10" height="1"></td>
	</tr>


<input type=hidden name=formID value=<%=sFormID%>>
<input type=hidden name=action value=<%=sAction%>>
	
<%	}


%>
</table>

</td>
    <td class="crvBox2-right"></td>
  </tr>
  <tr>
    <td align="left" valign="bottom"><img src="<%=request.getContextPath()%>/images/crvBox2-bl.png" width="10" height="17" /></td>
    <td class="crvBox2-botom"></td>
    <td align="right" valign="bottom"><img src="<%=request.getContextPath()%>/images/crvBox2-br.png" width="16" height="17" /></td>
  </tr>
</table></td></tr>

<tr>
    <td colspan="2" align="left" class="text"><input id="submitButton" name="submitButton" value="<%=formButtonTag%>" class="cm_new_button" type="button" style="margin-right:5px;" onClick="javascript:validate()" />
        <input id="Button" name="Button" value="<%=LanguageUtil.getString("Close")%>" class="cm_new_button" type="reset" style="margin-right:5px;" onclick="javascript:window.close()" /></td>
</tr>
</form>
  
</table>

</body>
</html>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>