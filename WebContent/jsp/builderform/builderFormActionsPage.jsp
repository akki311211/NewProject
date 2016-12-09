<%--
 - $Header						:  \web\jsp\builderForm\builderFormsActionPage.jsp
 - $Author						:  $Abhishek Gupta
 - $Version						:  $1.1
 - $Date						:  $ Dec 29,2011
 -
 - ==============================================================================================
 - Basic Description of JSP		: This jsp controls the action of builderFormModificationPopUp.jsp depending on the action value add , modify , delete .
 - P_FS_Enh_BuilderForm			16Dec2013			Naman Jain			Form Builder in Franchise Sales
 --%>
 <%@page import="com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "com.appnetix.app.portal.forms.*"%>
<%@ page import = "com.home.builderforms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>

<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="application"/>
<jsp:useBean id="builderWebForm" class="com.home.builderforms.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>
<jsp:setProperty name = "builderWebForm" property = "*" /><%--BB_Naming_Convention --%>
<%
try {
	String sAction = request.getParameter("action");
	String sFieldId = request.getParameter("fieldID");
	String sFimFormId = request.getParameter("fimFormId");
	String sFormID = request.getParameter("formID");
	String tableAnchor = request.getParameter("tableName");
	String isBuildField = request.getParameter(BuilderFormFieldNames.IS_BUILD_FIELD);
	String checkValue = request.getParameter("checkValue");
	String moduleId = request.getParameter("moduleId"); //P_FS_Enh_BuilderForm
	String includeInCampaign = request.getParameter(FieldNames.INCLUDE_IN_CAMPAIGN); //BB-20150319-268(FIM Campaign Center)
	
	boolean isBuildFld = Boolean.parseBoolean(isBuildField);
	boolean isInputVal = Boolean.parseBoolean(checkValue);

	System.out.println(" tableName: " + tableAnchor);
	String actionMain = null;
	int returnValue = -1;
	int optionCount = -1;
	
	String sMessage = "";
	String action = request.getParameter("action");
	System.out.println(" sFormID: " + sFormID + " sFimFormId: " + sFimFormId + " sAction: " + sAction);

	if (sAction != null && sAction.equals("modify")) {
		session.setAttribute("returnVal", "returnModifyValue");
		String rV = (String)session.getAttribute("returnVal");
		System.out.print("rV"+rV);
	}
	
	if (sAction != null && sAction.equals("add")) {
		session.setAttribute("returnVal", "returnAddValue");
		String rV = (String)session.getAttribute("returnVal");
		System.out.print("rV"+rV);
		//sAction = "modify";
	}	
	
	System.out.println("sAction.........>"+sAction);
	String dateType = null; 
	SequenceMap smap = null;
	String combooption = null;
	if(!isBuildFld) {
		if(sAction != null && sAction.equals("addOptions")) {//SMC-20140213-378
			System.out.println("addOptions tableName: " + tableAnchor);
	
			actionMain = request.getParameter("actionMain");
			
			String[] optionName = request.getParameterValues("optionName");
			String[] dependentFieldValues = request.getParameterValues("fieldDependentValueHidden"); //BB-20150203-259 (Dynamic Response based on parent field response)
			String fieldName = request.getParameter("fieldName");
			
			boolean flag = builderForm.deleteRadioOrComboOptions(fieldName, tableAnchor);
			System.out.println("addOptions flag: " + flag);
			builderForm.insertRadioOrComboOptionsMap(fieldName,optionName, tableAnchor, null, dependentFieldValues); //BB-20150203-259 (Dynamic Response based on parent field response)
			
		}
	}
%>
<link rel="stylesheet" href="style.css" type="text/css">
<%
	if (sAction != null && sAction.equals("delete")) {//SMC-20140213-378
		returnValue = builderWebForm.deleteBuilderField(sFieldId,sFormID);//BB_Naming_Convention
		if (returnValue != -1)
			sMessage = LanguageUtil.getString("Field Has Been deleted successfully.");
		else
			sMessage = LanguageUtil.getString("Internal Error: Could not delete the specified Field.");
	} else if (sAction != null && sAction.equals("add")) {//SMC-20140213-378
		boolean builderUpdate = true;
		System.out.println("\n\n\n\n\n\nCCCCCCCCCCCCCCCC:::::::::::::::::");
		MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = true;
		builderForm.setBuilderFormAddOrUpdate(request);
		/*if (builderUpdate) {
			String formId = request.getParameter(BuilderFormFieldNames.DB_COLUMN_SEQ);
			combooption = request.getParameter("dropdownoption");
			//builderWebForm.updateColumnInfo("CUSTOM_FIELD_DATA",new String[]{"IS_AVAILABLE"},new String[]{"N"},new String[]{"DB_FIELD_SEQ"},new String[]{formId});//BB_Naming_Convention
			sMessage = LanguageUtil.getString("Field has been added successfully.");
			if(!isBuildFld) {
				dateType = request.getParameter(BuilderFormFieldNames.DB_COLUMN_TYPE);
				if((dateType.equals("combo") && combooption != null && ("0".equals(combooption) || "3".equals(combooption))) || dateType.equals("radio") || dateType.equals("checkbox") || dateType.equals("multiselect")) { //BB-20150203-259 (Dynamic Response based on parent field response)
					String[] optionName = request.getParameterValues("optionName");
					String fieldName = request.getParameter(BuilderFormFieldNames.FIELD_NAME);
					//BB-20150203-259 (Dynamic Response based on parent field response) starts
					String[] dependentFieldValues = request.getParameterValues("fieldDependentValueHidden");
					builderForm.insertRadioOrComboOptionsMap(fieldName,optionName, tableAnchor, null, dependentFieldValues);
					//BB-20150203-259 (Dynamic Response based on parent field response) ends
				} 
			}
		} else {
			sMessage = LanguageUtil.getString("Internal Error: Could not add the specified Field.");
		}*/
	} else if (sAction != null && sAction.equals("modify")) {//SMC-20140213-378
		boolean builderUpdate = true;
		request.setAttribute("modifyFld","yes");
		System.out.println("\n\n\n\n\n\nDDDDDDDDDDDDDDD:::::::::::::::::");
		MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = true;
		builderForm.setBuilderFormAddOrUpdate(request);
		//returnValue = builderWebForm.modifyBuilderField(sFieldId, sFormID);//BB_Naming_Convention
		String dropdownOpt = request.getParameter("dropdownOpt");

		if(!isBuildFld && !isInputVal && !StringUtil.isValid(dropdownOpt)) {
			dateType = request.getParameter(BuilderFormFieldNames.DB_COLUMN_TYPE);
			if(!StringUtil.isValidNew(dateType)) {
				dateType = request.getParameter("dataTypeModify");
			}
			if("combo".equals(dateType) || "radio".equals(dateType) || "checkbox".equals(dateType)) {//P_CM_B_61858
				smap = builderForm.getRadioOrComboOptionsMap(request.getParameter(BuilderFormFieldNames.FIELD_NAME), tableAnchor);
				if(smap.size() > 0)
					optionCount = smap.size();
				else {
					optionCount = 1;
				}
			} 
		}
		//SMC-20140213-378 Starts		
		//if(!isBuildFld && !isInputVal) 
		/*{
			dateType = request.getParameter(BuilderFormFieldNames.DB_COLUMN_TYPE);
			if(dateType.equals("combo") || dateType.equals("radio") || dateType.equals("checkbox")||dateType.equals("multiselect")) {
				String[] optionName = request.getParameterValues("optionName");
				String[] statusValue = request.getParameterValues("optionActivatedOrdered");
				String[] dependentFieldValues = request.getParameterValues("fieldDependentValueHidden"); //BB-20150203-259 (Dynamic Response based on parent field response)
				String fieldName = request.getParameter(BuilderFormFieldNames.FIELD_NAME);
				boolean flag = builderForm.deleteRadioOrComboOptions(fieldName, tableAnchor);
				builderForm.insertRadioOrComboOptionsMap(fieldName,optionName, tableAnchor,statusValue, dependentFieldValues); //BB-20150203-259 (Dynamic Response based on parent field response)
			} 
		}*///SMC-20140213-378 Ends
		
		if (action.equalsIgnoreCase("modify")) {
			sMessage = LanguageUtil.getString("The Field has been modified successfully.");
		} else if (returnValue != -1 && action.equalsIgnoreCase("add")) {
			sMessage = LanguageUtil.getString("The Field has been added successfully.");
		} else {
			sMessage = LanguageUtil.getString("Internal Error: Could not") +" " + action + " " + LanguageUtil.getString("the specified Custom Field.");
		}
	}
    if(MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING == true) {
        long i = 0;
        while(true) {
            i++;
            //Thread thread = (Thread)session.getAttribute("formGeneratorFieldAddThread");          // For Tomcat Clustering Issue
            if(MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING == false) {
                //session.removeAttribute("formGeneratorFieldAddThread");
                if(i > 100000000) {
                    session.setAttribute("returnVal", "willTakeTime");
                }
                break;
            }
        }
    }
%>
<link rel=stylesheet href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<%@ include file = "builderScriptCss.jsp" %>
<html>
<head>
	<title><%=MultiTenancyUtil.getTenantContext().getAttribute("title")!= null ?MultiTenancyUtil.getTenantContext().getAttribute("title") : "Franchise System"%></title>
</head>
<%--<% if(dateType == null || dateType.equals("numeric") || dateType.equals("int") || dateType.equals("Date") || dateType.equals("text") || dateType.equals("varchar") || ((dateType.equals("combo") && combooption != null && !"0".equals(combooption)))) { %>--%><%--SMC-20140213-378--%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad=closeWindow()>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
</table>
<%--SMC-20140213-378 Starts--%>
<%--
<% } else { 
	String className = "text";
	String title = "";
	if(dateType.equals("combo")) { 
		title = "Add Drop-down Options";
	} else if(dateType.equals("radio")) {
		title = "Add Radio Options";
	} else if(dateType.equals("checkbox")) {
		title = "Add Checkbox Options";
	}
%>
<script>
var w=screen.width;
var h=screen.height;
var inc = 50*(h/768);
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
<% if(dateType == null || dateType.equals("numeric") || dateType.equals("int") || dateType.equals("Date") || dateType.equals("text") || dateType.equals("varchar") || ((dateType.equals("combo") && combooption != null && !"0".equals(combooption)))) { %>
<%}else{%>
window.onload = function(){
	var firstOpt = document.form1.optionName;
	<%if(optionCount == 1) { %>
		firstOpt.focus();
	<%}else{%>
		firstOpt[0].focus();
	<%}%>
	resizeWindow();
}
<%}%>
	
	var rowCount = <%=optionCount%>
	function addMore() {
		var classname =  "theme_text";
		var removeTD=document.getElementById("removeTD" + (rowCount));
		if(removeTD!=null)
		removeTD.innerHTML = "";
		
		var x = document.getElementById('table1').insertRow(rowCount);
		rowCount++;
		x.id = "row_" + rowCount;
		x.className = "text_b";
		str = "";
		var p = x.insertCell(0);
		var q = x.insertCell(1);
		var r = x.insertCell(2);
		
		if(rowCount%2==0) {
			classname = "theme_tb_data_b"
		} else {
			classname = "theme_text_b"
		}
		p.className = classname;
		q.className = classname;
		r.className = classname;
		p.align = "right";
		q.align = "left";
		r.align = "left";
		p.width = "50%";
		//q.width = "50%";
		r.width = "50%";
		r.id= "removeTD"+rowCount;

	
		p.innerHTML = "Option " + rowCount+" : ";
		q.innerHTML = "<input type = 'textbox' class = 'textbox' value = '' name = 'optionName' onchange = 'checkDuplicate(this)'>";
		r.innerHTML = "<a href = \"#\" onclick = \"remove()\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif\" border = '0'></a>";
		//height = height+35;
		
		resizeWindow();
	}
	
	function remove() {
		//if(rowCount!=1) {
			var removeTD=document.getElementById("removeTD" + (rowCount-1));
			if(removeTD!=null && rowCount-1 > 1)
			removeTD.innerHTML = "<a href = \"#\" onclick = \"remove()\"><img src=\"<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif\" border = '0'></a>";
			var i=document.getElementById("row_" + rowCount).rowIndex;
		    document.getElementById('table1').deleteRow(i);
		    //height = height-35;
			rowCount--;
			
			resizeWindow();
			
		//} else {
		    
		//}
	}
	
	
	function trim(s) {
        return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
    }

	function submitForm() {
		
		var optionName = document.getElementsByName("optionName");
		for(var i = 1;i<=optionName.length;i++){
			if(trim(optionName[i-1].value) == "") {
				alert("Please enter Option "+i);
				optionName[i-1].focus();
				return false;
			}
		}
		document.form1.submit();
	}
	function checkDuplicate(obj) {
		var arr = document.getElementsByName("optionName");
		
		for(var i=0;i < arr.length;i++) {
			if(arr[i] != obj && arr[i].value==trim(obj.value)) {
				alert("This option already exist.");
				obj.value="";
				obj.focus();
				break;
			}
		}
	}
	function resizeWindow(){
		var height = document.getElementById("containerDiv").offsetHeight;
		if(parent.$.fn.colorbox){
			parent.$.fn.colorbox.vkResize(500, height+inc);
		}
		else{
			
		if(height <= 400)
			resizeTo(450,height+inc);
		}
	}
</script>

<script language="javascript">
	<%--var height = 200;
	<%if(optionCount==1) {%>
	window.resizeTo(450, 220);
	<%} else {%>
	height = <%=150+optionCount*40%>;
	window.resizeTo(450, <%=150+optionCount*40%>);
	<%}--//%>
</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action = "builderFormActionsPage" method = "post" name = "form1" <%=Constants.FORM_ENCODING%>>
<input type = 'hidden' value = '<%=request.getParameter(BuilderFormFieldNames.FIELD_NAME)%>' name = 'fieldName'>
<input type=hidden name=action value='addOptions'>
<input type=hidden name=formID value='<%=sFormID%>'>
<input type=hidden name=formNames value='<%=sFormID%>'>
<input type=hidden name=actionMain value='<%=sAction%>'>
<input type=hidden name=tableName value='<%=tableAnchor%>'>
<input type=hidden name=dbColumnType value='<%=dateType%>'>
<input type=hidden name=<%=BuilderFormFieldNames.IS_BUILD_FIELD%> value='<%=isBuildField%>'>
<div id="containerDiv">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
        <td width="6" class="text" colspan="2"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="25"></td>
    </tr>
	<tr>
	<td colspan="2" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="18%" valign="bottom" colspan="3">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="9" class="crvBox2-ltBg"></td>
                <td  nowrap="nowrap" class="crvBox2-ttBg"><%=LanguageUtil.getString("FIM Builder Tab Fields",(String)session.getAttribute("userLanguage"))%></td>
                <td width="33" class="crvBox2-rtBg"></td>
              </tr>
            </table></td>
          </tr>
        </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
		  <tr>
		    <td width="10" valign="top"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl-fill.png" width="10" height="12" /></td>
		    <td width="475" class="crvBox2-top"></td>
		    <td width="15" align="right" valign="top"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png" width="16" height="12" /></td>
		  </tr>
		  <tr>
		    <td class="crvBox2-left"></td>
		    <td valign="top" class="crvBox2-bg"><div style="padding:0px 0px 4px 0px;">
		    

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1"></td>
		<td height="22" width="100%">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<tr>
				<td colspan="2" height="25" align="left" class="tb_hdr_b" nowrap><%=LanguageUtil.getString(title)%></td>
			</tr>
			<tr>
				<td width="100%" colspan="2">
				<table width="100%" border="0" cellspacing="1" cellpadding="4" id='table1'>
					<%if(optionCount == 1) { %>
					<tr id = "row_1">
						<td width="50%" align="right" class = "<%=className%>_b"><%=LanguageUtil.getString("Option") %> 1 : </td>
						<td align="left" class = "theme_text">
						<input type = "textbox" value = "" name = "optionName" class = "textbox" onchange = "checkDuplicate(this)">
						</td>
						<td width="50%" align="left" class = "<%=className%>" id = "removeTD1">
						 <%--<a href = "#" onclick = "remove()"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif' border = '0'></a> --//%>
						</td>
					</tr>
					<%} else { %>
						<%for(int i = 1 ; i <= smap.size() ;i++) {
						Info info = (Info)smap.get(i-1);
						if(i%2==0) {
							className = "theme_tb_data_b";
						} else {
							className = "theme_text_b";
						}
						%>
						<tr id = "row_<%=i %>">
							<td width="50%" align="right" class = "<%=className%>"><%=LanguageUtil.getString("Option")+" "+i %> : </td>
							<td align="left" class = "<%=className%>">
							<input type = 'textbox' value = '<%=info.getString(BuilderFormFieldNames.OPTION_VALUE) %>' name = 'optionName' class = "textbox" onchange = "checkDuplicate(this)">
							</td>
							<td width="50%" align="left" class = "<%=className%>" id = "removeTD<%=i%>">
							<%if(i == smap.size() && i>1) {%>
							 <a href = "#" onclick = "remove()"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/delete_icon.gif' border = '0'></a>
							 <%} %>
							</td>
						</tr>
						
						<%} %>
					<%} %>
							
					<tr>
					<td colspan="3" align="right" class = "theme_text_b">
					<a href = "#" onclick = "addMore()"><%=LanguageUtil.getString("Add More") %></a>
					</td>
					</tr>
				</table>
				</td>

			</tr>
<%--			<tr>
				<td class="tb_hdr" align="left">
					<input type="button" value="<%=LanguageUtil.getString("Save")%>" onClick="submitForm()" class="new_button" name="button"> 
					<input type="button" value="<%=LanguageUtil.getString("Close")%>" onClick="javascript:closeWindow()" class="new_button" name="button">
				</td>
			</tr> --//%>
		</table>
		</td>

		<td width="1"></td>
	</tr>
</table>


</td>
    <td class="crvBox2-right"></td>
  </tr>
<tr>
    <td align="left" valign="bottom"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png" width="10" height="17" /></td>
    <td class="crvBox2-botom"></td>
    <td align="right" valign="bottom"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png" width="16" height="17" /></td>
  </tr> 
</table>
</td>
</tr>

<tr>
    <td colspan="2" align="left" class="text">
    <input type="button" value="<%=LanguageUtil.getString("Save")%>" onClick="submitForm()" class="cm_new_button" name="button">
    <input type="button" value="<%=LanguageUtil.getString("Close")%>" onClick="javascript:closeWindow1()" class="cm_new_button" name="button">
    </td>
</tr>

</table>
</div>
</form>
<% } %>
--%><%--SMC-20140213-378 Ends--%>
</body>
<%-- BUG_76360 Start--%>
<script language="javascript">
         //   window.resizeTo(600,300);
		function closeWindow() 	{

			if(parent.$.fn.colorbox && parent.document.blankForm){
				<%if(actionMain != null) {%>
				parent.document.blankForm.action.value = "<%=actionMain%>";
			<% } else { %>
			parent.document.blankForm.action.value = "<%=sAction%>";
			<% } %>
			parent.document.blankForm.formID.value = "<%=sFormID%>";
			parent.document.blankForm.formNames.value = "<%=sFormID%>";
			parent.document.blankForm.moduleId.value = "<%=moduleId%>"; //P_FS_Enh_BuilderForm
			parent.document.blankForm.submit();
				
			}
			else if (window.opener && window.opener.document.blankForm) 	{
				<%if(actionMain != null) {%>
					window.opener.document.blankForm.action.value = "<%=actionMain%>";
				<% } else { %>
					window.opener.document.blankForm.action.value = "<%=sAction%>";
				<% } %>
				window.opener.document.blankForm.formID.value = "<%=sFormID%>";
				window.opener.document.blankForm.formNames.value = "<%=sFormID%>";
				window.opener.document.blankForm.moduleId.value = "<%=moduleId%>"; //P_FS_Enh_BuilderForm
				window.opener.document.blankForm.submit();
			}
			if(parent.$.fn.colorbox){
				parent.$.fn.colorbox.close();
			}else{
			window.close();
			}
		}
		<%--SMC-20140213-378 Starts--%>
<%--
		function closeWindow1() 	{
			<%if(sAction != null && sAction.equals("add")){%>
				if(confirm("Are you sure to add field without selecting any option ?")){
					closeWindow();
				}
			<%}else{%>
				closeWindow()
			<%}%>
		}
--%><%--SMC-20140213-378 Ends--%>
	</script>
<%-- BUG_76360 End--%>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>	
</html>
