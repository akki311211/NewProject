package com.home.builderforms;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class Field implements Cloneable
{

    public static final class DataType
    {
        public static final String STRING 			= "String";
        public static final String LARGE_STRING		= "LargeString";
        public static final String INTEGER 			= "Integer";
        public static final String DOUBLE			= "Double";
        public static final String FILE				= "File";
        public static final String DATE				= "Date";
        public static final String BOOLEAN			= "Boolean";
        public static final String CLOB				= "Clob";
    }

    public static final class FieldType
    {
        public static final int ALL_ONLY			= 0;  //This type of field is added to allFields only
        public static final int ALL_AND_SUMMARY		= 1;  //This type of field is added to allFields and summaryField
        public static final int NONE				= 2;  //This type of field is added to neither
    }
    public static final class ValidationType
    {
        public static final String INTEGER			= "Integer";
        public static final String DOUBLE			= "Double";
        public static final String PHONE			= "Phone";
        public static final String FAX				= "Fax";
        public static final String EMAIL			= "Email";
        public static final String URL				= "Url";                                      //BB-20150203-259	(Url validation for Text display type of field.)
        public static final String MANDATORY		= "Mandatory";
        public static final String PERCENTAGE		= "Percentage";//Added By Ram Avtar for fimLicenseAgreement
    };
    private Map<String,Map> keywordParentMap;
    private String maxKeyword;
    private String fieldName;
    private String dbField;
    private String displayName;
    private String displayExportName;
    private boolean sourceField;
    private String dataType;
    private String validationType;
    private String width;
    private String colspan; 	//Added by Anuj for Export layout 2005-07-23
    private String colspanexport;
    private String colspanMerge;
    private int 	fieldType;
    private String msSrcTable;
    private String msSrcField;
    private String msSrcValue;
    private String msTransformMethod;
    private String msTransformMethodParam;
    private String msTransformMethodExportParam;
    private String msTransformExportMethod;
    private String msTransformExportMethodParam;
    private boolean msTransformRequiredInExport;
    private boolean msAllowInMain;
    private String msFieldMethod;
    private String msFieldMethodParam;

    private String msSearchField;

    // Added by vivek
    private String[] msSearchSourceMethodArgs;
    private String  msSearchSourceValuesMethod;
    private String  msSearchSourceValuesURL;
    private Trigger mFieldTrigger;
    private boolean bFieldExport;
    private boolean sortable;
    private String  sortColumn;
    private boolean bFileField; //added by AmitG for FileField
    private boolean bCustomField; //added by Sunilk for Custom Field Export
    private boolean isCurrency;
    private boolean groupBy; // added by abhishek gupta for Custom Field Export
    private boolean isActive;
    private boolean isDependentTableField;
    private String dependentTableName;
    private boolean isBuildField;
    private String  buildFieldSeq;
    private int		orderBy;
    private boolean isMandatory;
    private String section;
    private String displayType;
    private String validInput;
    private String noOfCols;
    private String noOfRows;
    private int dbFieldLength;
    private String mKeyword;
    private boolean mActive;
    private String comboSourceMethod;
    private String comboSourceMethodArgs;
    private String comboMethodParam;
    private boolean parent;
    private String dependentField;
    private String tableName;
    private String tabName = null;
    private boolean dTableFld = false;
    private String tableAnchor;
    private boolean piiEnabled = false;
    private Object jsMap = null;
    private String comboSourceMethodOtherField = null;
    private String comboSourceMethodOtherFieldType = null;
    private String comboSourceMethodOtherFieldSpan = null;
    private String countryOpt = null;
    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    private String radioOpt = null;
    private String checkboxOpt = null;
    private String dependentParent = null;
    private SequenceMap dependentChildTotalMap = null;
    //BB-20150203-259 (Dynamic Response based on parent field response) ends
    
    //P_Enh_Sync_Fields starts
    private SequenceMap syncTotalMap = null;
    private String syncWith = null;
    private String readOnly = null;
    private String syncModule = null;
    private boolean isParsingKeywordConfigured =true;
    
    private HashMap fieldData = null;
    
    public String getSyncModule() {
		return syncModule;
	}

	public void setSyncModule(String syncModule) {
		this.syncModule = syncModule;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	//P_Enh_Sync_Fields ends
	
	private String radioSourceMethod;
    private String radioSourceMethodArgs;
    private String radioMethodParam;
    private String radioSourceMethodOtherField = null;
    private String radioSourceMethodOtherFieldType = null;
    private String checkboxSourceMethod;
    private String checkboxSourceMethodArgs;
    private String checkboxMethodParam;
    private String checkboxSourceMethodOtherField = null;
    private String checkboxSourceMethodOtherFieldType = null;
    private String combinedDisplayName = null;
    private String mailMergeTransformMethod = null;
    private String radioSourceOtherFieldMethod = null;
    private String msCheckboxMethodReturnType = null;
    private String msCheckboxConditionField = null;
    private String msCheckboxConditionFieldValue = null;
    private String msFieldCalledMethod = null;
    private String msDependentModule = null;
    private String conditionField = null;
    private String conditionFieldValue = null;
    private int checkboxSourceValuesColCount = 0;
    private String questionName = null;
    private String fldViewType; // OPTION_VIEW_H_V
    private String colNoWrap=null;
    private String isMultiSelect = null;
    private String includeInCampaign = "1"; //BB-20150319-268(FIM Campaign Center)
    private String internationalization=null;
    private boolean isDefaultPii=false; // BB-20150525-360
    private boolean defaultPiiEnabled=false; // BB-20150525-360
    //CUSTOM_REPORT_SORTING_ISSUE starts
	private String castInOrder=null;
	private String customSortable=null;
	//CUSTOM_REPORT_SORTING_ISSUE ends
	private boolean centerInfoDisplay = false;
	private String jsValidationMethod=null;
	
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Field (){
    	
    }
    public Field (String fieldName,
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
                  String psSearchSourceValuesURL,
                  Trigger pTrigger,
                  boolean pbFieldExport,
                  boolean pbFileField, //added By AmitG for FileField
                  boolean pbCustomField) //added By Sunilk for Custom Field Export
    {
        this.fieldName 		= fieldName;
        this.dbField 		= dbField;
        this.displayName 	= displayName;
        this.dataType		= dataType;
        this.validationType	= validationType;
        this.width			= width;
        this.colspan 		= colspan; //Added by Anuj 2005-07-23
        this.fieldType 		= fieldType;
        msSrcTable			= psSrcTable;
        msSrcField			= psSrcField;
        msSrcValue			= psSrcValue;
        msTransformMethod	= psTransformMethod;
        msTransformMethodParam	= psTransformMethodParam;
        msSearchField		= psSearchField;
        msSearchSourceValuesMethod
                = psSearchSourceValuesMethod;
        msSearchSourceValuesURL
                = psSearchSourceValuesURL;

        mFieldTrigger		= pTrigger;
        bFieldExport		= pbFieldExport;
        bFileField			= pbFileField; // Added by AmitG for FileField
        bCustomField			= pbCustomField; // Added by Sunilk for Custom Field Export
    }
    // *******************************/////////////////////////////
    public Field (String fieldName,
                  String dbField,
                  String displayName,
                  String dataType,
                  String validationType,
                  String width,
                  String colspan, //Added by Anuj - 2005-07-23
                  int fieldType,
                  String psSrcTable,
                  String psSrcField,
                  String psSrcValue,
                  String psTransformMethod,
                  String psTransformMethodParam,
                  String psSearchField,
                  String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL,
                  String psSearchSourceMethodArgs,

                  Trigger pTrigger,
                  boolean pbFieldExport,
                  boolean pbFileField, //added By AmitG for FileField
                  boolean pbCustomField) // Added by Sunilk for Custom Field Export
    {
        this.fieldName 		= fieldName;
        this.dbField 		= dbField;
        this.displayName 	= displayName;
        this.dataType		= dataType;
        this.validationType	= validationType;
        this.width			= width;
        this.colspan 		= colspan; //Added by Anuj 2005-07-23
        this.fieldType 	= fieldType;
        msSrcTable			= psSrcTable;
        msSrcField			= psSrcField;
        msSrcValue			= psSrcValue;
        msTransformMethod	= psTransformMethod;
        msTransformMethodParam	= psTransformMethodParam;
        msSearchField		= psSearchField;


        msSearchSourceValuesMethod= psSearchSourceValuesMethod;
        msSearchSourceValuesURL	= psSearchSourceValuesURL;
        msSearchSourceMethodArgs = StringUtil.toArray(psSearchSourceMethodArgs);//added by vivek
        mFieldTrigger		= pTrigger;
        bFieldExport		= pbFieldExport;
        bFileField			= pbFileField; // Added by AmitG for FileField
        bCustomField			= pbCustomField; // Added by Sunilk for Custom Field Export
    }

    public Field (
            String fieldName,
            String dbField,
            String displayName,
            String dataType,
            String validationType,
            String colspan, //Added by Anuj - 2005-07-23
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL,
            Trigger pTrigger,
            boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField) // Added by Sunilk for Custom Field Export
    {
        this(
                fieldName,
                dbField,
                displayName,
                dataType,
                validationType,
                null,
                colspan, //Added by Anuj 2005-07-23
                fieldType,
                psSrcTable,
                psSrcField,
                psSrcValue,
                psTransformMethod,
                psTransformMethodParam,
                psSearchField,
                psSearchSourceValuesMethod,
                psSearchSourceValuesURL,
                pTrigger,
                pbFieldExport,
                pbFileField,	//Added by AmitG
                pbCustomField	// Added by Sunilk for Custom Field Export
        );
    }


    public Field (String fieldName,
                  String dbField,
                  String displayName,
                  String dataType,
                  String width,
                  int fieldType,
                  String psSrcTable,
                  String psSrcField,
                  String psSrcValue,
                  String psTransformMethod,
                  String psTransformMethodParam,
                  String psSearchField,
                  String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL)
    {
        this.fieldName 		= fieldName;
        this.dbField 		= dbField;
        this.displayName 	= displayName;
        this.dataType		= dataType;
        this.width			= width;
        this.fieldType 		= fieldType;
        msSrcTable			= psSrcTable;
        msSrcField			= psSrcField;
        msSrcValue			= psSrcValue;
        msTransformMethod	= psTransformMethod;
        msTransformMethodParam = psTransformMethodParam;
        msSearchField		= psSearchField;
        msSearchSourceValuesMethod
                = psSearchSourceValuesMethod;
        msSearchSourceValuesURL
                = psSearchSourceValuesURL;
    }

    public Field (
            String fieldName,
            String dbField,
            String displayName,
            String dataType,
            int fieldType,
            String psSrcTable,
            String psSrcField,
            String psSrcValue,
            String psTransformMethod,
            String psTransformMethodParam,
            String psSearchField,
            String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL)
    {
        this(
                fieldName,
                dbField,
                displayName,
                dataType,
                null,
                fieldType,
                psSrcTable,
                psSrcField,
                psSrcValue,
                psTransformMethod,
                psTransformMethodParam,
                psSearchField,
                psSearchSourceValuesMethod,
                psSearchSourceValuesURL
        );
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan, // Added by Anuj - 2005-07-23
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField, // added By AmitG for FileField
                  boolean pbCustomField, // Added by Sunilk for Custom Field Export
                  boolean sortable, String sortColumn)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, "");
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan, // Added by Anuj - 2005-07-23
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField, // added By AmitG for FileField
                  boolean pbCustomField, // Added by Sunilk for Custom Field Export
                  boolean sortable, String sortColumn, String isCurrency)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, "", "");
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, "");
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, "");
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, 0);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, false);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, null, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory, String fieldMethod,String fieldMethodParam)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory, String fieldMethod,String fieldMethodParam, String section)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod, fieldMethodParam, section, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory,
                  String fieldMethod,String fieldMethodParam, String section,String displayType)
    {
        this(fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, null,null,null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy, boolean isMandatory,
                  String fieldMethod,String fieldMethodParam, String section,String displayType,String validInput, String noOfCols, String noOfRows)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, null,null);

    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,null,null,null,null,null,null,null);
    }


    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, null,null,null,null,null,null,null,null,null,null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, null, null, null, null, null, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam, null);

    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam, String transformRequiredInExport)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, null,null,null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, null,null,null,null,null,null,null,null,null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, null, "0");
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, null, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName,
                  String fldViewType, String isDependentTableField, String dependentTableName)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, null, null, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge,null);
    }

    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge,null, null, null, null, null, null);

    }

    //BB-20150203-259 (Dynamic Response based on parent field response) ends

    //BB-20150319-268(FIM Campaign Center)
    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect)
    {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, null);

    }


    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect,String includeInCampaign) {
        this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, null);

    }




    public Field (String fieldName, String dbField, String displayName, String dataType,
                  String validationType, String width,
                  String colspan,
                  int fieldType, String psSrcTable, String psSrcField,
                  String psSrcValue, String psTransformMethod, String psTransformMethodParam,
                  String psSearchField, String psSearchSourceValuesMethod,
                  String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
                  Trigger pTrigger, boolean pbFieldExport,
                  boolean pbFileField,
                  boolean pbCustomField,
                  boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
                  boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
                  String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
                  String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
                  String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
                  String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
                  String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
                  String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
                  String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
                  String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect,String includeInCampaign, String internationalization)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
    {
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,null);
    }
    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
    {
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,null,null,null); 
    }
    
    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
{
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,null);
}
    
    
    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, 
            String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, 
            String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
{
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,null, null, null, null); //P_Enh_Sync_Fields
    	
}

    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, 
            String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, 
            String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
    {
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,centerInfoDisplay, syncWith, syncTotalMap, readOnly, null);
    }

    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, 
            String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, 
            String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
{
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, null);
}
    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, 
            String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, 
            String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule,HashMap fieldData)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
{
    	this (fieldName, dbField, displayName, dataType, validationType, width,
                colspan, fieldType, psSrcTable, psSrcField, psSrcValue, psTransformMethod,
                psTransformMethodParam, psSearchField, psSearchSourceValuesMethod, psSearchSourceValuesURL,
                psSearchSourceMethodArgs, pTrigger, pbFieldExport, pbFileField,
                pbCustomField, sortable, sortColumn, isCurrency, groupBy, isActive, isBuildField, buildFieldSeq, orderBy, isMandatory, fieldMethod,
                fieldMethodParam, section, displayType, validInput,noOfCols,noOfRows,dbFldLength, mKeyword,mActive,comboSourceMethod,comboSourceMethodArgs,comboMethodParam,parent,
                dependentField,comboSourceMethodOtherField, comboSourceMethodOtherFieldType, piiEnabled, obj, countryOpt, radioSourceMethod,radioSourceMethodArgs,radioMethodParam,
                radioSourceMethodOtherField,radioSourceMethodOtherFieldType,checkboxSourceMethod,checkboxSourceMethodArgs,checkboxMethodParam,checkboxSourceMethodOtherField,
                checkboxSourceMethodOtherFieldType, colspanexport, sourceField, displayExportName, transformMethodExportParam, transformExportMethod, transformExportMethodParam,
                transformRequiredInExport, allowInMain,combinedDisplayName,mailMergeTransformMethod, radioSourceOtherFieldMethod, checkboxMethodReturnType, checkboxConditionField,
                checkboxConditionFieldValue, fieldCalledMethod, dependentModule, comboSourceMethodOtherFieldSpan, conditionField, conditionFieldValue, checkboxSourceValuesColCount, questionName, fldViewType, isDependentTableField, dependentTableName, colspanMerge, colNoWrap, radioOpt, checkboxOpt, dependentParent, dependentChildTotalMap, isMultiSelect, includeInCampaign, internationalization,defaultPiiEnabled,castInOrder,customSortable,jsValidationMethod,centerInfoDisplay, syncWith, syncTotalMap, readOnly, syncModule, fieldData,null);
}
    
    public Field (String fieldName, String dbField, String displayName, String dataType,
            String validationType, String width,
            String colspan,
            int fieldType, String psSrcTable, String psSrcField,
            String psSrcValue, String psTransformMethod, String psTransformMethodParam,
            String psSearchField, String psSearchSourceValuesMethod,
            String psSearchSourceValuesURL, String psSearchSourceMethodArgs,
            Trigger pTrigger, boolean pbFieldExport,
            boolean pbFileField,
            boolean pbCustomField,
            boolean sortable, String sortColumn, String isCurrency, String groupBy, String isActive, String isBuildField, String buildFieldSeq, int orderBy,
            boolean isMandatory, String fieldMethod, String fieldMethodParam, String section, String displayType, String validInput, String noOfCols, String noOfRows,
            String dbFldLength, String mKeyword, String mActive,String comboSourceMethod,String comboSourceMethodArgs,String comboMethodParam,String parent,String dependentField,
            String comboSourceMethodOtherField, String comboSourceMethodOtherFieldType, String piiEnabled, Object obj, String countryOpt, String radioSourceMethod,
            String radioSourceMethodArgs,String radioMethodParam, String radioSourceMethodOtherField, String radioSourceMethodOtherFieldType, String checkboxSourceMethod,
            String checkboxSourceMethodArgs, String checkboxMethodParam, String checkboxSourceMethodOtherField,  String checkboxSourceMethodOtherFieldType, String colspanexport,
            String sourceField, String displayExportName, String transformMethodExportParam, String transformExportMethod, String transformExportMethodParam,
            String transformRequiredInExport, String allowInMain,String combinedDisplayName,String mailMergeTransformMethod, String radioSourceOtherFieldMethod,
            String checkboxMethodReturnType, String checkboxConditionField, String checkboxConditionFieldValue, String fieldCalledMethod, String dependentModule,
            String comboSourceMethodOtherFieldSpan, String conditionField, String conditionFieldValue, String checkboxSourceValuesColCount, String questionName, String fldViewType, 
            String isDependentTableField, String dependentTableName, String colspanMerge,String colNoWrap, String radioOpt, String checkboxOpt, String dependentParent, SequenceMap dependentChildTotalMap, 
            String isMultiSelect,String includeInCampaign, String internationalization,String defaultPiiEnabled,String castInOrder,String customSortable,String jsValidationMethod,String centerInfoDisplay, String syncWith, SequenceMap syncTotalMap, String readOnly, String syncModule, HashMap fieldData, String isParsingKeywordConfigured)//BB-20150319-268(FIM Campaign Center) //BB-20150203-259 (Dynamic Response based on parent field response)
{
        this.fieldName 		= fieldName;
        this.dbField 		= dbField;
        this.displayName 	= displayName;
        this.dataType		= dataType;
        this.validationType	= validationType;
        this.width			= width;
        this.colspan 		= colspan;
        this.fieldType 	= fieldType;
        msSrcTable			= psSrcTable;
        msSrcField			= psSrcField;
        msSrcValue			= psSrcValue;
        msTransformMethod	= psTransformMethod;
        msTransformMethodParam	= psTransformMethodParam;
        msSearchField		= psSearchField;
        msSearchSourceValuesMethod= psSearchSourceValuesMethod;
        msSearchSourceValuesURL	= psSearchSourceValuesURL;
        msSearchSourceMethodArgs = StringUtil.toArray(psSearchSourceMethodArgs);
        mFieldTrigger		= pTrigger;
        bFieldExport		= pbFieldExport;
        bFileField			= pbFileField;
        bCustomField		= pbCustomField;
        this.sortable		= sortable;
        this.sortColumn		= sortColumn;
        this.isCurrency 	= "true".equals(isCurrency) ? true:false;
        this.groupBy 	= "true".equals(groupBy) ? true:false;
        this.isActive 	= isActive == null || "".equals(isActive) || "yes".equals(isActive)? true:false;
        this.isDependentTableField 	= isDependentTableField != null && !"".equals(isDependentTableField) && "yes".equals(isDependentTableField) ? true:false;
        this.dependentTableName = dependentTableName;
        this.isBuildField 	= isBuildField == null || "".equals(isBuildField) || "yes".equals(isBuildField)? true:false;
        this.buildFieldSeq 	= buildFieldSeq == null || "".equals(buildFieldSeq)? "" : buildFieldSeq;
        this.orderBy = orderBy;
        this.isMandatory = isMandatory;
        msFieldMethod = fieldMethod;
        msFieldMethodParam = fieldMethodParam;
        this.section = section;
        this.displayType = displayType;
        this.validInput = validInput;
        this.noOfCols = StringUtil.isValidNew(noOfCols) ? noOfCols:"0";
        this.noOfRows = StringUtil.isValidNew(noOfRows) ? noOfRows:"0";
        this.dbFieldLength = StringUtil.isValidNew(dbFldLength) ? Integer.parseInt(dbFldLength):0;
        this.mKeyword = StringUtil.isValidNew(mKeyword) ? mKeyword:"";
        this.mActive = StringUtil.isValidNew(mActive) ? Boolean.parseBoolean(mActive):false;
        this.comboSourceMethod = StringUtil.isValidNew(comboSourceMethod) ? comboSourceMethod:"";
        this.comboSourceMethodArgs = StringUtil.isValidNew(comboSourceMethodArgs) ? comboSourceMethodArgs:"";
        this.comboMethodParam = StringUtil.isValidNew(comboMethodParam) ? comboMethodParam:"";
        this.parent = StringUtil.isValidNew(parent) ? Boolean.parseBoolean(parent):false;
        this.dependentField = StringUtil.isValidNew(dependentField) ? dependentField:"";
        this.piiEnabled = "true".equals(piiEnabled) ? true:false;
        this.defaultPiiEnabled = "true".equals(defaultPiiEnabled) ? true:false; // BB-20150525-360
        this.jsMap = obj;
        this.comboSourceMethodOtherField = comboSourceMethodOtherField;
        this.comboSourceMethodOtherFieldType = comboSourceMethodOtherFieldType;
        this.countryOpt = countryOpt;

        this.radioSourceMethod = StringUtil.isValidNew(radioSourceMethod) ? radioSourceMethod:"";
        this.radioSourceMethodArgs = StringUtil.isValidNew(radioSourceMethodArgs) ? radioSourceMethodArgs:"";
        this.radioMethodParam = StringUtil.isValidNew(radioMethodParam) ? radioMethodParam:"";
        this.radioSourceMethodOtherField = radioSourceMethodOtherField;
        this.radioSourceMethodOtherFieldType = radioSourceMethodOtherFieldType;

        this.checkboxSourceMethod = StringUtil.isValidNew(checkboxSourceMethod) ? checkboxSourceMethod:"";
        this.checkboxSourceMethodArgs = StringUtil.isValidNew(checkboxSourceMethodArgs) ? checkboxSourceMethodArgs:"";
        this.checkboxMethodParam = StringUtil.isValidNew(checkboxMethodParam) ? checkboxMethodParam:"";
        this.checkboxSourceMethodOtherField = checkboxSourceMethodOtherField;
        this.checkboxSourceMethodOtherFieldType = checkboxSourceMethodOtherFieldType;
        this.colspanexport = colspanexport;
        this.colspanMerge = colspanMerge;
        this.sourceField = StringUtil.isValidNew(sourceField) && "true".equals(sourceField)?true:false;
        this.displayExportName = displayExportName;
        this.msTransformMethodExportParam = transformMethodExportParam;
        this.msTransformExportMethod = transformExportMethod;
        this.msTransformExportMethodParam =transformExportMethodParam;
        this.msTransformRequiredInExport = StringUtil.isValid(transformRequiredInExport) && "false".equals(transformRequiredInExport)?false:true;
        this.msAllowInMain = StringUtil.isValid(allowInMain) && "false".equals(allowInMain)?false:true;
        this.combinedDisplayName = StringUtil.isValidNew(combinedDisplayName) ? combinedDisplayName:"";;
        this.mailMergeTransformMethod = StringUtil.isValidNew(mailMergeTransformMethod) ? mailMergeTransformMethod:"";
        this.radioSourceOtherFieldMethod = StringUtil.isValidNew(radioSourceOtherFieldMethod)? radioSourceOtherFieldMethod:"";
        this.msCheckboxMethodReturnType = StringUtil.isValidNew(checkboxMethodReturnType)? checkboxMethodReturnType:null;
        this.msCheckboxConditionField = StringUtil.isValidNew(checkboxConditionField)? checkboxConditionField:null;
        this.msCheckboxConditionFieldValue = StringUtil.isValidNew(checkboxConditionFieldValue)? checkboxConditionFieldValue:null;
        this.msFieldCalledMethod =  StringUtil.isValidNew(fieldCalledMethod)? fieldCalledMethod:null;
        this.msDependentModule = StringUtil.isValidNew(dependentModule)? dependentModule:null;
        this.comboSourceMethodOtherFieldSpan = StringUtil.isValidNew(comboSourceMethodOtherFieldSpan)? comboSourceMethodOtherFieldSpan:null;
        this.conditionField = StringUtil.isValidNew(conditionField)? conditionField:null;
        this.conditionFieldValue = StringUtil.isValidNew(conditionFieldValue)? conditionFieldValue:null;
        this.checkboxSourceValuesColCount = StringUtil.isInt(checkboxSourceValuesColCount)?Integer.parseInt(checkboxSourceValuesColCount):0;
        this.questionName	= StringUtil.isValidNew(questionName)? questionName:null;
        this.fldViewType	= StringUtil.isValidNew(fldViewType)? fldViewType:"0"; // OPTION_VIEW_H_V
        this.colNoWrap 		= StringUtil.isValid(colNoWrap)? colNoWrap:"";
        this.internationalization 	= StringUtil.isValid(internationalization)? internationalization:null;
        //BB-20150203-259 (Dynamic Response based on parent field response) starts
        this.radioOpt		= StringUtil.isValid(radioOpt)? radioOpt:"";
        this.checkboxOpt	= StringUtil.isValid(checkboxOpt)? checkboxOpt:"";
        this.dependentParent	= StringUtil.isValid(dependentParent)? dependentParent:"";
        this.dependentChildTotalMap = dependentChildTotalMap;
        //BB-20150203-259 (Dynamic Response based on parent field response) ends
        this.syncTotalMap = syncTotalMap; //P_Enh_Sync_Fields
        this.isMultiSelect = isMultiSelect;
        this.includeInCampaign = includeInCampaign; //BB-20150319-268(FIM Campaign Center)
        //CUSTOM_REPORT_SORTING_ISSUE starts
		this.castInOrder=castInOrder;
		this.customSortable=customSortable;
		//CUSTOM_REPORT_SORTING_ISSUE ends
		
		this.jsValidationMethod=jsValidationMethod;
		this.centerInfoDisplay = "true".equals(centerInfoDisplay) ? true:false;
		
		this.syncWith = StringUtil.isValidNew(syncWith) ? syncWith : ""; //P_Enh_Sync_Fields
		this.readOnly = StringUtil.isValidNew(readOnly) ? readOnly : ""; //P_Enh_Sync_Fields
		this.syncModule = StringUtil.isValidNew(syncModule) ? syncModule : "";
		this.fieldData = fieldData;
		this.isParsingKeywordConfigured ="false".equals(isParsingKeywordConfigured)? false:true;
    }

    //BB-20150319-268(FIM Campaign Center) starts
    public void setIncludeInCampaign(String includeInCampaign) {
        this.includeInCampaign = includeInCampaign;
    }

    public boolean getIncludeInCampaign() {
        boolean flag = false;
        if("0".equals(includeInCampaign)) {
            flag = true;
        }
        return flag;
    }
    //BB-20150319-268(FIM Campaign Center) ends

    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    public SequenceMap getDependentChildTotalMap() {
        return dependentChildTotalMap;
    }

    public void setDependentChildTotalMap(SequenceMap dependentChildTotalMap) {
        this.dependentChildTotalMap = dependentChildTotalMap;
    }
    //BB-20150203-259 (Dynamic Response based on parent field response) ends
    
    //P_Enh_Sync_Fields starts
    public SequenceMap getSyncTotalMap() {
    	return syncTotalMap;
    }

    public boolean getIsParsingKeywordConfigured() {
		return isParsingKeywordConfigured;
	}

	public void setIsParsingKeywordConfigured(boolean isParsingKeywordConfigured) {
		this.isParsingKeywordConfigured = isParsingKeywordConfigured;
	}

	public void setSyncTotalMap(SequenceMap syncTotalMap) {
    	this.syncTotalMap = syncTotalMap;
    }
    //P_Enh_Sync_Fields ends
    
    public void setFieldName(String fieldName) {
        this.fieldName=fieldName;
    }

    public String getFieldName()
    {
        return fieldName;
    }


    public String getDbField()
    {
        return dbField;
    }

    public void setDisplayName(String displayName) {
        this.displayName=displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }
    public void setColNoWrap(String colNoWrap) {
        this.colNoWrap=colNoWrap;
    }

    public String getColNoWrap()
    {
        return colNoWrap;
    }
    /**
     * Set value only for Export Display name
     */
    public void setDisplayExportName(String displayExportName) {
        this.displayExportName=displayExportName;
    }
    /**
     * Get value only for Export Display name
     */
    public String getDisplayExportName()
    {
        return displayExportName;
    }
    /**
     * Check whether Field is made as source Field and can not be managed from Form Builder
     */
    public boolean isSourceField()
    {
        return sourceField;
    }

    public String getDataType()
    {
        return dataType;
    }
    public String getValidationType()
    {
        return validationType;
    }
    public String getWidth()
    {
        return width;
    }

    public int getFieldType()
    {
        return fieldType;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }

    public String getColspan()
    {
        return colspan;
    }
    /**
     * Only for Export colspan
     */
    public void setColspanExport(String colspanexport) {
        this.colspanexport = colspanexport;
    }
    public String getColspanExport()
    {
        return colspanexport;
    }
    /**
     * Only for Export colspan
     */

    public void setColspanMerge(String colspanMerge) {
        this.colspanMerge = colspanMerge;
    }
    public String getColspanMerge()
    {
        return colspanMerge;
    }
    public String getSrcTable(){
        return msSrcTable;
    }
    public String getSrcField(){
        return msSrcField;
    }
    public String getSrcValue(){
        return msSrcValue;
    }
    public String getTransformMethod(){
        return msTransformMethod;
    }
    
    public void setTransformMethod(String msTransformMethod){//Wee Watch phone numbers starts
    	this.msTransformMethod = msTransformMethod;
    }//Wee Watch phone numbers ends
    
    public String getTransformMethodParam(){
        return msTransformMethodParam;
    }

    public void setTransformMethodParam(String msTransformMethodParam){
        this.msTransformMethodParam = msTransformMethodParam;
    }
    /**
     * Only for Export validation parameter
     */
    public String getTransformMethodExportParam(){
        return msTransformMethodExportParam;
    }
    /**
     * Only calls if Export method calls differ from generic calls of same Field
     */
    public String getTransformExportMethod(){
        return msTransformExportMethod;
    }
    /**
     * Only for Export validation parameter
     */
    public String getTransformExportMethodParam(){
        return msTransformExportMethodParam;
    }
    public boolean isTransformRequiredInExport() {
        return msTransformRequiredInExport;
    }
    /**
     * This will detect Field which are not applicable in Generic Handler call.
     * Skip this Field when it is set as False
     * @return
     */
    public boolean fieldAllowedInMain() {
        return msAllowInMain;
    }

    public String getFieldMethod(){
        return msFieldMethod;
    }
    public String getFieldMethodParam(){
        return msFieldMethodParam;
    }

    public String getSearchField(){
        return msSearchField;
    }
    //Added by vivek
    public String[] getSearchSourceMethodArgs(){
        return msSearchSourceMethodArgs;
    }
    public String getSearchSourceValuesMethod(){
        return msSearchSourceValuesMethod;
    }
    public String getSearchSourceValuesURL(){
        return msSearchSourceValuesURL;
    }
    public Trigger getTrigger(){
        return mFieldTrigger;
    }
    public void setTrigger(Trigger pTrigger){
        mFieldTrigger		= pTrigger;
    }
    public boolean isFieldExportable(){
        return bFieldExport;
    }
    public boolean isCustomField(){
        return bCustomField;
    }
    public boolean isFileField(){
        return bFileField;
    }

    public boolean isSortable()
    {
        return sortable;
    }

    public boolean isCurrencyField()
    {
        return isCurrency;
    }
    /**
     * Added function to get group by field value
     * @author abhishek gupta
     * @date 24 dec 2009
     * @return
     */
    public boolean isGroupByField() {
        return groupBy;
    }

    public void setGroupByField(boolean groupBy) {
        this.groupBy = groupBy;
    }

    public void setActiveField(boolean isActive) {
        this.isActive=isActive;
    }

    public boolean isActiveField() {
        return isActive;
    }
    public void setDependentTableField(boolean isDependentTableField) {
        this.isDependentTableField = isDependentTableField;
    }
    public void setDependentTableName(String dependentTableName) {
        this.dependentTableName = dependentTableName;
    }
    public boolean isDependentTableField() {
        return isDependentTableField;
    }
    public String getDependentTableName() {
        return dependentTableName;
    }
    public boolean isBuildField() {
        return isBuildField;
    }
    //ZCUB-20150505-144 -CM_Document Field Changes Start
    public void setIsBuildField(boolean isBuildField) {
        this.isBuildField = isBuildField;
    }
    //ZCUB-20150505-144 -CM_Document Field Changes End
    
    public void setMandatoryField(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean isMandatoryField() {
        return isMandatory;
    }

    public void setSectionField(String section) {
        this.section = section;
    }

    public String getSectionField() {
        return section;
    }

    public String getDisplayTypeField() {
        return displayType;
    }

    public String getValidInputField() {
        return validInput;
    }
    public String getNoOfColsField() {
        return noOfCols;
    }
    public String getNoOfRowsField() {
        return noOfRows;
    }

    public int getDBFieldLength() {
        return dbFieldLength;
    }

    public String getMailMergeKeyword() {
        return mKeyword;
    }

    public void setMailMergeKeyword(String mailMergeKeyWord)
    {
        this.mKeyword = mailMergeKeyWord;
    }

    public boolean isMailMergeActive() {
        return mActive;
    }

    public String buildFieldSeq() {
        return buildFieldSeq;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public String getSortColumn()
    {
        return sortColumn;
    }

    public String toText()
    {
        return "(fieldName = "+fieldName+ "(dbField = "+dbField+ " , displayName = "+ displayName+", dataType=" + dataType+" )";
    }

    public String toString()
    {
        return fieldName;
    }


    public String getComboSourceMethod() {
        return comboSourceMethod;
    }

    public String getComboSourceMethodArgs() {
        return comboSourceMethodArgs;
    }

    public String getComboMethodParam() {
        return comboMethodParam;
    }
    public void setComboMethodParam(String comboMethodParam) {
        this.comboMethodParam= comboMethodParam;
    }

    public boolean isParent() {
        return parent;
    }

    public String getDependentField() {
        return dependentField;
    }

    public void setDependentField(String dependentField) {
        this.dependentField=dependentField;
    }
    public void setTableName(String tableName) {
        this.tableName=tableName;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTabName(String tabName) {
        this.tabName=tabName;
    }
    public String getTabName() {
        return tabName;
    }

    public void setTableAnchor(String tableAnchor) {
        this.tableAnchor=tableAnchor;
    }
    public String getTableAnchor() {
        return tableAnchor;
    }

    public void setFieldOfOtherTable(boolean dTableFld) {
        this.dTableFld=dTableFld;
    }
    public boolean isFieldOfOtherTable() {
        return dTableFld;
    }

    public void setPiiEnabled(boolean piiEnabled) {
        this.piiEnabled=piiEnabled;
    }

    public boolean isPiiEnabled() {
        return piiEnabled;
    }

    public void setJsFieldMap(Object jsMap) {
        this.jsMap=jsMap;
    }

    public Object getJsFieldMap() {
        return jsMap;
    }

    public String getComboSourceMethodOtherField() {
        return comboSourceMethodOtherField;
    }
    public String getComboSourceMethodOtherFieldType() {
        return comboSourceMethodOtherFieldType;
    }

    public String getComboSourceMethodOtherFieldSpan() {
        return comboSourceMethodOtherFieldSpan;
    }
    public String getConditionField() {
        return conditionField;
    }
    public String getConditionFieldValue() {
        return conditionFieldValue;
    }
    public String getFieldDropdownOpt() {
        return countryOpt;
    }

    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    public String getFieldRadioOption() {
        return radioOpt;
    }

    public String getFieldCheckboxOption() {
        return checkboxOpt;
    }

    //P_Enh_Sync_Fields
    public String getSyncWithField() {
    	return syncWith;
    }
    
    public void setSyncWithField(String syncWith) {
    	this.syncWith = syncWith;
    }
    
    public String getDependentParentField() {
        return dependentParent;
    }
    //BB-20150203-259 (Dynamic Response based on parent field response) ends
    public void setCenterInfoDisplay(boolean centerInfoDisplay) {
        this.centerInfoDisplay=centerInfoDisplay;
    }
    public boolean isCenterInfoField() {
        return centerInfoDisplay;
    }
    public String getRadioSourceMethod() {
        return radioSourceMethod;
    }

    public String getRadioSourceMethodArgs() {
        return radioSourceMethodArgs;
    }

    public String getRadioMethodParam() {
        return radioMethodParam;
    }
    public void setRadioMethodParam(String radioMethodParam) {
        this.radioMethodParam= radioMethodParam;
    }

    public String getRadioSourceMethodOtherField() {
        return radioSourceMethodOtherField;
    }
    public String getRadioSourceMethodOtherFieldType() {
        return radioSourceMethodOtherFieldType;
    }
    public String getRadioSourceOtherFieldMethod() {
        return radioSourceOtherFieldMethod;
    }
    public String getCheckboxMethodReturnType() {
        return msCheckboxMethodReturnType;
    }
    public String getCheckboxConditionField() {
        return msCheckboxConditionField;
    }
    public String getCheckboxConditionFieldValue() {
        return msCheckboxConditionFieldValue;
    }
    public int getCheckboxSourceValuesColCount() {
        return checkboxSourceValuesColCount;
    }

    public String getFieldCalledMethod() {
        return msFieldCalledMethod;
    }
    public String getDependentModule() {
        return msDependentModule;
    }

    public String getCheckboxSourceMethod() {
        return checkboxSourceMethod;
    }

    public String getCheckboxSourceMethodArgs() {
        return checkboxSourceMethodArgs;
    }

    public String getCheckboxMethodParam() {
        return checkboxMethodParam;
    }
    public void setCheckboxMethodParam(String checkboxMethodParam) {
        this.checkboxMethodParam= checkboxMethodParam;
    }

    public String getCheckboxSourceMethodOtherField() {
        return checkboxSourceMethodOtherField;
    }
    public String getCheckboxSourceMethodOtherFieldType() {
        return checkboxSourceMethodOtherFieldType;
    }
    public String getCombinedDisplayName() {
        return combinedDisplayName;
    }
    public String getMailMergeTransformMethod() {
        return mailMergeTransformMethod;
    }
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    public String getQuestionName() {
        return questionName;
    }

    //BB-20150203-259 (MultiSelect combo in add new field changes) starts
    public void setisMultiSelect(String isMultiSelect)
    {
        this.isMultiSelect=isMultiSelect;
    }
    public String getisMultiSelect()
    {
        return isMultiSelect;
    }
    //BB-20150203-259 (MultiSelect combo in add new field changes) starts


    /**
     * OPTION_VIEW_H_V
     * Start
     */
    public void setFieldViewType(String fldViewType) {
        this.fldViewType = fldViewType;
    }
    public String getFieldViewType() {
        return fldViewType;
    }
    /**
     * End
     */
    //P_CM_Enh_BuilderForm starts
    public void setSourceField(boolean sourceField) {
        this.sourceField=sourceField;
    }
    //P_CM_Enh_BuilderForm ends
    public void setKeywordInfo(String keyWord,Map dataMap)
    {
        if(keywordParentMap==null)
        {
            keywordParentMap = new HashMap<String, Map>();
        }
        keywordParentMap.put(keyWord, dataMap);
    }
    public Map getKeyWordInfo(String keyWord)
    {
        if(keywordParentMap!=null)
        {
            return keywordParentMap.get(keyWord);
        }
        return null;
    }
    public void setMaxKeyword(String maxKeyword)
    {
        this.maxKeyword = maxKeyword;
    }
    public String getMaxKeyword()
    {
        return maxKeyword;
    }
    public String getInternationalization() {
        return internationalization;
    }


    public void setInternationalization(String internationalization) {
        this.internationalization = internationalization;
    }
    public boolean isDefaultPii() { // BB-20150525-360 starts
    	if(((this.isBuildField()&&this.isPiiEnabled())||this.isDefaultPiiEnabled())||(!this.isBuildField())){
    		isDefaultPii=true;
    	}
        return isDefaultPii;
    }
    public void setDefaultPiiEnabled(boolean defaultPiiEnabled) {
        this.defaultPiiEnabled=defaultPiiEnabled;
    }

    public boolean isDefaultPiiEnabled() {
        return defaultPiiEnabled;
    }
    // BB-20150525-360 ends
    
    //CUSTOM_REPORT_SORTING_ISSUE starts
	public String getCastInOrder() {
		return castInOrder;
	}


	public String getCustomSortable() {
		return customSortable;
	}


	public void setCustomSortable(String customSortable) {
		this.customSortable = customSortable;
	}


	public void setCastInOrder(String castInOrder) {
		this.castInOrder = castInOrder;
	}
	//CUSTOM_REPORT_SORTING_ISSUE ends


	public String getJsValidationMethod() {
		return jsValidationMethod;
	}

	public void setJsValidationMethod(String jsValidationMethod) {
		this.jsValidationMethod = jsValidationMethod;
	}

	public HashMap getFieldData() {
		return fieldData;
	}

	public void setFieldData(HashMap fieldData) {
		this.fieldData = fieldData;
	}
}
