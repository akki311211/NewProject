package com.home.builderforms;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.UserTabConfigUtil;

public class PrivilegeMap implements java.io.Serializable
{
	private LinkedHashMap<String, Privilege> privileges;
	private Set<String> excludedPrivilges;
	
	
	public static PrivilegeMap getInstance()
	{
	   return new PrivilegeMap();
	}
	
	private PrivilegeMap()
	{
		privileges = new LinkedHashMap<String, Privilege>();
		excludedPrivilges = new HashSet<String>();
	}

	public LinkedHashMap<String, Privilege> getPrivileges()
	{
		return privileges;
	}

	public Iterator<Privilege> getAll()
	{
		return privileges.values().iterator();
	}

	public LinkedHashMap<String, Privilege> getPrivilegesByModule(String moduleID)
	{
		return getPrivilegesByModule(-1, moduleID);
	}

	public LinkedHashMap<String, Privilege> getPrivilegesByModule(int isAdmin, String moduleID)
	{
		SequenceMap userTabConfigMap = UserTabConfigUtil.getUserTabConfig();
		Iterator<Privilege> privIt = privileges.values().iterator();
		LinkedHashMap<String, Privilege> modulePrivileges = new LinkedHashMap<String, Privilege>();
		while(privIt != null && privIt.hasNext())
		{
			Privilege priv = (Privilege) privIt.next();
			if(priv.getModuleID().equals(moduleID) && (isAdmin == -1 || priv.getPrivilegeFor().contains(isAdmin)))
			{
				if(UserTabConfigUtil.isPrivilege(userTabConfigMap, moduleID, priv))
				{
					modulePrivileges.put(priv.getPrivilegeID(), priv);
				}
			}
		}

		return modulePrivileges;
	}

	public Privilege getPrivilege(String id)
	{
		return (Privilege) privileges.get(id);
	}

	public boolean isURLaPrivilege(String pageURL)
	{
		Iterator<Privilege> privIt = privileges.values().iterator();
		while(privIt != null && privIt.hasNext())
		{
			Privilege priv = (Privilege) privIt.next();
			if(priv.isURLInPrivilege(pageURL))
			{
				return true;
			}
		}
		return false;
	}

	public void put(String id, Privilege privilege)
	{
		privileges.put(id, privilege);
	}

	public Object clone()
	{
		LinkedHashMap<String, Privilege> cloneMap = new LinkedHashMap<String, Privilege>();
		cloneMap.putAll(privileges);
		return cloneMap;
	}

	public void addExcludedPrivileges(Set<String> _excludedPrivs)
	{
		excludedPrivilges.addAll(_excludedPrivs);
	}
	
	public Set<String> getExcludedPrivileges()
	{
		return excludedPrivilges;
	}
	
	public boolean isExcluded(String privilgeID)
	{
		return excludedPrivilges.contains(privilgeID);
	}
	
}
