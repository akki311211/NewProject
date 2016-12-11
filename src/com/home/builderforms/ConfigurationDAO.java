/*
P_E_CREDIT_CARD       23 April 2012    Neeti Solanki                 for adding 'Add Credit Card Payment Gateway' to franchisee user
P_E_OxiFre-20110915-043 Novemebr2012	Yashukant Tyagi					for configuring payment gateway in basebuild
P_E_JSpellSupplementalDictionary	11Jun2013		Vivek Maurya		Enh
P_ADMIN_B_26034			12/09/2013		Anubhav Jain					Sorting is not working for Added By
ZCB-20141017-055                6 Nov 2014              Madhusudan Singh                                Zcubator Automatic Email Dashboard reports for monthly, weekly and Quarterly
ZCUB-20150519-148   			 9 June 2015     		  Divanshu Verma           Combining Vendasta and Google Categories
*/
package com.home.builderforms;


import com.appnetix.app.components.BaseDAO;
import com.appnetix.app.components.adminmgr.manager.AdminMgr;
import com.appnetix.app.components.masterdatamgr.manager.MasterDataMgr;
import com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.exception.AppException;
import com.appnetix.app.portal.SearchConfiguration;
import com.appnetix.app.portal.admin.PasswordInfo;
import com.appnetix.app.portal.calendar.UserTimezoneMap;
import com.home.builderforms.UserRoleMap;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.StrutsUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;



public class ConfigurationDAO extends BaseDAO {

    static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(ConfigurationDAO.class);

    public ConfigurationDAO() {

    }








    public Info getPaymentConfigurationInfo(String moduleName, String subModuleName, String dataValue) {
        Info paymentConfigurationInfo = new Info();
        StringBuffer configQuery = new StringBuffer("");
        configQuery.append("SELECT * FROM ADMIN_CONFIGURATION_TABLE WHERE MODULE_NAME = '" + moduleName + "' AND SUB_MODULE_NAME = '" + subModuleName + "' AND DATA_VALUE = '" + dataValue + "'");
        ResultSet result=null;
        try {
             result = QueryUtil.getResult(configQuery.toString(), new Object[]{});
            while (result.next()) {

                paymentConfigurationInfo.set(FieldNames.CONFIGURATION_ID, result.getString("CONFIGURATION_ID"));
                paymentConfigurationInfo.set(FieldNames.CONFIGURATION_VALUE, result.getString("CONFIGURATION_VALUE"));

            }
        } catch (Exception e) {
            logger.error("Error in getting configuration Info :ConfigurationDAO");
        }
        finally
		{
	
        	QueryUtil.releaseResultSet(result);
		}

        return paymentConfigurationInfo;
    }

    public int updatePaymentMethod(String configurationId, String configurationValue) {
        StringBuffer updateConfigQuery = new StringBuffer("");
        //P_E_OxiFre-20110915-043 added by yashu tyagi
        int i=0;
        updateConfigQuery.append("UPDATE ADMIN_CONFIGURATION_TABLE SET CONFIGURATION_VALUE = '" + configurationValue.trim() + "' WHERE CONFIGURATION_ID = " + configurationId.trim());
        logger.info("update Qudry ===" + updateConfigQuery.toString());
        try{
        	 i = QueryUtil.executeInsert(updateConfigQuery.toString());
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        //ends P_E_OxiFre-20110915-043
        return i;
    }

    
    
    /**
	 *            
	 * @return String franNo that contain all the franchisee no in Table CT_CREDIT_CARD_CONFIGURATION.
     */
    public String getFranchiseeNumbers() {
        StringBuffer query = new StringBuffer("");
         String franNo="";
        query.append(" SELECT FRANCHISEE_NO FROM CT_MARCHANT_CONFIGURATION ");
        ResultSet result=null;
        try {
           
             result = QueryUtil.getResult(query.toString(), null);
            while (result.next()) {
                franNo=franNo+","+result.getString("FRANCHISEE_NO");
            }
        } catch (Exception e) {
            logger.info("Exception in getCreditCardConfigurationDetails function " + e);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return franNo;
    }
    
    /**
	 *            
	 * @return String franNo that contain all the franchisee no in Table CT_CREDIT_CARD_CONFIGURATION.
     */
     public String containCorporateType(String franchiseeNo){
         StringBuffer query = new StringBuffer("");
         String containCorporate="";
        query.append(" SELECT FRANCHISEE_NO,ACCOUNT_TYPE FROM CT_MARCHANT_CONFIGURATION WHERE FRANCHISEE_NO = "+franchiseeNo+" AND ACCOUNT_TYPE like 'Y%'");
        ResultSet result=null;
        //P_E_OxiFre-20110915-043 starts
        try {
           
             result = QueryUtil.getResult(query.toString(), null);
            while (result!=null && result.next()) {
                if(result.getString("FRANCHISEE_NO").equals(franchiseeNo) ){
                    containCorporate="Y";
                    }else{
                        containCorporate="N";
                    }
                    
                }
            //P_E_OxiFre-20110915-043 ends
        } catch (Exception e) {
            logger.info("Exception in containCorporate function " + e);
        }
        finally
		{
        	QueryUtil.releaseResultSet(result);
		}
        return containCorporate;
    }
   //P_E_CREDIT_CARD modified by neeti starts
     public boolean getCreditCardConfigurationAvailability(String franchiseeNo) {
         StringBuffer query = new StringBuffer("");
        boolean flag=false;
         query.append(" SELECT MARCHANT_ID FROM CT_MARCHANT_CONFIGURATION CT  where FRANCHISEE_NO='"+franchiseeNo+"'");
         ResultSet result=null;
         try {
         	
              result = QueryUtil.getResult(query.toString(), null);
             Info info = null;
             if(result.next()) {
            	 flag=true;
             }
         } catch (Exception e) {
             logger.info("Exception in getCreditCardConfigurationDetails function " + e);
         }
         finally
 		{
        	 QueryUtil.releaseResultSet(result);
 		}
                 return flag;
     }
   //P_E_CREDIT_CARD modified by neeti starts
    
     /**
      * P_E_JSpellSupplementalDictionary
      * @author Vivek Maurya
      * @date 11Jun2013
      * Returns words for summary
      */
     public SequenceMap getAllWordsMap(String sortCol, String sortOrder, final String pageID, 
    		 final String userNo, final String searchWord, final String key) {
    	 BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
 			SequenceMap resultMap = null;
    		Info tempInfo = null;
    		
    		String selectAlT= GetAllIdsBean.newInstance().getSelectAll(); 
    		String checkedIds="";
    		
    		StringBuffer countQuery = new StringBuffer("SELECT COUNT(WORD_ID) AS COUNT FROM SPELL_CHECK_WORDS WHERE 1=1 ");
    		StringBuffer selectAllQuery = new StringBuffer("SELECT DISTINCT WORD_ID FROM SPELL_CHECK_WORDS WHERE 1=1  ");
    		
    		StringBuffer query = new StringBuffer("SELECT WORD_ID,WORD_NAME,");
    		query.append("ADD_DATE,ADD_TIME,LAST_MODIFY_DATE,LAST_MODIFY_TIME,ADDED_BY ");
    		query.append("FROM SPELL_CHECK_WORDS SCW LEFT JOIN USERS U ON U.USER_NO=SCW.ADDED_BY  ");//P_ADMIN_B_26034 
    		query.append("WHERE 1=1 AND U.USER_NO=SCW.ADDED_BY ");//P_ADMIN_B_26034 
    		
    		if(StringUtil.isValid(searchWord)) {
    			countQuery.append("AND WORD_NAME LIKE '%").append(searchWord).append("%' ");
    			selectAllQuery.append("AND WORD_NAME LIKE '%").append(searchWord).append("%' ");
    			query.append("AND WORD_NAME LIKE '%").append(searchWord).append("%' ");
    		}
    		
    		if(StringUtil.isValid(key)) {
    			countQuery.append("AND WORD_NAME REGEXP \'^[").append(key).append("]\'");
    			selectAllQuery.append("AND WORD_NAME REGEXP \'^[").append(key).append("]\'");
    			query.append("AND WORD_NAME REGEXP \'^[").append(key).append("]\'");
    		}
    		
    		if(!StringUtil.isValid(sortOrder)) {
    			sortOrder = " ASC";
    		}
    		
    		if(!StringUtil.isValid(sortCol)) {
    			sortCol = " WORD_NAME ";
    			sortOrder = Constants.LBL_SPACER;
    		}
    		
    		if(StringUtil.isValid(sortCol) && FieldNames.WORD_NAME.equals(sortCol)) {
    			sortCol = " WORD_NAME ";
    		} else if(StringUtil.isValid(sortCol) && FieldNames.ADD_DATE.equals(sortCol)) {
    			sortCol = " ADD_DATE "+sortOrder+",ADD_TIME "+sortOrder;
    			sortOrder = FieldNames.EMPTY_STRING;
    		} else if(StringUtil.isValid(sortCol) && FieldNames.ADDED_BY.equals(sortCol)) {
    			sortCol = " U.USER_FIRST_NAME "+sortOrder+",U.USER_LAST_NAME  ";//P_ADMIN_B_26034 
    		}

    		query.append(" ORDER BY").append(sortCol).append(sortOrder);
    		
    		if (StringUtil.isValid(pageID)) {
    			int page = 0;

	            try {
	                page = Integer.parseInt(pageID);
	            } 
	            catch (NumberFormatException e) {
	                logger.error("NumberFormatException: ", e);
	            }

	            if (page > 1) {
	            	int limit = Constants.RECORDS_PER_PAGE * (Integer.parseInt(pageID) - 1);
	            	query.append(" LIMIT ").append(limit).append(" ,  " + Constants.RECORDS_PER_PAGE);
	            } else if (page == 1) {
	            	query.append(" LIMIT 0").append(" ,  " + Constants.RECORDS_PER_PAGE);
	            }
    		}

    		ResultSet result = null;
    		try {
    			logger.info("getAllWordsMap query=====================>>"+query);
    			logger.info("getAllWordsMap countQuery query==========>>"+countQuery);
    			result = QueryUtil.getResult(countQuery.toString(), null);
    			if(result!=null && result.next()) {
    				resultMap = new SequenceMap();
    				
    				if(StringUtil.isValidNew(result.getString("COUNT")) 
    						&& !FieldNames.ZERO.equals(result.getString("COUNT"))) {
    					resultMap.put(FieldNames.TOTAL_RECORDS, result.getString("COUNT"));
    				}
    				result = QueryUtil.getResult(query.toString(), null);
    	    			
    				if(result!=null) {
    					int dataCount = 0;
    					while(result.next()) {
    						tempInfo = new Info();
    						tempInfo.set(FieldNames.WORD_ID,result.getString("WORD_ID"));
    						tempInfo.set(FieldNames.WORD_NAME,result.getString("WORD_NAME"));
    						tempInfo.set(FieldNames.ADDED_BY,NewPortalUtils.getContactOwner(result.getString("ADDED_BY")));
    	    					
    						String rTime = result.getString("ADD_TIME");

    						if(!StringUtil.isValid(rTime)) {
    							rTime = "00:00:00";
    						}
    						String userTimeZone = UserTimezoneMap.newInstance().getTimezone(userNo);

    						String enquiryDateTime = result.getString("ADD_DATE")+ " "+ PortalUtils.convertTime(rTime);
    						enquiryDateTime = TimeZoneUtils.performUTCConversion(_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone,enquiryDateTime, _baseConstants.DISPLAY_FORMAT_HMA,_baseConstants.DISPLAY_FORMAT_HMA);
    						
    						tempInfo.set(FieldNames.ADD_DATE,enquiryDateTime);
    	    					
    						resultMap.put(dataCount++, tempInfo);
    					}
    				}
    				
    				
    				if("true".equals(selectAlT)){
    					result	= QueryUtil.getResult(selectAllQuery.toString(), null);

    					while (result.next()) {

    						if(StringUtil.isValid(checkedIds))
    							checkedIds+=","+result.getString("WORD_ID");
    						else
    							checkedIds=result.getString("WORD_ID");
    					}
    				
    					resultMap.put("CHECKEDIDS", checkedIds);
    				}
    				
    			}
    			
    		}
    		catch(Exception e) {
    			logger.error("Exception in getAllWordsMap method==========>>",e);
    		} finally {
    			countQuery = null;
    			query = null;
    			QueryUtil.releaseResultSet(result);
    		}
    		return resultMap;
 	}
     
     
     /**
      * P_E_JSpellSupplementalDictionary
      * @author Vivek Maurya
      * @date 11Jun2013
      * @return Updates supplement dictionary txt file
      */
     public boolean updateSupplementalDictionary(final String contextPath) {
    	 boolean updateSuccess = true;
    	 StringBuilder allWords = getAllDictionaryWords();
    	 File dictionaryFile = null;
    	 RandomAccessFile raf = null;
    	 try {
    		 String filePath = "/home/tomcat/supplementalDictionary"+contextPath+"_"+MultiTenancyUtil.getTenantName()+".txt";
    		 dictionaryFile = new File(filePath);
 			 raf= new RandomAccessFile(dictionaryFile,"rw");
 			 raf.writeBytes(allWords.toString());
 		 } catch (Exception e) {
 			 updateSuccess = false;
 			 logger.error("Exception in updateSupplementalDictionary", e);
 		 } finally {
 			dictionaryFile = null;
 			allWords = null;
 			if(raf!=null) {
 				try {
					raf.close();
				} catch (IOException e) {
					logger.error("Exception in closing RandomAccessFile Stream", e);
				} finally {
					raf = null;
				}
 			}
 		 }
 		 
    	 return updateSuccess;
     }
     
     /**
      * P_E_JSpellSupplementalDictionary
      * @author Vivek Maurya
      * @date 11Jun2013
      * @return All words
      */
     private StringBuilder getAllDictionaryWords() {
    	 final String query = "SELECT WORD_NAME FROM SPELL_CHECK_WORDS ORDER BY WORD_NAME ASC";
    	 StringBuilder wordBuilder = new StringBuilder();
    	 ResultSet result = null;
    	 try {
    		 result = QueryUtil.getResult(query, null);
    		 while(result.next()) {
    			 wordBuilder.append(result.getString("WORD_NAME")).append("\n");
    		 }
    	 } catch(Exception e) {
    		 logger.error("Exception in getAllDictionaryWords", e);
    	 } finally {
    		 QueryUtil.releaseResultSet(result);
    	 }
    	 return wordBuilder;
     }
     
     /**
      * P_E_JSpellSupplementalDictionary
      * @author Vivek Maurya
      * @date 11Jun2013
      * To insert word change history
      */
     public void insertWordChangeHistory(String from, String to, String changedBy, 
 			String dateChanged, String fromWhere) {
 		final String query = "INSERT INTO SPELL_CHECK_WORDS_CHANGE_HISTORY(CHANGED_BY_USER_NO,VALUE_FROM,VALUE_TO,DATE_CHANGED,FROM_WHERE) VALUES (?,?,?,?,?)";
 		String[] paramArray = {changedBy,from,to,dateChanged,fromWhere};
 		try {
 			QueryUtil.update(query, paramArray);
 		} catch(Exception e) {
 			logger.error("Exception in insertWordChangeHistory method==========>>",e);
 		} finally {
 			paramArray = null;
 		}
 	}
     
     /**
      * P_E_JSpellSupplementalDictionary
      * @author Vivek Maurya
      * @date 11Jun2013
      * To add new words to dictionary from spell checker
      */
     public void addDictionaryWords(String[] wordsArray, String userNo) {
    	 List<String> batchList = new ArrayList<String>();

    	 Date date = null;
    	 StringBuilder queryBuffer = null;
    	 List<String> duplicateRemoval=new ArrayList<String>();
    	 ResultSet rs = QueryUtil.getResult("SELECT MAX(WORD_ID) AS MAX_KEY FROM SPELL_CHECK_WORDS", new String[]{});
    	 int maxID=Integer.parseInt(IDGenerator.getNextKey("SPELL_CHECK_WORDS", "WORD_ID"));
    	 if(rs.next()){
    		 maxID=rs.getInteger(1);
    	 }
    	 for(String word : wordsArray) {
    		 if(StringUtil.isValid(word)) {
    			 word=word.trim();
    			 if(duplicateRemoval.isEmpty() || !duplicateRemoval.contains(word.toLowerCase())){
    			 duplicateRemoval.add(word.toLowerCase());
    			 }
    			 else{
    				 continue;
    			 }
    			 maxID++;
    			 date = new Date();
    			 String timeFormat = new SimpleDateFormat("HH:mm:ss").format(date);
    			 String currDate = DateUtil.getDbDate(DateUtil.getCurrentDateAsString());
    			 queryBuffer = new StringBuilder("INSERT INTO SPELL_CHECK_WORDS (WORD_ID,WORD_NAME,ADD_DATE,ADD_TIME,LAST_MODIFY_DATE,LAST_MODIFY_TIME,ADDED_BY) VALUES (").append(maxID).append(",");
    			 queryBuffer.append("'").append(word).append("','").append(currDate).append("','").append(timeFormat).append("','").append(currDate).append("','").append(timeFormat).append("','").append(userNo).append("')");
    			 
    			 batchList.add(queryBuffer.toString());
    		 }
    	 }
    	 
    	 if(!batchList.isEmpty()) {
    		 try {
				QueryUtil.batchUpdate(batchList, null);
			} catch (AppException e) {
				e.printStackTrace();
			}
    	 }
     }
     public void deleteDictionaryWord(String contextPath,String wordID){
    	 
    	 String whereClause = " WORD_ID IN("+wordID+")";
			SQLUtil.deleteRecord("SPELL_CHECK_WORDS", whereClause);
			BaseConstants _baseConstants=MultiTenancyUtil.getTenantConstants();
			//Updating supplemental dictionary on word deletion
			
			AdminMgr.newInstance().getConfigurationDAO().updateSupplementalDictionary(contextPath);
			//ZCUB-20140417-056 starts 
			String clientId = NewPortalUtils.getColumnFromTable("SOCIAL_MEDIA_CLIENTS","CLIENT_ID","1","1");
			
			int statusCode = 0;
			if(_baseConstants.FB_ENABLED){
				String socialMediaURL = NewPortalUtils.getColumnFromTable("SOCIAL_MEDIA_CLIENTS","INTERNAL_SOCIAL_URL","1","1");
				HttpClient client = new HttpClient();
				PostMethod method = null;
				method = new PostMethod(socialMediaURL+"/createClientDictionaryTxt.jsp");
				method.addParameter("client_id", clientId);
				try {
					if(StringUtil.isValidNew(clientId))
					statusCode = client.executeMethod(method);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					logger.info(" dictionary update for social media ");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.info(" dictionary update for social media ");
				}
				if(statusCode == HttpStatus.SC_OK){
					logger.info(" dictionary update for social media ");
				}
			}
			//ZCUB-20140417-056 ends 
    	 
     }
     
     
   //BB-20150427-345  starts
     public void loadConfiguration(){
    	 logger.info("------------- In ConfigurationDao > loadConfiguration ----------------");
    	 MultiTenancyUtil.getTenantConstants().setSearchFieldMap();//HomeTeam-20160603-584
	 }
     
     public Map<String,String[]> getConfiguredSearchFilters(String module,boolean onlyConfiguredFilters){
    	 Map<String,String[]> fieldMap = BaseUtils.getNewLinkedHashMapWithKeyValueType();
 		String[] displayFieldArr = null;
    	 StringBuilder query = new StringBuilder("SELECT FIELDS,DISPLAY_NAME,CRITERIA FROM SMART_SEARCH WHERE MODULE='"+module+"' AND IS_ACTIVE='yes' ");
    	 if(onlyConfiguredFilters)
    	 query.append( " AND IS_CONFIGURED = 'yes'" );
 		ResultSet result = null;
 		try{
	 		 result = QueryUtil.getResult(query.toString(), null);
	 		if(result!=null) {
	 			while(result.next()) {
	 				displayFieldArr = new String[]{result.getString("DISPLAY_NAME"),result.getString("CRITERIA")};
					fieldMap.put(result.getString("FIELDS"),displayFieldArr );
	 			}
	 		}
 		} catch(Exception e) {
 			logger.error("Exception in getConfiguredSearchFilters-----------  ",e);
 		}finally{
 			QueryUtil.releaseResultSet(result);
 		}
 		return fieldMap;
     }
   //BB-20150427-345 ends
     
   //BB-20150427-346  starts
     public void updateFIMSearchMenu(String configuredCriteria){
    	 SQLUtil.updateTableValue("FIM_SEARCH_CRITERIA", "SEARCH_CRITERIA", configuredCriteria, "MODULE_ID", "1");
    	 SearchConfiguration.getInstance().setFIMSearchSubMenu();
     }
   //BB-20150427-346  ends
     
   //BB-20150427-345 starts
     public void updateSearchCriteria(Map<String,String> paramMap){
    	 logger.info("----------------- In ConfigurationDao > updateSearchCriteria -------------");
    	 String module = paramMap.get("module");
    	 paramMap.remove("module");
    	 int totalFlds = Integer.parseInt(paramMap.get("totalFlds"));
    	 paramMap.remove("totalFlds");
    	 StringBuilder query = null;

    	 List<String> queryList = new ArrayList<String>();
    	 try{
    		 queryList.add("UPDATE SMART_SEARCH SET IS_CONFIGURED= 'no' WHERE MODULE='"+module+"'");
    		 for(int i=0; i<totalFlds;i++) {

    			 query = new StringBuilder();
    			 query.append("UPDATE SMART_SEARCH SET CRITERIA = '").append(paramMap.get("criteriaCombo_"+i)).append("' , IS_CONFIGURED= 'yes' WHERE FIELDS='").append(paramMap.get("fieldsCombo_"+i)).append("'");
    			 queryList.add(query.toString());
    		 }


    		 QueryUtil.batchUpdate(queryList, null);
    		 logger.info("-------------- Updating configurations----------------");
    		 SearchConfiguration.getInstance().loadConfiguration();
    	 } catch (Exception e){
    		 logger.error("Exception in updateSearchCriteria-----------  ",e);
    	 }finally{
    		 query = null;
    	 }
     }
   //BB-20150427-345 ends
     
//BB-20141017-177 starts     
     public Map getDisplayColumn(String userNo)throws Exception{
    	 Map<String,HashMap> returnMap=NewPortalUtils.getNewLinkedHashMapWithKeyValueType();
    	 
    	 UserRoleMap userRoleMap = (UserRoleMap) StrutsUtil.getHttpSession().getAttribute("userRoleMap");//P_CM_B_68658    	 
    	 
    	 String query="SELECT GROUP_CONCAT(DISPLAY_NAME ORDER BY SD.ORDER_SEQUENCE) AS DISPLAY,IF(IS_SELECTED='0','0','1') AS STATUS , SP.MODULE_ID,PAGE_NAME,SP.MODULE_NAME  AS MODULE_NAME FROM SUMMARY_DISPLAY SD INNER JOIN SUMMARY_PAGES SP ON SP.MODULE_ID=SD.MODULE_ID WHERE 1=1 ";
    	 if(StringUtil.isValid(userNo)){
    		query+=" AND USER_NO="+userNo;
    	 }
    	 if(!ModuleUtil.schedulerImplemented()){
    		 query+=" AND DISPLAY_NAME!='Transaction Status'";
    	 }
    	 
    	 if(ModuleUtil.zcubatorImplemented()) {
    		 query+=" AND DISPLAY_VALUE NOT IN ('fimTtEntityName','reportPeriodStartDate','storeName') ";
         }
    	 query += " AND SD.IS_ACTIVE = 'yes'"; //For activate Fields as custom fields can get activated or deactivated. //BB-20141017-177
    	 
    	 //HEAT_METER_CONFIG_ISSUE starts
    	 if("no".equalsIgnoreCase((String)MultiTenancyUtil.getTenantContext().getAttribute("heatMeter"))){
    		 query+=" AND DISPLAY_VALUE NOT IN ('heatIndexScore') ";
    	 }
    	 //HEAT_METER_CONFIG_ISSUE ends
    	 //P_CM_B_68658 Starts
    	 if(!userRoleMap.isPrivilegeInMap("/companySummary")){
    		 
    		 query+=" AND SD.MODULE_ID NOT IN (5) AND DISPLAY_NAME NOT IN('Account' ,'Industry')";
    	 }
    	//P_CM_B_68658 End
    	 
    	 query+=" GROUP BY STATUS,MODULE_ID ORDER BY SP.ORDER_SEQUENCE ASC";

    	 ResultSet rs = QueryUtil.getResult(query,null);
    	 
    	 HashMap moduleWiseMap=null;
    	 String pageName=null;
    	 while(rs.next()){
    		 if(returnMap.containsKey(rs.getString("MODULE_ID"))){
    			 moduleWiseMap=returnMap.get(rs.getString("MODULE_ID"));
    		 }
    		 else{
    			 moduleWiseMap=NewPortalUtils.getNewHashMapWithKeyValueType();
    			 pageName=rs.getString("PAGE_NAME");
    			 
    			 if("43".equals(rs.getString("MODULE_ID")))
    				 pageName=pageName.replaceAll("Division Users", MultiTenancyUtil.getTenantConstants().DIVISION_USER_LABEL); 
    			 
    			 moduleWiseMap.put("PAGE_NAME",pageName);
    		 }
    		 moduleWiseMap.put("MODULE_NAME",LanguageUtil.getString(rs.getString("MODULE_NAME"),null,BaseUtils.getModuleKey(rs.getString("MODULE_NAME"))));  // P_B_78521  		 
    		 moduleWiseMap.put(rs.getString("STATUS"),rs.getString("DISPLAY"));
    		 
    		 returnMap.put(rs.getString("MODULE_ID"),moduleWiseMap);
    	 }
    	 
    	 return returnMap;
     }
     // BB-20141017-177 ends
     
     public Map getContactManagerDisplayColumn(String userNo,Map<String, HashMap> returnMap)throws Exception{
    	 String query="SELECT GROUP_CONCAT(DISPLAY_NAME) AS DISPLAY,IS_SELECTED,SP.MODULE_ID,PAGE_NAME,SP.MODULE_NAME  AS MODULE_NAME FROM SUMMARY_DISPLAY SD INNER JOIN SUMMARY_PAGES SP ON SP.MODULE_ID=SD.MODULE_ID WHERE 1=1  AND IS_SELECTED IN (0,1) ";
    	 if(StringUtil.isValid(userNo)){
    		query+=" AND USER_NO="+userNo;
    	 }
    	 
    	 query+=" GROUP BY IS_SELECTED,MODULE_ID ORDER BY SP.ORDER_SEQUENCE ASC";
    	 ResultSet rs = QueryUtil.getResult(query,null);
    	 HashMap moduleWiseMap=null;
    	 while(rs.next()){
    		 
    		 if(returnMap.containsKey(rs.getString("MODULE_ID"))){
    			 moduleWiseMap=returnMap.get(rs.getString("MODULE_ID"));
    		 }
    		 else{
    			 moduleWiseMap=NewPortalUtils.getNewHashMapWithKeyValueType();
    			 moduleWiseMap.put("PAGE_NAME",rs.getString("PAGE_NAME"));
    		 }
    		 moduleWiseMap.put("MODULE_NAME",rs.getString("MODULE_NAME"));
    		 moduleWiseMap.put(rs.getString("IS_SELECTED"),rs.getString("DISPLAY"));
    		 returnMap.put(rs.getString("MODULE_ID"),moduleWiseMap);
    	 }
    	 
    	 return returnMap;
     }

     //Business Category starts
     public SequenceMap<String, Info> getBusinessCategories(String sortCol,String sortOrder,String pageID,String key ,String searchName) {
    	 SequenceMap<String, Info> businessCategoriesMap = new SequenceMap<String, Info>();
         StringBuffer query = new StringBuffer("");
         StringBuffer countQuery = new StringBuffer("");
         String totalRecords=null;
         query.append("SELECT BUSINESS_CATEGORY_ID,BUSINESS_CATEGORY_NAME,ADD_DATE,ADDED_BY,ADD_TIME FROM LOCAL_LISTING_CATEGORIES WHERE 1=1 ");
         countQuery.append("SELECT COUNT(BUSINESS_CATEGORY_ID) AS COUNT FROM LOCAL_LISTING_CATEGORIES WHERE 1=1 ");
         
         if(StringUtil.isValid(searchName)) {
        	searchName=PortalUtils.reverseFilterValue(searchName); //P_B_ADMIN_53433
        	searchName=PortalUtils.forSpecialCharForDB(searchName);
 			countQuery.append("AND BUSINESS_CATEGORY_NAME LIKE '%").append(searchName).append("%' ");
 			query.append("AND BUSINESS_CATEGORY_NAME LIKE '%").append(searchName).append("%' ");
 		 }
         if(StringUtil.isValid(key)) {
 			countQuery.append("AND BUSINESS_CATEGORY_NAME REGEXP \'^[").append(key).append("]\'");
 			query.append("AND BUSINESS_CATEGORY_NAME REGEXP \'^[").append(key).append("]\'");
 		 }
         
         
         if(!StringUtil.isValid(sortOrder)) {
 			sortOrder = " ASC";
 		 } 
 		
 		 if(!StringUtil.isValid(sortCol)) {
 			sortCol = " BUSINESS_CATEGORY_NAME ";
 			sortOrder = Constants.LBL_SPACER;
 		 }
 		
 		 if(StringUtil.isValid(sortCol) && FieldNames.BUSINESS_CATEGORY_NAME.equals(sortCol)) {
 			sortCol = " BUSINESS_CATEGORY_NAME ";
 		 } else if(StringUtil.isValid(sortCol) && FieldNames.ADD_DATE.equals(sortCol)) {
 			sortCol = " ADD_DATE "+sortOrder+",ADD_TIME "+sortOrder;
			sortOrder = FieldNames.EMPTY_STRING;
		}
         
 		query.append(" ORDER BY").append(sortCol).append(sortOrder);
 		if (StringUtil.isValid(pageID)) {
			int page = 0;

            try {
                page = Integer.parseInt(pageID);
            } 
            catch (NumberFormatException e) {
                logger.error("NumberFormatException: ", e);
            }

            if (page > 1) {
            	int limit = Constants.RECORDS_PER_PAGE * (Integer.parseInt(pageID) - 1);
            	query.append(" LIMIT ").append(limit).append(" ,  " + Constants.RECORDS_PER_PAGE);
            } else if (page == 1) {
            	query.append(" LIMIT 0").append(" ,  " + Constants.RECORDS_PER_PAGE);
            }
		}
         ResultSet result=null;
         ResultSet result1=null;
         try {
              result = QueryUtil.getResult(query.toString(), new Object[]{});
              result1 = QueryUtil.getResult(countQuery.toString(), new Object[]{});
              while(result1 != null && result1.next()){
        			totalRecords=	result1.getString("COUNT");
        			}
              while (result != null && result.next()) {
            	 Info businessCategoriesInfo = new Info();
            	 businessCategoriesInfo.set(FieldNames.BUSINESS_CATEGORY_ID, result.getString("BUSINESS_CATEGORY_ID"));
            	 businessCategoriesInfo.set(FieldNames.BUSINESS_CATEGORY_NAME, result.getString("BUSINESS_CATEGORY_NAME"));
            	 businessCategoriesInfo.set(FieldNames.ADD_DATE, result.getString("ADD_DATE"));
            	 businessCategoriesInfo.set(FieldNames.ADDED_BY, NewPortalUtils.getContactOwner(result.getString("ADDED_BY")));
            	 businessCategoriesInfo.set(FieldNames.TOTAL_RECORDS,totalRecords);
            	 businessCategoriesMap.put(result.getString("BUSINESS_CATEGORY_ID"), businessCategoriesInfo);
             }
                 
         } catch (Exception e) {
             logger.error("Error in getting businessCategoriesInfo Info :ConfigurationDAO");
         }
         finally
 		{
 	
         	QueryUtil.releaseResultSet(result);
 		}

         return businessCategoriesMap;
     }
     /**
      * @author divanshu
      * ZCUB-20150519-148
      * @param sortCol
      * @param sortOrder
      * @param pageID
      * @param key
      * @param searchName
      * @return
      */
     public SequenceMap<String, Info> getMappedBusinessCategories(String sortCol,String sortOrder,String pageID,String key ,String searchName) {
    	 SequenceMap<String, Info> businessCategoriesMap = new SequenceMap<String, Info>();
         StringBuffer query = new StringBuffer("");
         StringBuffer countQuery = new StringBuffer("");
         String totalRecords=null;
         query.append("SELECT LLC.BUSINESS_CATEGORY_ID AS LLC_BUSINESS_CATEGORY_ID,LLC.BUSINESS_CATEGORY_NAME AS LLC_BUSINESS_CATEGORY_NAME,VGCM.MAPPING_ID AS MAPPING_ID,VGCM.INSERTION_TIME,VGCM.ADDED_BY AS VGCM_ADDED_BY,VBC.BUSINESS_CATEGORY_ID AS VBC_BUSINESS_CATEGORY_ID,VBC.BUSINESS_CATEGORY_NAME AS VBC_BUSINESS_CATEGORY_NAME FROM VENDASTA_GOOGLE_CATEGORY_MAPPING VGCM JOIN LOCAL_LISTING_CATEGORIES LLC ON VGCM.GOOGLE_CATEGORY_ID=LLC.BUSINESS_CATEGORY_ID LEFT JOIN VENDASTA_BUSINESS_CATEGORY  VBC ON VBC.BUSINESS_CATEGORY_ID=VGCM.VENDASTA_CATEGORY_ID WHERE  1=1 ");
         countQuery.append("SELECT COUNT(*) AS COUNT FROM VENDASTA_GOOGLE_CATEGORY_MAPPING VGCM JOIN LOCAL_LISTING_CATEGORIES LLC ON VGCM.GOOGLE_CATEGORY_ID=LLC.BUSINESS_CATEGORY_ID LEFT JOIN VENDASTA_BUSINESS_CATEGORY  VBC ON VBC.BUSINESS_CATEGORY_ID=VGCM.VENDASTA_CATEGORY_ID WHERE 1=1 ");
         
         if(StringUtil.isValid(searchName)) {
        	searchName=PortalUtils.reverseFilterValue(searchName); //P_B_ADMIN_53433
        	searchName=PortalUtils.forSpecialCharForDB(searchName);
 			countQuery.append("AND LLC.BUSINESS_CATEGORY_NAME LIKE '%").append(searchName).append("%' ");
 			query.append("AND LLC.BUSINESS_CATEGORY_NAME LIKE '%").append(searchName).append("%' ");
 		 }
         if(StringUtil.isValid(key)) {
 			countQuery.append("AND LLC.BUSINESS_CATEGORY_NAME REGEXP \'^[").append(key).append("]\'");
 			query.append("AND LLC.BUSINESS_CATEGORY_NAME REGEXP \'^[").append(key).append("]\'");
 		 }
         
         
         if(!StringUtil.isValid(sortOrder)) {
 			sortOrder = " ASC";
 		 } 
 		
 		 if(!StringUtil.isValid(sortCol)) {
 			sortCol = " LLC.BUSINESS_CATEGORY_NAME ";
 			sortOrder = Constants.LBL_SPACER;
 		 }
 		
 		 if(StringUtil.isValid(sortCol) && FieldNames.BUSINESS_CATEGORY_NAME.equals(sortCol)) {
 			sortCol = " LLC_BUSINESS_CATEGORY_NAME ";
 		 } else if(StringUtil.isValid(sortCol) && FieldNames.ADD_DATE.equals(sortCol)) {
 			sortCol = " INSERTION_TIME "+sortOrder;
			sortOrder = FieldNames.EMPTY_STRING;
		}else if(StringUtil.isValid(sortCol) && FieldNames.VENDASTA_CATEGORY_NAME.equals(sortCol)) {
  			sortCol = " VBC_BUSINESS_CATEGORY_NAME "+sortOrder;
 			sortOrder = FieldNames.EMPTY_STRING;
 		}
  		 
 		query.append(" ORDER BY").append(sortCol).append(sortOrder);
 		if (StringUtil.isValid(pageID)) {
			int page = 0;

            try {
                page = Integer.parseInt(pageID);
            } 
            catch (NumberFormatException e) {
                logger.error("NumberFormatException: ", e);
            }

            if (page > 1) {
            	int limit = Constants.RECORDS_PER_PAGE * (Integer.parseInt(pageID) - 1);
            	query.append(" LIMIT ").append(limit).append(" ,  " + Constants.RECORDS_PER_PAGE);
            } else if (page == 1) {
            	query.append(" LIMIT 0").append(" ,  " + Constants.RECORDS_PER_PAGE);
            }
		}
         ResultSet result=null;
         ResultSet result1=null;
         try {
              result = QueryUtil.getResult(query.toString(), new Object[]{});
              result1 = QueryUtil.getResult(countQuery.toString(), new Object[]{});
              while(result1 != null && result1.next()){
        			totalRecords=	result1.getString("COUNT");
        			}
              while (result != null && result.next()) {
            	 Info businessCategoriesInfo = new Info();
            	 businessCategoriesInfo.set(FieldNames.BUSINESS_CATEGORY_ID, result.getString("LLC_BUSINESS_CATEGORY_ID"));
            	 businessCategoriesInfo.set(FieldNames.BUSINESS_CATEGORY_NAME, result.getString("LLC_BUSINESS_CATEGORY_NAME"));
            	 businessCategoriesInfo.set(FieldNames.ADD_DATE, result.getString("INSERTION_TIME"));
            	 businessCategoriesInfo.set(FieldNames.ADDED_BY, NewPortalUtils.getContactOwner(result.getString("VGCM_ADDED_BY")));
            	 businessCategoriesInfo.set(FieldNames.TOTAL_RECORDS,totalRecords);
            	 businessCategoriesInfo.set(FieldNames.CATEGORY_MAPPING_ID,result.getString("MAPPING_ID"));
            	 if(StringUtil.isValid(result.getString("VBC_BUSINESS_CATEGORY_NAME")))
            		 businessCategoriesInfo.set(FieldNames.VENDASTA_BUSINESS_CATEGORY_NAME, result.getString("VBC_BUSINESS_CATEGORY_NAME"));
            	 else
            		 businessCategoriesInfo.set(FieldNames.VENDASTA_BUSINESS_CATEGORY_NAME, "--");
            	 businessCategoriesMap.put(result.getString("MAPPING_ID"), businessCategoriesInfo);
             }
                 
         } catch (Exception e) {
             logger.error("Error in getting businessCategoriesInfo Info :ConfigurationDAO");
             e.printStackTrace();
         }
         finally
 		{
 	
         	QueryUtil.releaseResultSet(result);
 		}

         return businessCategoriesMap;
     }
     
     
     public void addBusinessCategories(String[] businessCategoryNameArray, String userNo,HttpServletRequest request) {
    	 List<String> batchList = new ArrayList<String>();
    	 Map businessParameters = new HashMap();
    	 Date date = null;
    	 StringBuilder queryBuffer = null;
    	 //SYNC_BUSINESSCATEGORIES_STARTS
    	 String businessCategoryId=null;
    	 StringBuffer businessCategoryIdforSync=new StringBuffer();
    	 StringBuffer businessCatNameSync=new StringBuffer();
    	 StringBuffer businessCatAddDateforSync=new StringBuffer();
    	 StringBuffer businessCatTimeforSync=new StringBuffer();
    	 //SYNC_BUSINESSCATEGORIES_ENDS
    	 List<String> duplicateRemoval=new ArrayList<String>();
    	 for(String businessCategory : businessCategoryNameArray) {
    		 if(StringUtil.isValid(businessCategory)) {
    			 businessCategory=businessCategory.trim();
    			 if(duplicateRemoval.isEmpty() || !duplicateRemoval.contains(businessCategory.toLowerCase())){
    			 duplicateRemoval.add(businessCategory.toLowerCase());
    			 }
    			 else{
    				 continue;
    			 }
    			 date = new Date();
    			 String timeFormat = new SimpleDateFormat("HH:mm:ss").format(date);
    			 String currDate = DateUtil.getDbDate(DateUtil.getCurrentDateAsString());
    			 //P_B_ADM_53635 starts
    			 if(StringUtil.isValidNew(businessCategory)){
    				 businessCategory=PortalUtils.forSpecialCharForDB(businessCategory);
    			 }
    			 //P_B_ADM_53635 ends    			 
    			 //SYNC_BUSINESSCATEGORIES_STARTS
    			 businessCategoryId=IDGenerator.getNextKey();
    			 businessCategoryIdforSync.append(",").append(businessCategoryId);
    			 businessCatNameSync.append(",").append(businessCategory);
    			 businessCatAddDateforSync.append(",").append(currDate);
    			 businessCatTimeforSync.append(",").append(timeFormat);
    			 //SYNC_BUSINESSCATEGORIES_ENDS
    			 queryBuffer = new StringBuilder("INSERT INTO LOCAL_LISTING_CATEGORIES (BUSINESS_CATEGORY_ID,BUSINESS_CATEGORY_NAME,ADD_DATE,ADD_TIME,ADDED_BY) VALUES (");
    			 queryBuffer.append("'").append(businessCategoryId).append("','").append(businessCategory).append("','").append(currDate).append("','").append(timeFormat).append("','").append(userNo).append("')");
    			 
    			 batchList.add(queryBuffer.toString());
    		 }
    	 }
    	 //SYNC_BUSINESSCATEGORIES_STARTS
    	    businessParameters.put("businessCategoryID", businessCategoryIdforSync.toString().substring(1,businessCategoryIdforSync.toString().length()));
    	    businessParameters.put("businessCategoryName", businessCatNameSync.toString().substring(1,businessCatNameSync.toString().length()));    
			businessParameters.put("businessCategoryAddDate", businessCatAddDateforSync.toString().substring(1,businessCatAddDateforSync.toString().length())); 
			businessParameters.put("businessCategoryAddTime", businessCatTimeforSync.toString().substring(1,businessCatTimeforSync.toString().length())); 
			businessParameters.put("businessCategoryUserNo", userNo);
			businessParameters.put("action", "addBusinessCategories");
		if(!batchList.isEmpty()) {
    		 try {
				QueryUtil.batchUpdate(batchList, null);
				NewPortalUtils.syncData("localListings", request, businessParameters);
			} catch (AppException e) {
				e.printStackTrace();
			}finally{
				businessCategoryIdforSync=null;
				businessCatNameSync=null;
				businessCatAddDateforSync=null;
				businessCatTimeforSync=null;
				queryBuffer=null;
			}
    	 }
		//SYNC_BUSINESSCATEGORIES_END
     } 
   //Business Category ends
     
     /**
      * This method is used to save mapping of Vendasta and Google
      * @author Divanshu Verma
      * @param serviceCategory
      * @param vendastaCategory
      * @param userNo
      * @param request
      */
     public void addBusinessCategoriesMapping(String[] serviceCategory,String[] vendastaCategory, String userNo,HttpServletRequest request) {
    	 List<String> batchList = new ArrayList<String>();
    	 Map businessParameters = new HashMap();
    	 Date date = null;
    	 StringBuffer queryBuffer = null;
    	 StringBuffer queryValue=new StringBuffer("");

    	 for(int i=0;i<serviceCategory.length;i++)
    		 queryValue.append("('"+serviceCategory[i]+"','"+vendastaCategory[i]+"',NOW(),"+userNo+"),");
    	 
    	 queryBuffer=new StringBuffer("INSERT INTO VENDASTA_GOOGLE_CATEGORY_MAPPING(GOOGLE_CATEGORY_ID,VENDASTA_CATEGORY_ID,INSERTION_TIME,ADDED_BY) VALUES "+queryValue.substring(0,queryValue.length()-1));
    	 try {
    		 QueryUtil.executeInsert(queryBuffer.toString(),null);
    		 if(ModuleUtil.zcubatorImplemented())
    		 {
	    		 businessParameters.put("category",queryValue.toString() );
	    		 businessParameters.put("action", "mapBusinessCategories");
	    		 NewPortalUtils.syncData("localListings", request, businessParameters);
    		 }
    	 } catch (Exception e) {
    		 e.printStackTrace();
    	 }finally{
    		 queryBuffer=null;
    	 }
		//SYNC_BUSINESSCATEGORIES_END
     } 
   //Business Category ends
     
     
     
     //ZCB-20141017-055 Start
     public void updateActivityModule(String activityIds,String moduleId, String franNo, String userNo){

        StringBuffer sbQuery=new StringBuffer();
        sbQuery.append("SELECT ID FROM MARKETING_CENTER_CONFIGURE_EMAIL_ACTIVITY WHERE MODULE_ID=? AND USER_NO=?");
        ResultSet resultSet = null;
        try{
            resultSet =QueryUtil.getResult(sbQuery.toString(),new Object[]{moduleId,userNo});

            if(resultSet != null && resultSet.next()){
                sbQuery=new StringBuffer("UPDATE MARKETING_CENTER_CONFIGURE_EMAIL_ACTIVITY SET CONFIGURE_ACTIVITY_ID=? WHERE MODULE_ID=? AND USER_NO=?");
                QueryUtil.update(sbQuery.toString(), new String[]{activityIds,moduleId,userNo} );

            }else{

                sbQuery=new StringBuffer("INSERT INTO MARKETING_CENTER_CONFIGURE_EMAIL_ACTIVITY(ID,CONFIGURE_ACTIVITY_ID,MODULE_ID,FRANCHISEE_NO,USER_NO) VALUES(?,?,?,?,?)");
                QueryUtil.update(sbQuery.toString(), new String[]{IDGenerator.getNextKey(),activityIds,moduleId,franNo,userNo});
            }


        }catch(Exception e){

            logger.error("\n Exception In updateActivityModule()\t", e);
        }

    }

    public void deleteConfiguredActivity(String moduleId, String franNo){

        String sbQuery="DELETE FROM MARKETING_CENTER_CONFIGURE_EMAIL_ACTIVITY WHERE FRANCHISEE_NO=? AND MODULE_ID=?";
        try{
            QueryUtil.update(sbQuery, new String[]{franNo,moduleId});

        }catch(Exception e){

          logger.error("\n Exception In deleteConfiguredActivity()\t", e);
        }
    }
    public boolean isMarketingDashboardPriviliges(UserRoleMap userRoleMap, boolean mapped, boolean cgSigned){

        boolean isPrivileges=false;
        BaseConstants _baseConstants= MultiTenancyUtil.getTenantConstants();
        if (ModuleUtil.cmImplemented() && userRoleMap.isModuleInMap("4")) {
            isPrivileges=true;
        }
        else if( ModuleUtil.ppcImplemented() && userRoleMap.isPrivilegeIDInMap("551002") && (cgSigned || mapped )){
            isPrivileges=true;
        }
        else if(ModuleUtil.wbImplemented() && userRoleMap.isModuleInMap("8")){
            isPrivileges=true;
        }
        else if(ModuleUtil.intranetImplemented() && userRoleMap.isPrivilegeIDInMap("1230319")){
            isPrivileges=true;
        }
        else if (ModuleUtil.cmImplemented() && userRoleMap.isModuleInMap("4") && userRoleMap.isPrivilegeIDInMap("680")) {
            isPrivileges=true;
        }
        else if(ModuleUtil.admakerImplemented() && userRoleMap.isModuleInMap("12301")){
            isPrivileges=true;
        }
        else if(  userRoleMap.isPrivilegeIDInMap("11109") && _baseConstants.LP_ENABLED){//P_B_50548
            isPrivileges=true;
        }
        else if(ModuleUtil.facebookBuilderImplemented() && userRoleMap.isPrivilegeIDInMap("11106") ){
            isPrivileges=true;
        }
        else if(userRoleMap.isPrivilegeIDInMap("11108") && ModuleUtil.cmImplemented() && "Y".equals(MultiTenancyUtil.getTenantConstants().CM_PILOT_ENABLED)){
            isPrivileges=true;
        }

        return isPrivileges;
    }

    public boolean isTwitterEnabled(){

        boolean isTwtEnable=false;

        String sbQuery="SELECT TWT_ENABLED FROM SOCIAL_MEDIA_CLIENTS";
        try{
            ResultSet resultSet =QueryUtil.getResult(sbQuery,null);
            if(resultSet.next()){
                if(StringUtil.isValid(resultSet.getString("TWT_ENABLED")) && "Y".equals(resultSet.getString("TWT_ENABLED"))){
                    isTwtEnable=true;
                }
            }
        }catch(Exception e){
            logger.error("\n Exception In isTwitterEnabled()\t", e);
        }

        return isTwtEnable;
    }
    public ArrayList getConfiguredActivityID(String userNo){

        ArrayList al=null;
        String sbQuery="SELECT CONFIGURE_ACTIVITY_ID FROM MARKETING_CENTER_CONFIGURE_EMAIL_ACTIVITY WHERE USER_NO=?";
        try{

            ResultSet resultSet =QueryUtil.getResult(sbQuery,new Object[]{userNo});
            String activityId=null;
            String [] activityIds=null;
            if(resultSet != null && resultSet.size()>0){
                al= new ArrayList();
                while(resultSet.next()){
                    activityId=resultSet.getString("CONFIGURE_ACTIVITY_ID");
                    if(activityId !=null && activityId.contains("##")){
                        activityIds=activityId.split("##");
                        for(int i=0;i<activityIds.length; i++){
                            al.add(activityIds[i]);
                        }


                    }else if(activityId != null){
                        al.add(activityId);
                    }
                }
            }

        }catch(Exception e){

            logger.error("\n Exception In getConfiguredActivityID()\t", e);
        }
        return al;
    }
    //ZCB-20141017-055 Ends
    /*-------------------------------Moved from BaseNewPortalUtils---------------------------------*/
    public Map getConfiguredSummaryColumns(String moduleId,
            String isSelected, String isNotSelected,String userNo, boolean userSpecific) {
    	return getConfiguredSummaryColumns(moduleId,isSelected,isNotSelected,userNo, userSpecific, null); 
    }
    
    public Map getConfiguredSummaryColumns(String moduleId,
            String isSelected, String isNotSelected,String userNo, boolean userSpecific, String fromWhere) {
        Map<String, Map> criteriaMap = null;
        ResultSet result = null;
        BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	UserRoleMap userRoleMap = null;
        if(StringUtil.isValidNew(fromWhere) && "noRequest".equals(fromWhere)){
        }else{
        	userRoleMap = (UserRoleMap) StrutsUtil.getHttpSession().getAttribute("userRoleMap");//P_CM_B_68658
        }
        Map finalMap= new HashMap();
        Map <String,String> valueMap=null;
        try {
            criteriaMap = new LinkedHashMap<String, Map>();
            //StringBuffer query = new StringBuffer(
                    //"SELECT DISPLAY_ID,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,IS_SELECTED,WIDTH,SORTING,DEFAULT_KEY,DEFAULT_SORTING,ORDER_SEQUENCE FROM SUMMARY_DISPLAY WHERE USER_NO=0 ");
            StringBuffer query = new StringBuffer(
                    "SELECT DISPLAY_ID,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,IS_SELECTED,WIDTH,SORTING,DEFAULT_KEY,DEFAULT_SORTING,ORDER_SEQUENCE,IS_PII_ENABLED, CUSTOM_FIELD_NAME FROM SUMMARY_DISPLAY WHERE USER_NO=0 ");//added IS_PII_ENABLED  // BB-20150525-360			//P_Enh_FC-76
            if (StringUtil.isRequestParamValid(moduleId)) {
                query.append(" AND MODULE_ID=").append(moduleId);
                /*ZCUB-20160719-268 Starts*/
                if("1402".equals(moduleId) && !MultiTenancyUtil.getTenantConstants().MS_PAYMENT || "1301".equals(moduleId) && !MultiTenancyUtil.getTenantConstants().AM_APPROVAL_PAYMENT){
                	query.append(" AND DISPLAY_VALUE NOT IN ('paymentStatus') ");
                }
                /*ZCUB-20160719-268 Ends*/
            }
            if (StringUtil.isRequestParamValid(isSelected)) {
                query.append(" AND IS_SELECTED IN (").append(isSelected).append(") ");
            }
            if (StringUtil.isRequestParamValid(isNotSelected)) {
                query.append(" AND DISPLAY_VALUE NOT IN ('").append(isNotSelected).append("') ");
            }
            
            if("8".equals(moduleId) && ModuleUtil.zcubatorImplemented()) {
            	query.append(" AND DISPLAY_VALUE NOT IN ('fimTtEntityName','reportPeriodStartDate','storeName') ");
            }
            //HEAT_METER_CONFIG_ISSUE starts
            if("no".equalsIgnoreCase((String)MultiTenancyUtil.getTenantContext().getAttribute("heatMeter")) ){
            	query.append(" AND DISPLAY_VALUE NOT IN ('heatIndexScore') ");
       	 	}
            //ADDING_ENTITY_WITHOUT_FIM starts
            if(("51".equals(moduleId) || "52".equals(moduleId)) && !ModuleUtil.fimImplemented()){
            	query.append(" AND DISPLAY_VALUE NOT IN ('entityName') ");
			}
            //ADDING_ENTITY_WITHOUT_FIM ends
            //P_CM_B_68658 Starts
            if(StringUtil.isValidNew(fromWhere) && "noRequest".equals(fromWhere)){
            }else{
            	if(!userRoleMap.isPrivilegeInMap("/companySummary")){
                	query.append(" AND DISPLAY_NAME NOT IN('Account', 'Industry') ");
                }	
            }
            
            
            //P_CM_B_68658 End
            
            //HEAT_METER_CONFIG_ISSUE ends
            query.append(" AND IS_ACTIVE = 'yes'"); //BB-20141017-177
            query.append(" ORDER BY MODULE_ID, ORDER_SEQUENCE ");
            result = QueryUtil.getResult(query.toString(), null);
            int count=0;
            while (result.next()) {
            	//Brand Col display in FDD Summary Starts
            	if(!"Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "Division".equals(result.getString("DISPLAY_NAME"))){
            		continue;
            	}
            	//Brand Col display in FDD Summary Ends
                valueMap = new HashMap<String, String>();
                valueMap.put("displayId", result.getString("DISPLAY_ID"));
                
                if(result.getString("DISPLAY_NAME").contains("$")){
                	valueMap.put("displayName", (result.getString("DISPLAY_NAME")).replace("$",MultiTenancyUtil.getTenantConstants().USER_CURRENCY));  
                }else
                valueMap.put("displayName", result.getString("DISPLAY_NAME"));
                valueMap.put("displayValue", result.getString("DISPLAY_VALUE"));
                valueMap.put("isSelected", result.getString("IS_SELECTED"));
                valueMap.put("orderSequence",
                        result.getString("ORDER_SEQUENCE"));

                valueMap.put("width", result.getString("WIDTH"));
                valueMap.put("moduleID", result.getString("MODULE_ID"));
                valueMap.put("sorting", result.getString("SORTING"));
                valueMap.put("defaultKey", result.getString("DEFAULT_KEY"));
                valueMap.put("defaultSorting",result.getString("DEFAULT_SORTING"));
                valueMap.put("piiEnabled", result.getString("IS_PII_ENABLED")); // BB-20150525-360
                valueMap.put("customFieldName", result.getString("CUSTOM_FIELD_NAME"));			//P_Enh_FC-76
                if(finalMap.containsKey(result.getString("MODULE_ID"))){
                	criteriaMap= (Map)finalMap.get(result.getString("MODULE_ID"));
                	criteriaMap.put("" + (++count), valueMap);
                }
                else{
                	criteriaMap=new LinkedHashMap<String, Map>();
                	criteriaMap.put("" + (++count), valueMap);
                	finalMap.put(result.getString("MODULE_ID"),criteriaMap);
                }
                
                
                
            }

            //BB-20141017-177
            //replacing with the user specific settings
            //commenting user specific
            if(userSpecific && StringUtil.isValid(userNo)){/*
            	Map userMap= new HashMap();
            	criteriaMap=new LinkedHashMap<String, Map>();
            	query = new StringBuffer(
            			"SELECT DISPLAY_ID,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,IS_SELECTED,WIDTH,SORTING,DEFAULT_KEY,DEFAULT_SORTING,ORDER_SEQUENCE FROM SUMMARY_DISPLAY WHERE USER_NO="+userNo+" ");
            	if (StringUtil.isRequestParamValid(moduleId)) {
            		query.append(" AND MODULE_ID=").append(moduleId);
            	}
            	if (StringUtil.isRequestParamValid(isSelected)) {
            		query.append(" AND IS_SELECTED IN (").append(isSelected).append(") ");
            	}
            	if (StringUtil.isRequestParamValid(isNotSelected)) {
            		query.append(" AND DISPLAY_VALUE NOT IN ('").append(isNotSelected).append("') ");
            	}
            	query.append(" AND IS_ACTIVE = 'yes'");
            	query.append(" ORDER BY MODULE_ID, ORDER_SEQUENCE ");
            	result = QueryUtil.getResult(query.toString(), null);
            	count=0;
            	while (result.next()) {

            		valueMap = new HashMap<String, String>();
            		valueMap.put("displayId", result.getString("DISPLAY_ID"));
            		if(result.getString("DISPLAY_NAME").endsWith("($)"))
            		{
            			valueMap.put("displayName", "Transactions "+"("+_baseConstants.USER_CURRENCY+")");

            		}
            		else
            			valueMap.put("displayName", result.getString("DISPLAY_NAME"));
            		valueMap.put("displayValue", result.getString("DISPLAY_VALUE"));
            		valueMap.put("isSelected", result.getString("IS_SELECTED"));
            		valueMap.put("orderSequence",
            				result.getString("ORDER_SEQUENCE"));

            		valueMap.put("width", result.getString("WIDTH"));
            		valueMap.put("moduleID", result.getString("MODULE_ID"));
            		valueMap.put("sorting", result.getString("SORTING"));
            		valueMap.put("defaultKey", result.getString("DEFAULT_KEY"));
            		valueMap.put("defaultSorting",result.getString("DEFAULT_SORTING"));

            		if(userMap.containsKey(result.getString("MODULE_ID"))){
            			criteriaMap= (Map)userMap.get(result.getString("MODULE_ID"));
            			criteriaMap.put("" + (++count), valueMap);
            		}
            		else{
            			criteriaMap=new LinkedHashMap<String, Map>();
            			criteriaMap.put("" + (++count), valueMap);
            			userMap.put(result.getString("MODULE_ID"),criteriaMap);
            		}



            	}


            	finalMap.putAll(userMap);
            if(userSpecific && StringUtil.isValid(userNo)){
            Map userMap= new HashMap();
            criteriaMap=new LinkedHashMap<String, Map>();
             query = new StringBuffer(
                    "SELECT DISPLAY_ID,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,IS_SELECTED,WIDTH,SORTING,DEFAULT_KEY,DEFAULT_SORTING,ORDER_SEQUENCE FROM SUMMARY_DISPLAY WHERE USER_NO="+userNo+" ");
            if (StringUtil.isRequestParamValid(moduleId)) {
                query.append(" AND MODULE_ID=").append(moduleId);
            }
            if (StringUtil.isRequestParamValid(isSelected)) {
                query.append(" AND IS_SELECTED IN (").append(isSelected).append(") ");
            }
            if (StringUtil.isRequestParamValid(isNotSelected)) {
                query.append(" AND DISPLAY_VALUE NOT IN ('").append(isNotSelected).append("') ");
            }
            query.append(" ORDER BY MODULE_ID, ORDER_SEQUENCE ");
            result = QueryUtil.getResult(String.valueOf(query), null);
            count=0;
            while (result.next()) {
            	
                valueMap = new HashMap<String, String>();
                valueMap.put("displayId", result.getString("DISPLAY_ID"));
                if(result.getString("DISPLAY_NAME").endsWith("($)"))
                {
                	valueMap.put("displayName", "Transactions "+"("+_baseConstants.USER_CURRENCY+")");
                	
                }
                else
                valueMap.put("displayName", result.getString("DISPLAY_NAME"));
                valueMap.put("displayValue", result.getString("DISPLAY_VALUE"));
                valueMap.put("isSelected", result.getString("IS_SELECTED"));
                valueMap.put("orderSequence",
                        result.getString("ORDER_SEQUENCE"));

            */}
            
        } catch (Exception e) {
            logger.info("ERROR: exception in getSummaryDisplay ::" + e);
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return finalMap;
    }	
    
    public boolean updateSummaryDataValue(String dataType, String dataValue, String moduleId) {


        StringBuffer query1 = new StringBuffer();

        try {
            query1.append("Update SUMMARY_DISPLAY SET IS_SELECTED='" + dataValue + "' WHERE DISPLAY_VALUE ='");
            query1.append(dataType + "'");
            query1.append(" AND MODULE_ID=");
            query1.append(moduleId);

            System.out.println("query1" + query1);
            int rs1 = QueryUtil.update(query1.toString(), new String[]{});


        } catch (Exception e) {
            logger.error("Exception in updateMasterDataValue===" + e);
            return false;
        }

        return true;

    }
    /*-------------------------------Moved from BasePortalUtils---------------------------------*/

    public String getCreditCardPaymentGatewayStatus()
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
    
    
    /**
	 * This function gives the custom field Names for the creating in the Info
	 * BB-20141017-177
	 * @param moduleId
	 * @return
	 */
	public String[] getCustomFields(String moduleId) {
		String customFields[] = new String[3];
		String customDbFieldColumns = "";
		String customFieldName = "";
		String customFieldType = "";
		String charsequence = "\'\\_%\'";
		String customselectquery = "SELECT DISPLAY_VALUE, CUSTOM_FIELD_NAME, CUSTOM_FIELD_TYPE FROM SUMMARY_DISPLAY WHERE MODULE_ID IN ('"+moduleId+"') AND IS_ACTIVE = 'yes' AND DISPLAY_VALUE LIKE "+charsequence;
		ResultSet result = null;
		try {
			result = QueryUtil.getResult(customselectquery, null);
			if(result != null) {
				while(result.next()) {     
					customDbFieldColumns += result.getString("DISPLAY_VALUE")+",";
					customFieldName += result.getString("CUSTOM_FIELD_NAME")+",";
					customFieldType += result.getString("CUSTOM_FIELD_TYPE")+",";
				}
				customFields[0] = customDbFieldColumns; //DB column
				customFields[1] = customFieldName; //Field Names
				customFields[2] = customFieldType; //Field Type
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCustomFields====>>"+e);
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return customFields;
	}
    
	/**
	 * This function is used to create the queryFields for custom fields for the display in Lead Summary.
	 * BB-20141017-177 
	 * @param tableAlias
	 * @param moduleId
	 * @return
	 */
	public String getCustomQueryFields(String tableAlias, String moduleId) {
		String queryFields = "";
		String charsequence = "\'\\_%\'";
		String customselectquery = "SELECT DISPLAY_VALUE FROM SUMMARY_DISPLAY WHERE MODULE_ID IN ('"+moduleId+"') AND IS_ACTIVE = 'yes' AND DISPLAY_VALUE LIKE "+charsequence;
		ResultSet result = null;
		try {
			result = QueryUtil.getResult(customselectquery, null);
			while(result!=null && result.next()) {     
				queryFields = queryFields + tableAlias+"."+result.getString("DISPLAY_VALUE")+",";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCustomQueryFields====>>"+e);
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return queryFields;
	}
	
	//P_E_ADM_PASSWORD_POLICY STARTS
	public String getPreviousPasswords(String userNo,String reuseValue){
		StringBuilder query=new StringBuilder("");
		query.append("SELECT AES_DECRYPT(PASSWORD,'"+MultiTenancyUtil.getTenantConstants().PASSWORD_KEY+"') AS PASSWORD FROM USER_PASSWORD_MAPPINGS WHERE USER_NO IN (").append(userNo).append(") ORDER BY CREATION_DATE DESC");
		StringBuilder passwords=new StringBuilder("");
		
		if(StringUtil.isvalidInteger(reuseValue) && Integer.parseInt(reuseValue)>0){
			query.append(" LIMIT ").append(reuseValue);
		}
		else {
			return passwords.toString();
		}
		
		ResultSet rs =null;
		try{
			rs =QueryUtil.getResult(query.toString(),null);
		while(rs.next()){
			passwords.append(",");
			passwords.append(rs.getString("PASSWORD"));
		}
		passwords.append(",");
		
		} catch (Exception e) {
			logger.error("Error in getPreviousPasswords():ConfigurationDAO");
		}
		finally
		{
			QueryUtil.releaseResultSet(rs);
		}
		
		return passwords.toString();
	}
	//P_E_ADM_PASSWORD_POLICY ENDS
	
	//P_E_ADM_PASSWORD_POLICY STARTS
    public String isSettingChanged(String userId){
        String query = null;
        ResultSet resultSet =null;
        //String lastSettingsChangeDate = null;
        String creationDate = null;
        try{
            /*query = " SELECT LAST_SETTINGS_CHANGE_DATE FROM CONFIGURE_PASSWORD_SETTINGS WHERE USER_NO = 1";
            ResultSet resultSet = QueryUtil.getResult(query, null);
            if(resultSet.next()){
                lastSettingsChangeDate =  DateUtil.formatDate(resultSet.getTimestamp("LAST_SETTINGS_CHANGE_DATE").toString(), TimeZoneUtils.DB_DATETIME);
            }*/
            //query = "SELECT CREATION_DATE FROM USER_PASSWORD_MAPPINGS WHERE CREATION_DATE < '"+lastSettingsChangeDate+"' AND USER_ID = '"+userId+"' ORDER BY CREATION_DATE LIMIT 1,1";
            query = " SELECT IF(LAST_SETTINGS_CHANGE_DATE > (SELECT CREATION_DATE FROM USER_PASSWORD_MAPPINGS WHERE USER_ID = '"+userId+"' AND USER_NO<>1  ORDER BY CREATION_DATE DESC LIMIT 1) , 'SC','SNC' ) SETTING FROM CONFIGURE_PASSWORD_SETTINGS;";
            resultSet = QueryUtil.getResult(query, null);
            if(resultSet.next()) {

                return resultSet.getString("SETTING");
            }
        }
        catch (Exception e) {
            logger.error("Error in isChangePassword():ConfigurationDAO");
        }
        finally
        {
            QueryUtil.releaseResultSet(resultSet);
        }
        return "SNC";
    }
	public String isChangePassword(String userId){
		PasswordInfo passInfo = PasswordInfo.getInstance();
		
		int frequency=0;
		String forcelyChange="";
		String frequentlyChange="";
		
		int days=0,count=0;
		ResultSet rs =null;
		try{
			forcelyChange = passInfo.isForcelyChangePasswordOn();
			frequentlyChange = passInfo.isFrequentlyChangePasswordOn();
			frequency = Integer.parseInt(passInfo.getPasswordChangeFrequency());
		
			if("Y".equalsIgnoreCase(forcelyChange)){
				String forceQuery="SELECT COUNT(USER_ID) COUNT FROM LOGIN_DETAILS WHERE USER_ID IN (SELECT USER_NO FROM USERS WHERE USER_ID='"+userId+"' AND STATUS=1)";
				String result = SQLUtil.getQueryResult(forceQuery, "COUNT");
		
				if(!StringUtil.isValid(result)) {
					result = FieldNames.ZERO;
				}
				count = Integer.parseInt(result);
		
				String firstLoginQuery="SELECT COUNT(MAPPING_ID) COUNT FROM USER_PASSWORD_MAPPINGS WHERE USER_ID='"+userId+"' AND USER_NO IN ( SELECT USER_NO FROM USERS WHERE USER_ID='"+userId+"' AND STATUS=1) AND HAS_PREVIOUSLY_LOGGED_IN='Y'";
				String firstLoginResult = SQLUtil.getQueryResult(firstLoginQuery, "COUNT");
				int firstLoginCount=0;
				if(!StringUtil.isValid(firstLoginResult)) {
					firstLoginResult = FieldNames.ZERO;
				}
				firstLoginCount = Integer.parseInt(firstLoginResult);
		
				if(count==0 && firstLoginCount==0){
					return "FL";
				}
			}
		
			if("N".equalsIgnoreCase(frequentlyChange)){
				return "false";
			}

			String query="SELECT DATEDIFF(NOW(),CREATION_DATE) AS DAYS FROM USER_PASSWORD_MAPPINGS WHERE USER_ID='"+userId+"' AND USER_NO IN ( SELECT USER_NO FROM USERS WHERE USER_ID='"+userId+"' AND STATUS=1) AND USER_NO<>1 ORDER BY CREATION_DATE DESC LIMIT 1";
			String result = SQLUtil.getQueryResult(query, "DAYS");
		
			if(!StringUtil.isValid(result)) {
				result = FieldNames.ZERO;
			}
			days = Integer.parseInt(result);
		
			if(days>frequency){
				return "PE";
			}
			} catch (Exception e) {
				logger.error("Error in isChangePassword():ConfigurationDAO");
			}
			finally
			{
				QueryUtil.releaseResultSet(rs);
			}
			return "false";
	}
    //P_E_ADM_PASSWORD_POLICY ENDS
	
	//HomeTeam-20160603-584 starts
	public List<String> getActiveSearchFields(String table,String fieldFor,String module) 
	{
		List<String> fieldList = new ArrayList<String>();
		ResultSet rs = QueryUtil.getResult("SELECT DB_FIELD_NAME FROM ACTIVE_SEARCH_FIELDS WHERE FIELD_FOR = ? AND TABLE_NAME = ? AND MODULE = ? AND IS_ACTIVE='Y' ", new String []{fieldFor,table,module});
		while(rs.next())
		{
			fieldList.add(rs.getString(1));
		}
		return fieldList;
	}
	//HomeTeam-20160603-584 ends
	//P_E_GLOBAL_POPUP_NOTIFICATION STARTS
	//Milky Way Change Notes Starts
	public Info getNotificationData() {
		return getNotificationData(null);
	}
	
	public Info getNotificationData(String userLevel) {
		String query = null;
		ResultSet result = null;
		Info notificationInfo = new Info();
		try {
			
			query = " SELECT NOTIFICATION_NO,IS_CONFIGURE,EXPIRATION_DATE,START_DATE,MAIN_FILE,UPLOAD_FILE,POPUP_WIDTH,POPUP_HEIGHT FROM GLOBAL_POPUP_NOTIFICATION WHERE USER_LEVEL IN ("+userLevel+",-1) ORDER BY USER_LEVEL DESC ";
			result = QueryUtil.getResult(query, null);
			if(result != null && result.next())
			{
				notificationInfo.set(FieldNames.IS_CONFIGURE,result.getString("IS_CONFIGURE"));
				notificationInfo.set(FieldNames.EXPIRATION_DATE,result.getString("EXPIRATION_DATE"));
				notificationInfo.set(FieldNames.MAIN_FILE,result.getString("MAIN_FILE"));
				notificationInfo.set(FieldNames.START_DATE,result.getString("START_DATE"));
				notificationInfo.set(FieldNames.UPLOAD_FILE,result.getString("UPLOAD_FILE"));
				notificationInfo.set(FieldNames.NOTIFICATION_NO,result.getString("NOTIFICATION_NO"));
				notificationInfo.set(FieldNames.WIDTH,result.getString("POPUP_WIDTH"));
				notificationInfo.set(FieldNames.HEIGHT,result.getString("POPUP_HEIGHT"));
				
			}
		} catch (Exception e) {
			logger.info("ERROR: exception in getLeadOwners ::" + e);
		}
		return notificationInfo;
	}
	//P_E_GLOBAL_POPUP_NOTIFICATION ENDS	
	//Milky Way Change Notes Ends
	
}
