package com.appnetix.app.util.form;

import java.util.Vector;
import java.util.HashMap;

public class FormTable
{
	private String name;
	private String suffix;
	private String childTable = "false";
	private Vector children;
	private HashMap columns;

	public void setName(String value)
	{
		name = value;
	}
	public String getName()
	{
		return name;
	}

	public void setSuffix(String value)
	{
		suffix = value;
	}
	public String getSuffix()
	{
		return suffix;
	}

	public void setChildTable(String value)
	{
		System.out.println("CTBool"+value);
		childTable = value;
	}
	public String getChildTable()
	{
		return childTable;
	}
	public boolean isChild()
	{
		System.out.println("isCTBool"+childTable+":"+childTable.equalsIgnoreCase("true"));
		return (childTable.equalsIgnoreCase("true"));
	}

	public void addChildren(String name, String suffix)
	{
		if (children == null)
			children = new Vector();
                if (suffix == null)
                    suffix = "0";
		children.addElement(name+":"+suffix);
	}
	public Vector getChildren()
	{
		return children;
	}
	public void addColumn(String name, String value)
	{
		if (columns == null)
			columns = new HashMap();
		columns.put(name, value);
	}
	public HashMap getColumns()
	{
		return columns;
	}

}