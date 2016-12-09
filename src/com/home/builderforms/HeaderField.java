/**
 * @author abhishek gupta
 * @Date 24 Jan 2012
 */

/*
--------------------------------------------------------------------------------------------------------
Version No.					Date			By				Against		Function Changed    Comments
PW_FORM_VERSION				15 July 2013	Veerpal Singh		Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
----------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import java.util.Iterator;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.Version;
import com.home.builderforms.Info;
import com.home.builderforms.TableXMLDAO;

public class HeaderField {
	private String msIdField			= null;
	private String msTypeField			= null;
	private String msSectionField 		= null;
	private String msBuildSection 		= null;
	private SequenceMap tableMap		= null;
	private SequenceMap docMap			= null;
	private SequenceMap notesMap		= null;
	private SequenceMap idHeaderMap		= null;
	private SequenceMap msHeaderTableMap= null;
	private SequenceMap msOtherMap		= null;
	private SequenceMap	msAttrData		= null;
	private String fieldAsHeader		= null;
	private SequenceMap questionMap		= null;
	private String tabName				= null;
	private SequenceMap versionMap		= null;//PW_FORM_VERSION
	private SequenceMap dependentChildMap = null; //BB-20150203-259 (Dynamic Response based on parent field response)
	private SequenceMap syncFieldMap = null; //P_Enh_Sync_Fields
	//P_Enh_FormBuilder_Tabular_Section starts
	private Boolean isTabularSection=false;
	private String tabularSectionTableAnchor=null;
	private String tabularSectionDbTable=null;
	//P_Enh_FormBuilder_Tabular_Section  ends
	
	//P_Enh_Sync_Fields starts
	public SequenceMap getSyncFieldMap() {
		return syncFieldMap;
	}

	public void setSyncFieldMap(SequenceMap syncFieldMap) {
		this.syncFieldMap = syncFieldMap;
	}
	//P_Enh_Sync_Fields ends
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, SequenceMap sMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.tableMap		= sMap;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, SequenceMap sMap, SequenceMap dMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.tableMap		= sMap;
		this.docMap		= dMap;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
	}

	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap			= idMap;
	}
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData, SequenceMap quesMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
		this.questionMap	= quesMap;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData, SequenceMap quesMap, String tabName, SequenceMap versionMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
		this.questionMap	= quesMap;
		this.tabName		= tabName;
		this.versionMap		= versionMap;//PW_FORM_VERSION
	}
	
	/**
	 * BB-20150203-259 (Dynamic Response based on parent field response) starts
	 * @param msTypeField
	 * @param msSectionField
	 * @param msBuildSection
	 * @param fieldAsHeader
	 * @param sMap
	 * @param dMap
	 * @param nMap
	 * @param idMap
	 * @param tMap
	 * @param oMap
	 * @param headerAttrFldData
	 * @param quesMap
	 * @param tabName
	 * @param versionMap
	 * @param dependentChildMap
	 */
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData, SequenceMap quesMap, String tabName, SequenceMap versionMap, SequenceMap dependentChildMap) {
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
		this.questionMap	= quesMap;
		this.tabName		= tabName;
		this.versionMap		= versionMap;//PW_FORM_VERSION
		this.dependentChildMap = dependentChildMap;
	}
	
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData, SequenceMap quesMap, String tabName, SequenceMap versionMap, SequenceMap dependentChildMap, SequenceMap syncFieldMap) { //P_Enh_Sync_Fields
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
		this.questionMap	= quesMap;
		this.tabName		= tabName;
		this.versionMap		= versionMap;//PW_FORM_VERSION
		this.dependentChildMap = dependentChildMap;
		this.syncFieldMap 	= syncFieldMap;
	}
	public HeaderField(String msTypeField, String msSectionField, String msBuildSection, String fieldAsHeader, SequenceMap sMap, SequenceMap dMap, SequenceMap nMap, SequenceMap idMap, SequenceMap tMap, SequenceMap oMap, SequenceMap headerAttrFldData, SequenceMap quesMap, String tabName, SequenceMap versionMap, SequenceMap dependentChildMap, SequenceMap syncFieldMap,String isTabularSection,String tabularSectionTableAnchor,String tabularSectionDbTable) {//P_Enh_FormBuilder_Tabular_Section 
		this.msTypeField	= msTypeField;
		this.msSectionField	= msSectionField;
		this.msBuildSection	= msBuildSection;
		this.fieldAsHeader  = fieldAsHeader;
		this.tableMap		= sMap;
		this.docMap			= dMap;
		this.notesMap 		= nMap;
		this.idHeaderMap	= idMap;
		this.msHeaderTableMap= tMap;
		this.msOtherMap		= oMap;
		this.msAttrData		= headerAttrFldData;
		this.questionMap	= quesMap;
		this.tabName		= tabName;
		this.versionMap		= versionMap;//PW_FORM_VERSION
		this.dependentChildMap = dependentChildMap;
		this.syncFieldMap 	= syncFieldMap;
		this.isTabularSection="yes".equals(isTabularSection)?true:false;//P_Enh_FormBuilder_Tabular_Section starts
		this.tabularSectionTableAnchor=tabularSectionTableAnchor;
		this.tabularSectionDbTable=tabularSectionDbTable;//P_Enh_FormBuilder_Tabular_Section ends
	}
	public SequenceMap getHeaderIdMap(){
		return idHeaderMap;
	}
	public SequenceMap getHeaderTableMap(){
		return msHeaderTableMap;
	}
	public SequenceMap getHeaderAttrMap(){
		return msAttrData;
	}
	
	public void setHeaderIdMap(SequenceMap idHeaderMap){
		this.idHeaderMap= idHeaderMap;
	}

	public String getTypeField(){
		return msTypeField;
	}
	public String getSectionField(){
		return msSectionField;
	}
	public String getFieldAsHeader(){
		return fieldAsHeader;
	}
	public String isBuildSection(){
		return msBuildSection;
	}
	public String getTabName(){
		return tabName;
	}
	
	public Documents[] getDocuments(){
		if(docMap == null) {
			return null;
		}
		Documents[] sTables = new Documents[docMap.size()];
		Iterator it = docMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			Documents table = (Documents)it.next();
			sTables[i++] = table;
		}
		return sTables;
	}
	
	public Documents getDocuments(String docName){
		if(docMap == null) {
			return null;
		}
		Iterator it = docMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			Documents docs = (Documents)it.next();
			String name = docs.getDocumentName();
			if(name.equals(docName)) {
				return docs;
			}
		}
		return null;
	}
	
	public DocumentMap[] getDocumentMap(String docName){
		if(docMap == null) {
			return null;
		}
		return getDocuments(docName).getDocumentMaps();
	}
	
	public DependentTable[] getDependentTables(){
		if(tableMap == null) {
			return null;
		}
		DependentTable[] sTables = new DependentTable[tableMap.size()];
		Iterator it = tableMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			DependentTable table = (DependentTable)it.next();
			sTables[i++] = table;
		}
		return sTables;
	}
	
	/**
	 * BB-20150203-259 (Dynamic Response based on parent field response) starts
	 * @return
	 */
	public SequenceMap getDependentChildMap() {
		return dependentChildMap;
	}
	
	public OtherTable[] getOtherTables(){
		if(msOtherMap == null) {
			return null;
		}
		OtherTable[] sTables = new OtherTable[msOtherMap.size()];
		Iterator it = msOtherMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			OtherTable table = (OtherTable)it.next();
			sTables[i++] = table;
		}
		return sTables;
	}
	
	public DependentTable getDependentTable(String tableName){
		if(tableMap == null) {
			return null;
		}
		Iterator it = tableMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			DependentTable table = (DependentTable)it.next();
			String name = table.getTableName();
			if(name.equals(tableName)) {
				return table;
			}
		}
		return null;
	}
	
	public TableField[] getTableFields(String tableName){
		if(tableMap == null) {
			return null;
		}
		return getDependentTable(tableName).getTableFields();
	}
	
	public Notes[] getNoteNodes(){
		if(notesMap == null) {
			return null;
		}
		Notes[] sNotes = new Notes[notesMap.size()];
		Iterator it = notesMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			Notes table = (Notes)it.next();
			sNotes[i++] = table;
		}
		return sNotes;
	}
	/**
	 * @AUDIT_ENHANCEMENT_CHANGES
	 * @return
	 */
	public Question[] getQuestions(){
		if(questionMap == null) {
			return null;
		}
		Question[] sTables = new Question[questionMap.size()];
		Iterator it = questionMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			Question table = (Question)it.next();
			sTables[i++] = table;
		}
		return sTables;
	}

	//PW_FORM_VERSION Starts
	public void setVersionMap(SequenceMap versionMap) {
		this.versionMap = versionMap;
	}

	public SequenceMap getVersionMap() {
		return versionMap;
	}
	
	public String getVersionValue(String version) {
		return (String)versionMap.get(TableXMLDAO.V+version);
	}
	
	public boolean isValidVersion(String version) {
		boolean isValid = true;
		if(versionMap != null && versionMap.size() > 0){
			String val	=	(String)versionMap.get(TableXMLDAO.V+version);
			isValid = !("NA".equalsIgnoreCase(val) || "D".equalsIgnoreCase(val) || "DEL".equalsIgnoreCase(val));
		}
		return isValid;
	}
	
	public String getHeaderVersion() {
		String hdrVer = Version.getVersion();
		if(versionMap != null && versionMap.size() > 0){
			hdrVer	=	(String)versionMap.get(TableXMLDAO.NAME);
		}
		return hdrVer;
	}
	//PW_FORM_VERSION Ends
	//P_Enh_FormBuilder_Tabular_Section starts
	public String getTabularSectionTableAnchor(){
		return tabularSectionTableAnchor;
	}
	public String getTabularSectionDbTable(){
		return tabularSectionDbTable;
	}
	public Boolean isTabularSection(){
		return isTabularSection;
	}
	//P_Enh_FormBuilder_Tabular_Section ends
}