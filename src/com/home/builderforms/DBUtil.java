package com.home.builderforms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.home.builderforms.SyncMap;
import com.home.builderforms.AppException;
import com.home.builderforms.BuilderCustomTab;
//import com.appnetix.app.portal.role.UserRoleMap;
import com.home.builderforms.BaseFieldNames;
//import com.home.builderforms.base.BaseTableAnchors;
import com.home.builderforms.DependentTable;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.ForeignTable;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.TableField;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLQuery;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.sqlqueries.SQLUtilHelper;
import com.home.builderforms.SQLQueriesXMLDAO;
import com.home.builderforms.TableXMLDAO;

public class DBUtil
{
	
	private static 	DBUtil 	dbUtil 		= new DBUtil();

	private DBUtil(){}

	public static DBUtil getInstance()
	{
		return dbUtil;
	}

	public DBConnectionManager getDBConnectionManager()
	{
		return DBConnectionManager.getInstance();
	}

	public HashMap getTableMappings()
	{
		HashMap tableMappings 		= (HashMap)MultiTenancyUtil.getTenantContext().getAttribute(WebKeys.TableMappingsKey);
		HashMap customTableMappings = new HashMap();
		HashMap tabularSectionMappings=new HashMap();
		if (tableMappings == null)
		{
			try
			{
				String tableMappingsURL 	= Constants.XML_DIRECTORY + "tablemappings.xml";
				tableMappings 				= TableXMLDAO.getTableMappings(tableMappingsURL);
				//P_Enh_FormBuilder_Tabular_Section starts
				tableMappingsURL=Constants.XML_DIRECTORY +"tabularSectionMappings.xml";
				tabularSectionMappings 				= TableXMLDAO.getTabularSectionMappings(tableMappingsURL);
				if(tabularSectionMappings!=null && tabularSectionMappings.size()>0){
					tableMappings.putAll(tabularSectionMappings);
				}
				//P_Enh_FormBuilder_Tabular_Section ends
				//ENH_MODULE_CUSTOM_TABS starts
				// load table mappings for custom tabs
				customTableMappings			= BuilderCustomTab.newInstance().getAllCustomTabMappings();
				if(customTableMappings != null && customTableMappings.size() > 0)
				{
					tableMappings.putAll(customTableMappings);
				}
				//ENH_MODULE_CUSTOM_TABS ends
				//AUDIT_ENHANCEMENT_CHANGES starts
				//customTableMappings			= AuditUtil.getAllCustomTabMappings();
				customTableMappings			= BaseUtils.getAllCustomTabMappings();  //For Product_Seperation_BL By Amar Singh
				if(customTableMappings != null && customTableMappings.size() > 0)
				{
					tableMappings.putAll(customTableMappings);
				}
				//AUDIT_ENHANCEMENT_CHANGES ends
				
                MultiTenancyUtil.getTenantContext().setAttribute(WebKeys.TableMappingsKey,tableMappings);
				
			}
			catch(Exception e)
			{
			}
			//AUDIT_ENHANCEMENT_CHANGES starts
			finally
			{
				customTableMappings = null;
			}
			//AUDIT_ENHANCEMENT_CHANGES ends
		}
		return tableMappings;
    }
	
	
	public HashMap getTableVsUrlMappings(String url)
	{
		HashMap<String,HashMap> tableVsUrlMappings = (HashMap<String,HashMap>)MultiTenancyUtil.getTenantContext().getAttribute(WebKeys.TableVsUrlMappingsKey);
		//System.out.println("tableVsUrlMappings============================"+tableVsUrlMappings);
		HashMap<String,String> tableVsUrlMapping = new HashMap<String, String>();
		if (tableVsUrlMappings == null)
		{
			try
			{
				String tableVsUrlMappingsUrl = Constants.XML_DIRECTORY + "tableVsUrlMappings.xml";
				tableVsUrlMappings = TableXMLDAO.getTableVsUrlMappings(tableVsUrlMappingsUrl);
                MultiTenancyUtil.getTenantContext().setAttribute(WebKeys.TableVsUrlMappingsKey, tableVsUrlMappings);
			}
			catch(Exception e)
			{
			}
		}
		if (tableVsUrlMappings != null) {
			tableVsUrlMapping = tableVsUrlMappings.get(url);
		}
		return tableVsUrlMapping;
	}
	public HashMap<String,String>[] getTableVsUrlMappingsByTag(String tagName,String keyTagName){
		return getTableVsUrlMappingsByTag(tagName, keyTagName, null);
	}
	public HashMap<String,String>[] getTableVsUrlMappingsByTag(String tagName,String keyTagName, String moduleName)//P_B_FIM_60836
	{
		HashMap<String,String>[] tableVsUrlMappings = (HashMap[])MultiTenancyUtil.getTenantContext().getAttribute(WebKeys.TableVsUrlMappingsKey+tagName);
		if (tableVsUrlMappings == null)
		{
			try
			{
				String tableVsUrlMappingsUrl = Constants.XML_DIRECTORY + "tableVsUrlMappings.xml";
				tableVsUrlMappings = TableXMLDAO.getTableVsUrlMappingsByTag(tableVsUrlMappingsUrl,tagName,keyTagName,moduleName);
                MultiTenancyUtil.getTenantContext().setAttribute(WebKeys.TableVsUrlMappingsKey+tagName, tableVsUrlMappings);
			}
			catch(Exception e)
			{
			}
		}
		return tableVsUrlMappings;
	}
	
	
	
	
    public FieldMappings getFieldMappings(String tableAnchor)
    {
		FieldMappings fieldMappings = (FieldMappings)getTableFieldMappings().getOne(tableAnchor);
		if (fieldMappings == null)
		{
			try
			{
				String loc		= (String)getTableMappings().get(tableAnchor);
				String location = Constants.XML_DIRECTORY + loc;
				fieldMappings = TableXMLDAO.getFieldMappings(location);
				getTableFieldMappings().put(tableAnchor,fieldMappings);
			}
			catch(Exception e)
			{
			}
		}
		if(fieldMappings == null){
		}
		return fieldMappings;

	}
    
    public FieldMappings getFieldMappingsByPath(String xmPath, String tableAnchor)
    {
		FieldMappings fieldMappings = (FieldMappings)getTableFieldMappings().getOne(tableAnchor);
		if (fieldMappings == null)
		{
			try
			{
				String loc		= (String)getTableMappings().get(tableAnchor);
				String location = xmPath;
				fieldMappings = TableXMLDAO.getFieldMappings(location);
				getTableFieldMappings().put(tableAnchor,fieldMappings);
			}
			catch(Exception e)
			{
			}
		}
		if(fieldMappings == null){
		}
		return fieldMappings;

	}
    
    public HashMap getFimTablesWithMergableFieldsData(String tableAnchor) {
    	HashMap sMap = null;
    	FieldMappings fieldMappings = getFieldMappings(tableAnchor);
		if(fieldMappings != null){
			sMap = new HashMap();
			sMap.put(tableAnchor, fieldMappings.getAllMargableFields());
			
			SequenceMap smForeignTables		= fieldMappings.getForeignTablesMap();
			if(smForeignTables != null){
				int sizeForTable				= smForeignTables.size();
				for(int i = 0 ; i < sizeForTable ; i++){
					ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
					
					fieldMappings = getFieldMappings(foreignTable.getName());
					if(fieldMappings.getAllMargableFields()!=null && fieldMappings.getAllMargableFields().length>0)
						sMap.put(foreignTable.getName(), fieldMappings.getAllMargableFields());
				}
			}
		}
		return sMap;
	}
/*    
    public HashMap getFimAllMergableKeyFieldsData(String tableAnchor) {
    	return getFimAllMergableKeyFieldsData(tableAnchor,null);
    }
    public HashMap getFimAllMergableKeyFieldsData(String tableAnchor,UserRoleMap userRoleMap) {
    	return getFimAllMergableKeyFieldsData(tableAnchor, userRoleMap, null); 
    }
    public HashMap getFimAllMergableKeyFieldsData(String tableAnchor,UserRoleMap userRoleMap, List<String> userTabConfigured) {
    	
    	HashMap sMap = new LinkedHashMap();
    	try{
    		HashMap hMap = DBUtil.getInstance().getTableVsUrlMappings(tableAnchor);
    		Field[] flds = null;
    		FieldMappings fieldMappings = getFieldMappings(tableAnchor);
    		flds = fieldMappings.getAllMargableFields();
    		if(flds != null){
    			
    			String maxKeyword = getMaxKeyword(tableAnchor);
    			if(hMap != null)
				{
					if(userRoleMap!=null && userRoleMap.isPrivilegeInMap((String)hMap.get("privilegeUrl")))
					{
						for(Field fld : flds) {
							fld.setTableName(tableAnchor);
							fld.setMaxKeyword(maxKeyword);
							sMap.put(fld.getMailMergeKeyword(), fld);
						}
						 insertDependentFields(tableAnchor,sMap,fieldMappings,maxKeyword);
					}
				}else{
					for(Field fld : flds) {
						fld.setTableName(tableAnchor);
						sMap.put(fld.getMailMergeKeyword(), fld);
					}
					insertDependentFields(tableAnchor,sMap,fieldMappings,maxKeyword);
				}
    			
    		}

    		SequenceMap smForeignTables		= fieldMappings.getForeignTablesExportMap();
    		if(smForeignTables != null){
    			int sizeForTable				= smForeignTables.size();
    			for(int i = 0 ; i < sizeForTable ; i++){
    				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
    				String tableName = foreignTable.getName();
    				
    				if(tableName.equals(BaseTableAnchors.CM_COMPANY_INFO) && !userRoleMap.isPrivilegeInMap("/cmCompanyPrivilege"))
    					continue;
    				
    				String mainTableAnchor = SQLUtil.getColumnValue("TABULAR_SECTION_DISPLAY_COLUMN", "MAIN_TABLE_NAME", "TABLE_NAME", tableName);
    				if(userTabConfigured != null && !StringUtil.isValidNew(mainTableAnchor)) {
    					if(!userTabConfigured.contains(tableName)) {
    						continue;
    					}
    				}
    				
    				sMap.putAll(getFimAllMergableKeyFieldsData(tableName,userRoleMap, userTabConfigured));
    			}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
		return sMap;
	}*/
    
    public void insertDependentFields(String parentTable,HashMap sMap,FieldMappings fieldMappings,String maxKeyword)
    {
        HeaderMap[] headerMap = fieldMappings.getHeaderMap();
        if(headerMap!=null)
        {
            HeaderField hFld; DependentTable dependentTables[];TableField tableFields[];
            FieldMappings fieldMapping;SequenceMap attributes;Field fld;
            String tableAnchor,keyword,isActive;
            Map param;

            for(HeaderMap h:headerMap)
            {
                hFld = h.getHeaderFields();
                dependentTables = hFld.getDependentTables();
                if(dependentTables!=null && dependentTables.length>0)
                {
                    for(DependentTable dependentTable : dependentTables)
                    {
                        tableFields = dependentTable.getTableFields();
                        if(tableFields!=null && tableFields.length>0)
                        {
                            tableAnchor = dependentTable.getTableAnchor();
                            fieldMapping 	= getFieldMappings(tableAnchor);
                            for(TableField tableField: tableFields)
                            {
                                attributes = tableField.getFieldMap();
                                keyword = (String)attributes.get("keyword-name");
                                isActive = (String)attributes.get("is-active");
                                if(StringUtil.isValid(keyword) && !"no".equals(isActive))
                                {
                                    fld = fieldMapping.getField((String) attributes.get("name"));
                                    if(fld!=null)
                                    {
                                        param = new HashMap();
                                        param.put("parentTable", parentTable);
                                        param.put("alias", dependentTable.getTableAliasName());
                                        param.put("maxKeyword", maxKeyword);
                                        fld.setKeywordInfo(keyword, param);
                                        fld.setTableName(tableAnchor);
                                        sMap.put(keyword, fld);
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }
    }
    
    public boolean removeFieldMappings(String tableAnchor)
    {
		FieldMappings fieldMappings = (FieldMappings)getTableFieldMappings().getOne(tableAnchor);
		if (fieldMappings != null) {
			try {
				getTableFieldMappings().remove(tableAnchor);
			} catch(Exception e) {
				return false;
			}
		}
		return true;
	}
    
	public FieldMappings getValidationFieldMappings(String[] mainTableAnchor , String[] childTableAnchor , int noOfChilds)
    {
		FieldMappings fieldMappings = null;
		try
		{
			String loc		= (String)getTableMappings().get(mainTableAnchor[0]);
			String location = Constants.XML_DIRECTORY + loc;
			fieldMappings = TableXMLDAO.getFieldMappings(location);
				
			if(noOfChilds > 0 )
			{
				for(int i = 0; i < noOfChilds; i++) {
					loc		= (String)getTableMappings().get(childTableAnchor[i]);
					location = _baseConstants.XML_DIRECTORY + loc;
					fieldMappings = 	TableXMLDAO.getValidationFieldMappings(location,childTableAnchor[i],fieldMappings,i);
					
				}					
			}
		}
		catch(Exception e)
		{
		}
		
		if(fieldMappings == null){
		}
		return fieldMappings;

	}

	public void setFieldMappings(
									String psTableAnchor,
									FieldMappings pFieldMappings
								)throws AppException, IOException{

		String sLocation			= (String)getTableMappings().get(psTableAnchor);
		if(sLocation == null){
			throw new AppException("XML for " + psTableAnchor + " does not exist");
		}
		TableXMLDAO.setTriggers(MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + sLocation, pFieldMappings);
	}
	public SyncMap getTableFieldMappings()
	{
		SyncMap tableFieldMappings =(SyncMap)MultiTenancyUtil.getTenantContext().getAttribute(WebKeys.TableFieldMappingsKey);
		if (tableFieldMappings==null)
		{

			tableFieldMappings = new SyncMap(new HashMap());
            MultiTenancyUtil.getTenantContext().setAttribute(WebKeys.TableFieldMappingsKey,tableFieldMappings);
		}
		return tableFieldMappings;
	}

	public SQLQuery getSQLQuery(String mgrName, String sqlQueryName){
		if(sqlQueryName == null){
			return null;
		}
		SQLQuery sqlQuery = (SQLQuery)getSQLQueriesMap(mgrName, false).get(sqlQueryName);
		return sqlQuery;
	}
	public SQLQuery getSQLQuery(String mgrName, String sqlQueryName, boolean bFromXML){
		if(sqlQueryName == null){
			return null;
		}
		SQLQuery sqlQuery = (SQLQuery)getSQLQueriesMap(mgrName, bFromXML).get(sqlQueryName);
		return sqlQuery;
	}

	public HashMap getSQLQueriesMap(String mgrName, boolean bFromXML)
	{
		HashMap sqlQueriesMap	= (HashMap)MultiTenancyUtil.getTenantContext().getAttribute(WebKeys.SQLQueriesMapKey + mgrName);

		if (sqlQueriesMap == null || bFromXML)
		{
			String url = null;
			try
			{
				url = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + "sqlqueries/"+mgrName+"/sqlqueries.xml";
			}
			catch (Exception e)
			{
			}
			sqlQueriesMap = SQLQueriesXMLDAO.getSQLQueries(url);
            MultiTenancyUtil.getTenantContext().setAttribute(WebKeys.SQLQueriesMapKey+mgrName,sqlQueriesMap);
		}

		return sqlQueriesMap;

	}


	public ResultList getResultList(String mgrName,String sqlQueryName,Object[] params) throws SQLException
	{
		SQLQuery sqlQuery = getSQLQuery(mgrName, sqlQueryName);
		if (sqlQuery==null)
		{
			return null;
		}
		return SQLUtil.getResultList(sqlQuery,params);
	}

	public ResultList getResultList(SQLQuery sqlQuery, Object[] params) throws SQLException{
		if (sqlQuery==null)
		{
			return null;
		}
		return SQLUtil.getResultList(sqlQuery,params);
	}

	public void clearBindingForTableAnchor(String tableAnchor)
	{
		getTableFieldMappings().remove(tableAnchor);
	}

	public void clearTableMappings()
	{
        MultiTenancyUtil.getTenantContext().removeAttribute(WebKeys.TableMappingsKey);
	}

	public void clearSQLQueriesMap(String mgrName)
	{
        MultiTenancyUtil.getTenantContext().removeAttribute(WebKeys.SQLQueriesMapKey+mgrName);
	}
	
	 
   // public static final List fimTables = Arrays.asList(BaseTableAnchors.FIM_LENDER,BaseTableAnchors.FIM_ADDRESS,BaseTableAnchors.FIM_INSURANCE,BaseTableAnchors.FIM_GUARANTOR,BaseTableAnchors.FIM_LEGAL_VIOLATION,BaseTableAnchors.FIM_MYSTERY_SHOPPER,BaseTableAnchors.FIM_COMPLAINT,BaseTableAnchors.FIM_PICTURE,BaseTableAnchors.FIM_TRAINING,BaseTableAnchors.FIM_TRANSFER,BaseTableAnchors.FIM_RENEWAL,BaseTableAnchors.FIM_EVENTS,BaseTableAnchors.FIM_OWNERS,BaseTableAnchors.FIM_EMPLOYEES,BaseTableAnchors.FIM_DOCUMENTS);
    /*public enum FimTabs
    {
        fimLender(BaseTableAnchors.FIM_LENDER,"FIM_LENDER","LENDER_ID"),
        fimAddress(BaseTableAnchors.FIM_ADDRESS,"FIM_ADDRESS","OTHER_ADDRESS_ID"),
        fimInsurance(BaseTableAnchors.FIM_INSURANCE,"FIM_INSURANCE","INSURANCE_ID"),
        fimGuarantor(BaseTableAnchors.FIM_GUARANTOR,"FIM_GUARANTOR","GUARANTOR_ID"),
        fimLegalViolation(BaseTableAnchors.FIM_LEGAL_VIOLATION,"FIM_LEGAL_VIOLATION","VIOLATION_ID"),
        fimMysteryShopper(BaseTableAnchors.FIM_MYSTERY_SHOPPER,"FIM_MYSTERY_SHOPPER","MYSTERY_SHOPPER_ID"),
        fimComplaint(BaseTableAnchors.FIM_COMPLAINT,"FIM_COMPLAINT","COMPLAINT_ID"),
        fimPicture(BaseTableAnchors.FIM_PICTURE,"FIM_PICTURE","PICTURE_TRANSFER_ID"),
        fimTraining(BaseTableAnchors.FIM_TRAINING,"FIM_TRAINING","TRAINING_ID"),
        fimTransfer(BaseTableAnchors.FIM_TRANSFER,"FIM_TRANSFER","TRANSFER_ID"),
        fimRenewal(BaseTableAnchors.FIM_RENEWAL,"FIM_RENEWAL","RENEWAL_ID"),
        fimEvents(BaseTableAnchors.FIM_EVENTS,"FIM_EVENTS","EVENTS_ID"),
        fimDocuments(BaseTableAnchors.FIM_DOCUMENTS,"FIM_DOCUMENTS","DOCUMENTS_ID"),//g6-20160224-220
        fimOwners(BaseTableAnchors.OWNERS,"OWNERS","OWNER_NO"),
        fimEmployees(BaseTableAnchors.FIM_EMPLOYEES_MAPPING,"FIM_EMPLOYEES_MAPPING","EMPLOYEE_NO");


        private final String tableAnchor;
        private final String tableName;
        private final String primaryKey;

        private FimTabs(String tableAnchor, String tableName,String primaryKey)
        {
            this.tableAnchor = tableAnchor;
            this.tableName = tableName;
            this.primaryKey = primaryKey;
        }

    }*/

/*    public static String getTabQuery(FimTabs fimTab,String queryType,String franNo)
    {
        String query = FieldNames.EMPTY_STRING;
        if(fimTab!=null && StringUtil.isValid(queryType))
        {
            if("update".equals(queryType))
            {
                if(BaseTableAnchors.OWNERS.equals(fimTab.tableAnchor))
                {
                    query = "UPDATE OWNERS LEFT JOIN FIM_OWNERS ON FIM_OWNERS.FRANCHISE_OWNER_ID = OWNERS.OWNER_ID SET ENTITY_ORDER = ? WHERE FRANCHISE_OWNER_ID = ? AND OWNERS.FRANCHISEE_NO = "+franNo;
                }
                else if(BaseTableAnchors.FIM_EMPLOYEES_MAPPING.equals(fimTab.tableAnchor))
                {
                    query = "UPDATE FIM_EMPLOYEES_MAPPING LEFT JOIN FIM_EMPLOYEES ON FIM_EMPLOYEES.EMPLOYEE_ID = FIM_EMPLOYEES_MAPPING.EMPLOYEE_ID SET ENTITY_ORDER = ? WHERE FIM_EMPLOYEES.EMPLOYEE_ID = ? AND FIM_EMPLOYEES_MAPPING.FRANCHISEE_NO = "+franNo;
                }
                else
                {
                    query = "UPDATE "+fimTab.tableName+" SET ENTITY_ORDER = ? WHERE "+fimTab.primaryKey+" = ?";
                }
            }
            else if("count".equals(queryType))
            {
                query  = "SELECT COUNT("+fimTab.primaryKey+") AS COUNT  FROM "+fimTab.tableName;

                if("fimDocuments".equals(fimTab.tableAnchor))
                {
                    query+=" WHERE  TAB_NAME = 'Documents' ";
                }
                String groupCol = "ENTITY_ID";
                if(BaseTableAnchors.OWNERS.equals(fimTab.tableAnchor) || BaseTableAnchors.FIM_EMPLOYEES_MAPPING.equals(fimTab.tableAnchor))
                {
                    groupCol = "FRANCHISEE_NO";
                }
                query+=" GROUP BY "+groupCol+" ORDER BY COUNT DESC LIMIT 0,1";
            }
            else
            {
                String whereClause = " WHERE ENTITY_ID = ?";
                if(BaseTableAnchors.OWNERS.equals(fimTab.tableAnchor) || BaseTableAnchors.FIM_EMPLOYEES_MAPPING.equals(fimTab.tableAnchor))
                {
                    whereClause = " WHERE FRANCHISEE_NO = ?";
                }
                else if("fimDocuments".equals(fimTab.tableAnchor))
                {
                    whereClause = " WHERE ENTITY_ID = ? AND TAB_NAME = 'Documents' ";
                }
                query = "SELECT MAX(ENTITY_ORDER) AS ENTITY_ORDER FROM "+fimTab.tableName+whereClause;
            }
        }
        return query;
    }

    public static void setTabEntityOrder(HttpServletRequest request)
    {
        try
        {
            String tableAnchor   = request.getParameter(BaseFieldNames.TABLE_ANCHOR);
            if(fimTables.contains(tableAnchor))
            {
                FimTabs tab = FimTabs.valueOf(tableAnchor);
                HttpSession session  = request.getSession();
                String franNo =  (String) session.getAttribute("fimFranchiseeNo");
                String[] ids;
                if("fimOwners".equals(tableAnchor))
                {
                    String[] exOwnerIDs = (String[])session.getAttribute("exOwnerIDs");
                    if(exOwnerIDs!=null && exOwnerIDs.length>0)
                    {
                        ids = exOwnerIDs;
                    }
                    else
                    {
                        ids = new String[]{(String)session.getAttribute("fimOwnerId")};
                    }
                    session.removeAttribute("exOwnerIDs");
                    session.removeAttribute("fimOwnerId");
                }
                else if("fimEmployees".equals(tableAnchor))
                {
                   String[] exEmployeeIDs = (String[])session.getAttribute("exEmployeeIDs");
                   if(exEmployeeIDs!=null && exEmployeeIDs.length>0)
                   {
                       ids = exEmployeeIDs;
                   }
                   else
                   {
                       ids = new String[]{(String)session.getAttribute("employeeId")};
                   }
                   session.removeAttribute("exEmployeeIDs");
                   session.removeAttribute("employeeId");
                }
                else
                {
                    SequenceMap insertedIds = (SequenceMap) session.getAttribute("insertedId");
                    ids = new String[]{insertedIds.get(tableAnchor).toString()};
                }
                manipulateOrder(tab,franNo,ids);
            }
        }catch(Exception e)
        {
        }
    }

    public static void manipulateOrder(FimTabs tab,String franNo,String[] ids)throws Exception
    {
        if(StringUtil.isValid(franNo) && ids!=null && ids.length>0)
        {
            for(String id:ids)
            {
                ResultSet result = QueryUtil.getResult(getTabQuery(tab,"select",franNo),new String[]{franNo});
                int order = 0;
                if(result!=null && result.next())
                {
                    String prevOrder = result.getString("ENTITY_ORDER");
                    if(StringUtil.isvalidInteger(prevOrder))
                    {
                        order = Integer.parseInt(prevOrder) + 1;
                    }
                }
                QueryUtil.update(getTabQuery(tab,"update",franNo),new String[]{order+FieldNames.EMPTY_STRING,id});
            }
        }
    }*/
    private String getMaxKeyword(String tableAnchor)
    {
        String maxKeyword = FieldNames.EMPTY_STRING;
        if(fimTables.indexOf(tableAnchor)!=-1)
        {
            FimTabs tab = FimTabs.valueOf(tableAnchor);
            ResultSet result = QueryUtil.getResult(getTabQuery(tab,"count",null),null);
            if(result!=null && result.next())
            {
                maxKeyword =  result.getString("COUNT");
            }
        }
        return maxKeyword;
    }
    public static String getKeywordString(Map fieldsMap)
    {
        StringBuilder keywordString = new StringBuilder();
        try
        {
            if(fieldsMap!=null && !fieldsMap.isEmpty())
            {
                Field field;
                String keyWord,maxKeyword;
                Iterator fieldBucket =  fieldsMap.keySet().iterator();
                while(fieldBucket.hasNext())
                {
                    keyWord = (String)fieldBucket.next();
                    field =  (Field)fieldsMap.get(keyWord);
                    maxKeyword = field.getMaxKeyword();
                    if(!StringUtil.isValid(maxKeyword))
                    {
                        Map keyWordInfo = field.getKeyWordInfo(keyWord);
                        if(keyWordInfo!=null && !keyWordInfo.isEmpty())
                        {
                            maxKeyword = (String)keyWordInfo.get("maxKeyword");
                        }
                    }
                    int length;
                    if(StringUtil.isvalidInteger(maxKeyword) && (length = Integer.parseInt(maxKeyword))>1)
                    {
                        String tempKeyword = keyWord.substring(0, keyWord.lastIndexOf('$'));
                        for(int i=1;i<=length;i++)
                        {
                            keywordString.append("\"").append(tempKeyword).append("SL").append(i).append("$").append("\",");
                        }
                    }
                    else
                    {
                        keywordString.append("\"").append(keyWord).append("\",");
                    }
                }
                keywordString = new StringBuilder(keywordString.substring(0,keywordString.length()-1));
                keywordString = keywordString.append("\n\n");
            }
        }
        catch(Exception e)
        {
        }
        return keywordString.toString();
    }
    
    public static SequenceMap getAddressData(String tableAnchor,String foreignId,int addressOrder)
    {
        ResultSet result = null;
        try
        {
            FieldMappings fieldMapping = DBUtil.getInstance().getFieldMappings(tableAnchor);
            String tableName = fieldMapping.getTableName();
            StringBuilder query = new StringBuilder("SELECT ");
            Field[] fields = fieldMapping.getAllFieldsArray();
            for(int i=0;i<fields.length;i++)
            {
                if(i!=0)query.append(",");
                query.append(tableName).append(".").append(fields[i].getDbField());
            }
            query.append(" FROM ").append(tableName).append(" WHERE ").append("FOREIGN_ID").append(" IN (").append(foreignId).append(")");
            if(addressOrder>=0)
            {
                query.append(" AND ").append(" ADDRESS_ORDER = ").append(addressOrder);
            }
            query.append(" ORDER BY FIELD(").append("FOREIGN_ID").append(",").append(foreignId).append(")");
            result	=  QueryUtil.getResult(query.toString(),new String[]{});
            if(result == null)
            {
                return null;
            }
            SequenceMap map =  SQLUtilHelper.getSequenceMap(result, fields, fieldMapping.getIdField());
            return map;
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }
    }

    public static SequenceMap getOwnerEmployeeData(String tableAnchor,String fNo)
    {
        ResultSet result = null;
        try
        {
            FieldMappings fieldMapping = DBUtil.getInstance().getFieldMappings(tableAnchor);
            String tableName = fieldMapping.getTableName();
            String whereClause  = "fimOwners".equals(tableAnchor)?" LEFT JOIN OWNERS ON FIM_OWNERS.FRANCHISE_OWNER_ID = OWNERS.OWNER_ID WHERE OWNERS.FRANCHISEE_NO = ?":" LEFT JOIN FIM_EMPLOYEES_MAPPING ON FIM_EMPLOYEES.EMPLOYEE_ID = FIM_EMPLOYEES_MAPPING.EMPLOYEE_ID WHERE FIM_EMPLOYEES_MAPPING.FRANCHISEE_NO = ?";
            StringBuilder query = new StringBuilder("SELECT ");
            Field[] fields = fieldMapping.getAllFieldsArray();
            for(int i=0;i<fields.length;i++)
            {
                if(i!=0)query.append(",");
                query.append(tableName).append(".").append(fields[i].getDbField());
            }
            query.append(" FROM ").append(tableName).append(whereClause).append(" ORDER BY ENTITY_ORDER ");

            result	=  QueryUtil.getResult(query.toString(),new String[]{fNo});
            if(result == null)
            {
                return null;
            }
            SequenceMap map =  SQLUtilHelper.getSequenceMap(result, fields, fieldMapping.getIdField());

            return map;
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }
    }
    
    /*public boolean removeFieldMappingsFromAllTenants(String tableAnchor)
    {
    	MultiTenantGlobalContext globalContext = MultiTenantGlobalContext.getInstance();
    	Map<String, ITenantContext> tenantBucket	=	globalContext.getTenants();
    	if(tenantBucket != null && tenantBucket.size() > 0){
    		Iterator tenantItr	=	tenantBucket.values().iterator();
    		while(tenantItr.hasNext()){
    			ITenantContext tenantContext 	= 	(ITenantContext)tenantItr.next();
    			SyncMap tableFieldMappings 		=	(SyncMap)tenantContext.getAttribute(WebKeys.TableFieldMappingsKey);
    			if (tableFieldMappings==null)
    			{
    				tableFieldMappings = new SyncMap(new HashMap());
    				tenantContext.setAttribute(WebKeys.TableFieldMappingsKey,tableFieldMappings);
    			}
    			FieldMappings fieldMappings = (FieldMappings)tableFieldMappings.getOne(tableAnchor);
				if (fieldMappings != null) {
					try {
						getTableFieldMappings().remove(tableAnchor);
					} catch(Exception e) {
					}
				}
    		}
    	}
		return true;
	}*/
    
}
