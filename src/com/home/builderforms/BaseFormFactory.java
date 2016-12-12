/**
 * Purpose of this Factory method is to avoid hard code element in DAO data
 * @author abhishek gupta
 * @date 17 Nov, 2011
 */
package com.home.builderforms;

import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Enumeration;
import com.home.builderforms.*;
import com.appnetix.app.control.*;
import com.home.builderforms.Constants;
import com.home.builderforms.Info;


import com.home.builderforms.BuilderFormFieldNames;
//import com.appnetix.app.portal.role.UserRoleMap;

public class BaseFormFactory
{
	static HashMap tables;

	private BaseFormFactory() {
	}

	static private final BaseFormFactory instance = new BaseFormFactory();

	public static BaseFormFactory getBaseFormFactoryInstance () {
		return instance;
	}

	public void initialize (ServletContext context) {
		String tablesURL = null;
		try {
			tablesURL = context.getResource(BuilderConstants.TABLE_DEFINITION_XML_FILE).toString();
		} catch (java.net.MalformedURLException ex) {
			Debug.println("BaseFormFactory: malformed URL exception: " + ex);
		}
		tables = BuilderXMLDAO.loadTables(tablesURL);
	}
	
	private SequenceMap getTableData(HashMap tableParameters) {
		HashMap table = null;
		String tableID = null;
		String tableClass = null;
		try {
			tableID = (String)tableParameters.get(BuilderConstants.TABLE_ID);
			table = (HashMap) tables.get(tableID);
			int id = Integer.parseInt(tableID);
			if (tableClass == null || tableClass.equals("")) {
				tableClass = (String) table.get(BuilderConstants.TABLE_CLASS);
			}
			if(StringUtil.isValid((String)table.get(BuilderConstants.SEQUENCE_MAP_KEY))) {
				tableParameters.put(BuilderConstants.SEQUENCE_MAP_KEY, (String)table.get(BuilderConstants.SEQUENCE_MAP_KEY));
			}
			if(StringUtil.isValid((String)table.get(BuilderConstants.PARAMETER_TABLE_ID))) {
				tableParameters.put(BuilderConstants.PARAMETER_TABLE, (HashMap) tables.get((String)table.get(BuilderConstants.PARAMETER_TABLE_ID)));
			}
			
			Class[] parameters = new Class[3];
			Object[] params = new Object[3];
			parameters[0] = HashMap.class;
			parameters[1] = HashMap.class;
			parameters[2] = String.class;
			params[0] = table;
			params[1] = tableParameters;
			params[2] = (String)tableParameters.get("menuName");
			
			Class className = getClass().getClassLoader().loadClass(tableClass);
			BaseBuilder tableData = (BaseBuilder) className.getConstructor(parameters).newInstance(params);
			return tableData.getTableMap();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			table = null;
			tableID = null;
			tableClass = null;
		}
	}
	
	private Object getObjectData(HashMap tableParameters) {
		HashMap table = null;
		String tableID = null;
		String tableClass = null;
		try {
			tableID = (String)tableParameters.get(BuilderConstants.TABLE_ID);
			table = (HashMap) tables.get(tableID);
			int id = Integer.parseInt(tableID);
			if (tableClass == null || tableClass.equals("")) {
				tableClass = (String) table.get(BuilderConstants.TABLE_CLASS);
			}
			if(StringUtil.isValid((String)table.get(BuilderConstants.SEQUENCE_MAP_KEY))) {
				tableParameters.put(BuilderConstants.SEQUENCE_MAP_KEY, (String)table.get(BuilderConstants.SEQUENCE_MAP_KEY));
			}
			if(StringUtil.isValid((String)table.get(BuilderConstants.PARAMETER_TABLE_ID))) {
				tableParameters.put(BuilderConstants.PARAMETER_TABLE, (HashMap) tables.get((String)table.get(BuilderConstants.PARAMETER_TABLE_ID)));
			}
			
			Class[] parameters = {HashMap.class,HashMap.class,String.class};
			Object[] params = {table,tableParameters,(String)tableParameters.get("menuName")};//new Object[3];
			//parameters[0] = HashMap.class;
			//parameters[1] = HashMap.class;
			//parameters[2] = String.class;
			//params[0] = table;
			//params[1] = tableParameters;
			//params[2] = (String)tableParameters.get("menuName");
			
			//Class className = getClass().getClassLoader().loadClass(tableClass);
			//BaseBuilder tableData = (BaseBuilder) className.getConstructor(parameters).newInstance(params);
			//return tableData.getObject();
			return ((BaseBuilder)getClass().getClassLoader().loadClass(tableClass).getConstructor(parameters).newInstance(params)).getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			table = null;
			tableID = null;
			tableClass = null;
		}
	}
	
	private void setRequestParameter(HttpServletRequest request, HashMap tableParameters) {
		String table_id = null;
		String form_id = null;
		String type = null;
		String builderModuleName = null;//ENH_MODULE_CUSTOM_TABS
		try {
			table_id = getRequestValue(request, BuilderConstants.TABLE_ID);
			form_id = getRequestValue(request, BuilderConstants.FORM_ID);
			type = getRequestValue(request, BuilderConstants.MAP_TYPE);
			builderModuleName = getRequestValue(request, BuilderFormFieldNames.BUILDER_MODULE_NAME);//ENH_MODULE_CUSTOM_TABS
			
			tableParameters.put("CONTEXT_PATH", request.getContextPath());
			tableParameters.put(BuilderConstants.TABLE_ID, table_id);
			tableParameters.put(BuilderConstants.FORM_ID, form_id);
			tableParameters.put(BuilderConstants.MAP_TYPE, type);
			tableParameters.put(BuilderFormFieldNames.BUILDER_MODULE_NAME,builderModuleName);//ENH_MODULE_CUSTOM_TABS
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			table_id = null;
			form_id = null;
		}
	}
	
	public static String getRequestValue(HttpServletRequest request, String colName) {
    	String val = null;
    	try {
    		val = request.getParameter(colName);
    		if(val == null) {
    			val = (String)request.getAttribute(colName);
    		}
    	} catch(Exception e) {
    		return val;
    	}
		return val;
	}
	
	public SequenceMap getBuilderTableData(HttpServletRequest request, ServletContext context) {
//		SequenceMap table = null;
		HashMap tableParameters = null;
		String[] parameterValues = null;
		Enumeration parameters = null;
		try {
			if (tables == null || tables.size() == 0) {
				if(context != null) {
					initialize(context);	
				} else {
					initialize(request.getSession().getServletContext());
				}
			}
			String menuName = (String) (request.getSession().getAttribute("menuName"));
			tableParameters = new HashMap();
			tableParameters.put("menuName", menuName);
			
			setRequestParameter(request, tableParameters);
			parameters = request.getParameterNames();
			
			String parameterName = null;
			while (parameters.hasMoreElements()) {
				parameterName = (String) parameters.nextElement();
				parameterValues = request.getParameterValues(parameterName);

				if (parameterValues != null && parameterValues.length == 1) {
					tableParameters.put(parameterName, parameterValues[0]);
				} else	{
					StringBuffer str = new StringBuffer();
					int size = parameterValues.length;
					for (int i = 0; i < size; i++)	{
						str.append(parameterValues[i]);
						if (i < size - 1) {
							str.append(",");
						}
					}
					String pValue = str.toString();
					tableParameters.put(parameterName, pValue);
				}
			}
			parameterName = null;
			
//			table = (SequenceMap) getTableData(tableParameters);
			return (SequenceMap) getTableData(tableParameters);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
//			table = null;
			if(tableParameters != null)
				tableParameters.clear();
//			tableParameters = null;
			parameterValues = null;
		}
	}
	
	public Object getBuilderObjectData(HttpServletRequest request, ServletContext context) {
		Object table = null;
		HashMap tableParameters = null;
		String[] parameterValues = null;
		Enumeration parameters = null;
		try {
			if (tables == null || tables.size() == 0) {
				if(context != null) {
					initialize(context);	
				} else {
					initialize(request.getSession().getServletContext());
				}
			}
			String menuName = (String) (request.getSession().getAttribute("menuName"));
			tableParameters = new HashMap();
			tableParameters.put("menuName", menuName);
			
			setRequestParameter(request, tableParameters);
			parameters = request.getParameterNames();
			
			String parameterName = null;
			while (parameters.hasMoreElements()) {
				parameterName = (String) parameters.nextElement();
				parameterValues = request.getParameterValues(parameterName);

				if (parameterValues != null && parameterValues.length == 1) {
					tableParameters.put(parameterName, parameterValues[0]);
				} else	{
					StringBuffer str = new StringBuffer();
					int size = parameterValues.length;
					for (int i = 0; i < size; i++)	{
						str.append(parameterValues[i]);
						if (i < size - 1) {
							str.append(",");
						}
					}
					String pValue = str.toString();
					tableParameters.put(parameterName, pValue);
				}
			}
			parameterName = null;
			
			table = (Object) getObjectData(tableParameters);
			return table;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			table = null;
			tableParameters = null;
			parameterValues = null;
		}
	}
}