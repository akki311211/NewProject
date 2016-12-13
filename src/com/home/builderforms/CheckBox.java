/**
 
 *	Name - CheckBox.java
 *	Directory position - com/appnetix/app/util/tagutils

 * @author  Tarun
 * @version 1.1.1.1, 
 * date 04/01/2005

 */

package com.home.builderforms;


import com.home.builderforms.LanguageUtil;
import com.home.builderforms.Info;

public class CheckBox {
private String displayName;
private String checkBoxValue;
private String jScript;
private Info infoCheckBox;
private int colCount = 0;

/**
 *	Default Constructor.
 */

	public CheckBox(){
		infoCheckBox = new Info();
	}

/**
 *	 This Constructor sets the display name for the CheckBox.
 */

	public CheckBox(String displayName){
		this(displayName,null);
		infoCheckBox = new Info();
	}

/**
 *	 This Constructor sets the display name and value for the CheckBox.
 */

	public CheckBox(String displayName, String checkBoxValue){
		this.displayName=displayName;
		this.checkBoxValue=checkBoxValue;
		infoCheckBox = new Info();
	}

/**
 *	 Returns the options to be displayed in Checkbox.
 */

	public Info getInfo(){
		return infoCheckBox;
	}

/**
 *	 Returns the display name of Checkbox.
 */

	public String getDisplayName(){
		return displayName;
	}

/**
 *	 Sets the display name of Checkbox.
 */

	public void setDisplayName(String val){
		displayName=val;
	}

/**
 *	 Sets the new value for Checkbox .
 */

	public void setValue(String displayName, String value){
		setValue(displayName, value, false);
	}

/**
 *	 Returns the javascript to be used.
 */

	public String getJScript(){
		return jScript;
	}


/**
 *	 Sets the javascript into the Checkbox.
 */

	public void setJScript(String value){
		jScript	= value;
	}

	/**
	 *	 Sets the Col count into the Checkbox.
	 */
	public void setColCount(int value){
		colCount	= value;
	}
	
	public int getColCount(){
		return colCount;
	}
	
/**
 *	 Sets the new values for Checkbox.
 */

	public void setValue(String displayName, String value, boolean checked){
		Info info = new Info();
		info.set("displayName",LanguageUtil.getString(displayName));
		info.set("value",value);
		if(checkBoxValue!=null && value.equals(checkBoxValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		infoCheckBox.set((infoCheckBox.size()+1)+"",info);
	}
	
	/**
	 * This function is used to set the options for the Radio.
	 * This function is differ from other setOption function because it contains disabled option.
	 * BB-20150203-259 (Dynamic Response based on parent field response)
	 * @author Naman Jain
	 * @param displayName
	 * @param value
	 * @param checked
	 * @param disabled
	 */
	public void setValue(String displayName, String value, boolean checked, boolean disabled){
		Info info = new Info();
		info.set("displayName",LanguageUtil.getString(displayName));
		info.set("value",value);
		if(checkBoxValue!=null && value.equals(checkBoxValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		info.set("disabled", disabled ? "disabled" : ""); //check for disabling the options
		infoCheckBox.set((infoCheckBox.size()+1)+"",info);
	}


}
  
