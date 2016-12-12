 /**
* Class Name	-       AssignCorpDAO
* Location		- com.appnetix.app.components.zipcodemgr.manager.dao;

*P_B_CM_64611 	 19 Oct 2015        Divanshu Verma         Owner Assignment Issue
*/

package com.home.builderforms;
import org.apache.log4j.Logger;

import com.home.builderforms.Info;
import com.home.builderforms.BaseDAO;
import com.home.builderforms.sqlqueries.ResultSet;
import java.util.*;
import java.sql.*;
import java.lang.*;

public class AssignCorpDAO extends BaseDAO{
//Logger
static Logger logger	= Logger.getLogger(AssignCorpDAO.class);








public AssignCorpDAO() {
//	this.tableAnchor = TableAnchors.SUPPLIER_VENDORS ;


}


public int updateCorporateUser(String corpUser)
{
	int flag=0;
	String query = "UPDATE CM_CORPORATE_ZIPCODE SET CORP_USER ='" + corpUser +"'";

	try{
		flag=QueryUtil.update(query,new String[]{});
	}
	catch (Exception e )
	{
		logger.error("Exception in updateFranchiseeZipcodes second: "+e);
	}
	return flag;
}

public String getRetainedCorpUser()
	{
	   StringBuffer query = new StringBuffer("SELECT DISTINCT CORP_USER FROM CM_CORPORATE_ZIPCODE");
	   ResultSet rs = QueryUtil.getResult(query.toString(), null);
		  String   result = "";

		if(rs.next())
		{
		  result=rs.getString("CORP_USER");
		}QueryUtil.releaseResultSet(rs);

		 return result;

	}

// Getting the state of  a franchisee ID
public String getState(String franchisee_no)
{
	String state = null ;
	ResultSet result=null;
	try{
		String query = "SELECT REGION_NAME FROM FRANCHISEE AS F , REGIONS AS R WHERE F.FRANCHISEE_NO= '" +Integer.parseInt(franchisee_no)+ "' AND F.REGION_NO = R.REGION_NO" ;
		//System.out.println("query in getState is " + query );

		//Integer.toString(int);
		 result =QueryUtil.getResult(query,null);
		while(result.next())
		{
			state = result.getString("REGION_NAME");
		//	System.out.println("state is " + state );
		}
	}
	catch(Exception e)
	{
		logger.error("Exception in getState: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return state;
}

// for getting the default corporate user

public String getDefaultOwner()
{
	String owner = "";
	String query = "SELECT CORP_USER FROM  CM_CORPORATE_ZIPCODE";
	ResultSet result=null;
	try {
		  result =QueryUtil.getResult(query,null);
		while(result.next())
		{
			owner = result.getString("CORP_USER");
		//	System.out.println("franchisee is " + owner );
		}
	}
	catch(Exception e)
	{
		logger.error("Exception in getDefaultOwner: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return owner ;
}
// returns list of all corporate users

//ZCUB-20150728-164 start 
// Returns Contact Owner for particular Franchise Location
public String getFranchiseeLocationUser(String franchiseeNo)
{
	String owner = "";
	String query = "SELECT FRANCHISE_USER_NO FROM  FRANCHISEE_LOC_OWNER FLO LEFT JOIN USERS U ON FLO.FRANCHISE_USER_NO=U.USER_NO WHERE FRANCHISEE_ID ="+franchiseeNo+" AND U.IS_DELETED='N' AND U.STATUS IN (1,4)";//P_B_CM_64611
	ResultSet result=null;
	try {
		  result =QueryUtil.getResult(query,null);
		if(result.next())
		{
			owner = result.getString("FRANCHISE_USER_NO");
		}
	}
	catch(Exception e)
	{
		logger.error("Exception in getFranchiseeLocationUser: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return owner ;
}
//ZCUB-20150728-164 end

public HashMap getCorporateUsers()
{
	HashMap corpusers = new HashMap();

	int count = 0 ;
	ResultSet result=null;
	try{
		String query = "SELECT USER_NO , CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME  FROM FRANCHISEE AS F , USERS AS U WHERE U.USER_LEVEL=0 AND U.STATUS='1' AND U.FRANCHISEE_NO = F.FRANCHISEE_NO  ORDER BY NAME" ;
		 result = QueryUtil.getResult(query,null);
		while(result.next())
		{
			HashMap temphash = new HashMap();
			temphash.put("1",result.getString("USER_NO")) ;
			temphash.put("2", result.getString("NAME"));
			corpusers.put( Integer.toString(count),temphash);
			count++ ;

		}

	}
	catch(Exception e)
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

		 return result;

	}


	public Info getLocationAndUser(String zipcode)
	{
	Info info =new Info();
	String query = "SELECT FRANCHISEE_ID,FRANCHISE_USERS,U.STATUS,U.IS_DELETED FROM  FRANCHISEE_ZIPCODES FZ LEFT JOIN USERS U ON FZ.FRANCHISE_USERS=U.USER_NO  WHERE ZIP_CODE='"+zipcode+"'";//P_B_CM_64612
	ResultSet result=null;
	String status="";
	try {
		  result =QueryUtil.getResult(query,null);
		while(result.next())
		{	
		info.set(FieldNames.FRANCHISEE_NO,result.getString("FRANCHISEE_ID"));
		status=result.getString("STATUS");//P_B_CM_64612 starts
		if((StringUtil.isValid(status) && ("1".equals(status) || "4".equals(status))) && (StringUtil.isValid(result.getString("IS_DELETED")) && "N".equals(result.getString("IS_DELETED"))) )
			info.set(FieldNames.CONTACT_OWNER_ID,result.getString("FRANCHISE_USERS"));
		else
			info.set(FieldNames.CONTACT_OWNER_ID,"");//P_B_CM_64612 ends
		}
	}
	catch(Exception e)
	{
		logger.error("Exception in getDefaultOwner: "+e);
	}finally
	{
		QueryUtil.releaseResultSet(result);
	}

	return info ;
}


}