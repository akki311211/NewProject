package com.home.builderforms;
import com.home.builderforms.DataCollector;


/**
 * Version No.				   Date		         By	    		           Function Changed			
 * P_B_CM_040413          	10 Apr 2013       	Prabhat Jain               Table header get disapeared on click sort field
 P_FIM_B_20074             30 apr 2013     Priyanka jain                    pagging was not proper
 P_E_CurrencyFormat         2 apr 2013     Priyanka jain                 to change currency format while displaying
 P_E_C_TRACKING  		23 May 2013   Brajesh Yadav    updateDocumentDownload()  New Method created to update Data on Download Artwork or Email Artwork
 P_CM_B_23006              05JULY2013       PRASHANT MALIK         Bug                 MUID
 P_ENH_SMS_CAMPAIGN             9 JUly 2013       Himanshi Gupta         SMS Campaign Enhancement 
 P_ADMIN_B_23654			10 July 2013		Anubhav Jain			BUG			ALL locations should show for muid user			 
 P_ADMIN_B_24277			16 July 2013		Prabhat Jain			BUG			creation date for print campaign is not being displayed.
 P_B_CM_24335		17 July 2013		Deepak Gangore		              MUID change
 P_ADMIN_B_24339			18 July 2013		Prabhat Jain			BUG			Email id is not a link for email column
 P_B_CM_25289				26 jULY 2013		Prabhat Jain			BUG			Phone No. and Mobile is not formatted correctly.
 P_B_CT_25415             27th July,2013      Prakriti Sharma         BUG         MUID, adding muid users to the "franchise users" combo in report.
 P_B_CT_23852             29th July,2013      Prakriti Sharma         BUG         MUID,dashboard wasn't relecting the data.
 P_B_25485				26July2013			Naman Jain				data is not coming in date
 P_B_CT_26266             6th Aug,2013        Prakriti Sharma         BUG         Js error when clicked on CT > Dashboard(widgets).
 P_ADMIN_B_26356			13 Aug 2013			Anubhav Jain			Bug			Franchise user info not dispalying
 P_B_COMMON_8137         19 Aug, 2013      Banti Prajapati 			Bug	  Case :{User Role Privileges has been passed in a Map<String,Boolean> to add privileges in action menu.}
 P_B_26757               22 aug 2013       Banti Prajapati 			Bug	       JS error { Special characters escaped}
 P_Enh_Contact_type_23   26 Aug 2013       Divya Mishra               Enh			where clause is added to display the contact type in seb menus
 P_B_CT_28804            23 Sep 2013       Teena Sharma               Bug         Template is not coming in the "Existing Email Templates " drop down menu
 P_B_SCH_29744		   08 Nov 2013       Banti Prajapati			Bug			Case [Improper details of task product and services were showing in the mail to owner ]    
 P_E_MUID_CONFIG			27/11/2013		Anubhav jain		Enh
 SCH_OPT					10/01/2014			Enh			Chinmay Pareek			regarding to optimization in schedular.
 P_MP_B_29582             06/02/2014        Deepanshi Miglani         Bug         to remove deactivated franchisees from applied franchisee list in marketing pilot
 P_B_CM_34490			5th March 2014     Rachit Parnami			Bug			All users are not coming of a particular region id.
 P_B_AB_33887 			 7/03/2014 			 Shubham Ruhela			Rng			Address Not Populated : SpecialCharProblem
 CKCA-20140211-016	 	07March2014			Gaurang Agarwal			Bug			Timezone issue in reminders

 BB-20140303-019        13Mar2014				Varun Negi						Implemented a new GUI

 Sperry-20140204-132		18March2013			Naman Jain				Enh         When user search by First Name (Jerry) 7 results come up. When they search by First and Last name (Jerry Anderson ) 9 results come up.
 SMC-20140224-392	11 March 2014		Deepak Gangore			added condition in getSource3Map() for PPC lead settings.

 SMC-20140313-420	26 March 2014		Deepak gangore			Top Bar enhc
 ZCUB-21040402-034		  03/04/2014	Mohit Mishra			Added Export button 
 P_B_SCH_36280			4 apr 2014			Manik Malhotra						No result was shown due to wrong query.
 P_B_CM_36203          07 Apr  2014        Akash Jain                           Contact Manager > Reports > Contact Source Analysis > Email Report. 
 P_B_CM_36666		8th April,2014		Nancy Goyal												on exporting as excel,Only the previous page's records are exported.(fixed)
 P_B_SCH_34534       16th April,2014      Teena Sharma                 issue all rate cards are being displayed even if the regions are different.
 P_B_SCH_35929       16th April,2014      Teena Sharma                  dd/mm/yyyy format is coming for added task emails, it should be mm/dd/yy format
 SMC-20140415-462	17 April 2014		Deepak Gangore			Marketing Pilot optimization
 P_CM_Reports_B_38357  16 may 2014       Ikramuddin             the date format should be as per  the UK format 
 SMC_PW_OPT           27May14            Pushkal Sharma          getFranchiseeComboForRegion1(),  getFranchiseComboOptions(),  getFranchiseeResultSet() mehods added          
 P_B_AB_38177		10-jun-2014			Nivrutti Joshi			Download / Email the Artworks, recently downloaded / Emails artworks not coming at top
 P_CM_B_41804        23 JULY 2014        SWATI GARG              Correction of search through title drop down
 P_CM_B_42078        23 JULY 2014        SWATI GARG              Correction of Add category detail combo
 P_B_CM_42652     25 July 2014           Rashmi Shakya           GUI issue of combo.
 P_B_CM_43425       12th Aug 2014        Amit Tanwani            ? was coming for quote while company ajax search!
 P_B_RESTRUCT		05th Sep, 2014		Sanshey Sachdeva		Restruct		Changes regarding Service Restructuring
 P_FS_B_47896		17 Sep,2014			Nishant Verma			to show all records on click of Show All
 P_B_GUI_805			12th Sep, 2014		Sanshey Sachdeva		GUI Changes		Combo class is changed according to new gui.
 P_B_MP_47876 		17 Sep 2014			Nishant Prabhakar		Franchisee Locations were not ordered alphabetically
 P_B_CT_41454        14aug2014           Chetan Rawat            solved report issue(CT). Null Pointer Exception handled.
 P_B_CT_41480        19Aug2014  			Chetan Rawat            Resolved save view issue in Customer Transactions > Reports > Revenue Comparison / Sales by Period
 P_B_GUI_805			18 Sep, 2014		Sanshey Sachdeva		GUI Changes		getFranchiseUserCombo()		Combo was not showing selected options
 P_B_SCH_48956       14 Oct 2014         Somya Garg              Bug                 						Task Subject's special characters get converted when viewed in the mail.
 P_E_CT_GUI          20oct2014           Chetan Rawat	        Enh          Gui changes(Changes Job status color according to new color picker.)
 BB-20141017-177               5 nov 2014          Nazampreet Kaur       code added for customized summary display
 P_B_SCH_49733		12 Nov 2014			Ronak Maru				Bug  		job is not visible to franchise user
 P_B_JOB_50201       18 Nov 2014         Teena Sharma            Bug         Spacing is not proper. 
 P_B_JOBS_50277		20 Nov 2014			Somya Garg				Bug			Under Owner user as well Default User mails, GUI issues have been corrected
 P_B_JOBS_50144		20 Nov 2014			Ronak Maru				Bug			Products/Service section had an older GUI and all the products were not displayed.
 P_B_JOBS_50157		24 Nov 2014			Somya Garg				Bug			Wrong Labels in cancelled task's mail.
 P_B_JOBS_50276		24 Nov 2014			Somya Garg				Bug			Changed address format.
 ZCB-20150120-071        25 feb 2015    Divanshu Verma       To enable social Media module to work in MUID case
 ZCUB-20150515-147   5 June 2015       Divanshu Verma        Zip Code Validation
ZCUB-20151208-210       19 Dec 2015     Divanshu Verma      Payment Approval Process
ZCUB-20160310-239    18 March  2016    Divanshu Verma    Email Notification in case of RM Reports
 BB-20160203-516	 1 Apr 2016			Ronak Maru				BI Dashboard 
 ABS_NM082016        17 aug 2016        Utsav Chauhan                Note message textarea and textbox.
 */








import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.InputStream;
import java.util.Properties;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;


import org.json.JSONObject;

import com.home.builderforms.Constants;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;


public class CommonUtil {
    static	Logger logger = Logger.getLogger(CommonUtil.class);
    public static String getSelectedFields(String tableAnchor)
	 {
		 String selectedFields = FieldNames.EMPTY_STRING;
		 String query = "SELECT GROUP_CONCAT(FIELDS) AS FIELDS FROM CONFIGURE_TABULAR_FIELDS WHERE TABLE_ANCHOR = ? AND IS_ACTIVE = ? ORDER BY ORDER_NO";
		 ResultSet rs = null;
		 try {
			 rs = QueryUtil.getResult(query, new String[]{tableAnchor,"Y"});
			 if(rs!=null && rs.next())
			 {
				 selectedFields = rs.getString("FIELDS");
			 }
		 } catch (Exception e) {
			 logger.error("ERROR: exception in getSelectedFields ::", e);
		 }
		 return selectedFields;
	 }


public static String[] getFranchiseeAndOwner(HttpServletRequest request)
   {
       String franchiseeOwner[]=new String[2];
       HttpSession session=request.getSession();
       String menuName = "";
       ArrayList list=(ArrayList)session.getAttribute("FranchiseeNoList");

       if("ppc".equals(menuName)){//P_B_26486
           list = (ArrayList)session.getAttribute("FranchiseeNoListAll");
       }

       String franchiseList="";
       try {
           if("All".equals((String)session.getAttribute("franchisee_all"))&&list!=null) {
               for(Object obj:list) {
                   if("All".equals(obj.toString())) {
                       continue;
                   }
                   else if("".equals(franchiseList)) {
                       franchiseList=obj.toString();
                   }
                   else {
                       franchiseList+=","+obj.toString();
                   }
               }

           }
       } catch(Exception e) {
           logger.error("Exception in getFranchiseeAndOwner", e);
       }

       franchiseeOwner[0] = franchiseList;
       franchiseeOwner[1] = (String)request.getSession().getAttribute("user_no");
       return franchiseeOwner;
   }

public static String getRegionList(String userList)
   {
       String regionList="";
       String owner[]=userList.split(",");
       for(String str:owner)
       {
           if(FieldNames.EMPTY_STRING.equals(regionList))
               regionList=AdminMgr.newInstance().getUsersDAO().getAreaID(str);
           else
               regionList+=","+AdminMgr.newInstance().getUsersDAO().getAreaID(str);
       }
       return regionList;
   }
public static Info getComboInfo(String tableName,String columnName){
       Info info=new Info();
      /* BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
       LinkedHashMap map=null;
       String initialValue=FieldNames.EMPTY_STRING;
       String endValue=FieldNames.EMPTY_STRING;
       String key=FieldNames.EMPTY_STRING;
       String score=FieldNames.EMPTY_STRING;
       HashMap hMap=null;
       if("NET_WORTH_SCORES".equals(tableName) || "LIQUIDITY_SCORES".equals(tableName))
//			map=(LinkedHashMap)CacheDataUtil.getHeatElementMap().get(tableName);
           map=(LinkedHashMap)NewPortalUtils.getHeatElementMap().get(tableName);
       else
//			map=(LinkedHashMap)CacheDataUtil.getHeatElementMap().get("INVESTMENT_SCORES");
           map=(LinkedHashMap)NewPortalUtils.getHeatElementMap().get("INVESTMENT_SCORES");
       if(map!=null)
       {
           Iterator it=map.keySet().iterator();
           while(it.hasNext())
           {
               key=(String)it.next();
               hMap=(HashMap)map.get(key);
               if("SCORE".equals(columnName))
               {
                   score=(String)hMap.get(FieldNames.SCORE);
                   info.set(key,score);
               }else
               {
                   initialValue=(String)hMap.get(FieldNames.INITIAL_VALUE);
                   endValue=(String)hMap.get(FieldNames.END_VALUE);
                   if("NET_WORTH_SCORES".equals(tableName) || "LIQUIDITY_SCORES".equals(tableName))
                   {
                       if("0".equals(initialValue))
                           initialValue="Under "+_baseConstants.USER_CURRENCY;
                       else if("0".equals(endValue))
                       {
                           endValue=initialValue;
                           initialValue="Over "+_baseConstants.USER_CURRENCY;
                       }
                       else
                           initialValue=_baseConstants.USER_CURRENCY+initialValue+"  to "+_baseConstants.USER_CURRENCY;
                   }else
                   {
                       if("0".equals(initialValue))
                       {
                           initialValue="Under ";
                           endValue+=" Month";
                       }
                       else if("0".equals(endValue))
                       {
                           endValue=initialValue +" Months";
                           initialValue="Over ";
                       }
                       else
                       {
                           initialValue+="  to ";
                           endValue+=" Months";
                       }
                   }
                   info.set(key,initialValue+endValue);
               }
           }
       }
       map=null;
       initialValue=null;
       endValue=null;
       key=null;
       score=null;
       hMap=null;*/
       return info;
   }
public static Info getBackGroundInfo() {
       return SQLUtil.getColumnValueInfo("EMPLOYMENT_BACKGROUND", "EBG_ID", "EBG_NAME", null, null, "EBG_NAME", null);
   }
	     
	     
	     
}
