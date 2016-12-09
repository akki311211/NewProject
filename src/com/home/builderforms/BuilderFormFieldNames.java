package com.home.builderforms;
/**
 *  This class is the central location to store the names of Add/Modified fields used in FIM forms
 *@author abhishek gupta
 *@created Nov 1, 2011

-----------------------------------------------------------------------------------------------------------------------------------------------
Version No.				Date			By						Against			Comments
-----------------------------------------------------------------------------------------------------------------------------------------------
BOEFLY_INTEGRATION		21/08/2014		Veerpal Singh			Enh				A third party integration with Boefly through REST-API for lead sync.
-----------------------------------------------------------------------------------------------------------------------------------------------
**/
 public class BuilderFormFieldNames {
	 public static final String BUILDER_FORM_ID				= "builderFormId";
	 public static final String FORM_NAME					= "formName";
	 public static final String BUILDER_FIELD_NO			= "builderFieldNo";
	 public static final String BUILDER_WEB_FORMS			= "builderWebForms";// BB_Naming_Convention
	 public static final String TABLE_ANCHOR				= "tableAnchor";
	 public static final String TABLE_NAME					= "tableName";
	 public static final String IS_FIM_TABLE				= "fimtable";
	 public static final String IS_ACTIVE					= "isActive";
	 public static final String FILE_LOCATION				= "fileLocation";
	 public static final String OTHER_TABLE_NAME			= "otherTableName";
	 public static final String XML_FILE_ANCHOR				= "xmlFileANchor";
	 
	 public static final String FIELD_ID					= "fieldId";
	 public static final String FIELD_NAME					= "fieldName";
	 public static final String PRE_DISPLAY_NAME			= "preDisplayName";
	 public static final String DISPLAY_NAME				= "displayName";
	 public static final String HTML_DISPLAY_NAME			= "htmlDisplayName";
	 public static final String DATA_TYPE					= "dataType";
	 public static final String FROM_WHERE					= "fromWhere";
	 public static final String FIELD_NO					= "fieldNo";
	 public static final String ORDER						= "order";
	 public static final String ORDER_NO					= "orderNo";
	 public static final String NO_OF_ROWS					= "noOfRows";
	 public static final String NO_OF_COLS					= "noOfCols";
	 public static final String EXPORTABLE					= "exportable";
	 public static final String SEARCHABLE					= "searchable";
	 public static final String FIM_BUILDER_FIELD			= "fimBuilderField";
	 public static final String ACTION						= "action";
	 public static final String ACTION_TYPE					= "actionType";
	 public static final String FORM_ID						= "formID";
	 public static final String YES							= "Yes";
	 public static final String NO							= "No";
	 public static final String EMPTY						= "";
	 public static final String MAP_TYPE					= "mapType";
	 public static final String DB_FIELD					= "dbField";
	 public static final String IS_BUILD_FIELD				= "isBuildField";
	 public static final String IS_BUILD_SECTION			= "isBuildSection";
	 public static final String IS_MANDATORY				= "isMandatory";
	 public static final String MODIFY_FIELD				= "modifyFld";
	 public static final String DB_COLUMN_SEQ				= "dbColumnSeq";
	 public static final String DB_COLUMN_NAME				= "dbColumnName";
	 public static final String DB_COLUMN_TYPE				= "dbColumnType";
	 public static final String DB_COLUMN_LENGTH			= "dbColumnLength";
	 public static final String MAIL_MERGE_ACTIVE			= "mailMergeActive";
	 public static final String MAIL_MERGE_KEYWORD			= "mailmergekeyword";
	 public static final String IS_OTHER_TABLE_FIELD		= "isOtherTableField";
	 public static final String IS_PII_ENABLED				= "isPiiEnabled";
	 public static final String IS_DEFAULT_PII				= "isDefaultPii";
	 public static final String IS_CENTERINFO_FIELD			= "isCenterInfoField";
	 public static final String COLUMN_NAME					= "colName";
	 public static final String COLUMN_TYPE					= "colType";
	 public static final String COLUMN_VALUE				= "colValue";
	 public static final String COLUMN_SPAN					= "colSpan";
	 public static final String DROPDOWN_OPTION				= "dropdownoption";
	 //BB-20150203-259 (Dynamic Response based on parent field response) starts
	 public static final String RADIO_OPTION				= "radioOption"; 
	 public static final String CHECKBOX_OPTION				= "checkboxOption";
	 //BB-20150203-259 (Dynamic Response based on parent field response) ends
	 public static final String FILETYPE_OPTION				= "filetypeoption";
	 public static final String IS_PARENT					= "isParent";
	 public static final String DEPENDENT_FLD				= "dependentFld";
	 public static final String CREATION_DATE				= "creationDate";
	 public static final String USER_NO						= "userNo";
	 public static final String IP_ADDRESS					= "ipAddress";
	 public static final String NOTES						= "notes";
	 
	 public static final String SPACE						= " ";
	 public static final String COLUN						= ":";
	 public static final String COLUN_WITH_SPACE			= " :";
	 
	 public static final String COL_FORM_NAME				= "formname";
	 public static final String COL_CONTEXT_NAME			= "contextname";
	 public static final String COL_TYPE					= "type";
	 public static final String COL_SPAN					= "colspan";
	 public static final String COL_VALUE					= "value";
	 public static final String COL_DISPLAY					= "display";
	 public static final String COL_CLASS					= "class";

	 public static final String SECTION						= "section";
	 public static final String SECTION_NAME				= "sectionName";
	 public static final String SECTION_VALUE				= "sectionValue";
	 public static final String DOCUMENT_TITLE				= "docTitle";
	 public static final String DOCUMENT_OPTION				= "docOption";
	 public static final String DOCUMENT_LABEL				= "docLabel";
	 public static final String DISPLAY_TYPE				= "displayType";
	 public static final String NO_OF_COLUMN				= "noOfCols";
	 public static final String NO_OF_ROW					= "noOfRows";
	 public static final String OPTION_ID					= "optionId";
	 public static final String OPTION_VALUE				= "optionValue";
	 public static final String TYPE						= "type";
	 
	 public static final String CHECK_ALLOW_COMMENT						= "checkAllowComment";   
	 
	 public static final String FIM_DOCUMENT_TITLE			= "fimDocumentTitle";
	 public static final String FIM_DOCUMENT_ATTACHMENT		= "fimDocumentAttachment";
	 public static final String IS_SOURCE_FIELD				= "isSourceField";
	 public static final String FLD_VALIDATION_TYPE			= "fldValidationType";
	 public static final String XML_ELEMENT					= "xmlElement";
	 public static final String XML_KEY						= "xmlKey";
	 public static final String KEY_TYPE					= "keyType";
	 public static final String SYNC_DATA     				= "syncData";
 	//ENH_MODULE_CUSTOM_TABS starts	 
	 public static final String TAB_DISPLAY_NAME 			= "tabDisplayName";
	 public static final String FORM_DESCRIPTION 			= "formDescription";
	 public static final String FREQUENCY 					= "frequency";//PW_WITHOUT_PLAN
	 public static final String FORM_NAMES					= "formNames";
	 public static final String TAB_ACTION					= "tabAction";
	 public static final String MODULE_TAB					= "module-tab";
	 public static final String TAB_NAME					= "tab-name";
	 public static final String TAB_DISPLAY		 			= "tab-display";
	 public static final String TAB_DISPLAY_LINK 			= "tab-display-link";
	 public static final String TAB_IS_ACTIVE	 			= "is-active";
	 public static final String TAB_IS_EXPORTABLE			= "is-exportable";
	 public static final String TAB_ROW	 					= "tab-row";
	 public static final String DB_TABLE	 				= "db-table";
	 
	 //Field names for module specific xml's modification
	 public static final String BUILDER_TABS 				= "buildertabs";
	 public static final String TAB			 				= "tab";
	 public static final String ID_FIELD	 				= "idField";
	 public static final String ROW			 				= "row";
	 public static final String TABS		 				= "tabs";
	 public static final String SUB_MODULE	 				= "subModule";
	 public static final String HREF		 				= "href";
	 public static final String PATH		 				= "path";
	 public static final String CONDITION	 				= "condition";
	 public static final String PRIVILEGE_URL 				= "privilegeUrl";
	 public static final String INTERNAL_NAME 				= "internalName";
	 public static final String CAN_DELETE 					= "canDelete";//SMC-20140213-378
	 public static final String OPTION_ACTIVATED			= "optionActivated";//SMC-20140213-378
	 
	 //Fields for logs
	 public static final String ID			 				= "id";
	 public static final String TAB_ID 						= "tabId";
	 public static final String BUILDER_MODULE_NAME			= "builderModuleName";
 	//ENH_MODULE_CUSTOM_TABS ends
	 //BOEFLY_INTEGRATION : START
	 public static final String MODULE_NAME					= "moduleName";
	 public static final String ADD_MORE					= "addMore";
	 //BOEFLY_INTEGRATION : END
	 
 	 public static final String RANDOM_ID  = "randomId";//AUDIT_ENHANCEMENT_CHANGES
 	 public static final String OPTION_VIEW_TYPE			= "optviewtype"; // OPTION_VIEW_H_V
 	 public static final String IS_MULTISELECT              ="isMultiSelect";//BB-20150203-259 (MultiSelect combo in add new field changes)
 	public static final String TAB_ORDER				= "tabOrder";//BB-20150203-259 (Tab Re-ordering changes)
 	public static final String TAB_GROUP				= "tabGroup";//BB-20150203-259 (Tab Re-ordering changes)
 	public static final String IS_CUSTOM = "isCustom";
 	public static final String CRITICAL_LEVEL = "criticalLevel";
 	public static final String IS_SMART_QUESTION				= "isSmartQuestion";
 	public static final String IS_TABULAR_SECTION				= "isTabularSection";//P_Enh_FormBuilder_Tabular_Section
 	public static final String TABLE_MAPPING						= "table-mapping";
 	public static final String TABULAR_SECTION_TABLE_DB_NAME="tabularSectionTableDBName";
 	public static final String TABULAR_SECTION_TABLE_NAME="tabularSectionTableName";
 	public static final String CAN_VIEW_ROLES="viewRoles";//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
 	public static final String CAN_WRITE_ROLES="writeRoles";//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
 	public static final String ROLE_BASE="roleBase";
 	public static final String IS_PARSING_KEYWORD_CONFIGURED="isParsingKeywordConfigured";
 }