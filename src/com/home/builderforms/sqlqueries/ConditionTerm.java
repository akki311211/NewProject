package com.home.builderforms.sqlqueries;





import com.home.builderforms.DBUtil;

import com.home.builderforms.*;



import java.util.TreeMap;

import java.util.ArrayList;

import java.util.Iterator;



public class ConditionTerm



{

	public static final int FIELD		= 0;

	public static final int	RELATION	= 1;

	public static final int	LITERAL		= 2;



	private	int 	termType;

	private String 	value;

	private String 	tableAnchor;

	private String 	fieldName;

	private DBUtil 	dbUtil;



	public ConditionTerm(int termType,String value)

	{

		this.termType		= termType;

		this.value			= value;

		this.dbUtil 		= DBUtil.getInstance();

	}



	public ConditionTerm(int termType,String tableAnchor,String fieldName)

	{

		this.termType		= termType;

		this.tableAnchor	= tableAnchor;

		this.fieldName		= fieldName;

		this.dbUtil 		= DBUtil.getInstance();

	}



	public String getString()

	{

		String conditionTermString = null;

		switch(termType)

		{

			case FIELD:

			{

				FieldMappings fieldMappings = dbUtil.getFieldMappings(tableAnchor);

				conditionTermString 		= fieldMappings.getTableName() + "."

												+fieldMappings.getDbField(fieldName);

				break;

			}

			case RELATION:

			{

				conditionTermString				= value;

				break;

			}

			case LITERAL:

			{

				if (value.indexOf("?") != -1)

				{

					conditionTermString			= value;

				}

				else

				{

					conditionTermString			="'"+value+"'";

				}



				break;

			}

			default:

			{

				conditionTermString				= null;

				break;

			}

		}

		return conditionTermString;

	}



	public void setTableAnchor(String name){

		tableAnchor				= name;

	}

	public String getTableAnchor(){

		return tableAnchor;

	}



	public int getTermType(){

		return termType;

	}

}

