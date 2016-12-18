/**
 * Copyright (c) 2001  Webrizon eSolutions Pvt Ltd
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
P_FRANONLINE_E_53204    05-Feb-2009     Saurabh Sinha                 getMultipleComboSelection
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
<<<<<<< PortalUtils.java
DB_F_B_76154       21 june 2011  vishal singh              for converting us date formate to other
P_DASHBOARD_B_QueryOptimization	16Jun2011		Omindra Rana		Optimization
P_FIN_B_76687                   30 june 2011     Himanshi GUpta     background color was not coming on pdf for waiting for acknowledgement sales report
 * B_76471  01/07/2011  Narotam Singh
P_FRANBUZZ_B_74587           21 july 2011              PRAKASH JODHA         CHANGES DONE TO handle anchore tag in FRNBUZZ
=======
ENH_71BBFCNE12           20June2011           Veerpal Singh                     Enh                    Upload Logo for invoice is one time activity
>>>>>>> 1.469.2.2
 --------------------------------------- -------------------------------------------------------------------------------------------------------------------------

 */

package com.home.builderforms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.home.builderforms.sqlqueries.ResultSet;

/**
 * This class contains some frequently used general utility methods being used
 * in the portal All methods have been declared static
 */

public class PortalUtils {
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
		else if(Constants.DISPLAY_FORMAT.equalsIgnoreCase("dd/MM/yyyy"))
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
	if(Constants.DISPLAY_FORMAT.equals("MM/dd/yyyy"))
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

public static Info getIdValueInfo(String table, String idField, String valueField)
{
	Info returnInfo = new Info();
	try 
	{
    	String query = "SELECT "+idField+ " , "+valueField+" FROM "+table+" ORDER BY "+valueField;
    	ResultSet  resultSet = QueryUtil.getResult(query.toString(), new Object[]{});
  	  	while(resultSet.next()) 
  	  	{
  	  		returnInfo.set(resultSet.getString(idField), LanguageUtil.getString(resultSet.getString(valueField)));
  	  	}
	} catch (Exception e) 
	{
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
    	ResultSet  resultSet = QueryUtil.getResult(query.toString(), new Object[]{});
  	  	while(resultSet.next()) 
  	  	{
  	  		returnInfo.set(resultSet.getString(idField), LanguageUtil.getString(resultSet.getString(valueField)));
  	  	}
	} catch (Exception e) 
	{
	}
	return returnInfo;
}
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

public static String getAreaDocumentsData(String foreignId, String order)
		throws ConnectionException {
	String returnValue = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	java.sql.ResultSet rs = null;

	try {
		con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
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
		}
	}
	return returnValue;
}

public static boolean hasDependentAddressTable(String tableName,HttpServletRequest request){
	try{
		String menuName = null;
		if(request!=null){
			menuName = request.getParameter("reportMenu")!=null?request.getParameter("reportMenu"):(String)request.getSession().getAttribute("menuName");
		}
		if("fim".equals(menuName)){
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
						if("address".equals(dTable.getTableAnchor())){
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

public static String formatPhoneNo(String phone, String country) {
	if (phone == null)
		phone = "";
	StringBuffer sb = new StringBuffer(phone);
	try {
		if(StringUtil.isInt(country)) {//Wee Watch phone numbers starts
			//country = PortalUtils.getCountryNameById(country);
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
}//end class

