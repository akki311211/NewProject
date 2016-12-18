package com.home.builderforms;

import com.home.builderforms.Field;
import com.home.builderforms.sqlqueries.SQLUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class is the central location to store the message names of various
 *
 * @author misam
 * @created November 9, 2001 @ Modified for supplies @ Author Vikas
 * @date Tue 6,Dec 2005.
 * @Modified by santanu on 31-08-2006
 * @Added MAX_FILE_UPLOAD_SIZE to get the value from keyval.xml
 * 
 * 
 * ZCUB-20151208-210       19 Dec 2015     Divanshu Verma      Payment Approval Process

 *
 */
public class Constants
{
	public static final String DISPLAY_FORMAT = "MM/dd/yyyy";
    public static final boolean IS_COAPPLICANT_ENABLED = true;
    public static final String EXPECTED_OPENING_DATE = "Expected Opening Date";
    public static final String MAX_RSS_FEEDS = "5";
    public static final boolean isSchedulerHelp = false;
    public static final String APPLICATION_TITLE = "Fran Connect System";
    public static final String LBL_DAYS = "# of Days";
    public static final String LBL_STATUS_NAME = "Status";
    public static final String SCOPE_NAME = "Scope";
    public static final String SCOPE_DESCRIPTION = "Description";
    public static final String LBL_CONTACT_NAME = "Customer Name";
    public static final String LBL_CONTACT_NUMBER = "Customer NO";
    public static final String PRODUCT_AMOUNT = " Product Amount";
    public static final String LBL_REPORT_PERIOD_TO = "Report Period To";
    public static final String LBL_GOAL_YEAR = "Goal Year";
    public static final String YEAR = "Year";
    public static final String LBL_REGION = "Region";
    public static final String LBL_STORE_ID = "Store ID";
    public static final String ESTIMATE_HEADER = "Estimate No.";
    public static final String LBL_AGE_OF_STORE = "Age of Store";
    public static final String INVOICE_HEADER = "Invoice No.";
    public static final String LBL_TIME_PERIOD = "Sales Period";
    public static final String SHIP_TO_ADDRESS = "Ship To Address";
    public static final String BILL_TO_ADDRESS = "Bill To Address";
    public static final String LBL_STORE_TYPE = "Store Type";
    public static final String LBL_WIDTH = "950";
    public static final String LBL_TASK_PAGE_WIDTH = "1100";
    public static final String LBL_TASK_PAGE_HEIGHT = "760";
    public static final String LBL_HEIGHT = "650";
    public static final String LBL_TOP = "20";
    public static final String LBL_LEFT = "20";
    public static final String INVOICED = "invoiced";
    public static final String INVOICED_FULLY_PAID = "invoicedFullyPaid";
    public static final String NON_CT = "nonCT";
    public static final String APPOINTMENT = "appointment";
    public static final String FOLLOWUP = "followup";
    public static final String NON_ESTIMATED = "nonEstimated";
    public static final String ESTIMATED = "estimated";
    public static final String NON_ESTIMATED_INVOICED = "nonEstimatedInvoiced";
    public static final String NON_ESTIMATED_FULLY_PAID_INVOICED = "nonEstimatedFullyPaidInvoiced";
    public static final String LP_STATIC_JS_PATH = "/jsp/social/javascript/";
    public static final String RATE_CARD_LABEL = "Rate Card";
    public static final String MY_SETTING_DISPLAY_NAME = "My Settings";
    public static final String STORE_SETTING_DISPLAY_NAME = "Store Settings";
    public static final String USERS = "Users";
    public static final String TEAM = "Team";
    public static final String AREA_FRANCHISE_LABEL = "Area Franchise ID";
    public static final String AREA_FRANCHISE_NAME = "Area Franchise Name";
    public static final String LBL_REPORT_PERIOD_FROM = "Report Period From";
    public static final String LBL_STR_LEVEL = "Sales Level";
    public static final String HYPHENS = "--";
    public static final String DASHES = HYPHENS;
    public static final String HYPHEN = "-";
    public static final String DASH = HYPHEN;
    public static final String NOT_AVAILABLE = "N / A";
    public static final String LBL_PERCENTAGE = "%";
    public static final String LBL_ADMIN_FEE_NO_CURR = "Administrative Fee";
    public static final String LBL_SUB_SERVICE_NAME = "Sub Category";
    public static final String LBL_SUB_SERVICES_NAME = "Sub Category(s)";
    public static final String ADJUSTMENT = "adjustment";
    public static final String BASE = "base";
    public static final String LBL_ALL = "All";
    public static final boolean FIN_EXG_RATE_ACTIVE = true;
    public static final String LBL_EXG_RATE_APPLIED = "Exchange Rate Applied";
    public static final String LBL_REPORT_DATE = "Report Date";
    public static final String LBL_ROYALTY_NO_CURR = "Royalty";
    public static final String STORE_ID_NOTIFICATION = "Store IDs marked with * are terminated.";
    public static final String LBL_ADDL_FEES_NO_CURR = "Additional Fees";
    public static final String LBL_TOTAL_TAX_NO_CURR = "Total Tax";//BB-20150430-350
    public static final String LBL_QUANTITY = "Quantity";
    public static final String INTRANET_SEARCH_MSG = "Search All Intranet Items";
    public static final String MONTHS = "Months";
    public static final String KEY_STR = "str";
    public static final String KEY_GROSS_REVENUE = "rev";
    public static final String PUT_STORE_ID_NOTIFICATION_MARK = "<font color = \"#FF00FF\">*</font>";
    public static final String ALL_FEES_IN_USD = "Note: All figures in USD";
    public static final String FORM_FOR_VIEWIN_LOCALUSD = "formforviewinlocal";
    public static final String JS_FUNCTION_CONVERTCURRENCY = "convertCurrency";
    public static final String LBL_ADDL_FEE = "Additional Fees";
    public static final String LBL_ACTION_LOGS = "Action Logs";
    public static final String ACTION = "Action";
    public static final String LBL_PERIOD_SELECTED = "Period Selected";
    public static final String LBL_ADDL_FEES_T = "Additional Fees";
    public static final String LBL_TAX_T = "Tax";//BB-20150430-350
    public static final String LBL_ADVERTISEMENT_NO_CURR = "Ad / Mktng. Funds";
    public static final String LBL_TOTAL_SALES_NO_CURR = "Total Sales";
    public static final String LBL_AF_SHARE_ROYALTY_NO_CURR = "AF Share Royalty";
    public static final String LBL_AF_SHARE_ADV_NO_CURR = "AF Share Ad / Mktng. Funds";
    public static final String LBL_SHARE_AD_MAKT_NO_CURR = "Ad / Mktng. Funds Share";
    public static final String CURRENCY_USD_INFO = "Currency: USD ($)";
    public static final String LBL_INVOICE_NO_CURR = "Invoice Amount";
    public static final String LBL_AMOUNT_DUE_NO_CURR = "Open Balance";
    public static final String LBL_GOAL_COUNT = "Customer Count";
    public static final String LBL_GOAL_TRANS = "Customer Transactions";
    public static final String LBL_GROWTH = "% of Growth";
    public static final String LBL_AV_NAME = "Agreement Version Name";
    public static final String LBL_REPORTING_FREQUENCY = "Reporting Frequency";
    public static final String LBL_ROYALTY_PERCENTAGE = "Royalty (%)";
    public static final String LBL_ADV_PERCENTAGE = "Ad / Mktng. Funds (%)";
    public static final String LBL_ROYALTY_PERCENTAGE_AF = "AF Share (Royalty) (%)";
    public static final String LBL_ADV_PERCENTAGE_AF = "AF Share (Ad / Mktng. Funds) (%)";
    public static final String LBL_REPORT_NO = "Report ID";
    public static final String LBL_REPORT_PERIOD = "Report Period";
    public static final String LBL_STATEMENT_PERIOD = "Statement Period";
    public static final String LBL_SUBMISSION_DATE = "Submission Date";
    public static final String LBL_EXCHANGE_RATE1 = "Exchange Rate";
    public static final String LBL_VERSION_NAME = "Agreement Version Name";
    public static final String LBL_DATE_RECEIVED = "Received Date";
    public static final String LBL_INVOICE_NO = "Invoice ID";
    public static final String LBL_PAYMENT_NO = "Payment ID";
    public static final String LBL_PAYMENT_DATE = "Payment Date";
    public static final String LBL_AF_SHARE_NO = "Statement ID";
    public static final String LBL_AF_SHARE_PAYMENT_NO = "Payment ID";
    public static final String LBL_STORE_SUMMARY = "Store Summary";
    public static final String LBL_TRANSACTION_STATUS = "Transaction Status";
    public static final String LBL_JOB = "Job";
    public static final String LBL_JOB_ID = "Job ID";
    public static final String LBL_JOB_NAME = "Job Name";
    public static final String LBL_DATE_START = "Date Started";
    public static final String LBL_DATE_END = "Date Ended";
    public static final String LBL_START_DATE = "Start Date";
    public static final String LBL_END_DATE = " End Date";
    public static final String LBL_INSURANCE_COMPANY = "Insurance Company";
    public static final String LBL_INSURANCE_POLICY_NUMBER = "Insurance Policy Number";
    public static final String LBL_ADJUSTER = "Adjuster";
    public static final String LBL_INSURANCE_CLAIM_NUMBER = "Insurance Claim Number";
    public static final String LBL_DEDUCTABLE = "Deductable";
    public static final String LBL_CHART = "No Data Found";
    public static final String LBL_EMAIL_ID = "Email";
    public static final String PURCHASE_ORDER_NO = "Purchase Order No.";
    public static final String AMOUNT_PAID = "Amount Paid";
    public static final String NO_TIME_SCHEDULED = LanguageUtil.getString("No Time Scheduled");
    public static final String GROUP_RESTORED = LanguageUtil.getString("Group") + " '{0}' " + LanguageUtil.getString("has been restored.");
    public static final String GROUPS_RESTORED = LanguageUtil.getString("Groups") + " '{0}' " + LanguageUtil.getString("has been restored.");
    public static final String Broker_Agency = "Broker Agency";
    public static final String Agency_Name = "Agency Name";
    public static final String PAYMENT_RECEIVED_DATE = "Payment Received Date";
    public static final String PAYMENT_REPORT_DATE = "Payment Report Date";
    public static final String CLIENT_NAME_KEY = "";
    public static final String FRANCHISE_LABEL = "Franchise ID";
    public static final String CORPORATE_LABEL = "Corporate User";
    public static final String MU_LABEL = "Multi Unit Franchisee";
    public static final String ENTITY = "Entity";
    public static final String CREDIT_ID_LABEL = "Memo ID";
    public static final String CREDIT_DATE_LABEL = "Received Date";
    public static final String CREDIT_TYPE_LABEL = "Type";
    public static final String TMS_ACTION_TAB_NAME = "Estimate";
    public static final String DATE = "Date";
    public static final String PURCHASE_ORDER = "PURCHASE ORDER";
    public static final String TMS_TAB_S_POST_FIX = "s";
    public static final String TMS_TAB_D_POST_FIX = "d";
    public static final String TMS_TAB_E_POST_FIX = "e";
    public static final String LBL_USER_TYPE = "User Type";
    public static final String LBL_MILES = "Miles";
    public static final String LBL_AREA_FRANCHISE_ID = "Area / Region";
    public static final String LBL_PERCENT_TOTAL = "% of Total";
    public static final String LBL_STORE_REPORTING = "# of Franchise(s) Reporting";
    public static final String LBL_RANK = "Rank";
    private static String sApplicationHome = "";
    public static final String FIRST_PHONE = "firstPhone";
    public static final String SECOND_PHONE = "secondPhone";
    public static final String FIRST_ATTACHMENT = "firstAttachment";
    public static final String SECOND_ATTACHMENT = "secondAttachment";
    public static final int RECORDS_PER_PAGE = 20;
    public static final int QUIZ_QUESTION_PER_PAGE = 5;
    public static final String SystemPrivilegesKey = "SystemPrivileges";
    public static final int MAX_TRIES = 1;
    public static final int DEFAULT_INT = -1;
    public static String TASK_COMPLETION = "";
    public static final String DEFAULT_INT_STR = "-1";
    public static final String DEFAULT_ZERO_FILL = "0.00";
    public static final String DEFAULT_SCREEN = "NOT_FOUND";
    public static final String MAIN_URL = "/main";
    public static final String CREDITCARD_URL = "https://test.authorize.net/gateway/transact.dll";
    public static final String REDIRECT_URL = "/redirect";
    public static final String MAIN_SCREEN = "MAIN";
    public static final String SYSTEM_VARIABLES = "system-variables";
    public static final String EMAIL_TEMPLATES_XML = "EmailTemplates.xml";
    public static final String LOCALE_COUNTRY_CODE_INDIA = "IN";
    public static final String LOCALE_COUNTRY_CODE_UNITED_STATES = "US";
    public static final String LOCALE_LANGUAGE = "en";
    public static final String LOCALE_COUNTRY_NAME_INDIA = "India";
    public static final String LOCALE_COUNTRY_NAME_UNITED_STATES = "United States";
    public static final String FALSE = "false";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String CONSTANT = "constant";
    public static final String FIN_SETUP_MSG = "Financial application is not setup/configured. Please contact your corporate administrator.";
    public static final String CURRENT_DATE = "currentDate";
    public static final String FILE_NAME = "fileName";
    public static final String BROWSER_TYPE = "browserType";
    public static final String FIELD_SEPARATOR = "-||-";
    public static final String MAX_CUSTOM_FORM_FIELDS = "10";
    public static String FILE_SERVER = getApplicationHome() + "/fileserver";
    public static String SUPPLIER_XML_DIR = getApplicationHome() + "/src/Documents/portalsuppliers";
    public static String LOG_DIR = FILE_SERVER + "/logs";
    public static final int WEB_LOGS_UPDATE_HOUR = 0;
    public static final int WEB_LOGS_UPDATE_MIN = 15;
    public static String CONFIG_DIR = getApplicationHome() + "/config";
    public static String TEMPORARY_FOLDER = FILE_SERVER + "/temp";
    public static final String XSL_DIRECTORY = CONFIG_DIR + "/xsl/";
    public static String DOCUMENTS_DIRECTORY = null;
    public static String SITE_HTML_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitehtmls/";
    public static String SITE_XML_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitexmls/";
    public static String SITE_XSL_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitexsls/";
    public static String IMAGE_DIRECTORY = getApplicationHome() + "/src/";
    public static final String VARIABLE = "variable";
    public static final String INVOICE_TOTAL = "Invoice Total";
    public static final String AMOUNT_RECEIVED = "Amount Received";
    public static final String AMOUNT_DUE = "Amount Due";
    public static final String TERMS_NOT_AVAILABLE = "No Terms & Conditions";
    public static final int CURRENCY_IMAGE_WIDTH = 9;
    public static final int CURRENCY_IMAGE_HEIGHT = 14;
    public static final int CURRENCY_IMAGE_SCH_WIDTH = 7;
    public static final int CURRENCY_IMAGE_SCH_HEIGHT = 10;
    public static final String LBL_SKILLSET = "Skill Set";
    public static final String LBL_SKILLSETS = LBL_SKILLSET + "s";
    public static final String LBL_CAREGIVER_SKILLSET = "Caregiver Skill Set";
    public static final String LBL_CAREGIVER_SKILLSETS = LBL_CAREGIVER_SKILLSET + "s";
    public static final String LBL_SKILL = "Skill";
    public static final boolean isRegionalUser = false;
    public static final String ASD_CLIENT = "1";
    public static final String ASD_CT = "2";
    public static final String ASD_SCH = "3";
    public static final String ASD_CM = "4";
    public static final String LBL_VEHICLE_ID = "Vehicle ID";
    public static final String LBL_VEHICLE_NAME = "Vehicle Name";
    public static final String ASD_DEFAULT = ASD_CLIENT;
    public static final String LBL_CONFIGURABLE_TAB = "Configurable Tab";
    public static final String LBL_ONE_LINE_DESCRIPTION = "One Line Description";
    public static final String LBL_DETAILED_DESCRIPTION = "Detailed Description";
    public static final String LBL_FOOTER = "&copy; 2004-2011, FranConnect Inc. All rights reserved.";
    public static final String SSO_LINK_LBL = "Single Sign On Links";
    public static final String LBL_TROUBLE_TICKET_FRAN = "Ask Corporate";
    public static final String LBL_TROUBLE_TICKET_CORP = "Tickets";
    public static final String LBL_EVENT_NAME = "Event Name";
    public static final String LBL_EVENT_ADDRESS = "Address";
    public static final String LBL_EVENT_CITY = "City";
    public static final String LBL_EVENT_COUNTRY = "Country";
    public static final String LBL_EVENT_STATE = "State";
    public static final String LBL_EVENT_ZIPCODE = "Zip / Postal Code";
    public static final String LBL_EVENT_PHONE_NUMBERS = "Phone";
    public static final String LBL_EVENT_EXTN = "Extn";
    public static final String LBL_EVENT_MOBILE_NUMBERS = "Mobile";
    public static final String LBL_EVENT_EMAIL_IDS = "Email ID";
    public static final String LBL_EVENT_FAX_NUMBERS = "Fax";
    public static final String LBL_TIME_UNIT = "hrs.";
    public static final String LBL_GUEST_NAME = "Name";
    public static final String LBL_GUEST_DOB = "Date of Birth";
    public static final String LBL_GUEST_SEX = "Sex";
    public static final String LBL_GUEST_EXPECTED_ATTENDANCE = "Expected Attendance";
    public static final String LBL_GUEST_AGE = "Age";
    public static final int X_THRESHOLD = 30;
    public static String XML_DIRECTORY = CONFIG_DIR + "/xml/";
    public static String WEB_INF_XML = "";
    //PreetiJ Adding String constants for Intranet Mobile Website
    public static final String CORPORATE_USER="corporateUser";
    public static final String FRANCHISE_USER="franchiseUser";
    public static final String REGIONAL_USER="regionalUser";
    public static final String SUPPLIER="supplier";
    //PreetiJ changes end for Intranet Mobile Website
 
    public static final String ENABLE_LEAD_DAYS="30";  //ZCUB-20150116-096
    public static Map<String,String> socialKeywordMap = new HashMap<String, String>();
    
    public static final HashMap DEFAULT_FIELD_MAPPINGS = new HashMap();
    static
    {
        DEFAULT_FIELD_MAPPINGS.put("count", new Field("count", "COUNT", "Count", Field.DataType.INTEGER, null, null, Field.FieldType.NONE, null, null, null, null, null, null, null, null, null, false, false, false));
        DEFAULT_FIELD_MAPPINGS.put("sum", new Field("sum", "SUM", "Sum", Field.DataType.INTEGER, null, null, Field.FieldType.NONE, null, null, null, null, null, null, null, null, null, false, false, false));
        DEFAULT_FIELD_MAPPINGS.put("avg", new Field("avg", "AVG", "Average", Field.DataType.INTEGER, null, null, Field.FieldType.NONE, null, null, null, null, null, null, null, null, null, false, false, false));
        
        socialKeywordMap.put("Facebook","$FACEBOOK_SOCIAL_URL$");
		socialKeywordMap.put("Twitter","$TWITTER_SOCIAL_URL$");
		socialKeywordMap.put("YouTube","$YOUTUBE_SOCIAL_URL$");
		socialKeywordMap.put("LinkedIn","$LINKEDIN_SOCIAL_URL$");
		socialKeywordMap.put("Pinterest","$PINTEREST_SOCIAL_URL$");
		socialKeywordMap.put("Instagram","$INSTAGRAM_SOCIAL_URL$");
		socialKeywordMap.put("GooglePlus","$GOOGLEPLUS_SOCIAL_URL$");
    }
    public static String adMakerFontsDirectory;
    public static String CREDIT_CARD_CONFIG_FILE = "";
    public static String CYBER_CONFIG_FILE = "";

    public static boolean isDefaultField(String fieldName)
    {
        return DEFAULT_FIELD_MAPPINGS.containsKey(fieldName);
    }

    public static String getApplicationHome()
    {
        return sApplicationHome;
    }

    public static void setApplicationHome(String psApplicationHome)
    {
        sApplicationHome = psApplicationHome;
        FILE_SERVER = sApplicationHome + "/fileserver";
        SUPPLIER_XML_DIR = getApplicationHome() + "/src/Documents/portalsuppliers";
        LOG_DIR = FILE_SERVER + "/logs";
        CONFIG_DIR = sApplicationHome + "/config";
        TEMPORARY_FOLDER = FILE_SERVER + "/temp";
        DOCUMENTS_DIRECTORY = getApplicationHome() + "/src/Documents/";
        SITE_HTML_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitehtmls/";
        SITE_XML_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitexmls/";
        SITE_XSL_DIRECTORY = getApplicationHome() + "/src/Documents/websitebuilder/sitexsls/";
        IMAGE_DIRECTORY = getApplicationHome() + "/src/";
        adMakerFontsDirectory = getApplicationHome().substring(0, getApplicationHome().lastIndexOf("/") + 1) + "adMaker_Fonts/";
        CREDIT_CARD_CONFIG_FILE = CONFIG_DIR + "/ccxml/CreditCardConfig.xml";
        CYBER_CONFIG_FILE = sApplicationHome + "/src/WEB-INF/classes/cybs.properties";
        Calendar cal = Calendar.getInstance();
        OUTLOOK_MAIL_FOLDER = cal.get(Calendar.WEEK_OF_YEAR) + "_" + cal.get(Calendar.YEAR);
        WEB_INF_XML = sApplicationHome + "/src/WEB-INF/xml/";
        XML_DIRECTORY = CONFIG_DIR + "/xml/";
        MULTITENANCY_CONFIG_XML = XML_DIRECTORY + "multitenancy-config.xml";
    }

    public static String MULTITENANCY_CONFIG_XML;
    public static final String SystemVariableManagerKey = "systemVariableManager";
    public static final String CONDITION_AND = "and";
    public static final String KPI = "KPI";
    public static final String CONDITION_OR = "or";
    public static final String OPERATOR_LIKE = "like";
    public static final String OPERATOR_IN = "in";
    public static final String OPERATOR_EQ = ".eq.";
    public static final String OPERATOR_GE = ".ge.";
    public static final String OPERATOR_LE = ".le.";
    public static final String OPERATOR_GT = ".gt.";
    public static final String OPERATOR_LT = ".lt.";
    public static final String TABLE_COLUMN_SEPARATOR = "_tc_";
    public static final String MAP = "map";
    public static final String DATA = "data";
    private static final String[] defaults = { "Select", "------", "......." };

    public static String[] getDefaultComboStrings()
    {
        return defaults;
    }

    public static final String BASE_DIR = sApplicationHome;
    public static String STATIC_KEY = "";
    public static final String TEMPLATE_CONTENT = "template_content";
    public static final String CONNECTION_NAME = "appnetix";
    public static final String TEXTCONVERTER = "false";
    public static final String HTMLCONVERTER = "false";
    public static final String DRIVES_SAVEDIR = "false";
    public static final String HEADHUNTERS_SAVEDIR = "false";
    public static final String DATABASE = "MySql";
    public static final String PrivilegeCategoriesKey = "SystemPrivilegeCategories";
    public static final String ModulesKey = "SystemModules";
    public static final String UserRoleMapKey = "userRoleMap";
    public static final String ScreenManagerKey = "screenManager";
    public static final String SalesReportFactoryKey = "reportFactory";
    public static final String DashboardReportFactoryKey = "dashboardReportFactory";
    public static final String selectedURLKey = "selectedURL";
    public static final String LBL_UNIT_OF_MEASUREMENT = "Unit of Measurement";// P_B_CM_33116
    public static final String CONTENT_TYPE_DOCUMENTS = "contentTypeDocuments";
    public static final String CONTENT_TYPE_NEWS = "contentTypeNews";
    public static final String CONTENT_TYPE_TRAINING = "contentTypeTraining";
    public static final String CONTENT_TYPE_DISCUSSION = "contentTypeDiscussion";
    public static final String IE11_PATTERN = ".*Trident/7.0.* rv:11.0.*";
    public static final String MAILMERGE_TEMPLATE = XML_DIRECTORY + "MergeTemplate.xml";
    public static final String msServer = "localhost";
    public static final String MIME_TYPE_FOR_WEB_DOCUMENT = "-1";
    public static final String DEFAULT_TEMPLATES = "defaultTemplate";
    public static final String LBL_SERVICE_NAME = "Category";
    public static final String LBL_SERVICES_NAME = "Category(s)";
    public static final String PL_LBL_SERVICES_NAME = "Categories";
    public static final String LBL_TEMPLATE_TITLE = "Template Title";
    public static final String LBL_PUBLIC_TEMPLATE = "Public Templates";
    public static final String LBL_PRIVATE_TEMPLATE = "Private Templates";
    public static final String TEXTAREA_STYLE = "style='height:600px; width:100%'";
    public static final String VENUE_DISPLAY_NAME = "Venue";
    public static final String EVENT_DISPLAY_NAME = "Event";
    public static final String COMPANY_DISPLAY_NAME = "Company";
    public static final String COMPANY_SMALL_DISPLAY_NAME = "business";
    public static final String COMPANY_PLURAL_DISPLAY_NAME = "Businesses";
    public static final String TASK_DISPLAY_NAME = "Task";
    public static final String TASK_SMALL_DISPLAY_NAME = "task";
    public static final String JOB_DISPLAY_NAME = "Job";

    public static String LBL_CAP_REPORT = "Corrective Action Plan(CAP) Report ";  //PW_ENH_CAP_REPORT
    
    public static final String LBL_JOB_TYPE = "Job Type";
    public static final String LBL_INV_TASKS = "Tasks";
    public static final String LBL_INV_JOB = "Job";// added by shubham for bug
    public static final String LBL_INV_TASK = "Task";// added by shubham for bug
    // P_B_ADMIN_42149 starts
    public static final String LBL_INV_RATE_DOLLAR = "Rate ($)";
    public static final String LBL_INV_RATE_NEW = "External Price / Rate";
    public static final String LBL_ACTUAL_PRICE = "Internal Cost";
    public static final String LBL_ACTUAL_PRICE_NEW = "Internal Price";
    public static final String LBL_SALE_PRICE = "Sale Price";
    public static final String UPSELL = "UpSell Commission";
    public static final String LBL_INV_TERMS_CONDITIONS = "Terms and Conditions";
    /* END: TMS_INVOICE_LABELS */
    public static final String UNASSIGNED_TASK_USER_ID = "111111111";
    public static final String LBL_UNASSIGNED = "Unassigned";
    public static final String USER_LEVEL_FRANCHISEE = "1";
    public static final String USER_LEVEL_DIVISION = "6";//NEW_USER_LEVEL_HIERARCHY
    public static final String USER_LEVEL_CORPORATE = "0";
    public static final String LBL_CORPORATE_USER = "Corporate User";
    public static final String LBL_FRANCHISEE_USER = "Franchisee User";
    public static final String LBL_CORPORATE_OWNER = "Corporate Owner";
    public static final String LBL_FRANCHISEE_OWNER = "Franchisee Owner";
    // SC_Setting_14102008, Starts.
    public static final String SETTING_DISPLAY_NAME = "Options";
    public static final String NAME_FORMAT_L_F = "L_F";
    public static final String NAME_FORMAT_Lc_F = "Lc_F";
    public static final String NAME_FORMAT_F_L = "F_L";
    public static final String NAME_FORMAT_Fc_L = "Fc_L";
    public static final String NAME_FORMAT_F_M_L = "F_M_L";
    // Added By Niraj Sachan Date 15 nov 07
    // public static final String message = "Please confirm whether you want to
    // ";
    public static final String message = "Are you sure you want to ";
    public static final String LBL_SSO = "Single Sign On";
    public static final String SPACER_WIDTH = "25";
    public static final String STATE_LABEL_WITH_S = "States / Provinces";
    public static final String STATE_LEBEL = "State / Province";
    public static final String ZIPCODE_LEBEL = "Zip / Postal Code";
    public static final String ZIPCODES_LEBEL = "Zip / Postal Codes";
    public static final String LBL_ADD_LEAD_KILLED_REASON = "Add Lead Killed Reason";
    public static final String LBL_MODIFY_LEAD_KILLED_REASON = "Modify Lead Killed Reason";
    public static final String LBL_NO_LEAD_KILLED_REASON = "No Lead Killed Reason found.";
    // public static final String LEAD_KILLED_REASON_MESSAGE =
    // "Indicates that respective Lead Killed Reason cannot be deleted as some leads are associated with it.";//36825
    public static final String LEAD_KILLED_REASON_MESSAGE = "Indicates that some leads are associated with the Lead Killed Reason. Hence, it cannot be deleted.";
    public static final String LBL_CONFIG_LEAD_CATEGORY = "Configure Lead Category";
    public static final String LBL_ADD_LEAD_CATEGORY = "Add Lead Category";
    public static final String LBL_MODIFY_LEAD_CATEGORY = "Modify Lead Category";
    public static final String LBL_CONFIG_LEAD_TYPE = "Configure Lead Type";
    public static final String LBL_ADD_LEAD_TYPE = "Add Lead Type";
    public static final String LBL_MODIFY_LEAD_TYPE = "Modify Lead Type";
    public static final String LBL_CONFIG_LEAD_KILLED_REASON = "Lead Killed Reason";
    public static final String LBL_NO_LEAD_CATEGORY = "No Lead Category found.";
    public static final String LBL_NO_LEAD_TYPE = "No Lead Type found.";
    public static final String LEAD_CATEGORY_MESSAGE = "Indicates that respective Lead Category cannot be deleted as some leads are associated with it.";
    public static final String LEAD_TYPE_MESSAGE = "Indicates that respective Lead Type cannot be deleted as some leads are associated with it.";
    // P_E_FCKEditor Added By Nikhil Verma
    public static final String FCK_EDITOR = "FCK Editor";
    public static final String AREA_LABEL = "Area / Region";
    // Constants defined for column type ---
    public static final String EXCEL_COLUMN_TYPE_TEXT = "T";
    public static final String EXCEL_COLUMN_TYPE_DATE = "D";
    public static final String EXCEL_COLUMN_TYPE_NUMBER = "N";
    public static final String EXCEL_COLUMN_TYPE_INT_NUMBER = "IN";
    public static final String EXCEL_COLUMN_TYPE_FLOAT_NUMBER = "FN";
    public static final String FLOAT_DATA = "Float";
    public static final String INT_DATA = "integer";
    public static final String FORM_TARGET_NEW = "new";
    public static final String LBL_START_TIME = "Start Time";
    public static final String LBL_END_TIME = "End Time";
    // Constant define for alligment
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String ALIGN_LEFT = "LEFT";
    public static final String INVOICE_DATE = "Invoice Date";
    public static final String ESTIMATE_DATE = "Estimate Date";
    public static final String INVOICE_ID = "Invoice ID";
    public static final String AMOUNT = "Amount";
    public static final String DESCRIPTION = "Description";
    public static final String ADJUSTMENT_AGAINST = "Adjustment Against";
    /* ===================Fin>Gen.Exl.report.Util================== */
    public static final String EXCEL_REPORT = "excelreport";
    public static final String EXCEL_REPORT_FORWARD = "excelReportForward";
    public static final String FORM_FOR_EXCEL_REPORT = "formforexcel";
    public static final String CURRENCY_NOTES = "currencyNotes";
    public static final String RIGHT_SIDE_NOTES = "rightSideNotes";
    public static final String FOOTER_NOTES = "footerNotes";
    public static final String DELIMITER_COLUMN_ALIGN = "##";
    public static final String DELIMITER_DOUBLE_COLON = "::";
    public static final String EXCEL_REPORT_COLUMN_ALIGN_LEFT = Constants.DELIMITER_COLUMN_ALIGN + "L";
    public static final String EXCEL_REPORT_COLUMN_ALIGN_RIGHT = Constants.DELIMITER_COLUMN_ALIGN + "R";
    public static final String EXCEL_REPORT_COLUMN_ALIGN_CENTRE = Constants.DELIMITER_COLUMN_ALIGN + "C";
    /* ============================================================== */
    /* DemoBuild/web/css/style.css */
    /* These constants are to get RGB/# parameters for Colors used in Builds */
    // P_FIN_B_4812 starts
    public static final String RGB_tb_data_dark = "238, 239, 239";// #EEEFEF
    // public static final String RGB_tb_data_dark_new =
    // "234, 241, 188";//#EAF1BC
    public static final String RGB_tb_data_dark_new = "250, 224, 127";
    // public static final String HASH_tb_data_dark = "#EEEFEF";
    public static final String RGB_tb_data_dark_red = "243, 197, 197";// #F3C5C5
    // RGB_tb_data_dark_red_new modified by rittika on 3/02/10
    // public static final String RGB_tb_data_dark_red_new =
    // "247, 197, 197";//#f3c5c5
    // public static final String RGB_tb_data_dark_red_new =
    // "223, 192, 162";//#DFC0A2
    public static final String RGB_tb_data_dark_red_new = "244, 178, 128";
    public static final String RGB_tb_data_dark_green = "125, 196, 163";// #7DC4A3
    // public static final String RGB_tb_data_dark_green_new =
    // "208, 223, 217";//#D0DFD9
    public static final String RGB_tb_data_dark_green_new = "231, 231, 231";// #D0DFD9
    public static final String RGB_tb_data_dark_blue = "191, 216, 255";// #BFD8FF
    // public static final String RGB_tb_data_dark_blue_new =
    // "255, 204, 153";//#ffcc99
    public static final String RGB_tb_data_dark_blue_new = "176, 221, 159";// #ffcc99
    // public static final String RGB_tb_data_fog_new = "203, 202, 182";
    // //#CBCAB6 //P_FIN_B_76687
    public static final String RGB_tb_data_fog_new = "202, 202, 245";
    // P_FIN_B_4812 ends
    /* ============================================================== */
    public static final String LBL_EXCHANGE_RATE = "Exchange Rates";
    public static final String LBL_CURRENCY_RATE = "Currency Rate as of";
    public static final int EXCHANGE_RATE_LENGTH_BEFORE_POINT = 12;
    public static final int EXCHANGE_RATE_LENGTH_AFTER_POINT = 10;
    public static final String FRANCHISE_ID_NOTIFICATION = "Franchise IDs marked with * are terminated.";
    public static final String FRANCHISEE_TERMINATION_MESSAGE = "Franchise IDs marked with <span class='urgent_fields'>*</span> are terminated.";
    /* ============================================================== */
    // Pet Name Added By Saurabh Vaish
    public static final String LBL_PET_NAME = "Name";
    public static final String LBL_PET_BREED = "Breed";
    public static final String LBL_PET_TYPE = "Type";
    public static final String LBL_PET_AGE = "Age";
    public static final String LBL_PET_SEX = "Sex";
    public static final String LBL_PET_WEIGHT = "Weight";
    public static final String LBL_SKIN_COAT_CONDITION = "Skin Coat Condition";
    public static final String LBL_HEALTH_CONDITION = "Health Condition";
    public static final String LBL_PET_DISPOSITION = "Pet Discomposition";
    public static final String LBL_BREED_DESCRIPTION = "Breed Description";
    public static final String SRC_TASK = "T";
    public static final String SRC_CONTACT = "C";
    public static final int MAX_ROWS = 25;
    public static final String EC_PET = "ecPet";
    public static final String DATASET_PET_SERVICE = "datasetPetService";
    public static final String LBL_SERVICES = "Services";
    public static final String TYPE_COMBO = "abctypeCombocba";
    public static final String TYPE_TEXT = "abctypeTextcba";
    public static final String TSK_APPOINTMENT = "1";
    public static final String TSK_FOLLOWUP = "2";
    public static final String LBL_INQUIRY_MEDIUM = "Inquiry Medium";
    public static final String TIME_START = "9";
    public static final String TIME_END = "6";
    public static final String DB_VAL_SEPERATE_CONTACT_TYPE = "0";
    public static final String DB_VAL_SINGLE_CONTACT_TYPE = "1";
    public static final String SUBTAB_RESOURCE_MANAGEMENT = "Resource Management";
    public static final int MAX_No_ROWS = 65000;// Added by NishantP
    public static final String CONTACT_TYPE = "Brokers";
    public static final String CONTACT_TYPEWOS = "Broker";
    public static final boolean isUnassignedUser = true;
    public static final String ROW_CLASS_CORPORATE = "tb_data_r";
    public static final String ROW_CLASS_FRANCHISEE = "tb_data_orange";
    public static final String NAV_CODE_CONTACT_CORPORATE = "1";
    public static final String NAV_CODE_CONTACT_FRANCHISEE = "2";
    public static final String NAV_CODE_CONTACT_NONE = "3";
    public static final String NAV_CODE_FOLLOWUP_CORPORATE = "4";
    public static final String NAV_CODE_FOLLOWUP_FRANCHISEE = "5";
    public static final String LBL_CORPORATE = "Corporate";
    public static final String LBL_FRANCHISEE = "Franchisee";
    public static final String SESSION_STAR_FOLLOWUP = "0";
    public static final String SESSION_STAR_CONTACT = "1";
    public static final String SESSION_STAR_SCHEDULE = "2";
    public static final String LBL_ZIPCODE = "Zip / Postal Code";
    public static final String LBL_CONTACT_TYPE = "Contact Type";
    public static final String LBL_ADDRESS = "Address";
    public static final String LBL_CITY = "City";
    public static final String LBL_STATE = "State";
    public static final String LBL_OPEN_BALANCE = "Open Balance";
    public static final String LBL_SELECT = "Select";
    public static final String LBL_OWNER_TYPE = "Contact Owner Type";
    public static final String LBL_PHONE1 = "Phone1";
    public static final String LBL_PHONE2 = "Phone2";
    // EXTN :Starts
    public static final String LBL_PHONE_EXTENSION = "Phone Extension";
    // EXTN :Ends
    public static final String LBL_MOBILE = "Mobile";
    public static final String LBL_EMAILID = "Email ID";
    public static final String LBL_EVENT = "Event";
    public static final String AVAIL_COLOR_AVAILABILE = "tb_data_dark_green";
    public static final String AVAIL_COLOR_NOT_AVAILABILE = "tb_data_orange";
    public static final String USER_COLOR_AVAILABILE = "tb_data_available";
    public static final String USER_COLOR_NOT_AVAILABILE = "tb_data_notavailable";
    public static final String HOLIDAY_COLOR = "tb_data_holiday";
    public static final String LBL_ADD_DATE = "Add Date";
    public static final String LBL_CONTACT_OWNER = "Contact Owner";
    public static final String BULK_CAMPAIGN_MASTER_PASSWORD = "Bulk Campaign Master Password";
    public static final String LBL_FIRST_CHOICE = "First Choice";
    public static final String LBL_SECOND_CHOICE = "Second Choice";
    public static final String LBL_FIRST_DATE_CHOICE = "First Date Choice";
    public static final String LBL_SECOND_DATE_CHOICE = "Second Date Choice";
    public static final String LBL_TIME_ZONE = "Time Zone";
    public static final String LBL_TIME = "Time";
    public static final boolean IS_TZ = true;
    public static final String STATUS_DRIVEN_LABEL = "Status-Driven";
    public static final String ADJUSTMENT_NOTIFICATION = "This report is an adjustment report.";
    public static final String LBL_COUNTRY = "Country";
    public static final String LBL_CURR_NAME = "Currency Name";
    public static final String LBL_RATE = "Rate";
    public static final String CURRENCY_TYPE_LOCAL = "LOCAL";
    public static final String CURRENCY_TYPE_USD = "USD";
    public static final String CURRENCY_TYPE = "currencytype";
    public static final String XE_ENABLED = "1";
    public static final String XE_DISABLED = "0";
    public static final String LBL_XE = "Xe.com";
    public static final String LINK_TYPE = "linkForPageSet";
    public static final String LINK_TYPE_TOP = "Top";
    public static final String LINK_TYPE_ALL = "All";
    public static final String MANUAL_EXG_ROW_CLASS = "tb_data";
    public static final String AUTO_EXG_ROW_CLASS = "tb_data_r";
    public static final String XE_ENABLED_CLASS = "tb_data_r";
    public static final String XE_DISABLED_CLASS = "tb_data";
    public static final String LBL_AUTO_ENTRY = "Xe.com Entry";
    public static final String LBL_MANUAL_ENTRY = "User Entry";
    public static final String AUTO_ENTRY = "0";
    public static final String MANUAL_ENTRY = "1";
    public static final String LBL_EXCHANGE_RATES = "Exchange Rates";
    public static final String VIEW_LINK_TYPE_ALL = "View All";
    public static final String VIEW_LINK_TYPE_TOP = "View Top";
    public static final String VIEWIN_USD = "View in USD";
    public static final String VIEWIN_LOCAL = "View in Local";
    public static final String LBL_XE_ENABLED = LBL_XE + " Enabled";
    public static final String LBL_XE_DISABLED = LBL_XE + " Disabled";
    public static final String LBL_CURR_CODE = "Currency Code";
    public static final String PDF_VALUE_SIZE = "10";
    public static final String PDF_VALUE_HEADER_SIZE = "12";
    public static final String PDF_VALUE_TITLE_SIZE = "11";
    public static final String SALES_WEB_FORM = "0";
    public static final String SALES_CSV_UPLOAD = "1";
    public static final String SALES_XLS_UPLOAD = "2";
    public static final String EXG_ENABLE = "Y";
    public static final String EXG_DISABLE = "N";
    public static final String ADJ_ENABLE = "1";
    public static final String ADJ_DISABLE = "0";
    public static final String PREVIOUS_MANDATORY = "1";
    public static final String NO_RESPONSE = "noResponse";
    public static final String CURRENCY = "currency";
    public static final String GENERAL = "general";
    public static final String NUMBER = "number";
    public static final String BASE_MARKER = "baseMarker";
    public static final String COUNTRY_CURRENCY_CODES = "Country Currency Codes";
    public static final String LBL_XLS = ".xls";
    public static final String LBL_CSV = ".csv";
    public static final String UNITS_PER_POUND = "Exchange Rates (Units per UK Pound)";
    public static final String UK_POUND_PER_UNIT = "UK Pounds per Unit";
    public static final String UNITS_PER_USD = "Exchange Rates (Units per USD)";
    public static final String USD_PER_UNIT = "USD per Unit";
    public static final String LBL_FRANCHISEE_ID = "Franchisee ID";
    public static final String LBL_FRANCHISEE_NAME = "Franchisee Name";
    public static final String SALES_PERSON = "Sales Person";
    public static final String LBL_SALES_PERSON = "Salesperson";
    public static final String LBL_TITLE = "Title";
    public static final String labelFranchisee = "Franchisee ID";
    public static final String LBL_ACTION = "Take Action";
    public static final String LBL_SALES_PERSON_SALES = "Salesperson Sales";
    public static final String LBL_FIRST_NAME = "First Name";
    public static final String LBL_LAST_NAME = "Last Name";
    public static final String LBL_REPORT_MONTH_FROM = "Salesperson Sales From";
    public static final String LBL_REPORT_MONTH_TO = "Salesperson Sales To";
    public static final String LBL_AMOUNT_LOCAL = "Amount (Local Currency)";
    public static final String LBL_AMOUNT_SP_SALES = "Amount";
    public static final String LBL_REPORT_MONTH = "Report Month";
    public static final String RANK = "Rank";
    public static final String LBL_FRAN_NAME = "Franchisee ID";
    public static final String LBL_FS_QAUALIFICATION = "Qualification";
    public static final String LBL_FS_CHECKLIST = "Checklist";
    public static final String LBL_FS_QAUALIFICATIONCHECKLIST = LBL_FS_QAUALIFICATION + " " + LBL_FS_CHECKLIST;
    public static final String LBL_SUPP_DESCRIPTION_HEADER = "Descriptive information";
    public static final String LBL_SUPP_DESCRIPTION = "Notes";
    public static final String LBL_STORE_NO = "Store Number";
    public static final String LBL_USER_ID = "User ID";
    public static final String LBL_PASSWORD = "Password";
    public static final String LBL_CONFIRM_PASSWORD = "Confirm Password";
    public static final String LBL_CREDITCARD_PAYMENT_GATEWAY = "Credit Card Payment Gateway";
    public static final String LBL_UPS_ONLINE_SHIPPING_TOOL = "UPS Online Shipping Tool";
    public static final String NO_COLSPAN = "NoColspan";
    public static final String TRIM_ME_JS = "trimMeJs";
    public static final String LBL_TIME_SLOT_MORNING = "Morning before 12 PM";
    public static final String LBL_TIME_SLOT_NOON = "Mid-day 12 PM - 3 PM";
    public static final String LBL_TIME_SLOT_EVENING = "Afternoon 3 PM - 6 PM";
    public static final String LBL_TIME_SLOT_NIGHT = "Evening 6 PM - 11 PM";
    public static final String ASSIGN_STATUS_NORMAL = "0";
    public static final String ASSIGN_STATUS_UNASSIGNED = "1";
    public static final String ASSIGN_STATUS_ASSIGNED = "2";
    public static final String TH_SCH_VIEW_MAIN = "0";
    public static final String TH_SCH_VIEW_VERT15 = "1";
    public static final String TH_SCH_VIEW_VERT30 = "2";
    public static final String TH_SCH_VIEW_TASK = "3";
    public static final String TH_SCH_FREQ_DAY = "0";
    public static final String TH_SCH_FREQ_WEEK = "1";
    public static final String TH_SCH_FREQ_MONTH = "2";
    public static final String LBL_TH_SCH_VIEW_MAIN = "Main View";
    public static final String LBL_TH_SCH_VIEW_VERT15 = "Vertical View (15 Min)";
    public static final String LBL_TH_SCH_VIEW_VERT30 = "Vertical View (30 Min)";
    public static final String LBL_TH_SCH_VIEW_TASK = "Task View";
    public static final String SCH_VIEW_MODE_USER = "0";
    public static final String SCH_VIEW_MODE_THEATER = "1";
    public static final String DISPATCH_SCH_VIEW_MODE_USER = "0";
    public static final String DISPATCH_SCH_VIEW_MODE_THEATER = "1";
    public static final String LBL_EVENT_DATE = "Event Date";
    public static final String LBL_EVENT_CREATION_DATE = "Event Creation Date";
    public static final String LBL_GUEST_CAPACITY = "Guest Capacity";
    public static final String LBL_TRUCK_ID = "Truck ID";
    public static final String VTYPE_TAG_ALONG = "0";
    public static final String VTYPE_FIFTH_WHEEL = "1";
    public static final String LBL_VTYPE_TAG_ALONG = "Tag Along";
    public static final String LBL_VTYPE_FIFTH_WHEEL = "Fifth Wheel";
    public static final String LBL_DURATION = "Duration";
    public static final String LBL_ASSIGN_STATUS_NORMAL = "Manual";
    public static final String LBL_ASSIGN_STATUS_UNASSIGNED = "Web Form";
    public static final String DATE_CHOICE_NONE = "0";
    public static final String DATE_CHOICE_MORNING = "1";
    public static final String DATE_CHOICE_NOON = "2";
    public static final String DATE_CHOICE_EVENING = "3";
    public static final String DATE_CHOICE_NIGHT = "4";
    public static final String LBL_LICENSE_NO = "License No";
    public static final String LBL_VEHICLE_COMPANY_NAME = "Vehicle Company Name";
    public static final String LBL_VEHICLE_CONDITION = "Vehicle Condition";
    public static final String TASK_STATUS_ID_NOT_STARTED = "637";
    public static final String TASK_STATUS_ID_COMPLETED = "632";
    public static final String TASK_STATUS_ID_CANCELLED = "53137822, 636";
    public static final String DTC_FOLLOWUP = "1";
    public static final String DTC_CONTACT = "2";
    public static final String DTC_SCHEDULE = "3";
    public static final int TR_START_HOUR = 0;
    public static final int TR_START_MINUTE = 1;
    public static final int TR_START_12_FORMAT = 2;
    public static final int TR_END_HOUR = 3;
    public static final int TR_END_MINUTE = 4;
    public static final int TR_END_12_FORMAT = 5;
    public static final int TR_EVENT_DATE = 6;
    public static final String FORMAT12_AM = "0";
    public static final String FORMAT12_PM = "1";
    public static final String LBL_ASSIGNED_TO_MULTI = "Assign To Multiple Users";
    public static final String CODE_ADD_GUEST_OF_HONOR = "code93874";
    public static final String LBL_NOT_AVAILABLE = "Not Available";
    public static final String FIN_SALES = "finSales";
    public static final String FIN_CORPORATE_SALES = "Manage Corporate Sales";
    public static final String FIN_CT_INT_CORPORATE_SALES = "CtFinIntCorpSales";
    public static final String FRANCHISE_AUDIT = "PerformanceWise";
    public static final String SMS_DEFAULT_ACCOUNT_NAME = "Configure SMS Settings";
    public static final String AD_BUILDER = "adbuilder";
    public static final String DEFAULT_MATCH_TYPE = "55"; // P_CM_B_39478
    public static final String CM_TMS = /* ModuleUtil.tmsImplemented()?"": */"CM_";
    public static final String FRAN_FORM_DATA_1B = "formData1b";
    public static final String FRAN_FORM_DATA_2B = "formData2b";
    public static final String FRAN_TOP_NAV_TEXT_BOLD = "topNavTextBold";
    public static final String FRAN_BOLD = "bold";
    public static final String FRAN_WHITE_ARROW = "whitearrow";
    public static final String FRAN_VIOLET_BOLD_TEXT = "violetBoldText";
    public static final String FRAN_DARKCURVE_BG = "darkCurveBG";
    public static final String FRAN_DARK_BG_HDR = "darkBGHdr";
    public static final String FRAN_LIGHT_BG1 = "lightbg1";
    public static final String FRAN_LIGHT_BG2 = "lightbg2";
    public static final String FRAN_LIGHT_BG3 = "lightbg3";
    public static final String FRAN_LIGHT_BG4 = "lightbg4";
    public static final String FRAN_LEFT_NAV_TEXT = "lftNavText";
    public static final String FRAN_LEFT_NAV_ICON1 = "lftNavIcon1";
    public static final String FRAN_LEFT_LINK_TEXT1 = "lftNavLinkText1";
    public static final String FRAN_LEFT_LINK_TEXT2 = "lftNavLinkText2";
    public static final String FRAN_NAV_ICON2 = "lftNavIcon2";
    public static final String FRAN_MEMBER_PHOTO_HDR = "memberphotoHdr";
    public static final String FRAN_MEMBER_PHOTO_BG = "memberphotoBG";
    public static final String FRAN_NAV_TEXT_WHITE = "lftNavTextwhite";
    public static final String FRAN_LIGHT_BG1_WHITE = "lightbg1white";
    public static final String FRAN_DATA_BOLD2 = "formDataBold2";
    public static final String FRAN_DROPDOWN_LIST = "dropdown_list";
    public static final String NEW_CONSTRUCTION_ID = "11";
    public static final String FIN_MODULE = "FIN";
    public static final String NEW_CONST_SLAB_NO = "200";
    public static final String BASE_FEE_SLAB_NO = "3";
    public static final String LBL_PHOTO = "Photo";
    public static final int ROWS_TO_DISPLAY = 10;
    public static final String INQUIRY_DATE_LABEL = "Inquiry Date";
    public static final String STATUS_DATE_LABEL = "Status Change Date";
    public static final String AWARDED_DATE_LABEL = "Franchise Awarded Date";
    public static final String LEAD_KILLED_DATE_LABEL = "Lead Killed Date";
    public static final String TRIGGER_NAME = "Trigger Name";
    public static final String TRIGGER_TYPE = "Based On";
    public static final String DAYS_DIFFERENCE = "Days Difference";
    public static final String DAYS_FILTER = "Send Campaign Mail On";
    public static final String CM_TRIGGER_HEADER = "Setup Campaign Triggers";
    public static final String CM_TRIGGER_CONTACT_STATUS = "Status";
    public static final String CM_TRIGGER_CAMPAIGN_NAME = "Campaign";
    public static final String CM_TRIGGER_MASTER_CAMPAIGN_NAME = "Master Campaign";
    public static final String OWNER_NAME = "Owner Name";
    public static final String BOTH = "Show Source and Source Details both";
    public static final String SOURCE_ONLY = "Show Source only";
    public static final String DETAILS_ONLY = "Show Source Details only";
    public static final String SOURCE_COUNT = "sourceCount";
    public static final String SOURCE_FILTER = "sourceFilter";
    public static final String SOURCE_NAME = "sourceName";
    public static final String DETAIL_COUNT = "detailCount";
    public static final String DETAIL_FILTER = "detailFilter";
    public static final String DETAIL_NAME = "detailName";
    public static final String SIZE = "size";
    public static final String LBL_SPACER = " ";
    public static final String PREFORMATTED = "Preformatted";
    public static final String HTML = "HTML";
    public static final String HTML_UPLOAD = "HTML File Upload";
    public static final String HTTP = "http://";
    public static final String LBL_CONTACTS = "Contacts";
    public static final String LBL_LEAD = "Lead";
    public static final String SEPARATOR = "@@";
    public static String OUTLOOK_MAIL_FOLDER = "";
    public static final String[] EMPTY_STRING_ARRAY = {};
    public static final String QUICK_BOOK_PREFIX = "QB";
    public static final String PROFILE_PAGE_LOCATION = "ppcProfile";
    public static final String WELCOME_PAGE_LOCATION = "ppcWelcome";
    public static final int CG_SHARE_PERCENT = 10;
    public static final String IS_MANUALLY_ADDED = "";
    public static final String IS_IMPORT_ENABLED = "";
    public static final String IS_EXTERNAL_ENABLED = "";
    public static final int auditDashboardVisitRecords = 20;
    public static final String EMOTION_DIR = "/mvnplugin/mvnforum/images/emotion/";
    public static final String UPLOAD_MAIL_CAB_VERSION = "2,0,0,0";//dki-20150715-461
    public static final String FORM_ENCODING = "accept-charset=\"UTF-8\"";
    public static final StringBuffer STRINGBUFFER = new StringBuffer();
    public static final String LBL_ACTIVITY_CODE = "Activity Code";
    public static final String LBL_CONTACT_ACTIVITY_CODE = "Contact Activity Code";
    public static final String FRANCHISEE_WEBSITE = "Local Websites";
    public static final boolean WEBSITE_LEADS = true;
    public static final String WEB_ANALYTICS = "webanalytics";
    public static final String ALL_FOR_CORP_VALUE = "4222222222";
    public static final String TIME_COLUMN_VALUE = "AVERAGE_TIME_ON_SITE";
    public static final String LBL_ADD = "Add";
    public static final String LBL_MODIFY = "Modify";
    public static final String LBL_DELETE = "Delete";
    public static final String LBL_CANCEL = "Cancel";
    public static final String LBL_CLOSE = "Close";
    public static final String LBL_PRINT = "Print";
    public static final double BUNDLED_MINUTES = 10;
    public static final boolean ENABLE_SMARTCONNECT = true;
    public static final String TRAINING = "training";
    public static final String AMAZON_LOGIN_URL = "https://www.amazon.com/ap/signin?openid.assoc_handle=aws&openid.return_to=https%3A%2F%2Fsignin.aws.amazon.com%2Foauth%3Fresponse_type%3Dcode%26client_id%3Darn%253Aaws%253Aiam%253A%253A015428540659%253Auser%252Fs3%26redirect_uri%3Dhttps%253A%252F%252Fconsole.aws.amazon.com%252Fs3%252Fhome%253Fregion%253Dus-west-2%2526state%253DhashArgs%252523%2526isauthcode%253Dtrue%26noAuthCookie%3Dtrue&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&action=&disableCorpSignUp=&clientContext=&marketPlaceId=&poolName=&authCookies=&pageId=aws.ssop&siteState=awscustomer&accountStatusPolicy=P1&sso=&openid.pape.preferred_auth_policies=MultifactorPhysical&openid.pape.max_auth_age=120&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&server=%2Fap%2Fsignin%3Fie%3DUTF8&accountPoolAlias=&forceMobileApp=0&forceMobileLayout=0";
    public static final String SKY_LOGIN_URL = "https://skydrive.live.com/";
    public static final String GOOGLE_LOGIN_URL = "https://drive.google.com/drive";
    public static final String INTRANET = "intranet";
    public static final String SESSION = "session";
    public static final String REQUEST = "request";
    public static final String STORE_LIST = "Store List";
    public static final String STORE_NO = "Store No.";
    public static final String NO_OF_OVERDUE_TASKS = "# OT";
    public static final String RESPONSIBILITY_AREA = "Responsibility Area";
    public static final String CONTACT = "Contact";
    public static final String SCHEDULE_START = "Schedule Start";
    public static final String SCHEDULE_COMPLETION = "Schedule Completion";
    public static final String COMPLETION_DATE = "Completion Date";
    public static final String EQUIPMENT = "Equipment";
    public static final String TASK_SEARCH = "Task Search";
    public static final String EQUIPMENT_SEARCH = "Equipment Search";
    public static final String DOCUMENT_SEARCH = "Document Search";
    public static final String PICTURE_SEARCH = "Picture Search";
    public static final String ITEM_NAME = "Item Name";
    public static final String DOCUMENT = "Document";
    public static final int MAX_ADDED_ADDRESS = 5;
    public static final String BOEFLY_TAB_NAME = "fsBqual";
    public static final String BOEFLY_TABLE_ANCHOR = "fsBqual";
    public static final String USER_LEVEL_MESSAGE = "userLevelMessage";
    public static final String FC_ID = "fcId";
    public static final String LBL_ACTIVITY_CODES = "Activity Codes";
    public static final String STATUS_AFO_SELETED_ONLY = "3";
    public static final String ROW_CLASS_LOCKED = "tb_data_dark_green";
    public static final String ROW_CLASS_DISPLAY_ONLY = "tb_data_orange";
    public static final String ROW_CLASS_SELECTED_ONLY = "tb_data_g";
    public static final String STATUS_AFO_DISPLAY_ONLY = "2";
    public static final String STATUS_AFO_UNLOCKED = "0";
    public static final String STATUS_AFO_LOCKED = "1";
    public static final String LBL_ACTIVITY_FORMS = "Activity Forms";
    public static final String LBL_FORM_NAME = "Form Name";
    public static final String LBL_ACTIVITY_FORM = "Activity Form";
    public static final String LBL_GROUP = "Group";
    public static final String LBL_FOLLOWUP_MAIL_TEMPLATE = "Followup Mail Template";
    public static final String LBL_LEAD_SOURCE_DETAILS = "Lead Source Details";
    public static final String LBL_CONTACT_CATEGORY_DETAILS = "Contact Source Details";
    public static final String LBL_EMAIL_BCC = "Email Bcc";
    public static final String LBL_ACTIVITY_DATE = "Activity Date";
    public static final String MAIL_SEARCH_BOX_TEXT = "Search by Subject, Recipient or Sender";
    public static final String GCM_SEND_ENDPOINT = "https://gcm-http.googleapis.com/gcm/send";
    public static final String PARAM_REGISTRATION_ID = "registration_id";
    public static final String PARAM_COLLAPSE_KEY = "collapse_key";
    public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";
    public static final String PARAM_DRY_RUN = "dry_run";
    public static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";
    public static final String PARAM_PAYLOAD_PREFIX = "data.";
    public static final String PARAM_TIME_TO_LIVE = "time_to_live";
    public static final String ERROR_QUOTA_EXCEEDED = "QuotaExceeded";
    public static final String ERROR_DEVICE_QUOTA_EXCEEDED = "DeviceQuotaExceeded";
    public static final String ERROR_MISSING_REGISTRATION = "MissingRegistration";
    public static final String ERROR_INVALID_REGISTRATION = "InvalidRegistration";
    public static final String ERROR_MISMATCH_SENDER_ID = "MismatchSenderId";
    public static final String ERROR_NOT_REGISTERED = "NotRegistered";
    public static final String ERROR_MESSAGE_TOO_BIG = "MessageTooBig";
    public static final String ERROR_MISSING_COLLAPSE_KEY = "MissingCollapseKey";
    public static final String ERROR_UNAVAILABLE = "Unavailable";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String ERROR_INVALID_TTL = "InvalidTtl";
    public static final String TOKEN_MESSAGE_ID = "id";
    public static final String TOKEN_CANONICAL_REG_ID = "registration_id";
    public static final String TOKEN_ERROR = "Error";
    public static final String JSON_REGISTRATION_IDS = "registration_ids";
    public static final String JSON_PAYLOAD = "data";
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_FAILURE = "failure";
    public static final String JSON_CANONICAL_IDS = "canonical_ids";
    public static final String JSON_MULTICAST_ID = "multicast_id";
    public static final String JSON_RESULTS = "results";
    public static final String JSON_ERROR = "error";
    public static final String JSON_MESSAGE_ID = "message_id";
    public static final int PPC_LEAD_PER_PAGE = 5;
    public static final String INTRANET_TT = "Communicate and Share Information with Other Users";
    public static final String INT_HOME = "Take a Quick Overview of Latest Updates";
    public static final String INT_DIRECTORY = "View Details of Users, Groups and Suppliers";
    public static final String INT_DIRECTORY_NO_SUPPLIERS = "View Details of Suppliers";
    public static final String INT_ALERTS = "Manage Alerts";
    public static final String INT_ALERTS_FRANCHISEE = "View Alerts";
    public static final String INT_MESSAGES = "Manage Communication Over Messages";
    public static final String INT_CALENDAR = "Manage Appointments/Meetings/Events";
    public static final String INT_CALENDAR_FRANCHISEE = "Manage Your Appointments";
    public static final String INT_NEWS = "View and Share Information through News Feeds";
    public static final String INT_TASKS = "View and Manage Tasks";
    public static final String INT_LIBRARY = "Share & Download Important Documents";
    public static final String INT_FORUMS = "Exchange Views over Centralized Discussion Board";
    public static final String INT_FRANBUZZ = "Connect with Other Users over Private Social Network";
    public static final String INT_RELATED_LINKS = "Share and Access Links for Ready Referral";
    public static final String INT_RELATED_LINKS_FRANCHISEE = "Manage Your Links for Ready Referral";
    public static final String INT_WHATS_NEW = "Get Updates Since Your Last Login";
    public static final String FRANCHISE_SALES_TT = "Streamline Franchisee Recruitment and Manage Leads";
    public static final String FS_HOME = "Get Quick Insights into Recruitment Metrics";
    public static final String FS_LEAD_MANAGEMENT = "Track and Manage Your Leads";
    public static final String FS_GROUPS = "Group Your Leads to Manage Them";
    public static final String FS_SEARCH = "Search Lead Data Subject to Defined Parameter";
    public static final String FS_EMAIL_CAMPAIGNS = "Manage Email Campaigns";
    public static final String FS_WORKFLOWS = "Manage Workflows";
    public static final String FS_FDD = "Manage FDD Delivery to Prospects";
    public static final String FS_TASKS = "Manage Tasks";
    public static final String FS_CALENDAR = "Manage Appointments/Meetings/Events";
    public static final String FS_IMPORT = "Import External Data into the System";
    public static final String FS_EXPORT = "Export System Data to XML/CSV/Excel";
    public static final String FS_MAIL_MERGE = "Send Personalized Document to Multiple Recipients";
    public static final String FS_SITES = "Manage and Track Physical Locations of Stores"; // P_B_FS_42670
    public static final String FS_BROKERS = "Manage Details of Brokers";
    public static final String FS_QUALIFICATION_CENTER = "View and Manage Pending Calls for Leads";
    public static final String FS_REPORTS = "Generate Desired Analytical Reports";
    public static final String FS_DASHBOARD = "View Detailed Account of Recruitment Metrics";
    public static final String FS_FEEDBACK = "View Feedback"; //PP_changes
    public static final String FS_PIPLELINE = "View Details of Pipeline";//PP_changes
    public static final String FS_WEBSITE_ACTIVITY = "Monitor your Website's Activity through Google Analytics";
    public static final String FIM_GROUPS = "Group Your Franchisees to Manage Them.";   //Added By Rachit Upmanyu
    public static final String CONTACT_MANAGER_TT = "Manage Contact Information and Customer Relationship";
    public static final String CM_HOME = "Take a quick overview";
    public static final String CM_CONTACTS = "View and manage details of existing contacts";
    public static final String CM_LEADS = "View and manage details of existing leads";
    public static final String CM_COMPANIES = "Manage Account details associated with contacts";// P_CM_B_40823
    public static final String CM_COMPANIES_LEAD = "Manage Account details associated with contacts and leads";
    public static final String CM_GROUPS = "Categorize contacts in groups and manage them";
    public static final String CM_GROUPS_LEAD = "Categorize contacts and leads in groups and manage them";
    public static final String CM_OPPORTUNITIES = "View and manage details of opportunities"; //P_B_CM_63391
    public static final String CM_OPPORTUNITIES_LEAD = "View and manage details of opportunities";
    public static final String CM_SEARCH = "Search desired contacts with customizable search";
    public static final String CM_SEARCH_LEAD = "Search desired contacts and leads with customizable search";
    public static final String CM_CAMPAIGN_CENTER = "Manage all your marketing campaigns";// P_CM_B_40823
    public static final String CM_TASKS = "View and manage tasks associated with contacts,leads";
    public static final String CM_TASKS_OPPORTUNITY = "View and manage tasks associated with contacts and opportunities";  // P_CM_B_65387
    public static final String CM_LEAD_TASKS = "View and manage tasks associated with leads  contacts";  
    public static final String CM_LEAD_TASKS_WITH_OPPORTUNITY = "View and manage tasks associated with leads, contacts and opportunities";  
    public static final String CM_OPPORTUNITY_TASKS = "View and manage tasks associated with contacts and opportunities";  // P_CM_B_65387
    public static final String CM_CALENDAR = "View and manage events meetings and appointments";
    public static final String CM_IMPORT = "Import external contact data to system";
    public static final String CM_IMPORT_LEAD = "Import external contact and lead data to system";
    public static final String CM_EXPORT = "Export contact data to different file format";
    public static final String CM_EXPORT_LEAD = "Export contact and lead data to different file format";
    public static final String CM_MAIL_MERGE = "Send personalized document to multiple recipients";
    public static final String CM_REPORTS = "Create and view reports";
    public static final String CM_TRANSACTIONS = "View and manage transaction details";// P_CM_B_40823
    public static final String CM_TRANSACTIONS_LEAD = "View and manage transaction details";
    public static final String FRANCHISE_OPENER_TT = "Streamline On boarding of New Stores";
    public static final String FO_STORE_SUMMARY = "Manage New Locations";
    public static final String FO_SEARCH = "Search Checklist(s) Subject to Defined Parameters";
    public static final String FO_TASKS = "Manage Tasks";
    public static final String FO_ARCHIVED_STORES = "Manage Archived Stores";
    public static final String FO_REPORTS = "Generate Analytical Reports about Onboarding/ Store Development";
    public static final String SMARTCONNECT_TT = "Communicate and manage one-to-one information portal";
    public static final String FIM_TT = "Manage Information and Compliance Metrics of Various Locations";
    public static final String FIM_DASHBOARD = "Get Quick Overview of Location Metrics";
    public static final String FIM_FRANCHISEES = "Manage Active Locations";
    public static final String FIM_TERMINATED = "Manage Terminated Locations";
    public static final String FIM_CORPORATE_LOCATIONS = "Manage Corporate Locations";
    public static final String FIM_REGIONAL = "Manage Area/Region Info";
    public static final String FIM_MULTI_UNIT_ENTITY = "View Multi-Unit/Entity Info";
    public static final String FIM_TASKS = "Manage Tasks";
    public static final String FIM_CALENDAR = "Manage Appointments/Meetings/Events";
    public static final String FIM_SEARCH = "Search Locations Subject to Defined Parameters";
    public static final String FIM_MAIL_MERGE = "Send Personalized Document to Multiple Recipients";
    public static final String FIM_TEMPLATES = "Manage Email Templates";
    public static final String FIM_EXPORT = "Export System Data to XML/CSV/Excel";
    public static final String FIM_IMPORT = "Import External Data into the System";
    public static final String FIM_REPORTS = "Generate Analytical Reports";
    public static final String FIM_LOCAL_LISTINGS = "Promote your Local Business through Online Directories";// P_B_FIM_41420
    public static final String FINANCIALS_TT = "Manage Financial Performance Details";
    public static final String FINANCIALS_TT_FRANCHISEE = "Manage Your Financial Performance Details";
    public static final String FIN_HOME = "Get a Quick Overview of Your Financial Performance";
    public static final String FIN_SALE = "Manage Sales Data";
    public static final String FIN_KPI = "Keep a Track of Your Performance Goals";
    public static final String FIN_PROFIT_LOSS_STATEMENT = "Manage Profit and Loss Statement";
    public static final String FIN_ROYALTIES = "View Royalty Invoices";
    public static final String FIN_PAYMENTS = "Apply Payments and View Payment Summary";
    public static final String FIN_EFT_TRANSACTIONS = "Manage EFT Processing";
    public static final String FIN_STORE_SUMMARY = "View Detailed Store Summary of Any Location";
    public static final String FIN_STORE_SUMMARY_FRANCHISEE = "View Your Detailed Store Summary";
    public static final String FIN_AREA_FRANCHISE = "View Payment and Share Summary of Area Franchise";
    public static final String FIN_DOCUMENTS = "View Documents";
    public static final String FIN_REPORTS = "Generate Detailed Financial Reports";
    public static final String PERFORMANCEWISE_TT = "Review franchisee's performance and assign remedial actions";
    public static final String PW_HOME = "Take a Quick Overview of Recent Audits and Tasks";
    public static final String PW_VISITS = "View and Schedule Franchisee Store Audits";
    public static final String PW_TASKS = "View and Manage Remedial Actions";
    public static final String PW_REPORTS = "Generate and View Customizable Performance Reports";
    public static final String SUPPORT_TT = "Ensure Timely Resolution of Franchisee Issues";
    public static final String SUPPORT_TT_FRANCHISEE = "Get Timely Resolution of Your Issues";
    public static final String SUPPORT_DASHBOARD = "Take Department-wise Rundown of Support Tickets";
    public static final String SUPPORT_TROUBLE_TICKETS = "Raise and Manage Support Tickets";
    public static final String SUPPORT_SEARCH = "Search Support Tickets Subject to Defined Parameters";
    public static final String SUPPORT_FAQS = "Browse Through FAQs to Resolve Your Concerns";
    public static final String SUPPORT_CONTACT_INFO = "View Contact Details of Support Departments";
    public static final String SUPPORT_REPORTS = "Generate Analytical Reports";
    public static final String SUPPORT_ASK_CORPORATE = "Resolve Your Queries Through Support Tickets";
    public static final String CUSTOMER_TRANSACTIONS_TT = "Manage invoices, estimates, and payments.";
    public static final String CT_ESTIMATES = "Add and manage estimates.";
    public static final String CT_INVOICES = "Manage existing invoices and create new one.";
    public static final String CT_PAYMENTS = "Handle payment and deposit details.";
    public static final String CT_QUICK_BOOKS = "Manage and download quick books.";
    public static final String CT_REPORTS = "Generate reports on sales, invoices, and estimates.";
    public static final String CT_DASHBOARD = "A graphical analysis and presentation of customer transactions data.";
    public static final String TRAINING_TT = "Learn Critical Business Skills through Online Training";
    public static final String TR_HOME = "Access Training Courses";
    public static final String TR_EVENTS = "Streamline Creation and Management of Events";
    public static final String TR_ASSESSMENT = "Evaluate Training Participants";
    public static final String TR_REPORTS = "Generate Analytical Reports";
    public static final String REVIEW_QUIZ = "Review Training Quiz";//which-20150312-234
    public static final String AD_BUILDER_TT = "Streamline customization and delivery of artwork";
    public static final String AD_LIGHTBOX = "Browse through the predefined artworks";//ZCUB-20151208-210
    public static final String AD_HOME = "Browse through the predefined artworks";
    public static final String AD_PURCHASE_ORDERS = "Manage purchase orders from one central place";
    public static final String AD_SAVED_CARTS = "Manage the shopping carts you have saved";
    public static final String AD_MY_PROFILE = "Manage your profile details";
    public static final String AD_SAVED_ARTWORK = "Handle saved artworks";
    public static final String AD_REPORTS = "Generate and view various analytical reports";
    public static final String AD_VENDORS = "Manage vendor details to send artwork";
    public static final String SUPPLIES_MARKETING_SHOP_TT = "Order branded goods (business cards, flyers, apparels) in bulk";
    public static final String SUPPLIES_CATEGORIES = "Categorize products for better management";
    public static final String SUPPLIES_HOME_TT = "View Request For Payment Approval";//ZCUB-20151208-210
    public static final String SUPPLIES_SUBCATEGORIES = "Organize products in different subcategories";
    public static final String SUPPLIES_PRODUCTS = "Manage products in different categories,subcategories and organize products into packages to place order.";

    public static final String SUPPLIES_PURCHASE_ORDERS = "Track all your purchase orders";
    public static final String SUPPLIES_ORDERING = "Place an order";
    public static final String SUPPLIES_PAYMENTS = "Maintain your payment history";
    public static final String SUPPLIES_IMPORT = "Import products inventory";//ZCUB-20160212-232
    public static final String SUPPLIES_EXPORT = "Export products or purchase orders details";
    public static final String SUPPLIES_REPORTS = "Generate various analytical reports";
    public static final String SUPPLIES_ORDERS = "View all your purchase order details";
    public static final String SUPPLIES_FAVORITE_PRODUCTS = "View and add your favorite products to shopping cart";
    public static final String SUPPLIES_SHOPPING_CART = "Manage your shopping cart";
    public static final String SUPPLIES_PURCHASE_ORDERS_FR = "Track and manage franchisees' purchase order";
    public static final String SUPPLIES_ORDERING_FR = "Place your order and manage shopping cart";
    public static final String PPC_TT = "Optimize your local search with Pay per Click";
    public static final String PPC_HOME = "Take a quick overview of the Google PPC performance";
    public static final String PPC_CAMPAIGNS = "View statistics of your campaigns performance";
    public static final String PPC_KEYWORDS = "Track how your keywords are performing";
    public static final String PPC_CALLS = "Summarize call details in graph and table form";
    public static final String WEB_SUBMISSION = "Display Leads from PPC Landing Pages";
    public static final String PPC_PAYMENTS = "Display payment summary and recent activities";
    public static final String PPC_REPORTS = "Analyze your Google PPC program with various statistics reports";
    public static final String LOCAL_WEBSITES_TT = "Localize franchise websites while keeping brand continuity";
    public static final String LW_HOME = "Take a quick view of the module and its recent activities";
    public static final String LW_LOCAL_WEBSITES = "Configure and control websites of franchisees";
    public static final String LW_REGIONAL_WEBSITES = "Set up and manage websites for regional stores";
    public static final String LW_WEB_ANALYTICS = "A dashboard to show analytical reports of ";
    public static final String SCHEDULER_TT = "Manage task schedules and appointments.";
    public static final String SCH_ADD_CONTACT_TASK = "Search existing contacts, add new contacts, and assign tasks.";
    public static final String SCH_SCHEDULE = "Schedule tasks for specific users.";
    public static final String SCH_JOB = "Manage jobs assigned to contacts and calculate cost.";
    public static final String SCH_TASKS = "Manage existing task details and add new tasks.";
    public static final String SCH_REPORT = "Generate customizable task and performance reports.";
    public static final String SCH_DASHBOARD = "View graphical task reports in widgets.";
    public static final String DISPATCH_TT = "Schedule and assign tasks to franchise users.";
    public static final String DIS_MY_FOLLOW_UPS = "View existing follow-up tasks and schedule new tasks.";
    public static final String DIS_MY_CONTACTS = "View contact details and schedule new tasks.";
    public static final String DIS_SCHEDULES = "Check franchisees' schedules and create tasks.";
    public static final String LINKS_TT = "Access external links";
    public static final String SSO_TT = "View and access available third party links";
    public static final String BRAND_ANALYTICS_TT = "Manage brand visibility, compare reviews and monitor your business reputation online"; //LGZCB-20150330-010

    public static final String ZCUBATOR_TT = "One stop platform to execute and track marketing efforts";
    public static final String LANDING_PAGES_TT = "Publish and track customizable landing pages for your online campaigns";
    public static final String PILOT_TT = "Automate your Marketing Strategy by enabling real time tracking of every marketing dollar spent";
    public static final String REPUTATION_MANAGER_TT = "Get detailed insights to monitor your brand perception and improve customer satisfaction";
    public static final String MARKETING_DASHBOARD_TT = "Analyze and Assess effectiveness of your Marketing initiatives";
    public static final String SOCIAL_MEDIA_TT = "Grow and manage your social presence through promotions, coupons and offers on social networks";
    public static final String LOCAL_LISTING_TT = "Help your customers find you online by getting listed in online directories";
    public static final String CUSTOMER_FEEDBACK_TT = "Improve customer loyalty and get more online reviews using the automated customer feedback system";

    public static final String HOME_TT = "Get quick overview of performances and appointments.";
    public static final String ANALYTICS_TT = "Assess real time performance analytics.";
    public static final String VIEW_ALL_LABEL = "View All";
    public static final String OPTED_IN_LABEL = "Opted In";
    public static final String OPTED_OUT_LABEL = "Opted Out";
    public static final String OPTED_IN_REQUEST_NOT_SENT_LABEL = "Opt-In Request not Sent";
    public static final String OPTED_IN_PENDING_LABEL = "Opt-In Pending";
    public static final String SEND_OPT_IN_REQUEST_LABEL = "Send Opt-In Request";
    public static final String DEFAULT_SUBSCRIPTION_CM = "";
    public static final String DEFAULT_SUBSCRIPTION_FS = "";
    public static final String IS_MANUAL_AUTOMATIC_FS = "";
    public static final String IS_MANUAL_AUTOMATIC_CM = "";
    public static final String LEAD_SINGLE_OPT_REMARK = "Lead has been updated with " + Constants.OPTED_IN_LABEL + " subscription status as another " + "Lead with same Email is present in the system with this Subscription status.";
    public static final String LEAD_DOUBLE_OPT_REMARK = "Lead has been updated with " + Constants.OPTED_IN_PENDING_LABEL + " subscription status as another " + "Lead with same Email is present in the system with this Subscription status.";
    public static final String LEAD_OPT_OUT_REMARK = "Lead has been updated with " + Constants.OPTED_OUT_LABEL + " subscription status as another " + "Lead with same Email is already present in the system with this Subscription status.";
    public static final String LEAD_OPT_OUT_FROM_SERVICE_MANULLY = "Lead has " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Service(s).";
    public static final String CONTACT_OPT_OUT_FROM_SERVICE_MANULLY = "Contact has " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Service(s).";
    public static final String CONTACT_OPT_OUT_FROM_CATEGORY_MANULLY = "Contact has " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Categories.";
    public static final String LEAD_OPT_OUT_FROM_CATEGORY_MANULLY = "Lead has " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Categories.";
    public static final String LEAD_OPT_OUT_FROM_SERVICE = "Lead has been " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Service(s) as another " + "Lead with same Email is already " + Constants.OPTED_OUT_LABEL + " from mentioned Service(s).";
    public static final String CM_LEAD_OPT_OUT_FROM_SERVICE = " has been " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Service(s) as another " + "Lead/Contact with same Email is already " + Constants.OPTED_OUT_LABEL + " from mentioned Service(s).";
    public static final String CONTACT_SINGLE_OPT_REMARK = "Contact has been updated with " + Constants.OPTED_IN_LABEL + " subscription status as another " + "Contact with same Email is present in the system with this Subscription status.";
    public static final String CONTACT_DOUBLE_OPT_REMARK = "Contact has been updated with " + Constants.OPTED_IN_PENDING_LABEL + " subscription status as another " + "Contact with same Email is present in the system with this Subscription status.";
    public static final String CONTACT_OPT_OUT_REMARK = "Contact has been updated with " + Constants.OPTED_OUT_LABEL + " subscription status as another " + "Contact with same Email is present in the system with this Subscription status.";
    public static final String CONTACT_OPT_OUT_IMPORT = "Contact has been updated with " + Constants.OPTED_OUT_LABEL + " subscription status from Import."; //Shivam ZC93_Import
    public static final String CONTACT_OPT_OUT_FROM_SERVICE = "Contct has been " + Constants.OPTED_OUT_LABEL + " from $SERVICE_NAME$ Service(s) as another " + "Contact with same Email is already " + Constants.OPTED_OUT_LABEL + " from mentioned Service(s).";
    public static final String ALREADY_UNSUBSCRIBED_EMAIL = "Thank You. Your Email address has already unsubscribed from our mailing list.";
    public static final String LEAD_SINGLE_OPT_REMARK_OWN = "Lead has been updated with " + Constants.OPTED_IN_LABEL + " subscription status as its Country has been changed.";
    public static final String LEAD_DOUBLE_OPT_REMARK_OWN = "Lead has been updated with " + Constants.OPTED_IN_PENDING_LABEL + " subscription status as its Country has been changed.";
    public static final String CONTACT_SINGLE_OPT_REMARK_OWN = "Contact has been updated with " + Constants.OPTED_IN_LABEL + " subscription status as its Country has been changed.";
    public static final String CONTACT_DOUBLE_OPT_REMARK_OWN = "Contact has been updated with " + Constants.OPTED_IN_PENDING_LABEL + " subscription status as its Country has been changed.";    
    //Bug#74043 Starts
    public static final String LEAD_BOUNCE_REMARK = "Lead has been updated with Bounce Status as another Contact/Lead with same Email is present in the system with Bounce status.";
    public static final String LEAD_SPAM_REMARK = "Lead has been updated with Spam Status as another Contact/Lead with same Email is present in the system with Spam status.";
    public static final String CONTACT_BOUNCE_REMARK = "Contact has been updated with Bounce Status as another Contact/Lead with same Email is present in the system with Bounce status.";    
    public static final String CONTACT_SPAM_REMARK = "Contact has been updated with Spam Status as another Contact/Lead with same Email is present in the system with Spam status.";
    //Bug#74043 Ends
    public static final String CM_INVALID_LEAD_MSG = "As they have been " + Constants.OPTED_OUT_LABEL + ", " + Constants.OPTED_IN_PENDING_LABEL + ", " + Constants.OPTED_IN_REQUEST_NOT_SENT_LABEL + " or do not have " + Constants.LBL_EMAIL_ID + " or already associated with this particular campaign or " + Constants.LBL_EMAIL_ID + " belongs to bounce/spam list";//ZCUB-20160304-237
    public static final String FS_INVALID_LEAD_MSG = "As they have been " + Constants.OPTED_OUT_LABEL + ", " + Constants.OPTED_IN_PENDING_LABEL + ", " + Constants.OPTED_IN_REQUEST_NOT_SENT_LABEL + " or do not have " + Constants.LBL_EMAIL_ID + " or have email which belongs to bounce/spam list or they are from Unregistered State / Province or already associated with this particular campaign";//ZCUB-20160304-237		//P_B_82192
    //public static final String FS_INVALID_LEAD_MSG_BROKER = "As they have been " + Constants.OPTED_OUT_LABEL + " or do not have " + Constants.LBL_EMAIL_ID + " or have email which belongs to bounce/spam list or they are from Unregistered State / Province or already associated with this particular campaign or " + Constants.LBL_EMAIL_ID + " belongs to bounce/spam list";
    public static final String FS_INVALID_LEAD_MSG_BROKER = "As they have been " + Constants.OPTED_OUT_LABEL + " or do not have " + Constants.LBL_EMAIL_ID + " or already associated with this particular campaign.";
    public static final String FS_SMS_INVALID_LEAD_MSG = "As they have been " + Constants.OPTED_OUT_LABEL + ", " + Constants.OPTED_IN_PENDING_LABEL + ", " + Constants.OPTED_IN_REQUEST_NOT_SENT_LABEL + " or do not have " + Constants.LBL_EVENT_PHONE_NUMBERS + " or they are from Unregistered State / Province or already associated with this particular campaign";//P_ENH_SMS_ENH
    public static final String IS_CAPTIVATE_CAMPAIGN = "yes";
    public static final String LEAD_OPT_OUT_MANULLY_REMARK = "Lead has been Opted Out manually.";
    public static final String FORM_ENCODING_MULTIPART = "accept-charset=\"UTF-8\" accept-charset=\"ASCII\"";
    public static final String ZCUBATOR_PROFILE_PAGE_LOCATION = "cgPaymentProfile";
    public static final String ZCUBATOR_WELCOME_PAGE_LOCATION = "marketingCenter";
    public static final String INTERVAL_DAYS = "30";
    public static final String THROUGH_DAYS = "90";
    public static final String ALL = "all";
    public static final String YES_LBL="Yes";
    //BBP-20140530-255-Phase-2 starts
    public static final String SINGLE_OPT_IN="Single Opt-in";
    public static final String DOUBLE_OPT_IN="Double Opt-in";
    public static final String DOUBLE_OPT_IN_CANADA="Double Opt-in for Canada Country";
    public static final String CHANGE_SINGLE_OPT_IN_STATUS_DOUBLE = "Email Subscription status has been changed  to "+Constants.OPTED_IN_LABEL+" as the Default Mailing Configuration has been changed from "+Constants.DOUBLE_OPT_IN+" to "+Constants.SINGLE_OPT_IN;
    public static final String CHANGE_SINGLE_OPT_IN_STATUS_DOUBLE_CANADA = "Email Subscription status has been changed  to "+Constants.OPTED_IN_LABEL+" as the Default Mailing Configuration has been changed from "+Constants.DOUBLE_OPT_IN_CANADA+" to "+Constants.SINGLE_OPT_IN;
    public static final String CHANGE_DOUBLE_OPT_IN_CANADA_STATUS = "Email Subscription status has been changed  to "+Constants.OPTED_IN_LABEL+" as the Default Mailing Configuration has been changed from "+Constants.DOUBLE_OPT_IN+" to "+Constants.DOUBLE_OPT_IN_CANADA;
    public static final String CONFIRM_VALUE_EMAIL_SUBSCRIPTION = "Changing Default Mailing Configuration may result in change of Email Subscription Status of leads. Are you sure you want to continue?";
    //BBP-20140530-255-Phase-2 ends
    
    public static final String FIM = "fim";

    public static final String LP_DASBOARD_TITLE="Create and Publish Landing Pages using custom templates and track campaign performance.";
    public static final String LP_SIGNUP_TITLE="Create and customize Sign up forms to generate Leads.";


    public static final String FIM_CAMPAIGN_CENTER = "Manage all your campaigns";   //BB-20150319-268 Starts
    
    //KeyRen-20141120-017 changes starts
    public static final String CAPCTHA_MSG="Enter the characters as shown in the image below";
    //KeyRen-20141120-017  changes ends

    public static boolean DISABLE_MU_TABS = false;
    //PROVEN_MATCH_INTEGRATION : START
	public static final String PM_TAB_NAME = "fsPMAssessment";
	public static final String PM_TAB_DISPLAY_NAME = "Proven Match Assessment";
	//PROVEN_MATCH_INTEGRATION : END

	public static final String CAMPAIGN_TABLE_SUFFIX = "_CAMPAIGN";             //BB-20150319-268(FIM Campaign Center)
	
	public static final String PPC_LOCATION_STATISTICS_KEYWORDS="Display Statistics by Franchisee Location";	//ZCUB-20150116-096
	//ENH_PW_ALERT_NOTIFICATION Starts
	public static final String CRITICAL_LEVEL = "Critical Level"; 
	public static final String CRITICAL = "Critical";
	public static final String NON_CRITICAL = "Non-Critical";
    public static final String TASK_LEVEL = "Task Level";
    public static final String LBL_ALERT_FREQUENCY = "Days";
    public static final String LBL_FROM_EMAIL = "From Email";
    public static final String LBL_TO = "To";
	//ENH_PW_ALERT_NOTIFICATION End
    public static final String LL_MODULE_ENABLE_MASTER_DATA_KEY = "21"; //ZCUB-20150319-123
    public static final String LBL_CONFIGURE_BUSSINESS_CATEGORIES = "Configure Business Categories";
    public static void setMuTabs() {
    	    		DISABLE_MU_TABS = Constants.YES_LBL.equals(SQLUtil.getColumnValue("MASTER_DATA", "DATA_VALUE", "DATA_TYPE", "8114"));
    	   	}
    public static final String ADD_REVIEW_URL_KEY = "216";//ZCUB-20150408-129
    
    //ZCUB-20150424-142 starts Added for MP
    public static Map <String,String> monthMap = new TreeMap<String,String>();
    public static Set<String> socialKeywordSet = new HashSet<String>();
    
    
    public static String HEADQUATER_ICON="";
    public static String PARENT_DISPLAY_NAME="parent account";
    //Branding Changes starts
    public static String KEYVAL_FINANCIALS="financials";
    public static String KEYVAL_INTRANET="intranet";
    public static String KEYVAL_FS="fs";
    public static String KEYVAL_STORE_OPENER="storeopener";
    public static String KEYVAL_FIM="fim";
    public static String KEYVAL_TRAINING="training";
    public static String KEYVAL_SUPPORT="support";
    public static String KEYVAL_AUDIT="audit";
    public static String KEYVAL_SMARTCONNECT="smartconnect";
    public static String KEYVAL_UFOC="ufoc";
    public static String KEYVAL_DASHBOARD="dashboard";
    public static String KEYVAL_ZIP="zip";
    public static String KEYVAL_CM="cm";
    public static String KEYVAL_PPC="ppc";
    public static String KEYVAL_MARKETING_ASSETS="marketingAssets";
    public static String KEYVAL_ADMAKER="admaker";
    public static String KEYVAL_SUPPLIES="supplies";
    public static String KEYVAL_LP="lp";
    public static String KEYVAL_MP="mp";
    public static String KEYVAL_LL="ll";
    public static String KEYVAL_RM="rm";
    public static String KEYVAL_MD="md";
    public static String KEYVAL_FB="fb";
    public static String KEYVAL_CF="cf";
    public static String KEYVAL_WB="wb";
    public static String KEYVAL_ZC="marketing-center";

    
    
    public static final Map<String,String> moduleNameMap;
	
	 static
	    {
		 moduleNameMap = new HashMap<String, String>();
		 moduleNameMap.put(KEYVAL_INTRANET,LanguageUtil.getString("Intranet"));
		 moduleNameMap.put(KEYVAL_FS,LanguageUtil.getString("Franchise Sales"));
		 moduleNameMap.put(KEYVAL_STORE_OPENER,LanguageUtil.getString("Franchise Opener"));
		 moduleNameMap.put(KEYVAL_FIM,LanguageUtil.getString("FIM"));
		 moduleNameMap.put(KEYVAL_SUPPORT,LanguageUtil.getString("Support"));
		 moduleNameMap.put(KEYVAL_FINANCIALS,LanguageUtil.getString("Financials"));
		 moduleNameMap.put(KEYVAL_TRAINING,LanguageUtil.getString("Training"));
		 moduleNameMap.put(KEYVAL_AUDIT,LanguageUtil.getString("PerformanceWise"));
		 moduleNameMap.put(KEYVAL_SMARTCONNECT,LanguageUtil.getString("SmartConnect"));
		 moduleNameMap.put(KEYVAL_CM,LanguageUtil.getString("Contact Manager"));
		 moduleNameMap.put(KEYVAL_PPC,LanguageUtil.getString("Google PPC"));
		 moduleNameMap.put(KEYVAL_MARKETING_ASSETS,LanguageUtil.getString("Marketing Assets"));
		 moduleNameMap.put(KEYVAL_ADMAKER,LanguageUtil.getString("Ad Builder"));
		 moduleNameMap.put(KEYVAL_SUPPLIES,LanguageUtil.getString("Marketing Shop"));
		 moduleNameMap.put(KEYVAL_LP,LanguageUtil.getString("Landing Pages"));
		 moduleNameMap.put(KEYVAL_MP,LanguageUtil.getString("Marketing Pilot"));
		 moduleNameMap.put(KEYVAL_LL,LanguageUtil.getString("Local Listings"));
		 moduleNameMap.put(KEYVAL_RM,LanguageUtil.getString("Reputation Management"));
		 moduleNameMap.put(KEYVAL_MD,LanguageUtil.getString("Marketing Dashboard"));
		 moduleNameMap.put(KEYVAL_FB,LanguageUtil.getString("Social Media Manager"));
		 moduleNameMap.put(KEYVAL_CF,LanguageUtil.getString("Customer Feedback"));
		 moduleNameMap.put(KEYVAL_WB,LanguageUtil.getString("Local Websites"));
		 moduleNameMap.put(KEYVAL_ZC,LanguageUtil.getString("Zcubator"));


	    }
	//Branding Changes ends
	static 
	{
		monthMap.put("13001","JANUARY");
		monthMap.put("13002","FEBRUARY");
		monthMap.put("13003","MARCH");
		monthMap.put("13004","APRIL");
		monthMap.put("13005","MAY");
		monthMap.put("13006","JUNE");
		monthMap.put("13007","JULY");
		monthMap.put("13008","AUGUST");
		monthMap.put("13009","SEPTEMBER");
		monthMap.put("13010","OCTOBER");
		monthMap.put("13011","NOVEMBER");
		monthMap.put("13012","DECEMBER");
		
		socialKeywordSet.add("$FACEBOOK_SOCIAL_URL$");
		socialKeywordSet.add("$TWITTER_SOCIAL_URL$");
		socialKeywordSet.add("$YOUTUBE_SOCIAL_URL$");
		socialKeywordSet.add("$LINKEDIN_SOCIAL_URL$");
		socialKeywordSet.add("$PINTEREST_SOCIAL_URL$");
		socialKeywordSet.add("$INSTAGRAM_SOCIAL_URL$");
		socialKeywordSet.add("$GOOGLEPLUS_SOCIAL_URL$");
		socialKeywordSet.add("$SKYPE_SOCIAL_URL$");
		
	}
	//ZCUB-20150424-142 ends
	//P_ENH_DOCUSIGN
	public static final String DOCUSIGN_PAID_URL ="https://www.docusign.net";
	public static final String DOCUSIGN_DEMO_URL ="https://demo.docusign.net";
	//P_ENH_DOCUSIGN
	public static final String LBL_ACCOUNTS = "Accounts";
	public static final String LBL_ACCOUNT = "Account";
	public static final String LBL_ACCOUNTSS = "Account(s)";
	
	public static final String LBL_OPPORTUNITY = "Opportunity";
	// Form_Builder_Starts
	public static final String MOBILE_NUMERIC_FIELDS = "phoneExt,homePhoneExt,ext,ssn,spouseSsn";
	public static final String MOBILE_PII_FIELDS = "ssn,birthMonth,spouseSsn,spouseBirthMonth";
	public static final String MOBILE_ASSETS_LIABILITIES_FIELDS = "ASSETS,cashOnHand,marketableSecurities,accountsReceivable,retirementPlans,realEstate,personalProperty,businessHoldings,lifeInsurance,otherAssets,assestsDescription,LIABILITIES,mortgages,accountsPayable,notesPayable,loansOnLifeInsurance,creditCardBalance,unpaidTaxes,otherLiabilities,liablitiesDescription,totalAssets,totalLiabilities,totalNetworth";
	public static final String MOBILE_ASSETS_FIELDS = "cashOnHand,marketableSecurities,accountsReceivable,retirementPlans,realEstate,personalProperty,businessHoldings,lifeInsurance,otherAssets,assestsDescription";
	public static final String MOBILE_LIABILITIES_FIELDS = "mortgages,accountsPayable,notesPayable,loansOnLifeInsurance,creditCardBalance,unpaidTaxes,otherLiabilities,liablitiesDescription";
	public static final String MOBILE_AL_TOTAL_FIELDS = "totalAssets,totalLiabilities,totalNetworth";
	public static final String MOBILE_ASSETS = "ASSETS";
	public static final String MOBILE_LIABILITIES = "LIABILITIES";
	public static final String MOBILE_ASSETS_LIABILITIES_NUMERIC = "cashOnHand,marketableSecurities,accountsReceivable,retirementPlans,realEstate,personalProperty,businessHoldings,lifeInsurance,otherAssets,mortgages,accountsPayable,notesPayable,loansOnLifeInsurance,creditCardBalance,unpaidTaxes,otherLiabilities";
	// Form_Builder_Ends
	
	 public static final String BI_DASHBOARD_TT = "Intelligence";//BB-20160203-516
	 public static final String BI_FRANCHISE_DASHBOARD_TITLE = "";//BB-20160203-516
	 public static final String BI_CORPORATE_DASHBOARD_TITLE = "";//BB-20160203-516
	 
	 public static final String SMS_SEARCH_BOX_TEXT = " Search by Message, Recipient or Sender";
	 public static final String SEND_OPT_IN_REQUEST_LABEL_SMS = "Send SMS Opt-In Request";
	 public static final String SEND_SMS = "Send SMS";
	 public static final String SMS = "SMS";
	 public static final String SUPPLIES_SAVED_CARTS = "View your saved cart to place the order";	 
	 public static final String SUPPLIES_PACKAGES = "View your packages to place the order";//code for packages ZCUB-20160421-245

	 public static final String CAMPAIGN_TABLE_NAME="campaignTableName";
	 public static final String CAMPAIGN_ASSOCIATION_TABLE="campaignAssociationTable";
	 public static final String CAMPAIGN_EMAIL_LOG_TABLE="ownerIDField";
	 public static final String ID_FIELD="idField";
	 public static final String ADDED_BY_FIELD="addedbyField";
	 public static final String OWNER_ID_FIELD="ownerIDField";
	 
	 
	 public static final String CAMPAIGN_LEAD="campaignLead";
	 public static final String CAMPAIGN_CONTACT="campaignContact";
	 
	 // P_E_FS_SMS_SUBSCRIPTION
	 public static final String SEND_SMS_OPT_IN_REQUEST_LABEL = "Send SMS Opt-In Request";
	 public static final String SEND_SMS_LABEL = "Send SMS";
	 public static final String OPT_IN_TEXT = "START";
	 public static final String OPT_OUT_TEXT = "STOP";
	 public static final String UNREGISTERED_COUNTRY_SMS_STATUS = "9";
	 public static final String SMS_SUBSCRIBE_STATUS = "0";
	 public static final String SMS_UNSUBSCRIBE_STATUS = "1";
	 public static final String SMS_SUBSCRIPTION_PENDING_STATUS = "2";
	 public static final String SMS_SUBSCRIPTION_REQ_NOT_SENT_STATUS = "3";
	 
	 public static final String UNREGISTERED_COUNTRY_SMS_STATUS_LABEL="SMS Not Supported";
	 public static final String SMS_OPTED_IN_REQUEST_NOT_SENT_LABEL = "Opt-In Request not Sent for SMS";
	 public static final String SMS_OPTED_IN_PENDING_LABEL = "Opt-In Pending for SMS";
	 public static final String SMS_UNSUBSCRIBE_LABEL = "Opted-Out from SMS";
	 public static final String SMS_SUBSCRIBE_LABEL = "Opted-In for SMS";
	 
	 public static final String SMS_TYPE_SUBSCRIPTION_INBOUND = "1";
	 public static final String SMS_TYPE_SUBSCRIPTION_OUTBOUND = "2";
	 public static final String SMS_TYPE_INBOUND = "3";
	 public static final String SMS_TYPE_OUTBOUND = "4";
	 public static final String TWILIO_BRAND_LABEL="Brand";
	 public static final int MAX_SMS_CHAR_PER_PAGE = 160;
	 public static final String OWNER_MAP = "ownerMap";
	 public static final String LEAD_DATA_MAP = "leadDataMap";
	 
	 public static final int MAX_FRANCHISEE_COUNT_FOR_MULTIPLE_VISITS = 20;
	 public static final String TENANT_NAME="default";
	 public static final boolean SECURE = false;
	 public static final String HOST_NAME = "www.franconnect.com";
	 public static final String DB_TIMEZONE_TIMEZONEUTILS = "";
	    
	    
	    public static final String DISPLAY_FORMAT_HMS = DateUtil.DB_FORMAT + " HH:mm:ss";
	    public  static final String DISPLAY_FORMAT_HMA = "hh:mm a";
	    public  static final String DISPLAY_FORMAT_HM = "hh:mm";
	    public static String USER_TIME_ZONE = "";
	    public static final String MAX_ROUNDED_DIGITS = "2";
	    public static final String DEFAULT_TIME_ZONE = "US/Eastern";
	    public static final String USER_CURRENCY = "$";
}
