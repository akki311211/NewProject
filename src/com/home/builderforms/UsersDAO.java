/**
* Class Name    - UsersDAO.java
* Location      - com.appnetix.app.components.adminmgr.manager.dao
* <p>
* @author       - Rakesh Verma
* @version      - 1.1
* date          - 08-Sept,2006
* Description	- This DAO is basically involved in interacting with the Database to insert/update/retrive certain User's Detail.
* 
* 
--------------------------------------------------------------------------------------------------------
Version No.					Date			By				Against		Function Changed    Comments
AUDIT_ENHANCEMENTS_PARTIAL_REGIONAL_LOGIN	8-5-2013	VARSHA GUPTA	Changes related to the regional user login, regional user can only view user info of its own area id , franchise id,and when franchise id is -1 then no user info will generate
BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
BBEH_INTRANET_SMC_OPTIMIZATION  18/07/2013      Rohit Jain    Code and Query optimization for Smc Intranet.
Enh_Int_roleBasedRssFeed			Shalini Rana			Enhancement to make RSS feed role based									
SCH_B_29502				   7/11/2013	Banti Prajapati		BUG			Special characters
PW_REPORT_MOD              6 OCT 2014   Rachit Upmanyu      Appropriate Franchise ID Users in Outstanding Actions Report                                       
P_B_CM_64980 	      23 Oct 2015      Divanshu 				Owner Deactivation Assignment Issue
----------------------------------------------------------------------------------------------------------------

*/

/*
-----------------------------------------------------------------------------------------
Version No.		      Date		  By	           Against		                    Function Changed          Comments
--------------------------------------------------------------------------------------------------------------------------------
D_Adm_E_userList101	04/12/06	SaurabhK	    Activation and Deactivation  
                                                corpoarte and Regional User
D_A_E_USERSDOB1 		01/12/2006	Rakesh			Add Query for dob insert and			insertFranchiseeData()
												update for franchisee users			updateFranchiseeData()

D_A_E_USERSDOB2 		12/12/2006	Rakesh			when dob is not given				insertFranchiseeData()
												then it will be "0000-00-00".		updateFranchiseeData()

D_A_B_21720 			13/12/2006	Rakesh			login id of deleted user can be		checkLoginIDNew()
												given to other user.	 and login id 	isUserExist()
												of deactivated user can not be  
												given to other user.

D_A_B_21807 			13/12/2006	Rakesh			add sorting on state. 				getFranchiseUsers()
P_SO_E_111          02/11/2007          Suchita Agrawal     getRegionalUsers(),getUserDetail(),updateRegionalData(),
                                                                            providing access of store opner to RU
												 									getCorporateUsers()
																					getRegionalUsers()
P_SO_B_29471            19/11/2007          Suchita Agrawal     updateRegionalData()            updating all checklist for regional user

P_SO_B_36594            10/07/2008          Vivek Sharma	     updateCkeckListsInfo()            updating all checklist for Franchisee users
												  
P_SO_B_37398            22/08/2008          Vivek Sharma	     updateRegStoreItems()            add this function to insertRegionalData() for updating all checklist for regional users.												
Theme Enhancement       26/04/10            Ankit Saini              added new method for theme enhancement.

-----------------------------------------------------------------------------------------
*/
/*
-----------------------------------------------------------------------------------------
Version No.		      Date		  By	           Against		                    Function Changed          Comments
--------------------------------------------------------------------------------------------------------------------------------
D_Adm_E_userDeletion	14/12/06	Kulmeet	    User Delteion  

D_A_E_FRANUSERS_1901	05/01/2007	SiddharthS	standardizations front end			getFranchiseUsers()
D_A_E_CapUserName	07/01/2007	Rakesh		Change first character				insertFranchiseeData(),updateFranchiseeData()
											of user's first and last			insertCorporateData(),updateCorporateData()
											name to upper case.					insertRegionalData(),updateRegionalData()

D_A_B_22402			08/01/2007	Rakesh		Place "," between					getFranchiseeName()
D_A_B_22690			18/01/2007	santanu		Order by name 					getRole()
											owner's last name and first name.
											
D_A_B_23060 			5/3/2007	Vivek		Make first char of city capital in insert and update functions		

D_CAL_B_23411 	    22/03/2007	Rakesh		Update user's TimeZone in calendar,
											when insert or update any user.				
D_A_B_23439  	    22/03/2007	Rakesh		Sorting on City,State for all type 
											of users.				
P_E_FRAN_ADD_EMP   19/08/2008 Ram Avtar    franchisee - can add employee 	
 //P_ADMIN_E_FRANCHISEE_ADD_MODIFY_USER  07/10/2008   Ram Avtar Add a field in method for franchisee Admin    getOwnerData()									
//P_ADM_B_39454    08/10/08			Ram Avtar     execute()              for preparemenu map different franchisee name
P_ADMIN_B_39573  14/10/08      	Ram Avtar     execute()              for formated phone no for USA only
 P_INT_B_41266    27-NOV-2008      Saurabh Sinha                 Filter search is made to replace special charachters
P_ADM_B_42354     31 Dec 2008      Ram Avtar     getRole()       Role for franchisee user on condition
P_FRANONLINE_E_ADDTOFRANONLINE 09/02/09   Ram Avtar   insertCorporateData(),insertFranchiseeData() Add new condition and  user informations to franOnline profile table
													  for corporate,regional user with fields(userNo,address,city,stateId,countryId,
													  zipcode,phone1,phone2,mobile,email) and additional (dob for franchisee user)
P_E_AGENT_ASSIGNMENT	April 17,2009 Manoj Kumar			getLeadCount()		All the actions will be performed on Agents as well as users. corporate users will be working as agent with AGENT_USERS table.
P_FRANONLINE_B_53428	14/Dec/09   Saurabh Sinha
P_ADMIN_B_55686         11/01/2010    Anuj Goel                       updateCorporateData()         For issue related to bug id 55686
P_CM_B_01022010      01/02/2010     Anuj Goel                      Contacts, campaign, templates and group data was not updating after modifying the region of regional user
 
//P_FS_E_3_08_2010     aug /3/2010 mukesh sundriyal insertCorporateData() Add phone1 extension and phone2 extension columns for corporate user,RegionalUser and FranchiseeUser
//P_B_ADMIN_64149         28/sep/2010    Ankit Saini
  P_ADM_E_0706            13 Oct 2010     Vartika Joshi       added remote ip and created user logs in user status logs link
P_ADM_B_69308            12 JAN 2011   JOGANDRA PAL SINGH       FOR PERSIONAL INFOO FOR FRANCHISEE USERS
P_FRANONLINE_B_64368    21 jan2011       Himanshi Gupta                Login user theme was coming 
P_E_70910154            03/03/2011          Nishant Prabhakar                       for opening the new window and focus on it if already previous one exists
D_CM_B_70785        10-03-2011      Vishal Lodha        updatecontactDetailsForRegionalUsers()  for update the areaID in  CM_CONTACT_DETAILS when regional users change the area/region
P_GEN_E_Exception    18 Mar 2011   Abishek Singhal     Exception             Wrong Logger level was used
REF:71BBFCNE21       16-JUNE-2011  Nipun Gupta          enh                  ADDED THE METHOD getFranUserNo()
P_FIM_B_3990         27 jan 2012    prakash jodha        bug                 changes done to remove null pointer exception
P_FIM_B_4852         22 feb 2012    prakash jodha        bug                 changes done to show admin site added new employee in FIM empoloyee tab
P_E_NOTIFICATION       23/03/2012   Nishant Arora                            GUI changes in NOTIFICATIONS AND QUICKLINKS
P_B_ADMIN_7458      30th March 2012 Prakriti Sharma     Bug
P_B_INT_9591          4 JUNE 2012     Megha Chitkara                        changed to update birth date of franchisee user in franbuzz profile when changed from add franchisee user form
P_ADMIN_ENH           27 July 2012       Dheeraj Madaan                      removed password.toLowerCase() as user is not able to login if password contains Capital letters
Topper_campaign_mail	05Sep2012		Vivek Maurya		Enh
P_ADMIN_B_13347 		11 Sep 2012 	Dravit Gupta		window height is changed to 360 from 335
BBEH_MERGING_DATE_UTIL_FILES  22/02/2013      Rohit Jain    merger of All DateUtil classes into /util/DateUtil.java And apropriate changes has been done in related files .
P_ADMIN_E_MUID				3 Jun 2013		Anubhav Jain		Enh
P_ADMIN_B_21954				19 Jun 2013		Anubhav Jain		Bug Search is Not Working
P_B_ADMIN_22788			10 July 2013		Deepak Gangore		getMUIDContactTaskMap(), getMUIDfromUserNo() are created regarding deactivation of MUID user.
P_ADMIN_B_26820			13 Aug 2013			Anubhav Jain		Displaying the deleted user,under manage user list
P_ADMIN_B_27008 		14 Aug 2013			Anubhav Jain		creating employe by selecting franchise id but the the employe user created is getting create on MU TYPE
P_ADMIN_B_27057			16 aug 2013			Anubhav Jain		unable to create muid employee
P_E_MUID_OWNERNAMES		20 Sep 2013			Anubhav Jain		Show ownere Names along with muid .
P_FIM_B_28863 			25 Sept 2013		Niranjan Kumar		sendMailPrivilige appended to checked ids & '+' in logger.error() replaced by ',' & sysouts changed to logger
P_E_MUID_ADDOWNER				7/11/2013	Anubhav Jain		Enh			Owner name should also populate while adding any franchise user (by selecting MUID)
P_E_MODIFY_MUID			18 Nov 2013			Anubhav Jain		Enh		Modify MUID option 
P_E_MUID_CONFIG			27/11/2013		Anubhav jain		Enh 
DB_CACHE_OPTIMIZATION   24March2014    Anu Gupta          User Data Cache Optimization 
P_B_CM_42441              24 July 2014         Devam Gupta          roles of corporate user was not retreiving                   
BB-20141017-177			30-Oct-2014			Gaurang Agarwal			Enh					Configurable Columns on users
P_B_ADM_34152				31 Oct 2014		 Ronak Maru				Task reassigned combo is not showing when we deactivate the user
BB-20151201-455      		23 Jan 2016		RonakM						 Active Directory Integration

 ----------------------------------------------------------------------------------------------------------------------*/
package com.home.builderforms;

import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


//import com.home.builderforms.cache.UserEmailCache;
//import com.home.builderforms.cache.UserNameCache;
//import com.home.builderforms.cache.UsersCache;
//import com.home.builderforms.cache.UsersCacheWithType;
public class UsersDAO extends BaseDAO {
	static Logger logger		= Logger.getLogger(UsersDAO.class);
	public String getAreaID(String userNO)
	   {
		   String areaID="-1";
		   String query="SELECT F.AREA_ID FROM USERS U LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE   U.USER_NO="+userNO;
		   ResultSet rs=QueryUtil.getResult(query,null);
		   if(rs.next())
			   areaID=rs.getString("AREA_ID");
		   return areaID;
	   }
public boolean isUserActive(String userNo) {
 boolean isUserActive = false;
 ResultSet rs1=null;
 try {
     String query = "SELECT STATUS FROM USERS WHERE USER_NO=" + userNo;
      rs1 = QueryUtil.getResult(query,null);
     if (rs1.next()) {
         if (rs1.getString("STATUS").equals("1")) {
             isUserActive = true;
         } else {
             isUserActive = false;
         }
     }
 } catch (Exception e) {
     e.printStackTrace();
 }finally
	{
		QueryUtil.releaseResultSet(rs1);
	}
 return isUserActive;
}
public SequenceMap getAllSourceOwnerMap() {
SequenceMap sourceOwner = new SequenceMap();
Info info=null;
int i=0;
ResultSet result = null;
StringBuffer query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS USER_NO FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND BRAND_ID='NA' AND FSO.ASSIGNMENT_TYPE='SR' GROUP BY FSO.SOURCE_ID");
query.append(" UNION ");
query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS USER_NO FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) AND REGION_ID='NA' AND BRAND_ID='NA' AND FSO.ASSIGNMENT_TYPE='SR' GROUP BY FSO.SOURCE_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
try {
	result = QueryUtil.getResult(query.toString(), null);
	while(result.next()) {
		info = new Info();
		info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
		info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
		info.set(FieldNames.USER_NO, result.getString("USER_NO"));
		if(StringUtil.isValidNew(result.getString("USER_NO"))){
			info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("USER_NO")));
		}else{
			info.set(FieldNames.USER_NAME,"");
		}
		sourceOwner.put(new Integer(i++), info);
	}
} catch (Exception e) {
	// TODO: handle exception
	logger.info("ERROR: exception in getAllSourceOwnerMap ::" + e);
} finally {
	QueryUtil.releaseResultSet(result);
}
return sourceOwner;


}
public SequenceMap getSourceOwnerBrandMap(String leadSource2ID,String assignMentType) 
{
SequenceMap sMap = null;
Info info =null;
ResultSet result=null;
StringBuffer query=null;
int i=0;
if("G".equals("G")){
	query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID,FA.DIVISION_NAME AS OWNER_NAME ,FA.DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN DIVISIONS AS FA ON FA.DIVISION_ID=FSO.BRAND_ID  WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID");
	query.append(" UNION ");
	query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID , FA.DIVISION_NAME AS OWNER_NAME,FA.DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN DIVISIONS AS FA ON FA.DIVISION_ID=FSO.BRAND_ID WHERE REGION_ID='NA' AND BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
}else{

	query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID,FA.BRAND_NAME AS OWNER_NAME ,FA.BRAND_ID AS DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN BRANDS AS FA ON FA.BRAND_ID=FSO.BRAND_ID  WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND FSO.BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID");
	query.append(" UNION ");
	query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID , FA.BRAND_NAME AS OWNER_NAME,FA.BRAND_ID AS DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN BRANDS AS FA ON FA.BRAND_ID=FSO.BRAND_ID WHERE REGION_ID='NA' AND FSO.BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");

}
String[]	param = new String[]{leadSource2ID,assignMentType,leadSource2ID,assignMentType};
try 
{
	sMap=new SequenceMap();
	result = QueryUtil.getResult(query.toString(),param);
	while (result.next()) {
		info=new Info();
		info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
		info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
		info.set(FieldNames.OWNER_ID, result.getString("OWNER_ID"));
		info.set(FieldNames.BRAND_ID, result.getString("DIVISION_ID"));
		if(StringUtil.isValidNew(result.getString("OWNER_ID"))){
			info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("OWNER_ID")));
		}else{
			info.set(FieldNames.USER_NAME,"");
		}
		info.set("type", "brand");
		info.set(FieldNames.OWNER_NAME, result.getString("OWNER_NAME"));
		sMap.put(new Integer(i++)+"brand", info);

	}
} catch (Exception e) {
	logger.info("ERROR: exception in getSourceOwnerTerritoryMap ::" + e);
}
finally
{
	QueryUtil.releaseResultSet(result);
}

return sMap;
}
public SequenceMap getSourceOwnerTerritoryMap(String leadSource2ID,String assignMentType) 
{
SequenceMap sMap = null;
Info info =null;
ResultSet result=null;
int i=0;
StringBuffer query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID, FA.AREA_ID , FA.AREA_NAME AS OWNER_NAME FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN FS_AREAS AS FA ON FA.AREA_ID=FSO.REGION_ID WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID<>'NA' AND BRAND_ID='NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.REGION_ID");
query.append(" UNION ");
query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID, FA.AREA_ID, FA.AREA_NAME AS OWNER_NAME FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN FS_AREAS AS FA ON FA.AREA_ID=FSO.REGION_ID WHERE REGION_ID<>'NA' AND BRAND_ID='NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.REGION_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
String[]	param = new String[]{leadSource2ID,assignMentType,leadSource2ID,assignMentType};
try 
{
	sMap=new SequenceMap();
	result = QueryUtil.getResult(query.toString(),param);
	while (result.next()) {
		info=new Info();
		info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
		info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
		info.set(FieldNames.OWNER_ID, result.getString("OWNER_ID"));
		info.set(FieldNames.AREA_ID, result.getString("AREA_ID"));
		if(StringUtil.isValidNew(result.getString("OWNER_ID"))){
			info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("OWNER_ID")));
		}else{
			info.set(FieldNames.USER_NAME,"");
		}
		info.set("type", "territory");
		info.set(FieldNames.OWNER_NAME, result.getString("OWNER_NAME"));
		sMap.put(new Integer(i++)+"territory", info);

	}
} catch (Exception e) {
	logger.info("ERROR: exception in getSourceOwnerTerritoryMap ::" + e);
}
finally
{
	QueryUtil.releaseResultSet(result);
}

return sMap;
}

public String getMultipleUserName(String userNo) {
	return  getMultipleUserName(userNo,false);
}

public String getMultipleUserName(String userNo,boolean onlyActiveUser) {

return getLeadUserNameWithAlias(userNo,onlyActiveUser);
}

public String getLeadUserNameWithAlias(String userNo)
{
	return getLeadUserNameWithAlias(userNo,false);
}
public String getLeadUserNameWithAlias(String userNo,boolean onlyActiveUser) {
	StringBuilder sbQuery = new StringBuilder();
	sbQuery.append("SELECT USER_FIRST_NAME, USER_LAST_NAME, USER_LEVEL FROM USERS U WHERE U.USER_NO IN(").append(userNo).append(")");
	if(onlyActiveUser)
	{
		sbQuery.append(" AND STATUS = '1'");
	}
	ResultSet result = QueryUtil.getResult(sbQuery.toString(), new String[]{});
	String userName = "";
	while(result.next()) {
		String userLevel = result.getString("USER_LEVEL");
		if(!"".equals(userName))
		{
			userName = userName + ", ";
		}
        if (userLevel != null && userLevel.equals("2")) {
        	userName =userName+ result.getString("USER_FIRST_NAME") + " " + result.getString("USER_LAST_NAME") + "(RU)";
        } else if(Constants.USER_LEVEL_DIVISION.equals(userLevel)) {
        	userName =userName+ result.getString("USER_FIRST_NAME") + " " + result.getString("USER_LAST_NAME") + "(DU)";
        } else {
        	userName =userName+ result.getString("USER_FIRST_NAME") + " " + result.getString("USER_LAST_NAME");
        }
	}
	return userName;
}
public String getFranchiseeListForMuid(String muid,String userType,String userTypeNo)
{
StringBuilder franchiseeList=new StringBuilder();
StringBuilder query=new StringBuilder("SELECT DISTINCT A.FRANCHISEE_NO FROM FRANCHISEE_MUID A, FRANCHISEE B ");       //MUID Concerns P1
query.append(" WHERE B.STATUS IN (1,3) AND A.FRANCHISEE_NO=B.FRANCHISEE_NO AND MUID_VALUE IN('").append(muid).append("')");       //MUID Concerns       //MUID Concerns 21 May
if(StringUtil.isValid(userType))
{
	query.append(" AND A.USER_TYPE='").append(userType).append("' ");       //MUID Concerns P1
}
if(StringUtil.isValid(userTypeNo))
{
	query.append(" AND A.USER_TYPE_NO IN(").append(userTypeNo).append(") ");        //MUID Concerns P1
}
query.append(" ORDER BY B.STATUS DESC ");       //MUID Concerns 15 June
ResultSet result=null;
try
{
	result=QueryUtil.getResult(query.toString(), null);
	while(result.next())
	{
		if(!StringUtil.isValid(franchiseeList.toString()))
		{
			franchiseeList.append(result.getString("FRANCHISEE_NO"));
		}
		else
		{
			franchiseeList.append(",").append(result.getString("FRANCHISEE_NO"));
		}
	}
}
catch(Exception e)
{
	logger.error("Exception inside get FranchiseeName for muid", e);
}
finally
{
	query=null;
	QueryUtil.releaseResultSet(result);
}
	return franchiseeList.toString();
}

public Info getUserInfoForMUID(String fNo,String ownerId, String userNo, boolean viewAllIntranetTasks, String moduleId){
 			StringBuffer  query = new StringBuffer("SELECT  GROUP_CONCAT(DISTINCT FU.FRANCHISEE_USER_NO)  AS FRAN_USER_NO FROM FIM_USERS FU LEFT JOIN USERS U ON(U.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO) LEFT JOIN FRANCHISEE F ON (FU.FRANCHISEE_NO = F.FRANCHISEE_NO)");
 			query.append(" WHERE U.IS_DELETED='N' AND U.STATUS!='0' AND   F.STATUS!='0' ");
 			
 			if(StringUtil.isValidNew(fNo))
 				query.append(" AND FU.FRANCHISEE_NO IN("+fNo+")");
 			//P_CM_B_67834 Start
		     if(ModuleUtil.zcubatorImplemented()){
// 				query.append(" AND F.IS_FRANCHISEE='Y'");
 			query.append(" AND (F.IS_FRANCHISEE='Y' OR (F.IS_STORE='Y'  AND F.IS_STORE_ARCHIVED='N' ))"); 
			}

 			if(!viewAllIntranetTasks && "12".equals(moduleId))
	             {
 				query.append(" AND U.USER_NO = ").append(userNo);
	             }
 			//P_CM_B_67834 End
 	        String franUserNo="";
 	        Info info=new Info();
 			ResultSet result = null;
 			try {
 				result = QueryUtil.getResult(query.toString(), null);
 				if (result.next()) {
 					franUserNo=result.getString("FRAN_USER_NO");
 				}
 				if(StringUtil.isValidNew(franUserNo)){
 				   query = new StringBuffer("SELECT USER_NO,CONCAT(USER_FIRST_NAME,' ',USER_LAST_NAME) AS NAME FROM USERS U WHERE USER_IDENTITY_NO IN("+franUserNo+")");
 				   //P_CM_B_24966 Nishant starts // MUID_PRODUCT_BUG starts
 				   if(ownerId != null && !ownerId.trim().equals("") && !ownerId.equals("-1")){
 					   query.append(" AND U.USER_NO IN (").append(ownerId).append(")");
 			       }
 				   //P_CM_B_24966 ends // MUID_PRODUCT_BUG ends
 				   result = QueryUtil.getResult(query.toString(), null);
 				    while (result.next()) {
 			        	info.set(result.getString("USER_NO"),result.getString("NAME"));
 			        }
 				}
 				
 				

 			} catch (Exception e) {
 				logger.error("Exception caught in getFranchiseeUserNo method::getFranchiseeUserNo");
 			}
 			finally
 			{
 				QueryUtil.releaseResultSet(result);
 			}
 			
 			return info;
 		}
public  Info getFieldManager(String franchiseNo){
	        Info info = new Info();
	        String fieldManager = FieldNames.EMPTY_STRING;
	        UsersDAO usrDao = AdminMgr.newInstance().getUsersDAO();
	        String query ="SELECT F.FRANCHISEE_NO,F.AREA_ID,A.FIELD_MANAGER FROM FRANCHISEE F LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE FRANCHISEE_NO=?";
	        ResultSet rs=null;
	        try{ 
	         rs = QueryUtil.getResult(query, new String[]{franchiseNo});
	         if(rs.next()){
	             fieldManager = rs.getString("FIELD_MANAGER");
	             
	             if(StringUtil.isValid(fieldManager) && !"0".equals(fieldManager)){
	               info = usrDao.getUserDetail(fieldManager);
	               info.set(FieldNames.USER_NO,fieldManager);
	               
	             }
	         
	         }
	        }catch(Exception e){
	            logger.error(e);
	        }
	        finally
	  		{
	  			QueryUtil.releaseResultSet(rs);
	  		}
	        return info;
	    }

public Info getUserDetail(String userNo){
	return getUserDetail(userNo,false);
}
public Info getUserDetail(String userNo,boolean divisional){
	//logger.error("Exception in userNo************************=="+userNo);
		StringBuffer query1 = new StringBuffer();
		StringBuffer query2 = new StringBuffer();
		Info details		= null;

		String franchiseeNo =	"";
		String userName		="";
		String userLevel		="";
		String userType		="";
		String expiryDays	="";
		String timezone		="";
		String userLanguage		="";
		String userPic="";
		String loginUserIp = "";  //P_ADMIN_ENH_LoginIp
		String isAudtor = "";//AUDIT_ENHANCEMENT_CHANGES
        String isBillable = ""; //BB-20141006-154
        String isDaylight = "";   //EN_DAYLIGHT_SAVING_TIME
        String divisionIds = "";
                //P_SO_E_111
                String visibleToStore = "";
                
                if(!StringUtil.isValidWithZero(userNo))//P_B_FS_7902
                	userNo="1";
                
                ResultSet result=null;
                ResultSet rs1=null;
		try
		{
			//query1.append("SELECT FRANCHISEE_NO,USER_LEVEL,USER_TYPE,EXPIRATION_TIME,USER_ID,TIMEZONE,VISIBLE_TO_STORE,USER_PICTURE_NAME FROM USERS WHERE USER_NO=");
            query1.append("SELECT FRANCHISEE_NO,USER_LEVEL,USER_TYPE,EXPIRATION_TIME,USER_ID,TIMEZONE,VISIBLE_TO_STORE,USER_PICTURE_NAME,USER_LANGUAGE,IS_AUDITOR,LOGIN_USER_IP,IS_BILLABLE,IS_DAYLIGHT");
           if(divisional)
            	query1.append(",GROUP_CONCAT(UDM.DIVISION_ID) AS DIVISION_ID");
            query1.append(" FROM USERS");
            if(divisional)
            	query1.append(" LEFT JOIN USERS_DIVISIONS_MAPPING UDM ON UDM.USER_NO=USERS.USER_NO");
            query1.append(" WHERE USERS.USER_NO=");
			query1.append(userNo);
			//logger.error("Exception in query1AAAAAAAAAAAAAA************************=="+query1);
			 result = QueryUtil.getResult(query1.toString(), new Object[]{});
			if(result.next()) {
				franchiseeNo	=	result.getString("FRANCHISEE_NO");
				userLevel		=	result.getString("USER_LEVEL");
				userType		=	result.getString("USER_TYPE");
				expiryDays		=	result.getString("EXPIRATION_TIME");
				userName			=	result.getString("USER_ID");
				timezone			=	result.getString("TIMEZONE");
				userLanguage			=	result.getString("USER_LANGUAGE");
				loginUserIp			=	result.getString("LOGIN_USER_IP");  //P_ADMIN_ENH_LoginIp
				userPic = result.getString("USER_PICTURE_NAME");
                                //P_SO_E_111
                                visibleToStore = result.getString("VISIBLE_TO_STORE");
                isAudtor = result.getString("IS_AUDITOR");//AUDIT_ENHANCEMENT_CHANGES
                isBillable = result.getString("IS_BILLABLE"); //BB-20141006-154
                isDaylight = result.getString("IS_DAYLIGHT");   //EN_DAYLIGHT_SAVING_TIME
                if(divisional)
                	divisionIds = result.getString("DIVISION_ID"); 
			}
			if(userLevel.equals("0") || Constants.USER_LEVEL_DIVISION.equals(userLevel))
			{ 
                              //P_FS_E_3_08_2010 start
				query2.append("SELECT JOB_TITLE,FIRST_NAME,LAST_NAME,ADDRESS,CITY,STATE,ZIPCODE,COUNTRY,COUNTRY_ID,REGION_NO,PHONE1,PHONE2,EMAIL_ID,MOBILE,FAX,BLACKBERRY_EMAIL,SMS_EMAIL,PHONE1_EXTN,PHONE2_EXTN  FROM FRANCHISEE WHERE FRANCHISEE_NO=");
				query2.append(franchiseeNo);
			} ///P_ADM_B_69308 
			else if(userLevel.equals("2") || userLevel.equals("1"))
			{
				query2.append("SELECT JOB_TITLE,REGIONAL_TYPE,FIRST_NAME,LAST_NAME,ADDRESS,CITY,STATE,ZIPCODE,COUNTRY,COUNTRY_ID,REGION_NO,PHONE1,PHONE2,EMAIL_ID,MOBILE,FAX,BLACKBERRY_EMAIL,SMS_EMAIL,PHONE1_EXTN,PHONE2_EXTN  FROM FRANCHISEE WHERE FRANCHISEE_NO=");
				query2.append(franchiseeNo);
			}//P_FS_E_3_08_2010 end
			//logger.error("Exception in query2AAAAAAAAAAAAAA************************=="+query2);
		    //logger.error( " userLevel userLevel "+userLevel );
			logger.info("====QUERYYYYYYYYYYYYYYYY222222222222222222222================>"+query2);
			 rs1 = QueryUtil.getResult(query2.toString(), new Object[]{});
			if(rs1.next()){
				details		= new Info();
                                //P_FS_E_3_08_2010 start
                                details.set("phoneExt1",rs1.getString("PHONE1_EXTN"));
                                details.set("phoneExt2",rs1.getString("PHONE2_EXTN"));
                                //P_FS_E_3_08_2010 end
				details.set(FieldNames.USER_TYPE,userType);
				details.set(FieldNames.EXPIRATION_TIME,expiryDays);
				details.set(FieldNames.USER_NAME,userName);
				details.set(FieldNames.USER_PICTURE_NAME,userPic);
				details.set(FieldNames.JOB_TITLE,rs1.getString("JOB_TITLE"));
				///P_ADM_B_69308 
				if(userLevel.equals("2") || userLevel.equals("1")) {
					details.set(FieldNames.REGIONAL_TYPE,rs1.getString("REGIONAL_TYPE"));
				}
				details.set(FieldNames.FIRST_NAME,rs1.getString("FIRST_NAME"));
				details.set(FieldNames.LAST_NAME,rs1.getString("LAST_NAME"));
				details.set(FieldNames.ADDRESS,rs1.getString("ADDRESS"));
				details.set(FieldNames.CITY,rs1.getString("CITY"));
				details.set(FieldNames.STATE,rs1.getString("STATE"));
				details.set(FieldNames.ZIPCODE,rs1.getString("ZIPCODE"));
				details.set(FieldNames.COUNTRY,rs1.getString("COUNTRY"));
				details.set(FieldNames.COUNTRY_ID,rs1.getString("COUNTRY_ID"));
				details.set(FieldNames.REGION_NO,rs1.getString("REGION_NO"));
				details.set(FieldNames.PHONE1,rs1.getString("PHONE1"));
				details.set(FieldNames.PHONE2,rs1.getString("PHONE2"));
				details.set(FieldNames.MOBILE,rs1.getString("MOBILE"));
				details.set(FieldNames.FAX,rs1.getString("FAX"));
				details.set(FieldNames.EMAIL_ID,rs1.getString("EMAIL_ID"));
				details.set("smsEmail",rs1.getString("SMS_EMAIL"));
				details.set("blackBerryEmail",rs1.getString("BLACKBERRY_EMAIL"));
				details.set(FieldNames.TIMEZONE,timezone);
                //P_SO_E_111
                details.set("visibleToStore",visibleToStore);
                details.set(FieldNames.USER_LANGUAGE,userLanguage);
                details.set(FieldNames.LOGIN_USER_IP,loginUserIp); //P_ADMIN_ENH_LoginIp
                logger.info( " userLevel userLevel "+userLevel ); //P_GEN_E_Exception
                                 ///P_ADM_B_69308 
                details.set("isAudtor",isAudtor);//AUDIT_ENHANCEMENT_CHANGES
                details.set(FieldNames.IS_BILLABLE,isBillable); //BB-20141006-154
                details.set("isDaylight",isDaylight);  //EN_DAYLIGHT_SAVING_TIME
                if(divisional)
                	details.set("division",divisionIds);
                if(userLevel.equals("2") || userLevel.equals("1")) {
                    
					query1=new StringBuffer("");
					query1.append("SELECT AREA_ID FROM AREA_USERS WHERE USER_NO=");
					query1.append(userNo);
					//logger.error( " Executing query ############33333333333333 "+query1 );
					rs1 = QueryUtil.getResult(query1.toString(), new Object[]{});
					if(rs1.next()) {
						details.set(FieldNames.AREA_ID,rs1.getString("AREA_ID"));
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception in getUserDetail==="+e);
                        e.printStackTrace();
		}
		finally
		{QueryUtil.releaseResultSet(result);
		QueryUtil.releaseResultSet(rs1);
		}

		return details;

}//getUserDetail() ends

public  String getFranchiseeUserNoFromFranchiseeNo(String user_no){
			String franNo ="";
			//boolean flag=false;
			if(!StringUtil.isValid(user_no)){
				user_no="";
			}
			String query = "SELECT FRANCHISEE_USER_NO FROM FRANCHISEE_USERS WHERE FRANCHISEE_NO= "+user_no;
			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query, new Object[] { });
			//System.out.println("\n\n\ngetFranchiseeNoByTheaterId>>>>>"+query);
			if (result != null && result.next()) {
				franNo=result.getString("FRANCHISEE_USER_NO");
				
			}
	      return franNo;
		}

public  Info getEmail(String contactOwnerId, String userType) {
 		//String email = "";
 		String query = "";
 		Info info = new Info();

 		if (userType != null && userType.equals("F")) {
 			query = "SELECT A.USER_NO,FU.FIRST_NAME, FU.LAST_NAME, FU.SMS_MAIL,FU.WEB_MAIL,FU.EMAIL_ID FROM FRANCHISEE_USERS FU, FRANCHISEE F,USERS A WHERE FU.FRANCHISEE_NO = F.FRANCHISEE_NO AND A.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND A.STATUS = '1' AND A.USER_NO='" + contactOwnerId + "'";
 		} else if (userType != null && (userType.equals("C") || userType.equals("R"))) {
 			query = "SELECT U.USER_NO,F.FIRST_NAME,F.LAST_NAME,F.SMS_EMAIL AS SMS_MAIL ,F.BLACKBERRY_EMAIL AS WEB_MAIL,F.EMAIL_ID FROM USERS U,FRANCHISEE F  WHERE 1=1     AND U.FRANCHISEE_NO=F.FRANCHISEE_NO AND U.USER_LEVEL IN ('0','2') AND U.STATUS='1' AND U.STATUS <> 0 AND F.STATUS <> 0  AND  U.USER_NO='" + contactOwnerId + "'";
 		}
 	
 		ResultSet result = null;
 		try {
 			
 			Object params[] = null;
 			result = QueryUtil.getResult(query, params);
 			if (result.next()) {
 				info.set(FieldNames.EMAIL_ID, isValidateEmail(result.getString("EMAIL_ID")));
 			}
 			info.set(FieldNames.SMS_MAIL, isValidateEmail(result.getString("SMS_MAIL")));
 			info.set(FieldNames.WEB_MAIL, isValidateEmail(result.getString("WEB_MAIL")));
 			info.set(
 					"NAME",
 					result.getString("FIRST_NAME") + "  " + result.getString("LAST_NAME"));

 		} catch (Exception e) {
 			logger.error("Exception in getting email ::::" + e);
 		}
 		finally
 		{
 			QueryUtil.releaseResultSet(result);
 		}
 		return info;
 	}
public static String isValidateEmail(String mailId) {
    String validMailIds = "";
    if (mailId != null && !"".equals(mailId)) {
        mailId = mailId.replaceAll(";", ",");
        //P_earth_fruit_space start
        mailId = PortalUtils.replaceAll(mailId, ", ", ",");
        mailId = PortalUtils.replaceAll(mailId, " ,", ",");
        //P_earth_fruit_space start end
        String mailid[] = mailId.split(",");
        for (int i = 0; i < mailid.length; i++) {
            if (mailid[i] != null) {
                validMailIds = validMailIds + mailid[i] + ", ";
            }
        }
    }
    // Removing last comma
    if (validMailIds != null && validMailIds.length() > 0) {
        validMailIds = validMailIds.substring(0, validMailIds.length() - 2);
    }
    return validMailIds;
}

}//class ends
