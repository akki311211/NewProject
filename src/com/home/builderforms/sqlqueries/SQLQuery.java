package com.home.builderforms.sqlqueries;



import java.util.HashSet;

import com.home.builderforms.*;


 






public class SQLQuery

{

	public static final String SELECT = "select";

	public static final String UPDATE = "update";



	private ColumnList 	columnList;

	private ColumnList 	groupByList;

	private ColumnList 	orderByList;



	private Condition	condition;

	private String 		name;

	private String		type;

	private String 		returnType;



	public SQLQuery(ColumnList columnList,

					Condition condition,

					String name,

					String type,

					String returnType,

					ColumnList groupByList,

					ColumnList orderByList)

	{

		this.columnList	= columnList;

		this.condition	= condition;

		this.name		= name;

		this.type		= type;

		this.returnType	= returnType;

		this.groupByList = groupByList;

		this.orderByList = orderByList;

	}





	public String getString()

	{

		String retString = null;

		if (SELECT.equals(type))

		{

			retString = getSelectString();

		}

		else if(UPDATE.equals(type))

		{

			retString = getUpdateString();

		}

		return retString;

	}

	public void setSelectTableAnchors(SequenceMap map){

		if(columnList != null){

			columnList.setTableAnchors(map);

		}

	}

	public void setConditionTableAnchors(SequenceMap map){

		if(condition != null){

			condition.setTableAnchors(map);

		}

	}

	public void setGroupByTableAnchors(SequenceMap map){

		if(groupByList != null){

			groupByList.setTableAnchors(map);

		}

	}

	public void setTableAnchors(SequenceMap map){

 

		if(columnList != null){

			columnList.setTableAnchors(map);

		}

 

		if(groupByList != null){

			groupByList.setTableAnchors(map);

		}

		if(orderByList != null){

			orderByList.setTableAnchors(map);

		}

 

		if(condition != null){

			condition.setTableAnchors(map);

		}

 



	}

	public String getSelectString()

	{

		HashSet tableSet = new HashSet();

		tableSet.addAll(columnList.getTableSet());

		tableSet.addAll(condition.getTableSet());



		String tableNames = StringUtil.toCommaSeparated((String[])tableSet.toArray(new String[0]));

		String selectString =

								"SELECT " + columnList.getColumnNames() + "  " +

								"FROM " + tableNames;

		if(condition != null)

		{

			selectString	+=  " " + "WHERE "+ condition.getString();

		}

		if(groupByList != null)

		{

			selectString	+=  " " + "GROUP BY "+ groupByList.getColumnNames();

		}

		if(orderByList != null)

		{

			selectString	+=  " ORDER BY "+ orderByList.getColumnNames();

		}

		return selectString;

	}



	public String getUpdateString()

	{

		String updateString = "";

		return updateString;

	}



	public String getName()

	{

		return name;

	}



	public String getType()

	{

		return type;

	}



	public String getReturnType()

	{

		return returnType;

	}



	public ColumnList getColumnList()

	{

		return columnList;

	}



	public ColumnList getGroupByList()

	{

		return groupByList;

	}





	public Condition getCondition()

	{

		return condition;

	}







}

