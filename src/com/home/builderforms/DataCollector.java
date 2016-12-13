package com.home.builderforms;

import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.home.builderforms.AdminMgr;
import com.home.builderforms.CommonMgr;
//import com.appnetix.app.components.locationmgr.manager.LocationMgr;
import com.home.builderforms.MasterDataMgr;
import com.home.builderforms.RegionMgr;
import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.BuilderFormFieldNames;
import com.home.builderforms.UserTimezoneMap;
import com.home.builderforms.UserRoleMap;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateTime;
import com.home.builderforms.DateUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.StrutsUtil;
import com.home.builderforms.TimeZoneUtils;
import com.home.builderforms.DependentChild;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.ForeignTable;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.SyncWithField;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.UserTabConfig;
import com.home.builderforms.UserTabConfigUtil;
import com.home.builderforms.CheckBox;
import com.home.builderforms.Combo;
import com.home.builderforms.Radio;



/**
 * $Author:   Abhishek Gupta
 * $Version:  $1.1
 * $Date: 2016/11/11 07:16:02 $17 Jan 2012
 * ====================================================================
 * Basic Description/Data Flow of Bean
 * P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 * BOEFLY_INTEGRATION		19/08/2014		Veerpal Singh			Enh				A third party integration with Boefly through REST-API for lead sync.
    ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 */
public class DataCollector  {
	private static HttpServletRequest request		= null;
	private static final Logger logger		= Logger.getLogger(DataCollector.class);
	static DataManipulator collector = new DataManipulator();
	public static Method[] methodRef		 = null;
	static public HashMap  sMap		 = null;

	String menuName = null;
	private int aliasCount				= 1;
	private int aliasFieldCount			= 1;
	private String fileType		= null;
	SequenceMap selForeignTables = null;
    private int countTable = 1 ;
    String tableArr[] = null;
    public static boolean isData=true;
    static public StringBuilder sbJSCombo = null;
    
    public int getTotalTable() {
        return countTable;
    }
	
	public void init(HttpServletRequest request) {
		this.request	= request;
		if(methodRef == null)
			methodRef = getClass().getDeclaredMethods();
		if(sMap == null) {
			sMap = new HashMap();
			for(Method m : methodRef) {
				sMap.put(m.getName(), m);
			}
		}
	}

	public void init(HttpServletRequest request, String sMainTable, String[] tableNames){
		this.request = request;
		if(tableArr != null) 
			tableArr = null;
		tableArr = tableNames;
		
		menuName = (String)request.getSession().getAttribute("menuName");
	
		FieldMappings mappings		= DBUtil.getInstance().getFieldMappings(sMainTable);
		DataManipulator.userTimeZone = (String)request.getSession().getAttribute("userTimeZone");
		if(mappings != null){
			selForeignTables = new SequenceMap();
			SequenceMap smForeignTables		= mappings.getForeignTablesMap();
			if(smForeignTables != null){
				int sizeForTable				= smForeignTables.size();
				for(int i = 0 ; i < sizeForTable ; i++){
					ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
					if(tableArr!=null) {
						for(int k = 0 ; k < tableArr.length ; k++){
							if (foreignTable.getName().equals(tableArr[k].trim())){
								selForeignTables.put(foreignTable.getName(), foreignTable);
							}
						}
					}
				}
			}
		}
	}
	
	public static Combo getAllRegionsInfo(Field fld,Boolean isModify) {
 		try{
 			
 			String regionId =""; 
 			
 			if(request!=null && request.getSession()!=null)
 			  regionId=(String)request.getSession().getAttribute("regionId");
 			
 			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(RegionMgr.newInstance().getRegionsDAO().getAllAreas((String)regionId));
			return combo;
 		} catch(Exception e){
 			e.printStackTrace();
 			return null;
 		}
 	}
	
	public static Combo getSearchMDCombo(Field fld,Boolean isModify)
	{
		String entityCode 	= fld.getComboSourceMethodArgs();
		Info comboInfo = new Info();
		comboInfo.setFields(MasterDataMgr.newInstance().getMasterDataDAO().getIdAndValue(entityCode));
		Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(comboInfo);
		return combo;
	}
	
	public static Radio getSearchMdRadio(Field fld,Boolean isModify)
	{
		String entityCode 	= fld.getRadioSourceMethodArgs();
		Info info = new Info();
		info.setFields(MasterDataMgr.newInstance().getMasterDataDAO().getDataMasterDataFieldType(entityCode,null));
		Radio radio0 = new Radio(fld.getDisplayName());
		Iterator it = info.getKeySetIterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			radio0.setOption(info.get(key), key);
		}
		return radio0;
	}
	
	public static CheckBox getSearchMDCheckBox(Field fld,Boolean isModify,String value)
	{
		String entityCode 	= fld.getCheckboxSourceMethodArgs();
		Info info = new Info();
		info.setFields(MasterDataMgr.newInstance().getMasterDataDAO().getDataMasterDataFieldType(entityCode,null));
		CheckBox check0 = new CheckBox(fld.getDisplayName());
		Iterator it = info.getKeySetIterator();
		HashMap arrLst = null;
		if(value != null) {
			arrLst = new HashMap();
			StringTokenizer nstr = new StringTokenizer(value, ",");
			while(nstr.hasMoreElements()) {
				String str = (String)nstr.nextElement();
				if(StringUtil.isValid(str.trim())) {
					arrLst.put(str.trim(), str.trim());
				}
			}
		}
		while(it.hasNext()) {
			String key = (String)it.next();
			if(value != null) {
				check0.setValue(info.get(key), key, arrLst.get(key) == null?false:true);
			} else {
				check0.setValue(info.get(key), key);
			}
		}
				
		return check0;
	}
	
	public static Combo getSearchMdCombo(Field fld,Boolean isModify)
	{
		String entityCode 	= fld.getComboSourceMethodArgs();
		Info comboInfo = new Info();
		comboInfo.setFields(MasterDataMgr.newInstance().getMasterDataDAO().getDataMasterDataFieldType(entityCode,null));
		Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(comboInfo);
		return combo;
	}
	
	/*public static Combo getTransferStatusCombo(Field fld,Boolean isModify)
	{
		Info comboInfo = AdminMgr.newInstance().getFimTransferlDAO().getTransferStatusValue();
		Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(comboInfo);
		return combo;
	}*/
	
	/*public static Combo getStoreTypeCombo(Field fld,Boolean isModify)
	{
		Info comboInfo = LocationMgr.newInstance().getLocationsDAO().getAllStoreTypeInfo();
		Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(comboInfo);
		return combo;
	}*/
	
	/*public static Combo comboFimState(Field fld,String countryID,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
		if(!isModify){
			countryID = "1";
		} else {
			if(countryID.equals("0")) {
//				countryID = MultiTenancyUtil.getTenantConstants().DEFAULT_COUNTRY_ID;
			}
		}
		SequenceMap stateMap = null;
		if(!countryID.equals("0")) {
//			stateMap = com.home.builderforms.CacheDataUtil.getStateSequenceMap(countryID);
			stateMap = NewPortalUtils.getStatesByCountry(countryID);
		}
		if(!countryID.equals("0")) {
//			comboInfo.setFields(new SequenceMap(stateMap.getHashMap()));
			comboInfo.setFields((SequenceMap)stateMap.cloneInfo());
		}
		comboInfo.insert(0,"Select", "");
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select State"));
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			combo.setJScript("onchange=\""+dependentChildFunction+"\"");
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		combo.setInfo(comboInfo);
		return combo;
	}
	
	public static Combo comboCountryForPreferredLocation(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
		SequenceMap countryMap = PortalUtils.getCountries();
		comboInfo.setFields((SequenceMap)countryMap.cloneInfo());
		combo.setInfo(comboInfo);
		comboInfo.insert(0,"Select", "");
		
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String functionName = "";
		String dependentChildFunction = getDependentChildFunctions(fld);
		String jScriptFunctions = "";
		
		if(StringUtil.isValid(fld.getDependentField())) {
			String dField = "temp"+fld.getDependentField();
			functionName = "getCombo('state',this.value,'"+dField+"', '-1');";
		}
		
		if(StringUtil.isValidNew(functionName)) {
			jScriptFunctions = jScriptFunctions + functionName;
		}
		
		if(StringUtil.isValidNew(dependentChildFunction)) {
			jScriptFunctions = jScriptFunctions + dependentChildFunction;
		}
		
		if(StringUtil.isValidNew(jScriptFunctions)) {
			combo.setJScript("onchange=\""+jScriptFunctions+"\"");
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select Country"));
		if(!isModify)
			combo.setComboValue(MultiTenancyUtil.getTenantConstants().DEFAULT_COUNTRY_ID);
		return combo;
	}
	
	public static Combo comboFimCountry(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
//		SequenceMap countryMap = com.home.builderforms.CacheDataUtil.getActiveCountryMap();
		SequenceMap countryMap = PortalUtils.getCountries();
//		comboInfo.setFields(new SequenceMap(countryMap.getHashMap()));
		comboInfo.setFields((SequenceMap)countryMap.cloneInfo());
		combo.setInfo(comboInfo);
		comboInfo.insert(0,"Select", "");
		
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String functionName = "";
		String dependentChildFunction = getDependentChildFunctions(fld);
		String jScriptFunctions = "";
		
		if(StringUtil.isValid(fld.getDependentField())) {
			functionName = "getCombo('state',this.value,'"+fld.getDependentField()+"', '-1');";
		}
		
		if(StringUtil.isValidNew(functionName)) {
			jScriptFunctions = jScriptFunctions + functionName;
		}
		
		if(StringUtil.isValidNew(dependentChildFunction)) {
			jScriptFunctions = jScriptFunctions + dependentChildFunction;
		}
		
		if(StringUtil.isValidNew(jScriptFunctions)) {
			combo.setJScript("onchange=\""+jScriptFunctions+"\"");
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select Country"));
		if(!isModify)
			combo.setComboValue(MultiTenancyUtil.getTenantConstants().DEFAULT_COUNTRY_ID);
		return combo;
	}
	
	public static Combo comboAddressHeading(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
//		SequenceMap countryMap = com.home.builderforms.CacheDataUtil.getActiveCountryMap();
		SequenceMap countryMap = PortalUtils.getCountries();
//		comboInfo.setFields(new SequenceMap(countryMap.getHashMap()));
		comboInfo.setFields((SequenceMap)countryMap.cloneInfo());
		combo.setInfo(comboInfo);
		return combo;
	}*/
	
	
	public static Combo getSearchStateCombo(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
		SequenceMap sMap = RegionMgr.newInstance().getRegionsDAO().getIdAndValue();
//		comboInfo.setFields(new SequenceMap(sMap.getHashMap()));
		comboInfo.setFields((SequenceMap)sMap.cloneInfo());
		comboInfo.insert(0,"Select", "");
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select State"));
		combo.setInfo(comboInfo);
		return combo;
	}
	
	
	public static Combo getSearchCountryCombo(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
		SequenceMap sMap = RegionMgr.newInstance().getCountriesDAO().getCountryIdAndValue();
//		comboInfo.setFields(new SequenceMap(sMap.getHashMap()));
		comboInfo.setFields((SequenceMap)sMap.cloneInfo());
		comboInfo.insert(0,"Select", "");
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select Country"));
		combo.setInfo(comboInfo);
		if(!isModify)
			combo.setComboValue("1");
		
		return combo;
	}
	
	
	public static Combo getAddressHeading(Field fld,Boolean isModify)
	{
		ResultSet result=null;
		SequenceMap returnMap	= new SequenceMap();
		//returnMap.put("-1" ,LanguageUtil.getString("Select"));//// BUG-1417///9895
		try
		{
			String query;
			query = "SELECT ADDRESS_HEADING_ID, ADDRESS_HEADING_NAME  FROM ADDRESS_STATUS ORDER BY ORDER_NO";//P_FIM_BUG_14461
			
			result = QueryUtil.getResult(query,null);

			while (result.next())
			{
				returnMap.put(result.getString("ADDRESS_HEADING_ID"), LanguageUtil.getString(result.getString("ADDRESS_HEADING_NAME")));//// BUG-1417
			}
		} catch(Exception e)
		{
			logger.error("\nException in com/appnetix/app/portal/export - SearchDataCollector.java --> Exception in getAddressHeading()" , e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
		//comboInfo.setFields(new SequenceMap(returnMap.getHashMap()));
		comboInfo.setFields((SequenceMap)returnMap.cloneInfo());//P_FIM_BUG_14461
		combo.setInfo(comboInfo);
		return combo;
	}
	
	
	
	public static Radio getRadioType(Field fld, String tableName, String value) {
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		Info info = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value);//SMC-20140213-378
		Radio radio0 = new Radio(fld.getDisplayName());
		Iterator it = info.getKeySetIterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			if(value != null) {
				radio0.setOption(info.get(key), key, key.equals(value));
			} else {
				radio0.setOption(info.get(key), key);
			}
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onclick = \"" + dependentChildFunction + "\"";
			radio0.setJScript(jScriptFunction);
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		return radio0;
	}
	
	/**
	 * This function is used in case of dependent response.
	 * BB-20150203-259 (Dynamic Response based on parent field response)
	 * @param fld
	 * @param tableName
	 * @param value
	 * @param parentValue
	 * @return
	 */
	public static Radio getDependentRadioType(Field fld, String tableName, String value, String parentValue) {
		Radio radio0 = new Radio(fld.getDisplayName());
		//String dependentParentField = fld.getDependentParentField();
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		Info infoData = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName, tableName, value, parentValue); //dependent on parentValue
		Info infoAll = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName, tableName, value); //return all Data
		
		Iterator it = infoAll.getKeySetIterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			boolean disabled = true;
			if(StringUtil.isValidNew(infoData.getString(key))) {
				disabled = false;
			}
			if(value != null) {
				radio0.setOption(infoAll.get(key), key, key.equals(value), disabled);
			} else {
				radio0.setOption(infoAll.get(key), key, false, disabled);
			}
		}
		
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onclick = \"" + dependentChildFunction + "\"";
			radio0.setJScript(jScriptFunction);
		}

		return radio0;
	}

	/**
	 * BB-20150203-259 (Dynamic Response based on parent field response)
	 * @param fld
	 * @param tableName
	 * @param value
	 * @param parentValue
	 * @return
	 */
	public static Combo getDependentComboType(Field fld, String tableName, String value, String parentValue) {
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		Info info = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value, parentValue);//SMC-20140213-378
		Combo combo = new Combo(fld.getDisplayName());
		if("true".equals(fld.getisMultiSelect()))//BB-20150203-259 (FORM BUILDER MULTISELECT COMBO changes)
		{
			combo.setMultiple();
			combo.setComboClass("form-control");
		}
		combo.setInfo(info);
		if(value != null)
			combo.setComboValue(value);

		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onchange = \"" + dependentChildFunction +"\"";
			combo.setJScript(jScriptFunction);
		}
		return combo;
	}
	
	public static Combo getComboType(Field fld, String tableName, String value) {
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		Info info = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value);//SMC-20140213-378
		Combo combo = new Combo(fld.getDisplayName());
		if("true".equals(fld.getisMultiSelect()))
		{
			combo.setMultiple();
			combo.setComboClass("form-control");
		}
		combo.setInfo(info);
		if(value != null)
			combo.setComboValue(value);

		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onchange = \"" + dependentChildFunction +"\"";
			combo.setJScript(jScriptFunction);
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		return combo;
	}

	/**
	 * This function is to get the response data for checkboxes
	 * BB-20150203-259 (Dynamic Response based on parent field response)
	 * @param fld
	 * @param tableName
	 * @param value
	 * @param parentValue
	 * @return
	 */
	public static CheckBox getDependentCheckBoxType(Field fld, String tableName, String value, String parentValue) {
		CheckBox check0 = new CheckBox(fld.getDisplayName());
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		Info info = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value);
		Info infoChild = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value, parentValue);
		Iterator it = info.getKeySetIterator();
		HashMap arrLst = null;
		if(value != null) {
			arrLst = new HashMap();
			StringTokenizer nstr = new StringTokenizer(value, ",");
			while(nstr.hasMoreElements()) {
				String str = (String)nstr.nextElement();
				if(StringUtil.isValid(str.trim())) {
					arrLst.put(str.trim(), str.trim());
				}
			}
		}
		while(it.hasNext()) {
			String key = (String)it.next();
			boolean disabled = true;
			if(StringUtil.isValidNew(infoChild.getString(key))) {
				disabled = false;
			}
			if(value != null) {
				check0.setValue(info.get(key), key, arrLst.get(key) == null?false:true, disabled);
			} else {
				check0.setValue(info.get(key), key, false, disabled);
			}
		}
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onchange = \"" + dependentChildFunction + "\"";
			check0.setJScript(jScriptFunction);
		}
		
		return check0;
	}
	
	public static CheckBox getCheckBoxType(Field fld, String tableName, String value) {
		return getCheckBoxType(fld, tableName, value,null);
	}
	public static CheckBox getCheckBoxType(Field fld, String tableName, String value,String parentValue) {

		boolean haveParent=true;
		Info infoAsParent =null;
		
		String syncWith = fld.getSyncWithField();
		String fieldName = fld.getFieldName();
		if(StringUtil.isValidNew(syncWith)) {
			String[] name = syncWith.split("##");
        	tableName = name[0];
        	fieldName = name[1];
		}
		
		if(StringUtil.isValidNew(parentValue)){
			infoAsParent = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value,parentValue);// for disabling checkbox while modify and having dependency   
		}else{
			haveParent=false;
			infoAsParent=new Info();
		}
		
		Info info = BuilderFormWebImpl.getInstance().getBuilderFieldsInfo(fieldName,tableName,value);//SMC-20140213-378
		CheckBox check0 = new CheckBox(fld.getDisplayName());
		//System.out.println("fld.getDisplayName() for checkbox -- "+fld.getDisplayName());
		Iterator it = info.getKeySetIterator();
		HashMap arrLst = null;
		if(value != null) {
			arrLst = new HashMap();
			StringTokenizer nstr = new StringTokenizer(value, ",");
			while(nstr.hasMoreElements()) {
				String str = (String)nstr.nextElement();
				if(StringUtil.isValid(str.trim())) {
					arrLst.put(str.trim(), str.trim());
				}
			}
		}
		//System.out.println("arrLst for checkbox -- "+arrLst);
		while(it.hasNext()) {
			String key = (String)it.next();
			if(haveParent){
				if(value != null) {
					check0.setValue(info.get(key), key, arrLst.get(key) == null?false:true,!infoAsParent.containsKey(key));
				} else {
					check0.setValue(info.get(key), key,false,!infoAsParent.containsKey(key));
				}
			}else{
				if(value != null) {
					check0.setValue(info.get(key), key, arrLst.get(key) == null?false:true);
				} else {
					check0.setValue(info.get(key), key,false);
				}
			}
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String dependentChildFunction = getDependentChildFunctions(fld);
		if(StringUtil.isValidNew(dependentChildFunction)) {
			String jScriptFunction = "onchange = \"" + dependentChildFunction + "\"";
			check0.setJScript(jScriptFunction);
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		return check0;
	}
	
	/**
	 * This function will give the sync field value for the field
	 * P_Enh_Sync_Fields
	 * @param field
	 * @param syncFieldMap
	 * @return
	 */
	public static String getSyncFieldInFormPage(Field field, Map<String, String> syncFieldMap, String columnValue) {
		String fieldValue = "";
		if(("Radio".equals(field.getDisplayTypeField()) || "Checkbox".equals(field.getDisplayTypeField()) || "Combo".equals(field.getDisplayTypeField())) && "0".equals(columnValue)){//bug 74859
			columnValue="-1";
		}
		if(!StringUtil.isValidNew(columnValue)) {
			String syncWith = field.getSyncWithField();
			if(StringUtil.isValidNew(syncWith)) {
				fieldValue = (String)syncFieldMap.get(field.getFieldName());
			} else if(field.getSyncTotalMap() != null && field.getSyncTotalMap().size() > 0) { //in case sync-with is not there but it is assume as sync field. 
				SequenceMap syncTotalaMap = field.getSyncTotalMap();
				Iterator itr = syncTotalaMap.values().iterator();
				while(itr.hasNext()) {
					SyncWithField sync = (SyncWithField)itr.next();
					if(sync != null) {
						String value = (String)syncFieldMap.get(sync.getFieldName());
						if(StringUtil.isValidNew(value)) {
							FieldMappings   syncMapping=DBUtil.getInstance().getFieldMappings(sync.getTableAnchor());// Bug 71470 starts
							String displayType=syncMapping.getField(sync.getFieldName()).getDisplayTypeField();
							if(("Radio".equals(displayType) || "Checkbox".equals(displayType) || "Combo".equals(displayType)) && "0".equals(value)){
								continue;
							}else if("Date".equals(displayType)){			//P_B_77472
								fieldValue = DateUtil.getDisplayDate(value);
							}else{// Bug 71470 ends
								fieldValue = value;
								break;
							}
						}
					}
				}
			}
		} else {
			fieldValue = columnValue;
		}
		
		if(!StringUtil.isValidNew(fieldValue)) {
			fieldValue = "";
		}
		return fieldValue;
	}
	
	/**
	 * This function will provide the map of the fields that are being synced with other fields.
	 * P_Enh_Sync_Fields
	 * @param tableAnchor
	 * @param module
	 * @param primaryId
	 * @return
	 */
	public static Map<String, String> getAllSyncValuesWithFieldMap(String tableAnchor, String module, String primaryId) {
		return getAllSyncValuesWithFieldMap( tableAnchor,  module,  primaryId ,null);
	}
	public static Map<String, String> getAllSyncValuesWithFieldMap(String tableAnchor, String module, String primaryId,String fromWhere) {
		Map<String, String> syncValueFieldMap = new HashMap<String, String>();
		FieldMappings fieldMappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
		HeaderMap[] hMap = fieldMappings.getHeaderMap();
		LinkedHashSet<String> tableSet = new LinkedHashSet<String>();
		LinkedHashSet<String> fieldSet = new LinkedHashSet<String>();
		LinkedHashSet<String> accrossFieldSet = new LinkedHashSet<String>();
		LinkedHashSet<String> accrossTableSet = new LinkedHashSet<String>();
		String acrossMainColumn = "";
		String mainColumn = "";
		if("fs".equals(module)) {
			tableSet.add("FS_LEAD_DETAILS"); //table name
			accrossTableSet.add("FRANCHISEE");
			mainColumn = "LEAD_ID";
			acrossMainColumn = "ENTITY_ID";
		} else if("fim".equals(module)) {
			tableSet.add("FRANCHISEE"); //table name
			accrossTableSet.add("FS_LEAD_DETAILS");
			mainColumn = "ENTITY_ID";
			acrossMainColumn = "LEAD_ID";
		}

		String idFieldValue = "";
		String idDbColumn = "";
		try {
			for(HeaderMap h : hMap) {
				HeaderField hFld = h.getHeaderFields();
				if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
					continue;
				}
				Field[] flds = fieldMappings.getSectionTablesFieldsArrayWithOrderBy(hFld); //to get only active fields
				if(flds != null) {
					for(Field fld : flds) {
						if(fld==null){// Sync Field Feature starts
							continue;
						}// Sync Field Feature ends
						//for the sync-data tag in xml starts
						SequenceMap syncTotalaMap = fld.getSyncTotalMap();
						if(syncTotalaMap != null && syncTotalaMap.size() > 0) {
							Iterator itr = syncTotalaMap.values().iterator();
							while(itr.hasNext()) {
								SyncWithField sync = (SyncWithField)itr.next();
								if(sync != null) {
									if("within".equals(sync.getSyncModule()) || module.equals(sync.getSyncModule())) { //same module
										tableSet.add(sync.getTableName()); //table name
										if(fld.isPiiEnabled()){
											fieldSet.add(" AES_DECRYPT ( "+sync.getTableName()+"."+sync.getColumnName()+" , 'pvm@e20' ) "+" AS "+sync.getFieldName()); //column name with table name as alias name
										}else{
											fieldSet.add(sync.getTableName()+"."+sync.getColumnName()+" AS "+sync.getFieldName()); //column name with table name as alias name
										}
									} else { //across module
										accrossTableSet.add(sync.getTableName()); //table name
										if(fld.isPiiEnabled()){
											accrossFieldSet.add(" AES_DECRYPT ( "+sync.getTableName()+"."+sync.getColumnName()+" , 'pvm@e20' ) "+" AS "+sync.getFieldName()); //column name with table name as alias name
										}else{
											accrossFieldSet.add(sync.getTableName()+"."+sync.getColumnName()+" AS "+sync.getFieldName()); //column name with table name as alias name
										}
									}
								}
							}
						}
						//for the sync-data tag in xml ends

						String syncWith = fld.getSyncWithField();
						if(StringUtil.isValidNew(syncWith)) {
							String syncTable = syncWith.split("##")[0];
							String syncField = syncWith.split("##")[1];
							String otherField = syncWith.split("##")[2];

							FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(syncTable);
							String syncModule = fld.getSyncModule();
							try {
								Field field = fMapping.getField(syncField);
								String tableName = fMapping.getTableName();
								if("true".equals(otherField)) {
									if("within".equals(syncModule)) {
										idDbColumn = tableName+"."+fMapping.getIdField()[0].getDbField(); 
										idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), mainColumn, primaryId);
										tableSet.add(tableName); //table name

										field = fMapping.getOtherTableField(syncField);
										FieldMappings mapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(field.getTableAnchor());
										if(mapping != null) {
											tableName = mapping.getTableName();
										}
										tableSet.add(tableName); //table name
									} else {
										idDbColumn = tableName+"."+fMapping.getIdField()[0].getDbField();
										String foreignId = primaryId;
										if("fs".equals(syncModule)) {
											foreignId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId);
											idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), acrossMainColumn, foreignId);											
										} else {
											foreignId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId);
											idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), acrossMainColumn, foreignId);
										}
										accrossTableSet.add(tableName); //table name

										field = fMapping.getOtherTableField(syncField);
										FieldMappings mapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(field.getTableAnchor());
										if(mapping != null) {
											tableName = mapping.getTableName();
										}
										accrossTableSet.add(tableName); //table name
									}
								}
								if(field != null) {
									if("within".equals(syncModule)) {
										tableSet.add(tableName); //table name
										if(field.isFieldOfOtherTable()) {
											if(fld.isPiiEnabled()){
												//fieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name
												if("ADDRESS".equals(tableName)){
													String tableAlias = field.getTableName();
													String order = tableAlias.split("_")[1];
													fieldSet.add(tableName+"."+field.getDbField()+" AS '"+fld.getFieldName()+":"+order+"' "); //column name with table name as alias name
													fieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS '"+fld.getFieldName()+":"+order+"' "); //column name with table name as alias name
													fieldSet.add(" ADDRESS_ORDER "); //column name with table name as alias name
												}else{
													fieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name
												}
											
											}else{
												if("ADDRESS".equals(tableName)){
													String tableAlias = field.getTableName();
													String order = tableAlias.split("_")[1];
													fieldSet.add(tableName+"."+field.getDbField()+" AS '"+fld.getFieldName()+":"+order+"' "); //column name with table name as alias name
													fieldSet.add(" ADDRESS_ORDER "); //column name with table name as alias name
												}else{
													fieldSet.add(tableName+"."+field.getDbField()+" AS "+fld.getFieldName()); //column name with table name as alias name	
												}
											}
										} else {
											if(fld.isPiiEnabled()){
												fieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name
											}else{
												fieldSet.add(tableName+"."+field.getDbField()+" AS "+fld.getFieldName()); //column name with table name as alias name
											}
										}
									} else {
										accrossTableSet.add(tableName); //table name
										if(field.isFieldOfOtherTable()) {
											if(fld.isPiiEnabled()){
												//accrossFieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name
												if("ADDRESS".equals(tableName)){
													String tableAlias = field.getTableName();
													String order = tableAlias.split("_")[1];
													accrossFieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS '"+fld.getFieldName()+":"+order+"' "); //column name with table name as alias name
													accrossFieldSet.add(" ADDRESS_ORDER "); //column name with table name as alias name
												}else{
													accrossFieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name	
												}
											}else{
												//accrossFieldSet.add(tableName+"."+field.getDbField()+" AS "+fld.getFieldName()); //column name with table name as alias name
												if("ADDRESS".equals(tableName)){
													String tableAlias = field.getTableName();
													String order = tableAlias.split("_")[1];
													accrossFieldSet.add(tableName+"."+field.getDbField()+" AS '"+fld.getFieldName()+":"+order+"' "); //column name with table name as alias name
													accrossFieldSet.add(" ADDRESS_ORDER "); //column name with table name as alias name
												}else{
													accrossFieldSet.add(tableName+"."+field.getDbField()+" AS "+fld.getFieldName()); //column name with table name as alias name	
												}
											}
										} else {
											if(fld.isPiiEnabled()){
												accrossFieldSet.add(" AES_DECRYPT ( "+tableName+"."+field.getDbField()+" , 'pvm@e20' ) "+" AS "+fld.getFieldName()); //column name with table name as alias name
											}else{
												accrossFieldSet.add(tableName+"."+field.getDbField()+" AS "+fld.getFieldName()); //column name with table name as alias name
											}
										}
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								fMapping = null;
							}
						}
					}
				}
			}
			if(!StringUtil.isValidNew(fromWhere)) {
				getSyncFieldMap(fieldSet, tableSet, module, idDbColumn, idFieldValue, primaryId, syncValueFieldMap);
			}
			if(accrossFieldSet != null && accrossTableSet != null) {
				String acrossModule = "";
				if(!StringUtil.isValidNew(fromWhere)) {
					if("fs".equals(module)) {
						acrossModule = "fim";
						primaryId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId);
					} else {
						acrossModule = "fs";
						primaryId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId);
					}
				} else { //case when lead is moving to FIM or FO
					acrossModule = "fs"; 
				}
				
				if(StringUtil.isValidNew(primaryId)) {
					getSyncFieldMap(accrossFieldSet, accrossTableSet, acrossModule, idDbColumn, idFieldValue, primaryId, syncValueFieldMap);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			fieldMappings = null;
		}
		return syncValueFieldMap;
	}
	
	private static void getSyncFieldMap(LinkedHashSet<String> fieldSet, LinkedHashSet<String> tableSet, String module, String idDbColumn, String idFieldValue, String primaryId, Map<String, String> syncValueFieldMap) {
		try {
			//creating query for syncFieldMap
			boolean addressFlag = false;
			StringBuilder queryBuilder = new StringBuilder("SELECT");
			StringBuilder columnBuilder = new StringBuilder(); //for selecting columns
			StringBuilder tableBuilder = new StringBuilder(); //for selecting columns
			if(fieldSet != null && tableSet != null) {
				Iterator it = fieldSet.iterator();
				while(it.hasNext()) {
					columnBuilder.append(", "+it.next());
				}

				it = null;
				it = tableSet.iterator();
				int count = 0;
				while(it.hasNext()) {
					String name = (String)it.next();
					if(count == 0) {
						tableBuilder.append(" FROM "+name);
					} else {
						if("fs".equals(module)) {
							tableBuilder.append(" LEFT JOIN "+name+" ON "+name+".LEAD_ID=FS_LEAD_DETAILS.LEAD_ID");
						} else if("fim".equals(module)) {
							if("ADDRESS".equals(name)) {
								addressFlag = true;
								tableBuilder.append(" LEFT JOIN "+name+" ON "+name+".FOREIGN_ID="+idDbColumn);
							} else {
								tableBuilder.append(" LEFT JOIN "+name+" ON "+name+".ENTITY_ID=FRANCHISEE.FRANCHISEE_NO");
							}
						}
					}
					count++;
				}

				if(StringUtil.isValidNew(columnBuilder.toString())) {
					queryBuilder.append(" "+columnBuilder.substring(1));

					if(StringUtil.isValidNew(tableBuilder.toString())) {
						queryBuilder.append(" "+tableBuilder);
					}

					if("fs".equals(module)) {
						queryBuilder.append(" WHERE 1=1 AND FS_LEAD_DETAILS.LEAD_ID=?");
					} else if("fim".equals(module)) {
						queryBuilder.append(" WHERE 1=1 AND FRANCHISEE.FRANCHISEE_NO=?");
						if(addressFlag) {
							if(StringUtil.isValidNew(idFieldValue)) {
								queryBuilder.append(" AND ADDRESS.FOREIGN_ID='"+idFieldValue+"'");
							}
						}
					}
					ResultSet result = QueryUtil.getResult(queryBuilder.toString(), new String[]{primaryId});
					if(result != null) {
						ResultSetMetaData metadata = result.getMetaData();
						int columnCount;
						columnCount = metadata.getColumnCount();
						while(result.next()) {
							for (int i = 1; i <= columnCount; i++) {
								if(metadata.getColumnLabel(i)!=null && metadata.getColumnLabel(i).contains(":")){
									if(metadata.getColumnLabel(i).split(":")[1].equals(result.getString("ADDRESS_ORDER"))){
										syncValueFieldMap.put(metadata.getColumnLabel(i).split(":")[0], result.getString(metadata.getColumnLabel(i)));
									}
								}else{
									syncValueFieldMap.put(metadata.getColumnLabel(i), result.getString(metadata.getColumnLabel(i)));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is used to get the functions called by parent field value,
	 * on which the data gets populated in the dependent field.
	 * BB-20150203-259 (Dynamic Response based on parent field response)
	 * @author Naman Jain
	 * @param fld
	 * @return functionNames
	 */
	public static String getDependentChildFunctions(Field fld) {
		String functionNames = "";
		if(fld != null) {
			SequenceMap dependentChildTotalMap = fld.getDependentChildTotalMap();
			Iterator itr = dependentChildTotalMap.values().iterator();
			while(itr.hasNext()) {
				DependentChild child = (DependentChild)itr.next();
				if(child != null) {
					functionNames = functionNames + child.getFunctionName() + "; ";
				}
			}
		}
		return functionNames;
	}
	
    public static CheckBox getExhibitMaterial(Field fld,Boolean isModify,String value){  
    	CheckBox check0 = new CheckBox(fld.getDisplayName());
    	HashMap arrLst = null;
    	
    	if(value != null) {
			arrLst = new HashMap();
			StringTokenizer nstr = new StringTokenizer(value, ",");
			while(nstr.hasMoreElements()) {
				String str = (String)nstr.nextElement();
				if(StringUtil.isValid(str.trim())) {
					arrLst.put(str.trim(), str.trim());
				}
			}
		}
    	check0.setValue("FDD Receipt", LanguageUtil.getString("FDD Receipt"), (value != null&&arrLst.get("FDD Receipt") == null)?false:true);
    	check0.setValue("Status of Accounts", LanguageUtil.getString("Status of Accounts"),value != null&&arrLst.get("Status of Accounts") == null?false:true);
    	check0.setValue("Territory Map", LanguageUtil.getString("Territory Map"),value != null&&arrLst.get("Territory Map") == null?false:true);
    	check0.setValue("Renewal Fees", LanguageUtil.getString("Renewal Fees"),value != null&&arrLst.get("Renewal Fees") == null?false:true);
    	check0.setValue("Insurance Policy", LanguageUtil.getString("Insurance Policy"),value != null&&arrLst.get("Insurance Policy") == null?false:true);
    	check0.setValue("Renewal Agreement", LanguageUtil.getString("Renewal Agreement"),value != null&&arrLst.get("Renewal Agreement") == null?false:true);
    	check0.setValue("Guaranty", LanguageUtil.getString("Guaranty"),value != null&&arrLst.get("Guaranty") == null?false:true);
    	    
    	return check0;                                       
    }
    
    public static String createRadioHtmlData(String radioType,String radioValue, String type) {
    	return createRadioHtmlData(radioType, radioValue, type, "");
    }
    
    public static String createRadioHtmlData(String radioType,String radioValue, String type, String jsFunction) {
		String divamt = "";	String divpert = ""; String divcomment = ""; String inputfixed = ""; String inputcurrent = ""; String inputpast = ""; String inputcomment = "";
		String fixlbl = "";String currlbl = "";String pstlbl = "";String commentlbl = "";
		if("royalty".equals(type)) {
			divamt = "royalamt";divpert = "currentroyal";divcomment = "commentroyal";inputfixed = "fixed_royalty";
			inputcurrent = "current_royalty";inputpast = "past_royalty";inputcomment = "comment_royalty";
			fixlbl = LanguageUtil.getString("Amount");	currlbl = LanguageUtil.getString("Current Royalty");	pstlbl = LanguageUtil.getString("Past Royalty");commentlbl = LanguageUtil.getString("Comment");
		} else if("advertising".equals(type)) {
			divamt = "advertisingamt";divpert = "currentad";divcomment = "commentad";inputfixed = "fixed_ad";
			inputcurrent = "current_ad";inputpast = "past_ad";inputcomment = "comment_ad";
			fixlbl = LanguageUtil.getString("Amount");	currlbl = LanguageUtil.getString("Current Ad Fee");	pstlbl = LanguageUtil.getString("Past Ad Fee");commentlbl = LanguageUtil.getString("Comment");
		} else {
			divamt = "otheramt";divpert = "currentother";divcomment = "commentother";inputfixed = "fixed_other";
			inputcurrent = "current_other";	inputpast = "past_other";inputcomment = "comment_other";
			fixlbl = LanguageUtil.getString("Amount");	currlbl = LanguageUtil.getString("Current Fee");	pstlbl = LanguageUtil.getString("Past Fee");commentlbl = LanguageUtil.getString("Comment");
		}
        StringBuffer tempRadioButtons = new StringBuffer("<div id=" + divamt +"  ");
        if((radioType != null && radioType.equals("Fixed Fee") && radioValue != null) || (radioType == null)) {
            tempRadioButtons.append(" style=\"visibility:visible; position:absolute\" ><table><tr ><td class=\"theme_text\"> "+fixlbl+" </td><td class=\"theme_text\" >");
            tempRadioButtons.append("<input type=\"text\" name="+inputfixed+" "+jsFunction+" class=\"fTextBox\" size=12 ").append("style=\"visibility:visible\"");
            if(radioValue != null)
                tempRadioButtons.append(" value=\""+radioValue+"\"></td> </tr></table></div> ");
            else
                tempRadioButtons.append(" ></td> </tr></table></div>");
        } else {
            tempRadioButtons.append(" style=\"visibility:hidden; position:absolute \" ><table><tr><td class=\"theme_text\">"+fixlbl+" </td><td class=\"theme_text\" >");
            tempRadioButtons.append("<input type=\"text\" name="+inputfixed+" "+jsFunction+" class=\"fTextBox\" size=12 ").append("style=\"visibility:hidden\"");
            tempRadioButtons.append(" ></td>  </tr></table> </div>");
        }
        tempRadioButtons.append("<div id="+divpert+" ");
        if(radioType != null && radioType.equals("Percentage") && radioValue != null){
            String[] list = radioValue.split(":");
            String firstValue="";
            String secondValue="";
            if(list.length >0)
                firstValue =list[0];
            if(list.length >1)
                secondValue =list[1];
            tempRadioButtons.append("style=\"visibility:visible; position:absolute\" ><table><tr><td class=\"theme_text\" >"+currlbl+"</td><td class=\"theme_text\" >");
            tempRadioButtons.append("<input type=\"text\" name="+inputcurrent+" "+jsFunction+" class=\"fTextBox\" size=12 ").append("style=\"visibility:visible\" value=\""+firstValue+"\" ></td>");
            tempRadioButtons.append("<td class=\"theme_text\" >"+pstlbl+"</td> ").append("<td class=\"theme_text\" ><input type=\"text\" name="+inputpast+" "+jsFunction+" ");
            tempRadioButtons.append("style=\"visibility:visible\" value=\""+secondValue+"\" class=\"fTextBox\" size=12></td></tr></table>  </div>");
        } else  {
            tempRadioButtons.append("style=\"visibility:hidden; position:absolute\" ><table><tr><td class=\"theme_text\" >"+currlbl+"</td><td class=\"theme_text\" >");
            tempRadioButtons.append("<input size=12 type=\"text\" name="+inputcurrent+" "+jsFunction+" class=\"fTextBox\" ").append("style=\"visibility:hidden\" ></td>");
            tempRadioButtons.append("<td class=\"theme_text\" >"+pstlbl+"</td>").append("<td class=\"theme_text\" ><input type=\"text\" name="+inputpast+" "+jsFunction+" class=\"fTextBox\" ");
            tempRadioButtons.append("style=\"visibility:hidden\" size=12></td> </tr></table></div>");
        }
        tempRadioButtons.append("<div id=" + divcomment +" ");
        if(radioType != null && radioType.equals("Other") && radioValue != null) {
            tempRadioButtons.append("style=\"visibility:visible; position:absolute\" ><table><tr><td class=\"theme_text\" >"+commentlbl+"</td><td class=\"theme_text\" ><input type=\"text\" name="+inputcomment+" "+jsFunction+" ");
            tempRadioButtons.append("style=\"visibility:visible\" value=\""+radioValue+"\" size=12 class=\"fTextBox\" > </td></tr>  </table></div>");
        } else  {
            tempRadioButtons.append("style=\"visibility:hidden; position:absolute\" ><table><tr><td class=\"theme_text\" >"+commentlbl+"</td><td class=\"theme_text\" ><input type=\"text\" name="+inputcomment+" "+jsFunction+" ");
            tempRadioButtons.append("style=\"visibility:hidden\" size=12 class=\"fTextBox\" > </td></tr> </tr></table></div> ");
        }
		return tempRadioButtons.toString();
	}
    
    public static String generateCheckBoxHtml(Field fld, String value, String condVal, Boolean isModify){
		String chkCondtFldVal = fld.getCheckboxConditionFieldValue();
		String visibility = "";
		if(isModify && StringUtil.isValid(condVal) && (condVal).equals(chkCondtFldVal)) {
			visibility = "visible";
		} else if(isModify && StringUtil.isValid(condVal) && !(condVal).equals(chkCondtFldVal)) {
			visibility = "none";
		} else {
			visibility = "inline";
		}
		StringBuffer returnString = new StringBuffer("");
		try{
			String subModuleName = "";
			/*if ("Yes".equalsIgnoreCase(MasterDataMgr.newInstance().getMasterDataDAO().getValueByType(MasterEntities.ENABLE_SMARTCONNECT))) {
				subModuleName="franchisee";                   	
			} else {
				subModuleName="franchiseewithoutsc";      
			}*/
			//SmartConnect is now a saperate module , thus submoduleName will always remain "franchiseewithoutsc"
			List<UserTabConfig> userTabConfigList = null;
			subModuleName="franchiseewithoutsc";
			String materialArr[]=null;
			SequenceMap userTabConfigMap1 = UserTabConfigUtil.getTabNames("fim",subModuleName);
			userTabConfigList = (List<UserTabConfig>)userTabConfigMap1.get("fim");

			returnString.append("<div id='copyTransfer' style='display:"+visibility+";'>"); 
			returnString.append("<table width='98%' border='0' cellspacing='1' cellpadding='0' ><tr>");
			returnString.append("<td  width=25.0% class='pqs_que' align='right' valign='center'>"+LanguageUtil.getString(fld.getDisplayName())+"&nbsp;:&nbsp;</td>");
			if (userTabConfigList != null && userTabConfigList.size() > 0) {	
				int count=0;
				for (UserTabConfig userTabConfig : userTabConfigList) {		
					
					if(ModuleUtil.auditImplemented() && false && "QA History".equalsIgnoreCase(userTabConfig.getName().trim()))//P_PW_ENH_FIM_EXPORT
						continue;
					
					if(!userTabConfig.getName().equalsIgnoreCase("SmartConnect") && !userTabConfig.getName().equalsIgnoreCase("Center Info") && !userTabConfig.getName().equalsIgnoreCase("Owners") && !userTabConfig.getName().equalsIgnoreCase("Contact History") && !userTabConfig.getName().equalsIgnoreCase("Users") && !userTabConfig.getName().equalsIgnoreCase("Pictures") && !userTabConfig.getName().equalsIgnoreCase("Previous Franchisees") && !userTabConfig.getName().equalsIgnoreCase("Transfer"))
					{
						count++;
					}
				}
				materialArr = new String[count];
				int i = 0;
				for (UserTabConfig userTabConfig : userTabConfigList) 
				{		
					if(ModuleUtil.auditImplemented() && false && "QA History".equalsIgnoreCase(userTabConfig.getName().trim()))//P_PW_ENH_FIM_EXPORT
						continue;
					
					if(!userTabConfig.getName().equalsIgnoreCase("SmartConnect") && !userTabConfig.getName().equalsIgnoreCase("Center Info") && !userTabConfig.getName().equalsIgnoreCase("Owners") && !userTabConfig.getName().equalsIgnoreCase("Contact History") && !userTabConfig.getName().equalsIgnoreCase("Users") && !userTabConfig.getName().equalsIgnoreCase("Pictures") && !userTabConfig.getName().equalsIgnoreCase("Previous Franchisees") && !userTabConfig.getName().equalsIgnoreCase("Transfer"))
					{
						materialArr[i]=userTabConfig.getName();
						i++;
						isData=true;
					}
				}
			}
			if(materialArr!=null &&materialArr.length==0) {
				isData=false;
			}
			int len = 0;
			if(materialArr!=null)
				len = materialArr.length;
			if(len > 0)
			if(value.equals("")){
				returnString.append("<td width=100% class='pqs_que' valign='center' colspan='3'><input type='checkbox' name='selectAll' id='selectAll' value = '"+len+"' onclick='javascript:checkUncheckAll(this);'>"+LanguageUtil.getString("Select All")+"</td>");
				for (int i=0; i < len; i++){
					if (i%3 == 0 )
						returnString.append("</tr><tr><td  width=25.0% class='pqs_que' align='right' valign='center'></td>");
					if(LanguageUtil.getString("Email Summary",(String)request.getSession().getAttribute("userLanguage")).equals(materialArr[i]))
						continue;
					returnString.append("<td width=25.0% class='pqs_que' valign='center'><input type='checkbox' name='"+fld.getFieldName()+"' id='"+i+"' value = '" +materialArr[i]+"' onclick='javascript:deSelectAll();'>"+LanguageUtil.getString(materialArr[i],(String)request.getSession().getAttribute("userLanguage"))+"</td>");
				}
				returnString.append("</tr>");
			}else{
				returnString.append("<td width=100% class='pqs_que' valign='center' colspan='3'><input type='checkbox' name='selectAll' id='selectAll' value = '"+len+"' onclick='javascript:checkUncheckAll(this);'>"+LanguageUtil.getString("Select All")+"</td>");
				for (int i=0; i < len; i++){
					if (i%3 == 0 )
						returnString.append("</tr><tr><td width=23.0% class='pqs_que' align='right' valign='center'></td>");
					if (value.indexOf(materialArr[i]) != -1)
					{
						if("Email Summary".equals(materialArr[i]))
							continue;
						returnString.append("<td width=25.0% class='pqs_que'><input type='checkbox' name='"+fld.getFieldName()+"' value = '" +materialArr[i]+"' id='"+i+"' checked onclick='javascript:deSelectAll();'>"+materialArr[i]+"</td>");
					}else
					{
						if("Email Summary".equals(materialArr[i]))
							continue;
						returnString.append("<td width=25.0% class='pqs_que'><input type='checkbox' name='"+fld.getFieldName()+"' value = '" +materialArr[i]+"' id='"+i+"' onclick='javascript:deSelectAll();'>"+materialArr[i]+"</td>");
					}
				}
				returnString.append("</tr>");
			}
			returnString.append("</table></div>");
		}catch(Exception E){
			E.printStackTrace();
		}
		return returnString.toString();
    }
    
    public static String getDateFieldHtmlData(Field fld, String value, Boolean finPresent){
    	if(fld.getFieldName().equals("reportPeriodStartDate")) {
    		StringBuffer  returnString=new StringBuffer();
    		returnString.append("<div id='copyTransfer2' style='display:inline;'><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>");
    		if(finPresent)
    			returnString.append("<td width=25.0% class='bText12b' valign='center' align='right'>"+LanguageUtil.getString(fld.getDisplayName(),(String)request.getSession().getAttribute("userLanguage"))+"&nbsp;:&nbsp;</td>");
    		else
    			returnString.append("<td width=25.0% class='bText12b' valign='center' align='right'>"+LanguageUtil.getString(fld.getDisplayName(),(String)request.getSession().getAttribute("userLanguage"))+"&nbsp;:&nbsp;</td>");

    		if(StringUtil.isValid(value)) {
    			returnString.append("<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tbody><tr><td><input type=text name="+fld.getFieldName()+" id="+fld.getFieldName()+" class='fTextBox' onfocus=\"onfocusCalendar(this,form.name)\" onBlur=dateUtility(this,form.name) value='"+value+"'>");
    		} else {
    			returnString.append("<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tbody><tr><td><input type=text name="+fld.getFieldName()+" id="+fld.getFieldName()+" class='fTextBox' onfocus=\"onfocusCalendar(this,form.name)\" onBlur=dateUtility(this,form.name) value=''>");
    		}
    		
    		returnString.append("</td><td>&nbsp;<input type=\"hidden\" name="+FieldNames.TERMINATION_REASON+" value=\"483\"></td><td width=\"100%\">");//P_FIM_B_64515
    		returnString.append("<a tabIndex=\"-1\" href='javascript:"+FieldNames.REPORT_PERIOD_START_DATE+".popup();'>");
    		returnString.append("<img src='"+ request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/calendar1.gif' width='34' height='21' border='0' valign='absmiddle'>");
    		returnString.append("</a></td></tr></tbody></table></td>");
    		returnString.append("</tr></table></div>");
    		return returnString.toString();
    	} else {
    		return "";
    	}
    }
    
    /*public static String getTextFieldData(Field fld, String value){
    		String val = ""; 
    		if(fld.getFieldName().equals("seller")) {
    			//val = (String)((Info)LocationMgr.newInstance().getLocationsDAO().getOwnerMap().get(value)).getString(FieldNames.OWNER_NAME);
    		} else if(fld.getFieldName().equals("shareholder")) {
    			SequenceMap guarantorMap = CommonMgr.newInstance().getCommonFimDAO().getGuarantorData(value);
    			Iterator guarantorIt = guarantorMap.values().iterator();
    			Info ginfo = new Info();
    			while(guarantorIt.hasNext()){
    				ginfo = null;
    				ginfo = (Info)guarantorIt.next();
    				if (val!=null && !val.trim().equals(""))
    					val = val + "; " + ginfo.getString("GUARANTOR_NAME");
    				else 
    					val = ginfo.getString("GUARANTOR_NAME");
    			}
    		} else {
    			val = "";
    		}
    		
    		return val;
    }*/
    //P_CM_Enh_BuilderForm starts
    public static Combo getComboFromSrc(Field fld,Boolean isModify) {
 		try{
 			String srcTable = fld.getSrcTable();
 			String srcField = fld.getSrcField();
 			String srcValue = fld.getSrcValue();
 			FieldMappings fldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(srcTable);
 			String table = fldMapping.getTableName();
 			String idField = fldMapping.getDbField(srcField);
 			String valueField = fldMapping.getDbField(srcValue);
 			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(PortalUtils.getIdValueInfo(table, idField, valueField));
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getComboFromSrc method::"+e);
 			return null;
 		}
 	}
  //KSZCB-20150317-003 Start
    public static Combo getStatusComboFromSrc(Field fld,Boolean isModify) {
 		try{
 			String srcTable = fld.getSrcTable();
 			String srcField = fld.getSrcField();
 			String srcValue = fld.getSrcValue();
 			FieldMappings fldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(srcTable);
 			String table = fldMapping.getTableName();
 			String idField = fldMapping.getDbField(srcField);
 			String valueField = fldMapping.getDbField(srcValue);
 			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(PortalUtils.getIdValueInfo(table, idField, valueField, "SORT_ALPHA", "Y","N", "CM_LEAD_STATUS_NAME", "CM_ORDER_NO"));
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getComboFromSrc method::"+e);
 			return null;
 		}
 	}
   //KSZCB-20150317-003 End
    
    public static Combo getLeadStatusComboFromSrc(Field fld,Boolean isModify) {
 		try{
 			String table = "CM_LEAD_STATUS_NEW";
 			String idField = "CM_LEAD_STATUS_ID";
 			String valueField ="CM_LEAD_STATUS_NAME";
 			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(PortalUtils.getIdValueInfo(table, idField, valueField, "SORT_ALPHA", "Y","N", "CM_LEAD_STATUS_NAME", "CM_ORDER_NO"));
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getComboFromSrc method::"+e);
 			return null;
 		}
 	}
    
    
    /*public static Combo getComboForContactType(Field fld,Boolean isModify)
    {
    	Combo combo = new Combo(fld.getDisplayName());
    	if(!"0".equals(MultiTenancyUtil.getTenantConstants().getContactTypeDisplay())) {
				combo.setInfo(CommonMgr.newInstance().getCommonCmDAO().getContactTypeInfo());
			} else {
				combo.setInfo(CommonMgr.newInstance().getCommonCmDAO().getDefaultContactTypeInfoCM(null));	
			}
    	return combo;
    }*/
    
    /**
	 * get info for add lead page
	 */
	/*public static Combo getLeadTypeInfo(Field fld,Boolean isModify) {
		try{
 			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(CommonMgr.newInstance().getCommonCmDAO().getLeadTypeInfo());
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getLeadTypeInfo::"+e );
 			return null;
 		}
		
	}
	public static Combo getBusinessTypeInfo(Field fld,Boolean isModify) {
		try{
 			Combo combo = new Combo(fld.getDisplayName());
 			String sortType=NewPortalUtils.getColumnFromTable("CM_BUSINESS_TYPE", "SORT_ALPHA", "1", "1");
 			String orderBy="ORDER_NO";
 			 if("Y".equals(sortType)){
 		         orderBy=("BUSINESS_TYPE_NAME");
 		        }
 			Info businessInfo=SQLUtil.getColumnValueInfo("CM_BUSINESS_TYPE", "BUSINESS_TYPE_ID", "BUSINESS_TYPE_NAME", "1","1",orderBy);
 			combo.setInfo(businessInfo);
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getBusinessTypeInfo::"+e );
 			return null;
 		}
		
	}*/
	
	//ENH_PW_SMART_QUESTIONS_STARTS
	/**
     * This function will return Type of Owner Ship Combo.
     * @return Combo
     * @author rohit jain
     */
	public static Combo getTypeOwnerShip(Field fld,Boolean isModify) {
		try{
			Info info1 = new Info();
			Combo combo = new Combo("Type of Ownership");
			info1.set("Absentee Owner",LanguageUtil.getString("Absentee Owner"));
			info1.set("Owner/Operator",LanguageUtil.getString("Owner/Operator"));
			combo.setInfo(info1);
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getBusinessTypeInfo::"+e );
 			return null;
 		}
		
	}
	/**
     * This function will return all agreement versions.
     * @return Combo
     * @author rohit jain
     */
	public static Combo getAllAgreement(Field fld,Boolean isModify){

		         try{
						StringBuffer query	= new StringBuffer("SELECT F.AGREEMENT_ID, F.AGREEMENT_NAME  FROM FIN_AGREEMENTS F WHERE F.IS_DELETED = 'N' ORDER BY AGREEMENT_NAME ASC ");
						Info agreementInfo =SQLUtil.getInfoFromQuery("AGREEMENT_ID", "AGREEMENT_NAME", query.toString());
						Combo combo = new Combo("Agreement Version");
						combo.setInfo(agreementInfo);
						return combo;
			 		} catch(Exception e){
			 			logger.error("Exception in getAllAgreement::"+e );
			 			return null;
			 		}
		
	}
	
	/**
     * This function will return enabled/disabled Radio for EFT.
     * @return Radio
     * @author rohit jain
     */
	public static Radio getEFTRadio(Field fld,Boolean isModify)
	{
		Radio radio0 = new Radio("Enable/Disable For EFT");
		radio0.setOption("Y", "Yes");
		radio0.setOption("N", "No");
		return radio0;
	}
	
	/**
     * This function will return Status Combo of Visits.
     * @return Combo
     * @author rohit jain
     */
    public static Combo getStatusComboForVisit(Field fld,Boolean isModify)
	 {
		Info comboInfo = SQLUtil.getColumnValueInfo("MASTER_DATA","PARENT_DATA_ID","DATA_VALUE","DATA_TYPE","130311");
	    Combo combo = new Combo("Status");
		combo.setInfo(comboInfo);
		return combo;
	 }
	
	//ENH_PW_SMART_QUESTIONS_ENDS
	
	
	public static Combo getSubStatusInfo(Field fld,String pField, String franchiseeNo, String userLevel,Boolean isModify) {
		try{
 			
			StringBuffer query	= new StringBuffer("SELECT CLS.CM_LEAD_SUB_STATUS_ID,CM_LEAD_SUB_STATUS_NAME FROM CM_LEAD_SUB_STATUS CLS LEFT JOIN CM_SUB_STATUS_SORT_ID CSSD ON (CLS.CM_LEAD_SUB_STATUS_ID=CSSD.CM_LEAD_SUB_STATUS_ID) ");
	        query.append(" WHERE CLS.CM_LEAD_STATUS_ID = '").append(pField).append("'");
			if(StringUtil.isValidNew(franchiseeNo))
			{
				query.append(" AND CLS.FRANCHISEE_NO = '").append(franchiseeNo).append("'");	
			}
			query.append(" ORDER BY CASE CLS.SORT_ALPHA WHEN 'Y' THEN CLS.CM_LEAD_SUB_STATUS_NAME END , CASE CLS.SORT_ALPHA WHEN 'N' THEN CSSD.ORDER_NO END");//KSZCB-20150317-003
			Info sub_Status_Info =SQLUtil.getInfoFromQuery("CM_LEAD_SUB_STATUS_ID", "CM_LEAD_SUB_STATUS_NAME", query.toString());
			Combo combo = new Combo(fld.getDisplayName());
			combo.setInfo(sub_Status_Info);
			return combo;
 		} catch(Exception e){
 			logger.error("Exception in getSubStatusInfo::"+e );
 			return null;
 		}
		
	}
	/*public static Combo getSourceCombo(Field fld,String franchiseeNo,Boolean isModify){
		try{
 			Combo combo = new Combo(fld.getDisplayName());
 			String fieldName=fld.getFieldName();
 			Info source_Info=null;
			if(FieldNames.CM_SOURCE_1_ID.equals(fieldName))
			{
				String query="SELECT  A.CM_SOURCE_1_ID, A.CM_SOURCE_1_NAME FROM CM_SOURCE_1 A GROUP BY A.CM_SOURCE_1_ID ORDER BY A.ORDER_NO";
				source_Info =  SQLUtil.getInfoFromQuery("CM_SOURCE_1_ID", "CM_SOURCE_1_NAME", query);
			}
			else if(FieldNames.CM_SOURCE_2_ID.equals(fieldName))
	 			source_Info =  CommonMgr.newInstance().getCommonCmDAO().getSourceInfo(null, franchiseeNo , null);
				
			combo.setInfo(source_Info);
			return combo;
		} catch(Exception e){
 			logger.error("Exception in getSource1Combo::"+e );
 			return null;
 		}
		
	}*/
	public static Combo getSMSCampaignCombo(Field fld,HttpServletRequest request,Boolean isModify)
	{
		
		HttpSession ses = request.getSession();
		String userLevel=(String)ses.getAttribute("user_level");            
        StringBuffer comboQuery=new StringBuffer("SELECT SMS_CAMPAIGN_ID,SMS_CAMPAIGN_TITLE FROM CM_SMS_CAMPAIGN WHERE ");
        String selectedSmsCampaign=(String)request.getAttribute("selectedSmsCampaign");
        String franchiseOwner[]= new String[2];
        String params[]=new String[2];
        boolean fromViewAll="All".equals((String)ses.getAttribute("franchisee_all"));//P_CM_B_23006,P_CM_B_24954
        String regionId=null;
        String user_no				= (String)ses.getAttribute("user_no");
        String from = request.getParameter("from");
        if(fromViewAll && "1".equals(userLevel))//P_CM_B_23006,P_CM_B_24954
        {
        	franchiseOwner=CommonUtil.getFranchiseeAndOwner(request);
        	params[0]=franchiseOwner[0];
        	params[1]=franchiseOwner[1];
        	regionId=CommonUtil.getRegionList(params[1]);
        	if("quickAdd".equals(from) && StringUtil.isValidNew(request.getParameter("franchiseeNo")))
            {
        		params[0]=request.getParameter("franchiseeNo");
            }
        }
        else
        {
        	params[0]=(String)ses.getAttribute("franchisee_no");
        	params[1]=user_no;
        	regionId=CommonUtil.getRegionList(params[1]);
        }
		
		if(userLevel!=null && ("1".equals(userLevel)))
          {
				String [] stringParam={" SMS_CAMPAIGN_ACCESSIBILITY "};
				String [] stringValues={"'Global' OR (SMS_CAMPAIGN_ACCESSIBILITY='Public' AND ((FRANCHISEE_NO IN ("+params[0]+",-1) AND SMS_CAMPAIGN_ADDED_BY IN ("+params[1]+")) OR (REGION_NO IN("+regionId+") AND FRANCHISEE_NO IN ("+params[0]+",-1,0))))   OR  SMS_CAMPAIGN_ID ='"+selectedSmsCampaign+"' OR (CASE SMS_CAMPAIGN_ACCESSIBILITY WHEN 'Private' THEN (SMS_CAMPAIGN_ADDED_BY IN("+params[1]+") AND FRANCHISEE_NO IN ("+params[0]+",-1)) END) "};
				comboQuery.append(stringParam[0]+"="+stringValues[0]);

				
          }
          if(userLevel!=null && ("2".equals(userLevel)))
          {
              String [] stringParam={"SMS_CAMPAIGN_ACCESSIBILITY"," FRANCHISEE_NO"};
              String [] stringValues={" 'Global' OR (SMS_CAMPAIGN_ACCESSIBILITY='Public' AND REGION_NO  ='"+regionId+"' and FRANCHISEE_NO=0)   OR  SMS_CAMPAIGN_ID ='"+selectedSmsCampaign+"'  OR (CASE SMS_CAMPAIGN_ACCESSIBILITY WHEN 'Private' THEN SMS_CAMPAIGN_ADDED_BY="+user_no+" END)"," 0 AND REGION_NO IN ("+regionId+",0)"};
              comboQuery.append(stringParam[0]+"="+stringValues[0]+" AND "+stringParam[1]+"="+stringValues[1]);
              
          }
          else if(userLevel!=null && ("0".equals(userLevel)))
          {
              String [] stringParam={" SMS_CAMPAIGN_ACCESSIBILITY"," REGION_NO" , "FRANCHISEE_NO"};
              String [] stringValues={" 'Global' OR (SMS_CAMPAIGN_ACCESSIBILITY='Public' AND REGION_NO=0 AND FRANCHISEE_NO=0) OR (CASE SMS_CAMPAIGN_ACCESSIBILITY WHEN 'Private' THEN SMS_CAMPAIGN_ADDED_BY="+user_no+" END)  OR  SMS_CAMPAIGN_ID ='"+selectedSmsCampaign+"'","0","0"};
              comboQuery.append(stringParam[0]+"="+stringValues[0]+" AND "+stringParam[1]+"="+stringValues[1] +" AND "+stringParam[2]+"="+stringValues[2]);
             
          }//P_SMS_Campaign_Enh by Ravi
          
          //himanshi ends
    	ResultSet result=null;
		Info comboInfo	= new Info();
		try
		{
			result = QueryUtil.getResult(comboQuery.toString(),null);
			comboInfo.set("-1","Select SMS Campaign");
			while (result.next())
			{
				comboInfo.set(result.getString("SMS_CAMPAIGN_ID"), LanguageUtil.getString(result.getString("SMS_CAMPAIGN_TITLE")));//// BUG-1417
			}
			comboInfo.set("Select","");
		} catch(Exception e)
		{
			logger.error("\nException in DataCollector.java --> Exception in getSMSCampaignCombo()" , e);
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(comboInfo);
		combo.setComboValue(selectedSmsCampaign);
		return combo;
	}
	//P_CM_Enh_BuilderForm ends	
	
	//P_SCH_ENH_008 Starts
	public static Combo getJobTypeCombo(Field fld,Boolean flag) {
		Combo jobTypeCombo = new Combo();
		ResultSet rs= null;
		try{
			Info jobCategoryInfo = new Info();

			StringBuilder query = new StringBuilder("SELECT JC.JOB_CATEGORY_NAME,JC.JOB_CATEGORY_ID  FROM JOB_CATEGORIES JC ");
			query.append("ORDER BY JOB_ORDER_NO ");
			rs = QueryUtil.getResult(query.toString(), null);

			while (rs.next()) {
				jobCategoryInfo.set(rs.getString("JOB_CATEGORY_ID"), rs.getString("JOB_CATEGORY_NAME"));
			}
			jobTypeCombo.setDisplayName(fld.getDisplayName());
			jobTypeCombo.setInfo(jobCategoryInfo);
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			QueryUtil.releaseResultSet(rs);
		}
		return jobTypeCombo;
	}
    //P_SCH_ENH_008 Ends
	//BBP-20140530-255 starts
	public static Combo comboFSCountry(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
//		SequenceMap countryMap = com.home.builderforms.CacheDataUtil.getActiveCountryMap();
		//SequenceMap countryMap = PortalUtils.getCountries();
		SequenceMap countryMap = new SequenceMap();
//		comboInfo.setFields(new SequenceMap(countryMap.getHashMap()));
		comboInfo.setFields((SequenceMap)countryMap.cloneInfo());
		combo.setInfo(comboInfo);
		comboInfo.insert(0,"Select", "");
		
		//BB-20150203-259 (Dynamic Response based on parent field response) starts
		String functionName = "";
		String dependentChildFunction = getDependentChildFunctions(fld);
		String jScriptFunctions = "";
		
		if(StringUtil.isValid(fld.getDependentField())) {
			functionName = "getCombo('state',this.value,'"+fld.getDependentField()+"', '-1'); hideShowCheckBox(this.value); showCountyCombo(this.value);";
		}
		
		if(StringUtil.isValidNew(functionName)) {
			jScriptFunctions = jScriptFunctions + functionName;
		}
		
		if(StringUtil.isValidNew(dependentChildFunction)) {
			jScriptFunctions = jScriptFunctions + dependentChildFunction;
		}
		
		if(StringUtil.isValidNew(jScriptFunctions)) {
			combo.setJScript("onchange=\""+jScriptFunctions+"\"");
		}
		//BB-20150203-259 (Dynamic Response based on parent field response) ends
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select Country"));
		if(!isModify)
			combo.setComboValue("1");
		return combo;
	}
	public static Combo comboCMCountry(Field fld,Boolean isModify)
	{
		Combo combo = new Combo(fld.getDisplayName());
		Info comboInfo = new Info();
//		SequenceMap countryMap = com.home.builderforms.CacheDataUtil.getActiveCountryMap();
		//SequenceMap countryMap = PortalUtils.getCountries();
				SequenceMap countryMap = new SequenceMap();
//		comboInfo.setFields(new SequenceMap(countryMap.getHashMap()));
		comboInfo.setFields((SequenceMap)countryMap.cloneInfo());
		combo.setInfo(comboInfo);
		comboInfo.insert(0,"Select", "");
		if(StringUtil.isValid(fld.getDependentField()))
			combo.setJScript("onchange=\"getCombo('state',this.value,'"+fld.getDependentField()+"', '-1');hideShowCheckBox(this.value);\"");
		comboInfo.insert(1,"-1", LanguageUtil.getString("Select Country"));
		if(!isModify)
			combo.setComboValue("1");
		return combo;
	}//BBP-20140530-255 ends
	
	//BOEFLY_INTEGRATION : START
    /*public static String generateBoeflyUrl(String urlMailMerge,String leadId)
	{		  BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		StringBuffer returnVal = new StringBuffer();
		if("on".equals(_baseConstants.BOEFLY_INTEGRATION_STATUS))
		{
			returnVal.append(_baseConstants.BOEFLY_URL).append("&fcid=").append(leadId);
		}
		return returnVal.toString();
	}*/
    //BOEFLY_INTEGRATION : END
    
    /**
     * This function is used to display the Site Type for creating any site.
     * P_Enh_Site_Clearance
     */
    public static Radio getSiteTypeLocationsCombo(Field field, Boolean isModify) {
    	Radio radio = new Radio(field.getDisplayName());
    	if(!isModify) {
    		radio.setOption(LanguageUtil.getString("New Available"),"N",true);
    	} else {
    		radio.setOption(LanguageUtil.getString("New Available"),"N");
    	}
		radio.setOption(LanguageUtil.getString("Resale"),"R");
		radio.setOption(LanguageUtil.getString("Existing"),"E");
		return radio;
    }
    
    
     // Added By Amit Tanwani CRM_OPPORTUNITY_INFO Starts
    
    public static Combo getCMMasterDataCombo(Field fld,Boolean isModify) {

    	String dataType 	= fld.getComboSourceMethodArgs();
    	boolean multiple = false;
    	if(dataType.indexOf(",multiple")!=-1) {
    		dataType = dataType.replace(",multiple", "");
    		multiple = true;
    	}
    	Info info = null;
    	StringBuffer query = new StringBuffer("SELECT * FROM CM_MASTER_DATA");
    	query.append(" WHERE 1=1");

    	if(StringUtil.isValid(dataType.toString())){
    		query.append(" AND DATA_TYPE= " + dataType);
    	}

    	query.append(" ORDER BY CM_MASTER_DATA_ID ");

    	ResultSet result = QueryUtil.getResult(query.toString(), new Object[]{});
    	try{
    		info = new Info();
    		while (result.next()) {
    			info.set(result.getString("CM_MASTER_DATA_ID"), result.getString("DATA_VALUE"));
    		}
    	}catch(Exception e){
    		logger.error("\nCmLeadUtil.getCMMasterDataInfo()\t", e);
    	}finally{
    		QueryUtil.releaseResultSet(result);
    	}

    	Combo combo = new Combo(fld.getDisplayName());
    	if(multiple) {
    		combo.setMultiple();
    		combo.setSize("5");
    		combo.setJScript(" style = 'height:85px;' ");
    	}
    	combo.setInfo(info);
    	return combo;
    }
    
    public static Combo getOpportunityTypeCombo(Field fld,Boolean isModify)
    {	
    	Info opportunityTypeInfo=new Info();
    	Combo combo = new Combo(fld.getDisplayName());
		combo.setInfo(opportunityTypeInfo);
		return combo;
    }
    
    public static Radio getAccountTypeRadio(Field fld,Boolean isModify){
    	
    	Radio radio = new Radio(fld.getDisplayName());
    	if(StringUtil.isValidWithZero(request.getParameter(FieldNames.PARENT_ACCOUNT_ID))){
    		radio.setOption(LanguageUtil.getString("B2B"),"Y",true,true);
        	radio.setOption(LanguageUtil.getString("B2C"),"N",false,true);	
    	}else{
    		radio.setOption(LanguageUtil.getString("B2B"),"Y",true);
    		radio.setOption(LanguageUtil.getString("B2C"),"N",false);
    	}
    	
    	if(!("yes".equals(request.getParameter("fromPreview")) || "yes".equals(request.getParameter("fromSectionPreview")))){
    		radio.setJScript("onClick=\"onClickAccountType()\"");
    	}
    	
    	if(StringUtil.isValid(request.getParameter(FieldNames.CONTACT_ID))){
    		radio.setValue("N");
    	}
    	
    	return radio;        
    }

    public static CheckBox getIsParentCheckBox(Field fld,Boolean isModify,String param) {

    	CheckBox checkbox = new CheckBox(fld.getDisplayName());

    	if(StringUtil.isValidWithZero(request.getParameter(FieldNames.PARENT_ACCOUNT_ID))){
    		checkbox.setValue("", "N", true,true);
    	}else{
    		if(isModify){
    			if("N".equals(param)){	
    				checkbox.setValue("", param, true);
    			}else{
    				checkbox.setValue("", param, false);
    			}
    		}else{
    			checkbox.setValue("","Y", false);
    		}
    	}

    	if(!("yes".equals(request.getParameter("fromPreview")) || "yes".equals(request.getParameter("fromSectionPreview")))){
    		checkbox.setJScript("onclick = 'javascript:enableDisableParentAccount();'");
    	}
    	return checkbox;
    }
    // Added By Amit Tanwani CRM_OPPORTUNITY_INFO Ends
    //P_Enh_ContactHistory_FormBuilder starts
  	public static Combo getCallStatus(Field field, Boolean isModify)
  	{

  		Combo combo = new Combo(field.getDisplayName());
  		/*combo.setInfo(AdminMgr.newInstance().getCallDAO().getCallStatus());
  		if(!isModify){
  			combo.setComboValue("0");
  		}*/
  		return combo;
  	}
  	public static Combo getCallType(Field field,Boolean isModify)
  	{

  		Combo combo = new Combo(field.getDisplayName());
  		/*combo.setInfo(AdminMgr.newInstance().getCallDAO().getCallTypes());
  		if(!isModify){
  			combo.setComboValue("7");
  		}*/
  		return combo;
  	}
  	public static void handleFsLeadCallTimeAdded(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify)throws Exception
  	{
  		Info combInfo = new Info();
  		for (int i = 1; i <= 12; i++) {
  			combInfo.set(i + "", i + "");
  		}

  		Info combInfoMin = new Info();
  		combInfoMin.set("00", "00 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("15", "15 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("30", "30 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("45", "45 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		int sTime = 0;
  		int apm = 0;
  		StringBuffer sBuff = new StringBuffer(
  		"<select name=\"sTime\" id=\"sTime\" class=\"multiList\">");
  		String userTimeZone = UserTimezoneMap.newInstance()
  		.getTimezone(
  				(String) (request.getSession()
  						.getAttribute("user_no")));
  		String curr = TimeZoneUtils.getCurrentTime(userTimeZone,
  		"yyyy-MM-dd HH:mm:ss");
  		Iterator keyInfo = combInfo.getKeySetIterator();
  		String key = null;
  		String val = null;
  		int time;
  		String timeAdedd1="";

  		while (keyInfo.hasNext()) {
  			key = (String) keyInfo.next();
  			val = (String) combInfo.getObject(key);
  			time = Integer.parseInt(val);

  			if (totalInfo != null && StringUtil.isValidNew(totalInfo.getString(field.getFieldName())) && isModify) {
  				String timeAdedd = totalInfo.getString(field.getFieldName());
  				String callDate =null;
  				if(StringUtil.isValid(totalInfo.get(FieldNames.CALL_DATE))){
  					callDate=totalInfo.get(FieldNames.CALL_DATE)+ " "+ totalInfo.get(field.getFieldName());
  				}else{
  					callDate=totalInfo.get(FieldNames.DATE)+ " "+ totalInfo.get(field.getFieldName());
  				}
  				String startDatetime="";
  				startDatetime = TimeZoneUtils.performUTCConversion(
                                      Constants.DB_TIMEZONE_TIMEZONEUTILS,userTimeZone,
                                      DateTime.getRequiredFormat(callDate, Constants.DISPLAY_FORMAT_HMS, "yyyy-MM-dd HH:mm:ss"),
                                      TimeZoneUtils.DB_DATETIME,
                                      TimeZoneUtils.DB_DATETIME );
  				timeAdedd = startDatetime.substring(11);
  				timeAdedd1 = timeAdedd;
  				if (timeAdedd != null && timeAdedd.length() > 0) {
  					sTime = Integer.parseInt(timeAdedd.substring(0, 2));

  					if (sTime == 0) {
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12) {
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12) {
  						apm = 1;
  						time += 12;
  					}
  				}
  			} else {
  				if (curr != null && curr.length() > 0) {
  					sTime = Integer.parseInt(curr.substring(11, 13));

  					if (sTime == 0) {
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12) {
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12) {
  						apm = 1;
  						time += 12;
  					}
  				}
  			}// end else
  			if (totalInfo!=null && totalInfo.get(field.getFieldName())!=null && time==sTime && isModify) {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" SELECTED>").append(val).append("</option>");
  			} else if (time == sTime) {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" SELECTED>").append(val).append("</option>");
  			} else {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("&nbsp;:&nbsp;");
  		sBuff
  		.append("<select name=\"sMinute\" id=\"sMinute\" class=\"multiList\">");
  		Iterator itInfo = combInfoMin.getKeySetIterator();
  		String sCurrenMinute = "00";
  		int cMinute = 0;

  		if (curr != null && curr.length() > 0) {
  			sCurrenMinute = curr.substring(14, 16);
  		}

  		if(totalInfo != null && StringUtil.isValidNew(totalInfo.getString(field.getFieldName())) && isModify){
  			cMinute=Integer.parseInt(timeAdedd1.substring(3,5));

  			if (cMinute<15)
  			{
  			   sCurrenMinute="00";
  			} else if (cMinute<30)
  			{
  			   sCurrenMinute="15";
  			} else if (cMinute<45)
  			{
  			   sCurrenMinute="30";
  			} else if (cMinute<=59)
  			{
  			   sCurrenMinute="45";
  			}	
  		}else  {
  			cMinute = Integer.parseInt(curr.substring(14, 16));

  			if (cMinute < 15)
  				sCurrenMinute = "00";
  			else if (cMinute < 30)
  				sCurrenMinute = "15";
  			else if (cMinute < 45)
  				sCurrenMinute = "30";
  			else if (cMinute < 59)
  				sCurrenMinute = "45";
  		}


  		key = null;
  		val = null;

  		while (itInfo.hasNext()) {
  			key = (String) itInfo.next();
  			val = (String) combInfoMin.getObject(key);

  			if (totalInfo!=null && totalInfo.get(field.getFieldName())!=null && key.equals(sCurrenMinute) && isModify) {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" SELECTED>").append(val).append("</option>");
  			} else if (key.equals(sCurrenMinute)) {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" SELECTED>").append(val).append("</option>");
  			} else {
  				sBuff.append("<option value = \" ").append(key).append(
  				"\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("<select name=\"APM\" id=\"APM\" class=\"multiList\">");
  		sBuff.append("<option value = \" ").append("0").append("\" ")
  		.append(apm == 0 ? " selected " : "").append(">")
  		.append(LanguageUtil.getString("AM")).append(" ").append("</option>");
  		sBuff.append("<option value = \" ").append("1").append("\" ")
  		.append(apm == 1 ? " selected " : "").append(">")
  		.append(LanguageUtil.getString("PM")).append(" ").append("</option>");
  		sBuff.append("</select>");
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", sBuff.toString());

  	}
  	public static void getAssignedToComboForTask(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,String contactId,String franchiseeNo,String areaID,int ownerCount )throws Exception
  	{
  		/*boolean bModify = false;
  		TaskMgr taskMgr = TaskMgr.newInstance();
          String taskID = request.getParameter("taskID");
          String foreignID = request.getParameter(FieldNames.FOREIGN_ID);
          String moduleId = request.getParameter("moduleID");
          String divisionIds = (String)request.getSession().getAttribute("divisionIds");
          String scheduleTime = null;
          String userID = (String) request.getSession().getAttribute("user_no");
          String userLevel = (String) request.getSession().getAttribute("user_level");
          boolean fromViewAll = "All".equals((String)request.getSession().getAttribute("franchisee_all"));
          BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
          UserRoleMap CMUserRoleMap = (UserRoleMap) request.getSession().getAttribute("userRoleMap");
  		boolean contactCorporatePriveleges = false;
  		boolean contactFranchiseePriveleges = false;
  		boolean contactRegionalPriveleges=false;
  		StringBuilder htmlContent = new StringBuilder();
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		String taskForeignType=(String)request.getAttribute("taskForeignType");
  		if (userLevel != null && userLevel.equals("1"))
  		{
  			contactFranchiseePriveleges = CMUserRoleMap.isPrivilegeInMap("/cmFranchiseeView");
  		} else if (userLevel != null && userLevel.equals("0"))
  		{
  			contactCorporatePriveleges = CMUserRoleMap.isPrivilegeInMap("/cmCorporateView");
  		}else if(userLevel != null && userLevel.equals("2"))
  		{
  			contactRegionalPriveleges=CMUserRoleMap.isPrivilegeInMap("/cmRegionalView");
  		}
          Info infoDetails = null;
          if (taskID != null && !taskID.equals(""))
  		{
  			infoDetails = taskMgr.getTaskDAO().getTaskDetails(taskID, userID);
  			if (infoDetails != null)
  			{
  				bModify = true;
  				moduleId = infoDetails.getString(FieldNames.MODULE_ID);
  				foreignID = infoDetails.getString(FieldNames.FOREIGN_ID);
  				scheduleTime = infoDetails.getString(FieldNames.SCHEDULE_TIME);
  			}
  		}
          StringBuffer sBuffCombo = new StringBuffer("<select id=\"" + FieldNames.ASSIGN_TO + "\" name=\"" + FieldNames.ASSIGN_TO + "\" class=\"form-control\" onchange=\"javascript:disableMasterAssign();\" multiple>");//P_ENH_ASSIGN_TO_USERS_TASK
  		Info comboci = null;
  		String privlidgeId = null;
  		String selectedVal = "-1";

  		if (moduleId != null && moduleId.trim().equals("2"))
  		{
  			privlidgeId = "13";
  		} else if (moduleId != null && moduleId.trim().equals("3"))
  		{
  			privlidgeId = null;
  		} else if(moduleId != null && moduleId.trim().equals("4"))
  		{
  			String ownerUserLevel=(String)request.getAttribute("ownerUserLevel");

  				if("0".equals(ownerUserLevel)){
  					
  					franchiseeNo="-1";
  					 areaID="-1";
  					
  				}else if("2".equals(ownerUserLevel)){
  					
  					franchiseeNo="0";
  					
  				}

  			if(areaID!=null && !areaID.equals("") && !areaID.equals("null") && !areaID.equals("-1"))
  			if(ownerCount==1)
  				selectedVal = CommonMgr.newInstance().getCommonCmDAO().getcmOwner(contactId);
  			else
  				selectedVal="-1";
  		}
  		if(StringUtil.isValid(request.getParameter("fromWhere")) && "cmLeadSummary".equals(request.getParameter("fromWhere")) && ownerCount==1 && StringUtil.isValid(selectedVal)){
  			request.setAttribute("fromPage", request.getParameter("fromWhere"));
  		}
  		if (moduleId != null && (moduleId.equals("2") || moduleId.equals("5")))
  		{
  			String check = "no";
  			if(!"fs".equals(_baseConstants.INCLUDED_MODULES.toLowerCase()) )
  				{
  					String modules = _baseConstants.INCLUDED_MODULES.toLowerCase();					
  					boolean isFsExist = modules.contains("fs");
  							
  						if(isFsExist)
  						{	
  							comboci = CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwnersForFS(userID,null,CMUserRoleMap,divisionIds,true);
  							check = "yes";
  						}
  				}
  			
  			if ("no".equals(check))
  				{
  					comboci = CommonMgr.newInstance().getCommonFsDAO().getAllLeadOwners(userID, divisionIds,true);
  				}
  		} else
  		{
  			String tempVal = franchiseeNo;
  			if(fromViewAll) {
  				String[] temp = CommonUtil.getFranchiseeAndOwner(request);
  				tempVal = temp[0]; 
  			}
  			comboci = taskMgr.getTaskDAO().getAllUsers1(userLevel, franchiseeNo, privlidgeId, userID, moduleId,areaID,tempVal,CMUserRoleMap, divisionIds,true);
  		}
  		String[] selectedValArr=null;//P_ENH_ASSIGN_TO_USERS_TASK
  		if (infoDetails != null && infoDetails.get(FieldNames.ASSIGN_TO) != null)
  		{
  			selectedVal = infoDetails.get(FieldNames.ASSIGN_TO);
  			if(StringUtil.isValid(selectedVal)){
  				
  				selectedValArr=selectedVal.split(",");
  			}
  		} else
  		{
  			try
  			{
  				if (moduleId != null && moduleId.equals("2"))
  				{

  					if (foreignID != null && foreignID.indexOf(",") == -1)
  					{
  						foreignID = foreignID.replaceAll("'", "");
  						Integer leadID = new Integer(foreignID);
  						Info leadInfo = CommonMgr.newInstance().getCommonFsDAO().getDetailsInfo(leadID);

  						if (leadInfo != null)
  						{
  							selectedVal = leadInfo.get(FieldNames.LEAD_OWNER_ID);
  						}
  					}
  				} else if (moduleId != null && moduleId.equals("5"))
  				{
  					selectedVal = userID;
  				}
  			} catch (Exception e)
  			{
  				logger.error("exception while setting assigned combo", e);
  			}
  		}
  		List selectedValList = null;
  		if(selectedValArr != null && selectedValArr.length>0)
  		{
  			selectedValList = StringUtil.convertToArrayList(selectedValArr);
  		}
  		
  		Iterator itInfo1 = comboci.getKeySetIterator();
  		sBuffCombo.append("<option value=\"").append("-1").append("\" ");
  		sBuffCombo.append(" selected>");
  		sBuffCombo.append(LanguageUtil.getString("Select",(String)request.getSession().getAttribute("userLanguage")));
  		sBuffCombo.append("</option>");

  		if (moduleId != null && moduleId.trim().equals("4") && ((userLevel.equals("0") && !contactCorporatePriveleges) || (userLevel.equals("1") && !contactFranchiseePriveleges) || (userLevel.equals("2") && !contactRegionalPriveleges) ))
  		{
  			sBuffCombo.append("<option value=\"").append(userID).append("\" ");
  			sBuffCombo.append(" selected >");
  			sBuffCombo.append((String) (request.getSession().getAttribute("sUserFirstName") + " " + request.getSession().getAttribute("sUserLastName")));
  			sBuffCombo.append("</option>");
  			sBuffCombo.append("</select>");
			//P_CM_B_76232 starts issue when user does't have privilege to view all contact
			if(selectedValList!=null && selectedValList.contains(userID))
			{
				selectedValList.remove(userID);
			}
			//P_CM_B_76232 Ends
  		} else
  		{
  			while (itInfo1.hasNext())
  			{
  				String key = (String) itInfo1.next();
  				String val = (String) comboci.getObject(key);
  				if(selectedValList!=null && selectedValList.contains(key))
  				{
  					selectedValList.remove(key);
  				}
  				sBuffCombo.append("<option value=\"").append(key).append("\" ");

  				if (selectedValArr != null && selectedValArr.length > 0)//P_ENH_ASSIGN_TO_USERS_TASK starts
  				{
  					for(int i =0;i<selectedValArr.length;i++){
  						if(selectedValArr[i].equals(key)){
  							sBuffCombo.append(" selected ");
  							break;
  						}
  					}
  					//P_ENH_ASSIGN_TO_USERS_TASK ends
  				}

  				sBuffCombo.append(" >");
  				sBuffCombo.append(val);
  				sBuffCombo.append("</option>");
  			}

  			sBuffCombo.append("</select>");
  		}
  		removeDeactivatedUser(selectedValList);
  		request.getSession().setAttribute("selectedValList", selectedValList);
  		request.setAttribute("selectedVal", selectedVal);
          if("2".equals(moduleId) || "4".equals(moduleId)) {
          String check = "checked";
          htmlContent.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
          htmlContent.append("<tr>");
  			if(!("opportunityPage".equals(request.getParameter("fromPage"))||"opportunityDetailPage".equals(request.getParameter("fromPage")) || "opportunityPage".equals((String)request.getAttribute("fromPage")))) {   
  				htmlContent.append("<td style=\"padding-bottom:3px\"><input type=\"radio\" name=\"assignToRadio\" id=\"radioOwner\" value=\"0\" checked onClick=\"javascript:changeUSer(this.value)\"/></td>");
  				if (request.getSession().getAttribute("menuName").equals("cm") || request.getSession().getAttribute("menuName").equals("zcubator") || ModuleUtil.zcubatorImplemented()){//P_CM_B_77804
  					if("leadTask".equals(request.getParameter("foreignType")) || "leadTask".equals((String)request.getAttribute("foreignType"))){
  						htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Lead Owner")).append("</td>");
  					}else{
  						htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Contact Owner")).append("</td>");
  					}
  				}else{ 
  					htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Lead Owner")).append("</td>");
  				} 
  				htmlContent.append("<td style=\"padding-bottom:3px\"><input type=\"radio\" name=\"assignToRadio\" id=\"radioUser\" value=\"1\" onClick=\"javascript:changeUSer(this.value)\"/></td>");
  				htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Other User")).append("</td>");
  				htmlContent.append("<td valign=\"middle\" id=\"userComboTd\">&nbsp;").append(sBuffCombo).append("</td>");
  				htmlContent.append("<input type=\"hidden\" name=\"contactOwnerID\" value=").append(request.getAttribute("contactOwnerID")).append(">");
  		
  		   } else { 
  		
  			   htmlContent.append("<td style=\"padding-bottom:3px\"><input type=\"radio\" name=\"assignToRadio\" id=\"radioOwner\" value=\"0\" checked onClick=\"javascript:changeUSer(this.value)\"/></td>");
  				if (request.getSession().getAttribute("menuName").equals("cm") || request.getSession().getAttribute("menuName").equals("zcubator")){
  					htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Opportunity Owner")).append("</td>");
  				} 
  				htmlContent.append("<td style=\"padding-bottom:3px\"><input type=\"radio\" name=\"assignToRadio\" id=\"radioUser\" value=\"1\" onClick=\"javascript:changeUSer(this.value)\"/></td>");
  				htmlContent.append("<td valign=\"middle\" nowrap=\"nowrap\">").append(LanguageUtil.getString("Other User")).append("</td>");
  				htmlContent.append("<td valign=\"middle\" id=\"userComboTd\">&nbsp;").append(sBuffCombo).append("</td>");
  				htmlContent.append("<input type=\"hidden\" name=\"contactOwnerID\" value=").append(request.getAttribute("contactOwnerID")).append(">");
  		  
         } 		  
  			htmlContent.append("</tr>");
  			htmlContent.append("</table>");
          }else{
          	htmlContent.append(sBuffCombo);
          }
  		builderDetailsInfo.set("htmlDisplayName", "<span class='urgent_fields'>*</span>displayName");
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("value", htmlContent);*/
  	}
  	public static Combo getTaskStatus(Field field,Boolean isModify)
  	{

  		Combo combo = new Combo(field.getDisplayName());
  		combo.setInfo(MasterDataMgr.newInstance().getMasterDataDAO().getMasterInfo(10007,"MASTER_DATA_ID",true,"-1"));
  		if(!isModify){
  			combo.setComboValue("637");
  		}
  		return combo;
  	}
  	public static Combo getTaskType(Field field,Boolean isModify)
  	{

  		Combo combo = new Combo(field.getDisplayName());
  		/*combo.setInfo(NewPortalUtils.getInfoFromMap(TaskMgr.newInstance().getTaskDAO().getTaskType()));
  		if(!isModify){
  			combo.setComboValue("1");
  		}*/
  		return combo;
  	}
  	public static Combo getTaskPriority(Field field,Boolean isModify)
  	{

  		Combo combo = new Combo(field.getDisplayName());
  		combo.setInfo(MasterDataMgr.newInstance().getMasterDataDAO().getMasterInfoById(10002));
  		if(!isModify){
  			combo.setComboValue("-1");
  		}
  		return combo;
  	}
  	public static void getTimeLessTaskCheckBox(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify)throws Exception
  	{
  		builderDetailsInfo.set("displayName", " ");
  		builderDetailsInfo.set("type", "htmlcode");
  		String timeless = "<table cellpadding='0' cellspacing='0' width='100%' border='0'><tr><td width='5'><input type=\"checkbox\" name=\"timelessTaskId\" onclick=\"changetimetask();\"";
  		String timelessValue = null;

  		if (totalInfo != null && totalInfo.getString(FieldNames.TIMELESS_TASK) != null && !totalInfo.getString(FieldNames.TIMELESS_TASK).equals("") && totalInfo.getString(FieldNames.TIMELESS_TASK).equals("Y"))
  		{
  			timeless += " checked ";
  			timelessValue = "Y";
  		} else
  		{
  			timelessValue = "N";
  		}

  		request.setAttribute("timelessValueNew", timelessValue);
  		timeless += "><input type=\"hidden\" name=\""+FieldNames.TIMELESS_TASK+"\" value=\""+timelessValue+"\">";
  		timeless+="</td><td class='text'> "+(LanguageUtil.getString(field.getDisplayName(), (String)request.getSession().getAttribute("userLanguage")))+"</td></tr></table>";//P_FIM_B_24165
  		builderDetailsInfo.set("addhtml", timeless);
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);

  	}
  	public static void getStartTime(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
  		Info combInfo = new Info();
  		Info info1=null;
  		StringBuffer sBuff = new StringBuffer();
  		String curr="";
  		String curr1="";
  		String starDate = null;
  		String userTimeZone = UserTimezoneMap.newInstance()
  				.getTimezone(
  						(String) (request.getSession()
  								.getAttribute("user_no")));
  		if (!isModify)
  		{
  			userTimeZone = (String) request.getSession().getAttribute("userTimeZone");
  			curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  			curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  		} else if (isModify)
  		{
  			// for modify
  			if (infoDetails.getObject(FieldNames.DATE) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.DATE);

  				starDate = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  				
  				if (infoDetails.get(FieldNames.TIMELESS_TASK) == null || !"Y".equals(infoDetails.get(FieldNames.TIMELESS_TASK)))
  				{
  					curr = new String(dateStamp.toString().substring(0, 19));
  				} else
  				{
  					curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  					curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  				}
  			}
  		}
  		// creating options for hrs
  		for (int i = 1; i <= 12; i++)
  		{
  			combInfo.set(i + "", i + "");
  		}

  		Info combInfoMin = new Info();

  		// creating options for min
  		combInfoMin.set("00", "00 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("15", "15 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("30", "30 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("45", "45 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		int sTime = 0;
  		int apm = 0;
  		sBuff = new StringBuffer("<select name=\"sTime\" class=\"multiList\" onChange=\"setendTime(this.value);\">");
  		Iterator itInfo = combInfo.getKeySetIterator();

  		while (itInfo.hasNext())
  		{
  			String key = (String) itInfo.next();
  			String val = (String) combInfo.getObject(key);
  			int time = Integer.parseInt(val);

  			if (isModify)
  			{
  				if (curr != null && curr.length() > 0)
  				{
  					sTime = Integer.parseInt(curr.substring(11, 13));

  					if (sTime == 0)
  					{
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12)
  					{
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12)
  					{
  						apm = 1;
  						time += 12;
  					}
  				}
  			} else
  			{
  				if (curr != null && curr.length() > 0)
  				{
  					sTime = Integer.parseInt(curr.substring(11, 13));

  					if (sTime == 0)
  					{
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12)
  					{
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12)
  					{
  						apm = 1;
  						time += 12;
  					}
  				}
  			}

  			if (isModify && time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else if (time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("&nbsp;:&nbsp;");
  		sBuff.append("<select name=\"sMinute\" class=\"multiList\" onChange=\"setendMinute(this.value);\">");
  		itInfo = combInfoMin.getKeySetIterator();
  		String sCurrenMinute = "00";
  		int cMinute = 0;

  		if (curr != null && curr.length() > 0)
  		{
  			sCurrenMinute = curr.substring(14, 16);
  		}

  		if (infoDetails != null && infoDetails.get(FieldNames.TIMELESS_TASK) != null && "Y".equals(infoDetails.get(FieldNames.TIMELESS_TASK)) && curr != null && curr.length() > 0)
  		{
  			cMinute = Integer.parseInt(curr.substring(14, 16));

  			if (cMinute < 15)
  			{
  				sCurrenMinute = "00";
  			} else if (cMinute < 30)
  			{
  				sCurrenMinute = "15";
  			} else if (cMinute < 45)
  			{
  				sCurrenMinute = "30";
  			} else if (cMinute < 59)
  			{
  				sCurrenMinute = "45";
  			}
  		}

  		if (!isModify && curr != null && curr.length() > 0)
  		{
  			cMinute = Integer.parseInt(curr.substring(14, 16));

  			if (cMinute < 15)
  			{
  				sCurrenMinute = "00";
  			} else if (cMinute < 30)
  			{
  				sCurrenMinute = "15";
  			} else if (cMinute < 45)
  			{
  				sCurrenMinute = "30";
  			} else if (cMinute < 59)
  			{
  				sCurrenMinute = "45";
  			}
  		}

  		String key;
  		String val;

  		while (itInfo.hasNext())
  		{
  			key = (String) itInfo.next();
  			val = (String) combInfoMin.getObject(key);

  			if (isModify && key.equals(sCurrenMinute))
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else if (key.equals(sCurrenMinute))
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("<select name=\"APM\" class=\"multiList\" onChange=\"setendAPM(this.value);\">");
  		sBuff.append("<option value = \" ").append("0").append("\" ").append(apm == 0 ? " selected " : "").append(">").append("AM ").append("</option>");
  		sBuff.append("<option value = \" ").append("1").append("\" ").append(apm == 1 ? " selected " : "").append(">").append("PM ").append("</option>");
  		sBuff.append("</select>");
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", sBuff.toString());
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);


  	}
  	public static void getEndTime(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
  		Info combInfo = new Info();
  		Info info1=null;
  		StringBuffer sBuff = new StringBuffer();
  		String curr="";
  		String curr1="";
  		String starDate = null;
  		String userTimeZone = UserTimezoneMap.newInstance()
  				.getTimezone(
  						(String) (request.getSession()
  								.getAttribute("user_no")));
  		if (!isModify)
  		{
  			userTimeZone = (String) request.getSession().getAttribute("userTimeZone");
  			curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  			curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  		} else if (isModify)
  		{
  			// for modify
  			if (infoDetails.getObject(FieldNames.DATE) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.DATE);

  				starDate = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  				
  				if (infoDetails.get(FieldNames.TIMELESS_TASK) == null || !"Y".equals(infoDetails.get(FieldNames.TIMELESS_TASK)))
  				{
  					curr = new String(dateStamp.toString().substring(0, 19));
  				} else
  				{
  					curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  					curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  				}
  			}
  		}
  		// creating options for hrs
  		for (int i = 1; i <= 12; i++)
  		{
  			combInfo.set(i + "", i + "");
  		}

  		Info combInfoMin = new Info();

  		// creating options for min
  		combInfoMin.set("00", "00 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("15", "15 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("30", "30 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("45", "45 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		int sTime = 0;
  		int apm = 0;
  		sBuff = new StringBuffer("<select name=\"eTime\" class=\"multiList\">");

  		int durhr = 1;
  		int durMin = 0;
  		String durMinStr = "00";
  		try{
  		if (isModify && "N".equals(infoDetails.getString(FieldNames.TIMELESS_TASK))) {
  			durhr = Integer.parseInt(infoDetails.get(FieldNames.DURATION).substring(0, 2));
  			durMin = Integer.parseInt(infoDetails.get(FieldNames.DURATION).substring(3, 5));
  			durMinStr = infoDetails.get(FieldNames.DURATION).substring(3, 5);
  		}
  		}catch(Exception e){
  			logger.info("Exception in CalendarTaskAction::::::"+e);
  		}
  		// int hourTime = 0;
  		Calendar c = Calendar.getInstance();

  		if (curr != null && curr.length() > 0)
  		{
  			c = DateTime.addHourMinute(curr, durhr, durMin, "yyyy-MM-dd HH:mm:ss");
  		}

  		Iterator itInfo = combInfo.getKeySetIterator();

  		while (itInfo.hasNext())
  		{
  			String key = (String) itInfo.next();
  			String val = (String) combInfo.getObject(key);
  			int time = Integer.parseInt(val);

  			if (isModify)
  			{
  				if (curr != null && curr.length() > 0)
  				{
  					sTime = c.get(Calendar.HOUR_OF_DAY);

  					if (sTime == 0)
  					{
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12)
  					{
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12)
  					{
  						apm = 1;
  						time += 12;
  					}
  				}
  			} else
  			{
  				if (curr != null && curr.length() > 0)
  				{
  					sTime = c.get(Calendar.HOUR_OF_DAY);

  					if (sTime == 0)
  					{
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12)
  					{
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12)
  					{
  						apm = 1;
  						time += 12;
  					}
  				}
  			}

  			if (isModify && time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else if (time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("&nbsp;:&nbsp;");
  		sBuff.append("<select name=\"eMinute\" class=\"multiList\">");
  		String sCurrenMinute = "00";
  		int cMinute = 0;
  		itInfo = combInfoMin.getKeySetIterator();
  		if(isModify && !"Y".equals(infoDetails.get(FieldNames.TIMELESS_TASK))){
  			if (curr != null && curr.length() > 0){
  				sCurrenMinute = c.get(Calendar.MINUTE) + "";
  			}
  		}
  		if (!isModify && curr != null && curr.length() > 0)
  		{
  			cMinute = c.get(Calendar.MINUTE);

  			if (cMinute < 15)
  			{
  				sCurrenMinute = "00";
  			} else if (cMinute < 30)
  			{
  				sCurrenMinute = "15";
  			} else if (cMinute < 45)
  			{
  				sCurrenMinute = "30";
  			} else if (cMinute < 59)
  			{
  				sCurrenMinute = "45";
  			}
  		}


  		while (itInfo.hasNext())
  		{
  			String key = (String) itInfo.next();
  			String val = (String) combInfoMin.getObject(key);

  			if (isModify && key.equals(sCurrenMinute))
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else if (key.equals(sCurrenMinute))
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("<select name=\"MPM\" class=\"multiList\">");
  		sBuff.append("<option value = \" ").append("0").append("\" ").append(apm == 0 ? " selected " : "").append(">").append("AM ").append("</option>");
  		sBuff.append("<option value = \" ").append("1").append("\" ").append(apm == 1 ? " selected " : "").append(">").append("PM ").append("</option>");
  		sBuff.append("</select>");
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", sBuff.toString());
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);
  	}
  	public static void getScheduledTime(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify){
  		String htmlChecked1 ="",htmlChecked2="",htmlChecked3="";
  		String disabled = "disabled";
  		if (totalInfo != null && totalInfo.get(FieldNames.SCHEDULE_TIME) != null)
  		{
  			if("0".equals(totalInfo.get(FieldNames.SCHEDULE_TIME)))
  			   htmlChecked1="checked";
  			else if("1".equals(totalInfo.get(FieldNames.SCHEDULE_TIME)))
  			   htmlChecked2="checked";  
  			else if("2".equals(totalInfo.get(FieldNames.SCHEDULE_TIME)))
  			{
  				htmlChecked3="checked";
  				disabled = "";
  			}
  			
  		} else
  		{
  			htmlChecked2="checked";  
  		}
  		String dependentChildFunction = getDependentChildFunctions(field);
  		String functionName = "changeHtml();";
  		if(StringUtil.isValidNew(dependentChildFunction)) {
  			functionName = functionName + dependentChildFunction;
  		}
  		StringBuffer radioHtmlCode = new StringBuffer();
  		radioHtmlCode.append("<input type=\"radio\" name=\"schduleTime\" id=\"schduleTime1\" value=\"0\" onclick=\""+functionName+"\" "+htmlChecked1+">");
  		radioHtmlCode.append("&nbsp;");
  		radioHtmlCode.append(LanguageUtil.getString("No Reminder", (String)request.getSession().getAttribute("userLanguage")));
  		radioHtmlCode.append("&nbsp;&nbsp;");
  		radioHtmlCode.append("<input type=\"radio\" name=\"schduleTime\" id=\"schduleTime2\" value=\"1\" onclick=\""+functionName+"\" "+htmlChecked2+">");
  		radioHtmlCode.append("&nbsp;");
  		radioHtmlCode.append(LanguageUtil.getString("15 Minutes Prior", (String)request.getSession().getAttribute("userLanguage")));
  		radioHtmlCode.append("&nbsp;&nbsp;");
  		radioHtmlCode.append("<input type=\"radio\" name=\"schduleTime\" id=\"schduleTime3\" value=\"2\" onclick=\""+functionName+"\" "+htmlChecked3+">");
  		radioHtmlCode.append("&nbsp;");
  		radioHtmlCode.append(LanguageUtil.getString("Set Time Yourself", (String)request.getSession().getAttribute("userLanguage")));
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		builderDetailsInfo.set("addhtml", radioHtmlCode.toString());
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);
  	}
  	public static void getTaskEmailReminder(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify){
  		StringBuffer checkboxHtml=new StringBuffer();
  		 String sendEmailNotification = totalInfo.get(FieldNames.TASK_EMAIL_REMINDER);
           String checked = "";
           if("Y".equals(sendEmailNotification)){
          	 checked="checked";
           }
          checkboxHtml.append("<label for=\"taskEmailReminder\" style=\"display: inline;\">");
  		checkboxHtml.append("<input type=\"checkbox\" name=\"").append(FieldNames.TASK_EMAIL_REMINDER).append("_checkbox\"").append(" ") .append(checked).append(" ").append( "onclick=\"sendEmailNotification();\" class=\"checkbox\" id=\"").append(FieldNames.TASK_EMAIL_REMINDER).append("_checkbox\">").append(LanguageUtil.getString(field.getDisplayName()));
  		checkboxHtml.append("</label>");
  		checkboxHtml.append("<input type=\"hidden\" name=\"").append(FieldNames.TASK_EMAIL_REMINDER).append("\" value=\"\">");
  		builderDetailsInfo.set("displayName", " ");
  		builderDetailsInfo.set("type", "htmlcode");
  		builderDetailsInfo.set("addhtml", checkboxHtml.toString());
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);
  	}
  	public static void getReminderDate(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
  		String dateString="";
  		String starDate = null;
  		String starDate1 = null;
  		String userTimeZone = UserTimezoneMap.newInstance()
  				.getTimezone(
  						(String) (request.getSession()
  								.getAttribute("user_no")));
  		if (isModify)
  		{
  			if (infoDetails.getObject(FieldNames.DATE) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.DATE);
  				starDate = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  			}
  			if (infoDetails.getObject(FieldNames.REMINDER_TIME) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.REMINDER_TIME);
  				starDate1 = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  			} else if (starDate1 == null && ("0").equals(infoDetails.getString(FieldNames.SCHEDULE_TIME)))
  			{
  				starDate1 = starDate;
  			}

  		}
  		if (isModify)
  		{
  			if (starDate1 != null && !starDate1.equals(""))
  			{
  				dateString="<input type=\"text\"  size=\"19\" onblur=\"dateUtility(this,form.name)\"  id=\"REMINDER_DATE\" name=\"REMINDER_DATE\" value=\""+starDate1+"\" disabled class=\"fTextBoxDate\">&nbsp;&nbsp;&nbsp;<a tabindex=\"-1\" href=\"#\" id=\"reminderdateCal\"><img  align=\"\" border=\"0\" src="+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/smallcalendar.gif /></a>";
  			} else
  			{
  				dateString="<input type=\"text\"  size=\"19\"  onblur=\"dateUtility(this,form.name)\"  id=\"REMINDER_DATE\" name=\"REMINDER_DATE\" value=\"\" disabled class=\"fTextBoxDate\">&nbsp;&nbsp;&nbsp;<a tabindex=\"-1\" href=\"#\" id=\"reminderdateCal\"><img align=\"\" border=\"0\" src="+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/smallcalendar.gif /></a>";
  			}
  		} else
  		{
  			dateString="<input type=\"text\"  size=\"19\" onblur=\"dateUtility(this,form.name)\" id=\"REMINDER_DATE\"  name=\"REMINDER_DATE\" value=\"\" disabled class=\"fTextBoxDate\">&nbsp;&nbsp;&nbsp;<a tabindex=\"-1\" href=\"#\" id=\"reminderdateCal\"><img  align=\"\" border=\"0\" src="+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/smallcalendar.gif /></a>";
  		}
  		String mandatoryMark ="<span class='urgent_fields' id='selReminder' name='selReminder'>*</span>";
  		builderDetailsInfo.set("displayName", field.getDisplayName());
  		builderDetailsInfo.set("htmlDisplayName", mandatoryMark+"displayName : ");
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", dateString);
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);
  		
  	}
  	public static void getReminderTime(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
  		String curr = "";
  		String curr1 = "";
  		String starDate = null;
  		String starDate1 = null;
  		String displayName = "";
  		String userTimeZone = UserTimezoneMap.newInstance()
  				.getTimezone(
  						(String) (request.getSession()
  								.getAttribute("user_no")));
  		String disabled = "disabled";
  		// for create page date and time will be populated as current date time
  		if (!isModify)
  		{
  			curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  			curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  		} else if (isModify)
  		{
  			// for modify
  			if (infoDetails.getObject(FieldNames.DATE) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.DATE);

  				starDate = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  				
  				if (infoDetails.get(FieldNames.TIMELESS_TASK) == null || !"Y".equals(infoDetails.get(FieldNames.TIMELESS_TASK)))
  				{
  					curr = new String(dateStamp.toString().substring(0, 19));
  				} else
  				{
  					curr = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  					curr1 = TimeZoneUtils.getCurrentTime(userTimeZone, "yyyy-MM-dd HH:mm:ss");
  				}
  			}
  		}
  		Info combInfo = new Info();

  		for (int i = 1; i <= 12; i++)
  		{
  			combInfo.set(i + "", i + "");
  		}

  		Info combInfoMin = new Info();
  		combInfoMin.set("00", "00 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("15", "15 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("30", "30 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		combInfoMin.set("45", "45 "+LanguageUtil.getString("Min",(String)request.getSession().getAttribute("userLanguage")));
  		int sTime = 0;
  		int apm = 0;
  		StringBuffer sBuff = new StringBuffer("<select name=\"rTime\" class=\"multiList\" " + disabled + " >");
  		Iterator itInfo = combInfo.getKeySetIterator();

  		while (itInfo.hasNext())
  		{
  			String key = (String) itInfo.next();
  			String val = (String) combInfo.getObject(key);
  			int time = Integer.parseInt(val);

  			if (isModify)
  			{
  				if (curr1 != null && curr1.length() > 0)
  				{
  					sTime = Integer.parseInt(curr1.substring(11, 13));
  				}

  				if (sTime == 0)
  				{
  					apm = 0;
  					time = 0;
  					sTime = 0;
  				} else if (sTime == 12)
  				{
  					apm = 1;
  					time = 12;
  				} else if (sTime > 11 && sTime != 12)
  				{
  					apm = 1;
  					time += 12;
  				}
  			} else
  			{
  				if (curr1 != null && curr1.length() > 0)
  				{
  					int thrs = Integer.parseInt(curr1.substring(11, 13)) * 60 + Integer.parseInt(curr1.substring(14, 16)) - 15;
  					sTime = thrs / 60;

  					if (sTime == 0)
  					{
  						apm = 0;
  						time = 0;
  						sTime = 0;
  					} else if (sTime == 12)
  					{
  						apm = 1;
  						time = 12;
  					} else if (sTime > 11 && sTime != 12)
  					{
  						apm = 1;
  						time += 12;
  					}
  				}
  			}

  			if (isModify && time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else if (time == sTime)
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("&nbsp;:&nbsp;");
  		sBuff.append("<select name=\"rMinute\" class=\"multiList\" " + disabled + " >");
  		itInfo = combInfoMin.getKeySetIterator();
  		String sCurrenMinute = "00";

  		if (curr1 != null && curr1.length() > 0)
  		{
  			sCurrenMinute = curr1.substring(14, 16);
  		}

  		if (curr1 != null && curr1.length() > 0)
  		{
  			int thrs = Integer.parseInt(curr1.substring(11, 13)) * 60 + Integer.parseInt(curr1.substring(14, 16)) - 15;
  			int cMinute1 = (thrs % 60);

  			if (isModify)
  			{
  				cMinute1 += 15;
  			}

  			if (cMinute1 < 15)
  			{
  				sCurrenMinute = "00";
  			} else if (cMinute1 < 30)
  			{
  				sCurrenMinute = "15";
  			} else if (cMinute1 < 45)
  			{
  				sCurrenMinute = "30";
  			} else if (cMinute1 < 59)
  			{
  				sCurrenMinute = "45";
  			}
  		}

  		while (itInfo.hasNext())
  		{
  			String key = (String) itInfo.next();
  			String val = (String) combInfoMin.getObject(key);

  			if (sCurrenMinute.length() > 0 && key.equals(sCurrenMinute))
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" SELECTED>").append(val).append("</option>");
  			} else
  			{
  				sBuff.append("<option value = \" ").append(key).append("\" >").append(val).append("</option>");
  			}
  		}

  		sBuff.append("</select>");
  		sBuff.append("<select name=\"RPM\" class=\"multiList\" " + disabled + " >");
  		sBuff.append("<option value = \" ").append("0").append("\" ").append(apm == 0 ? " selected " : "").append(">").append("AM ").append("</option>");
  		sBuff.append("<option value = \" ").append("1").append("\" ").append(apm == 1 ? " selected " : "").append(">").append("PM ").append("</option>");
  		sBuff.append("</select>");

  		builderDetailsInfo.set("displayName",field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", sBuff.toString());
  		//builderInfo.set(field.getFieldName(), builderDetailsInfo);

  	}
  	public static void getStartDateCombo(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
          StringBuffer startDateCombo = new StringBuffer();
          String value="";
          String starDate = null;
  		String starDate1 = null;
  		StringBuffer sBuff = new StringBuffer();
  		String userTimeZone = UserTimezoneMap.newInstance()
  				.getTimezone(
  						(String) (request.getSession()
  								.getAttribute("user_no")));
  		String currentUserTime = TimeZoneUtils.getCurrentTime(userTimeZone, TimeZoneUtils.DB_DATETIME);
  		if (isModify)
  		{
  			if (infoDetails.getObject(FieldNames.DATE) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.DATE);
  				starDate = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  			}
  			if (infoDetails.getObject(FieldNames.REMINDER_TIME) != null)
  			{
  				java.sql.Timestamp dateStamp = (java.sql.Timestamp) infoDetails.getObject(FieldNames.REMINDER_TIME);
  				starDate1 = DateTime.getDateTime(dateStamp, Constants.DISPLAY_FORMAT);
  			} else if (starDate1 == null && ("0").equals(infoDetails.getString(FieldNames.SCHEDULE_TIME)))
  			{
  				starDate1 = starDate;
  			}

  		}
          if (isModify)
          {
          	value = starDate;
          } else
          {
          	value= DateTime.getRequiredFormat(currentUserTime, TimeZoneUtils.DB_DATETIME, Constants.DISPLAY_FORMAT);
          }
          sBuff.append("<input class=\"fTextBoxDate\" name=\"startDate\"  onblur=\"dateUtility(this,form.name)\" onchange=\"showAvailabilityDiv()\" id=\"startDate\"  value=\""+value +"\"  type=\"text\" />");  //P_B_25595
          sBuff.append("&nbsp;&nbsp;&nbsp;<a tabindex=\"-1\" href=\"#\" id=\"startdateCal1\"><img border=\"0\"  align=\"\" src=\"").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/smallcalendar.gif\"/></a>");
          builderDetailsInfo.set("displayName",field.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(field.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", sBuff.toString());
  			
  		//builderInfo.set(field.getFieldName(), sBuff);
  	}
  	
  	public static void divisionBrandCombos(Info builderDetailsInfo, Info builderInfo, String franchiseeNo) {
  		/*boolean brandFlag = false;
		boolean divisionFlag = false;
		String userLevel = (String)StrutsUtil.getHttpSession().getAttribute("user_level");
		//case for division
		if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "G".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) { //case when division based is configured.
			String divisionId = DivisionUtil.getDivisionIdsForFranchisee(franchiseeNo);
			divisionFlag = true;
			builderDetailsInfo = new Info();
			
			if(Constants.USER_LEVEL_DIVISION.equals(userLevel)) {
				builderDetailsInfo.set("displayName", MultiTenancyUtil.getTenantConstants().DIVISION_LABEL);
				builderDetailsInfo.set("type", "display");
				String divisionName = "";
				if(StringUtil.isValidNew(divisionId)) {
					divisionName = DivisionUtil.getAllDivisionNames(divisionId);
				}
				builderDetailsInfo.set("value", divisionName);
				if(("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) || "0".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
					brandFlag = true;
				}
				if(brandFlag) {
					//do nothing
				} else {
					builderDetailsInfo.set("colspan", "2");
				}
				builderInfo.set("locationDivisionMapping_0divisionID_display", builderDetailsInfo);
				
				builderDetailsInfo = new Info();
				builderDetailsInfo.set("type", "hidden");
				builderDetailsInfo.set("value", divisionId);
				builderInfo.set("locationDivisionMapping_0divisionID", builderDetailsInfo);
				
			} else {
				Info divisionInfo = DivisionUtil.getDivisionComboInfo();
				Combo divisionCombo = new Combo(MultiTenancyUtil.getTenantConstants().DIVISION_LABEL);
				divisionCombo.setInfo(divisionInfo);
				if("Y".equals(MultiTenancyUtil.getTenantConstants().ALLOW_MULTIPLE_DIVISION)){
					divisionCombo.setMultiple();
					divisionCombo.setComboClass("form-control");
				}
				builderDetailsInfo.set(FieldNames.DISPLAY_NAME, "Division");
				if(("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) || "0".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
					brandFlag = true;
				}
				if(brandFlag) {
					//do nothing
				} else {
					builderDetailsInfo.set("colspan", "2");
				}
				if(StringUtil.isValidNew(franchiseeNo)) {
					divisionCombo.setComboValue(divisionId);
				}
				builderDetailsInfo.set("type", divisionCombo);
				builderInfo.set("locationDivisionMapping_0divisionID", builderDetailsInfo);

				builderDetailsInfo = new Info();
				builderDetailsInfo.set("type", "hidden");
				builderDetailsInfo.set("value", divisionId);
				builderInfo.set("divisionValueModify", builderDetailsInfo);
			}
		}
    	
    	//case for brand
		if(("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) || "0".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
			String brandLabel = "Brand";
			String brandId = DivisionUtil.getBrandsIdsForFranchisee(franchiseeNo);
			if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) {
				brandId = DivisionUtil.getDivisionIdsForFranchisee(franchiseeNo);
			}
			if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) {
				brandLabel = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL;
			}
			builderDetailsInfo = new Info();
			
			if(Constants.USER_LEVEL_DIVISION.equals(userLevel) && "Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) {
				builderDetailsInfo.set("displayName", brandLabel);
				builderDetailsInfo.set("type", "display");
				String brandName = "";
				if(StringUtil.isValidNew(brandId)) {
					brandName = DivisionUtil.getAllBrandNames(brandId);
				}
				builderDetailsInfo.set("value", brandName);
				if(divisionFlag) {
					//do nothing
				} else {
					builderDetailsInfo.set("colspan", "2");
				}
				builderInfo.set("fimBrandMapping_0brandID_display", builderDetailsInfo);
				
				builderDetailsInfo = new Info();
				builderDetailsInfo.set("type", "hidden");
				builderDetailsInfo.set("value", brandId);
				builderInfo.set("fimBrandMapping_0brandID", builderDetailsInfo);
			} else {
				Info brandInfo = DivisionUtil.getBrandComboInfo((String)StrutsUtil.getHttpSession().getAttribute("divisionIds"));
				if(divisionFlag) {
					brandInfo = DivisionUtil.getBrandComboInfo(null);
				}
				Combo divisionCombo = new Combo(brandLabel);
				divisionCombo.setInfo(brandInfo);
				if(!"0".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED) && !"N".equals(MultiTenancyUtil.getTenantConstants().ALLOW_MULTIPLE_DIVISION)) {
					divisionCombo.setMultiple();
					divisionCombo.setComboClass("form-control");
				}
				builderDetailsInfo.set(FieldNames.DISPLAY_NAME, "Brand");
				if(StringUtil.isValidNew(franchiseeNo)) {
					divisionCombo.setComboValue(brandId);
				}
				builderDetailsInfo.set("type", divisionCombo);
				if(divisionFlag) {
					//do nothing
				} else {
					builderDetailsInfo.set("colspan", "2");
				}
				builderInfo.set("fimBrandMapping_0brandID", builderDetailsInfo);

				builderDetailsInfo = new Info();
				builderDetailsInfo.set("type", "hidden");
				builderDetailsInfo.set("value", brandId);
				builderInfo.set("brandValueModify", builderDetailsInfo);
			}
		}*/
  	}
  	
  	public static void getAddToCalendarCheckBox(Field field, Info builderDetailsInfo, HttpServletRequest request,Info builderInfo,Info totalInfo,Boolean isModify,Info infoDetails){
  		StringBuffer sBuff = new StringBuffer();
  		sBuff.append("<table><tr><td><input type='checkbox' name='calendarTaskCheckBox' value='Y' ");		//P_B_72608
  		  if ((infoDetails == null || infoDetails.size() <= 0) || (infoDetails.getString(FieldNames.SCHEDULE_ID) != null && !infoDetails.getString(FieldNames.SCHEDULE_ID).trim().equals("0") && !infoDetails.getString(FieldNames.SCHEDULE_ID).trim().equals("")))
  			{
  			  ArrayList<String> arr=new ArrayList();
  				if(infoDetails!=null&&infoDetails.getString(FieldNames.STATUS)!=null&&infoDetails.getString(FieldNames.STATUS).equals("639"))
  				{
  					//completed task should not be checked as add to calendar in case of modify
  					arr.add(infoDetails.getString(FieldNames.TASK_ID));
  					//TaskMgr.newInstance().getTaskDAO().modifyTasksToCalendar(arr);
  				}
  				
  				  sBuff.append(" checked");
  			}
  			sBuff.append("></td><td>").append(LanguageUtil.getString(field.getDisplayName(), (String)request.getSession().getAttribute("userLanguage"))).append("</td></tr></table>"); //P_FS_B_25589
  			sBuff.append("<input type='hidden' name='calendarTask' value=''> ");		//P_B_72608
  			builderDetailsInfo.set("displayName"," ");
  			builderDetailsInfo.set("type", "htmlcode");
  			if(field.getColspan().equals("2")) {
  				builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  			}
  			if(infoDetails!=null && infoDetails.size()>0){
  				request.setAttribute("prevCalendarTask",infoDetails.getString(FieldNames.CALENDAR_TASK));  //P_B_72608
  			}
  			builderDetailsInfo.set("addhtml", sBuff.toString());
  			
  			//builderInfo.set(field.getFieldName(), sBuff);
  	}
  	public static void modifyValues(Field fld,String[] arrValue,String[] arrDisp,int index, Info totalInfo,String fieldName,HttpServletRequest request,String moduleId)
  	{
  		try {
  			String newValue=null;
  			String newDisplayName=null;
  			HttpSession ses=request.getSession();
  			String userLevel=(String)ses.getAttribute("user_level");
  			String userNo = (String)ses.getAttribute("user_no");
  			if(!StringUtil.isValid(arrValue[index]))
  				arrValue[index]="";
  			String print = request.getParameter("print");
  			String printLeadDetails = request.getParameter("printLeadDetails");
  			if(print==null)
  				print=printLeadDetails;
  			if("status".equals(fieldName)){
  	            Combo combo = getTaskStatus(fld, true);
  	            Info comboInfo = (Info) combo.getInfo();
  	            Iterator it = comboInfo.getKeySetIterator();
  	            String jScript = combo.getJScript() != null ? combo.getJScript() : "";
  	            StringBuilder taskTypeString = new StringBuilder("<select id=").append(fld.getFieldName()).append(" name=").append(fld.getFieldName()).append(" class='multiList' ").append(jScript).append(" >");
  	            if (combo.isShowDefault())
  	            {
  	            	taskTypeString.append("<option value=-1>").append(LanguageUtil.getString("Select", (String) request.getSession().getAttribute("userLanguage"))).append("</option>");
  	            }
  	            String comboValue = totalInfo.get(fieldName) != null ? totalInfo.get(fieldName) : combo.getComboValue();
  	            if("Yes".equals(request.getParameter("fromFsHome"))){
  	            	comboValue="639";
  	            }
  	            while (it.hasNext())
  	            {
  	                String comboKey = (String) it.next();
  	                String selected = comboKey.equals(comboValue) ? "selected" : "";
  	                taskTypeString.append("<option value=").append(comboKey).append(" ").append(selected).append(">").append((String) comboInfo.get(comboKey)).append("</option>");
  	            }
  	            taskTypeString.append("</select>");
  	            newValue=taskTypeString.toString();
  			}else if("comments".equals(fieldName))
  			{
  	            StringBuilder commentString = new StringBuilder("<label><textarea name=").append(fieldName).append(" id=\"").append(fieldName).append("\" rows=").append(fld.getNoOfRowsField()).append("  cols=").append(Integer.parseInt(fld.getColspan()) > 1 ? 60 :fld.getColspan())
  	                    .append(" class='fTextBox' ").append(" >").append(totalInfo.get(fieldName)).append("</textarea></label>");
  	            newValue=commentString.toString();
  			}
  				
  			
  			if(StringUtil.isValid(newValue))
  				arrValue[index]=newValue;
  			if(StringUtil.isValid(newDisplayName))
  				arrDisp[index]=newDisplayName;

  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			logger.error("Exception in modifyValues::"+e);
  		}
  	}
  	//P_Enh_ContactHistory_FormBuilder ends  	
  	

  	
    
  	//P_Enh_Mu-Entity_FormGenerator starts
  	/**
  	 * This method creates period combo for Mu/Entity/Area Financial Tab
  	 * P_Enh_Mu-Entity_FormGenerator
  	 * @author Akash Kumar
  	 * @param builderDetailsInfo
  	 * @param fld
  	 * @param totalInfo
  	 * @throws Exception
  	 */
  	public static void getPeriodCombo(Info builderDetailsInfo,Field fld,Info totalInfo) throws Exception {

  		String comboName=FieldNames.EMPTY_STRING;
  		String textFieldName=FieldNames.EMPTY_STRING;
  		String spanName=FieldNames.EMPTY_STRING;
  		String id=FieldNames.EMPTY_STRING;
  		String selectedValue=FieldNames.EMPTY_STRING;
  		String valueforTextField=FieldNames.EMPTY_STRING;
  		HttpSession session = StrutsUtil.getHttpServletRequest().getSession();

  		try {
  			if(FieldNames.FIM_CB_ROYALTY_PERIOD.equals(fld.getFieldName()))
  			{
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_ROYALTY_PERIOD))){
  					selectedValue=totalInfo.getString(FieldNames.FIM_CB_ROYALTY_PERIOD);
  				}
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_OTHER_ROYALTY_PERIOD))){
  				valueforTextField=totalInfo.getString(FieldNames.FIM_CB_OTHER_ROYALTY_PERIOD);
  				}
  				comboName=FieldNames.FIM_CB_ROYALTY_PERIOD;
  				textFieldName=FieldNames.FIM_CB_OTHER_ROYALTY_PERIOD;
  				spanName="royalityPeriod";
  			}else if(FieldNames.FIM_CB_ADVERTISING_PERIOD.equals(fld.getFieldName()))
  			{
  				
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_ADVERTISING_PERIOD))){
  					selectedValue=totalInfo.getString(FieldNames.FIM_CB_ADVERTISING_PERIOD);
  				}
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_OTHER_ADVERTISING_PERIOD))){
  	  				valueforTextField=totalInfo.getString(FieldNames.FIM_CB_OTHER_ADVERTISING_PERIOD);
  	  			}
  				comboName=FieldNames.FIM_CB_ADVERTISING_PERIOD;
  				textFieldName=FieldNames.FIM_CB_OTHER_ADVERTISING_PERIOD;
  				spanName="advertisingPeriod";
  			}else if(FieldNames.FIM_CB_OTHER_PERIOD.equals(fld.getFieldName()))
  			{
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_OTHER_PERIOD))){
  					selectedValue=totalInfo.getString(FieldNames.FIM_CB_OTHER_PERIOD);
  				}
  				if(totalInfo!=null && StringUtil.isValidNew(totalInfo.getString(FieldNames.FIM_CB_OTHER_PERIOD_VALUE))){
  	  				valueforTextField=totalInfo.getString(FieldNames.FIM_CB_OTHER_PERIOD_VALUE);
  	  			}
  				comboName=FieldNames.FIM_CB_OTHER_PERIOD;
  				textFieldName=FieldNames.FIM_CB_OTHER_PERIOD_VALUE;
  				spanName="otherChargesPeriod";
  				id="pCombo";
  			}
  			Info info1=MasterDataMgr.newInstance().getMasterDataDAO().getMasterInfo(Integer.parseInt("8022"));
  			StringBuffer stringForCOmbp=new StringBuffer();
  			stringForCOmbp.append("<select name='"+comboName+"' id='"+comboName+"' class='multiList' onchange=\"show('"+spanName+"',this)\"><option value='-1' ");

  			if(selectedValue==null || selectedValue.equals("") || selectedValue.equals("-1"))
  				stringForCOmbp.append(" selected");

  			stringForCOmbp.append( " >"+LanguageUtil.getString("Select",(String)session.getAttribute("userLanguage"))+"</option>");
  			Iterator it1=info1.getKeySetIterator();
  			StringBuffer mainString = new StringBuffer();
  			boolean flag = true;

  			while(it1.hasNext())
  			{
  				String key=(String)it1.next();
  				stringForCOmbp.append("<option value='");
  				stringForCOmbp.append(key+"' ");

  				if (selectedValue.equals(key))
  				{
  					stringForCOmbp.append("selected");
  				}

  				stringForCOmbp.append(" >");
  				stringForCOmbp.append(info1.get(key));
  				stringForCOmbp.append("</option>");
  			}

  			stringForCOmbp.append("</select>");

  			if (selectedValue.equals("612"))
  				mainString.append("<table cellspacing='0' id="+id+" cellpadding='0'><tr><td>"+stringForCOmbp.toString()+"</td></tr><tr><td ><span id='"+spanName+"' name='"+spanName+"' style='display:inline;'><input type='text' name='"+textFieldName+"' id='"+textFieldName+"' size=25 class='textbox' value='"+valueforTextField+"'></span></td></tr></table>");
  			else
  				mainString.append("<table cellspacing='0' id="+id+" cellpadding='0'><tr><td>"+stringForCOmbp.toString()+"</td></tr><tr><td ><span id='"+spanName+"' name='"+spanName+"' style='display:none;'><input type='text' name='"+textFieldName+"' id='"+textFieldName+"' size=25 class='textbox' value=''></span></td></tr></table>");
  			//return mainString.toString();
  			builderDetailsInfo.set("displayName",fld.getDisplayName());
  			builderDetailsInfo.set("type", "htmlcode");
  			if(fld.getColspan().equals("2")) {
  				builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  			}
  			builderDetailsInfo.set("addhtml", mainString.toString());
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			logger.error("Exception in getPeriodCombo in DataCollector::"+e);
  		}
  	}
  	public static void setHiddenFields(Info builderInfo,String value,String name)
	{
		Info info1 = null;
		info1=new Info();
		if(StringUtil.isValid(value))
			info1.set("value",value);
		info1.set("type","hidden");
		builderInfo.set(name,info1);
	}
  	public static void getHtmlRenewalOptionString(Info builderDetailsInfo,Field fld,Info totalInfo)
  	{
  		HttpSession session = StrutsUtil.getHttpServletRequest().getSession();
  		String renewalOption=totalInfo.get(FieldNames.RENEWAL_OPTIONS);
  		String consecutive=totalInfo.get(FieldNames.CONSECUTIVE);
  		if(!StringUtil.isValidNew(renewalOption)) renewalOption = "";
  		if(!StringUtil.isValidNew(consecutive)) consecutive = "";


  		StringBuffer htmlCode = new StringBuffer();
  		htmlCode.append("<table width=\"40%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\"><tr>");
  		htmlCode.append("<td class =\"dc_que\"><input class=\"fTextBox\" type=\"text\" name=\"renewalOptions\" value=\"");
  		htmlCode.append(renewalOption+"\" size=\"8\"></td>");
  		htmlCode.append("<td class =\"dc_que\">"+LanguageUtil.getString("Consecutive",(String)session.getAttribute("userLanguage"))+"</td>");
  		htmlCode.append("<td class =\"dc_que\"><input type=\"text\" class=\"fTextBox\" name=\"consecutive\" value=\"");
  		htmlCode.append(consecutive+"\" size=\"8\"></td>");
  		htmlCode.append("<td class =\"dc_que\">"+LanguageUtil.getString("Years term",(String)session.getAttribute("userLanguage"))+"</td>");
  		htmlCode.append("</tr></table>");
  		builderDetailsInfo.set("displayName",fld.getDisplayName());
  		builderDetailsInfo.set("type", "htmlcode");
  		if(fld.getColspan().equals("2")) {
  			builderDetailsInfo.set(BuilderFormFieldNames.COL_SPAN, "2");
  		}
  		builderDetailsInfo.set("addhtml", htmlCode.toString());
  	}
  	//P_Enh_Mu-Entity_FormGenerator ends
  	
  	
  	
 	 public static Combo getCMOpportunityStageCombo(Field fld,Boolean isModify) {

      	String dataType 	= fld.getComboSourceMethodArgs();
      	boolean multiple = false;
      	if(dataType.indexOf(",multiple")!=-1) {
      		dataType = dataType.replace(",multiple", "");
      		multiple = true;
      	}
      	Info info = null;
      	StringBuffer query = new StringBuffer("SELECT * FROM CM_OPPORTUNITY_STAGES WHERE 1=1 ORDER BY CM_ORDER_NO ASC ");

      	ResultSet result = QueryUtil.getResult(query.toString(), new Object[]{});
      	try{
      		info = new Info();
      		while (result.next()) {
      			info.set(result.getString("CM_STAGE_ID"), result.getString("CM_STAGE_NAME"));
      		}
      	}catch(Exception e){
      		logger.error("\nCmLeadUtil.getCMMasterDataInfo()\t", e);
      	}finally{
      		QueryUtil.releaseResultSet(result);
      	}

      	Combo combo = new Combo(fld.getDisplayName());
      	if(multiple) {
      		combo.setMultiple();
      		combo.setSize("5");
      		combo.setJScript(" style = 'height:85px;' ");
      	}
      	combo.setInfo(info);
      	return combo;
      }
 	 
 	 public static void removeDeactivatedUser(List selectedValList) {
 		StringBuffer query = new StringBuffer("SELECT USER_NO FROM USERS WHERE STATUS=0 ");
 		ResultSet result = QueryUtil.getResult(query.toString(), new Object[]{});
      	try{
      		String str =null;
      		while (result.next()) {
      			str = result.getString("USER_NO");
      			if(selectedValList!=null && selectedValList.contains(str))
  				{
  					selectedValList.remove(str);
  				}
      		}
      	}catch(Exception e){
      		logger.error("\nremoveDeactivatedUser\t", e);
      	}finally{
      		QueryUtil.releaseResultSet(result);
      	}
 	 }
}
