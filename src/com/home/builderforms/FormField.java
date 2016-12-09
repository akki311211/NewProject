package com.appnetix.app.util.form;

import java.util.*;

public class FormField
{
	private String name;
	private String table;
	private String tblSuffix;
	private String label;
	private String value;
	private String type = "text";
	private String colspan = "1";
	private String rowspan = "1";
	private String rows = "3";
	private String options;
	private String mandatory = "false";

	private String address1 = "ADDRESS";
	private String address2 = "ADDRESS2";
	private String city = "CITY";
	private String state = "STATE";
	private String country = "COUNTRY";
	private String zip = "ZIPCODE";

	public void setName(String value)
	{
		name = value;
	}
	public String getName()
	{
		return name;
	}

	public void setTable(String value)
	{
		table = value;
	}
	public String getTable()
	{
		return table;
	}

	public void setTblSuffix(String value)
	{
		tblSuffix = value;
	}
	public String getTblSuffix()
	{
		return tblSuffix;
	}

	public void setLabel(String value)
	{
		label = value;
	}
	public String getLabel()
	{
		return label;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	public String getValue()
	{
		return this.value;
	}

	public void setType(String value)
	{
		type = value;
	}
	public String getType()
	{
		return type;
	}

	public void setRows(String value)
	{
		rows = value;
	}
	public String getRows()
	{
		return rows;
	}

	public void setColspan(String value)
	{
		colspan = value;
	}
	public String getColspan()
	{
		return colspan;
	}

	public void setRowspan(String value)
	{
		rowspan = value;
	}
	public String getRowspan()
	{
		return rowspan;
	}

	public String getOptions()
	{
		return options;
	}
	public void setOptions(String value)
	{
		options = value;
	}

	public String getMandatory()
	{
		return mandatory;
	}
	public void setMandatory(String value)
	{
		mandatory = value;
	}
	public boolean isRequired()
	{
		return (mandatory.equalsIgnoreCase("true"));
	}

	public void setAddress1(String value)
	{
		address1 = value;
	}
	public String getAddress1()
	{
		return address1;
	}

	public void setAddress2(String value)
	{
		address2 = value;
	}
	public String getAddress2()
	{
		return address2;
	}

	public void setCity(String value)
	{
		city = value;
	}
	public String getCity()
	{
		return city;
	}

	public void setState(String value)
	{
		state = value;
	}
	public String getState()
	{
		return state;
	}

	public void setCountry(String value)
	{
		country = value;
	}
	public String getCountry()
	{
		return country;
	}

	public void setZip(String value)
	{
		zip = value;
	}
	public String getZip()
	{
		return zip;
	}



}