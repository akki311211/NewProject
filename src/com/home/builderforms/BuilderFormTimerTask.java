package com.home.builderforms;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.appnetix.app.components.builderformmgr.manager.dao.BuilderFormDAO;
import com.appnetix.app.components.builderformmgr.manager.dao.FormBaseDAO;
import com.appnetix.app.control.web.multitenancy.AbstractTenantRunnable;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.home.BuilderFormFieldNames;
import com.appnetix.app.util.Constants;
import com.appnetix.app.util.DBUtil;
import com.appnetix.app.util.DateUtil;
import com.appnetix.app.util.FieldNames;
import com.appnetix.app.util.QueryUtil;
import com.appnetix.app.util.SequenceMap;
import com.appnetix.app.util.StringUtil;
import com.appnetix.app.util.TableAnchors;
import com.appnetix.app.util.database.DependentTable;
import com.appnetix.app.util.database.Field;
import com.appnetix.app.util.database.FieldMappings;
import com.appnetix.app.util.database.HeaderField;
import com.appnetix.app.util.database.HeaderMap;
import com.appnetix.app.util.database.TableField;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.DBColumn;
import com.home.builderforms.sqlqueries.DBQuery;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLQueryGenerator;
import com.home.builderforms.sqlqueries.SQLUtil;

public class BuilderFormTimerTask extends AbstractTenantRunnable {

	static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BuilderFormTimerTask.class);
	HashMap dataMap = new HashMap();
	HttpServletRequest request;
	static boolean flag;
	String user_no;
	String tableAnchor;
	String formId;
	SequenceMap baseBuilder1;
	public BuilderFormTimerTask(HashMap dataMap, String userNo, String tableAnchor, String formId, HttpServletRequest request, SequenceMap baseBuilder1) {
		// TODO Auto-generated constructor stub
		this.dataMap = dataMap; //setting request inside the function
		this.user_no = userNo;
		this.tableAnchor = tableAnchor;
		this.formId = formId;
		this.baseBuilder1 = baseBuilder1;
		this.request = request;
	}

	public static boolean getFlag() {
		return flag;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try {
			
			String currentDateTimeDB = DateUtil.getCurrentDateTimeDB();
			SQLUtil.updateTableColumnWithCond("BASE_THREADS", "STATUS", "Working at "+currentDateTimeDB, "THREAD_ALIAS_NAME", "BUILDER_FORM_TIMER_TASK");
			
			System.out.println(">>>>>>>>>>>Adding Field>>>>>>>>>"+(String)dataMap.get(BuilderFormFieldNames.FIELD_NAME));
			BuilderFormDAO builderFormDao = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO();
			builderFormDao.setBuilderFormAddOrUpdate(dataMap, request, user_no, tableAnchor, formId, baseBuilder1);
			System.out.println("\n\n>>>>>>>>>>>Field Added Successfully>>>>>>>>>"+(String)dataMap.get(BuilderFormFieldNames.FIELD_NAME));
			
			currentDateTimeDB = DateUtil.getCurrentDateTimeDB();
			SQLUtil.updateTableData("BASE_THREADS", new String []{"STATUS", "LAST_EXECUTED"}, new String[]{"SUCCESSFUL" ,currentDateTimeDB}, false, null, true, "THREAD_ALIAS_NAME='BUILDER_FORM_TIMER_TASK'");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MultiTenancyUtil.getTenantConstants().IS_FORMBUILDER_PROGRESS = false;
			MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING = false;
		}
	}
}
