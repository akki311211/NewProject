package com.appnetix.app.portal.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.home.builderforms.Debug;
import com.home.builderforms.StringUtil;
import com.home.builderforms.xmldao.XMLUtil;

public class PrivilegesXMLDAO
{
	private static final Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(PrivilegesXMLDAO.class);
	
	private static final List<String> privilegeType = Arrays.asList("franchisee","corporate","rfm"); 
	
	public static Privileges loadPrivileges(String location,Privileges privileges)
	{
		Element root =  XMLUtil.loadDocumentNew(location);
		if(root!= null)
		{
			try
			{
				privileges = populatePrivileges(root,privileges);
			}
			catch(Exception ex)
			{
				logger.error("Exception in populating privileges for file "+location,ex);
			}
		}
		return privileges;
	}
	
	private static Privileges populatePrivileges(Element root,Privileges privileges)
	{
		NodeList moduleList = root.getElementsByTagName("module");

		for(int outerLoop = 0; outerLoop < moduleList.getLength(); outerLoop++)
		{
			Node moduleNode = moduleList.item(outerLoop);

			HashMap<String, String> moduleAttMap = XMLUtil.getAttributesMap(moduleNode);

			String moduleID = moduleAttMap.get("id");
			String moduleCode = moduleAttMap.get("code");
			String userLevel = moduleAttMap.get("userlevel");
			String privFor = moduleAttMap.get("privilegeFor");
			ArrayList<Integer> privilegeFor = null;
			if(StringUtil.isValidNew(privFor)){
				privilegeFor = new ArrayList<Integer>();
				String privForArray[] = privFor.split(",");
				for(int j=0;j<privForArray.length;j++){
					privilegeFor.add(Integer.parseInt(privForArray[j])); 
				}
			}
			
			String defaultUrl = moduleAttMap.get("defaulturl");
			privileges.getModules().getDefaultURLs().addUrl(userLevel, moduleCode, defaultUrl);
			if(moduleNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Module module = new Module();
				module.setModuleID(moduleID);
				Element moduleElement = (Element) moduleNode;
				
				NodeList privilegeList = moduleElement.getElementsByTagName("privilege");

				NodeList descList = moduleElement.getElementsByTagName("module-description");
				String moduleDesc = (String) getValue(descList, false);
				module.setDescription(moduleDesc);

				NodeList nameList = moduleElement.getElementsByTagName("module-name");
				String moduleName = (String) getValue(nameList, false);
				module.setModuleName(moduleName);
				module.setModuleCode(moduleCode);
				module.setPrivilegeFor(privilegeFor);
				
				getPrivileges(privilegeList,privileges,module);
				
				privileges.getModules().addModule(moduleID, module);
			}
		}
		NodeList excludedModules = root.getElementsByTagName("excluded-modules");
		if(excludedModules != null)
		{
			privileges.getModules().addExcludedModules(setValues(excludedModules));
		}
		NodeList excludedPrivileges = root.getElementsByTagName("excluded-privileges");
		if(excludedPrivileges != null)
		{
			privileges.getPrivileges().addExcludedPrivileges(setValues(excludedPrivileges));
		}
		return privileges;
	}
	
	public static void getPrivileges(NodeList privilegeNodes,Privileges privileges,Module module)
	{
		ArrayList<String> privList = new ArrayList<String>();
		for(int i = 0; i < privilegeNodes.getLength(); i++)
		{
			Node privilegeNode = privilegeNodes.item(i);
			HashMap<String,String> privilegeAttMap = XMLUtil.getAttributesMap(privilegeNode);
			String privilegeID = privilegeAttMap.get("id");
			String type = privilegeAttMap.get("type");
			String privFor = privilegeAttMap.get("privilegeFor");
			ArrayList<Integer> privilegeFor = new ArrayList<Integer>();
			if("corporate".equals(type) && !StringUtil.isValidNew(privFor)){
				privFor = "1";
			}
			if(StringUtil.isValidNew(privFor)){
				String privForArray[] = privFor.split(",");
				for(int j=0;j<privForArray.length;j++){
					privilegeFor.add(Integer.parseInt(privForArray[j])); 
				}
			}
			Privilege privilege = new Privilege();
			privilege.setPrivilegeID(privilegeID);
			privList.add(privilegeID);
			privilege.setModuleID(module.getModuleID());
			privilege.setModuleCode(module.getModuleCode());
			privilege.setPrivilegeFor(privilegeFor);
		    
			int adminFlag = privilegeType.indexOf(type);
			adminFlag = adminFlag==-1?0:adminFlag;
			privilege.setAdminFlag(adminFlag);
			
			if(privilegeNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element privilegeElement = (Element) privilegeNode;
				
				NodeList privilegeNameList = privilegeElement.getElementsByTagName("privilege-name");
				String privilegeName = (String) getValue(privilegeNameList, false);
				privilege.setPrivilegeName(privilegeName);

				NodeList descList = privilegeElement.getElementsByTagName("description");
				String privDesc = (String) getValue(descList, false);
				privilege.setDescription(privDesc);

				NodeList internalTabName = privilegeElement.getElementsByTagName("internalTabName");
				String internalTabNameStr = (String) getValue(internalTabName, false);
				privilege.setInternalTabName(internalTabNameStr);
				
				NodeList depd = privilegeElement.getElementsByTagName("dependentOn");
				String depdVal = (String) getValue(depd, false);
				if(depdVal != null && depdVal.equals(""))
				{
					depdVal = "false";
				}
				privilege.setDependentOn(depdVal);

				ArrayList<String> privURLS = new ArrayList<String>();
				NodeList urlList = privilegeElement.getElementsByTagName("privilege-urls");
				String privilegeUrls = (String) getValue(urlList, false);
				StringTokenizer strTokens = new StringTokenizer(privilegeUrls, ",");
				while(strTokens.hasMoreTokens())
				{
					String str = strTokens.nextToken();
					if(str != null && !str.equals(""))
					{
						privURLS.add(str.trim());
					}
				}
				privilege.setPrivilegeURLs(privURLS);
				privileges.getPrivileges().put(privilegeID, privilege);
			}
		}
		privileges.getModules().addPrivileges(module.getModuleID(), privList);
	}

	/**
	 * Gets the value attribute of the ReportsXmlDAO class
	 * 
	 * @param list Parameter
	 * @param returnArray Parameter
	 * @return The value value
	 */
	public static Object getValue(NodeList list, boolean returnArray)
	{

		ArrayList returnList = new ArrayList();

		if(list != null)
		{
			int len = list.getLength();
			if(len > 1 && !returnArray)
			{
				Debug.println("***Warning*** Element defined more than once in XML file.Using the first value");
			}

			for(int i = 0; i < list.getLength(); i++)
			{

				Node node = list.item(i);
				Node childNode = node.getFirstChild();
				if(childNode != null)
				{
					if(childNode.getNodeType() == Node.TEXT_NODE)
					{
						String value = childNode.getNodeValue();
						if(value != null)
						{
							if(value.equals("") || value.equals("\r"))
							{
								continue;
							}else
							{
								returnList.add(value);
							}
						}else
						{
							Debug.println("Value is null");
						}
					}
				}
			}

			if(returnArray)
			{
				return returnList;
			}else
			{
				if(returnList.size() != 0)
				{
					return (String) returnList.get(0);
				}else
				{
					return "";
				}
			}
		}else
		{
			return null;
		}
	}
	
	private static Set<String> setValues(NodeList list)
	{
		Set<String> returnList = new HashSet<String>();
		int length = list.getLength();
		for(int i = 0; i < length; i++)
		{
			Node childNode = list.item(i).getFirstChild();
			if(childNode != null)
			{
				if(childNode.getNodeType() == Node.TEXT_NODE)
				{
					String value = childNode.getNodeValue();
					if(value != null)
					{	
						returnList.addAll(Arrays.asList(StringUtil.toArray(value)));
					}
				}
			}
		}
		return returnList;
	}
	
}