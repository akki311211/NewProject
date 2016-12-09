package com.home.builderforms;



import org.xml.sax.InputSource;

import org.w3c.dom.Element;

import org.w3c.dom.Document;

import org.w3c.dom.NodeList;

import org.w3c.dom.Node;

import org.w3c.dom.NamedNodeMap;







import java.net.URL;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Iterator;



 

import com.home.builderforms.Constants;

import com.home.builderforms.*;


import com.home.builderforms.sqlqueries.*;



public class SQLQueriesXMLDAO

{



	//Tag Names

	public static final String SQL_QUERY	= "sql-query";

	public static final String SELECT		= "select";

	public static final String GROUP_BY		= "group-by";

	public static final String ORDER_BY		= "order-by";



	public static final String TABLE_ANCHOR	= "table-anchor";

	public static final String FIELD_NAME	= "field-name";

	public static final String CONDITION	= "condition";

	public static final String FIELD		= "field";

	public static final String RELATION		= "relation";

	public static final String LITERAL		= "literal";





	//Attribute Names

	public static final String TYPE			= "type";

	public static final String NAME			= "name";

	public static final String RETURN_TYPE	= "returnType";

	public static final String TABLEANCHOR	= "tableAnchor";

	public static final String FIELDNAME	= "fieldName";

	public static final String OPERATOR		= "operator";

	public static final String VALUE		= "value";

	public static final String ARG_FIELD	= "arg-field";

	public static final String ORDER		= "order";



	public static HashMap getSQLQueries(String location)

	{

		Element root 	= XMLUtil.loadDocument(location);

		HashMap sqlQueriesMap = new HashMap();

		NodeList list	= root.getElementsByTagName(SQL_QUERY);

		for (int i=0;i<list.getLength();i++)

		{

			Node node = list.item(i);

			SQLQuery sqlQuery = getSQLQuery(node);

			sqlQueriesMap.put(sqlQuery.getName(),sqlQuery);

		}

		return sqlQueriesMap;

	}



	private static SQLQuery getSQLQuery(Node node)

	{

		String name 			= XMLUtil.getAttributeValue(node,NAME);

		String type 			= XMLUtil.getAttributeValue(node,TYPE);;

		String returnType		= XMLUtil.getAttributeValue(node,RETURN_TYPE);

		ColumnList columnList 	= getColumnList(XMLUtil.getNodeInChildren(node,SELECT));

		Condition condition		= getCondition(XMLUtil.getNodeInChildren(node,CONDITION));

		ColumnList groupByList	= getColumnList(XMLUtil.getNodeInChildren(node,GROUP_BY));

		ColumnList orderByList	= getColumnList(XMLUtil.getNodeInChildren(node,ORDER_BY));



		return new SQLQuery(columnList,condition,name,type,returnType,groupByList,orderByList);

	}



	private static ColumnList getColumnList(Node node)

	{

		if (node==null) return null;

		ColumnList columnList = new ColumnList();

		Node[] nodes = XMLUtil.getNodesInChildren(node,TABLE_ANCHOR);

		for (int i=0;i<nodes.length;i++)

		{

			String tableAnchor = XMLUtil.getAttributeValue(nodes[i],NAME);

			ArrayList tableFields = new ArrayList();

			Node[] fieldNodes = XMLUtil.getNodesInChildren(nodes[i],FIELD_NAME);

			for (int j=0;j<fieldNodes.length;j++)

			{

				String fieldName 	= XMLUtil.getAttributeValue(fieldNodes[j],VALUE);

				String argFieldName = XMLUtil.getAttributeValue(fieldNodes[j],ARG_FIELD);

				String order		= XMLUtil.getAttributeValue(fieldNodes[j],ORDER);

				if (argFieldName!=null)

				{

					columnList.addArgField(fieldName,argFieldName);

				}

				if (order!=null)

				{

					columnList.addOrderField(fieldName, order);

				}

				tableFields.add(fieldName);

			}

			columnList.addList(tableAnchor,tableFields);

		}

		return columnList;

	}



	private static Condition getCondition(Node node)

	{

		if (node==null) return null;

		String operator = XMLUtil.getAttributeValue(node,OPERATOR);

		if (operator == null)

		{

			Node[] nodes = XMLUtil.getNodesInChildren(node);

			if (nodes == null || nodes.length==0) return null;

			ConditionTerm[] conditionTerms = new ConditionTerm[nodes.length];

			for (int i=0;i<nodes.length;i++)

			{

				String nodeName = nodes[i].getNodeName();

				if (FIELD.equals(nodeName))

				{

					String tableAnchor 	= XMLUtil.getAttributeValue(nodes[i],TABLEANCHOR);

					String fieldName   	= XMLUtil.getAttributeValue(nodes[i],FIELDNAME);

					conditionTerms[i]  	= new ConditionTerm(ConditionTerm.FIELD,

														   tableAnchor,

														   fieldName);

				}

				else if (RELATION.equals(nodeName))

				{

					String value = XMLUtil.getAttributeValue(nodes[i],VALUE);

					conditionTerms[i]  = new ConditionTerm(ConditionTerm.RELATION,

														   value);

				}

				else if (LITERAL.equals(nodeName))

				{

					String value = XMLUtil.getAttributeValue(nodes[i],VALUE);

					conditionTerms[i]  = new ConditionTerm(ConditionTerm.LITERAL,

														   value);

				}

			}

			return new Condition(conditionTerms);

		}

		else

		{

			Condition condition = new Condition(operator);

			Node[] nodes = XMLUtil.getNodesInChildren(node,CONDITION);

			for (int i=0;i<nodes.length;i++)

			{

				condition.addCondition(getCondition(nodes[i]));

			}

			return condition;

		}

	}









}

