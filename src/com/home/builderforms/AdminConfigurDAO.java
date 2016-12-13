package com.home.builderforms;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.AdminMgr;
import com.home.builderforms.MasterDataMgr;
//import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.home.builderforms.AppException;
import com.home.builderforms.*;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
/**
 *  The AdminConfigurDAO for the Admin link configuration
 * This configuration dao is to retrive data for the admin configuration links.  
 *
 *@author  prakash jodha
 *@created    
 * 
 *P_INT_B_3721  11/01/2012    Nishant Prabhakar       Notification for single word was not coming
 *P_INT_B_4345  2 Feb 2012    Shashank Kukreti    getOffensiveKeyInfo()    Changing the offensive keyword method logic from individual words to whole word. 
 *P_ADMIN_B_4826  29 Feb 2012           Prakash Jodha               bug resolved
 *P_BUG_ID7852    8 feb 2012            Nikhil Singh               correction for the related bug
  BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
 *P_INT_ENH_AUTO_ARCHIVE	20 Sept  Niranjan Kumar	 Methods added for accessing and updating statuses for auto-archive intranet items
 *P_ENH_Pure_Barre_Campaign_Features 	15 jan 2014 	Sachin Agarwal	Enh
 *BOEFLY_INTEGRATION		19/08/2014		Veerpal Singh			Enh				A third party integration with Boefly through REST-API for lead sync.
 *P_ENH_EXISTING_OFFENSIVE   17 nOV 2014     Nazampreet Kaur       Enh         code changed for OFRFENSIVE EMAIL CONTACTS CUSTOMIZATION
 *PROVEN_MATCH_INTEGRATION	 28/11/2014	     Amar Singh		Ens			A third party integration with Proven Match through REST-API for lead sync.
 *BB-20151201-455      		19 Jan 2016			RonakM				Enh		 Active Directory Integration
 */
public class AdminConfigurDAO extends BaseDAO {

    static Logger logger = Logger.getLogger(AdminConfigurDAO.class);

    public AdminConfigurDAO() {
    	
    }
    






    /**
     * this method will return SequenceMap of  all Kaywords (offensive,watch,great) each type of keywords will be in different SequenceMap within a single SequenceMap
     *
     *@returnType SequenceMap  of all keywords in the form of keywords info according to keyword type
     * 
     *
     */
    public SequenceMap getAllOffensiveKeywords() {
        SequenceMap offMap = new SequenceMap();
        SequenceMap watMap = new SequenceMap();
        SequenceMap grtMap = new SequenceMap();
        SequenceMap sMap = new SequenceMap();
        String query = "SELECT KEY_ID, KEYWORD,KEYWORD_TYPE FROM OFFENSIVE_KEYWORDS ORDER BY KEYWORD";
        ResultSet result=null;
        try {
             result = QueryUtil.getResult(query,null);
            while (result != null && result.next()) {
                if (result.getString("KEYWORD_TYPE").equals("O")) {
                    offMap.put(result.getString("KEY_ID"), result.getString("KEYWORD"));
                } else if (result.getString("KEYWORD_TYPE").equals("W")) {
                    watMap.put(result.getString("KEY_ID"), result.getString("KEYWORD"));
                } else if (result.getString("KEYWORD_TYPE").equals("G")) {
                    grtMap.put(result.getString("KEY_ID"), result.getString("KEYWORD"));
                }
            }
            sMap.put("offensive", offMap);
            sMap.put("watch", watMap);
            sMap.put("great", grtMap);
        } catch (Exception ee) {
            //ee.printStackTrace();
            logger.error("Exception while getting OFFENSIVE_KEYWORDS in getAllOffensiveKeywords()...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return sMap;
    }

    /**
     * this method will return SequenceMap of  all Contact Emails for Keyword notifications
     *
     *@return this will return map of all Email contact information stored in the OFFENSIVE_EMAIL_CONTACTS table
     * 
     *
     */
    public SequenceMap getOffensivEmailContacts() {
        Info info = null;
        SequenceMap sMap = new SequenceMap();
        //String query = "SELECT EMAIL_CONTACT_KEY, CONCAT(FIRST_NAME,' ', LAST_NAME) AS NAME,  EMAIL_ID,OFFENSIVE_EMAIL,WATCH_EMAIL,GREAT_EMAIL FROM  OFFENSIVE_EMAIL_CONTACTS ORDER BY NAME";
        //P_ENH_EXISTING_OFFENSIVE STARTS
        String query = "SELECT OEC.EMAIL_CONTACT_KEY, CONCAT(OEC.FIRST_NAME,' ',OEC.LAST_NAME) AS NAME ,CONCAT(U.USER_FIRST_NAME,' ', U.USER_LAST_NAME) AS NAME1,OEC.EMAIL_ID,OEC.FORUM_EMAIL,OEC.FRANBUZZ_EMAIL,OEC.USER_NO FROM OFFENSIVE_EMAIL_CONTACTS OEC left join USERS  U ON U.USER_NO=OEC.USER_NO WHERE OEC.USER_NO=-1 OR ( U.USER_LEVEL=0 AND U.IS_DELETED='N'  AND U.STATUS!=0) ORDER BY CASE WHEN NAME1 IS NULL THEN NAME ELSE NAME1 END";//P_B_50921

         ResultSet result=null;
         try {
             result = QueryUtil.getResult(query,null);
            while (result != null && result.next()) {
                info = new Info();
                info.set(FieldNames.EMAIL_CONTACT_KEY, result.getString("EMAIL_CONTACT_KEY"));
                
                if(StringUtil.isValid(result.getString("NAME")))
                {
                info.set("NAME", result.getString("NAME"));
                }
                else
                info.set("NAME", result.getString("NAME1"));
                
                if(StringUtil.isValidNew(result.getString("USER_NO")))
                {
                	 info.set(FieldNames.EMAIL_ID, NewPortalUtils.getUserEmailId(result.getString("USER_NO")));
                }
                else
                	info.set(FieldNames.EMAIL_ID, result.getString("EMAIL_ID"));
                //P_ENH_EXISTING_OFFENSIVE ENDS
              
                info.set(FieldNames.FRANBUZZ_EMAIL, result.getString("FRANBUZZ_EMAIL"));
                info.set(FieldNames.FORUM_EMAIL, result.getString("FORUM_EMAIL"));
                info.set(FieldNames.USER_NO, result.getString("USER_NO"));  //P_ENH_EXISTING_OFFENSIVE
                sMap.put(result.getString("EMAIL_CONTACT_KEY"), info);
            }
        } catch (Exception ee) {
          logger.error("Exception while getting OFFENSIVE_EMAIL_CONTACTS in getOffensivEmailContacts()...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return sMap;
    }

    /**
     * this method will return SequenceMap of  all Contact Emails Information for a given email contact key which contains email contact name, email id, keywords flag status
     *
     * 
     * 
     *@parameter - emailContactKey
     * 
     *@return this will return Email info from OFFENSIVE_EMAIL_CONTACTS table corresponding to emailContactKey 
     *  
     */
    public Info getOffensivEmailInfo(String emailContactKey) {
        Info info = null;
        //String query = "SELECT EMAIL_CONTACT_KEY,FIRST_NAME, LAST_NAME,  EMAIL_ID,OFFENSIVE_EMAIL,WATCH_EMAIL,GREAT_EMAIL FROM  OFFENSIVE_EMAIL_CONTACTS WHERE EMAIL_CONTACT_KEY=" + emailContactKey;
        String query = "SELECT EMAIL_CONTACT_KEY,FIRST_NAME, LAST_NAME,  EMAIL_ID,FORUM_EMAIL,FRANBUZZ_EMAIL FROM  OFFENSIVE_EMAIL_CONTACTS WHERE EMAIL_CONTACT_KEY=" + emailContactKey;
        ResultSet result=null;
        try {
             result = QueryUtil.getResult(query,null);
            if (result != null && result.next()) {
                info = new Info();
                info.set(FieldNames.EMAIL_CONTACT_KEY, result.getString("EMAIL_CONTACT_KEY"));
                info.set(FieldNames.FIRST_NAME, result.getString("FIRST_NAME"));
                info.set(FieldNames.LAST_NAME, result.getString("LAST_NAME"));
                info.set(FieldNames.EMAIL_ID, result.getString("EMAIL_ID"));
               // info.set(FieldNames.OFFENSIVE_EMAIL, result.getString("OFFENSIVE_EMAIL"));
               // info.set(FieldNames.WATCH_EMAIL, result.getString("WATCH_EMAIL"));
               // info.set(FieldNames.GREAT_EMAIL, result.getString("GREAT_EMAIL"));
                info.set(FieldNames.FRANBUZZ_EMAIL, result.getString("FRANBUZZ_EMAIL"));
                info.set(FieldNames.FORUM_EMAIL, result.getString("FORUM_EMAIL"));

            }
        } catch (Exception ee) {
            logger.error("Exception while getting OFFENSIVE_EMAIL_CONTACTS in getOffensivEmailInfo(String emailContactKey)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return info;
    }

    /**
     * this method will return keyword name coresponding to given keyId  
     *
     *@parameter - keyId  
     * 
     *@return string containing keyword name
     */
    public String getOffensiveKeywordName(String keyId) {
        String query = "SELECT KEYWORD FROM OFFENSIVE_KEYWORDS WHERE KEY_ID=?";
        String offensiveKeyword = null;
        ResultSet result=null;
        try{
         result = QueryUtil.getResult(query, new Object[]{keyId});

        if (result != null && result.next()) {
            offensiveKeyword = result.getString("KEYWORD");
        }
        }catch(Exception ee){
            logger.error("Exception while getting OFFENSIVE_EMAIL_CONTACTS in getOffensivEmailInfo(String emailContactKey)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return offensiveKeyword;
    }

    /**
     * this method will return all the emailtypes and notifications that are in EMAILTYPE_MASTER table according to modules that they belong 
     *
     *   
     * @return map which contains all the information stored in the EMAILTYPE_MASTER according to moduleName
     *
     */
    
    public SequenceMap getEmailTypeByModuleName() {
    	return getEmailTypeByModuleName(true);
    }
    public SequenceMap getEmailTypeByModuleName(boolean isAdmin) {
       // String query = "SELECT IF(ML.MODULE_NAME IS NULL,'Common',ML.MODULE_NAME) AS MODULE_NAME,IF(ML.ABBREVIATED_MODULE_NAME IS NULL,'com',ML.ABBREVIATED_MODULE_NAME) AS ABBREVIATED_MODULE_NAME,CAST(GROUP_CONCAT(DISTINCT EM.EMAIL_TYPE ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS EMAIL_TYPE,CAST(GROUP_CONCAT(DISTINCT EM.EMAIL_TYPE_ID ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS EMAIL_TYPE_ID,CAST(GROUP_CONCAT(EM.IS_NOTIFICATION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS IS_NOTIFICATION, CAST(GROUP_CONCAT(EM.ADMIN_DESCRIPTION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS ADMIN_DESCRIPTION, CAST(GROUP_CONCAT(EM.DESCRIPTION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS DESCRIPTION FROM EMAILTYPE_MASTER EM  LEFT JOIN   MODULE_LIST ML ON ML.MODULE_KEY =EM.MODULE_KEY GROUP BY ML.MODULE_NAME;";  //P_ADMIN_B_4826 
        String query = "SELECT IF(EM.MODULE_KEY IS NULL,'0',EM.MODULE_KEY) AS MODULE_KEY,IF(ML.MODULE_NAME IS NULL,'Common',ML.MODULE_NAME) AS MODULE_NAME,IF(ML.ABBREVIATED_MODULE_NAME IS NULL,'com',ML.ABBREVIATED_MODULE_NAME) AS ABBREVIATED_MODULE_NAME,CAST(GROUP_CONCAT(DISTINCT EM.EMAIL_TYPE ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS EMAIL_TYPE,CAST(GROUP_CONCAT(DISTINCT EM.EMAIL_TYPE_ID ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS EMAIL_TYPE_ID,CAST(GROUP_CONCAT(EM.IS_NOTIFICATION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS IS_NOTIFICATION, CAST(GROUP_CONCAT(EM.ADMIN_DESCRIPTION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS ADMIN_DESCRIPTION, CAST(GROUP_CONCAT(EM.DESCRIPTION ORDER BY EM.EMAIL_TYPE) AS CHAR ) AS DESCRIPTION FROM EMAILTYPE_MASTER EM  LEFT JOIN   MODULE_LIST ML ON ML.MODULE_KEY =EM.MODULE_KEY GROUP BY ML.MODULE_NAME";
        SequenceMap sMap = new SequenceMap();
        ResultSet result=null;
        try{
         result = QueryUtil.getResult(query,null);
        Info emailInfo = null;
        while (result != null && result.next()) {

            emailInfo = new Info();
            emailInfo.set("MODULE_KEY", result.getString("MODULE_KEY"));
            emailInfo.set("MODULE_NAME", result.getString("MODULE_NAME"));
            emailInfo.set("EMAIL_TYPE", result.getString("EMAIL_TYPE"));
            emailInfo.set("EMAIL_TYPE_ID", result.getString("EMAIL_TYPE_ID"));
            emailInfo.set("ABBREVIATED_MODULE_NAME", result.getString("ABBREVIATED_MODULE_NAME"));
            emailInfo.set("IS_NOTIFICATION", result.getString("IS_NOTIFICATION"));
            if(isAdmin)
            emailInfo.set("DESCRIPTION", result.getString("ADMIN_DESCRIPTION"));
            else
            	emailInfo.set("DESCRIPTION", result.getString("DESCRIPTION"));
            sMap.put(result.getString("MODULE_NAME"), emailInfo);

        }
        }catch(Exception ee){
            logger.error("Exception while getting OFFENSIVE_EMAIL_CONTACTS in getOffensivEmailInfo(String emailContactKey)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return sMap;
    }

    /**
     * getEmailTypeCount() return no of records present in the EMAILTYPE_MASTER table
     *
     *   
     *@return No of records present in the  EMAILTYPE_MASTER table
     *
     */
    public int getEmailTypeCount() {
        int count = 0;
        String query = "SELECT Max(EMAIL_TYPE_ID)AS EMAIL_COUNT FROM EMAILTYPE_MASTER";
        ResultSet result=null;
        try{
         result = QueryUtil.getResult(query,null);
        if (result != null && result.next()) {


            count = Integer.parseInt(result.getString("EMAIL_COUNT"));


        }
        }catch(Exception ee){
            logger.error("Exception while getting EMAILTYPE_MASTER in getEmailTypeCount()...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return count;
    }

    /**
     * isEmailTypeUser() this method return true if user have configured notifications
     *
     *    
     * 
     *@parameters userno
     * 
     *@return true if given userno s recods exist in the USER_EMAILTYPE_MAPPING
     */
    public boolean isEmailTypeUser(String userNo) {
        boolean isEmailUser = false;
        ResultSet result=null;
        try {
            String query = "SELECT USER_ID  FROM  USER_EMAILTYPE_MAPPING WHERE USER_ID=" + userNo;
             result = QueryUtil.getResult(query,null);
            if (result != null && result.next()) {


                isEmailUser = true;


            }
        } catch (Exception e) {

            logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in isEmailTypeUser(String userNo)...)", e);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return isEmailUser;
    }

    /**
     *  deleteUserEmailType() this method delete records from USER_EMAILTYPE_MAPPING table before updating 
     *
     * @parameters userno   
     * 
     *@return nonzero value if given userno entries are deleted from the USER_EMAILTYPE_MAPPING table
     * 
     */
    public int deleteUserEmailType(String userNo) {
        int deleteccount = 0;
        try {

            String query = "DELETE FROM  USER_EMAILTYPE_MAPPING WHERE USER_ID=?";
            deleteccount = QueryUtil.update(query.toString(), new String[]{userNo});

        } catch (Exception e) {

            logger.error("Exception in while Deleting from USER_EMAILTYPE_MAPPING deleteUserEmailType(String userNo)...)", e);
        }
        return deleteccount;
    }

    /**
     *getEmailFlagStatus() this method return email flag info corresponding to the given userno where emailflag is checked by the user
     *
     *@parameters userno  
     * 
     *@return emailflag info according to the EMAIL_TYPE_ID of  given userno
     * 
     */
    public Info getEmailFlagStatus(String userNo) {
        String query = "SELECT EMAIL_TYPE_ID,EMAIL_FLAG FROM USER_EMAILTYPE_MAPPING WHERE EMAIL_FLAG='Y' AND USER_ID=" + userNo;
        Info flagInfo = new Info();
        ResultSet result=null;
        try {
         result = QueryUtil.getResult(query,null);

        while (result != null && result.next()) {


            flagInfo.set(result.getString("EMAIL_TYPE_ID"), result.getString("EMAIL_FLAG"));


        }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in getEmailFlagStatus(String userNo)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return flagInfo;
    }

    /**
     *getNotificationFlagStatus() this method return notification flag info corresponding to the given userno where notification flag is checked by the user 
     *
     *@parameters userno 
     * 
     *@return notificationflag info according to the EMAIL_TYPE_ID of  given userno
     * 
     */
    public Info getNotificationFlagStatus(String userNo) {
        String query = "SELECT EMAIL_TYPE_ID,NOTIFICATION_FLAG FROM USER_EMAILTYPE_MAPPING WHERE NOTIFICATION_FLAG='Y' AND USER_ID=" + userNo;
        Info flagInfo = new Info();
        ResultSet result=null;
        try {
         result = QueryUtil.getResult(query,null);

        while (result != null && result.next()) {


            flagInfo.set(result.getString("EMAIL_TYPE_ID"), result.getString("NOTIFICATION_FLAG"));


        }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in getNotificationFlagStatus(String userNo)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return flagInfo;
    }
    public Info getNotificationFlagStatusMap(String userNo) {
        String query = "SELECT EMAIL_TYPE_ID,NOTIFICATION_FLAG FROM USER_EMAILTYPE_MAPPING WHERE USER_ID=" + userNo;
        Info flagInfo = new Info();
        ResultSet result=null;
        try {
         result = QueryUtil.getResult(query,null);

        while (result != null && result.next()) {


            flagInfo.set(result.getString("EMAIL_TYPE_ID"), result.getString("NOTIFICATION_FLAG"));


        }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in getNotificationFlagStatus(String userNo)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return flagInfo;
    }
    
    public HashMap getNotificationFlagStatusMap() {
        String query = "SELECT USER_ID,EMAIL_TYPE_ID,NOTIFICATION_FLAG FROM USER_EMAILTYPE_MAPPING";
        Info flagInfo = null;
        HashMap hMap = new HashMap();
        ResultSet result=null;
        try {
         result = QueryUtil.getResult(query,null);

        while (result != null && result.next()) {

        	if(hMap.containsKey(result.getString("USER_ID")))
        	{
        		((Info)hMap.get(result.getString("USER_ID"))).set(result.getString("EMAIL_TYPE_ID"), result.getString("NOTIFICATION_FLAG"));
        	}else
        	{
        		flagInfo = new Info();
        		flagInfo.set(result.getString("EMAIL_TYPE_ID"), result.getString("NOTIFICATION_FLAG"));
        		hMap.put(result.getString("USER_ID"), flagInfo);
        	}
        }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in getNotificationFlagStatus(String userNo)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return hMap;
    }

    /**
     * getEmailFlagStatus() method is to get whether user with given userno have blocked the email 
     * corresponding to emailtypeId or not, if there is no email configured corresponding to given userno
     * than status will return according to default email settings of userno=777777777 
     *
     *@parameters userno,emailType    
     * 
     *@return true if EMAIL_FLAG is Y corresponding to emailTypeId
     * 
     */
    public boolean getEmailFlagStatus(String userNo, String emailTypeId) {
        boolean flagval = false;
        ResultSet result=null;
        try {
            if(StringUtil.isValid(userNo) && StringUtil.isValid(emailTypeId)){
            String query = " SELECT EMAIL_FLAG FROM USER_EMAILTYPE_MAPPING WHERE EMAIL_TYPE_ID=" + emailTypeId + " AND USER_ID IN (SELECT IF(COUNT(*)=0,'777777777',USER_ID) FROM USER_EMAILTYPE_MAPPING WHERE USER_ID=" + userNo + ")";
             result = QueryUtil.getResult(query,null);

            if (result != null && result.next()) {

                if ("Y".equals(result.getString("EMAIL_FLAG"))) {
                    flagval = true;
                }
              }
            }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of USER_EMAILTYPE_MAPPING in getEmailFlagStatus(String userNo, String emailTypeId)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return flagval;
    }

    /**
     *  getOffensiveKeyInfo() this method return Keywords info(Great,Watch,Offensive) found in the text
     *
     *@parameters text
     * 
     *@return keywords info present in the given text
     */
    public Info getOffensiveKeyInfo(String text) {
        Info offensiveInfo = new Info();
        //P_BUG_ID7852 Start
        TreeSet<String> offensiveWord = new TreeSet<String>(Collections.reverseOrder());
        TreeSet<String> watchWord = new TreeSet<String>(Collections.reverseOrder());
        TreeSet<String> greatWord = new TreeSet<String>(Collections.reverseOrder());
        String keywordd="";
        Iterator itr=null;
      //P_BUG_ID7852 end
        String offensivekeys = "";
        String watchkeys = "";
        String greatkeys = "";
        ResultSet result=null;
        try {
            String query = " SELECT KEY_ID, KEYWORD,KEYWORD_TYPE FROM OFFENSIVE_KEYWORDS ORDER BY KEYWORD";
             result = QueryUtil.getResult(query,null);
            String keyword = "";
            // P_INT_B_4345 starts - removing casesensivity check   
            while (result.next()) {
            	//keyword = result.getString("KEYWORD").toLowerCase().trim();//P_INT_B_3721
            	keyword = result.getString("KEYWORD").trim();//P_INT_B_3721
            	// P_INT_B_4345 ends
            	try {
                //if (text.toLowerCase().matches(keywordPattern)) {
            	// P_INT_B_4345 starts 	- this pattern will check the boundary occurence of keyword in given string with non casesensivity. 
            	  if(Pattern.compile("((?i)\\b"+ Pattern.quote(keyword)+"\\b)").matcher(text.toLowerCase()).find()) {
                	if ("O".equals(result.getString("KEYWORD_TYPE"))) {
                		offensiveWord.add(keyword.toLowerCase());//P_INT_B_3721
                    } else if ("W".equals(result.getString("KEYWORD_TYPE"))) {
                    	watchWord.add(keyword.toLowerCase());//P_INT_B_3721
                    } else if ("G".equals(result.getString("KEYWORD_TYPE"))) {
                    	greatWord.add(keyword.toLowerCase());//P_INT_B_3721
                    }
               // P_INT_B_4345 ends 	
                } 
            } catch(Exception ex) {
            	ex.printStackTrace();
            }
           }
        } catch (Exception ee) {
           logger.error("Exception in execution of select query of OFFENSIVE_KEYWORDS in getOffensiveKeyInfo(String text)...", ee);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        Info masterValues=MasterDataMgr.newInstance().getMasterDataDAO().getMasterInfo(18005);
        if(offensiveWord.size()>0 && masterValues.get("18004").equals("Y")){
        	
            // Collections.sort(offensiveWord,Collections.reverseOrder());
        	//Collections.sort(offensiveWord);
            // for(int i=0; i<offensiveWord.size(); i++){
        	//P_BUG_ID7852 Start	
        	itr=offensiveWord.iterator();
        	while(itr.hasNext()){
        	 keywordd=(String)itr.next(); 
        	 
             offensivekeys = offensivekeys + keywordd + ",";
             //text = text.replaceAll("(?i)" + offensiveWord.get(i), "<font color='RED'>" + offensiveWord.get(i) + "</font>");
             // P_INT_B_4345 starts 	
             text = text.replaceAll("((?i)\\b"+Pattern.quote(keywordd) +"\\b)", "<font color='RED'>" + keywordd + "</font>");
             //System.out.println("after compare--------------------------------------->>>>>"+text);
           //P_BUG_ID7852 end
             // P_INT_B_4345 ends 	
             }
         }
        if(watchWord.size()>0 && masterValues.get("18006").equals("Y")){
            ///Collections.sort(watchWord,Collections.reverseOrder());
            //for(int i=0; i<watchWord.size(); i++){
        	//P_BUG_ID7852 Start
        	itr=watchWord.iterator();
        	while(itr.hasNext()){
        	 keywordd=(String)itr.next();
             watchkeys = watchkeys + keywordd + ",";
             //text = text.replaceAll("(?i)" + watchWord.get(i), "<font color='blue'>" + watchWord.get(i) + "</font>");
             //  P_INT_B_4345 starts 	
             text = text.replaceAll("((?i)\\b" +Pattern.quote(keywordd) +"\\b)", "<font color='blue'>" + keywordd + "</font>");
             // P_INT_B_4345 ends 	
             }//P_BUG_ID7852 end
        }
        if(greatWord.size()>0 && masterValues.get("18008").equals("Y")){
            //Collections.sort(greatWord,Collections.reverseOrder());
            //for(int i=0; i<greatWord.size(); i++) {
        	//P_BUG_ID7852 Start
        	itr=greatWord.iterator();
        	while(itr.hasNext()){
        	 keywordd=(String)itr.next();
             greatkeys = greatkeys + keywordd + ",";
             //text = text.replaceAll("(?i)" + greatWord.get(i), "<font color='Green'>" + greatWord.get(i) + "</font>");
             // P_INT_B_4345 starts 	
             text = text.replaceAll("((?i)\\b" + Pattern.quote(keywordd) +"\\b)", "<font color='Green'>" + keywordd + "</font>");
             // P_INT_B_4345 ends 	
            }//P_BUG_ID7852 end
        }
       // System.out.println("text  "+text);
        offensivekeys = offensivekeys.length() > 0 ? offensivekeys.substring(0, offensivekeys.length() - 1) : "";
        watchkeys = watchkeys.length() > 0 ? watchkeys.substring(0, watchkeys.length() - 1) : "";
        greatkeys = greatkeys.length() > 0 ? greatkeys.substring(0, greatkeys.length() - 1) : "";
        offensiveInfo.set("Offensive", offensivekeys);
        offensiveInfo.set("Watch", watchkeys);
        offensiveInfo.set("Great", greatkeys);
        offensiveInfo.set("text", text);

        return offensiveInfo;
    }

    /**
     * getOffensiveEmailContactsInfo() this method return Email ids present in OFFENSIVE_EMAIL_CONTACTS according to keyword flag
     *
     * 
     * 
     *@return emailids accoring to keywords 
     */
    public Info getOffensiveEmailContactsInfo() {
        Info offensiveEmailInfo = new Info();
//        String offensiveMails = "";
//        String watchMails = "";
//        String greatMails = "";
        String forumMails = "";
        String franBuzzMails = "";
        ResultSet result=null;
        try {
            //String query = " SELECT  EMAIL_ID,OFFENSIVE_EMAIL,WATCH_EMAIL,GREAT_EMAIL,FRANBUZZ_EMAIL,FORUM_EMAIL FROM  OFFENSIVE_EMAIL_CONTACTS";
            String query = " SELECT  OEC.EMAIL_ID,OEC.FRANBUZZ_EMAIL,OEC.FORUM_EMAIL,OEC.USER_NO FROM OFFENSIVE_EMAIL_CONTACTS OEC left join USERS  U ON U.USER_NO=OEC.USER_NO WHERE OEC.USER_NO=-1 OR ( U.USER_LEVEL=0 AND U.IS_DELETED='N'  AND U.STATUS!=0)";

             result = QueryUtil.getResult(query,null);

            while (result.next()) {

              /*  if ("Y".equals(result.getString("OFFENSIVE_EMAIL"))) {
                    offensiveMails = offensiveMails + result.getString("EMAIL_ID") + ",";
                }
                if ("Y".equals(result.getString("WATCH_EMAIL"))) {
                    watchMails = watchMails + result.getString("EMAIL_ID") + ",";
                }
                if ("Y".equals(result.getString("GREAT_EMAIL"))) {
                    greatMails = greatMails + result.getString("EMAIL_ID") + ",";
                }*/
            	
            	
            	
            	
            	//P_ENH_EXISTING_OFFENSIVE starts
            		 if ("Y".equals(result.getString("FRANBUZZ_EMAIL"))) 
            		 {
            			 if(StringUtil.isValidNew(result.getString("USER_NO")))
                         {
            			 franBuzzMails = franBuzzMails + NewPortalUtils.getUserEmailId(result.getString("USER_NO")) + ",";
            		     }
            			 else
            				 franBuzzMails = franBuzzMails + result.getString("EMAIL_ID") + ",";
                 }
                 
                 if ("Y".equals(result.getString("FORUM_EMAIL"))) 
            		 {
                	 if(StringUtil.isValidNew(result.getString("USER_NO")))
                     {
            			 forumMails = forumMails + NewPortalUtils.getUserEmailId(result.getString("USER_NO")) + ",";
            		 }
                	 else
                		 forumMails = forumMails + result.getString("EMAIL_ID") + ",";
            		 }
               //P_ENH_EXISTING_OFFENSIVE ends


            }
        } catch (Exception e) {

            logger.error("Exception in execution of select query of OFFENSIVE_EMAIL_CONTACTS in getOffensiveEmailContactsInfo()...", e);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
//        offensiveMails = offensiveMails.length() > 0 ? offensiveMails.substring(0, offensiveMails.length() - 1) : "";
//        watchMails = watchMails.length() > 0 ? watchMails.substring(0, watchMails.length() - 1) : "";
//        greatMails = greatMails.length() > 0 ? greatMails.substring(0, greatMails.length() - 1) : "";
        forumMails = forumMails.length() > 0 ? forumMails.substring(0, forumMails.length() - 1) : "";
        franBuzzMails = franBuzzMails.length() > 0 ? franBuzzMails.substring(0, franBuzzMails.length() - 1) : "";
//        offensiveEmailInfo.set("offensiveMails", offensiveMails);
//        offensiveEmailInfo.set("watchMails", watchMails);
//        offensiveEmailInfo.set("greatMails", greatMails);
        offensiveEmailInfo.set("forumMails", forumMails);
        offensiveEmailInfo.set("franBuzzMails", franBuzzMails);
        // System.out.println("offensiveEmailInfo  "+offensiveEmailInfo);
        return offensiveEmailInfo;
    }

    /**
     *UpdateMasterData() this method will update DATA_VALUE in MASTER_DATA table according to given MASTER_DATA_ID
     *
     *@parameters  dataValue,DataId
     * 
     *@return nonzero value if MASTER_DATA values updated successfully
     */
    public int UpdateMasterData(String dataValue, String DataId) {
        int count = 0;
        try {

            String query = "UPDATE MASTER_DATA SET DATA_VALUE='" + dataValue + "' WHERE MASTER_DATA_ID=" + DataId;
            count = QueryUtil.update(query.toString(), new String[]{});

        } catch (Exception e) {

            logger.error("Exception in execution of update query of  MASTER_DATA  in UpdateMasterData(String dataValue, String DataId)...)", e);
        }
        return count;
    }
    
    /**
     * This method is used to get the data for THREAD_POST_POPSERVER table for intranet forum watch mails.
     */
    public Info getPopServerData()
    {
    	String query = "SELECT * FROM THREAD_POST_POPSERVER";
    	ResultSet rs=null;
    	Info info = new Info();
    	try
    	{
    	 rs = QueryUtil.getResult(query,null);
    	
    	if(rs.next())
    	{
    		info.set("serverID",rs.getString("SERVER_ID"));
    		info.set("serverIp",rs.getString("SERVER_NAME"));
    		info.set("fromEmail",rs.getString("USER"));
			info.set("password",rs.getString("PASSWORD"));
			info.set("contextPath",rs.getString("CONTEXT_PATH"));
			info.set("jobSchedulerIp",rs.getString("JOB_SCHEDULER_IP"));
			info.set("userId",rs.getString("USER_ID"));
    	}
    	}
    	catch (Exception e) {
            logger.error("Exception in execution  in getPopServerData()", e);

		}
    	 finally
 		{
    		 QueryUtil.releaseResultSet(rs);
 		}
    	return info;
    }
    
    //Nishant Verma
    
    public String getFranbuzzNameData()
    {
    	String query = "SELECT DATA_VALUE FROM MASTER_DATA where DATA_TYPE='4444'";
    	ResultSet rs=null;
    	String franbuzzName="FranBuzz";
    	try
    	{
    	 rs = QueryUtil.getResult(query,null);
    	
    	if(rs.next())
    	{
    		franbuzzName=rs.getString("DATA_VALUE");
    	}
    	}
    	catch (Exception e) {
            logger.error("Exception in execution  in getFranbuzzNameData()", e);

		}
    	 finally
 		{
    		 QueryUtil.releaseResultSet(rs);
 		}
    	return franbuzzName;
    }
    
  //Nishant Verma
    public void updateFranbuzzNameConfiguration(String dataValue, String dataType)
    {
        String sbQuery	= FieldNames.EMPTY_STRING;
        dataValue = dataValue.replaceAll("\'", "\\\\\'");
        dataValue = dataValue.replaceAll("\"", "\\\\\"");
    	
        
        	sbQuery = "UPDATE MASTER_DATA SET DATA_VALUE='"+dataValue+"' WHERE DATA_TYPE = "+dataType ;   
        try
        {
            	QueryUtil.executeInsert(sbQuery);
            	
        }
        catch(Exception e)
        {
            logger.info("Exception in updateFranbuzzNameConfiguration"+e);
        }
    }
    
    
    public void deletePopServerData()
    {
    	String query = "DELETE FROM THREAD_POST_POPSERVER";
    	try
    	{
    		QueryUtil.update(query, new String[]{});
    	}
    	catch(AppException ae)
    	{
    		logger.info("Unable to delete data from THREAD_POST_POPSERVER");
    	}
    }
    
    
    /**
     * to check whether the user is active or not
     * @param userNo
     * @return
     */
    public boolean isActiveUser(String userNo)
    {
    	boolean isActive = false;
    	String query = "SELECT STATUS FROM USERS WHERE USER_NO = "+userNo+" AND STATUS = 1 " ;
    	ResultSet rs=null;
    	try
    	{
    		rs = QueryUtil.getResult(query,null);
    		if(rs.next())
    		{
    			isActive = true;
    		}
    	}
    	catch(Exception e)
    	{
    		logger.info("Exception in isActiveUser======>>>>"+e);
    	}
    	return isActive;
    }
    
	//P_INT_ENH_AUTO_ARCHIVE : STARTS
    
    /**
     * to get the status of intranet Auto archive Settings
     * @param 
     * @return
     */
    public boolean isAutoArchiveEnabled()
    {
    	boolean isEnabled = false;
    	String query = "SELECT STATUS FROM INTRANET_AUTO_ARCHIVE_STATUS WHERE STATUS = 1";
    	ResultSet rs=null;
    	try{
    		rs = QueryUtil.getResult(query,null);
    		if(rs.next()){
    			isEnabled = true;
    		}
    	} catch(Exception e){
    		logger.error("Exception in isAutoArchiveEnabled()======>>>>",e);
    	}finally{
    		QueryUtil.releaseResultSet(rs);
    	}
    	return isEnabled;
    }
    
    /**
     * to update the status of intranet Auto archive Settings 
     * @param status
     * @return
     */
	public int updateAutoArchiveStatus(String status) {
		int updateStatus = 0;
		ResultSet result = null;
		try {
			String selectQuery = "SELECT * FROM INTRANET_AUTO_ARCHIVE_STATUS";
			result = QueryUtil.getResult(selectQuery, new String[]{});
			if(result.next()){
				String updateQuery = "UPDATE INTRANET_AUTO_ARCHIVE_STATUS SET STATUS = ?";
				updateStatus = QueryUtil.update(updateQuery, new String[]{status});
			}else{
				String insertQuery = "INSERT INTO  INTRANET_AUTO_ARCHIVE_STATUS (STATUS) VALUES (?)";
				updateStatus = QueryUtil.executeInsert(insertQuery, new String[]{status});
			}
		} catch (Exception e) {
			logger.error("updateAutoArchiveStatus()======>>>>",e);
		}finally{
    		QueryUtil.releaseResultSet(result);
    	}
		return updateStatus;

	}
	
	
	   /**
     * P_ENH_CM_MASTER PASSWORD
     * @date 10Jan 2014
     * @author Dravit Gupta
     * @return
     */
    public String getCampaignMasterPassword()
    {
        String password = FieldNames.EMPTY_STRING;
        ResultSet result=null;
        try{
            StringBuffer query = new StringBuffer("SELECT AES_DECRYPT(PASSWORD,'pvm@e20') AS PASSWORD FROM CAMPAIGN_MASTER_PASSWORD WHERE ID='1'");
            result = QueryUtil.getResult(query.toString(), null);

            if(result.next()){
                password = result.getString("PASSWORD");
            }
        }catch(Exception e)
        {e.printStackTrace();
            logger.error("Exception in getCampaignMasterPassword>>>>",e);
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }
        return password;
    }
    /**
     * P_ENH_CM_MASTER PASSWORD
     * @date 10Jan 2014
     * @author Dravit Gupta
     * @return
     */
    public void updatecampaignMasterPassword(String password, String lastModifiedBy, String oldPassword) {
    	try {
			String updatePassword = "UPDATE CAMPAIGN_MASTER_PASSWORD SET PASSWORD = AES_ENCRYPT(?,'pvm@e20'), LAST_MODIFIED_BY= ? WHERE ID = 1";
			QueryUtil.update(updatePassword, new String[]{password, lastModifiedBy});
			String insertlogs = "INSERT INTO CAMPAIGN_MASTER_PASSWORD_CHANGE_LOGS (OLD_PASSWORD, NEW_PASSWORD, LAST_MODIFIED_BY) VALUES (AES_ENCRYPT(?,'pvm@e20'), AES_ENCRYPT(?,'pvm@e20') , ?)";
			QueryUtil.executeInsert(insertlogs, new String[]{oldPassword, password, lastModifiedBy});
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	/**
     * to get the status of intranet item which can be Auto archived
     * @param item
     * @return
     */
    public boolean isItemArchived(String item){
    	boolean isItemArchived = false;
    	String query = "SELECT STATUS FROM INTRANET_AUTO_ARCHIVE_ITEMS WHERE STATUS = 1 AND ITEM_NAME = ?";
    	ResultSet rs=null;
    	try{
    		rs = QueryUtil.getResult(query,new String[]{item});
    		if(rs.next()){
    			isItemArchived = true;
    		}
    	} catch(Exception e){
    		logger.error("Exception in isItemArchived(String item)======>>>>",e);
    	}finally{
    		QueryUtil.releaseResultSet(rs);
    	}
    	return isItemArchived;
    }
    
	/**
     * to update the status of intranet items which can be set to auto archive
     * @param status,item,date
     * @return 
     */
	public int updateAutoArchiveItemStatus(String status,String item,String days) {
		int updateStatus = 0;
		ResultSet result = null;
		try {
			String selectQuery = "SELECT * FROM INTRANET_AUTO_ARCHIVE_ITEMS WHERE ITEM_NAME = ?";
			result = QueryUtil.getResult(selectQuery, new String[]{item});
			if(result.next()){
				String updateQuery = "UPDATE INTRANET_AUTO_ARCHIVE_ITEMS SET STATUS = ? , DAYS_TO_ARCHIVE = ? WHERE ITEM_NAME = ?";
				updateStatus = QueryUtil.update(updateQuery, new String[]{status,days,item});
			}else{
				String insertQuery = "INSERT INTO  INTRANET_AUTO_ARCHIVE_ITEMS (STATUS,ITEM_NAME,DAYS_TO_ARCHIVE) VALUES (?,?,?)";
				updateStatus = QueryUtil.executeInsert(insertQuery, new String[]{status,item,days});
			}
		} catch (Exception e) {
			logger.error("updateAutoArchiveItemStatus()======>>>>",e);
		}finally{
    		QueryUtil.releaseResultSet(result);
    	}
		return updateStatus;
	}
	
	/**
     * to get no of days to archive intranet items : used by AutoArchiveItemsThread.java
     * @param item
     * @return days(String)
     */
	public String getAutoArchiveDays(String item) {
		String days = "";
		ResultSet rs = null;
		try {
			StringBuffer selectQuery = new StringBuffer(); 
			selectQuery.append("SELECT DAYS_TO_ARCHIVE FROM INTRANET_AUTO_ARCHIVE_ITEMS WHERE ITEM_NAME = ? AND STATUS = 1");
			rs = QueryUtil.getResult(selectQuery.toString(), new String[]{item});
			if(rs.next()){
				days = rs.getString("DAYS_TO_ARCHIVE");
			}
		} catch (Exception e) {
			logger.error("getAutoArchiveDays(String item)",e);
		}finally{
    		QueryUtil.releaseResultSet(rs);
    	}
		
		return days;
	}
	//P_INT_ENH_AUTO_ARCHIVE : ENDS
	//P_ENH_Pure_Barre_Campaign_Features starts
    public static  SequenceMap getGoogleAnalytiConfigByUser(String userno)
    {
    	SequenceMap Map = new SequenceMap();
    	try
    	{
    		StringBuffer query=null;
    		query  = new StringBuffer(" SELECT CONFIGURATION_ID, PARAMETER_ID, PARAMETER_VALUE FROM GOOGLE_ANALYTICS_CONFIGURATION");
    		ResultSet rs = QueryUtil.getResult(query.toString(), new Object[]{});
    		Info info;
    		while(rs.next())
    		{
    			info=new Info();
    			info.set(FieldNames.CONFIGURATION_ID,  (String)rs.getString("CONFIGURATION_ID"));
    			info.set(FieldNames.PARAMETER_ID,(String)rs.getString("PARAMETER_ID"));
    			info.set(FieldNames.PARAMETER_VALUE,(String)rs.getString("PARAMETER_VALUE"));
    			Map.put(rs.getString("CONFIGURATION_ID"),info);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		logger.info("exception in getGoogleAnalytiConfigByUser() ",e);
    	}
    	return Map;
    }
    public static SequenceMap getGoogleAnalyticsParam(String userno) 
    {
    	SequenceMap map = new SequenceMap();
    	StringBuffer query = null;
    	String isYesChecked = "1";
    	ResultSet rs = null;
    	try{
    		query  = new StringBuffer("SELECT GAP.PARAMETER_ID,HELP_MESSAGE, PARAMETER_NAME, URL_PARAM_NAME, IS_MANDATORY, IF(GAC.PARAMETER_VALUE IS NULL, GAP.PARAMETER_VALUE, GAC.PARAMETER_VALUE) AS PARAMETER_VALUE, IF(GAC.PARAMETER_VALUE IS NULL, '1', '0') AS CONFIG_EXIST FROM GOOGLE_ANALYTICS_PARAMETER GAP LEFT JOIN GOOGLE_ANALYTICS_CONFIGURATION GAC ON GAP.PARAMETER_ID = GAC.PARAMETER_ID");
    		rs = QueryUtil.getResult(query.toString(), new String[]{});
    		Info info;
    		while(rs.next())
    		{
    			info=new Info();
    			info.set(FieldNames.PARAMETER_ID, rs.getString("PARAMETER_ID"));
    			info.set(FieldNames.PARAMETER_NAME, rs.getString("PARAMETER_NAME"));
    			info.set(FieldNames.URL_PARAM_NAME, rs.getString("URL_PARAM_NAME"));
    			if("0".equals(rs.getString("CONFIG_EXIST")) && !"0".equals(isYesChecked)) {
    				isYesChecked = rs.getString("CONFIG_EXIST");
    			}
    			info.set("configExist", rs.getString("CONFIG_EXIST"));
    			info.set(FieldNames.PARAMETER_VALUE, rs.getString("PARAMETER_VALUE"));
    			info.set(FieldNames.HELP_MESSAGE, rs.getString("HELP_MESSAGE"));
    			info.set(FieldNames.IS_MANDATORY, rs.getString("IS_MANDATORY"));
    			map.put(rs.getString("PARAMETER_ID"),info);
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		logger.info("exception in getGoogleAnalyticsParam() ",e);
    	} finally {
    		query = null;
    		rs = null;
    	}
    	map.put("isYesChecked", isYesChecked);
    	return map;
    }
    public static  Info getGoogleAnalyticsParamInfo()
    {
    	Info info=new Info();
    	try
    	{
    		StringBuffer query=null;
    		query  = new StringBuffer(" SELECT PARAMETER_ID, PARAMETER_NAME FROM GOOGLE_ANALYTICS_PARAMETER ");
    		ResultSet rs = QueryUtil.getResult(query.toString(), new Object[]{});
    		while(rs.next())
    		{
    			info.set((String)rs.getString("PARAMETER_ID"),  (String)rs.getString("PARAMETER_NAME"));
    		}
    	}

    	catch(Exception e){
    		e.printStackTrace();
    		logger.info("exception in getGoogleAnalyticsParamInfo() ",e);
    	}
    	return info;
    }
    public static  Info getGoogleAnalyticsParamUrlInfo()
    {
    	Info info=new Info();
    	try
    	{
    		StringBuffer query=null;
    		query  = new StringBuffer(" SELECT PARAMETER_ID, URL_PARAM_NAME FROM GOOGLE_ANALYTICS_PARAMETER ");
    		ResultSet rs = QueryUtil.getResult(query.toString(), new Object[]{});
    		while(rs.next())
    		{
    			info.set((String)rs.getString("PARAMETER_ID"),  (String)rs.getString("URL_PARAM_NAME"));
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		logger.info("exception in getGoogleAnalyticsParamUrlInfo() ",e);
    	}
    	return info;
    }
    public static void GoogleAnalytiConfigDelete(String userno) {
    	try{
    		StringBuffer query=null;
    		query  = new StringBuffer("DELETE FROM GOOGLE_ANALYTICS_CONFIGURATION");
    		//query.append(" WHERE USER_NO = ");
    		//query.append(userno);
    		QueryUtil.update(query.toString(),new  String[]{});
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		logger.info("exception in GoogleAnalytiConfigDelete() ",e);
    	}
    }
    public static void GoogleAnalytiConfigInsert(String userno,String [] paramId,String [] paramValue) {
    	try{
    		StringBuffer query=null;
    		StringBuffer paramQuery=null;
    		if(paramId!=null && paramValue!=null)
    		{
    			for(int i=0;i<paramId.length;i++)
    			{
    				query  = new StringBuffer(" INSERT INTO GOOGLE_ANALYTICS_CONFIGURATION (USER_NO,PARAMETER_ID,PARAMETER_VALUE) VALUES('1',?,?)");
    				QueryUtil.update(query.toString(),new  String[]{paramId[i],paramValue[i]});
    			}
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		logger.info("exception in GoogleAnalytiConfigInsert()  "+e);
    	}
    }
    //P_ENH_Pure_Barre_Campaign_Features ends
    
    public  String getUserConfiguredColorCode(HttpServletRequest request,String filedName)
    {
    	SequenceMap colorConfigTheme=new SequenceMap(); 
    	if(request.getAttribute("colorConfigTheme") !=null && ((SequenceMap)request.getAttribute("colorConfigTheme")).size()>0 ){
    		colorConfigTheme=(SequenceMap)request.getAttribute("colorConfigTheme");
    	}
    	else if(request.getSession().getAttribute("colorConfigTheme") !=null){
    		colorConfigTheme=(SequenceMap)request.getSession().getAttribute("colorConfigTheme");
    	}
    	else{
    		colorConfigTheme=getUsersColorConfiguration("0,"+request.getSession().getAttribute("user_no"));
    		request.getSession().setAttribute("colorConfigTheme",colorConfigTheme);
    	}
    	
    	if(FieldNames.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE.equals(filedName)){
    		return "style='color:"+colorConfigTheme.get(FieldNames.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE)+";text-decoration:none;'";
    	}
    	 return (String)colorConfigTheme.get(filedName);
       
    }
   
    public SequenceMap getUsersColorConfiguration(String userNo){
    	return getUsersColorConfiguration(userNo,"#");
    }
    
    public SequenceMap getUsersColorConfiguration(String userNo,String colorCodeHash){

        SequenceMap colorConfig = new SequenceMap();
        ResultSet rs = null;
        try {
            StringBuffer selectQuery = new StringBuffer();

            //selectQuery.append("SELECT COLOR_CODE FROM USERS_COLOR_CONFIGURATION WHERE USER_NO="+userNo);

            selectQuery.append("SELECT COLOR_CODE FROM USERS_COLOR_CONFIGURATION WHERE USER_NO IN("+userNo+") ORDER BY USER_NO DESC ");

            rs = QueryUtil.getResult(selectQuery.toString(), null);
            if(rs.next()){

                ArrayList<String> userColorCodeList = StringUtil.toArrayList(rs.getString("COLOR_CODE"));
                for (int i = 0; i < userColorCodeList.size(); i++) {//P_B_ADMIN_43805
                    colorConfig.put(FieldNames.userColorConfiguration[i],colorCodeHash+userColorCodeList.get(i));
                }
            }else{
                for (int i = 0; i < FieldNames.userColorConfiguration.length; i++) {
                    colorConfig.put(FieldNames.userColorConfiguration[i],colorCodeHash+FieldNames.userColorCodes[i]);
                }
            }

             //P_B_ADMIN_40367 STARTS
             /*Constants.TOPBAR_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.TOPBAR_COLOR_CODE);
			 Constants.TOPBAR_TEXT_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.TOPBAR_TEXT_COLOR_CODE);
			 Constants.NAVIGATION_BAR_BG_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.NAVIGATION_BAR_BG_COLOR_CODE);
			 Constants.NAVIGATION_BAR_NORMAL_TEXT_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.NAVIGATION_BAR_NORMAL_TEXT_COLOR_CODE);
			 Constants.NAVIGATION_BAR_HIGHLIGTED_TEXT_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.NAVIGATION_BAR_HIGHLIGTED_TEXT_COLOR_CODE);
			 Constants.NAVIGATION_BAR_BORDER_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.NAVIGATION_BAR_BORDER_COLOR_CODE);
			// Constants.SUBMENU_BAR_TEXT_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.SUBMENU_BAR_TEXT_COLOR_CODE);
			 //Constants.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE);
			 Constants.TOPBAR_ICON_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.TOPBAR_ICON_COLOR_CODE);
			 Constants.TOPBAR_ICON_BG_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.TOPBAR_ICON_BG_COLOR_CODE);
			//P_B_ADMIN_40367 ENDS
            // Constants.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE="class='label5' style='color:"+Constants.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE+";text-decoration:none;'";
            //Constants.SUBMENU_BAR_TEXT_COLOR_CODE="class='label4' style='color:"+Constants.SUBMENU_BAR_TEXT_COLOR_CODE+";text-decoration:none;'";
            //Constants.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE="class='label5'";
            //Constants.SUBMENU_BAR_TEXT_COLOR_CODE="class='label4'";
			 
			 Constants.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE_THEME= "style='color:"+"#"+colorConfig.get(FieldNames.SUBMENU_BAR_HIGHLIGTED_TEXT_COLOR_CODE)+";text-decoration:none;'";
	            Constants.SUBMENU_BAR_TEXT_COLOR_CODE_THEME="style='color:"+"#"+colorConfig.get(FieldNames.SUBMENU_BAR_TEXT_COLOR_CODE)+";text-decoration:none;'";
            Constants.ZCUBATOR_HOME_BACKGROUND_COLOR_CODE_THEME = "#"+colorConfig.get(FieldNames.ZCUBATOR_HOME_BACKGROUND_COLOR_CODE);*/
        } catch (Exception e) {
            logger.error("getUsersColorConfiguration=============",e);
        }finally{
            QueryUtil.releaseResultSet(rs);
        }

        return colorConfig;
    }
    
    
    public String getUsersColorConfigurationForTheme(String themeId){
		SequenceMap colorConfig = new SequenceMap();
		String colorCode="";
		ResultSet rs = null;
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			//selectQuery.append("SELECT COLOR_CODE FROM USERS_COLOR_CONFIGURATION WHERE USER_NO="+userNo);
			
			selectQuery.append("SELECT COLOR_CODE FROM COLOR_CONFIGURATION_THEME where THEME_ID='"+themeId+"'");
			
			rs = QueryUtil.getResult(selectQuery.toString(), null);
			
			if(rs.next()){
				colorCode=rs.getString("COLOR_CODE");
			}
		} catch (Exception e) {
			logger.error("getUsersColorConfiguration=============",e);
		}finally{
    		QueryUtil.releaseResultSet(rs);
    	}
		
		return colorCode;
	}


    public static void insertThemeColorConfiguration(String themeName,String userConfiguredColorCode,String themeId,String userNo,boolean fromAdmin,String whichButton,String action)
    {


        try{
            String sQuery="";
            if(StringUtil.isValid(themeName))
            {
            	
	            if("modify".equals(action))
	            {
	                sQuery	=" update COLOR_CONFIGURATION_THEME set THEME_NAME = ? ,COLOR_CODE = ? where THEME_ID="+themeId;
	            }else
	            {
	                sQuery = "INSERT INTO  COLOR_CONFIGURATION_THEME (THEME_ID , THEME_NAME,COLOR_CODE,IS_DEFAULT,USER_NO) VALUES ("+themeId+",?,?,'N',"+(fromAdmin ? "0" :userNo)+")";
	            }
            	
            }
            QueryUtil.executeInsert(sQuery, new String[]{themeName,userConfiguredColorCode});
            //P_B_53631 starts
            String userThemeId="";
            userThemeId=SQLUtil.getColumnValue("USERS_COLOR_CONFIGURATION", "THEME_ID", "USER_NO", userNo);
            if("apply".equals(whichButton) || (StringUtil.isValid(userThemeId) && userThemeId.equals(themeId))){//P_B_53631 ends
            updateUsersColorConfiguration(userNo,userConfiguredColorCode,themeId,fromAdmin);
            }
        }catch(Exception e){
            logger.error("insertThemeColorConfiguration()======>>>>",e);
        }
    }
public static String isDefaultTheme(String themeId,String userNo){
	ResultSet rs = null;
	String defaultTheme="";
	try {
		StringBuffer selectQuery = new StringBuffer();
		selectQuery.append("SELECT IS_DEFAULT,USER_NO FROM COLOR_CONFIGURATION_THEME where THEME_ID='"+themeId+"'" );
		rs = QueryUtil.getResult(selectQuery.toString(), null);
		if(rs.next()){
				defaultTheme =rs.getString("IS_DEFAULT");
				if(!userNo.equals(rs.getString("USER_NO"))){
					defaultTheme="Y";
				}
		}
	} catch (Exception e) {
		logger.error("getUsersColorConfiguration=============",e);
	}finally{
		QueryUtil.releaseResultSet(rs);
	}
	return defaultTheme;
	
}
    public static Info  checkDefaultTheme(String themeId,String userNo){
    	String isDefault="";
    	String sQuery="";
    	Info setThemeInfo=new Info();
    	ResultSet rs = null;
    	
    	try{
    		if(StringUtil.isValid(themeId)){
    			sQuery =" select IS_DEFAULT ,  THEME_NAME,USER_NO from COLOR_CONFIGURATION_THEME  where  THEME_ID="+themeId ;
    		}else{
    		sQuery =" select IS_DEFAULT , THEME_NAME,USER_NO from COLOR_CONFIGURATION_THEME  where  THEME_ID=(select THEME_ID from USERS_COLOR_CONFIGURATION WHERE USER_NO IN("+userNo+")) ORDER BY USER_NO DESC ";
    		}
    		rs = QueryUtil.getResult(sQuery.toString(), null);
    		if(rs.next()){
    			setThemeInfo.set("Is Default" ,rs.getString("IS_DEFAULT"));
    			setThemeInfo.set("ThemeNme" ,rs.getString("THEME_NAME"));
    			setThemeInfo.set("userNo" ,rs.getString("USER_NO"));
    		}
    	}catch(Exception e){
    		logger.error("insertThemeColorConfiguration()======>>>>",e);   
    	}
    	
    	return setThemeInfo;
    	
    }
    public static String getConfiguredTheme(String userNo ){
    	return getConfiguredTheme(userNo,false);
    }
    public static String getConfiguredTheme(String userNo,boolean fromAdmin ){
    	String sQuery="";
    	ResultSet rs = null;
    	String setThemeId="";
    	try{
    		sQuery=" select THEME_ID from  USERS_COLOR_CONFIGURATION WHERE ";
    		if(fromAdmin){
    			sQuery+="USER_NO =0 ";
    		}
    		else{
    			sQuery+="USER_NO IN (0,"+userNo+") ORDER BY USER_NO DESC ";
    		}
    		rs = QueryUtil.getResult(sQuery.toString(), null);
    		if(rs.next()){
    			setThemeId=rs.getString("THEME_ID");
    		}
    	}catch(Exception e){
    		logger.error("insertThemeColorConfiguration()======>>>>",e);
    	}
    	return setThemeId;
    }
	
    public static  Info getColorConfigTheme(String userNo)
    {
    	Info info=new Info();
    	ResultSet rs = null;
    	try{
    		StringBuffer selectQuery = new StringBuffer();
    		selectQuery.append(" select * from COLOR_CONFIGURATION_THEME WHERE USER_NO IN("+userNo+") ");
    		rs = QueryUtil.getResult(selectQuery.toString(), null);
    		while(rs.next()){
    			if(!"0".equals(rs.getString("USER_NO"))){
    				info.set(rs.getString("THEME_ID"),rs.getString("THEME_NAME")+"**");
    			}else{
    			info.set(rs.getString("THEME_ID"),rs.getString("THEME_NAME"));
    			}
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return info;
    }

    /**
     * This method is used to restore logos to default build settings
     */
    public void restoreDefaultSettings()
    {
        String deleteQuery = "DELETE FROM LOGO_DETAILS ";
        String defaultHeight = "50";
        if(ModuleUtil.zcubatorImplemented()) {
        	defaultHeight  = "49";
        }
        String insertQuery = "INSERT INTO LOGO_DETAILS(LOGO_ID, LOGO_WIDTH,LOGO_HEIGHT, LOGO_ROWSPAN, LOGIN_LOGO_WIDTH,LOGIN_LOGO_HEIGHT, LOGO_TYPE, LOGIN_LOGO_NAME, BUILD_LOGO_NAME,SMALL_LOGO_NAME,SMALL_LOGO_WIDTH,SMALL_LOGO_HEIGHT, FAV_ICON ,FAV_ICON_WIDTH, FAV_ICON_HEIGHT,PDF_LOGO_NAME,PDF_LOGO_WIDTH,PDF_LOGO_HEIGHT) VALUES('1','156','"+defaultHeight+"','1','386','72','fms', 'loginLogo.jpg', 'buildLogo.jpg', 'smallLogo.jpg', '50', '50', 'favicon.ico', '150', '40','pdfLogo.jpg','200','56')";
        try {
            QueryUtil.update(deleteQuery, null);
            QueryUtil.update(insertQuery, null);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("Exception in AdminConfigurDAO.restoreDefaultSettings()");
        }
    }


    public static int updateUsersColorConfiguration(String userNo,String userConfiguredColorCode,String themeId,boolean fromAdmin){

        int flag = 0;
        try{
            String updateQuery = "";
            QueryUtil.update("DELETE FROM USERS_COLOR_CONFIGURATION WHERE USER_NO=? ", new String[]{userNo});
            if(fromAdmin)
            {
            	updateQuery = "UPDATE USERS_COLOR_CONFIGURATION SET COLOR_CODE =? , THEME_ID = ? WHERE USER_NO=0";
            	flag = QueryUtil.executeInsert(updateQuery,new String[]{userConfiguredColorCode,themeId});
            }
            else
            {
            	flag = QueryUtil.executeInsert("INSERT INTO USERS_COLOR_CONFIGURATION (THEME_ID,COLOR_CODE,USER_NO) VALUES(?,?,?)",new String[]{themeId,userConfiguredColorCode,userNo});
            }
        } catch (Exception e) {
            logger.error("updateUsersColorConfiguration=============",e);
        }
        return flag;
    }
	
	public Map<String,String> getAllImplementedModules(){
		Map<String, String> moduleMap = new LinkedHashMap<String, String>();
		ResultSet rs = null;
		try {
			StringBuffer selectQuery = new StringBuffer(); 
			selectQuery.append("SELECT MODULE_ID,MODULE_NAME FROM MODULE_LIST WHERE IS_CONFIGURED = 'Y' ORDER BY SORT_ORDER ASC");
			rs = QueryUtil.getResult(selectQuery.toString(), new String[]{});
			while(rs.next()){
				moduleMap.put(rs.getString("MODULE_ID"), rs.getString("MODULE_NAME"));
			}
		} catch (Exception e) {
			logger.error("getAllImplementedModules()",e);
		}finally{
    		QueryUtil.releaseResultSet(rs);
    	}
		return moduleMap;
	}
	
	 //BOEFLY_INTEGRATION : START
	public Info getBoeflyIntegrationDetails()
	{
		Info detailInfo = new Info();;
		ResultSet result = null;
		StringBuilder sbQuery = new StringBuilder("");
		try
		{
			sbQuery.append("SELECT INTEGRATION_ID,BOEFLY_URL,LEAD_SOURCE2_ID,LEAD_SOURCE3_ID,IS_INTEGRATED,SERVICE_URL,SEND_AUTOMATIC_MAIL FROM FS_BQUAL_INTEGRATION_DETAILS");
			result = QueryUtil.getResult(sbQuery.toString(), null);
			if(result!=null && result.next())
			{
				detailInfo.set(FieldNames.INTEGRATION_ID, result.getString("INTEGRATION_ID"));
				detailInfo.set(FieldNames.BOEFLY_URL, result.getString("BOEFLY_URL"));
				detailInfo.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
				detailInfo.set(FieldNames.LEAD_SOURCE3_ID, result.getString("LEAD_SOURCE3_ID"));
				detailInfo.set(FieldNames.IS_INTEGRATED, result.getString("IS_INTEGRATED"));
				detailInfo.set(FieldNames.SERVICE_URL, result.getString("SERVICE_URL"));
				detailInfo.set(FieldNames.SEND_AUTOMATIC_MAIL, result.getString("SEND_AUTOMATIC_MAIL"));
			}
		}catch(Exception e)
		{
			logger.error("Error in getBoeflyIntegrationDetails",e);
		}finally{
			QueryUtil.releaseResultSet(result);
		}

		return detailInfo;
	}
	//BOEFLY_INTEGRATION : END
	//PROVEN_MATCH_INTEGRATION : START
	
	public Info getProvenMatchIntegrationDetails()
	{
		Info detailInfo = new Info();;
		ResultSet result = null;
		StringBuilder sbQuery = new StringBuilder("");
		try
		{
			sbQuery.append("SELECT INTEGRATION_ID,FRANCHISOR_ID,IS_INTEGRATED FROM FS_PROVEN_MATCH_INTEGRATION");
			result = QueryUtil.getResult(sbQuery.toString(), null);
			if(result!=null && result.next())
			{
				detailInfo.set(FieldNames.INTEGRATION_ID, result.getString("INTEGRATION_ID"));
				if(result.getString("FRANCHISOR_ID")!=null)
				{
					detailInfo.set(FieldNames.FRANCHISOR_ID, StringUtil.covertDoubleToString(Double.parseDouble(BigDecimal.valueOf(Double.parseDouble(result.getString("FRANCHISOR_ID"))).toPlainString())));
				}
				detailInfo.set(FieldNames.IS_INTEGRATED, result.getString("IS_INTEGRATED"));
			}
		}catch(Exception e)
		{
			logger.error("Error in getprovenMatchIntegrationDetails",e);
		}finally{
			QueryUtil.releaseResultSet(result);
		}
		return detailInfo;
	}
	
   public Info getLeadDetailsForProvenMatch(String leadId)
	{
		Info detailInfo = new Info();;
		ResultSet result = null;
		StringBuilder sbQuery = new StringBuilder("");
		try
		{
			sbQuery.append("SELECT FIRST_NAME,LAST_NAME,EMAIL_ID,LEAD_STATUS_NAME,LEAD_OWNER_ID FROM FS_LEAD_DETAILS FLD LEFT JOIN FS_LEAD_STATUS FLS ON FLD.LEAD_STATUS_ID=FLS.LEAD_STATUS_ID WHERE LEAD_ID ="+leadId);
			result = QueryUtil.getResult(sbQuery.toString(), null);
			if(result!=null && result.next())
			{
				detailInfo.set(FieldNames.FIRST_NAME, result.getString("FIRST_NAME"));
				detailInfo.set(FieldNames.LAST_NAME, result.getString("LAST_NAME"));
				detailInfo.set(FieldNames.EMAIL_ID, result.getString("EMAIL_ID"));		
				detailInfo.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));		
				detailInfo.set(FieldNames.LEAD_OWNER_ID, result.getString("LEAD_OWNER_ID"));		
			}
		}catch(Exception e)
		{
			logger.error("Error in getLeadDetailsForProvenMatch",e);
		}finally{
			QueryUtil.releaseResultSet(result);
		}
		return detailInfo;
	}
   //PROVEN_MATCH_INTEGRATION : END
   
   	//PROCESS_PEAK_LEAD_MOVE_INTEGRATION START
   	public Map getProcessPeakIntegrationConfigurationDetails(){
		Map valueMap = null;
		ResultSet result = null;
		StringBuilder sbQuery = new StringBuilder("");
		try{
		   sbQuery.append("SELECT INTEGRATION_ID, IS_INTEGRATED, SFTP_HOST_NAME, USER_NAME, USER_PASSWORD, DIRECTORY_PATH, PIPELINE_CATALYST_URL FROM PROCESS_PEAK_INTEGRATION");
		   result = QueryUtil.getResult(sbQuery.toString(), null);
		   if(result!=null && result.next()){
			   valueMap = new HashMap();
			   valueMap.put(FieldNames.INTEGRATION_ID, result.getString("INTEGRATION_ID"));
			   valueMap.put(FieldNames.IS_INTEGRATED, result.getString("IS_INTEGRATED"));
			   valueMap.put("sftpHostName", result.getString("SFTP_HOST_NAME"));
			   valueMap.put(FieldNames.USER_NAME, result.getString("USER_NAME"));
			   valueMap.put("userPassword", result.getString("USER_PASSWORD"));
			   valueMap.put(FieldNames.DIRECTORY_PATH, result.getString("DIRECTORY_PATH"));
			   valueMap.put("pipelineCatalystUrl", result.getString("PIPELINE_CATALYST_URL"));
		   }
		}catch(Exception e){
			logger.error("Error in getProcessPeakIntegrationConfigurationDetails>>>>>>",e);
		}finally{
			QueryUtil.releaseResultSet(result);
		}
		return valueMap;
   	}
   	//PROCESS_PEAK_LEAD_MOVE_INTEGRATION END
   	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_22
   	 */
   	public void updateDecimalPlaceSetting(String datavalue)
	{
			
		int count=0;
				
		String query ="UPDATE MASTER_DATA SET DATA_VALUE= '"+datavalue+"' WHERE DATA_TYPE = '"+MasterEntities.ROUND_DECIMAL_PLACES+"'";
		try {
			count = QueryUtil.update(query, new String[]{});
			//MultiTenancyUtil.getTenantConstants().MAX_ROUNDED_DIGITS=datavalue;
		} catch (Exception e) {		
			logger.info("@@@updateDecimalPlaceSetting--->Exception::"+e);
			e.printStackTrace();
		}
	}
   	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_20
   	 */
	public void removeSignature(String userNo,String signaturenum)
	{
			
		int count=0;
				
		String query ="DELETE FROM MESSAGE_SIGNATURE WHERE SIGNATURE_NO="+signaturenum;
		try {
			count = QueryUtil.update(query, new String[]{});
			CacheMgr.getUserCache().removeSignature(userNo);
		} catch (Exception e) {		
			logger.info("@@@updateDecimalPlaceSetting--->Exception::"+e);
			e.printStackTrace();
		}
	}
	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_20
   	 */
   	public String addModifySignature(String defaultsignature,String userno,String signatureName,String signature,String signaturenum,String action){
   		return "";
   	}
   	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_14
   	 */
   	public void changeSignatureSequence(String checkFranNo,String order,String rowDisplayVal){
   		try {
    		String []temp = checkFranNo.split(",");
    		for(String f_no : temp) {
    			String isSignatureAvailable = SQLUtil.getColumnValue("SOCIAL_NETWORKING_SIGNATURES", "LINK_ID", "FRANCHISEE_NO", f_no);
    			if(!StringUtil.isValid(isSignatureAvailable))
    			{
    				String insertQuery= "INSERT INTO SOCIAL_NETWORKING_SIGNATURES (LINK_TITLE,LINK_URL,DESCRIPTION,IS_ENABLED,ORDER_SEQUENCE,ROW_DISPLAY,FRANCHISEE_NO ) SELECT LINK_TITLE,LINK_URL,DESCRIPTION,0,ORDER_SEQUENCE,ROW_DISPLAY,"+f_no+" FROM SOCIAL_NETWORKING_SIGNATURES WHERE FRANCHISEE_NO=0";
    				QueryUtil.update(insertQuery, null);
    			}
    			//ZCubator_Social_Signatures : added franchisee no in where clause starts
    			NewPortalUtils.reorderTableValues("SOCIAL_NETWORKING_SIGNATURES", "ORDER_SEQUENCE","FRANCHISEE_NO="+f_no,"LINK_TITLE", order.replaceAll("1", ""));
    			SQLUtil.toUpdateColumn("SOCIAL_NETWORKING_SIGNATURES", "ROW_DISPLAY",rowDisplayVal,"1=1 AND FRANCHISEE_NO="+f_no);
    			//ZCubator_Social_Signatures ends
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
   	}
   	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_14
   	 */
   	public void updateSocialNetworkingSignature(String eventType, String franchiseeNo, String franNo,String address,String address2,String city,String state,String country,String zipcode,String foreignType,String fromBoarding,String userNo,String signature,String signEvent,String signatureNo,HttpServletRequest request,Map<String, String> dataMap) throws AppException //ZCubator_Social_Signatures 
    {
    	if(StringUtil.isValid(franNo) && "1".equals(franNo)){
    		AdminMgr.newInstance().getBillingShippingAddressDAO().udateAddress(franNo,address,address2,city,state,country,zipcode,foreignType);  
    	}
		//ZCubator_Unsubscribe_Message ends
    	
    	StringBuffer sb = new StringBuffer();
        String[] val = SQLUtil.getCommaSeperateColumnValues("SOCIAL_NETWORKING_SIGNATURES", "LINK_TITLE", "1=1 AND FRANCHISEE_NO='0'").split(",");
        String description = "";
        String linkUrl = "";
        String orderSequence ="";
        String rowDisplay = "";
    	String isSignatureAvailable = SQLUtil.getColumnValue("SOCIAL_NETWORKING_SIGNATURES", "LINK_ID", "FRANCHISEE_NO", franchiseeNo);
        for (int i = 0; i < val.length; i++) 
        {
            String linkTitle = val[i];
            String param = "";
            if(request!=null){
            	param = request.getParameter(linkTitle);
            }else{
            	param = dataMap.get(linkTitle);
            }
            //ZCubator_Social_Signatures starts
            if("create".equals(eventType))
            {
            	if(request!=null){
            		description = request.getParameter(linkTitle+"_"+FieldNames.DESCRIPTION);
            		linkUrl = request.getParameter(linkTitle+"_"+FieldNames.LINK_URL);
            		orderSequence = request.getParameter(linkTitle+"_"+FieldNames.ORDER_SEQUENCE);
            		rowDisplay = request.getParameter(linkTitle+"_rowDisplay");
            	}else{
            		description = dataMap.get(linkTitle+"_"+FieldNames.DESCRIPTION);
            		linkUrl = dataMap.get(linkTitle+"_"+FieldNames.LINK_URL);
            		orderSequence = dataMap.get(linkTitle+"_"+FieldNames.ORDER_SEQUENCE);
            		rowDisplay = dataMap.get(linkTitle+"_rowDisplay");
            	}
            	String isEnabled = "0";
            	if(StringUtil.isValid(param))
            	{
            		isEnabled = "1";
            	}
            	
            	if(StringUtil.isValid(isSignatureAvailable)) 
            	{
            		if (StringUtil.isValid(param)) 
                    {
                        sb.append("'"+linkTitle+"'");
                        sb.append(",");
                        
                        description = param.split(",")[0];
                        linkUrl = param.split(",")[1];
                        updateSocialNetworkingLinks(linkUrl,description,linkTitle,franchiseeNo);
                    }
                	
                	if (StringUtil.isValid(sb.toString())) 
                    {
                        SQLUtil.toUpdateColumn("SOCIAL_NETWORKING_SIGNATURES", "IS_ENABLED", "0", "LINK_TITLE NOT IN(" + sb.substring(0, sb.lastIndexOf(",")) + ") AND FRANCHISEE_NO="+franchiseeNo);
                    }
                    else
                    {
                        SQLUtil.toUpdateColumn("SOCIAL_NETWORKING_SIGNATURES", "IS_ENABLED", "0", "1=1 AND FRANCHISEE_NO="+franchiseeNo);
                    }
            	} else {
            		String columns[] = {"LINK_TITLE","LINK_URL","DESCRIPTION","IS_ENABLED","ORDER_SEQUENCE","ROW_DISPLAY","FRANCHISEE_NO"};
                	String values[] = {linkTitle,linkUrl,description,isEnabled,orderSequence,rowDisplay,franchiseeNo};
            		SQLUtil.insertRowInTable("SOCIAL_NETWORKING_SIGNATURES", columns, values);
            	}
            }
            else
            {
            	if (StringUtil.isValid(param)) 
                {
                    sb.append("'"+linkTitle+"'");
                    sb.append(",");
                    
            if(param.split(",").length>1)
            {
            			description = param.split(",")[0];
            			 linkUrl = param.split(",")[1];
            }else{
            			if(request!=null){
            				description =request.getParameter(linkTitle+"_"+FieldNames.DESCRIPTION);
            				linkUrl = request.getParameter(linkTitle+"_"+FieldNames.LINK_URL);
            			}else{
            				description =dataMap.get(linkTitle+"_"+FieldNames.DESCRIPTION);
            				linkUrl = dataMap.get(linkTitle+"_"+FieldNames.LINK_URL);
            			}
            }
                  
                    updateSocialNetworkingLinks(linkUrl,description,linkTitle,franchiseeNo);
                }
            	
            	if (StringUtil.isValid(sb.toString())) 
                {
                    SQLUtil.toUpdateColumn("SOCIAL_NETWORKING_SIGNATURES", "IS_ENABLED", "0", "LINK_TITLE NOT IN(" + sb.substring(0, sb.lastIndexOf(",")) + ") AND FRANCHISEE_NO="+franchiseeNo);
                }
                else
                {
                    SQLUtil.toUpdateColumn("SOCIAL_NETWORKING_SIGNATURES", "IS_ENABLED", "0", "1=1 AND FRANCHISEE_NO="+franchiseeNo);
                }
            }
            //ZCubator_Social_Signatures ends
        }   
        //////ZCUB-20150602-154
        if("true".equals(fromBoarding)){
        	updateEmailSignature(userNo,signature,signEvent,signatureNo);
        }
        //////ZCUB-20150602-154
    }
   	/**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_14
   	 */
    private void updateSocialNetworkingLinks(String linkUrl, String description, String linkTitle,String franchiseeNo) throws AppException //ZCubator_Social_Signatures 
    {
        String query = "UPDATE SOCIAL_NETWORKING_SIGNATURES SET LINK_URL=?,DESCRIPTION=?,IS_ENABLED='1' WHERE LINK_TITLE=? AND FRANCHISEE_NO=?";//ZCubator_Social_Signatures

        int rs = QueryUtil.update(query, new String[]{linkUrl.trim(), description.trim(), linkTitle,franchiseeNo});
    }
    /**
   	 * P_ZC_AS_a_FC_Marketing_Module Point_14
   	 */
    private void updateEmailSignature(String userNo ,String signature,String eventType,String signNo) throws AppException 
    {
    	if(StringUtil.isValid(signature))
    	{
    	  if(EventType.CREATE.equals(eventType))
    	  {
    		  	String values[]={userNo,signature,"First Signature","1"};
  			SQLUtil.insertRowInTable("MESSAGE_SIGNATURE", new String[]{"USER_NO","SIGNATURE","SIGNATURE_NAME","DEFAULT_SIGNATURE"}, values);
    	  }
    	  else
    	  {
    		  	String query = "UPDATE MESSAGE_SIGNATURE SET SIGNATURE=? WHERE USER_NO=?  AND SIGNATURE_NO=?";
    		  	QueryUtil.update(query, new String[]{signature.trim(), userNo, signNo});
    	  }
    	}
    }
  //BB-20151201-455  Starts
   	public Map getLDAPIntegrationConfigurationDetails(){
		Map<String,String> valueMap = new HashMap<String,String>();
		ResultSet result = null;
		StringBuilder sbQuery = new StringBuilder("");
		try{
		   sbQuery.append("SELECT INTEGRATION_ID, IS_INTEGRATED,DC1,DC2,DC3,DOMAIN_NAME,AD_USER_ID,AD_USER_PASSWORD,AD_SERVER_IP,AD_BACKUP_SERVER_IP,AD_PORT,NEW_USER_DEFAULT_PASSWORD,NEW_USER_DEFAULT_TIMEZONE,DEFAULT_COUNTRY,DEFAULT_STATE,IS_CLIENT_CONNECTOR_ENABLED,CLIENT_CONNECTOR_URL,LOGIN_FAILED_MESSAGE,DEFAULT_FIRST_NAME,DEFAULT_LAST_NAME,DEFAULT_PHONE1,DEFAULT_CITY,DEFAULT_EMAIL,AD_C_USER_TYPE_STRING,AD_F_USER_TYPE_STRING,AD_R_USER_TYPE_STRING,AD_D_USER_TYPE_STRING,AD_MU_USER_TYPE_STRING,AD_FRAN_REG_DIV_VALUE FROM AD_INTEGRATION");
		   result = QueryUtil.getResult(sbQuery.toString(), null);
		   if(result!=null && result.next()){
			   valueMap.put(FieldNames.INTEGRATION_ID, result.getString("INTEGRATION_ID"));
			   valueMap.put(FieldNames.IS_INTEGRATED, result.getString("IS_INTEGRATED"));
			   valueMap.put("dc1", result.getString("DC1"));
			   valueMap.put("dc2", result.getString("DC2"));
			   valueMap.put("dc3", result.getString("DC3"));
			   valueMap.put("AD_USER_ID", result.getString("AD_USER_ID"));
			   valueMap.put("AD_USER_PASSWORD", result.getString("AD_USER_PASSWORD"));
			   valueMap.put("AD_SERVER_IP", result.getString("AD_SERVER_IP"));
			   valueMap.put("AD_BACKUP_SERVER_IP", result.getString("AD_BACKUP_SERVER_IP"));
			   valueMap.put("AD_PORT", result.getString("AD_PORT"));
			   valueMap.put(FieldNames.DOMAIN_NAME, result.getString("DOMAIN_NAME"));
			   valueMap.put("NEW_USER_DEFAULT_PASSWORD", result.getString("NEW_USER_DEFAULT_PASSWORD"));
			   valueMap.put("NEW_USER_DEFAULT_TIMEZONE", result.getString("NEW_USER_DEFAULT_TIMEZONE"));
			   valueMap.put("DEFAULT_COUNTRY", result.getString("DEFAULT_COUNTRY"));
			   valueMap.put("DEFAULT_STATE", result.getString("DEFAULT_STATE"));
			   valueMap.put("IS_CLIENT_CONNECTOR_ENABLED", result.getString("IS_CLIENT_CONNECTOR_ENABLED"));
			   valueMap.put("CLIENT_CONNECTOR_URL", result.getString("CLIENT_CONNECTOR_URL"));
			   valueMap.put("LOGIN_FAILED_MESSAGE", result.getString("LOGIN_FAILED_MESSAGE"));
			   valueMap.put("DEFAULT_CITY", result.getString("DEFAULT_CITY"));
			   valueMap.put("DEFAULT_FIRST_NAME", result.getString("DEFAULT_FIRST_NAME"));
			   valueMap.put("DEFAULT_LAST_NAME", result.getString("DEFAULT_LAST_NAME"));
			   valueMap.put("DEFAULT_PHONE1", result.getString("DEFAULT_PHONE1"));
			   valueMap.put("DEFAULT_EMAIL", result.getString("DEFAULT_EMAIL"));
			   valueMap.put("AD_C_USER_TYPE_STRING", result.getString("AD_C_USER_TYPE_STRING"));
			   valueMap.put("AD_F_USER_TYPE_STRING", result.getString("AD_F_USER_TYPE_STRING"));
			   valueMap.put("AD_R_USER_TYPE_STRING", result.getString("AD_R_USER_TYPE_STRING"));
			   valueMap.put("AD_D_USER_TYPE_STRING", result.getString("AD_D_USER_TYPE_STRING"));
			   valueMap.put("AD_MU_USER_TYPE_STRING", result.getString("AD_MU_USER_TYPE_STRING"));
			   valueMap.put("AD_FRAN_REG_DIV_VALUE", result.getString("AD_FRAN_REG_DIV_VALUE"));
		   }
		}catch(Exception e){
			logger.error("Error in AdminConfigurDAO.java> getLDAPIntegrationConfigurationDetails>>>>>>",e);
		}finally{
			QueryUtil.releaseResultSet(result);
		}
		return valueMap;
   	}
	//BB-20151201-455  Ends
}
