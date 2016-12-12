
/** ------------------------------------------------------------------------------------------------------------------------------- Version No.		Date		By	         Against	Function Changed									Comments -------------------------------------------------------------------------------------------------------------------------------- P_FIM_B_26648  8/17/2007   Niraj Sachan  bug		added new methods
													(blank trancform helper methods defined in xml's)
------------------------------------------------------------------------------------------------------------
Version No.			Date		By				Against						Function Changed    Comments
------------------------------------------------------------------------------------------------------------
P_E_FIM_58658     10/07/2009  Nikhil Verma   	Task Trigger FIM enhancement.  setResultInfo()
P_CM_Enh_BuilderForm    11Feb2014       Dheeraj Madaan                  Form Builder in Contact Manager Module 
--------------------------------------------------------------------------------------------------------------------------------
*/



package com.home.builderforms.sqlqueries;

import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


//import com.appnetix.app.portal.export.ExportDataManipulator;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.Debug;
import com.home.builderforms.IntConstants;
import com.home.builderforms.NumberFormatUtils;
//import com.home.builderforms.PortalUtils;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Field;
import com.home.builderforms.Info;

public class SQLUtilHelper{
	private static HashMap checkBoxMap;

	static {
		checkBoxMap							= new HashMap();
		checkBoxMap.put("", "");
		checkBoxMap.put("0", "No");
		checkBoxMap.put("1", "Yes");
		checkBoxMap.put("2", "NA");
		checkBoxMap.put("3", "No");
	}
private static HashMap liquorMap;

	static {
		liquorMap							= new HashMap();
		liquorMap.put("", "");
		liquorMap.put("0", "None");
		liquorMap.put("1", "Liquor");
		liquorMap.put("3", "Beer & Wine");
		liquorMap.put("2", "Beer & Wine");
	}
	private static final List<String> methodList = Arrays.asList("transformDouble","transformCheckBox","transformLiquorType","transformCountry","transformEntityType","transformStoreStatus","transformTitle","transformState","transformTrans","transformDefaultIntegerValToBlank"); 
		
		
	
	// get the result and set it in the info object
	public static void setResultInfo(
									Info info,
									ResultSet result,
									Field[] fields) {

		SQLUtilHelper helper	= new SQLUtilHelper();
		for (int i=0;i<fields.length;i++){
			/**
			 * Allow only those DB Fields which are not used additionally for records manipulation as done in export.
			 * @author abhishek gupta
			 * @date 1 Jun, 2012
			 */
			if(!fields[i].fieldAllowedInMain()) {
				continue;
			}
			String dataType		= fields[i].getDataType();

			String fieldName	= fields[i].getFieldName();
			Object obj = null;
			if (Field.DataType.CLOB.equals(dataType))
			{
				Clob clob = (Clob)result.getObject(i+1);
				obj = getStringFromClob(clob);
			}
			else
			{
				obj = result.getObject(i+1);
			}
			
			if(dataType.equals(Field.DataType.DATE) && obj != null && !"0000-00-00".equals(obj.toString())) 
			{
				//Edit  by sanjeev k for congig date format
				//obj = PortalUtils.fourDigitYearFormat(obj.toString());
				//P_E_FIM_58658 Added By Nikhil Verma
				if(fields[i]!=null && ("transformDateTimeForTimeLessTask".equals(fields[i].getTransformMethod())) || "transformDateSubmission".equals(fields[i].getTransformMethod()))  //P_ENH_DATE_SUBMISSION
					obj = com.home.builderforms.DateUtil.formatDate(result.getTimestamp(i+1),com.home.builderforms.DateUtil.DB_FORMAT_HMS);
				else
					obj = com.home.builderforms.DateUtil.getDisplayDate(obj.toString());
			}

			try
			{
				obj		= transformHelper(helper, fields[i], obj);
			}
			catch (Exception e)
			{
			}
			info.set(fieldName,obj);
		}

	}

	// get the result and set it in the info object,
	// get the fieldNames from columnArray
	public static void setResultInfo(
									Info info,
									ResultSet result,
									Field[] fields,
									String[] columnArray) {

		for (int i=0;i<fields.length;i++){
			String fieldName	= columnArray[i];
			String dataType		= fields[i].getDataType();
			info.set(fieldName,result.getObject(i+1));;
		}
	}

	//get the Info object with the idField
	public static Info getInfo(
								ResultSet result,
								Field[] fields,
								Field[] idField){

		String[] idFieldNames = new String[idField.length];
		for (int i=0; i<idField.length; i++){
			idFieldNames[i] = idField[i].getFieldName();
		}
		Info info				= new Info(idFieldNames);
		setResultInfo(info,result,fields);
		return info;
	}
	//get the Info object sans idField
	public static Info getInfo(ResultSet result, Field[] fields) {
		Info info				= new Info();
		setResultInfo(info, result, fields);
		return info;
	}

	// Get the resultSet from queryString and SequenceMap of (of Fields as keys)
	public static ResultSet getResultSet(
										String queryString,
										SequenceMap params,
										String connectionName) throws SQLException{

		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		PreparedStatement stmt 					= null;
		ResultSet result 						= null;
		try{
			stmt				= connection.prepareStatement(queryString);
			if (params != null)	{
				SQLQueryGenerator.setParams(connection,stmt,params, IntConstants.DEFAULT_INT);
			}
			
			result	=new ResultSet(stmt.executeQuery());
		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	

			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}

	// Get the resultSet from queryString and String[] of params
	public static ResultSet getResultSet(
										String queryString,
										Object[] params,
										String connectionName) throws SQLException{

		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		ResultSet result						= null;
		PreparedStatement stmt 					= null;
		try{
			stmt							= connection.prepareStatement(queryString);
			if (params!=null && params.length > 0)
			{
				for(int i=0; i<params.length; i++)
				{
					if (params[i] instanceof Timestamp)
					{
						stmt.setTimestamp(i+1,(Timestamp)params[i]);
					}
					else if (params[i] instanceof Integer)
					{
						stmt.setInt(i+1,((Integer)params[i]).intValue());
					}
					else
					{
						stmt.setString(i+1,params[i].toString());
					}
				}
			}
			//System.out.println("stmt == stmt"+stmt.toString());
			result							= new ResultSet(stmt.executeQuery());

		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	
			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}


	// update the DB given queryString and a SequenceMap (of Fields as keys)
	public static int update(
							String queryString,
							SequenceMap params,
							String connectionName)
										throws SQLException{

		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		connection.setAutoCommit(false);
		int result								= -1;
		PreparedStatement stmt					= null;
		try{
			stmt								= connection.prepareStatement(queryString);
			SQLQueryGenerator.setParams(connection,stmt,params, IntConstants.DEFAULT_INT);
			result								= stmt.executeUpdate();
		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	

			connection.commit();
			connection.setAutoCommit(true);
			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}
	/**
	 * Allow multiple data insert for table
	 * @author abhishek gupta
	 * @param queryString
	 * @param params
	 * @param connectionName
	 * @return
	 * @throws SQLException
	 */
	public static int[] updateMultiple(String queryString, SequenceMap[] params, String connectionName) throws SQLException{
		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		connection.setAutoCommit(false);
		int[] result								= null;
		PreparedStatement stmt					= null;
		try{
			stmt	= connection.prepareStatement(queryString);
			/**
			 * Setting all parameters as get in array map
			 */
			SQLQueryGenerator.setBatchParams(connection,stmt, params);
			result	= stmt.executeBatch();
		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	

			connection.commit();
			connection.setAutoCommit(true);
			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}
		// update the DB given queryString and a SequenceMap (of Fields as keys)
	public static int update(
							String queryString,
							SequenceMap paramMapUpdate,
							SequenceMap paramMapWhere,
							String connectionName)
										throws SQLException{

		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		int result								= -1;
		PreparedStatement stmt					= null;
		try{
			stmt								= connection.prepareStatement(queryString);
			int nNextIndex						= SQLQueryGenerator.setParams(
																			connection,
																			stmt,
																			paramMapUpdate,
																			IntConstants.DEFAULT_INT
																			);
			SQLQueryGenerator.setParams(connection,stmt, paramMapWhere, nNextIndex);
			result								= stmt.executeUpdate();
		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	

			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}
	// update the DB given queryString and a Integer[] of ids
	public static int update(
							String queryString,
							Integer[] ids,
							String connectionName) throws SQLException{

		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		int result								= -1;
		PreparedStatement stmt					= null;
		try	{
			 stmt				= connection.prepareStatement(queryString);
			for (int i=0;i<ids.length;i++){
				stmt.setInt(i+1,ids[i].intValue());
			}
			result								= stmt.executeUpdate();
		}finally{
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	

			connectionManager.freeConnection(connectionName,connection);
		}
		return result;
	}
	public static Integer[] createAndFetchID(
											String queryString,
											SequenceMap params,
											String connectionName) throws SQLException{
		Integer[] id							= new Integer[1];
		id[0]									= new Integer(-1);
		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		PreparedStatement stmt					= null;
		java.sql.ResultSet rset					= null;
		try{
			stmt				= connection.prepareStatement(queryString);
			SQLQueryGenerator.setParams(connection,stmt,params, IntConstants.DEFAULT_INT);
			stmt.execute();
			boolean resultNext;
			while(!(resultNext = stmt.getMoreResults())){
			}
			rset								= stmt.getResultSet();
			rset.next();
			int intID							= rset.getInt(1);
			id[0]								= new Integer(intID);
		}finally{

			if(rset != null){
				try{
					rset.close();
					rset = null;
				}
				catch(Exception e){
				}
			}	
			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}	
		/*	try{
				rset.close();
				stmt.close();
			}
			catch(Exception e){
			}*/
			connectionManager.freeConnection(connectionName,connection);
		}
 
		return id;
	}
	public static SequenceMap getSequenceMap(
											ResultSet result,
											Field[] fields,
											Field[] idField) throws SQLException{

		SequenceMap map							= new SequenceMap();
 
		while(result.next()){
 
			Info info							= getInfo(result, fields, idField);
			map.put(info.getID(),info);
		}
		return map;
	}
	public static String getStringFromClob(Clob obj) {
		String retString = "";
		if (obj == null) return retString;
		try{
			Clob clob = (Clob)obj;
			if (clob.length() > 0){
				retString = clob.getSubString(1,(int)clob.length());
			}
		}catch(SQLException sqle){
		}
		return retString;
	}
	public static Object transformHelper(
							SQLUtilHelper helper,
							Field pField,
							Object pValue
							)throws Exception{
								
		String sMethodName	= pField.getTransformMethod();
		if(sMethodName == null){
			return pValue;
		}
		try{
				String transformMethod = pField.getTransformMethod();
				if(methodList.contains(transformMethod))
				{	
					Method method		= helper.getClass().
													getDeclaredMethod(transformMethod,
														new Class[]{
																Object.class
																	}
																	);
					String sNewVal		= (String)method.invoke(
												null,
												new Object[]{pValue}
												);
					return sNewVal;
				}
				else
				{
					return pValue.toString();
				}
				
			}
		catch(Exception e) {
		}
		return pValue;
	}

	/**
	Methods to control transaction.
	*/
	public static void beginTransaction(String connectionName){
		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		try{
			connection.setAutoCommit(false);
		}catch(Exception e){
		}finally{
		
			connectionManager.freeConnection(connectionName,connection);
		}

	}
	public static void rollbackTransaction(String connectionName){
		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		try{
			connection.rollback();
		}catch(Exception e){
		}finally{
			connectionManager.freeConnection(connectionName,connection);
		}
	
	}
	public static void commitTransaction(String connectionName){
		DBConnectionManager connectionManager	= DBConnectionManager.getInstance();
		Connection connection					= connectionManager.getConnection(connectionName);
		try{
			connection.commit();
			}catch(Exception e){
			}finally{
			connectionManager.freeConnection(connectionName,connection);
		}
	}

	public static String transformDefaultIntegerValToBlank(Object val)
	{
		return BaseUtils.transformDefaultIntegerValToBlank((String)val);
	}
	
	public static String transformDouble(Object pValue) {
		if(pValue == null || pValue.toString().equals("0.0")) {
			return null;
		}
		else {
			try{
				return (NumberFormatUtils.formatCommaNumber(pValue.toString()));
			}catch(Exception e) {
			}
			return pValue.toString();
		}

	}
	
	public static String transformCheckBox(Object id){
		if(id == null) return "";
		else 
			return (String)checkBoxMap.get(id.toString());
	}
	/**
	*	transformLiquorType transforms the value of a checkbox to beer, wine or both
	
	 */

	public static String transformLiquorType(Object id){
		if(id == null) return "";
		else 
			return (String)liquorMap.get(id.toString());
	}
	/**
	 *	transformCountry returns the countryname corresponding to countryId
	 */
	public static String transformCountry(Object countryId){
		try{
			if(countryId == null) return "";
			else
//				return (String) CacheDataUtil.getCountryMap().get(countryId.toString());
			return BaseUtils.getCountryName(countryId.toString());
		}
		catch (Exception e){
		}
		return "";
	}
	public static String transformEntityType(Object entityTypeID){

		try{
			//return PortalUtils.getEntityType(entityTypeID.toString());
			return BaseUtils.getEntityType(entityTypeID.toString());    //For Product_Seperation_BL By Amar Singh.
		}catch(Exception e){
		}
		return "";
	}
	public static String transformStoreStatus(Object storeID){
		try{
			//return PortalUtils.getStoreStatus(storeID.toString());
			return BaseUtils.getStoreStatus(storeID.toString());    //For Product_Seperation_BL By Amar Singh.
		}catch(Exception e){
		}
		return "";
	}
	public static String transformTitle(String titleId){
	try{
			//return PortalUtils.getTitle(titleId);
			return BaseUtils.getTitle(titleId);    //For Product_Seperation_BL By Amar Singh.
		}catch(Exception e){
			Debug.print(e);
		}
		return "";
	}
	public static String transformState(Object stateId){
		try{
			
			return transformState(stateId.toString());
		}catch(Exception e){
			Debug.print(e);
		}
		return "";
	}
	public static String transformState(String stateId){
		try{
			return BaseUtils.getStateFromRegion(stateId);    //For Product_Seperation_BL By Amar Singh.
		}
		catch(Exception e)
		{
			Debug.print(e);
		}
		return "";
	}
	public static String transformTrans(Object transID){
		try{
			return BaseUtils.getTransName(transID.toString());   //For Product_Seperation_BL By Amar Singh.
		}catch(Exception e){
		}
		return "";
     }


}