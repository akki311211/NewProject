<%@ include file="/jsp/util/import.jsp"%>
<%@ page import = "com.appnetix.app.util.FieldNames" %>

<%@ page import = "com.appnetix.app.portal.role.UserRoleMap"%>
<%@ page import = "com.appnetix.app.control.SystemVariableManager"%>
<%@ page import = "com.appnetix.app.util.Constants,com.appnetix.app.util.*"%>
<%@ page import = "java.util.HashMap,java.util.LinkedHashMap,java.util.Set,java.util.Map,java.util.Iterator"%>
<jsp:useBean id="builderForm"	class="com.home.builderforms.BuilderFormWebImpl" scope="application"/>
<jsp:useBean id="builderWebForm"	class="com.home.builderforms.BuilderWebFormBean" scope="session"/>
<%
	String mid = (String)request.getAttribute("moduleId");
    boolean fromAdmin="yes".equals(request.getParameter("fromAdmin"));
	String pageName=(String)request.getAttribute("pageName");
    Map map = (Map) request.getAttribute("criteriaMap");
	String configureSummary="no";
	if(StringUtil.isValidNew(request.getParameter("configureSummary"))){
		configureSummary=request.getParameter("configureSummary");
	}
	String tableAnchor=request.getParameter("tabularSectionTableName");
	String isTabularSection=request.getParameter("isTabularSection");
    LinkedHashMap criteriaMap = null;
        	criteriaMap=(LinkedHashMap)builderForm.getTabularSectionColumn(tableAnchor,"0,1", null).get(tableAnchor);
    
    
	System.out.println("\n\n\nid in jsp" + criteriaMap+ "\n\n\n\n");
	Set entrySet = criteriaMap.entrySet();
	Iterator iterator = entrySet.iterator();
	String key = null;
	HashMap object = null;
	StringBuffer duplicateCriteriaRight = new StringBuffer(64);
	StringBuffer duplicateCriteriaLeft = new StringBuffer(64);
	while ( iterator != null && iterator.hasNext() )
	{
		Map.Entry entry = (Map.Entry) iterator.next();
		key = (String) entry.getKey();
		object = (HashMap) criteriaMap.get(key);
		
		if ( object != null && object.get("displayName") != null && object.get("displayValue") != null)
		{
			
			
            if (object.get("isSelected") != null
					&& object.get("isSelected").equals("1")) {
				duplicateCriteriaRight.append("<option  value=\"");
				duplicateCriteriaRight.append((String) object
						.get("displayId"));
				duplicateCriteriaRight.append("\">");
				duplicateCriteriaRight.append(LanguageUtil.getString(
						(String) object.get("displayName"),
						(String) session.getAttribute("userLanguage")));
				duplicateCriteriaRight.append("</option>");
			} else if (object.get("isSelected") != null
					&& object.get("isSelected").equals("2")) {
				duplicateCriteriaRight.append("<option value=\"");
				duplicateCriteriaRight.append((String) object
						.get("displayId"));
				duplicateCriteriaRight.append("\">* ");
				duplicateCriteriaRight.append(LanguageUtil.getString(
						(String) object.get("displayName"),
						(String) session.getAttribute("userLanguage")));
				duplicateCriteriaRight.append("</option>");
			} else if (object.get("isSelected") != null
					&& object.get("isSelected").equals("0")) {
				duplicateCriteriaLeft.append("<option value=\"");
				duplicateCriteriaLeft.append((String) object
						.get("displayId"));
				duplicateCriteriaLeft.append("\">");
				duplicateCriteriaLeft.append(LanguageUtil.getString(
						(String) object.get("displayName"),
						(String) session.getAttribute("userLanguage")));
				duplicateCriteriaLeft.append("</option>");
			}
		}
	}
	String userTheme = (String) session.getAttribute("userTheme") != null ? (String) session
			.getAttribute("userTheme") : "";
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
	<td width="100%"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
</tr>
         <tr>
                <td width="9" ></td>
                <td ></td>
                <td ></td>
              </tr>
<tr>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
	<td width="100%"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="10"></td>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
</tr>
<tr> 
        	<td width = "100%" colspan="3" valign="top" height="20" class="fline hText18theme pt pb5" nowrap="nowrap" align="left">
	    	<%=""+LanguageUtil.getString("Configure Display Columns for ",(String)session.getAttribute("userLanguage"))+" "%><%=request.getParameter("sectionDisplayName") %>
	    	</td>
	 
  	</tr>
  	<tr>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="20"></td>
</tr>
<tr style="display:none" id="displayMsg"> 
<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
        <td align="center" class="header"  width="100%" >&nbsp;&nbsp;&nbsp;<span> <%=LanguageUtil.getString("Your request has been processed")%>.&nbsp;&nbsp;&nbsp;</span></td>
</tr>
<tr>
	<form name="duplicateCriteriaForm" action="genericHandler" method="post" <%=Constants.FORM_ENCODING%>>	
	<input type="hidden" name="<%=FieldNames.RETURN_PAGE%>" value="configureTabularSectionColumns?tabularSectionTableName=<%=tableAnchor%>">
	<input type="hidden" name="eventType" value="modify">
	<input type="hidden" name="configureSummary" value="yes">
	<input type="hidden" name="sectionDisplayName" value="<%=request.getParameter("sectionDisplayName") %>">
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
	<td >
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td colspan="3">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
              <td class="bText12b" align="right" >&nbsp;<%=LanguageUtil.getString("Available Columns",(String)session.getAttribute("userLanguage"))%>&nbsp;:&nbsp;</td>
			<td align="left" class="bText12" >
				<select multiple name="duplicateCriteriaLeft" class="multiList" size="12"><%=duplicateCriteriaLeft%></select>
			</td>
			<td class="bText12" align ="center">
				<table>
				<tr>
					<td ><input type="button" class="cm_new_button" value=">>" onclick="javascript:addOption()"></td>
				</tr>
				<tr>
					<td><input type="button" class="cm_new_button" value="<<" onclick="javascript:removeOption()"></td>
				</tr>
				</table>
			</td>
                         <td class="bText12b" align="right" >&nbsp;<%=LanguageUtil.getString("Configured Columns",(String)session.getAttribute("userLanguage"))%>&nbsp;:&nbsp;</td>
			<td align="left" class="bText12" width="10%">
				<select multiple  name="duplicateCriteriaRight" id="duplicateCriteriaRight" class="multiList" size="12"><%=duplicateCriteriaRight%></select>
			</td>
			<td  width="20%" class="bText12" >
					<table width = "100%" cellspacing="5" cellpadding="2" border="0" height = "125">
                    <tr> 
						<td valign = "middle"  class="text"><a href = "javascript:moveToTop();"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveToTop1.gif" title = "<%=LanguageUtil.getString("Move to Top",(String)session.getAttribute("userLanguage"))%>" border = "0"></a></td>
					</tr>
                    <tr> 
						<td valign = "middle" class="text"><a href = "javascript:moveUp()"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveUp1.gif" title = "<%=LanguageUtil.getString("Move Up",(String)session.getAttribute("userLanguage"))%>" border = "0"></a></td>
					</tr>
					<tr><td>&nbsp;</td></tr>
                    <tr> 
						<td valign = "middle"  class="text"><a href = "javascript:moveDown();"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveDown1.gif" title = "<%=LanguageUtil.getString("Move Down",(String)session.getAttribute("userLanguage"))%>" border = "0"></a></td>
					</tr>
                    <tr> 
                        <td valign = "middle" class="text"><a href = "javascript:moveToBottom();"><img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveToBottom1.gif" title = "<%=LanguageUtil.getString("Move to Bottom",(String)session.getAttribute("userLanguage"))%>" border = "0"></a></td>
					</tr>
					</table>
				</td>
		</tr>
       
   </table>
	</td>
 </tr>
</table></td>
	<td><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="6"></td>
	<input type="hidden" value="<%=mid %>" name= "moduleName"  />
	<input type="hidden" name="isTabularSection" value="true"/>
	<input type="hidden" name="tabularSectionTableName" value="<%=tableAnchor%>"/>
	<% Iterator formEntry = entrySet.iterator();
	int entryCount=0;
	if(formEntry != null){
	while (formEntry.hasNext() )
	{
		Map.Entry entry = (Map.Entry) formEntry.next();
		key = (String) entry.getKey();
		object = (HashMap) criteriaMap.get(key);
%>
	<input type="hidden" name="tableAnchor" value="tabularSectionDisplayColumn_<%=entryCount%>"/>
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>displayName" value="<%=object.get("displayName")%>" id="displayName_<%=object.get("displayId")%>" />
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>displayValue" value="<%=object.get("displayValue")%>"/>
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>moduleID" value="<%=object.get("moduleID")%>"/>
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>tableName" value="<%=tableAnchor%>"/>
	 <input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>isSelected" id="isSelected_<%=object.get("displayId")%>" /> 
	 <input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>orderSequence" id="orderSequence_<%=object.get("displayId")%>" /> 
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>userNo" value="<%=session.getAttribute("user_no")%>"/>
	<input type="hidden" name="tabularSectionDisplayColumn_<%=entryCount%>fieldName" value="<%=object.get("fieldName")%>"/>
<% entryCount++;
}	}  %>
	
	
	
	</form>
</tr>
<tr> 
<td width="10">&nbsp;</td>
<td width="100%" style="padding-top:10px">
                <input type="button" class="cm_new_button" value="<%=LanguageUtil.getString("Submit",(String)session.getAttribute("userLanguage"))%>" onclick="javascript:submitForm()">
                &nbsp;<input type="button" class="cm_new_button" value="<%=LanguageUtil.getString("Close",(String)session.getAttribute("userLanguage"))%>" onclick="javascript:closeWindow();">
</td>
<td width="10">&nbsp;</td>
</table>

<script>

function moveUp() 
	{
		var formVar = document.duplicateCriteriaForm.duplicateCriteriaRight;
		var selectIndex = formVar.selectedIndex;
		var lenght = document.duplicateCriteriaForm.duplicateCriteriaRight.options.length;
	
	   //for selecting just one option (Start)
		  var selectedArray = new Array();
		  var selObj = document.getElementById('duplicateCriteriaRight');
		  var i;
		  var count = 0;
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      selectedArray[count] = selObj.options[i].value;
		      count++;
		    }
		  }
	       
	       if(selectedArray.length > 1){
	       //alert("Please select only one column.");
	       FCI.Alerts(FCLangAdmin.ONLY_ONE_COLUMN,"true");
	       return ;
	       }
        //for selecting just one option (End)
        
		if (selectIndex != -1 && selectIndex != 0) 
		{
			var selectedOptionValue = formVar.options[selectIndex].value;
			var selectedOptionText = formVar.options[selectIndex].text;
			var previousOptionValue = formVar.options[selectIndex -1].value;
			var previousOptionText = formVar.options[selectIndex -1].text;
			formVar.options[selectIndex].value = previousOptionValue;
			formVar.options[selectIndex].text = previousOptionText;
			formVar.options[selectIndex - 1].value = selectedOptionValue;
			formVar.options[selectIndex - 1].text = selectedOptionText;
			formVar.options[selectIndex - 1].selected = true;
			formVar.options[selectIndex].selected = false;//for deselecting the previous value when clicking on Move up button
		}

		return;
	}

	function moveDown() 
	{
		var formVar = document.duplicateCriteriaForm.duplicateCriteriaRight;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		
		//for selecting just one option (Start)
		  var selectedArray = new Array();
		  var selObj = document.getElementById('duplicateCriteriaRight');
		  var i;
		  var count = 0;
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      selectedArray[count] = selObj.options[i].value;
		      count++;
		    }
		  }
	       
	       if(selectedArray.length > 1){
	       //alert("Please select only one column.");
	       FCI.Alerts(FCLangAdmin.ONLY_ONE_COLUMN,"true");
	       return ;
	       }
        //for selecting just one option (End)
		
		if (selectIndex != -1 && selectIndex != (noOfOptions - 1)) 
		{
			var selectedOptionValue = formVar.options[selectIndex].value;
			var selectedOptionText = formVar.options[selectIndex].text;
			var nextOptionValue = formVar.options[selectIndex + 1].value;
			var nextOptionText = formVar.options[selectIndex + 1].text;
			formVar.options[selectIndex].value = nextOptionValue;
			formVar.options[selectIndex].text = nextOptionText;
			formVar.options[selectIndex + 1].value = selectedOptionValue;
			formVar.options[selectIndex + 1].text = selectedOptionText;
			formVar.options[selectIndex + 1].selected = true;
			formVar.options[selectIndex].selected = false; //for deselecting the previous value when clicking on Move down button
		}

		return;
	}

	function moveToBottom() 
	{
		var formVar = document.duplicateCriteriaForm.duplicateCriteriaRight;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		
		//for selecting just one option (Start)
		  var selectedArray = new Array();
		  var selObj = document.getElementById('duplicateCriteriaRight');
		  var i;
		  var count = 0;
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      selectedArray[count] = selObj.options[i].value;
		      count++;
		    }
		  }
	       
	       if(selectedArray.length > 1){
	       //alert("Please select only one column.");
	       FCI.Alerts(FCLangAdmin.ONLY_ONE_COLUMN,"true");
	       return ;
	       }
        //for selecting just one option (End)
        
		if (selectIndex != -1 && selectIndex != (noOfOptions - 1)) 
		{
			var selectedOption = formVar.options[selectIndex];
			formVar.options[selectIndex] = null;
			formVar.options[noOfOptions - 1] = selectedOption;
			formVar.options[noOfOptions - 1].selected = true;
		}

		return;
	}

	function moveToTop() 
	{
		var formVar = document.duplicateCriteriaForm.duplicateCriteriaRight;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		
        //for selecting just one option (Start)
		  var selectedArray = new Array();
		  var selObj = document.getElementById('duplicateCriteriaRight');
		  var i;
		  var count = 0;
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      selectedArray[count] = selObj.options[i].value;
		      count++;
		    }
		  }
	       
	       if(selectedArray.length > 1){
	       //alert("Please select only one column.");
	       FCI.Alerts(FCLangAdmin.ONLY_ONE_COLUMN,"true");
	       return ;
	       }
        //for selecting just one option (End)
        
		if (selectIndex != -1 && selectIndex != 0) 
		{
			var selectedOptionValue = formVar.options[selectIndex].value;
			var selectedOptionText = formVar.options[selectIndex].text;
			formVar.options[selectIndex] = null;
			var newOption = new Option("","",false,false);
			formVar.options[noOfOptions - 1] = newOption;

			for (var count = (noOfOptions - 1); count > 0; count --) 
			{
				formVar.options[count].text = formVar.options[count - 1].text;
				formVar.options[count].value = formVar.options[count - 1].value;
			}
			
			formVar.options[0].text = selectedOptionText;
			formVar.options[0].value = selectedOptionValue;
			formVar.options[0].selected = true;
		}

		return;
	}

	function addOption()
	{
        var targetForm = document.duplicateCriteriaForm;
        
        if ( targetForm.duplicateCriteriaLeft.selectedIndex == -1 )
		{
			//alert("Please select a Column from Available Columns to Configure.");
			FCI.Alerts(FCLangAdmin.A_COLUMN_FROM_CONFIGURE_COLUMNS_TO_CONFIGURE,"true");
			return false;
		}
        var count=targetForm.duplicateCriteriaRight.options.length;
        var countLeftSelected=0;
        var len = targetForm.duplicateCriteriaLeft.options.length;
        for ( var i = 0; i < len ; i++ )
        {
            if ( targetForm.duplicateCriteriaLeft.options[i].selected )
            {
            	countLeftSelected++;
            }
        }
        if((count+countLeftSelected)>6){
        	FCI.Messages(FCLangAdmin.MAX_COLUMNS_CAN_BE_SELECTED_FOR_DISPLAY);
        	return false;
        }
        for ( var i = 0; i < len ; i++ )
        {
            if ( targetForm.duplicateCriteriaLeft.options[i].selected )
            {
                appendOption(targetForm.duplicateCriteriaRight, targetForm.duplicateCriteriaLeft.options[i].text, targetForm.duplicateCriteriaLeft.options[i].value);
                targetForm.duplicateCriteriaLeft.options[i].text="Empty";
            }
        }
        
        for ( i = 0; i < targetForm.duplicateCriteriaLeft.options.length ; i++ ) 
        {
		    if ( targetForm.duplicateCriteriaLeft.options[i].text == "Empty" ) 
            {
                targetForm.duplicateCriteriaLeft.options[i] = null;
				--i;
			}
		}
	}

	function appendOption( criteriaBox, text, value )
	{
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		criteriaBox.options.add(optn);
	}

	function deleteOption(criteriaBox, index )
	{
		var size = criteriaBox.options.length;

		for ( var i = index; i < size-1 ; i++)
		{
			criteriaBox.options[i].text = criteriaBox.options[i+1].text;
			criteriaBox.options[i].value = criteriaBox.options[i+1].value;
		}

		criteriaBox.options.length = criteriaBox.options.length-1;
	}

	function reorderOptions(criteriaBox)
	{
	}

	function removeOption()
	{
		var targetForm = document.duplicateCriteriaForm;
		var flag=false;
		var defaultcolumns="";
		if(targetForm.duplicateCriteriaRight.selectedIndex == -1)
		{
			//alert("Please select a Column From Configure Columns to remove.");
			FCI.Alerts(FCLangAdmin.A_COLUMN_FROM_CONFIGURE_COLUMNS_TO_REMOVE,"true");
			return false;
		}

       // var len=targetForm.duplicateCriteriaRight.options.length;
        var countRightSelected=targetForm.duplicateCriteriaRight.options.length;
        var len = targetForm.duplicateCriteriaRight.options.length;
        for ( var i = 0; i < len ; i++ )
        {
            if ( targetForm.duplicateCriteriaRight.options[i].selected )
            {
            	countRightSelected--;
            }
        }
        if(countRightSelected<1){
        	FCI.Messages(FCLangAdmin.MIN_COLUMN_SELECTED_FOR_DISPLAY);
        	return false;	
        }
        var firstflag=true;
        for ( var i = 0; i < len ; i++ )
        {
            if ( targetForm.duplicateCriteriaRight.options[i].selected )
            {
				var deaultText = targetForm.duplicateCriteriaRight.options[i].text;
				var firstChar = deaultText[0];
				if(firstChar == "*")
				{
					flag=true;
					if(firstflag){
						defaultcolumns=deaultText;
						firstflag=false;
					}else{
					defaultcolumns=defaultcolumns+", "+deaultText;
					}	
					continue;
				}
				appendOption(targetForm.duplicateCriteriaLeft, targetForm.duplicateCriteriaRight.options[i].text, targetForm.duplicateCriteriaRight.options[i].value);
                targetForm.duplicateCriteriaRight.options[i].text = "Empty";
            }
        }
        
        for ( i = 0; i < targetForm.duplicateCriteriaRight.options.length ; i++ ) 
        {
			if ( targetForm.duplicateCriteriaRight.options[i].text == "Empty" ) 
            {
                targetForm.duplicateCriteriaRight.options[i] = null;
				--i;
			}
		}
        if(flag){
        	alert("Default columns("+defaultcolumns +") cannot be removed from the configured columns list.");//P_B_50639
        	return false;
        }
	}

	function resetForm()
	{
		document.duplicateCriteriaForm.reset();
	}

	function submitForm(){
		var targetForm = document.duplicateCriteriaForm;
		var len = targetForm.duplicateCriteriaRight.options.length;
		
		var fieldCount = -1;
            
		
		for (var i = 0; i < len; i++)
		{
			var deaultText = targetForm.duplicateCriteriaRight.options[i].text;
			var firstChar = deaultText[0];
			 var hiddenCriteriaId = document.createElement("input");
				hiddenCriteriaId.setAttribute("type", "hidden");
				hiddenCriteriaId.setAttribute("name", "tabularSectionDisplayColumn_"+(++fieldCount)+"displayId");
				hiddenCriteriaId.setAttribute("value", targetForm.duplicateCriteriaRight.options[i].value);
				targetForm.appendChild(hiddenCriteriaId);
			if(firstChar == "*")
			{
				document.getElementById("isSelected_"+targetForm.duplicateCriteriaRight.options[i].value).value="2";
			}
			else{
			document.getElementById("isSelected_"+targetForm.duplicateCriteriaRight.options[i].value).value="1";
			}
			document.getElementById("orderSequence_"+targetForm.duplicateCriteriaRight.options[i].value).value=20+i;
		}

		len = targetForm.duplicateCriteriaLeft.options.length;
		for (var i = 0; i < len; i++)
		{
			var hiddenCriteriaId = document.createElement("input");
			hiddenCriteriaId.setAttribute("type", "hidden");
			hiddenCriteriaId.setAttribute("name", "tabularSectionDisplayColumn_"+(++fieldCount)+"displayId");
			hiddenCriteriaId.setAttribute("value", targetForm.duplicateCriteriaLeft.options[i].value);
			targetForm.appendChild(hiddenCriteriaId);
			document.getElementById("isSelected_"+targetForm.duplicateCriteriaLeft.options[i].value).value="0";
			document.getElementById("orderSequence_"+targetForm.duplicateCriteriaLeft.options[i].value).value=100+i;
		}
		
		//targetForm.eventType.value="create";
		targetForm.submit();
             
	}
	  
	function closeWindow() {
		if (parent.$.fn.colorbox) {
			parent.$.fn.colorbox.close();
		} else {
			window.close();
		}
	}
	function showDisplayMessage() {
		document.getElementById("displayMsg").style.display = 'none';
	}
<%if(!"".equals(configureSummary) && "yes".equals(configureSummary)){%>
	document.getElementById("displayMsg").style.display='';
	setTimeout('showDisplayMessage()',8000 );
	<%}%>
</script>

