package com.home.builderforms;
/**
 * This class is the central location to store the names of various xml elements of tables.xml.
 *@author abhishek gupta
 *@created  Nov 18, 2011
*/
public class BuilderConstants {
		public static final String TABLE_DEFINITION_XML_FILE = "/WEB-INF/xml/tables.xml";
		public static final String TABLE_SECTION	= "section";
		public static final String TABLE_CLASS	= "table-class";
		public static final String TABLE_SECTION_NAME = "name";
		public static final String TABLE_ELEMENT	= "table-element";
		public static final String TABLE_ELEMENT_TYPE	= "type";
		public static final String TABLE_DEFINITION = "table-definition";
		public static final String PARAMETER_TABLE_ID = "parameter-table-id";
		public static final String REPORT	= "report";
		public static final String TABLE_NAME = "table-name";
		public static final String TABLE_ID = "table-id";
		public static final String TABLE_DESCRIPTION = "table-description";
		public static final String CUSTOM_TABLE_ID = "customTableId";
		public static final String TABLE_TYPE = "table-type";
		public static final String GROUPING_PARAMETER	= "grouping-parameter";
		public static final String SEQUENCE_MAP_KEY	= "sequence-map-key";
		
		public static final String TABLE_CHART = "chart";
		public static final String FORM_TABLE = "table";
		public static final String TABLE = "table";
		public static final String COMPARE_COLUMNS = "comparison-columns";
		public static final String DIFFERENCE_COLUMNS = "difference-columns";
		public static final String COLUMN_CAPTIONS = "column-captions";
		public static final String COLUMN_NAMES = "column-names";
		public static final String COLUMN_TYPES = "column-types";
		public static final String QUERY = "query";
		public static final String QUERIES = "queries";
		public static final String SELECT = "select";

		public static final String FROM = "from";
		public static final String WHERE = "where";
		public static final String GROUP_BY = "group-by";
		public static final String ORDER_BY = "order-by";
		public static final String LIMIT = "limit";
		public static final String TABLE_COLUMN_CAPTIONS = "column-captions";
		public static final String TABLE_COLUMN_WIDTHS = "table-column-widths";
		public static final String PARAMETER_TABLE = "parameterTable";
		public static final String FORM_ID = "form-id";

		public static final String TABLE_STAT = "stat";
		public static final String TABLE_STAT_SUM = "SUM";
		public static final String TABLE_STAT_AVG = "AVG";
		public static final String TABLE_STAT_COUNT = "COUNT";
		
		public static final String SPACE = " ";
		public static final String MAP_TYPE = "map-type";
		public static final String AND = "AND";
		public static final String GROUP_BY_CLASS = "GROUP BY";
		public static final String ORDER_BY_CLASS = "ORDER BY";
		public static final String LIMIT_CLASS = "LIMIT";
		public static final String SINGLE_QUOTE = "'";
		public static final String DOUBLE_QUOTE = "\"";

		public static final String ALL = "all";
		
		public static final String DATE	= "date";
		public static final String TEXT	= "text";
		public static final String HIDDEN = "hidden";
		public static final String COLUN = ":";
		public static final String COLUN_WITH_SPACE	= " :";
		public static final String EMPTY = "";
		public static final String URGENT_FIELD	= "<span class=urgent_fields>*</span>";
		public static final String STRING		= "String";
		public static final String NUMERIC		= "Numeric";
		public static final String NUMBER		= "Number";
		public static final String AMOUNT		= "Amount";
		public static final String TEXT_LBL		= "Text";
		public static final String FILE_TYPE	= "File"; //BB-20150203-259 (Add Document as field for positioning)
		public static final String TEXTAREA		= "TextArea";
		public static final String COMBO		= "Combo";
		public static final String RADIO		= "Radio";
		public static final String CHECKBOX		= "Checkbox";
		public static final String DATE_LBL		= "Date";
		public static final String COMBO_COUNTRY_METHOD	= "comboFimCountry";
		public static final String COMBO_STATE_METHOD	= "comboFimState";
		public static final String TRANSFORM_COUNTRY_METHOD	= "transformCountryFromId";
		public static final String TRANSFORM_STATE_METHOD	= "transformStateFromId";
		public static final String TRUE	= "true";
		public static final String FALSE	= "false";
		public static final String CHECKBOX_TYPE_METHOD	= "getCheckBoxType";
		public static final String RADIO_TYPE_METHOD	= "getRadioType";
		public static final String BUILDER_TABLE_PATH	= "tables/buildertabs/";
		public static final String BUILDER_TEMPLATE_ANCHOR	= "builderTemplateTab";
		public static final String BUILDER_MAPPING_XML		= "buildermappings.xml";
		//ENH_MODULE_CUSTOM_TABS starts
		public static final String TAB_MAPPING_XML		= "tabmodules.xml";
		public static final String TEMPLATE_TAB_XML		= "templateTab.xml";
		//ENH_MODULE_CUSTOM_TABS ends
		
		//AUDIT_ENHANCEMENT_CHANGES starts
		public static final String AUDIT_FORMS_PATH				= "tables/audit/auditforms";
		//AUDIT_ENHANCEMENT_CHANGES ends
		public static final String TEMPLATE_TAB_XML_SCHEDULER="templateTabScheduler.xml";//P_SCH_ENH_008
		
		//BB-20150203-259 (MultiSelect combo in add new field changes) 
		public static final String IS_MULTI_SELECT		= "true";
		public static final String TABULAR_SECTION_MAPPING_XML		= "tabularSectionMappings.xml";
}