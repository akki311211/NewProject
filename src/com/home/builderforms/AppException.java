package com.home.builderforms;

import java.util.ArrayList;

/**
 * This exception is the base class for all the application exceptions. All the 
 * exceptions created in the application should extend this base exception class.
 * The object can hold more than one messages as messages will get stored in the 
 * arraylist. The initial capacity for this arraylist is 5 , but as Arraylist grows
 * automatically it can add more than 5 messages.
 *
 *@author  misam
 *@created November 9, 2001
 */
public class AppException extends Exception {
	/**
	 * This Arraylist act as data storage for messages associated with the 
	 * exception
	 */    	 	
    ArrayList msg = new ArrayList();

    protected int _code = 0;
    protected String message = "";
    /**
     *  Constructor for the AppException object
     */
    public AppException() { }


    /**
     * Constructor for the AppException object with string message which will
     * get added into the message list.
     *
     *@param  str   The message to be added to the MessageList attribute
     */
    public AppException(String str) {
        msg.add(str);
    }
    /**
     *  Gets the messageList attribute of the AppException object
     *
     *@return    The messageList value
     */
    public ArrayList getMessageList() {
        return msg;
    }

	/**
	 * Constructs a SystemException by specifying message directly.
	 */
    /**
     *  Adds a message to the MessageList attribute of the AppException
     *  object
     *
     *@param  str  The message to be added to the MessageList attribute
     */
    public void addToMessageList(String str) {
        msg.add(str);
    }
    /**
     * Contructor with Throwable case to handle throw exception cases
     */
    public AppException(Throwable cause) {
    	this.message = ""; // with no message
    	initCause(cause);
    }
    public AppException(String str, Throwable cause) {
    	msg.add(str);
    	this.message = str; // current error message
    	initCause(cause);
    }
    public String toString(){ // current error message with thrown exception
        StringBuffer msg = new StringBuffer();
        msg.append(this.message);
        if(getCause() != null){
            msg.append(getCause().toString());
        }
        return msg.toString();
        
    }
}
