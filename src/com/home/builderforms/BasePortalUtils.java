/**
printPurchaseOrderDetailsReport.jsp * Copyright (c) 2001  Webrizon eSolutions Pvt Ltd
 * B-31, Sector 5, NOIDA. 201301, India.
 * All Rights Reserved
 * @updated by: Abhishek Chawla
 * @version 1.2,
 * date 29/March/2004
 * Comments := Modification in  getUserDetails function
 * @updated by: Neha Bhagat
 * dated Nov 4,2005
 * for Bug No:10512
 * updated by gaurav mehta
 * dated 20 Feb, 06
 * getOwnersIdCombo function changed to get owner user , earlier owners are coming
 * P_INTRANET_B_62994 1 sep 2010 mukesh sundriyal for phone extension field
 * P_FS_B_981    31 July 2012  Aakash Maheshwari  for virtual brochure activity Report
 */

/*
 ------------------------------------------------------------------------------------------------------------
 Version No.                     Date        By      			Against              Function Changed    Comments
 ------------------------------------------------------------------------------------------------------------
 D_Adm_O_USERS_ 2006091110000022  13/12/2006 Saurabh   			2006091110000022      getLoginId()
 DEMO_INTRA_E_2112060001			 21/12/06	Siddharth Singh		Req. change			  using PortalUtils.changeDateFormatone() for new date format

 D_E1.1			05/01/2007 	santanu		added getRateName()
 DB_625_CT_MIG				23Dec2009		YogeshT			CT migration from basebuild625, add product fetire in product / service combo removed.
P_INTRANET_B_64323                 28/12/10    modified by Himanshi Gupta         Phone Extension Field is coming blank when contact with same name & email id is added again
P_INTRANET_B_7758                 22/6/2012   Megha Chitkara          changed to show details of the user when message is compose
P_CM_B_44309                20 Aug 2014    swati garg           In contact.jsp page, category of other franchise id were visible for specific location of a franchise user.
 */

/*
 * Function Added: getSourceDetailsForCategory()
 * Date: 12/26/2006
 * Author: Vinay Bindal
 * Description: This method display source details of the respective source category being selected.
 * @Param: String sourceId
 * @Returns: ArrayList
 */
/*
 -------------------------------------------------------------------------------------------------------------------------------
 Version No.		Date		By          Against		Function Changed          Comments
 --------------------------------------------------------------------------------------------------------------------------------
 P_FIM_E_FILTERS1.1         09/02/07	Sandeep     Req. change         getAllCity()               --  
 D_INTRA_B_23193	13/03/07	Rakesh	Create Method to get Complete Story.		getCompleteStrory()	
 P_NEWS_B_23762	 26MARCH2007	YADUSHRI   Bugzilla 23762                           To change the window pattern
 P_SERCH_B_23632   2APRIL2007    YADUSHRI     23632                             seacrh for Acrhived News has been added               
 ----------------------------------------------------------------------------------------------------------------------------

 -------------------------------------------------------------------------------------------------------------------------------
 Version No.				Date	    	By	              Against	        	Function Changed          Comments
 ---------------------------------------------------------------------------------------------------------------------------------------------------------------
 D_S_B_23273	           16-April-07		Vivek Sharma   Basebuild Issue     added new function getUserNameNew corresponding to the changes in SupportFaqDAO.java
 P_FS_B_24572	       5/9/2007			Ravindra verma  Basebuild Issue     Adding (RU) with Regioncal Users.
 P_FM_B_25505  			10/07/07	    Vivek Sharma   	getAllUsersCombo()	Deactivated and Deleted Users shud not be Displayed in combo.
  P_Admin_E_Ftp101			13/08/07	Rakesh Verma   	Ftp Enhacement	getDeployedBuildName(),updateFtpServerCofiguration()
 ,getConfiguredHostName(),getFtpServerDetails()

 P_FM_B_26619  			08/17/2007	    Abhishek   	 Improper space in country   getCountryName()		Allow space between country name
 P_FM_STORES  			08/21/2007	    Abhishek   	 To get all stores           getStores()		    Add function to getting all stores exist in STORE_TYPE table
 08/22/2007	    Abhishek   	 To get selected storename   getStoreName()		    Add function to get store name of selected id exist in STORE_TYPE table

 P_FM_All_STORES  		09/11/2007	    Abhishek	  Get all stores names		 getAllStoresForFin()	This Method Retrieve the list of Stores with thier name in combo type

 ENH_STORETYPE			09/13/2007	    ABHISHEK    Result with store type		 getAllStoresForString()	Add function to get all store type exist

 ENH_STORETYPE			09/17/2007	    ABHISHEK    Result with store type		 getAllStoreTypeListForFin()	Add function to get result with selected/all store type for given area(s)

 ENH_STORETYPE			09/18/2007	    ABHISHEK    Result with store type		 getStoreTypeForFranFin()	Add function to get result with selected/all store type for given frnachise(s)

 ENH_STORETYPE			09/24/2007	    ABHISHEK    Result with store type		 getAllFranchiseesListForStoreFin()	Add function to get franchise(s) of store type 

 ENH_STORETYPE			09/27/2007	    ABHISHEK    Get area id for store type		 getAreaDetailByStoreFin()	Add function to get area of store type 

 BUG ID 30906			01/25/2008	    ABHISHEK    Getting only regional store  getAllStoresForFin()	   	Override this function so that get regional stores when region login

 P_TRAINING_B_32885  	10/03/08	   Suchita Agrawal   	filterValue()	for /r and /f
 BB_FIN_Bug_30898		23Jan2008		YogeshT			BUG			getStoreTypes();
 
 F_Phone_formating     17 july ,2008    Sanjeev k    For format date if length greter than 10
 P_BUG_37741           28 july 08       Niraj sachan  getUserDetails()     BaseBuild Bug         Phone No, Mob, Fax will come in format in the case of US only
 
 P_FIM_B_37767         28 july 2008     Sunilk 			Bug-37767					getAddress            Change the query and add left joins.
 P_SO_E_STORE_NO_FILTER 11 SEP 2008     Ram Avtar     searchStore() 									added functionality for store no filter for Store Summary and Archive
BB_FIN_E_getComboByInfo		09Sep2008	YogeshT		--				added methods getComboByInfo(...), getMonthsForCombo(), getYearsForCombo()
BB_ENH_OVERLOADED		07Oct2008		YogeshT		--				overloaded method getAreaCombo() to support size and customized combo name. 
BB_CT_REPORT_ARA		10 Oct 2008		YogeshT		--				getAllAreas()			updated if condition.
BB_ENH_11Nov08			13Nov2008		YogeshT		--				--						-added method getMaxIntKey() to get unique sequential key for given long/Integer type column.
Bug_34094               28/04/2008    Neha Rusiya                                            initiate invoice  String getRegionNamesCSV()
                                                                                             Added a if block 2 check the value whether it returns is greater than 0 as well as not equal to null
BB_B_39568               12-11-2008  Dheerendra                                               Changes regarding bug id --- 39568 and 40841
BB_FIN_EXCH_MODIFY_BULK 25Nov2008   Dheerendra                    getModifyInformation()      - added new method

BB_E_MOVE_SAI_TO_ADD	25Nov2008		YogeshT		--				--						-added method getFranchiseeForAddAddress() and isFranchiseOwnerUser().
BB_bug_41373      		    1stDec2008	Dheerendra   	bug 41373						                        exception handled.
BB_BUG_ID -- 42216      29 Dec 2008     Dheerendra      bug 42216  

BB_BLOG_ENH             17March2009     Dheerendra                     getDateInfo                  getDateInfo   is overrided with parameter colspan.
 * P_SUPP_E_70002    19-Mar-2009      Saurabh Sinha     Supplier has option to send mail
P_FIM_E_AddComplaint   15/04/2009   Ram Avtar       getFranchiseeDetails() Add complaint Feedback page from outside build and related with FIM complaint tab
P_E_FIM_58658     10/07/2009  Nikhil Verma   	Task Trigger FIM enhancement.  getAuditFormatDate()
BB_FIM_B_53677    18/12/2009    Abhishek Rastogi     getAllUsersComboExceptFranconnectAdm()
P_FIM_B_53959    18/Dec/2009     Saurabh Sinha   Bug Resolved
D_Bug_50947		18Dec2009	YogeshT			getCustomerName			- corrected format name method used
DB_FIN_Bug_55048	04Jan2010	YogeshT		Bug_55048				-getAllArealistForFin()			-corrected return value as empty string.
P_E_NewDashBoard		18-Jan-2010          Ganesh   Enchancement                 	For New DashBoard
P_ADMIN_CR_001 		21-Jan-2010          Ankush Tanwar   Enchancement                 	For Red Flag Changes
DemoBuild_CR_FS_001  25-Jan-2010  Anuradha Tiwari   Private field changes for  Birthdate
P_B_FIM_56578     10/07/2009  Nikhil Verma   	Task Trigger FIM enhancement.  getAuditFormatDate()
P_SUPP_E_70028  31-Mar-2010 Saurabh Sinha   Filter "Assigned / Unassigned" combo defalut value changed from "Assigned" to "All" and For "Status" combo "Not Closed" value changed to "All But Not Closed"
P_FDD_Enh_13Apr10 13 apr 2010 Vikram Raj added getFddReceiptDetailsInfo & updateUfocInfo methods to generate and update the FDD reciept details of a lead
P_FS_Enh_16Apr10  16 Apr-2010  Vikram Raj    modified isVisitedMethod to update the table FS_LEAD_DETAILS
Theme Enhancement   26-04-10     Ankit saini     added getThemeName() for theme enhancement.
P_FS_E_762223   20May2010   Saurabh Sinha Corporate User has the ability of adm in all perspective
 P_ADMIN_CM_B_60469 21/07/2010        Ankit Saini
P_FDD_E_18082010    18/08/2010        Vikram Raj   added entries to get ufoc name,downloaded date in getFddReceiptDetailsInfo method and added getufocNamemehtod
P_FDD_E_22092010    22/09/2010         Vikram      added getFddDownloadReceiptDetailsInfo, modified getFddReceiptDetailsInfo and updateUfocInfo to send fdd for additional contacts
P_Laboit_CR_20100913-013               1st Oct'10  Akhil Sharma             Cr-LaBoit-20100913-013                                                      -added method for fim addrerss
P_B_Intra_69160		7 Jan 2011   Neeti Solanki               for displaying user details in respect of labels  
P_INTRA_B_69305       28/01/2011     Narotam                for bug 69305
P_FS_B_64844 		11Feb2011		Vivek Maurya		Bug						--getDateDisplayComboForSearch,getMonthDisplayComboInWordsForSearch		-added for FS>Export search
BB_Address_and_Phone_format_3_28	14-04-2011	Praveen Khare							
P_Admin_B_72737                    13/04/2011        Nishant Prabhakar            getUserName()                 Added if & else condition to avoid NullPointer Exception
P_FDD_B_72726                      14/04/2011        Nishant Prabhakar            getFddReceiptDetailsInfo()    Improved query to add mailtext in fddemail receipt
P_FRANONLINE_ENC           19/04/2011         Prakash Jodha               changes done in getMonthDisplayComboInWords getDateDisplayCombo
P_B_FS_74075    16 May 2011     Neeti Solanki         			for displaying the Lead Source Category instead of blank
P_B_FRANO_74587          23 May 2011    Neeti Solanki            for fitting the scraps and comments in the frame
DB_F_B_76154       21 june 2011  vishal singh              for converting us date formate to other
P_DASHBOARD_B_QueryOptimization	16Jun2011		Omindra Rana		Optimization
P_FIN_B_76687                   30 june 2011     Himanshi GUpta     background color was not coming on pdf for waiting for acknowledgement sales report
 * B_76471  01/07/2011  Narotam Singh
P_FRANBUZZ_B_74587           21 july 2011              PRAKASH JODHA         CHANGES DONE TO handle anchore tag in FRNBUZZ

CM_ENH_FOR_SUB_STATUS        25 august 2011            Nipun Gupta          added the method getCMLeadSubStatus()

P_FRANBUZZ_B_74587           03 AUG 2011              PRAKASH JODHA         CHANGES DONE TO APPLY CHK ON THE MOZILLA AND IE ISSUE OF <WBR>
P_E_NOTIFY    25 AUG 2011     Prakash Jodah    METHOD HAS BEEN OVERRIDE TO GET FRANCHISEE EMAIL ID TOO 
CAPTIVATE_42_POINTS   20/09/2011   Narotam Singh
P_B_ADMIN_1555        21 Nov 2011   Neeti Solanki                          for removing the exception
P_FRANBUZZ_LongText_Issue  24Nov 2011   Akhil Gupta   getFormattedLongText()  Changes made to solve text formatting issue 
P_B_HelpDesk_fetch-20111216-023 21/12/2011  Abishek Singhal		wbr tag issue with html code
P_ColorCode76854327    09-02-2012 Nishant Prabhakar            to color code dynamic 
P_FIM_B_4759          21 feb 2012              PRAKASH JODHA         method created to get multiple owners info of a franchisee location
P_CM_B_7190			23Mar2012		Vivek Maurya		Bug
P_SUPPLIER_ENH			21 Aug 2012		Dravit Gupta	Used to select value of combo.
P_Zcub_B_13153     07Sep2012      PRASHANT MALIK           13153          Modified Category Should Be Visible .
P_ENH_RATE_CARD               07 sept 2012       Banti Prajapati                                  rate card functionality
P_CM_B_13529       14Sep2012      PRASHANT MALIK   13529                               INCLUDE IN WEB PAGE
P_B_CT_14368            11 oct 2012     banti Prajapti      bug                        modified query
P_E_OxiFre-20110915-043	17 Oct 2012 	Yashukant Tyagi		Enh				Added Functions to get Merchantid and key on basis of franchise number
P_E_AdBuilder_00112233  26 Oct 2012     Divya Mishra        Enh             getAdBuildFtpServerDetails (This method is used to get details of Default Ftp Server in AdBuilder)
BBEH_FOR_GETRESULT_METHOD  24/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
P_E_JSpellSupplementalDictionary	11Jun2013		Vivek Maurya		Enh
PW_WITHOUT_PLAN				27 June 2013	Veerpal Singh		Removing concept of Visit Plans from Performance Wise (Now Visits will create directly with Audit Forms.)
P_FIM_B_25249			08 Aug 2013			Niranjan Kumar		applying filtervalue() to getAllCity()
P_FIM_B_25702			21 Aug 2013			Niranjan Kumar		Change in getAllUsersCombo
BBEH_INTRANET_SMC_OPTIMIZATION  30/07/2013      Rohit Jain    ajax apply for users combo for Smc Intranet.
P_CM_B_PhnFormat 25/09/2013     Sonalika                                  Added method to return plain unformatted value of phone no which is in US format.
P_B_FIN_29932		12/11/2013			Banti Prajapati			Bug				MUID
P_B_DISP_33779              10th Feb,2014    Prakriti                     MUID issue in Dispatch Board
P_CM_Enh_BuilderForm    11Feb2014       Dheeraj Madaan                  Form Builder in Contact Manager Module 
 P_OPT_CM_REPORTS : 12/03/2014 :            Chetan Rawat     Optimization of contact Detail report 
 DB_CACHE_OPTIMIZATION   24March2014    	Anu Gupta          User Data Cache Optimization 
 P_B_SCH_34534       16th April,2014      Teena Sharma                 issue all rate cards are being displayed even if the regions are different.
 P_LP_B_37629    8 May 2014 	Ramu Agrawal		 the sign up from builder is not same as customized.
 P_MS_B_39963    10 July 2014            Gagan Yadav             UI/Button: Interface/Color Changes not implemented.     
CG Integration		9Sep2014			Aman Rana				Enhancement			Cheddar Getter Integration
P_CM_B_43424		13 Aug2014			Rajat Gupta				added for special apostrophe.
--------------------------------------- -------------------------------------------------------------------------------------------------------------------------

 */

package com.home.builderforms;
import java.awt.Color;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;//ParisB-20151021-016 starts
import java.util.HashSet;//ParisB-20151021-016 ends
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.AdminMgr;
import com.home.builderforms.CommonMgr;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.appnetix.app.components.adminmgr.manager.AdminMgr;
import com.appnetix.app.components.locationmgr.manager.LocationMgr;
import com.appnetix.app.components.locationmgr.manager.dao.LocationsDAO;
import com.appnetix.app.components.masterdatamgr.manager.MasterDataMgr;
import com.appnetix.app.components.regionmgr.manager.RegionMgr;
import com.appnetix.app.exception.AppException;
import com.appnetix.app.portal.calendar.UserTimezoneMap;
import com.appnetix.app.portal.role.UserRoleMap;
import com.appnetix.app.portal.role.UserRoleMgr;
import com.home.builderforms.AppColor;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.ConnectionException;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateTime;
import com.home.builderforms.DateUtil;
import com.home.builderforms.Debug;
import com.home.builderforms.DispatchBoardUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.FinancialPrivelegesID;
import com.home.builderforms.InfoFactory;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.MasterEntities;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.ModuleUtil.MODULE_NAME;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.NumberFormatUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.TableAnchors;
import com.home.builderforms.TimeZoneUtils;
import com.home.builderforms.cache.CacheMgr;
import com.home.builderforms.creditcardutil.ServiceConfigurator;
import com.home.builderforms.database.DependentTable;
import com.home.builderforms.database.FieldMappings;
import com.home.builderforms.database.HeaderField;
import com.home.builderforms.database.HeaderMap;
import com.home.builderforms.information.Info;
import com.home.builderforms.tagutils.Combo;
import com.home.builderforms.tagutils.TextArea;
import com.home.builderforms.DBConnectionManager;
/**
 * This class contains some frequently used general utility methods being used
 * in the portal All methods have been declared static
 */
public class BasePortalUtils {

	static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BasePortalUtils.class);

	public static final int DATEFORMAT_YYYY_MM_DD = 0;

	public static final int DATEFORMAT_MM_DD_YYYY = 1;

	public static final String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static final char[] CSV_SEARCH_CHARS = new char[] {',','"','\r','\n'};//BB-20140331-050 
	
	/**
	 * @author abhishek gupta
	 * @date   11/30/2007	
	 * @purpose Add for setting session attribure for menu and sub menu tabs, with sub menu url exist
	 * @param session
	 * @param subMenuName
	 * @param menuName
	 * @param subMenuUrl
	 */
	public static String getRecordsPerPage(HttpServletRequest request){
		String recordPerPage = null;
		recordPerPage = (String) request.getAttribute(FieldNames.RECORD_PER_PAGE);
		if(recordPerPage == null || recordPerPage.equals("")){
			recordPerPage = Integer.toString(Constants.RECORDS_PER_PAGE);
		}
		return recordPerPage;
	}
	public static void setMenuAndSubMenu(HttpSession session,
			String subMenuName, String menuName, String subMenuUrl) {
		if (subMenuUrl != null) {
			session.setAttribute(FieldNames.SUB_MENU_URL, subMenuUrl);
		}
		if (menuName != null) {
			session.setAttribute(FieldNames.MENU_NAME, menuName);
		}
		if (subMenuName != null) {
			session.setAttribute(FieldNames.SUB_MENU_NAME, subMenuName);
			//System.out.println("SUB_MENU_NAME ----" + subMenuName);
		}

	}
	//BB_Address_and_Phone_format_3_28 starts
	
public static String formatAddress(String Company_Name,String address,String city,String state,String country,String zip_code,String phone_number,String fax_number) throws Exception {
return formatAddress( Company_Name, address, city, state, country, zip_code, phone_number, fax_number,null);
}

	public static String formatAddress(String Company_Name,String address,String city,String state,String country,String zip_code,String phone_number,String fax_number,String extension) throws Exception {
		
		//String title="";
		String completeAddress="";
		//String address2="";
		if(Company_Name!=null && !Company_Name.equals("")){
			completeAddress+=Company_Name+"\n";
        }
		if(address!=null && !address.equals(""))
		{
			if(address.indexOf(", ")!=-1 && !StringUtil.isValid(city))  // P_B_CT_14289
			{	
				city=address.substring(address.lastIndexOf(", ")+2);
				address=address.substring(0,address.lastIndexOf(", "));
			}	
			completeAddress+=address;
		}
		if(!completeAddress.equals("") && !completeAddress.equals("null") && !completeAddress.trim().startsWith("Not Available")){
			completeAddress+="\n";
        }
		if(StringUtil.isValidNew(city))
		{
			completeAddress+=city;
		}
		if(state!=null){
			if(StringUtil.isValidNew(city))
				completeAddress+=", "+state;
			else
				completeAddress+=state;
		}
		if(StringUtil.isValidNew(zip_code))
        {
                    if(StringUtil.isValidNew(state))
                    {
                    	//completeAddress+=" - ";
                        completeAddress+="  ";      // Remove '-' by Veerpal Singh on 5 Jan 2012 
                    }else{
                    	completeAddress+=" ";
                    }
                    completeAddress+=zip_code;
        }
		
		System.out.print("\n\ncountry>>>>>>>>>>>"+country);
		if(StringUtil.isValidNew(country))
        {
			completeAddress+="\n"+country;
			
			
			phone_number = PortalUtils.formatPhoneNo(phone_number,country);
        }
		
		if(StringUtil.isValidNew(phone_number))
        {
			//if(StringUtil.isValidNew(country)){
			 completeAddress += "\n";
			 if(StringUtil.isValidNew(extension))
				 completeAddress += extension+"-";
			 completeAddress += PortalUtils.formatPhoneNo(phone_number,country);
        }
		completeAddress=completeAddress.replaceAll("\n\n", "\n");
		return completeAddress;
	}
//BB_Address_and_Phone_format_3_28 ends
	
	    	/**  CC_Gatewary_Configuration
	 * @author dheerandra 
	 * @ date  30 Nov 2009
	 * @ param moduleName String
	 * @ param subModuleName String
	 * @ param dataValue String
	 * @return boolean
	 * @purpose return paymentThorughCreditCard configuration from admin
	 * 
	 */
	
        public static boolean 	isPaymentThroughCCPaymentGateway(String moduleName, String subModuleName, String dataValue){
		
		Info paymentConfigurationInfo = new Info();
		StringBuffer configQuery = new StringBuffer("");
		
		///  START
		
		
		Connection con = null;
		boolean isPayThroughCCPG = false ;
		PreparedStatement pstmt = null;
		java.sql.ResultSet result = null;
		
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			configQuery.append("SELECT * FROM ADMIN_CONFIGURATION_TABLE WHERE MODULE_NAME = '"+moduleName+"' AND SUB_MODULE_NAME = '"+subModuleName+"' AND DATA_VALUE = '"+dataValue+"'");
			pstmt = con.prepareStatement(configQuery.toString());
			

			result = pstmt.executeQuery();
			
			while(result.next()){
				
				paymentConfigurationInfo.set(FieldNames.CONFIGURATION_ID, result.getString("CONFIGURATION_ID"));
				paymentConfigurationInfo.set(FieldNames.CONFIGURATION_VALUE, result.getString("CONFIGURATION_VALUE"));
				 
			}
			
			if(paymentConfigurationInfo!=null && "Y".equalsIgnoreCase(paymentConfigurationInfo.getString(FieldNames.CONFIGURATION_VALUE))){
				return isPayThroughCCPG =  true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		
		return isPayThroughCCPG;
	}
	public static String getFranchiseeNo(HttpSession session){
		String franchiseeNo = null;
		String userStatus = null;
		
		userStatus = (String) session.getAttribute("user_level");
		if(userStatus !=null && userStatus.equals("1")) {
			franchiseeNo = (String) session.getAttribute("franchisee_no");
		}
			
		return franchiseeNo;
	}
	public static String getMessageURL(String headerMessage, String message){
		String messageUrl = null;
		messageUrl = "showMessage?" + FieldNames.HEADER_MESSAGE + "=" + headerMessage + "&" + FieldNames.MESSAGE + "=" + message;
		return messageUrl;
	}
	public static String getName(String firstName, String lastName){
		return firstName + " " + lastName;
	}
		
	/**
	 * @author abhishek gupta
	 * @date   11/30/2007	
	 * @purpose Add for setting session attribure for menu and sub menu tabs
	 * @param session
	 * @param subMenuName
	 * @param menuName
	 */
	public static void setMenuAndSubMenu(HttpSession session,
			String subMenuName, String menuName) {
		setMenuAndSubMenu(session, subMenuName, menuName, null);
	}
	/**
	 * @author abhishek gupta
	 * @date   11/30/2007	
	 * @purpose Add for setting session attribure for menu tabs
	 * @param session
	 * @param subMenuName
	 */
	public static void setMenuAndSubMenu(HttpSession session, String subMenuName) {
		setMenuAndSubMenu(session, subMenuName, null);
	}

	/**
	 * This method will Display the Date combo of selected value
	 * 
	 * @param monthName
	 *            -String monthname as an input parameter
	 * @param yearName
	 *            -String year as an input parameter
	 * @param selectMonth
	 *            -Integer selectMonth to show month as selected in combo box
	 * @param selectYear-
	 *            Integer selectYear to show year as selected in combo box
	 * @return - String date
	 */
	public static String getDateComboVik(String monthName, String yearName,
			int selectMonth, int selectYear) {

		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		Calendar now = Calendar.getInstance();

		int currYear = now.get(Calendar.YEAR);
		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		for (int count = (currYear - 1); count < (currYear + 2); count++) {
			if (selectYear == count) {
				date += ("<option value=" + count + " selected>" + count);
			} else {
				date += ("<option value=" + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}
	
	public static String getContactID(String taskID) throws ConnectionException{

		Connection con = null;
		String contactID="";
		PreparedStatement pstmt= null ;
		java.sql.ResultSet rs = null ;
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

			pstmt = con.prepareStatement("SELECT FOREIGN_ID FROM TASKS WHERE TASK_ID=?");
			pstmt.setString(1,taskID);

			rs=pstmt.executeQuery();
			if(rs.next());
			contactID = rs.getString(1);
		}
		catch (SQLException e){
			Debug.print(e);
		}
		finally {
			try{
				if(rs != null) rs.close();
			}catch(Exception e){}
			try{
				if(pstmt != null) pstmt.close();
			}catch(Exception e){}
			try{
				if(con != null)
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception e){
				logger.error("Exception ", e);
			}
		}
		
			return contactID;
		
	}

	/**
	 * This method converts the given date to the format (year-mon-date)
	 * accepted in database
	 * 
	 * @param date
	 *            -Integer date as an input parameter
	 * @param month
	 *            -Integer month as an input parameter
	 * @param year
	 *            -Integer year as an input parameter
	 * @return date -(year-mon-date)
	 */

	public static String dbFormatDate(int date, int month, int year) {
		try {
			month += 1;
			StringBuffer newDate = new StringBuffer();
			newDate.append(year);
			newDate.append("-");
			if (month < 10) {
				newDate.append("0");
			}
			newDate.append(month);
			newDate.append("-");
			newDate.append(date);

			return newDate.toString();
		} catch (Exception e) {
			Debug.print(e);
			return "";
		}

	}

	/**
	 * This method converts the given date to the format(mon/date/year) accepted
	 * in database
	 * 
	 * @param date-Integer
	 *            date as an input parameter
	 * @param month
	 *            -Integer month as an input parameter
	 * @param year
	 *            -Integer year as an input parameter
	 * @author Rajeev Varshney
	 * @created December 16,2005
	 * @return date in (month/date/year format)
	 */

	public static String dbFormatDate1(int date, int month, int year) {
		try {
			month += 1;
			StringBuffer newDate = new StringBuffer();
			if (month < 10) {
				newDate.append("0");
			}
			newDate.append(month);
			newDate.append("/");
			if (date < 10) {
				newDate.append("0");
			}
			newDate.append(date);
			newDate.append("/");
			newDate.append(year);

			return newDate.toString();
		} catch (Exception e) {
			Debug.print(e);
			return "";
		}

	}

	/**
	 * @author Sunilk
	 * @created on 24,Feb 2006 This method converts the Date format to
	 *          MM/DD/YYYY
	 * @param -String
	 *            date in(dd mmm yyyy format)
	 * @return -String returnDate in(MM/DD/YYYY format)
	 */
	public static String getAuditFormatDate(String date) {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		int i = 0;
		String returnDate = "";
		if (date == null || date.equals("")) {
			return returnDate;
		}
		StringTokenizer dateSTr = new StringTokenizer(date);
		String day = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
		String month = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
		String year = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
		for (; i < 12; i++)
			if (month.equals(months[i]))
				break;
		if (i <= 8)
			month = "0" + (i + 1);
		//P_E_FIM_58658 By Nikhil Verma
		if(date!=null && date.length() >= 10 && i==12 && date.indexOf("AM")==-1 && date.indexOf("PM")==-1 && date.indexOf("No Time Scheduled")==-1)
		{
			if(date.length()>10)
				date = date.substring(0,10).trim();
			//HomeTeam-20160503-577 starts
			if(date.indexOf("-")!=-1)
			{
				month = date.substring(5,7);
				day = date.substring(8,10);
				 year = date.substring(0,4);
			}
			else if(MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT.equalsIgnoreCase("dd/MM/yyyy"))
			{
				 day= date.substring(0,2);
				month  = date.substring(3,5);
				 year = date.substring(6);
			}
			else
			{	
			 month = date.substring(0,2);
			 day = date.substring(3,5);
			 year = date.substring(6);
			}
			//HomeTeam-20160503-577 ends
		}
		else
		{
			month = new Integer((i + 1)).toString();
			//P_B_FIM_56578 By Nikhil Verma
			if(month!=null && month.length()==1)
				month="0"+month;
		}
		if(com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT.equals("MM/dd/yyyy"))
		{
			returnDate = month + "/" + day + "/" + year;
		}
		else
		{
			returnDate = day + "/" + month + "/" + year;
		}
		//P_E_FIM_58658 By Nikhil Verma
		if(date.indexOf("AM")!=-1 || date.indexOf("PM")!=-1 || date.indexOf("No Time Scheduled")!=-1)
			returnDate=date;
		return returnDate;
	}

	/**
	 * This method is used to change the format of a given date. It changes a
	 * date of format YYYY-MM-DD(eg.2001-12-25) to MM-DD-YYYY(eg.Dec-25-2001)
	 * 
	 * @param -
	 *            String inputDate in (YYYY-MM-DD) format
	 * @return - String date in (MM-DD-YYYY)format
	 */
	public static String changeDateFormat(String inputDate) {
		try {
			if (inputDate != null && inputDate.length() > 19) {
				inputDate = inputDate.substring(0, 10);
			}
			if (inputDate != null && !inputDate.trim().equals("")) {
				String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
				StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
				String year = "";
				String month = "";
				String day = "";
				// adeed by Tarun
				while (strTokens.hasMoreTokens()) {
					year = strTokens.nextToken();
					month = strTokens.nextToken();
					day = strTokens.nextToken();
				}
				// added by vikas
				if (Integer.parseInt(day) < 10 && day.length() < 2)
					day = "0" + day;
				month = months[Integer.parseInt(month) - 1];
				return (month + "-" + day + "-" + year);
			}
		} catch (Exception e) {
			Debug.print(e);
		}

		return "";
	}

	/**
	 * This method is used to change the format of a given date based upon the
	 * (format) provided as a second argument to this function.
	 * 
	 * @param inputDate-
	 *            String date as an input parameter
	 * @param format-Integer
	 *            value
	 * @return String requiredFormat(based on the value of argument (format)
	 *         provided in the function)
	 */
	public static String changeDateFormat(String inputDate, int format) {
		String delimiter = "-";
		if (inputDate != null && inputDate.trim().length() > 0
				&& inputDate.indexOf('/') > 0) {
			delimiter = "/";
		}
		StringTokenizer strTokens = new StringTokenizer(inputDate, delimiter);
		String requiredFormat = "";
		String year = null;
		String month = null;
		String day = null;
		switch (format) {
		case DATEFORMAT_YYYY_MM_DD:
			while (strTokens.hasMoreTokens()) {
				month = strTokens.nextToken();
				day = strTokens.nextToken();
				year = strTokens.nextToken();
			}
			requiredFormat = year + "-" + month + "-" + day;
			break;
		case DATEFORMAT_MM_DD_YYYY:
			while (strTokens.hasMoreTokens()) {
				year = strTokens.nextToken();
				month = strTokens.nextToken();
				day = strTokens.nextToken();
			}
			requiredFormat = month + "-" + day + "-" + year;
			break;
		}
		return requiredFormat;
	}

	/**
	 * This method is used to change the format of a given date. It changes the
	 * format from YYYY-MM-DD HH:MM:SS(eg.2001-12-25 21:36:15) to
	 * MM-DD-YYYY(eg.Dec-25-2001)
	 * 
	 * @param -String
	 *            inputDate (YYYY-MM-DD HH:MM:SS) as an Input parameter
	 * @return -in(MM-DD-YYYY)format.
	 */
	public static String changeDateTimeFormat(String inputDate) {
		try {
			StringTokenizer stDate = new StringTokenizer(inputDate);
			if (stDate.hasMoreTokens()) {
				inputDate = stDate.nextToken();
			}
			String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
					"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
			StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
			String year = "";
			String month = "";
			String day = "";
			while (strTokens.hasMoreTokens()) {
				year = strTokens.nextToken();
				month = strTokens.nextToken();
				day = strTokens.nextToken();
			}
			month = months[Integer.parseInt(month) - 1];

			return (month + "-" + day + "-" + year);

		} catch (Exception e) {
			Debug.print(e);
		}

		return "";
	}

	/**
	 * This method is used to change the format of a given date. It changes the
	 * format from YYYY-MM-DD(eg.2001-12-25) to MM/DD/YY(eg.12/25/01)
	 * 
	 * @param -
	 *            String inputDate (YYYY-MM-DD) as an Input parameter
	 * @return - String value in (MM/DD/YYYY) format
	 */
	public static String twoDigitYearFormat(String inputDate) {
		try {
			StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
			String year = "";
			String month = "";
			String day = "";
			while (strTokens.hasMoreTokens()) {
				year = strTokens.nextToken();
				month = strTokens.nextToken();
				day = strTokens.nextToken();
			}

			return (month + "/" + day + "/" + year.substring(2));
		} catch (Exception e) {
			Debug.print(e);
			return "";
		}
	}

	/**
	 * @author- Tarun This Method converts the format of date from 'MMM-DD-YYYY'
	 *          to format of 'YYYY-MM-DD' e.g Sep-05-2005 to 2005-09-05
	 * @param -
	 *            String inputDate object must be in(MMM-DD-YYYY)format
	 * @return- String inputDate in (YYYY-MM-DD)format
	 */
	public static String getDBFormatDate(String inputDate) {
		if (inputDate == null || inputDate.trim().equals(""))
			return "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		try {
			StringTokenizer token = new StringTokenizer(inputDate, "-");
			String Smonth = token.hasMoreTokens() ? token.nextToken() : "";
			String date = token.hasMoreTokens() ? token.nextToken() : "";
			String year = token.hasMoreTokens() ? token.nextToken() : "";
			String Dmonth = null;
			int i = 0;
			for (; i < 12; i++) {
				if (months[i].equals(Smonth))
					break;
			}
			i++;
			if (i < 10 && i >= 0)
				Dmonth = "0" + String.valueOf(i);
			else
				Dmonth = String.valueOf(i);
			return year + "-" + Dmonth + "-" + date;
		} catch (Exception e) {
			return inputDate;
		}
	}

	/**
	 * This method formats the given date for display in the sales graph
	 * 
	 * @param -
	 *            String inputDate object should be in (DD-MMM) format
	 * @return- String inputDate in (DD MMM) format
	 */
	public static String graphFormatDate(String inputDate) {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		try {
			StringTokenizer token = new StringTokenizer(inputDate, "-");

			String date = token.nextToken();
			String month = token.nextToken();
			int intMonth = Integer.parseInt(month);

			return date + " " + months[intMonth - 1];
		} catch (Exception e) {
			return inputDate;
		}
	}

	/**
	 * This method formats the given date for display in the sales graph
	 * InputDate is of the form "MM-DD"...OutputFormat is of the form "DD MM"
	 * 
	 * @param -String
	 *            inputDate should be of form(MM-DD)
	 * @return - String inputDate of form(DD MM)
	 */
	public static String graphFormatDate2(String inputDate) {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		try {
			StringTokenizer token = new StringTokenizer(inputDate, "-");

			String month = token.nextToken();
			String date = token.nextToken();
			int intMonth = Integer.parseInt(month);

			return date + " " + months[intMonth - 1];
		} catch (Exception e) {
			return inputDate;
		}
	}

	/**
	 * This method return date combo boxes with the No dates being selected
	 * 
	 * @param dateName
	 *            -String date as an input parameter
	 * @param monthName
	 *            -String month as an input parameter
	 * @param yearName
	 *            -String year as an input parameter
	 * @return String date
	 */
	public static String getDateCombo(String dateName, String monthName,
			String yearName) {

		StringBuffer date = new StringBuffer("");
		int count = 0;
		date.append("<select name = ").append(monthName).append(
				" class = \"formcontrol\">");
		date.append("<option selected value = \"00\">MM");
		for (; count < monthNames.length; count++)
			date.append("<option value = ").append(count + 1).append(">")
					.append(monthNames[count]);
		date.append("</select>");

		date.append("<select name = ").append(dateName).append(
				" class = \"formcontrol\">");
		date.append("<option selected value = \"00\">DD");
		for (count = 1; count < 32; count++)
			date.append("<option value=").append(count).append(" >").append(
					count);
		date.append("</select>");

		date.append("<select name = ").append(yearName).append(
				" class = \"formcontrol\">");
		date.append("<option selected value = \"0000\">YYYY");
		for (count = 1980; count < 2020; count++)
			date.append("<option value=").append(count).append(" >").append(
					count);
		date.append("</select>");

		return date.toString();
	}

	/**
	 * This method return date combo boxes with the chosen dates being selected
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @param selectDate
	 *            -Integer selectDate, to show date as selected in combo box
	 * @param selectMonth
	 *            -Integer selectMonth, to show month as selected in combo box
	 * @param selectYear
	 *            -Integer selectYear, to show year as selected in combo box
	 * @return date
	 */
	public static String getDateCombo(String dateName, String monthName,
			String yearName, int selectDate, int selectMonth, int selectYear) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<select name = " + dateName + " class = \"formcontrol\">");
		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option value=" + count + " selected>" + count);
			} else {
				date += ("<option value=" + count + ">" + count);
			}
		}
		date += "</select>";

		Calendar now = Calendar.getInstance();

		int currYear = now.get(Calendar.YEAR);
		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		for (int count = (currYear - 4); count < (currYear + 5); count++) {
			if (selectYear == count) {
				date += ("<option value=" + count + " selected>" + count);
			} else {
				date += ("<option value=" + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method will change the format of date from 'MM-DD-YYYY' to format of
	 * 'MM/DD/YYYY'
	 * 
	 * @param inputDate
	 *            object should be of MM-DD-YYYY format
	 * @return String value in MM/DD/YYYY format
	 */
	public static String fourDigitYearFormat(String inputDate) {
		try {
			if (inputDate == null || inputDate.equals("0000-00-00")
					|| inputDate.equals("")) {

				return "";
			}
			StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
			String year = "";
			String month = "";
			String day = "";
			while (strTokens.hasMoreTokens()) {
				year = strTokens.nextToken();
				month = strTokens.nextToken();
				day = strTokens.nextToken();
			}
			String a = month.trim() + "/" + day.trim() + "/" + year.trim();

			if (MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT.equals("dd/MM/yyyy")) {
				a = day.trim() + "/" + month.trim() + "/" + year.trim();
			}
			return (a);
		} catch (Exception e) {
			Debug.print(e);
			return "";
		}
	}

	/**
	 * This method return date combo boxes for DOB(Date of Birth) and value
	 * varies from Current year to last 60 Years
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @return String date
	 */
	public static String getDateComboForDOB(String dateName, String monthName,
			String yearName) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		date += ("<option selected value = ''>MM</option>");
		for (int count = 1; count < 13; count++) {
			date += ("<option value = " + count + ">" + months[count - 1]);
		}
		date += "</select>";
		date += ("<select name = " + dateName + " class = \"formcontrol\">");
		date += ("<option selected value = ''>DD</option>");
		for (int count = 1; count < 32; count++) {
			date += ("<option value=" + count + ">" + count);
		}
		date += "</select>";

		Calendar now = Calendar.getInstance();

		int currYear = now.get(Calendar.YEAR);
		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		date += ("<option selected value = ''>YY</option>");
		for (int count = (currYear - 60); count < (currYear + 2); count++) {
			date += ("<option value=" + count + ">" + count);
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method return date combo with the chosen date selected for DOB(Date
	 * of Birth) and value varies from Current year to last 60 Years
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @param selectDate
	 *            -Integer selectDate to show date as selected in combo box
	 * @param selectMonth
	 *            -Integer selectMonth to show month as selected in combo box
	 * @param selectYear
	 *            -Integer selectYear to show year as selected in combo box
	 * @return String date
	 */

	public static String getDateComboForDOB(String dateName, String monthName,
			String yearName, int selectDate, int selectMonth, int selectYear) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		if (selectMonth == 0)
			date += ("<option value = '' selected>MM</option>");
		else
			date += ("<option value = '' >MM</option>");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";
		date += ("<select name = " + dateName + " class = \"formcontrol\">");

		if (selectDate == 0)
			date += ("<option value = '' selected>DD</option>");
		else
			date += ("<option value = '' >DD</option>");

		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option value=" + count + " selected>" + count);
			} else {
				date += ("<option value=" + count + ">" + count);
			}
		}
		date += "</select>";

		Calendar now = Calendar.getInstance();

		int currYear = now.get(Calendar.YEAR);
		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		if (selectYear == 0)
			date += ("<option value = '' selected>YY</option>");
		else
			date += ("<option value = '' >YY</option>");

		for (int count = (currYear - 60); count < (currYear + 2); count++) {
			if (selectYear == count) {
				date += ("<option value=" + count + " selected>" + count);
			} else {
				date += ("<option value=" + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method return date combo boxes with the current dates selected
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @return String date
	 */
	public static String getPreviousDateCombo(String dateName,
			String monthName, String yearName) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		int selectDate = cal.get(Calendar.DATE);
		int selectMonth = cal.get(Calendar.MONTH) + 1;
		int selectYear = cal.get(Calendar.YEAR);

		date += ("<select name='" + monthName + "' class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<select name='" + dateName + "' class = \"formcontrol\">");
		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";
		date += ("<select name='" + yearName + "' class = \"formcontrol\">");
		for (int count = 2000; count < 2009; count++) {
			if (selectYear == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method return date combo boxes with the chosen dates selected as
	 * above, but the years range from 1980 to 2019 This combo is used for
	 * modifying contract start date for a franchise.
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @param selectDate
	 *            -Integer selectDate to show date as selected in combo box
	 * @param selectMonth
	 *            -Integer selectMonth to show month as selected in combo box
	 * @param selectYear
	 *            -Integer selectYear to show year as selected in combo box
	 * @return String date
	 */
	public static String getModifyDateCombo(String dateName, String monthName,
			String yearName, int selectDate, int selectMonth, int selectYear) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<select name = " + dateName + " class = \"formcontrol\">");
		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option selected>" + count);
			} else {
				date += ("<option>" + count);
			}
		}
		date += "</select>";

		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		for (int count = 1980; count < 2020; count++) {
			if (selectYear == count) {
				date += ("<option selected>" + count);
			} else {
				date += ("<option>" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method return date combo boxes with the current dates but the years
	 * range from 2000 to 2008
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @return String date
	 */
	public static String getCurrentDateCombo(String dateName, String monthName,
			String yearName) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		Calendar cal = Calendar.getInstance();

		int selectDate = cal.get(Calendar.DATE);
		int selectMonth = cal.get(Calendar.MONTH) + 1;
		int selectYear = cal.get(Calendar.YEAR);

		date += ("<select name='" + monthName + "' class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<select name='" + dateName + "' class = \"formcontrol\">");
		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";
		date += ("<select name='" + yearName + "' class = \"formcontrol\">");
		for (int count = 2004; count < 2013; count++) {
			if (selectYear == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method return date combo boxes with the current dates being selected
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String monthName as an input parameter
	 * @param yearName -
	 *            String yearName as an input parameter
	 * @param startYear
	 *            -Integer starting year
	 * @param endYear -
	 *            Integer end year
	 * @return String date value
	 * 
	 */
	public static String getCurrentDateCombo(String dateName, String monthName,
			String yearName, int startYear, int endYear) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		Calendar cal = Calendar.getInstance();

		int selectDate = cal.get(Calendar.DATE);
		int selectMonth = cal.get(Calendar.MONTH) + 1;
		int selectYear = cal.get(Calendar.YEAR);

		date += ("<select name = " + monthName + " class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<select name = " + dateName + " class = \"formcontrol\">");
		for (int count = 1; count < 32; count++) {
			if (selectDate == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";
		date += ("<select name = " + yearName + " class = \"formcontrol\">");
		for (int count = startYear; count < endYear; count++) {
			if (selectYear == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}

	/**
	 * This method returns the current system date in the form of a String
	 * suitable for diplay. Ths date is formatted as MM-DD-YYYY (eg.Jan-14-2002)
	 */
	public static String getCurrentDisplayDate() {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		Calendar calendar = Calendar.getInstance();

		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		StringBuffer returnDate = new StringBuffer();
		returnDate.append(months[month]);
		returnDate.append("-");
		returnDate.append(date);
		returnDate.append("-");
		returnDate.append(year);

		return returnDate.toString();
	}

	/**
	 * This method takes a float number in the form of a String and truncates it
	 * to two places of decimal.
	 */
	public static String truncateFloat(String number) {
		int indexOfDecimal = number.indexOf('.');
		if (indexOfDecimal != -1) { // A decimal exists in the number
			indexOfDecimal += 3; // Two places after the decimal
			if (indexOfDecimal <= number.length()) { // More than two digits
				// after decimal
				return number.substring(0, indexOfDecimal);
			} else { // There is only one digit after decimal
				return (number + "0");
			}
		} else
			return (number + ".00");
	}

	public static String getReverseDBDate(String inputDate){
 		try
 		{
 			if(inputDate == null || inputDate.equals("0000-00-00") || inputDate.equals("")){

 				return "";
 			}
 			StringTokenizer strTokens = new StringTokenizer(inputDate,"-");
 			String year = "";
 			String month = "";
 			String day = "";
 			while (strTokens.hasMoreTokens()){
 				year = strTokens.nextToken();
 				month = strTokens.nextToken();
 				day = strTokens.nextToken();
 			}
 			
 			return (month+"/"+day+"/"+year);
 		}
 		catch (Exception e)
 		{
 			Debug.print(e);
 			return "";
 		}
 	}
	
	/**
	 * This method takes a String & makes it compatible to netscape for passing
	 * it as a variable to another jsp.
	 */
	public static String formatstring(String str) {
		return URLEncoder.encode(str);

	}

	/**
	 * This method will get the Details of particular Franchisee
	 * 
	 * @param franchisee_no-Sting
	 *            franchisee no. as an Input parameter.
	 * @return ArrayList containing franchiseeDetails
	 */
	public static ArrayList getFranchiseeInfo(String franchisee_no) {
		StringBuffer query = new StringBuffer(
				"Select FRANCHISEE_NAME, ADDRESS, CITY, STATE, COUNTRY, ZIPCODE, PHONE1,COUNTRY_ID,REGION_NO from FRANCHISEE where FRANCHISEE_NO = ");
		query.append("\"").append(franchisee_no).append("\"");
		ArrayList franchiseeDetails = new ArrayList(0);
		Connection con = null;
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
		try {
			// Query FRANCHISEE for data given FRANCHISEE_NO....
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			rs.first();

			for (int i = 1; i <= 9; ++i) {
				franchiseeDetails.add(rs.getString(i));
			}

			return franchiseeDetails;
		} catch (SQLException sqle) {
			//Debug.printErrorCode("102");//7.0.5.14  	

		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}

			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				return null;
			}
		}

		return null;
	}

	public static String getUserName1(String sUserName)
    {
    	return BaseUtils.getUserName(sUserName);
	}
	
	public static String getUserName1(int userno) 
	{
		return BaseUtils.getUserName1(userno);
	}

 	public static String getUserName(String sUserName)
 	{
 		return BaseUtils.getUserName(sUserName);
	}
 	
 	public static String getUserFirstName(String sUserName)
 	{
 		return BaseUtils.getUserFirstName(sUserName);
	}
 	
 	public static String getUserLastName(String sUserName)
 	{
 		return BaseUtils.getUserLastName(sUserName);
	} 	 	

 	public static String getassignToName(String userno)
 	{
 		return BaseUtils.getUserName(userno);
 	} 
                
	public static String getUserNameWithUserType(String userno)
	{
		return getUserNameWithType(userno);
	} 

	public static String getUserName(int userno) 
	{
		return BaseUtils.getUserName1(userno);
	}

	public static String getUserNameForContact(int userno)
	{
		return BaseUtils.getUserName1(userno);
	}
	
	public static String getUserLevelForContact(int userno)
	{
		return getUserLevel(userno+FieldNames.EMPTY_STRING);
	}

			
	public static String getAreaNamesCSV(String areaIds) 
	{
		return getAreaNamesCSV(areaIds,false);
	}
	public static String getAreaNamesCSV(String areaIds,boolean showDeleted) {
		String areaName = "";
		Info areaInfo = RegionMgr.newInstance().getRegionsDAO().getAllAreas(areaIds,showDeleted);

		if (areaInfo == null || areaInfo.size() <= 0)
			return areaName;

		for (Iterator it = areaInfo.getValuesIterator(); it.hasNext();) {
			areaName = areaName + ", " + it.next();
		}
		return areaName.substring(1);
	}
	
 /*
 * @author Mohit Sharma
 * @param userIdentificationNo
 * @param userlevel
 * @return csv of emailIds of users
 */

	
	

	/**
  	 * This method is used for getting user details for given user
  	 * identification number
  	 * 
  	 * @param userIdentificationNo-
  	 *            String userIdentificationNo as an Input parameter
  	 * @return Arraylist containing user Details for given user
  	 * @throws ConnectionException
  	 */
  	public static ArrayList getUserDetails(String userNo) throws ConnectionException 
  	{
  		DBConnectionManager connectionManager = null;//UNMANAGED_DB_CONNECTION
        Connection con = null;
  		PreparedStatement pstmt = null;
  		java.sql.ResultSet rs = null;
  		ArrayList<String> details = new ArrayList<String>();
  		Map<String, Object> userMap = NewPortalUtils.getUserDetails(userNo);
  		if(userMap != null)
  		{
  			//details.add((String)userMap.get(FieldNames.FRANCHISEE_NAME));
  			details.add((String)userMap.get(FieldNames.USER_NAME));
  			details.add((String)userMap.get(FieldNames.ADDRESS));
  			details.add((String)userMap.get(FieldNames.CITY));
  			details.add((String)userMap.get(FieldNames.STATE));
  			details.add((String)userMap.get(FieldNames.ZIPCODE));
  			details.add((String)userMap.get(FieldNames.COUNTRY));
  			String phone1 = (String)userMap.get(FieldNames.PHONE1);
  			String phone2 = (String)userMap.get(FieldNames.PHONE2);

  			if (phone1 == null) {
  				phone1 = "";
  			}
  			if (phone2 == null) {
  				phone2 = "";
  			}
  			phone1 =PortalUtils.formatPhoneNo(phone1,(String)userMap.get(FieldNames.COUNTRY));
  			if(!PortalUtils.isBadString(phone1) && !PortalUtils.isBadString((String)userMap.get(FieldNames.PHONE1_EXTN))){
  				phone1=phone1+" ex "+(String)userMap.get(FieldNames.PHONE1_EXTN);
  			}
  			phone2=PortalUtils.formatPhoneNo(phone2,(String)userMap.get(FieldNames.COUNTRY));
  			if(!PortalUtils.isBadString(phone2) && !PortalUtils.isBadString((String)userMap.get(FieldNames.PHONE2_EXTN))){
  				phone2=phone2+" ex "+(String)userMap.get(FieldNames.PHONE2_EXTN);
  			}

  			try
  			{
  				if(phone2.length()!=0)
  				{
  					details.add(phone1+", "+phone2);
  				}
  				else
  				{
  					details.add(phone1);
  				}
  			}
  			catch (Exception e) 
  			{

  				logger.error("Exception in getting userDetails-----",e);
  			}
  			String emailID = ""+(String)userMap.get(FieldNames.EMAIL_ID);                                
  			if(emailID.equals(""))
  			{
  				emailID = MultiTenancyUtil.getTenantConstants().DEFAULT_FROM_EMAIL_ID;
  			}                                
  			details.add(emailID);

  			details.add(PortalUtils.formatPhoneNo((String)userMap.get(FieldNames.MOBILE), (String)userMap.get(FieldNames.COUNTRY)));
  			details.add(PortalUtils.formatPhoneNo((String)userMap.get(FieldNames.FAX), (String)userMap.get(FieldNames.COUNTRY)));
  			details.add((String)userMap.get(FieldNames.FRANCHISEE_NO));
  			
  			try
  			{
  				if (Integer.parseInt(userNo) != -1)
  				{
					  //UNMANAGED_DB_CONNECTION starts
					  //con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);			//P_AD_BUG_52235
					  connectionManager=DBConnectionManager.getInstance();  					  
					  con=connectionManager.getConnection(MultiTenancyUtil.getTenantName());
					  //UNMANAGED_DB_CONNECTION ends
	
					  pstmt = con.prepareStatement("SELECT SSN,DOB,SPOUSE_NAME,CHILDREN_NAME FROM USER_PERSONAL_DETAILS WHERE USER_NO=?");
					  pstmt.setInt(1, Integer.parseInt(userNo));
					  rs = pstmt.executeQuery();
					  while (rs.next()) 
					  {
					  	details.add(rs.getString("SSN"));
					  	details.add(rs.getString("DOB"));
					  	details.add(rs.getString("SPOUSE_NAME"));
					  	details.add(rs.getString("CHILDREN_NAME"));
					  }
                 }
             }
  			 catch (SQLException e) 
  			 {
  				 Debug.print(e);
  			 } finally
  			 {
  				 try 
  				 {
  					 if (rs != null)
  						 rs.close();
  				 } 
  				 catch (Exception e) 
  				 {
  				    logger.error("Exception in releasing resultset...",e);	 
  				 }

  				 try 
  				 {
  					 if (pstmt != null)
  						 pstmt.close();
  				 } catch (Exception e) 
  				 {
  					logger.error("Exception in closing statement...",e);
  				 }
  				 try 
  				 {
  					 if (con != null)
  						 DBConnectionManager.getInstance().freeConnection(con);
  				 } catch (Exception e) {
  					 logger.error("Exception ", e);
  				 }
  			 }
  		}
        return details;

   }

	/**
	 * This method Replace all occurences of _old with _new in _string.
	 */
	public static String replaceAll(String _string, String _old, String _new) {
		StringBuffer buffer = new StringBuffer(_string);
		int pos = _string.indexOf(_old);
		if(!StringUtil.isValidNew(_new) && !"&nbsp;".equals( _old))
		{
			_new="";
		}
        int newLen = _new.length();
		while (pos != -1) {
			// System.out.println(pos);
			buffer.replace(pos, pos + _old.length(), _new);
                        pos = buffer.indexOf(_old,pos+newLen);
//			pos = buffer.toString().indexOf(_old);
		}
		// System.out.println(buffer);
		return buffer.toString();
	}

	/**
	 * This method retrieve the Franchisee No for the particular User From USERS
	 * Table.
	 * 
	 * @param UserNo-String Userno as an input parameter
	 * @return-franchiseeno of the user as value
	 * @throws ConnectionException
	 */
	public static String getFranchiseeNo(String UserNo)
	{
        return NewPortalUtils.getFranchiseeNO(UserNo);
	}
	
	/**
	 * This method gives Status(Active/Inactive) of Users From USERS Table.
	 * 
	 * @param UserNo-String  Userno as an input parameter
	 * @return- String contaning status of users
	 * @throws ConnectionException
	 */
	public static String isActive(String userNo) throws ConnectionException 
	{
		String status = FieldNames.EMPTY_STRING;
    	Map<String, Object> userDetails = NewPortalUtils.getUserDetails(userNo);
    	
    	if (userDetails != null && StringUtil.isValid((String)userDetails.get(FieldNames.USER_STATUS)))
    	{
    		status = (String)userDetails.get(FieldNames.USER_STATUS);
    	}
    	return status;
 	}

	/**
	 * This method gives Status(Active/Inactive) of Franchisee From FRANCHISEE
	 * Table.
	 * 
	 * @param franchiseNo -  String franchisee no.as an input parameter
	 * @return- String contaning status of franchisee.
	 * @throws ConnectionException
	 */

	public static String isFranchiseActive(String franchiseNo)
	{
		String status = FieldNames.EMPTY_STRING;
    	Map<String, Object> franDetails = NewPortalUtils.getFranDetails(franchiseNo);
    	
    	if (franDetails != null && StringUtil.isValid((String)franDetails.get(FieldNames.STATUS)))
    	{
    		status = (String)franDetails.get(FieldNames.STATUS);
    	}
    	
    	return status;
	}

	/**
	 * This method returns isBaseAdmin value whisch states whether user is Base
	 * Admin or not.
	 * 
	 * @param franchiseNo -
	 *            String franchisee no.as an input parameter
	 * @return- String contaning isBaseAdmin value.
	 * @throws ConnectionException
	 */
	public static String isBaseAdmin(String franchiseNo)
	{
		String isBaseAdmin = FieldNames.EMPTY_STRING;
    	Map<String, Object> franDetails = NewPortalUtils.getFranDetails(franchiseNo);
    	
    	if (franDetails != null && StringUtil.isValid((String)franDetails.get(FieldNames.IS_BASE_ADMIN)))
    	{
    		isBaseAdmin = (String)franDetails.get(FieldNames.IS_BASE_ADMIN);
    	}
		return isBaseAdmin;
	}

	/**
	 * This method gives information whether user is Base Admin or not.
	 * 
	 * @param userNo - String userNo as an input parameter
	 * @return- String contaning isBaseAdmin value.
	 * @throws ConnectionException
	 */
	public static boolean isBaseAdminUserNo(String userNo)
	{
        String franNo = NewPortalUtils.getFranchiseeNO(userNo);
        String isBaseAdmin = isBaseAdmin(franNo);
        return "Y".equalsIgnoreCase(isBaseAdmin);
    }

	/**
	 * This method retrieve the Folders Details from FROM NEWS_FOLDERS Table.
	 * 
	 * @return ArrayList -dataList that contain Folder No. and Folder name
	 *         value.
	 * @throws ConnectionException
	 */

	public static ArrayList getFolders() throws ConnectionException {
		Connection con = null;
		String data[] = null;
		ArrayList dataList = new ArrayList();
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			/**
			 * instead of selecting all fields select request field modified by
			 * garima dated 28April06
			 */
			// pstmt = con.prepareStatement("SELECT * FROM NEWS_FOLDERS");
			pstmt = con
					.prepareStatement("SELECT FOLDER_NAME,FOLDER_NO FROM NEWS_FOLDERS WHERE PARENT_FOLDER_NO = -1");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				data = new String[2];
				data[0] = rs.getString("FOLDER_NAME");
				data[1] = rs.getString("FOLDER_NO");
				dataList.add(data);
			}

		} catch (SQLException e) {
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

		return dataList;

	}

	// function added by YADUSHRI for P_SERCH_B_23632
	public static ArrayList getArchivedFolders() throws ConnectionException {
		Connection con = null;
		String data[] = null;
		ArrayList dataList = new ArrayList();
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT FOLDER_NAME,FOLDER_NO FROM NEWS_FOLDERS_ARCHIVED WHERE PARENT_FOLDER_NO = -1");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				data = new String[2];
				data[0] = rs.getString("FOLDER_NAME");
				data[1] = rs.getString("FOLDER_NO");
				dataList.add(data);
			}
		} catch (SQLException e) {
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception in getArchivedFolders ", e);
			}
		}
		return dataList;
	}

	/**
	 * This method replaces quotes in a string by "\\\\\'" so that the string
	 * could be used in javascript
	 * 
	 * @param- input String that conatin "'",as an parameter.
	 * @return- String
	 */
	public static String replaceQuote(String originalString) {
		String stringToBeReplaced = "'";
		String replaceBy = "\\\\\'";
		// In a string replace one substring with another
		if (originalString.equals(""))
			return "";
		String newString = "";
		int i = originalString.indexOf(stringToBeReplaced, 0);
		int lastpos = 0;
		while (i != -1) {
			newString += originalString.substring(lastpos, i) + replaceBy;
			lastpos = i + stringToBeReplaced.length();
			i = originalString.indexOf(stringToBeReplaced, lastpos);
		}
		newString += originalString.substring(lastpos);
		return newString;
	}

	/**
	 * This method replaces quotes in a string by "\\\\\"" so that the string
	 * could be used in javascript
	 * 
	 * @param- input String that conatin "\"",as an parameter.
	 * @return- String
	 */
	public static String replaceDoubleQuote(String originalString) {
		String stringToBeReplaced = "\"";
		String replaceBy = "\\\"";
		// In a string replace one substring with another
		if (originalString.equals(""))
			return "";
		String newString = "";
		int i = originalString.indexOf(stringToBeReplaced, 0);
		int lastpos = 0;
		while (i != -1) {
			newString += originalString.substring(lastpos, i) + replaceBy;
			lastpos = i + stringToBeReplaced.length();
			i = originalString.indexOf(stringToBeReplaced, lastpos);
		}
		newString += originalString.substring(lastpos);
		return newString;
	}

	

	public static String[] getDateTime(String datetime) {
		String[] detail = new String[2];
		detail[0] = datetime.substring(0, 10);
		detail[1] = datetime.substring(10, datetime.length());
		return detail;

	}

	/**
	 * This method is used to generate combo containing owner Name of particular
	 * region and it will return FranchiseeOwnerNo as values in combo
	 * 
	 * @param region-
	 *            String region as an Input parameter
	 * @param features
	 *            -String features as an Input parameter
	 * @param selectedID-String
	 *            of id of region which is selected as an Input parameter
	 * @param psCountry
	 *            -Input parameter String that contain countries for the
	 *            particular Franchisee
	 * @param- boolean pbDeactiveOnly-If true value is being provided then it
	 *         will return owners of Deactivate franchisee and if False then it
	 *         will return owners of Activate franchisee
	 * @return String containing html for owner combo
	 * @throws ConnectionException
	 */
	public static String getOwnersIdCombo(String region, String features,
			String selectedID, String psCaption, String psCountry,
			boolean pbDeactiveOnly) throws ConnectionException {
		// System.out.println("in the funcation");

		if (isBadString(psCaption))
			psCaption = "All";
		boolean franchiseNumbersFlag = !isBadString(region);
		if (isBadString(features)) {
			features = ""; // To avoid 'null' to included as features
		}
		if (isBadString(selectedID)) {
			selectedID = ""; // For default Ist option to be selected if no
			// selected value is provided
		}
		Connection con = null;
		StringBuffer sbOwnersCombo = new StringBuffer();
		StringBuffer sbQuery = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer franchiseeNos = new StringBuffer();
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			sbQuery = new StringBuffer();
			sbQuery
					.append("SELECT DISTINCT FO.FRANCHISE_OWNER_ID,OWNER_FIRST_NAME,OWNER_LAST_NAME FROM OWNERS OW,FIM_OWNERS FO, FRANCHISEE F,FRANCHISEE_USERS FU,USERS U ");
			sbQuery
					.append(" WHERE  OW.OWNER_ID=FO.FRANCHISE_OWNER_ID AND  OW.FRANCHISEE_NO=F.FRANCHISEE_NO AND FO.FRANCHISE_OWNER_ID=FU.USER_TYPE_NO AND FU.USER_TYPE='Owner'  AND U.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND U.STATUS=1");
			if (!pbDeactiveOnly) {
				sbQuery.append(" AND FO.OWNER_DELETED='N'");
			}
			sbQuery.append(" AND F.STATUS!=0"
					+ (isBadString(psCountry) ? "" : " AND F.COUNTRY='"
							+ psCountry + "'"));
			if (franchiseNumbersFlag) {
				// System.out.println("inside if");
				pstmt = con
						.prepareStatement("SELECT FRANCHISEE_NO FROM FRANCHISEE WHERE AREA_ID ='"
								+ region + "'");
				// System.out.println("SELECT FRANCHISEE_NO FROM FRANCHISEE
				// WHERE AREA_ID ='" + region+"'");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					franchiseeNos.append("" + rs.getInt("FRANCHISEE_NO"));
					if (!rs.isLast()) {
						franchiseeNos.append(",");
					}
				}
				// System.out.println("franchiseeNos:" + franchiseeNos);
				if (franchiseeNos.length() == 0) {
					return getEmptyCombo(features); // Returns empty combo if
					// there is no franchisee in
					// selected region
				} else {
					sbQuery.append(" AND FO.FRANCHISEE_NO IN (" + franchiseeNos
							+ ")");
				}
			} else {
				// System.out.println("region is bad");
			}
			sbQuery.append(" ORDER BY FO.OWNER_FIRST_NAME");
			// Debug.println("Fran Owner Combo Query-->"+sbQuery.toString());
			pstmt = con.prepareStatement(sbQuery.toString());
			rs = pstmt.executeQuery();

			sbOwnersCombo.append("<Select " + features + " >");
			sbOwnersCombo.append("<option value=''>");
			sbOwnersCombo.append(psCaption);
			sbOwnersCombo.append("</option>");
			boolean selection = true;
			String franchiseOwnerNo = "";
			String ownerFName = "";
			String ownerLName = "";
			while (rs.next()) {

				franchiseOwnerNo = rs.getString("FRANCHISE_OWNER_ID");
				ownerFName = rs.getString("OWNER_FIRST_NAME");
				ownerLName = rs.getString("OWNER_LAST_NAME");
				sbOwnersCombo.append("<option value=" + franchiseOwnerNo + " ");
				if (franchiseOwnerNo.equals(selectedID) && selection) { // for given value to be selected in the Combo
					sbOwnersCombo.append("selected");
					selection = false;
				}
				sbOwnersCombo.append(" >");
				sbOwnersCombo.append(ownerFName + " " + ownerLName);
				sbOwnersCombo.append("</option>");
			}
			sbOwnersCombo.append("</Select>");
		} catch (SQLException e) {
			Debug.print(e);
			return getEmptyCombo(features); // Returns empty combo if there
			// occurs a SQLException.
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

		return sbOwnersCombo.toString();

	}

	/*
	 * getEmptyCombo() return the empty combo having no options in it it is
	 * called only when there occurs an exception in the making of the combo
	 * being populated by values from data base
	 */
	public static String getEmptyCombo(String features) {
		StringBuffer emptyCombo = new StringBuffer();
		emptyCombo.append("<Select " + features + ">");
		emptyCombo.append("<option value=''>None</option>");
		emptyCombo.append("</select>");
		return emptyCombo.toString();
	}

	public static boolean isBadString(String str) {
		if ((str != null) && (str.length() > 0) && (!str.equals("null"))) {
			return false;
		} else {
			return true;
		}
	}


	/**
	 * This method returns Date format as MM-DD-YYYY Time
	 * 
	 * @param inputDate
	 *            String for Input Date
	 * @return String for formatted Date
	 */

	public static String changeDateAndTimeFormat(String inputDate) {
		try {
			String time = "";
			time = "" + inputDate.substring(10, 19);
			StringTokenizer stDate = new StringTokenizer(inputDate);
			if (stDate.hasMoreTokens()) {
				inputDate = stDate.nextToken();
			}
			String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
					"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
			StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
			String year = "";
			String month = "";
			String day = "";

			while (strTokens.hasMoreTokens()) {
				year = strTokens.nextToken();
				month = strTokens.nextToken();
				day = strTokens.nextToken();
			}
			month = months[Integer.parseInt(month) - 1];

			return (month + "-" + day + "-" + year + time);

		} catch (Exception e) {
			Debug.print(e);
		}

		return "";
	}

    public static String getCustomerName(String custID) throws ConnectionException
		{
			/*String sName				= (String)UsersCache.getInstance().get(new Integer(userno));
			if(sName != null){
				return sName;
			}*/
			String fName = "";
			String lName = "";
			String mName = "";
			String sName = "";
			Connection con				= null;
			PreparedStatement pstmt		= null;
			java.sql.ResultSet rs				= null;
			try 
			{
				con	= DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
				pstmt = con.prepareStatement("SELECT CCD.CONTACT_LAST_NAME,CCD.CONTACT_MIDDLE_NAME,CCD.CONTACT_FIRST_NAME  FROM CM_CONTACT_DETAILS CCD WHERE CONTACT_ID=?");
				pstmt.setString(1,custID);
				rs	= pstmt.executeQuery();
				
				if(rs.next())
				{
					fName		= rs.getString("CONTACT_FIRST_NAME");
					mName		= rs.getString("CONTACT_MIDDLE_NAME");
					lName		= rs.getString("CONTACT_LAST_NAME");
					
				}
				//sName=StringUtil.formatNameWithSpace(fName,mName,lName);
				sName=StringUtil.formatName(fName,mName,lName);/* D_Bug_50947 */
			
				try
				{
					pstmt.close();
					pstmt				= null;
				}
				catch(Exception e)
				{
					
				}
				//DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception e)
			{
				
			}
			finally
			{
				QueryUtil.releaseResultSet(rs);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			
//			System.out.println("name in DAo--->>>"+sName);
			return sName;
	   }

	public static ArrayList<String> getAllActiveFranchisees() {
		String query = "SELECT FRANCHISEE_NO FROM FRANCHISEE WHERE STATUS = '1' AND IS_ADMIN='N' AND FRANCHISEE_NAME!='' ORDER BY FRANCHISEE_NAME";
		return getArrayList(query, "FRANCHISEE_NO", false, null);
	}
	
	/**
	 * This method Gives Month name along with the Month Number
	 * 
	 * @param monthNo-
	 *            Input String containing Month No.
	 * @return String containing Month name
	 */
	public static String getMonthName(String monthNo) {

		String monthName = "";
		if (monthNo.equals("01") || monthNo.equals("1")) {
			monthName = "Jan01";

		}
		if (monthNo.equals("02") || monthNo.equals("2")) {

			monthName = "Feb02";
		}
		if (monthNo.equals("03") || monthNo.equals("3")) {
			monthName = "Mar03";

		}
		if (monthNo.equals("04") || monthNo.equals("4")) {

			monthName = "Apr04";
		}
		if (monthNo.equals("05") || monthNo.equals("5")) {
			monthName = "May05";

		}
		if (monthNo.equals("06") || monthNo.equals("6")) {
			monthName = "Jun06";

		}
		if (monthNo.equals("07") || monthNo.equals("7")) {
			monthName = "Jul07";

		}
		if (monthNo.equals("08") || monthNo.equals("8")) {
			monthName = "Aug08";

		}
		if (monthNo.equals("09") || monthNo.equals("9")) {
			monthName = "Sep09";

		}
		if (monthNo.equals("10")) {

			monthName = "Oct10";
		}
		if (monthNo.equals("11")) {

			monthName = "Nov11";
		}
		if (monthNo.equals("12")) {
			monthName = "Dec12";

		}

		return monthName;
	}

	
	
	/**
	 * This method retrieve the Data values From The MASTER_DATA Table
	 * 
	 * @param -
	 *            String entitytype id as an Input parameter.
	 * @return String containing EntityType.
	 * @throws ConnectionException
	 */
	public static String getEntityType(String entitytypeid)
			throws ConnectionException {

		String value = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

			pstmt = con
					.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

			// System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
			// master_data_id=?");
			pstmt.setInt(1, Integer.parseInt(entitytypeid));
			// System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
			// master_data_id=?");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getString(1);

			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				System.out.println("The exception  entiy is " + e);
			}

		} catch (SQLException e) {
			System.out.println("The sqlexception entity  is " + e);
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final1 entity exception is " + e);
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				System.out.println("The fianl2 entity  exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return value;
	}

	/**
	 * This method retrieve the Store Staus Data values From The MASTER_DATA
	 * Table
	 * 
	 * @param storeid
	 *            -Input String store Id of Store
	 * @return -String containing Store status values
	 * @throws ConnectionException
	 */
	// get store status values
	public static String getStoreStatus(String storeid)
			throws ConnectionException {

		String value = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

			pstmt = con
					.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

			// System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
			// master_data_id=?");
			pstmt.setInt(1, Integer.parseInt(storeid));
			// System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
			// master_data_id=?");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getString(1);

			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
				pstmt = null;
			} catch (Exception e) {
				System.out.println("The exception is " + e);
			}

		} catch (SQLException e) {
			System.out.println("The sqlexception store  is " + e);
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final1 store exception is " + e);
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				System.out.println("The fianl2 store  exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return value;
	}

	/**
	 * This method retrieve the Data values From The MASTER_DATA Table
	 * 
	 * @param transid-String
	 *            transid as an Input parameter
	 * @return String containing Data value.
	 * @throws ConnectionException
	 */
	public static String getTransName(String transid)
			throws ConnectionException {

		String value = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

			pstmt = con
					.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

			// System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
			// master_data_id=?");
			pstmt.setInt(1, Integer.parseInt(transid));
			// System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
			// master_data_id=?");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getString(1);

			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
				pstmt = null;
			} catch (Exception e) {
				System.out.println("The exception is " + e);
			}

		} catch (SQLException e) {
			System.out.println("The sqlexception is " + e);
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final1 exception is " + e);
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				System.out.println("The fianl2 exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return value;
	}

	/**
	 * 
	 * This method will change the format of date from 'YYYY/MM/DD' to
	 * 'YYYY-MM-DD'
	 * 
	 * @param badString-String
	 *            as an Input parameter
	 * @return String Date in YYYY/MM/DD format
	 */
	public static String CheckBadDate(String badString) {
		if (badString == null || badString.equals("")
				|| badString.length() < 10) {
			return " ";
		} else if (badString.length() == 10) {
			StringTokenizer fDateS = new StringTokenizer(badString, "/");
			String monthS = (String) fDateS.nextToken();
			String dateS = (String) fDateS.nextToken();
			String yearS = (String) fDateS.nextToken();
			badString = yearS + "-" + monthS + "-" + dateS;
		}

		return badString;
	}
	
	/**
	 * This method return date combo boxes with the last date selected in the
	 * hidden variable
	 * 
	 * @param dateName -
	 *            String date as an input parameter
	 * @param monthName
	 *            -String name of month as an input parameter
	 * @param yearName -
	 *            String name of year as an input parameter
	 * @return -String value of date
	 */
	public static String getLastDateCombo(String dateName, String monthName,
			String yearName) {
		String date = "";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		Calendar cal = Calendar.getInstance();

		//int selectDate = cal.get(Calendar.DATE);
		int selectMonth = cal.get(Calendar.MONTH) + 1;
		int selectYear = cal.get(Calendar.YEAR);

		date += ("<select name='" + monthName + "' onChange= 'javascript:setDate()' class = \"formcontrol\">");
		for (int count = 1; count < 13; count++) {
			if (selectMonth == count) {
				date += ("<option selected value = " + count + ">" + months[count - 1]);
			} else {
				date += ("<option value = " + count + ">" + months[count - 1]);
			}
		}
		date += "</select>";

		date += ("<input type=hidden name='" + dateName + "' ");
		// 28 is the default date for the combo
		int dateValue = 28;

		date += (" value = " + dateValue + ">");
		date += ("<select name='" + yearName + "' onChange= 'javascript:setDate()' class = \"formcontrol\">");
		for (int count = 2000; count < 2015; count++) {
			if (selectYear == count) {
				date += ("<option selected value = " + count + ">" + count);
			} else {
				date += ("<option value = " + count + ">" + count);
			}
		}
		date += "</select>";

		return date;
	}
	
	
	
	
	
	 /**
	 * This method is used to generate string for combo by given Info object.
	 * @author YogeshT
	 * @param comboInfo
	 * @param comboName
	 * @param selectValue
	 * @param defaultOption		// if not given it will show "Select"
	 * @return StringBuffer
	 * @date 09 Sep 2008
	 */
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
			String selectValue, String defaultOption) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, null);
	}
	
	/**
	 * This method is used to generate string for combo by given Info object.
	 * @author YogeshT
	 * @param comboInfo
	 * @param comboName
	 * @param selectValue
	 * @param defaultOption		// if not given it will show "Select"
	 * @param jsFunction
	 * @date 15 Sep 2008
	 * @return StringBuffer
	 */
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
				String selectValue, String defaultOption, String jsFunction) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, jsFunction,null);
	}
	
	/**
	 * 
	 * @param comboInfo
	 * @param comboName
	 * @param selectValue
	 * @param defaultOption
	 * @param jsFunction
	 * @param fromWhere
	 * @return StringBuffer
	 */
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, jsFunction,fromWhere,null);
	}
	
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, jsFunction,fromWhere,ComboClass,null);
	}
	
	/**
	 * This method is used to generate string for combo by given Info object.
	 * @author YogeshT
	 * @param comboInfo
	 * @param comboName
	 * @param selectValue
	 * @param defaultOption		// if not given it will show "Select"
	 * @param jsFunction
	 * @date 15 Sep 2008
	 * @return StringBuffer
	 */
	
	// Overriding for show / hide select option Ref No.: PW_WITHOUT_PLAN
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, jsFunction,fromWhere,ComboClass,comboStyle,true);
	}

	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle, boolean showSelect) {
		return getComboByInfo(comboInfo, comboName,
				selectValue, defaultOption, jsFunction,fromWhere,ComboClass,comboStyle,showSelect,false);
	}
	
	
	public static StringBuffer getComboByInfo(Info comboInfo, String comboName,
				String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle, boolean showSelect,boolean multiSelect) {
		//BB_FIN_E_getComboByInfo
		if(StringUtil.isValid(jsFunction))
			 jsFunction = " onchange=\""+jsFunction+"\" ";
		else
			jsFunction = "";
		
        StringBuffer periodCombo = new StringBuffer();
        if(comboName != null && (comboName.equals("contactTaskID") || comboName.equals(FieldNames.ACTION_PLAN))){//AUDIT_ENHANCEMENT_CHANGES
        	if(StringUtil.isValidNew(ComboClass))
        	{
        		
        		if(multiSelect){
        		  periodCombo.append("<select multiple class=\""+ComboClass+"\" style=\"width:252px;\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
        		}
        		else
        		{
        		 periodCombo.append("<select class=\""+ComboClass+"\" style=\"width:252px;\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
        		}	
        	}
        	else
        	{
        		if(multiSelect){
        		periodCombo.append("<select multiple class='multiList' style='width:252px;' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
        		}
        		else
        		{
        			periodCombo.append("<select class='multiList' style='width:252px;' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
        		}
        		
        	}
        }
	 else if(comboName != null && comboName.equals("promotionalCampaigns")){
		 if(StringUtil.isValidNew(ComboClass))
     	{
     		if(multiSelect){
     		periodCombo.append("<select multiple class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction+"style='width:235px;'");
     		}
     		else
     		{
    			periodCombo.append("<select class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction+"style='width:235px;'");
     		}	
     	}
	 }  else{
        	if(StringUtil.isValidNew(ComboClass))
        	{
        		if(multiSelect){
        		periodCombo.append("<select multiple class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
        		}
        		else
        		{
       			periodCombo.append("<select class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
        		}	
        	}
        	else
        	{
        		if(multiSelect){
        		periodCombo.append("<select multiple class='multiList' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
        		}else
        		{
        			periodCombo.append("<select class='multiList' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
        		}
        	}
        }
  
if(StringUtil.isValid(comboStyle)){
	periodCombo.append(" "+comboStyle);
        }

periodCombo.append(">");
   // Condition added by veerpal Singh on 04-Nov-2011  BB_CT_ENH_71BBFCNE08
        if(StringUtil.isValidNew(comboName) && "contactIdCombotms".equals(comboName) || "companyNameCombo".equals(comboName) || "categoryProductCombo".equals(comboName)){
            periodCombo.append("<option value=''>select</option>");
            if(StringUtil.isValidNew(defaultOption))
              periodCombo.append("<option value='-1'>"+defaultOption+"</option>");
        }
        else if(defaultOption != null && !"".equals(defaultOption.trim())) {
        	periodCombo.append("<option value=\"-1\">"+LanguageUtil.getString(defaultOption)+"</option>");
        }else if(showSelect){
            periodCombo.append("<option value=\"-1\">"+LanguageUtil.getString("Select")+"</option>");
        }
        
        Iterator itKey = comboInfo.getKeySetIterator();

        String itValue = null;
        while (itKey.hasNext()){
        	itValue = itKey.next().toString();
        	if(multiSelect)
        	{
        		selectValue=","+selectValue+",";
        		if(StringUtil.isValid(selectValue) && selectValue.contains(","+itValue+",")){
    	            periodCombo.append("<option value=\""+itValue + "\" selected='selected'" ).append(">");//AUDIT_ENHANCEMENT_CHANGES
    	            periodCombo.append(LanguageUtil.getString(comboInfo.getString(itValue)));
    	            periodCombo.append("</option>");
            	} else {
                    periodCombo.append("<option value=\""+itValue).append("\">");
                    periodCombo.append(LanguageUtil.getString(comboInfo.getString(itValue)));
                    periodCombo.append("</option>");
            	}
        	}
        	else{
        	if(selectValue != null && selectValue.equals(itValue)){
	            periodCombo.append("<option value=\""+itValue + "\" selected='selected'" ).append(">");//AUDIT_ENHANCEMENT_CHANGES
	            periodCombo.append(LanguageUtil.getString(comboInfo.getString(itValue)));
	            periodCombo.append("</option>");
        	} else {
                periodCombo.append("<option value=\""+itValue).append("\">");
                periodCombo.append(LanguageUtil.getString(comboInfo.getString(itValue)));
                periodCombo.append("</option>");
        	}
        	}
        }
        if(StringUtil.isValid(fromWhere) &&( "tmsEst".equals(fromWhere) ||  "tmsInv".equals(fromWhere)))
        	periodCombo.append("<option   class='text_b' style='background-color: rgb(204, 204, 204);' value='0'  >"+LanguageUtil.getString("Add New Job")+"</option>");
        periodCombo.append("</select>");	
        
        return periodCombo;
    }
	
	
	
	public static StringBuffer getRateCardComboNew(String contactId,String userType,Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle) {
		return getRateCardComboNew( contactId,null, userType,comboInfo,  comboName,
				 selectValue,  defaultOption,  jsFunction, fromWhere, ComboClass, comboStyle); 
	}
		public static StringBuffer getRateCardComboNew(String contactId,HttpServletRequest request,String userType,Info comboInfo, String comboName,
				String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle) {	
	
	//BB_FIN_E_getComboByInfo
	String userLevel = "";
	if(request!=null){
		userLevel = (String)request.getSession().getAttribute("user_level");
	}
    if(StringUtil.isValid(jsFunction))
		 jsFunction = " onchange="+jsFunction+" ";
	else
		jsFunction = "";
	
    StringBuffer periodCombo = new StringBuffer();
        if(StringUtil.isValidNew(ComboClass))
    	{
    		periodCombo.append("<select class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
    	}
    	else
    	{
    		periodCombo.append("<select class='dropdown_list1' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
    	}
  

if(StringUtil.isValid(comboStyle)){
periodCombo.append(" "+comboStyle);
    }
periodCombo.append(">");

    // changes for new combo start  
    String franchiseeNoforContacID = CommonUtil.getFranchiseeNoByTransactionId(contactId);
	String isFranchisee = SQLUtil.getColumnValue("FRANCHISEE", "IS_FRANCHISEE", "FRANCHISEE_NO", franchiseeNoforContacID);
	if(StringUtil.isValid(userType)) {
   		if("0".equals(userType))
   			isFranchisee="N";
   		if(StringUtil.isValid(userType)&&!"0".equals(userType))
   		{
   			isFranchisee="Y";
   			//franchiseeNoforContacID=userType;
   		}
   	}
	
	if(("0".equals(userLevel) || "2".equals(userLevel)) && request!=null && StringUtil.isValid(request.getParameter("franNo"))){
		franchiseeNoforContacID = request.getParameter("franNo");
	}
	
	if ( "Y".equals(isFranchisee) && !StringUtil.isValidNew(selectValue) && !"startJob".equals(fromWhere))  
            selectValue = SQLUtil.getColumnValue("RATE_CARD", "RATE_CARD_ID",new String[]{"FRANCHISEE_NO","IS_DEFAULT"}, new String []{franchiseeNoforContacID,"Y"}) ;
           
    
	

	periodCombo.append("<optgroup label=\"------------ "+LanguageUtil.getString("Corporate ")+" ------------\" class=\"text_b\" style=\"background-color:#CCC;\"></optgroup>");
	 periodCombo.append("<option value=\"-1\"");
      if ( (!"Y".equals(isFranchisee) && !StringUtil.isValidNew(selectValue))|| ("Y".equals(isFranchisee) && !StringUtil.isValidNew(selectValue)))
    	  periodCombo.append(" selected");
	 periodCombo.append(">Default Corporate Rate</option>");
	 
    comboInfo = null ;
    //changes for bug P_B_SCH_34534 starts
    String regionId="";
    if("2".equals(userLevel)){
    	regionId = (String)request.getSession().getAttribute("regionId");
    	comboInfo = CommonUtil.getNewRateCardInfo(userType,contactId,null,"N",regionId);
    }else{
    	comboInfo = CommonUtil.getNewRateCardInfo(userType,contactId,null,"N");
    }
   //changes for bug P_B_SCH_34534 ends
    if(comboInfo!=null && comboInfo.size()>0){
    Iterator itKey = comboInfo.getKeySetIterator();
    String itValue = null;
    while (itKey.hasNext()){
    	itValue = itKey.next().toString();        	
    	if(selectValue != null && selectValue.equals(itValue)){
            periodCombo.append("<option value=\""+itValue + "\" selected").append(">");
            periodCombo.append(comboInfo.getString(itValue));
            periodCombo.append("</option>");
    	} else {
            periodCombo.append("<option value=\""+itValue).append("\">");
            periodCombo.append(comboInfo.getString(itValue));
            periodCombo.append("</option>");
    	}
    }
    }
    
  
    if(StringUtil.isValid(isFranchisee) && "Y".equals(isFranchisee))
    {
    	//to handle rate cards while page loaded and contact not selected for franchisee user log in 
    	if(!StringUtil.isValid(contactId) && request!=null && "1".equals(userLevel)){
    		if("All".equals((String)request.getSession().getAttribute("franchisee_all"))){
    			franchiseeNoforContacID = CommonUtil.getFranchiseeAndOwner(request)[0];
    		}else{
    			franchiseeNoforContacID = (String)request.getSession().getAttribute("franchisee_no");
    		}
    	} 
    	
    	 comboInfo = null ;
    	 comboInfo = CommonUtil.getNewRateCardInfo(userType,contactId,franchiseeNoforContacID,isFranchisee);
    	     	 
    	    if(comboInfo!=null && comboInfo.size()>0){
    	    	periodCombo.append("<optgroup label=\"------------ "+LanguageUtil.getString("Franchise ")+" ------------\" class=\"text_b\" style=\"background-color:#CCC;\"></optgroup>");
    		    Iterator itKey = comboInfo.getKeySetIterator();
    		    String itValue = null;
    		    while (itKey.hasNext()){
    		    	itValue = itKey.next().toString();        	
    		    	if(selectValue != null && selectValue.equals(itValue)){
    		            periodCombo.append("<option value=\""+itValue + "\" selected").append(">");
    		            periodCombo.append(comboInfo.getString(itValue));
    		            periodCombo.append("</option>");
    		    	} else {
    		            periodCombo.append("<option value=\""+itValue);
    		            periodCombo.append("\">");
    		            periodCombo.append(comboInfo.getString(itValue));
    		            periodCombo.append("</option>");
    		    	}
    		    }
    		    }
    	    
    }
    
    // changes for new combo end

    periodCombo.append("</select>");	
    
            
    return periodCombo;
}
	
	
	
	public static StringBuffer getComboByInfoAddJob(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass) {
	return  getComboByInfoAddJob(comboInfo,  comboName, selectValue,  defaultOption,  jsFunction, fromWhere, ComboClass,null);
	}
	public static StringBuffer getComboByInfoAddJob(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String style) {
	return  getComboByInfoAddJob(comboInfo,  comboName, selectValue,  defaultOption,  jsFunction, fromWhere, ComboClass,style,true);
	}
	public static StringBuffer getComboByInfoAddJob(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String style,boolean privilegeFlag) {
	//BB_FIN_E_getComboByInfo
	if(jsFunction != null)
		 jsFunction = " onchange="+jsFunction+" ";
	else
		jsFunction = "";
	
    StringBuffer periodCombo = new StringBuffer();
    if(comboName != null && comboName.equals("contactTaskID")){
    	if(StringUtil.isValidNew(ComboClass))
    	{
    		periodCombo.append("<select class='"+ComboClass+"' style='width:252px;' name='"+comboName+"' id='"+comboName+"' "+jsFunction+">");
    	}
    	else
    	{
    		periodCombo.append("<select class='dropdown_list1' style='width:252px;' name='"+comboName+"' id='"+comboName+"' "+jsFunction+">");
    	}
    }else{
    	if(StringUtil.isValidNew(ComboClass))
    	{
    		periodCombo.append("<select class='"+ComboClass+"' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
    	}
    	else
    	{
    		periodCombo.append("<select class='dropdown_list1' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
    	}
    	if(StringUtil.isValid(style)){
    		periodCombo.append("style='"+style+"'");
    	   }
    	periodCombo.append(">");
    }
   
    if(defaultOption != null && !"".equals(defaultOption.trim())) {
    	periodCombo.append("<option value='-1'>"+defaultOption+"</option>");                	
    }else {
        periodCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");
    }
    
    Iterator itKey = comboInfo.getKeySetIterator();
    String itValue = null;
    while (itKey.hasNext()){
    	itValue = itKey.next().toString();        	
    	if(selectValue != null && selectValue.equals(itValue)){
            periodCombo.append("<option value='"+itValue + "' selected" ).append(">");
            periodCombo.append(comboInfo.getString(itValue));
            periodCombo.append("</option>");
    	} else {
            periodCombo.append("<option value='"+itValue).append("'>");
            periodCombo.append(comboInfo.getString(itValue));
            periodCombo.append("</option>");
    	}
    }
    if("tmsEst".equals(fromWhere) ||  "tmsInv".equals(fromWhere)){
    	if(privilegeFlag){
    		periodCombo.append("<option   class='text_b' style='background-color: rgb(204, 204, 204);' value='0'  >"+LanguageUtil.getString("Add New Job")+"</option>");
    	}
    }
    periodCombo.append("</select>");	
    
    return periodCombo;
}
	
	
	
	
	public static String getCurrencyCombo(String comboName, String selectedValue){
		return getCurrencyCombo(comboName, selectedValue, null);
	}
	public static String getCurrencyCombo(String comboName, String selectedValue, String jsFunction,String forEnabledCounrty,String userLevel){
		StringBuffer comboHtml = new StringBuffer();
		Info currencyInfo= null;
		if(forEnabledCounrty!=null && "enabledCountry".equals(forEnabledCounrty)){
			if(userLevel!=null && !FieldNames.ZERO.equals(userLevel))//P_FIN_B_27607
				currencyInfo= CommonMgr.newInstance().getCommonFinancialsDAO().getCurrencyEnabledInfo(null,userLevel); 
			else
			    currencyInfo= CommonMgr.newInstance().getCommonFinancialsDAO().getCurrencyEnabledInfo(); 
		}
		else
		 currencyInfo= CommonMgr.newInstance().getCommonFinancialsDAO().getCurrencyInfo();    	
    	comboHtml.append(getComboByInfo(currencyInfo, comboName, selectedValue, null, jsFunction));
    	
    	return comboHtml.toString();
	}
	public static String getCurrencyCombo(String comboName, String selectedValue, String jsFunction){
		return getCurrencyCombo( comboName,  selectedValue,  jsFunction,null,null);
    	
    	
	}

	/**
	 * Return an Info Object containing MONTH_NO & NAME as key and Value pair.
	 * @author YogeshT
	 * @date 09 Sep 2008
	 * @return Info Object
	 */
	public static Info getMonthsForCombo(){
		//BB_FIN_E_getComboByInfo
		Info monthInfo = new Info();
		monthInfo.set("01", "Jan");
		monthInfo.set("02", "Feb");
		monthInfo.set("03", "Mar");
		monthInfo.set("04", "Apr");
		monthInfo.set("05", "May");
		monthInfo.set("06", "Jun");
		monthInfo.set("07", "Jul");
		monthInfo.set("08", "Aug");
		monthInfo.set("09", "Sep");
		monthInfo.set("10", "Oct");
		monthInfo.set("11", "Nov");
		monthInfo.set("12", "Dec");		
		return monthInfo;
	}
	
	/**
	 * @author YogeshT	
	 * @date 09 Sep 2008
	 * @return Info object of key value pair for creating Combo box. Default startYear 2005 to current Year. 
	 */
	public static Info getYearsForCombo(){
		return getYearsForCombo(null, null);
	}
	/**
	 * @author YogeshT
	 * @param fromYear
	 * @date 09 Sep 2008
	 * @return Info object of key value pair for creating Combo box. 'fromYear' to current Year.
	 */
	public static Info getYearsForCombo(String fromYear){
		return getYearsForCombo( fromYear, null);
	}
	/**
	 * @author YogeshT
	 * @param fromYear
	 * @param toYear
	 * @date 09 Sep 2008
	 * @return Info object of key value pair for creating Combo box. 'fromYear' to 'toYear'.
	 */
	public static Info getYearsForCombo( String fromYear, String toYear){
		//BB_FIN_E_getComboByInfo
		Info yearInfo = new Info();	
		DateUtil dateObj = new DateUtil();
		String currentDate = dateObj.formatDate(new Date(), "yyyy-mm-dd");
		String currentYear = currentDate.substring(0, 4);
		int yearStart = 2005;//we assumed 2005 as default Year
		int yearEnd = Integer.parseInt(currentYear);	
			
		try{
			if (fromYear != null && fromYear.length() != 4){				
				throw new Exception("Parameter \'Year From\' is not correct.");			
			}
			else{				
				if(fromYear != null)
				yearStart = Integer.parseInt(fromYear);				
			}
			
			if (toYear != null && toYear.length() != 4){				
				throw new Exception("Parameter \'Year To\' is not correct.");
			}else{
				if(toYear != null)
				yearEnd = Integer.parseInt(toYear);				
			}
			
			for (int year=yearStart; year<=yearEnd; year=year+1){
				yearInfo.set(String.valueOf(year), String.valueOf(year));				
			}			
		}catch(Exception e){
			logger.info("Exception in PortalUtils.getYearsForCombo() :"+e);
			logger.error("Exception in PortalUtils.getYearsForCombo() :"+e);			
			e.printStackTrace();
		}		
		return yearInfo;
	}//end of method getYearsForCombo

	/**
	 * This Method Retrieve the list of Countries
	 * 
	 * @return Return a sequenceMap of all the Countries
	 */
	public static SequenceMap<String,String> getCountries()
	{
		Map<String, Map<String,Map<String,Object>>> countryMap = CacheMgr.getCountryCache().get();
    	Map<String,Map<String,Object>> activeCountries = null;
    	SequenceMap<String,String> countries = null;
    	if (countryMap != null && !countryMap.isEmpty())
    	{
    		activeCountries = countryMap.get("1"); 
    	}
    	if (activeCountries != null)
    	{
    		Map.Entry<String,Map<String,Object>> entry = null;
    		Map<String,Object> map = null;
    		countries = new SequenceMap<String,String>();
     		for (Iterator<Map.Entry<String,Map<String,Object>>> itr = activeCountries.entrySet().iterator();itr.hasNext();)
    		{
    			entry = itr.next();
    			map = entry.getValue();
    			if (map != null)
    				//countries.put(entry.getKey(), LanguageUtil.getString((String)map.get(FieldNames.COUNTRY_NAME)));
    				countries.put(entry.getKey(), (String)map.get(FieldNames.COUNTRY_NAME));
    		}
    	}
    	return countries;
	}

	
	
	



	/**
	 * P_FM_All_STORES 09/11/2007 Abhishek This Method Retrieve the list of
	 * Stores with thier name in combo type
	 * 
	 * @return Return a Info of all the Stores
	 */
	public static Info getAllStoresForFin() {
		return getAllStoresForFin(null);
		
	}
	
	public static Info getAllStoresForFin(String regionID) {
		Info info = new Info();
		SequenceMap seqmap = CommonMgr.newInstance().getCommonFinancialsDAO().getStoreTypes(regionID);
		for (Object k : seqmap.keys()) {
			info.set(k, seqmap.get(k));
		}

		return info;
	}
    
      
	/**
	 * This method takes a phone number as a sring and returns the formatted
	 * string in (xxx)yyy-zzzz format only if the country is set to USA
	 * 
	 * @param phone-String
	 *            PhoneNo.as an input Parameter
	 * @param-string country as an input Parameter
	 * @return -StringBuffer as formatted string in (xxx)yyy-zzzz format
	 */
	public static String formatPhoneNo(String phone, String country) {
		if (phone == null)
			phone = "";
		StringBuffer sb = new StringBuffer(phone);
		try {
			if(StringUtil.isInt(country)) {//Wee Watch phone numbers starts
				country = PortalUtils.getCountryNameById(country);
			}//Wee Watch phone numbers ends
			if (country != null && country.equals("USA")) {
			String validChars = "0123456789";
			for (int count = 0; count < sb.length(); count++) {
				if (validChars.indexOf(sb.charAt(count)) == -1) {
					sb = sb.deleteCharAt(count);
					count = -1;
				}
			}

//Sanjeev K F_Phone_formating			
			/**	if (sb.length() > 10) {
					int difference = sb.length() - 10;
					sb = new StringBuffer(sb.substring(0, sb.length()
							- difference));
				}*/

			if(country != null && country.equals("USA")) {
				if (sb.length() == 10) {
					sb.insert(0, '(');
					sb.insert(4, ')');
					// Modified by Anuj 14-Nov-2004
					// sb.insert(5,'-');
					sb.insert(5, ' ');
					sb.insert(9, '-');
				}
				else
				{
					//else part added by sekhar for bug#53323
					sb = new StringBuffer(phone);
				}
			}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return sb.toString();
	}

	/**
	 * This method will change the format of date from 'MM-DD-YYYY' to
	 * 'MM/DD/YYYY' e.g 11-30-2005 to 11/30/2005
	 * 
	 * @param inputDate-
	 *            String Dte as input parameter
	 * @return -String Value in MM/DD/YYYY Format
	 */
	public static String changeNewDateFormmat(String inputDate) {
		String default1="";
		try 
		{
			
			if(StringUtil.isValid(inputDate))
			{	
			StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
			String year = "";
			String month = "";
			String day = "";
			while (strTokens.hasMoreTokens()) {
				month = strTokens.nextToken();
				day = strTokens.nextToken();
				year = strTokens.nextToken();
			}

			return (month + "/" + day + "/" + year);
			}
			else
			{
				return default1;
			}
			} catch (Exception e) {
			Debug.print(e);
			return "";
		}
	}

	/**
	 * This method takes a phone number as a sring and returns the formatted
	 * string in (xxx)yyy-zzzz format only if the country is set to USA
	 * 
	 * @param phone-String
	 *            PhoneNo.as an input Parameter
	 * @return -StringBuffer as formatted string in (xxx)yyy-zzzz format
	 */
	public static String formatPhoneNo(String phone) {
		if (phone == null)
			return "";
		StringBuffer sb = new StringBuffer(phone);
		try {
			String validChars = "0123456789,";
			for (int count = 0; count < sb.length(); count++) {
				if (validChars.indexOf(sb.charAt(count)) == -1) {
					sb = sb.deleteCharAt(count);
					count = -1;
				}
			}

			/*
			 * if (sb.length() > 10){ int difference = sb.length()-10; sb = new
			 * StringBuffer(sb.substring(0,sb.length()-difference)); }
			 */
			if (sb.length() == 10) {
				sb.insert(0, '(');
				sb.insert(4, ')');
				// Modified by Anuj 14-Nov-2004
				// sb.insert(5,'-');
				sb.insert(5, ' ');
				sb.insert(9, '-');
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return sb.toString();
	}
	//  DB_F_B_76154 start 
	public static String formatPhoneNofromUSAtoOther(String phone) {
		if (phone == null)
			return "";
		StringBuffer sb = new StringBuffer(phone);
		try {
			String validChars = "0123456789";
			for (int count = 0; count < sb.length(); count++) {
				if (validChars.indexOf(sb.charAt(count)) == -1) {
					sb = sb.deleteCharAt(count);
					count = -1;
				}
			}

			/*
			 * if (sb.length() > 10){ int difference = sb.length()-10; sb = new
			 * StringBuffer(sb.substring(0,sb.length()-difference)); }
			 */
			
		} catch (Exception e) {
			System.out.println(e);
		}

		return sb.toString();
	}
	
	
	
	// DB_F_B_76154 end
	
	// addedd by saurabh kochhar to get the names of the user created folders

	/**
	 * This method fetches the Folder Names From USER_FOLDERS Table
	 * corresponding To particular Folder No.
	 * 
	 * @param String
	 *            sUserNo As an Input value
	 * @return -String that contain Folder Name
	 */

	public static String getFoldersName(String folder_no)
			throws ConnectionException {
		Connection con = null;
		String foldername = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT DISTINCT FOLDER_NO,FOLDER_NAME FROM USER_FOLDERS WHERE FOLDER_NO="
							+ folder_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				foldername = rs.getString("FOLDER_NAME");

			}

		} catch (SQLException e) {
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final exception is " + e);
			}

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				System.out.println("The fianl exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

		return foldername;

	}
     
	/**
	 * Overloaded method of getUserNamesTo(int i)
	 * 
	 * @param sUserName
	 *            -Input String Username.
	 * @return getUserNamesTo(Integer.parseInt(userName));
	 * @throws ConnectionException
	 */
	public static String getUserNamesTo(String sUserName)
			throws ConnectionException {
		return getUserNamesTo(Integer.parseInt(sUserName));
	}

	/**
	 * This method returns FirstName LastName for a particular user no.
	 * 
	 * @param userno
	 *            User no for which name is to be fetched
	 * @return String containing the User name
	 * @throws ConnectionException
	 */
	public static String getUserNamesTo(int userno) 
    {
		return BaseUtils.getUserName1(userno);
	}
        
	/**
	 * This method will Fetch the User Details From USERS Correponding to
	 * particular User
	 * 
	 * @param userIdentificationNo-String
	 *            user identification no.as an Input parameter
	 * @return -ArrayList containg details of Users
	 * @throws ConnectionException
	 */
	// Updated by Neha on Nov 4,2005 for Bug No:10512gunjan (same as getUserDetails()
	// except name format)
	public static ArrayList getUserDetails1(String userNo) throws ConnectionException 
	{
		Connection con = null;
		ArrayList details = new ArrayList();

		PreparedStatement pstmt = null;
		
		java.sql.ResultSet rs = null;
		Map<String, Object> userMap = NewPortalUtils.getUserDetails(userNo);
		if(userMap != null && !userMap.isEmpty())
		{
			try {
				details.add((String)userMap.get(FieldNames.FRANCHISEE_NAME));
				details.add((String)userMap.get(FieldNames.ADDRESS));
				details.add((String)userMap.get(FieldNames.CITY));
				details.add((String)userMap.get(FieldNames.STATE));
				details.add((String)userMap.get(FieldNames.ZIPCODE));
				details.add((String)userMap.get(FieldNames.COUNTRY));
				details.add((String)userMap.get(FieldNames.PHONE1_EXTN));
				String phonem1=(String)userMap.get(FieldNames.PHONE1);
				details.add(phonem1);
				String phone1 = (String)userMap.get(FieldNames.PHONE1);
				String phone2 = (String)userMap.get(FieldNames.PHONE2);

				if (phone1 == null) {
					phone1 = "";
				}
				if (phone2 == null) {
					phone2 = "";
				}
				phone1 =PortalUtils.formatPhoneNo(phone1,(String)userMap.get(FieldNames.COUNTRY));
				if(!PortalUtils.isBadString(phone1) && !PortalUtils.isBadString((String)userMap.get(FieldNames.PHONE1_EXTN))){
					phone1=phone1+" ex "+(String)userMap.get(FieldNames.PHONE1_EXTN);
				}
				phone2=PortalUtils.formatPhoneNo(phone2,(String)userMap.get(FieldNames.COUNTRY));
				if(!PortalUtils.isBadString(phone2) && !PortalUtils.isBadString((String)userMap.get(FieldNames.PHONE2_EXTN)))
				{
					phone2=phone2+" ex "+(String)userMap.get(FieldNames.PHONE2_EXTN);
				}

				try {
					if(phone2.length()!=0)
					{
						details.add(phone1+", "+phone2);
					}
					else
					{
						details.add(phone1);
					}
				} catch (Exception e) 
				{
					logger.error("Exception while closing resultset ",e);
				}
				//P_INTRANET_B_62994 start
				details.add((String)userMap.get(FieldNames.PHONE2_EXTN));//BUG_7286 27-03-2012 Narotam Singh
				//P_INTRANET_B_62994 end      
				details.add((String)userMap.get(FieldNames.EMAIL_ID));
				details.add((String)userMap.get(FieldNames.MOBILE));
				details.add((String)userMap.get(FieldNames.COUNTRY));
				details.add((String)userMap.get(FieldNames.FRANCHISEE_NO));
				details.add((String)userMap.get(FieldNames.COUNTRY_ID));
				details.add((String)userMap.get(FieldNames.REGION_NO));
				details.add(formatPhoneNo((String)userMap.get(FieldNames.FAX))); 
				details.add((String)userMap.get(FieldNames.TIMEZONE));
				if (Integer.parseInt(userNo) != -1) 
				{
					pstmt = con.prepareStatement("SELECT SSN,DOB,SPOUSE_NAME,CHILDREN_NAME FROM USER_PERSONAL_DETAILS WHERE USER_NO=?");
					pstmt.setInt(1, Integer.parseInt(userNo));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						details.add(rs.getString("SSN"));
						details.add(rs.getString("DOB"));
						details.add(rs.getString("SPOUSE_NAME"));
						details.add(rs.getString("CHILDREN_NAME"));
					}
				}
			}catch (SQLException e) 
			{
				Debug.print(e);
			} 
			finally 
			{
				try {
					if (rs != null)
						rs.close();
				} catch (Exception e) 
				{
					logger.error("Exception while closing resultset ",e);
				}

				try {
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) 
				{
					logger.error("Exception while closing prepareStatment ",e);
				}
				DBConnectionManager.getInstance().freeConnection(con);
			}
		}
		return details;

	}// ends
	 //DB_CACHE_OPTIMIZATION_ENDS

	/**
	 * This method will change the format of date from 'YYYY/MM/DD' to
	 * 'YYYY-MM-DD' e.g 11/30/2005 to 2005-11-30
	 * 
	 * @param inputDate-
	 *            String date as input parameter
	 * @return -String Value in YYYY-MM-DD Format
	 */
	// changes input date of form 11/30/2005 to 2005-11-30
	public static String changeCalenderDateToDBDate(String inputDate) {
		if (inputDate == null || inputDate.equals("")) {
			return "";
		}
		// splitting date into month,day & year as required by method add
		StringTokenizer strTokens = new StringTokenizer(inputDate, "/");
		String year = "";
		String month = "";
		String day = "";
		// String outputDate="";
		while (strTokens.hasMoreTokens()) {
			month = strTokens.nextToken();
			day = strTokens.nextToken();
			year = strTokens.nextToken();
		}
		return (year + "-" + month + "-" + day);

	} // /ends

	/**
	 * This method retrieve the File Name From SUPPORT_REQUESTS Table
	 * corresponding to File No.
	 * 
	 * @param fileNo-
	 *            String fileNo as input parameter
	 * @return- String Attachname-(File that is being attached)
	 * @throws ConnectionException
	 */
	public static String TTFileName(String fileNo) throws ConnectionException {
		Connection con = null;
		String Attachname = "";
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT ATTACHED_FILE_NAME FROM SUPPORT_REQUESTS WHERE REQUEST_ID IN("
							+ fileNo + ")");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Attachname = rs.getString("ATTACHED_FILE_NAME");

				// System.out.println("the file name----->>>>>>"+Attachname);
			}

		} catch (SQLException e) {
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}

		}

		return Attachname;

	}

	
	// added by sanjeev tripathi on 16 nov 06

	public static String dateFormatYYYYMMDD(String inputDate) {
		try {
			StringTokenizer strTokens = new StringTokenizer(inputDate, "/");
			String year = "";
			String month = "";
			String day = "";
			while (strTokens.hasMoreTokens()) {
				month = strTokens.nextToken();
				day = strTokens.nextToken();
				year = strTokens.nextToken();
			}

			// return (month + "/" + day + "/" + year.substring(2));
			return (year + "-" + month + "-" + day);
		} catch (Exception e) {
			Debug.print(e);
			return "";
		}
	}

	/**
	 * This method close the result set and statment and nullify it
	 * 
	 * @author- garima
	 * @created on- 28 April06
	 * @param pStatement
	 * @param pResultSet
	 */

	public static void closeStatements(Statement pStatement,
			ResultSet pResultSet) {
		if (pResultSet != null) {
			try {
				pResultSet.close();
				pResultSet = null;
			} catch (SQLException e) {
				Debug.print(e);
			}
		}

		if (pStatement != null) {
			try {
				pStatement.close();
				pStatement = null;
			} catch (SQLException e) {
				Debug.print(e);
			}
		}

	}

	/**
	 * This method close the result set and statment and nullify it added by
	 * garima dated 28April06
	 * 
	 * @param pStatement
	 * @param pResultSet
	 */
	public static void closePreparedStatements(PreparedStatement pStatement,
			ResultSet pResultSet) {
		if (pResultSet != null) {
			try {
				pResultSet.close();
				pResultSet = null;
			} catch (SQLException e) {
				Debug.print(e);
			}
		}

		if (pStatement != null) {
			try {
				pStatement.close();
				pStatement = null;
			} catch (SQLException e) {
				Debug.print(e);
			}
		}

	}// eof

	/**
	 * This method returns the First Name and Last Name of the Users.
	 * 
	 * @param sUserName
	 *            String containing userName
	 * @return String having FirstName and Lastname of the User
	 * @throws ConnectionException
	 */

	public static String getUserNameCalendar(String sUserName)
			throws ConnectionException {
		return getUserName(Integer.parseInt(sUserName));
	}

	/**
	 * Input: USER_NO Output: UserName()
	 */
	/**
	 * This method returns the First Name and Last Name of the Users according
	 * to the Input userno.
	 * 
	 * @param int
	 *            userno Integer containing user number.
	 * @return String having FirstName and Lastname of the User
	 * @throws ConnectionException
	 */
	public static String getUserNameCalendar(int userno)
	{
		return getUserName1(userno);
	}

	/**
	 * This method will returns the Users Type of Users.
	 * 
	 * @param Fno -
	 *            String Franchisee no. as an Input Parameter
	 * @return String that contain UserType of Users
	 * @throws ConnectionException
	 */

	public static String getUserType(String userNo) 
    {
		String userType = FieldNames.EMPTY_STRING;
		Map<String, Object> userMap = NewPortalUtils.getUserDetails(userNo);
		if(userMap != null && userMap.get(FieldNames.FU_USER_TYPE) != null)
		{
			userType = (String)userMap.get(FieldNames.FU_USER_TYPE);
		}
		return userType;
	}

	/** Added by Sunilk on 27 Oct 2006, for exporting the Area Documents, */

	public static String getAreaDocumentsData(String foreignId, String order)
			throws ConnectionException {
		String returnValue = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT AD.DOCUMENT_FIM_TITLE, AD.DOCUMENT_FIM_ATTACHMENT FROM AREA_DOCUMENTS AD WHERE AD.AREA_TAB_PRIMARY_ID = ? AND AREA_ORDER_NO = ?");
			pstmt.setString(1, foreignId);
			pstmt.setString(2, order);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String temp = null;
				if (rs.getString("DOCUMENT_FIM_TITLE") == null
						|| rs.getString("DOCUMENT_FIM_TITLE").equals("null"))
					temp = "";
				else
					temp = rs.getString("DOCUMENT_FIM_TITLE");
				returnValue = "Title:" + temp;
				returnValue = returnValue + "<BR>";
				returnValue = returnValue + "Upload Document:<BR>";
				if (rs.getString("DOCUMENT_FIM_ATTACHMENT") != null
						&& !rs.getString("DOCUMENT_FIM_ATTACHMENT").equals("")) {
					temp = rs.getString("DOCUMENT_FIM_ATTACHMENT");
					temp = temp.substring(0, temp.indexOf("_"))
							+ temp.substring(temp.lastIndexOf("."));
					returnValue = returnValue + temp;
				} else
					returnValue = returnValue + "Not Available";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return returnValue;
	}

    public static SequenceMap getEstNotAssociateWithTask(String check,
			String userLevel, String selectedValue) {
		SequenceMap dataMap = null;
		String query = null;
		// UserRoleMap TMSUserRoleMap =
		// (UserRoleMap)request.getSession().getAttribute("userRoleMap");
		try {
				query = "SELECT ESTIMATE_ID,ESTIMATE_NUMBER FROM TMS_ESTIMATE WHERE TASK_ID='0' ";
			
                                System.out.println("query========================077777====================="+query.toString());
			com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil
					.getResult(query, new Object[] {});
			dataMap = new SequenceMap();
			while (rs.next()) {
				dataMap.put(rs.getString("ESTIMATE_ID"), ("EST"+rs.getString("ESTIMATE_NUMBER")));
			}
			
		} catch (Exception e) {
			Debug.println("Exception in PortalUtil-->getProduct(): " + e);
		}

		return dataMap;
	}
	
	public static String getAreaCombo(String selectedValue, String multiple) {
		return getAreaCombo(selectedValue, multiple, null);
	}
	public static String getAreaCombo(String selectedValue, String multiple, String comboSize) {
		return getAreaCombo(selectedValue, multiple, comboSize, null);
	}
	public static String getAreaCombo(String selectedValue, String multiple, String comboSize, String comboName) {
		//BB_ENH_OVERLOADED
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer areaCombo = null;
		String addSize = "";
		String combo_name = FieldNames.AREA_ID+"Name";//this name is used earlier so its been here by default.
		
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT AREA_ID, AREA_NAME FROM AREAS  WHERE IS_DELETED ='N' ORDER BY AREA_NAME");
			// System.out.println("pstmt----------"+pstmt);
			rs = pstmt.executeQuery();

			if(comboName!=null && !"".equals(comboName.trim()) && !"null".equalsIgnoreCase(comboName.trim())){
				combo_name = comboName;
			}
			areaCombo = new StringBuffer("<select name ="+combo_name+ " id='"+combo_name+"' class = \"dropdown_list\"");
					
			if (multiple != null && multiple.equals("Y")) {
				if(comboSize!=null && !"".equals(comboSize.trim()) && !"0".equals(comboSize.trim())){
					addSize = "size='"+comboSize+"'";
				}
				areaCombo.append("  multiple "+addSize+" >");				
			} else {
				areaCombo.append("   >");
			}		
			
			areaCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");

			while (rs.next()) {
				areaCombo.append("<option value='").append(
						rs.getString("AREA_ID"));
				areaCombo.append("'");
				if (selectedValue != null && selectedValue.equals(rs.getString("AREA_ID")))
					areaCombo.append(" selected");

				areaCombo.append(">");
				areaCombo.append(rs.getString("AREA_NAME")).append("</option>");
			}
		} catch (Exception e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		// System.out.println("areaCombo----------"+areaCombo);
		areaCombo.append("</select>");

		return areaCombo.toString();

	}
/**
 * @createdBy Priyanka Jain
 * 
 * @param selectedValue
 * @param multiple
 * @param comboSize
 * @param comboName
 * @return
 */
 
 
	public static String getCountryCombo(String selectedValue, String multiple, String comboSize, String comboName) {
		//BB_ENH_OVERLOADED
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer areaCombo = null;
		String addSize = "";
		String combo_name = FieldNames.COUNTRY+"Name";//this name is used earlier so its been here by default.
		
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT DISTINCT COUNTRY_ID, NAME FROM COUNTRIES WHERE SHOW_COUNTRY=1 ORDER BY NAME");
			// System.out.println("pstmt----------"+pstmt);
			rs = pstmt.executeQuery();

			if(comboName!=null && !"".equals(comboName.trim()) && !"null".equalsIgnoreCase(comboName.trim())){
				combo_name = comboName;
			}
			areaCombo = new StringBuffer("<select name ="+combo_name+ " id='"+combo_name+"' class = \"dropdown_list\"");
					
			if (multiple != null && multiple.equals("Y")) {
				if(comboSize!=null && !"".equals(comboSize.trim()) && !"0".equals(comboSize.trim())){
					addSize = "size='"+comboSize+"'";
				}
				areaCombo.append("  multiple "+addSize+" >");				
			} else {
				areaCombo.append("   >");
			}		
			
			areaCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");

			while (rs.next()) {
				areaCombo.append("<option value='").append(
						rs.getString("COUNTRY_ID"));
				areaCombo.append("'");
				if (selectedValue != null && selectedValue.equals(rs.getString("COUNTRY_ID")))
					areaCombo.append(" selected");

				areaCombo.append(">");
				areaCombo.append(rs.getString("NAME")).append("</option>");
			}
		} catch (Exception e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		// System.out.println("areaCombo----------"+areaCombo);
		areaCombo.append("</select>");

		return areaCombo.toString();

	}

	/**
	 * @created by Priyanka Jain
	 * @param selectedValue
	 * @param multiple
	 * @param comboSize
	 * @param comboName
	 * @return
	 */
	public static String getFranchiseeCombo(String selectedValue, String multiple, String comboSize, String comboName) {
		//BB_ENH_OVERLOADED
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer areaCombo = null;
		String addSize = "";
		String combo_name = FieldNames.FRANCHISE_ID+"Name";//this name is used earlier so its been here by default.
		
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT FAV.FRANCHISEE_NO AS FRANCHISEE_NO, F.FRANCHISEE_NAME FROM FIN_FRANCHISE_AGREEMENT_VERSIONS FAV LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO = FAV.FRANCHISEE_NO  WHERE F.IS_ADMIN ='N'  ORDER BY F.FRANCHISEE_NAME");
			// System.out.println("pstmt----------"+pstmt);
			rs = pstmt.executeQuery();

			if(comboName!=null && !"".equals(comboName.trim()) && !"null".equalsIgnoreCase(comboName.trim())){
				combo_name = comboName;
			}
			areaCombo = new StringBuffer("<select name ="+combo_name+ " id='"+combo_name+"' class = \"dropdown_list\"");
					
			if (multiple != null && multiple.equals("Y")) {
				if(comboSize!=null && !"".equals(comboSize.trim()) && !"0".equals(comboSize.trim())){
					addSize = "size='"+comboSize+"'";
				}
				areaCombo.append("  multiple "+addSize+" >");				
			} else {
				areaCombo.append("   >");
			}		
			
			areaCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");

			while (rs.next()) {
				areaCombo.append("<option value='").append(
						rs.getString("FRANCHISEE_NO"));
				areaCombo.append("'");
				if (selectedValue != null && selectedValue.equals(rs.getString("FRANCHISEE_NO")))
					areaCombo.append(" selected");

				areaCombo.append(">");
				areaCombo.append(rs.getString("FRANCHISEE_NAME")).append("</option>");
			}
		} catch (Exception e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		// System.out.println("areaCombo----------"+areaCombo);
		areaCombo.append("</select>");

		return areaCombo.toString();

	}

	

	/**
	 * This method is used to change the format of a given date. It changes a
	 * date of format YYYY-MM-DD(eg.2001-12-25) to MM/DD/YYYY(eg.12/25/2001)
	 * 
	 * @param -
	 *            String inputDate in (YYYY-MM-DD) format
	 * @return - String date in (MM/DD/YYYY)format
	 */
	public static String changeDateFormatone(String inputDate) {
		try {
			if (inputDate != null && inputDate.length() > 19) {
				inputDate = inputDate.substring(0, 10);
			}
			if (inputDate != null && !inputDate.trim().equals("")) {
				// String months[] =
				// {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
				StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
				String year = "";
				String month = "";
				String day = "";
				// adeed by Tarun
				while (strTokens.hasMoreTokens()) {
					year = strTokens.nextToken();
					month = strTokens.nextToken();
					day = strTokens.nextToken();
				}
				// added by vikas
				if (Integer.parseInt(day) < 10 && day.length() < 2)
					day = "0" + day;
				// month = months[Integer.parseInt(month) - 1];
				return (month + "/" + day + "/" + year);
			}
		} catch (Exception e) {
			Debug.print(e);
		}

		return "";
	}

	

	
	/**
	 * Added by Sunilk on 15 Mar 2007 This Method will return a string in Title
	 * Case. e.g 'this is string' will be returned as 'This Is String' Seprators
	 * can be provided in char array, then it will captalize after each
	 * occarence of delimiters.
	 */
	/*public static String toTitleCase(String str) {
		if (str == null || str.trim().equals(""))
			return "";
		str = str.toLowerCase();
		char[] delimiters = new char[] { ' ', '.' };
		return WordUtils.capitalize(str, delimiters);

	}*/

	/**
	 * Added by Sunilk on 27 Mar 2007 , for getting the user name with their
	 * type associated.*
	 */
	public static String getUserNameWithType(String userno) {
		return getUserNameWithType(userno, false);
	}
	public static String getUserNameWithType(String userno, boolean withoutSpan)
	{
		String sName = FieldNames.EMPTY_STRING;
		Map<String, Object> userMap = NewPortalUtils.getUserDetails(userno);
		if(userMap != null)
		{
			String userAbbr = (String)userMap.get(FieldNames.USER_TYPE1);
			if(Constants.USER_LEVEL_DIVISION.equals((String)userMap.get(FieldNames.USER_LEVEL))) {
				userAbbr = "("+MultiTenancyUtil.getTenantConstants().DIVISION_USER_ABBR+")";
			}
			if(withoutSpan) {
				sName = (String)userMap.get(FieldNames.USER_NAME)+" " + userAbbr;
			} else {
				sName = (String)userMap.get(FieldNames.USER_NAME)+" "+ "<span class='urgent_fields'>" + userAbbr+"</span>";
			}
		}

		return sName;
	}

	public static String getUserNameWithType(int userno)
	{
		return getUserNameWithType(userno+FieldNames.EMPTY_STRING);
	}


	public static String getQuizTargetTime(String quizStartTime,
			String timeAllotedHour, String timeAllotedMin) {

		Timestamp targetTimeStamp = Timestamp.valueOf(quizStartTime);

		long quizStartTimeMiliSecs = targetTimeStamp.getTime();

		long timeAllotedHourLongValue = Integer.parseInt(timeAllotedHour, 10) * 60 * 60 * 1000;

		long timeAllotedMinLongValue = Integer.parseInt(timeAllotedMin, 10) * 60 * 1000;

		long targetTimeLongValue = quizStartTimeMiliSecs
				+ timeAllotedHourLongValue + timeAllotedMinLongValue;

		targetTimeStamp.setTime(targetTimeLongValue);

		String targetTime = DateTime.getStringFromTimeStamp(targetTimeStamp
				.toString());

		return targetTime;

	}

	// D_S_B_23273 start
	public static String getUserNameNew(String sUserName)
	{
		return getUserNameNew(Integer.parseInt(sUserName));
	}

	public static String getUserNameNew(int userno)
	{
		return getUserName1(userno);
	}

	// D_S_B_23273 end

	// Added By Ravindra Verma on 5/9/2007 to show (RU) with Regional Users
	// P_FS_B_24572

	public static String getUserNameNew1(String userNo)
	{
		return getUserNameWithType(userNo);
	}

	// ganesh on 25-may-2007
	public static Info UserPersonalDetailsBateDate(String userNo)
			throws ConnectionException {
		Connection con = null;
		//String DOB = "";
                Info info=null;
               // String birthDate ="";// P_ADMIN_CR_001 added by ankush tanwar 
		//String birthMonth ="";

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
                        PreparedStatement pstmt = con.prepareStatement("SELECT BIRTH_DATE,BIRTH_MONTH FROM  USER_PERSONAL_DETAILS  WHERE USER_NO IN(" + userNo+")");// P_ADMIN_CR_001 added by ankush tanwar 

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				//DOB = rs.getString("DOB");
                            info=new Info();
                            info.set("birthDate",rs.getString("BIRTH_DATE") );// P_ADMIN_CR_001 added by ankush tanwar 
                            info.set("birthMonth", rs.getString("BIRTH_MONTH"));

			}

		} catch (SQLException e) {
			Debug.print(e);
		} finally {
			DBConnectionManager.getInstance().freeConnection(con);
			return info;
		}
	}

	public static String getTicketStateCombo(String ticketState) {

		StringBuffer ticketStateCombo = new StringBuffer(
				//"<Select name=\"ticketState\" onChange=\"searchFilter(this)\" class=\"dropdown_list\" > <option value=\"allTickets\"");
		"<select name=\"ticketState\" onChange=\"searchFilter(this)\" class=\"multiList\" style=\"display:inline\" > <option value=\"allTickets\"");
//P_SUPP_E_70028
		if ("allTickets".equals(ticketState)|| ticketState == null
				|| ticketState.equals(FieldNames.EMPTY_STRING)
				|| ticketState.equals(FieldNames.NULL))//P_SUPP_E_70028
			ticketStateCombo.append(" selected ");

		ticketStateCombo
				.append("> "+LanguageUtil.getString("All")+" </option> <option value=\"assignedTickets\"");
//P_SUPP_E_70028
		if ("assignedTickets".equals(ticketState))
			ticketStateCombo.append(" selected ");

		ticketStateCombo
				.append("> "+LanguageUtil.getString("Assigned")+" </option> <option value=\"unAssigned\"");

		if ("unAssigned".equals(ticketState))
			ticketStateCombo.append(" selected ");

		ticketStateCombo.append("> "+LanguageUtil.getString("Unassigned")+" </option> </Select>");

		return ticketStateCombo.toString();
	}

	
	// P_Admin_E_Ftp101 Starts
	// This method is used to get details of Default Ftp Server.
	public static Info getFtpServerDetails() {
		StringBuffer query = new StringBuffer(
				"SELECT FTP_SERVER_ID, HOST_NAME, USER_NAME, PASSWORD, FLAG, HOST_URL, IS_DEFAULT FROM DEFAULT_FTP_SERVER ");

		Info serverInfo = null;
		try {
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
					.getResult(query.toString(), new Object[] {});
			serverInfo = new Info();
			if (result.next()) {
				serverInfo.set(FieldNames.FTP_SERVER_ID, result
						.getString("FTP_SERVER_ID"));
				serverInfo.set(FieldNames.HOST_NAME, result
						.getString("HOST_NAME"));
				serverInfo.set(FieldNames.HOST_URL, result
						.getString("HOST_URL"));
				serverInfo.set(FieldNames.USER_NAME, result
						.getString("USER_NAME"));
				serverInfo.set(FieldNames.PASSWORD, result
						.getString("PASSWORD"));
				serverInfo.set(FieldNames.IS_DEFAULT, result
						.getString("IS_DEFAULT"));
				serverInfo.set(FieldNames.FLAG, result.getString("FLAG"));
			} else {
				serverInfo.set(FieldNames.FTP_SERVER_ID, "");
				serverInfo.set(FieldNames.HOST_NAME, "");
				serverInfo.set(FieldNames.HOST_URL, "");
				serverInfo.set(FieldNames.USER_NAME, "");
				serverInfo.set(FieldNames.PASSWORD, "");
				serverInfo.set(FieldNames.IS_DEFAULT, "");
				serverInfo.set(FieldNames.FLAG, "0");
			}// end else
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
		return serverInfo;
	}
	

	

	// D_Admin_E_1001 Starts
	// This method is used to get details of Default Ftp Server.
	public static boolean isDefaultFtpServer() {
		StringBuffer query = new StringBuffer(
				"SELECT IS_DEFAULT FROM DEFAULT_FTP_SERVER WHERE FLAG='1'");

		boolean isDefault = false;
		try {
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
					.getResult(query.toString(), new Object[] {});
			if (result.next()) {
				if (result.getString("IS_DEFAULT") != null
						&& ("Y").equals(result.getString("IS_DEFAULT"))) {
					isDefault = true;
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return isDefault;
		}
		return isDefault;
	}

	// D_Admin_E_1001 Ends

	// This method is used to get name of Configured Ftp Server.
	public static String getConfiguredHostName() {
		String query = "SELECT HOST_URL FROM DEFAULT_FTP_SERVER WHERE FLAG =1 ";
		String sHostName = "";
		try {
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
					.getResult(query, new Object[] {});
			if (result.next()) {
				sHostName = result.getString("HOST_URL");
			}
			return sHostName;
		} catch (Exception e) {
			logger.error("excepion in getConfiguredHostName : " + e);
			return "";
		}
	}

	// P_Admin_E_Ftp101 ends

	public static String getUserName2(String sUserName)
			throws ConnectionException {
		//DB_CACHE_OPTIMIZATION_STARTS
				return getUserName1(Integer.parseInt(sUserName));
				//return getUserName2(Integer.parseInt(sUserName));
				//DB_CACHE_OPTIMIZATION_ENDS
	}

	/**
	 * This method will returns the First Name, Last name of Users
	 * 
	 * @param userno
	 *            -Integer User No. as an input parameter.
	 * @return - String containing First Name, Last name of Users.
	 */
	public static String getUserName2(int userno) throws ConnectionException {
		return getUserName1(userno);
	}

	// this method is used get current time from DataBase
	public static String getCurrentTimeFromDB() {
		String currentTime = "";
		String query = "SELECT NOW() AS CURRENTTIME";
		try {
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
					.getResult(query, new Object[] {});
			if (result.next()) {
				java.sql.Timestamp dbTime = result.getTimestamp("CURRENTTIME");
				currentTime = dbTime.toString();
			}
			return currentTime;
		} catch (Exception e) {
			logger.error("Exception in getCurrentTimeFromDB :" + e);
			return currentTime;
		}
	}// end of getCurrentTimeFromDB

	public static String getFranchiseeNameWithStatus(String franchiseeNo,HttpServletRequest request)
	throws ConnectionException {
Connection con = null;
String franchiseeName = "--Not Available--";
Statement stmt = null;
java.sql.ResultSet rs = null;
try {
	con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
	stmt = con.createStatement();
	rs = stmt
			.executeQuery("SELECT FRANCHISEE_NAME,STATUS FROM FRANCHISEE WHERE FRANCHISEE_NO="
					+ franchiseeNo);
	if (rs.next()) {
		String status = rs.getString("STATUS");
		if (status != null && (status.equals("0") || status.equals("4"))) {
			String viewAsPdf = (String)request.getParameter("viewAsPdf");
			if(viewAsPdf!=null && !"".equals(viewAsPdf) && "true".equals(viewAsPdf)){
				status="*";
			}else{
				status = "<span class='urgent_fields'>*</span>";
			}
			franchiseeName = status + rs.getString("FRANCHISEE_NAME");
			request.setAttribute("terminatedMessage", "true");
		} else {
			franchiseeName = rs.getString("FRANCHISEE_NAME");
		}
	}
	return franchiseeName;
} catch (SQLException e) {
	Debug.print(e);
	return "--Not Available--";
} catch (Exception e) {
	Debug.print(e);
	return "--Not Available--";
} finally {
	try {
		if (rs != null)
			rs.close();
	} catch (Exception e) {
	}
	try {
		if (stmt != null)
			stmt.close();
	} catch (Exception e) {
	}
	try {
		if (con != null)
			DBConnectionManager.getInstance().freeConnection(con);
	} catch (Exception e) {
		logger.error("Exception ", e);
	}
}

}
	
	
	public static String getItemNameById(String tableName,
			String requiredColumnName, String givenColumnName,
			String givenColumnValue) {
		String itemName = "";
		Connection con = null;
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		String query = "SELECT " + requiredColumnName + " FROM " + tableName
				+ " WHERE " + givenColumnName + "='" + givenColumnValue + "'";
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				// ganesh,13-nov-2007,modified so that it retrive the first
				// string always,so that we can implement concat operation also
				itemName = rs.getString(1);
			}

		}

		catch (Exception e) {
			logger.info("Exception in getItemNameById");
		}

		finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		//if itemName contains null it generates exception
		if(StringUtil.isValid(itemName))
		{
			itemName = itemName.replaceAll("\\b\\s{2,}\\b", " ");
		}
		return itemName;
	}

	public static String getLeadStatusName(String statusId)
			throws ConnectionException {
		Connection con = null;
		String statusName = "Not Available";
		Statement stmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			stmt = con.createStatement();

			rs = stmt
					.executeQuery("SELECT LEAD_STATUS_NAME FROM FS_LEAD_STATUS WHERE LEAD_STATUS_ID="
							+ statusId);
			if (rs.next()) {
				statusName = LanguageUtil.getString(rs.getString("LEAD_STATUS_NAME"));
			}
			return statusName;
		} catch (SQLException e) {
			Debug.print(e);
			return "Not Available";
		} catch (Exception e) {
			Debug.print(e);
			return "Not Available";
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final1 exception is " + e);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				System.out.println("The fianl2 exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

	}

	public static String getEncryptedData(String data) {
		Connection con = null;
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		String data1 = "";
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT AES_ENCRYPT(" + data
					+ ",'pvm@e20')  AS DATA");
			if (rs.next()) {
				data1 = rs.getString("DATA");
			}
			return data1;
		} catch (SQLException e) {
			Debug.print(e);
			return data1;
		} catch (Exception e) {
			Debug.print(e);
			return data1;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("The final1 exception is " + e);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				System.out.println("The fianl2 exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

	}

	public static String getStateFromRegion(String stateid)
			throws ConnectionException {
		// System.out.println("JMK inside state "+stateid);
		String value = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

			// pstmt = con.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA
			// WHERE master_data_id=?");
			pstmt = con
					.prepareStatement("SELECT REGION_NAME FROM REGIONS WHERE REGION_NO=?");

			// System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
			// master_data_id=?");
			pstmt.setInt(1, Integer.parseInt(stateid));
			// System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
			// master_data_id=?");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getString(1);

			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				logger.error("The exception is " + e);
			}

		} catch (SQLException e) {
			logger.error("The sqlexception state  is " + e);
			Debug.print(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				logger.error("The final1 state exception is " + e);
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.error("The fianl2 state  exception is " + e);
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return value;
	}

	/**
	 * Added by Sunilk
         * P_SUPP_B_53369 Modified By Vikram Raj added values for "[" & "]"
         * Return passed value if starts with "&#" and has ";" characters.(UTF-8 chars for chinese and european and other charsets)
	 */
	public static String filterValue(String pValue) {

		if (pValue == null || pValue.length() == 0)
			return (pValue);
			
		//Return passed value if starts with "&#" and has ";" characters.(UTF-8 chars for chinese and european and other charsets)
		if (pValue.startsWith("&#") && pValue.contains(";"))
		{
			return pValue;
		}
		//end Return
		
		char[] content = pValue.toCharArray();
		int size = content.length;
		StringBuffer result = new StringBuffer(size + 50);
		int mark = 0;
		String replacement = null;
		boolean flag = false;//caribo-20140328-030
		for (int i = 0; i < size; i++) {
			//Juice-20131211-016 : starts
			if(flag || (i+7<size && "&".equals(content[i]+"") && "#".equals(content[i+1]+"") && "x".equals(content[i+2]+"") && ";".equals(content[i+7]+""))) //caribo-20140328-030
			{
				//caribo-20140328-030 starts
				/*if(specialCharList.contains(content[i+3]+""+content[i+4]+""+content[i+5]+""+content[i+6]+"")){
					i=i+7;*/
				flag = flag?false:true; 
					continue;
				//}
			} //Juice-20131211-016 : ends
			//caribo-20140328-030 ends
			switch (content[i]) {
			case '<':
				replacement = "&lt;";
				break;
			case '>':
				replacement = "&gt;";
				break;
			case '&':
				replacement = "&amp;";
				break;
			case '"':
				replacement = "&quot;";
				break;
			case '\n':
				replacement = "<br />";//AUDIT_ENHANCEMENT_CHANGES
				break;
			case '\r':
				replacement = FieldNames.EMPTY_STRING;
				break;
			case '\f':
				replacement = FieldNames.EMPTY_STRING;
				break;
			case '?':
				replacement = "&#63;";
				break;
			case '\'':
				replacement = "&#39;";
				break;
			case '#':
				replacement = "&#35;";
				break;
			case '\\':
				replacement = "&#92;";
				break;
			case '/':
				replacement = "&#47;";
				break;				
			case '%':
				replacement = "&#37;";
				break;
			case '+':
				replacement = "&#43;";
				break;
			case '=':
				replacement = "&#61;";
				break;
			case '[':
				replacement = "&#91;";
				break;
			case ']':
				replacement = "&#93;";
				break;
			}
			
			if (replacement != null) {
				if (mark < i) {
					result.append(content, mark, i - mark);
				}
				result.append(replacement);
				replacement = null;
				mark = i + 1;
			}
		}
		if (mark < size) {
			result.append(content, mark, size - mark);
		}
		return (result.toString());

	}
	//P_E_ENCODING starts
	public static String toHex(String str) {
		/*Iterator<Map.Entry<String , String >> itr=replacementMap.entrySet().iterator();
		Map.Entry<String , String > entrySet;
		while(itr.hasNext()){
			entrySet=itr.next();
			str = replaceAll(str,entrySet.getValue(),entrySet.getKey());
		}*/
		return str ;
	}
	public static String toOriginal(String str) {
		/*Iterator<Map.Entry<String , String >> itr=replacementMap.entrySet().iterator();
		Map.Entry<String , String > entrySet;
		while(itr.hasNext()){
			entrySet=itr.next();
			str = replaceAll(str,entrySet.getKey(),entrySet.getValue());
		}*/		
		return str ;
	}//P_E_ENCODING ends

	 // BB_BUG_42771 
        //P_SUPP_B_53369 Modified By Vikram Raj added values for "[" & "]"
	
	 public static String reverseFilterValue(String pValue) 
	 {
		 
	      if (pValue == null || pValue.length() == 0)
	         return (pValue);
	     
	      pValue=replaceAll(pValue,"&lt;", "<");
	      pValue=replaceAll(pValue,"&gt;", ">");
	      pValue=replaceAll(pValue,"&amp;", "&");
	      pValue=replaceAll(pValue,"&quot;", "\"");
	      pValue=replaceAll(pValue,"&#35;", "#");
	      pValue=replaceAll(pValue,"<br>", "\n");
	      pValue=replaceAll(pValue,"&#63;", "?");
	      pValue=replaceAll(pValue,"&#39;", "'");
	      pValue=replaceAll(pValue,"&#92;", "\\");
	      pValue=replaceAll(pValue,"&#37;", "%");
	      pValue=replaceAll(pValue,"&#43;", "+");
	      pValue=replaceAll(pValue,"&#61;", "=");
	      pValue=replaceAll(pValue,"&apos;","'");
	      pValue=replaceAll(pValue,"&nbsp;"," ");
	      pValue=replaceAll(pValue,"&#91;","[");
	      pValue=replaceAll(pValue,"&#93;","] ");
	      pValue=replaceAll(pValue,"&#47;","/");
	      pValue=replaceAll(pValue,"&#146;","'");
	      pValue=replaceAll(pValue,"&#148;","\"");
	      pValue=replaceAll(pValue,"&#8217;","’");//P_CM_B_43424
	      pValue=replaceAll(pValue,"&#8211;","–");
	      return pValue;
	 }
	 // P_LP_B_37629 start
	 public static String reverseFilterValueWBR(String pValue) 
	 {
		 
	      if (pValue == null || pValue.length() == 0)
	         return (pValue);
	     
	      pValue=replaceAll(pValue,"&lt;", "<");
	      pValue=replaceAll(pValue,"&gt;", ">");
	      pValue=replaceAll(pValue,"&amp;", "&");
	      pValue=replaceAll(pValue,"&quot;", "\"");
	      pValue=replaceAll(pValue,"&#35;", "#");
	      pValue=replaceAll(pValue,"&#63;", "?");
	      pValue=replaceAll(pValue,"&#39;", "'");
	      pValue=replaceAll(pValue,"&#92;", "\\");
	      pValue=replaceAll(pValue,"&#37;", "%");
	      pValue=replaceAll(pValue,"&#43;", "+");
	      pValue=replaceAll(pValue,"&#61;", "=");
	      pValue=replaceAll(pValue,"&apos;","'");
	      pValue=replaceAll(pValue,"&nbsp;"," ");
	      pValue=replaceAll(pValue,"&#91;","[");
	      pValue=replaceAll(pValue,"&#93;","] ");
	      pValue=replaceAll(pValue,"&#47;","/");
	      pValue=replaceAll(pValue,"&#146;","'");
	      pValue=replaceAll(pValue,"&#148;","\"");
	      return pValue;
	 }
	// P_LP_B_37629 ends
	 /**
	  * P_ENH_SMS_CAMPAIGN
	  * @author himanshi
	  * @date 14 June 2013
	  * @param pValue
	  * @return
	  */
	 public static String reverseFilterValueNew(String pValue)
	    {
	      if ((pValue == null) || (pValue.length() == 0)) {
	        return pValue;
	      }
	      pValue = pValue.replace("&lt;", "<");
	      pValue = pValue.replace("&gt;", ">");
	      pValue = pValue.replace("&amp;", "&");
	      pValue = pValue.replace("&quot;", "\"");
	      pValue = pValue.replace("<br>", "\n");
	      pValue = pValue.replace("<p>", "");
	      pValue = pValue.replace("</p>", "");
	      pValue = pValue.replace("&#63;", "?");
	      pValue = pValue.replace("&#39;", "'");
	      pValue = pValue.replace("&#35;", "#");
	      pValue = pValue.replace("&#92;", "\\");
	      pValue = pValue.replace("&#37;", "%");
	      pValue = pValue.replace("&#43;", "+");
	      pValue = pValue.replace("&#61;", "=");
	      pValue = pValue.replace("&apos;", "'");
	      pValue = pValue.replace("&nbsp;", " ");
	      pValue = pValue.replace("&#91;", "[");
	      pValue = pValue.replace("&#93;", "] ");
	      pValue = pValue.replace("&#47;", "/");
	      pValue = pValue.replace("&#146;", "'");
	      pValue = pValue.replace("&#148;", "\"");
	      return pValue;
	    }
	
	/**
	 * @author Dheerendra  
	 * @param String   
	 * This method replace all special characters in the query
	 * which can be saved in database. 
	 */
	public static String forSpecialCharForDB(String pValue) {

		if (pValue == null || pValue.length() == 0)
			return (pValue);

		char[] content = pValue.toCharArray();
		int size = content.length;
		StringBuffer result = new StringBuffer(size + 50);
		int mark = 0;
		String replacement = null;
		for (int i = 0; i < size; i++) {
			switch (content[i]) {
			
			case '"':
				replacement = "\\\"";
				break;
			
			case '?':
				replacement = "\\?";
				break;
			case '\'':
				replacement = "\\\'";
				break;
			
			case '\\':
				replacement = "\\\\";
				break;
		
		
		
			}
			
			if (replacement != null) {
				if (mark < i) {
					result.append(content, mark, i - mark);
				}
				result.append(replacement);
				replacement = null;
				mark = i + 1;
			}
		}
		if (mark < size) {
			result.append(content, mark, size - mark);
		}
		return (result.toString());

	}

	/**
	 * @author dheerendra
	 * @param  HttpSession 
	 * return the last submenu name in financials .
	 * regarding bug id --- 37281
	 * 18th of july 2008
	 */
	
	public static String getLastSubmenuNameForFinancial(HttpSession session){
		String lastSubMenuName = "notAvailable";
		
		if(FinancialPrivelegesID.canAccessReports(session))
			lastSubMenuName = "reports";
		else if(FinancialPrivelegesID.canAccessDocViewDocSummary(session) || FinancialPrivelegesID.canAccessDocSelectFranId(session))
			lastSubMenuName = "finDocs";
		else if(FinancialPrivelegesID.canAccessAfViewAfShareSummary(session))
			lastSubMenuName = "areafranchise";
		else if(FinancialPrivelegesID.canAccessStoreSummaryViewStoreSummary(session))
			lastSubMenuName = "dashboard";
		else if(FinancialPrivelegesID.canAccessEFTTAreaFranDetailSumary(session) || FinancialPrivelegesID.canAccessEFTTFranDetailSummary(session))
			lastSubMenuName = "eft";
		else if(FinancialPrivelegesID.canAccessPayDisplaysPaymentSummary(session))
			lastSubMenuName = "payment";			
		else if(FinancialPrivelegesID.canAccessRoyaltiesViewSummary(session))
			lastSubMenuName = "vinvoice";
		else if(FinancialPrivelegesID.canAccessPLViewPLSummary(session))
			lastSubMenuName = "profitloss";
		else if(FinancialPrivelegesID.canAccessSalesSummaryPage(session))
			lastSubMenuName = "vsales";
		else if(FinancialPrivelegesID.canAccessStoreSummaryViewDashBoard(session) || FinancialPrivelegesID.canAccessFinancialHomePage(session))
			lastSubMenuName = "home";
		else
			lastSubMenuName = "finDocs";
		return lastSubMenuName;
		
	}
	
	
	public static String createBlankRow(){
		//return "<tr><td>&nbsp;</td></tr>";
		return "<tr><td height='10'></td></tr>";
	}
	

public static Info getBlankInfo(){
	Info info1 = new Info();
	info1.set("displayName","");
	info1.set("value","");
	info1.set("type","display");
	return info1;
}

public static String getCountryNameById(String countryId) {
	String countryName = "";
	Map<String,Object> countryMap = CacheMgr.getCountryCache().getCountry(countryId);
	if (countryMap != null && !countryMap.isEmpty())
	{
		countryName = (String)countryMap.get(FieldNames.COUNTRY_NAME);
	}
	
	/*Connection con = null;
	Statement stmt = null;
	java.sql.ResultSet rs = null;
	String query = "SELECT NAME FROM COUNTRIES WHERE COUNTRY_ID = "
			+ countryId;
	try {
		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			
			countryName = rs.getString("NAME");
		}

	}

	catch (Exception e) {
		logger.info("Exception in getCountryNameById");
	}

	finally {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		}
		try {
			if (con != null)
				DBConnectionManager.getInstance().freeConnection(con);
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}*/
	return countryName;
}

// This function return the string in table form for showing logo and company Address on the top of jsp
public static String getTopWithLogo(HttpServletRequest request,String franchiseeNo,String userLevel,boolean isPdf) throws ConnectionException{

	StringBuffer logoWithAddress = new StringBuffer("");
	
	logoWithAddress.append("<table class=\"\" width='100%' border='0' bgcolor=\"\" cellspacing='0' cellpadding='0'> ");
	logoWithAddress.append("<tr height=\"20\">");
	//logoWithAddress.append("<td width=\"1%\">");
	//logoWithAddress.append("</td>");
	logoWithAddress.append("<td align = \"left\" width=\"15%\">");
	if(isPdf){
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE
	}else{
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE	
	}
	logoWithAddress.append("</td>");
	logoWithAddress.append("<td align=\"left\" class=\"text\">");
	//logoWithAddress.append(getCompanyAddress(franchiseeNo,userLevel,isPdf));
	logoWithAddress.append("</td>");
	logoWithAddress.append("</tr>");
	logoWithAddress.append("</table>");
	if(isPdf){
		return PortalUtils.getCompanyAddress(franchiseeNo,userLevel,isPdf);
	}
	return logoWithAddress.toString();
}
//This function return the string in table form for showing logo and optional company Address on the top of jsp
public static String getTopWithLogo(HttpServletRequest request,String franchiseeNo,String userLevel,boolean isPdf,boolean showAddress) throws ConnectionException{
	StringBuffer logoWithAddress = new StringBuffer("");
	
	logoWithAddress.append("<table class=\"\" width='100%' border='0' bgcolor=\"\" cellspacing='0' cellpadding='0'> ");
	logoWithAddress.append("<tr height=\"20\">");
	//logoWithAddress.append("<td width=\"2%\">");
	//logoWithAddress.append("</td>");
	logoWithAddress.append("<td align = \"left\" width=\"15%\">");
	if(isPdf){
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE
	}else{
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE	
	}
	logoWithAddress.append("</td>");
	if(showAddress)
	{
	logoWithAddress.append("<td align=\"left\" class=\"text\">safs");
	logoWithAddress.append(getCompanyAddress(franchiseeNo,userLevel,isPdf));
	logoWithAddress.append("</td>");
	}
	logoWithAddress.append("</tr>");
	logoWithAddress.append("</table>");
	if(isPdf ){
		if(showAddress)
		return getCompanyAddress(franchiseeNo,userLevel,isPdf);
		else
	    return "";
	}
        logger.info("Portalutils@getTopwithlogo"+logoWithAddress.toString());
	return logoWithAddress.toString();
}

//This function return the string in table form for showing logo and optional company Address on the top of jsp
public static String getTopWithLogo(HttpServletRequest request,String franchiseeNo,String userLevel,boolean isPdf,boolean showAddress,String purchaseOrder) throws ConnectionException{
	StringBuffer logoWithAddress = new StringBuffer("");
	
	logoWithAddress.append("<table class=\"\" width='100%' border='0' bgcolor=\"\" cellspacing='0' cellpadding='0'> ");
	logoWithAddress.append("<tr height=\"20\">");
	//logoWithAddress.append("<td width=\"2%\">");
	//logoWithAddress.append("</td>");
	logoWithAddress.append("<td align = \"left\" width=\"15%\">");
	if(isPdf){
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().PDF_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE
	}else{
		logoWithAddress.append("<img src=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_NAME+"\" height=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_HEIGHT+"\" width=\""+com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().BUILD_LOGO_WIDTH+"\" name=\"logo\" id=\"logo\" border='0'>");//MUL_TEN_PHASE_ONE	
	}
	logoWithAddress.append("</td>");
	if(showAddress)
	{
	logoWithAddress.append("<td align=\"left\" class=\"text\">");
	logoWithAddress.append(getCompanyAddress(franchiseeNo,userLevel,isPdf));
	logoWithAddress.append("</td>");
	}
	logoWithAddress.append("</tr>");
	logoWithAddress.append("</table>");
	if(isPdf ){
		if(showAddress)
		return getCompanyAddress(franchiseeNo,userLevel,isPdf,purchaseOrder);
		else
	    return "";
	}
	return logoWithAddress.toString();
}


public static String getCompanyAddress(String franchiseeNo,String userLevel,boolean isPdf) throws ConnectionException{

	LocationMgr locationMgr=LocationMgr.newInstance();
	LocationsDAO locationsDAO=locationMgr.getLocationsDAO(); 
	 Info adressInfo = (Info)locationsDAO.getPdfAddressInfo(franchiseeNo);
	
	 String newLineString = "";
	 if(isPdf){
		 newLineString = "\n";
	 }
	 else{
		 newLineString = "<br>";
	 }
	 
	 int size = adressInfo.size();
	    String sZipCode			= "";
	    String sCountryId		= "";
	    String sAddress			= "";
	    String sAddressId			= "";
		String sCity				= "";
		String sRegionNo			= "";
		String sStorePhone		= "";
		String sStoreMobile		= "";
		String sStoreFax			= "";
		String sStoreEmail		= "";
		StringBuffer pdfAddress = new StringBuffer("");
		String LocationName="";
		String companyName = "";
		
		for(int i = 0; i < size; i++ )
		{   
			sAddressId = adressInfo.getString(FieldNames.ADDRESS_ID) ;
			sAddress = adressInfo.getString(FieldNames.ADDRESS) ;
			companyName = adressInfo.getString("FRANCHISEE_NAME") ;
			sCountryId = adressInfo.getString(FieldNames.COUNTRY) ;
			sZipCode = adressInfo.getString(FieldNames.ZIPCODE) ;
			sCity = adressInfo.getString(FieldNames.CITY) ;
			sRegionNo = adressInfo.getString(FieldNames.STATE) ;
			sStorePhone = adressInfo.getString(FieldNames.PHONE_NUMBERS) ;
			sStoreMobile = adressInfo.getString(FieldNames.MOBILE_NUMBERS) ;
			sStoreFax = adressInfo.getString(FieldNames.FAX_NUMBERS) ;
			sStoreEmail = adressInfo.getString(FieldNames.EMAIL_IDS) ;
		}
		
		//added for P_B_CT_26484 starts
		if("1".equals(userLevel) && StringUtil.isValid(companyName))
			LocationName = "<span class='invoicetxt_b'>"+companyName+"</span>";
		else //ends
		 if(userLevel!=null && userLevel.trim().equals("1")){
			String franchiseeAndAreaNameCSV = AdminMgr.newInstance().getStoreDAO().getFranchiseeAndAreaNameCSV(franchiseeNo);
			String franchiseeAndAreaName [] = franchiseeAndAreaNameCSV.split(",");
			
			LocationName=franchiseeAndAreaName[0];
			}else{
			MasterDataMgr masterDataMgr = MasterDataMgr.newInstance();
			LocationName=masterDataMgr.getMasterDataDAO().getValue(10009,"0");
			}
	
	
	Pattern pattern = Pattern.compile("[0-9]{10}");
	boolean pureDigits = false;
	Matcher matcher = null; 
            if(sStorePhone == null) 

            	sStorePhone = "";

		pureDigits = false;
		matcher = pattern.matcher(sStorePhone);
		pureDigits = matcher.find();
		
		if(pureDigits && sStorePhone.length()==10)
			sStorePhone = com.home.builderforms.PortalUtils.formatPhoneNo(sStorePhone,sCountryId);

		 if(sStoreMobile == null) 
			 sStoreMobile = "";

			pureDigits = false;
			matcher = pattern.matcher(sStoreMobile);
			pureDigits = matcher.find();
			
			if(pureDigits && sStoreMobile.length()==10)
				sStoreMobile = com.home.builderforms.PortalUtils.formatPhoneNo(sStoreMobile,sCountryId);
            //BB_B_39568    
         if (sCountryId != null &&  !sCountryId.equals("") &&  sCountryId.equals("USA")){
        	
        	 if(LocationName!=null && !"".equals(LocationName)){
        		 pdfAddress.append(LocationName+newLineString);
        	 }
        	 if(sAddress!=null && !"".equals(sAddress)){
        		 pdfAddress.append(sAddress+newLineString);
        	 }
        	 if(sCity!=null && !"".equals(sCity)){
        		 pdfAddress.append(sCity).append(", ");
        	 }
        	 if(sRegionNo!=null && !"".equals(sRegionNo)){
        		 pdfAddress.append(sRegionNo);
        	 }
        	 if(sZipCode!=null && !"".equals(sZipCode)){
        		 pdfAddress.append("  ").append(sZipCode);
        	 }
        	 if(sCountryId!=null && !"".equals(sCountryId)){
        		 pdfAddress.append(newLineString).append(sCountryId);
        	 }
        	 if(sStorePhone!=null && !"".equals(sStorePhone)){
        		 pdfAddress.append(newLineString).append("Phone : "+sStorePhone);
        	 }
        	 /*
        	 if(sZipCode!=null && !"".equals(sZipCode))
	         pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+" - "+sZipCode+newLineString+ "Phone : "+sStorePhone);
        	 else
        		 pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+sZipCode+newLineString+ "Phone : "+sStorePhone);
		   */}else{
			   
			   if(LocationName!=null && !"".equals(LocationName)){
	        		 pdfAddress.append(LocationName+newLineString);
	        	 }
	        	 if(sAddress!=null && !"".equals(sAddress)){
	        		 pdfAddress.append(sAddress+newLineString);
	        	 }
	        	 if(sCity!=null && !"".equals(sCity)){
	        		 pdfAddress.append(sCity).append(", ");
	        	 }
	        	 if(sRegionNo!=null && !"".equals(sRegionNo)){
	        		 pdfAddress.append(sRegionNo);
	        	 }
	        	 if(sZipCode!=null && !"".equals(sZipCode)){
	        		 pdfAddress.append("  ").append(sZipCode);
	        	 }
	        	 if(sCountryId!=null && !"".equals(sCountryId)){
	        		 pdfAddress.append(newLineString).append(sCountryId);
	        	 }
	        	 if(sStorePhone!=null && !"".equals(sStorePhone)){
	        		 pdfAddress.append(newLineString).append("Phone : "+sStorePhone);
	        	 }
	        	 
			  /* if(sZipCode!=null && !"".equals(sZipCode))
			 pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+" - "+sZipCode+newLineString+sCountryId+ newLineString+"Phone : "+sStorePhone);
			   else
				   pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+sZipCode+newLineString+sCountryId+ newLineString+"Phone : "+sStorePhone);*/  
		   }
			if (sStoreMobile != null &&  !sStoreMobile.equals("") &&  !sStoreMobile.equals("-1")){
                                pureDigits = false;
                                matcher = pattern.matcher(sStoreMobile);
                                pureDigits = matcher.find();
                                if(pureDigits && sStoreMobile.length()==10)
                                    sStoreMobile = com.home.builderforms.PortalUtils.formatPhoneNo(sStoreMobile,sCountryId);
				pdfAddress=pdfAddress.append(newLineString+"Mobile : "+sStoreMobile);
			}
			if (sStoreFax != null &&  !sStoreFax.equals("") &&  !sStoreFax.equals("-1")){
				pdfAddress=pdfAddress.append(newLineString+"Fax : "+sStoreFax);
			}
			if (sStoreEmail != null &&  !sStoreEmail.equals("") &&  !sStoreEmail.equals("-1")){
			
				//pdfAddress=pdfAddress.append(newLineString+com.home.builderforms.Constants.LBL_EMAIL_ID +" : "+sStoreEmail);
                                pdfAddress=pdfAddress.append(newLineString+sStoreEmail);
			}
			
			
		return pdfAddress.toString();	
		
}

public static String getCompanyAddress(String franchiseeNo,String userLevel,boolean isPdf,String purchaseOrder) throws ConnectionException{
	
	LocationMgr locationMgr=LocationMgr.newInstance();
	LocationsDAO locationsDAO=locationMgr.getLocationsDAO();
	 Info adressInfo = (Info)locationsDAO.getPdfAddressInfo(franchiseeNo);
	 String newLineString = "";
	 if(isPdf){
		 newLineString = "\n";
	 }
	 else{
		 newLineString = "<br>";
	 }
	 
	 int size = adressInfo.size();
	    String sZipCode			= "";
	    String sCountryId		= "";
	    String sAddress			= "";
	    String sAddressId			= "";
		String sCity				= "";
		String sRegionNo			= "";
		String sStorePhone		= "";
		String sStoreMobile		= "";
		String sStoreFax			= "";
		String sStoreEmail		= "";
		StringBuffer pdfAddress = new StringBuffer("");
		String LocationName="";
		
		if(userLevel!=null && userLevel.trim().equals("1")){
			String franchiseeAndAreaNameCSV = AdminMgr.newInstance().getStoreDAO().getFranchiseeAndAreaNameCSV(franchiseeNo);
			String franchiseeAndAreaName [] = franchiseeAndAreaNameCSV.split(",");
			
			LocationName=franchiseeAndAreaName[0];
			logger.info("franchiseeAndAreaName[0]"+LocationName);
			
			}else{
			//MasterDataMgr masterDataMgr = MasterDataMgr.newInstance();
			LocationName=getFranchiseeAndAreaNameCSVofPurchaseOrder(purchaseOrder);	
			logger.info("masterDataMgr.getMasterDataDAO().getValue(10009,"+LocationName);
			}
		
		
		for(int i = 0; i < size; i++ )
		{   
			sAddressId = adressInfo.getString(FieldNames.ADDRESS_ID) ;
			sAddress = adressInfo.getString(FieldNames.ADDRESS) ;
			sCountryId = adressInfo.getString(FieldNames.COUNTRY) ;
			sZipCode = adressInfo.getString(FieldNames.ZIPCODE) ;
			sCity = adressInfo.getString(FieldNames.CITY) ;
			sRegionNo = adressInfo.getString(FieldNames.STATE) ;
			sStorePhone = adressInfo.getString(FieldNames.PHONE_NUMBERS) ;
			sStoreMobile = adressInfo.getString(FieldNames.MOBILE_NUMBERS) ;
			sStoreFax = adressInfo.getString(FieldNames.FAX_NUMBERS) ;
			sStoreEmail = adressInfo.getString(FieldNames.EMAIL_IDS) ;
			
			
		}
	
	
	Pattern pattern = Pattern.compile("[0-9]{10}");
	boolean pureDigits = false;
	Matcher matcher = null; 
            if(sStorePhone == null) 

            	sStorePhone = "";

		pureDigits = false;
		matcher = pattern.matcher(sStorePhone);
		pureDigits = matcher.find();
		
		if(pureDigits && sStorePhone.length()==10)
			sStorePhone = com.home.builderforms.PortalUtils.formatPhoneNo(sStorePhone,sCountryId);

		 if(sStoreMobile == null) 
			 sStoreMobile = "";

			pureDigits = false;
			matcher = pattern.matcher(sStoreMobile);
			pureDigits = matcher.find();
			
			if(pureDigits && sStoreMobile.length()==10)
				sStoreMobile = com.home.builderforms.PortalUtils.formatPhoneNo(sStoreMobile,sCountryId);
			   //BB_B_39568          
         if (sCountryId != null &&  !sCountryId.equals("") &&  sCountryId.equals("USA")){
        	 
        	 if(LocationName!=null && !"".equals(LocationName)){
        		 pdfAddress.append(LocationName+newLineString);
        	 }
        	 if(sAddress!=null && !"".equals(sAddress)){
        		 pdfAddress.append(sAddress+newLineString);
        	 }
        	 if(sCity!=null && !"".equals(sCity)){
        		 pdfAddress.append(sCity).append(", ");
        	 }
        	 if(sRegionNo!=null && !"".equals(sRegionNo)){
        		 pdfAddress.append(sRegionNo);
        	 }
        	 if(sZipCode!=null && !"".equals(sZipCode)){
        		 pdfAddress.append(" - ").append(sZipCode);
        	 }
        	 if(sCountryId!=null && !"".equals(sCountryId)){
        		 pdfAddress.append(newLineString).append(sCountryId);
        	 }
        	 if(sStorePhone!=null && !"".equals(sStorePhone)){
        		 pdfAddress.append(newLineString).append("Phone : "+sStorePhone);
        	 }
        	 /*if(sZipCode!=null && !"".equals(sZipCode))
	         pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+" - "+sZipCode+newLineString+newLineString+sCountryId+newLineString+ "Phone : "+sStorePhone);
        	 else
        		 pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+sZipCode+newLineString+newLineString+sCountryId+newLineString+ "Phone : "+sStorePhone);
        		 */
		   }else{
			   
			   if(LocationName!=null && !"".equals(LocationName)){
	        		 pdfAddress.append(LocationName+newLineString);
	        	 }
	        	 if(sAddress!=null && !"".equals(sAddress)){
	        		 pdfAddress.append(sAddress+newLineString);
	        	 }
	        	 if(sCity!=null && !"".equals(sCity)){
	        		 pdfAddress.append(sCity).append(", ");
	        	 }
	        	 if(sRegionNo!=null && !"".equals(sRegionNo)){
	        		 pdfAddress.append(sRegionNo);
	        	 }
	        	 if(sZipCode!=null && !"".equals(sZipCode)){
	        		 pdfAddress.append(" - ").append(sZipCode);
	        	 }
	        	 if(sCountryId!=null && !"".equals(sCountryId)){
	        		 pdfAddress.append(newLineString).append(sCountryId);
	        	 }
	        	 if(sStorePhone!=null && !"".equals(sStorePhone)){
	        		 pdfAddress.append(newLineString).append("Phone : "+sStorePhone);
	        	 }
			  /* if(sZipCode!=null && !"".equals(sZipCode))
			 pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+" - "+sZipCode+newLineString+sCountryId+ newLineString+"Phone : "+sStorePhone);
			   else
				   pdfAddress=pdfAddress.append(LocationName+newLineString+sAddress+newLineString+sCity+", "+sRegionNo+sZipCode+newLineString+sCountryId+ newLineString+"Phone : "+sStorePhone);
				   */
		   }
			if (sStoreMobile != null &&  !sStoreMobile.equals("") &&  !sStoreMobile.equals("-1")){
                                pureDigits = false;
                                matcher = pattern.matcher(sStoreMobile);
                                pureDigits = matcher.find();
                                if(pureDigits && sStoreMobile.length()==10)
                                    sStoreMobile = com.home.builderforms.PortalUtils.formatPhoneNo(sStoreMobile,sCountryId);
				pdfAddress=pdfAddress.append(newLineString+"Mobile : "+sStoreMobile);
			}
			if (sStoreFax != null &&  !sStoreFax.equals("") &&  !sStoreFax.equals("-1")){
				pdfAddress=pdfAddress.append(newLineString+"Fax : "+sStoreFax);
			}
			if (sStoreEmail != null &&  !sStoreEmail.equals("") &&  !sStoreEmail.equals("-1")){
			
				pdfAddress=pdfAddress.append(newLineString+com.home.builderforms.Constants.LBL_EMAIL_ID +" : "+sStoreEmail);
			}
			
			
		return pdfAddress.toString();	
		
}


private static String getFranchiseeAndAreaNameCSVofPurchaseOrder(
		String purchaseOrder)throws ConnectionException{
Connection con = null;
String finalValue = "";
logger.info("purchaseOrder"+purchaseOrder);

PreparedStatement pstmt = null;
java.sql.ResultSet rs = null;
try {
	con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

	pstmt = con
			.prepareStatement("SELECT AREA_NAME,FRANCHISEE_NAME FROM FRANCHISEE F,USERS U,FRANCHISEE_USERS FU,AREAS A,SUPPLIER_PURCHASEORDER SPO WHERE F.AREA_ID = A.AREA_ID AND F.FRANCHISEE_NO=FU.FRANCHISEE_NO AND U.USER_NO=SPO.USER_NO AND FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO  AND SPO.PURCHASEORDER_NO=?");
	pstmt.setString(1, purchaseOrder);

	rs = pstmt.executeQuery();
	if (rs.next())
		finalValue = rs.getString("FRANCHISEE_NAME");/* + ","
				+ rs.getString("AREA_NAME") + " ";*/
} catch (SQLException e) {
	Debug.print(e);
} finally {
	try {
		if (rs != null)
			rs.close();
	} catch (Exception e) {
	}
	try {
		if (pstmt != null)
			pstmt.close();
	} catch (Exception e) {
	}
	try {
		if (con != null)
			DBConnectionManager.getInstance().freeConnection(con);
	} catch (Exception e) {
		logger.error("Exception ", e);
	}
}

return finalValue;

}

public static String removeExtraString(String data){
	
	String returnData = "";
	if(data.indexOf("nextUrl")!=-1){
		returnData =  data.substring(data.indexOf("<u>")+3,data.indexOf("</u>"));
		
	}
	
	else if(data.indexOf("<u>")!=-1){
		returnData =  data.substring(data.indexOf("<u>")+3,data.indexOf("</u>"));
		
	}
	else if(data.indexOf("<input")!=-1){
	  returnData = data.substring(0,data.indexOf("<input"));
	}
	else if(data.indexOf("<b>")!=-1){
		returnData =  data.substring(data.indexOf(">")+1,data.indexOf("</b>"));
	}
	
	else if(data.indexOf("<br>")!=-1){
		returnData =  data.replaceAll("<br>","\n");
	}
	
	else if(data.indexOf("<span")!=-1){
		returnData =  data.substring(data.indexOf("</span>")+7);
	}
	
	else if(data.indexOf("<img")!=-1){
		returnData =  data.substring(0,data.indexOf("<img"));
	}
	else{
		returnData = data;
	}
	return returnData;
}

public static Color returnColorObject(String rowColor){
	Color color = null;
	
	if(rowColor!=null && rowColor.equals("tb_data")){
		color =  AppColor.getColorObject(Constants.RGB_tb_data_dark);
	}
	else if(rowColor!=null && rowColor.equals("tb_data_r")){
		color =  AppColor.getColorObject(Constants.RGB_tb_data_dark_red);
	}
	else if(rowColor!=null && rowColor.equals("tb_data_g")){
		color =   AppColor.getColorObject(Constants.RGB_tb_data_dark_green_new);
	}
	else if(rowColor!=null && rowColor.equals("tb_data_blue")){
		color =   AppColor.getColorObject(Constants.RGB_tb_data_dark_blue);
	}
	
	else if(rowColor!=null && rowColor.equals("text_dbg")){
		color =  AppColor.getColorObject(Constants.RGB_tb_data_dark_new);
	}
	else if(rowColor!=null && rowColor.equals("tb_data_dark_red")){
		
		color =  AppColor.getColorObject(Constants.RGB_tb_data_dark_red_new);
	}
	
	else if(rowColor!=null && rowColor.equals("tb_data_g")){
		color =   AppColor.getColorObject(Constants.RGB_tb_data_dark_green_new);
	}
	else if(rowColor!=null && rowColor.equals("tb_data_orange")){
		color =   AppColor.getColorObject(Constants.RGB_tb_data_dark_blue_new);
	}
	//P_FIN_B_76687 starts
	else if(rowColor!=null && rowColor.equals("tb_data_fog")){
		color =   AppColor.getColorObject(Constants.RGB_tb_data_fog_new);
	}
	//P_FIN_B_76687 ends
	return color;
}

//P_ColorCode76854327  Nishant starts
public static Color returnColorObjectDynamic(String rowColor){
	Color color = null;	
	String rowColor1 = rowColor;
	if(StringUtil.isValid(rowColor)){
		try{
			rowColor = rowColor.substring(1);	
			int r= Integer.parseInt(rowColor.substring(0,2),16);
			int g= Integer.parseInt(rowColor.substring(2,4),16);
			int b= Integer.parseInt(rowColor.substring(4),16);
			color = new Color(r, g, b); 
		}
		catch(NumberFormatException e){
			//System.out.println("\n\nNumberFormatException------------------"+rowColor);
			color = returnColorObject(rowColor1);
		}
	}
	return color;
}
//P_ColorCode76854327 ends

/**
 * @author Nishant Prabhakar
 * @param  SequenceMap,String 
 * @return String
 * @date 21 May 2012
 * @purpose returns String containg combo options.
 */
public static String getComboByMap(SequenceMap sMap,String selected)
{
	return getComboByMap(sMap, selected,null);
}

public static String getComboByMap(SequenceMap sMap,String selected,String defaultValue)
{
	return getComboByMap(sMap, selected, defaultValue,false);
}

public static String getComboByMap(SequenceMap sMap,String selected,String defaultValue,boolean isMultiSelect){
	
	StringBuffer returnValue = new StringBuffer();
	if(!StringUtil.isValid(defaultValue))
	{
		defaultValue="Select All";
	}
	try{			
		if(StringUtil.isValidNew(selected)){
			returnValue.append("<option value='-1'>"+LanguageUtil.getString(defaultValue)+"</option>");
		}
		else{
			returnValue.append("<option value=\"-1\" selected>"+LanguageUtil.getString(defaultValue)+"</option>");
		}		

		boolean isAllSelected=false;
		
		if("-1".equals(selected) && isMultiSelect )
				isAllSelected=true;		
		
		if (sMap != null){
			
			ArrayList  keys = (ArrayList)sMap.keys();
			ArrayList  values = (ArrayList)sMap.values();
			String matchingKey = "-1";
			
			if(StringUtil.isValidNew(selected)){
				selected = ","+selected.replaceAll(" ", "").trim()+",";
			}
			
			for (int i = 0; i < keys.size(); i++) 
			{
				matchingKey = (String)keys.get(i);
				
				if(matchingKey.equals("C")||matchingKey.equals("R")||matchingKey.equals("F"))
				{
					returnValue.append("<option disabled value="+ keys.get(i)+">" + values.get(i)+ "</option>");
				}
				else
				if((StringUtil.isValidNew(selected) && selected.indexOf(","+matchingKey+",")!= -1) || isAllSelected ){
					returnValue.append("<option selected value=\""+ keys.get(i)+"\">" + values.get(i)+ "</option>");
				}
				else{
					returnValue.append("<option value="+ keys.get(i)+">" + values.get(i)+ "</option>");
				}
			}
			
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
		logger.error("Exception ", e);
	}	
	return returnValue.toString();
}

/**
 * @author Dheerendra
 * @return Info
 * @date 12 September 2008
 * @purpose returns Info for displaying data.
 */
public static Info getHtmlCodeInfo(String displayName, String htmlCode){
	return getHtmlCodeInfo(displayName, htmlCode, null);
}
public static Info getHtmlCodeInfo(String displayName, String htmlCode, String colspan){
	Info info = new Info();
	if(colspan == null)
		colspan = "2";
	info.set("displayName", displayName);
	info.set("type","htmlcode");
	//info.set("colspan", colspan);
	info.set("addhtml", htmlCode);
    return info;
}

/**
 * @author Dheerendra
 * @return Info
 * @date 12 September 2008
 * @purpose returns Info for combo.
 */
public static Info getComboInfo(String cmbName, Info dataInfo, String displayName, String selectedValue){
	Info comboInfo = new Info();
	Combo combo = new Combo(cmbName);
	if(displayName != null)
		combo.setDisplayName(displayName);
	if(selectedValue != null)
		combo.setComboValue(selectedValue);
	combo.setInfo(dataInfo);
	comboInfo.set("type", combo);
	comboInfo.set("align", "left");
	//comboInfo.set("colspan", "2");
	return comboInfo;
}

public static boolean isExchangeRateEnable(){
	boolean isExgRateEnable = false;
	String dataValue = null;
	
	dataValue = CommonMgr.newInstance().getCommonFinancialsDAO().getEnaDisaStatusFromFMD();

	if(StringUtil.isValid(dataValue)){
		dataValue = dataValue.split(",")[1].trim();
		
		if(dataValue.equals(Constants.EXG_ENABLE)){
			
			isExgRateEnable = true;
		}
	}
	return isExgRateEnable;
}
//P_SO_E_STORE_NO_FILTER

/*public static String getFranchiseRoyaltyId(String salesReportId){
	return FinancialMgr.newInstance().getXlsImportDAO().getFranchiseRoyaltyId(salesReportId);
}

public static String getSingleSalesReportKey(String uploadErrorLogId){
	return FinancialMgr.newInstance().getXlsImportDAO().getSingleSalesReportKey(uploadErrorLogId);
}*/

public static boolean isPrimaryKeyExist(String tableName, String primaryKeyColumnName, String primaryKey){
	boolean isPrimaryKeyExist = false;
	com.home.builderforms.sqlqueries.ResultSet result = null;
	String query = null;
	
	try {
		query = " SELECT " + primaryKeyColumnName + " FROM " + tableName + " WHERE " + primaryKeyColumnName + " = '" + primaryKey + "'";
		result = QueryUtil.getResult(query.toString(), null);
                //Added by Saurabh Sinha for throwing null pointer exception on console of no match found
		if(result!=null && result.next()){
			isPrimaryKeyExist = true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return isPrimaryKeyExist;
}

/**
 * This method returns Max value key used in given column.
 * Column must be of long/Integer type.
 * @author YogeshT
 * @param tableName
 * @param keyColumnName
 * @return long
 * @date 11nov2008
 */
public static long getMaxIntKey(String tableName, String keyColumnName) {
	//BB_ENH_11Nov08
	long maxKey = 0;
	com.home.builderforms.sqlqueries.ResultSet result = null;
	String query = null;
	
	try {
		query = "SELECT IFNULL(MAX("+keyColumnName+"),0) AS MAX_KEY FROM "+tableName;
		result = QueryUtil.getResult(query.toString(), null);
		if(result.next()){
			maxKey = Long.parseLong(result.getString("MAX_KEY"));
		}
	} catch (Exception e) {
		e.printStackTrace();
		logger.info("Exception Occured in PortalUtils.getMaxIntKey method...");
	}
	return maxKey;
}

//P_SO_E_NO_OF_OVERDUE_TASKS Added by Ram Avtar

public static Info getInfo(String name){
	Info info = new Info();
    info.set("displayName", name);
    info.set("type", "htmlcode");
    return info;
}


public static Info getHiddenInfo(String value){
	Info info = new Info();
	info.set("type", "hidden");
	info.set("value", value);
	return info;
}

public static Info getInfo(String displayName, String htmlCode){
	Info info = new Info();
    info.set("displayName", "<B>" + displayName + "</B>");
    info.set("type", "htmlcode");
    info.set("addhtml", htmlCode);
    return info;
}

public static Info getUrgentInfoForTextBox(String name, String value){
	return getUrgentInfoForTextBox(name, value, null);
}

public static Info getUrgentInfoForTextBox(String name, String value, String colspan){
	return getUrgentInfoForTextBox(name, value, colspan, null);
}

public static Info getUrgentInfoForTextBox(String name, String value, String colspan, String cssClass){
	Info info = new Info();
	info.set("type", "text");
	if(colspan != null)
		info.set("colspan", colspan);
    info.set("displayName", "<span class = \"urgent_fields\">*</span>"+name);
    if(StringUtil.isValid(value)){
    	info.set("value", value);
    }
    if(StringUtil.isValid(cssClass)){
    	info.set("class", cssClass);
    }
    return info;
}

public static Info getComboInfo(String cmbName, Info dataInfo){
	return getComboInfo(cmbName, dataInfo, null, null);
}
public static Info getComboInfo(String cmbName, Info dataInfo, String displayName){
	return getComboInfo(cmbName, dataInfo, null, null);
}

// BB_BLOG_ENH
public static Info getDateInfo(String formName, String contextPath, String displayName, String selectedValue){
	
	return getDateInfo( formName,  contextPath,  displayName,  selectedValue,null);
}

public static Info getDateInfo(String formName, String contextPath, String displayName, String selectedValue,String colspan){
	Info info = new Info();
	info.set("type","date");
	info.set("formname", formName);
	info.set("contextname", contextPath);
	// BB_BLOG_ENH
	if(colspan!=null && !"".equals(colspan))
	info.set("colspan", "2");
	info.set("displayName", displayName);
	if(StringUtil.isValid(selectedValue))
		info.set("value", selectedValue);
	return info;
}

/*public static boolean isAdjustmentAllowed(){
	boolean isAdjAllowed = false;
	
	if(SetupPreferencesUtil.newInstance().getAdjustment().equals(Constants.ADJ_ENABLE)){
		isAdjAllowed = true;
	}
	return isAdjAllowed;
}*/
/*public static boolean isPreviousMandatory(){
	boolean isPreviousMandatory = false;
	
	if(SetupPreferencesUtil.newInstance().getFillPastDues().equals(Constants.PREVIOUS_MANDATORY)){
		isPreviousMandatory = true;
	}
	return isPreviousMandatory;
}*/


//BB_FIN_EXCH_MODIFY_BULK



	/**
	 * @author abhishek gupta on 13 jan 2008
	 * @purpose method craete where clause as string format with using filter regionID ,territoryID and countryID 
	 * @param regionID
	 * @param territoryID
	 * @param countryID
	 * @return String value
	 */
    public static String getProperWhereConditionForRTC(String regionID , String territoryID ,String countryID){
    	StringBuffer properWhereConditionForRTC = new StringBuffer("");
    	boolean isCountry = true;
 		boolean isTeritory = true;
 		//boolean isRegion   = true;
 		
 		if(countryID !=null && !"".equals(countryID) && !"-1".equals(countryID) && !"null".equalsIgnoreCase(countryID)){
 			properWhereConditionForRTC.append(" AND F.COUNTRY_ID=").append(countryID);	
				isCountry = false;
			}
			
			if(isCountry){
	 			
	 			if(territoryID !=null && !"".equals(territoryID) && !"-1".equals(territoryID) && !"null".equalsIgnoreCase(territoryID)){					
	 				properWhereConditionForRTC.append(" AND T.TERRITORY_ID=").append(territoryID);
	 				isTeritory = false ;
	 			}	
	 			if(isTeritory){
	 			
	 			if(regionID !=null && !"".equals(regionID) && !"-1".equals(regionID) && !"null".equalsIgnoreCase(regionID)){					
	 				properWhereConditionForRTC.append(" AND R.REGION_ID=").append(regionID);
	 			}
	 			}
	 	    }
    	return properWhereConditionForRTC.toString();
    	
    }
    /**
     * @author abhishek gupta on 13 jan 2008
     * @param fromDate
     * @return
     */
	public static SequenceMap getColumnNameMapForSameStoreSaleReport(String fromDate){
		 String date = fromDate;
		 int month = Integer.parseInt(date.substring(5,7));
 		 int year  =  Integer.parseInt(date.substring(0,4));
 		 //int year1 = year - 1;
 		 int month1 = month;
 		 String months[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
 		 int month2 =1;
 		 
 		 SequenceMap columnNameMap = new SequenceMap();
 		 for(int i=1;i<=12;i++){
 			 if(month1<=12){
 				columnNameMap.put(String.valueOf(month1), months[month1-1]);
 				month1++;
 			 }
 			 else{
 				columnNameMap.put(String.valueOf(month2), months[month2-1]);
 				month2++;
 			 }
 		 }
 		 
 		 logger.info("this is column map on Portal Utils ---- "+columnNameMap);
 		 return columnNameMap;
	} 
	
	/**
	 * @author abhishek gupta on 13 jan 2008
	 * @return
	 */
	public static String putUrgentFields(){
		return putUrgentFields(false);
	}
	public static String putUrgentFields(boolean forExcel){
		if(forExcel){
			return "*";
		}
		else{
			return Constants.PUT_STORE_ID_NOTIFICATION_MARK;
		}	
	}
	
	 /*Modified By Pratap
     * TableName changed from MASTER_DATA   to MASTER_DATA_FOR_SCHEDULER
     * 
     * */           
    public static String getMasterDataFromDataType(String status) throws ConnectionException{

        Connection con = null;
        String statusName="";
        PreparedStatement pstmt= null ;
        java.sql.ResultSet rs = null ;
        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            pstmt = con.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=?");
            pstmt.setString(1,status);

            rs=pstmt.executeQuery();
            //rs.next();
            if (rs.next()){
                statusName = rs.getString(1);
            }
        } catch (SQLException e){
            Debug.print(e);
        } 
        finally {
			try{
				if(rs != null) rs.close();
			}catch(Exception e){}
			try{
				if(pstmt != null) pstmt.close();
			}catch(Exception e){}
			try{
				if(con != null)
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception e){
				logger.error("Exception ", e);
			}
		}
            return statusName;
        
    }
	
  //Added By Pratap for fetching data from MASTER_DATA_FOR_SCHEDULER
	public static Info getDataValueWithParentIdForScheduler(String dataType){
		Info info = new Info();
        StringBuffer query = new StringBuffer("SELECT * FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE="+dataType+"");
        com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
        while(result.next()){
        	info.set(result.getString("PARENT_DATA_ID"), result.getString("DATA_VALUE"));
        }
        return info;
    }
	
    /**
     *P_FRANONLINE_E_53204
     * Added By Saurabh Sinha
     * This is the method to create multiple combos with selection field
     * @param comboName The Name of the combo
     * @param masterDataType The data type value of masterData
     * @param selectedData The value is to be selected
     * @param onChangeFunctionName The value is for the javascript function
     * @return String
     * @throws com.home.builderforms.ConnectionException This will throw exception if there is an error in connection with db
     */
   
     /**
     *P_FRANONLINE_E_43790
     * Added By Saurabh Sinha
     * This is the method which converts text content into hyperlink format by searching start char with http://,https://,www.
     * @param textHavingHyperLink Text content
     * @return String     
     */
    public static String convertTextToHyperlinkForHtml(String textHavingHyperLink)
    { 
        String str = "";
        textHavingHyperLink  = textHavingHyperLink.replaceAll("<br>", " <br> ");
        textHavingHyperLink  = textHavingHyperLink.replaceAll("<p>", " <p> ");
        textHavingHyperLink  = textHavingHyperLink.replaceAll("</p>", " </p> ");      //P_FRANBUZZ_B_74587
        StringTokenizer st = new StringTokenizer(textHavingHyperLink," ");
        int i=0;
        while(st.hasMoreTokens())
            {
            String str1 = st.nextToken();            
            if(str1.startsWith("http:") || str1.startsWith("https:") || str1.startsWith("www."))
            {
                if(str1.startsWith("www"))
                {
                    str1 = "<a href=\"http://"+str1.replaceAll("&shy;","")+"\" target=\"_blank\">"+str1+"</a>";//P_B_HelpDesk_fetch-20111216-023
                }
                else
                {
                    str1 = "<a href=\""+str1.replaceAll("&shy;","")+"\" target=\"_blank\">"+str1+"</a>";//P_B_HelpDesk_fetch-20111216-023
                }
            }
            if(i==0)
            {
                str = str1;
            }
            else
            {
                str = str +" "+str1;
            }
            i++;
        }      
        str.replaceAll(" <br> ", "<br>");
        return str;
    }

    public static boolean isUserIsOwner(String userNo,String franchiseeNo) throws ConnectionException    
    {
        boolean isOwner = false;
        String userType = "";
        Connection con = null;
    PreparedStatement pstmt= null ;
    java.sql.ResultSet rs = null ;
        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
            
            pstmt = con.prepareStatement("SELECT FU.USER_TYPE AS USERTYPE FROM OWNERS O LEFT JOIN FRANCHISEE_USERS FU ON FU.USER_TYPE_NO = O.OWNER_ID LEFT JOIN USERS U ON FU.FRANCHISEE_USER_NO = U.USER_IDENTITY_NO WHERE O.FRANCHISEE_NO = ? AND U.USER_NO = ?");
            pstmt.setString(1,franchiseeNo);
            pstmt.setString(2,userNo);
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                userType = rs.getString("USERTYPE");
            }
        } catch (SQLException e){
            Debug.print(e);
        }
        finally {
			try{
				if(rs != null) rs.close();
			}catch(Exception e){}
			try{
				if(pstmt != null) pstmt.close();
			}catch(Exception e){}
			try{
				if(con != null)
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception e){
				logger.error("Exception ", e);
			}
		}
        if(userType.equalsIgnoreCase("Owner")){
                isOwner = true;
        }else if(userType.equalsIgnoreCase("Employee")){
                isOwner = false;
        }
        return isOwner;
    }
	        /* Added by Kapi Tyagi on Feb 12, 2008 to get all Teams as per franchisee no */
            
            public static Info getTeamList(String fran_no){
           Info info = new Info();
//           StringBuffer query = new StringBuffer("SELECT DISTINCT(T.TEAM_ID), T.TEAM_NAME  FROM TEAM_USER  T WHERE T.FRANCHISEE_NO = "+fran_no);
           StringBuffer query = new StringBuffer("SELECT DISTINCT(T.TEAM_ID), T.TEAM_NAME, TM.USER_NO FROM TEAM_USER  T LEFT JOIN TEAM_USER_MAPPING TM ON T.TEAM_ID=TM.MAP_KEY WHERE T.FRANCHISEE_NO = "+fran_no);
           
           com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
           while(result.next())
           {
           	String userNoStr = result.getString("USER_NO");
           	if(userNoStr!=null && userNoStr.trim().length()>0)
           	{
           		StringBuffer queryUsers = new StringBuffer("SELECT SUM(CASE IS_SCHEDULER_USER WHEN 'Y' THEN 1 ELSE 0 END) AS CNT FROM USERS WHERE USER_NO IN (");
           		queryUsers.append(userNoStr);
           		queryUsers.append(")");
           		com.home.builderforms.sqlqueries.ResultSet result1 = QueryUtil.getResult(queryUsers.toString(), new Object[]{});
           		while(result1.next() && result1.getString("CNT")!=null)
           		{
           			String cntStr = result1.getString("CNT");
           			int countUsers = Integer.parseInt(cntStr.substring(0,cntStr.indexOf(".")));
           			if(countUsers>0)
           			{
           				info.set(result.getString("TEAM_ID"), result.getString("TEAM_NAME"));
           			}
           		}
           	}
//               info.set(result.getString("TEAM_ID"), result.getString("TEAM_NAME"));
           }
           return info;
       }
            
            public static Info getAllTeamUsers(String userLevel,String franchiseeNo,String privlidgeId,String userNo,boolean fromSch,boolean contactFranchiseePriveleges){
                return getAllTeamUsers( userLevel, franchiseeNo, privlidgeId, userNo, fromSch, contactFranchiseePriveleges,null);
            }
            public static Info getAllTeamUsers(String userLevel,String franchiseeNo,String privlidgeId,String userNo,boolean fromSch,boolean contactFranchiseePriveleges,String fromWhere){
                
                com.home.builderforms.sqlqueries.ResultSet result;
                String params[]={};
                SequenceMap userMap=new SequenceMap();
                Info userInfo=new Info();
                ArrayList idList=new ArrayList();
                StringBuffer sQuery=new StringBuffer();
        		boolean unionf=false;
        		Info muFranUserInfo=null;
        		String key = "",value="";
        		Iterator it=null;
                        
                        String isSchedulerQuery = " IS_SCHEDULER_USER ='Y' ";
                    if(StringUtil.isValidNew(fromWhere) && fromWhere.equalsIgnoreCase("addTaskPage")){
                        isSchedulerQuery = " 1=1 ";
                    }
                        
                try{
                	if(privlidgeId!=null && privlidgeId.equals("103")){
                     String  sQuery1	=	"SELECT DISTINCT B.CONTACT_USER_NO USER_ID FROM MESSAGE_GROUP_DETAIL A,MESSAGE_GROUP_DETAIL B LEFT JOIN MESSAGE_GROUPS MG ON B.GROUP_NO=MG.GROUP_NO WHERE A.GROUP_NO=B.GROUP_NO AND MG.FLAG='I' AND A.CONTACT_USER_NO='"+userNo+"'";
                        
                        
                        result		= QueryUtil.getResult(sQuery1,params);
                        
                        
                        
                        while (result.next()) {
                            idList.add(result.getString("USER_ID"));
                        }
                        int size=idList.size();
                        for(int i=0;i<size;i++){
                            String userId=(String)idList.get(i);
                            userInfo.set(userId,PortalUtils.getUserName(userId));
                        }
                        
                        
//        	 }else if(userLevel.equals("0")){
                    }else {
//                        System.out.println("i m in userlevel 0");
                        if(userLevel!=null && userLevel.equals("0")){
                            //sQuery.append("SELECT USER_NO,FIRST_NAME,LAST_NAME  FROM USERS,FRANCHISEE WHERE USERS.FRANCHISEE_NO=FRANCHISEE.FRANCHISEE_NO AND "+isSchedulerQuery+" AND USERS.USER_LEVEL IN( ");
                            sQuery.append("SELECT USER_NO,FIRST_NAME,LAST_NAME  FROM USERS,FRANCHISEE WHERE USERS.FRANCHISEE_NO=FRANCHISEE.FRANCHISEE_NO AND "+isSchedulerQuery+" AND USERS.IS_DELETED='N' AND USERS.USER_LEVEL IN( ");
                            unionf=true;
                            if(fromSch){
                            	if(franchiseeNo.trim().equals("0")){
                            		sQuery.append(" '0' )");
                            		unionf=false;
                            	}
                            	else if(franchiseeNo.trim().equals("2")){
                            		if(userLevel.equals("0"))
                            			sQuery.append(" '2' )");
                            		if(userLevel.equals("2"))
                            			sQuery.append(" '2' ) AND USER_NO = '"+userNo+"'");
                            		unionf=false;
                            	}
                            	else if(franchiseeNo!=null){
                            		sQuery.append(" 'forFranUser' )");
                            		unionf=true;
                            	}
                            }
                            else{
                            	sQuery.append(" '0','2' )");
                            }
                           	sQuery.append("  AND USERS.STATUS='1' ");
        					//ORDER BY LAST_NAME , FIRST_NAME ASC
        //ONLY FOR UPS BUILD

        					
                        }
                        if(userLevel!=null && userLevel.equals("2")){
                        	if(userNo!=null && userNo.length()>0){
                        		sQuery.append(" SELECT USERS.USER_NO, FIRST_NAME, LAST_NAME FROM FRANCHISEE, USERS LEFT JOIN AREA_USERS AU ON AU.USER_NO = USERS.USER_NO LEFT JOIN AREAS ON AU.AREA_ID = AREAS.AREA_ID WHERE (USERS.USER_LEVEL=2 OR USERS.USER_LEVEL=3) AND FRANCHISEE.FRANCHISEE_NO=USERS.FRANCHISEE_NO  AND USERS.IS_DELETED='N'  AND  AREAS.AREA_ID IN (SELECT AREA_ID FROM AREA_USERS WHERE USER_NO="+userNo+")  AND USERS.STATUS=1 AND FRANCHISEE.STATUS<>0 AND  "+isSchedulerQuery);
                        	}else{
                        		unionf=true;
                        	}
                        }
                        else if(userLevel.equals("1")){
                        	if(contactFranchiseePriveleges){
        	                	//sQuery.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_LEVEL=1 AND "+isSchedulerQuery);
        	                	sQuery.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_LEVEL=1 AND A.IS_DELETED='N' AND "+isSchedulerQuery);
        	                    sQuery.append(" AND FU.FRANCHISEE_NO	=	"+franchiseeNo);
                        	}else{
                        		//sQuery.append(" SELECT FIRST_NAME, LAST_NAME FROM FRANCHISEE_USERS WHERE FRANCHISEE_USER_NO="+userNo);
                        		//sQuery.append(" SELECT U.USER_NO,FU.FIRST_NAME,FU.LAST_NAME FROM USERS U LEFT JOIN FRANCHISEE_USERS FU ON FU.FRANCHISEE_USER_NO = U.USER_IDENTITY_NO WHERE U.USER_NO="+userNo+" AND "+isSchedulerQuery);
                        		sQuery.append(" SELECT U.USER_NO,FU.FIRST_NAME,FU.LAST_NAME FROM USERS U LEFT JOIN FRANCHISEE_USERS FU ON FU.FRANCHISEE_USER_NO = U.USER_IDENTITY_NO WHERE U.USER_NO="+userNo+" AND U.IS_DELETED='N' AND "+isSchedulerQuery);
                        	}
                        	unionf=false;
                        }
                        if(!userLevel.equals("2"))
                        {
                        	if(unionf)sQuery.append("UNION");
                        }
        //getting franchisee users
        //COMMENTED FOR UPS
                        if(unionf)
                        {
        	                //sQuery.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND  "+isSchedulerQuery);
        	                sQuery.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.IS_DELETED='N' AND  "+isSchedulerQuery);
        	                if(userLevel.equals("1") || (franchiseeNo!=null && !franchiseeNo.trim().equals("0") && !franchiseeNo.trim().equals("1") && !franchiseeNo.trim().equals("2") && !franchiseeNo.trim().equals("")))
        	               // if(userLevel.equals("2") && franchiseeNo!=null && !franchiseeNo.trim().equals("0") && !franchiseeNo.trim().equals("1")  && !franchiseeNo.trim().equals(""))
                                {
        	                	//sQuery.append(" AND A.USER_LEVEL = '");
        		                //sQuery.append(userLevel);
        		                //sQuery.append("'");
        	                	sQuery.append(" AND FU.FRANCHISEE_NO IN("+franchiseeNo+")");
        	                }
        	                else
        	                {
        	                	sQuery.append(" AND A.USER_LEVEL = '");
        		                sQuery.append(userLevel);
        		                sQuery.append("'");
        	                }
                        }
                        /** DEMO_IN_E_CAL_0601071002, Siddharth Singh
                         * adding USER_NO as order by parameter.
                         */
                        if(userLevel!=null && userLevel.equals("1"))
                        {
                        	if(contactFranchiseePriveleges)
                        	{
                        		sQuery.append(" ORDER BY FIRST_NAME , LAST_NAME , USER_NO ");
                        	}
                        	else
                        	{
                        		sQuery.append(" ORDER BY FIRST_NAME , LAST_NAME , FRANCHISEE_USER_NO ");
                        	}
                        }
                        else
                        {
                        	sQuery.append(" ORDER BY FIRST_NAME , LAST_NAME , USER_NO ");
                        }
                        //sQuery.append(" ORDER BY LAST_NAME , FIRST_NAME , USER_NO ");
//                        System.out.println("sQuery ------ >>> "+sQuery);
                        result		= QueryUtil.getResult(sQuery.toString(),params);
                        
                        logger.info(">>>  sQuery inside DAO is >>>>>> 121221 "+sQuery);
                        //if(franchiseeNo.trim().equals("0"))
                        //userMap.put(userNo,PortalUtils.getUserName(userNo));
                        logger.info(">>>userMap for first is>>>>  "+userMap);
                        logger.info(">>>userNo for first is>>>>  "+userNo);
                        while (result.next()) {
        		    if((!userNo.equals("1"))&&(result.getString("USER_NO").equals("1")) ){
                                         continue;
                                        }
                            String fName=result.getString("FIRST_NAME");
                            String lName=result.getString("LAST_NAME");
                            userInfo.set(result.getString("USER_NO"),StringUtil.formatName(fName,lName));
                        }
                        
                       // logger.info(">>>>>>>>>userMap 1212122 inside SDAO is >>>>>  "+userMap); 
                    }
                }catch(Exception e){
                    logger.error("exception while getting users",e);
                }
                idList=null;
                if(userInfo.size()==0){
                    try{
                        userInfo.set(userNo,PortalUtils.getUserName(userNo));
                       // logger.info(">>>>>>>>>userMap inside SDAO is >>>>>  "+userMap);
                    }catch(Exception e){
                        logger.error("Error in getting the name",e);
                    }
                }
                //starts for P_B_SCH_30588
    			if(StringUtil.isValidNew(franchiseeNo)){
    				muFranUserInfo=AdminMgr.newInstance().getUsersDAO().getUserInfoForMUID(franchiseeNo);	
    			}
    			if(muFranUserInfo!=null && muFranUserInfo.size()>0){
    				it = muFranUserInfo.getKeySetIterator();
    				while(it.hasNext())
    				{
    					key = (String)it.next();
    					value = (String)muFranUserInfo.get(key);
    					userInfo.set(key , value);	
    				}
    				
    			}
    			 //ends here
                return userInfo;
            }
            public static int checkRegionalUsersExists()
            {
            	return checkRegionalUsersExists(null);
            }
            public static int checkRegionalUsersExists(String regionId)
	        {
	        	int count = 0;
	        	
	        	StringBuffer query = new StringBuffer("SELECT COUNT(USERS.USER_NO) AS COUNT FROM FRANCHISEE, USERS LEFT JOIN AREA_USERS AU ON AU.USER_NO = USERS.USER_NO LEFT JOIN AREAS ON AU.AREA_ID = AREAS.AREA_ID WHERE (USERS.USER_LEVEL=2 OR USERS.USER_LEVEL=3) AND FRANCHISEE.FRANCHISEE_NO=USERS.FRANCHISEE_NO  AND USERS.IS_DELETED='N' AND USERS.STATUS=1 AND FRANCHISEE.STATUS<>0");
	        	if(StringUtil.isValid(regionId))
	        	{
	        	  query.append(" AND FRANCHISEE.AREA_ID= '").append(regionId).append("'");
	        	}
	        	com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
	        	while(result.next())
	        	{
	        		count = Integer.parseInt(result.getString("COUNT")); 
	        	}
	        	return count;
	        }
            
            
            public static Info getTeamUserList(String userCat,String mapKey,String userLevel,String userNo,boolean contactFranchiseePriveleges){
                return getTeamUserList( userCat, mapKey, userLevel, userNo, contactFranchiseePriveleges,null);
            }
            public static Info getTeamUserList(String userCat,String mapKey,String userLevel,String userNo,boolean contactFranchiseePriveleges,String fromWhere){
                    
                    String isSchedulerQuery = " IS_SCHEDULER_USER ='Y' ";
                    if(StringUtil.isValidNew(fromWhere) && fromWhere.equalsIgnoreCase("addTaskPage")){
                        isSchedulerQuery = " 1=1 ";
                    }
                
	            Info info = new Info();
                    ArrayList userList=new ArrayList();
	            StringBuffer query = new StringBuffer("");
                    StringBuffer query2 = new StringBuffer("SELECT USER_NO FROM USERS WHERE "+isSchedulerQuery);
                  if(userLevel.equals("1")){ 
                    if(contactFranchiseePriveleges)
                    {
                   if(mapKey!=null && !mapKey.equals("") && !mapKey.equals("null") && !mapKey.equals("-1") &&  !mapKey.equals("--")) 
                   {
                    query.append("SELECT DISTINCT(T.USER_MAPPING_ID), T.USER_NO  FROM TEAM_USER_MAPPING  T WHERE T.MAP_KEY = "+mapKey);
                   }
                   else
                   { 
                    //query.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_LEVEL=1   AND  "+isSchedulerQuery);
                       query.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_LEVEL=1 AND A.IS_DELETED='N'  AND  "+isSchedulerQuery);
                    query.append(" AND FU.FRANCHISEE_NO	=	"+userCat);
                    query.append(" ORDER BY FU.FIRST_NAME, FU.LAST_NAME ");
                   }
                    }
                    else
                    {
                       //query.append(" SELECT U.USER_NO,FU.FIRST_NAME,FU.LAST_NAME FROM USERS U LEFT JOIN FRANCHISEE_USERS FU ON FU.FRANCHISEE_USER_NO = U.USER_IDENTITY_NO WHERE U.USER_NO="+userNo+" AND  "+isSchedulerQuery);
                       query.append(" SELECT U.USER_NO,FU.FIRST_NAME,FU.LAST_NAME FROM USERS U LEFT JOIN FRANCHISEE_USERS FU ON FU.FRANCHISEE_USER_NO = U.USER_IDENTITY_NO WHERE U.IS_DELETED='N' AND U.USER_NO="+userNo+" AND  "+isSchedulerQuery);
                       query.append(" ORDER BY FU.FIRST_NAME, FU.LAST_NAME ");
                    }
                  }
                  else  if(userLevel.equals("0") || userLevel.equals("2")){ 
                     if(mapKey!=null && !mapKey.equals("") && !mapKey.equals("null") && !mapKey.equals("-1") &&  !mapKey.equals("--")) 
                     {
                      
                      query.append("SELECT DISTINCT(T.USER_MAPPING_ID), T.USER_NO  FROM TEAM_USER_MAPPING  T WHERE T.MAP_KEY = "+mapKey);
                     }
                    else
                   {
                    //query.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_LEVEL=1   AND  "+isSchedulerQuery);
                    query.append(" SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.IS_DELETED='N' AND A.USER_LEVEL=1   AND  "+isSchedulerQuery);
                    query.append(" AND FU.FRANCHISEE_NO	=	"+userCat);
                    query.append(" ORDER BY FU.FIRST_NAME, FU.LAST_NAME ");
                    logger.info("Inside hit");
                   }
                  }
                    
                    
                    
	           System.out.println("query----->>"+query) ;
                   System.out.println("query2----->>"+query2) ;
	            com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
                     com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(query2.toString(), new Object[]{});
                       
  		try{
			 
			 while (rs.next())
			 {
                           userList.add(rs.getString("USER_NO"));
			 }
		}
		catch(Exception e){
			logger.info("Exception in getContactId function "+e);
		}
                     
                   logger.info("userList>>>>>>>"+userList);  
                    String name="";
	            while(result.next())
                    { 
                          name="";
                          StringTokenizer st=new StringTokenizer(result.getString("USER_NO"),",");
                               while(st.hasMoreTokens()) 
			{       name="";
                                
				String uNo = st.nextToken();
                              
                                
				try{
                                  name=getUserNameNew1(uNo);
                                   if( userList.contains(uNo))
                                       info.set(uNo, name);
                              }
                                catch (Exception e){
			  e.printStackTrace();
		   }
                               }
                        
                       
                         
                       
                 }
	            //P_B_DISP_33779 starts
	        	String key="",value="";
				Info muFranUserInfo=null;
				if(StringUtil.isValidNew(userCat)){
					muFranUserInfo=AdminMgr.newInstance().getUsersDAO().getUserInfoForMUID(userCat);	
				}
				Iterator it=null;             
            
	            if(muFranUserInfo!=null && muFranUserInfo.size()>0){
       				it = muFranUserInfo.getKeySetIterator();
       				while(it.hasNext())
       				{
       					key = (String)it.next();
       					value = (String)muFranUserInfo.get(key);
       					info.set(key , value);	
       				}
       				
       			}
	            //P_B_DISP_33779 ends
	            return info;
	        }
            
            /**
        	 * This method will return other method getUserNameFirstLast with integer parameter
        	 * @param sUserName
        	 * @return
        	 * @throws ConnectionException
        	 */
        	// CHM_E_081607_1001
        	/*public static String getUserNameFirstLast(String sUserName)throws ConnectionException{
        		return getUserNameFirstLast(Integer.parseInt(sUserName));
        	}*/
        	
        	/**This method will returns the First name and Last name of Users
        	 *@param userno -Integer User No. as  an input parameter.
        	 * @return - String containing Last name and First name of Users.
        	 */
        	// CHM_E_081607_1001
        	//P_CM_B_7190 starts
        	/*public static String getUserNameFirstLast(int userno) throws ConnectionException
        	{
        		return getUserNameFirstLast(userno,", ");
        	}*/
        	//P_CM_B_7190 ends
        	
        	/*public static String getUserNameFirstLast(int userno,String separator) throws ConnectionException//P_CM_B_7190 : added param separator
        	{
		String sName				= (String)UsersCache.getInstance().get(new Integer(userno));
        		if(sName != null){
        			return sName;
        		}

		//P_CM_B_7190 starts
		if(!StringUtil.isValid(separator))
		{
			separator = " ";
		}

		//P_CM_B_7190 ends
		//DB_CACHE_OPTIMIZATION_STARTS
		String sName = "";
		HashMap sMap=(HashMap)UserDataCache.getInstance().get(Integer.toString(userno));

		if(sMap!=null)
		{
			if(sMap.get("FIRST_NAME")!=null && sMap.get("LAST_NAME")!=null)
				sName = (String)sMap.get("FIRST_NAME")+separator+(String)sMap.get("LAST_NAME");
			
			sMap=null;
			////testing
			return sName;

		}
		//DB_CACHE_OPTIMIZATION_ENDS


		Connection con				= null;
		PreparedStatement pstmt		= null;
		java.sql.ResultSet rs				= null;
		try 
		{//DB_CACHE_OPTIMIZATION_STARTS
	
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT USER_FIRST_NAME, USER_LAST_NAME FROM USERS WHERE USER_NO=?");
			pstmt.setInt(1, userno);

			rs = pstmt.executeQuery();


			if (rs.next()) {
				sName = rs.getString("USER_FIRST_NAME") +separator+ rs.getString("USER_LAST_NAME");
				HashMap userMap=(HashMap)UserDataCache.getInstance().get(Integer.toString(userno));
				if(userMap==null)
					userMap=new HashMap();
				userMap.put("NAME",sName);
				userMap.put("FIRST_NAME",rs.getString("USER_FIRST_NAME"));
				userMap.put("LAST_NAME",rs.getString("USER_LAST_NAME"));
				UserDataCache.getInstance().put(Integer.toString(userno),userMap);
				userMap=null;

			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
			}


			//DB_CACHE_OPTIMIZATION_ENDS
		}
		catch (ConnectionException e) 
		{
			throw e;
		}
		catch (SQLException e)
		{
			Debug.print(e);
		}
		finally {
			try{
				if(rs != null) rs.close();
			}catch(Exception e){}
			try{
				if(pstmt != null) pstmt.close();
			}catch(Exception e){}
			DBConnectionManager.getInstance().freeConnection(con);
		}
		return sName;
	}//End
*/    
        	 /**
        	     * This method converts the given date to the format (year-mon-date) accepted in database
        	     * @param date -Integer date as an input parameter
        	     * @param month -Integer month as an input parameter
        	     * @param year -Integer year as an input parameter
        	     * @return date -(year-mon-date)
        		 */
        	 	public static String getDBDate(String inputDate){
        	 		try
        	 		{
        	 			if(inputDate == null || inputDate.equals("0000-00-00") || inputDate.equals("")){

        	 				return "";
        	 			}
        	 			StringTokenizer strTokens = new StringTokenizer(inputDate,"/");
        	 			String year = "";
        	 			String month = "";
        	 			String day = "";
        	 			while (strTokens.hasMoreTokens()){
        	 				month = strTokens.nextToken();
        	 				day = strTokens.nextToken();
        	 				year = strTokens.nextToken();
        	 			}
        	 			
        	 			return (year + "-" + month + "-" + day);
        	 		}
        	 		catch (Exception e)
        	 		{
        	 			Debug.print(e);
        	 			return "";
        	 		}
        	 	}
        	 	
//              Added By Mukesh Singla for getting AREA_ID FROM Franchisee_No.
	            /**
	             * This method will give the information of Area of a particular Franchisee.
	             * @param franchiseID -String Franchisee Id of Franchisee as an Input Parameter.
	             * @return areaId -String that contain areaId of particular Franchisee.
	             */
	          public static String getAreaidFromFranchisee(String franchiseID){
	            
	            Connection con = null;
	            String areaId="";
	            String query = "SELECT F.AREA_ID FROM  FRANCHISEE F WHERE F.FRANCHISEE_NO IN("+franchiseID+")";
	            Statement stmt = null ;
	            java.sql.ResultSet result = null ;
	            logger.info(">>> query is >>> "+query);
	    		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
	    		

	    		try{
	    			stmt = con.createStatement();
	    			result = stmt.executeQuery(query.toString());
	                            while (result.next())
	                            {
	                            	areaId+=result.getString("AREA_ID")+",";
	                               
	                            }
	                    }catch(SQLException sqle){
	    			
	    		}
	                    finally{
	            			try{
	            				if(result != null){
	            					result.close();
	            					result = null;
	            				}
	            			}
	            			catch(Exception e){
	            				logger.error("Exception ", e);
	            			}

	            			try{
	            				if(stmt != null){
	            					stmt.close();
	            					stmt = null;
	            				}
	            			}
	            			catch(Exception e){
	            				logger.error("Exception ", e);
	            			}

	            						
	            			try{
	            				if(con != null)
	            				DBConnectionManager.getInstance().freeConnection(con);
	            			}
	            			catch(Exception e){
	            				logger.error("Exception ", e);
	            			}
	            		}
	    		if(StringUtil.isValid(areaId) && areaId.endsWith(","))
	    		{
	    			areaId = areaId.substring(0, areaId.length()-1);
	    		}
	                
	            return areaId;
	        }
	          
	//Added By Mukesh Singla for gettingt Area Name By Franchisee No. // Moved to ReportDao
	
	
	/** SC_SSOCHECK_13102008.
     * @author mukesh
     * @purpose : Check whether SSO is on or off for our system.
     * @return : boolean
     */
    public static boolean getSSOLogin()
	{
        boolean isSSOLogin = false;
        String query = "SELECT SYSTEM_DATA FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE = 90010";
	    com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query,null);
	    //int i=0;
        if(result.next())
        {
        	if(result.getString("SYSTEM_DATA")!=null && "1".equals(result.getString("SYSTEM_DATA").trim())) 
        	{
        		isSSOLogin = true;
        	}
	    }
        return isSSOLogin;
	}
    public static String getToolTipDisplay()
	{
        String toolTipDisplay = "";
        String query = "";
         query = "SELECT WS_TOOL_TIP_VALUE FROM WS_MASTER_DATA ";
	    try{
        com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query,null);
	    //int i=0;
        if(result.next())
        {
        		toolTipDisplay = result.getString("WS_TOOL_TIP_VALUE");
	    }
	    }catch(Exception e){
	    	logger.error("Exception ", e);
	    }
        return toolTipDisplay;
	}
    
        
    public static boolean isFranchiseeUser(HttpSession session){
    	boolean isFranchiseeUser = false;
    	String userLevel = null;
    	userLevel = (String) session.getAttribute("user_level");
    	if(StringUtil.isValidNew(userLevel) && userLevel.trim().equalsIgnoreCase("1")){
    		isFranchiseeUser = true;
    	}
    	return isFranchiseeUser;
    }
    
    public static String getGuestName (String guestId) throws ConnectionException {
		StringBuffer queryString = new StringBuffer("SELECT FIRST_NAME, LAST_NAME FROM CM_GUESTS WHERE 1 = 1");
		String guestName = "";
		if(guestId!=null && !"-1".equals(guestId) && !"0".equals(guestId) && !"".equals(guestId) && !"null".equals(guestId)){
			queryString.append(" AND GUEST_ID = "+guestId);
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			
			java.sql.ResultSet rs = null;
			
			try {
				con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			
				pstmt = con.prepareStatement(queryString.toString());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					guestName = rs.getString("LAST_NAME");
					guestName = ", "+rs.getString("LAST_NAME");
					
				}		
			} catch (SQLException e) {
				Debug.print(e);
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (Exception e) {	}
				
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {}
				
				try {
					if (con != null)
						DBConnectionManager.getInstance().freeConnection(con);
				} catch (Exception e) {
					logger.error("Exception ", e);
				}
		}
	}
		return guestName;
    }
    
    public static String getContactFranchiseeNo(String contactID){
    	return DispatchBoardUtil.getContactFranchiseeNo(contactID);
    }
    public static String getContactAddressID(String contactID){
    	return LocationMgr.newInstance().getLocationsDAO().getContactAddressID(contactID);
    }
    public static String getFranchiseeOwnerUserID(String franchiseeNo){
		return AdminMgr.newInstance().getUsersDAO().getFranchiseeOwnerUserID(franchiseeNo);
	}
          
    public static boolean isCorporateUser(HttpSession session){
    	boolean isCorporateUser = false;
    	String userLevel = null;
    	userLevel = (String) session.getAttribute("user_level");
    	if(StringUtil.isValidNew(userLevel) && userLevel.trim().equalsIgnoreCase("0")){
    		isCorporateUser = true;
    	}
    	return isCorporateUser;
    }
       
    public static boolean isRegionalUser(HttpSession session){
    	boolean isCorporateUser = false;
    	String userLevel = null;
    	userLevel = (String) session.getAttribute("user_level");
    	if(StringUtil.isValidNew(userLevel) && userLevel.trim().equalsIgnoreCase("2")){
    		isCorporateUser = true;
    	}
    	return isCorporateUser;
    }
    
    public static SequenceMap subMenuMappingMap(String userNo) {

		Connection con = null;

		SequenceMap map = new SequenceMap();
		int count = 0;

		String query = "SELECT ABBREVIATED_MODULE_NAME,ABBREVIATED_SUBMODULE_NAME,MU.SUB_MENU_PRIVILEGE,MENU_PRIVILEGE,MU.SUB_MODULE_URL FROM MODULE_USER_PAGE_MAPPING MU, MODULE_LIST ML, SUB_MODULE_LIST SML WHERE MU.MODULE_NAME=ML.MODULE_KEY AND MU.SUB_MODULE_NAME=SML.SUB_MODULE_ID  AND MU.USER_NO="
				+ userNo;
		Statement stmt = null;
		java.sql.ResultSet result = null;

		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

		try {
			stmt = con.createStatement();
			result = stmt.executeQuery(query.toString());
			while (result.next()) {
				count++;
				Info mapInfo = new Info();
				mapInfo.set("ABBR_MODULE_NAME", result
						.getString("ABBREVIATED_MODULE_NAME"));
				mapInfo.set("ABBR_SUBMODULE_NAME", result
						.getString("ABBREVIATED_SUBMODULE_NAME"));
				mapInfo.set("SUB_MENU_PRIVILEGE", result
						.getString("SUB_MENU_PRIVILEGE"));
				mapInfo.set("MENU_PRIVILEGE", result
						.getString("MENU_PRIVILEGE"));
				mapInfo.set("SUB_MODULE_URL", result
						.getString("SUB_MODULE_URL"));
				map.put(count + "", mapInfo);
			}
		} catch (SQLException sqle) {

		} finally {
			try {
				if (result != null) {
					result.close();
					result = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}

			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}

			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

		return map;
	}
    
    	/**
    	 * @author Nikhil Verma
    	 * @param mailid
    	 * @return boolean 
    	 */
    	public static boolean isValidEmail(String mailId)
    	{
    		boolean valid=false;
    		if(mailId!=null && !"".equals(mailId) && org.apache.commons.validator.GenericValidator.isEmail(mailId) && mailId.indexOf('\'')==-1)
    			valid=true;
    		return valid;
    	}
    	public static String getUserFirstOrLastName(String userno,String cloumName)
    	throws ConnectionException {
    		return getUserFirstOrLastName(Integer.parseInt(userno),cloumName);
    	}

    	
    	public static String getUserFirstOrLastName(int userno ,String cloumName) /*throws ConnectionException*/ {
    		//37255
    		return getUserName(userno);
    		/*String sName ="";// (String) UsersCache.getInstance().get(new Integer(userno));
    		//if (sName != null) {
    		//	return sName;
    		//}
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		java.sql.ResultSet rs = null;
    		//DB_CACHE_OPTIMIZATION_STARTS
    		HashMap sMap=(HashMap)UserDataCache.getInstance().get(Integer.toString(userno));

    		if(sMap!=null)
    		{
    			if("firstName".equals(cloumName) && sMap.get("FIRST_NAME")!=null)
    				sName = (String)sMap.get("FIRST_NAME");
    			else if("lastName".equals(cloumName) && sMap.get("LAST_NAME")!=null)
    				sName =  (String)sMap.get("LAST_NAME");
    			else if ("firstLastName".equals(cloumName) && (sMap.get("FIRST_NAME")!=null && sMap.get("LAST_NAME")!=null))//steam-20130826-145 
    				sName =  (String)sMap.get("FIRST_NAME") +" "+ (String)sMap.get("LAST_NAME");
    			else
    				sName="";  
    			return sName;
    		}
    		//DB_CACHE_OPTIMIZATION_ENDS

    		try {//DB_CACHE_OPTIMIZATION_STARTS
    
    			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
    			pstmt = con
    					.prepareStatement("SELECT USER_FIRST_NAME, USER_LAST_NAME FROM USERS WHERE USER_NO=?");
    			pstmt.setInt(1, userno);

    			rs = pstmt.executeQuery();


    			if (rs.next()) {
    				if("firstName".equals(cloumName))
    					sName = rs.getString("USER_FIRST_NAME");
    				else if("lastName".equals(cloumName))
    					sName =  rs.getString("USER_LAST_NAME");
    				else if ("firstLastName".equals(cloumName))//steam-20130826-145 
    					sName =  rs.getString("USER_FIRST_NAME") +" "+ rs.getString("USER_LAST_NAME");
    				else
    					sName="";

    				HashMap userMap=(HashMap)UserDataCache.getInstance().get(Integer.toString(userno));
    				if(userMap==null)
    					userMap=new HashMap();
    				userMap.put("NAME",rs.getString("USER_FIRST_NAME") +Constants.LBL_SPACER+ rs.getString("USER_LAST_NAME"));
    				userMap.put("FIRST_NAME",rs.getString("USER_FIRST_NAME"));
    				userMap.put("LAST_NAME",rs.getString("USER_LAST_NAME"));
    				UserDataCache.getInstance().put(Integer.toString(userno),userMap);
    				userMap=null;

    			}
    			try {
    				if (rs != null)
    					rs.close();
    			} catch (Exception e) {
    			}
    			try {
    				if (pstmt != null) {
    					pstmt.close();
    					pstmt = null;
    				}
    			} catch (Exception e) {
    			}


    			//DB_CACHE_OPTIMIZATION_ENDS
    		} catch (ConnectionException e) {
    			throw e;
    		} catch (SQLException e) {
    			Debug.print(e);
    		} finally {
    			try {
    				if (rs != null)
    					rs.close();
    			} catch (Exception e) {
    			}
    			try {
    				if (pstmt != null)
    					pstmt.close();
    			} catch (Exception e) {
    			}
    			try {
    				if (con != null)
    					DBConnectionManager.getInstance().freeConnection(con);
    			} catch (Exception e) {
    				logger.error("Exception ", e);
    			}
    		}
    		return sName;*/
    	}
    	/**
    	 * 
    	 * @param userno
    	 * @return
    	 * @throws ConnectionException
    	 */
    	public static Info getUserInfo(String userno) throws ConnectionException
    	{
    		Info userInfo = new Info();

//    		Connection con			= null;
//    		PreparedStatement pstmt	= null;
//    		java.sql.ResultSet rs			= null;
//    		String regionNo	= "";
    		//DB_CACHE_OPTIMIZATION_STARTS
//    		HashMap sMap=(HashMap)UserDataCache.getInstance().get(userno);
    		HashMap<String,Object> sMap = NewPortalUtils.getUserDetails(userno); 
    		if(sMap!=null)
    		{
    			if(sMap.get(FieldNames.USER_LEVEL)!=null)
    				userInfo.set("userlevel",(String)sMap.get(FieldNames.USER_LEVEL));
    			if(sMap.get(FieldNames.USER_IDENTITY_NO)!=null)
    				userInfo.set("userIdNo",(String)sMap.get(FieldNames.USER_IDENTITY_NO));
    			if(sMap.get(FieldNames.FRANCHISEE_NO)!=null)
    				userInfo.set("franchiseeNos",(String)sMap.get(FieldNames.FRANCHISEE_NO));
    			
//    			userInfo.set("regionNo",regionNo);
    			userInfo.set("regionNo",(String)sMap.get(FieldNames.REGION_NO));
//    			sMap=null;
//    			if(userInfo.size()>0)
//    			return userInfo;
    		}//DB_CACHE_OPTIMIZATION_ENDS

    		/*try 
    		{
    			con	= DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
    			pstmt = con.prepareStatement("SELECT F.AREA_ID ,USER_LEVEL, USER_IDENTITY_NO, U.FRANCHISEE_NO FROM USERS U LEFT JOIN FRANCHISEE F ON U.FRANCHISEE_NO=F.FRANCHISEE_NO WHERE USER_NO=?");
    			pstmt.setInt(1,Integer.parseInt(userno));

    			rs	= pstmt.executeQuery();
    			String userType			= "";
    			String userIdNos		= "";
    			String franchiseeNos	= "";


    			if(rs.next())
    			{
    				userType			= rs.getString("USER_LEVEL");
    				userIdNos			= rs.getString("USER_IDENTITY_NO");
    				franchiseeNos		= rs.getString("FRANCHISEE_NO");
    			}
    			userInfo.set("userlevel",userType);
    			userInfo.set("userIdNo",userIdNos);
    			userInfo.set("franchiseeNos",franchiseeNos);
    			userInfo.set("regionNo",regionNo);
    			if(sMap==null)
    			{
    				sMap=new HashMap();
    			}
    			sMap.put("USER_IDENTITY_NO",userIdNos);
    			sMap.put("FRANCHISEE_NO",franchiseeNos);
    			sMap.put("USER_LEVEL",userType);
    			sMap.put("REGION_NO",regionNo);
    			UserDataCache.getInstance().put(userno,sMap);
    			sMap=null;
    			try
    			{
    				if(rs != null)
    					rs.close();
    			}
    			catch(Exception e){}
    			try
    			{
    				if(pstmt != null){
    					pstmt.close();
    					pstmt				= null;
    				}
    			}
    			catch(Exception e){}

    		}
    		catch (ConnectionException e)
    		{
    			throw e;
    		}
    		catch (SQLException e)
    		{
    			Debug.print(e);
    		}
    		catch(Exception  e)
    		{
    			e.printStackTrace();
    		}
    		finally 
    		{
    			try
    			{
    				if(rs != null) rs.close();
    			}
    			catch(Exception e){}
    			try
    			{
    				if(pstmt != null) pstmt.close();
    			}
    			catch(Exception e){}
    			try{
    				if(con != null)
    					DBConnectionManager.getInstance().freeConnection(con);
    			}
    			catch(Exception e){
    				logger.error("Exception ", e);
    			}
    		}*/
    		return userInfo;
    	}

    	/**
    	 * This will get all Bill and Shipp address of Franchise ID selected
    	 * @author abhishek gupta
    	 * @date 23 jun 2009
    	 * @param fNo
    	 * @return
    	 */
    	public static SequenceMap getAllFranchiseBillShipp(String fNo) {
    		SequenceMap sMap = new SequenceMap();
    		StringBuffer  query = new StringBuffer("SELECT ADDRESS_ID,TITLE FROM ADDRESS A1  ") ;
    		query.append("WHERE (FOREIGN_TYPE = 'billing' OR FOREIGN_TYPE = 'shipping') ");	
//    		query.append(" AND FOREIGN_ID = " + fNo);	
    	//	query.append(" GROUP BY TITLE ");
    		
    		if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
    			//query.append(" AND F.FRANCHISEE_NO = " + fNo);
    			query.append(" AND FOREIGN_ID = " + fNo);
    		}
    		query.append(" GROUP BY TITLE ");
    		query.append(" ORDER BY TITLE ");
    		
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
    				.getResult(query.toString(), new Object[] {});
    		while (result.next())
    			sMap.put(result.getString("ADDRESS_ID"), result.getString("TITLE"));
                
                /*if(!(fNo.equals("false"))){
                    sMap.put("-3","----- "+LanguageUtil.getString("Other available Addresses")+" -----" );
                }*/
                
                //P_Laboit_CR_20100913-013 starts
                StringBuffer  query0 = new StringBuffer("SELECT FRANCHISEE_NO, ADDRESS, CITY, STATE, COUNTRY, ZIPCODE, PHONE1, LEGAL_NOTICE_ADDRESS, LEGAL_NOTICE_CITY, LEGAL_NOTICE_STATE, LEGAL_NOTICE_COUNTRY, LEGAL_NOTICE_ZIPCODE, LEGAL_NOTICE_PHONE FROM FRANCHISEE ") ;
                query0.append("WHERE 1=1");
                if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
	    			query0.append(" AND FRANCHISEE_NO = " + fNo);
	    		}
                
                 com.home.builderforms.sqlqueries.ResultSet result0 = QueryUtil
    				.getResult(query0.toString(), new Object[] {});
                while (result0.next()){
                    if(StringUtil.isValid(result0.getString("ADDRESS"))){
                    	sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("ADDRESS") );
                    }else if(StringUtil.isValid(result0.getString("CITY"))){
                        //sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("CITY") );
                    }else if(StringUtil.isValid(result0.getString("STATE"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("STATE") );
                    }else if(StringUtil.isValid(result0.getString("COUNTRY"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("COUNTRY") );
                    }else if(StringUtil.isValid(result0.getString("ZIPCODE"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("ZIPCODE") );
                    }else if(StringUtil.isValid(result0.getString("PHONE1"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":CENTER_INFO",result0.getString("PHONE1") );
                    }
                    if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_ADDRESS"))){
                    	sMap.put(result0.getString("FRANCHISEE_NO")+":LEGAL_NOTICE",result0.getString("LEGAL_NOTICE_ADDRESS") );
                    }else if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_CITY"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":LEGAL_NOTICE",result0.getString("LEGAL_NOTICE_CITY") );
                    }else if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_STATE")) && !(result0.getString("LEGAL_NOTICE_STATE").equals("0")) && !(result0.getString("LEGAL_NOTICE_STATE").equals("-1"))){
                        sMap.put(result0.getString("FRANCHISEE_NO") + ":LEGAL_NOTICE",  RegionMgr.newInstance().getRegionsDAO().getStateName(result0.getString("LEGAL_NOTICE_STATE")));
                    }else if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_COUNTRY")) && !(result0.getString("LEGAL_NOTICE_COUNTRY").equals("-1"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":LEGAL_NOTICE",getCountryNameById(result0.getString("LEGAL_NOTICE_COUNTRY")) );
                    }else if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_ZIPCODE"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":LEGAL_NOTICE",result0.getString("LEGAL_NOTICE_ZIPCODE") );
                    }else if(StringUtil.isValid(result0.getString("LEGAL_NOTICE_PHONE"))){
                        sMap.put(result0.getString("FRANCHISEE_NO")+":LEGAL_NOTICE",result0.getString("LEGAL_NOTICE_PHONE") );
                    }
                       
                }
                
                StringBuffer  query1 = new StringBuffer("SELECT FED.FIM_TT_ENTITY_NAME, A.ADDRESS_ID,A.ADDRESS, A.CITY, A.STATE, A.COUNTRY, A.ZIPCODE , A.PHONE_NUMBERS, A.FOREIGN_ID, A.FOREIGN_TYPE FROM ADDRESS A LEFT JOIN FIM_ENTITY_DETAIL FED ON FED.FIM_ENTITY_ID=A.FOREIGN_ID WHERE FOREIGN_TYPE ='fimEntityDetail' ") ;
    		if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
    			query1.append(" AND ENTITY_ID = " + fNo);
    		}
                com.home.builderforms.sqlqueries.ResultSet result1 = QueryUtil
    				.getResult(query1.toString(), new Object[] {});
                while (result1.next()){
                    
                    if(StringUtil.isValid(result1.getString("ADDRESS"))){
                    	//sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL",result1.getString("ADDRESS") );
                    }else if(StringUtil.isValid(result1.getString("CITY"))){
                        sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL",result1.getString("CITY") );
                    }else if(StringUtil.isValid(result1.getString("STATE"))  && !(result1.getString("STATE").equals("0")) && !(result1.getString("STATE").equals("-1"))){
                            sMap.put(result1.getString("ADDRESS_ID") + ":ENTITY_DETAIL", RegionMgr.newInstance().getRegionsDAO().getStateName(result1.getString("STATE")));
                    }else if(StringUtil.isValid(result1.getString("COUNTRY")) && !(result1.getString("COUNTRY").equals("-1"))){
                        sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL",getCountryNameById(result1.getString("COUNTRY")) );
                    }else if(StringUtil.isValid(result1.getString("ZIPCODE"))){
                        sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL",result1.getString("ZIPCODE") );
                    }else if(StringUtil.isValid(result1.getString("PHONE_NUMBERS"))){
                        sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL",result1.getString("PHONE_NUMBERS") );
                    }
                    
    			//sMap.put(result1.getString("ADDRESS_ID")+":ENTITY_DETAIL", LanguageUtil.getString("Entity")+" - "+result1.getString("FIM_TT_ENTITY_NAME"));
                }
                
                StringBuffer  query2 = new StringBuffer("SELECT OWNER_ID, OWNER_FIRST_NAME, OWNER_LAST_NAME, HOME_ADDRESS, HOME_CITY, HOME_STATE, HOME_COUNTRY, HOME_ZIPCODE, PHONE FROM OWNERS O INNER JOIN FIM_OWNERS FO  ON O.OWNER_ID= FO.FRANCHISE_OWNER_ID WHERE 1=1") ;
    		if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
    			query2.append(" AND O.FRANCHISEE_NO = " + fNo);
    		}
                com.home.builderforms.sqlqueries.ResultSet result2 = QueryUtil
    				.getResult(query2.toString(), new Object[] {});
                while (result2.next()){
                    if(StringUtil.isValid(result2.getString("HOME_ADDRESS"))){
                    	sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS",result2.getString("HOME_ADDRESS") );
                    }else if(StringUtil.isValid(result2.getString("HOME_CITY"))){
                        sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS",result2.getString("HOME_CITY") );
                    }else if(StringUtil.isValid(result2.getString("HOME_STATE"))  && !(result2.getString("HOME_STATE").equals("0")) && !(result2.getString("HOME_STATE").equals("-1"))){
                            sMap.put(result2.getString("OWNER_ID") + ":FIM_OWNERS", RegionMgr.newInstance().getRegionsDAO().getStateName(result2.getString("HOME_STATE")));
                    }else if(StringUtil.isValid(result2.getString("HOME_COUNTRY")) && !(result2.getString("HOME_COUNTRY").equals("-1"))){
                        sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS",getCountryNameById(result2.getString("HOME_COUNTRY")) );
                    }else if(StringUtil.isValid(result2.getString("HOME_ZIPCODE"))){
                        sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS",result2.getString("HOME_ZIPCODE") );
                    }else if(StringUtil.isValid(result2.getString("PHONE"))){
                        sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS",result2.getString("PHONE") );
                    }
                    
    			//sMap.put(result2.getString("OWNER_ID")+":FIM_OWNERS", LanguageUtil.getString("Owners")+"  - "+result2.getString("OWNER_FIRST_NAME")+" "+result2.getString("OWNER_LAST_NAME"));
                }
                
                
                
                 StringBuffer  query3 = new StringBuffer("SELECT A.ADDRESS_ID, A.FOREIGN_ID, A.FOREIGN_TYPE,FA.FIM_TT_ADDRESS_HEADING ,A.ADDRESS, A.CITY, A.STATE, A.COUNTRY, A.ZIPCODE , A.PHONE_NUMBERS FROM ADDRESS A LEFT JOIN FIM_ADDRESS FA ON FA.OTHER_ADDRESS_ID=A.FOREIGN_ID WHERE FOREIGN_TYPE ='fimAddress' ") ;
    		if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
    			query3.append(" AND ENTITY_ID = " + fNo);
    		}
                com.home.builderforms.sqlqueries.ResultSet result3 = QueryUtil
    				.getResult(query3.toString(), new Object[] {});
                while (result3.next()){
                    if(StringUtil.isValid(result3.getString("ADDRESS"))){
                    	sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS",result3.getString("ADDRESS") );
                    }else if(StringUtil.isValid(result3.getString("CITY"))){
                        sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS",result3.getString("CITY") );
                    }else if(StringUtil.isValid(result3.getString("STATE"))  && !(result3.getString("STATE").equals("0")) && !(result3.getString("STATE").equals("-1"))){
                            sMap.put(result3.getString("ADDRESS_ID") + ":FIM_ADDRESS", RegionMgr.newInstance().getRegionsDAO().getStateName(result3.getString("STATE")));
                    }else if(StringUtil.isValid(result3.getString("COUNTRY")) && !(result3.getString("COUNTRY").equals("-1"))){
                        sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS",getCountryNameById(result3.getString("COUNTRY")) );
                    }else if(StringUtil.isValid(result3.getString("ZIPCODE"))){
                        sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS",result3.getString("ZIPCODE") );
                    }else if(StringUtil.isValid(result3.getString("PHONE_NUMBERS"))){
                        sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS",result3.getString("PHONE_NUMBERS") );
                    }
                    
    			//sMap.put(result3.getString("ADDRESS_ID")+":FIM_ADDRESS", LanguageUtil.getString("Other")+"  - "+result3.getString("FIM_TT_ADDRESS_HEADING"));
                }
                
                         StringBuffer  query5 = new StringBuffer("SELECT FRANCHISEE_USER_NO , FIRST_NAME, LAST_NAME,ADDRESS, CITY, REGION_NO, COUNTRY_ID, ZIPCODE, PHONE1 FROM FRANCHISEE_USERS WHERE 1=1 ") ;
                            if (fNo != null && !"".equals(fNo.trim()) && !"null".equalsIgnoreCase(fNo.trim())){
                                    query5.append(" AND FRANCHISEE_NO = " + fNo +" AND USER_TYPE !='Owner' ");
                            }
                                com.home.builderforms.sqlqueries.ResultSet result5 = QueryUtil
                                            .getResult(query5.toString(), new Object[] {});
                                while (result5.next()){
                                    if(StringUtil.isValid(result5.getString("ADDRESS"))){
                    	//sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER",result5.getString("ADDRESS") );
                    }else if(StringUtil.isValid(result5.getString("CITY"))){
                        sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER",result5.getString("CITY") );
                    }else if(StringUtil.isValid(result5.getString("REGION_NO"))  && !(result5.getString("REGION_NO").equals("0")) && !(result5.getString("REGION_NO").equals("-1"))){
                            sMap.put(result5.getString("FRANCHISEE_USER_NO") + ":USER", RegionMgr.newInstance().getRegionsDAO().getStateName(result5.getString("REGION_NO")));
                    }else if(StringUtil.isValid(result5.getString("COUNTRY_ID")) && !(result5.getString("COUNTRY_ID").equals("-1"))){
                        sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER",getCountryNameById(result5.getString("COUNTRY_ID")) );
                    }else if(StringUtil.isValid(result5.getString("ZIPCODE"))){
                        sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER",result5.getString("ZIPCODE") );
                    }else if(StringUtil.isValid(result5.getString("PHONE1"))){
                        sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER",result5.getString("PHONE1") );
                    }
                                       // sMap.put(result5.getString("FRANCHISEE_USER_NO")+":USER", LanguageUtil.getString("User")+"  - "+result5.getString("FIRST_NAME")+" "+result5.getString("LAST_NAME"));
                                        
                                }
                                //P_Laboit_CR_20100913-013 ends
                
     		return sMap;
    	}
    	
    	/**
    	 * Getting user level of user
    	 * @author abhishek gupta
    	 * @date 19 jun 2009
    	 * @param UserNo
    	 * @return
    	 * @throws ConnectionException
    	 */
    	public static String getUserLevel(String userNo)
    	{
    		return NewPortalUtils.getUserLevel(userNo);
    	}
    	
		/**
		 * This method retrieve Area Names of those Areas which have been Deleted.
		 * 
		 * @param areaIds
		 *            -String that contain Area Ids.
		 * @return info that contain Area Names
		 */
		public static String getAreaType(String areaId) {
			String areaType="";
			StringBuffer qBuff = new StringBuffer(
					"SELECT AREA_TYPE FROM AREAS A WHERE IS_DELETED ='N' ");
			//BB_CT_REPORT_ARA
			if (areaId != null && !"".equals(areaId) && !"-1".equals(areaId))
				qBuff.append("AND A.AREA_ID ='").append(areaId).append("' ");
			
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
					.getResult(qBuff.toString(), new Object[] {});
			if (result.next()) {
				areaType=result.getString("AREA_TYPE");
			}
			return areaType;
		}
	
		public static Info getGlobalGateWayInformation(String module)
		{
			
			Connection con = null;
	                Info info = new Info(); 
	                //boolean flag=false;
			
			StringBuilder query = new StringBuilder();
	        com.home.builderforms.sqlqueries.ResultSet rs = null;
	        BaseConstants _baseConstants =	MultiTenancyUtil.getTenantConstants();
			try {
				con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			
				
						query.append("SELECT CREDIT_CARD_CONNECT_URL,CREDIT_CARD_URL,PORT,CLIENT_CERTIFICATE_PATH,CERTIFICATE_PASSWORD FROM GLOBALGATEWAY_INFORMATION WHERE MODULE='"+module+"'");
				/*if(StringUtil.isValidNew(serviceName))
				{
					query.append(" AND CREDIT_CARD_CONNECT_URL LIKE '%"+serviceName+"%'");
				}*/
				rs = QueryUtil.getResult(query.toString(),null);
				if (rs.next()) 
	                        {
							  info.set("CREDIT_CARD_CONNECT_URL",rs.getString("CREDIT_CARD_CONNECT_URL"));	
	                          info.set("CREDIT_CARD_URL",rs.getString("CREDIT_CARD_URL"));
	                          info.set("PORT",rs.getString("PORT"));
	                          info.set("CLIENT_CERTIFICATE_PATH",rs.getString("CLIENT_CERTIFICATE_PATH"));
	                          info.set("CERTIFICATE_PASSWORD",rs.getString("CERTIFICATE_PASSWORD"));
	            }
			
				} catch (Exception e) {
					 e.printStackTrace();
				} finally {
					
				try {
					if (con != null)
						DBConnectionManager.getInstance().freeConnection(con);
					} catch (Exception e) {
						logger.error("Exception ", e);
				}
			}
			
			return info;
			
		}
		
		public static boolean checkCreditCardCollectionMethod(String poID)
		throws ConnectionException {

			Connection con = null;
			String collectionMethod = "";
	                boolean flag=false;
			PreparedStatement pstmt = null;
			java.sql.ResultSet rs = null;
			try {
				con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			
				pstmt = con
						.prepareStatement("SELECT COLLECTION_METHOD FROM SUPPLIER_PURCHASEORDER WHERE PURCHASEORDER_ID=?");
				pstmt.setString(1, poID);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					collectionMethod = rs.getString("COLLECTION_METHOD");
	                                if(collectionMethod!=null && "CC".equalsIgnoreCase(collectionMethod))
	                                {
	                                  flag = true;
	                                }          
				}
			
				} catch (SQLException e) {
					Debug.print(e);
				} finally {
					try {
						if (rs != null)
							rs.close();
					} catch (Exception e) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
					} catch (Exception e) {
				}
				try {
					if (con != null)
						DBConnectionManager.getInstance().freeConnection(con);
					} catch (Exception e) {
						logger.error("Exception ", e);
				}
			}
			
			return flag;
		
		} 
		
		public static String getAreaComboPurchaseOrder(String selectedValue, String multiple, String comboSize, String comboName) {
	    		//BB_ENH_OVERLOADED
	    		Connection con = null;
	    		PreparedStatement pstmt = null;
	    		java.sql.ResultSet rs = null;
	    		StringBuffer areaCombo = null;
	    		String addSize = "";
	    		String combo_name = FieldNames.AREA_ID+"Name";//this name is used earlier so its been here by default.
	    		
	    		try {
	    			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
	    			pstmt = con
	    					.prepareStatement("SELECT AREA_ID, AREA_NAME FROM AREAS  WHERE IS_DELETED ='N' ORDER BY AREA_NAME");
	    			// System.out.println("pstmt----------"+pstmt);
	    			rs = pstmt.executeQuery();

	    			if(comboName!=null && !"".equals(comboName.trim()) && !"null".equalsIgnoreCase(comboName.trim())){
	    				combo_name = comboName;
	    			}
	    			areaCombo = new StringBuffer("<select name ="+combo_name+ " id='"+combo_name+"' class = \"multiList\" onchange=\"getFranchiseeandUser(this)\""); // P_MS_B_39963
	    					
	    			if (multiple != null && multiple.equals("Y")) {
	    				if(comboSize!=null && !"".equals(comboSize.trim()) && !"0".equals(comboSize.trim())){
	    					addSize = "size='"+comboSize+"'";
	    				}
	    				areaCombo.append("  multiple "+addSize+" >");				
	    			} else {
	    				areaCombo.append("   >");
	    			}		
	    			
	    			areaCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");

	    			while (rs.next()) {
	    				areaCombo.append("<option value='").append(
	    						rs.getString("AREA_ID"));
	    				areaCombo.append("'");
	    				if (selectedValue != null && selectedValue.equals(rs.getString("AREA_ID")))
	    					areaCombo.append(" selected");

	    				areaCombo.append(">");
	    				areaCombo.append(rs.getString("AREA_NAME")).append("</option>");
	    			}
	    		} catch (Exception e) {
	    		} finally {
	    			try {
	    				if (rs != null)
	    					rs.close();
	    				if (pstmt != null)
	    					pstmt.close();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}

	    			try {
	    				if (con != null)
	    					DBConnectionManager.getInstance().freeConnection(con);
	    			} catch (Exception e) {
	    				logger.error("Exception ", e);
	    			}
	    		}
	    		// System.out.println("areaCombo----------"+areaCombo);
	    		areaCombo.append("</select>");

	    		return areaCombo.toString();

	    	}	          

                public static boolean isLoggedGuestUserAreSame(HttpServletRequest request){
                    HttpSession 		session 	= 		request.getSession();
                    String user_member_no    =  (String)session.getAttribute("user_member_no");
                    return isLoggedGuestUserAreSame(request,user_member_no);
                }

                public static boolean isLoggedGuestUserAreSame(HttpServletRequest request,String currentUser){
                    boolean flag = false;
                    HttpSession 		session 	= 		request.getSession();
                    String uNumber    =  (String)session.getAttribute("user_no");
                    if(PortalUtils.isBadString(currentUser) || uNumber.equals(currentUser))
                    {
                    flag = true;
                    }
                    return flag;
                }

	    public static boolean checkPrivilege(String userNO, String priviledgeID)
	 	    {
	      		 return checkPrivilege(userNO,priviledgeID,null);
	 	    }
	    
	    public static boolean checkPrivilege(String userNO, String priviledgeID,String roleID)
	    {
	    	Connection con = null;
	    	StringBuffer query = new StringBuffer("SELECT RP.RPID FROM ROLE_PRIVILEGES RP LEFT JOIN USER_ROLES UR ON UR.ROLE_ID = RP.ROLE_ID WHERE 1=1 ");
	    	query.append(" AND RP.PRIVILEGE_ID IN (").append(priviledgeID).append(")");
	    	
	    	if(StringUtil.isValid(userNO)){
	    		query.append(" AND UR.USER_NO ='").append(userNO).append("'");
	    	}
	    	
	    	if(StringUtil.isValid(roleID))
	    		query.append(" AND RP.ROLE_ID ='").append(roleID).append("'");
	    	
	    	boolean flag = false;
	    	Statement stmt = null;
	    	java.sql.ResultSet rs = null;
	    	con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
	    	
	    	try 
	    	{
	    		stmt = con.createStatement();
	    		rs = stmt.executeQuery(query.toString());
	    		if(rs.next()) 
	    		{
					flag = true;
	    		}
	    		return flag;
	    	} 
	    	catch (SQLException sqle) 
	    	{
	    		sqle.printStackTrace();
	    	}
	    	finally 
	    	{
	    		try 
	    		{
	    			if (rs != null) 
	    			{
	    				rs.close();
	    				rs = null;
	    			}
	    		} catch (Exception e) 
	    		{
	    			logger.error("Exception ", e);
	    		}

	    		try 
	    		{
	    			if (stmt != null) 
	    			{
	    				stmt.close();
	    				stmt = null;
	    			}
	    		} catch (Exception e) 
	    		{
	    			logger.error("Exception ", e);
	    		}

	    		try 
	    		{
	    			if (con != null)
	    				DBConnectionManager.getInstance().freeConnection(con);
	    		} catch (Exception e) 
	    		{
	    			logger.error("Exception ", e);
	    		}
	    	}
	    	return flag;
	    }
	    
	    	  
    //P_B__DRP_51523
  
		public static String getLibraryFolderNo(String DocNo)throws ConnectionException 
		{
			Connection con = null;
			String folderNo = "";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList adoc=new ArrayList(0);
			String flag="N";
			
			String query="SELECT DOCUMENT_NO FROM LIBRARY_DOCUMENTS";
            com.home.builderforms.sqlqueries.ResultSet rs1=QueryUtil.getResult(query,null);
            while(rs1.next()){
                adoc.add(rs1.getString("DOCUMENT_NO"));
            }
			if(!adoc.contains(DocNo)){
				return flag;
			}else{
			try 
			{
				con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
				pstmt = con.prepareStatement("SELECT FOLDER_NO FROM LIBRARY_DOCUMENTS WHERE DOCUMENT_NO ='"+ DocNo + "'");
				rs = pstmt.executeQuery();
				if(rs.next()) {
					folderNo = rs.getString("FOLDER_NO");
				}
			}catch (SQLException e) {
				Debug.print(e);
		} finally {
		try {
			closeStatements(pstmt, rs);
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		} catch (Exception e) {
		}

		return folderNo;
	}
			}
}
		
               
       public static boolean isMultiplePassword(){
   		StringBuffer query = new StringBuffer("SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE="+MasterEntities.CAPTIVATE_PASSWORD_CONFIG);
   		String dataValue ="";
   		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
   		if(result.next()) {
                           dataValue=result.getString("DATA_VALUE");
                       }
                        if(dataValue!=null && !"".equals(dataValue) && !"null".equals(dataValue) && "Yes".equalsIgnoreCase(dataValue))
                       {
   			return true;
   		} else {
   			return false;
   		}
   	}
               
      /* public static boolean isPasswrodAlredyGenerated(String leadId){
   		StringBuffer query = new StringBuffer("SELECT COUNT(*) AS COUNT FROM FS_CAPTIVATE_PASSWORD WHERE LEAD_ID= '"+leadId+"' ");
   		int count=0;
   		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
   		if(result.next()) {
                           count=Integer.parseInt(result.getString("COUNT"));
                       }
                       if(count>0)
                       {
   			return true;
   		} else {
   			return false;
   		}
   	}*/
          	
       public static String replaceSpecialChars(String sourceString, String [] specialChars)
       {
			if(sourceString == null) return "";
			if(specialChars==null || specialChars.length == 0) return sourceString;
			//char blank = ' ';
			//StringBuffer sourceStringBuffer = new StringBuffer(sourceString);
			Pattern p = null;
			
			Matcher m = null;			

			for (int i=0; i<specialChars.length ; i++){				
				
				//String nextToken = (String) fqST.nextToken();

				//int count = 0;
				
				p = Pattern.compile("(?i:\\"+specialChars[i]+")");
				
				m = p.matcher(sourceString); // get a matcher object
				
				sourceString = m.replaceAll(FieldNames.EMPTY_STRING);
			}
			return sourceString;
		}    
		//BB_CaptivateMultiplePassword_E_23102009 ends
      
 public static void generateCandidatePortalPassword()
	{
           
           //System.out.println("################inside generateCandidatePortalPassword############");
		  Set passwordList=null;
                 passwordList=new HashSet();//ParisB-20151021-016
		  ArrayList leadIDList=null;
                 leadIDList=new ArrayList();
		  String password="";
		  Info mainInfo=null;
                 mainInfo=new Info();
		  Info info=null;
                 String spcialChar[] = {"\"","\'","!","@","#","$","%","^","&","*","(",")","+","=","[","]",";","/","{","}","|","<",">","?","_"};
                 info=new Info();
		  String query1="SELECT FLD.LEAD_ID,REPLACE(CONCAT(LOWER(SUBSTRING(FLD.FIRST_NAME,1,1)),LOWER(IF(LOCATE(' ',LAST_NAME)=0,LAST_NAME,SUBSTRING(LAST_NAME,1,LOCATE(' ',LAST_NAME)-1)))),' ','') AS LAST_NAME,CONCAT(REPLACE(CONCAT(LOWER(SUBSTRING(FLD.FIRST_NAME,1,1)),LOWER(IF(LOCATE(' ',LAST_NAME)=0,LAST_NAME,SUBSTRING(LAST_NAME,1,LOCATE(' ',LAST_NAME)-1)))),' ',''),cast(FLOOR(concat('1',REPEAT('0',3)) + (RAND() * CONCAT('9',REPEAT('0',3))))as binary)) AS CANDIDATE_PASSWORD FROM FS_LEAD_DETAILS FLD WHERE CANDIDATE_PASSWORD IS NULL OR CANDIDATE_PASSWORD IN ('null','')";
		  com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(query1, new Object[] {});
		  try
		  {
                     
                       while(rs.next())
                       {
                           info=null;
                         info=new Info();
                         info.set(FieldNames.LEAD_ID,rs.getString("LEAD_ID"));
                         info.set(FieldNames.CANDIDATE_PASSWORD,replaceSpecialChars(rs.getString("CANDIDATE_PASSWORD"),spcialChar));
                         info.set(FieldNames.LAST_NAME,rs.getString("LAST_NAME"));
                         mainInfo.set(rs.getString("LEAD_ID"),info);
                         leadIDList.add(rs.getString("LEAD_ID"));
                       }
                       if(leadIDList.size()!=0)
                       {
                                 query1="SELECT CANDIDATE_PASSWORD FROM FS_LEAD_DETAILS";
                                 rs = QueryUtil.getResult(query1, new Object[] {});
                                 while(rs.next())
                                         passwordList.add(replaceSpecialChars(rs.getString("CANDIDATE_PASSWORD"),spcialChar));

                                 for(int i=0;i<leadIDList.size();i++)
                                 {
                                         password=((Info)mainInfo.getObject(leadIDList.get(i).toString())).getString(FieldNames.CANDIDATE_PASSWORD);
                                         //This password already exist for another lead.
                                         //ParisB-20151021-016 starts
                                         if(passwordList.contains(password))
                                         {
                                                 // Until a new password is not genrated.
                                                         password=((Info)mainInfo.getObject(leadIDList.get(i).toString())).getString(FieldNames.LAST_NAME) +new Random().nextInt(9999);
                                                         password = replaceSpecialChars(password,spcialChar);
                                          }
                                         if(passwordList.contains(password))
                                         {
                                        	 password=((Info)mainInfo.getObject(leadIDList.get(i).toString())).getString(FieldNames.LAST_NAME) +leadIDList.get(i);
                                             password = replaceSpecialChars(password,spcialChar);
                                         }
                                         //ParisB-20151021-016 ends
                                         QueryUtil.update("UPDATE FS_LEAD_DETAILS SET CANDIDATE_PASSWORD=? WHERE LEAD_ID=?",new String[]{password,leadIDList.get(i).toString()});
                                         // Added new password into the exist password list
                                         passwordList.add(password);
                                 }
                       }
		  }catch(Exception e)
		  {
			  logger.info("Exception in generateCandidatePortalPassword",e);
                         e.printStackTrace();
		  }
                 
                 query1=null;
		  rs=null;
		  passwordList=null;
		  leadIDList=null;
		  mainInfo=null;
		  info=null;
		  password=null;
                                 
	}
      
   	
    //P_DASHBOARD_B_QueryOptimization
    public static Map<String, Map<String, String>> getStatusLead(List<String[]> list) 
    {
    	Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
    	try{
    		String query = "SELECT count(LEAD_ID) AS COUNT ,FLSC.STATUS_CATEGORY_ID, REQUEST_DATE FROM FS_LEAD_DETAILS FLD LEFT JOIN FS_LEAD_STATUS FLS ON FLS.LEAD_STATUS_ID = FLD.LEAD_STATUS_ID LEFT JOIN FS_LEAD_STATUS_CATEGORY FLSC ON   FLSC.STATUS_CATEGORY_ID=FLS.STATUS_CATEGORY GROUP BY STATUS_CATEGORY_ID, REQUEST_DATE";
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query,null);
    		SimpleDateFormat fmt = new SimpleDateFormat (DateUtil.DB_FORMAT);

    		while(result.next()) 
    		{
    			String leadCount = result.getString("COUNT");
    			String statusCategoryId = result.getString("STATUS_CATEGORY_ID");
    			Date requestDate = result.getDate("REQUEST_DATE");


    			for (String[] values : list) {
    				Date fromDate = fmt.parse(values[1]);

    				Date toDate = fmt.parse(values[2]);

    				if (requestDate.equals(fromDate) || requestDate.after(fromDate) && (requestDate.equals(toDate)|| requestDate.before(toDate)))
    				{
    					if (map.get(values[0]) != null)
    					{
    						Map<String, String> statusMap = map.get(values[0]);
    						if (statusMap.get(statusCategoryId) != null)
    						{
    							statusMap.put(statusCategoryId, Integer.toString(Integer.parseInt(statusMap.get(statusCategoryId))+Integer.parseInt(leadCount)));
    						}
    						else
    						{
    							statusMap.put(statusCategoryId, Integer.toString(Integer.parseInt(leadCount)));
    						}
    					}
    					else
    					{
    						Map<String, String> statusMap = new HashMap<String, String>();
    						statusMap.put(statusCategoryId, Integer.toString(Integer.parseInt(leadCount)));
    						map.put(values[0], statusMap);
    					}

    				}
    			}

    		}							
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	return map;
    }
    

  
    public static HashMap getStatusCount(String fromDate,String toDate){
    	//P_DASHBOARD_B_QueryOptimization
    	return getStatusCount(AdminMgr.newInstance().getFsLeadStatusDAO().getStatusLead(fromDate,toDate));
    }
    
    //P_E_NewDashBoard
    public static HashMap getStatusCount(HashMap  statusMap)
 	  {
    	//P_DASHBOARD_B_QueryOptimization
 		HashMap  returnMap =new HashMap(); 
 		int activeValue=0;
 		int inActiveValue=0;
 		int closedValue=0;
 		int killedValue=0;
 		if(statusMap !=null)
 		{
 			if(statusMap.get("1")!=null )
 			{
 				activeValue =Integer.parseInt(statusMap.get("1").toString());
 			}
 			else
 			{
 				activeValue=0;
 			}
 			if(statusMap.get("2")!=null )
 			{
 				inActiveValue =Integer.parseInt(statusMap.get("2").toString());
 			}
 			else
 			{
 				inActiveValue=0;
 			}
 			if(statusMap.get("3")!=null )
 			{
 				closedValue =Integer.parseInt(statusMap.get("3").toString());
 			}
 			else
 			{
 				closedValue=0;
 			}
 			if(statusMap.get("4")!=null )
 			{
 				killedValue =Integer.parseInt(statusMap.get("4").toString());
 			}
 			else
 			{
 				killedValue=0;
 			}
 		}
 		else
 		{
 			activeValue=0;
 			inActiveValue=0;
 			closedValue=0;
 			killedValue=0;
 		}
 		returnMap.put("0", activeValue);
 		returnMap.put("1", inActiveValue);
 		returnMap.put("2", closedValue);
 		returnMap.put("3", killedValue);
 		return returnMap;
 	}
    //P_E_NewDashBoard
    public static HashMap getClosingPercentMap(HashMap yearStatusMap ,HashMap monthStatusMap ,HashMap lastYearStatusMap)
    {
     	HashMap closingPercentMap = new HashMap();
     	int sum=Integer.parseInt(yearStatusMap.get("0").toString())+Integer.parseInt(yearStatusMap.get("1").toString())+Integer.parseInt(yearStatusMap.get("2").toString())+Integer.parseInt(yearStatusMap.get("3").toString());
     	double percent=0;
     	NumberFormat percatageFormat = NumberFormat.getPercentInstance();
     	percatageFormat = new DecimalFormat("0.0#"); 
     	if(sum>0)
     	{
     		percent=(double)((double)Integer.parseInt(yearStatusMap.get("2").toString())/sum)*100;
     	}
     	closingPercentMap.put("ytd",percatageFormat.format(percent));
     	
     	sum=Integer.parseInt(monthStatusMap.get("0").toString())+Integer.parseInt(monthStatusMap.get("1").toString())+Integer.parseInt(monthStatusMap.get("2").toString())+Integer.parseInt(monthStatusMap.get("3").toString());
     	percent=0;
     	if(sum>0)
     	{
     		percent=(double)((double)Integer.parseInt(monthStatusMap.get("2").toString())/sum)*100;
     	}
     	closingPercentMap.put("mtd",percatageFormat.format(percent));
     	
     	sum=Integer.parseInt(lastYearStatusMap.get("0").toString())+Integer.parseInt(lastYearStatusMap.get("1").toString())+Integer.parseInt(lastYearStatusMap.get("2").toString())+Integer.parseInt(lastYearStatusMap.get("3").toString());
     	percent=0;
     	if(sum>0)
     	{
     		percent=(double)((double)Integer.parseInt(lastYearStatusMap.get("2").toString())/sum)*100;
     	}
     	closingPercentMap.put("lastYear",percatageFormat.format(percent));

     	return closingPercentMap;
     }
    
     /**
         * This method creates the dropdown list of months which displays months in words.
      *DemoBuild_CR_FS_001
        */
    
     public static String getMonthDisplayComboInWords(int month1)
        {
         return  getMonthDisplayComboInWords(month1,null);
            
        }
        
        public static String getMonthDisplayComboInWords(int month1,String disabled)
        {
         return  getMonthDisplayComboInWords(month1,disabled,null);
            
        }
               
        public static String getMonthDisplayComboInWords(int month1,String disabled,String fromwhere){
	
                String month = "";
                String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

                if(disabled != null && !disabled.trim().equals("") && !disabled.trim().equalsIgnoreCase("null") && disabled.equalsIgnoreCase("DISABLED"))
                {    
		month += ("<select name ='birthMonth' id='birthMonth' class='multiList' disabled  onChange=javascript:getMonth()>");
                }//P_FRANONLINE_ENC
                else if(fromwhere != null && !fromwhere.trim().equals("") && !fromwhere.trim().equalsIgnoreCase("null") && fromwhere.trim().equalsIgnoreCase("franOnlineProfile"))
                {
                 month += ("<select name ='birthMonth' id='birthMonth' class='inputList' onChange=javascript:getMonth()>");   
                }
                //ZCUB-20150728-166 start
                else if("profileInfo".equals(fromwhere)){
                	month += ("<select name ='birthMonth' id='birthMonth' class='multiList newInput half' onChange=javascript:getMonth()>");  
                }//ZCUB-20150728-166 end
                else if(fromwhere != null && !fromwhere.trim().equals("") && !fromwhere.trim().equalsIgnoreCase("null"))
                {
                 month += ("<select name ='"+fromwhere+"birthMonth' id ='"+fromwhere+"birthMonth' class='multiList' onChange=javascript:getMonth()>");      
                }
                else{   
                 month += ("<select name ='birthMonth' id='birthMonth' class='multiList' onChange=javascript:getMonth()>");   
                }
                if(month1==-1){
                        month +="<option value ='-1' selected>"+LanguageUtil.getString("Select Month")+"";
                }else{
                        month +="<option value ='-1' >"+LanguageUtil.getString("Select Month")+"";
                }
                
		for(int count = 0;count<12;count++){
                    
                    if(count < 10){
			if(count < 10){
		            if(month1==count){
				month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                            }else{
                                month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                            }    
			}
			else{
				month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
			}
                    }else{
                        if(count < 10){
				month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
			}
			else{
                            if(month1==count){
				month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                            }else{
                                month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                            }    
			}
                    }   
		}

                month += "</select>";
		
		return month;

        }
     
        //added by ankush tanwar starts

        /**
         * This method creates the dropdown list of dates according to month.
         *DemoBuild_CR_FS_001
        */
     
       public static String getDateDisplayCombo(int date1,int month)
         {
            return getDateDisplayCombo(date1,month,null);
         }
         
         public static String getDateDisplayCombo(int date1,int month,String disabled)
         {
            return getDateDisplayCombo(date1,month,disabled,null);
         }
        
       
        public static String getDateDisplayCombo(int date1,int month,String disabled,String fromWhere){
	
                String date = "";
                int limit=32;
                if(month==1){
                    limit=30;
                }
                if(month==3||month==5||month==8||month==10){
                    limit=31;
                }
                if(disabled != null && !disabled.trim().equals("null") && !disabled.trim().equals(""))
                {   
		date += ("<select name ='birthDate' id='birthDate' class='multiList' disabled>");
                }//P_FRANONLINE_ENC 
                else if(fromWhere != null && !fromWhere.trim().equals("") && !fromWhere.trim().equalsIgnoreCase("null") && fromWhere.trim().equalsIgnoreCase("franOnlineProfile"))
                {  
                     date += ("<select name ='birthDate'  class='inputList' id='birthDate'>");
                }
                //ZCUB-20150728-166 start
                else if("profileInfo".equals(fromWhere)){
                	 date += ("<select name ='birthDate'  class='multiList newInput half' id='birthDate'>");
                }//ZCUB-20150728-166 end
                else if(fromWhere != null && !fromWhere.trim().equals("") && !fromWhere.trim().equalsIgnoreCase("null"))
                {
                    date += ("<select name ='"+fromWhere+"birthDate'  class='multiList' ='"+fromWhere+"birthDate'>");
                }else   
                {
                    date += ("<select name ='birthDate'  class='multiList' id='birthDate'>");
                }
                if(date1==-1){
		     date += ("<option selected value ='-1'>"+LanguageUtil.getString("Select Date")+"");
                }else{
                     date += ("<option value ='-1'>"+LanguageUtil.getString("Select Date")+"");
                }
		for(int count = 1;count<limit;count++){
                    if(date1 < 10){
			if(count < 10){
		            if(date1==count){
				date += ("<option selected value = " + count + ">0" + count);
                            }else{
                                date += ("<option value = " + count + ">0" + count);
                            }    
			}
			else{
				date += ("<option value = " + count + ">" + count);
			}
                    }else{
                        if(count < 10){
				date += ("<option value = " + count + ">0" + count);
			}
			else{
                            if(date1==count){
				date += ("<option selected value = " + count + ">" + count);
                            }else{
                                date += ("<option value = " + count + ">" + count);
                            }    
			}
                    }   
		}
		date += "</select>";


		
		return date;

        }
        
        
         /** 
         *This method is used  to convert private field into ******
          @param privateField - String private field as input patameter.
           return type String with **** as return DemoBuild_CR_FS_001
       */
        
         public  static String getPrivateFieldFormat(String privateField)
         {
             return getPrivateFieldFormat(privateField,null);
         }
        
        
        public  static String getPrivateFieldFormat(String privateField,String bdtate)
        {
          String privateFieldConversion = "" ;
          //as per the requirement
          int privateFieldNumber = 4 ;
          int secondPrivateNo = privateFieldNumber - 2 ;
         if(privateField != null && !privateField.trim().equals("") && !privateField.trim().equals("null") && !"<a href='mailto:'></a>".equals(privateField.trim()))
          {
            if(privateField.indexOf("</a>")!=-1 || (bdtate !=null && !bdtate.trim().equals("") && !bdtate.trim().equalsIgnoreCase("null") && bdtate.trim().equalsIgnoreCase("bDate")))//P_CM_B_48051 
            {
                privateFieldConversion = "******";
            }  
              
            else if(privateField.length() > privateFieldNumber)
             {
               
                privateFieldConversion = "******" + privateField.substring((privateField.length() - privateFieldNumber),privateField.length());
             }
             else if(privateField.length()<= privateFieldNumber && privateField.length() > secondPrivateNo)
              {
                
                privateFieldConversion = "******" + privateField.substring((privateField.length() - secondPrivateNo),privateField.length());
              }
             else if(privateField != null && (privateField.length()<= secondPrivateNo))
             {
               privateFieldConversion = "******";
             }
          }
           return privateFieldConversion;
        }
        
    /**
         * This method retrives the retrives all the month names.DemoBuild_CR_FS_001
        */
        public static String getMonthName(int month1){
	
                String month = "";
                String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

                for(int count=0;count<12;count++){
                    if(month1==count){
                        month=monthNames[count];
                    }
                }
		
		return month;

        }
        
        /**
         * This method retrives the month number from month name
         *DemoBuild_CR_FS_001
        */
        
        public static String getMonthNumer(String monthName)
        {
            String monthNum = "";
            if(monthName != null && !monthName.trim().equals("") && !monthName.trim().equalsIgnoreCase("null"))
            {
              if(monthName.equalsIgnoreCase("jan") || monthName.equalsIgnoreCase("January"))
                  monthNum = "0";
              else if(monthName.equalsIgnoreCase("Feb") || monthName.equalsIgnoreCase("February"))
                  monthNum = "1";  
              else if(monthName.equalsIgnoreCase("Mar") || monthName.equalsIgnoreCase("March"))
                  monthNum = "2"; 
              else if(monthName.equalsIgnoreCase("Apr") || monthName.equalsIgnoreCase("April"))
                  monthNum = "3";
              else if(monthName.equalsIgnoreCase("May"))
                  monthNum = "4";
              else if(monthName.equalsIgnoreCase("Jun") || monthName.equalsIgnoreCase("June"))
                  monthNum = "5";
              else if(monthName.equalsIgnoreCase("Jul") || monthName.equalsIgnoreCase("July"))
                  monthNum = "6";
              else if(monthName.equalsIgnoreCase("Aug") || monthName.equalsIgnoreCase("August"))
                  monthNum = "7";
              else if(monthName.equalsIgnoreCase("Sep") || monthName.equalsIgnoreCase("September"))
                  monthNum = "8";
              else if(monthName.equalsIgnoreCase("Oct") || monthName.equalsIgnoreCase("October"))
                  monthNum = "9";
              else if(monthName.equalsIgnoreCase("Nov") || monthName.equalsIgnoreCase("November"))
                  monthNum = "10";
              else if(monthName.equalsIgnoreCase("Dec") || monthName.equalsIgnoreCase("December"))
                  monthNum = "11";
            }  
            return   monthNum; 
        }
   
        
     
        
   /**
         * This method creates the dropdown list of months which displays months in words.DemoBuild_CR_FS_001
        */
          
         public static String getSpouseMonthDisplayComboInWords(int month1)
         {
             return getSpouseMonthDisplayComboInWords(month1,null);
         }

         
          public static String getSpouseMonthDisplayComboInWords(int month1,String disabled)
         {
             return getSpouseMonthDisplayComboInWords(month1,disabled,null);
         }

        public static String getSpouseMonthDisplayComboInWords(int month1,String disabled,String fromWhere){
                String month = "";
                String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

                if(disabled != null && !disabled.trim().equals("") && !disabled.trim().equalsIgnoreCase("null") && disabled.equalsIgnoreCase("disabled"))
                {    
		month += ("<select name ='spouseBirthMonth' id='spouseBirthMonth'  class='multiList' disabled onChange=javascript:getSpouseMonth()>");
                }else if(fromWhere != null && !fromWhere.trim().equals("") && !fromWhere.trim().equalsIgnoreCase("null"))
                {
                 month += ("<select name ='"+fromWhere+"spouseBirthMonth' id ='"+fromWhere+"spouseBirthMonth' class='multiList' onChange=javascript:getSpouseMonth()>");      
                }else 
                {
                 month += ("<select name ='spouseBirthMonth' id='spouseBirthMonth' class='multiList' onChange=javascript:getSpouseMonth()>");   
                }
                
                if(month1==-1){
                        month +="<option value ='-1'>"+LanguageUtil.getString("Select Month")+"";
                }else{
                        month +="<option value ='-1' selected>"+LanguageUtil.getString("Select Month")+"";
                }
                        
		for(int count = 0;count<12;count++){
                    
                    if(count < 10){
			if(count < 10){
		            if(month1==count){
				month += ("<option selected value = " + count + ">" + monthNames[count]);
                            }else{
                                month += ("<option value = " + count + ">" + monthNames[count]);
                            }    
			}
			else{
				month += ("<option value = " + count + ">" + monthNames[count]);
			}
                    }else{
                        if(count < 10){
				month += ("<option value = " + count + ">" + monthNames[count]);
			}
			else{
                            if(month1==count){
				month += ("<option selected value = " + count + ">" + monthNames[count]);
                            }else{
                                month += ("<option value = " + count + ">" + monthNames[count]);
                            }    
			}
                    }   
		}

                month += "</select>";
		
		return month;

        }
         /**
         * This method creates the dropdown list of dates according to month.DemoBuild_CR_FS_001
        */
        
         public static String getSpouseDateDisplayCombo(int date1,int month)
         {
             return getSpouseDateDisplayCombo(date1,month,null);
         }
         
         
          public static String getSpouseDateDisplayCombo(int date1,int month,String disabled)
         {
             return getSpouseDateDisplayCombo(date1,month,disabled,null);
         }
         
        
        public static String getSpouseDateDisplayCombo(int date1,int month,String disabled,String fromWhere){
	
                String date = "";
                int limit=32;
                if(month==1){
                    limit=30;
                }
                if(month==3||month==5||month==8||month==10){
                    limit=31;
                }
                 if(disabled != null && !disabled.trim().equals("") && !disabled.trim().equalsIgnoreCase("null") && disabled.equalsIgnoreCase("disabled"))
                 {   
		date += ("<select name ='spouseBirthDate' id='spouseBirthDate' class='multiList' disabled>");
                 }else if(fromWhere != null && !fromWhere.trim().equalsIgnoreCase("null") && !fromWhere.trim().equals(""))
                 {
                   date += ("<select name ='"+fromWhere+"spouseBirthDate' id='"+fromWhere+"spouseBirthDate' class='multiList' >");  
                 }   
                 else{
                 date += ("<select name ='spouseBirthDate' id='spouseBirthDate' class='multiList' >");   
                 }
                if(month==-1&&date1==-1){
		     date += ("<option selected value ='-1'>"+LanguageUtil.getString("Select Date")+"");
                }
		for(int count = 1;count<limit;count++){
                    if(date1 < 10){
			if(count < 10){
		            if(date1==count){
				date += ("<option selected value = " + count + ">0" + count);
                            }else{
                                date += ("<option value = " + count + ">0" + count);
                            }    
			}
			else{
				date += ("<option value = " + count + ">" + count);
			}
                    }else{
                        if(count < 10){
				date += ("<option value = " + count + ">0" + count);
			}
			else{
                            if(date1==count){
				date += ("<option selected value = " + count + ">" + count);
                            }else{
                                date += ("<option value = " + count + ">" + count);
                            }    
			}
                    }   
		}
		date += "</select>";


		
		return date;

        }

        //added by ankush tanwar ends
      /**
         * This method retrives the retrives the dates for the corresponding month.DemoBuild_CR_FS_001
        */
        public static ArrayList getDateForMonth(int birthMonth){
		ArrayList returnList	= new ArrayList();
		String [] birthDate			= null;
		int limit=32;
                if(birthMonth==1){
                    limit=30;
                }
                if(birthMonth==3||birthMonth==5||birthMonth==8||birthMonth==10){
                    limit=31;
                }
                
			for(int count=1;count<limit;count++){
				birthDate = new String[2];
                                
				birthDate[0]=count+"";
				birthDate[1]=count+"";
                                

				returnList.add(birthDate);
			}

		
		return returnList;

}
                       
public static boolean showGuestOfHonor(){
		boolean showGuestOfHonor = false;
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		if(StringUtil.isValidNew(_baseConstants.SHOW_GUEST_OF_HONOR) && _baseConstants.SHOW_GUEST_OF_HONOR.trim().equals("Y")){
			showGuestOfHonor = true;
		}
		
		return showGuestOfHonor;
}

/**
 * SC_FS_FIM_Task_04092008
 * 
 * @author mukesh
 * @param :
 *            franNo
 * @return : FIM Owner (String)
 */
public static String getFIMOwnerName(String franNo)
		throws ConnectionException {
	String fName = "";
	String lName = "";
	String sName = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	java.sql.ResultSet rs = null;
	try {
		if (franNo != null && !"null".equals(franNo)
				&& !"".equals(franNo.trim())) {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			pstmt = con
					.prepareStatement("SELECT FO.OWNER_LAST_NAME, FO.OWNER_MIDDLE_NAME, FO.OWNER_FIRST_NAME  FROM FRANCHISEE F, FIM_OWNERS FO, OWNERS O WHERE F.FRANCHISEE_NO = O.FRANCHISEE_NO AND O.OWNER_ID = FO.FRANCHISE_OWNER_ID AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND F.STATUS = '1' AND F.FRANCHISEE_NO = '"
							+ franNo + "'");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				fName = rs.getString("OWNER_LAST_NAME");

				lName = rs.getString("OWNER_FIRST_NAME");

				sName = StringUtil.formatNameWithSpace(fName, lName);
			}
			try {
				pstmt.close();
				pstmt = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			//DBConnectionManager.getInstance().freeConnection(con);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		SQLUtil.closeStmt(pstmt);
		SQLUtil.closeResultSet(rs);
		DBConnectionManager.getInstance().freeConnection(con);
	}
	return sName;
}

//added by ankush tanwar for service314 changes starts


/**
	 * Get Address Id of contact
	 * @author abhishek gupta
	 * @date 22Apr2009
	 * @param contactId
	 * @param type
	 * @return
	 */
	public static String getAddressIdOfType(String contactId, String type) {
		String addressId = "";
		Connection con = null;
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		String query = "SELECT ADDRESS_ID FROM ADDRESS WHERE FOREIGN_ID = "	+ contactId;
		if(type != null && !"".equals(type)) {
			query = query + " AND FOREIGN_TYPE='" + type + "' ";;
		}
		try {
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				
				addressId = rs.getString("ADDRESS_ID");
			}

		}

		catch (Exception e) {
			logger.info("Exception in getAddressIdOfType");
		}

		finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					DBConnectionManager.getInstance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return addressId;
	}
       
//added by ankush tanwar for service314 changes ends

public static SequenceMap getAllFranchiseesMap() {
	return getAllFranchiseesMap(null);
}

public static SequenceMap getAllFranchiseesMap(String areaId) {
	// SequenceMap returnMap=null;
	SequenceMap returnMap = new SequenceMap();
	Info info = new Info();
	StringBuffer query = new StringBuffer(
			//"SELECT FRANCHISEE_NO, FRANCHISEE_NAME FROM FRANCHISEE WHERE IS_FRANCHISEE='Y' ");
			"SELECT FRANCHISEE_NO, FRANCHISEE_NAME FROM FRANCHISEE WHERE IS_FRANCHISEE='Y' AND STATUS IN('1','3')"); // FOR BUG 4882
	if (areaId != null)
		query.append("AND AREA_ID = ").append(areaId).append(" ");
	query.append("ORDER BY FRANCHISEE_NAME ");
	com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
			.getResult(query.toString(), new Object[] {});
	while (result.next()) {
		info = new Info();
		// info.set("FRANCHISEE_NAME", result.getString("FRANCHISEE_NAME"));
		returnMap.put(result.getString("FRANCHISEE_NO"), result
				.getString("FRANCHISEE_NAME"));
	}
	return returnMap;
}

/**
 * Created By : Siddharth Singh This method retrieves the Data Value of
 * Given dataType for MASTER_DATA
 * 
 * @param dataType-
 *            Input String containing Area Id.
 * @return -info containg Data Value with Parent Id as key.
 */
public static Info getDataValueWithParentId(String dataType) {
	Info info = new Info();
	StringBuffer query = new StringBuffer(
			"SELECT * FROM MASTER_DATA WHERE DATA_TYPE=" + dataType + "");
	com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
			.getResult(query.toString(), new Object[] {});
	while (result.next()) {
		info.set(result.getString("PARENT_DATA_ID"), result
				.getString("DATA_VALUE"));
	}
	return info;
}

public static Info getUserNameInfo(String userno) 
{
	Info userName = new Info();
	HashMap<String,Object> userDetails = NewPortalUtils.getUserDetails(userno);	
	if (userDetails != null && !userDetails.isEmpty())
	{
		userName.set("FIRST_NAME", userDetails.get(FieldNames.USER_FIRST_NAME));
		userName.set("LAST_NAME", userDetails.get(FieldNames.USER_LAST_NAME));
	}
	return userName;
}

public static String getFranchiseeCombo(String name, String location) {
	StringBuffer returnString = new StringBuffer();
	String query = "SELECT FRANCHISEE_NO,FRANCHISEE_NAME FROM FRANCHISEE WHERE IS_FRANCHISEE ='Y' ORDER BY FRANCHISEE_NAME";
	com.home.builderforms.sqlqueries.ResultSet result = null;
	String array[] = null;
	if (location != null && !location.equals("")
			&& location.indexOf(",") != -1) {
		array = location.split(",");
	} else {
		array = new String[1];
		array[0] = location;
	}

	try {
		result = QueryUtil.getResult(query,null);
	} catch (Exception e) {
		logger.info("Exception in getFranchiseeCombo while firing query"
				+ e);
	}
	
	returnString.append("<select class='multiList' name='");
	returnString.append(name);
   
	   returnString.append("'><option value=\"-1\">Select</option>");
    
	String fName = null;
	String fNo = null;
	boolean flag = false;
	while (result.next()) {
		fNo = result.getString("FRANCHISEE_NO");
		fName = result.getString("FRANCHISEE_NAME");
		returnString.append("<option value=\"");
		returnString.append(fNo);
		if (location != null && !location.equals("")) {
			flag = false;
			for (int i = 0; i < array.length; i++) {
				if ((array[i].trim()).equals(fNo)) {
					flag = true;
					returnString.append("\" selected>");
					//returnString.append("'>");
				}
			}
			if (flag == false) {
				returnString.append("\">");
			}
		} else {
			returnString.append("\">");
		}
		returnString.append(fName);
		returnString.append("</option>");
	}
	returnString.append("</select>");
	return returnString.toString();
}

// Added By Praveen Chaturvedi


public static String getFranchiseNamesList(String franchiseeNoList)
throws ConnectionException {
	Connection con = null;
	String franchiseeNameList = "";
	// String franchiseeNoList = "";
	Statement stmt = null;
	int count = 0;
	java.sql.ResultSet rs = null;

	try {
		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
		stmt = con.createStatement();
		rs = stmt
		.executeQuery("SELECT FRANCHISEE_NAME FROM FRANCHISEE WHERE FRANCHISEE_NO IN ("
				+ franchiseeNoList + ")");
		while (rs.next()) {
			count++;
			if (count == 1)
				franchiseeNameList = rs.getString("FRANCHISEE_NAME");
			else {
				franchiseeNameList = franchiseeNameList + ", ";
				franchiseeNameList = franchiseeNameList
				+ rs.getString("FRANCHISEE_NAME");
			}
		}
		return franchiseeNameList;
	} catch (SQLException e) {
		Debug.print(e);
		return "";
	} catch (Exception e) {
		Debug.print(e);
		return "";
	} finally {
		try {
			/**
			 * close statment and results set add by garima dated 28April06
			 */
			closeStatements(stmt, rs);
			DBConnectionManager.getInstance().freeConnection(con);
		} catch (Exception e) {
		}
	}
}



public static SequenceMap getTmsProductNamesService314(String check, String userLevel) {
	return getTmsProductNamesService314(check, userLevel, null);
}

public static SequenceMap getTmsProductNamesService314(String check,
		String userLevel, String selectedValue) {
	return getTmsProductNamesService314(check, userLevel, selectedValue, true);
}
public static SequenceMap getTmsProductNamesService314(String check,
		String userLevel, String selectedValue, boolean isAddNew) {
	SequenceMap dataMap = null;
	String query = null;
	// UserRoleMap TMSUserRoleMap =
	// (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	try {
		if (1 == 2) {
			query = "SELECT SUPPLIER_ITEM_ID AS PRODUCT_ID, ITEM_NAME AS PRODUCT_NAME FROM SUPPLIER_ITEMS WHERE STATUS = '1' ORDER BY ITEM_NAME ";
		} else {
			query = "SELECT PRODUCT_ID, PRODUCT_NAME FROM TMS_PRODUCTS WHERE STATUS = 'A' ORDER BY PRODUCT_NAME ";
		}
		com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil
				.getResult(query, new Object[] {});
		dataMap = new SequenceMap();
		while (rs.next()) {
			dataMap.put(rs.getString("PRODUCT_ID"), rs
					.getString("PRODUCT_NAME"));
		}
		if (userLevel != null
				&& userLevel.equals("1")
				&& (selectedValue == null || (selectedValue != null && !selectedValue
						.trim().equalsIgnoreCase("addtask")))) {
			if (check != null && check.equals("Y")) {
				dataMap.put("addMore", "Add New Product");
			}
		} else if (isAddNew && (selectedValue == null || (selectedValue != null && !selectedValue.trim()
						.equalsIgnoreCase("addtask")))) {
			dataMap.put("addMore", "Add New Product");
		}
	} catch (Exception e) {
		Debug.println("Exception in PortalUtil-->getProduct(): " + e);
	}

	return dataMap;
}

/**
 * @author abhishek gupta
 * @date 24Apr2009
 * @param eventId
 * @return
 */
public static String  getEventLastUsed(String eventId) {
	String countQuery = "SELECT DATE FROM TASKS WHERE EVENT_ID="+ eventId+" ORDER BY DATE DESC LIMIT 1";
	String eventDate = "";
	Connection con = null;
	Statement stmt = null;
	java.sql.ResultSet rs = null;
	
	try {
		con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
		stmt = con.createStatement();
		rs = stmt.executeQuery(countQuery);
		if (rs.next()) {
			eventDate = rs.getString("DATE");
		}
	}

	catch (Exception e) {
		logger.info("Exception in getEventLastUsed");
	}

	finally {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		}
		try {
			if (con != null)
				DBConnectionManager.getInstance().freeConnection(con);
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}
	return eventDate;
}

public static Info getMappingInfo(String userNo) {

	Connection con = null;
	Info mapInfo = new Info();
	String query = "SELECT ABBREVIATED_MODULE_NAME,ABBREVIATED_SUBMODULE_NAME,MU.SUB_MENU_PRIVILEGE,MENU_PRIVILEGE,MU.SUB_MODULE_URL FROM MODULE_USER_PAGE_MAPPING MU, MODULE_LIST ML, SUB_MODULE_LIST SML WHERE ROOT_MODULE='Y' AND MU.MODULE_NAME=ML.MODULE_KEY AND MU.SUB_MODULE_NAME=SML.SUB_MODULE_ID AND MU.USER_NO="
			+ userNo;        
	Statement stmt = null;
	java.sql.ResultSet result = null;

	con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);


	try {
		stmt = con.createStatement();
		result = stmt.executeQuery(query.toString());
		while (result.next()) {
			mapInfo.set("ABBR_MODULE_NAME", result
					.getString("ABBREVIATED_MODULE_NAME"));
			mapInfo.set("ABBR_SUBMODULE_NAME", result
					.getString("ABBREVIATED_SUBMODULE_NAME"));
			mapInfo.set("SUB_MENU_PRIVILEGE", result
					.getString("SUB_MENU_PRIVILEGE"));
			mapInfo.set("MENU_PRIVILEGE", result
					.getString("MENU_PRIVILEGE"));
			mapInfo.set("SUB_MODULE_URL", result
					.getString("SUB_MODULE_URL"));
		}
		if(mapInfo.size()==0 && ModuleUtil.zcubatorImplemented()) {
			try {
				if (result != null) {
					result.close();
					result = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
			query = "SELECT ABBREVIATED_MODULE_NAME,ABBREVIATED_SUBMODULE_NAME,SUB_MODULE_URL FROM  MODULE_LIST ML,SUB_MODULE_LIST SML WHERE ML.MODULE_KEY=SML.MODULE_KEY AND SML.MODULE_KEY = 16";
			stmt = con.createStatement();
			result = stmt.executeQuery(query.toString());
			if (result.next()) {
				mapInfo.set("ABBR_MODULE_NAME", result.getString("ABBREVIATED_MODULE_NAME"));
				mapInfo.set("ABBR_SUBMODULE_NAME", result.getString("ABBREVIATED_SUBMODULE_NAME"));
				mapInfo.set("SUB_MENU_PRIVILEGE", "");
				mapInfo.set("MENU_PRIVILEGE", "");
				mapInfo.set("SUB_MODULE_URL", result.getString("SUB_MODULE_URL"));
			}
		}
	} catch (SQLException sqle) {

	} finally {
		try {
			if (result != null) {
				result.close();
				result = null;
			}
		} catch (Exception e) {
			logger.error("Exception ", e);
		}

		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e) {
			logger.error("Exception ", e);
		}

		try {
			if (con != null)
				DBConnectionManager.getInstance().freeConnection(con);
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	return mapInfo;
}
     

		/**
		 * This function return the theme Name basis on User No.
		 * when user is not configured then its return default theme name. 
		 * @author Rohit Jain
		 * @param userNo 
		 * @return String theme name
		 */
		
		 public static String getThemeName(String userNo){
			  String themeName=MultiTenancyUtil.getTenantConstants().THEME_NAME;
			  if(!StringUtil.isValid(themeName))
				  themeName="Blue";
			  try{
		   	
		       StringBuffer query=new StringBuffer("SELECT AT.THEME_NAME FROM APPLICATION_THEMES AT JOIN USERS_THEME UT ON UT.THEME_ID=AT.THEME_ID WHERE UT.USER_NO= "+userNo);
		       com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
		       if(result.next()){
		       	themeName=result.getString("THEME_NAME");
		       }
		       
		     }catch(Exception e){
		         e.printStackTrace();
		         themeName= "Blue";
		     }
		     return themeName;
		 }

   
      public static String getFimSellerName(String franNo, String scheduleID) {
    	  String sellerName = FieldNames.EMPTY_STRING;
    	  String sellerNameAdded = FieldNames.EMPTY_STRING;

    	  String selectQuery = "SELECT SELLER_NAMES, SELLER_NAMES_ADDED FROM FIM_UFOC_SEND_SCHEDULE WHERE FRANCHISEE_NO = " + franNo + " AND SCHEDULE_ID=" + scheduleID;
    	  try {
    		  com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(selectQuery, new Object[]{});
    		  if (rs.next()) {
    			  sellerName = rs.getString("SELLER_NAMES");
    			  sellerNameAdded = rs.getString("SELLER_NAMES_ADDED");
    		  }

    		  StringBuffer ownerNames = new StringBuffer();
    		  if(StringUtil.isValid(sellerName)) {
    			  ownerNames = CommonMgr.newInstance().getCommonFsDAO().getOwnerNames(sellerName.split(","));
    			  if(StringUtil.isValid(sellerNameAdded)) {
    				  sellerName = (ownerNames.toString()+";"+sellerNameAdded).replaceAll(";",", ");
    			  } else {
    				  sellerName = ownerNames.toString().replaceAll(";",", ");
    			  }
    		  } else if(StringUtil.isValid(sellerNameAdded)) { 
    			  sellerName = sellerNameAdded.replaceAll(";",", ");
    		  }
    	  } catch(Exception ex) {
    		  logger.error(ex);
    	  }

    	  return sellerName;
      }
      
      
      /**
       * Allow month combo with name specify, This will return combo with default selection 
       * @author abhishek gupta
       * @return
       */
      public static String getMonthComboFS(){
    	  return getMonthComboFS(-1, "birthMonth");
      }
      /**
       * Allow month combo with name specify, This will return combo with selected parameter 
       * @param month1
       * @param comboName
       * @return
       */
      public static String getMonthComboFS(int month1, String comboName){
    	  if(comboName == null || "".equals(comboName)) {
    		  comboName = "birthMonth";
    	  }
    	  String month = "";
    	  String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
         
    	  month += ("<select name ='"+comboName+"' id='"+comboName+"' class='multiList' >");
    
    	  if(month1==-1){
    		  month +="<option value ='-1' selected>"+LanguageUtil.getString("Select Month")+"";
    	  }else{
    		  month +="<option value ='-1' >"+LanguageUtil.getString("Select Month")+"";
    	  }
    	  
    	  for(int count = 0;count<12;count++){
    		  if(count < 10){
    			  if(count < 10){
    				  if(month1==count){
    					  month += ("<option value = " + count + " selected>" + monthNames[count]);
    				  }else{
    					  month += ("<option value = " + count + ">" + monthNames[count]);
    				  }    
    			  }
    			  else{
    				  month += ("<option value = " + count + ">" + monthNames[count]);
    			  }
    		  }else{
    			  if(count < 10){
    				  month += ("<option value = " + count + ">" + monthNames[count]);
    			  }
    			  else{
    				  if(month1==count){
    					  month += ("<option value = " + count + " selected>" + monthNames[count]);
    				  }else{
    					  month += ("<option value = " + count + ">" + monthNames[count]);
    				  }    
    			  }
    		  }   
    	  }

    	  month += "</select>";
    	  return month;
      }

      /**
       * Sourabh MultiSelect
       * @param month1
       * @param comboName
       * @param feature
       * @return
       */
      public static String getMultiSelectMonthComboFS(String month1, String comboName, String feature)
      {
    	  if(comboName == null || "".equals(comboName)) {
    		  comboName = "birthMonth";
    	  }
    	  
    	  boolean flag = false;
    	  String[] month1Values = null;
    	  StringBuilder month = new StringBuilder("");;
    	  String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
         
    	  month.append("<select name ='"+comboName+"' id='"+comboName+"' "+feature+" >");
    	  if(StringUtil.isValid(month1))
    	  {
    		  if(month1.indexOf(":")!=-1)
    		  {
    			  month1 = month1.replaceAll(":", ",");
    		  }
    		  if(month1.indexOf(",")!=-1)
    		  {
    			  flag = true;
    			  month1Values = month1.split(",");
    		  }
    	  }
    	  if("-1".equals(month1)){
    		  month.append("<option value ='-1' selected>"+LanguageUtil.getString("Select Month")+"");
    	  }else{
    		  month.append("<option value ='-1' >"+LanguageUtil.getString("Select Month")+"");
    	  }
    	  
    	  for(int count = 0;count<12;count++)
    	  {
    		  String tempSelected = "";
    		  month.append("<option value='");
    		  month.append(count);
    		  month.append("' ");
    		  if(flag)
    		  {
    			  if(month1Values != null && month1Values.length > 0)
    			  {
    				  for(int i = 0; i < month1Values.length; i++) {
    					  if(Integer.parseInt(month1Values[i].trim())==count){
    						  tempSelected = "selected";
    						  break;
    					  }
    				  }
    			  }
    			  month.append("\" "+tempSelected);
    			  month.append(">");
    		  }else
    		  {
    			  if(month1 !=null && Integer.parseInt(month1)==count){
    				  month.append("\" selected ");
    				  month.append(">");
    			  }
    			  else{
    				  month.append("\">");
    			  }
    		  }
    		  month.append(LanguageUtil.getString(monthNames[count]));
    		  month.append("</option>");
    	  }

    	  month.append("</select>");
    	  return month.toString();
      }
      
      /**
       * Sourabh MultiSelect
       * @param date1
       * @param month
       * @param colName
       * @param feature
       * @return
       */
      public static String getMultiSelectDateComboFS(String date1,String month,String colName, String feature)
      {
    	  if(colName == null) {
    		  colName = "birthDate";
    	  }
          StringBuilder date = new StringBuilder();
          boolean flag = false;
    	  String[] date1Values = null;
    	  
          date.append("<select name ='"+colName+"' "+feature+" id='"+colName+"'>");
          
          if(StringUtil.isValid(date1))
    	  {
    		  if(date1.indexOf(":")!=-1)
    		  {
    			  date1 = date1.replaceAll(":", ",");
    		  }
    		  if(date1.indexOf(",")!=-1)
    		  {
    			  flag = true;
    			  date1Values = date1.split(",");
    		  }
    	  }
          if("-1".equals(date1)){
        	  date.append("<option value ='-1' selected>"+LanguageUtil.getString("Select Date")+"");
          }else{
              date.append("<option value ='-1'>"+LanguageUtil.getString("Select Date")+"");
          }
          for(int count = 1;count<32;count++)
          {
        	  String tempSelected = "";
        	  date.append("<option value='");
        	  date.append(count);
        	  date.append("' ");
    		  if(flag)
    		  {
    			  if(date1Values != null && date1Values.length > 0)
    			  {
    				  for(int i = 0; i < date1Values.length; i++) {
    					  if(Integer.parseInt(date1Values[i].trim())==count){
    						  tempSelected = "selected";
    						  break;
    					  }
    				  }
    			  }
    			  date.append("\" "+tempSelected);
    			  date.append(">");
    		  }else
    		  {
    			  if(date1 !=null && Integer.parseInt(date1)==count){
    				  date.append("\" selected ");
    				  date.append(">");
    			  }
    			  else{
    				  date.append("\">");
    			  }
    		  }
    		  date.append(count);
    		  date.append("</option>");
          }
          date.append("</select>");
          return date.toString();
      }
      
      public static String getDateComboFS(){
    	  return getDateComboFS(-1,-1,null);
      }
      public static String getDateComboFS(int date1,int month,String colName){
    	  if(colName == null) {
    		  colName = "birthDate";
    	  }
          String date = "";
          int limit=32;
          if(month==1){
              limit=30;
          }
          if(month==3||month==5||month==8||month==10){
              limit=31;
          }
       
          date += ("<select name ='"+colName+"'  class='multiList' id='"+colName+"'>");
          
          if(date1==-1){
        	  date += ("<option value ='-1' selected>"+LanguageUtil.getString("Select Date")+"");
          }else{
               date += ("<option value ='-1'>"+LanguageUtil.getString("Select Date")+"");
          }
          for(int count = 1;count<limit;count++){
        	  if(date1 < 10){
        		  if(count < 10){
        			  if(date1==count){
        				  date += ("<option value = " + count + " selected>0" + count);
                      }else{
                          date += ("<option value = " + count + ">0" + count);
                      }    
        		  }
        		  else{
        			  date += ("<option value = " + count + ">" + count);
        		  }
        	  }else{
        		  if(count < 10){
        			  date += ("<option value = " + count + ">0" + count);
        		  }
        		  else{
        			  if(date1==count){
        				  date += ("<option value = " + count + " selected>" + count);
                      }else{
                          date += ("<option value = " + count + ">" + count);
                      }    
        		  }
        	  }   
          }
          date += "</select>";
	
          return date;
      }

      public static int[] getRGBValues(String themeName){
    	  int[] rgbArray = new int[3];
    	  String query = "SELECT RED,GREEN,BLUE FROM APPLICATION_THEMES WHERE THEME_NAME = ?";
    	  com.home.builderforms.sqlqueries.ResultSet resultSet = QueryUtil.getResult(query.toString(), new String[]{themeName});
    	  if(resultSet.next()) {
    		  rgbArray[0] = Integer.parseInt(resultSet.getString("RED"));
    		  rgbArray[1] = Integer.parseInt(resultSet.getString("GREEN"));
    		  rgbArray[2] = Integer.parseInt(resultSet.getString("BLUE"));
    	  }
    	  
    	  return rgbArray;
      }
    
      //P_FS_B_64844 added by vivek maurya starts
       public static String getDateDisplayComboForSearch(int date1,int month,String disabled,String fromWhere){
    	  return getDateDisplayComboForSearch(date1, month, disabled, fromWhere, null);  //Sourabh MultiSelect
       }
    	   public static String getDateDisplayComboForSearch(int date1,int month,String disabled,String fromWhere, String feature){ //Sourabh MultiSelect
       	
           String date = "";
           String cssClass = StringUtil.isValid(feature) ? feature : "class = 'multiList'";   //Sourabh MultiSelect
           int limit=32;
           if(month==1){
               limit=30;
           }
           if(month==3||month==5||month==8||month==10){
               limit=31;
           }
           if(disabled != null && !disabled.trim().equals("null") && !disabled.trim().equals(""))
           {   
        	   date += ("<select name ='birthDate' id='birthDate' "+cssClass+" disabled>");  //Sourabh MultiSelect
           }else if(fromWhere != null && !fromWhere.trim().equals("") && !fromWhere.trim().equalsIgnoreCase("null"))
           {
               date += ("<select name ='"+fromWhere+"birthDate'  "+cssClass+" id='"+fromWhere+"birthDate'>"); //Sourabh MultiSelect
           }else   
           {
               date += ("<select name ='birthDate'  "+cssClass+"'id='birthDate'>"); //Sourabh MultiSelect
           }
           if(date1==-1){
	     date += ("<option selected value =''>"+LanguageUtil.getString("Select Date")+"");
           }else{
                date += ("<option value =''>"+LanguageUtil.getString("Select Date")+"");
           }
	for(int count = 1;count<limit;count++){
               if(date1 < 10){
		if(count < 10){
	            if(date1==count){
			date += ("<option selected value = " + count + ">0" + count);
                       }else{
                           date += ("<option value = " + count + ">0" + count);
                       }    
		}
		else{
			date += ("<option value = " + count + ">" + count);
		}
               }else{
                   if(count < 10){
			date += ("<option value = " + count + ">0" + count);
		}
		else{
                       if(date1==count){
			date += ("<option selected value = " + count + ">" + count);
                       }else{
                           date += ("<option value = " + count + ">" + count);
                       }    
		}
               }   
	}
	date += "</select>";


	
	return date;

   }
       
       
       public static String getMonthDisplayComboInWordsForSearch(int month1,String disabled,String fromwhere){
    	   return getMonthDisplayComboInWordsForSearch(month1, disabled, fromwhere, null);  //Sourabh MultiSelect
       }
       public static String getMonthDisplayComboInWordsForSearch(int month1,String disabled,String fromwhere, String feature){  //Sourabh MultiSelect
   		
    	   String cssClass = StringUtil.isValid(feature) ? feature : "class = 'multiList'"; //Sourabh MultiSelect
           String month = "";
           String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
           //String str="this.options[this.selectedIndex].value";
           if(disabled != null && !disabled.trim().equals("") && !disabled.trim().equalsIgnoreCase("null") && disabled.equalsIgnoreCase("DISABLED"))
           {    
  	//month += ("<select name ='birthMonth' id='birthMonth' class='dropdown_list' disabled  onChange=javascript:getSeletedMonth(this.value)>");
        	   month += ("<select name ='birthMonth' id='birthMonth' "+cssClass+" disabled >");//Sourabh MultiSelect
           }
           else if(fromwhere != null && !fromwhere.trim().equals("") && !fromwhere.trim().equalsIgnoreCase("null"))
           {
           // month += ("<select name ='"+fromwhere+"birthMonth' id ='"+fromwhere+"birthMonth' class='dropdown_list' onChange=javascript:getSeletedMonth(this.value)>");      
        	   month += ("<select name ='"+fromwhere+"birthMonth' id ="+fromwhere+"birthMonth "+cssClass+" >");//Sourabh MultiSelect
           }
           else{   
          //  month += ("<select name ='birthMonth' id='birthMonth' class='dropdown_list' onChange=javascript:getSeletedMonth(this.value)>");   
        	   month += ("<select name ='birthMonth' id='birthMonth' "+cssClass+" >");//Sourabh MultiSelect
           }
           if(month1==-1){
                   month +="<option value ='' selected>"+LanguageUtil.getString("Select Month")+"";
           }else{
                   month +="<option value ='' >"+LanguageUtil.getString("Select Month")+"";
           }
           
  	for(int count = 0;count<12;count++){
               
               if(count < 10){
  		if(count < 10){
  	            if(month1==count){
  			month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                       }else{
                           month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                       }    
  		}
  		else{
  			month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
  		}
               }else{
                   if(count < 10){
  			month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
  		}
  		else{
                       if(month1==count){
  			month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                       }else{
                           month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
                       }    
  		}
               }   
  	}

           month += "</select>";
  	
  	return month;

   }
       //P_FS_B_64844 added by vivek maurya ends
      
       
       //P_B_FRANO_74587 modified by neeti starts
      
         /**
        * P_FRANBUZZ_B_74587
        * Created By Prakash Jodha
        * @desc This mthod is to breake text string without anchore tag
        * @param text-text to be formatted
        * @param noOfChar-character per line
        * @return formatted text
        */
        public static String getFormattedLongText(String text,int noOfChar,String status){ //P_B_HelpDesk_fetch-20111216-023 filterValue method used in this method
          String formattedText="";
          text = text.replaceAll("&#8203;","");//caribo-20140328-030
          int scrapSize = text.length();
          //int rows;
          //int textLimit=0;
          //caribo-20140328-030 starts
          int startingindex=0;
          int endIndex=0;
          if(scrapSize>noOfChar)
          {
        	  while(endIndex<scrapSize)
        	  {
        		  if(StringUtil.isValid(formattedText))
        		  {
        			  formattedText = formattedText+"&#8203;";
        		  }
                  	endIndex =text.indexOf(" ",noOfChar+endIndex);
                  	if(endIndex==-1){
                  		endIndex=scrapSize;
                  	}
                  	formattedText = formattedText+filterValue(text.substring(startingindex,endIndex));
                  	startingindex=endIndex;
        	  }
           }
           else
           {
        	 formattedText=filterValue(text);
           }////caribo-20140328-030 ends
            if(StringUtil.isValid(formattedText))
            {
              	//P_B_HelpDesk_fetch-20111216-023
                formattedText = formattedText.replaceAll("&lt;br&gt;", "<br>");//P_B_HelpDesk_fetch-20111216-023 ends     
                formattedText = formattedText.replaceAll("&lt;&#47;a&gt;", "</a>");//P_B_HelpDesk_fetch-20111216-023 ends
            }
              
                return formattedText;
              //caribo-20140328-030 ends
          
                
          	
          	
          }
        
          /**
        * P_FRANBUZZ_B_74587
        * Created By Prakash Jodha
        * @desc This mthod is the modified Version of getFormattedText to handle anchore tag within given text, this method will breake text using <wbr> according to the noOfChar provided to the method 
        * @param text-text to be formatted
        * @param noOfChar-character per line
        * @return formatted text
        */
       
       public static String getFormattedText(String text,int noOfChar,String status){
          String formattedText="";
          
          //int scrapSize = text.length();
       //check wheather given text have anchor tag or not
        if(text.contains("<a") && text.indexOf("</a>")!=-1){//SMC-20131218-306
                boolean flag=true;
                 while(flag){
                String newformatText="";
                String tempformatText="";
                String newtempText="";
                String tempText="";//SMC-20131218-306
                
                try{
                		//SMC-20131218-306 starts
            			tempText = text.substring(0,text.indexOf("</a>"));
            			//newformatText=text.substring(0, text.indexOf("<a"));
            			
            			//NurseNextDoor Starts
            			/*newformatText=text.substring(0, tempText.lastIndexOf("<a"));//SMC-20131218-306 ends
                	    if(newformatText.length()>0)
                        newformatText=getFormattedLongText(newformatText,noOfChar,status);
                        //tempformatText=text.substring(text.indexOf("<a"),text.indexOf("</a>")+4);//SMC-20131218-306
                        tempformatText=text.substring(tempText.lastIndexOf("<a"),text.indexOf("</a>")+4);//SMC-20131218-306
                        newtempText=tempformatText.substring(tempformatText.indexOf("<a"), tempformatText.indexOf("\">")+2);
                        newformatText=newformatText+newtempText;
                        newtempText=tempformatText.substring(tempformatText.indexOf(">")+1, tempformatText.indexOf("</a>")+4);
                        newtempText=getFormattedLongText(newtempText,noOfChar,status);
                        newformatText=newformatText+newtempText;
                        formattedText=formattedText+newformatText;
                        text=text.substring(text.indexOf("</a>")+4);*/
            			
            			if(tempText.indexOf("<a")!=-1){
	            			newformatText=text.substring(0, tempText.lastIndexOf("<a"));//SMC-20131218-306 ends
	                	    if(newformatText.length()>0)
	                        newformatText=getFormattedLongText(newformatText,noOfChar,status);
	                        //tempformatText=text.substring(text.indexOf("<a"),text.indexOf("</a>")+4);//SMC-20131218-306
	                        tempformatText=text.substring(tempText.lastIndexOf("<a"),text.indexOf("</a>")+4);//SMC-20131218-306
	                        newtempText=tempformatText.substring(tempformatText.indexOf("<a"), tempformatText.indexOf("\">")+2);
	                        newformatText=newformatText+newtempText;
	                        newtempText=tempformatText.substring(tempformatText.indexOf(">")+1, tempformatText.indexOf("</a>")+4);
	                        newtempText=getFormattedLongText(newtempText,noOfChar,status);
	                        newformatText=newformatText+newtempText;
            			}else{
            				newformatText=tempText;
            			}
            			formattedText=formattedText+newformatText;
            			text=text.substring(text.indexOf("</a>")+4);
            			//NurseNextDoor Ends
            			
                        if(text.contains("<a") && text.indexOf("</a>")!=-1){//SMC-20131218-306
                                flag=true;
                        }else{
                            formattedText=formattedText+getFormattedLongText(text,noOfChar,status);
                            flag=false;
                        }
                }catch(Exception e){
                             e.printStackTrace();

                }

              }
        }else{                     //if no anchor tag than no anchor tag manipulation required
            formattedText= getFormattedLongText(text,noOfChar,status);
        }
           
          	return formattedText;
          }

      //P_B_FRANO_74587 modified by neeti ends
       
        
             /**
         	 * This method is used to change the format of a given date from
         	 * YYYY-MM-DD(eg.2001-12-25) to (MM DD, YY) format.(eg.Dec 25,2001)
         	 * 
         	 * @param -String
         	 *            inputDate (YYYY-MM-DD) as an Input Parameter.
         	 * @return (MM DD, YY) format.
         	 */
         	public static String changeDateFormatToGlobal(String inputDate) {
         		try {

         			String months[] = { "Jan", "Feb", "March", "April", "May", "June",
         					"July", "Aug", "Sep", "Oct", "Nov", "Dec" };
         			StringTokenizer strTokens = new StringTokenizer(inputDate, "/");
         			String year = "";
         			String month = "";
         			String day = "";
         			while (strTokens.hasMoreTokens()) {
         				month = strTokens.nextToken();
         				day = strTokens.nextToken();
         				year = strTokens.nextToken();
         			}
         			month = months[Integer.parseInt(month) - 1];

         			return (month + " " + day + ", " + year);
         		} catch (Exception e) {
         			Debug.print(e);
         		}
         		return "";

         	}      
         	
                	
         //Canteen-20110810-533 Start
  	/**
  	 * This method returns address heading
  	 * 
  	 * @param fimTtAddressHeading
  	 *            Input String having Tab primary Id of the Address heading
  	 * @return String having the fimTtAddressHeading
  	 * 
  	 * @throws ConnectionException
  	 */
      
      public static String getAddressHeading(String fimTtAddressHeading)
      {
          //Info info = new Info();
          String addressHeadingName="";
          String selectQuery = "SELECT ADDRESS_HEADING_NAME  FROM ADDRESS_STATUS WHERE ADDRESS_HEADING_ID = "+fimTtAddressHeading;
          try{
          com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(selectQuery, new Object[]{});
         
          if (rs.next()) {
              
        	  addressHeadingName = rs.getString("ADDRESS_HEADING_NAME");
          }
          }catch(Exception ex){
              logger.error(ex);
          }
          return addressHeadingName;
      }
  	//Canteen-20110810-533 End
  
       public static String getEmail(String foreignName, String order, String foreignId) throws ConnectionException {

           String value = "";
           Connection con = null;
           PreparedStatement pstmt = null;
           java.sql.ResultSet rs = null;
           String eMail = "";



           try {
               con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

               pstmt = con.prepareStatement("SELECT EMAIL_IDS FROM ADDRESS WHERE FOREIGN_TYPE=? AND ADDRESS_ORDER=? AND FOREIGN_ID=?");

               // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE master_data_id=?");
               pstmt.setString(1, foreignName.replaceAll("Email", ""));
               pstmt.setString(2, order);
               pstmt.setString(3, foreignId);
               //System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

               rs = pstmt.executeQuery();


               if (rs.next()) {
                   if (rs.getString("EMAIL_IDS") != null && !rs.getString("EMAIL_IDS").equals("null") && !rs.getString("EMAIL_IDS").equals("")) {
                       eMail = rs.getString("EMAIL_IDS");
                   }
                   if (!eMail.equals("")) {
                       value = value + eMail ;
                   }
               }
               try {
                   rs.close();
               } catch (Exception e) {
               }
               try {
                   pstmt.close();
                   pstmt = null;
               } catch (Exception e) {
                   System.out.println("The exception is " + e);
               }



           } catch (SQLException e) {
               System.out.println("The sqlexception is " + e);
               Debug.print(e);
           } catch (Exception e) {
               System.out.println("The exception is " + e);
               e.printStackTrace();
           } finally {
               try {
                   if (rs != null) {
                       rs.close();
                   }
               } catch (Exception e) {
                   System.out.println("The final1 exception is " + e);
               }
               try {
                   if (pstmt != null) {
                       pstmt.close();
                   }
               } catch (Exception e) {
                   System.out.println("The fianl2 exception is " + e);
               }
               DBConnectionManager.getInstance().freeConnection(con);
           }
           return value;
       }
       //Captivate_Feature end
         
//CAPTIVATE_INTEGRATION  Added By Narotam Singh ends
       public static String getMonthDisplayComboInWordsForSpouse(int month1,String disabled,String fromwhere){

    	   String month = "";
    	   String monthNames[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    	   if(disabled != null && !disabled.trim().equals("") && !disabled.trim().equalsIgnoreCase("null") && disabled.equalsIgnoreCase("DISABLED"))
    	   {    
    		   month += ("<select name ='"+fromwhere+"spouseBirthMonth' id='"+fromwhere+"spouseBirthMonth' class='dropdown_list' disabled  onChange=javascript:getSpouseMonth()>");
    	   }
    	   else{   
    		   month += ("<select name ='"+fromwhere+"spouseBirthMonth' id='"+fromwhere+"spouseBirthMonth' class='dropdown_list' onChange=javascript:getSpouseMonth()>");   
    	   }
    	   if(month1==-1){
    		   month +="<option value ='-1' selected>"+LanguageUtil.getString("Select Month")+"";
    	   }else{
    		   month +="<option value ='-1' >"+LanguageUtil.getString("Select Month")+"";
    	   }

    	   for(int count = 0;count<12;count++){

    		   if(count < 10){
    			   if(count < 10){
    				   if(month1==count){
    					   month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    				   }else{
    					   month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    				   }    
    			   }
    			   else{
    				   month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    			   }
    		   }else{
    			   if(count < 10){
    				   month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    			   }
    			   else{
    				   if(month1==count){
    					   month += ("<option selected value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    				   }else{
    					   month += ("<option value = " + count + ">" + LanguageUtil.getString(monthNames[count]));
    				   }    
    			   }
    		   }   
    	   }

    	   month += "</select>";

    	   return month;

       }
 public static String getDateDisplayComboForSpouse(int date1,int month)
       {
          return getDateDisplayCombo(date1,month,null);
       }
 public static String getDateDisplayComboForSpouse(int date1,int month,String disabled)
 {
    return getDateDisplayComboForSpouse(date1,month,disabled,null);
 }
 public static String getDateDisplayComboForSpouse(int date1,int month,String disabled,String fromWhere){

     String date = "";
     int limit=32;
     if(month==1){
         limit=30;
     }
     if(month==3||month==5||month==8||month==10){
         limit=31;
     }
     if(disabled != null && !disabled.trim().equals("null") && !disabled.trim().equals(""))
     {   
        date += ("<select name ='"+fromWhere+"spouseBirthDate' id='"+fromWhere+"spouseBirthDate' class='dropdown_list' disabled>");
     }/*else if(fromWhere != null && !fromWhere.trim().equals("") && !fromWhere.trim().equalsIgnoreCase("null"))
     {
         date += ("<select name ='"+fromWhere+"spouseBirthDate'  class='dropdown_list' ='"+fromWhere+"spouseBirthDate'>");
     }*/else   
     {
         date += ("<select name ='"+fromWhere+"spouseBirthDate'  class='dropdown_list' id='"+fromWhere+"spouseBirthDate'>");
     }
     if(date1==-1){
             date += ("<option selected value ='-1'>"+LanguageUtil.getString("Select Date")+"");
     }else{
          date += ("<option value ='-1'>"+LanguageUtil.getString("Select Date")+"");
     }
        for(int count = 1;count<limit;count++){
         if(date1 < 10){
                if(count < 10){
                    if(date1==count){
                        date += ("<option selected value = " + count + ">0" + count);
                 }else{
                     date += ("<option value = " + count + ">0" + count);
                 }    
                }
                else{
                        date += ("<option value = " + count + ">" + count);
                }
         }else{
             if(count < 10){
                        date += ("<option value = " + count + ">0" + count);
                }
                else{
                 if(date1==count){
                        date += ("<option selected value = " + count + ">" + count);
                 }else{
                     date += ("<option value = " + count + ">" + count);
                 }    
                }
         }   
        }
        date += "</select>";



        return date;

 }
//CAPTIVATE_42_POINTS ends
    
  //P_FIN_BUG_6317 starts
	/*
	 * This method returns the value of zeroes tobe shown after decimal.
	 */
	public static String getMaxRoundDigits()
	{
		String maxRound = MultiTenancyUtil.getTenantConstants().MAX_ROUNDED_DIGITS; 
		String zeroRoundValue = "0.00";
		if("1".equals(maxRound))
		{
			zeroRoundValue="0.0";
		}
		else if("3".equals(maxRound))
		{
			zeroRoundValue="0.000";
		}
		else if("4".equals(maxRound))
		{
			zeroRoundValue="0.0000";
		}
		else{
			zeroRoundValue = "0.00";
		}
		return zeroRoundValue;
    }
	
//P_FIN_BUG_6317 ends
	
	/*
	 * @author Banti Prajapti 
	 */	
	public static boolean isOwner(String useridentityNo){
		return "Owner".equalsIgnoreCase(SQLUtil.getColumnValue("FRANCHISEE_USERS" , "USER_TYPE", "FRANCHISEE_USER_NO",useridentityNo))?true:false;   	
	}
	
	public static String getFranchiseeNoByTheaterId(String TheaterId){
		String franNo ="";
		System.out.println("getFranchiseeNoByTheaterId>>>>>>>>>>>>"+TheaterId);
		if(!StringUtil.isValid(TheaterId)){
			TheaterId="";
		}
		String query = "SELECT FRANCHISEE_NO FROM THEATERS WHERE THEATER_ID= "+TheaterId;
		System.out.println("\n\n\ngetFranchiseeNoByTheaterId>>>>>"+query);
		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query, new Object[] { });
		
		if (result != null && result.next()) {
			franNo =result.getString("FRANCHISEE_NO");
			
		}
      return franNo;
	}
	
	public static String getCreditCardPaymentGatewayStatus()
	 {

		String CONFIGURATION_VALUE = "";
		
		
		try {
		
		String query = "SELECT CONFIGURATION_VALUE from ADMIN_CONFIGURATION_TABLE WHERE MODULE_NAME='tms' AND SUB_MODULE_NAME='payment'";
		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query, new Object[] { });
			
		
			while (result.next()) {
				
				CONFIGURATION_VALUE =  result.getString("CONFIGURATION_VALUE");
			}
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return CONFIGURATION_VALUE; 


	 }
	
//P_E_OxiFre-20110915-043     
public static String getMarchantTransactionKeyId(String franchiseeNo){
	String transactionKey = null;
	String query = null;
	String query1 = null;
	com.home.builderforms.sqlqueries.ResultSet result = null;
	com.home.builderforms.sqlqueries.ResultSet result1 = null;
	 String serviceName = ServiceConfigurator.getServiceName();
     boolean isauthNet=false;
 	if(StringUtil.isValid(serviceName)&&serviceName.equals("AUTHORIZENET"))
		{
			isauthNet=true;
		}
 	query = " SELECT PASSWORD FROM CT_MARCHANT_CONFIGURATION WHERE FRANCHISEE_NO='" + franchiseeNo + "'";
	try
	{
		result = QueryUtil.getResult(query, null);
		if(result.next()){
			transactionKey = result.getString("PASSWORD");
		}	
		if(!StringUtil.isValidNew(transactionKey)&& !"".equals(transactionKey))
		{
			//TODO If franchise does ot have his own key than it can use key of francconnect adminonly
			query1 = " SELECT PASSWORD FROM CT_MARCHANT_CONFIGURATION WHERE FRANCHISEE_NO='1'";
			
			result1 = QueryUtil.getResult(query1, null);
			if(result1.next()){
				transactionKey = result1.getString("PASSWORD");
			}
			
		}
	}catch(Exception e){
	logger.info(e);
	}
	return transactionKey;
}

/*
 * P_E_OxiFre-20110915-043 ADDED BY YASHU TYAGI
 * use above function to get billto shipto of contact
 */
public static Info getBillingAddressInfo(String contactID)//For the case if table SERVICE314_TASKS does not exist self called from above method
{
	
	Info ContactAddressDetailsInfo = new Info();
	 
	 String query1 = "";
	 String query2 = "";
	 com.home.builderforms.sqlqueries.ResultSet result1 = null;
	 com.home.builderforms.sqlqueries.ResultSet result2 = null;
	 try {
		 query1="SELECT * FROM ADDRESS WHERE FOREIGN_ID="+contactID+"AND FOREIGN_TYPE='cmContactDetails'"+contactID;
		 result1 = QueryUtil.getResult(query1, null);
		 while(result1!=null && result1.next())
		 {
			 ContactAddressDetailsInfo.set(FieldNames.BILLTO_ADDRESS,result1.getString("ADDRESS"));
			 ContactAddressDetailsInfo.set(FieldNames.BILLTO_CITY, result1.getString("CITY"));
			 ContactAddressDetailsInfo.set(FieldNames.BILLTO_STATE, RegionMgr.newInstance().getRegionsDAO().getStateName(result1.getString("STATE")));
			 ContactAddressDetailsInfo.set(FieldNames.BILLTO_ZIP, result1.getString("ZIPCODE"));
			 ContactAddressDetailsInfo.set(FieldNames.BILLTO_COUNTRY, RegionMgr.newInstance().getCountriesDAO().getCountryName(result1.getString("COUNTRY")));
			 ContactAddressDetailsInfo.set(FieldNames.PHONE_NUMBERS, formatPhoneNo(result1.getString("PHONE_NUMBERS")));
		 }
		query2="SELECT * FROM ADDRESS WHERE FOREIGN_ID="+contactID+"AND FOREIGN_TYPE='cmAddressInfo'"+contactID;
		result2=QueryUtil.getResult(query1, null);
		while(result2!=null && result2.next())
		 {
			 ContactAddressDetailsInfo.set(FieldNames.SHIPTO_ADDRESS, result2.getString("ADDRESS"));
			 ContactAddressDetailsInfo.set(FieldNames.SHIPTO_CITY, result2.getString("CITY"));
			 ContactAddressDetailsInfo.set(FieldNames.SHIPTO_STATE, RegionMgr.newInstance().getRegionsDAO().getStateName(result2.getString("STATE")));
			 ContactAddressDetailsInfo.set(FieldNames.SHIPTO_ZIP, result2.getString("ZIPCODE"));
			 ContactAddressDetailsInfo.set(FieldNames.SHIPTO_COUNTRY2,RegionMgr.newInstance().getCountriesDAO().getCountryName(result2.getString("COUNTRY")));
			 ContactAddressDetailsInfo.set(FieldNames.MOBILE_NUMBERS, formatPhoneNo(result2.getString("PHONE_NUMBERS")));
		 }
	
	 
	 }
	 
	 catch (Exception ex){
			ex.printStackTrace();
	}

return ContactAddressDetailsInfo;
}

public static boolean checkArray(String[] argArray) {
	boolean check = false;
	if(argArray!=null && argArray.length>0) {
		check = true;
	}
	return check;
}




/**
 * P_E_JSpellSupplementalDictionary
 * @author Vivek Maurya
 * @date 11Jun2013
 * @return
 */
public static String getMenuBar(int count, HttpServletRequest request) {
	StringBuffer menuBar = new StringBuffer("<div id=\"menuBar\"><layer ID=\"Actions_dynamicmenu").append(count).append("Bar\">");
    menuBar.append(" <a href=\"javascript:void(0);\" onMouseOver=\"openPulldownMenu(event,'Actions_dynamicmenu");
    menuBar.append(count).append("');\" onMouseOut=\"closePulldownMenu('Actions_dynamicmenu").append(count).append( "');\">");
    menuBar.append(" <img src=\"").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/edit.gif\" border=\"0\"></a></layer></div>");
    
    return menuBar.toString();
}

public static String convertTime(String dateTime) {
	if(StringUtil.isValid(dateTime)) {
		String sTime = dateTime.substring(0,2);
		String sMinute = dateTime.substring(3,5);
							
		if (sTime != null)
			sTime = sTime.trim();
	
		if (sMinute != null)
			sMinute = sMinute.trim();
	
		int scheduledTimeInt = Integer.parseInt(sTime);	
		int scheduledMinuteInt = Integer.parseInt(sMinute);
		int apmInt = 0;
	
		if(scheduledTimeInt > 12) {
			scheduledTimeInt=scheduledTimeInt-12;
			apmInt=1;
		}
	
		if(scheduledTimeInt == 12) {
			apmInt=1;
		}
	
		StringBuffer displayTime = new StringBuffer();
	
		if(scheduledTimeInt < 10) {
			displayTime.append(FieldNames.ZERO);
			displayTime.append(scheduledTimeInt);
		}else
			displayTime.append(scheduledTimeInt);
	
		displayTime.append(":");
	
		if(scheduledMinuteInt < 10) {
			displayTime.append(FieldNames.ZERO);
			displayTime.append(scheduledMinuteInt);
		}else
			displayTime.append(scheduledMinuteInt);
	
		displayTime.append(" ");
	
		if(apmInt == 0) {
			displayTime.append("AM");
		}else
			displayTime.append("PM");
			
		return displayTime.toString();
	}
	
	return FieldNames.EMPTY_STRING;
}
//starts by rohit jain BBEH_INTRANET_SMC_OPTIMIZATION
public static String getComboInfo(SequenceMap map,String selected) {
    StringBuffer combo = new StringBuffer();
    String checked;
    String key = "";
    String value = "";
    //int status=0;	
    ArrayList it = (ArrayList)map.keys();

    for(int p=0;p<it.size();p++) {
    	key = (String)it.get(p);
    	value = (String)map.get(key);
        checked = "";
    
        	if(com.home.builderforms.StringUtil.isValidNew(selected) && selected.equals(key)){
        		checked = "selected";
        	}
        	combo.append("<option value ='");
        	combo.append(key).append("' "+checked+">").append(value);
     
    }
    
     
    return combo.toString();

}
/**
*P_CM_B_PhnFormat :starts
* @param phoneNo
* @author Sonalika
* @Description : This method returns plain unformatted value of phone no which is in US format
*/
public static String reversePhoneFormat(String phoneNo)
{
    if(StringUtil.isValidNew(phoneNo)){
        phoneNo = phoneNo.replaceAll(" ","").replace('(','-').replace(')','-').replaceAll("-", "");
    }
    return phoneNo;
}


/*P_CM_B_PhnFormat : ends*/
 //ends by rohit jain BBEH_INTRANET_SMC_OPTIMIZATION

//P_CM_Enh_BuilderForm starts
public static Info getIdValueInfo(String table, String idField, String valueField)
{
	Info returnInfo = new Info();
	try 
	{
    	String query = "SELECT "+idField+ " , "+valueField+" FROM "+table+" ORDER BY "+valueField;
    	com.home.builderforms.sqlqueries.ResultSet  resultSet = QueryUtil.getResult(query.toString(), new Object[]{});
  	  	while(resultSet.next()) 
  	  	{
  	  		returnInfo.set(resultSet.getString(idField), LanguageUtil.getString(resultSet.getString(valueField)));
  	  	}
	} catch (Exception e) 
	{
		logger.error("Exception in getIdValueInfo::"+e);
	}
	return returnInfo;
}
//KSZCB-20150317-003 Start
public static Info getIdValueInfo(String table, String idField, String valueField, String orderByColumn, String orderByValue,String orderByValue1, String orderBy1, String orderBy2)
{
	Info returnInfo = new Info();
	try 
	{
    	//String query = "SELECT "+idField+ " , "+valueField+" FROM "+table+" ORDER BY IF("+orderBy1;
    	
    	StringBuffer query =new StringBuffer();
    	query.append("SELECT ").append(idField).append(" , ").append(valueField).append(" FROM ").append(table).append(" ORDER BY CASE ").append(orderByColumn).append(" WHEN '").append(orderByValue).append("' THEN ").append(orderBy1).append(" END, CASE ").append(orderByColumn).append(" WHEN '").append(orderByValue1).append("' THEN ").append(orderBy2).append(" END");
    	com.home.builderforms.sqlqueries.ResultSet  resultSet = QueryUtil.getResult(query.toString(), new Object[]{});
  	  	while(resultSet.next()) 
  	  	{
  	  		returnInfo.set(resultSet.getString(idField), LanguageUtil.getString(resultSet.getString(valueField)));
  	  	}
	} catch (Exception e) 
	{
		logger.error("Exception in getIdValueInfo::"+e);
	}
	return returnInfo;
}
//KSZCB-20150317-003 End


//P_CM_Enh_BuilderForm ends

/**
 * P_E_Fields-20130905-035
 * Will return if a table has address as its dependent in header-map node
 * @param tableName
 * @param request
 * @return
 */
public static boolean hasDependentAddressTable(String tableName,HttpServletRequest request){
	try{
		String menuName = null;
		if(request!=null){
			menuName = request.getParameter("reportMenu")!=null?request.getParameter("reportMenu"):(String)request.getSession().getAttribute("menuName");
		}
		if(MODULE_NAME.NAME_FIM.equals(menuName)){
			FieldMappings mappings = DBUtil.getInstance().getFieldMappings(tableName);
			HeaderMap[] hMapArray = mappings.getHeaderMap();
			DependentTable dTable = null;
			for(HeaderMap headerMap: hMapArray) {
				HeaderField hFld = headerMap.getHeaderFields();
				DependentTable[] dependenTables = hFld.getDependentTables();
				if(dependenTables!=null && dependenTables.length>0){
					int dependantTableSize = dependenTables.length;
					for(int i=0;i<dependantTableSize;i++){
						dTable =  dependenTables[i];
						if(TableAnchors.ADDRESS.equals(dTable.getTableAnchor())){
							return Boolean.TRUE;
						}
					}
				}
			}
		}
		return Boolean.FALSE;
	}catch(Exception ex){
		ex.printStackTrace();
		return Boolean.FALSE;
	}
	
}
/**
 * P_E_Fields-20130905-035
 * Used to get table export name for dependent tables.
 * @param dTableAnchor anchor of table
 * @param headerValue headerValue already present for section names
 * @return
 */
public static String getHeaderValue(String dTableAnchor,String headerValue) {
	String dTableDisplayName = FieldNames.EMPTY_STRING;
	try{
		FieldMappings mappings = DBUtil.getInstance().getFieldMappings(dTableAnchor);
		if(mappings.getTableExportDisplayName() != null) {
			dTableDisplayName =mappings.getTableExportDisplayName(); 
		} else {
			dTableDisplayName =mappings.getTableDisplayName();
		}
		/*if(StringUtil.isValid(headerValue) && headerValue.trim().lastIndexOf(" ")!=-1 && !headerValue.contains(dTableDisplayName)){ //NCafe-20160919-1291 starts
			headerValue = headerValue.trim().substring(0, headerValue.lastIndexOf(" ")+1)+dTableDisplayName+" "+headerValue.substring(headerValue.lastIndexOf(" ")+1, headerValue.length());
		}*/ 
		if(StringUtil.isValid(headerValue) && !headerValue.contains(dTableDisplayName) && StringUtil.isValid(dTableDisplayName)){
			//headerValue = headerValue.trim().substring(0, headerValue.lastIndexOf(" ")+1)+dTableDisplayName+" "+headerValue.substring(headerValue.lastIndexOf(" ")+1, headerValue.length());
			headerValue=headerValue+" ("+dTableDisplayName+")"; //NCafe-20160919-1291  ends
		}
	}catch(Exception ex){
		logger.error("Error while generating display name",ex);
	}
	return headerValue;
}



/**
*P_TR_ENH_AmazonSkyConfig
* @param str
* @author Priyanka jain
* @Description : This method returns String after checking its validity otherwise return EmtyString
*/
private static String isValid(String str){
	
	return StringUtil.isValid(str)?str:"";
} 

/**
*P_TR_ENH_AmazonSkyConfig
*P_INT_ENH_AmazonSkyConfig
*P_AB_ENH_31122014 
* @param modulename
* @author Priyanka jain
* @Description : This method returns configured server information
*/

public static Map<String,String> getServerDetails(String module) {
	
	String[] columns = {"SERVER_ID","FTP_HOST_NAME","FTP_HOST_URL","FTP_USER_NAME","FTP_PASSWORD","AMAZON_USER_NAME","AMAZON_PASSWORD",
			"CONFIGURED","ACCESS_KEY","SECRET_KEY","SKY_USER_NAME","SKY_PASSWORD","SKY_ACCESS_KEY","SKY_REFRESH_KEY","SKY_CLIENT_ID","SKY_CLIENT_SECRET","CONNECTION_TYPE","DIRECTORY_PATH","FTP_ENABLE",
			"GOOGLE_USER_NAME","GOOGLE_PASSWORD","GOOGLE_ACCESS_TOKEN","GOOGLE_REFRESH_TOKEN","GOOGLE_CLIENT_ID","GOOGLE_CLIENT_SECRET","SFTP_HOST_NAME","SFTP_HOST_URL","SFTP_USER_NAME","SFTP_PASSWORD"};  //P_AB_ENH_AmazonSkyConfig  // P_AB_ENH_31122014 
	
	String[] infoKeys = {FieldNames.SERVER_ID,FieldNames.FTP_HOST_NAME,FieldNames.FTP_HOST_URL,FieldNames.FTP_USER_NAME,FieldNames.FTP_PASSWORD,
			FieldNames.AMAZON_USER_NAME,FieldNames.AMAZON_PASSWORD,FieldNames.CONFIGURED,FieldNames.ACCESS_KEY,FieldNames.SECRET_KEY,FieldNames.SKY_USER_NAME,
			FieldNames.SKY_PASSWORD,FieldNames.SKY_ACCESS_KEY,FieldNames.SKY_REFRESH_KEY,FieldNames.SKY_CLIENT_ID,FieldNames.SKY_CLIENT_SECRET,FieldNames.CONNECTION_TYPE,FieldNames.DIRECTORY_PATH,FieldNames.FTP_ENABLE,
			FieldNames.GOOGLE_USER_NAME,FieldNames.GOOGLE_PASSWORD,FieldNames.GOOGLE_ACCESS_TOKEN,FieldNames.GOOGLE_REFRESH_TOKEN,FieldNames.GOOGLE_CLIENT_ID,FieldNames.GOOGLE_CLIENT_SECRET,FieldNames.SFTP_HOST_NAME,FieldNames.SFTP_HOST_URL,FieldNames.SFTP_USER_NAME,FieldNames.SFTP_PASSWORD}; //P_AB_ENH_AmazonSkyConfig  // P_AB_ENH_31122014 
	
	Map<String, String> serverMap = SQLUtil.getSingleRowMap("SERVER_CONFIGURATION", columns, infoKeys, "MODULE", module, false);
	if(serverMap!=null && serverMap.isEmpty()) {
		for(String key : infoKeys) {
			if(FieldNames.CONFIGURED.equals(key)) {
				serverMap.put(key, "L");
			} else {
				serverMap.put(key, FieldNames.EMPTY_STRING);	
			}
		}
	}

	return serverMap;
}

	      //caribo-20140328-030 starts
	      //Juice-20131211-016 : starts
//P_E_ENCODING starts
	        public static String getOriginalSpecialChar(String str){
	        	return str;
	        }//P_E_ENCODING ends
	        //Juice-20131211-016 : starts
	      //caribo-20140328-030 ends
	        
	     // START : P_OPT_CM_REPORTS : 12/03/2014 : Chetan Rawat
	        public static String getMaxAndMinContactAddDate() {
	     	    String maxDate = "";
	     	    String minDate = "";
	     	    
	     	    StringBuffer sbQuery = new StringBuffer("SELECT MIN(DATE(CONTACT_ADD_DATE)) AS MIN_DATE,MAX(DATE(CONTACT_ADD_DATE)) AS MAX_DATE FROM CM_CONTACT_DETAILS");
	     	   
	     	    com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(sbQuery.toString(), new Object[] { });
	     	    try 
	     		{
	     	    if(rs!=null){
	     	    while (rs.next()) {
	     	    	minDate =    NewPortalUtils.getFormattedDate(rs.getString("MIN_DATE"));
	     	    	maxDate =    NewPortalUtils.getFormattedDate(rs.getString("MAX_DATE"));
	     	    }
	     	    	}
	     		}catch(Exception e)
	     		{
	     			logger.error("error in getMaxOrMinContactAddDate map"+e.getMessage());
	     		}
	     	    return minDate+"##"+maxDate;

	     	}
	      //DB_CACHE_OPTIMIZATION_ENDS

	        /**
	    	 * P_FS_B_29530<br/>
	    	 * Method to add line breaks in the passed text.
	    	 * @author Vivek Maurya
	    	 * @date 22Nov2013
	    	 * @return
	    	 */
	    	public static String breakText(String text, int maxLength) {
	        	String formattedText = FieldNames.EMPTY_STRING;
	        	
	        	if(StringUtil.isValid(text)) {
	        		try {
	        			int actualLen = text.length();
	        			while(actualLen > maxLength) {
	        				BreakIterator bi = BreakIterator.getWordInstance();
	                        bi.setText(text);

	                        int temp = 0;
	                        if(bi.isBoundary(maxLength-1)) {
	                        	temp = maxLength-1;
	                        } else {
	                            int preceding = bi.preceding(maxLength-1);
	                            temp = preceding-1;
	                        }
	                        
	                        if(temp != -1) {
	                        	//FORM_GENERATOR_ISSUE_FOR_TEXTAREA starts
	                        	String tempString ="";
	                        	if(text.charAt(temp) == ' ') {
	                        		tempString = text.substring(0, temp);
	                        	}
	                        	else{
	                        		tempString = text.substring(0, temp+1);
	                        	}
	                        	//String tempString = text.substring(0, temp);
	                        	//FORM_GENERATOR_ISSUE_FOR_TEXTAREA ends
	                        	
	                            formattedText = formattedText+tempString+"<br/>";
	                            text = text.substring(temp+1, actualLen);
	                        
	                            actualLen = text.length();
	                        } else {
	                        	actualLen = -1;
	                        }
	        			}
	        			
	            		formattedText = formattedText+text;
	        		} catch(Exception e) {
	        			logger.error("Exception in breakText", e);
	        		}
	        		
	        	}
	        	
	        	return formattedText;
	        }
	    	 /**
	    	* for escaping special characters e.g ",','\n','\r' in generated csv file 
	 	    * @author Priyanka Jain
	 	    * BB-20140331-050 
	 	    */
	    	public static String escapeCsv(String str) {
	    		
	            if (org.apache.commons.lang.StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
	                return str;
	            }
	            try {
	                StringWriter writer = new StringWriter();
	                escapeCsv(writer, str);
	                return writer.toString();
	            } catch (IOException ioe) {
	               ioe.printStackTrace();
	            }
	            return "";
	        }
	    	 /**
	    	* for escaping special characters e.g ",','\n','\r' in generated csv file 
	 	    * @author Priyanka Jain
	 	    * BB-20140331-050 
	 	    */
	    	 public static void escapeCsv(Writer out, String str) throws IOException {
	    	        if (org.apache.commons.lang.StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
	    	            if (str != null) {
	    	                out.write(str);
	    	            }
	    	            return;
	    	        }
	    	        out.write('"');
	    	        for (int i = 0; i < str.length(); i++) {
	    	            char c = str.charAt(i);
	    	            if (c == '"') {
	    	                out.write('"'); // escape double quote
	    	            }
	    	            out.write(c);
	    	        }
	    	        out.write('"');
	    	    }
	   
	    	 /**
	    	  * Method get ArrayList from query result.
	    	  * @author Vivek Maurya
	    	  * @date 26Jun2014
	    	  */
	    	 public static ArrayList<String> getArrayList(String query, String columnName, 
	    			 boolean isPerfix, String strValue) {
	    		 
	    		 ArrayList<String> dataList = new ArrayList<String>();
	    		 if(StringUtil.isValid(query)) {
	    			 com.home.builderforms.sqlqueries.ResultSet result = null;
	    			 try {
	    				 result = QueryUtil.getResult(query, null);
	    				 if(result != null) {
	    					 String value = null;
	    					 if(!StringUtil.isValid(strValue)) {
	    						 strValue = FieldNames.EMPTY_STRING;
	    					 }
	    					 
	    					 while(result.next()) {
	    						 value = isPerfix ? (strValue+result.getString(columnName)) : 
	    							 (result.getString(columnName)+strValue);
	    						 
	    						 dataList.add(value);
	    					 }
	    				 }
	    			 } catch(Exception e) {
	    				 logger.error("Exception in getArrayList", e);
	    			 } finally {
	    				 QueryUtil.releaseResultSet(result);
	    			 }
	    		 }
	    		 
	    		 return dataList;
	    	 }
	
	//CG Integration starts
	public static String isRoleID(String userNo)
	{
		Connection con = null;
	    UserRoleMgr	roleMgr = UserRoleMgr.getInstance();
	    PreparedStatement st = null;
	    java.sql.ResultSet rst = null;
	    String roleID = ",";
	    try{
	    	con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
	    	st	= con.prepareStatement("SELECT ROLE_ID FROM USER_ROLES WHERE USER_NO = ?");
	    	st.setString(1, userNo);
	    	rst = st.executeQuery();
	    	while(rst.next())
	    	{
	    		roleID+= rst.getString("ROLE_ID")+",";
	    	}
	    }
	    catch (Exception exp){
	    	Debug.print(exp);
	    	Debug.println("Exception. Unable to initialize UserRoleMap");
	    }
	    finally {
	    	try
	    	{
	    		DBConnectionManager.getInstance().freeConnection(con);
	    	   	SQLUtil.closeStmt(st);
	    	   	SQLUtil.closeResultSet(rst);
	    	}
	    	catch(Exception exp)
	    	{
	    		Debug.print(exp);
	    	}
	    }
	    return roleID;
	}
	//CG Integration ends
	
	public static String getUserDateTime(String userTimeZone) throws ConnectionException 
	{
		String dateLimit="";
		try{
		dateLimit=TimeZoneUtils.performUTCConversion(com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().DB_TIMEZONE_TIMEZONEUTILS,userTimeZone,DateUtil.getCurrentDateTimeDB(),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dateLimit;
	}
	
	//BBEH_PW_TASK_TRIGGER_MAIL_STARTS
	 public static String getOwnerName(String userNO) {
			StringBuffer name = new StringBuffer();
	               System.out.println("\n\n Userno is :::::::::::::::::::::::::::"+userNO);
			String query = "SELECT USER_FIRST_NAME,USER_LAST_NAME FROM USERS WHERE USER_NO=?";
			 com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), new Object[] { userNO });
			if (result != null && result.next()) {
				name.append(result.getString("USER_FIRST_NAME"));
	                       name.append(" ");
	                       name.append(result.getString("USER_LAST_NAME"));
			}
	               return name.toString();
		}
	
	
	
	
	public static String getEmailIDs(String userIdentificationNos,String userlevl){

		String emailID = "";
		String query = "";
		 com.home.builderforms.sqlqueries.ResultSet rs = null;
	 	try{
			if(userlevl==null || "0".equals(userlevl) || "2".equals(userlevl)){	
	            query ="SELECT EMAIL_ID FROM FRANCHISEE,USERS WHERE USERS.FRANCHISEE_NO=FRANCHISEE.FRANCHISEE_NO AND USERS.STATUS='1' AND USERS.IS_DELETED='N'  AND USERS.USER_NO IN ("+userIdentificationNos+")";
	        }
			else if("1".equals(userlevl)){
	            query ="SELECT EMAIL_ID FROM USERS U LEFT JOIN FRANCHISEE_USERS FU ON U.USER_IDENTITY_NO=FU.FRANCHISEE_USER_NO  WHERE U.FRANCHISEE_NO=FU.FRANCHISEE_NO AND U.STATUS='1' AND U.IS_DELETED='N'  AND U.USER_NO IN ("+userIdentificationNos+")";
	        }
	        rs = QueryUtil.getResult(query.toString(), null);
	        boolean isFirst = true;
	        if (rs!=null)
	        
	        	while(rs.next())
	        
	        	{
	        		if(isFirst)
	        		{
	        			emailID = rs.getString(1);
	        			isFirst = false;
	        		}
	        		else
	        		{
	        			emailID = emailID + "," + rs.getString(1);
	        		}
	        	}
	    } 
		catch (Exception e) 
		{
			logger.info("Exception while fetching emailid in BaseNewPortalUtils || "+e);
		}
		finally
		{
			QueryUtil.releaseResultSet(rs);
		}
		if(StringUtil.isValid(emailID))
			emailID.replaceAll(";",",");
		return emailID;
	}
	//BBEH_PW_TASK_TRIGGER_MAIL_ENDS
	
	
	//FC-184--(Schedule visit for multiple FranchiseIDs at the same time) Starts
	public static StringBuffer getComboByInfoAudit(Info comboInfo, String comboName,
			String selectValue, String defaultOption, String jsFunction,String fromWhere,String ComboClass,String comboStyle, boolean showSelect,boolean multiSelect)
	{
	if(StringUtil.isValid(jsFunction))
		 jsFunction = " onchange=\""+jsFunction+"\" ";
	else
		jsFunction = "";
	
    StringBuffer periodCombo = new StringBuffer();
    if(StringUtil.isValidNew(ComboClass))
	{
		if(multiSelect){
		periodCombo.append("<select multiple=\"true\" size=\"1\" class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
		}
		else
		{
		periodCombo.append("<select class=\""+ComboClass+"\" name=\""+comboName+"\" id=\""+comboName+"\" "+jsFunction);
		}	
	}
	else
	{
		if(multiSelect){
		periodCombo.append("<select multiple=\"true\" size=\"1\" class='form-control' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
		}else
		{
			periodCombo.append("<select class='form-control' name='"+comboName+"' id='"+comboName+"' "+jsFunction);
		}
	}
    

    if(StringUtil.isValid(comboStyle)){
    	periodCombo.append(" "+comboStyle);
    }

    periodCombo.append(">");
    if(StringUtil.isValidNew(comboName) && "contactIdCombotms".equals(comboName) || "companyNameCombo".equals(comboName) || "categoryProductCombo".equals(comboName))
    {
        periodCombo.append("<option value=''>select</option>");
        if(StringUtil.isValidNew(defaultOption))
        	periodCombo.append("<option value='-1'>"+defaultOption+"</option>");
    }
    else if(defaultOption != null && !"".equals(defaultOption.trim()))
    {
    	periodCombo.append("<option value=\"-1\">"+LanguageUtil.getString(defaultOption)+"</option>");
    }
    else if(showSelect)
    {
        periodCombo.append("<option value=\"-1\">"+LanguageUtil.getString("Select")+"</option>");
    }
    
    Iterator itKey = comboInfo.getKeySetIterator();
    String itValue = null;
    while (itKey.hasNext())
    {
    	itValue = itKey.next().toString();
    	if(multiSelect)
    	{
    		if(StringUtil.isValid(selectValue) && selectValue.contains(itValue))
    		{
	            periodCombo.append("<option value=\""+itValue + "\" selected='selected'" ).append(">");
	            periodCombo.append(comboInfo.getString(itValue));
	            periodCombo.append("</option>");
        	}
    		else
    		{
                periodCombo.append("<option value=\""+itValue).append("\">");
                periodCombo.append(comboInfo.getString(itValue));
                periodCombo.append("</option>");
        	}
    	}
    	else
    	{
    		if(selectValue != null && selectValue.equals(itValue))
    		{
    			periodCombo.append("<option value=\""+itValue + "\" selected='selected'" ).append(">");
    			periodCombo.append(comboInfo.getString(itValue));
    			periodCombo.append("</option>");
    		}
    		else
    		{
    			periodCombo.append("<option value=\""+itValue).append("\">");
    			periodCombo.append(comboInfo.getString(itValue));
    			periodCombo.append("</option>");
    		}
    	}
    }
    if(StringUtil.isValid(fromWhere) &&( "tmsEst".equals(fromWhere) ||  "tmsInv".equals(fromWhere)))
    	periodCombo.append("<option   class='text_b' style='background-color: rgb(204, 204, 204);' value='0'  >"+LanguageUtil.getString("Add New Job")+"</option>");
    periodCombo.append("</select>");	
    
    return periodCombo;
	}
	//FC-184--(Schedule visit for multiple FranchiseIDs at the same time) Ends
	
	public static String getMultipleCountryName(String countryId) {
		String countryName = "";
		
		String[] countryList=countryId.split(",");
		for(int i=0;i<countryList.length;i++)
		{
			Map<String,Object> countryMap = CacheMgr.getCountryCache().getCountry(countryList[i]);

			if (countryMap != null && !countryMap.isEmpty())
			{
				countryName += "'"+(String)countryMap.get(FieldNames.COUNTRY_NAME)+"',";
			}
		}
		if(countryName.indexOf(",")!= -1){
			countryName=countryName.substring(0,countryName.lastIndexOf(","));
		}
		
		return countryName;
	}
}//end class
