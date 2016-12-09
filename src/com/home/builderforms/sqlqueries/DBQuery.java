/**
 * @author abhishek gupta
 */
package com.appnetix.app.util.sqlqueries;

public class DBQuery
{
	private String ddlType; 
	private String tableDBName;
	private DBColumn[] dbCols;
	
	public DBQuery() {
		ddlType = "";
		tableDBName = null;
		dbCols = null;
	}

	public DBQuery(String ddlType) {
		this.ddlType = ddlType;
		this.tableDBName = null;
		this.dbCols = null;
	}

	public DBQuery(String ddlType, String tableDBName) {
		this.ddlType = ddlType;
		this.tableDBName = tableDBName;
		this.dbCols = null;
	}

	public DBQuery(String ddlType, String tableDBName, DBColumn[] dbCols) {
		this.ddlType = ddlType;
		this.tableDBName = tableDBName;
		this.dbCols = dbCols;
	}

	public void setDDLType(String ddlType) {
		this.ddlType = ddlType;
	}
	
	public String getDDLType() {
		return ddlType;
	}

	public void setTableName(String tableDBName) {
		this.tableDBName = tableDBName;
	}
	
	public String getTableDBName() {
		return tableDBName;
	}
	
	public void setDBColumns(DBColumn[] dbCols) {
		this.dbCols = dbCols;
	}
	
	public  DBColumn[] getDBColumns() {
		return dbCols;
	}
}

