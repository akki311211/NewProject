package com.home.builderforms;

import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.BaseDAO;
import com.home.builderforms.sqlqueries.*;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The LeadSourceDAO for the LEAD_SOURCE table
 *
 *@author     
 *@created    Wed Jul 21 15:46:05 PDT 2004
 *BBEH_FOR_GETRESULT_METHOD  22/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
 */
public class FsLeadSource3DAO extends BaseDAO {
	/**
	*  Constructor for the LeadStatusDAO object
	*/
	public FsLeadSource3DAO() {
		this.tableAnchor = "fsLeadSource3";

	}
	
	static Logger logger = Logger.getLogger(FsLeadSource3DAO.class);






	

            public Info getSource3Info(String source2Id ) {
            	ResultSet result=null;
		
		Info info = new Info();
		//String query = "SELECT A.LEAD_SOURCE3_ID,A.LEAD_SOURCE3_NAME,COUNT(LD.LEAD_SOURCE3_ID) 'LEADS' FROM FS_LEAD_SOURCE3 A  LEFT JOIN FS_LEAD_DETAILS LD ON A.LEAD_SOURCE3_ID = LD.LEAD_SOURCE3_ID WHERE  A.FLAG <>'N' AND A.LEAD_SOURCE2_ID='"+ source2Id + "' GROUP BY A.LEAD_SOURCE3_ID";
		try{
			StringBuffer sbQuery	= new StringBuffer();
			sbQuery.append("SELECT A.LEAD_SOURCE3_ID,A.LEAD_SOURCE3_NAME,A.LEAD_SOURCE2_ID,COUNT(LD.LEAD_SOURCE3_ID) 'LEADS',WEBPAGE_FLAG,MAIL_SUBJECT ");//P_FS_E_23032011 to get mail subject  //P_B_FS_58151
			sbQuery.append( " FROM FS_LEAD_SOURCE3 A  LEFT JOIN FS_LEAD_DETAILS LD ");
			sbQuery.append(" ON A.LEAD_SOURCE3_ID = LD.LEAD_SOURCE3_ID ");
			sbQuery.append(" WHERE  A.FLAG <>'N' AND A.LEAD_SOURCE2_ID='" ).append(source2Id);
//			3-Aug-2009,order by LEAD_SOURCE3_NAME
			sbQuery.append("' GROUP BY A.LEAD_SOURCE3_ID ORDER BY  A.LEAD_SOURCE3_NAME");

                        
		 result			= QueryUtil.getResult(sbQuery.toString(), null);
                sbQuery=null;
		int i=0;
			while(result.next()){
				//info = new Info();
				 info.set(FieldNames.LEAD_SOURCE2_ID, result.getString("LEAD_SOURCE2_ID"));
                                info.set(FieldNames.LEAD_SOURCE_ID + i, result.getString("LEAD_SOURCE3_ID"));
				info.set(FieldNames.LEAD_SOURCE_NAME +i , result.getString("LEAD_SOURCE3_NAME"));
				info.set("ASSOCIATEDLEADS" + i, result.getString("LEADS"));
				info.set(FieldNames.WEBPAGE_FLAG +i , result.getString("WEBPAGE_FLAG"));
				info.set(FieldNames.MAIL_SUBJECT +i , result.getString("MAIL_SUBJECT")!=null?result.getString("MAIL_SUBJECT"):"");//P_FS_E_23032011
				
				i++;
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

	public HashMap getSource3HashMap( ) {

		
		HashMap hMap1 = new HashMap();
		HashMap hMap2 = new	HashMap();
		String source2Id= null;
		String query = "select  LEAD_SOURCE2_ID , LEAD_SOURCE3_ID, LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3  ORDER BY LEAD_SOURCE2_ID,LEAD_SOURCE3_NAME;";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
		int i=0;
			while(result.next()){
				
				 if( source2Id != null && !(source2Id.equals(result.getString("LEAD_SOURCE2_ID")))) {
					hMap1.put(source2Id,hMap2);
					hMap2= new	HashMap();
				 }
				

				hMap2.put(result.getString("LEAD_SOURCE3_ID"),result.getString("LEAD_SOURCE3_NAME"));

				
				source2Id=	result.getString("LEAD_SOURCE2_ID");

			}
			if( source2Id != null)
			hMap1.put(source2Id,hMap2);
					
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return hMap1;
	}
	
	/*----------------------------------------Moved From BaseNewPortalUtils---------------------------------------------------*/
	
	
	public String getCaegoryDetailsName(String sourceID) {
        String sourceName = "";
        StringBuffer query = new StringBuffer();
        query.append("SELECT LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE LEAD_SOURCE3_ID ='");
        if (StringUtil.isValidNew(sourceID)) {
            query.append(sourceID);
            query.append("' OR LEAD_SOURCE3_NAME='");
            query.append(sourceID);
            query.append("'");
            sourceName = SQLUtil.getQueryResult(query.toString(), "LEAD_SOURCE3_NAME");
        }
        return sourceName;
    }
	

    /**
     * This method returns the SequenceMap object of Lead Source3 or Lead Source
     * Details
     */
    public SequenceMap getLeadSourceDetails() {
        SequenceMap smLeadSrcDetails = null;
        StringBuffer query = null;
        ResultSet result = null;
        try {
            Info info = null;
            query = new StringBuffer();
            query.append("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE3_ID, LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE FLAG='Y'");
            query.append(" ORDER BY LEAD_SOURCE3_NAME");
            result = QueryUtil.getResult(query.toString(), null);
            smLeadSrcDetails = new SequenceMap();
            String leadSource3 = "";
            while (result.next()) {
                String[] stateData = new String[2];
                stateData[0] = result.getString("LEAD_SOURCE3_ID");

                leadSource3 = result.getString("LEAD_SOURCE3_NAME");
                leadSource3 = leadSource3.replaceAll("'", "\'");
                leadSource3 = leadSource3.replaceAll("\"", "\\\\\"");

                stateData[1] = leadSource3;
                String dataId = result.getString("LEAD_SOURCE2_ID");

                if (smLeadSrcDetails.containsKey(dataId)) {
                    ArrayList stateList = (ArrayList) smLeadSrcDetails.get(dataId);
                    stateList.add(stateData);
                    smLeadSrcDetails.put(dataId, stateList);
                } else {
                    ArrayList stateList = new ArrayList();
                    stateList.add(stateData);
                    smLeadSrcDetails.put(dataId, stateList);
                }
            }// end while
        } catch (Exception e) {
            logger.info("ERROR: exception in SequenceMap getLeadSourceDetails ::"
                    + e);
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return smLeadSrcDetails;
    }


    /**
     * This method returns the info object of Lead Source3 or Lead Source
     * Details
     */
    public Info getLeadSourceDetails(String leadSourceId) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT LEAD_SOURCE3_ID, LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE FLAG='Y' ");
        if (leadSourceId != null && leadSourceId.length() != 0
                && !leadSourceId.equals("") && !leadSourceId.equals("null")) {
            query.append("AND LEAD_SOURCE2_ID=").append(leadSourceId);
        }
        query.append(" ORDER BY LEAD_SOURCE3_NAME");
        
        return SQLUtil.getInfoFromQuery("LEAD_SOURCE3_ID", "LEAD_SOURCE3_NAME", query.toString());
    }
    /**
     * This method returns the SequenceMap object of Lead Source3 or Lead Source
     * Details ONLY being used for Leadpage. Here WEBPAGE_FLAG will switch
     * on/off inclusion in list appearing in leadpage.jsp - Amrit Paul on
     * 0710-2005
     */
    public SequenceMap getLeadSourceDetailsForLeadPage() {
        SequenceMap smLeadSrcDetails = null;
        StringBuffer query = null;
        ResultSet result = null;
        try {
            Info info = null;
            query = new StringBuffer();
            query.append("SELECT LEAD_SOURCE2_ID, LEAD_SOURCE3_ID, LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE FLAG='Y' AND WEBPAGE_FLAG='Y'");
            query.append(" ORDER BY LEAD_SOURCE3_NAME");
            result = QueryUtil.getResult(query.toString(), null);
            smLeadSrcDetails = new SequenceMap();
            while (result.next()) {
                String[] stateData = new String[2];
                stateData[0] = result.getString("LEAD_SOURCE3_ID");
                stateData[1] = result.getString("LEAD_SOURCE3_NAME");
                String dataId = result.getString("LEAD_SOURCE2_ID");

                if (smLeadSrcDetails.containsKey(dataId)) {
                    ArrayList stateList = (ArrayList) smLeadSrcDetails.get(dataId);
                    stateList.add(stateData);
                    smLeadSrcDetails.put(dataId, stateList);
                } else {
                    ArrayList stateList = new ArrayList();
                    stateList.add(stateData);
                    smLeadSrcDetails.put(dataId, stateList);
                }
            }// end while
        } catch (Exception e) {
            logger.info("ERROR: exception in SequenceMap getLeadSourceDetails ::"
                    + e);
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return smLeadSrcDetails;
    }
    
    public String getLeadSourceName(String sourceId) {
        String leadSourceName = null;
        String query = null;
        ResultSet result = null;

        if (!StringUtil.isValidNew(sourceId)) {
            return "";
        }

        return SQLUtil.getQueryResult("SELECT LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE LEAD_SOURCE3_ID="+sourceId,
				"LEAD_SOURCE3_NAME");
    }


    /**
   	 * This method display source details of the respective source category
   	 * being selected for FS.
   	 * 
   	 * @param sourceId-String
   	 *            sourceId as an input parameter
   	 * @return ArrayList containg returnList of source details of the respective
   	 *         source category being selected
   	 */

   	public ArrayList getSourceDetailsForCategory(String sourceId) {
   		ArrayList returnList = new ArrayList();
   		String[] source = null;
   		Connection con = null;
   		PreparedStatement pstmt = null;
   		java.sql.ResultSet rs = null;
   		try {
   			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
   			pstmt = con
   					.prepareStatement("SELECT LEAD_SOURCE3_ID,LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE LEAD_SOURCE2_ID=? AND FLAG='Y' AND WEBPAGE_FLAG='Y' ORDER BY LEAD_SOURCE3_NAME");
   			pstmt.setString(1, sourceId);

   			rs = pstmt.executeQuery();

   			while (rs.next()) {
   				source = new String[2];
   				source[0] = rs.getString("LEAD_SOURCE3_ID");
   				source[1] = rs.getString("LEAD_SOURCE3_NAME");

   				returnList.add(source);
   			}

   		} catch (SQLException e) {
   			Debug.print(e);
   		} finally {
   			SQLUtil.closeStmt(pstmt);
   			SQLUtil.closeResultSet(rs);

   			//PortalUtils.closePreparedStatements(pstmt, rs);
   			try {
   				if (con != null)
   					DBConnectionManager.getInstance().freeConnection(con);
   			} catch (Exception e) {
   				logger.error("Exception ", e);
   			}
   			return returnList;
   		}

   	}
	/**
	 * This method gets the Source Name From LEAD_SOURCE3 Table for particular
	 * source Id
	 * 
	 * @param sourceId
	 *            -Input String having SourceId as parameter
	 * @return String containg the Source Name
	 * @throws ConnectionException
	 */
	public String getSourceName3(String sourceId)
			throws ConnectionException {
		Connection con = null;
		String sourceName = "Not Available";
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		System.out
				.println("SELECT LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE LEAD_SOURCE3_ID="
						+ sourceId);
		try {
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT LEAD_SOURCE3_NAME FROM FS_LEAD_SOURCE3 WHERE LEAD_SOURCE3_ID="
							+ sourceId);
			if (rs.next()) {
				sourceName = rs.getString("LEAD_SOURCE3_NAME");
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

}
