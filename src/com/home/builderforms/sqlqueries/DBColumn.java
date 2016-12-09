/**
 * @author abhishek gupta
 */
package com.appnetix.app.util.sqlqueries;

public class DBColumn
{
	private String NAME; 
	private String ACTION;
	private String COL_TYPE;
	private String COL_TYPE_VALUE = "-1";
	private boolean IS_COL_PRIMARY = false;
	private String DEFAULT;
	
	public DBColumn() {
		ACTION = null;
		NAME = null;
		COL_TYPE = null;
		DEFAULT = null;
	}

	public DBColumn(String name) {
		NAME = name;
		ACTION = null;
		COL_TYPE = null;
		DEFAULT = null;
	}
	public DBColumn(String name, String action) {
		NAME = name;
		ACTION = action;
		COL_TYPE = null;
		DEFAULT = null;
	}
	public DBColumn(String name, String action, String colType) {
		NAME = name;
		ACTION = action;
		COL_TYPE = colType;
		DEFAULT = null;
	}
	public DBColumn(String name, String action, String colType, String colTypeVal) {
		NAME = name;
		ACTION = action;
		COL_TYPE = colType;
		COL_TYPE_VALUE = colTypeVal;
		DEFAULT = null;
	}
	
	public void setColDBName(String colDBName) {
		this.NAME = colDBName;
	}
	public String getColDBName() {
		return NAME;
	}
	public void setAction(String action) {
		this.ACTION = action;
	}
	public String getAction() {
		return ACTION;
	}
	public void setColType(String colType) {
		this.COL_TYPE = colType;
	}
	public String getColType() {
		return COL_TYPE;
	}
	public void setColTypeVal(String colTypeVal) {
		this.COL_TYPE_VALUE = colTypeVal;
	}
	public String getColTypeVal() {
		return COL_TYPE_VALUE;
	}
	public void setColPrimary(boolean colPri) {
		IS_COL_PRIMARY = colPri;
	}
	public boolean isColPrimary() {
		return IS_COL_PRIMARY;
	}
	public void setColDefault(String colDefault) {
		DEFAULT = colDefault;
	}
	public String getColDefault() {
		return DEFAULT;
	}
}

