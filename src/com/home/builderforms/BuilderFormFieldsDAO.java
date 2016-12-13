package com.home.builderforms;


import com.home.builderforms.*;
import com.home.builderforms.sqlqueries.ResultSet;

import java.util.ArrayList;

/**
 *  The BuilderFormFieldsDAO for the BUILDER_FORM_FIELDS table
 *@author abhishek gupta
 *@created Nov 1, 2011
 */
public class BuilderFormFieldsDAO extends BaseDAO {

	public BuilderFormFieldsDAO() {
		this.tableAnchor = "builderFormFields";

	}
	public SequenceMap getFullData(String table,Integer recordID){
		SequenceMap sMap = new SequenceMap();
		ResultSet result=null;
		StringBuffer query = new StringBuffer("SELECT *,CFF.CUSTOM_FORM_FIELDS_ID CFFID,CFF.DATA_TYPE DT FROM CUSTOM_FORM_FIELDS CFF LEFT JOIN MASTER_DATA MD ON CFF.TABLE_NAME=MD.PARENT_DATA_ID AND MD.DATA_TYPE=3019");

		if(recordID!=null){
			query.append(" LEFT JOIN CUSTOM_FORM_DATA CFD ON CFF.CUSTOM_FORM_FIELDS_ID = CFD.CUSTOM_FORM_FIELDS_ID AND RECORD_ID=").append(recordID);
		}
		if(table!=null)	query.append(" WHERE MD.DATA_VALUE='"+table+"'");
		try{
			 result			= QueryUtil.getResult(query.toString(), null);
			while(result.next()){
				Info info = new Info();
				info.set(FieldNames.CUSTOM_FORM_FIELDS_ID,result.getString("CFFID"));
				info.set(FieldNames.DISPLAY_NAME,result.getString("DISPLAY_NAME"));
				info.set(FieldNames.DATA_TYPE,result.getString("DT"));
				info.set(FieldNames.DEFAULT_VALUE,result.getString("DEFAULT_VALUE"));
				if(recordID!=null){
//					info.set(FieldNames.RECORD_ID,recordID);
					info.set(FieldNames.VALUE,result.getString("VALUE"));
					info.set(FieldNames.CUSTOM_FORM_DATA_ID,result.getString("CUSTOM_FORM_DATA_ID"));
				}
				info.set(FieldNames.DATA_VALUE,result.getString("DATA_VALUE"));
				sMap.put(result.getString("CFFID"),info);
			}
		}catch(Exception e){e.printStackTrace();
		}
		finally
		{
	
			QueryUtil.releaseResultSet(result);
		}

		return sMap;	
	}

	public ArrayList getCustomFieldsList(String table){

		ArrayList arr = new ArrayList();
		ResultSet result=null;
		String query = "SELECT CUSTOM_FORM_FIELDS_ID FROM CUSTOM_FORM_FIELDS CFF LEFT JOIN MASTER_DATA MD ON CFF.TABLE_NAME=MD.PARENT_DATA_ID AND MD.DATA_TYPE=3019 WHERE replace(DATA_VALUE,' ','') = '" + table + "'";
		try{
			 result			= QueryUtil.getResult(query,null);
//			int i=0;
			while(result.next()){
				arr.add(result.getString("CUSTOM_FORM_FIELDS_ID"));
			}
		}catch(Exception e){e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return arr;	
	}

}