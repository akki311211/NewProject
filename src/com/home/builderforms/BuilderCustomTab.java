/*
 *@author: Mohit Sharma
 *Created for ENH_MODULE_CUSTOM_TABS
 *P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 */
package com.home.builderforms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.appnetix.app.control.web.multitenancy.resources.constants.BuilderFormConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;

import org.apache.log4j.Logger;

import com.home.BuilderFormFieldNames;
import com.home.ExportModules;
import com.appnetix.app.util.BaseUtils;
import com.appnetix.app.util.Constants;
import com.appnetix.app.util.DBUtil;
import com.appnetix.app.util.FieldNames;
import com.appnetix.app.util.ModuleUtil;
import com.appnetix.app.util.ModuleUtil.MODULE_NAME;
import com.appnetix.app.util.SequenceMap;
import com.appnetix.app.util.StringUtil;
import com.appnetix.app.util.TabUtil;
import com.appnetix.app.util.database.FieldMappings;
import com.appnetix.app.util.database.ForeignTable;
import com.appnetix.app.util.database.LinkField;
import com.home.builderforms.Info;
import com.appnetix.app.util.tabs.Tab;
import com.appnetix.app.util.xmldao.TableXMLDAO;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class BuilderCustomTab 
{
	static private final String DB_TABLE = "db-table";

	static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BuilderCustomTab.class);
	
	public static BuilderCustomTab newInstance()
	{
        BuilderFormConstants _constants = MultiTenancyUtil.getTenantFormBuilderConstants();
		if(_constants.tabGeneratorMap == null)
		{
            _constants.tabGeneratorMap	= new SequenceMap();
		}
		return new BuilderCustomTab();
	}
	
	public SequenceMap getAllTabsMap()
	{		
		String location 			= getCustomTabConfig();
		File customTabFile 			= new File(location);
		long lastModified 			= customTabFile.lastModified();
        BuilderFormConstants _constants = MultiTenancyUtil.getTenantFormBuilderConstants();
		try
		{
			if(_constants.lastModified < lastModified)
			{
				Object obj = TableXMLDAO.getBuilderFileXMLAttr(location, BuilderFormFieldNames.MODULE_TAB, BuilderFormFieldNames.TAB_NAME, "Info");
				if(obj instanceof SequenceMap) 
				{
                    _constants.tabGeneratorMap = (SequenceMap)obj;
                    _constants.lastModified = lastModified;
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in getAllTabsMap ::::"+e);
		}
		
		return _constants.tabGeneratorMap;
	}
	
	public SequenceMap getCustomTab(String moduleName)
	{
		return getCustomTab(moduleName,null);
	}
	
	public SequenceMap filterActiveTabs(String moduleName,SequenceMap tabsMap)
	{
		String subModuleName=null;
		//P_Enh_Mu-Entity_FormGenerator  starts
		if("mu".equals(moduleName)){
			moduleName="fim";
			subModuleName="mu";
		}else if("area".equals(moduleName)){
			moduleName="fim";
			subModuleName="area";
		}//P_Enh_Mu-Entity_FormGenerator ends
		SequenceMap customTabs = getCustomTab(moduleName,subModuleName,true);
		if(customTabs!=null && tabsMap!=null)
		{
			HashMap hm = new HashMap();
			Object tabArr[] = tabsMap.keys().toArray();
			String key = null;
			Info tabInfo = null;
			String tableAnchor = null;
			for (Object tabArrVar:tabArr) 
			{
				key = (String)tabArrVar;
				tabInfo = (Info)tabsMap.get(key);
				tableAnchor = (tabInfo!=null)?tabInfo.get(FieldNames.TABLE_ANCHOR):FieldNames.EMPTY_STRING;
				if(!customTabs.containsKey(tableAnchor))
					tabsMap.remove(key);
			}
		}
		return tabsMap;
	}
			
	public SequenceMap getCustomTab(String moduleName,String submoduleName)
	{
		return getCustomTab(moduleName,submoduleName,false);
	}
	public SequenceMap getCustomTab(String moduleName,String submoduleName,boolean onlyActive)
	{
		SequenceMap tabGeneratorMap = null;
		SequenceMap customTabMap 	= null;
		Info tabInfo 				= null;
		String key					= null;
		int count					= 0;

		tabGeneratorMap = getAllTabsMap();
		//BOEFLY_INTEGRATION : START
		if("fs".equals(moduleName) && (!StringUtil.isValidNew(submoduleName) || "captivateAndCallCenter".equals(submoduleName))) //PP_changes
		{
			submoduleName = "lead";
		}else if("cm".equals(moduleName)){//P_CM_B_60162
			submoduleName = "contact";
		}else if(ModuleUtil.MODULE_NAME.NAME_ACCOUNT.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName)){
			submoduleName=moduleName;
		}else if(!StringUtil.isValidNew(submoduleName) && "fim".equals(moduleName)){
			submoduleName="franchisee";
		}else if(!StringUtil.isValidNew(submoduleName) && ("mu".equals(moduleName) || "fimmu".equals(moduleName))){
			moduleName="fim";
			submoduleName="mu";
		}else if(!StringUtil.isValidNew(submoduleName) && ("area".equals(moduleName) || "fimarea".equals(moduleName))){
			moduleName="fim";
			submoduleName="area";
		}
		//BOEFLY_INTEGRATION : END
		if(submoduleName!=null)
		{
			submoduleName = TabUtil.getSubModuleMapping(moduleName,submoduleName);
		}
				
		if(tabGeneratorMap != null && tabGeneratorMap.size() > 0)
		{
			customTabMap	= new SequenceMap();
			Object tabArr[] = tabGeneratorMap.keys().toArray();
			String displayName = null;
			for (Object tabArrVar:tabArr) 
			{
				key = (String)tabArrVar;
				tabInfo = (Info)tabGeneratorMap.get(key);
				if(tabInfo != null && "-1".equals(moduleName))
				{
                    if(!ModuleUtil.fsImplemented() && "fsBqual".equals(key)) {
                        continue;
                    }
					customTabMap.put(key,tabInfo);
				}
				else if(tabInfo != null && onlyActive && !"Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)))
				{
					continue;
				}
				else if(moduleName != null && tabInfo!=null && moduleName.equals(tabInfo.getString(FieldNames.MODULE)))
				{
					if(submoduleName!=null)//for valid submodule
					{
						if(submoduleName.contains(tabInfo.getString(FieldNames.SUB_MODULE)))
						{
							count++;
							tabInfo.set(BuilderFormFieldNames.NO, count+".");
							customTabMap.put(key,tabInfo);
						}
					}
					else
					{
						count++;
						tabInfo.set(BuilderFormFieldNames.NO, count+".");
						customTabMap.put(key,tabInfo);
					}
				}
			}
		}
		return customTabMap;
	}
	/**
	 * 
	 * @param moduleName
	 * @param subModuleName
	 * @param tableAnchor
	 * @return Tab object having specified anchor
	 */
	public Tab getCustomTabObject(String moduleName,String subModuleName,String tableAnchor){
		return getCustomTabObject(moduleName, subModuleName, tableAnchor, true);
	}
	public Tab getCustomTabObject(String moduleName,String subModuleName,String tableAnchor,boolean onlyActive){
		Tab returnTab = null;
		if(StringUtil.isValid(moduleName) && StringUtil.isValid(tableAnchor)){
			try{
				SequenceMap customTabMap = getCustomTab(moduleName,subModuleName);
				Info returnTabInfo = null;
				if(customTabMap != null && customTabMap.size() > 0)
				{
					String nextKey = null;
					for(Iterator<?> cusTabIterator = customTabMap.keys().iterator() ; cusTabIterator.hasNext();)
					{
						nextKey = String.valueOf(cusTabIterator.next());
						if(tableAnchor.equals(nextKey)){
							returnTabInfo 	= (Info)customTabMap.get(nextKey);
							if(onlyActive && !"Y".equals(returnTabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE))){
								continue;
							}
							returnTab = new Tab();
							returnTab.setTabID(returnTabInfo.getString(BuilderFormFieldNames.TAB_NAME));
							returnTab.setDisplayName(returnTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
							returnTab.setCondition(returnTabInfo.getString(BuilderFormFieldNames.CONDITION));
							returnTab.setHref(returnTabInfo.getString(BuilderFormFieldNames.HREF));
							returnTab.setInternalName(returnTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
							returnTab.setPath(returnTabInfo.getString(BuilderFormFieldNames.PATH));
							returnTab.setPrivilegeUrl(returnTabInfo.getString(BuilderFormFieldNames.PRIVILEGE_URL));
							returnTab.setPrivileged("false");
							returnTab.setIsCustom("Y"); 
						}
					}
				}
			}catch(Exception ex){
				logger.error("Error in getCustomTabObject===",ex);
			}
		}
		return returnTab;
	}
	
	public HashMap getAllCustomTabMappings()
	{
		//Load Table Mappings
		HashMap customMappings = null;
		SequenceMap tabGeneratorMap = getAllTabsMap();
		
		try
		{
			if(tabGeneratorMap != null && tabGeneratorMap.size() > 0)
			{
				customMappings 				= new HashMap();;
				Info tabInfo 			= null;
				String anchor				= null;
				String fileLocation			= null;
				
				Object tabArr[] = tabGeneratorMap.keys().toArray();
				
				for (Object tabArrVar:tabArr) 
				{
					tabInfo = (Info)tabGeneratorMap.get(tabArrVar);
					
					if(tabInfo != null)
					{
						anchor		=	tabInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR);
						fileLocation 	=	tabInfo.getString(BuilderFormFieldNames.FILE_LOCATION);
						
						if(StringUtil.isValid(anchor) && StringUtil.isValid(fileLocation))
						{
							customMappings.put(anchor,fileLocation);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in getAllTabMappings ::::"+e);
		}
		return customMappings;
	}
	
	public void processForeignMapsWithCustomTab(String moduleName,SequenceMap foreignTablesMap,SequenceMap foreignTablesExportMap)
	{
		SequenceMap tabGeneratorMap = getAllTabsMap();
		String tableSubModule="";
		Info tabInfo 			= null;
		
		try
		{
			if(moduleName!=null && moduleName.contains("_")){//P_Enh_Mu-Entity_FormGenerator
				tableSubModule=moduleName.split("_")[1];
				moduleName=moduleName.split("_")[0];
			}
			if(tabGeneratorMap != null && tabGeneratorMap.size() > 0)
			{
				Object tabArr[] 			= tabGeneratorMap.keys().toArray();
				String exportable 			= null;
				String subModule=null;
				SequenceMap linkFieldMap 	= null;
				String name					= null;
				//P_SCH_ENH_008 Starts
				boolean showSchedulerCustomTabs=false;
				if(ModuleUtil.schedulerImplemented() && MODULE_NAME.NAME_CM.equals(moduleName))
					showSchedulerCustomTabs=true;
				boolean isSchedulerCustomTab=false;
				//P_SCH_ENH_008 Ends
				
				for (Object tabArrVar:tabArr) 
				{
					tabInfo = (Info)tabGeneratorMap.get(tabArrVar);
					if(StringUtil.isValidNew(tabInfo.getString(FieldNames.SUB_MODULE))){//P_Enh_Mu-Entity_FormGenerator
						subModule=tabInfo.getString(FieldNames.SUB_MODULE);
					}
					//P_SCH_ENH_008 Starts
					// process custom tabs of scheduler module along with cm.
					if(showSchedulerCustomTabs)
						isSchedulerCustomTab=MODULE_NAME.NAME_SCHEDULER.equals(tabInfo.getString(FieldNames.MODULE));
					//if(tabInfo != null && "Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)) && tabInfo.getString(FieldNames.MODULE).equals(moduleName))
					if(StringUtil.isValidNew(tableSubModule) && !subModule.equals(tableSubModule)){//P_Enh_Mu-Entity_FormGenerator
						continue;
					}
					if(tabInfo != null && "Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)) && (tabInfo.getString(FieldNames.MODULE).equals(moduleName) || isSchedulerCustomTab ))//P_SCH_ENH_008 Ends
					{
						exportable 	= tabInfo.getString(BuilderFormFieldNames.TAB_IS_EXPORTABLE);
						name		= tabInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR);
						linkFieldMap = new SequenceMap();
						if(MODULE_NAME.NAME_FIM.equals(moduleName))
						{
							if("area".equals(subModule)){//P_Enh_Mu-Entity_FormGenerator starts
								linkFieldMap.put(FieldNames.AREA_ID, new LinkField(FieldNames.AREA_ID, FieldNames.AREA_INFO_ID, null));
							}else if("mu".equals(subModule)){
								linkFieldMap.put(FieldNames.MU_ID, new LinkField(FieldNames.FRANCHISE_OWNER_ID, FieldNames.MU_ID, null));	
							}else{
								linkFieldMap.put(FieldNames.ENTITY_ID, new LinkField(FieldNames.FRANCHISEE_NO, FieldNames.ENTITY_ID, null));
							}//P_Enh_Mu-Entity_FormGenerator ends
						}
						else if(MODULE_NAME.NAME_FS.equals(moduleName))
						{
							linkFieldMap.put(FieldNames.LEAD_ID, new LinkField(FieldNames.LEAD_ID, FieldNames.LEAD_ID, null));
						}
						else if(MODULE_NAME.NAME_CM.equals(moduleName))
						{
							linkFieldMap.put(FieldNames.CONTACT_ID, new LinkField(FieldNames.CONTACT_ID, FieldNames.CONTACT_ID, null));
						}
						else if(MODULE_NAME.NAME_ACCOUNT.equals(moduleName))
						{
							linkFieldMap.put(FieldNames.COMPANY_ID, new LinkField(FieldNames.COMPANY_ID, FieldNames.COMPANY_ID, null));
						}
						else if(MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName))
						{
							linkFieldMap.put(FieldNames.OPPORTUNITY_ID, new LinkField(FieldNames.OPPORTUNITY_ID, FieldNames.OPPORTUNITY_ID, null));
						}
						else if(MODULE_NAME.NAME_LEAD.equals(moduleName))  //P_CM_LEAD
						{
							linkFieldMap.put(FieldNames.LEAD_ID, new LinkField(FieldNames.LEAD_ID, FieldNames.LEAD_ID, null));
						}
						
						foreignTablesMap.put(name, new ForeignTable( name, linkFieldMap, exportable));
						
						if(StringUtil.isValid(exportable) && "true".equals(exportable)) 
						{
							foreignTablesExportMap.put(name, new ForeignTable(name, linkFieldMap));
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in processMapWithCustomTab ::::"+e);
		}
	}
	
	public void processCustomExportTabData(SequenceMap smModuleMap, String moduleName,int moduleId){
		 processCustomExportTabData(smModuleMap,moduleName,moduleId,"");
	}
	//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB starts
	public void processCustomExportTabData(SequenceMap smModuleMap, String moduleName,int moduleId,String fimFlag){
		 processCustomExportTabData(smModuleMap,moduleName,moduleId,fimFlag,null);
	}
	
	public void processCustomExportTabData(SequenceMap smModuleMap, String moduleName,int moduleId,String fimFlag,SequenceMap roleIdMap)//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB ends
	{
		try
		{
			SequenceMap tabGeneratorMap = getAllTabsMap();
			if(tabGeneratorMap != null && tabGeneratorMap.size() > 0)
			{
				Object tabArr[] 			= tabGeneratorMap.keys().toArray();
				Info tabInfo 			= null;
				ExportModules module		= (ExportModules)smModuleMap.get(moduleId);
				SequenceMap	moduleMap		= null;
				ExportModules childModule	= null;
				FieldMappings fieldMappings	= null;
				boolean	containsActive		= true;
				String subModule=null;
				boolean breakFlag=false;//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
				//P_SCH_ENH_008 Starts
				boolean showSchedulerCustomTabs=false;
				if(ModuleUtil.schedulerImplemented() && MODULE_NAME.NAME_CM.equals(moduleName))
					showSchedulerCustomTabs=true;
				boolean isSchedulerCustomTab=false;
				//P_SCH_ENH_008 Ends
				if(module != null)
				{
					moduleMap	= module.getSubModuleMap();
							
					for (Object tabArrVar:tabArr) 
					{
						tabInfo 	= (Info)tabGeneratorMap.get(tabArrVar);
						childModule	= new ExportModules();
						//P_SCH_ENH_008 Starts
						// process custom tabs of scheduler module along with cm.
						if(showSchedulerCustomTabs)
							isSchedulerCustomTab=MODULE_NAME.NAME_SCHEDULER.equals(tabInfo.getString(FieldNames.MODULE));
						//if(tabInfo != null && tabInfo.getString(FieldNames.MODULE).equals(moduleName))
						if(tabInfo != null && "Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)) && (tabInfo.getString(FieldNames.MODULE).equals(moduleName) || isSchedulerCustomTab ))//P_SCH_ENH_008 Ends
						{
							
							if("true".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_EXPORTABLE)) && "Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)))
							{
								fieldMappings		= DBUtil.getInstance().getFieldMappings(tabInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR));
								containsActive		=  fieldMappings.activeFieldExist();
								if(StringUtil.isValidNew(tabInfo.getString(FieldNames.SUB_MODULE))){//P_Enh_Mu-Entity_FormGenerator
									subModule=tabInfo.getString(FieldNames.SUB_MODULE);
								}
								if(!containsActive)
								{
									moduleMap.remove(tabInfo.getString(BuilderFormFieldNames.TAB_NAME));
									continue;
								}
								//Bug_41298_Starts
								if(("1".equals(fimFlag) && !"area".equals(subModule)) || ("0".equals(fimFlag) && !"franchisee".equals(subModule)) || (("2".equals(fimFlag) || "3".equals(fimFlag)) && !"mu".equals(subModule))){//P_Enh_Mu-Entity_FormGenerator
									continue;
								}
								//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB starts
								if(MODULE_NAME.NAME_FS.equals(moduleName) || MODULE_NAME.NAME_FIM.equals(moduleName) || MODULE_NAME.NAME_CM.equals(moduleName)){
									breakFlag=BaseUtils.isRoleHasPrivilege(tabInfo, roleIdMap,BuilderFormFieldNames.CAN_VIEW_ROLES);
								}else{
									breakFlag = true;
								}
								if(!breakFlag){
									continue;
								}
								//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB ends
								//Bug_41298_Starts 
								/*if("2".equals(fimFlag) || "3".equals(fimFlag)){
									continue;
								}*/
								//Bug_41298_Ends
								childModule.setModuleName(tabInfo.getString(BuilderFormFieldNames.TAB_NAME));
								childModule.setId(tabInfo.getString(BuilderFormFieldNames.TAB_NAME));
								childModule.setModuleDisplayName(tabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
								childModule.setModuleLevel("1");
								childModule.setInternalTabName(tabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
								childModule.setModuleMainTable(tabInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR));
								//childModule.setPrivilege(tabInfo.getString(BuilderFormFieldNames.PRIVILEGE_URL));
								if(moduleMap.containsKey(tabInfo.getString(BuilderFormFieldNames.TAB_NAME)))
									moduleMap.remove(tabInfo.getString(BuilderFormFieldNames.TAB_NAME));
									
									moduleMap.put(tabInfo.getString(BuilderFormFieldNames.TAB_NAME), childModule);
							}
							else if("false".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_EXPORTABLE)) || "N".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)))//P_CM_B_38259 
							{
								moduleMap.remove(tabInfo.getString(BuilderFormFieldNames.TAB_NAME));
							}
						} else if(tabInfo != null && "N".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE))) { //P_B_44971 starts
							moduleMap.remove(tabInfo.getString(BuilderFormFieldNames.TAB_NAME)); //removing custom tab which is not active
						}//P_B_44971 ends
					}
					
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in processMapWithCustomTab ::::"+e);
		}
		
	}
	//BOEFLY_INTEGRATION : START
	public void addCustomTabs(String moduleName,ArrayList list){
		addCustomTabs(moduleName,null,list);
	}
	//BOEFLY_INTEGRATION : END
	
	public boolean addCustomTabs(String moduleName,String submoduleName,ArrayList list)
	{
		if("inDev".equals(submoduleName) || "franchiseewithoutsc".equals(submoduleName)) {//signal-20150601-011
			submoduleName = "franchisees";
		}
		SequenceMap customTabMap	= getCustomTab(moduleName,submoduleName);
		Info customTabInfo			= null;
		Tab	customTab				= null;
		//ArrayList configuredTabs	= UserTabConfigUtil.configuredCustomTab(moduleName);
		boolean isDataPresent = false;
		if(customTabMap != null && customTabMap.size() > 0)
		{
			for(Iterator cusTabIterator = customTabMap.keys().iterator() ; cusTabIterator.hasNext();)
			{
				customTabInfo 	= (Info)customTabMap.get(cusTabIterator.next());

				if("Y".equals(customTabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)))
				{
					/*if("fim".equals(customTabInfo.getString(FieldNames.MODULE)))
					{
						if(!configuredTabs.contains(customTabInfo.getString(BuilderFormFieldNames.TAB_NAME)))
						{
							continue;
						}
					}*/
					customTab 		= new Tab();
					customTab.setTabID(customTabInfo.getString(BuilderFormFieldNames.TAB_NAME));
					customTab.setDisplayName(customTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
					customTab.setCondition(customTabInfo.getString(BuilderFormFieldNames.CONDITION));
					customTab.setHref(customTabInfo.getString(BuilderFormFieldNames.HREF));
					customTab.setInternalName(customTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
					customTab.setPath(customTabInfo.getString(BuilderFormFieldNames.PATH));
					customTab.setPrivilegeUrl(customTabInfo.getString(BuilderFormFieldNames.PRIVILEGE_URL));
					customTab.setPrivileged("false");
					customTab.setIsCustom("Y");
					customTab.setCanViewRoles(customTabInfo.getString(BuilderFormFieldNames.CAN_VIEW_ROLES));//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
					if(StringUtil.isValidNew(customTabInfo.getString(BuilderFormFieldNames.TAB_ORDER))){//BB-20150203-259 (Tab Re-ordering changes)
					customTab.setTabOrder(customTabInfo.getString(BuilderFormFieldNames.TAB_ORDER));
					}
					list.add(customTab);
					isDataPresent = true;
				}
				
			}
		}
		return isDataPresent;
	}
	
	private String getCustomTabConfig()
	{
			StringBuffer configPath = new StringBuffer(MultiTenancyUtil.getTenantConstants().XML_DIRECTORY).append("tables/admin/").append(BuilderConstants.TAB_MAPPING_XML);
			return configPath.toString();
	}
	
	//BOEFLY_INTEGRATION : START
	/**
	 * 
	 * @param moduleName
	 * @param subModuleName
	 * @param tableAnchor
	 * @return Tab object having specified anchor
	 */
	public Tab getCustomTabObject(String moduleName,String tableAnchor){
		return getCustomTabObject(moduleName, tableAnchor, true);
	}
	public Tab getCustomTabObject(String moduleName,String tableAnchor,boolean onlyActive){
		Tab returnTab = null;
		if(StringUtil.isValid(moduleName) && StringUtil.isValid(tableAnchor)){
			try{
				SequenceMap customTabMap = getCustomTab(moduleName);
				Info returnTabInfo = null;
				if(customTabMap != null && customTabMap.size() > 0)
				{
					String nextKey = null;
					for(Iterator<?> cusTabIterator = customTabMap.keys().iterator() ; cusTabIterator.hasNext();)
					{
						nextKey = String.valueOf(cusTabIterator.next());
						if(tableAnchor.equals(nextKey)){
							returnTabInfo 	= (Info)customTabMap.get(nextKey);
							if(onlyActive && !"Y".equals(returnTabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE))){
								continue;
							}
							returnTab = new Tab();
							returnTab.setTabID(returnTabInfo.getString(BuilderFormFieldNames.TAB_NAME));
							returnTab.setDisplayName(returnTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
							returnTab.setCondition(returnTabInfo.getString(BuilderFormFieldNames.CONDITION));
							returnTab.setHref(returnTabInfo.getString(BuilderFormFieldNames.HREF));
							returnTab.setInternalName(returnTabInfo.getString(BuilderFormFieldNames.TAB_DISPLAY));
							returnTab.setPath(returnTabInfo.getString(BuilderFormFieldNames.PATH));
							returnTab.setPrivilegeUrl(returnTabInfo.getString(BuilderFormFieldNames.PRIVILEGE_URL));
							returnTab.setPrivileged("false");
							returnTab.setIsCustom("Y"); 
						}
					}
				}
			}catch(Exception ex){
				logger.error("Error in getCustomTabObject===",ex);
			}
		}
		return returnTab;
	}
	//BOEFLY_INTEGRATION : END
	public SequenceMap getCustomTabForFilter(String moduleName,String submoduleName,boolean onlyActive)
	{
		SequenceMap tabGeneratorMap = null;
		SequenceMap customTabMap 	= null;
		Info tabInfo 				= null;
		String key					= null;
		int count					= 0;

		HashSet moduleSet=new HashSet();
		if(StringUtil.isValidNew(moduleName))
			moduleSet=new HashSet(StringUtil.convertToArrayList(moduleName.split(",")));
		HashSet submoduleSet=null;
		if(StringUtil.isValid(submoduleName))
			submoduleSet=new HashSet(StringUtil.convertToArrayList(submoduleName.split(",")));
		else
			submoduleSet=new HashSet();
		tabGeneratorMap = getAllTabsMap();
		//BOEFLY_INTEGRATION : START
		if(moduleSet.contains("fs") && (submoduleSet.size()==0 || submoduleSet.contains("captivateAndCallCenter"))) //PP_changes
		{
			submoduleSet.add("lead");
		}
		if(moduleSet.contains("cm")){//P_CM_B_60162
			submoduleSet.add("contact");
		}
		if(moduleSet.contains(ModuleUtil.MODULE_NAME.NAME_ACCOUNT)){
			submoduleSet.add(ModuleUtil.MODULE_NAME.NAME_ACCOUNT);
		}
		if(moduleSet.contains(ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY)){
			submoduleSet.add(ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY);
		}
		if(submoduleSet.size()==0 && moduleSet.contains("fim")){
			submoduleSet.add("franchisee");
		}
		if(submoduleSet.size()==0 && (moduleSet.contains("mu") || moduleSet.contains("fimmu"))){
			moduleSet.remove("mu");
			moduleSet.remove("fimmu");
			moduleSet.add("fim");
			submoduleSet.add("mu");
		}
		if(submoduleSet.size()==0 && (moduleSet.contains("area") || moduleSet.contains("fimarea"))){
			moduleSet.remove("area");
			moduleSet.remove("fimarea");
			moduleSet.add("fim");
			submoduleSet.add("area");
		}
		//BOEFLY_INTEGRATION : END
		//P_ENH_FC-136 start
		/*if(submoduleName!=null)
		{
			submoduleName = TabUtil.getSubModuleMapping(moduleName,submoduleName);
		}*/
		//P_ENH_FC-136 end
				
		if(tabGeneratorMap != null && tabGeneratorMap.size() > 0)
		{
			customTabMap	= new SequenceMap();
			Object tabArr[] = tabGeneratorMap.keys().toArray();
			String displayName = null;
			for (Object tabArrVar:tabArr) 
			{
				key = (String)tabArrVar;
				tabInfo = (Info)tabGeneratorMap.get(key);
				if(tabInfo != null && moduleSet.size()==0)
				{
                    if(!ModuleUtil.fsImplemented() && "fsBqual".equals(key)) {
                        continue;
                    }
					customTabMap.put(key,tabInfo);
				}
				else if(tabInfo != null && onlyActive && !"Y".equals(tabInfo.getString(BuilderFormFieldNames.TAB_IS_ACTIVE)))
				{
					continue;
				}
				else if(moduleSet.size() != 0 && tabInfo!=null && moduleSet.contains(tabInfo.getString(FieldNames.MODULE)))
				{
					if(submoduleSet.size()!=0)//for valid submodule
					{
						if(submoduleSet.contains(tabInfo.getString(FieldNames.SUB_MODULE)))
						{
							count++;
							tabInfo.set(BuilderFormFieldNames.NO, count+".");
							customTabMap.put(key,tabInfo);
						}
					}
					else
					{
						count++;
						tabInfo.set(BuilderFormFieldNames.NO, count+".");
						customTabMap.put(key,tabInfo);
					}
				}
			}
		}
		return customTabMap;
	}	
}
