package com.home.builderforms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.sqlqueries.*;
import org.apache.log4j.Logger;

/**
 *  The FsLeadOwnerDAO for the FS_SCHEDULE_LEAD_OWNER table
 *
 *@author     kulmeet
 *@created    Wed April 11 15:46:05 PDT 2006

--------------------------------------------------------------------------------------------------------------------------------------
Version No.		Date		  By	         Against		Method changed			Comments

P_FS_E_RRIR	   12/12/06	    Y.NagaRaja	    Round Robbing	getLeadOwnersMap();	To assign Round Robin users for Regions
--------------------------------------------------------------------------------------------------------------------------------------											  in Region		getMaxOrderNo();
Bug 18689	12/12/06	Santanu				getLeadOwnersMap();    passed userNo to get list of owners except adm in case of othere user login.
--------------------------------------------------------------------------------------------------------------------------------------
P_CC_ENHANCEMENT    Aug 22 ,2008 	Manoj kumar getConfigInfo(id),getLeadAgentsMap,getMaxOrderNo      To set and select the Agent scheme.
BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object 
--------------------------------------------------------------------------------------------------------------------------------------

 */
public class FsLeadOwnerDAO extends BaseDAO {
	static	Logger logger					= Logger.getLogger(FsLeadOwnerDAO.class);
	
	/**
	*  Constructor for the LeadStatusDAO object
	*/
	public FsLeadOwnerDAO() {
		this.tableAnchor = "fsScheduleLeadOwner";

	}







	/*public SequenceMap getIdStatusInfo() {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
		try{
		ResultSet result			= QueryUtil.getResult(query,null);
			while(result.next()){
				
				sMap.put(result.getString("LEAD_STATUS_ID"),result.getString("LEAD_STATUS_NAME") );
				
			}
		}
		catch(Exception e){
		}
		return sMap;
	}*/
	
	public SequenceMap getLeadOwnersMap(String regionNo, String userNo) { //P_FS_Enh_AssignmentBySourceDetail
		return getLeadOwnersMap(regionNo, userNo, "NA");
	}
	public  SequenceMap getLeadOwnersMap(String regionNo,String userNo, String sourceId) 
	{ 	//P_FS_Enh_AssignmentBySourceDetail
		SequenceMap sMap = new SequenceMap();	
		//String query = "SELECT A.USER_NO,U.USER_ID,A.ORDER_NO,COUNT(A.USER_NO) 'LEADS'  FROM FS_SCHEDULE_LEAD_OWNER A  LEFT JOIN USERS U ON A.USER_NO= U.USER_NO ORDER BY A.ORDER_NO";
		//String query = "SELECT A.SCHEDULE_ID,A.USER_NO,U.USER_ID,F.FIRST_NAME,F.LAST_NAME,A.ORDER_NO,A.PICK,COUNT(A.USER_NO) 'LEADS'  FROM FS_SCHEDULE_LEAD_OWNER A  LEFT JOIN USERS U ON A.USER_NO= U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE U.STATUS=1 GROUP BY A.USER_NO ORDER BY A.ORDER_NO";
		// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
		StringBuffer sbQuery= new StringBuffer(" SELECT A.SCHEDULE_ID,A.USER_NO,U.USER_ID,U.USER_LEVEL,F.FIRST_NAME,F.LAST_NAME,A.ORDER_NO,A.PICK,COUNT(A.USER_NO) 'LEADS'  FROM FS_SCHEDULE_LEAD_OWNER A  LEFT JOIN USERS U ON A.USER_NO= U.USER_NO LEFT JOIN FRANCHISEE F ON F.FRANCHISEE_NO=U.FRANCHISEE_NO WHERE ");
		//P_FS_Enh_AssignmentBySourceDetail starts
		sbQuery.append("A.REGION_ID ='").append(regionNo).append("' AND A.SOURCE_ID='").append(sourceId).append("'");
		//P_FS_Enh_AssignmentBySourceDetail ends
		
		sbQuery.append("  AND  U.STATUS=1 ");
		sbQuery.append(" GROUP BY A.USER_NO ORDER BY A.ORDER_NO ");
		// Y.NagaRaja comments ends here
		ResultSet result=null;
        try
        {
          	result	= QueryUtil.getResult(sbQuery.toString(), null);
			while(result.next())
			{
				Info info = new Info();

				if(userNo!=null && !userNo.equals("1") && result.getString("USER_NO").equals("1"))
					continue;
                
				info.set(FieldNames.SCHEDULE_ID, result.getString("SCHEDULE_ID"));
                info.set(FieldNames.USER_NO, result.getString("USER_NO"));			
				info.set(FieldNames.USER_ID, result.getString("USER_ID"));
                info.set(FieldNames.ORDER_NO, result.getString("ORDER_NO"));
                info.set(FieldNames.PICK, result.getString("PICK"));
                info.set(FieldNames.FIRST_NAME, result.getString("FIRST_NAME"));
                String lastName = result.getString("LAST_NAME");
                if(Constants.USER_LEVEL_DIVISION.equals(result.getString("USER_LEVEL")))
                {
                	lastName = lastName+"(DU)";
                }else if("2".equals(result.getString("USER_LEVEL")))
                {
                	lastName = lastName+"(RU)";
                }
                info.set(FieldNames.LAST_NAME, lastName);
				info.set("ASSOCIATEDLEADS", result.getString("LEADS"));

				sMap.put(result.getString("USER_NO"), info);
			}
		}
		catch(Exception e){
			logger.info("exception in getLeadOwnersMap() ");
		}
		finally
		{QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}


/*
	public void createStatusMap(SequenceMap sMap) {

		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
		try{
		ResultSet result			= QueryUtil.getResult(query,null);
			while(result.next()){
				Info info = new Info();
				info.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));
				sMap.put(result.getString("LEAD_STATUS_ID"), info);
				
			}
		}
		catch(Exception e){
		}
	}
*/
	/*public Info getStatusInfo() {

		Info info = new Info();
		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
		try{
		ResultSet result			= QueryUtil.getResult(query,null);
			while(result.next()){
				
				info.set(result.getString("LEAD_STATUS_ID"),result.getString("LEAD_STATUS_NAME") );
				
			}
		}
		catch(Exception e){e.printStackTrace();
		}
		return info;
	}*/

	/*public String getStatusForUnregisteredStates() {

		String StatusForUnregisteredStates = null;
		String query = "SELECT LEAD_STATUS_ID FROM FS_LEAD_STATUS WHERE FOR_UNREGISTERED_STATES ='1'";
		try{
		ResultSet result			= QueryUtil.getResult(query,null);
			if(result.next()){

				StatusForUnregisteredStates = result.getString("LEAD_STATUS_ID");
			}
		}
		catch(Exception e){e.printStackTrace();
		}
		return StatusForUnregisteredStates;
	}*/
	/* Set the status of all the states to registered and then update unregistered state */
	/*public boolean updateUnregisteredState(String statusId){


	String sQuery1 = null;
	String sQuery2 = null;
	ResultSet result		= null;
	int length;
	try{
		sQuery1 = "UPDATE FS_LEAD_STATUS SET FOR_UNREGISTERED_STATES = '0'";
		sQuery2 = "UPDATE FS_LEAD_STATUS SET FOR_UNREGISTERED_STATES = '1' WHERE LEAD_STATUS_ID = ?";
		if(statusId !="" || statusId != null){
		
		length	= QueryUtil.update(sQuery1, new String[]{});
		length = QueryUtil.update(sQuery2, new String[]{statusId});

		}
	}catch(Exception e){e.printStackTrace(); return false;}
	return true;
}*/
	
/*

public String getMaxOrderNo() {
		String query = "SELECT MAX(ORDER_NO) 'maxOrderNo' FROM  FS_SCHEDULE_LEAD_OWNER";
		String maxOrderNo=null;
		try{
		ResultSet result			= QueryUtil.getResult(query,null);
			if(result.next()){
				maxOrderNo= result.getString("maxOrderNo");

			}
		}
		catch(Exception e){
		}

		return maxOrderNo;
	} 
*/
	// P_CC_ENHANCEMENT Added by Manoj for Agent Scheme Selection
	public String getMaxOrderNo(String regionNo) {
		return getMaxOrderNo(regionNo,null); 
	}
	
	// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
	public String getMaxOrderNo(String regionNo,String tableName) { //P_FS_Enh_AssignmentBySourceDetail
		return getMaxOrderNo(regionNo, tableName, "NA");
	}
	public String getMaxOrderNo(String regionNo,String tableName, String sourceId) {
		//String query = "SELECT MAX(ORDER_NO) 'maxOrderNo' FROM  FS_SCHEDULE_LEAD_OWNER";
		StringBuffer sbQuery = new StringBuffer("SELECT MAX(ORDER_NO) 'maxOrderNo' FROM  ");
		if(tableName!=null && !tableName.equals("")){
			sbQuery.append(tableName);
		}else {
			sbQuery.append("FS_SCHEDULE_LEAD_OWNER ");
		}
		sbQuery.append(" where REGION_ID ='").append(regionNo).append("'");
		if(!StringUtil.isValidNew(tableName)) { //P_FS_Enh_AssignmentBySourceDetail starts
			sbQuery.append(" and SOURCE_ID ='").append(sourceId).append("'");
		} //P_FS_Enh_AssignmentBySourceDetail ends
		
		
		String maxOrderNo=null;
		ResultSet result=null;
		try{
		//ResultSet result			= QueryUtil.getResult(query,null);
		 result			= QueryUtil.getResult(sbQuery.toString(), null);
			if(result.next()){
				maxOrderNo= result.getString("maxOrderNo");

			}
		}
		catch(Exception e){
		}
		finally
		{QueryUtil.releaseResultSet(result);
		}
		return maxOrderNo;
	} 
	// Y.NagaRaja comments ends here
//Added by Kushagra for the selection of the Round Robin
//This function returns Details of FS_CONFIG which contails configuration details
public Info getConfigInfo() {
		String query = "SELECT CONFIG_ID , IS_ROUND_ROBIN FROM  FS_CONFIG WHERE CONFIG_ID=1 ORDER BY MAPPED_ON DESC";
		Info configInfo = null;
		ResultSet result=null;
		try{
				 result			= QueryUtil.getResult(query,null);
				if(result.next()){
						configInfo = new Info();
						configInfo.set(FieldNames.CONFIG_ID,result.getString("CONFIG_ID"));
						configInfo.set(FieldNames.IS_ROUND_ROBIN,result.getString("IS_ROUND_ROBIN"));
				}
		}
		catch(Exception e){
		}
		finally
		{QueryUtil.releaseResultSet(result);
		}

return configInfo;
} 

/**
 * @author manoj 
 * @param id 2 for call center Agent.
 * @return Info of call center association. 
 */
public Info getConfigInfo(String id) {
	String query = "SELECT CONFIG_ID , IS_ROUND_ROBIN FROM  FS_CONFIG WHERE CONFIG_ID="+id+" ORDER BY MAPPED_ON DESC";
	Info configInfo = null;
	ResultSet result=null;
	try{
			 result			= QueryUtil.getResult(query,null);
			if(result.next()){
					configInfo = new Info();
					configInfo.set(FieldNames.CONFIG_ID,result.getString("CONFIG_ID"));
					configInfo.set(FieldNames.IS_ROUND_ROBIN,result.getString("IS_ROUND_ROBIN"));
			}
	}
	catch(Exception e){
	}
	finally
	{

		QueryUtil.releaseResultSet(result);
	}

return configInfo;
}
/**
 * @author manoj
 * @param regionNo region number
 * @return SequenceMap of Agents
 * 
 */
public SequenceMap getLeadAgentsMap(String regionNo) {
	SequenceMap sMap = new SequenceMap();	
	StringBuffer sbQuery= new StringBuffer(" SELECT A.SCHEDULE_ID,A.AGENT_NO,AU.AGENT_ID,AU.USER_FIRST_NAME,AU.USER_LAST_NAME,AU.USER_TYPE,A.ORDER_NO,A.PICK,COUNT(A.AGENT_NO) 'LEADS'  FROM FS_SCHEDULE_LEAD_AGENT A  LEFT JOIN AGENT_USERS AU ON A.AGENT_NO= AU.AGENT_NO WHERE ");
	sbQuery.append("A.REGION_ID ='").append(regionNo).append("'  AND  AU.STATUS=1 AND AU.IS_DELETED!='Y' ");
	sbQuery.append(" GROUP BY A.AGENT_NO ORDER BY A.ORDER_NO ");
	ResultSet result=null;
	try{
		 result			= QueryUtil.getResult(sbQuery.toString(), null);
		while(result.next()){
			Info info = new Info();
			info.set(FieldNames.SCHEDULE_ID, result.getString("SCHEDULE_ID"));
			info.set(FieldNames.AGENT_NO, result.getString("AGENT_NO"));			
			info.set(FieldNames.AGENT_ID, result.getString("AGENT_ID"));
			info.set(FieldNames.ORDER_NO, result.getString("ORDER_NO"));
			info.set(FieldNames.PICK, result.getString("PICK"));
			info.set(FieldNames.FIRST_NAME, result.getString("USER_FIRST_NAME"));
			info.set(FieldNames.LAST_NAME, result.getString("USER_LAST_NAME"));
			info.set(FieldNames.USER_TYPE, result.getString("USER_TYPE"));
			info.set("ASSOCIATEDLEADS", result.getString("LEADS"));
			
			sMap.put(result.getString("AGENT_NO"), info);
		}
	}
	catch(Exception e){
		logger.info("exception in getLeadAgentsMap() ");
	}
	finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return sMap;
   }
	/*----------------------------------------Moved From BaseNewPortalUtils--------------------------------------------------*/
	public  int isDefaultLeadOwner(String userNo) {
	    int count = 0;
	    StringBuffer leadOwnerQuery = new StringBuffer(
	            "SELECT COUNT(1) FROM FS_AREA_OWNERS WHERE AREA_ID = -1 AND USER_NO=");
	    leadOwnerQuery.append(userNo);
	    try {
	        ResultSet result = QueryUtil.getResult(leadOwnerQuery.toString(),
	                new Object[]{});
	
	        if (result.next()) {
	            count = Integer.parseInt(result.getString(1));
	        }
	    } catch (Exception e) {
	        logger.error("Error In getTaskCount()");
	    }
	    return count;
	}

	/*public static String setOwnerForNewLead(String regionId) {
		return setOwnerForNewLead(regionId, "NA");
	}
	public static String setOwnerForNewLead(String regionId, String sourceId) {
		String ownerId= null;


		StringBuffer strQuery=null;
		StringBuffer query=null;
		StringBuffer strQueryUpdate=null;

		int maxOrder=0;
		int minOrder=0;
		int orderNo=0;
		int orderNo1=0;
		ResultSet result = null;
		ResultSet resultForMax = null;
		ResultSet result1 = null;

		try{
			// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
			StringBuffer queryForMax = new StringBuffer("SELECT MAX(ORDER_NO) as ORDER_NO,MIN(ORDER_NO) AS MIN_ORDER_NO FROM FS_SCHEDULE_LEAD_OWNER F LEFT JOIN USERS U ON F.USER_NO=U.USER_NO WHERE ");
			queryForMax.append("F.REGION_ID='").append(regionId).append("' AND F.SOURCE_ID='").append(sourceId).append("' and U.STATUS=1");
			// Y.NagaRaja comments ends here
			resultForMax = QueryUtil.getResult(queryForMax.toString(), new Object[]{});
			if(resultForMax.next()){

				maxOrder =Integer.parseInt(resultForMax.getString("ORDER_NO"));
				minOrder=Integer.parseInt(resultForMax.getString("MIN_ORDER_NO"));

			}

			// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
			query= new StringBuffer("SELECT F.USER_NO,ORDER_NO,PICK FROM FS_SCHEDULE_LEAD_OWNER F LEFT JOIN USERS U ON F.USER_NO=U.USER_NO WHERE ");
			query.append("F.REGION_ID='").append(regionId).append("' AND F.SOURCE_ID='").append(sourceId).append("' and PICK='true' AND  U.STATUS=1");
			// Y.NagaRaja comments ends here
			result = QueryUtil.getResult(query.toString(), null);


			if (result.next()){
				ownerId = result.getString("USER_NO");
				orderNo = Integer.parseInt(result.getString("ORDER_NO"));
			} else{
				query=  new StringBuffer("SELECT F.USER_NO,ORDER_NO,PICK FROM FS_SCHEDULE_LEAD_OWNER F LEFT JOIN USERS U ON F.USER_NO=U.USER_NO WHERE ");
				query.append(" F.REGION_ID='").append(regionId).append("' AND F.SOURCE_ID='").append(sourceId).append("' and U.STATUS=1 and ORDER_NO=").append(minOrder);


				result = QueryUtil.getResult(query.toString(), null);
				if (result.next()){
					ownerId = result.getString("USER_NO");
					orderNo = Integer.parseInt(result.getString("ORDER_NO"));
				}
			}

			// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
			strQuery=  new StringBuffer("UPDATE FS_SCHEDULE_LEAD_OWNER SET PICK = 'false' WHERE");
			strQuery.append(" REGION_ID='").append(regionId).append("' AND SOURCE_ID='").append(sourceId).append("' and  ORDER_NO=").append(orderNo);
			// Y.NagaRaja comments ends here


			QueryUtil.update(strQuery.toString(), new String[]{});
			// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
			if(orderNo==maxOrder){
				strQueryUpdate= new StringBuffer("UPDATE FS_SCHEDULE_LEAD_OWNER SET PICK = 'true' WHERE");
				strQueryUpdate.append(" REGION_ID='").append(regionId).append("' AND SOURCE_ID='").append(sourceId).append("' and  ORDER_NO=").append(minOrder);
				// Y.NagaRaja comments ends here
			} else {
				strQuery = new StringBuffer("SELECT MIN(ORDER_NO) AS ORDER_NO FROM FS_SCHEDULE_LEAD_OWNER F LEFT JOIN USERS U ON F.USER_NO=U.USER_NO WHERE  ");
				strQuery.append(" F.REGION_ID='").append(regionId).append("' AND F.SOURCE_ID='").append(sourceId).append("' and U.STATUS=1 and ORDER_NO>").append(orderNo);

				result1 = QueryUtil.getResult(strQuery.toString(), new Object[]{});
				if(result1.next()){
					orderNo1 =Integer.parseInt(result1.getString("ORDER_NO"));
				}
				// P_FS_E_RRIR -- Added by Y.NagaRaja for Round Robbin in Regions 
				strQueryUpdate	=  new StringBuffer("UPDATE FS_SCHEDULE_LEAD_OWNER SET PICK = 'true' WHERE  ");
				strQueryUpdate.append(" REGION_ID='").append(regionId).append("' AND SOURCE_ID='").append(sourceId).append("'  and ORDER_NO=").append(orderNo1);
				// Y.NagaRaja comments ends here
			}
			QueryUtil.update(strQueryUpdate.toString(), new String[]{});

			// Added by Abishek Singhal 30 Oct 2006 Start for Bugzilla Bug 20745            
			if (ownerId == null || ownerId.equals("-1")) {

				 Query the DB for the default owner	
				//as default area id chanfed -1 to 1
				query					= new StringBuffer("SELECT USER_NO FROM FS_AREA_OWNERS A WHERE A.AREA_ID=1");
				result			= QueryUtil.getResult(query.toString(), null);
				if(result.next()){
					ownerId = result.getString(1);
				}
			}
			// Added by Abishek Singhal 30 oct 2006 End  for Bugzilla Bug 20745

			if (ownerId == null || ownerId.equals("-1")) {
				//as default area id chanfed -1 to 1
				query= new StringBuffer("SELECT  FO.USER_NO FROM FS_AREA_OWNERS FO , USERS U WHERE FO.AREA_ID='1' AND  FO.USER_NO=U.USER_NO AND U.STATUS=1"); 

				result			= QueryUtil.getResult(query.toString(), null);
				if(result.next()){
					ownerId = result.getString(1);
				}else {
					ownerId = "1";	// Hard coded
				}
			}

		}catch(Exception e){
			logger.error("Exception while setOwnerForNewLead in Lead Bean:: " +e, e);
		}//end catch
		finally{
			try{
				if(result != null){
					result = null;
				}
				if(resultForMax != null){
					resultForMax = null;
				}
				if(result1 != null){
					result1 = null;
				}
			}catch(Exception e){
				logger.error(e);
			}
		}

		return ownerId;
	}*/
	
	public void updateRoundRobinMapping(String userToDelete) {
		String selectQuery = "SELECT ORDER_NO, REGION_ID, SOURCE_ID FROM FS_SCHEDULE_LEAD_OWNER WHERE USER_NO IN("+userToDelete+")"; //P_FS_Enh_AssignmentBySourceDetail
		Map<String,List<String[]>> batchMap = BaseUtils.getNewHashMapWithKeyValueType();
		ResultSet result = null;
		try {
			result = QueryUtil.getResult(selectQuery, null);
    		if(result != null) {
    			while(result.next()) {
    				//String[] params = new String[2];
    				String[] params = new String[3]; //P_FS_Enh_AssignmentBySourceDetail
		            params[0] = result.getString("REGION_ID");
		            params[1] = result.getString("ORDER_NO");
		            params[2] = result.getString("SOURCE_ID"); //P_FS_Enh_AssignmentBySourceDetail
		            
		            if(batchMap.containsKey(FieldNames.ZERO)) {
		    			(batchMap.get(FieldNames.ZERO)).add(params);
		    		}
		    		else {
		    			List<String[]> paramList = new ArrayList<String[]>();
		    			paramList.add(params);
		    			batchMap.put(FieldNames.ZERO, paramList);
		    		}
    			}
    		}
    		if(batchMap != null && batchMap.size() > 0) {
    			String updateQuery = "UPDATE FS_SCHEDULE_LEAD_OWNER SET ORDER_NO = ORDER_NO-1 WHERE ORDER_NO > ? AND  REGION_ID = ? AND SOURCE_ID = ?"; //P_FS_Enh_AssignmentBySourceDetail
    			QueryUtil.preparedStatementsBatchUpdate(new String[]{updateQuery}, batchMap);
    			String deleteQuery = "DELETE FROM FS_SCHEDULE_LEAD_OWNER WHERE USER_NO IN("+userToDelete+")";
    			QueryUtil.update(deleteQuery, null);
    		}
		} catch(Exception e) {
			
		}
	}
}