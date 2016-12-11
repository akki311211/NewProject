package com.home.builderforms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.home.builderforms.StringUtil;

public class UserRole implements java.io.Serializable
{
	private int isAdmin;
	private int isDefault;
	private int countOfUser;

	private String roleID;
	private String roleName;
	private String assignedTo;
	private String franchiseeNo;
	private String franchiseeName;

	private Map<String, Privilege> privileges;
	private Map<String, PrivilegedContent> contents;

	private UserRole()
	{
		isAdmin = 0;
		isDefault = 0;
		privileges = new HashMap<String, Privilege>();
		contents = new HashMap<String, PrivilegedContent>();
	}

	public static UserRole newInstance()
	{
		return new UserRole();
	}
	
	public String getRoleID()
	{
		return roleID;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public Map<String, Privilege> getPrivileges()
	{
		return privileges;
	}

	public Map<String, PrivilegedContent> getContents()
	{
		return contents;
	}

	public int isAdminRole()
	{
		return isAdmin;
	}

	public int getCountOfUser()
	{
		return countOfUser;
	}

	public String getAssignedTo()
	{
		return assignedTo;
	}

	public int isDefault()
	{
		return isDefault;
	}

	public void setAdminFlag(int flag)
	{
		this.isAdmin = flag;
	}

	public void setDefaultFlag(int flag)
	{
		this.isDefault = flag;
	}

	public void setAssignedTo(String value)
	{
		this.assignedTo = value;
	}

	public void setRoleID(String id)
	{
		this.roleID = id;
	}

	public void setRoleName(String name)
	{
		if("Default Divisional Role".equals(name)){
			name = "Default "+MultiTenancyUtil.getTenantConstants().DIVISION_LABEL+" Role";
		}
		this.roleName = name;
	}

	public void setPrivileges(Map<String, Privilege> privileges)
	{
		this.privileges = privileges;
	}

	public void setContents(Map<String, PrivilegedContent> contents)
	{
		this.contents = contents;
	}

	public void setCountOfUser(int count)
	{
		this.countOfUser = count;
	}
	
	public String getFranchiseeNo()
	{
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo)
	{
		this.franchiseeNo = franchiseeNo;
	}
	
	public String getFranchiseeName()
	{
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName)
	{
		this.franchiseeName = franchiseeName;
	}

	public Map<String, Privilege> getPrivilegesByModule(String moduleID)
	{
		Iterator<Privilege> privIt = privileges.values().iterator();
		Map<String, Privilege> _privileges = new HashMap<String, Privilege>();
		while(privIt != null && privIt.hasNext())
		{
			Privilege priv = (Privilege) privIt.next();
			if(priv != null && moduleID.equals(priv.getModuleID()))
			{
				_privileges.put(priv.getPrivilegeID(), priv);
			}
		}
		return _privileges;
	}

	public Map<String, Privilege> getPrivilegesByModuleCode(String moduleCode)
	{
		Iterator<Privilege> privIt = privileges.values().iterator();
		Map<String, Privilege> _privileges = new HashMap<String, Privilege>();
		while(privIt != null && privIt.hasNext())
		{
			Privilege priv = (Privilege) privIt.next();
			if(priv != null && moduleCode.equals(priv.getModuleCode()))
			{
				_privileges.put(priv.getPrivilegeID(), priv);
			}
		}
		return _privileges;
	}

	public Privilege getPrivilegeforURL(String pageURL)
	{
		Iterator<Privilege> privIt = privileges.values().iterator();
		while(privIt != null && privIt.hasNext())
		{
			Privilege priv = (Privilege) privIt.next();
			if(priv != null && priv.isURLInPrivilege(pageURL))
			{
				return priv;
			}
		}
		return null;
	}
	
	public Privilege getPrivilegeById(String privilegeId)
	{
		return privileges.get(privilegeId);
	}
	
	public Map<String, PrivilegedContent> getContentsByType(String contentType)
	{
		Iterator<PrivilegedContent> contentIt = contents.values().iterator();
		Map<String, PrivilegedContent> _contents = new HashMap<String, PrivilegedContent>();
		while(contentIt.hasNext())
		{
			PrivilegedContent cont = (PrivilegedContent) contentIt.next();
			if(cont != null && contentType.equals(cont.getContentType()))
			{
				_contents.put(cont.getContentID(), cont);
			}
		}
		return _contents;
	}

	public boolean isPrivilegeForId(String privilegeId)
	{
		return getPrivilegeById(privilegeId)==null?false:true;
	}
	
	public boolean isURLInPrivileges(String pageURL)
	{
		return getPrivilegeforURL(pageURL) == null ? false : true;
	}

	public boolean isContentInRole(String contentID)
	{
		Iterator<PrivilegedContent> contentIt = contents.values().iterator();
		while(contentIt != null && contentIt.hasNext())
		{
			PrivilegedContent cont = (PrivilegedContent) contentIt.next();
			if(cont != null && cont.getContentID() != null && cont.getContentID().equals(contentID))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isContentInRole(String contentID, String contentType)
	{
		Iterator<PrivilegedContent> contentIt = contents.values().iterator();
		while(contentIt != null && contentIt.hasNext())
		{
			PrivilegedContent cont = (PrivilegedContent) contentIt.next();
			if(cont != null && contentID.equals(cont.getContentID()) && contentType.equals(cont.getContentType()))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isContentViewable(String contentHierarchy, String contentType)
	{
		if(StringUtil.isValid(contentHierarchy))
		{
			String contents[] = contentHierarchy.split(",");
			for(String contentId : contents)
			{
				if(!isContentInRole(contentId, contentType))
				{
					return false;
				}
			}
		}
		return true;
	}
}
