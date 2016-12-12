package com.home.builderforms;
/**
 * This class deletes data from tabular section DB table when data is deleted from tabs in FS/FIM
 * @author Akash kumar
 * @date 9 Dec 2015
 */
import com.home.builderforms.Event;
import com.home.builderforms.EventType;
import com.home.builderforms.Manipulator;
import com.home.builderforms.DBUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IDGenerator;
import com.home.builderforms.MultipartRequest;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.DocumentMap;
import com.home.builderforms.Documents;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.FieldMappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
public class CommonTabularSectionManipulator implements Manipulator {
	public void manipulate (final SequenceMap eventMap, final MultipartRequest request){
		String eventType = request.getParameter(FieldNames.EVENT_TYPE);
		String tableAnchor=request.getParameter(FieldNames.TABLE_ANCHOR);
		if(EventType.DELETE.equals(eventType)){
			FieldMappings mappings=DBUtil.getInstance().getFieldMappings(tableAnchor);
			Field[] flds = mappings.getIdField();
			String idFld = flds[0].getFieldName();
			String tabPrimaryId=(String)request.getHttpServletRequest().getAttribute(idFld);
			String menuName=(String)request.getHttpServletRequest().getSession().getAttribute("menuName");
			if(!StringUtil.isValidNew(tabPrimaryId)){
				tabPrimaryId=request.getParameter(idFld);
			}
			String idfield=request.getParameter(FieldNames.LEAD_ID);
			if("fim".equals(menuName)){
				String isMuTab=request.getParameter("isMuTab");
				String subMenuName = (String) request.getHttpServletRequest().getSession().getAttribute("subMenuName");
				String docTable="FIM_DOCUMENTS";
				if (subMenuName!=null && subMenuName.equals("fimArea") && "yes".equals(isMuTab))
				{
					docTable="AREA_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"AREA_TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}else if(subMenuName!=null && (subMenuName.equals("fimMU")) && "yes".equals(isMuTab))
				{
					docTable = "FIM_MU_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"FIM_MU_TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}else{
					docTable="FIM_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}
			}
			HeaderMap[] hMap = mappings.getHeaderMap();
			for(HeaderMap h : hMap)
			{
				DocumentMap[] docMaps = null;
				HeaderField hFld = h.getHeaderFields();
				Documents[] docs = hFld.getDocuments();
				Boolean isTabularSection=hFld.isTabularSection();
				String tabularSectionDbTable=hFld.getTabularSectionDbTable();
				if(isTabularSection){
					if("fim".equals(menuName)){
						SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
					}else{
						if(StringUtil.isValidNew(idfield)){
							SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"LEAD_ID","TAB_PRIMARY_ID"}, new String[]{idfield,tabPrimaryId});
						}else{
							SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
						}
					}

				}
			}
		}else if(EventType.CREATE.equals(eventType)){
			try {
				Event tabularEvent = (Event) eventMap.get(tableAnchor);
				Info tabularInfo = tabularEvent.getInfo();
				String tabPrimaryId="";
				if(!StringUtil.isValidNew(tabularInfo.getString(FieldNames.TAB_PRIMARY_ID))){
					tabPrimaryId=IDGenerator.getNextKey();
					tabularInfo.set(FieldNames.TAB_PRIMARY_ID,tabPrimaryId);
					String mainTableAnchor=SQLUtil.getColumnValue("TABULAR_SECTION_DISPLAY_COLUMN","MAIN_TABLE_NAME","TABLE_NAME",tableAnchor);
					if(StringUtil.isValid(mainTableAnchor)) //AWCAN-20160921-012 starts
					{
					FieldMappings fMapping=DBUtil.getInstance().getFieldMappings(mainTableAnchor);
					if(fMapping != null)
					{									//AWCAN-20160921-012 ends
					String mainDbTable=fMapping.getTableName();
					if("fs".equals(request.getSession().getAttribute("menuName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"LEAD_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.LEAD_ID)});		
					}else if("fim".equals(request.getSession().getAttribute("menuName"))){
						if(StringUtil.isValidNew(tabularInfo.get(FieldNames.MU_ID))){
							SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"MU_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.MU_ID)});	
						}else if(StringUtil.isValidNew(tabularInfo.get(FieldNames.AREA_INFO_ID))){
							SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"AREA_INFO_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.AREA_INFO_ID)});	
						}else{
							SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"ENTITY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.ENTITY_ID)});
						}
					}else if("cm".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"CONTACT_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.CONTACT_ID)});		
					}else if("lead".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"LEAD_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.LEAD_ID)});		
					}else if("account".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"COMPANY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.COMPANY_ID)});		
					}else if("opportunity".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"OPPORTUNITY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.OPPORTUNITY_ID)});		
					}
					}//AWCAN-20160921-012 
					} //AWCAN-20160921-012 
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*if("fim".equals(request.getSession().getAttribute("menuName"))){
		FimThirdPartyUpload fimThirdPartyUpload = new FimThirdPartyUpload();
		fimThirdPartyUpload.manipulate(eventMap, request);
		}*/
	}

	public void manipulate (final SequenceMap eventMap, final HttpServletRequest request){
		String eventType = request.getParameter(FieldNames.EVENT_TYPE);
		String tableAnchor=request.getParameter(FieldNames.TABLE_ANCHOR);
		if(EventType.DELETE.equals(eventType)){
			FieldMappings mappings=DBUtil.getInstance().getFieldMappings(tableAnchor);
			Field[] flds = mappings.getIdField();
			String idFld = flds[0].getFieldName();
			String tabPrimaryId=(String)request.getAttribute(idFld);
			String menuName=(String)request.getSession().getAttribute("menuName");
			if(!StringUtil.isValidNew(tabPrimaryId)){
				tabPrimaryId=request.getParameter(idFld);
			}
			String idfield=request.getParameter(FieldNames.LEAD_ID);
			if("fim".equals(menuName)){
				String isMuTab=request.getParameter("isMuTab");
				String subMenuName = (String) request.getSession().getAttribute("subMenuName");
				String docTable="FIM_DOCUMENTS";
				if (subMenuName!=null && subMenuName.equals("fimArea") && "yes".equals(isMuTab))
				{
					docTable="AREA_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"AREA_TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}else if(subMenuName!=null && (subMenuName.equals("fimMU")) && "yes".equals(isMuTab))
				{
					docTable = "FIM_MU_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"FIM_MU_TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}else{
					docTable="FIM_DOCUMENTS";
					SQLUtil.deleteRows(docTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
				}
			}
			HeaderMap[] hMap = mappings.getHeaderMap();
			for(HeaderMap h : hMap)
			{
				DocumentMap[] docMaps = null;
				HeaderField hFld = h.getHeaderFields();
				Documents[] docs = hFld.getDocuments();
				Boolean isTabularSection=hFld.isTabularSection();
				String tabularSectionDbTable=hFld.getTabularSectionDbTable();
				if(isTabularSection){
					if("fim".equals(menuName)){
						SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
					}else{
						if(StringUtil.isValidNew(idfield)){
							SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"LEAD_ID","TAB_PRIMARY_ID"}, new String[]{idfield,tabPrimaryId});
						}else{
							SQLUtil.deleteRows(tabularSectionDbTable, new String[]{"TAB_PRIMARY_ID"}, new String[]{tabPrimaryId});
						}
					}

				}
			}
		}else if(EventType.CREATE.equals(eventType)){
			try {
				Event tabularEvent = (Event) eventMap.get(tableAnchor);
				Info tabularInfo = tabularEvent.getInfo();
				String tabPrimaryId="";
				if(!StringUtil.isValidNew(tabularInfo.getString(FieldNames.TAB_PRIMARY_ID))){
					tabPrimaryId=IDGenerator.getNextKey();
					tabularInfo.set(FieldNames.TAB_PRIMARY_ID,tabPrimaryId);
					String mainTableAnchor=SQLUtil.getColumnValue("TABULAR_SECTION_DISPLAY_COLUMN","MAIN_TABLE_NAME","TABLE_NAME",tableAnchor);
					if(StringUtil.isValid(mainTableAnchor)) //AWCAN-20160921-012 starts
					{
						FieldMappings fMapping=DBUtil.getInstance().getFieldMappings(mainTableAnchor);
					if(fMapping != null)
					{      							//AWCAN-20160921-012 ends
					String mainDbTable=fMapping.getTableName();
					if("fs".equals(request.getSession().getAttribute("menuName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"LEAD_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.LEAD_ID)});		
					}else if("fim".equals(request.getSession().getAttribute("menuName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"ENTITY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.ENTITY_ID)});
					}else if("cm".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"CONTACT_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.CONTACT_ID)});		
					}else if("lead".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"LEAD_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.LEAD_ID)});		
					}else if("account".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"COMPANY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.COMPANY_ID)});		
					}else if("opportunity".equals(request.getSession().getAttribute("moduleName"))){
						SQLUtil.insertRowInTable(mainDbTable, new String[]{fMapping.getIdField()[0].getDbField(),"OPPORTUNITY_ID"}, new String[]{tabPrimaryId,tabularInfo.get(FieldNames.OPPORTUNITY_ID)});		
					}
					} //AWCAN-20160921-012
				} //AWCAN-20160921-012
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*if("fim".equals(request.getSession().getAttribute("menuName"))){
		FimThirdPartyUpload fimThirdPartyUpload = new FimThirdPartyUpload();
		fimThirdPartyUpload.manipulate(eventMap, request);
		}*/
	}
}
