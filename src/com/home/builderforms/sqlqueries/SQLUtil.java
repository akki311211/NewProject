/*
 * 
 * ZCUB-20150728-166		9 October 2015  Mittanshi Garg					making social url connection with social signatures
 * ZCUB-20150915-176     9 oct 2015      Divanshu Verma     Adding Last Contacted Field in CM
 * 
 * */

package com.home.builderforms.sqlqueries;
import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.StringUtil;

import java.lang.StringBuffer;
import java.util.Iterator;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ColumnList;
import java.util.Map;
import java.sql.Statement;
import java.util.LinkedHashMap;

import com.appnetix.app.exception.AppException;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.sqlqueries.ResultSet;

public class SQLUtil {


    public static ResultList getResultList(SQLQuery sqlQuery, Object[] params) throws SQLException {
        String sqlQueryString = sqlQuery.getString();
        String[] columnArray = sqlQuery.getColumnList().getColumnArray();
        Field[] fieldArray = sqlQuery.getColumnList().getFieldArray();
        ResultSet result = SQLUtilHelper.getResultSet(sqlQueryString, params, MultiTenancyUtil.getTenantName());
        ArrayList list = new ArrayList();
        while (result.next()) {
            Info info = new Info();
            SQLUtilHelper.setResultInfo(info, result, fieldArray, columnArray);
            list.add(info);
        }
        return new ResultList(list, columnArray);
    }

    public static Info getDetailsInfo(FieldMappings fieldMappings, Integer id) throws RecordNotFoundException, SQLException {
        String queryString = SQLQueryGenerator.getDetailsInfoQueryString(fieldMappings);
        SequenceMap params = new SequenceMap();
        params.put(fieldMappings.getIdField()[0], id);
        ResultSet result = SQLUtilHelper.getResultSet(queryString, params, fieldMappings.getConnectionName());
        if (!result.next()) {
            throw new RecordNotFoundException(fieldMappings.getTableName() + " : " + id);
        } else {
            Field[] detailsFields = fieldMappings.getAllValidFieldsArray();
            return SQLUtilHelper.getInfo(result, detailsFields, fieldMappings.getIdField());
        }
    }

    public static ResultSet getResultSetForQuery(String queryString, FieldMappings fieldMappings, SequenceMap paramMap) throws RecordNotFoundException, SQLException {
        SequenceMap params = new SequenceMap();
        if (paramMap != null) {
            Iterator it = paramMap.keys().iterator();
            while (it.hasNext()) {
                String fieldName = (String) it.next();
                Object value = paramMap.get(fieldName);
                params.put(fieldMappings.getField(fieldName), value);
            }
        }
        ResultSet result = SQLUtilHelper.getResultSet(queryString, params, fieldMappings.getConnectionName());
        return result;
    }

    public static Info getDetailsInfo(FieldMappings fieldMappings, SequenceMap paramMap) throws RecordNotFoundException, SQLException {
        String queryString = SQLQueryGenerator.getDetailsInfoQueryString(fieldMappings, paramMap);
        ResultSet result = getResultSetForQuery(queryString, fieldMappings, paramMap);
        if (!result.next()) {
            throw new RecordNotFoundException(fieldMappings.getTableName() + " : " + paramMap);
        } else {
            Field[] detailsFields = fieldMappings.getAllValidFieldsArray();
            return SQLUtilHelper.getInfo(result, detailsFields, fieldMappings.getIdField());
        }
    }

    public static Info getSummaryInfo(FieldMappings fieldMappings, Integer[] id) throws RecordNotFoundException, SQLException {
        String queryString = SQLQueryGenerator.getSummaryInfoQueryString(fieldMappings);
        SequenceMap params = new SequenceMap();
        Field[] idFields = fieldMappings.getIdField();
        for (int i = 0; i < idFields.length; i++) {
            params.put(idFields[i], id[i]);
        }
        ResultSet result = SQLUtilHelper.getResultSet(queryString, params, fieldMappings.getConnectionName());
        if (!result.next()) {
            throw new RecordNotFoundException(fieldMappings.getTableName() + " : " + id);
        } else {
            Field[] summaryFields = fieldMappings.getSummaryValidFieldsArray();
            return SQLUtilHelper.getInfo(result, summaryFields, fieldMappings.getIdField());
        }
    }

    public static Info getSummaryInfo(FieldMappings fieldMappings, String[] fieldNames, Integer[] id) throws RecordNotFoundException, SQLException {
        SequenceMap params = new SequenceMap();
        Field[] idFields = fieldMappings.getIdField();
        for (int i = 0; i < idFields.length; i++) {
            params.put(idFields[i].getFieldName(), id[i]);
        }
        SequenceMap params2 = new SequenceMap();
        for (int i = 0; i < idFields.length; i++) {
            params2.put(idFields[i], id[i]);
        }
        String queryString = SQLQueryGenerator.getCollectionQueryString(fieldMappings, fieldNames, params);
        ResultSet result = SQLUtilHelper.getResultSet(queryString, params2, fieldMappings.getConnectionName());
        if (!result.next()) {
            throw new RecordNotFoundException(fieldMappings.getTableName() + " : " + id);
        } else {
            Field[] fields = fieldMappings.getFields(fieldNames);
            return SQLUtilHelper.getInfo(result, fields, fieldMappings.getIdField());
        }
    }

    /**
Methods to control transaction.
*/
    public static void beginTransaction(String connectionName) {
        SQLUtilHelper.beginTransaction(connectionName);
    }

    public static void rollbackTransaction(String connectionName) {
        SQLUtilHelper.rollbackTransaction(connectionName);
    }

    public static void commitTransaction(String connectionName) {
        SQLUtilHelper.commitTransaction(connectionName);
    }

    public static void createRecord(FieldMappings fieldMappings, Info info, boolean insertID) throws SQLException {
        if (fieldMappings == null) {
            return;
        }
        String queryString = SQLQueryGenerator.getCreateQueryString(fieldMappings, insertID);
        Field[] allFields = null;
        if (insertID) {
            allFields = fieldMappings.getAllFieldsArray();
        } else {
            allFields = fieldMappings.getAllFieldsArraySansID();
        }
        SequenceMap params = new SequenceMap();
        SQLException e = null;
        for (int i = 0; i < allFields.length; i++) {
            if (!allFields[i].fieldAllowedInMain()) {
                continue;
            }
            String fieldName = allFields[i].getFieldName();
            Object value = info.getObject(fieldName);
            params.put(allFields[i], value);
        }
        //System.out.println("\n\n Executing query " + queryString +" with"+(params==null?"No Params":params.toString()));
        boolean inserted = false;
        for (int i = 0; i < Constants.MAX_TRIES && !inserted; i++) {
            try {
                SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
                inserted = true;
            } catch (SQLException sqle) {
                e = sqle;
            }
        }
        if (!inserted) {
            throw e;
        }
    }

    /**
	 * Multiple insert of records for one table
	 * @author abhishek gupta
	 * @param fieldMappings
	 * @param info
	 * @param insertID
	 * @throws SQLException
	 */
    public static void createDataRecord(FieldMappings fieldMappings, Info[] info, boolean insertID) throws SQLException {
        if (fieldMappings == null) {
            return;
        }
        if (info == null || info.length == 0) {
            return;
        }
        String queryString = SQLQueryGenerator.getCreateQueryString(fieldMappings, insertID);
        Field[] allFields = null;
        if (insertID) {
            allFields = fieldMappings.getAllFieldsArray();
        } else {
            allFields = fieldMappings.getAllFieldsArraySansID();
        }
        SequenceMap[] params = new SequenceMap[info.length];
        SQLException e = null;
        for (int j = 0; j < info.length; j++) {
            params[j] = new SequenceMap();
            for (int i = 0; i < allFields.length; i++) {
                if (!allFields[i].fieldAllowedInMain()) {
                    continue;
                }
                String fieldName = allFields[i].getFieldName();
                Object value = info[j].getObject(fieldName);
                params[j].put(allFields[i], value);
            }
        }
        boolean inserted = false;
        for (int i = 0; i < Constants.MAX_TRIES && !inserted; i++) {
            try {
                SQLUtilHelper.updateMultiple(queryString, params, fieldMappings.getConnectionName());
                inserted = true;
            } catch (SQLException sqle) {
                e = sqle;
            }
        }
        if (!inserted) {
            throw e;
        }
    }

    public static Integer[] createRecordAndFetchID(FieldMappings fieldMappings, Info info) throws SQLException {
        Integer[] id = new Integer[1];
        id[0] = new Integer(-1);
        if (fieldMappings == null) {
            return null;
        }
        String queryString = SQLQueryGenerator.getCreateAndFetchQueryString(fieldMappings);
        Field[] allFields = fieldMappings.getAllFieldsArraySansID();
        SequenceMap params = new SequenceMap();
        SQLException e = null;
        for (int i = 0; i < allFields.length; i++) {
            if (!allFields[i].fieldAllowedInMain()) {
                continue;
            }
            String fieldName = allFields[i].getFieldName();
            Object value = info.getObject(fieldName);
            params.put(allFields[i], value);
        }
        boolean inserted = false;
        for (int i = 0; i < Constants.MAX_TRIES && !inserted; i++) {
            try {
                id = SQLUtilHelper.createAndFetchID(queryString, params, fieldMappings.getConnectionName());
                inserted = true;
            } catch (SQLException sqle) {
                e = sqle;
            }
        }
        if (!inserted) {
            throw e;
        }
        info.setID(id);
        return id;
    }

    public static void createRecord(FieldMappings fieldMappings, Info info) throws SQLException {
        createRecord(fieldMappings, info, false);
    }

    public static void replaceRecord(FieldMappings fieldMappings, Info info) throws SQLException {
        String queryString = SQLQueryGenerator.getReplaceQueryString(fieldMappings);
        SequenceMap params = new SequenceMap();
        Field[] allFields = fieldMappings.getAllFieldsArraySansID();
        for (int i = 0; i < allFields.length; i++) {
            if (!allFields[i].fieldAllowedInMain()) {
                continue;
            }
            params.put(allFields[i], info.getObject(allFields[i].getFieldName()));
        }
        Field[] idFields = fieldMappings.getIdField();
        Integer[] id = info.getIDs();
        for (int i = 0; i < idFields.length; i++) {
            params.put(idFields[i], id[i]);
        }
        SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
    }

    public static void modifyRecord(FieldMappings fieldMappings, Info info) throws SQLException {
        SequenceMap params = new SequenceMap();
        ArrayList dbFieldList = new ArrayList();
        Iterator keyIter = info.getKeySetIterator();
        int i = 0;
        while (keyIter.hasNext()) {
            String fieldName = (String) keyIter.next();
            if (info.isIdField(fieldName)) continue;
            Field field = fieldMappings.getField(fieldName);
            if (field == null) {
                continue;
            }
            dbFieldList.add(field.getDbField());
            params.put(field, info.getObject(fieldName));
        }
        Field[] idFields = fieldMappings.getIdField();
        Object[] idObject = info.getIDObject();
        for (int loop = 0; loop < idFields.length; loop++) {
            params.put(idFields[loop], idObject[loop]);
        }
        String queryString = SQLQueryGenerator.getModifyQueryString((String[]) dbFieldList.toArray(new String[0]), fieldMappings);
        SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
    }

    public static void modifyRecord(FieldMappings fieldMappings, Info info, SequenceMap paramMap) throws SQLException {
        SequenceMap params = new SequenceMap();
        ArrayList dbFieldsList = new ArrayList();
        Iterator keyIter = info.getKeySetIterator();
        int i = 0;
        while (keyIter.hasNext()) {
            String fieldName = (String) keyIter.next();
            if (info.isIdField(fieldName)) continue;
            Field field = fieldMappings.getField(fieldName);
            if (field == null) {
                continue;
            }
            dbFieldsList.add(field.getDbField());
            params.put(field, info.get(fieldName));
        }
        Iterator it = paramMap.keys().iterator();
        SequenceMap whereMap = new SequenceMap();
        while (it.hasNext()) {
            String fieldName = (String) it.next();
            Field field = fieldMappings.getField(fieldName);
            if (field == null) {
                continue;
            }
            whereMap.put(field, paramMap.get(fieldName));
        }
        String queryString = SQLQueryGenerator.getModifyQueryString(CollectionUtil.getStringArray(dbFieldsList), fieldMappings, paramMap);
        SQLUtilHelper.update(queryString, params, whereMap, fieldMappings.getConnectionName());
    }

    public static void deleteRecord(FieldMappings fieldMappings, Integer[] id) throws SQLException {
        SequenceMap params = new SequenceMap();
        Field[] idFields = fieldMappings.getIdField();
        for (int i = 0; i < idFields.length; i++) {
            params.put(idFields[i], id[i]);
        }
        String queryString = SQLQueryGenerator.getDeleteQueryString(fieldMappings);
        SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
    }

    public static void deleteRecords(FieldMappings fieldMappings, Integer[] ids) throws SQLException {
        if (ids == null || ids.length == 0) {
            return;
        }
        String queryString = SQLQueryGenerator.getMultipleDeleteQueryString(fieldMappings, ids.length);
        SQLUtilHelper.update(queryString, ids, fieldMappings.getConnectionName());
    }

    public static void deleteRecords(FieldMappings fieldMappings, SequenceMap paramMap) throws SQLException {
        SequenceMap params = new SequenceMap();
        Iterator it = paramMap.keys().iterator();
        while (it.hasNext()) {
            String fieldName = (String) it.next();
            Object value = paramMap.get(fieldName);
            params.put(fieldMappings.getField(fieldName), value);
        }
        String queryString = SQLQueryGenerator.getMultipleDeleteQueryString(fieldMappings, paramMap);
        SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
    }

    public static void deleteRecord(FieldMappings fieldMappings, Info info) throws SQLException {
        SequenceMap params = new SequenceMap();
        Iterator itField = info.getKeySetIterator();
        while (itField != null && itField.hasNext()) {
            String fieldName = (String) itField.next();
            params.put(fieldMappings.getField(fieldName), info.get(fieldName));
        }
        String queryString = SQLQueryGenerator.getDeleteQueryString(fieldMappings, info);
        SQLUtilHelper.update(queryString, params, fieldMappings.getConnectionName());
    }

    public static SequenceMap getSummaryCollection(FieldMappings fieldMappings) throws RecordNotFoundException, SQLException {
        return getSummaryCollection(fieldMappings, null);
    }

    public static SequenceMap getSummaryCollection(FieldMappings fieldMappings, SequenceMap paramMap) throws RecordNotFoundException, SQLException {
        return getCollection(fieldMappings, fieldMappings.getValidSummaryFieldNames(), paramMap);
    }

    public static int getCount(FieldMappings fieldMappings, SequenceMap paramMap) throws RecordNotFoundException, SQLException {
        String queryString = SQLQueryGenerator.getCountQueryString(fieldMappings, paramMap);
        ResultSet result = getResultSetForQuery(queryString, fieldMappings, paramMap);
        if (result == null) {
            return IntConstants.DEFAULT_INT;
        }
        if (result.next()) {
            return result.getInteger(1).intValue();
        }
        return IntConstants.DEFAULT_INT;
    }
    /**
     * This Method returns map of all table data according to limit of pagging parameters
     * P_Enh_FormBuilder_Tabular_Section
     * @author Akash Kumar
     * @param fieldMappings
     * @param fieldNamesIn
     * @param paramMap
     * @param pageSize
     * @param pageId
     * @return
     * @throws RecordNotFoundException
     * @throws SQLException
     */
    public static SequenceMap getCollection(FieldMappings fieldMappings, String[] fieldNamesIn, SequenceMap paramMap,String pageSize,String pageId,String orderBy) throws RecordNotFoundException, SQLException {
        if (fieldNamesIn == null) {
            return null;
        }
        StringBuffer orderByString=new StringBuffer();
        ArrayList fieldNamesInList = new ArrayList();
        boolean flag = false;
        for (int i = 0; i < fieldNamesIn.length; i++) {
            if (!fieldMappings.isIdField(fieldNamesIn[i])) {
                fieldNamesInList.add(fieldNamesIn[i]);
            }
        }
        String[] idFieldNames = fieldMappings.getIdFieldNames();
        for (int i = 0; i < idFieldNames.length; i++) {
            fieldNamesInList.add(idFieldNames[i]);
        }
        fieldNamesIn = CollectionUtil.getStringArray(fieldNamesInList);
        StringBuffer limitString=new  StringBuffer();
        limitString.append(" ");
        if (pageSize != null) {
			int intPageSize = Integer.parseInt(pageSize);
			int page = 0;

			try {
				page = Integer.parseInt(pageId);
			} catch (NumberFormatException e) {
			}

			if (page > 1) {
				int limit = intPageSize * (Integer.parseInt(pageId) - 1);
				limitString.append(" LIMIT ").append(
						limit).append(" ,  " + intPageSize);
			} else if (page == 1) {
				limitString.append(" LIMIT 0")
						.append(" ,  " + intPageSize);
			}
		}
        
        if(StringUtil.isValidNew(orderBy)){
        	orderByString.append(" ORDER BY ").append(orderBy);
        }
        String queryString = SQLQueryGenerator.getCollectionQueryString(fieldMappings, fieldNamesIn, paramMap)+orderByString+" "+limitString;
        ResultSet result = getResultSetForQuery(queryString, fieldMappings, paramMap);
        if (result == null) {
            return null;
        }
        Field[] fields = new Field[fieldNamesIn.length];
        for (int i = 0; i < fieldNamesIn.length; i++) {
            fields[i] = fieldMappings.getField(fieldNamesIn[i]);
        }
        Field[] idField = fieldMappings.getIdField();
        SequenceMap map = SQLUtilHelper.getSequenceMap(result, fields, idField);
        return map;
    }
    public static SequenceMap getCollection(FieldMappings fieldMappings, String[] fieldNamesIn, SequenceMap paramMap) throws RecordNotFoundException, SQLException {
        if (fieldNamesIn == null) {
            return null;
        }
        ArrayList fieldNamesInList = new ArrayList();
        boolean flag = false;
        for (int i = 0; i < fieldNamesIn.length; i++) {
            if (!fieldMappings.isIdField(fieldNamesIn[i])) {
                fieldNamesInList.add(fieldNamesIn[i]);
            }
        }
        String[] idFieldNames = fieldMappings.getIdFieldNames();
        for (int i = 0; i < idFieldNames.length; i++) {
            fieldNamesInList.add(idFieldNames[i]);
        }
        fieldNamesIn = CollectionUtil.getStringArray(fieldNamesInList);
        String queryString = SQLQueryGenerator.getCollectionQueryString(fieldMappings, fieldNamesIn, paramMap);
        ResultSet result = getResultSetForQuery(queryString, fieldMappings, paramMap);
        if (result == null) {
            return null;
        }
        Field[] fields = new Field[fieldNamesIn.length];
        for (int i = 0; i < fieldNamesIn.length; i++) {
            fields[i] = fieldMappings.getField(fieldNamesIn[i]);
        }
        Field[] idField = fieldMappings.getIdField();
        SequenceMap map = SQLUtilHelper.getSequenceMap(result, fields, idField);
        return map;
    }

    public static SequenceMap getCollection(FieldMappings fieldMappings, String[] fieldNamesIn, SequenceMap paramMap, String orderBy) throws RecordNotFoundException, SQLException {
        if (fieldNamesIn == null) {
            return null;
        }
        ArrayList fieldNamesInList = new ArrayList();
        boolean flag = false;
        for (int i = 0; i < fieldNamesIn.length; i++) {
            if (!fieldMappings.isIdField(fieldNamesIn[i])) {
                fieldNamesInList.add(fieldNamesIn[i]);
            }
        }
        String[] idFieldNames = fieldMappings.getIdFieldNames();
        for (int i = 0; i < idFieldNames.length; i++) {
            fieldNamesInList.add(idFieldNames[i]);
        }
        fieldNamesIn = CollectionUtil.getStringArray(fieldNamesInList);
        String queryString = SQLQueryGenerator.getCollectionQueryString(fieldMappings, fieldNamesIn, paramMap);
        if(StringUtil.isValidNew(orderBy)){
        	queryString += " ORDER BY "+orderBy;
        }
        ResultSet result = getResultSetForQuery(queryString, fieldMappings, paramMap);
        if (result == null) {
            return null;
        }
        Field[] fields = new Field[fieldNamesIn.length];
        for (int i = 0; i < fieldNamesIn.length; i++) {
            fields[i] = fieldMappings.getField(fieldNamesIn[i]);
        }
        Field[] idField = fieldMappings.getIdField();
        SequenceMap map = SQLUtilHelper.getSequenceMap(result, fields, idField);
        return map;
    }

    public static void executeProcedure(String procedureName, Object[] params, String connectionName) {
        Connection con = null;
        CallableStatement cstmt = null;
        DBConnectionManager conMgr = DBConnectionManager.getInstance();
        try {
            con = conMgr.getConnection(connectionName);
            String callProc = "{Call " + procedureName + "(" + StringUtil.toNtimes("?", params.length) + ")}";
            cstmt = con.prepareCall(callProc);
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null) {
                }
                cstmt.setObject(i + 1, params[i]);
            }
            cstmt.execute();
        } catch (Exception e) {
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                    cstmt = null;
                } catch (Exception e) {
                }
            }
            conMgr.freeConnection(connectionName, con);
        }
    }

    public static void executeProcedure(String procedureName, Object[] params) {
        executeProcedure(procedureName, params, MultiTenancyUtil.getTenantName());
    }

    public static void createTables(String tableName, String columnName[], String DataType[], String signedOrUnsigned[], String nullOrNot[], String DefaultValue[], String primaryKey, String autoIncrementColumn) {
        StringBuilder createQuery = new StringBuilder();
        String query = "CRETE TABLE" + tableName + "(";
        for (int i = 0; i < columnName.length; i++) {
            if (StringUtil.isValid(columnName[i])) {
                createQuery.append(columnName[i]);
            }
            if (StringUtil.isValid(DataType[i])) {
                createQuery.append(DataType[i]);
            }
            if (StringUtil.isValid(signedOrUnsigned[i])) {
                createQuery.append(signedOrUnsigned[i]);
            }
            if (StringUtil.isValid(nullOrNot[i])) {
                createQuery.append(nullOrNot[i]);
            } else {
                createQuery.append("null");
            }
            if (StringUtil.isValid(DefaultValue[i])) {
                createQuery.append(DefaultValue[i]);
            }
            if (StringUtil.isValid(autoIncrementColumn)) {
                createQuery.append("AUTO_INCREMENT");
            }
        }
    }

    public static void insertTableData(Info tabInfo, String tableName) {
        StringBuffer query = new StringBuffer("INSERT INTO ");
        String key = null;
        String value = null;
        boolean isFirst = true;
        StringBuffer columns = new StringBuffer();
        StringBuffer values = new StringBuffer();
        String parameters[] = null;
        Iterator tabIterator = null;
        int parameterCount = 0;
        if (StringUtil.isValid(tableName)) {
            query.append(tableName + " (");
        }
        if (tabInfo != null && tabInfo.size() > 0) {
            parameters = new String[tabInfo.size()];
            tabIterator = tabInfo.getKeySetIterator();
            while (tabIterator.hasNext()) {
                key = (String) tabIterator.next();
                value = (String) tabInfo.get(key);
                if (isFirst) {
                    columns.append(key);
                    values.append("?");
                    parameters[parameterCount] = value;
                    isFirst = false;
                } else {
                    columns.append("," + key);
                    values.append(",?");
                    parameters[parameterCount] = value;
                }
                parameterCount++;
            }
            query.append(columns.toString() + ") ");
            query.append("VALUES(" + values.toString() + ")");
            try {
                QueryUtil.executeInsert(query.toString(), parameters);
            } catch (Exception ae) {
            }
        }
    }

    public static void deleteTableData(String tableName, Info conditionInfo) {
        Iterator tabIterator = null;
        String columnName = null;
        String value = null;
        String parameters[] = null;
        StringBuffer query = null;
        boolean isFirst = true;
        int parameterCount = 0;
        if (conditionInfo != null && conditionInfo.size() > 0) {
            query = new StringBuffer("DELETE FROM ");
            query.append(tableName);
            query.append(" WHERE ");
            parameters = new String[conditionInfo.size()];
            tabIterator = conditionInfo.getKeySetIterator();
            while (tabIterator.hasNext()) {
                columnName = (String) tabIterator.next();
                value = (String) conditionInfo.get(columnName);
                if (isFirst) {
                    query.append(columnName).append(" = ?");
                    parameters[parameterCount] = value;
                    isFirst = false;
                } else {
                    query.append(" AND ").append(columnName).append(" = ?");
                    parameters[parameterCount] = value;
                }
                parameterCount++;
            }
            try {
                QueryUtil.update(query.toString(), parameters);
            } catch (AppException ae) {
            }
        }
    }

    public static void updateTableData(String tableName, String conditionColumn, String conditionValue, Info updateInfo) {
        Iterator tabIterator = null;
        String columnName = null;
        String value = null;
        String parameters[] = null;
        StringBuffer query = null;
        boolean isFirst = true;
        int parameterCount = 0;
        if (updateInfo != null && updateInfo.size() > 0) {
            query = new StringBuffer("UPDATE ");
            query.append(tableName);
            query.append(" SET ");
            parameters = new String[updateInfo.size() + 1];
            tabIterator = updateInfo.getKeySetIterator();
            while (tabIterator.hasNext()) {
                columnName = (String) tabIterator.next();
                value = (String) updateInfo.get(columnName);
                if (isFirst) {
                    query.append(columnName).append(" = ?");
                    parameters[parameterCount] = value;
                    isFirst = false;
                } else {
                    query.append(" , ").append(columnName).append(" = ?");
                    parameters[parameterCount] = value;
                }
                parameterCount++;
            }
            query.append(" WHERE ").append(conditionColumn).append(" = ?");
            parameters[parameterCount] = conditionValue;
            try {
                QueryUtil.update(query.toString(), parameters);
            } catch (AppException ae) {
            }
        }
    }

    /**
		     * Topper_campaign_mail<br/>
		     * Method to get multiple columns from a table.
		     * @author Vivek Maurya
		     * @date 06Sep2012
		     * @param columnArr array of required columns
		     * @param tableName
		     * @param keyColumn
		     * @param key
		     * @return
		     */
    public static Map<String, String> getMultipleColumns(String[] columnArr, String tableName, String keyColumn, String key) {
        return getMultipleColumns(columnArr, tableName, keyColumn, key, false);
    }

    /**
		     * 
		     * @param columnArr
		     * @param tableName
		     * @param keyColumn
		     * @param key
		     * @param columnArrAsKey
		     * @return
		     */
    public static Map<String, String> getMultipleColumns(String[] columnArr, String tableName, String keyColumn, String key, boolean columnArrAsKey) {
        ResultSet result = null;
        try {
            StringBuffer sbQuery = new StringBuffer("SELECT ");
            sbQuery.append(columnArr[0]);
            final int arrlen = columnArr.length;
            for (int i = 1; i < arrlen; i++) {
                sbQuery.append(",").append(columnArr[i]);
            }
            sbQuery.append(" FROM ").append(tableName).append(" WHERE ");
            sbQuery.append(keyColumn).append("= ?");
            result = QueryUtil.getResult(sbQuery.toString(), new String[] { key });
            if (result.next()) {
                Map<String, String> map = BaseUtils.getNewHashMapWithKeyValueType();
                for (int i = 0; i < arrlen; i++) {
                    map.put(columnArrAsKey ? columnArr[i] : String.valueOf(i), result.getString(columnArr[i]));
                }
                return map;
            }
        } catch (Exception e) {
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
            columnArr = null;
        }
        return Collections.<String, String>emptyMap();
    }

    /**
		     * TDG-MOBILE-20101221
		     *
		     * @author Vivek Maurya
		     * @Date 15Apr2011
		     * @desc Method is used to retrieve value of a column from table
		     * @param tableName
		     *            name of table
		     * @param columnName
		     *            name of column
		     * @param keyColumn
		     *            primary key of table or any other column used in where clause
		     * @param key
		     *            value of keyColumn
		     * @return String value of column
		     */
    public static String getColumnValue(String tableName, String columnName, String keyColumn, String key) {
        return getColumnValue(tableName, columnName, keyColumn, key, null);
    }

    public static String getColumnValue(String tableName, String columnName, String keyColumn, String key, String inClause) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        String returnValue = FieldNames.EMPTY_STRING;
        if (columnName.trim().toUpperCase().equals(keyColumn.trim().toUpperCase())) {
            return key;
        }
        try {
            String[] paramArray = null;
            query.append("SELECT ").append(columnName).append(" FROM ").append(tableName);
            if (StringUtil.isValid(inClause)) {
                query.append(" WHERE ").append(keyColumn).append(" IN (" + key + ")");
            } else if (StringUtil.isValidNew(keyColumn)) {
                paramArray = new String[] { key };
                query.append(" WHERE ").append(keyColumn).append("=?");
            }
            result = QueryUtil.getResult(query.toString(), paramArray);
            if (result != null && result.next()) {
                returnValue = result.getString(columnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
        }
        return returnValue;
    }

    public static Info getColumnValueInfo(String tableName, String columnName1, String columnName2, String keyColumn, String key) {
        return getColumnValueInfo(tableName, columnName1, columnName2, keyColumn, key, null);
    }

    public static Info getColumnValueInfo(String tableName, String columnName1, String columnName2, String keyColumn, String key, String columnForOrder) {
        return getColumnValueInfo(tableName, columnName1, columnName2, keyColumn, key, columnForOrder, null);
    }

    public static Info getColumnValueInfo(String tableName, String columnName1, String columnName2, String keyColumn, String key, String columnForOrder, String sortOrder) {
        return getColumnValueInfo(tableName, columnName1, columnName2, keyColumn, key, columnForOrder, sortOrder, false);
    }

    public static Info getColumnValueInfo(String tableName, String columnName1, String columnName2, String keyColumn, String key, String columnForOrder, String sortOrder, boolean applyLanguageUtil) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        Info info = new Info();
        try {
            query.append("SELECT ").append(columnName1 + "," + columnName2).append(" FROM ").append(tableName);
            if (StringUtil.isValid(keyColumn)) {
                query.append(" WHERE ").append(keyColumn).append(" IN(" + key + ")");
                if (StringUtil.isValid(columnForOrder)) {
                    query.append(" ORDER BY ").append(columnForOrder);
                }
                if (StringUtil.isValid(sortOrder)) {
                    query.append("  ").append(sortOrder);
                }
                result = QueryUtil.getResult(query.toString(), null);
            } else {
                if (StringUtil.isValid(columnForOrder)) {
                    query.append(" ORDER BY ").append(columnForOrder);
                }
                result = QueryUtil.getResult(query.toString(), null);
            }
            while (result.next()) {
                info.set(result.getString(columnName1), applyLanguageUtil ? LanguageUtil.getString(result.getString(columnName2)) : result.getString(columnName2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return info;
    }
    
    
    public static Map<String,String> getColumnValueMap(String tableName, String columnName1, String columnName2, String keyColumn, String key) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        Map tableMap = BaseUtils.getNewHashMapWithKeyValueType();
        try {
            query.append("SELECT ").append(columnName1 + "," + columnName2).append(" FROM ").append(tableName);
            if (StringUtil.isValid(keyColumn)) 
            {
                query.append(" WHERE ").append(keyColumn).append(" IN(" + key + ")");
            } 
            result = QueryUtil.getResult(query.toString(), null);
            while (result.next()) 
            {
            	tableMap.put(result.getString(columnName1),result.getString(columnName2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return tableMap;
    }
    

    public static Info getInfoFromQuery(String keyColumn, String valueColumn, String query) {
        return getInfoFromQuery(keyColumn, valueColumn, query, false);
    }

    public static Info getInfoFromQuery(String keyColumn, String valueColumn, String query, boolean applyLanguageUtil) {
        Info dataInfo = new Info();
        if (StringUtil.isValid(query)) {
            com.home.builderforms.sqlqueries.ResultSet result = null;
            try {
                result = QueryUtil.getResult(query, null);
                if (result != null) {
                    while (result.next()) {
                        dataInfo.set(result.getString(keyColumn), applyLanguageUtil ? LanguageUtil.getString(result.getString(valueColumn)) : result.getString(valueColumn));
                    }
                }
            } catch (Exception e) {
            } finally {
                QueryUtil.releaseResultSet(result);
            }
        }
        return dataInfo;
    }

    public static boolean isPrimaryKeyExist(String tableName, String primaryKeyColumnName, String primaryKey) {
        boolean isPrimaryKeyExist = false;
        com.home.builderforms.sqlqueries.ResultSet result = null;
        String query = null;
        try {
            query = " SELECT " + primaryKeyColumnName + " FROM " + tableName + " WHERE " + primaryKeyColumnName + " = '" + primaryKey + "'";
            result = QueryUtil.getResult(query.toString(), null);
            if (result != null && result.next()) {
                isPrimaryKeyExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPrimaryKeyExist;
    }

    public static String getItemNameById(String tableName, String requiredColumnName, String givenColumnName, String givenColumnValue) {
        String itemName = "";
        Connection con = null;
        Statement stmt = null;
        java.sql.ResultSet rs = null;
        String query = "SELECT " + requiredColumnName + " FROM " + tableName + " WHERE " + givenColumnName + "='" + givenColumnValue + "'";
        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                itemName = rs.getString(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (con != null) DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
            }
        }
        if (StringUtil.isValid(itemName)) {
            itemName = itemName.replaceAll("\\b\\s{2,}\\b", " ");
        }
        return itemName;
    }

    public static String getQueryResult(String query, String requiredColumn) {
        ResultSet result = null;
        try {
            result = QueryUtil.getResult(query, null);
            if (result != null && result.next()) {
                return result.getString(requiredColumn);
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
        }
        return null;
    }

    public static Info getSingleRowInfo(String tableName, String[] columns, String[] infoKeys, String keyColumn, String keyColumnValue) {
        Info dataInfo = new Info();
        if (BaseUtils.checkArray(columns) && BaseUtils.checkArray(infoKeys) && columns.length == infoKeys.length) {
            StringBuffer sbQuery = new StringBuffer("SELECT ");
            sbQuery.append(columns[0]);
            final int arrlen = columns.length;
            for (int i = 1; i < arrlen; i++) {
                sbQuery.append(",").append(columns[i]);
            }
            sbQuery.append(" FROM ").append(tableName).append(" WHERE ");
            sbQuery.append(keyColumn).append("=?");
            com.home.builderforms.sqlqueries.ResultSet rs = null;
            String[] paramArray = { keyColumnValue };
            try {
                rs = QueryUtil.getResult(sbQuery.toString(), paramArray);
                if (rs != null && rs.next()) {
                    for (int i = 0; i < arrlen; i++) {
                        dataInfo.set(infoKeys[i], rs.getString(columns[i]));
                    }
                }
            } catch (Exception e) {
            } finally {
                if (rs != null) {
                    rs.release();
                    rs = null;
                }
                columns = null;
                infoKeys = null;
                sbQuery = null;
                paramArray = null;
            }
        } else {
            throw new IllegalArgumentException("columns and infoKeys must not be null.\n Length of both arrays must be same");
        }
        return dataInfo;
    }

    public static Map<String, String> getSingleRowMap(String tableName, String[] columns, String[] infoKeys, String keyColumn, String keyColumnValue, boolean isOrdered) {
        Map<String, String> dataMap = null;
        if (isOrdered) {
            dataMap = BaseUtils.getNewLinkedHashMapWithKeyValueType();
        } else {
            dataMap = BaseUtils.getNewHashMapWithKeyValueType();
        }
        if (BaseUtils.checkArray(columns) && BaseUtils.checkArray(infoKeys) && columns.length == infoKeys.length) {
            StringBuffer sbQuery = new StringBuffer("SELECT ");
            sbQuery.append(columns[0]);
            final int arrlen = columns.length;
            for (int i = 1; i < arrlen; i++) {
                sbQuery.append(",").append(columns[i]);
            }
            sbQuery.append(" FROM ").append(tableName).append(" WHERE ");
            sbQuery.append(keyColumn).append("=?");
            com.home.builderforms.sqlqueries.ResultSet rs = null;
            String[] paramArray = { keyColumnValue };
            try {
                rs = QueryUtil.getResult(sbQuery.toString(), paramArray);
                if (rs != null && rs.next()) {
                    for (int i = 0; i < arrlen; i++) {
                        dataMap.put(infoKeys[i], StringUtil.isValid(rs.getString(columns[i])) ? rs.getString(columns[i]) : FieldNames.EMPTY_STRING);
                    }
                }
            } catch (Exception e) {
            } finally {
                QueryUtil.releaseResultSet(rs);
                columns = null;
                infoKeys = null;
                sbQuery = null;
                paramArray = null;
            }
        } else {
            throw new IllegalArgumentException("columns and infoKeys must not be null.\n Length of both arrays must be same");
        }
        return dataMap;
    }

    public static void updateTableValue(String tableName, String updateCol, String updateVal, String validateCol, String validateVal) {
        updateTableValue(tableName, updateCol, updateVal, validateCol, validateVal, null, null);
    }

    public static void updateTableValue(String tableName, String updateCol, String updateVal, String validateCol, String validateVal, String valiDateCol, String valiDateVal) {
        StringBuffer query = new StringBuffer((new StringBuilder()).append("UPDATE ").append(tableName).append(" SET ").append(updateCol).append("=?").toString());
        query.append((new StringBuilder()).append(" WHERE ").append(validateCol).append("='").append(validateVal).append("' ").toString());
        if (StringUtil.isValid(valiDateCol)) {
            query.append(" ").append((new StringBuilder()).append("AND ").append(valiDateCol).append("='").append(valiDateVal).append("'").toString());
        }
        int result;
        try {
            result = QueryUtil.update(query.toString(), new String[] { updateVal });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //ENH_INT_DEL_MULTIPLE_DOCS Starts
    public static void updateTableValueWithInClause(String tableName, String updateCol, String updateVal, String validateCol, String validateVal) {
        
    	StringBuffer query = new StringBuffer((new StringBuilder()).append("UPDATE ").append(tableName).append(" SET ").append(updateCol).append("=?").toString());
        query.append((new StringBuilder()).append(" WHERE ").append(validateCol).append(" IN (").append(validateVal).append(")").toString());
        
        int result;
        try {
            result = QueryUtil.update(query.toString(), new String[] { updateVal });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //ENH_INT_DEL_MULTIPLE_DOCS Ends 
   
    public static void updateTableColumnWithCond(String tableName, String colToset, String colVal, String colFrom, String colFrmVal) {
        StringBuffer query = new StringBuffer("UPDATE " + tableName + " SET " + colToset + "='" + colVal + "' ");
        if (colFrom != null) {
            query.append("WHERE " + colFrom + "='" + colFrmVal + "' ");
        }
        try {
            QueryUtil.update(query.toString(), new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
 * @author Vivek Maurya
 * @date 01Mar2013
 */
    public static boolean isDataAvailable(String tableName, String whereClause) {
        boolean dataAvailable = false;
        String query = "SELECT * FROM " + tableName;
        if (StringUtil.isValid(whereClause)) {
            query += " WHERE " + whereClause;
        }
        com.home.builderforms.sqlqueries.ResultSet result = null;
        try {
            result = QueryUtil.getResult(query, null);
            if (result != null && result.next()) {
                dataAvailable = true;
            }
        } catch (Exception e) {
        } finally {
            QueryUtil.releaseResultSet(result);
        }
        return dataAvailable;
    }

    /**
 * @author Vivek Maurya
 * @date 01Mar2013
 * @return SequenceMap from query result.
 */
    public static SequenceMap<String, String> getSequenceMapFromQuery(String keyColumn, String valueColumn, String query) {
        SequenceMap dataMap = new SequenceMap<String, String>();
        if (StringUtil.isValid(query)) {
            com.home.builderforms.sqlqueries.ResultSet result = null;
            try {
                result = QueryUtil.getResult(query, null);
                if (result != null) {
                    while (result.next()) {
                        dataMap.put(result.getString(keyColumn), result.getString(valueColumn));
                    }
                }
            } catch (Exception e) {
            } finally {
                QueryUtil.releaseResultSet(result);
            }
        }
        return dataMap;
    }

    /**
 * @author Naman Jain
 * @param sMap
 * @param id
 * @param value
 * @param tablename
 * @param wherefields
 * @param wherevalues
 * @param orderBy
 * @return
 */
    public static Map<String, String> getDataMap(Map<String, String> sMap, String id, String value, String tablename, String[] wherefields, String[] wherevalues, String orderBy) {
        return getDataMap(sMap, id, value, tablename, wherefields, wherevalues, orderBy, null);
    }

    public static Map<String, String> getDataMap(Map<String, String> sMap, String id, String value, String tablename, String[] wherefields, String[] wherevalues, String orderBy, String query) {
        if (sMap == null) {
            sMap = new LinkedHashMap<String, String>();
        }
        if (!StringUtil.isValid(query)) {
            StringBuilder tempQuery = new StringBuilder("SELECT ");
            tempQuery.append(id).append(", ").append(value);
            tempQuery.append(" FROM ").append(tablename);
            StringBuffer whereQuery = new StringBuffer();
            if (wherefields != null && wherefields.length > 0) {
                boolean whereNeeded = true;
                int arrayLen = wherefields.length;
                for (int i = 0; i < arrayLen; i++) {
                    if (StringUtil.isValid(wherevalues[i])) {
                        whereQuery.append(whereNeeded ? " WHERE " : " AND ");
                        whereNeeded = false;
                        whereQuery.append(wherefields[i]);
                        if (wherevalues[i].indexOf(',') != -1) {
                            whereQuery.append(" IN (");
                            whereQuery.append(wherevalues[i] + " )");
                        } else {
                            whereQuery.append(" ='");
                            whereQuery.append(wherevalues[i] + "'");
                        }
                    }
                }
            }
            tempQuery = tempQuery.append(whereQuery);
            tempQuery = tempQuery.append(" ORDER BY ");
            tempQuery.append(orderBy);
            query = tempQuery.toString();
        }
        com.home.builderforms.sqlqueries.ResultSet result = null;
        try {
            result = QueryUtil.getResult(query, null);
            if (result != null) {
                while (result.next()) {
                    sMap.put(result.getString(1), result.getString(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            QueryUtil.releaseResultSet(result);
        }
        return sMap;
    }

    /**
     * Method is used to update a table.<br/>
     * 
     * @author Vivek Maurya
     * @date 06Aug2012
     * @param tableName : name of the table.
     * @param columnArray : String array of column names, must be non-null.
     * @param valueArray : String array of column values, must be non-null.<br/>
     * <span class=\"bText12b\">Note:</span><br/>
     * <span class=\"bText12b\">1.</span> Length of column array must be equal to length of value array.<br/>
     * <span class=\"bText12b\">2.</span> Order of column names in column array must be same as order<br/>
     * of values in value array.
     * @param isANDSeparated : true if AND separated where condition is needed,<br/>
     * false for OR separated.
     * @param whereConds : String arrary of where conditions.
     * @param isWhereClause : true, if passed whereClause is needed in the query.
     * @param whereClause : where clause passed to method as argument.
     * @throws IllegalArgumentException<br/>
     * If table name is null,<br/>
     * column and value arrays are null,<br/>
     * columnArray.length != valueArray.length
     * 
     */
    public static void updateTableData(String tableName, String[] columnArray, String[] valueArray, boolean isANDSeparated, String[] whereConds, boolean isWhereClause, String whereClause) {
        if (StringUtil.isValid(tableName) && columnArray != null && valueArray != null && (columnArray.length == valueArray.length)) {
            StringBuffer updateQuery = null;
            try {
                updateQuery = new StringBuffer();
                updateQuery.append("UPDATE ").append(tableName);
                updateQuery.append(" SET ");
                int columnArrLen = columnArray.length;
                for (int i = 0; i < columnArrLen; i++) {
                    if (i != 0) {
                        updateQuery.append(",");
                    }
                    updateQuery.append(columnArray[i]).append("=? ");
                }
                if (isWhereClause) {
                    updateQuery.append("WHERE ").append(whereClause);
                } else {
                    updateQuery.append("WHERE ");
                    if (isANDSeparated) {
                        updateQuery.append(StringUtil.getSeparatedString(whereConds, " AND "));
                    } else {
                        updateQuery.append(StringUtil.getSeparatedString(whereConds, " OR "));
                    }
                }
                QueryUtil.update(updateQuery.toString(), valueArray);
            } catch (Exception e) {
            } finally {
                updateQuery = null;
                tableName = null;
                columnArray = null;
                valueArray = null;
                whereClause = null;
            }
        } else {
            throw new IllegalArgumentException("Exception=>Table name, columnArray, valueArray must be non-null !!");
        }
    }

    /**
     * @author Divya Mishra
     * @param tablename,required column ,validatingColumn ,validatingColumnValue
     * @description This Method returns all the cases of LIKE in the query .
     * @comment used P_BUG_Supplies_category_modify
     */
    public static String getColumnFromTableUsingLikeAllCases(String tableName, String requiredColumn, String validatingColumn, String validatingColumnValue) {
        String returncolumnValue = "";
        if (StringUtil.isValid(tableName) && StringUtil.isValid(requiredColumn) && StringUtil.isValid(validatingColumn) && StringUtil.isValid(validatingColumnValue)) {
            StringBuffer query = new StringBuffer();
            ResultSet result = null;
            try {
                query.append("SELECT  ");
                query.append(requiredColumn);
                query.append("  FROM  ");
                query.append(tableName);
                query.append("  WHERE  ");
                query.append(validatingColumn);
                query.append(" LIKE ");
                if (validatingColumn != null) {
                    validatingColumnValue = validatingColumnValue.trim();
                }
                query.append(" '%,");
                query.append(validatingColumnValue);
                query.append(",%'");
                query.append(" OR ");
                query.append(validatingColumn);
                query.append(" LIKE '");
                query.append(validatingColumnValue);
                query.append(",%'");
                query.append(" OR ");
                query.append(validatingColumn);
                query.append(" LIKE '%,");
                query.append(validatingColumnValue);
                query.append("'");
                result = QueryUtil.getResult(query.toString(), null);
                while (result.next()) {
                    if (StringUtil.isValid(returncolumnValue)) returncolumnValue = returncolumnValue + "," + result.getString(requiredColumn); else returncolumnValue = result.getString(requiredColumn);
                }
            } catch (Exception e) {
            } finally {
                if (result != null) {
                    result = null;
                }
            }
        }
        return returncolumnValue;
    }

    /**
     * This method used to update any single column in any table, by validating
     * using given validate column name and validate column value
     *
     * @param tableName
     * @param insertColName
     * @param insertColValue
     * @param validateColName
     * @param validateColValue
     */
    public static void toUpdateColumn(String tableName, String insertColName, String insertColValue, String validateColName, String validateColValue) {
        StringBuffer queryBuffer = new StringBuffer();
        try {
        	queryBuffer.append("UPDATE  ").append(tableName).append(" SET  ").append(insertColName).append(" =?  WHERE  ").append(validateColName).append(" = '").append(validateColValue).append("' ");
            QueryUtil.update(queryBuffer.toString(), new String[]{insertColValue});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecord(String tableName, String colName, String colVal) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE " + colName + "  = ?";
            QueryUtil.update(query, new String[] { colVal });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecord(String tableName, String whereClause) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE " + whereClause;
            QueryUtil.update(query, Constants.EMPTY_STRING_ARRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to delete Row from table, by validating using given
     * validate column name and validate column value
     *
     * @param tableName
     * @param validateColName
     * @param validateColValue
     */
    public static void toDeleteRow(String tableName, String validateColName, String validateColValue) {
        StringBuffer queryBuffer = new StringBuffer();
        try {
            queryBuffer.append("DELETE FROM  ").append(tableName).append("  WHERE  ").append(validateColName).append(" = '").append(validateColValue).append("' ");
            QueryUtil.update(queryBuffer.toString(), new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by Vikram Raj P_CONFIG_E_1403201 method to get a comma separated
     * column value
     *
     * @param tableName
     *            name of the table from which column is required
     * @param columnName
     *            column name which is required as comma seperated value
     * @param whereClause
     *            where clause for the query
     * @return Comma separated string of the column value which was required
     */
    public static String getCommaSeperateColumnValuesCSVInput(String tableName, String columnName, String whereClause, String duplicateRecordFlag) {
        String returnString = "";
        ResultSet rs = null;
        try {
            String query = "";
            int counter = 0;
            if (StringUtil.isValid(duplicateRecordFlag)) {
                query = "SELECT GROUP_CONCAT(DISTINCT(" + columnName + ")) AS " + columnName + " FROM " + tableName + " WHERE " + whereClause;
            } else {
                query = "SELECT GROUP_CONCAT(" + columnName + ") AS " + columnName + " FROM " + tableName + " WHERE " + whereClause;
            }
            rs = QueryUtil.getResult(query, null);
            while (rs.next()) {
                if (counter == 0) returnString = rs.getString(columnName); else returnString += "," + rs.getString(columnName);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            QueryUtil.releaseResultSet(rs);
        }
        return returnString;
    }

    public static void toUpdateColumn(String tableName, String insertColName, String insertColValue, String whereClause) {
        StringBuffer queryBuffer = new StringBuffer();
        try {
            queryBuffer.append("UPDATE  ").append(tableName).append(" SET  ").append(insertColName).append(" = '").append(insertColValue).append("'  WHERE  ").append(whereClause);
            QueryUtil.update(queryBuffer.toString(), new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This Method will update column with column name.
     * like UPDATE TABLE SET COUNT=COUNT+1
     * @param tableName
     * @param insertColName
     * @param insertColValue
     * @param validateColName
     * @param validateColValue
     */
    public static void toUpdateColumnWithColumnKey(String tableName, String insertColName, String insertColValue, String validateColName, String validateColValue) {
        StringBuffer queryBuffer = new StringBuffer();
        try {
            queryBuffer.append("UPDATE  ").append(tableName).append(" SET  ").append(insertColName).append(" = ").append(insertColValue).append("  WHERE  ").append(validateColName).append(" = '").append(validateColValue).append("' ");
            QueryUtil.update(queryBuffer.toString(), new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by Vikram Raj P_CONFIG_E_1403201 method to get a comma separated
     * column value
     *
     * @param tableName
     *            name of the table from which column is required
     * @param columnName
     *            column name which is required as comma seperated value
     * @param whereClause
     *            where clause for the query
     * @return Comma separated string of the column value which was required
     */
    public static String getCommaSeperateColumnValues(String tableName, String columnName, String whereClause) {
        return getCommaSeperateColumnValues(tableName, columnName, whereClause, null);
    }

    public static String getCommaSeperateColumnValues(String tableName, String columnName, String whereClause, String duplicateRecordFlag) {
        String returnString = "";
        ResultSet rs = null;
        try {
            String query = "";
            int counter = 0;
            if (StringUtil.isValid(duplicateRecordFlag)) {
                query = "SELECT GROUP_CONCAT(DISTINCT(" + columnName + ")) AS " + columnName + " FROM " + tableName + " WHERE " + whereClause;
            } else {
                query = "SELECT GROUP_CONCAT(" + columnName + ") AS " + columnName + " FROM " + tableName + " WHERE " + whereClause;
            }
            rs = QueryUtil.getResult(query, null);
            while (rs.next()) {
                if (counter == 0) returnString = rs.getString(columnName); else returnString += "," + rs.getString(columnName);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            QueryUtil.releaseResultSet(rs);
        }
        return returnString;
    }

    /**
     * @author Vivek Maurya
     * @return column's value from last row using while(rs.next())
     */
    public static String getLastColumnValue(String tableName, String columnName, String keyColumn, String key) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        if (columnName.trim().toUpperCase().equals(keyColumn.trim().toUpperCase())) {
            return key;
        }
        String[] paramArray = null;
        query.append("SELECT ").append(columnName).append(" FROM ").append(tableName);
        if (StringUtil.isValidNew(keyColumn)) {
            paramArray = new String[] { key };
            query.append(" WHERE ").append(keyColumn).append("=?");
        }
        return getLastResult(query.toString(), columnName, paramArray);
    }

    public static String getLastResult(String query, String requiredColumn) {
        return getLastResult(query, requiredColumn, null);
    }

    public static String getLastResult(String query, String requiredColumn, String[] paramArray) {
        ResultSet result = null;
        String returnValue = null;
        try {
            result = QueryUtil.getResult(query, paramArray);
            if (result != null) {
                while (result.next()) {
                    returnValue = result.getString(requiredColumn);
                }
            }
        } catch (Exception e) {
        } finally {
            QueryUtil.releaseResultSet(result);
        }
        return returnValue;
    }

    public static String getValidColumnInClause(String tableName, String columnName, String keyColumn, String key) {
        String returnValue = null;
        StringBuffer query = new StringBuffer("SELECT ").append(columnName);
        query.append(" FROM ").append(tableName);
        query.append(" WHERE ").append(keyColumn).append(" IN ('").append(key).append("')");
        ResultSet result = null;
        try {
            result = QueryUtil.getResult(query.toString(), null);
            if (result != null && result.next()) {
                if (StringUtil.isValidNew(result.getString(columnName))) {
                    returnValue = result.getString(columnName);
                }
            }
        } catch (Exception e) {
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
        }
        return returnValue;
    }

    /**
     *
     * @param tableName
     * @param columnNames
     * @param keyColumns
     * @return
     */
    public static HashMap getColumnValueMultiple(String tableName, String[] columnNames, String[] keyColumns) {
     return getColumnValueMultiple(tableName,  columnNames,  keyColumns,null);
    }
    public static HashMap getColumnValueMultiple(String tableName, String[] columnNames, String[] keyColumns,String[] displayColumn) {
        StringBuilder queryBuilder = null;
        HashMap dataMap = null;
        ResultSet rs = null;
        try {
            queryBuilder = new StringBuilder(" SELECT ");
            for (String columnName : columnNames) {
                queryBuilder.append(columnName).append(",");
            }
            queryBuilder.append("''");
            queryBuilder.append(" FROM ");
            queryBuilder.append(tableName);
            queryBuilder.append(" WHERE ");
            for (String keyColumn : keyColumns) {
                queryBuilder.append(keyColumn);
                queryBuilder.append(" ");
            }
            rs = QueryUtil.getResult(queryBuilder.toString(), new String[] {});
            while (rs!=null && rs.next()) {
                dataMap = new HashMap();
                if(displayColumn!=null){
                	for (String columnName : displayColumn) {
                		dataMap.put(columnName, rs.getString(columnName));
                	}
                }else{
                	for (String columnName : columnNames) {
                		dataMap.put(columnName, rs.getString(columnName));
                	}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return dataMap;
    }

    /**
     *
     * @param tableName
     * @param columnNames
     * @param keyColumns
     * @return
     */
    public static HashMap getColumnValueMultipleRow(String tableName, String[] columnNames, String[] keyColumns) {
        return getColumnValueMultipleRow(tableName, columnNames, keyColumns, null);
    }

    public static HashMap getColumnValueMultipleRow(String tableName, String[] columnNames, String[] keyColumns, String orderByClause) {
        StringBuilder queryBuilder = null;
        HashMap dataMap = new HashMap();
        ;
        ResultSet rs = null;
        try {
            queryBuilder = new StringBuilder(" SELECT ");
            for (String columnName : columnNames) {
                queryBuilder.append(columnName).append(",");
            }
            queryBuilder.append("''");
            queryBuilder.append(" FROM ");
            queryBuilder.append(tableName);
            if (keyColumns.length > 0) {
                queryBuilder.append(" WHERE ");
                for (String keyColumn : keyColumns) {
                    queryBuilder.append(keyColumn);
                    queryBuilder.append(" ");
                }
            }
            if (StringUtil.isValid(orderByClause)) {
                queryBuilder.append(" ORDER BY ").append(orderByClause);
            }
            rs = QueryUtil.getResult(queryBuilder.toString(), new String[] {});
            int i = 0;
            while (rs.next()) {
                i++;
                for (String columnName : columnNames) {
                    dataMap.put(columnName + "" + i, rs.getString(columnName));
                }
            }
            dataMap.put("rowCount", i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return dataMap;
    }

    public static void insertRowInTable(String tableName, String[] columns, String[] values) {
        StringBuffer queryBuffer = new StringBuffer("INSERT INTO ");
        queryBuffer.append(tableName).append(" (");
        StringBuffer valueString = new StringBuffer();
        int count = 0;
        ArrayList a = new ArrayList();
        for (String col : columns) {
            queryBuffer.append(col).append(",");
            if ("NOW()".equals(values[count])) {
                valueString.append("'" + DateUtil.getCurrentDateAsString(DateUtil.DB_FORMAT_HMS) + "',");
            } else if ("CURRENT_DATE()".equals(values[count])) {
                valueString.append("CURRENT_DATE(),");
            } else {
                valueString.append("?,");
                a.add(values[count]);
            }
            count++;
        }
        values = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            values[i] = (String) a.get(i);
        }
        if (queryBuffer.charAt(queryBuffer.length() - 1) == ',') {
            queryBuffer.setCharAt(queryBuffer.length() - 1, ' ');
            valueString.setCharAt(valueString.length() - 1, ' ');
        }
        queryBuffer.append(") VALUES(").append(valueString).append(")");
        try {
            int result = QueryUtil.executeInsert(queryBuffer.toString(), values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRows(String tableName, String[] whereColums, String[] whereValue) {
        StringBuffer queryBuffer = new StringBuffer("DELETE FROM ");
        queryBuffer.append(tableName);
        StringBuffer valueString = new StringBuffer();
        if (whereColums != null) {
            queryBuffer.append(" WHERE ");
            int count = 0;
            for (String col : whereColums) {
                queryBuffer.append(col).append("='");
                queryBuffer.append(whereValue[count]).append("' AND ");
                count++;
            }
            queryBuffer.append(" 1=1 ");
        }
        try {
            System.out.println("queryBuffer.toString()=====Delete========================================" + queryBuffer.toString());
            int result = QueryUtil.update(queryBuffer.toString(), new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Info getColumnValueInfowithWhere(String tableName, String columnName1, String columnName2, String keyColumn, String key, String columnForOrder, String sortOrder, boolean applyLanguageUtil, String whereClause) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        Info info = new Info();
        try {
            query.append("SELECT ").append(columnName1 + "," + columnName2).append(" FROM ").append(tableName);
            if (StringUtil.isValid(whereClause)) {
                query.append(" WHERE ").append(whereClause);
                if (StringUtil.isValid(columnForOrder)) {
                    query.append(" ORDER BY ").append(columnForOrder);
                }
                if (StringUtil.isValid(sortOrder)) {
                    query.append("  ").append(sortOrder);
                }
                result = QueryUtil.getResult(query.toString(), null);
            } else if (StringUtil.isValid(keyColumn)) {
                query.append(" WHERE ").append(keyColumn).append(" IN(" + key + ")");
                if (StringUtil.isValid(columnForOrder)) {
                    query.append(" ORDER BY ").append(columnForOrder);
                }
                if (StringUtil.isValid(sortOrder)) {
                    query.append("  ").append(sortOrder);
                }
                result = QueryUtil.getResult(query.toString(), null);
            } else {
                if (StringUtil.isValid(columnForOrder)) {
                    query.append(" ORDER BY ").append(columnForOrder);
                }
                result = QueryUtil.getResult(query.toString(), null);
            }
            while (result.next()) {
                info.set(result.getString(columnName1), applyLanguageUtil ? LanguageUtil.getString(result.getString(columnName2)) : result.getString(columnName2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return info;
    }

    public static String getColumnValue(String tableName1, String tableName2, String columnName, String joinOn1, String joinOn2,
            String keyColumn, String key) {
        String query = null;
        ResultSet result = null;
        String returnValue=FieldNames.EMPTY_STRING;

        // Added By Amit Kumar Srivastava on 24th June 2008

        if (columnName.trim().toUpperCase().equals(keyColumn.trim().toUpperCase())) {

            return key;
        }

        // Till here added by amit

        try {

            query = "SELECT " + "B." + columnName + " FROM " + tableName1 + " A" + " LEFT JOIN " + tableName2 + " B ON " + "A." + joinOn1 + "=" + "B." + joinOn2 + " WHERE "
                    + "A." + keyColumn + "=" + key;
            result = QueryUtil.getResult(query,null);
            if(result.next())
            {
            	returnValue = result.getString(columnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result = null;
            }
        }
        return returnValue;
    }
    
    
    /**
     *@author Banti Prajapati
     * @param tableName
     * @param columnNames
     * @param keyColumns
     * @return
     */
    public static String getColumnValue(String tableName,String getColumn, String[] columnNames,
            String[] keyColumns) {
        StringBuilder queryBuilder = null;
        HashMap dataMap = null;
        ResultSet rs = null;
        String returnValue=FieldNames.EMPTY_STRING;
        try {
            queryBuilder = new StringBuilder(" SELECT "+getColumn);
            /*for (String columnName : columnNames) {
                queryBuilder.append(columnName).append(",");
            }*/
//            queryBuilder.append("''");
            queryBuilder.append(" FROM ");
            queryBuilder.append(tableName);
            queryBuilder.append(" WHERE ");
            for (String columnName : columnNames) {
                queryBuilder.append(columnName+"=?");
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(" 1=1");
            rs = QueryUtil.getResult(queryBuilder.toString(), keyColumns);
            if (rs!=null && rs.next())
            {
                dataMap = new HashMap();
                returnValue=rs.getString(getColumn);
            }
        } catch (Exception e) {
     	   rs = null;
            queryBuilder = null;
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return returnValue;
    }
    /**
     * @author Sourabh Singh
     * @param tableName
     * @param getColumn
     * @param columnNames
     * @param keyColumns
     * @return
     */
    public static String getDecryptedColumnValue(String tableName,String getColumn, String[] columnNames,
            String[] keyColumns) {
        StringBuilder queryBuilder = null;
        ResultSet rs = null;
        String returnValue=FieldNames.EMPTY_STRING;
        try {
            queryBuilder = new StringBuilder(" SELECT AES_DECRYPT("+getColumn);
            queryBuilder.append(",'pvm@e20') AS ").append(getColumn).append(" FROM ");
            queryBuilder.append(tableName);
            queryBuilder.append(" WHERE ");
            for (String columnName : columnNames) {
                queryBuilder.append(columnName+"=?");
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(" 1=1");
            rs = QueryUtil.getResult(queryBuilder.toString(), keyColumns);
            if (rs != null && rs.next()) {
                returnValue = rs.getString(getColumn);
            }
        } catch (Exception e) {
     	   rs = null;
            queryBuilder = null;
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return returnValue;
    }
     
    /**
     * @author Sourabh Singh
     * @param tableName
     * @param columnName
     * @param keyColumn
     * @param key
     * @return
     */
    public static String getDecryptedColumnValue(String tableName, String columnName, String keyColumn, String key) {
        StringBuffer query = new StringBuffer();
        ResultSet result = null;
        String returnValue = FieldNames.EMPTY_STRING;
        if (columnName.trim().toUpperCase().equals(keyColumn.trim().toUpperCase())) {
            return key;
        }
        try {
            String[] paramArray = null;
            query.append("SELECT AES_DECRYPT(").append(columnName).append(",'pvm@e20') AS ").append(columnName).append(" FROM ").append(tableName);
            if (StringUtil.isValidNew(keyColumn)) {
                paramArray = new String[] { key };
                query.append(" WHERE ").append(keyColumn).append("=?");
            }
            result = QueryUtil.getResult(query.toString(), paramArray);
            if (result != null && result.next()) {
                returnValue = result.getString(columnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
        }
        return returnValue;
    }
    
    /**
     *@author Banti Prajapati
     * @param tableName
     * @param columnNames
     * @param keyColumns
     * @param operator String array specify type of operator to be used.Keep its length one less than columnNames 
     * @return
     */
    public static String getColumnValue(String tableName,String getColumn, String[] columnNames,
            String[] keyColumns, String []operator) {
        StringBuilder queryBuilder = null;
        HashMap dataMap = null;
        ResultSet rs = null;
        try {
            queryBuilder = new StringBuilder(" SELECT "+getColumn);

            queryBuilder.append(" FROM ");
            queryBuilder.append(tableName);
            queryBuilder.append(" WHERE ");
            for (int i=0;i<columnNames.length;i++) {
                queryBuilder.append(columnNames[i]+" IN("+keyColumns[i]+")");
                queryBuilder.append(" "+operator[i]);
            }
            queryBuilder.append("AND 1=1");
            rs = QueryUtil.getResult(queryBuilder.toString(), null);
            if (rs!=null && rs.next()) {
                dataMap = new HashMap();
         return rs.getString(getColumn);
            }
        } catch (Exception e) {
     	   rs = null;
            queryBuilder = null;
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return "";
    }
    
    /**
     * BB-20150320-274
     * @param tableName
     * @param columns
     * @param infoKeys
     * @param keyColumn
     * @param keyColumnValue
     * @return
     */
    public static Info getMultipleRowsInfo(String tableName, String[] columns, String[] infoKeys, String keyColumn, String keyColumnValue) {
    	Info dataInfo = new Info();

    	if (BaseUtils.checkArray(columns) && BaseUtils.checkArray(infoKeys)
    			&& columns.length == infoKeys.length) {
    		String[] paramArray = null;
    		StringBuffer query = new StringBuffer("SELECT ");

    		query.append(columns[0]);
    		int length = columns.length;
    		for (int i = 1; i < length; i++) {
    			query.append(",").append(columns[i]);
    		}

    		query.append(" FROM ").append(tableName);
    		if (StringUtil.isValidNew(keyColumn)) {
    			query.append(" WHERE ").append(keyColumn).append("=?");
    			paramArray = new String[] { keyColumnValue };
    		}

    		ResultSet rs = null;
    		try {
    			rs = QueryUtil.getResult(query.toString(), paramArray);
    			if (rs != null) {
    				while (rs.next()) {
    					for (int i = 0; i < length; i++) {
    						dataInfo.set(infoKeys[i], rs.getString(columns[i]));
    					}
    				}
    			}
    		} catch (Exception e) {
    		} finally {
    			if (rs != null) {
    				rs.release();
    				rs = null;
    			}
    			columns = null;
    			infoKeys = null;
    			paramArray = null;
    		}
    	} else {
    		throw new IllegalArgumentException(
    				"columns and infoKeys must not be null.\n Length of both arrays must be same");
    	}

    	return dataInfo;
    }
    
    /**
     *@author Prashant Malik Pilot COde
     * @param tableName
     * @param columnNames
     * @param keyColumns
     * @param operator String array specify type of operator to be used
     * @param conditionOperator String array specify type of column condition to be used 
     * @return
     */
    public static String getColumnValue(String tableName,String getColumn, String[] columnNames,
            String[] keyColumns, String []operator, String []conditionOperator) {
        StringBuilder queryBuilder = null;
        HashMap dataMap = null;
        ResultSet rs = null;
        try {
            queryBuilder = new StringBuilder(" SELECT "+getColumn);

            queryBuilder.append(" FROM ");
            queryBuilder.append(tableName);
            queryBuilder.append(" WHERE ");
            queryBuilder.append(" 1=1 ");
            for (int i=0;i<columnNames.length;i++) {
         	   queryBuilder.append(" "+operator[i]+" ");
                queryBuilder.append(columnNames[i]);
                queryBuilder.append(conditionOperator[i]);
                if(keyColumns[i]!=null && keyColumns[i].equals("NULL"))
                	queryBuilder.append(keyColumns[i]);
                else
             		   queryBuilder.append("'"+keyColumns[i]+"'");
            }
            rs = QueryUtil.getResult(queryBuilder.toString(), null);
            if (rs!=null && rs.next()) {
                dataMap = new HashMap();
         return rs.getString(getColumn);
            }
        } catch (Exception e) {
     	   rs = null;
            queryBuilder = null;
            e.printStackTrace();
        } finally {
            rs = null;
            queryBuilder = null;
        }
        return "";
   }   
    public static Map<String,HashMap> getMultipleRow(String tableName, String[] columnNames,String Column , String key) 
    {
    	 	Map<String,HashMap> smap=new HashMap<String,HashMap>();
    	 	HashMap<String,String> info=new HashMap<String,String>();
    	 	StringBuilder queryBuilder = null;
        int counter=0;
    	 	HashMap dataMap = null;
    	 	ResultSet rs = null;
        int i;
        try 
        {
                   queryBuilder = new StringBuilder(" SELECT ");
             boolean flag=false;
             for ( i=0;i<columnNames.length;i++) 
             {
            	 if(flag){
         	 		queryBuilder.append(" , ");
         	 	}else{
         	 		  flag=true;
         	 	}
            	 	queryBuilder.append(columnNames[i]);
             }
         		queryBuilder.append(" FROM ");
         		queryBuilder.append(tableName);
         		queryBuilder.append(" WHERE ");
         		queryBuilder.append(" 1=1 ");
         		queryBuilder.append(" AND "+Column+" = ? ");
           		rs = QueryUtil.getResult(queryBuilder.toString(), new String[]{key});
	         while(rs!=null && rs.next()) 
	         {
	        	 info=new HashMap<String,String>();
	        	 for ( i=0;i<columnNames.length;i++) 
	             {
	        		 	info.put(columnNames[i], rs.getString(columnNames[i]));
	             }
	        	 		smap.put(counter+"", info);
	        	 		counter++;
	        }
	     } catch (Exception e) {
	     }
    	 return smap;
    }
    

	public static void closeResultSet( java.sql.ResultSet rs){

       try{

	       if(rs != null){		  

		      rs.close();

			  rs = null;

           } 

	    }catch(Exception e){

	      Debug.print(e);

          System.out.println("Error in closing resultset" + e);

	   }   

	}



    public static void closePstmt(PreparedStatement pstmt){

       try{

	       if(pstmt != null){		  

		      pstmt.close(); 

			  pstmt = null;

           } 

	    }catch(Exception e){

	      Debug.print(e);

          Debug.println("Error in closing statement" + e,3);

	   }   

	}



	 public static void closeStmt(Statement stmt){

       try{

	       if(stmt != null){		  

		      stmt.close(); 

			  stmt = null;

           } 

	    }catch(Exception e){

	      Debug.print(e);

          System.out.println("Error in closing statement" + e);

	   }   

	}
	//ZCUB-20150915-176 starts
	public static void updateColumnWithCurrentDBTime(String tableName,String columnToSet,String conditionQuery)
	{
		try{
			StringBuffer query=new StringBuffer(" UPDATE ").append(tableName).append(" SET ").append(columnToSet).append(" = NOW() ").append(" WHERE ").append(conditionQuery);
			QueryUtil.update(query.toString(), null);
		}
		catch(Exception e)
		{
		}
	}

	//ZCUB-20150915-176 ends
	
	

	 //ZCUB-20150728-166 start
	 public static ArrayList<String> getArrayList(String tablename,String columnName,String condition) throws SQLException {
		 
		StringBuffer query = new StringBuffer("SELECT ");
		query.append(columnName+" from "+tablename+" "+condition); 
		 ResultSet result=null;
		 ArrayList<String> list=new ArrayList<String>();
		 try{
				result = QueryUtil.getResult(query.toString(), null);
				while(result != null && result.next()){
					list.add(result.getString(columnName));
				}
	       
		 }catch(Exception e){
		 }
			finally{
				QueryUtil.releaseResultSet(result);
				}
			return list;
		}
	 //ZCUB-20150728-166 ends
	 
	 
	 public static boolean isTableDataExists(String tableName,String idField){
		 
		 boolean haveData=false;
		 
		 String query="SELECT "+idField+" FROM "+tableName+" limit 0,1 ";
		 
		 ResultSet result = QueryUtil.getResult(query, null);
		 if(result!=null && result.next()){
			 haveData=true;
		 }
		 	
		 return haveData;
	 }
	 
}
