/**
 * Updating this utility according to new Form design pattern.
 * Some Map and Fields are associated with this design to make Generic solution of the problem 
 * @author abhishek gupta
 * @date 2 Jan 2012
 */
/*
 P_B_FIM_75246       8 Jun 2011         Neeti Solanki               field table-internal-name is added for configure the custom report with configure fim tab
 PW_FORM_VERSION	 15 July 2013		Veerpal Singh				Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
  PWISE_FORM_LOADING_ISSUE	19 June 2014	Veerpal Singh		due to long time delay in loading a long form , Now form data loaded by ajax asynchronously.
  PW_ACTIONITEM_DELETE_ISSUE  25 Sep 2014	 Rohit Jain				Action Item can't be delete until it associated with Form,Visits and Questions
  EXTERNAL_FORM_BUILDER			24 Feb 2015		Veerpal Singh		Page generation API for External Pages
 */
package com.home.builderforms;


import com.home.builderforms.Info;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.*;
import com.home.builderforms.Field;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.TableXMLDAO;

import org.json.JSONObject;

public class FieldMappings
{
    private SequenceMap fieldMap;
    private String connectionName;
    private String tableName;
    private String[] idField;
    private String[][] queryParams;
    private int summaryCount;
    private int allCount;
    private int validationCount;
    private SequenceMap foreignTablesMap;
    private SequenceMap foreignTablesExportMap;
    private SequenceMap headerMap;
    private String msTableDisplayName;
    private String msTableExportDisplayName;
    //P_B_FIM_75246 added by neeti starts
    private String msTableInternalName;
    //P_B_FIM_75246 added by neeti ends
    private Trigger mRecordTrigger;
    private String msSeqFldName;
    private SequenceMap questionExtMap;
    private int totalActionItemCount = -1;
    private SequenceMap metaDataMap;
    private String isBuildTable;//P_B_CM_38261

    private String entityTableDisplayName;

    public String getEntityTableDisplayName() {
        return entityTableDisplayName;
    }

    public void setEntityTableDisplayName(String entityTableDisplayName) {
        this.entityTableDisplayName = entityTableDisplayName;
    }
    
	public SequenceMap getquestionExtMap()
	{
		return questionExtMap;
	}

    public boolean isEntityExport() {
        HttpSession session  = StrutsUtil.getHttpSession();
        if("fim".equals(session.getAttribute("menuName")) && ("fimexport".equals(session.getAttribute("subMenuName")) || "fimreports".equals(session.getAttribute("subMenuName"))) && "3".equals(session.getAttribute("fimFlag"))){
            return true;
        }
        return false;
    }


    public FieldMappings(
            String connectionName,
            String tableName,
            String[] idField,
            SequenceMap foreignTables,
            String psTableDisplayName,
            String psTableInternalName,
            Trigger pTrigger
    )
    {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext())
        {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount = 0;
        allCount = 0;
        validationCount = 0;
        foreignTablesMap		= foreignTables;

        msTableDisplayName		= psTableDisplayName;
        //P_B_FIM_75246 added by neeti starts
        msTableInternalName     = psTableInternalName;
        //P_B_FIM_75246 added by neeti ends
        mRecordTrigger			= pTrigger;
    }

    public FieldMappings(
            String connectionName,
            String tableName,
            String[] idField,
            SequenceMap foreignTables,
            String psTableDisplayName,
            String psTableInternalName
    )
    {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext())
        {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount = 0;
        allCount = 0;
        foreignTablesMap		= foreignTables;


        msTableDisplayName		= psTableDisplayName;
        //P_B_FIM_75246 added by neeti starts
        msTableInternalName		= psTableInternalName;
        //P_B_FIM_75246 added by neeti ends
    }

    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount = 0;
        allCount = 0;
        validationCount = 0;
        foreignTablesMap		= foreignTables;
        msTableDisplayName		= psTableDisplayName;
        msTableInternalName     = psTableInternalName;
        mRecordTrigger			= pTrigger;
        this.headerMap = headerMap;
    }

    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount = 0;
        allCount = 0;
        validationCount = 0;
        foreignTablesMap		= foreignTables;
        msTableDisplayName		= psTableDisplayName;
        msTableInternalName     = psTableInternalName;
        mRecordTrigger			= pTrigger;
        this.headerMap = headerMap;
        this.msSeqFldName = msSeqFldName;
    }

    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName, String psTableExportDisplayName) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount = 0;
        allCount = 0;
        validationCount = 0;
        foreignTablesMap		= foreignTables;
        msTableDisplayName		= psTableDisplayName;
        msTableInternalName     = psTableInternalName;
        mRecordTrigger			= pTrigger;
        this.headerMap = headerMap;
        this.msSeqFldName = msSeqFldName;
        this.msTableExportDisplayName = psTableExportDisplayName;
    }

    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName, String psTableExportDisplayName, SequenceMap foreignTablesExport) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount 					= 0;
        allCount 						= 0;
        validationCount 				= 0;
        foreignTablesMap				= foreignTables;
        msTableDisplayName				= psTableDisplayName;
        msTableInternalName     		= psTableInternalName;
        mRecordTrigger					= pTrigger;
        this.headerMap 					= headerMap;
        this.msSeqFldName 				= msSeqFldName;
        this.msTableExportDisplayName 	= psTableExportDisplayName;
        foreignTablesExportMap			= foreignTablesExport;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Allow Question pattern in Field Mapping object e.g. Question(s) will be part of Mapping object where each question can have one or More Field(s) in it.
     * Here contructor saves Map of Question(s) related information to member variable which can be later retrieved and manipulated for Form level processes such as Add/Modify/View.  
     * @param connectionName
     * @param tableName
     * @param idField
     * @param foreignTables
     * @param psTableDisplayName
     * @param psTableInternalName
     * @param pTrigger
     * @param headerMap
     * @param msSeqFldName
     * @param psTableExportDisplayName
     * @param foreignTablesExport
     * @param questionExtMap
     */
    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName, String psTableExportDisplayName, SequenceMap foreignTablesExport, SequenceMap questionExtMap) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount 					= 0;
        allCount 						= 0;
        validationCount 				= 0;
        foreignTablesMap				= foreignTables;
        msTableDisplayName				= psTableDisplayName;
        msTableInternalName     		= psTableInternalName;
        mRecordTrigger					= pTrigger;
        this.headerMap 					= headerMap;
        this.msSeqFldName 				= msSeqFldName;
        this.msTableExportDisplayName 	= psTableExportDisplayName;
        foreignTablesExportMap			= foreignTablesExport;
        this.questionExtMap				= questionExtMap;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Meta data have being become part of Form level management in which it contains Form's Header and Tab(s) information.
     * Constructor now allow storing these in Mapping object so that these information further retrieved and manipulated to
     * manage Display e.g. Multiple part Form View, thier Heading with multiple column(s) sub heading format, Submission of in multi level etc.   
     * @param connectionName
     * @param tableName
     * @param idField
     * @param foreignTables
     * @param psTableDisplayName
     * @param psTableInternalName
     * @param pTrigger
     * @param headerMap
     * @param msSeqFldName
     * @param psTableExportDisplayName
     * @param foreignTablesExport
     * @param questionExtMap
     * @param metaDataMap
     */
    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName, String psTableExportDisplayName, SequenceMap foreignTablesExport, SequenceMap questionExtMap, SequenceMap metaDataMap) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount 					= 0;
        allCount 						= 0;
        validationCount 				= 0;
        foreignTablesMap				= foreignTables;
        msTableDisplayName				= psTableDisplayName;
        msTableInternalName     		= psTableInternalName;
        mRecordTrigger					= pTrigger;
        this.headerMap 					= headerMap;
        this.msSeqFldName 				= msSeqFldName;
        this.msTableExportDisplayName 	= psTableExportDisplayName;
        foreignTablesExportMap			= foreignTablesExport;
        this.questionExtMap				= questionExtMap;
        this.metaDataMap				= metaDataMap;
    }
    //P_B_CM_38261 : starts
    public FieldMappings(String connectionName, String tableName, String[] idField, SequenceMap foreignTables, String psTableDisplayName, String psTableInternalName,
                         Trigger pTrigger, SequenceMap headerMap, String msSeqFldName, String psTableExportDisplayName, SequenceMap foreignTablesExport, SequenceMap questionExtMap, SequenceMap metaDataMap,String isBuildTable) {
        fieldMap = new SequenceMap();
        this.connectionName		= connectionName;
        this.tableName 			= tableName;
        this.idField 			= idField;
        Iterator defaultFieldNames = Constants.DEFAULT_FIELD_MAPPINGS.keySet().iterator();
        while (defaultFieldNames.hasNext()) {
            String fieldName = (String)defaultFieldNames.next();
            fieldMap.put(fieldName,Constants.DEFAULT_FIELD_MAPPINGS.get(fieldName));
        }
        summaryCount 					= 0;
        allCount 						= 0;
        validationCount 				= 0;
        foreignTablesMap				= foreignTables;
        msTableDisplayName				= psTableDisplayName;
        msTableInternalName     		= psTableInternalName;
        mRecordTrigger					= pTrigger;
        this.headerMap 					= headerMap;
        this.msSeqFldName 				= msSeqFldName;
        this.msTableExportDisplayName 	= psTableExportDisplayName;
        foreignTablesExportMap			= foreignTablesExport;
        this.questionExtMap				= questionExtMap;
        this.metaDataMap				= metaDataMap;
        this.isBuildTable				= isBuildTable;
    }
    //P_B_CM_38261 : ends
    public void setQueryParams(String[][] queryParams)
    {
        this.queryParams = queryParams;
    }

    public String[][] getQueryParams()
    {
        return queryParams;
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan, //Added by Anuj 2005-07-23
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,//added by vivek
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField, //added by AmitG for FileField
                         boolean pbCustomField) //added by Sunilk for Custom Field Export
    {
        addField(fieldName, dbField, displayName,
                dataType,  validationType, width, colspan, //Added by Anuj 2005-07-23
                fieldType, psSrcTable, psSrcField, psSrcValue,
                psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod,
                psSearchSourceMethodArgs,// added by vivek
                psSearchSourceValuesURL, pTrigger, pbFieldExport,
                pbFileField, //added by AmitG for FileField
                pbCustomField, //added by Sunilk for Custom Field Export
                false, "");
    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan, //Added by Anuj 2005-07-23
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs, // added by vivek
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,   // added by AmitG for FileField
                         boolean pbCustomField, // added by Sunilk for Custom Field Export
                         boolean sortable, String sortColumn)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan, //Added by Anuj 2005-07-23
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs, // added by vivek
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,   // added by AmitG for FileField
                         boolean pbCustomField, // added by Sunilk for Custom Field Export
                         boolean sortable, String sortColumn, String isCurrency)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan, //Added by Anuj 2005-07-23
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs, // added by vivek
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,   // added by AmitG for FileField
                         boolean pbCustomField, // added by Sunilk for Custom Field Export
                         boolean sortable, String sortColumn, String isCurrency, String groupBy)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, 0);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, false);
    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, null, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam) {

        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section) {

        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, section, null);
    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType) {

        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, section, displayType,null,null,null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                         boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,String validInput,String noOfCols, String noOfRows) {

        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, null, null);

    }
    /**
     * Override function to add currency field and group by field for custom report
     * @updated by abhishek gupta
     * @date 24 dec 2009
     * @param fieldName
     * @param dbField
     * @param displayName
     * @param dataType
     * @param validationType
     * @param width
     * @param colspan
     * @param fieldType
     * @param psSrcTable
     * @param psSrcField
     * @param psSrcValue
     * @param psTransformMethod
     * @param psTransformMethodParam
     * @param psSearchField
     * @param psSearchSourceValuesMethod
     * @param psSearchSourceMethodArgs
     * @param psSearchSourceValuesURL
     * @param pTrigger
     * @param pbFieldExport
     * @param pbFileField
     * @param pbCustomField
     * @param sortable
     * @param sortColumn
     * @param isCurrency
     * @param groupBy
     */
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,null,null,null,null,null,null, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField,String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField,String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, null,null,null,null,null,
                null,null,null,null,null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, null, null, null, null, null, null);

    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, null);

    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, null,null,null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, null);
    }

    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                null,null,null,null,null,null,null,null,null);
    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, null, "0");
    }
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, null, null);
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Allow question name as part of Field to identify how many Fields are in association for one question
     * @param fieldName
     * @param dbField
     * @param displayName
     * @param dataType
     * @param validationType
     * @param width
     * @param colspan
     * @param fieldType
     * @param psSrcTable
     * @param psSrcField
     * @param psSrcValue
     * @param psTransformMethod
     * @param psTransformMethodParam
     * @param psSearchField
     * @param psSearchSourceValuesMethod
     * @param psSearchSourceMethodArgs
     * @param psSearchSourceValuesURL
     * @param pTrigger
     * @param pbFieldExport
     * @param pbFileField
     * @param pbCustomField
     * @param sortable
     * @param sortColumn
     * @param isCurrency
     * @param groupBy
     * @param isActive
     * @param isBuildField
     * @param buildFieldSeq
     * @param orderBy
     * @param isMandatory
     * @param fieldMethod
     * @param fieldMethodParam
     * @param section
     * @param displayType
     * @param validInput
     * @param noOfCols
     * @param noOfRows
     * @param dbFldLength
     * @param mKeyword
     * @param mActive
     * @param comboSourceValuesMethod
     * @param comboSourceMethodArgs
     * @param comboMethodParam
     * @param parent
     * @param dependentField
     * @param comboSourceMethodOtherField
     * @param comboSourceMethodOtherFieldType
     * @param piiEnabled
     * @param obj
     * @param countryOpt
     * @param radioSourceValuesMethod
     * @param radioSourceMethodArgs
     * @param radioMethodParam
     * @param radioSourceMethodOtherField
     * @param radioSourceMethodOtherFieldType
     * @param checkboxSourceValuesMethod
     * @param checkboxSourceMethodArgs
     * @param checkboxMethodParam
     * @param checkboxSourceMethodOtherField
     * @param checkboxSourceMethodOtherFieldType
     * @param colspanexport
     * @param sourceField
     * @param displayExportName
     * @param transformMethodExportParam
     * @param transformExportMethod
     * @param transformExportMethodParam
     * @param transformRequiredInExport
     * @param allowInMain
     * @param combinedDisplayName
     * @param mailMergeTransformMethod
     * @param radioSourceOtherFieldMethod
     * @param checkboxMethodReturnType
     * @param checkboxConditionField
     * @param checkboxConditionFieldValue
     * @param fieldCalledMethod
     * @param dependentModule
     * @param comboSourceMethodOtherFieldSpan
     * @param conditionField
     * @param conditionFieldValue
     * @param checkboxSourceValuesColCount
     * @param questionName
     * @param isDependentTableField
     * @param dependentTableName
     */
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, null, null, null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Allow question name as part of Field to identify how many Fields are in association for one question
     * @param fieldName
     * @param dbField
     * @param displayName
     * @param dataType
     * @param validationType
     * @param width
     * @param colspan
     * @param fieldType
     * @param psSrcTable
     * @param psSrcField
     * @param psSrcValue
     * @param psTransformMethod
     * @param psTransformMethodParam
     * @param psSearchField
     * @param psSearchSourceValuesMethod
     * @param psSearchSourceMethodArgs
     * @param psSearchSourceValuesURL
     * @param pTrigger
     * @param pbFieldExport
     * @param pbFileField
     * @param pbCustomField
     * @param sortable
     * @param sortColumn
     * @param isCurrency
     * @param groupBy
     * @param isActive
     * @param isBuildField
     * @param buildFieldSeq
     * @param orderBy
     * @param isMandatory
     * @param fieldMethod
     * @param fieldMethodParam
     * @param section
     * @param displayType
     * @param validInput
     * @param noOfCols
     * @param noOfRows
     * @param dbFldLength
     * @param mKeyword
     * @param mActive
     * @param comboSourceValuesMethod
     * @param comboSourceMethodArgs
     * @param comboMethodParam
     * @param parent
     * @param dependentField
     * @param comboSourceMethodOtherField
     * @param comboSourceMethodOtherFieldType
     * @param piiEnabled
     * @param obj
     * @param countryOpt
     * @param radioSourceValuesMethod
     * @param radioSourceMethodArgs
     * @param radioMethodParam
     * @param radioSourceMethodOtherField
     * @param radioSourceMethodOtherFieldType
     * @param checkboxSourceValuesMethod
     * @param checkboxSourceMethodArgs
     * @param checkboxMethodParam
     * @param checkboxSourceMethodOtherField
     * @param checkboxSourceMethodOtherFieldType
     * @param colspanexport
     * @param sourceField
     * @param displayExportName
     * @param transformMethodExportParam
     * @param transformExportMethod
     * @param transformExportMethodParam
     * @param transformRequiredInExport
     * @param allowInMain
     * @param combinedDisplayName
     * @param mailMergeTransformMethod
     * @param radioSourceOtherFieldMethod
     * @param checkboxMethodReturnType
     * @param checkboxConditionField
     * @param checkboxConditionFieldValue
     * @param fieldCalledMethod
     * @param dependentModule
     * @param comboSourceMethodOtherFieldSpan
     * @param conditionField
     * @param conditionFieldValue
     * @param checkboxSourceValuesColCount
     * @param questionName
     * @param isDependentTableField
     * @param dependentTableName
     */
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge,null);
    }

    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge,null, null, null, null, null, null);

    }

    //BB-20150319-268(FIM Campaign Center)
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, null);

    }



    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign)
    {
        addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, null);

    }


    //BB-20150203-259 (Dynamic Response based on parent field response)
    public void addField(String fieldName,
                         String dbField,
                         String displayName,
                         String dataType,
                         String validationType,
                         String width,
                         String colspan,
                         int fieldType,
                         String psSrcTable,
                         String psSrcField,
                         String psSrcValue,
                         String psTransformMethod,
                         String psTransformMethodParam,
                         String psSearchField,
                         String psSearchSourceValuesMethod,
                         String psSearchSourceMethodArgs,
                         String psSearchSourceValuesURL,
                         Trigger pTrigger,
                         boolean pbFieldExport,
                         boolean pbFileField,
                         boolean pbCustomField,
                         boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                         int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                         String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                         String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                         String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                         String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                         String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                         String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                         String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                         String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                         String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization)//BB-20150319-268(FIM Campaign Center)
    {
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
                fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
                psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
                pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
                fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
                comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
                radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
                checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
                transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
                checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
                conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,null);
    }
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled)//BB-20150319-268(FIM Campaign Center)
{
addField(fieldName, dbField, displayName, dataType, validationType, width, colspan,
   fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam,
   psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL,
   pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,
   fieldMethod, fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword, mActive,comboSourceValuesMethod,comboSourceMethodArgs,
   comboMethodParam,parent,dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod,
   radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs,
   checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
   transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod,
   checkboxMethodReturnType,checkboxConditionField,checkboxConditionFieldValue,fieldCalledMethod,dependentModule,comboSourceMethodOtherFieldSpan,conditionField,
   conditionFieldValue,checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,null,null,null);
}
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod)//BB-20150319-268(FIM Campaign Center)
{
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL, pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy,
    			isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput, noOfCols, noOfRows, dbFldLength, mKeyword, mActive, comboSourceValuesMethod, comboSourceMethodArgs, comboMethodParam, parent, dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, 
    			radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain, combinedDisplayName, mailMergeTransformMethod, radioSourceOtherFieldMethod, 
    			checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign,
    			internationalization, defaultPiiEnabled, castInOrder, customSortable, jsValidationMethod,null);
}
    
    //P_Enh_Sync_Fields starts
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay)//BB-20150319-268(FIM Campaign Center)
{
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL, pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy,
    			isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput, noOfCols, noOfRows, dbFldLength, mKeyword, mActive, comboSourceValuesMethod, comboSourceMethodArgs, comboMethodParam, parent, dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, 
    			radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain, combinedDisplayName, mailMergeTransformMethod, radioSourceOtherFieldMethod, 
    			checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign,
    			internationalization, defaultPiiEnabled, castInOrder, customSortable, jsValidationMethod,null, null, null, null);
}
  //P_Enh_Sync_Fields ends
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly)//BB-20150319-268(FIM Campaign Center) //P_Enh_Sync_Fields
    {
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL, pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy,
    			isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput, noOfCols, noOfRows, dbFldLength, mKeyword, mActive, comboSourceValuesMethod, comboSourceMethodArgs, comboMethodParam, parent, dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, 
    			radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain, combinedDisplayName, mailMergeTransformMethod, radioSourceOtherFieldMethod, 
    			checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign,
    			internationalization, defaultPiiEnabled, castInOrder, customSortable, jsValidationMethod, centerInfoDisplay, syncWith, syncTotalMap, readOnly, null);
    }
    
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule)//BB-20150319-268(FIM Campaign Center) //P_Enh_Sync_Fields
{
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL, pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy,
    			isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput, noOfCols, noOfRows, dbFldLength, mKeyword, mActive, comboSourceValuesMethod, comboSourceMethodArgs, comboMethodParam, parent, dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, 
    			radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain, combinedDisplayName, mailMergeTransformMethod, radioSourceOtherFieldMethod, 
    			checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign,
    			internationalization, defaultPiiEnabled, castInOrder, customSortable, jsValidationMethod, centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, null);
}
    
    public void addField(String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String width,
            String colspan,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceMethodArgs,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
            int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
            String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
            String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
            String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
            String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
            String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
            String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
            String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
            String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
            String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule,HashMap fieldData)//BB-20150319-268(FIM Campaign Center) //P_Enh_Sync_Fields
{
    	addField(fieldName, dbField, displayName, dataType, validationType, width, colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod, psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceMethodArgs, psSearchSourceValuesURL, pTrigger, pbFieldExport, pbFileField, pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy,
    			isMandatory, fieldMethod, fieldMethodParam, section, displayType, validInput, noOfCols, noOfRows, dbFldLength, mKeyword, mActive, comboSourceValuesMethod, comboSourceMethodArgs, comboMethodParam, parent, dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceValuesMethod, radioSourceMethodArgs, radioMethodParam, radioSourceMethodOtherField, 
    			radioSourceMethodOtherFieldType, checkboxSourceValuesMethod, checkboxSourceMethodArgs, checkboxMethodParam, checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, transformRequiredInExport, allowInMain, combinedDisplayName, mailMergeTransformMethod, radioSourceOtherFieldMethod, 
    			checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign,
    			internationalization, defaultPiiEnabled, castInOrder, customSortable, jsValidationMethod, centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, fieldData,null);
}
    
        public void addField(String fieldName,
                String dbField,
                String displayName,
                String dataType,
                String validationType,
                String width,
                String colspan,
                int fieldType,
                String psSrcTable,
                String psSrcField,
                String psSrcValue,
                String psTransformMethod,
                String psTransformMethodParam,
                String psSearchField,
                String psSearchSourceValuesMethod,
                String psSearchSourceMethodArgs,
                String psSearchSourceValuesURL,
                Trigger pTrigger,
                boolean pbFieldExport,
                boolean pbFileField,
                boolean pbCustomField,
                boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq,
                int orderBy, boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType,
                String validInput,String noOfCols, String noOfRows, String dbFldLength, String mKeyword, String mActive,String comboSourceValuesMethod,String comboSourceMethodArgs,
                String comboMethodParam,String parent,String dependentField, String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj,
                String countryOpt, String radioSourceValuesMethod,String radioSourceMethodArgs, String radioMethodParam, String radioSourceMethodOtherField,
                String radioSourceMethodOtherFieldType, String checkboxSourceValuesMethod,String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,
                String checkboxSourceMethodOtherFieldType, String colspanexport,  String sourceField, String displayExportName, String transformMethodExportParam,
                String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport, String allowInMain,String combinedDisplayName,
                String mailMergeTransformMethod, String radioSourceOtherFieldMethod, String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue,
                String fieldCalledMethod, String dependentModule, String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue,String checkboxSourceValuesColCount,
                String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect, String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule, HashMap fieldData,String isParsingKeywordConfigured)//BB-20150319-268(FIM Campaign Center) //P_Enh_Sync_Fields
{
fieldMap.put(fieldName, new Field(fieldName,
       dbField,
       displayName,
       dataType,
       validationType,
       width,
       colspan,
       fieldType,
       psSrcTable,
       psSrcField,
       psSrcValue,
       psTransformMethod,
       psTransformMethodParam,
       psSearchField,
       psSearchSourceValuesMethod,
       psSearchSourceValuesURL,
       psSearchSourceMethodArgs,
       pTrigger,
       pbFieldExport,
       pbFileField,
       pbCustomField,
       sortable, sortColumn, isCurrency,
       groupBy,isActive, isBuildField, buildFieldSeq, orderBy, isMandatory,fieldMethod,fieldMethodParam,
       section,displayType,validInput, noOfCols,noOfRows, dbFldLength,mKeyword,mActive,comboSourceValuesMethod,comboSourceMethodArgs,comboMethodParam,parent,
       dependentField, comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj,countryOpt, radioSourceValuesMethod,radioSourceMethodArgs,
       radioMethodParam, radioSourceMethodOtherField, radioSourceMethodOtherFieldType, checkboxSourceValuesMethod,checkboxSourceMethodArgs, checkboxMethodParam,
       checkboxSourceMethodOtherField, checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam,
       transformExportMethod, transformExportMethodParam,transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod,
       radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField, checkboxConditionFieldValue,fieldCalledMethod,dependentModule,
       comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue,checkboxSourceValuesColCount,questionName, fldViewType,isDependentTableField,
       dependentTableName, colspanMerge,colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, 
       internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, fieldData,isParsingKeywordConfigured//BB-20150319-268(FIM Campaign Center)// added by Abhishek gupta for Custom Field Export //P_Enh_Sync_Fields
)); //BB-20150203-259 (Dynamic Response based on parent field response)

        if (fieldType == Field.FieldType.ALL_AND_SUMMARY)
        {
            summaryCount++;
            allCount++;
        } else if (fieldType==Field.FieldType.ALL_ONLY)
        {
            allCount++;
        }

        if (validationType != null)
        {
            validationCount++;
        }
    }


    /*public void addField(String fieldName,
						 String dbField,
						 String displayName,
						 String dataType,
						 String width,
						 int fieldType,
						 String psSrcTable,
						 String psSrcField,
						 String psSrcValue,
						 String psTransformMethod,
						 String psSearchField,
						 String psSearchSourceValuesMethod,
						 String psSearchSourceValuesURL)
	{
		fieldMap.put(fieldName,
					 new Field(fieldName,
							   dbField,
							   displayName,
							   dataType,
							   fieldType,
							   psSrcTable,
							   psSrcField,
							   psSrcValue,
							   psTransformMethod,
							   psSearchField,
							   psSearchSourceValuesMethod,
							   psSearchSourceValuesURL
							  ));
		if (fieldType == Field.FieldType.ALL_AND_SUMMARY)
		{
			summaryCount++;
			allCount++;
		}
		else if (fieldType==Field.FieldType.ALL_ONLY)
		{
			allCount++;
		}

	}*/
    public String getDbField(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.getDbField();
        else
            return null;
    }

    public String getDisplayName(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.getDisplayName();
        else
            return null;
    }

    public String getDataType(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.getDataType();
        else
            return null;
    }

    public String getValidationType(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.getValidationType();
        else
            return null;
    }

    public boolean getActiveFldType(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.isActiveField();
        else
            return false;
    }
    public boolean getMandatoryFldType(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.isMandatoryField();
        else
            return false;
    }

    public List<Field> getMandatoryFieldsList()
    {
        Iterator<?> it = fieldMap.values().iterator();
        List<Field> mandatoryFields = new ArrayList<Field>();
        while(it.hasNext())
        {
            Field field = (Field)it.next();
            if (field!=null && field.isMandatoryField()){
                mandatoryFields.add(field);
            }
        }
        return mandatoryFields;
    }

    public boolean getBuildFldType(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        if (field!=null)
            return field.isBuildField();
        else
            return false;
    }

    public Field[] getIdField()
    {
        Field[] fields = new Field[idField.length];
        for (int i=0; i<fields.length; i++)
        {
            fields[i] = getField(idField[i]);
        }
        return fields;
    }

    public String[] getIdFieldNames()
    {
        Field[] fields = getIdField();
        String[] idFieldNames = new String[fields.length];
        for (int i=0; i<fields.length; i++)
        {
            idFieldNames[i] = fields[i].getFieldName();
        }
        return idFieldNames;
    }

    public Field getField(String fieldName)
    {
        Field field = (Field)fieldMap.get(fieldName);
        return field;
    }

    public Field[] getFields(String[] fieldNames)
    {
        Field[] fields = new Field[fieldNames.length];
        for (int i=0;i<fields.length;i++)
        {
            fields[i]=getField(fieldNames[i]);
        }
        return fields;
    }

    public String toString()
    {
        return fieldMap.toString();
    }

    public SequenceMap getAllFields()
    {
        return fieldMap;
    }


    public Field[] getAllFieldsArray()
    {
        Field[] allFields = new Field[allCount];
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY ||
                    field.getFieldType() == Field.FieldType.ALL_ONLY)
            {
                allFields[i++] = field;
            }
        }
        return allFields;
    }

    public Field[] getAllValidFieldsArray()
    {
        Field[] allFields = new Field[allCount];
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY ||
                    field.getFieldType() == Field.FieldType.ALL_ONLY)
            {
                allFields[i++] = field;
            }
        }

        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < allFields.length; i++) {
            if(allFields[i].fieldAllowedInMain()) {
                arrLst.add(allFields[i]);
            }
        }

        Field[] summaryFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryFields[i] = fld;
        }

        return summaryFields;
    }

    public Field[] getAllFieldsArrayWithOrderBy() {
        Field[] allFields = new Field[allCount];
        boolean flag = false;
        Iterator it = fieldMap.values().iterator();
        int count = 0;
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            int orderId = field.getOrderBy();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                if(!field.isActiveField())
                    continue;
                if(orderId != -1) {
                    allFields[orderId] = field;
                    count++;
                }
            }
        }
        if(count == 0) {
            it = fieldMap.values().iterator();
            for (int i=0;it!=null && it.hasNext();) {
                Field field = (Field)it.next();
                if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                    allFields[i++] = field;
                }
            }
        }
        return allFields;
    }

    public Field getOtherTableField(String fieldName) {
        Iterator it = headerMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            HeaderMap hMap = (HeaderMap)it.next();
            HeaderField hFld = hMap.getHeaderFields();

            SequenceMap dependentChildMap = hFld.getDependentChildMap(); //BB-20150203-259 (Dynamic Response based on parent field response)

            DependentTable[] dTables = hFld.getDependentTables();
            for(DependentTable dTable:dTables) {
                String name = dTable.getTableAnchor();
                String name1 = dTable.getTableName();

                FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
//				String tStr = dTable.getTableAliasName();
                TableField[] tFields = dTable.getTableFields();
                for(TableField tField :tFields) {
                    String fName = tField.getFieldName();
                    String tStr = dTable.getTableAliasName()+fName;
                    if(fieldName.equals(tStr)) {
                        Field dFld = fdpMapping.getField(fName);
                        Field newdFld = (Field)dFld.clone();
                        String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                        newdFld.setSectionField(section);

                        //BB-20150203-259 (Dynamic Response based on parent field response) starts
                        if(dependentChildMap != null && dependentChildMap.size() > 0) {
                            SequenceMap dependentChildTotalMap = new SequenceMap();
                            Iterator depIt = dependentChildMap.values().iterator();
                            while(depIt.hasNext()) {
                                DependentChild child = (DependentChild)depIt.next();
                                if(child != null) {
                                    if(child.getParentField().equals(fieldName)) {
                                        dependentChildTotalMap.put(child.getFieldName(), new DependentChild(child.getFieldName(), child.getFieldMap(), child.getFunctionName()));
                                    }
                                }
                            }
                            newdFld.setDependentChildTotalMap(dependentChildTotalMap);
                        }
                        //BB-20150203-259 (Dynamic Response based on parent field response) ends
                        
                      //P_Enh_Sync_Fields starts
                        SequenceMap syncFieldMap = hFld.getSyncFieldMap();
                        if(syncFieldMap != null && syncFieldMap.size() > 0) {
                            SequenceMap syncTotalMap = new SequenceMap();
                            Iterator depIt = syncFieldMap.values().iterator();
                            while(depIt.hasNext()) {
                            	SyncWithField child = (SyncWithField)depIt.next();
                                if(child != null) {
                                    if(child.getParentField().equals(fieldName)) {
                                    	syncTotalMap.put(child.getFieldName(), new SyncWithField(child.getFieldName(), child.getTableName(), child.getColumnName(), child.getTableAnchor(), child.getParentField(), child.getDisplayName(), child.getSyncModule()));
                                    }
                                }
                            }
                            newdFld.setSyncTotalMap(syncTotalMap);
                        }
                        
                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH))) {
                        	newdFld.setSyncWithField((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH));
                        }
                        
                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE))) {
                        	newdFld.setSyncModule((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE));
                        }
                        
                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY))) {
                        	newdFld.setReadOnly((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY));
                        }
                        
                      //P_Enh_Sync_Fieldse ends

                        String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                        if(str != null && "no".equals(str)) {
                            newdFld.setActiveField(false);
                        } else {
                            newdFld.setActiveField(true);
                        }
                        String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                        if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                            newdFld.setMandatoryField(false);
                        } else {
                            newdFld.setMandatoryField(true);
                        }

                        String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                        if(tabName == null || "".equals(tabName)) {
                            newdFld.setTabName(null);
                        } else {
                            newdFld.setTabName(tabName);
                        }

                        if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                            newdFld.setColspan("2");
                        }
                        if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                            newdFld.setPiiEnabled(true);
                        } else {
                            newdFld.setPiiEnabled(false);
                        }
                        //P_CM_Enh_BuilderForm starts
                        if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                            newdFld.setSourceField(true);
                        } else {
                            newdFld.setSourceField(false);
                        }
                        //P_CM_Enh_BuilderForm ends
                        String isBuildField =(String) tField.getFieldMap().get(TableXMLDAO.IS_BUILD_FIELD);
                        if(isBuildField == null || "".equals(isBuildField) || "yes".equals(isBuildField)) {
                        	newdFld.setIsBuildField(true);
                        } else {
                        	newdFld.setIsBuildField(false);
                        }
                        
                        newdFld.setFieldName(dTable.getTableAliasName()+fName);
                        newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                        newdFld.setFieldOfOtherTable(true);
                        newdFld.setTableName(name1);
                        newdFld.setTableAnchor(name);
                        //BB-20150319-268(FIM Campaign Center) starts
                        String includeInCampaign = (String)tField.getFieldMap().get(TableXMLDAO.INCLUDE_IN_CAMPAIGN);
                        newdFld.setIncludeInCampaign(includeInCampaign);
                        //BB-20150319-268(FIM Campaign Center) ends

                        if(StringUtil.isValid(newdFld.getDependentField()))
                            newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                        if(StringUtil.isValid(newdFld.getComboMethodParam()))
                            newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                            newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                        return newdFld;
                    }
                }
            }
        }
        return null;
    }

    public SequenceMap getDependentTblFields(HeaderField hFld){
        SequenceMap headerFields = new SequenceMap();
        if(hFld!=null){
            DependentTable[] dTables = hFld.getDependentTables();
            for(DependentTable dTable:dTables) {
                String name = dTable.getTableAnchor();
                String name1 = dTable.getTableName();
                FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
                TableField[] tFields = dTable.getTableFields();
                for(TableField tField :tFields) {
                    String fName = tField.getFieldName();
                    Field dFld = fdpMapping.getField(fName);
                    Field newdFld = (Field)dFld.clone();
                    String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                    newdFld.setSectionField(section);
                    String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                    if(str != null && "no".equals(str)) {
                        newdFld.setActiveField(false);
                        continue;
                    } else {
                        newdFld.setActiveField(true);
                    }
                    String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                    if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                        newdFld.setMandatoryField(false);
                    } else {
                        newdFld.setMandatoryField(true);
                    }
                    String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                    if(tabName == null || "".equals(tabName)) {
                        newdFld.setTabName(null);
                    } else {
                        newdFld.setTabName(tabName);
                    }

                    if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                        newdFld.setColspan("2");
                    }
                    if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                        newdFld.setPiiEnabled(true);
                    } else {
                        newdFld.setPiiEnabled(false);
                    }

                    String isBuildField =(String) tField.getFieldMap().get(TableXMLDAO.IS_BUILD_FIELD);
                    if(isBuildField == null || "".equals(isBuildField) || "yes".equals(isBuildField)) {
                    	newdFld.setIsBuildField(true);
                    } else {
                        newdFld.setIsBuildField(false);
                    }
                    
                    
                    newdFld.setFieldName(dTable.getTableAliasName()+fName);
                    newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                    newdFld.setFieldOfOtherTable(true);
                    newdFld.setTableName(name1);
                    newdFld.setTableAnchor(name);
                    if(StringUtil.isValid(newdFld.getDependentField()))
                        newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                    if(StringUtil.isValid(newdFld.getComboMethodParam()))
                        newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                    if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                        newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));
                    String groupBy = (String)tField.getFieldMap().get(TableXMLDAO.GROUP_BY);
                    newdFld.setGroupByField("true".equals(groupBy) ? true:false);
                    headerFields.put(dTable.getTableAliasName()+fName,newdFld);
                }
            }
        }

        return headerFields;
    }

    /**
     * This function is used when we modify the field position, but calls before the positions get saved.
     * BB-20150203-259 (Add Preview Button in Modify Field Management Section)
     * @param hFld
     * @param dependentOnly
     * @return
     */
    public Field[] getSectionTablesFieldsByChangedPosition(HeaderField hFld, boolean dependentOnly, HttpServletRequest request) {
        Field[] allFields = null;
        ArrayList<Field> list = new ArrayList();
        if(!dependentOnly){
            String sectionType = hFld.getSectionField();
            Iterator it = fieldMap.values().iterator();
            for (int i=0;it!=null && it.hasNext();) {
                Field field = (Field)it.next();
                int orderId = field.getOrderBy();
                if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                    if(!field.isActiveField())
                        continue;
                    if(!sectionType.equals(field.getSectionField())) {
                        continue;
                    }
                    if(orderId != -1) {
                        list.add(field);
                    }
                }
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        SequenceMap dependentChildMap = hFld.getDependentChildMap(); //BB-20150203-259 (Dynamic Response based on parent field response)

        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();
            String name1 = dTable.getTableName();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();
                Field dFld = fdpMapping.getField(fName);
                Field newdFld = (Field)dFld.clone();
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                if(dependentChildMap != null && dependentChildMap.size() > 0) {
                    SequenceMap dependentChildTotalMap = new SequenceMap();
                    Iterator depIt = dependentChildMap.values().iterator();
                    while(depIt.hasNext()) {
                        DependentChild child = (DependentChild)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                                dependentChildTotalMap.put(child.getFieldName(), new DependentChild(child.getFieldName(), child.getFieldMap(), child.getFunctionName()));
                            }
                        }
                    }
                    newdFld.setDependentChildTotalMap(dependentChildTotalMap);
                }
                
              //P_Enh_Sync_Fields starts
                SequenceMap syncFieldMap = hFld.getSyncFieldMap();
                if(syncFieldMap != null && syncFieldMap.size() > 0) {
                    SequenceMap syncTotalMap = new SequenceMap();
                    Iterator depIt = syncFieldMap.values().iterator();
                    while(depIt.hasNext()) {
                    	SyncWithField child = (SyncWithField)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                            	syncTotalMap.put(child.getFieldName(), new SyncWithField(child.getFieldName(), child.getTableName(), child.getColumnName(), child.getTableAnchor(), child.getParentField(), child.getDisplayName(), child.getSyncModule()));
                            }
                        }
                    }
                    newdFld.setSyncTotalMap(syncTotalMap);
                }
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH))) {
                	newdFld.setSyncWithField((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE))) {
                	newdFld.setSyncModule((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY))) {
                	newdFld.setReadOnly((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY));
                }
                
              //P_Enh_Sync_Fields ends
                
                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                    continue;
                } else {
                    newdFld.setActiveField(true);
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }

                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                newdFld.setFieldName(dTable.getTableAliasName()+fName);

                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name1);
                newdFld.setTableAnchor(name);

                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                if(StringUtil.isValid(newdFld.getTransformMethodParam()))
                    newdFld.setTransformMethodParam(dTable.getTableAliasName()+newdFld.getTransformMethodParam());
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));
                String groupBy = (String)tField.getFieldMap().get(TableXMLDAO.GROUP_BY);
                newdFld.setGroupByField("true".equals(groupBy) ? true:false);

                list.add(newdFld);
            }
        }
        if(dependentOnly){
            return list.toArray(new Field[0]);
        }

        if(list.size() > 0) {
            allFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
                String fieldName = key.getFieldName();

                for(int j=0; j< list.size(); j++) {
                    String val = request.getParameter(FieldNames.FIELD_NAME+"_"+j);
                    if(StringUtil.isValidNew(val) && val.equals(fieldName)) {
                        String orderBy = request.getParameter(FieldNames.FIELD_NAME+"_"+j + "_" + FieldNames.ORDER_NO);
                        if(Integer.parseInt(orderBy) >= allFields.length) {
                            continue;
                        }
                        allFields[Integer.parseInt(orderBy)] = key;
                        break;
                    }
                }
            }
        }

        return allFields;
    }


    public Field[] getSectionTablesFieldsArrayWithOrderBy(HeaderField hFld) {
        return getSectionTablesFieldsArrayWithOrderBy(hFld,false);
    }
    public Field[] getSectionTablesFieldsArrayWithOrderBy(HeaderField hFld,boolean dependentOnly) {
        Field[] allFields = null;
        ArrayList<Field> list = null;
        try {
            list = new ArrayList();
        } catch(Exception e) {

        }
        if(!dependentOnly){
            String sectionType = hFld.getSectionField();
            Iterator it = fieldMap.values().iterator();
            for (int i=0;it!=null && it.hasNext();) {
                Field field = (Field)it.next();
                int orderId = field.getOrderBy();
                if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                    if(!field.isActiveField())
                        continue;
                    if(!sectionType.equals(field.getSectionField())) {
                        continue;
                    }
                    if(orderId != -1) {
                        list.add(field);
                    }
                }
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        SequenceMap dependentChildMap = hFld.getDependentChildMap(); //BB-20150203-259 (Dynamic Response based on parent field response)

        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();
            String name1 = dTable.getTableName();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();
                Field dFld = fdpMapping.getField(fName);
                Field newdFld = (Field)dFld.clone();
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                //BB-20150203-259 (Dynamic Response based on parent field response) starts
                if(dependentChildMap != null && dependentChildMap.size() > 0) {
                    SequenceMap dependentChildTotalMap = new SequenceMap();
                    Iterator depIt = dependentChildMap.values().iterator();
                    while(depIt.hasNext()) {
                        DependentChild child = (DependentChild)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                                dependentChildTotalMap.put(child.getFieldName(), new DependentChild(child.getFieldName(), child.getFieldMap(), child.getFunctionName()));
                            }
                        }
                    }
                    newdFld.setDependentChildTotalMap(dependentChildTotalMap);
                }
                //BB-20150203-259 (Dynamic Response based on parent field response) ends
                
                //P_Enh_Sync_Fields starts
                SequenceMap syncFieldMap = hFld.getSyncFieldMap();
                if(syncFieldMap != null && syncFieldMap.size() > 0) {
                    SequenceMap syncTotalMap = new SequenceMap();
                    Iterator depIt = syncFieldMap.values().iterator();
                    while(depIt.hasNext()) {
                    	SyncWithField child = (SyncWithField)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                            	syncTotalMap.put(child.getFieldName(), new SyncWithField(child.getFieldName(), child.getTableName(), child.getColumnName(), child.getTableAnchor(), child.getParentField(), child.getDisplayName(), child.getSyncModule()));
                            }
                        }
                    }
                    newdFld.setSyncTotalMap(syncTotalMap);
                }
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH))) {
                	newdFld.setSyncWithField((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE))) {
                	newdFld.setSyncModule((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY))) {
                	newdFld.setReadOnly((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY));
                }
                
                //P_Enh_Sync_Fields ends
                
                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                    continue;
                } else {
                    newdFld.setActiveField(true);
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }

                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                newdFld.setFieldName(dTable.getTableAliasName()+fName);

                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name1);
                newdFld.setTableAnchor(name);

                newdFld.setIncludeInCampaign((String)tField.getFieldMap().get(TableXMLDAO.INCLUDE_IN_CAMPAIGN)); //BB-20150319-268(FIM Campaign Center)

                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                if(StringUtil.isValid(newdFld.getTransformMethodParam()))
                    newdFld.setTransformMethodParam(dTable.getTableAliasName()+newdFld.getTransformMethodParam());
//				System.out.println("order by 1===================="+tField.getFieldMap().get(TableXMLDAO.ORDER_BY));
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));
                String groupBy = (String)tField.getFieldMap().get(TableXMLDAO.GROUP_BY);
                newdFld.setGroupByField("true".equals(groupBy) ? true:false);
                //System.out.println("newdFld display type  ---------- " + newdFld.getDisplayTypeField());

                list.add(newdFld);
            }
        }
        if(dependentOnly){
            return list.toArray(new Field[0]);
        }
        if(list.size() > 0) {
            //System.out.println("list -- "+list);
            allFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
                if(key.getOrderBy() >= allFields.length) continue;
                //System.out.print(", Key -- "+key.getFieldName());
                //System.out.print(",key.getOrderBy() -- "+key.getOrderBy());
                allFields[key.getOrderBy()] = key;
            }
        }
        return allFields;
    }

    public Field[] getSectionTableFieldsWithOrderBy(String sectionType) {
        Field[] allFields = null;
        ArrayList list = null;
        HeaderField hFld = null;
        try {
            list = new ArrayList();
        } catch(Exception e) {

        }
        Iterator itMap = headerMap.values().iterator();
        for (int i=0;itMap!=null && itMap.hasNext();) {
            HeaderMap field = (HeaderMap)itMap.next();
            String secType = field.getHeaderFields().getSectionField();
            if(field.getHeaderFields().getSectionField().equals(sectionType)) {
                hFld = field.getHeaderFields();
                break;
            }
//			sFields[i++] = field;
        }
        if(hFld == null) {
            return null;
        }

        //String sectionType = hFld.getSectionField();
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            int orderId = field.getOrderBy();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                if(!field.isActiveField())
                    continue;
                if(!sectionType.equals(field.getSectionField())) {
                    continue;
                }
                if(orderId != -1) {
                    list.add(field);
                }
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();

                Field dFld = fdpMapping.getField(fName);

                Field newdFld = (Field)dFld.clone();
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                    continue;
                } else {
                    newdFld.setActiveField(true);
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }

                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                newdFld.setFieldName(dTable.getTableAliasName()+fName);

                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name);
                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());

                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                list.add(newdFld);
            }
        }
        if(list.size() > 0) {
            //System.out.println("list size -- "+list.size());
            allFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
				/*System.out.println("Key -- "+key.getFieldName());
				System.out.println("key.getOrderBy() -- "+key.getOrderBy());*/
                allFields[key.getOrderBy()] = key;
            }
        }

        return allFields;
    }

    public Field[] getSectionTableAllActiveDeactiveFieldsWithOrderBy(String sectionType) {
        Field[] allFields = null;
        ArrayList list = null;
        HeaderField hFld = null;
        try {
            list = new ArrayList();
        } catch(Exception e) {

        }
        Iterator itMap = headerMap.values().iterator();
        for (int i=0;itMap!=null && itMap.hasNext();) {
            HeaderMap field = (HeaderMap)itMap.next();
            String secType = field.getHeaderFields().getSectionField();
            if(field.getHeaderFields().getSectionField().equals(sectionType)) {
                hFld = field.getHeaderFields();
                break;
            }
//			sFields[i++] = field;
        }
        if(hFld == null) {
            return null;
        }

        //String sectionType = hFld.getSectionField();
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            int orderId = field.getOrderBy();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
//				if(!field.isActiveField())
//					continue;
                if(!sectionType.equals(field.getSectionField())) {
                    continue;
                }
                if(orderId != -1) {
                    list.add(field);
                }
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                if(!sectionType.equals(section)) {
                    continue;
                }
                String fName = tField.getFieldName();

                Field dFld = fdpMapping.getField(fName);

                Field newdFld = (Field)dFld.clone();
                newdFld.setSectionField(section);

                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
//					continue;
                } else {
                    newdFld.setActiveField(true);
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }

                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                newdFld.setFieldName(dTable.getTableAliasName()+fName);

                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name);
                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());

                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                list.add(newdFld);
            }
        }
        if(list.size() > 0) {
            //System.out.println("list size -- "+list.size());
            allFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
				/*System.out.println("Key -- "+key.getFieldName());
				System.out.println("key.getOrderBy() -- "+key.getOrderBy());*/
                allFields[key.getOrderBy()] = key;
            }
        }

        return allFields;
    }

    public Field[] getSectionTablesFieldsArray(HeaderField hFld) {
        Field[] allFields = null;
        ArrayList list = null;
        try {
            list = new ArrayList();
        } catch(Exception e) {

        }
        String sectionType = hFld.getSectionField();
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            int orderId = field.getOrderBy();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
//				if(!field.isActiveField())
//					continue;
                if(!sectionType.equals(field.getSectionField())) {
                    continue;
                }
                if(orderId != -1) {
                    list.add(field);
                }
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();
            String name1 = dTable.getTableName();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();

                Field dFld = fdpMapping.getField(fName);

                Field newdFld = (Field)dFld.clone();

                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                    //continue;
                } else {
                    newdFld.setActiveField(true);
                }
                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                
                if(tField.getFieldMap().get(TableXMLDAO.CENTER_INFO_DISPLAY) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.CENTER_INFO_DISPLAY))) {
                    newdFld.setCenterInfoDisplay(true);
                } else {
                    newdFld.setCenterInfoDisplay(false);
                }
                
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                
                //P_Enh_Sync_Fields starts
                SequenceMap syncFieldMap = hFld.getSyncFieldMap();
                if(syncFieldMap != null && syncFieldMap.size() > 0) {
                    SequenceMap syncTotalMap = new SequenceMap();
                    Iterator depIt = syncFieldMap.values().iterator();
                    while(depIt.hasNext()) {
                    	SyncWithField child = (SyncWithField)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                            	syncTotalMap.put(child.getFieldName(), new SyncWithField(child.getFieldName(), child.getTableName(), child.getColumnName(), child.getTableAnchor(), child.getParentField(), child.getDisplayName(), child.getSyncModule()));
                            }
                        }
                    }
                    newdFld.setSyncTotalMap(syncTotalMap);
                }
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH))) {
                	newdFld.setSyncWithField((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE))) {
                	newdFld.setSyncModule((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY))) {
                	newdFld.setReadOnly((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY));
                }
                
                //P_Enh_Sync_Fields ends

                
                
                String isBuildField = (String)tField.getFieldMap().get(TableXMLDAO.IS_BUILD_FIELD);
                //commented because in build field case, it is not necessary that yes will always come
                //In this case "" or null may also can appear.
                //if("yes".equals((String)tField.getFieldMap().get(TableXMLDAO.IS_BUILD_FIELD))) { 
                if(isBuildField == null || "".equals(isBuildField) || "yes".equals(isBuildField)) {
                    newdFld.setIsBuildField(true);
                } else {
                    newdFld.setIsBuildField(false);
                }
                
                newdFld.setFieldName(dTable.getTableAliasName()+fName);

                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name1);
                newdFld.setTableAnchor(name);

                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());

                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                list.add(newdFld);
            }
        }
        if(list.size() > 0) {
            allFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
                allFields[key.getOrderBy()] = key;
                i++;
            }
        }

        return allFields;
    }

    public Field[] getAllFieldsArrayWithOrderBy(String sectionType) {
        try {
            Field[] allFields = new Field[allCount];
            boolean flag = false;
            Iterator it = fieldMap.values().iterator();
            int count = 0;
            for (int i=0;it!=null && it.hasNext();) {
                Field field = (Field)it.next();
                int orderId = field.getOrderBy();

                if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                    if(!field.isActiveField())
                        continue;
                    if(!sectionType.equals(field.getSectionField())) {
                        continue;
                    }
                    if(orderId != -1) {
                        allFields[orderId] = field;
                        count++;
                    }
                }
            }
            if(count == 0) {
                it = fieldMap.values().iterator();
                for (int i=0;it!=null && it.hasNext();) {
                    Field field = (Field)it.next();
                    if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                        allFields[i++] = field;
                    }
                }
            }
            return allFields;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Field[] getAllFieldsArraySansID()
    {

        Field[] allFields = new Field[allCount-1];
        Iterator it = fieldMap.values().iterator();
        for (int i=0 ;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if ( (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY ||
                    field.getFieldType() == Field.FieldType.ALL_ONLY )&&
                    !isIdField(field.getFieldName()) )
            {
                allFields[i++] = field;
            }
        }
        return allFields;
    }

    public boolean isIdField(String fieldName)
    {
        for (int i=0; i<idField.length; i++)
        {
            if (fieldName.equals(idField[i]))
            {
                return true;
            }
        }
        return false;
    }


    public Field[] getSummaryFieldsArray()
    {
        Field[] summaryFields = new Field[summaryCount];
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY)
            {
                summaryFields[i++] = field;
            }
        }
        return summaryFields;
    }

    public Field[] getSummaryFieldsArrayWithAddressDependent(HeaderField hFld)//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT
    {
        Field[] summaryFields = null;
        ArrayList<Field> list = null;
        try {
            list = new ArrayList();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY)
            {
                list.add(field);
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        SequenceMap dependentChildMap = hFld.getDependentChildMap(); //BB-20150203-259 (Dynamic Response based on parent field response)
        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();
            if(!"address".equals(name)||!StringUtil.isValidNew(name))
                continue;
            String name1 = dTable.getTableName();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();
                Field dFld = fdpMapping.getField(fName);
                Field newdFld = (Field)dFld.clone();
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                //BB-20150203-259 (Dynamic Response based on parent field response) starts
                if(dependentChildMap != null && dependentChildMap.size() > 0) {
                    SequenceMap dependentChildTotalMap = new SequenceMap();
                    Iterator depIt = dependentChildMap.values().iterator();
                    while(depIt.hasNext()) {
                        DependentChild child = (DependentChild)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                                dependentChildTotalMap.put(child.getFieldName(), new DependentChild(child.getFieldName(), child.getFieldMap(), child.getFunctionName()));
                            }
                        }
                    }
                    newdFld.setDependentChildTotalMap(dependentChildTotalMap);
                }
                //BB-20150203-259 (Dynamic Response based on parent field response) ends
                
                //P_Enh_Sync_Fields starts
                SequenceMap syncFieldMap = hFld.getSyncFieldMap();
                if(syncFieldMap != null && syncFieldMap.size() > 0) {
                    SequenceMap syncTotalMap = new SequenceMap();
                    Iterator depIt = syncFieldMap.values().iterator();
                    while(depIt.hasNext()) {
                    	SyncWithField child = (SyncWithField)depIt.next();
                        if(child != null) {
                            if(child.getParentField().equals(dTable.getTableAliasName()+fName)) {
                            	syncTotalMap.put(child.getFieldName(), new SyncWithField(child.getFieldName(), child.getTableName(), child.getColumnName(), child.getTableAnchor(), child.getParentField(), child.getDisplayName(), child.getSyncModule()));
                            }
                        }
                    }
                    newdFld.setSyncTotalMap(syncTotalMap);
                }
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH))) {
                	newdFld.setSyncWithField((String)tField.getFieldMap().get(TableXMLDAO.SYNC_WITH));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE))) {
                	newdFld.setSyncModule((String)tField.getFieldMap().get(TableXMLDAO.SYNC_MODULE));
                }
                
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY))) {
                	newdFld.setReadOnly((String)tField.getFieldMap().get(TableXMLDAO.READ_ONLY));
                }
                //P_Enh_Sync_Fields ends
                
                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                    continue;
                } else {
                    newdFld.setActiveField(true);
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                String tabName = (String)tField.getFieldMap().get(TableXMLDAO.TAB_NAME);
                if(tabName == null || "".equals(tabName)) {
                    newdFld.setTabName(null);
                } else {
                    newdFld.setTabName(tabName);
                }

                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                if(tField.getFieldMap().get(TableXMLDAO.PII_ENABLED) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.PII_ENABLED))) {
                    newdFld.setPiiEnabled(true);
                } else {
                    newdFld.setPiiEnabled(false);
                }
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                newdFld.setFieldName(fName);
                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name1);
                newdFld.setTableAnchor(name);

                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                if(StringUtil.isValid(newdFld.getTransformMethodParam()))
                    newdFld.setTransformMethodParam(dTable.getTableAliasName()+newdFld.getTransformMethodParam());
                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));
                String groupBy = (String)tField.getFieldMap().get(TableXMLDAO.GROUP_BY);
                newdFld.setGroupByField("true".equals(groupBy) ? true:false);
                list.add(newdFld);
            }
        }
        if(list.size() > 0) {
            summaryFields = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Field key = (Field)itr.next();
                summaryFields[i++] = key;
            }
        }
        return summaryFields;
    }
    public Field[] getSummaryValidFieldsArray()
    {
        Field[] summaryFields = new Field[summaryCount];
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY)
            {
                summaryFields[i++] = field;
            }
        }

        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < summaryFields.length; i++) {
            if(summaryFields[i].fieldAllowedInMain()) {
                arrLst.add(summaryFields[i]);
            }
        }
        Field[] allValidFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++) {
            allValidFields[i] = (Field)arrLst.get(i);
        }

        return allValidFields;
    }

    public String getTableName()
    {
        return tableName;
    }

    public String getConnectionName()
    {
        return connectionName;
    }

    public String[] getAllDbFieldsSansID()
    {

        Field[] allFields = getAllFieldsArraySansID();
        String[] allDbFields = new String[allFields.length];
        for (int i=0 ;i<allFields.length;i++)
        {
            allDbFields[i] = allFields[i].getDbField();
        }
        return allDbFields;
    }

    /**
     * Allow only valid DB Sans Field when xml file contains Same DB Field for different Field Name which used for different purpose like Insert / Export etc.
     * Where Export type Field do not required when inserting data then skip Field in Insert case
     * @return
     */
    public String[] getAllValidDbFieldsSansID() {
        Field[] allFields = getAllFieldsArraySansID();
        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < allFields.length; i++) {
            if(allFields[i].fieldAllowedInMain()) {
                arrLst.add(allFields[i]);
            }
        }

        String[] allDbFields = new String[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++) {
            Field fld = (Field)arrLst.get(i);
            allDbFields[i] = fld.getDbField();
//			allDbFields[i] = allFields[i].getDbField();
        }
        return allDbFields;
    }

    public String[] getAllDbFields()
    {

        Field [] allFields = getAllFieldsArray();
        String[] allDbFields = new String[allFields.length];
        for (int i=0 ;i<allFields.length;i++)
        {
            allDbFields[i] = allFields[i].getDbField();
        }
        return allDbFields;
    }
    /**
     * Allow only valid DB Field when xml file contains Same DB Field for different Field Name which used for different purpose like Insert / Export etc.
     * Where Export type Field do not required when inserting data then skip Field in Insert case
     * @return
     */
    public String[] getAllValidDbFields()
    {

        Field [] allFields = getAllFieldsArray();
        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < allFields.length; i++) {
            if(allFields[i].fieldAllowedInMain()) {
                arrLst.add(allFields[i]);
            }
        }

        String[] allDbFields = new String[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            allDbFields[i] = fld.getDbField();
        }
        return allDbFields;
    }

    public String[] getSummaryDbFields()
    {
        Field [] summaryFields = getSummaryFieldsArray();
        String[] summaryDbFields = new String[summaryFields.length];
        for (int i=0 ;i<summaryFields.length;i++)
        {
            summaryDbFields[i] = summaryFields[i].getDbField();
        }
        return summaryDbFields;
    }

    public String[] getValidSummaryDbFields()
    {
        Field [] summaryFields = getSummaryFieldsArray();
        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < summaryFields.length; i++) {
            if(summaryFields[i].fieldAllowedInMain()) {
                arrLst.add(summaryFields[i]);
            }
        }

        String[] summaryDbFields = new String[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryDbFields[i] = fld.getDbField();
        }
        return summaryDbFields;
    }

    public String[] getAllFieldNames()
    {
        Field [] allFields = getAllFieldsArray();
        String[] allFieldNames = new String[allFields.length];
        for (int i=0 ;i<allFields.length;i++)
        {
            allFieldNames[i] = allFields[i].getFieldName();
        }
        return allFieldNames;
    }

    public String[] getAllFieldNamesWithOrderBy() {
        Field [] allFields = getAllFieldsArrayWithOrderBy();
        String[] allFieldNames = new String[allFields.length];
        for (int i=0 ;i<allFields.length;i++) {
            if(allFields[i] == null) {
                continue;
            }
            allFieldNames[i] = allFields[i].getFieldName();
        }
        return allFieldNames;
    }

    public String[] getAllFieldNamesWithValidation()
    {

        if(validationCount == 0) {
            return null;
        } else {

            Field [] allFields					= getAllFieldsArray();
            String[] allValidationFieldNames	= new String[validationCount];
            int j = 0;
            String validationType				= null;
            for (int i=0 ;i<allFields.length;i++)
            {
                if(allFields[i] != null)
                {
                    validationType	 = allFields[i].getValidationType();
                    if(validationType != null) {
                        allValidationFieldNames[j]	= allFields[i].getFieldName();
                        j++;
                    }
                }
            }
            return allValidationFieldNames;
        }

    }

    public String[] getSummaryFieldNames()
    {
        Field [] summaryFields = getSummaryFieldsArray();
        String[] summaryFieldNames = new String[summaryFields.length];
        for (int i=0 ;i<summaryFields.length;i++)
        {
            summaryFieldNames[i] = summaryFields[i].getFieldName();
        }
        return summaryFieldNames;
    }

    public String[] getValidSummaryFieldNames()
    {
        Field [] summaryFields = getSummaryFieldsArray();
        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < summaryFields.length; i++) {
            if(summaryFields[i].fieldAllowedInMain()) {
                arrLst.add(summaryFields[i]);
            }
        }

        String[] summaryFieldNames = new String[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryFieldNames[i] = fld.getFieldName();
        }

//		String[] summaryFieldNames = new String[summaryFields.length];
//		for (int i=0 ;i<summaryFields.length;i++)
//		{
//			summaryFieldNames[i] = summaryFields[i].getFieldName();
//		}
        return summaryFieldNames;
    }

    public SequenceMap getForeignTablesMap(){
        return foreignTablesMap;
    }
    public SequenceMap getForeignTablesExportMap(){
        return foreignTablesExportMap;
    }

    public String getTableDisplayName(){
        if(isEntityExport() && StringUtil.isValid(getEntityTableDisplayName())) {
            return getEntityTableDisplayName();
        }
        return msTableDisplayName;
    }
    //P_B_CM_38261 : starts
    public String getIsBuildTable(){
        return isBuildTable;
    }
    //P_B_CM_38261 : ends
    public String getTableExportDisplayName(){
        return msTableExportDisplayName;
    }
    //P_B_FIM_75246 added by neeti starts
    public String getTableInternalName(){
        return msTableInternalName;
    }
    //P_B_FIM_75246 added by neeti ends
    public ForeignTable getForeignTable(String psName){
        if(foreignTablesMap.containsKey(psName)){
            return (ForeignTable)foreignTablesMap.get(psName);
        }else{
            return null;
        }
    }

    public Trigger getRecordTrigger(){
        return mRecordTrigger;
    }
    public void setRecordTrigger(Trigger pTrigger){
        mRecordTrigger	= pTrigger;
    }

    public String getSequenceFldName() {
        return msSeqFldName;
    }
    public String[] getMapFieldNamesWithOrderBy(String sectionType) {
        System.out.print("sectionType " + sectionType);
        Field [] allFields = getAllFieldsArrayWithOrderBy(sectionType);
        String[] allFieldNames = null;
        ArrayList list = null;
        try {
            list = new ArrayList<String>(0);
            for (int i=0 ;i<allFields.length;i++) {
                if(allFields[i] == null) {
                    continue;
                }
                if(!StringUtil.isValidNew(allFields[i].getSectionField())) {
                    continue;
                }
                if(!allFields[i].isActiveField()) {
                    continue;
                }
                if(sectionType.equals(allFields[i].getSectionField())) {
                    list.add(allFields[i].getFieldName());
                }
            }
            if(list.size() > 0) {
                allFieldNames = new String[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    String key = (String)str.next();
                    allFieldNames[i] = key;
                    i++;
                }
            }
            return allFieldNames;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //list = null;
            //allFields = null;
            //allFieldNames = null;
        }

    }

    public String[] getDeactiveFieldNamesMap(String sectionType) {
        Field [] allFields = getAllFieldsArray();
        String[] allFieldNames = null;
        ArrayList list = null;
        try {
            list = new ArrayList<String>(0);
            for (int i=0 ;i<allFields.length;i++) {
                if(allFields[i] == null) {
                    continue;
                }
                if(!StringUtil.isValidNew(allFields[i].getSectionField())) {
                    continue;
                }
                if(allFields[i].isActiveField()) {
                    continue;
                }
                if(sectionType.equals(allFields[i].getSectionField())) {
                    list.add(allFields[i].getFieldName());
                }
            }
            if(list.size() > 0) {
                allFieldNames = new String[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    String key = (String)str.next();
                    allFieldNames[i] = key;
                    i++;
                }
            }
            return allFieldNames;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //list = null;
            //allFields = null;
            //allFieldNames = null;
        }

    }

    public Field[] getDeactiveFieldMap(String sectionType) {
        Field[] allFields = null;
//		ArrayList list = null;
        HashMap hMap = null;
        HeaderField hFld = null;
        try {
//			list = new ArrayList();
            hMap = new HashMap();
        } catch(Exception e) {

        }
        Iterator itMap = headerMap.values().iterator();
        for (int i=0;itMap!=null && itMap.hasNext();) {
            HeaderMap field = (HeaderMap)itMap.next();
            String secType = field.getHeaderFields().getSectionField();
            if(sectionType.equals(secType)) {
                hFld = field.getHeaderFields();
                break;
            }
//			sFields[i++] = field;
        }
        if(hFld == null) {
            return null;
        }

        //String sectionType = hFld.getSectionField();
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                if(field == null)
                    continue;
                if(field.isActiveField())
                    continue;
                if(!sectionType.equals(field.getSectionField())) {
                    continue;
                }
                hMap.put(field.getOrderBy(), field);
//				list.add(field);
            }
        }
        DependentTable[] dTables = hFld.getDependentTables();
        for(DependentTable dTable:dTables) {
            String name = dTable.getTableAnchor();

            FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
            TableField[] tFields = dTable.getTableFields();
            for(TableField tField :tFields) {
                String fName = tField.getFieldName();

                Field dFld = fdpMapping.getField(fName);

                Field newdFld = (Field)dFld.clone();
                String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                newdFld.setSectionField(section);

                String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                if(str != null && "no".equals(str)) {
                    newdFld.setActiveField(false);
                } else {
                    newdFld.setActiveField(true);
                }
                if(newdFld.isActiveField()) {
                    continue;
                }
                String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                    newdFld.setMandatoryField(false);
                } else {
                    newdFld.setMandatoryField(true);
                }
                if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                    newdFld.setColspan("2");
                }
                newdFld.setFieldName(dTable.getTableAliasName()+fName);
                //P_CM_Enh_BuilderForm starts
                if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                    newdFld.setSourceField(true);
                } else {
                    newdFld.setSourceField(false);
                }
                //P_CM_Enh_BuilderForm ends
                newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                newdFld.setFieldOfOtherTable(true);
                newdFld.setTableName(name);
                if(StringUtil.isValid(newdFld.getDependentField()))
                    newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                if(StringUtil.isValid(newdFld.getComboMethodParam()))
                    newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());

                if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                    newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                hMap.put(newdFld.getOrderBy(), newdFld);
//				list.add(newdFld);
            }
        }
        if(hMap.size() > 0) {
            ArrayList keys = new ArrayList();
            keys.addAll(hMap.keySet());
            Collections.sort(keys);
            Iterator itr = keys.iterator();
            allFields = new Field[hMap.size()];
            int i = 0;
            while(itr.hasNext()) {
                Integer key = (Integer)itr.next();
                Field key1 = (Field)hMap.get(key);
                allFields[i++] = key1;
            }
        }
        return allFields;
    }

    public String[] getAllMapFieldNamesWithOrderBy(String sectionType) {
        Field [] allFields = getAllFieldsArray();
        String[] allFieldNames = null;
        ArrayList list = null;
        try {
            list = new ArrayList<String>(0);
            for (int i=0 ;i<allFields.length;i++) {
                if(allFields[i] == null) {
                    continue;
                }
                if(!StringUtil.isValidNew(allFields[i].getSectionField())) {
                    continue;
                }
                if(sectionType.equals(allFields[i].getSectionField())) {
                    list.add(allFields[i].getFieldName());
                }
            }
            if(list.size() > 0) {
                allFieldNames = new String[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    String key = (String)str.next();
                    allFieldNames[i] = key;
                    i++;
                }
            }
            return allFieldNames;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //list = null;
            //allFields = null;
            //allFieldNames = null;
        }

    }

    /**
     * @PW_FORM_VERSION
     * This method will return Header Map for given version
     */
    public HeaderMap[] getHeaderMap() {
        return getHeaderMap(null);
    }

    public HeaderMap[] getHeaderMap(String formVersion) {
        HeaderMap[] sFields = new HeaderMap[headerMap.size()];
        Iterator it = headerMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            HeaderMap field = (HeaderMap)it.next();
            //PW_FORM_VERSION Starts
            if(StringUtil.isValid(formVersion) && !field.getHeaderFields().isValidVersion(formVersion)){
                continue;
            }
            //PW_FORM_VERSION Ends
            sFields[i++] = field;
        }
        return sFields;
    }

    public String[] getHeaderNames() {
        String[] sFields = new String[headerMap.size()];
        Iterator it = headerMap.keys().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            String field = (String)it.next();
            sFields[i++] = field;
        }
        return sFields;
    }

    public HeaderMap getHeaderNameMap(String name) {
        HeaderMap sFields = null;
        Iterator it = headerMap.keys().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            String field = (String)it.next();
            if(name.equals(field)) {
                sFields = (HeaderMap)headerMap.get(field);
            }
        }
        return sFields;
    }

    public String[] getHeaderFieldsNames(String name) {
        String[] strNames = null;
        Iterator it = headerMap.keys().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            String field = (String)it.next();
            if(name.equals(field)) {
                HeaderMap hFld = (HeaderMap)headerMap.get(field);
                strNames = getMapFieldNamesWithOrderBy(hFld.getHeaderFields().getSectionField());
                break;
            }
        }
        return strNames;
    }

    public String[] getHeaderSectionAllTablesFieldsNames(String name) {
        String[] strNames = null;
        Iterator it = headerMap.keys().iterator();
        for (int i=0;it!=null && it.hasNext();) {
            String field = (String)it.next();
            if(name.equals(field)) {
                HeaderMap hFld = (HeaderMap)headerMap.get(field);
                //strNames = getMapFieldNamesWithOrderBy(hFld.getHeaderFields().getSectionField());
                break;
            }
        }
        return strNames;
    }

    public Field[] getAllSectionsFields() {
        Field [] allFields = getAllFieldsArray();
        Field [] allSecFields = null;
        ArrayList list = null;
        try {
            list = new ArrayList<Field>(0);
            for (int i=0 ;i<allFields.length;i++) {
                if(allFields[i] == null) {
                    continue;
                }
                if(!StringUtil.isValidNew(allFields[i].getSectionField())) {
                    continue;
                }
                list.add(allFields[i]);
            }
            Iterator itMap = headerMap.values().iterator();
            for (int i=0;itMap!=null && itMap.hasNext();) {
                HeaderMap field = (HeaderMap)itMap.next();
                String secType = field.getHeaderFields().getSectionField();
              //P_Enh_FormBuilder_Tabular_Section starts
                Boolean isTabularSection=field.getHeaderFields().isTabularSection();
                if(isTabularSection){
                	FieldMappings tabularSectionMappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(field.getHeaderFields().getTabularSectionTableAnchor());
                	Field[] fieldArr = tabularSectionMappings.getSectionTablesFieldsArrayWithOrderBy(field.getHeaderFields());
                	for(Field fld : fieldArr) {
                		list.add(fld);
                	}
                }
              //P_Enh_FormBuilder_Tabular_Section ends
                DependentTable[] dTables = field.getHeaderFields().getDependentTables();
                for(DependentTable dTable:dTables) {
                    String name = dTable.getTableAnchor();

                    FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
                    TableField[] tFields = dTable.getTableFields();
                    for(TableField tField :tFields) {
                        String fName = tField.getFieldName();

                        Field dFld = fdpMapping.getField(fName);

                        Field newdFld = (Field)dFld.clone();
                        String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                        newdFld.setSectionField(section);

                        String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                        if(str != null && "no".equals(str)) {
                            newdFld.setActiveField(false);
                        } else {
                            newdFld.setActiveField(true);
                        }

                        String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                        if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                            newdFld.setMandatoryField(false);
                        } else {
                            newdFld.setMandatoryField(true);
                        }
                        if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                            newdFld.setColspan("2");
                        }
                        //P_CM_Enh_BuilderForm starts
                        if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                            newdFld.setSourceField(true);
                        } else {
                            newdFld.setSourceField(false);
                        }
                        //P_CM_Enh_BuilderForm ends
                        newdFld.setFieldName(dTable.getTableAliasName()+fName);

                        newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                        newdFld.setFieldOfOtherTable(true);
                        newdFld.setTableName(name);
                        if(StringUtil.isValid(newdFld.getDependentField()))
                            newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                        if(StringUtil.isValid(newdFld.getComboMethodParam()))
                            newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());

                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                            newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));

                        list.add(newdFld);
                    }
                }
            }

            if(list.size() > 0) {
                allSecFields = new Field[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    allSecFields[i] = (Field)str.next();
                    i++;
                }
            }
            return allSecFields;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public Field getFormField(String fieldName) {
        Field [] allFields = getAllFieldsArray();
        Field [] allSecFields = null;
        try {
            for (int i=0 ;i<allFields.length;i++) {
                if(allFields[i] == null) {
                    continue;
                }
                if(!StringUtil.isValidNew(allFields[i].getSectionField())) {
                    continue;
                }
                if(allFields[i].getFieldName().equals(fieldName)) {
                    return allFields[i];
                }
            }
            Iterator itMap = headerMap.values().iterator();
            for (int i=0;itMap!=null && itMap.hasNext();) {
                HeaderMap field = (HeaderMap)itMap.next();
                String secType = field.getHeaderFields().getSectionField();

                DependentTable[] dTables = field.getHeaderFields().getDependentTables();
                for(DependentTable dTable:dTables) {
                    String name = dTable.getTableAnchor();
                    FieldMappings fdpMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(name);
                    TableField[] tFields = dTable.getTableFields();
                    for(TableField tField :tFields) {
                        String fName = tField.getFieldName();
                        Field dFld = fdpMapping.getField(fName);
                        Field newdFld = (Field)dFld.clone();
                        String section = (String)tField.getFieldMap().get(TableXMLDAO.SECTION);
                        newdFld.setSectionField(section);
                        String str = (String)tField.getFieldMap().get(TableXMLDAO.IS_ACTIVE);
                        if(str != null && "no".equals(str)) {
                            newdFld.setActiveField(false);
                        } else {
                            newdFld.setActiveField(true);
                        }
                        String str1 = (String)tField.getFieldMap().get(TableXMLDAO.IS_MANDATORY);
                        if(str1 == null || "".equals(str1) || "false".equals(str1)) {
                            newdFld.setMandatoryField(false);
                        } else {
                            newdFld.setMandatoryField(true);
                        }
                        //P_CM_Enh_BuilderForm starts
                        if(tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD) != null && "true".equals((String)tField.getFieldMap().get(TableXMLDAO.SOURCE_FIELD))) {
                            newdFld.setSourceField(true);
                        } else {
                            newdFld.setSourceField(false);
                        }
                        //P_CM_Enh_BuilderForm ends
                        if(tField.getFieldMap().get(TableXMLDAO.COLSPAN) != null && "2".equals((String)tField.getFieldMap().get(TableXMLDAO.COLSPAN))) {
                            newdFld.setColspan("2");
                        }
                        newdFld.setFieldName(dTable.getTableAliasName()+fName);
                        newdFld.setDisplayName((String)tField.getFieldMap().get(TableXMLDAO.DISPLAY_NAME));
                        newdFld.setFieldOfOtherTable(true);
                        newdFld.setTableName(name);
                        if(StringUtil.isValid(newdFld.getDependentField()))
                            newdFld.setDependentField(dTable.getTableAliasName()+newdFld.getDependentField());
                        if(StringUtil.isValid(newdFld.getComboMethodParam()))
                            newdFld.setComboMethodParam(dTable.getTableAliasName()+newdFld.getComboMethodParam());
                        if(StringUtil.isValidNew((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)))
                            newdFld.setOrderBy(Integer.parseInt((String)tField.getFieldMap().get(TableXMLDAO.ORDER_BY)));
                        if(newdFld.getFieldName().equals(fieldName)) {
                            return newdFld;
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Field[] getAllMargableFields() {
        Field [] allFields = getAllFieldsArray();
        Field [] allMergFields = null;
        ArrayList list = null;
        try {
            list = new ArrayList<Field>(0);
            for (int i=0 ;i<allFields.length;i++) {
				/*if(allFields[i] == null) {
					continue;
				}*/
                if(allFields[i] == null || !allFields[i].isActiveField() || !StringUtil.isValid(allFields[i].getMailMergeKeyword())) {
                    continue;
                }
                list.add(allFields[i]);
            }
            if(list.size() > 0) {
                allMergFields = new Field[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    allMergFields[i] = (Field)str.next();
                    i++;
                }
            }
            return allMergFields;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }
    //ENH_MODULE_CUSTOM_TABS starts
    public boolean activeFieldExist()
    {
        boolean exist			= false;//set continue as true initially for every tab

        Field allSectionField[]		=	this.getAllSectionsFields();

        if(allSectionField==null || allSectionField.length <= 0)
        {
            return false;
        }

        forlabel:
        for(Field fld : allSectionField)
        {
            if(fld.isActiveField())
            {
                exist	= true;
                break forlabel;
            }
        }

        return exist;
    }
    //ENH_MODULE_CUSTOM_TABS ends
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     */
    public Field[] getQuestionFields(String questionName) {
        Field [] allFields = getAllFieldsArray();
        Field [] allSecFields = null;
        ArrayList list = null;
        try {
            list = new ArrayList<Field>(0);
            for(int i=0; i < allFields.length; i++) {
                if(allFields[i] != null){
                    String qName = allFields[i].getQuestionName();
                    if(StringUtil.isValid(qName) && qName.equals(questionName)) {
                        list.add(allFields[i]);
                    }
                }
            }

            if(list.size() > 0) {
                allSecFields = new Field[list.size()];
                int i = 0;
                Iterator str = list.iterator();
                while(str.hasNext()) {
                    allSecFields[i] = (Field)str.next();
                    i++;
                }
            }
            return allSecFields;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Question object of given question name(ID)
     */
    public Question getQuestion(String quesName) {
        Question ques = null;
        HeaderMap[] hMaps = getHeaderMap();
        for(HeaderMap headerMap:hMaps) {
            HeaderField hField = headerMap.getHeaderFields();
            Question[] questions = hField.getQuestions();
            for(int i=0; i < questions.length; i++) {
                if(questions[i].getQuestionName().equals(quesName)) {
                    ques = questions[i];
                    break;
                }
            }
        }
        return ques;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Non Header Question object of given question name(ID)
     */
    public Question getNonHeaderQuestion(String quesName) {
        Question ques = null;
        Question[] questions = getAllNonHeaderQuestions();
        for(int i=0; i < questions.length; i++) {
            if(questions[i].getQuestionName().equals(quesName)) {
                ques = questions[i];
                break;
            }
        }
        return ques;
    }
    /**
     * @PW_FORM_VERSION
     * This will return All Question objects array of Form for given version
     */
    public Question[] getAllQuestions() {
        return getAllQuestions(null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return All Question objects array of Form
     */
    public Question[] getAllQuestions(String formVersion) {
        Question[] ques = new Question[0];
        ArrayList list = new ArrayList<Question>(0);
        try {
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                //PW_FORM_VERSION Starts
                if(StringUtil.isValid(formVersion) && !hField.isValidVersion(formVersion)){
                    continue;
                }
                //PW_FORM_VERSION Ends
                Question[] questions = hField.getQuestions();
                for(int i=0; i < questions.length; i++) {
                    //PW_FORM_VERSION Starts
                    if(StringUtil.isValid(formVersion) && !questions[i].isValidVersion(formVersion)){
                        continue;
                    }
                    //PW_FORM_VERSION Ends
                    list.add(questions[i]);
                }
            }
        } catch(Exception e) {
        }

        if(list.size() > 0) {
            ques = new Question[list.size()];
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                ques[i] = (Question)str.next();
                i++;
            }
        }
        return ques;
    }

    /**
     * @PW_FORM_VERSION
     * This will return Ordered Question(s) of respective Section for given version
     */
    public Question[] getAllNonHeaderQuestions() {
        return getAllNonHeaderQuestions(null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * @return
     */
    public Question[] getAllNonHeaderQuestions(String formVersion) {
        Question[] ques = new Question[0];
        ArrayList list = new ArrayList<Question>(0);
        if(questionExtMap == null){
            return ques;
        }
        Iterator it = questionExtMap.keys().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            //PW_FORM_VERSION Starts
            if(StringUtil.isValid(formVersion) && !((Question)questionExtMap.get(key)).isValidVersion(formVersion)){
                continue;
            }
            //PW_FORM_VERSION Ends
            list.add((Question)questionExtMap.get(key));
        }

        if(list.size() > 0) {
            ques = new Question[list.size()];
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                ques[i] = (Question)str.next();
                i++;
            }
        }
        return ques;
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return All Question Names array of Form
     */
    public String[] getAllQuestionNames() {
        String[] quesIds = new String[0];
        ArrayList list = new ArrayList<String>(0);
        /**
         * Get all non header questions ids
         */
        quesIds = getAllNonHeaderQuestionNames();
        for(int i=0; i < quesIds.length; i++) {
            list.add(quesIds[i]);
        }

        try {
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                Question[] questions = hField.getQuestions();
                for(int i=0; i < questions.length; i++) {
                    list.add(questions[i].getQuestionName());
                }
            }
        } catch(Exception e) {
        }

        if(list.size() > 0) {
            quesIds = new String[list.size()];
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                quesIds[i] = (String)str.next();
                i++;
            }
        }
        return quesIds;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return All Non header Question Names array of Form
     * @return
     */
    public String[] getAllNonHeaderQuestionNames() {
        String[] quesNames = new String[0];
        ArrayList list = new ArrayList<Question>(0);
        if(questionExtMap == null){
            return quesNames;
        }
        Iterator it = questionExtMap.keys().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            list.add((Question)questionExtMap.get(key));
        }

        if(list.size() > 0) {
            quesNames = new String[list.size()];
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                Question ques = (Question)str.next();
                quesNames[i] = ques.getQuestionName();
                i++;
            }
        }
        return quesNames;
    }


    public Question[] getOrderedHeaderQuestions(String sectionType) {
        return getOrderedHeaderQuestions(sectionType,null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Ordered Question(s) of respective Section
     */
    public Question[] getOrderedHeaderQuestions(String sectionType, String formVersion) {
        Question[] ques = new Question[0];
        Map treeMap;
        try {
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                if(hField.getSectionField().equals(sectionType)) {
                    treeMap = new TreeMap();
                    Question[] questions = hField.getQuestions();
                    for(int i=0; i < questions.length; i++) {
                        //PW_FORM_VERSION Starts
                        if(StringUtil.isValid(formVersion) && !questions[i].isValidVersion(formVersion)){
                            continue;
                        }
                        //PW_FORM_VERSION Ends
                        treeMap.put(Integer.parseInt(questions[i].getKeyValue(TableXMLDAO.ORDER_BY)), questions[i]);
                    }
                    ques = new Question[treeMap.size()];

                    Iterator it = treeMap.keySet().iterator();
                    int i = 0;
                    while(it != null && it.hasNext()){
                        Object key = it.next();
                        ques[i] = (Question)treeMap.get(key);
                        i++;
                    }

                    break;
                }
            }
        } catch(Exception e) {
        }
        return ques;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return all question ids with ordered values as in Map
     * @return
     */
    public SequenceMap getOrderedNonHeaderQuestions() {
        SequenceMap listMap = null;
        Map treeMap;
        if(questionExtMap == null){
            return null;
        }
        try {
            if(questionExtMap.size() > 0) {
                listMap = new SequenceMap();
                Iterator it = questionExtMap.keys().iterator();
                treeMap = new TreeMap();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    treeMap.put(Integer.parseInt(question.getKeyValue(TableXMLDAO.ORDER_BY)), question.getQuestionName());
                }
                Iterator itkey = treeMap.keySet().iterator();
                int i = 0;
                while(itkey != null && itkey.hasNext()){
                    Object key1 = it.next();
                    String value = (String)treeMap.get(key1);
                    listMap.put(value, i);
                    i++;
                }
            }
        } catch(Exception e) {
        }
        return listMap;
    }

    /**
     * @PW_FORM_VERSION
     * This will return all non header questions of given version
     */
    public Question[] getNonHeaderOrderedQuestions() {
        return getNonHeaderOrderedQuestions(null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return all non header questions
     * @return
     */
    public Question[] getNonHeaderOrderedQuestions(String formVersion) {
        Question[] listMap = new Question[0];
        Map treeMap = null;
        if(questionExtMap == null){
            return listMap;
        }
        try {
            if(questionExtMap.size() > 0) {
                Iterator it = questionExtMap.keys().iterator();
                treeMap = new TreeMap();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    //PW_FORM_VERSION Starts
                    if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                        continue;
                    }
                    //PW_FORM_VERSION Endss
                    treeMap.put(Integer.parseInt(question.getKeyValue(TableXMLDAO.ORDER_BY)), question);
                }

                listMap = new Question[treeMap.size()];

                Iterator itkey = treeMap.keySet().iterator();
                int i = 0;
                while(itkey != null && itkey.hasNext()){
                    Object key1 = itkey.next();
                    listMap[i] = (Question)treeMap.get(key1);
                    i++;
                }
            }
        } catch(Exception e) {
        }
        return listMap;
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return all options of selected question
     * @param quesName
     * @return
     */
    public QuestionOption[] getQuestionOptions(String quesName) {
        QuestionOption[] ques = new QuestionOption[0];
        try {
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                Question[] questions = hField.getQuestions();
                for(int i=0; i < questions.length; i++) {
                    if(questions[i].getQuestionName().equals(quesName)) {
                        ques = questions[i].getQuestionOptions();
                        break;
                    }
                }
            }
        } catch(Exception e) {
        }
        return ques;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Return help notes of Question if exist
     * @param quesName
     * @return
     */
    public String getQuestionHelpNotes(String quesName) {
        String helpNotes = "";
        HeaderMap[] hMaps = getHeaderMap();
        for(HeaderMap headerMap:hMaps) {
            HeaderField hField = headerMap.getHeaderFields();
            Question[] questions = hField.getQuestions();
            for(int i=0; i < questions.length; i++) {
                if(questions[i].getQuestionName().equals(quesName)) {
                    helpNotes = questions[i].getHelpNotes();
                    break;
                }
            }
        }
        return helpNotes;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Return help notes of Non Header Question if exist
     * @param quesName
     * @return
     */
    public String getNonHeaderQuestionHelpNotes(String quesName) {
        String helpNotes = "";
        Question[] questions = getAllNonHeaderQuestions();
        for(int i=0; i < questions.length; i++) {
            if(questions[i].getQuestionName().equals(quesName)) {
                helpNotes = questions[i].getHelpNotes();
                break;
            }
        }
        return helpNotes;
    }

    public void setTotalActionItemCount(int totalActionItemCount) {
        this.totalActionItemCount = totalActionItemCount;
    }

    public int getTotalActionItemCount() {
        return this.totalActionItemCount;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Form Meta Data object in which all Header and Tab informtaion exist of Related Form  
     * @return
     */
    public FormMetaData getFormMetaData() {
        if(metaDataMap != null && metaDataMap.size() > 0) {
            Iterator it = metaDataMap.values().iterator();
            if(it.hasNext()) {
                try {
                    Object obj = it.next();
                    if(obj instanceof FormMetaData) {
                        return (FormMetaData)obj;
                    }
                } catch(Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Tab's All Question objects array of Form headers
     */
    public Question[] getTabAllHeadersQuestions(String tabName) {
        Question[] ques = new Question[0];
        ArrayList list = new ArrayList<Question>(0);

        HeaderMap[] hMaps = getHeaderMap();
        try {
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                if(StringUtil.isValid(tabName) && StringUtil.isValid(hField.getTabName()) && tabName.equals(hField.getTabName())) {
                    Question[] questions = hField.getQuestions();
                    for(int i=0; i < questions.length; i++) {
                        list.add(questions[i]);
                    }
                }
            }
        } catch(Exception e) {
            return ques;
        }

        if(list.size() > 0) {
            ques = new Question[list.size()];
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                ques[i] = (Question)str.next();
                i++;
            }
        }
        return ques;
    }

    /**
     * @PW_FORM_VERSION
     * This will return Tab's all non header questions of given version
     */
    public Question[] getTabNonHeaderOrderedQuestions(String tabName) {
        return getTabNonHeaderOrderedQuestions(tabName,null);
    }
    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return Tab's all non header questions
     * @return
     */
    public Question[] getTabNonHeaderOrderedQuestions(String tabName, String formVersion) {
        Question[] listMap = new Question[0];
        Map treeMap;
        if(questionExtMap == null){
            return listMap;
        }
        try {
            if(questionExtMap.size() > 0 && StringUtil.isValid(tabName)) {
                Iterator it = questionExtMap.keys().iterator();
                treeMap = new TreeMap();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    if(tabName.equals(question.getTabName())) {
                        //PW_FORM_VERSION Starts
                        if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                            continue;
                        }
                        //PW_FORM_VERSION Ends
                        treeMap.put(Integer.parseInt(question.getKeyValue(TableXMLDAO.ORDER_BY)), question);
                    }
                }
                if(treeMap.size() > 0) {
                    listMap = new Question[treeMap.size()];

                    Iterator itkey = treeMap.keySet().iterator();
                    int i = 0;
                    while(itkey != null && itkey.hasNext()){
                        Object key1 = itkey.next();
                        listMap[i] = (Question)treeMap.get(key1);
                        i++;
                    }
                }
            }
        } catch(Exception e) {
            return listMap;
        }
        return listMap;
    }

    /**
     * @PW_FORM_VERSION
     * This will return all Orderd Headers of tab for given version
     */
    public HeaderMap[] getTabOrderedHeaderMap(String tabName) {
        return getTabOrderedHeaderMap(tabName, null);
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return all Orderd Header of tab input
     * @param tabName
     * @return
     */
    public HeaderMap[] getTabOrderedHeaderMap(String tabName, String formVersion) {
        Map treeMap;
        HeaderMap[] sFields = new HeaderMap[0];
        if(!StringUtil.isValid(tabName)) {
            return sFields;
        }
        if(headerMap == null || headerMap.size() == 0) {
            return sFields;
        } else {
            treeMap = new TreeMap();
        }

        Iterator it = headerMap.values().iterator();
        while(it.hasNext()) {
            HeaderMap field = (HeaderMap)it.next();
            if(tabName.equals(field.getTabName())) {
                //PW_FORM_VERSION Starts
                if(StringUtil.isValid(formVersion) && !field.getHeaderFields().isValidVersion(formVersion)){
                    continue;
                }
                //PW_FORM_VERSION Ends
                treeMap.put(field.getOrder(), field);
            }
        }
        sFields = new HeaderMap[treeMap.size()];

        Iterator itkey = treeMap.keySet().iterator();
        int i = 0;
        while(itkey != null && itkey.hasNext()){
            Object key1 = itkey.next();
            sFields[i] = (HeaderMap)treeMap.get(key1);
            i++;
        }

        return sFields;
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * This will return all Header of tab input
     * @param tabName
     * @return
     */
    public HeaderMap[] getTabHeaderMap(String tabName) {
        ArrayList list;
        HeaderMap[] sFields;
        if(!StringUtil.isValid(tabName)) {
            return new HeaderMap[0];
        }
        if(headerMap == null || headerMap.size() == 0) {
            return new HeaderMap[0];
        } else {
            list = new ArrayList<HeaderMap>(0);
        }

        Iterator it = headerMap.values().iterator();
        while(it.hasNext()) {
            HeaderMap field = (HeaderMap)it.next();
            if(tabName.equals(field.getTabName())) {
                list.add(field);
            }
        }

        sFields = new HeaderMap[list.size()];
        if(list.size() > 0) {
            int i = 0;
            Iterator str = list.iterator();
            while(str.hasNext()) {
                sFields[i] = (HeaderMap)str.next();
                i++;
            }
        }

        return sFields;
    }

    /**
     * @PW_FORM_VERSION
     * Ordered header for Simple Form view of given version
     */
    public HeaderMap[] getOrderedHeaderMap() {
        return getOrderedHeaderMap(null);
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Ordered header for Simple Form view
     * @return
     */
    public HeaderMap[] getOrderedHeaderMap(String formVersion) {
        Map treeMap;
        HeaderMap[] sFields;
        if(headerMap == null || headerMap.size() == 0) {
            return new HeaderMap[0];
        } else {
            treeMap = new TreeMap();
        }

        Iterator it = headerMap.values().iterator();
        while (it!=null && it.hasNext()) {
            HeaderMap field = (HeaderMap)it.next();
            if(!StringUtil.isValid(field.getTabName())) {
                //PW_FORM_VERSION Starts
                if(StringUtil.isValid(formVersion) && !field.getHeaderFields().isValidVersion(formVersion)){
                    continue;
                }
                //PW_FORM_VERSION Ends
                treeMap.put(field.getOrder(), field);
            }
        }

        sFields = new HeaderMap[treeMap.size()];

        Iterator itkey = treeMap.keySet().iterator();
        int i = 0;
        while(itkey != null && itkey.hasNext()){
            Object key1 = itkey.next();
            sFields[i] = (HeaderMap)treeMap.get(key1);
            i++;
        }

        return sFields;
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Getting all Header Fields
     * @return
     */
    public Field[] getAllHeaderFields(String sectionType) {
        try {
            Field[] allFields = null;
            ArrayList list = new ArrayList();
            boolean flag = false;
            Iterator it = fieldMap.values().iterator();
            for (int i=0;it!=null && it.hasNext();) {
                Field field = (Field)it.next();
                if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                    if(!sectionType.equals(field.getSectionField())) {
                        continue;
                    }
                    list.add(field);
                }
            }
            if(list.size() > 0){
                allFields = new Field[list.size()];
                int count = 0;
                Iterator itr = list.iterator();
                while (itr.hasNext()) {
                    Field field = (Field)itr.next();
                    allFields[count++] = field;
                }
            }
            return allFields;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Getting all Tab Questions
     * @return
     */
    public Question[] getAllTabQuestions(String tabName) {
        Question[] ques = new Question[0];
        ArrayList list = new ArrayList<Question>(0);

        HeaderMap[] hMaps = getHeaderMap();
        try {
            //geting all Non Header Questions
            if(questionExtMap != null && questionExtMap.size() > 0){
                Iterator it = questionExtMap.keys().iterator();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    if(tabName.equals(question.getTabName())) {
                        list.add(question);
                    }
                }
            }
            //getting all header questions
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                if(StringUtil.isValid(tabName) && StringUtil.isValid(hField.getTabName()) && tabName.equals(hField.getTabName())) {
                    Question[] questions = hField.getQuestions();
                    for(int i=0; i < questions.length; i++) {
                        list.add(questions[i]);
                    }
                }
            }
        } catch(Exception e) {
            return ques;
        }

        if(list.size() > 0) {
            ques = new Question[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                ques[i] = (Question)itr.next();
                i++;
            }
        }
        return ques;
    }

    /**
     * @AUDIT_ENHANCEMENT_CHANGES
     * Getting all Tab Fields
     * @return
     */
    public Field[] getAllTabFields(String tabName) {
        Field[] flds = new Field[0];
        ArrayList list = new ArrayList<Question>(0);

        HeaderMap[] hMaps = getHeaderMap();
        try {
            //geting all Non Header Questions
            if(questionExtMap != null && questionExtMap.size() > 0){
                Iterator it = questionExtMap.keys().iterator();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    if(tabName.equals(question.getTabName())) {
                        Field[] fld = getQuestionFields(question.getKeyValue(TableXMLDAO.NAME));
                        for (Field field : fld) {
                            list.add(field);
                        }
                    }
                }
            }
            //getting all header questions
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                if(StringUtil.isValid(tabName) && StringUtil.isValid(hField.getTabName()) && tabName.equals(hField.getTabName())) {
                    Question[] questions = hField.getQuestions();
                    for(int i=0; i < questions.length; i++) {
                        Field[] fld = getQuestionFields(questions[i].getKeyValue(TableXMLDAO.NAME));
                        for (Field field : fld) {
                            list.add(field);
                        }
                    }
                }
            }
        } catch(Exception e) {
            return flds;
        }

        if(list.size() > 0) {
            flds = new Field[list.size()];
            int i = 0;
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                flds[i] = (Field)itr.next();
                i++;
            }
        }
        return flds;
    }

    public Field[] getAllNonBuildFieldsArray()
    {
        Field[] allFields = new Field[allCount];
        Iterator it = fieldMap.values().iterator();
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY ||
                    field.getFieldType() == Field.FieldType.ALL_ONLY)
            {
                allFields[i++] = field;
            }
        }

        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < allFields.length; i++) {
            if(!allFields[i].isBuildField()) {
                arrLst.add(allFields[i]);
            }
        }

        Field[] summaryFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryFields[i] = fld;
        }

        return summaryFields;
    }
    //START : PWISE_FORM_LOADING_ISSUE
    public SequenceMap getAllFormQuestions(String formVersion, String tabName, boolean withAction){
        return getAllFormQuestions(formVersion,tabName,withAction,null);
    }
    
    public SequenceMap getAllFormQuestions(String formVersion, String tabName, boolean withAction,SequenceMap requiredInfo) {
    	return getAllFormQuestions(formVersion,tabName,withAction,requiredInfo,null,null,null);
    }

    public SequenceMap getAllFormQuestions(String formVersion, String tabName, boolean withAction,SequenceMap requiredInfo,String franchiseeNo,SequenceMap optionScoreMap,Info smartQuestionInfo) {
        int j=0;
        SequenceMap quesMap = new SequenceMap();
        // live scoring Changes starts
        //int overAllMaxScore=0;
        Float overAllMaxScore=0.00F; //PW_ENH_QUE_NUMBER 
        Float actualLiveScore=0.00F;
        JSONObject scoreArrayNotExcluded = new JSONObject();
        JSONObject overAllMaxArray = new JSONObject();
        JSONObject scoreArrayAttemptedQuestion = new JSONObject();        
        String maxScore 	= FieldNames.EMPTY_STRING;
        String questionName = FieldNames.EMPTY_STRING;
        String excludeNonResponse= FieldNames.EMPTY_STRING;
        String scoreIncluded = FieldNames.EMPTY_STRING;
        String isSmartQuestion=FieldNames.EMPTY_STRING;
        String moduleName=FieldNames.EMPTY_STRING;
        String dbFieldName=FieldNames.EMPTY_STRING;
        String dbTableName=FieldNames.EMPTY_STRING;
        String smartQuestionData=FieldNames.EMPTY_STRING;
        SequenceMap questionOptionsMap=null;
        
        // live scoring Changes ends
        try {
            if(questionExtMap != null && questionExtMap.size() > 0) {
                Iterator it = questionExtMap.keys().iterator();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    if(StringUtil.isValid(tabName) && !tabName.equals(question.getTabName())) {
                        continue;
                    }
                    if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                        continue;
                    }
                    if(withAction && "notApplicable".equals(question.getKeyValue(TableXMLDAO.COMP_TYPE))){ //PWISE_FORM_LOADING_ISSUE
                        continue;
                    }
                    quesMap.put(question.getQuestionName(), question);
                    if(requiredInfo!=null){ // live scoring Changes starts
                        maxScore 		= question.getKeyValue(TableXMLDAO.MAX_SCORE);
                        excludeNonResponse 	= question.getKeyValue(TableXMLDAO.EXCLUDE_NON_RESPONSE);
                        scoreIncluded 	= question.getKeyValue(TableXMLDAO.IS_SCORABLE);
                        questionName 	= question.getKeyValue(TableXMLDAO.NAME);
                      //ENH_PW_SMART_QUESTIONS_STARTS
                        isSmartQuestion 	= question.getKeyValue(TableXMLDAO.IS_SMART_QUESTION);
                        dbFieldName= question.getDbField();
                        dbTableName= question.getDbTableName();
                        moduleName= question.getModuleName();
                      //ENH_PW_SMART_QUESTIONS_ENDS
                        if("N".equals(excludeNonResponse) && "Y".equals(scoreIncluded) && maxScore!=null)
                         {
                            //overAllMaxScore  = overAllMaxScore +  Integer.parseInt(maxScore);
							overAllMaxScore  = overAllMaxScore +  Float.parseFloat(maxScore); //PW_ENH_QUE_NUMBER 
                            scoreArrayNotExcluded.put(questionName,0);
                            scoreArrayAttemptedQuestion.put(questionName,0);//ENH_PW_SMART_QUESTIONS
                         }
                      //ENH_PW_SMART_QUESTIONS_STARTS 
                        if("Y".equals(isSmartQuestion)){
                        	
                        	try{
                        	   smartQuestionData=getDataForLocation( dbTableName,dbFieldName,moduleName,franchiseeNo);
                        	  }catch(Exception e){
                        		  e.printStackTrace();
                        	  }
                        	System.out.println("\n smartQuestionData:::"+smartQuestionData);
                        	smartQuestionData="1000.00";//only for testing purpose of Range Type SmartQuestions
                         if(StringUtil.isValidNew(smartQuestionData) &&  optionScoreMap!=null)
                        	{
                        	    smartQuestionInfo.put(questionName, smartQuestionData);
                        		questionOptionsMap=(SequenceMap)optionScoreMap.get(questionName);
                        		if(questionOptionsMap!=null && !questionOptionsMap.isEmpty())
                        		{
                        			Iterator itr=questionOptionsMap.keys().iterator();
                        			Info optionInfo=null;
                        			boolean flag=false;
                        			String scoreValue="0.00";
                        			String optionId =null;
                        			String type="";
                        			String optionValue="";
                        			float optionVal=0.00F;
                        		while(itr.hasNext())
                        	     {
                                         optionId = (String)itr.next();
                                         optionInfo=(Info)questionOptionsMap.get(optionId);
                                         type=optionInfo.getString("type");
                                         optionValue=optionInfo.getString("optionValue");
                                         
                                     if(("range".equals(type) ||"multiinput".equals(type)) && "Y".equals(scoreIncluded) && maxScore!=null)
                                      {
                                    	 if(!"And Above".equals(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length())))
                                    	      optionVal = Float.parseFloat(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length()));
                                    	 if("And Above".equals(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length())) || Float.parseFloat(smartQuestionData)< optionVal)
                                    	  {
                                    		 scoreValue=optionInfo.getString("score");
 	                        				
 	                        				if(!StringUtil.isValid(scoreValue))
 	                        					scoreValue="0.00";
 	                        				
 	                        				break;
                                    	  }
                                         
                                      }
                                     else //for all other questions combo,radio,checkbox,multiselect
                                      {
	                        			if(smartQuestionData.equals(optionInfo.getString("optionID")) && "Y".equals(scoreIncluded) && maxScore!=null)
	                        			{
	                        				scoreValue=optionInfo.getString("score");
	                        				
	                        				if(!StringUtil.isValid(scoreValue))
	                        					scoreValue="0.00";
	                        				
	                        				break;
                        			    }
                                     }
                        		} 
                        		
		                        		if("Y".equals(excludeNonResponse) && "Y".equals(scoreIncluded) && maxScore!=null){
		                        			overAllMaxScore  = overAllMaxScore +  Integer.parseInt(maxScore);
		                        		}
		                        		
		                        	actualLiveScore=actualLiveScore+Float.parseFloat(scoreValue);
		                        	scoreArrayAttemptedQuestion.put(questionName,scoreValue);
	                        		
                        		}	
                        	}
                            else{
                            	scoreArrayAttemptedQuestion.put(questionName,0);
                            	 smartQuestionInfo.put(questionName, "-1");
                             }
                        }
                        
                        overAllMaxArray.put(questionName,maxScore);
                    }  // live scoring Changes ends

                }
            }
            
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();
                if(StringUtil.isValid(tabName) && !tabName.equals(headerMap.getTabName())) {
                    continue;
                }
                if(StringUtil.isValid(formVersion) && !headerMap.getHeaderFields().isValidVersion(formVersion)){
                    continue;
                }
                Question[] questions = hField.getQuestions();
                Question question = null;
                for(int i=0; i < questions.length; i++) {
                    question = questions[i];
                    if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                        continue;
                    }
                    if(withAction && "notApplicable".equals(question.getKeyValue(TableXMLDAO.COMP_TYPE))){ //PWISE_FORM_LOADING_ISSUE
                        continue;
                    }
                    quesMap.put(question.getQuestionName(), question);
                    if(requiredInfo!=null){ // live scoring Changes starts
                        maxScore 		= question.getKeyValue(TableXMLDAO.MAX_SCORE);
                        excludeNonResponse 	= question.getKeyValue(TableXMLDAO.EXCLUDE_NON_RESPONSE);
                        scoreIncluded 	= question.getKeyValue(TableXMLDAO.IS_SCORABLE);
                        questionName 	= question.getKeyValue(TableXMLDAO.NAME);
                        if("N".equals(excludeNonResponse) && "Y".equals(scoreIncluded) && maxScore!=null)
                        {
                           //overAllMaxScore  = overAllMaxScore +  Integer.parseInt(maxScore);
							overAllMaxScore  = overAllMaxScore +  Float.parseFloat(maxScore); //PW_ENH_QUE_NUMBER
                            scoreArrayNotExcluded.put(questionName,0);
                        }
                        
                      //smQuestion starts
                        if("Y".equals(isSmartQuestion)){
                        	
                        	try{
                        	   smartQuestionData=getDataForLocation( dbTableName,dbFieldName,moduleName,franchiseeNo);
                        	  }catch(Exception e){
                        		  e.printStackTrace();
                        	  }
                        	
                        	System.out.println("\n smartQuestionData:::"+smartQuestionData);

                         if(StringUtil.isValidNew(smartQuestionData) &&  optionScoreMap!=null)
                        	{
                        	    smartQuestionInfo.put(questionName, smartQuestionData);
                        		questionOptionsMap=(SequenceMap)optionScoreMap.get(questionName);
                        		if(questionOptionsMap!=null && !questionOptionsMap.isEmpty())
                        		{
                        			Iterator itr=questionOptionsMap.keys().iterator();
                        			Info optionInfo=null;
                        			boolean flag=false;
                        			String scoreValue="0.00";
                        			String optionId =null;
                        			String type="";
                         			String optionValue="";
                         			float optionVal=0.00F;
                        		while(itr.hasNext()){
                                         optionId = (String)itr.next();
                                         optionInfo=(Info)questionOptionsMap.get(optionId);
                                         type=optionInfo.getString("type");
                                         optionValue=optionInfo.getString("optionValue");
                                      if(("range".equals(type) ||"multiinput".equals(type)) && "Y".equals(scoreIncluded) && maxScore!=null)
                                       {
                                         if(!"And Above".equals(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length())))
                                       	      optionVal = Float.parseFloat(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length()));
                                       	 if("And Above".equals(optionValue.substring(optionValue.lastIndexOf('-')+1,optionValue.length())) || Float.parseFloat(smartQuestionData)< optionVal)
                                       	  {
                                       		 scoreValue=optionInfo.getString("score");
    	                        				
    	                        				if(!StringUtil.isValid(scoreValue))
    	                        					scoreValue="0.00";
    	                        				
    	                        				break;
                                       	  }
                                            
                                        }
                                        else //for all other questions combo,radio,checkbox,multiselect
                                         {    
		                        			if(smartQuestionData.equals(optionInfo.getString("optionID")) && "Y".equals(scoreIncluded) && maxScore!=null){
		                        				scoreValue=optionInfo.getString("score");
		                        				
		                        				if(!StringUtil.isValid(scoreValue))
		                        					scoreValue="0.00";
		                        				
		                        				break;
		                        			}
                        			
                        		        }
                        			 } 
                        		
		                        		if("Y".equals(excludeNonResponse) && "Y".equals(scoreIncluded) && maxScore!=null){
//		                        			overAllMaxScore  = overAllMaxScore +  Integer.parseInt(maxScore);
		                        			overAllMaxScore  = overAllMaxScore +  Float.parseFloat(maxScore);
		                        		}
		                        		
		                        	actualLiveScore=actualLiveScore+Float.parseFloat(scoreValue);
		                        	scoreArrayAttemptedQuestion.put(questionName,scoreValue);
	                        		
                        		}	
                        	}
                            else{
                            	scoreArrayAttemptedQuestion.put(questionName,0);
                            	 smartQuestionInfo.put(questionName, "-1");
                             }
                        }//smQuestion ends
                        
                        overAllMaxArray.put(questionName,maxScore);
                    } // live scoring Changes ends


                }

            }
        } catch(Exception e) {
        }
        // live scoring Changes starts
        if(requiredInfo!=null){
            requiredInfo.put("overAllMaxScore",overAllMaxScore+"");
            requiredInfo.put("actualLiveScore",actualLiveScore+"");  //smQuestion
            requiredInfo.put("scoreArrayNotExcluded",scoreArrayNotExcluded);
            requiredInfo.put("overAllMaxArray",overAllMaxArray);
            requiredInfo.put("scoreArrayAttemptedQuestion",scoreArrayAttemptedQuestion);//  //smQuestion
        }// live scoring Changes ends
        return quesMap;
    }
    //END : PWISE_FORM_LOADING_ISSUE

    
    
    
  //ENH_PW_SMART_QUESTIONS_STARTS
public String getDataForLocation(String tableName,String fieldName,String moduleName,String franchiseeNo){
			
	String  data= null;
	StringBuffer query = null;
	
	if("fim".equals(moduleName)){

	   query = new StringBuffer("SELECT "+fieldName+" FROM "+tableName+" F1 ");
/*
	   if(!"FRANCHISEE".equals(tableName))
	      query.append(" LEFT JOIN FRANCHISEE F2.FRANCHISEE_NO=F1.ENTITY_ID ");
*/	   
	   query.append(" WHERE  1=1");
	   
/*	   if(StringUtil.isValid(franchiseeNo)){
		   query.append(" AND F2.FRACHISEE_NO ='"+franchiseeNo+"'");
	   }
*/
	   if(StringUtil.isValid(franchiseeNo)){
		   
		   if("FRANCHISEE".equals(tableName))
			  query.append(" AND FRANCHISEE_NO ='"+franchiseeNo+"'");
		   else
			   query.append(" AND ENTITY_ID ='"+franchiseeNo+"'");
			   
	   }
	   ResultSet result = QueryUtil.getResult(query.toString(), null);
	    
	    try {
	    	if  (result!=null && result.next()) 
	    			data=result.getString(fieldName);
	    		
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	QueryUtil.releaseResultSet(result);
	    }
	    
	}else if("pwise".equals(moduleName)){
		
		
		
	}else if("fin".equals(moduleName)){
		
		
		
	}else if("fcSystem".equals(moduleName)){
		
		
		
	}else if("cm".equals(moduleName)){
		
		
		
	}  
	
	
	
	    return data;
	}
	    
//ENH_PW_SMART_QUESTIONS_ENDS
    /**
     * This function will return all action ids associated with  form.
     * @author rohit
     * @param formVersion
     * @param tabName
     * PW_ACTIONITEM_DELETE_ISSUE
     * @return
     */
    public String getAllActionPlans(String formVersion) {

        String actionPlan=FieldNames.EMPTY_STRING;
        String actionIDs=FieldNames.EMPTY_STRING;
        try {
            if(questionExtMap != null && questionExtMap.size() > 0) {
                Iterator it = questionExtMap.keys().iterator();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Question question = (Question)questionExtMap.get(key);
                    if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                        continue;
                    }
                    if( "notApplicable".equals(question.getKeyValue(TableXMLDAO.COMP_TYPE))){
                        continue;
                    }
                    actionPlan= question.getKeyValue(TableXMLDAO.ACTION_PLAN);
                    if(StringUtil.isValidWithZero(actionPlan))
                        actionIDs=actionIDs+actionPlan+",";
                }
            }
            HeaderMap[] hMaps = getHeaderMap();
            for(HeaderMap headerMap:hMaps) {
                HeaderField hField = headerMap.getHeaderFields();

                if(StringUtil.isValid(formVersion) && !headerMap.getHeaderFields().isValidVersion(formVersion)){
                    continue;
                }
                Question[] questions = hField.getQuestions();
                Question question = null;
                for(int i=0; i < questions.length; i++) {
                    question = questions[i];
                    if(StringUtil.isValid(formVersion) && !question.isValidVersion(formVersion)){
                        continue;
                    }
                    if("notApplicable".equals(question.getKeyValue(TableXMLDAO.COMP_TYPE))){
                        continue;
                    }
                    actionPlan= question.getKeyValue(TableXMLDAO.ACTION_PLAN);
                    if(StringUtil.isValidWithZero(actionPlan))
                        actionIDs=actionIDs+actionPlan+",";
                }

            }
        } catch(Exception e) {
        }
        if(StringUtil.isValid(actionIDs) && actionIDs.indexOf(",")!=-1)
            actionIDs=actionIDs.substring(0, actionIDs.length()-1);
        return actionIDs;
    }

    /**
     * This function will return section name for corresponding question 
     * @author Faisal Anees
     * @param quesName
     * Audit form delete issue if section changes
     * @return
     */
    public String getQuestionSection(String quesName) {
        Question[] questions = getAllNonHeaderQuestions();
        for(int i=0; i < questions.length; i++) {
            if(questions[i].getQuestionName().equals(quesName)) {
                return "noSection";
            }
        }
        HeaderMap[] hMaps = getHeaderMap();
        for(HeaderMap headerMap:hMaps) {
            HeaderField hField = headerMap.getHeaderFields();
            questions = hField.getQuestions();
            for(int i=0; i < questions.length; i++) {
                if(questions[i].getQuestionName().equals(quesName)) {
                    return headerMap.getName();
                }
            }
        }
        return "";
    }

    //EXTERNAL_FORM_BUILDER : START
    public Field[] getAllHiddenFieldsArray()
    {
        Iterator it = fieldMap.values().iterator();
        ArrayList arrLst = new ArrayList(0);
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if ("hidden".equals(field.getDisplayTypeField()))
            {
                arrLst.add(field);
            }
        }

        Field[] summaryFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryFields[i] = fld;
        }

        return summaryFields;
    }
    public Field[] getAllHeaderFieldsArray()
    {
        Iterator it = fieldMap.values().iterator();
        ArrayList arrLst = new ArrayList(0);
        for (int i=0;it!=null && it.hasNext();)
        {
            Field field = (Field)it.next();
            if ("Header".equals(field.getDisplayTypeField()))
            {
                arrLst.add(field);
            }
        }

        Field[] summaryFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            summaryFields[i] = fld;
        }

        return summaryFields;
    }
    
    public Info getSectionFieldsForExtForms(HeaderField hFld) {
        Info fieldInfo = new Info();
        String sectionType = hFld.getSectionField();
        Iterator it = fieldMap.values().iterator();
        int maxOrder = 0;
        for (int i=0;it!=null && it.hasNext();) {
            Field field = (Field)it.next();
            int orderId = field.getOrderBy();
            if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY || field.getFieldType() == Field.FieldType.ALL_ONLY) {
                if(!field.isActiveField())
                    continue;
                if(!sectionType.equals(field.getSectionField())) {
                    continue;
                }
                if(orderId != -1) {
                    if(orderId > maxOrder){
                        maxOrder = orderId;
                    }
                    fieldInfo.put(""+orderId, field);
                }
            }
        }
        Documents [] documentsArr = hFld.getDocuments();
        for (int i=0;i<documentsArr.length;i++) {
        	Documents documents 	 = documentsArr[i];
        	DocumentMap [] docMapArr = documents.getDocumentMaps();
        	for (int ii=0;ii<docMapArr.length;ii++) {
	        	SequenceMap docFldData 	= docMapArr[ii].getDocumentFieldMap();
	        	int orderId = Integer.parseInt(""+docFldData.get("order-by"));
	        	if(orderId != -1) {
                    if(orderId > maxOrder){
                        maxOrder = orderId;
                    }
                    fieldInfo.put(""+orderId, docFldData);
                }
        	}
        }
        if(fieldInfo.size() >= 0){
            fieldInfo.put("maxOrder", maxOrder);
        }
        return fieldInfo;
    }
    
    public List<Field> getAllExternalFormFieldsArray(){
    	 List<Field>	fieldList = new ArrayList();
         Iterator it = fieldMap.values().iterator();
         for (int i=0;it!=null && it.hasNext();)
         {
             Field field = (Field)it.next();
             if (field.getFieldType() == Field.FieldType.ALL_AND_SUMMARY ||
                     field.getFieldType() == Field.FieldType.ALL_ONLY)
             {
            	 fieldList.add(field);
             }
         }
         HeaderMap[] hMap	= this.getHeaderMap();
         if(hMap != null && hMap.length > 0){
        	 for(HeaderMap h:hMap) {
        		HeaderField hFld	=	h.getHeaderFields();
        		Documents [] documentsArr = hFld.getDocuments();
    	        for (int i=0;i<documentsArr.length;i++) {
    	        	Documents documents 	 = documentsArr[i];
    	        	DocumentMap [] docMapArr = documents.getDocumentMaps();
    	        	for (int ii=0;ii<docMapArr.length;ii++) {
    		        	SequenceMap docFldData 	= docMapArr[ii].getDocumentFieldMap();
    		        	Field docField 			= new Field (
    		        			(String)docFldData.get("field-name"),
    		        			(String)docFldData.get("db-field"),
    		        			(String)docFldData.get("display-name"),
    		        			(String)docFldData.get("display-type"), 
    		                    0,
    		                    null,
    		                    null,
    		                    null,
    		                    null,
    		                    null,
    		                    null,
    		                    null,
    		                    null);
    		        	
    		        	docField.setPiiEnabled(false);
    		        	docField.setMandatoryField("true".equals((String)docFldData.get("is-mandatory"))?true:false);
    		        	if(StringUtil.isValid((String)docFldData.get("dependentTableName"))){
    		        		docField.setDependentTableName((String)docFldData.get("dependentTableName"));
    		        	}
    		        	docField.setFieldData(docFldData.getHashMap());
    		        	fieldList.add(docField);
    	        	}
    	        }
        	 }
         }
         return fieldList;
    }
    //EXTERNAL_FORM_BUILDER : END
    
    public Field[] getAllCustomFieldsArr() {
    	
    	Field [] allFields = getAllFieldsArray();
        ArrayList arrLst = new ArrayList(0);
        for(int i=0 ; i < allFields.length; i++) {
            if(!allFields[i].isBuildField()) {
                arrLst.add(allFields[i]);
            }
        }

        Field [] allDbFields = new Field[arrLst.size()];
        for (int i=0 ;i<arrLst.size();i++)
        {
            Field fld = (Field)arrLst.get(i);
            allDbFields[i] = fld;
        }
        return allDbFields;
        
    }
    
    
}

	
