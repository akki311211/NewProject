/**
 
 *	Name - TextArea.java			
 *	Directory position - com/appnetix/app/util/tagutils

 * @author  Pritish
 * @version 1.1.1.1, 
 * date 2/02/2004
 * 
 * BBEH_CLUSTER_ENVIRONMENT  05/06/2013      Rohit Jain    Class is serialized For cluster Environment.

 */
/**
 *	TextArea is the class used to automatically generate the text area in an html
 *	with some of the necessary formatings.
 */

package com.home.builderforms;


public class TextArea implements java.io.Serializable{
private String value;
private String displayName;
private String rows = "3";
private String cols = "15";
private String jScript;
private int colspan = 1;

/**
 *	Default Constructor.
 */

	public TextArea(){
	}

/**
 *	 This Constructor sets the display name for the text area.
 */

	public TextArea(String displayName){
		this(displayName,"");
	}

/**
 *	 This Constructor sets the display name and value for the text area.
 */

	public TextArea(String displayName, String value){
		this.displayName=displayName;
		this.value=value;
	}

/**
 *	 Returns the value of text area.
 */

	public String getValue(){
		return value;
	}

/**
 *	 Sets the value of text area.
 */

	public void setValue(String val){
		value=val;
	}

/**
 *	 Returns the display name of text area.
 */

	public String getDisplayName(){
		return displayName;
	}

/**
 *	 Sets the display name of text area.
 */

	public void setDisplayName(String val){
		displayName=val;
	}

/**
 *	 Returns the rows of text area.
 */

	public String getRows(){
		return rows;
	}

/**
 *	 Sets the no. of rows of text area.
 */

	public void setRows(String val){
		rows=val;
	}

/**
 *	 Returns the columns of text area.
 */

	public String getCols(){
		return cols;
	}

/**
 *	 Sets the no. of columns of text area.
 */

	public void setCols(String val){
		cols=val;
	}

/**
 *	 Returns the colspan of text area.
 */

	public int getColspan(){
		return colspan;
	}

/**
 *	 Sets the colspan of text area.
 */

	public void setColspan(int val){
		colspan=val;
	}
	
/**
 *	 Sets the javascript into text area.
 */

	public void setJScript(String value){
		jScript	= value;
	}


/**
 *	 Returns the javascript to be used.
 */

	public String getJScript(){
		return jScript;
	}
	
}
  
