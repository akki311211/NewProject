<%--
 - $Author						:  $Abhishek gupta
 - $Version						:  $1.1
 - $Date						:  $MAr 26,2012
 P_FS_Enh_BuilderForm		16Dec2013		Naman Jain				FormBuilder in Franchise Sales
 --%>
<%--
----------------------------------------------------------------------------------------------------------
Version No.		Date		By			Against		Function Changed	Comments
-----------------------------------------------------------------------------------------------------------
P_B_CM_35418   26th march 2014		    Nancy Goyal				GUI issue
P_B_CM_070714  7th July 2014            Swati Garg              GUI issue
P_B_CM_41811   18 July 2014             Rashmi Shakya           Alert note for special characters is not there on the colorbox
-----------------------------------------------------------------------------------------------------------
--%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.lang.*"%>
<%@ page import = "com.home.builderforms.forms.*"%>
<%@ page import = "com.home.builderforms.builderforms.*"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.util.database.*"%>
<%@ page import = "com.appnetix.app.util.information.*"%>
<%@ page import = "com.home.builderforms.*"%>
<%String pathString="/static"+Constants.STATIC_KEY + "/javascript/string.js";
System.out.println("pathString " + pathString);%>
<jsp:include page = "<%=pathString%>"/>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/checkNumeric.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.bubblepopup.v2.3.1.min.js"> </script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/jquery/jquery.bubblepopup.v2.3.1.css" />    

<jsp:useBean id="builderForm"	class="com.appnetix.app.control.web.webimpl.BuilderFormWebImpl" scope="session"/>
<jsp:useBean id="builderWebForm"	class="com.appnetix.app.portal.FormCustomization.BuilderWebFormBean" scope="session"/><%--BB_Naming_Convention --%>
	
<%
try {
String moduleId = request.getParameter("moduleId"); //P_FS_Enh_BuilderForm
String sTabModuleName= request.getParameter(FieldNames.MODULE);
String sAction = request.getParameter("action");
String sFormID = request.getParameter("formID");
String sFormSectionName = request.getParameter(BuilderFormFieldNames.SECTION_NAME);
String sFormSectionValue = request.getParameter(BuilderFormFieldNames.SECTION_VALUE);
String sFormSection = request.getParameter(BuilderFormFieldNames.SECTION);
String sFormType = request.getParameter(BuilderFormFieldNames.TYPE);
String sFormOrder = request.getParameter(BuilderFormFieldNames.ORDER);
int regularSectionCount=0;
if(StringUtil.isValidNew(request.getParameter("regularSectionCount"))){
regularSectionCount=Integer.parseInt(request.getParameter("regularSectionCount"));
}
String formButtonTag = "";
FieldMappings sectionMapping = null;
HeaderMap[] hMap = null;

System.out.println("sAction== " + sAction );
System.out.println("sFormID== " + sFormID );
System.out.println("sFormSectionName== " + sFormSectionName );
System.out.println("sFormSectionValue== " + sFormSectionValue );
System.out.println("sFormSection== " + sFormSection );
System.out.println("sFormType== " + sFormType);
System.out.println("sFormOrder== " + sFormOrder);

String actionFromSectionPage = request.getParameter("actionFromSectionPage");
System.out.print("actionFromSectionPage== " + actionFromSectionPage );

String sectionName = null;
String sectionValue = null;
String section = null;
String type = null;
int order = 0;
if(StringUtil.isValidNew(sFormOrder)) {
	order = Integer.parseInt(sFormOrder);
}
boolean flag = false;

if(StringUtil.isValidNew(actionFromSectionPage)) {
	request.setAttribute(BuilderConstants.TABLE_ID,"2");
	request.setAttribute("form-id",sFormID);
	request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
	request.setAttribute(FieldNames.MODULE,sTabModuleName);
	System.out.print("actionFromSectionPage== call builder start ..");
	flag = builderForm.setBuilderFormAddOrUpdateSection(request);
	System.out.print("actionFromSectionPage== call builder end ..");
}
if(!StringUtil.isValidNew(actionFromSectionPage)) {
	request.setAttribute(BuilderConstants.TABLE_ID,"2");
	request.setAttribute("form-id",sFormID);
	request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
	sectionMapping = builderForm.getFimFormBuilderSectionMapping(request);
	if(sectionMapping != null) {
		hMap = sectionMapping.getHeaderMap();
	}
	if("modify".equals(sAction)) {
		for(int jj=0;jj< hMap.length;jj++) {
			//for(HeaderMap s:hMap) {
				//HeaderField hFld = s.getHeaderFields();
				HeaderField hFld = hMap[jj].getHeaderFields();
			if(StringUtil.isValidNew(sFormSectionName) && sFormSectionName.equals(hMap[jj].getName())) {
				sectionName = hMap[jj].getName();
				sectionValue = hMap[jj].getValue();
				order = hMap[jj].getOrder();
				section = hFld.getSectionField();
				type = hFld.getTypeField();
				break;
			}
		}
	}
	if(!StringUtil.isValid(sectionName)) {
		sectionName = "";
	}
	if(!StringUtil.isValid(sectionValue)) {
		sectionValue = "";
	}
	if(!StringUtil.isValid(section)) {
		section = "";
	}
	if(!StringUtil.isValid(type)) {
		type = "0";
	}
}
//(legalDetails = (sectionName = legalDetails,sectionValue = Legal Violation Details,order = 0,section = 1,tableAnchor = fimLegalViolation)
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
		document.fieldModify.<%=BuilderFormFieldNames.SECTION_VALUE%>.focus();
		var height = document.getElementById("containerDiv").offsetHeight;
		resizeTo(430,height+inc);
	}*/
$(document).ready(function(){
	document.fieldModify.<%=BuilderFormFieldNames.SECTION_VALUE%>.focus();
	parent.$.fn.colorbox.vkResize($(document).width(), $(document).height()+20);
});
	
<%}%>


	function validate() {
		var f = document.fieldModify;
		var name = f.<%=BuilderFormFieldNames.SECTION_NAME%>.value;
		var value = f.<%=BuilderFormFieldNames.SECTION_VALUE%>.value;
		if (isEmpty(trim(value))) {
        	FCI.Alerts(FCLang.SECTION_NAME);
	        //alert("Please enter Section Name.");
            f.<%=BuilderFormFieldNames.SECTION_VALUE%>.focus();
		    return false;
		}
		
		if (trim(value) != "") 
		{
			var firstChar = trim(value).substring(0, 1);
			var regExp = new RegExp("[a-zA-Z]");
			if (!firstChar.match(regExp)) 
			{
				FCI.Messages(FCLang.DISPLAY_NAME_MUST_BEGIN_WITH_A_LETTER);
				f.<%=BuilderFormFieldNames.SECTION_VALUE%>.focus();
				return false;
			}
		}
		
		if (trim(value) != "") {
			var secChar = trim(value);
			var regExp = new RegExp("[!|@%#$^&*_:,;<>\\\\]");
			if (secChar.match(regExp)) {
				//FCI.Messages(FCLang.SECTION_NAME_CAN_NOT_CONTAIN_SPECIAL_CHARACTERS);
				//alert("Section Name cannot contain special charcters.");
				FCI.Messages(FCLangAdmin.SECTION_NAME_CANNOT_CONTAIN_SPECIAL_CHARACTERS);
				f.<%=BuilderFormFieldNames.SECTION_VALUE%>.focus();
				return false;
			}
		}
		var spacefix = / /gi;
		value=value.replace(spacefix,"");
		<%if(!"modify".equals(sAction)){%>  //BB-20150203-259 (Section Rename changes) 
		name = "bSec_" + value.toLowerCase();
		<%}%>  
		<%
		if(hMap != null) 
			for(int ii=0;ii< hMap.length;ii++) {
				HeaderField hFld = hMap[ii].getHeaderFields();
				String	varSectionName = hMap[ii].getValue();
				if(varSectionName.contains("'")){
					varSectionName=StringUtil.encodeEscapeCharacter(varSectionName, '\'');
                }
                if(varSectionName.contains("\"")){
                	varSectionName=StringUtil.encodeEscapeCharacter(varSectionName, '\"');
                }
			%>
			//BUG_48647
			if(checkDuplicateName(f.<%=BuilderFormFieldNames.SECTION_VALUE%>.value) == '<%=varSectionName%>') {
				if(!(<%=StringUtil.isValid(sAction)%> && <%=sAction.equalsIgnoreCase("modify")%> && trim(f.previousSectionValue.value) == '<%=varSectionName%>')){					
					alert("Section Name already exists.");
					return false;
				}
			}
		<% } %>
			
			name = replaceSpecialChar(name);	
			f.<%=BuilderFormFieldNames.SECTION_NAME%>.value = name;
			f.<%=BuilderFormFieldNames.SECTION%>.value = name;

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
<html>
<%if(StringUtil.isValidNew(actionFromSectionPage)) { %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad=closeWindow()>
<% } else {  %>
<body topmargin="0" leftmargin="0">
<% } %>

<title><%=LanguageUtil.getString("Section Configuration",(String)session.getAttribute("userLanguage"))%></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<%if(!StringUtil.isValidNew(actionFromSectionPage)) { %>
<div id="containerDiv">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<form name="fieldModify" autocomplete='off' action="builderFormSection" method=post onsubmit="return validate()" <%=Constants.FORM_ENCODING%>>
<input type=hidden name='moduleId' value='<%=moduleId%>'> <!-- P_FS_Enh_BuilderForm -->
<input type=hidden name='actionFromSectionPage' value='true'>
	<tr>
	<td colspan="2" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="9" ></td>
                <td ></td>
                <td ></td>
              </tr>
            </table></td>
            <tr>
	 <tr width="100%">
	 </tr>
	 <tr>
	 		
	 		<td class="bText11gr" align="right" width="100%" height="30" style="padding-right:25px"><%=LanguageUtil.getString("Fields marked with",(String)session.getAttribute("userLanguage"))%>
	 		<span class="urgent_fields"> *</span> <%=LanguageUtil.getString("are mandatory.",(String)session.getAttribute("userLanguage"))%></td>
          </tr>
        </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="clear:both;">
		  <tr>
   				<td height="10" colspan="4"/>
			</tr>
			<tr>
				
			</tr>
			<tr> 
   			<td>
   			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="5"></td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<%
			    if (sAction.equals("delete")) {
			%>
			
			<%
				} else {
					if(sectionMapping != null) {
						
					
						String title = "";
						if("add".equals(sAction)){
						title = ""+LanguageUtil.getString("Add Section",(String)session.getAttribute("userLanguage"))+"";	
						formButtonTag = ""+LanguageUtil.getString("Add",(String)session.getAttribute("userLanguage"))+"";
						}
						else if("modify".equals(sAction)){ //BB-20150203-259 (Section Rename changes) starts 
						title = ""+LanguageUtil.getString("Modify Section",(String)session.getAttribute("userLanguage"))+"";
						formButtonTag = ""+LanguageUtil.getString("Save",(String)session.getAttribute("userLanguage"))+"";
						}
						//BB-20150203-259 (Section Rename changes) ends 
			%>
			<tr> 
        	<td width = "100%" colspan="3" valign="top" height="20" class="fline hText18theme pt pb5" nowrap="nowrap" align="left">
	    	<%=title%>
	    	</td>
	 
  	</tr>
			
						<!--<tr class="tb_hdr_b">
							<td colspan="2"><%=title%></td>
						</tr>
						--><tr>
							<input type=hidden name="formID" value='<%=sFormID%>'>
							<input type=hidden name="action" value='<%=sAction%>'>
							<input type="hidden" name="<%=BuilderFormFieldNames.SECTION_NAME%>" value="<%=sectionName%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.IS_BUILD_SECTION%>" value="false">
							<input type="hidden" name="<%=BuilderFormFieldNames.SECTION%>" value="<%=section%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.TYPE%>" value="<%=type%>">
							<input type="hidden" name="<%=BuilderFormFieldNames.ORDER%>" value="<%=order%>">
							<input type="hidden" name="module" value="<%=sTabModuleName%>">
							<input type="hidden" name="previousSectionValue" value="<%=PortalUtils.filterValue(sectionValue)%>">
								
							<td class="TextLbl_b" width="30%" align="right"><span class="urgent_fields">*</span><%=LanguageUtil.getString("Section Name",(String)session.getAttribute("userLanguage"))%> :</td> <%--P_B_CM_35418 --%>
							<td width="70%">
								<input maxlength="255" type="text" name="<%=BuilderFormFieldNames.SECTION_VALUE%>" value="<%=PortalUtils.filterValue(sectionValue)%>" class="fTextBox" style="width: 250px;">
							</td>
						</tr>
						<!--P_Enh_FormBuilder_Tabular_Section starts-->
						<%
						if("add".equals(sAction)  && ("fs".equals(sTabModuleName) || "fim".equals(sTabModuleName) || "mu".equals(sTabModuleName) || "area".equals(sTabModuleName) || "cm".equals(sTabModuleName) || "lead".equals(sTabModuleName) || "account".equals(sTabModuleName) || "opportunity".equals(sTabModuleName)) && !("43".equals(sFormID)|| "45".equals(sFormID) || "46".equals(sFormID)|| "48".equals(sFormID)|| "44".equals(sFormID) || "47".equals(sFormID) || "93".equals(sFormID)|| "94".equals(sFormID) || "95".equals(sFormID)|| "96".equals(sFormID)|| "97".equals(sFormID) || "98".equals(sFormID) || "83".equals(sFormID) || "84".equals(sFormID) || "85".equals(sFormID) || "86".equals(sFormID) || "87".equals(sFormID) || "88".equals(sFormID) || "89".equals(sFormID) || "90".equals(sFormID)  || "91".equals(sFormID)  || "92".equals(sFormID))){%>
						<tr>
						<td class="TextLbl_b" width="30%" align="right"><%=LanguageUtil.getString("Section Type",(String)session.getAttribute("userLanguage"))%> :</td>
						<td width="70%" class="text">
						<input type="radio" checked="" value="no" name="isTabularSection">
							<%=LanguageUtil.getString("Regular",(String)session.getAttribute("userLanguage"))%>
						<input type="radio" value="yes" name="isTabularSection">
							<%=LanguageUtil.getString("Tabular",(String)session.getAttribute("userLanguage"))%>
							<a id="link3" href="javascript:void(0)">
								<img width="16" hspace="4" height="14" border="0" align="bottom" id="link1" class="editImage" name="link1" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/theme/Blue/helpIcon.gif">
							</a>
							
							
						</td>
						</tr>
						<%} %>
						<!--P_Enh_FormBuilder_Tabular_Section ends-->
						</table>
					</td>
					<td width="5"></td>
				</tr>
			
				
			<%		}
				}
			
			
			%>
<!-- P_B_CM_41811 starts-->
			 <tr>
				<% String sCharVal = "&nbsp;**"+LanguageUtil.getString("Avoid using special characters like")+" ! @ % # $ ^ & * _ : , ; < > \\"; %>
	            <td class="TextLbl_b" colspan="2"><font color="#ff6600"><i><%=sCharVal%></i></font>
				  </td>
							 </tr>
			<!-- P_B_CM_41811 ends-->
			</table>

		</td>
		 </tr>

		</table></td></tr>

	<tr height="20">
	<td colspan="2"/>
	</tr>
	<tr> 
	    <td  align="left" class="text"><input id="submitButton" name="submitButton" value="<%=formButtonTag%>" class="cm_new_button" type="submit"  style="margin-right:6px;padding-left:7px; " /><%-- P_B_CM_35418--%>
	       <input id="Button"  name="Button" value="<%=LanguageUtil.getString("Close")%>" class="cm_new_button" type="reset" style="margin-right:5px;" onclick="javascript:parent.$.fn.colorbox.close();" /></td>
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
         var bubbleThemePath = '<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/bubbleThemes/jquerybubblepopup-theme';
         
		function closeWindow() 	{
			if(parent.$.fn.colorbox && parent.document.blankForm){
				parent.document.blankForm.action.value = "<%=sAction%>";
				parent.document.blankForm.formID.value = "<%=sFormID%>";
				parent.document.blankForm.formNames.value = "<%=sFormID%>";
				<%if("modify".equals(sAction)){%> //BB-20150203-259 (Section Rename changes)  
				parent.document.blankForm.fimSection.value = "modifydone";
				<%}else{%>
				parent.document.blankForm.fimSection.value = "done";
				<%}%>
				parent.document.blankForm.moduleId.value = "<%=moduleId%>"; //P_FS_Enh_BuilderForm
				
				parent.document.blankForm.submit();
				
			}
			else if (window.opener && window.opener.document.blankForm) 	{
				window.opener.document.blankForm.action.value = "<%=sAction%>";
				window.opener.document.blankForm.formID.value = "<%=sFormID%>";
				window.opener.document.blankForm.formNames.value = "<%=sFormID%>";
				window.opener.document.blankForm.fimSection.value = "done";
				window.opener.document.blankForm.moduleId.value = "<%=moduleId%>"; //P_FS_Enh_BuilderForm
				
				window.opener.document.blankForm.submit();
			}
			if(parent.$.fn.colorbox){
				parent.$.fn.colorbox.close();
			}else{
			window.close();
			}		}
		
		$('#link1').CreateBubblePopup({
			position: 'right', align: 'middle',
			width:'300px',
			innerHtml:'<p style="margin:0px; padding:0px;font-family:Arial,Helvetica,sans-serif;font-size:12px;"><%=LanguageUtil.getString("Tabular section allow multiple records against a single Record of a Tab.",(String)session.getAttribute("userLanguage"))%></p>',  
			innerHtmlStyle: {
				background:'#ffffff',
				color:'#666666',
			},

			themeName: 	'<%=(String)session.getAttribute("userTheme")%>',
			themePath: 	bubbleThemePath
		});
		
		
</script>
	
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>