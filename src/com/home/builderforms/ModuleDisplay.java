/*
----------------------------------------------------------------------------------------------------------
 Version No.			Date		 		By	        		Against				Function Changed 						Comments
----------------------------------------------------------------------------------------------------------
 BB_E_CONF_MODSTARTUP  	10 Sep 2010     	Deepak Raj Purohit  Configure module startup like laquinta
 * D_B_75432              28-June-2011        Vishal Lodha    CT module is not showing in Configure startup module.
         * SC_Sch_B_6386              26-Mar-2012        Sonalika                               Bug                           ModuleDisplay()           Showing Scheduler module for Franchise Roles not having sch privilege.
Zcubator_module_startup_18423 22 -Mar-2013    Divya Mishra     Module start up not displaying zcubator on login

BBEH_CLUSTER_ENVIRONMENT  05/06/2013      Rohit Jain    Class is serialized For cluster Environment.
P_ADM_B_26908			16/08/13		Chinmay Pareek			 	franchisee websites configuration issue.
P_ENH_RM_50882			11/12/2014		Rajat Gupta					Roles for RM.
P_B_RM_51640			15/12/2014		Rajat Gupta					added regional user privilege. 
---------------------------------------------------------------------------------------------------------- 
*/
package com.home.builderforms;

import javax.servlet.http.HttpSession;
import com.home.builderforms.sqlqueries.SQLUtil;

import com.home.builderforms.UserRoleMap;

//import com.home.builderforms.base.MenuUtils;

public class ModuleDisplay implements java.io.Serializable {
	
	/*BB_E_CONF_MODSTARTUP starts*/
	public boolean isDisplayIntranet = false;
	public boolean isDisplaySmartConnect = false;//ENH_SMARTCONNECT_SEP
	public boolean isDisplayFS = false;
	public boolean isDisplayFDD = false;
	public boolean isDisplayFO = false;
	public boolean isDisplayFIM = false;
	public boolean isDisplaySupport = false;
	
	public boolean isDisplayFinancial = false;
	public boolean isDisplayFinForCorp = false;
	public boolean isDisplayFinForRegional = false;
	public boolean isDisplayFinForFranchisee = false;
	
	public boolean isDisplayContactManager = false;
	public boolean isDisplayScheduler = false;
	public boolean isDisplayCustomerTransaction = false;
	
	public boolean isDisplayFranchiseWebsite = false;
	public boolean isDisplayTraining = false;
	public boolean isDisplayAdBuilder = false;
	public boolean isDisplaySupplies = false;
	public boolean isDisplayDashboard = false;
	/*BB_E_CONF_MODSTARTUP ends*/
	public boolean isDisplayDispatchBoard = false;
	public boolean isDisplayAnalytics = false;
	public boolean isDisplayHome = false;
	public boolean isDisplaySchedulerHome = false;
	//Add by Sanshey Sachdeva
	public boolean isDisplayAuditHome = false;  // AUDIT_ENHANCEMENT_CHANGES
	public boolean isDisplayPPC = false;  // PPC_ENHANCEMENT_CHANGES
	public boolean isDisplayZcubator = false;   // Zcubator_module_startup_18423
	
	public boolean isDisplayLP = false;
	public boolean isDisplayRM = false;
	public boolean isDisplayFB = false;
	public boolean isDisplayMP = false;
	public boolean isDisplayMD = false;
	public boolean isDisplayLL = false;
	private SequenceMap isModuleDisplayMap = null;
	private String displayedModules = null;
	private static ModuleDisplay _moduleDisplay = null;
	
	public boolean isDisplayIntelligence= false;
	
	private ModuleDisplay(){
		
	}
	
	public static ModuleDisplay getModuleDisplay(HttpSession session){
			if(session.getAttribute(FieldNames.MODULE_DISPLAY)!=null){
                                _moduleDisplay = (ModuleDisplay) session.getAttribute(FieldNames.MODULE_DISPLAY);
			}else{
				_moduleDisplay = new ModuleDisplay(session);
				session.setAttribute(FieldNames.MODULE_DISPLAY, _moduleDisplay);


			}
		
		return _moduleDisplay;
	}
	
	private ModuleDisplay(HttpSession session){
		UserRoleMap leftUserRoleMap = (UserRoleMap)session.getAttribute("userRoleMap");
		String userLvl = (String)session.getAttribute("user_level");
		String isStoreVisible=(String)session.getAttribute("isStoreVisible");
		/*BB_E_CONF_MODSTARTUP starts*/
		if(StringUtil.isValid(userLvl) && leftUserRoleMap!= null) {
		isDisplayIntranet = ModuleUtil.intranetImplemented() && (((userLvl.equals("0") ||userLvl.equals("6"))  && leftUserRoleMap.isModuleInMap("14")) || (userLvl.equals("2") && leftUserRoleMap.isModuleInMap("99")) || (userLvl.equals("3") && leftUserRoleMap.isModuleInMap("99"))  || (userLvl.equals("1") && leftUserRoleMap.isModuleInMap("45")));
		//isDisplaySmartConnect = MenuUtils.canAccessSmartConnect(leftUserRoleMap, userLvl, (String)session.getAttribute("franchisee_no"));//ENH_SMARTCONNECT_SEP 
		isDisplaySmartConnect = BaseUtils.canAccessSmartConnect(leftUserRoleMap, userLvl, (String)session.getAttribute("franchisee_no"));//ENH_SMARTCONNECT_SEP    //For Product_Seperation_BL By Amar Singh.
		isDisplayFS = ModuleUtil.fsImplemented() && (((userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("2030")) || (userLvl.equals("2") && leftUserRoleMap.isModuleInMap("2230")) || (userLvl.equals("3") && leftUserRoleMap.isModuleInMap("2230"))) ;
		isDisplayFDD = ModuleUtil.ufocImplemented() && (((userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("2030")) || (userLvl.equals("2") && leftUserRoleMap.isModuleInMap("2230")) || (userLvl.equals("3") && leftUserRoleMap.isModuleInMap("2230")) );
		isDisplayFO =  ModuleUtil.storeopenerImplemented() && ((leftUserRoleMap.isModuleInMap("93") && userLvl.equals("1") && (isStoreVisible!=null && isStoreVisible.equals("true"))) || (leftUserRoleMap.isModuleInMap("92") && (userLvl.equals("0") ||userLvl.equals("6")))|| (leftUserRoleMap.isModuleInMap("94") && userLvl.equals("2")));
		isDisplayFIM = ModuleUtil.fimImplemented() && (((userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("53")) || (userLvl.equals("2")  && leftUserRoleMap.isModuleInMap("5000")) || (userLvl.equals("3") && leftUserRoleMap.isModuleInMap("5000"))) ;
		isDisplaySupport = ModuleUtil.supportImplemented()   && (((userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("23")) || (userLvl.equals("2")  && leftUserRoleMap.isModuleInMap("216")) || (userLvl.equals("1") && leftUserRoleMap.isModuleInMap("217"))) ;
		
		isDisplayFranchiseWebsite = ModuleUtil.wbImplemented() && ((leftUserRoleMap.isModuleInMap("8") && userLvl.equals("1")) || (leftUserRoleMap.isModuleInMap("21") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isModuleInMap("10") && userLvl.equals("2")));    //P_ADM_B_26908 Modified by Chinmay Pareek
		isDisplayTraining = ModuleUtil.trainingImplemented() && ((leftUserRoleMap.isModuleInMap("10001") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isModuleInMap("10002") && userLvl.equals("1"))|| (leftUserRoleMap.isModuleInMap("10003") && userLvl.equals("2"))  || (leftUserRoleMap.isModuleInMap("10003") && userLvl.equals("3")));
		isDisplayAdBuilder = ModuleUtil.admakerImplemented() && ((leftUserRoleMap.isPrivilegeIDInMap("12101") && (userLvl.equals("0") ||userLvl.equals("6")) ) || (leftUserRoleMap.isPrivilegeIDInMap("12301") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("12201") && userLvl.equals("2")));
		isDisplaySupplies = ModuleUtil.suppliesImplemented() && (leftUserRoleMap.isModuleInMap("11") && (userLvl.equals("0") ||userLvl.equals("6")) || userLvl.equals("1") && leftUserRoleMap.isModuleInMap("12") || (userLvl.equals("2") && leftUserRoleMap.isModuleInMap("13")));
		isDisplayDashboard = ModuleUtil.dashboardImplemented() && (((userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("20012")) || (userLvl.equals("2") && leftUserRoleMap.isModuleInMap("20013")));
		
		isDisplayContactManager = ModuleUtil.cmImplemented()&& leftUserRoleMap != null && !leftUserRoleMap.isPrivilegeInMap("/onlyDispatchBoard") && ((leftUserRoleMap.isModuleInMap("4") && userLvl.equals("1")) || (leftUserRoleMap.isModuleInMap("204") && (userLvl.equals("0") ||userLvl.equals("6")) ||leftUserRoleMap.isModuleInMap("5") && userLvl.equals("2")));
        isDisplayScheduler = ModuleUtil.schedulerImplemented() && leftUserRoleMap!=null && userLvl!= null && (leftUserRoleMap.isModuleInMap("10048") && ( (userLvl.equals("0") ||userLvl.equals("6")) || (userLvl.equals("1"))|| (userLvl.equals("2"))  || (userLvl.equals("3")) ));//SC_Sch_B_6386
                     
		isDisplayCustomerTransaction = ModuleUtil.tmsImplemented() && leftUserRoleMap!=null && userLvl!= null && ((leftUserRoleMap.isModuleInMap("10012") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isPrivilegeIDInMap("10014") && userLvl.equals("1"))|| (userLvl.equals("2"))  || (userLvl.equals("3")));//D_B_75432 Added By Vishal Lodha
		
		if(ModuleUtil.financialsImplemented() && leftUserRoleMap!=null && userLvl!= null){
			if(leftUserRoleMap!=null && userLvl!= null && (userLvl.equals("0") ||userLvl.equals("6")) && leftUserRoleMap.isModuleInMap("9")){
				isDisplayFinForCorp = true;
			}else if(userLvl.equals("1") &&  leftUserRoleMap.isModuleInMap("999")){
				isDisplayFinForFranchisee = true;
			}else if(userLvl.equals("2") &&  leftUserRoleMap.isModuleInMap("9999")){
				isDisplayFinForRegional = true;
			}
			isDisplayFinancial = isDisplayFinForCorp || isDisplayFinForFranchisee || isDisplayFinForRegional;
		}
		/*BB_E_CONF_MODSTARTUP ends*/
		isDisplayDispatchBoard = ModuleUtil.dispatchImplemented() && leftUserRoleMap!=null && leftUserRoleMap.isModuleInMap("106") && userLvl!= null  && (userLvl.equals("0") ||userLvl.equals("6"));
		isDisplayAnalytics = ModuleUtil.summaryImplemented() && leftUserRoleMap!=null && userLvl!= null && ((leftUserRoleMap.isPrivilegeIDInMap("10024") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isPrivilegeIDInMap("100241") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("100242") && userLvl.equals("2")) );  // modified by Prabhat for franchise & regional user privileges
		
		isDisplayHome = ModuleUtil.homeImplemented() &&  leftUserRoleMap!=null && userLvl!= null && !leftUserRoleMap.isPrivilegeInMap("/onlyDispatchBoard") && isDisplayContactManager && isDisplayScheduler && isDisplayCustomerTransaction && isDisplayAnalytics;
		isDisplaySchedulerHome = ModuleUtil.schedulerImplemented() && leftUserRoleMap!=null && userLvl!= null &&((leftUserRoleMap.isPrivilegeIDInMap("11104")&&(userLvl.equals("0") ||userLvl.equals("6")))||(leftUserRoleMap.isPrivilegeIDInMap("111041")&&userLvl.equals("1")));
		//Added by Sanshey Sachdeva
		isDisplayAuditHome = ModuleUtil.auditImplemented() && ((leftUserRoleMap.isModuleInMap("541") && userLvl.equals("1")) || (leftUserRoleMap.isModuleInMap("54") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isModuleInMap("542") && userLvl.equals("2")));		  // AUDIT_ENHANCEMENT_CHANGES  // AUDIT_ENHANCEMENT_CHANGES
		isDisplayPPC = ModuleUtil.ppcImplemented() && ((leftUserRoleMap.isPrivilegeIDInMap("550002") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isPrivilegeIDInMap("551002") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("552002") && userLvl.equals("2")));		  // PPC_ENHANCEMENT_CHANGES
		isDisplayZcubator = ModuleUtil.zcubatorImplemented();  //Zcubator_module_startup_18423
		
		/*BaseConstants _baseConstants =MultiTenancyUtil.getTenantConstants();
		isDisplayLP = ModuleUtil.landingPageImplemented() && _baseConstants.LP_ENABLED && (leftUserRoleMap.isPrivilegeInMap("/cmLandingPages"));
        isDisplayRM = ModuleUtil.reputationManagementmImplemented() &&  _baseConstants.RM_ENABLED && ((leftUserRoleMap.isPrivilegeIDInMap("111093") && ("0".equals(userLvl) ||userLvl.equals("6"))) || ((leftUserRoleMap.isPrivilegeIDInMap("1110105")  && "1".equals(userLvl)))  || (leftUserRoleMap.isPrivilegeIDInMap("1110115") && userLvl.equals("2"))); //P_ENH_RM_50882 //P_B_RM_51640 //P_ENH_20150423_RM_LL_MUID_ISSUE  //ZCUB-20150728-166
		isDisplayFB = ModuleUtil.facebookBuilderImplemented() && _baseConstants.FB_ENABLED && ((leftUserRoleMap.isPrivilegeIDInMap("11105") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isPrivilegeIDInMap("11106") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("11107") && userLvl.equals("2")));
		isDisplayMP = ModuleUtil.marketingPilotImplemented() && "Y".equals(_baseConstants.CM_PILOT_ENABLED) && (leftUserRoleMap.isPrivilegeInMap("/cmMpDetails"));
		isDisplayMD = ModuleUtil.marketingDashBoardImplemented() && ((leftUserRoleMap.isPrivilegeIDInMap("1125001") && (userLvl.equals("0") ||userLvl.equals("6"))) || (leftUserRoleMap.isPrivilegeIDInMap("1126001") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("1127001") && userLvl.equals("2")));
        isDisplayLL = ModuleUtil.localListingImplemented() && _baseConstants.LL_ENABLED && ((leftUserRoleMap.isPrivilegeIDInMap("111090") && (userLvl.equals("0") ||userLvl.equals("6")))  || (leftUserRoleMap.isPrivilegeIDInMap("1110100") && userLvl.equals("1")) || (leftUserRoleMap.isPrivilegeIDInMap("1110110") && userLvl.equals("2"))); //P_ENH_20150423_RM_LL_MUID_ISSUE    //ZCUB-20150728-166
        isDisplayIntelligence = ModuleUtil.intelligenceImplemented();*/
		prepareIsModuleDisplayMap();
		setDisplayModules();
		}
	}
        public static boolean isSchedulerImplementedforHome(HttpSession session){
               boolean flag =false;
               boolean moduleflag = ModuleUtil.schedulerImplemented();
               UserRoleMap leftUserRoleMap = (UserRoleMap)session.getAttribute("userRoleMap");
               if(moduleflag){
                   if(leftUserRoleMap!=null&&leftUserRoleMap.isPrivilegeInMap("/schHome1")){
                         flag= true;
                    }
               }else{
                  flag= false;
               }
               return flag;
        }
	
	public String toString(){
		StringBuffer tabDisplaysummary = new StringBuffer();
		tabDisplaysummary.append("<br>isDisplayContactManager="+isDisplayContactManager);
		tabDisplaysummary.append("<br>isDisplayScheduler="+isDisplayScheduler);
		tabDisplaysummary.append("<br>isDisplayCustomerTransaction="+isDisplayCustomerTransaction);
		tabDisplaysummary.append("<br>isDisplayFinancial="+isDisplayFinancial);
		
		return tabDisplaysummary.toString();
	}
	
	
	/*BB_E_CONF_MODSTARTUP starts*/
	/**
	 * To check that module  will be display or not
	 * @author deepak
	 * @param abbrModuleName
	 * @return
	 */
	public void prepareIsModuleDisplayMap() {
		isModuleDisplayMap = new SequenceMap();
		isModuleDisplayMap.put("smartconnect", isDisplaySmartConnect);
		isModuleDisplayMap.put("intranet", isDisplayIntranet);
		isModuleDisplayMap.put("fs", isDisplayFS);
		isModuleDisplayMap.put("ufoc", isDisplayFDD);
		isModuleDisplayMap.put("sm", isDisplayFO);
		isModuleDisplayMap.put("fim", isDisplayFIM);
		isModuleDisplayMap.put("support",isDisplaySupport);
		isModuleDisplayMap.put("financials",isDisplayFinancial);
		isModuleDisplayMap.put("cm",isDisplayContactManager);
		isModuleDisplayMap.put("dispatch",isDisplayDispatchBoard);
		isModuleDisplayMap.put("scheduler",isDisplayScheduler);
		isModuleDisplayMap.put("tms",isDisplayCustomerTransaction);
		isModuleDisplayMap.put("website",isDisplayFranchiseWebsite);
		isModuleDisplayMap.put("training",isDisplayTraining);
		isModuleDisplayMap.put("adMaker",isDisplayAdBuilder);
		isModuleDisplayMap.put("supplies",isDisplaySupplies);
		isModuleDisplayMap.put("dBoard",isDisplayDashboard);
		isModuleDisplayMap.put("summary",isDisplayAnalytics);
		isModuleDisplayMap.put("Home",isDisplaySchedulerHome);
		isModuleDisplayMap.put("audit",isDisplayAuditHome);
		isModuleDisplayMap.put("ppc",isDisplayPPC);
		isModuleDisplayMap.put("zcubator",isDisplayZcubator); 
		isModuleDisplayMap.put("ll",isDisplayLL);  
		isModuleDisplayMap.put("mp",isDisplayMP);  
		isModuleDisplayMap.put("fb",isDisplayFB);  
		isModuleDisplayMap.put("rm",isDisplayRM);  
		isModuleDisplayMap.put("lp",isDisplayLP);  
		isModuleDisplayMap.put("md",isDisplayMD);
		isModuleDisplayMap.put("intelligence",isDisplayIntelligence);
	}
	
	public boolean isDisplayModule(String abbrModuleName) {
		return (Boolean)isModuleDisplayMap.get(abbrModuleName);
	}
	/*BB_E_CONF_MODSTARTUP ends*/
	
	public void setDisplayModules()
	{
		displayedModules="";
		boolean isDisplay=false;
		for(int i=0;i<isModuleDisplayMap.size();i++)
		{
			isDisplay=(Boolean)isModuleDisplayMap.get(i);
			if(isDisplay)
				displayedModules+=isModuleDisplayMap.getKey(i)+",";
		}
		
		if(displayedModules.length()>0) {
			displayedModules=displayedModules.substring(0,displayedModules.length()-1);
		}
		
	}
	
	public String getDisplayModules()
	{
		return displayedModules;
	}
}
