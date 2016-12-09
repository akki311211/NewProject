package com.appnetix.app.util.database;

import com.appnetix.app.util.SequenceMap;

/**
 * This class is used as bean class for dependent child objects.
 * BB-20150203-259 (Dynamic Response based on parent field response)
 * @author Naman Jain
 *
 */
public class DependentChild {
	private SequenceMap dependentChildFieldMap = null;
	private String name = null;
	private String function = null;
	private String parentField = null;
	
	/**
	 * This is parameterize constructor to set the values in the variable.
	 * @param name
	 * @param dependentChildFieldMap
	 */
	public DependentChild(String name, SequenceMap dependentChildFieldMap, String function) {
		this.name = name;
		this.dependentChildFieldMap	= dependentChildFieldMap;
		this.function = function;
	}
	
	public DependentChild(String name, SequenceMap dependentChildFieldMap, String function, String parentField) {
		this.name = name;
		this.dependentChildFieldMap	= dependentChildFieldMap;
		this.function = function;
		this.parentField = parentField;
	}
	
	public String getParentField() {
		return parentField;
	}
	
	public String getFieldName() {
		return name;
	}
	
	public SequenceMap getFieldMap() {
		return dependentChildFieldMap;
	}
	
	public String getFunctionName() {
		return function;
	}
	
	public String getKeyValue(String key) {
		return (String)dependentChildFieldMap.get(key);
	}
	
	public SequenceMap getAttributesMap() {
		return dependentChildFieldMap;
	}
}
