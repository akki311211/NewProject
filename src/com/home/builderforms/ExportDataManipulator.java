package com.home.builderforms;

//Import statements

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.AdminMgr;
import com.home.builderforms.RegionMgr;
import com.home.builderforms.CommonMgr;

import org.apache.log4j.Logger;

//import com.home.builderforms.LocationMgr;
import com.home.builderforms.MasterDataMgr;
import com.home.builderforms.MasterDataDAO;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateTime;
import com.home.builderforms.DateUtil;
import com.home.builderforms.Debug;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IntConstants;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.NumberFormatUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.StringUtil;
import com.home.builderforms.TimeZoneUtils;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;

/**
 * $Header:  com.appnetix.app.portal.export.ExportDataManipulator
 * $Author: vinay.gupta $AmitGokhru
 * $Version:  $1.1
 * $Date: 2016/10/18 13:01:22 $18 May 2004
 *
 * Basic Description/Data Flow of Bean
	This class contain all the manipulator methods whose names are given in the table xml.
	Methods from this class will be called by the ExportDataCollector for each field in 
	which trasform method is given in the XML file.
	This is a Singleton class and its getInstance method is the only way to get its instance.

 
 * Other's beans called with methods
	com.home.builderforms.xmldao.XMLUtil
	*@ Modified  for supplies
	*@ Author Vikas
	*@date Tue 6,Dec 2005

------------------------------------------------------------------------------------------------------------
Version No.			Date		By				Against					Function Changed    Comments
------------------------------------------------------------------------------------------------------------
P_E_STORE_2307    	23 oct 07   Niraj Sachan    Base Build Enhansement       transformStoreType()  AQdd the  transformStoreType method           
P_E_DATE_FORMAT   	30/05/2008  Nikhil Verma   	Date Format enhancement.
P_E_PHONE_FORMAT  	16/07/2008  Manoj Kumar	 	Phone Format According to Country
P_FS_B_36928		28/07/2008	Ravindra Verma	transformDate() method was throwing NullPointer for certain Date Input values.
P_FIM_B_39358       06/10/2008       Nikhil Verma     transformDateTimeForTimeLessTask(),transformEndDateTimeForTask()    If task is time less ,Duration is zero.
BB_FIN_B_41469		04Dec2008	YogeshT			B_41469					-added a method named 'paddedString'.
P_FIM_B_41827       23/12/2008  Abhishek Rastogi   transformPictureData()    for getting the picture file name with out ID being appended
P_FS_E_1000         24/12/2008  Abhishek Rastogi   transformState()         for getting the State Name from the cache which is stored in Sequence Map.----
P_FS_E_1001         26/12/2008  Abhishek Rastogi   transformLocation()     for getting the Preferred Location City,State and Location Site name from the cache which is stored in Sequence Map.-
DemoBuild_CR_FS_001 15-Jan-2010 Anuradha Tiwari   Private field changes   added a method formatMonthsName()
P_FIM_B_1417        30 Dec 2011  Himanshi Gupta    transformAddressHeading()  for getting the Address Heading from id.
P_FIM_B_4340        2 Feb 2012   Shashank Kukreti 	transformAddressIdToValue() for converting value of Address Heading in FIM > Other Addresses in Trigger History
P_FS_B_5243			27Mar2012	Vivek Maurya	Bug		
P_SUPP_B_14520      9 oct 2012   Megha Chitkara           BUG           changed to convert the purchaseorder no 11 digits String to 7 digit for export
CR-BP-20121218-563	02 Jan 2013		Dravit Gupta		Gui getting distorted in case of Comments.
P_B_SUPP_23391		06 July 2013	Deepak Gangore		Contact Name(Suppier name) was not appearing in export. transformFunction has been changed.			                          
P_EXPORT_DATE_ACC_TIMEZONE	04 Dec 2013	Niranjan Kumar		Export Date according to TimeZone 
GROUP_FILTER_ENH  			15 jan 2014   		Deepanshi Miglani    Enh        for adding group filter in CM Search
P_B_SUPP_35187      25/04/2014    Gagan Yadav                   BUG             Product color and size is not present in Export.
P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
LISTEN360_INTEGRATION			01/12/2014		Prateek Sharma		Ens			A third party integration with Listen360.
PROVEN_MATCH_INTEGRATION		28/11/2014		Amar Singh		Ens			A third party integration with Proven Match through REST-API for lead sync. 
 * -------------------------------------------------------------------------------------------------------
*/
public class ExportDataManipulator
{

	/**
		reference for storing the class's object
	*/
	
	static private ExportDataManipulator objectRef	= null;

	/**
		Logger
	*/
	
	static Logger logger			= Logger.getLogger(ExportDataManipulator.class);
	public static String userTimeZone=FieldNames.EMPTY_STRING;
	public static String userTimeZoneFromRestAPI = FieldNames.EMPTY_STRING;//805_REST_API_CHANGES
	/**
		private constructor to force no object creation
	*/

	private static HashMap checkBoxMap;
/*	private static HashMap locationMap		=null;
	private static HashMap stateMap			=null;
	private static HashMap countryMap		=null;
	private static SequenceMap statusMap	= null;
	private static Info ratingMap			=null;
	private static Info source2Map			=null;
	private static Info source3Map			=null;
	private static Info ownerMap			=null;
	private static Info forecastratingMap			=null;
*/
	


	static 
	{
		checkBoxMap = new HashMap();
		checkBoxMap.put("", "");
		checkBoxMap.put("0", "No");
		checkBoxMap.put("1", "Yes");
		checkBoxMap.put("2", "NA");
		checkBoxMap.put("3", "");
	}

	private ExportDataManipulator()
	{
		//locationMap = getLocationMap();
		//stateMap = getStateMap();
		//countryMap = getCountryMap();
		//statusMap = AdminMgr.newInstance().getFsLeadStatusDAO().getLeadStatus();
		//ratingMap = NewPortalUtils.getLeadRating();
        //forecastratingMap = CommonMgr.newInstance().getCommonFsDAO().getForecastRating();
		//source2Map = NewPortalUtils.getLeadSourceCategory();
		//source3Map = AdminMgr.newInstance().getFsLeadSource3DAO().getLeadSourceDetails(null);

		// Modified By Ravindra Verma on 5/2/2007 for adding Regional user as leadOwner
		//ownerMap = CommonMgr.newInstance().getCommonFsDAO().getLeadOwners();

		//ownerMap = CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwners();
	}

	public static ExportDataManipulator getInstance()
	{
		if(objectRef != null)
		{ 
			//If class contain the object reference then return that
			return objectRef;
		}else
		{
			//Create the new object by calling private constructor and call getinstance again.
			objectRef	= new ExportDataManipulator();
			return ExportDataManipulator.getInstance();
		}
	}

	public static String transformDouble(String value)
	{
		return value;
	}

	/**
	 *	transformCheckBox transforms the value of a checkbox to No, Yes or NA
	*/
	
	public static String transformCheckBox(Object id)
	{
		if(id == null) 
			return "";
		else 
			return (String)checkBoxMap.get(id.toString());
	}

	public static String transformCheckBox(String id)
	{
		if(id == null) 
			return "";
		else 
			return (String)checkBoxMap.get(id);
	}

	/*public static String transformState(String id)
	{
		/*
		try
		{
			if(stateId==null) 
				return "";
			return 
				(String)getStateMap().get(stateId);
		}catch(Exception e)
		{
			Debug.print(e);
		}
		
		return "";
		*/
		//1-aug-2008.for resource saving
       /* try
        {
        	if(id == null) 
        		return "";
        	else
        		return (String) FIMMgr.newInstance().getFIMDAO().getState(id);
        } catch (Exception e)
        {
        	logger.error(e);
        }
        return "";

	}
    */
    
	//Added By Abhishek Rastogi for P_FS_E_1000 for getting the State Name from the cache which is stored in Sequence Map.
	public static String transformState(String id){
		String state = FieldNames.EMPTY_STRING;
		if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
			return state;
		
 		try{
// 			state =	(String)com.home.builderforms.CacheDataUtil.getAllActiveStateMap().get(id);
 			state =	RegionMgr.newInstance().getRegionsDAO().getStateName(id);
 			
 		} catch (Exception e)
 		{
 			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformState(id)"+e);
 			
 		}

		return state;
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
		/*Map<String,String> countyMap = CacheMgr.getCountyCache().getCounty(countyID);
		if (countyMap != null && !countyMap.isEmpty()) {
			countyName = (String)countyMap.get(FieldNames.COUNTY_NAME);
		}*/
		return countyName;
	}

	  
	public static String transformCountry(String id)
	{
		/*
		try
		{
			if(countryId == null)
				return "";
			else
				return (String)getCountryMap().get(countryId);
		}catch (Exception e)
		{
			logger.error(e);
		}
		
		return "";
		*/
		//1-Aug-2008,for Resource saving
  		String country = FieldNames.EMPTY_STRING;
  		try{
//  			country=(String)com.home.builderforms.CacheDataUtil.getCountryMap().get(id);
  			//country = PortalUtils.getCountryNameById(id);
  		} catch (Exception e)
 		{
 			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformCountry(id)"+e);
 		}
		
		return country;
    
	}
	public static String transformRelationship(String id) {
		String value = SQLUtil.getColumnValue("COAPPLICANT_RELATIONSHIP", "COAPPLICANT_RELATIONSHIP", "COAPPLICANT_RELATIONSHIP_ID", id);
		if(!StringUtil.isValid(value)) {
			value = FieldNames.EMPTY_STRING;	
		}
		return value;
	}
	public static String transformLeadOwner(String id)
	{
		try
		{
			//return CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwners().get(id);
		} catch(Exception e)
		{
			Debug.print(e);
		}

		return "";
	}
	
	public static String getLeadOwnerId(String id) throws Exception {
		try {
			//return CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwners().get(id);
		} catch(Exception e) {
			Debug.print(e);
		}
		return "";
	}

	public static String transformYesNo(String id)
	{
		try
		{
			if(id == null) 
				return "";
			else if(id.equals("1") || id.equalsIgnoreCase("Y") || id.equalsIgnoreCase("YES"))
			{
				return "Yes";
			} else if(id.equals("2") || id.equalsIgnoreCase("N")  || id.equalsIgnoreCase("NO"))
			{
				return "No";
			} else if(id.equals("3"))
			{
				return "Not Available";
			} else
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public static String transformMinusOne(String id)
	{
		try
		{
			if(id == null) 
				return "";
			else if(id.equals("-1"))
			{
				return "";
			} else 
			{
				return id;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String transformGender(String id)
	{
		try
		{
			if(id == null) 
				return "";
			else if(id.equals("M"))
			{
				return "Male";
			} else if(id.equals("F"))
			{
				return "Female";
			} else if(id.equalsIgnoreCase("MALE"))
			{
				return "Male";
			} else if(id.equalsIgnoreCase("FEMALE"))
			{
				return "Female";
			}else
				return "";
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/*public static String transformLocation(String locationId)
	{
		String locationName ="";

		try
		{
			locationName = (String)getLocationMap().get(locationId);
		} catch(Exception e)
		{
			logger.error("Exception: "+e, e);
			Debug.print(e);
			locationName ="";
		}

		return locationName;
	}
	private  static HashMap getLocationMap() {
		ExportDataCollector	collector	= new ExportDataCollector();
		HashMap locationMap = new HashMap();
		String query="SELECT LOCATION_ID,CITY,STATE_ID,LOCATION_TITLE FROM FS_SITE_LOCATION,REGIONS WHERE REGIONS.REGION_NO=FS_SITE_LOCATION.STATE_ID ";
		try {
			locationMap=collector.getLocationMap(query);
		}catch(Exception e){
				logger.error(e);
		}finally{
			//System.out.println("location map "+locationMap.toString());
				return locationMap;
		}

	}
	*/

	/**
	 * Added By Abhishek Rastogi for P_FS_E_1001 for getting the Preferred Location
	 * City,State and Location Site name from the cache which is stored in Sequence Map.
	 */
	public static String transformLocation(String locationId)
    {
     		String locationName = FieldNames.EMPTY_STRING;
   		if(locationId == null || locationId.length()==0|| "-1".equals(locationId))
   			return locationName;
	
	try{
		
//		locationName =	(String)com.home.builderforms.CacheDataUtil.getAllActiveLocationMap().get(locationId);
		//locationName =	(String)CommonMgr.newInstance().getCommonFsDAO().getAllActiveLocationMap().get(locationId);
		
	} catch (Exception e)
	{
		logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLocation(locationId) "+e);
	}

	return locationName;
   }
	
	public static String transformLeadStatus(String statusId){
		String leadStatus="";
		try{
			leadStatus				= (String)AdminMgr.newInstance().getFsLeadStatusDAO().getLeadStatus().get(statusId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLeadStatus(statusId)>>"+e);
		}
			return leadStatus;
		
	}
	
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
	            		 //name = PortalUtils.getUserName(assignTo);
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
		 
	 }
	
	public static String transformBrandName(String brandId) {
		String brandName = "";
		//brandName = DivisionUtil.getAllBrandNames(brandId);
		return brandName;
	}
	
	public static String transformDivisionName(String divisionId) {
		//return DivisionUtil.getAllDivisionNames(divisionId);
		return "";
	}
	
	public static String transformGroupName(String groupId){                  //P_Enh_CustomReport_Group Starts
		String groupName="";
		try{
			//groupName				= (String)CommonMgr.newInstance().getCommonFsDAO().getGroupName().get(groupId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformGroupName(groupId)>>"+e);
		}
			return groupName;
	}								
	
	public static String transformFimGroupName(String groupId){                  
		String groupName = "";
		try{
			//groupName				= (String)CommonMgr.newInstance().getCommonFimDAO().getGroupName().get(groupId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformFimGroupName(groupId)>>"+e);
		}
			return groupName;
	}       																//P_Enh_CustomReport_Group Ends
	
	public static String transformLeadRating(String ratingId){
		String result	=null;
		try{
			//result=AdminMgr.newInstance().getFsLeadRatingDAO().getLeadRating().get(ratingId);
		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLeadRating(ratingId)"+e);
		}
			return result;
	}
	public static String transformContactRating(String ratingId){
		String result	=null;
		if(StringUtil.isValid(ratingId)){
			try{
				MasterDataMgr.newInstance().getMasterDataDAO();
				result = MasterDataDAO.getDataValue("9003", ratingId);
			}catch(Exception e){
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformContactRating()"+e);
			}
		}
		return result;
	}
        public static String transformForecastRating(String ratingId){
		String result	=null;
		try{
			//result=CommonMgr.newInstance().getCommonFsDAO().getForecastRating().get(ratingId);

		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java-->transformForecastRating(ratingId)"+e);
		}finally{
			return result;
		}
	}
	
	public static String transformLeadSource2(String sourceId){
		String result	=null;
		try{
			result=AdminMgr.newInstance().getFsLeadSource2DAO().getLeadSourceCategory().get(sourceId);

		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java-->transformLeadSource2(sourceId)"+e);
		}finally{
			return result;
		}
	}
	public static String transformLeadSource3(String sourceId){
		String result	=null;
		try{
			result=AdminMgr.newInstance().getFsLeadSource3DAO().getLeadSourceDetails(null).get(sourceId);

		}catch(Exception e){
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLeadSource3(sourceId)"+e);
		}finally{
			return result;
		}
	}
	
	//code added by Tarun

	public static String transformIdToValue(String id)
	{
		try
		{
			if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
    			return FieldNames.EMPTY_STRING;			
			else
				return "";
				//return (String)com.home.builderforms.CacheDataUtil.getMasterDataMap().get(id);
				//return MasterDataMgr.newInstance().getMasterDataDAO().getValueForId("8022",icpStatus);
		} catch (Exception e)
		{
			e.printStackTrace(); 
		}
		return "";
	}
 	//PP_changes starts
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
			e.printStackTrace(); 
		}
		return "";
	}
	//PP_changes starts
	/**
	 * P_FIM_B_4340  STARTS
	 * <p>This method is used specially to transform the value of trigger in FIM > Other Addressess > Address Heading tab
	 * it converts the id value from table <code>ADDRESS_STATUS</code> to the view section in trigger history</p>
	 * @param id - primary key for table <code>ADDRESS_STATUS</code>
	 * @return the Viewable value of the Address combo in other addresses. 
	 * @author shashankk & prakashj
	 */
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
	
	// P_FIM_B_4340  ends
	//	Added by Vivek Sharma
	// This method used to convert Default Integer Value (i.e -1) to Blank.
	public static String transformDefaultIntegerValToBlank(String val)
	{
		try
		{
			if (val != null && val.equals(String.valueOf(IntConstants.DEFAULT_INT))) 
				return "";
			else
				return val;
				
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}

	

	
	
	
	
	//code added by Sandeep on 09 Jan 2006
	public static String transformAreaIdToValue(String id){
            String areaName = "Default Area";            
		try{
                    String query = "SELECT AREA_NAME FROM AREAS WHERE AREA_ID ="+id ;
                    ResultSet result	= QueryUtil.getResult(query,null);
                    if(result != null && result.next()){
                            areaName = result.getString(1);
                    }//end while
            }catch(Exception e){
                    e.printStackTrace();
                    logger.error("Exception in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformAreaIdToValue(id)" + e);
            }

	return areaName;
	}
	
	public static String transformMUIdToValue(String id){
        String muName = "";            
	try{
                String query = "SELECT MU_NAME FROM MU_DETAILS WHERE MU_ID ="+id ;
                ResultSet result	= QueryUtil.getResult(query,null);
                if(result != null && result.next()){
                        muName = result.getString(1);
                }//end while
        }catch(Exception e){
                e.printStackTrace();
                logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformMUIdToValue(id)" + e);
        }

	return muName;
	}

	public static String transformAddress(String id, String fieldName){
		/*try{
			if(id == null) return "";
			else
			{
				String forType = fieldName.substring(0,fieldName.length()-1);
				String order = fieldName.substring(fieldName.length()-1);;
				return LocationMgr.newInstance().getLocationsDAO().getAddress(forType,order,id);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}*/
		return "";
	}
	
	public static String transformDocumentsData(String id, String fieldName){
		try{
			if(id == null) return "";
			else
			{
                                String order = fieldName.substring(fieldName.length()-1);;
				//return CommonMgr.newInstance().getCommonFimDAO().getDocumentsData(id,order);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	// Added by vivek on 3 Aug 09...
	public static String transformMultipleDocumentsData(String id){
		try{
			if(id == null) return "";
			else
			{
                //return CommonMgr.newInstance().getCommonFimDAO().getMultipleDocumentsData(id);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	//Added By Abhishek Rastogi for P_FIM_B_41827 for getting the picture file name with out ID being appended.
	public static String transformPictureData(String id){
		try{
			
			if(id == null) return "";
			
			else
			{
				id = id.substring(0, id.indexOf("_"))+id.substring(id.lastIndexOf("."));                
				return id;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}


	//added by esha
	public static String transformcallstatusToValue(String pDataId)
	{
		try
		{
			logger.info("\n\n\n INSIDE transformcallstatusToValue \n\n\n\n");
			// return MasterDataMgr.newInstance().getMasterDataDAO().getValue(3021,pDataId);
			//return AdminMgr.newInstance().getCallDAO().getCallStatusName(pDataId);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}
	
	

	public static HashMap getCountryMap(){
		HashMap countryMap	=new HashMap();
		try{
			String query ="SELECT COUNTRY_ID,NAME FROM COUNTRIES";
			ResultSet result				= QueryUtil.getResult(query,null);
			while(result.next()){
				countryMap.put(result.getString(1),result.getString(2));
			}//end while
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> getCountryMap():" + e);
		}

	return countryMap;
	}

	public static HashMap getStateMap(){
		HashMap stateMap	=new HashMap();
		try{
			String query ="SELECT REGION_NO,REGION_NAME FROM REGIONS";
			ResultSet result				= QueryUtil.getResult(query,null);
			while(result.next()){
				stateMap.put(result.getString(1),result.getString(2));
			}//end while
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> getStateMap():" + e);
		}

	return stateMap;
	}
	public static String transformFranchiseeType(String id){
		String name = "";
					
			try{
				if(id == null) 	return "";
					else if(id.equals("0") || id.equals("4")){
						return LanguageUtil.getString("Terminated Franchisees");
					}else if(id.equals("1")){
						return LanguageUtil.getString("Active Franchisees");
					}else if(id.equals("3")){
						return LanguageUtil.getString("Corporate Locations");
					}
				
			}catch(Exception e){
			
			}
		

			return name;
	}



/*

Added By Saurabh K on 25th Sep 2006 for directory Export

*/

public static String transformUserType(String id){
		String name = "";
					
			try{
				if(id == null) 	return "All Users";
					else if(id.equals("0") ){
						return LanguageUtil.getString("Corporate Users");
					}else if(id.equals("1")){
						return LanguageUtil.getString("Franchise Users");
					}else if(id.equals("2")){
						return LanguageUtil.getString("Regional Users");	//P_B_60897
					}else if(id.equals("3")){
						return LanguageUtil.getString("Regional Manager");
					}else if(id.equals("6")){
						return LanguageUtil.getString("Division");
					}
				
			}catch(Exception e){
			
			}
		

			return name;
	}


public static String transformMemoType(String id){
		String name = "";
					
			try{
				if(id == null) 	return "";
					else if(id.equals("C")){
						return "Credit Memo";
					}else if(id.equals("D")){
						return "Debit Memo";
					}else return id;
			}catch(Exception e){
			
			}
		
return name;
}

public static String transformMemoNo(String id){
    
    return StringUtil.getPaddedString(id, "0", 6, true);
    
}

 public static String transformAddedByUser(String id){
	 	String name = "";
		if(id == null) return "";
		else if("0".equals(id)){
			name="Web Site User";
		}else{
			
			try{
				//name =PortalUtils.getUserName(id);
			}catch(Exception e){
			
			}
		}

			return name;
	}

//Shivam ZC93_EXPORT_OPT_IN_SEARCH starts
public static String transformSubscriptionStatus(String id)
{
	 String name = "";
	 if(id == null)
		 name = "";
	 else if("0".equals(id))
		 name="Opted In";
	 else if("1".equals(id))
		 name="Opted Out";
	 else if("2".equals(id))
		 name="Opt-In Pending";
	 else if("3".equals(id))
		 name="Opt-In Request not Sent";
	 
	 return name;
}
//Shivam ZC93_EXPORT_OPT_IN_SEARCH ends

 public static String transformUser(String id){
	 	String name = "";
		if(id == null) return "";
		else{
			
			try{
				//name =PortalUtils.getUserName(id);
			}catch(Exception e){
			
			}
		}
			return name;
	}
 //Added by Manoj put on a view for an agent
 
 
 public static String transformRemarkUser(String id){
	 	String name = "";
		if(id == null) return "";
		else if("0".equals(id)){
			return "Web Site User";
		}
		else{
			
			try{
				//name =PortalUtils.getUserName(id);
			}catch(Exception e){
			
			}
		}
			return name;
	}
 
 public static String transformUser(String id,String creatoruserID){
		String name = "";
		if(id == null) return "";
		else{
			
			try{
				if(!id.trim().equals(creatoruserID.trim())){
					//name =CommonUtil.getAgentDetails(creatoruserID).get(0)+" (Agent)";
				}else{
					//name =PortalUtils.getUserName(id);
				}
				
			}catch(Exception e){
			
			}
		}

			return name;
	}
 	//P_EXPORT_DATE_ACC_TIMEZONE : starts
	public static String transformDate(String id)
	{
		return transformDate(id,null);
	}
 
	public static String transformDate(String id,String time)
	{
		// return DateTime.getDisplayDate(id);
		// P_E_DATE_FORMAT
		// P_FS_B_36928
		String returnDate;

		 //BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			/**
			 * @added by abhishek gupta
			 * @date 19 may 2009
			 * @purpose Avoid null pointer exception when date is not in correct format
			 */
	        //DateFormat format = new SimpleDateFormat(DateUtil.DB_FORMAT);
//	        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DB_FORMAT);
	        //format.setLenient(false);
		 try 
	        {
	            if(time!=null) {
	            	id = id+" "+time;
	            }
	            if(id!=null && id.length()>=19) {
	            	if(!StringUtil.isValid(userTimeZone) && StringUtil.isValid(userTimeZoneFromRestAPI)){//805_REST_API_CHANGES----START
	            		returnDate =TimeZoneUtils.performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZoneFromRestAPI, id, TimeZoneUtils.DB_DATETIME, Constants.DISPLAY_FORMAT );
	            	}else{//805_REST_API_CHANGES----END
	            		returnDate =TimeZoneUtils.performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, id, TimeZoneUtils.DB_DATETIME, Constants.DISPLAY_FORMAT );
	            	}
	            } else {
	            	returnDate = DateUtil.getDisplayDate(id);
	            }
	        } catch (Exception e) {
	        	logger.error("Exception In transformDate(String id,String time) ::::::::::: ",e);
	            returnDate = "";
	        }
			//returnDate = DateUtil.getDisplayDate(id);
		return returnDate;
	}
        //Added by kushagra for Dates having time appended to it.(yyyy-dd-mm 00:00:00)
        public static String transformDateTime(String id){
        	String returnDate = "";
        	 //BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
        	if(id!=null && id.length()>=19) {
	        	try {
	        		returnDate =TimeZoneUtils.performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, id, TimeZoneUtils.DB_DATETIME, Constants.DISPLAY_FORMAT_HMA );
	        	} catch(Exception e) {
	        		logger.error("Exception In transformDateTime(String id) ::::::::::: ",e);
	        		returnDate=DateTime.getDisplayDate(id,"time");	
	        	}
        	}
        	else {
        		returnDate=DateTime.getDisplayDate(id,"time");
        	}
        	return returnDate;
	}//P_EXPORT_DATE_ACC_TIMEZONE : ends

	public static String getItemStatus(String status){
		
		if(status == null) return "";
		else if (status.equals("1"))
		{
			return "Active";
		}else{
			 return "Deactivated";
			
		}

			
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
 //P_E_PHONE_FORMAT 
 /**
  * @author manoj
  * @param id Phone
  * @param countryID Country id
  * @return Formatted phone number according to country.
  */
 public static String  formatPhoneNoAccCountry(String id,String countryID) {
     try{
    	 String country="";
    	 if(StringUtil.isRequestParamValid(countryID)){
    		 //country=NewPortalUtils.getCountryName(countryID);
    	 }
         if(id == null || id.equals("-1") ) return "";
         else
             return PortalUtils.formatPhoneNo(id,country);                
     } catch (Exception e){
       e.printStackTrace();
     }
     return "";
 }
 //End of P_E_PHONE_FORMAT
/**
*@method getUPSServiceName get the name of service from shipping option id
*@author Vikas
*@date Tue 6,Dec 2005
*@param upsShippingOptionID
*/
		public static String getUPSServiceName(String upsShippingOptionID){
			String serviceName=null;
			
			if(upsShippingOptionID != null && "0".equals(upsShippingOptionID)){
				serviceName="Other";
			}else{
				//serviceName=CommonUtil.getUPSServiceName(upsShippingOptionID);
				serviceName = "";
			}
			if(serviceName==null)serviceName="";
			return serviceName;

		} 

		//P_B_SUPP_23391 starts 
		/**
		 * The method is used for getting supplier name while exporting supplies product information
		 * @param vendorID
		 * @return String
		 * @author Deepak Gangore
		 */
		public static String getVendorName(String vendorID){
			String vendorName="";
			try{
				if(StringUtil.isValidNew(vendorID)){
					Map<String, String> vendorNameMap = SQLUtil.getMultipleColumns(new String[]{"SUPPLIER_NAME"}, "SUPPLIERS", "SUPPLIER_NO", vendorID);
					if( vendorNameMap!=null && vendorNameMap.size()>0){
						vendorName=vendorNameMap.get("0");
					}
				}
			}catch(Exception e){
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> getVendorName(vendorID)",e);
			}
			if(!StringUtil.isValidNew(vendorID)){
				vendorName="";
			}
			return vendorName;

		}
		//custom shipping in marketing shop starts
		public static String getCustomShippingName(String shippingType){
			String shippingName="";
			try{
				if(StringUtil.isValidNew(shippingType)){
					if("F".equals(shippingType)){
						shippingName= LanguageUtil.getString("Flat");
					}else if("F".equals(shippingType)){
						shippingName= LanguageUtil.getString("Percentage");
					}
				}
			}catch(Exception e){
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> getCustomShippingName(shippingType)",e);
			}
			return shippingName;
		}
		//custom shipping in marketing shop ends
		
		//P_B_SUPP_23391 ends
		
		
		//P_B_SUPP_35187 : 25/04/2014 : Gagan Yadav 
				/**
				 * The method is used for getting supplier product color while exporting supplies product information
				 * @param productColorValue
				 * @return String
				 * @author Gagan Yadav
				 */
				public static String getProductColor(String productColorValue){
					String value = SQLUtil.getColumnValue("SUPPLIER_PRODUCT_COLOR", "DISPLAY", "VALUE", productColorValue);
					if(!StringUtil.isValid(value)) {
						value = FieldNames.EMPTY_STRING;	
					}
					return value;
				} 
				
				/**
				 * The method is used for getting supplier product size while exporting supplies product information
				 * @param productSizeValue
				 * @return String
				 * @author Gagan Yadav
				 */
				public static String getProductSize(String productSizeValue){
					String value = SQLUtil.getColumnValue("SUPPLIER_PRODUCT_SIZE", "DISPLAY", "VALUE", productSizeValue);
					if(!StringUtil.isValid(value)) {
						value = FieldNames.EMPTY_STRING;	
					}
					return value;
				} 
				
				// P_B_SUPP_35187 : 25/04/2014 : Gagan Yadav 
		
			public static String getShippingCharges(String poID){
	String shippingCharges="0";
		try{
			String query ="SELECT TOTAL_AMOUNT,PURCHASEORDER_AMOUNT FROM SUPPLIER_PURCHASEORDER WHERE PURCHASEORDER_ID="+poID;
			ResultSet result				= QueryUtil.getResult(query,null);
			if(result!=null && result.next()){
String total=result.getString("TOTAL_AMOUNT");
String subtotal=result.getString("PURCHASEORDER_AMOUNT");
float shipTotal=Float.parseFloat(total)-Float.parseFloat(subtotal);
if(shipTotal>=0)
shippingCharges=shipTotal+"";
shippingCharges=formatCurrency(shippingCharges);
			}//end while
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java-->getShippingCharges(poID):" , e);
		}

	return shippingCharges;
	}

	 public static String formatCurrency(String id){
            try{
                if(id == null || id.equals("") || id.equals("null") ) return "";
                else
                    return NumberFormatUtils.formatNumber(id);                
            } catch (Exception e){
             e.printStackTrace();
            }
            return "";
        }
         
         public static String CurrencyFormat(String id){
            try{

				logger.info("ID::::::::::::::"+id);
                if(id == null || id.equals("") || id.equals("null") ) return "";
                else
                    return NumberFormatUtils.formatCommaNumber(id);                
            } catch (Exception e){
              e.printStackTrace();
            }
            return "";
        }
         
          public static String paymentStatus(String id){
            try{
                if(id == null || id.equals("") || id.equals("null") ) return "";
                else if(id.equals("P")) return "Partial Paid";
                else if(id.equals("N")) return "UnPaid";
                else if(id.equals("C")) return "Fully Paid";
                    
            } catch (Exception e){
             e.printStackTrace();
            }
            return "";
        }

		 public static String formatOrderStatus(String colMethod){
            try{
                if(colMethod == null || colMethod.equals("") || colMethod.equals("null") ) return "";
                else if(colMethod.equals("I")) return "Invoiced";
                //else if(colMethod.equals("CC") || colMethod.equals("CH")) return "Paid";
                
                else if(colMethod.equals("CC") || colMethod.equals("CH") || colMethod.equals("CA")) return "Paid";
                else if(colMethod.equals("PCC") || colMethod.equals("PCH") || colMethod.equals("PCA")) return "Partial Paid";              
                    
            } catch (Exception e){
               e.printStackTrace();
            }
            return "";
        }

          public static String paymentCollectionMethod(String id){
            try{
                if(id == null || id.equals("") || id.equals("null") ) return "";
                else 
                return MasterDataMgr.newInstance().getMasterDataDAO().getValueForId("5001",id);
                    
            } catch (Exception e){
               e.printStackTrace();
            }
            return "";
        }


		public static String transformFranchiseeName(String id){
            
                if(id == null || id.equals("") || id.equals("null") ) return "";
                try{
					String query ="SELECT STATUS,FRANCHISEE_NAME FROM FRANCHISEE WHERE FRANCHISEE_NO='"+id+"' LIMIT 1";
					ResultSet result				= QueryUtil.getResult(query,null);
					if(result.next()){
							if( result.getString(1).equals("0") || result.getString(1).equals("4")){
								return "<font color=\"red\">*</font> "+ result.getString(2);
							}else if( result.getString(1).equals("2")){
								return "<font color=\"red\">**</font> "+result.getString(2);
							}else{
								return result.getString(2);
							}
					}//end while
				}catch(Exception e){
						
						logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformFranchiseeName(id):" + e);
				}

				return "";
           }

		   public static String transformFranchiseeNamefileType(String id){
            
                if(id == null || id.equals("") || id.equals("null") ) return "";
                try{
					String query ="SELECT STATUS,FRANCHISEE_NAME FROM FRANCHISEE WHERE FRANCHISEE_NO='"+id+"' LIMIT 1";
					ResultSet result				= QueryUtil.getResult(query,null);
					if(result.next()){
							if( result.getString(1).equals("0") || result.getString(1).equals("4")){
								return "* "+ result.getString(2);
							}else if( result.getString(1).equals("2")){
								return "** "+result.getString(2);
							}else{
								return result.getString(2);
							}
					}//end while
				}catch(Exception e){
						
						logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformFranchiseeNamefileType(id):" + e);
				}

				return "";
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


	 public static String paymentMappingMethod(String id){
            
                if(id == null || id.equals("") || id.equals("null") ) return "";
				StringBuffer resultNumber =new StringBuffer(id);
                try{
					String query ="SELECT DISTINCT(FRANCHISE_ROYALTY_ID) FROM FIN_PAYMENT_MAPPING WHERE PAYMENT_ID ='"+id+"'";
					
					//System.out.println("query ::"+query);

					ResultSet result				= QueryUtil.getResult(query,null);
					StringBuffer result1 =new StringBuffer("");
					while(result.next()){
							if(result.getString(1) != null && !result.getString(1).equals(""))
								result1.append(result.getString(1)+",");

					}//end while
 
						//System.out.println("result1 ::"+result1);
						result1 = StringUtil.removeLastComma(result1);
						//System.out.println("result1 ::"+result1);														   
						if(result1 != null && !result1.equals("") && !result1.equals(" ") && result1.length() > 2){
							resultNumber.append(" ( "+result1 +" )");
						}
					//	System.out.println("resultNumber ::"+resultNumber);
						
				}catch(Exception e){
						
						logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> paymentMappingMethod(id):" + e);
				}

				return StringUtil.getPaddedString(resultNumber.toString(), "0", 6, true);
           }

		   /** Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country*/

		   public static String transformPhoneFim(String id, String dependentFieldValue){
            
                String formatedPhone = "";
				if(id != null && !id.equals("") && StringUtil.isValid(dependentFieldValue)) {
					try{
//						formatedPhone = PortalUtils.formatPhoneNo(id, (String)com.home.builderforms.CacheDataUtil.getCountryMap().get(dependentFieldValue));						
						//formatedPhone = PortalUtils.formatPhoneNo(id, PortalUtils.getCountryNameById(dependentFieldValue));						
					}catch(Exception e){
							
							logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformPhoneFim():" + e);
					}
				}
				return formatedPhone ;	 
			}
			
			/** Added by Sunilk on 27 Oct 2006 , for exporting Area documents  */
			public static String transformAreaDocumentsData(String id, String fieldName){
				String returnValue = "";
				try{
					if(id != null){
						String order = fieldName.substring(fieldName.length()-1);
						returnValue = PortalUtils.getAreaDocumentsData(id,order);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
				return returnValue ;
			}
	
	// Added By Ravindra verma on 5/14/2007 for bug 24649

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

	public static String getCallTypeName(String callTypeId)
	{
		//return AdminMgr.newInstance().getCallDAO().getCallTypeName(callTypeId);
		return "";
	}
	//ganesh --- 19-june-2007 to replace "0" as ""(blank) 
	public static String transformZeroToBlank(String val){
		String returnValue = "";
		try{
			if(val != null && val.equalsIgnoreCase("0")){
				returnValue ="";
			}else
			{
				returnValue =val;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnValue ;
	}
	// P_E_STORE_2307
	
	public  static String transformStoreType(String id)
	{
		/*try
		{
			if(id == null) 
				return "";
			else
				return (String)LocationMgr.newInstance().getLocationsDAO().getFtName(id);
		} catch (Exception e)
		{
		e.printStackTrace();
		}*/
		return "";
	}
        
         // Added By Nikhil Verma
          	public static String transformStateFromId(String id)
         {
                /* try
 		{
 			if(id == null) 
 				return "";
 			else
 				return (String) FIMMgr.newInstance().getFIMDAO().getState(id);
 		} catch (Exception e)
 		{
 			logger.error(e);
 		}
 		return "";
 		*/
          		String state = FieldNames.EMPTY_STRING;
        		if(id == null || id.length()==0|| "-1".equals(id)|| "19".equals(id))
        			return state;
		
 		try{
     		
//     		state =	(String)com.home.builderforms.CacheDataUtil.getAllActiveStateMap().get(id);
     		state =	RegionMgr.newInstance().getRegionsDAO().getStateName(id);
			
 		} catch (Exception e)
 		{
 			e.printStackTrace();
 		}

		return state;
	}
          
                 
 	
  	// Added by Sunilk, 
 	public static String transformStateFromId(String stateId, String countryId)
    {
 		String state = FieldNames.EMPTY_STRING;
 		try{
     		
//     		state =	(String)	com.home.builderforms.CacheDataUtil.getStateMap(countryId).get(stateId);
     		state =	RegionMgr.newInstance().getRegionsDAO().getStateName(stateId);
			
 		} catch (Exception e)
 		{
 			
                    e.printStackTrace();
 		}

		return state;
    }
    
     // Added By Nikhil Verma
    
  	public static String transformCountryFromId(String id)
    {
  		String country = FieldNames.EMPTY_STRING;
  		try{
//  			country=(String)com.home.builderforms.CacheDataUtil.getCountryMap().get(id);
  			//country = PortalUtils.getCountryNameById(id);
  		} catch (Exception e)
 		{
 			e.printStackTrace();
 		}
		
		return country;
    }

 	// Added By Nikhil Verma
  	//P_EXPORT_DATE_ACC_TIMEZONE : starts
 	public static String transformTimeFormat(String time)
	{
 		return transformTimeFormat(time,null);
  	}
  	
 	public static String transformTimeFormat(String time,String date)
	{
 		 //BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		String tZConvertedDateTime = "";
		String formatedTime=FieldNames.EMPTY_STRING;
		String timeZone=userTimeZone;
		if(date!=null){
			try{
				date = DateUtil.getDisplayDate(date);
				date = date+" "+time;	
				if(!StringUtil.isValid(timeZone)){
					timeZone = Constants.DEFAULT_TIME_ZONE;
				}
		 		tZConvertedDateTime =TimeZoneUtils.performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS, timeZone, DateTime.getRequiredFormat(date, Constants.DISPLAY_FORMAT_HMS, "yyyy-MM-dd HH:mm:ss"), TimeZoneUtils.DB_DATETIME, TimeZoneUtils.DB_DATETIME );//ZC_CM_B_46268
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
					//formatedTime  =  zero + hour + timeAdd.substring(2,5) + ampm;
					formatedTime  =  zero + hour + timeAdd.substring(2,5)+timeAdd.substring(5,8) + ampm;//Bug 62056
					
				}else{
					//formatedTime  =  hour + timeAdd.substring(2,5) + ampm;
					formatedTime  =  hour + timeAdd.substring(2,5)+timeAdd.substring(5,8) + ampm;//Bug 62056
				}
		}
		return formatedTime;
	}
 	
 	
//  Transform date and time With AM-PM format
 	// Added By Nikhil Verma
 	public static String transformDateTimeFormat(String time)
	{
 		return transformDateTime(time);//P_EXPORT_DATE_ACC_TIMEZONE
 		//P_EXPORT_DATE_ACC_TIMEZONE commenting starts
 		/*String formatedDateTime=FieldNames.EMPTY_STRING;
		String formatedTime=FieldNames.EMPTY_STRING;
		if(time!=null && !time.equals(""))
 		{
			
			formatedDateTime=DateUtil.getDisplayDate(time);
			if(time.length()>11)
				formatedTime=DateTime.convertTime(time.substring(11));
			formatedDateTime=formatedDateTime+" "+formatedTime;
		}
		return formatedDateTime;*///P_EXPORT_DATE_ACC_TIMEZONE commenting ends
	}
 	
 	//  Added By Nikhil Verma
 	public static String transformEndDateTimeForTask(String duration, String date)
	{
 		Calendar c	= Calendar.getInstance();
 		String formatedDate=FieldNames.EMPTY_STRING;
 		if(duration!=null && date!=null && !duration.equals("") && !date.equals(""))
 		{
 			if(duration.equals("00:00:00"))
 			{
 				formatedDate="Not Scheduled";
 			}
 			else
 			{
		 		int durhr=0;
		 		int durMin=0;
		 	 	durhr=Integer.parseInt(duration.substring(0,2));
			    durMin=Integer.parseInt(duration.substring(3,5));
			    if(date.length()>0){
					c = DateTime.addHourMinute(date,durhr,durMin,"yyyy-MM-dd hh:mm:ss");
					}
			    String mm		= Integer.toString(c.get(Calendar.MONTH) + 1);
				String dd		= Integer.toString(c.get(Calendar.DATE));
				String yy		= Integer.toString(c.get(Calendar.YEAR));
				String hr		= Integer.toString(c.get(Calendar.HOUR));
				String min		= Integer.toString(c.get(Calendar.MINUTE));
				String ampm		= Integer.toString(c.get(Calendar.AM_PM));
				
				if(mm.length() == 1)
					mm = "0" + mm;
				if(dd.length() == 1)
					dd = "0" + dd;
				if(hr.length() == 1)
					hr = "0" + hr;
				if(min.length() == 1)
					min = "0" + min;
				if(ampm!=null && ampm.equals("0"))
					ampm = "AM";
				else if(ampm!=null && ampm.equals("1"))
					ampm = "PM";
				
				if(ampm!=null && ampm.equals("PM") && hr!=null && hr.equals("00")){
				hr = "12";
				}
				if("calendar2".equals("calendar2"))
					formatedDate = (mm) + "/" + dd + "/" + yy + " " + hr + ":" + min + " " + ampm;
				else
					formatedDate = dd + "/" + (mm) + "/" + yy + " " + hr + ":" + min + " " + ampm;
 			}
 		}

 		return formatedDate;
	}

 	/**
 	 * 
 	 * @param reasonId
 	 * @return Lead Killed Reason based on leadKilledReasonId
 	 */
 	public static String getLeadKilledReason (String reasonId)
 	{
 		String query = "SELECT KILLED_REASON FROM LEAD_KILLED_REASON WHERE KILLED_REASON_ID=?";
 		String killedReason;
 		killedReason = "";

 		try
 		{
 			ResultSet result = QueryUtil.getResult(query, new String[]{reasonId});

 			if (result != null && result.next())
 			{
 				killedReason = result.getString("KILLED_REASON");
 			}
 		} catch (Exception e)
 		{
 			e.printStackTrace();
 		}

 		return killedReason;
 	}
 	/**
 	 * @author Nikhil Verma
 	 * @param id 
 	 * @return Status Value according to the it's id.
 	 */
 	public static String getTransferStatus(String id)
	{
		try
		{
			if(id == null || id.equals("-1")) 
				return "";
			//else
				//return AdminMgr.newInstance().getFimTransferlDAO().getTransferStatusValue().getString(id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
 	/**
 	 * @author Nikhil Verma
 	 * @param time
 	 * @param duration
 	 * @return Date Time
 	 */
 	public static String transformDateTimeForTimeLessTask(String time,String duration)
	{
 		String formatedDateTime=FieldNames.EMPTY_STRING;
		String formatedTime=FieldNames.EMPTY_STRING;
		//System.out.println("duration"+duration);
		//System.out.println("time"+time);
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
 	
 	public static String transformLeadOwnerFromId(String userTemp)
	{
 		if(userTemp!=null && !userTemp.trim().equals("") && userTemp.length()>0 && !userTemp.equalsIgnoreCase("Web Site User")){
 			try {
 				//userTemp=PortalUtils.getUserName(userTemp);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
 		return userTemp;
	}
 //bug-id:39665
 	public static String replaceHref(String content)
	{
		if(content!=null &&	!content.equalsIgnoreCase("null")){
		try
		{
			int i;		//CR-BP-20121218-563
			content =content.replaceAll("<a", "<a1");
			content =content.replaceAll("<A", "<A1 ");
			if(content.indexOf("<!--")!=-1 && content.indexOf("-->")==-1)		//CR-BP-20121218-563 starts
			{ 
				i=content.indexOf("<!--");
				content=PortalUtils.replaceAll(content.substring(i,content.length()),content,"");
			}		//CR-BP-20121218-563 ends
		} catch (Exception e)
		{
			
			e.printStackTrace();
			content = "";
		}
}else{
	content="";
}
		return content;
	}
 	
	//P_FS_E_TEMPLATE_HISTORY_VERSION START
 	public static String transformMailText(String templateVersionID)
	{
 		String content="";//CommonMgr.newInstance().getCommonFsDAO().getTemplateText(templateVersionID);
 		String textOnly = StringUtil.htmlToSimpleText(content,Integer.valueOf(250));
		return replaceHref(textOnly);
	}
 	//P_FS_E_TEMPLATE_HISTORY_VERSION end 
 	
 	/**
 	 * @author Balvinder Mehla
 	 * to transform the mailText
 	 * param content:take the mailText
 	 */
 	public static String transformMailTextCM(String content)
	{
 		
 		
 		
 		String textOnly = StringUtil.htmlToSimpleText(content,Integer.valueOf(250));
		return replaceHref(textOnly);
	}
 	
 	
 	public static String transformToManipulateValue(String value)
	{
		try
		{
			if(value == null) 
				return "";
			else 
			{
				if(value.equals("Y")){				
					return "Yes";
				}else if(value.equals("N")){
					return "No";
				}else{
					return "";
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
 	
 	public static String replaceCommaToCommaSpace(String value)
	{
		try
		{
			  if(value!=null){
             	 value = value.replaceAll(" ", "");
             	 value = value.replaceAll(",", ", ");
              }else{
            	  value ="";
              }
              return value;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
 	
 	/**
 	 * This method is used to pad 11 places to given string.
 	 * @param value
 	 * @return String
 	 */
 	public static String paddedString(String value)	{
 		//BB_FIN_B_41469
		try
		{
			  if(value!=null){
             	 value = StringUtil.getPaddedString(value, "0", 11, true);
              }else{
            	  value ="";
              }
              return value;
		} catch (Exception e)
		{
		e.printStackTrace();
			return "";
		}
	}
 	
 	
 	/**
 	 * This method is used to pad 7 places to given string.
 	 * @param value
 	 * @return String
 	 */
 	//P_SUPP_B_14520 starts
 	public static String paddedString1(String value)	{
 		//BB_FIN_B_41469
		try
		{
			  if(value!=null){
             	 value = StringUtil.getPaddedString(value, "0", 7, true);
              }else{
            	  value ="";
              }
              return value;
		} catch (Exception e)
		{
		e.printStackTrace();
			return "";
		}
	}
 	
 	//P_SUPP_B_14520 end
 	
 	
 	/**
 	 * @author Nikhil Verma
 	 * @param id
 	 * @param fieldName
 	 */
 	public static String transformMuDocumentsData(String id, String fieldName){
		String returnValue = "";
		try{
			if(id != null){
				String order = fieldName.substring(fieldName.length()-1);
				//returnValue = CommonMgr.newInstance().getCommonFimDAO().getMuDocumentsData(id,order);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnValue ;
	}

    
    public static String transformNetWorthScores(String statusId){
		String leadStatus="";
		try{
            leadStatus = CommonUtil.getComboInfo("NET_WORTH_SCORES","VALUES").getString(statusId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return leadStatus;
		}
	}


    public static String transformCashAvailableInvestment(String statusId) {
        String leadStatus = "";
        try {
            leadStatus = CommonUtil.getComboInfo("LIQUIDITY_SCORES","VALUES").getString(statusId);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            return leadStatus;
        }
    }


    public static String investTimeFrame(String statusId) {
        String leadStatus = "";
        try {
            leadStatus = CommonUtil.getComboInfo("INVESTMENT_TIME_FRAME_SCORES","VALUES").getString(statusId);
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            return leadStatus;
        }
    }

    public static String transformEmploymentBackground(String key){
        Info info = CommonUtil.getBackGroundInfo();
        String str = info.getString(key);
        return str;
    }
    
     //added by Anuradha tiwari
        /*
         * This method is used to change the months in words.
         */ 
        public static String formatMonthsName(String id){
            try{
                if(id == null || id.equals("-1") ) return "";
                else
                    return PortalUtils.getMonthName(Integer.parseInt(id));                
            } catch (Exception e){
                logger.error(e);
            }
            return "";
        }


    	public static String transformLeadMarketingCode(String marketingId){
    		String result	=null;
    		try{
    			//result=AdminMgr.newInstance().getFsLeadMarketingCodeDAO().getLeadMarketingCode().get(marketingId);

    		}catch(Exception e){
    			logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformLeadRating(ratingId)"+e);
    		}finally{
    			return result;
    		}
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
    	
        /*
         * This method is used to export totalTransaction field.
         */ 
        public static String transformInvoiceData(String contactID) {
            if(StringUtil.isValid(contactID)){
            	String totalTransaction = "";
                try {
                	
                	//totalTransaction= CommonMgr.newInstance().getCommonCmDAO().getInvoiceData(contactID);
                } catch (Exception e) {
                e.printStackTrace();
                } finally {
                    return totalTransaction;
                }
            }	return "";
        	
        }
        
     // P_FIM_B_1417 starts
        
        /*
         * This method is used to get Address Heading from id.
         */ 
    	
    	public  static String transformAddressHeading(String id)
    	{
    		try
    		{
    			if(id == null) 
    				return "";
    			//else
    				//return (String)PortalUtils.getAddressHeading(id);
    		} catch (Exception e)
    		{
    		e.printStackTrace();
    		}
    		return "";
    	}
    	
    	// P_FIM_B_1417 ends
    	//P_B_47920
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
    	//P_B_47920
    	//P_FS_B_5243 starts
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
    	//P_FS_B_5243 ends
    	
    	public static String getTotalAssetsFromQualification(String assets,String leadId)
    	{
    		String totalAssests="";
    		if(StringUtil.isValid(leadId))
    			totalAssests=NewPortalUtils.getColumnFromTable("FS_LEAD_QUALIFICATION_DETAIL", "TOTAL_ASSETS", "LEAD_ID", leadId);
    		return totalAssests;
    	}
    	
    	public static String getTotalLiabilitesFromQualification(String liabilites,String leadId)
    	{
    		String totalLiabilites="";
    		if(StringUtil.isValid(leadId))
    			totalLiabilites=NewPortalUtils.getColumnFromTable("FS_LEAD_QUALIFICATION_DETAIL", "TOTAL_LIABILITIES", "LEAD_ID", leadId);
    		return totalLiabilites;
    	}
    	
    	public static String getNetWorthFromQualification(String assets,String leadId)
    	{
    		String netWorth="";
    		if(StringUtil.isValid(leadId))
    			netWorth=NewPortalUtils.getColumnFromTable("FS_LEAD_QUALIFICATION_DETAIL", "TOTAL_NETWORTH", "LEAD_ID", leadId);
    		return netWorth;
    	}
    	/**
    	 * This will transform Non Builder Field's ids.
    	 * If invlid data found in method call then empty value as a result.
    	 * @param fieldName
    	 * @param tableAnchor
    	 * @param id
    	 * @return
    	 */
    	public static String transformOptionValueForId(String fieldName, String tableAnchor, String id) {
    		if("multiUnitOwnerExport".equals(tableAnchor) ){
    			tableAnchor="fimOwners";
    		}else if("entityDisplayDetail".equals(tableAnchor)){
    			tableAnchor="fimEntityDetail";
    		}
    		try {
	    		String query = "SELECT OPTION_ID,OPTION_VALUE FROM FIM_BUILDER_MASTER_DATA  WHERE FIELD_NAME = '"+fieldName+"' AND TABLE_ANCHOR = '"+tableAnchor+"' AND OPTION_ID IN ("+id+") ";
	            ResultSet result = QueryUtil.getResult(query,null);
	        	String tmpVal = "";
	            while(result.next()) {
	            	if(tmpVal.equals("")) {
	            		tmpVal = result.getString("OPTION_VALUE");
	            	} else {
	            		tmpVal = tmpVal + ", "+result.getString("OPTION_VALUE");
	            	}
	            } 
	            return tmpVal;
    		} catch(Exception e) {
    			return "";
    		}
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
    				name = "";
    			}
    		}

    			return name;
    	}
    	//GROUP_FILTER_ENH ends
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
    	public static String transformJobStatus(String jobStatusId){
    		String jobTypeName = null;
    		if(jobStatusId == null) 
    			return "";
    			try{
    				jobTypeName = NewPortalUtils.getColumnFromTable("JOB_STATUS_COLOR_CODE_MAPPING", "STATUS_NAME", "STATUS_ID", jobStatusId);
    			}catch(Exception e){
    				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformJobStatus(jobStatusId)"+e);
    			}
    			return jobTypeName;
    	}
    	public static String transformJobOwner(String userID){
  		  String ownerName="";
  		  String query="SELECT CONCAT(USER_FIRST_NAME,' ',USER_LAST_NAME) AS JOB_OWNER FROM USERS U WHERE U.USER_NO ="+userID;
  		  ResultSet rs= QueryUtil.getResult(query, null);
  		  if(rs.next())
  			  ownerName=rs.getString("JOB_OWNER");
  		  QueryUtil.releaseResultSet(rs);
  		  return ownerName;
  	  }
   	  //P_SCH_ENH_008 Ends
    	//BOEFLY_INTEGRATION : START
		public static String transformBinaryToBoolean(String val) {
			String retVal = "";
			if("1".equals(val)){
				retVal = "TRUE";
			}
			return retVal;
		}
		public static String transformDateWithTime(String value) throws Exception
		{
			 //BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			if(StringUtil.isValid(value)){
				String[] tempArr =  value.split(" ");
				value = DateUtil.getDisplayDate(tempArr[0]);
				if(tempArr.length > 1){
					value = value + " " + convertTime(tempArr[1]);
					value = TimeZoneUtils.performUTCConversion(
							Constants.DB_TIMEZONE_TIMEZONEUTILS, Constants.USER_TIME_ZONE,
							value, Constants.DISPLAY_FORMAT_HMA,
                            Constants.DISPLAY_FORMAT_HMA);
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
		//BOEFLY_INTEGRATION : END
		//LISTEN360_INTEGRATION : START
		/*public static String transformInvoiceNumberfileType(String value)
		{ BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			String returnValue = FieldNames.EMPTY_STRING;
		    try
		    {
		    	if(StringUtil.isValid(value)){
		    		int invNumber = Integer.parseInt(value);
		    		invNumber+=_baseConstants.INVOICE_START_NO;
		    		returnValue=_baseConstants.INVOICE_PREFIX+invNumber;
		    	}
		    }
		    catch(Exception e) {
	        	e.printStackTrace();
	        	returnValue = FieldNames.EMPTY_STRING;
	    	}
		    return returnValue;
		}*/
		
		public static String getCountryISO2NameFromId(String id)
	    {
	  		String country = FieldNames.EMPTY_STRING;
	  		try{
	  			country	=	SQLUtil.getColumnValue("ISO_COUNTRY_DETAILS", "ISO2_ABBR", "COUNTRY_ID", id);
	  		} catch (Exception e)
	 		{
	 			e.printStackTrace();
	 		}
			
			return country;
	    }
		
		//LISTEN360_INTEGRATION : END
		//PROVEN_MATCH_INTEGRATION : START
		public static String covertDoubleToString(String d1)
		{
		  try
		  {
			double d=Double.parseDouble(d1);
			   if(d == (int) d)
		           return String.format("%d",(int)d);
		       else
		           return String.format("%s",d);
		  }
		  catch(Exception e) {
	        	e.printStackTrace();

	    		return d1;
	    	}
		}
		//PROVEN_MATCH_INTEGRATION : END
		//Bug 50215 : START
		public static String formatCommaNumber(String id)
		{
			 try{

					logger.info("ID::::::::::::::"+id);
	                if(id == null || id.equals("") || id.equals("null") ) return "";
	                else
	                    return NumberFormatUtils.formatCommaNumber(id);                
	            } catch (Exception e){
	              e.printStackTrace();
	            }
	            return "";
		}
		//Bug 50215 : END
		
		// ZCUB-20150609-155 starts
		public static String transformProductName(String productID){
		 	String name = "";
			if(productID == null) return "";
			else{
				
				try{
					name =NewPortalUtils.getColumnFromTable("CM_TMS_PRODUCTS", "PRODUCT_NAME", "PRODUCT_ID", productID);
				}catch(Exception e){
				
				}
			}
				return name;
		}
		
		public static String transformAccountName(String accountId){
			return SQLUtil.getColumnValue("CM_COMPANY_INFO","COMPANY_NAME","COMPANY_ID",accountId);	 
		}
		
		public static String transFormAccountType(String accountType){
			if("N".equals(accountType)){
				return "B2C";
			}else if("Y".equals(accountType)){	//P_B_REST_65184
				return "B2B";
			}else{
				return "";
			}
		}
		//ENH_PW_FBC Starts
		public  static String transformfbc(String id)
		{
			try
			{
				if(id == null) 
					return "";
				//else
					//return (String)PortalUtils.getUserName(Integer.parseInt(id));
			} catch (Exception e)
			{
			e.printStackTrace();
			}
			return "";
		}
		//ENH_PW_FBC Ends
		
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
		public static String transFormCmMasterData(String id){

			try {
				if(id == null || id.length()==0|| "-1".equals(id))
	    			return FieldNames.EMPTY_STRING;			
				//else
					//return  CacheMgr.getCmMasterDataCache().getCmMasterDataValue(id);
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			return "";
		
		}
		
		
		// ZCUB-20150609-155 ends
		
		// Configure_Opportunity_Stages start
		 public static String transFormCmStages(String id) {
			 
		     String val=null;
		 
			 if(id == null || id.length()==0|| "-1".equals(id)){
			 }else{
				 val=SQLUtil.getColumnValue("CM_OPPORTUNITY_STAGES","CM_STAGE_NAME","CM_STAGE_ID",id,null);
				 }
			 return val;
			 }
		// Configure_Opportunity_Stages ends
		public static String getRenewalOption(String renewalOptions,String consecutive){
			 String renewalOption="";
			 if(StringUtil.isValidNew(consecutive) && StringUtil.isValidNew(renewalOptions)){
			  renewalOption = "<b>"+renewalOptions+"</b>&nbsp;Consecutive&nbsp;<b>"+consecutive+"</b>&nbsp;Years term";
			 }
			 
		 return renewalOption;
		 }
		
																													//Broker Custom report-FC-177 starts
		public static String tranformAgency(String id) {
			String Rvalue="";
			 try {
				if(StringUtil.isValidNew(id))					
					 Rvalue=SQLUtil.getColumnValue("BROKER_AGENCY_DETAILS","AGENCY_NAME","BROKER_AGENCY_ID",id,null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> tranformAgency"+e);
			}

			 return  LanguageUtil.getString(Rvalue); 
			
		}
		
		public static String transformBrokerPriority(String brokerPriority) {

			try {
				if(!StringUtil.isValidNew(brokerPriority)){
					return "";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformBrokerPriority"+e);
			}
			return brokerPriority;
		}
		public static String transformBrokerModeOfContact(String primaryPhoneToCall) {
			String tempPrimaryPhoneToCall="";
			try {
				if(StringUtil.isValidNew(primaryPhoneToCall)){
				StringBuilder str=new StringBuilder(primaryPhoneToCall);
				if(primaryPhoneToCall.indexOf('_') !=0){
				str.setCharAt(primaryPhoneToCall.indexOf('_'), ' ');
				}
				tempPrimaryPhoneToCall=String.valueOf(str) ;
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformBrokerModeOfContact"+e);
			}
			return LanguageUtil.getString(tempPrimaryPhoneToCall);
			
		}
		
		public static String transformBrokerContact(String id) {
			String Rvalue="";
			
			try {
				if(StringUtil.isValidNew(id))					
					 Rvalue=SQLUtil.getColumnValue("FS_CONTACT_TYPE","CONTACT_TYPE_NAME","CONTACT_TYPE_ID",id,null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("\nException in com.appnetix/app/portal/export/ExportDataManipulator.java--> transformBrokerContact"+e);
			}

			 return  LanguageUtil.getString(Rvalue); 
		}
																													//Broker Custom report-FC-177 ends

		public static String transformCategoryName(String category) {
			String categoryName = "";
			if(StringUtil.isValid(category)) {
				categoryName = SQLUtil.getColumnValue("CM_TMS_CATEGORY", "CATEGORY_NAME", "CATEGORY_ID", category);
			}
			return categoryName;
		}
		
		public static String transformProductStatus(String status) {
			String statusValue = "";
			if("A".equals(status)) {
				statusValue = "Active";
			} else if("I".equals(status)) {
				statusValue = "Inactive";
			}
			return LanguageUtil.getString(statusValue);
		}
}
