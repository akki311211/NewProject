package com.home.builderforms;

import com.home.builderforms.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.SQLException;

import com.home.builderforms.*;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.BuilderFormMgr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *This class is the web-tier representation of the Data.
 *@author     abhishek gupta
 *@created    Nov 10, 2011

-------------------------------------------------------------------------------------------------
Version No.				Date			By						Against         Comments
-------------------------------------------------------------------------------------------------
BOEFLY_INTEGRATION		21/08/2014		Veerpal Singh			Enh				A third party integration with Boefly through REST-API for lead sync.  
-------------------------------------------------------------------------------------------------------------------------------
*/
public final class BuilderFormWebImpl implements java.io.Serializable {
    private HttpSession session;
	private BuilderFormMgr form		= null;
	private static BuilderFormWebImpl impl	= null;

    public BuilderFormWebImpl() { }

	public static BuilderFormWebImpl getInstance(){
		if(impl == null){
			impl = new BuilderFormWebImpl();
		}
		return impl;
	}

    public BuilderFormMgr getBuilderFormMgr() {
		if(form == null){
			form= new BuilderFormMgr();
		}
        return form;
    }

    public void init(HttpSession session) {
    }

	public SequenceMap getBuilderForm(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getBuilderFormList(request);
		}
		catch (Exception e){
			return null;
		}
	}
	
	public Map getQuickFormField(String tableanchor) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getQuickFormField(tableanchor);
		}
		catch (Exception e){
			return null;
		}
	}

	public void setBuilderFormAddOrUpdate(HttpServletRequest request) {
		try {
			getBuilderFormMgr().getBuilderFormDAO().setBuilderFormAddOrUpdate(request);
		}
		catch (Exception e){
			//return false;
		}
	}
	
	public boolean setBuilderFormAddOrUpdateSection(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().setBuilderFormAddOrUpdateSection(request);
		}
		catch (Exception e){
			return false;
		}
	}
	
	public boolean setBuilderFormAddOrUpdateDocument(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().setBuilderFormAddOrUpdateDocument(request);
		}
		catch (Exception e){
			return false;
		}
	}
	
	public SequenceMap getRadioOrComboOptionsMap(String name, String tableAnchor) {
		try {
		 	return getBuilderFormMgr().getBuilderFormDAO().getRadioOrComboOptionsMap(name, tableAnchor);
		}
		catch (Exception e){
			return null;
		}
	}
	public boolean deleteRadioOrComboOptions(String name, String tableAnchor) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().deleteRadioOrComboOptions(name, tableAnchor);
		}
		catch (Exception e){
			return false;
		}
	}
	
	/**
	 * P_Enh_Sync_Fields
	 * @param tableAnchor
	 * @param displayType
	 * @return
	 */
	public StringBuffer getFieldNamesForTabs(String tableAnchor, String displayType, String actualTableAnchor, String validationType) {
		return getBuilderFormMgr().getBuilderFormDAO().getFieldNamesForTabs(tableAnchor, displayType, actualTableAnchor, validationType);
	}
	
	public String getSyncFieldDisplayName(String syncWith) {
		return getBuilderFormMgr().getBuilderFormDAO().getSyncFieldDisplayName(syncWith);
	}
	
	//SMC-20140213-378 Starts
	public boolean insertRadioOrComboOptionsMap(String name, String[] options, String tableAnchor) {
		return insertRadioOrComboOptionsMap(name,options,tableAnchor,null);
	}
	public boolean insertRadioOrComboOptionsMap(String name, String[] options, String tableAnchor,String[] statusValue) {
		return insertRadioOrComboOptionsMap(name, options, tableAnchor, statusValue, null); //BB-20150203-259 (Dynamic Response based on parent field response)
	}
	public boolean insertRadioOrComboOptionsMap(String name, String[] options, String tableAnchor,String[] statusValue, String[] dependentValue) {//Alleg-20160615-349 starts
		return insertRadioOrComboOptionsMap(name, options, tableAnchor, statusValue, dependentValue,null);
	}
	public boolean insertRadioOrComboOptionsMap(String name, String[] options, String tableAnchor,String[] statusValue, String[] dependentValue,String[] optionValues) {
		try {
				return getBuilderFormMgr().getBuilderFormDAO().insertComboOptionsMap(name, options, tableAnchor, statusValue, dependentValue,optionValues);//SMC-20140213-378 Ends //BB-20150203-259 (Dynamic Response based on parent field response)  //Alleg-20160615-349 ends
		}
		catch (Exception e){
			return false;
		}
	}
	
	public boolean removeBuilderFormField(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().removeBuilderFormField(request);
		}
		catch (Exception e){
			return false;
		}
	}
	
	public boolean removeBuilderFormSection(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().removeBuilderFormSection(request);
		}
		catch (Exception e){
			return false;
		}
	}
	/**
	 * P_Enh_FC-76
	 * @param request
	 * @return
	 */
	public List getPiiFieldPasswordMap(String formID) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getPiiFieldPasswordMap(formID);
		}
		catch (Exception e){
			return null;
		}
	}
	public String getBuilderFieldsTable(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderTable(request);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFields(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderFields(request);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFieldsMap(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderFieldsMap(request);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFieldsAllMap(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderFieldsAllMap(request);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFieldsAllTablesMap(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderFieldsAllTablesMap(request);
		} catch (Exception e) {
			return null;
		}
	}
	public Info getBuilderSectionCount(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getBuilderSectionCount(request);
		} catch (Exception e) {
			return null;
		}
	}
	public SequenceMap getBuilderSectionMap(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderSectionMap(request);
		} catch (Exception e) {
			return null;
		}
	}
	//BB-20150203-259 (Tab Re-ordering changes) starts
	public SequenceMap getBuilderTabMap(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getBuilderTabDataMapFromXMl(request);
		} catch (Exception e) {
			return null;
		}
	}
	//BB-20150203-259 (Tab Re-ordering changes) ends
	
	public List getConfiguredTab(String subModuleName) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getConfiguredTab(subModuleName);
		} catch (Exception e) {
			return null;
		}
	}
	
	//JIS-20160815-009 : START
	public List getConfiguredTabByInternalNames(String subModuleName) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getConfiguredTabByInternalNames(subModuleName);
		} catch (Exception e) {
			return null;
		}
	}
	//JIS-20160815-009 : END
	
	public FieldMappings getFimFormBuilderSectionMapping(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderSectionMapping(request);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * P_Enh_Multiple_Input_Tabular_Summary
	 * @param request
	 * @param tableAnchor
	 * @return
	 */
	public Map getTabularSummaryFieldsMap(HttpServletRequest request, String tableAnchor) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getTabularSummaryFieldsMap(request, tableAnchor);
		} catch (Exception e) {
			return null;
		}
	}
	
	public FieldMappings getFimFormBuilderMapping(String tableAnchor) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getBuilderSectionMapping(tableAnchor);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean addModifyBuilderFieldsOrder(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().addModifyFimFormBuilderFieldsOrder(request);
		} catch (Exception e) {
			return false;
		}
	}
	//BB-20150203-259 (Section Re-ordering changes) starts
	public boolean addModifyBuilderSectionsOrder(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().addModifyFimFormBuilderSectionsOrder(request);
		} catch (Exception e) {
			return false;
		}
	}
	//BB-20150203-259 (Section Re-ordering changes) ends
	//BB-20150203-259 (Tab Re-ordering changes) starts
	public boolean addModifyBuilderTabsOrder(HttpServletRequest request) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().addModifyTabOrderData(request);
		} catch (Exception e) {
			return false;
		}
	}
	//BB-20150203-259 (Tab Re-ordering changes) ends
	public FieldMappings getBuilderFieldsMapping(String tableAnchor) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFieldMappings(tableAnchor);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFormCM() {
		try {
			return getBuilderFormMgr().getBuilderFormDAOforCm().getBuilderFormListCM();
		} catch (Exception e) {
			return null;
		}
	}

	public SequenceMap getBuilderFieldsCM(String formId) {
		try {
			return getBuilderFormMgr().getBuilderFormDAOforCm().getCmFormBuilderFields(formId);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SequenceMap getBuilderFormViewFieldsCM(String formId , String tableName , String columnName , String foreignId) {
		try {
			return getBuilderFormMgr().getBuilderFormDAOforCm().getViewCmBuilderFields(formId,tableName,columnName,foreignId);
		} catch (Exception e) {
			return null;
		}
	}

	public SequenceMap getBuilderFormDisplayFieldsCM(String formId) {
		try {
			return getBuilderFormMgr().getBuilderFormDAOforCm().getCmDisplayBuilderFields(formId);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean checkForSkipValue(Vector flds, String sDBTableName, String fldName) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().checkForSkipValue(flds, sDBTableName, fldName);
		} catch (Exception e) {
			return false;
		}
	}
	
	public Object getDetailsObject(String tableName, int fldId) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getDetailsInfo(tableName, fldId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//SMC-20140213-378 Starts
	public Info getBuilderFieldsInfo(String fldName, String tableName) {
		return getBuilderFieldsInfo(fldName, tableName, null);
	}
	public Info getBuilderFieldsInfo(String fldName, String tableName, String value) {
		return getBuilderFieldsInfo(fldName, tableName, value, null); //BB-20150203-259 (Dynamic Response based on parent field response)
	}
	public Info getBuilderFieldsInfo(String fldName, String tableName, String value, String parentValue) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getFimFormBuilderFieldsInfo(fldName, tableName, value, parentValue);//SMC-20140213-378 Ends //BB-20150203-259 (Dynamic Response based on parent field response)
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getRadioOrComboOptionsValueForId(String fldName, String tableName, String id) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getRadioOrComboOptionsValueForId(fldName, tableName, id);
		} catch (Exception e) {
			return null;
		}
	}
	public String getCheckboxOptionsValueForId(String fldName, String tableName, String id) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().getCheckboxOptionsValueForIds(fldName, tableName, id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean checkColumnValueInputInTable(String tableName, String fldName) {
		return checkColumnValueInputInTable(tableName, fldName, null);
	}
	
	public boolean checkColumnValueInputInTable(String tableName, String fldName, HashMap hMap) {
		return checkColumnValueInputInTable(tableName, fldName, hMap, -1);
	}
	/**
	 * 
	 */
	public boolean checkColumnValueInputInTable(String tableName, String fldName, HashMap hMap,int OrderNo) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().checkColumnValueInputInTable(tableName, fldName, hMap,OrderNo);
		} catch (Exception e) {
			return false;
		}
	}
	public boolean checkColumnValueForCMFileField(String sFieldName) {//P_CM_B_60109 
		try {
			return getBuilderFormMgr().getBuilderFormDAO().checkColumnValueForCMFileField(sFieldName);
		} catch (Exception e) {
			return false;
		}
	}
	public boolean checkColumnValueInputInAuditTable(String tableName, String fldName) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().checkColumnValueInputInAuditTable(tableName, fldName);
		} catch (Exception e) {
			return false;
		}
	}
	//PW_B_26809 Starts
	public boolean isDataExistForFimDocument(String fieldPrefix) {
		return isDataExistForFimDocument(fieldPrefix, false);
	}
	
	public boolean isDataExistForFimDocument(String fieldPrefix, boolean checkForData) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().isDataExistForFimDocument(fieldPrefix, checkForData);
		} catch (Exception e) {
			return false;
		}
	}
	//PW_B_26809 Ends
	
	// START : CM_B_37561 : 05/05/2014 : Veerpal Singh
	public boolean isDataExistForCmDocument(String fieldPrefix) {
		return isDataExistForCmDocument(fieldPrefix, false);
	}
	
	public boolean isDataExistForCmDocument(String fieldPrefix, boolean checkForData) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().isDataExistForCmDocument(fieldPrefix, checkForData);
		} catch (Exception e) {
			return false;
		}
	}
	// END : CM_B_37561 : 05/05/2014 : Veerpal Singh
	
	/**
	 * signal-20150529-009
	 */
	public boolean isDataExistForFsDocument(String fieldPrefix) {
		try {
			return getBuilderFormMgr().getBuilderFormDAO().isDataExistForFsDocument(fieldPrefix);
		} catch(Exception e) {
			return false;
		}
	}
	
	public Object processBuilderXmlFileAttributes(HttpServletRequest request) {
		try {
			Object obj = getBuilderFormMgr().getBuilderFormDAO().processBuilderXmlFileAttributes(request);
			if(obj instanceof SequenceMap) {
				return (SequenceMap)obj;
			} else if(obj instanceof Info) {
				return (Info)obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//ENH_MODULE_CUSTOM_TABS starts
	
	//BOEFLY_INTEGRATION : START
	public Object processBuilderXmlFileAttributes(Info requestInfo) {
		try {
			Object obj = getBuilderFormMgr().getBuilderFormDAO().processBuilderXmlFileAttributes(requestInfo);
			if(obj instanceof SequenceMap) {
				return (SequenceMap)obj;
			} else if(obj instanceof Info) {
				return (Info)obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
	//BOEFLY_INTEGRATION : END
	
	public boolean processFormGeneratorTabData(HttpServletRequest request) 
	{	
		try 
		{
			boolean isSuccess = getBuilderFormMgr().getBuilderFormDAO().processFormGeneratorTabData(request);
			return isSuccess;
		} catch (Exception e) 
		{
		}
		return false;
	}
	
	public Info getSubmoduleList(String moduleName)
	{
		return getBuilderFormMgr().getBuilderFormDAO().getSubmoduleList(moduleName);
	}
	//P_Enh_Mu-Entity_FormGenerator starts
	public Info getSubmoduleList(String moduleName,String subModuleName)
	{
		return getBuilderFormMgr().getBuilderFormDAO().getSubmoduleList(moduleName,subModuleName);
	}//P_Enh_Mu-Entity_FormGenerator ends
	/**
	 * PP_changes
	 * @return
	 */
	public Info getCaptivateSectionList() {
		return getBuilderFormMgr().getBuilderFormDAO().getCaptivateSectionList();
	}
	
	public SequenceMap getDeletedCustomTabs(String moduleName)
	{
		return getBuilderFormMgr().getBuilderFormDAO().getDeletedCustomTabs(moduleName);
	}
	
	public SequenceMap getAllTabs(String moduleName)
	{
		return getBuilderFormMgr().getBuilderFormDAO().getAllTabs(moduleName);
	}
	
	public boolean isCustomTab(String tableAnchor)
	{
		return getBuilderFormMgr().getBuilderFormDAO().isCustomTab(tableAnchor);
	}
	public boolean isCustomForm(String formName,String moduleName)
	{
		return getBuilderFormMgr().getBuilderFormDAO().isCustomForm(formName,moduleName);
	}
	//ENH_MODULE_CUSTOM_TABS ends
	public Map getTabularSectionColumn(String tableAnchor,String isSelected, String isNotSelected)
	{
		return getBuilderFormMgr().getBuilderFormDAO().getConfiguredSummaryColumns( tableAnchor, isSelected,  isNotSelected);
	}
	public boolean isTabularSection(String tableAnchor)
	{
		return getBuilderFormMgr().getBuilderFormDAO().isTabularSection(tableAnchor);
	}
	//P_Enh_Mu-Entity_FormGenerator starts
	public boolean isMuTab(String tableAnchor)
	{
		return getBuilderFormMgr().getBuilderFormDAO().isMuTab(tableAnchor);
	}
	//P_Enh_Mu-Entity_FormGenerator ends
}
