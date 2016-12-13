package com.home.builderforms.sqlqueries;



import java.lang.StringBuffer;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;



import java.util.Map;







import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.DateTime;
import com.home.builderforms.DateUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.sqlqueries.SQLQuery;
import com.home.builderforms.Info;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IntConstants;



import com.home.builderforms.StringUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.*;

import java.sql.*;



public class SQLQueryGenerator{


	/**
	 * Used for Structured query operations
	 */
	public static final String CREATE    	= "CREATE";
	public static final String ALTER     	= "ALTER";
	public static final String DROP      	= "DROP";
	public static final String SPACE     	= " ";
	public static final String TABLE     	= "TABLE";
	public static final String IF_EXISTS 	= "IF EXISTS";
	public static final String COLUMN 	 	= "COLUMN";
	public static final String ADD		 	= "ADD";
	public static final String MODIFY		= "MODIFY";
	public static final String DELETE	 	= "DELETE";
	public static final String PRIMARY_KEY	= "PRIMARY KEY";
	public static final String DEFAULT		= "DEFAULT";
	public static final String NULL			= "NULL";
	public static final String DOUBLE		= "DOUBLE";
	public static final String INT			= "INT";
	public static final String VARCHAR		= "VARCHAR";
	public static final String TEXT			= "TEXT";
	public static final String AUTO_INCREMENT			= "AUTO_INCREMENT";
	public static final String FLOAT			= "FLOAT";    //PW_ENH_QUE_NUMBER
	// Method to set parameters in a prepared statement,

	// Takes the prepared statement and a SequenceMap (of Field as keys)

	// as parameters

	public static int setParams(Connection con,PreparedStatement stmt, SequenceMap params, int nIndex)

																throws SQLException{

		Iterator paramFieldsIter		= params.keys().iterator();

		if(nIndex == IntConstants.DEFAULT_INT){

			nIndex						= 1;

		}

		int i							= 1;

		for (i=nIndex; paramFieldsIter.hasNext();){

			Field field					= (Field)paramFieldsIter.next();

			Object value				= params.get(field);
			/**
			 * Integer type DB Field not accept null or empty value as input.
			 * When integer type field found then need to take care of null or empty inputs.
			 * System support only +ve value for Integer data type so pass -1 for such Field to avoid 0 value in database.
			 * @author abhishek gupta
			 * @date 24 Aug 2012   
			 */
			if (value == null){
				if(field.getValidationType() != null && field.getValidationType().equals("Integer")) {
					stmt.setObject(i++,-1);
				} else {
					stmt.setString(i++,null);
				}
			} else if ("".equals(value) && field.getValidationType() != null && field.getValidationType().equals("Integer")) {
				stmt.setObject(i++,-1);
			}else if (value.getClass().isArray()){

				Object[] values			= (Object[])value;



				for (int j=0; j<values.length; j++){
					
					setParam(i++,con, stmt, field, values[j]);

				}

			}else{
				setParam(i++,con, stmt, field, value);

			}


		}

		return i;

	}



	public static void setParam(

								int i,

								Connection con,

								PreparedStatement stmt,

								Field field,

								Object value) throws SQLException{



		String dataType					= field.getDataType();

		try{

			if (Field.DataType.INTEGER.equals(dataType)){

					stmt.setInt(i,((Integer)value).intValue());

			}else if (Field.DataType.DATE.equals(dataType)){
				// Added By Nikhil Verma
				// If date format comes in mm/dd/yyyy or dd/mm/yyyyy it converts in DB format.
				if(value!=null && ((String)value).indexOf('/')!=-1)
				{
						String dateValue=(String)value;
						if(((String)value).length() == 11){
							dateValue = DateUtil.getDbDate(dateValue);
						}
						stmt.setTimestamp(i,DateTime.getTimestamp(dateValue));
				}
				else
					stmt.setTimestamp(i,(Timestamp)value);

			}else if (Field.DataType.BOOLEAN.equals(dataType)){

				stmt.setBoolean(i,((Boolean)value).booleanValue());

			}else if (Field.DataType.DOUBLE.equals(dataType)){

				stmt.setDouble(i, ((Double)value).doubleValue());

			}else if (Field.DataType.CLOB.equals(dataType)){

					Clob clob  = ClobUtil.getClob(con,(String)value);

					stmt.setClob(i,clob);

			}else{

				stmt.setString(i,(String)value);

			}

		}catch(ClassCastException e)

		{


			stmt.setString(i,value.toString());

		}

	}



	// Similar to the method except that params can have multiple values in the where clause

	// The nameValueMap has name Integer mappings where the Integer denotes the number of

	// values for the name

	public static String getWhereClauseQueryString(

													String startString,

													FieldMappings fieldMappings,

													SequenceMap nameValuesMap){

			StringBuffer sb				= new StringBuffer(startString);



			Iterator nameValuesIter		= null;

			if (nameValuesMap != null){

				nameValuesIter			= nameValuesMap.keys().iterator();

			}

			if (nameValuesIter != null && nameValuesIter.hasNext()){

				sb.append(" WHERE ");

			}

			boolean flag = false;

			while(nameValuesIter != null && nameValuesIter.hasNext()){

				String fieldName		= (String)nameValuesIter.next();

				Object obj				= nameValuesMap.get(fieldName);

				int noOfValues			= 0;

				if (obj.getClass().isArray()){

					noOfValues 			= ((Object[])obj).length;

				}else{

					noOfValues			= 1;

				}



				if (noOfValues > 0){

					if (flag)

					{

						sb.append(" AND ");

					}

					else

					{

						flag = true;

					}

					sb.append(fieldMappings.getDbField(fieldName));

					if (noOfValues==1){

						sb.append(" = ? ");

					}else{

						sb.append(" IN ( "+StringUtil.toNtimes("?",noOfValues) +") ");

					}



				}

			}

			return sb.toString();

	}

	public static String getDetailsInfoQueryString(FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("SELECT ");
		/**
		 * Allow only those DB Fields which are not used additionally for records manipulation as done in export.
		 * @author abhishek gupta
		 * @date 1 Jun, 2012
		 */
		String[] dbFields				= fieldMappings.getAllValidDbFields();
		//sb.append(StringUtil.toCommaSeparated(fieldMappings.getAllDbFields()));

		Map<String, Boolean> piiCheckMap = new HashMap<String, Boolean>();
		SequenceMap allFieldsMap = fieldMappings.getAllFields();
		for(int i=0; i < allFieldsMap.size(); i++) {
			piiCheckMap.put(fieldMappings.getDbField((String)allFieldsMap.getKey(i)), fieldMappings.getField((String)allFieldsMap.getKey(i)).isPiiEnabled());
		}
		piiCheckMap.put("TAXPAYER_ID", true);
		for (int i = 0; i < dbFields.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            if(piiCheckMap.get(dbFields[i])) {
                sb.append(" AES_DECRYPT("+dbFields[i]+",'pvm@e20') ");
            } else {
                sb.append(dbFields[i]);
            }
        }
		
		sb.append(" FROM "+fieldMappings.getTableName());

		Field[] fields					= fieldMappings.getIdField();

		for (int loop = 0; loop < fields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE " + fields[loop].getDbField() + " = ? ");

			}

			else {

				sb.append(" AND " + fields[loop].getDbField() + " = ? ");

			}

		}

		return sb.toString();

	}

	public static String getDetailsInfoQueryString(FieldMappings fieldMappings, SequenceMap paramMap){

		return getWhereClauseQueryString(

									"SELECT " +

										StringUtil.toCommaSeparated(fieldMappings.getAllValidDbFields())+

										" FROM " + fieldMappings.getTableName(),

									fieldMappings, paramMap

										);



	}

	public static String getSummaryInfoQueryString(FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("SELECT ");
		/**
		 * Allow only those DB Fields which are not used additionally for records manipulation as done in export.
		 * @author abhishek gupta
		 * @date 1 Jun, 2012
		 */
		//sb.append(StringUtil.toCommaSeparated(fieldMappings.getSummaryDbFields()));
		sb.append(StringUtil.toCommaSeparated(fieldMappings.getValidSummaryDbFields()));

		sb.append(" FROM "+fieldMappings.getTableName());



		Field[] fields					= fieldMappings.getIdField();

		for (int loop = 0; loop < fields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE ");

			}

			else {

				sb.append(" AND ");

			}

			sb.append(fields[loop].getDbField()).append(" = ? ");

		}

		return sb.toString();

	}


	/**
	 * Checking Field which are not required in Insert while used for other purpose like Export etc.
	 * This case exist for ID Field which is used for export to get some records exist in other table.
	 * Function have been replaced to create Query with only required Fields useful for inserting records in same table. 
	 * @author abhishek gupta
	 * @date 31 May 2012
	 */
	public static String getCreateQueryString(FieldMappings fieldMappings,boolean insertID)

	{

		StringBuffer sb					= new StringBuffer("INSERT INTO ");

		String[] dbFields				= null;

		if (insertID){
			//dbFields					= fieldMappings.getAllDbFields();
			dbFields					= fieldMappings.getAllValidDbFields();

		}else{
			//dbFields					= fieldMappings.getAllDbFieldsSansID();
			dbFields					= fieldMappings.getAllValidDbFieldsSansID();

		}
		// BB-20150525-360 starts
		Map<String, Boolean> piiCheckMap = new HashMap<String, Boolean>();
		SequenceMap allFields = fieldMappings.getAllFields();
		for(int i=0; i < allFields.size(); i++) {
			piiCheckMap.put(fieldMappings.getDbField((String)allFields.getKey(i)), fieldMappings.getField((String)allFields.getKey(i)).isPiiEnabled());
		}//// BB-20150525-360 ends
		piiCheckMap.put("TAXPAYER_ID", true);
		sb.append(fieldMappings.getTableName());

		sb.append("(");

		sb.append(StringUtil.toCommaSeparated(dbFields));

		sb.append(") VALUES (");

		//sb.append(StringUtil.toNtimes("?",dbFields.length));
		sb.append(StringUtil.toNtimesWithEncryption("?",dbFields.length, piiCheckMap, dbFields)); // BB-20150525-360

		sb.append(")");

		return sb.toString();

	}



	public static String getCreateAndFetchQueryString(FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("BEGIN \n");

		sb.append(getCreateQueryString(fieldMappings,false)+"\n");

		sb.append("SELECT IDENT_CURRENT('");

		sb.append(fieldMappings.getTableName()+"')\n");

		sb.append("END");

		return sb.toString();

	}






	/**
	 * Checking Field which are not required in Insert while used for other purpose like Export etc.
	 * This case exist for ID Field which is used for export to get some records exist in other table.
	 * Function have been replaced to create Query with only required Fields useful for inserting records in same table. 
	 * @author abhishek gupta
	 * @date 31 May 2012
	 */
	public static String getReplaceQueryString(FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("UPDATE ");

		//String[] dbFields				= fieldMappings.getAllDbFieldsSansID();
		String[] dbFields				= fieldMappings.getAllValidDbFieldsSansID();

		String[] fields					= StringUtil.getArrayWithEquals(dbFields);
		
		sb.append(fieldMappings.getTableName());

		sb.append(" SET ");

		sb.append(StringUtil.toCommaSeparated(fields));



		Field[] idFields				= fieldMappings.getIdField();

		for (int loop = 0; loop < idFields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE ");

			}

			else {

				sb.append(" AND ");

			}

			sb.append(idFields[loop].getDbField()).append(" = ? ");

		}

		return sb.toString();

	}



	public static String getModifyQueryString(String[] dbFields, FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("UPDATE ");
		Map<String, Boolean> piiCheckMap = new HashMap<String, Boolean>();// BB-20150525-360 starts
		SequenceMap allFields = fieldMappings.getAllFields();
		for(int i=0; i < allFields.size(); i++) {
			piiCheckMap.put(fieldMappings.getDbField((String)allFields.getKey(i)), fieldMappings.getField((String)allFields.getKey(i)).isPiiEnabled());
		}
		piiCheckMap.put("TAXPAYER_ID", true);
		String[] fields					= StringUtil.getArrayWithEqualsWithEncryption(dbFields,piiCheckMap);// BB-20150525-360 ends

		sb.append(fieldMappings.getTableName());

		sb.append(" SET ");

		sb.append(StringUtil.toCommaSeparated(fields));



		Field[] idFields				= fieldMappings.getIdField();

		for (int loop = 0; loop < idFields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE ");

			}

			else {

				sb.append(" AND ");

			}

			sb.append(idFields[loop].getDbField()).append(" = ? ");

		}
		return sb.toString();

	}

	public static String getModifyQueryString(

											String[] dbFields,

											FieldMappings fieldMappings,

											SequenceMap paramMap){

		StringBuffer sb					= new StringBuffer("UPDATE ");

		String[] fields					= StringUtil.getArrayWithEquals(dbFields);

		sb.append(fieldMappings.getTableName());

		sb.append(" SET ");

		sb.append(StringUtil.toCommaSeparated(fields));


		return getWhereClauseQueryString(

									sb.toString() + " ",

									fieldMappings,

									paramMap

										);

	}



	public static String getDeleteQueryString(FieldMappings fieldMappings){

		StringBuffer sb					= new StringBuffer("DELETE FROM ");

		sb.append(fieldMappings.getTableName());

		Field[] fields					= fieldMappings.getIdField();

		for (int loop = 0; loop < fields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE ");

			}

			else {

				sb.append(" AND ");

			}

			sb.append(fields[loop].getDbField()).append(" = ? ");

		}

		return sb.toString();

	}



	public static String getMultipleDeleteQueryString(FieldMappings fieldMappings,int numberOfRecords){

		StringBuffer sb					= new StringBuffer("DELETE FROM ");

		sb.append(fieldMappings.getTableName());



		Field[] fields					= fieldMappings.getIdField();

		for (int loop = 0; loop < fields.length; loop++){

			if (loop == 0) {

				sb.append(" WHERE ");

				sb.append(fields[0].getDbField() + " IN (" + 

							StringUtil.toNtimes("?", numberOfRecords) + ")");

			}

			else {

				sb.append(" AND ");

				sb.append(fields[1].getDbField() + " IN (" + 

							StringUtil.toNtimes("?", numberOfRecords) + ")");

			}

		}

		return sb.toString();

	}



	public static String getDeleteQueryString(FieldMappings fieldMappings,Info info){

		StringBuffer sb					= new StringBuffer("DELETE FROM ");

		sb.append(fieldMappings.getTableName());

		sb.append(" WHERE ");

		Iterator itField				= info.getKeySetIterator();

		while(itField != null && itField.hasNext()){

			String fieldName			= (String)itField.next();

			sb.append(fieldMappings.getDbField(fieldName) + " = ? ");

			if(itField.hasNext()){

				sb.append(" AND ");

			}

		}

		return sb.toString();

	}



	public static String getMultipleDeleteQueryString(FieldMappings fieldMappings, SequenceMap map)	{

		return getWhereClauseQueryString(

									"DELETE FROM " + fieldMappings.getTableName(),

									fieldMappings,

									map

										);

	}



	public static String getCollectionQueryString(FieldMappings fieldMappings,

												  String [] fieldNames,

												  SequenceMap map){

		String[] dbFields				= new String[fieldNames.length];
		//P_FS_Enh_BuilderForm starts
		String table = fieldMappings.getTableName();
		
		if(map != null) {
			if(StringUtil.isValidNew((String)map.get("archiveTable"))) {
				table = (String)map.get("archiveTable");
				map.remove("archiveTable");
			}
		}

		for (int i=0;i<fieldNames.length;i++){
			if(FieldNames.TAXPAYER_ID.equals(fieldMappings.getField(fieldNames[i]).getFieldName()))
			{
				dbFields [i] = "AES_DECRYPT("+fieldMappings.getDbField(fieldNames[i])+",'pvm@e20') AS "+fieldMappings.getDbField(fieldNames[i]);
			}else
			{
				if(fieldMappings.getField(fieldNames[i]).isPiiEnabled()){// BB-20150525-360 starts
					dbFields [i] = "AES_DECRYPT("+fieldMappings.getDbField(fieldNames[i])+",'pvm@e20') AS "+fieldMappings.getDbField(fieldNames[i]);	
				}else{// BB-20150525-360 ends
					dbFields [i] = fieldMappings.getDbField(fieldNames[i]);
				}
			}

		}

		return getWhereClauseQueryString(

										"SELECT "+StringUtil.toCommaSeparated(dbFields) +

											" FROM " + table, //P_FS_Enh_BuilderForm

										fieldMappings,

										map

										);

	}

	public static String getCountQueryString(

											FieldMappings fieldMappings,

											SequenceMap map

											){

		return getWhereClauseQueryString(

						"SELECT COUNT(*) FROM " + fieldMappings.getTableName(),

						fieldMappings,

						map

										);

		

	}

	/**
	 * Method call to manipulate DDL based queries with DDL Operation CREATE/DROP/ALTER to Table/Column(s)
	 * @author abhishek gupta	
	 * @param dbQuery
	 * @return
	 */
	public static String getDdlQuery(DBQuery dbQuery){
		try {
			StringBuffer strBuff = new StringBuffer(0); 
			DBColumn[] dbColumn = dbQuery.getDBColumns();
			
			if(CREATE.equals(dbQuery.getDDLType())) {
				strBuff.append(dbQuery.getDDLType() + SPACE + TABLE + SPACE).append(dbQuery.getTableDBName() + "(");
				if(dbColumn != null) {
					int count = 0;
					for(int i = 0; i < dbColumn.length; i++) {
						String colNme = dbColumn[i].getColDBName();
						if(i == 0) {
							strBuff.append(dbColumn[i].getColDBName() + SPACE);
							strBuff.append(dbColumn[i].getColType());
							if(StringUtil.isValid(dbColumn[i].getColTypeVal())) {
								strBuff.append("("+dbColumn[i].getColTypeVal()+")" + SPACE);
							}
							if(StringUtil.isValid(dbColumn[i].getColDefault())) {
								strBuff.append(SPACE + DEFAULT + SPACE+ dbColumn[i].getColDefault()+ SPACE);
							}
							if(dbColumn[i].isColPrimary()) {
								strBuff.append(" NOT NULL " + SPACE + PRIMARY_KEY + SPACE + AUTO_INCREMENT);
								++count;
							}
						} else {
							strBuff.append("," + dbColumn[i].getColDBName() + SPACE);
							strBuff.append(dbColumn[i].getColType());
							if(StringUtil.isValid(dbColumn[i].getColTypeVal())) {
								strBuff.append("("+dbColumn[i].getColTypeVal()+")" + SPACE);
							}
							if(StringUtil.isValid(dbColumn[i].getColDefault())) {
								strBuff.append(SPACE + DEFAULT + SPACE+ dbColumn[i].getColDefault()+ SPACE);
							}
							if(dbColumn[i].isColPrimary() && count == 0) {
								strBuff.append(SPACE + PRIMARY_KEY + SPACE + AUTO_INCREMENT);
								++count;
							}
						}
					}
					//strBuff.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8");//P_E_ENCODING
					strBuff.append(") ENGINE=").append("yes".equalsIgnoreCase("no")?"InnoDB":"MyISAM").append(" DEFAULT CHARSET=utf8");//P_E_ENCODING
					return strBuff.toString();
				} else {
					return "";
				}
			} else if(DROP.equals(dbQuery.getDDLType())) {
				strBuff.append(dbQuery.getDDLType() + SPACE + TABLE + SPACE + IF_EXISTS + SPACE).append(dbQuery.getTableDBName()); 
				return strBuff.toString();
			} else {
				strBuff.append(dbQuery.getDDLType() + SPACE + TABLE + SPACE).append(dbQuery.getTableDBName()+ SPACE);
				if(dbColumn != null) {
					for(int i = 0; i < dbColumn.length; i++) {
						if(i == 0) {
							strBuff.append(dbColumn[i].getAction() + SPACE);
							strBuff.append(dbColumn[i].getColDBName() + SPACE);
							if(!DROP.equals(dbColumn[i].getAction())){
								if(StringUtil.isValid(dbColumn[i].getColType()))
									strBuff.append(dbColumn[i].getColType());
								if(StringUtil.isValidNew(dbColumn[i].getColTypeVal())) {
									strBuff.append("("+dbColumn[i].getColTypeVal()+")" + SPACE);
								}
								//FORM_BUILDER_CHANGES Starts
								if(StringUtil.isValid(dbColumn[i].getColDefault())) {
									strBuff.append(" DEFAULT "+dbColumn[i].getColDefault()+ SPACE);
								}
								//FORM_BUILDER_CHANGES Ends
							}
						} else {
							strBuff.append(", " + dbColumn[i].getAction()+ SPACE);
							strBuff.append(dbColumn[i].getColDBName() + SPACE);
							if(!DROP.equals(dbColumn[i].getAction())){
								if(StringUtil.isValid(dbColumn[i].getColType()))
									strBuff.append(dbColumn[i].getColType());
								if(StringUtil.isValidNew(dbColumn[i].getColTypeVal())) {
									strBuff.append("("+dbColumn[i].getColTypeVal()+")" + SPACE);
								}
								//FORM_BUILDER_CHANGES Starts
								if(StringUtil.isValid(dbColumn[i].getColDefault())) {
									strBuff.append(" DEFAULT "+dbColumn[i].getColDefault()+ SPACE);
								}
								//FORM_BUILDER_CHANGES Ends
							}
						}
					}
					
					if("FRANCHISEE".equals(dbQuery.getTableDBName())) {
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("action", "create");
						parameters.put("query", strBuff.toString());
						//BaseUtils.syncData("formgeneratorsync", null, parameters);
					}
					
					return strBuff.toString();
				} else {
					return "";
				}
			}
		} catch(Exception e) {
		}
		return "";
	}
	/**
	 * Allow setting parameter set for multiple insert in table 
	 * @author abhishek gupta
	 * @param con
	 * @param stmt
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int setBatchParams(Connection con, PreparedStatement stmt, SequenceMap[] params) throws SQLException{
		try {
			int nIndex						= 1;
			for(int p=0; p < params.length; p++) {
				Iterator paramFieldsIter		= params[p].keys().iterator();
				int i							= 1;
				for (i=nIndex; paramFieldsIter.hasNext();){
					Field field					= (Field)paramFieldsIter.next();
					Object value				= params[p].get(field);
					if (value == null){
						if(field.getValidationType() != null && field.getValidationType().equals("Integer")) {
							stmt.setObject(i++,-1);
						} else {
							stmt.setString(i++,null);
						}
					} else if ("".equals(value) && field.getValidationType() != null && field.getValidationType().equals("Integer")) {
						stmt.setObject(i++,-1);
					}else if (value.getClass().isArray()){
						Object[] values			= (Object[])value;
						for (int j=0; j<values.length; j++){
							setParam(i++,con, stmt, field, values[j]);
						}
					}else{
						setParam(i++,con, stmt, field, value);
					}
				}
				stmt.addBatch();
			}
		}catch(SQLException e) {
			return -1;
		}
		return 1;
	}
	
}

