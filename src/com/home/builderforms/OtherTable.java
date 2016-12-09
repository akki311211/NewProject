/**
 * @author abhishek gupta
 * @Date 26 July 2012
 */
package com.appnetix.app.util.database;

import java.util.Iterator;

import com.appnetix.app.util.SequenceMap;

public class OtherTable {
	private String msTableName			= null;
	private String msTableAliasName			= null;
	private String tableAnchor			= null;
	private TableField pTableFields		= null;
	private SequenceMap tableAttrMap;
	private SequenceMap mainFldMap;
	private SequenceMap childFldMap;
	
	public OtherTable(String msTableName, SequenceMap msAttrMap,String tableAnchor) {
		this.msTableName		= msTableName;
		this.tableAnchor		= tableAnchor;
		this.tableAttrMap		= msAttrMap;
	}
	public OtherTable(String msTableName, SequenceMap msAttrMap,SequenceMap mainMap, SequenceMap childMap) {
		this.msTableName		= msTableName;
		this.tableAnchor		= tableAnchor;
		this.tableAttrMap		= msAttrMap;
		this.mainFldMap			= mainMap;
		this.childFldMap		= childMap;
	}

	public String getTableName() {
		return msTableName;
	}
	public String getTableAnchor() {
		return tableAnchor;
	}
	public SequenceMap getOtherTableAttrMap() {
		return tableAttrMap;
	}
	
	public MainField[] getOtherTableMainFields(){
		if(mainFldMap == null) {
			return null;
		}
		MainField[] sTableFLds = new MainField[mainFldMap.size()];
		Iterator it = mainFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			MainField table = (MainField)it.next();
			sTableFLds[i++] = table;
		}
		return sTableFLds;
	}
	public MainField getOtherTableMainField(String name){
		if(mainFldMap == null) {
			return null;
		}
		Iterator it = mainFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			MainField table = (MainField)it.next();
			if(table.getFieldName().equals(name)) {
				return table;
			}
		}
		return null;
	}
	public ChildField[] getOtherTableChildFields(){
		if(childFldMap == null) {
			return null;
		}
		ChildField[] sTableFLds = new ChildField[childFldMap.size()];
		Iterator it = childFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			ChildField table = (ChildField)it.next();
			sTableFLds[i++] = table;
		}
		return sTableFLds;
	}
	public ChildField getOtherTableChildField(String name){
		if(childFldMap == null) {
			return null;
		}
		Iterator it = childFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			ChildField table = (ChildField)it.next();
			if(table.getFieldName().equals(name)) {
				return table;
			}
		}
		return null;
	}

}