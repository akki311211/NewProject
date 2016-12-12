/*
 *
 * Class Name	- Event
 * Location		- com.appnetix.app.control.EventHandler
 * @author		- Amit Gokhru
 * @version		- 1.1.1.1,
 * date			- 28/12/2003

 * @List/Names of Class using this class
	*
	*
	*

 * Usage - This class is the wrapper for every event. We will define Event for each seperate unit of
   work that has to be done in order to make data change persistent, i.e if we have to insert data in to differtent table then we have to task one for each table and that two events.

   Information contained by the object of this class

   1. Event type	- This property will define that type of event. Value will be a constant from EevntType Class.

   2. Info Object	- Info object containing the information to be changed

   3. Table Name	- Defines the table name in which info is to be inserted/changed/deleted

   4. ID			- value of primary field.

   5.ForeignTables[]- list of foreign tables associated with this event




 */
package com.home.builderforms;



 public class Event{

	private String eventType		= null;		//defines the eventType for the event
	private Info info				= null;		//defines the information with this event
	private String tableName		= null;		//defines the table Name for this event
	private String tableNameAsInRequest		= null;		//store the table name as in request to compare if its a children table
	private Object ID				= null;		//defines the ID field value of the changed info
	private String[] foreignTable	= null;		 //list of foreign table
	private String sTriggerFormName	= null;
	Integer gModifierUserNo			= null;
	private String fromRestAPI		= null;	//REST_API Changes
	private String requestFrom		= null;	//EXTERNAL_FORM_BUILDER Changes

	public String toString(){
		StringBuffer eventSummary = new StringBuffer();
		eventSummary.append("<<<<<<<<<<Start Event Details For Table "+tableName+" >>>>>>>>>>>");
		eventSummary.append("\neventType="+eventType);
		eventSummary.append("\ninfo="+info);
		eventSummary.append("\ntableName="+tableName);
		eventSummary.append("\ntableNameAsInRequest="+tableNameAsInRequest);
		eventSummary.append("\nID="+ID);
		if(foreignTable!= null){
			for(int i=0;i<foreignTable.length;i++){
				eventSummary.append("\nforeignTable["+i+"]="+foreignTable[i]);
			}
		}
		eventSummary.append("\nsTriggerFormName="+sTriggerFormName);
		eventSummary.append("\ngModifierUserNo="+gModifierUserNo);
		eventSummary.append("\n<<<<<<<<<<End Event Details For Table "+tableName+" >>>>>>>>>>>");
		
		return eventSummary.toString();
	}

	//getter methods

	public String getEventType(){
		return eventType;
	}

	public Info getInfo(){
		return info;
	}

	public String getTableName(){
		return tableName;
	}

	public String getTableNameAsInRequest(){
		return tableNameAsInRequest;
	}

	public Object getID(){
		return ID;
	}

	public String[] getForeignTable(){
		return foreignTable;
	}

	public String getTriggerFormName(){
		return sTriggerFormName;
	}

 	public Integer getModifierUserNo(){
		return gModifierUserNo;

	}


	//setter methods

	public void setEventType(String value){
		eventType	= value;
	}

	public void setTableName(String value){
		tableName	= value;
	}

	public void setTableNameAsInRequest(String value){
		tableNameAsInRequest	= value;
	}


	public void setInfo(Info value){
		info	= value;
	}


	public void setID(Object value){
		ID	= value;
	}

	public void setForeignTable(String[] value){
		foreignTable	= value;
	}

	public void setTriggerFormName(String value){
		sTriggerFormName = value;
	}

	public void setModifierUserNo(Integer value){
		gModifierUserNo = value;
	}
	
	/*REST_API Changes*/
	public void setFromRestAPI(String value){
		fromRestAPI = value;
	}
	
	public String getFromRestAPI(){
		return fromRestAPI;
	}
	/*REST_API Changes*/

	//EXTERNAL_FORM_BUILDER : START
	public String getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	//EXTERNAL_FORM_BUILDER : END
 };