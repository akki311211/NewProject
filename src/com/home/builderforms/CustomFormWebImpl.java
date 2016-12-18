package com.home.builderforms;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.SQLException;

import com.home.builderforms.sqlqueries.ResultSet;

import com.home.builderforms.Info;


import com.home.builderforms.CustomFormMgr;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *  This class is the web-tier representation of the Referrer Data.
 *
 *@author     Onkar
 *@created    March 31, 2004
 */

public class CustomFormWebImpl implements java.io.Serializable {
	static Logger logger	= Logger.getLogger(CustomFormWebImpl.class);


    //private HttpSession session;

	private CustomFormMgr form		= null;
	private static CustomFormWebImpl impl	= null;

    /**
     *  Constructor for the ReferrerWebImpl object
     */
    public CustomFormWebImpl() { }

	public static CustomFormWebImpl getInstance(){
		if(impl == null){
			impl = new CustomFormWebImpl();
		}
		return impl;
	}

    public CustomFormMgr getCustomFormMgr() {
		if(form == null){
			form= CustomFormMgr.newInstance();
		}

        return form;

    }

    /**
     *   Method
     *
     *@param  session   Parameter
     */
    public void init(HttpSession session) {


    }

	public SequenceMap getCustomForm() {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getCustomFormList();
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	public SequenceMap getAreaCustomForm() {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getAreaCustomFormList();
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	public SequenceMap getMUCustomForm() {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getMUCustomFormList();
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}

	public SequenceMap getCustomFields(String formId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getFimFormCustomFields(formId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	
	public SequenceMap getAreaCustomFields(String formId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getAreaFimFormCustomFields(formId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	//P_FIM_E_CUSTOM_TAB by devendra starts
	public SequenceMap getCustomTabFormDisplayFields(String tableName , String columnName, String foreignId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getFimDisplayCustomTabFields(tableName,columnName,foreignId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	public String getCustomComboValue(String id,String fieldID) {
		String value		= "";
		try {
			value				= getCustomFormMgr().getCustomFormDAO().getCustomComboValue(id,fieldID);
			return value;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	public SequenceMap getCustomCombo(String fieldID) {
		SequenceMap value		= new SequenceMap();
		try {
			value				= getCustomFormMgr().getCustomFormDAO().getComboOptionsMap(fieldID);
			return value;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
//	P_FIM_E_CUSTOM_TAB ends
	public SequenceMap getMUCustomFields(String formId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getMUFimFormCustomFields(formId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}


	public SequenceMap getCustomFormViewFields(String formId , String tableName , String columnName , String foreignId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getViewFimCustomFields(formId,tableName,columnName,foreignId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}


	public SequenceMap getCustomFormDisplayFields(String formId , String tableName , String columnName , String foreignId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getFimDisplayCustomFields(formId,tableName,columnName,foreignId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	
	public SequenceMap getAreaCustomFormDisplayFields(String formId , String tableName , String columnName , String foreignId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getAreaDisplayCustomFields(formId,tableName,columnName,foreignId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}
	
	// for custom field in MultiUnit Regional  ALOK
	public SequenceMap getMUCustomFormDisplayFields(String formId , String tableName , String columnName , String foreignId) {
		SequenceMap map		= null;
		try {
			map				= getCustomFormMgr().getCustomFormDAO().getMUDisplayCustomFields(formId,tableName,columnName,foreignId);
			return map;
		}
		catch (Exception e){
			logger.error("Exception ", e);
			return null;
		}
	}





	
	
	



/**Addition Complete */
public Info getCustomFieldsForExport(String menuName, String formId, String fieldNo) {

	try{
		return getCustomFieldsForExport(menuName,formId,fieldNo,null);
	}
	catch (Exception e){
		logger.error("Exception ", e);
		return null;
	}
}

public Info getCustomFieldsForExport(String menuName, String formId, String fieldNo,String fimFlag) {
	try {
		return getCustomFormMgr().getCustomFormDAO().getCustomFieldsForExport(menuName, formId, fieldNo,fimFlag);
	}
	catch (Exception e){
		logger.error("Exception ", e);
		return null;
	}
}
//P_FIM_E_CUSTOM_TAB by devendra starts
public Info getCustomTabFieldsForExport(String menuName, String fieldNo) {

	try {
		return getCustomFormMgr().getCustomFormDAO().getCustomTabFieldsForExport(menuName, fieldNo);
	}
	catch (Exception e){
		logger.error("Exception ", e);
		return null;
	}
}

//P_FIM_E_CUSTOM_TAB by devendra ends
public SequenceMap getFimContactCustomFields(String formId) {
	SequenceMap map		= null;
	try {
		map				= getCustomFormMgr().getCustomFormDAO().getFimContactFormCustomFields(formId);
		return map;
	}
	catch (Exception e){
		logger.error("Exception ", e);
		return null;
	}
}


public SequenceMap getFimContactCustomFieldsInfo(String formId) {
	SequenceMap map		= null;
	try {
		map				= getCustomFormMgr().getCustomFormDAO().getFimContactFormCustomFieldsInfo(formId);
		return map;
	}
	catch (Exception e){
		e.printStackTrace();
		logger.error("Exception ", e);
		return null;
	}
}


public SequenceMap getCustomContactFormDisplayFields(String formId , String tableName , String columnName , String foreignId) {
	SequenceMap map		= null;
	try {
		map				= getCustomFormMgr().getCustomFormDAO().getFimContactDisplayCustomFields(formId,tableName,columnName,foreignId);
		return map;
	}
	catch (Exception e){
		logger.error("Exception ", e);
		return null;
	}
}

/**P_FIM_E_CUSTOM_TAB by devendra 
 * to check record exist for custom tabs for particular franchisee
 * @param franchiseeNo
 * @return
 */
public boolean recordExistForCustomTab(String franchiseeNo) {
	return getCustomFormMgr().getCustomFormDAO().recordExistForCustomTab(franchiseeNo);
}

	public SequenceMap getFoStoreDisplayCustomFields(String fieldPrefix,
			String tableName, String columnName, String foreignId) {
		SequenceMap map = null;
		try {
			map = getCustomFormMgr().getCustomFormDAO()
					.getFoStoreDisplayCustomFields(fieldPrefix, tableName,
							columnName, foreignId);
			return map;
		} catch (Exception e) {
			logger.error("Exception ", e);
			return null;
		}
	}

	public SequenceMap getFoStoreCustomFields() {
		SequenceMap map = null;
		try {
			map = getCustomFormMgr().getCustomFormDAO()
					.getFoStoreCustomFields();
			return map;
		} catch (Exception e) {
			logger.error("Exception ", e);
			return null;
		}
	}
	
}
