<%@ include file="/jsp/util/import.jsp"%>
<%@ page import = "com.appnetix.app.util.*"%>
<%@ page import = "com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil"%>
<%@ page import = "com.appnetix.app.util.database.*"%>
<%@ page import="com.appnetix.app.util.information.Info" %>
<%@ page import="com.appnetix.app.struts.EventHandler.EventType" %>
<%@ taglib uri="/artags" prefix="a" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery-1.6.1.js"></script> <!-- P_B_78421 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<script type="text/javaScript" src="<%=request.getContextPath()%>/javascript/jqueryPopUp.js"></script>
<link type="text/css" media="screen" rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/smartconnect/colorbox1.css" />
<%if("cm".equals((String)session.getAttribute ("menuName"))){ %>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/progressBar.js"></script> 
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/AmazonUpload.js"></script>
<%} %>
<%
try{
FieldMappings mappings 	= (FieldMappings)request.getAttribute("mappings");
String triggerFormNameForTabularSection=(String)request.getAttribute("triggerFormNameForTabularSection");
String conxtname	= request.getContextPath();
String sViewDetails = (String)request.getAttribute("viewDetails");
String encType = (String)request.getAttribute("encType");
String archiveFlag = (String)session.getAttribute ("forArchived");
String menuName= (String)session.getAttribute ("menuName");
String subMenuName= (String)session.getAttribute ("subMenuName");
String isMuTab=(String)request.getAttribute("isMuTab");
String addModifyUrl="addModifyTabularSectionDataFS";
String viewUrl="viewTabularSectionDataFS";
if("yes".equals(isMuTab)){
	 addModifyUrl="addModifyTabularSectionDataMU";
	 viewUrl="viewTabularSectionDataMU";
}else if("fim".equals(menuName)){
	 addModifyUrl="addModifyTabularSectionDataFIM";
	 viewUrl="viewTabularSectionDataFIM";
}else if("cm".equals(menuName)){
	 addModifyUrl="addModifyTabularSectionDataCM";
	 viewUrl="viewTabularSectionDataCM";
	 sViewDetails=request.getParameter("viewDetails");
}
Info dataInfo = (Info)request.getAttribute("INFOENTRYDETAIL");
if(dataInfo==null){
	dataInfo=(Info)request.getAttribute("dataInfo");
}
Info info = null;
String sleadId = (String)session.getAttribute ("leadID");
String displayFormat=MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT;
String menuBar = (String) session.getAttribute("menuBar");
String tableAnchor=(String)request.getAttribute("tableAnchor");
String leadId=(String)request.getAttribute(FieldNames.LEAD_ID);
String franchiseeNo=(String)request.getAttribute(FieldNames.FRANCHISEE_NO);
String tabPrimaryId=(String)request.getAttribute(FieldNames.TAB_PRIMARY_ID);
String idfieldValue=(String)request.getAttribute(FieldNames.ID_FIELD);
String mainIdFieldName=request.getParameter("mainIdFieldName");												//P_Enh_Multiple_Input_Tabular_Summary
String flag=request.getParameter("flag");
String mandatoryFlag = (String)request.getAttribute("tabular_mandatory");       //P_B_80593
String onLoadString="";
String action=(String)request.getAttribute("viewData");
if("display".equals(action))																				//P_Enh_Multiple_Input_Tabular_Summary starts
{
	tabPrimaryId = request.getParameter(FieldNames.TAB_PRIMARY_ID);
}																											//P_Enh_Multiple_Input_Tabular_Summary ends		
String fromDocDelete=request.getParameter("fromDocDelete");
String muID		= (String)session.getAttribute("muID");
String showEntityTabs = (String) session.getAttribute("showEntityTabs");
if("entity".equals(showEntityTabs)) {
	muID = (String) session.getAttribute("entityID");
}
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/siteIE.css" type="text/css">
<script>
    
    function deleteData()
    {
    	if(FCI.Confirms(FCLang.ARE_YOU_SURE_TO_DELETE_THIS_COMLIANCE))
    	{
    		document.deleteForm.submit();
    	}
    }
    function closeWindow() 	{
		if(parent.$.fn.colorbox){
			if(parent && parent.document.blankFormForOptOutAction) {
				parent.document.blankFormForOptOutAction.idField.value = '<%=idfieldValue%>';
				<%if("true".equals((String)session.getAttribute("isTabularFieldsConfigured"))) {%>								//P_Enh_Multiple_Input_Tabular_Summary starts
					parent.document.blankFormForOptOutAction.<%=FieldNames.FROM_WHERE%>.value = "tabularFieldsPage";
					parent.document.blankFormForOptOutAction.idFieldName.value = '<%=mainIdFieldName%>';
					parent.document.blankFormForOptOutAction.idVal.value = <%=tabPrimaryId%>;
					parent.document.blankFormForOptOutAction.from.value = '<%=request.getParameter("from")%>';
				<%}%>																											//P_Enh_Multiple_Input_Tabular_Summary ends
				parent.document.blankFormForOptOutAction.submit();
			}
			parent.$.fn.colorbox.close();
		}else{
		window.close();
		}
	}

    function checkValues(){
          return fimValidation("tabularSectionForm");
     }
    var divIDName="printReady";
    var cssForPrint="<link rel='stylesheet' href='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css' type='text/css'>";
	</script>
	<% String varincludefileP0 ="/static"+Constants.STATIC_KEY+"/javascript/printOutPut.js"; %>
	<jsp:include page = "<%=varincludefileP0 %>"/>
	<% String varincludefileP1 ="/static"+Constants.STATIC_KEY+"/javascript/printOut.js"; %>
	<jsp:include page = "<%=varincludefileP1 %>"/>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/Calendar1-82.js"></script>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/DateTimeMath.js"></script>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/dateUtility.js"></script>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/date.js"></script>
<script language="javascript1.2" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/cal2.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/cal1.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/cal_conf2.js"></script>
<script type="text/javaScript" src="<%=request.getContextPath()%>/javascript/jqueryPopUp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/js/jquery/jquery.colorbox.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/javascript/ajaxforcombos.js"> </script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<%@ include file = "/jsp/builderform/builderValidation.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/css/<%=(String)session.getAttribute("userTheme")%>/style.css" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <%
    String sButton = "";
        if (sViewDetails!=null && sViewDetails.equals ("Modify")){ 
            sButton = "Save";
        }else if ( (sViewDetails == null) || (sViewDetails!=null && sViewDetails.equals ("Details"))){ 
            sButton = "Add";
        }
    %>
		<tr>
			<td colspan="3">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td width="10"><img
								src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif"
								height="25" width="10"></td>
						</tr>
						<tr>
							<%
								if (!StringUtil.isValidNew(action)&&"true".equals(mandatoryFlag)) {      //P_B_80593
							%>
							<td width="34%" style="padding-right: 27px;" align="right">
								<span class="bText11gr"><%=LanguageUtil.getString("Fields marked with")%>
									<span class="urgent_fields">*</span> <%=LanguageUtil.getString("are mandatory")%>.&nbsp;
							</span>
							</td>
							<%
								}
							%>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
        <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="1" width="10"></td>
        <td>
        <%if(StringUtil.isValidNew(action) && "display".equals(action)){ %>
        <div id='printReady1' >
        		<%if("fim".equals(session.getAttribute("menuName"))) {%>
        		<a:buildersummery noOfColumns="2" tableBorder="0" cssClassKey="tb_data_b" cssClassValue="tb_data" fieldsInfo="<%=dataInfo%>"
 					 functionForButtons="printPage()" buttonPriveleges="true" formHeaderClass="header_contrast">
 				</a:buildersummery>
        		<%}else{ %>
                <ar:Newcustomtables noOfColumns="2" fieldsInfo="<%=dataInfo%>" cssClassKey="pqs_que" cssClassValue="tb_data_mokup"  cellspacing="0" cellpadding="0" i18n="true">
                </ar:Newcustomtables>
                <%} %>
            </div>
        <%}else{ %>
        <%if("fim".equals(session.getAttribute("menuName"))) {%>
        	<form name=tabularSectionForm id=tabularSectionForm action=genericHandler method=post enctype="multipart/form-data" accept-charset="UTF-8">
        	<ar:builderforms  noOfColumns="2" fieldsInfo='<%=dataInfo%>' cssClassKey="pqs_que" cssClassValue="tb_data_mokup_green" tableWidth="100%" tableAllign="center" formHeaderClass="header_contrast">
				</ar:builderforms>
				<input type="hidden" name="mainIdFieldName" value="<%=mainIdFieldName%>"> 						<!--P_Enh_Multiple_Input_Tabular_Summary-->
				<input type="hidden" name="from" value="<%=request.getParameter("from")%>">
			<%}else{ %>
            <ar:Newcustomforms name = "tabularSectionForm" action="genericHandler" noOfColumns="2" fieldsInfo="<%=dataInfo%>" cssClassKey="tb_data"  cellspacing="0" cellpadding="0" submit="<%=sButton%>" functionForSubmit="return checkValues()" buttons="Close" functionForButtons="closeWindow();" i18n="true"  enctype = "<%=encType%>" > 
            
            <% if("cm".equals(request.getSession().getAttribute("moduleName"))){%>
            	<input type="hidden" name="<%=FieldNames.CONTACT_ID %>" value="<%=StringUtil.isValid(leadId)?leadId:(String)session.getAttribute(FieldNames.CONTACT_ID)%>"> 
            <%}else if("lead".equals(request.getSession().getAttribute("moduleName"))){ %>
            	<input type="hidden" name="<%=FieldNames.LEAD_ID %>" value="<%=StringUtil.isValid(leadId)?leadId:(String)session.getAttribute(FieldNames.LEAD_ID)%>"> 
            <%}else if("account".equals(request.getSession().getAttribute("moduleName"))){ %>
            	<input type="hidden" name="<%=FieldNames.COMPANY_ID %>" value="<%=StringUtil.isValid(leadId)?leadId:StringUtil.isValid((String)session.getAttribute(FieldNames.ACCOUNT_ID))?(String)session.getAttribute(FieldNames.ACCOUNT_ID):StringUtil.isValid((String)session.getAttribute(FieldNames.COMPANY_ID))?(String)session.getAttribute(FieldNames.COMPANY_ID):(String)session.getAttribute("A_ID")%>"> 
            <%}else if("opportunity".equals(request.getSession().getAttribute("moduleName"))){%>
            	<input type="hidden" name="<%=FieldNames.OPPORTUNITY_ID %>" value="<%=StringUtil.isValid(leadId)?leadId:(String)session.getAttribute(FieldNames.OPPORTUNITY_ID)%>"> 
            <%}else{ %>
            <input type="hidden" name="<%=FieldNames.LEAD_ID %>" value="<%=leadId%>">           <%--P_FS_Enh_BuilderForm --%>
            <%} %>
            <input type="hidden" name="<%=FieldNames.FRANCHISEE_NO %>" value="<%=franchiseeNo%>">
            <input type="hidden" name="<%=FieldNames.ENTITY_ID %>" value="<%=franchiseeNo%>">
            <input type="hidden" name="<%=FieldNames.TAB_PRIMARY_ID %>" value="<%=tabPrimaryId%>">    
            <input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=tableAnchor%>">
             <input type="hidden" name="<%=FieldNames.RETURN_PAGE%>" value="<%=viewUrl%>?fromTabularSection=yes"> 
            <input type="hidden" name="<%=FieldNames.ERROR_PAGE%>" value="errorPage">
            <input type="hidden" name="<%=FieldNames.PROCESSING_CLASS%>" value="com.appnetix.app.portal.CommonTabularSectionManipulator">
            <%//P_FS_Enh_BuilderForm 
            if (sButton.equals ("Add")) {
            %>
            <input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=EventType.CREATE%>">
            <%
                } else {
            %>
            
            <input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="<%=EventType.MODIFY%>">
            <%}
            %>
            </ar:Newcustomforms>
            <%} %>
            <%} %>
            </td>
            <td width="10"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" height="10" width="1"></td>
        </tr>
         <%if (StringUtil.isValidNew(action) && "display".equals(action)){ //P_FS_Enh_BuilderForm
         %>
         <tr>
             <td>&nbsp;</td>
             <td align="center" valign="top"> 
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                         <td align="left">
                             <input type="button" name="closeButton" value="<%=LanguageUtil.getString("Close", (String)session.getAttribute("userLanguage"))%>" class="cm_new_button" onClick="javascript:closeWindow();" > <%--P_FS_Enh_BuilderForm--%>
                         </td>
                     </tr>
                 </table>
             </td>
             <td>&nbsp;</td>
         </tr>

        <%}%>
        <%if("fim".equals(session.getAttribute("menuName")) && !"display".equals(action)) {%>
        <tr>
    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
	<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr>
		<td width="75%" align="left" colspan="2">
		<input type="button" name="Submit" value="<%=LanguageUtil.getString(sButton)%>"  class="cm_new_button" onclick="return checkValues();">
		<input type="button" name="Close" value=<%=LanguageUtil.getString("Close",(String)session.getAttribute("userLanguage")) %> class="cm_new_button" onClick="closeWindow();">	
		</td>
		</tr>
		</table>
	</td>
    <td width="6" class="text"><img src="<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/spacer.gif" width="10" height="8"></td>
</tr>
     <input type="hidden" name="<%=FieldNames.FRANCHISEE_NO %>" value="<%=franchiseeNo%>">
     <input type="hidden" name="<%=FieldNames.ENTITY_ID %>" value="<%=franchiseeNo%>">     
     <input type="hidden" name="<%=FieldNames.MU_ID %>" value="<%=muID%>">
     <input type="hidden" name="<%=FieldNames.AREA_ID %>" value="<%=(String) session.getAttribute("areaID")%>">
     <input type="hidden" name="<%=FieldNames.AREA_INFO_ID %>" value="<%=(String) session.getAttribute("areaID")%>">
     <input type="hidden" name="<%=FieldNames.TAB_PRIMARY_ID %>" value="<%=tabPrimaryId%>">    
     <input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=tableAnchor%>">
     <input type="hidden" name="<%=FieldNames.RETURN_PAGE%>" value="<%=viewUrl%>?fromTabularSection=yes"> 
     <input type="hidden" id="triggerFormName" name="triggerFormName" value="<%=triggerFormNameForTabularSection%>">
     <input type="hidden" name="<%=FieldNames.ERROR_PAGE%>" value="errorPage">
     <input type="hidden" name="<%=FieldNames.PROCESSING_CLASS%>" value="com.appnetix.app.portal.CommonTabularSectionManipulator">
		<%
			//P_FS_Enh_BuilderForm 
		           			if (sButton.equals("Add")) {
		%>
		<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>"
			value="<%=EventType.CREATE%>">
		<%
			} else {
		%>

		<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>"
			value="<%=EventType.MODIFY%>">
		<%
			}
		%>
		</form>
<%
	}
%>
</table>
<%
String pathNew = StrutsUtil.getPath().substring(1) + "?tableAnchor="+tableAnchor+"&fromDocDelete=yes";
if("cm".equals(menuName)){
	pathNew=pathNew+"&leadID="+leadId+"&tabPrimaryId="+tabPrimaryId+"&idField="+request.getParameter("idField")+"&viewDetails=Modify";
}
String muTabName = (String)session.getAttribute("tabNameForTabular");
%>
<form action="genericHandler" method="POST" name="deleteDocForm" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="<%=FieldNames.EVENT_TYPE%>" value="modify">
	<input type="hidden" name="<%=FieldNames.DOCUMENTS_ID%>" value="">
	<input type="hidden" name="<%=FieldNames.DOCUMENT_FIM_ATTACHMENT%>" value="">
	<input type="hidden" name="DocumentName" value="" >
	<input type='hidden' name='idField' value='<%=idfieldValue%>'>
	<input type='hidden' name='addTabularSectionData' value=''>
	<%if(StringUtil.isValidNew(tabPrimaryId) && !"cm".equals(menuName)) {%>
	<input type="hidden" name="<%=FieldNames.TAB_PRIMARY_ID %>" value="<%=tabPrimaryId%>">  
	<%} %>
	<% if("fim".equals(menuName)) { %>	
		<input type="hidden" name="<%=FieldNames.PROCESSING_CLASS%>" value="com.appnetix.app.portal.fim.DocumentManipulator">
		<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="fim-documentmanipulator" >
		<%if("fimArea".equals(subMenuName)){ %>
		<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=TableAnchors.AREA_DOCUMENTS%>">
		<%}else if(("entity".equals(session.getAttribute("showEntityTabs")) || "mu".equals(session.getAttribute("showEntityTabs"))) && !"Owners".equals(muTabName) && !"Entity Detail".equals(muTabName)){%>
		<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=TableAnchors.FIM_MU_DOCUMENTS%>">
		<%}else{ %>
		<input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=TableAnchors.FIM_DOCUMENTS%>">
		<%} %>
	<% }else if("cm".equals(menuName)) {%>
			<input type="hidden" name="documentID" value="">
		<%if("Contact".equals(subMenuName)) {%>
			<input type="hidden" name="tableAnchor" value="<%=TableAnchors.CM_DOCUMENTS%>">
		<%} else if("Lead".equals(subMenuName)) {%>
			<input type="hidden" name="tableAnchor" value="<%=TableAnchors.LEAD_DOCUMENTS%>">
		<%} else if("Companies".equals(subMenuName)) {%>
			<input type="hidden" name="tableAnchor" value="<%=TableAnchors.ACCOUNT_DOCUMENTS%>">
		<%} else if("Opportunities".equals(subMenuName)) {%>
			<input type="hidden" name="tableAnchor" value="<%=TableAnchors.OPPORTUNITY_DOCUMENTS%>">
		<%}%>
		
	<%} else { %>
		<input type="hidden" name="<%=FieldNames.PROCESSING_CLASS%>"    value="com.appnetix.app.portal.franchise_sales.lead.DocumentManipulator">
		<input type="hidden" name="<%=FieldNames.MANIPULATOR%>" value="fs-documentmanipulator" >
    	<input type="hidden" name="tableAnchor" value="<%=TableAnchors.FS_DOCUMENTS%>">
	<% } %>
	
	<input type="hidden" name="returnPage" value="tabreturnpage">
    <input type="hidden" name="returnTabPage" value="<%=pathNew%>">
    <input type="hidden" name="formName" value="tabularSectionForm">
</form>

<!-- <form action="genericHandler" method="POST" name="deleteDocForm" <%=Constants.FORM_ENCODING%>>
	<input type="hidden" name="tableAnchor" value="fimDocuments">
	<input type="hidden" name="eventType" value="modify">
	<input type="hidden" name="documentsID" value="">
	<input type="hidden" name="fimDocumentAttachment" value=" ">
	<input type="hidden" name="DocumentName" value=" " >
	<input type="hidden" name="processingClass" value="com.appnetix.app.portal.fim.DocumentManipulator">
	<input type="hidden" name="manipulator" value="fim-documentmanipulator" >
	<input type='hidden' name='idField' value='<%=idfieldValue%>'>
	<input type="hidden" name="returnPage" value="tabreturnpage">
    <input type="hidden" name="returnTabPage" value="<%=StrutsUtil.getPath().substring(1)%>">
</form>-->
  <form name="deleteDocument" action="" method=post <%=Constants.FORM_ENCODING%>>
    <input type="hidden" name="tableName">
    <input type='hidden' name='dbColName'>
    <input type="hidden" name="docName">
    <input type='hidden' name='module'>
    <input type='hidden' name='deleteDocument' value="yes">
    <input type='hidden' name='fromDocDelete' value="yes">
	<input type='hidden' name='leadID' value='<%=leadId%>'>
	<input type='hidden' name='mappingId' value="">									<!-- FIM FTP upload issue -->
	<input type='hidden' name='idField' value='<%=idfieldValue%>'>
	<input type='hidden' name='addTabularSectionData' value=''>
	<input type="hidden" name="DetailsleadId" value="<%=request.getAttribute("viewDetails")%>">
	<% if("fim".equals(menuName) && "true".equals((String)session.getAttribute("isTabularFieldsConfigured"))) { %>							<!--P_Enh_Multiple_Input_Tabular_Summary starts-->
		<input type="hidden" name="mainIdFieldName" value="<%=mainIdFieldName%>"> 
	<%}%>																																	<!--P_Enh_Multiple_Input_Tabular_Summary ends-->
	<input type="hidden" name="formName" value="tabularSectionForm">
	<input type="hidden" name="<%=FieldNames.TAB_PRIMARY_ID %>" value="<%=tabPrimaryId%>">    
    <input type="hidden" name="<%=FieldNames.TABLE_ANCHOR%>" value="<%=tableAnchor%>">
	<%if("true".equals(request.getParameter("fromCoAppTab"))) { %>
		<input type="hidden" name="action" value="<%=request.getParameter("action")%>">
	<%} %>
</form>
<form action="modifyFIMCenterInfo"  method="POST" name="addModifyForm" <%=Constants.FORM_ENCODING%>> 
<input type="hidden" name="franchiseeNo" value="" > 
<input type="hidden" name="UnlockedFields" value=""> 
<input type="hidden" name="fno" value="">
<input type="hidden" name="subMenuName" value="">
 </form> 
<script>
function isValidFranchisee(value)
{
	if(value == null)
		return false;
	$.ajax({
		type: "POST",
		url: "fimAjaxAction",
		data: "dataset=isValidFranchiseeForEmailType&key="+value,
		async: false,
		success: function (data) {
			if('N' == data.trim())
			{
				$(".randomclass").hide();
			}else if('Y' == data.trim())
			{
				$(".randomclass").show();
			}
		}
	});
	if('-1' == value)
	{
		$(".randomclass").show();
	}
}
function deleteDoc(DocumentID,DocumentName)
{
   var f  = document.deleteDocForm;
   if(f.addTabularSectionData){
	   f.addTabularSectionData.value="yes";
   }
    if( FCI.Confirms(FCLang.DO_YOU_WANT_TO_DELETE_THIS_DOCUMENT))
	{
	   if(f.documentsID) {
	   		f.documentsID.value= DocumentID;
	   }
	   if(f.documentID){
		   	f.documentID.value= DocumentID;
	   }
	   if(f.DocumentName) {
	   	f.DocumentName.value= DocumentName;
	   }
	   <%if("cm".equals(menuName)) {%>
	   		f.eventType.value= "delete";
	   <%}%>
	   f.submit();
	}

}
function downloadDoc(fimDocumentNo,tabName,fileName, fromWhere) {
	$.post("thirdPartyDownload",{"fimDocumentNo":fimDocumentNo,"tabName":tabName, "fromWhere":fromWhere, "fileName":fileName},
			function(data){
		data = $.trim(data);
		if(data=="0"){
			FCI.Messages(FCLang.UPLOADING_IS_IN_PROGRESS);
		}else if(data=="1"){
			var localHref ="fimDocumentDownload?tabName="+tabName+"&fileName="+fileName+"&"+cft;
			location.href = localHref;
		}else if(data == "false")
		{
			FCI.Messages(FCLang.DOCUMENT_DOESNOT_EXIST_IN_THE_SYSTEM);
			return false;
		}else {
			if(data.length > 0 && data.substring( 0, 8 ) === "ftpfile:"){
				var ftpUrl = data.substring(8);
				window.open(ftpUrl,"", " HEIGHT=500,WIDTH=1000, top=' + y + ',left=' +x+',scrollbars=yes,resizable=yes");
			} else {
			  location.href = data;
			}
		}
	}
	);	        		
}
function deleteDocFromMainTable(tableName, dbColName, docName, module, idFldValue, mappingId) {
	var url = '<%=StrutsUtil.getPath().substring(1)%>';
	if(document.deleteDocument.addTabularSectionData){
		document.deleteDocument.addTabularSectionData.value="yes";
	   }
	var response=FCI.Confirms(FCLang.ARE_YOU_SURE_TO_DELETE_THIS_DOCUMENT);
    if (response){
    	document.deleteDocument.tableName.value = tableName;
    	document.deleteDocument.dbColName.value = dbColName;
    	document.deleteDocument.docName.value = docName;
    	document.deleteDocument.module.value = module;
    	if(idFldValue.trim() != "") {
    		document.deleteDocument.idField.value = idFldValue;
    	}
    	document.deleteDocument.mappingId.value = mappingId;			//FIM FTP upload issue
    	document.deleteDocument.action = url;
    	document.deleteDocument.submit();
    }
}
$(document).ready(function(){
	
	//dki-20160812-546 starts
	try{
	if(parent.document.getElementById("colorbox")){
		parent.hideProgressPopup();
	parent.document.getElementById("colorbox").style.removeProperty("z-index");
	}
	}catch(err){}
	//dki-20160812-546 ends
	if(parent.$.fn.colorbox) {
		<%if(StringUtil.isValidNew(action) && "display".equals(action)){ %>
			parent.$.fn.colorbox.vkResize($(document).width()+60, $(document).height());   //P_B_78708 //P_CM_B_77635
		<%} else {
			if("yes".equals(fromDocDelete)){%>
			parent.$.fn.colorbox.vkResize($(document).width(), $(document).height());
			<%}else{%>
			parent.$.fn.colorbox.vkResize($(document).width()+60, $(document).height()+180);   //P_B_79768  //P_B_81873
		<%}}%>
 	}
});
 
 
var  unlockedBuilderFields = "";

function addHtmlBuilder(fieldName ,status ,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere, tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField,piiFieldname)
{
	var displayPrivateValue = privatevalue;
	var displayParamValue = paramValue;
	//BB-20150525-360 Starts
	$.post("handleAjaxRequest",{"dataset": "piiPasswordDecrypt","key":displayParamValue,"canAcessSendMail":canAcessSendMail,"isEmailField":isEmailField,"moduleName":moduleName,"isUrlField":isUrlField},
	function(data)
	{															//BB-20150525-360 Ends
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
				var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view','"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"','"+isUrlField+"','"+piiFieldname+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";
				if(document.getElementById(fieldName)){
				document.getElementById(fieldName).innerHTML = displayPrivateValue+"&nbsp;"+displayAdditional; 
				}else{
					document.getElementById(piiFieldname).innerHTML = displayPrivateValue+"&nbsp;"+displayAdditional;
				}
				if(unlockedBuilderFields.match(fieldId) == fieldId);
				{
					unlockedBuilderFields = unlockedBuilderFields.replace(fieldId,"");
				}
			}
			else if(status == "unlocked")
			{
				var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','view', '"+tableAnchorForLogs+"','"+canAcessSendMail+"','"+isEmailField+"','"+isUrlField+"','"+piiFieldname+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a>";  
				if(document.getElementById(fieldName)){
				document.getElementById(fieldName).innerHTML = displayParamValue+"&nbsp;"+displayAdditional; 
				}else{
					document.getElementById(piiFieldname).innerHTML = displayParamValue+"&nbsp;"+displayAdditional; 	
				}
				unlockedBuilderFields =  unlockedBuilderFields + "," + fieldId;
			}
			document.addModifyForm.UnlockedFields.value = unlockedBuilderFields;
			document.pdfform.UnlockedFields.value = unlockedBuilderFields;
		}
		else  if(fromWhere == "modify")
		{
			if(status == "locked")
			{
				var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:unLockFieldBuilder('"+fieldName+"','unlocked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify', '"+tableAnchorForLogs+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16' vspace='3'   border = '0'  ></a>";
				
				var fieldId = fieldName.replace("piiField","");
				document.getElementById(fieldId).disabled=true;
				document.getElementById(fieldId).value=displayPrivateValueText; 
				document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
			}
			else if(status == "unlocked")
			{
				var displayAdditional = "<a href='javascript:void(0)' onClick =\"javascript:lockedFieldBuilder('"+fieldName+"','locked','"+privatevalue+"','"+paramValue+"','"+moduleName+"','"+pageName+"','"+leadID+"','modify', '"+tableAnchorForLogs+"')\"><img src='<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'  vspace='3'  border = '0'  ></a>";  
				var fieldId = fieldName.replace("piiField","");
				document.getElementById(fieldId).disabled=false;
				document.getElementById(fieldId).value=displayParamValueText; 
				document.getElementById(fieldName).innerHTML = "&nbsp;"+displayAdditional;
			}
		}
	});				
}
function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere, tableAnchorForLogs)
{
	return unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,'false','false');
}
function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField)
{
	unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField,'');
}
function unLockFieldBuilder(fieldName,status,privatevalue, paramValue, moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField,piiFieldname)
{
	openJqueryPopUp('checkRedAlertBuilder?FieldName='+fieldName+'&Status='+status+'&Value='+paramValue+'&paramValue='+privatevalue+'&moduleName='+moduleName+'&pageName='+pageName+'&leadID='+leadID+'&fromWhere='+fromWhere+'&tableAnchorForLogs='+tableAnchorForLogs+'&canAcessSendMail='+canAcessSendMail+'&isEmailField='+isEmailField+'&isUrlField='+isUrlField+'&piiFieldname='+piiFieldname,600,300);
}
function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs)
{
  return lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,'false','false')
}
function lockedFieldBuilder(fieldName,status,privatevalue, paramValue,moduleName,pageName,leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField,piiFieldname)
{
	if(FCI.Confirms(FCLang.ARE_YOU_SURE_YOU_WANT_TO_LOCK_THIS_ENTRY))
	{
		addHtmlBuilder(fieldName,"locked",privatevalue,paramValue,moduleName, pageName, leadID,fromWhere,tableAnchorForLogs,canAcessSendMail,isEmailField,isUrlField,piiFieldname);
	}
}
 
</script>
</body>
<%
} catch(Exception e)
{
	e.printStackTrace();
}%>
