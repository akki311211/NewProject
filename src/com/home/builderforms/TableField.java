/**
 * @author abhishek gupta
 * @Date 13 Apr 2012
 */
package com.home.builderforms;

import java.util.HashMap;

import com.home.builderforms.SequenceMap;

public class TableField {
	private SequenceMap msTableFieldMap			= null;
	private String name = null;
	public TableField(String name, SequenceMap msFieldMap) {
		this.name = name;
		this.msTableFieldMap	= msFieldMap;
	}

	public String getFieldName() {
		return name;
	}
	public SequenceMap getFieldMap() {
		return msTableFieldMap;
	}
}