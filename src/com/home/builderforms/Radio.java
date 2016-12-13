/**

 *	Name - Radio.java
 *	Directory position - com/appnetix/app/util/tagutils

 * @author  Pritish
 * @version 1.1.1.1,
 * date 2/02/2004

 */

package com.home.builderforms;


import com.home.builderforms.Info;

public class Radio {
private String displayName;
private String radioValue;
private String jScript;
private Info infoRadio;
private int colCount = 0;

/**
 *	Default Constructor.
 */

	public Radio(){
		infoRadio = new Info();
	}

/**
 *	 This Constructor sets the display name for the radio.
 */

	public Radio(String displayName){
		this(displayName,null);
		infoRadio = new Info();
	}

/**
 *	 This Constructor sets the display name and value for the radio.
 */

	public Radio(String displayName, String radioValue){
		this.displayName=displayName;
		this.radioValue=radioValue;
		infoRadio = new Info();
	}

/**
 *	 Returns the options to be displayed in radio.
 */

	public Info getInfo(){
		return infoRadio;
	}
	
/**
 * Set Radio Info 	
 */

	public void setInfo(Info info){
		infoRadio = info;
	}
	
	
/**
 * Set Radio Value
 */
	public void setRadioValue(String value){
		radioValue = value;
	}
	
	
/**
 *	 Returns the display name of radio.
 */

	public String getDisplayName(){
		return displayName;
	}

/**
 *	 Sets the display name of radio.
 */

	public void setDisplayName(String val){
		displayName=val;
	}

/**
 *	 Sets the new option in radio .
 */

	public void setOption(String displayName, String value){
		setOption(displayName, value, false);
	}

/**
 *	 Returns the javascript to be used.
 */

	public String getJScript(){
		return jScript;
	}


/**
 *	 Sets the javascript into the RADIO.
 */

	public void setJScript(String value){
		jScript	= value;
	}

	/**
	 *	 Sets the Col count into the RADIO.
	 */
	public void setColCount(int value){
		colCount	= value;
	}
	
	public int getColCount(){
		return colCount;
	}
/**
 *	 Sets the new option in combo. You can select or deselect it.
 */

	public void setOption(String displayName, String value, boolean checked){
		Info info = new Info();
		info.set("displayName",displayName);
		info.set("value",value);
		if(radioValue!=null && value.equals(radioValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		infoRadio.set((infoRadio.size()+1)+"",info);
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
	public void setOption(String displayName, String value, boolean checked, boolean disabled){
		Info info = new Info();
		info.set("displayName",displayName);
		info.set("value",value);
		if(radioValue!=null && value.equals(radioValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		info.set("disabled", disabled ? "disabled" : ""); //check for disabling the options
		infoRadio.set((infoRadio.size()+1)+"",info);
	}

//        public void setOption(String displayName, String value, boolean checked,String Id,String event){
//		Info info = new Info();
//		info.set("displayName",displayName);
//		info.set("value",value);
//                info.set("id",Id);
//		if(radioValue!=null && value.equals(radioValue)) checked=true;
//		info.set("checked",checked ? "checked" : "");
//		infoRadio.set((infoRadio.size()+1)+"",info);
//	}


	public void setOption(String displayName, String value,boolean checked,String event){
		Info info = new Info();
		info.set("displayName",displayName);
		info.set("value",value);
		info.set("event",event);
		if(radioValue!=null && value.equals(radioValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		infoRadio.set((infoRadio.size()+1)+"",info);
	}

	public void setOption(String displayName, String value,String event){
		Info info = new Info();
		info.set("displayName",displayName);
		info.set("value",value);
		info.set("event",event);
		infoRadio.set((infoRadio.size()+1)+"",info);
	}

        
        /*
       added by uma jeena
       Ref No.  71BBCT1
        */

        public void setOption(String displayName, String value, boolean checked,String Id,String event){
		Info info = new Info();
		info.set("displayName",displayName);
		info.set("value",value);
                info.set("id",Id);
		if(radioValue!=null && value.equals(radioValue)) checked=true;
		info.set("checked",checked ? "checked" : "");
		infoRadio.set((infoRadio.size()+1)+"",info);
	}// ended by Uma Jeena


/**
 *	 Sets the value to be selected in the radio
 */
	public void setValue(String value){
		int size = infoRadio.size();//get Current radio size
		for(int i=0; i<size;i++){
			Info currentInfo = (Info)infoRadio.getObject((i+1)+"");
			//checking if the radio is to be set checked
			if(currentInfo.get("value").equals(value)) currentInfo.set("checked", "checked");
			else currentInfo.set("checked", "");
		}//end for
	}
	
	public void setOptionChecked(int loc){
		int size = infoRadio.size();
		if(loc > size || loc < 1) {
			return;
		}
		for(int i=0; i<size;i++){
			Info currentInfo = (Info)infoRadio.getObject((i+1)+"");
			if((i+1) == loc) {
				currentInfo.set("checked", "checked");
			} else {
				currentInfo.set("checked", "");
			}
		}//end for
	}

}

