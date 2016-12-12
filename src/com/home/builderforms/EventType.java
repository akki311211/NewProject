/*
 *
 * Class Name	- EventType
 * Location		- com.appnetix.app.control.EventHandler
 * @author		- Amit Gokhru
 * @version		- 1.1.1.1, 
 * date			- 28/12/2003

 * @List/Names of Class using this class
	* Event.java
	*
	*

 * Usage - This class defines various eventType that the application can have.
 * Various Events that the application support are :-

	1. Create		- event to insert information into persistent data store
	2. Modify		- event to modify information into persistent data store
	3. Delete		- event to delete information from the persistent data store
	4. CreateBatch	- event to insert batch information into the persistent data store
	5. ModifyBatch	- event to modify batch information into the persistent data store
	6. DeleteBatch	- event to delete batch information

	Remark 1:event type 1-3 does not support transaction atomicity while events from 4-6 support 
	atomicity i.e either whole batch is commited to the persistent data store or non.

	Remark 2: These event type must be specified in each and every form which requires submission of 
	the form data by the generic handling functionality under the hidden field named EVENT_TYPE 


 
 */

package com.home.builderforms;

public class EventType{
	

	public final static String  CREATE			= "create";		 	//to insert info
	public final static String  MODIFY			= "modify";			//to modify info
	public final static String  DELETE			= "delete";			//to delete info
	public final static String  COPY				= "copy";			//to copy info
	public final static String  CREATE_BATCH		= "createBatch";	//to insert batch info
	public final static String  MODIFY_BATCH		= "modifyBatch";	//to modify batch info
	public final static String  DELETE_BATCH		= "deleteBatch";	//to delete batch info
	public final static String  COPY_RECURSIVE	= "copyRecursive";			//to copy info


}