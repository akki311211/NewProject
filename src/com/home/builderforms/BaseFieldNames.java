    /*
TAX_PERCENTAGE *P_ADMIN_FDD_E16082010 16/08/2010 mukesh sundriyal add two new fields entry for CONFIGURE_FDD_TEMPLATE table.

 *P_E_LeadMapPreview	16/12/2010	Vivek Maurya	Enh		added for BUSINESS_NAME
 *P_E_FIN_UPGRADE		28/12/2010	Vivek Maurya	Enh
 BBEH-20110408-055 		24/06/2011  Nishant Prabhakar Enh       for Export Settings
 P_FRANONLINE_B_74847   26 May 2011  Shashank Kukreti		adding SCRAP_TITLE field for franbuzz FRAN_SCRAP table.
 P_FRANONLINE_B_77082&76922    1Aug 2011  Prakash Jodha           new fields added to the FRAN_GROUPtable
 ENH_71BBCME09                 16-AUG-2011              Veerpal Singh           ENH                      for GUI and Functionality change 
 CAPTIVATE_42_POINTS   20/09/2011   Narotam Singh
 P_FS_ENH_FranchiseAward	04oct2011	Vivek Maurya		Enh
 P_FIM_E_CUSTOM_TAB		24Oct2011		Devendra Kant Porwal		Enh			New Custom Tab in Fim 
 P_BB_ENH_EMAILSIGN     21 Nov 2011  Himanshi Gupta      modified for signature configurable
 rmango-20111207-023_FDD_History_For_All_Mails	12Dec2011	Vivek Maurya
 P_E_ConfigurableReturnPath		14Mar2012		Vivek Maurya	Enh
 P_E_CM_QuickCampaign			23Apr2012		Vivek Maurya	Enh	
 P_ENH_INTRANET                 2-MAY-2012      Nipun Gupta     Enh
 P_E_CT_8461   10 May 2012      Neeti Solanki             for adding a customizable label for 'Service'
 P_E_CampaignCenter_Triggers		28July2012		Vivek Maurya		Enh

 P_CM_ENH                            16 AUG2012       Dheeraj Madaan	Added new filed in cm and changed gui

  P_INT_B_8098     16 aug 2012   Megha Chitkara        Product Issue : Intranet Library Folders and Documents are not maintaining the Alphabetic sequence
P_E_CM_FranchiseeTriggers		30Aug2012		Vivek Maurya
Topper_campaign_mail			05Sep2012		Vivek Maurya
P_E_CM_MailSubscription	21Nov2012		Vivek Maurya	Enh
P_E_MoveToFim_AddlFDD		27Nov2012		Veerpal Singh		Patch
fetch-20121126-090          19Dec1012       Nishant Arora       Enh
OutdoorLiving-20130116-590  22 Jan 2013              Ravi Kumar          Enh
P_ENH_SCH_15940				29 jan 2013				Shraddha Seth		Enh
P_E_FO_CUSTOMIZATION		30 jan 2013		Anubhav Jain		
P_E_AddlContactSendMail 13/03/2013	Abubhav Jain		Enh
P_EnH_FIN_Category			19March2013			Dravit Gupta		Enh
P_GUI_LG_9                  10 April 2013     Divya Mishra
P_EnH_CM_Print_Campaign		17 April 2013		Naman Jain   
P_E_JSpellSupplementalDictionary	11Jun2013	Vivek Maurya	Enh 
P_ENH_SMS_CAMPAIGN             9 JUly 2013       Himanshi Gupta         SMS Campaign Enhancement
P_INT_END_STORY_ROLE_BASED     31 Oct 2013		 Niranjan Kumar		Enh
P_ENH_LETTERHEAD       08Nov2013			Naman Jain		Enh		LetterHead
P_TR_ENH_AmazonSkyConfig 12 Nov 2013    Shalini Rana   to configure Amazon S3 service and SkyDrive Service 
P_E_FO_PROJECT_STATUS		18 Nov 2013			Anubhav Jain			Enh
P_INT_ENH_AmazonSkyConfig 22 Nov 2013    Shalini Rana   to configure Amazon S3 service and SkyDrive Service 
P_ENH_FS_WEBFORMIMG Pulkit Gupta      18th nov 2013        enhancement     added 1 new field WEBSITE_LEAD for fs 
P_FS_Enh_BuilderForm	16Dec2013			Naman Jain		Form Builder in Franchise Sales
P_EnH_Print		9/jan/14		Pulkit Gupta		Enh				New Print GUI
P_CM_Enh_BuilderForm    11Feb2014       Dheeraj Madaan                  Form Builder in Contact Manager Module 
GROUP_FILTER_ENH  			15 jan 2014   		Deepanshi Miglani    Enh        for adding group filter in CM Search
 P_ENH_SMC-20140224-392 	13 Mar 2014		Mohit Mishra		Added topbar at Zcubator home Page
 ZCUB-20140307-014       27 March 2014       Amit Tanwani                Marketing Shop  Color and Size changes according to particular CR. 
ZCUB-20140416-054		16 April 2014	Ramu Agrawal				Ad Builder Enhancements
ZcubatorSales_ActivityCode	14 May-2014  Nivrutti Joshi				added contact activity code
 P_ENH_GOOGLELISTING           25 june 2014  SHASHANK GUPTA         Sub module in FIM
 P_SC_CR_009  			5 Sep 2014 		 Ronak Maru			CR(Srv314-20140506-009)   Ability to have Tax (State And Local Tax / Tax) Functionality per each Product/Service 
CG Integration				9Sep2014		 Aman Rana				Cheddar Getter Integration
BOEFLY_INTEGRATION		19/08/2014		Veerpal Singh	Enh		A third party integration with Boefly through REST-API for lead sync.
NATHAN_PROFILER_INTEGRATION		27/08/2014		Prateek Sharma		Ens			A third party integration with Nathan Profiler for lead sync. 
PROVEN_MATCH_INTEGRATION		28/11/2014		Amar Singh			Ens			A third party integration with Proven Match through REST-API for lead sync.
PWISE805_PATCH1		    24-Dec-2014		Rohit Jain          for N/A functionality
  ZCUB-20150202-111       19 march 2015        Divanshu Verma             Integration of Reputation Manager with Brand Analytics
BB-20150108-238		09 Mar 2014		Ashwani Kumar  							SproutLoud SSO Integration in Base 905 Main Branch
PW_ENH_APPROVE_TSK_STATUS 24 Apr 2015	Rohit Jain			 Approve/Reject status will show on task status.
PW_ENH_VST_MAIL       24 Apr 2015	    Rohit Jain		     Visit Completion email is sent to the location owners When it will checked "Send visit completion email to franchisee owner" checkbox.
ENH_PW_ALERT_NOTIFICATION  17 APR 2015  Rachit Upmanyu              PWise Configure Alert Notification Email Triggers
ZCUB-20150116-096      25 April 2015  Divanshu Verma         Adding New tab Locations in PPC
BB-20150430-350			28May2015		Ankesh Khandelwal		New Fields for tax Rates tables
BB-20150501-351		   05May2015	  Sreejan Sur			 Adding new fields to be used in Nacha File Format Configuration
 ZCUB-20150519-148    9 June 2015       Divanshu Verma           Combining Vendasta and Google Categories
 BB-20141230-235 	10 June 2014		Ashwani Kumar  								SAML integration in for third party SSO
 ZCUB-20150728-164        18 sept 2015      Swati Middha                 Adding Store Details in Onboarding Screen
 BB-20150501-351	06Aug2015			Sreejan Sur				Nacha File format changes
 ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 BB-20150713-372				30 Jul 2015		Gautam Gupta		POS Implementation in Financials
ZCUB-20151124-201      30 Nov 2015           Divanshu Verma        Configuration of Tax on Payment and Handling Charges Extraction
ZCUB-20151208-210       19 Dec 2015     Divanshu Verma      Payment Approval Process
ZCUB-20160310-239      11 March 2016   Divanshu Verma       Brand Region Management
ZCUB-20160310-239    18 March  2016    Divanshu Verma    Email Notification in case of RM Reports
ZCUB-20150609-155      16 March 2016    Shiva Bhalla      Enh          Campaign Center Reports.
ZCUB-20160503-258     31 Aug 2016       Divanshu Verma    Facebook Ads Insights PDF Generation
*/



package com.home.builderforms;

import java.lang.reflect.Field;

import com.home.builderforms.FieldNames;

public class BaseFieldNames
{
    public static String NEW_LINE="\n";
	public static final String PIXEL_DPI = "pixeldpi";//AB_MS_NOTE_SETTINGS starts
	public static final String AVAILABLE_ON = "availableOn";
	public static final String MAXIMUM_LENGTH = "maximumLength";
    public static final String PLACEMENT = "placement";
    public static final String ENABLED = "enabled";
    public static final String NOTE_TEXT_AREA = "noteTextArea";
    public static final String FIELD_HEADING = "fieldHeading";//ABS_NM082016
    public static final String NOTE = "note";//ABS_NM082016
    public static final String OPPORTUNITY_CONTACT_ID = "opportunityContactId";
    public static final String INSERTION_TIME= "insertionTime";
    public static final String TEXT_AREA_VALUE= "textAreaValue";
    public static final String NOTE_VALUE= "noteValue";




	public static final String MANIPULATOR = "manipulator";
    public static final String TOTAL_FRANCHISEE = "totalfranchisee";
	public static final String USER_CACHE_KEY = "userCache";
	public static final String MASTER_DATA_CACHE_KEY = "masterDataCacheKey";
	public static final String CM_MASTER_DATA_CACHE_KEY = "cmMasterDataCacheKey";
	public static final String PARENT_DATA = "parentData";
	public static final String MASTER_DATA = "masterData";
	public static final String FRANCHISEE_CACHE_KEY = "franchiseeCache";
	public static final String COUNTRY_CACHE_KEY = "countryCache";
	public static final String STATE_CACHE_KEY = "stateCache";
	public static final String OWNER_CACHE_KEY = "ownerCache";
	public static final String COUNTY_CACHE_KEY = "countyCache"; //P_Enh_County
	public static final String TABS_CACHE_KEY = "tabsCache";
	public static final String DIVISION_CACHE_KEY = "divisionCache";//NEW_USER_LEVEL_HIERARCHY
	public static final String USERS = "users";
	public static final String ALL_USER = "allUser";
	public static final String SOCIAL_NW_SIGNATURE = "sociaNwSignature";
	public static final String SOCIAL_NW_SIGNATURE_CORP = "sociaNwSignatureCorp";
	public static final String CORPORATE_SOCIAL_SIGNATURE = "corporateSocialSgnature";
	public static final String CM_CORPORATE_USER_NOS = "cmCorporateUserNos";
	public static final String FU_USER_TYPE="fuUserType";
	public static final String USER_TYPE1="userType1";
	public static final String CORPORATE_USER="corporate";
	public static final String REGIONAL_USER="regional";
	public static final String FRANCHISEE_USER="franchisee";
	public static final String FRANCHISEE="franchisee";
	public static final String CORPORATE_FRANCHISEE="corporatefranchisee";
	public static final String TERMINATED_FRANCHISEE="terminatedfranchisee";
	public static final String TERMINATED_CORPORATE_FRANCHISEE="terminatedcorporatefranchisee";
	public static final String ACTIVE_COUNTRY = "activecountry";
	public static final String INACTIVE_COUNTRY = "inactivecountry";
	public static final String CCFD_ABBREV = "ccfd_abbrev";
	public static final String VISIBLE_TO_STORE = "visibleToStore";
	public static final String FS_USER = "fsUser";
	public static final String USER_STATUS = "userStatus";
	public static final String FRANCHISEE_STATUS = "franchiseeStatus";
	public static final String ROW_DISPLAY = "rowDisplay";
	public static final String APP_TYPE = "appType";
	public static final String PHOTO_DESCRIPTION = "photoDescription";

	//P_B_LL 61812
	public static final String GOOGLE_BUSINESS_CATEGORY_LABEL = "Google Business Category";
	//P_B_LL 61812
	
	
	//P_E_ResponsiveTemplates Shivam starts
		public static final String RES_TEMPLATE_ID = "resTemplateID";
		//public static final String TEMPLATE_ID = "templateID"; already exists
		//public static final String NAME = "name"; already exists
		public static final String EDITABLE_FIELDS =  "editableFields";
		public static final String EDITABLE_IMAGES =  "editableImages";
		public static final String EDITABLE_HTML = "editableHTML";
		public static final String VIEW_HTML = "viewHTML";
		public static final String TEMPLATE_IMAGE = "templateImage";
		
		public static final String IS_RESPONSIVE = "isResponsive";
	//P_E_ResponsiveTemplates Shivam ends

	
	public static final String SEND_MAIL="sendMail";//P_MAIL_RESPONSE
	public static final String MAIL_FROM_NAME="mailFromName";//P_MAIL_RESPONSE
	public static final String REPORT_PERIOD_END_YEAR = "reportPeriodEndYear";//SMC_FIN_OPTIMIZATION
	public static final String NOTIFICATION_ID="notificationID";
	public static final String MONTH_DAY = "monthDay";
	public static final String WEEK_DAY = "weekDay";
	public static final String MONTH_MAIL = "monthMail";
	public static final String WEEK_MAIL = "weekMail";
	public static final String DAILY_DIGEST_MAIL = "dailyDigestMail";
	public static final String QUARTER_MAIL = "quarterMail";//ZCB-20141017-055

	public static final String MEDIA_NAME = "mediaName";
	public static final String MEDIA_TITLE = "mediaTitle";
	public static final String MEDIA_ID= "mediaID";
	public static final String MEDIA_TYPE = "mediaType";
	public static final String MODULES_INCLUDED = "modulesIncluded";
	public static final String VIDEO_LINK = "videoLink";
    public static final String GOOGLE_DEFAULT_SCOPE = "googleScopeDefault";
    public static final String ALT_TAG = "altTag";
	//P_ENH_ADWORDS
	public static final String CG_CLIENT_ID = "cgClientId";
	public static final String SUBSCRIPTION_STATUS = "subscriptionStatus";
	public static final String OPTED_PLAN = "optedPlan";
	public static final String CALL_DISCOUNT = "callDiscount";
	public static final String NEXT_BILLING_DATE = "nextBillingDate";
	public static final String IS_CG_CANCELLED = "isCgCancelled";
	public static final String CURRENT_SUBSCRIPTION_FLAG = "currentSubscriptionFlag";
	public static final String MARKETING_CHALLENGES = "marketingChallenges";
	public static final String ACTIVE_PLAN_CODE = "activePlancode";
	public static final String LAST_PLAN_CODE = "lastPlancode";
	public static final String ACTIVE_PLAN = "activePlan";
	public static final String LAST_PLAN = "lastPlan";
	public static final String REQUEST_STATUS = "requestStatus";
	public static final String PLAN_CODE = "planCode";
	public static final String ACTIVITY_STATUS = "activityStatus";
	public static final String ACTIVITY_DISPLAY = "activityDisplay";
	public static final String PLAN_STATUS = "planStatus";
	public static final String CLICK_TO_LEAD = "clickToLead";
	public static final String COST_PER_LEAD = "costPerLead";
	public static final String LOCATION_DISPLAY = "locationDisplay";
	public static final String MASTER_COMBO = "masterCombo";
	public static final String COST_PER_CLICK = "costPerClick";
	public static final String SUB_SEGMENT = "subSegment";
	public static final String CAMPAIGN_SEGMENT = "campaignSegment";
	public static final String ACCOUNT_ID = "accountID";
	public static final String COST_CONVERSION = "costConversion";
	public static final String CAMPAIGN = "campaign";
	public static final String ADGROUP = "adgroup";
	public static final String CLICK = "click";
	public static final String DEVICE = "device";
	public static final String NETWORK = "network";
	public static final String AD_COPY = "adCopy";
	public static final String IMPRESSIONS = "impressions";
	public static final String CLICKS = "clicks";
	public static final String CLICK_THROUGH_RATE = "clickThroughRate";
	public static final String CONVERSION = "conversion";
	public static final String TRANSACTION = "transaction";
	public static final String AREA = "area";
	public static final String CALLS = "calls";
	public static final String AVERAGE_POSITION = "averagePosition";
	public static final String FIRST_PAGE_CPC = "	FirstPageCPC";
	public static final String TOTAL_CONVERSION_VALUE = "totalConversionValue";
	public static final String CONVERSION_RATE_PER_CLICK = "conversionRatePerClick";
	public static final String COST_CONVERSION_PER_CLICK = "conversionconversionPerClick";
	public static final String  ADGROUP_ID = "adgroup_ID";
	public static final String AVERAGE_SCORE = "averageScore";
	public static final String KEYWORD_ID = "keywordId";
	public static final String ACCOUNT = "account";
	public static final String PLAN_SETUP_FEE = "planSetUpFee";
	public static final String PLAN_RECURRING_FEE = "planRecurringFee";
	public static final String CALL_TRACKING_FEE = "callTrackingFee";
	public static final String PLAN_NAME = "budgetSelected";
	public static final String PLANS_SUBSCRIPTION_ID = "planSubscriptionID";
	public static final String AD_DIVISION_ID = "comboDivision";
	
	
	//P_TR_ENH_AmazonSkyConfig ,P_INT_ENH_AmazonSkyConfig starts
    public static final String FTP_HOST_NAME="ftpHostName";
    public static final String FTP_HOST_URL="ftpHostUrl";
    public static final String FTP_USER_NAME="ftpUserName";
    public static final String FTP_PASSWORD="ftpPassword";
    public static final String AMAZON_USER_NAME="amazonUserName";
    public static final String AMAZON_PASSWORD="amazonPassword";
    public static final String CONFIGURED="configured";
    public static final String ACCESS_KEY="accessKey";
    public static final String SECRET_KEY="secretKey";
    public static final String DOC_KEY="docKey";
    
    //ZCUB-20150121-098 starts
    public static final String AMZ_DOC_KEY="amzDocKey";
    public static final String AMZ_DOC_KEY1="amzDocKey1";
    public static final String SKY_FILE_ID1="skyFileId1";
    //ZCUB-20150121-098 ends
    
    public static final String FTP_ENABLE="ftpEnable";
    public static final String FTP_CHECK="ftpCheck";
    
    public static final String SKY_USER_NAME="skyUserName";
    public static final String SKY_PASSWORD="skyPassword";
    public static final String SKY_ACCESS_KEY="skyAccessKey";
    public static final String SKY_REFRESH_KEY="skyRefreshKey";
    public static final String SKY_CLIENT_ID="skyClientId";
    public static final String SKY_CLIENT_SECRET="skyClientSecret";
    public static final String ERROR = "error";
    
    public static final String GOOGLE_USER_NAME="googleUserName";
    public static final String GOOGLE_PASSWORD="googlePassword";
    public static final String GOOGLE_CODE="googleCode";
    public static final String GOOGLE_ACCESS_TOKEN="googleAccessToken";
    public static final String GOOGLE_REFRESH_TOKEN="googleRefreshToken";
    public static final String GOOGLE_CLIENT_ID="googleClientId";
    public static final String GOOGLE_CLIENT_SECRET="googleClientSecret";
    
    public static final String SFTP_HOST_NAME="sftpHostName";
    public static final String SFTP_HOST_URL="sftpHostUrl";
    public static final String SFTP_USER_NAME="sftpUserName";
    public static final String SFTP_PASSWORD="sftpPassword";

    
    //ZCB-20141028-057 Nishant starts
    public static final String UPLOAD_STATUS = "uploadStatus";
    public static final String UPLOAD_STATUS1 = "uploadStatus1";
    public static final String UPLOAD_TABLE = "uploadTable";
    public static final String FILE_COLUMN = "fileColumn";
    public static final String DELETE_FLAG = "amzdeleteFlag";
    public static final String AMZ_MODULE_NAME = "amzModuleName";
    public static final String INSERTED_IDS_DOC_UPLOAD = "insertedIdDocUpload";
    //ZCB-20141028-057 ends
    
    //P_TR_ENH_AmazonSkyConfig,P_INT_ENH_AmazonSkyConfig ends
	//webanalytics starts
    public static final String VISITS = "visits";
    public static final String UNIQUE_VISITORS = "uniquevisitors";
    public static final String PAGEVIEWS = "page Views";
    public static final String PAGES_PER_VISIT = "pagespervisit";
    public static final String AVERAGE_VISIT_DURATION = "averagevisitduration";
    public static final String PERCENT_NEW_VISIT = "percentnewvisit";
	public static final String PAGE_ENTRANCE = "pageEntrance";
	public static final String UNIQUE_PAGEVIEWS = "uniquePageViews";
	public static final String EXIT_RATE_PERCENT = "exitRatePercent";
	public static final String AVERAGE_TIME_ON_PAGE = "averageTimeOnPage";
	public static final String AVERAGE_TIME_ON_SITE = "averageTimeOnSite";
    //webanalytics ends
	public static final String SKY_FILE_ID="skyFileId";//P_INT_ENH_AmazonSkyConfig

	public static final String GOOGLE_FILE_ID="googleFileId";
	//public static final String PLAN_RECURRING_FEE = "planRecurringFee";
   //P_INT_B_8098
	public static final String SEQUENCE = "sequence"; 
	
	//for Zcubator Marketing Pilot
	public static final String CM_MARKETING_ID = "cmMarketingId";
	public static final String MARKETING_CODE = "marketingCode";
	public static final String CM_MARKETING_NAME = "cmMarketingName";
	
	     //P_ENH_SMC-20140224-392 starts
		 public static final String CONTACT_STATUS_ID = "contactStatusId";
		 public static final String CONTACT_SOURCE_1_ID = "contactSource1Id";
		 public static final String CONTACT_SOURCE_2_ID = "contactSource2Id";
		 public static final String CONTACT_SOURCE_3_ID = "contactSource3Id";
		 public static final String CONTACT_MARKETING_CODE_ID = "contactMarektingCodeId";
		 public static final String CONTACT_REGULAR_CAMPAIGN = "contactRegularCampaign";
		 public static final String CONTACT_PROMOTIONAL_CAMPAIGN = "contactPromotionalCampaign";
		 public static final String CALLER_FIRST_NAME = "callerFirstName";
		 public static final String CALLER_LAST_NAME = "callerLastName";
		//P_ENH_SMC-20140224-392 ends
		 public static final String OFFLINE_APP="offlineApp";// BB-20140723-143  Ramu Agrawal
	//SMC-20140227-397 starts
		 public static final String CALLER_NAME = "callerName";
	//SMC-20140227-397 starts 
	
	public static final String CM_MARKETING_DESCRIPTION = "cmMarketingDescription";
	public static final String CM_COST_SHEET_DETAIL_ID = "cmCostSheetDetailId";
	public static final String MONTH_ID = "monthId";
	public static final String UPLOADING_DATE = "uploadingDate";
	public static final String CM_PILOT_DOCUMENT_ID= "cmPilotDocumentId";
	public static final String UPLOADED_FILE_NAME= "uploadedFileName";

	//P_B_CM_22016 by Ravi
	public static final String CM_COST_SHEET_ID = "cmCostSheetId";
	public static final String MP_MARKETING_NAME = "marketingName";
	public static final String YEAR_ID = "yearId";	
	public static final String CREATED_ON = "createdOn";
	public static final String COST_TYPE = "costType";
	public static final String EXPECTED_LEAD_TYPE = "expectedLeadType";
	public static final String MP_CONFIG = "mpConfig";	
	public static final String FROM_PILOT = "fromPilot";
	//P_B_CM_22016 by Ravi

	//for Zcubator assigning goals
	public static final String GOAL_ACTIVATE_FROM = "goalActivateFrom";
	public static final String GOAL_ACTIVATE_TO = "goalActivateTo";
	public static final String GOALS_VALUES = "goalsValues";
	public static final String DATE_ORDER = "dateOrder";
	
	
	//public static final String WEBSITEURL = "websiteurl";

//public static final String FACEBOOKPAGE = "facebookpage";

//	public static final String TWITTERPAGE = "twitterpage";

	// public static final String PHONE_EXTENSION = "phoneExtension";

    /* Application Fiels Names */
    public static final String ERROR_PAGE = "errorPage";
    
    //for Inc Account type FieldName // CR No : GlasGu-20110525-051
	public static final String ACC_TYPE = "accType";
    public static final String INC_ACCNT_ID = "incAccntId";
    public static final String UP_SELL = "upSell";//P_E_UPSELL added by neeti
    public static final String PARENT_INC_ACCNT_ID = "parentaccountId";
    public static final String INC_ACCNT_VALUE = "incAccntValue";
    public static final String INC_ACCNT_VALUE1 = "incAccntValue1";
    public static String ACCNT_ID="accntId";
    public static String ACCNT_TYPE="accntType";
    public static String ACCNT_VALUE="accntValue";
    public static final String IS_EXPORTED = "isExported";
    // CR No : GlasGu-20110525-051

    public static final String SUCCESS = "success";

    public static final String ACTION = "action";
    public static final String INSIGHTS = "insights";//P_ENH_CAMPAIGN_DASHBOARD
    public static final String ANSWER = "answer";
    public static final String CAPTIVATE_ID = "captivateID";
    public static final String CAPTIVATE_TEMPLATE_ID = "captivateTemplateID";
    public static final String CP_TEMPLATE_ID = "cpTemplateID";//P_FS_E_24012011 
    public static final String ERROR_RETURN_PAGE = "errorReturnPage";
    public static final String PERIOD_SELECTED = "periodSelected";
    public static final String OTHER_LIST = "otherList";

    public static final String CHILDREN_MAP = "childrenMap";

    public static final String DATE_AUTO_FIELD = "_dateAutoField_";

    public static final String USERID_AUTO_FIELD = "_userIDAutoField_";

    public static final String MAIN_DATA = "mainData";

    public static final String INSERT = "insert";

    public static final String UPDATE = "update";

    public static final String DELETE = "delete";

    public static final String DEPENDS_ON = "dependsOn";

    public static final String CLIENT_NAME = "clientName";

    public static final String DATABASE_NAME = "databaseName";

    //devendra starts.
    public static final String IS_MANAGER="isManager";
    //devendra ends.
    public static final String MAPPING = "mapping";
    public static final String COMPLETE_NEWS = "completeNews";
    
    // Start Added by Chetan on 21st Oct
    
    public static final String TASK_DESC="taskDesc";       
    public static final String AUTOMATED_TASK_ID ="automatedTaskID";
    public static final String PUT_ON_CALENDER = "putOnCalender";
    public static final String ACTIVITY_TYPE = "activityType";
    
    public static final String REMINDER_DATE = "reminderDate";
        
   // End Added by Chetan on 21st Oct

    public static final String TRANSFORM_METHOD = "transformMethod";
    
    public static final String TRANSFORM_VALUE = "transformValue";

    public static final String DEFAULT_ON = "defaultOn";

    public static final String MAIN_TABLE = "mainTable";

    public static final String QUICK_BOOK_ID = "quickBookId";

    /* FieldNames for Generic Handler */
    public static final String RETURN_PAGE = "returnPage";

    // Added By sandeep On 30 June 2006
    public static final String RETURN_PAGE_TAB = "returnTabPage";

    public static final String PROCESSING_CLASS = "processingClass";

    public static final String INSERTED_IDS = "insertedId";

    public static final String SAVE_DIRECTORY = "saveDirectory";
    
    
    public static final String THUMBNAILPATH = "thumbnailPath";
    public static final String THUMBNAILSIZE = "thumbnailSize";
    
    /**
     * @author abhishek gupta
     * @purpose added variable under PMD code review
     */
    public static final String TABLE_MAP_CHART = "tableMapChart";
    public static final String INFO_LBL = "INFO";
    public static final String CHECK_YES = "yes";
    public static final String DISPLAY_FLD = "display";
    public static final String MAP_FOR_REPORT = "MapForReport";
    public static final String TOP_INFO_FOR_REPORT = "topInfoForReport";
    public static final String EXL_FRANCHISEE_NAME = "excelFranchiseeName";
    public static final String TOTAL_EXL = "totalExcel";
    public static final String AVG_FLD = "average";
    public static final String SALES_FLD = "sales";
     public static final String TOTAL_TRANSACTION = "Transactions ($)";
    
    public static final String AVG_SALES_FLD = "averageSales";
    public static final String TOT_AMT_PRCT_FLD = "totalAmountPer";
    public static final String SUMMARY_PAGE_ID = "SummaryPageId";
    public static final String PAGGING_URL_SALES = "paggingUrlSales";
    public static final String SALES_DATE_FROM = "salesDateFrom";
    public static final String SALES_DATE_TO = "salesDateTo";
    public static final String COLS_SPAN = "colspan";
    public static final String FORM_NAME1 = "formname";
    public static final String SEARCH = "search";
    public static final String RPT_PRD_COMBO = "reportPeriodCombo";
    public static final String HTML_CODE = "htmlcode";
    public static final String ADD_HTML = "addhtml";
    public static final String CONTEXT_NAME = "contextname";
    public static final String ALIGN_FLD = "align";
    public static final String RIGHT_FLD = "right";
    
    // Added by Chetan Sharma 0n 26 Dec 2008
    
    public static final String PIC_ID = "picID";
    public static final String ALBUM_ID = "albumID";
    public static final String ALBUM_NAME = "albumName";
    public static final String PHOTO_TITLE = "photoTitle";
    public static final String ALBUM_COVER = "albumCover";
    
    public static final String SCRAP_ID = "scrapID";
	public static final String SCRAP_TEXT = "scrapText";
	public static final String SCRAP_TO = "scrapTo";
	public static final String SCRAP_BY = "scrapBy";
	public static final String SCRAP_DATE = "scrapDate";
        //P_FRANONLINE_ENH_71 Akhil Gupta Starts 
	public static final String SCRAP_TYPE = "scrapType";
        //P_FRANONLINE_ENH_71 Ends
    
    public static final String PICTURE_MIME_TYPE = "pictureMimeType";

    public static final String TABLE_ANCHOR = "tableAnchor";

    public static final String CHILDREN = "Children";

    public static final String RETURNPAGEKEY = "returnPage";

    public static final String ACTION_TYPE = "actionType";

    public static final String EVENT_TYPE = "eventType";

    public static final String DIRECTORY_FROM = "directoryFrom";

    public static final String DIRECTORY_TO = "directoryTo";

    public static final String COPY_FIELD_NAME = "copyFieldName";

    public static final String COPY_FIELD_VALUE = "copyFieldValue";

    public static final String COPY_FILE_DIR = "copyFileDir";

    public static final String SUBMISSION_DATE = "submissionDate";
    /*Added by DheerajM for P_CM_ENH  STARTS*/
    public static final String INCLUDE_IN_PAGE= "includeInPage";
    
    public static String CM_HOW_DID_YOU_HEAR_ID= "cmSource2DisplayName";
    
    public static String CM_PLEASE_SPECIFY_ID= "cmSource3DisplayName";
    
    public static String SOURCE_NAME_DISPLAY= "cmSource2DisplayName";
    
    public static String SUB_SOURCE_NAME_DISPLAY= "cmSource3DisplayName";
    
    /*Added by DheerajM for P_CM_ENH  ENDS*/
   

    public static final String DATE_ADDED = "dateAdded";

    public static final String LEAD = "lead";

    public static final String TO = "to";

    public static final String CSS_NAME = "cssName";

    public static final String IMAGE_NAME = "imageName";

    public static final String USER_NAME = "userName";

    public static final String IS_ROLE = "isRole";
    public static final String NON_FIN_DETAILS = "nonFinDetails";

    /* Field Names for Fim Message Section */
    public static final String MAIL_NO = "mailNo";

    public static final String MAIL_FROM = "mailFrom";
    
    public static final String IS_RFC_EMAIL_SEND = "isRFCEMailSend";

    public static final String MAIL_TO = "mailTo";

    public static final String MAIL_DATE = "mailDate";

    public static final String MAIL_CC = "mailCc";

    public static final String MAIL_BCC = "mailBcc";

    public static final String MAIL_MESSAGE = "mailMessage";

    public static final String ATTACHMENT_FILE_NAME = "attachmentFileName";

    public static final String ATTACHMENT_TYPE = "attachmentType";

    public static final String FRAN_NO = "franNo";

    public static final String IS_ZERO_PRICE = "isZeroPrice";

      /* Field Names for Table Title */
    
       public static final String TITLE_ID = "titleID";
    
    public static final String TITLE_NAME = "titleName";

    //P_E_SUPPORT_ABC-SENIOR added by neeti starts for adding Ticket Timeline Report
    public static final String TIMMER_COUNT_DATE = "timmerCountDate";
    //P_E_SUPPORT_ABC-SENIOR added by neeti ends for adding Ticket Timeline Report

    /* Field Names for Table ADDRESS */
    public static final String ADDRESS_ID = "addressID";
    
    public static final String BILLING_ADDRESS_ID = "billingAddressID";
    public static final String SHIPPING_ADDRESS_ID = "shippingAddressID";
    
    public static final String ADDRESS = "address";

    public static final String ADDRESS2 = "address2";

    public static final String ADDRESS3 = "address3";

    public static final String CITY = "city";

    public static final String COUNTY = "county";
    public static final String COUNTY_ID = "countyID"; //P_Enh_County
    public static final String COUNTY_NAME = "countyName"; //P_Enh_County

    public static final String STATE = "state";

    public static final String COUNTRY = "country";

    public static final String ZIPCODE = "zipcode";

    public static final String PHONE_NUMBERS = "phoneNumbers";

    public static final String MOBILE_NUMBERS = "mobileNumbers";

    public static final String FAX_NUMBERS = "faxNumbers";

    public static final String PAGER_NUMBER = "pagerNumber";
    
    public static final String INDUSTRY_TYPE = "industryType";
    
    public static final String EMAIL_IDS = "emailIds";
    
    public static final String SEND_EMAIL = "sendMail";
    
    public static final String ENABLE_PPC_REQUIREMENTS = "enablePpcRequirements";
    
    public static final String ENABLE_ORDER_FORM = "enableOrderForm";
    
    public static final String COMPANY_DETAILS = "companyDetails";

    public static final String ADDRESS_ORDER = "addressOrder";

    public static final String FOREIGN_ID = "foreignID";

    public static final String FOREIGN_TYPE = "foreignType";

    public static final String EXTN = "extn";

    public static final int ADDRESS_ORDER_FIRST = 0;

    public static final int ADDRESS_ORDER_SECOND = 1;

    public static final int ADDRESS_ORDER_THIRD = 2;

    public static final int ADDRESS_ORDER_FOURTH = 3;

    // Tables for Add Maker Module Start
    /* Added by Abishek Singhal 10 feb 2006 */
    /* Field Names for Table Orders */
    public static final String ORDER_ID = "orderID";

    public static final String ORDER_STATUS_ID = "orderStatusID";

    public static final String ORDERED_BY = "orderBy";
    public static final String CHANGE_STATUS_ID = "changeStatusId";
    public static final String STATUS_CHANGED_DATE  = "statusChangedDate";
    public static final String STATUS_CHANGED_BY = "statusChangedBy";
    public static final String STATUS_CHANGED_TO = "statusChangedTo";
    public static final String STATUS_CHANGED_FROM = "statusChangedFrom";
    public static final String VENDOR_COMMENTS = "vendorComments";
    public static final String SUPPLIER_COMMENTS = "supplierComments";
    
    
    // public static final String FIRST_NAME = "firstName";
    // public static final String LAST_NAME = "lastName";
    // public static final String COMPANY_NAME = "companyName";
    // public static final String ADDRESS1 = "address1";
    // public static final String ADDRESS2 = "address2";
    // public static final String CITY = "city";
    // public static final String STATE = "state";
    // public static final String ZIP = "zip";
    // public static final String PHONE = "phone";
    // public static final String FAX = "fax";
    // public static final String EMAIL_ID = "emaiID";
    // public static final String AMOUNT = "amount";
    // public static final String DATE = "date";
    /* Field Names for Table Orders end .. */

    /* Added by Abishek Singhal 22 feb 2006 */
    /* Field Names for Table ADDMAKER_TEMPLATES */
    // public static final String TEMPLATE_ID = "";
    // public static final String NAME = "";
    public static final String PATH = "path";

    public static final String THUMBNAIL = "thumbnail";

    /* Added by Abishek Singhal 05 April 2006 */
    /* Field Names for Table ADDMAKER_TEMPLATES_LABEL */
    // public static final String TEMPLATE_ID = "";
    public static final String LABEL_NAME = "labelName";

    public static final String LABEL_VALUE = "labelValue";

    /* Added by Abishek Singhal 06 march 2006 Start */
    /* Field Names for Table ADDMAKER_MAILINGLIST */
    public static final String MAILINGLIST_ID = "mailingListID";

    public static final String MAILINGLIST_NAME = "mailingListName";

    public static final String MAILINGLIST_DESCRIPTION = "description";

    public static final String MAILINGLIST_OWNER_ID = "ownerID";

    // CREATION_DATE,LAST_MODIFIED,IS_DELETED

    /* Field Names for Table ADDMAKER_MAILINGLIST_MAPPING */
    public static final String MAILINGLIST_MAPPING_ID = "mailingListMappingID";

    public static final String MAILINGLIST_RECORD_ID = "mailingListRecordID";

    // MAILINGLIST_ID

    /* Field Names for Table ADDMAKER_MAILINGLIST_RECORD */
    // public static final String FIRST_NAME = "firstName";
    // public static final String LAST_NAME = "lastName";
    // public static final String PHONE = "phone";
    // public static final String DEFAULT_EMAIL_ID = "defaultEmailID";
    // public static final String ZIP = "zip";
    public static final String ADDMAKER_DUPLICATE_EMAIL = "addMakerDuplicateEmail";

    // public static final String RECORD_OWNER_ID = "recordOwnerID";
    // MAILINGLIST_RECORD_ID,

    /* Added by Abishek Singhal 06 march 2006 End */
    /* Added by Abishek Singhal 16 march 2006 Start */
    // ADDMAKER_VENDOR
    // public static final String VENDOR_ID = "vendorID";
    /* Added by Abishek Singhal 16 march 2006 End */

    /* Added by Abishek Singhal 20 march 2006 Start */
    // ADDMAKER_EMAIL_TEMPLATES
    public static final String EMAIL_TEMPLATE_ID = "emailTemplateID";

    /* Added by Abishek Singhal 20 march 2006 End */
    // Tables for Add Maker Module End
    /* Field Names for Table ALERT_STATUS */
    public static final String USER_NO = "userNo";
    public static final String USER_UPDATE_NO = "userUpdateNo";

    public static final String ALERT_NO = "alertNo";

    public static final String ALERT_DATE = "alertDate";

    public static final String IS_DELETED = "isDeleted";

    public static final String DATE_DELETED = "dateDeleted";

    public static final String ALERT_STATUS_ID = "alertStatusId";

    public static final String ALERT_STATUS = "alertStatus";

    /* Field Names for Table ALERTS */
    public static final String FROM_USER_NO = "fromUserNo";

    public static final String SENT_DATE = "sentDate";

    public static final String TEXT = "text";

    public static final String FLAG = "flag";

    public static final String END_DATE = "endDate";

    public static final String FROM_USERNAME = "fromUserName";

    public static final String TO_USERNAME = "toUserName";

    public static final String TO_USERNO = "toUserNo";
    
    public static final String FROM_STATUS = "fromStatus";
    public static final String TO_STATUS = "toStatus";

    /* Field Names for Table APPNETIX_CALENDAR */
    public static final String EVENT_ID = "eventID";

    public static final String SCHEDULE = "schedule";

    public static final String TITLE = "title";

    public static final String DETAILS = "details";

    public static final String CALENDAR_EVENT_TYPE = "calendarEventType";

    public static final String EVENT_CATEGORY = "eventCategory";

    public static final String START_DATE = "startDate";

    public static final String START_TIME = "startTime";

    public static final String DURATION = "duration";

    public static final String RECURRING = "recurring";

    public static final String REMIND_BEFORE = "remindBefore";

    public static final String REC_END_DATE = "recEndDate";

    public static final String REC_END_TIME = "recEndTime";

    public static final String CREATER_USER_ID = "createrUserID";

    public static final String MODULE_ID = "moduleID";

    public static final String MODULE_PRIMARY_ID = "modulePrimaryID";

    public static final String CALENDAR_VIEW_FLAG = "calendarViewFlag";

    public static final String COUNT0 = "count0";

    public static final String COUNT1 = "count1";

    public static final String COUNT2_3 = "count2_3";

    public static final String COUNT4 = "count4";
    
	public static final String IS_SCHEDULER_USER = "isSchedulerUser";

    public static final String CAL_PARTICIPANTS = "calParticipants";

    public static final String PARTICIPANTS_ID = "participantsID";

    public static final String SCHEDULE_ID = "scheduleID";
    //P_FDD_E_22092010 added by vikram 
    public static final String COMPOSITE_SCHEDULE_ID = "compositeScheduleID";
    
    public static final String PARENT_SCHEDULE_ID = "parentScheduleID";
    
    public static final String USER_ID = "userID";

    public static final String REMINDER_ID = "reminderID";

    public static final String REMINDER_DATETIME = "reminderDatetime";

    public static final String STATUS = "status";
    
    public static final String STATUS_FILTER = "statusFilter";

    public static final String SUBJECT = "subject";

    public static final String DESCRIPTION = "description";

    public static final String START_DATETIME = "startDatetime";

    public static final String END_DATETIME = "endDatetime";
    
    public static final String MULTIPLE_TASK_START_TIME = "multipleTaskStartTime";
    
    public static final String MULTIPLE_TASK_END_TIME = "multipleTaskEndTime";
    
    public static final String MULTIPLE_SCHEDULE_MAPPING_ID = "multipleScheduleMappingId";

    public static final String LOCATION = "location";

    public static final String REMINDER_FLAG = "reminderFlag";

    public static final String SCHEDULE_TYPE = "scheduleType";

    public static final String PRIORITY = "priority";

    public static final String PRIORITY_ID = "priorityID";

    public static final String SCHEDULE_CATEGORY = "scheduleCategory";

    public static final String BUSY_STATUS = "busyStatus";

    public static final String SCHEDULE_CREATED_BY = "scheduleCreatedBy";

    public static final String SCHEDULE_CREATED_FOR = "scheduleCreatedFor";

    public static final String SCHEDULE_SCOPE = "scheduleScope";

    public static final String RECURRENCE_FLAG = "recurrenceFlag";

    public static final String RECURRENCE_PATTERN = "recurrencePattern";

    public static final String RECURRENCE_DAYOFWEEK = "recurrenceDayofweek";

    public static final String RECURRENCE_DAYOFMONTH = "recurrenceDayofmonth";

    public static final String RECURRENCE_MONTHOFYEAR = "recurrenceMonthofyear";

    public static final String RECURRENCE_INTERVAL = "recurrenceInterval";

    public static final String RECURRENCE_INSTANCE = "recurrenceInstance";

    public static final String RECURRENCE_END_DATETIME = "recurrenceEndDatetime";

    public static final String OCCURRENCE = "occurrence";
  

    // public static final String OPTION_ID = "optionID";
    public static final String OPTION_MINUTE_VALUE = "optionMinuteValue";

    public static final String REMINDER_OPTION = "reminderOption";

    public static final String REMINDER = "reminder";

    public static final String REMINDER_TIME = "reminderTime";

    public static final String REMINDER_DETAIL_ID = "reminderDetailID";

    public static final String CAL_VIEW_KEY = "calViewKey";

    // public static final String USER_NO = "userNo";
    public static final String VIEW_ID = "viewID";

    public static final String WORKING_HOURS_KEY = "workingHoursKey";

    // public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    // public static final String LAST_MODIFIED = "lastModified";
    public static final String OUTLOOK_KEY = "outlookKey";

    // public static final String DELETED = "deleted";
    public static final String SCHEDULE_CATEGORY_NAME = "scheduleCategoryName";

    public static final String CAN_DELETE = "canDelete";

    public static final String FOR_TASK = "forTask";

    public static final String CREATOR_USER_ID = "creatorUserID";

    public static final String ASSIGN_TO = "assignTo";
    
    public static final String ASSIGN_TO_MULTIPLE = "assignToMultiple";

    public static final String DUE_DATE = "dueDate";

    // Added by Abishek Singhal 15 Nov 2006 Start
    public static final String SCHEDULE_TIME = "schduleTime";
    
    public static final String SCHEDULE_END_TIME = "scheduleEndTime";

    // Added by Abishek Singhal 15 Nov 2006 End
    // ////
    public static final String ALL_DAY_EVENT = "alldayEvent";
    
	public static final String ALL_DAY = "allDay";
	public static final String LOCATION_HOLIDAY = "locationHoliday";
    
    /* Field Names for Table ARCHIVED_MESSAGES */
    public static final String MESSAGE_NO = "messageNo";

    public static final String AUTHOR = "author";

    public static final String SENT_TO = "sentTo";

    public static final String CC = "cc";

    /* Field Names for Table AREA_FRANCHISE_MAPPING */
    public static final String AREA_ID = "areaID";

    public static final String FRANCHISE_ID = "franchiseID";
    public static final String CORPORATE_ID = "corporateID";
    public static final String Month_NAME = "monthName";
    public static final String STORE_MONTH = "storeMonth";
    
     //P_E_JOB_CATEGORY added by neeti starts
	 public static final String JOB_CATEGORY_ID = "jobCategoryId";
	 public static final String JOB_CATEGORY_NAME = "jobCategoryName";
	 public static final String JOB_ORDER_NO = "jobOrderNo";
	 //P_E_JOB_CATEGORY added by neeti ends
    
	 //P_SCH_JOBS  table:JOBS  By:Banti Prajapati starts
	 public static final String ADJUSTER_CONTACT_NAME = "adjusterContactName";
	 public static final String JOB_NAME = "jobName";
	 public static final String DEDUCTABLE = "deductable";
	 public static final String REFERRER = "referrer";
	 public static final String PROJECT_MANAGER_ID="projectManagerId";
	 public static final String TOTAL_COST_CHOICE = "totalCostChoice";
	 public static final String MARGIN_CHOICE="marginChoice";
	 //P_SCH_JOBS  table:JOBS  By:Banti Prajapati ends
    
    /* Field Names for Table AREA_OWNERS */

    /* Field Names for Table AREA_USERS */
    public static final String USER_TYPE = "userType";

    /* Field Names for Table AREAS */
    public static final String AREA_NAME = "areaName";

    public static final String AREA_TYPE = "areaType";

    /* Field Names for Table AUDITING */
    public static final String AUDIT_ID = "auditID";

    public static final String ENTITY_ID = "entityID";

    public static final String EVENT = "event";

    public static final String DATE_TIME = "dateTime";

    public static final String FORM_NAME = "formName";

    public static final String TABLE_NAME = "tableName";

    public static final String FIELD_NAME = "fieldName";

    public static final String VALUE_BEFORE_UPDATE = "valueBeforeUpdate";

    public static final String VALUE_AFTER_UPDATE = "valueAfterUpdate";

    public static final String FULL_NAME = "fullName";

    /* Field Names for Table CATEGORY_MAPPING */
    public static final String CATEGORY_MAPPING_NO = "categoryMappingNo";

    public static final String FRANCHISE_NO = "franchiseNo";

    public static final String CATEGORY_ID = "categoryID";
    public static final String ASSOCIATION_COUNT = "associationCount"; 

    /* CM - Start */

    /* Field Names for Table CM_ASSIGN_HISTORY */
    public static final String ASSIGN_HISTORY_ID = "assignHistoryID";

    public static final String CONTACT_ID = "contactID";

    public static final String ASSIGN_FROM_USER = "assignFromUser";

    public static final String ASSIGN_TO_USER = "assignToUser";

    public static final String ASSIGN_BY = "assignBy";

    public static final String ASSIGN_DATE = "assignDate";

    public static final String REMARKS = "remarks";
    
    public static final String WEBSITEURL = "websiteurl";

	public static final String FACEBOOKPAGE = "facebookpage";

	public static final String TWITTERPAGE = "twitterpage";

	public static final String LINKEDIN = "linkedin";

	public static final String YOUTUBE = "youtube";

	
	public static final String GOOGLEPLUS = "googleplus";


	public static final String PHONE_EXTENSION = "phoneExtension";

	public static final String UPLOAD_PICTURE = "uploadPicture";
	public static final String UPLOAD_PICTURE_PATH = "uploadPicturePath";

    public static final String CONTACT_VIEWED_FLAG = "contactViewedFlag";

    /* Field Names for Table CM_COMPANY_INFO */
    public static final String COMPANY_ID = "companyID";
    public static final String TOP_INFO = "topInfo";
    public static final String NO_OF_EMPLOYEE = "noOfEmployee";
    public static final String DATE_RANGE = "dateRange";
    public static final String NO_OF_STORE = "noOfStore";

    public static final String COMMENT_DATE = "commentDate";

    public static final String COMMENTS_BY = "commentsBy";

    public static final String COMMENT_ID = "commentID";

    public static String COMPANY_NAME = "companyName";

    public static final String INDUSTRY = "industry";

    public static final String SIC_CODE = "sicCode";

    public static final String SERVICES_PROVIDED = "servicesProvided";
    public static final String TOP_RANGE = "topRange";
    public static final String COMPANY_ADD_DATE = "companyAddDate";
    // public static final String WEBSITE = "website";
    public static final String DIRECTION = "direction";

    public static final String COMPUTER_INFORMATION = "computerInformation";

    public static final String NETWORK_INFORMATION = "networkInformation";

    public static final String OWNER = "owner";

    public static final String BIRTHDATE = "birthdate";

    public static final String ALTERNATE_EMAIL = "alternateEmail";

    /* Field Names for Table CM_CONTACT_CALL */
    public static final String CALL_ID = "callID";

    public static final String CALL_DATE = "callDate";

    public static final String CALL_ADDED_BY = "callAddedBy";

    public static final String CALL_MADE_BY = "callMadeBy";

    public static final String CALL_SUBJECT = "callSubject";

    public static final String CALL_CONTACT_PERSON = "callContactPerson";

    public static final String CALL_STATUS = "callStatus";

    /* abhishek 11/14/2007 - Add title name for all frnachise(s) selection field for all reports in Financial */
    public static final String ALL_FRANCHISEE_TITLE = "All Franchisees";
    public static final String COUNTRY_NAME = "countryName";

    /* Field Names for Table CM_CONTACT_DETAILS */
    public static final String FRANCHISEE_NO = "franchiseeNo";

    public static final String DEACTIVATION_DATE = "deactivatedDate";

    public static final String CONTACT_ADD_DATE = "contactAddDate";

    public static final String CONTACT_MODIFY_DATE = "contactModifyDate";

    public static final String CONTACT_FIRST_NAME = "contactFirstName";
    
    public static final String CONTACT_NAME = "contactName";

    public static final String CONTACT_MIDDLE_NAME = "contactMiddleName";

    public static final String CONTACT_LAST_NAME = "contactLastName";

    public static final String CM_SOURCE_1_ID = "cmSource1ID";

    public static final String CM_SOURCE_2_ID = "cmSource2ID";

    public static final String CM_SOURCE_3_ID = "cmSource3ID";

    public static final String CM_RATING_ID = "cmRatingID";
    //ADDED BY NIPUN
    public static final String CM_LEAD_STATUS_ID = "cmLeadStatusID";
    
    public static final String CM_LEAD_SUB_STATUS_ID = "cmLeadSubStatusID";


    public static final String IS_LEAD = "isLead";

    public static final String CONTACT_TYPE = "contactType";

    public static final String BEST_TIME_TO_CONTACT = "bestTimeToContact";

    public static final String CONTACT_ADDED_BY = "contactAddedBy";

    public static final String CONTACT_OWNER_ID = "contactOwnerID";

    public static final String OWNER_TYPE = "ownerType";

    public static final String TURN_TO_CUSTOMER_DATE = "turnToCustomerDate";

    public static final String TURN_TO_CUSTOMER_BY = "turnToCustomerBy";

    public static final String EMAIL_TYPE = "emailType";

    public static final String SUFFIX = "suffix";

    public static final String CM_COMPANY_ID = "cmCompanyID";

    public static final String CONTACT_CATEGORY = "contactCategory";
    public static final String CONTACT_CATEOGRY_DETAILS_LABEL = "Contact Source Details";
    public static final String LEAD_CATEOGRY_DETAILS_LABEL = "Lead Source Details";
    public static final String CONTACT_SOURCE_LABEL = "Contact Source";
    public static final String CONTACT_SOURCE = "Source";
    public static final String CONTACT_CATEOGRY_DETAILS = "Source Details";
    

    public static final String OTHER_CONTACT = "otherContact";

    public static final String DEPARTMENT = "department";

    public static final String PRIMARY_CONTACT_METHOD = "primaryContactMethod";

    public static final String POSITION = "position";

    public static final String NUMBER_OF_PCS = "numberOfPcs";

    public static final String CM_CONTACT_DETAILS_C1 = "cmContactDetailsC1";
    
    public static final String CM_CONTACT_DETAILS_C = "cmContactDetailsC";

    public static final String CM_CONTACT_DETAILS_C2 = "cmContactDetailsC2";

    public static final String CM_CONTACT_DETAILS_C3 = "cmContactDetailsC3";

    public static final String CM_CONTACT_DETAILS_C4 = "cmContactDetailsC4";

    public static final String CM_CONTACT_DETAILS_C5 = "cmContactDetailsC5";

    public static final String CM_CONTACT_DETAILS_C6 = "cmContactDetailsC6";

    public static final String CM_CONTACT_DETAILS_C7 = "cmContactDetailsC7";

    public static final String CM_CONTACT_DETAILS_C8 = "cmContactDetailsC8";

    public static final String CM_CONTACT_DETAILS_C9 = "cmContactDetailsC9";

    public static final String CM_CONTACT_DETAILS_C10 = "cmContactDetailsC10";

    public static final String EMAIL_CONTENT = "emailContent";

    public static final String OTHER_EMAIL_CONTENT = "otherEmailContent";
    public static final String OTHER_EMAIL_FIELDS = "otherEmailFields";

    /* Field Names for Table CM_CONTACT_STATUS_CHANGE_INFO */
    public static final String STATUS_CHANGE_INFO_ID = "statusChangeInfoID";

    public static final String STATUS_CHANGE_FROM = "statusChangeFrom";
    
    public static final String SUB_STATUS_CHANGE_FROM = "subStatusChangeFrom";

    public static final String STATUS_CHANGE_TO = "statusChangeTo";
    
    public static final String SUB_STATUS_CHANGE_TO = "subStatusChangeTo";

    public static final String STATUS_CHANGE_BY = "statusChangeBy";

    public static final String SUB_STATUS_CHANGE_BY = "subStatusChangeBy";

    public static final String STATUS_DATE = "statusDate";
    
    public static final String SUB_STATUS_DATE = "subStatusDate";

    /* Field Names for Table CM_CUSTOMER_CONTACTS */
    public static final String CUSTOMER_CONTACT_ID = "customerContactID";

    public static final String PRIMARY_CONTACT = "primaryContact";

    /* Field Names for Table CM_LEAD_STATUS */
    public static final String CM_LEAD_STATUS_NAME = "cmLeadStatusName";
    
    public static final String CM_LEAD_SUB_STATUS_NAME = "cmLeadSubStatusName";

    public static final String CM_ORDER_NO = "cmOrderNo";
    public static final String FS_ORDER_NO = "fsOrderNo";

    /* Field Names for Table CM_OTHER_ADDRESS */
    public static final String OTHER_ADDRESS_ID = "otherAddressID";

    public static final String OTHER_ADDRESS_TYPE = "otherAddressType";

    /* Field Names for Table CM_RATING */
    public static final String CM_LEAD_RATING_ID = "cmLeadRatingID";

    public static final String CM_LEAD_RATING_NAME = "cmLeadRatingName";

    /* Field Names for Table CM_REMARKS */
    public static final String REMARK_ID = "remarkID";
    
    //P_Enh_Site_Clearance starts
    public static final String REMARK_TIME = "remarkTime";
    public static final String SITE_REMARK = "siteRemark";
    public static final String REMARK_AUTHOR = "remarkAuthor";
    public static final String IS_APPROVED = "isApproved";
    //P_Enh_Site_Clearance ends
    
    public static final String LEAD_MAPPING_ID = "leadMappingId";
    public static final String DIVISION_ID = "divisionID";
    public static final String BRAND_MAPPING_ID = "brandMappingId";
    public static final String BRAND_ID = "brandID";
    public static final String DIVISION = "division";
    public static final String BRAND = "brand";
    
    public static String TOPBAR_COLOR_CODE = "colorCode1";
	public static String TOPBAR_TEXT_COLOR_CODE = "colorCode2";
	public static String NAVIGATION_BAR_BG_COLOR_CODE = "colorCode3";
	public static String NAVIGATION_BAR_NORMAL_TEXT_COLOR_CODE = "colorCode4";
	public static String NAVIGATION_BAR_HIGHLIGTED_TEXT_COLOR_CODE = "colorCode5";
	public static String NAVIGATION_BAR_BORDER_COLOR_CODE = "colorCode6";
	public static String SUBMENU_BAR_TEXT_COLOR_CODE = "colorCode7";
	public static String SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE = "colorCode8";
	public static String TOPBAR_ICON_COLOR_CODE="colorCode9";
	public static String TOPBAR_ICON_BG_COLOR_CODE="colorCode10";
	public static String ZCUBATOR_HOME_BACKGROUND_COLOR_CODE="colorCode11";
	public static String[] userColorConfiguration = new String[]
                                                       {
												    		TOPBAR_COLOR_CODE,
												    		TOPBAR_ICON_COLOR_CODE,
												    		TOPBAR_ICON_BG_COLOR_CODE,
												    		NAVIGATION_BAR_NORMAL_TEXT_COLOR_CODE,
												    		NAVIGATION_BAR_HIGHLIGTED_TEXT_COLOR_CODE,
												    		NAVIGATION_BAR_BORDER_COLOR_CODE,
												    		NAVIGATION_BAR_BG_COLOR_CODE,
												    		SUBMENU_BAR_TEXT_COLOR_CODE,
												    		SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE,
												    		ZCUBATOR_HOME_BACKGROUND_COLOR_CODE
                                                       	};
	public static String[] userColorCodes = new String[]{"ffffff","58595B","ffffff","003f7f","bf0000","bf0000","0887ca","ffffff","ffffff","e5e5e5"};
	
    
    public static final String ADD_DATE = "addDate";

    public static final String ADDED_BY = "addedBy";

    public static final String REMARK = "remark";

    /* Field Names for Table CM_SALES_REPORT */
    public static final String SALES_REPORT_ID = "salesReportID";

    public static final String SALES_REPORT_NUMBER = "salesReportNumber";

    public static final String REPORT_DATE = "reportDate";

    public static final String SALE_DATE = "saleDate";

    public static final String SALES_PERSON = "salesPerson";

    public static final String REPORT_ADDED_BY = "reportAddedBy";

    public static final String PAYMENT_MODE = "paymentMode";

    public static final String CREDIT_CARD_TYPE = "creditCardType";

    public static final String INVOICE_ID = "invoiceID";

    public static final String TOTAL_AMOUNT = "totalAmount";

    /* Field Names for Table CM_SALES_REPORT_DETAILS */
    public static final String SALES_REPORT_DETAILS_ID = "salesReportDetailsID";

    public static final String SALES_ITEM_ID = "salesItemID";

    public static final String SALES_ITEM_QUANTITY = "salesItemQuantity";

    public static final String SALES_AMOUNT = "salesAmount";
    public static final String SALES_AMOUNT_LAST = "salesAmountLast";
    
    public static final String GOALS_MONTHLY_ID = "goalsMonthlyID";
    public static final String GOALS_REPORT_DETAILS_ID = "goalsReportDetailsID";
    public static final String GOALS_REPORT_ID = "goalsReportID";
    public static final String GOALS_REPORT_YEAR = "goalsReportYear";
    public static final String GOALS_ITEM_ID = "goalsItemID";
    public static final String GOALS_AMOUNT = "goalsAmount";
    public static final String GOALS_CUST_COUNT = "goalsCustCount";
    public static final String GOALS_CUST_TRANS = "goalsCustTransaction";
    
    public static final String GOALS_STATUS = "goalsStatus";
    
  public static String CM_TITLE = "cmTitle";
    /* Field Names for Table CM_SOURCE_1 */
    public static final String CM_SOURCE_1_NAME = "cmSource1Name";
    public static final String CM_SOURCE_1_DESCRIPTION = "cmSource1Description";

    /* Field Names for Table CM_SOURCE_2 */
    public static final String CM_SOURCE_2_NAME = "cmSource2Name";

    /* Field Names for Table CM_SOURCE_3 */
    public static final String CM_SOURCE_3_NAME = "cmSource3Name";

    /* Field Names for Table CM_TRANSFER_HISTORY */
    public static final String TRANSFER_HISTORY_ID = "transferHistoryID";

    /* Field Names for Table CM_TRANSFER_HISTORY */
    public static final String LEAD_TRANSFER_HISTORY_ID = "leadTransferHistoryID";
    
    public static final String TRANSFER_FROM_FRANCHISEE_NO = "transferFromFranchiseeNo";

    public static final String TRANSFER_TO_FRANCHISEE_NO = "transferToFranchiseeNo";

    public static final String TRANSFER_BY = "transferBy";

    public static final String TRANSFER_DATE = "transferDate";

    /* Field Names for Table CM_SAVE_SEARCH_QUERY */
    public static final String DATE_FROM = "dateFrom";

    public static final String DATE_TO = "dateTo";
    public static final String LBL_DAYS = "noOfDays";

    public static final String SAVE_QUERY_ID = "saveQueryID";

    /* Field Names for Table CM_LEAD_TYPE */
    public static final String CM_LEAD_TYPE_ID = "cmLeadTypeID";

    public static final String CM_LEAD_TYPE_NAME = "cmLeadTypeName";

    /* Field Names for Table CM_BUSINESS_TYPE */
    public static final String BUSINESS_TYPE_ID = "businessTypeID";

    public static final String BUSINESS_TYPE_NAME = "businessTypeName";

    /* Field Names for Table CM_CONTACT_TYPE */
    public static final String CONTACT_TYPE_ID = "contactTypeID";

    public static final String CONTACT_TYPE_NAME = "contactTypeName";

    /* Field Names for Table CM_CALL_TYPE */
    public static final String CALL_TYPE_ID = "callTypeID";

    public static final String CALL_TYPE_NAME = "callTypeName";

    /* Field Names for Table AM_ORDER_STATUS */
    public static final String ORDER_STATUS_NAME = "orderStatusName";

    /* Field Names for Table CM_CONTACT_CATGEORY */
    // public static final String CATEGORY_ID = "categoryID";
    // public static final String CATEGORY_NAME = "categoryName";
    /* CM -End */

    /* Field Names for Table CONFIGURE_EMAIL */
    public static final String EMAIL = "email";

    public static final String DATE = "date";

    public static final String TIME = "time";

    public static final String UNMAPPED_EMAIL = "unmappedEmail";
    
    public static String EMAIL_SIGNATURE = "emailSignature";

    /* Field Names for Table COUNTRIES */
    public static final String COUNTRY_ID = "countryID";

    public static final String NAME = "name";

    public static final String SHOW_COUNTRY = "showCountry";

    /* Field Names for Table CUSTOM_FORM_DATA */
    public static final String CUSTOM_FORM_DATA_ID = "customFormDataID";

    public static final String CUSTOM_FORM_FIELDS_ID = "customFormFieldsID";

    public static final String RECORD_ID = "recordID";

    public static final String VALUE = "value";
  
    public static final String START_DATE_TIME = "Start Date Time";
    public static final String END_DATE_TIME = "End Date Time";

    /* Field Names for Table CUSTOM_FORM_FIELDS */
    public static final String DISPLAY_NAME = "displayName";

    public static final String DATA_TYPE = "dataType";

    public static final String DEFAULT_VALUE = "defaultValue";

    /* Field Names for Table CUSTOM_REPORT */
    public static final String CUSTOM_REPORT_ID = "customReportID";

    public static final String CUSTOM_REPORT_NAME = "customReportName";

    public static final String CUSTOM_REPORT_MAIN_TABLE = "customReportMainTable";

    public static final String CUSTOM_REPORT_OTHER_TABLE = "customReportOtherTable";

    public static final String CUSTOM_REPORT_SELECT_FIELDS = "customReportSelectFields";

    public static final String CUSTOM_REPORT_WHERE_FIELDS = "customReportWhereFields";
    public static final String CUSTOM_REPORT_SUMRY_FIELDS = "customReportSummaryFields";
    public static final String CUSTOM_REPORT_FORMAT_FIELDS = "customReportFormatFields";
    public static final String DEPENDENT_TABLE_FIELDS = "dependentTableFields";//P_E_Fields-20130905-035
    //P_CRPT_E_10042011
    public static final String CUSTOM_REPORT_FIELD_ORDER = "customReportFieldOrder";
    public static final String CUSTOM_REPORT_FILTER_ORDER = "customReportFilterOrder";
    public static final String CUSTOM_REPORT_SELECT_FIELDS_WITH_TABLES = "customReportSelectFieldsWithTables";//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
    
    public static final String SORT_BY = "sortBy";
    public static final String ORDER_BY = "orderBy";
    public static final String SORT_CONTENT = "sortContentBy";
    public static final String CONFIGURE_TABLE_ANCHORS = "configuredTableAnchors";//Problem in Custom Report while do export
    public static final String IS_NUMBERED = "isNumbered";
    

    // public static final String ADDED_BY = "addedBy";
    public static final String ADDED_ON = "addedOn";

    // public static final String LEVEL = "level";
    // Abhishek 11/30/2007 - Use this every where so that avoid to select wrong page attribute
    // START
    public static final String MENU_NAME = "menuName";
    public static final String KRYVAL_NAME = "keyvalName";

    public static final String SUB_MENU_NAME = "subMenuName";

    public static final String SUB_MENU_URL = "subMenuUrl";

    public static final String MENU_NAME_FIN = "financials";

    public static final String SUB_MENU_NAME_FIN_DASHBOARD = "dashboard";

    public static final String SUB_MENU_NAME_FIN_SALES = "vsales";

    public static final String SUB_MENU_NAME_FIN_ROYALTY = "vinvoice";

    public static final String SUB_MENU_NAME_FIN_PAYMENT = "payment";

    public static final String SUB_MENU_NAME_FIN_AREAFRANCHISE = "areafranchise";

    public static final String SUB_MENU_NAME_FIN_DOCS = "finDocs";

    // END

    /* Field Names for Table DOCUMENT_MODIFY_HISTORY */
    public static final String DOCUMENT_MODIFY_ID = "documentModifyID";

    public static final String DOCUMENT_NO = "documentNo";
    public static final String AUDIT_WEB_PAGES_ID = "auditWebPagesID";

    public static final String DOCUMENT_NAME = "documentName";
    public static final String DOCUMENT_LABEL = "documentLabel";
    public static final String DOCUMENT_OPTION = "documentOption";

    public static final String DOCUMENT_TYPE = "documentType";

    public static final String LAST_MODIFY_DATE = "lastModifyDate";

    /* Field Names for Table DOCUMENTS_DOWNLOAD */
    public static final String DOCUMENT_DOWNLOAD_ID = "documentDownloadID";

    public static final String CONTENT_TYPE = "contentType";
    
    public static final String AUTO_GENERATE_THUMBNAIL = "autoGenerateThumbnail";

    public static final String FIRST_DOWNLOAD_DATE = "firstDownloadDate";

    public static final String LAST_DOWNLOAD_DATE = "lastDownloadDate";

    public static final String DOWNLOAD_TYPE = "downloadType";
    
    public static final String REQUIRE_APPROVAL = "requireApproval";//ZCUB-20151208-210 
    
    public static final String PDF_MARGIN = "pdfMargin";//ZCUB-20160205-225
    public static final String SCALING = "scaling";//ZCUB-20160205-225
    public static final String MIN_SCALING = "minScaling";//ZCUB-20160205-225
    public static final String MAX_SCALING = "maxScaling";//ZCUB-20160205-225
    
    /* Field Names for Table FIQ_DOWNLOAD */
    public static final String FIQ_DOWNLOAD_ID = "fiqDownloadID";

    /* Field Names for Table EN_BOUNCE_MESSAGE_ID */
    public static final String BOUNCE_MESSAGE_ID = "bounceMessageID";

    public static final String MESSAGE_ID = "messageID";

    /* Field Names for Table EN_BOUNCE_POPSERVER */
    public static final String SERVER_ID = "serverID";

    public static final String SERVER_NAME = "serverName";

    public static final String USER = "user";

    public static final String PASSWORD = "password";

    /* Field Names for Table EN_CAMPAIGN */
    public static final String CAMPAIGN_ID = "campaignID";
    
    public static final String ARCHIVED_USER_NAME="archivedUserName";//P_CM_ENH_Campaign optimization
    public static final String CAMPAIGN_TITLE = "campaignTitle";

    public static final String CAMPAIGN_DESCRIPTION = "campaignDescription";

    public static final String CAMPAIGN_OWNER_ID = "campaignOwnerID";

    public static final String CAMPAIGN_REPLY_ADDRESS = "campaignReplyAddress";

    public static final String CAMPAIGN_FROM_NAME = "campaignFromName";

    public static final String CAMPAIGN_INTERVAL = "campaignInterval";

    public static final String CAMPAIGN_STATUS = "campaignStatus";

    public static final String FROM_OWNER = "fromOwner";

    public static final String CAMPAIGN_USER_NO = "campaignUserNo";

    public static final String CAMPAIGN_ACCESSIBILITY = "campaignAccessibility";

    public static final String TEMPLATE_ACCESSIBILITY = "templateAccessibility";
    
    /* Field Names for Table TASK_STATUS  By Brajesh  */
	public static final String TASK_STATUS_ID = "taskStatusID";
	public static final String TASK_STATUS_NAME = "taskStatusName";
	public static final String TASK_ORDER_NO = "taskOrderNO";
	public static final String STATUS_TYPE = "statusType";
	
	
	public static final String TYPE_KEY = "typeKey";
	public static final String SUB_TASK_TYPE_NAME = "subTaskTypeName";
	public static final String SUB_TASK_TYPE_ID = "subTaskTypeID";
	
    /* Field Names for Table EN_CAMPAIGN_LOGS */
	
	
	//SERVICE_MASTER_INT_ENH start
    public static final String SEARCH_TRACKING_ID = "searchTrackingId";

    public static final String SEARCH_TEXT = "searchText";
    public static final String SEARCH_TRACKING_DATE = "searchTrackingDate";
	//SERVICE_MASTER_INT_ENH end
	
	
    public static final String EN_CAMPAIGN_LOGS_ID = "enCampaignLogsID";

    public static final String LIST_ID = "listID";

    public static final String MAIL_BOUNCE = "mailBounce";

    public static final String MAIL_READ = "mailRead";

    public static final String MAIL_SENT = "mailSent";

    public static final String LIST_RECORD_ID = "listRecordID";

    public static final String OPT_OUT = "optOut";

    public static final String TEMPLATE_ID = "templateID";

    public static final String URL_VISITED = "urlVisited";

    /* Field Names for Table EN_CAMPAIGN_SCHEDULE */
    public static final String CAMPAIGN_SCHEDULE_ID = "campaignScheduleID";

    public static final String CAMPAIGN_SCHEDULE_DATE = "campaignScheduleDate";

    public static final String CAMPAIGN_TEMPLATE_ID = "campaignTemplateID";

    /* Field Names for Table EN_CAMPAIGN_TEMPLATE */
    public static final String TEMPLATE_INTERVAL = "templateInterval";

    public static final String TIME_INTERVAL = "timeInterval";
    /* Field Names for Table EN_TEMPLATES_FOLDERS */
    public static final String TEMPLATE_FOLDER_ID = "templateFolderID";

    /* Field Names for Table EN_LIST */
    public static final String LIST_NAME = "listName";

    public static final String LIST_DESCRIPTION = "listDescription";

    public static final String CREATION_DATE = "creationDate";

    public static final String LAST_MODIFIED = "lastModified";

    public static final String LIST_OWNER_ID = "listOwnerID";

    /* Saurabh Vaish Field Names for Table FIN_PL_REPORT */
    public static final String PL_INCOME_CAT_ID = "plIncomeCatId";

    public static final String PL_EXPENSE_CAT_ID = "plExpenseCatId";
    
    public static final String PL_OVERHEAD_CAT_ID = "plOverheadCatId";

    public static final String FREQUENCY = "frequency";

    public static final String PL_INCOME_AMOUNT = "plIncomeAmount";

    public static final String PL_EXPENSE_AMOUNT = "plExpenseAmount";
    
    public static final String PL_OVERHEAD_AMOUNT = "plOverheadAmount";

    public static final String PLREP_ID = "plrepId";

    public static final String PLREP_INCOME_ID = "plrepIncomeId";

    public static final String PLREP_EXPENSE_ID = "plrepExpenseId";
    
    public static final String PLREP_OVERHEAD_ID = "plrepOverheadId";

    public static final String NET_PROFIT = "netProfit";

    /* Field Names for Table EN_LIST_CAMPAIGN */
    public static final String LIST_CAMPAIGN_ID = "listCampaignID";

    /* Field Names for Table EN_LIST_RECORD */
    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String PHONE = "phone";
    public static final String PHONE_EXTN = "phoneExtn";

    public static final String DEFAULT_EMAIL_ID = "defaultEmailID";

    public static final String ZIP = "zip";
    
    public static final String LATITUDE="latitude";
            
    public static final String LONGITUDE="longitude";

    public static final String EN_DUPLICATE_EMAIL = "enDuplicateEmail";

    public static final String RECORD_OWNER_ID = "recordOwnerID";

    /* Field Names for Table EN_TEMPLATE_CATEGORY */
    public static final String CATEGORY_NAME = "categoryName";

    /* Field Names for Table EN_TEMPLATE_SUB_CATEGORY */
    public static final String SUB_CATEGORY_ID = "subCategoryID";

    public static final String SUB_CATEGORY_NAME = "subCategoryName";

    public static final String SUB_CATEGORY_DESC = "subCategoryDesc";

    public static final String SUB_CATEGORY_IMG = "subCategoryImg";

    /* Field Names for Table EN_TEMPLATES */
    public static final String TEMPLATE_TITLE = "templateTitle";
    public static final String TEMPLATE_TITLE_LINKED = "templateTitleLinked";

    public static final String TEMPLATE_SUBJECT = "templateSubject";

    public static final String TEMPLATE_CONTENT = "templateContent";

    public static final String TEMPLATE_WEBPAGE_URL = "templateWebpageUrl";

    public static final String TEMPLATE_TYPE = "templateType";

    public static final String IS_STANDARD = "isStandard";

    public static final String TEMPLATE_ATTACHMENTS = "templateAttachments";

    public static final String TEMPLATE_ATTACHMENT_MIME_TYPE = "templateAttachmentMimeType";

    public static final String TEMPLATE_FOR = "templateFor";

    public static final String CREATE_TIMESTAMP = "creationDatetimeStamp";

    /* Field Names for Table EPOLL */
    public static final String EPOLL_ID = "epollID";

    public static final String EPOLL_TITLE = "epollTitle";

    public static final String FROM_DATE = "fromDate";

    public static final String TO_DATE = "toDate";

    public static final String LAST_DATE = "lastDate";

    public static final String EPOLL_STATUS = "epollStatus";

    public static final String FLAG_VIEW = "flagView";

    public static final String FLAG_ALLUSERS = "flagAllusers";

    public static final String MAIN_POLL = "mainPoll";

    public static final String RESPONSE_COUNT = "responseCount";
    
    public static final String POSTED_BY ="postedby";
    
    public static final String OPTIONS_ORDER = "optionsOrder"; /*
                                                             * Entry added by Meena
                                                             */

    /* Field Names for Table EPOLL_ARCHIVED */

    /* Field Names for Table EPOLL_OPTIONS */
    public static final String OPTION_ID = "optionID";

    public static final String QUESTION_ID = "questionID";

    public static final String ANSWERS = "answers";

    /* Field Names for Table EPOLL_OPTIONS_ARCHIVED */

    /* Field Names for Table EPOLL_QUESTION */
    public static final String QUESTION = "question";

    public static final String OPTION_TYPE = "optionType";

    /* Field Names for Table EPOLL_QUESTION_ARCHIVED */

    /* Field Names for Table EPOLL_RESPONSE */
    public static final String RESPONSE_ID = "responseID";

    /* Field Names for Table EPOLL_RESPONSE_ARCHIVED */

    /* Field Names for Table EPOLL_USERS */
    public static final String EPOLL_USERS_ID = "epollUsersID";

    public static final String ROLE_ID = "roleID";
    //P_ENH_INT_ADDRESS_BOOK_ADD_ROLE : starts
    public static final String ROLE_NAME = "roleName";
    public static final String ROLE_USERS_COUNT = "roleUsersCount";
    public static final String GROUP_USERS_COUNT = "groupUsersCount";
    //P_ENH_INT_ADDRESS_BOOK_ADD_ROLE : ends
    /* Field Names for Table EPOLL_USERS_ARCHIVED */

    /* Field Names for Table EVENT_PARTICIPANTS */
    public static final String PARTICIPANT_USER_ID = "participantUserID";

    public static final String EVENT_PARTICIPANTS_KEY = "eventParticipantsKey";

    public static final String CURRENT_ASSIGNMENT_FLAG = "currentAssignmentFlag";

    public static final String CURRENT_TIME = "currentTime"; // P_E_ManageSupplierCategories
    public static final String COMMENTS_HISTORY = "commentsHistory";

    /* Field Names for Table FAQ_CATEGORY */
    public static final String FAQ_CATEGORY_ID = "faqCategoryID";

    public static final String FAQ_CATEGORY = "faqCategory";

    public static final String ORDER_NO = "orderNo";
    public static final String FIELD_PREFIX						= "fieldPrefix";
    
    public static final String DOCUMENT_ORDER = "documentOrder";
    public static final String RECOMMENDED_DOC = "recommendedDoc";
    public static final String PRIVATE_DOC = "privateDoc";
    
    public static final String FOLDER_ORDER = "folderOrder";

    /* Field Names for Table FAQ_DETAILS */
    public static final String FAQ_DETAILS_ID = "faqDetailsID";

    public static final String FAQ_QUESTION = "faqQuestion";

    public static final String FAQ_ANSWER = "faqAnswer";

    public static final String CREATED_BY_NAME = "createdByName";

    /* Field Names for Table FIM_ADDRESS */
    public static final String ADDRESS_HEADING = "addressHeading";

    public static final String CONTACT_TITLE = "contactTitle";

    public static final String MIDDLE_NAME = "middleName";

    public static final String FRANCHISEE_TYPE = "franchiseeType";
    
    /* Field Names for Table FIM_PICTURE */
    //ADDED BY ABHISHEK RASTOGI
    public static final String PICTURE_TRANSFER_ID = "pictureTransferId";
    public static final String MOVE_PICTURE = "movePicture";
    public static final String PICTURE_TITLE = "pictureTitle";
    public static final String PICTURE_ADDED_BY = "pictureAddedBy";
    public static final String PICTURE_FILENAME = "pictureFilename";
    
    /* Field Names for Table FIM_COMPLAINT */
    // public static final String COMPLAINT_ID = "complaintID";
    public static final String COMPLAINT_DATE = "complaintDate";

    public static final String METHOD_ID = "methodID";

    public static final String COMPLAINT = "complaint";

    public static final String COMPLAINT_ATTACHMENT = "complaintAttachment";

    public static final String COMPLAINT_BY = "complaintBy";

    public static final String INCIDENT_DATE = "incidentDate";

    public static final String PRODUCT_CODE = "productCode";

    public static final String COMPLAINT_TYPE_ID = "complaintTypeID";

    public static final String SUMMARY = "summary";

    public static final String ACTION_TAKEN = "actionTaken";
    
    //Deconet-20121114-748 Nishant starts
    public static final String PURCHASEORDER_ARTWORK_ID = "purchaseOrderArtworkId";       
    public static final String ARTWORK_ITEM_ID = "ArtworkItemId";  
    public static final String FRESH_IMAGE = "freshImage"; 
    //Deconet-20121114-748 ends
    
    //OutdoorLiving-20130116-590  by Ravi
    public static final String PAPER = "paper";       
    public static final String DOCUMENT_SIZE = "documentSize";
    public static final String ARTWORK_DIMENSIONS = "artworkDimensions";//ZCUB-20160205-225
    public static final String COLOR_CODE = "colorCode"; 
    public static final String FINAL_SIZE = "finalSize"; 
    public static final String DIMENSIONS = "dimensions"; 
    
    //OutdoorLiving-20130116-590  by Ravi
   
    // P_AB_ENH_PRINT_TYPE by Dheeraj Madaan starts
    public static final String PRINT_TYPE = "printType"; 
    // P_AB_ENH_PRINT_TYPE by Dheeraj Madaan ends
    
  //OutdoorLiving-20130429-634 starts ravi
  	
  	public static final String CART_UNIQUE_KEY = "cartUniqueKey";
  	//OutdoorLiving-20130429-634 ends
    
    public static final String MAIL_LABEL_CONSTANT = "Send Email";
    public static final String MESSAGE_LABEL_CONSTANT = "Send Message";
    // For TERRITORY Table Entry By sanjeev k
    public static final String TERRITORY_ID="territoryID";
    public static final String FIM_TT_TYPE_TERRITORY="fimTtTypeTerritory";
    public static final String FIM_TT_PROTECTED="fimTtProtected";
    public static final String FIM_TT_RIGHT_REFUSAL="fimTtRightRefusal";
    public static final String FIM_TT_MARKETING_OBLIGATION="fimTtMarketingObligation";
    public static final String FIM_TT_RESTRICTIONS="fimTtRestrictions";
    public static final String FIM_TT_JURIDICTION="fimTtJuridiction";
    public static final String FIM_TT_SALES_REQUIREMENT="fimTtSalesRequirement";
    public static final String FIM_TA_NATURAL_HAZARDS="fimTaNaturalHazards";
    public static final String FIM_TA_DISPUTES="fimTaDisputes";
    public static final String FIM_TA_OWNED="fimTaOwned";
    
    public static final String FIM_TT_LOCATION="fimTtLocation";
    public static final String FIM_TT_GEO_COORDINATES="fimTtGeoCoordinates";
    public static final String FIM_TT_COUNTY="fimTtCounty";
    public static final String FIM_TT_ZIP="fimTtZip";
    public static final String FIM_TT_ZIP_LOCATOR="fimTtZipLocator";
    public static final String SYNC_CHECKBOX="syncCheckBox";
    public static final String FIM_ZIP_LOCATOR_IDENTICAL="fimTtZipLocatorIdentical";
    public static final String FIM_TT_LAND_BOUNDARIES="fimTtLandBoundaries";
    public static final String FIM_TT_AREA_SIZE="fimTtAraeSize";
   
   
    public static final String FIM_TA_COMPETITORS_FRANCHISE="fimTaCompetitorsFranchise";
    public static final String FIM_TA_PROXIMITY="fimTaProximity";
    public static final String FIM_TT_COMPETITION_FIVE="fimTtCompetitionFive";
    public static final String FIM_TT_COMPETITION_TEN="fimTtCompetitionTen";
    
    public static final String FIM_TT_POPULATION="fimTtPopulation";
    public static final String FIM_TT_MEDIANINCOME="fimTtMedianIncome";
    
    public static final String FIM_TT_PORTS_HARBORS="fimTtPortsHarbors";
    public static final String FIM_TT_AIRPORT="fimTtAirport";
    public static final String FIM_TT_HELIPORTS="fimTtHeliports";
    
    public static final String FIM_TERRITORY_C1="fimTerritoryC1";
    public static final String FIM_TERRITORY_C2="fimTerritoryC2";
    public static final String FIM_TERRITORY_C3="fimTerritoryC3";
    public static final String FIM_TERRITORY_C4="fimTerritoryC4";
    public static final String FIM_TERRITORY_C5="fimTerritoryC5";
    public static final String FIM_TERRITORY_C6="fimTerritoryC6";
    public static final String FIM_TERRITORY_C7="fimTerritoryC7";
    public static final String FIM_TERRITORY_C8="fimTerritoryC8";
    public static final String FIM_TERRITORY_C9="fimTerritoryC9";
    public static final String FIM_TERRITORY_C10="fimTerritoryC10";
    
    
    
    
    /* Field Names for Table AREA_COMPLAINT */
    public static final String AREA_COMPLAINT_C1 = "areaComplaintC1";

    public static final String AREA_COMPLAINT_C2 = "areaComplaintC2";

    public static final String AREA_COMPLAINT_C3 = "areaComplaintC3";

    public static final String AREA_COMPLAINT_C4 = "areaComplaintC4";

    public static final String AREA_COMPLAINT_C5 = "areaComplaintC5";

    public static final String AREA_COMPLAINT_C6 = "areaComplaintC6";

    public static final String AREA_COMPLAINT_C7 = "areaComplaintC7";

    public static final String AREA_COMPLAINT_C8 = "areaComplaintC8";

    public static final String AREA_COMPLAINT_C9 = "areaComplaintC9";

    public static final String AREA_COMPLAINT_C10 = "areaComplaintC10";

    /* Field Names for Table FIM_CONTRACT */
    // public static final String CONTRACT_ID = "contractID";
    public static final String CONTRACT_RECIEVED_SIGNED = "contractRecievedSigned";

    public static final String LOCAL_CO_OP = "localCoOp";

    public static final String LEASE_SIGNED_ATTACHMENT = "leaseSignedAttachment";

    public static final String LEASE_SIGNED_TITLE = "leaseSignedTitle";

    public static final String LICENSE_SIGNED = "licenseSigned";

    public static final String LICENSE_SIGNED_ATTACHMENT = "licenseSignedAttachment";

    public static final String LICENSE_SIGNED_TITLE = "licenseSignedTitle";

    public static final String PROMISSORY_SIGNED = "promissorySigned";

    public static final String PROMISSORY_SIGNED_ATTACHMENT = "promissorySignedAttachment";

    public static final String CONVENANT_SIGNED = "convenantSigned";

    public static final String CONVENANT_SIGNED_ATTACHMENT = "convenantSignedAttachment";

    public static final String CONVENANT_SIGNED_TITLE = "convenantSignedTitle";

    public static final String UFOC_SIGNED = "ufocSigned";

    public static final String UFOC_SIGNED_ATTACHMENT = "ufocSignedAttachment";

    public static final String UFOC_SIGNED_TITLE = "ufocSignedTitle";

    public static final String GUARANTEE_SIGNED = "guaranteeSigned";

    public static final String GUARANTEE_SIGNED_ATTACHMENT = "guaranteeSignedAttachment";

    public static final String GUARANTEE_SIGNED_TITLE = "guaranteeSignedTitle";

    public static final String OTHER_DOC_SIGNED = "otherDocSigned";

    public static final String OTHER_DOC_SIGNED_ATTACHMENT = "otherDocSignedAttachment";

    public static final String OTHER_DOC_SIGNED_TITLE = "otherDocSignedTitle";

    public static final String ADDENDUM_SIGNED = "addendumSigned";

    public static final String ADDENDUM_SIGNED_ATTACHMENT = "addendumSignedAttachment";

    public static final String ADDENDUM_SIGNED_TITLE = "addendumSignedTitle";

    public static final String HANDWRITTEN_CHANGES = "handwrittenChanges";

    public static final String ADDENDA_SIGNED = "addendaSigned";

    public static final String ADDENDA_SIGNED_ATTACHMENT = "addendaSignedAttachment";

    public static final String ADDENDA_SIGNED_TITLE = "addendaSignedTitle";

    public static final String REAL_ESTATE = "realEstate";

    public static final String ENTITY_TYPE = "entityType";

    public static final String ACCOUNT_BALANCE = "accountBalance";

    public static final String FORMATION_DOC = "formationDoc";

    public static final String FORMATION_DOC_ATTACHMENT = "formationDocAttachment";

    public static final String FORMATION_DOC_TITLE = "formationDocTitle";

    public static final String GOVERNING_DOC = "governingDoc";

    public static final String GOVERNING_DOC_ATTACHMENT = "governingDocAttachment";

    public static final String GOVERNING_DOC_TITLE = "governingDocTitle";

    public static final String ACTIVE = "active";

    public static final String ACTIVE_ATTACHMENT = "activeAttachment";

    public static final String ACTIVE_TITLE = "activeTitle";

    public static final String PATRIOT_CHECKED = "patriotChecked";

    public static final String FINANCIAL_STATEMENT = "financialStatement";

    public static final String INSURANCE_PROVIDED = "insuranceProvided";

    public static final String INSURANCE_PROVIDED_ATTACHMENT = "insuranceProvidedAttachment";

    public static final String INSURANCE_PROVIDED_TITLE = "insuranceProvidedTitle";

    public static final String POLICY_TITLE = "policyTitle";

    public static final String WC_COVERAGE_REQUIRED = "wcCoverageRequired";

    public static final String WC_COVERAGE_PRESENT = "wcCoveragePresent";

    public static final String GL_COVERAGE_REQUIRED = "glCoverageRequired";

    public static final String GL_COVERAGE_PRESENT = "glCoveragePresent";

    public static final String GL_EACH_OCCURRENCE = "glEachOccurrence";

    public static final String GL_AGGREGATE = "glAggregate";

    public static final String PROPERTY_COVERAGE_REQUIRED = "propertyCoverageRequired";

    public static final String PROPERTY_COVERAGE_PRESENT = "propertyCoveragePresent";

    public static final String PROPERTY_DEDUCTIBLE = "propertyDeductible";

    public static final String VEHICLE_COVERAGE_REQUIRED = "vehicleCoverageRequired";

    public static final String VEHICLE_COVERAGE_PRESENT = "vehicleCoveragePresent";

    public static final String OTHER_COVERAGE_REQUIRED = "otherCoverageRequired";

    public static final String OTHER_COVERAGE_PRESENT = "otherCoveragePresent";

    public static final String OTHER_POLICY_DESCRIPTION = "otherPolicyDescription";

    public static final String OTHER2_COVERAGE_REQUIRED = "other2CoverageRequired";

    public static final String OTHER2_COVERAGE_PRESENT = "other2CoveragePresent";

    public static final String OTHER2_COMMENTS = "other2Comments";

    public static final String SIGN_ROI = "signRoi";

    public static final String SIGN_ROI_ATTACHMENT = "signRoiAttachment";

    public static final String SIGN_ROI_TITLE = "signRoiTitle";

    public static final String REFURBISHMENT_PLAN = "refurbishmentPlan";

    public static final String REFURBISHMENT_PLAN_ATTACHMENT = "refurbishmentPlanAttachment";

    public static final String REFURBISHMENT_PLAN_TITLE = "refurbishmentPlanTitle";

    public static final String GENERAL_RELEASE = "generalRelease";

    public static final String GENERAL_RELEASE_ATTACHMENT = "generalReleaseAttachment";

    public static final String GENERAL_RELEASE_TITLE = "generalReleaseTitle";

    public static final String LEASE_ASSUMPTION = "leaseAssumption";

    public static final String LEASE_ASSUMPTION_ATTACHMENT = "leaseAssumptionAttachment";

    public static final String LEASE_ASSUMPTION_TITLE = "leaseAssumptionTitle";

    public static final String FINANCE_APPROVED = "financeApproved";

    public static final String CASH_REVENUE = "cashRevenue";

    public static final String CASH_REVENUE_ATTACHMENT = "cashRevenueAttachment";

    public static final String CASH_REVENUE_TITLE = "cashRevenueTitle";

    public static final String FRANCHISEE_FEE = "franchiseeFee";

    public static final String CASH_REFUNDABLE = "cashRefundable";

    public static final String PROMISSORY_AMT = "promissoryAmt";

    public static final String NOTE_REVENUE = "noteRevenue";

    public static final String PROMISSORY_DATE = "promissoryDate";

    public static final String NOTE_REFUNDABLE = "noteRefundable";

    public static final String FDD_DATE = "fddDate";

    public static final String AGREEMENT_RECIEVED_DATE = "agreementRecievedDate";

    public static final String FRANCHISEE_RECIEVED_DATE = "franchiseeRecievedDate";

    public static final String ADDITIONAL_FRANCHISEE_RECIEVED_DATE = "additionalFranchiseeRecievedDate";

    public static final String BUSINESS_5_EXPIRES_DATE = "business5ExpiresDate";

    public static final String AGREEMENT_SIGNED_DATE = "agreementSignedDate";

    public static final String ADDITIONAL_UFOC_DATE = "additionalUfocDate";

    public static final String RULE_CHECK_FLAG = "ruleCheckFlag";

    public static final String RULE_AGGREEMENTS_10_FLAG = "ruleAggreements10Flag";

    public static final String BUSINESS_10_EXPIRES_DATE = "business10ExpiresDate";

    public static final String ADDITIONAL_BUSINESS_10_EXPIRES_DATE = "additionalBusiness10ExpiresDate";

    public static final String ASSIGNMENT_ASSUMTIONS = "assignmentAssumtions";

    public static final String ASSIGNMENT_ASSUMTIONS_DATE = "assignmentAssumtionsDate";

    public static final String RULE_AGGREEMENTS_5_FLAG = "ruleAggreements5Flag";

    public static final String FIRST_PAYMENT_DATE = "firstPaymentDate";

    public static final String SECOND_PAYMENT_DATE = "secondPaymentDate";

    public static final String STATE_REGISTRATION = "stateRegistration";

    public static final String STATE_ADDENDUM = "stateAddendum";

    public static final String AGREEMENT_VERSION_DATE = "agreementVersionDate";

    public static final String COMMITTEE_APPROVAL_DATE = "committeeApprovalDate";

    public static final String COMMISSION_DUE = "commissionDue";

    public static final String COMMISSION_PROCESSED_DATE = "commissionProcessedDate";

    public static final String DEAL_COUNTED = "dealCounted";

    public static final String YEAR = "year";

    public static final String FINAL_COM_RESULT = "finalComResult";

    public static final String UNIT = "unit";

    public static final String CITY_NAME = "cityName";

    public static final String STATE_NAME = "stateName";

    public static final String SITE_SELECTION = "siteSelection";

    public static final String LEASE_NAME = "leaseName";

    public static final String OPEN_BY_DATE = "openByDate";

    public static final String LICENSE_AGREEMENT_PROPERLY_SIGNED = "licenseAgreementProperlySigned";

    public static final String PROMISSORY_AGREEMENT_PROPERLY_SIGNED = "promissoryAgreementProperlySigned";

    public static final String LICENSE_AGREEMENT_ATTACHMENT = "licenseAgreementAttachment";

    public static final String PROMISSORY_AGREEMENT_ATTACHMENT = "promissoryAgreementAttachment";

    public static final String PERSONAL_COVENANTS_AGREEMENT_PROPERLY_SIGNED = "personalCovenantsAgreementProperlySigned";

    public static final String UFOC_RECIEPT_PROPERLY_SIGNED = "ufocRecieptProperlySigned";

    public static final String PERSONAL_COVENANTS_AGREEMENT_ATTACHMENT = "personalCovenantsAgreementAttachment";

    public static final String UFOC_RECIEPT_ATTACHMENT = "ufocRecieptAttachment";

    public static final String GUARANTEE_PROPERLY_SIGNED = "guaranteeProperlySigned";

    public static final String LEASE_RIDER_PROPERLY_SIGNED = "leaseRiderProperlySigned";

    public static final String GUARANTEE_ATTACHMENT = "guaranteeAttachment";

    public static final String LEASE_RIDER_ATTACHMENT = "leaseRiderAttachment";

    public static final String RENEWAL_ADDENDUM_PROPERLY_SIGNED = "renewalAddendumProperlySigned";

    public static final String CONSENT_AGMT_PROPERLY_SIGNED = "consentAgmtProperlySigned";

    public static final String RENEWAL_ADDENDUM_ATTACHMENT = "renewalAddendumAttachment";

    public static final String CONSENT_AGMT_ATTACHMENT = "consentAgmtAttachment";

    public static final String TRANSFER_ADDENDUM_PROPERLY_SIGNED = "transferAddendumProperlySigned";

    public static final String TRANFER_AFFIDAVIT_PROPERLY_SIGNED = "tranferAffidavitProperlySigned";

    public static final String TRANSFER_ADDENDUM_ATTACHMENT = "transferAddendumAttachment";

    public static final String TRANSFER_AFFIDAVIT_ATTACHMENT = "transferAffidavitAttachment";

    public static final String QUESTIONAIRE_PROPERLY_SIGNED = "questionaireProperlySigned";

    public static final String STATE_ADDENDUM_PROPERLY_SIGNED = "stateAddendumProperlySigned";

    public static final String QUESTIONAIRE_ATTACHMENT = "questionaireAttachment";

    public static final String STATE_ADDENDUM_ATTACHMENT = "stateAddendumAttachment";

    public static final String SOFTWARE_LICENSE_PROPERLY_SIGNED = "softwareLicenseProperlySigned";

    public static final String EXECUTIVE_ORDER_PROPERLY_SIGNED = "executiveOrderProperlySigned";

    public static final String SOFTWARE_LICENSE_ATTACHMENT = "softwareLicenseAttachment";

    public static final String EXECUTIVE_ORDER_ATTACHMENT = "executiveOrderAttachment";

    public static final String CONSENT_AGREEMENT_PROPERLY_SIGNED = "consentAgreementProperlySigned";

    public static final String OWNERS_AFFIDAVIT_PROPERLY_SIGNED = "ownersAffidavitProperlySigned";

    public static final String CONSENT_AGREEMENT_ATTACHMENT = "consentAgreementAttachment";

    public static final String OWNERS_AFFIDAVIT_ATTACHMENT = "ownersAffidavitAttachment";

    public static final String NATL_ADV_AGMT_PROPERLY_SIGNED = "natlAdvAgmtProperlySigned";

    public static final String SBA_ADDENDUM_PROPERLY_SIGNED = "sbaAddendumProperlySigned";

    public static final String NATL_ADV_AGMT_ATTACHMENT = "natlAdvAgmtAttachment";

    public static final String SBA_ADDENDUM_ATTACHMENT = "sbaAddendumAttachment";

    public static final String GETTY_ADDENDUM_PROPERLY_SIGNED = "gettyAddendumProperlySigned";

    public static final String HANDWRITTEN_CHANGES_APPROVED = "handwrittenChangesApproved";

    public static final String GETTY_ADDENDUM_ATTACHMENT = "gettyAddendumAttachment";

    public static final String ACAL_PROPERLY_SIGNED = "acalProperlySigned";

    public static final String REAL_ESTATE_PROOF = "realEstateProof";

    public static final String ACAL_ATTACHMENT = "acalAttachment";

    public static final String REAL_ESTATE_ATTACHMENT = "realEstateAttachment";

    public static final String ASSIGNED_AREA_PROPERLY_SIGNED = "assignedAreaProperlySigned";

    public static final String ASSIGNED_AREA_ATTACHMENT = "assignedAreaAttachment";

    public static final String SITE_SELECTION_ATTACHMENT = "siteSelectionAttachment";

    public static final String EFT_PROPERLY_SIGNED = "eftProperlySigned";

    public static final String VET_FRAN_PROPERLY_SIGNED = "vetFranProperlySigned";

    public static final String EFT_ATTACHMENT = "eftAttachment";

    public static final String VET_FRAN_ATTACHMENT = "vetFranAttachment";

    public static final String PAC_PROPERLY_SIGNED = "pacProperlySigned";

    public static final String PIP_PROPERLY_SIGNED = "pipProperlySigned";

    public static final String PAC_ATTACHMENT = "pacAttachment";

    public static final String PIP_ATTACHMENT = "pipAttachment";

    public static final String ARTICLES_INCORPORATION = "articlesIncorporation";

    public static final String ORGANIZATION_AGREEMENT = "organizationAgreement";

    public static final String BYLAWS = "bylaws";

    public static final String MANAGEMENT_AGREEMENT = "managementAgreement";

    public static final String STOCK_SPECIMEN = "stockSpecimen";

    public static final String EVIDENCE_CLOSING_PROVIDED = "evidenceClosingProvided";

    public static final String PROMISSORY_NOTE_PREPARE_DATE = "promissoryNotePrepareDate";

    public static final String PAYMENT_PLAN_DATED = "paymentPlanDated";

    public static final String PROMISSORY_NOTE_DATED = "promissoryNoteDated";

    public static final String PAYMENT_PLAN_AMOUNT = "paymentPlanAmount";

    public static final String PROMISSORY_NOTE_AMOUNT = "promissoryNoteAmount";

    public static final String PLAN_FINAL_PAYMENT_DATED = "planFinalPaymentDated";

    public static final String PROMISSORY_FINAL_PAYMENT_DATED = "promissoryFinalPaymentDated";

    public static final String SETTLEMENT_PREPARED_DATE = "settlementPreparedDate";

    public static final String SETTLEMENT_EFFECTIVE_DATE = "settlementEffectiveDate";

    public static final String SETTLEMENT_SENT_DATE = "settlementSentDate";

    public static final String SETTLEMENT_FINALIZED_DATE = "settlementFinalizedDate";

    public static final String MODIFIED_OPERATING_FEES = "modifiedOperatingFees";

    public static final String DEBT_PAYOFF = "debtPayoff";

    public static final String OTHER = "other";

    public static final String TRANSFER_FA = "transferFa";

    public static final String SEE_NOTES = "seeNotes";

    public static final String TERMINATE_DEIDENTIFY = "terminateDeidentify";

    public static final String LEGAL_NOTICE_TITLE = "LegalNoticeTitle";

    public static final String LEGAL_NOTICE_FIRST_NAME = "legalNoticeFirstName";

    public static final String LEGAL_NOTICE_LAST_NAME = "legalNoticeLastName";

    public static final String LEGAL_NOTICE_ADDRESS = "legalNoticeAddress";

    public static final String LEGAL_NOTICE_CITY = "legalNoticeCity";

    public static final String LEGAL_NOTICE_STATE = "legalNoticeState";

    public static final String LEGAL_NOTICE_COUNTRY = "legalNoticeCountry";

    public static final String LEGAL_NOTICE_ZIPCODE = "legalNoticeZipcode";

    public static final String LEGAL_NOTICE_FAX = "legalNoticeFax";

    public static final String LEGAL_NOTICE_MOBILE = "legalNoticeMobile";

    public static final String LEGAL_NOTICE_PHONE = "legalNoticePhone";
    
    public static final String LEGAL_NOTICE_PHONE_EXTN = "legalNoticePhoneExtn";

    public static final String LEGAL_NOTICE_EMAIL = "legalNoticeEmail";

    public static final String MODIFIED_FA_RENEWAL_TERMS = "modifiedFaRenewalTerms";

    public static final String PATRIOT_CHECKED_ATTACHMENT = "patriotCheckedAttachment";

    public static final String INS_CERT_FILE = "insCertFile";

    public static final String SETTLEMENT_AGREEMENT = "settlementAgreement";

    public static final String INS_CERT_FILE_ATTACHMENT = "insCertFileAttachment";

    public static final String SETTLEMENT_AGREEMENT_ATTACHMENT = "settlementAgreementAttachment";

    public static final String COLLATERAL_ASSIGNMENT = "collateralAssignment";

    public static final String COLLATERAL_ASSIGNMENT_ATTACHMENT = "collateralAssignmentAttachment";

    public static final String SALES_PERSON_NAME = "salesPersonName";

    /* Field Names Added for AREA_CONTRACT */

    public static final String FIM_TT_LICENSE_AGREEMENT_ATTACHMENT = "fimTtLicenseAgreementAttachment";

    public static final String FIM_TT_PROMISSORY_AGREEMENT_ATTACHMENT = "fimTtPromissoryAgreementAttachment";

    public static final String FIM_TT_UFOC_RECIEPT_ATTACHMENT = "fimTtUfocRecieptAttachment";

    public static final String FIM_TT_GUARANTEE_ATTACHMENT = "fimTtGuaranteeAttachment";

    public static final String FIM_TT_STATE_ADDENDUM_ATTACHMENT = "fimTtStateAddendumAttachment";

    public static final String FIM_TT_NATL_ADV_AGMT_ATTACHMENT = "fimTtNatlAdvAgmtAttachment";

    public static final String FIM_TT_EFT_ATTACHMENT = "fimTtEftAttachment";

    /* Field Names for Table FIM_CUSTOMIZATION_FIELD */
    public static final String FIELD_ID = "fieldID";

    public static final String CUSTOM_FORM_ID = "customFormID";

    public static final String FIELD_NO = "fieldNo";

    public static final String NO_OF_ROWS = "noOfRows";

    public static final String NO_OF_COLS = "noOfCols";

    public static final String EXPORTABLE = "exportable";

    public static final String SEARCHABLE = "searchable";

    public static final String AVAILABLE = "available";

    /* Field Names for Table FIM_CUSTOMIZATION_FORM */
    public static final String FORM_ID = "formID";

    public static final String CUSTOM_FIELD_NO = "customFieldNo";

    /* Field Names for Table FIM_DOCUMENTS and AREA_DOCUMENTS */
    public static final String DOCUMENT_FIM_TITLE = "fimDocumentTitle";

    public static final String DOCUMENT_FIM_ATTACHMENT = "fimDocumentAttachment";
    
    public static final String DOCUMENT_UPLOADED = "documentUploaded";

    public static final String DOCUMENTS_ID = "documentsID";

    public static final String TAB_NAME = "tabName";

    public static final String TAB_PRIMARY_ID = "tabPrimaryId";

    public static final String FIM_DOCUMENTS_C1 = "fimDocumentsC1";

    public static final String FIM_DOCUMENTS_C2 = "fimDocumentsC2";

    public static final String FIM_DOCUMENTS_C3 = "fimDocumentsC3";

    public static final String FIM_DOCUMENTS_C4 = "fimDocumentsC4";

    public static final String FIM_DOCUMENTS_C5 = "fimDocumentsC5";

    public static final String FIM_DOCUMENTS_C6 = "fimDocumentsC6";

    public static final String FIM_DOCUMENTS_C7 = "fimDocumentsC7";

    public static final String FIM_DOCUMENTS_C8 = "fimDocumentsC8";

    public static final String FIM_DOCUMENTS_C9 = "fimDocumentsC9";

    public static final String FIM_DOCUMENTS_C10 = "fimDocumentsC10";

    public static final String AREA_TAB_PRIMARY_ID = "areaTabPrimaryId";
    
    public static final String AREA_FIELD_PREFIX="areaFieldPrefix";

    public static final String AREA_ORDER_NO = "areaOrderNo";

    public static final String AREA_TAB_NAME = "areaTabName";

    /* Field Names for Table FIM_EVENTS */
    // public static final String EVENTS_ID = "eventsID";
    public static final String EVENT_DATE = "eventDate";

    public static final String EVENT_AUTHOR = "eventAuthor";

    public static final String TYPE = "type";
    public static final String BACK_COVER = "backCover";
    
    public static final String ARTWORK_PROCESSED = "artworkProcessed";//ZCUB-20160205-225
    

    public static final String EVENT_SUMMARY = "eventSummary";

    public static final String EVENT_TIMESTAMP = "eventTimestamp";

    /* Field Names for Table FIM_FINANCIAL */
    // public static final String FINANCIAL_ID = "financialID";
    public static final String PAID_CASH_REVENUE_FLAG = "paidCashRevenueFlag";

    public static final String PAID_CASH_REFUNDABLE_FLAG = "paidCashRefundableFlag";

    public static final String PAID_NOTE_REVENUE_FLAG = "paidNoteRevenueFlag";

    public static final String PAID_NOTE_REFUNDABLE_FLAG = "paidNoteRefundableFlag";

    public static final String NOTE_AMOUNT = "noteAmount";

    public static final String NOTE_DUE_DATE = "noteDueDate";

    public static final String NOTE_PAID_DATE = "notePaidDate";

    public static final String ROYALTY_SURCHARGE = "royaltySurcharge";

    public static final String ADVERTISING_SURCHARGE = "advertisingSurcharge";

    public static final String VMI_CATEGORY = "vmiCategory";

    public static final String AR_BALANCE = "arBalance";

    public static final String MONTH = "month";

    public static final String ROYALTY_EARNED = "royaltyEarned";

    public static final String ROYALTY_RECEIVED = "royaltyReceived";

    public static final String ADVERTISING_EARNED = "advertisingEarned";

    public static final String ADVERTISING_RECEIVED = "advertisingReceived";

    public static final String RSP_PARTICIPANT = "rspParticipant";

    public static final String RSP_SURCHARGE = "rspSurcharge";

    public static final String RSP_BALANCE = "rspBalance";

    public static final String PRECISION_PRINTING = "precisionPrinting";

    public static final String ESTIMATED_SALES = "estimatedSales";

    public static final String REPORT_OWNING = "reportOwning";

    public static final String WEEKS_MISSING = "weeksMissing";

    public static final String NO_MISSING_WEEKS = "noMissingWeeks";

    public static final String MISCELLANEOUS = "miscellaneous";

    public static final String PAYMENT_AS_OF = "paymentAsOf";

    public static final String ROYALTY_AGE_RECEIVABLE = "royaltyAgeReceivable";

    public static final String ROYALTY_ESTIMATED = "royaltyEstimated";

    public static final String ROYALTY_REPORTED_DELINQUENT = "royaltyReportedDelinquent";

    public static final String ROYALTY_UNREPORTED_DELINQUENT = "royaltyUnreportedDelinquent";

    public static final String ADVERTISING_AGE_RECEIVABLE = "advertisingAgeReceivable";

    public static final String ADVERTISING_ESTIMATED = "advertisingEstimated";

    public static final String ADVERTISING_REPORTED_DELINQUENT = "advertisingReportedDelinquent";

    public static final String ADVERTISING_UNREPORTED_DELINQUENT = "advertisingUnreportedDelinquent";

    public static final String YEAR_1 = "year1";

    public static final String YEAR_2 = "year2";

    public static final String YEAR_3 = "year3";

    public static final String YEAR_4 = "year4";

    public static final String YEAR_5 = "year5";

    public static final String EFFECTIVE_RM_DATE = "effectiveDate";

    public static final String ROYALTY_PERC = "royaltyPercentage";

    public static final String CURRENT_PERC = "currentPercentage";

    public static final String PREVIOUS_PERC = "previousPercentage";

    public static final String CHANGE_RM_DATE = "changeDate";

    public static final String EXTRANET_FEE = "extranetFee";

    public static final String CMS_FEE = "cMSFee";

    public static final String ASP_FEE = "aSPFee";

    public static final String MONTHLY_MAIN_FEE = "monthlyMainFee";

    public static final String VPN_FEE = "vPNFee";

    public static final String CREDIT_STATUS = "creditStatus";

    public static final String OTHER_1 = "other1";

    public static final String OTHER_2 = "other2";

    public static final String OTHER_3 = "other3";

    public static final String ROYALTIES_PAYABLE_START_DATE = "royaltiesPayableStartDate";

    public static final String ROYALTY_R_TYPE = "royaltyRType";

    public static final String ROYALTY_TYPE_VALUE = "royaltyTypeValue";

    public static final String ROYALTY_PERIOD = "royaltyPeriod";

    public static final String ADVERTISING_FEE_PAYABLE_START_DATE = "advertisingFeePayableStartDate";

    public static final String ADVERTISING_TYPE = "advertisingType";

    public static final String ADVERTISING_TYPE_VALUE = "advertisingTypeValue";

    public static final String ADVERTISING_PERIOD = "advertisingPeriod";

    public static final String HEADING = "heading";

    public static final String EFFECTIVE_START_DATE = "effectiveStartDate";

    public static final String OTHER_FEE_TYPE = "otherFeeType";

    public static final String OTHER_FEE_VALUE = "otherFeeValue";

    public static final String OTHER_PERIOD = "otherPeriod";

    public static final String WEEKS_REMAINING = "weeksRemaining";

    public static final String LICENSE_FEE = "licenseFee";

    public static final String AREA_FINANCIAL_C1 = "areaFinancialC1";

    public static final String AREA_FINANCIAL_C2 = "areaFinancialC2";

    public static final String AREA_FINANCIAL_C3 = "areaFinancialC3";

    public static final String AREA_FINANCIAL_C4 = "areaFinancialC4";

    public static final String AREA_FINANCIAL_C5 = "areaFinancialC5";

    public static final String AREA_FINANCIAL_C6 = "areaFinancialC6";

    public static final String AREA_FINANCIAL_C7 = "areaFinancialC7";

    public static final String AREA_FINANCIAL_C8 = "areaFinancialC8";

    public static final String AREA_FINANCIAL_C9 = "areaFinancialC9";

    public static final String AREA_FINANCIAL_C10 = "areaFinancialC10";

    /* Field Names for Table FIM_GUARANTOR */
    // public static final String GUARANTOR_ID = "guarantorID";
    public static final String GURANTOR_TITLE = "gurantorTitle";

    public static final String GUARANTOR_CONTACT_NAME = "guarantorContactName";

    public static final String GUARANTOR_OWNERSHIP = "guarantorOwnership";

    public static final String RESIDENCY = "residency";

    public static final String ENTITY_TYPE_ID = "entityTypeID";

    public static final String TAXPAYER_ID = "taxpayerID";

    public static final String PERCENTAGE = "percentage";

    public static final String ENTITY_NAME = "entityName";

    /* Field Names for Table AREA_GUARANTOR */
    public static final String AREA_GUARANTOR_C1 = "areaGuarantorC1";

    public static final String AREA_GUARANTOR_C2 = "areaGuarantorC2";

    public static final String AREA_GUARANTOR_C3 = "areaGuarantorC3";

    public static final String AREA_GUARANTOR_C4 = "areaGuarantorC4";

    public static final String AREA_GUARANTOR_C5 = "areaGuarantorC5";

    public static final String AREA_GUARANTOR_C6 = "areaGuarantorC6";

    public static final String AREA_GUARANTOR_C7 = "areaGuarantorC7";

    public static final String AREA_GUARANTOR_C8 = "areaGuarantorC8";

    public static final String AREA_GUARANTOR_C9 = "areaGuarantorC9";

    public static final String AREA_GUARANTOR_C10 = "areaGuarantorC10";

    /* Field Names for Table FIM_INSURANCE */
    // public static final String INSURANCE_ID = "insuranceID";
    public static final String AGENCY_NAME = "agencyName";

    public static final String AGENCY_ADDRESS_ID = "agencyAddressID";

    public static final String CONTACT_ADDRESS_ID = "contactAddressID";

    public static final String INSURANCE_EFF_DATE = "insuranceEffDate";

    public static final String INSURANCE_EXP_DATE = "insuranceExpDate";

    public static final String POLICY_NO = "policyNo";

    public static final String RATING = "rating";
    
    public static final String RANK = "rank";

    public static final String GL_REQUIRED = "glRequired";

    public static final String GL_PRESENT = "glPresent";

    public static final String REPLACEMENT_REQUIRED = "replacementRequired";

    public static final String REPLACEMENT_PRESENT = "replacementPresent";

    public static final String VEHICLE_REQUIRED = "vehicleRequired";

    public static final String VEHICHLE_PRESENT = "vehichlePresent";

    public static final String INTERPRETATION_REQUIRED = "interpretationRequired";

    public static final String INTERPRETATION_PRESENT = "interpretationPresent";

    public static final String WC_AMOUNT = "wcAmount";

    public static final String WC_PRESENT = "wcPresent";

    public static final String INSURED_REQUIREMENTS = "insuredRequirements";

    public static final String INSURED_CORRECT = "insuredCorrect";

    public static final String NOTICE = "notice";

    public static final String WAIVER = "waiver";
    
    public static final String REVENUE = "revenue";
    
    public static final String STOCK_SYMBOL = "stockSymbol";
    
    public static final String COMMENTS = "comments";
    public static final String FIM_FLAG = "fimFlag";
    
    public static final String COMMENTS_ATTACHMENT = "commentsAttachment";

    public static final String COMMENTS_ATTITLE = "commentsAttitle";

    public static final String INSURANCE_COMPANY_NAME = "insuranceCompanyName";

    public static final String PFL_INSURED = "pflInsured";

    public static final String AREA_DEVELOPER_INSURED = "areaDeveloperInsured";

    public static final String TOTAL_COVERAGE = "totalCoverage";

    /* Field Names for Table TMS_PAYMENT_MODE */
    public static final String PAYMENT_MODE_ID = "paymentModeID";

    public static final String CC_FLAG = "ccFlag";

    public static final String CHQ_FLAG = "chqFlag";

    public static final String CASH_FLAG = "cashFlag";

    public static final String TERMS_CONDITIONS = "termsConditions";

    public static final String TERMS_ID = "termsID";

    public static final String TERMS_CONDITIONS_E = "termsConditionsE";

    /* FIELDNAMES FOR AUTHORIZE.NET INTEGRATION CT_MARCHANT_CONFIGURATION ADDED BY YASHU P_E_OxiFre-20110915-043*/
    public static final String MARCHANT_ID = "marchantID";
    public static final String MARCHANT_LOGIN_ID = "marchantLoginID";
    public static final String MARCHANT_TRANSACTION_KEY = "marchantTransactionKey";
    public static final String ACCOUNT_TYPE = "accountType";
    //public static final String HOST_NAME = "hostName";
    //public static final String STORE_NO = "storeNo";
   // public static final String USER_ID = "userID";
    //public static final String PASSWORD = "password";
    //public static final String CONFIRM_PASSWORD = "confirmPassword";
    //ADDED BY UMA JEENA



      public static final String TERM_NO = "termNO";
        public static final String TERMS = "terms";

     public static final String NET_DUE_BEFORE = "netDueBefore";
      public static final String DISCOUNT_DAYS_BEFORE= "discountDaysBefore";
      public static final String NET_DUE = "netDue";
      public static final String DUE_DAYS_FOR_NEXT_MONTH= "dueDaysForNextMonth";
      public static final String DISCOUNT_DAYS = "discountDays";
    //  public static final String ADDED_ON = "addedON";
     //  public static final String ADDED_BY = "addedBY";







    //ADDED BY SAURABH VAISH FOR PETS_SERVICES
    public static final String FAVORITE_SERVICE_ID = "favoriteServiceID";
    public static final String SERVICE_ID = "serviceID";
    
    /* Added By Veerpal Singh on 02 SEP-2011   ENH_71BBCME09 */
    public static String CONFIGURE_CATEGORY = "configureCategory";
    /* ENH_71BBCME09 Ends*/
    public static String CONFIGURE_TAX = "configureTax";
    public static String CONFIGURE_DISCOUNT = "configureDiscount";
    public static String DEFAULT_JS = "defaultJS";
    public static String IS_MODIFY = "isModify";
    public static String CTS_MAP = "ctsMap";
    public static String TIME_SPENT = "timeSpent";
    public static String HOURS_SPENT = "hoursSpent";
    public static String MINUTES_SPENT = "minutesSpent";
    public static String SECONDS_SPENT = "secondsSpent"; 
    public static String  TMS_PAYMENT_CONFIGURATION  = "tmsPaymentConfiguration";
    public static String EXPECTED_ATTENDANCE= "expectedAttendance";
    public static String CHECK_ID = "checkID";
    public static String SEND_INVC_DATE = "sendInvcDate";
    public static String SEND_INVT_DATE = "sendInvtDate";
    public static String FOLL_CALL_DATE = "follCallDate";
    public static String SEND_CLNT_DATE = "sendClntDate";
    public static String DATASET_COURSE_USER = "datasetCourseCheck";
    public static  String DATASET_CAREGIVER = "datasetCaregiver";
    public static  String DATASET_SKILLED_USERS = "datasetSkilledUsers";
    public static  String DATASET_TASK_VALUES = "datasetTaskValues";
    public static  String DATASET_CAREGIVER_ALL = "datasetCaregiverAll";
    
    /* Field Names for Table AREA_INSURANCE */
    public static final String AREA_INSURANCE_C1 = "areaInsuranceC1";

    public static final String AREA_INSURANCE_C2 = "areaInsuranceC2";

    public static final String AREA_INSURANCE_C3 = "areaInsuranceC3";

    public static final String AREA_INSURANCE_C4 = "areaInsuranceC4";

    public static final String AREA_INSURANCE_C5 = "areaInsuranceC5";

    public static final String AREA_INSURANCE_C6 = "areaInsuranceC6";

    public static final String AREA_INSURANCE_C7 = "areaInsuranceC7";

    public static final String AREA_INSURANCE_C8 = "areaInsuranceC8";

    public static final String AREA_INSURANCE_C9 = "areaInsuranceC9";

    public static final String AREA_INSURANCE_C10 = "areaInsuranceC10";

    public static final String FIM_RR_PFL_INSURED = "fimRrPflInsured";

    public static final String FIM_RR_AREA_DEVELOPER_INSURED = "fimRrAreaDeveloperInsured";

    public static final String FIM_TT_WC_AMOUNT = "fimTtWcAmount";

    public static final String FIM_RR_REPLACEMENT_PRESENT = "fimRrReplacementPresent";

    public static final String FIM_RR_INSURED_CORRECT = "fimRrInsuredCorrect";

    public static final String FIM_RR_NOTICE = "fimRrNotice";

    public static final String FIM_RR_INTERPRETATION_PRESENT = "fimRrInterpretationPresent";

    public static final String FIM_RR_WAIVER = "fimRrWaiver";

    /* Field Names for Table FIM_LEGAL_VIOLATION */
    // public static final String VIOLATION_ID = "violationID";
    public static final String COMPLAINT_ATTITLE = "complaintAttitle";

    public static final String CURED_DATE = "curedDate";

    /* Field Names for Table FIM_LENDER */
    // public static final String LENDER_ID = "lenderID";
    public static final String COMFORT_LETTER_FORM = "comfortLetterForm";

    public static final String COMFORT_LETTER_FORM_ATTACHMENT = "comfortLetterFormAttachment";

    public static final String COMFORT_LETTER_FORM_ATTITLE = "comfortLetterFormAttitle";

    public static final String COMFORT_LETTER_DATE = "comfortLetterDate";

    public static final String COMFORT_AGREEMENT = "comfortAgreement";

    public static final String DATE_COMFORT_AGREEMENT = "dateComfortAgreement";

    public static final String DATE_COMFORT_AGREEMENT_INFO = "dateComfortAgreementInfo";

    public static final String LENDER_CONTACT_TITLE_ONE = "lenderContactTitleOne";

    public static final String LENDER_CONTACT_TITLE_TWO = "lenderContactTitleTwo";

    public static final String CONTACT_ONE_NAME_FIRST = "contactOneNameFirst";

    public static final String CONTACT_ONE_NAME_MIDDLE = "contactOneNameMiddle";

    public static final String CONTACT_ONE_NAME_LAST = "contactOneNameLast";

    public static final String CONTACT_TWO_NAME_FIRST = "contactTwoNameFirst";

    public static final String CONTACT_TWO_NAME_MIDDLE = "contactTwoNameMiddle";

    public static final String CONTACT_TWO_NAME_LAST = "contactTwoNameLast";

    public static final String CONTACT_1_PHONE = "contact1Phone";

    public static final String CONTACT_2_PHONE = "contact2Phone";

    public static final String CONTACT_1_FAX = "contact1Fax";

    public static final String CONTACT_2_FAX = "contact2Fax";

    public static final String CONTACT_1_EMAIL = "contact1Email";

    public static final String CONTACT_2_EMAIL = "contact2Email";

    public static final String COLLATERAL_ASSIGNMENT_EXPIRATION_DATE = "collateralAssignmentExpirationDate";

    public static final String CONTACT_1_COUNTRY = "contact1Country";

    public static final String CONTACT_2_COUNTRY = "contact2Country";

    /* Field Names for Table AREA_LENDER */

    public static final String FIM_TT_COMFORT_LETTER_FORM_ATTITLE = "fimTtComfortLetterFormAttitle";

    public static final String FIM_TT_COMFORT_LETTER_FORM_ATTACHMENT = "fimTtComfortLetterFormAttachment";

    public static final String FIM_TT_LENDER_CONTACT_TITLE_ONE = "fimTtLenderContactTitleOne";

    public static final String FIM_TT_LENDER_CONTACT_TITLE_TWO = "fimTtLenderContactTitleTwo";

    public static final String AREA_LENDER_C1 = "areaLenderC1";

    public static final String AREA_LENDER_C2 = "areaLenderC2";

    public static final String AREA_LENDER_C3 = "areaLenderC3";

    public static final String AREA_LENDER_C4 = "areaLenderC4";

    public static final String AREA_LENDER_C5 = "areaLenderC5";

    public static final String AREA_LENDER_C6 = "areaLenderC6";

    public static final String AREA_LENDER_C7 = "areaLenderC7";

    public static final String AREA_LENDER_C8 = "areaLenderC8";

    public static final String AREA_LENDER_C9 = "areaLenderC9";

    public static final String AREA_LENDER_C10 = "areaLenderC10";

    /* Field Names for Table FIM_LICENSE_AGREEMENT */
    // public static final String LICENSE_AGREEMENT_ID = "licenseAgreementID";
    public static final String INITIAL_TERM = "initialTerm";

    public static final String RENEWAL_TERM_FIRST = "renewalTermFirst";

    public static final String RENEWAL_TERM_SECOND = "renewalTermSecond";

    public static final String RENEWAL_TERM_THIRD = "renewalTermThird";

    public static final String RENEWAL_DUE_DATE_FIRST = "renewalDueDateFirst";

    public static final String RENEWAL_DUE_DATE_SECOND = "renewalDueDateSecond";

    public static final String RENEWAL_DUE_DATE_THIRD = "renewalDueDateThird";

    public static final String RENEWAL_FEE_FIRST = "renewalFeeFirst";

    public static final String RENEWAL_FEE_SECOND = "renewalFeeSecond";

    public static final String RENEWAL_FEE_THIRD = "renewalFeeThird";

    public static final String RENEWAL_FEE_PAID_FIRST_DATE = "renewalFeePaidFirstDate";

    public static final String RENEWAL_FEE_PAID_SECOND_DATE = "renewalFeePaidSecondDate";

    public static final String RENEWAL_FEE_PAID_THIRD_DATE = "renewalFeePaidThirdDate";

    public static final String INITIAL_FEE = "initialFee";

    public static final String ROYALTY_RATE = "royaltyRate";

    public static final String ROYALTY_MINIMUM_GALLONS = "royaltyMinimumGallons";

    public static final String ADVERTISING_RATE = "advertisingRate";

    public static final String ADVERTISING_MINIMUM_GALLONS = "advertisingMinimumGallons";

    public static final String TRANSFER_FEE = "transferFee";

    public static final String PRIMARY_CONTACT_NAME1 = "primaryContactName1";

    public static final String PRIMARY_CONTACT_NAME2 = "primaryContactName2";

    public static final String OTHER_ADDENDUM = "otherAddendum";

    public static final String RIGHTS_OF_FIRST_REFUSAL = "rightsOfFirstRefusal";

    public static final String PROTECTED_TERRITORY = "protectedTerritory";

    public static final String DATE_EXECUTED = "dateExecuted";

    public static final String EFFECTIVE_DATE = "effectiveDate";
    
    
    public static final String DATE_OPENED = "dateOpened";

    public static final String OPENING_DATE = "openingDate";

    public static final String OPENING_DATE_FROM = "openingDateFrom";

    public static final String OPENING_DATE_TO = "openingDateTo";
    
    public static final String TIME_OPENED = "timeOpened";

    public static final String EXPIRATION_DATE = "expirationDate";

    public static final String DISPLAY_DATE = "displayDate";

    public static final String VERSION_NO = "versionNo";

    public static final String RELATED_STORES = "relatedStores";

    public static final String LICENSE_NUMBER = "licenseNumber";

    public static final String NCNERNY_RELEASE_ATTITLE = "ncnernyReleaseAttitle";

    public static final String RENEWAL_RELEASE_ATTITLE = "renewalReleaseAttitle";

    public static final String OTHER_RELEASE_ATTITLE = "otherReleaseAttitle";

    public static final String SUPERMARKET_ADDENDUM_ATTITLE = "supermarketAddendumAttitle";

    public static final String ASSIGNMENT_AGREEMENT_ATTITLE = "assignmentAgreementAttitle";

    public static final String ASSIGNING_FRANCHISEE_ID = "assigningFranchiseeID";

    public static final String STORE_SOLD_DATE = "storeSoldDate";

    public static final String REGION = "region";

    public static final String GRAND_OPENING_FEE_BY_CARVEL = "grandOpeningFeeByCarvel";

    public static final String FRANCHISE_REQUEST = "franchiseRequest";

    public static final String FRANCHISE_REQUEST_DATE = "franchiseRequestDate";

    public static final String REMODEL_SCHEDULE = "remodelSchedule";

    public static final String REMODEL_SCHEDULE_DATE = "remodelScheduleDate";

    public static final String SUCCESSOR_FA = "successorFa";

    public static final String FA_CODE = "faCode";

    public static final String APPROVED_DATE = "approvedDate";

    public static final String CLOSING_DATE = "closingDate";

    public static final String FUTURE_LOST_PROFITS_MULTIPLIER = "futureLostProfitsMultiplier";

    public static final String SALESPERSON = "salesperson";

    public static final String PREVIOUS_LICENSE_NUMBER = "previousLicenseNumber";

    public static final String RELATED_CENTER = "relatedCenter";

    public static final String FUTURE = "future";

    public static final String INITIAL_PURCHASE_DATE = "initialPurchaseDate";

    public static final String FRANCHISE_FEE = "franchiseFee";

    public static final String COURTESY_REMINDER_DATE = "courtesyReminderDate";

    public static final String COURTESY_REMINDER = "courtesyReminder";

    public static final String SUCCESSOR_FA_DATE = "successorFaDate";

    public static final String APPROVAL_LETTER = "approvalLetter";

    public static final String APPROVAL_LETTER_DATE = "approvalLetterDate";

    public static final String NOT_APPROVED = "notApproved";

    public static final String NOT_APPROVED_DATE = "notApprovedDate";

    public static final String SUCCESSOR_FA_2 = "successorFa2";

    public static final String OPENING_DATE_REQUIRED = "openingDateReqd";

    public static final String REQUIRED_OPENING_DATE = "reqdOpeningDate";

    public static final String DESCRIPTION_1 = "description1";

    public static final String AMOUNT_1 = "amount1";

    public static final String AMOUNT_TERM_1 = "amountTerm1";

    public static final String INTEREST_1 = "interest1";

    public static final String COMMENT_1 = "comment1";

    public static final String DESCRIPTION_2 = "description2";

    public static final String AMOUNT_2 = "amount2";

    public static final String AMOUNT_TERM_2 = "amountTerm2";

    public static final String INTEREST_2 = "interest2";

    public static final String COMMENT_2 = "comment2";

    public static final String DESCRIPTION_3 = "description3";

    public static final String AMOUNT_3 = "amount3";

    public static final String AMOUNT_TERM_3 = "amountTerm3";

    public static final String INTEREST_3 = "interest3";

    public static final String COMMENT_3 = "comment3";

    /* Field Names for Table AREA_LICENSE_AGREEMENT */
    public static final String FIM_TT_INITIAL_FEE = "fimTtInitialFee";

    public static final String FIM_TT_ROYALTY_RATE = "fimTtRoyaltyRate";

    public static final String FIM_TT_ROYALTY_MINIMUM_GALLONS = "fimTtRoyaltyMinimumGallons";

    public static final String FIM_TT_ADVERTISING_RATE = "fimTtAdvertisingRate";

    public static final String FIM_TT_ADVERTISING_MINIMUM_GALLONS = "fimTtAdvertisingMinimumGallons";

    public static final String FIM_TT_TRANSFER_FEE = "fimTtTransferFee";

    public static final String FIM_TT_OPENING_DATE_REQUIRED = "fimTtOpeningDateRequired";

    public static final String AREA_LICENSE_AGREEMENT_C1 = "areaLicenseAgreementC1";

    public static final String AREA_LICENSE_AGREEMENT_C2 = "areaLicenseAgreementC2";

    public static final String AREA_LICENSE_AGREEMENT_C3 = "areaLicenseAgreementC3";

    public static final String AREA_LICENSE_AGREEMENT_C4 = "areaLicenseAgreementC4";

    public static final String AREA_LICENSE_AGREEMENT_C5 = "areaLicenseAgreementC5";

    public static final String AREA_LICENSE_AGREEMENT_C6 = "areaLicenseAgreementC6";

    public static final String AREA_LICENSE_AGREEMENT_C7 = "areaLicenseAgreementC7";

    public static final String AREA_LICENSE_AGREEMENT_C8 = "areaLicenseAgreementC8";

    public static final String AREA_LICENSE_AGREEMENT_C9 = "areaLicenseAgreementC9";

    public static final String AREA_LICENSE_AGREEMENT_C10 = "areaLicenseAgreementC10";

    /* Field Names for Table FIM_LICENSEE */
    public static final String LICENSE_ID = "licenseID";

    public static final String STATE_RESIDENCY = "stateResidency";

    public static final String ETHNIC_BACKGROUND = "ethnicBackground";

    public static final String CFAC_MEMBER = "cfacMember";

    public static final String CFAC_MARKETING = "cfacMarketing";

    public static final String CFAC_PURCHASING = "cfacPurchasing";

    public static final String CFAC_EXECUTIVE = "cfacExecutive";

    public static final String ADD_LICENSEE = "addLicensee";

    public static final String FIM_LICENSEE_C1 = "fimLicenseeC1";

    public static final String FIM_LICENSEE_C2 = "fimLicenseeC2";

    public static final String FIM_LICENSEE_C3 = "fimLicenseeC3";

    public static final String FIM_LICENSEE_C4 = "fimLicenseeC4";

    public static final String FIM_LICENSEE_C5 = "fimLicenseeC5";

    public static final String FIM_LICENSEE_C6 = "fimLicenseeC6";

    public static final String FIM_LICENSEE_C7 = "fimLicenseeC7";

    public static final String FIM_LICENSEE_C8 = "fimLicenseeC8";

    public static final String FIM_LICENSEE_C9 = "fimLicenseeC9";

    public static final String FIM_LICENSEE_C10 = "fimLicenseeC10";

    /* Field Names for Table FIM_MARKETING */
    // public static final String MARKETING_ID = "marketingID";
    public static final String GRAND_OPENING_REQUIRED = "grandOpeningRequired";

    public static final String GRAND_OPENING_COMPLETED_DATE = "grandOpeningCompletedDate";

    public static final String CAMPAIGN_NAME = "campaignName";

    public static final String CAMPAIGN_PARTICIPATION = "campaignParticipation";

    public static final String COUPON_REDEMPTION = "couponRedemption";

    public static final String PROGRAM_NAME = "programName";

    public static final String PROGRAM_PARTICIPATION = "programParticipation";

    public static final String PIONEER_STORE = "pioneerStore";

    public static final String PARTICIPATING_STORE = "participatingStore";

    public static final String MENU_BOARD_SYSTEM_TYPE = "menuBoardSystemType";

    public static final String OUTSIDE_BANNER_POSSIBLE = "outsideBannerPossible";

    public static final String DOMINANT_INFLUENCE_AREA = "dominantInfluenceArea";

    public static final String MARKET_TYPE = "marketType";

    public static final String SERVICES_SUPERMARKETS = "servicesSupermarkets";

    public static final String COBRANDED_STORE = "cobrandedStore";

    public static final String ADDITIONAL_INFORMATION = "additionalInformation";

    public static final String POWERMILL_AGREEMENT = "powermillAgreement";

    public static final String OTHER_AGREEMENTS = "otherAgreements";

    /* Field Names for Table FIM_OWNERS */
    public static final String FRANCHISE_OWNER_ID = "franchiseOwnerID";

    public static final String OWNER_TITLE = "ownerTitle";

    public static final String OWNER_FIRST_NAME = "ownerFirstName";

    public static final String OWNER_MIDDLE_NAME = "ownerMiddleName";

    public static final String OWNER_LAST_NAME = "ownerLastName";

    public static final String STORE_OWNED = "storeOwned";

    public static final String STORE_OWNED_PERCENTAGE = "storeOwnedPercentage";

    public static final String TYPE_OF_OWNERSHIP = "typeOfOwnership";

    public static final String OWNER_DELETED = "ownerDeleted";

    public static final String OTHER_OWNERS = "otherOwners";

    public static final String FIM_OWNERS_C1 = "fimOwnersC1";

    public static final String FIM_OWNERS_C2 = "fimOwnersC2";

    public static final String FIM_OWNERS_C3 = "fimOwnersC3";

    public static final String FIM_OWNERS_C4 = "fimOwnersC4";

    public static final String FIM_OWNERS_C5 = "fimOwnersC5";

    public static final String FIM_OWNERS_C6 = "fimOwnersC6";

    public static final String FIM_OWNERS_C7 = "fimOwnersC7";

    public static final String FIM_OWNERS_C8 = "fimOwnersC8";

    public static final String FIM_OWNERS_C9 = "fimOwnersC9";

    public static final String FIM_OWNERS_C10 = "fimOwnersC10";

    //P_FIM_E_CR:503_MultipleEmployeeLogin by devendra starts
    /* Field Names for Table FIM_EMPLOYEES */
    public static final String EMPLOYEE_NO = "employeeNo";
    public static final String EMPLOYEE_ID = "employeeID";
    public static final String EMPLOYEE_DELETED = "employeeDeleted";
    public static final String FIM_EMPLOYEES_C1 = "fimEmployeesC1";
    public static final String FIM_EMPLOYEES_C2 = "fimEmployeesC2";
    public static final String FIM_EMPLOYEES_C3 = "fimEmployeesC3";
    public static final String FIM_EMPLOYEES_C4 = "fimEmployeesC4";
    public static final String FIM_EMPLOYEES_C5 = "fimEmployeesC5";
    public static final String FIM_EMPLOYEES_C6 = "fimEmployeesC6";
    public static final String FIM_EMPLOYEES_C7 = "fimEmployeesC7";
    public static final String FIM_EMPLOYEES_C8 = "fimEmployeesC8";
    public static final String FIM_EMPLOYEES_C9 = "fimEmployeesC9";
    public static final String FIM_EMPLOYEES_C10 = "fimEmployeesC10";
    //P_FIM_E_CR:503_MultipleEmployeeLogin by devendra ends
    
    
    /* Field Names for Table FS_SCHEDULE_LEAD_OWNER */
    public static final String PICK = "pick";

    public static final String REQUEST_TIME = "requestTime";

    /* Field Names for Table FIM_QA */
    // public static final String QA_ID = "qaID";
    public static final String INSPECTION_DATE = "inspectionDate";

    public static final String INSPECTION_TYPE_ID = "inspectionTypeID";

    public static final String INSPECTOR_NAME = "inspectorName";

    public static final String EXPERIENCE_SCORE = "experienceScore";

    public static final String EXPERIENCE_GRADE = "experienceGrade";

    public static final String ASSURANCE_SCORE = "assuranceScore";

    public static final String ASSURANCE_GRADE = "assuranceGrade";

    public static final String RE_INSPECTION_DATE = "reInspectionDate";

    public static final String NEXT_INSPECTION_DATE = "nextInspectionDate";

    public static final String ATTACHMENTS = "attachments";

    public static final String REPORT_ATTACHMENTS_ATTITLE = "reportAttachmentsAttitle";

    public static final String FIELD_CONSULTANT_ID = "fieldConsultantId";

    public static final String FACILITY_SCORE = "facilityScore";

    public static final String ADMIN_SCORE = "adminScore";

    public static final String CRITICAL = "critical";

    public static final String CURE_DATE = "cureDate";

    public static final String MTM = "mtm";

    /* Field Names for Table FIM_REAL_ESTATE */
    // public static final String REAL_ESTATE_ID = "realEstateID";
    public static final String OWNED_LEASED = "ownedLeased";

    public static final String OWNED_LEASED_ATTACHMENT = "ownedLeasedAttachment";

    public static final String OWNED_LEASED_ATTITLE = "ownedLeasedAttitle";

    public static final String LANDLORD_NAME = "landlordName";

    public static final String SQUARE_FOOTAGE = "squareFootage";

    public static final String PREMISES_TYPE = "premisesType";

    public static final String LEASE_SIGNED_DATE = "leaseSignedDate";

    public static final String LEASE_COMMENCEMENT_DATE = "leaseCommencementDate";

    public static final String MONTHLY_RENT = "monthlyRent";

    public static final String RENT_INCREASES = "rentIncreases";

    public static final String RENEWAL_OPTIONS = "renewalOptions";

    public static final String RENEWAL_FEE_PAID_FIRST = "renewalFeePaidFirst";

    public static final String RENEWAL_FEE_PAID_SECOND = "renewalFeePaidSecond";

    public static final String RENEWAL_FEE_PAID_THIRD = "renewalFeePaidThird";

    public static final String TENANT_FIRST_NAME = "tenantFirstName";

    public static final String SUBLESSOR_CONTACT_TITLE = "sublessorContactTitle";

    public static final String SUBLESSOR_FIRST_NAME = "sublessorFirstName";

    public static final String SUBLESSOR_MIDDLE_NAME = "sublessorMiddleName";

    public static final String SUBLESSOR_LAST_NAME = "sublessorLastName";

    public static final String ACAL = "acal";

    public static final String TENANT_MIDDLE_NAME = "tenantMiddleName";

    public static final String TENANT_LAST_NAME = "tenantLastName";

    public static final String TENANT_TITLE = "tenantTitle";

    /* Field Names for Table FIM_RENEWAL */
    // public static final String RENEWAL_ID = "renewalID";
    public static final String CURRENT_STATUS = "currentStatus";

    public static final String AS_OF = "asOf";

    public static final String PERSON_SIGNING = "personSigning";

    public static final String NEW_EXPIRATION_DATE = "newExpirationDate";

    public static final String NINETY_DAY_NOTICE = "ninetyDayNotice";

    public static final String RENEWAL_PACKAGE_SENT = "renewalPackageSent";

    public static final String MISSING_DOCS = "missingDocs";

    public static final String RENEWAL_MATERIALS = "renewalMaterials";

    /* Field Names for Table FIM_TERMINATION */

    // public static final String TERMINATION_ID = "terminationID";
    public static final String REASON = "reason";

    public static final String ACTION_DATE = "actionDate";

    public static final String FOLLOW_UP_DATE = "followUpDate";

    public static final String EFFECTIVE_ACTION_DATE = "effectiveActionDate";

    public static final String COMMENTS_ATTACHMENTS_ATTITLE = "commentsAttachmentsAttitle";

    public static final String AMOUNT = "amount";
    
    public static final String AMOUNT_RECEIVED = "amountReceived";
    
    public static final String AMOUNT_LOCAL = "amountLocal";
    
    public static final String AVG_AMOUNT = "avgAmount";

    public static final String SHIPPING_AMOUNT = "shippingAmount";

    public static final String COLLECTED = "collected";

    public static final String LENDER_NOTICE = "lenderNotice";

    public static final String TERMINATED_DATE = "terminatedDate";

    public static final String TYPE_OF_ACTION = "typeOfAction";

    public static final String LIQUIDATED_DAMAGES = "liquidatedDamages";

    public static final String BANKRUPTCY_TYPE = "bankruptcyType";

    public static final String ENTITY_CLAIMING_BK = "entityClaimingBk";

    public static final String REPRESENTATIVE = "repersentative";

    public static final String FRANCHISE_ON_COUNSIL = "franchiseOnCounsil";

    public static final String CASE_BK = "caseBk";

    public static final String COLLECTION_AGENCY = "collectionAgency";

    public static final String NAME1 = "name1";

    public static final String NAME2 = "name2";

    /* Field Names for Table FIM_TRAINING */

    // public static final String TRAINING_ID = "trainingID";
    public static final String TRAINING_PROGRAM = "trainingProgram";

    public static final String INSTRUCTOR = "instructor";

    public static final String LOCATION_TR = "location";

    public static final String ATTENDEE = "attendee";

    public static final String ATTENDEE_TITLE = "attendeeTitle";

    public static final String COMPLETION_DATE_TR = "completionDate";

    public static final String SCORE = "score";

    public static final String GRADE = "grade";

    public static final String FIM_TT_TRAINING_TYPE = "fimTttrainingType";

    /* Field Names for Table FIM_TRANSFER */
    public static final String TRANSFER_ID = "transferId";

    public static final String NEW_CENTER_NO = "newCenterNo";

    public static final String CENTER_NO = "centerNo";

    public static final String BUYER1 = "buyer1";

    public static final String BUYER2 = "buyer2";

    public static final String BUYER_SIGNER = "buyerSigner";

    public static final String BUYER_SIGNER_TITLE = "buyerSignerTitle";

    public static final String SELLER = "seller";

    public static final String SHAREHOLDER = "shareholder";

    public static final String SELLER_SIGNER = "sellerSigner";

    public static final String SELLER_SIGNER_TITLE = "sellerSignerTitle";

    public static final String FDD_RECEIPT = "fddReceipt";

    public static final String RENEWAL_TAB = "renewalTab";

    public static final String TRANSFER_FEE_NUMERICAL = "transferFeeNumerical";

    public static final String AD_APPROVAL = "adApproval";

    public static final String FINANCIAL_PREQUALIFICATION = "financialPrequalification";

    public static final String RETAIN_AS_TERMINATED = "retainAsTerminated";

    public static final String TRANSFER_AGREEMENT = "transferAgreement";

    public static final String TRANSFER_ADDENDUM = "transferAddendum";

    public static final String LEASE = "lease";

    public static final String PTAF_AGREEMENT = "ptafAgreement";

    public static final String GUARANTY = "guaranty";

    public static final String CERT_OF_INSURANCE = "certOfInsurance";

    public static final String FTP_SENT = "ftpSent";

    public static final String FTP_EXPIRATION = "ftpExpiration";

    public static final String TF_FA_NEW_EXPIRATION = "tfFaNewExpiration";

    public static final String BUY_SELL_AGREEMENT = "buySellAgreement";

    public static final String CORPORATE_DOCUMENTS = "corporateDocuments";

    public static final String CQR = "cqr";

    public static final String STATE_OF_FORMATION_RESIDENCY = "stateOfFormationResidency";

    public static final String BUYER_EXISTING_OR_NEW_FRANCHISEE = "buyerExistingOrNewFranchisee";

    public static final String FOLLOW_UP = "followUp";

    public static final String OTHER_NAME = "otherName";

    public static final String CO_OP_DEBT = "coOpDebt";

    public static final String CO_OP_PAYMENT_PAID = "coOpPaymentPaid";

    public static final String FINANCIAL_DEMAND = "financialDemand";

    public static final String PTAC_PAYMENT_DUE = "ptacPaymentDue";

    public static final String TRANSFER_AFFIDAVIT = "transferAffidavit";

    public static final String UFOC_DISCLOSURE_QUESTIONAIRE = "ufocDisclosureQuestionaire";

    public static final String SOFTWARE_LICENSE = "softwareLicense";

    public static final String EXECUTIVE_ORDER = "executiveOrder";

    public static final String CONSENT_AGREEMENT = "consentAgreement";

    public static final String ASSIGNED_AREA = "assignedArea";

    public static final String PROMISSORY_NOTE = "promissoryNote";

    public static final String VETFRAN = "vetfran";

    public static final String PFL_NO_CHANGE_CERTIFICATE = "pflNoChangeCertificate";

    public static final String FA_EFFECTIVE_DATE = "faEffectiveDate";

    public static final String FA_EXPIRATION = "faExpiration";

    public static final String FINANCIAL_PRE_QUALIFICATION_APPROVAL = "financialPreQualificationApproval";

    public static final String SBA_ADDENDUM = "sbaAddendum";

    public static final String EFT = "eft";

    public static final String OWNERS_AFFIDAVIT = "ownersAffidavit";

    public static final String GETTY_ADDENDUM = "gettyAddendum";

    public static final String TF_FA_EFFECTIVE_DATE = "tfFaEffectiveDate";

    public static final String SEND_LEGAL_DOCS_TO = "sendLegalDocsTo";

    public static final String SEND_DOCS_TO = "sendDocsTo";

    public static final String ARTICLES_OF_INCORPORATION = "articlesOfIncorporation";

    public static final String OWNED = "owned";

    public static final String OWNED_PERCENTAGE = "ownedPercentage";

    public static final String CONTACT_INFO_TAB = "contactInfoTab";

    public static final String OTHER_ADDRESS_TAB = "otherAddressTab";

    public static final String CONTACT_HISTORY_TAB = "contactHistoryTab";

    public static final String LEGAL_VIOLATION_TAB = "legalViolationTab";

    public static final String PREVIOUS_FRANCHISEE_TAB = "previousFranchiseeTab";

    public static final String LICENSEES_TAB = "licenseesTab";

    public static final String OWNERS_TAB = "ownersTab";

    public static final String GUARANTORS_TAB = "guarantorsTab";

    public static final String LENDER_TAB = "lenderTab";

    public static final String LICENSE_AGREEMENT_TAB = "licenseAgreementTab";

    public static final String TRANSFER_TAB = "transferTab";

    public static final String INSURANCE_TAB = "insuranceTab";

    public static final String MARKETING_TAB = "marketingTab";

    public static final String QA_HISTORY_TAB = "qaHistoryTab";

    public static final String CUSTOMER_COMPLAINT_TAB = "customerComplaintTab";

    public static final String ENFORCEMENT_TAB = "enforcementTab";

    public static final String FINANCIAL_TAB = "financialTab";

    public static final String REAL_ESTATE_TAB = "realEstateTab";

    public static final String EVENTS_TAB = "eventsTab";

    public static final String DEFAULT_TERMINATION_TAB = "defaultTerminationTab";

    public static final String BANKCRUPTCY_TAB = "bankcruptcyTab";

    public static final String CONTRAT_SIGNING_TAB = "contratSigningTab";

    public static final String TRANSFER_TYPE = "transferType";

    public static final String TRANSFER_FEE_PD = "transferFeePd";

    public static final String FIM_TRANSFER_C1 = "fimTransferC1";

    public static final String FIM_TRANSFER_C2 = "fimTransferC2";

    public static final String FIM_TRANSFER_C3 = "fimTransferC3";

    public static final String FIM_TRANSFER_C4 = "fimTransferC4";

    public static final String FIM_TRANSFER_C5 = "fimTransferC5";

    public static final String FIM_TRANSFER_C6 = "fimTransferC6";

    public static final String FIM_TRANSFER_C7 = "fimTransferC7";

    public static final String FIM_TRANSFER_C8 = "fimTransferC8";

    public static final String FIM_TRANSFER_C9 = "fimTransferC9";

    public static final String FIM_TRANSFER_C10 = "fimTransferC10";

    public static final String TRANSFER_TABS = "transferTabs";

    /* Field Names for Table FIM_ENTITY_DETAIL */
    // public static final String FIM_ENTITY_ID = "fimEntityID";
    public static final String STATE_OF_FORMATION = "stateOfFormation";

    public static final String DATE_OF_FORMATION = "dateOfFormation";

    public static final String FORMAT_DOCUMENT_RECEIVED = "formatDocumentReceived";

    public static final String GOVERNING_DOCUMENT_RECEIVED = "governingDocumentReceived";

    public static final String FORMAT_UPLOADED_DOCUMENT = "formatUploadedDocument";

    public static final String GOVERNING_UPLOADED_DOCUMENT = "governingUploadedDocument";

    /* Feild Names for Table FIM_MYSTERY_SHOPPER */

    // public static final String MYSTERY_SHOPPER_ID = "mysteryShopperID";
    public static final String POINT_VALUE_PERFORMANCE_Q1 = "pointValuePerformanceQ1";

    public static final String POINT_VALUE_PERFORMANCE_Q2 = "pointValuePerformanceQ2";

    public static final String POINT_VALUE_PERFORMANCE_Q3 = "pointValuePerformanceQ3";

    public static final String POINT_VALUE_PERFORMANCE_Q4 = "pointValuePerformanceQ4";

    public static final String POINT_VALUE_PERFORMANCE_Q5 = "pointValuePerformanceQ5";

    public static final String POINT_VALUE_PERFORMANCE_Q6 = "pointValuePerformanceQ6";

    public static final String POINT_VALUE_PERFORMANCE_Q7 = "pointValuePerformanceQ7";

    public static final String POINT_VALUE_PERFORMANCE_Q8 = "pointValuePerformanceQ8";

    public static final String COMMENTS_FOR_PERFORMANCE = "commentsForPerformance";

    public static final String PERFORMANCE_QUESTION_SCORE = "performanceQuestionScore";

    public static final String POINT_VALUE_SERVICE_Q1 = "pointValueServiceQ1";

    public static final String POINT_VALUE_SERVICE_Q2 = "pointValueServiceQ2";

    public static final String POINT_VALUE_SERVICE_Q3 = "pointValueServiceQ3";

    public static final String POINT_VALUE_SERVICE_Q4 = "pointValueServiceQ4";

    public static final String POINT_VALUE_SERVICE_Q5 = "pointValueServiceQ5";

    public static final String POINT_VALUE_SERVICE_Q6 = "pointValueServiceQ6";

    public static final String POINT_VALUE_SERVICE_Q7 = "pointValueServiceQ7";

    public static final String POINT_VALUE_SERVICE_Q8 = "pointValueServiceQ8";

    public static final String POINT_VALUE_SERVICE_Q9 = "pointValueServiceQ9";

    public static final String POINT_VALUE_SERVICE_Q10 = "pointValueServiceQ10";

    public static final String POINT_VALUE_SERVICE_Q11 = "pointValueServiceQ11";

    public static final String POINT_VALUE_SERVICE_Q12 = "pointValueServiceQ12";

    public static final String COMMENTS_FOR_SERVICE = "commentsForService";

    public static final String SERVICE_QUESTION_SCORE = "serviceQuestionScore";

    public static final String POINT_VALUE_APPEARANCE_Q1 = "pointValueAppearanceQ1";

    public static final String POINT_VALUE_APPEARANCE_Q2 = "pointValueAppearanceQ2";

    public static final String POINT_VALUE_APPEARANCE_Q3 = "pointValueAppearanceQ3";

    public static final String POINT_VALUE_APPEARANCE_Q4 = "pointValueAppearanceQ4";

    public static final String POINT_VALUE_APPEARANCE_Q5 = "pointValueAppearanceQ5";

    public static final String COMMENTS_FOR_APPEARANCE = "commentsForAppearance";

    public static final String APPEARANCE_QUESTION_SCORE = "appearanceQuestionScore";

    public static final String INSPECTION_TIME = "inspectionTime";

    public static final String REPORT_SENT_TO_FRANCHISEE_DATE = "reportSentToFranchiseeDate";

    public static final String WEATHER_CONDITION = "weatherCondition";

    public static final String GENERAL_COMMENTS = "generalComments";

    public static final String TOTAL_SCORE = "totalScore";

    /* Field Names for Table FRANCHISE_STATUS_CHANGED */
    public static final String CHANGE_DATE = "changeDate";

    public static final String CHANGE_DATA = "changeData";

    public static final String FROM_USER = "fromUser";

    /* Field Names for Table FRANCHISEE */
    public static final String FRANCHISEE_NAME = "franchiseeName";
    public static final String CURR_ID = "currencyId";
    public static final String CURR_RATE = "currencyRate";
    public static final String CURR_CODE = "currencyCode";

    public static final String REGION_NO = "regionNo";

    public static final String PHONE1 = "phone1";
    
    public static final String PHONE1_EXTN = "phone1Extn";
    public static final String PHONE2_EXTN = "phone2Extn";
    public static final String WORK_PHONE_EXTN = "workPhoneExtn";
    public static final String HOME_PHONE_EXTN = "homePhoneExtn";                                          
    public static final String PHONE2 = "phone2";

    public static final String MOBILE = "mobile";

    public static final String FAX = "fax";

    public static final String STORE_FAX = "storeFax";

    public static final String EMAIL_ID = "emailID";

    public static final String STORE_PHONE = "storePhone";
    
    public static final String STORE_PHONE_EXTN = "storePhoneExtn";

    public static final String STORE_EMAIL = "storeEmail";
    
    public static final String STORE_WEBSITE = "storeWebsite";

    public static final String STORE_MOBILE = "storeMobile";

    public static final String IS_ADMIN = "isAdmin";

    public static final String IS_BASE_ADMIN = "isBaseAdmin";

    public static final String TRIGGER_SENT = "triggerSent";

    public static final String IS_FRANCHISEE = "isFranchisee";

    public static final String TRANSACTION_TYPE = "transactionType";

    public static final String STORE_STATUS = "storeStatus";

    public static final String LICENSE_NO = "licenseNo";

    public static final String STORE_OPENING_DATE = "storeOpeningDate";

    public static final String GRAND_STORE_OPENING_DATE = "grandStoreOpeningDate";

    public static final String FIM_FS_LEAD_ID = "fimFsLeadID";

    public static final String TRANSFER = "transfer";

    public static final String COMPLETED = "completed";

    public static final String COMPLETED_BY = "completedBy";

    public static final String PREVIOUS_FRANCHISEE = "previousFranchisee";

    public static final String PREVIOUS_OWNERS = "previousOwners";

    public static final String TRANSFER_FLAG = "transferFlag";

    public static final String FRANCHISEE_C1 = "franchiseeC1";

    public static final String FRANCHISEE_C2 = "franchiseeC2";

    public static final String FRANCHISEE_C3 = "franchiseeC3";

    public static final String FRANCHISEE_C4 = "franchiseeC4";

    public static final String FRANCHISEE_C5 = "franchiseeC5";

    public static final String FRANCHISEE_C6 = "franchiseeC6";

    public static final String FRANCHISEE_C7 = "franchiseeC7";

    public static final String FRANCHISEE_C8 = "franchiseeC8";

    public static final String FRANCHISEE_C9 = "franchiseeC9";

    public static final String FRANCHISEE_C10 = "franchiseeC10";

    public static final String WEBPAGE_MAPPING = "webpageMapping";

    public static final String STORE_HOURS = "storeHours";

    public static final String REPORT_PERIOD_START_DATE = "reportPeriodStartDate";
    
    public static final String REPORT_PERIOD_END_DATE = "reportPeriodEndDate";

    public static final String CENTER_NAME = "centerName";

    public static final String LEAD_NAME = "lName";

    public static final String IS_LOCAL_CUR = "isLocalCur";

    public static final String JOB_TITLE = "jobTitle";

    public static final String REGIONAL_TYPE = "regionalType";

    public static final String BLACKBERRY_EMAIL = "blackberryMail";

    public static final String SMS_EMAIL = "smsEmail";

    /* Field Names for Table FRANCHISEE_CALL */
    public static final String LOGGED_BY_ID = "loggedByID";

    public static final String TASK_ID = "taskID";
    
    public static final String TASK_NUMBER = "taskNumber";
    public static final String AUDITOR = "auditor";
    public static final String TIME_IN_VISIT = "timeInVisit";//SMC_CHANGES
    public static final String ENABLE_APPROVED_TASK_STATUS = "enableApproveTaskStatus";//PW_ENH_APPROVE_TSK_STATUS

    public static final String ENABLE_LANDSCAPE_MODE = "enableLandscapeMode";
    

    public static final String ENABLE_MODIFICATION_POPUP_SMQUESTION = "enableModificationPopUpSMQuestion";
    public static final String SEND_COMPLETION_EMAIL = "sendCompletionEmail";//PW_ENH_VST_MAIL
    public static final String SEND_NOTIFICATION_EMAIL = "sendNotificationEmail";
    
    public static final String SCHEDULE_TIME_DB_FORMAT = "scheduleTimeDbFormat";
    
    public static final String SECONDRY_CHECKLIST_ID = "secondryChecklistID";

    public static final String CHECKLIST_DESCRIPTION = "checklistDescription";
    
    public static final String IS_PRIMARY_CHECKLIST = "isPrimaryChecklist";

    public static final String CALL_TYPE = "callType";
    
    
    public static final String KEY_ID = "keyID";
    public static final String KEYWORD = "keyword";
    public static final String KEYWORD_TYPE = "keywordType";
    public static final String OFFENSIVE_EMAIL = "offensiveEmail";
    public static final String WATCH_EMAIL = "watchEmail";
    public static final String GREAT_EMAIL = "greatEmail";
    public static final String FRANBUZZ_EMAIL = "franBuzzEmail";   //added by prakash jodha
    public static final String FORUM_EMAIL = "forumEmail";
    
    

    /* Field Names for Table FRANCHISEE_USERS */
    public static final String FRANCHISEE_USER_NO = "franchiseeUserNo";
    
    // Field added by Ram Avtar for franchisee can add employee
    public static final String CAN_ADD_EMPLOYEE = "canAddEmployee";
    
    public static final String NO_OF_OVERDUE_TASKS = "noOfOverdueTasks";
    
    public static final String USER_TYPE_NO = "userTypeNo";

    // public static final String C1 = "c1";
    // public static final String C2 = "c2";
    // public static final String C3 = "c3";
    // public static final String C4 = "c4";
    // public static final String C5 = "c5";
    // public static final String C6 = "c6";
    // public static final String C7 = "c7";
    // public static final String C8 = "c8";
    // public static final String C9 = "c9";
    // public static final String C10 = "c10";
    public static final String SALUTATION = "salutation";

    public static final String GREETING = "greeting";

    public static final String WEB_MAIL = "webMail";

    public static final String SMS_MAIL = "smsMail";

    /* Field Names for Table STORE_TYPE */
    public static final String STORETYPE = "storeType";

    public static final String STORENAME = "storeName";

    /* Field Names for Table FS_ALL_ZIPCODES */
    public static final String ZIP_CODE_ID = "zipCodeID";

    public static final String ZIP_CODE_NAME = "zipCodeName";

    public static final String STATE_CODE = "stateCode";

    public static final String STATE_ABBREVIATION = "stateAbbreviation";

    /* Field Names for Table FS_AREA_FRANCHISE_MAPPING */

    /* Field Names for Table FS_AREA_OWNERS */

    /* Field Names for Table FS_AREA_USERS */

    /* Field Names for Table FS_AREAS */

    /* Field Names for Table FS_ATTACHMENT */
    public static final String ATTACHMENT_ID = "attachmentID";

    public static final String FILE_ATTACHMENT = "fileAttachment";
    public static final String HTML_FILE_ATTACHMENT = "htmlFileAttachment";////P_ENH_INTRANET

    public static final String FILE_ATTACHMENT_MIME_TYPE = "fileAttachmentMimeType";

    /* Field Names for Table FS_BOUNCE_MESSAGE_ID */

    /* Field Names for Table FS_BOUNCE_POPSERVER */

    /* Field Names for Table FS_BUSINESS_OVERVIEW */
    public static final String BUSINESS_OVERVIEW_ID = "businessOverviewID";

    public static final String LEAD_ID = "leadID";
    //P_FS_E_MMM_103 Added By Vikram Raj  
    public static String LINK_LEAD_ID = "linkLeadID";  

    public static final String SALE_HISTORY_PRE_2 = "saleHistoryPre2";

    public static final String SALE_HISTORY_PRE_1 = "saleHistoryPre1";

    public static final String SALE_HISTORY_CURRENT = "saleHistoryCurrent";

    public static final String SALE_GOAL = "saleGoal";

    public static final String STAFF_SALES = "staffSales";

    public static final String STAFF_SUPPORT = "staffSupport";

    public static final String STAFF_OTHER = "staffOther";

    public static final String GROSS_MARGIN = "grossMargin";

    public static final String CREDIT_CARD = "creditCard";

    public static final String CURRENT_PAYABLE = "currentPayable";

    public static final String CURRENT_RECEIVABLE = "currentReceivable";

    public static final String PLP = "plp";

    public static final String TYPE_OF_SYSTEM = "typeOfSystem";

    public static final String INVENTORY = "inventory";

    public static final String INVENTORY_VALUE = "inventoryValue";

    public static final String YEARS_IN_BUSINESS = "yearsInBusiness";

    public static final String PERSONAL_PERSONALITY_STYLE = "personalPersonalityStyle";

    public static final String BACKGROUND_OVERVIEW = "backgroundOverview";

    public static final String GOAL_DREAM = "goalDream";

    public static final String OTHER_OPTIONS = "otherOptions";

    public static final String TIMING = "timing";

    public static final String HOT_BUTTONS = "hotButtons";

    public static final String CONCERNS = "concerns";

    public static final String COMMENTS_STAFF = "commentsStaff";

    public static final String COMMENTS_CASHFLOW = "commentsCashflow";

    /* Field Names for Table FS_CAMPAIGN */
    public static final String CAMPAIGN_MANAGER_NAME = "campaignManagerName";

    public static final String CAMPAIGN_MAIL_INTERVAL = "campaignMailInterval";

    public static final String CAMPAIGN_START_LAG = "campaignStartLag";

    public static final String CAN_BE_DELETED = "canBeDeleted";

    public static final String FOR_UNREGISTERED_STATES = "forUnregisteredStates";

    public static final String CAMPAIGN_FOR = "campaignFor";

    public static final String CAMPAIGN_TYPE = "campaignType";

    public static final String TEMPLATE_STATUS_TYPE = "templateStatusType";

    public static final String CAMPAIGN_STATUS_TYPE = "campaignStatusType";

    public static final String IS_INTERVAL_CONSTANT = "isIntervalConstant";
    //P_FS_E_13092010 to add for whom the campaign is added
    public static final String CAMPAIGN_ADDED_FOR = "campaignAddedFor";

    /* Field Names for Table FS_CAMPAIGN_EMAIL_LOG */
    public static final String CAMPAIGN_EMAIL_LOG_ID = "campaignEmailLogID";

    public static final String SCHEDULE_ID_ID = "scheduleIDID";

    /* Field Names for Table FS_CAMPAIGN_SCHEDULE */
    public static final String SCHEDULE_DATE = "scheduleDate";

    public static final String NEXT_DATE = "nextDate";

    public static final String NEXT_ORDER = "nextOrder";

    public static final String CAMPAIGN_SENT = "campaignSent";

    /* Field Names for Table FS_CONTRACT_INFORMATION */
    public static final String LEGAL_BUSINESS_NAME = "legalBusinessName";

    public static final String BUSINESS_STRUCTURE = "businessStructure";

    public static final String EIN = "ein";

    public static final String NO_OF_OWNERS = "noOfOwners";

    public static final String PERCENT_OWNERSHIP = "percentOwnership";

    public static final String GROSS_VOLUME_OF_BUSINESS = "grossVolumeOfBusiness";

    public static final String HOME_OFFICE = "homeOffice";

    public static final String BUSINESS_ADDRESS = "businessAddress";

    public static final String BUSINESS_CITY = "businessCity";

    public static final String BUSINESS_STATE = "businessState";

    public static final String BUSINESS_COUNTRY_ID = "businessCountryID";

    public static final String BUSINESS_COUNTRY = "businessCountry";

    public static final String BUSINESS_ZIP = "businessZip";

    public static final String BUSINESS_PHONE = "businessPhone";

    public static final String WEBSITE = "website";

    public static final String COMMITT_DATE = "committDate";

    public static final String COMMITT_WEEK = "committWeek";

    public static final String AGREEMENT_COMMENTS = "agreementComments";

    public static final String DISCLOSURE_DATE = "disclosureDate";

    public static final String CONTROLL_NUMBER = "controllNumber";

    public static final String AGREEMENT_BY = "agreementBy";

    public static final String AGREEMENT_BACK = "agreementBack";

    public static final String INITIAL_INVESTMENT = "initialInvestment";

    public static final String SERVICE_FEE_STRUCTURE = "serviceFeeStructure";

    public static final String SOFTWARE_PAYMENT = "softwarePayment";

    public static final String OTHER_PAYMENT = "otherPayment";

    public static final String STIPULATION_DETAILS = "stipulationDetails";

    public static final String SURVIVOR_NAME = "survivorName";

    public static final String SURVIVOR_RELATIONSHIP = "survivorRelationship";

    public static final String SURVIVOR_ADDRESS = "survivorAddress";

    public static final String SURVIVOR_CITY = "survivorCity";

    public static final String SURVIVOR_STATE = "survivorState";

    public static final String SURVIVOR_COUNTRY = "survivorCountry";

    public static final String INSURANCE_CERTIFICATE = "insuranceCertificate";

    public static final String SEND_PACKAGE_TO = "sendPackageTo";

    public static final String PUBLIC_RELATION = "publicRelation";

    public static final String PARTNERSHIP_AGREEMENT = "partnershipAgreement";

    public static final String BUSINESS_FAX = "businessFax";

    public static final String TRAINING_SCHOOL = "trainingSchool";

    public static final String PROFORMA_NAME = "proformaName";

    public static final String COMMENCEMENT_DATE = "commencementDate";

    public static final String CHECKEDBY = "checkedby";

    public static final String CONFLICT = "conflict";

    public static final String CONFLICT_EXPLANATION = "conflictExplanation";

    public static final String MANAGER_COMMENTS = "managerComments";

    public static final String CUSTOMER_LIST = "customerList";
    public static final String CUSTOMER_NO = "customerNo";

    public static final String FRAN_FEE_DATE = "franFeeDate";

    public static final String RELATION_NAME = "relationName";

    public static final String SECONDARY_PHONE = "secondaryPhone";

    public static final String SECONDARY_EXT = "secondaryExt";

    public static final String SECONDARY_ADDRESS = "secondaryAddress";

    public static final String SECONDARY_CITY = "secondaryCity";

    public static final String SECONDARY_COUNTY = "secondaryCounty";

    public static final String SECONDARY_STATE_ID = "secondaryStateID";

    public static final String SECONDARY_ZIP = "secondaryZip";

    public static final String SECONDARY_COUNTRY = "secondaryCountry";

    public static final String SECONDARY_FAX = "secondaryFax";

    public static final String OTHER_STATE = "otherState";

    public static final String OTHER_COUNTRY = "otherCountry";
    
    
    /* Field Names for Table FS_DISCOVERY_DAY_MEETING */
    public static final String DDM_ID = "ddmID";

    /*
     * Field Names for Table FS_REPLYTO_EMAIL Added by Abishek Singhal 18 Sep 2006
     */
    public static final String REPLYTO_ID = "replyToID";

    /* Field Names for Table FS_FRANCHISE_DEVELOPMENT */
    public static final String FRANCHISE_DEVELOPMENT_ID = "franchiseDevelopmentID";

    public static final String DESIGNATED_MARKET = "designatedMarket";

    public static final String SALESMAN = "salesman";

    public static final String SALESMAN_DATE = "salesmanDate";

    public static final String SALESMAN_AMOUNT = "salesmanAmount";

    public static final String SALESMAN_DEPOSIT = "salesmanDeposit";

    public static final String CALLED_OFFICE = "calledOffice";

    public static final String MSA = "msa";

    public static final String POPULATION = "population";

    public static final String OPEN_CENTERS = "openCenters";

    public static final String REGISTRATION = "registration";

    public static final String SOLD = "sold";

    public static final String CASH_HAND = "cashHand";

    public static final String LOANS = "loans";

    public static final String STOCKS = "stocks";

    public static final String ACCOUNTS_DUE = "accountsDue";

    public static final String ACCOUNTS_RECIEVABLE = "accountsRecievable";

    public static final String OTHER_LIABILITIES = "otherLiabilities";

    public static final String NET_VALUE = "netValue";

    public static final String IRA = "ira";

    public static final String LIFE_INSURANCE = "lifeInsurance";

    public static final String OTHER_ASSETS = "otherAssets";

    public static final String TOTAL_ASSETS = "totalAssets";

    public static final String TOTAL_LIABLITIES = "totalLiablities";

    public static final String TOTAL_NET_WORTH = "totalNetWorth";

    public static final String APPROVED_BY = "approvedBy";

    public static final String APPROVED_BY_DATE = "approvedByDate";

    public static final String WELLCOME_CALL = "wellcomeCall";

    public static final String WELLCOME_CALL_DATE = "wellcomeCallDate";

    public static final String SALESMAN_COMMISSION = "salesmanCommission";

    public static final String SALESMAN_COMMISSION_DATE = "salesmanCommissionDate";

    public static final String QUALIFIER_COMMISSION = "qualifierCommission";

    public static final String QUALIFIER_COMMISSION_DATE = "qualifierCommissionDate";

    public static final String LENDABLE_CASH = "lendableCash";

    public static final String LENDABLE_EQUITY = "lendableEquity";

    public static final String LENDABLE_ACCOUNTS_RECEIVABLE = "lendableAccountsReceivable";

    public static final String LENDABLE_REAL_ESTATE = "lendableRealEstate";

    public static final String LENDABLE_LIFE_INSURANCE = "lendableLifeInsurance";

    public static final String LENDABLE_OTHER = "lendableOther";

    public static final String LENDABLE_NET_WORTH = "lendableNetWorth";

    /* Field Names for Table FS_FRANCHISEE_QUALIFICATION */
    public static final String FRANCHISEE_QUALIFICATION_ID = "franchiseeQualificationID";

    public static final String HOME_PHONE = "homePhone";

    public static final String WORK_PHONE = "workPhone";

    public static final String SSN = "ssn";

    public static final String PRESENT_ADDRESS = "presentAddress";

    public static final String STATE_ID = "stateID";

    public static final String ZIP_CODE = "zipCode";

    public static final String HOW_LONG = "howLong";

    public static final String PREVIOUS_ADDRESS = "previousAddress";

    public static final String PREVIOUS_COUNTRY = "previousCountry";

    public static final String PREVIOUS_STATE_ID = "previousStateID";

    public static final String PREVIOUS_CITY = "previousCity";

    public static final String PREVIOUS_ZIP_CODE = "previousZipCode";

    public static final String SPOUSE_NAME = "spouseName";

    public static final String SPOUSE_SSN = "spouseSsn";

    public static final String CASH_ON_HAND = "cashOnHand";

    public static final String MARKETABLE_SECURITIES = "marketableSecurities";

    public static final String ACCOUNTS_RECEIVABLE = "accountsReceivable";

    public static final String RETIREMENT_PLANS = "retirementPlans";

    public static final String PERSONAL_PROPERTY = "personalProperty";

    public static final String BUSINESS_HOLDINGS = "businessHoldings";

    public static final String ASSESTS_DESCRIPTION = "assestsDescription";

    public static final String TOTAL_NETWORTH = "totalNetworth";

    public static final String MORTGAGES = "mortgages";

    public static final String ACCOUNTS_PAYABLE = "accountsPayable";

    public static final String NOTES_PAYABLE = "notesPayable";

    public static final String LOANS_ON_LIFE_INSURANCE = "loansOnLifeInsurance";

    public static final String CREDIT_CARD_BALANCE = "creditCardBalance";

    public static final String UNPAID_TAXES = "unpaidTaxes";

    public static final String LIABLITIES_DESCRIPTION = "liablitiesDescription";

    public static final String TOTAL_LIABILITIES = "totalLiabilities";

    public static final String RE_ADDRESS = "reAddress";

    public static final String RE_DATE_PURCHASED = "reDatePurchased";

    public static final String RE_ORIG_COST = "reOrigCost";

    public static final String RE_PRESENT_VALUE = "rePresentValue";

    public static final String RE_MORTGAGE_BALANCE = "reMortgageBalance";

    public static final String RE_ADDRESS1 = "reAddress1";

    public static final String RE_DATE_PURCHASED1 = "reDatePurchased1";

    public static final String RE_ORIG_COST1 = "reOrigCost1";

    public static final String RE_PRESENT_VALUE1 = "rePresentValue1";

    public static final String RE_MORTGAGE_BALANCE1 = "reMortgageBalance1";

    public static final String RE_ADDRESS2 = "reAddress2";

    public static final String RE_DATE_PURCHASED2 = "reDatePurchased2";

    public static final String RE_ORIG_COST2 = "reOrigCost2";

    public static final String RE_PRESENT_VALUE2 = "rePresentValue2";

    public static final String RE_MORTGAGE_BALANCE2 = "reMortgageBalance2";

    public static final String RE_ADDRESS3 = "reAddress3";

    public static final String RE_DATE_PURCHASED3 = "reDatePurchased3";

    public static final String RE_ORIG_COST3 = "reOrigCost3";

    public static final String RE_PRESENT_VALUE3 = "rePresentValue3";

    public static final String RE_MORTGAGE_BALANCE3 = "reMortgageBalance3";

    public static final String ANNUAL_SALARY = "annualSalary";

    public static final String ANNUAL_INVESTMENT = "annualInvestment";

    public static final String ANNUAL_RE_INCOME = "annualReIncome";

    public static final String OTHER_ANNUAL_SOURCE = "otherAnnualSource";

    public static final String OTHER_ANNUAL_SOURCE_DESCRIPTION = "otherAnnualSourceDescription";

    public static final String ANNUAL_SOURCE_TOTAL = "annualSourceTotal";

    public static final String LOAN_CO_SIGN = "loanCoSign";

    public static final String LEGAL_JUDGEMENT = "legalJudgement";

    public static final String INCOME_TAXES = "incomeTaxes";

    public static final String OTHER_SPECIAL_DEBT = "otherSpecialDebt";

    public static final String TOTAL_CONTINGENT = "totalContingent";

    public static final String WHEN_READY_IF_APPROVED = "whenReadyIfApproved";

    public static final String SKILLS_EXPERIENCE = "skillsExperience";

    public static final String ENABLE_REACH_GOALS = "enableReachGoals";

    public static final String RESPONSIBLE_FOR_DAILY_OPERATIONS = "responsibleForDailyOperations";

    public static final String CASH_AVAILABLE = "cashAvailable";

    public static final String APPROVED_FOR_FINANCING = "approvedForFinancing";

    public static final String AMOUNT_APPROVED_FOR_FINANCE = "amountApprovedForFinance";

    public static final String SOLE_INCOME_SOURCE = "soleIncomeSource";

    public static final String CONVICTED_OF_FELONY = "convictedOfFelony";

    public static final String EXPLAIN_CONVICTION = "explainConviction";

    public static final String FILED_BANKRUPTCY = "filedBankruptcy";

    public static final String DATE_FILED = "dateFiled";

    public static final String DATE_DISCHARGED = "dateDischarged";

    public static final String LOCATION_PREFERENCE1 = "locationPreference1";

    public static final String LOCATION_PREFERENCE2 = "locationPreference2";

    public static final String LOCATION_PREFERENCE3 = "locationPreference3";

    public static final String BUSINESS_QUESTION1 = "businessQuestion1";

    public static final String BUSINESS_QUESTION2 = "businessQuestion2";

    public static final String BUSINESS_QUESTION3 = "businessQuestion3";

    /* Field Names for Table FS_GOALS */
    public static final String GOALS_ID = "goalsID";

    public static final String GOALS_SHEET_TITLE = "goalsSheetTitle";

    public static final String GOALS_YEAR = "goalsYear";

    public static final String GOALS_USER_ID = "goalsUserID";
    public static final String GOALS_MODIFY_ID = "goalsModifyID";
    public static final String GOALS_USER_NAME = "goalsUserName";
    

    public static final String GOALS_TYPE = "goalsType";

    public static final String LEAD_ASSIGNMENT_ID = "leadAssignmentID";

    /* Field Names for Table FS_GOALS_MONTHLY */
    public static final String GOALS_MONTHLY_DETAILS_ID = "goalsMonthlyDetailsID";

    public static final String LEAD_STATUS_ID = "leadStatusID";

    public static final String JANUARY = "january";

    public static final String FEBRUARY = "february";

    public static final String MARCH = "march";

    public static final String APRIL = "april";

    public static final String MAY = "may";

    public static final String JUNE = "june";

    public static final String JULY = "july";

    public static final String AUGUST = "august";

    public static final String SEPTEMBER = "september";

    public static final String OCTOBER = "october";

    public static final String NOVEMBER = "november";

    public static final String DECEMBER = "december";

    /* Field Names for Table FS_GOALS_QUATERLY */
    public static final String QUATERLY_ID = "quaterlyID";

    public static final String QUATER_1 = "quater1";

    public static final String QUATER_2 = "quater2";

    public static final String QUATER_3 = "quater3";

    public static final String QUATER_4 = "quater4";

    /* Field Names for Table FS_GOALS_YEARLY */
    public static final String YEARLY_ID = "yearlyID";

    public static final String YEARLY = "yearly";

    /* Field Names for Table FS_INTERNATIONAL_AREAS */

    /* Field Names for Table FS_LEAD_BUSINESS_PROFILE */
    public static final String BUSINESS_PROFILE_ID = "businessProfileID";

    public static final String GENDER = "gender";

    public static final String US_CITIZEN = "usCitizen";

    public static final String HOME_ADDRESS = "homeAddress";

    public static final String HOW_LONG_AT_ADDRESS = "howLongAtAddress";

    public static final String HOME_CITY = "homeCity";

    public static final String HOME_STATE = "homeState";

    public static final String HOME_ZIP = "homeZip";

    public static final String HOME_COUNTRY = "homeCountry";

    public static final String TIME_TO_CALL = "timeToCall";

    public static final String HOME_OWNERSHIP = "homeOwnership";

    public static final String BIRTH_DATE = "birthDate";
    
    public static final String BIRTH_MONTH = "birthMonth";

    public static final String MARITAL_STATUS = "maritalStatus";

    public static final String SPOUSE_BIRTH_DATE = "spouseBirthDate";
    
    public static final String SPOUSE_BIRTH_MONTH = "spouseBirthMonth"; //P_FS_Enh_BuilderForm

    public static final String SPOUSE_US_CITIZEN = "spouseUsCitizen";

    public static final String LIABILITES = "liabilites";

    public static final String BANKRUPTCY = "bankruptcy";

    public static final String LAWSUIT = "lawsuit";

    public static final String CONVICTED = "convicted";

    public static final String NAME_BUSINESS = "nameBusiness";

    public static final String PERCENT_OWNED = "percentOwned";

    public static final String DATE_COMPANY_STARTED = "dateCompanyStarted";

    public static final String CALL_AT_WORK = "callAtWork";

    public static final String HOW_LONG_WITH_BUSINESS = "howLongWithBusiness";

    public static final String FULL_TIME_BUSINESS = "fullTimeBusiness";

    public static final String BUSINESS_EMAIL = "businessEmail";

    public static final String BUSINESS_POSITION = "businessPosition";

    public static final String SALES_EMP = "salesEmp";

    public static final String SUPPORT_EMP = "supportEmp";

    public static final String OTHERS_EMP = "othersEmp";

    public static final String CASH_FLOW = "cashFlow";

    public static final String USE_BANK_CREDIT_CARD = "useBankCreditCard";

    public static final String PREVIOUS_YEAR_SALES = "previousYearSales";

    public static final String PREVIOUS_YEAR_PROFIT = "previousYearProfit";

    public static final String PREVIOUS_YEAR_NET_INCOME = "previousYearNetIncome";

    public static final String CURRENT_YEAR_SALES = "currentYearSales";

    public static final String CURRENT_YEAR_PROFIT = "currentYearProfit";

    public static final String CURRENT_YEAR_NET_INCOME = "currentYearNetIncome";

    public static final String PROMOTIONAL_PRODUCT = "promotionalProduct";

    public static final String BUSINESS_FORMS = "businessForms";

    public static final String COMMERCIAL_PRINT = "commercialPrint";

    public static final String OTHER_PRODUCTS = "otherProducts";

    public static final String HAVE_WEBSITE = "haveWebsite";

    public static final String WEB_ADDRESS = "webAddress";

    public static final String HAVE_PARTNER = "havePartner";

    public static final String PARTNER_NAME = "partnerName";

    public static final String PARTNER_OWNERSHIP_PERCENT = "partnerOwnershipPercent";

    public static final String PARTNER_SALES_PERCENT = "partnerSalesPercent";

    public static final String SOFTWARE_USE_TYPE = "softwareUseType";

    public static final String MOST_LIKED_ACTIVITY = "mostLikedActivity";

    public static final String LEAST_LIKED_ACTIVITY = "leastLikedActivity";

    public static final String LEARN_ABOUT_PROFORMA = "learnAboutProforma";

    public static final String MOTIVATION = "motivation";

    public static final String NEED_TO_DO = "needToDo";

    public static final String GROW_YOUR_SALES = "growYourSales";

    public static final String GROW_YOUR_PROFIT = "growYourProfit";

    public static final String CONTROL_OVER_BUSINESS = "controlOverBusiness";

    public static final String INCREASE_BUSINESS_VALUE = "increaseBusinessValue";

    public static final String HAVE_MORE_FUN = "haveMoreFun";

    public static final String INVESTIGATING_OTHER = "investigatingOther";

    public static final String EXPLAIN_INVESTIGATION = "explainInvestigation";

    public static final String OTHER_COMMENTS = "otherComments";

    /* Field Names for Table FS_LEAD_CALL */

    /* Field Names for Table FS_LEAD_COMPLIANCE */
    public static final String DC_ID = "dcID";

    public static final String LEAD_COMPLIANCE_ID = "leadComplianceID";

    public static final String FRANC_REC_AGR_DATE = "francRecAgrDate";

    public static final String REC_BY_FRANC_DATE1 = "recByFrancDate1";

    public static final String BUSS_DAY5_EXP_DATE = "bussDay5ExpDate";

    public static final String BUSS_DAY10_EXP_DATE = "bussDay10ExpDate";

    public static final String FRANC_SIGN_AGR_DATE = "francSignAgrDate";

    public static final String ADD_UFOC_DATE = "addUfocDate";

    public static final String BUSS_DAY_RULE10_CHECK = "bussDayRule10Check";

    public static final String REC_BY_FRANC_DATE2 = "recByFrancDate2";

    public static final String BUSS_DAY_RULE10_AGR_DATE = "bussDayRule10AgrDate";

    public static final String BUSS_DAY_EXP10_DATE = "bussDayExp10Date";

    public static final String BUSS_DAY_RULE5_AGR_DATE = "bussDayRule5AgrDate";

    public static final String FIRST_FRANC_PAYMENT_DATE = "firstFrancPaymentDate";

    public static final String SECOND_FRANC_PAYMENT_DATE = "secondFrancPaymentDate";

    public static final String VERSION_FRANC_AGR = "versionFrancAgr";

    public static final String STATE_REG_REQ = "stateRegReq";

    public static final String STATE_ADDENDUM_REQ = "stateAddendumReq";

    public static final String FRANC_COMMITEE_APPROVAL = "francCommiteeApproval";

    public static final String CONTRACT_REC_SIGN = "contractRecSign";

    public static final String LEASE_RIDER_PROPERLY_SIGN = "leaseRiderProperlySign";

    public static final String LIC_AGR_PROPERLY_SIGN = "licAgrProperlySign";

    public static final String PROM_NOTE_PROP_SIGN = "promNotePropSign";

    public static final String PER_COVENANT_AGR_PROPERLY_SIGN = "perCovenantAgrProperlySign";

    public static final String UFOC_REC_PROPERLY_SIGN = "ufocRecProperlySign";

    public static final String GUARANTEE_PROPERLY_SIGN = "guaranteeProperlySign";

    public static final String OTHER_DOC_PROPERLY_SIGN = "otherDocProperlySign";

    public static final String STATE_REQ_ADDENDUM_PROPERLY_SIGN = "stateReqAddendumProperlySign";

    public static final String HAND_WRITTEN_CHANGES = "handWrittenChanges";

    public static final String OTHER_ATTENDA_PROPERLY_SIGN = "otherAttendaProperlySign";

    public static final String PROOF_CONTROL_OVER_REAL_ESTATE = "proofControlOverRealEstate";

    public static final String FROM = "from";

    public static final String VERSION_OF_UFOC = "versionOfUfoc";

    public static final String VERSION_OF_ADDITIONAL_UFOC = "versionOfAdditionalUfoc";

    public static final String IP_ADDRESS = "ipAddress";

    public static final String BROWSER_TYPE = "browserType";
    
    public static final String DATE_FRM_EXT_PAGE = "fsDateFrmExtPage";

    public static final String IP_ADDRESS_ADDITIONAL = "ipAddressAdditional";

    public static final String BROWSER_TYPE_ADDITIONAL = "browserTypeAdditional";

    /* Field Names for Table FS_LEAD_DETAILS */
    public static final String LEAD_OWNER_ID = "leadOwnerID";

    public static final String LEAD_RATING_ID = "leadRatingID";

    public static final String REQUEST_DATE = "requestDate";

    public static final String Ext = "ext";

    // public static final String OTHER_PHONE = "otherPhone";
    // public static final String OTHER_PHONE_EXT = "otherPhoneExt";
    public static final String SOURCE_OF_FUNDING = "sourceOfFunding";

    // public static final String WEB_URL = "webUrl";
    // public static final String SUBSCRIBE = "subscribe";
    public static final String LAST_MAIL_SENT = "lastMailSent";

    public static final String MAIL_SENT_DATE = "mailSentDate";

    public static final String LAST_MAIL_TEMPLATE_ID = "lastMailTemplateID";
    
    public static final String ASSOCIATED_FROM = "associatedFrom";		//P_EnH_Promo_Campaign
    
    public static final String ASSOCIATION_DATE = "associationDate";	//P_EnH_Promo_Campaign

    public static final String LEAD_SOURCE_ID = "leadSourceID";

    public static final String WEBPAGE_FLAG = "webpageFlag";

    public static final String LEAD_SOURCE2_ID = "leadSource2ID";

    public static final String LEAD_SOURCE3_ID = "leadSource3ID";

    // public static final String SPOUSE_INVOLVEMENT = "spouseInvolvement";
    public static final String OTHER_LEAD_SOURCE_DETAIL = "otherLeadSourceDetail";

    // public static final String GOLDMINE_ACCOUNT_NO = "goldmineAccountNo";
    // public static final String GOLDMINE_DC = "goldmineDc";
    public static final String SPOUSE_FIRST_NAME = "spouseFirstName";

    public static final String SPOUSE_LAST_NAME = "spouseLastName";

    public static final String SPOUSE_MOBILE = "spouseMobile";

    public static final String SPOUSE_PHONE = "spousePhone";
    
    public static final String SPOUSE_PHONE_EXTN = "spousePhoneExtn";

    // public static final String HOME_PHONE_EXT = "homePhoneExt";
    public static final String PRIMARY_PHONE_TO_CALL = "primaryPhoneToCall";

    public static final String EMAIL2 = "email2";

    // public static final String INCOME_RANGE = "incomeRange";
    // public static final String OWN_HOME = "ownHome";
    // public static final String CREDIT_INTERESTED_INRANKING = "creditRanking";
    // public static final String HOME_VALUE = "homeValue";
    // public static final String DONOT_SOLICIT = "donotSolicit";
    // public static final String DONOT_EMAIL = "donotEmail";
    // public static final String CURRENT_INDUSTRY = "currentIndustry";
    // public static final String BRIEF_DESC_OF_CURR_OCCUPATION =
    // "briefDescOfCurrOccupation";
    // public static final String LIST_SOURCE = "listSource";
    // public static final String LOST_STATUS_REASON = "lostStatusReason";
    public static final String LAST_CONTACTED_BY = "lastContactedBy";

    public static final String BACKGROUND = "background";

    // public static final String INTRESTED_IN = "intrestedIn";
    // public static final String LOCATION_CITY = "locationCity";
    // public static final String LOCATION_STATE = "locationState";
    public static final String DUPLICATE_EMAIL = "duplicateEmail";

    public static final String LOCATION_ID1 = "locationId1";

    public static final String LOCATION_ID1B = "locationId1b";

    public static final String LOCATION_ID2 = "locationId2";

    // public static final String LAST_CONTACT_DATE = "lastContactDate";
    // public static final String FIRST_CONTACT_DATE = "firstContactDate";
    public static final String PREFERRED_COUNTRY1 = "preferredCountry1";

    public static final String PREFERRED_CITY1 = "preferredCity1";

    public static final String PREFERRED_STATE_ID1 = "preferredStateId1";

    public static final String PREFERRED_COUNTRY2 = "preferredCountry2";

    public static final String PREFERRED_STATE_ID2 = "preferredStateId2";

    public static final String PREFERRED_CITY2 = "preferredCity2";

    public static final String LEAD_SOURCE = "leadSource";

    public static final String LEAD_SOURCE_NAME = "leadSourceName";

    public static final String LIQUID_CAPITAL_MIN = "liquidCapitalMin";

    public static final String LIQUID_CAPITAL_MAX = "liquidCapitalMax";

    public static final String LIQUID_CAPITAL_AMOUNT = "liquidCapitalAmount";

    public static final String INVEST_TIMEFRAME = "investTimeframe";

    // public static final String IS_PREPARED_TO_INVEST = "isPreparedToInvest";
    public static final String CURRENT_NET_WORTH = "currentNetWorth";

    public static final String LIQUID_ASSESTS_AVAILABLE = "liquidAssestsAvailable";

    // public static final String IS_ADDITIONAL_FUND = "isAdditionalFund";
    // public static final String FUND_DESCRIPTION = "fundDescription";
    public static final String COUNTY1 = "county1";

    public static final String COUNTY2 = "county2";

    public static final String UNSUBSCRIBE = "unsubscribe";

    public static final String UNSUBSCRIBE_DATE = "unsubscribeDate";

    // public static final String DISCOVERY_PROCESS = "discoveryProcess";
    // public static final String BROKER = "broker";
    public static final String SALUATION = "saluation";

    public static final String VERIFY_EMAIL_ID = "verifyEmailID";

    public static final String VERIFY_PHONE = "verifyPhone";

    public static final String VERIFY_ZIPCODE = "verifyZipcode";

    public static final String INCLUDE_IN_GOALS = "includeInGoals";

    public static final String QUALIFICATION_CATEGORY = "qualificationCategory";

    public static final String PROBABILITY = "probability";

    public static final String FORECAST_RATING = "forecastRating";

    public static final String FORECAST_REVENUE = "forecastRevenue";

    public static final String FORECAST_CLOSURE_DATE = "forecastClosureDate";

    public static final String CHANGED_BY_USER_NO = "changedByUserNo";
    public static final String IS_VISITED = "isVisited";
    
    
    //P_FS_E_04042011  starts 
    public static final String NO_OF_UNIT_REQ = "noOfUnitReq";
    public static final String NO_OF_UNIT_SOLD = "noOfFieldSold";
    public static final String DATE_TO_OPEN = "dateOfOpen";
     //   P_FS_E_04042011  ends
   
    /* Field Names for Table FS_LEAD_EMAIL */
    public static final String FROM_ID = "fromID";

    public static final String BCC = "bcc";

    public static final String MAIL_TEXT = "mailText";

    /* Field Names for Table FS_Config */
    public static final String CONFIG_ID = "configID";

    public static final String IS_ROUND_ROBIN = "isRoundRobin";

    /* Field Names for Table FS_LEAD_FRANCHISE_AGREEMENT */
    public static final String FRANCHISE_AGREEMENT_ID = "franchiseAgreementID";

    public static final String DATE_REGSTD = "dateRegstd";

    public static final String FA_REQUESTED_DATE = "faRequestedDate";

    public static final String FA_RECEIVED_DATE = "faReceivedDate";

    public static final String ADA_DATE = "adaDate";

    public static final String UFOC_SENT = "ufocSent";

    public static final String FRAN_FEE_AMT = "franFeeAmt";

    public static final String AREA_FEE_DATE = "areaFeeDate";

    public static final String AREA_FEE_AMT = "areaFeeAmt";

    public static final String FA_EXECUTION_DATE = "faExecutionDate";

    /* Field Names for Table FS_LEAD_MAIL_MESSAGEID */
    public static final String LEAD_MAIL_MESSAGE_ID = "leadMailMessageID";

    /* Field Names for Table FS_LEAD_MAIL_TEMPLATES */
    public static final String MAIL_TEMPLATE_ID = "mailTemplateID";
    
    public static final String LP_TEXT_DATA = "lpTextData";
    
    
    
    public static final String LP_FIELD_NAME = "lpFieldName";
    public static final String LP_FIELD_DISPLAY_NAME = "lpFieldDisplayName";
    public static final String LP_FIELD_DESCRIPTION = "lpFieldDescription";
    public static final String LP_FIELD_TYPE = "lpFieldType";
    public static final String LP_FIELD_ID   = "lpFieldId";
    
    
    public static final String ADDITIONAL_ATTACHMENTS   = "additionalAttachments";
    public static final String MAIL_SUBJECT = "mailSubject";

    public static final String ALTERNATE_MAIL_TEMPLATE_ID = "alternateMailTemplateID";
    public static final String DEFAULT_MAIL_TEMPLATE_ID ="defaultMailTemplateID";
    public static final String SENDING_ORDER = "sendingOrder";

    public static final String MODULE = "module";

    public static final String WEBPAGE_URL = "webpageUrl";
    
    public static final String MAIL_SENT_BY = "mailSentBy";
    
    public static final String NO_OF_COUNT = "noOfCount";
    
    public static final String NO_OPENED = "noOpened";
   
    public static final String AUTO_GENERATE_NO = "autoGenerateNo";
    
    public static final String AUTO_GENERATED_NO = "autoGeneratedNo";
    
    public static final String LAST_OPENED = "lastOpened";
    
    public static final String TIME_LAST_OPENED = "lastTimeOpened";

    public static final String DISPLAY_URL = "displayUrl";

    public static final String IS_STANDARD_TEMPLATE = "isStandardTemplate";

    public static final String MAIL_TITLE = "mailTitle";
    
    
    public static final String WEB_PAGES_ID = "webPagesID";
    
    public static final String PAGE_TITLE = "pageTitle";
    
    public static final String PAGE_TEXT = "pageText";

    /* Field Names for Table FS_LEAD_PERSONAL_PROFILE */
    public static final String PERSONAL_PROFILE_ID = "personalProfileID";

    public static final String HEARD_PROFORMA_FROM = "heardProformaFrom";

    public static final String SEEKING_OWN_BUSINESS = "seekingOwnBusiness";

    public static final String OTHER_INVESTIGATION = "otherInvestigation";

    public static final String PRESENT_EMPLOYER = "presentEmployer";

    public static final String PERCENT_OWN = "percentOwn";

    public static final String EMPLOYER_ADDRESS = "employerAddress";

    public static final String EMPLOYER_CITY = "employerCity";

    public static final String EMPLOYER_STATE = "employerState";

    public static final String EMPLOYER_ZIP = "employerZip";

    public static final String EMPLOYER_COUNTRY = "employerCountry";

    public static final String HOUR_PER_WEEK = "hourPerWeek";

    public static final String DATE_STARTED = "dateStarted";

    public static final String SALARY = "salary";

    public static final String RESPONSIBILITY = "responsibility";

    public static final String SELF_EMPLOYED = "selfEmployed";

    public static final String LIMIT_PROFORMA = "limitProforma";

    public static final String SIMILAR_WORK = "similarWork";

    public static final String FINANCE_PROFORMA = "financeProforma";

    public static final String PARTNER = "partner";

    public static final String SUPPORT_HOW_LONG = "supportHowLong";

    public static final String INCOME = "income";

    public static final String OTHER_SALARY = "otherSalary";

    public static final String OTHER_INCOME_EXPLAINATION = "otherIncomeExplaination";

    public static final String SOLE_SOURCE = "soleSource";

    public static final String TOTAL_LIABILITES = "totalLiabilites";

    public static final String NET_WORTH = "netWorth";

    public static final String HOW_SOON = "howSoon";

    public static final String RUN_YOURSELF = "runYourself";

    public static final String RESPONSIBLE_FOR_OPERATION = "responsibleForOperation";

    public static final String CONVICTED_FOR_FELONY = "convictedForFelony";

    public static final String FAMILY_FEELINGS = "familyFeelings";

    public static final String OTHER_FACTS = "otherFacts";

    /* Field Names for Table FS_LEAD_POPSERVER_INFO */

    /* Field Names for Table FS_LEAD_QUALIFICATION */
    public static final String LEAD_QUALIFICATION_ID = "leadQualificationID";

    public static final String FIELD = "field";

    public static final String OPERATOR = "operator";

    public static final String JOIN_OP = "joinOp";

    /* broker Details added by Y.Nagaraja */

    public static final String PAYMENT_DETAILS = "paymentDetails";

    public static final String ASSOCIATED_LEADS = "associatedLeads";

    public static final String BROKER_ID = "brokerID";

    public static final String BROKER_NO = "brokerNo";

    public static final String OWNERS_COL = "ownersCol";

    /* Field Names for Table FS_LEAD_RATING */
    public static final String LEAD_RATING_NAME = "leadRatingName";

    /* Field Names for Table FS_LEAD_FORECAST_RATING */
    public static final String FORECAST_RATING_ID = "forecastRatingID";

    public static final String FORECAST_RATING_NAME = "forecastRatingName";

    /* Field Names for Table FS_LEAD_REAL_ESTATE */
    public static final String SITE_ADDRESS1 = "siteAddress1";

    public static final String BUILDING_SIZE = "buildingSize";

    public static final String BUILDING_DIMENTIONS_X = "buildingDimentionsX";

    public static final String BUILDING_DIMENTIONS_Y = "buildingDimentionsY";

    public static final String BUILDING_DIMENTIONS_Z = "buildingDimentionsZ";

    public static final String PARKING_SPACES = "parkingSpaces";

    public static final String APPROVAL_DATE = "approvalDate";

    public static final String DEAL_TYPE = "dealType";

    public static final String LOI_SENT = "loiSent";

    public static final String LOI_SIGNED = "loiSigned";

    public static final String LEASE_COMMENCEMENT = "leaseCommencement";

    public static final String LEASE_EXPIRATION = "leaseExpiration";

    public static final String OPTION_TERM = "optionTerm";

    public static final String PURCHASE_OPTION = "purchaseOption";

    public static final String PROJECTED_OPENING_DATE = "projectedOpeningDate";

    public static final String GENERAL_CONTRACTOR_SELECTOR = "generalContractorSelector";

    public static final String NAME_GENERAL_CONTRACTOR = "nameGeneralContractor";

    public static final String ADDRESS_GENERAL_CONTRACTOR = "addressGeneralContractor";

    public static final String PERMIT_APPLIED = "permitApplied";

    public static final String PERMIT_ISSUED = "permitIssued";

    public static final String CERTIFICATE = "certificate";

    public static final String TURN_OVER_DATE = "turnOverDate";

    public static final String GRAND_OPENING_DATE = "grandOpeningDate";

    public static final String SITE_ADDRESS2 = "siteAddress2";

    public static final String SITE_CITY = "siteCity";

    public static final String SITE_STATE = "siteState";

    public static final String SITE_COUNTRY = "siteCountry";

    /* Field Names for Table FS_LEAD_REMARKS */
    public static final String LEAD_REMARK_ID = "leadRemarkID";
 public static final String CONTACT_REMARK_ID = "contactRemarkID";
    public static final String TIME_ADDED = "timeAdded";

    /* Field Names for Table FS_LEAD_SCHEDULE */
    public static final String DUEDATE = "duedate";

    public static final String VISITDATE = "visitdate";

    public static final String SCHEDULE_STATUS_ID = "scheduleStatusID";

    public static final String PROFILE_RECIEVED = "profileRecieved";

    public static final String VISIT_CONFIRMED = "visitConfirmed";

    public static final String AGREED_REIMBURSEMENT = "agreedReimbursement";

    public static final String ACTUAL_REIMBURSEMENT = "actualReimbursement";

    public static final String CHECK_SENT = "checkSent";

    public static final String MULTIPLE_PART_SIT = "multiplePartSit";

    public static final String VISITOR1_NAME = "visitor1Name";

    public static final String RELATIONSHIP1 = "relationship1";

    public static final String VISITOR2_NAME = "visitor2Name";

    public static final String RELATIONSHIP2 = "relationship2";

    public static final String VISITOR3_NAME = "visitor3Name";

    public static final String RELATIONSHIP3 = "relationship3";

    /* Field Names for Table FS_LEAD_SCHEDULE_VISITORS */
    public static final String SCHEDULE_VISITORS_ID = "scheduleVisitorsID";

    public static final String RELATIONSHIP = "relationship";

    /* Field Names for Table FS_LEAD_SOURCE2 */
    public static final String LEAD_SOURCE2_NAME = "leadSource2Name";

    /* Field Names for Table FS_LEAD_SOURCE3 */
    public static final String LEAD_SOURCE3_NAME = "leadSource3Name";

    /* Field Names for Table FS_LEAD_STATUS */
    public static final String LEAD_STATUS_NAME = "leadStatusName";

    /* Field Names for Table FS_LEAD_STATUS_CHANGE_INFO */
    public static final String STATUS_ID = "statusID";

    public static final String DATE_CHANGED = "dateChanged";

    // Field added by yadushri
    public static final String IS_FINAL = "isFinal";

    public static final String SNO = "sno";

    /* Field Names for Table FS_LEAD_USER_STATUSVIEW */
    public static final String VIEW_FOR = "viewFor";

    public static final String VERIFICATION_SHOW = "verificationShow";

    /* Field Names for Table FS_LEAD_VERIFICATION */
    public static final String VERIFICATION_ID = "verificationID";

    /* Field Names for Table FS_LIST */
    public static final String SYSTEM_LIST = "systemList";

    /* Field Names for Table FS_LIST_CAMPAIGN */

    /* Field Names for Table FS_LIST_RULES */
    public static final String LIST_RULES_ID = "listRulesID";

    /* Field Names for Table FS_OWNER_INFORMATION */
    public static final String OWNER_ID = "ownerID";

    public static final String OWNER_NAME = "ownerName";

    public static final String OWNER_SSN = "ownerSsn";

    public static final String OWNER_BIRTHDATE = "ownerBirthdate";

    public static final String OWNER_EMAIL = "ownerEmail";

    public static final String OWNER_ADDRESS = "ownerAddress";

    public static final String OWNER_CITY = "ownerCity";

    public static final String OWNER_STATE = "ownerState";

    public static final String OWNER_COUNTRY = "ownerCountry";

    public static final String OWNER_ZIP = "ownerZip";

    public static final String OWNER_PHONE = "ownerPhone";

    public static final String OWNER_CELL = "ownerCell";

    public static final String OWNER_NO = "ownerNo";

    public static final String SPOUSE_BIRTHDATE = "spouseBirthdate";

    public static final String MODIFY_DATE = "modifyDate";

    public static final String MODIFY_BY = "modifyBy";

    /* Field Names for Table FS_PROSPECT_SUMMARY */
    public static final String PROSPECT_SUMMARY_ID = "prospectSummaryID";

    public static final String OBSTACLES_OVERCOME = "obstaclesOvercome";

    public static final String BACK_SUCCESS_FRAN = "backSuccessFran";

    public static final String REASON_OWN_BUSINESS = "reasonOwnBusiness";

    public static final String REASON_BUY_FRAN = "reasonBuyFran";

    public static final String REASON_BUY_MAACO_FRAN = "reasonBuyMaacoFran";

    public static final String MAJOR_CONCERN = "majorConcern";

    public static final String GOALS = "goals";

    public static final String CONCERNS_DISADV = "concernsDisadv";

    public static final String SOLE_OWNER = "soleOwner";

    public static final String PARTNER1 = "partner1";

    public static final String PARTNER2 = "partner2";

    public static final String PARTNER3 = "partner3";

    public static final String FRAN_AGR_NAME1 = "franAgrName1";

    public static final String FRAN_AGR_NAME2 = "franAgrName2";

    public static final String FRAN_AGR_NAME3 = "franAgrName3";

    public static final String CASH = "cash";

    public static final String LOAN1 = "loan1";

    public static final String LOAN2 = "loan2";

    public static final String ALTERNATE_SOURCE = "alternateSource";

    public static final String PRIMARY_MSA = "primaryMsa";

    public static final String SECONDARY_MSA = "secondaryMsa";

    public static final String JUDGEMENT_SALE = "judgementSale";

    /* Field Names for Table FS_QUALIFICATION_CATEGORY */
    public static final String QUALIFICATION_CATEGORY_ID = "qualificationCategoryID";

    public static final String QUALIFICATION_CATEGORY_NAME = "qualificationCategoryName";

    /* Field Names for Table FS_SITE_LOCATION */
    public static final String SITE_STATUS = "siteStatus"; //P_Enh_Site_Clearance
    public static final String LOCATION_ID = "locationID";

    public static final String RESALE_LOCATION = "resaleLocation";

    public static final String LOCATION_TITLE = "locationTitle";

    /* Field Names for Table FS_STATE_AREAS */

    /* Field Names for Table FS_ZIPCODE_AREA_COUNTRY */

    /* Field Names for Table FS_ZIPCODE_AREAS */

    /* Field Names for Table GLOSSARY_CATEGORY */
    public static final String GLOSSARY_CATEGORY_ID = "glossaryCategoryID";

    public static final String GLOSSARY_CATEGORY = "glossaryCategory";

    /* Field Names for Table GLOSSARY_DETAILS */
    public static final String GLOSSARY_DETAILS_ID = "glossaryDetailsID";

    public static final String GLOSSARY_WORD = "glossaryWord";

    public static final String GLOSSARY_DESCRIPTION = "glossaryDescription";

    /* Field Names for Table INTERNATIONAL_AREAS */

    /* Field Names for Table LEAD_ADDITIONAL_CONTACT */

    /* Field Names for Table LEAD_CAMPAIGN_TEMPLATES */
    public static final String DAYS_DIFF = "daysDifference";

    /* Field Names for Table LEAD_MAIL_DUMP */
    public static final String MAIL_ID = "mailID";

    public static final String MAIL_CONTENT = "mailContent";

    public static final String MAIL_RECEIVED_DATE = "mailReceivedDate";

    /* Field Names for Table LEAD_MAIL_MESSAGEID */

    /* Field Names for Table LEAD_MAIL_TEMPLATES */

    /* Field Names for Table LEAD_REMARKS */

    /* Field Names for Table LEAD_SECONDARY_CONTACT_DETAILS */
    public static final String LEAD_CONTACT_ID = "leadContactID";

    public static final String EXT = "ext";

    public static final String SHOW_NAME = "showName";

    public static final String SHOW_DATE = "showDate";

    public static final String MEETING_DATE = "meetingDate";

    public static final String SHOW_COMMENTS = "showComments";

    /* Field Names for Table LEGAL_INFORMATION */
    public static final String INFORMATION_ID = "informationID";

    public static final String CONTRACT_START_DATE = "contractStartDate";

    public static final String AGREEMENT_DATE = "agreementDate";

    public static final String GOOD_STANDING = "goodStanding";

    public static final String TAX_ID = "taxID";

    public static final String SALES_TAX_ID = "salesTaxID";

    public static final String DISCLOSURE_DATE1 = "disclosureDate1";

    public static final String DISCLOSURE_DATE2 = "disclosureDate2";

    public static final String RECEIPT_DATE = "receiptDate";

    public static final String DEPOSIT_DATE = "depositDate";

    public static final String CHECK_DATE = "checkDate";

    public static final String CHECK_AMOUNT = "checkAmount";

    public static final String DDSI_APPROVAL_DATE = "ddsiApprovalDate";

    public static final String CHECK_NUMBER = "checkNumber";

    public static final String PROM_NOTE_AMOUNT = "promNoteAmount";

    public static final String TERMINATION_DATE = "terminationDate";

    public static final String TERM_REASON = "termReason";

    public static final String INSURANCE_COMPANY = "insuranceCompany";

    public static final String CERTIFICATE_NUMBER = "certificateNumber";

    public static final String CERTIFICATE_REC = "certificateRec";

    public static final String CONTRACT_TYPE = "contractType";

    public static final String SOURCE = "source";

    public static final String ACCOUNT_NAME = "accountName";

    public static final String LOCATION_STATUS= "locationStatus";//ZCUB-20150116-096

    public static final String ACCOUNT_NUMBER = "accountNumber";

    public static final String BANK_NAME = "bankName";

    public static final String ROUTING_NUMBER = "routingNumber";

    public static final String BANK_ADDRESS = "bankAddress";

    public static final String BANK_PHONE = "bankPhone";

    public static final String LEGAL_NAME = "legalName";

    /* Field Names for Table LIBRARY_DOCUMENTS */
    public static final String FOLDER_NO = "folderNo";

    public static final String DOCUMENT_TITLE = "documentTitle";
    public static final String IS_WEB_PAGE = "isWebPage";//P_ENH_INTRANET

    public static final String DOC_FILE = "docFile";

    public static final String DOC_FILE_CLICK = "docFileClick";
    public static final String DATE_CREATED = "dateCreated";

    public static final String DATE_MODIFIED = "dateModified";

    public static final String MIME_TYPE = "mimeType";

    public static final String FTP_MIME_TYPE = "ftpMimeType";

    public static final String CREATED_BY = "createdBy";

    public static final String MODIFIED_BY = "modifiedBy";
    
    //Added By Divya Mishra on 31/01/2013 for Ad_Build 29_1  
    public static final String NO_PAGES = "pageNo";

    // Added By Priyanka Gupta on 22/02/2006 for Thumb Nail Images for Library
    public static final String THUMB_FILE = "thumbFile";

    public static final String FOLDER_TYPE = "folderType";

    public static final String THUMBFILE_UPLOADED = "thumbFileUploaded";

    // Added By Gaurav Mehta on 8 June,2006 for FTP Files for Library
    public static final String FTP_FILE = "ftpFile";

    public static final String FTP_STATUS = "ftpStatus";
    
    public static final String SFTP_FILE_PATH = "sftpFilePath";
    

    
    public static final String PORT_NUMBER = "portNumber";
    
    public static final String CONNECTION_TYPE = "connectionType";
    /* Field Names for Table FTP for Library */
    public static final String FTP_SERVER_ID = "ftpServerId";

    public static final String UPDATED_BY = "updatedBy";

    public static final String UPDATED_DATE = "updatedDate";

    public static final String DIRECTORY_PATH = "directoryPath";

    /* Field Names for Table LIBRARY_DOWNLOAD */
    public static final String DOWNLOAD_COUNT = "downloadCount";

    /* Field Names for Table LIBRARY_FOLDERS */
    public static final String FOLDER_NAME = "folderName";
    public static final String SUB_FOLDER_NAME = "subFolderName";                                                       //ZCB-20150326-085
    
    public static final String FOLDER_DISCRIPTION = "folderDescription";

    public static final String PARENT_FOLDER_NO = "parentFolderNo";

    public static final String FOLDER_SUMMARY = "folderSummary";
    
    
    public static final String VIDEO_NAME = "videoName";
    public static final String THUMB_NAME = "thumbName";

    /* Field Names for Table LIST_MAPPING */
    public static final String LIST_MAPPING_ID = "listMappingID";

    public static final String RECORD_TYPE = "recordType";

    /* Field Names for Table LOGGED_USERS */
    public static final String LOG_ID = "logID";

    public static final String LOGIN_TIME = "loginTime";

    public static final String LOGGED_OUT = "loggedOut";

    /* Field Names for Table LOGIN_DETAILS */
    public static final String LOGIN_DATE = "loginDate";

    public static final String LOGOUT_DATE = "logoutDate";
    
    public static final String USER_AGENT = "userAgent";
    
    public static final String USER_IP = "userIp";

    public static final String USER_HOST = "userHost";

    public static final String IS_ARCHIVE = "isArchive";

    /* Field Names for Table MAILMERGE_TEMPLATE_DETAIL */
    public static final String SECTION_TYPE = "sectionType";

    public static final String FILE_NAME = "fileName";

    public static final String TEMPLATE_FIELD = "templateField";

    public static final String CREATED_DATE = "createdDate";

    public static final String DEL_TEMPLATE = "delTemplate";

    /* Field Names for Table MASTER_DATA */
    public static final String MASTER_DATA_ID = "masterDataID";

    public static final String PARENT_DATA_ID = "parentDataId";

    public static final String DATA_VALUE = "dataValue";
    
    //P_EnH_FIN_Category starts
    public static final String CAL_TYPE = "calType"; 
    public static String CALCULATION_METHOD = "calculationMethod";
    //P_EnH_FIN_Category ends
    
    public static final String SYSTEM_DATA = "systemData";

    /* Field Names for Table MESSAGE_ATTACHMENT */
    public static final String ATTACHMENT_NO = "attachmentNo";

    public static final String ATTACHMENT_NAME = "attachmentName";

    public static final String WEB_URL_LINK = "urlLink"; //P_Enh_FO_Link_In_CheckLists

    public static final String FILE_SAVE_NAME = "fileSaveName";

    public static final String FILE_SIZE = "fileSize";

    /* Field Names for Table MESSAGE_BOARD_CATEGORIES */
    public static final String BOARD_NO = "boardNo";

    public static final String BOARD_NAME = "boardName";

    public static final String PARENT_BOARD_NO = "parentBoardNo";

    public static final String IS_A_PRIVILEGE = "isAPrivilege";

    public static final String ISACTIVE = "isactive";

    public static final String IS_MAINFORUM = "isMainforum";

    /* Field Names for Table MESSAGE_BOARD_USERS */
    public static final String DATE_READ = "dateRead";

    /* Field Names for Table MESSAGE_BOARDS */
    public static final String MESSAGE_DATE = "messageDate";

    public static final String ORIGINAL_MESSAGE_NO = "originalMessageNo";

    public static final String HAS_ATTACHMENTS = "hasAttachments";

    public static final String LEVEL = "level";
    public static final String PUBLISH_REPORT = "publishReport";
    public static final String CHART_VIEW = "chartView";
    public static final String CHART_TYPE = "chartType";
    public static final String PERCENTAGE_CONVERSION = "percentageConversion";

    public static final String ROOT_MESSAGE = "rootMessage";

    /* Field Names for Table MESSAGE_DETAIL */
    public static final String FROM_USER_NAME = "fromUserName";

    public static final String RCPT_TO = "rcptTo";

    public static final String RCPT_CC = "rcptCc";

    public static final String RCPT_BCC = "rcptBcc";

    public static final String READ_RECEIPT = "readReceipt";

    public static final String CONTACT_FLAG = "contactFlag";

    public static final String IS_DRAFT = "isDraft";

    public static final String HAS_ATTACHMENT = "hasAttachment";

    public static final String MESSAGE_ATTACHMENT = "messageattachment";

    public static final String MESSAGE_REPLIED = "messagereplied";

    public static final String MESSAGE_FORWARDED = "messageforwarded";

    public static final String IS_SIGNATURE = "isSignature";

    public static final String ISSIGNATURE_NAME = "isSignatureName";

    /* Field Names for Table MESSAGE_DETAIL_ARCHIVED */
    public static final String IS_EXTERNAL = "isExternal";

    /* Field Names for Table MESSAGE_DETAIL_ARCHIVED_BACKUP */

    /* Field Names for Table MESSAGE_DETAIL_OTHER */
    public static final String RCPT_USER_NO = "rcptUserNo";

    public static final String RCPT_USER_NAME = "rcptUserName";

    /* Field Names for Table MESSAGE_GROUP_DETAIL */
    public static final String GROUP_NO = "groupNo";

    public static final String CONTACT_USER_NO = "contactUserNo";

    /* Field Names for Table MESSAGE_GROUPS */
    public static final String GROUP_NAME = "groupName";

    public static final String GLOBAL = "global";

    /* Field Names for Table MESSAGE_RCPT */
    public static final String TO_USER_NO = "toUserNo";

    public static final String TO_CC_BCC = "toCcBcc";

    public static final String READ_DATE = "readDate";

    public static final String MESSAGE_RCPT_NO = "messageRcptNo";

    /* Field Names for Table MESSAGE_RCPT_ARCHIVED */
    public static final String DELETED = "deleted";

    public static final String MESSAGE_RCPT_ARCHIVED_NO = "messageRcptArchivedNo";

    /* Field Names for Table MESSAGE_RCPT_ARCHIVED_BACKUP2 */

    /* Field Names for Table MESSAGE_RCPT_OTHER */
    public static final String MESSAGE_RCPT_OTHER_NO = "messageRcptOtherNo";

    /* Field Names for Table MESSAGE_SIGNATURE */
    public static final String SIGNATURE = "signature";

    public static final String SIGNATURE_NO = "signatureNo";

    public static final String SIGNATURE_NAME = "signatureName";

    public static final String DEFAULT_SIGNATURE = "defaultSignature";

    public static final String FLAG_DELETE = "flagDelete";

    /* Field Names for Table MESSAGE_USERS */
    public static final String USER_TO = "userTo";

    public static final String USER_CC = "userCc";

    /*
     * Coded Added By Abhishek Agrawal for OOO feature Table Used MESSAGE_USERS_OOO
     */
    public static final String USER_OOO_ID = "userOOOId";

    public static final String USER_OOO_FLAG = "oooFlag";

    public static final String USER_MESSAGE = "userMessage";

    /* Field Names for Table MISCELLANEOUS_ITEM */
    public static final String ITEM_ID = "itemID";

    public static final String NACHA_FILE = "nachaFile";

    public static final String MODULE_DISPLAY_NAME = "moduleDisplayName";
    public static final String MODULE_KEY = "moduleKey";
    public static final String ABBREVIATED_SUBMODULE_NAME = "abbreviatedSubModuleName";
    public static final String CORP_DISPLAY = "corpDisplay";
    public static final String FRAN_DISPLAY = "franDisplay";
    public static final String REG_DISPLAY = "regDisplay";
    public static final String SUB_MODULE_DISPLAY_NAME = "subModuleDisplayName";

    /* Field Names for Table MODULE_MAPPING */
	public static final String MODULE_NAME = "moduleName";
	public static final String SUB_MODULE_NAME = "subModuleName";
	public static final String ROOT_MODULE = "rootModule";
	public static final String SUB_MENU_PRIVILEGE = "subMenuPrivilege";
	public static final String MENU_PRIVILEGE = "menuPrivilege";
	public static final String SUB_MODULE_URL = "subMenuUrl";
	//public static final String PAGE_ID = "pageID";
	public static final String SUB_MODULE_ID = "subModuleID";
	public static final String DEFAULT_SUB_MODULE = "defaultSubModule";
	public static final String MODULE_FIELD = "moduleField";//ENH_PW_SMART_QUESTIONS
	public static final String MODULE_TABLE = "moduleTable";//ENH_PW_SMART_QUESTIONS
    /* Field Names for Table NEWS_MAPPING */
    public static final String IS_READ = "isRead";

    public static final String IS_NEWS = "isNews";

    /* Field Names for Table OUTLOOK_MAIL_ATTACHMENTS */
    public static final String OUTLOOK_MAIL_ATTACHMENT_ID = "mailAttachmentID";

    /* Field Names for Table OUTLOOK_MAILS */
    public static final String OUTLOOK_RECEPIENT = "recepient";

    public static final String OUTLOOK_SENDER = "sender";

    public static final String OUTLOOK_SEND_ON = "sendOn";

    public static final String OUTLOOK_RCVD_DATE = "rcvdDate";

    public static final String OUTLOOK_MAIN_FILE = "mainFile";
    
    public static final String OUTLOOK_MAIL_FOLDER = "outLookMailFolder";//OUTLOOK_MAIL_FOLDER

    /* Field Names for Table OWNER_INFORMATION */

    /* Field Names for Table OWNERS */

    /* Field Names for Table PRIVILEGED_CONTENT */
    public static final String PRIVILEGED_CONTENT_ID = "privilegedContentID";

    public static final String CONTENT_ID = "contentID";

    /* Field Names for Table REGIONS */
    public static final String REGION_NAME = "regionName";
    
    public static final String PARENT_REGION_NAME="parentRegionName";

    public static final String REGION_ABBREV = "regionAbbrev";

    public static final String REGISTERED = "registered";

    /* Field Names for Table RELATED_LINKS */
    public static final String LINK_NO = "linkNo";

    public static final String LINK_URL = "linkUrl";

    public static final String LINK_TITLE = "linkTitle";

    public static final String LINK_DATE = "linkDate";

    public static String LINK_PUBLISH = "linkPublish";
    
    /* Field Names for Table ROLE */
    public static final String IS_DEFAULT = "isDefault";

    public static final String ASSIGNED_TO = "assignedTo";
    public static final String ASSIGNED_TO_USERS = "assignedToUsers";

    /* Field Names for Table ROLE_DATA_MAPPING */
    public static final String DATA_ID = "dataID";

    /* Field Names for Table ROLE_MODULE_MAPPING */

    /* Field Names for Table ROLE_PRIVILEGES */
    public static final String RPID = "rpid";

    public static final String PRIVILEGE_ID = "privilegeID";

    public static final String IS_CONTENT = "isContent";

    /* Field Names for Table ROLE_URL_MAPPING */
    public static final String URL = "url";

    /* Field Names for Table ROTATE_ZIP_CODES */
    public static final String ZIPCODE_OWNER = "zipcodeOwner";

    /* Field Names for Table SM_DOCUMENT_CHECKLIST */
    public static final String DOCUMENT_ID = "documentID";

    public static final String TASK = "task";

    public static final String TASK_NAME = "taskName";

    public static final String INSERTED_FROM = "insertedFrom";

    public static final String START_ALERT_DATE = "startAlertDate";

    public static final String FRANCHISEE_ACCESS = "franchiseeAccess";

    public static final String UPLOAD_DOCUMENT = "uploadDocument";

    public static final String CHEKLIST_TYPE = "cheklistType";

    public static final String SCHEDULE_FLAG = "scheduleFlag";

    /* Field Names for Table SM_EQUIPMENT_CHECKLIST */
    public static final String EQUIPMENT_ID = "equipmentID";

    public static final String START_FLAG = "startFlag";

    public static final String EQUIPMENT_NAME = "equipmentName";

    /* Field Names for Table SM_MASTER_CHECKLIST_TABLE */
    public static final String MASTER_CHECKLIST_ID = "masterChecklistID";

    public static final String CHECKLIST_TYPE = "checklistType";
    
    public static final String CHECKLIST = "checklist";

    public static final String CHECKLIST_ID = "checklistID";

    public static final String COMPLETION_DATE = "completionDate";
    
    public static final String PREVIOUS_COMPLETION_DATE = "previousCompletionDate";
    
    public static final String KEEP_SCHEDULED = "keepScheduled";

    public static final String STORE_NO = "storeNo";
    
    public static final String CREDITCARD_PAYMENT_FUNCTIONALITY = "creditCard";
    
    public static final String UPS_ONLINE_FUNCTIONALITY = "upsOnline";

    /* Field Names for Table SM_PICTURE_CHECKLIST */
    public static final String PICTURE_ID = "pictureID";

    public static final String UPLOADED_PICTURE = "uploadedPicture";

    /* Field Names for Table SM_TASK_CHECKLIST */
    public static final String RESPONSIBILITY_AREA = "responsibilityArea";

    public static final String RESPONSIBILITY_AREA_ID = "responsibilityAreaId";

    public static final String CONTACT = "contact";

    public static final String IS_STORE = "isStore";

    public static final String IS_STORE_ARCHIVED = "isStoreArchive";

    /* Field Names for Table ALERT_PERIOD */
    public static final String NO_OF_DAYS = "noOfDays";

    /* Field Names for Table SOURCE_COST */
    public static final String SOURCE_COST_ID = "sourceCostID";

    /* Field Names for Table SOURCE_COST_DETAILS */
    public static final String SOURCE3ID = "source3id";

    public static final String COST = "cost";

    /* Field Names for Table STATE_AREAS */

    /* Field Names for Table STATUS_CHANGE_RECORDS */
    public static final String PURCHASEORDER_ID = "purchaseorderID";

    public static final String OLD_STATUS_ID = "oldStatusID";

    public static final String CHANGED_DATE = "changedDate";

    /* Field Names for Table STORY */
    public static final String STORY_ID = "storyID";
    public static final String STORY_ORDER = "storyOrder";
    public static final String COMPLETE_STORY = "completeStory";

    public static final String IS_MAIN_STORY = "isMainStory";
    //P_INT_E_7.1_youtube
    public static final String EMBED_CODE = "embedCode";

    public static final String STORY_FILE = "storyFile";

    /* Field Names for Table STORY_ARCHIVED */
    public static final String DATE_ARCHIVED = "dateArchived";

    public static final String ARCHIVED_BY = "archivedBy";

    /* Field Names for Table STORY_IMAGES */
    public static final String IMAGE_ID = "imageID";

    public static final String IS_MAIN_IMAGE = "isMainImage";

    /* Field Names for Table STORY_IMAGES_ARCHIVED */

    /* Field Names for Table SUPPLIERS */
    public static final String CORPORATE_NAME = "corporateName";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String HOME_URL = "homeUrl";

    public static final String VENDOR_ID = "vendorID";

    public static final String DEFAULT_FLAG = "defaultFlag";

    public static final String PO_EMAIL = "poEmail";
    
    public static final String SUPPLIER_PO_EMAIL = "supplierPoMail";//CC_Mail_issue_supplier
    
    public static final String ALERT_EMAIL = "alertEmail";
    
    public static String POS_EMAIL = "posEmail";

    public static final String SUPPLIER_NAME = "supplierName";

    public static final String BASE_URL = "baseUrl";

    public static final String CATEGORY = "category";
    
    public static final String SERVICE = "service";//P_E_CT_8461 added by neeti

    // Added by Abishek Singhal 22 June 2006 START
    public static final String VENDOR_TYPE = "vendorType";

    public static final String USER_VENDOR_ID = "userVendorID";
    
   // ADDED BY VEERPAL SINGH 25-AUG-2011 ENH:71BBCME09 STARTS
   /* Field Names for Table TMS_TRANSACTION   &  TMS_ESTIMATE */
    public static final String SHIPTO_ADDRESS = "shipToAddress";
    
    public static final String SHIPTO_CITY = "shipToCity";
    
    public static final String SHIPTO_STATE = "shipToState";
    
    public static final String SHIPTO_ZIP = "shipToZip";
    
    public static final String SHIPTO_COUNTRY = "billToCountry";
    public static final String SHIPTO_COUNTRY2 = "shipToCountry";
    
    
    public static final String BILLTO_ADDRESS = "billToAddress";
    
    public static final String BILLTO_CITY = "billToCity";
    
    public static final String BILLTO_STATE = "billToState";
    
    public static final String BILLTO_ZIP = "billToZip";
    
    public static final String BILLTO_COUNTRY = "billToCountry";
    
    public static final String TMS_DOCUMENT = "tmsDocument";
    
     //ENH:71BBCME09 ENDS
    
    // Added by Abishek Singhal 22 June 2006 END

    /* Field Names for Table SUPPLIER_ADD_CATEGORIES */
    public static final String CONFIGURATION_ID = "configurationID";

    public static final String CONFIGURATION_NAME = "configuartionName";

    public static final String CONFIGURATION_VALUE = "configuartionValue";

    public static final String CONFIGURATION_LEVEL = "configuartionLevel";

    public static final String CONFIGURATION_DISPLAY_NAME = "configuartionDisplayName";

    public static final String SUPPLIER_ADD_CATEGORY_ID = "supplierAddCategoryId";

    public static final String SUPPLIER_ADD_CATEGORY_NAME = "supplierAddCategoryName";

    /* Field Names for Table SUPPLIER_ADDRESS_INFO */
    public static final String SUPPLIER_ADDRESS_INFO_ID = "supplierAddressInfoID";

    public static final String BILLING_STREET_ADDRESS = "billingStreetAddress";
    
    public static final String BILLING_STREET_ADDRESS2 = "billingStreetAddress2";

    public static final String BILLING_CITY = "billingCity";

    public static final String BILLING_STATE_ID = "billingStateID";

    public static final String BILLING_COUNTRY_ID = "billingCountryID";

    public static final String BILLING_ZIPCODE = "billingZipcode";

    public static final String SHIPPING_STREET_ADDRESS = "shippingStreetAddress";
    
    public static final String SHIPPING_STREET_ADDRESS2 = "shippingStreetAddress2";

    public static final String SHIPPING_CITY = "shippingCity";

    public static final String SHIPPING_STATE_ID = "shippingStateID";

    public static final String SHIPPING_COUNTRY_ID = "shippingCountryID";

    public static final String SHIPPING_ZIPCODE = "shippingZipcode";

    public static final String BILLING_PHONE = "billingPhone";

    public static final String SHIPPING_PHONE = "shippingPhone";
    
    public static String SHIPPER_STREET_ADDRESS = "shipperStreetAddress";
    
    public static String SHIPPER_STREET_ADDRESS2 = "shipperStreetAddress2";

    public static String SHIPPER_CITY = "shipperCity";

    public static String SHIPPER_STATE_ID = "shipperStateID";

    public static String SHIPPER_COUNTRY_ID = "shipperCountryID";

    public static String SHIPPER_ZIPCODE = "shipperZipcode";

    public static String SHIPPER_PHONE = "shipperPhone";    

    /* Field Names for Table SUPPLIER_CATEGORIES */
    public static final String SUPPLIER_CATEGORY_ID = "supplierCategoryID";

    public static final String SUPPLIER_CATEGORY_NAME = "supplierCategoryName";

    /* Field Names for Table SUPPLIER_EMAIL_CONTACTS */
    public static final String EMAIL_CONTACT_KEY = "emailContactKey";

    /* Field Names for Table SUPPLIER_FAVORITE_LIST */
    public static final String SUPPLIER_FAVORITE_LIST_ID = "supplierFavoriteListID";

    public static final String SUPPLIER_ITEM_ID = "supplierItemID";

    public static final String ITEM_WEIGHT = "itemWeight";

    /* Field Names for Table SUPPLIER_ITEMS */
    public static final String SPECIFICATION_NO = "specificationNo";

    public static final String SUPPLIER_SUB_CATEGORY_ID = "supplierSubCategoryID";

    public static final String ITEM_NAME = "itemName";

    public static final String ITEM_DESCRIPTION = "itemDescription";

    public static final String ONE_LINE_ITEM_DESCRIPTION = "oneLineItemDescription";

    public static final String UOM_ID = "uomID";

    public static final String TOTAL_AVAILABILITY = "totalAvailability";

    public static final String ITEM_PRICE = "itemPrice";

    public static final String ITEM_CURRENT_PRICE = "itemCurrentPrice";

    public static final String ITEM_PICTURE = "itemPicture";

    public static final String ITEM_PICTURE_MIME_TYPE = "itemPictureMimeType";

    public static final String ITEM_PICTURE_SMALL = "itemPictureSmall";

    public static final String ITEM_PICTURE_SMALL_MIME_TYPE = "itemPictureSmallMimeType";
    
     public static final String MAIN_PICTURE_NO = "mainPictureNo";
     
     public static final String ITEM_PICTURE_1 = "itemPicture1";
     
     public static final String ITEM_PICTURE_2= "itemPicture2";
     
     public static final String ITEM_PICTURE_3 = "itemPicture3";
     
     public static final String ITEM_PICTURE_4 = "itemPicture4";
    
    /* Field Names for Table SUPPLIER_PURCHASEORDER */
    public static final String ITEM_ALERT_MAIL_ID = "itemAlertMailID";

    public static final String PURCHASEORDER_DATE = "purchaseorderDate";

    public static final String PURCHASEORDER_NO = "purchaseorderNo";
    
    public static final String SUPPLIER_PURCHASEORDER_NO = "supplierPurchaseorderNo";//ZCUB-20150921-181

    public static final String PURCHASEORDER_AMOUNT = "purchaseorderAmount";

    public static final String SUPPLIER_SHIPPING_OPTION_ID = "supplierShippingOptionID";

    public static final String TRACKING_NO = "trackingNo";

    public static final String SHIPPING_TRACKING_NO = "shippingTrackingNo";

    public static final String TOTAL = "total";
    
    public static final String HANDLING_KEY = "handlingKey";

    public static final String HANDLING_CHARGES = "handlingCharges";

    /* Field Names for Table SUPPLIER_PURCHASEORDER_ITEMS */
    public static final String PURCHASEORDER_ITEM_ID = "purchaseorderItemID";

    public static final String NEXT_ORDERDATE = "nextOrderdate";

    public static final String REORDER_QUANTITY = "reorderQuantity";

    public static final String ALERT_QUANTITY = "alertQuantity";
    
    public static final String PRODUCT_SIZE="product_size";    //  ZCUB-20140307-014 
    
    public static final String PRODUCT_COLOR="product_color";  //  ZCUB-20140307-014 

    /* Field Names for Table SUPPLIER_PURCHASEORDER_REMARKS */
    public static final String PURCHASEORDER_REMARK_ID = "purchaseorderRemarkID";

    public static final String PURCHASEORDER_REMARKS = "purchaseorderRemarks";

    public static final String TARGET_USER_LEVEL = "targetUserLevel";

    public static final String READ_FLAG = "readFlag";

    /* Field Names for Table SUPPLIER_PURCHASEORDER_STATUS */
    public static final String STATUS_NAME = "statusName";

    public static final String STATUS_DESCRIPTION = "statusDescription";

    public static final String MODIFY_FLAG = "modifyFlag";

    /* Field Names for Table SUPPLIER_PURCHASEORDER_STATUS_CHANGE_INFO */
    public static final String STATUS_CHANGE_ID = "statusChangeID";

    public static final String STATUS_ID_FROM = "statusIDFrom";

    public static final String STATUS_ID_TO = "statusIDTo";

    public static final String STATUS_CHANGE_DATE = "statusChangeDate";

    public static final String SUB_STATUS_CHANGE_DATE = "subStatusChangeDate";    
    public static final String VALUE_FROM = "valueFrom";

    public static final String VALUE_TO = "valueTo";

    public static final String CHANGE_TYPE = "changeType";

    /* Field Names for Table SUPPLIER_SHIPPING_OPTIONS */
    public static final String SUPPLIER_SHIPPING_CHARGES = "supplierShippingCharges";
    public static final String SUPPLIER_ORDER_STATUS = "supplierOrderStstus";
    public static final String SUPPLIER_SHIPPING_OPTION = "supplierShippingOption";

    /* Field Names for Table SUPPLIER_SHOPPING_CART */
    public static final String CART_ID = "cartID";

    public static final String CART_NAME = "cartName";

    /* Field Names for Table SUPPLIER_SHOPPINGCART_ITEMS */
    public static final String SUPPLIER_SHOPPINGCART_ITEMS_ID = "supplierShoppingcartItemsID";

    /* Field Names for Table SUPPLIER_STATUS */

    /* Field Names for Table SUPPLIER_SUB_CATEGORIES */
    public static final String SUPPLIER_SUB_CATEGORY_NAME = "supplierSubCategoryName";

    /* Field Names for Table SUPPLIER_UOM */
    public static final String UOM_UNIT = "uomUnit";

    public static final String UOM_DESCRIPTION = "uomDescription";

    /* Field Names for Table SUPPORT_CONTACT_ADDRESS */
    public static final String ADDRESS1 = "address1";

    /* Field Names for Table SUPPORT_FAQ */
    public static final String FAQ_ID = "faqID";

    public static final String TICKET_ID = "ticketID";

    /* Field Names for Table SUPPORT_FAQ_CATEGORY */

    /* Field Names for Table SUPPORT_REMARKS */
    public static final String IS_AUTO = "isAuto";

    /* Field Names for Table SUPPORT_REQUESTS */
    public static final String REQUEST_ID = "requestID";

    public static final String DATE_REPORTED = "dateReported";

    public static final String REPORTED_BY = "reportedBy";

    public static final String CLOSED_BY = "closedBy";

    public static final String CLOSED_DATE = "closedDate";

    public static final String IS_FAQ = "isFaq";

    public static final String ATTACHED_FILE_NAME = "attachedFileName";

    public static final String ATTACHED_FILE_TYPE = "attachedFileType";

    /* Field Names for Table SUPPORT_REQUESTS_MESSAGES */
    public static final String MESSAGE = "message";

    /* Field Names for Table SUPPORT_REQUESTS_STATUS */
    public static final String COUNT = "count";
    public static final String PUBLIC_COUNT = "publicCount";
    public static final String PRIVATE_COUNT = "privateCount";

    /* Field Names for Table SUPPORT_TICKET_ASSIGNMENT */
    public static final String ASSIGNED_BY = "assignedBy";

    /* Field Names for Table SUPPORT_TICKET_STATUS */

    /* Field Names for Table SUPPORT_TICKETS */
    public static final String LOGGED_BY = "loggedBy";

    /* Field Names for Table TODAYS_EVENTS */
    public static final String TODAYS_EVENT_NO = "todaysEventNo";

    public static final String REMINDER_MILLIS = "reminderMillis";

    /* Field Names for Table TRIGGER */
    public static final String TRIGGER_ID = "triggerId";

    /* Field Names for Table TRIGGER_EVENT */
    public static final String TRIGGER_ONOFF = "triggerOnOff";
    //P_E_EVENT_TRIGGER added by neeti starts
    public static final String ACTUAL_DATA_TYPE="actualDataType";
    //P_E_EVENT_TRIGGER added by neeti ends
    public static final String AUDITING_ONOFF = "auditingOnOff";

    public static final String ALERT_MESSAGE = "alertMessage";

    public static final String DAYS_PRIOR = "daysPrior";
    public static final String DAYS_AFTER = "daysAfter";
    public static final String VALUE_TO_COMPARE = "valueToCompare";

    public static final String FIELD_ORDER = "fieldOrder";

    /* Field Names for Table TRIGGER_FROM */
    public static final String TRIGGER_FROM_ID = "triggerFromID";

    /* Field Names for Table TRIGGER_EVENT_COMPLETION */
    public static final String COMPLETION_ID = "completionID";

    /* Field Names for Table TRIGGER_USERS */
    public static final String SEND_TO = "sendTo";

    /* Field Names for Table TRIGGERS */
    public static final String TIME_PERIOD = "timePeriod";

    public static final String SEND_CC = "sendCc";

    public static final String SEND_ALERT = "sendAlert";

    /* Field Names for Table USER_CONTACTS */
    public static final String CONTACT_NO = "contactNo";

    public static final String FIRSTNAME = "firstname";

    public static final String LASTNAME = "lastname";

    public static final String NICKNAME = "nickname";

    public static final String DEFMAILID = "defmailid";

    public static final String OTHERMAILID = "othermailid";

    public static final String ORGNAME = "orgname";

    public static final String OTHERINFO = "otherinfo";

    /* Field Names for Table USER_FOLDERS */

    /* Field Names for Table USER_MESSAGE_MAPPING */
    public static final String MAPPING_ID = "mappingID";

    /* Field Names for Table USER_PERSONAL_DETAILS */
    public static final String DOB = "dob";

    public static final String CHILDREN_NAME = "childrenName";

    /* Field Names for Table USER_ROLES */

    /* Field Names for Table USER_STATUS_CHANGED */

    /* Field Names for Table USERS */
    public static final String USER_LEVEL = "userLevel";

    public static final String USER_IDENTITY_NO = "userIdentityNo";

    public static final String SECRET_QUESTION = "secretQuestion";

    public static final String SECRET_QUESTION_ANSWER = "secretQuestionAnswer";

    public static final String USER_PICTURE_NAME = "userPictureName";

    public static final String USER_PICTURE_MIME_TYPE = "userPictureMimeType";

    public static final String PASSWORD_FLAG = "passwordFlag";

    public static final String TIMEZONE = "timezone";

    public static final String SUPPLIER_NO = "supplierNo";

    public static final String STORE_ID = "storeID";

    public static final String YWCODE = "ywcode";

    public static final String EXPIRATION_TIME = "expirationTime";

    public static final String USER_FIRST_NAME = "firstname";

    public static final String USER_LAST_NAME = "lastname";
    
    public static final String LOGIN_USER_IP = "loginUserIp";

    /* Field Names for Table UTILITY */
    public static final String UTILITY_NO = "utilityNo";

    public static final String UTILITY_TITLE = "utilityTitle";

    public static final String UTILITY_FILE = "utilityFile";

    /* Field Names for Table WATCHES */
    public static final String WATCH_NO = "watchNo";

    public static final String SUBBOARDNO = "subboardno";

    public static final String TOPICNO = "topicno";

    /* Field Names for Table WEATHER_DATA */
    public static final String WEATHERAUTOID = "weatherautoid";

    public static final String STOREDATE = "storedate";

    public static final String LASTUPDATEDHOUR = "lastupdatedhour";

    /* Field Names for Table WEATHER_DOWNLOAD_LOG */
    public static final String WEATHER_DOWNLOAD_ID = "weatherDownloadID";

    public static final String LAST_UPDATE = "lastUpdate";

    public static final String ZIP_CITY = "zipCity";

    /* Field Names for Table ZIPCODE_AREA_COUNTRY */

    /* Field Names for Table ZIPCODE_AREAS */

    /* Option tab start */
    public static final String OPTION_NEW_PASSWORD = "optionNewPassword";

    public static final String OPTION_OLD_PASSWORD = "optionOldPassword";

    public static final String OPTION_CONFIRM_PASSWORD = "optionConfirmPassword";

    public static final String CONFIRM_ANSWER = "confirmAnswer";

    /* Web Page Builder */
    public static final String WEB_PAGE_NAME = "webPageName";

    public static final String WEB_PAGE_NEXT = "webPageNext";

    public static final String TEXT_XML_TAGS = "textXMLTags";

    public static final String ATTR_XML_TAGS = "attrXMLTags";
    
    // Added By Rajat Bapna
	public static final String CREDITCARD_PAYMENT_GATEWAY = "creditCardPaymentGateway"; 


    /* Field Names for Table LEAD_MAILMERGE_TEMPLATE_REL */
    public static final String LEAD_MAILMERGE_TEMPLATE_ID = "leadMailmergeTemplateID";

    public static final String MAILMERGE_DATE = "mailmergeDate";

    /* For UFOC */
    public static final String UFOC_NAME = "ufocName";

    public static final String UFOC_ID = "ufocId";

    public static final String UFOC_VERSION = "version";

    public static final String MAIL_READ_DATE = "mailReadDate";
    
    public static final String UFOC_RECIEPT_NAME = "ufocRecieptName";

    public static final String DOWNLOAD_DATE = "downloadDate";

    public static final String FROM_EMAIL = "fromEmail";

    public static final String ELECTRONIC_UFOC = "electronicUfoc";

    public static final String MAIL_STATUS = "mailStatus";

    public static final String SITE_NAME = "siteName";

    public static final String INFO_NO = "infoNo";

    public static final String HOST_NAME = "hostName";

    public static final String HOST_URL = "hostUrl";

    public static final String IMAGE_TITLE = "imageTitle";

    public static final String CONFIRM_PASSWORD = "confirmPassword";

    public static final String NOTES = "notes";

    // for FIM Area
    public static final String AREA_DEVELOPER = "areaDeveloper";

    public static final String AREA_DEVELOPER_ID = "areaDeveloperID";

    public static final String LICENSE_TYPE = "licenseType";

    public static final String AGREEMENT_TYPE = "agreementType";

    public static final String RENEWAL_FEE = "renewalFee";

    public static final String CONSECUTIVE = "consecutive";

    public static final String RENEWAL_FEE_PERCENTAGE = "renewalFeePercentage";

    public static final String OPERATING_FEE_PERCENTAGE = "operatingFeePercentage";

    public static final String TRANSFER_FEE_PERCENTAGE = "transferFeePercentage";

    public static final String INITIAL_FEE_PERCENTAGE = "initialFeePercentage";

    public static final String PERIOD = "period";

    public static final String TARGET = "target";

    public static final String ACTUAL = "actual";

    public static final String AREA_INFO_ID = "areaInfoID";

    public static final String DESIGNATION = "designation";

    public static final String PERCENT = "percent";

    public static final String FRANCHISE_ROYALTY_AMOUNT = "franchiseRoyaltyAmount";

    /* Field Names for Table HELPDESK_NEWS */

    public static final String NEWS_ID = "newsId";

    public static final String NEWS_HEADING = "newsHeading";

    /* Field Names for Table HELPDESK_SUBCATEGORY */

    public static final String SUBCATEGORY_ID = "subCategoryId";

    public static final String SUBCATEGORY_NAME = "subCategoryName";

    // Field Names for table EOA_FRANCHISE_APPLICATION

    public static final String EOA_ID = "eoaID";

    public static final String PI_FIRST_NAME = "piFirstName";

    public static final String PI_LAST_NAME = "piLastName";

    public static final String PI_MIDDLE_NAME = "piMiddleName";

    public static final String PI_GENDER = "piGender";

    public static final String PI_HOME_ADDRESS = "piHomeAddress";

    public static final String PI_TIM_AT_ADDRESS = "piTimAtAddress";

    public static final String PI_CITY = "piCity";

    public static final String PI_STATE = "piState";

    public static final String PI_HOME_ZIP = "piHomeZip";

    public static final String PI_BIRTH_DATE = "piBirthDate";

    public static final String PI_HOME_PHONE = "piHomePhone";

    public static final String PI_MOBILE = "piMobile";

    public static final String PI_HOME_OWNERSHIP = "piHomeOwnership";

    public static final String PI_EMAIL = "piEmail";

    public static final String PI_MARITAL_STATUS = "piMaritalStatus";

    public static final String PI_SPOUSE_NAME = "piSpouseName";

    public static final String PI_SSN = "piSsn";

    public static final String PI_EMPLOYER = "piEmployer";

    public static final String PI_TYPE_OF_BUSINESS = "piTypeOfBusiness";

    public static final String PI_BUSINESS_ADDRESS = "piBusinessAddress";

    public static final String PI_BUSINESS_PHONE = "piBusinessPhone";

    public static final String PI_POSITION = "piPosition";

    public static final String PI_SALARY = "piSalary";

    public static final String PI_OTHER_INCOME = "piOtherIncome";

    public static final String PI_SPOUSE_EMPLOYER = "piSpouseEmployer";

    public static final String PI_SPOUSE_TYPE_OF_BUSINESS = "piSpouseTypeOfBusiness";

    public static final String PI_SPOUSE_POSITION = "piSpousePosition";

    public static final String PI_SPOUSE_SALARY = "piSpouseSalary";

    public static final String PI_SPOUSE_BUSINESS_PHONE = "piSpouseBusinessPhone";

    public static final String PI_NO_OF_DEPENDENTS = "piNoOfDependents";

    public static final String PI_PREV_RESIDENCE = "piPrevResidence";

    public static final String PI_CITIZEN_OF_US = "piCitizenOfUs";

    public static final String PI_DRIVING_LICENSE = "piDrivingLicense";

    public static final String PI_BANK_ADJU_STATE = "piBankAdjuState";

    public static final String PI_CONV_FELONY = "piConvFelony";

    public static final String BE_NAME1 = "beName1";

    public static final String BE_ADDRESS1 = "beAddress1";

    public static final String BE_PHONE1 = "bePhone1";

    public static final String BE_NAME_OF_BUSINESS1 = "beNameOfBusiness1";

    public static final String BE_POS_AND_RES1 = "bePosAndRes1";

    public static final String BE_NAME2 = "beName2";

    public static final String BE_ADDRESS2 = "beAddress2";

    public static final String BE_PHONE2 = "bePhone2";

    public static final String BE_NAME_OF_BUSINESS2 = "beNameOfBusiness2";

    public static final String BE_POS_AND_RES2 = "bePosAndRes2";

    public static final String BE_NAME3 = "beName3";

    public static final String BE_ADDRESS3 = "beAddress3";

    public static final String BE_PHONE3 = "bePhone3";

    public static final String BE_NAME_OF_BUSINESS3 = "beNameOfBusiness3";

    public static final String BE_POS_AND_RES3 = "bePosAndRes3";

    public static final String EDU_HIGHSCHOOL_GRADUATE = "eduHighschoolGraduate";

    public static final String EDU_COLLEGE = "eduCollege";

    public static final String AS_CASH_ON_HAND = "asCashOnHand";

    public static final String AS_US_GOVT_SECUR = "asUsGovtSecur";

    public static final String AS_ACC_NOTES_REC = "asAccNotesRec";

    public static final String AS_NOTES_REC_NOT_DIS = "asNotesRecNotDis";

    public static final String AS_NOTES_REC_DIS_WITH_BANKS = "asNotesRecDisWithBanks";

    public static final String AS_LI_CASH_SUR_VALUE = "asLiCashSurValue";

    public static final String AS_OTHER_STOCKS_AND_BONDS = "asOtherStocksAndBonds";

    public static final String AS_REAL_ESTATE = "asRealEstate";

    public static final String AS_AUTOMOBILES_REG = "asAutomobilesReg";

    public static final String AS_MORTGAGES_PAYABLE = "asMortgagesPayable";

    public static final String AS_OTHER_ASSETS = "asOtherAssets";

    public static final String AS_TOTAL_ASSETS = "asTotalAssets";

    public static final String LI_NOTES_PAY_TO_BANKS_UNSECURED = "liNotesPayToBanksUnsecured";

    public static final String LI_NOTES_PAY_TO_BANK_SECURED = "liNotesPayToBankSecured";

    public static final String LI_NOTES_REC_DIS_WITH_BANKS = "liNotesRecDisWithBanks";

    public static final String LI_NOTES_PAY_TO_OTHERS_UNSECURED = "liNotesPayToOthersUnsecured";

    public static final String LI_NOTES_PAY_TO_OTHERS_SECURED = "liNotesPayToOthersSecured";

    public static final String LI_LOAN_AGAINST_LIFE_INS = "liLoanAgainstLifeIns";

    public static final String LI_ACCOUNTS_PAY = "liAccountsPay";

    public static final String LI_INTEREST_PAY = "liInterestPay";

    public static final String LI_TAXES_AND_ASS_PAY = "liTaxesAndAssPay";

    public static final String LI_BROKERS_MARGIN_ACC = "liBrokersMarginAcc";

    public static final String LI_OTHER_LIA = "liOtherLia";

    public static final String LI_TOTAL_LIA = "liTotalLia";

    public static final String LI_NET_WORTH = "liNetWorth";

    public static final String SOI_BONUS = "soiBonus";

    public static final String SOI_DIVIDENDS = "soiDividends";

    public static final String SOI_REALSTATE_INCOME = "soiRealstateIncome";

    public static final String SOI_OTHER_INCOME = "soiOtherIncome";

    public static final String SOI_TOTAL = "soiTotal";

    public static final String CL_ENDORSER = "clEndorser";

    public static final String CL_ON_LEASES = "clOnLeases";

    public static final String CL_LEGAL_CLAIMS = "clLegalClaims";

    public static final String CL_PROV_FOR_FEDERAL = "clProvForFederal";

    public static final String CL_OTHER_SPL_DEBT = "clOtherSplDebt";

    public static final String CL_TOTAL = "clTotal";

    public static final String BR_NAME1 = "brName1";

    public static final String BR_NAME2 = "brName2";

    public static final String BR_NAME3 = "brName3";

    public static final String BR_CASH_BAL1 = "brCashBal1";

    public static final String BR_CASH_BAL2 = "brCashBal2";

    public static final String BR_CASH_BAL3 = "brCashBal3";

    public static final String BR_OUTSTANDING_LOAN1 = "brOutstandingLoan1";

    public static final String BR_OUTSTANDING_LOAN2 = "brOutstandingLoan2";

    public static final String BR_OUTSTANDING_LOAN3 = "brOutstandingLoan3";

    public static final String BR_MATURITY_OF_LOANS1 = "brMaturityOfLoans1";

    public static final String BR_MATURITY_OF_LOANS2 = "brMaturityOfLoans2";

    public static final String OS_FACE_VALUE2 = "osFaceValue2";

    public static final String OS_DESC_OF_SECURITY1 = "osDescOfSecurity1";

    public static final String OS_DESC_OF_SECURITY2 = "osDescOfSecurity2";

    public static final String OS_REG_IN_NAME1 = "osRegInName1";

    public static final String OS_REG_IN_NAME2 = "osRegInName2";

    public static final String OS_COST1 = "osCost1";

    public static final String OS_COST2 = "osCost2";

    public static final String OS_PR_MARKET_VALUE1 = "osPrMarketValue1";

    public static final String OS_PR_MARKET_VALUE2 = "osPrMarketValue2";

    public static final String OS_INCOM_REC_LAST_YR1 = "osIncomRecLastYr1";

    public static final String OS_INCOM_REC_LAST_YR2 = "osIncomRecLastYr2";

    public static final String OS_TO_WHOM_PLEDGED1 = "osToWhomPledged1";

    public static final String OS_TO_WHOM_PLEDGED2 = "osToWhomPledged2";

    public static final String RE_DESC_OF_STREET_NO1 = "reDescOfStreetNo1";

    public static final String RE_DESC_OF_STREET_NO2 = "reDescOfStreetNo2";

    public static final String RE_DESC_OF_STREET_NO3 = "reDescOfStreetNo3";

    public static final String RE_DIM_OF_ACRES1 = "reDimOfAcres1";

    public static final String RE_DIM_OF_ACRES2 = "reDimOfAcres2";

    public static final String RE_DIM_OF_ACRES3 = "reDimOfAcres3";

    public static final String RE_IMP_CON_OF1 = "reImpConOf1";

    public static final String RE_IMP_CON_OF2 = "reImpConOf2";

    public static final String RE_IMP_CON_OF3 = "reImpConOf3";

    public static final String RE_MORTGAGES1 = "reMortgages1";

    public static final String RE_MORTGAGES2 = "reMortgages2";

    public static final String RE_MORTGAGES3 = "reMortgages3";

    public static final String RE_DUEDATE_OF_PAY1 = "reDuedateOfPay1";

    public static final String RE_DUEDATE_OF_PAY2 = "reDuedateOfPay2";

    public static final String RE_DUEDATE_OF_PAY3 = "reDuedateOfPay3";

    public static final String RE_ASS_VAL1 = "reAssVal1";

    public static final String RE_ASS_VAL2 = "reAssVal2";

    public static final String RE_ASS_VAL3 = "reAssVal3";

    public static final String RE_PR_MAR_VALUE1 = "rePrMarValue1";

    public static final String RE_PR_MAR_VALUE2 = "rePrMarValue2";

    public static final String RE_PR_MAR_VALUE3 = "rePrMarValue3";

    public static final String RE_UNPAID_TAX_YEAR1 = "reUnpaidTaxYear1";

    public static final String RE_UNPAID_TAX_YEAR2 = "reUnpaidTaxYear2";

    public static final String RE_UNPAID_TAX_YEAR3 = "reUnpaidTaxYear3";

    public static final String RE_UNPAID_TAX_AMT1 = "reUnpaidTaxAmt1";

    public static final String RE_UNPAID_TAX_AMT2 = "reUnpaidTaxAmt2";

    public static final String RE_UNPAID_TAX_AMT3 = "reUnpaidTaxAmt3";

    public static final String PR_NAME1 = "prName1";

    public static final String PR_ADDRESS1 = "prAddress1";

    public static final String PR_PHONE1 = "prPhone1";

    public static final String PR_OCCUPATION1 = "prOccupation1";

    public static final String PR_NAME2 = "prName2";

    public static final String PR_ADDRESS2 = "prAddress2";

    public static final String PR_PHONE2 = "prPhone2";

    public static final String PR_OCCUPATION2 = "prOccupation2";

    public static final String BR_MATURITY_OF_LOANS3 = "brMaturityOfLoans3";

    public static final String BR_HOW_ENDORSED1 = "brHowEndorsed1";

    public static final String BR_HOW_ENDORSED2 = "brHowEndorsed2";

    public static final String ACC_DEBTOR_NAME1 = "accDebtorName1";

    public static final String ACC_DEBTOR_NAME2 = "accDebtorName2";

    public static final String ACC_AMT_OWING1 = "accAmtOwing1";

    public static final String ACC_AMT_OWING2 = "accAmtOwing2";

    public static final String ACC_AGE_OF_DEBT1 = "accAgeOfDebt1";

    public static final String ACC_AGE_OF_DEBT2 = "accAgeOfDebt2";

    public static final String ACC_DESC_OF_NATURE1 = "accDescOfNature1";

    public static final String ACC_DESC_OF_NATURE2 = "accDescOfNature2";

    public static final String ACC_DESC_OF_SECURITY1 = "accDescOfSecurity1";

    public static final String ACC_DESC_OF_SECURITY2 = "accDescOfSecurity2";

    public static final String ACC_DATE_PAY_EXP1 = "accDatePayExp1";

    public static final String ACC_DATE_PAY_EXP2 = "accDatePayExp2";

    public static final String PI_COUNTRY = "piCountry";

    public static final String FIRST_NAME_1 = "firstName1";

    public static final String LAST_NAME_1 = "lastName1";

    public static final String COUNT_READ_MAIL = "countReadMail";
    public static final String COUNT_UNREAD_MAIL = "countUnReadMail";

    /* Added by Amrit on 05-10-2005. Inclusion of new tab "Qualification Detail" */
    /* Field Names for Table FS_LEAD_QUALIFICATION_DETAIL */
    public static final String LEAD_QUALIFICATION_DETAIL_ID = "leadQualificationDetailID";

    public static final String FIN_STATE = "finState";

    public static final String FIN_COUNTRY = "finCountry";

    public static final String FIN_CITY = "finCity";

    public static final String FIN_FIRST_NAME = "finFirstName";

    /* Fields Added By RamP while copying files from NHBuild */
    public static final String TITLEWITHOUTHREF = "titleWithoutHREF";

    public static final String ALERT_ID = "alertId";

    /* Added by Rajeev Varshney on 25/10/2005 for adding Customization field */

    public static final String FS_LEAD_DETAILS_C1 = "fsLeadDetailsC1";

    public static final String FS_LEAD_DETAILS_C2 = "fsLeadDetailsC2";

    public static final String FS_LEAD_DETAILS_C3 = "fsLeadDetailsC3";

    public static final String FS_LEAD_DETAILS_C4 = "fsLeadDetailsC4";

    public static final String FS_LEAD_DETAILS_C5 = "fsLeadDetailsC5";

    public static final String FS_LEAD_DETAILS_C6 = "fsLeadDetailsC6";

    public static final String FS_LEAD_DETAILS_C7 = "fsLeadDetailsC7";

    public static final String FS_LEAD_DETAILS_C8 = "fsLeadDetailsC8";

    public static final String FS_LEAD_DETAILS_C9 = "fsLeadDetailsC9";

    public static final String FS_LEAD_DETAILS_C10 = "fsLeadDetailsC10";

    // ////////////////////////////////FIM NEW BLOCK
    // STARTS//////////////////////////////////

    // /* Field Names for Table FIM_EVENTS */
    public static final String EVENTS_ID = "eventsID";

    public static final String FIM_DD_EVENT_DATE = "fimDdEventDate";

    public static final String FIM_TT_EVENT_AUTHOR = "fimTtEventAuthor";

    public static final String FIM_TT_TYPE = "fimTtType";

    public static final String FIM_TA_EVENT_SUMMARY = "fimTaEventSummary";

    public static final String FIM_EVENTS_C1 = "fimEventsC1";

    public static final String FIM_EVENTS_C2 = "fimEventsC2";

    public static final String FIM_EVENTS_C3 = "fimEventsC3";

    public static final String FIM_EVENTS_C4 = "fimEventsC4";

    public static final String FIM_EVENTS_C5 = "fimEventsC5";

    public static final String FIM_EVENTS_C6 = "fimEventsC6";

    public static final String FIM_EVENTS_C7 = "fimEventsC7";

    public static final String FIM_EVENTS_C8 = "fimEventsC8";

    public static final String FIM_EVENTS_C9 = "fimEventsC9";

    public static final String FIM_EVENTS_C10 = "fimEventsC10";

    /* Field Names for Table FIM_ENTITY_DETAIL */
    
    public static final String AREA_ENTITY_ID = "areaEntityID";
    
    public static final String FIM_ENTITY_ID = "fimEntityID";

    public static final String FIM_TT_ENTITY_NAME = "fimTtEntityName";

    public static final String FIM_CB_STATE_OF_FORMATION = "fimCbStateOfFormation";
    public static final String FIM_CB_COUNTRY_OF_FORMATION = "fimCbCountryOfFormation";
    public static final String FIM_DD_DATE_OF_FORMATION = "fimDdDateOfFormation";

    public static final String FIM_TT_TAXPAYER = "fimTtTaxpayer";

    public static final String FIM_TT_TAXPAYER_ID = "fimTtTaxpayerID";

    public static final String FIM_CB_ENTITY_TYPE = "fimCbEntityType";

    public static final String FIM_CB_ENTITY_TYPE_ID = "fimCbEntityTypeID";

    public static final String FIM_ENTITY_DETAIL_C1 = "fimEntityDetailC1";

    public static final String FIM_ENTITY_DETAIL_C2 = "fimEntityDetailC2";

    public static final String FIM_ENTITY_DETAIL_C3 = "fimEntityDetailC3";

    public static final String FIM_ENTITY_DETAIL_C4 = "fimEntityDetailC4";

    public static final String FIM_ENTITY_DETAIL_C5 = "fimEntityDetailC5";

    public static final String FIM_ENTITY_DETAIL_C6 = "fimEntityDetailC6";

    public static final String FIM_ENTITY_DETAIL_C7 = "fimEntityDetailC7";

    public static final String FIM_ENTITY_DETAIL_C8 = "fimEntityDetailC8";

    public static final String FIM_ENTITY_DETAIL_C9 = "fimEntityDetailC9";

    public static final String FIM_ENTITY_DETAIL_C10 = "fimEntityDetailC10";

    /* Field Names for Table FIM_GUARANTOR */
    public static final String GUARANTOR_ID = "guarantorID";

    public static final String FIM_CBT_GURANTOR_TITLE = "fimCbtGurantorTitle";

    public static final String FIM_TT_FIRST_NAME = "fimTtFirstName";

    public static final String FIM_TT_MIDDLE_NAME = "fimTtMiddleName";

    public static final String FIM_TT_LAST_NAME = "fimTtLastName";

    // public static final String FIM_CB_ENTITY_TYPE = "fimCbEntityType";
    public static final String FIM_CB_RESIDENCY = "fimCbResidency";

    public static final String FIM_TT_PERCENTAGE = "fimTtPercentage";

    public static final String FIM_TA_COMMENTS = "fimTaComments";

    public static final String FIM_GUARANTOR_C1 = "fimGuarantorC1";

    public static final String FIM_GUARANTOR_C2 = "fimGuarantorC2";

    public static final String FIM_GUARANTOR_C3 = "fimGuarantorC3";

    public static final String FIM_GUARANTOR_C4 = "fimGuarantorC4";

    public static final String FIM_GUARANTOR_C5 = "fimGuarantorC5";

    public static final String FIM_GUARANTOR_C6 = "fimGuarantorC6";

    public static final String FIM_GUARANTOR_C7 = "fimGuarantorC7";

    public static final String FIM_GUARANTOR_C8 = "fimGuarantorC8";

    public static final String FIM_GUARANTOR_C9 = "fimGuarantorC9";

    public static final String FIM_GUARANTOR_C10 = "fimGuarantorC10";

    /* Field Names for Table FIM_LEGAL_VIOLATION */
    public static final String VIOLATION_ID = "violationID";

    public static final String FIM_TT_NUMBER = "fimTtNumber";

    public static final String FIM_DD_INCIDENT_DATE = "fimDdIncidentDate";

    public static final String FIM_CB_COMPLAINT_TYPE = "fimCbComplaintType";

    public static final String FIM_CB_STATUS = "fimCbStatus";

    public static final String FIM_DD_COMPLAINT_DATE = "fimDdComplaintDate";

    public static final String FIM_DD_CURED_DATE = "fimDdCuredDate";

    public static final String FIM_TA_SUMMARY = "fimTaSummary";

    public static final String FIM_TA_ACTION_TAKEN = "fimTaActionTaken";

    public static final String FIM_LEGAL_VIOLATION_C1 = "fimLegalViolationC1";

    public static final String FIM_LEGAL_VIOLATION_C2 = "fimLegalViolationC2";

    public static final String FIM_LEGAL_VIOLATION_C3 = "fimLegalViolationC3";

    public static final String FIM_LEGAL_VIOLATION_C4 = "fimLegalViolationC4";

    public static final String FIM_LEGAL_VIOLATION_C5 = "fimLegalViolationC5";

    public static final String FIM_LEGAL_VIOLATION_C6 = "fimLegalViolationC6";

    public static final String FIM_LEGAL_VIOLATION_C7 = "fimLegalViolationC7";

    public static final String FIM_LEGAL_VIOLATION_C8 = "fimLegalViolationC8";

    public static final String FIM_LEGAL_VIOLATION_C9 = "fimLegalViolationC9";

    public static final String FIM_LEGAL_VIOLATION_C10 = "fimLegalViolationC10";

    /* Field Names for Table FIM_TRAINING */
    public static final String TRAINING_ID = "trainingID";

    public static final String FIM_TT_TRAINING_PROGRAM = "fimTtTrainingProgram";

    public static final String FIM_TT_INSTRUCTOR = "fimTtInstructor";

    public static final String FIM_TT_LOCATION_TR = "fimTtLocationTr";

    public static final String FIM_TT_ATTENDEE = "fimTtAttendee";

    public static final String FIM_TT_ATTENDEE_TITLE = "fimTtAttendeeTitle";

    public static final String FIM_DD_COMPLETION_DATE_TR = "fimDdCompletionDateTr";

    public static final String FIM_II_SCORE = "fimIiScore";

    public static final String FIM_TT_GRADE = "fimTtGrade";

    public static final String FIM_TRAINING_C1 = "fimTrainingC1";

    public static final String FIM_TRAINING_C2 = "fimTrainingC2";

    public static final String FIM_TRAINING_C3 = "fimTrainingC3";

    public static final String FIM_TRAINING_C4 = "fimTrainingC4";

    public static final String FIM_TRAINING_C5 = "fimTrainingC5";

    public static final String FIM_TRAINING_C6 = "fimTrainingC6";

    public static final String FIM_TRAINING_C7 = "fimTrainingC7";

    public static final String FIM_TRAINING_C8 = "fimTrainingC8";

    public static final String FIM_TRAINING_C9 = "fimTrainingC9";

    public static final String FIM_TRAINING_C10 = "fimTrainingC10";

    /* Field Names for Table FIM_COMPLAINT */
    public static final String COMPLAINT_ID = "complaintID";

    // public static final String FIM_DD_COMPLAINT_DATE = "fimDdComplaintDate";
    public static final String FIM_TT_METHOD = "fimTtMethod";

    public static final String FIM_TT_COMPLAINT = "fimTtComplaint";

    public static final String FIM_TT_COMPLAINT_BY = "fimTtComplaintBy";

    // public static final String FIM_DD_INCIDENT_DATE = "fimDdIncidentDate";
    public static final String FIM_TT_PRODUCT_CODE = "fimTtProductCode";

    // public static final String FIM_CB_COMPLAINT_TYPE_ID = "fimCbComplaintTypeID";
    // public static final String FIM_TA_SUMMARY = "fimTaSummary";
    // public static final String FIM_TA_ACTION_TAKEN = "fimTaActionTaken";
    // public static final String FIM_CB_STATUS = "fimCbStatus";
    public static final String FIM_COMPLAINT_C1 = "fimComplaintC1";

    public static final String FIM_COMPLAINT_C2 = "fimComplaintC2";

    public static final String FIM_COMPLAINT_C3 = "fimComplaintC3";

    public static final String FIM_COMPLAINT_C4 = "fimComplaintC4";

    public static final String FIM_COMPLAINT_C5 = "fimComplaintC5";

    public static final String FIM_COMPLAINT_C6 = "fimComplaintC6";

    public static final String FIM_COMPLAINT_C7 = "fimComplaintC7";

    public static final String FIM_COMPLAINT_C8 = "fimComplaintC8";

    public static final String FIM_COMPLAINT_C9 = "fimComplaintC9";

    public static final String FIM_COMPLAINT_C10 = "fimComplaintC10";

    /* Feild Names for Table FIM_MYSTERY_SHOPPER */

    public static final String MYSTERY_SHOPPER_ID = "mysteryShopperID";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q1 = "fimFtPointValuePerformanceQ1";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q2 = "fimFtPointValuePerformanceQ2";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q3 = "fimFtPointValuePerformanceQ3";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q4 = "fimFtPointValuePerformanceQ4";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q5 = "fimFtPointValuePerformanceQ5";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q6 = "fimFtPointValuePerformanceQ6";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q7 = "fimFtPointValuePerformanceQ7";

    public static final String FIM_FT_POINT_VALUE_PERFORMANCE_Q8 = "fimFtPointValuePerformanceQ8";

    public static final String FIM_TA_COMMENTS_FOR_PERFORMANCE = "fimTaCommentsForPerformance";

    public static final String FIM_FT_PERFORMANCE_QUESTION_SCORE = "fimFtPerformanceQuestionScore";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q1 = "fimFtPointValueServiceQ1";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q2 = "fimFtPointValueServiceQ2";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q3 = "fimFtPointValueServiceQ3";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q4 = "fimFtPointValueServiceQ4";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q5 = "fimFtPointValueServiceQ5";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q6 = "fimFtPointValueServiceQ6";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q7 = "fimFtPointValueServiceQ7";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q8 = "fimFtPointValueServiceQ8";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q9 = "fimFtPointValueServiceQ9";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q10 = "fimFtPointValueServiceQ10";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q11 = "fimFtPointValueServiceQ11";

    public static final String FIM_FT_POINT_VALUE_SERVICE_Q12 = "fimFtPointValueServiceQ12";

    public static final String FIM_TA_COMMENTS_FOR_SERVICE = "fimTaCommentsForService";

    public static final String FIM_FT_SERVICE_QUESTION_SCORE = "fimFtServiceQuestionScore";

    public static final String FIM_FT_POINT_VALUE_APPEARANCE_Q1 = "fimFtPointValueAppearanceQ1";

    public static final String FIM_FT_POINT_VALUE_APPEARANCE_Q2 = "fimFtPointValueAppearanceQ2";

    public static final String FIM_FT_POINT_VALUE_APPEARANCE_Q3 = "fimFtPointValueAppearanceQ3";

    public static final String FIM_FT_POINT_VALUE_APPEARANCE_Q4 = "fimFtPointValueAppearanceQ4";

    public static final String FIM_FT_POINT_VALUE_APPEARANCE_Q5 = "fimFtPointValueAppearanceQ5";

    public static final String FIM_TA_COMMENTS_FOR_APPEARANCE = "fimTaCommentsForAppearance";

    public static final String FIM_FT_APPEARANCE_QUESTION_SCORE = "fimFtAppearanceQuestionScore";

    public static final String FIM_DD_INSPECTION_DATE = "fimDdInspectionDate";

    public static final String FIM_DD_REPORT_SENT_TO_FRANCHISEE_DATE = "fimDdReportSentToFranchiseeDate";

    public static final String FIM_TA_GENERAL_COMMENTS = "fimTaGeneralComments";

    public static final String FIM_FT_TOTAL_SCORE = "fimFtTotalScore";

    // public static final String ENTITY_ID = "entityID";
    public static final String FIM_MYSTERY_SHOPPER_C1 = "fimMysteryShopperC1";

    public static final String FIM_MYSTERY_SHOPPER_C2 = "fimMysteryShopperC2";

    public static final String FIM_MYSTERY_SHOPPER_C3 = "fimMysteryShopperC3";

    public static final String FIM_MYSTERY_SHOPPER_C4 = "fimMysteryShopperC4";

    public static final String FIM_MYSTERY_SHOPPER_C5 = "fimMysteryShopperC5";

    public static final String FIM_MYSTERY_SHOPPER_C6 = "fimMysteryShopperC6";

    public static final String FIM_MYSTERY_SHOPPER_C7 = "fimMysteryShopperC7";

    public static final String FIM_MYSTERY_SHOPPER_C8 = "fimMysteryShopperC8";

    public static final String FIM_MYSTERY_SHOPPER_C9 = "fimMysteryShopperC9";

    public static final String FIM_MYSTERY_SHOPPER_C10 = "fimMysteryShopperC10";

    // ******Suport added by sanjeev

    public static final String TICKETDATE = "ticketDate";

    public static final String REPORTTO = "reportTo";

    public static final String ASSIGNED = "assigned";

    // ******Suport

    /* Field Names for Table FIM_TERMINATION */

    public static final String TERMINATION_ID = "terminationID";

    public static final String FIM_DD_APPROVED_DATE = "fimDdApprovedDate";

    public static final String FIM_CB_REASON = "fimCbReason";

    public static final String FIM_DD_ACTION_DATE = "fimDdActionDate";

    public static final String FIM_DD_FOLLOW_UP_DATE = "fimDdFollowUpDate";

    // public static final String FIM_DD_CURED_DATE = "fimDdCuredDate";
    public static final String FIM_DD_EFFECTIVE_ACTION_DATE = "fimDdEffectiveActionDate";

    // public static final String FIM_TA_COMMENTS = "fimTaComments";
    // public static final String ENTITY_ID = "entityID";
    public static final String FIM_CB_TYPE_OF_ACTION = "fimCbTypeOfAction";

    public static final String FIM_TT_AMOUNT = "fimTtAmount";

    public static final String FIM_TT_COLLECTED = "fimTtCollected";

    public static final String FIM_TT_LIQUIDATED_DAMAGES = "fimTtLiquidatedDamages";

    public static final String FIM_DD_TERMINATED_DATE = "fimDdTerminatedDate";

    public static final String FIM_TERMINATION_C1 = "fimTerminationC1";

    public static final String FIM_TERMINATION_C2 = "fimTerminationC2";

    public static final String FIM_TERMINATION_C3 = "fimTerminationC3";

    public static final String FIM_TERMINATION_C4 = "fimTerminationC4";

    public static final String FIM_TERMINATION_C5 = "fimTerminationC5";

    public static final String FIM_TERMINATION_C6 = "fimTerminationC6";

    public static final String FIM_TERMINATION_C7 = "fimTerminationC7";

    public static final String FIM_TERMINATION_C8 = "fimTerminationC8";

    public static final String FIM_TERMINATION_C9 = "fimTerminationC9";

    public static final String FIM_TERMINATION_C10 = "fimTerminationC10";

    public static final String FIM_RR_BANKRUPTCY = "fimRrBankruptcy";

    public static final String FIM_CB_BANKRUPTCY_TYPE = "fimCbBankruptcyType";

    public static final String FIM_TT_CASE_BK = "fimTtCaseBk";

    public static final String FIM_TT_COLLECTION_AGENCY = "fimTtCollectionAgency";

    public static final String FIM_CB_ENTITY_CLAIMING_BK = "fimCbEntityClaimingBk";

    public static final String FIM_RR_REPRESENTATIVE = "fimRrRepresentative";

    public static final String FIM_TT_NAME2 = "fimTtName2";

    public static final String FIM_TT_NAME1 = "fimTtName1";

    /* Field Names for Table FIM_MARKETING */
    public static final String MARKETING_ID = "marketingID";

    public static final String FIM_CBT_CONTACT_TITLE = "fimCbtContactTitle";

    // public static final String FIM_TT_FIRST_NAME = "fimTtFirstName";
    public static final String FIM_RR_GRAND_OPENING_REQUIRED = "fimRrGrandOpeningRequired";

    public static final String FIM_DD_GRAND_OPENING_COMPLETED_DATE = "fimDdGrandOpeningCompletedDate";

    public static final String FIM_TT_CAMPAIGN_NAME = "fimTtCampaignName";

    public static final String FIM_RR_CAMPAIGN_PARTICIPATION = "fimRrCampaignParticipation";

    public static final String FIM_RR_COUPON_REDEMPTION = "fimRrCouponRedemption";

    public static final String FIM_TT_PROGRAM_NAME = "fimTtProgramName";

    public static final String FIM_RR_PROGRAM_PARTICIPATION = "fimRrProgramParticipation";

    public static final String FIM_TT_DMA = "fimTtDMA";

    // public static final String FIM_DD_OPENING_DATE = "openingDate";
    // public static final String ENTITY_ID = "entityID";
    public static final String FIM_MARKETING_C1 = "fimMarketingC1";

    public static final String FIM_MARKETING_C2 = "fimMarketingC2";

    public static final String FIM_MARKETING_C3 = "fimMarketingC3";

    public static final String FIM_MARKETING_C4 = "fimMarketingC4";

    public static final String FIM_MARKETING_C5 = "fimMarketingC5";

    public static final String FIM_MARKETING_C6 = "fimMarketingC6";

    public static final String FIM_MARKETING_C7 = "fimMarketingC7";

    public static final String FIM_MARKETING_C8 = "fimMarketingC8";

    public static final String FIM_MARKETING_C9 = "fimMarketingC9";

    public static final String FIM_MARKETING_C10 = "fimMarketingC10";

    // public static final String FIM_TT_MIDDLE_NAME = "fimTtMiddleName";
    // public static final String FIM_TT_LAST_NAME = "fimTtLastName";

    /* Field Names for Table FIM_ADDRESS */
    public static final String FIM_TT_ADDRESS_HEADING = "fimTtAddressHeading";

    public static final String FIM_ADDRESS_C1 = "fimAddressC1";

    public static final String FIM_ADDRESS_C2 = "fimAddressC2";

    public static final String FIM_ADDRESS_C3 = "fimAddressC3";

    public static final String FIM_ADDRESS_C4 = "fimAddressC4";

    public static final String FIM_ADDRESS_C5 = "fimAddressC5";

    public static final String FIM_ADDRESS_C6 = "fimAddressC6";

    public static final String FIM_ADDRESS_C7 = "fimAddressC7";

    public static final String FIM_ADDRESS_C8 = "fimAddressC8";

    public static final String FIM_ADDRESS_C9 = "fimAddressC9";

    public static final String FIM_ADDRESS_C10 = "fimAddressC10";

    /*
     * public static final String FIM_CBT_CONTACT_TITLE = "fimCbtContactTitle"; public static final String FIM_TT_FIRST_NAME = "fimTtFirstName"; public static final String FIM_TT_MIDDLE_NAME = "fimTtMiddleName"; public static final String FIM_TT_LAST_NAME = "fimTtLastName";
     */

    /* Field Names for Table FIM_CONTRACT */
    public static final String CONTRACT_ID = "contractID";

    public static final String FIM_DD_CONTRACT_RECIEVED_SIGNED = "fimDdContractRecievedSigned";

    public static final String FIM_RR_LICENSE_AGREEMENT_PROPERLY_SIGNED = "fimRrLicenseAgreementProperlySigned";

    public static final String FIM_RR_PROMISSORY_AGREEMENT_PROPERLY_SIGNED = "fimRrPromissoryAgreementProperlySigned";

    public static final String FIM_RR_UFOC_RECIEPT_PROPERLY_SIGNED = "fimRrUfocRecieptProperlySigned";

    public static final String FIM_RR_GUARANTEE_PROPERLY_SIGNED = "fimRrGuaranteeProperlySigned";

    public static final String FIM_RR_STATE_ADDENDUM_PROPERLY_SIGNED = "fimRrStateAddendumProperlySigned";

    public static final String FIM_RR_NATL_ADV_AGMT_PROPERLY_SIGNED = "fimRrNatlAdvAgmtProperlySigned";

    public static final String FIM_RR_EFT_PROPERLY_SIGNED = "fimRrEftProperlySigned";

    public static final String FIM_DD_UFOC_DATE = "fimDdUfocDate";

    public static final String FIM_DD_AGREEMENT_RECIEVED_DATE = "fimDdAgreementRecievedDate";

    public static final String FIM_DD_FRANCHISEE_RECIEVED_DATE = "fimDdFranchiseeRecievedDate";

    public static final String FIM_DD_BUSINESS_5_EXPIRES_DATE = "fimDdBusiness5ExpiresDate";

    public static final String FIM_DD_BUSINESS_10_EXPIRES_DATE = "fimDdBusiness10ExpiresDate";

    public static final String FIM_DD_AGREEMENT_SIGNED_DATE = "fimDdAgreementSignedDate";

    public static final String FIM_DD_ADDITIONAL_UFOC_DATE = "fimDdAdditionalUfocDate";

    public static final String FIM_RR_RULE_CHECK_FLAG = "fimRrRuleCheckFlag";

    public static final String FIM_DD_ADDITIONAL_FRANCHISEE_RECIEVED_DATE = "fimDdAdditionalFranchiseeRecievedDate";

    public static final String FIM_RR_RULE_AGGREEMENTS_10_FLAG = "fimRrRuleAggreements10Flag";

    public static final String FIM_DD_ADDITIONAL_BUSINESS_10_EXPIRES_DATE = "fimDdAdditionalBusiness10ExpiresDate";

    public static final String FIM_RR_RULE_AGGREEMENTS_5_FLAG = "fimRrRuleAggreements5Flag";

    public static final String FIM_DD_FIRST_PAYMENT_DATE = "fimDdFirstPaymentDate";

    public static final String FIM_RR_STATE_REGISTRATION = "fimRrStateRegistration";

    public static final String FIM_DD_SECOND_PAYMENT_DATE = "fimDdSecondPaymentDate";

    public static final String FIM_RR_STATE_ADDENDUM = "fimRrStateAddendum";

    public static final String FIM_DD_AGREEMENT_VERSION_DATE = "fimDdAgreementVersionDate";

    public static final String FIM_DD_SETTLEMENT_PREPARED_DATE = "fimDdSettlementPreparedDate";

    public static final String FIM_DD_SETTLEMENT_EFFECTIVE_DATE = "fimDdSettlementEffectiveDate";

    public static final String FIM_DD_SETTLEMENT_SENT_DATE = "fimDdSettlementSentDate";

    public static final String FIM_DD_SETTLEMENT_FINALIZED_DATE = "fimDdSettlementFinalizedDate";

    public static final String FIM_RR_MODIFIED_OPERATING_FEES = "fimRrModifiedOperatingFees";

    public static final String FIM_RR_DEBT_PAYOFF = "fimRrDebtPayoff";

    public static final String FIM_RR_OTHER = "fimRrOther";

    public static final String FIM_RR_MODIFIED_FA_RENEWAL_TERMS = "fimRrModifiedFaRenewalTerms";

    public static final String FIM_RR_TRANSFER_FA = "fimRrTransferFa";

    public static final String FIM_RR_SEE_NOTES = "fimRrSeeNotes";

    public static final String FIM_RR_TERMINATE_DEIDENTIFY = "fimRrTerminateDeidentify";

    public static final String FIM_RR_COMMISSION_DUE = "fimRrCommissionDue";

    public static final String FIM_TT_SALES_PERSON_NAME = "fimTtSalesPersonName";

    // public static final String FIM_TT_AMOUNT = "fimTtAmount";
    public static final String FIM_CONTRACT_C1 = "fimContractC1";

    public static final String FIM_CONTRACT_C2 = "fimContractC2";

    public static final String FIM_CONTRACT_C3 = "fimContractC3";

    public static final String FIM_CONTRACT_C4 = "fimContractC4";

    public static final String FIM_CONTRACT_C5 = "fimContractC5";

    public static final String FIM_CONTRACT_C6 = "fimContractC6";

    public static final String FIM_CONTRACT_C7 = "fimContractC7";

    public static final String FIM_CONTRACT_C8 = "fimContractC8";

    public static final String FIM_CONTRACT_C9 = "fimContractC9";

    public static final String FIM_CONTRACT_C10 = "fimContractC10";

    /* Field Names for Table FIM_INSURANCE */
    public static final String INSURANCE_ID = "insuranceID";

    public static final String FIM_TT_POLICY_TITLE = "fimTtPolicyTitle";

    public static final String FIM_DD_INSURANCE_EFF_DATE = "fimDdInsuranceEffDate";

    public static final String FIM_TT_INSURANCE_COMPANY_NAME = "fimTtInsuranceCompanyName";

    public static final String FIM_TT_RATING = "fimTtRating";

    public static final String FIM_TT_POLICY_NO = "fimTtPolicyNo";

    public static final String FIM_DD_INSURANCE_EXP_DATE = "fimDdInsuranceExpDate";

    public static final String FIM_RR_WC_PRESENT = "fimRrWcPresent";

    public static final String FIM_TT_VEHICLE_REQUIRED = "fimTtVehicleRequired";

    public static final String FIM_RR_VEHICHLE_PRESENT = "fimRrVehichlePresent";

    public static final String FIM_TT_GL_REQUIRED = "fimTtGlRequired";

    public static final String FIM_RR_GL_PRESENT = "fimRrGlPresent";

    public static final String FIM_TT_WC_COVERAGE_REQUIRED = "fimTtWcCoverageRequired";

    public static final String FIM_TT_WC_COVERAGE_PRESENT = "fimTtWcCoveragePresent";

    public static final String FIM_TT_GL_COVERAGE_REQUIRED = "fimTtGlCoverageRequired";

    public static final String FIM_TT_GL_COVERAGE_PRESENT = "fimTtGlCoveragePresent";

    public static final String FIM_TT_GL_EACH_OCCURRENCE = "fimTtGlEachOccurrence";

    public static final String FIM_TT_GL_AGGREGATE = "fimTtGlAggregate";

    public static final String FIM_TT_PROPERTY_COVERAGE_REQUIRED = "fimTtPropertyCoverageRequired";

    public static final String FIM_TT_PROPERTY_COVERAGE_PRESENT = "fimTtPropertyCoveragePresent";

    public static final String FIM_TT_PROPERTY_DEDUCTIBLE = "fimTtPropertyDeductible";

    public static final String FIM_TT_VEHICLE_COVERAGE_REQUIRED = "fimTtVehicleCoverageRequired";

    public static final String FIM_TT_VEHICLE_COVERAGE_PRESENT = "fimTtVehicleCoveragePresent";

    public static final String FIM_TT_OTHER_COVERAGE_REQUIRED = "fimTtOtherCoverageRequired";

    public static final String FIM_TT_OTHER_COVERAGE_PRESENT = "fimTtOtherCoveragePresent";

    public static final String FIM_TT_OTHER_POLICY_DESCRIPTION = "fimTtOtherPolicyDescription";

    public static final String FIM_TA_OTHER_COMMENTS = "fimTaOtherComments";

    public static final String FIM_TT_OTHER2_COVERAGE_REQUIRED = "fimTtOther2CoverageRequired";

    public static final String FIM_TT_OTHER2_COVERAGE_PRESENT = "fimTtOther2CoveragePresent";

    public static final String FIM_TA_OTHER2_COMMENTS = "fimTaOther2Comments";

    public static final String FIM_TT_TOTAL_COVERAGE = "fimTtTotalCoverage";

    public static final String FIM_TT_CONTACT_FIRST_NAME = "fimTtContactFirstName";

    public static final String FIM_TT_CONTACT_MIDDLE_NAME = "fimTtContactMiddleName";

    public static final String FIM_TT_CONTACT_LAST_NAME = "fimTtContactLastName";

    public static final String FIM_TT_AGENCY_NAME = "fimTtAgencyName";

    /* public static final String FIM_TA_COMMENTS = "fimTaComments"; */
    public static final String FIM_TT_COMMENTS_ATTACHMENT = "fimTtCommentsAttachment";

    public static final String FIM_TT_COMMENTS_ATTITLE = "fimTtCommentsAttitle";

    public static final String FIM_INSURANCE_C1 = "fimInsuranceC1";

    public static final String FIM_INSURANCE_C2 = "fimInsuranceC2";

    public static final String FIM_INSURANCE_C3 = "fimInsuranceC3";

    public static final String FIM_INSURANCE_C4 = "fimInsuranceC4";

    public static final String FIM_INSURANCE_C5 = "fimInsuranceC5";

    public static final String FIM_INSURANCE_C6 = "fimInsuranceC6";

    public static final String FIM_INSURANCE_C7 = "fimInsuranceC7";

    public static final String FIM_INSURANCE_C8 = "fimInsuranceC8";

    public static final String FIM_INSURANCE_C9 = "fimInsuranceC9";

    public static final String FIM_INSURANCE_C10 = "fimInsuranceC10";

    /* Field Names for Table FIM_LENDER */
    public static final String LENDER_ID = "lenderID";

    public static final String FIM_CBT_TITLE = "fimCbtTitle";

    public static final String FIM_DD_COLLATERAL_ASSIGNMENT_EXPIRATION_DATE = "fimDdCollateralAssignmentExpirationDate";

    public static final String FIM_TT_COMFORT_LETTER_FORM = "fimTtComfortLetterForm";

    public static final String FIM_DD_COMFORT_LETTER_DATE = "fimDdComfortLetterDate";

    public static final String FIM_RR_COMFORT_AGREEMENT = "fimRrComfortAgreement";

    public static final String FIM_DD_DATE_COMFORT_AGREEMENT_INFO = "fimDdDateComfortAgreementInfo";

    public static final String FIM_TT_LENDER_CONTACT_ONE = "fimTtLenderContactOne";

    public static final String FIM_TT_LENDER_CONTACT_TWO = "fimTtLenderContactTwo";

    public static final String FIM_TT_CONTACT_1_PHONE = "fimTtContact1Phone";
    
    // Added by Chetan on 17th sep 2008 start
    public static final String FIM_TT_CONTACT_1_PHONE_EXTN = "fimTtContact1PhoneExtn";
    
   
    
    // Added by Chetan on 17th sep 2008 end
    
	public static String 	ABORT_GENERIC_PROCESSING_NOW = "aportGenericProcess"; 
    
    /*START: TMS_INVOICE_LABELS*/
	public static String INV_LABEL_ID = "invLabelID";
	public static String INV_LABEL_KEY_FIELD = "labelKeyField";
	public static String INV_LABEL_NAME = "labelName";
	public static String INV_LABEL_VALUE = "labelValue";
	
	public static String INV_SHIP_TO = "invShipTo";
	public static String INV_BILL_TO = "invBillTo";
	public static String INV_PRODUCT_SERVICE = "invProductService";
	public static String INV_DESCRIPTION = "invDescription";
	public static String INV_QUANTITY = "invQuantity";
	public static String INV_RATE = "invRate";
	public static String INV_AMOUNT = "invAmount";
	public static String INV_SERVICE = "invService";//P_E_CT_8461 added by neeti
	/*END: TMS_INVOICE_LABELS*/
    

    public static final String FIM_TT_CONTACT_2_PHONE = "fimTtContact2Phone";

    public static final String FIM_TT_CONTACT_1_FAX = "fimTtContact1Fax";

    public static final String FIM_TT_CONTACT_2_FAX = "fimTtContact2Fax";

    public static final String FIM_TT_CONTACT_1_EMAIL = "fimTtContact1Email";

    public static final String FIM_TT_CONTACT_2_EMAIL = "fimTtContact2Email";

    public static final String FIM_LENDER_C1 = "fimLenderC1";

    public static final String FIM_LENDER_C2 = "fimLenderC2";

    public static final String FIM_LENDER_C3 = "fimLenderC3";

    public static final String FIM_LENDER_C4 = "fimLenderC4";

    public static final String FIM_LENDER_C5 = "fimLenderC5";

    public static final String FIM_LENDER_C6 = "fimLenderC6";

    public static final String FIM_LENDER_C7 = "fimLenderC7";

    public static final String FIM_LENDER_C8 = "fimLenderC8";

    public static final String FIM_LENDER_C9 = "fimLenderC9";

    public static final String FIM_LENDER_C10 = "fimLenderC10";
    
    // Added by Abhishek Rastogi on 17th sep 2008 start
    public static final String FIM_TT_CONTACT_2_PHONE_EXTN = "fimTtContact2PhoneExtn";
   // Added by Abhishek Rastogi on 17th sep 2008 end

    /* Field Names for Table FIM_REAL_ESTATE */
    public static final String REAL_ESTATE_ID = "realEstateID";

    public static final String FIM_CB_OWNED_LEASED = "fimCbOwnedLeased";

    public static final String FIM_TT_SITE_ADDRESS1 = "fimTtSiteAddress1";

    public static final String FIM_TT_SITE_ADDRESS2 = "fimTtSiteAddress2";

    public static final String FIM_TT_SITE_CITY = "fimTtSiteCity";

    public static final String FIM_TT_SQUARE_FOOTAGE = "fimTtSquareFootage";

    public static final String FIM_TT_BUILDING_DIMENTIONS_X = "fimTtBuildingDimentionsX";

    public static final String FIM_TT_BUILDING_DIMENTIONS_Y = "fimTtBuildingDimentionsY";

    public static final String FIM_TT_BUILDING_DIMENTIONS_Z = "fimTtBuildingDimentionsZ";

    public static final String FIM_TT_PARKING_SPACES = "fimTtParkingSpaces";

    public static final String FIM_TT_DEAL_TYPE = "fimTtDealType";

    public static final String FIM_CB_PREMISES_TYPE = "fimCbPremisesType";

    public static final String FIM_DD_LOI_SENT = "fimDdLoiSent";

    public static final String FIM_DD_LEASE_SIGNED_DATE = "fimDdLeaseSignedDate";

    public static final String FIM_DD_APPROVAL_DATE = "fimDdApprovalDate";

    public static final String FIM_DD_LEASE_COMMENCEMENT_DATE = "fimDdLeaseCommencementDate";

    public static final String FIM_DD_EXPIRATION_DATE = "fimDdExpirationDate";

    public static final String FIM_TT_MONTHLY_RENT = "fimTtMonthlyRent";

    public static final String FIM_DD_RENT_INCREASES = "fimDdRentIncreases";

    public static final String FIM_RR_ACAL = "fimRrAcal";

    public static final String FIM_DD_RENEWAL_FEE_PAID_FIRST = "fimDdRenewalFeePaidFirst";

    public static final String FIM_DD_RENEWAL_FEE_PAID_SECOND = "fimDdRenewalFeePaidSecond";

    public static final String FIM_DD_RENEWAL_FEE_PAID_THIRD = "fimDdRenewalFeePaidThird";

    public static final String FIM_TT_RENEWAL_TERM_FIRST = "fimTtRenewalTermFirst";

    public static final String FIM_TT_RENEWAL_TERM_SECOND = "fimTtRenewalTermSecond";

    public static final String FIM_TT_RENEWAL_TERM_THIRD = "fimTtRenewalTermThird";

    public static final String FIM_DD_RENEWAL_DUE_DATE_FIRST = "fimDdRenewalDueDateFirst";

    public static final String FIM_DD_RENEWAL_DUE_DATE_SECOND = "fimDdRenewalDueDateSecond";

    public static final String FIM_DD_RENEWAL_DUE_DATE_THIRD = "fimDdRenewalDueDateThird";

    public static final String FIM_TT_RENEWAL_FEE_FIRST = "fimTtRenewalFeeFirst";

    public static final String FIM_TT_RENEWAL_FEE_SECOND = "fimTtRenewalFeeSecond";

    public static final String FIM_TT_RENEWAL_FEE_THIRD = "fimTtRenewalFeeThird";

    public static final String FIM_TA_RENEWAL_OPTIONS = "fimTaRenewalOptions";

    public static final String FIM_RR_PURCHASE_OPTION = "fimRrPurchaseOption";

    public static final String FIM_DD_PROJECTED_OPENING_DATE = "fimDdProjectedOpeningDate";

    public static final String FIM_RR_GENERAL_CONTRACTOR_SELECTOR = "fimRrGeneralContractorSelector";

    public static final String FIM_TT_NAME_GENERAL_CONTRACTOR = "fimTtNameGeneralContractor";

    public static final String FIM_TT_ADDRESS_GENERAL_CONTRACTOR = "fimTtAddressGeneralContractor";

    public static final String FIM_DD_PERMIT_APPLIED = "fimDdPermitApplied";

    public static final String FIM_DD_PERMIT_ISSUED = "fimDdPermitIssued";

    public static final String FIM_DD_CERTIFICATE = "fimDdCertificate";

    public static final String FIM_DD_TURN_OVER_DATE = "fimDdTurnOverDate";

    public static final String FIM_DD_GRAND_OPENING_DATE = "fimDdGrandOpeningDate";

    public static final String FIM_CBT_SUBLESSOR_CONTACT_TITLE = "fimCbtSublessorContactTitle";

    public static final String FIM_TT_SUBLESSOR_FIRST_NAME = "fimTtSublessorFirstName";

    public static final String FIM_TT_SUBLESSOR_MIDDLE_NAME = "fimTtSublessorMiddleName";

    public static final String FIM_TT_SUBLESSOR_LAST_NAME = "fimTtSublessorLastName";

    public static final String FIM_CBT_TENANT_TITLE = "fimCbtTenantTitle";

    public static final String FIM_TT_TENANT_FIRST_NAME = "fimTtTenantFirstName";

    public static final String FIM_TT_TENANT_MIDDLE_NAME = "fimTtTenantMiddleName";

    public static final String FIM_TT_TENANT_LAST_NAME = "fimTtTenantLastName";

    public static final String FIM_TT_OWNED_LEASED_ATTITLE = "fimTtOwnedLeasedAttitle";

    public static final String FIM_REAL_ESTATE_C1 = "fimRealEstateC1";

    public static final String FIM_REAL_ESTATE_C2 = "fimRealEstateC2";

    public static final String FIM_REAL_ESTATE_C3 = "fimRealEstateC3";

    public static final String FIM_REAL_ESTATE_C4 = "fimRealEstateC4";

    public static final String FIM_REAL_ESTATE_C5 = "fimRealEstateC5";

    public static final String FIM_REAL_ESTATE_C6 = "fimRealEstateC6";

    public static final String FIM_REAL_ESTATE_C7 = "fimRealEstateC7";

    public static final String FIM_REAL_ESTATE_C8 = "fimRealEstateC8";

    public static final String FIM_REAL_ESTATE_C9 = "fimRealEstateC9";

    public static final String FIM_REAL_ESTATE_C10 = "fimRealEstateC10";

    /* Field Names for Table FIM_RENEWAL */
    public static final String RENEWAL_ID = "renewalID";

    public static final String FIM_CB_CURRENT_STATUS = "fimCbCurrentStatus";

    public static final String FIM_DD_AS_OF = "fimDdAsOf";

    public static final String FIM_TT_PERSON_SIGNING = "fimTtPersonSigning";

    public static final String FIM_TT_TITLE = "fimTtTitle";

    public static final String FIM_DD_NEW_EXPIRATION_DATE = "fimDdNewExpirationDate";

    public static final String FIM_DD_NINETY_DAY_NOTICE = "fimDdNinetyDayNotice";

    public static final String FIM_DD_RENEWAL_PACKAGE_SENT = "fimDdRenewalPackageSent";

    public static final String FIM_TT_RENEWAL_MATERIALS = "fimTtRenewalMaterials";

    public static final String FIM_TA_MISSING_DOCS = "fimTaMissingDocs";

    public static final String FIM_TA_NOTES = "fimTaNotes";

    public static final String FIM_RENEWAL_C1 = "fimRenewalC1";

    public static final String FIM_RENEWAL_C2 = "fimRenewalC2";

    public static final String FIM_RENEWAL_C3 = "fimRenewalC3";

    public static final String FIM_RENEWAL_C4 = "fimRenewalC4";

    public static final String FIM_RENEWAL_C5 = "fimRenewalC5";

    public static final String FIM_RENEWAL_C6 = "fimRenewalC6";

    public static final String FIM_RENEWAL_C7 = "fimRenewalC7";

    public static final String FIM_RENEWAL_C8 = "fimRenewalC8";

    public static final String FIM_RENEWAL_C9 = "fimRenewalC9";

    public static final String FIM_RENEWAL_C10 = "fimRenewalC10";

    /* Field Names for Table FIM_FINANCIAL */
    public static final String FINANCIAL_ID = "financialID";

    // public static final String FIM_CBT_CONTACT_TITLE = "fimCbtContactTitle";
    // public static final String FIM_TT_FIRST_NAME = "fimTtFirstName";
    // public static final String FIM_TT_MIDDLE_NAME = "fimTtMiddleName";
    // public static final String FIM_TT_LAST_NAME = "fimTtLastName";
    // public static final String ENTITY_ID = "entityID";
    public static final String FIM_TT_CASH_REVENUE = "fimTtCashRevenue";

    public static final String FIM_TT_NOTE_REVENUE = "fimTtNoteRevenue";

    public static final String FIM_FINANCIAL_C1 = "fimFinancialC1";

    public static final String FIM_FINANCIAL_C2 = "fimFinancialC2";

    public static final String FIM_FINANCIAL_C3 = "fimFinancialC3";

    public static final String FIM_FINANCIAL_C4 = "fimFinancialC4";

    public static final String FIM_FINANCIAL_C5 = "fimFinancialC5";

    public static final String FIM_FINANCIAL_C6 = "fimFinancialC6";

    public static final String FIM_FINANCIAL_C7 = "fimFinancialC7";

    public static final String FIM_FINANCIAL_C8 = "fimFinancialC8";

    public static final String FIM_FINANCIAL_C9 = "fimFinancialC9";

    public static final String FIM_FINANCIAL_C10 = "fimFinancialC10";

    public static final String FIM_DD_ROYALTIES_PAYABLE_START_DATE = "fimDdRoyaltiesPayableStartDate";

    public static final String FIM_DD_ADVERTISING_FEE_PAYABLE_START_DATE = "fimDdAdvertisingFeePayableStartDate";

    public static final String FIM_TT_HEADING = "fimTtHeading";

    public static final String FIM_DD_EFFECTIVE_START_DATE = "fimDdEffectiveStartDate";

    public static final String FIM_DD_EFFECTIVE_RM_DATE = "fimDdEffectiveRmDate";

    public static final String FIM_TT_ROYALTY_PERC = "fimTtRoyaltyPerc";

    public static final String FIM_TT_CURRENT_PERC = "fimTtCurrentPerc";

    public static final String FIM_TT_PREVIOUS_PERC = "fimTtPreviousPerc";

    public static final String FIM_DD_CHANGE_RM_DATE = "fimDdChangeRmDate";

    public static final String FIM_TT_YEAR_1 = "fimTtYear1";

    public static final String FIM_TT_YEAR_2 = "fimTtYear2";

    public static final String FIM_TT_YEAR_3 = "fimTtYear3";

    public static final String FIM_TT_YEAR_4 = "fimTtYear4";

    public static final String FIM_TT_YEAR_5 = "fimTtYear5";

    public static final String FIM_TT_EXTRANET_FEE = "fimTtExtranetFee";

    public static final String FIM_TT_CMS_FEE = "fimTtCmsFee";

    public static final String FIM_TT_ASP_FEE = "fimTtAspFee";

    public static final String FIM_TT_MONTHLY_MAIN_FEE = "fimTtMonthlyMainFee";

    public static final String FIM_TT_VPN_FEE = "fimTtVpnFee";

    public static final String FIM_TT_CREDIT_STATUS = "fimTtCreditStatus";

    public static final String FIM_TT_OTHER_1 = "fimTtOther1";

    public static final String FIM_TT_OTHER_2 = "fimTtOther2";

    public static final String FIM_TT_OTHER_3 = "fimTtOther3";

    public static final String FIM_RR_ROYALTY_R_TYPE = "fimRrRoyaltyRType";

    public static final String FIM_HD_ROYALTY_TYPE_VALUE = "fimHdRoyaltyTypeValue";

    public static final String FIM_CB_ROYALTY_PERIOD = "fimCbRoyaltyPeriod";

    public static final String FIM_RR_ADVERTISING_TYPE = "fimRrAdvertisingType";

    public static final String FIM_HD_ADVERTISING_TYPE_VALUE = "fimHdAdvertisingTypeValue";

    public static final String FIM_CB_ADVERTISING_PERIOD = "fimCbAdvertisingPeriod";

    public static final String FIM_RR_OTHER_FEE_TYPE = "fimRrOtherFeeType";

    public static final String FIM_HD_OTHER_FEE_VALUE = "fimHdOtherFeeValue";

    public static final String FIM_CB_OTHER_PERIOD = "fimCbOtherPeriod";

    public static final String FIM_RR_FINANCE_APPROVED = "fimRrFinanceApproved";

    public static final String FIM_TT_FRANCHISEE_FEE = "fimTtFranchiseeFee";

    public static final String FIM_DD_PROMISSORY_NOTE_PREPARE_DATE = "fimDdPromissoryNotePrepareDate";

    public static final String FIM_DD_PAYMENT_PLAN_DATED = "fimDdPaymentPlanDated";

    public static final String FIM_DD_PROMISSORY_NOTE_DATED = "fimDdPromissoryNoteDated";

    public static final String FIM_TT_PAYMENT_PLAN_AMOUNT = "fimTtPaymentPlanAmount";

    public static final String FIM_TT_PROMISSORY_NOTE_AMOUNT = "fimTtPromissoryNoteAmount";

    public static final String FIM_DD_PLAN_FINAL_PAYMENT_DATED = "fimDdPlanFinalPaymentDated";

    public static final String FIM_DD_PROMISSORY_FINAL_PAYMENT_DATED = "fimDdPromissoryFinalPaymentDated";

    public static final String FIM_RR_FINANCIAL_STATEMENT = "fimRrFinancialStatement";

    /* Field Names for Table FIM_QA */
    public static final String QA_ID = "qaID";

    // public static final String FIM_DD_INSPECTION_DATE = "fimDdInspectionDate";
    public static final String FIM_TT_INSPECTION_TYPE_ID = "fimTtInspectionTypeID";

    public static final String FIM_TT_INSPECTOR_NAME = "fimTtInspectorName";

    public static final String FIM_TT_EXPERIENCE_SCORE = "fimTtExperienceScore";

    public static final String FIM_TT_EXPERIENCE_GRADE = "fimTtExperienceGrade";

    public static final String FIM_TT_ASSURANCE_SCORE = "fimTtAssuranceScore";

    public static final String FIM_TT_ASSURANCE_GRADE = "fimTtAssuranceGrade";

    public static final String FIM_DD_RE_INSPECTION_DATE = "fimDdReInspectionDate";

    public static final String FIM_DD_NEXT_INSPECTION_DATE = "fimDdNextInspectionDate";

    // public static final String ENTITY_ID = "entityID";
    public static final String FIM_RR_CRITICAL = "fimRrCritical";

    public static final String FIM_QA_C1 = "fimQaC1";

    public static final String FIM_QA_C2 = "fimQaC2";

    public static final String FIM_QA_C3 = "fimQaC3";

    public static final String FIM_QA_C4 = "fimQaC4";

    public static final String FIM_QA_C5 = "fimQaC5";

    public static final String FIM_QA_C6 = "fimQaC6";

    public static final String FIM_QA_C7 = "fimQaC7";

    public static final String FIM_QA_C8 = "fimQaC8";

    public static final String FIM_QA_C9 = "fimQaC9";

    public static final String FIM_QA_C10 = "fimQaC10";
    //added by pritam for Area QA history in FIm 2 aug 2010
    
    public static final String FIM_MU_QA_C1 = "fimMuQaC1";

    public static final String FIM_MU_QA_C2 = "fimMuQaC2";

    public static final String FIM_MU_QA_C3 = "fimMuQaC3";

    public static final String FIM_MU_QA_C4 = "fimMuQaC4";

    public static final String FIM_MU_QA_C5 = "fimMuQaC5";

    public static final String FIM_MU_QA_C6 = "fimMuQaC6";

    public static final String FIM_MU_QA_C7 = "fimMuQaC7";

    public static final String FIM_MU_QA_C8 = "fimMuQaC8";

    public static final String FIM_MU_QA_C9 = "fimMuQaC9";

    public static final String FIM_MU_QA_C10 = "fimMuQaC10";
    
    public static final String FIM_MU_MYSTERY_SHOPPER = "fimMuMysteryShopper";
    
    public static final String FIM_MU_MYSTERY_SHOPPER_C1 = "fimMuMysteryShopperC1";

    public static final String FIM_MU_MYSTERY_SHOPPER_C2 = "fimMuMysteryShopperC2";

    public static final String FIM_MU_MYSTERY_SHOPPER_C3 = "fimMuMysteryShopperC3";

    public static final String FIM_MU_MYSTERY_SHOPPER_C4 = "fimMuMysteryShopperC4";

    public static final String FIM_MU_MYSTERY_SHOPPER_C5 = "fimMuMysteryShopperC5";

    public static final String FIM_MU_MYSTERY_SHOPPER_C6 = "fimMuMysteryShopperC6";

    public static final String FIM_MU_MYSTERY_SHOPPER_C7 = "fimMuMysteryShopperC7";

    public static final String FIM_MU_MYSTERY_SHOPPER_C8 = "fimMuMysteryShopperC8";

    public static final String FIM_MU_MYSTERY_SHOPPER_C9 = "fimMuMysteryShopperC9";

    public static final String FIM_MU_MYSTERY_SHOPPER_C10 = "fimMuMysteryShopperC10";
    
    
    
    public static final String FIM_MU_MARKETING_ID = "fimMuMarketingID";
    
    public static final String FIM_MU_MARKETING_C1 = "fimMuMarketingC1";

    public static final String FIM_MU_MARKETING_C2 = "fimMuMarketingC2";

    public static final String FIM_MU_MARKETING_C3 = "fimMuMarketingC3";

    public static final String FIM_MU_MARKETING_C4 = "fimMuMarketingC4";

    public static final String FIM_MU_MARKETING_C5 = "fimMuMarketingC5";

    public static final String FIM_MU_MARKETING_C6 = "fimMuMarketingC6";

    public static final String FIM_MU_MARKETING_C7 = "fimMuMarketingC7";

    public static final String FIM_MU_MARKETING_C8 = "fimMuMarketingC8";

    public static final String FIM_MU_MARKETING_C9 = "fimMuMarketingC9";

    public static final String FIM_MU_MARKETING_C10 = "fimMuMarketingC10";
    
    public static final String FIM_MU_EVENTS_ID = "fimMuEventsID";
    
    public static final String FIM_MU_EVENTS_C1 = "fimMuEventsC1";

    public static final String FIM_MU_EVENTS_C2 = "fimMuEventsC2";

    public static final String FIM_MU_EVENTS_C3 = "fimMuEventsC3";

    public static final String FIM_MU_EVENTS_C4 = "fimMuEventsC4";

    public static final String FIM_MU_EVENTS_C5 = "fimMuEventsC5";

    public static final String FIM_MU_EVENTS_C6 = "fimMuEventsC6";

    public static final String FIM_MU_EVENTS_C7 = "fimMuEventsC7";

    public static final String FIM_MU_EVENTS_C8 = "fimMuEventsC8";

    public static final String FIM_MU_EVENTS_C9 = "fimMuEventsC9";

    public static final String FIM_MU_EVENTS_C10 = "fimMuEventsC10";
    
    
    public static final String FIM_AREA_EVENTS_C1 = "fimAreaEventsC1";

    public static final String FIM_AREA_EVENTS_C2 = "fimAreaEventsC2";

    public static final String FIM_AREA_EVENTS_C3 = "fimAreaEventsC3";

    public static final String FIM_AREA_EVENTS_C4 = "fimAreaEventsC4";

    public static final String FIM_AREA_EVENTS_C5 = "fimAreaEventsC5";

    public static final String FIM_AREA_EVENTS_C6 = "fimAreaEventsC6";

    public static final String FIM_AREA_EVENTS_C7 = "fimAreaEventsC7";

    public static final String FIM_AREA_EVENTS_C8 = "fimAreaEventsC8";

    public static final String FIM_AREA_EVENTS_C9 = "fimAreaEventsC9";

    public static final String FIM_AREA_EVENTS_C10 = "fimAreaEventsC10";
    
    public static final String FIM_AREA_MARKETING_C1 = "fimAreaMarketingC1";

    public static final String FIM_AREA_MARKETING_C2 = "fimAreaMarketingC2";

    public static final String FIM_AREA_MARKETING_C3 = "fimAreaMarketingC3";

    public static final String FIM_AREA_MARKETING_C4 = "fimAreaMarketingC4";

    public static final String FIM_AREA_MARKETING_C5 = "fimAreaMarketingC5";

    public static final String FIM_AREA_MARKETING_C6 = "fimAreaMarketingC6";

    public static final String FIM_AREA_MARKETING_C7 = "fimAreaMarketingC7";

    public static final String FIM_AREA_MARKETING_C8 = "fimAreaMarketingC8";

    public static final String FIM_AREA_MARKETING_C9 = "fimAreaMarketingC9";

    public static final String FIM_AREA_MARKETING_C10 = "fimAreaMarketingC10";
    public static final String FIM_AREA_QA = "fimAreaQa";
    public static final String FIM_AREA_QA_C1 = "fimAreaQaC1";

    public static final String FIM_AREA_QA_C2 = "fimAreaQaC2";

    public static final String FIM_AREA_QA_C3 = "fimAreaQaC3";

    public static final String FIM_AREA_QA_C4 = "fimAreaQaC4";

    public static final String FIM_AREA_QA_C5 = "fimAreaQaC5";

    public static final String FIM_AREA_QA_C6 = "fimAreaQaC6";

    public static final String FIM_AREA_QA_C7 = "fimAreaQaC7";

    public static final String FIM_AREA_QA_C8 = "fimAreaQaC8";

    public static final String FIM_AREA_QA_C9 = "fimAreaQaC9";

    public static final String FIM_AREA_QA_C10 = "fimAreaQaC10";
    
    public static final String FIM_AREA_MYSTERY_SHOPPER = "fimAreaMysteryShopper";
    
    public static final String FIM_AREA_MYSTERY_SHOPPER_C1 = "fimAreaMysteryShopperC1";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C2 = "fimAreaMysteryShopperC2";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C3 = "fimAreaMysteryShopperC3";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C4 = "fimAreaMysteryShopperC4";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C5 = "fimAreaMysteryShopperC5";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C6 = "fimAreaMysteryShopperC6";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C7 = "fimAreaMysteryShopperC7";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C8 = "fimAreaMysteryShopperC8";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C9 = "fimAreaMysteryShopperC9";

    public static final String FIM_AREA_MYSTERY_SHOPPER_C10 = "fimAreaMysteryShopperC10";
    
    public static final String FIM_AREA_ENTITY_DETAIL_C1 = "fimAreaEntityDetailC1";

    public static final String FIM_AREA_ENTITY_DETAIL_C2 = "fimAreaEntityDetailC2";

    public static final String FIM_AREA_ENTITY_DETAIL_C3 = "fimAreaEntityDetailC3";

    public static final String FIM_AREA_ENTITY_DETAIL_C4 = "fimAreaEntityDetailC4";

    public static final String FIM_AREA_ENTITY_DETAIL_C5 = "fimAreaEntityDetailC5";

    public static final String FIM_AREA_ENTITY_DETAIL_C6 = "fimAreaEntityDetailC6";

    public static final String FIM_AREA_ENTITY_DETAIL_C7 = "fimAreaEntityDetailC7";

    public static final String FIM_AREA_ENTITY_DETAIL_C8 = "fimAreaEntityDetailC8";

    public static final String FIM_AREA_ENTITY_DETAIL_C9 = "fimAreaEntityDetailC9";

    public static final String FIM_AREA_ENTITY_DETAIL_C10 = "fimAreaEntityDetailC10";
    public static final String AREA_ENTITY_DETAIL = "areaEntityDetail";
    // pritam  end

    public static final String FIM_TT_MTM = "fimTtMtm";

    /* Field Names for Table FIM_LICENSE_AGREEMENT */
    public static final String LICENSE_AGREEMENT_ID = "licenseAgreementID";

    public static final String FIM_TT_INITIAL_TERM = "fimTtInitialTerm";

    /*
     * //public static final String FIM_TT_RENEWAL_TERM_FIRST = //"fimTtRenewalTermFirst"; //public static final String FIM_TT_RENEWAL_TERM_SECOND = //"fimTtRenewalTermSecond"; //public static final String FIM_TT_RENEWAL_TERM_THIRD = "fimTtRenewalTermThird"; //public static final String FIM_DD_RENEWAL_DUE_DATE_FIRST = "fimDdRenewalDueDateFirst"; //public static final String FIM_DD_RENEWAL_DUE_DATE_SECOND =
     * "fimDdRenewalDueDateSecond"; //public static final String FIM_DD_RENEWAL_DUE_DATE_THIRD = "fimDdRenewalDueDateThird"; //public static final String FIM_TT_RENEWAL_FEE_FIRST = "fimTtRenewalFeeFirst"; //public static final String FIM_TT_RENEWAL_FEE_SECOND = "fimTtRenewalFeeSecond"; //public static final String FIM_TT_RENEWAL_FEE_THIRD = "fimTtRenewalFeeThird";
     */
    public static final String FIM_DD_RENEWAL_FEE_PAID_FIRST_DATE = "fimDdRenewalFeePaidFirstDate";

    public static final String FIM_DD_RENEWAL_FEE_PAID_SECOND_DATE = "fimDdRenewalFeePaidSecondDate";

    public static final String FIM_DD_RENEWAL_FEE_PAID_THIRD_DATE = "fimDdRenewalFeePaidThirdDate";

    public static final String FIM_DD_TRANSFER_DATE = "fimDdTransferDate";

    // public static final String FIM_CBT_CONTACT_TITLE = "fimCbtContactTitle";
    public static final String FIM_TT_STATE_ADDENDUM = "fimTtStateAddendum";

    public static final String FIM_TT_OTHER_ADDENDUM = "fimTtOtherAddendum";

    public static final String FIM_TT_PROTECTED_TERRITORY = "fimTtProtectedTerritory";

    public static final String FIM_DD_DATE_EXECUTED = "fimDdDateExecuted";

    public static final String FIM_DD_EFFECTIVE_DATE = "fimDdEffectiveDate";

    /* //public static final String FIM_DD_EXPIRATION_DATE = "fimDdExpirationDate"; */
    public static final String FIM_DD_STORE_SOLD_DATE = "fimDdStoreSoldDate";

    /*
     * //public static final String FIM_TA_COMMENTS = "fimTaComments"; //public static final String ENTITY_ID = "entityID"; //public static final String FIM_DD_APPROVED_DATE = "fimDdApprovedDate";
     */
    public static final String FIM_DD_CLOSING_DATE = "fimDdClosingDate";

    public static final String FIM_TT_SALESPERSON = "fimTtSalesperson";

    // public static final String FIM_TT_FIRST_NAME = "fimTtFirstName";
    // public static final String FIM_TT_MIDDLE_NAME = "fimTtMiddleName";
    // public static final String FIM_TT_LAST_NAME = "fimTtLastName";
    public static final String FIM_TT_PREVIOUS_LICENSE_NUMBER = "fimTtPreviousLicenseNumber";

    public static final String FIM_TT_RELATED_CENTER = "fimTtRelatedCenter";

    public static final String FIM_DD_OPENING_DATE = "fimDdOpeningDate";

    public static final String FIM_DD_REQUIRED_OPENING_DATE = "fimDdRequiredOpeningDate";

    public static final String FIM_TT_DESCRIPTION_1 = "fimTtDescription1";

    public static final String FIM_TT_AMOUNT_1 = "fimTtAmount1";

    public static final String FIM_TT_AMOUNT_TERM_1 = "fimTtAmountTerm1";

    public static final String FIM_TT_INTEREST_1 = "fimTtInterest1";

    public static final String FIM_TA_COMMENT_1 = "fimTaComment1";

    public static final String FIM_TT_DESCRIPTION_2 = "fimTtDescription2";

    public static final String FIM_TT_AMOUNT_2 = "fimTtAmount2";

    public static final String FIM_TT_AMOUNT_TERM_2 = "fimTtAmountTerm2";

    public static final String FIM_TT_INTEREST_2 = "fimTtInterest2";

    public static final String FIM_TA_COMMENT_2 = "fimTaComment2";

    public static final String FIM_TT_DESCRIPTION_3 = "fimTtDescription3";

    public static final String FIM_TT_AMOUNT_3 = "fimTtAmount3";

    public static final String FIM_TT_AMOUNT_TERM_3 = "fimTtAmountTerm3";

    public static final String FIM_TT_INTEREST_3 = "fimTtInterest3";

    public static final String FIM_TA_COMMENT_3 = "fimTaComment3";

    public static final String FIM_LICENSE_AGREEMENT_C1 = "fimLicenseAgreementC1";

    public static final String FIM_LICENSE_AGREEMENT_C2 = "fimLicenseAgreementC2";

    public static final String FIM_LICENSE_AGREEMENT_C3 = "fimLicenseAgreementC3";

    public static final String FIM_LICENSE_AGREEMENT_C4 = "fimLicenseAgreementC4";

    public static final String FIM_LICENSE_AGREEMENT_C5 = "fimLicenseAgreementC5";

    public static final String FIM_LICENSE_AGREEMENT_C6 = "fimLicenseAgreementC6";

    public static final String FIM_LICENSE_AGREEMENT_C7 = "fimLicenseAgreementC7";

    public static final String FIM_LICENSE_AGREEMENT_C8 = "fimLicenseAgreementC8";

    public static final String FIM_LICENSE_AGREEMENT_C9 = "fimLicenseAgreementC9";

    public static final String FIM_LICENSE_AGREEMENT_C10 = "fimLicenseAgreementC10";
    
    ///Added by niraj sachan start
    public static final String MU_LICENSE_AGREEMENT_ID = "muLicenseAgreementID";
    public static final String MU_FINANCIAL_ID ="mufinancialID";
    public static final String MU_INSURANCE_ID = "muinsuranceID";
    public static final String MU_CALL_ID = "muCallID";
    ///Added by niraj sachan end

    public static final String FIM_RR_RIGHTS_OF_FIRST_REFUSAL = "fimRrRightsOfFirstRefusal";

    public static final String DASHBOARD_REPORT = "dashboardReport";

    public static final String DASHBOARD_REPORT_TIME_PERIOD = "dashboardReportTimePeriod";

    public static String CUSTOMER_DETAILS = "customerDetails";
    // Entries for FS_DASHBOARD ~ Amrit Paul
    public static final String DASHBOARD_ID = "dashboardID";

    public static final String REPORT_ID = "reportID";

    public static final String LEAD_SOURCE_CATEGORY = "leadSourceCategory";

    // Entries for FS_DASHBOARD_TIME_PERIOD ~ Amrit Paul
    public static final String TIME_PERIOD_ID = "timePeriodID";

    public static final String TIME_PERIOD_TEXT = "timePeriodText";

    // //////////////////// FIM NEW BLOCK ENDS//////////////////////////////////

    public static final String FROM_ADDRESS = "fromAddress";

    public static final String TO_ADDRESS = "toAddress";

    public static final String MAIL_ATTACHMENT_ID = "mailAttachmentID";

    public static final String LAST_ATTENDED = "lastAttended";

    // Added by Suniti on 23rd March 06: To modify mail content
    /* Field Names for Table SM_ALERT_MESSAGE */
    public static final String ALERT_MESSAGE_ID = "alertMessageId";

    public static final String START_ALERT_MESSAGE = "startAlertMessageValue";

    public static final String START_REMINDER_ALERT_MESSAGE = "startReminderAlertMessageValue";

    public static final String OVERDUE_ALERT_MESSAGE = "overdueAlertMessageValue";

    public static final String SCHEDULE_ALERT_MESSAGE = "scheduleAlertMessageValue";

    public static final String SCHEDULE_REMINDER_ALERT_MESSAGE = "scheduleReminderAlertMessageValue";

    public static final String EMAIL_CONFIGURE_ID = "emailConfigureId";

    public static final String EMAIL_STATUS = "emailStatus";

    public static final String EMAIL_SUBJECT = "emailSubject";

    public static final String TIME_HOURS = "timeHours";

    public static final String TIME_MINUTES = "timeMinutes";

    public static final String EMAIL_INTERVAL = "emailInterval";

    public static final String LAST_MAILSENT_DATE = "lastMailSentDate";

    /*
     * Code Added by RamP Bugzilla Bug 14473 FS > lm > Primary info > click on print button > on print page data is not appearing for Microsoft Outlook Mails section, Even data is not appearing for open task section and activity history section
     */
    public static final String SUBJECT_PRINT = "subjectPrint";

    /***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
     * START OF FINANCIAL TABLES *
     **********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/

    public static final String UPLOAD_DOC_NAMES = "uploadDocNames";

    public static final String ADDL_FEES = "addlFees";

    // public static final String OPEN_BAL_ADDL_FEES = "openBalAddlFees";
    public static final String ADDL_FEES_DETAILS_ID = "addlFeesDetailsId";

    /* Field Names for Table FIN_ADDL_LINE_ITEMS */
    public static final String ADDL_ITEM_ID = "addlItemID";

    public static final String PARENT_ID = "parentID";

    public static final String ADJ_TYPE = "adjType";

    /* Field Names for Table FIN_AGREEMENT_VERSIONS */
    public static final String VERSION_ID = "versionID";

    public static final String VERSION_NAME = "versionName";

    public static final String ROYALTY_FREQUENCY = "royaltyFrequency";

    public static final String ROYALTY_PERCENTAGE = "royaltyPercentage";

    public static final String ROYALTY_PERCENTAGE_AF = "royaltyPercentageAf";

    public static final String ADV_PERCENTAGE = "advPercentage";

    public static final String ADV_PERCENTAGE_AF = "advPercentageAf";

    /* Field Names for Table FIN_AREA_FRANCHISE_ROYALTY */
    public static final String INVOICE_NO = "invoiceNo";

    public static final String ROYALTY_AMOUNT = "royaltyAmount";
    public static final String ROYALTY_AMOUNT_LAST = "royaltyAmountLast";
    
    public static final String ADV_AMOUNT = "advAmount";
    public static final String ADV_AMOUNT_LAST = "advAmountLast";

    public static final String INVOICE_AMOUNT = "invoiceAmount";

    public static final String TOTAL_SALES = "totalSales";
    
    public static final String TOTAL_SALES_LOCAL = "totalSalesLocal";
    
    public static final String TOTAL_SALES_INCLUDED = "totalSalesIncluded";
    
    public static final String TOTAL_AMOUNT_RECEIVED = "totalAmountReceived";
    
    public static final String INVOICE_DATE = "invoiceDate";
    
    //Added By Veerpal Singh on 25-AUG-2011 ENH_71BBCME09
    
    public static final String INVOICE_DUE_DATE = "invoiceDueDate";
    
    public static final String TMS_TERMS = "tmsTerms";
       
    //ENH_71BBCME09 Eds

    public static final String PAYMENT_STATUS = "paymentStatus";

    public static final String REPORT_STATUS = "reportStatus";

    public static final String AMOUNT_PAID = "amountPaid";

    public static final String EFT_DATE = "eftDate";

    public static final String OPEN_BAL_ROY = "openBalRoy";

    public static final String OPEN_BAL_ADV = "openBalAdv";

    public static final String OPEN_BAL_ADDL_FEES = "openBalAddlFees";

    /* Field Names for Table FIN_AF_SHARE */
    public static final String AF_SHARE_ID = "afShareID";

    public static final String AF_SHARE_NO = "afShareNo";

    public static final String PERIOD_START_DATE = "periodStartDate";

    public static final String PERIOD_END_DATE = "periodEndDate";

    public static final String ROY_SHARE = "royShare";

    public static final String ADV_SHARE = "advShare";

    public static final String OPEN_BAL_ROY_SHARE = "openBalRoyShare";

    public static final String OPEN_BAL_ADV_SHARE = "openBalAdvShare";

    public static final String GENERATED_ON = "generatedOn";

    /* Field Names for Table FIN_AF_SHARE_PAYMENT */
    public static final String PAYMENT_DATE = "paymentDate";

    public static final String PAYMENT_RECEIVED_DATE = "paymentReceivedDate";
    /* Field Names for Table FIN_AREA_FRANCHISE_ROYALTY_COLLECTION */
    public static final String PAYMENT_ID = "paymentID";

    public static final String AMOUNT_COLLECTED = "amountCollected";

    public static final String COLLECTION_DATE = "collectionDate";

    public static final String COLLECTION_METHOD = "collectionMethod";

    public static final String CHEQUE_NO = "chequeNo";

    public static final String COLLECTION_COMMENTS = "collectionComments";

    // Added For Upload Xls Sales Report
    public static final String ADDITIONAL_FEES = "additionalFees";
    public static final String SELECTED_ADJ_ITEM = "selectedAdjItem";
    public static final String GARBAGE = "garbage";
    public static final String ADJ_CHK_BOX_NAMES = "adjChkBoxNames";
    public static final String PRI_KEY_NAME = "priKeyName";
    public static final String PRI_KEY_VALUE = "priKeyValue";
    
    public static final String WEEK_NO					= "weekno";
    
    /* Field Names for Table FIN_AREA_PAYMENT */
    public static final String AREA_PAYMENT_ID = "areaPaymentID";

    public static final String DATE_RECEIVED = "dateReceived";

    public static final String REF_NO = "refNo";

    public static final String MEMO = "memo";

    public static final String PAYMENT_NO = "paymentNo";

    public static final String SUBMIT_DATE = "submitDate";
    public static final String GOALS_SUBMIT_DATE = "goalSubmitDate";
    public static final String GOALS_TOTAL = "goalsTotal";
    public static final String GOALS_COUNT = "goalsCount";
    public static final String GOALS_TRANS = "goalsTrans";
    public static final String GOALS_USER_NO = "goalUserNo";
    public static final String GOALS_UPDATE_NO = "goalUpdateNo";
    
    public static final String OPEN_BALANCE = "openBalance";

    public static final String AF_ROYALTY_ID = "afRoyaltyId";
    
    
    /* Field Names for Table FIN_COUNTRY_EXCHANGE_RATE and used in that Actions*/
	public static final String COUNTRY_EXCHANGE_RATE_ID = "countryExchangeRateID";
	public static final String CURRENCY_DATE = "currencyDate";	
	public static final String CURRENCY_DATE_FROM = "currencyDateFrom";
	public static final String CURRENCY_DATE_TO = "currencyDateTo";
	public static final String EXCHANGE_RATE = "exchangeRate";
	public static final String USD_PER_UNIT = "UDSPerUnit";
	public static final String MODIFIED_DATE = "modifiedDate";
	public static final String WEEK_OF_YEAR = "weekOfYear";
	//public static final String PER_UNIT_COST = "perUnitCost";
	public static final String PER_UNIT = "perUnit";
	public static final String CREATION_DAY = "creationDay";    

    /* Field Names for Table FIN_CREDIT_DEBIT */
    public static final String MEMO_ID = "memoID";

    public static final String MEMO_TYPE = "memoType";

    public static final String MEMO_DATE = "memoDate";

    public static final String EXG_RATE = "exgRate";

    public static final String EXG_RATE_ID = "exgRateID";

    public static final String MEMO_NO = "memoNo";

    /* Field Names for Table FIN_CREDIT_DEBIT_HISTORY */
    public static final String CREDIT_DEBIT_HISTORY_ID = "creditDebitHistoryID";

    public static final String FRANCHISE_ROYALTY_ID = "franchiseRoyaltyID";

    public static final String AVL_AMOUNT = "avlAmount";

    public static final String AMOUNT_DEBITED = "amountDebited";

    public static final String AMOUNT_CREDITED = "amountCredited";

    public static final String TRANS_DATE = "transDate";

    /* Field Names for Table FIN_CREDIT_DEBIT_MASTER */
    public static final String CREDIT_DEBIT_ID = "creditDebitID";

    public static final String LAST_UPDATED_DATE = "lastUpdatedDate";

    public static final String MAPPED_ON = "mappedOn";

    public static final String HISTORY = "history";

    /* Field Names for Table FIN_FRANCHISE_AGREEMENT_VERSIONS */
    public static final String FRANCHISE_VERSION_ID = "franchiseVersionID";

    public static final String APPLIED_FROM = "appliedFrom";

    /* Field Names for Table FIN_FRANCHISE_ROYALTY */
    public static final String AF_SHARE_ROY = "afShareRoy";

    public static final String AF_SHARE_ADV = "afShareAdv";

    public static final String ADDL_AMOUNT = "addlAmount";

    // public static final String ADDL_FEES = "addlFess";
    public static final String INV_TYPE = "invType";

    // ABHISHEK - To update FFR table when eft process
    public static final String PROCESS_ROYALTY_DATE = "processRoyaltyDate";

    public static final String PROCESS_ADVERTISEMENT_DATE = "processAdvertisementDate";

    public static final String PROCESS_OTHERS_DATE = "processOthersDate";

    public static final String PROCESS_EXCEPTION_MODE = "processExceptionMode";

    public static final String EFT_PROCESS_DETAIL = "eftProcessDetail";

    /* Field Names for Table FIN_MISC_CONFIG */
    public static final String CONFIG_VERSION_ID = "configVersionID";

    public static final String CYCLES = "cycles";

    public static final String FILL_PAST_DUES = "fillPastDues";

    public static final String SALES_REPORT_APPROVAL = "salesReportApproval";

    public static final String ADJUSTMENT = "adjustment";

    public static final String INV_THREAD = "invThread";

    public static final String INV_CYCLE = "invCycle";

    public static final String INV_CYCLE_PARAM1 = "invCycleParam1";

    public static final String INV_CYCLE_PARAM2 = "invCycleParam2";

    public static final String EFT_THREAD = "eftThread";

    public static final String EFT_CYCLE = "eftCycle";

    public static final String EFT_CYCLE_PARAM1 = "eftCycleParam1";

    public static final String EFT_CYCLE_PARAM2 = "eftCycleParam2";

    public static final String AREA_FRANCHISEE = "areaFranchisee";

    public static final String AF_SHARE_CYCLE = "afShareCycle";

    public static final String AF_CYCLE_PARAM1 = "afCycleParam1";

    public static final String AF_CYCLE_PARAM2 = "afCycleParam2";

    public static final String CONSOLIDATE_PAYMENT = "consolidatePayment";

    public static final String UPLOAD_SALES = "uploadSales";
    public static final String UPLOAD_PNL = "uploadPNL";		//P_ENH_PNL_UPLOAD
    //public static final String CUSTOMER_DETAILS = "customerDetails";
    
    /* Field Names for Table FIN_PAYMENT_MAPPING */
    public static final String PAYMENT_MAPPING_ID = "paymentMappingID";

    public static final String AMOUNT_APPLIED = "amountApplied";

    public static final String DATE_APPLIED = "dateApplied";

    public static final String APPLIED_ON = "appliedOn";

    public static final String INVOICE_ARCHIVED = "invoiceArchived";

    public static final String PAYMENT_TYPE = "paymentType";

    /* Field Names for Table FIN_SALES_REPORT */
    public static final String REPORT_NO = "reportNo";

    public static final String REPORT_PERIOD_START = "reportPeriodStart";

    public static final String REPORT_PERIOD_END = "reportPeriodEnd";

    public static final String REPORT_SUBMIT_DATE = "reportSubmitDate";

    public static final String REPORT_TYPE = "reportType";
    public static final String OPERATOR_FIELD = "opratorSearch";
    public static final String RUNTIME_FIELD = "runTimeSearch";
    public static final String ORDER_FIELD = "orderFld";
    public static final String LBL_FIELD = "lblFld";
    public static final String PARAMETER_FIELD = "inputSearchFld";
    public static final String PARAMETER_VALUE = "inputSearchFldValue";
    public static final String SUMMARY_FIELD = "inputSummaryFldValue";
    public static final String FORMAT_FIELD = "inputFormatFldValue";
    
    /* Field Names for Table FIN_SALES_REPORT_DETAILS */
    public static final String LINE_ITEM_ID = "lineItemID";
    public static final String LINE_ITEM_VALUE = "lineItemValue";
    
    public static final String MONTH_COUNT = "monthCount";
    public static final String MONTH_TRANS = "monthTrans";
    public static final String MONTH_AMOUNT = "monthAmount";
    
    public static final String SALES_COUNT = "salesCount";
    public static final String SALES_TRANSACTION = "salesTransaction";
    public static final String SALES_COUNT_LAST = "salesCountLast";
    public static final String SALES_TRANSACTION_LAST = "salesTransactionLast";
    
    public static final String MONTH_COUNT_LAST = "monthCountLast";
    public static final String MONTH_TRANS_LAST = "monthTransLast";
    public static final String MONTH_AMOUNT_LAST = "monthAmountLast";
    
    public static final String QUANTITY = "quantity";

    /* Field Names for Table FIN_UPLOAD_HISTORY */
    public static final String UPLOAD_HISTORY_ID = "uploadHistoryID";

    public static final String UPLOAD_DATE = "fileUploadDate";
    
    public static final String PARSE_DATE = "fileparseDate";

    public static final String RECORDS_INSERTED = "recordsInserted";

    public static final String RECORDS_REJECTED = "recordsRejected";

    public static final String RECORDS_READ = "recordsRead";

    /* Other Field Names */
    public static final String AMOUNT_DUE = "amountDue";

    /* Field Names for Table NACHA_AREA_ENTRY_DETAIL_RECORD */
    public static final String NACHA_AREA_ENTRY_DETAIL_RECORD_ID = "nachaAreaEntryDetailRecordID";

    public static final String REPORT_PERIOD = "reportPeriod";

    public static final String IS_NACHA_PROCESSED = "isNachaProcessed";

    public static final String ROYALTY_TYPE = "royaltyType";
    public static final String IS_EXCEPTION = "isException"; 

    /* Field Names for Table NACHA_AREA_FRANCHISEE_ENTRIES */
    public static final String NACHA_AREA_FRANCHISEE_ENTRIES_ID = "nachaAreaFranchiseeEntriesID";

    public static final String IS_ENABLED = "isEnabled";
    
    public static final String START_DAY = "startDay";  
    
    public static final String RECORD_TYPE_CODE = "recordTypeCode";

    public static final String TRANSACTION_CODE = "transactionCode";

    public static final String RECEIVING_DFI_ID = "receivingDfiID";

    public static final String CHECK_DIGIT = "checkDigit";

    public static final String DFI_ACCOUNT_NO = "dfiAccountNo";

    public static final String INDIVIDUAL_ID_NO = "individualIDNo";

    public static final String INDIVIDUAL_NAME = "individualName";

    public static final String DISCRETIONARY_DATA = "discretionaryData";

    public static final String ADDENDA_RECORD_INDICATOR = "addendaRecordIndicator";

    /* Field Names for Table NACHA_BATCH_HEADER_RECORD */
    public static final String NACHA_BATCH_HEADER_RECORD_ID = "nachaBatchHeaderRecordID";

    public static final String SERVICE_CLASS_CODE = "serviceClassCode";

    public static final String COMPANY_DISCRETIONARY_DATA = "companyDiscretionaryData";

    public static final String COMPANY_IDENTIFICATION = "companyIdentification";

    public static final String ENTRY_CLASS_CODE = "entryClassCode";

    public static final String COMPANY_ENTRY_DESCRIPTION = "companyEntryDescription";

    public static final String DESCRIPTIVE_DATE = "descriptiveDate";

    public static final String EFFECTIVE_ENTRY_DATE = "effectiveEntryDate";

    public static final String SETTLEMENT_DATE = "settlementDate";

    public static final String ORIGINATOR_STATUS = "originatorStatus";

    public static final String ORIGINATING_BANK_ROUTING_NO = "originatingBankRoutingNo";

    public static final String BATCH_NO = "batchNo";

    /* Field Names for Table NACHA_CORPORATE_ENTRIES */
    public static final String NACHA_CORPORATE_ENTRIES_ID = "nachaCorporateEntriesID";

    public static final String TRANSACTION_CODE_ADV = "transactionCodeAdv";

    public static final String RECEIVING_DFI_ID_ADV = "receivingDfiIDAdv";

    public static final String CHECK_DIGIT_ADV = "checkDigitAdv";

    public static final String DFI_ACCOUNT_NO_ADV = "dfiAccountNoAdv";

    public static final String INDIVIDUAL_ID_NO_ADV = "individualIDNoAdv";

    public static final String INDIVIDUAL_NAME_ADV = "individualNameAdv";

    public static final String TRANSACTION_CODE_ROY = "transactionCodeRoy";
    
    public static final String RECEIVING_DFI_ID_ROY = "receivingDfiIDRoy";

    public static final String CHECK_DIGIT_ROY = "checkDigitRoy";

    public static final String DFI_ACCOUNT_NO_ROY = "dfiAccountNoRoy";

    public static final String INDIVIDUAL_ID_NO_ROY = "individualIDNoRoy";

    public static final String INDIVIDUAL_NAME_ROY = "individualNameRoy";

    public static final String TRANSACTION_CODE_ADDL = "transactionCodeAddl";

    public static final String RECEIVING_DFI_ID_ADDL = "receivingDfiIDAddl";

    public static final String CHECK_DIGIT_ADDL = "checkDigitAddl";

    public static final String DFI_ACCOUNT_NO_ADDL = "dfiAccountNoAddl";

    public static final String INDIVIDUAL_ID_NO_ADDL = "individualIDNoAddl";

    public static final String INDIVIDUAL_NAME_ADDL = "individualNameAddl";

    /* Field Names for Table NACHA_ENTRY_DETAIL_RECORD */
    public static final String NACHA_ENTRY_DETAIL_RECORD_ID = "nachaEntryDetailRecordID";

    /* Field Names for Table NACHA_FILE_HEADER_RECORD */
    public static final String NACHA_FILE_HEADER_RECORD_ID = "nachaFileHeaderRecordID";

    public static final String PRIORITY_CODE = "priorityCode";

    public static final String IMMEDIATE_DESTINATION_ROUTING_NO = "immediateDestinationRoutingNo";

    public static final String IMMEDIATE_ORIGIN_ROUTING_NO = "immediateOriginRoutingNo";

    public static final String FILE_CREATION_DATE = "fileCreationDate";

    public static final String FILE_CREATION_TIME = "fileCreationTime";

    public static final String FILE_ID_MODIFIER = "fileIDModifier";

    public static final String RECORD_SIZE = "recordSize";

    public static final String BLOCKING_FACTOR = "blockingFactor";

    public static final String FORMAT_CODE = "formatCode";

    public static final String IMMEDIATE_DESTINATION_NAME = "immediateDestinationName";

    public static final String IMMEDIATE_ORIGIN_NAME = "immediateOriginName";

    public static final String REFERENCE_CODE = "referenceCode";

    public static final String FILE_UPLOAD_DATE = "fileUploadDate";

    /* Field Names for Table NACHA_FRANCHISEE_ENTRIES */
    public static final String NACHA_FRANCHISEE_ENTRIES_ID = "nachaFranchiseeEntriesID";

    /* added by Y.Nagaraja on 28.07.2006 for sending multiple campigns */

    public static final String CAMPAIGN_TRIGGER_ID = "campaignTriggerID";
    public static final String ALL_CASE_OWNER_ID = "allCaseownerID";
    // Added by Abishek Singhal 22 Sep 2006 Start

    public static final String LEAD_VIEWED_FLAG = "leadViewedFlag";

    public static final String EMAIL_ADDRESS_ID = "emailAddressID";

    public static final String MAIL_TYPE = "mailType";

    // Added by Abishek Singhal 22 Sep 2006 End

    // ///////////Training Tab Tables//////////////////////////////

    public static final String COURSE_ID = "courseId";

    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_INFO = "courseInfo";
    public static final String CATEGORY_INFO = "categoryInfo";

    public static final String FLAG_MODIFIEDDATE = "flagModifiedDate";

    public static final String FLAG_PRIVILEGED = "flagPrivileged";

    public static final String STATUS_POS = "statusPos";

    public static final String FINISHED_DATE = "finishedDate";

    public static final String QUIZ_NO = "quizNo";

    public static final String QUIZ_SCORE = "quizScore";
    
    public static final String INSTRUCTION ="instruction";
    
    public static final String FILE_UPLOADED = "fileUploaded";
    
    public static final String SECTION_CONTENT = "sectionContent";
    

    // ////////////////////////////////////////////////////////////

    public static final String FRANCHISE_USERS = "franchiseUsers";

    public static final String FRANCHISEE_ID = "franchiseeId";

    public static final String ASSOCIATION_TYPE = "associationType";

    public static final String CM_MAILMERGE_TEMPLATE_ID = "cmMailMergeTemplateID";

    public static final String IS_NOTIFIED = "isNotified";

    // Added by Rakesh Verma 08/03/2007 Starts

    /* Field Names for Table TRAINING_EVENT */

    public static final String EVENT_NAME = "eventName";

    public static final String EVENT_DESCRIPTION = "eventDescription";

    public static final String DATE_SCHEDULED = "dateScheduled";

    public static final String EVENT_LOCATION = "eventLocation";

    public static final String PERSON_RESPONSIBLE = "personResponsible";

    // Added by Rakesh Verma 08/03/2007 Ends
    /***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
     * END OF FINANCIAL TABLES *
     **********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
    /***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
     * START OF TMS TABLES
     **********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/

    /* Field Names for Table TMS_PAYMENTS */
    
	public static String OID = "oId";

    public static final String PAYMENT_NUMBER = "paymentNumber";

    public static final String CASH_AMOUNT = "cashAmount";

    public static final String CC_AMOUNT = "ccAmount";

    public static final String CC_NUMBER = "ccNumber";

    public static final String CC_EXPIRATION = "ccExpiration";

    public static final String CC_CVC = "ccCvc";

    public static final String CHEQUE_AMOUNT = "chequeAmount";

    public static final String CHEQUE_NUMBER = "chequeNumber";

    public static final String CHEQUE_ROUTING_NUMBER = "chequeRountingNum";

    public static final String CHEQUE_DATE = "chequeDate";

    public static final String TOTAL_AMOUNT_PAID = "totalAmountPaid";

    public static final String CC_SELECT = "ccSelect";

    public static final String CASH_SELECT = "cashSelect";

    public static final String CHEQUE_SELECT = "chequeSelect";
    
    public static final String DEPOSIT_SELECT = "depositSelect";

    /* Field Names for Table TMS_TRANSACTION */

    public static final String TRANSACTION_NUMBER = "transactionNumber";

    public static final String TRANSACTION_ID = "transactionID";

    public static final String TRANSACTION_AMOUNT = "transactionAmount";

    /* following 3 variables are added by Yogesh T, for Estimates tab CUSTOMER TRANSACTION, 04 Oct 2007 */
    public static final String ESTIMATE_NUMBER = "estimateNumber";

    public static final String ESTIMATE_ID = "estimateID";

    public static final String ESTIMATE_DATE = "estimateDate";

    public static final String NO_OF_TRANSACTION = "noOfTransaction";
    
    public static final String TRANSACTION_SUBMIT_DATE = "transactionSubmitDate";

    /* following 3 variables are added by Yogesh T, for Transaction Page updation, Date 13Sep 2007 */
    public static final String COMPANY_ADDRESS = "companyAddress";

    public static final String HEADER_STATEMENT = "headerStatement";

    public static final String TERMS_AND_CONDITIONS = "termsAndConditions";
    
    public static final String UPSELL = "upsell";   // Added by Veerpal Singh on 06-MAR-2012   P_CT_ENH_UPSELL

    /* Field Names for Table TMS_TRANSACTION_DETAILS */

    public static final String TRANSACTION_DETAILS_ID = "transactionDetailsID";

    // Added by suchita Agrawal 12 Dec 2007 Start
    /* Field Names for Table FS_GROUPS */
    public static final String GROUP_ID = "groupId";

    public static final String GROUP_DESCRIPTION = "groupDescription";

    public static final String EXISTING_FRANCHISEE_ASSOCIATION = "existingFranchiseeAssociation"; //P_EnH_SMART_GROUP

    public static final String EXISTING_LEADS_ASSOCIATION = "existingLeadsAssociation"; //P_EnH_SMART_GROUP

    public static final String GROUP_OWNER = "groupOwner";

    public static final String GROUP_TYPE = "groupType";

    // Added by suchita Agrawal 12 Dec 2007 end

    /* FieldNames for Table TMS_PRODUCTS */
    public static final String TAX = "tax";

    public static final String DEFAULT_TAX = "defaultTax";

    public static final String PRODUCT_ID = "productID";

    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_AMOUNT = "productAmount";

    public static final String RATE = "rate";
    public static final String ACTUAL_PRICE = "actualPrice";

    public static final String DISCOUNT_PERCENTAGE = "discountPercentage";

    public static final String DISCOUNT_AMOUNT = "discountAmount";
 // P_CT_B_61008 added by pappu start 
    public static final String TOTAL_DISCOUNT_AMOUNT = "totalDiscountAmount";
 // P_CT_B_61008 added by pappu end 
    public static final String TAXABLE_AMOUNT = "taxableAmount";
    
    public static final String DISCOUNT_STATUS = "discountStatus";
    
    public static final String TAX_TYPE = "taxType";

    public static final String TAX_AMOUNT = "taxAmount";

    public static final String TAX_PERCENT = "taxPercent";

    public static final String TAX_PERCENTAGE = "taxPercentage";

    public static final String DISCOUNT_PERCENT = "discountPercent";

    public static final String SUB_TOTAL_AMOUNT = "subTotalAmount";

    public static final String INVOICE_NUMBER = "invoiceNumber";

    public static final String DISCOUNT = "discount";

    public static final String MODULE_DESCRIPTION = "moduleDescription";

    /* Field Names For Home Page View Table */

    public static final String RSS_ID = "rssId";

    public static final String HOME_ACTIVE_VALUE = "homeActiveValue";

    public static final String RSS_VALUE = "rssValue";

    public static final String ADDBY = "addBy";

    public static final String ADD_DATE_TIME = "addDateTime";

    public static final String RSS_DESC = "rssDesc";

    public static final String RSS_FEED_NAME = "rssFeedName";

    // new Field added by yadushri
    public static final String IS_PARSED = "isParsed";

    public static final String DASHBOARD_REPORT_ID = "dashBoardReportID";

    public static final String REPORT_NAME = "reportName";

    public static final String BROKER_AGENCY_ID = "brokerAgencyId";

    public static final String BROKER_TOTAL_LEADS = "brokerTotalLeads";

    /* add by ganesh for order details */
    public static final String ORDER_REF_ID = "orderRefID";

    public static final String BILLING_ADDRESS1 = "billingAddress1";

    public static final String BILLING_ADDRESS2 = "billingAddress2";

    // public static final String BILLING_CITY="billingCity";
    public static final String BILLING_STATE = "billingState";

    public static final String BILLING_POSTAL_CODE = "billingPostalCode";

    public static final String BILLING_COUNTRY = "billingCountry";

    // public static final String BILLING_PHONE="billingPhone";
    public static final String SHIPPING_ADDRESS1 = "shippingAddress1";

    public static final String SHIPPING_ADDRESS2 = "shippingAddress2";

    // public static final String SHIPPING_CITY="shippingCity";
    public static final String SHIPPING_STATE = "shippingState";

    public static final String SHIPPING_POSTAL_CODE = "shippingPostalCode";

    public static final String SHIPPING_COUNTRY = "shippingCountry";

    // public static final String SHIPPING_PHONE="shippingPhone";
    public static final String SUPPLIER_FIRST_NAME = "supplierFirstName";

    public static final String SUPPLIER_LAST_NAME = "supplierLastName";

    public static final String SUPPLIER_ADDRESS = "supplierAddress";

    public static final String SUPPLIER_COMPANY_NAME = "supplierCompanyName";

    public static final String SUPPLIER_CITY = "supplierCity";

    public static final String SUPPLIER_STATE = "supplierState";

    public static final String SUPPLIER_POSTAL_CODE = "supplierPostalCode";

    public static final String SUPPLIER_COUNTRY = "supplierCountry";

    public static final String SUPPLIER_DAYTIME_PHONE = "supplierDayTimePhone";

    public static final String SUPPLIER_FAX = "supplierFax";

    public static final String SUPPLIER_EMAIL = "supplierEmail";

    public static final String NUMBER_OF_ORDER = "numberOfOrder";

    public static final String ORDER_DETAIL_ID = "orderDetailID";

    public static final String COMMENT = "comment";
    public static final String COMMENTED_BY = "commentedBy";

    // Added by manoj kumar for table AM_PAYMENTS_SHIPPING_OPTIONS
    public static final String PAYMENTS_SHIPPING_ID = "paymentsShippingID";

    public static final String CARD_NUMBER = "cardNumber";

    public static final String CVC2_NUMBER = "cvc2Number";

    public static final String SHIPPING = "shipping";

    public static final String VENDOR_NAME = "vendorName";

    public static final String VENDOR_NO = "vendorNo";

    public static final String DOCUMENT_FS_ATTACHMENT = "fsDocumentAttachment";

    // document in fs,Field Names for Table FS_DOCUMENTS,end

    /***********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
     * END OF TMS TABLES
     **********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/

    public static final String EXT1 = "ext1";

    public static final String EXT2 = "ext2";

    // Added By ravindra Verma on 5/29/2007 for Configuration of call status and
    // type

    public static final String CALL_STATUS_ID = "callStatusId";

    public static final String CALL_STATUS_NAME = "callStatusName";
    
    //Sanjeev on 9 july 2008 for configure
    public static final String TRANSFER_STATUS_ID = "transferStatusId";

    public static final String TRANSFER_STATUS_NAME = "transferStatusName";

    public static final String CRITERIA_ID = "criteriaId";

    public static final String CRITERIA_NAME = "criteriaName";

    public static final String CRITERIA_VALUE = "criteriaValue";

    public static final String IS_SELECTED = "isSelected";

    // Entries of fields for Support Module starts

    public static final String TICKET_NAME = "ticketName";

    public static final String DEPARTMENT_ID = "departmentId";

    public static final String IS_LOCKED = "isLocked";

    public static final String LOCKED_BY = "lockedBy";

    public static final String IS_IMPLICIT_LOCK = "isImplicitLock";

    public static final String LOCKED_TIME = "lockedTime";

    public static final String IS_USER_STATUS = "isUserStatus";

    public static final String STATUS_ORDER = "statusOrder";

    public static final String DEPARTMENT_NAME = "departmentName";

    public static final String DEPARTMENT_ABBR = "departmentAbbr";
    
    public static final String DEPARTMENT_EMAIL = "departmentEmail";

    public static final String IS_TEMPLATE = "isTemplate";

    public static final String TIME_WORKED_UPON = "timeWorkedUpon";

    public static final String IS_RESOLUTION = "isResolution";

    public static final String NOTE_ID = "noteId";

    public static final String HISTORY_MESSAGE_ID = "historyMessageId";

    public static final String NEW_DESCRIPTION = "newDescription";

    public static final String OLD_DESCRIPTION = "oldDescription";

    public static final String OVERWRITE_BY = "overwriteBy";

    public static final String OVERWRITED_ON = "overwritedOn";

    public static final String EMPTY_STRING = "";
    public static final String QUOTE_FLD = ",";

    public static final String NULL = "null";

    public static final String REPORTED_DATE = "reportedDate";

    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    public static final String LAST_MODIFIED_ON = "lastModifiedDate";

    public static final String CREATED_BY_USER_NO = "createdByUserNo";
    public static final String CREATED_BY_FRANCHISEE_NO ="createdByFranchiseeNo";
    public static final String CONTACT_FRANCHISEE_NO ="contactFranchiseeNo";
    
    // Entries of fields for Support Module ends
    // ganesh,20-july-2007,for Ad Builder
    public static final String FORMAT = "format";

    public static final String HEIGHT = "height";

    public static final String WIDTH = "width";

    // Added By Rakesh Verma, P_Admin_E_Ftp101 Starts
    public static final String IS_CONFIGURE = "isConfigure";

    // Added By Rakesh Verma, P_Admin_E_Ftp101 Ends

    /* Field Names for Table WEBSITE_LINKS */
    public static final String LINK_USER_ID = "linkUserID";

    public static final String LINK_PASSWORD = "linkPassword";

    // Added by niraj sachan
    public static final String Store_ID = "stId";

    public static final String ST_ID = "storeTypeId";

    public static final String ST_NAME = "stName";

    public static final String ST_ORDER = "stOrder";
  //PP_changes starts
    public static final String TT_ID = "taskTypeId";

    public static final String TT_NAME = "ttName";

    public static final String TT_ORDER = "ttOrder";
  //PP_changes ends
    
    public static final String CREATED_TIME = "createdTime";

    // Added by niraj sachan ends

    /* Added by Niraj Sachan Field Names for Table FS_INFORMERCIAL */
    public static final String INFORMERCIAL_NAME = "informName";

    public static final String INFORMERCIAL_ID = "informID";

    public static final String IDENTIFIER = "informIdenty";

    public static final String UPDATED_TIME = "informUpdateTime";

    public static final String LOGIN_OWNER = "loginUser";

    public static final String PREVIOUS_OWNER = "previousOwner";

    public static final String CURRENT_OWNER = "currentOwner";

    public static final String NEXT_CALL_DATE = "nextCallDate";

    public static final String NEXT_CALL_DATE_FROM = "nextCallDateFrom";

    public static final String NEXT_CALL_DATE_TO = "nextCallDateTo";

    /* Added by Rakesh for Table User's Credit Card Details */
    public static final String CARD_ID = "cardId";

    public static final String CARD_TYPE = "cardType";

    public static final String CARD_TITLE = "cardTitle";
    
    public static final String REPORT_MONTH = "reportMonth";
    public static final String REPORT_YEAR = "reportYear";
    
    // Added By Nikhil Verma.
    public static final String FIM_REACQUIRING_ID = "fimReacquiringID";
    public static final String IS_CORPORATE = "isCorporate";

    /*Added By Pratap for TEAM_USER*/
    public static final String TEAM_ID				= "teamID";
    public static final String TEAM_NO				= "teamNo";
    public static final String TEAM_NAME				= "teamName";
    public static final String TEAM_DESC				= "teamDesc";
    public static final String USER_MAPPING_ID		= "userMappingId";
    public static final String MAP_KEY				= "mapKey";
    
	/* Added by Rakesh for SSO, Starts */
	public static final String LINK_ID="linkID";
	public static final String LINKS_ORDER="linksOrder";
	public static final String IMAGE="imageName";
	public static final String TAB_ID="tabID";
	public static final String IS_SSO_LINK="singleSignOn";
        public static final String SAML_AUTHENTICATION="samlAuthentication";
        public static final String SAML_AUTHENTICATION_TYPE="samlAuthenticationType";
	public static final String SSO_ITEM="ssoItem";
	public static final String URL_FORMAT="urlFormat";
	public static final String URL_DESCRIPTION="urlDescription";
	public static final String METHOD="method";
	public static final String IS_DEACTIVATE="isDeavticate";
	public static final String IS_TEST="isTest";
	public static final String TOKEN_ID="tokenID";
	public static final String TOKEN_NAME="tokenName";
	public static final String VALUE_ID="valueID";
	public static final String TOKEN_VALUE="tokenValue";
	
	public static final String TAB_TYPE = "tabType";//P_Admin_E_201309041629 Nishant
	
	public static final String FRANCHISEE_DISPLAY_ID = "franchiseeDisplayID";
	
	//SC_CT_Reports_E_24102008, Starts.
    public static final String  INTERVAL_DAYS = "intervalDays";
    public static final String  THROUGH_DAYS  = "throughDays";
    //SC_CT_Reports_E_24102008, End.
	
	public static final String PAGE_NAME="pageName";
	public static final String PREVIOUS_VALUE="previousValue";
	public static final String CURRENT_VALUE="currentValue";
	public static final String MAILMERGE_ID = "mailmergeId";
	public static final String OUTLOOK_ID = "outlookId";
	public static final String TERMINATION_REASON = "terminationReason";
 	public static final String HOME_PHONE_EXT = "homePhoneExt";
 	public static final String PHONE_EXT2 = "phoneExt2";
 	public static final String PHONE_EXT = "phoneExt";
 	public static final String TIMELESS_TASK = "timelessTask";

    //P_EnH_TaskReminder
    public static final String TASK_EMAIL_REMINDER = "taskEmailReminder";
    public static final String ADDITIONAL_FIELDS = "additionalFields";

        public static final String THIRD_PARTY_TITLE = "thirdPartyTitle";
        // Added By Nikhil Verma
        public static final String FIM_CB_OTHER_ENTITY_TYPE="fimCbOtherEntityType";
        public static final String FIM_CB_OTHER_COMPLAINT_TYPE="fimCbOtherComplaintType";
        public static final String FIM_CB_OTHER_CURRENT_STATUS="fimCbOtherCurrentStatus";
        public static final String CURRENT_STATUS_OTHER="currentStatusOther";
        public static final String SEND_LEGAL_OTHER_DOCS_TO="sendLegalOtherDocsTo";
        public static final String SEND_OTHER_DOCS_TO="sendOtherDocsTo";
        public static final String FIM_CB_OTHER_ROYALTY_PERIOD="fimCbOtherRoyalityPeriod";
        public static final String FIM_CB_OTHER_ADVERTISING_PERIOD="fimCbOtherAdvertisingPeriod";
        public static final String FIM_CB_OTHER_PERIOD_VALUE="fimCbOtherPeriodValue";
        
        //added by rittika
       public static final String FIM_COMPLAINT_TYPE_OTHER ="fimOtherComplaintType";
       public static final String OTHER_CONTACT_CATEGORY_DETAILS="otherContactCategoryDetails";
       public static final String FIM_TT_REASON_OTHER ="fimCbReasonOther";
       public static final String FIM_CB_BANKRUPTCY_TYPE_OTHER ="fimOtherBankruptcyType";
       public static final String LEAD_KILLED_REASON_ID   = "leadKilledReasonId";
       public static final String LEAD_KILLED_REASON_NAME = "leadKilledReasonName";
       public static final String IS_EDITABLE = "isEditable";
       public static final String TOTAL_RECORDS = "totalRecords";
       public static final String SECTION_NAME =  "sectionName";
       public static final String USER_NO_IN_SESSION = "user_no";
       public static final String TEMPLATE_STATUS = "templateStatus";
       public static final String PRIVATE_STATUS = "Private"; 
       public static final String PUBLIC_STATUS = "Public";
       public static final String RECORDS_PER_PAGE = "recordsPerPage";
       public static final String PAGE_ID = "pageid";
       public static final String SORT_KEY = "sortKey";
       public static final String SORT_COL = "sortCol";
       public static final String SORT_ORDER = "sortOrder";
       public static final String PAGGING_URL = "paggingUrl";
       public static final String MAIL_MERGE_MAP = "mailMergeMap";
       public static final String BREAD_CRUMB = "breadCrumb";
       public static final String MENU_BAR = "menuBar";
       public static final String ROOT_URL = "root_url";
       public static final String PURCHASE_ORDER_NO = "purchaseOrderNo";
       public static final String SUPPLIER_PAYMENT_ID = "supplierPaymentId";
       //added by neha
       public static final String REGION_ID = "regionId";
       public static final String AREA_FRANCHISE_ID = "areaFranchiseId";
   	public static final String AREA_FRANCHISE_TYPE = "areaFranchiseType";
	public static final String STR = "str";
	public static final String  CENTRE_LIST = "centreList";

     //ganesh for jFree chart
     public static final String BAR_CHART="barChart";
     public static final String PIE_CHART="pieChart";
	/* Added by Rakesh for SSO, Ends */	
	public static final String LEAD_RATING_ORDER = "leadRatingOrder";
	
	//Added By Manoj Kumar for call center implementation
	public static final String AGENT_NO = "agentNo";
    public static final String AGENT_ID = "agentID";
    public static final String CALL_CENTER_CONFIGURATION_ID="callCenterConfigurationID";
    public static final String VOICE_MESSAGE="voiceMessage";
    public static final String INTRODUCTORY_TEXT="introductoryText";
    public static final String CALL_LEAD_COUNT="callLeadCount";
    public static final String SECOND_CALL_HOUR="secondCallHour";
    public static final String SECOND_CALL_MINUTE="secondCallMinute";
    public static final String THIRD_CALL_HOUR="thirdCallHour";
    public static final String THIRD_CALL_MINUTE="thirdCallMinute";
    public static final String AGENT_LEAVE_MESSAGE="agentLeaveMessage";
    public static final String AGENT_LEAVE_MESSAGE_CALL_NUMBER="agentLeaveMessageCallNumber";
    public static final String APPOINTMENT_SELL_PERSON="appointmentSellPerson";
    public static final String LEAD_CC_QUEUE="leadCcQueue";
    public static final String LEAD_CC_QUEUE_CALLS="leadCcQueueCalls";
    public static final String QUESTION_CONFIGURATION_ID="questionConfigurationID";
    public static final String QUESTION_ORDER_NO="questionOrderNo";
    public static final String QUESTION_NAME="questionName";
    public static final String IS_QUALIFICATION="isQualification";
    public static final String IS_MANDATORY="isMandatory";
    public static final String IS_QUALIFIED="isQualified";
    public static final String IS_REMOVED="isRemoved";
    public static final String LEAD_QUESTIONS_DETAIL_ID="leadQuestionDetailID";
    public static final String MAIL_FIELDS="mailFields";
    public static final String COUNTRY_ID_FOR_UPDATE = "countryIDForUpdate"; 
    public static final String CURRENCY_ID = "currencyID";
    public static final String XE_ENABLE = "xeEnable";
    public static final String DATA_MAP = "dataMap";
    public static final String ACTION_MENU_STRING = "actionMenuString";
    public static final String SEARCH_INFO = "searchInfo";
    public static final String PAGE_URL = "pageUrl";
    public static final String SORT_URL = "sortUrl";
    public static final String RECORD_PER_PAGE = "recordPerPage";
    public static final String DATASET_CURRENCY_FUNCTION = "datasetCurrencyFunction";
    public static final String PAGE_SIZE = "pageSize";
    public static final String JS_CALL_STRING = "jsCallString";
    public static final String CER_ID = "cerId";
    public static final String CURRENCY_NAME = "currencyName";
    public static final String CURRENCY_CODE = "currencyCode";
    
    
    public static final String FRANCHISEE_S1 = "franchiseeS1";
    public static final String FRANCHISEE_S2 = "franchiseeS2";
    public static final String FRANCHISEE_S3 = "franchiseeS3";
    public static final String FRANCHISEE_S4 = "franchiseeS4";
    public static final String FRANCHISEE_S5 = "franchiseeS5";
    public static final String FRANCHISEE_S6 = "franchiseeS6";
    public static final String FRANCHISEE_S7 = "franchiseeS7";
    public static final String FRANCHISEE_S8 = "franchiseeS8";
    public static final String FRANCHISEE_S9 = "franchiseeS9";
    public static final String FRANCHISEE_S10 = "franchiseeS10";
    // Added By Nikhil Verma P_ADMIN_E_1111
    public static final String IS_DOMESTIC="isDomestic";
    public static final String FIM_CAPTURE_MAIL_ID="fimCaptuureMailID";
    
    public static final String DATA_INFO = "dataInfo";
    public static final String CASE_MODIFY = "caseModify";
    
    
    
      /**Added by Saurabh Sinha for the fieldname in compose having compose user detail*/
    public static final String USERNAME_EMAIL = "usernameEmail";
    
    
    
    // Entries for table BLOGS
    
    public static final String ID = "ID";
    public static final String BLOG_URL_NAME = "blogUrlName";
    public static final String BLOG_FULL_NAME = "blogFullName";
    public static final String BLOG_TAGLINE = "blogTagline";
    public static final String THEME = "theme";
    public static final String CREATE_TIME = "createTime";
    public static final String KEYWORDS = "keywords";
    public static final String FOUNDER_ID = "founderID";
    public static final String COMMUNITY_TYPE = "communityType";
    public static final String COMMUNITY_ID = "communityId";
    public static final String COMMUNITY_IMAGE_ID = "communityImageId";
    public static final String IMAGE_FILE = "imagefile";
    public static final String IS_FRANCHISE_BLOG = "isFranchiseBlog";
    public static final String VIEWED_N_COMMENTED_BY = "viewNCommentedBy";
    
    // Entries for table BLOG ENTRIES
    
    public static final String POST_ID = "postID";
    public static final String BLOG_ID = "blogID";
    public static final String POST_URL = "postUrl";
    public static final String ALIASNAME = "aliasName";
    public static final String AUTHOR_ID = "authorID";
    public static final String BODY = "body";
    public static final String COMMENTING_ALLOWED = "commentingAllowed";
    public static final String EXCERPT = "excerpt";
    
    public static final String SINGLE_MENU = "singleMenu";
    public static final String ACTIONS = "actions";
    public static final String ROW_CLASS = "rowClass";
    
    public static final String CURRENCY_RATE = "currencyRate";
        
    // For FranOnlie..
    public static final String USER_FRIEND_ID = "userFriendNo";
    public static final String FRIEND_NO 		= "friendNo";
    public static final String AGE 			= "age";
    public static final String LANGUAGE		= "language";
    public static final String ABOUT_ME		= "aboutMe";
    public static final String OFFICE_PHONE		= "officePhone";
    
    public static final String CAMP_SENDER_NAME = "campSenderName";
    public static final String FIM_MU_ENTITY_ID = "fimMuEntityID";
    public static final String MU_ID = "muID";
    public static final String FIM_MU_TAB_NAME = "fimMuTabName";
    public static final String FIM_MU_TAB_PRIMARY_ID = "fimMuTabPrimaryID";
    public static final String FIM_MU_ORDER_NO="fimMuOrderNo";
    public static final String FIM_MU_FIELD_PREFIX="fimMuFieldPrefix";
    public static final String FIM_MU_INFO_ID="fimMuInfoID";
    
    // For MU_DETAILS Table Entry By Ram Avtar
    public static final String MU_NAME = "muName";
    public static final String MANAGING_DIRECTOR = "managingDirector";
    public static final String USER_CONTACT_ID="userContactID";
    public static final String CONTACT_PHONE = "contactPhone";
    public static final String CONTACT_EMAIL = "contactEmail";
    public static final String MU_DETAILS_ID="muDetailsID";

  //For CM Summary page Customization by Manoj
      public static final String DISPLAY_ID = "displayId";
      public static final String DISPLAY_VALUE = "displayValue";
    
    //public static String COMMENT_TEXT = "commentText";
    //public static String COMMENT_BY = "commentBy";
    //public static String COMMENT_TO = "commentTo";
    
    //Added By SAURABH SINHA for fran online profile starts
    public static final String RELATIONSHIP_STATUS = "relationshipStatus";
    public static final String CELL_PHONE = "cellPhone";
    public static final String LANGUAGES_KNOWN = "langsKnown";
    public static final String PROF_USER_ID = "profUserId";
    public static final String EDUCATION = "education";
    public static final String HIGH_SCHOOL = "highSchool";
    public static final String COLLEGE = "college";
    public static final String MAJOR = "major";
    public static final String DEGREE = "degree";
    public static final String OCCUPATION = "occupation";
    public static final String COMPANY = "company";
    public static final String COMPANY_WEBSITE = "companyWebsite";
    public static final String JOB_DESCRIPTION = "jobDesc";
    public static final String WORK_EMAIL = "workEmail";
    public static final String CAREER_SKILLS = "careerSkills";
    public static final String CAREER_INTEREST = "careerInterest";
    public static final String USER_PROFILE_NO = "userProfileNo";
    //Added By Birendra For FranBuzz Start
    public static final String PROF_EXP_GOAL = "profExpGoal";    
    public static final String GROUP_ASSOCIATE = "groupAssociate";
    public static final String HONOUR_AWARDS = "honourAwards";   
    public static final String BLOG = "blog";
  //Added By Birendra For FranBuzz End
    
    //Added By SAURABH SINHA for fran online profile ends
  //FIN SALES PERSON
    public static final String SP_ID = "spId";
    public static final String FRAN_NAME_WITH_CENTER = "franNameWithCenter";
    public static final String DATASET_TITLE_FUNCTION = "keyTitleFunction";
    public static final String DATASET_FRANCHISEE_FUNCTION = "keyFranchiseeFunction";
    public static final String SERIAL_NO = "serialNo";
    public static final String SALES_PERSON_MAP = "salesPersonMap";
    public static final String DATASET_SP_FUNCTION = "keySPFunction";
    public static final String BY_REPORT_MONTH = "byReportMonth";
    public static final String ADD_SALES_PERSON_INFO = "addSalesPersonInfo";
    public static final String HEADER_MESSAGE = "headerMessage";
    public static final String TYPE_SALES_PERSON = "typeSalesPerson";
    public static final String NO_SEARCHING = "noSearching";
    public static final String REPORT_MONTH_FROM = "reportMonthFrom";
    public static final String REPORT_MONTH_TO = "reportMonthTo"; 
    public static final String SPS_ID = "spsId";
    public static final String REPORT_MONTH_DISPLAY = "reportMonthDisplay";
    public static final String SALES_PERSON_SALES_MAP = "salesPersonSalesMap";
    public static final String ADD_SP_SALES_INFO = "addSalesPersonSalesInfo";
	public static final String LATEST = "latest";
    public static final String CURRENT_STEP = "currentStep";
   // public static final String TITLE_NAME = "TITLE_NAME";
    public static final String AM_DOCUMENT_LABEL_ID="amDocumentLabelID";
    public static final String LABEL_TYPE="labelType";
    public static final String AREA_DEVELOPER_MAPPING_ID="areaDeveloperMappingID";
    public static final String LABEL_DATA="labelData";
    public static final String VALUE_TYPE="valueType";
    public static final String MANDATORY="mandatory";
    public static final String VALIDATION_TYPE="validationType";
    public static final String MAX_LENGTH="maxLength";
    public static final String COMBO_VALUE="comboValue";
    /**/
    //public static final String TERMS_AND_CONDITIONS= "termsAndConditions";// ADDED BY AKHILESH
    //public static final String TERMS_ID= "termsID";// ADDED BY AKHILESH
    public static final String COLOR_ID= "colorID";// ADDED BY AKHILESH   
    public static final String COLOR_NAME= "colorName";// ADDED BY AKHILESH  
    public static final String HTML_COLOR_CODE= "htmlColorCode";// ADDED BY AKHILESH  
    public static final String COLOR_CODE_ID= "colorCodeId";// ADDED BY AKHILESH
    public static final String DEFAULT_COLOR_CODE= "defaultColorCode";// ADDED BY AKHILESH
    //public static final String TERMS_CONDITIONS_E= "termsConditionsE";// ADDED BY AKHILESH
    
    //ADDED BY PRATAP FOR SCHEDULE_VIEW_FLAG
 	//public static final String ID	= "id";		
 	public static final String VIEW_FLAG = "viewFlag";
 	public static final String PRINT_FLAG = "printFlag";
 	public static final String BLOCK_FLAG = "blockFlag";
  //ADDED BY Akhilesh FOR SCHEDULE_VIEW
    public static final String SCHEDULER_VIEW_ID	  = "schedulerViewID";	
    public static final String MAIN_VIEW	          = "mainView";
    public static final String VERTICAL_VIEW_15MIN   = "verticalView15Min";
    public static final String VERTICAL_VIEW_30MIN	  = "verticalView30Min";
    public static final String TASK_VIEW	          = "taskView";
    //ADDED BY BANTI FOR SCHEDULE VIEW NEW IN ADMIN
    public static final String HORIZONTAL_VIEW_15_MIN   = "horizontalView15Min";
    public static final String HORIZONTAL_VIEW_30_MIN   = "horizontalView30Min";
    public static final String HORIZONTAL_VIEW_60_MIN   = "horizontalView60Min";
    public static final String TASK_VIEW_DAY   = "taskViewDay";
    public static final String TASK_VIEW_WEEK   = "taskViewWeek";
    public static final String TASK_VIEW_MONTH   = "taskViewMonth";
    public static final String HORIZONTAL_VIEW_15_MIN_NAME   = "Horizontal View (15Min)";
    public static final String HORIZONTAL_VIEW_30_MIN_NAME   = "Horizontal View (30Min)";
    public static final String HORIZONTAL_VIEW_60_MIN_NAME   = "Horizontal View (60Min)";
    public static final String TASK_VIEW_DAY_NAME   = "Task View (Day)";
    public static final String TASK_VIEW_WEEK_NAME   = "Task View (Week)";
    public static final String TASK_VIEW_MONTH_NAME   = "Task View (Month)";
    
    
    public static final String TO_EMAIL	          = "toEmail";
    public static final String CC_EMAIL	          = "ccEmail";
    public static final String BCC_EMAIL	          = "bccEmail";
    public static final String DEFAULT_EMAIL	  = "defaultEmail";
    public static final String DEFAULT_EMAIL_DATA	  = "defaultEmailData";
    public static final String DO_NOT_SEND_EMAIL	  = "doNotSendEmail";
    public static final String REGIONAL_USER_FLAG = "regionalUserFlag";
    
     //Added by Anuradha Tiwari 
    
    public static final String MEMBER_IMAGE ="memberImage";
    public static final String MEMBER_NAME ="memberName";
    public static final String MEMBER_PHONE_NO ="memberPhoneNo";
    public static final String MEMBER_EMAIL_ID ="memberEmailId"; 
    public static final String MEMBER_ID ="memberId"; 
    public static final String MEMBER_PUBLISH_ON_PORTAL ="memberPublishonPortal"; 
    public static final String MEMBER_IS_DELETED ="memberDeleted"; 
    
    
    
    public static final String  TASK_SUBJECT  = "taskSubject";
    
    /* Application Fiels Names */
	public static final String QUANTITY_PER_UNIT                 = "quantityPerUnit";
	public static final String FRANCHISEE_ADDRESS = "franchiseeAddress";
	public static final String RADIO_BUTTON                 = "radiobutton";
	public static final String PO_NUMBER = "poNumber"; 
	public static final String PARENT_DEPOSIT_ID = "parentDepositID"; 
	public static final String HOLIDAY_ID = "holidayID";
	public static final String HOLIDAY_TYPE = "holidayType";
	public static final String PO_DATE = "poDate";
	
	public static final String IS_TAX ="isTax";
	public static final String CENTRAL_STATE_TAX		= "centralStateTax";
	public static final String IS_CFORM		= "isCForm";
	public static final String SHIPPING_DELEVERY_AMOUNT		= "shippingDeleveryAmount";
	public static final String IS_APPOINTMENT = "isAppointment";
	public static final String IS_SOLD = "isSold";
	public static final String TOTAL_DEPOSITED_AMOUNT_PAID="totalDepositedAmountPaid";
	public static final String DEPOSIT_AMOUNT="depositAmount";
	
	/* Field Names for Table TMS_PAYMENTS */

	public static final String DEPOSIT_ID = "depositID";
	public static final String DEPOSIT_NUMBER = "depositNumber";
	public static final String AGAINST_ID = "againstID";
	public static final String AGAINST_TYPE = "againstType";
	public static final String C_DEPOSIT_AMOUNT="cDepositAmount";
	public static final String I_DEPOSIT_AMOUNT="iDepositAmount";
	
	public static final String ET_FLAG		= "etFlag";
	public static final String REFERENCE_NUMBER = "referenceNumber";
	
	/* Field Names for Table TMS_DEPOSIT_APPLICATION */

	public static final String APPL_DETAILS_ID = "appDepositID";
    public static final String ORIGINAL_AMOUNT = "originalAmount";
    public static final String APPL_DATE = "applDate";
    
    
    public static final String  DEFAULT_USER_ID  = "defaultUserId"; //SCH_Configure_DefaultUser_18112008.
    public static final String  BREED  = "breed";
    public static final String  SEX  = "sex";
    public static final String  WEIGHT  = "weight";
    public static final String PET_SERVICE_MAP = "petServiceMap";
 // Added By Nikhil Verma for Resource management
    public static final String RESOURCE_MANAGEMENT_ID = "resourceManagementID";
    public static final String SUN_FLAG = "sunFlag";
    public static final String MON_FLAG = "monFlag";
    public static final String TUES_FLAG= "tuesFlag";
    public static final String WED_FLAG = "wedFlag";
    public static final String THU_FLAG = "thuFlag";
    public static final String FRI_FLAG = "friFlag";
    public static final String SAT_FLAG = "satFlag";
    public static final String SUN_START_TIME = "sunStartTime";
    public static final String MON_START_TIME = "monStartTime";
    public static final String TUES_START_TIME = "tuesStartTime";
    public static final String WED_START_TIME = "wedStartTime";
    public static final String THU_START_TIME = "thuStartTime";
    public static final String FRI_START_TIME = "friStartTime";
    public static final String SAT_START_TIME = "satStartTime";
    public static final String SUN_END_TIME = "sunEndTime";
    public static final String MON_END_TIME = "monEndTime";
    public static final String TUES_END_TIME = "tuesEndTime";
    public static final String WED_END_TIME = "wedEndTime";
    public static final String THU_END_TIME = "thuEndTime";
    public static final String FRI_END_TIME = "friEndTime";
    public static final String SAT_END_TIME = "satEndTime";
    public static final String ACTION_MENU_BUFFER = "actionMenuBuffer";
    public static final String WORKING_HOURS = "workingHours";
    public static final String IS_VERIFIED = "isVerified";
    public static final String IS_SERVICE_PRESENT = "isServicePresent";
    public static final String ASSIGN_BY_USER = "assignByUser";
    public static final String VALID_ADDERSS_MESSAGE = "validAddressMessage";
     
    /* Field Names Added By Ashish Singh on 25 Oct,2007 */
	public static final String TASK_TYPE = "taskType";
	public static final String TASK_TYPE_NAME = "taskTypeName";
	public static final String TASK_TYPE_ID = "taskTypeID";
        public static final String JOB_CATEGORY = "jobCatName";
    
	
    /**/
    
    public static final String PDF_NAME = "pdfName";

    public static final String JAN="January";
    public static final String FEB="February";
    public static final String MAR="March";
    public static final String APR="April";
    public static final String MAY1="May";   
    public static final String JUN="June";
    public static final String JUL="July";
    public static final String AUG="August";
    public static final String SEP="September";
    public static final String OCT="October";
    public static final String NOV="November";
    public static final String DEC="December";

    
    public static final String DEAL_TYPE_LOCATION = "dealTypeLocation";
    public static final String LEASE_RATE = "leaseRate";
    public static final String LAND_SIZE = "landsize";
    public static final String LAND_COST = "landCost";
    public static final String ZONED_AUTO = "zonedAuto";
    public static final String PROPERTY_TAXES = "propertyTaxes";
    
    //Sales log
    public static final String FIN_SALES_TASK_ID = "finSalesTaskId";
    public static final String FIN_SALES_USER_ID = "userId";
    //public static final String TASK_TYPE = "taskType";
    // heat parameter Field Names added by Dheerendra 
    
    public static final String LEAD_QUAL_PARAM_ID = "leqdQualParamId";
    public static final String PARAMETER_NAME = "parameterName";
    public static final String RANGE_VALUE = "rangeValue";              //P_ENH_RANGE
    public static final String IS_APPLICABLE = "isApplicable";
    public static final String PARAM_NAME = "paramName";
    //ENH_CT_FIN_INTEGRATION STARTS
    public static final String CT_FIN_INTEGRATION = "ctFinIntegration";
    public static final String SALES_REPORT_BASIS = "sRBasis";
	
    public static final String GEN_SALES = "genSales";
    public static final String MASTER_DATA_ID_1 = "masterDataId";
    public static final String QUANTITY_DISPLAY = "quantityDisplay";
    public static final String AMOUNT_DISPLAY = "amountDisplay";
  //ENH_CT_FIN_INTEGRATION ENDS
    
    
    //Changes for custom YTD
    public static final String CUSTOM_YTD_MONTH = "customYtdMonth";
    //Changes for custom YTD
    
    
    public static final String ALLOW_NEGATIVE_VALUE="allowNegativeValue";
    public static final String QFN_CHECKLIST_ID = "qfnCheckListId";
    public static final String QFN_CHECKLIST_NAME = "qfnCheckListName";
    public static final String QFN_CHECKLIST_ORDER = "qfnCheckListOrder";
    public static final String QFN_CHK_ACK_ID = "qfnChkAckId";
    public static final String QFN_CHK_ACK_CHECKLIST_ID = "qfnChkAckCheckListId";
    public static final String QFN_CHK_ACK_LEAD_QUALIFICATION_DETAIL_ID = "qfnChkAckLeadQualificationId";
    public static final String QFN_CHK_ACK_VALUE = "qfnChkAckValue";
    public static final String QFN_CHK_ACK_COMPLETED = "qfnChkAckCompleted";
    public static final String QFN_CHK_ACK_COMPLETION_DATE = "qfnChkAckCompletionDate";
    public static final String QFN_CHK_ACK_DOC_NAME = "qfnChkAckDocName";
    public static final String QFN_CHK_ASSOCIATE_DOCUMENT = "qfnChkAssociateDoc";
    public static final String QFN_CHK_ACK_DOC_TYPE = "qfnChkAckDocType";
    public static final String QFN_CHK_ACK_VERIFIED_BY = "qfnChkAckVerifiedBy";
    public static final String QFN_CHK_ACK_DATE = "qfnChkAckDate";
    //public static final String LINK_PUBLISH = "linkPublish";
    public static final String COMMENT_TEXT = "commentText";
    public static final String COMMENT_BY = "commentBy";
    public static final String COMMENT_TO = "commentTo";
    public static final String HEAT_ELEMENT_SEQUENCE_ID="heatElementSequenceID";
    public static final String HEAT_ELEMENT_NAME="heatElementName";
    public static final String HEAT_ELEMENT_TYPE="heatElementType";
    public static final String HEAT_ELEMENT_ORDER="heatElementOrder";
    public static final String HEAT_ELEMENT_ID="heatElementID";
    public static final String IS_PHONE_ENABLE="isPhoneEnable";
    public static final String PHONE_MAX="phoneMax";
    public static final String PHONE_CONTACT_MADE="phoneContactMade";
    public static final String PHONE_CALL_RECEIVE="phoneCallReceive";
    public static final String IS_EMAIL_ENABLE="isEmailEnable";
    public static final String EMAIL_MAX="emailMax";
    public static final String EMAIL_OPEN="emailOpen";
    public static final String EMAIL_LINK_CLICKED="emailLinkClicked";
    public static final String IS_BROCHURE_ENABLE="isBrochureEnable";
    public static final String BROCHURE_MAX="brochureMax";
    public static final String BROCHURE_VISITED="brochureVisited";
    public static final String IS_DETAILED_QUALIFICATION_ENABLE="isDetailedQualificationEnable";
    public static final String SCORE_FOR_COMPLETION="scoreForCompletion";
    public static final String CREDIT_SCORE="creditScore";
    public static final String IS_BACKGROUND_CHECK_APPROVAL="isBackgroundCheckApproval";
    public static final String BACKGROUND_CHECK_APPROVAL_SCORE="backgroundCheckApprovalScore";
    public static final String IS_CREDIT_CHECK_APPROVAL="isCreditCheckApproval";
    public static final String CREDIT_CHECK_APPROVAL_SCORE="creditCheckApprovalScore";
    public static final String IS_DISCOVERY_DAY_VISIT="isDiscoveryDayVisit";
    public static final String DISCOVERY_DAY_VISIT_SCORE="discoveryDayVisitScore";
    public static final String IS_FDD_RECEIPT="isFddReceipt";
    public static final String FDD_RECEIPT_SCORE="fddReceiptScore";
    public static final String IS_FINANCING_APPROVED="isFinancingApproved";
    public static final String FINANCING_APPROVED_SCORE="financingApprovedScore";
    public static final String IS_AGREEMENT_SIGNED="isAgreementSigned";
    public static final String AGREEMENT_SIGNED_SCORE="agreementSignedScore";
    public static final String LIQUIDITY_SCORES_ID="liquidityScoresID";
    public static final String INITIAL_VALUE="initialValue";
    public static final String END_VALUE="endValue";
    public static final String NET_WORTH_SCORES_ID="netWorthScoresID";
    public static final String INVESTMENT_TIME_FRAME_SCORES_ID="investmentTimeFrameScoresID";
    public static final String TOTAL_HEAT_MAX="totalHeatMax";
    public static final String BACKGROUND_CHECK = "backgroundCheck";
    public static final String TABLE_FOR_RANGE = "tableForRange";
    public static final String RANGE_TYPE = "rangeType";
    public static final String SECTION_ID 			=		"sectionID";
    public static final String SECTION_ORDER 		=		"sectionOrder";
    public static final String QUIZ_NAME 			=		"quizName";
    public static final String QUIZ_SUMMARY 		=		"quizSummary";
    public static final String QUIZ_ID 				=		"quizID";    
    public static final String TIME_ALLOTED_MIN 	=		"timeAllotedMin";
    public static final String TIME_ALLOTED_HOUR 	=		"timeAllotedHour";
    public static final String PASSING_MARKS 		=		"passingMarks";
    public static final String QUIZ_TYPE	 		=		"quizType";
    public static final String QUESTION_FORMAT 		=		"questionFormat";
    public static final String QUESTION_MARKS	 	=		"questionMarks";
    public static final String IS_PUBLISHED	 		=		"isPublished";
    public static final String QUESTION_ORDER	 	=		"questionOrder";
    public static final String SUB_SECTION_ID	 	=		"subSectionID";
    public static final String SUB_SECTION_NAME	 	=		"subSectionName";
    public static final String HEAT_INDEX_SCORE	 	=		"heatIndexScore";
    public static final String HEAT_INDEX_RANGE	 	=		"heatIndexRange";
    
    public static final String SET_TEMPLATE	 	=		"setTemplate";//P_GUI_LG_9 added by divya
    
    public static final String BCK_CHK_APPROVAL = "bckChkApproval";

    public static final String SEQUENCE_NO = "sequenceNo";

    /* Field Names for Table TRAINING_SUB_SECTIONS added by Ram Avtar on 8 Apr 2009 */
    
    public static final String SUB_SECTION_CONTENT	 	=		"subSectionContent";
    
    public static final String SUPPLIER_ATTENTION_NAME = "supplierAttentionName";
    
    public static final String SELECTED_FOR_CRITERIA1 = "selectedForCriteria1";
    public static final String SELECTED_FOR_CRITERIA2 = "selectedForCriteria2";
    
    
    public static final String FS_CUSTOM_FIELD = "fsCustomField";
  
    public static final String DISCLAIMER_TEXT = "disclaimerText";
    public static final String DISCLAIMER_ID = "disclaimerID";
    //P_ADMIN_FDD_E16082010 start
    public static final String CHANGE_BY = "changeBY";
    public static final String TEMPLATE_TEXT = "templateText";
    public static final String OLD_TEMPLATE_TEXT="oldTemplateText";
    public static final String NEW_TEMPLATE_TEXT="newTemplateText";
    public static final String TEMPLATE_DELETED_FLAG = "templateDeletedFlag";
    //P_ADMIN_FDD_E16082010 end
   // public static final String CHANGE_DATE = "changeDate";
    
    //Added by Santosh for web page disclaimer form Starts
    public static final String WEBFORM_DISCLAIMER_ID = "webFormDisclaimerID";
    public static final String WEBFORM_DISCLAIMER_RADIO = "webFormDisclaimerRadio";
    public static final String WEBFORM_CAPTCHA = "webFormCaptcha";
    public static final String ACCEPT_TERMS = "acceptTerms";
    public static final String E_SIGNATURE = "eSignature";
    //Added by Santosh for web page disclaimer Ends
    
    public static final String HOME_ZIPCODE = "homeZipCode";
    public static final String OTHER_ADDRESS = "otherAddress";
    public static final String OTHER_CITY = "otherCity";
    public static final String OTHER_ZIPCODE = "otherZipCode";
    public static final String DISPLAY_FIELD_NAME="displayFieldName";
    
    /*Start Table : TMS_PAYMENT_GATEWAY*/
    public static final String PG_ID = "pgID";
    public static final String IS_ACTIVE = "isActive";
    public static final String UPDATED_ON = "updatedOn";
    /*End Table : TMS_PAYMENT_GATEWAY*/
    
    public static String GUEST_ID			= "guestId";
    public static String GUEST_FIRST_NAME	= "guestFirstName";
    public static String GUEST_LAST_NAME	= "guestLastName";
    public static String MODULE_DISPLAY = "moduleDisplay";
        
  //For Table THEATERS
    public static String THEATER_ID = "theaterID";
    public static String THEATER_NAME = "theaterName";
    public static String VEHICLE_COMPANY_NAME = "vehicleCompanyName";
    public static String VEHICLE_CONDITION = "vehicleCondition";
    public static String GUEST_CAPACITY = "guestCapacity";
    public static String VEHICLE_TYPE = "vehicleType";
    public static String THEATER_NO = "theaterNo";
    
    //For Table THEATER_ACTIVITY
    public static String  THEATER_ACTIVITY_ID = "theaterActivityID";
    public static String  ACTIVITY_SUBMIT_TIME = "activitySubmitTime";
    public static String  THEATER_DRIVER_ID = "theaterDriverID";
    public static String  THEATER_DRIVER_NAME = "theaterDriverName";
    public static String  DISTANCE_COVERED = "distanceCovered";
    public static String  START_READING = "startReading";
    public static String  END_READING = "endReading";
    public static String  TOTAL_COST= "totalCost";
    public static String  FUEL_CONSUMED = "fuelConsumed";
    public static String  FUEL_COST = "fuelCost";
    public static String  PARKING_COST = "parkingCost";
    public static String  TRAFFIC_TICKET_COST = "trafficTicketCost";
    public static String  OTHER_COST = "otherCost";
    
    public static String FORM_INFO = "formInfo";
    public static String THEATER_ACTIVITY_IDS = "theaterActivityIDs";
    public static String THEATER_SCH_VIEW = "theaterSchView";
    public static String THEATER_SCH_HTML = "thaeterSchHtml";
    public static String ACTIVITY_DATE = "activityDate";
    public static String ACTIVITY_TIME = "activityTime";
    public static String THEATER_SCH_FREQUENCY = "theaterSchFrequency";
    public static String THEATER_SCH_DATE = "theaterSchDate";
    
    public static String SCHEDULER_VIEW_MODE = "schedulerViewMode";
    public static String DISPATCH_SCHEDULER_VIEW_MODE = "dispatchSchedulerViewMode";
    public static String FROM_PREVIOUS_ADDRESS = "fromPreviousAddress";

    public static String FROM_CONTACT_JSP = "fromContactJsp";
    public static String FROM_CONTACT_FIRST_DATE_CHOICE = "fromContactFirstDateChoice";
    public static String FROM_CONTACT_SECOND_DATE_CHOICE = "fromContactSecondDateChoice";
    public static String FROM_CONTACT_FIRST_TIME_CHOICE = "fromContactFirstTimeChoice";
    public static String FROM_CONTACT_SECOND_TIME_CHOICE = "fromContactSecondTimeChoice";
    
 	 
    //public static  String QUANTITY_DISPLAY="quantity_display";
    //public static  String AMOUNT_DISPLAY="amount_display";
	 public static  String DATASET_MOBILE_THEATER = "datasetMobileTheater";
	 public static String TASK_ORIGIN = "taskOrigin";
	 public static String SEARCH_USER_LEVEL = "searchUserLevel";
	 public static String CONTACT_FULL_NAME = "contactFullName";
	 public static String CONTACT_OWNER_NAME = "contactOwnerName";
	 public static String TASK_NUMBER_NEW = "taskNumberNew";
	 public static String DISPATCH_TASK_NEW = "dispatchTaskNew";
	 public static String CASE_FOLLOWUP_TO_EVENT = "caseFollowingToEvent";
	 public static String FOLLOWUP_ID_FOR_EVENT = "followupIDForEvent";
	 public static String USER_LIST = "userList";
	 public static String TIME_COUNT = "timeCount";
	 public static String TIME_ZONE_USER_NO = "timeZoneUserNo";	 
	 public static String NAVIGATION_CODE = "navigationCode";
	 public static String MILES = "miles";
	 public static String SELECT = "select";
	 public static String CONTINUE_TYPE = "continueType";
	 public static String SESSION_STAR = "sessionStar";
	 public static String NAVIGATION_CODE_VAL = "navigationCodeVal";
	 public static String FOLLOWUP_TASK_ID = "followupTaskID";
	 public static String TEMP_VALUE = "tempValue";
	 public static String IS_TRANSFER_CONTACT = "isTransferContact";
	 public static String CONTACT_ID_FOR_TRANSFER = "contactIDForTransfer";
	 public static String FRANCHISEE_NO_TO_TRANSFER = "franchiseeNoToTransfer";
	 public static String CONTACT_TOP_MESSAGE = "contactTopMessage";
	 public static String TEMP_ZIP_CODE = "tempZipCode";
	 public static String TEMP_MILES = "tempMiles";
	 public static String MILE_DISTANCE = "mileDistance";
	 public static String ASSIGNED_TO_USER_ID = "assignedToUserID";
	 public static String ZIP_CODE_MESSAGE = "zipCodeMessage";
	 public static String NO_RIGHT_LINK = "noRightLink";
	 public static String DISPATCH_TAB_CASE = "dispatchTabCase";
	 public static String CONTACT_ADD_DATE_FORMATTED = "contactAddDateFormatted";
	 public static String YES = "yes";
	 public static final String NO = "no";
	 public static String IGNORE_ME = "ignoreMe";
	 public static String OTHER_FRANCHISEE_CASE = "otherFranchiseeCase";
	 public static String IS_FRANCHISEE_OWNER = "isFranchiseeOwner"; 
	 public static String DISTANCE = "distance";
	 public static String SHOW_SEARCH_RESULTS = "showSearchResults";
	 public static String DISPATCH_CONTACT_NAME = "dispatchContactName";
	 public static String OPT_GROUP_LABEL = "optGroupLabel";	 
	 public static String IS_CONTACT_UNREAD = "isContactUnread";
    
    /* Field Names for Table FRANCHISE_GRANTING_ACTIVITY added by Abhishek Rastogi on 21 Oct 2009 */
    public static String ACTIVITY_ID = "activityID";
    public static String ORDER_SEQUENCE = "orderSequence";
    public static String ACTIVITY_NAME = "activityName";
    public static String ACTIVITY_LINK = "activityLink";
    public static String EMAIL_FIELDS = "emailFields";
    //Added By Vikram Raj P_FS_Enh_12Apr10
    public static String ACTIVATED_DATE = "activatedDate";
    public static String ACTIVATED_FOR = "activatedFor";
    
    //P_FS_EMAIL_REPETITION Starts
    public static String TRY_DATE="tryDate";
    //P_FS_EMAIL_REPETITION Ends
    //User language variable start
    public static String USER_LANGUAGE="userLanguage";
    //User language variable Ends
    
    //Candidate Portal Integration, Added by Anuj Goel on 20/12/2009  starts
    public static String FRANCHISE_ACTIVITY_ID = "franchiseActivityID";
    public static String IS_VISIBLE = "isVisible";
    public static String CANDIDATE_PASSWORD = "candidatePassword";
    //Candidate Portal Integration, Added by Anuj Goel on 20/12/2009  ends
    //  BB63_P_E_StatusCategory
    public static final String STATUS_CATEGORY="statusCategory";
    public static final String KILLED_STATUS_ID   = "leadKilledStatusId";
    public static String LAYERED_CHART="layeredChart";
    
    //UPS ONLINE SHIPPER ACCOUNT DETAILS,added by Priyanka Jain on 11 Feb 2010  starts 
    public static final String UPS_SHIIPER_ID="shipperID";
    public static final String UPS_USERID="userID";
    public static final String UPS_LICENSE_NUMBER="licenseNumber";
    public static final String UPS_PASSWORD="password";
    public static final String UPS_CONFIRM_PASSWORD="confirmPassword";
    
    //UPS ONLINE SHIPPER ACCOUNT DETAILS,added by Priyanka Jain on 11 Feb 2010  ends
    

     public static String CONFIGURE_VEHICLE = "configureVehicle";
     public static String CONFIGURE_JOBS = "configureJobs";
     public static String CONFIGURE_RATE_CARD = "configureRateCard";
     public static String CONFIGURE_SCOPE = "configureScope";
    	 public static String VEHICLE_NAME = "vehicleName";
    	 public static String GUEST_OF_HONOR = "guestOfHonor";
    	 public static String CONFIGURE_ASSIGN_TO_OTHER = "configureAssignToOther";
    	 
    	 
    	 public static String CONFIGURABLE_TAB = "configurableTab";
    	 public static String CONFIGURABLE_TAB_NAME = "configurableTabName";
         
         public static final String FROM_CHECK = "fromCheck";
         /* FieldNames for Table SKILLSET */
            public static String SKILL_ID = "skillID";
            public static String SKILL_NAME = "skillName";
            public static String ONE_LINE_SKILL_DESCRIPTION = "oneLineSkillDescription";
            public static String SKILL_COMMA_SEPARATED = "skillCommaSeparated";
            public static String RATE_PER_HOUR = "ratePerHour";

            public static String CAREGIVER_ID = "caregiverID";
            public static String CAREGIVER_SKILLSET_ID = "caregiverSkillsetID";
            public static String CONFIGURE_SKILL = "configureSkill";
	
	/* Field Names For Home Page View Table */
          
	public static final String CARD_OWNER_NAME 		= "ccOwnerName";
	public static final String CARD_OWNER_ADDRESS	= "ccOwnerAddress";
	public static final String CARD_OWNER_STATE		= "ccOwnerState";
	public static final String CARD_OWNER_COUNTRY	= "ccOwnerCountry";
	public static final String CARD_OWNER_ZIP		= "ccOwnerZip";
	public static final String CARD_OWNER_EMAIL_ID	= "ccOwnerEmail";
        
        public static String ADMIN_SETTING_DISPLAY = "adminSettingDisplay";
        
         public static String IS_DATA_PRESENT = "isDataPresent";
    public static String FRANCHISEE_USER_NAME = "franchiseeUserNo";
    public static String ALL_ASSIGNED_USERS = "allAssignedUsers";
    

    //Service314 Entries Start
    /* FieldNames for Table SKILLSET */
	
	
	public static String ASSIGN_STATUS = "assignStatus";
	public static String FIRST_CHOICE_START_TIME = "firstChoiceStartTime";
	public static String FIRST_CHOICE_END_TIME = "firstChoiceEndTime";
	public static String SECOND_CHOICE_START_TIME = "secondChoiceStartTime";
	public static String SECOND_CHOICE_END_TIME = "secondChoiceEndTime";

	//	Added By Ram Avtar 
    public static String DISPATCH_VIEW_PARAMS = "dispatchViewParams";
    public static String DISPATCH_FRANCHISEE_ID = "dispatchFranchiseeID";
    public static String DISPATCH_USER_ID = "dispatchUserID";
    
    public static String ASSIGN_TO_OTHERS = "assignToOthers";
    public static String DEPLOYED_VERSION_NUMBER = "deployedVersionNumber";
    public static String DEFAULT_SCHEDULER_VIEW = "defaultSchedulerView";
    
    
	public static String EVENT_INFO_ID = "eventInfoID";
	public static String IS_INFO = "isInfo";
	
	public static String DT_FROM = "dtFrom";
	 public static String DT_TO = "dtTo";
	 public static String DT_CC = "dtCc";
	 public static String DT_BCC = "dtBcc";
	 public static String DT_SUBJECT = "dtSubject";
	 public static String DT_MESSAGE = "dtMessage";
	 public static String DT_MESSAGE_TYPE = "dtMessageType";
	 public static String DT_ATTACHMENTS = "dtAttachments";
	 public static String DT_ATTACHMENT_MIME_TYPES = "dtAttachmentMimeTypes";
	 public static String DT_ATTACHMENT_NAMES = "dtAttachmentNames";
	 public static String DT_INLINE_IMAGES = "dtInlineImages";
	 public static String DT_CALLER_OBJECT = "dtCallerObject"; 

         //added by ankit saini after code review of theme enhncement
         public static String FROM_WHERE="fromWhere";  
         public static String THEME_ID="themeID";  
         public static String USER_THEME="userTheme";  
         public static String RESULT="result";  
         public static String UNLINKED = "unlinked";
         
         //For FIM_SC_FRANCHISE_TODO_LIST table 
         public static String FRANCHISE_TODO_ID="franchiseToDoID";  
          
         //For FIM_SC_TODO_LIST table 
         public static String TODO_ID="todoID";   
         public static String RECCURRING="reccurring";   
         public static String ESTIMATED_COMPLETION_DAYS="estimatedCompletionDays";   
         public static final String WEB_URL = "webUrl";
         //ganesh,18-may-2010
         public static final String QUALIFICATION_WEBPAGE_DISPLAY = "qualificationWebPageDisplay";
         public static final String PERSONAL_PROFILE_WEBPAGE_DISPLAY = "personalProfileWebPageDisplay";
         
         public static String LEAD_CODE_ID="leadCodeID";   
         public static final String LEAD_CODE_NAME = "leadCodeName";
         public static final String LEAD_CODE_DESCRIPTION = "leadCodeDescription";
         public static final String LEAD_CODE_ORDER = "leadCodeOrder";
         public static final String FRAN_LEAD_CODE_ORDER = "franleadCodeOrder"; //Added Bu NishantP 
         public static final String MARKETING_CODE_ID="marketingCodeId";
         public static final String MARKETING_CODE_NAME="marketingCodeName";
         public static String CAPTIVATE_VISITED = "captivateVisited";
         
         public static final String AREA_INFO_C1="areaInfoC1";
         public static final String AREA_INFO_C2="areaInfoC2";
         public static final String AREA_INFO_C3="areaInfoC3";
         public static final String AREA_INFO_C4="areaInfoC4";
         public static final String AREA_INFO_C5="areaInfoC5";
         public static final String AREA_INFO_C6="areaInfoC6";
         public static final String AREA_INFO_C7="areaInfoC7";
         public static final String AREA_INFO_C8="areaInfoC8";
         public static final String AREA_INFO_C9="areaInfoC9";
         public static final String AREA_INFO_C10="areaInfoC10";
         
         
         public static final String AREA_REAL_ESTATE_C1="areaRealEstateC1";
         public static final String AREA_REAL_ESTATE_C2="areaRealEstateC2";
         public static final String AREA_REAL_ESTATE_C3="areaRealEstateC3";
         public static final String AREA_REAL_ESTATE_C4="areaRealEstateC4";
         public static final String AREA_REAL_ESTATE_C5="areaRealEstateC5";
         public static final String AREA_REAL_ESTATE_C6="areaRealEstateC6";
         public static final String AREA_REAL_ESTATE_C7="areaRealEstateC7";
         public static final String AREA_REAL_ESTATE_C8="areaRealEstateC8";
         public static final String AREA_REAL_ESTATE_C9="areaRealEstateC9";
         public static final String AREA_REAL_ESTATE_C10="areaRealEstateC10";
         
         public static final String AREA_TRAINING_C1="areaTrainingC1";
         public static final String AREA_TRAINING_C2="areaTrainingC2";
         public static final String AREA_TRAINING_C3="areaTrainingC3";
         public static final String AREA_TRAINING_C4="areaTrainingC4";
         public static final String AREA_TRAINING_C5="areaTrainingC5";
         public static final String AREA_TRAINING_C6="areaTrainingC6";
         public static final String AREA_TRAINING_C7="areaTrainingC7";
         public static final String AREA_TRAINING_C8="areaTrainingC8";
         public static final String AREA_TRAINING_C9="areaTrainingC9";
         public static final String AREA_TRAINING_C10="areaTrainingC10";
         
         
         public static final String AREA_RENEWAL_C1="areaRenewalC1";
         public static final String AREA_RENEWAL_C2="areaRenewalC2";
         public static final String AREA_RENEWAL_C3="areaRenewalC3";
         public static final String AREA_RENEWAL_C4="areaRenewalC4";
         public static final String AREA_RENEWAL_C5="areaRenewalC5";
         public static final String AREA_RENEWAL_C6="areaRenewalC6";
         public static final String AREA_RENEWAL_C7="areaRenewalC7";
         public static final String AREA_RENEWAL_C8="areaRenewalC8";
         public static final String AREA_RENEWAL_C9="areaRenewalC9";
         public static final String AREA_RENEWAL_C10="areaRenewalC10";
         
         /* Field Names for Table FIM FOR AREA_LEGAL_VIOLATION */ 
         public static final String AREA_LEGAL_VIOLATION_C1="areaLegalViolationC1";
         public static final String AREA_LEGAL_VIOLATION_C2="areaLegalViolationC2";
         public static final String AREA_LEGAL_VIOLATION_C3="areaLegalViolationC3";
         public static final String AREA_LEGAL_VIOLATION_C4="areaLegalViolationC4";
         public static final String AREA_LEGAL_VIOLATION_C5="areaLegalViolationC5";
         public static final String AREA_LEGAL_VIOLATION_C6="areaLegalViolationC6";
         public static final String AREA_LEGAL_VIOLATION_C7="areaLegalViolationC7";
         public static final String AREA_LEGAL_VIOLATION_C8="areaLegalViolationC8";
         public static final String AREA_LEGAL_VIOLATION_C9="areaLegalViolationC9";
         public static final String AREA_LEGAL_VIOLATION_C10="areaLegalViolationC10";
         
 /* Field Names for Table FIM FOR AREA_CONTRACT */
         
         public static final String AREA_CONTRACT_C1="areaContractC1";
         public static final String AREA_CONTRACT_C2="areaContractC2";
         public static final String AREA_CONTRACT_C3="areaContractC3";
         public static final String AREA_CONTRACT_C4="areaContractC4";
         public static final String AREA_CONTRACT_C5="areaContractC5";
         public static final String AREA_CONTRACT_C6="areaContractC6";
         public static final String AREA_CONTRACT_C7="areaContractC7";
         public static final String AREA_CONTRACT_C8="areaContractC8";
         public static final String AREA_CONTRACT_C9="areaContractC9";
         public static final String AREA_CONTRACT_C10="areaContractC10";
         
         public static final String AREA_DEVELOPER_C1="areaAdC1";
         public static final String AREA_DEVELOPER_C2="areaAdC2";
         public static final String AREA_DEVELOPER_C3="areaAdC3";
         public static final String AREA_DEVELOPER_C4="areaAdC4";
         public static final String AREA_DEVELOPER_C5="areaAdC5";
         public static final String AREA_DEVELOPER_C6="areaAdC6";
         public static final String AREA_DEVELOPER_C7="areaAdC7";
         public static final String AREA_DEVELOPER_C8="areaAdC8";
         public static final String AREA_DEVELOPER_C9="areaAdC9";
         public static final String AREA_DEVELOPER_C10="areaAdC10";
         
         
         public static final String AREA_ADDRESS_C1="areaAddressC1";
         public static final String AREA_ADDRESS_C2="areaAddressC2";
         public static final String AREA_ADDRESS_C3="areaAddressC3";
         public static final String AREA_ADDRESS_C4="areaAddressC4";
         public static final String AREA_ADDRESS_C5="areaAddressC5";
         public static final String AREA_ADDRESS_C6="areaAddressC6";
         public static final String AREA_ADDRESS_C7="areaAddressC7";
         public static final String AREA_ADDRESS_C8="areaAddressC8";
         public static final String AREA_ADDRESS_C9="areaAddressC9";
         public static final String AREA_ADDRESS_C10="areaAddressC10";
         
         public static final String AREA_ENTITY_DETAIL_C1="AreaEntityDetailC1";
         public static final String AREA_ENTITY_DETAIL_C2="AreaEntityDetailC2";
         public static final String AREA_ENTITY_DETAIL_C3="AreaEntityDetailC3";
         public static final String AREA_ENTITY_DETAIL_C4="AreaEntityDetailC4";
         public static final String AREA_ENTITY_DETAIL_C5="AreaEntityDetailC5";
         public static final String AREA_ENTITY_DETAIL_C6="AreaEntityDetailC6";
         public static final String AREA_ENTITY_DETAIL_C7="AreaEntityDetailC7";
         public static final String AREA_ENTITY_DETAIL_C8="AreaEntityDetailC8";
         public static final String AREA_ENTITY_DETAIL_C9="AreaEntityDetailC9";
         public static final String AREA_ENTITY_DETAIL_C10="AreaEntityDetailC10";
         
         
         public static final String AREA_TERRITORY_C1="areaTerritoryC1";
         public static final String AREA_TERRITORY_C2="areaTerritoryC2";
         public static final String AREA_TERRITORY_C3="areaTerritoryC3";
         public static final String AREA_TERRITORY_C4="areaTerritoryC4";
         public static final String AREA_TERRITORY_C5="areaTerritoryC5";
         public static final String AREA_TERRITORY_C6="areaTerritoryC6";
         public static final String AREA_TERRITORY_C7="areaTerritoryC7";
         public static final String AREA_TERRITORY_C8="areaTerritoryC8";
         public static final String AREA_TERRITORY_C9="areaTerritoryC9";
         public static final String AREA_TERRITORY_C10="areaTerritoryC10";
         
         // ADDING FOR MU REALESTATE
         public static final String FIM_MU_REAL_ESTATE_C1="muRealEstateC1";
         public static final String FIM_MU_REAL_ESTATE_C2="muRealEstateC2";
         public static final String FIM_MU_REAL_ESTATE_C3="muRealEstateC3";
         public static final String FIM_MU_REAL_ESTATE_C4="muRealEstateC4";
         public static final String FIM_MU_REAL_ESTATE_C5="muRealEstateC5";
         public static final String FIM_MU_REAL_ESTATE_C6="muRealEstateC6";
         public static final String FIM_MU_REAL_ESTATE_C7="muRealEstateC7";
         public static final String FIM_MU_REAL_ESTATE_C8="muRealEstateC8";
         public static final String FIM_MU_REAL_ESTATE_C9="muRealEstateC9";
         public static final String FIM_MU_REAL_ESTATE_C10="muRealEstateC10";
         
         // ADDING FOR MU REALESTATE
         public static final String FIM_MU_TERRITORY_C1="muTerritoryC1";
         public static final String FIM_MU_TERRITORY_C2="muTerritoryC2";
         public static final String FIM_MU_TERRITORY_C3="muTerritoryC3";
         public static final String FIM_MU_TERRITORY_C4="muTerritoryC4";
         public static final String FIM_MU_TERRITORY_C5="muTerritoryC5";
         public static final String FIM_MU_TERRITORY_C6="muTerritoryC6";
         public static final String FIM_MU_TERRITORY_C7="muTerritoryC7";
         public static final String FIM_MU_TERRITORY_C8="muTerritoryC8";
         public static final String FIM_MU_TERRITORY_C9="muTerritoryC9";
         public static final String FIM_MU_TERRITORY_C10="muTerritoryC10";
         
         public static final String DAYS="days";
         public static final String ACTIVITY_COMPLETION_DATE="activityCompletionDate";
         // Added by Akhil Kumar for Key Config
         public static final String LANGUAGE_ID="languageId";
         public static final String LANGUAGE_NAME="languageName";
         

         
         public static final String MU_DETAILS_C1="muDetailsC1";
         public static final String MU_DETAILS_C2="muDetailsC2";
         public static final String MU_DETAILS_C3="muDetailsC3";
         public static final String MU_DETAILS_C4="muDetailsC4";
         public static final String MU_DETAILS_C5="muDetailsC5";
         public static final String MU_DETAILS_C6="muDetailsC6";
         public static final String MU_DETAILS_C7="muDetailsC7";
         public static final String MU_DETAILS_C8="muDetailsC8";
         public static final String MU_DETAILS_C9="muDetailsC9";
         public static final String MU_DETAILS_C10="muDetailsC10";
         
         public static final String FIM_MU_ENTITY_DETAIL_C1="fimMuEntityDetailC1";
         public static final String FIM_MU_ENTITY_DETAIL_C2="fimMuEntityDetailC2";
         public static final String FIM_MU_ENTITY_DETAIL_C3="fimMuEntityDetailC3";
         public static final String FIM_MU_ENTITY_DETAIL_C4="fimMuEntityDetailC4";
         public static final String FIM_MU_ENTITY_DETAIL_C5="fimMuEntityDetailC5";
         public static final String FIM_MU_ENTITY_DETAIL_C6="fimMuEntityDetailC6";
         public static final String FIM_MU_ENTITY_DETAIL_C7="fimMuEntityDetailC7";
         public static final String FIM_MU_ENTITY_DETAIL_C8="fimMuEntityDetailC8";
         public static final String FIM_MU_ENTITY_DETAIL_C9="fimMuEntityDetailC9";
         public static final String FIM_MU_ENTITY_DETAIL_C10="fimMuEntityDetailC10";
   
         public static final String FIM_MU_OTHER_ADDRESS_C1="fimMuOtherAddressC1";
         public static final String FIM_MU_OTHER_ADDRESS_C2="fimMuOtherAddressC2";
         public static final String FIM_MU_OTHER_ADDRESS_C3="fimMuOtherAddressC3";
         public static final String FIM_MU_OTHER_ADDRESS_C4="fimMuOtherAddressC4";
         public static final String FIM_MU_OTHER_ADDRESS_C5="fimMuOtherAddressC5";
         public static final String FIM_MU_OTHER_ADDRESS_C6="fimMuOtherAddressC6";
         public static final String FIM_MU_OTHER_ADDRESS_C7="fimMuOtherAddressC7";
         public static final String FIM_MU_OTHER_ADDRESS_C8="fimMuOtherAddressC8";
         public static final String FIM_MU_OTHER_ADDRESS_C9="fimMuOtherAddressC9";
         public static final String FIM_MU_OTHER_ADDRESS_C10="fimMuOtherAddressC10";
         
         public static final String FIM_MU_LICENSE_AGREEMENT_C1="fimMuLicenseAgreementC1";
         public static final String FIM_MU_LICENSE_AGREEMENT_C2="fimMuLicenseAgreementC2";
         public static final String FIM_MU_LICENSE_AGREEMENT_C3="fimMuLicenseAgreementC3";
         public static final String FIM_MU_LICENSE_AGREEMENT_C4="fimMuLicenseAgreementC4";
         public static final String FIM_MU_LICENSE_AGREEMENT_C5="fimMuLicenseAgreementC5";
         public static final String FIM_MU_LICENSE_AGREEMENT_C6="fimMuLicenseAgreementC6";
         public static final String FIM_MU_LICENSE_AGREEMENT_C7="fimMuLicenseAgreementC7";
         public static final String FIM_MU_LICENSE_AGREEMENT_C8="fimMuLicenseAgreementC8";
         public static final String FIM_MU_LICENSE_AGREEMENT_C9="fimMuLicenseAgreementC9";
         public static final String FIM_MU_LICENSE_AGREEMENT_C10="fimMuLicenseAgreementC10";
        
         public static final String FIM_MU_CONTRACT_C1="fimMuContractC1";
         public static final String FIM_MU_CONTRACT_C2="fimMuContractC2";
         public static final String FIM_MU_CONTRACT_C3="fimMuContractC3";
         public static final String FIM_MU_CONTRACT_C4="fimMuContractC4";
         public static final String FIM_MU_CONTRACT_C5="fimMuContractC5";
         public static final String FIM_MU_CONTRACT_C6="fimMuContractC6";
         public static final String FIM_MU_CONTRACT_C7="fimMuContractC7";
         public static final String FIM_MU_CONTRACT_C8="fimMuContractC8";
         public static final String FIM_MU_CONTRACT_C9="fimMuContractC9";
         public static final String FIM_MU_CONTRACT_C10="fimMuContractC10";
         
         public static final String MU_INSURANCE_C1="muInsuranceC1";
         public static final String MU_INSURANCE_C2="muInsuranceC2";
         public static final String MU_INSURANCE_C3="muInsuranceC3";
         public static final String MU_INSURANCE_C4="muInsuranceC4";
         public static final String MU_INSURANCE_C5="muInsuranceC5";
         public static final String MU_INSURANCE_C6="muInsuranceC6";
         public static final String MU_INSURANCE_C7="muInsuranceC7";
         public static final String MU_INSURANCE_C8="muInsuranceC8";
         public static final String MU_INSURANCE_C9="muInsuranceC9";
         public static final String MU_INSURANCE_C10="muInsuranceC10";
         
         public static final String MU_GUARANTOR_C1="muGuarantorC1";
         public static final String MU_GUARANTOR_C2="muGuarantorC2";
         public static final String MU_GUARANTOR_C3="muGuarantorC3";
         public static final String MU_GUARANTOR_C4="muGuarantorC4";
         public static final String MU_GUARANTOR_C5="muGuarantorC5";
         public static final String MU_GUARANTOR_C6="muGuarantorC6";
         public static final String MU_GUARANTOR_C7="muGuarantorC7";
         public static final String MU_GUARANTOR_C8="muGuarantorC8";
         public static final String MU_GUARANTOR_C9="muGuarantorC9";
         public static final String MU_GUARANTOR_C10="muGuarantorC10";
         
         public static final String MU_FINANCIAL_C1="muFinancialC1";
         public static final String MU_FINANCIAL_C2="muFinancialC2";
         public static final String MU_FINANCIAL_C3="muFinancialC3";
         public static final String MU_FINANCIAL_C4="muFinancialC4";
         public static final String MU_FINANCIAL_C5="muFinancialC5";
         public static final String MU_FINANCIAL_C6="muFinancialC6";
         public static final String MU_FINANCIAL_C7="muFinancialC7";
         public static final String MU_FINANCIAL_C8="muFinancialC8";
         public static final String MU_FINANCIAL_C9="muFinancialC9";
         public static final String MU_FINANCIAL_C10="muFinancialC10";
         
         public static final String MU_LENDER_C1="muLenderC1";
         public static final String MU_LENDER_C2="muLenderC2";
         public static final String MU_LENDER_C3="muLenderC3";
         public static final String MU_LENDER_C4="muLenderC4";
         public static final String MU_LENDER_C5="muLenderC5";
         public static final String MU_LENDER_C6="muLenderC6";
         public static final String MU_LENDER_C7="muLenderC7";
         public static final String MU_LENDER_C8="muLenderC8";
         public static final String MU_LENDER_C9="muLenderC9";
         public static final String MU_LENDER_C10="muLenderC10";
         
         public static final String FIM_MU_LEGAL_VIOLATION_C1="fimMuLegalViolationC1";
         public static final String FIM_MU_LEGAL_VIOLATION_C2="fimMuLegalViolationC2";
         public static final String FIM_MU_LEGAL_VIOLATION_C3="fimMuLegalViolationC3";
         public static final String FIM_MU_LEGAL_VIOLATION_C4="fimMuLegalViolationC4";
         public static final String FIM_MU_LEGAL_VIOLATION_C5="fimMuLegalViolationC5";
         public static final String FIM_MU_LEGAL_VIOLATION_C6="fimMuLegalViolationC6";
         public static final String FIM_MU_LEGAL_VIOLATION_C7="fimMuLegalViolationC7";
         public static final String FIM_MU_LEGAL_VIOLATION_C8="fimMuLegalViolationC8";
         public static final String FIM_MU_LEGAL_VIOLATION_C9="fimMuLegalViolationC9";
         public static final String FIM_MU_LEGAL_VIOLATION_C10="fimMuLegalViolationC10";
        
         public static final String MU_RENEWAL_C1="muRenewalC1";
         public static final String MU_RENEWAL_C2="muRenewalC2";
         public static final String MU_RENEWAL_C3="muRenewalC3";
         public static final String MU_RENEWAL_C4="muRenewalC4";
         public static final String MU_RENEWAL_C5="muRenewalC5";
         public static final String MU_RENEWAL_C6="muRenewalC6";
         public static final String MU_RENEWAL_C7="muRenewalC7";
         public static final String MU_RENEWAL_C8="muRenewalC8";
         public static final String MU_RENEWAL_C9="muRenewalC9";
         public static final String MU_RENEWAL_C10="muRenewalC10";
         
         public static final String ACTIVITY_DAYS="activityDays";
         public static final String FS_ACTIVITY_DAY_ID="fsActivityDayID";
         public static final String TOTAL_DAYS="totalDays";
         
         //added by vivek maurya
         
         public static final String VARIABLE_FROM_WHERE = "variableFromWhere";
         
         //P_E_LeadMapPreview added by vivek maurya for configure corporate address starts
         public static final String BUSINESS_NAME="businessName";
         //P_E_LeadMapPreview added by vivek maurya for configure corporate address ends
         
         //P_E_FIN_UPGRADE added by vivek maurya starts
         /* Field Names for Tables FIN_AGREEMENT_RULES, FIN_AGREEMENT_RULE_DETAILS*/
         public static final String RULE_ID = "ruleID";
         public static final String RULE_NAME = "ruleID";
         public static final String CREATION_TIME = "creationTime";
         public static final String RULE_DETAIL_ID = "ruleDetailID";
         public static final String RANGE_FROM = "rangeFrom";
         public static final String RANGE_TO = "rangeTo";
         public static final String DEFAULT_RATE = "defaultRate";
         public static final String AGREEMENT_ID = "agreementId";
         public static final String AGREEMENT_NAME = "agreementName";
         public static final String AGREEMENT_TYPE_ID = "agreementTypeId";
         public static final String AGREEMENT_TYPE_NAME = "agreementTypeName";
         public static final String AGREEMENT_TYPE_ITEM_ID = "agreementTypeItemId";
         public static final String AGREEMENT_ITEM_ID = "agreementItemId";
         public static final String MASTER_ITEM_ID = "masterItemId";
         public static final String AGREEMENT_ITEM_NAME = "agreementItemName";
         public static final String AGREEMENT_ITEM_TYPE = "agreementItemType";
         public static final String AGREEMENT_ITEM_VALUE = "agreementItemValue";
         public static final String APPLY_MIN_CAP = "applyMinCap";
         public static final String APPLY_MAX_CAP = "applyMaxCap";
         public static final String MIN_CAP = "minCap";
         public static final String MAX_CAP = "maxCap";
         public static final String LAST_MODIFY_TIME = "lastModifyTime";
         public static final String ITERATIONS = "iterations";
         public static final String DELETED_ON = "deletedOn";
         public static final String DELETED_BY = "deletedBy";
         public static final String REPORTING_REQ = "reportingReq";
         public static final String REWARDS_PROG_REQ = "rewardsProgReq";
         public static final String AGREEMENT_RULE_ID = "agreementRuleId";
         public static final String AGREEMENT_RULE_NAME = "agreementRuleName";
         public static final String AGREEMENT_RULE_TYPE = "agreementRuleType";
         public static final String ACCOUNTING_BASIS = "accountingBasis";
         public static final String AGREEMENT_RULE_DETAILS_ID = "agreementRuleDetailsId";
         public static final String APPLIED_AS = "appliedAs";
         //ZCUB-20151125-204 start
         public static final String COST_RULE_TYPE = "costRuleType";
         public static final String BUNDLE_TYPE = "bundleType";
         public static final String BUNDLE_QUANTITY ="bundleQuantity";
         public static final String BUNDLE_PRICE = "bundlePrice";
         public static final String MIN_BUNDLE = "minBundle";
         public static final String MAX_BUNDLE = "maxBundle";
         public static final String MIN_BUNDLE_PRICE = "minBundlePrice";
         //ZCUB-20151125-204 ends
         public static final String APPLIED_VALUE = "appliedValue";
         public static final String FRANCHISEE_INFO = "franchiseeInfo";
         public static final String MAIN_CATEGORIES = "mainCategories";
         public static final String SUB_CATEGORIES = "subcategories";
         public static final String ADDL_ITEMS = "addlItems";
         public static final String NON_FIN_CATEGORIES = "nonFinCategories";
         public static final String MEMO_AND_HIDDEN_FIELDS_INFO = "memoAndHiddenFieldsInfo";
         public static final String SALES_FORM_MAP = "salesFormMap";
         public static final String COLUMN_INFO = "columnInfo";
         public static final String ADV_FEE = "advFee";
         public static final String ADMIN_FEE = "adminFee";
         public static final String OTHER_FEE_INV1 = "otherFeeInv1";
         public static final String OTHER_FEE_INV2 = "otherFeeInv2";
         public static final String OPEN_BAL_FRANCHISE_FEE = "openBalFranchiseFee";
         public static final String OPEN_BAL_ADV_FEE = "openBalAdvFee";
         public static final String OPEN_BAL_ADMIN_FEE = "openBalAdminFee";
         public static final String OPEN_BAL_OTHER_FEE_INV1 = "openBalOtherFeeInv1";
         public static final String OPEN_BAL_OTHER_FEE_INV2 = "openBalOtherFeeInv2";
         public static final String PROCESS_FRANCHISE_FEE_DATE = "processFranchiseFeeDate";
         public static final String PROCESS_ADV_FEE_DATE = "processAdvFeeDate";
         public static final String PROCESS_ADMIN_FEE_DATE = "processAdminFeeDate";
         public static final String PROCESS_OTHER_FEE_INV1_DATE = "processOtherFeeInv1Date";
         public static final String PROCESS_OTHER_FEE_INV2_DATE = "processOtherFeeInv2Date";
         public static final String DRP_GROUP_ID = "drpGroupId";
         public static final String DRP_GROUP_MAPPING_ID = "drpGroupMappingId";
         public static final String DRP_GROUP_NAME = "drpGroupName";
         public static final String DRP_PROGRAM_ID = "drpProgramId";
         public static final String DRP_PROGRAM_DETAIL_ID = "drpProgramDetailId";
         public static final String DRP_PROGRAM_NAME = "drpProgramName";
         public static final String RANK_FROM = "rankFrom";
         public static final String RANK_TO = "rankTo";
         public static final String DISCOUNT_TYPE = "discountType";
         public static final String DRP_RANK = "drpRank";
         public static final String PARENT_GYM_ID = "parentGymID";
         public static final String PARENT_GYM_NAME = "parentGymName";
         public static final String PARENT_GYM_DESC = "parendGymDesc";
         public static final String CHILD_GYM_ID = "childGymID";
         public static final String CHILD_GYM_NAME = "childGymName";
         public static final String CHILD_GYM_DESC = "childGymDesc";
         public static final String INTRA_GYM_ID = "intraGymID";
         public static final String RELATION_STATUS = "relationStatus";
         public static final String RELATED_GYM_ID = "relatedGymId";
         public static final String CHILD_GYM_SALES = "childGymSales";
         public static final String SUB_INVOICE_AMOUNT = "subInvoiceAmount";
         public static final String ADV_FEE_ACTUAL = "advFeeActual";
         public static final String ADMIN_FEE_ACTUAL = "adminFeeActual";
         public static final String FRANCHISE_FEE_ACTUAL = "franchiseFeeActual";
         public static final String INVOICE_AMOUNT_ACTUAL = "invoiceAmountActual";
         public static final String FRANCHISEE_DESCRIPTION = "franchiseeDescription";
         public static final String TOTAL_QUANTITY = "totalQuantity";
         public static final String GP_FLAG = "gpFlag";
         public static final String NON_REPORTING = "nonReporting";
         public static final String COLUMN_NAME = "columnName";
         public static final String ROYALTY_FOR = "royaltyFor";
         public static final String MIN_ANNUAL_CAP = "minAnnualCap";
         public static final String MAX_ANNUAL_CAP = "maxAnnualCap";
         public static final String DEPENDENT_ON = "dependentOn";
         
         
         public static final String TRANSACTION_CODE_FEE = "transactionCodeFee";

         public static final String RECEIVING_DFI_ID_FEE = "receivingDfiIDFee";

         public static final String CHECK_DIGIT_FEE = "checkDigitFee";

         public static final String DFI_ACCOUNT_NO_FEE = "dfiAccountNoFee";

         public static final String INDIVIDUAL_ID_NO_FEE = "individualIDNoFee";

         public static final String INDIVIDUAL_NAME_FEE = "individualNameFee";
         public static final String COLUMN_FOR = "columnFor";
         public static final String NACHA_FEE_CORPORATE_ENTRIES_ID = "nachaFeeCorporateEntriesID";
         
         public static final String DOCUMENT_CM_ATTACHMENT = "cmDocumentAttachment";
         public static final String DOCUMENT_ACCOUNT_ATTACHMENT = "accountDocumentAttachment";
         public static final String DOCUMENT_OPPORTUNITY_ATTACHMENT = "opportunityDocumentAttachment";
         public static final String DOCUMENT_LEAD_ATTACHMENT = "leadDocumentAttachment";
         public static final String TEMPLATE_VERSION_ID="templateVersionID"; //P_FS_E_TEMPLATE_HISTORY_VERSION
         
         //P_E_FIN_UPGRADE added by vivek maurya ends
         
       //TDG-MOBILE-20101221 starts
         public static String SYNC_RESPONSE_CODE  = "syncResponseCode";
         public static String SYNC_MODULE_NAME  = "syncModuleName";
         public static String SYNC_METHOD_NAME  = "syncMethodName";
         public static String RECORD_SYNC_ID = "recordSyncID";
         public static String WS_SYNC_FLAG  = "wsSyncFlag";
         public static String SYNC_TYPE ="syncType";
         public static String CALL_DURATION = "callDuration";
         public static String FRANCHISEE_CONSULTANT = "franConsultant";
         public static String SYNC_EXCEPTION_MESSAGE  = "syncExceptionMessage";
         public static String SYNC_STATUS_FLAG="syncStatusFlag";
         public static String NO_OF_ATTEMPTS = "noOfAttempts";
         public static String LAST_UPDATED_ON = "lastUpdatedOn";
         public static String RECORD_NO = "recordNo";
         public static String RETURN_PAGE_NEW  = "returnPageNew";
         public static String CERT_TYPE="certificateType";
         public static String CERT_NO="certificateNo";
         public static String CERT_DATE="certificateDate";
         public static String CERT_EXP_DATE="certificateExpDate";
         public static String SALES_NC = "salesNC";
     	public static String SALES_EXL_NC = "salesExlNC";
     	public static String ADV_NC = "advNC";
     	public static String ADV_EXL_NC = "advExlNC";
     	public static String MIN_ADV_AMOUNT = "minAdvAmount";
     	public static String BASE_FEE_ROY="baseFeeRoy";
        public static String BASE_FEE_ADV="baseFeeAdv";
        public static String MIN_ROYALTY_AMOUNT = "minRoyaltyAmount";
        public static String IS_AMOUNT="isAmount";
        public static String IS_AMOUNT_NC="isAmountNC";
        public static String ROYALTY_SCALE_TYPE="royaltyScaleType";
        public static String VERSION_SCALE_TYPE="versionScaleType";
        public static String MIN_ROYALTY_TYPE="minRoyaltyType";
        public static String ANNUAL_INCOME = "annualIncome";
        public static String VERSION_TYPE="versionType";
        public static String TOTAL_ADD_FEE="totalAddFee";
        public static String ADD_FEE_CAP_APPLIED = "addFeeCapApplied";
        public static String ROYALTY_PERCENTAGE_NC = "royaltyPercentageNc";
    	public static String ADV_PERCENTAGE_NC = "advPercentageNc";
    	public static String ROYALTY_NC = "royaltyNC";
    	public static String ROYALTY_EXL_NC = "royaltyExlNC";
    	public static String CATEGORY_SALES= "categorySales";
        public static String CATEGORY_QUANTITY= "categoryQuantity";
        public static String IS_SALES_SUBMITTED="maxAnualRangeAmount";
        public static String ROLL_IN_WEEKS="rollInWeeks";
        public static String AV_START_DATE="avStartDate";
        public static String AV_END_DATE="avEndDate";
        public static String TOTAL_ROLL_IN_WEEKS="totalRollInWeeks";
        public static String IS_CALC_PARTS="isCalcParts";
        public static String MIN_ROLL_IN_SALES="minRollInSales";
        public static String TOTAL_AMOUNT_SALES="totalAmounSales";
        public static String SLAB_NO="slabNo";
        
        public static String SHIPPER_ADDRESS="shipperAddress";
        public static String BILLING_ADDRESS="billingAddress";
        public static String SHIPPER_ADDRESS2="shipperAddress2";
        public static String SHIPPING_ADDRESS="shippingAddress";
        public static String SHIPPER_STATE="shipperState";
        public static String SHIPPER_COUNTRY="shipperCountry";
        public static String SHIPPING_AREA_NAME="shippingAreaName";
        public static String BILLING_AREA_NAME="billingAreaName";
        public static String SHIPPING_FRANCHISEE_NAME="shippingFranchiseeName";
        public static String BILLING_FRANCHISEE_NAME="billingFranchiseeName";
        public static String BILLING_STATE_NAME="billingStateName";
        
        //BBEH-20110408-055 Added by Nishant starts   
        public static String EXPORT_ID="exportId";
        public static String IS_EXPORT_CONTACT="isExportContact";
        public static String IS_CONFIGURE_TAB="isConfigureTab";
        public static String NUMBER_OF_TABS="numberofTabs";
        public static String IS_CONFIGURE_PERCENTAGE="isCofigurePercentage";
        public static String PERCENTAGE_OF_RECORDS="percentageofRecords";       
        public static String NUMBER_OF_RECORDS="numberofRecords";        
        //BBEH-20110408-055 Added by Nishant ends
        
        //ZCubator_Unsubscribe_Message Nishant starts
        public static String UNSUBSCRIBE_1 = "unSubscribe1";
        public static String UNSUBSCRIBE_2 = "unSubscribe2";
        public static String UNSUBSCRIBE_3 = "unSubscribe3";        
        //ZCubator_Unsubscribe_Message ends
        
        //  P_FRANONLINE_B_74847 starts 
        public static String SCRAP_TITLE="scrapTitle";      
        //  P_FRANONLINE_B_74847 ends 
        //P_FRANONLINE_ENH_71 FranBuzz Groups Akhil Gupta Starts
        public static String GROUP_IMAGE_NAME="groupIamgeName";        
        public static String USERS_GROUPS_ID="usersGroupsID";     
        public static String VISIBILITY_OF_SCRAP="visibilityOfScrap";     
        public static String GROUP_MEMBERS="groupMembers";   
        public static final String FS_TASK_DAYS="fsTaskDays";//ACC-20101113-004 
         //P_FRANONLINE_B_77082&76922
        public static String IS_CREATOR_VISIBLE="isCreatorVisible"; 
        public static final String IS_ALL_CORP_CHECKED   = "isAllCorpChecked";
        public static final String IS_ALL_REG_CHECKED   = "isAllRegChecked";
        public static final String IS_ALL_FRAN_CHECKED   = "isAllFranChecked";
        public static final String IS_ALL_DIV_CHECKED   = "isAllDivChecked";
                
      //TDG-MOBILE-20101221 ends
        //Canteen-20110810-533  New Field is added for Configure Address Setting Start
         public static final String ADDRESS_HEADING_ID = "addressHeadingId";
         public static final String ADDRESS_HEADING_NAME = "addressHeadingName";
         
         //Canteen-20110810-533  New Field is added for Configure Address Setting Ends
         
         public static final String DEFAULT_USER   =  "defaultUser";
        
           //CAPTIVATE_INTEGRATION  Added By Narotam Singh starts
    public static String ENQURY_DATE="enquryDate";
    public static String FS_APPLICATION_PAGE_FLAG = "fsApplicationPageFlag";
   public static String FS_APPLICATION_FORM_LOCATION_FLAG = "fsApplicationFormLocationFlag";    
    public static String APPLICATION_PASSWORD ="applicationPassword";    
    public static String FS_MA_MAIL_SENT_FLAG ="fsMaMailSentFlag";
     public static String FS_MA_PASSWORD = "fsMaPassword";
     public static String LEAD_USER_ID = "leadUserId";
    //CAPTIVATE_INTEGRATION  Added By Narotam Singh ends
	  //Added by Muskaan Batra CAP_ENH_CHANGES starts
     public static final String SPOUSE_STATE="spouseState";
     public static final String ACTIVITY_FLAG="activityFlag";	
     public static final String CAMPAIGN_NO="campaignNo";	
     public static final String CLICK_ID="clickID";
     //Added by Muskaan Batra CAP_ENH_CHANGES ends
     /* Field Names for Table TMS_LOGO ADDED BY VEERPAL SINGH  on  20June2011   ENH_71BBFCNE12 */

     public static final String LOGO_ID = "logoId";
     public static final String LOGO_TYPE = "logoType";

     public static final String LOGO_NAME = "logoName";

     public static final String LOGO_PATH = "logoPath";
public static final String PHOTO = "photo";
     /* Field Names for Table TMS_PAYMENT_MODE */

	//P_E_CM_CampaignCenter added by vivek maurya starts
	public static final String CONTACT_ADD_DATE_FROM = "contactAddDateFrom";
	public static final String CONTACT_ADD_DATE_TO = "contactAddDateTo";
	//P_E_CM_CampaignCenter added by vivek maurya ends
        
       //CAPTIVATE_42_POINTS starts
     public static String CEW_MAIL_SUBJECT="cewMailSubject";
     public static String CEW_MAIL_TEXT="cewMailText";
     public static String CEW_EMAIL_FIELDS="cewEmailFields";
     /*CPT_20110627_004{34}ends*/
               
    public static final String CAPTIVATE_TEMPLATE_ACTIVE_STATUS = "captivateTemplateActiveStatus";
    public static final String CEW_DATA_ID= "cewDataId";
    public static final String CAPTIVATE_MAIL_TEMPLATE_ID="captivateMailTemplateId";
    public static final String CEW_MAIL_TEMPLATE_ID="cewMailTemplateId";
    public static final String CEW_EMAIL_NOT_READ="cewEmailNotRead";
    public static final String CEW_EMAIL_READ="cewEmailRead";
    public static final String CEW_ERROR="cewError";
    public static final String CEW_OPT_OUT="cewOptOut";
    public static final String CEW_URL_VISITED="cewUrlVisited";
    public static final String CEW_ALERT_ACTIVE="cewAlertActive";
    public static final String LAST_MODIFIED_DATE="lastModifiedDate";
    public static final String LAST_MODIFIED_USER="lastModifiedUser";
    
     public static final String  CEW_DATA_WAIT_PERIOD="cewDataWaitPeriod";
     public static final String  CEW_DATA_WAIT_PERIOD_TYPE="cewDataWaitPeriodType";
     public static final String  CEW_DATA_EMAIL_NOT_READ_STATUS ="cewDataEmailNotReadStatus";
     public static final String  CEW_DATA_EMAIL_READ_STATUS ="cewDataEmailReadStatus";
     public static final String  CEW_DATA_ERROR_STATUS ="cewDataErrorStatus";
     public static final String CEW_DATA_OPT_OUT_STATUS ="cewDataOptOutStatus";
     public static final String CEW_DATA_URL_VISITED_STATUS ="cewDataUrlVisitedStatus";
     public static final String CEW_DATA_EMAIL_NOT_READ_SEND_TO_OWNER="cewDataEmailNotReadSendToOwner";
     public static final String CEW_DATA_EMAIL_READ_SEND_TO_OWNER ="cewDataEmailReadSendToOwner";
     public static final String CEW_DATA_ERROR_SEND_TO_OWNER  ="cewDataErrorSendToOwner";
     public static final String CEW_DATA_OPT_OUT_SEND_TO_OWNER ="cewDataOptOutSendToOwner";
     public static final String CEW_DATA_URL_VISITED_SEND_TO_OWNER ="cewDataUrlVisitedSendToOwner";
     public static final String CEW_DATA_EMAIL_NOT_READ_OTHER_EMAIL_ID ="cewDataEmailNotReadOtherEmailId";
     public static final String CEW_DATA_EMAIL_READ_OTHER_EMAIL_ID  ="cewDataEmailReadOtherEmailId";
     public static final String CEW_DATA_ERROR_OTHER_EMAIL_ID  ="cewDataErrorOtherEmailId";
     public static final String CEW_DATA_OPT_OUT_OTHER_EMAIL_ID  ="cewDataOptOutOtherEmailId";
     public static final String CEW_DATA_URL_VISITED_OTHER_EMAIL_ID  ="cewDataUrlVisitedOtherEmailId";

    /* Application Fiels Names  starts*/
     	public static final String QUESTION1 = "question1"; //PP_changes
        public static final String QUESTION2 = "question2";
        public static final String QUESTION2_OTHERS = "question2Other";
        public static final String QUESTION3 = "question3";
        public static final String QUESTION4 = "question4";
        public static final String QUESTION5 = "question5";
        public static final String QUESTION6  = "question6";
        public static final String QUESTION7  = "question7";
        public static final String QUESTION8  = "question8";
        public static final String QUESTION9  = "question9";
        public static final String QUESTION10  = "question10";
        public static final String QUESTION10_IF_SO_WHERE = "question10Where";
        public static final String QUESTION11  = "question11";
        public static final String QUESTION12  = "question12";
        public static final String CONFIRMATION_DATE  = "confirmationDate";
        public static final String APPLICANT_SIGNATURE  = "applicantSignature";   
/* Application Fiels Names  ends*/
   //CAPTIVATE_42_POINTS ends

        //P_BB_IHC_20101018_504 Akhil Gupta  Starts
        public static String IS_EXCLUDED = "isExcluded";
        //P_BB_IHC_20101018_504  Ends
        
        //P_E_CM_CampaignCenter : Vivek M starts
        public static String HISTORY_ID = "historyID";
        public static String NO_OF_RECIPIENTS = "noOfRecipients";
        public static String SEND_DATE = "sendDate";
        //P_E_CM_CampaignCenter ends

        //P_FS_ENH_FranchiseAward starts
        public static String FRANCHISE_AWARDED = "franchiseAwarded";
        public static String FRANCHISE_AWARDED_DATE = "franchiseAwardedDate";
        public static String KILLED_REASON = "killedReason";
        //P_FS_ENH_FranchiseAward ends
        
        //  P_FIM_E_CUSTOM_TAB
        public static final String VISIBLE = "visible";
        
        public static final String FIELD_LENGTH = "fieldLength";
        public static String CUSTOM_TAB_ID = "customTabId";
        public static String FIM_CUSTOM_TAB_C1 = "fimCustomTabC1";
        public static String FIM_CUSTOM_TAB_C2 = "fimCustomTabC2";
        public static String FIM_CUSTOM_TAB_C3 = "fimCustomTabC3";
        public static String FIM_CUSTOM_TAB_C4 = "fimCustomTabC4";
        public static String FIM_CUSTOM_TAB_C5 = "fimCustomTabC5";
        public static String FIM_CUSTOM_TAB_C6 = "fimCustomTabC7";
        public static String FIM_CUSTOM_TAB_C7 = "fimCustomTabC7";
        public static String FIM_CUSTOM_TAB_C8 = "fimCustomTabC8";
        public static String FIM_CUSTOM_TAB_C9 = "fimCustomTabC9";
        public static String FIM_CUSTOM_TAB_C10 = "fimCustomTabC10";
        public static String FIM_CUSTOM_TAB_C11 = "fimCustomTabC11";
        public static String FIM_CUSTOM_TAB_C12 = "fimCustomTabC12";
        
        public static String FIM_CUSTOM_TAB_C13 = "fimCustomTabC13";
        public static String FIM_CUSTOM_TAB_C14 = "fimCustomTabC14";
        public static String FIM_CUSTOM_TAB_C15 = "fimCustomTabC15";
        public static String FIM_CUSTOM_TAB_C16 = "fimCustomTabC16";
        public static String FIM_CUSTOM_TAB_C17 = "fimCustomTabC17";
        public static String FIM_CUSTOM_TAB_C18 = "fimCustomTabC18";
        public static String FIM_CUSTOM_TAB_C19 = "fimCustomTabC19";
        public static String FIM_CUSTOM_TAB_C20 = "fimCustomTabC20";
        public static String FIM_CUSTOM_TAB_C21 = "fimCustomTabC21";
        public static String FIM_CUSTOM_TAB_C22 = "fimCustomTabC22";
        public static String FIM_CUSTOM_TAB_C23 = "fimCustomTabC23";
        public static String FIM_CUSTOM_TAB_C24 = "fimCustomTabC24";
        public static String FIM_CUSTOM_TAB_C25 = "fimCustomTabC25";
        public static String FIM_CUSTOM_TAB_C26 = "fimCustomTabC26";
        public static String FIM_CUSTOM_TAB_C27 = "fimCustomTabC27";
        public static String FIM_CUSTOM_TAB_C28 = "fimCustomTabC28";
        public static String FIM_CUSTOM_TAB_C29 = "fimCustomTabC29";
        public static String FIM_CUSTOM_TAB_C30 = "fimCustomTabC30";
        public static String FIM_CUSTOM_TAB_C31 = "fimCustomTabC31";
        public static String FIM_CUSTOM_TAB_C32 = "fimCustomTabC32";
        public static String FIM_CUSTOM_TAB_C33 = "fimCustomTabC33";
        public static String FIM_CUSTOM_TAB_C34 = "fimCustomTabC34";
        public static String FIM_CUSTOM_TAB_C35 = "fimCustomTabC35";
        public static String FIM_CUSTOM_TAB_C36 = "fimCustomTabC36";
        public static String FIM_CUSTOM_TAB_C37 = "fimCustomTabC37";
        public static String FIM_CUSTOM_TAB_C38 = "fimCustomTabC38";
        public static String FIM_CUSTOM_TAB_C39 = "fimCustomTabC39";
        public static String FIM_CUSTOM_TAB_C40 = "fimCustomTabC40";
        public static String FIM_CUSTOM_TAB_C41 = "fimCustomTabC41";
        public static String FIM_CUSTOM_TAB_C42 = "fimCustomTabC42";
        public static String FIM_CUSTOM_TAB_C43 = "fimCustomTabC43";
        public static String FIM_CUSTOM_TAB_C44 = "fimCustomTabC44";
        public static String FIM_CUSTOM_TAB_C45 = "fimCustomTabC45";
        public static String FIM_CUSTOM_TAB_C46 = "fimCustomTabC46";
        public static String FIM_CUSTOM_TAB_C47 = "fimCustomTabC47";
        public static String FIM_CUSTOM_TAB_C48 = "fimCustomTabC48";
        public static String FIM_CUSTOM_TAB_C49 = "fimCustomTabC49";
        public static String FIM_CUSTOM_TAB_C50 = "fimCustomTabC50";
        public static String FIM_CUSTOM_TAB_C51 = "fimCustomTabC51";
        public static String FIM_CUSTOM_TAB_C52 = "fimCustomTabC52";
        public static String FIM_CUSTOM_TAB_C53 = "fimCustomTabC53";
        public static String FIM_CUSTOM_TAB_C54 = "fimCustomTabC54";
        public static String FIM_CUSTOM_TAB_C55 = "fimCustomTabC55";
        public static String FIM_CUSTOM_TAB_C56 = "fimCustomTabC56";
        public static String FIM_CUSTOM_TAB_C57 = "fimCustomTabC57";
        public static String FIM_CUSTOM_TAB_C58 = "fimCustomTabC58";
        public static String FIM_CUSTOM_TAB_C59 = "fimCustomTabC59";
        public static String FIM_CUSTOM_TAB_C60 = "fimCustomTabC60";
        
        public static String FIM_CUSTOM_TAB_C61 = "fimCustomTabC61";
        public static String FIM_CUSTOM_TAB_C62 = "fimCustomTabC62";
        public static String FIM_CUSTOM_TAB_C63 = "fimCustomTabC63";
        public static String FIM_CUSTOM_TAB_C64 = "fimCustomTabC64";
        public static String FIM_CUSTOM_TAB_C65 = "fimCustomTabC65";
        public static String FIM_CUSTOM_TAB_C66 = "fimCustomTabC66";
        public static String FIM_CUSTOM_TAB_C67 = "fimCustomTabC67";
        public static String FIM_CUSTOM_TAB_C68 = "fimCustomTabC68";
        public static String FIM_CUSTOM_TAB_C69 = "fimCustomTabC69";
        public static String FIM_CUSTOM_TAB_C70 = "fimCustomTabC70";
        public static String FIM_CUSTOM_TAB_C71 = "fimCustomTabC71";
        public static String FIM_CUSTOM_TAB_C72 = "fimCustomTabC72";
        public static String FIM_CUSTOM_TAB_C73 = "fimCustomTabC73";
        public static String FIM_CUSTOM_TAB_C74 = "fimCustomTabC74";
        public static String FIM_CUSTOM_TAB_C75 = "fimCustomTabC75";
        public static String FIM_CUSTOM_TAB_C76 = "fimCustomTabC76";
        public static String FIM_CUSTOM_TAB_C77 = "fimCustomTabC77";
        public static String FIM_CUSTOM_TAB_C78 = "fimCustomTabC78";
        public static String FIM_CUSTOM_TAB_C79 = "fimCustomTabC79";
        public static String FIM_CUSTOM_TAB_C80 = "fimCustomTabC80";
        public static String FIM_CUSTOM_TAB_C81 = "fimCustomTabC81";
        public static String FIM_CUSTOM_TAB_C82 = "fimCustomTabC82";
        public static String FIM_CUSTOM_TAB_C83 = "fimCustomTabC83";
        public static String FIM_CUSTOM_TAB_C84 = "fimCustomTabC84";
        public static String FIM_CUSTOM_TAB_C85 = "fimCustomTabC85";
        public static String FIM_CUSTOM_TAB_C86 = "fimCustomTabC86";
        public static String FIM_CUSTOM_TAB_C87 = "fimCustomTabC87";
        public static String FIM_CUSTOM_TAB_C89 = "fimCustomTabC89";
        public static String FIM_CUSTOM_TAB_C90 = "fimCustomTabC90";
        public static String FIM_CUSTOM_TAB_C91 = "fimCustomTabC91";
        public static String FIM_CUSTOM_TAB_C92 = "fimCustomTabC92";
        public static String FIM_CUSTOM_TAB_C93 = "fimCustomTabC93";
        public static String FIM_CUSTOM_TAB_C94 = "fimCustomTabC94";
        public static String FIM_CUSTOM_TAB_C95 = "fimCustomTabC95";
        public static String FIM_CUSTOM_TAB_C96 = "fimCustomTabC96";
        public static String FIM_CUSTOM_TAB_C97 = "fimCustomTabC97";
        public static String FIM_CUSTOM_TAB_C98 = "fimCustomTabC98";
        public static String FIM_CUSTOM_TAB_C99 = "fimCustomTabC99";
        public static String FIM_CUSTOM_TAB_C100 = "fimCustomTabC100";
        public static String FIM_CUSTOM_TAB_C101 = "fimCustomTabC101";
        public static String FIM_CUSTOM_TAB_C102 = "fimCustomTabC102";
        public static String FIM_CUSTOM_TAB_C103 = "fimCustomTabC103";
        public static String FIM_CUSTOM_TAB_C104 = "fimCustomTabC104";
        public static String FIM_CUSTOM_TAB_C105 = "fimCustomTabC105";
        public static String FIM_CUSTOM_TAB_C106 = "fimCustomTabC106";
        public static String FIM_CUSTOM_TAB_C107 = "fimCustomTabC107";
        public static String FIM_CUSTOM_TAB_C108 = "fimCustomTabC108";
        public static String FIM_CUSTOM_TAB_C109 = "fimCustomTabC109";
        public static String FIM_CUSTOM_TAB_C110 = "fimCustomTabC110";
        public static String FIM_CUSTOM_TAB_C111 = "fimCustomTabC111";
        public static String FIM_CUSTOM_TAB_C112 = "fimCustomTabC112";
        public static String FIM_CUSTOM_TAB_C113 = "fimCustomTabC113";
        public static String FIM_CUSTOM_TAB_C114 = "fimCustomTabC114";
        public static String FIM_CUSTOM_TAB_C115 = "fimCustomTabC115";
        public static String FIM_CUSTOM_TAB_C116 = "fimCustomTabC116";
        public static String FIM_CUSTOM_TAB_C117 = "fimCustomTabC117";
        public static String FIM_CUSTOM_TAB_C118 = "fimCustomTabC118";
        public static String FIM_CUSTOM_TAB_C119 = "fimCustomTabC119";
        public static String FIM_CUSTOM_TAB_C120 = "fimCustomTabC120";
        public static String FIM_CUSTOM_TAB_C121 = "fimCustomTabC121";
        public static String FIM_CUSTOM_TAB_C122 = "fimCustomTabC122";
        public static String FIM_CUSTOM_TAB_C123 = "fimCustomTabC123";
        public static String FIM_CUSTOM_TAB_C124 = "fimCustomTabC124";
        public static String FIM_CUSTOM_TAB_C125 = "fimCustomTabC125";
        public static String FIM_CUSTOM_TAB_C126 = "fimCustomTabC126";
        public static String FIM_CUSTOM_TAB_C127 = "fimCustomTabC127";
        public static String FIM_CUSTOM_TAB_C128 = "fimCustomTabC128";
        public static String FIM_CUSTOM_TAB_C129 = "fimCustomTabC129";
        public static String FIM_CUSTOM_TAB_C130 = "fimCustomTabC130";
        public static String FIM_CUSTOM_TAB_C131 = "fimCustomTabC131";
        public static String FIM_CUSTOM_TAB_C132 = "fimCustomTabC132";
        public static String FIM_CUSTOM_TAB_C133 = "fimCustomTabC133";
        public static String FIM_CUSTOM_TAB_C134 = "fimCustomTabC134";
        public static String FIM_CUSTOM_TAB_C135 = "fimCustomTabC135";
        public static String FIM_CUSTOM_TAB_C136 = "fimCustomTabC136";
        public static String FIM_CUSTOM_TAB_C137 = "fimCustomTabC137";
        public static String FIM_CUSTOM_TAB_C138 = "fimCustomTabC138";
        public static String FIM_CUSTOM_TAB_C139 = "fimCustomTabC139";
        public static String FIM_CUSTOM_TAB_C140 = "fimCustomTabC140";
        public static String FIM_CUSTOM_TAB_C141 = "fimCustomTabC141";
        public static String FIM_CUSTOM_TAB_C142 = "fimCustomTabC142";
        public static String FIM_CUSTOM_TAB_C143 = "fimCustomTabC143";
        public static String FIM_CUSTOM_TAB_C144 = "fimCustomTabC144";
        public static String FIM_CUSTOM_TAB_C145 = "fimCustomTabC145";
        public static String FIM_CUSTOM_TAB_C146 = "fimCustomTabC146";
        public static String FIM_CUSTOM_TAB_C147 = "fimCustomTabC147";
        public static String FIM_CUSTOM_TAB_C148 = "fimCustomTabC148";
        public static String FIM_CUSTOM_TAB_C149 = "fimCustomTabC149";
        public static String FIM_CUSTOM_TAB_C150 = "fimCustomTabC150";
        public static String FIM_CUSTOM_TAB_C151 = "fimCustomTabC151";
        public static String FIM_CUSTOM_TAB_C152 = "fimCustomTabC152";
        public static String FIM_CUSTOM_TAB_C153 = "fimCustomTabC153";
        public static String FIM_CUSTOM_TAB_C154 = "fimCustomTabC154";
        public static String FIM_CUSTOM_TAB_C155 = "fimCustomTabC155";
        public static String FIM_CUSTOM_TAB_C156 = "fimCustomTabC156";
        public static String FIM_CUSTOM_TAB_C157 = "fimCustomTabC157";
        public static String FIM_CUSTOM_TAB_C158 = "fimCustomTabC158";
        public static String FIM_CUSTOM_TAB_C159 = "fimCustomTabC159";
        public static String FIM_CUSTOM_TAB_C160 = "fimCustomTabC160";
        public static String FIM_CUSTOM_TAB_C161 = "fimCustomTabC161";
        public static String FIM_CUSTOM_TAB_C162 = "fimCustomTabC162";
        public static String FIM_CUSTOM_TAB_C163 = "fimCustomTabC163";
        public static String FIM_CUSTOM_TAB_C164 = "fimCustomTabC164";
        public static String FIM_CUSTOM_TAB_C165 = "fimCustomTabC165";
        public static String FIM_CUSTOM_TAB_C166 = "fimCustomTabC166";
        public static String FIM_CUSTOM_TAB_C167 = "fimCustomTabC167";
        public static String FIM_CUSTOM_TAB_C168 = "fimCustomTabC168";
        public static String FIM_CUSTOM_TAB_C169 = "fimCustomTabC169";
        public static String FIM_CUSTOM_TAB_C170 = "fimCustomTabC170";
        public static String FIM_CUSTOM_TAB_C171 = "fimCustomTabC171";
        public static String FIM_CUSTOM_TAB_C172 = "fimCustomTabC172";
        public static String FIM_CUSTOM_TAB_C173 = "fimCustomTabC173";
        public static String FIM_CUSTOM_TAB_C174 = "fimCustomTabC174";
        public static String FIM_CUSTOM_TAB_C175 = "fimCustomTabC175";
        public static String FIM_CUSTOM_TAB_C176 = "fimCustomTabC176";
        public static String FIM_CUSTOM_TAB_C177 = "fimCustomTabC177";
        public static String FIM_CUSTOM_TAB_C178 = "fimCustomTabC178";
        public static String FIM_CUSTOM_TAB_C179 = "fimCustomTabC179";
        public static String FIM_CUSTOM_TAB_C180 = "fimCustomTabC180";
        public static String FIM_CUSTOM_TAB_C181 = "fimCustomTabC181";
        public static String FIM_CUSTOM_TAB_C182 = "fimCustomTabC182";
        public static String FIM_CUSTOM_TAB_C183 = "fimCustomTabC183";
        public static String FIM_CUSTOM_TAB_C184 = "fimCustomTabC184";
        public static String FIM_CUSTOM_TAB_C185 = "fimCustomTabC185";
        public static String FIM_CUSTOM_TAB_C186 = "fimCustomTabC186";
        public static String FIM_CUSTOM_TAB_C187 = "fimCustomTabC187";
        public static String FIM_CUSTOM_TAB_C188 = "fimCustomTabC188";
        public static String FIM_CUSTOM_TAB_C189 = "fimCustomTabC189";
        public static String FIM_CUSTOM_TAB_C190 = "fimCustomTabC190";
        public static String FIM_CUSTOM_TAB_C191 = "fimCustomTabC191";
        public static String FIM_CUSTOM_TAB_C192 = "fimCustomTabC192";
        public static String FIM_CUSTOM_TAB_C193 = "fimCustomTabC193";
        public static String FIM_CUSTOM_TAB_C194 = "fimCustomTabC194";
        public static String FIM_CUSTOM_TAB_C195 = "fimCustomTabC195";
        public static String FIM_CUSTOM_TAB_C196 = "fimCustomTabC196";
        public static String FIM_CUSTOM_TAB_C197 = "fimCustomTabC197";
        public static String FIM_CUSTOM_TAB_C198 = "fimCustomTabC198";
        public static String FIM_CUSTOM_TAB_C199 = "fimCustomTabC199";
        public static String FIM_CUSTOM_TAB_C200 = "fimCustomTabC200";
        
        public static final String OPTION_VALUE = "optionValue";
		 public static final String OPTION_NAME = "optionName";
		 public static final String CUSTOM_ID = "customID";
		 public static final String NEW_FOOTER = "newFooter"; //P_BB_ENH_EMAILSIGN
		//  P_FIM_E_CUSTOM_TAB ends
		 public static final String FDD_ORDER = "fddOrder";//rmango-20111207-023_FDD_History_For_All_Mails
		 public static final String IS_MANDATORY_VALUE = "isMandatoryValue";//P_E_FIN_DefaultAddlAmount : 19Dec2011 : Vivek Maurya
                 // FACEBOOK INTEGRATION ADDED BY PRAKASH JODHA
        public static final String CLIENT_ID = "clientID";
        public static final String SOCIAL_URL_CONFIGURED = "socialURLConfigured";
        public static final String BUILD_URL_CONFIGURED = "buildURLConfigured";
        public static final String CLIENT_FOLDER = "clientFolder";
        public static final String USER_FACEBOOK_ID= "userFacebookID";
        // FACEBOOK INTEGRATION ADDED BY PRAKASH JODHA
        
        //resource Library starts
        public static final String TYPE_ID = "typeID";
        public static final String TYPE_NAME = "typeName";
        public static final String TYPE_ORDER = "typeOrder";
        //ends
       
        public static final String BUILDER_FORM_DATA_ID = "builderFormDataID";
        public static final String BUILDER_FORM_FIELDS_ID = "builderFormFieldsID";

        
        public static final String HOURS = "hours";
        public static final String DATE_OF_LABOUR = "dateOfLabour";
        public static final String SERVICE_PURCHASED = "servicePurchased";
        public static final String DATE_OF_SUBCONTRACT = "dateOfSubcontract";
        public static final String DATE_OF_MATERIAL = "dateOfMaterial";
        public static final String VENDOR = "vendor";
        public static final String ITEM_PURCHASED = "itemPurchased";
        public static final String LABOUR_COST_ID = "labourCostID";
        public static final String ENTER_BY= "enterBy";
        public static final String DATE_ENTER= "dateEnter";
        public static final String JOB_ID= "jobID";
       
        public static final String JOB_ID__EXCEL= "jobID";//P_ENH_SCH_15940
        public static final String CONTACT_NAME_EXCEL= "contactName";//P_ENH_SCH_15940
        
        //Jobs starts
        public static final String JOB_NUMBER = "jobNumber";
        //Jobs ends
        
        
        public static final String LP_TEMPLATE_ID= "lpTemplateId";
        public static final String LP_IMAGE_NAME= "lpImageName";
        public static final String LP_TEMPLATE_CODE= "lpTemplateCode";
        public static final String LP_TEMPLATE_LAYOUT_ID= "layoutID";
        public static String FORM_DESCRIPTION = "formDescription";
        public static String FORM_CONFIRMATION_MESSAGE_CONTENT = "formConfirmationMessssageContent";
        public static String FORM_CONFIRMATION_MESSAGE = "formConfirmationMessage";
        public static String FORM_IS_DEFAULT = "formIsDefault";
        public static String FORM_HEADER_DESCRIPTION = "formHeaderDescription";
        public static String FORM_BUTTON_DISPLAY_NAME = "formButtonDisplayName";
        public static String FORM_MODIFY_DATE = "formModifyDate";
        public static String FORM_ADDED_BY = "formAddedBy";
        public static String FORM_ADD_DATE = "formAddDate";
        public static String FORM_MODIFIED_BY = "formModifiedBy";
        public static String FORM_HTML_CODE = "formHtmlCode";
        public static String FORM_LEAD_TYPE = "formLeadType";
        public static String FORM_CONTACT_TYPE = "formContactType";
        public static String FORM_FRANCHISEE_NO = "formFranchiseeNo";
        public static String FORM_OWNER_TYPE = "formOwnerType";
        public static String FORM_AREA_ID = "formAreaId";
        public static String FORM_TITLE = "formTitle";
        public static String FIELDS_ID = "fieldsId";
        public static String FIELDS_NAME = "fieldsName";
        public static String FIELDS_DISPLAY_NAME = "fieldsDisplayName";
        public static String FIELDS_TYPE = "fieldsType";
        public static String FIELDS_SIZE = "fieldsSize";
        public static String FIELDS_IS_REQUIRED = "fieldsIsRequired";
        public static String FIELDS_VALUE = "fieldsValue";
        public static String FIELDS_WIDTH = "fieldsWidth";
        public static String FIELDS_HEIGHT = "fieldsHeight";
        public static String FIELDS_CATEGORY = "fieldsCategory";
        public static String FIELDS_ORDER = "fieldsOrder";
        
        
        //P_E_ConfigurableReturnPath starts
        public static final String MAIL_RETURN_PATH = "mailReturnpath";
        public static final String IS_SAME = "isSame";
        //P_E_ConfigurableReturnPath ends
        
        //fields for marketing goals starts
        public static final String GOAL_ID = "goalId";
        public static final String GOAL_NAME = "goalName";
        public static final String CONFIGURED_ID = "configuredId";
        public static final String METRIC_TYPE = "metricType";
        public static final String METRIC_TYPE_CONTENT = "metricTypeContent";
       //fields for marketing goals ends
        
        //P_E_CM_QuickCampaign starts
        public static final String IS_MODIFIED = "isModified";
        public static final String QUICK_CAMPAIGN = "quickCampaign";
        //P_E_CM_QuickCampaign ends
        public static String EMP_HOURLY_RATE = "empHourlyRate";
        
        //P_E_CampaignCenter_Triggers starts
        public static final String TRIGGER_TYPE = "triggerType";
        public static final String TRIGGER_NAME = "triggerName";
        public static final String DAYS_DIFFERENCE = "daysDifference";
        public static final String DAYS_FILTER = "daysFilter";
        //P_E_CampaignCenter_Triggers ends
        
        //P_E_CM_FranchiseeTriggers starts
        public static final String SAVE = "save";
        public static final String MINUS_ONE = "-1";
        public static final String ZERO = "0";
        public static final String ONE = "1";
        public static final String TWO = "2";
        public static final String MASTER_KEY = "masterCampaign";
        public static final String COPY = "copy";
        //P_E_CM_FranchiseeTriggers ends
        
        public static String ANNIVERSARY_DATE = "anniversarydate";
        public static final String PROMOTIONAL_CAMPAIGNS = "promotionalCampaigns";
        public static final String ACTIVE_CAMPAIGNS = "activeCampaigns";
        public static final String OLDER_PROMOTIONAL_CAMPAIGNS = "olderPromotionalCampaigns";	//P_CM_ENH_Campaign optimization
        
        //P_E_RATE_CARD START BY YASHU
        public static String RATE_CARD_ID = "rateCardID";
        public static String RATE_CARD_AVAILABLE = "rateCardID";
        public static String RATE_CARD_NAME = "rateCardName";
        public static String DEFAULT_LOAD = "defaultLoad";
        public static String CONSOLIDATED_DISCOUNT = "consolidatedDiscount";
        //public static String DISCOUNT_TYPE = "discountType";
        //public static String DISCOUNT = "discount";
        //public static String TAX_TYPE = "taxType";
        public static String Tax = "tax";
        //public static final String FRANCHISEE_NO = "franchiseeNo";
        //public static String ADDED_BY = "addedBy";
        public static String COVER_LETTER = "coverLetter";
        public static String COVER_LETTER_CHOICE = "coverLetterChoice";
        
        //Topper_campaign_mail start
        public static final String DEFAULT = "default";
        public static final String NO_OWNER = "No Owner";
        //Topper_campaign_mail end
        
        public static final String CATEGORY_DESCRIPTION = "categoryDescription";
        public static final String THREE = "3";
        public static final String IMPORT = "import";
        public static final String FOUR = "4";
        public static final String FIVE = "5";
        public static final String SIX = "6";
        public static final String SEVEN = "7";
        public static final String SPACE = "&nbsp;";
        public static final String NUMBER_OF_CONTACTS="numberOfContacts";
        public static final String CONFIGURED_ON="configuredOn";
        public static final String MAIL_ENABLED="mailEnabled";
        
        public static final String CONTEXT_PATH = "contextPath";
        public static final String JOB_SCHEDULER_IP = "jobSchedulerIp";
        
        //p_E_RATE_CARD END BY YASHU
        
      //P_E_CM_MailSubscription	starts
        public static final String IS_IN_MAILING_LIST = "isInMailingList";
        public static final String CAPTIVATE_MAIL_UNSUBSCRIBE = "captivateMailUnsubscribe";
        public static final String CAPTIVATE_MAIL_UNSUBSCRIBE_DATE = "captivateMailUnsubscribeDate";
        public static final String OPTED_FROM_BUILD = "optedFromBuild";
        public static final String LINK_MESSAGE_2 = "linkMessage2";
        public static final String LINK_MESSAGE_3 = "linkMessage3";
        public static final String IS_CONFIRM = "isConfirm";
        public static final String IS_UNSUBSCRIBE = "isUnsubscribe";
        public static final String SUBSCRIPTION_ID = "subscriptionID";
        public static final String LOG_DATE = "logDate";
        public static final String STATUS_FROM = "statusFrom";
        public static final String STATUS_TO = "statusTo";
        public static final String OPT_IN = "optIn";
        public static final String OPT_IN_MESSAGE = "optInMessage";
        public static final String OPT_OUT_MESSAGE = "optOutMessage";
        //P_E_CM_MailSubscription ends
        public static final String ELECTRONIC_UFOC_DISCLOSURE = "electronicUfocSignature";//P_E_MoveToFim_AddlFDD
        
        /*Added by Divya for Deconet-20121114-748  STARTS*/
        public static final String UOM_VALUE = "uomValue";
        
        public static final String UOM_TYPE = "uomType";
        
        public static final String AM_ART_RULE_DETAILS_ID = "amArtRuleDetailsId";
        
        public static final String AM_RULE_ID = "amRuleId";
        
        public static final String AM_RULE_NAME = "amRuleName";
        
        public static final String FIX_VENDOR = "fixVendor";
        /*Added by Divya for Deconet-20121114-748   ENDS*/
        public static final String SUPPLIER_NAMES = "supplierNames";
        
        public static String TWT_ENABLED = "twtEnabled"; //Social_Media_Twitter by Deepak Gangore 
        public static final String SEARCH_STRING = "searchString";
    	public static final String SEARCH_ENGINE = "searchEngine";
    	public static final String FORUM_ID = "forumId";/*fetch-20121126-090*/
    	
    	//AUDIT_ENHANCEMENT_CHANGES starts
    	public static final String QUESTION_DB_NAME = "questionDbName";
    	public static final String IS_ARCHIVED = "isArchived";
    	public static final String RESPONSE_TYPE = "responseType";
    	public static final String PARENT_CATEGORY_ID = "parentCategoryId";
    	public static final String RESPONSE_NAME = "responseName";
    	public static final String QUESTION_TYPE = "questionType";
    	public static final String QUESTION_DESCRIPTION = "questionDescription";
    	public static final String QUESTION_MANDATORY = "questionMandantory";
    	public static final String FOR_QUESTION = "forQuestion";
    	public static final String SCORE_INCLUDED = "scoreIncluded";
    	public static final String WITH_COMMENTS = "withComments";
    	public static final String WITH_ATTACHMENTS = "withAttachments";
    	public static final String SCORE_LIMIT  = "scoreLimit";
    	public static final String ACTION_PLAN  = "actionPlan";
    	public static final String MAX_SCORE = "maxScore";
    	public static final String MIN_SCORE = "minScore";
    	public static final String COMPLIANCE_CHECK = "complianceCheck";
    	public static final String COMPLIANCE = "compliance";
    	public static final String ACTUAL_SCORE = "actualScore";
    	public static final String ACTUAL_OWNER_SCORE = "actualOwnerScore";
    	public static final String OWNER_SCORE = "ownerScore";
    	public static final String ACTION_SUBJECT = "actionSubject";
    	public static final String ACTION_PRIORITY = "actionPriority";
    	public static final String ACTION_DESCRIPTION = "actionDescription";
    	public static final String ACTION_ITEM_TYPE = "actionItemType";
    	public static final String INCLUDE_ACTION = "includeAction";
    	public static final String QUESTION_COUNT = "questionCount";
    	public static final String EXCLUDE_NON_RESPONSE  = "excludeNonResponse";
    	public static final String NOT_APPLICABLE  = "notApplicableResponse";//PWISE805_PATCH1	
    	public static final String CRITICAL_LEVEL = "criticalLevel";     //ENH_CRITICAL_LEVEL
    	
    	public static final String OPTION_ID_BEFORE_UPDATE = "optionIdBeforeUpdate"; 
    	public static final String OPTION_ID_AFTER_UPDATE = "optionIdAfterUpdate";

    	public static final String ALLOW_COMMENT = "allowComment";  //PW_ENH_PRIVATE_COMMENT

    	public static final String IS_COMMENT_VISIBLE = "isCommentVisible";
    	//ZCUB-20150921-181 start
    	public static final String MS_RULE_NAME = "msRuleName";
    	public static final String MS_RULE_ID = "msRuleId";
    	public static final String MS_ART_RULE_DETAILS_ID = "msArtRuleDetailsId";
    	//ZCUB-20150921-181 ends

    	public static final String SELECT_RESPONE = "selectRespone";
    	
    	//ENH_PW_SMART_QUESTIONS_STARTS
    	public static final String CAN_EDIT_RESPONSE  = "canEditResponse";	
    	public static final String IS_SMART_QUESTION  = "isSmartQuestion";	
    	public static final String MODULE_FIELD_NAME  = "moduleFieldName";	
    	public static final String MODULE_TABLE_NAME  = "moduleTableName";
    	public static final String DB_FIELD_NAME  = "dbFieldName";
    	public static final String DISPLAY_TYPE  = "displayType";
    	public static final String SOURCE_METHOD  = "sourceMethod";
    	public static final String SOURCE_VALUES_METHOD="sourceValuesMethod";
    	public static final String SOURCE_METHOD_ARGS = "sourceMethodArgs";
    	public static final String IS_BUILD_FIELD  = "isBuildField";
    	//ENH_PW_SMART_QUESTIONS_ENDS
    	
    	//BB-20140311-030 starts
    	public static final String LIVE_MAX_SCORE = "liveMaxScore";
    	public static final String LIVE_SCORE = "liveScore";
    	public static final String LIVE_OWNER_MAX_SCORE = "liveOwnerMaxScore";
    	public static final String LIVE_OWNER_SCORE = "liveOwnerScore";
    	public static final String OWNER_RESPONSE_JSON = "ownerResponseJson";
    	public static final String RESPONSE_JSON = "responseJson";
    	//BB-20140311-030 ends
    	//PWISE805_PATCH1  Starts By Rohit Jain
    	public static final String RESPONSE_NOT_APPLICABLE_JSON = "notApplicableResponseJson";
    	public static final String OWNER_NOT_APPLICABLE_RESPONSE_JSON  = "notApplicableOwnerResponseJson";
    	//PWISE805_PATCH1  ends
    	//Start Added by Sanshey Sachdeva on 21st Dec 2012
    	public static final String MASTER_VISIT_NUMBER = "masterVisitNumber";
    	public static final String FEEDBACK = "feedBack";
        public static final String SCHEDULE_COMPLETION_DAYS="scheduleCompletionDays";
        public static final String ACTION_ID="actionID";
    	public static final String VISIT_ID = "visitId";
    	public static final String VISIT_NUMBER = "visitNumber";
    	public static final String SCHEDULE_DATETIME = "scheduleDateTime";
    	public static final String AUDIT_DATETIME = "auditDateTime";
    	public static final String PREVIOUS_STATUS = "previousStatus";
    	public static final String MODIFICATION_DATE = "modificationDate";
    	public static final String VISIT_NAME = "visitName";
    	public static final String VISIT_DESCRIPTION = "visitDescription";
    	public static final String MASTER_VISIT_ID = "masterVisitId";
    	public static final String FORM_TYPE = "formType";
    	public static final String FORM_LAYOUT = "formLayout";
    	public static final String OWNER_RESPONSE = "ownerResponse";
    	public static final String MASTER_VISIT_NAME = "masterVisitName";
    	public static final String QUESTIONS_ACTIONS_ID = "questionActionId";
    	public static final String INSPECTION_FORM_ID = "inspectionFormId";
    	public static final String IS_AUDITOR = "isAuditor";
        public static final String IS_BILLABLE = "isBillable"; //BB-20141006-154
        public static final String IS_DAYLIGHT = "isDaylight";  //EN_DAYLIGHT_SAVING_TIME
        public static final String AUDIT_FORM_ID = "auditFormId";
    	public static final String AUDITOR_ACTUAL_SCORE = "auditorActualScore";
    	public static final String AUDITOR_MAX_SCORE = "auditorMaxScore";
    	public static final String OWNER_ACTUAL_SCORE = "ownerActualScore";
    	public static final String OWNER_MAX_SCORE = "ownerMaxScore";
    	//AUDIT_ENHANCEMENTS_PARTIAL starts
    	public static final String PARTIAL_SUBMISSION = "partialSubmission";
    	public static final String SEQUENTIAL_SUBMISSION = "sequentialSubmission";
    	//AUDIT_ENHANCEMENTS_PARTIAL_PRIVATE_COMMENTS starts
    	public static final String PRIVATE_COMMENTS = "privateComments";
    	public static final String PRIVATE_QUESTION_ID = "privateQuestionId";
    	public static final String FORM_CYCLE = "formCycle";
    	//AUDIT_ENHANCEMENTS_PARTIAL_PRIVATE_COMMENTS ends
    	//AUDIT_ENHANCEMENTS_PARTIAL ends
    	//End Added by Sanshey Sachdeva on 21st Dec 2012
    	public static final String ACTION_MANDATORY = "actionMandatory";
    	public static final String IS_ACTION_REQUIRED = "isActionRequired";//PW_805_Is_Action_Req
    	public static final String CHOOSE_SCORE_DISPLAY = "chooseScoreDisplay";
    	//public static final String HIGH_PRIORITY_QUESTION = "highPriorityQuestion";//HIGH_PRIORITY_QUESTION
    	//AUDIT_ENHANCEMENT_CHANGES ends
    	public static final String NON_MANDATORY_TASK = "nonMandatoryTask";//PW_805_CHANGE
    	
    	/** PW_FORM_VERSION Starts **/
    	public static final String FORM_VERSION 	= "formVersion";
    	/** PW_FORM_VERSION Ends **/
    	
    	//P_E_FO_CUSTOMIZATION  starts
    	
        public static String REFERENCE_FIELD = "referenceField";
        public static String REFERENCE_FLAG = "referenceFlag";
        public static String ADMIN_TASK_ID = "adminTaskId";
        public static String ADMIN_DOCUMENT_ID = "adminDocumentId";
        public static String ADMIN_EQUIPMENT_ID = "adminEquipmentId";
        public static String ADMIN_PICTURE_ID = "adminPictureId";
        public static String ADMIN_ITEM_ID = "adminItemId";
        public static String REFERENCE_PARENT="referenceParent";
        
      //P_E_FO_CUSTOMIZATION  ends
    	

    	public static final String SUB_CATEGORY = "subCategory";

    	public static final String PRE_DOCUMENT_NO = "preDocumentNo";//P_INT_VERSION_HISTORY
    	///
    	public static String FO_CUSTOM_FIELD_C1 = "foCustomFieldC1";
        public static String FO_CUSTOM_FIELD_C2 = "foCustomFieldC2";
        public static String FO_CUSTOM_FIELD_C3 = "foCustomFieldC3";
        public static String FO_CUSTOM_FIELD_C4 = "foCustomFieldC4";
        public static String FO_CUSTOM_FIELD_C5 = "foCustomFieldC5";
        public static String FO_CUSTOM_FIELD_C6 = "foCustomFieldC6";
        public static String FO_CUSTOM_FIELD_C7 = "foCustomFieldC7";
        public static String FO_CUSTOM_FIELD_C8 = "foCustomFieldC8";
        public static String FO_CUSTOM_FIELD_C9 = "foCustomFieldC9";
        public static String FO_CUSTOM_FIELD_C10 = "foCustomFieldC10";
        
        public static String CONTACT_STREET_ADDRESS = "contactStreetAddress";		//P_EnH_CM_Keywords
        
        public static String ADDITIONAL_CONTACTS="additionalContacts";	//P_E_AddlContactSendMail
        public static String OPTION_SCORE = "optionScore";
        public static String QUESTION_OPTIONS = "questionOptions";
        public static String OPTION_TEXT = "optionText";
        public static String SCORABLE = "scorable";
        public static String ADD_NEXT = "addNext";
        public static String LINK = "link";
        //P_CM_ENH_Campaign_Sch starts
        public static String CAMPAIGN_START_TIME="campaignStartTime";	
        public static String TEMPLATE_START_TIME="templateStartTime";
        public static String REMAINING_TEMPLATE_START_TIME="remainingTemplateStartTime";
        //P_CM_ENH_Campaign_Sch ends
        public static String IS_ATTACH_PDF="isAttachPdf";//P_E_FS_ConfigMail

        public static final String SERVICE_NAME = "serviceName";
        public static final String ADD_TIME = "addTime";
        
        //P_SMC_WB_ENH starts
        //Added by Sanshey Sachdeva
        public static String MENU_ID = "menuID";
        public static String MENU_DISPLAY_NAME = "menuDisplayName";
        public static String SUB_MENU_ID = "subMenuID";
        public static String SUB_MENU_DISPLAY_NAME = "subMenuDisplayName";
        public static String IS_COAPPLICANT_ENABLED = "isCoapplicantEnabled";
        public static String COAPPLICANT_ID = "coApplicantId";
        public static String COAPPLICANT_TYPE = "coApplicantType";
        public static String COAPPLICANT_RELATIONSHIP = "coApplicantRelationship";
        public static String COAPPLICANT_RELATIONSHIP_ID = "coApplicantRelationshipID";
        public static String URL_LINK = "urlLink";
        public static String FTP_LINK = "ftpLink";
        //P_SMC_WB_ENH ends
        //added by Naman Jain
    	public static final String IMAGE_WIDTH = "imageWidth";
    	public static final String IMAGE_HEIGHT= "imageHeight";
    	 public static final String[] EMPTY_STRING_ARRAY = new String[] {};
    	//ended by Naman Jain
        public static final String LOCATION_IMAGE = "locationImage";  //Appended By Dheeraj Madaan for P_AM_PROFILE_ENH ends

    	public static final String CM_DOCUMENT_LABEL_ID="cmDocumentLabelID";
    	public static String JOB_CODE_ID = "jobCodeID";
    	public static String MKT_ACTIVITY_ID = "mktactivityID";
    	public static final String IS_QUALIFIED_LEAD = "isQualifiedLead";
    	public static String LEAD_FLOW_VISITED = "leadFlowVisited";
    	public static String NO_OF_FRANCHISED_UNITS="noOfFranchisedUnits";
    	public static String NEXT_TR_ROW_ID = "nextTrRowID";
    	public static String OPTOUT_ID = "optOutId"; //P_E_ConfOptOutStatus
    	public static String CAMPAIGN_STATUS_ID = "campaignStatusID";
    	
   	 
    //P_ENH_SMS_CAMPAIGN himanshi starts
 	   public static final String SMS_TEMPLATE_ID = "smsTemplateID";
 	   public static final String SMS_TEXT = "smsText";
 	   public static final String SMS_IMAGES = "smsImages";
 	   public static final String IS_MMS = "isMMS";
 	   public static final String SMS_TEMPLATE_TITLE = "smsTemplateTitle";
 	   public static final String SMS_ADD_DATE = "smsAddDate";
 	   public static final String SMS_ADDED_BY = "smsAddedBy";
 	   public static final String DEFAULT_ID = "defaultId";
 	   public static final String DEFAULT_MESSAGE = "defaultMessage";
 	   public static final String SMS_CAMPAIGN_ID = "smsCampaignId";
 	   public static final String SMS_CAMPAIGN_TITLE = "smsCampaignTitle";
 	   public static final String SMS_CAMPAIGN_DESCRIPTION = "smsCampaignDescription";
 	   public static final String SMS_CAMPAIGN_ADD_DATE = "smsCampaignAddDate";
 	   public static final String SMS_CAMPAIGN_UPDATED_DATE = "smsCampaignUpdateDate";//P_CM_B_33224
 	   public static final String SMS_CAMPAIGN_ADDED_BY = "smsCampaignAddedBy";
 	   public static final String SMS_CAMPAIGN_START_DATE = "smsCampaignStartDate";
 	   public static final String SMS_CAMPAIGN_END_DATE = "smsCampaignEndDate";
 	   public static final String SMS_CAMPAIGN_TEMPLATE_ID = "smsCampaignTemplateId";
 	   public static final String SMS_TOKEN_ID = "smsTokenId";
 	   public static final String SMS_ACCOUNT_ID = "smsAccountId";
 	   public static final String SMS_AUTH_TOKEN = "smsAuthToken";
 	   public static final String SMS_CAMPAIGN_START_LAG = "smsCampaignStartLag";
 	   public static final String SMS_CAMPAIGN_START_TIME = "smsCampaignStartTime";
 	   public static final String SMS_CAMPAIGN_INTERVAL = "smsCampaignInterval";
 	   public static final String IS_SMS_INTERVAL_CONSTANT = "isSmsIntervalConstant";
 	   public static final String SMS_SENDING_ORDER = "smsSendingOrder";
 	   public static final String SMS_DAYS_DIFF = "smsDaysDiff";
 	   public static final String SMS_TEMPLATE_START_TIME = "smsTemplateStartTime";
 	   public static final String REMAINING_SMS_TEMPLATE_START_TIME = "remainingSmsTemplateStartTime";
 	   public static final String SMS_CAMPAIGN_TRIGGER_ID = "smsCampaignTriggerID";
 	   public static final String SMS_AUDIT_ID = "smsAuditID";
 	   public static final String SMS_SENT_DATE = "smsSentDate";
 	   public static final String SMS_CAMPAIGN_MAPPING_ID = "smsCampaignMappingID";
 	   public static final String LAST_SMS_SENT = "lastSmsSent";
 	   public static final String LAST_SMS_TEMPLATE_ID = "lastSmsTemplateID";
 	   public static final String SMS_UNSUBSCRIBE="smsUnsubscribe";
 	   public static final String SMS_UNSUBSCRIBE_DATE="smsUnsubscribeDate";
 	   public static final String FROM_NUMBER = "fromNumber";
 	   public static final String TOTAL_SMS_LIMIT = "totalSmsLimit";

 	   public static final String SMS_CAMPAIGN_ACCESSIBILITY = "smsCampaignAccessibility";//P_SMS_Campaign_Enh by Ravi
 	   public static final String SMS_TEMPLATE_ACCESSIBILITY = "smsTemplateAccessibility";//P_SMS_Campaign_Enh by Ravi
 	   public static final String INCLUDE_DEFAULT_MESSAGE = "includeDefaultMessage";//P_SMS_CAMP_DEF_MSG by Ravi
 	  public static final String ENABLE_DEFAULT_MESSAGE = "enableDefaultMessage";//P_SMS_CAMP_DEF_MSG by Ravi
 	
  //P_ENH_SMS_CAMPAIGN himanshi ends
	   public static final String FORMAT_OF_MAIL = "formatOfMail";
   	 
   //P_ADMIN_E_MUID starts
   	public static String MUID_KEY = "muidKey"; 
	public static String MUID_VALUE = "muidValue"; 
	//P_ADMIN_E_MUID ends
	
	//P_E_JSpellSupplementalDictionary starts
	public static final String WORD_ID = "wordID";
	public static final String WORD_NAME = "wordName";
	//P_E_JSpellSupplementalDictionary ends
	
	public static String MAX_RATING = "maxRating"; 
	public static String MIN_RATING = "minRating"; 
	//P_AD_E_136666_UPSIntegration Ravi starts
    public static final String ARTWORK_WEIGHT="artworkWeight";
    public static final String ACCOUNT_NO="accountNo";
    public static final String LABEL_FILE_NAME="labelFileName";
    public static final String SERVICE_TYPE="serviceType";
    //P_AD_E_136666_UPSIntegration Ravi ends
    //P_AdBuilder_Enh
    public static final String ARTWORK_NO="artworkNo";
    public static final String DATE_SAVED="dateSaved";
    public static final String SAVED_BY="savedBy";
    //P_AdBuilder_Enh
    
    public static final String IN_LOCAL = "inLocal";//P_FIN_B_24610
  //P_SUPP_ENH_Z805 ADDING UPS INTEGRATION starts
    public static final String SUPPLIER_ZIP_FILENAME="supplierZipFileName"; 
    public static final String SHIPPING_STATUS="shippingStatus";
  //P_SUPP_ENH_Z805 ADDING UPS INTEGRATION ends
    public static final String USER_COMMENTS = "userComments";
    public static final String ENABLE_SMART_SWITCH = "enableSmartSwitch";//ENH_SMARTCONNECT_SEP
    //ENH_MODULE_CUSTOM_TABS starts
    public static final String SUB_MODULE = "submodule";
    public static final String IS_CUSTOM = "isCustom";
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[]{};
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[]{};
    //ENH_MODULE_CUSTOM_TABS ends

    public static final String ACCESSIBLE_TO_ALL = "accessibleToAll"; //P_INT_END_STORY_ROLE_BASED
    public static final String DOCUMENT_CM_TITLE = "cmDocumentTitle"; //P_CM_Enh_BuilderForm 
  //P_ENH_LETTERHEAD starts
    public static final String LETTER_HEAD_ID = "letterHeadId";
    public static final String LETTER_HEAD_TITLE = "letterHeadTitle";
    public static final String LETTER_HEAD_SUBJECT = "letterHeadSubject";
    public static final String ADDED_DATE = "addedDate";
    public static final String HEAD_LOGO = "headLogo";
    public static final String FOOTER_LOGO = "footerLogo";
    public static final String AVAILABLE_FOR_USE = "availableForUse";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String BODY_COLOR = "bodyColor";
    public static final String LETTER_HEAD_DESC = "letterHeadDesc";
    
    public static final String HEAD_HEIGHT = "headHeight";
    public static final String HEAD_POSITION= "headPosition";
    public static final String HEAD_COLOR = "headColor";
    
    public static final String FOOTER_HEIGHT = "footerHeight";
    public static final String FOOTER_POSITION= "footerPosition";
    public static final String FOOTER_COLOR = "footerColor";
    public static final String HEAD_ALIGN = "headAlign";
    public static final String HEAD_VALIGN = "headValign";
    public static final String FOOTER_ALIGN = "footerAlign";
    public static final String FOOTER_VALIGN = "footerValign";
    public static final String TOP_LINE_WIDTH = "topLineWidth";
    public static final String MIDDLE_LINE_WIDTH = "middleLineWidth";
    public static final String BOTTOM_LINE_WIDTH = "bottomLineWidth";
    public static final String TOP_LINE_COLOR = "topLineColor";
    public static final String MIDDLE_LINE_COLOR = "middleLineColor";
    public static final String BOTTOM_LINE_COLOR = "bottomLineColor";
    
    //P_ENH_LETTERHEAD ends

    
    public static final String IS_OVERNIGHT = "isOvernight";//P_ENH_OVERNIGHT_TASKS


    //P_E_FO_PROJECT_STATUS starts
    public static final String PROJECT_ID = "projectId";
  //P_E_FO_PROJECT_STATUS ends

    public static final String WEBSITE_LEAD="websiteLead";//P_ENH_FS_WEBFORMIMG 
    //P_EnH_Print:starts
    public static final String GROUP_OWNER_NAME="groupOwnerName";
    public static final String LEAD_COUNT_PRINT="leadCountPrint";
    public static final String CAMPAIGN_COUNT_PRINT="campaignCountPrint";
    public static final String GROUP_NAME_PRINT="groupNamePrint";
    public static final String  SUBJECT_1="subject1";
    public static final String DAYS_TO_GRAND_OPENING_DATE = "daystoGrandOpeningDate";
    public static final String CHECKLIST_NAME = "checklistName";

    public static final String ITEM_TITLE_LEN = "20";

    public static final String FOLDER_ACCESSIBILITY = "folderAccessibility";
    
    public static final String MAPPED_COMPANY_LOCATION="mappedCompanyLocation";//ZCUB-20140226-004
    
  //P_EnH_Print:ends
    //BB-20140303-018 starts
    public static final String IS_ADDED = "isAdded";
    public static final String FORM_FILLED_DATE = "formFilledDate";
    //BB-20140303-018 ends
    
    public static final String ITEM_NAME_LEN = "20";

    
    public static final String SKIP_PRIMARY_KEY = "skipPrimaryKey";
    public static final String MANUAL_PRIMARY_KEY = "manualPrimaryKey";

    public static String OWNER_PICTURE="ownerPicture";
    
    //P_ENH_Pure_Barre_Campaign_Features starts
    public static final String PARAMETER_ID="paramaterID";
    //public static final String PARAMETER_NAME="parameterName";
    public static final String URL_PARAM_NAME="urlparamname";
    public static final String HELP_MESSAGE="helpMessage";
    //public static final String PARAMETER_VALUE="parameterValue";
    //public static final String CONFIGURATION_ID="configurationID";
    //P_ENH_Pure_Barre_Campaign_Features ends
    

    //ZCUB-20140416-054 start
  	public static final String Ad_START_DATE = "startDate";
  	public static final String Ad_END_DATE = "endDate";
  	//ZCUB-20140416-054 end

	//Valvol-20140310-006 starts
	public static final String FIM_ID="fimID";
	public static final String FIM_LOC_ID="fimLocID";
	public static final String ENTITY_CALL_ID = "entityCallID";
    public static final String ENTITY_CALL = "entityCall";
  //Valvol-20140310-006 ends

    public static final String IS_DUPLICATE_COMPANY = "isDuplicateCompany";
    
    public static final String GROUP_ORDER = "groupOrder";//P_E_FO_GROUPS
    public static final String ENTITY_ORDER="entityOrder";
    
    public static final String SCOPE_ID="scopeId";
    public static final String SCOPE_NAME="scopeName";
    public static final String SCOPE_ORDER_NO ="scopeOrderNo";

    
  //ZcubatorSales_ActivityCode Nivrutti starts
    
    public static String MKT_ACTIVITY_CODE = "activityName";
    public static String ACTIVITY_FORM_ID = "activityFormID";
    public static String EMAIL_BCC = "emailBcc";
    public static final String LAST_ASSIGNED_TO = "lastAssignedTo";
    public static String IS_SELECTED_ONLY = "isSelectedOnly";
    //ZcubatorSales_ActivityCode ends
    
	public static final String ACCOUNT_STATUS = "accountStatus";
	public static final String FIRST_NAME_REGISTERED = "firstNameFromRegistration";
	public static final String LAST_NAME_REGISTERED = "lastNameFromRegistration";
	public static final String TS_REGISTER = "tsRegister";
	public static final String BQUAL_STATUS = "bQualStatus";
	public static final String EID_VERIFIED = "eidVerified";
	public static final String EID_FAILED = "eidFailed";
	public static final String FIRST_NAME_VERIFIED = "firstNameFromVerification";
	public static final String LAST_NAME_VERIFIED = "lastNameFromVerification";
	public static final String ADDREES1_VERIFIED = "address1FromVerification";
	public static final String ADDREES2_VERIFIED = "address2FromVerification";
	public static final String CITY_VERIFIED = "cityFromVerification";
	public static final String STATE_VERIFIED = "stateFromVerification";
	public static final String POSTAL_CODE_VERIFIED = "postalCodeFromVerification";
	public static final String SUITE_VERIFIED = "suiteFromVerification";
	public static final String PHONE_VERIFIED = "phoneNumberFromVerification";
	
	//P_ENH_GOOGLELISTING
	public static final String PROFILE_COMPLETENESS = "profileCompleteness";
	public static final String STORE_INFO = "storeInfo";
	public static final String REQUEST_TYPE = "requestType";
	public static final String ALTERNATE_NO = "alternateNo";
	
	public static final String OPERATING_HOUR = "operatingHour";
	//ZCUB-20150728-164 starts
	public static final String OPERATING_DAY = "operatingDay";
	public static final String OPENING_MIN = "openingMin";
	public static final String OPENING_HOUR = "openingHour";
	public static final String OPENING_MERIDIAN = "openingMer";
	public static final String CLOSING_HOUR = "closingHour";
	public static final String CLOSING_MIN = "closingMin";
	public static final String CLOSING_MERIDIAN = "closingMer";
	//ZCUB-20150728-164 ends
	
	
	public static final String PAYMENT_TYPE_ID = "paymentTypeId";      	
	public static final String CONTAINING_LOCATION = "containingLocation";
	public static final String LOCAL_BUSINESS_TYPE_ID = "businessTypeId";
	public static final String BRANDS_CARRIED = "brandsCarried";
	public static final String NEIGHBORHOOD = "neighborhood";
	public static final String CERTIFICATION_SPECIALITY = "certificationSpeciality";
	public static final String OUT_OF_BUSINESS = "outOfBusiness";
	public static final String CATEGORY_OVERRIDES = "categoryOverrides";
	public static final String TAGLINE = "tagline";
	public static final String EXTERN_MEDIA_URL = "externalMediaUrl";
	public static final String PROMOTIONAL_URL = "promotionalUrl";
	public static final String FACEBOOK_URL = "facebookUrl";
	public static final String TWITTER_URL = "twitterUrl";
	public static final String YOUTUBE_URL = "youtubeUrl";
	public static final String LINKEDIN_URL = "linkedinUrl";
	public static final String GOOGLEPLUS_URL = "googleplusUrl";
	public static final String YELP_URL = "yelpUrl";
	public static final String VIMEO_URL = "vimeoUrl";
	public static final String RSS_URL = "rssUrl";
	public static final String CENTER_IMAGE = "centerImage";
	public static final String LOGO_URL = "logoUrl";
	public static final String EVENT_FROM = "eventFrom";
	public static final String LOCAL_EVENT = "localEvent";
	public static final String LOCAL_USER_NO = "localUserNo";
	public static final String LOCAL_FRANCHISEE_NO = "localFranchieeNo";
	public static final String AUDIT_DATE			=	"auditDate";
	
	//P_ENH_GOOGLELISTING END
	
	public static final String IN_SMS_LIST = "inSMSList";		//Moquito Joe SMS
	
	//BBP-20140530-255 starts
    public static final String MANUALLY_ADDED = "manuallyAdded";
    public static final String IN_MAILING_LIST = "inMailingList";
    public static final String SMS_OPT_RADIO = "smsOptRadio";
    public static final String MANUAL_AUTOMATIC = "manualAutomatic";
    public static final String SUBSCRIBE_FROM = "subscribeFrom";
    public static final String SUBSCRIBE_TO = "subscribeTo";
    public static final String PENDING_EMAIL_CONTENT = "pendingEmailContent";
    public static final String PENDING_EMAIL_CONTENT_EMAIL_SUBJECT = "pendingEmailContentEmailSubject";
    public static final String CONFIRMATION_EMAIL_CONTENT = "confirmationEmailContent";
    public static final String CONFIRMATION_EMAIL_CONTENT_EMAIL_SUBJECT = "confirmationEmailContentEmailSubject";
    public static final String UNSUBSCRIBE_EMAIL_CONTENT = "unsubscribeEmailContent";
    public static final String LINK_MESSAGE_1 = "linkMessage1";
    public static final String  BROWSER = "browser";

    
    public static final String TENANT_ID = "tenantid";
    public static final String SESSION_TENANT_CONTEXT = "sessionTenantContext";
    public static final String SESSION_LOCAL_LANGUAGE = "sessionLocalLanguage";
    public static final String FORWARD_SLASH = "/";
    public static final String TENANTS = "tenants";
    public static final String TENANT = "tenant";
    public static final String T_ID = "id";

    //P_SC_CR_009 statrs
	public static String CONFIGURE_PRODUCT_LEVEL_TAX = "configureProductLevelTax";
	public static String CONFIGURE_STATE_LOCAL_TAX = "configureStateLocalTax";
	public static final String SHOW_STATE_LOCAL_TAX = "ShowStateLocalTax";
	public static final String SHOW_PRODUCT_INVOICE_LEVEL_TAX = "ShowProductInvoiceLevelTax";
    //P_SC_CR_009 ends

    //BBP-20140530-255 ends
	
	//NATHAN_PROFILER_INTEGRATION : START
    public static final String NATHAN_URL = "nathanUrl";
	public static final String NATHAN_KEY = "nathanKey";
	public static final String TARGET_COUNTRY_ID = "targetCountryId";
	public static final String TARGET_URL = "targetUrl";
	//NATHAN_PROFILER_INTEGRATION : END
	
    //CG Integration starts
	public static final String CG_SUB_DOMAIN_CREATE = "https://%s.chargevault.com/create";
	public static final String CG_SUB_DOMAIN_UPDATE = "https://%s.chargevault.com/update?key=";
	public static final String CG_SERVICE_ROOT = "https://cheddargetter.com/xml";
	public static final String NON_TRIAL_PREMIUM = "nonTrialPremium";
	public static final String IS_TRIAL = "isTrial";
	public static final String IS_PREMINUM = "isPremium";
	//CG Integration ends

	
	 //BOEFLY_INTEGRATION : START
    public static final String ID_FIELD="idField";
    public static final String INTEGRATION_ID = "integrationID"; //NATHAN_PROFILER_INTEGRATION
	public static final String BOEFLY_URL = "boeflyUrl";
	public static final String SERVICE_URL = "serviceUrl";
	public static final String IS_INTEGRATED = "isIntegrated"; //NATHAN_PROFILER_INTEGRATION
	public static final String BOEFLY_ID = "boeflyId";
	public static final String SEND_AUTOMATIC_MAIL="sendAutomaticMail";
	//BOEFLY_INTEGRATION : END
	//PROVEN_MATCH_INTEGRATION : START 
	public static final String FRANCHISOR_ID = "franchisorId";
	public static final String ASSESSMENT_ID = "assessmentId";
	public static final String DATE_COMPLETED = "dateCompleted";
	public static final String RANKING = "ranking";
	public static final String LINK_TO_PDF = "linkToPDF";
	public static final String SSO_URL = "ssoUrl";
	public static final String SERVICE_URI="serviceUri";
	public static final String NAME_SPACE="nameSpace";
	public static final String LEAD_INVITE="leadInvite";
	public static final String SEND_REMINDER="sendReminder";
	public static final String UPDATE_STATUS="updateStatus";
	//PROVEN_MATCH_INTEGRATION : END

	
	public static final String IS_FDD_BRAND_CONFIGURED = "isFddBrandConfigured";
	public static final String IS_COAPPLICANT_AS_LEAD_CONFIGURED = "isCoApplicantAsLeadConfigured";		//P_ENH_COAPPLICANT_AS_LEAD
	
	//vendasta starts
	public static final String VENDASTA_LISTING_ID = "vendastaListingID";
    public static final String ACCOUNT_GROUP_ID = "accountGroupID";
    public static final String VENDASTA_CREATE_SITE_URL_DATA_TYPE = "101";
    public static final String VENDASTA_UPDTAE_SITE_URL_DATA_TYPE = "102";
    public static final String VENDASTA_DELETE_SITE_URL_DATA_TYPE = "103";
    public static final String VENDASTA_API_KEY_DATA_TYPE = "104";
    public static final String VENDASTA_API_USER_DATA_TYPE = "105";
    public static final String VENDASTA_MARKET_ID_DATA_TYPE = "106";
    public static final String VENDASTA_PURCHASE_SITE_URL_DATA_TYPE = "107";
    public static final String VENDASTA_SSO_LINK_URL_DATA_TYPE = "109";
    public static final String VENDASTA_UPDATE_ALERT_DATA_TYPE = "110";
    public static final String VENDASTA_PRESENCE_BUILDER_ID = "1";
    public static final String CUSTOMER_IDENTIFIER = "customerIdentifier";
    public static final String MSID = "msid";
    public static final String SLUG = "slug";
    public static final String SSO_TOKEN = "ssoToken";
    public static final String VENDASTA_CATEGORY = "vendastaCategory";
    public static final String VENDASTA_IDENTIFIER_ID = "200";
    //vendasta ends
    //ZCUB-20150202-111 starts
    public static final String BRAND_API_URL_DATA_TYPE="211";
    //ZCUB-20150202-111 ends
    public static final String VENDASTA_RENEW_START_TIME_DATA_TYPE = "111";
    public static final String VENDASTA_RENEW_MAX_THREAD_COUNT = "112";
    public static final String VENDASTA_UPDATE_START_TIME_DATA_TYPE = "113";
    public static final String VENDASTA_UPDATE_MAX_THREAD_COUNT = "114";
    
    public static final String PDF_VERSION_NO = "pdfVersionNo";//GHS-20140925-016 

    //Field Names for Local Listing Category Table starts
	public static final String BUSINESS_CATEGORY_ID = "businessCategoryId";
	public static final String BUSINESS_CATEGORY_NAME = "businessCategoryName";
	public static final String VENDASTA_CATEGORY_NAME = "vendastaBusinessCategoryName";//ZCUB-20150519-148
	//Field Names for Local Listing Category Table ends
	//Field Names for Vendasta Master Data table starts
	public static final String REPUTATION_CREATE_SITE_URL_DATA_TYPE ="201";
	public static final String REPUTATION_UPDATE_SITE_URL_DATA_TYPE = "202";
	public static final String REPUTATION_DELETE_SITE_URL_DATA_TYPE= "203";
	
	// ZCUB-20160808-281 Starts
	public static final String SOCIAL_CREATE_SITE_URL_DATA_TYPE = "208";
	public static final String SOCIAL_UPDATE_SITE_URL_DATA_TYPE = "209";
	public static final String SOCIAL_DELETE_SITE_URL_DATA_TYPE = "210";
	public static final String VENDASTA_SOCIAL_SSO_LOGIN_URL = "211";
	public static final String MODIFIDED_BY = "modifidedBy";
	public static final String LAST_MODIFIDED_DATE = "lastModifyDate";
	// ZCUB-20160808-281 Ends
	public static final String VENDASTA_REPUATATION_SSO_LOGIN_URL="204";
	public static final String VENDASTA_CREATE_PB_SITE_URL_DATA_TYPE = "205";
	public static String IS_TIME_TRACKER = "enabletimetracker"; //P_B_ADM_49828
	//ZCUB-20150121-098 starts
    public static String MORE_FILES="morefiles";
    public static String DOC_FILE1="docFile1";
    public static String ADD_MORE_FILES="addMoreFiles";
    //ZCUB-20150121-098ends
    
    public static String DOMAIN_NAME="domainName";   //ZCUB-20150121-divanshu  
    
	//Field Names for Vendasta Master Data table ends
	
	//P_ENH_LOGO_UPLOAD( Configure Logo changes starts)
	public static final String LOGIN_LOGO_NAME="loginLogoName";
	public static final String BUILD_LOGO_NAME="buildLogoName";
	public static final String SMALL_LOGO_NAME="smallLogoName";
    public static final String SMALL_LOGO_HEIGHT="smallLogoHeight";
    public static final String SMALL_LOGO_WIDTH="smallLogoWidth";
	public static final String LOGIN_LOGO_WIDTH="loginLogoWidth";
	public static final String LOGIN_LOGO_HEIGHT="loginLogoHeight";
	public static final String LOGO_ROWSPAN="logoRowSpan";
	public static final String LOGO_HEIGHT="logoHeight";
	public static final String LOGO_WIDTH="logoWidth";
	public static final String PDF_LOGO_HEIGHT="pdfLogoHeight";
	public static final String PDF_LOGO_WIDTH="pdfLogoWidth";
    public static final String FAV_ICON="favIcon";
    public static final String FAV_ICON_HEIGHT="favIconHeight";
    public static final String FAV_ICON_WIDTH="favIconWidth";
    public static final String PDF_LOGO_NAME="pdfLogoName";

	//P_ENH_LOGO_UPLOAD( Configure Logo changes ends)
    public static String getVal(String constName)
    {
        try
        {
            final Class clz = BaseFieldNames.class;
            Field f = clz.getDeclaredField(constName);
            String propName = (String) f.get(null);
            return propName;
        } catch (Exception e)
        {
            System.err.println("FieldNames.getVal(): Ex=" + e);
            return "";
        }
    }
    public static final String SSO_GLOBAL_VARS = "SSOGlobalVars";//SSO_MULTITENANCY_CHANGES
    public static final String GLOBAL_VARS = "Globalvar";	//REST_MULTITENANCY_CHANGES
    // BB-20150108-238 Starts
    public static final String SPROUT_KEY="soroutKey";
    public static final String SSO_CONFIG_VALUE="ssoconfigValue";
    public static final String SSO_CONFIG_KEY="ssoconfigKey";
	public static final String THIRD_PARTY_SSO = "thirdPartySso";
	public static final String CONFIG_NAME = "ssoconfigName";
	// BB-20150108-238 Ends
	// BB-20141230-235 Starts
    public static final String BASE_64_ENCODED_TOKEN = "base64EncodedToken";
	public static final String IS_ENABLE = "isEnable";
	public static final String SAML_IDP_ID = "samlIdPID";
	public static final String IDP_URL = "idpURL";
	public static final String ISSUER_URL = "issuerURL";
	public static final String DESTINATION_URL = "destinationURL";
	public static final String RECIPIENT_URL = "recipientURL";
	public static final String FAILED_ASSERTION_URL = "failedAssertionURL";
	public static final String USERNAME = "username";
	public static final String REQUEST_LACK_TIME = "requestLackTime";
	public static String SMG_HIERARCHY = "smgHierarchy";
	public static String REQUEST_LOGGING = "requestLogging";
	public static String TOKEN_PATH = "tokenPath";
	public static String CERTIFICATE_PATH = "certificatePath";
	public static String KEYSTORE_PATH = "keystorePath";
	public static String KEYSTORE_PASSWORD = "keystorePassword";
	public static String KEY_ALIAS = "keyAlias";
	public static String KEY_PASSWORD = "keyPassword";
	public static final String SSO_KEY = "ssoKey";
	public static final String FIELD_TYPE = "fieldType";
	public static final String FIELD_VALUE = "fieldValue";
	public static final String JS_FUNCTION = "jsFunction";
	public static final String INTEGRATION_KEY = "integrationKey";
	public static final String SUPPORTED_MODULE = "supportedModule";



	// BB-20141230-235 Ends
	
	//ENH_PW_ALERT_NOTIFICATION Starts
	public static final String FROM_EMAIL_ALERT = "fromEmailAlert";
    public static final String ALERT_EMAIL_CONTENT = "alertEmailContent";
    public static final String ALERT_FREQUENCY = "alertFrequency";
    public static final String ALERT_ADDED_DATE = "alertAddedDate";
    public static final String EXPECTED_COMPLETION_DATE = "expectedCompletionDate";
    public static final String STANDARD = "standard";
    public static final String OBSERVATION = "observation";
    public static final String TASK_LEVEL = "taskLevel";
  //ENH_PW_ALERT_NOTIFICATION Ends
    
    
    //BB-20150501-351 starts
    public static final String NACHA_FILE_FORMAT_ID = "nachaFileFormatId";
    public static final String BALANCED_BATCH_FILE = "balancedBatchFile";
    public static final String BATCH_OPTION = "batchOption";
    public static final String BATCH_OF = "batchOf";
    public static final String IS_SYSTEM_FORMAT = "isSystemFormat";
    public static final String FORMAT_DETAILS_ID="formatDetailsId";
    public static final String ELEMENT_NAME="elementName";
    public static final String START_POS="startPos";
    public static final String END_POS="endPos";
    public static final String IS_READ_ONLY="isReadOnly";
    public static final String HAS_DESCRIPTION="hasDescription";
    public static final String PADDING_TYPE="paddingType";
    public static final String PADDING_CHAR="paddingChar";
    public static final String POS_RANGE="posRange";
    public static final String STARTING_POINT="startingPoint";
    public static final String FILE_MAX_LENGTH="fileMaxLength";
    public static final String DETAILS_ID="detailsId";
    public static final String FIELD_TYPE_ID="fieldTypeId";
    public static final String PADDING_CHAR_ACTUAL="paddingCharActual";
    public static final String PADDING_TYPE_ID="paddingTypeId";
    //BB-20150501-351 ends
    //ENH_PW_ALERT_NOTIFICATION Ends
    //ZCUB-20150410-136 Start : Field names has been added against UPS changes.
    public static final String COUNTRY_ABBREV = "countryAbbrev";
    public static final String BILLING_STATE_ABBREV = "billingStateAbbrev";
    public static final String SHIPPING_STATE_ABBREV = "shippingStateAbbrev";
    public static final String BILLING_COUNTRY_ABBREV = "billingcountryAbbrev";
    public static final String SHIPPING_COUNTRY_ABBREV = "shippingcountryAbbrev";
    public static final String THRESHOLD_WEIGHT = "thresholdWeight";
    public static final String SHIPPING_LABEL = "shippingLabel";
    //ZCUB-20150410-136 End    
    //ENH_PW_ALERT_NOTIFICATION Ends
    public static final String LAST_ACTIVITY_DATE="lastActivityDate"; //ZCUB-20150515-147
    
    //BB-20150430-350 starts
    public static final String TAX_TYPE_NAME = "taxTypeName";
    public static final String TAX_RATE_NAME = "taxRateName";
	public static final String TAX_TYPE_ID = "taxTypeId";
	public static final String TAX_RATE_ID = "taxRateId";
	public static final String TOTAL_TAX_RATE = "totalTaxRate";
	public static final String FRANCHISE_TAX_RATE_ID = "franchiseTaxRateId";
	public static final String FIN_SALES_TAX_ID = "finSalesTaxId";
	public static final String OPEN_BAL_TAX = "openBalTax";
	
	public static final String TRANSACTION_CODE_TAX = "transactionCodeTax";
    public static final String RECEIVING_DFI_ID_TAX = "receivingDfiIDTax";
    public static final String CHECK_DIGIT_TAX = "checkDigitTax";
    public static final String DFI_ACCOUNT_NO_TAX = "dfiAccountNoTax";
    public static final String INDIVIDUAL_ID_NO_TAX = "individualIDNoTax";
    public static final String INDIVIDUAL_NAME_TAX = "individualNameTax";
    public static final String ADDL_TAX_AMOUNT = "addlTaxAmount";
    public static final String ENABLE_TAX_RATES = "enableTaxRates";
    public static final String BALANCED_CONTROL_RECORD = "balancedControlRecord";
    //BB-20150430-350 ends

    public static final String CATEGORY_MAPPING_ID="mappingId";	//ZCUB-20150519-148		//ZCUB-20150519-148
    public static final String VENDASTA_BUSINESS_CATEGORY_NAME="vendastaBusinessCategoryName"; //ZCUB-20150519-148
    public static final String REPUTATION_REVIEW_URL_DATA_TYPE="214";//ZCUB-20150408-129
    public static final String KILLED_REASON_ID   = "killedReasonID";//CUSTOM_REPORT_SORTING_ISSUE
    
    //BB-20150713-372 starts
    public static final String POS_FTP_THREAD   = "posFtpThread";
    public static final String POS_FTP_CYCLE   = "posFtpCycle";
    public static final String POS_FTP_CYCLE_PARAM1   = "posFtpCycleParam1";
    public static final String POS_FTP_CYCLE_PARAM2   = "posFtpCycleParam2";
    public static final String POS_FTP_FORMAT   = "posFtpFormat";
    public static final String PROTOCOL_TYPE   = "protocolType";
    public static final String ERROR_EMAIL   = "errorEmails";
    //BB-20150713-372 ends
    
    
    
    // CRM_OPTIMIZATION Starts
    
    public static String IS_PARENT="isParent";
    public static String PARENT_ACCOUNT_ID="parentAccountID";
    public static String PARENT_ACCOUNT_NAME="parentAccountName";
    public static String EMAILS="emails";
    
    public static String OPPORTUNITY="opportunity";
    public static String OPPORTUNITY_ID="opportunityID";
    public static String OPPORTUNITY_NAME="opportunityName";
    public static String OPPORTUNITY_OWNER="opportunityOwner";
    public static String OPPORTUNITY_OWNER_ID="opportunityOwnerId";
    public static String OPPORTUNITY_STAGE="stage";
    public static String OPPORTUNITY_TYPE="opportunityType";
    public static String OPPORTUNITY_SUB_TYPE="opportunitySubType";
    public static String OPPORTUNITY_SOURCE="opportunitySource";
    public static String OPPORTUNITY_ADD_DATE="opportunityAddDate";
    public static String OPP_CONTACT_ID="oppContactID";
    public static String OPP_ACCOUNT_ID="oppAccountID";
    public static String CHILD_ACCOUNT_IDS="childAccountIds";
    public static String ASSOCIATED_WITH="associatedWith";
    public static String ACCOUNT_EXISTING_ID="accountExistingID";
    public static String ACCOUNT_EXISTING_NAME="accountExistingName";
    public static final String RATING_ID = "ratingID";
    // CRM_OPTIMIZATION Ends
    //BB-20150806-376 starts
    public static String INCLUDE_SUB_CATEGORY = "includeSubCategory";
    public static String MINMAX_TYPE = "minMaxType";
    public static String AGREEMENT_RULE_MAPPING_ID = "agreementRuleIdMapping";
    public static String FEE_TABLE_ID = "feeTableId";
    public static String MIN_MAX_TYPE = "minMaxType";
    public static String CALCULATION_FLAG = "calculationFlag";
    public static String CALCULATION_TYPE = "calculationType";
    //BB-20150806-376 ends
    public static String LAST_CONTACTED_FIELD="lastContactedField";
    public static final String RECOMMENDED_EXP_DATE = "recommendedExpDate";   //ENH_INT_RECOMMEDED_EXP_DATE
    
    //ZCB-20150818-116:PayPal Integration starts
    public static final String LIVE_OR_SANDBOX = "liveOrSandbox";
    public static final String MERCHANT_USER_NAME = "merchantUserName";
    public static final String MERCHANT_PASSWORD = "merchantPassword";
    public static final String MERCHANT_SIGNATURE = "merchantSignature";
    //ZCB-20150818-116 Ends

    //GHS-20140806-006 starts
	public static final String TRIM_SIZE = "trimSize"; 
	public static final String PAPER_COATING = "papercoating";
	//GHS-20140806-006 ends
	
	//P_ENH_DOCUSIGN
	public static final String DOCUSIGN_EMAIL ="docusignEmail";
	public static final String DOCUSIGN_PASSWORD ="docusignPassword";
	public static final String DOCUSIGN_INTEGRATION_KEY ="docusignKey";
	public static final String DOCUSIGN_RETURN_PAGE ="docusignReturnPage";
	public static final String DOCUSIGN_SERVER_URL ="docusignServerUrl";
	//P_ENH_DOCUSIGN
	
	public static String GROWTH_BASED = "growthBased";
	public static String FORMULA = "formula";
	public static String AREA_FORMULA = "areaFormula";
    //ZCUB-20150410-137 Start : Field names has been added against UPS / Fedex changes.    
    public static final String FEDEX_METER_NUMBER="meterNumber";
    public static final String FEDEX_KEY_VALUE="key";
    public static final String FEDEX_ENABLED="fedexEnabled";
    public static final String FEDEX_SHIPPER_ID="shipperID";
    public static final String FEDEX_ONLINE_FUNCTIONALITY = "fedexOnline";
    public static final String FEDEX_PASSWORD="password";
    public static final String FEDEX_CONFIRM_PASSWORD="confirmPassword";
    public static final String DOMESTIC_THRESHOLD_WEIGHT = "domesticThresholdWeight";
    public static final String WAREHOUSE_PICKUP = "warehousePickUp";
    public static final String SHIPPER_ID = "shipperID";
    public static final String SELECTED_SERVICE = "selectedService";
    public static final String RATE_SERVICE_ENDPOINT_LIVE_URL = "rateServiceEndpointLiveUrl";
    //ZCUB-20150410-137 End

    public static final String NOTIFICATION_NO="notificationNO";//INH_GLOBAL_POPUP_NOTIFICATION 

    public static final String SALES_TAX = "salesTax";//ZCUB-20151124-201
    public static final String SHIPPING_TAX= "shippingTax";//ZCUB-20151124-201

    public static final String SALES_TAX_AMOUNT = "salesTaxAmount";//ZCUB-20151124-201
    public static final String SHIPPING_TAX_AMOUNT= "shippingTaxAmount";//ZCUB-20151124-201
    
    //P_FIN_ENH_LATE_FEE STARTS
    public static final String IS_LATE_FEE = "isLateFee";
    public static final String FEE_TYPE = "feeType";
    public static final String ENABLE_LATE_FEE = "enableLateFee";
    
    //P_FIN_ENH_LATE_FEE ENDS
    public static final String YEARLY_CAP_ID = "yearlyCapId";		//P_FIN_ENH_YEARLY_CAP
    
    public static final String START_OF_WEEK = "startOfWeek";		//tintwo-20160316-284
    
    public static final String CAN_SITE_APPROVAL_EMAIL_SEND = "canSiteApprovalEmailSend"; //P_Enh_Site_Clearance
  //P_Enh_ContactHistory_FormBuilder starts
    public static final String DOCUMENT_ATTACHMENT = "documentAttachment";
	public static final String CALENDAR_TASK = "calendarTask";
	//P_Enh_ContactHistory_FormBuilder ends
	
	public static final String LEAD_DUPLICATE_CRITERIA="leadDuplicateCriteria";
	public static final String LEAD_FIRST_NAME="leadFirstName";
	public static final String LEAD_MIDDLE_NAME="leadMiddleName";
    public static final String LEAD_LAST_NAME="leadLastName";
    public static final String LEAD_ADD_DATE="leadAddDate";
    public static final String LEAD_ADDED_BY="leadAddedBy";
    public static final String CONVERTED_BY="converted_by";
    public static final String CONVERTED_ON="converted_on";
    public static final String LEAD_COMPANY_NAME="companyName";
    public static final String RESULT_PER_PAGE="resultsPerPage";
    
    public static final String FBC = "fbc"; //ENH_PW_FBC
    //PW_ENH_CAP_REPORT Starts
	public static final String CAP_REPORT = "capReport";
    public static final String CORRECTIVE_ACTION = "correctiveAction";
    public static final String CAP_COMPLETION_DATE = "capCompletionDate";
    public static final String CORRECTIVE_ACTION_STATUS = "correctiveActionStatus";
    public static final String RISK = "risk";
    //PW_ENH_CAP_REPORT Ends
    public static final String QUESTION_NUMBERING = "questionNumbering";  //PW_ENH_QUESTION_NUMBERING
    
  
   
    public static final String IS_DIVISION_CONFIGURED="isDivisionConfigure";
    public static final String DIVISION_BASED_ON="divisionBasedOn";
    public static final String DIVISION_LABEL="divisionLabel";
    public static final String DIVISION_USER_LABEL="divisionUserLabel";
    public static final String DIVISION_USER_ABBR="divisionUserAbbr";
    public static final String EXCLUSIVE_STATES="exclusiveState";
    public static final String ALLOW_MULTIPLE_DIVISION="allowMultipleDivision";
  
    
    public static final String IS_ICAL_MAIL="isICALMail";
    public static final String SHOW_COLOR_BOX="showColorBox";
    public static final String ATTACH_AUDIT_FORM="attachAuditForm";

    public static final String DATES = "dates";
	public static final String BLACKOUT_END_DATE = "blackOutEndDate";
	public static final String BLACKOUT_START_DATE = "blackOutStartDate";
	public static final String BLACK_OUT_NAME = "blackOutName";
	public static final String BLACKOUT_ID = "blackOutId";
	public static final String BLACK_OUT_EVENT = "blackOutEvent";
	public static final String ACTION_MENU_SCRIPT="actionnMenuScript";

	public static final String POST_MANIPULATOR = "postManipulator";
	public static final String POST_PROCESSING_CLASS = "postProcessingClass";
	
	public static final String CAMPAIGN_APPROVAL_REQUEST="campaignApprovalRequest";
	public static final String IS_BLACKOUT_ENABLED="isBlackoutEnable";
	
	public static final String IS_ACCEPTED="isAccepted";
	
    public static final String THEME_NAME="themeName";//Mobile_Enh_9.3_Themes

    public static final String MIN_ORDER_QUANTITY="minOrderQuantity";
    public static final String MAX_ORDER_QUANTITY="maxOrderQuantity";
    public static String LBL_HANDLING_CHARGES="Handling Charges";
    public static String PAYMENT_APPROVAL_KEY="100332";//ZCUB-20151208-210
    public static String NEED_PAYMENT_APPROVAL="needPaymentApproval";//ZCUB-20151208-210
    public static String SALES_TAX_PERCENTAGE="salesTaxPercentage";//ZCUB-20151124-201
    public static String SHIPPING_TAX_PERCENTAGE="shippingTaxPercentage";//ZCUB-20151124-201
    public static String ATTRIBUTE_VALUE="attributeValue";
    public static String ATTRIBUTE_ID="attributeID";
    public static String DATE_SUBMISSION="dateSubmission";//P_ENH_DATE_SUBMISSION
    public static final String PURCHASESUBORDER_NO="purchaseSubOrderNo";
    public static final String TAB_NAME_DISPLAY="tabNameDisplay";

    public static final String VENDOR_ORDER_STATUS_ID="vendorOrderStatusId";
    public static final String STORAGE_KEY="storageKey";
    public static final String MAIN_FILE = "mainFile";
    public static final String UPLOAD_FILE = "uploadFile";
    public static final String OLD_FILE = "oldFile";
    
    
    
    public static final String S_NO="sNo";
    public static final String FEATURE="feature";
    public static final String SECTION="section";
    public static final String DEFAULT_SETTING="defaultSetting";
    public static final String CLIENT_SPECIFIC_CHANGE_REQUIRED="clientSpecificChange";
    public static final String ONLY_DB_CONFIGURABLE="dbConfigurable";
    public static final String COMENTS="coments";

    public static final String IS_AD_USER = "isADUser";  //BB-20151201-455    
    
    public static final String IS_FRAN_VISIT = "isFranVisit";
    
    public static final String FRAN_CAN_CREATE_VISIT = "franCanCreateVisit";
    public static final String INCLUDE_VISIT = "includeVisit";
    public static final String VISIT_COMPLETION_EMAIL = "visitCompletionMail";
    
    public static final String NUMBER_OF_LOCATIONS="numberOfLocations";//ZCUB-20160310-239
    /*P_ENH_CRITICALITY_LEVEL_ALERTS_STARTS*/
    public static final String CRITICAL_LEVEL_ID = "criticalLevelId";
	public static final String DEPENDENCY_FLAG = "dependencyFlag";
	public static final String FROM_FO = "fromFO";
	public static final String SCHEDULE_COMPLETION ="scheduleCompletion";
	public static final String SCHEDULE_START="scheduleStart";
	public static final String OVERDUE_DAYS="overdueDays";
    /*P_ENH_CRITICALITY_LEVEL_ALERTS_ENDS*/
	public static final String RM_EMAIL_NOTIFICATION_ID="320";//ZCUB-20160310-239
	public static final String EMAIL_NOTIFICATION_JSON_ID="321";//ZCUB-20160310-239
	public static final String LEAD_SOURCE_LABEL = "Lead Source";

	//ZCUB-20150609-155 starts
    public static final String EMAIL_READ="emailRead";
    public static final String BOUNCED="bounced";
    public static final String OPTED_OUT="optedOut";
    public static final String SUBSCRIBER="subscriber";
    public static final String TOTAL_MAIL_SENT="totalMailSent";
    public static final String SOFT_BOUNCED="softBounced";
    public static final String DELIVERED="delivered";
    
    //ZCUB-20150609-155  ends

    public static final String ENABLE_SMS_NOTIFICATION = "enableSmsNotification";	//P_E_SMS_NOTIFICATION
    public static final String SMS_TITLE = "smsTitle";
    public static final String CAMPAIGN_SMS_INTERVAL = "campaignSMSInterval";
  
    //ZCUB-20160421-245 start
    public static final String LINK_MESSAGE = "linkMessage";
    public static final String INTERNAL_EXTERNAL = "internalExternal";
    public static final String DISCLAIMER_TITLE = "disclaimerTitle";
    public static final String DISCLAIMER_TYPE = "disclaimerType";
    public static final String DISCLAIMER_TYPE_VALUE = "disclaimerTypeValue";
    //ZCUB-20160421-245 ends
    
    //CUSTOMIZE_FIELD_IN_SHOP_PRINT_ADS start
    public static final String AVAILABLE_ON_PO = "availablePO";
    public static final String AVAILABLE_ON_POM = "availablePOM";
    public static final String AVAILABLE_ON_CSV = "availableCSV";
    public static final String AVAILABLE_ON_XML = "availableXML";
    public static final String VALIDATION = "fldValidationType";
    public static final String MAPPING_FIELD_NAME = "mappings";
    public static final String XML_MAPING = "xmlMapping";
    public static final String NUMBER = "number";
    //CUSTOMIZE_FIELD_IN_SHOP_PRINT_ADS ends
    
    public static final String GROUP_FOR = "groupFor";
	public static final String SETTING_FOR = "settingFor";
	public static final String VIEW_BY = "viewBy";

	/*CM_POS_CHANGES Starts*/
	public static final String FTP_PORT = "ftpPort";
	public static final String FILE_TYPE = "fileType";
	public static final String THREAD_TIME = "threadTime";
	/*CM_POS_CHANGES Ends*/	
	public static final String LEAD_TYPE="leadType";//ZCUB-20151124-197
        
        public static final String SEND_GRID_BOUNCE = "sendGridBounce";
        public static final String SEND_GRID_SPAM = "sendGridSpam";
        public static final String SEND_GRID_BOUNCE_REASON = "sendGridBounceReason";
        
        public static final String ADMIN_CONTACT = "adminContact";
        
        public static final String SINGLE_ITEM_WEIGHT ="singleItemWeight";
        public static final String DEFAULT_SHIPPING_OPTIONS ="defaultShippingOptions";//P_Enh_ShippingOptionConfiguration
        public static final String DEFAULT_SHIPPING_LABEL ="defaultShippingLabel";//P_Enh_ShippingOptionConfiguration
        
        public static final String BILLING_CONTACT_FIRST_NAME ="billingContactFirstName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String BILLING_CONTACT_LAST_NAME ="billingContactLastName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String SHIPPING_CONTACT_FIRST_NAME ="shippingContactFirstName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String SHIPPING_CONTACT_LAST_NAME ="shippingContactLastName";//locations first name last name inhancement ends ZCUB-20160421-245
       
        public static final String BILLING_FIRST_NAME ="billingFirstName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String BILLING_LAST_NAME ="billingLastName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String SHIPPING_FIRST_NAME ="shippingFirstName";//locations first name last name inhancement ends ZCUB-20160421-245
        public static final String SHIPPING_LAST_NAME ="shippingLastName";//locations first name last name inhancement ends ZCUB-20160421-245
       
        public static final String CM_OPPORTUNITY_ID ="cmOpportunityID";
        public static final String CM_OPPORTUNITY_NAME ="cmOpportunityName";
        
        public static final String CM_STAGE_ID="cmStageID";
        public static final String CM_STAGE_NAME="cmStageName";
        public static final String IS_ASSOCIATED="isAssociated";
        public static final String BRAND_REGION_LEVEL_ID="322";
        
        public static final String SMS_ID = "smsId";
        public static final String SMS_MESSAGE = "smsMessage";
        
        //code for packages starts ZCUB-20160421-245
        public static final String SUPPLIER_PACKAGE_ID = "supplierPackageId";
        public static final String PACKAGE_ID = "packageId";
        
        public static final String PACKAGE_NAME = "packageName";
        public static final String PACKAGE_DESCRIPTION = "packageDescription";
        public static final String MODIFIED_USER_NO = "modifiedUserNo";
        public static final String PACKAGE_ACCESSIBILITY = "packageAccessibility";
        public static final String PRODUCT_COUNT = "productCount";
        public static final String QUANTITY_TEXTBOX = "quantityTextBox";
        public static final String SUPPLIER_PACKAGE_PRODUCT_ID = "supplierPackageProductId";
        public static final String PRODUCT_QUANTITY = "productQuantity";
        public static final String ITEM_SIZE = "itemSize";
        public static final String ITEM_COLOR = "itemColor";
        //code for packages ends ZCUB-20160421-245
        public static final String BI_REPORT_URL = "biReportURL";
        //P_E_FS_SMS_SUBSCRIPTION
        public static final String SMS_ENABLED = "smsEnabled";
        public static final String SMS_SUBSCRIPTION_STATUS = "smsSubscriptionStatus";
        public static final String SUBSCRIPTION_DATE = "subscriptionDate";
        public static final String COUNTRY_CODE = "countryCode";
        
        public static final String MILESTONE_LABEL = "milestoneLabel";
        public static final String MILESTONE_DATE_ID ="milestoneDateId";
        
        public static final String SCHEDULE_START_DATE = "scheduleStartDate";
        public static final String SCHEDULE_COMPLETION_DATE = "scheduleCompletionDate";
        
        public static final String TOTAL_SMS_SENT="totalSmsSent";
        public static final String PENDING_SMS="pendingSms";
        public static final String SENT_SMS="sentSms";
        public static final String FAILED_SMS="failedSms";
        public static final String UNDELIVERED_SMS="undeliveredSms";

        public static final String TOTAL_SMS_SENT_LEADS="totalSmsSentLeads";
        public static final String PENDING_SMS_LEADS="pendingSmsLeads";
        public static final String SENT_SMS_LEADS="sentSmsLeads";
        public static final String FAILED_SMS_LEADS="failedSmsLeads";
        public static final String UNDELIVERED_SMS_LEADS="undeliveredSmsLeads";
        public static final String TOTAL_REPLY_LEADS="totalReplyLeads";
        
        
        public static final String TOTAL_REPLIES="totalReplies";
        public static final String MULTITASK_COMBO = "multitaskCombo";
        public static final String SMS_LIMIT="smsLimit";
        
        public static final String ASSIGNMENT_TYPE="assignmentType";

        public static final String CATEGORY_LEVEL="categoryLevel";
        public static final String PLREP_DETAILS_ID = "plReportDetailsID";
        
        public static final String PENDING="pending";
        //CUSTOMIZE_FIELD_IN_SHOP_adbuilder starts
        public static final String CUSTOM_FIELD1 = "customField1";
        public static final String CUSTOM_FIELD2 = "customField2";
        public static final String CUSTOM_FIELD3 = "customField3";
        public static final String CUSTOM_FIELD4 = "customField4";
        public static final String CUSTOM_FIELD5 = "customField5";
      //CUSTOMIZE_FIELD_IN_SHOP_adbuilder ends
      //MKT-411 starts
        public static final String CUSTOM_FIELD6 = "customField6";
        public static final String CUSTOM_FIELD7 = "customField7";
        public static final String CUSTOM_FIELD8 = "customField8";
        public static final String CUSTOM_FIELD9 = "customField9";
        public static final String CUSTOM_FIELD10 = "customField10";
      //MKT-411 ends
        public static final String WORK_FLOW_NAME="workFlowName";
        public static final String WORK_FLOW_DESCRIPTION="workFlowDescription";
        public static final String WORK_FLOW_TYPE="workFlowType";
        public static final String WORK_FLOW_ID="workFlowId";
        public static final String BASED_ON="basedOn";
        public static final String ACTION_TYPE_ID="actionTypeId";
        public static final String ACTION_ACTIVITY_ID="actionActivityId";
        public static final String EXECUTE_ON_EVENT="executeOnEvent";
        public static final String ALL_IMAGES="allImages";//ZCUB-20160722-273
        public static final String SMS_OPT_IN="smsOptIn";//P_Bug_74343
        public static final String RESPONSIVE_EMAIL="responsiveEmail";
    	public static final String REACH="reach";//ZCUB-20160503-258 starts
        public static final String SPENT="spent";
        public static final String RESULTS="results";
        public static final String COST_PER_RESULT="costPerResult";        
        public static final String PPC_EMAIL_ENABLE_ID="100501";//ZCUB-20160503-258 ends

        public static final String ONE_LINE_PACKAGE_DESCRIPTION="oneLinePackageDescription";//ZCUB-20160722-273
        public static final String PACKAGE_NO="packageNo";//ZCUB-20160722-273
        public static final String PACKAGE_PRICE="packagePrice";//ZCUB-20160722-273
        	
        
        public static final String DPI_VALUE="dpiValue";
        public static final String PIXEL_VALUE="pixelValue";
        public static final String IS_PACKAGE="isPackage";//ZCUB-20160722-273
        public static final String MIN_QUANTITY="minQuantity";//ZCUB-20160722-273
        public static final String PACKAGE_TYPE="packageType";//ZCUB-20160722-273
        public static final String AREA_REGION="areaRegion";
        public static final String ECOM_REQUEST_ID ="ecomRequestID";//ZCUB-20160808-278:ETS INTEGRATION
        public static final String ECOM_RESPONSE_ID ="ecomResponseID";//ZCUB-20160808-278:ETS INTEGRATION
        
        public static final String IOS_URL = "iosUrl";
        public static final String ANDROID_URL = "androidUrl";
        public static final String IOS_VERSION = "iosVersion";
        public static final String ANDROID_VERSION = "androidVersion";
        public static final String APP_DISPLAY_NAME = "appDisplayName";

        public static final String SHIPPING_CHARGES_TYPE = "shippingChargesType";
        public static final String SHIPPING_CHARGES_VALUE = "shippingChargesValue";
        public static final String CUSTOM_SHIPPING = "customShipping";
        public static final String SUPPLIER_CUSTOM_SHIPPING_ID = "supplierCustomShippingId";
        public static final String SUPPLIER_CUSTOM_SHIPPING_NAME = "supplierCustomShippingName";
        public static final String RECIPIENT_ID = "recipientId";
        public static final String SMODULE_NAME = "sModuleName";
        public static final String CUSTOMIZE = "customize";
        public static final String EXECUTE_ON_ALL_RECORD = "executeOnAllRecord";
        
        public static final String VZAAR_UPLOAD = "vzaarUpload";
        public static final String VZAAR_VIDEO_ID = "vzaarVideoId";
        public static final String VZAAR_STATUS = "vzaarStatus";
        public static final String PACKAGE_MAX_ORDER_QUANTITY="packageMaxQrderQuantity";
        public static final String PACKAGE_MIN_ORDER_QUANTITY="packageMinQrderQuantity";
        public static final String  PACKAGE_MIN_QUANTITY="packageMinQuantity";
        public static final String SMART_GROUP_SEARCH_QUERY="smartGroupSearchQuery";
        
        public static final String FOR_PACKAGE="forPackage";
        public static final String SOLE_SALE="soleSale";
        public static final String WEIGHT_WITHOUT_QUANTITY="weightWithoutQuantity";
        public static final String DIMENSION_TYPE="dimensionType";
        public static final String FROM_NUMBER_DATA="fromNumberData";
        public static final String SUB_TOTAL="subTotal";
}
