package com.home.builderforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModuleMap implements java.io.Serializable
{
	private LinkedHashMap<String,Module> modules;
	private Set<String> excludedModules;
	private DefaultUrlMap defaultURLs;
	public Map<String,List<String>> _modulePrivileges;
	
	public static ModuleMap getInstance()
	{
		return new ModuleMap();
	}
	
	private ModuleMap()
	{
		modules = new LinkedHashMap<String,Module>();
		defaultURLs = DefaultUrlMap.getInstance();
		excludedModules = new HashSet<String>();
		_modulePrivileges = new HashMap<String ,List<String>>();
	}

	public LinkedHashMap<String,Module> getModules()
	{
		return modules;
	}

	public Iterator<Module> getAll()
	{
		return modules.values().iterator();
	}

	public Object getModule(String id)
	{
		return modules.get(id);
	}

	public void addModule(String id, Module module)
	{
		modules.put(id, module);
	}
	
	public DefaultUrlMap getDefaultURLs()
	{
		return defaultURLs;
	}

	public void addExcludedModules(Set<String> _excludedMods)
	{
		excludedModules.addAll(_excludedMods);
	}
	
	public Set<String> getExcludedModules()
	{
		return excludedModules;
	}
	
	public void addPrivileges(String moduleID,List<String> privilegeID)
	{
		List<String> privList = _modulePrivileges.get(moduleID);
		{
			if(privList == null)
			{	
				privList = new ArrayList<String>();
				_modulePrivileges.put(moduleID,privList);
			}
			privList.addAll(privilegeID);

		}
	}
	
	public List<String> getModulePrivilege(String moduleID)
	{
		return _modulePrivileges.get(moduleID);
	}
	
	public boolean isExcluded(String moduleID)
	{
		return excludedModules.contains(moduleID);
	}
	
	public Map<String,List<String>> getModulePrivileges()
	{
		return _modulePrivileges;
	}
}