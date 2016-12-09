/*
---------------------------------------------------------------------------------------------------------------------------------
Version No.				Date		By			Against					Function Changed          	Comments
---------------------------------------------------------------------------------------------------------------------------------
BB_ENH_11Nov08			13Nov2008	YogeshT		--						--							-added method getNextSequentialIntKey() to get unique sequential key for given long/Integer type column.
---------------------------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import com.home.builderforms.sqlqueries.SQLUtil;

import com.home.builderforms.RandomJava;
import com.home.builderforms.sqlqueries.ResultSet;

/**

 *   Class for generating new IDs

 *

 *@author     misam

 *@created    November 9, 2001

 */

    public class IDGenerator {
	private static RandomJava random;



	static{

		random = new RandomJava();

	}
	//ClockWork-20141201-680 starts
	public static String getNextKey(String tableName, String primaryKeyColumnName) {
		return getNextKey(tableName, primaryKeyColumnName,50);
	}
	public static String getNextKey(String tableName, String primaryKeyColumnName,int tryCount) {
		String primaryKey = null;
		boolean isPrimaryKeyExist = true;
		if("FS_LEAD_DETAILS".equals(tableName)){
			tableName="FS_LEAD";
		}
		int i = 0; 
		try{
		while(isPrimaryKeyExist && i++<tryCount){
			logger.info("Attempt No. " + i + " To Obtain A Primary Key For Table = " + tableName);
			primaryKey = new Integer(Math.abs(random.nextInt())).toString();
			//isPrimaryKeyExist = PortalUtils.isPrimaryKeyExist(tableName, primaryKeyColumnName, primaryKey);	
			isPrimaryKeyExist = SQLUtil.isPrimaryKeyExist(tableName, primaryKeyColumnName, primaryKey);    //For Product_Seperation_BL By Amar Singh.
			if(isPrimaryKeyExist){
				logger.info("primary key(" + primaryKey + ") already exist in " + tableName);
			}else{
				if("FS_LEAD".equals(tableName)){
					QueryUtil.executeInsert("INSERT INTO FS_LEAD VALUES("+primaryKey+")");
				}
				logger.info("primary key(" + primaryKey + ") Generation Successfull ");
				break;
			}
		}
		}catch(Exception e){
			logger.info("Exception inside getNextKey()",e);
		}
		return primaryKey;
	}//ClockWork-20141201-680 ends
/**
 * Function used for generating a random number primarily used 
 * for insert the value of primary key in database.
 * @return string format of the number
 */
	public static String getNextKey() {

	   return new Integer(Math.abs(random.nextInt())).toString();

	}

/**
 * Function used for generating a random number primarily used 
 * for insert the value of primary key in database.
 * @return string format of the number
 */

	public String getNextID(){

		 return new Integer(Math.abs(random.nextInt())).toString();

	}
	
	/**
	 * This method is used to get unique sequential key for given long/Integer type column.
	 * @author YogeshT
	 * @param tableName
	 * @param keyColumnName
	 * @return long
	 * @throws Exception
	 * @date 11Nov2008
	 */
	public static long getNextSequentialIntKey(String tableName, String keyColumnName)throws Exception {
		//BB_ENH_11Nov08
		long newMaxKey = 1;
		
		if(tableName!=null && !"".equals(tableName.trim()) && !"-1".equals(tableName.trim()) &&
				keyColumnName!=null && !"".equals(keyColumnName.trim()) && !"-1".equals(keyColumnName.trim())){
			
			long existingMaxKey = 0;
			boolean generatedKeyExist = true;
			int attemptNo = 0;
			try{
				while(generatedKeyExist && attemptNo++ < 50) {
					logger.info("Attempt No. " + attemptNo + " To Obtain A Primary Key For Table = " + tableName);
					
					//existingMaxKey = PortalUtils.getMaxIntKey(tableName, keyColumnName);
					existingMaxKey = BaseUtils.getMaxIntKey(tableName, keyColumnName);  //For Product_Seperation_BL By Amar Singh.
					newMaxKey = Math.abs(existingMaxKey)+1;
					//generatedKeyExist = PortalUtils.isPrimaryKeyExist(tableName, keyColumnName, String.valueOf(newMaxKey));
					generatedKeyExist = SQLUtil.isPrimaryKeyExist(tableName, keyColumnName, String.valueOf(newMaxKey)); //For Product_Seperation_BL By Amar Singh.
					
					if(generatedKeyExist){
						logger.info("unique sequential key(" + newMaxKey + ") already exist in " + tableName);
					}else{
						logger.info("unique sequential key(" + newMaxKey + ") Generation Successfull ");
					}
				}
			}catch(Exception e){
				logger.info("Exception Occured while generating key...");
				throw new Exception("Exception Occured while generating key...");
			}
		}else{
			throw new Exception("Exception Occured::::::::Invalid Table or Column name...");
		}
		return newMaxKey;
	}
	
	public static synchronized String getNextPurchaseOrderNo(){
		
		String query = "SELECT IFNULL(MAX(PURCHASEORDER_NO),0)+1 AS NUMB FROM SUPP_PURCHASEORDER_NO";
		String poNo=null;
        try{
        	ResultSet result = QueryUtil.getResult(query,null);
   
        	if(result.next())
        	{
        		poNo=result.getString("NUMB");
        		System.out.println("NUMB"+poNo);    
        	}
        	if(poNo==null)
        	{
        		poNo="1";
        	}
        	QueryUtil.executeInsert("INSERT INTO SUPP_PURCHASEORDER_NO (PURCHASEORDER_NO) VALUES("+poNo+")");
        	
        }catch(Exception e){
        	logger.info("Exception in getPurchaseOrderNo function "+e);
        }
        return poNo;
	}
	

    }



