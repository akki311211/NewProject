package com.home.builderforms;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.sqlqueries.*;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 *  The FsLeadStatusDAO for the FS_LEAD_STATUS table
 *
 *@author     Pritish
 *@created    Wed Jul 21 15:46:05 PDT 2004
 * ---------------------------------------------------------------------------------------------------
Version No.			Date		  By	                    Comments
----------------------------------------------------------------------------------------------------
P_ADM_TASK_1                   5APRIL2007        YADUSHRI       to add a new field "Final status"  
P_E_admin_fs_unregisterstate   24 Sep,2008   sanjeev k     for view unregter state on admin side on clicl unregisterstate link under fs
BB63_P_E_StatusCategory      24/12/2009      Ganesh      Categorizing each status under 
P_BB_IHC_20101018_504          20Sep 2011      Akhil Gupta    Changes have been made to add feature status to be excluded from heatmeter
BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object  
----------------------------------------------------------------------------------------------------
 
 */
public class FsLeadStatusDAO extends BaseDAO {
	static	Logger logger					= Logger.getLogger(FsLeadStatusDAO.class);


	
	/**
	*  Constructor for the LeadStatusDAO object
	*/
    public void setUserNo (String userno){
    }
    
    public String getUserNo (){
    	return "1";
    }
    
    public void setUserLevel (String userlevel){
    }
    
    public String getUserLevel (){
    	return "0";
    }
    public void setAreaId (String areaid){
    	
    }
    
    public String getAreaId (){
    	return null;
    }
	
    
    public FsLeadStatusDAO() {
		this.tableAnchor = "fsLeadStatus";

	}
	






	
	public SequenceMap getIdStatusInfo() {
		return getIdStatusInfo(null);
	}
	
	public SequenceMap getIdStatusInfo(String leadStatusCategory) {

		SequenceMap sMap = new SequenceMap();
		 ResultSet result=null;
		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME,IS_FINAL FROM FS_LEAD_STATUS";
		if(StringUtil.isValidNew(leadStatusCategory))
		{
			 query = query+"  WHERE STATUS_CATEGORY IN ("+leadStatusCategory+")";
		}
			query= query+"  ORDER BY ORDER_NO";
			
		try{
		 result			= QueryUtil.getResult(query,null);
			while(result.next()){
				
				sMap.put(result.getString("LEAD_STATUS_ID"),result.getString("LEAD_STATUS_NAME"));
				
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}
    public SequenceMap getCallData(String fromDate , String toDate,String conntacted,String source, String userNo, String userLevel, String areaID, boolean viewAllLeads) {//P_FS_B_6065 Nishant //CODEBASE_ISSUE IN_90010_13FEB15
    	return getCallData( fromDate ,  toDate, conntacted, source,  userNo,  userLevel,  areaID,  viewAllLeads, null);
    }
    public SequenceMap getCallData(String fromDate , String toDate,String conntacted,String source, String userNo, String userLevel, String areaID, boolean viewAllLeads, String coApplicantAsLead) {//P_FS_B_6065 Nishant //CODEBASE_ISSUE IN_90010_13FEB15
    	return getCallData( fromDate ,  toDate, conntacted, source,  userNo,  userLevel,  areaID,  viewAllLeads, coApplicantAsLead, null);
    }
    public SequenceMap getCallData(String fromDate , String toDate,String conntacted,String source, String userNo, String userLevel, String areaID, boolean viewAllLeads, String coApplicantAsLead, String division_Ids) {//P_ENH_COAPPLICANT_AS_LEAD
		SequenceMap sMap = new SequenceMap();
		String divisionIds=(String)StrutsUtil.getHttpSession().getAttribute("divisionIds");
		StringBuffer selectQuery;
		StringBuffer selectQuery1;
		ResultSet result=null;
		ResultSet result1=null;;
		fromDate = fromDate+" 00:00:00";//P_FS_B_6069 Nishant
		toDate = toDate +" 23:59:00";
		
		if(conntacted!=null && "notContacted".equals(conntacted)){
			selectQuery = new StringBuffer("SELECT LEAD_OWNER_ID , COUNT(LEAD_ID) AS COUNT FROM FS_LEAD_DETAILS WHERE LEAD_ID NOT IN (SELECT DISTINCT(LEAD_ID) FROM FS_LEAD_CALL");		
			selectQuery.append(" WHERE DATE >='").append(fromDate).append("' and DATE <= '").append(toDate ).append("')");

			selectQuery1 =  new StringBuffer("SELECT LEAD_OWNER_ID , COUNT(LEAD_ID) AS COUNT FROM FS_LEAD_DETAILS WHERE");
			selectQuery1.append(" REQUEST_DATE >='").append(fromDate).append("' and REQUEST_DATE <= '").append(toDate ).append("'");
			selectQuery1.append(" AND LEAD_ID NOT IN (SELECT DISTINCT(LEAD_ID) FROM FS_LEAD_CALL)");
			if(true && "true".equals(coApplicantAsLead))			//P_ENH_COAPPLICANT_AS_LEAD starts
			{
				selectQuery.append(" AND (COAPPLICANT_TYPE <> 'C' OR COAPPLICANT_TYPE IS NULL) ");
				selectQuery1.append(" AND (COAPPLICANT_TYPE <> 'C' OR COAPPLICANT_TYPE IS NULL) ");
			}
			//P_ENH_COAPPLICANT_AS_LEAD ends
			/*if(true && StringUtil.isValidNew(division_Ids))
			{
				String leadIds = DivisionUtil.getAllDivisionLeadIds(division_Ids);
				if(StringUtil.isValid(leadIds)){
					selectQuery.append(" AND LEAD_ID IN ("+leadIds+") ");
					selectQuery1.append(" AND LEAD_ID IN ("+leadIds+") ");
				}
			}*/
            //CODEBASE_ISSUE IN_90010_13FEB15 starts
            if(viewAllLeads) {
                if ("2".equals(userLevel)) {
                    selectQuery1.append(" AND ( AREA_ID =").append(areaID).append(" OR LEAD_OWNER_ID IN (").append(NewPortalUtils.getRegionalOwnersMap(userNo)).append("))");
                    selectQuery.append(" AND ( AREA_ID =").append(areaID).append(" OR LEAD_OWNER_ID IN (").append(NewPortalUtils.getRegionalOwnersMap(userNo)).append("))");
                }
            } else {
                selectQuery1.append(" AND OR LEAD_OWNER_ID = (").append(userNo).append(")");
                selectQuery.append(" AND OR LEAD_OWNER_ID = (").append(userNo).append(")");
            }
            
            if(viewAllLeads) {
            	/*if (Constants.USER_LEVEL_DIVISION.equals(userLevel)) {
            		selectQuery1.append(" AND ( ");
            		if(StringUtil.isValid(DivisionUtil.getAllDivisionLeadIds(divisionIds))){
            			selectQuery1.append("  FS_LEAD_DETAILS.LEAD_ID IN ( ").append(DivisionUtil.getAllDivisionLeadIds(divisionIds)).append(" ) OR ");
            		}
            		selectQuery1.append(" LEAD_OWNER_ID IN (").append(NewPortalUtils.getRegionalOwnersMap(userNo)).append("))");

            		selectQuery.append(" AND ( ");
            		if(StringUtil.isValid(DivisionUtil.getAllDivisionLeadIds(divisionIds))){
            			selectQuery.append(" FS_LEAD_DETAILS.LEAD_ID IN ( ").append(DivisionUtil.getAllDivisionLeadIds(divisionIds)).append(" ) OR " ); 
            		}
            		selectQuery.append(" LEAD_OWNER_ID IN (").append(NewPortalUtils.getRegionalOwnersMap(userNo)).append("))");

            	}*/
            } else {
                selectQuery1.append(" AND  LEAD_OWNER_ID = (").append(userNo).append(")");
                selectQuery.append(" AND  LEAD_OWNER_ID = (").append(userNo).append(")");
            }
            //CODEBASE_ISSUE IN_90010_13FEB15 ends
			//P_FS_B_6065 Nishant starts
			if(StringUtil.isValidNew(source)){
				selectQuery1.append(" AND ( LEAD_SOURCE2_ID IN (");
				selectQuery1.append(source);
				selectQuery1.append(")OR  SOURCE_CONTACT_TYPE IN (");
				selectQuery1.append(source).append("))");
			}
			
			if(StringUtil.isValidNew(source)){
				selectQuery.append(" AND ( LEAD_SOURCE2_ID IN (");
				selectQuery.append(source);
				selectQuery.append(")OR  SOURCE_CONTACT_TYPE IN (");
				selectQuery.append(source).append("))");
			}
			//P_FS_B_6065 ends	
			
			selectQuery1.append(" GROUP BY LEAD_OWNER_ID");
			selectQuery.append(" GROUP BY LEAD_OWNER_ID");			 
			try{
				result = QueryUtil.getResult(selectQuery.toString(), null);
				result1 = QueryUtil.getResult(selectQuery1.toString(), new Object[]{});
				while(result.next()){
					sMap.put(result.getString("LEAD_OWNER_ID"),result.getString("COUNT"));
				}
				while(result1.next()){
					sMap.put(result1.getString("LEAD_OWNER_ID")+"date",result1.getString("COUNT"));
				}
			}catch(Exception e){
				logger.info("exception in getCallData");
			}
			finally
			{
				QueryUtil.releaseResultSet(result);
				QueryUtil.releaseResultSet(result1);
			}

		}else{
			
		String query = "SELECT LOGGED_BY_ID,COUNT(distinct(CALL_ID)) AS CALLDATA,COUNT(DISTINCT(FS_LEAD_CALL.LEAD_ID)) AS LEAD from FS_LEAD_CALL ,FS_LEAD_DETAILS WHERE FS_LEAD_CALL.LEAD_ID=FS_LEAD_DETAILS.LEAD_ID";
                if(StringUtil.isValid(conntacted)){
                 query = query + " AND FS_LEAD_CALL.CALL_STATUS IN (0,1) ";  
                }
                
                //P_FS_B_6065 Nishant starts
                if(StringUtil.isValidNew(source)){
                	query = query +" AND ( LEAD_SOURCE2_ID IN ("+source+")";    				
                	query = query +"OR  SOURCE_CONTACT_TYPE IN ("+source+"))";
    			}
                //P_FS_B_6065 ends
            //CODEBASE_ISSUE IN_90010_13FEB15 starts
            if(viewAllLeads) {
                if ("2".equals(userLevel)) {
                    query = query + (" AND ( AREA_ID =" + areaID + " OR LEAD_OWNER_ID IN (" + NewPortalUtils.getRegionalOwnersMap(userNo) + ") )");
                }
            }else {
                query = query + (" AND  LEAD_OWNER_ID = " + userNo );
            }
            //CODEBASE_ISSUE IN_90010_13FEB15 ends
                
            if(true && "true".equals(coApplicantAsLead))		//P_ENH_COAPPLICANT_AS_LEAD starts
            {
            	query = query +" AND (COAPPLICANT_TYPE <> 'C' OR COAPPLICANT_TYPE IS NULL) ";
            }				//P_ENH_COAPPLICANT_AS_LEAD ends
            
            /*if(true && StringUtil.isValidNew(division_Ids))
			{
				String leadIds = DivisionUtil.getAllDivisionLeadIds(division_Ids);
				if(StringUtil.isValid(leadIds)){
					query = query +" AND LEAD_ID IN ("+leadIds+") ";
				}
			}*/
            
                query = query +" AND  FS_LEAD_CALL.DATE>= '"+fromDate+"' AND FS_LEAD_CALL.DATE<='"+toDate+"'  GROUP BY LOGGED_BY_ID ORDER BY LOGGED_BY_ID";
                
                try{
			result			= QueryUtil.getResult(query,null);
			while(result.next()){
				if(StringUtil.isValid(conntacted))
				sMap.put(result.getString("LOGGED_BY_ID"),result.getString("LEAD"));
                                else{
                                    Info info = new Info();
                                    info.set(FieldNames.LEAD, result.getString("LEAD"));
				info.set("CALLDATA", result.getString("CALLDATA"));
				sMap.put(result.getString("LOGGED_BY_ID"),info);
                                }
				
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
				
		}
		}
		return sMap;
	}
	// change made by Niraj Sachan Bud ID 29139 date 14 nov 2007 for ARCHIVE_LEADS
	public  SequenceMap getStatusMap() {
		SequenceMap sMap = new SequenceMap();	
			//BB63_P_E_StatusCategory
		//String query = "SELECT A.LEAD_STATUS_ID,A.LEAD_STATUS_NAME,A.ORDER_NO,COUNT(LD.LEAD_STATUS_ID) 'LEADS' FROM FS_LEAD_STATUS A  LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_STATUS_ID = LD.LEAD_STATUS_ID GROUP BY A.LEAD_STATUS_ID ORDER BY ORDER_NO";
		//UPS_UPGRADE  	   String query = "SELECT A.STATUS_CATEGORY,A.LEAD_STATUS_ID,A.LEAD_STATUS_NAME,A.ORDER_NO,COUNT(LD.LEAD_STATUS_ID) 'LEADS' ,COUNT(LDA.LEAD_STATUS_ID) 'ARCHIVE_LEADS' FROM FS_LEAD_STATUS A  LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_STATUS_ID = LD.LEAD_STATUS_ID  LEFT JOIN FS_LEAD_DETAILS_ARCHIVE LDA ON A.LEAD_STATUS_ID=LDA.LEAD_STATUS_ID GROUP BY A.LEAD_STATUS_ID ORDER BY ORDER_NO";
		String query = "SELECT A.STATUS_CATEGORY,A.LEAD_STATUS_ID,A.LEAD_STATUS_NAME,A.ORDER_NO,COUNT(LD.LEAD_STATUS_ID) 'LEADS'  FROM FS_LEAD_STATUS A  LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_STATUS_ID = LD.LEAD_STATUS_ID   GROUP BY A.LEAD_STATUS_ID ORDER BY ORDER_NO";
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			while(result.next()){
				Info info = new Info();
				info.set(FieldNames.STATUS_CATEGORY, result.getString("STATUS_CATEGORY"));
				info.set(FieldNames.LEAD_STATUS_ID, result.getString("LEAD_STATUS_ID"));			
				info.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));
				if(result.getString("LEADS")!=null && !result.getString("LEADS").equals("0")){
					info.set("ASSOCIATEDLEADS", result.getString("LEADS"));
				}/*else if(result.getString("ARCHIVE_LEADS")!=null && !result.getString("ARCHIVE_LEADS").equals("0")){
					info.set("ASSOCIATEDLEADS", result.getString("ARCHIVE_LEADS"));
				}*/else{
					info.set("ASSOCIATEDLEADS", "0");
				}
				//info.set("ASSOCIATEDLEADS", result.getString("LEADS"));
				sMap.put(result.getString("LEAD_STATUS_ID"), info);
				
			}
		}
		catch(Exception e){
			logger.info("exception in getStatusMap() ");
		}finally
		{
			QueryUtil.releaseResultSet(result);
	}

		
		
		query = "SELECT A.STATUS_CATEGORY,A.LEAD_STATUS_ID,A.LEAD_STATUS_NAME,A.ORDER_NO ,COUNT(LDA.LEAD_STATUS_ID) 'ARCHIVE_LEADS' FROM FS_LEAD_STATUS A    LEFT JOIN FS_LEAD_DETAILS_ARCHIVE LDA ON A.LEAD_STATUS_ID=LDA.LEAD_STATUS_ID GROUP BY A.LEAD_STATUS_ID ORDER BY ORDER_NO";
		
	       //UPS_UPGRADE modification starts.
		
			try{
			 result			= QueryUtil.getResult(query,null);
			   Info modInfo=new Info();
			   int associatedLeads=0;
			   while(result.next()){
					modInfo=(Info)sMap.get(result.getString("LEAD_STATUS_ID"));
					if(modInfo!=null)
					{
						 try
						 {
							  associatedLeads=Integer.parseInt(modInfo.get("ASSOCIATEDLEADS"));
							  if(associatedLeads<=0)
							  {
								  if(result.getString("ARCHIVE_LEADS")!=null && !result.getString("ARCHIVE_LEADS").equals("0"))
								  {
									  modInfo.set("ASSOCIATEDLEADS",result.getString("ARCHIVE_LEADS"));
									  sMap.put(result.getString("LEAD_STATUS_ID"), modInfo);
								  }
							  }
						 }catch(Exception exce)
						 {
							 logger.error(exce);
						 }
					}
				}
			}
			catch(Exception e){
				logger.info("exception in getStatusMap() ");
			}
			finally
			{
				QueryUtil.releaseResultSet(result);
			}

			//UPS_UPGRADE modification ends.
		return sMap;
	}
       //Function added by YADUSHRI for P_ADM_TASK_1
        public HashMap getFinalStatus() {

		HashMap sMap = new HashMap();
		String query = "SELECT LEAD_STATUS_ID , LEAD_STATUS_NAME FROM FS_LEAD_STATUS WHERE IS_FINAL='1'";
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			while(result.next()){
				
				sMap.put(result.getString("LEAD_STATUS_ID"),result.getString("LEAD_STATUS_NAME"));
				
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}  

      //P_B_FS_58151 starts
        public Info getDefaultWebFormValues() {

            Info info = new Info();
            String query = "SELECT CS.LEAD_STATUS_ID , FLS.LEAD_STATUS_NAME FROM CAMPAIGN_STATUS CS LEFT JOIN FS_LEAD_STATUS FLS ON FLS.LEAD_STATUS_ID = CS.LEAD_STATUS_ID";
            ResultSet result=null;
            try{
                result			= QueryUtil.getResult(query,null);
                while(result.next()){
                    info.set("LEAD_STATUS_ID",result.getString("LEAD_STATUS_NAME"));
                }
            }
            catch(Exception e){
            }
            finally
            {
                QueryUtil.releaseResultSet(result);
            }

            return info;
        }

        public Info getDefaultWebFormSource() {

            Info info = new Info();
            String query = "SELECT CS.LEAD_SOURCE2_ID , FLS.LEAD_SOURCE2_NAME FROM CAMPAIGN_STATUS CS LEFT JOIN FS_LEAD_SOURCE2 FLS ON FLS.LEAD_SOURCE2_ID  = CS.LEAD_SOURCE2_ID";
            ResultSet result=null;
            try{
                result			= QueryUtil.getResult(query,null);
                while(result.next()){
                    info.set("LEAD_SOURCE2_ID",result.getString("LEAD_SOURCE2_NAME"));
                }
            }
            catch(Exception e){
            }
            finally
            {
                QueryUtil.releaseResultSet(result);
            }

            return info;
        }
        public Info getDefaultWebFormSourceDetails() {

            Info info = new Info();
            String query = "SELECT CS.LEAD_SOURCE3_ID , FLS.LEAD_SOURCE3_NAME FROM CAMPAIGN_STATUS CS LEFT JOIN FS_LEAD_SOURCE3 FLS ON FLS.LEAD_SOURCE3_ID  = CS.LEAD_SOURCE3_ID ";
            ResultSet result=null;
            try{
                result			= QueryUtil.getResult(query,null);
                while(result.next()){
                    info.set("LEAD_SOURCE3_ID",result.getString("LEAD_SOURCE3_NAME"));
                }
            }
            catch(Exception e){
            }
            finally
            {
                QueryUtil.releaseResultSet(result);
            }

            return info;
        }


        //P_B_FS_58151 ends
	public void createStatusMap(SequenceMap sMap) {

		//P_FS_E_2510201001 added condition to remove closed lead status from sales funnel by vikram

		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
		ResultSet result = null;

		try{


			result = QueryUtil.getResult(query, null);
			Info info = null;

			while(result.next()){
				info = new Info();
				info.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));
				sMap.put(result.getString("LEAD_STATUS_ID"), info);
			}
		}
		catch(Exception e){
			logger.error("Exception in createStatusMap", e);
		} 
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

	}

	public Info getStatusInfo() {

		Info info = new Info();
		String query = "SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME ,IS_FINAL FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			while(result.next()){
				
				info.set(result.getString("LEAD_STATUS_ID"),result.getString("LEAD_STATUS_NAME"));
				
			}
		}
		catch(Exception e){e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return info;
	}
	/*public void updateStatusFromTrigger(ArrayList leadIdList, String fromWhere,String fieldId)
	{
		if(leadIdList != null && leadIdList.size()>0)
		{
			String leadId = "";
			String value = "";
			for(int i=0; i<leadIdList.size(); i++)
			{
				leadId = (String)leadIdList.get(i);
				NewPortalUtils.executeTrigger(leadId,"1",fromWhere,fieldId,null);
			}
		}
	}*/
	
	
	public String getStatusForUnregisteredStates() {

		String StatusForUnregisteredStates = null;
		String query = "SELECT LEAD_STATUS_ID FROM FS_LEAD_STATUS WHERE FOR_UNREGISTERED_STATES ='1'";
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			if(result.next()){

				StatusForUnregisteredStates = result.getString("LEAD_STATUS_ID");
			}
		}
		catch(Exception e){e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return StatusForUnregisteredStates;
	}

	//P_E_admin_fs_unregisterstate 
	/**
	 * @author sanjeev
	 * @return String
	 * Des:- return a name of status asscited with unregistered state
	 */
	public String getStatusNameForUnregisteredStates() {

		String StatusForUnregisteredStates = "";
		String query = "SELECT LEAD_STATUS_NAME FROM FS_LEAD_STATUS WHERE FOR_UNREGISTERED_STATES ='1'";
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			if(result.next()){

				StatusForUnregisteredStates = result.getString("LEAD_STATUS_NAME");
			}
		}
		catch(Exception e){e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return StatusForUnregisteredStates;
	}
	//P_E_admin_fs_unregisterstate end
	
	public boolean updateUnregisteredState(String statusId){

/* Set the status of all the states to registered and then update unregistered state */
	String sQuery1 = null;
	String sQuery2 = null;
	ResultSet result		= null;
	int length;
	try{
		sQuery1 = "UPDATE FS_LEAD_STATUS SET FOR_UNREGISTERED_STATES = '0'";
		sQuery2 = "UPDATE FS_LEAD_STATUS SET FOR_UNREGISTERED_STATES = '1' WHERE LEAD_STATUS_ID = ?";
		if(StringUtil.isValid(statusId)){
		
		length	= QueryUtil.update(sQuery1, new String[]{});
		length = QueryUtil.update(sQuery2, new String[]{statusId});

		}
	}catch(Exception e){e.printStackTrace(); return false;}
	return true;
}
	
	public String getMaxOrderNo() {
		String query = "SELECT MAX(ORDER_NO) 'maxOrderNo' FROM  FS_LEAD_STATUS";
		String maxOrderNo=null;
		 ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			if(result.next()){
				maxOrderNo= result.getString("maxOrderNo");

			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return maxOrderNo;
	}
        
        
        //P_BB_IHC_20101018_504 Starts
        /*
        **Method is created to select those values whose lead status are exluded from heat meter**
        **@author:Akhil Gupta
        **@toShow:parameter denfines wether to get all status or only excluded status 
        **@return:Sequence Map is returned.          
        */
        public SequenceMap getExcludedStatus(String toShow){
		
                String query=null;
                ResultSet result=null;
                SequenceMap sMap = new SequenceMap();
                
                if("all".equals(toShow)){
		query = "SELECT LEAD_STATUS_ID ,LEAD_STATUS_NAME,IS_EXCLUDED FROM FS_LEAD_STATUS";		
                }else{
                 query = "SELECT LEAD_STATUS_ID ,LEAD_STATUS_NAME,IS_EXCLUDED FROM FS_LEAD_STATUS WHERE IS_EXCLUDED='1'";
                }
		try{
		result	= QueryUtil.getResult(query,null);
                    while(result.next()){

                            Info info = new Info();
                            info.set(FieldNames.LEAD_STATUS_ID, result.getString("LEAD_STATUS_ID"));			
                            info.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));
                            info.set(FieldNames.IS_EXCLUDED, result.getString("IS_EXCLUDED"));
                            sMap.put(result.getString("LEAD_STATUS_ID"), info);
                    }
		}
		catch(Exception e){
		}finally{
			QueryUtil.releaseResultSet(result);			
              }
		return sMap;
     }

		
        
    //P_BB_IHC_20101018_504 Ends     

        	
        /*-------------------------------------------Moved From BaseNewPortalUtils-------------------------------------------------------*/
        
        public SequenceMap getAllStatus() {
        	return SQLUtil.getSequenceMapFromQuery("LEAD_STATUS_ID", "LEAD_STATUS_NAME", 
        			"SELECT LEAD_STATUS_ID, LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO");
        }
        
        
        /**
         * P_B_23606
         * @author Naman Jain
         * @desc for the lead status
         * @return
         */
        public Info getLeadStatus() {
        	String query = "SELECT LEAD_STATUS_ID,LEAD_STATUS_NAME FROM FS_LEAD_STATUS WHERE LEAD_STATUS_ID  ORDER BY ORDER_NO;";
        	return SQLUtil.getInfoFromQuery("LEAD_STATUS_ID", "LEAD_STATUS_NAME", query,true);
        }
        /**
         * @author sanjeev
         * @param statusCategory
         * @return des: for status selected value
         */
        public SequenceMap getLeadStatus(String statusCategory) {
            SequenceMap sequenceMap = null;
            String query = null;
            ResultSet result = null;
            try {
                sequenceMap = new SequenceMap();
                query = "SELECT LEAD_STATUS_ID,LEAD_STATUS_NAME FROM FS_LEAD_STATUS";//fs_category

                if (StringUtil.isValidNew(statusCategory)) {
                    query = query + " WHERE STATUS_CATEGORY IN ("
                            + statusCategory + ")";
                }
                query = query + " ORDER BY ORDER_NO";
                result = QueryUtil.getResult(query,null);
                sequenceMap.put("-1", "Select Status");
                while (result.next()) {
                    sequenceMap.put(result.getString("LEAD_STATUS_ID"),
                            result.getString("LEAD_STATUS_NAME"));
                }// end while
            } catch (Exception e) {
                logger.info("ERROR: exception in getLeadOwners ::" + e);
            } finally {
                if (result != null) {
                    result = null;
                }
            }
            return sequenceMap;
        }
        /**
         * This method returns the info object of All Lead Status @ start @ added by
         * Rajeev Varshney @ 16/12/2005
         */
        public Info getNewLeadStatus() {
            Info info = null;
            ResultSet result = null;
            try {
                info = new Info();
                String query = "SELECT LEAD_STATUS_ID,LEAD_STATUS_NAME FROM FS_LEAD_STATUS ORDER BY ORDER_NO";
                result = QueryUtil.getResult(query,null);
                while (result.next()) {
                    if (result.getString("LEAD_STATUS_ID") != null
                            && ("3".equals(result.getString("LEAD_STATUS_ID"))
                            || "2".equals(result.getString("LEAD_STATUS_ID")) || "1".equals(result.getString("LEAD_STATUS_ID")))) {
                        info.set(result.getString("LEAD_STATUS_ID"), LanguageUtil.getString(result.getString("LEAD_STATUS_NAME")));
                    } else {
                        info.set(result.getString("LEAD_STATUS_ID"),
                                result.getString("LEAD_STATUS_NAME"));
                    }
                }// end while
            } catch (Exception e) {
                logger.info("ERROR: exception in getLeadStatus ::" + e);
            } finally {
                if (result != null) {
                    result = null;
                }
            }
            return info;
        }
        
        public String getLeadStatusName(String statusId)
    			throws ConnectionException {
    		Connection con = null;
    		String statusName = "Not Available";
    		Statement stmt = null;
    		java.sql.ResultSet rs = null;

    		try {
    			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
    			stmt = con.createStatement();

    			rs = stmt
    					.executeQuery("SELECT LEAD_STATUS_NAME FROM FS_LEAD_STATUS WHERE LEAD_STATUS_ID="
    							+ statusId);
    			if (rs.next()) {
    				statusName = LanguageUtil.getString(rs.getString("LEAD_STATUS_NAME"));
    			}
    			return statusName;
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
    				System.out.println("The final1 exception is " + e);
    			}
    			try {
    				if (stmt != null)
    					stmt.close();
    			} catch (Exception e) {
    				System.out.println("The fianl2 exception is " + e);
    			}
    			try {
    				if (con != null)
    					DBConnectionManager.getInstance().freeConnection(con);
    			} catch (Exception e) {
    				logger.error("Exception ", e);
    			}
    		}

    	}
        
        public HashMap getStatusLead(String fromDate,String toDate) 
        {
     	    HashMap statusMap=new HashMap();
    			System.out.println("fromDatefromDate::"+fromDate);
    			System.out.println("toDatetoDate::::"+toDate);
    			String query = "SELECT COUNT(LEAD_ID) AS COUNT ,FLSC.STATUS_CATEGORY_ID FROM FS_LEAD_DETAILS FLD LEFT JOIN FS_LEAD_STATUS FLS ON FLS.LEAD_STATUS_ID = FLD.LEAD_STATUS_ID LEFT JOIN FS_LEAD_STATUS_CATEGORY FLSC ON   FLSC.STATUS_CATEGORY_ID=FLS.STATUS_CATEGORY WHERE 1=1 AND REQUEST_DATE >=? AND REQUEST_DATE <=? GROUP BY FLSC.STATUS_CATEGORY_ID";
    			com.home.builderforms.sqlqueries.ResultSet result = QueryUtil.getResult(query, new Object[] {fromDate,toDate});
    			while(result.next()) 
    			{
    				statusMap.put(result.getString("STATUS_CATEGORY_ID"),result.getString("COUNT"));
    			}
    			return statusMap;
    	  }
}
