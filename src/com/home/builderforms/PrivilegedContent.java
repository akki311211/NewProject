/*
 * Copyright (c) 2001  Webrizon eSolutions Pvt. Ltd.
 * B-31, Sector 5, NOIDA. 201301, India.
 * All Rights Reserved
 *
 * This Class retreives details of All Franchisee Users
 *
 * @author Pranav
 *
 * @version 1.0
 * @date 10-01-2002
 */

// Filename: UserRole.java
package com.home.builderforms;

import java.io.Serializable;
import java.util.*;

/**
*	This Class is a representation of the User's role.
*/
public class PrivilegedContent implements Serializable {

	private String privilegedContentID;
	private String contentID;
	private String contentType;
	private String contentName;
	private String contentParentName;
	private HashMap subContents;
	private int isAdmin;

	public String getPrivilegedContentID()
	{
		return privilegedContentID;
	}

	public String getContentID()
	{
		return contentID;
	}

	public String getContentType()
	{
		return contentType;
	}

	public String getContentName()
	{
		return contentName;
	}

	public String getContentParentName()
	{
		return contentParentName;
	}

	public HashMap getSubContents()
	{
		if (subContents == null)
		{
			subContents = new HashMap();
		}

		return subContents;
	}

	public int isAdmin()
	{
		return isAdmin;
	}
	
	public void setAdminFlag(int flag)
	{
		this.isAdmin			= flag;
	}

	public void setPrivilegedContentID(String id)
	{
		this.privilegedContentID		= id;
	}

	public void setContentID(String id)
	{
		this.contentID		= id;
	}

	public void setContentType(String type)
	{
		this.contentType		= type;
	}

	public void setContentName(String name)
	{
		this.contentName		= name;
	}

	public void setContentParentName(String name)
	{
		this.contentParentName		= name;
	}
	
	public void setSubContents(HashMap contents)
	{
		subContents		= contents;
	}

	public String getSubPrivilegedContentIDsCS()
	{
		String cIDs = privilegedContentID;
		if (subContents != null)
		{
			Iterator it = subContents.values().iterator();
			while (it.hasNext())
			{
				PrivilegedContent subContent = (PrivilegedContent)it.next();
				cIDs = cIDs + "," + subContent.getSubPrivilegedContentIDsCS();
			}
		}
		return cIDs;
	}
		

}
