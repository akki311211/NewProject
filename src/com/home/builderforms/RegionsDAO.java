
/**
 ----------------------------------------------------------------------------------------------
Version No.		Date		By		Against									Function Changed	Comments
-----------------------------------------------------------------------------------------------
D_B-22013		21/12/2006	santanu		getRegionFranchisesBoolean() Added condition for Regional users
D_A_B_22396		07/01/2007	Rakesh		Change first character of 
										owner's first and last 
										name to upper case.
P_E_ADMIN_1000  30/9/2008 Nikhil Verma Regions enhencement.	  getAllDomesticRegions(),       International Region also add with state.
P_FIM_B_39491			   08/10/2008   Nikhil Verma Bug	Phone number is not formated for USA.
P_ADMIN_B_39684			   15/10/2008   Nikhil Verma Bug	Html Code is displaying.
P_ADMIN_E_11062010                 11/06/2010   Vikram Raj entries for the field Manager
P_B_FIM_63341       11 March 2011    Neeti Solanki               for changing the existing zip code functionality in regional
P_ENH_MYSQL55             15 SEP 2011           PRAKASH JODHA               changes done ACCORDING TO MYSQL5.5
* BBEH_FOR_GETRESULT_METHOD  23/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
-----------------------------------------------------------------------------------------------
*/


package com.home.builderforms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import com.home.builderforms.BaseDAO;
import com.home.builderforms.ConnectionException;
import com.home.builderforms.Constants;
import com.home.builderforms.Debug;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.FieldNames;
//import com.home.builderforms.cache.UnRegisteredState;
import com.home.builderforms.Info;
import com.home.builderforms.CommonMgr;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

import org.apache.log4j.Logger;

public class RegionsDAO extends BaseDAO {
static Logger logger = Logger.getLogger(RegionsDAO.class);
	private void setTotalRecords(String total){
	}

	public String getTotalRecords(){
		return "";
	}

	private void setTotalRecordsInt(String total){
	}

	public String getTotalRecordsInt(){
		return "";
	}
    /**
     * Constructor for the RegionsDAO object
     */
    public RegionsDAO() {
        this.tableAnchor = "regions";

    }

/*
  Function to get All States Names or States in a specific country
*/
    public Info getStates(String sCountryId) {
        Info statesAll = null;
        ResultSet result = null;
        try {

            String sQuery = null;
           
            sQuery = "SELECT REGION_NO, REGION_NAME FROM REGIONS ";
            if (sCountryId != null && sCountryId.length() != 0 && !sCountryId.equals("null") && !sCountryId.equals("")) {
                sQuery += " WHERE COUNTRY_ID=" + sCountryId;
            }
            sQuery += " ORDER BY REGION_NAME";
            result = QueryUtil.getResult(sQuery, new Object[]{});

            if (result != null) {
                statesAll = new Info();
                while (result.next()) {
                    //statesAll.set(result.getString("REGION_NO"), LanguageUtil.getString(result.getString("REGION_NAME")));
                	statesAll.set(result.getString("REGION_NO"), result.getString("REGION_NAME"));
                }//end while
            }//end if
        } catch (Exception e) {
            logger.error("Exception in updateUnregisteredStates :" +e);
			return null;
        }finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return statesAll;
    }//updateUnregisteredStates ends

// function to update the unregistered states
    public boolean updateUnregisteredStates(String[] stateIds) {
        String sQuery1 = null;
        String sQuery2 = null;
        ResultSet result = null;
        int length;
	
		try {
            sQuery1 = "UPDATE REGIONS SET REGISTERED = '1'";
            sQuery2 = "UPDATE REGIONS SET REGISTERED = '0' WHERE REGION_NO = ?";

            if (stateIds.length != 0) {
				length = QueryUtil.update(sQuery1, new String[]{});
            }

             if (stateIds.length == 0) {
				
                length = QueryUtil.update(sQuery1, new String[]{});
            }

            /* Update the status of unregistered states */
            if (stateIds != null) {
                for (int count = 0; count < stateIds.length; count++) {
                    length = QueryUtil.update(sQuery2, new String[]{stateIds[count]});
                }
            }
            //For nullifing the unregistered states stored in the cache
//            UnRegisteredState.getInstance().clearCache();
        } catch (Exception e) {
            logger.error("Exception in updateUnregisteredStates :" +e);
		    return false;
        }finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return true;
    }//updateUnregisteredStates ends

    /**
     * P_Enh_Unregistered_State
     * @param state
     * @return
     */
    public boolean updateUnregisteredState(String state) 
    {
    	String sQuery1 = null;
        String sQuery2 = null;
        ResultSet result = null;
		try {
            sQuery1 = "UPDATE REGIONS SET REGISTERED = '1'";
            sQuery2 = "UPDATE REGIONS SET REGISTERED = '0' WHERE REGION_NO IN ("+state+")";
            QueryUtil.update(sQuery1, new String[]{});
            if(StringUtil.isValid(state)) {
            	QueryUtil.update(sQuery2, new String[]{});
            }
            } catch (Exception e) {
                logger.error("Exception in updateUnregisteredStates :" +e);
    		    return false;
            }finally
    		{
    			QueryUtil.releaseResultSet(result);
    		}
    	return true;
    }
    
// function to update the unregistered states
	public boolean updateUnregisteredState() {
        String sQuery1 = null;
        int length;
	        try {
            sQuery1 = "UPDATE REGIONS SET REGISTERED = '1'";
               length = QueryUtil.update(sQuery1, new String[]{});
                } catch (Exception e) {
			logger.error("Exception in updateUnregisteredState :" +e);
		    return false;
        }
        return true;
    }//updateUnregisteredState ends
	
	public SequenceMap getStatesSearch(String sCountryId) {
		SequenceMap statesAll = null;
		   ResultSet result = null;
        try {
            String sQuery = null;
         
            sQuery = "SELECT REGION_NO, REGION_NAME FROM REGIONS ";
            if (sCountryId != null && sCountryId.length() != 0 && !sCountryId.equals("null") && !sCountryId.equals("")) {
                sQuery += " WHERE COUNTRY_ID=" + sCountryId;
            }
            sQuery += " ORDER BY REGION_NAME";
            result = QueryUtil.getResult(sQuery, new Object[]{});

            if (result != null) {
                statesAll = new SequenceMap();
                while (result.next()) {
                    statesAll.put(result.getString("REGION_NO"), result.getString("REGION_NAME"));
                }//end while
            }//end if
        } catch (Exception e) {
        	logger.error("Exception in getStatesSearch :" +e);
			return null;
        }finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return statesAll;
    }//getStatesSearch ends

    public SequenceMap getAllStates(String countryID,String rId) {

		SequenceMap sMap = new SequenceMap();
		String allStates = "";
		String state = "";
		ResultSet rs1=null;
		try{
			String sQuery	= "SELECT STATE_ID FROM STATE_AREAS SA,AREAS A WHERE SA.AREA_ID=A.AREA_ID AND A.IS_DELETED='N'";
			 rs1 = QueryUtil.getResult(sQuery, new Object[]{});
			if(rs1.next()){
				allStates = rs1.getString("STATE_ID");
			}
			while(rs1.next()){
				state = rs1.getString("STATE_ID");
				allStates = allStates + "," + state;
			}

			StringBuffer query = new StringBuffer("SELECT DISTINCT REGION_NO,REGION_NAME FROM REGIONS R");
			if(!rId.equals("-1")){
				query.append(", STATE_AREAS S");
			}
			query.append(" WHERE R.COUNTRY_ID = ");
			query.append(countryID);
			if(rId.equals("-1") && (allStates!=null && allStates.length()!=0)){
				query.append(" AND R.REGION_NO NOT IN (");
				query.append(allStates);
				query.append(")");
			}
			else if(allStates!=null && allStates.length()!=0){
				query.append(" AND (R.REGION_NO NOT IN (");
				query.append(allStates);
				query.append(") OR (R.REGION_NO = S.STATE_ID AND S.AREA_ID = ");
				query.append(rId);
				query.append("))");
			}
			query.append(" ORDER BY REGION_NAME");
			
			ResultSet rs = QueryUtil.getResult(query.toString(), null);
			Info info	= null;
			while(rs.next()){
				info	= new Info();
				info.set(FieldNames.REGION_NO,rs.getString("REGION_NO"));
				info.set(FieldNames.REGION_NAME,rs.getString("REGION_NAME"));
				sMap.put(rs.getString("REGION_NO"),info);
			}
			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getAllStates :" +e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs1);
		}

	}//getAllStates ends

    public String insertRegionDetails(String areaName, String areaType,String fieldManager)
    {
         return insertRegionDetails(areaName,areaType,fieldManager,null);
    }
    public String insertRegionDetails(String areaName, String areaType,String fieldManager,String manualAreaId) {
                
		String nameBuffer1 =StringUtil.formatName(areaName,null);
		//areaName=nameBuffer1.substring(nameBuffer1.indexOf(",")+1,nameBuffer1.length());
	
		String areaId;
        if(StringUtil.isValid(manualAreaId))
        {
            areaId		= manualAreaId;
        }
        else
        {
            areaId		= IDGenerator.getNextKey();
        }
        
		try{
			String sQueryAreas	= "INSERT INTO AREAS(AREA_ID,AREA_NAME,AREA_TYPE,IS_DELETED,FIELD_MANAGER) VALUES(?,?,?,'N',?)";
                        //areaName 		= StringUtil.replaceSubString(areaName,"\'","&#34;");
			//areaName 		= StringUtil.replaceSubString(areaName,"\"","&#39;");
			// Commented By Nikhil Verma P_ADMIN_B_39684
                 //       areaName 		= StringUtil.replaceSubString(areaName,"(","&#40;");
                   //     areaName 		= StringUtil.replaceSubString(areaName,")","&#41;");
			String[] paramArea	= {areaId,areaName,areaType,fieldManager};
			int result		= QueryUtil.update(sQueryAreas,paramArea);

			String sQueryOwners	= "INSERT INTO AREA_OWNERS(AREA_ID) VALUES(?)";
			String[] paramOwners	= {areaId};
			result			= QueryUtil.update(sQueryOwners,paramOwners);
			return areaId;
		}
		catch(Exception e){
			logger.error("Exception in insertRegionDetails : " +e);
			return "-1";
		}
	}//insertRegionDetails ends

    public boolean addCountyToRegion(String areaId, String[] countyId) {
		try{
             String[] queryArray = {"INSERT INTO COUNTY_AREAS(AREA_ID, COUNTRY_ID, STATE_ID, COUNTY_ID) SELECT ?, COUNTRY_ID, REGION_NO, COUNTY_NO FROM COUNTIES WHERE COUNTY_NO = ?"};
             List<String[]> paramList = null;
             String[] paramArray = null;
             Map<String,List<String[]>> batchMap = BaseUtils.getNewHashMapWithKeyValueType();;
             
             for(int i=0;i<countyId.length;i++){
            	 paramArray = new String[]{areaId,countyId[i]};
            	 if(batchMap.containsKey(FieldNames.ZERO)) {
            		 (batchMap.get(FieldNames.ZERO)).add(paramArray);
            	 } else {
            		 paramList = new ArrayList<String[]>();
            		 paramList.add(paramArray);
            		 batchMap.put(FieldNames.ZERO, paramList);
            	 }
             }
             QueryUtil.preparedStatementsBatchUpdate(queryArray, batchMap);
			return true;
		}
		catch(Exception e){
			logger.error("Exception in addCountyToRegion :" ,e);
			return false;
		}

	}
    
	public boolean addStateToRegion(String areaId, String[] stateid) {
		try{

			String sQueryState	= "INSERT INTO STATE_AREAS(AREA_ID, STATE_ID,COUNTRY_ID) SELECT ?,REGION_NO,COUNTRY_ID FROM REGIONS WHERE REGION_NO=?";
			int result		= -1;

			String[] paramState	= {areaId,""};
			for(int i=0;i<stateid.length;i++){
				paramState[1]	= stateid[i];
				 result		= QueryUtil.update(sQueryState,paramState);
			}
			return true;
		}
		catch(Exception e){
			logger.error("Exception in addStateToRegion :" +e);
			return false;
		}

	} //addStateToRegion ends

	public boolean addZipcodeToRegion(String areaId, String[] country, String[] zipCode) {

		try{
			String sQuery	= "INSERT INTO ZIPCODE_AREAS(AREA_ID, ZIPCODE) VALUES(?,?)";
			int result		= -1;
			for(int i=0;i<zipCode.length;i++){
				// Adding condition to prevent blank zipcodes By Nikhil Verma
				if(zipCode[i]==null || "".equals(zipCode[i].trim()))
					continue;
				String[] paramState	= {areaId,zipCode[i].trim()};
				 result		= QueryUtil.update(sQuery,paramState);
			}
			String sQueryCountry	= "INSERT INTO ZIPCODE_AREA_COUNTRY(COUNTRY_ID,AREA_ID) VALUES(?,?)";
			for(int j=0;j<country.length;j++){
				String[] param	= {country[j], areaId};
				 result		= QueryUtil.update(sQueryCountry,param);
			}
			return true;
		}
		catch(Exception e){
			logger.error("Exception in addZipcodeToRegion :" +e);
			return false;
		}
	}//addZipcodeToRegion ends
	//P_B_FIM_63341 added by neeti starts
	/*
	 * for updating the AREA_TERRITORY on the basis of areaId
	 * author: Neeti Solanki
	 */
	public boolean updateAreaTerritory(String areaId, String zipCode) {
		String zipCodeValue   = "";
    	String newZipCode = "";
		try{
			StringTokenizer st = new StringTokenizer(zipCode,",");
    		while(st.hasMoreTokens()){
    			zipCodeValue   =  (String)st.nextToken().trim();
    			if(zipCodeValue.indexOf("'", 0)!= -1){
    				zipCodeValue  =   zipCodeValue.substring(1);
    			}
    			if(zipCodeValue.indexOf("'", (zipCodeValue.length()-1))!= -1){
    				zipCodeValue  =   zipCodeValue.substring(0, (zipCodeValue.length()-1));
    			}
    			if(newZipCode.equals(""))
    				newZipCode =  zipCodeValue;
    			else 
    				newZipCode =  newZipCode+","+zipCodeValue;
    		}
			String updateQuery = "UPDATE AREA_TERRITORY SET FIM_TT_ZIP=? WHERE AREA_ID=?";
			QueryUtil.update(updateQuery,new String[]{newZipCode,areaId});
			return true;
		}
		catch(Exception e){
			logger.error("Exception in addZipcodeToRegion :" +e);
			return false;
		}
	}
	//P_B_FIM_63341 added by neeti ends
	
	
	public boolean addCountryToRegion(String areaId, String[] countryId) {
		try{

			String sQuery	= "INSERT INTO INTERNATIONAL_AREAS(AREA_ID, COUNTRY_ID) VALUES(?,?)";
			int result	= -1;
			for(int i=0;i<countryId.length;i++){
				String[] params	= {areaId,countryId[i]};
				 result		= QueryUtil.update(sQuery,params);
			}
			//37200
			UpdateLeadForAreaId1(areaId);
			return true;
		}
		
		catch(Exception e){
			logger.error("Exception in addCountryToRegion :" +e);
			return false;
		}

	}//addCountryToRegion ends

	public void UpdateLeadForAreaId1(String areaId) {
        boolean leadUpdated = false;
        ArrayList leadIdList = new ArrayList();
        String query = null;
        String countryCode = null;
        String newAreaID = null;
        ResultSet rs = null;
        int length = 0;
        try {
            query = "SELECT LEAD_ID,FIRST_NAME FROM FS_LEAD_DETAILS WHERE AREA_ID='" + areaId + "'";
            rs = QueryUtil.getResult(query, null);
            while (rs.next()) {
                leadIdList.add(rs.getString("LEAD_ID"));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        String leadId = null;
        if (leadIdList != null && leadIdList.size() > 0) {
            for (int i = 0; i < leadIdList.size(); i++) {
                leadId = (String) leadIdList.get(i);
                if (leadId != null && leadId.length() > 0 && !leadId.trim().equals("")) {
                    try {
                        query = "SELECT AREA_ID,COUNTRY FROM FS_LEAD_DETAILS WHERE LEAD_ID='" + leadId + "'";
                        rs = QueryUtil.getResult(query, null);
                        if (rs.next()) {
                            countryCode = rs.getString("COUNTRY");
                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    newAreaID = RegionMgr.newInstance().getCountriesDAO().getAreaIdForcontry(countryCode);
                    if (newAreaID != null && newAreaID.trim().length() > 0 && !newAreaID.trim().equals("")) {
                        try {
                            query = "UPDATE FS_LEAD_DETAILS SET AREA_ID = ? WHERE LEAD_ID=? ";
                            length = QueryUtil.update(query, new String[] { newAreaID, leadId });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        String countryList = null;
        if (areaId != null) {
            countryList = RegionMgr.newInstance().getCountriesDAO().getCountryIdByAreaID(areaId);
        }
        if (countryList == null || countryList.trim().equals("")) {
            countryList = "'0'";
        }
        updateAreaIdByCountry(countryList);
    }

	public void updateAreaIdByCountry(String country) {
        String countryCode = null;
        String stateIdCode = null;
        String newAreaID = null;
        ResultSet rs = null;
        String query = null;
        int length = 0;
        ArrayList leadIdList = new ArrayList();
        query = "SELECT LEAD_ID,FIRST_NAME FROM FS_LEAD_DETAILS WHERE (COUNTRY IN (" + country + ") )";
        rs = QueryUtil.getResult(query, null);
        while (rs.next()) {
            leadIdList.add(rs.getString("LEAD_ID"));
        }
        String leadId = null;
        if (leadIdList != null && leadIdList.size() > 0) {
            for (int i = 0; i < leadIdList.size(); i++) {
                leadId = (String) leadIdList.get(i);
                if (leadId != null && leadId.length() > 0 && !leadId.trim().equals("")) {
                    countryCode = SQLUtil.getQueryResult("SELECT COUNTRY FROM FS_LEAD_DETAILS WHERE LEAD_ID='" + leadId + "'", "COUNTRY");
                    newAreaID = RegionMgr.newInstance().getCountriesDAO().getAreaIdForcontry(countryCode);
                    if (newAreaID != null && newAreaID.trim().length() > 0 && !newAreaID.trim().equals("")) {
                        try {
                            query = "UPDATE FS_LEAD_DETAILS SET AREA_ID = ? WHERE LEAD_ID=? ";
                            length = QueryUtil.update(query, new String[] { newAreaID, leadId });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
	/*
    * This function set is_deleted='Y' for the region to be deleted, specified by the regionId
	* Called from DeleteRegionAction
	*/
	public void deleteRegion(String areaId, String areaType) {

		StringBuffer deleteQuery = null;
		int result = 0;
		String params[]	= {areaId};
		try
		{
			deleteQuery = new StringBuffer("UPDATE AREAS SET IS_DELETED='Y' WHERE AREA_ID = ?");
			result = QueryUtil.update(deleteQuery.toString(),params);
			/*if(areaType.equals("1")) {
				deleteQuery = new StringBuffer("DELETE FROM STATE_AREAS WHERE AREA_ID = ?");
				result = QueryUtil.update(deleteQuery.toString(),params);
			} else if(areaType.equals("2")) {
				deleteQuery = new StringBuffer("DELETE FROM ZIPCODE_AREAS WHERE AREA_ID = ?");
				result = QueryUtil.update(deleteQuery.toString(),params);	
				deleteQuery = new StringBuffer("DELETE FROM ZIPCODE_AREA_COUNTRY WHERE AREA_ID = ?");
				result = QueryUtil.update(deleteQuery.toString(),params);
			} else if(areaType.equals("3")) {
				deleteQuery = new StringBuffer("DELETE FROM INTERNATIONAL_AREAS WHERE AREA_ID = ?");
				result = QueryUtil.update(deleteQuery.toString(),params);
			}*/
		}
		catch (Exception e) {
			logger.error("Exception in deleteRegion :"+e);
		}
	}// deleteRegion ends

//This method is for getting the list of franchisee for a region.
	public SequenceMap getRegionFranchisee(String regionId,String pageId,String sortOrder,String sortKey){

		SequenceMap data = new SequenceMap();
		/*String totalRecords = "0";
		String order = null;

		if(sortKey!=null && sortKey.equals("name")){
			order = "FRANCHISEE_NAME";
		}else{
			order = "FRANCHISEE_NAME";
			sortOrder = "DESC";
		}

		StringBuffer query = new StringBuffer();
		StringBuffer sCountQuery = new StringBuffer();

    	query.append("SELECT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE");
		query.append(" ,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM ");
		query.append(" FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO");
		query.append(" AND A.AREA_ID = F.AREA_ID AND F.IS_ADMIN='N' AND ");
		query.append(" ((F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4))");
		if(ModuleUtil.franchiseModulesWithUser()){ //P_E_FC-175
			query.append("  OR (F.IS_STORE='Y' AND F.IS_STORE_ARCHIVED!='Y')");
		}
		query.append(")  AND F.AREA_ID =").append(regionId);
		query.append(" GROUP BY F.FRANCHISEE_NO");
		query.append(" ORDER BY "+order+" "+sortOrder);
  		sCountQuery.append("SELECT COUNT(F.FRANCHISEE_NO) count FROM FRANCHISEE F,OWNERS O, AREAS A WHERE");
		sCountQuery.append(" F.FRANCHISEE_NO=O.FRANCHISEE_NO AND A.AREA_ID = F.AREA_ID AND F.IS_ADMIN='N' ");
		sCountQuery.append(" AND ((F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4)) ");
		if(ModuleUtil.franchiseModulesWithUser()){ //P_E_FC-175
			sCountQuery.append("  OR (F.IS_STORE='Y' AND F.IS_STORE_ARCHIVED!='Y')");
		}
		sCountQuery.append(")  AND F.AREA_ID =").append(regionId);
		sCountQuery.append(" GROUP BY F.FRANCHISEE_NO ");

		int limit 	= 20;
		if(pageId != null && !pageId.equals("") && !pageId.equals("null") && !pageId.equals("0")){
			int page	= Integer.parseInt(pageId);
			int offset	= (page-1)*20;
			query.append(" LIMIT " + offset);
			query.append( " ," + limit);

		}else if(pageId != null && pageId.equals("0")){

		}else{
			query.append(" LIMIT 0 , "+limit);
		}
		ResultSet rs = null;
		ResultSet rs1 = null;
		 try{

		 	
		 	rs	= QueryUtil.getResult(sCountQuery.toString(), null);
			if(rs.next()){
				totalRecords = Integer.toString(rs.size());
				setTotalRecords(totalRecords);
			}
		 	
			rs1	= QueryUtil.getResult(query.toString(), null);
			String name = new String();


			while(rs1.next()) {
				Info info = new Info();

				String city = rs1.getString("CITY");
				String state = rs1.getString("STATE");
				String status = rs1.getString("STATUS");
				//StringBuffer address = new StringBuffer(city);
				//address.append(" / ").append(state);
				info.set(FieldNames.COUNTRY,rs1.getString("COUNTRY"));
				name = NewPortalUtils.getFranchiseIdDisplayName(rs1.getString("FRANCHISEE_NO"), rs1.getString("FRANCHISEE_NAME"));
				if(status.equals("3")){
					name = name+"<span class=\"urgent_fields\"> (CL)</span>";
				}
				info.set(FieldNames.FRANCHISEE_NAME,name);
				info.set(FieldNames.CITY,city);
				info.set(FieldNames.STATE,state);
				//P_FIM_B_39491 By Nikhil Verma
                                
                                //P_ADMIN_B_52855 By ankit saini on 25/11/2009
                                info.set("cityState",city+" / "+state);
                                
				info.set(FieldNames.STORE_PHONE,PortalUtils.formatPhoneNo(rs1.getString("STORE_PHONE"),rs1.getString("COUNTRY")));
				info.set(FieldNames.ZIPCODE,rs1.getString("ZIPCODE"));

				data.put(rs1.getString("FRANCHISEE_NO") , info);

			}
		}
		catch(Exception e){
		  	logger.error("Exception in getRegionFranchisee :"+e);
			return null;
		}
		finally
		{
			QueryUtil.releaseResultSet(rs);
			QueryUtil.releaseResultSet(rs1);
		}*/

		return data;
	}//getRegionFranchisee ends

//This method is used for getting the areaName
	public String getAreaName(String areaId)
	{
		String areaName = "";
		ResultSet rs=null;
		try
		{
			StringBuffer query = new StringBuffer("SELECT AREA_NAME  FROM AREAS WHERE AREA_ID=").append(areaId);
			 rs	= QueryUtil.getResult(query.toString(), null);
			if(rs.next())
			{
				areaName= rs.getString("AREA_NAME");
			}
			return areaName;
		}
		catch(Exception e)
		{
			logger.error("Exception in getAreaName :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

	}//getAreaName ends

//This method is used for getting all the existing areaNames
	public String getExistingAreaName()
	{
		StringBuffer areaName	= new StringBuffer("");
		ArrayList areaNameList	= new ArrayList();
		ResultSet rs=null;
		try
		{
			StringBuffer query = new StringBuffer("SELECT AREA_NAME  FROM AREAS WHERE IS_DELETED='N'");
			 rs	= QueryUtil.getResult(query.toString(), null);
			while(rs.next())
			{
				areaNameList.add(rs.getString("AREA_NAME"));
			}
			int size=areaNameList.size();
			for(int i=0; i<size; i++){
				areaName.append((String)areaNameList.get(i));
				if(i!=(size-1)){
					areaName.append(",");
				}
			}
			
			return areaName.toString();
		}
		catch(Exception e)
		{
			logger.error("Exception in getExistingAreaName :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

	}//getExistingAreaName ends

//This method is for getting the stateId of a region
	public String getRegionObjList(String regionType,String regionId){

		String list = "";
		StringBuffer query = null;
		ResultSet rs=null;
		try{
			//P_E_ADMIN_1000 By Nikhil Verma
			if(regionType.equals("1") || regionType.equals("3")){
				query = new StringBuffer("SELECT STATE_ID FROM STATE_AREAS WHERE AREA_ID = ").append(regionId);
			}
			//P_E_ADMIN_1000 By Nikhil Verma
			else if(regionType.equals("2") || regionType.equals("4")){
				query = new StringBuffer("SELECT ZIPCODE FROM ZIPCODE_AREAS WHERE AREA_ID = ").append(regionId);
			}
			//P_E_ADMIN_1000 By Nikhil Verma
			/*else if(regionType.equals("3")){
				query = new StringBuffer("SELECT COUNTRY_ID FROM INTERNATIONAL_AREAS WHERE AREA_ID = ").append(regionId);
			}*/
			 rs	= QueryUtil.getResult(query.toString(), null);
			while(rs.next()){
				//P_E_ADMIN_1000 By Nikhil Verma
				/*if(regionType.equals("3")){
					list = list + rs.getString("COUNTRY_ID") + ",";
				}else */if(regionType.equals("1") || regionType.equals("3")){
					list = list + rs.getString("STATE_ID") + ",";
				}else{
					list = list + rs.getString("ZIPCODE") + ",";
				}
			}
			
			if (list.indexOf(',')!=-1){
				list = list.substring(0, list.length()-1);
			}
				
			/*while(rs.next()){
				list = list + "," + rs.getString("STATE_ID");
			}
			*/
		}
		catch(Exception e){
			logger.error("Exception in getRegionObjList  :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

		return list;
	}// getRegionObjList ends


//this method is for getting the list of franchisee of other area/region
	public SequenceMap getRegionOtherFranchisee(String stateId,String regionid,String regionType){

		SequenceMap data = new SequenceMap();
		/*StringBuffer query = new StringBuffer();

    	query.append("SELECT DISTINCT F.FRANCHISEE_NO,");
		query.append(" A.AREA_NAME,F.FRANCHISEE_NAME,F.CITY, ");
		query.append(" F.STATE,F.ZIPCODE,F.COUNTRY,F.STATUS");
		//P_B_55181 starts
		query.append(" FROM AREAS A, FRANCHISEE F WHERE F.IS_ADMIN='N' AND F.STATUS NOT IN (0,4) ");
		query.append(" AND ((F.IS_FRANCHISEE='Y')");
		if(ModuleUtil.franchiseModulesWithUser()){ //P_E_FC-175
			query.append("  OR (F.IS_STORE='Y' AND F.IS_STORE_ARCHIVED!='Y')");
		}
		query.append(") AND F.AREA_ID = A.AREA_ID AND ");
		//P_B_55181 ends
		//P_E_ADMIN_1000 By Nikhil Verma
		if(regionType.equals("3")){
			query.append(" F.COUNTRY_ID IN (").append(stateId).append(") AND ");
		}else if(regionType.equals("1") || regionType.equals("3")){
			query.append(" REGION_NO IN (").append(stateId).append(") AND ");
		}else{
			query.append(" F.ZIPCODE  IN (").append(stateId).append(") AND ");
		}
		query.append(" F.AREA_ID <> ").append(regionid).append(" ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME");
		ResultSet rs1 = null;
		 try{
		 	
			rs1	= QueryUtil.getResult(query.toString(), null);
			String name = new String();
			while(rs1.next()) {
				Info info = new Info();
				info.set("contactidFranchisee" ,"<input type='checkbox' name='contactidFran' value=\""+rs1.getString("FRANCHISEE_NO")+"\" onClick='deSelect();'>");
				String status = rs1.getString("STATUS");
				info.set(FieldNames.ZIPCODE,rs1.getString("ZIPCODE"));
				info.set(FieldNames.COUNTRY,rs1.getString("COUNTRY"));
				info.set(FieldNames.STATE,rs1.getString("STATE"));
				name = NewPortalUtils.getFranchiseIdDisplayName(rs1.getString("FRANCHISEE_NO"), rs1.getString("FRANCHISEE_NAME"));
				if(status.equals("3")){
					name = name+"<span class=\"urgent_fields\"> (CL)</span>";
				}
				info.set(FieldNames.FRANCHISEE_NAME,name);
				info.set(FieldNames.CITY,rs1.getString("CITY"));
				info.set(FieldNames.AREA_NAME,rs1.getString("AREA_NAME"));

				data.put(rs1.getString("FRANCHISEE_NO") , info);
			}
		}
		catch(Exception e){
		  	logger.error("Exception in getRegionOtherFranchisee :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs1);
		}*/
		return data;
	}// getRegionOtherFranchisee ends

	public String readZipcodeFile(String fileName) {
		
		String line			= "";
		String list		= null;
		FileReader reader	= null;
		try{
			reader = new FileReader(fileName);
		}
		catch (FileNotFoundException e){
			deleteFile(fileName);		// Delete the file
			try{
				reader.close();			// Close the FileReader
			}catch (Exception e1){}
			logger.error("FileNotFoundException in readZipcodeFile : " +e);
		}
		
		BufferedReader buff = new BufferedReader(reader);
		try{
			while ((line = buff.readLine()) != null){
				if(list == null){
					list = line;
				}
				else{
					list = list + "," + line;
				}
			}
			reader.close();				// Close the FileReader
			deleteFile(fileName);		// Delete the file
			return list;
		}
		catch (IOException e){
			try{
				reader.close();
			}
			catch (Exception exp){}
			logger.error("IOException in readZipcodeFile : " +e);
		}
		finally{
			deleteFile(fileName);
		}
		return null;
	}// readZipcodeFile ends

	public void deleteFile(String fileName){
		try{
			File file = new File(fileName);
			file.delete();
		}catch (Exception e){
			logger.error("Exception in deleteFile : " +e);
		}
	}// deleteFile ends

	public ArrayList zipExists(String[] zip,String rId) {
		ArrayList repeats = new ArrayList(0);
		ArrayList newZipCodeList = new ArrayList(0);
		HashMap map=new HashMap();
		if(zip!=null)
		{
			for(int i=0;i<zip.length;i++)
				newZipCodeList.add(zip[i].trim());
		}
		ResultSet rs=null;
		try{
			
			// Query is changed by Nikhil Verma
			StringBuffer sQuery = new StringBuffer("SELECT ZIPCODE FROM ZIPCODE_AREAS ZA,AREAS A  WHERE ZA.AREA_ID=A.AREA_ID AND A.IS_DELETED='N'  ");
			if(rId!=null && !"".equals(rId) &&!rId.equals("-1")){
				sQuery.append(" AND A.AREA_ID <>").append(rId);
			}
			sQuery.append(" ORDER BY ZIPCODE ");
			 rs = QueryUtil.getResult(sQuery.toString(), null);
			while(rs.next())
			{
				if(newZipCodeList.contains(rs.getString("ZIPCODE")) && map.get(rs.getString("ZIPCODE"))==null)
				{
						repeats.add(rs.getString("ZIPCODE"));
						map.put(rs.getString("ZIPCODE"),rs.getString("ZIPCODE"));
				}
			}
		/*	for(int i=0;i<zip.length;i++){
				exist = 0;
				param[0] = zip[i];
				ResultSet rs = QueryUtil.getResult(sQuery.toString(),param);
				if(rs.next()){
					exist = (rs.getInteger(1)).intValue();
				}
				if(exist != 0){
					repeats.add(zip[i]);
				}
			}*/
			return repeats;
		}
		catch(Exception e){
			logger.error("Exception in zipExists : " +e);
			return null;
		}
		finally
		{
			QueryUtil.releaseResultSet(rs);
		}
	}// zipExists ends

	public ArrayList getRegionCountries(String areaId,String areaType) {

		ArrayList detail		= new ArrayList(0);
		String[] param		={areaId};
		StringBuffer sQuery = null;
		ResultSet rs=null;
		try{
			if(areaType.equals("1")){
				sQuery = new StringBuffer("SELECT DISTINCT R.COUNTRY_ID FROM REGIONS R, STATE_AREAS S WHERE S.AREA_ID = ? AND S.STATE_ID = R.REGION_NO ORDER BY R.COUNTRY_ID");
			}
			else if(areaType.equals("2")){
				sQuery = new StringBuffer("SELECT DISTINCT COUNTRY_ID FROM ZIPCODE_AREA_COUNTRY WHERE AREA_ID = ? ORDER BY COUNTRY_ID");
			}
			else if(areaType.equals("3")){
				sQuery = new StringBuffer("SELECT COUNTRY_ID FROM INTERNATIONAL_AREAS WHERE AREA_ID = ?");
			}
			 rs = QueryUtil.getResult(sQuery.toString(),param);
			while(rs.next()){
				detail.add(rs.getString(1));
			}
			return detail;
		}
		catch(Exception e){
			logger.error("Exception in getRegionCountries : " +e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}
	}// getRegionCountries ends

	/*
   This function returns the states/zipcodes/countries comprising a region within a specific country
	*/
	public ArrayList getRegionDetailsByCountry(String areaId,String areaType) {
		
		ArrayList detail = new ArrayList(0);
		StringBuffer sQuery=null;
		String[] param={areaId};
		ResultSet rs=null;
		try{
			if(areaType.equals("1")){
				sQuery = new StringBuffer("SELECT R.REGION_NO AS ID FROM REGIONS R, STATE_AREAS S WHERE R.REGION_NO = S.STATE_ID AND S.AREA_ID = ? ORDER BY REGION_NAME");
			}
			else if(areaType.equals("2")){
				sQuery = new StringBuffer("SELECT A.ZIPCODE AS ID FROM ZIPCODE_AREAS A WHERE A.AREA_ID = ? ORDER BY ZIPCODE");
			}
			else if(areaType.equals("3")){
				sQuery = new StringBuffer("SELECT C.COUNTRY_ID AS ID FROM COUNTRIES C, INTERNATIONAL_AREAS I WHERE C.COUNTRY_ID = I.COUNTRY_ID AND I.AREA_ID = ? ORDER BY NAME");
			}
			
			 rs = QueryUtil.getResult(sQuery.toString(),param);
			while(rs.next()){
				detail.add(rs.getString("ID"));
			}
			return detail;
		}
		catch(Exception e){
			logger.error("Exception in getRegionDetailsByCountry : " +e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

	}// getRegionDetailsByCountry ends
	public boolean modifyRegion(String areaId,String areaName, String areaType, String[] stateid,	String[] country, String[] zipCode,String fieldManager) {
		boolean modified = false;
		StringBuffer sQuery=null;

		String nameBuffer1 =StringUtil.formatName(areaName,null);
		//areaName=nameBuffer1.substring(nameBuffer1.indexOf(",")+1,nameBuffer1.length());
                //Added by manoj 
               // areaName 		= StringUtil.replaceSubString(areaName,"\'","&#34;");
                //areaName 		= StringUtil.replaceSubString(areaName,"\"","&#39;");
		// Commented By Nikhil Verma P_ADMIN_B_39684
            //     areaName 		= StringUtil.replaceSubString(areaName,"(","&#40;");
              //    areaName 		= StringUtil.replaceSubString(areaName,")","&#41;");
		String[] param={areaName,fieldManager,areaId};
		int result= -1;     
		try{
			sQuery	=	new StringBuffer("UPDATE AREAS SET AREA_NAME= ?,FIELD_MANAGER=? WHERE AREA_ID = ?");
                        
			result	=	QueryUtil.update(sQuery.toString(),param);
                        
			String[] param1={areaId};
			if(areaType.equals("1")){
				sQuery	=	new StringBuffer("DELETE FROM STATE_AREAS WHERE AREA_ID = ?");
				result	=	QueryUtil.update(sQuery.toString(),param1);
	
				modified = addStateToRegion(areaId,stateid);
//				36879
				UpdateLeadForAreaId(areaId);
			}
			else if(areaType.equals("2")){
				sQuery	=	new StringBuffer("DELETE FROM ZIPCODE_AREAS WHERE AREA_ID = ?");
				result	=	QueryUtil.update(sQuery.toString(),param1);
				
				sQuery	=	new StringBuffer("DELETE FROM ZIPCODE_AREA_COUNTRY WHERE AREA_ID = ?");
				result	=	QueryUtil.update(sQuery.toString(),param1);
				
	
				modified = addZipcodeToRegion(areaId,country,zipCode);
//				36879
				UpdateLeadForAreaId(areaId);
			}
			else if(areaType.equals("3")){
				sQuery	=	new StringBuffer("DELETE FROM INTERNATIONAL_AREAS WHERE AREA_ID = ?");
				result	=	QueryUtil.update(sQuery.toString(),param1);
	
				modified = addCountryToRegion(areaId,country);
			}else if(areaType.equals("5")){
				sQuery	=	new StringBuffer("DELETE FROM COUNTY_AREAS WHERE AREA_ID = ?");
				result	=	QueryUtil.update(sQuery.toString(),param1);
	
			}			
			
			
			
			
			return modified;
		}
		catch(Exception e){
			logger.error("Exception in modifyRegion : " +e);
			return false;
		}
	}// modifyRegion ends
	
	
	public void UpdateLeadForAreaId(String areaId) {
        boolean leadUpdated = false;
        ArrayList leadIdList = new ArrayList();
        String query = null;
        String zipCode = null;
        String stateIdCode = null;
        String countyId = null;
        String country = null;
        String newAreaID = null;
        ResultSet rs = null;
        int length = 0;
        try {
            query = "SELECT LEAD_ID,FIRST_NAME	 FROM FS_LEAD_DETAILS WHERE AREA_ID='" + areaId + "'";
            rs = QueryUtil.getResult(query, null);
            while (rs.next()) {
                leadIdList.add(rs.getString("LEAD_ID"));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        String leadId = null;
        if (leadIdList != null && leadIdList.size() > 0) {
            for (int i = 0; i < leadIdList.size(); i++) {
                leadId = (String) leadIdList.get(i);
                if (leadId != null && leadId.length() > 0 && !leadId.trim().equals("")) {
                    try {
                        query = "SELECT AREA_ID,STATE_ID,ZIP,COUNTY_ID,COUNTRY FROM FS_LEAD_DETAILS WHERE LEAD_ID='" + leadId + "'";
                        rs = QueryUtil.getResult(query, null);
                        if (rs.next()) {
                            zipCode = rs.getString("ZIP");
                            stateIdCode = rs.getString("STATE_ID");
                            countyId = rs.getString("COUNTY_ID");
                            country = rs.getString("COUNTRY");
                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    newAreaID = ZipcodeMgr.newInstance().getZipCodeDAO().getAreaIdForIntContry(country, stateIdCode, zipCode, countyId);
                    if (newAreaID != null && newAreaID.trim().length() > 0 && !newAreaID.trim().equals("")) {
                        try {
                            query = "UPDATE FS_LEAD_DETAILS SET AREA_ID = ? WHERE LEAD_ID=? ";
                            length = QueryUtil.update(query, new String[] { newAreaID, leadId });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        String stateIdList = RegionMgr.newInstance().getRegionsDAO().getStateIdByAreaID(areaId);
        String zipCodeList = ZipcodeMgr.newInstance().getZipCodeDAO().getZipCodeByAreaID(areaId);
        if (stateIdList == null || stateIdList.trim().equals("")) {
            stateIdList = "'0'";
        }
        if (zipCodeList == null || zipCodeList.trim().equals("")) {
            zipCodeList = "0";
        }
        updateAreaIdByStateAndZip(stateIdList, zipCodeList);
    }
	
	/**
    *
    *
    *
    * This method used to update AreaId based on stateids and zip codes
    */
   public void updateAreaIdByStateAndZip(String state, String zip) {
       String zipCode = null;
       String stateIdCode = null;
       String countyId = null;
       String country = null;
       String newAreaID = null;
       ResultSet rs = null;
       String query = null;
       int length = 0;
       ArrayList leadIdList = new ArrayList();
       query = "SELECT LEAD_ID,FIRST_NAME FROM FS_LEAD_DETAILS WHERE (STATE_ID IN (" + state + ") OR (ZIP IN (" + zip + ") AND ZIP !=0))";
       rs = QueryUtil.getResult(query, null);
       while (rs.next()) {
           leadIdList.add(rs.getString("LEAD_ID"));
       }
       String leadId = null;
       if (leadIdList != null && leadIdList.size() > 0) {
           for (int i = 0; i < leadIdList.size(); i++) {
               leadId = (String) leadIdList.get(i);
               if (leadId != null && leadId.length() > 0 && !leadId.trim().equals("")) {
                   try {
                       query = "SELECT STATE_ID,ZIP,COUNTY_ID,COUNTRY FROM FS_LEAD_DETAILS WHERE LEAD_ID='" + leadId + "'";
                       rs = QueryUtil.getResult(query, null);
                       if (rs.next()) {
                           zipCode = rs.getString("ZIP");
                           stateIdCode = rs.getString("STATE_ID");
                           countyId = rs.getString("COUNTY_ID");
                           country = rs.getString("COUNTRY");
                       }
                   } catch (RuntimeException e) {
                       e.printStackTrace();
                   }
                   newAreaID = ZipcodeMgr.newInstance().getZipCodeDAO().getAreaIdForIntContry(country, stateIdCode, zipCode, countyId);
                   if (newAreaID != null && newAreaID.trim().length() > 0 && !newAreaID.trim().equals("")) {
                       try {
                           query = "UPDATE FS_LEAD_DETAILS SET AREA_ID = ? WHERE LEAD_ID=? ";
                           length = QueryUtil.update(query, new String[] { newAreaID, leadId });
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               }
           }
       }
   }

	//P_B_FIM_63341 added by neeti starts
	/*
	 * for getting area Type on the basis of areaID
	 * author: Neeti Solanki
	 */
	public String getAreaType(String areaID)
	{
		String areaType=null;
       StringBuffer query = new StringBuffer("SELECT DISTINCT A.AREA_TYPE FROM AREAS A where A.AREA_ID='"+areaID+"'");
    	ResultSet rs=null;
        try{
             rs = QueryUtil.getResult(query.toString(), null);
            while (rs.next()) {
            	areaType=rs.getString("AREA_TYPE");
            }
            
        }catch(Exception e){
        	
            logger.error("\nException in com/appnetix/app/components/regionmgr/manager/dao - RegionsDAO.java --> getAreaType(String areaID)"+e);
        }finally
		{
			QueryUtil.releaseResultSet(rs);
		}
        return areaType;
		
		
	}
	//P_B_FIM_63341 added by neeti ends
	public SequenceMap getAllDomesticRegions(String sortOrder,String sortKey,String countryId,String regionKey ,String key,String pageId) {

		StringBuffer sQueryCountryDom		= new StringBuffer("");
		StringBuffer sQueryCountryInt		= new StringBuffer("");
		StringBuffer sQueryCountryCounty	= new StringBuffer("");
		String Area_Iddom = "";
		String Area_Idint = "";
		String Area_Idcounty = "";
		SequenceMap sMap = new SequenceMap();
		ResultSet rsNext=null;
		try{
			
			if(countryId != null && !countryId.equals("") && !countryId.equals("-1")){
			sQueryCountryDom.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM STATE_AREAS WHERE COUNTRY_ID IN ("+countryId).append(")");
			sQueryCountryInt.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM ZIPCODE_AREA_COUNTRY WHERE COUNTRY_ID IN ("+countryId).append(")");
			sQueryCountryCounty.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM COUNTY_AREAS WHERE COUNTRY_ID IN ("+countryId).append(")");
			
			 rsNext = QueryUtil.getResult(sQueryCountryDom.toString(), new Object[]{});
			if(rsNext.next()){
				Area_Iddom = rsNext.getString("areaid");
			}

			ResultSet rsNextInt = QueryUtil.getResult(sQueryCountryInt.toString(), new Object[]{});
			if(rsNextInt.next()){
				Area_Idint = rsNextInt.getString("areaid");
			}
			
			ResultSet rsNextCounty = QueryUtil.getResult(sQueryCountryCounty.toString(), new Object[]{});
			if(rsNextCounty.next()){
				Area_Idcounty = rsNextCounty.getString("areaid");
			}
			
			}
                        //P_ADMIN_E_11062010
			//StringBuffer sQuery	= new StringBuffer("SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREA_TYPE=1 AND IS_DELETED='N' ");
			//StringBuffer sQuery	= new StringBuffer(" SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A,STATE_AREAS,COUNTRIES LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE=1 AND A.IS_DELETED='N' ");    //P_ENH_MYSQL55
                        //BUG_4968 10-02-2012 Narotam Singh starts
			//StringBuffer sQuery	= new StringBuffer(" SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN   (USERS U LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO) on A.FIELD_MANAGER=U.USER_NO,STATE_AREAS,COUNTRIES WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE=1 AND A.IS_DELETED='N'");
                        StringBuffer sQuery	= new StringBuffer(" SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, STATE_AREAS,COUNTRIES WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE=1 AND A.IS_DELETED='N' ");
                        //BUG_4968 10-02-2012 Narotam Singh ends
                        
			StringBuffer sQueryCountDom 	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREA_TYPE =1 AND IS_DELETED='N' ");
			
			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {           //P_ADMIN_E_11062010
			sQuery.append(" AND A.AREA_ID  IN( "+Area_Iddom+")");
			sQueryCountDom.append(" AND AREAS.AREA_ID  IN( "+Area_Iddom+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCountDom.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCountDom.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID  ");
			//P_E_ADMIN_1000 By Nikhil Verma
			//sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND A.AREA_TYPE=2 AND A.IS_DELETED='N'  ");      //P_ENH_MYSQL55
                        //BUG_4968 10-02-2012 Narotam Singh starts
			//sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN   (USERS U LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO) on A.FIELD_MANAGER=U.USER_NO,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND A.AREA_TYPE=2 AND A.IS_DELETED='N'  ");
			sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND A.AREA_TYPE=2 AND A.IS_DELETED='N'  ");
                        //BUG_4968 10-02-2012 Narotam Singh ends
			//sQuery.append(" UNION SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=2 AND IS_DELETED='N' ");
			
			StringBuffer sQueryCountInt 	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=2 AND IS_DELETED='N' ");

			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN( "+Area_Idint+")");
				sQueryCountInt.append(" AND AREAS.AREA_ID IN( "+Area_Idint+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCountInt.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCountInt.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID ");
			
			sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, COUNTY_AREAS CA,COUNTRIES C WHERE A.AREA_ID = CA.AREA_ID AND C.COUNTRY_ID = CA.COUNTRY_ID AND A.AREA_TYPE=5 AND A.IS_DELETED='N'  ");
			
			StringBuffer sQueryCountCounty 	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,COUNTY_AREAS CA,COUNTRIES WHERE AREAS.AREA_ID = CA.AREA_ID AND COUNTRIES.COUNTRY_ID = CA.COUNTRY_ID AND AREA_TYPE=5 AND IS_DELETED='N' ");
			
			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN( "+Area_Idcounty+")");
				sQueryCountCounty.append(" AND AREAS.AREA_ID IN( "+Area_Idcounty+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCountCounty.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCountCounty.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID ");
			
			// new added
			if (sortKey.equals("areaName")){
				sQuery.append(" ORDER BY AREA_NAME ").append(sortOrder);
			}
			else if(sortKey.equals("areaType")){
				sQuery.append(" ORDER BY AREA_TYPE ").append(sortOrder);
			}
			else if(sortKey.equals("country")){
				sQuery.append(" ORDER BY CountryName ").append(sortOrder);
			}//P_ADMIN_E_11062010
			else if(sortKey.equals("fieldManager")){
                            sQuery.append(" ORDER BY FIRST_NAME ").append(sortOrder);
                            sQuery.append(" , LAST_NAME ").append(sortOrder);
                        }else
                        {
				sQuery.append(" ORDER BY A.AREA_NAME ").append(sortOrder);
			}
			// new added

			int nTotal =0;
			
			ResultSet rsCount = QueryUtil.getResult(sQueryCountDom.toString(), new Object[]{});
			if(rsCount.next()){
				nTotal = Integer.parseInt(rsCount.getString("count"));
				}

			ResultSet rsCountZip = QueryUtil.getResult(sQueryCountInt.toString(), new Object[]{});
			if(rsCountZip.next()){
				nTotal += Integer.parseInt(rsCountZip.getString("count"));
				
			}
			
			ResultSet rsCountCounty = QueryUtil.getResult(sQueryCountCounty.toString(), new Object[]{});
			if(rsCountCounty.next()){			//OTF-20160621-190 Udai Agarwal 09/08/2016
				nTotal += Integer.parseInt(rsCountCounty.getString("count"));
				
			}
			setTotalRecords(String.valueOf(nTotal));
			
			int limit 	= 20;
			if(pageId != null && !pageId.equals("") && !pageId.equals("null") && !pageId.equals("0")){
				int page	= Integer.parseInt(pageId);
				int offset	= (page-1)*20;
				sQuery.append(" LIMIT " + offset);
				sQuery.append( " ," + limit);
			}else if(pageId != null && pageId.equals("0")){
				
			}else{
				sQuery.append(" LIMIT 0 , "+limit);
			}
			ResultSet rs = QueryUtil.getResult(sQuery.toString(), null);
			Info info	= null;
			while(rs.next()){
				info	= new Info();
				info.set(FieldNames.AREA_NAME,rs.getString("AREA_NAME"));
				info.set(FieldNames.AREA_TYPE,rs.getString("AREA_TYPE"));
				info.set(FieldNames.AREA_ID,rs.getString("AREA_ID"));
				info.set("fieldManager",StringUtil.formatName(rs.getString("FIRST_NAME"),rs.getString("LAST_NAME") ));//P_ADMIN_E_11062010
				info.set(FieldNames.COUNTRY,rs.getString("CountryName"));
				sMap.put(rs.getString("AREA_ID"),info);
			}

			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getAllDomesticRegions : " +e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rsNext);
		}


	}// getAllDomesticRegions ends


	public SequenceMap getAllInternationalRegions(String sortOrder,String sortKey,String countryId,String regionKey,String key,String pageId) {

		String Area_Idint = "";
		String Area_IdintZipCode = FieldNames.EMPTY_STRING;
		String Area_Idcounty = "";
		StringBuffer sQueryCountryInt	= new StringBuffer("");
		StringBuffer sQueryCountryZipCode	= new StringBuffer("");
		StringBuffer sQueryCountryCounty	= new StringBuffer("");
		SequenceMap sMap = new SequenceMap();
		ResultSet rsNext=null;
		countryId=StringUtil.isValid(countryId)?StringUtil.toCommaSeparatedSpaceLess(countryId.split(",")):"-1";
		try{
			//String sQuery	= "SELECT AREA_ID,AREA_NAME FROM AREAS WHERE AREA_TYPE = 3 ORDER BY AREA_NAME";
			
			if(StringUtil.isValidNew(countryId)){
			
			sQueryCountryInt.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM STATE_AREAS WHERE COUNTRY_ID IN ("+countryId).append(")");
			sQueryCountryZipCode.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM ZIPCODE_AREA_COUNTRY WHERE COUNTRY_ID IN ("+countryId).append(")");
			sQueryCountryCounty.append("SELECT GROUP_CONCAT(DISTINCT AREA_ID) areaid FROM COUNTY_AREAS WHERE COUNTRY_ID IN ("+countryId).append(")");
			 rsNext = QueryUtil.getResult(sQueryCountryInt.toString(), new Object[]{});
			if(rsNext.next()){
				Area_Idint = rsNext.getString("areaid");
			}
			rsNext = QueryUtil.getResult(sQueryCountryZipCode.toString(), new Object[]{});
			if(rsNext.next()){
				Area_IdintZipCode = rsNext.getString("areaid");
			}
			ResultSet rsNextCounty = QueryUtil.getResult(sQueryCountryCounty.toString(), new Object[]{});
			if(rsNextCounty.next()){
				Area_Idcounty = rsNextCounty.getString("areaid");
			}
			}
			// modified by rakesh for P_Admin_B_36894 ,  Starts
			//StringBuffer sQuery	= new StringBuffer("SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,GROUP_CONCAT(DISTINCT NAME  SEPARATOR ', ') CountryName FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREA_TYPE IN(3,4) AND IS_DELETED='N' ");
                        //P_ADMIN_E_11062010
			//StringBuffer sQuery	= new StringBuffer("SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A,STATE_AREAS,COUNTRIES LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE IN(3,4) AND A.IS_DELETED='N'  ");     //P_ENH_MYSQL55
                        //BUG_4968 10-02-2012 Narotam Singh starts
			//StringBuffer sQuery	= new StringBuffer("SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN   (USERS U LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO) on A.FIELD_MANAGER=U.USER_NO,STATE_AREAS,COUNTRIES WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE IN(3,4) AND A.IS_DELETED='N'  ");
			StringBuffer sQuery	= new StringBuffer("SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, STATE_AREAS,COUNTRIES WHERE A.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND A.AREA_TYPE IN(3,4) AND A.IS_DELETED='N'  ");
                        //BUG_4968 10-02-2012 Narotam Singh ends
			//StringBuffer sQuery	= new StringBuffer("SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREA_TYPE IN(3,4) AND IS_DELETED='N' ");
			// modified by rakesh for P_Admin_B_36894 ,  Ends

			StringBuffer sQueryCount	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREA_TYPE IN(3,4) AND IS_DELETED='N' ");

			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {           //P_ADMIN_E_11062010
			sQuery.append(" AND A.AREA_ID IN ("+Area_Idint+")");
			sQueryCount.append(" AND AREAS.AREA_ID IN ("+Area_Idint+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCount.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCount.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID ");
			// new added
			// Commented By Nikhil Verma
		/*	if (sortKey.equals("areaName")){
				sQuery.append(" ORDER BY AREA_NAME ").append(sortOrder);
			}
			else if(sortKey.equals("country")){
				sQuery.append(" ORDER BY CountryName ").append(sortOrder);
			}
			else {
				sQuery.append(" ORDER BY AREAS.AREA_NAME ").append(sortOrder);
			}*/
			// new added
			
			//sQuery.append(" UNION SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,GROUP_CONCAT(DISTINCT NAME  SEPARATOR ', ') CountryName FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND IS_DELETED='N' ");
                        //P_ADMIN_E_11062010
			//sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND A.IS_DELETED='N' ");                          //P_ENH_MYSQL55
			//BUG_4968 10-02-2012 Narotam Singh starts
                        //sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN   (USERS U LEFT JOIN   FRANCHISEE F  ON F.FRANCHISEE_NO=U.FRANCHISEE_NO) on A.FIELD_MANAGER=U.USER_NO,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND A.IS_DELETED='N' ");
			sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES WHERE A.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND A.IS_DELETED='N' ");
                        //BUG_4968 10-02-2012 Narotam Singh ends
			//sQuery.append(" UNION SELECT AREAS.AREA_ID,AREA_NAME,AREA_TYPE,FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME ORDER BY NAME ASC SEPARATOR ', ') CountryName FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND IS_DELETED='N' ");
			
			StringBuffer sQueryCountInt 	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREA_TYPE=4 AND IS_DELETED='N' ");

			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN( "+Area_IdintZipCode+")");
				sQueryCountInt.append(" AND AREAS.AREA_ID IN( "+Area_IdintZipCode+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCountInt.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCountInt.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID ");
			
			sQuery.append(" UNION SELECT FIRST_NAME,LAST_NAME,A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.FIELD_MANAGER,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS A LEFT JOIN USERS U ON A.FIELD_MANAGER=U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO, COUNTY_AREAS CA,COUNTRIES C WHERE A.AREA_ID = CA.AREA_ID AND C.COUNTRY_ID = CA.COUNTRY_ID AND A.AREA_TYPE=6 AND A.IS_DELETED='N'  ");
			
			StringBuffer sQueryCountCounty 	= new StringBuffer("SELECT COUNT(DISTINCT AREAS.AREA_ID) count FROM AREAS ,COUNTY_AREAS CA,COUNTRIES WHERE AREAS.AREA_ID = CA.AREA_ID AND COUNTRIES.COUNTRY_ID = CA.COUNTRY_ID AND AREA_TYPE=6 AND IS_DELETED='N' ");
			
			if(countryId != null && !countryId.equals("") && !countryId.equals("-1"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN( "+Area_Idcounty+")");
				sQueryCountCounty.append(" AND AREAS.AREA_ID IN( "+Area_Idcounty+")");
			}
			if(regionKey != null && !regionKey.equals("") && !regionKey.equals("-1") && !regionKey.equals("0"))
            {                   //P_ADMIN_E_11062010
				sQuery.append(" AND A.AREA_ID IN ("+regionKey).append(")");
				sQueryCountCounty.append(" AND AREAS.AREA_ID IN ("+regionKey).append(")");
			}
			if(key!=null && !key.equals("")){
				sQuery.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
				sQueryCountCounty.append(" AND AREA_NAME REGEXP \'^["+ key +"]\'");
			}
			sQuery.append(" GROUP BY AREA_ID ");
			
			// new added
			if (sortKey.equals("areaName")){
				sQuery.append(" ORDER BY AREA_NAME ").append(sortOrder);
			}
			else if(sortKey.equals("country")){
				sQuery.append(" ORDER BY CountryName ").append(sortOrder);
			}
			else if(sortKey.equals(FieldNames.AREA_TYPE)){
				sQuery.append(" ORDER BY AREA_TYPE ").append(sortOrder);
			}//P_ADMIN_E_11062010
			else if(sortKey.equals("fieldManager")){
                             sQuery.append(" ORDER BY FIRST_NAME ").append(sortOrder);
                            sQuery.append(" , LAST_NAME ").append(sortOrder);
                        }else
                        {
				sQuery.append(" ORDER BY A.AREA_NAME ").append(sortOrder);
			}
			
			int nTotal=0;
			ResultSet rsCount = QueryUtil.getResult(sQueryCount.toString(), new Object[]{});
			if(rsCount.next()){
				nTotal = Integer.parseInt(rsCount.getString("count"));
				
			}
			ResultSet rsCountZip = QueryUtil.getResult(sQueryCountInt.toString(), new Object[]{});
			if(rsCountZip.next()){
				nTotal += Integer.parseInt(rsCountZip.getString("count"));
				
			}
			
			ResultSet rsCountCounty = QueryUtil.getResult(sQueryCountCounty.toString(), new Object[]{});
			if(rsCountZip.next()){
				nTotal += Integer.parseInt(rsCountCounty.getString("count"));
				
			}
			
			setTotalRecordsInt(String.valueOf(nTotal));
			int limit 	= 20;
			if(pageId != null && !pageId.equals("") && !pageId.equals("null") && !pageId.equals("0")){
				int page	= Integer.parseInt(pageId);
				int offset	= (page-1)*20;
				sQuery.append(" LIMIT " + offset);
				sQuery.append( " ," + limit);
			}else if(pageId != null && pageId.equals("0")){
				
			}else{
				sQuery.append(" LIMIT 0 , "+limit);
			}
                        ResultSet rs = QueryUtil.getResult(sQuery.toString(), null);
			Info info	= null;
			while(rs.next()){
				info	= new Info();
				info.set(FieldNames.AREA_NAME,rs.getString("AREA_NAME"));
				info.set(FieldNames.AREA_ID,rs.getString("AREA_ID"));
				info.set(FieldNames.COUNTRY,rs.getString("CountryName"));
				info.set(FieldNames.AREA_TYPE,rs.getString("AREA_TYPE"));
                                info.set("fieldManager",StringUtil.formatName(rs.getString("FIRST_NAME"),rs.getString("LAST_NAME") ));//P_ADMIN_E_11062010
				sMap.put(rs.getString("AREA_ID"),info);
			}

			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getAllInternationalRegions : " +e);
			return null;
		}
		finally
		{
			QueryUtil.releaseResultSet(rsNext);
		}


	}// getAllInternationalRegions ends


	public String[] getRegionsCountries(String AreaId ,String AreaType ) {
	
		String[] countryReg = new String[2];
		String sQuery	=  "";
	ResultSet rs=null;
		try{
			
			if(AreaType.equals("1") || AreaType.equals("3")){
			
				sQuery	= "SELECT AREA_NAME,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,STATE_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = STATE_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = STATE_AREAS.COUNTRY_ID AND AREAS.AREA_ID = "+AreaId+" GROUP BY AREAS.AREA_ID";
			}else if(AreaType.equals("2") || AreaType.equals("4")){

				sQuery	= "SELECT AREA_NAME,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,ZIPCODE_AREA_COUNTRY ZAC,COUNTRIES  WHERE AREAS.AREA_ID = ZAC.AREA_ID AND COUNTRIES.COUNTRY_ID = ZAC.COUNTRY_ID AND AREAS.AREA_ID = "+AreaId+" GROUP BY AREAS.AREA_ID";
			}else if(AreaType.equals("5")){

				sQuery	= "SELECT AREA_NAME,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,COUNTY_AREAS CA,COUNTRIES  WHERE AREAS.AREA_ID = CA.AREA_ID AND COUNTRIES.COUNTRY_ID = CA.COUNTRY_ID AND AREAS.AREA_ID = "+AreaId+" GROUP BY AREAS.AREA_ID";
			}else{

				sQuery	= "SELECT AREA_NAME,GROUP_CONCAT(DISTINCT NAME SEPARATOR ', ') CountryName FROM AREAS ,INTERNATIONAL_AREAS,COUNTRIES  WHERE AREAS.AREA_ID = INTERNATIONAL_AREAS.AREA_ID AND COUNTRIES.COUNTRY_ID = INTERNATIONAL_AREAS.COUNTRY_ID AND AREAS.AREA_ID = "+AreaId+" GROUP BY AREAS.AREA_ID";
			}
			 rs = QueryUtil.getResult(sQuery, new Object[]{});
			while(rs.next()){
				countryReg[0] = rs.getString("AREA_NAME");
				countryReg[1] = rs.getString("CountryName");
			}
		}
		catch(Exception e){
			logger.error("Exception in getRegionsCountries : " +e);
			return null;
		}
		finally
		{
			QueryUtil.releaseResultSet(rs);
		}

		return countryReg;
	}// getRegionsCountries ends

	public ArrayList getCountries(){
		ArrayList countries = new ArrayList();
		String data[] = null;
		ResultSet rs=null;
		try{
			data= new String[2];
			String Query = "SELECT COUNTRY_ID,NAME FROM COUNTRIES WHERE NAME!='None' AND SHOW_COUNTRY = 1 ORDER BY NAME";
			 rs	= QueryUtil.getResult(Query,null);
			while(rs.next()){
				data= new String[2];
				data[0] = rs.getString("COUNTRY_ID");
				data[1] = rs.getString("NAME");
				countries.add(data);
			}
			return countries;
		}//try
		catch(Exception e){
			logger.info("Exception in getCountries :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

	}// getCountries ends


//Method to retrive all Area names and ids for regional user
	public ArrayList getAllRegions()  {
		ArrayList regions = new ArrayList(0);
		String data[] = null;
		ResultSet rs=null;
		try{
			String QUERY = "SELECT AREA_ID,AREA_NAME,AREA_TYPE FROM AREAS WHERE IS_DELETED='N'  ORDER BY AREA_NAME";
			 rs	= QueryUtil.getResult(QUERY,null);
			while(rs.next()){
				data = new String[3];
				data[0] = rs.getString("AREA_ID");
				data[1] = rs.getString("AREA_NAME");
				data[2] = rs.getString("AREA_TYPE");
				regions.add(data);
			}
			return regions;
		}//try
		catch(Exception e){
			logger.info("Excetpion in getAllRegions :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

	}//getAllRegions ends 


	public SequenceMap getRegionsStates(String AreaId ) {
		//String StateReg = "";
		String sQuery	=  "";
		SequenceMap data=new SequenceMap();
		ResultSet rs=null;
		try{
			
			sQuery	= "SELECT COU.NAME,GROUP_CONCAT(R.REGION_NAME ORDER BY R.REGION_NAME SEPARATOR ', ') AS STATE_NAME FROM STATE_AREAS SA LEFT JOIN REGIONS R ON R.REGION_NO=SA.STATE_ID LEFT JOIN COUNTRIES COU ON COU.COUNTRY_ID=SA.COUNTRY_ID  WHERE SA.AREA_ID="+AreaId+" GROUP BY SA.COUNTRY_ID ORDER BY COU.NAME";
			
			 rs = QueryUtil.getResult(sQuery, new Object[]{});
			while(rs.next()){
			//StateReg = rs.getString("RegionName");
				data.put(rs.getString("NAME"),rs.getString("STATE_NAME"));
			}
			}
		catch(Exception e){
			logger.info("Excetpion in getRegionsStates :"+e);	
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

		return data;
	}// getRegionsStates ends


	public String getRegionsZipCodes(String AreaId ) {
		String StateReg = "";
		String sQuery	=  "";
		ResultSet rs=null;
		try{
			sQuery	= "SELECT GROUP_CONCAT(DISTINCT ZIPCODE ORDER BY ZIPCODE SEPARATOR ', ') ZipCodes FROM AREAS,ZIPCODE_AREAS  WHERE ZIPCODE_AREAS.AREA_ID = AREAS.AREA_ID  AND AREAS.AREA_ID = "+AreaId+" GROUP BY AREAS.AREA_ID";
			
			 rs = QueryUtil.getResult(sQuery, new Object[]{});
			while(rs.next()){
			StateReg = rs.getString("ZipCodes");
			}
			}
		catch(Exception e){
			logger.error("Exception in getRegionsZipCodes :"+e);
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}
		return StateReg;
	}// getRegionsZipCodes ends

	public SequenceMap getRegionsCounties(String AreaId ) {
		String sQuery	=  "";
		SequenceMap data = new SequenceMap();
		Info info = null;
		ResultSet rs=null;
		try{

			sQuery	= "SELECT C.NAME, R.REGION_NAME, GROUP_CONCAT(COU.COUNTY_NAME ORDER BY COU.COUNTY_NAME SEPARATOR ', ') AS COUNTY_NAME FROM COUNTY_AREAS CA LEFT JOIN COUNTRIES C ON C.COUNTRY_ID=CA.COUNTRY_ID LEFT JOIN REGIONS R ON R.REGION_NO=CA.STATE_ID  LEFT JOIN COUNTIES COU ON COU.COUNTY_NO = CA.COUNTY_ID WHERE CA.AREA_ID="+AreaId+" GROUP BY CA.STATE_ID, CA.COUNTRY_ID ORDER BY C.NAME";

			rs = QueryUtil.getResult(sQuery, new Object[]{});
			while(rs!=null && rs.next()){
				if(data.containsKey(rs.getString("NAME")))
				{
					info = (Info) data.get(rs.getString("NAME"));
					info.put(rs.getString("REGION_NAME"), rs.getString("COUNTY_NAME"));
				}else
				{
					info = new Info();
					info.put(rs.getString("REGION_NAME"), rs.getString("COUNTY_NAME"));
				}
				data.put(rs.getString("NAME"),info);
			}
		}
		catch(Exception e){
			logger.info("Excetpion in getRegionsCounties :"+e);	
			return null;
		}finally
		{
			QueryUtil.releaseResultSet(rs);
		}

		return data;
	}

//This method is used in case of modifying franchisee
	public void addFranchiseToRegion(String regionId,String values[]){
		String franchiseeNo = null;
		try{
			StringBuffer query = new StringBuffer("UPDATE FRANCHISEE SET AREA_ID = ? ");
			query.append("  WHERE FRANCHISEE_NO = ? ");

			int result	= 0;
			for(int i=0;i<values.length;i++){

				String params[]	= {regionId,values[i]};
				result	= QueryUtil.update(query.toString(),params);

			}

		}
		catch(Exception e){
			logger.error("Exception in addFranchiseToRegion :"+e);
		}

	}// addFranchiseToRegion ends


//This method is for getting the franchisee not assigned to any region
	public SequenceMap getFranchiseeNotAssigned(){

		SequenceMap data = new SequenceMap();
		StringBuffer query = new StringBuffer();

   		query.append("SELECT DISTINCT F.FRANCHISEE_NO,");
		query.append(" A.AREA_NAME,F.FRANCHISEE_NAME,F.CITY, ");
		query.append(" F.STATE,F.ZIPCODE,F.COUNTRY,F.STATUS");
		//P_B_55181 starts
		query.append(" FROM AREAS A, FRANCHISEE F WHERE F.IS_ADMIN='N' AND F.STATUS NOT IN (0,4)");
		query.append(" AND ((F.IS_FRANCHISEE='Y')");
		if(ModuleUtil.franchiseModulesWithUser()){  //P_E_FC-175
			query.append("  OR (F.IS_STORE='Y' AND F.IS_STORE_ARCHIVED!='Y')");
		}
		query.append(") AND F.AREA_ID = A.AREA_ID AND IS_DELETED='N' AND ");
		//P_B_55181 neds
		query.append(" F.AREA_ID=").append("''").append(" ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME");
		ResultSet rs1 = null;
		try{
		 	
			rs1	= QueryUtil.getResult(query.toString(), null);
			String name = new String();
			while(rs1.next()) {
				Info info = new Info();
				String status = rs1.getString("STATUS");
				info.set("franNotAssigned" ,"<input type='checkbox' name='contactidFranNotAssigned' value=\""+rs1.getString("FRANCHISEE_NO")+"\" onClick='deSelectAllFranNotAssigned();'>");
				info.set(FieldNames.ZIPCODE,rs1.getString("ZIPCODE"));
				info.set(FieldNames.COUNTRY,rs1.getString("COUNTRY"));
				info.set(FieldNames.STATE,rs1.getString("STATE"));
				name = rs1.getString("FRANCHISEE_NAME");
				if(status.equals("3")){
					name = name+"<span class=\"urgent_fields\">(CL)</span>";
				}
				info.set(FieldNames.FRANCHISEE_NAME,name);
				info.set(FieldNames.CITY,rs1.getString("CITY"));
				info.set(FieldNames.AREA_NAME,rs1.getString("AREA_NAME"));

				data.put(rs1.getString("FRANCHISEE_NO") , info);
			}

		}
		catch(Exception e){
		  	logger.error("Exception in getFranchiseeNotAssigned :"+e);
		}finally
		{
			QueryUtil.releaseResultSet(rs1);
		}
		return data;
	}// getFranchiseeNotAssigned ends

//this method is for getting the list of franchisee assigned to  other area/region
	public SequenceMap getFranchiseeOfOtherArea(String stateId,String regionid,String regionType){

		SequenceMap data = new SequenceMap();
		/*StringBuffer query = new StringBuffer();

		query.append("SELECT DISTINCT F.FRANCHISEE_NO,");
		query.append(" A.AREA_NAME,F.FRANCHISEE_NAME,F.CITY, ");
		query.append(" F.STATE,F.ZIPCODE,F.COUNTRY,F.STATUS");
		//P_B_55181
		query.append(" FROM AREAS A, FRANCHISEE F WHERE F.IS_ADMIN='N' AND F.STATUS NOT IN (0,4)");
		query.append(" AND F.AREA_ID = A.AREA_ID AND ((F.IS_FRANCHISEE='Y') ");
		if(ModuleUtil.franchiseModulesWithUser()){    //P_E_FC-175
			query.append(" OR (F.IS_STORE='Y' AND F.IS_STORE_ARCHIVED!='Y')");
		}
		query.append(" ) AND   ");
		//P_B_55181 ends
		//P_E_ADMIN_1000 By Nikhil Verma
		if(regionType.equals("3")){
			query.append(" F.COUNTRY_ID NOT IN (").append(stateId).append(") AND ");
		}else if(regionType.equals("1") || regionType.equals("3")){
			query.append(" F.REGION_NO NOT IN (").append(stateId).append(") AND ");
		}else{
			query.append(" F.ZIPCODE NOT IN (").append(stateId).append(") AND ");
		}
		query.append(" F.AREA_ID <> ").append(regionid).append(" ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME");
		ResultSet rs1 = null;
		 try{
		 	
			rs1	= QueryUtil.getResult(query.toString(), null);
			String name = new String();
			while(rs1.next()) {
				Info info = new Info();
				info.set("contactid" ,"<input type='checkbox' name='contactidFranOther' value=\""+rs1.getString("FRANCHISEE_NO")+"\" onClick='deSelectAll();'>");
				String status = rs1.getString("STATUS");
				info.set(FieldNames.ZIPCODE,rs1.getString("ZIPCODE"));
				info.set(FieldNames.COUNTRY,rs1.getString("COUNTRY"));
				info.set(FieldNames.STATE,rs1.getString("STATE"));
				name = NewPortalUtils.getFranchiseIdDisplayName(rs1.getString("FRANCHISEE_NO"), rs1.getString("FRANCHISEE_NAME"));
				if(status.equals("3")){
					name = name+"<span class=\"urgent_fields\">(CL)</span>";
				}
				info.set(FieldNames.FRANCHISEE_NAME,name);
				info.set(FieldNames.CITY,rs1.getString("CITY"));
				info.set(FieldNames.AREA_NAME,rs1.getString("AREA_NAME"));

				data.put(rs1.getString("FRANCHISEE_NO") , info);
			}
		}
		catch(Exception e){
		  	logger.error("Exception in getFranchiseeOfOtherArea :"+e);
		}finally
		{
			QueryUtil.releaseResultSet(rs1);
		}
*/
		return data;
	}// getFranchiseeOfOtherArea ends

	public boolean getRegionFranchisesBoolean(String areaId) {
		
		StringBuffer sQuery = new StringBuffer();

		try{

			sQuery = new StringBuffer("SELECT F.FRANCHISEE_NO FROM FRANCHISEE F WHERE STATUS <> '0' AND F.AREA_ID = ").append(areaId).append(" AND IS_ADMIN='N' UNION SELECT A.USER_NO FROM AREA_USERS A, USERS U, FRANCHISEE F WHERE U.USER_NO=A.USER_NO AND U.FRANCHISEE_NO=F.FRANCHISEE_NO AND F.IS_ADMIN='R' AND F.STATUS='1' AND U.STATUS='1' AND A.AREA_ID=").append(areaId);
			ResultSet rs = QueryUtil.getResult(sQuery.toString(), null);
			if(rs.next()){
				return true;
				
			} else {			
				return false;
			}
		}
		catch(Exception e){
		  	logger.error("Exception in getRegionFranchisesBoolean :"+e);
			return false;
		}
	}// getRegionFranchisesBoolean ends


 /**
	* This function returns the list of franchises assigned to a particular region.
	* Called from selectFranchiseForRegion.jsp
	*/
	public SequenceMap getRegionFranchisesNotMapped(String regionId,String regionType)  {

		SequenceMap sMap	= null;
		try{
			String 		dataListQuery 		= 	null;
			String 		queryFranNotMapped 	= 	null;
			String 		dataIdList 			= 	"";

			if(regionType.equals("1")) {
				dataListQuery = "SELECT GROUP_CONCAT(STATE_ID) ID FROM STATE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else if(regionType.equals("2")) {
				dataListQuery = "SELECT GROUP_CONCAT(ZIPCODE) ID FROM ZIPCODE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else {
				dataListQuery = "SELECT GROUP_CONCAT(COUNTRY_ID) ID FROM INTERNATIONAL_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			}

			String[]  params		= {regionId};
			ResultSet rs 			= QueryUtil.getResult(dataListQuery, params);

			if(rs.next()){
				dataIdList = rs.getString("ID");
			}
			
			if(regionType.equals("1")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND REGION_NO IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else if(regionType.equals("2")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND ZIPCODE IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND COUNTRY_ID IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			}
			ResultSet notMappedRs = QueryUtil.getResult(queryFranNotMapped, new Object[]{});
			Info info	= null;
			sMap		= new SequenceMap();
			while(notMappedRs.next()){
				info	= new Info();
				info.set(FieldNames.FRANCHISEE_NO ,"<input type='checkbox' name='franNotMapped' value=\""+notMappedRs.getString("FRANCHISEE_NO")+"\" onClick='deSelectfranNotMappedMap();'>");									
				info.set(FieldNames.FRANCHISEE_NAME,notMappedRs.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.CITY,notMappedRs.getString("CITY"));
				info.set(FieldNames.STATE,notMappedRs.getString("STATE"));
				info.set(FieldNames.COUNTRY,notMappedRs.getString("COUNTRY"));
				info.set(FieldNames.ZIPCODE,notMappedRs.getString("ZIPCODE"));
				info.set(FieldNames.STORE_PHONE,notMappedRs.getString("STORE_PHONE"));
				info.set(FieldNames.STATUS,notMappedRs.getString("STATUS"));
				sMap.put(notMappedRs.getString("FRANCHISEE_NO"),info);
			}
			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getRegionFranchisesNotMapped :"+e);
			return null;
		}

	}// getRegionFranchisesNotMapped ends

	 /**
	* This function returns the list of franchises assigned to a particular region.
	* Called from selectFranchiseForRegion.jsp
	*/
	public SequenceMap getRegionFranchisesMappedToOther(String regionId,String regionType)  {

		SequenceMap sMap	= null;

		try{
			String dataListQuery 		= null;
			String queryFranNotMapped 	= null;
			String dataIdList			= "";
			if(regionType.equals("1")) {
				dataListQuery = "SELECT GROUP_CONCAT(STATE_ID) ID FROM STATE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else if(regionType.equals("2")) {
				dataListQuery = "SELECT GROUP_CONCAT(ZIPCODE) ID FROM ZIPCODE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else {
				dataListQuery = "SELECT GROUP_CONCAT(COUNTRY_ID) ID FROM INTERNATIONAL_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			}

			String[] params		= {regionId};
			ResultSet rs = QueryUtil.getResult(dataListQuery, params);
			if(rs.next()){
				dataIdList = rs.getString("ID");
			}
			if(regionType.equals("1")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND REGION_NO IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else if(regionType.equals("2")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND ZIPCODE IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND COUNTRY_ID IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			}

			ResultSet notMappedRs = QueryUtil.getResult(queryFranNotMapped, new Object[]{});
			Info info	= null;
			sMap		= new SequenceMap();
			while(notMappedRs.next()){
				info	= new Info();
				info.set(FieldNames.FRANCHISEE_NO ,"<input type='checkbox' name='franMappedToOther' value=\""+notMappedRs.getString("FRANCHISEE_NO")+"\" onClick='deSelectfranMappedToOtherMap();'>");
				info.set(FieldNames.FRANCHISEE_NAME,notMappedRs.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.CITY,notMappedRs.getString("CITY"));
				info.set(FieldNames.STATE,notMappedRs.getString("STATE"));
				info.set(FieldNames.COUNTRY,notMappedRs.getString("COUNTRY"));
				info.set(FieldNames.ZIPCODE,notMappedRs.getString("ZIPCODE"));
				info.set(FieldNames.STORE_PHONE,notMappedRs.getString("STORE_PHONE"));
				info.set(FieldNames.STATUS,notMappedRs.getString("STATUS"));
				info.set(FieldNames.AREA_NAME,notMappedRs.getString("AREA_NAME"));
				sMap.put(notMappedRs.getString("FRANCHISEE_NO"),info);
			}
			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getRegionFranchisesMappedToOther :"+e);
			return null;
		}

	}// getRegionFranchisesMappedToOther ends

	 /**
	* This function returns the list of franchises assigned to a particular region.
	* Called from selectFranchiseForRegion.jsp
	*/
	public SequenceMap getRegionFranchisesNotMappedOutsideArea(String regionId,String regionType)  {

		SequenceMap sMap	= null;

		try{
			String dataListQuery = null;
			String queryFranNotMapped = null;
			String dataIdList = "";
			if(regionType.equals("1")) {
    				dataListQuery = "SELECT GROUP_CONCAT(STATE_ID) ID FROM STATE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";

			} else if(regionType.equals("2")) {
    				dataListQuery = "SELECT GROUP_CONCAT(ZIPCODE) ID FROM ZIPCODE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else {
    				dataListQuery = "SELECT GROUP_CONCAT(COUNTRY_ID) ID FROM INTERNATIONAL_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			}

			String[] params		= {regionId};
			ResultSet rs = QueryUtil.getResult(dataListQuery, params);
			if(rs.next()){
				dataIdList = rs.getString("ID");
			}

			if(regionType.equals("1")) {
    				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND REGION_NO NOT IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else if(regionType.equals("2")) {
    				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND ZIPCODE NOT IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else {
    				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE FROM FRANCHISEE F,OWNERS O LEFT JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND COUNTRY_ID NOT IN ("+dataIdList+") AND A.AREA_ID IS NULL  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			}

			ResultSet notMappedRs = QueryUtil.getResult(queryFranNotMapped, new Object[]{});
			Info info	= null;
			sMap		= new SequenceMap();
			while(notMappedRs.next()){
				info	= new Info();
				info.set(FieldNames.FRANCHISEE_NO ,"<input type='checkbox' name='franNotMappedOutside' value=\""+notMappedRs.getString("FRANCHISEE_NO")+"\" onClick='deSelectfranNotMappedOutsideMap();'>");
				info.set(FieldNames.FRANCHISEE_NAME,notMappedRs.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.CITY,notMappedRs.getString("CITY"));
				info.set(FieldNames.STATE,notMappedRs.getString("STATE"));
				info.set(FieldNames.COUNTRY,notMappedRs.getString("COUNTRY"));
				info.set(FieldNames.ZIPCODE,notMappedRs.getString("ZIPCODE"));
				info.set(FieldNames.STORE_PHONE,notMappedRs.getString("STORE_PHONE"));
				info.set(FieldNames.STATUS,notMappedRs.getString("STATUS"));
				sMap.put(notMappedRs.getString("FRANCHISEE_NO"),info);
			}
			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getRegionFranchisesNotMappedOutsideArea :"+e);
			return null;
		}

	}// getRegionFranchisesNotMappedOutsideArea ends

	 /**
	* This function returns the list of franchises assigned to a particular region.
	* Called from selectFranchiseForRegion.jsp
	*/
	public SequenceMap getRegionFranchisesMappedToOtherOutsideArea(String regionId,String regionType)  {

		SequenceMap sMap	= null;

		try{
			String dataListQuery = null;
			String queryFranNotMapped = null;
			String dataIdList = "";
			if(regionType.equals("1")) {
				dataListQuery = "SELECT GROUP_CONCAT(STATE_ID) ID FROM STATE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else if(regionType.equals("2")) {
				dataListQuery = "SELECT GROUP_CONCAT(ZIPCODE) ID FROM ZIPCODE_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			} else {
				dataListQuery = "SELECT GROUP_CONCAT(COUNTRY_ID) ID FROM INTERNATIONAL_AREAS WHERE AREA_ID=? GROUP BY AREA_ID";
			}

			String[] params		= {regionId};
			ResultSet rs = QueryUtil.getResult(dataListQuery, params);
			if(rs.next()){
				dataIdList = rs.getString("ID");
			}
			if(regionType.equals("1")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND REGION_NO NOT IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else if(regionType.equals("2")) {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND ZIPCODE NOT IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			} else {
				queryFranNotMapped = "SELECT DISTINCT F.FRANCHISEE_NO,F.FRANCHISEE_NAME,F.CITY,F.STATE,F.COUNTRY,F.STATUS,F.ZIPCODE,F.STORE_PHONE,A.AREA_NAME FROM FRANCHISEE F,OWNERS O, AREAS A WHERE F.FRANCHISEE_NO=O.FRANCHISEE_NO AND F.AREA_ID=A.AREA_ID  AND F.IS_ADMIN='N' AND F.IS_FRANCHISEE='Y' AND  F.STATUS NOT IN (0,4) AND COUNTRY_ID NOT IN ("+dataIdList+")  ORDER BY COUNTRY,STATE,CITY,FRANCHISEE_NAME";
			}


			ResultSet notMappedRs = QueryUtil.getResult(queryFranNotMapped, new Object[]{});
			Info info	= null;
			sMap		= new SequenceMap();
			while(notMappedRs.next()){
				info	= new Info();
				info.set(FieldNames.FRANCHISEE_NO ,"<input type='checkbox' name='franMappedOtherOutside' value=\""+notMappedRs.getString("FRANCHISEE_NO")+"\" onClick='deSelectfranMappedOtherMap();'>");
				info.set(FieldNames.FRANCHISEE_NAME,notMappedRs.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.CITY,notMappedRs.getString("CITY"));
				info.set(FieldNames.STATE,notMappedRs.getString("STATE"));
				info.set(FieldNames.COUNTRY,notMappedRs.getString("COUNTRY"));
				info.set(FieldNames.ZIPCODE,notMappedRs.getString("ZIPCODE"));
				info.set(FieldNames.STORE_PHONE,notMappedRs.getString("STORE_PHONE"));
				info.set(FieldNames.STATUS,notMappedRs.getString("STATUS"));
				info.set(FieldNames.AREA_NAME,notMappedRs.getString("AREA_NAME"));
				sMap.put(notMappedRs.getString("FRANCHISEE_NO"),info);
			}
			return sMap;
		}
		catch(Exception e){
			logger.error("Exception in getRegionFranchisesMappedToOtherOutsideArea :"+e);
			return null;
		}
	}// getRegionFranchisesMappedToOtherOutsideArea ends
	
	public ArrayList isWrongZipcode(String[] zip,String[] countries) {
		ArrayList wrongZips = new ArrayList(0);
		int wrong = 0;
		try{
			if(countries.length==1 && countries[0].equals("1")) {
				for(int i=0;i<zip.length;i++){
					wrong = 0;
					if(zip[i].length() != 5) {
						wrong = 1;
					} else {
						for(int j=0; j<zip[i].length(); j++)
						{
							boolean check = Character.isDigit(zip[i].charAt(j));
							if(!check)
							{
								wrong = 1;
								break;
							}
						}
					}// else ends
					if(wrong==1) {
						wrongZips.add(zip[i]);
					}
				}//for ends
			}//if ends
			return wrongZips;
		}
		catch(Exception e){
			logger.error("Exception in isWrongZipcode : " +e);
			return null;
		}
	}// zipExists ends
	
	/*---------------------------------------Moved From BaseNewPortalUtils----------------------------------------------*/
	  public int getFieldManagerAreaCount(String userNo) {
	        StringBuffer sbQuery = new StringBuffer();
	        int count = 0;
	        sbQuery.append(
	                "SELECT COUNT(*) COUNT FROM  AREAS A WHERE FIELD_MANAGER=").append(userNo);
	        ResultSet rs = QueryUtil.getResult(sbQuery.toString(), new String[]{});
	        while (rs.next()) {
	            count = Integer.parseInt(rs.getString("COUNT"));
	        }
	        return count;
	  }
	
	     /**
	     * Method return the comma separated String of Franchisee Names which fall within a given Area (or an areaId)
	     * @param parameter
	     * @param isRegionName
	     * @return String
	     */
	    public String getFranchiseeNamesForRegionName(String parameter,boolean isRegionName)
	    {
	    	
	    	StringBuilder query = new StringBuilder("SELECT GROUP_CONCAT(FRANCHISEE_NAME SEPARATOR ';') AS FRANCHISEE_NAME FROM FRANCHISEE F JOIN AREAS A ON F.AREA_ID=A.AREA_ID WHERE IS_FRANCHISEE='Y' AND F.STATUS IN (1,3) AND ");
	    	if(isRegionName)
	    	{
	    		query.append(" A.AREA_NAME='").append(parameter).append("'");
	    	}else{
	    		query.append(" A.AREA_ID=").append(parameter);
	    	}
	    	query.append(" ORDER BY  FRANCHISEE_NAME");

	    	
	        ResultSet result=null;
	        try{
	        	 result = QueryUtil.getResult(query.toString(), new Object[] {});
	        	 if(result.next()) {
	        		 return result.getString("FRANCHISEE_NAME");
	        	 }
	        }catch(Exception e)
	        {
	        	logger.info("Excption in method getFranchiseeNamesForRegionName()");
	        }
	        return FieldNames.EMPTY_STRING;
	    }
	    public String getStateID(String stateName)
	    {
	    	return getStateID(stateName,null);
	    }
	    public String getStateID(String stateName ,String countryID) {
	        String stateID = "";
	        ResultSet result=null;
	        try {
	            StringBuffer query = new StringBuffer();
	            query.append("SELECT REGION_NO FROM REGIONS WHERE REGION_NAME = ? ");
	            if(StringUtil.isValidNew(countryID))
	            {
	            	query.append(" AND COUNTRY_ID='"+countryID+"'");
	            }
	            if (stateName != null && stateName.trim().length() != 0
	                    && !stateName.equals("null") && !stateName.equals("-1")) {
	                 result = QueryUtil.getResult(query.toString(),
	                        new Object[]{stateName});
	                if (result.next()) {
	                    stateID = result.getString("REGION_NO");
	                }// end IF
	            }
	        } catch (Exception e) {
	            logger.info("ERROR: exception in Info getStateN0() ::" + e);
	            e.printStackTrace();
	        }
	        finally
			{
				QueryUtil.releaseResultSet(result);
			}

	        return stateID;
	    } 
	    /**
	     *
	     * @param areaId
	     * @return This method returns all state ids as a single string which
	     *         matches the given areaId
	     */
	    public String getStateIdByAreaID(String areaId) {
	        StringBuffer stateIdList = new StringBuffer();
	        stateIdList.append(" ");
	        String query = "SELECT AREA_ID,STATE_ID FROM STATE_AREAS WHERE AREA_ID='"
	                + areaId + "'";
	        ResultSet rs=null;
	        try {
	             rs = QueryUtil.getResult(query,null);
	            while (rs.next()) {
	                stateIdList.append("'");
	                stateIdList.append(rs.getString("STATE_ID"));
	                stateIdList.append("'");
	                stateIdList.append(",");
	            }
	        } catch (RuntimeException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finally
			{
				QueryUtil.releaseResultSet(rs);
			}

	        return stateIdList.toString().substring(0,
	                stateIdList.toString().length() - 1);
	    }
	    
	    
	    /*
	     * This method returns the Name of the State based on the given STATE_ID ~~
	     * Amrit on 22-May-06 (OTRS #2006051910000051)
	     */
	    public String getStateName(String stateID) {
	    	//Map<String,String> state = CacheMgr.getStateCache().getState(stateID);
	    	Map<String,String> state = null;
	    	String statename = FieldNames.EMPTY_STRING;
	    	if (state != null)	
	    		statename = state.get(FieldNames.STATE_NAME);
	    	return statename;
	    }
	    
	    public String getUserAreaId(String userNo) {
	    	return SQLUtil.getQueryResult("SELECT AREA_ID FROM AREA_USERS WHERE USER_NO="+userNo, "AREA_ID");
	    }

  /*---------------------------------------Moved From BasePortalUtils----------------------------------------------*/
		   
		/**
    	 * This will return all active areas
    	 * @author abhishek gupta
    	 * @date 18 jun 2009
    	 * @param areaId
    	 * @return
    	 */
    	public Info getAllAreaInfoForSupplier() {
    		return getAllAreaInfoForSupplier(null);
    	}
    	public Info getAllAreaInfoForSupplier(String areaId) {

    		StringBuffer query = new StringBuffer(
    				"SELECT DISTINCT(AREA_NAME), AREA_ID FROM AREAS WHERE IS_DELETED='N' ");
    		if (areaId != null)
    			query.append("AND AREA_ID = ").append(areaId).append(" ");
    		query.append("ORDER BY AREA_NAME ");
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
    				.getResult(query.toString(), new Object[] {});
    		Info map = new Info();
    		while (result.next())
    			map.set(result.getString("AREA_ID"), result.getString("AREA_NAME"));
    		return map;

    	}
    	
    	public SequenceMap getAllAreaMapForFinPL(String areaId) {

    		StringBuffer query = new StringBuffer(
    				"SELECT AREA_NAME,AREA_ID FROM AREAS where IS_DELETED='N'  ");
    		if (areaId != null)
    			query.append("AND F.AREA_ID = ").append(areaId).append(" ");
    		query.append("ORDER BY AREA_NAME ");
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
    				.getResult(query.toString(), new Object[] {});
    		SequenceMap map = new SequenceMap();
    		while (result.next())
    			map.put(result.getString("AREA_ID"), result.getString("AREA_NAME"));
    		QueryUtil.releaseResultSet(result);
    		return map;

    	}
	  
    	/**
    	 * Map for all area / region
    	 * @author abhishek gupta
    	 * @date 18 jun 2009
    	 * @return
    	 */
    	public SequenceMap getAllAreaMapSupp() {
    		return getAllAreaMapSupp(null);
    	}
    	public SequenceMap getAllAreaMapSupp(String areaId) {
    		StringBuffer query = new StringBuffer(
    				"SELECT DISTINCT(AREA_NAME), AREA_ID FROM AREAS WHERE 1=1 ");
    		
    		query.append(" AND IS_DELETED='N' ");
    		if (areaId != null)
    			query.append("AND AREA_ID = ").append(areaId).append(" ");
    		query.append("ORDER BY AREA_NAME ");

    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
    				.getResult(query.toString(), new Object[] {});
    		SequenceMap map = new SequenceMap();
    		while (result.next())
    			map.put(result.getString("AREA_ID"), result.getString("AREA_NAME"));
    		return map;
    	}
    	
    	public Info getAllAreas() {
    		return getAllAreas(null);
    	}


    	public Info getAllAreas(String areaIds) 
    	{
    		return getAllAreas(areaIds,false);
    	}
    	public Info getAllAreas(String areaIds,boolean showDeleted)
    	{
    		Info info = new Info();
    		StringBuffer qBuff = new StringBuffer("SELECT AREA_ID, AREA_NAME FROM AREAS A WHERE 1=1 ");
    		if(!showDeleted)
    		{
    			qBuff.append(" AND IS_DELETED ='N' ");
    		}
    		//BB_CT_REPORT_ARA
    		if(StringUtil.isValidNew(areaIds) && areaIds.startsWith(",")){
    			areaIds=areaIds.replaceFirst(",", "");
    		}
    		if (areaIds != null && !"".equals(areaIds) && !"-1".equals(areaIds)&& !"null".equals(areaIds))
    			qBuff.append("AND A.AREA_ID IN (").append(areaIds).append(") ");
    		qBuff.append("ORDER BY AREA_NAME ");
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil
    				.getResult(qBuff.toString(), new Object[] {});
    		while (result.next()) {
    			info
    					.set(result.getString("AREA_ID"), result
    							.getString("AREA_NAME"));
    		}
    		QueryUtil.releaseResultSet(result);
    		return info;
    	}
    	
    	/**
    	 * This will check is area / region exist in system 
    	 * @author abhishek gupta
    	 * @date 17 jun 2009
    	 * @return
    	 */
    	public boolean getAreaFranchiseeForAddAddress(){
    		StringBuffer query = new StringBuffer("SELECT AREA_ID FROM AREAS ");
    		
    		com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), null);
    		if(result.size() > 0) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    	    	  	
    	   public static String getCountryIDFromStateID(String stateID){
           	String query = " SELECT COUNTRY_ID FROM REGIONS WHERE REGION_NO = '"+stateID+"'";
           	String countryID = null;
           	com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(query,null);	
           	if(rs.next()){
           		countryID = rs.getString("COUNTRY_ID");
           	}
           	return countryID;
           }    	
    	   
    		public static String getStateAbbrevName(String stateId)
    	        	throws ConnectionException {
    	        			Connection con = null;
    	        			String stateName = null;
    	        			Statement stmt = null;
    	        			java.sql.ResultSet rs = null;
    	        			try 
    	        	       	{
    	        				 String query = "SELECT (CASE REGION_ABBREV WHEN NULL THEN REGION_NAME WHEN '' THEN REGION_NAME ELSE REGION_ABBREV END) AS STATE FROM REGIONS WHERE REGION_NO = '"+stateId+ "'";
    	        	   				com.home.builderforms.sqlqueries.ResultSet  resultSet = QueryUtil.getResult(query.toString(), null);
    	        	         	  	if(resultSet.next()) 
    	        	         	  	{
    	        	         	  		stateName=resultSet.getString("STATE");
    	        	         	  	}
    	        	       	} catch (Exception e) 
    	        	       	{
    	        	       		e.printStackTrace();
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
    	        	       	return stateName;
    	        		}
    		/**
    		 * This method display States of the respective country being selected.
    		 * 
    		 * @param countryID-String
    		 *            countryID as an input parameter
    		 * @return ArrayList containg returnList of states of the respective country
    		 *         being selected
    		 */

    		public static ArrayList getStateForCountry(String countryID) {
    			ArrayList returnList = new ArrayList();
    			String[] state = null;
    			Connection con = null;
    			PreparedStatement pstmt = null;
    			java.sql.ResultSet rs = null;
    			try {
    				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
    				pstmt = con
    						.prepareStatement("SELECT REGION_NO , REGION_NAME FROM REGIONS WHERE COUNTRY_ID =? ORDER BY REGION_NAME");
    				pstmt.setString(1, countryID);

    				rs = pstmt.executeQuery();

    				while (rs.next()) {
    					state = new String[2];
    					state[0] = rs.getString("REGION_NO");
    					state[1] = rs.getString("REGION_NAME");

    					returnList.add(state);
    				}

    			} catch (SQLException e) {
    				Debug.print(e);
    			} finally {
    			
    				SQLUtil.closeStmt(pstmt);
    				SQLUtil.closeResultSet(rs);
    				try {
    					if (con != null)
    						DBConnectionManager.getInstance().freeConnection(con);
    				} catch (Exception e) {
    					logger.error("Exception ", e);
    				}
    				return returnList;
    			}

    		}
    		
    		public static String getStateIdByName(String stateName)
    		 {

    				String stateId = "";
    				
    				
    				try {
    				
    				String query = "SELECT REGION_NO FROM REGIONS WHERE REGION_NAME IN('"+ stateName + "')";
    				com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query, new Object[] { });
    					
    				
    					while (result.next()) {
    						System.out.println("SELECT REGION_NO FROM REGIONS WHERE REGION_NAME IN("+ stateName + ")");
    						stateId =  result.getString("REGION_NO");
    					}
    					
    					
    				} catch (Exception e) {
    					 e.printStackTrace();
    				}
    				return stateId; 


    		 }
    		

    		public SequenceMap getIdAndValue()
    		{
    		    String query="SELECT REGION_NAME,REGION_NO FROM REGIONS WHERE COUNTRY_ID='1' ORDER BY REGION_NAME";
    		    ResultSet rs = QueryUtil.getResult(query,null);
    		    SequenceMap dataMap = new SequenceMap();
    		    try 
    		{
    			while (rs.next())
    			{
    				dataMap.put(rs.getString("REGION_NO"), rs.getString("REGION_NAME"));
    			}
    		} catch(Exception e)
    		{
    			logger.info("\nException in com/appnetix/app/components/fimmgr/manager/dao - FIMDAO.java --> getStoreType: " , e);
    		}
    		finally
    		{
    			QueryUtil.releaseResultSet(rs);
    		}
    		return dataMap;
    		}



    		public String getRegionId(String zip, String regionName, String countryId,boolean uszipcheck){


    			//String baseAdminId = null;
    			ResultSet result = null;
    			try {

    				//Added by Rajeev 2/21/2006 - to pick only first five digts of Zip Code for comparision 		
    				// Demo_FDD_B_50365 chnage starts
    				if(uszipcheck && zip!= null && zip.trim().length()>4){
    					zip = zip.trim();   						
    				}

    				/* Rule 1, find the ared id for this zipcode */
    				String query=null;   
    				StringBuffer sbQuery = new StringBuffer(" SELECT FA.AREA_ID FROM FS_ZIPCODE_AREAS ZA  ,FS_AREAS FA , FS_ZIPCODE_AREA_COUNTRY ZAC  WHERE ZIPCODE = '");
    				sbQuery.append(zip).append("'  AND FA.AREA_ID=ZA.AREA_ID ");
    				sbQuery.append("AND ZA.AREA_ID=ZAC.AREA_ID AND ZAC.COUNTRY_ID= ").append(countryId);
    				result = QueryUtil.getResult(sbQuery.toString(),null);
    				if (result!=null && result.next()) {	// Mapping exists for this area
    					return "" + result.getInteger(1);
    				}else{
    					/* Rule 2, find the area id for the state */
    					query = "SELECT FA.AREA_ID FROM FS_STATE_AREAS SA ,FS_AREAS FA WHERE STATE_ID = '"+regionName+"' AND FA.AREA_ID=SA.AREA_ID ";

    					result = QueryUtil.getResult(query,null);
    					if (result!=null && result.next()) {	// Mapping exists for this area
    						return "" + result.getInteger(1);
    					}else{
    						/* Query the DB for the default owner	*/
    						return "-1";
    					}
    				}



    			}
    			catch (Exception e){
    				logger.error(e);
    			}
    			finally{
    				try{
    					if(result != null){
    						result.release();
    						result = null;
    					}
    				}catch(Exception e){
    					logger.error(e);
    				}
    			}
    			return "-1";

    		}

    		public HashMap<String,String> getStateMap()
    		{
    			return getStateMap(null);
    		}

    		public HashMap<String,String> getStateMap(String countryID)
    		{
    			HashMap<String,String> stateMap = new HashMap<String,String>();
    			ResultSet rs = null;
    			try {
    				String query = "SELECT LCASE(REGION_NAME), LCASE(REGION_ABBREV), REGION_NO FROM REGIONS";
    				if (countryID != null) {
    					query += " WHERE COUNTRY_ID='" + countryID + "'";
    				}
    				rs = QueryUtil.getResult(query, null);

    				String regionNo = null;
    				while (rs.next()) {
    					regionNo = rs.getString(3);
    					stateMap.put(rs.getString(1).trim(), regionNo);
    					stateMap.put(rs.getString(2).trim(), regionNo);
    				}
    			} catch (Exception e) {
    				logger.error("Exception in getStateMap" , e);
    			} finally {
    				if(rs!=null) {
    					rs.release();
    					rs = null;
    				}
    			}
    			return stateMap;
    		}

    		/* Method to retrieve all regions, added by sandeep on 09 Jan 2006 */
    		public SequenceMap getValueForRegion(String regionID) {
    		    SequenceMap dataMap = null;
    		    
    		    String query = new String("SELECT A.AREA_ID, A.AREA_NAME FROM AREAS A WHERE IS_DELETED='N' ");
    			if (regionID!=null && !regionID.equals("-1") && !regionID.equals("") && !regionID.equals("null"))
    			{
    				query = query + " AND AREA_ID="+regionID;
    			}
    			
    			query = query + " ORDER BY AREA_NAME";
    		    ResultSet result=null;
    		    try{
    		         result = QueryUtil.getResult(query,null);
    		        dataMap = new SequenceMap();
    		        while (result.next())
    		            dataMap.put(result.getString("AREA_ID"), result.getString("AREA_NAME"));
    		        
    		    }catch(Exception e){
    		        logger.error("Exception in com/appnetix/app/components/fimmgr/manager/dao - FIMDAO.java --> getValueForRegion: "+e);
    		    }
    		    finally
    			{
    				QueryUtil.releaseResultSet(result);
    			}
    		    return dataMap;
    		}

    		/**
    		 * @author Nikhil Verma
    		 * @param franchiseeNo
    		 * @param areaID
    		 */
    		public void updateAreaId(String franchiseeNo, String areaID) {
    			try {
    				
    				String query = "UPDATE CM_CONTACT_DETAILS SET AREA_ID=" + areaID + " WHERE FRANCHISEE_NO IN(" + franchiseeNo+")";//CaringTransitions-20150210-722
    				QueryUtil.update(query, new String[]{});
    				
    				query = "UPDATE CM_CONTACT_DETAILS_ARCHIVE SET AREA_ID=" + areaID + " WHERE FRANCHISEE_NO IN(" + franchiseeNo+")";//CaringTransitions-20150210-722
    				QueryUtil.update(query, new String[]{});
    				
    				if(true){
    					query = "UPDATE CM_LEAD_DETAILS SET AREA_ID=" + areaID + " WHERE FRANCHISEE_NO IN(" + franchiseeNo+")";//CaringTransitions-20150210-722
    					QueryUtil.update(query, new String[]{});
    					
    					query = "UPDATE CM_LEAD_DETAILS_ARCHIVE SET AREA_ID=" + areaID + " WHERE FRANCHISEE_NO IN(" + franchiseeNo+")";//CaringTransitions-20150210-722
    					QueryUtil.update(query, new String[]{});
    				}
    				
    			} catch (Exception e) {
    				logger.error("ERROR: exception in updateAreaId ::" + e);
    			}
    		}
    		
    		  //setter method for method getAreaInfo()
    	    public SequenceMap getAreaInfo(String areaId){
    	        return (SequenceMap) getAreaInfo(areaId,"MAP");
    	    }
    	    //this method is used to retreive the AREA_NAME and AREA_ID
    	    public Object getAreaInfo(String areaId, String retType){
    	        
    	        StringBuffer query = new StringBuffer("SELECT A.AREA_ID, A.AREA_NAME FROM AREAS A ");
    	        if (areaId != null && !"-1".equals(areaId)) {
    	            query.append("WHERE A.IS_DELETED='N' AND A.AREA_ID = '").append(areaId).append("' ");
    			} else {
    				query.append("WHERE A.IS_DELETED='N' ");
    			}
    			query.append("ORDER BY AREA_NAME");
    	        
    	        logger.info("getAreaInfo:"+query.toString());

    	        if (retType.equals("MAP")){
    	        	return SQLUtil.getSequenceMapFromQuery("AREA_ID", "AREA_NAME", query.toString());
    	        }else{
    	        	return SQLUtil.getInfoFromQuery("AREA_ID", "AREA_NAME", query.toString());
    	        }
    	    }
    		
    	    /**
    		 * This method will give the information of Area of a particular Franchisee.
    		 * 
    		 * @param franchiseID
    		 *            -String Franchisee Id of Franchisee as an Input Parameter.
    		 * @return areaName -String that contain Area names of particular
    		 *         Franchisee.
    		 */
    		public Info getFranchiseAndAreaDetailInfo(String franchiseID) {

    			Connection con = null;
    			Info areaName = new Info();
    			String query = "SELECT A.AREA_NAME,A.AREA_ID,F.FRANCHISEE_NAME,F.STATUS FROM AREAS A INNER JOIN FRANCHISEE F ON A.AREA_ID=F.AREA_ID WHERE F.FRANCHISEE_NO="
    					+ franchiseID;
    			Statement stmt = null;
    			java.sql.ResultSet result = null;

   				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);

    			try {
    				stmt = con.createStatement();
    				result = stmt.executeQuery(query.toString());
    				while (result.next()) {
    					areaName.set(FieldNames.AREA_NAME, result
    							.getString("AREA_NAME"));
    					areaName.set(FieldNames.AREA_ID, result.getString("AREA_ID"));
    					areaName.set(FieldNames.FRANCHISEE_NAME, result
    							.getString("FRANCHISEE_NAME"));
    					areaName.set(FieldNames.STATUS, result.getString("STATUS"));
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

    			return areaName;
    		}
    		/**
    		 * P_ENH_MULTISELECT_FILTERS_FO
    		 * @param stateID
    		 * @return  comma seperated String of state names.
    		 */
    		public String getMultipleStateName(String stateID) {
    			
    			String stateName = FieldNames.EMPTY_STRING;

    			String[] stateList=stateID.split(",");
    			Map<String,String> stateMap = null;
    			for(int i=0;i<stateList.length;i++)
    			{
    				//stateMap = CacheMgr.getStateCache().getState(stateList[i]);
    				if (stateMap != null){	
    					stateName +="'"+ StringUtil.byPassQuotesInQuery(stateMap.get(FieldNames.STATE_NAME))+"',";
    				}
    			}
    			if(stateName.indexOf(",")!= -1){
    				stateName=stateName.substring(0,stateName.lastIndexOf(","));
    			}  
    			return stateName;
    		}   		
    		
}// RegionDAO ends

