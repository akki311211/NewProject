<%--
 - $Author						:  $Abhishek gupta
 - $Version						:  $1.1
 - $Date						:  $May 9,2012
 --%>
<%--
----------------------------------------------------------------------------------------------------------
Version No.		Date		By			Against		Function Changed	Comments
-----------------------------------------------------------------------------------------------------------
PW_B_26809		23 Aug 2013		Anjali Pundir			basebuild>admin>manage form generator>document> If a document contains entry for any user. User could not modify that document type.
P_FS_Enh_BuilderForm		16Dec2013		Naman Jain		FormBuilder in Franchise Sales 
P_B_ADMIN_CM_35369 24 march 2014 Chetan Rawat    	admin>cm>form generator>add document>changing urgent fields tag as per user requirement.
P_B_CM_35418   26th march 2014		    Nancy Goyal				GUI issue	 			
P_B_CM_35902   28th march 2014		    Vishal Lodha                        GUI issue
P_Admin_CM_B_38407    20 May 2014       Ikramuddin              Add special characters label 	 
P_B_CM_070714  7th July 2014            Swati Garg              GUI issue			
P_B_FB_42702		08 Aug 2014			Prateek Sharma			Admin > Franchise Sales > Manage Form Generator > Add Document > Select option only for document > subject field should not be displayed.
-----------------------------------------------------------------------------------------------------------
--%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import = "com.home.builderforms.forms.*"%>
<%@ page import = "com.home.builderforms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.database.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "com.home.builderforms.*"%>
<%@ include file = "builderScriptCss.jsp" %> 
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>
<%--<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/string.js"></script>--%>

<jsp:useBean id="builderForm"	class="com.home.builderforms.BuilderFormWebImpl" scope="session"/>
<jsp:useBean id="builderWebForm"	class="com.home.builderforms.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>
	
<%
try {

String sAction = request.getParameter("action");
String sFormID = request.getParameter("formID");
//String sFormDocTitle = request.getParameter(BuilderFormFieldNames.DOCUMENT_TITLE);
String isTabularSection=request.getParameter("isTabularSection");
String tabularSectionTableName=request.getParameter("tabularSectionTableName");
String tabularSectionTableDBName=request.getParameter("tabularSectionTableDBName");
String sFormSection = request.getParameter(BuilderFormFieldNames.SECTION);
String sFormOrder = request.getParameter(BuilderFormFieldNames.ORDER);
String sTableName = request.getParameter(BuilderFormFieldNames.TABLE_NAME);
String docName = request.getParameter(BuilderFormFieldNames.FIELD_NAME);
boolean docOptionDisable = builderForm.isDataExistForFimDocument(docName,true);//PW_B_26809
String formButtonTag = "";
FieldMappings sMapping = null;
HeaderMap[] hMap = null;

String actionFromSectionPage = request.getParameter("actionFromSectionPage");
System.out.print("actionFromSectionPage== " + actionFromSectionPage );

String sectionName = null;
String sectionValue = null;
String section = null;
String type = null;
int order = 0;
boolean flag = false;
String docTitle = "";
String sFormDocTitle = "";
String sFormDocLabel = null;
String docLabel = "Document";
String docOption = "0";
String moduleShortName = request.getParameter("moduleShortName"); //P_FS_Enh_BuilderForm

int count = 0;
if(StringUtil.isValidNew(actionFromSectionPage)) {
	request.setAttribute(BuilderConstants.TABLE_ID,"2");
	request.setAttribute("form-id",sFormID);
	request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
	flag = builderForm.setBuilderFormAddOrUpdateDocument(request);
}
if(!StringUtil.isValidNew(actionFromSectionPage)) {
	sMapping = builderForm.getFimFormBuilderMapping(sTableName);
	if(sMapping != null) {
		hMap = sMapping.getHeaderMap();
	}
	 if(hMap != null && hMap.length > 0)
	     for(int i=0;i < hMap.length;i++) {
            HeaderMap s= hMap[i];
            HeaderField hFld = s.getHeaderFields();
            Documents[] docs =  hFld.getDocuments();
            if(docs != null && docs.length > 0) {
                DocumentMap[] docMaps = docs[0].getDocumentMaps();
                if(docMaps != null && docMaps.length > 0) {
                    count = docMaps.length;
                    for(int jj=0;jj < docMaps.length;jj++) {
                    	if((docMaps[jj].getDocumentFieldMap()).get("name").equals(docName)){
                    		docOption     = (String)(docMaps[jj].getDocumentFieldMap()).get("doc-option");
                    		if(docOption == null) {
                    			docOption = "0";
                    		}
                    		sFormDocTitle = (String)(docMaps[jj].getDocumentFieldMap()).get("title-display");
                    		sFormDocLabel = (String)(docMaps[jj].getDocumentFieldMap()).get("doc-display");
                    	}
                    }
                }
            }
     	}

}
if(StringUtil.isValid(sFormDocTitle)){
	docTitle = sFormDocTitle;
	//BUG_26164 Starts
	if(sFormDocTitle != null && sFormDocTitle.contains("'")){
		sFormDocTitle=StringUtil.encodeEscapeCharacter(sFormDocTitle, '\'');
	}
	if(sFormDocTitle != null && sFormDocTitle.contains("\"")){
		sFormDocTitle=StringUtil.encodeEscapeCharacter(sFormDocTitle, '\"');
	}
}
if(StringUtil.isValid(sFormDocLabel)){
	docLabel = sFormDocLabel;
	if(sFormDocLabel != null && sFormDocLabel.contains("'")){
		sFormDocLabel=StringUtil.encodeEscapeCharacter(sFormDocLabel, '\'');
	}
	if(sFormDocLabel != null && sFormDocLabel.contains("\"")){
		sFormDocLabel=StringUtil.encodeEscapeCharacter(sFormDocLabel, '\"');
	}
}
//BUG_26164 Ends
System.out.println("sAction== " + sAction);
System.out.println("sTableName== " + sTableName);
System.out.println("sFormDocTitle== " + sFormDocTitle);
System.out.println("sFormDocLabel== " + sFormDocLabel);
System.out.println("sFormSection== " + sFormSection);
System.out.println("docName== " + docName);
%>
<script language ="javaScript">
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
<%if(!StringUtil.isValidNew(actionFromSectionPage)) { %>
/*window.onload = function(){
	var height = document.getElementById("containerDiv").offsetHeight;
	resizeTo(460,height+inc);
}*/

$(document).ready(function(){
	parent.$.fn.colorbox.vkResize($(document).width(), $(document).height()+20);
	//PW_B_26809 Starts
	var filetype = document.getElementsByName("docOption");
	if(<%=docOptionDisable%>)
	{
		filetype[0].disabled=true;
		filetype[1].disabled=true;
	}
	//PW_B_26809 Ends
});
	
<%}%>

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
		var value = f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.value;
		var valueL = f.<%=BuilderFormFieldNames.DOCUMENT_LABEL%>.value;
		var filetype = document.getElementsByName("docOption");
		if (filetype[0].checked) {
			if (isEmpty(trim(value))) {
//		        alert("Please enter Subject.");
                        FCI.Messages(FCLangAdmin.PLEASE_ENTER_SUBJECT);
	            f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.focus();
			    return false;
			}
		}
		if (isEmpty(trim(valueL))) {
//	        alert("Please enter Document Label.");
                FCI.Messages(FCLangAdmin.PLEASE_ENTER_DOCUMENT_LABEL);
            f.<%=BuilderFormFieldNames.DOCUMENT_LABEL%>.focus();
		    return false;
		}
		if (filetype[0].checked) {
			if (trim(value) != "") {
				var fieldChar = trim(value);
				var regExp = new RegExp("[!|@#$^&*_:,;<>\\\\]");
				if (fieldChar.match(regExp)) {
					//FCI.Messages(FCLang.DOCUMENT_TITLE_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
					
//					alert("Document Subject cannot contain special charcters.");
                                        FCI.Messages(FCLangAdmin.DOCUMENT_SUBJECT_CANNOT_CONTAIN_SPECIAL_CHARACTERS);
					f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.focus();
					return false;
				}
			}
		}
		if (trim(valueL) != "") {
			var fieldChar = trim(valueL);
			var regExp = new RegExp("[|!@#$^&*_:,;<>\\\\]");
			if (fieldChar.match(regExp)) {
				//FCI.Messages(FCLang.DOCUMENT_TITLE_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
//				alert("Document Label cannot contain special charcters.");
                            FCI.Messages(FCLangAdmin.DOCUMENT_LABEL_CANNOT_CONTAIN_SPECIAL_CHARACTERS);
				f.<%=BuilderFormFieldNames.DOCUMENT_LABEL%>.focus();
				return false;
			}
		}
		<%if(hMap != null && hMap.length > 0){
			String titleDisp = "";
			String labelDisp = "";
		     for(int i=0;i < hMap.length;i++) {
	            HeaderMap s= hMap[i];
	            HeaderField hFld = s.getHeaderFields();
	            Documents[] docs =  hFld.getDocuments();
	          
	            String sSection = request.getParameter("section");
	            if(docs != null && docs.length > 0) {
	            	if(sSection !=null && sSection.equals((docs[0].getDocumentSection()))){
	                DocumentMap[] docMaps = docs[0].getDocumentMaps();
	            	for(int ii=0;ii < docMaps.length;ii++) {
	            		titleDisp = (String)(docMaps[ii].getDocumentFieldMap()).get("title-display");
	            		labelDisp = (String)(docMaps[ii].getDocumentFieldMap()).get("doc-display");
	            		if(titleDisp != null && titleDisp.contains("'")){
	            			titleDisp=StringUtil.encodeEscapeCharacter(titleDisp, '\'');
	                    }
	                    if(titleDisp != null && titleDisp.contains("\"")){
	                    	titleDisp=StringUtil.encodeEscapeCharacter(titleDisp, '\"');
	                    }
	            		if(labelDisp.contains("'")){
	            			labelDisp=StringUtil.encodeEscapeCharacter(labelDisp, '\'');
	                    }
	                    if(labelDisp.contains("\"")){
	                    	labelDisp=StringUtil.encodeEscapeCharacter(labelDisp, '\"');
	                    }
	            		%>
	            	//BUG_46847
	            	if(filetype[0].checked && checkDuplicateName(value) == '<%=titleDisp%>'){
	            		<%if(!(StringUtil.isValid(sAction) && sAction.equalsIgnoreCase("modify") && StringUtil.isValid(sFormDocTitle) && sFormDocTitle.equals(titleDisp))){%>
//		            		alert("Subject already exist.");
                                    FCI.Messages(FCLangAdmin.SUBJECT_ALREADY_EXIST);
							f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.focus();
							return false;
						<%}%>
					}
	            	//BUG_46847
	            	if(checkDuplicateName(valueL) == '<%=labelDisp%>'){
	            		<%if(!(StringUtil.isValid(sAction) && sAction.equalsIgnoreCase("modify") && StringUtil.isValid(sFormDocLabel) && sFormDocLabel.equals(labelDisp))){%>
//		            		alert("Document Label already exist.");
                                        FCI.Messages(FCLangAdmin.DOCUMENT_LABEL_ALREADY_EXIST);
							f.<%=BuilderFormFieldNames.DOCUMENT_LABEL%>.focus();
							return false;
						<%}%>
					}
					
	           <%} }}
	    	}
	    }%>
<%-- 	if (filetype[1].checked) {
			f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.disabled=false;
			f.<%=BuilderFormFieldNames.DOCUMENT_TITLE%>.value = "";
		} --%>
		//PW_B_26809 Starts
		if(<%=docOptionDisable%>)
		{
			filetype[0].disabled=false;
			filetype[1].disabled=false;
		}
		//PW_B_26809 Ends
        f.submitButton.disabled=true;
        return true;
        //f.submit();
    }

	function trim(str) {
		str = str.replace(/^\s+/, '');
		for (var i = str.length - 1; i >= 0; i--) {
			if (/\S/.test(str.charAt(i))) {
				str = str.substring(0, i + 1);
				break;
			}
		}
		return str;
	}
		
	function isEmpty(s)
	{   
		return ((s == null) || (s.length == 0));
	}
	
</script>
<html>
<%if(StringUtil.isValidNew(actionFromSectionPage)) { %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad=closeWindow()>
<% } else {  %>
<body topmargin="0" leftmargin="0">
<% } %>

<title><%=LanguageUtil.getString("Field Configuration",(String)session.getAttribute("userLanguage"))%></title>
<%--<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">--%>
<%if(!StringUtil.isValidNew(actionFromSectionPage)) { %>
<div id="containerDiv">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<form name="fieldModify" autocomplete='off' action="builderFormDocument" method=post onsubmit="return validate();" <%=Constants.FORM_ENCODING%>>
<input type=hidden name='actionFromSectionPage' value='true'>
<input type=hidden name='moduleShortName' value='<%=moduleShortName%>'> <!-- P_FS_Enh_BuilderForm -->
<input type="hidden" name="isTabularSection" value="<%=isTabularSection%>">
<input type="hidden" name="tabularSectionTableDBName" value="<%=tabularSectionTableDBName%>">
<input type="hidden" name="tabularSectionTableName" value="<%=tabularSectionTableName%>">
	<tr>
	<td colspan="2" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <!--<td  valign="bottom">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="9" class="crvBox2-ltBg"></td>
                <td  nowrap="nowrap" class="crvBox2-ttBg">FIM <%=LanguageUtil.getString("Form",(String)session.getAttribute("userLanguage"))%></td>
                <td width="33" class="crvBox2-rtBg"></td>
              </tr>
            </table></td>
            
	 
	 		-->
	 		
	 		<td width="59%" align="right" class="bText11gr"  height="30" style="padding-right:25px"><%=LanguageUtil.getString("Fields marked with",(String)session.getAttribute("userLanguage"))%>
	 		<span class="urgent_fields"> *</span> <%=LanguageUtil.getString("are mandatory.",(String)session.getAttribute("userLanguage"))%></td>
          </tr>
        </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
		  <tr>
   				<td height="10" colspan="4"/>
			</tr>
			<tr>
			<%-- 	<td width="10" valign="top"><img width="10" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tl.png"></td>    			
				<td class="crvBox2-top"></td>		
				<td width="16" valign="top"><img width="16" height="12" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tr.png"></td>
		 --%>	</tr>
			<tr> 
   				<!-- <td class="crvBox2-left"  width="100%"/>
				<td class="crvBox2-bg">
 -->


			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="5"></td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<%
						String title = ""+LanguageUtil.getString("Add Document",(String)session.getAttribute("userLanguage"))+"";
						formButtonTag = ""+LanguageUtil.getString("Add",(String)session.getAttribute("userLanguage"))+"";
						if(StringUtil.isValid(sAction) && sAction.equalsIgnoreCase("modify")){
							title = ""+LanguageUtil.getString("Modify Document",(String)session.getAttribute("userLanguage"))+"";
							formButtonTag = ""+LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"))+"";
						}
			%>
							<input type=hidden name="formID" value='<%=sFormID%>'>
							<input type=hidden name="action" value='<%=sAction%>'>
							<input type="hidden" name="<%=BuilderFormFieldNames.IS_BUILD_SECTION%>" value="false">
							<input type="hidden" name="<%=BuilderFormFieldNames.SECTION%>" value="<%=sFormSection%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.ORDER%>" value="<%=count%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.TABLE_NAME%>" value="<%=sTableName%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.FIELD_NAME%>" value="<%=docName%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.DOCUMENT_TITLE%>_OLD" value="<%=StringEscapeUtils.escapeHtml(docTitle)%>">
							<tr> 
        	<td width = "100%" colspan="3" valign="top" height="20" class="fline hText18theme pt pb5" nowrap="nowrap" align="left">
	    	<%=title%>
	    	</td>
	 
  	</tr>
						<!--<tr class="tb_hdr_b">
							<td colspan="2"><%=title%></td>
						</tr>
						--><tr>
							<td width="25%" align="right" class="TextLbl_b"><%=LanguageUtil.getString("Document Option",(String)session.getAttribute("userLanguage"))%> :</td><%--P_B_CM_35418 --%>
							<td width="75%" class="text">
								<input type="radio" name="<%=BuilderFormFieldNames.DOCUMENT_OPTION%>" value="0" onClick="showHide()" <% if(docOption.equals("0")) { %> checked <%} %>><%=LanguageUtil.getString("Document with Subject",(String)session.getAttribute("userLanguage"))%>
								<input type="radio" name="<%=BuilderFormFieldNames.DOCUMENT_OPTION%>" value="1" onClick="showHide()" <% if(docOption.equals("1")) { %> checked <%} %>><%=LanguageUtil.getString("Document Only",(String)session.getAttribute("userLanguage"))%>		
							</td>
							
						</tr>
						<tr id="abhi">
							<td width="25%" align="right" class="TextLbl_b"><span class="urgent_fields" id="urgent_sub" >*</span><%=LanguageUtil.getString("Subject",(String)session.getAttribute("userLanguage"))%> :</td>  <%--P_B_CM_35418 --%><%-- P_B_ADMIN_CM_35369 --%>
							<td width="75%">
								<input maxlength="255" type="text" name="<%=BuilderFormFieldNames.DOCUMENT_TITLE%>" id="<%=BuilderFormFieldNames.DOCUMENT_TITLE%>" value="<%=StringEscapeUtils.escapeHtml(docTitle)%>" class="fTextBox" style="width: 250px;">
							</td>
						</tr>
						<tr id="abhi1" class="text_b">
							<td width="25%" align="right" class="TextLbl_b"><span class="urgent_fields">*</span><%=LanguageUtil.getString("Document Label",(String)session.getAttribute("userLanguage"))%> :</td><%--P_B_CM_35418 --%>
							<td width="75%">
								<input maxlength="255" type="text" name="<%=BuilderFormFieldNames.DOCUMENT_LABEL%>" value="<%=StringEscapeUtils.escapeHtml(LanguageUtil.getString(docLabel,(String)session.getAttribute("userLanguage")))%>" class="fTextBox" style="width: 250px;">
							</td>
						</tr>
						</table>
					</td>
					<td width="5"></td>
				</tr>
				<%--P_Admin_CM_B_38407 starts--%>
				<tr>
					<td width="1"></td>
						<% 
							String sCharVal = "**"+LanguageUtil.getString("Avoid using special characters like")+" ! @ % # $ ^ & * _ : , ; < > \\";
						%>
					<td class="TextLbl_b"><font color="#ff6600"><i><%=sCharVal%>.</i></font>
					</td>
					<td width="1"></td>
				</tr>
				<%--P_Admin_CM_B_38407 ends--%>
			<%	 %>
			</table>

		</td>
				<!-- <td class="crvBox2-right" width="100%"></td> -->
			</tr>
			<tr>
				<%-- <td align="left" valign="bottom"><img width="10" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-bl.png"></td>				
				<!-- <td class="crvBox2-bl"></td> -->				
				<td class="crvBox2-botom"></td>	
				<td align="right" valign="bottom"><img width="16" height="17" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-br.png"></td>				 --%>
            </tr>
		</table></td></tr>
		       <tr height="20">
         <td></td>
         </tr>

	<tr>
	    <td colspan="2" align="left" class="text"><input id="submitButton" name="submitButton" value="<%=formButtonTag%>" class="cm_new_button" type="submit" style="margin-right:5px;" />
	        <input id="Button" name="Button" value="<%=LanguageUtil.getString("Close")%>" class="cm_new_button" type="reset" style="margin-right:5px;" onclick="if(parent.$.fn.colorbox){ parent.$.fn.colorbox.close(); }else{window.close(); }"								
 /></td>
	</tr>
</form>
  
</table>
</div>
<%}else{%>
<table width="100%">
	<tr valign="middle">
		<td  align='center' class='crvBox2-bg'><img src = '<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/loading-Image.gif'></td>
	</tr>
</table>
<%}%>
</body>
</html>

<script language="javascript">
         //   window.resizeTo(600,300);
		function closeWindow() 	{
			if(parent.$.fn.colorbox && parent.document.blankForm){
				parent.document.blankForm.action.value = "<%=sAction%>";
				parent.document.blankForm.formID.value = "<%=sFormID%>";
				parent.document.blankForm.formNames.value = "<%=sFormID%>";
				parent.document.blankForm.fimDocument.value = "<%=sAction%>";
				
				parent.document.blankForm.submit();
			}
			else if (window.opener && window.opener.document.blankForm) 	{
				window.opener.document.blankForm.action.value = "<%=sAction%>";
				window.opener.document.blankForm.formID.value = "<%=sFormID%>";
				window.opener.document.blankForm.formNames.value = "<%=sFormID%>";
				window.opener.document.blankForm.fimDocument.value = "<%=sAction%>";
				
				window.opener.document.blankForm.submit();
			}
			if(parent.$.fn.colorbox){
				parent.$.fn.colorbox.close();
			}else{
			window.close();
			}
		}
                <%--P_B_CM_35902 Start --%>
                var fieldValue="";
                fieldValue = $('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').val();
                <%--P_B_CM_35902 End --%>
		function showHide() {
			var f = document.fieldModify;
			var filetype = document.getElementsByName("docOption");
			if (filetype[0] != undefined && filetype[0].checked) {
                        <%--P_B_CM_35902 Start --%>
                            if(fieldValue != undefined && fieldValue != ""){
                                $('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').val(fieldValue);
                            }
                            <%--P_B_CM_35902 End --%>
				$('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').removeAttr('readonly');
				<%--$('#urgent_sub').show(); --%>
				$('#abhi').show();						// For BUG_42702
			} else if (filetype[1] != undefined && filetype[1].checked) {
				<%--fieldValue = $('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').val();
				$('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').val('');
				$('#<%=BuilderFormFieldNames.DOCUMENT_TITLE%>').attr('readonly','readonly');
				 --%>		
				$('#abhi').hide();						// For BUG_42702
			}	
		}
		showHide();

		$(document).ready(function() {
			$(window).bind("load", function() {
		        var iW = $(document.body).width();
		        var iH = $(document.body).height();
		        parent.$.fn.colorbox.vkResize(600, 300);
			});
			var form = document.fieldModify;
		    for(var i = 0; i < form.length; i++){
			    if(!form[i].readonly != undefined && form[i].type != "hidden" && form[i].disabled != true && form[i].style.display != 'none' && form[i].type == 'text'){
		            form[i].focus();
		            return;
		        }
		    }
		});
		//BUG_46847_START
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
		//BUG_46847_END
</script>
	
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>