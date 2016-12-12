package com.home.builderforms;

/**
 * This class is used as POJO for sync fields across tabs/modules.
 * P_Enh_Sync_Fields
 * @author Naman Jain
 *
 */
public class SyncWithField {
	
	private String fieldName = null;
	private String tableName = null;
	private String columnName = null;
	private String tableAnchor = null;
	private String parentField = null;
	private String displayName = null;
	private String syncModule = null;
	
	public String getSyncModule() {
		return syncModule;
	}

	public void setSyncModule(String syncModule) {
		this.syncModule = syncModule;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
	public String getTableAnchor() {
		return tableAnchor;
	}

	public void setTableAnchor(String tableAnchor) {
		this.tableAnchor = tableAnchor;
	}

	public SyncWithField(String fieldName, String tableName, String columnName, String tableAnchor, String parentField, String displayName, String syncModule) {
		this.fieldName = fieldName;
		this.tableName = tableName;
		this.columnName = columnName;
		this.tableAnchor = tableAnchor;
		this.parentField = parentField;
		this.displayName = displayName;
		this.syncModule = syncModule;
	}
}
