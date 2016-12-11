package com.home.builderforms;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.FieldNames;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

import org.apache.log4j.Logger;
import java.util.LinkedHashMap;
import com.home.builderforms.Info;

/**
 *  The CallDAO for the CALL_STATUS and CALL_TYPE table
 *
 *@author  ravindra Verma
 *@created    
 * 
 * BUGNO-75857   5/29/2007        14June 2011 Anupreksha         Added Languageutil check
 * 
 * Canteen-20110810-533           12 sep 2011   prakash jodha    changes done to add other address heading 
 * BB_ORDER_SEQ_ISSUE             8 Dec 2011   Himanshi Gupta    modified  for order sequence in modify case
 * P_ADM_BUG_3020                 22 Dec 2011         Ashish Uniyal    When call status and call type get added then newly added should be at the
bottom
 */

public class CallDAO extends BaseDAO 
{
	static	Logger logger					= Logger.getLogger(CallDAO.class);

	public CallDAO() {
		
	}
	






	public LinkedHashMap getAllCallStatus()
	{
		String query = "SELECT CALL_STATUS_ID, CALL_STATUS_NAME FROM CALL_STATUS ORDER BY ORDER_NO";
		ResultSet result = QueryUtil.getResult(query,null);
		LinkedHashMap<String, String> statusMap = new LinkedHashMap<String, String>();
		
		while(result!=null && result.next())
		{
			statusMap.put(result.getString("CALL_STATUS_ID"), result.getString("CALL_STATUS_NAME"));//BUGNO-75857
		}
		QueryUtil.releaseResultSet(result);;
		return statusMap;
	}

	public LinkedHashMap getAllCallType()
	{
		String query = "SELECT CALL_TYPE_ID, CALL_TYPE_NAME FROM CALL_TYPE ORDER BY ORDER_NO";
		ResultSet result = QueryUtil.getResult(query,null);
		LinkedHashMap<String, String> typeMap = new LinkedHashMap<String, String>();
        System.out.println("call type Id-----"+query);
		while(result!=null && result.next())
		{
			typeMap.put(result.getString("CALL_TYPE_ID"), result.getString("CALL_TYPE_NAME"));
		}
		QueryUtil.releaseResultSet(result);;
		return typeMap;
	}

	 
	public int getOrderNo ()
	{
		String query = "SELECT MAX(ORDER_NO) AS ORDER_NO FROM CALL_STATUS";
       // System.out.println("inside the first method=========="+query);
		ResultSet result = QueryUtil.getResult(query,null);
		int orderNo = 999;

		if ( result != null && result.next() )
		{
			orderNo = Integer.parseInt( result.getString("ORDER_NO"));
		}
		QueryUtil.releaseResultSet(result);;

		return orderNo;
	}

    /**
     * For P_ADM_BUG_3020 Added method getCallOrderNo
     * @return integer value (Order No)
     * param void
     */



    public int getCallOrderNo ()
	{
		String query = "SELECT MAX(ORDER_NO) AS ORDER_NO FROM CALL_TYPE";
        //System.out.println("insid ethe 2 method==============="+query);
		ResultSet result = QueryUtil.getResult(query,null);
		int orderNo = 999;

		if ( result != null && result.next() )
		{
			orderNo = Integer.parseInt( result.getString("ORDER_NO"));
		}
		QueryUtil.releaseResultSet(result);;

		return orderNo;
	}
        //Canteen-20110810-533 Start
    /**
      *getAllAddressStatus() this method will return SequenceMap of addressheading id and addressheading name 
      *
      *
      * 
      *@return map of ADDRESS_HEADING_ID, ADDRESS_HEADING_NAME from table  ADDRESS_STATUS
      */
	
	public  SequenceMap getAllAddressStatus() {
		SequenceMap sMap = new SequenceMap();	
		String query = "SELECT ADDRESS_HEADING_ID, ADDRESS_HEADING_NAME FROM ADDRESS_STATUS ORDER BY ORDER_NO";
		ResultSet result=null;
		try{
			 result			= QueryUtil.getResult(query,null);
			while(result.next()){
				Info info = new Info();
				info.set(FieldNames.ADDRESS_HEADING_ID, result.getString("ADDRESS_HEADING_ID"));	
				info.set(FieldNames.ADDRESS_HEADING_NAME, result.getString("ADDRESS_HEADING_NAME"));
				
				String count = getTotalAssociateHeading(result.getString("ADDRESS_HEADING_ID"));
				if("0".equals(count))
				{
					count = getTotalAssociateHeadingForMU(result.getString("ADDRESS_HEADING_ID"));
				}
				if("0".equals(count))
				{
					count = getTotalAssociateHeadingForRegional(result.getString("ADDRESS_HEADING_ID"));
				}	
				info.set("COUNT", count);
				
				sMap.put(result.getString("ADDRESS_HEADING_ID"), info);	
			}
		}catch(Exception e){logger.error("Error in getting status Map class:CmLeadStatusDAO");
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

	return sMap;
	}
	/**
          *getTotalAssociateHeading() this method will return SequenceMap of addressheading id and addressheading name 
          *
          *@parameter headingID
          * 
          *@return count of  ADDRESS_HEADING from table  FIM_ADDRESS according to given fim address heading Id
          */
	public String getTotalAssociateHeading(String headingID)
	{
		String query = "SELECT COUNT(*) AS COUNT FROM FIM_ADDRESS WHERE FIM_TT_ADDRESS_HEADING='"+headingID+"' ";
		String count = "0";
		ResultSet result = QueryUtil.getResult(query,null);
		
		//System.out.println("query::::getTotalAssociateHeading:::::"+query);
		if ( result != null && result.next() )
		{
			count = result.getString("COUNT");
		}
		//System.out.println("COUNT::::::::::"+count);
		QueryUtil.releaseResultSet(result);;
		return count;
	}
	
        /**
          *getTotalAssociateHeadingForMU this method will return count of   ADDRESS_HEADING
          *
          *@parameter headingID
          * 
          *@return count of  ADDRESS_HEADING from table  FIM_MU_OTHER_ADDRESS according to given fim TT address heading Id
          */
	public String getTotalAssociateHeadingForMU(String headingID)
	{
		String query = "SELECT COUNT(*) AS COUNT FROM FIM_MU_OTHER_ADDRESS WHERE FIM_TT_ADDRESS_HEADING='"+headingID+"' ";
		String count = "0";
		ResultSet result = QueryUtil.getResult(query,null);
		
		//System.out.println("query::::getTotalAssociateHeadingForMU:::::"+query);
		if ( result != null && result.next() )
		{
			count = result.getString("COUNT");
		}
		//System.out.println("COUNT::::::::::"+count);
		QueryUtil.releaseResultSet(result);;
		return count;
	}
	 /**
          *getTotalAssociateHeadingForMU this method will return count of   ADDRESS_HEADING according to area
          *
          *@parameter headingID
          * 
          *@return count of  ADDRESS_HEADING from table  FIM_MU_OTHER_ADDRESS according to given fim TT address heading Id within region
          */
	public String getTotalAssociateHeadingForRegional(String headingID)
	{
		String query = "SELECT COUNT(*) AS COUNT FROM AREA_ADDRESS WHERE FIM_TT_ADDRESS_HEADING='"+headingID+"' ";
		String count = "0";
		ResultSet result = QueryUtil.getResult(query,null);
		
		//System.out.println("query::::getTotalAssociateHeadingForMU:::::"+query);
		if ( result != null && result.next() )
		{
			count = result.getString("COUNT");
		}
		//System.out.println("COUNT::::::::::"+count);
		QueryUtil.releaseResultSet(result);;
		return count;
	}
	 /**
          *getAddressStatusName() this method will return Address Heading
          *
          *@parameter addressHeadingId
          * 
          *@return String ADDRESS_HEADING_NAME from table  ADDRESS_STATUS according to given addressHeadingId
          */
	public String getAddressStatusName ( String addressHeadingId )
	{
		String query = "SELECT ADDRESS_HEADING_NAME FROM ADDRESS_STATUS WHERE ADDRESS_HEADING_ID=?";
		ResultSet result = QueryUtil.getResult(query, new Object[]{addressHeadingId});
		String callStatusName = null;

		if ( result != null && result.next() )
		{
			callStatusName = result.getString("ADDRESS_HEADING_NAME");
		}
		QueryUtil.releaseResultSet(result);;
		return callStatusName;
	}
	//Canteen-20110810-533 End
	
	//BB_ORDER_SEQ_ISSUE starts
    public Info getCallStatusNameWithOrderNo ( String callStatusId )
{
	String query = "SELECT CALL_STATUS_NAME,ORDER_NO FROM CALL_STATUS WHERE CALL_STATUS_ID=?";
	ResultSet result = QueryUtil.getResult(query, new Object[]{callStatusId});
	Info info=new Info();

	if ( result != null && result.next() )
	{
                info.set("statusName", result.getString("CALL_STATUS_NAME"));
                info.set("orderNo", result.getString("ORDER_NO"));
	}
	QueryUtil.releaseResultSet(result);;
	return info;
}
  
    public Info getCallTypeNameWithOrderNo ( String callTypeId )
{
	String query = "SELECT CALL_TYPE_NAME,ORDER_NO FROM CALL_TYPE WHERE CALL_TYPE_ID=?";
	ResultSet result = QueryUtil.getResult(query, new Object[]{callTypeId});
	Info info=new Info();

	if ( result != null && result.next() )
	{
              info.set("typeName", result.getString("CALL_TYPE_NAME"));
              info.set("orderNo", result.getString("ORDER_NO"));
	}
	QueryUtil.releaseResultSet(result);;
	return info;
}
  //BB_ORDER_SEQ_ISSUE ends
    
    /*--------------------------------Moved from BaseNewPortalUtils--------------------------------------------*/
    
    // Added By Ravindra Verma to get All Call Status from CALL_STATUS table.
    // Call Status are configurable from admin.
    public Info getCallStatus() {
        String query = "SELECT CALL_STATUS_ID, CALL_STATUS_NAME FROM CALL_STATUS ORDER BY ORDER_NO";
        Info callStatus = new Info();

        try {
            ResultSet result = QueryUtil.getResult(query,null);

            while (result.next()) {
                callStatus.set(result.getString("CALL_STATUS_ID"), LanguageUtil.getString(result.getString("CALL_STATUS_NAME")));
            }
        } catch (Exception e) {
            logger.error("Exception in getCallTypes: " + e);
        }

        return callStatus;
    }
    	
    public Info getCallTypes() {
        String query = "SELECT CALL_TYPE_ID, CALL_TYPE_NAME FROM CALL_TYPE ORDER BY ORDER_NO";
        Info callTypes = new Info();

        try {
            ResultSet result = QueryUtil.getResult(query,null);

            while (result.next()) {
                callTypes.set(result.getString("CALL_TYPE_ID"), LanguageUtil.getString(result.getString("CALL_TYPE_NAME")));
            }
        } catch (Exception e) {
            logger.error("Exception in getCallTypes: " + e);
        }

        return callTypes;
    }
    
    public String getCallStatusName(String callStatusId) {
        if(!StringUtil.isValidNew(callStatusId)) {		//P_FS_B_15173 by Dravit Gupta
        	callStatusId = "-1";
        }
        return SQLUtil.getLastColumnValue("CALL_STATUS", "CALL_STATUS_NAME", "CALL_STATUS_ID", callStatusId);
    }
    
    public String getCallTypeName(String callTypeId) {
    	return SQLUtil.getLastColumnValue("CALL_TYPE", "CALL_TYPE_NAME", "CALL_TYPE_ID", callTypeId);
    }
    
    public String getCallType(String callID){
		return SQLUtil.getLastColumnValue("CM_CALL_TYPE", "CALL_TYPE_NAME", "CALL_TYPE_ID", callID);
	}
}