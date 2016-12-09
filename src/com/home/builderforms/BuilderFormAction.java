package com.home.builderforms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.appnetix.app.struts.EventHandler.*;
import com.appnetix.app.struts.actions.AbstractAppAction;
import com.home.builderforms.Info;
import com.appnetix.app.util.tagutils.Combo;
import java.util.*;
import com.appnetix.app.util.*;
import com.appnetix.app.components.customformsmgr.manager.CustomFormsMgr;
/**
 * Purpose of this form is to process form action
 * @author abhishek gupta
 * @version 1.1.1.1,
 * @created  Nov 14, 2011
 */

public class BuilderFormAction extends AbstractAppAction{
	static Logger logger			= com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BuilderFormAction.class);
	public String execute()
		throws Exception {

		Info info = new Info();
		if(request.getParameter(FieldNames.BUILDER_FORM_FIELDS_ID)!=null){

			Integer id = IntWrapper.getInteger(request.getParameter(FieldNames.BUILDER_FORM_FIELDS_ID));
			info = new CustomFormsMgr().getCustomFormFieldsDAO().getDetailsInfo(id);
			info.set(FieldNames.EVENT_TYPE,EventType.MODIFY);
			info.set(FieldNames.BUILDER_FORM_FIELDS_ID,request.getParameter(FieldNames.BUILDER_FORM_FIELDS_ID));
		}else{
			info.set(FieldNames.EVENT_TYPE,EventType.CREATE);		
		}

		request.setAttribute("INFOVALUE",info);

		return "success";
	}
}
