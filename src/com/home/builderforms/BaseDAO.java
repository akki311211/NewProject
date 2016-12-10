/**
 * Name - BaseDAO.java
 * Directory position - com\appnetix\app\components\
 * Description: This is the basic DAO which defines all the functions of BaseDAOInterface to provide
 *	basic methods to developer
 * @List/Names of JSPs referenced by this JSP

 * @author  -Pritish
 * @version 1.1.1.1
 * @created December 30, 2003
 * By sanjeev  k On 11 july 2008 37002_dateFOrmat
 * 
------------------------------------------------------------------------------------------------------------
Version No.			Date		By				Against						Function Changed    Comments
------------------------------------------------------------------------------------------------------------
P_E_FIM_58658     10/07/2009  Nikhil Verma   	Task Trigger FIM enhancement.  processEvent(),convertIdFields()
--------------------------------------------------------------------------------------------------------------------------------
 */
package com.home.builderforms;

import java.sql.SQLException;
import java.util.Iterator;


import com.home.builderforms.DBUtil;
import com.home.builderforms.DateTime;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IDGenerator;
import com.home.builderforms.IntConstants;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

public class BaseDAO
{
	protected DBUtil dbUtil = DBUtil.getInstance();
	protected String tableAnchor;
	protected String tableName;

	public BaseDAO()
	{

	}

	public BaseDAO(String psTableAnchor)
	{
		tableAnchor = psTableAnchor;
	}

	public void init()
	{

	}

	public static void beginTransaction(String connectionName)
	{
		try
		{
			SQLUtil.beginTransaction(connectionName);
		}catch(Exception e)
		{
		}
	}

	public static void rollbackTransaction(String connectionName)
	{
		try
		{
			SQLUtil.rollbackTransaction(connectionName);
		}catch(Exception e)
		{
		}
	}

	public static void commitTransaction(String connectionName)
	{
		try
		{
			SQLUtil.commitTransaction(connectionName);
		}catch(Exception e)
		{
		}
	}

	public FieldMappings getFieldMappings()
	{
	
		return dbUtil.getFieldMappings(tableAnchor);
	}

	// Insert a record into the table , given the info object.
	// returns the primary key of the inserted record
	public Object[] create(Info info) throws SQLException
	{
		return createHelper(info, 0);
	}

	public Object[] createHelper(Info info, int count) throws SQLException
	{
		try
		{
			// Implemented in case we explicitly specify primary key for the record
			if("true".equals(info.getString(FieldNames.SKIP_PRIMARY_KEY)))
			{
				info.setID(new Integer[] { Integer.valueOf(info.getString(FieldNames.MANUAL_PRIMARY_KEY)) });
			}else
			{
				Integer[] id = new Integer[getFieldMappings().getIdField().length];
				for(int i = 0; i < id.length; i++)
				{
					// ClockWork-20141201-680 starts
					// id[i] = new Integer(IDGenerator.getNextKey());
					if(i == 0)
					{
						id[i] = new Integer(IDGenerator.getNextKey(getFieldMappings().getTableName(), getFieldMappings().getIdField()[0].getDbField(), 5));
					}else
					{
						id[i] = new Integer(IDGenerator.getNextKey());
					}// ClockWork-20141201-680 ends
				}
				info.setID(id);
			}
			// setNullIntsToDefault(info);
			SQLUtil.createRecord(getFieldMappings(), info, true);
			return info.getIDObject();
		}catch(com.mysql.jdbc.MysqlDataTruncation e)
		{
			return info.getIDObject();

		}catch(SQLException e)
		{
			if(count < 5)
			{
				// info.setID(new Integer(IDGenerator.getNextKey()));
				createHelper(info, ++count);
			}else
			{
				throw e;
			}
		}
		return null;

	}

	// Modifies an existing record in the table, the columns which are not in
	// the info object are left untampered
	public void modify(Info info)
	{
		try
		{
			// setNullIntsToDefault(info, true);
			SQLUtil.modifyRecord(getFieldMappings(), info);
		}catch(Exception e)
		{
		}
	}

	public void modify(Info info, SequenceMap paramMap)
	{
		try
		{
			setNullIntsToDefault(info, true);
			SQLUtil.modifyRecord(getFieldMappings(), info, paramMap);
		}catch(Exception e)
		{
		}
	}

	// Replaces an existing record in the table, the columns which are not in
	// the info object are set to null
	public void replace(Info info)
	{
		try
		{
			setNullIntsToDefault(info);
			SQLUtil.replaceRecord(getFieldMappings(), info);
		}catch(Exception e)
		{
		}

	}

	// deletes a record from the table
	public void delete(Integer[] id) throws SQLException
	{
		SQLUtil.deleteRecord(getFieldMappings(), id);
	}

	// deletes a record from the table
	public void delete(Info info) throws SQLException
	{
		SQLUtil.deleteRecord(getFieldMappings(), info);
	}

	public void deleteRecords(Integer[] ids) throws SQLException
	{
		SQLUtil.deleteRecords(getFieldMappings(), ids);
	}

	public void deleteRecords(SequenceMap map) throws SQLException
	{
		SQLUtil.deleteRecords(getFieldMappings(), map);
	}

	public Info getDetailsInfo(Integer id)
	{
		Info info = null;
		try
		{
			info = SQLUtil.getDetailsInfo(getFieldMappings(), id);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return info;
	}

	public Info getDetailsInfo1(Integer id)
	{
		Info info = null;
		try
		{
			info = SQLUtil.getDetailsInfo(getFieldMappings(), id);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return info;
	}

	public Info getDetailsInfo(SequenceMap map) throws SQLException, RecordNotFoundException
	{
		Info info = null;
		try
		{
			info = SQLUtil.getDetailsInfo(getFieldMappings(), map);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return info;
	}

	public int getCount(SequenceMap map) throws SQLException, RecordNotFoundException
	{
		return SQLUtil.getCount(getFieldMappings(), map);
	}

	public SequenceMap getSummaryCollection(SequenceMap map) throws SQLException, RecordNotFoundException
	{
		return SQLUtil.getSummaryCollection(getFieldMappings(), map);
	}

	public Info getSummaryInfo(Integer[] id) throws SQLException, RecordNotFoundException
	{
		return SQLUtil.getSummaryInfo(getFieldMappings(), id);
	}

	public SequenceMap getSummaryCollection() throws SQLException, RecordNotFoundException
	{
		return SQLUtil.getSummaryCollection(getFieldMappings());
	}

	public SequenceMap getCollection(String[] fieldNames, SequenceMap params, String orderBy)
	{
		try
		{
			return SQLUtil.getCollection(getFieldMappings(), fieldNames, params, orderBy);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return null;
	}

	public SequenceMap getCollection(SequenceMap params, String orderBy)
	{
		try
		{
			return getCollection(getFieldMappings().getAllFieldNames(), params, orderBy);
		}catch(Exception e)
		{
		}
		return null;
	}
	public SequenceMap getCollection(String[] fieldNames, SequenceMap params,String pageSize,String page,String orderBy)
	{
		try
		{
			return SQLUtil.getCollection(getFieldMappings(), fieldNames, params, pageSize, page,orderBy);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return null;
	}

	public SequenceMap getCollection(String[] fieldNames, SequenceMap params)
	{
		try
		{
			return SQLUtil.getCollection(getFieldMappings(), fieldNames, params);
		}catch(RecordNotFoundException rnfe)
		{
		}catch(Exception e)
		{
		}
		return null;
	}
	public SequenceMap getCollection(SequenceMap params,String pageSize,String page,String orderBy)
	{
		return getCollection(getFieldMappings().getAllFieldNames(), params, pageSize, page,orderBy);
	}
	public SequenceMap getCollection(SequenceMap params)
	{
		return getCollection(getFieldMappings().getAllFieldNames(), params);
	}

	public String[] getIDField()
	{
		Field[] fields = getFieldMappings().getIdField();
		String[] fieldName = new String[fields.length];
		for(int i = 0; i < fields.length; i++)
		{
			fieldName[i] = fields[i].getFieldName();
		}
		return fieldName;
	}

	public Info getSummaryInfo(String[] fieldNames, Integer[] id) throws SQLException, RecordNotFoundException
	{
		return SQLUtil.getSummaryInfo(getFieldMappings(), fieldNames, id);
	}

	public Object getFieldValue(String fieldName, Integer[] id) throws SQLException, RecordNotFoundException
	{
		return getSummaryInfo(new String[] { fieldName }, id).get(fieldName);
	}

	public void setNullIntsToDefault(Info info)
	{
		setNullIntsToDefault(info, false);
	}

	public void setNullIntsToDefault(Info info, boolean setSetObjectsOnly)
	{
		FieldMappings mappings = getFieldMappings();
		Field[] idFields = mappings.getIdField();
		String[] fieldNames = mappings.getAllFieldNames();
		for(int i = 0; i < fieldNames.length; i++)
		{
			String fieldName = fieldNames[i];
			String dataType = mappings.getDataType(fieldName);
			if(Field.DataType.INTEGER.equals(dataType) && info.getObject(fieldName) == null)
			{
				if(setSetObjectsOnly && !info.isSet(fieldName) && isIdField(fieldName, idFields))
				{
					continue;
				}
				info.set(fieldName, new Integer(IntConstants.DEFAULT_INT));
			}
		}
	}

	private boolean isIdField(String fieldName, Field[] idFields)
	{
		if(idFields != null)
		{
			for(int i = 0; i < idFields.length; i++)
			{
				String idFieldName = idFields[i].getFieldName();
				if(idFieldName != null && idFieldName.equals(fieldName))
				{
					return true;
				}
			}
		}
		return false;
	}

}
