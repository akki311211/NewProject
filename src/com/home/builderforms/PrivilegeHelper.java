package com.home.builderforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.home.builderforms.FieldNames;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.Privileges;

public class PrivilegeHelper
{
   
	public static Privileges getPrivileges(Map<String,Privileges> _privileges)
	{	
		String tenantName = Constants.TENANT_NAME;
		Privileges _composite = null;
		Privileges _tenant = _privileges.get(tenantName);
		Privileges _base = _privileges.get("base");
		
		if(_tenant!=null)
		{	
			//_composite = (Privileges)MultiTenancyUtil.getTenantContext().getAttribute(tenantName+"_privileges");
			if(_composite != null)
			{
				return _composite;
			}
			else
			{
				_composite = Privileges.getInstance();
				mergePrivilegeMap(_base,_composite,_tenant);
				mergeModuleMap(_base,_composite,_tenant);
				//MultiTenancyUtil.getTenantContext().setAttribute(tenantName+"_privileges",_composite);
			}
		}
		else
		{
			_composite = _base;
		}
		return _composite;
	}
	
	private static void mergePrivilegeMap(Privileges _base,Privileges _composite,Privileges _tenant)
	{
		LinkedHashMap<String, Privilege> _baseMap = _base.getPrivileges().getPrivileges();
		LinkedHashMap<String, Privilege> _tenantMap = _tenant.getPrivileges().getPrivileges();
		LinkedHashMap<String, Privilege> _compositeMap = _composite.getPrivileges().getPrivileges();
		_compositeMap.putAll(_baseMap); _compositeMap.putAll(_tenantMap);
		_compositeMap.keySet().removeAll(_tenant.getPrivileges().getExcludedPrivileges());
	}
	
	private static void mergeModuleMap(Privileges _base,Privileges _composite,Privileges _tenant)
	{
		LinkedHashMap<String, Module> _baseMap = _base.getModules().getModules();
		LinkedHashMap<String, Module> _tenantMap = _tenant.getModules().getModules();
		LinkedHashMap<String, Module> _compositeMap = _composite.getModules().getModules();
		_compositeMap.putAll(_baseMap); _compositeMap.putAll(_tenantMap);
		_compositeMap.keySet().removeAll(_tenant.getModules().getExcludedModules());
		mergeDefaultUrlMap(_base,_composite,_tenant);
		mergeModulePrivilegs(_base, _composite, _tenant);
	}
	
	private static void mergeDefaultUrlMap(Privileges _base,Privileges _composite,Privileges _tenant)
	{
		Map<String,SequenceMap<String,String>> _baseMap = _base.getModules().getDefaultURLs().getDefaultUrls();
		Map<String,SequenceMap<String,String>> _tenantMap = _tenant.getModules().getDefaultURLs().getDefaultUrls();
		Map<String,SequenceMap<String,String>> _compositeMap = _composite.getModules().getDefaultURLs().getDefaultUrls();
		for(int i=0;i<3;i++)
		{
			HashMap<String,String> _compMap = new HashMap<String,String> ();
			_compMap.putAll(_baseMap.get(Integer.toString(i)).getHashMap());
			if(_tenantMap.get(Integer.toString(i))!=null)
			{
				_compMap.putAll(_tenantMap.get(Integer.toString(i)).getHashMap());
			}
			_compositeMap.put(i+FieldNames.EMPTY_STRING,new SequenceMap<String, String>(_compMap));
		}
	}
	
	private static void mergeModulePrivilegs(Privileges _base,Privileges _composite,Privileges _tenant)
	{
		Map<String,List<String>> _baseMap = _base.getModules().getModulePrivileges(); 
		Map<String,List<String>> _tenantMap = _tenant.getModules().getModulePrivileges(); 
		Map<String,List<String>> _compositeMap = _composite.getModules().getModulePrivileges(); 
		_compositeMap.putAll(_baseMap);
		Iterator<String> modItr = 	_composite.getModules().getModules().keySet().iterator();
		while(modItr.hasNext())
		{	
			String moduleId = modItr.next();
			if(_tenantMap.containsKey(moduleId))
			{	
				if(_compositeMap.get(moduleId)==null)
				{
					_compositeMap.put(moduleId, new ArrayList<String>());
				}
				_compositeMap.get(moduleId).addAll(_tenantMap.get(moduleId));
			}
			_compositeMap.get(moduleId).removeAll(_tenant.getPrivileges().getExcludedPrivileges());
		}
	}
	
}
