/**
 * @author abhishek gupta
 * @Date 26 Jul 2012
 */
package com.home.builderforms;

import java.util.HashMap;

import com.home.builderforms.SequenceMap;

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