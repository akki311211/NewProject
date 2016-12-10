package com.home.builderforms;

import java.io.File;

import org.apache.commons.digester.Digester;

import com.appnetix.app.control.web.ParamResolver;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.tabs.TabGroup;

public class TabXMLDAO
{

	private static TabXMLDAO _instance = null;

	private TabXMLDAO()
	{

	}

	public SequenceMap getTabGroups()
	{
		SequenceMap tabGroups = (SequenceMap)ParamResolver.getResolver().get("tabGroupsMap");
		if(tabGroups == null)
		{
			tabGroups = new SequenceMap();
			ParamResolver.getResolver().put("tabGroupsMap", tabGroups);
		}

		return tabGroups;
	}

	public void setTabGroups(SequenceMap tabGroups)
	{
		ParamResolver.getResolver().put("tabGroupsMap", tabGroups);
	}

	// TAB_LOADING_ISSUE starts
	public String getModule()
	{
		String module = (String) ParamResolver.getResolver().get("module");
		if(!StringUtil.isValid(module))
		{
			module = "";
			ParamResolver.getResolver().put("module", module);
		}
		return module;
	}

	public void setModule(String module)
	{
		ParamResolver.getResolver().put("module", module);
	}

	// TAB_LOADING_ISSUE neds

	public SequenceMap getLastModifiedMap()
	{
		SequenceMap lastModifiedMap = (SequenceMap) ParamResolver.getResolver().get("lastModifiedMap");
		if(lastModifiedMap == null)
		{
			lastModifiedMap = new SequenceMap();
			ParamResolver.getResolver().put("lastModifiedMap", lastModifiedMap);
		}
		return lastModifiedMap;

	}

	public void setLastModifiedMap(SequenceMap lastModifiedMap)
	{
		ParamResolver.getResolver().put("lastModifiedMap", lastModifiedMap);
	}

	public static TabXMLDAO newInstance()
	{
		if(null == _instance)
			_instance = new TabXMLDAO();
		return _instance;
	}

	public void addTabGroup(TabGroup tabGroup)
	{
		if(!tabGroup.getSubModule().equals(""))
		{

			getTabGroups().put(getModule() + "_" + tabGroup.getSubModule(), tabGroup);// TAB_LOADING_ISSUE

		}else
			getTabGroups().put(getModule(), tabGroup);// TAB_LOADING_ISSUE
	}

	public TabGroup getTabGroup(String moduleName)
	{
		setModule((moduleName.indexOf("_") == -1) ? moduleName : moduleName.substring(0, moduleName.indexOf("_")));// TAB_LOADING_ISSUE
		if(!getTabGroups().containsKey(moduleName))
			parse();

		if(!((String) getLastModifiedMap().get(getModule())).equals(Long.toString(getTabConfig().lastModified())))// TAB_LOADING_ISSUE
			parse();

		return((TabGroup) getTabGroups().get(moduleName));
	}

	private void parse()
	{
		try
		{
			File tabConfig = getTabConfig();
			getLastModifiedMap().put(getModule(), Long.toString(tabConfig.lastModified()));// TAB_LOADING_ISSUE

			Digester frmDigester = new Digester();
			frmDigester.setNamespaceAware(true);
			frmDigester.setUseContextClassLoader(true);
			frmDigester.addRuleSet(new TabGroupRuleSet());
			frmDigester.push(this);
			frmDigester.parse(tabConfig);

		}catch(Exception e)
		{
		}
	}

	private File getTabConfig()
	{
		StringBuffer configPath = new StringBuffer(MultiTenancyUtil.getTenantConstants().XML_DIRECTORY).append("/tabs/").append(getModule()).append("Tabs.xml");// TAB_LOADING_ISSUE
		return new File(configPath.toString());
	}

}
