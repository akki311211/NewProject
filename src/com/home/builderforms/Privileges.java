package com.home.builderforms;

import  com.home.builderforms.ModuleMap;

public class Privileges implements java.io.Serializable
{
	private ModuleMap modules;
	private PrivilegeMap privileges;
	

	public Privileges()
	{
		modules = ModuleMap.getInstance();
		privileges = PrivilegeMap.getInstance();
	}

	public static Privileges getInstance()
	{
		return new Privileges();
	}

	public ModuleMap getModules()
	{
		return modules;
	}

	public PrivilegeMap getPrivileges()
	{
		return privileges;
	}

}
