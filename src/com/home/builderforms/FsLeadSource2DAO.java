package com.home.builderforms;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.home.builderforms.AdminMgr;
import com.home.builderforms.MasterDataMgr;
import com.home.builderforms.BaseDAO;
import com.home.builderforms.AppException;
import com.home.builderforms.sqlqueries.*;

import org.apache.log4j.Logger;


/**
 *  The FsLeadSource2DAO for the FS_LEAD_SOURCE2 table
 *
 *@author  Rajiv    
 *@created    Wed Jul 21 15:46:05 PDT 2004
 *BB63_P_E_StatusCategory      24/12/2009      Ganesh      Categorizing each status under
 *P_ENH_RANGE                 15 SEP 2011      PRAKASH JODHA      RANGE IS RENAME TO RANGE_VALUE
 *BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object 
 */
public class FsLeadSource2DAO extends BaseDAO {
	static	Logger logger					= Logger.getLogger(FsLeadSource2DAO.class);
	
	/**
	*  Constructor for the LeadStatusDAO object
	*/
	public FsLeadSource2DAO() {
		this.tableAnchor = "fsLeadSource2";

	}
	








	public Info getSource2Info() {

		Info info = new Info();

		ResultSet result=null;
		//String query = "SELECT A.LEAD_SOURCE2_ID,A.LEAD_SOURCE2_NAME,COUNT(LD.LEAD_SOURCE2_ID) 'LEADS' FROM FS_LEAD_SOURCE2 A  LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_SOURCE2_ID = LD.LEAD_SOURCE2_ID WHERE  A.FLAG <>'N'  GROUP BY A.LEAD_SOURCE2_ID";
		try{

			StringBuffer sbQuery	= new StringBuffer();


			//getting verified LEADs
			sbQuery.append(" SELECT A.LEAD_SOURCE2_ID,A.LEAD_SOURCE2_NAME,COUNT(LD.LEAD_SOURCE2_ID) 'LEADS',WEBPAGE_FLAG ");
			sbQuery.append(" FROM FS_LEAD_SOURCE2 A  LEFT JOIN FS_LEAD_DETAILS LD ");
			sbQuery.append(" ON A.LEAD_SOURCE2_ID = LD.LEAD_SOURCE2_ID ");
			//3-Aug-2009,order by LEAD_SOURCE2_NAME
			sbQuery.append(" WHERE  A.FLAG <>'N'  GROUP BY A.LEAD_SOURCE2_ID ORDER BY A.LEAD_SOURCE2_NAME;");

		 result			= QueryUtil.getResult(sbQuery.toString(), null);
		int i=0;
			while(result.next()){
				//info = new Info();
                info.set(FieldNames.LEAD_SOURCE_ID + i, result.getString("LEAD_SOURCE2_ID"));
				info.set(FieldNames.LEAD_SOURCE_NAME +i , result.getString("LEAD_SOURCE2_NAME"));
				info.set("ASSOCIATEDLEADS" + i, result.getString("LEADS"));
				info.set(FieldNames.WEBPAGE_FLAG +i , result.getString("WEBPAGE_FLAG"));				
				 i++;
			}

		    sbQuery = null;
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return info;
	}

	public boolean source2Delete(String sourceId) {
				
		 boolean deleted = false;
		 
		StringBuffer sbQuery1	= new StringBuffer();
		sbQuery1.append(" UPDATE FS_LEAD_SOURCE2 SET FLAG ='N' WHERE LEAD_SOURCE2_ID='");
		sbQuery1.append(sourceId).append("'");

		 StringBuffer sbQuery2	= new StringBuffer();
		 sbQuery2.append("UPDATE FS_LEAD_SOURCE3 SET FLAG ='N' WHERE FS_LEAD_SOURCE3.LEAD_SOURCE2_ID='");
		 sbQuery2.append(sourceId).append("'");	
		 StringBuffer leadStatusquery	= new StringBuffer();		
		 leadStatusquery.append("SELECT COUNT(LD.LEAD_SOURCE2_ID) 'LEADS' FROM FS_LEAD_SOURCE2 A ");
		 leadStatusquery.append	(" LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_SOURCE2_ID = LD.LEAD_SOURCE2_ID ");
		 leadStatusquery.append	( " WHERE  A.LEAD_SOURCE2_ID = '").append(sourceId);
		 leadStatusquery.append("'  GROUP BY A.LEAD_SOURCE2_ID ");
		 String count = "1";
		 int count2=0;
		 ResultSet result=null;
		 try {
			 result = QueryUtil.getResult(leadStatusquery.toString(), new Object[]{});
			if(result.next()) {
			   count = result.getString("LEADS");
	
			}

			if(Integer.parseInt(count)==0) {
				count2=	QueryUtil.update(sbQuery1.toString(), new String[]{});
			    QueryUtil.update(sbQuery2.toString(), new String[]{});
			    QueryUtil.update("DELETE FROM FS_SOURCE_OWNERS  WHERE SOURCE_ID  IN("+sourceId+") ", null);
				System.out.println(count2 + " record deleted ");
				deleted= count2 != 0 ? true :false ;
			}
			
			QueryUtil.update("DELETE FROM FS_SCHEDULE_LEAD_OWNER  WHERE SOURCE_ID ="+sourceId+"", null);
			QueryUtil.update("DELETE FROM FS_OWNER_TERRITORY_CAMPAIGN  WHERE OWNER_ID ="+sourceId+" AND TYPE= 'source'", null);
			QueryUtil.update("DELETE FROM FS_OWNER_TERRITORY_BRAND_CAMPAIGN  WHERE OWNER_ID ="+sourceId+" AND TYPE= 'source'", null);
			
				 	
		 }
		 catch (Exception e )
		 {
		
		 } 
		 finally
			{QueryUtil.releaseResultSet(result);
			}

		   sbQuery1= null;
		   sbQuery2=null;
		   leadStatusquery= null;

			return deleted;
	}

	public String getSource2Name(String source2Id) {
		StringBuffer sbQuery = new StringBuffer("SELECT LEAD_SOURCE2_NAME FROM  FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID=");
		sbQuery.append(source2Id);
		String source2Name=null;
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(sbQuery.toString(), null);
			if(result.next()){
				source2Name= result.getString("LEAD_SOURCE2_NAME");

			}
		}
		catch(Exception e){
			logger.info("Exception in getSource2Name ");

		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return source2Name;
	} 
	//	BB63_P_E_StatusCategory
	public SequenceMap getLeadKilledReasonMap(){
		SequenceMap killedReasonMap = new SequenceMap();
		Info krInfo = null;
		StringBuffer selectQuery	= new StringBuffer("SELECT FLS.LEAD_STATUS_NAME,LKR.KILLED_REASON_ID, LKR.KILLED_REASON, IS_EDITABLE, COUNT(FSLD.LEAD_KILLED_REASON) AS ASSOCIATED_LEADS FROM LEAD_KILLED_REASON LKR LEFT JOIN FS_LEAD_DETAILS FSLD ON LKR.KILLED_REASON_ID = FSLD.LEAD_KILLED_REASON  LEFT JOIN  FS_LEAD_STATUS FLS ON FLS.LEAD_STATUS_ID = LKR.KILLED_STATUS_ID  GROUP BY LKR.KILLED_REASON_ID ORDER BY LKR.KILLED_REASON ");
		
		ResultSet result=null;
		try{			
			 result			= QueryUtil.getResult(selectQuery.toString(), null);
			int i=0;
			while(result.next()){
				krInfo = new Info();
				krInfo.set(FieldNames.LEAD_KILLED_REASON_ID, result.getString("KILLED_REASON_ID"));
				krInfo.set(FieldNames.LEAD_KILLED_REASON_NAME, result.getString("KILLED_REASON"));
				krInfo.set(FieldNames.IS_EDITABLE, result.getString("IS_EDITABLE"));
				krInfo.set(FieldNames.ASSOCIATED_LEADS, result.getString("ASSOCIATED_LEADS"));
				krInfo.set(FieldNames.LEAD_STATUS_NAME, result.getString("LEAD_STATUS_NAME"));
				killedReasonMap.put(String.valueOf(i++), krInfo);
			}
			selectQuery = null;
		}catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return killedReasonMap;
	}
	public int deleteLead(String tableName, String pkColumnName, String primaryKeys){
		int i = -1;		
		StringBuffer delQuery = new StringBuffer("DELETE FROM "+tableName+" WHERE "+pkColumnName+" IN ("+primaryKeys+")");		
		try{
			String[] param = {};
			i = QueryUtil.update(delQuery.toString(), param );			
			delQuery = null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}
	
	public ArrayList getAssocitedSounrce2Id()
	{
		ArrayList list=new ArrayList();
		String query="SELECT PARAMETER_NAME,RANGE_VALUE FROM LEAD_QUALIFICATION_PARAMETER WHERE PARAMETER_NAME='LS'";                     //P_ENH_RANGE
		ResultSet result=null;
		try{			
			 result			= QueryUtil.getResult(query,null);
			if(result.next())
			{
				String []ranges=null;
				ranges=result.getString("RANGE_VALUE").split(",");
				for(int i=0;i<ranges.length;i++)
					list.add(ranges[i].split("##")[0]);
			}
			
		}catch(Exception e){
			logger.error("Exception in getAssocitedSounrce2Id",e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return list;
	}
	
	public void updateQualificationParameter(String oldValue,String newValue)
	{
		String query="UPDATE LEAD_QUALIFICATION_PARAMETER SET RANGE_VALUE=REPLACE(RANGE_VALUE,REPLACE('"+oldValue+"',\" \",\"_\"),REPLACE('"+newValue+"',\" \",\"_\")) WHERE PARAMETER_NAME='LS'";                               //P_ENH_RANGE
		try{
			String[] param = {};
			QueryUtil.update(query, param);			
		}catch(Exception e){
			logger.error("Exception in updateQualificationParameter ",e);
		}
	}
	
	/**
	 * This function will provide the source Name and the assigned users corresponding to it.
	 * @author Naman Jain
	 * @return {@link SequenceMap}
	 * @reference P_FS_Enh_AssignmentBySourceDetail
	 */
	public SequenceMap<String, Info> getAllSouceDetailsWithOwnerNames(String sortOrder) {
		SequenceMap<String, Info> allSourceMap = new SequenceMap<String, Info>();
		ResultSet result = null;
		Info info = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME, GROUP_CONCAT(DISTINCT USER_NO ORDER BY ORDER_NO ASC) AS USER_NO FROM FS_LEAD_SOURCE2 FSL2");
		queryBuilder.append(" LEFT JOIN FS_SCHEDULE_LEAD_OWNER FSLO ON  FSL2.LEAD_SOURCE2_ID = FSLO.SOURCE_ID");
		queryBuilder.append(" WHERE FLAG='Y'");
		queryBuilder.append(" AND FSL2.LEAD_SOURCE2_ID != 7");
		queryBuilder.append(" GROUP BY LEAD_SOURCE2_ID");
		queryBuilder.append(" UNION");
		queryBuilder.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME, GROUP_CONCAT(DISTINCT USER_NO ORDER BY ORDER_NO ASC) AS USER_NO FROM FS_CONTACT_TYPE"); 
		queryBuilder.append(" LEFT JOIN FS_SCHEDULE_LEAD_OWNER FSLO ON  CONCAT('B7_',CONTACT_TYPE_ID) = FSLO.SOURCE_ID");
		queryBuilder.append(" GROUP BY CONCAT('B7_',CONTACT_TYPE_ID)");
		queryBuilder.append(" ORDER BY LEAD_SOURCE2_NAME ").append(sortOrder);
		try {
			result = QueryUtil.getResult(queryBuilder.toString(), null);
			while(result.next()) {
				info = new Info();
				info.set(FieldNames.LEAD_SOURCE2_ID,result.getString("LEAD_SOURCE2_ID"));
				info.set(FieldNames.LEAD_SOURCE2_NAME,result.getString("LEAD_SOURCE2_NAME"));
				String userNos = result.getString("USER_NO");
				String userNo[] = null;
				if(StringUtil.isValidNew(userNos)) {
					userNo = userNos.split(",");
				}
				String ownerName= "";
				if(userNo != null && userNo.length > 0) {
					for(String userNo1 : userNo) {
						if(AdminMgr.newInstance().getUsersDAO().isUserActive(userNo1)) {
							ownerName = ownerName + BaseUtils.getUserName(userNo1)+", ";
						}
					}
					if(StringUtil.isValidNew(ownerName) && ownerName.endsWith(", ")) {
						ownerName = ownerName.substring(0, ownerName.lastIndexOf(","));
					}
				}

				if(StringUtil.isValidNew(ownerName)) {
					info.set(FieldNames.OWNER_NAME, ownerName);   
				} else {
					info.set(FieldNames.OWNER_NAME, "");
				}
				allSourceMap.put(result.getString("LEAD_SOURCE2_ID"), info);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Exception in getAllSouceDetailsWithOwnerNames=======>>>"+e);
		} finally {
			info = null;
			QueryUtil.releaseResultSet(result);
		}
		return allSourceMap;
	}
	
	/**
	 * This function will provide the source Name and sourceId .
	 * @return {@link ArrayList}
	 * P_FS_Enh_AssignmentBySourceDetail
	 */
	public ArrayList getAllSourceDetails() {
		ArrayList source = new ArrayList();
		String sourceData[] = null;
		ResultSet result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'");
		queryBuilder.append(" UNION");
		queryBuilder.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME FROM FS_CONTACT_TYPE");
		queryBuilder.append(" ORDER BY LEAD_SOURCE2_NAME ASC");
		try {
			result = QueryUtil.getResult(queryBuilder.toString(), null);
			while(result.next()) {
				sourceData = new String[2];
				sourceData[0] = result.getString("LEAD_SOURCE2_ID");
				sourceData[1] = result.getString("LEAD_SOURCE2_NAME");
				
				source.add(sourceData);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			QueryUtil.releaseResultSet(result);
			sourceData = null;
		}
		return source;
	}
	
	/**
	 * this funtion will give the owner corresponding to the source.
	 * P_FS_Enh_AssignmentBySourceDetail
	 * @return
	 */
	public HashMap<String, String> getAllSourceOwner() {
		HashMap<String, String> sourceOwner = new HashMap<String, String>();
		ResultSet result = null;
		String query = "SELECT * FROM FS_SOURCE_OWNERS";
		try {
			result = QueryUtil.getResult(query, null);
			while(result.next()) {
				sourceOwner.put(result.getString("SOURCE_ID"),result.getString("USER_NO"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return sourceOwner;
	}
	
	/**
	 * This function is used to update the owners corresponding to the source
	 * @param sourceOwnerMappings
	 * P_FS_Enh_AssignmentBySourceDetail
	 */
	public void updateSourceOwnerMapping(HashMap sourceOwnerMappings) {
		String allQuery = "SELECT SOURCE_ID FROM FS_SOURCE_OWNERS";	// to find sources which are already mapped
		String insertQuery = "INSERT INTO FS_SOURCE_OWNERS (SOURCE_ID,USER_NO) VALUES (?,?)"; // For sources which are not mapped
		String updateQuery = "UPDATE FS_SOURCE_OWNERS SET USER_NO = ? WHERE SOURCE_ID = ?"; // For sources which are mapped
		ResultSet result = null;
		ArrayList sourceList = null;
		try {
			result = QueryUtil.getResult(allQuery, null);
			sourceList = new ArrayList();
			while(result.next()) {
				sourceList.add(result.getString("SOURCE_ID"));
			}
			Set allKeys = sourceOwnerMappings.keySet();
			Iterator allKeysIter = allKeys.iterator();
			String sourceId = null;
			while (allKeysIter.hasNext()) {
				sourceId = (String)allKeysIter.next();
				if (sourceList.contains(sourceId)) {
					QueryUtil.update(updateQuery, new String[]{(String)sourceOwnerMappings.get(sourceId), sourceId});
				} else {
					QueryUtil.executeInsert(insertQuery, new String[]{sourceId, (String)sourceOwnerMappings.get(sourceId)});
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
		}
	}
	
	   public String getCaegoryName(String sourceID) {
	        String sourceName = "";
	        StringBuffer query = new StringBuffer();
	        query.append("SELECT LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID ='");
	        if (StringUtil.isValidNew(sourceID)) {
	            query.append(sourceID);
	            query.append("' OR LEAD_SOURCE2_NAME='");
	            query.append(sourceID);
	            query.append("'");
	            sourceName = SQLUtil.getQueryResult(query.toString(), "LEAD_SOURCE2_NAME");
	        }
	        
	        return sourceName;
	    }
	
	    public String getLeadCategoryName(String categoryId) {
	        String leadCategoryName = null;
	        String query = null;
	        ResultSet result = null;

	        if (!StringUtil.isValidNew(categoryId)) {
	            return "";
	        }
	        return SQLUtil.getQueryResult("SELECT LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID="+categoryId,
	        		"LEAD_SOURCE2_NAME");
	    }
	    /**
	     * Sourabh Trigger
	     * @param leadSource2Id
	     * @param sourceContactType
	     * @return
	     */
	    public String getSourceLeadCategoryName(String leadSource2Id, String sourceContactType) 
	    {
	        String leadCategoryName = null;
	        if(StringUtil.isValidNew(sourceContactType))
	        {
	        	leadCategoryName = SQLUtil.getColumnValue("FS_CONTACT_TYPE", "CONTACT_TYPE_NAME", "CONTACT_TYPE_ID", sourceContactType.substring(sourceContactType.indexOf("_")+1));
	        }else
	        {
	        	leadCategoryName = SQLUtil.getColumnValue("FS_LEAD_SOURCE2", "LEAD_SOURCE2_NAME", "LEAD_SOURCE2_ID", leadSource2Id);
	        }
	        return leadCategoryName;
	    }
	/**
	 * Sourabh Trigger
	 * @param leadSource2Id
	 * @param ownerId
	 * @param campaignId
	 * @param modifiedBy
	 * @param dateModified
	 * @param type
	 * @param sourceContactType
	 * @return
	 */
	    public String updateSourceCompaign(String leadSource2Id,String ownerId,String campaignId,String modifiedBy,String dateModified,String type, String sourceContactType)
	      {
	    	  ResultSet rs=null;
	    	  String[] param = null;
	    	  StringBuffer sbQuery = new StringBuffer();
	    	  String query="";
	    	  try
	    	  {
	    		  if(StringUtil.isValidNew(sourceContactType))
    			  {
	    			  query="SELECT * FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ?  AND TYPE = ? AND SOURCE_CONTACT_TYPE = ?";
	    			  param = new String[]{ownerId, leadSource2Id, type, sourceContactType};
    			  }else
    			  {
    				  query="SELECT * FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ?  AND TYPE = ?";
	    			  param = new String[]{ownerId, leadSource2Id, type};
    			  }

	    		  rs = QueryUtil.getResult(query,param);
	    		  String defaultSourceCampaignId = "";
	    		  if(StringUtil.isValidNew(sourceContactType))
	    		  {
	    			  defaultSourceCampaignId = SQLUtil.getColumnValue("FS_CAMPAIGN_TRIGGERS", "CAMPAIGN_ID", new String[]{"LEAD_SOURCE2_ID", "SOURCE_CONTACT_TYPE"}, new String[]{leadSource2Id, sourceContactType});
	    		  }else
	    		  {
	    			  defaultSourceCampaignId = SQLUtil.getColumnValue("FS_CAMPAIGN_TRIGGERS", "CAMPAIGN_ID", "LEAD_SOURCE2_ID", leadSource2Id);
	    		  }
	    		  if(!rs.next() && !campaignId.equals(defaultSourceCampaignId))
	    		  {
	    			  if("Select_Modified".equals(campaignId))
	    			  {
	    				  campaignId="-1";
	    			  }
	    			  if(StringUtil.isValidNew(sourceContactType))
	    			  {
	    				  sbQuery.append("INSERT INTO FS_OWNER_TERRITORY_SOURCE_CAMPAIGN(LEAD_SOURCE2_ID, OWNER_ID, CAMPAIGN_ID, MODIFIED_BY, DATE_MODIFIED, TYPE, SOURCE_CONTACT_TYPE) VALUES(?,?,?,?,?,?,?)");
	    				  QueryUtil.update(sbQuery.toString(), new String[]{leadSource2Id, ownerId, campaignId, modifiedBy, dateModified, type, sourceContactType});
	    			  }else
	    			  {
	    				  sbQuery.append("INSERT INTO FS_OWNER_TERRITORY_SOURCE_CAMPAIGN(LEAD_SOURCE2_ID, OWNER_ID, CAMPAIGN_ID, MODIFIED_BY, DATE_MODIFIED, TYPE) VALUES(?,?,?,?,?,?)");
	    				  QueryUtil.update(sbQuery.toString(), new String[]{leadSource2Id, ownerId, campaignId, modifiedBy, dateModified, type});
	    			  }
	    		  }else
	    		  {

	    			  if("Select_Modified".equals(campaignId)){
	    				  campaignId="-1";
	    			  }
	    			  if(StringUtil.isValidNew(sourceContactType))
	    			  {
	    				  sbQuery.append("UPDATE FS_OWNER_TERRITORY_SOURCE_CAMPAIGN SET CAMPAIGN_ID = ? ,MODIFIED_BY = ?, DATE_MODIFIED = ? WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ? AND TYPE = ? AND SOURCE_CONTACT_TYPE = ?");
	    				  QueryUtil.update(sbQuery.toString(),new String[] {campaignId,modifiedBy, dateModified, ownerId, leadSource2Id, type, sourceContactType});
	    			  }else
	    			  {
	    				  sbQuery.append("UPDATE FS_OWNER_TERRITORY_SOURCE_CAMPAIGN SET CAMPAIGN_ID = ? ,MODIFIED_BY = ?, DATE_MODIFIED = ? WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ? AND TYPE = ?");
	    				  QueryUtil.update(sbQuery.toString(),new String[] {campaignId,modifiedBy, dateModified, ownerId, leadSource2Id, type});
	    			  }
	    		  }
	    	  } catch (Exception e)
	    	  {
	    		  logger.info("ERROR: exception in updateLeadStatus ::" + e);
	    	  }
	    	  finally
	    	  {
	    		  if(rs != null)
	    		  {
	    			  rs.release();
	    			  rs = null;
	    		  }
	    	  }
	    	  return "true";
	      }
	    
	    public String getSourceAssignedType(String leadSource2ID, String sourceContactType) 
	    {
	    	String query="";
	    	if(StringUtil.isValidNew(sourceContactType))
	    	{
	    		query="SELECT DISTINCT(TYPE) FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE LEAD_SOURCE2_ID='"+leadSource2ID+"' AND SOURCE_CONTACT_TYPE ='"+sourceContactType+"'";
	    	}else
	    	{
	    		query="SELECT DISTINCT(TYPE) FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE LEAD_SOURCE2_ID='"+leadSource2ID+"'";
	    	}
	    	return SQLUtil.getQueryResult(query, "TYPE");
	    }
	    
	    public String getCampaignIDForOwner(String ownerId,String leadSource2ID,String type, String sourceContactType) 
	    {
	    	String query="";
	    	if(StringUtil.isValidNew(sourceContactType))
	    	{
	    		query = "SELECT CAMPAIGN_ID FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE OWNER_ID='"+ownerId+"' AND LEAD_SOURCE2_ID='"+leadSource2ID+"' AND TYPE='"+type+"' AND SOURCE_CONTACT_TYPE ='"+sourceContactType+"'";
	    	}else
	    	{ 
	    		query = "SELECT CAMPAIGN_ID FROM FS_OWNER_TERRITORY_SOURCE_CAMPAIGN WHERE OWNER_ID='"+ownerId+"' AND LEAD_SOURCE2_ID='"+leadSource2ID+"' AND TYPE='"+type+"'";
	    	}
	    	return SQLUtil.getQueryResult(query, "CAMPAIGN_ID");
	    }
	    
	    public String getDefaultCampaignIDForOwner(String leadSource2ID, String sourceContactType) 
	    {
	    	String query="";
	    	if(StringUtil.isValidNew(sourceContactType))
	    	{
	    		query="SELECT CAMPAIGN_ID FROM FS_CAMPAIGN_TRIGGERS WHERE LEAD_SOURCE2_ID='"+leadSource2ID+"' AND SOURCE_CONTACT_TYPE ='"+sourceContactType+"'";
	    	}else
	    	{
	    		query="SELECT CAMPAIGN_ID FROM FS_CAMPAIGN_TRIGGERS WHERE LEAD_SOURCE2_ID='"+leadSource2ID+"'";
	    	}
	    	return SQLUtil.getQueryResult(query, "CAMPAIGN_ID");
	    }
	    
	    public ArrayList<String> getSourceSummary(String userNo) {
	    	ArrayList<String> list = new ArrayList<String>();
	    	ResultSet result = null;
	    	StringBuilder queryBuilder = new StringBuilder();
	    	queryBuilder.append("SELECT LEAD_SOURCE2_NAME FROM FS_SOURCE_OWNERS FS LEFT JOIN FS_LEAD_SOURCE2 FL ON FS.SOURCE_ID = FL.LEAD_SOURCE2_ID WHERE FLAG='Y' AND FL.LEAD_SOURCE2_ID != '7' AND USER_NO = ").append(userNo);
	    	queryBuilder.append(" UNION");
	    	queryBuilder.append(" SELECT CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME FROM  FS_CONTACT_TYPE FC LEFT JOIN FS_SOURCE_OWNERS FS ON FS.SOURCE_ID = CONCAT('B7_',CONTACT_TYPE_ID) WHERE USER_NO = ").append(userNo);
	    	try {
	    		result = QueryUtil.getResult(queryBuilder.toString(), null);
	    		while(result.next()) {
	    			if(StringUtil.isValidNew(result.getString("LEAD_SOURCE2_NAME"))) {
	    				list.add(result.getString("LEAD_SOURCE2_NAME"));
	    			}
	    		}
	    	} catch (Exception e) {
				// TODO: handle exception
	    		logger.error("EXCEPTION ::: getSourceSummary =========  "+e);
			} finally {
				QueryUtil.releaseResultSet(result);
				queryBuilder = null;
			}
	    	return list;
	    }
	    
	    /**
	     * This function will provide the count of source to which it is associated.
	     * @author Naman Jain
	     * @param userNo
	     * @return
	     */
	    public int getSourceOwnerCount(String userNo) {
	    	int count = 0;
	    	String query = "SELECT COUNT(USER_NO) COUNT FROM FS_SOURCE_OWNERS WHERE USER_NO = "+userNo;
	    	ResultSet result = null;
	    	try {
	    		result = QueryUtil.getResult(query, null);
	    		if(result.next()) {
	    			count = Integer.parseInt(result.getString("COUNT"));
	    		}
	    	} catch(Exception e) {
	    		logger.error("Exception in getSourceOwnerCount ====================== "+e);
	    	}
	    	return count;
	    }
	    
	    public void reAssignSourceOwner(String userNo, String oldUserNo) {
	    	SQLUtil.updateTableValue("FS_SOURCE_OWNERS", "USER_NO", userNo, "USER_NO", oldUserNo);
	    }
	    
		/*------------------------------------Moved from BasePortalUtils---------------------------------------*/

	    /**
		 * This method retrieve the Source Name from LEAD_SOURCE2 Table according to
		 * lead source id being provided.
		 * 
		 * @param sourceId-
		 *            Input String having SourceId as parameter
		 * @return String containg the Source Name
		 * @throws ConnectionException
		 */
		public String getSourceName(String sourceId)
				throws ConnectionException {
			Connection con = null;
			String sourceName = "Not Available";
			Statement stmt = null;
			java.sql.ResultSet rs = null;
			System.out
					.println("SELECT LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID="
							+ sourceId);
			try {
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("SELECT LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE LEAD_SOURCE2_ID="
								+ sourceId);
				if (rs.next()) {
					sourceName = rs.getString("LEAD_SOURCE2_NAME");
				}
				return sourceName;
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
		/**
		 * this function will the owner id for the source to which it is associated
		 * @reference P_FS_Enh_AssignmentBySourceDetail
		 * @param sourceId
		 * @return
		 */
		public String getOwnerIdForSource(String sourceId) {
			String query = "SELECT A.USER_NO FROM FS_SOURCE_OWNERS A LEFT JOIN USERS U ON A.USER_NO = U.USER_NO WHERE A.SOURCE_ID='"+sourceId+"' AND U.STATUS=1";
			String queryNew = "SELECT A.USER_NO FROM FS_AREA_OWNERS A LEFT JOIN USERS U ON A.USER_NO = U.USER_NO WHERE A.AREA_ID=1 AND U.STATUS=1";
			ResultSet result = null;
			String ownerId = "";
			try {
				result = QueryUtil.getResult(query, null);
				if(result.next()) {
					ownerId = result.getString("USER_NO");
				}
				if(!StringUtil.isValidNew(ownerId)) {
					result = QueryUtil.getResult(queryNew, null);
					if(result.next()) {
						ownerId = result.getString("USER_NO");
					}
				}
				if(!StringUtil.isValidNew(ownerId)) {
					ownerId = "1";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return ownerId;
		}	    

		 public static Info getLeadSourceCategory() {
		        return getLeadSourceCategory(null);
		    }
			
			public static Info getLeadSourceCategory(String fromWhere) {
		        String query = "SELECT LEAD_SOURCE2_ID,LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE FLAG='Y'";
		        if("webPageSettings".equals(fromWhere)) { //P_B_FS_58151 starts
		            query = query + " AND WEBPAGE_FLAG = 'Y' ";
		        }
		        //P_B_FS_58151 ends
				if ("0".equals(MasterDataMgr.newInstance().getMasterDataDAO().getValue(10014, 12585)) && !"webPageSettings".equals(fromWhere)) { //P_B_FS_58151
					query = query + " AND LEAD_SOURCE2_ID != 7 UNION SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME FROM FS_CONTACT_TYPE ";
				}
				query = query + " ORDER BY LEAD_SOURCE2_NAME ASC ";
		        return SQLUtil.getInfoFromQuery("LEAD_SOURCE2_ID", "LEAD_SOURCE2_NAME", query,true);//P_B_FS_56371
			}

		public void deleteSourceTrigger(String sourceIds) {
			String query = "DELETE FROM FS_CAMPAIGN_TRIGGERS WHERE LEAD_SOURCE2_ID IN ("+sourceIds+")";
			try {
				QueryUtil.update(query, new String[]{});
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		public SequenceMap getLeadSourceComboMap() {
			String contactTypeDisplay= MasterDataMgr.newInstance().getMasterDataDAO().getValue(10014,12585);
			StringBuffer query =null;
			if(FieldNames.ZERO.equals(contactTypeDisplay)) {
				query = new StringBuffer("SELECT LEAD_SOURCE2_ID ,LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE FLAG='Y' AND LEAD_SOURCE2_ID != 7 UNION SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME FROM FS_CONTACT_TYPE ORDER BY LEAD_SOURCE2_NAME");
			}
			else {
				query = new StringBuffer("SELECT LEAD_SOURCE2_ID ,LEAD_SOURCE2_NAME FROM FS_LEAD_SOURCE2 WHERE FLAG='Y' ORDER BY LEAD_SOURCE2_NAME");        	   
			}
			return SQLUtil.getSequenceMapFromQuery("LEAD_SOURCE2_ID","LEAD_SOURCE2_NAME",query.toString());
		}  

		public static ArrayList getContactTypeNames() 
		{
			ArrayList contactTypeNames = new ArrayList();
			//P_B_FS_69116 start
			//String query ="SELECT CONTACT_TYPE_NAME FROM FS_CONTACT_TYPE ORDER BY CONTACT_TYPE_NAME";
			String query ="SELECT CAST(SUM(COUNT) AS CHAR) AS COUNT,CONTACT_TYPE_ID, CONTACT_TYPE_NAME  FROM(SELECT A.CONTACT_TYPE_ID, A.CONTACT_TYPE_NAME, COUNT(BD.BROKER_ID) AS COUNT FROM FS_CONTACT_TYPE A LEFT JOIN BROKER_DETAILS BD ON (A.CONTACT_TYPE_ID = BD.CONTACT_TYPE) GROUP BY A.CONTACT_TYPE_ID  UNION SELECT A.CONTACT_TYPE_ID, A.CONTACT_TYPE_NAME, COUNT(BD.LEAD_ID) AS COUNT FROM FS_CONTACT_TYPE A LEFT JOIN FS_LEAD_DETAILS BD ON (BD.SOURCE_CONTACT_TYPE = CONCAT('B7_',A.CONTACT_TYPE_ID))  GROUP BY A.CONTACT_TYPE_ID) AS DUMMY GROUP BY CONTACT_TYPE_ID";
			//P_B_FS_69116 end
			ResultSet result=null;
			try
			{
				 result = QueryUtil.getResult(query,null);
				while(result.next())
				{
					contactTypeNames.add(result.getString("CONTACT_TYPE_NAME"));
				} 
			}catch (Exception e) 
			{
	            e.printStackTrace();
	        }
			finally
			{
		
				QueryUtil.releaseResultSet(result);
			}
	        return contactTypeNames;
		}  
		public SequenceMap getClosedLeadCountMap(String leadNames, String source,String areaId,String userLevel,String userNo,String leadStatusCategory){
			return getClosedLeadCountMap( leadNames,  source, areaId, userLevel, userNo, leadStatusCategory,null);
		}
		public SequenceMap getClosedLeadCountMap(String leadNames, String source,String areaId,String userLevel,String userNo,String leadStatusCategory,String sourceDetail){// P_Enh_Source_Detail_Filter
			return getClosedLeadCountMap( leadNames,  source, areaId, userLevel, userNo, leadStatusCategory,sourceDetail,null);
		}
		public SequenceMap getClosedLeadCountMap(String leadNames, String source,String areaId,String userLevel,String userNo,String leadStatusCategory,String sourceDetail,String coApplicantAsLead){// P_Enh_Source_Detail_Filter
			return getClosedLeadCountMap( leadNames,  source, areaId, userLevel, userNo, leadStatusCategory,sourceDetail,coApplicantAsLead,null);
		}
		public SequenceMap getClosedLeadCountMap(String leadNames, String source,String areaId,String userLevel,String userNo,String leadStatusCategory,String sourceDetail,String coApplicantAsLead, String division_Ids)//P_ENH_COAPPLICANT_AS_LEAD
      	{
			String divisionIds=(String)StrutsUtil.getHttpSession().getAttribute("divisionIds");
      		int year = DateUtil.getYear(DateUtil.getCurrentDate());
      		int month = DateUtil.getMonth(DateUtil.getCurrentDate());
      		String date = DateUtil.getDbDate(DateUtil.getCurrentDateAsString());
      		String day=date.substring(date.lastIndexOf("-")+1);
      		String mon=""+month;
      		if(mon.length()<2)
      			mon="0"+mon;
      		if(day.length()<2)
      			day="0"+day;

      		String toDate=(year)+"-"+mon+"-"+day+" 23:59:59";
      		String fromDate=(year-1)+"-"+mon+"-"+day+" 00:00:01";
      		//Added by Ankush Tanwar for getting Report with parameters starts                
      		//jyotsna_123
      		String leadParam=FieldNames.EMPTY_STRING;
      		String sourceParam= FieldNames.EMPTY_STRING;
      		String sourceParamDetail= FieldNames.EMPTY_STRING;// P_Enh_Source_Detail_Filter
      		String statusCategory = FieldNames.EMPTY_STRING;
      		if(StringUtil.isValidNew(leadNames))
      		{
      			leadParam = " AND FLD.LEAD_OWNER_ID IN ("
      				+ leadNames + ") ";
      		}
      		else if("-1".equals(leadNames) && "2".equals(userLevel))
      		{
      			leadParam = " AND ( FLD.AREA_ID ='"+areaId+"' OR  FLD.LEAD_OWNER_ID IN ("
      			+NewPortalUtils.getRegionalOwnersMap(userNo)+ ") ) ";
      		}else if("-1".equals(leadNames) && Constants.USER_LEVEL_DIVISION.equals(userLevel))
 			{
 				leadParam=leadParam+" AND ( ";
 				/*if(StringUtil.isValidNew(DivisionUtil.getAllDivisionLeadIds(divisionIds))){
 					leadParam=leadParam+"  FLD.LEAD_ID IN ( " + DivisionUtil.getAllDivisionLeadIds(divisionIds) + " ) OR  ";
 				}*/
 				leadParam=leadParam+" FLD.LEAD_OWNER_ID IN ( "
 						+ NewPortalUtils.getRegionalOwnersMap(userNo) + ") ) ";

 			}
      		String coApplicantAsLeadParam = "";   //P_ENH_COAPPLICANT_AS_LEAD starts
     		/*if("0".equals(MultiTenancyUtil.getTenantConstants().IS_COAPPLICANT_AS_LEAD_CONFIGURED) && "true".equals(coApplicantAsLead))
    		{
     			coApplicantAsLeadParam = " AND (FLD.COAPPLICANT_TYPE <> 'C' OR FLD.COAPPLICANT_TYPE IS NULL) ";
    		}		*/					//P_ENH_COAPPLICANT_AS_LEAD ends
     		String divisionIdsParam = FieldNames.EMPTY_STRING;
     		/*if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && StringUtil.isValidNew(division_Ids))
            {
            	String leadIds = DivisionUtil.getAllDivisionLeadIds(division_Ids);
    			if(StringUtil.isValid(leadIds)){
    				divisionIdsParam = " AND FLD.LEAD_ID IN ("+leadIds+") ";
    			}
            }*/
      		if(StringUtil.isValidNew(source))//jyotsna_123
      		{
      			String souceID[]=source.replaceAll(" ","").split(",");
      			source= FieldNames.EMPTY_STRING;
      			for(int i=0;i<souceID.length;i++)
      			{
      				if(i!=0)
      				{
      					source=source+",'"+souceID[i]+"'";
      				}
      				else
      				{
      					source="'"+souceID[i]+"'";
      				}
      			}
      			sourceParam=" AND (LEAD_SOURCE2_ID IN ("+source+")  OR SOURCE_CONTACT_TYPE IN("+source+")) ";//jyotsna_123
      		} 
      		if(StringUtil.isValidNew(sourceDetail))// P_Enh_Source_Detail_Filter starts
      		{
      			String souceID[]=sourceDetail.replaceAll(" ","").split(",");
      			String tempSourceDetail= FieldNames.EMPTY_STRING;
      			for(int i=0;i<souceID.length;i++)
      			{
      				if(i!=0)
      				{
      					tempSourceDetail=tempSourceDetail+",'"+souceID[i]+"'";
      				}
      				else
      				{
      					tempSourceDetail="'"+souceID[i]+"'";
      				}
      			}
      			sourceParamDetail=" AND LEAD_SOURCE3_ID IN ("+tempSourceDetail+")";
      		} // P_Enh_Source_Detail_Filter ends
      		//P_FS_B_6899 Nishant starts
      		statusCategory = " AND FLD.FRANCHISE_AWARDED='Yes'";
      		//P_FS_B_6899 ends
      		//Added by Ankush Tanwar for getting Report with parameters ends     
      		SequenceMap dataMap =new SequenceMap();
      		ResultSet result=null;
      		try
      		{
      			String query = "SELECT DATE_FORMAT(DATE_TIME,'%m') AS MONTH FROM FS_CAPTIVATE_TRACKING FCT,FS_LEAD_DETAILS FLD LEFT JOIN FS_LEAD_STATUS FLS ON FLD.LEAD_STATUS_ID= FLS.LEAD_STATUS_ID WHERE   FCT.LEAD_ID=FLD.LEAD_ID "+leadParam+sourceParam+sourceParamDetail+statusCategory+coApplicantAsLeadParam+divisionIdsParam+" AND DATE_TIME >= ? AND DATE_TIME<= ?"+"" ;    //P_ENH_COAPPLICANT_AS_LEAD
      			result = QueryUtil.getResult(query, new Object[] {fromDate,toDate});

      			if(result.size()>0)//Added by Ankush Tanwar for GUI changes
      			{
      				for(int i=1;i<=12;i++)
      				{
      					if(i<10)
      						dataMap.put("0"+i,0);
      					else
      						dataMap.put(""+i,0);
      				}
      				while(result.next()) 
      				{
      					dataMap.put(result.getString("MONTH"),""+(Integer.parseInt(dataMap.get(result.getString("MONTH")).toString())+1));
      				}
      			}
      		}
      		catch(Exception e)
      		{
      			logger.error("Exception in getClosedLeadCountMap() " ,e);
      		}finally
      		{
      			QueryUtil.releaseResultSet(result);
      		}
      		return dataMap;
      	}
		/**
		 * This method updates brand based campaigns
		 * P_Enh_Brand_Campaign_Trigger
		 * @author Akash Kumar
		 * @param brandId
		 * @param ownerId
		 * @param campaignId
		 * @param modifiedBy
		 * @param dateModified
		 * @param type
		 * @return "true"
		 */
	    public String updateBrandCampaign(String brandId,String ownerId,String campaignId,String modifiedBy,String dateModified,String type,String sourceContactType)
	    {
	    	ResultSet rs=null;
	    	String[] param = null;
	    	StringBuffer sbQuery = new StringBuffer();
	    	String query="";
	    	try
	    	{
	    		if(StringUtil.isValidNew(sourceContactType)){
	    			query="SELECT * FROM FS_OWNER_TERRITORY_BRAND_CAMPAIGN WHERE OWNER_ID = ? AND BRAND_ID = ?  AND TYPE = ? AND SOURCE_CONTACT_TYPE=?";
	    			param = new String[]{ownerId, brandId, type,sourceContactType};	
	    		}else{
	    			query="SELECT * FROM FS_OWNER_TERRITORY_BRAND_CAMPAIGN WHERE OWNER_ID = ? AND BRAND_ID = ?  AND TYPE = ?";
	    			param = new String[]{ownerId, brandId, type};
	    		}

	    		rs = QueryUtil.getResult(query,param);
	    		String defaultSourceCampaignId = "";
	    		defaultSourceCampaignId = SQLUtil.getColumnValue("FS_CAMPAIGN_TRIGGERS", "CAMPAIGN_ID", "BRAND_ID", brandId);
	    		if(!rs.next() && !campaignId.equals(defaultSourceCampaignId))
	    		{
	    			if("Select_Modified".equals(campaignId))
	    			{
	    				campaignId="-1";
	    			}
	    			if(StringUtil.isValidNew(sourceContactType)){
	    				sbQuery.append("INSERT INTO FS_OWNER_TERRITORY_BRAND_CAMPAIGN(BRAND_ID, OWNER_ID, CAMPAIGN_ID, MODIFIED_BY, DATE_MODIFIED, TYPE,SOURCE_CONTACT_TYPE) VALUES(?,?,?,?,?,?,?)");
	    				QueryUtil.update(sbQuery.toString(), new String[]{brandId, ownerId, campaignId, modifiedBy, dateModified, type,sourceContactType});	
	    			}else{
	    				sbQuery.append("INSERT INTO FS_OWNER_TERRITORY_BRAND_CAMPAIGN(BRAND_ID, OWNER_ID, CAMPAIGN_ID, MODIFIED_BY, DATE_MODIFIED, TYPE) VALUES(?,?,?,?,?,?)");
	    				QueryUtil.update(sbQuery.toString(), new String[]{brandId, ownerId, campaignId, modifiedBy, dateModified, type});
	    			}
	    		}else
	    		{

	    			if("Select_Modified".equals(campaignId)){
	    				campaignId="-1";
	    			}
	    			if(StringUtil.isValidNew(sourceContactType)){
	    				sbQuery.append("UPDATE FS_OWNER_TERRITORY_BRAND_CAMPAIGN SET CAMPAIGN_ID = ? ,MODIFIED_BY = ?, DATE_MODIFIED = ? WHERE OWNER_ID = ? AND BRAND_ID = ? AND TYPE = ? AND SOURCE_CONTACT_TYPE = ?");
	    				QueryUtil.update(sbQuery.toString(),new String[] {campaignId,modifiedBy, dateModified, ownerId, brandId, type,sourceContactType});
	    			}else{
	    				sbQuery.append("UPDATE FS_OWNER_TERRITORY_BRAND_CAMPAIGN SET CAMPAIGN_ID = ? ,MODIFIED_BY = ?, DATE_MODIFIED = ? WHERE OWNER_ID = ? AND BRAND_ID = ? AND TYPE = ?");
	    				QueryUtil.update(sbQuery.toString(),new String[] {campaignId,modifiedBy, dateModified, ownerId, brandId, type});
	    			}
	    		}
	    	} catch (Exception e)
	    	{
	    		logger.info("ERROR: exception in updateBrandCampaign ::" + e);
	    	}
	    	finally
	    	{
	    		if(rs != null)
	    		{
	    			rs.release();
	    			rs = null;
	    		}
	    	}
	    	return "true";
	    }
	    /**
	     * P_Enh_Brand_Owner_Assignment
	     * @author Akash Kumar
	     * @return  all source owners Map
	     */
	    public SequenceMap getAllSourceOwnerMap() {
	    	SequenceMap sourceOwner = new SequenceMap();
	    	Info info=null;
	    	int i=0;
	    	ResultSet result = null;
	    	StringBuffer query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS USER_NO FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND BRAND_ID='NA' AND FSO.ASSIGNMENT_TYPE='SR' GROUP BY FSO.SOURCE_ID");
	    	query.append(" UNION ");
	    	query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS USER_NO FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) AND REGION_ID='NA' AND BRAND_ID='NA' AND FSO.ASSIGNMENT_TYPE='SR' GROUP BY FSO.SOURCE_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
	    	try {
	    		result = QueryUtil.getResult(query.toString(), null);
	    		while(result.next()) {
	    			info = new Info();
	    			info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
	    			info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
	    			info.set(FieldNames.USER_NO, result.getString("USER_NO"));
	    			if(StringUtil.isValidNew(result.getString("USER_NO"))){
	    				info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("USER_NO")));
	    			}else{
	    				info.set(FieldNames.USER_NAME,"");
	    			}
	    			sourceOwner.put(new Integer(i++), info);
	    		}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		logger.info("ERROR: exception in getAllSourceOwnerMap ::" + e);
	    	} finally {
	    		QueryUtil.releaseResultSet(result);
	    	}
	    	return sourceOwner;


	    }
	   /* public String updateSourceOwnerBrandTerritoryMapping(String leadSource2Id,String ownerNos,String ownerId,String modifiedBy,String dateModified,String type)
	      {
	    	  ResultSet rs=null;
	    	  String[] param = null;
	    	  StringBuffer sbQuery = new StringBuffer();
	    	  String query="";
	    	  try
	    	  {
  				  query="SELECT * FROM FS_SOURCE_BRAND_TERRITORY_OWNERS WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ?  AND TYPE = ?";
	    			  param = new String[]{ownerId, leadSource2Id, type};

	    		  rs = QueryUtil.getResult(query,param);
	    		  if(!rs.next())
	    		  {
	    				  sbQuery.append("INSERT INTO FS_SOURCE_BRAND_TERRITORY_OWNERS(LEAD_SOURCE2_ID, OWNER_ID, MODIFIED_BY, DATE_MODIFIED, TYPE,USER_NO) VALUES(?,?,?,?,?,?)");
	    				  QueryUtil.update(sbQuery.toString(), new String[]{leadSource2Id, ownerId, modifiedBy, dateModified, type,ownerNos});
	    		  }else
	    		  {

	    				  sbQuery.append("UPDATE FS_SOURCE_BRAND_TERRITORY_OWNERS SET USER_NO = ? ,MODIFIED_BY = ?, DATE_MODIFIED = ? WHERE OWNER_ID = ? AND LEAD_SOURCE2_ID = ? AND TYPE = ?");
	    				  QueryUtil.update(sbQuery.toString(),new String[] {ownerNos,modifiedBy, dateModified, ownerId, leadSource2Id, type});
	    		  }
	    	  } catch (Exception e)
	    	  {
	    		  logger.info("ERROR: exception in updateSourceOwnerBrandTerritoryMapping ::" + e);
	    	  }
	    	  finally
	    	  {
	    		  if(rs != null)
	    		  {
	    			  rs.release();
	    			  rs = null;
	    		  }
	    	  }
	    	  return "true";
	      }*/
	    public SequenceMap getSourceOwnerTerritoryMap(String leadSource2ID,String assignMentType) 
	    {
	    	SequenceMap sMap = null;
	    	Info info =null;
	    	ResultSet result=null;
	    	int i=0;
	    	StringBuffer query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID, FA.AREA_ID , FA.AREA_NAME AS OWNER_NAME FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN FS_AREAS AS FA ON FA.AREA_ID=FSO.REGION_ID WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID<>'NA' AND BRAND_ID='NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.REGION_ID");
	    	query.append(" UNION ");
	    	query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID, FA.AREA_ID, FA.AREA_NAME AS OWNER_NAME FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN FS_AREAS AS FA ON FA.AREA_ID=FSO.REGION_ID WHERE REGION_ID<>'NA' AND BRAND_ID='NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.REGION_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
	    	String[]	param = new String[]{leadSource2ID,assignMentType,leadSource2ID,assignMentType};
	    	try 
	    	{
	    		sMap=new SequenceMap();
	    		result = QueryUtil.getResult(query.toString(),param);
	    		while (result.next()) {
	    			info=new Info();
	    			info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
	    			info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
	    			info.set(FieldNames.OWNER_ID, result.getString("OWNER_ID"));
	    			info.set(FieldNames.AREA_ID, result.getString("AREA_ID"));
	    			if(StringUtil.isValidNew(result.getString("OWNER_ID"))){
	    				info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("OWNER_ID")));
	    			}else{
	    				info.set(FieldNames.USER_NAME,"");
	    			}
	    			info.set("type", "territory");
	    			info.set(FieldNames.OWNER_NAME, result.getString("OWNER_NAME"));
	    			sMap.put(new Integer(i++)+"territory", info);

	    		}
	    	} catch (Exception e) {
	    		logger.info("ERROR: exception in getSourceOwnerTerritoryMap ::" + e);
	    	}
	    	finally
	    	{
	    		QueryUtil.releaseResultSet(result);
	    	}

	    	return sMap;
	    }
	    public SequenceMap getSourceOwnerBrandMap(String leadSource2ID,String assignMentType) 
	    {
	    	SequenceMap sMap = null;
	    	/*Info info =null;
	    	ResultSet result=null;
	    	StringBuffer query=null;
	    	int i=0;
	    	if("G".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)){
	    		query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID,FA.DIVISION_NAME AS OWNER_NAME ,FA.DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN DIVISIONS AS FA ON FA.DIVISION_ID=FSO.BRAND_ID  WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID");
	    		query.append(" UNION ");
	    		query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID , FA.DIVISION_NAME AS OWNER_NAME,FA.DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN DIVISIONS AS FA ON FA.DIVISION_ID=FSO.BRAND_ID WHERE REGION_ID='NA' AND BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");
	    	}else{

	    		query = new StringBuffer("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID,FA.BRAND_NAME AS OWNER_NAME ,FA.BRAND_ID AS DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO LEFT JOIN FS_LEAD_SOURCE2 FLS ON FSO.SOURCE_ID = FLS.LEAD_SOURCE2_ID JOIN BRANDS AS FA ON FA.BRAND_ID=FSO.BRAND_ID  WHERE FLS.LEAD_SOURCE2_ID != 7 AND FLAG = 'Y'  AND REGION_ID='NA' AND FSO.BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID");
	    		query.append(" UNION ");
	    		query.append(" SELECT CONCAT('B7_',CONTACT_TYPE_ID) AS LEAD_SOURCE2_ID, CONTACT_TYPE_NAME AS LEAD_SOURCE2_NAME,GROUP_CONCAT(FSO.USER_NO) AS OWNER_ID , FA.BRAND_NAME AS OWNER_NAME,FA.BRAND_ID AS DIVISION_ID FROM FS_SCHEDULE_LEAD_OWNER FSO JOIN FS_CONTACT_TYPE FCT ON FSO.SOURCE_ID = CONCAT('B7_', FCT.CONTACT_TYPE_ID) JOIN BRANDS AS FA ON FA.BRAND_ID=FSO.BRAND_ID WHERE REGION_ID='NA' AND FSO.BRAND_ID<>'NA' AND FSO.SOURCE_ID = ? AND ASSIGNMENT_TYPE = ? GROUP BY FSO.SOURCE_ID,FSO.BRAND_ID ORDER BY LEAD_SOURCE2_NAME ASC ; ");

	    	}
	    	String[]	param = new String[]{leadSource2ID,assignMentType,leadSource2ID,assignMentType};
	    	try 
	    	{
	    		sMap=new SequenceMap();
	    		result = QueryUtil.getResult(query.toString(),param);
	    		while (result.next()) {
	    			info=new Info();
	    			info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
	    			info.set(FieldNames.LEAD_SOURCE2_NAME, result.getString("LEAD_SOURCE2_NAME"));
	    			info.set(FieldNames.OWNER_ID, result.getString("OWNER_ID"));
	    			info.set(FieldNames.BRAND_ID, result.getString("DIVISION_ID"));
	    			if(StringUtil.isValidNew(result.getString("OWNER_ID"))){
	    				info.set(FieldNames.USER_NAME, AdminMgr.newInstance().getUsersDAO().getMultipleUserName(result.getString("OWNER_ID")));
	    			}else{
	    				info.set(FieldNames.USER_NAME,"");
	    			}
	    			info.set("type", "brand");
	    			info.set(FieldNames.OWNER_NAME, result.getString("OWNER_NAME"));
	    			sMap.put(new Integer(i++)+"brand", info);

	    		}
	    	} catch (Exception e) {
	    		logger.info("ERROR: exception in getSourceOwnerTerritoryMap ::" + e);
	    	}
	    	finally
	    	{
	    		QueryUtil.releaseResultSet(result);
	    	}*/

	    	return sMap;
	    }
}
