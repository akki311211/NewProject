package com.home.builderforms;
import com.home.builderforms.*;

/**
 *@author abhishek gupta
 *@version 1.1
 */

/* -------------------------------------------------------------------------------------------------------------
Version No.	              Date		    By			           Function Changed                Comments
-------------------------------------------------------------------------------------------------------------
P_E_MoveToFim_AddlFDD     29Nov2012     Veerpal Singh          Patch
--------------------------------------------------------------------------------------------------------------------
*/

public class HeaderMap {
	private String msName;
	private String msValue;
	private int msOrder;
	//P_E_MoveToFim_AddlFDD starts
	private boolean optional;
	private String recordHeaderPrefix;
	private String recordHeaderSufix;
	//P_E_MoveToFim_AddlFDD ends
	private String tabName;
	
	private HeaderField pHeaderFields;
	
	public HeaderMap(String psName, String psValue, String psOrder, HeaderField pHeaderFields){
		msName			= psName;
		msValue			= psValue;
		msOrder			= psOrder == null?0:Integer.parseInt(psOrder);
		//P_E_MoveToFim_AddlFDD starts
		optional        = false;
		recordHeaderPrefix = "";
		recordHeaderSufix = "";
		//P_E_MoveToFim_AddlFDD ends
		this.pHeaderFields	= pHeaderFields;
	}
	
	public HeaderMap(String psName, String psValue, String psOrder, String psOptional, String psRecordHeaderPrefix, String psRecordHeaderSufix, HeaderField pHeaderFields){
		msName			= psName;
		msValue			= psValue;
		msOrder			= psOrder == null?0:Integer.parseInt(psOrder);
		//P_E_MoveToFim_AddlFDD starts
		optional        = psOptional == null?false:Boolean.parseBoolean(psOptional);
		recordHeaderPrefix = psRecordHeaderPrefix;
		recordHeaderSufix = psRecordHeaderSufix;
		//P_E_MoveToFim_AddlFDD ends
		this.pHeaderFields	= pHeaderFields;
	}
	
	public HeaderMap(String psName, String psValue, String psOrder, String psOptional, String psRecordHeaderPrefix, String psRecordHeaderSufix, HeaderField pHeaderFields, String tabName){
		msName			= psName;
		msValue			= psValue;
		msOrder			= psOrder == null?0:Integer.parseInt(psOrder);
		//P_E_MoveToFim_AddlFDD starts
		optional        = psOptional == null?false:Boolean.parseBoolean(psOptional);
		recordHeaderPrefix = psRecordHeaderPrefix;
		recordHeaderSufix = psRecordHeaderSufix;
		//P_E_MoveToFim_AddlFDD ends
		this.pHeaderFields	= pHeaderFields;
		this.tabName		= tabName;
	}
	
	public String getName(){
		return msName;
	}
	public HeaderField getHeaderFields(){
		return pHeaderFields;
	}
	public String getValue() {
		return msValue;
	}
	public int getOrder() {
		return msOrder;
	}
	//P_E_MoveToFim_AddlFDD starts
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setRecordHeaderPrefix(String recordHeaderPrefix) {
		this.recordHeaderPrefix = recordHeaderPrefix;
	}
	public String getRecordHeaderPrefix() {
		return recordHeaderPrefix;
	}
	public void setRecordHeaderSufix(String recordHeaderSufix) {
		this.recordHeaderSufix = recordHeaderSufix;
	}
	public String getRecordHeaderSufix() {
		return recordHeaderSufix;
	}
	//P_E_MoveToFim_AddlFDD ends
	public String getTabName() {
		return tabName;
	}
}