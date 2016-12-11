/*
 * Name - RetrieveDAO.java
 * Directory position - com/appnetix/app/components/masterdatamgr/manager/dao
 * Description: The DAO for the MASTERDATA table
 * @List/Names of JSPs referenced by this JSP
 * @author  -Vikas Mittal
 * @version 1.1.1.1
 * @created June 2, 2004
 * P_FIM_E_FILTERS1.1.1      18/10/07	Niraj Sachan   method getIdAndValue(entitycode)  
 * PW_ENH_APPROVE_TSK_STATUS  24-Apr-2015	Rohit Jain   Approve/Reject status will show on task status.  
 */

package com.appnetix.app.components.masterdatamgr.manager.dao;

import java.util.Calendar;
import com.appnetix.app.components.BaseDAO;
import com.home.builderforms.*;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class MasterDataDAO extends BaseDAO {

	static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(MasterDataDAO.class);

	public MasterDataDAO() {
		this.tableAnchor = TableAnchors.MASTER_DATA;

	}

	/*
	 * This Method Gets the dataid,PARENT_DATA_ID and returns the data value
	 * from * master data data table that is used to display the combovalue on
	 * the forms
	 */
	public String getComboDataValue(int dataId, String parentDataID) {
		String resultValue = " ";
		StringBuffer query = new StringBuffer(
				"SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE=? ");
		if (parentDataID != null && !parentDataID.equals(""))
			query.append(" AND PARENT_DATA_ID=? ");

		query.append(" ORDER BY DATA_VALUE ");
		String selectParams[] = { dataId + "", parentDataID };
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(),
					selectParams);
			while (result.next()) {
				resultValue = result.getString("DATA_VALUE");
			}
		} catch (Exception e) {
			logger.error(
					"Exception in getting record for getComboDataValue : ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}


		return resultValue;
	}

	/**
	 * This method take DATA_TYPE and return DATA_VALUE ORDER BY DATA_VALUE
	 * containing Info
	 */
	public Info getMasterInfo(int dataType) {
		return getMasterInfo(dataType, null);
	}

	/**
	 * This method take DATA_TYPE and return DATA_VALUE ORDER BY MASTER_DATA_ID
	 * containing Info
	 */
	public Info getMasterInfoById(int dataType) {
		return getMasterInfo(dataType, "MASTER_DATA_ID");
	}

	// P_INT_B_59820:START:ANKIT SAINI
	public Info getMasterInfoById(int dataType, String userLanguage) {
		return getMasterInfo(dataType, "MASTER_DATA_ID", userLanguage);
	}

	// P_INT_B_59820:END:ANKIT SAINI
	/**
	 * This method take DATA_TYPE and the key for sorting and return Info of
	 * DATA_VALUE
	 */
	//PW_ENH_APPROVE_TSK_STATUS starts
	public Info getMasterInfo(int dataType, String sortKey) {
		return getMasterInfo(dataType,sortKey,false,null);
	}
	
	public Info getMasterInfo(int dataType, String sortKey,boolean isParentIdCheck,String parentDataId) {
		Info info = new Info();
		StringBuffer query = new StringBuffer(
				"SELECT MASTER_DATA_ID,DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= ")
				.append(dataType);
		if(isParentIdCheck && StringUtil.isValid(parentDataId)){
			query.append(" AND PARENT_DATA_ID='" + parentDataId + "'");
		}
		//PW_ENH_APPROVE_TSK_STATUS ends
		if (sortKey != null && !sortKey.equals("")) {
			query.append(" ORDER BY ").append(sortKey);
		} else {
			query.append(" ORDER BY DATA_VALUE ");
		}
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(),
					new Object[] {});
			while (result.next()) {
				info.set(result.getString("MASTER_DATA_ID"),LanguageUtil.getString(result.getString("DATA_VALUE")));
			}
		} catch (Exception e) {
			logger.error("Exception in getMasterInfo: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return info;
	}
	
	/**
	 * This Method takes DATA_TYPE and sortKey 
	 * @return Info of PARENT_DATA_ID and DATA_VALUE
	 */
	public Info getMasterInfoParentData(int dataType, String sortKey) {
		Info info = new Info();
		StringBuffer query = new StringBuffer("SELECT PARENT_DATA_ID,DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= ").append(dataType);
		
		if (sortKey != null && !sortKey.equals("")) {
			query.append(" ORDER BY ").append(sortKey);
		} else {
			query.append(" ORDER BY DATA_VALUE ");
		}
		
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(), new Object[] {});
			while (result.next()) {
				info.set(result.getString("PARENT_DATA_ID"),LanguageUtil.getString(result.getString("DATA_VALUE")));
			}
		} catch (Exception e) {
			logger.error("Exception in getMasterInfo: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return info;
	}
	
	

	// P_INT_B_59820:START:ANKIT SAINI
	//PW_ENH_APPROVE_TSK_STATUS starts	
	public Info getMasterInfo(int dataType, String sortKey, String userLanguage){
		return getMasterInfo(dataType,  sortKey,  userLanguage,null);
	} 
	
	public Info getMasterInfo(int dataType, String sortKey, String userLanguage, String parentDataId) {
		Info info = new Info();
		StringBuffer query = new StringBuffer(
				"SELECT MASTER_DATA_ID,DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= ")
				.append(dataType);
		
		if(StringUtil.isValid(parentDataId)){
			query.append(" AND PARENT_DATA_ID='" + parentDataId + "'");
		}
		//PW_ENH_APPROVE_TSK_STATUS ends
		if (sortKey != null && !sortKey.equals("")) {
			query.append(" ORDER BY ").append(sortKey);
		} else {
			query.append(" ORDER BY DATA_VALUE ");
		}
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(),
					new Object[] {});
			while (result.next()) {
				info.set(result.getString("MASTER_DATA_ID"),
						LanguageUtil.getString(result.getString("DATA_VALUE"),
								userLanguage));
			}
		} catch (Exception e) {
			logger.error("Exception in getMasterInfo: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return info;
	}

	// P_INT_B_59820:END:ANKIT SAINI
	/**
	 * This method take DATA_TYPE and return HashMap of DATA_VALUE with
	 * DATA_VALUE as Key.
	 */
	public HashMap getMasterMap(int dataType) {
		HashMap masterDataMap = new HashMap();
		StringBuffer query = new StringBuffer(
				"SELECT MASTER_DATA_ID,DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= ")
				.append(dataType);
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(),
					new Object[] {});
			while (result.next()) {
				masterDataMap.put(result.getString("DATA_VALUE").toLowerCase()
						.trim(), result.getString("MASTER_DATA_ID"));
			}
		} catch (Exception e) {
			logger.error("Exception in getMasterInfo: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return masterDataMap;
	}

	/** This method takes DATA_TYPE AND PARENT_DATA_ID and return DATA_VALUE */
	public String getValue(int dataId, String parentDataId) {
		String query = "SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE='"
				+ dataId + "' AND PARENT_DATA_ID='" + parentDataId + "'";
		String dataValue = "";
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query,null);
			if (result.size() > 0) {
				result.next();
				dataValue = result.getString("DATA_VALUE");
			}

		} catch (Exception e) {
			logger.error("Exception in getValue: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataValue;
	}

	public String getValue(int dataId, int parentDataId) {
		String query = "SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE='"
				+ dataId + "' AND PARENT_DATA_ID='" + parentDataId + "'";
		String dataValue = "";
		String value = "";
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query,null);
			if (result.next()) {
				dataValue = result.getString("DATA_VALUE");
			}
			if (dataValue != null && !"".equals(dataValue.trim())) {
				value = dataValue;
			} else {
				value = null;
			}

		} catch (Exception e) {
			logger.error("Exception in getValue: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return value;
	}

	/** This method takes DATA_TYPE AND MASTER_DATA_ID and return DATA_VALUE */
	public String getVal(int dataId, String parentDataId) {

		String query = "SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE='"
				+ dataId + "' AND MASTER_DATA_ID='" + parentDataId + "'";
		String dataValue = FieldNames.EMPTY_STRING;

		ResultSet result = null;
		try {
			result = QueryUtil.getResult(query, null);

			if (result != null && result.next()) {
				dataValue = result.getString("DATA_VALUE");
			}
		} catch (Exception e) {
			logger.error("Exception in getVal: ", e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataValue;
	}

	public String getID(int dataId, int parentDataId) {

		String query = "SELECT MASTER_DATA_ID FROM MASTER_DATA WHERE DATA_TYPE='"
				+ dataId + "' AND PARENT_DATA_ID='" + parentDataId + "'";
		String dataValue = "";
		String value = "";
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query,null);
			if (result.next()) {
				dataValue = result.getString("MASTER_DATA_ID");
			}
			if (dataValue != null && !"".equals(dataValue.trim())) {
				value = dataValue;
			} else {
				value = null;
			}

		} catch (Exception e) {
			logger.error("Exception in getValue: ", e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return value;
	}

	/**
	 * This method returns data value from MASTER_DATA table on data type and
	 * parentDataID
	 */

	public static String getDataValue(String dataType, String masterDataID) {
		StringBuffer query = new StringBuffer(
				"SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= "
						+ dataType);
		query.append(" AND MASTER_DATA_ID= " + masterDataID);
		String dataValue = null;
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(),
					new Object[] {});
			while (result.next()) {
				dataValue = result.getString("DATA_VALUE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataValue;
	}

	/* Method to retrieve all id-value pairs for a master data entity */
	// MODIFIED BY BINU ON 6TH FEB 07 to fetch the configured states in store
	// opener.
	public SequenceMap getIdAndValue(String entityCode, String excludeValues) {
		//PW_ENH_APPROVE_TSK_STATUS starts	
		return getIdAndValue(entityCode,excludeValues,null);
	}
	public SequenceMap getIdAndValue(String entityCode, String excludeValues, String parentID) {
		SequenceMap dataMap = null;
		StringBuffer query = new StringBuffer(
				"SELECT MASTER_DATA_ID, DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE= ? ");
		if (entityCode != null && entityCode.equals("2001")) {
			query.append("AND PARENT_DATA_ID IN (SELECT MASTER_DATA_ID FROM MASTER_DATA MD LEFT JOIN COUNTRIES C ON MD.MASTER_DATA_ID = C.COUNTRY_ID  WHERE DATA_TYPE = 2000 AND C.SHOW_COUNTRY = 1 ORDER BY DATA_VALUE ) ");

		}
		if (excludeValues != null && !"".equals(excludeValues.trim())) {
			query.append("AND MASTER_DATA_ID NOT IN (").append(excludeValues)
					.append(") ");

		}
		if (parentID != null && !"".equals(parentID.trim())) {
			query.append("AND PARENT_DATA_ID  IN (").append(parentID)
					.append(") ");

		}
		//PW_ENH_APPROVE_TSK_STATUS ends
		query.append("ORDER BY DATA_VALUE ");
		String params[] = new String[] { entityCode };
		ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(), params);

			dataMap = new SequenceMap();
			while (result.next())
				dataMap.put(result.getString("MASTER_DATA_ID"),
						LanguageUtil.getString(result.getString("DATA_VALUE")));

		} catch (Exception e) {
			logger.error("Exception in getIdAndValue: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataMap;
	}
	
	public SequenceMap getDataMasterDataFieldType(String entityCode,String dataValue) {
		SequenceMap dataMap = null;
		StringBuffer query = new StringBuffer(
				"SELECT DATA_VALUE,DATA_DISPLAY FROM MASTER_DATA_FIELD_TYPE WHERE DATA_TYPE = ?  ");
		if(StringUtil.isValid(dataValue))
		{
			dataValue= dataValue.replaceAll(" ", "");
			if(dataValue.indexOf(",") != -1)
				dataValue=dataValue.replaceAll(",", "','");			
			query.append(" AND DATA_VALUE IN('").append(dataValue).append("')");
		}
		//query.append(" ORDER BY DATA_DISPLAY ");
		String params[] = new String[] { entityCode };
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(), params);

			dataMap = new SequenceMap();
			while (result.next()) {
				System.out.println("data display=-================================-"+result.getString("DATA_DISPLAY"));
				dataMap.put(result.getString("DATA_VALUE"),
						LanguageUtil.getString(result.getString("DATA_DISPLAY")));
				
			}

		} catch (Exception e) {
			logger.error("Exception in getIdAndValue: ", e);
			e.printStackTrace();
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataMap;
	}

	// P_FIM_E_FILTERS1.1.1 Niraj Sachan
	public SequenceMap getIdAndValue(String entityCode) {

		return getIdAndValue(entityCode, null);
	}

	/* Method to retrieve value of a master data record */
	public String getValueForId(String entityCode, String id) {
		return getIdOrValue(entityCode, id, "ID");
	}

	/* Method to retrieve Id of a master data record */
	public String getIdForValue(String entityCode, String id) {
		return getIdOrValue(entityCode, id, "Value");
	}

    public String getIdOrValue(String entityCode, String value, String type) {
        return getIdOrValue(entityCode, value,type,true);
    }
    public String getIdOrValue(String entityCode, String value, String type,boolean internationalization) {
		if (value == null || value.equals("") || value.equals("-1"))
			return "";

		StringBuffer query = new StringBuffer(
				"SELECT MASTER_DATA_ID,DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE = ? ");

		if (type != null && type.equals("ID"))
			query.append(" AND MASTER_DATA_ID = ?");
		else if (type != null && type.equals("Value"))
			query.append(" AND DATA_VALUE = ?");

		String params[] = new String[] { entityCode, value };
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query.toString(), params);

			while (result.next())
				if (type != null && type.equals("ID"))
                    return internationalization?result.getString("DATA_VALUE"):result.getString("DATA_VALUE");
				else if (type != null && type.equals("Value"))
                    return internationalization?result.getString("MASTER_DATA_ID"):result.getString("MASTER_DATA_ID");
		} catch (Exception e) {
			logger.error("Exception in getIdOrValue: ", e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return null;
	}

	/*
	 * Method to retrieve all id-value pairs for a master data entity for ex.
	 * used to return state-country id pair for entitycode '2001'
	 */
	public HashMap getIdAndParentIDMap(String entityCode) {
		return getDataMap(entityCode, "parentId");
	}

	/**
	 * Method to retrieve all id-value pairs for a master data entity for ex.
	 * used to return statename-pid pair for entitycode '2001'
	 */
	public HashMap getIdAndValueMap(String entityCode) {
		return getDataMap(entityCode, "dataValue");
	}

	/**
	 * This method take the DATA_TYPE and return hashmap containing pair
	 * of(master_data_id,parent_data_id) or (data_value,master_data_id)
	 */
	public HashMap getDataMap(String entityCode, String type) {
		HashMap dataMap = null;
		String query = "SELECT DATA_VALUE, MASTER_DATA_ID,PARENT_DATA_ID FROM MASTER_DATA WHERE DATA_TYPE = ? ";
		String params[] = new String[] { entityCode };
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query, params);

			dataMap = new HashMap();
			while (result.next())
				if (type != null && type.equals("parentId"))
					dataMap.put(result.getString("MASTER_DATA_ID"),
							result.getString("PARENT_DATA_ID"));
				else if (type != null && type.equals("dataValue"))
					dataMap.put(result.getString("DATA_VALUE").toLowerCase(),
							result.getString("MASTER_DATA_ID"));

		} catch (Exception e) {
			logger.error("Exception in getDataMap: ", e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataMap;
	}

	/*** This method return the country,currency info from the master data */
	public static Info getCountryCurIDs(boolean flag) {
		Info info = new Info();
		String query = "select B.MASTER_DATA_ID AS DID,A.DATA_VALUE AS DV from  MASTER_DATA A INNER JOIN MASTER_DATA B ON A.MASTER_DATA_ID=B.PARENT_DATA_ID  WHERE A.DATA_TYPE=2000 AND B.DATA_TYPE=5005";
		ResultSet result = QueryUtil.getResult(query,null);

		while (result.next()) {
			info.set(result.getString("DID"), result.getString("DV"));
		}
		if (flag) {
			info.set("disable", "true");
		}

		return info;
	}

	/**
	 * This method takes DATA_TYPE AND PARENT_DATA_ID and return DATA_VALUE from
	 * MASTER_DATA_FOR_SCHEDULER
	 */
	public String getValueForScheduler(int dataId, String parentDataId) {
		String query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE='"
				+ dataId + "' AND PARENT_DATA_ID='" + parentDataId + "'";
		String dataValue = "";
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query,null);
			//result.next();
			if(result.next())
			dataValue = result.getString("DATA_VALUE");
		} catch (Exception e) {
			logger.error("Exception in getValue: " + e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataValue;
	}

	// Added By Amit Kumar Srivastava on 24 th June
	public String getTaskCompletion(String datatype,String datavalue1) {
		// TODO Auto-generated method stub

		String query = "SELECT MASTER_DATA_ID FROM MASTER_DATA WHERE DATA_TYPE='"+datatype+"' AND DATA_VALUE='"+datavalue1+"'";
		String dataValue = "";
		
		logger.info("100007   "+query);
ResultSet result=null;
		try {
			 result = QueryUtil.getResult(query,null);

			while (result.next()) {
				dataValue = dataValue + result.getString("MASTER_DATA_ID")
						+ ",";
			}
			if(!dataValue.trim().equals("")){
				dataValue = dataValue.substring(0,dataValue.length()-1);
			}
			logger.info("100007   dataValue  "+dataValue);

		} catch (Exception e) {
			logger.error("Exception in getValue: " + e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return dataValue;
	}

	// Added By Amit Kumar Srivastava on 24 th June till here
	
  /*-----------------------------------------Moved From BaseNewPortalUtils-------------------------------------------------*/
	public String getValueById(String MasterDataId)
	{
		return SQLUtil.getColumnValue("MASTER_DATA", "DATA_VALUE","MASTER_DATA_ID" , MasterDataId);
	}
	
	public String getValueByType(String DataType)
	{
		return SQLUtil.getColumnValue("MASTER_DATA", "DATA_VALUE","DATA_TYPE" , DataType);
	}
	
	public String getIdByType(String DataType) 
	{
        return SQLUtil.getColumnValue("MASTER_DATA", "MASTER_DATA_ID", "DATA_TYPE", DataType);
    }
  	
    /**
     * This method returns the configured Listen360 credentials
     * @return HashMap<String,String>
     */
    public HashMap<String,String> getListen360Credentials()
    {
    	StringBuilder query = new StringBuilder("SELECT MD.DATA_VALUE,MD.DATA_TYPE FROM MASTER_DATA MD WHERE MD.DATA_TYPE IN ('2015','2016','2017') ORDER BY DATA_TYPE;"); 
    	ResultSet result=null;
    	HashMap<String,String> config=new HashMap<String, String>();;
    	try{
    		result = QueryUtil.getResult(query.toString(), null);
    		if(result!=null)
    		{
    			if(result.next() && "2015".equals(result.getString("DATA_TYPE")))
    				config.put("providerKey", result.getString("DATA_VALUE"));
    			if(result.next() && "2016".equals(result.getString("DATA_TYPE")))
    				config.put("secretKey", result.getString("DATA_VALUE"));
    			if(result.next() && "2017".equals(result.getString("DATA_TYPE")))
    				config.put("ssoUrl", result.getString("DATA_VALUE"));
    		}
    		
    	}catch(Exception e){
			logger.info("Excption in method getListen360Credentials()");
		}finally{
			QueryUtil.releaseResultSet(result);
			query=null;
		}
		return config ;
    }
    public String getMonthNames(ArrayList monthIds) {
        ResultSet result=null;
        String query;
        StringBuffer monthCS;
        StringBuffer monthNames;
        monthCS = new StringBuffer();

        if (monthIds == null || monthIds.size() == 0) {
            monthNames = new StringBuffer();
        } else {
            monthNames = new StringBuffer();

            for (int i = 0; i < monthIds.size(); i++) {
                monthCS.append(monthIds.get(i)).append(",");
            }

            if (monthCS.length() > 0) {
                monthCS.deleteCharAt(monthCS.length() - 1);
            }

            query = "SELECT DATA_VALUE FROM MASTER_DATA WHERE MASTER_DATA_ID IN ("
                    + monthCS + ")";

            try {
                result = QueryUtil.getResult(query,null);

                while (result.next()) {
                    monthNames.append(LanguageUtil.getString(result.getString("DATA_VALUE"))).append(
                            ", ");
                }

                if (monthNames.length() > 0) {
                    monthNames.deleteCharAt(monthNames.length() - 2);
                }
            } catch (Exception e) {
                logger.error("ExceptioN: ", e);
            }finally
    		{
    			QueryUtil.releaseResultSet(result);
    		}

        }

        return monthNames.toString();
    }
	
    public String getMultipleStatus(String statusName, String dataType) {

        String status = "";
        String query = "SELECT MASTER_DATA_ID FROM MASTER_DATA  WHERE  DATA_TYPE='"
                + dataType + "' AND DATA_VALUE IN('" + statusName + "')";
        try {
            ResultSet result = QueryUtil.getResult(query,null);
            while (result.next()) {
                if (status.equals("")) {
                    status = result.getString("MASTER_DATA_ID");
                } else {
                    status = status + "','"
                            + result.getString("MASTER_DATA_ID");
                }
            }
        } catch (Exception e) {
            logger.error("Exception in getMasterInfo: " + e);
        }
        return status;
    }
    public String getStatus(String statusName, String dataType) {

        // 8103
        String status = "";
        // P_SO_E_111
        String query = "SELECT MASTER_DATA_ID FROM MASTER_DATA  WHERE  DATA_TYPE='"
                + dataType + "' AND DATA_VALUE IN('" + statusName + "')";
        // System.out.println("query"+query);
        try {
            ResultSet result = QueryUtil.getResult(query,null);
            while (result.next()) {

                status = result.getString("MASTER_DATA_ID");
            }
        } catch (Exception e) {
            logger.error("Exception in getMasterInfo: " + e);
        }
        return status;
    }
    
    public boolean isValidMailDomainName(String mailDomainName)
    {
 	   
 	   StringBuffer query = new StringBuffer("SELECT DATA_VALUE FROM MASTER_DATA WHERE DATA_TYPE=?");
 	   String dataValue ="";
 	   boolean domainName=true;
 	   com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query.toString(), new Object[]{MasterEntities.CAPTIVATE_MAIL_DOMAIN});
 	   if(result.next()) 
 	   {
            dataValue=result.getString("DATA_VALUE");
        }
        if(StringUtil.isValid(dataValue))
        {
     	   if(dataValue.indexOf(mailDomainName)!= -1)
     	   {
     		   domainName= true;
     	   }
     	   else
     	   {
     		   domainName= false;
     	   }
     	   
        } else 
        {
     	   domainName= true;
        }
        return domainName;
    }
    

    public String updateDataValue(String dataType, String dataValue) {
        SQLUtil.updateTableValue("MASTER_DATA", "DATA_VALUE", dataValue, "DATA_TYPE", dataType, null, null);
        return dataValue;
    }
    


    public SequenceMap getYears() {
    	Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR) + 11017;
        String query = "SELECT MASTER_DATA_ID, DATA_VALUE FROM  MASTER_DATA WHERE DATA_TYPE='14001' AND MASTER_DATA_ID <= ".concat(Integer.toString(currentYear)).concat(
                " ORDER BY MASTER_DATA_ID");
        
        return SQLUtil.getSequenceMapFromQuery("MASTER_DATA_ID", "DATA_VALUE", query);
    }
    
    public SequenceMap getMonths() {
        SequenceMap years;
        String query;
        ResultSet result=null;

        years = new SequenceMap();
        query = "SELECT MASTER_DATA_ID, DATA_VALUE FROM  MASTER_DATA WHERE DATA_TYPE='15001' ORDER BY MASTER_DATA_ID";

        try {
            result = QueryUtil.getResult(query,null);

            while (result.next()) {
                years.put(result.getString("MASTER_DATA_ID"),
                        LanguageUtil.getString(result.getString("DATA_VALUE")));
            }
        } catch (Exception e) {
            logger.error("ExceptioN: ", e);
        }
        finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return years;
    }

}
