/*
----------------------------------------------------------------------------------------------------
Version No.			Date			By		Against						Function Changed	Comments
----------------------------------------------------------------------------------------------------
D_CAL_E_EndTime101	11/07/2007		Rakesh	Put Combo Box of "End Time"  addHourMinute()
											in place of "Duration" for 
											all items of Calendar.
P_FS_Enh_12Apr10        12/04/2010                  Vikram Raj        added compareTime method to compare time
----------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.home.builderforms.BaseDateTime;
/**
 *   Class for manipulating dataTime information
 *
 *@author     misam
 *@created    November 9, 2001
 */

/*
------------------------------------------------------------------------------------------------------------------------
Version No.	   		Date		 By	             Against	             	                     Function Changed      Comments
------------------------------------------------------------------------------------------------------------------------
P_E_DATE_FORMAT    29/05/2008 Nikhil Verma   Date Format enhancement.
P_DATE_FORMAT		4 June 2008 By Sanjeev K For Date Format
P_DATE_FORMAT      24 JUNE 2008  Neha Rusiya            Added a method getNoOfMonth() in which we pass Date object as a parameter
OTRS_2008102010000722 23-10-08   Rakesh Verma													getDaysBetweenDates() Not showing proper day for event 
------------------------------------------------------------------------------------------------------------------------
 */

public class DateTime extends BaseDateTime implements java.io.Serializable {
	
}
