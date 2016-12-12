/**
 * Common Utility for sharing useful and common resources in case of form builder.
 * @author Mohit Sharma
 * @date 24 Oct 2012
 * Created for ENH_MODULE_CUSTOM_TABS
 */

package com.home.builderforms;

import com.home.builderforms.BuilderFormMgr;
import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.BuilderFormFieldNames;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.TableXMLDAO;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BuilderFormUtil 
{
	private static	Logger logger = Logger.getLogger(BuilderFormUtil.class);

	private static void init()
	{
		Info requestInfo = new Info();
		requestInfo.set(BuilderFormFieldNames.TABLE_ANCHOR,"builderModules");
		requestInfo.set(BuilderFormFieldNames.XML_ELEMENT,"module");
		requestInfo.set(BuilderFormFieldNames.XML_KEY,"module-name");
		requestInfo.set(BuilderFormFieldNames.KEY_TYPE,"SequenceMap");
		Object mMap1 = BuilderFormWebImpl.getInstance().processBuilderXmlFileAttributes(requestInfo);
		SequenceMap mMap = null;
		if(mMap1 instanceof SequenceMap) {
			mMap = (SequenceMap)mMap1;
		}
		
		Info moduleInfo = null;
		if(mMap != null) {
			Iterator<?> it2 = mMap.keys().iterator();
			String key 		 = null;
			SequenceMap sMap = null;
			String 	moduleName   = null;
			String moduleDisplay = null;
			String display = null;
			while(it2.hasNext()) {
				
				moduleInfo = new Info();
				key = (String)it2.next();
				sMap = (SequenceMap)mMap.get(key);
				moduleName = (String)sMap.get("module-name");
				/*if("fimarea".equals(moduleName)){
					moduleName="area";
				}else if("fimmu".equals(moduleName)){
					moduleName="mu";
				}*/
				//73298
				moduleDisplay = (String)sMap.get("module-display");
				display = (String)sMap.get("display");
				if((!ModuleUtil.fsImplemented() && "fs".equals(moduleName)) || (!ModuleUtil.cmImplemented() && ("cm".equals(moduleName) || "account".equals(moduleName))) || (!ModuleUtil.fimImplemented() && "fim".equals(moduleName)) || (!ModuleUtil.schedulerImplemented() && "scheduler".equals(moduleName)) || (!ModuleUtil.fimImplemented() && "fimarea".equals(moduleName)) || (!ModuleUtil.fimImplemented() && "fimmu".equals(moduleName))){
					continue;
				}
				if(!"yes".equals("yes") && "site".equals(moduleName)) {
					continue;
				}
				
				
				if((!ModuleUtil.cmImplemented() || false) && "lead".equals(moduleName))
					continue;
				
				if((!ModuleUtil.cmImplemented() || false) && "opportunity".equals(moduleName))
					continue;
				
				if("yes".equals(display))
                    //MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_MODULE_NAMES.set(moduleName,LanguageUtil.getString(moduleDisplay,null,BaseUtils.getModuleKey(moduleDisplay)));
				moduleInfo.set("module-name",moduleName);
				moduleInfo.set("module-display",moduleDisplay);
				moduleInfo.set("moduleImplemented",ModuleUtil.isModuleImplemented(moduleName));
				moduleInfo.set("DOCUMENT_ATTACHMENT", sMap.get("document-attachment"));
				moduleInfo.set("DocumentTableAnchor", sMap.get("documentTableAnchor"));
				moduleInfo.set("documentId", sMap.get("documentId"));//ENH_MODULE_CUSTOM_TABS
				moduleInfo.set("display",display);
				moduleInfo.set("keyField", sMap.get("keyField"));
				moduleInfo.set("keyFieldDB",sMap.get("keyFieldDB"));
				moduleInfo.set("entityField", sMap.get("entityField"));
				moduleInfo.set("entityName", sMap.get("entityName"));
				moduleInfo.set("moduleNameDisplay",sMap.get("moduleNameDisplay"));
				moduleInfo.set("keyTable",sMap.get("keyTable"));
				moduleInfo.set("keyTableDB",sMap.get("keyTableDB"));
				moduleInfo.set(TableXMLDAO.FIELD_NAME,sMap.get(TableXMLDAO.FIELD_NAME));
				moduleInfo.set(TableXMLDAO.DISPLAY_NAME,sMap.get(TableXMLDAO.DISPLAY_NAME));
				moduleInfo.set(TableXMLDAO.DB_FIELD,sMap.get(TableXMLDAO.DB_FIELD));
				moduleInfo.set(TableXMLDAO.DATA_TYPE,sMap.get(TableXMLDAO.DATA_TYPE));
				//MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_MODULE_MAP.put(moduleName, moduleInfo);
			}
		}
       // MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_UTIL_INITIALIZED = true;
	}

	public static SequenceMap getModuleMap()
	{
        /*if(!MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_UTIL_INITIALIZED)
        {
            init();
        }*/
		//return MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_MODULE_MAP;
		return new SequenceMap();
	}
	
	public static Info getFormBuilderModules()
	{
        /*if(!MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_UTIL_INITIALIZED)
        {
            init();
        }
		return MultiTenancyUtil.getTenantFormBuilderConstants().BUILDER_MODULE_NAMES;*/
		return new Info();
	}
	
	/**
	 * Function used while merging leads in FS/CM
	 * Copy all details in all custom tabs of old lead to new lead
	 * @author Yashukant Tyagi 
	 * @param tabLeadMap(tabName-oldLeadId)
	 * @param newLeadId
	 * @param updateParams
	 */
	public static boolean mergeCustomTabRecord(HashMap<String,String> tabLeadMap,String newLeadId,HashMap<Object,Object> updateParams){
		Iterator<String> iter = tabLeadMap.keySet().iterator();
		Info tabInfo = null;
		List<String> batchQuery = new ArrayList<String>();
		boolean updated = Boolean.FALSE;
		if(updateParams!=null){
			String moduleName = (String)updateParams.get(FieldNames.MODULE);
			String subModule = (String)updateParams.get(FieldNames.SUB_MODULE);
			SequenceMap customTabsMap = getModuleMap();
			if(customTabsMap!=null){
				BuilderFormMgr builderMgr = new BuilderFormMgr();
				tabInfo = (Info)customTabsMap.get(builderMgr.getBuilderFormDAO().handleSubModule(moduleName,subModule));
			}
		}
		
		String dbTableName = null;
		StringBuilder query = null;
		FieldMappings mappings = null;
		String modulekeyFieldDB = null;
		String tabKey = null;
		String previousLead = null;
		String dbFieldNames=null;
		try{
			while(iter.hasNext()){
				tabKey = iter.next();
				if(StringUtil.isValid(tabKey) && tabInfo!=null){
					mappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tabKey);
					previousLead = tabLeadMap.get(tabKey);
				}

				if(mappings!=null && tabInfo!=null){
					dbTableName = mappings.getTableName();
					if("FS_BQUAL_DETAILS".equals(dbTableName))//BOEFLY_INTEGRATION
						continue;//BOEFLY_INTEGRATION
					modulekeyFieldDB = tabInfo.get("keyFieldDB");
					query=new StringBuilder();//P_CM_B_61791
					query.append("INSERT INTO "+dbTableName);
					query.append(" SELECT NULL,"+newLeadId);
					dbFieldNames=getAllValidDbFieldsSansID(mappings,modulekeyFieldDB);
					if(StringUtil.isValid(dbFieldNames)){
						query.append(", ").append(dbFieldNames);
					}
					query.append(" FROM "+dbTableName+ " ");
					query.append(" WHERE "+modulekeyFieldDB+" = '"+previousLead+"' ");
					batchQuery.add(query.toString());
				}
			}
			
			QueryUtil.batchUpdate(batchQuery, null);
			updated = Boolean.TRUE;
		}catch(Exception ex){
			logger.error("Error while mergeCustomTabRecord",ex);
			ex.printStackTrace();
		}
		return updated;
	}
	
	/**
	 * Return all D.b. Field Names Other than primary Key
	 * @param mappings
	 * @param skipField- Field to be skipped if any(Name as in database)
	 * @return
	 */
	private static String getAllValidDbFieldsSansID(FieldMappings mappings,String skipField) {
		StringBuilder dbFields = new StringBuilder();
		
		if(mappings!=null){
			Field[] allFields = mappings.getAllFieldsArraySansID();
			Field field = null;
			int allFieldsLength = 0;
			if(allFields!=null){
				allFieldsLength = allFields.length;
			}
			for(int i=0 ; i < allFieldsLength; i++) {
				field = allFields[i];
				if(field!=null && field.fieldAllowedInMain() && (skipField==null || !skipField.equals(field.getDbField())) ) {
					dbFields.append(field.getDbField()).append(",");
				}
			}
		}
		return dbFields.toString().replaceAll(",$|^,", "");
	}
	
	
	public static boolean canActivateCustomTab(Info moduleInfo) {
		String tabName = moduleInfo.getString(BuilderFormFieldNames.TAB_NAME);
		//BOEFLY_INTEGRATION : START
		if(Constants.BOEFLY_TAB_NAME.equals(tabName)){
			return Boolean.FALSE;
		}
		//BOEFLY_INTEGRATION : END
		return Boolean.TRUE;
	}
}
