/**
 * @author abhishek gupta
 * @Date 26 Jul 2012
 */
package com.appnetix.app.util.database;

import java.util.HashMap;

import com.appnetix.app.util.SequenceMap;

public class MainField {
	private SequenceMap msTableFieldMap			= null;
	private String name = null;
	public MainField(String name, SequenceMap msFieldMap) {
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