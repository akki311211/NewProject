 /**
* Class Name	-       ZipCodeDAO
* Location		- com.appnetix.app.components.zipcodemgr.manager.dao;
*
**/

package com.home.builderforms;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.home.builderforms.DBConnectionManager;

import org.apache.log4j.Logger;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.ConnectionException;
import com.home.builderforms.Debug;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.StringUtil;
import com.home.builderforms.sqlqueries.ResultSet;

public class AssignZipCodeDAO extends BaseDAO{
//Logger
static Logger logger	= Logger.getLogger(AssignZipCodeDAO.class);








public AssignZipCodeDAO() {
//	this.tableAnchor = TableAnchors.SUPPLIER_VENDORS ;
	//System.out.println("in ZipCodeDao " );
	

}
// to get the List of all zip codes owned by franchisee user

public ArrayList  getOwnedZipCodeList(String franchisee_no, String fuser)
{

	String query ="SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE ASSOCIATION_TYPE IN ('O','W') AND FRANCHISEE_ID= '" + franchisee_no  +"' AND (FRANCHISE_USERS ='' OR FRANCHISE_USERS IN(SELECT USER_NO FROM USERS WHERE STATUS IN(0) AND USER_LEVEL=1)) ORDER BY ZIP_CODE";
	ArrayList zipcodes = new ArrayList();
ResultSet result=null;
	try
	{
		 result  = QueryUtil.getResult(query,null);
	
		while(result.next())
		{
			zipcodes.add(result.getString("ZIP_CODE"));
		}

		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getOwnedZipCodeList: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	//System.out.println("owned zip codes in DAO " + zipcodes);
	return zipcodes;
}



public ArrayList getAssignedZipCodeList(String franchisee_no, String fuser)
{
	String query ="SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE FRANCHISEE_ID= '" + franchisee_no  +"' and FRANCHISE_USERS = '"+fuser+"' ORDER BY ZIP_CODE";
	ArrayList zipcodes = new ArrayList();
	ResultSet result=null;
	try
	{
		 result  = QueryUtil.getResult(query,null);
	
		while(result.next())
		{
			zipcodes.add(result.getString("ZIP_CODE"));
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getOwnedZipCodeList: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return zipcodes;
}
//ZCUB-20150728-164 start  this method is used to save the contact owner for particular franchise location
public  void updatefranchiseOwner(String franchiseLocationUser,String franchiseUser){
	String query1= "SELECT FRANCHISEE_ID FROM FRANCHISEE_LOC_OWNER WHERE FRANCHISEE_ID = '"+franchiseLocationUser+"'";
	ResultSet result = null;
	ArrayList zipcodes = new ArrayList();
	try
	{
		 result  = QueryUtil.getResult(query1,null);
	
		while(result != null && result.next())
		{
			zipcodes.add(result.getString("FRANCHISEE_ID"));
		}
	}catch(Exception e){
		logger.error("Exception in updatefranchiseOwner query1: ",e);
	}
	if(zipcodes.contains(franchiseLocationUser)){
	String query = "UPDATE FRANCHISEE_LOC_OWNER SET FRANCHISE_USER_NO = '"+franchiseUser+"' WHERE FRANCHISEE_ID='"+franchiseLocationUser+"'";
	try{
		 QueryUtil.update(query,null);
	}catch(Exception e){
		logger.error("Exception in updatefranchiseOwner: ",e);
	}
	}else{
		String query = "INSERT INTO FRANCHISEE_LOC_OWNER ( FRANCHISEE_ID,FRANCHISE_USER_NO) VALUES( '"+franchiseLocationUser+"','"+franchiseUser+"')";
		try{
			 QueryUtil.update(query,null);
		}catch(Exception e){
			logger.error("Exception in updatefranchiseOwner: ",e);
		}
	}
}//ZCUB-20150728-164 end

// to get the List of all zip codes Watched by franchisee user
public ArrayList getWatchedZipCodeList(String franchisee_no , String fuser)
{
	String query ="SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE ASSOCIATION_TYPE = 'W' AND FRANCHISEE_ID= '" + franchisee_no   +"' ORDER BY ZIP_CODE";
	ArrayList zipcodes = new ArrayList();
	//System.out.println("query in getWatchedZipCodeList " + query);
	ResultSet result=null;
	try
	{
		 result  = QueryUtil.getResult(query,null);
	
		while(result.next())
		{
			zipcodes.add(result.getString("ZIP_CODE"));
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getWatchedZipCodeList: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return zipcodes;
}
// to get the List of all zip codes that are available
public ArrayList getAvailableZipCodeList(String state)
{
	String query ="select DISTINCT ZIP_CODE  FROM FRANCHISEE_ZIPCODES WHERE STATE='" + state +"' AND ( ASSOCIATION_TYPE IS NULL OR ASSOCIATION_TYPE = '' ) ORDER BY ZIP_CODE";
	//System.out.println("get available " + query);
	ArrayList zipcodes = new ArrayList();
	ResultSet result=null;
	try
	{
		 result  = QueryUtil.getResult(query,null);
	
		while(result.next())
		{
			zipcodes.add(result.getString("ZIP_CODE"));
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getAvailableZipCodeList: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return zipcodes;
}

// updating the FRANCHISEE_ZIPCODES table , for setting the data for Owned ZipCodes
public int updateOwnedZipCodes(String franchisee_no , String ownedZipCodes, String UserName)
{
	int flag = 0 ;
// commented by Dilip Kumar Gupta for BugID 59597
/*	if (ownedZipCodes == null || ownedZipCodes.equals(""))
	{
		ownedZipCodes = "-1";	
	}

	String query = "UPDATE FRANCHISEE_ZIPCODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS ='" + UserName +"'  WHERE ZIP_CODE IN ( " + ownedZipCodes + ")" ;
	String query1 = "UPDATE ZIP_CODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS ='" + UserName +"'  WHERE ZIP IN ( " + ownedZipCodes + ")" ;
*/ 
	if (ownedZipCodes == null || ownedZipCodes.equals(""))
	{
// do nothing
		return flag;
	}else{

		String query = "UPDATE FRANCHISEE_ZIPCODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS ='" + UserName +"'  WHERE ZIP_CODE IN ( " + ownedZipCodes + ")" ;
		String query1 = "UPDATE ZIP_CODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS ='" + UserName +"'  WHERE ZIP IN ( " + ownedZipCodes + ")" ;

		try
			{
				flag=QueryUtil.update(query,new String[]{}); 
				if(ModuleUtil.schedulerImplemented())
                                    flag= flag & QueryUtil.update(query1,new String[]{});
			}catch (Exception e ){
				logger.error("Exception in updateFranchiseeZipcodes second: ", e);
			}
		return flag; 
	}
}

//this for removing the franchise user association

public int updateFranchiseeZipCodes(String franchisee_no , String franchiseeZipCodes)
{
	int flag = 0 ;
	if(StringUtil.isValid(franchiseeZipCodes))
	{	
	String query = "UPDATE FRANCHISEE_ZIPCODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS =''  WHERE ZIP_CODE IN ( " + franchiseeZipCodes + ")" ;
	String query1 = "UPDATE ZIP_CODES SET FRANCHISEE_ID = '"+ franchisee_no+  "' , FRANCHISE_USERS =''  WHERE ZIP IN ( " + franchiseeZipCodes + ")" ;
	try
	{
		flag= QueryUtil.update(query,new String[]{}); 
		if(ModuleUtil.schedulerImplemented())
                    flag = flag & QueryUtil.update(query1,new String[]{});
	}catch (Exception e )
	{
		logger.error("Exception in updateFranchiseeZipCodes : "+e);
	}
	}
	return flag;
}

// getiing list of all active Franchisees

public HashMap getFranchisee()
{
	HashMap franchisee = new HashMap();
	int count = 0 ;
	ResultSet result=null;
	try
	{
		String query = "SELECT DISTINCT FRANCHISEE.FRANCHISEE_NO,FRANCHISEE_NAME FROM FRANCHISEE, OWNERS  , AREAS WHERE FRANCHISEE.FRANCHISEE_NO=OWNERS.FRANCHISEE_NO  AND AREAS.AREA_ID = FRANCHISEE.AREA_ID AND IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND (IS_FRANCHISEE='Y' OR (IS_STORE='Y'  AND IS_STORE_ARCHIVED='N' )) ORDER BY  FRANCHISEE_NAME";
		 result  = QueryUtil.getResult(query,null);

		while(result.next())
		{
			HashMap temphash = new HashMap();
			temphash.put("1",result.getString("FRANCHISEE_NO")) ;
			temphash.put("2", result.getString("FRANCHISEE_NAME"));
			franchisee.put( Integer.toString(count),temphash);
			count++ ;
			//franchisee.add(result.getString("FRANCHISEE_NO"));
		}

		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getFranchisee: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return franchisee;
}

// returns the list of all Franchis users for a particular Franchise ID

public HashMap getFranchiseUsers(String franchisee_no)
{
	HashMap franchiseUsers = new HashMap();
	int count = 0 ;
	ResultSet result=null;
	try
	{
		String query = "SELECT USER_NO, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME ,U.FRANCHISEE_NO FROM FRANCHISEE_USERS FU, USERS U WHERE FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO  AND U.STATUS <> 0  AND U.IS_DELETED='N' AND FU.FRANCHISEE_NO=" + Integer.parseInt(franchisee_no) + "  ORDER BY NAME " ;
		 result  = QueryUtil.getResult(query,null);

		while(result.next())
		{
			HashMap temphash = new HashMap();
			temphash.put("1",result.getString("USER_NO")) ;
			temphash.put("2", result.getString("NAME"));
			franchiseUsers.put( Integer.toString(count),temphash);
			count++ ;
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getFranchiseUsers: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return franchiseUsers;
}

// returns the franchisee user that owns or watch the passed zipcode

public String getFranchiseUserfromzip (String zipcode)
{
	String franchisee = "";
	String query = "SELECT FRANCHISE_USERS FROM FRANCHISEE_ZIPCODES WHERE ZIP_CODE ='" + zipcode +"'";
	//System.out.println("query in getFranchiseefromzip "+ query );
ResultSet result=null;
	try 
	{
		 result =QueryUtil.getResult(query,null);
		
		while(result.next())
		{
			franchisee = result.getString("FRANCHISE_USERS");
			
			if(!isDeletedFranchiseUser(franchisee))
				franchisee=null;

			//	System.out.println("franchisee is " + franchisee );
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getFranchiseUserfromzip: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return franchisee ;
}

// for getting the default corporate user

public ArrayList<String> getFranchiseAndUserzip(String zipcode)
{
	ArrayList<String> franchisee = null;
	String query = "SELECT FRANCHISEE_ID,FRANCHISE_USERS FROM FRANCHISEE_ZIPCODES FZ LEFT JOIN USERS U ON FZ.FRANCHISE_USERS = U.USER_NO WHERE U.IS_DELETED='N' AND  U.STATUS <> 0 AND FZ.ZIP_CODE ='" + zipcode +"'";
	ResultSet result=null;
	try 
	{
		result =QueryUtil.getResult(query,null);
		franchisee = new ArrayList<String>();
		while(result.next())
		{
			franchisee.add(result.getString("FRANCHISE_USERS"));
			franchisee.add(result.getString("FRANCHISEE_ID"));
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getFranchiseUserfromzip: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return franchisee ;
}

public String getDefaultOwner()
{
	String owner = "";
	String query = "SELECT FRANCHISEE_ID FROM  FRANCHISEE_ZIPCODES WHERE STATE='-1'  ";
	ResultSet result=null;
	try 
	{
		 result =QueryUtil.getResult(query,null);
		
		while(result.next())
		{
			owner = result.getString("FRANCHISEE_ID");
			//	System.out.println("franchisee is " + owner );
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getDefaultOwner: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return owner ;
}

// returns list of all corporate users

public HashMap getCorporateUsers()
{
	HashMap corpusers = new HashMap();
	int count = 0 ;
	ResultSet result=null;
	try
	{
		String query = "SELECT USER_NO , CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME  FROM FRANCHISEE AS F , USERS AS U WHERE U.USER_LEVEL=0 AND U.FRANCHISEE_NO = F.FRANCHISEE_NO  ORDER BY NAME" ;
		 result = QueryUtil.getResult(query,null);
	
		while(result.next())
		{
			HashMap temphash = new HashMap();
			temphash.put("1",result.getString("USER_NO")) ;
			temphash.put("2", result.getString("NAME"));
			corpusers.put( Integer.toString(count),temphash);
			count++ ;
		}
		result  = null;
	}catch(Exception e)
	{
		logger.error("Exception in getCorporateUsers: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return corpusers;
}
// returns Franchisee name corresponding to Franchisee ID

public String getFranName(String FranNo)
{
	StringBuffer query = new StringBuffer("SELECT FRANCHISEE_NAME FROM FRANCHISEE WHERE IS_BASE_ADMIN = 'N' AND FRANCHISEE_NO = " + FranNo);
	ResultSet rs = QueryUtil.getResult(query.toString(), null);
	String   result = "";

	if(rs.next())
	{
		result=rs.getString("FRANCHISEE_NAME");
	}
	//result  = null;
	return result;
}
// returns the user num corresponding to franchisee_no
	public String getUserNum(String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT USER_NO FROM USERS WHERE FRANCHISEE_NO=" + FranNo);
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String   result = "";

		if(rs.next())
		{
			result=rs.getString("USER_NO");
		}
		//result  = null;
		return result;
	}
// Method for sending mail
	public void sendMail(String from,String user_no,String subject,String to,String user_no2,String message) throws ConnectionException
	{
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt1 = con.prepareStatement("INSERT INTO MESSAGE_DETAIL (FROM_USER_NAME,FROM_USER_NO,SUBJECT,TEXT,RCPT_TO,MESSAGE_DATE,RCPT_CC) VALUES(?,?,?,?,?,NOW(),'')");
			pstmt1.setString(1,from);
			pstmt1.setString(2,user_no);
			pstmt1.setString(3,subject);
			pstmt1.setString(4,message);
			pstmt1.setString(5,to);
			pstmt1.executeUpdate();
//This query has been changed
//INSERT INTO MESSAGE_RCPT (MESSAGE_NO,TO_USER_NO,FOLDER_NO,TO_CC_BCC) VALUES (LAST_INSERT_ID(),?,-1,?)
			pstmt2 = con.prepareStatement("INSERT INTO MESSAGE_RCPT (MESSAGE_NO,TO_USER_NO,TO_CC_BCC) VALUES (LAST_INSERT_ID(),?,?)");
			pstmt2.setInt(1,Integer.parseInt(user_no2));
			pstmt2.setString(2,"T");
			pstmt2.executeUpdate();
		}catch (SQLException e)
		{
			Debug.print(e);
		}finally 
		{
			try
			{
				pstmt1.close();
				pstmt2.close();
			}catch (Exception e)
			{
			}

			DBConnectionManager.getInstance().freeConnection(con);
			Debug.print(" Trouble ticket Updated ");
		}
	}

// returns the email id of Franchise
	
	public String getEmailofFran(String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT EMAIL_ID FROM FRANCHISEE_USERS WHERE FRANCHISEE_USER_NO IN (SELECT USER_IDENTITY_NO FROM USERS WHERE USER_NO=" + FranNo + ")");
		//  System.out.println("query getEmailofFran " + query.toString());
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String   result = "";

		if(rs.next())
		{
			result=rs.getString("EMAIL_ID");
		}
		rs = null;
		return result;

	}

	public String getEmailofCorp(String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT EMAIL_ID FROM FRANCHISEE WHERE FRANCHISEE_NO IN (SELECT FRANCHISEE_NO FROM USERS WHERE USER_NO = "+FranNo + ")");
		//    System.out.println("query getEmailofCorp " + query.toString());
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String   result = "";

		if(rs.next())
		{
		  result=rs.getString("EMAIL_ID");
		}
		rs = null;
		return result;
	}

	public boolean isDeletedFranchiseUser(String userNo)
	{
		boolean flag = false;
		String query = "SELECT USER_NO FROM USERS WHERE USER_NO ='" + userNo +"' AND IS_DELETED='N' AND  STATUS <> 0";
		//System.out.println("query in getFranchiseefromzip "+ query );
		ResultSet result = null;
		
		try 
		{
			result = QueryUtil.getResult(query,null);
			
			if(result.next())
			{
				flag=true;
			}
		}catch(Exception e)
		{
			result = null;
			logger.error("Exception in getFranchiseUserfromzip: "+e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return flag ;
	}
}
