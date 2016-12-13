/**
 
 *	Name - Combo.java			
 *	Directory position - com/appnetix/app/util/tagutils

 * @author  Pritish
 * @version 1.1.1.1, 
 * date 2/02/2004
 * 
 * BBEH_CLUSTER_ENVIRONMENT  05/06/2013      Rohit Jain    Class is serialized For cluster Environment.
 * P_FS_Enh_BuilderForm		16Dec2013		Naman Jain		FOrm Builder in Franchise Sales
 */

package com.home.builderforms;


import com.home.builderforms.Info;

public class Combo implements java.io.Serializable {
private String comboValue;
private String displayName;
private String jScript;
private Info infoCombo;
private String multiple;
private String size;
private String disabledOptions;
private String comboClass;
private String comboStyle; //P_B_AB_61473
private boolean comboWithCheckBox; //this attribute will work only when it is used after setter method of multiple attribute i.e. Combo.setMultiple
private boolean showDefault = true; //P_FS_Enh_BuilderForm

/**
 *	Default Constructor.
 */

	public Combo(){
		infoCombo = new Info();
	}

	public String getMultiple(){
		return multiple;
	}
	public void setMultiple(){
		 this.multiple = "true";
		 this.comboWithCheckBox = true;
	}

	public String getSize(){
		return size;
	}

	public void setSize(String size){
		this.size = size;
	}

/**
 *	 This Constructor sets the display name for the combo.
 */

	public Combo(String displayName){
		this(displayName,null);
	}

/**
 *	 This Constructor sets the display name and value for the combo.
 */

	public Combo(String displayName, String comboValue){
		this.displayName=displayName;
		this.comboValue=comboValue;
		infoCombo = new Info();
	}

/**
 *	 Returns the value to be displayed.
 */

	public String getComboValue(){
		return comboValue;
	}

/**
 *	 Returns the options in combo.
 */

	public Info getInfo(){
		return infoCombo;
	}

/**
 *	 Returns the javascript to be used.
 */

	public String getJScript(){
		return jScript;
	}


/**
 *	 Sets the options in combo.
 */

	public void setInfo(Info info){
		infoCombo=info;
	}

/**
 *	 Sets the value to be displayed in combo.
 */

	public void setComboValue(String val){
		comboValue=val;
	}

/**
 *	 Returns the display name of combo.
 */

	public String getDisplayName(){
		return displayName;
	}

/**
 *	 Sets the display name of combo.
 */

	public void setDisplayName(String val){
		displayName=val;
	}

/**
 *	 Sets the new option in combo.
 */

	public void setOption(String key, String value){
		infoCombo.set(key,value);
	}

/**
 *	 Sets the javascript into the combo.
 */

	public void setJScript(String value){
		jScript	= value;
	}
	
	//P_FS_Enh_BuilderForm starts
	public boolean isShowDefault()
	{
		return showDefault;
	}

	public void setShowDefault(boolean showDefault)
	{
		this.showDefault = showDefault;
	}
	//P_FS_Enh_BuilderForm ends
	
	public String getDisabledOptions() {
		return disabledOptions;
	}

	public void setDisabledOptions(String disabledOptions) {
		this.disabledOptions = disabledOptions;
	}
	public String getComboClass() {
		return comboClass;
	}

	public void setComboClass(String comboClass) {
		this.comboClass = comboClass;
	}
	//P_B_AB_61473 start
	public void setComboStyle(String comboStyle) { 
		this.comboStyle = comboStyle;
	}

	public String getComboStyle() {
		return comboStyle;
	}//P_B_AB_61473 end
	
	public boolean isComboWithCheckBox() {
		return comboWithCheckBox;
	}
/**
 * Use this method after combo.setMultiple. This attribute is added to avoid the creation of multi-select combo when
 * class form-control is not mentioned and setMultiple method is used on object of Combo class.  
 * @param comboWithCheckBox
 */
	public void setComboWithCheckBox(boolean comboWithCheckBox) {
		this.comboWithCheckBox = comboWithCheckBox;
	}

}
  
