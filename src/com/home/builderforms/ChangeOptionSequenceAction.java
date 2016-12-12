package com.home.builderforms;
/*
 * P_Enh_Form_Builder_Option_Sequence
 * In order to change the sequence of option added through Form Builder(Only for custom Field).
 */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import com.appnetix.app.components.adminmgr.manager.AdminMgr;
import com.appnetix.app.components.builderformmgr.manager.dao.BuilderFormDAO;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.appnetix.app.struts.actions.AbstractAppAction;
import com.home.builderforms.FieldNames;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StrutsUtil;
import com.home.builderforms.Info;

public class ChangeOptionSequenceAction extends AbstractAppAction
{
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception 
	{
		String fieldName = request.getParameter("fieldName");
		String tableAnchor = request.getParameter("tableAnchor");
		String formId = request.getParameter("formID");
		String moduleId = request.getParameter("moduleId");
		Map insertoptionOrderMap = new HashMap();
		if("yes".equals(request.getParameter("modifySequence")))
		{
			for(int i=1; i<=Integer.parseInt(request.getParameter("mapSize")); i++)
			{
				insertoptionOrderMap.put(request.getParameter("sequence_"+i+"optionId"),request.getParameter("sequence_"+i+"orderNo"));
			}
			BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().updateOptionsOrder(insertoptionOrderMap,fieldName, tableAnchor);
			request.setAttribute("fromSave","yes");
		}
		Map<String, String> optionOrderMap = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().getOptionsOrderMap(fieldName, tableAnchor);
		request.setAttribute("fieldName", fieldName);
		request.setAttribute("tableAnchor", tableAnchor);
        request.setAttribute("optionOrderMap",optionOrderMap);
        request.setAttribute("formId",formId);
        request.setAttribute("moduleId",moduleId);
        return "success";
   	}
}
