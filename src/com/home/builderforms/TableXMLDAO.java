/**
 * Updating this utility according to new Form design pattern.
 * Some Map and Fields are associated with this design to make Generic solution of the problem 
 * @author abhishek gupta
 * @date 2 Jan 2012
 */
/*
 * $Id: TableXMLDAO.java,v 1.32 2016/10/19 13:40:14 sachin Exp $
 * P_B_FIM_75246       8 Jun 2011         Neeti Solanki               field table-internal-name is added for configure the custom report with configure fim tab
 * P_E_MoveToFim_AddlFDD    29Nov2012     Veerpal Singh        Patch
 * PW_WITHOUT_PLAN				27 June 2013	Veerpal Singh		Removing concept of Visit Plans from Performance Wise (Now Visits will create directly with Audit Forms.)
 * PW_FORM_VERSION				15 July 2013	Veerpal Singh		Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
 * P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 * PWISE805_PATCH1		    24-Dec-2014		Rohit Jain          for N/A functionality
 * ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 */
package com.home.builderforms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.home.builderforms.BuilderCustomTab;
import com.home.builderforms.Constants;
import com.home.builderforms.FieldNames;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.ChildField;
import com.home.builderforms.DependentChild;
import com.home.builderforms.DependentTable;
import com.home.builderforms.DocumentMap;
import com.home.builderforms.Documents;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.ForeignTable;
import com.home.builderforms.FormMetaData;
import com.home.builderforms.FormTab;
import com.home.builderforms.HeaderColumn;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.LinkField;
import com.home.builderforms.MainField;
import com.home.builderforms.Notes;
import com.home.builderforms.OtherTable;
import com.home.builderforms.Question;
import com.home.builderforms.QuestionField;
import com.home.builderforms.QuestionOption;
import com.home.builderforms.ScoreMappings;
import com.home.builderforms.ScoreSection;
import com.home.builderforms.SyncWithField;
import com.home.builderforms.TableField;
import com.home.builderforms.Trigger;
import com.home.builderforms.Info;

public class TableXMLDAO {
    // constants
    public static final String TABLE 								= "table";
    public static final String TABLE_NAME 							= "table-name";
    public static final String TABLE_DISPLAY_NAME	 				= "table-display-name";
    public static final String ENTITY_TABLE_DISPLAY_NAME	 		= "entity-table-display-name";
    public static final String TABLE_EXPORT_DISPLAY_NAME			= "table-export-display-name";
    //P_B_FIM_75246 added by neeti starts
    public static final String TABLE_INTERNAL_NAME					= "table-internal-name";
    //P_B_FIM_75246 added by neeti ends	
    public static final String CONNECTION_NAME		 				= "connection-name";
    public static final String TABLE_ANCHOR 						= "table-anchor";
    public static final String ID_FIELD 							= "id-field";
    public static final String SEQUENCE_FIELD 						= "sequence-field";
    public static final String IS_MULTISELECT                       = "is-multiselect"; //BB-20150203-259 (MultiSelect combo)
    
    public static final String SUMMARY		 						= "summary";
    public static final String FIELD 								= "field";
    public static final String FIELD_NAME 							= "field-name";
    public static final String DB_FIELD 							= "db-field";
    public static final String DISPLAY_NAME 						= "display-name";
    public static final String DISPLAY_EXPORT_NAME 					= "display-export-name";
    public static final String QUERY_PARAMS 						= "query-params";
    public static final String VALUE								= "value";

    public static final String TABLE_MAPPING						= "table-mapping";
   	public static final String NAME									= "name";
   	public static final String FUNCTION								= "function"; //BB-20150203-259 (Dynamic Response based on parent field response)
   	public static final String PARENT_FIELD							= "parent-field"; //BB-20150203-259 (Dynamic Response based on parent field response)
    public static final String FILELOCATION							= "filelocation";
    public static final String PARAM								= "param";
    public static final String DATA_TYPE							= "data-type";
    public static final String WIDTH								= "width";
    public static final String COLSPAN								= "colspan"; //Added by Anuj 2005-07-23
    public static final String COLSPAN_EXPORT						= "colspan-export";
    public static final String COLSPAN_MERGE						= "colspan-merge";

	public static final String FOREIGN_TABLES						= "foreign-tables";
	public static final String FOREIGN_TABLE						= "foreign-table";
	public static final String THIS_FIELD							= "thisField";
	public static final String FOREIGN_FIELD						= "foreignField";
	public static final String FOREIGN_FIELD_VALUE					= "foreignFieldValue";
	public static final String SKIP_THIS_FIELD						= "skipThisField";
	public static final String CHILD_TABLE					        = "child-table";
	public static final String TABLE_EXPORT							= "table-export";
	public static final String TABLE_IMPORT							= "table-import";
	public static final String DEPENDENT_MODULE						= "dependentModule";
	public static final String REQUIRED								= "required";
	
	public static final String LINK_FIELD							= "link-field";
	public static final String SRC_TABLE							= "src-table";
	public static final String SRC_FIELD							= "src-field";
	public static final String SRC_VALUE	 						= "src-value";
	public static final String TRANSFORM_METHOD						= "transform-method";
	public static final String TRANSFORM_METHOD_PARAM				= "transform-method-param";
	public static final String TRANSFORM_METHOD_EXPORT_PARAM		= "transform-method-export-param";
	public static final String TRANSFORM_EXPORT_METHOD				= "transform-export-method";
	public static final String TRANSFORM_EXPORT_METHOD_PARAM		= "transform-export-method-param";
	public static final String TRANSFORM_METHOD_IN_EXPORT			= "transform-method-in-export";
	
	public static final String FIELD_METHOD							= "field-method";
	public static final String FIELD_METHOD_PARAM					= "field-method-param";
	public static final String SEARCH								= "search";
	public static final String SEARCH_FIELD							= "search-field";
	public static final String SEARCH_SOURCE_VALUES_METHOD			= "search-source-values-method";
	public static final String SEARCH_SOURCE_METHOD_ARGS 			= "search-source-method-args";	

	public static final String SEARCH_SOURCE_VALUES_URL				= "search-source-values-url";
	
	public static final String RADIO								= "radio";
	public static final String RADIO_SOURCE_VALUES_METHOD 			= "radio-source-values-method";
	public static final String RADIO_SOURCE_METHOD_ARGS			 	= "radio-source-method-args";
	public static final String RADIO_METHOD_PARAM 					= "radio-method-param";
	public static final String RADIO_METHOD_OTHER_FIELD				= "radio-source-method-other-field";
	public static final String RADIO_METHOD_OTHER_FIELD_ARGS		= "radio-source-method-other-field-args";
	public static final String RADIO_SOURCE_OTHER_FIELD_METHOD		= "radio-source-other-field-method";
	
	public static final String CHECKBOX								= "checkbox";
	public static final String CHECKBOX_SOURCE_VALUES_METHOD 		= "checkbox-source-values-method";
	public static final String CHECKBOX_SOURCE_METHOD_ARGS 			= "checkbox-source-method-args";
	public static final String CHECKBOX_METHOD_PARAM 				= "checkbox-method-param";
	public static final String CHECKBOX_METHOD_OTHER_FIELD			= "checkbox-source-method-other-field";
	public static final String CHECKBOX_METHOD_OTHER_FIELD_ARGS 	= "checkbox-source-method-other-field-args";
	public static final String CHECKBOX_SOURCE_VALUES_COL_COUNT 	= "checkbox-source-values-col-count";

	public static final String CHECKBOX_METHOD_RETURN_TYPE 			= "checkbox-method-return-type";
	public static final String CHECKBOX_CONDITION_FIELD 			= "checkbox-condition-field";
	public static final String CHECKBOX_CONDITION_FIELD_VALUE 		= "checkbox-condition-field-value";
	
	//BB-20150203-259 (Dynamic Response based on parent field response) starts
	public static final String DEPENDENT							= "dependent";
	public static final String DEPENDENT_PARENT						= "dependent-parent";
	public static final String DEPENDENT_CHILD						= "dependent-child";
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
	
	//deepak starts
	public static final String COMBO								= "combo";
	public static final String COMBO_SOURCE_VALUES_METHOD		 	= "combo-source-values-method";
	public static final String COMBO_SOURCE_METHOD_ARGS 			= "combo-source-method-args";
	public static final String COMBO_METHOD_PARAM 					= "combo-method-param";
	public static final String PARENT 								= "parent";
	public static final String DEPENDENT_FIELD		 				= "dependent-field";
	public static final String PII_ENABLED							= "pii-enabled";
	public static final String DEFAULT_PII_ENABLED						= "default-pii-enabled";
	public static final String CENTER_INFO_DISPLAY					= "center-info-display";
	//deepak ends
	public static final String COMBO_METHOD_OTHER_FIELD				= "combo-source-method-other-field";
	public static final String COMBO_METHOD_OTHER_FIELD_ARGS		= "combo-source-method-other-field-args";
	public static final String COMBO_METHOD_OTHER_FIELD_SPAN		= "combo-source-method-other-field-span";
	
	public static final String RECORD_TRIGGER						= "record-trigger";
	public static final String FIELD_TRIGGER						= "field-trigger";
	public static final String FIELD_WISE							= "fieldWise";
	public static final String INSERTION							= "insertion";
	public static final String MODIFICATION							= "modification";
	public static final String DELETION								= "deletion";
	public static final String FIELD_EXPORT							= "field-export";
	public static final String VALIDATION							= "validation";
	public static final String VALIDATION_TYPE						= "validation-type";
	//code for adding file-field in xml
	public static final String FILE_FIELD							= "file-field";
	//end
	public static final String FIELD_OPTION_VIEW					= "field-option_view"; // OPTION_VIEW_H_V
	// added by Sunilk code for custom field export
	public static final String CUSTOM_FIELD							= "custom-field";
	//end
	public static final String CURRENCY_FIELD						= "isCurreny";
	public static final String GROUP_BY								= "group-by";
	public static final String IS_ACTIVE							= "is-active";
    public static final String IS_DEPENDENT_TABLE_FIELD				= "is-dependent-table-field";
    public static final String DEPENDENT_TABLE_NAME                 = "dependent-table-name";
    public static final String IS_BUILD_FIELD						= "build-field";
	public static final String BUILD_FIELD_SEQ						= "build-field-seq";
	public static final String ORDER_BY								= "order-by";
	public static final String IS_MANDATORY							= "is-mandatory";
	public static final String TABLE_HEADER_MAP						= "table-header-map";
	public static final String HEADER		  						= "header";
	public static final String DEPENDENT_TABLE						= "dependent-table";
	public static final String TABLE_FIELD							= "table-field";
	public static final String DOCUMENTS							= "documents";
	public static final String DOCUMENT								= "document";
	public static final String NOTES								= "notes";
	public static final String HEADING								= "heading";
	public static final String CONTENT								= "content";
	public static final String ID_MAP								= "id-map";
	public static final String TABLE_MAP							= "table-map";
	public static final String OTHER_TABLE							= "other-table";
	public static final String MAIN_FIELD							= "main-field";
	public static final String CHILD_FIELD							= "child-field";
	
	//P_Enh_Sync_Fields starts
	public static final String SYNC_WITH							= "sync-with";
	public static final String SYNC_MODULE							= "sync-module";
	public static final String SYNC									= "sync";
	public static final String SYNC_DATA							= "sync-data";
	public static final String READ_ONLY							= "read-only";
	//P_Enh_Sync_Fields ends
	
	public static final String TYPE									= "type";
	public static final String SECTION								= "section";
	public static final String ORDER								= "order";
	public static final String DISPLAY_TYPE							= "display-type";
	public static final String NO_OF_COLUMN							= "no-of-column";
	public static final String NO_OF_ROW							= "no-of-row";
	public static final String VALID_INPUT							= "valid-input";
	public static final String DB_FIELD_LENGTH 						= "db-field-length";
	public static final String IS_BUILD_SECTION						= "is-build-section";
	public static final String FIELD_AS_HEADER						= "field-as-header";
	public static final String MAILMERGE							= "mailmerge";
	public static final String KEYWORD_NAME							= "keyword-name";
	public static final String URL_MAPPING							= "url-mapping";
	public static final String URL_FORM_MAPPING						= "url-form-mapping";
	public static final String ACTION_PATH							= "action-path";
	public static final String TAB_MAPPING							= "tab-mapping";
	public static final String MODULE_NAME							= "module-name";
	public static final String HREF									= "href";
	
	public static final String MODIFY_PATH							= "modify-path";
	public static final String VIEW_PATH							= "view-path";
	public static final String DELETE_PATH							= "delete-path";
	public static final String ADD_PATH								= "add-path";
	public static final String PRIV_ADD_PATH						= "priv-add-path";
	public static final String PRINT_PATH							= "print-path";
	public static final String FORM_NAME							= "form-name";
	public static final String TABLE_WITH_ALIAS						= "tableWithalias";
	public static final String J_SCRIPT_FUNCT						= "jScriptFunct";
	public static final String TAB_NAME								= "tabName";
	public static final String DROPDOWN_OPTION						= "dropdown-option";
	//BB-20150203-259 (Dynamic Response based on parent field response) starts
	public static final String RADIO_OPTION	  						= "radio-option";
	public static final String CHECKBOX_OPTION						= "checkbox-option";
	//BB-20150203-259 (Dynamic Response based on parent field response) ends
	public static final String COUNT								= "count";
	public static final String TITLE_NAME							= "title-name";
	public static final String TITLE_DISPLAY						= "title-display";
	public static final String TITLE_COLUMN							= "title-column";
	public static final String DOC_NAME								= "doc-name";
	public static final String DOC_DISPLAY							= "doc-display";
	public static final String DOC_COLUMN							= "doc-column";
	public static final String DOC_OPTION							= "doc-option";
	
	public static final String SOURCE_FIELD							= "source-field";
	public static final String ALLOW_FIELD_IN_MAIN					= "allow-field-in-main";
	public static final String COMBINED_DISPLAY_NAME				= "combined-display-name";
	public static final String MAILMERGE_TRANSFORM_METHOD			= "mailmerge-transform-method";
	public static final String FIELD_CALLED_METHOD					= "field-method-call";
	public static final String CONDITION_FIELD						= "condition-field";
	public static final String CONDITION_FIELD_VALUE				= "condition-field-value";
	
	// BUILDER FORM TAB START
	public static final String BUILDER_TAB_MAPPINGS					= "builder-tab-mappings";
	public static final String BUILDER_TAB_MAPPING					= "builder-tab-mapping";
	
	// BUILDER FORM TAB END
	
	/*P_E_MoveToFim_AddlFDD starts*/
	public static final String OPTIONAL	                            = "optional";
	public static final String RECORD_HEADER_PREFIX		            = "record-header-prefix";
	public static final String RECORD_HEADER_SUFIX		            = "record-header-sufix";
	/*P_E_MoveToFim_AddlFDD ends*/
	/**
	 * @AUDIT_ENHANCEMENT_CHANGES Starts 
	 */
	public static final String SCORE_TEMPLATE_NAME                  = "score-template-name";
	public static final String SCORE_TEMPLATE_DISPLAY               = "score-template-display";
	public static final String SCORE_SECTIONS		                = "score-sections";
	public static final String SCORE_SECTION		                = "score-section";
	public static final String DISPLAY		                		= "display";
	public static final String SCORE_TYPE							= "score-type";
	public static final String SCORE_MAX							= "score-max";
	public static final String CALL_METHOD							= "call-method";
	public static final String SCORE_LOW							= "score-low";
	public static final String SCORE_FIELD							= "score-field";
    public static final String TOTAL_SCORE							= "total-score";
    public static final String ACTUAL_SCORE							= "actual-score";
    public static final String IS_SCORING							= "is-scoring";
    public static final String IS_PERCENTAGE						= "is-percentage";
    public static final String TOTAL_SCORE_PARAM					= "total-score-param";
    public static final String ACTUAL_SCORE_PARAM					= "actual-score-param";
    public static final String QUESTION								= "question";
    public static final String QUESTION_NAME						= "question-name";
    public static final String QUESTION_OPTION						= "question-option";
    public static final String HELP_NOTES							= "help-notes";
    public static final String FORM_META_DATA						= "form-meta-data";
    public static final String IS_PRIVATE							= "is-private";
    public static final String EXCLUDE_NON_RESPONSE					= "excludeNonResponse";
    public static final String NOT_APPLICABLE					    = "notApplicableResponse";//PWISE805_PATCH1
  //starts SmartQuestion
    public static final String IS_SMART_QUESTION					    = "isSmartQuestion";
    public static final String MODULE_FIELD_NAME					    = "module-field-name";
    public static final String MODULE_TABLE_NAME					    = "module-table-name";
    public static final String CAN_EDIT_RESPONSE					    = "can-edit-Response";
    public static final String SMART_QUESTION					    = "smart-question";
    public static final String SOURCE_METHOD					    = "source-method";
  //ends SmartQuestion

    public static final String DISPLAY_FORMAT						= "display-format";
    public static final String DISPLAY_VIEW							= "display-view";
    public static final String RESPONSE_COUNT						= "response-count";
    public static final String HEADER_COLUMN						= "header-column";
    public static final String TAB 									= "tab";
    public static final String TABS									= "tabs";
    public static final String TAB_SUB_MODULE						= "subModule";
    public static final String TAB_GROUP							= "tabGroup";
    public static final String ROW									= "row";
    public static final String AUDIT_FORM_ID						= "audit-form-id";
    public static final String TABS_NAME							= "tab-name";
    public static final String QUESTION_HEADER						= "question-header";
    public static final String ALLOW_COMMENTS						= "allow-comments";
    public static final String ALLOW_SCORE_HEADER					= "allow-score-header";
    public static final String ALLOW_MAX_SCORE_HEADER				= "allow-max-score-header";
    public static final String QUES_FIELD							= "ques-field";
    public static final String IS_OWNER_RESPONSE					= "is-owner-response";
    public static final String FREQUENCY							= "frequency";//PW_WITHOUT_PLAN
    public static final String MIN_SCORE							= "min-score";
    public static final String MAX_SCORE							= "max-score";
    public static final String COMP_LIMIT							= "compLimit";
    public static final String COMP_TYPE							= "comp-type";
    public static final String ACTION_PLAN							= "action-plan";
    public static final String RESPONSE_TYPE						= "response-type";
    public static final String WITH_ATTACHMENT						= "with-attachment";
    public static final String IS_OWNER								= "is-owner";
    public static final String IS_SCORABLE							= "isScorable";
    public static final String ACTION_MANDATORY						= "actionMandatory";
    public static final String IS_ACTION_REQUIRED					= "isActionRequired";//PW_805_Is_Action_Req
    //public static final String HIGH_PRIORITY_QUESTION				= "highPriorityQuestion";//HIGH_PRIORITY_QUESTION
    public static final String IS_ADMIN								= "isAdmin";
    public static final String QUESTION_TYPE						= "question-type";
    public static final String WITH_COMMENT							= "with-comment";
    public static final String IS_OWNER_FIELD						= "is-owner-field";
    public static final String IS_COMMENT_FIELD						= "is-comment-field";
    // AUDIT_ENHANCEMENT_CHANGES Ends
    
    public static final String COMMENT_VISIBLE						= "comment-visible";
    
    /** PW_FORM_VERSION Starts **/
    public static final String V									= "v";
    public static final String VERSION								= "version";	
    public static final String CURRENT_VERSION						= "current-version";
    public static final String PRE_VERSION							= "pre-version";
    public static final String TAB_VERSION							= "tab-version";
    /** PW_FORM_VERSION Ends **/
    
	//ENH_MODULE_CUSTOM_TABS starts
	public static final String MODULE								= "module";
	public static final String SUB_MODULE							= "sub-module";
	public static final String TABLE_ARCHIVE_NAME					= "table-archive-name";
	public static final String NO_WRAP								= "nowrap";
    public static final String INTERNATIONALIZATION					= "internationalization";
    //ENH_MODULE_CUSTOM_TABS ends
	
	public static final String INCLUDE_IN_CAMPAIGN                  = "include-in-campaign"; //BB-20150319-268(FIM Campaign Center)
	public static final String CRITICAL_LEVEL                       = "critical-level";    //ENH_CRITICAL_LEVEL
	
	public static final String ALLOW_COMMENT                       = "allow-comment"; //PW_ENH_PRIVATE_COMMENT
	
	//CUSTOM_REPORT_SORTING_ISSUE starts
	public static final String CAST_IN_ORDER					= "cast-in-order";
	public static final String CUSTOM_SORTABLE					= "custom-sortable";
	//CUSTOM_REPORT_SORTING_ISSUE ends
	
	public static final String JS_VALIDATION_METHOD				= "js-validation-method";  // SMC_CRM
	public static final String MAX_ORDER_BY						="max-order-by";
	public static final String IS_TABULAR_SECTION						="is-tabular-section";
	public static final String TABULAR_SECTION_TABLE_ANCHOR						="tabular-section-table-anchor";
	public static final String TABULAR_SECTION_DB_TABLE						="tabular-section-db-table";
	public static final String IS_PARSING_KEYWORD_CONFIGURED ="is-parsing-keyword-configured";
	public static HashMap getTableMappings(String location)
	{
		HashMap tableMappings = new HashMap();
		Element root = XMLUtil.loadDocument(location);
		NodeList list = root.getElementsByTagName(TABLE_MAPPING);
		for (int i = 0;i<list.getLength();i++)
		{
		  Node node				= list.item(i);
		  String tableAnchor 	= XMLUtil.getAttributeValue(node,TABLE_ANCHOR);
		  String fileLocation	= XMLUtil.getAttributeValue(node,FILELOCATION);
		  tableMappings.put(tableAnchor,fileLocation);
		}
		return tableMappings;
	}
	/**
	 * This method returns table mappings for tabular sections.
	 * @author Akash Kumar
	 * @param location
	 * @param tableMappings
	 * @return tableMappings
	 */
	public static HashMap getTabularSectionMappings(String location)
	{
		HashMap tabularSectionMappings = new HashMap();
		Element root = XMLUtil.loadDocument(location);
		NodeList list = root.getElementsByTagName(TABLE_MAPPING);
		for (int i = 0;i<list.getLength();i++)
		{
		  Node node				= list.item(i);
		  String tableAnchor 	= XMLUtil.getAttributeValue(node,TABLE_ANCHOR);
		  String fileLocation	= XMLUtil.getAttributeValue(node,FILELOCATION);
		  tabularSectionMappings.put(tableAnchor,fileLocation);
		}
		return tabularSectionMappings;
	}
	/**
	 * Allow getting List of Builder custom table(s) mappings
	 * @param location
	 * @return
	 */
	public static HashMap getBuilderTableMappings(String location)
	{
		HashMap buildertableMappings = new HashMap();
		Element root = XMLUtil.loadDocument(location);
		NodeList list = root.getElementsByTagName(BUILDER_TAB_MAPPING);
		for (int i = 0;i<list.getLength();i++)
		{
		  Node node				= list.item(i);
		  String tableAnchor 	= XMLUtil.getAttributeValue(node,TABLE_ANCHOR);
		  String fileLocation	= XMLUtil.getAttributeValue(node,FILELOCATION);
		  buildertableMappings.put(tableAnchor,fileLocation);
		}
		return buildertableMappings;
	}
	
	public static Object getBuilderFileXMLAttr(String location, String element, String key, String keyType) {
		try {
			SequenceMap builderMappings = new SequenceMap();
			Element root = XMLUtil.loadDocument(location);
			NodeList list = root.getElementsByTagName(element);
			if(!StringUtil.isValid(keyType)) {
				keyType = "SequenceMap";
			}
			for (int i = 0;i<list.getLength();i++) {
				if("Info".equals(keyType)) {
					Info keyInfo= (Info)XMLUtil.getObjectNodeAttributes(list.item(i), keyType);
					builderMappings.put(keyInfo.get(key),keyInfo);
				} else if("SequenceMap".equals(keyType)) {
					SequenceMap keyData= (SequenceMap)XMLUtil.getObjectNodeAttributes(list.item(i), keyType);
					
					builderMappings.put(keyData.get(key),keyData);
				}
			}
			return builderMappings;
		} catch(Exception e) {
			return null;
		}
	}
	
	public static HashMap getTableVsUrlMappings(String location) {
		HashMap urlMappings = new HashMap();
		HashMap tableData = null;
		Element root = XMLUtil.loadDocument(location);
		NodeList list = root.getElementsByTagName(URL_MAPPING);
		for (int i = 0;i<list.getLength();i++) {
			tableData = XMLUtil.getNodeAttributes(list.item(i));
			urlMappings.put(tableData.get(ACTION_PATH), tableData);
		}
		
		list = root.getElementsByTagName(URL_FORM_MAPPING);
		for (int i = 0;i<list.getLength();i++) {
			tableData = XMLUtil.getNodeAttributes(list.item(i));
			urlMappings.put(tableData.get(ACTION_PATH), tableData);
		}

		list = root.getElementsByTagName(TAB_MAPPING);
		for (int i = 0;i<list.getLength();i++) {
			tableData = XMLUtil.getNodeAttributes(list.item(i));
			urlMappings.put(tableData.get(TABLE_ANCHOR), tableData);
		}

		return urlMappings;
	}
	
	public static HashMap<String,String>[] getTableVsUrlMappingsByTag(String location,String tagName,String keyTagName) {
		return getTableVsUrlMappingsByTag(location, tagName, keyTagName, null);
	}
	public static HashMap<String,String>[] getTableVsUrlMappingsByTag(String location,String tagName,String keyTagName,String moduleName) {//P_B_FIM_60836
		HashMap<String,HashMap> urlMappings = new HashMap();
		HashMap<String,String> tableData = null;
		Element root = XMLUtil.loadDocument(location);
		NodeList list = root.getElementsByTagName(tagName);
		for (int i = 0;i<list.getLength();i++) {
			tableData = XMLUtil.getNodeAttributes(list.item(i));
			if(StringUtil.isValid((String)tableData.get(ORDER_BY)) && (!StringUtil.isValid(moduleName) || moduleName.equals((String)tableData.get(MODULE_NAME)))) {//P_B_FIM_60836
				urlMappings.put((String)tableData.get(keyTagName), tableData);
			}
		}
		HashMap<String,String>[] urlMappingsArr =  new HashMap[urlMappings.size()];
		Iterator tableVsUrlMappingItr 				= urlMappings.keySet().iterator();
		while(tableVsUrlMappingItr.hasNext()) {
			HashMap<String,String> urlMap 	= urlMappings.get(tableVsUrlMappingItr.next());
			int orderBy = Integer.parseInt(urlMap.get(ORDER_BY));
			urlMappingsArr[orderBy] = urlMap;
		}
		return urlMappingsArr;
	}
	
	public static String[][] getQueryParams(Element root)
	{
		Node node = XMLUtil.getTagNode(root,QUERY_PARAMS);
		if (node==null)
		{
			return null;
		}
		Node[] paramNodes = XMLUtil.getNodesInChildren(node,PARAM);
		ArrayList alist = new ArrayList();
		for (int i=0; i <paramNodes.length;i++)
		{
			String name = XMLUtil.getAttributeValue(paramNodes[i],NAME);
			String value = XMLUtil.getAttributeValue(paramNodes[i],VALUE);

			alist.add(new String[]{name,value});
		}
		String[][] queryParams = new String[alist.size()][2];
		for (int i=0;i<alist.size();i++)
		{
			queryParams[i]=(String [])alist.get(i);
		}
		return queryParams;
	}

	public static void setTriggers(String location, FieldMappings mapping)throws IOException{
		Document doc			= XMLUtil.getDocument(location);
		Element root			= doc.getDocumentElement();
		Element recordTriggerElement
								= (Element)XMLUtil.getTagNode(root, RECORD_TRIGGER);

		if(recordTriggerElement == null){
			recordTriggerElement
								= doc.createElement(RECORD_TRIGGER);
		}

		Trigger recordTrigger	= mapping.getRecordTrigger();

		recordTriggerElement.setAttribute(
								INSERTION,
								String.valueOf(recordTrigger.insertionTrigger())
									);
		recordTriggerElement.setAttribute(
								MODIFICATION,
								String.valueOf(recordTrigger.modificationTrigger())
									);
		recordTriggerElement.setAttribute(
								DELETION,
								String.valueOf(recordTrigger.deletionTrigger())
									);
		recordTriggerElement.setAttribute(
								FIELD_WISE,
								String.valueOf(recordTrigger.fieldWiseTrigger())
									);

		NodeList list = root.getElementsByTagName(FIELD);
        for (int loop = 0; loop < list.getLength(); loop++){
            Element element			= (Element)list.item(loop);
			Element fieldTriggerElement
									= (Element)XMLUtil.getTagNode(
														element,
														FIELD_TRIGGER
															);
			String fieldName		= XMLUtil.getTagText(element,FIELD_NAME);
			Field field				= mapping.getField(fieldName);
			Trigger fieldTrigger	= field.getTrigger();
			if(fieldTrigger == null){
				continue;
			}
			if(fieldTriggerElement == null){
				fieldTriggerElement
									= doc.createElement(RECORD_TRIGGER);
			}
			fieldTriggerElement.setAttribute(
									INSERTION,
									String.valueOf(recordTrigger.insertionTrigger())
									);
			fieldTriggerElement.setAttribute(
									MODIFICATION,
									String.valueOf(recordTrigger.modificationTrigger())
									);
			fieldTriggerElement.setAttribute(
									DELETION,
									String.valueOf(recordTrigger.deletionTrigger())
									);
			element.appendChild(fieldTriggerElement);
		}
		root.appendChild(recordTriggerElement);
		XMLUtil.writeDOMToFile(doc, location);
	}
	
    public static FieldMappings getFieldMappings(String location)
    {
    	FieldMappings fieldMappings = null;
    	try {
//    		System.out.println("location in parsin xml file -- "+location);
        Element root					= XMLUtil.loadDocument(location);
        String connectionName		 	= MultiTenancyUtil.getTenantName();
        String tableName 				= XMLUtil.getTagText(root,TABLE_NAME);
		String tableDisplayName 		= XMLUtil.getTagText(root,TABLE_DISPLAY_NAME);
		String entityTableDisplayName 		= XMLUtil.getTagText(root,ENTITY_TABLE_DISPLAY_NAME);
		String tableExportDisplayName 	= XMLUtil.getTagText(root,TABLE_EXPORT_DISPLAY_NAME);
		String tableModule				= XMLUtil.getTagText(root,MODULE);//ENH_MODULE_CUSTOM_TABS
		
		//P_B_FIM_75246 added by neeti starts
		String tableInternalName = XMLUtil.getTagText(root,TABLE_INTERNAL_NAME);
		//P_B_FIM_75246 added by neeti ends
		String seqField			 = XMLUtil.getTagText(root,SEQUENCE_FIELD);
		
		SequenceMap headerMap = new SequenceMap();
		SequenceMap smap=new SequenceMap();
		HashMap tableData = null;
		HashMap docData = null;
		SequenceMap notesData = null;
		SequenceMap docMap = null;
		SequenceMap notesMap = null;
		SequenceMap tableFldData = null;
		SequenceMap docFldData = null;
		SequenceMap idAttrMap	 = null;
		SequenceMap idMap	 = null;
		SequenceMap tableAttrMap = null;
		SequenceMap tableMap = null;
		SequenceMap otherTableAttrMap = null;
		SequenceMap otherTablesMap = null;
		SequenceMap otherTablesNodeMap=null;
		SequenceMap mainFldData = null;
		SequenceMap childFldData = null;
		SequenceMap headerAttrFldData = null;
		SequenceMap questionExtMap = null; // Allow question outside header
		SequenceMap tabsData = null;
		/**
		 * @AUDIT_ENHANCEMENT_CHANGES
		 * Form Meta data section which contains all basic data which used for top level design concept
		 * START
		 */
		SequenceMap metaDataMap = null;
		Node formMetaDataNode	= XMLUtil.getTagNode(root, FORM_META_DATA);
		if(formMetaDataNode != null) {
			metaDataMap = new SequenceMap();
			SequenceMap headerDataMap 	= null;
			SequenceMap tabDataMap 		= null;
			
			String aFormId 		= XMLUtil.getTagText(formMetaDataNode, AUDIT_FORM_ID);
			String dFormat 		= XMLUtil.getTagText(formMetaDataNode, DISPLAY_FORMAT);
			String dView 		= XMLUtil.getTagText(formMetaDataNode, DISPLAY_VIEW);
			String rCount 		= XMLUtil.getTagText(formMetaDataNode, RESPONSE_COUNT);
		    String qHeader		= XMLUtil.getTagText(formMetaDataNode, QUESTION_HEADER);
		    String aComments	= XMLUtil.getTagText(formMetaDataNode, ALLOW_COMMENTS);
		    String asHeader		= XMLUtil.getTagText(formMetaDataNode, ALLOW_SCORE_HEADER);
		    String amsHeader	= XMLUtil.getTagText(formMetaDataNode, ALLOW_MAX_SCORE_HEADER);
		    String isOwnerRes	= XMLUtil.getTagText(formMetaDataNode, IS_OWNER_RESPONSE);
		    String isPrivate	= XMLUtil.getTagText(formMetaDataNode, IS_PRIVATE);
		    String excludeNonResponse = XMLUtil.getTagText(formMetaDataNode, EXCLUDE_NON_RESPONSE);
		    String frequency 	= XMLUtil.getTagText(formMetaDataNode, FREQUENCY);//PW_WITHOUT_PLAN
		    
			String name			= null;
			String value		= null;
			String isOwner		= null;
			String order		= null;

			Node[] headerColsNodes	= XMLUtil.getNodesInChildren(formMetaDataNode, HEADER_COLUMN);
			if(headerColsNodes != null) {
				headerDataMap	= new SequenceMap();
			}
			for (int loop=0; headerColsNodes != null && loop < headerColsNodes.length; loop++) {
				name		= XMLUtil.getAttributeValue(headerColsNodes[loop], NAME);
				value		= XMLUtil.getAttributeValue(headerColsNodes[loop], VALUE);
				isOwner		= XMLUtil.getAttributeValue(headerColsNodes[loop], IS_OWNER);
				headerDataMap.put(name, new HeaderColumn(name, value, isOwner));
			}
			Node[] tabsNodes	= XMLUtil.getNodesInChildren(formMetaDataNode, TAB);
			if(tabsNodes != null) {
				tabDataMap	= new SequenceMap();
			}
			for (int loop=0; tabsNodes != null && loop < tabsNodes.length; loop++) {
//				name		= XMLUtil.getAttributeValue(tabsNodes[loop], NAME);
//				value		= XMLUtil.getAttributeValue(tabsNodes[loop], VALUE);
//				order		= XMLUtil.getAttributeValue(tabsNodes[loop], ORDER_BY);
				tabsData = XMLUtil.getNodeAttributesMap(tabsNodes[loop]);
				tabDataMap.put((String)tabsData.get(NAME), new FormTab((String)tabsData.get(NAME), (String)tabsData.get(VALUE), (String)tabsData.get(ORDER_BY), tabsData,(String)tabsData.get(COMMENT_VISIBLE)));
			}
			
			//PW_FORM_VERSION Starts
			SequenceMap verMap 		= 	new SequenceMap();
			Node verNode			= 	XMLUtil.getNodeInChildren(formMetaDataNode, VERSION);
			if(verNode != null) {
				verMap	= XMLUtil.getNodeAttributesMap(verNode);
			}
			//PW_FORM_VERSION Ends
			
			metaDataMap.put(FORM_META_DATA, new FormMetaData(aFormId, dFormat, dView, rCount, qHeader, aComments, asHeader, amsHeader, headerDataMap, tabDataMap, isOwnerRes, isPrivate, excludeNonResponse, frequency, verMap));//PW_WITHOUT_PLAN  //PW_FORM_VERSION
		}
		/**
		 * END
		 */
		Node headerTablesNode	= XMLUtil.getTagNode(root, TABLE_HEADER_MAP);
		if (headerTablesNode != null) {
			Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, HEADER);

			String name			= null;
			String value		= null;
			String order		= null;
			/*P_E_MoveToFim_AddlFDD starts*/
			String optional		= null;
			String recordHeaderPrefix		= null;
			String recordHeaderSufix		= null;
			/*P_E_MoveToFim_AddlFDD ends*/

			for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
				headerAttrFldData = XMLUtil.getNodeAttributesMap(headerNodes[loop]);
				name		= XMLUtil.getAttributeValue(headerNodes[loop], NAME);
				value		= XMLUtil.getAttributeValue(headerNodes[loop], VALUE);
				order		= XMLUtil.getAttributeValue(headerNodes[loop], ORDER);
				/*P_E_MoveToFim_AddlFDD starts*/
				optional				= XMLUtil.getAttributeValue(headerNodes[loop], OPTIONAL);
				recordHeaderPrefix		= XMLUtil.getAttributeValue(headerNodes[loop], RECORD_HEADER_PREFIX);
				recordHeaderSufix		= XMLUtil.getAttributeValue(headerNodes[loop], RECORD_HEADER_SUFIX);
				/*P_E_MoveToFim_AddlFDD ends*/
				
				String fieldType		= XMLUtil.getTagText(headerNodes[loop], TYPE);
				String fieldSection		= XMLUtil.getTagText(headerNodes[loop], SECTION);
				String sectionBuild		= XMLUtil.getTagText(headerNodes[loop], IS_BUILD_SECTION);
				String fieldAsHeader	= XMLUtil.getTagText(headerNodes[loop], FIELD_AS_HEADER);
				String tabName1			= XMLUtil.getTagText(headerNodes[loop], TABS_NAME);
				String isTabularSection	= XMLUtil.getTagText(headerNodes[loop], IS_TABULAR_SECTION);
				String tabularSectionTableAnchor=XMLUtil.getTagText(headerNodes[loop], TABULAR_SECTION_TABLE_ANCHOR);
				String tabularSectionDbTable=XMLUtil.getTagText(headerNodes[loop], TABULAR_SECTION_DB_TABLE);
				Node[] notes			= XMLUtil.getNodesInChildren(headerNodes[loop], NOTES);
				if(notes != null) {
					notesMap = new SequenceMap();
					for (int looptables=0; notes != null && looptables < notes.length; looptables++) {
						notesData = XMLUtil.getNodeAttributesMap(notes[looptables]);
						String heading		= XMLUtil.getTagText(notes[looptables], HEADING);
						String content		= XMLUtil.getTagText(notes[looptables], CONTENT);
						
						notesMap.put((String)notesData.get(NAME), new Notes((String)notesData.get(NAME), notesData, heading, content));
					}
				}
				if(!StringUtil.isValid(sectionBuild)) {
					sectionBuild = "true";
				}
				/**
				 * @AUDIT_ENHANCEMENT_CHANGES
				 * START
				 */
				SequenceMap questionMap = null;
				SequenceMap quesFldData = null;
				SequenceMap quesResFldData = null;
				Node[] questionTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], QUESTION);
				if(questionTableNodes != null) {
					questionMap = new SequenceMap();
				}
				HashMap questionData = null;
				for (int looptables=0; questionTableNodes != null && looptables < questionTableNodes.length; looptables++) {
					questionData = XMLUtil.getNodeAttributes(questionTableNodes[looptables]);
					String tabName	= XMLUtil.getTagText(questionTableNodes[looptables], TABS_NAME);
					
					String helpNotes	= XMLUtil.getTagText(questionTableNodes[looptables], HELP_NOTES);
					
					Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(questionTableNodes[looptables], QUESTION_OPTION);
					SequenceMap quesFldMap = new SequenceMap();
					
					for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
						quesFldData = XMLUtil.getNodeAttributesMap(tablesFldNodes[looptablesfld]);
						quesFldMap.put((String)quesFldData.get(NAME), new QuestionOption((String)quesFldData.get(NAME), quesFldData));
					}
					
					Node[] tablesQesFldNodes	=  XMLUtil.getNodesInChildren(questionTableNodes[looptables], QUES_FIELD);
					SequenceMap quesResFldMap = new SequenceMap();
					for (int looptablesfld=0; tablesQesFldNodes != null && looptablesfld < tablesQesFldNodes.length; looptablesfld++) {
						quesResFldData = XMLUtil.getNodeAttributesMap(tablesQesFldNodes[looptablesfld]);
						quesResFldMap.put((String)quesResFldData.get(NAME), new QuestionField((String)quesResFldData.get(NAME), quesResFldData));
					}
					
					//PW_FORM_VERSION Starts
					SequenceMap verMap 	= 	new SequenceMap();
					Node verNode		=	XMLUtil.getNodeInChildren(questionTableNodes[looptables], VERSION);
					if(verNode != null) {
						verMap	= XMLUtil.getNodeAttributesMap(verNode);
					}
					//PW_FORM_VERSION Ends
					
					 //Starts SmartQuestions
					
					String fieldName = "";
					String quesTableName = "";
					String dbField = "";
					String dbTableName = "";
					String moduleName = "";
					String canEditResponse = "";
					String sourceMethod = "NA";
					String isBuildField = "N";
					String transformMethod = "NA";
					
					Node smartQuestionNode = XMLUtil.getNodeInChildren(questionTableNodes[looptables], SMART_QUESTION); //getting node for smart question
					if(smartQuestionNode != null) {
						fieldName	= XMLUtil.getTagText(smartQuestionNode, FIELD_NAME);
						quesTableName = XMLUtil.getTagText(smartQuestionNode, TABLE_NAME);
						dbField=XMLUtil.getTagText(smartQuestionNode, DB_FIELD);
						dbTableName=XMLUtil.getTagText(smartQuestionNode, MODULE_TABLE_NAME);
						moduleName=XMLUtil.getTagText(smartQuestionNode, MODULE_NAME);
						canEditResponse=XMLUtil.getTagText(smartQuestionNode, CAN_EDIT_RESPONSE);
						sourceMethod = XMLUtil.getTagText(smartQuestionNode, SOURCE_METHOD);
						isBuildField = XMLUtil.getTagText(smartQuestionNode, IS_BUILD_FIELD);
						transformMethod = XMLUtil.getTagText(smartQuestionNode, TRANSFORM_METHOD);
						
					}
					
					questionMap.put((String)questionData.get(NAME), new Question((String)questionData.get(NAME), questionData, quesFldMap, helpNotes, tabName, quesResFldMap, verMap, fieldName, quesTableName,dbField, dbTableName, moduleName, canEditResponse, sourceMethod, isBuildField, transformMethod));//PW_FORM_VERSION
					//ends SmartQuestions
				
				}
				/**
				 * END
				 */
				
				SequenceMap tablesMap = null;
				SequenceMap dependentChildMap = null; //BB-20150203-259 (Dynamic Response based on parent field response)
				SequenceMap syncFieldMap = null; //P_Enh_Sync_Fields
				Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], DEPENDENT_TABLE);
				if(dependentTableNodes != null) {
					tablesMap = new SequenceMap();
					dependentChildMap = new SequenceMap(); //BB-20150203-259 (Dynamic Response based on parent field response)
					syncFieldMap = new SequenceMap(); //P_Enh_Sync_Fields
				}
				
				for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
					tableData = XMLUtil.getNodeAttributes(dependentTableNodes[looptables]);
					
					Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TABLE_FIELD);
					SequenceMap tablesFldMap = new SequenceMap();
					
					for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
						tableFldData = XMLUtil.getNodeAttributesMap(tablesFldNodes[looptablesfld]);
						tablesFldMap.put((String)tableFldData.get(NAME), new TableField((String)tableFldData.get(NAME), tableFldData));
					}
					tablesMap.put((String)tableData.get(NAME), new DependentTable((String)tableData.get(NAME), tablesFldMap,(String)tableData.get(TABLE_WITH_ALIAS),(String)tableData.get("tableAnchor")));
				}
				
				//BB-20150203-259 (Dynamic Response based on parent field response) starts
				for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
					Node dependentNode = XMLUtil.getNodeInChildren(dependentTableNodes[looptables], DEPENDENT); //gets the dependent node for dependent-child
					if(dependentNode != null) {
						Node[] dependentChildNode	=  XMLUtil.getNodesInChildren(dependentNode, DEPENDENT_CHILD);
						for (int looptablesfld=0; dependentChildNode != null && looptablesfld < dependentChildNode.length; looptablesfld++) {
							SequenceMap childMap = XMLUtil.getNodeAttributesMap(dependentChildNode[looptablesfld]);
							dependentChildMap.put((String)childMap.get(NAME), new DependentChild((String)childMap.get(NAME), childMap, (String)childMap.get(FUNCTION), (String)childMap.get(PARENT_FIELD)));					
						}
					}
				}
				//BB-20150203-259 (Dynamic Response based on parent field response) ends
				
				//P_Enh_Sync_Fields starts
				for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
					Node syncNode = XMLUtil.getNodeInChildren(dependentTableNodes[looptables], SYNC); //gets the dependent node for dependent-child
					if(syncNode != null) {
						Node[] syncChildNode	=  XMLUtil.getNodesInChildren(syncNode, SYNC_DATA);
						for (int looptablesfld=0; syncChildNode != null && looptablesfld < syncChildNode.length; looptablesfld++) {
							SequenceMap childMap = XMLUtil.getNodeAttributesMap(syncChildNode[looptablesfld]);
							syncFieldMap.put((String)childMap.get("parent-field-name"), new SyncWithField((String)childMap.get(NAME), (String)childMap.get(TABLE_NAME), (String)childMap.get("column-name"), (String)childMap.get("table-anchor"), (String)childMap.get("parent-field-name"), (String)childMap.get(DISPLAY_NAME), (String)childMap.get(SYNC_MODULE)));
						}
					}
				}
				//P_Enh_Sync_Fields ends
				
				Node[] documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], DOCUMENTS);
				if(documentsNode != null) {
					docMap = new SequenceMap();
				}
				for (int loopdocs=0; documentsNode != null && loopdocs < documentsNode.length; loopdocs++) {
					docData = XMLUtil.getNodeAttributes(documentsNode[loopdocs]);
					
					Node[] docFldNodes	=  XMLUtil.getNodesInChildren(documentsNode[loopdocs], DOCUMENT);
					SequenceMap docFldMap = new SequenceMap();
					
					for (int loopdocsfld=0; docFldNodes != null && loopdocsfld < docFldNodes.length; loopdocsfld++) {
						docFldData = XMLUtil.getNodeAttributesMap(docFldNodes[loopdocsfld]);
						docFldMap.put((String)docFldData.get(NAME), new DocumentMap((String)docFldData.get(NAME), docFldData));
					}
					docMap.put((String)docData.get(NAME), new Documents((String)docData.get(NAME), docFldMap,(String)docData.get("tableAnchor"), (String)docData.get(SECTION), (String)docData.get("tabName")));
				}
				
				Node[] ipMapNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], ID_MAP);
				if(ipMapNode != null) {
					idMap = new SequenceMap();
					for (int loopidmap=0; ipMapNode != null && loopidmap < ipMapNode.length; loopidmap++) {
						idAttrMap = XMLUtil.getNodeAttributesMap(ipMapNode[loopidmap]);
						idMap.put((String)idAttrMap.get(NAME), idAttrMap);
					}
				}
				Node[] tableMapNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TABLE_MAP);
				if(tableMapNode != null) {
					tableMap = new SequenceMap();
					for (int looptablemap=0; tableMapNode != null && looptablemap < tableMapNode.length; looptablemap++) {
						tableAttrMap = XMLUtil.getNodeAttributesMap(tableMapNode[looptablemap]);
						tableMap.put((String)idAttrMap.get(NAME), tableAttrMap);
					}
				}
				Node[] otherTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], OTHER_TABLE);
				if(otherTableNodes != null) {
					otherTablesMap = new SequenceMap();
				}
				for (int loopothertables=0; otherTableNodes != null && loopothertables < otherTableNodes.length; loopothertables++) {
					otherTableAttrMap = XMLUtil.getNodeAttributesMap(otherTableNodes[loopothertables]);
					
					Node[] mainFldNodes	=  XMLUtil.getNodesInChildren(otherTableNodes[loopothertables], MAIN_FIELD);
					Node[] childFldNodes	=  XMLUtil.getNodesInChildren(otherTableNodes[loopothertables], CHILD_FIELD);
					SequenceMap tablesMainFldMap = new SequenceMap();
					SequenceMap tablesChildFldMap = new SequenceMap();
					for (int looptablesfld=0; mainFldNodes != null && looptablesfld < mainFldNodes.length; looptablesfld++) {
						mainFldData = XMLUtil.getNodeAttributesMap(mainFldNodes[looptablesfld]);
						tablesMainFldMap.put((String)mainFldData.get(NAME), new MainField((String)mainFldData.get(NAME), mainFldData));
					}
					for (int looptablesfld=0; childFldNodes != null && looptablesfld < childFldNodes.length; looptablesfld++) {
						childFldData = XMLUtil.getNodeAttributesMap(childFldNodes[looptablesfld]);
						tablesChildFldMap.put((String)childFldData.get(NAME), new ChildField((String)childFldData.get(NAME), childFldData));
					}
					otherTablesMap.put((String)otherTableAttrMap.get(NAME), new OtherTable((String)otherTableAttrMap.get(NAME), otherTableAttrMap, tablesMainFldMap, tablesChildFldMap));
				}
				
				//PW_FORM_VERSION Starts
				SequenceMap verMap 	= 	new SequenceMap();
				Node verNode		=	XMLUtil.getNodeInChildren(headerNodes[loop], VERSION);
				if(verNode != null) {
					verMap	= XMLUtil.getNodeAttributesMap(verNode);
				}
				//PW_FORM_VERSION Ends
				
				/*P_E_MoveToFim_AddlFDD starts*/
				
				headerMap.put(name, new HeaderMap(name, value, order, optional, recordHeaderPrefix, recordHeaderSufix,  new HeaderField(fieldType, fieldSection, sectionBuild, fieldAsHeader, tablesMap, docMap, notesMap, idMap, tableMap, otherTablesMap, headerAttrFldData, questionMap, tabName1, verMap, dependentChildMap, syncFieldMap,isTabularSection,tabularSectionTableAnchor,tabularSectionDbTable), tabName1)); //BB-20150203-259 (Dynamic Response based on parent field response) //P_Enh_Sync_Fields
				headerMap=sortHeaderMap(headerMap);//BB-20150203-259 (Tab Re-ordering changes)
				/*P_E_MoveToFim_AddlFDD ends*/
			}
			
			/**
			 * @AUDIT_ENHANCEMENT_CHANGES
			 * START
			 */
			Node[] questionNodes	= XMLUtil.getNodesInChildren(headerTablesNode, QUESTION);
			SequenceMap quesExtFldData = null;
			SequenceMap quesResFldData = null;
			if(questionNodes != null) {
				questionExtMap = new SequenceMap();
			}
			HashMap questionExtData = null;
			for (int looptables=0; questionNodes != null && looptables < questionNodes.length; looptables++) {
				questionExtData = XMLUtil.getNodeAttributes(questionNodes[looptables]);
				String tabName	= XMLUtil.getTagText(questionNodes[looptables], TABS_NAME);
				
				String helpExtNotes	= XMLUtil.getTagText(questionNodes[looptables], HELP_NOTES);
				
				Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(questionNodes[looptables], "question-option");
				SequenceMap quesExtFldMap = new SequenceMap();
				
				for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
					quesExtFldData = XMLUtil.getNodeAttributesMap(tablesFldNodes[looptablesfld]);
					quesExtFldMap.put((String)quesExtFldData.get(NAME), new QuestionOption((String)quesExtFldData.get(NAME), quesExtFldData));
				}
				
				Node[] tablesQesFldNodes	=  XMLUtil.getNodesInChildren(questionNodes[looptables], QUES_FIELD);
				SequenceMap quesResFldMap = new SequenceMap();
				for (int looptablesfld=0; tablesQesFldNodes != null && looptablesfld < tablesQesFldNodes.length; looptablesfld++) {
					quesResFldData = XMLUtil.getNodeAttributesMap(tablesQesFldNodes[looptablesfld]);
					quesResFldMap.put((String)quesResFldData.get(NAME), new QuestionField((String)quesResFldData.get(NAME), quesResFldData));
				}
				
				//PW_FORM_VERSION Starts
				SequenceMap verMap 	= 	new SequenceMap();
				Node verNode		=	XMLUtil.getNodeInChildren(questionNodes[looptables], VERSION);
				if(verNode != null) {
					verMap	= XMLUtil.getNodeAttributesMap(verNode);
				}
				//PW_FORM_VERSION Ends

			 //Starts SmartQuestions
				
				String fieldName = "";
				String quesTableName = "";
				String dbField = "";
				String dbTableName = "";
				String moduleName = "";
				String canEditResponse = "";
				String sourceMethod = "NA";
				String isBuildField = "N";
				String transformMethod = "NA";
				
				Node smartQuestionNode = XMLUtil.getNodeInChildren(questionNodes[looptables], SMART_QUESTION); //getting node for smart question
				if(smartQuestionNode != null) {
					fieldName	= XMLUtil.getTagText(smartQuestionNode, FIELD_NAME);
					quesTableName = XMLUtil.getTagText(smartQuestionNode, TABLE_NAME);
					dbField=XMLUtil.getTagText(smartQuestionNode, DB_FIELD);
					dbTableName=XMLUtil.getTagText(smartQuestionNode, MODULE_TABLE_NAME);
					moduleName=XMLUtil.getTagText(smartQuestionNode, MODULE_NAME);
					canEditResponse=XMLUtil.getTagText(smartQuestionNode, CAN_EDIT_RESPONSE);
					sourceMethod = XMLUtil.getTagText(smartQuestionNode, SOURCE_METHOD);
					isBuildField = XMLUtil.getTagText(smartQuestionNode, IS_BUILD_FIELD);
					transformMethod = XMLUtil.getTagText(smartQuestionNode, TRANSFORM_METHOD);
				}
				
				questionExtMap.put((String)questionExtData.get(NAME), new Question((String)questionExtData.get(NAME), questionExtData, quesExtFldMap, helpExtNotes, tabName, quesResFldMap, verMap, fieldName, quesTableName,dbField, dbTableName, moduleName, canEditResponse, sourceMethod,isBuildField, transformMethod));//PW_FORM_VERSION
				//ends SmartQuestions
			}
			/**
			 * END
			 */
		}
		
		NodeList idList			= root.getElementsByTagName(ID_FIELD);
        String[] idField 		= new String[idList.getLength()];
		for (int loop = 0; loop < idList.getLength(); loop++)
		{
			Node node			= idList.item(loop);
			idField[loop]		= node.getFirstChild().getNodeValue();
		}

		Node foreignTablesNode	= XMLUtil.getTagNode(root, FOREIGN_TABLES);
		Node recordTriggerNode	= XMLUtil.getTagNode(root, RECORD_TRIGGER);
		Trigger recordTrigger	= null;

		if (recordTriggerNode != null)
		{
			recordTrigger		= new Trigger(XMLUtil.getAttributeValue(recordTriggerNode, INSERTION),
											XMLUtil.getAttributeValue(recordTriggerNode, MODIFICATION),
											XMLUtil.getAttributeValue(recordTriggerNode, DELETION ),
											XMLUtil.getAttributeValue(recordTriggerNode, FIELD_WISE));
		}

		SequenceMap foreignTablesMap = new SequenceMap();
		/**
		 * 
		 */
		SequenceMap foreignTablesExportMap = new SequenceMap();
		
		if (foreignTablesNode != null)
		{
			Node[] foreignTables	= XMLUtil.getNodesInChildren(foreignTablesNode, FOREIGN_TABLE );

			for (int loop=0; foreignTables != null && loop < foreignTables.length; loop++)
			{
				String name			= XMLUtil.getAttributeValue(foreignTables[loop], NAME );
				String tableExport	= XMLUtil.getAttributeValue(foreignTables[loop], TABLE_EXPORT);
				String tableImport	= XMLUtil.getAttributeValue(foreignTables[loop], TABLE_IMPORT);
				//dependentModule="FIN" required="false"
				String dependentModule	= XMLUtil.getAttributeValue(foreignTables[loop], DEPENDENT_MODULE);
				String required	= XMLUtil.getAttributeValue(foreignTables[loop], REQUIRED);
				if(StringUtil.isValid(dependentModule) && !ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValid(required) && "true".equals(required)) {
					// EKIP THIS DATA
					continue;
				} 
				Node[] linkFields	= XMLUtil.getNodesInChildren( foreignTables[loop], LINK_FIELD);
				SequenceMap linkFieldMap = new SequenceMap();

				for (int i=0; linkFields != null && i<linkFields.length; i++)
				{
					String sThisField	= XMLUtil.getAttributeValue(linkFields[i], THIS_FIELD);
					String sForeignField = XMLUtil.getAttributeValue(linkFields[i], FOREIGN_FIELD );
					String sForeignFieldValue = XMLUtil.getAttributeValue( linkFields[i], FOREIGN_FIELD_VALUE );
					String skipThisField = XMLUtil.getAttributeValue( linkFields[i], SKIP_THIS_FIELD );
					
					linkFieldMap.put(sForeignField, new LinkField( sThisField, sForeignField, sForeignFieldValue,skipThisField));
				}
				/**
				 * Concept : Merging Import/Manipulate XML utilities with Export XML utilities 
				 * This will set two map of Foreign tables, one for Import/Manipulation and another for Export data.
				 * There are some tab's data which not required linked foreign table when Export data i.e. Foreign Tables like Address, Document etc.    
				 */
				if(StringUtil.isValid(tableExport) && "false".equals(tableExport)) {
					if(StringUtil.isValid(tableImport) && "false".equals(tableImport)) {
//						foreignTablesMap.put(name, new ForeignTable( name, linkFieldMap, tableExport));
					} else {
						foreignTablesMap.put(name, new ForeignTable( name, linkFieldMap, tableExport));
					}
				} else {
					if(StringUtil.isValid(tableImport) && "false".equals(tableImport)) {
//						foreignTablesMap.put(name, new ForeignTable( name, linkFieldMap ));
					} else {
						foreignTablesMap.put(name, new ForeignTable( name, linkFieldMap ));
					}
					foreignTablesExportMap.put(name, new ForeignTable( name, linkFieldMap));
				}
			}
			
			/**
			 * Changes done predominantly to hide export/search of QA history when QA tab integration is on. 
			 * //AUDIT_ENHANCEMENT_CHANGES
			 */
			
	
			
			/**
			 * Placing Custom Tables Mapping
			 */
			// call function which return HashMap 
			/**
			 * 
			 */
			
			//P_SCH_ENH_008 Starts
			// Jobs is the foreign table of cm. So prevent processing of foreign tables(custom Tabs) of foreign table(Jobs) 
			if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(tableModule))
				tableModule="";
			//P_SCH_ENH_008 Ends
			//ENH_MODULE_CUSTOM_TABS starts
			if(StringUtil.isValid(tableModule))
			{
				BuilderCustomTab.newInstance().processForeignMapsWithCustomTab(tableModule,foreignTablesMap,foreignTablesExportMap);
			}
			//ENH_MODULE_CUSTOM_TABS ends
		}

		fieldMappings = new FieldMappings(connectionName, tableName, 
												idField,
        										foreignTablesMap,
        										tableDisplayName,
        										tableInternalName,
        										recordTrigger, headerMap, seqField, tableExportDisplayName, foreignTablesExportMap, questionExtMap, metaDataMap);
		fieldMappings.setEntityTableDisplayName(entityTableDisplayName);
				
        NodeList list = root.getElementsByTagName(FIELD);

        for (int loop = 0; loop < list.getLength(); loop++)
        {
            Node node 				= list.item(loop);
            HashMap fieldData 		= null;
            String summaryString 	= XMLUtil.getAttributeValue(node,SUMMARY);
            String width			= XMLUtil.getAttributeValue(node,WIDTH);
            String dependentModule	= XMLUtil.getAttributeValue(node, DEPENDENT_MODULE);
			String required	= XMLUtil.getAttributeValue(node, REQUIRED);
			if(StringUtil.isValid(summaryString)){
				fieldData	=	XMLUtil.getNodeAttributes(node);
			}
			if(StringUtil.isValid(dependentModule) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValid(required) && "false".equals(required)) {
				// EKIP THIS DATA
				continue;
			} 
			// Added by Anuj 2005-07-23 for Export Layout
            String colspan			= XMLUtil.getAttributeValue(node,COLSPAN) != null ? XMLUtil.getAttributeValue(node,COLSPAN) : "1";
            String colspanexport	= XMLUtil.getAttributeValue(node,COLSPAN_EXPORT) != null ? XMLUtil.getAttributeValue(node,COLSPAN_EXPORT) : null;
            String colspanMerge	= XMLUtil.getAttributeValue(node,COLSPAN_MERGE) != null ? XMLUtil.getAttributeValue(node,COLSPAN_MERGE) : null;
			boolean summary 		= ("true".equalsIgnoreCase(summaryString));
			String isMultiSelect    = XMLUtil.getTagText(node,IS_MULTISELECT);//BB-20150203-259 (MultiSelect combo in add new field changes)
			String fieldName		= XMLUtil.getTagText(node,FIELD_NAME);
			String dbField			= XMLUtil.getTagText(node,DB_FIELD);
			String dataType			= XMLUtil.getTagText(node,DATA_TYPE);
			String sourceField		= XMLUtil.getTagText(node,SOURCE_FIELD);
			String colNoWrap 			= XMLUtil.getTagText(node, NO_WRAP);
			String displayName		= XMLUtil.getTagText(node,DISPLAY_NAME);
			String displayExportName= XMLUtil.getTagText(node,DISPLAY_EXPORT_NAME);
			//CUSTOM_REPORT_SORTING_ISSUE starts
			String castInOrder		= XMLUtil.getTagText(node,CAST_IN_ORDER);
			String customSortable		= XMLUtil.getTagText(node,CUSTOM_SORTABLE);
			//CUSTOM_REPORT_SORTING_ISSUE ends
			String srcTable			= XMLUtil.getTagText(node,SRC_TABLE);
			String srcField			= XMLUtil.getTagText(node,SRC_FIELD);
			String srcValue			= XMLUtil.getTagText(node,SRC_VALUE);
			String transformMethod	= XMLUtil.getTagText(node,TRANSFORM_METHOD);
			String transformMethodParam	= XMLUtil.getTagText(node,TRANSFORM_METHOD_PARAM);
			String transformMethodExportParam	= XMLUtil.getTagText(node,TRANSFORM_METHOD_EXPORT_PARAM);
			String transformExportMethod	= XMLUtil.getTagText(node,TRANSFORM_EXPORT_METHOD);
			String transformExportMethodParam	= XMLUtil.getTagText(node,TRANSFORM_EXPORT_METHOD_PARAM);
			String transformRequiredInExport	= XMLUtil.getTagText(node,TRANSFORM_METHOD_IN_EXPORT);
			String jsValidationMethod = XMLUtil.getTagText(node,JS_VALIDATION_METHOD); 
			
			
			String fieldExport		= XMLUtil.getTagText(node,FIELD_EXPORT);
			String isCurrency		= XMLUtil.getTagText(node, CURRENCY_FIELD);
			String groupBy		= XMLUtil.getTagText(node, GROUP_BY);
			String isActive		= XMLUtil.getTagText(node, IS_ACTIVE);
            String isDependentTableField		= XMLUtil.getTagText(node, IS_DEPENDENT_TABLE_FIELD);
            String dependentTableName		= XMLUtil.getTagText(node, DEPENDENT_TABLE_NAME);
			String isBuildField		= XMLUtil.getTagText(node, IS_BUILD_FIELD);
			String buildFieldSeq	= XMLUtil.getTagText(node, BUILD_FIELD_SEQ);
			String orderBy 			= XMLUtil.getTagText(node, ORDER_BY);
			String isMandatory 		= XMLUtil.getTagText(node, IS_MANDATORY);
			String fieldMethod		= XMLUtil.getTagText(node,FIELD_METHOD);
			String fieldMethodParam	= XMLUtil.getTagText(node,FIELD_METHOD_PARAM);
			String section	 		= XMLUtil.getTagText(node,SECTION);
			String displayType 		= XMLUtil.getTagText(node,DISPLAY_TYPE);
			String validInput 		= XMLUtil.getTagText(node,VALID_INPUT);
			String noOfCols 		= XMLUtil.getTagText(node,NO_OF_COLUMN);
			String noOfRows 		= XMLUtil.getTagText(node,NO_OF_ROW);
			String dbFldLength		= XMLUtil.getTagText(node,DB_FIELD_LENGTH);
			String piiEnabled		= XMLUtil.getTagText(node,PII_ENABLED);
			String defaultPiiEnabled		= XMLUtil.getTagText(node,DEFAULT_PII_ENABLED);
			String countryOpt   	= XMLUtil.getTagText(node,DROPDOWN_OPTION);
            String internationalization		= XMLUtil.getTagText(node,INTERNATIONALIZATION);
			//BB-20150203-259 (Dynamic Response based on parent field response) starts
			String radioOpt			= XMLUtil.getTagText(node, RADIO_OPTION);
			String checkboxOpt		= XMLUtil.getTagText(node, CHECKBOX_OPTION);
			String dependentParent  = XMLUtil.getTagText(node, DEPENDENT_PARENT);
			String syncWith			= XMLUtil.getTagText(node, SYNC_WITH); //P_Enh_Sync_Fields
			String readOnly			= XMLUtil.getTagText(node, READ_ONLY); //P_Enh_Sync_Fields
			String isParsingKeyordConfigured = XMLUtil.getTagText(node, IS_PARSING_KEYWORD_CONFIGURED);
			String syncModule = "";
			Node syncWithNode = XMLUtil.getNodeInChildren(node, SYNC_WITH); //gets the Sync Node
			if(syncWithNode != null) {
				syncModule = XMLUtil.getAttributeValue(syncWithNode, SYNC_MODULE);
			}
			
			String centerInfoDisplay		= XMLUtil.getTagText(node,CENTER_INFO_DISPLAY);
			if(countryOpt == null) {
				countryOpt = "";
			}
			if(radioOpt == null) {
				radioOpt = "";
			}
			if(checkboxOpt == null) {
				checkboxOpt = "";
			}
			
			if(syncWith == null) { //P_Enh_Sync_Fields starts
				syncWith = "";
			} //P_Enh_Sync_Fields ends
			//BB-20150203-259 (Dynamic Response based on parent field response) ends
			
			String allowInMain   	= XMLUtil.getTagText(node,ALLOW_FIELD_IN_MAIN);
			if(allowInMain == null) {
				allowInMain = "true";
			}
			String combinedDisplayName   	= XMLUtil.getTagText(node,COMBINED_DISPLAY_NAME);
			String fieldCalledMethod		= XMLUtil.getTagText(node,	FIELD_CALLED_METHOD);
			String conditionField	 		= XMLUtil.getTagText(node,	CONDITION_FIELD);
			String conditionFieldValue 		= XMLUtil.getTagText(node,	CONDITION_FIELD_VALUE);
			
			Node mMergeNode			= XMLUtil.getNodeInChildren(node, MAILMERGE);
			String mKeyword 	= "";
			String mActive 	= "false";
			String mailMergeTransformMethod 	= "";
			if(mMergeNode != null ) {
				mKeyword	= XMLUtil.getAttributeValue(mMergeNode, KEYWORD_NAME);
				mActive = XMLUtil.getAttributeValue(mMergeNode, IS_ACTIVE);
				mailMergeTransformMethod = XMLUtil.getAttributeValue(mMergeNode, MAILMERGE_TRANSFORM_METHOD);
			}
			
			
			Node mJSNode			= XMLUtil.getNodeInChildren(node, J_SCRIPT_FUNCT);
			String mjsKeyword 	= "";
			Object jsData = null;
			if(mJSNode != null ) {
				jsData = XMLUtil.getNodeAttributesInfo(mJSNode);
			}
			
			if(!StringUtil.isValidNew(isMandatory)) {
				isMandatory = Constants.FALSE;
			}
			if(!StringUtil.isValidNew(orderBy)) {
				orderBy = Constants.DEFAULT_INT_STR;
			}
			if(!StringUtil.isValidNew(validInput)) {
				validInput = "alfanumeric";
			}
			if(!StringUtil.isValidNew(noOfCols)) {
				noOfCols = "60";
			}
			if(!StringUtil.isValidNew(noOfRows)) {
				noOfRows = "5";
			}
			
			
			boolean bFieldExport	= false;
			boolean sortable		= false;
			String sortColumn		= dbField;

			if (XMLUtil.getTagText(node, "sortable") != null && XMLUtil.getTagText(node, "sortable").equals("true"))
			{
				sortable = true;

				if (XMLUtil.getTagText(node, "sort-column") != null && !XMLUtil.getTagText(node, "sort-column").equals(""))
				{
					sortColumn = XMLUtil.getTagText(node, "sort-column");
				}
			}
	
			if (fieldExport != null) 
			{
				bFieldExport		= StringUtil.boolValue(fieldExport);
			}

			// code for fileField
			String fileField		= XMLUtil.getTagText(node,FILE_FIELD);
			boolean bFileField		= false;

			if (fileField != null) 
			{
				bFileField			= StringUtil.boolValue(fileField);
			}

		
			// code for Custom Field export added by Sunilk
			String customField		= XMLUtil.getTagText(node,CUSTOM_FIELD);
			boolean bCustomField		= false;

			if (customField != null) 
			{
				bCustomField			= StringUtil.boolValue(customField);
			}

			Node searchNode			= XMLUtil.getNodeInChildren(
														node,
														SEARCH
																);
			String searchField		= null;
			String searchSourceValuesMethod
									= null;
			String searchSourceMethodArgs   		= null;

			String searchSourceValuesURL
									= null;
			if(searchNode != null){

				searchField			= XMLUtil.getTagText(searchNode, SEARCH_FIELD);

				searchSourceValuesMethod
									= XMLUtil.getTagText(
														searchNode,
														SEARCH_SOURCE_VALUES_METHOD
														);
				searchSourceMethodArgs
									= XMLUtil.getTagText(
														searchNode,
														SEARCH_SOURCE_METHOD_ARGS
														);		//Added by vivek
				searchSourceValuesURL
									= XMLUtil.getTagText(
														searchNode,
														SEARCH_SOURCE_VALUES_URL
														);
			}
			Node fieldTriggerNode	= XMLUtil.getNodeInChildren(
														node,
														FIELD_TRIGGER
																);
			Trigger fieldTrigger	= null;
			if(fieldTriggerNode != null){
				fieldTrigger		= new Trigger(
										XMLUtil.getAttributeValue(
																recordTriggerNode,
																INSERTION
																),
										XMLUtil.getAttributeValue(
																recordTriggerNode,
																MODIFICATION
																),
										XMLUtil.getAttributeValue(
																recordTriggerNode,
																DELETION
																),
										null
												);
			}
				String validationType	= null;
				String includeInCampaign = "1"; //BB-20150319-268(FIM Campaign Center)
			Node validationNode		= XMLUtil.getNodeInChildren(
															node,
															VALIDATION
																);
			if(validationNode != null ) {
				validationType		= XMLUtil.getTagText(
														validationNode,
														VALIDATION_TYPE
														);
				includeInCampaign = XMLUtil.getTagText(validationNode,INCLUDE_IN_CAMPAIGN); //BB-20150319-268(FIM Campaign Center)
			}
			
			
			//deepak starts
			
			Node   comboNode					= XMLUtil.getNodeInChildren(node,COMBO);
			String comboSourceValuesMethod		= null;
			String comboSourceMethodArgs   		= null;
			String comboMethodParam   		    = null;
			String parent   		    		= null;
			String dependentField   		    = null;
			String comboSourceMethodOtherField	    = null;
			String comboSourceMethodOtherFieldType	= null;
			String comboSourceMethodOtherFieldSpan= null;
			
			if(comboNode != null){

				
				comboSourceValuesMethod			= XMLUtil.getTagText(
														comboNode,
														COMBO_SOURCE_VALUES_METHOD
														);
				
				comboSourceMethodArgs			= XMLUtil.getTagText(
														comboNode,
														COMBO_SOURCE_METHOD_ARGS
														);		//Added by vivek
				comboMethodParam				= XMLUtil.getTagText(
														comboNode,
														COMBO_METHOD_PARAM
														);
				
				parent							= XMLUtil.getTagText(
														comboNode,
														PARENT
														);
				dependentField					= XMLUtil.getTagText(
														comboNode,
														DEPENDENT_FIELD
														);
				comboSourceMethodOtherField 	= XMLUtil.getTagText(comboNode,	COMBO_METHOD_OTHER_FIELD);
				comboSourceMethodOtherFieldType = XMLUtil.getTagText(comboNode,	COMBO_METHOD_OTHER_FIELD_ARGS);
				comboSourceMethodOtherFieldSpan = XMLUtil.getTagText(comboNode, COMBO_METHOD_OTHER_FIELD_SPAN);
			}
			
			//deepak ends
			
			Node   radioNode					= XMLUtil.getNodeInChildren(node,RADIO);
			String radioSourceValuesMethod		= null;
			String radioSourceMethodArgs   		= null;
			String radioMethodParam   		    = null;
			String radioSourceMethodOtherField	    = null;
			String radioSourceMethodOtherFieldType	= null;
			String radioSourceOtherFieldMethod= null;
			
			Node   checkboxNode					= XMLUtil.getNodeInChildren(node,CHECKBOX);
			String checkboxSourceValuesMethod		= null;
			String checkboxSourceMethodArgs   		= null;
			String checkboxMethodParam   		    = null;
			String checkboxSourceMethodOtherField	    = null;
			String checkboxSourceMethodOtherFieldType	= null;
			String checkboxMethodReturnType	= null;
			String checkboxConditionField	= null;
			String checkboxConditionFieldValue	= null;
			String checkboxSourceValuesColCount	= null;
			
			if(radioNode != null){
				radioSourceValuesMethod				= XMLUtil.getTagText(radioNode, RADIO_SOURCE_VALUES_METHOD);
				radioSourceMethodArgs				= XMLUtil.getTagText(radioNode, RADIO_SOURCE_METHOD_ARGS);
				radioMethodParam					= XMLUtil.getTagText(radioNode,	RADIO_METHOD_PARAM);
				radioSourceMethodOtherField 		= XMLUtil.getTagText(radioNode,	RADIO_METHOD_OTHER_FIELD);
				radioSourceMethodOtherFieldType 	= XMLUtil.getTagText(radioNode,	RADIO_METHOD_OTHER_FIELD_ARGS);
				radioSourceOtherFieldMethod 		= XMLUtil.getTagText(radioNode,	RADIO_SOURCE_OTHER_FIELD_METHOD);
			}			
			if(checkboxNode != null){
				checkboxSourceValuesMethod			= XMLUtil.getTagText(checkboxNode,  CHECKBOX_SOURCE_VALUES_METHOD);
				checkboxSourceMethodArgs			= XMLUtil.getTagText(checkboxNode,  CHECKBOX_SOURCE_METHOD_ARGS);
				checkboxMethodParam					= XMLUtil.getTagText(checkboxNode,	CHECKBOX_METHOD_PARAM);
				checkboxSourceMethodOtherField 		= XMLUtil.getTagText(checkboxNode,	CHECKBOX_METHOD_OTHER_FIELD);
				checkboxSourceMethodOtherFieldType 	= XMLUtil.getTagText(checkboxNode,	CHECKBOX_METHOD_OTHER_FIELD_ARGS);
				
				checkboxMethodReturnType		 	= XMLUtil.getTagText(checkboxNode,	CHECKBOX_METHOD_RETURN_TYPE);
				checkboxConditionField 				= XMLUtil.getTagText(checkboxNode,	CHECKBOX_CONDITION_FIELD);
				checkboxConditionFieldValue 		= XMLUtil.getTagText(checkboxNode,	CHECKBOX_CONDITION_FIELD_VALUE);
				checkboxSourceValuesColCount 		= XMLUtil.getTagText(checkboxNode,	CHECKBOX_SOURCE_VALUES_COL_COUNT);
			}			
			
			//BB-20150203-259 (Dynamic Response based on parent field response) starts
			Node dependentNode = XMLUtil.getNodeInChildren(node, DEPENDENT); //gets the dependent node for dependent-child
			SequenceMap dependentChildTotalMap = new SequenceMap();
			if(dependentNode != null) {
				Node[] dependentChildNode	=  XMLUtil.getNodesInChildren(dependentNode, DEPENDENT_CHILD);
				for (int looptablesfld=0; dependentChildNode != null && looptablesfld < dependentChildNode.length; looptablesfld++) {
					SequenceMap dependentChildMap = XMLUtil.getNodeAttributesMap(dependentChildNode[looptablesfld]);
					dependentChildTotalMap.put((String)dependentChildMap.get(NAME), new DependentChild((String)dependentChildMap.get(NAME), dependentChildMap, (String)dependentChildMap.get(FUNCTION)));					
				}
			}
			//BB-20150203-259 (Dynamic Response based on parent field response) ends
			
			//P_Enh_Sync_Fields starts
			Node syncNode = XMLUtil.getNodeInChildren(node, SYNC); //gets the Sync Node
			SequenceMap syncTotalMap = new SequenceMap();
			if(syncNode != null) {
				Node[] syncDataNode	=  XMLUtil.getNodesInChildren(syncNode, SYNC_DATA);
				for (int looptablesfld=0; syncDataNode != null && looptablesfld < syncDataNode.length; looptablesfld++) {
					SequenceMap syncMap = XMLUtil.getNodeAttributesMap(syncDataNode[looptablesfld]);
					syncTotalMap.put((String)syncMap.get(NAME), new SyncWithField((String)syncMap.get(NAME), (String)syncMap.get(TABLE_NAME), (String)syncMap.get("column-name"), (String)syncMap.get("table-anchor"), (String)syncMap.get("parent-field-name"), (String)syncMap.get(DISPLAY_NAME), (String)syncMap.get(SYNC_MODULE)));					
				}
			}
			//P_Enh_Sync_Fields ends
			
			/**
			 * @AUDIT_ENHANCEMENT_CHANGES
			 */
			String questionName		= XMLUtil.getTagText(node, QUESTION_NAME);
			
			/**
			 * OPTION_VIEW_H_V
			 */
			String fldViewType		= XMLUtil.getTagText(node, FIELD_OPTION_VIEW);
			
			fieldMappings.addField	(fieldName,
									 dbField, displayName, dataType,
									 validationType, width,
									 colspan, 	   
									 summary ? Field.FieldType.ALL_AND_SUMMARY : Field.FieldType.ALL_ONLY,
									 srcTable, srcField, srcValue,
									 transformMethod, transformMethodParam,
									 searchField, searchSourceValuesMethod,
									 searchSourceMethodArgs, searchSourceValuesURL,
									 fieldTrigger, bFieldExport,
									 bFileField,   
									 bCustomField, 
									 sortable, sortColumn, isCurrency,
									 groupBy, isActive, isBuildField, buildFieldSeq, Integer.parseInt(orderBy), Boolean.parseBoolean(isMandatory), 
									 fieldMethod, fieldMethodParam, section,displayType,validInput,noOfCols,noOfRows,dbFldLength,mKeyword,mActive,comboSourceValuesMethod,
									 comboSourceMethodArgs,comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled,jsData,
									 countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType,
									 checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType,
									 colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
									 transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, 
									 checkboxConditionField, checkboxConditionFieldValue,fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan,conditionField,
									 conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge,colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder, customSortable,jsValidationMethod,centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, fieldData,isParsingKeyordConfigured); //BB-20150203-259 (Dynamic Response based on parent field response)
																																																																				//BB-20150319-268(FIM Campaign Center)												
        }
        
    } catch(Exception e) {
    	e.printStackTrace();
    }
        return fieldMappings;
    }
    
    public static FieldMappings getBuilderValidationFieldMappings(String location, String childTableAnchor , FieldMappings fieldMappings,int appendor)
    {
        Element root			= XMLUtil.loadDocument(location);
        String connectionName 	= MultiTenancyUtil.getTenantName();
        String tableName 		= XMLUtil.getTagText(root,TABLE_NAME);
		String tableDisplayName = XMLUtil.getTagText(root,TABLE_DISPLAY_NAME);
		//P_B_FIM_75246 added by neeti starts
		String tableInternalName = XMLUtil.getTagText(root,TABLE_INTERNAL_NAME);
		//P_B_FIM_75246 added by neeti ends
		NodeList idList			= root.getElementsByTagName(ID_FIELD);
        String[] idField 		= new String[idList.getLength()];
		for (int loop = 0; loop < idList.getLength(); loop++)
		{
			Node node			= idList.item(loop);
			idField[loop]		= node.getFirstChild().getNodeValue();
		}
		NodeList list = root.getElementsByTagName(FIELD);
        for (int loop = 0; loop < list.getLength(); loop++)
        {
            Node node 				= list.item(loop);
            String summaryString 	= XMLUtil.getAttributeValue(node,SUMMARY);
            String width			= XMLUtil.getAttributeValue(node,WIDTH);

            //Added by Anuj 2005-07-23 for Export Layout
			String colspan			= XMLUtil.getAttributeValue(node,COLSPAN) != null ? XMLUtil.getAttributeValue(node,COLSPAN) : "1";

			boolean summary 		= (MultiTenancyUtil.getTenantConstants().TRUE.equalsIgnoreCase(summaryString));
			String fieldName		= XMLUtil.getTagText(node,FIELD_NAME);
			fieldName				= childTableAnchor+"_"+appendor+fieldName;
			String dbField			= XMLUtil.getTagText(node,DB_FIELD);
			String dataType			= XMLUtil.getTagText(node,DATA_TYPE);
			String displayName		= XMLUtil.getTagText(node,DISPLAY_NAME);
			String srcTable			= XMLUtil.getTagText(node,SRC_TABLE);
			String srcField			= XMLUtil.getTagText(node,SRC_FIELD);
			String srcValue			= XMLUtil.getTagText(node,SRC_VALUE);
			String transformMethod	= XMLUtil.getTagText(node,TRANSFORM_METHOD);
			String transformMethodParam	= XMLUtil.getTagText(node,TRANSFORM_METHOD_PARAM);
			String fieldExport		= XMLUtil.getTagText(node,FIELD_EXPORT);

			String isActive		= XMLUtil.getTagText(node, IS_ACTIVE);
			String isBuildField		= XMLUtil.getTagText(node, IS_BUILD_FIELD);
			String isMandatory 		= XMLUtil.getTagText(node, IS_MANDATORY);
			String section	 		= XMLUtil.getTagText(node,SECTION);
			String displayType 		= XMLUtil.getTagText(node,DISPLAY_TYPE);
			
			if(!StringUtil.isValidNew(isMandatory)) {
				isMandatory = Constants.FALSE;
			}

			String validationType	= null;
			Node validationNode		= XMLUtil.getNodeInChildren(
															node,
															VALIDATION
																);
			if(validationNode != null ) {
				validationType		= XMLUtil.getTagText(
														validationNode,
														VALIDATION_TYPE
														);
			}
			Trigger fieldTrigger	= null;
			
				
			fieldMappings.addField	(fieldName,
					 dbField, displayName, dataType,
					 validationType, width,
					 colspan, 	  
					 summary ? Field.FieldType.ALL_AND_SUMMARY : Field.FieldType.ALL_ONLY,
					 srcTable, srcField, srcValue,
					 transformMethod, transformMethodParam,
					 null, null,
					 null, null,
					 null, false,
					 false,   
					 false, 
					 false, null, null,
					 "false", isActive, isBuildField, null, 0, Boolean.parseBoolean(isMandatory), 
					 null, null, section,displayType,null,null,null,null
					);
        }
        return fieldMappings;
    }
    
	public static FieldMappings getValidationFieldMappings(String location, String childTableAnchor , FieldMappings fieldMappings,int appendor)
    {
        Element root			= XMLUtil.loadDocument(location);
        String connectionName 	= MultiTenancyUtil.getTenantName();
        String tableName 		= XMLUtil.getTagText(root,TABLE_NAME);
		String tableDisplayName = XMLUtil.getTagText(root,TABLE_DISPLAY_NAME);
		//P_B_FIM_75246 added by neeti starts
		String tableInternalName = XMLUtil.getTagText(root,TABLE_INTERNAL_NAME);
		//P_B_FIM_75246 added by neeti ends
		NodeList idList			= root.getElementsByTagName(ID_FIELD);
        String[] idField 		= new String[idList.getLength()];
		for (int loop = 0; loop < idList.getLength(); loop++)
		{
			Node node			= idList.item(loop);
			idField[loop]		= node.getFirstChild().getNodeValue();
		}
		NodeList list = root.getElementsByTagName(FIELD);
        for (int loop = 0; loop < list.getLength(); loop++)
        {
            Node node 				= list.item(loop);
            String summaryString 	= XMLUtil.getAttributeValue(node,SUMMARY);
            String width			= XMLUtil.getAttributeValue(node,WIDTH);

            //Added by Anuj 2005-07-23 for Export Layout
			String colspan			= XMLUtil.getAttributeValue(node,COLSPAN) != null ? XMLUtil.getAttributeValue(node,COLSPAN) : "1";

			boolean summary 		= (MultiTenancyUtil.getTenantConstants().TRUE.equalsIgnoreCase(summaryString));
			String fieldName		= XMLUtil.getTagText(node,FIELD_NAME);
			fieldName				= childTableAnchor+"_"+appendor+fieldName;
			String dbField			= XMLUtil.getTagText(node,DB_FIELD);
			String dataType			= XMLUtil.getTagText(node,DATA_TYPE);
			String displayName		= XMLUtil.getTagText(node,DISPLAY_NAME);
			String srcTable			= XMLUtil.getTagText(node,SRC_TABLE);
			String srcField			= XMLUtil.getTagText(node,SRC_FIELD);
			String srcValue			= XMLUtil.getTagText(node,SRC_VALUE);
			String transformMethod	= XMLUtil.getTagText(node,TRANSFORM_METHOD);
			String transformMethodParam	= XMLUtil.getTagText(node,TRANSFORM_METHOD_PARAM);
			String fieldExport		= XMLUtil.getTagText(node,FIELD_EXPORT);


			String validationType	= null;
			Node validationNode		= XMLUtil.getNodeInChildren(
															node,
															VALIDATION
																);
			if(validationNode != null ) {
				validationType		= XMLUtil.getTagText(
														validationNode,
														VALIDATION_TYPE
														);
			}
			fieldMappings.addField	(fieldName,
									 dbField,
									 displayName,
									 dataType,
									 validationType,
									 width,
									 colspan, //Added by Anuj 2005-07-23
									 summary ? Field.FieldType.ALL_AND_SUMMARY : Field.FieldType.ALL_ONLY,
									 srcTable,
									 srcField,
									 srcValue,
									 transformMethod,
									 transformMethodParam,
									 null,
									 null,
									 null,
									 null,
									 null,
									 false,
									 false,		// add by AmitG for fileField
									 false		// add by Sunilk for Custom Field Export
									);

        }
        return fieldMappings;
    }
	
/*    *//**
     * @AUDIT_ENHANCEMENT_CHANGES
     * @param location
     * @return
     *//*
    public static ScoreMappings getScoresMappings(String location) {
    	ScoreMappings fieldMappings = null;
    	try {
    		Element root					= XMLUtil.loadDocument(location);
    		String connectionName		 	= MultiTenancyUtil.getTenantName();
    		String tempName 				= XMLUtil.getTagText(root,SCORE_TEMPLATE_NAME);
    		String tempDisplayName 		= XMLUtil.getTagText(root,SCORE_TEMPLATE_DISPLAY);

    		SequenceMap sectionAttrFldData = null;
    		Node scoreSectionsNode	= XMLUtil.getTagNode(root, SCORE_SECTION);
    		String sName = null;
    		String display = null;
    		String type		= null;
    		if (scoreSectionsNode != null) {
    			sName 					= XMLUtil.getAttributeValue(scoreSectionsNode, NAME);
    			display 				= XMLUtil.getAttributeValue(scoreSectionsNode, DISPLAY);
				type					= XMLUtil.getAttributeValue(scoreSectionsNode, TYPE);
				String scoreMethod 		= XMLUtil.getTagText(scoreSectionsNode, CALL_METHOD);
				String paramTotal 		= XMLUtil.getTagText(scoreSectionsNode, TOTAL_SCORE_PARAM);
				String paramActual 		= XMLUtil.getTagText(scoreSectionsNode, ACTUAL_SCORE_PARAM);
				String isScoring 		= XMLUtil.getTagText(scoreSectionsNode, IS_SCORING);
				String isPercentage		= XMLUtil.getTagText(scoreSectionsNode, IS_PERCENTAGE);
				
				fieldMappings = new ScoreMappings(connectionName, tempName, tempDisplayName, new ScoreSection(sName, display, type, scoreMethod, paramTotal, paramActual, isScoring, isPercentage));
				
    			Node[] scoreNodes	= XMLUtil.getNodesInChildren(scoreSectionsNode, SCORE_FIELD);
    			String name			= null;
    			for (int loop=0; scoreNodes != null && loop < scoreNodes.length; loop++) {
    				sectionAttrFldData = XMLUtil.getNodeAttributesMap(scoreNodes[loop]);
    				name		= XMLUtil.getAttributeValue(scoreNodes[loop], NAME);
    				type		= XMLUtil.getAttributeValue(scoreNodes[loop], TYPE);

    				Node scorelow		= XMLUtil.getNodeInChildren(scoreNodes[loop], SCORE_LOW);
    				String scoreLowMethod 		= XMLUtil.getAttributeValue(scorelow, CALL_METHOD);
    				String scoreLowValue 		= XMLUtil.getAttributeValue(scorelow, VALUE);
    				
    				Node scoreMax		= XMLUtil.getNodeInChildren(scoreNodes[loop], SCORE_MAX);
    				String scoreMaxMethod 		= XMLUtil.getAttributeValue(scoreMax, CALL_METHOD);
    				String scoreMaxValue 		= XMLUtil.getAttributeValue(scoreMax, VALUE);

    				fieldMappings.addScoreField(name, type, scoreLowMethod, scoreLowValue, scoreMaxMethod, scoreMaxValue);
    			}
    		}
		
        
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return fieldMappings;
    }*/
     //BB-20150203-259 (Section Re-ordering changes) starts
    public static SequenceMap sortHeaderMap(SequenceMap headerMap) {
        int n, c, d;
        HeaderMap swap;
        n = headerMap.size();
        HeaderMap array[] = new HeaderMap[n];
        for (c = 0; c < n; c++)
            array[c] = (HeaderMap)headerMap.get(headerMap.getKey(c));
        for (c = 0; c < (n - 1); c++) {
            for (d = 0; d < n - c - 1; d++) {
                if(array[d].getOrder() > array[d+1].getOrder()) /* For ascending order sort according to section order < */
                {
                    swap = array[d];
                    array[d] = array[d+1];
                    array[d+1] = swap;
                }
            }
        }
        SequenceMap sortedHeaderMap = new SequenceMap();
        for (c = 0; c < n; c++)
            sortedHeaderMap.put(array[c].getName(), array[c]);
        
        return sortedHeaderMap;

    }
  //BB-20150203-259 (Section Re-ordering changes) ends
    public static void main(String[] args)throws Exception{
    	String sLocation		= "/home/abhishek/cvs/DemoBuildNew/DemoBuild/src/config/xml/tables/audit/scoreTemplate.xml";
//    	Trigger trig			= new Trigger(false, false, false);
//    	FieldMappings map		= getFieldMappings(sLocation);
//		map.setRecordTrigger(trig);
//		setTriggers(sLocation, map);
    	ScoreMappings map		= getScoresMappings(sLocation);
    	String[] strArray = map.scoreFields();
    	for(int i=0; i < strArray.length; i++) {
    		System.out.println(strArray[i]);
    	}
    }
}
