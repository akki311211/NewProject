package com.home.builderforms.sqlqueries;



import com.home.builderforms.*;



import java.util.*;





public class ColumnList

{

	private SequenceMap tables;



	private DBUtil dbUtil;



	private HashMap argFields;

	private HashMap orderFields;



	public ColumnList()

	{

		tables = new SequenceMap();

		dbUtil = DBUtil.getInstance();

		argFields = new HashMap();

		orderFields = new HashMap();

	}



	public void addList(String tableAnchor,ArrayList tableFields)

	{

		tables.put(tableAnchor,tableFields);

	}



	public void addArgField(String fieldName,String argFieldName)

	{

		argFields.put(fieldName,argFieldName);

	}

	public void addOrderField(String fieldName,String order)

	{

		orderFields.put(fieldName,order);

	}

	private int size()

	{

		Iterator tableValuesIt = tables.values().iterator();

		int size = 0;

		while (tableValuesIt.hasNext())

		{

			ArrayList tableFields = (ArrayList)tableValuesIt.next();

			size += tableFields.size();

		}

		return size;

	}



	public String[] getColumnArray()

	{

		String[] columnArray = new String[size()];

		int count = 0;

		Iterator tablesIt = tables.keys().iterator();

		ArrayList duplicateColumnNames = new ArrayList();

		HashSet columnNames = new HashSet();



		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();

			ArrayList tableFields = (ArrayList)tables.get(tableAnchor);

			Iterator tableFieldsIter = tableFields.iterator();

			while(tableFieldsIter.hasNext())

			{

				String columnName = (String)tableFieldsIter.next();

				if (!columnNames.add(columnName))

				{

					duplicateColumnNames.add(columnName);

				}

			}

		}



		tablesIt = tables.keys().iterator();

		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();

			ArrayList tableFields = (ArrayList)tables.get(tableAnchor);

			Iterator tableFieldsIter = tableFields.iterator();

			while(tableFieldsIter.hasNext())

			{

				String columnName = (String)tableFieldsIter.next();

				String initString = "";

				if (duplicateColumnNames.contains(columnName))

				{

					initString = tableAnchor+".";

				}



				columnArray[count++]=initString+columnName;

			}

		}



		return columnArray;



	}







	public String getColumnNames()

	{

		Iterator tablesIt = tables.keys().iterator();



		StringBuffer sb = new StringBuffer();

		boolean flag = true;



		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();



			ArrayList tableFields = (ArrayList)tables.get(tableAnchor);

			Iterator tableFieldsIter = tableFields.iterator();

			FieldMappings fieldMappings = dbUtil.getFieldMappings(tableAnchor);

			String tableName = fieldMappings.getTableName();

			while(tableFieldsIter.hasNext())

			{

				if (flag)

				{

					flag = false;

				}

				else

				{

					sb.append(" , ");

				}

				String fieldName = (String)tableFieldsIter.next();



				if (Constants.isDefaultField(fieldName))

				{

					String argField = (String)argFields.get(fieldName);

					String argDbField =null;

					if (argField!=null)

					{

						argDbField = fieldMappings.getDbField(argField);

					}

					else

					{

						argDbField = "*";

					}

					sb.append(fieldMappings.getDbField(fieldName)+"("+argDbField+")");



				}

				else

				{

					sb.append(tableName+".");

					sb.append(fieldMappings.getDbField(fieldName));

					String order = (String)orderFields.get(fieldName);

					if(order != null)

					{

						sb.append(" " + order.toUpperCase());

					}

				}

			}

		}



		return sb.toString();

	}



	public Field[] getFieldArray()

	{

		Field[] fieldArray = new Field[size()];

		int count = 0;

		Iterator tablesIt = tables.keys().iterator();



		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();

			ArrayList tableFields = (ArrayList)tables.get(tableAnchor);

			FieldMappings fieldMappings = dbUtil.getFieldMappings(tableAnchor);

			Iterator tableFieldsIter = tableFields.iterator();

			while(tableFieldsIter.hasNext())

			{



				fieldArray[count++]= fieldMappings.getField((String)tableFieldsIter.next());

			}

		}



		return fieldArray;



	}



	public String getTableNames()

	{

		Iterator tablesIt = tables.keys().iterator();



		StringBuffer sb = new StringBuffer();

		boolean flag = true;



		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();

			FieldMappings fieldMappings = dbUtil.getFieldMappings(tableAnchor);

			String tableName = fieldMappings.getTableName();

			if (flag)

			{

				flag = false;

			}

			else

			{

				sb.append(",");

			}

			sb.append(tableName);

		}



		return sb.toString();

	}



	public HashSet getTableSet()

	{

		Iterator tablesIt = tables.keys().iterator();

		HashSet set = new HashSet();

		while (tablesIt.hasNext())

		{

			String tableAnchor = (String)tablesIt.next();

			FieldMappings fieldMappings = dbUtil.getFieldMappings(tableAnchor);

			String tableName = fieldMappings.getTableName();

			set.add(tableName);

		}



		return set;

	}



	public void setTableAnchors(SequenceMap oldNewMap){

		if(oldNewMap == null){

			return;

		}

		Iterator it				= oldNewMap.keys().iterator();

		while(it.hasNext()){

			String oldKey		= (String)it.next();

			String newKey		= (String)oldNewMap.get(oldKey);

			Object objValue		= tables.get(oldKey);

			if(objValue != null){

				tables.remove(oldKey);

				tables.put(newKey, objValue);

			}

		}

	}

}

