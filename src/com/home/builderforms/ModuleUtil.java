package com.home.builderforms;

import com.home.builderforms.AppException;
import com.home.builderforms.UserRoleMap;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class ModuleUtil
{
	static Logger logger = Logger.getLogger(ModuleUtil.class);

	public static class MODULE_NAME
	{
		public static final String NAME_FIM = "fim";
		//P_Enh_Mu-Entity_FormGenerator starts
		public static final String NAME_FIM_MU = "mu";
		public static final String NAME_FIM_ENTITY = "entity";
		public static final String NAME_FIM_AREA = "area";
		//P_Enh_Mu-Entity_FormGenerator ends
		public static final String NAME_FS = "fs";
		public static final String NAME_CM = "cm";
		public static final String NAME_SCHEDULER = "scheduler";
		public static final String NAME_ACCOUNT = "account";
		public static final String NAME_OPPORTUNITY = "opportunity";
		public static final String NAME_LEAD = "lead";
		public static final String NAME_SITE = "site"; //P_Enh_Site_Clearance
	}

	public static class MODULE_DISPLAY
	{
		public static final String DISPLAY_FIM = "FIM";
		//P_Enh_Mu-Entity_FormGenerator starts
		public static final String DISPLAY_FIM_MU = "Multi-Unit";
		public static final String DISPLAY_FIM_ENTITY = "Entity";
		public static final String DISPLAY_FIM_AREA = "Regional";  //BUG_73204
		//P_Enh_Mu-Entity_FormGenerator ends
		public static final String DISPLAY_FS = "Franchise Sales";
		public static final String DISPLAY_CM = "Contact Manager";
		public static final String DISPLAY_SCHEDULER = "Jobs";
		public static final String DISPLAY_ACCOUNT = "Account";
		public static final String DISPLAY_OPPORTUNITY = "Opportunity";
		public static final String DISPLAY_LEAD = "Lead";
		public static final String DISPLAY_SITE = "Site Clearance"; //P_Enh_Site_Clearance
		public static final String DISPLAY_FIM_FRANCHISEE = "Franchisee";
	}

	public static class MODULE_ID
	{
		public static final int ID_FIM = 3;
		public static final int ID_FS = 2;
		public static final int ID_CM = 4;
		public static final int ID_ACCOUNT = 5;
		public static final int ID_OPPORTUNITY = 6;
		public static final int ID_LEAD = 7;
		public static final int ID_SCHEDULER = 8;
		public static final int ID_SITE = 9; //P_Enh_Site_Clearance
		//P_Enh_Mu-Entity_FormGenerator starts
		public static final int ID_FIM_MU = 10;
		public static final int ID_FIM_ENTITY = 11;
		public static final int ID_FIM_AREA = 12;
		//P_Enh_Mu-Entity_FormGenerator ends
	}

	public static class MODULE_KEY
	{
		public static final String KEY_FIM_AREA = FieldNames.AREA_INFO_ID;
		public static final String KEY_FIM_MU = FieldNames.MU_ID;
		public static final String KEY_FIM = FieldNames.ENTITY_ID;
		public static final String KEY_FS = FieldNames.LEAD_ID;
		public static final String KEY_CM = FieldNames.CONTACT_ID;
		public static final String KEY_ACCOUNT = FieldNames.COMPANY_ID;
		public static final String KEY_OPPORTUNITY = FieldNames.OPPORTUNITY_ID;
		public static final String KEY_SCHEDULER = FieldNames.JOB_ID;
		public static final String KEY_CM_LEAD= FieldNames.LEAD_ID;
		public static final String KEY_SITE = FieldNames.LOCATION_ID; //P_Enh_Site_Clearance
	}

	public static void setModules() throws AppException
	{
		
	}

	public static boolean singleModule()
	{
		return false;
	}

	public static boolean isModuleImplemented(String _module)
	{
		return false;
	}

	public static boolean zcubatorImplemented()
	{
		return false;
	}

	public static boolean intranetImplemented()
	{
		return false;
	}

	public static boolean fsImplemented()
	{
		return false;
	}

	public static boolean onlyFSImplemented()
	{
		return false;
	}

	public static boolean onlySMImplemented()
	{
		return false;
	}

	public static boolean onlyFSDashBoardImplemented()
	{
		return false;
	}

	public static boolean onlyIntranetSupportImplemented()
	{
		return false;
	}
	
	public static boolean onlyIntranetImplemented()
	{
		return false;
	}

	public static boolean onlyAdmakerImplemented()
	{
		return false;
	}

	public static boolean onlyCmImplemented()
	{
		return false;
	}

	public static boolean onlySOImplemented()
	{
		return false;
	}

	public static boolean onlyFSSOImplemented()
	{
		return false;
	}

	public static boolean onlySupportImplemented()
	{
		return false;
	}
    public static boolean onlyStoreopenerImplemented()
    {
    	return false;
    }

	public static boolean onlyPPCImplemented()
	{
		return false;
	}

	public static boolean ufocImplemented()
	{
		return false;
	}

	public static boolean storeopenerImplemented()
	{
		return false;
	}

	public static boolean onlyfimImplemented()
	{
		return false;
	}

	public static boolean onlytrainingImplemented()
	{
		return false;
	}

	public static boolean onlyfinancialsImplemented()
	{
		return false;
	}

	public static boolean fimImplemented()
	{
		return false;
	}

	public static boolean financialsImplemented()
	{
		return false;
	}

	public static boolean cmImplemented()
	{
		return false;
	}
	
	public static boolean cmWsSyncImplemented()
	{
		return false;
	}

	public static boolean ziplocatorImplemented()
	{
		return false;
	}

	public static boolean emailtoolImplemented()
	{
		return false;
	}

	public static boolean wbImplemented()
	{
		return false;
	}

	public static boolean admakerImplemented()
	{
		return false;
	}

	public static boolean trainingImplemented()
	{
		return false;
	}

	public static boolean suppliesImplemented()
	{
		return false;
	}

	public static boolean dashboardImplemented()
	{
		return false;
	}

	public static boolean supportImplemented()
	{
		return false;
	}

	public static boolean tmsImplemented()
	{
		return false;
	}

	public static boolean schedulerImplemented()
	{
		return false;
	}

	public static boolean dispatchImplemented()
	{
		return false;
	}

	public static boolean summaryImplemented()
	{
		return false;
	}

	public static boolean homeImplemented()
	{
		return false;
	}

	public static boolean facebookBuilderImplemented()
	{
		return false;
	}

	public static boolean smartConnectImplemented()
	{
		return false;
	}

	public static boolean franchiseModules()
	{
		return false;
	}
	public static boolean franchiseModulesWithUser()
	{
		return false;
	}
	public static boolean isStoreOpenerExistsOnlyForTheIndicatedModules()
	{
		return false;
	}

	public static boolean auditImplemented()
	{
		return false;
	}

	public static boolean ppcImplemented()
	{
		return false;
	}

	public static boolean onlyWBImplemented()
	{
		return false;
	}

	public static boolean onlysuppliesImplemented()
	{
		return false;
	}

	public static boolean canAccessPPC(UserRoleMap userRoleMap, String userLevel)
	{
		return false;
	}
	
	
	public static Boolean marketingDashBoardImplemented()
	{
		return false;
	}

	public static Boolean landingPageImplemented()
	{
		return false;
	}

	public static Boolean marketingPilotImplemented()
	{
		return false;
	}

	public  static Boolean reputationManagementmImplemented()
	{
		return false;
	}

	public  static Boolean localListingImplemented()
	{
		return false;
	}
	
	public  static Boolean customerFeedbackImplemented()
	{
		return false;
	}
	

	public static boolean getEventType(String userNo)
	{
		String query = "SELECT PAGE_ID FROM MODULE_USER_PAGE_MAPPING WHERE USER_NO=" + userNo;
		boolean eventType = false;
		ResultSet result = null;
		try
		{
			result = QueryUtil.getResult(query, null);

			if (result.next())
			{
				eventType = true;
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getEventType method :" + e.getMessage());
		} finally
		{
			query = null;
			QueryUtil.releaseResultSet(result);
		}

		return eventType;
	}

	public static String getModulePaggingKeys(String userNo)
	{
		String query = "SELECT PAGE_ID FROM MODULE_USER_PAGE_MAPPING WHERE USER_NO=" + userNo + " ORDER BY MODULE_NAME";
		String keys = FieldNames.EMPTY_STRING;
		int count = 0;
		ResultSet result = null;
		try
		{
			result = QueryUtil.getResult(query, null);
			while (result.next())
			{
				count++;
				if (count == 1)
				{
					keys = result.getString("PAGE_ID");
				} else
				{
					keys = keys + ",";
					keys = keys + result.getString("PAGE_ID");
				}
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getModulePaggingKeys method ==> " + e.getMessage());
		} finally
		{
			QueryUtil.releaseResultSet(result);
		}
		return keys;
	}

	public static String getDefaultModule(String userNo)
	{
		String query = "SELECT ABBREVIATED_MODULE_NAME FROM MODULE_LIST ML,MODULE_USER_PAGE_MAPPING MP WHERE MP.ROOT_MODULE='Y' AND MP.MODULE_NAME=ML.MODULE_KEY AND USER_NO=" + userNo;
		String keys = "";

		ResultSet result = null;
		try
		{
			result = QueryUtil.getResult(query, null);

			while (result.next())
			{
				keys = result.getString("ABBREVIATED_MODULE_NAME");
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getDefaultModule method :" + e.getMessage());
		} finally
		{
			query = null;
			QueryUtil.releaseResultSet(result);
		}

		return keys;
	}

	public static SequenceMap getModuleInfo(String userLevel, boolean isSSOLogin)
	{
		SequenceMap sMap = new SequenceMap();
		int count = 0;
		StringBuffer query = new StringBuffer("SELECT * FROM MODULE_LIST ORDER BY SORT_ORDER ");//P_B_53059 
		ResultSet result = null;
		Info info = null;
		try
		{
			result = QueryUtil.getResult(query.toString(), null);
			while (result.next())
			{
				count++;
				info = new Info();
				if ("Franchisee Websites".equalsIgnoreCase(result.getString("MODULE_NAME")))
				{
					info.set("MODULE_NAME",com.home.builderforms.Constants.FRANCHISEE_WEBSITE);
				}
				else
				{
					info.set("MODULE_NAME", result.getString("MODULE_NAME"));
				}
				info.set("MODULE_KEY", result.getString("MODULE_KEY"));
				info.set("ABBR_MODULE", result.getString("ABBREVIATED_MODULE_NAME"));
				sMap.put(count + "", info);
			}
		}
		catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getModuleInfo method :" + e.getMessage());
		}
		finally
		{
			query = null;
			info = null;
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}

	public static SequenceMap getSubModuleInfo(String key, String userLevel, String showEstimateParam, boolean isDashBoardPriv, boolean isTroubleTicketSearchPriv)
	{
		Info info = null;
		SequenceMap sMap = new SequenceMap();
		int count = 0;
		StringBuffer query = new StringBuffer("SELECT * FROM SUB_MODULE_LIST SML,MODULE_LIST ML WHERE SML.MODULE_KEY = " + key + " AND ML.MODULE_KEY = " + key);
		if (userLevel != null && userLevel.trim().equals("0"))
		{
			query.append("  AND CORP_DISPLAY = 'Y' ");
		} else if (userLevel != null && userLevel.trim().equals("2"))
		{
			query.append("  AND REG_DISPLAY = 'Y' ");
		} else if (userLevel != null && userLevel.trim().equals("1"))
		{
			query.append("  AND FRAN_DISPLAY ='Y' ");
		}else if (userLevel != null && userLevel.trim().equals("6"))
		{
			query.append("  AND DIV_DISPLAY ='Y' ");
		}
		query.append(" ORDER BY SML.SORT_ORDER ");
		ResultSet result = null;
		try
		{
			result = QueryUtil.getResult(query.toString(), null);
			while (result.next())
			{

				count++;
				info = new Info();
				info.set(FieldNames.SUB_MODULE_NAME, "1".equals(userLevel) && "participants".equals(result.getString("ABBREVIATED_SUBMODULE_NAME"))?"Review Quiz":result.getString("SUB_MODULE_NAME"));//which-20150312-234
				info.set(FieldNames.SUB_MODULE_URL, result.getString("SUB_MODULE_URL"));
				info.set(FieldNames.SUB_MENU_PRIVILEGE, result.getString("SUB_MENU_PRIVILEGE"));
				info.set(FieldNames.SUB_MODULE_ID, result.getString("SUB_MODULE_ID"));
				info.set(FieldNames.DEFAULT_SUB_MODULE, result.getString("DEFAULT_SUB_MODULE"));
				info.set("ABBR_MODULE", result.getString("ABBREVIATED_MODULE_NAME"));
				info.set("ABBREVIATED_SUBMODULE_NAME", result.getString("ABBREVIATED_SUBMODULE_NAME"));
				sMap.put(count + "", info);
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getSchedulerViews method ===> ",e);
		} finally
		{
			query = null;
			info = null;
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}

	public static String getSubModules(String userNo)
	{

		String query = "SELECT SUB_MODULE_NAME FROM MODULE_USER_PAGE_MAPPING WHERE USER_NO=" + userNo + " ORDER BY SUB_MODULE_NAME";
		String keys = FieldNames.EMPTY_STRING;
		int count = 0;
		ResultSet result = null;
		try
		{
			result = QueryUtil.getResult(query, null);

			while (result.next())
			{
				count++;
				if (count == 1)
				{

					keys = result.getString("SUB_MODULE_NAME");

				} else
				{
					keys = keys + ",";
					keys = keys + result.getString("SUB_MODULE_NAME");

				}
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in getModulePaggingKeys method ===> ", e);
		} finally
		{
			query = null;
			QueryUtil.releaseResultSet(result);
		}

		return keys;
	}

	public static void checkModuleNames(SequenceMap moduleMap, ModuleDisplay md)
	{
		Iterator it = null;
		String key = null;
		Info info = null;
		String abbrName = null;
		ArrayList removeKeysList = null;

		try
		{
			it = moduleMap.keys().iterator();
			removeKeysList = new ArrayList();
			while (it.hasNext())
			{
				key = (String) it.next();
				info = (Info) moduleMap.get(key);
				abbrName = info.get("ABBR_MODULE");
				if ("financials".equals(abbrName) && !md.isDisplayFinancial)
				{
					removeKeysList.add(key);
				} else if ("cm".equals(abbrName) && !md.isDisplayContactManager)
				{
					removeKeysList.add(key);
				} else if ("tms".equals(abbrName) && !md.isDisplayCustomerTransaction)
				{
					removeKeysList.add(key);
				} else if ("home".equals(abbrName) && !md.isDisplayHome)
				{
					removeKeysList.add(key);
				}
			}

			for (int i = 0; i < removeKeysList.size(); i++)
			{
				moduleMap.remove(removeKeysList.get(i));
			}
		} catch (Exception e)
		{
			logger.error(" Exception in ModuleUtil class in checkModuleNames method ==> " ,e);
		} finally
		{
			it = null;
			key = null;
			info = null;
			abbrName = null;
			removeKeysList = null;
		}
	}


	public static String totalModuleImplemented()
	{
		return "";
	}

	public static boolean canAccessAudit(UserRoleMap userRoleMap, String userLevel)
	{
		return false;
	}
	
	//P_B_64035 STARTS
	public static boolean onlyFSFIMImplemented(){
		return false;
	}
	//P_B_64035 ENDS
	//BB-20160203-516 Starts
	public static boolean intelligenceImplemented()
	{
		return false;
	}
	//BB-20160203-516 Ends
}
