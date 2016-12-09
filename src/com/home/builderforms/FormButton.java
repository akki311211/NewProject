package com.appnetix.app.util.form;

public class FormButton
{
	private String label;
	private String type = "submit";
	private String onClick;
	
	public void setLabel(String value)
	{
		label = value;
	}
	public String getLabel()
	{
		return label;
	}

	public void setType(String value)
	{
		type = value;
	}
	public String getType()
	{
		return type;
	}

	public void setOnClick(String value)
	{
		onClick = value;
	}
	public String getOnClick()
	{
		return onClick;
	}

}