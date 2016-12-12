/**
 created by binu tomar
 * Class Name	-       ZipCodeDAO
 * Location		- com.appnetix.app.components.zipcodemgr.manager.dao;
 */
/* 
---------------------------------------------------------------------------------------------------------------------------------
Version No.				Date		By			Against						Function Changed          	Comments
---------------------------------------------------------------------------------------------------------------------------------

BB_ADMIN_CM_Zip_Loc_Bug_36424  01 July2008	Yogesh T   			removed all word 'DESC' from queries where zip Codes were desc. 
P_B_FIM_63378         9 March 2011   Neeti Solanki                for getting the zip codes of all multiunit franchisees associated with an owner
P_B_FIM_63341       11 March 2011    Neeti Solanki               for changing the existing zip code functionality in regional
---------------------------------------------------------------------------------------------------------------------------------
 
 */
package com.home.builderforms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import com.home.builderforms.AdminMgr;
import com.home.builderforms.DBConnectionManager;

import org.apache.log4j.Logger;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.ConnectionException;
import com.home.builderforms.Constants;
import com.home.builderforms.Debug;
import com.home.builderforms.FieldNames;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

public class ZipCodeDAO extends BaseDAO
{
	// Logger
	static Logger logger = Logger.getLogger(ZipCodeDAO.class);







	public ZipCodeDAO() {

	}

//	 to get the List of all zip codes owned by franchisee user

	public ArrayList getOwnedZipCodeList (String franchisee_no)
	{
		String query = "SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE ASSOCIATION_TYPE = 'O' AND FRANCHISEE_ID= '" + franchisee_no + "' ORDER BY ZIP_CODE";
		ResultSet result = null;
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIP_CODE"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}

		// System.out.println("owned zip codes in DAO " + zipcodes);
		return zipcodes;
	}

	public HashMap getzipcode (String franchisee_no, String fuser)
	{
		HashMap zipCode = new HashMap();
		String query=null;
		String query1;

		ResultSet result = null;
		ResultSet result1 = null;
		int count = 0;
		try
		{
			if (fuser.equals("-1"))
			{
				query1 = "SELECT USER_NO FROM FRANCHISEE_USERS FU, USERS U WHERE FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO  AND U.IS_DELETED='N' AND FU.STATUS <> 0 AND U.STATUS <> 0 AND FU.FRANCHISEE_NO IN (" + franchisee_no + ")   ";
				result1 = QueryUtil.getResult(query1, new Object[] {});
				String userno = "";

				while (result1.next())
				{
					if (userno.equals(""))
					{
						userno = "'" + result1.getString("USER_NO") + "'";
					} else
					{
						userno = userno + ",'" + result1.getString("USER_NO") + "'";
					}
					// userno = userno + result1.getString("USER_NO") ;
					// userno = ",";
				}

				if (userno == null && userno.equals(""))
				{
					userno = "";
				}
				if(StringUtil.isRequestParamValid(userno) && StringUtil.isRequestParamValid(franchisee_no)){
					query = "select FRANCHISEE_NAME, ZIP_CODE, CONCAT(C.FIRST_NAME, ' ', C.LAST_NAME) AS NAME from FRANCHISEE_ZIPCODES A, FRANCHISEE B, FRANCHISEE_USERS C, USERS D where A.franchisee_id in (" + franchisee_no + ")  AND D.IS_DELETED='N' and B.FRANCHISEE_NO = A.franchisee_id and A.franchise_users in (" + userno + ") and D.USER_NO in (" + userno
						+ ")  and C.FRANCHISEE_USER_NO = D.USER_IDENTITY_NO and D.USER_NO =A.franchise_users ORDER BY  ZIP_CODE";
				}
			} else
			{
				if (fuser == null && fuser.equals(""))
				{
					fuser = "";
				}
				if(StringUtil.isRequestParamValid(fuser) && StringUtil.isRequestParamValid(franchisee_no)){
					query = "select FRANCHISEE_NAME,ZIP_CODE,CONCAT(C.FIRST_NAME,' ',C.LAST_NAME) AS NAME from FRANCHISEE_ZIPCODES A, FRANCHISEE B, FRANCHISEE_USERS C, USERS D where A.franchisee_id in (" + franchisee_no + ")  AND D.IS_DELETED='N' and B.FRANCHISEE_NO = A.franchisee_id and A.franchise_users in (" + fuser + ") and D.USER_NO in (" + fuser
					+ ")  and C.FRANCHISEE_USER_NO = D.USER_IDENTITY_NO and D.USER_NO =A.franchise_users ORDER BY  ZIP_CODE";
				}
			}
			if(StringUtil.isRequestParamValid(query)){
				System.out.println(query);
				result = QueryUtil.getResult(query,null);

				while (result.next())
				{
					HashMap temphash = new HashMap();
					temphash.put("1", result.getString("FRANCHISEE_NAME"));
					temphash.put("2", result.getString("ZIP_CODE"));
					temphash.put("3", result.getString("NAME"));
					zipCode.put(Integer.toString(count), temphash);
					count++;

				}
			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
			QueryUtil.releaseResultSet(result1);
		}

		return zipCode;
	}

	public HashMap getZipcodes (String franchisee_no)
	{
		// Info info = new Info();
		HashMap zipCode = new HashMap();
		ResultSet result = null;

		int count = 0;
		try
		{

			// MODIFIED BY BINU ON 24TH OCT 06
			// String query ="SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE
			// FROM FRANCHISEE_ZIPCODES,FRANCHISEE, OWNERS , AREAS WHERE
			// FRANCHISEE.FRANCHISEE_NO=OWNERS.FRANCHISEE_NO AND
			// FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID AND
			// AREAS.AREA_ID = FRANCHISEE.AREA_ID AND IS_ADMIN='N' AND
			// FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND
			// ASSOCIATION_TYPE IN('O','W') AND FRANCHISEE_ID IN (
			// "+franchisee_no+" ) ORDER BY ZIP_CODE ";
			String query = "";
			if (franchisee_no != null && franchisee_no.trim().equals("0"))
			{
				query = "SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE FROM FRANCHISEE_ZIPCODES LEFT JOIN FRANCHISEE ON FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID  WHERE   IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND ASSOCIATION_TYPE IN('O','W')  ORDER BY ZIP_CODE ";
			} else
			{
				query = "SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE FROM FRANCHISEE_ZIPCODES LEFT JOIN FRANCHISEE ON FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID  WHERE   IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND ASSOCIATION_TYPE IN('O','W') AND FRANCHISEE_ID IN ( " + franchisee_no + " )   ORDER BY ZIP_CODE ";
			}

			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				HashMap temphash = new HashMap();
				temphash.put("1", result.getString("FRANCHISEE_NAME"));
				temphash.put("2", result.getString("ZIP_CODE"));
				temphash.put("3", result.getString("ASSOCIATION_TYPE"));
				zipCode.put(Integer.toString(count), temphash);
				count++;

			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
			}
		return zipCode;
	}

	// changed by binu on 24th oct 06

	public SequenceMap getexportzipcodes (String franchisee_no)
	{
		// MODIFIED BY BINU ON 24TH OCT 06
		// String query ="SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE FROM
		// FRANCHISEE_ZIPCODES,FRANCHISEE, OWNERS , AREAS WHERE
		// FRANCHISEE.FRANCHISEE_NO=OWNERS.FRANCHISEE_NO AND
		// FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID AND
		// AREAS.AREA_ID = FRANCHISEE.AREA_ID AND IS_ADMIN='N' AND
		// FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND
		// ASSOCIATION_TYPE IN('O','W') AND FRANCHISEE_ID IN ( "+franchisee_no+"
		// ) ORDER BY ZIP_CODE ";//BB_ADMIN_CM_Zip_Loc_Bug_36424
		String query = "";
		if (franchisee_no != null && franchisee_no.trim().equals("0"))
		{
			query = "SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE FROM FRANCHISEE_ZIPCODES LEFT JOIN FRANCHISEE ON FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID   WHERE IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND ASSOCIATION_TYPE IN('O','W') ORDER BY ZIP_CODE";//BB_ADMIN_CM_Zip_Loc_Bug_36424
		} else
		{
			query = "SELECT FRANCHISEE_NAME,ZIP_CODE,ASSOCIATION_TYPE FROM FRANCHISEE_ZIPCODES LEFT JOIN FRANCHISEE ON FRANCHISEE.FRANCHISEE_NO=FRANCHISEE_ZIPCODES.FRANCHISEE_ID   WHERE IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND IS_FRANCHISEE IN ('Y','N') AND ASSOCIATION_TYPE IN('O','W') AND FRANCHISEE_ID IN (  " + franchisee_no + " )   ORDER BY ZIP_CODE";//BB_ADMIN_CM_Zip_Loc_Bug_36424
		}

		ResultSet result = null;
		SequenceMap stackMap = new SequenceMap();
		// SequenceMap dataRow = new SequenceMap();
		// SequenceMap obj = new SequenceMap();
		Info info = new Info();
		// ArrayList orderlist = new ArrayList();
		// Stack s1 = new Stack();

		// int count = 0 ;
		try
		{

			result = QueryUtil.getResult(query,null);

			while (result.next())
			{
				info = new Info();
				info.set(FieldNames.FRANCHISEE_NAME, result.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.ZIP_CODE, result.getString("ZIP_CODE"));
				String Association_Type = result.getString("ASSOCIATION_TYPE");
				if (Association_Type.equals("O"))
					Association_Type = "Owned Zip / Postal Code";
				else if (Association_Type.equals("W"))
					Association_Type = "Watched Zip / Postal Code";
				info.set(FieldNames.ASSOCIATION_TYPE, Association_Type);

				stackMap.put(result.getString("ZIP_CODE"), info);

			}
			// orderlist.add("test");
			// stackMap.put("test",s1);

			// ArrayList headerlist = new ArrayList();
			// headerlist.add("Franchise ID");
			// headerlist.add("Zip Code");
			// headerlist.add("Association Type");
			// obj.put("orderlist",orderlist);
			// obj.put("stackMap",stackMap);
			// obj.put("headerlist",headerlist);
		} catch (Exception e)
		{
			logger.error("Exception in getexportzipcodes: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return stackMap;
	}

	public SequenceMap getexportusers (String franchisee_no, String fuser)
	{
		String query;
		String query1;
		ResultSet result = null;
		ResultSet result1 = null;
		int count = 0;

		if (fuser.equals("-1"))
		{
			if (franchisee_no != null && !franchisee_no.equals(""))
			{
				query1 = "SELECT USER_NO FROM FRANCHISEE_USERS FU, USERS U WHERE FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO AND U.IS_DELETED='N'  AND FU.FRANCHISEE_NO IN (" + franchisee_no + ")   ";
				result1 = QueryUtil.getResult(query1, new Object[] {});
				String userno = "";

				while (result1.next())
				{
					if (userno.equals(""))
					{
						userno = "'" + result1.getString("USER_NO") + "'";
					} else
					{
						userno = userno + ",'" + result1.getString("USER_NO") + "'";
					}
				}

				if (userno == null || userno.equals(""))
				{
					userno = "-1";
				}

				query = "select  FRANCHISEE_NAME,ZIP_CODE,CONCAT(C.FIRST_NAME,' ',C.LAST_NAME) AS  NAME from FRANCHISEE_ZIPCODES A, FRANCHISEE B, FRANCHISEE_USERS C, USERS D where A.FRANCHISEE_ID IN (" + franchisee_no + ")  AND D.IS_DELETED='N' and B.FRANCHISEE_NO = A.FRANCHISEE_ID and A.FRANCHISE_USERS in (" + userno + ") and D.USER_NO in (" + userno
						+ ")  and C.FRANCHISEE_USER_NO = D.USER_IDENTITY_NO and D.USER_NO =A.FRANCHISE_USERS ORDER BY  ZIP_CODE";//BB_ADMIN_CM_Zip_Loc_Bug_36424

				try
				{
					result = QueryUtil.getResult(query,null);
				} catch (Exception e)
				{
					logger.error("Exception in getexportzipcodes: ", e);
				}
			}
		} else
		{
			if (franchisee_no != null && !franchisee_no.equals(""))
			{
				if (fuser == null || fuser.equals(""))
				{
					fuser = "-1";
				}

				query = "select  FRANCHISEE_NAME,ZIP_CODE,CONCAT(C.FIRST_NAME,' ',C.LAST_NAME) AS NAME from FRANCHISEE_ZIPCODES A, FRANCHISEE B, FRANCHISEE_USERS C, USERS D where A.FRANCHISEE_ID in (" + franchisee_no + ") and B.FRANCHISEE_NO = A.FRANCHISEE_ID  AND D.IS_DELETED='N' and A.FRANCHISE_USERS in (" + fuser + ") and D.USER_NO in (" + fuser
						+ ")  and C.FRANCHISEE_USER_NO = D.USER_IDENTITY_NO and D.USER_NO =A.FRANCHISE_USERS ORDER BY ZIP_CODE";//BB_ADMIN_CM_Zip_Loc_Bug_36424

				try
				{
					result = QueryUtil.getResult(query,null);
				} catch (Exception e)
				{
					logger.error("Exception in getexportzipcodes: ", e);
				}
			}
		}

		ArrayList zipcodes = new ArrayList();
		SequenceMap stackMap = new SequenceMap();
		Info info = new Info();
		// SequenceMap dataRow = new SequenceMap();
		// SequenceMap obj = new SequenceMap();
		// ArrayList orderlist = new ArrayList();
		// Stack s1 = new Stack();

		try
		{

			// result = QueryUtil.getResult(query,null);

			while (result.next())
			{
				info = new Info();

				info.set(FieldNames.FRANCHISEE_NAME, result.getString("FRANCHISEE_NAME"));
				info.set(FieldNames.ZIP_CODE, result.getString("ZIP_CODE"));
				info.set("NAME", result.getString("NAME"));

				stackMap.put(result.getString("ZIP_CODE"), info);
			}

		} catch (Exception e)
		{
			logger.error("Exception in getexportzipcodes: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
			QueryUtil.releaseResultSet(result1);
		}

		return stackMap;
	}

	public ArrayList getWatchedZipCodeList (String franchisee_no)
	{
		ResultSet result = null;
		String query = "SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE ASSOCIATION_TYPE = 'W' AND FRANCHISEE_ID= '" + franchisee_no + "'  ORDER BY ZIP_CODE";
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIP_CODE"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getWatchedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return zipcodes;

	}

	public ArrayList getAvailableZipCodeList (String state)
	{
		ResultSet result = null;
		String query = "SELECT DISTINCT ZIP_CODE  FROM FRANCHISEE_ZIPCODES ZIPS LEFT JOIN FRANCHISEE F ON ZIPS.FRANCHISEE_ID=F.FRANCHISEE_NO  WHERE ZIPS.STATE='" + state + "' AND ( ASSOCIATION_TYPE IS NULL OR ASSOCIATION_TYPE = '' OR (ASSOCIATION_TYPE IS NOT NULL AND F.STATUS IN(0,4))) ORDER BY ZIP_CODE";//To make availability of Deactivate Franchisee Zipcodes in Available zipcodes combo  
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIP_CODE"));
			}

		} catch (Exception e)
		{
			logger.error("Exception in getAvailableZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return zipcodes;
	}

	public int updateOwnedZipCodes (String franchisee_no, String ownedZipCodes)
	{
		int flag = 0;
		String query = "UPDATE FRANCHISEE_ZIPCODES SET ASSOCIATION_TYPE = 'O' , FRANCHISEE_ID = '" + franchisee_no + "', FRANCHISE_USERS=''  WHERE ZIP_CODE IN ( " + ownedZipCodes + ")"; //P_CM_B_76495
		String query1 = "UPDATE ZIP_CODES SET ASSOCIATION_TYPE = 'O' , FRANCHISEE_ID = '"+ franchisee_no+  "', FRANCHISE_USERS=''  WHERE ZIP IN ( " + ownedZipCodes + ") AND (FRANCHISE_USERS is null  OR FRANCHISE_USERS ='')" ;

		// System.out.println(query);
		try
		{
			flag = QueryUtil.update(query, new String[] {});
			if(ModuleUtil.schedulerImplemented())
                            flag = flag & QueryUtil.update(query1, new String[] {});
		} catch (Exception e)
		{
			logger.error("Exception in updateFranchiseeZipcodes second: " + e);
		}
		return flag;
	}

	public int updateWatchedZipCodes (String franchisee_no, String watchedZipCodes)
	{
		int flag = 0;
		if (watchedZipCodes != null && !watchedZipCodes.equals("") && franchisee_no != null && !franchisee_no.equals(""))
		{
			String query = "UPDATE FRANCHISEE_ZIPCODES SET ASSOCIATION_TYPE = 'W' , FRANCHISEE_ID = '" + franchisee_no + "', FRANCHISE_USERS=''  WHERE ZIP_CODE IN ( " + watchedZipCodes + ") AND (FRANCHISE_USERS is null  OR FRANCHISE_USERS ='')";//P_B_CM_9657
			String query1 = "UPDATE ZIP_CODES SET ASSOCIATION_TYPE = 'W' , FRANCHISEE_ID = '"+ franchisee_no+  "', FRANCHISE_USERS=''  WHERE ZIP IN ( " + watchedZipCodes + ") AND (FRANCHISE_USERS is null  OR FRANCHISE_USERS ='')" ;//P_B_CM_9657
			// System.out.println(query);
			try
			{
				flag = QueryUtil.update(query, new String[] {});
				if(ModuleUtil.schedulerImplemented())
                                    flag = flag & QueryUtil.update(query, new String[] {});
			} catch (Exception e)
			{
				logger.error("Exception in updateWatchedZipCodes second: " + e);
			}
		}
		return flag;
	}

	// updating the FRANCHISEE_ZIPCODES table , for setting the data for
	// Available ZipCodes
	public int updateAvailableZipCodes (String availableZipCodes)
	{
		int flag = 0;

		String querytemp = "UPDATE FRANCHISEE_ZIPCODES SET ASSOCIATION_TYPE = '',FRANCHISEE_ID = '',FRANCHISE_USERS=''   WHERE ZIP_CODE IN (" + availableZipCodes + ")";
		String querytemp1="UPDATE ZIP_CODES SET ASSOCIATION_TYPE = '',FRANCHISEE_ID = '',FRANCHISE_USERS=''   WHERE ZIP IN (" + availableZipCodes +")";
		try
		{
			flag = QueryUtil.update(querytemp, new String[] {});
			if(ModuleUtil.schedulerImplemented())
                            flag = flag & QueryUtil.update(querytemp1, new String[] {});
		} catch (Exception e)
		{
			logger.error("Exception in updateAvailableZipCodes updatingzipcodes: " + e);
		}

		return flag;
	}

	// inserting new Zip Codes
	// modified by binu 0n 8th nov
	// To avoid duplicay of zipcodes on adding through .csv file.
	public int insertZipcodes (HashSet zipcodes, String state, ArrayList repeats)
	{
		insertZipcodesService314(zipcodes, state, repeats);
		System.out.println("in inserting zipcodes" + zipcodes.size());
		String query = "";
		String query1 = "";
		int flag = 0;
		query = "INSERT INTO FRANCHISEE_ZIPCODES ( STATE,ZIP_CODE) VALUES ('" + state + "' , '";
		try
		{
			if (repeats == null || repeats.isEmpty())
			{
				// System.out.println("repeats are null");
				Iterator it = zipcodes.iterator();
				for (int i = 1; it.hasNext(); i++)
				{
					String el = (String) it.next();
					query1 = query + el + "')";
					System.out.println("updating zipcodes   " + query1);
					flag = QueryUtil.update(query1, new String[] {});
				}

			} else
			{
				// System.out.println("repeats are not null" + repeats);
				Iterator it = zipcodes.iterator();
				for (int i = 1; it.hasNext(); i++)
				{
					String el = (String) it.next();
					if (!repeats.contains(el))
					{
						query1 = query + el + "')";

						flag = QueryUtil.update(query1, new String[] {});
					}
				}
			}
		} catch (Exception e)
		{
			logger.error("Exception in updateAvailableZipCodes second: " + e);
		}

		return flag;

	}

//	 inserting new Zip Codes
//	 modified by binu 0n 8th nov
//	 To avoid  duplicay of zipcodes on adding through .csv file.
	public int  insertZipcodesService314(HashSet zipcodes , String state , ArrayList repeats)
	{
		System.out.println("in inserting zipcodes" +  zipcodes.size());
		String query ="" ;
		String query1="" ;
		int flag = 0 ;
		String [] temp=null;
		query = "INSERT INTO ZIP_CODES ( STATE,ZIP,LATITUDE,LONGITUDE) VALUES ('" + state + "' , '" ;
		try{
	 	if (repeats == null || repeats.isEmpty())
		 {
		 	//System.out.println("repeats are null");
			Iterator it=zipcodes.iterator();
			
			for (int i =1 ; it.hasNext() ; i++ )
			{
			String el=(String)it.next();
			temp=el.split("#");
			el=temp[0];
			String lati=temp[1];
			String longi=temp[2];
			
				query1 = query + el + "', '"+lati+"', '"+longi+"')";
				System.out.println("updating zipcodes   " + query1);
				flag=QueryUtil.update(query1,new String[]{});
			}


	 	}
		else{
			//System.out.println("repeats are not null" + repeats);
			Iterator it=zipcodes.iterator();
		
			for (int i =1 ;  it.hasNext() ; i++ )
			{
				String el=(String)it.next();
				temp=el.split("#");
				el=temp[0];
				String lati=temp[1];
				String longi=temp[2];
				if (!repeats.contains(el)){
					
					query1 = query + el + "', '"+lati+"', '"+longi+"')";
					
					flag=QueryUtil.update(query1,new String[]{});
				}
			}
		}
		}
		catch (Exception e )
		{
		logger.error("Exception in updateAvailableZipCodes second: "+e);
		}

		return flag ;




	}
	// getiing list of all active Franchisees
	public HashMap getFranchisee ()
	{
		ResultSet result = null;
		HashMap franchisee = new HashMap();
		int count = 0;
		try
		{
			String query = "SELECT DISTINCT FRANCHISEE.FRANCHISEE_NO,FRANCHISEE_NAME FROM FRANCHISEE, OWNERS  , AREAS WHERE FRANCHISEE.FRANCHISEE_NO=OWNERS.FRANCHISEE_NO  AND AREAS.AREA_ID = FRANCHISEE.AREA_ID AND IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('1','3') AND (IS_FRANCHISEE='Y' OR (IS_STORE='Y'  AND IS_STORE_ARCHIVED='N' )) ORDER BY  FRANCHISEE_NAME";

			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				HashMap temphash = new HashMap();
				temphash.put("1", result.getString("FRANCHISEE_NO"));
				temphash.put("2", result.getString("FRANCHISEE_NAME"));
				franchisee.put(Integer.toString(count), temphash);
				count++;

				// franchisee.add(result.getString("FRANCHISEE_NO"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getFranchisee: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return franchisee;
	}

	// getting list of all zipcodes owned or watched by a Franchisee locastion
	// that is terminated

	public String getZipsforInactive (String fran_no)
	{
		ResultSet result = null;
		String zip = "";
		String query = "SELECT  ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE FRANCHISEE_ID ='" + fran_no + "' AND ASSOCIATION_TYPE IN ('W' ,'O') ORDER BY ZIP_CODE";
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zip = zip + "," + result.getString("ZIP_CODE");
			}
		} catch (Exception e)
		{
			logger.error("Exception in getZipsforInactive: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}
		if (zip.length() > 0)
			zip = zip.substring(1);
		// System.out.println("zip string in DAO " + zip);
		return zip;
	}

	// getting list of all terminated fracnhisees

	public ArrayList getTerminatedFranchisee ()
	{

		ArrayList franchisee = new ArrayList();
		ResultSet result = null;

		try
		{
			String query = "SELECT DISTINCT FRANCHISEE.FRANCHISEE_NO,FRANCHISEE_NAME FROM FRANCHISEE, OWNERS  , AREAS WHERE FRANCHISEE.FRANCHISEE_NO=OWNERS.FRANCHISEE_NO  AND AREAS.AREA_ID = FRANCHISEE.AREA_ID AND IS_ADMIN='N' AND FRANCHISEE.STATUS IN ('0','4') AND IS_FRANCHISEE IN ('Y','N')  ORDER BY  FRANCHISEE_NAME";

			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				franchisee.add(result.getString("FRANCHISEE_NO"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getTerminatedFranchisee: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return franchisee;
	}

	// Resetting the Owned or Watched zipcodes for a terminated Franchisee

	public int UpdateInvalidFranZips (String invalidFran_no, String owner)
	{
		String query = "";
		int flag = 0;
		if (invalidFran_no != null && !invalidFran_no.equals("") && !invalidFran_no.equals("-1"))
		{
			query = "UPDATE FRANCHISEE_ZIPCODES SET ASSOCIATION_TYPE='' , FRANCHISEE_ID='' ,FRANCHISE_USERS='' WHERE FRANCHISEE_ID IN (" + invalidFran_no + ")";

			try
			{
				flag = QueryUtil.update(query, new String[] {});
			} catch (Exception e)
			{
				logger.error("Exception in UpdateInvalidFranZips second: " + e);
			}
		}
		return flag;
		// return 0;
	}

	// Updating the contact details of a customer which is assigned to
	// Terminated Franchisee
	public int UpdateContactDetails (String invalidFran_no, String owner)
	{

		String query = "";
		int flag = 0;
		if (invalidFran_no != null && !invalidFran_no.equals("") && !invalidFran_no.equals("-1"))
		{
			query = "UPDATE CM_CONTACT_DETAILS SET  FRANCHISEE_NO=0,OWNER_TYPE='C',CONTACT_OWNER_ID='" + owner + "' WHERE FRANCHISEE_NO IN(" + invalidFran_no + ")";

			try
			{
				flag = QueryUtil.update(query, new String[] {});
			} catch (Exception e)
			{
				logger.error("Exception in UpdateContactDetails second: " + e);
			}
		}
		return flag;
	}

	// returns the List of all the states .
	public ArrayList getState ()
	{
		ArrayList state = new ArrayList();
		ResultSet result = null;
		try
		{
			String query = "SELECT DISTINCT REGION_NAME FROM REGIONS WHERE COUNTRY_ID=1 ORDER BY REGION_NAME ";
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				state.add(result.getString("REGION_NAME"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getState: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return state;
	}

	// Getting the state of a franchisee ID
	public String getState (String franchisee_no)
	{
		String state = null;
		ResultSet result = null;
		try
		{
			String query = "SELECT REGION_NAME FROM FRANCHISEE AS F , REGIONS AS R WHERE F.FRANCHISEE_NO= '" + Integer.parseInt(franchisee_no) + "' AND F.REGION_NO = R.REGION_NO";

			// Integer.toString(int);
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				state = result.getString("REGION_NAME");
				// System.out.println("state is " + state );
			}
		} catch (Exception e)
		{
			logger.error("Exception in getState: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return state;
	}

	// to find if the added zipcodes already exists
	// modified by binu 0n 8th nov
	// To avoid duplicay of zipcodes on adding through .csv file.
	public ArrayList existszip (HashSet zip, String state)
	{
		ArrayList repeats = new ArrayList(0);

		ResultSet result = null;
		int exist = 0;
		String query = "SELECT COUNT(*) FROM FRANCHISEE_ZIPCODES  WHERE ZIP_CODE = '";
		String query1 = "";
		// System.out.println("zip.length " + zip.length);
		try
		{
			Iterator it = zip.iterator();
			for (int i = 1; it.hasNext(); i++)
			{
				String el = (String) it.next();
				query1 = query + el + "'";
				// System.out.println("query in existsZip() is ---" + query1);
				result = QueryUtil.getResult(query1, new Object[] {});
				while (result.next())
				{
					exist = Integer.parseInt(result.getString("COUNT(*)"));
					// System.out.println("exist " + exist);
				}
				if (exist != 0)
				{
					repeats.add(el);
				}
			}
			return repeats;
		} catch (Exception e)
		{
			Debug.print(e);
			return null;
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}

	}

	// Returns the Fracnhsie ID which owns or watches the passed zipcode

	public String getFranchiseefromzip (String zipcode)
	{
		String franchisee = "";
		ResultSet result = null;
		String query = "SELECT FRANCHISEE_ID FROM FRANCHISEE_ZIPCODES WHERE ZIP_CODE ='" + zipcode + "'";
		// System.out.println("query in getFranchiseefromzip "+ query );
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				franchisee = result.getString("FRANCHISEE_ID");
				// System.out.println("franchisee is " + franchisee );
			}
		} catch (Exception e)
		{
			logger.error("Exception in getFranchiseefromzip: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return franchisee;
	}

	// returns the list of all Franchis users for a particular Franchise ID
	public HashMap getFranchiseUsers (String franchisee_no)
	{
		HashMap franchiseUsers = new HashMap();
		int count = 0;
		ResultSet result = null;
		try
		{
			String query = "SELECT USER_NO, CONCAT(First_NAME,' ',LAST_NAME) AS NAME ,U.FRANCHISEE_NO FROM FRANCHISEE_USERS FU, USERS U WHERE FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO  AND U.IS_DELETED='N'  AND FU.FRANCHISEE_NO=" + Integer.parseInt(franchisee_no) + "  ORDER BY NAME ";

			// String firstname = "" ;
			// String lastname = "" ;
			result = QueryUtil.getResult(query,null);
			// System.out.println("franchisee_no is " + franchisee_no + "query
			// is " + query) ;
			while (result.next())
			{
				HashMap temphash = new HashMap();
				temphash.put("1", result.getString("USER_NO"));
				temphash.put("2", result.getString("NAME"));
				franchiseUsers.put(Integer.toString(count), temphash);
				count++;
			}
		} catch (Exception e)
		{
			logger.error("Exception in getFranchiseUsers: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return franchiseUsers;

	}

	// RETURN THE FRANCHISEE USER CORRESPONDING TO THE FRANCHISE ID

	public HashMap getFranchiseeUsers (String franchisee_no)
	{
		HashMap franchiseUsers = new HashMap();
		ResultSet result = null;
		int count = 0;
		try
		{
			String query = "SELECT USER_NO, CONCAT(FIRST_NAME,' ',LAST_NAME) AS NAME ,U.FRANCHISEE_NO FROM FRANCHISEE_USERS FU, USERS U WHERE FU.FRANCHISEE_USER_NO=U.USER_IDENTITY_NO AND U.IS_DELETED='N' AND  FU.FRANCHISEE_NO IN (" + franchisee_no + ")  ORDER BY NAME ";
			logger.info("query>>>>>>>>>" + query);

			// String firstname = "" ;
			// String lastname = "" ;
			result = QueryUtil.getResult(query,null);
			// System.out.println("franchisee_no is " + franchisee_no + "query
			// is " + query) ;
			while (result.next())
			{
				HashMap temphash = new HashMap();
				temphash.put("1", result.getString("USER_NO"));
				temphash.put("2", result.getString("NAME"));
				franchiseUsers.put(Integer.toString(count), temphash);
				count++;
			}
		} catch (Exception e)
		{
			logger.error("Exception in getFranchiseUsers: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return franchiseUsers;

	}

	// returns the franchisee user that owns or watch the passed zipcode
	public String getFranchiseUserfromzip (String zipcode)
	{
		String franchisee = "";
		ResultSet result = null;
		String query = "SELECT FRANCHISE_USERS FROM FRANCHISEE_ZIPCODES WHERE ZIP_CODE ='" + zipcode + "'";
		// System.out.println("query in getFranchiseefromzip "+ query );
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				franchisee = result.getString("FRANCHISE_USERS");
				// System.out.println("franchisee is " + franchisee );
			}
		} catch (Exception e)
		{
			logger.error("Exception in getFranchiseUserfromzip: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return franchisee;
	}

	// for getting the default corporate user

	public String getDefaultOwner ()
	{
		String owner = "";
		ResultSet result = null;
		String query = "SELECT CORP_USER FROM  CM_CORPORATE_ZIPCODE ";
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				owner = result.getString("CORP_USER");
				// System.out.println("franchisee is " + owner );
			}
		} catch (Exception e)
		{
			logger.error("Exception in getDefaultOwner: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);

		}
		return owner;
	}

	// returns Franchisee name corresponding to Franchisee ID

	public String getFranName (String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT FRANCHISEE_NAME FROM FRANCHISEE WHERE IS_BASE_ADMIN = 'N' AND FRANCHISEE_NO = " + FranNo);
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String result = "";

		if (rs.next())
		{

			result = rs.getString("FRANCHISEE_NAME");

		}
		QueryUtil.releaseResultSet(rs);
		return result;

	}

	// returns the user num corresponding to franchisee_no
	public String getUserNum (String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT USER_NO FROM USERS WHERE FRANCHISEE_NO=" + FranNo);
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String result = "";

		if (rs.next())
		{

			result = rs.getString("USER_NO");

		}
		QueryUtil.releaseResultSet(rs);

		return result;

	}

	// Method for sending mail
	public void sendMail (String from, String user_no, String subject, String to, String user_no2, String message) throws ConnectionException
	{
		Connection con = null;

		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO MESSAGE_DETAIL (FROM_USER_NAME,FROM_USER_NO,SUBJECT,TEXT,RCPT_TO,MESSAGE_DATE,RCPT_CC) VALUES(?,?,?,?,?,NOW(),'')");
			pstmt1.setString(1, from);
			pstmt1.setString(2, user_no);
			pstmt1.setString(3, subject);
			pstmt1.setString(4, message);
			pstmt1.setString(5, to);

			pstmt1.executeUpdate();
			// This query has been changed
			// INSERT INTO MESSAGE_RCPT
			// (MESSAGE_NO,TO_USER_NO,FOLDER_NO,TO_CC_BCC) VALUES
			// (LAST_INSERT_ID(),?,-1,?)
			PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO MESSAGE_RCPT (MESSAGE_NO,TO_USER_NO,TO_CC_BCC) VALUES (LAST_INSERT_ID(),?,?)");
			pstmt2.setInt(1, Integer.parseInt(user_no2));
			pstmt2.setString(2, "T");
			pstmt2.executeUpdate();

		} 
		catch (SQLException e)
		{
			Debug.print(e);
		}

		finally
		{
			DBConnectionManager.getInstance().freeConnection(con);
			Debug.print(" Trouble ticket Updated ");
		}
	}

	// returns the email id of Franchise
	public String getEmailofFran (String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT EMAIL_ID FROM FRANCHISEE_USERS WHERE FRANCHISEE_USER_NO IN (SELECT USER_IDENTITY_NO FROM USERS WHERE USER_NO=" + FranNo + ")");
		// System.out.println("query getEmailofFran " + query.toString());
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String result = "";

		if (rs.next())
		{

			result = rs.getString("EMAIL_ID");

		}
		QueryUtil.releaseResultSet(rs);
		return result;

	}

	public String getEmailofCorp (String FranNo)
	{
		StringBuffer query = new StringBuffer("SELECT EMAIL_ID FROM FRANCHISEE WHERE FRANCHISEE_NO IN (SELECT FRANCHISEE_NO FROM USERS WHERE USER_NO = " + FranNo + ")");
		// System.out.println("query getEmailofCorp " + query.toString());
		ResultSet rs = QueryUtil.getResult(query.toString(), null);
		String result = "";

		if (rs.next())
		{

			result = rs.getString("EMAIL_ID");

		}
		QueryUtil.releaseResultSet(rs);
		return result;

	}
        
        //Added by ankush tanwar for service314 changes starts
        

public String getStateAbbrName(String stateName)
{
	String state = null ;
        ResultSet result =null;
	try{
		String query = "SELECT DISTINCT REGION_ABBREV FROM REGIONS WHERE REGION_NAME='"+stateName+"'" ;

		//Integer.toString(int);
		 result =QueryUtil.getResult(query,null);
		while(result.next())
		{
			state = result.getString("REGION_ABBREV");
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



	public String[] getLattiTudeNLongitudeFromZipCode(String zipCode){
		StringBuffer query = new StringBuffer("");
		if(zipCode==null || "".equals(zipCode) || "null".equals(zipCode))
			zipCode = "121212";
		
		while(zipCode!=null && zipCode.startsWith("0")){
			
			zipCode = zipCode.replaceFirst("0", "");
		}
		query.append(" SELECT LATITUDE, LONGITUDE FROM ZIP_CODES WHERE ZIP = '"+zipCode+"'");
		ResultSet resultSet	= QueryUtil.getResult(query.toString(), null);
		String lattitudeNLongitude[] = {"0.00","0.00"}; 
		if(resultSet.next()){
			lattitudeNLongitude[0] = resultSet.getString("LATITUDE");
			lattitudeNLongitude[1] = resultSet.getString("LONGITUDE");
		}
		return lattitudeNLongitude;
	}
	
	public boolean isValidZipCode(String zipCode){
		boolean isValidZipCode = false;
		StringBuffer query = new StringBuffer("");
	
		while(zipCode!=null && zipCode.startsWith("0")){
			
			zipCode = zipCode.replaceFirst("0", "");
		}
		
		query.append(" SELECT ZIP FROM ZIP_CODES WHERE ZIP = '"+zipCode+"'");
		ResultSet resultSet	= QueryUtil.getResult(query.toString(), null);
	
		if(resultSet.next()){
			isValidZipCode = true;
		}
		
		return isValidZipCode;
	}



        
        //Added by ankush tanwar for service314 changes ends

	//	to get the List of all zip codes owned by franchisee user

	public ArrayList getAreaOwnedZipCodeList (String areaID)
	{

		String query = "SELECT DISTINCT ZIPCODE FROM ZIPCODE_AREAS WHERE AREA_ID= '" + areaID + "' ORDER BY ZIPCODE";
		ResultSet result = null;
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIPCODE"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}
		// System.out.println("owned zip codes in DAO " + zipcodes);
		return zipcodes;
	}
	//P_B_FIM_63341 added by neeti starts
	/*
	 * for getting zip codes from AREA_TERRITORY table on the basis of areaID
	 * author: Neeti Solanki
	 */
	public ArrayList getRegionOwnedZipCodeList(String areaID)
	{
		
		String query = "SELECT DISTINCT FIM_TT_ZIP FROM AREA_TERRITORY WHERE AREA_ID= '" + areaID + "' ORDER BY FIM_TT_ZIP";
		ResultSet result = null;
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			if(result.next())
			{
				zipcodes.add(result.getString("FIM_TT_ZIP"));
			}
			else
			{
				zipcodes=getAreaOwnedZipCodeList(areaID);
			}
			
			
			
			
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return zipcodes;
	}
	//P_B_FIM_63341 added by neeti ends

//	to get the List of all zip codes owned by Multi-Unit Owner Franchisee
//P_B_FIM_63378 added by neeti starts
	public ArrayList getMuOwnedZipCodeList1 (String franNo)
	{

		String query = "SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE ASSOCIATION_TYPE = 'O' AND FRANCHISEE_ID in ("+franNo+") ORDER BY ZIP_CODE";
		ResultSet result = null;
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIP_CODE"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}

		// System.out.println("owned zip codes in DAO " + zipcodes);
		return zipcodes;
	}
//P_B_FIM_63378 added by neeti ends
	public ArrayList getMuOwnedZipCodeList (String muID)
	{

		String query = "SELECT DISTINCT ZIP_CODE FROM FRANCHISEE_ZIPCODES WHERE FRANCHISEE_ID= '" + muID + "' ORDER BY ZIP_CODE";
		ResultSet result = null;
		ArrayList zipcodes = new ArrayList();
		try
		{
			result = QueryUtil.getResult(query,null);
			while (result.next())
			{
				zipcodes.add(result.getString("ZIP_CODE"));
			}
		} catch (Exception e)
		{
			logger.error("Exception in getOwnedZipCodeList: " + e);
		}finally
		{
			QueryUtil.releaseResultSet(result);
		}

		// System.out.println("owned zip codes in DAO " + zipcodes);
		return zipcodes;
	}
    /*-----------------------------------Moved From BaseNewPortalUtils--------------------------------------------*/

	 public String getAreaIdForIntContry(String country, String state) {
	        return getAreaIdForIntContry(country, state, null);
	    }

	    public String getAreaIdForIntContry(String country, String state,
	            String zip) {
	    	return getAreaIdForIntContry(country, state, zip, null);
	    }
	    public String getAreaIdForIntContry(String country, String state,String zip, String countyID) 
	    {
	        String baseAreaId = "0";
	        ResultSet result = null;
	        String query = null;
	        try {
	            if (country != null && !country.trim().equals("") && zip != null
	                    && !zip.trim().equals("")) {
	                query = "SELECT DISTINCT(ZA.AREA_ID)  FROM ZIPCODE_AREAS ZA,ZIPCODE_AREA_COUNTRY ZAC WHERE ZAC.AREA_ID=ZA.AREA_ID AND ZAC.COUNTRY_ID='"//P_FS_B_6234
	                        + country + "' AND  ZA.ZIPCODE= '" + zip + "'";
	                result = QueryUtil.getResult(query,null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }

	            if ("0".equals(baseAreaId) && zip != null && !zip.trim().equals("")) 
	            {
	                query = "SELECT DISTINCT(ZA.AREA_ID)  FROM ZIPCODE_AREAS ZA,ZIPCODE_AREA_COUNTRY ZAC WHERE ZAC.AREA_ID=ZA.AREA_ID AND  ZA.ZIPCODE= '" + zip + "'";
	                result = QueryUtil.getResult(query,null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }
	            
	            if ("0".equals(baseAreaId) && country != null  && !country.trim().equals("") && countyID != null  && !countyID.trim().equals("")) {
	            	query = "SELECT DISTINCT(CA.AREA_ID) FROM COUNTY_AREAS CA LEFT JOIN AREAS A ON A.AREA_ID=CA.AREA_ID WHERE COUNTY_ID = ?";
	                result = QueryUtil.getResult(query,new String[]{countyID});
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }
	            
	            if (baseAreaId.equals("0") && country != null  && !country.trim().equals("") && state != null && !state.trim().equals("")) {
	                query = "SELECT DISTINCT(ZA.AREA_ID)  FROM  STATE_AREAS ZA WHERE ZA.COUNTRY_ID="+ country.trim() + " AND ZA.STATE_ID=" + state.trim();
	                result = QueryUtil.getResult(query,null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
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
	    public String getAreaIdForZipState(String zip, String regionName) {
	        String baseAreaId = "0";
	        ResultSet result = null;
	        String query = null;
	        try {
	            // if(zip!= null && zip.trim().length()>4){
	            // zip = zip.trim().substring(0,5);
	            // }
	            if (zip != null && zip.trim().length() > 4) {
	            	query = "SELECT ZA.AREA_ID  FROM ZIPCODE_AREAS ZA LEFT JOIN AREAS A ON ZA.AREA_ID = A.AREA_ID  WHERE A.IS_DELETED='N' AND ZA.ZIPCODE="
	                        + zip;
	            } else {
	            	query = "SELECT SA.AREA_ID  FROM STATE_AREAS SA LEFT JOIN AREAS A ON SA.AREA_ID = A.AREA_ID  WHERE A.IS_DELETED='N' AND SA.STATE_ID="
	                        + regionName;
	            }
	            result = QueryUtil.getResult(query,null);
	            if (result != null && result.next()) {
	                baseAreaId = result.getString(1);
	            } else {
	                baseAreaId = "0";
	            }
	            // ganesh,this case will executed ,if the zipcode is available,but
	            // the particular zipcode not specified in any region
	            // In this case:it take state and the same process will get executed
	            if (zip != null && zip.trim().length() > 4 && baseAreaId != null
	                    && baseAreaId.length() > 0 && baseAreaId.equals("0")) {
	            	query = "SELECT SA.AREA_ID  FROM STATE_AREAS SA LEFT JOIN AREAS A ON SA.AREA_ID = A.AREA_ID  WHERE A.IS_DELETED='N' AND SA.STATE_ID="
	                        + regionName;
	                result = QueryUtil.getResult(query,null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }

	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (result != null) {
	                	result.release();
	                    result = null;
	                }
	            } catch (Exception e) {
	                logger.error(e);
	            }
	        }

	        return baseAreaId;
	    }
	    public SequenceMap getFranchiseeByZipCode(double MaxLatitude,
	            double MinLatitude, double MaxLongitude, double MinLongitude,
	            String sortKey, String sortOrder, String pageId, int numberOfRecords) {
	        return getFranchiseeByZipCode(MaxLatitude, MinLatitude, MaxLongitude,
	                MinLongitude, sortKey, sortOrder, pageId, numberOfRecords, null);
	    }

	    public SequenceMap getFranchiseeByZipCode(double MaxLatitude,
	            double MinLatitude, double MaxLongitude, double MinLongitude,
	            String sortKey, String sortOrder, String pageId,
	            int numberOfRecords, String zipCode) {
	        StringBuffer sbQuery = new StringBuffer();
	        StringBuffer sbQuery1 = new StringBuffer();
	        SequenceMap smap = new SequenceMap();
	        ResultSet result = null;
	        try {
	            if (zipCode != null && !"".equals(zipCode)) {
	                sbQuery.append(
	                        "SELECT DISTINCT A.ZIP_CODE, A.FRANCHISEE_ID, A.ASSOCIATION_TYPE, A.STATE, A.FRANCHISE_USERS,B.FRANCHISEE_NAME FROM FRANCHISEE_ZIPCODES A, FRANCHISEE B WHERE B.IS_FRANCHISEE = 'Y' AND A.FRANCHISEE_ID=B.FRANCHISEE_NO AND (A.ZIP_CODE='").append(zipCode).append("' OR A.ZIP_CODE IN  (SELECT ZIP FROM ZIP_CODES ZC WHERE LATITUDE <").append(MaxLatitude).append("  AND LATITUDE>").append(MinLatitude).append("  AND LONGITUDE<").append(MaxLongitude).append("  AND LONGITUDE>").append(MinLongitude).append(")) ");
	                sbQuery1.append(
	                        "SELECT COUNT(DISTINCT A.ZIP_CODE,A.STATE) AS COUNT FROM FRANCHISEE_ZIPCODES A, FRANCHISEE B WHERE B.IS_FRANCHISEE = 'Y' AND A.FRANCHISEE_ID=B.FRANCHISEE_NO AND (A.ZIP_CODE='").append(zipCode).append("' OR A.ZIP_CODE IN  (SELECT ZIP FROM ZIP_CODES ZC WHERE LATITUDE <").append(MaxLatitude).append("  AND LATITUDE>").append(MinLatitude).append("  AND LONGITUDE<").append(MaxLongitude).append("  AND LONGITUDE>").append(MinLongitude).append("))");
	            } else {
	                sbQuery.append(
	                        "SELECT DISTINCT A.ZIP_CODE, A.FRANCHISEE_ID, A.ASSOCIATION_TYPE, A.STATE, A.FRANCHISE_USERS,B.FRANCHISEE_NAME FROM FRANCHISEE_ZIPCODES A, FRANCHISEE B WHERE B.IS_FRANCHISEE = 'Y' AND A.FRANCHISEE_ID=B.FRANCHISEE_NO AND A.ZIP_CODE IN  (SELECT ZIP FROM ZIP_CODES ZC WHERE LATITUDE <").append(MaxLatitude).append("  AND LATITUDE>").append(MinLatitude).append("  AND LONGITUDE<").append(MaxLongitude).append("  AND LONGITUDE>").append(MinLongitude).append(")");
	                sbQuery1.append(
	                        "SELECT COUNT(DISTINCT A.ZIP_CODE,A.STATE) AS COUNT FROM FRANCHISEE_ZIPCODES A, FRANCHISEE B WHERE B.IS_FRANCHISEE = 'Y' AND A.FRANCHISEE_ID=B.FRANCHISEE_NO AND A.ZIP_CODE IN  (SELECT ZIP FROM ZIP_CODES ZC WHERE LATITUDE <").append(MaxLatitude).append("  AND LATITUDE>").append(MinLatitude).append("  AND LONGITUDE<").append(MaxLongitude).append("  AND LONGITUDE>").append(MinLongitude).append(")");
	            }
	            String orderBy = "";
	            if ("ZC".equals(sortKey)) {
	                orderBy = " ORDER BY A.ZIP_CODE ";
	            } else if ("AT".equals(sortKey)) {
	                orderBy = " ORDER BY A.ASSOCIATION_TYPE ";
	            } else if ("FN".equals(sortKey)) {
	                orderBy = " ORDER BY B.FRANCHISEE_NAME ";
	            } else if ("S".equals(sortKey)) {
	                orderBy = " ORDER BY A.STATE ";
	            } else {
	                orderBy = " ORDER BY A.ZIP_CODE ";
	            }

	            String sort = " ASC";
	            if (StringUtil.isRequestParamValid(sortOrder)) {
	                sort = sortOrder;
	            }

	            sbQuery.append(orderBy).append(" ").append(sort);

	            int limitPerPage = Constants.RECORDS_PER_PAGE;
	            if (!"noPagging".equals(pageId)) {
	                if (pageId != null && !pageId.equals("")
	                        && !pageId.equals("null") && !pageId.equals("0")) {
	                    int page = Integer.parseInt(pageId);
	                    int offset = (page - 1) * limitPerPage;
	                    sbQuery.append(" LIMIT " + offset + " , " + limitPerPage);

	                } else {
	                    sbQuery.append(" LIMIT 0 , " + limitPerPage);
	                }
	            } else if (numberOfRecords > 0) {
//					System.out.println("numberOfRecords---?" + numberOfRecords);
	                sbQuery.append(" LIMIT 0 , " + numberOfRecords);
	            }
	            result = QueryUtil.getResult(sbQuery.toString(), null);
	            String FranchiseeName = "";
	            int i = 0;
	            while (result.next()) {
	                Info info = new Info();
	                info.set(FieldNames.ZIP_CODE, result.getString("ZIP_CODE"));
	                info.set(FieldNames.FRANCHISEE_ID,
	                        result.getString("FRANCHISEE_ID"));
	                info.set(FieldNames.FRANCHISEE_NAME,
	                        result.getString("FRANCHISEE_NAME"));
	                info.set(FieldNames.ASSOCIATION_TYPE,
	                        result.getString("ASSOCIATION_TYPE"));
	                if ("O".equals(result.getString("ASSOCIATION_TYPE"))) {
	                    info.set("ASSOCIATION_TYPE", "Owned");
	                } else if ("W".equals(result.getString("ASSOCIATION_TYPE"))) {
	                    info.set("ASSOCIATION_TYPE", "Watched");
	                }
	                info.set(FieldNames.STATE, result.getString("STATE"));
	                info.set(FieldNames.FRANCHISE_USERS,
	                        result.getString("FRANCHISE_USERS"));
	                smap.put(i, info);
	                i++;
	            }
	            result = QueryUtil.getResult(sbQuery1.toString(), new Object[]{});
	            if (result.next()) {
	                smap.put("totalRecords", result.getString("COUNT"));
	            }
	        } catch (Exception e) {
	            logger.error("ERROR: exception in getFranchiseeByZipCode ::" + e);
	        } finally {
	            if (result != null) {
	                result = null;
	            }
	        }
	        return smap;
	    }

	    public String getFranchiseeIDNEW(String Zipcode) {
	    	String query = "SELECT FRANCHISEE_ID FROM FRANCHISEE_ZIPCODES WHERE ZIP_CODE = '"+ Zipcode + "'";
	    	return SQLUtil.getQueryResult(query, "FRANCHISEE_ID");
	    }

	    public Info getlatitude(String foreignID) {
	        StringBuffer sbQuery = null;
	        Info info = null;
	        ResultSet result=null;
	        try {
	            info = new Info();

	            sbQuery = new StringBuffer(
	                    "SELECT ZC.LATITUDE,ZC.LONGITUDE FROM ZIP_CODES ZC  WHERE ZC.ZIP IN ('"
	                    + foreignID + "')");
	             result = QueryUtil.getResult(sbQuery.toString(),
	                    new Object[]{});
	            // System.out.println("result>>>>sbQuery.toString()>>>"+sbQuery.toString());
	            if (result.size() == 0) {
	                info.set(FieldNames.LATITUDE, null);
	                info.set(FieldNames.LONGITUDE, null);

	            } else {

	                while (result.next()) {
	                    // System.out.println("result.getString>>>>>>>");
	                    info.set(FieldNames.LATITUDE, result.getString("LATITUDE"));
	                    info.set(FieldNames.LONGITUDE,
	                            result.getString("LONGITUDE"));
	                    // System.out.println("result.getString>>>>>>>"+info);
	                }// end while
	            }
	        } catch (Exception e) {
	            logger.error("ERROR: exception in getlatitude ::" + e);
	        }
	        finally
			{
				QueryUtil.releaseResultSet(result);
			}

	        return info;
	    }

	    
	    public static String getSalesTerIdForZipStateCounty(String zip, String state, String country, boolean uszipcheck, String countyID){
			String baseAreaId = "0";
			String query = "";
	        ResultSet result = null;
	        try {
	        	/*if(uszipcheck && zip!= null && zip.trim().length()>4){
	    			zip = zip.trim().substring(0,5);
	    		}*/
	            if (country != null && !country.trim().equals("") && zip != null
	                    && !zip.trim().equals("")) {
	            	
	                StringBuffer sbQuery = new StringBuffer(" SELECT FA.AREA_ID FROM FS_ZIPCODE_AREAS ZA  ,FS_AREAS FA , FS_ZIPCODE_AREA_COUNTRY ZAC  WHERE ZIPCODE = '");
		    		sbQuery.append(zip).append("'  AND FA.AREA_ID=ZA.AREA_ID ");
		    		sbQuery.append("AND ZA.AREA_ID=ZAC.AREA_ID AND ZAC.COUNTRY_ID= ").append(country);
	                result = QueryUtil.getResult(sbQuery.toString(),null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }

	            if ("0".equals(baseAreaId) && zip != null && !zip.trim().equals("")) 
	            {
	            	StringBuffer sbQuery = new StringBuffer(" SELECT FA.AREA_ID FROM FS_ZIPCODE_AREAS ZA  ,FS_AREAS FA , FS_ZIPCODE_AREA_COUNTRY ZAC  WHERE ZIPCODE = '");
		    		sbQuery.append(zip).append("'  AND FA.AREA_ID=ZA.AREA_ID ");
		    		sbQuery.append("AND ZA.AREA_ID=ZAC.AREA_ID ");
	                result = QueryUtil.getResult(sbQuery.toString(),null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }
	            
	            if ("0".equals(baseAreaId) && country != null  && !country.trim().equals("") && countyID != null  && !countyID.trim().equals("")) {
	            	query = "SELECT CA.AREA_ID FROM FS_COUNTY_AREAS CA LEFT JOIN FS_AREAS FA ON FA.AREA_ID=CA.AREA_ID WHERE COUNTY_ID = ?";
	                result = QueryUtil.getResult(query,new String[]{countyID});
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
	            }
	            
	            if (baseAreaId.equals("0") && country != null  && !country.trim().equals("") && state != null && !state.trim().equals("")) {
	                query = "SELECT FA.AREA_ID FROM FS_STATE_AREAS SA ,FS_AREAS FA WHERE STATE_ID = '"+state+"' AND FA.AREA_ID=SA.AREA_ID";
	                result = QueryUtil.getResult(query,null);
	                if (result != null && result.next()) {
	                    baseAreaId = result.getString(1);
	                } else {
	                    baseAreaId = "0";
	                }
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
	    
	    public String getRegionId(String zip, String regionName, String countryId,boolean uszipcheck){
	    	return getRegionId(zip, regionName, countryId, uszipcheck, "");
	    }
	    public String getRegionId(String zip, String regionName, String countryId,boolean uszipcheck, String county){

	    	String baseAdminId = null;
	    	ResultSet result = null;

	    	try {

	    		//baseAdminId = "0";

	    		//baseAdminId = "0";
	    		//Added by Rajeev 2/21/2006 - to pick only first five digts of Zip Code for comparision
	    		//zip = zip.substring(0,5);

	    		// Demo_FDD_B_50365 chnage starts
	    		//if(zip!= null && zip.trim().length()>4){
	    		if(uszipcheck && zip!= null && zip.trim().length()>4){
	    			zip = zip.trim().substring(0,5);

	    		}

	    		/* Rule 1, find the ared id for this zipcode */

	    		//	String query					= "SELECT FA.AREA_ID FROM FS_ZIPCODE_AREAS ZA  ,FS_AREAS FA  WHERE ZIPCODE = '"+zip+"'  AND FA.AREA_ID=ZA.AREA_ID  ";
	    		String query=null;   
	    		StringBuffer sbQuery = new StringBuffer(" SELECT FA.AREA_ID FROM FS_ZIPCODE_AREAS ZA  ,FS_AREAS FA , FS_ZIPCODE_AREA_COUNTRY ZAC  WHERE ZIPCODE = '");
	    		sbQuery.append(zip).append("'  AND FA.AREA_ID=ZA.AREA_ID ");
	    		sbQuery.append("AND ZA.AREA_ID=ZAC.AREA_ID AND ZAC.COUNTRY_ID= ").append(countryId);
	    		//		result	= QueryUtil.getResult(query,null);
	    		result	= QueryUtil.getResult(sbQuery.toString(), null);
	    		if (result!=null && result.next()) {	// Mapping exists for this area
	    			return "" + result.getInteger(1);
	    		}else if(StringUtil.isValidNew(county)){
	    			query					= "SELECT CA.AREA_ID FROM FS_COUNTY_AREAS CA LEFT JOIN FS_AREAS FA ON FA.AREA_ID=CA.AREA_ID WHERE COUNTY_ID = ?";

	    			result			= QueryUtil.getResult(query,new String[]{county});
	    			if (result!=null && result.next()) {	
	    				return  "" + result.getInteger(1);
	    			}
	    		}else{
	    			/* Rule 2, find the area id for the state */
	    			query					= "SELECT FA.AREA_ID FROM FS_STATE_AREAS SA ,FS_AREAS FA WHERE STATE_ID = '"+regionName+"' AND FA.AREA_ID=SA.AREA_ID ";

	    			result			= QueryUtil.getResult(query,null);
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
	    				result = null;
	    			}
	    		}catch(Exception e){
	    			logger.error(e);
	    		}
	    	}
	    	return "-1";
	    }
	    
	    /**
	     *
	     * @param areaId
	     * @return This method returns all zip codes as a single string which
	     *         matches the given areaId
	     */
	    public String getZipCodeByAreaID(String areaId) {
	        StringBuffer zipCodeList = new StringBuffer();
	        zipCodeList.append(" ");
	        String query = "SELECT AREA_ID,ZIPCODE FROM ZIPCODE_AREAS WHERE AREA_ID='"
	                + areaId + "'";
	        ResultSet rs=null;
	        try {
	             rs = QueryUtil.getResult(query,null);
	            while (rs.next()) {
	                // zipCodeList.append("'");
	                zipCodeList.append(rs.getString("ZIPCODE"));
	                // zipCodeList.append("'");
	                zipCodeList.append(",");
	            }
	        } catch (RuntimeException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finally
			{
				QueryUtil.releaseResultSet(rs);
			}

	        return zipCodeList.toString().substring(0,
	                zipCodeList.toString().length() - 1);
	    }
	    public Info getZipcodeStatus(String Zipcode) {
	        Info info = null;
	        ResultSet result = null;
	        try {
	            info = new Info();
	            String FranchiseeName = null;
	            StringBuffer sbQuery = new StringBuffer();
	            sbQuery.append("SELECT DISTINCT A.ZIP_CODE, A.FRANCHISEE_ID, A.ASSOCIATION_TYPE, A.STATE, A.FRANCHISE_USERS FROM FRANCHISEE_ZIPCODES A, FRANCHISEE B WHERE B.IS_FRANCHISEE = 'Y' AND A.ZIP_CODE = '"
	                    + Zipcode + "'");
	            result = QueryUtil.getResult(sbQuery.toString(), null);
	            if (result.next()) {
	                info.set(FieldNames.ZIP_CODE, result.getString("ZIP_CODE"));
	                info.set(FieldNames.FRANCHISEE_ID,
	                        result.getString("FRANCHISEE_ID"));
	                //FranchiseeName = AdminMgr.newInstance().getStoreDAO().getFranchiseeName(result.getString("FRANCHISEE_ID"));
	                if (FranchiseeName == null) {
	                    FranchiseeName = "";
	                }
	                info.set(FieldNames.FRANCHISEE_NAME, FranchiseeName);
	                info.set(FieldNames.ASSOCIATION_TYPE,
	                        result.getString("ASSOCIATION_TYPE"));
	                if ("O".equals(result.getString("ASSOCIATION_TYPE"))) {
	                    info.set("ASSOCIATION_TYPE", "Owned");
	                } else if ("W".equals(result.getString("ASSOCIATION_TYPE"))) {
	                    info.set("ASSOCIATION_TYPE", "Watched");
	                }
	                info.set(FieldNames.STATE, result.getString("STATE"));
	                info.set(FieldNames.FRANCHISE_USERS,
	                        result.getString("FRANCHISE_USERS"));
	            }// end if
	            sbQuery = null;
	        } catch (Exception e) {
	            logger.info("ERROR: exception in getZipcodeStatus ::" + e);
	        } finally {
	            if (result != null) {
	                result = null;
	            }
	        }
	        return info;
	    }
	    
	    public int isDefaultContactOwner(String userNo) {
	        int count = 0;
	        StringBuffer leadOwnerQuery = new StringBuffer(
	                "SELECT COUNT(1) FROM CM_CORPORATE_ZIPCODE WHERE CORP_USER=");
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

}
