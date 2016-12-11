package com.home.builderforms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.home.builderforms.StringUtil;

public class UserRoleMap implements java.io.Serializable
{
	private Map<String, UserRole> roles;
	private String userNo;

	public static UserRoleMap newInstance(String userNo, Map<String, UserRole> roles)
	{
		return new UserRoleMap(userNo, roles);
	}

	public String getUserNo()
	{
		return userNo;
	}

	private UserRoleMap(String userNo, Map<String, UserRole> roles)
	{
		this.userNo = userNo;
		this.roles = roles;
	}
	//BB-20151201-455  Starts
	public UserRoleMap(String userNumber, PrivilegeMap privilegeMap, String associatedRoles) {
		this.userNo	=	userNumber;
		roles							=	new HashMap();
		UserRoleMgr.getInstance().getUserRoles(privilegeMap,associatedRoles,roles,userNo);
	}
	//BB-20151201-455  Ends
	public Iterator<UserRole> getAll()
	{
		return roles.values().iterator();
	}

	public Object getOne(String id)
	{
		return roles.get(id);
	}

	public Privilege getPrivilegeInMap(String pageURL)
	{
		Iterator<UserRole> roleIt = getAll();
		while(roleIt.hasNext())
		{
			UserRole userRole = (UserRole) roleIt.next();
			Privilege priv = userRole.getPrivilegeforURL(pageURL);
			if(priv != null && priv.getPrivilegeID() != null)
			{
				return priv;
			}
		}
		return null;
	}

	public boolean isModuleInMap(String moduleID)
	{
		Iterator<UserRole> roleIt = getAll();
		while(roleIt.hasNext())
		{

			UserRole userRole = (UserRole) roleIt.next();
			Map privs = (HashMap) userRole.getPrivilegesByModule(moduleID);
			if(privs != null && privs.size() > 0)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isModuleCodeInMap(String moduleCode)
	{
		Iterator<UserRole> roleIt = getAll();
		while(roleIt.hasNext())
		{
			UserRole userRole = (UserRole) roleIt.next();
			if(StringUtil.isValid(moduleCode))
			{
				Map privs = (HashMap) userRole.getPrivilegesByModuleCode(moduleCode);
				if(privs != null && privs.size() > 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isPrivilegeInMap(String pageURL)
	{
		boolean isPrivilege = false;

		if(StringUtil.isValidNew(pageURL))
		{
			Iterator<UserRole> roleIt = getAll();
			while(roleIt.hasNext())
			{
				UserRole userRole = (UserRole) roleIt.next();
				if(userRole.isURLInPrivileges(pageURL))
				{
					isPrivilege = true;
					break;
				}
			}

		}
		return isPrivilege;
	}

	public boolean isPrivilegeIDInMap(String PrivilegeID)
	{
		boolean isPrivilege = false;

		if(StringUtil.isValidNew(PrivilegeID))
		{
			Iterator<UserRole> roleIt = getAll();

			String privIds[] = PrivilegeID.split(",");

			while(roleIt.hasNext())
			{
				UserRole userRole = (UserRole) roleIt.next();
				for(String priv : privIds)
				{
					if(userRole.isPrivilegeForId(priv))
					{
						isPrivilege = true;
						break;
					}

				}
			}
		}

		return isPrivilege;
	}

	public boolean isContentInMap(String contentID)
	{
		Iterator<UserRole> roleIt = getAll();
		while(roleIt.hasNext())
		{
			UserRole userRole = (UserRole) roleIt.next();
			if(userRole.isContentInRole(contentID))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isContentViewable(String contentHierarchy, String contentType)
	{
		boolean flag = true;
		if(StringUtil.isValid(contentHierarchy))
		{
			StringTokenizer strTokens = new StringTokenizer(contentHierarchy, ",");
			while(strTokens.hasMoreTokens())
			{
				String contentID = strTokens.nextToken();
				if(!isContentInMap(contentID, contentType))
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isContentInMap(String contentID, String contentType)
	{
		Iterator<UserRole> roleIt = getAll();
		while(roleIt.hasNext())
		{
			UserRole userRole = (UserRole) roleIt.next();
			if(userRole.isContentInRole(contentID, contentType))
			{
				return true;
			}
		}
		return false;
	}

	public HashMap<String, PrivilegedContent> getContentsByType(String contentType)
	{
		Iterator<UserRole> roleIt = getAll();
		HashMap<String, PrivilegedContent> contentTypesInMap = new HashMap<String, PrivilegedContent>();
		while(roleIt.hasNext())
		{
			UserRole userRole = (UserRole) roleIt.next();
			HashMap<String, PrivilegedContent> typeContents = (HashMap<String, PrivilegedContent>) userRole.getContentsByType(contentType);
			contentTypesInMap.putAll(typeContents);
		}
		return contentTypesInMap;
	}
}
