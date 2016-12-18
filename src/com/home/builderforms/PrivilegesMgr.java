package com.home.builderforms;

import java.util.HashMap;
import java.util.Map;

import com.home.builderforms.StringUtil;

public class PrivilegesMgr
{
	private static PrivilegesMgr _privilegesMgr = new PrivilegesMgr();

	private Map<String, Privileges> _privileges;

	private PrivilegesMgr()
	{
		_privileges = new HashMap<String, Privileges>();
	}

	public static PrivilegesMgr getInstance()
	{
		return _privilegesMgr;
	}

	public Privileges getPrivileges()
	{
		return PrivilegeHelper.getPrivileges(_privileges);
	}

	public PrivilegeMap getPrivilegeMap()
	{
		PrivilegeMap _privilegeMap = null;

		Privileges _privileges = getPrivileges();

		if(_privileges != null)
		{
			_privilegeMap = _privileges.getPrivileges();
		}
		return _privilegeMap;
	}

	public ModuleMap getModuleMap()
	{
		ModuleMap _moduleMap = null;

		Privileges _privileges = getPrivileges();

		if(_privileges != null)
		{
			_moduleMap = _privileges.getModules();
		}
		return _moduleMap;
	}
	
	public DefaultUrlMap getDefaultUrlMap()
	{
		DefaultUrlMap _defaultUrlMap = null;

		Privileges _privileges = getPrivileges();

		if(_privileges != null)
		{
			_defaultUrlMap = _privileges.getModules().getDefaultURLs();
		}
		return _defaultUrlMap;
	}
	
	public Privileges loadPrivileges(String location,String refrenceKey)
	{
		Privileges privileges = null;
		
		if(StringUtil.isValid(refrenceKey))
		{
			privileges = _privileges.get(refrenceKey);
			if(privileges==null)
			{
				 privileges = Privileges.getInstance();
				_privileges.put(refrenceKey, privileges);
			}	
		}
		else
		{
			privileges = Privileges.getInstance();
		}

		PrivilegesXMLDAO.loadPrivileges(location, privileges);
		
		return privileges;
	}
}
