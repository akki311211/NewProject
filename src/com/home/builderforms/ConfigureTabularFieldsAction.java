package com.home.builderforms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.home.BuilderFormFieldNames;
import com.appnetix.app.struts.actions.AbstractAppAction;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;

public class ConfigureTabularFieldsAction extends AbstractAppAction
{
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception 
	{
		String fieldNames = FieldNames.EMPTY_STRING;
		String fieldOrder = FieldNames.EMPTY_STRING;
		String moduleName = request.getParameter("moduleName");
		String formId = request.getParameter(BuilderFormFieldNames.FORM_ID);
		Map insertoptionOrderMap = new HashMap();
		String tableAnchor = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "TABLE_ANCHOR", new String[]{"BUILDER_FORM_ID", "MODULE"}, new String[]{formId, moduleName});
		try{
			if("save".equals(request.getParameter("fromWhere")))
			{
				/*for(int i=1; i<=Integer.parseInt(request.getParameter("fieldsSize")); i++)
				{
					if(i == 1){
						fieldNames = request.getParameter("selectedField_"+i+"_FieldName");
						fieldOrder = request.getParameter("selectedField_"+i+"_OrderNo");
					} else {
						fieldNames += "," + request.getParameter("selectedField_"+i+"_FieldName");
						fieldOrder += "," + request.getParameter("selectedField_"+i+"_OrderNo");
					}
				}*/

				SQLUtil.deleteRecord("CONFIGURE_TABULAR_FIELDS", "TABLE_ANCHOR = "+"'"+tableAnchor+"'");
				//SQLUtil.insertRowInTable("CONFIGURE_TABULAR_FIELDS", new String[]{"TABLE_ANCHOR","FIELDS","ORDER_NO"}, new String[]{tableAnchor, fieldNames, fieldOrder});
				BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().insertTabularFields(request, tableAnchor);
				request.getSession().setAttribute("fromTabularFields", "yes");
			}

			Map unselectedFieldsMap = BuilderFormWebImpl.getInstance().getTabularSummaryFieldsMap(request, tableAnchor);
			String selectFields = CommonUtil.getSelectedFields(tableAnchor);
			String[] selectedFieldsArr = null;
			Map selectedFieldsMap = null;
			if(StringUtil.isValid(selectFields))
			{
				selectedFieldsMap = new LinkedHashMap();
				selectedFieldsArr = selectFields.split(",");
				for(String field : selectedFieldsArr)
				{
					if(unselectedFieldsMap.containsKey(field))
					{
						selectedFieldsMap.put(field, (String)unselectedFieldsMap.get(field));
						unselectedFieldsMap.remove(field);
					}
				}
			}
			request.setAttribute("selectedFieldsMap", selectedFieldsMap);
			request.setAttribute("unselectedFieldsMap", unselectedFieldsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
}
