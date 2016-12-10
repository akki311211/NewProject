/**
 * @AUDIT_ENHANCEMENT_CHANGES
 * @author abhishek gupta
 * @Date 28 Jan 2012
 */

/*
--------------------------------------------------------------------------------------------------------
Version No.					Date			By				Against		Function Changed    Comments
PW_FORM_VERSION				15 July 2013	Veerpal Singh		Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
----------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import java.util.HashMap;
import java.util.Iterator;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.TableXMLDAO;

public class FormTab implements Cloneable {
	private String name;
	private String commentVisible="Y";
	private String value;
	private int order;
	private SequenceMap msTableAttrMap			= null;
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public FormTab(String name, String value, String order) {
		this.name		= name;
		this.value		= value;
		this.order		= StringUtil.isvalidInteger(order)?Integer.parseInt(order):-1;
	}
	
	public FormTab(String name, String value, String order, SequenceMap tabAttrMap) {
		this.name		= name;
		this.value		= value;
		this.order		= StringUtil.isvalidInteger(order)?Integer.parseInt(order):-1;
		this.msTableAttrMap = tabAttrMap;
	}

	public FormTab(String name, String value, String order, SequenceMap tabAttrMap,String commentVisible) {
		this.name		= name;
		this.value		= value;
		this.order		= StringUtil.isvalidInteger(order)?Integer.parseInt(order):-1;
		this.msTableAttrMap = tabAttrMap;
		this.commentVisible= StringUtil.isValidNew(commentVisible)?commentVisible:"Y";
	}
	public String getName() {
		return name;
	}
	
	public void setCommentVisible(String commentVisible) {
		this.commentVisible = commentVisible;
	}
	
	public String getCommentVisible() {
		return commentVisible;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public int getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = StringUtil.isvalidInteger(order)?Integer.parseInt(order):-1;
	}
	public String getKeyValue(String key) {
		return (String)msTableAttrMap.get(key);
	}
	public SequenceMap getAttributesMap() {
		return msTableAttrMap;
	}
	//PW_FORM_VERSION Starts
	public String getTabVersion() {
		return (String)msTableAttrMap.get(TableXMLDAO.TAB_VERSION);
	}
	
	public boolean isValidVersion(String version) {
		boolean isValid = true;
		if(msTableAttrMap != null && msTableAttrMap.size() > 0){
			String val	=	(String)msTableAttrMap.get(TableXMLDAO.V+version);
			isValid = !("NA".equalsIgnoreCase(val) || "D".equalsIgnoreCase(val) || "DEL".equalsIgnoreCase(val));
		}
		return isValid;
	}
	//PW_FORM_VERSION Ends
}