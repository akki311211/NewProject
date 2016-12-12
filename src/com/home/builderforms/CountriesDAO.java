/*
-------------------------------------------------------------------------------------------------------------------------------
Version No.			   Date			By				Against		Function Changed         		 Comments
--------------------------------------------------------------------------------------------------------------------------------
P_Region_B_36256	27/06/2008	  Nikhil Verma		Bug       getInternationalCountries(),getDomesticCountries()
P_ADMIN_E_1111  30/09/2008       Nikhil Verma   International country enhancement. 			updateCountries(),getAllCountriesMap()       International country added.
P_ADMIN_E_1112   24/07/2009      Nikhil Verma					getAllCountriesMap()        1)We are unable to  inactive country while area is deleted.2)Area Region added by Zip code and  it's associated Country should not be inactive.
P_ADMIN_E_1113   10/03/2010      Nikhil Verma   Enhancement   getAllCountriesMap()         Performance enhancement.
* BBEH_FOR_GETRESULT_METHOD  23/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
--------------------------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.RegionMgr;
import com.home.builderforms.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class CountriesDAO extends BaseDAO {
	static	Logger logger					= Logger.getLogger(CountriesDAO.class);
	/*
	  Constructor for the CountriesDAO object
	*/
	public CountriesDAO() {
		this.tableAnchor = "countries";

	}
	/*
	Function to get COUNTRIES Names that are flagged
	*/	
	public Info getAllCountries(){
	return getAllCountries(0);
	}

 // This function returns the all countries that have not been assigned to any region yet.
	public SequenceMap getInternationalCountries(String regionId) {

		SequenceMap countriesMap = null;
		String allCountries = "";
		String country = "";
		ResultSet result=null;
		try{
			StringBuffer sbQuery = new StringBuffer("SELECT COUNTRY_ID FROM INTERNATIONAL_AREAS IA,AREAS A WHERE IA.AREA_ID=A.AREA_ID AND A.IS_DELETED='N'");
			
			 result		= QueryUtil.getResult(sbQuery.toString(), null);
			if(result.next()){
				allCountries = result.getString(1);
			}
			while(result.next()){
				country = result.getString(1);
				allCountries = allCountries + "," + country;
			}
			sbQuery = new StringBuffer("SELECT DISTINCT C.COUNTRY_ID,C.NAME FROM COUNTRIES C");
			if(!regionId.equals("-1")){
				sbQuery.append(", INTERNATIONAL_AREAS S");
			}
			if(regionId.equals("-1") && (allCountries!=null && allCountries.length()!=0)){
				sbQuery.append(" WHERE C.COUNTRY_ID NOT IN (");
				sbQuery.append(allCountries);
				//P_Region_B_36256
				sbQuery.append(") AND C.SHOW_COUNTRY=1 AND C.NAME NOT IN('USA','Canada') ");
			}
			else if(allCountries!=null && allCountries.length()!=0){
				sbQuery.append(" WHERE  C.SHOW_COUNTRY=1 AND C.COUNTRY_ID NOT IN (");
				sbQuery.append(allCountries);
				//P_Region_B_36256
				sbQuery.append(") AND C.NAME NOT IN('USA','Canada')  OR C.COUNTRY_ID = S.COUNTRY_ID AND C.SHOW_COUNTRY=1 AND S.AREA_ID = ");
				sbQuery.append(regionId);
				//P_Region_B_36256
				sbQuery.append(" AND C.NAME NOT IN('USA','Canada')");
			}else{
				//P_Region_B_36256
				sbQuery.append(" WHERE C.SHOW_COUNTRY=1 AND C.NAME NOT IN('USA','Canada')  ");
			}
			sbQuery.append(" ORDER BY C.NAME");
			result		= QueryUtil.getResult(sbQuery.toString(), null);
			Info	info	= null;
			countriesMap = new SequenceMap();
			while(result.next()){
				info	= new Info();
				info.set(FieldNames.COUNTRY_ID,result.getString("COUNTRY_ID"));
				info.set(FieldNames.NAME,result.getString("NAME"));
				countriesMap.put(result.getString("COUNTRY_ID"),info);
			}
			return countriesMap;
		}
		catch(Exception e){
			logger.error("Exception in getting getAllCountries :"+e);
			return null;
		}	finally
		{
			QueryUtil.releaseResultSet(result);
		}

	}//getInternationalCountries ends	

	public Info getAllCountries(int gFlag){
			Info countriesAll = null;	
		ResultSet result=null;
			try{
			
			StringBuffer sbQuery = new StringBuffer("SELECT * FROM COUNTRIES ");
			sbQuery.append(gFlag==1 ? " WHERE SHOW_COUNTRY=1":"");
			sbQuery.append(" ORDER BY COUNTRY_ID");
			 result			= QueryUtil.getResult(sbQuery.toString(), null);
			sbQuery = null;
			countriesAll	 = new Info();
			if(result!=null){
				while(result.next()){					
					countriesAll.set(result.getString("COUNTRY_ID"),result.getString("NAME"));
				}//end while			
			}//end if
		}catch(Exception e){
			logger.error("Exception in getting All Countries returning Info"+e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}
		
		return countriesAll;
	}//getAllCountries ends


	/*
	Function to get All COUNTRIES  Names in SequenceMap
	*/	
	public SequenceMap getAllCountriesMap(){
		SequenceMap countriesAll = null;	
		ResultSet result=null;
		try{
			//StringBuffer sbQuery = new StringBuffer("SELECT * FROM COUNTRIES ORDER BY NAME");
                       // StringBuffer sbQuery = new StringBuffer(" SELECT NAME, C.COUNTRY_ID, SHOW_COUNTRY, IFNULL(SA.AREA_ID, '') AS AREA_ID FROM COUNTRIES C LEFT JOIN STATE_AREAS SA ON C.COUNTRY_ID = SA.COUNTRY_ID GROUP BY C.COUNTRY_ID ORDER BY NAME");
			//P_ADMIN_E_1111 By Nikhil Verma
			//P_ADMIN_E_1112 By Nikhil Verma
			String query="SELECT AREA_ID,IS_DELETED FROM AREAS";
			 result			= QueryUtil.getResult(query,null);
			Info checkDeletedInfo=new Info();
			while(result.next())
				checkDeletedInfo.set(result.getString("AREA_ID"),result.getString("IS_DELETED"));
			//P_ADMIN_E_1113 By Nikhil Verma
			query="SELECT COUNTRY_ID,AREA_ID FROM  STATE_AREAS  GROUP BY COUNTRY_ID ";
			result			= QueryUtil.getResult(query,null);
			Info countryStateAreasInfo=new Info();
			while(result.next())
				countryStateAreasInfo.set(result.getString("COUNTRY_ID"),result.getString("AREA_ID"));
				
			query="SELECT COUNTRY_ID,AREA_ID FROM  INTERNATIONAL_AREAS  GROUP BY COUNTRY_ID  ";
			result			= QueryUtil.getResult(query,null);
			Info countryInternationalAreasInfo=new Info();
			while(result.next())
				countryInternationalAreasInfo.set(result.getString("COUNTRY_ID"),result.getString("AREA_ID"));
			
			query="SELECT COUNTRY_ID,AREA_ID FROM  ZIPCODE_AREA_COUNTRY  GROUP BY COUNTRY_ID";
			result			= QueryUtil.getResult(query,null);
			Info countryZipCodeAreaInfo=new Info();
			while(result.next())
				countryZipCodeAreaInfo.set(result.getString("COUNTRY_ID"),result.getString("AREA_ID"));
			
			query="SELECT COUNTRY_ID,FRANCHISEE_NO FROM FRANCHISEE GROUP BY COUNTRY_ID";
			result			= QueryUtil.getResult(query,null);
			Info countryFranchiseeInfo=new Info();
			while(result.next())
				countryFranchiseeInfo.set(result.getString("COUNTRY_ID"),result.getString("FRANCHISEE_NO"));
			
			//P_ADMIN_E_1112 By Nikhil Verma
			//StringBuffer sbQuery = new StringBuffer(" SELECT C.IS_DOMESTIC,NAME, C.COUNTRY_ID, SHOW_COUNTRY, IFNULL(SA.AREA_ID, '') AS AREA_ID,IFNULL(IA.AREA_ID, '') AS AREA_ID1,IFNULL(ZAC.AREA_ID, '') AS AREA_ID2, IFNULL(F.FRANCHISEE_NO, '') AS FRANCHISEE_NO,IFNULL(FU.FRANCHISEE_USER_NO, '') AS FRANCHISEE_USER_NO  FROM COUNTRIES C LEFT JOIN STATE_AREAS SA ON C.COUNTRY_ID = SA.COUNTRY_ID LEFT JOIN INTERNATIONAL_AREAS IA ON C.COUNTRY_ID = IA.COUNTRY_ID LEFT JOIN FRANCHISEE F ON F.COUNTRY = C.NAME AND F.STATUS IN(1,3) LEFT JOIN FRANCHISEE_USERS FU ON FU.COUNTRY = C.NAME AND FU.STATUS='1' LEFT JOIN ZIPCODE_AREA_COUNTRY ZAC ON ZAC.COUNTRY_ID=C.COUNTRY_ID GROUP BY C.COUNTRY_ID ORDER BY NAME ");
			StringBuffer sbQuery = new StringBuffer(" SELECT C.IS_DOMESTIC,NAME, C.COUNTRY_ID, SHOW_COUNTRY, IFNULL(FU.FRANCHISEE_USER_NO, '') AS FRANCHISEE_USER_NO  FROM COUNTRIES C   LEFT JOIN FRANCHISEE_USERS FU ON FU.COUNTRY = C.NAME AND FU.STATUS='1'  GROUP BY C.COUNTRY_ID ORDER BY NAME ");
			result			= QueryUtil.getResult(sbQuery.toString(), null);
			sbQuery = null;
			Info info	 = null;
                        StringBuffer associtedActiveCountries = new StringBuffer(",");
                        StringBuffer franchiseeassocitedActiveCountries = new StringBuffer(",");
			if(result!=null){
                                String areaid = null;
                                String areaid1 = null;
                                String areaid2 = null;
                                String franchiseeno = null;
                                String franchiseeUserno = null;
				countriesAll = new SequenceMap();
				while(result.next()){					
					info	 = new Info();
					info.set(FieldNames.COUNTRY_ID,result.getString("COUNTRY_ID"));
					info.set(FieldNames.NAME,result.getString("NAME"));
					info.set(FieldNames.IS_DOMESTIC,result.getString("IS_DOMESTIC"));
					info.set(FieldNames.SHOW_COUNTRY,result.getString("SHOW_COUNTRY"));
					//P_ADMIN_E_1113 By Nikhil Verma
                    //areaid = result.getString("AREA_ID");
					areaid=countryStateAreasInfo.getString(result.getString("COUNTRY_ID"));
                    //areaid1 = result.getString("AREA_ID1");
					areaid1=countryInternationalAreasInfo.getString(result.getString("COUNTRY_ID"));
					
                    //areaid2 = result.getString("AREA_ID2");
					areaid2 = countryZipCodeAreaInfo.getString(result.getString("COUNTRY_ID"));
                    if(((areaid != null && !areaid.equals("")) || (areaid1 != null && !areaid1.equals("")) || (areaid2 != null && !areaid2.equals("")) ) && (result.getString("SHOW_COUNTRY").equals("1")))
                    	//P_ADMIN_E_1112 By Nikhil Verma
                    	if("N".equals(checkDeletedInfo.getString(areaid)) || "N".equals(checkDeletedInfo.getString(areaid2)))
                    		associtedActiveCountries.append(result.getString("COUNTRY_ID")).append(",");
                        //franchiseeno = result.getString("FRANCHISEE_NO");
                    	franchiseeno =countryFranchiseeInfo.getString(result.getString("COUNTRY_ID"));
                        franchiseeUserno = result.getString("FRANCHISEE_USER_NO");
                        if(((franchiseeno != null && !franchiseeno.equals("")) || (franchiseeUserno != null && !franchiseeUserno.equals(""))) && (result.getString("SHOW_COUNTRY").equals("1"))) {
                        	franchiseeassocitedActiveCountries.append(result.getString("COUNTRY_ID")).append(",");
						}
						countriesAll.put(result.getString("COUNTRY_ID"),info);
						info = null;
				        areaid = null;
				        areaid1 = null;
                        franchiseeno = null;
                        franchiseeUserno = null;
				}//end while			
				countriesAll.put("associtedActiveCountries", associtedActiveCountries.toString());
                countriesAll.put("franchiseeassocitedActiveCountries", franchiseeassocitedActiveCountries.toString());
			}//end if
			checkDeletedInfo=null;
		}catch(Exception e){
			logger.error("Exception in getAllCountriesMap :"+e);
			return null;
		}	finally
		{
			QueryUtil.releaseResultSet(result);
		}
	
		return countriesAll;
	}//updateCountries ends
	/*

	public SequenceMap getAllCountriesMap(){
		SequenceMap countriesAll = null;	
		try{
			//StringBuffer sbQuery = new StringBuffer("SELECT * FROM COUNTRIES ORDER BY NAME");
                       // StringBuffer sbQuery = new StringBuffer(" SELECT NAME, C.COUNTRY_ID, SHOW_COUNTRY, IFNULL(SA.AREA_ID, '') AS AREA_ID FROM COUNTRIES C LEFT JOIN STATE_AREAS SA ON C.COUNTRY_ID = SA.COUNTRY_ID GROUP BY C.COUNTRY_ID ORDER BY NAME");
			StringBuffer sbQuery = new StringBuffer(" SELECT NAME, C.COUNTRY_ID, SHOW_COUNTRY, IFNULL(SA.AREA_ID, '') AS AREA_ID, IFNULL(F.FRANCHISEE_NO, '') AS FRANCHISEE_NO  FROM COUNTRIES C LEFT JOIN STATE_AREAS SA ON C.COUNTRY_ID = SA.COUNTRY_ID LEFT JOIN FRANCHISEE F ON F.COUNTRY = C.NAME GROUP BY C.COUNTRY_ID ORDER BY NAME");
			ResultSet result			= QueryUtil.getResult(sbQuery.toString(), null);
			sbQuery = null;
			Info info	 = null;
                        StringBuffer associtedActiveCountries = new StringBuffer(",");
                        StringBuffer franchiseeassocitedActiveCountries = new StringBuffer(",");
			if(result!=null){
                                String areaid = null;
                                String franchiseeno = null;
				countriesAll = new SequenceMap();
				while(result.next()){					
					info	 = new Info();
					info.set(FieldNames.COUNTRY_ID,result.getString("COUNTRY_ID"));
					info.set(FieldNames.NAME,result.getString("NAME"));
					info.set(FieldNames.SHOW_COUNTRY,result.getString("SHOW_COUNTRY"));
                                        areaid = result.getString("AREA_ID");
                                        if(areaid != null && !areaid.equals("") && result.getString("SHOW_COUNTRY").equals("1"))
                                            associtedActiveCountries.append(result.getString("COUNTRY_ID")).append(",");
                                        
                                        franchiseeno = result.getString("FRANCHISEE_NO");
                                        if(franchiseeno != null && !franchiseeno.equals("") && result.getString("SHOW_COUNTRY").equals("1")){
                                            franchiseeassocitedActiveCountries.append(result.getString("COUNTRY_ID")).append(",");
                                        }
					countriesAll.put(result.getString("COUNTRY_ID"),info);
					info = null;
                                        areaid = null;
                                        franchiseeno = null;
				}//end while			
                                countriesAll.put("associtedActiveCountries", associtedActiveCountries.toString());
                                countriesAll.put("franchiseeassocitedActiveCountries", franchiseeassocitedActiveCountries.toString());
			}//end if
		}catch(Exception e){
			logger.error("Exception in getAllCountriesMap :"+e);
			return null;
		}		
		return countriesAll;
	}//updateCountries ends
	*/

	/*
		Function to update Countries that are Active n Inactive
	*/	
	
	public String getCountiesForCountry(String country)
	{
		String flag = "false";
		ResultSet result = null;
		if(StringUtil.isValid(country))
		{
			country = country.trim();
			if(country.startsWith(","))
			{
				country = country.substring(1, country.length());
			}
			if(country.endsWith(","))
			{
				country = country.substring(0, country.length()-1);
			}
		}
		StringBuffer sbQuery = new StringBuffer("SELECT COUNTY_NO FROM COUNTIES WHERE COUNTRY_ID IN (");
		sbQuery.append(country);
		sbQuery.append(")");
		try{
			result	= QueryUtil.getResult(sbQuery.toString(), new String[]{});
			if(result!=null && result.next())
			{
				flag = "true";
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCountiesForCountry "+e);
			flag  = "false";
		}
		return flag;
	}
	
	public void updateCountries(String sActive, String sInActive,String internationalActiveConutries){
		try{
			StringBuffer sbQuery = new StringBuffer(" UPDATE COUNTRIES SET SHOW_COUNTRY =1,IS_DOMESTIC='Y' WHERE COUNTRY_ID IN (");
			sbQuery.append(sActive);
			sbQuery.append(")");
			int result			= QueryUtil.update(sbQuery.toString(), new String[]{});
			sbQuery = null;
			logger.info("Query 1 to configure countries executed"+result);
			if(sInActive!=null && !FieldNames.EMPTY_STRING.equals(sInActive)) {
				sbQuery = new StringBuffer(" UPDATE COUNTRIES SET SHOW_COUNTRY = 0,IS_DOMESTIC='N' WHERE COUNTRY_ID IN (");
				sbQuery.append(sInActive);
				sbQuery.append(")");
				result			= QueryUtil.update(sbQuery.toString(), new String[]{});
				logger.info("Query 2 to configure countries executed"+result);
			}
			sbQuery = null;
			// P_ADMIN_E_1111 By Nikhil Verma
			if(internationalActiveConutries!=null && !internationalActiveConutries.equals(""))
			{
				sbQuery=new StringBuffer("UPDATE COUNTRIES SET SHOW_COUNTRY =1,IS_DOMESTIC='N' WHERE COUNTRY_ID IN (");
				sbQuery.append(internationalActiveConutries);
				sbQuery.append(")");
				result			= QueryUtil.update(sbQuery.toString(), new String[]{});
			}
			sbQuery = null;
		}catch(Exception e){
			logger.error("Exception in updateCountries "+e);
			e.printStackTrace();
		}		
	}//updateCountries ends

	public SequenceMap countriesMapSearch(){
		SequenceMap countriesAll = null;
		ResultSet result=null;
		try{

			StringBuffer sbQuery = new StringBuffer("SELECT * FROM COUNTRIES ORDER BY COUNTRY_ID");
			 result			= QueryUtil.getResult(sbQuery.toString(), null);
			sbQuery = null;
			if(result!=null){
				countriesAll = new SequenceMap();
				while(result.next()){
					countriesAll.put(result.getString("COUNTRY_ID"),result.getString("NAME"));
				}//end while
			}//end if
		}catch(Exception e){
			logger.error("Exception in getting All Countries SequenceMap"+e);
			e.printStackTrace();
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return countriesAll;
	}//countriesMapSearch ends

// function to get all Domestic Countrie's id and name
	public SequenceMap getDomesticCountries(String category){
		SequenceMap countriesMap = null;
		ResultSet result=null;
		if("2".equals(category))
			category="N";
		else
			category="Y";

		try{
			//P_Region_B_36256
			StringBuffer sbQuery = new StringBuffer("SELECT * FROM COUNTRIES WHERE SHOW_COUNTRY=1 AND IS_DOMESTIC='"+category+"' ORDER BY NAME");
			 result			= QueryUtil.getResult(sbQuery.toString(), null);
			sbQuery = null;
			if(result!=null){
				countriesMap = new SequenceMap();
				Info	info	= null;
				while(result.next()){
					info	= new Info();
					info.set(FieldNames.COUNTRY_ID,result.getString("COUNTRY_ID"));
					info.set(FieldNames.NAME,result.getString("NAME"));
					countriesMap.put(result.getString("COUNTRY_ID"),info);
					
				}//end while
				
			}//end if
			
		}catch(Exception e){
			logger.error("Exception in getting All Countries SequenceMap"+e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return countriesMap;
	}//getInternationalCountries ends
        
  //P_B_51059 deepakrajpurohit start      
/**
 *  returns the comma seprated list of territories which are associated with the any territory
 * @return String countryList
 */
        public String getTerrAssociatedCountries(){
            String countryList=new String();
            String query="SELECT DISTINCT(COUNTRY_ID) FROM FS_STATE_AREAS";
            ResultSet result =QueryUtil.getResult(query,null);
            if(result!=null){
                while(result.next()){
                         countryList=countryList+","+result.getString("COUNTRY_ID");
                }
                countryList=countryList+",";
            }
            System.out.println("countryList----------"+countryList);
            return countryList;
        }
 //P_B_51059 deepakrajpurohit end
    	// Get Country ID based on name
    	
    	public String getCountryID(String countryName){
    		return RegionMgr.newInstance().getCountriesDAO().getCountryID(countryName, true);
    	}//getInternationalCountries ends
    
/*---------------------------------------Moved from BaseNewPortalUtils------------------------------------------*/    	
	  public boolean applyZipLengthcheck(String countryId) {
			StringBuffer sbquery = new StringBuffer("SELECT COUNTRY_ID FROM  COUNTRIES WHERE COUNTRY_ID=");
			sbquery.append(countryId).append(" AND NAME IN ('USA')");
			boolean applycheck=false;
			
			Info configInfo = null;
			try{
					ResultSet result			= QueryUtil.getResult(sbquery.toString(), null);
					if(result.next()){
						applycheck=true;
					}
			}
			catch(Exception e){
			}
		return applycheck;
		} 
    	
	    public boolean chkForUsaCanda(String countryId) {
	        boolean isUsaCanda = false;
	        String sbQuery = "SELECT NAME,COUNTRY_ID FROM COUNTRIES WHERE COUNTRY_ID="+countryId;
	        String countryNameTemp = SQLUtil.getQueryResult(sbQuery, "NAME");

	        if (countryNameTemp != null
	                && !countryNameTemp.trim().equals("")
	                && countryNameTemp.trim().length() > 0
	                && (countryNameTemp.trim().equalsIgnoreCase("usa") || countryNameTemp.trim().equalsIgnoreCase("Canada"))) {
	            isUsaCanda = true;
	        }

	        return isUsaCanda;

	    }
    	
	    public String getAreaIdForcontry(String country) {
	        String baseAreaId = "0";
	        ResultSet result = null;
	        String query = null;
	        try {
	            query = "SELECT DISTINCT(ZA.AREA_ID)  FROM  INTERNATIONAL_AREAS ZA WHERE ZA.COUNTRY_ID="//P_FS_B_6234
	                    + country;
	            result = QueryUtil.getResult(query,null);
	            if (result != null && result.next()) {
	                baseAreaId = result.getString(1);
	            } else {
	                baseAreaId = "0";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (result != null) {
	                    result = null;
	                }
	            } catch (Exception e) {
	                logger.error(e);
	            }
	        }

	        return baseAreaId;
	    }
	    
		public String getCountryID(String countryName,boolean fromStartup){
			String countryID = null;
			if(StringUtil.isValidNew(countryName)){
				ResultSet result = null;			
				try {
					String sbQuery = "SELECT COUNTRY_ID FROM COUNTRIES WHERE SHOW_COUNTRY=1 AND NAME = '"+countryName+"' ORDER BY NAME";
					result = QueryUtil.getResult(sbQuery, null);
					if(result!=null && result.next()){
						countryID = result.getString("COUNTRY_ID");
					}
				} catch(Exception e){
					logger.error("Exception in getting Country ID",e);
				} finally {
					if(result!= null) {
						result.release();
						result = null;
					}
				}
			}
			if(!StringUtil.isValidNew(countryID)){
				countryID = "1";
			}
			return countryID;
		}
    	
		public String getCountryIdByAreaID(String areaId) {
	        StringBuffer countryIdList = new StringBuffer();
	        countryIdList.append(" ");
	        String query = "SELECT AREA_ID,COUNTRY_ID FROM INTERNATIONAL_AREAS WHERE AREA_ID='"
	                + areaId + "'";
	       ResultSet rs=null;
	        try {
	             rs = QueryUtil.getResult(query,null);
	            while (rs.next()) {
	                countryIdList.append("'");
	                countryIdList.append(rs.getString("COUNTRY_ID"));
	                countryIdList.append("'");
	                countryIdList.append(",");
	            }
	        } catch (RuntimeException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finally
			{
				QueryUtil.releaseResultSet(rs);
			}

	        return countryIdList.toString().substring(0,
	                countryIdList.toString().length() - 1);
	    }
		/*---------------------------------------Moved from BasePortalUtils------------------------------------------*/    	

		/**
		 * This method Retrieve the name of Country corresponding to the Id being
		 * provided.
		 * 
		 * @param countryId-String
		 *            Id of country as an Input parameter
		 * @return String that contain country Name
		 * @throws ConnectionException
		 */
		public String getCountryName(String countryIds)
				throws ConnectionException {
			Connection con = null;
			String countryName = "";
			Statement stmt = null;
			java.sql.ResultSet rs = null;
			try {
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("SELECT NAME FROM COUNTRIES WHERE COUNTRY_ID IN("
								+ countryIds + ")");

				while (rs.next()) {
					// P_FM_B_26619 08/17/2007 Abhishek - Insert space after comma
					// to view proper space between country
					countryName = countryName + rs.getString("NAME") + ", ";
				}
				if(countryName.indexOf(",")!=-1){
					countryName = countryName.substring(0, countryName.lastIndexOf(","));
				}
				if (countryName == null || countryName.equals(""))
					countryName = "Not Available";
				return countryName;
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
		
		
		public Info getCountriesInfo() {
			String query = "SELECT COUNTRY_ID,NAME FROM COUNTRIES WHERE NAME!='None' AND SHOW_COUNTRY = 1 ORDER BY NAME";
			return SQLUtil.getInfoFromQuery("COUNTRY_ID", "NAME", query,true);
		}

		  public SequenceMap getCountryIdAndValue() // added by Uma Jeena
		    {
		        String query="SELECT NAME,COUNTRY_ID FROM COUNTRIES WHERE SHOW_COUNTRY='1' ORDER BY NAME";
		        ResultSet rs = QueryUtil.getResult(query,null);
		        SequenceMap dataMap = new SequenceMap();
		        try 
			{
				while (rs.next())
				{
					dataMap.put(rs.getString("COUNTRY_ID"), rs.getString("NAME"));
				}
			} catch(Exception e)
			{
				logger.info("\nException in com/appnetix/app/components/fimmgr/manager/dao - FIMDAO.java --> getCountryIdAndValue: " , e);
			}
			finally
			{
				QueryUtil.releaseResultSet(rs);
			}
			return dataMap;
		    }
}//CountriesDAO ends
