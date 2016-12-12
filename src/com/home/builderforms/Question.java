/**
 * @author abhishek gupta
 * @Date Dec 2012
 */

/*
--------------------------------------------------------------------------------------------------------
Version No.					Date			By				Against		Function Changed    Comments
PW_FORM_VERSION				15 July 2013	Veerpal Singh		Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
----------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import java.util.HashMap;
import java.util.Iterator;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Version;
import com.home.builderforms.TableXMLDAO;

public class Question implements Cloneable {
	public static final class QuestionType
	{
		public static final String TEXT				= "Text";
		public static final String TEXTAREA			= "TextArea";
		public static final String NUMERIC			= "Numeric";
		public static final String RADIO			= "Radio";
		public static final String DROPDOWN			= "Combo";
		public static final String CHECKBOX			= "Checkbox";
		public static final String DROPDOWN_MULTI	= "DropdownMulti";
	};
	
	public static final class QuesValidateType
	{
		public static final String INTEGER			= "Integer";
		public static final String DOUBLE			= "Double";
		public static final String PHONE			= "Phone";
		public static final String FAX				= "Fax";
		public static final String EMAIL			= "Email";
		public static final String MANDATORY		= "Mandatory";
		public static final String PERCENTAGE		= "Percentage";
	};
	
	private String msTableName			= null;
	private String msTableAliasName		= null;
	private String tableAnchor			= null;
	private HashMap questionData		= null;
	private SequenceMap questionFldMap;
	private String helpNotes			= "";
	private String tabName				= null;
	private SequenceMap questionResMap	= null;
	private SequenceMap versionMap			= null;//PW_FORM_VERSION
	
	//ENH_PW_SMART_QUESTIONS_STARTS
	private String fieldName = null;
	private String tableName = null;
	private String dbField = null;
	private String dbTableName = null;
	private String moduleName = null;
	private String canEditResponse = null;
	private String transformMethod = null;
	private String sourceMethod = null;
	private String isBuildField = null;
	
	public String getSourceMethod() {
		return sourceMethod;
	}
	
	public void setSourceMethod(String sourceMethod) {
		this.sourceMethod = sourceMethod;
	}
	
	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}

	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCanEditResponse() {
		return canEditResponse;
	}

	public void setCanEditResponse(String canEditResponse) {
		this.canEditResponse = canEditResponse;
	}
	
	public String getTransformMethod() {
		return transformMethod;
	}

	public void setTransformMethod(String transformMethod) {
		this.transformMethod = transformMethod;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getIsBuildField() {
		return isBuildField;
	}

	public void setIsBuildField(String isBuildField) {
		this.isBuildField = isBuildField;
	}
	//ENH_PW_SMART_QUESTIONS_ENDS	

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	//ENH_PW_SMART_QUESTIONS_STARTS
	public Question(String msTableName, HashMap questionData, SequenceMap msBuildMap, String helpNotes, String tabName, SequenceMap quesResFldMap, SequenceMap verMap) {
		this(msTableName, questionData, msBuildMap, helpNotes, tabName, quesResFldMap, verMap, null, null,null, null,null,null, "NA",null,"NA");
	}
	//ENH_PW_SMART_QUESTIONS_ENDS
	public Question(String msTableName, HashMap questionData, SequenceMap msBuildMap, String helpNotes, String tabName, SequenceMap quesResFldMap, SequenceMap verMap, String fieldName, String tableName,String dbField,String dbTableName,String moduleName,String canEditResponse, String sourceMethod, String isBuildField, String transformMethod) {//ENH_PW_SMART_QUESTIONS
		this.msTableName		= msTableName;
		this.questionData 		= questionData;
		this.questionFldMap		= msBuildMap;
		this.helpNotes			= helpNotes;
		this.tabName			= tabName;
		this.questionResMap		= quesResFldMap;
		this.versionMap			= verMap;	//PW_FORM_VERSION
		//ENH_PW_SMART_QUESTIONS_STARTS
		this.fieldName = fieldName;
		this.tableName = tableName;
		this.moduleName = moduleName;
		this.dbField = dbField;
		this.dbTableName = dbTableName;
		this.canEditResponse = canEditResponse;
		this.sourceMethod = sourceMethod;
		this.isBuildField = isBuildField;
		this.transformMethod = transformMethod;
		//ENH_PW_SMART_QUESTIONS_ENDS
	}

	public String getQuestionName() {
		return msTableName;
	}
	
	public QuestionOption[] getQuestionOptions() {
		if(questionFldMap == null) {
			return null;
		}
		QuestionOption[] sqOption = new QuestionOption[questionFldMap.size()];
		Iterator it = questionFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			QuestionOption table = (QuestionOption)it.next();
			sqOption[i++] = table;
		}
		return sqOption;
	}
	
	public QuestionField[] getQuestionResFields() {
		QuestionField[] sqOption = new QuestionField[questionResMap.size()];
		Iterator it = questionResMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			QuestionField table = (QuestionField)it.next();
			sqOption[i++] = table;
		}
		return sqOption;
	}
	
	public QuestionField getQuestionResField(String quesField) {
		Iterator it = questionResMap.values().iterator();
		QuestionField qFld = null;
		
		for (int i=0;it!=null && it.hasNext();) {
			QuestionField table = (QuestionField)it.next();
			if(StringUtil.isValid(quesField) && quesField.equals(table.getFieldName())) {
				qFld = table;
				break;
			}
		}
		return qFld;
	}
	
	public HashMap getAttributesMap() {
		return questionData;
	}
	
	public String getKeyValue(String key) {
		return (String)questionData.get(key);
	}
	
	public String getHelpNotes() {
		return helpNotes;
	}
	
	public String getTableAliasName() {
		// TODO Auto-generated method stub
		return msTableAliasName;
	}
	public String getTableAnchor() {
		// TODO Auto-generated method stub
		return tableAnchor;
	}
	public String getTabName() {
		return tabName;
	}
	
	//PW_FORM_VERSION Starts
	public void setVersionMap(SequenceMap versionMap) {
		this.versionMap = versionMap;
	}

	public SequenceMap getVersionMap() {
		return versionMap;
	}
	
	public String getQueVerKeyValue(String version) {
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
	
	public String getQueVer() {
		String queVer = Version.getVersion();
		if(versionMap != null && versionMap.size() > 0){
			queVer	=	(String)versionMap.get(TableXMLDAO.NAME);
		}
		return queVer;
	}
	//PW_FORM_VERSION Ends

}