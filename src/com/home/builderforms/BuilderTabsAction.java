/**
 *@author Mohit Sharma
 *@since Nov 2012
 * Class for performing actions on tabs before display and getting the data to be displayed
 * ENH_MODULE_CUSTOM_TABS
 * P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 BB-20141017-177               5 nov 2014          Nazampreet Kaur       code added for summary display of custom fields
 P_SCH_B_49982			14 Nov 2014		Manik Malhotra					Delete option not available for Newly added Scheduler custom tab.
 */
package com.home.builderforms;


import java.util.HashMap;


import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.home.builderforms.QueryUtil;
import com.appnetix.app.components.accesscontrolmgr.manager.AccessControlMgr;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.home.BuilderFormFieldNames;
import com.appnetix.app.struts.actions.AbstractAppAction;
import com.home.builderforms.BuilderFormUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.FieldNames;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

public class BuilderTabsAction extends AbstractAppAction {

	public String execute() throws Exception {
		BuilderFormWebImpl builderWebImpl = BuilderFormWebImpl.getInstance();
		SequenceMap tabGeneratorMap = new SequenceMap();
		HttpSession session = request.getSession();
		String sTabModuleName 	= request.getParameter(FieldNames.MODULE);
		String passwordFunctionality=AccessControlMgr.newInstance().getAccessControlDAO().getPasswordFunctionalityValue();
		String subModuleName = request.getParameter("subModuleName"); //PP_changes
                //CM_B_57250 starts
                if(!StringUtil.isValid(subModuleName)){
                    subModuleName=null;
                }
		//CM_B_57250 End
		boolean isModified = false;
		if(sTabModuleName == null)
		{
			sTabModuleName = (String)session.getAttribute("sTabModuleName");
		}
		else
		{
			session.setAttribute("sTabModuleName", sTabModuleName);
		}
		
		
		String moduleName = ModuleUtil.MODULE_NAME.NAME_FIM;
		String moduleShortName = "fim";
		String href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#FIM";
		//P_Enh_Mu-Entity_FormGenerator starts
		if("mu".equals(subModuleName))
		{
			sTabModuleName="fim";
			moduleName = ModuleUtil.MODULE_NAME.NAME_FIM_MU;
		    request.setAttribute(BuilderConstants.TABLE_ID,"23");

		}else if("area".equals(subModuleName))
		{
			sTabModuleName="fim";
			moduleName = ModuleUtil.MODULE_NAME.NAME_FIM_AREA;
		    request.setAttribute(BuilderConstants.TABLE_ID,"21");

		}//P_Enh_Mu-Entity_FormGenerator ends
		else if("fim".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_FIM;
		    request.setAttribute(BuilderConstants.TABLE_ID,"1");

		}
		else if("cm".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_CM;
		    request.setAttribute(BuilderConstants.TABLE_ID,"11");
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Contact_Manager";
		    moduleShortName = "cm";
		}
		else if("account".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_ACCOUNT;
		    request.setAttribute(BuilderConstants.TABLE_ID,"16");
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Contact_Manager";
		    moduleShortName = "account";
		}
		else if("opportunity".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY;
		    request.setAttribute(BuilderConstants.TABLE_ID,"17");
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Contact_Manager";
		    moduleShortName = "opportunity";
		}else if("lead".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_LEAD;
		    request.setAttribute(BuilderConstants.TABLE_ID,"19");
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Contact_Manager";
		    moduleShortName = "lead";
		}
		else if("site".equals(sTabModuleName)) //P_Enh_Site_Clearance starts
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_SITE;
		    request.setAttribute(BuilderConstants.TABLE_ID,"18");
		    href = "administration#Site_Clearance";
		    moduleShortName = "site";
		} //P_Enh_Site_Clearance ends
		else if("fs".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_FS;
			if("captivate".equals(subModuleName)) { //PP_changes starts
				request.setAttribute(BuilderConstants.TABLE_ID,"14");
			} else {
		    request.setAttribute(BuilderConstants.TABLE_ID,"10");
			} //PP_changes ends
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Franchise_Sales";
		    moduleShortName = "fs";
		}
		//P_SCH_ENH_008 Starts
		else if("scheduler".equals(sTabModuleName))
		{
			moduleName = ModuleUtil.MODULE_NAME.NAME_SCHEDULER;
		    request.setAttribute(BuilderConstants.TABLE_ID,"13");
		    href = "administration?cft="+java.net.URLEncoder.encode((String)request.getSession().getAttribute("csrfToken"))+"#Scheduler";
		    moduleShortName = "scheduler";
		}//P_SCH_ENH_008 Ends

		if(StringUtil.isValid(sTabModuleName))
			session.setAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME,moduleName);

		request.setAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME,moduleName);
		if(StringUtil.isValidNew(sTabModuleName)) 
		{
			
			if(request.getParameter(BuilderFormFieldNames.TAB_ACTION) != null)
			{
				isModified = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().processFormGeneratorTabData(request);
			}
		}
 		BuilderFormWebImpl builderForm = BuilderFormWebImpl.getInstance();
		SequenceMap builderFormMap = builderForm.getBuilderForm(request);
		if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleShortName))
		{
			request.setAttribute(BuilderConstants.TABLE_ID,"12");
			SequenceMap customBuilderFormMap = builderForm.getBuilderForm(request);
			if(customBuilderFormMap!=null && customBuilderFormMap.size()>0)
				builderFormMap.putAll(customBuilderFormMap);
		}
		request.setAttribute(FieldNames.MODULE,sTabModuleName);
		request.setAttribute(FieldNames.SUB_MODULE_NAME,subModuleName);//P_Enh_Mu-Entity_FormGenerator
		SequenceMap xmlbuilderFormMap = builderForm.getBuilderTabMap(request);//BB-20150203-259(Builder for tab repositioning ) changes
		tabGeneratorMap = new SequenceMap();
		SequenceMap contactHistoryMap = new SequenceMap();
		builderFormMap=sortedTabMap(builderFormMap,xmlbuilderFormMap);//BB-20150203-259(Builder for tab repositioning ) changes
		int tabCount = 0;
		int contactHistoryCount = 0;
		int count = 1;
		
		Object [] allForms = builderFormMap.keys().toArray();
		Info currentInfo = null;
		Info newInfo = null;
		StringBuffer methodBuffer 	= new StringBuffer();
		for(Object operatingForm:allForms)
		{
			currentInfo = (Info)builderFormMap.get(operatingForm);
			if(currentInfo ==null || "Y".equals(currentInfo.get(FieldNames.IS_CUSTOM)))
			{
				continue;
			}

			newInfo = new Info();
			newInfo.set(BuilderFormFieldNames.IS_CUSTOM, "N");
			newInfo.set(BuilderFormFieldNames.DISPLAY_NAME,currentInfo.get(BuilderFormFieldNames.DISPLAY_NAME));//BB-20150203-259(Builder for tab repositioning ) changes
			newInfo.set(BuilderFormFieldNames.TAB_DISPLAY,LanguageUtil.getString(currentInfo.get(BuilderFormFieldNames.FORM_NAME)));
			newInfo.set(BuilderFormFieldNames.TAB_NAME,currentInfo.get(BuilderFormFieldNames.TABLE_ANCHOR));
			newInfo.set(BuilderFormFieldNames.TAB_DISPLAY_LINK,"<a href=\"javascript:void(0);\" onclick=\"callForm('"+currentInfo.get(BuilderFormFieldNames.BUILDER_FORM_ID)+"')\">"+LanguageUtil.getString(currentInfo.get(BuilderFormFieldNames.FORM_NAME))+"</ a>");//internationalization
			newInfo.set(FieldNames.SUB_MODULE_NAME, "--");
			newInfo.set(FieldNames.LABEL_NAME, "&nbsp;&nbsp<img class='transparent_class' src='" + request.getContextPath() + "/static"+ Constants.STATIC_KEY + "/images/customform/active.png' border=\"0\">");


			StringBuffer actions  		= null;
			StringBuffer links	  		= null;
			actions 	= new StringBuffer("[ ");
			links 		= new StringBuffer("[ ");

			actions.append("'"	+ LanguageUtil.getString("Modify", (String) session.getAttribute("userLanguage")) + "','']");
			links.append("'1','']");
			//BB-20150203-259 starts(Tab Rename changes)

			if("Remark".equals(currentInfo.get(BuilderFormFieldNames.DISPLAY_NAME)) || "Call".equals(currentInfo.get(BuilderFormFieldNames.DISPLAY_NAME)) || "Task".equals(currentInfo.get(BuilderFormFieldNames.DISPLAY_NAME))) {
				newInfo.set(BuilderFormFieldNames.NO, ++contactHistoryCount);
				newInfo.set(FieldNames.ACTION, "--");
				contactHistoryMap.put(currentInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR), newInfo);
			} else {
				newInfo.set(BuilderFormFieldNames.NO, ++tabCount);
				String goSingleAction = "javascript:void(0);redirect(?,'" + currentInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR) + "','" + currentInfo.getString(FieldNames.MODULE_NAME) + "','','N')";
				if("mu".equals(currentInfo.getString(FieldNames.MODULE_NAME)) || "area".equals(currentInfo.getString(FieldNames.MODULE_NAME))){//P_Enh_Mu-Entity_FormGenerator
					goSingleAction = "javascript:void(0);redirect(?,'" + currentInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR) + "','" + currentInfo.getString(FieldNames.MODULE_NAME) + "','"+currentInfo.getString(FieldNames.MODULE_NAME)+"','N')";
				}
				methodBuffer.append("prepareActionsMenu2(" + actions + "," + links + ", " + actions + ", ");
				methodBuffer.append(count + ", " + "\"" + goSingleAction + "\""  + ",'dynamicmenu');\n");
				
				if("fim".equals(currentInfo.getString(FieldNames.MODULE_NAME)) || "mu".equals(currentInfo.getString(FieldNames.MODULE_NAME)) || "area".equals(currentInfo.getString(FieldNames.MODULE_NAME))||"fs".equals(currentInfo.getString(FieldNames.MODULE_NAME))
						|| "cm".equals(currentInfo.getString(FieldNames.MODULE_NAME))
						|| "account".equals(currentInfo.getString(FieldNames.MODULE_NAME)) 
						|| "opportunity".equals(currentInfo.getString(FieldNames.MODULE_NAME))
						|| "lead".equals(currentInfo.getString(FieldNames.MODULE_NAME)) )
					newInfo.set(FieldNames.ACTION, getMenuBar(count++, request.getContextPath()));
				else//BB-20150203-259 ends
					newInfo.set(FieldNames.ACTION, "--");
				tabGeneratorMap.put(currentInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR), newInfo);
			}
		}
		
		if(StringUtil.isValidNew(sTabModuleName)) 
		{
			request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR,BuilderFormFieldNames.BUILDER_TABS);
			request.setAttribute(BuilderFormFieldNames.XML_ELEMENT,BuilderFormFieldNames.MODULE_TAB);
			request.setAttribute(BuilderFormFieldNames.XML_KEY,BuilderFormFieldNames.TAB_NAME);
			request.setAttribute(BuilderFormFieldNames.KEY_TYPE,"Info");
			
			
			

			SequenceMap customTabs = BuilderCustomTab.newInstance().getCustomTab(sTabModuleName,subModuleName);//ENH_MODULE_CUSTOM_TABS //PP_changes
			Object tabArr[] =  new Object[0];
			if(customTabs!=null && customTabs.size()>0)
			{
				tabArr = customTabs.keys().toArray();
			}

			
			Map<String, String> tabularFieldCountMap = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getTabularFieldCountMap();
			boolean dataPresent = false;
			boolean tabularDataPresnt = false;
			String key1 	= null;
			Info moduleInfo = null;
			StringBuffer actions  		= null;
			StringBuffer links	  		= null;
			String canViewRolesValue="";//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
			String canWriteRolesValue="";//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
			boolean canActivateCustomTab = Boolean.FALSE;
			Info subModuleList = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getSubmoduleList(sTabModuleName);//ENH_MODULE_CUSTOM_TABS
			for (Object tabArrVar:tabArr) 
			{
				key1 		= (String)tabArrVar;
				moduleInfo 	= (Info)customTabs.get(key1);
				canViewRolesValue=(String)moduleInfo.get(BuilderFormFieldNames.CAN_VIEW_ROLES);//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
				canWriteRolesValue=(String)moduleInfo.get(BuilderFormFieldNames.CAN_WRITE_ROLES);//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
				if((moduleInfo==null) || (((String)moduleInfo.get(BuilderFormFieldNames.TAB_DISPLAY)).equals("bQual")))//BUG_60439 NOW BQUAL TAB WILL NOT COME
				{
					continue;
				}

				canActivateCustomTab = BuilderFormUtil.canActivateCustomTab(moduleInfo);
				moduleInfo.set(BuilderFormFieldNames.DISPLAY_NAME,moduleInfo.get(BuilderFormFieldNames.TAB_DISPLAY));// BB-20150203-259(Builder for tab repositioning ) changes
				actions 	= new StringBuffer("[ ");
				links 		= new StringBuffer("[ ");

				if ("Y".equals(moduleInfo.getString("is-active"))) {
					actions.append("'"	+ LanguageUtil.getString("Modify", (String) session.getAttribute("userLanguage")) + "','");
					links.append("'1'");
				}
				else
				{
					actions.append("'");
				}
				//P_SCH_B_49982 Starts
				boolean checkForScheduler=false;
				if(StringUtil.isValid(moduleInfo.getString(FieldNames.MODULE)) && ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(moduleInfo.getString(FieldNames.MODULE))){
					checkForScheduler=true;
				}
				//dataPresent = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().isTabDataPresent(moduleInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR));
				dataPresent = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().isTabDataPresent(moduleInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR),checkForScheduler);
				//P_SCH_B_49982 Ends	
				
				if(!dataPresent) {
					if(tabularFieldCountMap != null && tabularFieldCountMap.size() > 0) {
						String fieldCount = tabularFieldCountMap.get(moduleInfo.getString(BuilderFormFieldNames.TABLE_ANCHOR));
						if(StringUtil.isValidNew(fieldCount)) {
							int fieldCountInteger = Integer.parseInt(fieldCount);
							if(fieldCountInteger > 0) {
								dataPresent = true;
							}
						}
					}
 				}

				if(!dataPresent && canActivateCustomTab)
				{
					if ("Y".equals(moduleInfo.getString("is-active"))) {
						actions.append(LanguageUtil.getString("Delete", (String) session.getAttribute("userLanguage")) + "','");
						links.append(",'4'");
					}
					else
					{
						actions.append(LanguageUtil.getString("Delete", (String) session.getAttribute("userLanguage")) + "','");
						links.append("'4'");
					}

				}
				String submodule = "";
				if(subModuleList!=null)
				{
					submodule = moduleInfo.get(FieldNames.SUB_MODULE); 
					if(StringUtil.isValid(submodule))
					{
						moduleInfo.set(FieldNames.SUB_MODULE_NAME,subModuleList.get(submodule));
					}
					else
					{
						moduleInfo.set(FieldNames.SUB_MODULE_NAME,"--");
					}
				}
				if ("N".equals(moduleInfo.getString("is-active"))) {
					moduleInfo.set(BuilderFormFieldNames.TAB_DISPLAY_LINK,moduleInfo.get(BuilderFormFieldNames.TAB_DISPLAY));
				}
				else
				{
					moduleInfo.set(BuilderFormFieldNames.TAB_DISPLAY_LINK,"<a href=\"javascript:void(0);\" onclick=\"callForm('"+moduleInfo.get(BuilderFormFieldNames.BUILDER_FORM_ID)+"')\">"+moduleInfo.get(BuilderFormFieldNames.TAB_DISPLAY)+"</ a>");
				}

				if("captivate".equals(subModuleName)) { //PP_changes starts
					String sectionId = moduleInfo.get(BuilderFormFieldNames.SECTION_VALUE);
					String sectionName  = NewPortalUtils.getColumnFromTable("FS_CAPTIVATE_SECTIONS", "SECTION_NAME", "SECTION_ID", sectionId); //to get the name of the captivate section
					moduleInfo.set(FieldNames.SECTION_NAME, sectionName);
				} //PP_changes ends

				if(canActivateCustomTab){
					if ("N".equals(moduleInfo.getString("is-active"))) {
						moduleInfo.set(FieldNames.LABEL_NAME,"&nbsp;&nbsp;<a href=\"javascript:void(0);\" onClick=\"doActivate('"+moduleInfo.getString(BuilderFormFieldNames.TAB_NAME)+"','"+moduleInfo.getString(FieldNames.MODULE)+"','active')\"><img src='"	+ request.getContextPath() + "/static"+ Constants.STATIC_KEY	+ "/images/customform/deactive.png' border=\"0\"></a>");
						actions.append(LanguageUtil.getString("Activate", (String) session.getAttribute("userLanguage"))	+ "','']");
						if(!dataPresent)
							links.append(",'2','']");
						else
							links.append("'2','']");

					} else {
						moduleInfo.set(FieldNames.LABEL_NAME,"&nbsp;&nbsp;<a href=\"javascript:void(0);\" onClick=\"doActivate('"+moduleInfo.getString(BuilderFormFieldNames.TAB_NAME)+"','"+moduleInfo.getString(FieldNames.MODULE)+"','deactive')\"><img src='" + request.getContextPath() + "/static"+ Constants.STATIC_KEY	+ "/images/customform/active.png' border=\"0\"></a>");

						actions.append(LanguageUtil.getString("Deactivate", (String) session.getAttribute("userLanguage")) + "','']");
						links.append(",'3','']");
					}
				}else{
					actions.append(",'']");
					links.append(",'']");
				}

				if("Remark".equals(moduleInfo.get(BuilderFormFieldNames.DISPLAY_NAME)) || "Call".equals(moduleInfo.get(BuilderFormFieldNames.DISPLAY_NAME)) || "Task".equals(moduleInfo.get(BuilderFormFieldNames.DISPLAY_NAME))) {
					moduleInfo.set(BuilderFormFieldNames.NO, ++contactHistoryCount);
					moduleInfo.set(FieldNames.ACTION, "--");
					contactHistoryMap.put(moduleInfo.getString(BuilderFormFieldNames.TAB_NAME), moduleInfo);
				} else {
					String goSingleAction = "javascript:void(0);redirect(?,'" + moduleInfo.getString(BuilderFormFieldNames.TAB_NAME) + "','" + moduleInfo.getString(FieldNames.MODULE) + "','" + submodule+ "')";
					methodBuffer.append("prepareActionsMenu2(" + actions + "," + links + ", " + actions + ", ");
					methodBuffer.append(count + ", " + "\"" + goSingleAction + "\""  + ",'dynamicmenu');\n");
					moduleInfo.set(BuilderFormFieldNames.IS_CUSTOM, "Y");
					moduleInfo.set(BuilderFormFieldNames.NO, ++tabCount);
					moduleInfo.set(FieldNames.ACTION, getMenuBar(count++, request.getContextPath()));
					tabGeneratorMap.put(moduleInfo.getString(BuilderFormFieldNames.TAB_NAME), moduleInfo);
				}
			}
			tabGeneratorMap=sortedTabMap(tabGeneratorMap,xmlbuilderFormMap);//BB-20150203-259(Builder for tab repositioning ) changes
			session.setAttribute("contactHistoryMap", contactHistoryMap);
			session.setAttribute("tabGeneratorMap", tabGeneratorMap);
			session.setAttribute("allExistingTabsName", builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getAllExistingTabNames(sTabModuleName));
			request.setAttribute("methodBuffer", methodBuffer.toString());
			request.setAttribute("canViewRolesValue", canViewRolesValue);//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
			request.setAttribute("canWriteRolesValue", canWriteRolesValue);//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
		}
		request.setAttribute("isModified", isModified);
		String sFimFormId = request.getParameter("formNames");
		if(sFimFormId==null)
		{
			sFimFormId = request.getParameter("formID");
		}

		/*int moduleID= 0; //BB-20141017-177 commented
		if("33".equals(sFimFormId)){
			moduleID = 11;
		}else if("1".equals(sFimFormId)){
			moduleID = 3;
		} else if("31".equals(sFimFormId)){
			moduleID = 4;
		}*/
		request.setAttribute("tabCount",xmlbuilderFormMap.size());//BB-20150203-259(Builder for tab repositioning ) changes
		request.setAttribute("builderFormMap",builderFormMap);
		session.setAttribute("builderFormMap",builderFormMap); //P_Enh_Site_Clearance
		request.setAttribute("passwordFunctionality", passwordFunctionality);
		
		//// BB-20141017-177 starts commented
		/*String tableAnchor=request.getParameter("tableAnchor");
		if(tableAnchor == null || "".equals(tableAnchor))
		{
			tableAnchor = request.getParameter("tableName");
		}
		System.out.println("\n\n\n>>>>>>>>>tableAnchor>>>>>>>>"+tableAnchor);
		if(tableAnchor == null || "".equals(tableAnchor))
		{
			tableAnchor = (String)request.getAttribute("tableAnchor");
		}
		String isActive=request.getParameter("isActive");
		String fieldName=request.getParameter("fieldName");
		String action=request.getParameter("action");
		String dbfield = "";
		String sDisplayName = "";
		sDisplayName =  request.getParameter("sdisplayName");
		if(StringUtil.isValidNew(sDisplayName)) {
			sDisplayName = StringUtil.reversesAppostrophiSpecial(sDisplayName);
			sDisplayName = StringUtil.byPassQuotesInQuery(sDisplayName);
		}
		
		String dbfieldquery = "";	
		String updateQuery = "";
		ResultSet rs = null;
		int updateFlag = -2;
		System.out.println("tableAnchor:111111111333:" + tableAnchor + "isActive::" + isActive+"fieldName::" + fieldName+"action::" + action + "request.getParameter(\"statuschange\")" + request.getParameter("statuschange") + "isActiveisActive::" + isActive+"sDisplayName::"+ sDisplayName);
		try
		{
				dbfieldquery="SELECT DISPLAY_VALUE FROM SUMMARY_DISPLAY WHERE DISPLAY_NAME='"+sDisplayName+"' AND MODULE_ID='"+moduleID+"'";
				//rs=QueryUtil.getResult(dbfieldquery,null);
			    rs.next();
				dbfield=rs.getString("DISPLAY_VALUE");
				System.out.println("tableAnchor:111111111:" + tableAnchor + "isActive::" + isActive+"fieldName::" + fieldName+"action::" + action + "request.getParameter(\"statuschange\")" + request.getParameter("statuschange") + "isActiveisActive::" + isActive+"dbfieldquery" + dbfieldquery);
			
		if("delete".equals(action))
		{
			updateQuery="DELETE FROM SUMMARY_DISPLAY WHERE DISPLAY_VALUE ='"+dbfield+"' AND MODULE_ID='"+moduleID+"'";
			//updateFlag=QueryUtil.update(updateQuery,null);
		}
		else if("true".equals(request.getParameter("statuschange")))
		{
		if("franchisees".equals(tableAnchor) || "fsLeadDetails".equals(tableAnchor))
		{    
			if("true".equals(isActive))
			{
				
				updateQuery="UPDATE SUMMARY_DISPLAY SET IS_SELECTED=IS_SELECTED+3 WHERE DISPLAY_VALUE='"+dbfield+"'";
				//updateFlag=QueryUtil.update(updateQuery,null);
			}
			else
			{
				updateQuery="UPDATE SUMMARY_DISPLAY SET IS_SELECTED=IS_SELECTED-3 WHERE DISPLAY_VALUE='"+dbfield+"'";
				//updateFlag=QueryUtil.update(updateQuery,null);
			}
		}		
		}
		}
			catch(Exception e)
			{
			}*/
			// BB-20141017-177 ends
		
			
			//BB-20150203-259 Starts (To make available all the fields of Form builder as keyword.)		
			String keywordFieldName = "";
			String deleteKeyword = "";
			String isKeywordActivated = "";
			if(StringUtil.isValid((String)request.getParameter("deleteKeyword")))
			{
				deleteKeyword = (String)request.getParameter("deleteKeyword");
			}
			if(StringUtil.isValid((String)request.getParameter("fieldName")))
			{
				keywordFieldName = (String)request.getParameter("fieldName");
			}
			if("delete".equals(deleteKeyword))
			{
				SQLUtil.deleteRecord("CONFIGURE_TABULAR_FIELDS", "FIELDS = '"+keywordFieldName+"' AND TABLE_ANCHOR = "+"'"+request.getParameter("tableName")+"'");
				isKeywordActivated = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().deleteKeywordsField(keywordFieldName,sFimFormId);
			}
			HashMap<Integer,String> fieldMap =  new HashMap<Integer,String>();
			fieldMap =  builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getKeywordsFieldNames(sFimFormId);
			request.setAttribute("fieldMap", fieldMap);
			//BB-20150203-259 Ends (To make available all the fields of Form builder as keyword.)		
			//Disable keyword starts
			if("cm".equals(sTabModuleName) || "lead".equals(sTabModuleName)){
				HashMap<Integer,String> deactiveKeyWordMap =  new HashMap<Integer,String>();
				deactiveKeyWordMap=builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().getDeactiveKeywordsFieldNames(sFimFormId);
				request.setAttribute("deactiveKeyWordMap", deactiveKeyWordMap);
			}
			//Disable keyword starts
		session.removeAttribute("finalMap"); //remove from session as it custom fields also get configured
		if(!StringUtil.isValid(sFimFormId))
			return "customTabsView";
		else
		{
			return "formGeneratorView";
		}

	}

	public String getMenuBar(int count, String contextPath) 
	{
		String menuBar = "<div id=\"menuBar\"><layer ID=\"Actions_dynamicmenu"
				+ count
				+ "Bar\">"
				+ " <a href=\"javascript:void(0);\" onMouseOver=\"openPulldownMenu(event,'Actions_dynamicmenu"
				+ count + "');\""
				+ " onMouseOut=\"closePulldownMenu('Actions_dynamicmenu"
				+ count + "');\">" + " <img src=\"" + contextPath + "/static" + Constants.STATIC_KEY
				+ "/images/edit.gif\" border=\"0\"></a></layer></div>";
		return menuBar;
	}
	/**
	 * this method returns sorted map for formbuilder tabNames
	 *  BB-20150203-259
	 * @param initialMap
	 * @param finalMap
	 * @return sequencemap
	 */
	public SequenceMap sortedTabMap(SequenceMap initialMap,SequenceMap finalMap )
	{
		SequenceMap sortedMap=new SequenceMap();
		SequenceMap customEntryMap=new SequenceMap();
		int count=1;
		try{
			if(finalMap != null && finalMap.size() > 0) {
				Info[] tabsInfo = new Info[finalMap.size()];
				Info[] tabsInfo1=new Info[initialMap.size()];
				for(int i=0; i < finalMap.size(); i++) {
					Info currentInfo = (Info) finalMap.get(i);
					tabsInfo[i] = currentInfo;
				}
				for(int i=0; i < initialMap.size(); i++) {
					Info currentInfo1 = (Info) initialMap.get(i);
					tabsInfo1[i] = currentInfo1;
				}
				int n = finalMap.size();
				Info swap = null;
				for (int c = 0; c < n; c++) {
					for(int j=0;j<initialMap.size();j++){
						if((tabsInfo[c].getString(BuilderFormFieldNames.TAB_DISPLAY_NAME).equals(tabsInfo1[j].getString(BuilderFormFieldNames.DISPLAY_NAME)))
								|| (tabsInfo[c].getString("isCustom").equals("Y") && tabsInfo1[j].getString("formName") != null 
								&& tabsInfo[c].getString(BuilderFormFieldNames.TAB_DISPLAY_NAME).equals(tabsInfo1[j].getString("formName")))
						)
						{
							tabsInfo1[j].set(BuilderFormFieldNames.NO,count++);
							if(StringUtil.isValidNew(tabsInfo1[j].getString(BuilderFormFieldNames.TAB_NAME))){
								sortedMap.put(tabsInfo1[j].getString(BuilderFormFieldNames.TAB_NAME), tabsInfo1[j]);
							}
							else{
								sortedMap.put(tabsInfo1[j].getString(BuilderFormFieldNames.FORM_NAME), tabsInfo1[j]);
							}
						} else { 
							if("Remark".equals(tabsInfo1[j].getString(BuilderFormFieldNames.DISPLAY_NAME)) || "Call".equals(tabsInfo1[j].getString(BuilderFormFieldNames.DISPLAY_NAME)) || "Task".equals(tabsInfo1[j].getString(BuilderFormFieldNames.DISPLAY_NAME))){
								if(!customEntryMap.containsKey(tabsInfo1[j].getString(BuilderFormFieldNames.TAB_NAME)) && !customEntryMap.containsKey(tabsInfo1[j].getString(BuilderFormFieldNames.FORM_NAME)))
									if(StringUtil.isValidNew(tabsInfo1[j].getString(BuilderFormFieldNames.TAB_NAME))){
										customEntryMap.put(tabsInfo1[j].getString(BuilderFormFieldNames.TAB_NAME), tabsInfo1[j]);
									}
									else{
										customEntryMap.put(tabsInfo1[j].getString(BuilderFormFieldNames.FORM_NAME), tabsInfo1[j]);
									}
							}
						}
						
					}
				}
				//P_Enh_ContactHistory_FormBuilder starts
				if(customEntryMap!=null && customEntryMap.size()>0){
					for(int j=0;j<customEntryMap.size();j++){
						Info info=(Info)customEntryMap.get(j);
						info.set(BuilderFormFieldNames.NO,count++);
						sortedMap.put(customEntryMap.getKey(j),info);
					}
				}
				//P_Enh_ContactHistory_FormBuilder ends
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(sortedMap.size() > 0) { //P_Enh_Site_Clearance starts
			return sortedMap;
		} else {
			return initialMap;
		} //P_Enh_Site_Clearance ends
	}
}
