package com.home.builderforms;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.AdminMgr;
import com.home.builderforms.RegionMgr;
import com.home.builderforms.CommonMgr;

import org.apache.log4j.Logger;

import com.home.builderforms.LocationMgr;
import com.home.builderforms.MasterDataMgr;
import com.home.builderforms.MasterDataDAO;
import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateTime;
import com.home.builderforms.DateUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IntConstants;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.StrutsUtil;
import com.appnetix.app.portal.role.UserRoleMap;
import com.home.builderforms.TimeZoneUtils;
import com.home.builderforms.cache.CacheMgr;
import com.home.builderforms.database.Field;
import com.home.builderforms.database.FieldMappings;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.NumberFormatUtils;
import com.home.builderforms.LanguageUtil;

import static com.home.builderforms.base.BaseFieldNames.EMPTY_STRING;

/**
 * @see  com.appnetix.app.portal.builderforms.DataManipulator
 * @author abhishek gupta
 * @version Initial
 * @Date: 2012/01/17 12:25:06
 * Basic Description/Data Flow of Bean
 * -------------------------------------------------------------------------------------------------------
*/
/**
-----------------------------------------------------------------------------------------------------------------------------------------------
Version No.				Date			By								Against									Function Changed	Comments
-----------------------------------------------------------------------------------------------------------------------------------------------
P_E_MoveToFim_AddlFDD	27Nov2012	  	Veerpal Singh					Patch
P_FS_Enh_BuilderForm	16Dec2013		Naman Jain						Form Builder in Franchise Sales Module
P_CM_Enh_BuilderForm    11Feb2014       Dheeraj Madaan                  Form Builder in Contact Manager Module 
P_SCH_ENH_008			20 June 2014	Manik Malhotra		CR			Add Form Generator on JOBS Page
BOEFLY_INTEGRATION				21/08/2014		Veerpal Singh		Enh		A third party integration with Boefly through REST-API for lead sync.
**/
public class DataManipulator {
	static private DataManipulator objectRef	= null;
	static Method[] methodRef			= null;
	static public HashMap  sMap				= null;
	private static HashMap checkBoxMap;
	private static HashMap<String, String> siteLocationTypeMap; //P_Enh_Site_Clearance
	static Logger logger			= com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(DataManipulator.class);
	private static String boeflyUrl;//BOEFLY_INTEGRATION
	public static String userTimeZone=FieldNames.EMPTY_STRING;
	static {
		checkBoxMap = new HashMap();
		checkBoxMap.put("", "");
		checkBoxMap.put("0", "No");
		checkBoxMap.put("1", "Yes");
		checkBoxMap.put("2", "NA");
		checkBoxMap.put("3", "");
		
		//P_Enh_Site_Clearance starts
		siteLocationTypeMap = new HashMap<String, String>();
		siteLocationTypeMap.put("N", "New Available");
		siteLocationTypeMap.put("R", "Resale");
		siteLocationTypeMap.put("E", "Existing");
		//P_Enh_Site_Clearance ends
	}

	private void init() {
		methodRef = getClass().getDeclaredMethods();
		sMap = new HashMap();
		for(Method m : methodRef) {
			sMap.put(m.getName(), m);
		}
	}

	public static DataManipulator getInstance()
	{
		if(objectRef != null)
		{ 
			//If class contain the object reference then return that
			return objectRef;
		}else
		{
			//Create the new object by calling private constructor and call getinstance again.
			objectRef	= new DataManipulator();
			return DataManipulator.getInstance();
		}
	}
	
	public static String transformDouble(String value ) {
		return value;
	}

	public static String transformCheckBox(Object id) {
		if(id == null) 
			return "";
		else 
			return (String)checkBoxMap.get(id.toString());
	}

	public static String transformCheckBox(String id ) {
		if(id == null) 
			return "";
		else 
			return (String)checkBoxMap.get(id);
	}

	public static String transformState(String id){
		String state = FieldNames.EMPTY_STRING;
		if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
			return state;
 		try{
 			if(StringUtil.isInt(id)) {
// 				state =	(String)com.home.builderforms.CacheDataUtil.getAllActiveStateMap().get(id); 				
 				state =	RegionMgr.newInstance().getRegionsDAO().getStateName(id); 				
 			} else {
 				state = id;
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
		//return LanguageUtil.getString(state);
 		return state;
	}
	
	public static String transformCountry(String id ) {
  		String country = FieldNames.EMPTY_STRING;
  		try{
  			if(StringUtil.isInt(id)) {
//  				country=(String)com.home.builderforms.CacheDataUtil.getCountryMap().get(id);
  				country = PortalUtils.getCountryNameById(id);
  			} else {
  				country = id;
  			}
  		} catch (Exception e) {
  			e.printStackTrace();
 		}
		//return LanguageUtil.getString(country);
		return country;
	}

	public static String transformYesNo(String id ) {
		try {
			if(id == null) 
				return "";
			else if(id.equals("1") || id.equalsIgnoreCase("Y") || id.equalsIgnoreCase("YES")) {
				return LanguageUtil.getString("Yes");
			} else if(id.equals("2") || id.equalsIgnoreCase("N")  || id.equalsIgnoreCase("NO")) {
				return LanguageUtil.getString("No");
			} else if(id.equals("3")) {
				return LanguageUtil.getString("Not Available");
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String transformMinusOne(String id) {
		try { 
			if(id == null) 
				return "";
			else if(id.equals("-1")) {
				return "";
			} else {
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String transformGender(String id ) {
		try {
			if(id == null) 
				return "";
			else if(id.equals("M")) {
				return "Male";
			} else if(id.equals("F")) {
				return "Female";
			} else if(id.equalsIgnoreCase("MALE")) {
				return "Male";
			} else if(id.equalsIgnoreCase("FEMALE")) {
				return "Female";
			}else
				return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	//P_Enh_Site_Clearance
	public static String getSiteLocationType(String locationType) {
		return LanguageUtil.getString(siteLocationTypeMap.get(locationType));
	}
	
	public static String transformLocation(String locationId) {
     		String locationName = FieldNames.EMPTY_STRING;
   		if(locationId == null || locationId.length()==0|| "-1".equals(locationId))
   			return locationName;
		try{
//			locationName =	(String)com.home.builderforms.CacheDataUtil.getAllActiveLocationMap().get(locationId);
			locationName =	(String)CommonMgr.newInstance().getCommonFsDAO().getAllActiveLocationMap().get(locationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationName;
   }
	
	public static String transformIdToValue(String id) {
		try {
			if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
    			return FieldNames.EMPTY_STRING;			
			else
				return (String)com.home.builderforms.CacheDataUtil.getMasterDataMap().get(id);
				//return MasterDataMgr.newInstance().getMasterDataDAO().getValueForId("8022",icpStatus);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return "";
	}
	public static String transformYandN(String id) {
		return transformYandN(id,null);
	}
	
	public static String transformYandN(String id,String dataValue) {
		try {
			if(id == null) 
				return "";
			else if(id.equals("1") || id.equalsIgnoreCase("Y") || id.equalsIgnoreCase("YES")) {
				return "Yes";
			} else if(id.equals("2") || id.equalsIgnoreCase("N")  || id.equalsIgnoreCase("NO")) {
				return "No";
			} else {
				return "No";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * P_B_35111
	 * @author Yashukant Tyagi
	 * @param fieldName
	 * @param dataValue
	 * @param conditionValue
	 * @return correct value for keyword
	 */
	public static String splitColOnParent(String fieldName,String dataValue,String conditionValue){
		if(!StringUtil.isValid(fieldName) || !StringUtil.isValid(dataValue)){
			return dataValue;
		}
		
		String returnValue = dataValue;
		String fieldNameCommon = FieldNames.EMPTY_STRING;
		if(fieldName.startsWith(FieldNames.FIM_HD_ROYALTY_TYPE_VALUE)){
			fieldNameCommon = FieldNames.FIM_HD_ROYALTY_TYPE_VALUE;
		}else if(fieldName.startsWith(FieldNames.FIM_HD_ADVERTISING_TYPE_VALUE)){
			fieldNameCommon = FieldNames.FIM_HD_ADVERTISING_TYPE_VALUE;
		}else if(fieldName.startsWith(FieldNames.FIM_HD_OTHER_FEE_VALUE)){
			fieldNameCommon = FieldNames.FIM_HD_OTHER_FEE_VALUE;
		}
		try{
			final String [] suffixesArray = new String[]{"Merge","Comments","Current","Past"};
			final List<String> royalityCols = Arrays.asList(fieldNameCommon+suffixesArray[0],fieldNameCommon+suffixesArray[1],fieldNameCommon+suffixesArray[2],fieldNameCommon+suffixesArray[3]);

			if(royalityCols.contains(fieldName) && "Percentage".equals(conditionValue)){
				int colPlace = royalityCols.indexOf(fieldName);
				switch(colPlace){
				case 0: case 1: default:
					returnValue = FieldNames.EMPTY_STRING;
					break;
				case 2:
					if(dataValue.indexOf(":")!=1){
						returnValue = returnValue.substring(0, returnValue.indexOf(":"));
					}
					break;
				case 3:
					if(dataValue.indexOf(":")!=1){
						returnValue = returnValue.substring(returnValue.indexOf(":")+1,returnValue.length());
					}
					break;
				}
			}
		}catch(Exception ex){
			logger.error("Error while manipulation splitColOnParent",ex);
		}
		return returnValue;
	}
	
	public static String transformRoyaltyType(String id) {
		try {
			if(id == null) 
				return "";
			else if(id.equals("F")) {
				return "Flat";
			} else if(id.equals("P")) {
				return "Percent";
			} else if(id.equals("R")){
				return "Rule Based";
			} else {
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String convertIdToValue(String dataValue) {
		return convertIdToValue(dataValue,null);
	}
	public static String convertIdToValue(String dataValue,String id) {
		try {
			String value = "";
			if(id == null || id.length()==0|| "-1".equals(id))
    			return FieldNames.EMPTY_STRING;			
			else
			{
                SequenceMap sMap=MasterDataMgr.newInstance().getMasterDataDAO().getDataMasterDataFieldType(id,dataValue);
                
                for(int i=0;i<sMap.size();i++)
                {
                	if(StringUtil.isValid(value))
                		value=value+","+(String)sMap.get(i);
                	else
                		value = (String)sMap.get(i);
                }
                return value;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return "";
	}
	
	
	public static String transformAddressIdToValue(String id)
	{
		try {
			if(StringUtil.isValidNew(id)) {
				return SQLUtil.getColumnValue("ADDRESS_STATUS", "ADDRESS_HEADING_NAME", "ADDRESS_HEADING_ID", id);
			} else {
				return FieldNames.EMPTY_STRING;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return "";
	}
	
	public static String transformDefaultIntegerValToBlank(String val) {
		try {
			if (val != null && val.equals(String.valueOf(IntConstants.DEFAULT_INT))) 
				return "";
			else
				return val;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String transformAreaIdToValue(String id){
		String areaName = "Default Area";            
		ResultSet result=null;
		try{
			String query = "SELECT AREA_NAME FROM AREAS WHERE AREA_ID ="+id ;
			 result	= QueryUtil.getResult(query,null);
			if(result != null && result.next()){
				areaName = result.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return areaName;
	}
	
	public static String transformMUIdToValue(String id){
        String muName = "";            
        ResultSet result=null;
        try{
        	String query = "SELECT MU_NAME FROM MU_DETAILS WHERE MU_ID ="+id ;
        	 result	= QueryUtil.getResult(query,null);
        	if(result != null && result.next()){
        		muName = result.getString(1);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return muName;
	}

	public static String transformAddress(String id, String fieldName){
		try{
			if(id == null) return "";
			else {
				String forType = fieldName.substring(0,fieldName.length()-1);
				String order = fieldName.substring(fieldName.length()-1);;
				return LocationMgr.newInstance().getLocationsDAO().getAddress(forType,order,id);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static String transformcallstatusToValue(String pDataId) {
		try {
			return AdminMgr.newInstance().getCallDAO().getCallStatusName(pDataId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static HashMap getCountryMap(){
		HashMap countryMap	=new HashMap();
		ResultSet result=null;
		try{
			String query ="SELECT COUNTRY_ID,NAME FROM COUNTRIES";
			 result				= QueryUtil.getResult(query,null);
			while(result.next()){
				countryMap.put(result.getString(1),result.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}


	return countryMap;
	}

	public static HashMap getStateMap(){
		HashMap stateMap	=new HashMap();
		ResultSet result=null;
		try{
			String query ="SELECT REGION_NO,REGION_NAME FROM REGIONS";
			 result				= QueryUtil.getResult(query,null);
			while(result.next()){
				stateMap.put(result.getString(1),result.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return stateMap;
	}
	
	public static String transFormCmMasterData(String id){

		try {
			if(id == null || id.length()==0|| "-1".equals(id))
    			return FieldNames.EMPTY_STRING;			
			else
				return  CacheMgr.getCmMasterDataCache().getCmMasterDataValue(id);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return "";	
	}
 public static String transFormCmStages(String id) {
		 
	     String val=null;
	 
		 if(id == null || id.length()==0|| "-1".equals(id)){
		 }else{
			 val=SQLUtil.getColumnValue("CM_OPPORTUNITY_STAGES","CM_STAGE_NAME","CM_STAGE_ID",id,null);
			 }
		 return val;
		 }	
	
 	public static String transformDateTimeFormat(String time)
	{
 		String formatedDateTime=FieldNames.EMPTY_STRING;
		String formatedTime=FieldNames.EMPTY_STRING;
		if(time!=null && !time.equals("")) {
			
			formatedDateTime=DateUtil.getDisplayDate(time);
			if(time.length()>11)
				formatedTime=DateTime.convertTime(time.substring(11));
			formatedDateTime=formatedDateTime+" "+formatedTime;
		}
		return formatedDateTime;
	}
 	//P_E_MoveToFim_AddlFDD starts
 	public static String transformDate(String id)
	{
		String returnDate;
		try{
			returnDate = DateUtil.getDisplayDate(id);
	    }catch (Exception e){
			e.printStackTrace();
			returnDate = "";
		}
		return returnDate;
	}
 	/**
 	 * @author priya kumari
 	 * This method is used for display date in datetime format
 	 * P_ENH_DATE_SUBMISSION
 	 */
 	public static String transformDateSubmission(String id)
	{
		String returnDate="";
		try{
			returnDate = DateUtil.getDisplayDate(id,MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT_HMA);
	    }catch (Exception e){
			logger.info("exception"+e);
		}
		return returnDate;
	}
 	//P_E_MoveToFim_AddlFDD ends
 	public static String getTransferStatus(String id)
	{
		try
		{
			if(id == null || id.equals("-1")) 
				return "";
			else
				return AdminMgr.newInstance().getFimTransferlDAO().getTransferStatusValue().getString(id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

 	public static String replaceHref(String content) {
		if(content!=null &&	!content.equalsIgnoreCase("null")){
			try {
				content =content.replaceAll("<a", "<a1");
				content =content.replaceAll("<A", "<A1 ");
			} catch (Exception e) {
				e.printStackTrace();
				content = "";
			}
		}else{
			content="";
		}
		return content;
	}
 	
 	public static String transformMailText(String templateVersionID)
	{
 		String content=CommonMgr.newInstance().getCommonFsDAO().getTemplateText(templateVersionID);
		return replaceHref(content);
	}
 	
 	public static String transformToManipulateValue(String value) {
		try {
			if(value == null) 
				return "";
			else {
				if(value.equals("Y")){				
					return "Yes";
				}else if(value.equals("N")){
					return "No";
				}else{
					return "";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
 	
 	public static String replaceCommaToCommaSpace(String value) {
		try {
			if(value!=null){
				value = value.replaceAll(" ", "");
				value = value.replaceAll(",", ", ");
			}else{
				value ="";
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
 	
 	public static String paddedString(String value)	{ 
 		try {
 			if(value!=null){
 				value = StringUtil.getPaddedString(value, "0", 11, true);
 			}else{
 				value ="";
 			}
 			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

 	public static String formatMonthsName(String id){
 		try{
 			if(id == null || id.equals("-1") ) return "";
 			else
 				return PortalUtils.getMonthName(Integer.parseInt(id));                
 		} catch (Exception e){
 			e.printStackTrace();
 		}
 		return "";
 	}
 	
 	public Info getAllRegionsInfo(Object regionId) {
 		try{
 			return RegionMgr.newInstance().getRegionsDAO().getAllAreas((String)regionId);
 		} catch(Exception e){
 			e.printStackTrace();
 			return null;
 		}
 	}

 	public Info getObjectInfo(Field fld) {
 		try{
 			return null;
 		} catch(Exception e){
 			e.printStackTrace();
 			return null;
 		}
 	}
 	
 	
 	
 	public static String transformAreaName(String id){
		try{
			if(id == null) return "";
			else
				return (String)RegionMgr.newInstance().getRegionsDAO().getAreaName(id);
				//return MasterDataMgr.newInstance().getMasterDataDAO().getValueForId("8022",icpStatus);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
 	
 	
 	public  static String transformStoreType(String id)
	{
		try
		{
			if(id == null) 
				return "";
			else
				return (String)LocationMgr.newInstance().getLocationsDAO().getFtName(id);
		} catch (Exception e)
		{
		e.printStackTrace();
		}
		return "";
	} 	
 	
 	
  public static String transformStateFromId(String id)
  {
   		String state = FieldNames.EMPTY_STRING;
		if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
			return state;

	try{
		
//		state =	(String)com.home.builderforms.CacheDataUtil.getAllActiveStateMap().get(id);
		state =	RegionMgr.newInstance().getRegionsDAO().getStateName(id);
	
	} catch (Exception e)
	{
		e.printStackTrace();
	}

	return state;
  }
  
         

  public static String transformStateFromId(String stateId, String countryId)
  {
	String state = FieldNames.EMPTY_STRING;
	try{
		
//		state =	(String)	com.home.builderforms.CacheDataUtil.getStateMap(countryId).get(stateId);
		state =	RegionMgr.newInstance().getRegionsDAO().getStateName(stateId);
	
	} catch (Exception e)
	{
		
            e.printStackTrace();
	}

	return state;
  }
  
  public static String transformStateFromIdWithAbbr(String stateId )
  {
	  String state = FieldNames.EMPTY_STRING;
	  ResultSet result=null;
	  try{
		  String query = "SELECT IF(REGION_ABBREV='',REGION_NAME,REGION_ABBREV) REGION_ABBREV FROM REGIONS WHERE REGION_NO=?";
		   result = QueryUtil.getResult(query, new String[] {stateId});
		  if(result.next()) {
			  state = result.getString("REGION_ABBREV");
		  }
	  } catch (Exception e)
	  {
		  
		  e.printStackTrace();
	  }
	  finally
		{
			QueryUtil.releaseResultSet(result);
		}

	  return state;
  }
  
  
  public static String transformCountryFromId(String id)
  {
		String country = FieldNames.EMPTY_STRING;
		try{
//			country=(String)com.home.builderforms.CacheDataUtil.getCountryMap().get(id);
			country = PortalUtils.getCountryNameById(id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//return LanguageUtil.getString(country);
		return country;
  }
  
  /**
   * This function provides the county Name
   * @param countyID
   * @param dependentFieldValue
   * P_Enh_County
   * @return
   */
  public static String transformCountyId(String countyID) {
	  String countyName = "";
		Map<String,String> countyMap = CacheMgr.getCountyCache().getCounty(countyID);
		if (countyMap != null && !countyMap.isEmpty()) {
			countyName = (String)countyMap.get(FieldNames.COUNTY_NAME);
		}
	  return countyName;
  }
  
  public static String transformPhoneFim(String id, String dependentFieldValue){
      
      String formatedPhone = "";
		if(id != null && !id.equals("")) {
			try{
				formatedPhone = PortalUtils.formatPhoneNo(id, dependentFieldValue);//Wee Watch phone numbers
			}catch(Exception e){
					logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformPhoneFim():" + e);
			}
		}
		return formatedPhone ;	 
	}
  
  public static String formatPhoneNO(String id){
      try{
          if(id == null || id.equals("-1") ) return "";
          else
              return PortalUtils.formatPhoneNo(id);                
      } catch (Exception e){
         e.printStackTrace();
      }
      return "";
  }
  
  
  //P_FS_Enh_BuilderForm starts
  /**
   * @author Naman Jain
   * used to get the forecast rating Name for the lead
   * @param id
   * @return String
   */
  public static String transformForecastRating(String id)
  {
	  String forecastRatingName = FieldNames.EMPTY_STRING;
	  forecastRatingName = SQLUtil.getLastColumnValue("FS_LEAD_FORECAST_RATING", "FORECAST_RATING_NAME", "FORECAST_RATING_ID", id);
	  return forecastRatingName;
  }
  
  /**
   * @author Naman Jain
   * this function provides the liquidCapitalMin for the lead
   * @param id
   * @return
   */
  public static String transformNetWorthScores(String id) {
	  String liquidCapitalMin = FieldNames.EMPTY_STRING;
	  liquidCapitalMin = CommonUtil.getComboInfo("NET_WORTH_SCORES","VALUES").getString(id);
	  return liquidCapitalMin;
  }
  
  /**
   * @author Naman Jain
   * this function provides the liquidCapitalMax for the lead
   * @param id
   * @return
   */
  public static String transformCashAvailableInvestment(String id) {
	  String liquidCapitalMax = FieldNames.EMPTY_STRING;
	  liquidCapitalMax = CommonUtil.getComboInfo("LIQUIDITY_SCORES","VALUES").getString(id);
	  return liquidCapitalMax;
  }
  
  
  /**
   * @author Naman Jain
   * this function provides investment time frame
   * @param id
   * @return
   */
  public static String investTimeFrame(String id) {
	  String investTimeframe = FieldNames.EMPTY_STRING;
	  investTimeframe = CommonUtil.getComboInfo("INVESTMENT_TIME_FRAME_SCORES","VALUES").getString(id);
	  return investTimeframe;
  }
  
  /**
   * @author Naman Jain
   * this function provides background
   * @param id
   * @return
   */
  public static String transformEmploymentBackground(String id) {
	  String background = FieldNames.EMPTY_STRING;
	  Info backGrounfInfo = CommonUtil.getBackGroundInfo();
	  background = backGrounfInfo.getString(id);
	  return background;
  }
 
  /**
   * gives the owner name corresponding to the id
   * @param id
   * @return
   * @throws Exception
   */
  public static String getLeadOwnerId(String id) throws Exception {
	  try {
			return CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwners().get(id);
		} catch(Exception e) {
			//Debug.print(e);
			e.printStackTrace();
		}
		return "";
  }
  
  /**
   * gives the rating name for the lead
   * @param id
   * @return
   * @throws Exception
   */
  public static String getRatingName(String id) throws Exception {
	  String leadRatingName = CommonMgr.newInstance().getCommonFsDAO().getLeadRatingValue(id);
		if (!StringUtil.isValidNew(leadRatingName)) {
			leadRatingName = "";
		}
	  return LanguageUtil.getString(leadRatingName);
  }
  
  /**
   * gives the marketing name for the lead
   * @param marketingCode
   * @return
   */
  public static String transformLeadMarketingCode(String marketingCode)
  {
	  String marketingCode1  = NewPortalUtils.getColumnFromTable("FS_LEAD_MARKETING_CODE", "LEAD_CODE_NAME", "LEAD_CODE_ID", marketingCode);
	  return marketingCode1;
  }
  /**
   * ADDED_BROKER_NAME_MAIL_MERGE
   * gives the broker name for the lead
   * @param brokerId
   * @return
   */
  public static String transformBrokerId(String brokerId){
		String result	=null;
		try{
			result=SQLUtil.getColumnValue("BROKER_DETAILS", "CONCAT(FIRST_NAME,' ',LAST_NAME)", "BROKER_ID", brokerId);
			
		}catch(Exception e){
			logger.error("\nException in transformBrokerId(brokerId)"+e);
		}finally{
			return result;
		}
	}
  
  public static String getFsCampaignName(String campaignId) {
	 return NewPortalUtils.getColumnFromTable("FS_CAMPAIGN", "CAMPAIGN_TITLE", "CAMPAIGN_ID", campaignId); 
  }
  
  public static String transformLeadSource2(String sourceId) {
	  return transformLeadSource2(sourceId, null);
  }
  
  public static String transformLeadSource2(String sourceId, String sourceContactType){
		String result	=null;
		try{
            if(StringUtil.isValid(sourceContactType) && sourceContactType.indexOf("_") != -1) {
                result = AdminMgr.newInstance().getFsLeadSource2DAO().getLeadSourceCategory().get(sourceContactType);
            } else {
                result = AdminMgr.newInstance().getFsLeadSource2DAO().getLeadSourceCategory().get(sourceId);
            }
		}catch(Exception e){
			logger.error("\nException in transformLeadSource2(sourceId)"+e);
		}finally{
			return result;
		}
	}
	public static String transformLeadSource3(String sourceId){
		String result	=null;
		try{
			result=AdminMgr.newInstance().getFsLeadSource3DAO().getLeadSourceDetails(null).get(sourceId);

		}catch(Exception e){
			logger.error("\nException in --> transformLeadSource3(sourceId)"+e);
		}finally{
			return result;
		}
	}

  
 /* public static String getCountryName(String country)
  {
	  return country;
  }
  public static String getStateName(String state)
  {
	  return state;
  }
*/  
  public static String transformTimeFormat(String time)
	{
		return transformTimeFormat(time,null);
	}
	
	public static String transformTimeFormat(String time,String date)
	{		  BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		String tZConvertedDateTime = "";
		String formatedTime=FieldNames.EMPTY_STRING;
		if(date!=null){
			try{
				date = DateUtil.getDisplayDate(date);
				date = date+" "+time;	
		 		tZConvertedDateTime =TimeZoneUtils.performUTCConversion(_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, DateTime.getRequiredFormat(date, _baseConstants.DISPLAY_FORMAT_HMS, "yyyy-MM-dd HH:mm:ss"), TimeZoneUtils.DB_DATETIME, TimeZoneUtils.DB_DATETIME );
				time = tZConvertedDateTime.substring(11,tZConvertedDateTime.length());
			}catch(Exception e){
				time = "";
			}
		}
		//P_EXPORT_DATE_ACC_TIMEZONE : ends
		if(time!=null && !time.equals(""))
		{
				String timeAdd=time;
				int hour=Integer.parseInt(timeAdd.substring(0,2));
				String ampm= " AM";
				String zero = "0";
				if(hour>12)
				{
					ampm=" PM";
					hour=hour-12;
				}
				if(hour==12 && ampm.equals(" AM"))
				{
					ampm=" PM";
				}
				if(hour==0)
				{
					hour=12;
					ampm=" AM";
				}
				if(hour<10)
				{
					formatedTime  =  zero + hour + timeAdd.substring(2,5) + ampm;
				}else
					formatedTime  =  hour + timeAdd.substring(2,5) + ampm;
		}

		return formatedTime;
	}
  //P_FS_Enh_BuilderForm ends
	
	
	
  public String transform(Field pField, String psValue,String psDependentFieldValue,String paramData) throws Exception {
	  String sMethodName 			= 	pField.getTransformMethod();
	  String sMethodNameParam 	= 	pField.getTransformMethodParam();
	  if (sMethodName == null) {
		  return psValue;
	  }
	  Class clsArr[] 				= 	new Class[] { String.class};
	  Object valArr[] 			= 	new Object[] { psValue};
	  if (StringUtil.isValid(sMethodNameParam))
	  {
		  clsArr 			= 		new Class[] { String.class, String.class};
		  valArr 			= 		new Object[] { psValue, psDependentFieldValue};
	  }else  if (StringUtil.isValid(sMethodName) && StringUtil.isValid(paramData)) {
		  clsArr 			= 		new Class[] { String.class, String.class};
		  valArr 			= 		new Object[] { psValue, paramData};
	  }
	  Method method = this.getClass().getDeclaredMethod(pField.getTransformMethod(), clsArr);
	  String sNewVal = (String) method.invoke(null, valArr);
	  //System.out.println("pfield name===============::::::::::;"+pField.getFieldName());
	  //System.out.println("sNewVal:::::::::::::::::::::"+sNewVal);
	  return sNewVal;
  }
	//P_CM_Enh_BuilderForm starts
	public static String transformIdFromSrc(Field fld, String id)
	{
		String 	returnValue = id;   
		try
		{
			if(id == null) 
				returnValue ="";
			else{
				String srcTable = fld.getSrcTable();
	 			String srcField = fld.getSrcField();
	 			String srcValue = fld.getSrcValue();
	 			FieldMappings fldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(srcTable);
	 			String table = fldMapping.getTableName();
	 			String idField = fldMapping.getDbField(srcField);
	 			String valueField = fldMapping.getDbField(srcValue);
	 			returnValue = SQLUtil.getColumnValue(table, valueField, idField, id);        
	 	}
		} catch (Exception e)
		{
			logger.error(e);
		}
		return returnValue;
	}
	public static String getMarketingCodeName(String id,String franchiseeNo)
	{
		
		String returnValue=id;
		try
		{
			if(!AdminMgr.newInstance().getStoreDAO().isFranchisee(franchiseeNo))
				franchiseeNo="1";
			returnValue=SQLUtil.getColumnValue("CM_COST_SHEET" ,"MP_MARKETING_NAME",new String[] {"CM_MARKETING_ID","CREATED_BY"},new String[]{id,franchiseeNo});
		}catch (Exception e)
		{
			logger.error(e);
		}
		
		return returnValue;
	}
	public static String getTotalUrlVisited(String url,String contactID)
	{
		String returnValue=null;
		try
		{
			String query= "SELECT COUNT(URL_VISITED) URL_VISITED FROM CM_CAMPAIGN_EMAIL_LOG WHERE CONTACT_ID="+contactID+" AND URL_VISITED > '0' GROUP BY CONTACT_ID";
			returnValue=SQLUtil.getQueryResult(query, "URL_VISITED");
			
		}catch (Exception e)
		{
			logger.error(e);
		}
		if(!StringUtil.isValid(returnValue))
			returnValue="0";
		return returnValue;	
	}
	public static String getTotalMailSent(String contactID)
	{
		String returnValue=null;
		try
		{
			String query= "SELECT COUNT(CCEL.CONTACT_ID) COUNT_MAIL FROM CM_CONTACT_DETAILS CD LEFT JOIN CM_CAMPAIGN_EMAIL_LOG CCEL ON CD.CONTACT_ID=CCEL.CONTACT_ID WHERE CD.CONTACT_ID="+contactID;
			returnValue=SQLUtil.getQueryResult(query, "COUNT_MAIL");
			
		}catch (Exception e)
		{
			logger.error(e);
		}
		if(!StringUtil.isValid(returnValue))
			returnValue="0";
		return returnValue;	
	}
	public static String getTotalMailRead(String contactID)
	{
		String returnValue=null;
		try
		{
			String query= "SELECT COUNT(MAIL_READ) COUNT_MAIL_READ FROM CM_CAMPAIGN_EMAIL_LOG WHERE CONTACT_ID="+contactID+" AND MAIL_READ > '0' GROUP BY CONTACT_ID";
			returnValue=SQLUtil.getQueryResult(query, "COUNT_MAIL_READ");
			
		}catch (Exception e)
		{
			logger.error(e);
		}
		if(!StringUtil.isValid(returnValue))
			returnValue="0";
		return returnValue;	
	}
	public static String getLastMailRead(String contactID)
	{
		String returnValue=null;
		try
		{
			String query= "SELECT TEMPLATE_ID, MAIL_READ FROM CM_CAMPAIGN_EMAIL_LOG WHERE MAIL_READ IS NOT NULL AND MAIL_READ <> '' AND CONTACT_ID="+contactID+" ORDER BY MAIL_READ DESC LIMIT 1";
			String templateID=SQLUtil.getQueryResult(query, "TEMPLATE_ID");
			
			if(StringUtil.isValid(templateID))
			{
				query="SELECT MAIL_SUBJECT FROM CM_TEMPLATES WHERE MAIL_TEMPLATE_ID="+templateID;
				returnValue=SQLUtil.getQueryResult(query, "MAIL_SUBJECT");
			}
			
			
		}catch (Exception e)
		{
			logger.error(e);
		}
		if(!StringUtil.isValid(returnValue))
			returnValue="None";
		return returnValue;	
	}
	public static String transformOwnerType(String ownerType)
	{
		if("F".equals(ownerType))
			return "Franchisee";
		else
			return "Corporate";
		
		
	}
	public static String checkNonApplicability(String value)
	{
		if(!StringUtil.isValid(value))
			return "N/A";
		else
			return value;
	}
	public static String getOwnerFirstName(String id)
	{
		String returnValue="";
		StringBuilder query=new StringBuilder("SELECT O.OWNER_NAME  FROM (SELECT FU.FIRST_NAME AS OWNER_NAME ,CLD.CONTACT_ID FROM  CM_CONTACT_DETAILS CLD,FRANCHISEE_USERS FU,USERS U WHERE CLD.CONTACT_OWNER_ID=U.USER_NO AND U.USER_IDENTITY_NO=FU.FRANCHISEE_USER_NO"); 
                            query.append("UNION");
                            query.append("SELECT F.FIRST_NAME AS OWNER_NAME,CLD.CONTACT_ID FROM  CM_CONTACT_DETAILS CLD,FRANCHISEE F,USERS U WHERE CLD.CONTACT_OWNER_ID=U.USER_NO AND U.FRANCHISEE_NO=F.FRANCHISEE_NO AND U.USER_IDENTITY_NO=-1) AS O  WHERE O.CONTACT_ID=?");
        try {
        	returnValue=SQLUtil.getQueryResult(query.toString(), "OWNER_NAME");
		} catch (Exception e) {
			logger.error("Exception in getOwnerFirstName()::"+e);
		} 
        
        return returnValue;
	}
	//GROUP_FILTER_ENH starts
	public static String transformGroup(String groupId){
		String name = "";
		if(groupId == null) return "";
		else{
			
			try{
				name = NewPortalUtils.getColumnFromTable("CM_GROUPS", "GROUP_NAME", "GROUP_ID", groupId);
			}catch(Exception e){
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformGroup(groupId)"+e);
			}
		}

			return name;
	}
	//GROUP_FILTER_ENH ends
  //P_CM_Enh_BuilderForm ends
	
	public static String transformCmLeadStatus(String statusID){
		String name = "";
		if(statusID == null) return "";
		else{
			try{
				name = NewPortalUtils.getColumnFromTable("CM_LEAD_STATUS_NEW", "CM_LEAD_STATUS_NAME", "CM_LEAD_STATUS_ID", statusID);
			}catch(Exception e){
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transofrmLead(statusID)"+e);
			}
		}
		return name;
	}

	
	
		//P_SCH_ENH_008 Starts
	  public static String transformJobTypeCombo(String jobCategoryId){
		  try{
				if(StringUtil.isValid(jobCategoryId))
				{
						return NewPortalUtils.getColumnFromTable("JOB_CATEGORIES", "JOB_CATEGORY_NAME", "JOB_CATEGORY_ID", jobCategoryId);
				}
				else
					return "";
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return "";
		}
	  
	  
	  public static String transformJobOwner(String userID){
		  String ownerName="";
		  // Bug 50532
		  String query="SELECT CONCAT(USER_FIRST_NAME,' ',USER_LAST_NAME) AS JOB_OWNER FROM USERS U WHERE U.USER_NO ="+userID +" AND U.IS_DELETED='N' ";
		  ResultSet rs= QueryUtil.getResult(query, null);
		  if(rs.next())
			  ownerName=rs.getString("JOB_OWNER");
		  QueryUtil.releaseResultSet(rs);
		  return ownerName;
	  }
	  
	public static String transformJobStatus(String statusID){
		  String jobStatus= SQLUtil.getColumnValue("MASTER_DATA_FOR_SCHEDULER" , "DATA_VALUE", "MASTER_DATA_ID", statusID);
		  return jobStatus;
	  }
	
	public static String transformFranchiseeName(String id){
        
        if(!StringUtil.isValid(id) ) return "";
        try{
			String query ="SELECT FRANCHISEE_NAME FROM FRANCHISEE WHERE FRANCHISEE_NO="+id;
			ResultSet result				= QueryUtil.getResult(query,null);
			if(result.next()){
				return result.getString("FRANCHISEE_NAME");	
			}
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/DataManipulator.java--> transformFranchiseeName(id):" + e);
		}

		return "";
   }
	//P_SCH_ENH_008 Ends
	
	public static String transformLeadStatus(String statusId){
		String leadStatus="";
		try{
			leadStatus				= (String)AdminMgr.newInstance().getFsLeadStatusDAO().getLeadStatus().get(statusId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLeadStatus(statusId)>>"+e);
		}
			return leadStatus;
		
	}
	
	public static String transformContactRating(String ratingId){
		String result	=null;
		try{
			MasterDataMgr.newInstance().getMasterDataDAO();
			result = MasterDataDAO.getDataValue("9003", ratingId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformContactRating()"+e);
		}
		return result;
	}
	
	public static String formatCommaNumber(String value) {
		return NumberFormatUtils.formatCommaNumber(value,true);
	}
	
	public static String  formatPhoneNoAccCountry(String id,String countryID) {
		try{
			String country="";
			if(StringUtil.isRequestParamValid(countryID)){
				country=NewPortalUtils.getCountryName(countryID);
			}
			if(id == null || id.equals("-1") ) return "";
			else
				return PortalUtils.formatPhoneNo(id,country);                
		} catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public static String transformRelationship(String id) {
		String value = SQLUtil.getColumnValue("COAPPLICANT_RELATIONSHIP", "COAPPLICANT_RELATIONSHIP", "COAPPLICANT_RELATIONSHIP_ID", id);
		if(!StringUtil.isValid(value)) {
			value = FieldNames.EMPTY_STRING;	
		}
		return value;
	}
	
	  //BOEFLY_INTEGRATION : START
	  public String generateBoeflyUrl(String urlMailMerge,String leadId){
		  StringBuilder returnVal = new StringBuilder();
		  if("on".equals(MultiTenancyUtil.getTenantConstants().BOEFLY_INTEGRATION_STATUS))
		  {
			  if(boeflyUrl==null){
				  boeflyUrl = SQLUtil.getColumnValue("FS_BQUAL_INTEGRATION_DETAILS","BOEFLY_URL",EMPTY_STRING,EMPTY_STRING);
				  if(boeflyUrl.charAt(boeflyUrl.length())!='?'){
					  returnVal.append('?');
				  }
				  returnVal.append("fcid").append("=");
			  }

			  returnVal.append(leadId);
		  }
		  return returnVal.toString();
	  }
	  
		public static String transformBinaryToBoolean(String val) {
			String retVal = "";
			if("1".equals(val)){
				retVal = "TRUE";
			}
			return retVal;
		}
		public static String transformDateWithTime(String value) throws Exception
		{		  BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			if(StringUtil.isValid(value)){
				String[] tempArr =  value.split(" ");
				value = DateUtil.getDisplayDate(tempArr[0]);
				if(tempArr.length > 1){
					value = value + " " + convertTime(tempArr[1]);
					value = TimeZoneUtils.performUTCConversion(
							_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, _baseConstants.USER_TIME_ZONE,
							value, _baseConstants.DISPLAY_FORMAT_HMA,
                            _baseConstants.DISPLAY_FORMAT_HMA);
				}else{
					value = DateUtil.getDisplayDate(value);
				}
			}
			return value;
		}	
		
		public static String convertTime(String dateTime) throws Exception 
		{
			StringBuffer displayTime = new StringBuffer();
			//P_b_54997 add in try catch and put condition if null or length less than 1
			try
			{
				if(dateTime != null && dateTime.length()>1)
				{
					String sTime = dateTime.substring(0, 2);
					String sMinute = dateTime.substring(3, 5);

			
					if (sTime != null)
						sTime = sTime.trim();
			
					if (sMinute != null)
						sMinute = sMinute.trim();
			
					int scheduledTimeInt = Integer.parseInt(sTime);
					int scheduledMinuteInt = Integer.parseInt(sMinute);
					int apmInt = 0;
			
					if (scheduledTimeInt > 12) {
						scheduledTimeInt = scheduledTimeInt - 12;
						apmInt = 1;
					}
			
					if (scheduledTimeInt == 12) {
						apmInt = 1;
					}
			
			
					if (scheduledTimeInt < 10) {
						displayTime.append("0");
						displayTime.append(scheduledTimeInt);
					} else
						displayTime.append(scheduledTimeInt);
			
					displayTime.append(":");
			
					if (scheduledMinuteInt < 10) {
						displayTime.append("0");
						displayTime.append(scheduledMinuteInt);
					} else
						displayTime.append(scheduledMinuteInt);
			
					displayTime.append(" ");
			
					if (apmInt == 0) {
						displayTime.append("AM");
					} else
						displayTime.append("PM");
				}
			}
			catch(Exception e)
			{
				logger.error("ERROR: in convertTime in NewLeadPrimaryInfoAction.java:-"+e.getMessage());
			}
			return displayTime.toString();

		}
		public static String tranformModeOfContact(String primaryPhoneToCall) 
		{
			/*if(!StringUtil.isValid(primaryPhoneToCall)) 
			{
				primaryPhoneToCall = "-1";
			}else if(!(primaryPhoneToCall.equals("Fax") || primaryPhoneToCall.equals("Mobile") || primaryPhoneToCall.equals("Email") || primaryPhoneToCall.equals("Work Phone") || primaryPhoneToCall.equals("Home Phone"))){
					primaryPhoneToCall = "-1";
			}*/

			FieldMappings fieldMappings = DBUtil.getInstance().getFieldMappings("fsLeadDetails"); //becausse this field is present in this table
			if(StringUtil.isValidNew(primaryPhoneToCall)) {
				if(fieldMappings != null) {
					if(primaryPhoneToCall.contains("_")) {
						String fieldName = primaryPhoneToCall.substring(0, primaryPhoneToCall.lastIndexOf("_contactMode")); //this will give the fieldName
						primaryPhoneToCall = fieldMappings.getField(fieldName).getDisplayName();
					} else {
						//do nothing
					}
				} else {
					primaryPhoneToCall = "";
				} 
			} else {
				primaryPhoneToCall = "";
			}

			return LanguageUtil.getString(primaryPhoneToCall);
		}
		//BOEFLY_INTEGRATION : END
		
		public static String transformDateForFranchiseAwarded(String transdatetime,String franchiseAwarded)
    	{
    		if("Yes".equals(franchiseAwarded))
    		{
    			if(transdatetime == null) 
                	return "";
                else
                	return transformDate(transdatetime);
    		}
    		return "";
    	}
		
		public static String transformFranchiseAwarded(String value) {
			String newValue = "No";
			if("Yes".equals(value)) {
				newValue = "Yes";
			}
			return newValue;
		}
		
		public static String transformUnitsForFranchiseAwarded(String unit,String franchiseAwarded)
    	{
    		if("Yes".equals(franchiseAwarded))
    		{
    			return unit;
    		}
    		else
    		{
    		return "";
    		}
    	}
		
		
		// Added By Amit Tanwani CRM_OPPORTUNITY_INFO Starts
		
		public static String validateAccountName(Field fld){
			StringBuffer jsCode=new StringBuffer(""
					+ " if(!isValidAccount()) 	{"
					+ "	alert(\"Please enter a valid "+fld.getDisplayName()+"\");"
					+ " document.form1."+fld.getFieldName()+".focus();"
					+ " return false;}"
					);

			return jsCode.toString();
		}
		
		public static String validateParentAccountName(Field fld){
			StringBuffer jsCode=new StringBuffer(""
					+ "	if(document.form1.isParent.checked==true){"
					+ "if(!isValidParentAccount()) 	{"
					+ "	alert(\"Please select "+fld.getDisplayName()+"\"); "
					+ " document.form1.parentAccountID.focus(); "
				    + " return false;}}"
					);
			
			return jsCode.toString();
		}

		public static String transFormAccountType(String accountType){
			if("N".equals(accountType)){
				return "B2C";
			}else{
				return "B2B";
			}
		}

		public static String transFormCompanyType(String id){
			return 	SQLUtil.getColumnValue("CM_COMPANY_TYPE","COMPANY_TYPE", "COMPANY_TYPE_ID",id) ;	 
		}
		
		public static String transformParentAccount(String id) {
			String query = "SELECT COMPANY_NAME FROM CM_COMPANY_INFO WHERE COMPANY_ID ='"+id+"'";//P_CM_B_67732 
			ResultSet rs			= QueryUtil.getResult(query, null);
			if(rs.next()){
				return rs.getString("COMPANY_NAME"); 
			} else {
				return "";
			}
		}
		
		public static String validateOpportunityType(Field fld)
		{
			StringBuffer jsCode=new StringBuffer(""
					+ "	if(!isValidAccount() && !isValidContact()) 	{"
					+ "	alert(\""+LanguageUtil.getString("Please select "+fld.getDisplayName())+"\"); return false;}"
					);
			
			return jsCode.toString();
		}
		
		public static String validateOpportunityOwner(Field fld)
		{
			StringBuffer jsCode=new StringBuffer(""
					+ "	if(!isValidOwner()) 	{"
					+ "	alert(\""+LanguageUtil.getString("Please select "+fld.getFieldName())+"\");"
					+ " document.form1."+fld.getFieldName()+".focus();"
					+ " return false;}else{ if(!isValidFranchisee()){return false}; }"
					);

			return jsCode.toString();
		}
		
		public static String validateProbability(Field fld)
		{
			StringBuffer jsCode=new StringBuffer("if(!isValidProbability()) {return false;}");
			
			return jsCode.toString();
		}
		
		public static String transformContactName(String contactId){

			String  newValue=null;
			newValue=SQLUtil.getColumnValue("CM_CONTACT_DETAILS","CONCAT(CONTACT_FIRST_NAME,' ',CONTACT_LAST_NAME)","CONTACT_ID",contactId);
			//if(!isPrint){
				newValue="<a href=\"javascript:void(0)\" onclick=\"javascript:showDetails('"+contactId+"','contact')\">"+newValue+"</a>";
			//}

			return newValue ;	 
		}
		public static String transformAccountName(String accountId){

			String newValue=null;			
			newValue=SQLUtil.getColumnValue("CM_COMPANY_INFO","COMPANY_NAME","COMPANY_ID",accountId);
			//if(!isPrint){
			 UserRoleMap userRoleMap = (UserRoleMap) StrutsUtil.getHttpSession().getAttribute("userRoleMap");
			 if(userRoleMap.isPrivilegeInMap("/companySummary")) //P_CM_B_68658
				newValue="<a href=\"javascript:void(0)\" onclick=\"javascript:showDetails('"+accountId+"','account')\">"+newValue+"</a>";
			//}

			return newValue ;	 
		}
		// Added By Amit Tanwani CRM_OPPORTUNITY_INFO Ends
		//P_Enh_ContactHistory_FormBuilder starts
		public static String getLeadName(String leadId,String forArchived){
	    	return CommonMgr.newInstance().getCommonFsDAO().getLeadName(leadId, forArchived);	
	    }
		public static String transformDateTimeForTimeLessTask(String time,String duration)
		{
	 		String formatedDateTime=FieldNames.EMPTY_STRING;
			String formatedTime=FieldNames.EMPTY_STRING;
			if("00:00:00".equals(duration))
			{
				if(time!=null && !time.equals(""))
		 			formatedDateTime=DateUtil.getDisplayDate(time)+" (No Time Scheduled)";
		 		else
		 			formatedDateTime="Not Scheduled";
			}
			else
			{	
				if(time!=null && !time.equals(""))
		 		{
					formatedDateTime=DateUtil.getDisplayDate(time);
					if(time.length()>11)
						formatedTime=DateTime.convertTime(time.substring(11));
					formatedDateTime=formatedDateTime+" "+formatedTime;
				}
			}
			return formatedDateTime;
		}
		//ENH_PW_FBC Starts
		public  static String transformfbc(String id)
		{
			try
			{
				if(id == null) 
					return "";
				else
					return (String)PortalUtils.getUserName(Integer.parseInt(id));
			} catch (Exception e)
			{
			e.printStackTrace();
			}
			return "";
		}
		//ENH_PW_FBC Ends
		 public static String transformUser(String id){
			 	String name = "";
				if(id == null) return "";
				else{
					
					try{
						name =PortalUtils.getUserName(id);
					}catch(Exception e){
					
					}
				}
					return name;
			}
		 public static String transformTaskIdToValue(String id)
		 {
			try
			{
				if(StringUtil.isValidNew(id))
				{
					return SQLUtil.getColumnValue("TASK_TYPE", "TT_NAME", "TT_ID", id);
				}
				else
				{
					return FieldNames.EMPTY_STRING;
				}
			} catch (Exception e)
			{
				logger.error("ERROR: in transformTaskIdToValue in datamanipulator:-"+e.getMessage());
			}
			return "";
		 }
		 public static String getCallTypeName(String callTypeId)
		 {
			 return AdminMgr.newInstance().getCallDAO().getCallTypeName(callTypeId);
		 }
		 public static String transformcalltypeToValue(String callTypeId){
			 return AdminMgr.newInstance().getCallDAO().getCallTypeName(callTypeId);
		 }
		 
		 public static String getHotActivity(String noUseParam){
			 
			 HttpServletRequest request=StrutsUtil.getHttpServletRequest();
			 
			 String contactID=(String)request.getSession().getAttribute(FieldNames.CONTACT_ID);
			 String user_No=(String)request.getSession().getAttribute("user_no");
			 String forArchived=(String)request.getSession().getAttribute("forArchived");
			 
			 String hotActivity="";
			 if(contactID!=null){
				 hotActivity=AdminMgr.newInstance().getCmContactHotActivityDAO().getActivity(contactID, user_No, forArchived);
			 }
			 
			 return hotActivity;
		 }
		//P_Enh_ContactHistory_FormBuilder ends
		 //P_ENH_ASSIGN_TO_USERS_TASK starts
		 public static String transformUserAssignTo(String AssignTo,String TaskID){
			 String query ="SELECT ASSIGN_TO FROM TASK_OWNER_MAPPING WHERE TASK_ID="+TaskID;
			 ResultSet result = QueryUtil.getResult(query,null);
			 String assignTo="";
			 String[] userAssign=null;
			 String name ="";
			 int count = 0;
			 String name1 ="";
			 try{
				while(result.next()){
					assignTo =	result.getString("ASSIGN_TO");
					if(StringUtil.isValid(assignTo))
		             {
		            		 name = PortalUtils.getUserName(assignTo);
		            		 if(StringUtil.isValid(name)){
		            			 if(count == 0){
		            				 name1 = name;
		            			 }else{
		            				 name1= name1+", "+name;
		            			 }
		            			 count++;
		            		 }else {
		            			return  name1;
		            		 }
		             }
				}
			}catch(Exception e){
				logger.error("tasks transformUserAssign>>>>" + e);
			}
             
			return name1;
			 
		 }//P_ENH_ASSIGN_TO_USERS_TASK ends
		 public static String getRenewalOption(String renewalOptions,String consecutive){
			 String renewalOption="";
			 if(StringUtil.isValidNew(consecutive) && StringUtil.isValidNew(renewalOptions)){
			  renewalOption = "<b>"+renewalOptions+"</b>&nbsp;Consecutive&nbsp;<b>"+consecutive+"</b>&nbsp;Years term";
			 }
			 
		 return renewalOption;
		 }
}
