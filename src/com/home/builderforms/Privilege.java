package com.home.builderforms;

import java.util.ArrayList;
import java.util.Iterator;

public class Privilege implements java.io.Serializable
{
	private int isAdmin;
	private String privilegeID;
	private String privilegeName;
	private String description;
	private String moduleID;
	private String moduleCode;
	private String dependentOn;
	private String internalTabName;
	private ArrayList<String> privURLs;
	private ArrayList<Integer> privilegeFor;
	public ArrayList<Integer> getPrivilegeFor() {
		return privilegeFor;
	}

	public void setPrivilegeFor(ArrayList<Integer> privilegeFor) {
		if(privilegeFor!=null){
			this.privilegeFor = privilegeFor;
		}else{
			this.privilegeFor = new ArrayList<Integer>();
		}
	}

	public String getInternalTabName()
	{
		return internalTabName;
	}

	public void setInternalTabName(String internalTabName)
	{
		this.internalTabName = internalTabName;
	}

	public String getPrivilegeID()
	{
		return privilegeID;
	}

	public String getPrivilegeName()
	{
		return privilegeName;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDependentOn()
	{
		return dependentOn;
	}

	public ArrayList<String> getPrivilegeURLs()
	{
		return privURLs;
	}

	public int isAdminPrivilege()
	{
		return isAdmin;
	}

	public String getModuleID()
	{
		return this.moduleID;
	}

	public String getModuleCode()
	{
		return this.moduleCode;
	}

	public void setAdminFlag(int flag)
	{
		this.isAdmin = flag;
	}

	public void setPrivilegeID(String id)
	{
		this.privilegeID = id;
	}

	public void setPrivilegeName(String name)
	{
		this.privilegeName = name;
	}

	public void setDescription(String desc)
	{
		this.description = desc;
	}

	public void setDependentOn(String depen)
	{
		this.dependentOn = depen;
	}

	public void setPrivilegeURLs(ArrayList<String> urls)
	{
		this.privURLs = urls;
	}

	public void setModuleID(String value)
	{
		this.moduleID = value;
	}

	public void setModuleCode(String value)
	{
		this.moduleCode = value;
	}

	public boolean isURLInPrivilege(String pageURL)
	{
		Iterator<String> it = privURLs.iterator();
		while(it.hasNext())
		{
			if(pageURL.indexOf((String) it.next()) != -1)
			{
				return true;
			}
		}
		return false;
	}

}
