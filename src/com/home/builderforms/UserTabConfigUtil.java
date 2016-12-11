/**
 *  ----------------------------------------------------------------------------------------------------------------------------------------------
 *  Version No.	                Date		      By			    Function Changed          Comments
 *  -----------------------------------------------------------------------------------------------------------------------------------------------
 *  FIM_TABS_PRIVILEGE_CHECK    14/05/2012        Nishant Arora                               PrivilegeUrl Check Regarding To Different Tabs In FIM
 *  BBEH_FOR_GETRESULT_METHOD  24/05/2013      Rohit Jain    For pass Null parameter in get result method instead of blank Object
 *  ----------------------------------------------------------------------------------------------------------------------------------------------
 */
package com.home.builderforms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.portal.export.ExportModules;
import com.appnetix.app.portal.role.Privilege;
import com.home.builderforms.Constants;
import com.home.builderforms.FieldNames;
import com.home.builderforms.MasterEntities;
//import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
/*
-----------------------------------------------------------------------------------------------------------
Version No.		  Date		 By	     Against	  Function Added				Comments
-----------------------------------------------------------------------------------------------------------
P_B_FIM_68824   6 Jan 2011      Neeti Solanki                              for changing the display name of center info with respect of franchisee/area/mu
-----------------------------------------------------------------------------------------------------------
*/

import com.home.builderforms.xmldao.TabXMLDAO;

public class UserTabConfigUtil 
{
	public static List<UserTabConfig> getUserTabConfig(String moduleName)
	{
		List<UserTabConfig> list = new ArrayList<UserTabConfig>();
		
		ResultSet result = QueryUtil.getResult("SELECT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME,KEY_NAME,IS_CUSTOM FROM USER_TAB_CONFIG WHERE MODULE = ?", new String[]{moduleName});//ENH_MODULE_CUSTOM_TABS
		if(result != null)
		{
			while (result.next())
			{
				UserTabConfig userTabConfig = new UserTabConfig();
				
				userTabConfig.setUserTabId(result.getString("USER_TAB_ID"));
				userTabConfig.setRowNo(result.getString("ROW_NO"));
				userTabConfig.setModule(result.getString("MODULE"));
				userTabConfig.setSubModule(result.getString("SUB_MODULE"));
				userTabConfig.setHref(result.getString("HREF"));
				userTabConfig.setName(result.getString("NAME"));
				userTabConfig.setInternalName(result.getString("INTERNAL_NAME"));
				
			//ENH_MODULE_CUSTOM_TABS starts
				userTabConfig.setKeyName(result.getString("KEY_NAME"));
				userTabConfig.setIsCustom(result.getString("IS_CUSTOM"));
			//ENH_MODULE_CUSTOM_TABS ends
				list.add(userTabConfig);
			}
		}
		QueryUtil.releaseResultSet(result);
		return list;
	}
	//P_B_fim_64233 starts by neeti solanki
	public static List<UserTabConfig> getTabsForModule(String moduleName,String submoduleName)
	{
		List<UserTabConfig> list = new ArrayList<UserTabConfig>();
	//ENH_MODULE_CUSTOM_TABS
		String query=new String("SELECT DISTINCT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME, KEY_NAME, IS_CUSTOM FROM USER_TAB_CONFIG WHERE MODULE='"+moduleName+"' AND SUB_MODULE='"+submoduleName+"'");
		ResultSet result = QueryUtil.getResult(query,null);
		
		if(result != null)
		{
			while (result.next())
			{
				UserTabConfig userTabConfig = new UserTabConfig();
				
				userTabConfig.setUserTabId(result.getString("USER_TAB_ID"));
				userTabConfig.setRowNo(result.getString("ROW_NO"));
				userTabConfig.setModule(result.getString("MODULE"));
				userTabConfig.setSubModule(result.getString("SUB_MODULE"));
				userTabConfig.setHref(result.getString("HREF"));
				userTabConfig.setName(result.getString("NAME"));
				userTabConfig.setInternalName(result.getString("INTERNAL_NAME"));
				
			//ENH_MODULE_CUSTOM_TABS starts
				userTabConfig.setKeyName(result.getString("KEY_NAME"));
				userTabConfig.setIsCustom(result.getString("IS_CUSTOM"));
			//ENH_MODULE_CUSTOM_TABS ends
			//	if(!"Local Listing".equalsIgnoreCase(result.getString("NAME")))
				list.add(userTabConfig);
			}
		}
		QueryUtil.releaseResultSet(result);
		return list;
	}
	//P_B_fim_64233 starts by neeti solanki
	
	public static SequenceMap getUserTabConfig()
	{
		SequenceMap sequenceMap = new SequenceMap();
		
		ResultSet result = QueryUtil.getResult("SELECT DISTINCT MODULE FROM USER_TAB_CONFIG",null);
		
		if(result != null)
		{
			while (result.next())
			{
				String module = result.getString("MODULE");
				sequenceMap.put(module, getUserTabConfig(module));
			}
		}
		QueryUtil.releaseResultSet(result);
		return sequenceMap;
	}
        //P_CRPT_B_08082011
	public static SequenceMap getUserTabSubModuleConfig(String subModule)
	{
		return getUserTabSubModuleConfig(subModule, false);
	}
	public static SequenceMap getUserTabSubModuleConfig(String subModule,boolean isMuEntity)
	{
            
                SequenceMap subModuleMap = new SequenceMap();
		List<UserTabConfig> list = new ArrayList<UserTabConfig>();
		ResultSet result = null;
		if(isMuEntity) {
			result = QueryUtil.getResult("SELECT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME FROM USER_TAB_CONFIG WHERE SUB_MODULE = '"+subModule.toLowerCase()+"'", null);
		} else {
			 result = QueryUtil.getResult("SELECT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME FROM USER_TAB_CONFIG WHERE SUB_MODULE LIKE '"+subModule.toLowerCase()+"%'", null);
		}
		if(result != null)
		{
			while (result.next())
			{
				UserTabConfig userTabConfig = new UserTabConfig();
				
				userTabConfig.setUserTabId(result.getString("USER_TAB_ID"));
				userTabConfig.setRowNo(result.getString("ROW_NO"));
				userTabConfig.setModule(result.getString("MODULE"));
				userTabConfig.setSubModule(result.getString("SUB_MODULE"));
				userTabConfig.setHref(result.getString("HREF"));
				userTabConfig.setName(result.getString("NAME"));
				userTabConfig.setInternalName(result.getString("INTERNAL_NAME"));
				
				list.add(userTabConfig);
			}
		}
		subModuleMap.put(subModule, list);
		QueryUtil.releaseResultSet(result);
		return subModuleMap;
	}
	
	
	public static boolean isPrivilege(SequenceMap userTabConfigMap, String moduleID, Privilege priv)
	{
		boolean isPrivilege = false;
		List<UserTabConfig> userTabConfigList = null;		
		
		if (userTabConfigMap == null || (priv.getInternalTabName() != null &&(priv.getInternalTabName().equalsIgnoreCase("ignore") || priv.getInternalTabName().equalsIgnoreCase("false"))))
		{
			isPrivilege = true;
			return isPrivilege;
		}
		
		if (moduleID.equals("53") || moduleID.equals("5000"))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{
				if (!StringUtil.isValid(priv.getInternalTabName()) || userTabConfig.getInternalName().equals(priv.getInternalTabName()))
				{
					isPrivilege = true;													
				}
			}
		}
		else
		{
			isPrivilege = true;
		}
		
		return isPrivilege;
	}
	
	//P_B_FIM_64241 starts by neeti solanki
	/**
	 * @param userTabConfigMap - Map of tabNames
	 * @param moduleName - fim
	 * @param subModuleName - franchisee
	 * @param href - Action of tabs
	 * @return
	 * this method compares the tab names with configure fim tabs
	 */
	public static boolean isTabNameContains(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String href)
	{
		boolean isTabname = false;
		
		List<UserTabConfig> userTabConfigList = null;
		
		if (userTabConfigMap == null)
		{
			isTabname = true;
			return isTabname;
		}
		
		if ("fim".equalsIgnoreCase(moduleName))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{
				if(userTabConfig.getHref().equals(href))
					{
						isTabname = true;
						break;					
					}															
				}
			}
		
		else
		{
			isTabname = true;
		}
		
		return isTabname;
	}
	//P_B_FIM_64230 starts by neeti solanki 
        //P_CRPT_B_08082011
	public static boolean isFilterContains(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String displayName){
		return isFilterContains(userTabConfigMap, moduleName, subModuleName, displayName, false);
	}
	public static boolean isFilterContains(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String displayName,boolean isMuEntity)
	{
            boolean isTabname = false;

            List<UserTabConfig> userTabConfigList = null;
            
            if ("fim".equalsIgnoreCase(moduleName))
            {
            	userTabConfigList = (List<UserTabConfig>)getUserTabSubModuleConfig(subModuleName,isMuEntity).get(subModuleName);
            	//userTabConfigList.addAll((List<UserTabConfig>)getUserTabSubModuleConfig("custom").get("custom"));//ENH_MODULE_CUSTOM_TABS
            }
            if (userTabConfigList == null)
            {
                    isTabname = true;
                    return isTabname;
            }
 
	   if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
                     
			for (UserTabConfig userTabConfig : userTabConfigList) 
                        {
                          	if(userTabConfig.getInternalName().equalsIgnoreCase(displayName) || ("FIM "+userTabConfig.getInternalName()).equalsIgnoreCase(displayName) || (userTabConfig.getInternalName().contains("Training") && displayName.contains("Training")) || (userTabConfig.getInternalName().contains("Financial") && displayName.contains("Financial")) || (userTabConfig.getInternalName().contains("Guarantor") && displayName.contains("Guarantor")) || (userTabConfig.getInternalName().contains("QA") && displayName.contains("QA")) || (userTabConfig.getInternalName().contains("Address") && displayName.contains("Address"))|| (userTabConfig.getInternalName().contains("Remark") && displayName.contains("Remark")) || "Multi-Unit Term Details".equals(displayName) || "Entity Details".equals(displayName)) // P_B_80888//P_FIM_B_69963,P_FIM_B_69962,P_FIM_B_68528,P_FIM_B_73053,P_FIM_B_68528 starts by jyotsna
					{
                        isTabname = true;
                        break;
                    }

                }
			}
		
		else
		{
                isTabname = true;
            }

            return isTabname;
	}
	//P_B_FIM_64230 ends by neeti solanki
	public static boolean isComboNameContains(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String href)
	{
		boolean isTabname = false;
		
		List<UserTabConfig> userTabConfigList = null;
		//String subModuleName = "franchisee";
		
		if (userTabConfigMap == null)
		{
			isTabname = true;
			return isTabname;
		}
		
		if ("fim".equalsIgnoreCase(moduleName))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{		
				if(userTabConfig.getInternalName().equals(href))
					{
						isTabname = true;
						break;					
					}															
				}
			}
		
		else
		{
			isTabname = true;
		}
		
		return isTabname;
	}
	//P_B_FIM_64241 ends by neeti solanki
	
	// added by pappu for bug 64228 start
	public static boolean isTriggerAndAuditing(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String formName)
	{
		boolean isTrigger = false;
		
		List<UserTabConfig> userTabConfigList = null;
		
		if (userTabConfigMap == null)
		{
			isTrigger = true;
			return isTrigger;
		}
		
		if ("fim".equalsIgnoreCase(moduleName))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		
		
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{		
					if (userTabConfig.getInternalName().equalsIgnoreCase(formName))
					{
						isTrigger = true;
						break;					
					}															
				}
			}
		
		else
		{
			isTrigger = true;
		}
		
		return isTrigger;
	}
	// added by pappu for bug 64228 end
	//added by pappu for bug 64257 start
	public static boolean isFranchiseeLocation(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String formName)
	{
		boolean isFranLocation = false;
		
		List<UserTabConfig> userTabConfigList = null;
		
		
		if (userTabConfigMap == null)
		{
			isFranLocation = true;
			return isFranLocation;
		}
		
		if ("fim".equalsIgnoreCase(moduleName))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		
		
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{		
					if (userTabConfig.getInternalName().equals(formName.trim()))
					{
						isFranLocation = true;
						break;					
					}															
				}
			}
		
		else
		{
			isFranLocation = true;
		}
		
		return isFranLocation;
	}
	//added by pappu for bug 64257 end
	
	//added by pappu for bug 64233 start
	public static boolean isTransfer(SequenceMap userTabConfigMap, String moduleName,String subModuleName,String formName)
	{
		boolean isTransfer = false;
		
		List<UserTabConfig> userTabConfigList = null;
		
		
		if (userTabConfigMap == null)
		{
			isTransfer = true;
			return isTransfer;
		}
		
		if ("fim".equalsIgnoreCase(moduleName))
		{
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
		}
		
		
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{		
					if (userTabConfig.getInternalName().equals(formName.trim()))
					{
						isTransfer = true;
						break;					
					}															
				}
			}
		
		else
		{
			isTransfer = true;
		}
		
		return isTransfer;
	}
	//added by pappu for bug 64233 end
	//P_B_fim_64233 starts by neeti solanki
	public static SequenceMap getTabNames(String moduleName,String subModuleName)
	{
		SequenceMap sequenceMap = new SequenceMap();
		String query=new String("SELECT MODULE from USER_TAB_CONFIG where MODULE='"+moduleName+"' AND SUB_MODULE='"+subModuleName+"'");
		ResultSet result = QueryUtil.getResult(query,null);
		
		if(result != null)
		{
			while (result.next())
			{
				String module = result.getString("MODULE");
				//String subModule=result.getString("SUB_MODULE");
				sequenceMap.put(module, getTabsForModule(module,subModuleName));
			}
		}
		QueryUtil.releaseResultSet(result);
		return sequenceMap;
	}
	//P_B_fim_64233 starts by neeti solanki
	public static boolean isExportModule(SequenceMap userTabConfigMap, ExportModules module, ExportModules sModule)
	{
		boolean isExport = false;
		
		List<UserTabConfig> userTabConfigList = null;
		String subModuleName = "franchisee";
		
		if (userTabConfigMap == null || (sModule.getInternalTabName() != null && (sModule.getInternalTabName().equalsIgnoreCase("ignore") || sModule.getInternalTabName().equalsIgnoreCase("false"))))
		{	
			
			isExport = true;
			return isExport;
		}
		
		if ("fim".equalsIgnoreCase(module.getModuleName()) || "regional".equalsIgnoreCase(module.getModuleName()) || "multiUnit".equalsIgnoreCase(module.getModuleName()) || "entity".equalsIgnoreCase(module.getModuleName()))
		{
			
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap.get("fim");
						
			if ("regional".equalsIgnoreCase(module.getModuleName()))
			{
				
				subModuleName = "area";
			}
			else if ("multiUnit".equalsIgnoreCase(module.getModuleName()))
			{
				
				subModuleName = "mu";
			}
			else if ("entity".equalsIgnoreCase(module.getModuleName()))
			{
				
				subModuleName = "muEntity";
			}
		}
		
		if (userTabConfigList != null && userTabConfigList.size() > 0)
		{	
			for (UserTabConfig userTabConfig : userTabConfigList) 
			{		
				if("mu".equals(subModuleName) || "muEntity".equals(subModuleName) ){
					if((subModuleName).equals(userTabConfig.getSubModule())) {
						if (userTabConfig.getInternalName().equals(sModule.getInternalTabName()) || sModule.getModuleDisplayName().toLowerCase().contains(userTabConfig.getInternalName().toLowerCase()) || userTabConfig.getInternalName().toLowerCase().contains(sModule.getModuleDisplayName().toLowerCase()) || "muDetails".equals(sModule.getModuleName()))
						{
							isExport = true;
							break;					
						}
					}
				} else
				
				if (userTabConfig.getSubModule().contains(subModuleName) || ("franchisee".equals(subModuleName) && userTabConfig.getSubModule().contains("custom")))//ENH_MODULE_CUSTOM_TABS	
				{
					if (userTabConfig.getInternalName().equals(sModule.getInternalTabName()) || sModule.getModuleDisplayName().toLowerCase().contains(userTabConfig.getInternalName().toLowerCase()) || userTabConfig.getInternalName().toLowerCase().contains(sModule.getModuleDisplayName().toLowerCase()))
					{
						//P_B_FIM_68824 added by neeti starts
						if(userTabConfig.getInternalName().equals("Area Info"))
						isExport=false;
						else
						isExport = true;
						//P_B_FIM_68824 added by neeti ends
						break;					
					}															
				}
			}
		}
		else
		{
			isExport = true;
		}
		
		return isExport;
	}
	
	public static void deleteModuleUserTabConfig(String module)
	{
		if (StringUtil.isValid(module))
		{
			QueryUtil.executeInsert("DELETE FROM USER_TAB_CONFIG WHERE MODULE = ?", new String[]{module});	
		}		
	}
	
	public static void insertUserTabConfig(UserTabConfig userTabConfig)
	{
		if (userTabConfig != null)
		{
			//ENH_MODULE_CUSTOM_TABS starts
			String query = "INSERT into USER_TAB_CONFIG (ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME,KEY_NAME,IS_CUSTOM) values(?,?,?,?,?,?,?,?)";
			QueryUtil.executeInsert(query, new String[]{userTabConfig.getRowNo(), userTabConfig.getModule(), userTabConfig.getSubModule(), userTabConfig.getHref(), userTabConfig.getName(), userTabConfig.getInternalName(), userTabConfig.getKeyName(), userTabConfig.getIsCustom()});	
			//ENH_MODULE_CUSTOM_TABS ends
			if("Email Summary".equals(userTabConfig.getName()))
			{
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG VALUES (null,1,'fim','mu','fimEmailSummary?requestType=MuUser','nokey','Email Summary','MU Email Summary','N')",null);
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG VALUES (null,1,'fim','muEntity','fimEmailSummary?requestType=MuUser','nokey','Email Summary','MU Email Summary','N')",null);
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='franchiseewithoutsc' AND HREF='logTask'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG VALUES(null,1,'fim','franchiseewithoutsc','logTask','nokey','Task','Task','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='mu' AND HREF='muTasks'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'mu','muTasks','nonkey','Task','Task','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='area' AND HREF='areaTasks'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'area','areaTasks','nonkey','Task','Task','N')");
			}


			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='franchiseewithoutsc' AND HREF='logCall'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG VALUES(null,1,'fim','franchiseewithoutsc','logCall','nokey','Call','Call','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='mu' AND HREF='muCall'")) {

				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'mu','muCall','nonkey','Call','Call','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='area' AND HREF='areaCall'")) {

				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'area','areaCall','nonkey','Call','Call','N')");
			}


			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='franchiseewithoutsc' AND HREF='fimfranchiseeRemarks'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG VALUES(null,1,'fim','franchiseewithoutsc','fimfranchiseeRemarks','nokey','Remark','Remark','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='mu' AND HREF='fimMuRemarks'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'mu','fimMuRemarks','nonkey','Remark','Remark','N')");
			}

			if(!SQLUtil.isDataAvailable("USER_TAB_CONFIG", "SUB_MODULE='area' AND HREF='fimAreaRemarks'")) {
				QueryUtil.executeInsert("INSERT INTO USER_TAB_CONFIG(USER_TAB_ID,ROW_NO,MODULE,SUB_MODULE,HREF,KEY_NAME,NAME,INTERNAL_NAME,IS_CUSTOM) values(null,1,'fim', 'area','fimAreaRemarks','nonkey','Remark','Remark','N')");
			}

		}		
	}
        //P_FIM_ENH_FIM_KEYWORD:Added by Narotam on 30/12/2010 starts
        //This method is called from FCInitHandlerServlet
        public static void configUserTabFIN(String basePath)
	{
            try{
            	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
                String filePath="";
                String directoryPath="";
                String fileName = "FIM_IFFINANCIALkeywords.txt";
                String dirName ="FIMIFFINANCIAL_Keywords";
                String directoryName = "mailmerge";
                directoryPath=_baseConstants.DOCUMENTS_DIRECTORY + "/Documents/" +directoryName +"/"+dirName;
                filePath = _baseConstants.DOCUMENTS_DIRECTORY + "/Documents/" + directoryName + "/" + fileName;
                File dir = new File(directoryPath); 
                
                String[] fileList = dir.list(); 
                
                File file = new File(filePath);
                if(file.exists())
                {
                    file.delete();
                }
                file.createNewFile();
                Writer output = null;
                output = new BufferedWriter(new FileWriter(file));
                
                StringBuffer stringBuffer=new StringBuffer();
		
                //Commented by Narotam form bug 69155 and 69156
		
                 //Added by Narotam on 07/01/2011 for Bug 69155 and 69156 starts
                TabGroup tabGroup = TabXMLDAO.newInstance().getTabGroup("fim_franchisee");
                SequenceMap tabsMap = tabGroup.getTabsMap();
		List<UserTabConfig> userTabConfigList = UserTabConfigUtil.getUserTabConfig("fim");
			
		for(int n=0; n<tabsMap.size(); n++)
		{
                    ArrayList list = (ArrayList) tabsMap.get(n);
                           
		    for (int j=0; j<list.size(); j++)
                    {                                        
                        Tab tab = (Tab) list.get(j);
                                        
                        if (userTabConfigList != null && userTabConfigList.size() > 0)
	                {
	                    for (int k = 0; k < userTabConfigList.size(); k++) 
	                    {	                                        	
                                UserTabConfig userTabConfig = userTabConfigList.get(k);
                                if (userTabConfig.getRowNo().equalsIgnoreCase(Integer.toString(n+1)) 
		                && userTabConfig.getModule().equalsIgnoreCase("fim")
		                && userTabConfig.getSubModule().equalsIgnoreCase("franchisee")
		                && userTabConfig.getHref().equalsIgnoreCase(tab.getHref()))
		                {
                                   //P_FIM_ENH_FIM_KEYWORD:Added by Narotam on 30/12/2010 starts
                                   for (int m=0; m<fileList.length; m++) 
                                   { 
                                       String fName = fileList[m];
                                       if(fName.equals(tab.getHref()+".txt"))
                                       {                                                                                    
                                           String pathOfFile=directoryPath+"/"+fName;
                                           FileInputStream fstream = new FileInputStream(pathOfFile);
                                           DataInputStream in = new DataInputStream(fstream);
                                           BufferedReader br = new BufferedReader(new InputStreamReader(in));
                                           String strLine;
                                           while ((strLine = br.readLine()) != null)   
                                           {
                                               stringBuffer.append(strLine);
                                           }
                                           in.close();
                                           stringBuffer.append(",");
                                        }
                                   }                                                                                                                //////////
		               }		                                        	                                                
                          }
	             }
                }
           }
           //Added by Narotam on 07/01/2011 for Bug 69155 and 69156 ends
                if(StringUtil.isValid(stringBuffer.toString())) {
                    stringBuffer.replace(stringBuffer.lastIndexOf(","),stringBuffer.lastIndexOf(",")+1,"");
                    output.write(stringBuffer.toString());
                }
           output.close();
           }catch(Exception e)
           {
               e.printStackTrace();

           }
	}
         //This method is called from FCInitHandlerServlet
        public static void configUserTab(String basePath)
	{
            try{
            	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
                String filePath="";
                String directoryPath="";
                String fileName ="FIM_keywords.txt";
                String dirName = "FIM_Keywords";
                String directoryName = "mailmerge";
                directoryPath=_baseConstants.DOCUMENTS_DIRECTORY + "/Documents/" +directoryName +"/"+dirName;
                filePath = _baseConstants.DOCUMENTS_DIRECTORY + "/Documents/" + directoryName + "/" + fileName;
                File dir = new File(directoryPath);


                String[] fileList = dir.list(); 
                
                File file = new File(filePath);
                if(file.exists())
                {
                    file.delete();
                }
                file.createNewFile();
                Writer output = null;
                output = new BufferedWriter(new FileWriter(file));
                
                StringBuffer stringBuffer=new StringBuffer();
		
                //Commented by Narotam form bug 69155 and 69156 
	
                //Added by Narotam on 07/01/2011 for Bug 69155 and 69156 starts
                TabGroup tabGroup = TabXMLDAO.newInstance().getTabGroup("fim_franchisee");
                SequenceMap tabsMap = tabGroup.getTabsMap();
		List<UserTabConfig> userTabConfigList = UserTabConfigUtil.getUserTabConfig("fim");
			
		for(int n=0; n<tabsMap.size(); n++)
		{
                    ArrayList list = (ArrayList) tabsMap.get(n);
                           
		    for (int j=0; j<list.size(); j++)
                    {                                        
                        Tab tab = (Tab) list.get(j);
                                        
                        if (userTabConfigList != null && userTabConfigList.size() > 0)
	                {
	                    for (int k = 0; k < userTabConfigList.size(); k++) 
	                    {	                                        	
                                UserTabConfig userTabConfig = userTabConfigList.get(k);
                                if (userTabConfig.getRowNo().equalsIgnoreCase(Integer.toString(n+1)) 
		                && userTabConfig.getModule().equalsIgnoreCase("fim")
		                && userTabConfig.getSubModule().equalsIgnoreCase("franchisee")
		                && userTabConfig.getHref().equalsIgnoreCase(tab.getHref()))
		                {
                                   //P_FIM_ENH_FIM_KEYWORD:Added by Narotam on 30/12/2010 starts
                                   for (int m=0; m<fileList.length; m++) 
                                   { 
                                       String fName = fileList[m];
                                       if(fName.equals(tab.getHref()+".txt"))
                                       {                                                                                    
                                           String pathOfFile=directoryPath+"/"+fName;
                                           FileInputStream fstream = new FileInputStream(pathOfFile);
                                           DataInputStream in = new DataInputStream(fstream);
                                           BufferedReader br = new BufferedReader(new InputStreamReader(in));
                                           String strLine;
                                           while ((strLine = br.readLine()) != null)   
                                           {
                                               stringBuffer.append(strLine);
                                           }
                                           in.close();
                                           stringBuffer.append(",");
                                        }
                                   }                                                                                                                //////////
		               }		                                        	                                                
                          }
	             }
                }
           }
           //Added by Narotam on 07/01/2011 for Bug 69155 and 69156 ends
            if(StringUtil.isValid(stringBuffer.toString())) {
                stringBuffer.replace(stringBuffer.lastIndexOf(","),stringBuffer.lastIndexOf(",")+1,"");
                output.write(stringBuffer.toString());
            }
           output.close();
           }catch(Exception e)
           {
               e.printStackTrace();

           }
	}
       //P_FIM_ENH_FIM_KEYWORD:Added by Narotam on 30/12/2010 ends
    //FIM_TABS_PRIVILEGE_CHECK  Starts    
	    public static SequenceMap configuredTab(String moduleName)
		{
			SequenceMap listTabs=new SequenceMap();
			ResultSet result = QueryUtil.getResult("SELECT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME FROM USER_TAB_CONFIG WHERE MODULE = ?", new String[]{moduleName});
			
			if(result != null)
			{
				while (result.next())
				{
					listTabs.put(result.getString("HREF"),result.getString("INTERNAL_NAME"));
				}
			}
			QueryUtil.releaseResultSet(result);
			return listTabs;
		}
	    
    //FIM_TABS_PRIVILEGE_CHECK  Ends
	    public static SequenceMap configuredTabFIM(String moduleName,String submoduleName)
		{
			SequenceMap listTabs=new SequenceMap();
			StringBuffer query = new StringBuffer();
			query.append("SELECT USER_TAB_ID, ROW_NO, MODULE, SUB_MODULE, HREF, NAME, INTERNAL_NAME FROM USER_TAB_CONFIG WHERE MODULE = '");
			query.append(moduleName);
			query.append("' AND SUB_MODULE = '");
			query.append(submoduleName);
			query.append("'");
			ResultSet result = QueryUtil.getResult(query.toString(), null);
			
			if(result != null)
			{
				while (result.next())
				{
					listTabs.put(result.getString("HREF"),result.getString("INTERNAL_NAME"));
				}
			}
			QueryUtil.releaseResultSet(result);
			return listTabs;
		} 
	    //ENH_MODULE_CUSTOM_TABS starts
	    public static ArrayList<String> configuredCustomTab(String moduleName)
		{
			ArrayList<String> listTabs=new ArrayList<String>();
			ResultSet result = QueryUtil.getResult("SELECT KEY_NAME,IS_CUSTOM FROM USER_TAB_CONFIG WHERE MODULE = ?", new String[]{moduleName});
			if(result != null)
			{
				while (result.next())
				{
					if("Y".equals(result.getString("IS_CUSTOM")))
					{
						listTabs.add(result.getString("KEY_NAME"));
					}
				}
			}
			QueryUtil.releaseResultSet(result);
			return listTabs;
		}
		//ENH_MODULE_CUSTOM_TABS ends
	    
	    /**
	     * This will give the table anchors for the configure tabs using Configure Fim Tabs link.
	     * Will provide only the build tabs. 
	     */
	    public static List<String> getFimConfigureTableAnchor() {
	    	List<String> configureTabs = new ArrayList<String>();
	    	String query = "SELECT TABLE_ANCHOR FROM BUILDER_WEB_FORMS WHERE (DISPLAY_NAME IN(SELECT INTERNAL_NAME FROM USER_TAB_CONFIG WHERE SUB_MODULE='franchiseewithoutsc') AND MODULE='fim') OR (MODULE='fim' AND IS_CUSTOM = 'Y')";
	    	ResultSet result = null;
	    	try {
	    		result = QueryUtil.getResult(query, null);
	    		while(result.next()) {
	    			configureTabs.add(result.getString("TABLE_ANCHOR"));
	    		}
	    	} catch (Exception e) {
				// TODO: handle exception
			} finally {
				QueryUtil.releaseResultSet(result);
			}
			
	    	return configureTabs;
	    }
	    
}