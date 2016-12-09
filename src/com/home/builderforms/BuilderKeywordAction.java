package com.home.builderforms;
/**
 * BB-20150203-259
 * To make available all the fields of Form builder as keyword.
 */
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appnetix.app.util.BaseUtils;
import com.appnetix.app.util.StringUtil;
import com.appnetix.app.util.StrutsUtil;
import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.appnetix.app.struts.actions.AbstractAppAction;

public class BuilderKeywordAction extends AbstractAppAction {
	static final Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BuilderTabsAction.class);

	public String execute() throws Exception {
		BuilderFormWebImpl builderWebImpl = BuilderFormWebImpl.getInstance();
		String formId = request.getParameter("formNames");
		if(formId==null)
		{
			formId = request.getParameter("formID");
		}
		String tableAnchor=request.getParameter("tableAnchor");
		if(tableAnchor == null || "".equals(tableAnchor))
		{
			tableAnchor = request.getParameter("tableName");
		}
		String isTabularSection=(String)request.getParameter("isTabularSection");
		String tabularSectionTableName=(String)request.getParameter("tabularSectionTableName");
		if("true".equals(isTabularSection)){
			tableAnchor=tabularSectionTableName;
		}
		String insertFld = "";
		String moduleId = "";
		String keywordFieldName = "";
		String isKeywordActivated = "";
		String isKeywordDeactivated = "";
		String fieldFormName = "";
		
		String isOtherTableField = "";
		if(StringUtil.isValid((String)request.getParameter("insertFld")))
		{
			insertFld = (String)request.getParameter("insertFld");
		}
		if(StringUtil.isValid((String)request.getParameter("fieldName")))
		{
			keywordFieldName = (String)request.getParameter("fieldName");
		}
		if(StringUtil.isValid((String)request.getParameter("moduleId")))
		{
			moduleId = (String)request.getParameter("moduleId");
		}
		if(StringUtil.isValid((String)request.getParameter("fieldFormName")))
		{
			fieldFormName = (String)request.getParameter("fieldFormName");
		}
		if(StringUtil.isValid((String)request.getParameter("isOtherTableField")))
		{
			isOtherTableField = (String)request.getParameter("isOtherTableField");
		}
		if("delete".equals(insertFld))
		{
			isKeywordDeactivated = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().deleteKeywordsField(keywordFieldName,formId);
			request.getSession().setAttribute("isKeywordDeactivated", isKeywordDeactivated);
		} else if("insert".equals(insertFld))
		{
			isKeywordActivated = builderWebImpl.getBuilderFormMgr().getBuilderFormDAO().InsertkeywordField(keywordFieldName, moduleId, tableAnchor, isOtherTableField, formId,fieldFormName);
			request.getSession().setAttribute("isKeywordActivated", isKeywordActivated);
		}
		String forwardUrl="/control/builderWebForm?formNames="+formId;

		return StrutsUtil.actionForward(forwardUrl, true);
	}
}
