package com.home.builderforms;

//Import statements

import java.util.HashMap;
import java.util.Iterator;

import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.UserRoleMap;
import com.home.builderforms.Info;
import com.home.builderforms.UserTabConfigUtil;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
	
 * $Header:  com.appnetix.app.portal.export
 * $Author: gauranga $AmitGokhru
 * $Version:  $1.1
 * $Date: 2016/03/03 09:20:35 $8 May 2004
 *
 * ====================================================================
 * Basic Description/Data Flow of Bean
	This class is the wrapper to hold ExportModules info
 
 * Other's beans called with methods
	com.appnetix.app.util.xmldao.XMLUtil

 *  Updated by Parveen
 */

 /**
 -----------------------------------------------------------------------------------------------------------
 Version No.		  Date		 By	     Against	  Function Added				Comments
 -----------------------------------------------------------------------------------------------------------
 P_COMMON_E_5700	  26/06/2007 Sunilk        Enhancement  getCustomReportModuleTable()	To Make Custom Report Editable.	
  
 P_COMMON_E_1111	  25/10/2007 Nikhil Verma  Enhancement  getCustomReportModuleTable()	To Make Custom Report Privileges Based.
 P_FS_B_40855			12 nov,2008   Sanjeev k   	
 P_E_FIM_7.1             2 Nov 2010     Neeti Solanki                                    for replacing primary info with center info in fim tab export
 P_B_FIM_68824     6 Jan 2011      Neeti Solanki                              for changing the display name of center info with respect of franchisee/area/mu
 BBEH-20110408-055 		24/06/2011        Nishant Prabhakar         for Export Settings
 PRI_COMPANY_5_JULY    18 July 2013    Divya Mishra             Company Information and Task history will show only when the user will have the privilege
 GROUP_FILTER_ENH  		15 jan 2014   Deepanshi Miglani     Enh     for adding group filter in CM Search
 P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 BOEFLY_INTEGRATION		19/08/2014		Veerpal Singh			Enh				A third party integration with Boefly through REST-API for lead sync.
 PROVEN_MATCH_INTEGRATION	28/11/2014		Amar Singh			Ens			A third party integration with Proven Match through REST-API for lead sync.
 -----------------------------------------------------------------------------------------------------------
 */



public class ExportModules{

	/**
		Logger		
	*/
	static Logger logger			= Logger.getLogger(ExportModules.class);
	
	/**
		module id
	*/
	private String id						= null;

	/**
		Module Level
	*/
	private String moduleLevel				= null;

	/**
		Module Name
	*/
	private String moduleName				= null;

	/**
		Module Display Name
	*/
	private String moduleDisplayName		= null;

	/**
		Module Main Table
	*/
	private String moduleMainTable			= null;

	/**
		Module Module Dependent On
	*/
	private String moduleDependentOn			= null;

	/**
		Module Module Dependent Table
	*/
	private String moduleDependentTable			= null;

	/**
		Sub module
	*/
	private SequenceMap subModule			= null;
	
	
	/**
	Priviledge
	 */
	private String privilege			= null;

	private String internalTabName = null;	
	
	public String getInternalTabName() {
		return internalTabName;
	}

	public void setInternalTabName(String internalTabName) {
		this.internalTabName = internalTabName;
	}

	/**
	setter method for privilege 
	 */

	public void setPrivilege(String privilege){
		this.privilege			= privilege;
	}

	/**
		Getter method for privilege
	 */
	public String getPrivilege(){
		return privilege;
	}

	/**
		Setter method for id
	*/
	public void setId(String id){
		this.id					= id;
	}
	
	/**
		Getter method for id
	*/
	public String getId(){
		return id;
	}

	/**
		Setter method for  module level
	*/

	public void setModuleLevel(String moduleLevel){
		this.moduleLevel		= moduleLevel;
	}
	
	/**
		Getter method for module level
	*/
	public String getModuleLevel(){
		return moduleLevel;
	}
	
	/**
		setter method for module name
	*/

	public void setModuleName(String moduleName){
		this.moduleName			= moduleName;
	}

	/**
		Getter method for module name
	*/

	public String getModuleName(){
		return moduleName;
	}
	
	/**
		Setter method for module display name
	*/
	public void setModuleDisplayName(String moduleDisplayName){
		this.moduleDisplayName	= moduleDisplayName;
	}

	/**
		Getter method for Module display name
	*/

	public String getModuleDisplayName(){
		return moduleDisplayName;
	}

	/**
		Setter method for Module main table
	*/

	public void setModuleMainTable(String moduleMainTable){
		this.moduleMainTable		= moduleMainTable;
	}

	/**
		Getter method for module main table
	*/
	
	public String getModuleMainTable(){
		return moduleMainTable;
	}

	/**
		Setter method for Module Dependent On
	*/

	public void setModuleDependentOn(String moduleDependentOn){
		this.moduleDependentOn		= moduleDependentOn;
	}

	/**
		Getter method for Module Dependent On
	*/
	
	public String getModuleDependentOn(){
		return moduleDependentOn;
	}

	/**
		Setter method for moduleDependentTable
	*/

	public void setModuleDependentTable(String moduleDependentTable){
		this.moduleDependentTable		= moduleDependentTable;
	}

	/**
		Getter method for Module Dependent On
	*/
	
	public String getModuleDependentTable(){
		return moduleDependentTable;
	}
	
	/**
		Getter method for sub module map
	*/
	public SequenceMap getSubModuleMap(){
		return subModule;
	}
	
	/**
		Setter method for sub module map
	*/
	public void setSubModule(SequenceMap subModule){
		this.subModule		= subModule;
	}

	/**
		Method for adding sub module to the module
	*/

	public void setAddSubModule(String id , ExportModules module){
		if(subModule == null){
			subModule	= new SequenceMap();
		}//end if
		subModule.put(id , module);
	}	

	/**
		Getter method for getting submodule with the giving id.
	*/

	public ExportModules getSubModule(String id){
		ExportModules module		= null;	
		if(subModule != null){
				module			= (ExportModules)subModule.get(id);
		}//end if
		return module;
	}
	
	/**
		Method returns the string representation of the ExportModules object.
	*/
	public String toString(){
		StringBuffer sBuffer	= new StringBuffer();
		try{
			sBuffer.append("ID :");
			sBuffer.append(id);
			sBuffer.append(",Module Level :");
			sBuffer.append(moduleLevel);
			sBuffer.append(",Module Name:");
			sBuffer.append(moduleName);
			sBuffer.append(",Module Display Name:");
			sBuffer.append(moduleDisplayName);
			sBuffer.append(",Module Main Table:");
			sBuffer.append(moduleMainTable);
			sBuffer.append(",InternalName:").append(StringUtil.isValid(internalTabName)? internalTabName:FieldNames.EMPTY_STRING);
			
            //Added by GANESH
			sBuffer.append(",Privilege :");
			sBuffer.append(privilege);
			
			if(subModule != null){
				int size		 = subModule.size();
				for(int i = 0; i < size; i++){
					// for adding submodule 
					ExportModules module	= (ExportModules)subModule.get(i);
					sBuffer.append(",sub module id :");
					sBuffer.append(module.getId());
					sBuffer.append(",subModule value :");
					//recursive call to module.toString method
					sBuffer.append(module.toString());
				}//end for 
			}// end if
		}catch(Exception e){
			logger.error("\nException in com/appnetix/app/portal/export - ExportModules.java--> toString():" + e);
		}

		return sBuffer.toString();
	}

	/**
		This method will return the Html table structure for the module including its submodule
		@Param ExportModule object
	*/
	/**
	 * Modified by Suilk on 21 Sep 2006 ,
	 * For the details of if condition see the implementation of FIM Training and FIM Financials Exports.
	*/
	//PRI_COMPANY_5_JULY userRoleMap is added to provide the privilege for the company and task 
	public static String getModuleTable(ExportModules module, boolean bSub,String userLanguage, String forSearch){
		return getModuleTable( module,  bSub, userLanguage,  forSearch , null);
	}
	
	public static String getModuleTable(ExportModules module, boolean bSub,String userLanguage, String forSearch , UserRoleMap userRoleMap){
		//System.out.println("print bSub----"+bSub+"bSub");
		StringBuffer sBuffer = new StringBuffer("<table width='100%' cellspacing='0' cellpadding='0'>");
		sBuffer.append("<tr><td colspan=2 class='bText12'>");
		//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
		String tempModuleMainTable =  module.getModuleMainTable();
		
		try {
		if (bSub) {
			if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
				//GROUP_FILTER_ENH starts
				if("Group Info".equals(module.getModuleDisplayName()))
					sBuffer.append("<input type='checkbox' name='selTables' checked='true' style='display:none' onclick='javaScript:checkSelTables(this)' value=");
				//GROUP_FILTER_ENH ends
				else
					sBuffer.append("<input type='checkbox' name='selTables'  onclick='javaScript:checkSelTables(this)' value=");
				if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
					tempModuleMainTable = module.getModuleDependentTable();
				sBuffer.append(tempModuleMainTable);
				sBuffer.append(">");
				if("Group Info".equals(module.getModuleDisplayName())){}//GROUP_FILTER_ENH 
				else if("cmCompanyInfo".equals(module.getModuleMainTable())){//ZCUB-20150505-144
					if(StringUtil.isValidNew(BaseUtils.getTabDisplayNameFs(module.getModuleMainTable()))) {
						sBuffer.append(LanguageUtil.getString(BaseUtils.getTabDisplayNameFs(module.getModuleMainTable())));
					}else{
						sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName()));
					}
				}else
				sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName()));
				sBuffer.append("</td></tr>");
			}
		}
		else{
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			sBuffer.append("<input type='checkbox' name='selAll' ");
			sBuffer.append(" onClick='selectAll(this,");
			sBuffer.append("selTables");
			sBuffer.append(")' value =");
			sBuffer.append(tempModuleMainTable);
			sBuffer.append(">");
			sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName()));
			sBuffer.append("</td></tr>");
		}	}catch(Exception exp){
			exp.printStackTrace();
		}
		
		
		/**This if block is added to show the primary table's disabled checkbox, 
		   So that one can export only primary table 
		*/
		if (!bSub){
					//System.out.println("in side print bSub----"+bSub+"bSub");
				sBuffer.append("<tr><td width='3%'></td> <td class='text'>");
				sBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
				sBuffer.append(module.getModuleMainTable());
                                sBuffer.append(">");
                if(StringUtil.isValidNew(BaseUtils.getTabDisplayNameFs(tempModuleMainTable))) {//ZCUB-20150505-144 
                	sBuffer.append(LanguageUtil.getString(BaseUtils.getTabDisplayNameFs(tempModuleMainTable)));
                }else{
                	sBuffer.append(LanguageUtil.getString("Primary Info"));
                }
				sBuffer.append("</td></tr>");
                                if(module.getModuleMainTable().equals("cmContactDetails")){ //P_CM_Enh_BuilderForm 
                                sBuffer.append("<input type='hidden' name='selTables'   value='cmAddress'");
                                }
		}
		SequenceMap subModule		= module.getSubModuleMap();
		//sBuffer.append("<input type='radio' name='exportModule' value=");
		if(subModule != null){
			int size				= subModule.size();
			//Info exportSettings=NewPortalUtils.getExportSettings();
			Info exportSettings=BaseUtils.getExportSettings();
	    	String isExportContactHistory=exportSettings.getString(FieldNames.IS_EXPORT_CONTACT);
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				String moduleName = sModule.getModuleName() ;
				//PRI_COMPANY_5_JULY start
				if(userRoleMap!= null && (("companyInfo".equals(moduleName) &&  !userRoleMap.isPrivilegeInMap("/cmCompanyPrivilege")) || ("tasksExport".equals(moduleName) && !userRoleMap.isPrivilegeInMap("/cmViewTaskPrivilege"))) )
				{
					continue ;
				}
				//P_SCH_ENH_008 Starts
				else if("jobs".equals(moduleName)  && userRoleMap!= null &&  !userRoleMap.isPrivilegeInMap(sModule.getPrivilege()))
				{
					continue ;
				}
				//P_SCH_ENH_008 Ends
				//PRI_COMPANY_5_JULY end
				
				if(!(forSearch != null && forSearch.equals("search"))){
					if(sModule != null && !("Yes".equalsIgnoreCase(isExportContactHistory)) && ("outlookMails".equals(sModule.getModuleMainTable()) || "tasks".equals(sModule.getModuleMainTable()) || "franchiseeCall".equals(sModule.getModuleMainTable()) || "cmContactCall".equals(sModule.getModuleMainTable()) || "cmCampaignEmailLog".equals(sModule.getModuleMainTable()) || "cmContactEmail".equals(sModule.getModuleMainTable()) || "tasksExport".equals(sModule.getModuleMainTable()) || "cmMailMergeTemplateRel".equals(sModule.getModuleMainTable()) || "cmOutlookMails".equals(sModule.getModuleMainTable())))//dki-20140711-273//P_B_ADMIN_43952
						continue;
					}
                sBuffer.append("<tr><td width='3%'></td> <td>");
				sBuffer.append(getModuleTable(sModule,true,userLanguage,forSearch,userRoleMap));
				sBuffer.append("</td></tr>");
                        }
		}
		//sBuffer.append("<tr><td>&nbsp;</td></tr>");
		sBuffer.append("</table>");

		return sBuffer.toString();

	}
	public static String getModuleTable(ExportModules module, boolean bSub,String moduleName){
		StringBuffer sBuffer = new StringBuffer("<table width='100%' cellspacing='0' cellpadding='0'>");
		sBuffer.append("<tr><td colspan=2 class='text'>");
		//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
		if (bSub)
			sBuffer.append("<input type='checkbox' name='selTables'  onclick='javaScript:checkSelTables(this)' value=");
		else
		{
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			sBuffer.append("<input type='checkbox' name='selAll' ");
        sBuffer.append(" onClick='selectAll(this,");
        sBuffer.append("selTables");
        sBuffer.append(")' value =");
			}

		sBuffer.append(module.getModuleMainTable());
		sBuffer.append(">");
		sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName()));
		sBuffer.append("</td></tr>");
		/**This if block is added to show the primary table's disabled checkbox, 
		   So that one can export only primary table 
		*/
		if (!bSub){
				sBuffer.append("<tr><td width='3%'></td> <td class='text'>");
				sBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
				sBuffer.append(module.getModuleMainTable());
				sBuffer.append(">");
				if(moduleName != null && !moduleName.equals(""))
				{
				sBuffer.append(LanguageUtil.getString(moduleName));
				}
				else
				{
				sBuffer.append(LanguageUtil.getString("Primary Info"));
				}
				sBuffer.append("</td></tr>");
		}
		SequenceMap subModule		= module.getSubModuleMap();
		//sBuffer.append("<input type='radio' name='exportModule' value=");
		if(subModule != null){
			int size				= subModule.size();
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				sBuffer.append("<tr><td width='3%'></td> <td>");
				sBuffer.append(getModuleTable(sModule,true,moduleName));
				sBuffer.append("</td></tr>");
			}
		}
		//sBuffer.append("<tr><td>&nbsp;</td></tr>");
		sBuffer.append("</table>");

		return sBuffer.toString();

	}
	public static String getCustomReportModuleTable(ExportModules module, boolean bSub, String customReportOthetTables,UserRoleMap userRoleMap){
		return getCustomReportModuleTable(module,bSub,customReportOthetTables,userRoleMap,null);
	}
	//P_COMMON_E_5700
	public static String getCustomReportModuleTable(ExportModules module, boolean bSub, String customReportOthetTables,UserRoleMap userRoleMap,String userLanguage){
		StringBuffer sBuffer = new StringBuffer("<table width='100%' cellspacing='0' cellpadding='0'>");
		sBuffer.append("<tr><td colspan=2 class='text'>");
		//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
		//P_E_FS_INTER start added by ram Avtar for internationalization of code
		boolean isFsMenuWithI18n=false;
		if (LanguageUtil.isI18nImplemented())
		{
			isFsMenuWithI18n=true;
		}
		//P_E_FS_INTER ends
		String tempModuleMainTable =  module.getModuleMainTable();
		try {
		if (bSub) {
			if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
				sBuffer.append("<input type='checkbox' name='selTables'  onclick='javaScript:checkSelTables(this)' value=");
				if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
					tempModuleMainTable = module.getModuleDependentTable();
				sBuffer.append(tempModuleMainTable);

				if (customReportOthetTables!=null && customReportOthetTables.indexOf(tempModuleMainTable)!=-1){
					sBuffer.append(" checked ");
				}
				sBuffer.append(">");
				/**
				 * @Bug-id 50858
				 * Allow all sub tables related to main module table
				 * This will maintain all tables used for checking fields in search section or fields used in custom section
				 */
				sBuffer.append("<input type='hidden' name='allTables' value=").append(tempModuleMainTable).append(">");
				
				sBuffer.append(isFsMenuWithI18n?LanguageUtil.getString(module.getModuleDisplayName(),userLanguage):module.getModuleDisplayName());//P_E_FS_INTER
				sBuffer.append("</td></tr>");
			}
		}
		else{
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			
			sBuffer.append("<input type='checkbox' name='selAll' ");
			sBuffer.append(" onClick='selectAll(this,");
			sBuffer.append("selTables");
			sBuffer.append(")' value =");
			sBuffer.append(tempModuleMainTable);
			sBuffer.append(">");
			sBuffer.append(isFsMenuWithI18n?LanguageUtil.getString(module.getModuleDisplayName(),userLanguage):module.getModuleDisplayName());//P_E_FS_INTER
			sBuffer.append("</td></tr>");
		}	}catch(Exception exp){
			exp.printStackTrace();
		}
		
		/**This if block is added to show the primary table's disabled checkbox, 
		   So that one can export only primary table 
		*/
		if (!bSub){
			/**
			 * @Bug-id 50858
			 * @author abhishek gupta
			 * @date 21 dec 2009
			 * Allow all sub tables related to main module table
			 * This will maintain all tables used for checking fields in search section or fields used in custom section
			 */
				sBuffer.append("<input type='hidden' name='allTables' value=").append(tempModuleMainTable).append(">");

				sBuffer.append("<tr><td width='3%'></td> <td class='text'>");
				sBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
				sBuffer.append(module.getModuleMainTable());
				sBuffer.append(">");
				sBuffer.append(isFsMenuWithI18n?LanguageUtil.getString("Primary Info",userLanguage):"Primary Info");//P_E_FS_INTER
				sBuffer.append("</td></tr>");
		}
		SequenceMap subModule		= module.getSubModuleMap();
		//sBuffer.append("<input type='radio' name='exportModule' value=");
		if(subModule != null){
			int size				= subModule.size();
			
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				//P_FS_B_40855
				if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
					continue;
				//P_FS_B_40855 end
				String privilege        = sModule.getPrivilege();
                if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
					sBuffer.append("<tr><td width='3%'></td> <td>");
					sBuffer.append(getCustomReportModuleTable(sModule,true, customReportOthetTables,userRoleMap,userLanguage));
					sBuffer.append("</td></tr>");
                }
			}
		}
		//sBuffer.append("<tr><td>&nbsp;</td></tr>");
		sBuffer.append("</table>");

		return sBuffer.toString();

	}
	
	public static String getCustomReportModuleTableNew(ExportModules module, boolean bSub, String customReportOthetTables,UserRoleMap userRoleMap){
		StringBuffer sBuffer = new StringBuffer("<table width=100% cellspacing=0 cellpadding=0>");
		sBuffer.append("<tr><td colspan=2 class=text height=20 >");
		//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
		String tempModuleMainTable =  module.getModuleMainTable();
		try {
		if (bSub) {
			if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
				sBuffer.append("<input type=checkbox name=selTables  onclick=javaScript:checkSelTables(this) value=");
				if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
					tempModuleMainTable = module.getModuleDependentTable();
				sBuffer.append(tempModuleMainTable);

				if (customReportOthetTables!=null && customReportOthetTables.indexOf(tempModuleMainTable)!=-1){
					sBuffer.append(" checked ");
				}
				sBuffer.append(">");
				sBuffer.append(module.getModuleDisplayName());
				sBuffer.append("</td></tr>");
			}
		}
		else{
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			sBuffer.append("<input type=checkbox name=selAll ");
			sBuffer.append(" onClick=selectAll(this,");
			sBuffer.append("selTables");
			sBuffer.append(") value =");
			sBuffer.append(tempModuleMainTable);
			sBuffer.append(">");
			sBuffer.append(module.getModuleDisplayName());
			sBuffer.append("</td></tr>");
		}	}catch(Exception exp){
			exp.printStackTrace();
		}
		
		
		/**This if block is added to show the primary table's disabled checkbox, 
		   So that one can export only primary table 
		*/
		if (!bSub){
				sBuffer.append("<tr><td width=3% height=20 ></td> <td class=text height=20 >");
				sBuffer.append("<input type=checkbox disabled  checked name=primaryTable   value=");
				sBuffer.append(module.getModuleMainTable());
				sBuffer.append(">");
				sBuffer.append("Primary Info");
				sBuffer.append("</td></tr>");
		}
		SequenceMap subModule		= module.getSubModuleMap();
		//sBuffer.append("<input type='radio' name='exportModule' value=");
		if(subModule != null){
			int size				= subModule.size();
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
					continue;
				String privilege        = sModule.getPrivilege();
                if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
					sBuffer.append("<tr><td width=3% height=20 ></td> <td height=20 >");
					sBuffer.append(getCustomReportModuleTableNew(sModule,true, customReportOthetTables,userRoleMap));
					sBuffer.append("</td></tr>");
                }
			}
		}
		//sBuffer.append("<tr><td>&nbsp;</td></tr>");
		sBuffer.append("</table>");

		return sBuffer.toString();

	}
	/**
	 * Generate list of modules
	 * @author abhishek gupta
	 * @date 13 AUG 2009
	 * @param module
	 * @param bSub
	 * @param customReportOthetTables
	 * @param userRoleMap
	 * @return
	 */
	public static String getCustomReportModuleListTable(ExportModules module, boolean bSub, String customReportOthetTables,UserRoleMap userRoleMap){
		StringBuffer sBuffer = new StringBuffer("");
		String tempModuleMainTable =  module.getModuleMainTable();
		
		try 
		{
			if (bSub) {
				if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
					if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
						tempModuleMainTable = module.getModuleDependentTable();
					sBuffer.append("<option value='"+tempModuleMainTable);
					if (customReportOthetTables!=null && customReportOthetTables.indexOf(tempModuleMainTable)!=-1){
						sBuffer.append(" selected ");
					}
					sBuffer.append(">");
					sBuffer.append(module.getModuleDisplayName());
					sBuffer.append("</option>");
				}
			}
			else{
				sBuffer.append("<select id='selTables' name='selTables' multiple='multiple' class='select' style='width: 200px; height: 250px; visibility: visible;'>");
				sBuffer.append("<option value='"+tempModuleMainTable+"' selected>"+module.getModuleDisplayName()+"</option>");
			}	
		}catch(Exception exp){
			exp.printStackTrace();
		}
			
		SequenceMap subModule		= module.getSubModuleMap();
		if(subModule != null){
			int size				= subModule.size();
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
					continue;
				String privilege        = sModule.getPrivilege();
                if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
					sBuffer.append(getCustomReportModuleListTable(sModule,true, customReportOthetTables,userRoleMap));
                }
			}
		}
		return sBuffer.toString();
	}	
	/**
	 * This method will retrieve all tables name of given module 
	 * @author abhishek gupta
	 * @date 25 nov 2009
	 * @param module
	 * @param bSub
	 * @param customReportOthetTables
	 * @param userRoleMap
	 * @return
	 */
	public static String getCustomReportModuleList(ExportModules module, boolean bSub, String customReportOthetTables,UserRoleMap userRoleMap){
		StringBuffer sBuffer = new StringBuffer("");
		String tempModuleMainTable =  module.getModuleMainTable();
		
		try 
		{
			if (bSub) {
				if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
					if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
						tempModuleMainTable = module.getModuleDependentTable();
					sBuffer.append(","+tempModuleMainTable);
				}
			}
			else{
				//nothing to do
			}	
		}catch(Exception exp){
			exp.printStackTrace();
		}
			
		SequenceMap subModule		= module.getSubModuleMap();
		if(subModule != null){
			int size				= subModule.size();
			for(int i=0; i < size; i++){
				ExportModules sModule	= (ExportModules)subModule.get(i);
				if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
					continue;
				String privilege        = sModule.getPrivilege();
                if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
					sBuffer.append(getCustomReportModuleList(sModule,true, customReportOthetTables,userRoleMap));
                }
			}
		}
		return sBuffer.toString();
	}	
	
    /*********** Added by GANESH on 07 AUG, 07
     *This method will return the Html table structure for the module including its submodule according to the privileges given to the user
     *User would only be able to view those submodules in export which he has the privilege to view.
     */
	public static String getModuleTableWithPrivileges(ExportModules module, boolean bSub, UserRoleMap userRoleMap){
		return getModuleTableWithPrivileges(module,bSub,userRoleMap,null,null);
	}
	public static String getModuleTableWithPrivileges(ExportModules module, boolean bSub, UserRoleMap userRoleMap,String userLanguage, String forSearch){
		return getModuleTableWithPrivileges(module, bSub, userRoleMap, userLanguage, forSearch,null);
	}
	public static String getModuleTableWithPrivileges(ExportModules module, boolean bSub, UserRoleMap userRoleMap,String userLanguage, String forSearch, String checkBoxName){
		return getModuleTableWithPrivileges(module, bSub, userRoleMap, userLanguage, forSearch,checkBoxName,null);//BB-20150203-259 (Tab Re-ordering changes)
	}
	//BBEH-20110408-055 Added PARAMETER forSearch in method
public static String getModuleTableWithPrivileges(ExportModules module, boolean bSub, UserRoleMap userRoleMap,String userLanguage, String forSearch, String checkBoxName,HttpServletRequest request){//BB-20150203-259 (Tab Re-ordering changes)
	//BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	if(!StringUtil.isValid(checkBoxName)){
		checkBoxName="selTables";
	} 
	String moduleName="";
	if(request!=null){
	 moduleName=(String)request.getSession().getAttribute("menuName");
	}
	String fimFlag=request.getParameter("fimFlag");
	HashMap<String, String> sectionMap=(HashMap)request.getAttribute("sectionMap");
	if(sectionMap==null||sectionMap.size()<=0){
	 sectionMap = BaseUtils.getModuleTabMap(moduleName);//BB-20150203-259 (Tab Rename) changes
	}
	
	StringBuffer tempsBuffer=null;
	SequenceMap tempMap=new SequenceMap();
	StringBuffer sBuffer = new StringBuffer("<table width='100%' cellspacing='0' cellpadding='0'>");
	sBuffer.append("<tr><td colspan=2 class='text'>");
	//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
	String tempModuleMainTable =  module.getModuleMainTable();	
	//P_E_FS_INTER start added by ram Avtar for internationalization of code
	boolean isFsMenuWithI18n=false;
	if (LanguageUtil.isI18nImplemented())
	{
		isFsMenuWithI18n=true;
	}
	//P_E_FS_INTER ends
	try {
		if (bSub) {
			if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
				sBuffer.append("<input type='checkbox' name='"+checkBoxName+"'  onclick='javaScript:checkSelTables(this)' value=");
				if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
					tempModuleMainTable = module.getModuleDependentTable();
				sBuffer.append(tempModuleMainTable);
				sBuffer.append(">");
				if(isFsMenuWithI18n) { //P_E_FS_INTER 
					if(sectionMap!=null && StringUtil.isValidNew(sectionMap.get(module.getModuleDisplayName())) && !"1".equals(fimFlag)) {
						sBuffer.append(LanguageUtil.getString(sectionMap.get(module.getModuleDisplayName())));
					} else {
						sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName(),userLanguage));
					}
				} else {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(module.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
						sBuffer.append(sectionMap.get(module.getModuleDisplayName()));
					} else {
						sBuffer.append(module.getModuleDisplayName());
					}
				}
				sBuffer.append("</td></tr>");
			}
		}
		else{
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			sBuffer.append("<input type='checkbox' name='selAll' ");
			sBuffer.append(" onClick='selectAll(this,");
			sBuffer.append("selTables");
			sBuffer.append(")' value =");
			sBuffer.append(tempModuleMainTable);			
			sBuffer.append(">");
			if(isFsMenuWithI18n)//P_E_FS_INTER 
				sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName(),userLanguage));//BB-20150203-259 (Form builder Tab Rename Changes)
			else
				sBuffer.append(module.getModuleDisplayName());
			sBuffer.append("</td></tr>");			
		}	
	}catch(Exception exp){
		exp.printStackTrace();
	}
	
	
	/**This if block is added to show the primary table's disabled checkbox, 
	   So that one can export only primary table 
	*/
	if (!bSub){	
		String tempString="";
		tempsBuffer=new StringBuffer();
		if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
			tempsBuffer.append("<tr><td width='2%'></td> <td class='text'>");
			tempsBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
			tempsBuffer.append(module.getModuleMainTable());
			tempsBuffer.append(">");
		}else{
			sBuffer.append("<tr><td width='2%'></td> <td class='text'>");
			sBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
			sBuffer.append(module.getModuleMainTable());
			sBuffer.append(">");
		}
			if(isFsMenuWithI18n)//P_E_FS_INTER 
			{
				//P_E_FIM_7.1 added by neeti starts
				//P_B_FIM_68824 added by neeti starts
				if(module.id.equals("2")) {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Center Info"))&&!"1".equals(fimFlag)) {//BB-20150203-259 (Form builder Tab Rename Changes)
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(LanguageUtil.getString(sectionMap.get("Center Info")));
						}else{
							sBuffer.append(LanguageUtil.getString(sectionMap.get("Center Info")));
						}
					} else {
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString("Center Info",userLanguage));
						}else{
						sBuffer.append(LanguageUtil.getString("Center Info",userLanguage));
						}
					}
					tempString=LanguageUtil.getString("Center Info",userLanguage);
				} else if(module.id.equals("4")) {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Area Info"))&&!"1".equals(fimFlag)) {//BB-20150203-259 (Form builder Tab Rename Changes)
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString(sectionMap.get("Area Info")));
						}else{
						sBuffer.append(LanguageUtil.getString(sectionMap.get("Area Info")));
						}
					} else { 
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString("Area Info",userLanguage));
						}else{
						sBuffer.append(LanguageUtil.getString("Area Info",userLanguage));
						}
					}
					tempString=LanguageUtil.getString("Area Info",userLanguage);
				} else if(module.id.equals("12")) {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Multi Unit Info"))&&!"1".equals(fimFlag)) {//BB-20150203-259 (Form builder Tab Rename Changes)
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString(sectionMap.get("Multi Unit Info")));
						}else{
						sBuffer.append(LanguageUtil.getString(sectionMap.get("Multi Unit Info")));
						}
					} else {
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString("Multi Unit Info",userLanguage));
						}else{
						sBuffer.append(LanguageUtil.getString("Multi Unit Info",userLanguage));
						}
					}
					tempString=LanguageUtil.getString("Multi Unit Info",userLanguage);
				} else if(module.id.equals("39")) {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Entity Detail"))&&!"1".equals(fimFlag)) {//BB-20150203-259 (Form builder Tab Rename Changes)
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString(sectionMap.get("Entity Detail")));
						}else{
						sBuffer.append(LanguageUtil.getString(sectionMap.get("Entity Detail")));
						}
					} else {
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString("Entity Detail",userLanguage));
						}else{
						sBuffer.append(LanguageUtil.getString("Entity Detail",userLanguage));
						}
					}
					tempString=LanguageUtil.getString("Entity Detail",userLanguage);
				} else {
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Primary Info"))&&!"1".equals(fimFlag)) {//BB-20150203-259 (Form builder Tab Rename Changes)
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString(sectionMap.get("Primary Info")));
						}else{
						sBuffer.append(LanguageUtil.getString(sectionMap.get("Primary Info")));
						}
					} else {
						if("fs".equals(moduleName)||"fim".equals(moduleName)){
						tempsBuffer.append(LanguageUtil.getString("Primary Info",userLanguage));
						}else{
						sBuffer.append(LanguageUtil.getString("Primary Info",userLanguage));
						}
					}
					tempString=LanguageUtil.getString("Primary Info",userLanguage);
				}
			}
				else
				{
					if(module.id.equals("2")) {
						if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Center Info"))&&!"1".equals(fimFlag)){
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(sectionMap.get("Center Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}else{
							sBuffer.append(sectionMap.get("Center Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}
						}else{
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append("Center Info");
							}else{
							sBuffer.append("Center Info");
							}
						}
						tempString="Center Info";
					} else if(module.id.equals("4")) {
						if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Area Info"))&&!"1".equals(fimFlag)){
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(sectionMap.get("Area Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}else{
							sBuffer.append(sectionMap.get("Area Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}
						}else{
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append("Area Info");
							}else{
							sBuffer.append("Area Info");
							}
						}
						tempString="Area Info";
					} else if(module.id.equals("12")) {
						if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Multi Unit Info"))&&!"1".equals(fimFlag)){
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(sectionMap.get("Multi Unit Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}else{
							sBuffer.append(sectionMap.get("Multi Unit Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}
						}else{
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append("Multi Unit Info");
							}else{
							sBuffer.append("Multi Unit Info");
							}
						}
						tempString="Multi Unit Info";
					} else if(module.id.equals("39")) { 
						if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Entity Detail"))&&!"1".equals(fimFlag)){
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(sectionMap.get("Entity Detail"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}else{
							sBuffer.append(sectionMap.get("Entity Detail"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}
						}else{
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append("Entity Detail");
							}else{
							sBuffer.append("Entity Detail");
							}
						}
						tempString="Entity Detail";
					} else {
						if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Primary Info"))&&!"1".equals(fimFlag)){
							if("fs".equals(moduleName)||"fim".equals(moduleName)){
							tempsBuffer.append(sectionMap.get("Primary Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}else{
							sBuffer.append(sectionMap.get("Primary Info"));//BB-20150203-259 (Form builder Tab Rename Changes)
							}
						}else{
							if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
							tempsBuffer.append("Primary Info");
							}else{
							sBuffer.append("Primary Info");
							}
						}
						tempString="Primary Info";
					}
					//P_B_FIM_68824 added by neeti ends
					//P_E_FIM_7.1 added by neeti ends
				}
			sBuffer.append("</td></tr>");
			tempsBuffer.append("</td></tr>");
			if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
				tempMap.put(tempString,tempsBuffer);
			}
			
	}
	SequenceMap subModule		= module.getSubModuleMap();	
	//sBuffer.append("<input type='radio' name='exportModule' value=");
	//if(subModule != null && (userRoleMap.isPrivilegeInMap(privilege) || privilege.equals(null))){
    if(subModule != null){
    	int size				= subModule.size();
    	//BBEH-20110408-055 Added by Nishant starts
    	//Info exportSettings=NewPortalUtils.getExportSettings();
    	Info exportSettings=BaseUtils.getExportSettings();
    	String isExportContactHistory=exportSettings.getString(FieldNames.IS_EXPORT_CONTACT);
    	//BBEH-20110408-055 Added by Nishant ends
    	for(int i=0; i < size; i++){
			ExportModules sModule	= (ExportModules)subModule.get(i);	
			tempsBuffer=new StringBuffer();
			//P_FS_B_40855
			if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
				continue;			
			//P_FS_B_40855 end
			//BBEH-20110408-055 Added by Nishant starts
			//System.out.println("\n\n----table-------------"+sModule.getModuleMainTable());
			//AUDIT_ENHANCEMENT_CHANGES:starts
			if (ModuleUtil.auditImplemented() && false && "fimQa".equals(sModule.getModuleMainTable()))//P_PW_ENH_FIM_EXPORT
				continue;
				//AUDIT_ENHANCEMENT_CHANGES:ends
			if(!(forSearch != null && forSearch.equals("search"))){
			if(sModule != null && !("Yes".equalsIgnoreCase(isExportContactHistory)) && ("fsleadCall".equals(sModule.getModuleMainTable()) || "fsOutlookMails".equals(sModule.getModuleMainTable()) || "fsExternalMails".equals(sModule.getModuleMainTable()) || "fsleadRemarks".equals(sModule.getModuleMainTable()) || "fsLeadEmail".equals(sModule.getModuleMainTable()) || "tasksExport".equals(sModule.getModuleMainTable()) || "fsCampaignEmailLog".equals(sModule.getModuleMainTable()) || "leadMailmergeTemplateRel".equals(sModule.getModuleMainTable()) || "ufocSendSchedule".equals(sModule.getModuleMainTable())))
					continue;
			}
			if(!(forSearch != null && forSearch.equals("search"))){
			if(sModule != null && !("Yes".equalsIgnoreCase(isExportContactHistory)) && ("outlookMails".equals(sModule.getModuleMainTable()) || "tasks".equals(sModule.getModuleMainTable()) || "tasksExport".equals(sModule.getModuleMainTable()) || "franchiseeCall".equals(sModule.getModuleMainTable()) || "messageDetail".equals(sModule.getModuleMainTable()) || "fimExternalMail".equals(sModule.getModuleMainTable()) || "outlookMailsExport".equals(sModule.getModuleMainTable()) || "areaCall".equals(sModule.getModuleMainTable()) || "areaOutlookMailsExport".equals(sModule.getModuleMainTable()) || "muCall".equals(sModule.getModuleMainTable()) || "muOutlookMailsExport".equals(sModule.getModuleMainTable())))//P_B_ADMIN_43952
				continue;	    			   
			}
			//BBEH-20110408-055 Added by Nishant  ends
			//BOEFLY_INTEGRATION : START
			if (!"on".equals("off") && ("fsBqualUser".equals(sModule.getModuleMainTable()) || "fsBqual".equals(sModule.getModuleMainTable())))
				continue;
			//BOEFLY_INTEGRATION : END
			//PROVEN_MATCH_INTEGRATION : START
			if (!"on".equals("off") && ("fsPMAssessment".equals(sModule.getModuleMainTable())))
				continue;
		    //PROVEN_MATCH_INTEGRATION : END
			// P_COMMON_E_1111
			String privilege        = sModule.getPrivilege();
			
			SequenceMap userTabConfigMap = UserTabConfigUtil.getUserTabConfig();
						
			if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
				//Added by Omindra Rana
				//User tab config
				if (UserTabConfigUtil.isExportModule(userTabConfigMap, module, sModule) || BuilderFormWebImpl.getInstance().isCustomTab(sModule.getModuleMainTable()))//ENH_MODULE_CUSTOM_TABS 
				{
					if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
					tempsBuffer.append("<tr><td width='2%'></td> <td>");
					tempsBuffer.append(getModuleTableWithPrivileges(sModule,true, userRoleMap,userLanguage,forSearch,null,request));
					tempsBuffer.append("</td></tr>");
						tempMap.put(sModule.getModuleDisplayName(),tempsBuffer);
					}else{
					sBuffer.append("<tr><td width='2%'></td> <td>");
					sBuffer.append(getModuleTableWithPrivileges(sModule,true, userRoleMap,userLanguage,forSearch));
					sBuffer.append("</td></tr>");
					}
				}								
			}
		}
    	
    	if(request!=null&&("fs".equals(moduleName)||"fim".equals(moduleName))){//BB-20150203-259 (Tab Re-ordering changes)
    		sBuffer.append(sortedModuleMap(tempMap,request));
    	}
	}
	//sBuffer.append("<tr><td>&nbsp;</td></tr>");
	sBuffer.append("</table>");
	return sBuffer.toString();
}

public static String getModuleTableForMuEntity(ExportModules module, boolean bSub, UserRoleMap userRoleMap,String userLanguage, String forSearch, ExportModules dependentModule){
	return getModuleTableForMuEntity( module,  bSub,  userRoleMap, userLanguage,  forSearch,  dependentModule,null);
}

public static String getModuleTableForMuEntity(ExportModules module, boolean bSub, UserRoleMap userRoleMap,String userLanguage, String forSearch, ExportModules dependentModule,HttpServletRequest request){
	//BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	String fimFlag=request.getParameter("fimFlag");
	String moduleName="";
	if(request!=null){
	 moduleName=(String)request.getSession().getAttribute("menuName");
	}
	HashMap<String, String> sectionMap = BaseUtils.getModuleTabMap(moduleName);//BB-20150203-259 (Tab Rename) changes
	request.setAttribute("sectionMap",sectionMap);
	StringBuffer tempsBuffer=null;
	SequenceMap tempMap=new SequenceMap();
	StringBuffer sBuffer = new StringBuffer("<table width='100%' cellspacing='0' cellpadding='0'>");
	sBuffer.append("<tr><td colspan=2 class='text' width=\"50%\" >");
	//sBuffer.append("<tr><td" + module.getModuleMainTable() + " class='text'>");
	String tempModuleMainTable =  module.getModuleMainTable();
	String dependentModuleMainTable =  dependentModule.getModuleMainTable();
	//P_E_FS_INTER start added by ram Avtar for internationalization of code
	boolean isFsMenuWithI18n=false;
	if (LanguageUtil.isI18nImplemented())
	{
		isFsMenuWithI18n=true;
	}
	//P_E_FS_INTER ends
	try {
		
			//sBuffer.append("<input type='checkbox' name='selAll' value=");
			sBuffer.append("<input type='checkbox' name='selAll' ");
			sBuffer.append(" onClick='selectAll(this,");
			sBuffer.append("selTables");
			sBuffer.append(")' value =");
			sBuffer.append(tempModuleMainTable);			
			sBuffer.append(">");
			if(isFsMenuWithI18n){//P_E_FS_INTER 
				if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(module.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
				sBuffer.append(LanguageUtil.getString(sectionMap.get(module.getModuleDisplayName()),userLanguage));	
				}else{
				sBuffer.append(LanguageUtil.getString(module.getModuleDisplayName(),userLanguage));
				}
				
			}else{
				if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(module.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
				sBuffer.append(sectionMap.get(module.getModuleDisplayName()));	
				}else{
				sBuffer.append(module.getModuleDisplayName());
				}
			}
			sBuffer.append("</td>");
			if(dependentModule!=null) {
				sBuffer.append("<td colspan=2 class='text'  width=\"50%\" >");
				sBuffer.append("<input type='checkbox' name='selFranAll' ");
				sBuffer.append(" onClick='selectAll(this,");
				sBuffer.append("selTablesFran");
				sBuffer.append(")' value =");
				sBuffer.append(dependentModuleMainTable);			
				sBuffer.append(">");
				if(isFsMenuWithI18n){//P_E_FS_INTER 
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(dependentModule.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
					sBuffer.append(LanguageUtil.getString(sectionMap.get(dependentModule.getModuleDisplayName()),userLanguage));	
					}else{
					sBuffer.append(LanguageUtil.getString(dependentModule.getModuleDisplayName(),userLanguage));
					}
				}else{
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(dependentModule.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
					sBuffer.append(sectionMap.get(dependentModule.getModuleDisplayName()));
					}else{
					sBuffer.append(dependentModule.getModuleDisplayName());	
					}
				}
				sBuffer.append("</td>");
				
			}
			
			
			
			sBuffer.append("</tr>");
			
			
		
	}catch(Exception exp){
		exp.printStackTrace();
	}
	
	
	/**This if block is added to show the primary table's disabled checkbox, 
	   So that one can export only primary table 
	*/
	if (!bSub){		
			//sBuffer.append("<tr><td width='3%'></td> <td  class='text'>");
			sBuffer.append("<tr>");
			sBuffer.append("<td valign='top' class=bText12 colspan=\"2\" width=\"50%\">");
			sBuffer.append("<table cellpadding=0 cellspacing =0  border=0 width=\"100%\">");
			sBuffer.append("<tr><td width='3%'></td> <td>");
			sBuffer.append("<input type='checkbox' disabled  checked name='primaryTable'   value=");
			sBuffer.append(module.getModuleMainTable());
			sBuffer.append(">");
			if(isFsMenuWithI18n)//P_E_FS_INTER 
			{
				 if(module.id.equals("12")){   
					 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Multi Unit Info"))&&!"1".equals(fimFlag)) {
						 sBuffer.append(LanguageUtil.getString(sectionMap.get("Multi Unit Info"),userLanguage));	 
					 }else{
					sBuffer.append(LanguageUtil.getString("Multi Unit Info",userLanguage));
					 }
				 }else if(module.id.equals("39")){    
					 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Entity Detail"))&&!"1".equals(fimFlag)) {
						 sBuffer.append(LanguageUtil.getString(sectionMap.get("Entity Detail"),userLanguage));	 
					 }else{
					sBuffer.append(LanguageUtil.getString("Entity Detail",userLanguage));
					 }
				 }else{
					 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Primary Info"))&&!"1".equals(fimFlag)) {
						 sBuffer.append(LanguageUtil.getString(sectionMap.get("Primary Info"),userLanguage));	 
					 }else{
				sBuffer.append(LanguageUtil.getString("Primary Info",userLanguage));
					 }
				 }
			}
				else
			{
					 if(module.id.equals("12")){   
						 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Multi Unit Info"))&&!"1".equals(fimFlag)) {
							 sBuffer.append(sectionMap.get("Multi Unit Info"));	 
						 }else{
						sBuffer.append("Multi Unit Info");
						 }
					 }else if(module.id.equals("39")){    
						 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Entity Detail"))&&!"1".equals(fimFlag)) {
							 sBuffer.append(sectionMap.get("Entity Detail"));	 
						 }else{
						sBuffer.append("Entity Detail");
						 }
					 }else{
						 if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get("Primary Info"))&&!"1".equals(fimFlag)) {
							 sBuffer.append(sectionMap.get("Primary Info"));	 
						 }else{
					sBuffer.append("Primary Info");
						 }
					 }
					
			}
			sBuffer.append("</td>");
			sBuffer.append("</tr></table></td>");
			if(dependentModule!=null ) {
			SequenceMap subModule		= dependentModule.getSubModuleMap();
			ExportModules sModule	= (ExportModules)subModule.get(0);
			String subModuleMainTable =  sModule.getModuleMainTable();
			sBuffer.append("<td valign='top' class=bText12 colspan=\"2\" width=\"50%\">");
			sBuffer.append("<table cellpadding=0 cellspacing =0  border=0 width=\"100%\">");
			sBuffer.append("<tr><td width='3%'></td> <td>");
			if (sModule.getModuleDependentOn()==null || (sModule.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(sModule.getModuleDependentOn())) || sModule.getModuleDependentTable()!=null && !sModule.getModuleDependentTable().equals(tempModuleMainTable)){
				sBuffer.append("<input type='checkbox' name='selTablesFran'  onclick='javaScript:checkSelTables(this)' value=");
				if (sModule.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(sModule.getModuleDependentOn()) && sModule.getModuleDependentTable()!=null)
					subModuleMainTable = sModule.getModuleDependentTable();
				sBuffer.append(subModuleMainTable);
				sBuffer.append(">");
				if(isFsMenuWithI18n){//P_E_FS_INTER 
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(sModule.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
						sBuffer.append(LanguageUtil.getString(sectionMap.get(sModule.getModuleDisplayName()),userLanguage));	
					}else{
					sBuffer.append(LanguageUtil.getString(sModule.getModuleDisplayName(),userLanguage));
					}
				}else{
					if(sectionMap!=null&&StringUtil.isValidNew(sectionMap.get(sModule.getModuleDisplayName()))&&!"1".equals(fimFlag)) {
						sBuffer.append(sectionMap.get(sModule.getModuleDisplayName()));	
					}else{
					sBuffer.append(sModule.getModuleDisplayName());
					}
				}
				sBuffer.append("</td>");
			}
			sBuffer.append("</tr></table></td>");
			}
			/*sBuffer.append("<td colspan=\"2\" width=\"50%\" >");
			sBuffer.append("</td>");*/
			
			sBuffer.append("</tr>");
			
			
			
	}
	sBuffer.append("<tr>");
	//SequenceMap subModule		= module.getSubModuleMap();	
	if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
		getSubModule(module, userRoleMap, userLanguage, forSearch, sBuffer,null,request);	
	}else{
	getSubModule(module, userRoleMap, userLanguage, forSearch, sBuffer,null);
	}
	if(dependentModule!=null){
		if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
			getSubModule(dependentModule, userRoleMap, userLanguage, forSearch, sBuffer,"selTablesFran",request);	
		}else{
		getSubModule(dependentModule, userRoleMap, userLanguage, forSearch, sBuffer,"selTablesFran");
		}
	}
	sBuffer.append("</tr>");
	sBuffer.append("</table>");
	return sBuffer.toString();
}

public static StringBuffer getSubModule(ExportModules module,UserRoleMap userRoleMap,String userLanguage, String forSearch, StringBuffer sBuffer,String checkBoxName) {
	return getSubModule( module, userRoleMap, userLanguage,  forSearch,  sBuffer, checkBoxName,null);
}
public static StringBuffer getSubModule(ExportModules module,UserRoleMap userRoleMap,String userLanguage, String forSearch, StringBuffer sBuffer,String checkBoxName,HttpServletRequest request) {
	
	//BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	String moduleName="";
	if(request!=null){
	 moduleName=(String)request.getSession().getAttribute("menuName");
	}
	StringBuffer tempsBuffer=null;
	SequenceMap tempMap=new SequenceMap();
SequenceMap subModule		= module.getSubModuleMap();	
	
    if(subModule != null){
    	sBuffer.append("<td valign='top' class=bText12 colspan=\"2\" width=\"50%\">");
    	sBuffer.append("<table cellpadding=0 cellspacing =0  border=0 width=\"100%\"> ");
    	int size				= subModule.size();
    	Info exportSettings=BaseUtils.getExportSettings();
    	String isExportContactHistory=exportSettings.getString(FieldNames.IS_EXPORT_CONTACT);
    	int i=0;
    	if("selTablesFran".equals(checkBoxName)){
    		i=1;
    	}
    	for( ; i < size; i++){
			ExportModules sModule	= (ExportModules)subModule.get(i);			
			if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
				continue;			
			if (ModuleUtil.auditImplemented() && false && "fimQa".equals(sModule.getModuleMainTable()))//P_PW_ENH_FIM_EXPORT
				continue;
			if(!(forSearch != null && forSearch.equals("search"))){
			if(sModule != null && !("Yes".equalsIgnoreCase(isExportContactHistory)) && ("fsleadCall".equals(sModule.getModuleMainTable()) || "fsOutlookMails".equals(sModule.getModuleMainTable()) || "fsExternalMails".equals(sModule.getModuleMainTable()) || "fsleadRemarks".equals(sModule.getModuleMainTable()) || "fsLeadEmail".equals(sModule.getModuleMainTable()) || "tasksExport".equals(sModule.getModuleMainTable()) || "fsCampaignEmailLog".equals(sModule.getModuleMainTable()) || "leadMailmergeTemplateRel".equals(sModule.getModuleMainTable()) || "ufocSendSchedule".equals(sModule.getModuleMainTable())))
					continue;
			}
			if(!(forSearch != null && forSearch.equals("search"))){
			if(sModule != null && !("Yes".equalsIgnoreCase(isExportContactHistory)) && ("outlookMails".equals(sModule.getModuleMainTable()) || "tasks".equals(sModule.getModuleMainTable()) || "tasksExport".equals(sModule.getModuleMainTable()) || "franchiseeCall".equals(sModule.getModuleMainTable()) || "messageDetail".equals(sModule.getModuleMainTable()) || "fimExternalMail".equals(sModule.getModuleMainTable()) || "outlookMailsExport".equals(sModule.getModuleMainTable()) || "areaCall".equals(sModule.getModuleMainTable()) || "areaOutlookMailsExport".equals(sModule.getModuleMainTable()) || "muCall".equals(sModule.getModuleMainTable()) || "muOutlookMailsExport".equals(sModule.getModuleMainTable())))//P_B_ADMIN_43952
				continue;	    			   
			}
			if (!"on".equals("off") && ("fsBqualUser".equals(sModule.getModuleMainTable()) || "fsBqual".equals(sModule.getModuleMainTable())))
				continue;
			if (!"on".equals("off") && ("fsPMAssessment".equals(sModule.getModuleMainTable())))
				continue;
		    String privilege        = sModule.getPrivilege();
			
			SequenceMap userTabConfigMap = UserTabConfigUtil.getUserTabConfig();
						
			if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
				if (UserTabConfigUtil.isExportModule(userTabConfigMap, module, sModule) || BuilderFormWebImpl.getInstance().isCustomTab(sModule.getModuleMainTable()))//ENH_MODULE_CUSTOM_TABS
				{
					if("fs".equals(moduleName)||"fim".equals(moduleName)){//BB-20150203-259 (Tab Re-ordering changes)
						tempsBuffer=new StringBuffer();
						tempsBuffer.append("<tr><td width='3%'></td> <td>");
						tempsBuffer.append(getModuleTableWithPrivileges(sModule,true, userRoleMap,userLanguage,forSearch,checkBoxName,request));
						tempsBuffer.append("</td></tr>");	
							tempMap.put(sModule.getModuleDisplayName(),tempsBuffer);
					}else{
					sBuffer.append("<tr><td width='3%'></td> <td>");
					sBuffer.append(getModuleTableWithPrivileges(sModule,true, userRoleMap,userLanguage,forSearch,checkBoxName));
					sBuffer.append("</td></tr>");
					}
				}								
			}
		}
    	if(request!=null&&("fs".equals(moduleName)||"fim".equals(moduleName))){//BB-20150203-259 (Tab Re-ordering changes)
    		sBuffer.append(sortedModuleMap(tempMap,request));
    	}
    	sBuffer.append("</table></td>");
	}
	
	return sBuffer;
}

public static String getModuleTableListWithPrivileges(ExportModules module, boolean bSub, UserRoleMap userRoleMap){
	StringBuffer sBuffer = new StringBuffer("");
	String tempModuleMainTable =  module.getModuleMainTable();
	boolean isFsMenuWithI18n=false;
	if (LanguageUtil.isI18nImplemented())
	{
		isFsMenuWithI18n=true;
	}
	try {
		if (bSub) {
			if (module.getModuleDependentOn()==null || (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn())) || module.getModuleDependentTable()!=null && !module.getModuleDependentTable().equals(tempModuleMainTable)){
				sBuffer.append(",");
				if (module.getModuleDependentOn()!=null && ModuleUtil.isModuleImplemented(module.getModuleDependentOn()) && module.getModuleDependentTable()!=null)
					tempModuleMainTable = module.getModuleDependentTable();
				sBuffer.append(tempModuleMainTable);
			}
		}
		else{
			
		}	
	}catch(Exception exp){
		exp.printStackTrace();
	}
	/**This if block is added to show the primary table's disabled checkbox, 
	   So that one can export only primary table 
	*/
	if (!bSub){

	}
	SequenceMap subModule		= module.getSubModuleMap();
    if(subModule != null){
    	int size				= subModule.size();
		for(int i=0; i < size; i++){
			ExportModules sModule	= (ExportModules)subModule.get(i);
			if(sModule != null  && "ufocSendSchedule".equals(sModule.getModuleMainTable()) && !ModuleUtil.ufocImplemented())
				continue;
			String privilege        = sModule.getPrivilege();
	
			if(privilege==null || userRoleMap.isPrivilegeInMap(privilege)){
				sBuffer.append(getModuleTableListWithPrivileges(sModule,true, userRoleMap));
			}
		}
	}
	//sBuffer.append("<tr><td>&nbsp;</td></tr>");
	return sBuffer.toString();
}

/**
 * @author Naman Jain
 * @param tableSize
 * @param currPageStr
 * @param paggingUrl
 * @param pageSize
 * this function is used to create the pagging in the export from Modules corresponding to it.
 * @return
 */
public static String createPagging( String tableSize, String currPageStr, String paggingUrl,String pageSize) {

	StringBuffer paggingData = new StringBuffer();
	try {
		int curPage = 1;
		int recPerPage = 50;
		int totalSize = 0;
		String pagingJavascript = "submitPageForm";

		String displayShowAll="no";
		String showPageListCombo="no";
		if(tableSize!=null && !tableSize.equals("")) {
			totalSize = Integer.parseInt(tableSize);
		}	
		if(pageSize!=null && !pageSize.equals("")) {
			recPerPage = Integer.parseInt(pageSize);
		}
		int noPages = (int) Math.ceil((float)totalSize/(float)recPerPage);
		String reqURI = "";
		boolean currPageStrSet = false;
		if(currPageStr==null || currPageStr.equals("")) {
			currPageStr ="1"; currPageStrSet = true;
		}
		curPage = Integer.parseInt(currPageStr);
		int rectoShow  = curPage*recPerPage;
		if(rectoShow>totalSize) {
			if(curPage==1)
				rectoShow = totalSize;
			else
				rectoShow = (recPerPage * (curPage-1));
		} else {
			if(curPage==1)
				rectoShow = recPerPage;
			else
				rectoShow = (recPerPage * (curPage-1));
		}
		int startData = 0;
		int endData = 0;
		int endPage = ((int) Math.ceil((float)curPage/8))*8;
		if(endPage>noPages)
			endPage	= noPages;
		int startPage = 1;
		if(endPage >7)
			startPage = endPage-7;

		paggingData.append("<TABLE WIDTH=\"100%\" BORDER=\"0\">\n ");
		paggingData.append("<TR><TD CLASS=\"bText12\" align=right nowrap>\n");
		if(totalSize>0) {
			if(curPage==0) {
				recPerPage=totalSize;//all
				rectoShow=totalSize;//all
				startData = 0;
				endData = totalSize;
				endPage = ((int) Math.ceil((float)1/8))*8;
				if(endPage>noPages)	endPage	= noPages;
				startPage = 1;
				if(endPage >7) startPage = endPage-7;
				paggingData.append(" "+LanguageUtil.getString("Items")+" <b>1 - "+endData+"</b> "+ LanguageUtil.getString("of")+" "+totalSize);
			} else if(curPage==1) {
				startData = 0;
				endData = rectoShow;
				paggingData.append(" "+LanguageUtil.getString("Items")+" <b>"+curPage+" - "+endData+"</b> "+ LanguageUtil.getString("of")+" "+totalSize);
			} else {
				startData = rectoShow;
				endData = rectoShow+recPerPage;
				if(endData>=totalSize)
					endData = totalSize;
				paggingData.append(" "+LanguageUtil.getString("Items")+" <b>"+(rectoShow+1)+" - "+endData+"</b> "+ LanguageUtil.getString("of")+" "+totalSize);
			}
		} else {
			//paggingData.append("No data found.<br>");
		} if(!pagingJavascript.equals("")){
			paggingData.append("<input type=\"hidden\" name=\"pageid\" value=\"1\">");
			paggingData.append("<input type=\"hidden\" name=\"load\" value=\"false\">");
		}
		if(totalSize>=recPerPage || curPage==0) {
			if (showPageListCombo == null || !showPageListCombo.equals("yes")) {
				if(curPage==1 || curPage==0) {
					paggingData.append("&nbsp;|&nbsp;"+LanguageUtil.getString("First")+"&nbsp;|&nbsp;"+LanguageUtil.getString("Prev")+"&nbsp;|&nbsp;");
				} else {
					if(pagingJavascript.equals(""))
						paggingData.append("&nbsp;|&nbsp;<a href=\""+reqURI+"?pageid=1&load=false"+paggingUrl+"\" title=\"Go to page 1\"><u>First</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?pageid="+(curPage-1)+"&load=false"+paggingUrl+"\" title=\"Go to page "+(curPage-1)+"\" class=\"text\"><u>Prev</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;");
					else
						paggingData.append("&nbsp;|&nbsp;<a href=\"javascript:"+pagingJavascript+"(1,false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page 1\"><u>First</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+(curPage-1)+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+(curPage-1)+"\" class=\"text\"><u>Prev</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;");
				}
				for(int i=startPage;i<=endPage;i++) {
					if(i==startPage) {
						if(pagingJavascript.equals(""))
							paggingData.append(((i==curPage) ? "<b>"+i+"</b>" : "<a href=\""+reqURI+"?pageid="+i+"&load=false"+paggingUrl+"\" title=\" "+LanguageUtil.getString("Go to page")+" "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
						else
							paggingData.append(((i==curPage) ? "<b>"+i+"</b>" : "<a href=\"javascript:"+pagingJavascript+"("+i+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\" "+LanguageUtil.getString("Go to page")+" "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
					} else {
						if(pagingJavascript.equals(""))
							paggingData.append(((i==curPage) ? "&nbsp;&nbsp;|&nbsp;&nbsp;<b>"+i+"</b>" : "&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?pageid="+i+"&load=false"+paggingUrl+"\" title=\" "+LanguageUtil.getString("Go to page")+" "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
						else
							paggingData.append(((i==curPage) ? "&nbsp;&nbsp;|&nbsp;&nbsp;<b>"+i+"</b>" : "&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+i+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\""+LanguageUtil.getString("Go to page")+" "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
					}
				}

				if(curPage==noPages || curPage==0)
					paggingData.append("&nbsp;|&nbsp;"+LanguageUtil.getString("Next")+"&nbsp;|&nbsp;"+LanguageUtil.getString("Last")+"&nbsp;|&nbsp;");
				else{
					if(pagingJavascript.equals(""))
						paggingData.append("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?pageid="+(curPage+1)+"&load=false"+paggingUrl+"\" title=\" "+LanguageUtil.getString("Go to page")+" "+(curPage+1)+"\"><u>"+LanguageUtil.getString("Next")+"</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?pageid="+noPages+"&load=false"+paggingUrl+"\" title=\" "+LanguageUtil.getString("Go to page")+" "+noPages+"\" class=\"text\"><u>"+LanguageUtil.getString("Last")+"</u></a>&nbsp;|&nbsp;");
					else
						paggingData.append("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+(curPage+1)+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\" "+LanguageUtil.getString("Go to page")+" "+(curPage+1)+"\"><u>"+LanguageUtil.getString("Next")+"</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+noPages+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\" "+LanguageUtil.getString("Go to page")+" "+noPages+"\" class=\"text\"><u>"+LanguageUtil.getString("Last")+"</u></a>&nbsp;|&nbsp;");
				}
			}
			if (showPageListCombo != null && showPageListCombo.equals("yes") && totalSize>50) {
				paggingData.append("&nbsp;|&nbsp;");					
			}
			if(displayShowAll.equals("yes")) {
				paggingData.append("<a href=\""+reqURI+"?pageid=0&load=false"+paggingUrl+"\" title=\" "+LanguageUtil.getString("Go to page")+" "+noPages+"\" class=\"text\"><u>"+LanguageUtil.getString("Show All")+"</u></a>");
			}
		}

		if (showPageListCombo != null && showPageListCombo.equals("yes") && totalSize>30){
			StringBuffer tempPaggingUrl = new StringBuffer(paggingUrl);
			if (paggingUrl!=null && paggingUrl.indexOf("pageid") != -1) {
				tempPaggingUrl.delete(tempPaggingUrl.indexOf("pageid"), tempPaggingUrl.indexOf("&", tempPaggingUrl.indexOf("pageid"))+1);
			}
			paggingData.append("\n<script language='JavaScript'>\n<!--  \n function gotoPageNo(pageList){ \n document.goToPageNoForm.action = \"exportHtmlView?load=false"+tempPaggingUrl+"\";\n ");
			paggingData.append("document.goToPageNoForm.pageid.value = pageList.options[pageList.selectedIndex].value;\n ");
			paggingData.append("if(pageList.options[pageList.selectedIndex].value!=-1)\n ");
			paggingData.append("document.goToPageNoForm.submit();\n ");
			paggingData.append("}\n ");
			paggingData.append("//--></script>\n ");
			paggingData.append("&nbsp;&nbsp;<select class=\"multiList\" name=\"pageList\" onChange=\"gotoPageNo(this)\">");
			paggingData.append("<option value=\"-1\">"+LanguageUtil.getString("Go to Page")+"</option>");
			for(int i=1;i<=noPages;i++) {
				if(i==curPage && currPageStrSet == false) {
					paggingData.append("<option selected value="+i+">"+i+"</option>");
				} else {
					paggingData.append("<option value="+i+">"+i+"</option>");
				}
			}
			paggingData.append("</SELECT>");
			tempPaggingUrl = null;
		}
		paggingData.append("</TD></TR></TABLE>\n");

	} catch(Exception e) {
		System.out.println("pagging "+e);
		//return paggingData.toString();
	}
	return paggingData.toString();
}


public static String muFranCreatePagging( String tableSize, String currPageStr, String paggingUrl,String pageSize) {

	StringBuffer paggingData = new StringBuffer();
	try {
		int curPage = 1;
		int recPerPage = 50;
		int totalSize = 0;
		String pagingJavascript = "muFranSubmitPageForm";

		String displayShowAll="no";
		String showPageListCombo="no";
		if(tableSize!=null && !tableSize.equals("")) {
			totalSize = Integer.parseInt(tableSize);
		}	
		if(pageSize!=null && !pageSize.equals("")) {
			recPerPage = Integer.parseInt(pageSize);
		}
		int noPages = (int) Math.ceil((float)totalSize/(float)recPerPage);
		String reqURI = "";
		boolean currPageStrSet = false;
		if(currPageStr==null || currPageStr.equals("")) {
			currPageStr ="1"; currPageStrSet = true;
		}
		curPage = Integer.parseInt(currPageStr);
		int rectoShow  = curPage*recPerPage;
		if(rectoShow>totalSize) {
			if(curPage==1)
				rectoShow = totalSize;
			else
				rectoShow = (recPerPage * (curPage-1));
		} else {
			if(curPage==1)
				rectoShow = recPerPage;
			else
				rectoShow = (recPerPage * (curPage-1));
		}
		int startData = 0;
		int endData = 0;
		int endPage = ((int) Math.ceil((float)curPage/8))*8;
		if(endPage>noPages)
			endPage	= noPages;
		int startPage = 1;
		if(endPage >7)
			startPage = endPage-7;

		paggingData.append("<TABLE WIDTH=\"100%\" BORDER=\"0\">\n ");
		paggingData.append("<TR><TD CLASS=\"bText12\" align=right nowrap>\n");
		if(totalSize>0) {
			if(curPage==0) {
				recPerPage=totalSize;//all
				rectoShow=totalSize;//all
				startData = 0;
				endData = totalSize;
				endPage = ((int) Math.ceil((float)1/8))*8;
				if(endPage>noPages)	endPage	= noPages;
				startPage = 1;
				if(endPage >7) startPage = endPage-7;
				paggingData.append(" Items <b>1 - "+endData+"</b> of "+totalSize);
			} else if(curPage==1) {
				startData = 0;
				endData = rectoShow;
				paggingData.append("Items <b>"+curPage+" - "+endData+"</b> of "+totalSize);
			} else {
				startData = rectoShow;
				endData = rectoShow+recPerPage;
				if(endData>=totalSize)
					endData = totalSize;
				paggingData.append("Items <b>"+(rectoShow+1)+" - "+endData+"</b> of  "+totalSize);
			}
		} else {
			//paggingData.append("No data found.<br>");
		} if(!pagingJavascript.equals("")){
			paggingData.append("<input type=\"hidden\" name=\"muFranPageid\" value=\"1\">");
			paggingData.append("<input type=\"hidden\" name=\"load\" value=\"false\">");
		}
		if(totalSize>=recPerPage || curPage==0) {
			if (showPageListCombo == null || !showPageListCombo.equals("yes")) {
				if(curPage==1 || curPage==0) {
					paggingData.append("&nbsp;|&nbsp;First&nbsp;|&nbsp;Prev&nbsp;|&nbsp;");
				} else {
					if(pagingJavascript.equals(""))
						paggingData.append("&nbsp;|&nbsp;<a href=\""+reqURI+"?muFranPageid=1&load=false"+paggingUrl+"\" title=\"Go to page 1\"><u>First</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?muFranPageid="+(curPage-1)+"&load=false"+paggingUrl+"\" title=\"Go to page "+(curPage-1)+"\" class=\"text\"><u>Prev</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;");
					else
						paggingData.append("&nbsp;|&nbsp;<a href=\"javascript:"+pagingJavascript+"(1,false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page 1\"><u>First</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+(curPage-1)+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+(curPage-1)+"\" class=\"text\"><u>Prev</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;");
				}
				for(int i=startPage;i<=endPage;i++) {
					if(i==startPage) {
						if(pagingJavascript.equals(""))
							paggingData.append(((i==curPage) ? "<b>"+i+"</b>" : "<a href=\""+reqURI+"?muFranPageid="+i+"&load=false"+paggingUrl+"\" title=\"Go to page "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
						else
							paggingData.append(((i==curPage) ? "<b>"+i+"</b>" : "<a href=\"javascript:"+pagingJavascript+"("+i+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
					} else {
						if(pagingJavascript.equals(""))
							paggingData.append(((i==curPage) ? "&nbsp;&nbsp;|&nbsp;&nbsp;<b>"+i+"</b>" : "&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?muFranPageid="+i+"&load=false"+paggingUrl+"\" title=\"Go to page "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
						else
							paggingData.append(((i==curPage) ? "&nbsp;&nbsp;|&nbsp;&nbsp;<b>"+i+"</b>" : "&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+i+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+i+"\" class=\"text\"><u>"+i+"</u></a>"));
					}
				}

				if(curPage==noPages || curPage==0)
					paggingData.append("&nbsp;|&nbsp;Next&nbsp;|&nbsp;Last&nbsp;|&nbsp;");
				else{
					if(pagingJavascript.equals(""))
						paggingData.append("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?muFranPageid="+(curPage+1)+"&load=false"+paggingUrl+"\" title=\"Go to page "+(curPage+1)+"\"><u>Next</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\""+reqURI+"?muFranPageid="+noPages+"&load=false"+paggingUrl+"\" title=\"Go to page "+noPages+"\" class=\"text\"><u>Last</u></a>&nbsp;|&nbsp;");
					else
						paggingData.append("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+(curPage+1)+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+(curPage+1)+"\"><u>Next</u></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:"+pagingJavascript+"("+noPages+",false,"+paggingUrl+String.valueOf(recPerPage)+");\" title=\"Go to page "+noPages+"\" class=\"text\"><u>Last</u></a>&nbsp;|&nbsp;");
				}
			}
			if (showPageListCombo != null && showPageListCombo.equals("yes") && totalSize>50) {
				paggingData.append("&nbsp;|&nbsp;");					
			}
			if(displayShowAll.equals("yes")) {
				paggingData.append("<a href=\""+reqURI+"?muFranPageid=0&load=false"+paggingUrl+"\" title=\"Go to page "+noPages+"\" class=\"text\"><u>Show All</u></a>");
			}
		}

		if (showPageListCombo != null && showPageListCombo.equals("yes") && totalSize>30){
			StringBuffer tempPaggingUrl = new StringBuffer(paggingUrl);
			if (paggingUrl!=null && paggingUrl.indexOf("muFranPageid") != -1) {
				tempPaggingUrl.delete(tempPaggingUrl.indexOf("muFranPageid"), tempPaggingUrl.indexOf("&", tempPaggingUrl.indexOf("muFranPageid"))+1);
			}
			paggingData.append("\n<script language='JavaScript'>\n<!--  \n function gotoPageNo(pageList){ \n document.goToPageNoForm.action = \"exportHtmlView?load=false"+tempPaggingUrl+"\";\n ");
			paggingData.append("document.goToPageNoForm.muFranPageid.value = pageList.options[pageList.selectedIndex].value;\n ");
			paggingData.append("if(pageList.options[pageList.selectedIndex].value!=-1)\n ");
			paggingData.append("document.goToPageNoForm.submit();\n ");
			paggingData.append("}\n ");
			paggingData.append("//--></script>\n ");
			paggingData.append("&nbsp;&nbsp;<select class=\"multiList\" name=\"muFranPageList\" onChange=\"gotoPageNo(this)\">");
			paggingData.append("<option value=\"-1\">Go to Page</option>");
			for(int i=1;i<=noPages;i++) {
				if(i==curPage && currPageStrSet == false) {
					paggingData.append("<option selected value="+i+">"+i+"</option>");
				} else {
					paggingData.append("<option value="+i+">"+i+"</option>");
				}
			}
			paggingData.append("</SELECT>");
			tempPaggingUrl = null;
		}
		paggingData.append("</TD></TR></TABLE>\n");

	} catch(Exception e) {
		System.out.println("pagging "+e);
		//return paggingData.toString();
	}
	return paggingData.toString();
}
/**
 * BB-20150203-259
 * @author Akash Kumar
 * @param moduleMap
 * @param request
 * @return this method returns sorted String of tabs in export and Advance Search
 */
public static StringBuffer sortedModuleMap(SequenceMap moduleMap,HttpServletRequest request){
	BuilderFormWebImpl builderWebImpl = BuilderFormWebImpl.getInstance();
	SequenceMap sMap=new SequenceMap();
	SequenceMap sMap1=new SequenceMap();
	int i=0,j=0;
	String fimFlag=request.getParameter("fimFlag");
	StringBuffer str=new StringBuffer();
	String module=(String)request.getSession().getAttribute("menuName");
	request.setAttribute(FieldNames.MODULE,module);
	SequenceMap tabMapXml=builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getBuilderTabDataMapFromXMl(request);
	if(tabMapXml!=null&&!"1".equals(fimFlag)){
		for(j=0;j<tabMapXml.size();j++){
			Info currentInfo = (Info) tabMapXml.get(j);
			for( i=0;i<moduleMap.size();i++){
				if((currentInfo.getString("tabDisplayName").trim()).equals(((String)moduleMap.getKey(i)).trim())){
					sMap1.put(moduleMap.getKey(i),moduleMap.get(moduleMap.getKey(i)));
				}else{
					if(!sMap1.containsKey(moduleMap.getKey(i))){
						sMap.put(moduleMap.getKey(i),moduleMap.get(moduleMap.getKey(i)));
					}
				}
			}
		}
		
		for(i=0;i<sMap1.size();i++){
			str.append(sMap1.get(i));
			sMap.remove(sMap1.getKey(i));
		}
		for(i=0;i<sMap.size();i++){
			str.append(sMap.get(i));
		}
	}else{
		for(i=0;i<moduleMap.size();i++){
			str.append(moduleMap.get(i));
		}	
	}
	return str;
}
};
