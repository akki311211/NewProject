<%--
P_Enh_Form_Builder_Option_Sequence
In order to change the sequence of option added through Form Builder(Only for custom Field).
@author Souarbh Singh
 --%> 

<%@page import="com.appnetix.app.util.Constants"%>
<%@page import="com.appnetix.app.util.LanguageUtil"%>
<%@page import="com.appnetix.app.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>

<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkDelete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>


<% 
	Map<String, String> optionOrderMap = (Map)request.getAttribute("optionOrderMap");
	String fromSave = (String)request.getAttribute("fromSave");
	String formId = (String)request.getAttribute("formId");
	String moduleId = (String)request.getAttribute("moduleId");
%>
<html>
<%if("yes".equals(fromSave)) { %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad=closeWindow()>
<%}else{ %>
<body topmargin="0" leftmargin="0">
<%} %>
<%if(!StringUtil.isValidNew(fromSave)) { %>
<form name="changeOptionSequenceForm" action=""  method="post" <%=Constants.FORM_ENCODING%>>         <!-- made post request to handle bulk data in dropdown-->
<input type="hidden" name="modifySequence" value="">
<input type="hidden" name="fieldName" value="<%=(String)request.getAttribute("fieldName")%>">
<input type="hidden" name="tableAnchor" value="<%=(String)request.getAttribute("tableAnchor")%>">
<input type="hidden" name="mapSize" value="<%=optionOrderMap.size()%>">
<input type="hidden" name="formID" value="<%=formId%>">
<input type="hidden" name="moduleId" value="<%=moduleId%>">
<%	
	for ( int i = 1; i <= optionOrderMap.size(); i++ ) 
	{
%>
		<input type="hidden" name="sequence_<%=i%>optionId">
		<input type="hidden" name="sequence_<%=i%>orderNo">
<%
	}
%>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr valign="bottom">
		</tr>
		<%		
		Set entrySet = optionOrderMap.entrySet();
		Iterator iterator = entrySet.iterator();
		String key = null;
		String value = null;
		%>
		
		<tr>
			<td  width="100%" >
				<table border = "0" width = "100%" cellspacing="0" cellpadding="0">
 					 <tr colspan="3"> 
    					<td style="padding-top:10px">
      						<table width="100%" border="0" cellspacing="0" cellpadding="0">
        						<tr>
	    							<td class='fline hText18theme pt pb5' width="11%" nowrap="nowrap">&nbsp;<%=LanguageUtil.getString("Change Option Sequence",(String)session.getAttribute("userLanguage"))%></td>
	    						</tr>
      						</table>
    					</td>
  					</tr>
  				</table> 
  			</td>
  		</tr>
  		
  		<tr>
  			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="15"></td>
  		</tr>
  		
					<tr>
						<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">  
     
     
                  <tbody><tr class="">
                  <td valign="top">
							<select name = "changeSequenceOrder" class="multiList" size = "8">
							<%while ( iterator != null && iterator.hasNext())
							{
								Map.Entry entry = (Map.Entry) iterator.next();
								key = (String) entry.getKey();
								value = (String) optionOrderMap.get(key);
							%>
								<option value ="<%=key%>"><%=LanguageUtil.getString(value)%></option>
							<% } %>
							</select>
						</td>
						<td width = "100%" valign = "middle" style="padding-left:10px;"> 
                                <table width = "100%" cellspacing="0" cellpadding="0" border="0" height = "125">
                                  <tr> 
                                    <td valign = "middle"  class="text"> <a href = "javascript:moveToTop();"> 
                                      <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveToTop1.gif" title = "<%=LanguageUtil.getString("Move to Up",(String)session.getAttribute("userLanguage"))%>" border = "0"> 
                                      </a> </td>
                                  </tr>
                                  <tr> 
                                    <td valign = "middle" class="text"> <a href = "javascript:moveUp()"> 
                                      <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveUp1.gif" title = "<%=LanguageUtil.getString("Move Up",(String)session.getAttribute("userLanguage"))%>" border = "0"> 
                                      </a> </td>
                                  </tr>
                                  <tr>
									<td>&nbsp;</td>
					  			</tr>
                                  <tr> 
                                    <td valign = "middle"  class="text"> <a href = "javascript:moveDown();"> 
                                      <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveDown1.gif" title = "<%=LanguageUtil.getString("Move Down",(String)session.getAttribute("userLanguage"))%>" border = "0"> 
                                      </a> </td>
                                  </tr>
                                  <tr> 
                                    <td valign = "middle" class="text"> <a href = "javascript:moveToBottom();"> 
                                      <img src = "<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/MoveToBottom1.gif" title = "<%=LanguageUtil.getString("Move To Bottom",(String)session.getAttribute("userLanguage"))%>" border = "0"> 
                                      </a> </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            
   
                       </tbody></table>
					</tr>
					<tr>
  			<td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="10"></td>
  		</tr>
	
		<tr>
			<td height="35">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr> 
						<td><input type = "button" value = "<%=LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"))%>" onClick = "javascript:submitForm();" class="cm_new_button" name="button">
						<input type = "button" class = "cm_new_button" name = "cancelButton" value="<%=LanguageUtil.getString("Close",(String)session.getAttribute("userLanguage"))%>" onClick="parent.$.fn.colorbox.close()"></td>
					</tr>
				</table>
       		</td>
   		</tr>
	</table>
</form>
<%}else{ %>
<table width="100%">
	<tr valign="middle">
		<td  align='center' class='crvBox2-bg'><img src = '<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/loading-Image.gif'></td>
	</tr>
</table>
<%}%>
</body>
</html>

 <script language = "javascript">
 function moveUp() 
	{
		var formVar = document.changeOptionSequenceForm.changeSequenceOrder;
		var selectIndex = formVar.selectedIndex;

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
		}

		return;
	}

	function moveDown() 
	{
		var formVar = document.changeOptionSequenceForm.changeSequenceOrder;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		
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
		}

		return;
	}

	function moveToBottom() 
	{
		var formVar = document.changeOptionSequenceForm.changeSequenceOrder;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		
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
		var formVar = document.changeOptionSequenceForm.changeSequenceOrder;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;

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

	function submitForm() 
	{
		var f = document.changeOptionSequenceForm;
<%		for (int i = 1; i<= optionOrderMap.size();i++) {
%>			optionId  = document.changeOptionSequenceForm.changeSequenceOrder.options[<%=i-1%>].value;
			f.sequence_<%=i%>optionId.value =  optionId; 
			f.sequence_<%=i%>orderNo.value = <%=i%>;
<% } %>   
		f.modifySequence.value = "yes";
		f.action = "changeOptionSequence";
		f.submit();

	}
	
	function closeWindow() 	
	{
		if(parent.$.fn.colorbox && parent.document.blankForm)
			{
			parent.document.blankForm.formID.value = "<%=formId%>";
			parent.document.blankForm.formNames.value = "<%=formId%>";
			parent.document.blankForm.moduleId.value = "<%=moduleId%>"; 
			parent.document.blankForm.submit();
		}
		else if (window.opener && window.opener.document.blankForm) 	
		{
			window.opener.document.blankForm.formID.value = "<%=formId%>";
			window.opener.document.blankForm.formNames.value = "<%=formId%>";
			window.opener.document.blankForm.moduleId.value = "<%=moduleId%>"; 
			window.opener.document.blankForm.submit();
		}
		if(parent.$.fn.colorbox)
		{
			parent.$.fn.colorbox.close();
		} else
		{
			window.close();
		}		
	}	
	</script>