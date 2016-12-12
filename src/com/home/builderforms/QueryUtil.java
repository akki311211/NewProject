
package com.home.builderforms;



import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.home.builderforms.Info;

import com.home.builderforms.AppException;
import com.home.builderforms.sqlqueries.ResultSet;





/**

 *   Class for utility methods for SQL Queries

 *

 *@author     misam

 *@created    November 9, 2001
	*@ updated  June 2 2005 by Parveen
 *   added a method executeInsertAndReturnIds()
 *   which returns comma separated string of ids of rows inserted
 *   P_A_E_AUTOMATIC_TASKS 4/06/2009 Chetan Sharma       Added method for task trigger.  
 *   Topper_campaign_mail	06Sep2012		Vivek Maurya		added method to execute prepared statements in batch
 *   BBEH_FOR_CODE__OPTIMIZATION  21/05/2013  Rohit Jain    Add the check of null for parameter in update Method and Optimization the code, remove sop and printStackTrace.
 *   P_OPT_CONNECTIONS		12/05/2015		Sanshey			Opt		Connection was not properly closed 
 */

public class QueryUtil {



	//static String lastInsertId	="";



    /**

     *   Method returns comma (',') separated string of elements of an String array.     *

     *@param  elems   String array

     *@return         Return String value

     */

    public static String toCommaSeparated(String[] elems) {

        if (elems == null || elems.length == 0) {

            return "";

        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < elems.length; i++) {

            if (i != 0) {

                sb.append(", ");

            }

            sb.append(elems[i]);

        }

        return sb.toString();

    }





    /**

     *  Method returns comma (',') separated string of elements of an ArrayList array.    *

     *@param  al   ArrayList parameter

     *@return      Return String value

     */

    public static String toCommaSeparated(ArrayList al) {

        String[] elems = new String[al.size()];



        Iterator it = al.iterator();



        for (int i = 0; it.hasNext(); i++) {

            elems[i] = ((CustomField) it.next()).getDbColumnName();

        }



        return toCommaSeparated(elems);

    }





    /**

     *   Method returns comma separated string with 'tablename.columnname'
     *   format of every element in it.
     *   
     *@param  al        as an ArrayList  Parameter

     *@param  tableName   as an String value

     *@return             Return String value

     */

    public static String toCommaSeparated(ArrayList al, String tableName) {

        String[] elems = new String[al.size()];



        Iterator it = al.iterator();



        for (int i = 0; it.hasNext(); i++) {

            elems[i] = tableName + "." + ((CustomField) it.next()).getDbColumnName();

        }



        return toCommaSeparated(elems);

    }





    /**

     *   Method prepares an 'OR' separated query string.

     *@param  queryParams   as an two dimensional array with every first dimension
     *element having two 2nd dimension elements,i.e,(name,value) pair.
     *@return               Return String value

     */

    private static String toORSeparated(String[][] queryParams) {

        if (queryParams == null || queryParams.length == 0 || queryParams[0].length != 2) {

            return "";

        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < queryParams.length; i++) {

            if (i != 0) {

                sb.append(" OR ");

            }

            sb.append(queryParams[i][0] + " = '" + queryParams[i][1] + "'");

        }

        return sb.toString();

    }





    /**

     *  Method prepares an 'OR' separated query string.

     *

     *@param  queryParams   as an two dimensional array with every first dimension
     *element having two 2nd dimension elements,i.e,(name,value) pair.

     *@return               Return String value


     */

    private static String toANDSeparated(String[][] queryParams) {

        if (queryParams == null || queryParams.length == 0 || queryParams[0].length != 2) {

            return "";

        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < queryParams.length; i++) {

            if (i != 0) {

                sb.append(" AND ");

            }

            sb.append(queryParams[i][0] + " = '" + queryParams[i][1] + "'");

        }

        return sb.toString();

    }





    /**

     *  Returns the select query for database with 'where' and 'groupby'
     *  clauses also appended and the search parameters separated with 
     *  'AND' opeartor. 

     *

     *@param  tableName     Parameter

     *@param  selectCols    Parameter

     *@param  queryParams   Parameter

     *@param  groupByCols   Parameter

     *@return              The queryString value

     */

    public static String getQueryString(String tableName, String[] selectCols,

            String[][] queryParams, String[] groupByCols) {



        StringBuffer sb = new StringBuffer("SELECT ");



        String cols = toCommaSeparated(selectCols);

        if (cols.equals("")) {

            cols = "*";

        }

        sb.append(cols);



        sb.append(" FROM " + tableName);



        String params = toANDSeparated(queryParams);

        if (!params.equals("")) {

            sb.append(" WHERE " + params);

        }



        String gcols = toCommaSeparated(groupByCols);

        if (!gcols.equals("")) {

            sb.append(" GROUP BY " + gcols);

        }



        return sb.toString();

    }





    /**

     * Returns the select query for database with only 'where' clause
     * appended and the search parameters separated with 
     * 'AND' opeartor.
     *

     *@param  tableName     Parameter

     *@param  selectCols    Parameter

     *@param  queryParams   Parameter

     *@return              The queryString value

     */

    public static String getQueryString(String tableName, String[] selectCols,

            String[][] queryParams) {

        return getQueryString(tableName, selectCols, queryParams, null);

    }



    /**
     * Returns the select query for database with 'where' and 'groupby'
     * clauses also appended and the search parameters separated with 
     * 'OR' opeartor. 
     *@param  tableName     Parameter

     *@param  selectCols    Parameter

     *@param  queryParams   Parameter

     *@param  groupByCols   Parameter

     *@return              The queryStringOR value

     */

    public static String getQueryStringOR(String tableName, String[] selectCols,

            String[][] queryParams, String[] groupByCols) {



        StringBuffer sb = new StringBuffer("SELECT ");



        String cols = toCommaSeparated(selectCols);

        if (cols.equals("")) {

            cols = "*";

        }

        sb.append(cols);



        sb.append(" FROM " + tableName);



        String params = toORSeparated(queryParams);

        if (!params.equals("")) {

            sb.append(" WHERE " + params);

        }



        String gcols = toCommaSeparated(groupByCols);

        if (!gcols.equals("")) {

            sb.append(" GROUP BY " + gcols);

        }



        return sb.toString();

    }

	public static ResultSet getResult(String sqlQuery, Object[] params) {

		return getResult(MultiTenancyUtil.getTenantName(), sqlQuery, params);

	}





    /**

     *  Establishes a connection with given connection name and return the 
     *  ResultSet of query passed as an arguement.

     *

     *@param  sqlQuery   Parameter

     *@param  params     Parameter

     *@return           The result value

     */

    public static ResultSet getResult(String connectionName, String sqlQuery, Object[] params) {
        return getResult(connectionName, sqlQuery, params, false);
    }
    public static ResultSet getResult(String connectionName, String sqlQuery, Object[] params, boolean isSetFetchSize) {
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(connectionName);
        ResultSet result = null;
        PreparedStatement stmt = null;
        try {
            if (isSetFetchSize) {
                stmt = connection.prepareStatement(sqlQuery, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(Integer.MIN_VALUE);
            } else {
                stmt = connection.prepareStatement(sqlQuery);
            }
            if (params!=null){
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof Timestamp) {
                        stmt.setTimestamp(i + 1, (Timestamp) params[i]);
                    } else if (params[i] instanceof String) {
                        stmt.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        stmt.setInt(i + 1, ((Integer) params[i]).intValue());
                    } else {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
            }
            result = new ResultSet(stmt.executeQuery());
        } catch (Exception e) {
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (Exception e) {
            }
            connectionManager.freeConnection(connectionName, connection);
        }
        return result;
    }
    //FastSi-20160819-033 STARTS
    public static java.sql.ResultSet getResultJava(String connectionName, String sqlQuery, Object[] params, boolean isSetFetchSize) {
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(connectionName);
        java.sql.ResultSet result = null;
        PreparedStatement stmt = null;
        try {
            if (isSetFetchSize) {
                stmt = connection.prepareStatement(sqlQuery, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(Integer.MIN_VALUE);
            } else {
                stmt = connection.prepareStatement(sqlQuery);
            }
            if (params!=null){
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof Timestamp) {
                        stmt.setTimestamp(i + 1, (Timestamp) params[i]);
                    } else if (params[i] instanceof String) {
                        stmt.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        stmt.setInt(i + 1, ((Integer) params[i]).intValue());
                    } else {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
            }
            result = stmt.executeQuery();
        } catch (Exception e) {
        } finally {
            try {
                /*if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }*/
            } catch (Exception e) {
            }
            //connectionManager.freeConnection(connectionName, connection);
        }
        return result;
    }		//FastSi-20160819-033 ENDS

    /**

     *  Used for updating records in a table.
     *  Returns 1 if record is updated otherwise returns -1.

     *

     *@param  sqlQuery               Parameter

     *@param  params                 Parameter

     *@return                        Return Value

     *@exception  AppException   Exception

     */

    public static int update(String sqlQuery, String[] params) throws AppException {

        DBConnectionManager connectionManager = DBConnectionManager.getInstance();

        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());

        PreparedStatement stmt = null;

             int result = -1;

        try {

            stmt = connection.prepareStatement(sqlQuery);
          //for null check added by Rohit Jain
         if(params!=null){ 
            for (int i = 0; i < params.length; i++) {

                stmt.setString(i + 1, params[i]);

            }
         }
            result = stmt.executeUpdate();

        } catch (Exception e) {
            throw new AppException(e.toString());

        } finally {

			try
			{

				if(stmt != null){
					stmt.close();
					stmt = null;
				}

			}

			catch(Exception e)

			{

			}

            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);

        }

        return result;

    }

    
    /**
     * Topper_campaign_mail<br/>
     * Method to execute prepared statements in batch for multiple queries.
     * @author Vivek Maurya
     * @date 06Sep2012
     * @param query array of queries
     * @param batchMap
     * @return
     * @throws AppException
     */
    public static void preparedStatementsBatchUpdate(String[] query, Map<String,List<String[]>> batchMap) throws AppException 
    {
    	preparedStatementsBatchUpdate(query, batchMap,false); 
    }
    public static void preparedStatementsBatchUpdate(String[] query, Map<String,List<String[]>> batchMap,boolean throwException) throws AppException 
    {
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        PreparedStatement pstmt=null;
        boolean exception = false;
        try 
        {
		    if(query!=null)
		    {
		    	List<String[]> paramsArray = null;
		    	String paramValues[]=null;
		    	int arrLength = 0;
		    	int valueArrLength = 0;
		    	
		    	for(int i=0;i<query.length;i++)
	        	{
		    		
		    		if(StringUtil.isValid(query[i]))
					{
						connection.setAutoCommit(false);
						pstmt = connection.prepareStatement(query[i]);

						paramsArray = batchMap.get(String.valueOf(i));

						if(paramsArray != null)
						{
							arrLength = paramsArray.size();

							for(int j = 0; j < arrLength; j++)
							{
								paramValues = paramsArray.get(j);
								if(paramValues != null)
								{
									valueArrLength = paramValues.length;

									for(int k = 0; k < valueArrLength; k++)
									{
										pstmt.setString(k + 1, paramValues[k]);
									}

									pstmt.addBatch();
								}

							}
							pstmt.executeBatch();
							connection.commit();
						}
					}
	          }
		    }
        }
        catch (BatchUpdateException e) 
        {
            try
            {
            	connection.rollback();
            }
            catch (Exception e2) 
            {

            }
            exception = true;
        }
        catch (Exception e) 
        {
            exception = true;
        }
        finally 
        {
            try 
            {
            	if(pstmt!=null)
            	{
            	  pstmt.close();
            	}
			} catch (SQLException e)
			{
			}
 			connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
 			if(exception && throwException)
            {
                throw new AppException();
            }
        }
    }
    
    /*
     * this method is added by anilT on date 29 Sep 2011
     * it is used for updating multiple update queries in a batch
     */
        /**
         * P_E_CampaignCenter_Triggers
         * Changed first parameter to List
         * @updated by Vivek Maurya
         * @date 26July2012
         */
        

    public static int batchUpdate(List<String> batchQuery, String[] params) throws AppException 
    {

        DBConnectionManager connectionManager = DBConnectionManager.getInstance();

        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        
        Statement stmt = null;
        String fetchQuery=null;
        int[] updateCounts = null;

        try {
        	connection.setAutoCommit(false);
        	stmt = connection.createStatement();

		   if(batchQuery!=null && batchQuery.size()>0){
			   for(int j=0;j<batchQuery.size();j++){
			   fetchQuery = batchQuery.get(j);
		            stmt.addBatch(fetchQuery);
			   }    

		   }
   
		   updateCounts = stmt.executeBatch();
		   
		   connection.commit();

        } 
        catch (BatchUpdateException e) 
        {
            try {
            	connection.rollback();
            } catch (Exception e2) {
              e.printStackTrace();
            }
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            try {
				stmt.close();
			} catch (SQLException e) {
			}
            
            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);

          }

        return updateCounts.length;

    }
    
   

    /**

     *  Gets the displayData attribute of the QueryUtil class

     *

     *@param  fields   Parameter

     *@param  result   Parameter

     *@return         The displayData value

     */

    public static DisplayData getDisplayData(ArrayList fields, ResultSet result) {

        DisplayData displayData = new DisplayData(fields);

        try {

            while (result.next()) {

                ArrayList dataRow = new ArrayList();

                for (int i = 0; i < fields.size(); i++) {

                    dataRow.add(result.getString(i + 1));

                }

                displayData.addDataRow(dataRow);

            }

        } catch (Exception e) {


        }

        return displayData;

    }





    /**

     *  Gets the displayData attribute of the QueryUtil class

     *

     *@param  fields     Parameter

     *@param  sqlQuery   Parameter

     *@param  params     Parameter

     *@return           The displayData value

     */

    public static DisplayData getDisplayData(ArrayList fields, String sqlQuery, String[] params) {

        return getDisplayData(fields, getResult(sqlQuery, params));

    }





    /**

     *  Gets the orderByDbColumn attribute of the QueryUtil class

     *

     *@param  al   Parameter  

     *@return     The orderByDbColumn value

     */

    public static String getOrderByDbColumn(ArrayList al) {

        String column = null;

        for (Iterator it = al.iterator(); it != null && it.hasNext(); ) {

            CustomField field = (CustomField) it.next();

            if (field.isOrderBy()) {

                column = field.getDbColumnName();

                break;

            }

        }

        return column;

    }

    /**
     * Returns the result of an query passed as an argument.
     * @param connectionName Parameter
     * @param sqlQuery Parameter
     * @param params Parameter
     * @return
     */
//    PH_Intranet_Messages_Optimized Start
    public static Object[] getResultJava(java.sql.Connection connectionName, String sqlQuery, Object[] params) {
        java.sql.ResultSet result = null;
        PreparedStatement stmt = null;
        try {
            stmt = connectionName.prepareStatement(sqlQuery);
            // set the params in statement
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof Timestamp) {
                        stmt.setTimestamp(i + 1, (Timestamp) params[i]);
                    } else if (params[i] instanceof String) {
                        stmt.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        stmt.setInt(i + 1, ((Integer) params[i]).intValue());
                    } else {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
            }
            // now execute query 
            result = stmt.executeQuery();
            // returing both the jdbc objects .. caller method must CLOSE these resources
            return new Object[]{result,stmt}; 
        }  catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
//    PH_Intranet_Messages_Optimized End


/**
 * Returns 1 if record is inserted otherwise -1.
 * @param sSqlQuery  Parameter
 * @return integer value
 */
    public static int executeInsert(String sSqlQuery)
	{
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        Statement stmt = null;
        PreparedStatement stmtId = null;
		ResultSet result=null;
        int iResult = -1;

        try
		{
            stmt = connection.createStatement();
			iResult = stmt.executeUpdate(sSqlQuery);
			//for LAST_INSERT_ID
			stmtId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			result=new ResultSet(stmtId.executeQuery());
			if(result.next()){
				MultiTenancyUtil.getTenantConstants().LAST_INSERTED_ID =result.getString(1);
			}
        }
		catch (Exception e)
		{
        }
		finally
		{
			try
			{
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
			}
			catch(Exception e)
			{
			}

			try
			{
				if(stmtId != null){
					stmtId.close();
					stmtId = null;
				}
			}
			catch(Exception e)
			{
			}

			if(connection != null)
            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
        }

       return iResult;

    }
/**
 * Returns 1 if record is inserted otherwise -1.
 * @param1 sSqlQuery  Parameter
 * @param1 params  Parameter
 * @return integer value
 */
public static int executeInsert(String sSqlQuery,String[] params) {
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        
		PreparedStatement stmt = null;
		PreparedStatement stmtId = null;
		ResultSet result=null;
        int iResult = -1;
        

        try {

            stmt = connection.prepareStatement(sSqlQuery);
            //Nishant Added check for null
            if(params != null){
	            for (int i = 0; i < params.length; i++) {
					stmt.setString(i + 1, params[i]);
	            }
            }
            
    		iResult = stmt.executeUpdate();
			
			//for LAST_INSERT_ID
			stmtId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			result=new ResultSet(stmtId.executeQuery());
			if(result.next()){
				MultiTenancyUtil.getTenantConstants().LAST_INSERTED_ID =result.getString(1);
			}
        }
		catch (Exception e)
		{
        }
		finally
		{
			try
			{
				if(stmt != null){
					stmt.close();
					stmt = null;
				} 
				if(stmtId != null){
					stmtId.close();
					stmtId = null;
				}
				
			}
			catch(Exception e)
			{
			}

			if(connection != null)
            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
        }

       return iResult;

}
	
    /**
     * Insert recrds in the table amd returns comma separated string of
     * IDs .
     * @param sSqlQuery   Parameter
     * @return String value
     */
	 public static String executeInsertAndReturnIds(String sSqlQuery)
	{
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        Statement stmt = null;
        PreparedStatement stmtId = null;
		ResultSet result=null;
		ResultSet result1=null;
        int iResult = -1;
		StringBuffer  ids = new  StringBuffer();
        try
		{
            stmt = connection.createStatement();
			iResult = stmt.executeUpdate(sSqlQuery,Statement.RETURN_GENERATED_KEYS);//FS_NewMySQL_Query
			
			result1=new ResultSet(stmt.getGeneratedKeys());
			if(result1.next()){
				ids.append(result1.getString(1));
			}

			while(result1.next()){
				ids.append(",").append(result1.getString(1));
			}
			
			//for LAST_INSERT_ID
			stmtId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			result=new ResultSet(stmtId.executeQuery());
			if(result.next()){
				MultiTenancyUtil.getTenantConstants().LAST_INSERTED_ID =result.getString(1);
			}
        }
		catch (Exception e)
		{
        }
		finally
		{
			try
			{
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
			}
			catch(Exception e)
			{
			}

			try
			{
				if(stmtId != null){
					stmtId.close();
					stmtId = null;
				}
			}
			catch(Exception e)
			{
			}

			if(connection != null)
            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
        }

       return ids.toString();

    }

	 // P_A_E_AUTOMATIC_TASKS
	 
	 public static String executeInsertAndReturnIds(String sSqlQuery,String[] params)
		{
	        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
	        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
	        PreparedStatement stmt = null;
	        PreparedStatement stmtId = null;
			ResultSet result=null;
			ResultSet result1=null;
	        int iResult = -1;
			StringBuffer  ids = new  StringBuffer();
	        try
			{
	        	stmt = connection.prepareStatement(sSqlQuery,Statement.RETURN_GENERATED_KEYS);//FS_NewMySQL_Query
	            for (int i = 0; i < params.length; i++) {
					stmt.setString(i + 1, params[i]);
	            }
	    		iResult = stmt.executeUpdate();
	    		
	          			

				result1=new ResultSet(stmt.getGeneratedKeys());
				if(result1.next()){
					ids.append(result1.getString(1));
				}

				while(result1.next()){
					ids.append(",").append(result1.getString(1));
				}
				
				//for LAST_INSERT_ID
				stmtId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
				result=new ResultSet(stmtId.executeQuery());
				if(result.next()){
					MultiTenancyUtil.getTenantConstants().LAST_INSERTED_ID =result.getString(1);
				}
	        }
			catch (Exception e)
			{
	        }
			finally
			{
				try
				{
					if(stmt != null){
						stmt.close();
						stmt = null;
					}
				}
				catch(Exception e)
				{
				}

				try
				{
					if(stmtId != null){
						stmtId.close();
						stmtId = null;
					}
				}
				catch(Exception e)
				{
				}

				if(connection != null)
	            connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
	        }

	       return ids.toString();

	    }
	 // P_A_E_AUTOMATIC_TASKS

	 /**
	  * This function used only when structural query need to do for Form Builder.
	  * Call will make sure only one connection exist and allowed to execute alter query at database to avoid metadata lock. 
	  * @author abhishek gupta
	  * @since 19 oct 2012
	  * @param sqlQuery
	  * @return
	  * @throws AppException
	  */
	 public static int alterDBTable(String sqlQuery) throws AppException {
		 DBConnectionManager connectionManager = DBConnectionManager.getInstance();
		 try {
			   	connectionManager.releaseConnections(MultiTenancyUtil.getTenantName());
		 } catch(Exception e) {
		 }
		 
		 Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
		 PreparedStatement stmt = null;
		 int result = -1;
		 try {
			 stmt = connection.prepareStatement(sqlQuery);
			 result = stmt.executeUpdate();
		 } catch (Exception e) {
			 throw new AppException(e.toString());
		 } finally {
			 try {
				 if(stmt != null) {
					 stmt.close();
					 stmt = null;
				 }
			 } catch(Exception e) {
			 }
			 connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
		 }
		 return result;
	 }
	 
/**
 * returns the id of last inserted record.
 * @return string value
 */
	public static String getLastInsertId(){
		return MultiTenancyUtil.getTenantConstants().LAST_INSERTED_ID;
	}
/**
 * @author Balvinder Mehla
 * @param result
 * To release the resultSet which is acquired from QueryUtil.getResult etc
 */
	public static void  releaseResultSet(Object result)
	{
		
		if(result instanceof java.sql.ResultSet)
		{
			
			try
			{
			((java.sql.ResultSet) result).close();
			result=null;
			}
			catch (SQLException e) {

			}
		}
		else if(result instanceof ResultSet)
		{
			
			((ResultSet) result).release();
			result=null;
		}
	}

    private static final HashMap<String,String> sqlTokens;
    private static Pattern sqlTokenPattern;
    static
    {
        //MySQL escape sequences: http://dev.mysql.com/doc/refman/5.1/en/string-syntax.html
        String[][] search_regex_replacement = new String[][]
                {
                        //search string     search regex        sql replacement regex
                        {   "\u0000"    ,       "\\x00"     ,       "\\\\0"     },
                        {   "'"         ,       "'"         ,       "\\\\'"     },
                        {   "\""        ,       "\""        ,       "\\\\\""    },
                        {   "\b"        ,       "\\x08"     ,       "\\\\b"     },
                        {   "\n"        ,       "\\n"       ,       "\\\\n"     },
                        {   "\r"        ,       "\\r"       ,       "\\\\r"     },
                        {   "\t"        ,       "\\t"       ,       "\\\\t"     },
                        {   "\u001A"    ,       "\\x1A"     ,       "\\\\Z"     },
                        {   "\\"        ,       "\\\\"      ,       "\\\\\\\\\\\\\\\\"  }
                };

        sqlTokens = new HashMap<String,String>();
        String patternStr = "";
        for (String[] srr : search_regex_replacement)
        {
            sqlTokens.put(srr[0], srr[2]);
            patternStr += (patternStr.isEmpty() ? "" : "|") + srr[1];
        }
        sqlTokenPattern = Pattern.compile('(' + patternStr + ')');
    }

    /**
     * This method is used to escape special characters of param passed in sql query. Pass parameter as is it without escaping any char.
     * @author Dravit Gupta
     * @param s Parameter of query that needs to be escaped.
     * @return String
     */
    public static String escape(String s)
    {
        Matcher matcher = sqlTokenPattern.matcher(s);
        StringBuffer sb = new StringBuffer();
        while(matcher.find())
        {
            matcher.appendReplacement(sb, sqlTokens.get(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * This method is used to get the primary key column of the given table
     * @author Nishant Prabhakar
     * @param tableName name of table.
     * @return String
     * ZCB-20141028-057
     */
    public static String getPrimaryColumn(String tableName)
    {
    	DBConnectionManager connectionManager = DBConnectionManager.getInstance();
    	Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
    	String columnName = null;
    	java.sql.ResultSet result = null;	//P_OPT_CONNECTIONS
    	try{
    		DatabaseMetaData databaseMetaData = connection.getMetaData();

    		result = databaseMetaData.getPrimaryKeys(null, null, tableName);
    		while(result.next()){
    		    columnName = result.getString("COLUMN_NAME");
    		}
    	}
    	catch (SQLException e) {

		}finally{	//P_OPT_CONNECTIONS
			releaseResultSet(result);
			connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
		}
    	return columnName;
    }
    //ZCB-20141028-057 ends
    /**
     * This method returns metadata for a column such as DATA_TYPE,DEFAULT_VALUE etc
     * // BB-20150525-360
     * @author Akash Kumar
     * @param TableName
     * @param ColumnName
     * @return Info of metaData of column
     */
    public static Info getColumnMetaData(String TableName,String ColumnName) 
    {
	DBConnectionManager connectionManager = DBConnectionManager.getInstance();
	Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
    Info info=null;
    java.sql.ResultSet rs = null;	//P_OPT_CONNECTIONS
    try{
    DatabaseMetaData md = connection.getMetaData() ;
    rs = md.getColumns(connection.getCatalog(), md.getUserName(),TableName,ColumnName);
    if(rs.next())
    {
    	info=new Info();
        info.set("DEFAULT_VALUE",rs.getString("COLUMN_DEF"));
        info.set("DATA_TYPE",rs.getString("DATA_TYPE"));
        info.set("COLUMN_SIZE",rs.getString("COLUMN_SIZE"));
        info.set("TYPE_NAME",rs.getString("TYPE_NAME"));
    }
    }
    catch (SQLException e) {

	}finally{	//P_OPT_CONNECTIONS
		releaseResultSet(rs);
		connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
	}
    return info;
      
    }
}




