/**
 * @author abhishek gupta
 * @Date 13 Apr 2012
 */
package com.home.builderforms;

import java.util.Iterator;

import com.home.builderforms.SequenceMap;

public class DependentTable {
	private String msTableName			= null;
	private String msTableAliasName			= null;
	private String tableAnchor			= null;
	private TableField pTableFields		= null;
	private SequenceMap tableFldMap;
	
	public DependentTable(String msTableName, SequenceMap msBuildMap,String msTableAliasName,String tableAnchor) {
		this.msTableName		= msTableName;
		this.msTableAliasName	= msTableAliasName;
		this.tableAnchor		= tableAnchor;
		this.tableFldMap		= msBuildMap;
	}

	public String getTableName() {
		return msTableName;
	}
	public TableField[] getTableFields(){
		if(tableFldMap == null) {
			return null;
		}
		TableField[] sTableFLds = new TableField[tableFldMap.size()];
		Iterator it = tableFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			TableField table = (TableField)it.next();
			sTableFLds[i++] = table;
		}
		return sTableFLds;
	}
	public TableField getTableField(String name){
		if(tableFldMap == null) {
			return null;
		}
		Iterator it = tableFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			TableField table = (TableField)it.next();
			if(table.getFieldName().equals(name)) {
				return table;
			}
		}
		return null;
	}

	public String getTableAliasName() {
		// TODO Auto-generated method stub
		return msTableAliasName;
	}
	public String getTableAnchor() {
		// TODO Auto-generated method stub
		return tableAnchor;
	}
	
	
}