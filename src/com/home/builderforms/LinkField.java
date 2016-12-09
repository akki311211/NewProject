/*
 * Created on Sep 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.appnetix.app.util.database;

/**
 * @author Vaibhav Sinha
 */
public class LinkField {
	private String msThisField			= null;
	private String msForeignField 		= null;
	private String msForeignFieldValue	= null;
	private String skipThisField;
	
	
	public LinkField(
					String psThisField,
					String psForeignField,
					String psForeignFieldValue
					){
		
		this(psThisField, psForeignField, psForeignFieldValue, null);
	}
	
	public LinkField(
			String psThisField,
			String psForeignField,
			String psForeignFieldValue,
			String skipThisField
			){

		msForeignField					= psForeignField;
		if(psForeignFieldValue != null){
			msForeignFieldValue			= psForeignFieldValue;
		}else{
			msThisField					= psThisField;
		}
		this.skipThisField = skipThisField;
	}
	
	public String getThisField(){
		return msThisField;
	}
	public String getForeignField(){
		return msForeignField;
	}
	public String getForeignFieldValue(){
		return msForeignFieldValue;
	}
	public String getSkipThisField() {
		return skipThisField;
	}
}