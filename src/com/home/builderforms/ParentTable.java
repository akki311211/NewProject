package com.home.builderforms;

import java.util.*;

public class ParentTable
{
	private String heading;
	private String linkTable;
	private String linkField;
	private String delete;
	private String insert;

	private Vector foreignTable = new Vector();
	
	public void setHeading(String value)    
	{
		this.heading = value;
	}
	public String getHeading()
	{
		return heading;
	}


	public void setinsert(String value)
	{
		this.insert = value;
	}
	public String getinsert()
	{
		return insert;
	}

	public void setdelete(String value)
	{
		this.delete = value;
	}
	public String getdelete()
	{
		return delete;
	}

	public void setlinkTable(String value)
	{
		this.linkTable = value;
	}
	public String getlinkTable()
	{
		return linkTable;
	}

	public void setlinkField(String value)
	{
		this.linkField = value;
	}
	public String getlinkField()
	{
		return linkField;
	}
	
	public Vector getForeignTable()
	{
		return foreignTable;
	}

	public void addForeignTable(ForeignTable fld)
	{
		foreignTable.addElement(fld);
		
	}

}