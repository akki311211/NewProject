/**
 * @author abhishek gupta
 * @version 1.0
 * @date 18.11.2011
 */
package com.home.builderforms;

import java.util.*;
import com.appnetix.app.util.*;
import com.home.builderforms.Info;
/**
 * Abstract Class for a Form Builder
 */
public abstract class BaseBuilder{
	protected int tableID;
	protected int tableWidth =0;
	protected String tableName;
	protected String tableDescription;
	protected java.util.Date creationDate;
	public String xmlName;
	protected HashMap tableElements;
	protected SequenceMap tableData;
	protected Info tableInfo;
	protected Object objectData;
	
	protected BaseBuilder() {}

	protected BaseBuilder(HashMap cParams, HashMap tableParameters)	{
		try {
			this.setTableID(Integer.parseInt((String)cParams.get(BuilderConstants.TABLE_ID)));
			this.setTableName((String)cParams.get(BuilderConstants.TABLE_NAME));
			this.setTableDescription((String)cParams.get(BuilderConstants.TABLE_DESCRIPTION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void setTableID(int value){
		tableID = value;
	}

	public final void setTableWidth(int value){
		tableWidth = value;
	}

	public final int getTableWidth(){
		return tableWidth;
	}

	public final void setTableName(String value){
		tableName = value;
	}

	public final void setTableDescription(String value){
		tableDescription = value;
	}

	public final void setCreationDate(java.util.Date date){
		creationDate = date;
	}
	
	public void setTableElements(HashMap value){
		tableElements = value;
	}
	
	public void setTableMap(SequenceMap value){
		tableData = value;
	}
	
	public void setTableInfo(Info value){
		tableInfo = value;
	}
	
	public void setObject(Object value){
		objectData = value;
	}

	public final int getTableID(){
		return tableID;
	}

	public final String getTableName(){
		return tableName;
	}

	public final String getTableDescription(){
		return tableDescription;
	}
	
	public final java.util.Date getCreationDate(){
		return creationDate;
	}

	public final SequenceMap getTableMap(){
		return tableData;
	}
	
	public final Object getObject(){
		return objectData;
	}
	
	public final Info getTableInfo(){
		return tableInfo;
	}
	
	public final HashMap getTableElements(){
		return tableElements;
	}

	public abstract void createTableElements(HashMap cParams, HashMap tableParameters) throws Exception;

	public abstract String generateXMLTable();
}
