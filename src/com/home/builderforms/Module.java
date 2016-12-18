package com.home.builderforms;

import java.util.ArrayList;

public class Module implements java.io.Serializable
{
	private String moduleID;
	private String moduleName;
	private String description;
	private String moduleCode;
	private ArrayList<Integer> privilegeFor;
	public ArrayList<Integer> getPrivilegeFor() {
		return privilegeFor;
	}

	public void setPrivilegeFor(ArrayList<Integer> privilegeFor) {
		if(privilegeFor==null || privilegeFor.size()==0){
			this.privilegeFor = null;
		}else{
			this.privilegeFor = privilegeFor;
		}
	}
	
	public String getModuleCode()
	{
		return moduleCode;
	}

	public void setModuleCode(String moduleCode)
	{
		this.moduleCode = moduleCode;
	}

	public String getModuleID()
	{
		return moduleID;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setModuleID(String id)
	{
		this.moduleID = id;
	}

	public void setModuleName(String name)
	{
		this.moduleName = name;
	}

	public void setDescription(String value)
	{
		this.description = value;
	}

}
