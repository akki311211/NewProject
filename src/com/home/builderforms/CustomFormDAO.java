/*
 * -------------------------------------------------------------------------------------------------------------------------------
Version No.		Date		By              Against		Function Changed          Comments
--------------------------------------------------------------------------------------------------------------------------------
 P_FO_B_58866           07-06-2010      Varika Joshi     Bug            added query for association of cumtom field wid chklist.
 P_B_FIM_64232          23 Nov 2010      Neeti Solanki    Bug            changed query for hiding the unconfigured tabs
P_B_FIM_64471           10 Dec 2010      Neeti Solanki      Bug           changed query for hiding the unconfigured tabs in Admin > FIM > Customize Regional Form
P_B_FIM_64471           14 Dec 2010      Neeti Solanki     Bug             changed query for hiding the unconfigured tabs in Admin > FIM > Customize MU Form
P_B_FIM_69705           25 Jan 2011      Neeti Solanki    Bug               created method for getting subModuleNames in Admin > FIM > Customize FIM Form
P_B_FIM_69667           7 Feb 2011       Neeti Solanki    Bug           for maintaining the order of custom fields in FIM >  Export in case of regional
P_B_FIM_69758           7 Feb 2011       Neeti Solanki     Bug           for maintaining the order of custom fields in FIM >  Export in case of multi unit
P_FIM_E_CUSTOM_TAB		24Oct2011		Devendra Kant Porwal		Enh			New Custom Tab in Fim  
P_FIM_BUG_4616            22 Feb 2012                 Himanshi Gupta                Custom Section that are associated with some fields should come with * 
P_FIM_BUG_4558             19 April 2012      Himanshi Gupta                        Modified for pagging  
P_CM_B_14538               19 Oct 2012    Ravi Kumar           Bug          advance search was not working correctly over custom fields in CM
 */
package com.home.builderforms;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.sql.Date;
import java.sql.SQLException;

import oracle.jdbc.driver.Const;

import org.apache.log4j.Logger;

import java.io.File;//REFERENCE_DATE_ENH starts

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;//REFERENCE_DATE_ENH ends

import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtilHelper;
import com.home.builderforms.TableXMLDAO;//REFERENCE_DATE_ENH
import com.home.builderforms.BaseDAO;
import com.home.builderforms.FormBaseDAO;//REFERENCE_DATE_ENH
import com.home.builderforms.BuilderFormFieldNames;//REFERENCE_DATE_ENH
import com.home.builderforms.CustomFormFieldNames;
import com.home.builderforms.BuilderConstants;//REFERENCE_DATE_ENH

/**
 * The DAO for the Custom Form Table *
 * 
 * @author Onkar
 * @created March 31 . 2004
 */

public class CustomFormDAO extends BaseDAO {

	static Logger logger = Logger.getLogger(CustomFormDAO.class);

	/** cunstructor for the CustomFormDAO object */

	public CustomFormDAO() {
		this.tableAnchor = CustomFormFieldNames.FIM_CUSTOMIZATION_FORM;

	}
	//P_B_FIM_69705 added by neeti starts
	/**
	 * created by neeti for getting submodule names
	 */
	public String getSubModule()
	{
		String subModule="";
		String query = "select SUB_MODULE from USER_TAB_CONFIG where SUB_MODULE like 'franchisee%'";
		try
		{
		ResultSet result = SQLUtilHelper.getResultSet(query, new SequenceMap(),
				Constants.TENANT_NAME);
		while (result.next()) {
			subModule=result.getString("SUB_MODULE");
		}
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
		return subModule;
	}	
	//P_B_FIM_69705 added by neeti ends
	public SequenceMap getCustomFormList() throws SQLException,
			RecordNotFoundException {
		//P_B_FIM_64232 query changed by neeti solanki for hiding the unconfigured fim tabs
		//P_B_FIM_69705 added by neeti starts
		String subModule=getSubModule();
		String query = "SELECT CUSTOM_FORM_ID,FORM_NAME,TABLE_ANCHOR,CUSTOM_FIELD_NO FROM FIM_CUSTOMIZATION_FORM,USER_TAB_CONFIG WHERE USER_TAB_CONFIG.NAME=FIM_CUSTOMIZATION_FORM.FORM_NAME AND SUB_MODULE='"+subModule+"' ORDER BY FORM_NAME";
		//P_B_FIM_69705 added by neeti ends
		ResultSet result = SQLUtilHelper.getResultSet(query, new SequenceMap(),
				Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();

		while (result.next()) {
			Info info = new Info();
			info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
					.getObject("CUSTOM_FORM_ID"));
			String formName = result.getString("FORM_NAME");
			info.set(CustomFormFieldNames.FORM_NAME, formName);
			info.set(CustomFormFieldNames.TABLE_ANCHOR, result
					.getString("TABLE_ANCHOR"));
			info.set(CustomFormFieldNames.CUSTOM_FIELD_NO, (Integer) result
					.getObject("CUSTOM_FIELD_NO"));
			map.put(formName, info);
		}
		return map;
	}

	public SequenceMap getFimFormCustomFields(String formId)
			throws SQLException, RecordNotFoundException {

		String query = " SELECT * FROM FIM_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
				+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO,FM.FIELD_NO ";//P_FIM_B_1335 added FIELD_NO in order by to change the order in whcih fields appear by vikram
               ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
            		   Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();

		while (result.next()) {

			Info info = new Info();
			info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
					.getObject("FIELD_ID"));
			info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
					.getObject("CUSTOM_FORM_ID"));
			info.set(CustomFormFieldNames.DISPLAY_NAME, result
					.getString("DISPLAY_NAME"));
			info.set(CustomFormFieldNames.DATA_TYPE, result
					.getString("DATA_TYPE"));
			String label = result.getObject("FIELD_NO").toString();
			String exportable = changeFormat((Integer) result
					.getObject("EXPORTABLE"));
			String searchable = changeFormat((Integer) result
					.getObject("SEARCHABLE"));

			info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
					.getObject("FIELD_NO"));
			info.set(CustomFormFieldNames.ORDER, (Integer) result
					.getObject("ORDER_NO"));
			info.set(CustomFormFieldNames.NO_OF_ROWS, (Integer) result
					.getObject("NO_OF_ROWS"));
			info.set(CustomFormFieldNames.NO_OF_COLS, (Integer) result
					.getObject("NO_OF_COLS"));
			info.set(CustomFormFieldNames.EXPORTABLE, exportable);
			info.set(CustomFormFieldNames.SEARCHABLE, searchable);
			// info.set(CustomFormFieldNames.CUSTOM_FIELD_NO, (Integer)
			// result.getObject("CUSTOM_FIELD_NO"));

			map.put(label, info);
		}
		return map;
	}

	public SequenceMap getViewFimCustomFields(String formId, String tableName,
			String columnName, String foreignId) throws SQLException,
			RecordNotFoundException {

		String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_CUSTOMIZATION_FORM T1,FIM_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
				+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";

		String query1 = "";
		String fieldValue = "";
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
				Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();
		ResultSet result1 = null;
		while (result.next()) {

			Info info = new Info();

			String formName = result.getString("FORM_ID");
			info.set(CustomFormFieldNames.DISPLAY_NAME, result
					.getString("DISPLAY_NAME"));
			info.set(CustomFormFieldNames.DATA_TYPE, result
					.getString("DATA_TYPE"));
			String label = result.getObject("FIELD_NO").toString();
			info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
					.getObject("FIELD_NO"));
			String fieldName = tableName + "_C" + label;
			query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "
					+ columnName + "=" + foreignId;
			String inputName = formId + "_" + label;
			result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
					Constants.TENANT_NAME);

			if (result1.next() && result1.getObject(fieldName) != null) {

				fieldValue = result1.getObject(fieldName).toString();
			}

			info.set("fieldvalue", fieldValue);
			info.set("fieldName", inputName);
			info.set(CustomFormFieldNames.NO_OF_ROWS, result.getObject(
					"NO_OF_ROWS").toString());
			info.set(CustomFormFieldNames.NO_OF_COLS, result.getObject(
					"NO_OF_COLS").toString());

			map.put(label, info);
		}
		return map;
	}

	public SequenceMap getFimDisplayCustomFields(String formId,
			String tableName, String columnName, String foreignId)
			throws SQLException, RecordNotFoundException {

		String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_CUSTOMIZATION_FORM T1,FIM_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
				+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
		//System.out.println("query111>>>>>>"+query);
		String query1 = "";
		String fieldValue = "";
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
				Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();
		ResultSet result1 = null;
		if (foreignId != null && (foreignId.trim()).equals("")) {
			foreignId = null;
		}

		while (result.next()) {

			Info info = new Info();
			String formName = result.getString("FORM_ID");
			info.set(CustomFormFieldNames.DISPLAY_NAME, result
					.getString("DISPLAY_NAME"));
			info.set(CustomFormFieldNames.NO_OF_ROWS, result
					.getString("NO_OF_ROWS"));
			info.set(CustomFormFieldNames.NO_OF_COLS, result
					.getString("NO_OF_COLS"));
			info.set(CustomFormFieldNames.DATA_TYPE, result
					.getString("DATA_TYPE"));
			String label = result.getObject("FIELD_NO").toString();
			info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
					.getObject("FIELD_NO"));
			String fieldName = tableName + "_C" + label;
			query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "
					+ columnName + "=" + foreignId;
			//System.out.println("query22222>>>>>>"+query1);
			result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
					Constants.TENANT_NAME);

			if (result1.next() && result1.getObject(fieldName) != null) {

				fieldValue = result1.getObject(fieldName).toString();
			} else {
				fieldValue = "";
			}

			info.set("fieldvalue", fieldValue);
			info.set("fieldName", fieldName);

			map.put(label, info);
		}
		return map;
	}

	public String changeFormat(Integer value) {
		if (value.intValue() == 0) {
			return "No";
		} else if (value.intValue() == 1) {
			return "Yes";
		} else {
			return "";
		}

	}
	public Info getCustomFieldsForExport(String menuName, String formId, String fieldNo) throws SQLException,RecordNotFoundException {
		
		try{
			return getCustomFieldsForExport(menuName,formId,fieldNo,null);
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	//P_FIM_E_CUSTOM_TAB starts 
	/** This method gets all the custom fields for export 
	 * @param menuName
	 * @param fieldNo
	 * @return
	 * @throws SQLException
	 * @throws RecordNotFoundException
	 */
	public Info getCustomTabFieldsForExport(String menuName,  String fieldNo) throws SQLException,
	RecordNotFoundException {

//String query = "SELECT * FROM FIM_CUSTOMIZATION_FIELD FCFLD, FIM_CUSTOMIZATION_FORM FCFRM WHERE FCFLD.CUSTOM_FORM_ID = FCFRM.CUSTOM_FORM_ID AND FCFRM.FORM_ID = \""+formId+"\" AND FCFLD.FIELD_NO = "+fieldNo+" AND FCFLD.AVAILABLE=0 AND FCFLD.EXPORTABLE=1 ";
StringBuffer query = new StringBuffer("SELECT * FROM ");
if ("fim".equals(menuName)){
	query.append("FIM_CUSTOM_TAB_FIELDS FCTF");
}
query.append(" WHERE 1=1 ");
query.append("AND FCTF.AVAILABLE=0 AND FCTF.EXPORTABLE=1 ");
query.append("AND FCTF.FIELD_NO = \"").append(fieldNo).append("\" ");
ResultSet result = SQLUtilHelper.getResultSet(query.toString(), new Object[] {},
		Constants.TENANT_NAME);
Info info = null;
while (result.next()) {

	info = new Info();

	info.set(CustomFormFieldNames.DISPLAY_NAME, result
			.getString("DISPLAY_NAME"));
	info.set(CustomFormFieldNames.DATA_TYPE, result
			.getString("DATA_TYPE"));
	info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
			.getObject("FIELD_NO"));

	info.set(CustomFormFieldNames.NO_OF_ROWS, result.getObject(
			"NO_OF_ROWS").toString());
	info.set(CustomFormFieldNames.NO_OF_COLS, result.getObject(
			"NO_OF_COLS").toString());
}
return info;
}
	//P_FIM_E_CUSTOM_TAB
	public Info getCustomFieldsForExport(String menuName, String formId, String fieldNo,String fimFlag) throws SQLException,RecordNotFoundException {
		
//String query = "SELECT * FROM FIM_CUSTOMIZATION_FIELD FCFLD, FIM_CUSTOMIZATION_FORM FCFRM WHERE FCFLD.CUSTOM_FORM_ID = FCFRM.CUSTOM_FORM_ID AND FCFRM.FORM_ID = \""+formId+"\" AND FCFLD.FIELD_NO = "+fieldNo+" AND FCFLD.AVAILABLE=0 AND FCFLD.EXPORTABLE=1 ";
		Info info = null;
		if("fim".equals(menuName) || "cm".equals(menuName) || "fs".equals(menuName)){
		StringBuffer query = new StringBuffer("SELECT * FROM ");

if(fimFlag==null || "".equals(fimFlag) || "null".equals(fimFlag))
{
	fimFlag="0";
}

if ("fim".equals(menuName) && fimFlag.equals("0")){
	query.append("FIM_CUSTOMIZATION_FIELD FCFLD, FIM_CUSTOMIZATION_FORM FCFRM ");
}else if("fim".equals(menuName) && fimFlag.equals("1")){
	query.append("FIM_AREA_CUSTOMIZATION_FIELD FCFLD, FIM_AREA_CUSTOMIZATION_FORM FCFRM ");
}
else if("fim".equals(menuName) && fimFlag.equals("2")){
	query.append("FIM_MU_CUSTOMIZATION_FIELD FCFLD, FIM_MU_CUSTOMIZATION_FORM FCFRM ");
}
else if("cm".equals(menuName)){
	query.append("CM_CUSTOMIZATION_FIELD FCFLD, CM_CUSTOMIZATION_FORM FCFRM ");	
}else if("fs".equals(menuName)){
	query.append("FS_CUSTOMIZATION_FIELD FCFLD, FS_CUSTOMIZATION_FORM FCFRM ");	
}

query.append("WHERE FCFLD.CUSTOM_FORM_ID = FCFRM.CUSTOM_FORM_ID ");
query.append("AND FCFLD.AVAILABLE=0 AND FCFLD.EXPORTABLE=1 ");
query.append("AND FCFRM.FORM_ID = \"").append(formId).append("\" ");
query.append("AND FCFLD.FIELD_NO = \"").append(fieldNo).append("\" ");//P_FIM_B_1335 changed field_no to order_no to change the order in whcih fields appear by vikram

//P_CM_B_14538 by Ravi Kumar for search over custom fields
/*if("cm".equals(menuName)){
	query.append("AND FCFLD.FIELD_NO = \"").append(fieldNo).append("\" ");
}
else{
query.append("AND FCFLD.ORDER_NO = \"").append(fieldNo).append("\" ");//P_FIM_B_1335 changed field_no to order_no to change the order in whcih fields appear by vikram
}*/
//P_CM_B_14538 by Ravi Kumar for search over custom fields

ResultSet result = SQLUtilHelper.getResultSet(query.toString(), new Object[] {},
		Constants.TENANT_NAME);

while (result.next()) {
	
	info = new Info();
	
	info.set(CustomFormFieldNames.DISPLAY_NAME, result
			.getString("DISPLAY_NAME"));
	info.set(CustomFormFieldNames.DATA_TYPE, result
			.getString("DATA_TYPE"));
	info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
			.getObject("FIELD_NO"));
	
	info.set(CustomFormFieldNames.NO_OF_ROWS, result.getObject(
			"NO_OF_ROWS").toString());
	info.set(CustomFormFieldNames.NO_OF_COLS, result.getObject(
			"NO_OF_COLS").toString());
}
}
return info;
}

	
	
	
public SequenceMap getFimContactFormCustomFields(String formId)
	throws SQLException, RecordNotFoundException {

		String query = " SELECT * FROM FIM_CONTACT_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
				+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO ";
		//System.out.println("query.........>"+query);
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
				Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();
		
		while (result.next()) {
		
			Info info = new Info();
			info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
					.getObject("FIELD_ID"));
			info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
					.getObject("CUSTOM_FORM_ID"));
			info.set(CustomFormFieldNames.DISPLAY_NAME, result
					.getString("DISPLAY_NAME"));
			info.set(CustomFormFieldNames.DATA_TYPE, result
					.getString("DATA_TYPE"));
			String label = result.getObject("FIELD_NO").toString();
			
			info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
					.getObject("FIELD_NO"));
			info.set(CustomFormFieldNames.ORDER, (Integer) result
					.getObject("ORDER_NO"));
			info.set(CustomFormFieldNames.NO_OF_ROWS, (Integer) result
					.getObject("NO_OF_ROWS"));
			info.set(CustomFormFieldNames.NO_OF_COLS, (Integer) result
					.getObject("NO_OF_COLS"));
			map.put(label, info);
		}
		return map;
}

public SequenceMap getFimContactDisplayCustomFields(String formId,
		String tableName, String columnName, String foreignId)
		throws SQLException, RecordNotFoundException {

	String query = "SELECT T2.FIELD_ID,T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_CONTACT_CUSTOMIZATION_FORM T1,FIM_CONTACT_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
			+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
	String query1 = "";
	String fieldValue = "";
	ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
			Constants.TENANT_NAME);
	SequenceMap map = new SequenceMap();
	ResultSet result1 = null;
	if (foreignId != null && (foreignId.trim()).equals("")) {
		foreignId = null;
	}

	while (result.next()) {

		Info info = new Info();
		String formName = result.getString("FORM_ID");
		info.set(CustomFormFieldNames.FIELD_ID, result
				.getString("FIELD_ID"));
		info.set(CustomFormFieldNames.DISPLAY_NAME, result
				.getString("DISPLAY_NAME"));
		info.set(CustomFormFieldNames.NO_OF_ROWS, result
				.getString("NO_OF_ROWS"));
		info.set(CustomFormFieldNames.NO_OF_COLS, result
				.getString("NO_OF_COLS"));
		info.set(CustomFormFieldNames.DATA_TYPE, result
				.getString("DATA_TYPE"));
		String label = result.getObject("FIELD_NO").toString();
		info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
				.getObject("FIELD_NO"));
		String fieldName = tableName + "_S" + label;
		query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "
				+ columnName + "=" + foreignId;
		result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
				Constants.TENANT_NAME);

		if (result1.next() && result1.getObject(fieldName) != null) {

			fieldValue = result1.getObject(fieldName).toString();
		} else {
			fieldValue = "";
		}

		info.set("fieldvalue", fieldValue);
		info.set("fieldName", fieldName);

		map.put(label, info);
	}
	return map;
}




public Info getFimContactCustomField(String fieldId,String tableName)
		throws SQLException, RecordNotFoundException {

	String query = "SELECT T1.FORM_ID ,T2.FIELD_ID, T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_CONTACT_CUSTOMIZATION_FORM T1,FIM_CONTACT_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
			+ tableName + "' AND T2.AVAILABLE='0' AND T2.FIELD_NO = "+fieldId;
	//System.out.println("query111>>>>>>"+query);
	Info info = new Info();
	
	try{
	
	ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
			Constants.TENANT_NAME);
	
		
	if(result.next()) {

		
		String formName = result.getString("FORM_ID");
		info.set(CustomFormFieldNames.DISPLAY_NAME, result
				.getString("DISPLAY_NAME"));
		info.set(CustomFormFieldNames.FIELD_ID, result
				.getString("FIELD_ID"));
		info.set(CustomFormFieldNames.NO_OF_ROWS, result
				.getString("NO_OF_ROWS"));
		info.set(CustomFormFieldNames.NO_OF_COLS, result
				.getString("NO_OF_COLS"));
		info.set(CustomFormFieldNames.DATA_TYPE, result
				.getString("DATA_TYPE"));
		String label = result.getObject("FIELD_NO").toString();
		info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
				.getObject("FIELD_NO"));
		
		
		
	}
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("Exception in getFimContactCustomField");
		
	}
	
	return info;
}




public boolean getFieldIdsfromFimContactCustomizationField(String contact_id)
		throws SQLException, RecordNotFoundException {

	
	String query = "SELECT FIELD_ID FIELD_ID FROM FIM_CONTACT_CUSTOMIZATION_FIELD ";
	//System.out.println("query111>>>>>>"+query);
    String fieldsID  =  "";
    boolean  flag  = false;
	
	try{
	ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
			Constants.TENANT_NAME);
	while(result.next()) {
		fieldsID   =  result.getString("FIELD_ID");
		if(fieldsID.equals(contact_id))
			flag = true;
			
	}
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("Exception in getFimContactCustomField");
	}
	return flag;
}

	public Info getFimContactCustomFieldByFieldId(String fieldId,String tableName,String columnName,String foreignId)
		throws SQLException, RecordNotFoundException {
		
		fieldId = fieldId.trim();
		
		String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_CONTACT_CUSTOMIZATION_FORM T1,FIM_CONTACT_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
			+ tableName + "' AND T2.AVAILABLE='0' AND T2.FIELD_ID = "+fieldId;
		Info info = new Info();
		
		try{
		
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
				Constants.TENANT_NAME);
		
		
		if(result.next()) {
		
		
		String formName = result.getString("FORM_ID");
		info.set(CustomFormFieldNames.DISPLAY_NAME, result
				.getString("DISPLAY_NAME"));
		info.set(CustomFormFieldNames.NO_OF_ROWS, result
				.getString("NO_OF_ROWS"));
		info.set(CustomFormFieldNames.NO_OF_COLS, result
				.getString("NO_OF_COLS"));
		info.set(CustomFormFieldNames.DATA_TYPE, result
				.getString("DATA_TYPE"));
		String label = result.getObject("FIELD_NO").toString();
		info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
				.getObject("FIELD_NO"));
		String fieldName = tableName + "_S" + label;
		
		String fieldValue = "";
		String query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "
		+ columnName + "=" + foreignId;
			ResultSet result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
					Constants.TENANT_NAME);
			
			if (result1.next() && result1.getObject(fieldName) != null) {
			
				fieldValue = result1.getObject(fieldName).toString();
			} else {
				fieldValue = "";
			}
			
			info.set("fieldvalue", fieldValue);
			info.set("fieldName", fieldName);
					
		
		}
		}catch(Exception e){
		e.printStackTrace();
		System.out.println("Exception in getFieldIdsfromFimContactCustomizationField");
		
		}
		
		return info;
		}
	
	
	
	
	public SequenceMap getFimContactFormCustomFieldsInfo(String formId)
	throws SQLException, RecordNotFoundException {

		String query = " SELECT * FROM FIM_CONTACT_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
				+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO ";
		
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
				Constants.TENANT_NAME);
		String query1 = "";
		int	count =  0;
		String associated  = "No";
		SequenceMap map = new SequenceMap();
		
		while (result.next()) {
		
			Info info = new Info();
			info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
					.getObject("FIELD_ID"));
			info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
					.getObject("CUSTOM_FORM_ID"));
			info.set(CustomFormFieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
			info.set(CustomFormFieldNames.DATA_TYPE, result
					.getString("DATA_TYPE"));
			String label = result.getObject("FIELD_NO").toString();
			
			info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
					.getObject("FIELD_NO"));
			info.set(CustomFormFieldNames.ORDER, (Integer) result
					.getObject("ORDER_NO"));
			info.set(CustomFormFieldNames.NO_OF_ROWS, (Integer) result
					.getObject("NO_OF_ROWS"));
			info.set(CustomFormFieldNames.NO_OF_COLS, (Integer) result
					.getObject("NO_OF_COLS"));
			
			
			query1  =  "  SELECT COUNT(FRANCHISEE_NAME) COUNT FROM FRANCHISEE WHERE IS_STORE_ARCHIVED='N' AND IS_STORE ='Y'AND (FRANCHISEE_S"+label+" IS NOT NULL OR FRANCHISEE_S"+label+" != '') ";
			 ResultSet rs1 = null;
			 try
			 {
				rs1= QueryUtil.getResult(query1, new Object[]{});
			 
			 	if(rs1.next()){			    			
			 		count = Integer.parseInt(rs1.getString("COUNT"));
			 	}
                        //P_FO_B_58866 starts
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_TASK_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_EQUIPMENT_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_DOCUMENT_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_PICTURE_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_SECONDRY_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_DOCUMENT_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_EQUIPMENT_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_PICTURE_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_SECONDRY_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
                         
                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_TASK_CHECKLIST WHERE IS_DELETED = 'N' AND CONTACT LIKE '%-"+label+"S%'";
			 rs1 = QueryUtil.getResult(query1, new Object[]{});
                         if(rs1.next()){			    			
                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
                         }
			 }
			 catch (Exception e) {
				// TODO: handle exception
			}
			 finally
				{
					QueryUtil.releaseResultSet(rs1);
				}

			 //P_FO_B_58866 ends
                         if(count >0)
                         {
				 associated  = "Yes";
                         }
			 else
                         {
				 associated  = "No";
                         } 
			 	
			info.set("associated",associated);
				
			 	
			map.put(label, info);
		}
		return map;
}
	public void checkFsCustomField(String formID,String formNames)
    {

            String query = "SELECT FIELD_ID FROM FS_CUSTOMIZATION_FIELD WHERE CUSTOM_FORM_ID="+formID;
          ResultSet result=null;
            try
            {
                     result =QueryUtil.getResult(query,null);
                    if(!result.next())
                    {
                            for(int i=1;i<=10;i++)
                            {
                                    query="INSERT INTO FS_CUSTOMIZATION_FIELD(CUSTOM_FORM_ID,FIELD_NO,ORDER_NO,NO_OF_ROWS,NO_OF_COLS,EXPORTABLE,SEARCHABLE,AVAILABLE,FORM_NAME) VALUES(?,?,?,?,?,?,?,?,?)";
                                    QueryUtil.update(query,new String[]{formID,i+"","100","4","35","0","0","1",formNames});
                            }
                    }
            }
            catch(Exception e)
            {
                    logger.error("Exception in checkFsCustomField",e);
            }
            finally
    		{
    			QueryUtil.releaseResultSet(result);
    		}

    }
	 public SequenceMap getFsFormCustomFields(String formId) {
         String query = "SELECT FM.*,FCF.FORM_ID FROM FS_CUSTOMIZATION_FIELD FM LEFT JOIN FS_CUSTOMIZATION_FORM FCF ON FM.CUSTOM_FORM_ID=FCF.CUSTOM_FORM_ID WHERE FM.CUSTOM_FORM_ID = "+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO ASC ,FM.AVAILABLE";
         ResultSet result = QueryUtil.getResult(query,null);
         SequenceMap map = new SequenceMap();
         int i=0;
         while (result.next()) {
                 i++;
                 Info info = new Info();
                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
                 info.set(FieldNames.CUSTOM_FORM_ID,result.getString("CUSTOM_FORM_ID"));
                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
                 info.set(FieldNames.DATA_TYPE, LanguageUtil.getString(result.getString("DATA_TYPE")));
                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
                 info.set(FieldNames.ORDER_NO,result.getString("ORDER_NO"));
                 info.set(FieldNames.NO_OF_ROWS,result.getString("NO_OF_ROWS"));
                 info.set(FieldNames.NO_OF_COLS,result.getString("NO_OF_COLS"));
                 info.set(FieldNames.EXPORTABLE, changeFormat((Integer) result.getObject("EXPORTABLE")));
                 info.set(FieldNames.SEARCHABLE, changeFormat((Integer) result.getObject("SEARCHABLE")));
                 info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
                 info.set(FieldNames.FORM_ID, result.getString("FORM_ID"));
                 info.set(FieldNames.LABEL_NAME, i+"");
                 map.put(result.getString("FIELD_ID"), info);
         }
         QueryUtil.releaseResultSet(result);
         return map;
	 }
	 
	 public SequenceMap getFsDisplayCustomFields(String tableName)
     {
             SequenceMap map = new SequenceMap();
             ResultSet result=null;
             try
             {
             String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FS_CUSTOMIZATION_FORM T1,FS_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
              result =QueryUtil.getResult(query,null);

             while (result.next()) {

                     Info info = new Info();
                     info.set(CustomFormFieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
                     info.set(CustomFormFieldNames.NO_OF_ROWS, result.getString("NO_OF_ROWS"));
                     info.set(CustomFormFieldNames.NO_OF_COLS, result.getString("NO_OF_COLS"));
                     info.set(CustomFormFieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
                     String label = result.getObject("FIELD_NO").toString();
                     info.set(CustomFormFieldNames.FIELD_NO, (Integer) result.getObject("FIELD_NO"));
                     String fieldName = "FS_CUSTOM_FIELD_C" + label;
                     info.set("fieldName", fieldName);

                     map.put(label, info);
             }
             }catch (Exception e)
             {
                     e.printStackTrace();
             }
             finally
     		{
     			QueryUtil.releaseResultSet(result);
     		}

             return map;
     }

	 //added by pritam 
	 public SequenceMap getCMDisplayCustomFields(String tableName)
     {
             SequenceMap map = new SequenceMap();
             ResultSet result=null;
             try
             {
             String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM CM_CUSTOMIZATION_FORM T1,CM_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
              result =QueryUtil.getResult(query,null);

             while (result.next()) {

                     Info info = new Info();
                     info.set(CustomFormFieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
                     info.set(CustomFormFieldNames.NO_OF_ROWS, result.getString("NO_OF_ROWS"));
                     info.set(CustomFormFieldNames.NO_OF_COLS, result.getString("NO_OF_COLS"));
                     info.set(CustomFormFieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
                     String label = result.getObject("FIELD_NO").toString();
                     info.set(CustomFormFieldNames.FIELD_NO, (Integer) result.getObject("FIELD_NO"));
                     String fieldName = "CM_CONTACT_DETAILS_C" + label;
                     info.set("fieldName", fieldName);

                     map.put(label, info);
             }
             }catch (Exception e)
             {
                     e.printStackTrace();
             }
             finally
     		{
     			QueryUtil.releaseResultSet(result);
     		}

             return map;
     }
	 
	 
	 
	 
	 
	 public SequenceMap getAreaCustomFormList() throws SQLException,RecordNotFoundException {

		//P_B_FIM_64471 added by neeti starts
		//String query = "SELECT * FROM FIM_AREA_CUSTOMIZATION_FORM ORDER BY FORM_NAME";
		 String query="SELECT CUSTOM_FORM_ID,FORM_NAME,TABLE_ANCHOR,CUSTOM_FIELD_NO FROM FIM_AREA_CUSTOMIZATION_FORM,USER_TAB_CONFIG WHERE USER_TAB_CONFIG.NAME=FIM_AREA_CUSTOMIZATION_FORM.FORM_NAME AND SUB_MODULE='area' ORDER BY FORM_NAME";
		//P_B_FIM_64471 added by neeti ends
		 ResultSet result = SQLUtilHelper.getResultSet(query, new SequenceMap(),
				 Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();
	
		while (result.next()) {
			Info info = new Info();
			info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
					.getObject("CUSTOM_FORM_ID"));
			String formName = result.getString("FORM_NAME");
			info.set(CustomFormFieldNames.FORM_NAME, formName);
			info.set(CustomFormFieldNames.TABLE_ANCHOR, result
					.getString("TABLE_ANCHOR"));
			info.set(CustomFormFieldNames.CUSTOM_FIELD_NO, (Integer) result
					.getObject("CUSTOM_FIELD_NO"));
			map.put(formName, info);
		}
		return map;
	}
	 // add by alok
	 public SequenceMap getMUCustomFormList() throws SQLException,RecordNotFoundException {
		    //P_B_FIM_64471 modified by neeti starts
		    String query = "SELECT CUSTOM_FORM_ID,FORM_NAME,TABLE_ANCHOR,CUSTOM_FIELD_NO FROM FIM_MU_CUSTOMIZATION_FORM,USER_TAB_CONFIG WHERE USER_TAB_CONFIG.NAME=FIM_MU_CUSTOMIZATION_FORM.FORM_NAME AND SUB_MODULE='mu' ORDER BY FORM_NAME";		 
			//String query = "SELECT * FROM FIM_MU_CUSTOMIZATION_FORM ORDER BY FORM_NAME";
			//P_B_FIM_64471 modified by neeti ends
		    ResultSet result = SQLUtilHelper.getResultSet(query, new SequenceMap(),
		    		Constants.TENANT_NAME);
			SequenceMap map = new SequenceMap();
		
			while (result.next()) {
				Info info = new Info();
				info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
						.getObject("CUSTOM_FORM_ID"));
				String formName = result.getString("FORM_NAME");
				info.set(CustomFormFieldNames.FORM_NAME, formName);
				info.set(CustomFormFieldNames.TABLE_ANCHOR, result
						.getString("TABLE_ANCHOR"));
				info.set(CustomFormFieldNames.CUSTOM_FIELD_NO, (Integer) result
						.getObject("CUSTOM_FIELD_NO"));
				map.put(formName, info);
			}
			return map;
		}
	 // by alok mu
	 public SequenceMap getMUFimFormCustomFields(String formId)throws SQLException, RecordNotFoundException {
		 	//P_B_FIM_69758 modified by neeti starts
			/*String query = " SELECT * FROM FIM_MU_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
					+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO ";*/
			String query = " SELECT * FROM FIM_MU_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
				+ Integer.parseInt(formId) + " ORDER BY FM.FIELD_ID ";
			//P_B_FIM_69758 modified by neeti ends
			ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
					Constants.TENANT_NAME);
			SequenceMap map = new SequenceMap();

			while (result.next()) {

				Info info = new Info();
				info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
						.getObject("FIELD_ID"));
				info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
						.getObject("CUSTOM_FORM_ID"));
				info.set(CustomFormFieldNames.DISPLAY_NAME, result
						.getString("DISPLAY_NAME"));
				info.set(CustomFormFieldNames.DATA_TYPE, result
						.getString("DATA_TYPE"));
				String label = result.getObject("FIELD_NO").toString();
				String exportable = changeFormat((Integer) result
						.getObject("EXPORTABLE"));
				String searchable = changeFormat((Integer) result
						.getObject("SEARCHABLE"));

				info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
						.getObject("FIELD_NO"));
				info.set(CustomFormFieldNames.ORDER, (Integer) result
						.getObject("ORDER_NO"));
				info.set(CustomFormFieldNames.NO_OF_ROWS, (Integer) result
						.getObject("NO_OF_ROWS"));
				info.set(CustomFormFieldNames.NO_OF_COLS, (Integer) result
						.getObject("NO_OF_COLS"));
				info.set(CustomFormFieldNames.EXPORTABLE, exportable);
				info.set(CustomFormFieldNames.SEARCHABLE, searchable);
			
				map.put(label, info);
			}
			return map;
		}
	 
	

	 public SequenceMap getAreaFimFormCustomFields(String formId)throws SQLException, RecordNotFoundException {
//P_B_FIM_69667  modified by neeti starts
/*	String query = " SELECT * FROM FIM_AREA_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
		+ Integer.parseInt(formId) + " ORDER BY FM.ORDER_NO ";*/
		 String query = " SELECT * FROM FIM_AREA_CUSTOMIZATION_FIELD AS FM WHERE CUSTOM_FORM_ID = "
				+ Integer.parseInt(formId) + " ORDER BY FM.FIELD_ID ";
//P_B_FIM_69667 modified by neeti ends
	ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
			Constants.TENANT_NAME);
	SequenceMap map = new SequenceMap();

	while (result.next()) {

		Info info = new Info();
		info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
				.getObject("FIELD_ID"));
		info.set(CustomFormFieldNames.CUSTOM_FORM_ID, (Integer) result
				.getObject("CUSTOM_FORM_ID"));
		info.set(CustomFormFieldNames.DISPLAY_NAME, result
				.getString("DISPLAY_NAME"));
		info.set(CustomFormFieldNames.DATA_TYPE, result
				.getString("DATA_TYPE"));
		String label = result.getObject("FIELD_NO").toString();
		String exportable = changeFormat((Integer) result
				.getObject("EXPORTABLE"));
		String searchable = changeFormat((Integer) result
				.getObject("SEARCHABLE"));

		info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
				.getObject("FIELD_NO"));
		info.set(CustomFormFieldNames.ORDER, (Integer) result
				.getObject("ORDER_NO"));
		info.set(CustomFormFieldNames.NO_OF_ROWS, (Integer) result
				.getObject("NO_OF_ROWS"));
		info.set(CustomFormFieldNames.NO_OF_COLS, (Integer) result
				.getObject("NO_OF_COLS"));
		info.set(CustomFormFieldNames.EXPORTABLE, exportable);
		info.set(CustomFormFieldNames.SEARCHABLE, searchable);
	
		map.put(label, info);
	}
	return map;
}

	 
	 
	 public SequenceMap getAreaDisplayCustomFields(String formId,
				String tableName, String columnName, String foreignId)
				throws SQLException, RecordNotFoundException {

			String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_AREA_CUSTOMIZATION_FORM T1,FIM_AREA_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
					+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
			String query1 = "";
			String fieldValue = "";
			ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
					Constants.TENANT_NAME);
			SequenceMap map = new SequenceMap();
			ResultSet result1 = null;
			if (foreignId != null && (foreignId.trim()).equals("")) {
				foreignId = null;
			}

			while (result.next()) {

				Info info = new Info();
				String formName = result.getString("FORM_ID");
				info.set(CustomFormFieldNames.DISPLAY_NAME, result
						.getString("DISPLAY_NAME"));
				info.set(CustomFormFieldNames.NO_OF_ROWS, result
						.getString("NO_OF_ROWS"));
				info.set(CustomFormFieldNames.NO_OF_COLS, result
						.getString("NO_OF_COLS"));
				info.set(CustomFormFieldNames.DATA_TYPE, result
						.getString("DATA_TYPE"));
				String label = result.getObject("FIELD_NO").toString();
				info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
						.getObject("FIELD_NO"));
				String fieldName = tableName + "_C" + label;
				query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "+ columnName + "=" + foreignId;
				result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},Constants.TENANT_NAME);

				if (result1.next() && result1.getObject(fieldName) != null) {

					fieldValue = result1.getObject(fieldName).toString();
				} else {
					fieldValue = "";
				}
				//System.out.println("query"+query);
				//System.out.println("query1"+query1);
				info.set("fieldvalue", fieldValue);
				info.set("fieldName", fieldName);

				map.put(label, info);
			}
			return map;
		}
	 
	 
	 // FOR  ADDING CUSTOMIZE FIELD IN FIM (MULTI UNIT FRACHISEE) ALOK
	 public SequenceMap getMUDisplayCustomFields(String formId,
				String tableName, String columnName, String foreignId)
				throws SQLException, RecordNotFoundException {

			String query = "SELECT T1.FORM_ID , T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE ,T2.NO_OF_ROWS ,T2.NO_OF_COLS FROM FIM_MU_CUSTOMIZATION_FORM T1,FIM_MU_CUSTOMIZATION_FIELD T2 WHERE T1.CUSTOM_FORM_ID=T2.CUSTOM_FORM_ID AND T1.FORM_ID='"
					+ tableName + "' and T2.AVAILABLE='0' ORDER BY T2.ORDER_NO";
			String query1 = "";
			String fieldValue = "";
			ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
					Constants.TENANT_NAME);
			SequenceMap map = new SequenceMap();
			ResultSet result1 = null;
			if (foreignId != null && (foreignId.trim()).equals("")) {
				foreignId = null;
			}

			while (result.next()) {

				Info info = new Info();
				String formName = result.getString("FORM_ID");
				info.set(CustomFormFieldNames.DISPLAY_NAME, result
						.getString("DISPLAY_NAME"));
				info.set(CustomFormFieldNames.NO_OF_ROWS, result
						.getString("NO_OF_ROWS"));
				info.set(CustomFormFieldNames.NO_OF_COLS, result
						.getString("NO_OF_COLS"));
				info.set(CustomFormFieldNames.DATA_TYPE, result
						.getString("DATA_TYPE"));
				String label = result.getObject("FIELD_NO").toString();
				info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
						.getObject("FIELD_NO"));
				String fieldName = tableName + "_C" + label;
				query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "+ columnName + "=" + foreignId;
				
				result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},Constants.TENANT_NAME);

				if (result1.next() && result1.getObject(fieldName) != null) {

					fieldValue = result1.getObject(fieldName).toString();
				} else {
					fieldValue = "";
				}
				//System.out.println("query"+query);
				//System.out.println("query1"+query1);
				info.set("fieldvalue", fieldValue);
				info.set("fieldName", fieldName);

				map.put(label, info);
			}
			return map;
		}//END   MUDisplayCustomFields   method
	//P_FIM_E_CUSTOM_TAB 
		 /**
		 * @return
		 */
		public SequenceMap getFimCustomTabFields() {
	         String query = "SELECT * FROM FIM_CUSTOM_TAB_FIELDS  ORDER BY AVAILABLE,ORDER_NO,FIELD_NO";
	         ResultSet result = QueryUtil.getResult(query,null);
	         SequenceMap map = new SequenceMap();
	         int i=0;
	         while (result.next()) {
	                 i++;
	                 Info info = new Info();
	                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
	                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
	                 info.set(FieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
	                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
	                 info.set(FieldNames.ORDER_NO,result.getString("ORDER_NO"));
	                 info.set(FieldNames.FIELD_LENGTH,result.getString("FIELD_LENGTH"));
	                 info.set(FieldNames.VISIBLE,changeFormat((Integer) result.getObject("VISIBLE")));
	                 info.set(FieldNames.EXPORTABLE, changeFormat((Integer) result.getObject("EXPORTABLE")));
	                 info.set(FieldNames.SEARCHABLE, changeFormat((Integer) result.getObject("SEARCHABLE")));
	                 info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
	                 info.set(FieldNames.LABEL_NAME, i+"");
	                 map.put(result.getString("FIELD_ID"), info);
	         }
	        
	 			QueryUtil.releaseResultSet(result);
	 		

	         return map;
		 }
		
		//P_FIM_BUG_4558 starts
		
		/**
		 * This method returns the count of CustomTab fields.It is introduced to add paging on page.
		 * @return
		 */
		 public String getFimCustomTabFieldsTotalRecords() {
			 String queryCount = "SELECT COUNT(FIELD_ID) FROM FIM_CUSTOM_TAB_FIELDS  ORDER BY AVAILABLE,ORDER_NO,FIELD_NO";//by himanshi
			  ResultSet resultCount	= QueryUtil.getResult(queryCount.toString(), new Object[]{});
		 		String totalRecords="";
		         if(resultCount.next()) {
		 			totalRecords = resultCount.getString(1);
		 		}
		         QueryUtil.releaseResultSet(resultCount);
		         return totalRecords;
		 }
		 
		
		 /**
		  * It is introduced to add paging on page.Returns the map of custom tab fields.
		  * @param flag
		  * @param pageId
		  * @return
		  */
			public SequenceMap getFimCustomTabFields(String flag,String pageId) {
				StringBuffer query	=	new StringBuffer("SELECT * FROM FIM_CUSTOM_TAB_FIELDS  ORDER BY AVAILABLE,ORDER_NO,FIELD_NO");
		         
		        
		         int limit 	= 20;
		         int page=0;
		 		if(pageId != null && !pageId.equals("") && !pageId.equals("null") && !pageId.equals("0")){
		 			page	= Integer.parseInt(pageId);
		 			int offset	= (page-1)*20;
		 			query.append(" LIMIT " + offset);
		 			query.append( " ," + limit);
		 		}else if(pageId != null && pageId.equals("0")){
		 			
		 		}else{
		 			query.append(" LIMIT 0 , "+limit);
		 		}
		        
		 		
		 		ResultSet result	= QueryUtil.getResult(query.toString(), null);
		 	
		         SequenceMap map = new SequenceMap();
		         int i=0;
		         while (result.next()) {
		                 i++;
		                 Info info = new Info();
		                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
		                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
		                 info.set(FieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
		                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
		                 info.set(FieldNames.ORDER_NO,result.getString("ORDER_NO"));
		                 info.set(FieldNames.FIELD_LENGTH,result.getString("FIELD_LENGTH"));
		                 info.set(FieldNames.VISIBLE,changeFormat((Integer) result.getObject("VISIBLE")));
		                 info.set(FieldNames.EXPORTABLE, changeFormat((Integer) result.getObject("EXPORTABLE")));
		                 info.set(FieldNames.SEARCHABLE, changeFormat((Integer) result.getObject("SEARCHABLE")));
		                 info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
		                 if(page==0){
		                	 info.set(FieldNames.LABEL_NAME,i+"");
		                 }else{
		                 info.set(FieldNames.LABEL_NAME, (((page-1)*20)+i)+"");
		                 }
		                 map.put(result.getString("FIELD_ID"), info);
		         }
		         QueryUtil.releaseResultSet(result);
		         return map;
			 }
		
		//P_FIM_BUG_4558 ends
		 
		
		 /**
		 * @param sectionID
		 * @return
		 */
		public SequenceMap getFimCustomTabAvailableFields(String sectionID) {
	         String query = "SELECT * FROM FIM_CUSTOM_TAB_FIELDS  WHERE AVAILABLE=0 ";
	         if(StringUtil.isValid(sectionID))
	         {
	        	 query+=" AND SECTION_ID = '"+sectionID+"'";
	         }
	         query+=" ORDER BY ORDER_NO ";
	         ResultSet result = QueryUtil.getResult(query,null);
	         SequenceMap map = new SequenceMap();
	         int i=0;
	         while (result.next()) {
	                 i++;
	                 Info info = new Info();
	                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
	                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
	                 info.set(FieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
	                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
	                 info.set(FieldNames.ORDER_NO,result.getString("ORDER_NO"));
	                 info.set(FieldNames.FIELD_LENGTH,result.getString("FIELD_LENGTH"));
	                 info.set(FieldNames.VISIBLE,changeFormat((Integer) result.getObject("VISIBLE")));
	                 info.set(FieldNames.EXPORTABLE, changeFormat((Integer) result.getObject("EXPORTABLE")));
	                 info.set(FieldNames.SEARCHABLE, changeFormat((Integer) result.getObject("SEARCHABLE")));
	                 info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
	                 info.set(FieldNames.LABEL_NAME, i+"");
	                 info.set(FieldNames.SECTION_ID, result.getString("SECTION_ID"));
	                 map.put(result.getString("FIELD_ID"), info);
	         }
	         QueryUtil.releaseResultSet(result);
	         return map;
		 }
		 
		 /**
		  * 
		  * @return
		  */
		 public SequenceMap getFimCustomTabAvailableFields() {
	        return getFimCustomTabAvailableFields(null);
		 }
		
		 	/** @author devendra
		 	 * This method is used to get all the sections
		 	 * @return
		 	 */
		 	public LinkedHashMap getAllSections()
			{
				String query = "SELECT SECTION_ID, SECTION_NAME FROM FIM_CUSTOM_TAB_SECTIONS ORDER BY ORDER_NO";
				ResultSet result = QueryUtil.getResult(query,null);
				LinkedHashMap<String, String> sectionMap = new LinkedHashMap<String, String>();
System.out.print("\nquery>>>>>>>>>>>>>>>>>>>>>>>>>"+query);
				while(result!=null && result.next())
				{
					sectionMap.put(result.getString("SECTION_ID"), LanguageUtil.getString(result.getString("SECTION_NAME")));
				}
				QueryUtil.releaseResultSet(result);
				return sectionMap;
			}
		 	
		 	
		 	// P_FIM_BUG_4616 himanshi starts
		 	
		 	/*
		 	 * This method returns a flag if custom section is associated with some fields 
		 	 */
		 	
		 	public static boolean  getSectionAssociated(String key)
			{
		        boolean finalFlag=false;

		        String squery = "SELECT SECTION_ID,SECTION_NAME FROM FIM_CUSTOM_TAB_SECTIONS WHERE SECTION_ID IN (SELECT DISTINCT SECTION_ID FROM FIM_CUSTOM_TAB_FIELDS)";
		        ResultSet result = QueryUtil.getResult(squery, new Object[]{});
				while(result.next() && result!=null)
		        {
		            
		            String sectionID=result.getString("SECTION_ID");
		            if(sectionID.equals(key))
		            {
		            finalFlag=true;
		            break;
		           }
		        else{
				 finalFlag=false;
		            
		        }
		        }
				QueryUtil.releaseResultSet(result);
		         return  finalFlag;
		    }
		 	
		 	// P_FIM_BUG_4616 himanshi ends
		 	
		 	public String getSectionName(String sectionId)
			{
				String query = "SELECT  SECTION_NAME FROM FIM_CUSTOM_TAB_SECTIONS WHERE SECTION_ID='"+sectionId+"'";
				String sectionName="";
				ResultSet result = QueryUtil.getResult(query,null);
				
				while(result!=null && result.next())
				{
					sectionName=LanguageUtil.getString(result.getString("SECTION_NAME"));
				}
				QueryUtil.releaseResultSet(result);
				return sectionName;
			}
		 	public int getOrderNo()
			{
				String query = "SELECT MAX(ORDER_NO) AS ORDER_NO FROM FIM_CUSTOM_TAB_SECTIONS";
				ResultSet result = QueryUtil.getResult(query,null);
				int orderNo = 999;

				if ( result != null && result.next() )
				{
					orderNo = Integer.parseInt( result.getString("ORDER_NO"));
				}
				QueryUtil.releaseResultSet(result);
				return orderNo;
			}
		 	
		 
		 /**
		  * 
		  * @param fieldID
		  * @return
		  */
		 public Info getFimCustomTabFields(String fieldID) {
	         String query = "SELECT * FROM FIM_CUSTOM_TAB_FIELDS  WHERE FIELD_ID="+fieldID;
	         ResultSet result = QueryUtil.getResult(query,null);
	         //System.out.println("query------------"+query);
	         Info info = new Info();
	         if (result.next()) {
	                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
	                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
	                 info.set(FieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
	                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
	                 info.set(FieldNames.ORDER_NO,result.getString("ORDER_NO"));
	                 info.set(FieldNames.FIELD_LENGTH,result.getString("FIELD_LENGTH"));
	                 info.set(FieldNames.VISIBLE,changeFormat((Integer) result.getObject("VISIBLE")));
	                 info.set(FieldNames.EXPORTABLE, changeFormat((Integer) result.getObject("EXPORTABLE")));
	                 info.set(FieldNames.SEARCHABLE, changeFormat((Integer) result.getObject("SEARCHABLE")));
	                 info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
	                 info.set(FieldNames.SECTION_ID, result.getString("SECTION_ID"));
	         }
	         QueryUtil.releaseResultSet(result);
	         return info;
		 }
		//P_FIM_E_CUSTOM_TAB 
		 /**
			 * to check record exist for custom tabs for particular franchisee
			 * @param franchiseeNo
			 * @return
			 */
			public boolean recordExistForCustomTab(String franchiseeNo) {
				boolean exist = false;
				
				String query = "SELECT * FROM FIM_CUSTOM_TAB WHERE FRANCHISEE_NO = ?";
				
				ResultSet result = QueryUtil.getResult(query, new String[]{franchiseeNo});

				if (result.next()) {
					exist = true;
				}
				QueryUtil.releaseResultSet(result);
				return exist;
			}
			
			
			/**
			 * To get combo options for particualr field id
			 * @param fieldID
			 * @return
			 */
			public SequenceMap getComboOptionsMap(String fieldID) {
				String query = "SELECT * FROM FIM_CUSTOM_TAB_COMBO  WHERE FIELD_ID = ?  ORDER BY OPTION_VALUE";
		         ResultSet result = QueryUtil.getResult(query, new Object[] {fieldID});
		         SequenceMap map = new SequenceMap();
		         while (result.next()) {
		                 
		                 Info info = new Info();
		                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
		                 info.set(FieldNames.OPTION_NAME, result.getString("OPTION_NAME"));
		                 info.set(FieldNames.OPTION_VALUE, result.getString("OPTION_VALUE"));
		                 info.set(FieldNames.CUSTOM_ID, result.getString("CUSTOM_ID"));
		                 map.put(result.getString("CUSTOM_ID"), info);
		         }
		         QueryUtil.releaseResultSet(result);
		         return map;
			}
			/**
			 * To delete combo options for particualr field id
			 * @param fieldID
			 * @return
			 */
			public boolean deleteComboOptionsMap(String fieldID) {
				boolean flag = false;
				String query = "DELETE FROM FIM_CUSTOM_TAB_COMBO  WHERE FIELD_ID = ?";
				try {
					QueryUtil.update(query, new String[]{fieldID});
					flag = true;
				} catch (Exception e) {
					logger.error("error in delete combo options map==="+e.getMessage());
				}
				return flag;
				
			}
			
			
			/**
			 * To INSERT combo options for particualr field id
			 * @param fieldID
			 * @return
			 */
			public boolean insertComboOptionsMap(String fieldID,String [] optionName) {
				boolean flag = false;
				String query = "INSERT INTO FIM_CUSTOM_TAB_COMBO  VALUES (?,?,?,?)";
				try {
					for(int i=0;i<optionName.length;i++) {
						QueryUtil.update(query, new String[]{"",fieldID,optionName[i],""+(i+1)});
					}
					flag = true;
				} catch (Exception e) {
					logger.error("error in INSERTING combo options map==="+e.getMessage());
				}
				return flag;
				
			}
			
			/**
			 * To get combo value for with respect to particular field id and option value
			 * @param fieldID
			 * @param id
			 * @return 
			 */
			public String getCustomComboValue(String id,String fieldID) {
				 String value="";
				 String query = "SELECT * FROM FIM_CUSTOM_TAB_COMBO  WHERE FIELD_ID = ? AND OPTION_VALUE = ?";
		         ResultSet result = QueryUtil.getResult(query, new Object[] {fieldID,id});
		         if(result.next()) {
		                 value = result.getString("OPTION_NAME");
		         }
		         QueryUtil.releaseResultSet(result);
		         return value;
				
			}
			
			
			/**
			 * @param tableName
			 * @param columnName
			 * @param foreignId
			 * @return
			 * @throws SQLException
			 * @throws RecordNotFoundException
			 */
			public SequenceMap getFimDisplayCustomTabFields(String tableName, String columnName, String foreignId)
					throws SQLException, RecordNotFoundException {
				
				
				SequenceMap sectionMap = new SequenceMap();
				
				String sectionQuery="select SECTION_ID,SECTION_NAME  from FIM_CUSTOM_TAB_SECTIONS GROUP BY ORDER_NO";
				ResultSet sectionResult = SQLUtilHelper.getResultSet(sectionQuery, new Object[] {},
						Constants.TENANT_NAME);
				while (sectionResult.next()) {
					String query = "SELECT T2.FIELD_NO ,T2.FIELD_LENGTH,T2.DISPLAY_NAME ,T2.DATA_TYPE,T2.FIELD_ID ,T2.SECTION_ID " +
						"FROM FIM_CUSTOM_TAB_FIELDS T2 " +
						"WHERE T2.AVAILABLE='0' and VISIBLE='1' AND T2.SECTION_ID ="+sectionResult.getString("SECTION_ID")+" ORDER BY T2.ORDER_NO";
				
				
				String query1 = "";
				String fieldValue = "";
				String customId="";
				ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
						Constants.TENANT_NAME);
				SequenceMap map = new SequenceMap();
				ResultSet result1 = null;
				while (result.next()) {
					Info info = new Info();
					info.set(CustomFormFieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
					info.set(CustomFormFieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
					info.set(FieldNames.FIELD_ID, result.getString("FIELD_ID"));
					info.set(FieldNames.FIELD_LENGTH, result.getString("FIELD_LENGTH"));
					String label = result.getObject("FIELD_NO").toString();
					info.set(CustomFormFieldNames.FIELD_NO, (Integer) result.getObject("FIELD_NO"));
					String fieldName = tableName + "_C" + label;
					query1 = "SELECT CUSTOM_TAB_ID, " + fieldName + " FROM " + tableName + " WHERE "
					+ columnName + "="+foreignId;
					
					result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
							Constants.TENANT_NAME);
				
					if (result1.next() && result1.getObject(fieldName) != null) {
						fieldValue = result1.getObject(fieldName).toString();
						if(fieldValue.equals("0"))
						{
							fieldValue = "";
						}
						//System.out.println("fieldValue=========="+fieldValue);
						customId = result1.getObject("CUSTOM_TAB_ID").toString();
					} else {
						fieldValue = "";
					}
					
					/*if (result1.next() && result1.getObject("CUSTOM_TAB_ID") != null) {
						customId = result1.getObject("CUSTOM_TAB_ID").toString();
					}*/
					info.set("fieldvalue", fieldValue);
					info.set("fieldName", fieldName);
					info.set("customId",customId);
					
					
					map.put(label, info);
				}
				
				if(map.size()!=0)
				sectionMap.put(sectionResult.getString("SECTION_NAME"), map);
				
				}
				return sectionMap;
			}
			//P_FIM_E_CUSTOM_TAB  ends
			///P_E_FO_CUSTOMIZATION starts
			public String checkFoCustomField()
			{

				String query = " SELECT FIELD_ID FROM FO_CUSTOMIZATION_FIELD WHERE AVAILABLE = 1  AND (DISPLAY_NAME IS NULL OR DISPLAY_NAME= '') ORDER BY ORDER_NO ASC ";//REFERENCE_DATE_ENH starts
				String fieldId = "1";
				ResultSet result=null;
				try
				{
					result =QueryUtil.getResult(query,null);
					if(result != null && result.next()){
						fieldId = result.getString("FIELD_ID");
					}else
					{          
						/*for(int i=1;i<=15;i++)
						{
							System.out.println("11111111111111111111111111111111");
							query="INSERT INTO FO_CUSTOMIZATION_FIELD(FIELD_ID,FIELD_NO,ORDER_NO,EXPORTABLE,SEARCHABLE,AVAILABLE) VALUES(?,?,?,?,?,?)";
							QueryUtil.update(query,new String[]{i+"",i+"","100","0","0","1"});
						}*/
						query = "SELECT MAX(FIELD_ID) FIELD_ID FROM FO_CUSTOMIZATION_FIELD";
						result =QueryUtil.getResult(query,null);
						if(result.next()){
							fieldId = Integer.parseInt(result.getString("FIELD_ID"))+1+"";
						}
						query="INSERT INTO FO_CUSTOMIZATION_FIELD(FIELD_ID,FIELD_NO,ORDER_NO,EXPORTABLE,SEARCHABLE,AVAILABLE) VALUES(?,?,?,?,?,?)";
						QueryUtil.update(query,new String[]{fieldId,fieldId,"100","0","0","1"});
						StringBuffer alterQuery = new StringBuffer();
						alterQuery.append("ALTER TABLE ").append("FRANCHISEE").append(" ");
				        alterQuery.append("ADD COLUMN ").append("FO_CUSTOM_FIELD_C").append(fieldId).append(" VARCHAR(25)  default NULL");
						QueryUtil.alterDBTable(alterQuery.toString());
						
						Document tabulardoc = null;
						String location 		= Constants.XML_DIRECTORY+"tables/admin/franchisees.xml";
						File tabularfile 			= new File(location);
		    			if(tabularfile.isFile())
		    			{
		    				try
		    				{
		    					tabulardoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(tabularfile);
		    					Node firstChild 		= tabulardoc.getFirstChild();
		    					Element childElement = tabulardoc.createElement(TableXMLDAO.FIELD);
		    					childElement.setAttribute(TableXMLDAO.SUMMARY,"true" );
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, TableXMLDAO.FIELD_NAME, "foCustomFieldC"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, TableXMLDAO.DISPLAY_NAME, "FO Custom Field-"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, TableXMLDAO.DB_FIELD, "FO_CUSTOM_FIELD_C"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, TableXMLDAO.DATA_TYPE,"String"));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, TableXMLDAO.CUSTOM_FIELD, "true"));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc, "custom-field-table", "FO_CUSTOMIZATION_FIELD"));
		    					firstChild.appendChild(childElement);
		    					DOMSource source = new DOMSource(tabulardoc);
		    					TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(location));
	    						((Element) tabulardoc).normalize();
		    				}
		    				catch(Exception e)
		    				{
		    					e.printStackTrace();
		    					logger.error("Exception in checkFoCustomField xml writer",e);
		    				}
		    			}
	     				
		    			Document tabulardoc1 = null;
						String location1 		= Constants.XML_DIRECTORY+"tables/storeopener/smFranchisees.xml";
						File tabularfile1 			= new File(location1);
		    			if(tabularfile1.isFile())
		    			{
		    				try
		    				{
		    					tabulardoc1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(tabularfile1);
		    					Node firstChild 		= tabulardoc1.getFirstChild();
		    					Element childElement = tabulardoc1.createElement(TableXMLDAO.FIELD);
		    					childElement.setAttribute(TableXMLDAO.SUMMARY,"true" );
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc1, TableXMLDAO.FIELD_NAME, "foCustomFieldC"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc1, TableXMLDAO.DISPLAY_NAME, "FO Custom Field-"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc1, TableXMLDAO.DB_FIELD, "FO_CUSTOM_FIELD_C"+fieldId));
		    					childElement.appendChild(FormBaseDAO.getElementNode(tabulardoc1, TableXMLDAO.DATA_TYPE,"String"));
		    					firstChild.appendChild(childElement);
		    					DOMSource source = new DOMSource(tabulardoc1);
		    					TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(location1));
	    						((Element) tabulardoc).normalize();
		    				}
		    				catch(Exception e)
		    				{
		    					e.printStackTrace();
		    					logger.error("Exception in udateTaskManipulator xml writer",e);
		    				}
		    			}

					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error("Exception in checkFoCustomField",e);
				}
				finally
				{
					QueryUtil.releaseResultSet(result);
				}//REFERENCE_DATE_ENH ends

		            return fieldId;
		            
		    }
			public SequenceMap getFoFormCustomFields(String fieldId){
						StringBuffer query =new StringBuffer(" SELECT * FROM FO_CUSTOMIZATION_FIELD  ");
						if(StringUtil.isValid(fieldId))
							query.append("  WHERE FIELD_ID=").append(fieldId);
						query.append(" ORDER BY ORDER_NO");
						ResultSet result = QueryUtil.getResult(query.toString(), null);
						String query1 = "";
						int	count =  0;
						String associated  = "No";
						SequenceMap map = new SequenceMap();
						
						while (result.next()) {
						
							Info info = new Info();
							info.set(CustomFormFieldNames.FIELD_ID, (Integer) result
									.getObject("FIELD_ID"));
							info.set(CustomFormFieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
							info.set(CustomFormFieldNames.DATA_TYPE, result
									.getString("DATA_TYPE"));
							String label = result.getObject("FIELD_NO").toString();
							info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
									.getObject("FIELD_NO"));
							info.set(CustomFormFieldNames.ORDER, (Integer) result
									.getObject("ORDER_NO"));
							info.set(FieldNames.AVAILABLE, result.getString("AVAILABLE"));
							
							query1  =  "  SELECT COUNT(FRANCHISEE_NAME) COUNT FROM FRANCHISEE WHERE IS_STORE_ARCHIVED='N' AND IS_STORE ='Y'AND (FO_CUSTOM_FIELD_C"+label+" IS NOT NULL AND FO_CUSTOM_FIELD_C"+label+" != '') ";
							 ResultSet rs1 = QueryUtil.getResult(query1, new Object[]{});
							 	if(rs1.next()){			    			
							 		count = Integer.parseInt(rs1.getString("COUNT"));
							 	}
							 	query1  =  "SELECT COUNT(*) COUNT FROM SM_TASK_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_EQUIPMENT_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_DOCUMENT_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_PICTURE_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_SECONDRY_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_DOCUMENT_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_EQUIPMENT_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_PICTURE_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_SECONDRY_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         
				                         query1  =  "SELECT COUNT(*) COUNT FROM SM_USER_TASK_CHECKLIST WHERE IS_DELETED = 'N' AND REFERENCE_FIELD LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
							 rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         query1="SELECT COUNT(*) COUNT FROM FO_MILESTONE_DATE_TRIGGERS WHERE MILESTONE_DATE_ID LIKE 'FO_CUSTOM_FIELD_C"+label+"'";
				                         rs1 = QueryUtil.getResult(query1, new Object[]{});
				                         if(rs1.next()){			    			
				                                 count = count + Integer.parseInt(rs1.getString("COUNT"));
				                         }
				                         if(count >0)
				                         {
								 associated  = "Yes";
				                         }
							 else
				                         {
								 associated  = "No";
				                         } 
							 	
							info.set("associated",associated);
							map.put(label, info);
						}
						return map;
				}
			 
			public SequenceMap getFoStoreDisplayCustomFields(String fieldPrefix,
					String tableName, String columnName, String foreignId)
					throws SQLException, RecordNotFoundException {

				String query = "SELECT T2.FIELD_ID,T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE,T2.MILESTONE_APPLICABLE  FROM FO_CUSTOMIZATION_FIELD T2 WHERE  T2.AVAILABLE='0' ORDER BY T2.ORDER_NO"; //P_ENH_FO_MILESTONE_DATE
				String query1 = "";
				String fieldValue = "";
				logger.info("\nQUERY1 IN getFoStoreDisplayCustomFields :-"+query);
				ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {},
						Constants.TENANT_NAME);
				SequenceMap map = new SequenceMap();
				ResultSet result1 = null;
				if (foreignId != null && (foreignId.trim()).equals("")) {
					foreignId = null;
				}

				while (result.next()) {

					Info info = new Info();
					info.set(CustomFormFieldNames.FIELD_ID, result
							.getString("FIELD_ID"));
					info.set(CustomFormFieldNames.DISPLAY_NAME, result
							.getString("DISPLAY_NAME"));
					info.set(CustomFormFieldNames.DATA_TYPE, result
							.getString("DATA_TYPE"));
					info.set(CustomFormFieldNames.MILESTONE_APPLICABLE, result
							.getString("MILESTONE_APPLICABLE")); //P_ENH_FO_MILESTONE_DATE
					String label = result.getObject("FIELD_NO").toString();
					info.set(CustomFormFieldNames.FIELD_NO, (Integer) result
							.getObject("FIELD_NO"));
					String fieldName = fieldPrefix + label;
					query1 = "SELECT " + fieldName + " FROM " + tableName + " WHERE "
							+ columnName + "=" + foreignId;
					logger.info("\nQUERY2 IN getFoStoreDisplayCustomFields	 :-"+query1);
					result1 = SQLUtilHelper.getResultSet(query1, new Object[] {},
							Constants.TENANT_NAME);

					if (result1.next() && result1.getObject(fieldName) != null) {

						fieldValue = result1.getObject(fieldName).toString();
					} else {
						fieldValue = "";
					}

					info.set("fieldvalue", fieldValue);
					info.set("fieldName", fieldName);

					map.put(label, info);
				}
				return map;
			}
			
			public SequenceMap getFoStoreCustomFields(){
				String query = "SELECT T2.FIELD_ID,T2.FIELD_NO ,T2.DISPLAY_NAME ,T2.DATA_TYPE,T2.MILESTONE_APPLICABLE  FROM FO_CUSTOMIZATION_FIELD T2 WHERE  T2.AVAILABLE='0' ORDER BY T2.ORDER_NO"; //P_ENH_FO_MILESTONE_DATE
				SequenceMap map = new SequenceMap();
				ResultSet result=null;
				try{
					 result = QueryUtil.getResult(query,null);
			        int i=0;
			         while (result.next()) {
			                 i++;
			                 Info info = new Info();
			                 info.set(FieldNames.FIELD_ID,result.getString("FIELD_ID"));
			                 info.set(FieldNames.DISPLAY_NAME, result.getString("DISPLAY_NAME"));
			                 info.set(FieldNames.DATA_TYPE, result.getString("DATA_TYPE"));
			                 info.set(FieldNames.FIELD_NO,result.getString("FIELD_NO"));
			                 info.set(CustomFormFieldNames.MILESTONE_APPLICABLE,result.getString("MILESTONE_APPLICABLE")); //P_ENH_FO_MILESTONE_DATE
			                 map.put(result.getString("FIELD_ID"), info);
			         }
			         
				}catch (Exception e) {
					logger.error("Exception in getFoStoreCustomFields",e);
				}
				finally
				{
					QueryUtil.releaseResultSet(result);
				}

				return map;
			}
			public String getFoStoreCustomFieldDisplayName(String fieldNo){
				String query = "SELECT T2.DISPLAY_NAME FROM FO_CUSTOMIZATION_FIELD T2 WHERE FIELD_NO='"+fieldNo+"'";
				String displayName = "";
				ResultSet result=null;
				try{
					 result = QueryUtil.getResult(query,null);
			         while (result.next()) {
			        	 displayName = result.getString("DISPLAY_NAME");
			         }
			         
				}catch (Exception e) {
					logger.error("Exception in getFoStoreCustomFields",e);
				}
				finally
				{
					QueryUtil.releaseResultSet(result);
				}

				return displayName;
			}
			//	P_E_FO_CUSTOMIZATION ends	
}

