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

import com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;

import com.appnetix.app.util.*;

import org.apache.log4j.Logger;

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
SC_DIS_B_14671     11 OCT 2012   Karun Handa       bug                                        to apply dateformat
P_SCH_CAL_DAY		03/04/2014	Sanshey Sachdeva	Bug		getDateInTopFormat(),getDateInFullFormat(),getDateInShortFormat()	added offset value for calendar start day in order to display days correctly.
------------------------------------------------------------------------------------------------------------------------
 */

@SuppressWarnings("serial")
public class BaseDateTime extends Object implements java.io.Serializable {
	static Logger logger					= com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BaseDateTime.class);
	public static final String DB_DATETIME 			= "yyyy-MM-dd HH:mm:ss";
	public static final String DB_DATE	 			= "yyyy-MM-dd";
	public static final String STD_DATETIME_FORMAT	= "MMM-dd-yyyy hh:mm a z";
	
	public static final String STD_DATE_FORMAT		= "MMM-dd-yyyy";

//	public static String[] months = {LanguageUtil.getString("January"),LanguageUtil.getString("February"),LanguageUtil.getString("March"),LanguageUtil.getString("April"),LanguageUtil.getString("May"),LanguageUtil.getString("June"),LanguageUtil.getString("July"),LanguageUtil.getString("August"),LanguageUtil.getString("September"),LanguageUtil.getString("October"),LanguageUtil.getString("November"),LanguageUtil.getString("December")};    //P_B_SCH_7438  	added by Prakriti Sharma
	
	//public static String[] monthSortNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//	public static String[] monthSortNames = {LanguageUtil.getString("Jan"),LanguageUtil.getString("Feb"),LanguageUtil.getString("Mar"),LanguageUtil.getString("Apr"),LanguageUtil.getString("May"),LanguageUtil.getString("Jun"),LanguageUtil.getString("Jul"),LanguageUtil.getString("Aug"),LanguageUtil.getString("Sep"),LanguageUtil.getString("Oct"),LanguageUtil.getString("Nov"),LanguageUtil.getString("Dec")};    //P_B_SCH_7438  	added by Prakriti Sharma
	
	public static final int[] monthNoArray = {1,2,3,4,5,6,7,8,9,10,11,12};
	
//	public static String[] daySortArray = { "S", "M", "T", "W", "T", "F", "S" };
	
//	public static String[] dayArray =
//		{ LanguageUtil.getString("Sun"), LanguageUtil.getString("Mon"),LanguageUtil.getString("Tue"),LanguageUtil.getString("Wed"),LanguageUtil.getString("Thu"),LanguageUtil.getString("Fri"),LanguageUtil.getString("Sat"), };    //P_B_SCH_7438  	added by Prakriti Sharma
	
//	public static String[] dayNamesArray = {LanguageUtil.getString("Sunday"), LanguageUtil.getString("Monday"), LanguageUtil.getString("Tuesday"), LanguageUtil.getString("Wednesday"), LanguageUtil.getString("Thursday"),       
//		LanguageUtil.getString("Friday"), LanguageUtil.getString("Saturday"),};                //P_B_SCH_7438  	added by Prakriti Sharma


	/**
     *  Constructor for the DateTime object
     */
    
	public BaseDateTime() { }

/*
	*
	*/
public static long getMinutesFromTimestamps(Timestamp t1,Timestamp t2)
	{
		long millis1	= t1.getTime();
		long millis2	= t2.getTime();
		long dateDiff	= millis2 - millis1;
		long minuteDiff = dateDiff/60000;
		return minuteDiff;
	}
	/**
	
	 * This method will generate the Display Time in Am/PM format 
	 * @param timestamp - comp. timestamp object
	 * @return String - Time in AM/PM format
	 */


	@SuppressWarnings("deprecation")
	public static String getCalendarDisplayTime(Timestamp timestamp)
	{
		StringBuffer returnDateTime=new StringBuffer();
		if(timestamp!=null){

				int hours = timestamp.getHours();
				int min = timestamp.getMinutes();

				if ((hours%12) < 10 && hours!=12)
					returnDateTime.append("0");
				if(hours==12){
					returnDateTime.append((hours));
				}else /*if(hours<24)*/{
					returnDateTime.append((hours%12));
				}
				returnDateTime.append(":");
				
				if (min < 10)
					returnDateTime.append("0");
				returnDateTime.append(min);


				if(hours>=12){
					returnDateTime.append(LanguageUtil.getString("PM"));
				}else{
					returnDateTime.append(LanguageUtil.getString("AM"));
				}
		}
		return returnDateTime.toString();

	}
	
	public static String  getOfficebazarDate(String str){
		if(str == null){

			return "";

		}

		if(str.indexOf("0000") != -1){

			return "";

		}

		if(str.length()>=8 && str.indexOf("-")!=-1){

			StringTokenizer st = new StringTokenizer(str , "-");

			String yy          = st.nextToken();

			String mm          = st.nextToken();

			String dd          = st.nextToken();
			if(mm !=null && mm.length()!=2){
				   mm = "0"+mm;
			}


			str               = dd+"/"+mm+"/"+yy;

		}

		return  str;

	}
	
	/*
	*added by vikas to get the display adte time for db date time format
	*/
	/**
	 * This method will display the Display date Time for db date time format
	 * @param timeStampString - timeStampString object
	 * @return - String date time in db format
	 */
	
	
	public static String getDisplayDateTime(String timeStampString)
	{
		Timestamp timeStamp=getTimestamp(timeStampString,TimeZoneUtils.DB_DATETIME);
		return getDateTime(timeStamp,"dd MMM yyyy HH:mm");
	}

	
	/**
	 * This method will generate the Display date Time in Am/PM format 
	 * @param timestamp - timeStampString object
	 * @return - String Date time in AM/PM format
	 */
	
@SuppressWarnings("deprecation")
public static String getCalendarDisplayDateTime(Timestamp timestamp)
	{
		StringBuffer returnDateTime=new StringBuffer();
		String startTime="";
		if(timestamp!=null){
				returnDateTime.append(getDisplayDate(timestamp)).append(" ");
				int hours = timestamp.getHours();
				int min = timestamp.getMinutes();
				//P_B_44793
		// changed by binu on 16th aug 06
				/*if ((hours%12) < 10 && hours !=12)
					returnDateTime.append("0");
					if(hours !=12)
				returnDateTime.append((hours%12));
				else
				returnDateTime.append((hours));

				returnDateTime.append(":");
				if (min < 10)
					returnDateTime.append("0");
				returnDateTime.append(min);

				if(hours>=12){
					returnDateTime.append(" PM");
				}else{
					returnDateTime.append(" AM");
				}*/
				try{
					SimpleDateFormat sdf=new SimpleDateFormat("kk:mm");
	                Date date=sdf.parse(hours+":"+min);
	                sdf = new SimpleDateFormat("hh:mm a");
	                startTime=sdf.format(date);
	                }
	                catch (Exception e) {
					logger.error(e,e);
					}
				returnDateTime.append(startTime);//P_B_44793
		}
		return returnDateTime.toString();

	}


/**
 * This method will compare dates
 * @param startDate - startdate object 
 * @param endDate - endDate object
 * @return - integer value  -1 if startDate < endDate
 							 0 if startDate = endDate
 							 1 if startDate > endDate
 */


public static int compare(String startDate , String endDate){
	int iReturn		= 0;
	try{

			String startMM		= startDate.substring(0,2);
			String startDD		= startDate.substring(3,5);
			String startYY		= startDate.substring(6);

			String endMM		= endDate.substring(0,2);
			String endDD		= endDate.substring(3,5);
			String endYY		= endDate.substring(6);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			endCal.set(Integer.parseInt(endYY) , Integer.parseInt(endMM)-1 , Integer.parseInt(endDD));

			long milliStart		= startCal.getTimeInMillis() ;
			long milliEnd		= endCal.getTimeInMillis() ;

			if(milliStart > milliEnd){
				iReturn		= 1;
			}else if(milliStart == milliEnd){
				iReturn		= 0;
			}else if (milliStart < milliEnd){
				iReturn		= -1;
			}

	}catch(Exception e){
		logger.error("Exception in compare:" , e);
	}
	return iReturn;
}


/**
 * This method will compare dates
 * @param startDate - startdate object 
 * @param endDate - endDate object
 * @return - integer value  -1 if startDate < endDate
 							 0 if startDate = endDate
 							 1 if startDate > endDate
 */


public static int compareDate(String startDate , String endDate){
	int iReturn		= 0;
	if(StringUtil.isValid(startDate) && StringUtil.isValid(endDate) )
	{
	try{

			String startMM		= startDate.substring(5,7);
			String startDD		= startDate.substring(8,10);
			String startYY		= startDate.substring(0,4);

			String endMM		= endDate.substring(5,7);
			String endDD		= endDate.substring(8,10);
			String endYY		= endDate.substring(0,4);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			endCal.set(Integer.parseInt(endYY) , Integer.parseInt(endMM)-1 , Integer.parseInt(endDD));

			long milliStart		= startCal.getTimeInMillis() ;
			long milliEnd		= endCal.getTimeInMillis() ;

			if(milliStart > milliEnd){
				iReturn		= 1;
			}else if(milliStart == milliEnd){
				iReturn		= 0;
			}else if (milliStart < milliEnd){
				iReturn		= -1;
			}

	}catch(Exception e){
		logger.error("Exception in compare:" , e);
	}
	}
	else
	{
		iReturn=-1;
	}
	return iReturn;
}
/**
 * P_FS_Enh_12Apr10
 * created by Vikram Raj
 * @desc method to compare time
 * @param startTime the starting time to compare
 * @param endTime the end time to compare
 * @return 0 if the times are equal, 1 if the starttime is greater , -1 if the endtime is greater
 */
public static int compareTime(String startTime , String endTime){
	int iReturn		= 0;
	try{

			String startHH		= startTime.substring(0,2);
			String startMM		= startTime.substring(3,5);
			String startSS		= startTime.substring(6,8);

			String endHH		= endTime.substring(0,2);
			String endMM		= endTime.substring(3,5);
			String endSS		= endTime.substring(6,8);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();
                        
			startCal.set(Calendar.HOUR,Integer.parseInt(startHH));
                        startCal.set(Calendar.MINUTE,Integer.parseInt(startMM));
                        startCal.set(Calendar.SECOND,Integer.parseInt(startSS));
                        
                        endCal.set(Calendar.HOUR,Integer.parseInt(endHH));
                        endCal.set(Calendar.MINUTE,Integer.parseInt(endMM));
                        endCal.set(Calendar.SECOND,Integer.parseInt(endSS));
                        
                                

			
			long milliStart		= startCal.getTimeInMillis() ;
			long milliEnd		= endCal.getTimeInMillis() ;
                        
			if(milliStart > milliEnd){
				iReturn		= 1;
			}else if(milliStart == milliEnd){
				iReturn		= 0;
			}else if (milliStart < milliEnd){
				iReturn		= -1;
			}

	}catch(Exception e){
		logger.error("Exception in compare:" , e);
	}
	return iReturn;
}
/**
 * This method will return if the date is weekday otherwise false.
 * @param date - date in mm-dd-yyyy
 * @return - boolean value true/false
 */



@SuppressWarnings("static-access")
public static boolean isWeekDay(String date){

	boolean bReturn		= false;
	try{
	String mm			= date.substring(0,2);
	String dd			= date.substring(3,5);
	String yy			= date.substring(6);



	Calendar toCal	= Calendar.getInstance();
	toCal.set(Calendar.DATE , Integer.parseInt(dd));
	toCal.set(Calendar.MONTH , Integer.parseInt(mm) -1);
	toCal.set(Calendar.YEAR , Integer.parseInt(yy));

	int weekDay		= toCal.get(toCal.DAY_OF_WEEK);
	if(weekDay == toCal.MONDAY ||weekDay == toCal.TUESDAY || weekDay == toCal.WEDNESDAY ||weekDay == toCal.THURSDAY || weekDay == toCal.FRIDAY ){
		bReturn		= true;
	}else{
		bReturn		= false;
	}
	}catch(Exception e){
		logger.error("Exception in isweekDay :" , e);
	}
	return bReturn;
}


/**
 * this method Calculates the  date after adding interval into the date time string of db format
 * @param psInPictureDateTime - date time string into db format
 * @param pnMagnitudeDays - integer no to add into the datetime string
 * @param calendarAttribute - attribute to add Calendar.MINUTE,Claendar.MONTH etc
 * @return - date 
 */



	public static String addDateTimeInDBFormat(String psInPictureDateTime, int pnMagnitudeDays,int calendarAttribute) {

		String sCalculatedDate 		= "";
		try{

			
			
		// Traetment required for next and previous date links.
			int [] nArrDateTimeDBFormat = TimeZoneUtils.breakTimeStamp(psInPictureDateTime);
			Calendar calObj			= Calendar.getInstance();
			calObj.set(
						nArrDateTimeDBFormat[0],
						nArrDateTimeDBFormat[1],
						nArrDateTimeDBFormat[2],
						nArrDateTimeDBFormat[3],
						nArrDateTimeDBFormat[4],
						nArrDateTimeDBFormat[5]
					);
			//adding the interval into the calendar attribute
			calObj.add(calendarAttribute, pnMagnitudeDays);
			sCalculatedDate 		= getDBFormatDateTime(calObj);
		}catch(Exception e){
			logger.error("Exception in addDateTimeInDBFormat:" , e);
		}
		return sCalculatedDate;
	}
	/*
	*method to compare the dates
	*/
	
	
/**
 * This method to compare the dates
 * @param date1 -  date1 object
 * @param format1 - format1 object
 * @param date2 - date2 object 
 * @param format2 - format2 object 
 * @return -    -1 if dateOne < dateTwo
 				 0 if dateOne = dateTwo
 				 1 if dateOne > dateTwo
 */
	
	
  public static int compareDates(String date1,String format1, String date2,String format2) {
		if(date1 == null || date2 == null || date1.equals("") || date2.equals("")){
			return -1;
		}
        try {
			long dateOne	= getMilliseconds(date1,format1);
			long dateTwo	= getMilliseconds(date2,format2);
		//	logger.info("date1 :" + dateOne);
		//	logger.info("date2 :" + dateTwo);
			if(dateOne > dateTwo){
				return 1;
			}else if(dateOne < dateTwo){
				return -1;
			}else if(dateOne == dateTwo){
				return 0;
			}
        } catch (Exception e) {
			logger.error("Exception in compareDates:" , e);
        }
        return -1;
    }
  
  
  /**
   * This method will return the no of weekdays between two given dates excluding the startDate and
   * including the end Date
   * @param startDate : date startDate should be in mm-dd-yyyy fromat
   * @param endDate date  endDate should be in mm-dd-yyyy format
   * @return - Integer  no of weekdays 
   */

  
  public static int getWeekDayNo(String startDate, String endDate){


	int returnInt		= 0; // as looping is one less
		try{
			String startMM		= startDate.substring(0,2);
			String startDD		= startDate.substring(3,5);
			String startYY		= startDate.substring(6);

			String endMM		= endDate.substring(0,2);
			String endDD		= endDate.substring(3,5);
			String endYY		= endDate.substring(6);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			endCal.set(Integer.parseInt(endYY) , Integer.parseInt(endMM)-1 , Integer.parseInt(endDD));
			if(!startCal.after(endCal)){
			while(!startCal.after(endCal)){
				int weekDay	= startCal.get(Calendar.DAY_OF_WEEK);

				if(weekDay == Calendar.MONDAY ||weekDay == Calendar.TUESDAY || weekDay == Calendar.WEDNESDAY ||weekDay == Calendar.THURSDAY || weekDay == Calendar.FRIDAY ){
					returnInt++;

				}
				startCal.add(Calendar.DAY_OF_MONTH , 1);
		}

			}else{
				//for befor date
				return -1;
			}
		}catch(Exception e){
			logger.error("Exception in getWeekDayNo(String startDate , String endDate) : " , e);
			returnInt = 0;
		}
		return returnInt;
	}
  
  /**
   * @author Yogesh Tiwari
   * @purpose This method can be use to convert any date from YYYY-MM-DD format to DD/MM/YY format.    
   * @param dateString in YYYY-MM-DD format
   * @return dateString in DD/MM/YY format   
   */
  public static String  DBDateFormatToDisplayDateFormat(String dateString){
	  if(dateString == null){
		  return "";
	  }
	  if(dateString.indexOf("0000") != -1){
		  return "";
	  }
	  if(dateString.length()>=8 && dateString.indexOf("-")!=-1){
		  StringTokenizer st = new StringTokenizer(dateString , "-");
		  String yy          = st.nextToken();
		  String mm          = st.nextToken();
		  String dd          = st.nextToken();
		  if(mm !=null && mm.length()!=2){
			  mm = "0"+mm;
		  }
		  dateString= dd+"/"+mm+"/"+yy;
	  }
	  return  dateString;
  }
  
  
/**
 * This method will return no of weeks between startdate and endDate
 * @param startDate	- startDate object must be in mm-dd-yyyy format
 * @param endDate	- endDate object must be in mm-dd-yyyy format
 * @return - Integer no. of weeks 
 */  
public static int getNoOfWeeks(String startDate , String endDate){
	int iReturn		= 0;
	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	try{
			String startMM		= startDate.substring(0,2);
			String startDD		= startDate.substring(3,5);
			String startYY		= startDate.substring(6);

			Calendar startCal	= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));



			int weekDay	= startCal.get(Calendar.DAY_OF_WEEK);

			int increment		= 0;

			if(weekDay == Calendar.SUNDAY ){
				increment	= 7;
			}else if(weekDay == Calendar.MONDAY){
				increment	= 6;
			}else if(weekDay == Calendar.TUESDAY){
				increment	= 5;
			}else if( weekDay == Calendar.WEDNESDAY){
				increment	= 4;
			}else if(weekDay == Calendar.THURSDAY){
				increment	= 3;
			}else if(weekDay == Calendar.FRIDAY ){
				increment	= 2;
			}else if(weekDay == Calendar.SATURDAY ){
				increment	= 1;
			}

			startCal.add(Calendar.DATE , increment);

			String mm		= Integer.toString(startCal.get(Calendar.MONTH) + 1);
			String dd		= Integer.toString(startCal.get(Calendar.DATE));
			String yy		= Integer.toString(startCal.get(Calendar.YEAR));

			if(mm.length() == 1){
				mm = "0" + mm;
			}

			if(dd.length() == 1){
				dd = "0" + dd;
			}

			String nextSunDate	= (mm) + "-" + dd + "-" + yy;

			logger.info("nextSunDate :" + nextSunDate);
			logger.info("endDate :" + endDate);
			int cmpDate		= (int)compare(nextSunDate , endDate );
			logger.info("cmpDate:" + cmpDate);
			if(cmpDate <= 0){
				//p_caringtransitions_cal_sync  start
				//int noOfDays		= (int)getDaysBetweenDates(nextSunDate , endDate)-1;
				int noOfDays		= (int)getDaysBetweenDates(DateTime.getRequiredFormat(nextSunDate,"MM-dd-yyyy",_baseConstants.DISPLAY_FORMAT),DateTime.getRequiredFormat( endDate,"MM-dd-yyyy",_baseConstants.DISPLAY_FORMAT) )-1;
				//p_caringtransitions_cal_sync  end
				logger.info("noOfDays:" + noOfDays);
				iReturn				= (noOfDays/7) + 1;
			}

		}catch(Exception e){
			logger.error("Exception in getWeekDayNo(String startDate , String endDate) : startDate=" +startDate+ " endDate="+endDate , e);
			iReturn		= 0;
		}

	return iReturn;
}



/**
 * This method will return  the day of week for the given date
 * @param date - date object must be in mm-dd-yyyy format
 * @return Integer -  day for the given date 
 */


public static String getDayOfWeek(String date){
	int iReturn		= 0;

	try{
			String startMM		= date.substring(0,2);
			String startDD		= date.substring(3,5);
			String startYY		= date.substring(6);

			Calendar startCal	= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			 iReturn	= startCal.get(Calendar.DAY_OF_WEEK);
		}catch(Exception e){
			logger.error("Exception in getDayOfWeek(String date ) : " , e);

		}
	return Integer.toString(iReturn);
}



/**
 * This method will return no of month between startdate and endDate
 * @param startDate : startDate object must be in mm-dd-yyyy format
 * @param endDate : endDate object must be in mm-dd-yyyy ormat
 * @return Integer no of month
 */




public static int getNoOfMonth(String startDate , String endDate){
	int iReturn		= 0;

	try{
		int startYear		= Integer.parseInt(startDate.substring(6));

		int endYear			= Integer.parseInt(endDate.substring(6));

		int startMonth		= Integer.parseInt(startDate.substring(0,2));

		int endMonth		= Integer.parseInt(endDate.substring(0,2));
	
		if(startYear == endYear){
			iReturn			= endMonth - startMonth;

		}else{
			int diff		= endYear - startYear;
			iReturn			= 12 - startMonth;
			iReturn			= iReturn + endMonth;
			iReturn			= iReturn + (12* (diff -1));

		}
	}catch(Exception e){
		logger.error("Exception in getNoOfMonth :" , e);
	}

	return iReturn;
}



/**
 * This method will return no of month between startMonth and endMonth
 * @return Integer no of month
 */



public static int getNoOfMonth(Date startDate , Date endDate){
	int iReturn		= 0;

	try{
		int startYear		= com.appnetix.app.util.DateUtil.getYear(startDate);
	
		int endYear			= com.appnetix.app.util.DateUtil.getYear(endDate);

		int startMonth		= com.appnetix.app.util.DateUtil.getMonth(startDate);

		int endMonth		= com.appnetix.app.util.DateUtil.getMonth(endDate);
	
		if(startYear == endYear){
			iReturn			= endMonth - startMonth;

		}else{
			int diff		= endYear - startYear;
			iReturn			= 12 - startMonth;
			iReturn			= iReturn + endMonth;
			iReturn			= iReturn + (12* (diff -1));

		}
	}catch(Exception e){
		logger.error("Exception in getNoOfMonth :" , e);
	}

	return iReturn;
}

/**
 * This method will return day of the month for the given Date
 * @param date : date object must be in mm-dd-yyyy format
 * @return String 
 */

public static String getDayOfMonth(String date){
	String sReturn		= "01";
	try{
		sReturn			= date.substring(3,5);
	}catch(Exception e){
		logger.error("Exception in getDayOfMonth :" , e);
	}
	return sReturn;
}


/**
 * This method will return the maximum no of weeks of the month for the given date
 * @param date : date object must be in mm-dd-yyyy format
 * @return String - max. no of weeks
 */


public static String getMaxWeekOfMonth(String date){
	String sReturn		= "";

	try{
			String startMM		= date.substring(0,2);
			String startDD		= date.substring(3,5);
			String startYY		= date.substring(6);

			Calendar startCal	= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			 sReturn	= Integer.toString(startCal.getActualMaximum(Calendar.WEEK_OF_MONTH));
		}catch(Exception e){
			logger.error("Exception in getMaxWeekOfMonth(String date ) : " , e);

		}
	return sReturn;

}


/**
 * This method will return the nth day of week ie(first sunday of month)
 * @param date : date object - must be in mm-dd-yyyy
 * @param nthOcr : nthOcr object
 * @param dayNo : dayN object 
 * @return String - first sunday of month
 */


@SuppressWarnings("unused")
public static String getNthWeekDayOfMonth(String date,String nthOcr,String dayNo){
	String sReturn		= "";
	if(date==null || nthOcr==null || dayNo==null )
		return sReturn;
	try{
			String startMM		= date.substring(0,2);
			String startDD		= date.substring(3,5);
			String startYY		= date.substring(6);

			int  startMMInt=Integer.parseInt(startMM);
			int  startDDInt=Integer.parseInt(startDD);
			int  startYYInt=Integer.parseInt(startYY);
			int  dayNoInt=Integer.parseInt(dayNo);
			int  nthOcrInt=Integer.parseInt(nthOcr);
		Calendar startCal	= Calendar.getInstance();

			startCal.set(startYYInt , startMMInt-1 , 1);
			int maxDays=startCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int nth=0;
			for(int i=1;i<=maxDays;i++){
				startCal.set(Calendar.DATE,i);

				if(startCal.get(Calendar.DAY_OF_WEEK)==dayNoInt){
					nth++;

				}

				if(nth==nthOcrInt){
					sReturn=""+i;
					break;
				}



			}
		}catch(Exception e){
			logger.error("Exception in getWeekOfMonth(String date ) : " , e);
		}
	return sReturn;
}


/**
 * This method will return the no of week of the month for the given date
 * @param date : date object must be in mm-dd-yyyy format
 * @return String - no of week
 */


public static String getWeekOfMonth(String date){
	String sReturn		= "";

	try{
			String startMM		= date.substring(0,2);
			String startDD		= date.substring(3,5);
			String startYY		= date.substring(6);

			Calendar startCal	= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			 sReturn	= Integer.toString(startCal.get(Calendar.WEEK_OF_MONTH));
		}catch(Exception e){
			logger.error("Exception in getWeekOfMonth(String date ) : " , e);

		}
	return sReturn;

}

/**
 * This method will return the no of week of the year for the given date
 * @param date : date object must be in mm-dd-yyyy or mm/dd/yyyy format
 * @return String - no of week
 */

public static String getWeekOfYear(String date){
	String sReturn		= "";

	try{
			String startMM		= date.substring(0,2);
			String startDD		= date.substring(3,5);
			String startYY		= date.substring(6);

			Calendar startCal	= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM)-1 , Integer.parseInt(startDD));

			 sReturn	= Integer.toString(startCal.get(Calendar.WEEK_OF_YEAR));
		}catch(Exception e){
			logger.error("Exception in getWeekOfYear(String date ) : " , e);

		}
	return sReturn;

}

/**
 * This method will return no of years between startDate and end Date
 * @param startDate :  startDate object must be in mm-dd-yyyy
 * @param endDate   :  endDate object must be in mm-dd-yyyy
 * @return Integer  - No of years
 */


public static int getNoOfYear(String startDate , String endDate){
	int iReturn		 = 0;
	try{

		int startYear		= Integer.parseInt(startDate.substring(6));
		int endYear			= Integer.parseInt(endDate.substring(6));

		iReturn				= endYear - startYear;

	}catch(Exception e){
		logger.error("Exception in getNoOfYear:" , e);
	}
	return iReturn;
}


/**
 * This method will return month(01-12) of the date.
 * @param date : date object must be in mm-dd-yyyy
 * @return String 
 */



public static String getMonth(String date){
	String sReturn		= "01";
	try{
		sReturn			= date.substring(0,2);
	}catch(Exception e){
		logger.error("Exception in getDayOfMonth :" , e);
	}
	return sReturn;
}

   /**
    * Gets the standardDateString for the Current Date
    * @return String : return date in MM/dd/yyyy HH:mm:ss
    */

    public static String getStandardDateString() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = formatter.format(now);
        return date;
    }
    /**
     *  This method will return the standardDateString for the given Date
     *@param  date : date object 
     *@return  String : return date in MM/dd/yyyy HH:mm:ss
     */
    public static String getStandardDateString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dateStr = null;
        try {
            dateStr = formatterStd.format(date);
        } catch (Exception e) {
        }
        if (dateStr == null) {
            return getStandardDateString();
        }
        return dateStr;
    }

    
    /**
    * This method will return the standardDateOnlyString attribute of the DateTime class
    * @return  String : return date in dd-MM-yyyy
    */
    
    
	public static String getStandardDatesString() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(now);
        return date;
    }
	
	
/**
 * This method will return the date String attribute in MM/dd/yyyy format
 * @param date : date object
 * @return : String in MM/dd/yyyy format
 */
	
	
	
 public static String  getDateInDesiredFormat(String date) {
		 if(date != null && date.length() == 11){
		 String month=date.substring(0,3);
		 String returnDate="";
		 String returnMonth="";

		String[] monthArray = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

		int index=0;

		for(;index<monthArray.length;index++){
			if(monthArray[index].equals(month))
				break;
		}

		index++;

		if(index<10)
			returnMonth="0"+index;
        else
			returnMonth=""+index;

		returnDate=returnMonth+"/"+date.substring(4,6)+"/"+date.substring(7,11);

		return returnDate;
		 }else{
			 return "";
		 }
	 }
 
 
 /**
  *  Gets the date String attribute in yyyy-mm-dd HH:mm:ss format
  *@param date: date object 
  *@return  The date string in mm-dd-yyyy HH:mm:ss format
	 */
 
 
 
 public static String  changeDateFormatOfTimeStamp(String date) {

		 if(date != null && date.length() == 19){
		 String year=date.substring(0,4);
		 String month=date.substring(5,7);
		 String day=date.substring(8,10);
		 String time=date.substring(11,19);
		 String returnDate="";


		String[] monthArray = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

		month = monthArray[Integer.parseInt(month) - 1];
		returnDate=month + "-" + day + "-" + year + time;

		return returnDate;
		 }else{
			 return "";
		 }
	 }
 
 
 /**
  *  Gets the sqlTimestampString attribute of the DateTime class
  *@return  String - date in the yyyy-MM-dd HH:mm:ss format
  */ 
 
 
 public static String getSqlTimestampString() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(now);
        return date;
    }
 
 
    /**
     *  Gets the sqlTimestamp attribute of the DateTime class
     *@return date  The sqlTimestamp value
     */
 
 
    public static Timestamp getSqlTimestamp() {
        return new Timestamp(new Date().getTime());
    }
    
    
/**
 * This method will return date in the yyyy-MM-dd HH:mm:ss format
 * @param stdDate : stdDate object
 * @return String
 */
    
    
    public static String convertToTimestampString(String stdDate) {
	        if (stdDate == null || stdDate.trim().equals("")) {
	            return getSqlTimestampString();
	        }
	        return getSqlTimestamp(stdDate).toString();
    }

    
/**
 * Gets the sqlTimestamp attribute of the DateTime class
 * @param stdDate : stdDate object 
 * @return String - return date in yyyy-MM-dd HH:mm:ss format
 */

    public static Timestamp getSqlTimestamp(String stdDate) {
       if (stdDate == null) {
            return null;
        }
		Debug.println("Phase I");
        if (stdDate.length() < "MM/dd/yyyy HH:mm".length() &&
                stdDate.length() < "M/d/yyyy HH:mm".length()) {
            stdDate = stdDate + " 00:00";
        }
        SimpleDateFormat formatterSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //P_E_DATE_FORMAT Changed By Nikhil Verma for export Section on 29/05/2008
        SimpleDateFormat formatterStd = new SimpleDateFormat(MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT+" HH:mm");
        Date dateObj = null;
        String finalDate = null;
        ParsePosition pos = new ParsePosition(0);

        try {
            Debug.println("Got stdDate = " + stdDate);
            dateObj = formatterStd.parse(stdDate, pos);
            if (dateObj != null) {
                finalDate = formatterSql.format(dateObj).toString();
            } else {
                Debug.println("Could not parse the standard Date String");
            }
        } catch (Exception e) {
            Debug.println("Exception in parsing standard date string");
        }
		Debug.println("Phase II");

        if (finalDate == null) {
            return null;
        }
		Debug.println("Phase III");

        return java.sql.Timestamp.valueOf(finalDate);
    }
    
    
 /**
  * Gets the sqlTimestampString attribute of the DateTime class
  * @param date : date object 
  * @return String -  date in MM-dd-yyyy HH:mm:ss format
  */
    
    
     public static String getSqlTimestampString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatterStd = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String dateStr = null;

        if (date != null) {
            try {
                dateStr = formatterStd.format(date);
            } catch (Exception e) {
            }
        }

        if (dateStr == null) {
            return "";
        }

        return dateStr;
    }
     
     
 /**
  * Gets the standardDateOnlyString attribute of the DateTime class
  * @return String -date in MM/dd/yyyy format
  */
     
     
   
    public static String getStandardDateOnlyString() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String date = formatter.format(now);
        return date;
    }
  
    
    
    
  /**
   * Gets the standardTimeOnlyString attribute of the DateTime class
   * @return String - The standard Time in HH:mm:ss
   */
    
    
    public static String getStandardTimeOnlyString() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String time = formatter.format(now);
        return time;
    }


 /**
  * Gets the standardDateOnlyString attribute of the DateTime class
  * @param str : str object
  * @return String - The standard Date in MM/dd/yyyy format
  */
    
    
    public static String getStandardDateOnlyString(String str) {
        if (str == null) {
            return "";
        }
        String dateStr = null;
        try {
            SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterStd.parse(str, pos);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            dateStr = formatter.format(date);
        } catch (Exception e) {
        }
        if (dateStr == null) {
            dateStr = "";
        }

        return dateStr;
    }


/**
 * Gets the standardDateOnlyString2 attribute of the DateTime class
 * @param str - str object 
 * @return String - The standard Date in MMMM d, yyyy format
 */
    
   
    public static String getStandardDateOnlyString2(String str) {
        if (str == null) {
            return "";
        }
        String dateStr = null;
        try {
            SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterStd.parse(str, pos);
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
            dateStr = formatter.format(date);
        } catch (Exception e) {
        }
        if (dateStr == null) {
            dateStr = "";
        }

        return dateStr;
    }

    
/**
 * Gets the date attribute of the DateTime class
 * @param str : str object
 * @return String - The date in  MMMM d, yyyy format
 */

  
    public static String getDate(String str) {
        if (str == null) {
            return "";
        }
        String dateStr = null;
        try {
            str = str.substring(0, 10);
            SimpleDateFormat formatterStd = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterStd.parse(str, pos);
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
            dateStr = formatter.format(date);
        } catch (Exception e) {
        }
        if (dateStr == null) {
            dateStr = "";
        }

        return dateStr;
    }


	/**
 * recieves the date as dd/mm/yyyy and returns 
 * @param str : str object
 * @return String - The date in  mm/dd/yyyy  format
 */

  
    public static String getDate_mmddyyyy(String str) {
        if (str == null) {
            return "";
        }
        String date1="";
        try {
            str = str.substring(0, 10);
            	String date[] =str.split("/");
				 date1=date[1]+"/"+date[0]+"/"+date[2];
        } catch (Exception e) {
        }
       

        return date1;
    }


  /**
   * Gets the standardTimeOnlyString attribute of the DateTime class
   * @param str : str object 
   * @return String  -  The standard Time in HH:mm a format
   */
    public static String getStandardTimeOnlyString(String str) {
        if (str == null) {
            return "";
        }

        SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterStd.parse(str, pos);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
        String timeStr = formatter.format(date);
        if (timeStr == null) {
            timeStr = "";
        }

        return timeStr;
    }

  /**
   * Gets the stringFromTimeStamp attribute of the DateTime class
   * @param timestamp : timestamp object
   * @return String - The String in MM/dd/yyyy HH:mm:ss format
   */
   
    public static String getStringFromTimeStamp(String timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat formatterStd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterSql = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dateObj = null;
        String finalDate = null;
        ParsePosition pos = new ParsePosition(0);

        try {
            dateObj = formatterStd.parse(timestamp, pos);
            if (dateObj != null) {
                finalDate = formatterSql.format(dateObj).toString();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (dateObj == null) {
            return "";
        }
        return finalDate;
    }

/**
 * Gets the monthString attribute of the DateTime class
 * @param month : integer month 
 * @return String - The month value
 */
    

      
    
    public static String getMonthString(int month) {
        String[] monthArray = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        return monthArray[month];
    }

/**
 * Gets the dayOfWeekString attribute of the DateTime class
 * @param day : integer day
 * @return String - The day Of Week value
 */ 
    
    
    
       public static String getDayOfWeekString(int day) {
        String[] dayArray = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday",};
        return dayArray[day];
    }

       /*
        *  This method compares two dates "presentDate" and "pastDate" .If the difference between these two dates is more than
        *  "numberOfMonths" then this return "true" else "false".
        */
       /**
        *   Method
        *
        *@param  presentDate      Parameter
        *@param  pastDate         Parameter
        *@param  numberOfMonths   Parameter
        *@return                  Return Value
        */
    /**
     * this method will return the value in milli second 
     * @param str : str object 
     * @return long - time in milli second 
     */
       public static long getMilliseconds(String str) {
        long time = 0;

        if (str == null) {
            return time;
        }

        try {
			if (str.length() < "MM/dd/yyyy HH:mm".length() &&
					str.length() < "M/d/yyyy HH:mm".length()) {
				str = str + " 00:00";
			}

            SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterStd.parse(str, pos);
            time = date.getTime();
        } catch (Exception e) {
        }
        return time;
    }
       /**
        * this method will return the value in milli second 
        * @param str : str object 
        * @param givenFormat : givenFormat object 
        * @return long - time in milli second 
        */
	 public static long getMilliseconds(String str, String givenFormat) {
        long time = 0;
        if (str == null || givenFormat==null) {
            return time;
        }
        try {
            SimpleDateFormat formatterStd = new SimpleDateFormat(givenFormat);
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterStd.parse(str, pos);
            time = date.getTime();
        } catch (Exception e) {
        }
        return time;
    }


	 /**
	  * This method will compare dates
	  * @param date1 - date1 object 
	  * @param date2 - date2 object
	  * @return -  value  -1 if dateOne < dateTwo
	  							 0 if dateOne = dateTwo
	  							 1 if dateOne > dateTwo
	  */

	  public static int compareDates(String date1, String date2) {
		if(date1 == null || date2 == null || date1.equals("") || date2.equals("")){
			return -1;
		}
        try {
			long dateOne	= getMilliseconds(date1);
			long dateTwo	= getMilliseconds(date2);
			if(dateOne > dateTwo){
				return 1;
			}else if(dateOne < dateTwo){
				return -1;
			}else if(dateOne == dateTwo){
				return 0;
			}
        } catch (Exception e) {
        }
        return -1;
    }

/**
 * This method will return the string in yyyy-MM-dd format
 * @param stdDate : stdDate object 
 * @return String - date in yyyy-MM-dd format
 */

	 public static String convertToTimestampDateOnlyString(String stdDate) {
        if (stdDate == null) {
            return "";
        }
        SimpleDateFormat formatterSql = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy");
        Date dateObj = null;
        String finalDate = null;
        ParsePosition pos = new ParsePosition(0);

        try {
            dateObj = formatterStd.parse(stdDate, pos);
            if (dateObj != null) {
                finalDate = formatterSql.format(dateObj).toString();
            }
        } catch (Exception e) {
            Debug.println("Exception " + e);
        }

        if (dateObj == null) {
            return "";
        }
        return finalDate;
    }

	/**
     *  This method compares two dates "presentDate" and "pastDate" .If the difference between these two dates is more than
     *  "numberOfMonths" then this return "true" else "false".
     *@param  presentDate  :  presentDate object 
     *@param  pastDate     :    pastDate object
     *@param  numberOfMonths : numberOfMonths object    
     *@return  value  
     */

    public static boolean validateDateDifference(String presentDate, String pastDate, int numberOfMonths) {

        try {
            Debug.println(presentDate + " " + pastDate + " " + numberOfMonths);
            SimpleDateFormat formatterStd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date formattedPresentDate = formatterStd.parse(presentDate, pos);
            Debug.println(formattedPresentDate.toString());
            pos.setIndex(0);
            Date formattedPastDate = formatterStd.parse(pastDate, pos);
            Debug.println(formattedPastDate.toString());
            long dateDifferenceInSecs = (formattedPresentDate.getTime() - formattedPastDate.getTime()) / 1000;
            if (dateDifferenceInSecs < 0) {
                return false;
            }
            float numberOfYears = ((float) numberOfMonths) / 12.0f;
            float numberOfSecs = numberOfYears * 365 * 86400;
            float diff = dateDifferenceInSecs - numberOfSecs;
            if (diff >= 0.0f) {
                return true;
            }

            return false;
        } catch (Exception e) {
            Debug.println("Exception " + e);
            return false;
        }
    }

	/**
	*  Adds a feature to the Date attribute of the DateTime class
	*
	*@param  strDate : The feature to be added to the Date attribute
	*@param  numberOfDays :  The feature to be added to the Date attribute
	*@return   :    Return Value
	*/
   public static String addDate(String strDate, int numberOfDays) {
	   return addDate(strDate, Calendar.DATE, numberOfDays);
   }
	/**
	*  Adds a feature to the Date attribute of the DateTime class
	*
	*@param  strDate   :    The feature to be added to the Date attribute
	*@param  numberOfDays :  The feature to be added to the Date attribute
	*@return   :  Return Value
	*/
   public static String addMonth(String strDate, int numberOfMonths) {
	   return addDate(strDate, Calendar.MONTH, numberOfMonths);
   }
/**
	*  Adds a feature to add Date attribute of the DateTime class
	*
	*@param  strDate :   strDate object
	*@param  pnField : pnField integer
	*@param  number : number integer
	*@return     :  date
	*/
   public static String addDate(String strDate, int pnField, int number) {
	if(strDate == null){
		return null;
	}
	   Date nextDate = null;
	   try {
		if (strDate.length() < "MM/dd/yyyy HH:mm".length() &&
			   strDate.length() < "M/d/yyyy HH:mm".length()) {
			strDate = strDate + " 00:00";
		}
		   SimpleDateFormat formatterStd
					= new SimpleDateFormat("MM/dd/yyyy HH:mm");
		   ParsePosition pos = new ParsePosition(0);
		   Date date = formatterStd.parse(strDate, pos);
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(date);
		   //cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH) + numberOfDays);
		   cal.add(pnField, number);
		   nextDate = cal.getTime();
	   } catch (Exception e) {
		   Debug.println("Exception in adding dates " + e);
	   }
	   return getStandardDateString(nextDate);
	}
/**
 * This method will return the object of timestamp of currentdate
 * @return : timestamp object 
 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(new Date().getTime());
	}
/**
 *  This method will return the object of timestamp of given date and format
 * @param strDate : strDate object
 * @param formatString : formatString object 
 * @return : timestamp object 
 */
	public static Timestamp getTimestamp(String strDate, String formatString)
	{
		Timestamp timestamp = null;
		try
		{
			if(strDate != null && !strDate.equals("0000-00-00")){
				SimpleDateFormat formatter = new SimpleDateFormat(formatString);
				ParsePosition pos = new ParsePosition(0);
				Date date = formatter.parse(strDate, pos);
				timestamp = new Timestamp(date.getTime());
			}
		}
		catch(Exception e)
		{
			Debug.println("DateTime : getTimestamp: "+strDate +" "+e);
		}
		return timestamp;
	}
	/**
	 * This method will return the object of timestamp of given date
	 * @param strDate : strDate object
	 * @return : timestamp object 
	 */

	public static Timestamp getTimestamp(String strDate)
	{
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		if (strDate == null ) return null;
	//	String formatString1 = "MM/dd/yyyy";
	//	String formatString2 = "MM/dd/yyyy HH:mm";
	//	String formatString3 = "MM/dd/yyyy HH:mm:ss";

		if (strDate.length() == _baseConstants.DISPLAY_FORMAT_HMS.length())
		{
			return getTimestamp(strDate,_baseConstants.DISPLAY_FORMAT_HMS);
		}
		if (strDate.length() == _baseConstants.DISPLAY_FORMAT_HM.length())
		{
			return getTimestamp(strDate,_baseConstants.DISPLAY_FORMAT_HM);
		}
		if ( strDate.length() == _baseConstants.DISPLAY_FORMAT.length() -1)
		{
			strDate = "0"+strDate;
		}
		if (strDate.length() == _baseConstants.DISPLAY_FORMAT.length())
		{
			return getTimestamp(strDate,_baseConstants.DISPLAY_FORMAT);
		}
		Debug.println("Unknown format Date :"+strDate);
		return null;
	}


/**
 * This method will add the timestamp object 
 * @param timestamp : timestamp object 
 * @param numberOfDays : numberOfDays integer
 * @return : add timestamp object 
 */

	public static Timestamp addTimestamp(Timestamp timestamp,int numberOfDays)
	{
		long numberOfMillis = (long)numberOfDays * 86400 * 1000;
		return new Timestamp(timestamp.getTime() + numberOfMillis);
	}
/**
 * This method will return the datetime for the timestamp object 
 * @param timestamp : timestamp object 
 * @param formatString : formatString object 
 * @return : the date time
 */
	public static String getDateTime(Timestamp timestamp,String formatString)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		return formatter.format(timestamp);
	}
/**
 * This method will display the date for the timestamp 
 * @param timestamp : timestamp object 
 * @return : date 
 */
	public static String getDisplayDate(Timestamp timestamp)
	{
		return getDateTime(timestamp,"dd MMM yyyy");
	}
/**
 * This method will display the date time  for the timestamp 
 * @param timestamp : timestamp object 
 * @return : date
 */
	public static String getDisplayDateTime(Timestamp timestamp)
	{
		return getDateTime(timestamp,"dd MMM yyyy HH:mm");
	}
    // Method Overriden to consider the date in TimeStamp format
    // added by suneel

    /**
     * Method Overriden to consider the date in TimeStamp format
     * @param str : str object 
     * @param time : time object 
     * @return String - date in mm/dd/yyyy format
     */
	public static String getDisplayDate(String str,String time){
            String returnString	 = "";
            BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
        try {
            if(time!=null && time.equals("time") && str != null && str.length() > 10){

                   str = str.substring(0,10).trim();

            }
            else if ("time".equals(time) && str != null && str.length() == 10){
                str = DateUtil.formatDate(str, DateUtil.DB_FORMAT);
            }
            if(str == null ||str.length() < 10 )
            {
            	return "";
            }
            else
            {
            	if (str.indexOf(' ')>1)str=str.substring(0,str.indexOf(' '));
            	String dd	 = str.substring(8);
            	String mm	 = str.substring(5,7);
            	String yy	= str.substring(0,4);
            	//SC_DIS_B_14671
            	if(_baseConstants.DISPLAY_FORMAT.equalsIgnoreCase("MM/dd/yyyy"))
            	returnString = mm+ "/" + dd + "/" + yy;
            	else if(_baseConstants.DISPLAY_FORMAT.equalsIgnoreCase("dd/MM/yyyy"))
            	returnString = dd+ "/" + mm + "/" + yy;
            	//SC_DIS_B_14671 ENDS
            }
            if(returnString.equals("00/00/0000")){
            	returnString = "Not Available";
            }
            if(returnString.equals("00-00-0000")){
            	returnString = "Not Available";
            }
        } finally {
        }
		return returnString;

        }

//	 
//	 added by suneel
	/**
	 * Old method which is overridden  This method return only accept 10 digit date format
	 * @param str : str object 
	 * @return date
	 */
	public static String getDisplayDate(String str)
	{
		return getDisplayDate(str,null);
	}

/**
 * This method will used to compare the timestamp 
 * @param t1 : t1 object
 * @param t2 : t2 object
 * @return -1 if millis1 < millis2
	  		0 if millis1 = millis2
	  		1 if millis1 > millis2
 */
	public static int compareTimestamps(Timestamp t1,Timestamp t2)
	{
		long millis1 = t1.getTime();
		long millis2 = t2.getTime();
		return (millis1 == millis2) ? 0 : ( (millis1 > millis2) ? 1 : -1);
	}
/**
 * This method will used to compare the timestamp 
 * @param t1 : t1 object
 * @param t2 : t2 object
 * @param monthDifference : integer monthDifference
 * @return true if diff>= 0 else false 
 * 		   
 */
	public static boolean compareTimestamps(Timestamp t1,Timestamp t2, int monthDifference)
	{
		long millis1	= t1.getTime();
		long millis2	= t2.getTime();
		long dateDiff	= millis2 - millis1;
		float monthDiff = (((float) monthDifference) / 12.0f) * 365 * 86400;
		float diff		= dateDiff - monthDiff;
		if(diff >= 0){
			return true;
		}
		return false;
	}
	/**
	 * this method will return the time of start of the day for the timestamp
	 * @param timestamp : timestamp object 
	 * @return : time
	 */
		public static Timestamp getStartOfDay(Timestamp timestamp)
	{
		if(timestamp == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return new Timestamp(cal.getTime().getTime());
	}
/**
  * this method will return the time of end of the day for the timestamp
	 * @param timestamp : timestamp object 
	 * @return : time
 */
	public static Timestamp getEndOfDay(Timestamp timestamp)
	{
		if(timestamp == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		cal.set(Calendar.MILLISECOND,999);
		return new Timestamp(cal.getTime().getTime());

	}
/**
 * This method will return the period 
 * @param period : period object 
 * @return String : period
 */
	
    public static String getDisplayPeriod(String period){
        String returnPeriod= "  ";
        if (period == null){
            return 	returnPeriod;
        } else if (period.length() > 0){
            returnPeriod= period.substring(6)+" "+MultiTenancyUtil.getTenantConstants().months[Integer.parseInt(period.substring(4,6))-1] + " " + period.substring(0,4);
        }
        return returnPeriod;
    }
/**
 * this method will return the date in the given format
 * @param date ; date object 
 * @param formatString : formatString object
 * @return String ; date in the given format
 */
	public static String formatDate(Date date, String formatString) {
		if(formatString == null){
			formatString			= "MM/dd/yyyy HH:mm";
		}
        SimpleDateFormat formatter	= new SimpleDateFormat(formatString);
        String sDate					= formatter.format(date);
        return sDate;
    }

/**
 * this method will return the date in the DB format
 * @param pObjCal : pObjCal object
 * @return : value in YYYY-MM-DD format.
 */
	public static String getDBFormatDate(Calendar pObjCal){
		int nTempYear	= pObjCal.get(Calendar.YEAR);
		int nTempMonth	= pObjCal.get(Calendar.MONTH) + 1;
		int nTempDay	= pObjCal.get(Calendar.DAY_OF_MONTH);

		String sTempMonth		= (nTempMonth<10) ? "0"+nTempMonth : ""+nTempMonth;
		String sTempDay			= (nTempDay<10) ? "0"+nTempDay : ""+nTempDay;
		return (nTempYear + "-" + sTempMonth + "-" + sTempDay);
	}
	/**
	 * this method will return the date & time in the DB format
	 * @param pObjCal : pObjCal object 
	 * @return : value in YYYY-MM-DD HH:MM:SS
	 */
	public static String getDBFormatDateTime(Calendar pObjCal){
		int nTempYear	= pObjCal.get(Calendar.YEAR);
		int nTempMonth	= pObjCal.get(Calendar.MONTH) + 1;
		int nTempDay	= pObjCal.get(Calendar.DAY_OF_MONTH);

		int nTempHrs	= pObjCal.get(Calendar.HOUR_OF_DAY);
		int nTempMins	= pObjCal.get(Calendar.MINUTE);
		int nTempSecs	= pObjCal.get(Calendar.SECOND);

		String sTempMonth	= (nTempMonth<10) ? "0"+nTempMonth : ""+nTempMonth;
		String sTempDay		= (nTempDay<10) ? "0"+nTempDay : ""+nTempDay;

		String sTempHrs		= (nTempHrs<10) ? "0"+nTempHrs : ""+nTempHrs;
		String sTempMins	= (nTempMins<10) ? "0"+nTempMins : ""+nTempMins;
		String sTempSecs	= (nTempSecs<10) ? "0"+nTempSecs : ""+nTempSecs;
		return (nTempYear + "-" + sTempMonth + "-" + sTempDay +" "+sTempHrs+":"+sTempMins+":"+sTempSecs);
	}

	/**
	 * this method will return the date for the given format
	 * @param strDate : strDate object
	 * @param givenFormat : givenFormat object
	 * @return : date
	 */
	public static Date getDateFromString(String strDate, String givenFormat){
		return getDateFromString(strDate, givenFormat, TimeZone.getDefault());
	}
	/**
	 * this method will return the date for the given format
	 * @param strDate : strDate object
	 * @param givenFormat : givenFormat object
	 * @param pUserTZ : pUserTZ object
	 * @return : date
	 */
	public static Date getDateFromString(String strDate, String givenFormat, TimeZone pUserTZ ){
		Date date = null;
		try{
			if(strDate != null && !strDate.equals("0000-00-00")){
				SimpleDateFormat formatter = new SimpleDateFormat(givenFormat);
				formatter.setTimeZone(pUserTZ);
				ParsePosition pos = new ParsePosition(0);
				date = formatter.parse(strDate, pos);
			}
		}
		catch(Exception e){
			Debug.println("DateTime : getTimestamp: "+strDate +" "+e);
		}
		return date;
	}

	/**
	 * this method Converts given date in string to required format in string
	 * @param psGivenDate : psGivenDate object 
	 * @param psGivenFormat : psGivenFormat object
	 * @param sRequiredFormat : sRequiredFormat object
	 * @return String : date in required format
	 */
	 
	public static String getRequiredFormat(String psGivenDate, String psGivenFormat, String sRequiredFormat){
		String sReturnValue = "";
		try{
			if(sReturnValue != null){
				SimpleDateFormat formatter = new SimpleDateFormat(psGivenFormat);
				ParsePosition pos = new ParsePosition(0);
				Date date = formatter.parse(psGivenDate, pos);
				formatter.applyPattern(sRequiredFormat);
		        sReturnValue = formatter.format(date);
			}
		}
		catch(Exception e){
			Debug.println("DateTime : sReturnValue: "+psGivenDate +" "+e);
		}
		return sReturnValue;
	}
	/**
	 * this method will return  date in DB format  after adding the no of days
	 * @param psInPictureDate : psInPictureDate object 
	 * @param pnMagnitudeDays : pnMagnitudeDays integer  
	 * @return String : date in DB format after adding days
	 */
	 
	
	
	public static String addDateDBFormat(String psInPictureDate, int pnMagnitudeDays){
		String sCalculatedDate 		= "";
		try{
		// Traetment required for next and previous date links.
			int [] nArrDateDBFormat = TimeZoneUtils.breakDate(psInPictureDate);
			Calendar calObj			= Calendar.getInstance();
			calObj.set(nArrDateDBFormat[0], nArrDateDBFormat[1], nArrDateDBFormat[2]);
			// To get last date of week
			calObj.add(Calendar.DAY_OF_MONTH, pnMagnitudeDays);
			sCalculatedDate 		= getDBFormatDate(calObj);
		}catch(Exception e){
			Debug.print(e);
		}
		return sCalculatedDate;
	}

	/**
	 * this method will return  date & time  in DB format  after adding the no of days
	 * @param psInPictureDateTime : psInPictureDateTime object 
	 * @param pnMagnitudeDays : pnMagnitudeDays integer  
	 * @return String : date in DB format after adding days
	 */
	public static String addDateTimeDBFormat(String psInPictureDateTime, int pnMagnitudeDays){
		String sCalculatedDate 		= "";
		try{

		// Traetment required for next and previous date links.
			int [] nArrDateTimeDBFormat = TimeZoneUtils.breakTimeStamp(psInPictureDateTime);
			Calendar calObj			= Calendar.getInstance();
			calObj.set(
						nArrDateTimeDBFormat[0],
						nArrDateTimeDBFormat[1],
						nArrDateTimeDBFormat[2],
						nArrDateTimeDBFormat[3],
						nArrDateTimeDBFormat[4],
						nArrDateTimeDBFormat[5]
					);
			// To get last date of week
			calObj.add(Calendar.DAY_OF_MONTH, pnMagnitudeDays);

			sCalculatedDate 		= getDBFormatDateTime(calObj);
		}catch(Exception e){
			Debug.print(e);
		}
		return sCalculatedDate;
	}

   /**
    * this method will return the current time in milli sec.
    * @return : time in milli sec.
    */
    public static long getCurrentMillis() {
        return (new java.util.Date()).getTime();
    }
/**
 * This method will return the calendar in which time in milli sec.
 * @param pObjCal : pObjCal object
 * @param psReqFormat : psReqFormat object
 * @return String 
 */
	public static String formatCalendar(Calendar pObjCal, String psReqFormat){
//		Debug.println("pObjCal.toString() -->"+TimeZoneUtils.calToString(pObjCal));
        SimpleDateFormat formatter	= new SimpleDateFormat(psReqFormat);
//		formatter.setCalendar(pObjCal);
		formatter.setTimeZone(pObjCal.getTimeZone());

		long lCalendarMillis = pObjCal.getTimeInMillis();

	//	lCalendarMillis	= lCalendarMillis + lDiffInMillis;

//		Debug.println("pObjCal.toString() -->"+TimeZoneUtils.calToString(pObjCal));
//		Debug.println("pObjCal.getTime() return a date object-->"+pObjCal.getTime());
        String 	sDate			= formatter.format(new java.util.Date(lCalendarMillis));
//		Debug.println("sDate derived from Calendar-->"+sDate);
		return sDate;
	}


	/**
	 * This method Checks whether the year has changed in given dates
	 * Returns True if year has changed elsewise false.
	 * @param psDate1 : psDate1  object
	 * @param psDate2 : psDate2 object 
	 * @return boolean 
	 */
	

	public static boolean checkYearChange(String psDate1, String psDate2) {
		boolean bYearChanged = false;
		try{
			//if(PortalUtils.isBadString(psDate1) || PortalUtils.isBadString(psDate2)){
			if(BaseUtils.isBadString(psDate1) || BaseUtils.isBadString(psDate2)){     //For Product_Seperation_BL By Amar Singh.
				return bYearChanged;
			}else{
				int nYear1 = Integer.parseInt(psDate1.substring(0, 4));
				int nYear2 = Integer.parseInt(psDate2.substring(0, 4));
				if(nYear1 != nYear2){
					bYearChanged = true;
				}
			}
		}catch(Exception e){
			Debug.print(e);
		}
		return bYearChanged;
	}

		public static String getSimleDateChange(String str) {

		if(str == null){

			return "";

		}

		if(str.length()>9 && str.indexOf("/")!=-1){

			StringTokenizer st = new StringTokenizer(str , "/");

			String mm          = st.nextToken();

			String dd          = st.nextToken();

			String yy          = st.nextToken();

			str               =  yy+"-"+mm+"-"+dd;

		}

		return  str;

    }


/**
 * this method will return date in mm/dd/yy format
 * @param str : str object 
 * @return String : date in mm/dd/yy fomat
 */
	public static String getSimleDateChangeBack(String str) {

		if(str == null){

			return "";

		}

		if(str.indexOf("0000") != -1){

			return "";

		}

		if(str.length()>=8 && str.indexOf("-")!=-1){

			StringTokenizer st = new StringTokenizer(str , "-");

			String yy          = st.nextToken();

			String mm          = st.nextToken();

			String dd          = st.nextToken();
			if(mm !=null && mm.length()!=2){
				   mm = "0"+mm;
			}


			str               =  mm+"/"+dd+"/"+yy;

		}

		return  str;

    }


/**
 * This method will return the date in DB FORMAT (yy-mm-dd)
 * @param str : str object
 * @return String : date in YY-MM-DD format
 */

	public static String  getDBDate(String str){
		String returnString			= null;
		
                if(str!=null && str.length()==9)
                    str="0"+str;
		if(str == null || str.length()!=10){

		}else{
			try{
				String mm		= str.substring(0,2);
				String dd		= str.substring(3,5);
				String yy		= str.substring(6);
			

			returnString	= yy+ "-" + mm + "-" + dd;
			}catch(Exception e){
				returnString	= null;
			}
			
		}

		return returnString;
	}

/**
 * this Method calculate the number of weekdays in the supplied month. Format of supplied string
	should be in YYYYMM format
 * @param month : month object 
 * @return Integer : no of weekdays
 */
	
	public static int getWeekdayNumber(String month){

		int returnInt	= 0;
		try{
		if(month != null && month.length() == 6){

			String yy		= month.substring(0,4);
			String mm		= month.substring(4);
			int monthInt	= Integer.parseInt(mm);
			int noOfDays	= 30;

			if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 ||monthInt == 8 || monthInt == 10 || monthInt == 12 ){
				noOfDays	= 31;
			}else{
				noOfDays	= 30;
			}

			String startDate	= mm + "-01-" + yy;
			String endDate		= mm + "-" + noOfDays + "-" + yy;

			returnInt = getWeekdayNumber(startDate , endDate);

		}
		}catch(Exception e){
			Debug.println("Exception in getWeekdaynumber(String month) : " + e);
		}
		return returnInt;
	}

	/**
	 * this Method calculate the number of weekdays between supplied months. Format of supplied string
	should be in YYYYMM format
	 * @param startMonth : startmonth object
	 * @param endMonth : endMonth object	
	 * @return Integer : no of weekdays
	 */
	
	public static int getWeekdayNumberYYYYMM(String startMonth , String endMonth){

		int returnInt	= 0;
		try{
		if(startMonth != null && startMonth.length() == 6 && endMonth != null && endMonth.length() == 6){

			String year		= startMonth.substring(0,4);
			String month	= startMonth.substring(4);
			int monthInt	= Integer.parseInt(month);


			String startDate	= month + "-01-" + year;

			year			= endMonth.substring(0,4);
			month			= endMonth.substring(4);
			monthInt		= Integer.parseInt(month);

			int noOfDays	= 30;

			if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 ||monthInt == 8 || monthInt == 10 || monthInt == 12 ){
				noOfDays	= 31;
			}else{
				noOfDays	= 30;
			}



			String endDate		= month + "-" + noOfDays + "-" + year;

			returnInt = getWeekdayNumber(startDate , endDate);

		}
		}catch(Exception e){
			Debug.println("Exception in getWeekdaynumber(String startMonth , String endMonth) : " + e);
		}

		return returnInt;
	}

/**
 * This method will calculate the number of weekday between the to dates
	supplied dates should be in the format mm-dd-yyyy
 * @param startDate : startDate object
 * @param endDate : endDate object
 * @return Integer : no of weekdays
 */
	
	public static int getWeekdayNumber(String startDate , String endDate){
		int returnInt		= 0;
		try{
			String startMM		= startDate.substring(0,2);
			String startDD		= startDate.substring(3,5);
			String startYY		= startDate.substring(6);

			String endMM		= endDate.substring(0,2);
			String endDD		= endDate.substring(3,5);
			String endYY		= endDate.substring(6);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM) , Integer.parseInt(startDD));

			endCal.set(Integer.parseInt(endYY) , Integer.parseInt(endMM) , Integer.parseInt(endDD));

			while(!startCal.equals(endCal)){

				int weekDay	= startCal.get(Calendar.DAY_OF_WEEK);
				if(weekDay == Calendar.MONDAY || weekDay == Calendar.TUESDAY || weekDay == Calendar.WEDNESDAY || weekDay == Calendar.THURSDAY || weekDay == Calendar.FRIDAY){
					returnInt++;
				}
				startCal.add(Calendar.DAY_OF_MONTH , 1);
			}
		}catch(Exception e){
			Debug.println("Exception in getWeekdayNumber(String startDate , String endDate) : " + e);
			returnInt = 0;
		}
		return returnInt;
	}

	/**
	 * This Method calculate the number of weekends in the supplied month. Format of supplied string
	should be in YYYYMM format
	 * @param month : month object 
	 * @return integer : no of weekends
	 */
	/**
	
*/

	public static int getWeekendNumber(String month){

		int returnInt	= 0;
		try{
		if(month != null && month.length() == 6){

			String year		= month.substring(0,4);
			String mm		= month.substring(4);
			int monthInt	= Integer.parseInt(mm);
			int noOfDays	= 30;

			if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 ||monthInt == 8 || monthInt == 10 || monthInt == 12 ){
				noOfDays	= 31;
			}else{
				noOfDays	= 30;
			}

			String startDate	= mm + "-01-" + year;
			String endDate		= mm + "-" + noOfDays + "-" + year;

			returnInt = getWeekendNumber(startDate , endDate);

		}
		}catch(Exception e){
			Debug.println("Exception in getWeekendNumber(String month) : " + e);
			returnInt = 0;
		}
		return returnInt;
	}

	/**
	 * This Method calculate the number of weekends between supplied months. Format of supplied string
	 	should be in YYYYMM format
	 * @param startMonth : startMonth object
	 * @param endMonth : endMonth object
	 * @return integer : no of weekends
	 */
	public static int getWeekendNumberYYYYMM(String startMonth , String endMonth){

		int returnInt	= 0;
		try{
		if(startMonth != null && startMonth.length() == 6 && endMonth != null && endMonth.length() == 6){

			String year		= startMonth.substring(0,4);
			String month	= startMonth.substring(4);
			int monthInt	= Integer.parseInt(month);


			String startDate	= month + "-01-" + year;

			year			= endMonth.substring(0,4);
			month			= endMonth.substring(4);
			monthInt		= Integer.parseInt(month);

			int noOfDays	= 30;

			if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 ||monthInt == 8 || monthInt == 10 || monthInt == 12 ){
				noOfDays	= 31;
			}else{
				noOfDays	= 30;
			}



			String endDate		= month + "-" + noOfDays + "-" + year;

			returnInt = getWeekendNumber(startDate , endDate);

		}

		}catch(Exception e){
			Debug.println("Exception in getWeekendNumber(String startMonth , String endMonth) : " + e);
			returnInt = 0;
		}

		return returnInt;
	}
/**
 * This method will calculate the number of weekend between the dates
	supplied dates should be in the format mm-dd-yyyy
 * @param startDate : startDate object
 * @param endDate : endDate object
 * @return integer : no of weekend
 */
	
	
	public static int getWeekendNumber(String startDate , String endDate){
		int returnInt		= 0;
		try{
			String startMM		= startDate.substring(0,2);
			String startDD		= startDate.substring(3,5);
			String startYY		= startDate.substring(6);

			String endMM		= endDate.substring(0,2);
			String endDD		= endDate.substring(3,5);
			String endYY		= endDate.substring(6);

			Calendar startCal	= Calendar.getInstance();
			Calendar endCal		= Calendar.getInstance();

			startCal.set(Integer.parseInt(startYY) , Integer.parseInt(startMM) , Integer.parseInt(startDD));

			endCal.set(Integer.parseInt(endYY) , Integer.parseInt(endMM) , Integer.parseInt(endDD));

			while(!startCal.equals(endCal)){

				int weekDay	= startCal.get(Calendar.DAY_OF_WEEK);
				if(weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY){
					returnInt++;
				}
				startCal.add(Calendar.DAY_OF_MONTH , 1);
			}
		}catch(Exception e){
			Debug.println("Exception in getWeekendNumber(String startDate , String endDate) : " + e);
			returnInt = 0;
		}
		return returnInt;
	}
/**
 * This method will return the no of days between dates supplied dates should be in the format mm-dd-yyyy
 * @param fromDate : fromDate object
 * @param toDate : toDate object 
 * @return long : no of days
 */
	public static long getDaysBetweenDates(String fromDate , String toDate){
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		long diff		= 0;
		try{
			String mm		= null;
			String dd		= null;
			String yy		= null;

			if(fromDate != null  && fromDate.length() == 10 && toDate != null && toDate.length() == 10)
			{
				//P_DATE_FORMAT Sanjeev K
				if(!"calendar2".equals(_baseConstants.CALENDAR_INDEX))
				{
					dd			= fromDate.substring(0,2);
					mm			= fromDate.substring(3,5);
				}
				else
				{
					mm			= fromDate.substring(0,2);
					dd			= fromDate.substring(3,5);
				}
//				mm			= fromDate.substring(0,2);
//				dd			= fromDate.substring(3,5);
				yy			= fromDate.substring(6);

				Calendar fromCal	= Calendar.getInstance();
				fromCal.set(Calendar.DATE , Integer.parseInt(dd));
				//OTRS_2008102010000722 Starts
				fromCal.set(Calendar.MONTH , Integer.parseInt(mm)-1);
				//OTRS_2008102010000722 Ends
				fromCal.set(Calendar.YEAR , Integer.parseInt(yy));
				//P_DATE_FORMAT Sanjeev K
				if(!"calendar2".equals(_baseConstants.CALENDAR_INDEX))
				{
					dd			= toDate.substring(0,2);
					mm			= toDate.substring(3,5);
				}
				else
				{
					mm			= toDate.substring(0,2);
					dd			= toDate.substring(3,5);
				}
//				mm			= toDate.substring(0,2);
//				dd			= toDate.substring(3,5);
				yy			= toDate.substring(6);

				Calendar toCal	= Calendar.getInstance();
				toCal.set(Calendar.DATE , Integer.parseInt(dd));
				//OTRS_2008102010000722 Starts
				toCal.set(Calendar.MONTH , Integer.parseInt(mm)-1);
				////OTRS_2008102010000722 Ends
				toCal.set(Calendar.YEAR , Integer.parseInt(yy));

				long fromMillis			= fromCal.getTimeInMillis();
				long toMillis			= toCal.getTimeInMillis();

				long diffMillis			= toMillis - fromMillis;

				diff					= diffMillis /(1000*60*60*24);

				diff++; //for including the last day

			}
		}catch(Exception e){
			Debug.print(e);
		}

		return diff;

	}
	
	
/**
 * This method will return the month 
 * @param str : str object
 * @return String : month value
 */
	public static String getMonthValue(String str){
		String returnString				= "";

		if(str == null ){
		}else{
			if(str.equalsIgnoreCase("1")){
				returnString	="Jan";
			}else if(str.equalsIgnoreCase("2")){
				returnString	="Feb";
			}else if(str.equalsIgnoreCase("3")){
				returnString	="Mar";
			}else if(str.equalsIgnoreCase("4")){
				returnString	="Apr";
			}else if(str.equalsIgnoreCase("5")){
				returnString	="May";
			}else if(str.equalsIgnoreCase("6")){
				returnString	="Jun";
			}else if(str.equalsIgnoreCase("7")){
				returnString	="Jul";
			}else if(str.equalsIgnoreCase("8")){
				returnString	="Aug";
			}else if(str.equalsIgnoreCase("9")){
				returnString	="Sep";
			}else if(str.equalsIgnoreCase("10")){
				returnString	="Oct";
			}else if(str.equalsIgnoreCase("11")){
				returnString	="Nov";
			}else if(str.equalsIgnoreCase("12")){
				returnString	="Dec";
			}

		}

		return returnString;

	}


/**
 * This method will return the date in db format 
 * @param date : date object 
 * @return String : date in db format
 */
	@SuppressWarnings("unused")
	public static String  getDateInDBFormat(String date) {
		 if(date != null && date.length() == 11){
		 String month=date.substring(3,6);
		 String returnDate="";
		 String returnMonth="";

		String[] monthArray = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
		String[] monthNumArray = {"01", "02", "03", "04",
                "05", "06", "07", "08",
                "09", "10", "11", "12"};

		int index=0;

		for(;index<monthArray.length;index++){
			if(monthArray[index].equals(month))
			{
				break;

				}
		}

		index++;

		if(index<10)
			returnMonth="0"+index;
        else
			returnMonth=""+index;

		returnDate=date.substring(7,11)+"-"+returnMonth+"-"+date.substring(0,2);

		return returnDate;
		 }else{
			 return "";
		 }
	 }

	///////////function to change date in mm/dd/yyyy format

	public static String convertDate(String date)
	{
		StringBuffer displayDate=new StringBuffer();
		displayDate.append( date.substring(5,10) );
		displayDate.append("-");
		displayDate.append( date.substring(0,4) );
		displayDate.append( " ");
		return displayDate.toString().replaceAll("-","/");

	}

	///////////function to change time(HH:mm:ss) in AM-PM format

	public static String convertTime(String dateTime)
	{
		String sTime = dateTime.substring(0,2);
		String sMinute = dateTime.substring(3,5);
							
		if (sTime != null)
			sTime = sTime.trim();

		if (sMinute != null)
			sMinute = sMinute.trim();

		int scheduledTimeInt = Integer.parseInt(sTime);	
		int scheduledMinuteInt = Integer.parseInt(sMinute);
		int apmInt = 0;

		if(scheduledTimeInt > 12)
		{
			scheduledTimeInt=scheduledTimeInt-12;
			apmInt=1;
		}

		if(scheduledTimeInt == 12)
		{
			apmInt=1;
		}

		//SafeGuard-20120612-580 Start
		if(scheduledTimeInt == 0)
		{
			scheduledTimeInt=12;
		}
		//SafeGuard-20120612-580 End
		
		StringBuffer displayTime = new StringBuffer();

		if(scheduledTimeInt < 10)
		{
			displayTime.append("0");
			displayTime.append(scheduledTimeInt);
		}else
			displayTime.append(scheduledTimeInt);

		displayTime.append(":");

		if(scheduledMinuteInt < 10)
		{
			displayTime.append("0");
			displayTime.append(scheduledMinuteInt);
		}else
			displayTime.append(scheduledMinuteInt);

		displayTime.append(" ");

		if(apmInt == 0)
		{
			displayTime.append(LanguageUtil.getString("AM"));
		}else
			displayTime.append(LanguageUtil.getString("PM"));
			
		return displayTime.toString();

	}

	// D_CAL_E_EndTime101 starts
	//function to add hour and minute in a string date provided format of date
	public static Calendar addHourMinute(String dateTime,int hour,int minute,String format)
	{
		Calendar c	= Calendar.getInstance();
		try{
			DateFormat df = new SimpleDateFormat (format);
			Date d = df.parse (dateTime);
			c.setTime (d);
			c.add (Calendar.HOUR, hour);
			c.add (Calendar.MINUTE, minute);
		} catch (Exception e) {
				e.printStackTrace();
		}
		return(c);
	}//end here
	
	///////////function toadd hour and minute in a stringdate provided format of date

	public static String addHourMinuteAndReturnDate(String dateTime,int hour,int minute,String format)
	{
	Calendar c	= Calendar.getInstance();
	String nextDate = dateTime;
	try{
	DateFormat df = new SimpleDateFormat (format);
	Date d = df.parse (dateTime);
	
	c.setTime (d);
	//System.out.println("Before adding" +c);
	c.add (Calendar.HOUR, hour);
	c.add (Calendar.MINUTE, minute);
			String mm		= Integer.toString(c.get(Calendar.MONTH) + 1);
			String dd		= Integer.toString(c.get(Calendar.DATE));
			String yy		= Integer.toString(c.get(Calendar.YEAR));
			String hr		= Integer.toString(c.get(Calendar.HOUR));
			String min		= Integer.toString(c.get(Calendar.MINUTE));
			String ampm		= Integer.toString(c.get(Calendar.AM_PM));


			if(mm.length() == 1){
				mm = "0" + mm;
			}

			if(dd.length() == 1){
				dd = "0" + dd;
			}

			if(hr.length() == 1){
				hr = "0" + hr;
			}

			if(min.length() == 1){
				min = "0" + min;
			}

			if(ampm!=null && ampm.equals("0")){
				ampm = "AM";
			}else if(ampm!=null && ampm.equals("1")){
				ampm = "PM";
			}

			if(ampm!=null && ampm.equals("PM") && hr!=null && hr.equals("00")){
			hr = "12";
			}
			
			nextDate	= DateUtil.formatDate(yy+"-"+mm + "-" + dd)+ " " + hr + ":" + min + " " + ampm;
	//int mins = c.get (Calendar.MINUTE);
	//System.out.println("After adding" +c);
	} catch (Exception e) {
            e.printStackTrace();
    }
	return(nextDate);
	}//end here

	
	// D_CAL_E_EndTime101 ends
	
	/**
	 * SCH_E_170620081002
	 * 
	 * method: get24HoursFormatTime
	 * @param timestamp
	 * @return String
	 * 
	 * added by: Siddharth Singh
	 * date: Jun 17, 2008
	 * purpose: To get time in 24 hours format
	 */
	@SuppressWarnings("deprecation")
	public static String get24HoursFormatTime(Timestamp timestamp)
	{
		StringBuffer returnDateTime=new StringBuffer();
		if(timestamp!=null)
		{
			int hours = timestamp.getHours();
			int min = timestamp.getMinutes();
				
			if (hours < 10)
			{
				returnDateTime.append("0");
			}
			returnDateTime.append(hours);
			returnDateTime.append(":");
			
			if (min < 10)
			{
				returnDateTime.append("0");
			}
			returnDateTime.append(min);
		}
		return returnDateTime.toString();
	}
	
	/**
	 * SCH_E_170620081002
	 * 
	 * method: get24HoursFormatTime
	 * @param timestamp
	 * @return String
	 * 
	 * added by: Siddharth Singh
	 * date: Jun 17, 2008
	 * purpose: To get time in 24 hours format
	 */
	public static String convertTO24HoursFormat(String fullTimeStr)
	{
		StringBuffer returnDateTime=new StringBuffer();
		String timePrefixStr = "";
		String timeStr = "";
		System.out.println("fullTimeStr before----------->>> "+fullTimeStr);
		if(fullTimeStr!=null && fullTimeStr.contains("M") && fullTimeStr.length()==7)
		{
			fullTimeStr = "0"+fullTimeStr;
		}
		if(fullTimeStr!=null && fullTimeStr.length()>=8)
		{
			int len = fullTimeStr.length();
			if(len>=8)
			{
				timePrefixStr = fullTimeStr.substring(0, len-8);
				timeStr = fullTimeStr.substring(len-8, len);
			}
			
			int hours = Integer.parseInt(timeStr.substring(0,2));
			int min = Integer.parseInt(timeStr.substring(3,5));
			String apm = timeStr.substring(6,8);
	
			if(apm!=null && apm.trim().equalsIgnoreCase("AM") && hours==12)
			{
				hours = 0;
			}
			
			if(apm!=null && apm.trim().equalsIgnoreCase("PM") && hours!=12)
			{
				hours = hours + 12;
			}
			
			returnDateTime.append(timePrefixStr);
			returnDateTime.append(" ");
			
			if (hours < 10)
			{
				returnDateTime.append("0");
			}
			returnDateTime.append(hours);
			returnDateTime.append(":");
			
			if (min < 10)
			{
				returnDateTime.append("0");
			}
			returnDateTime.append(min);
		}

		System.out.println("returnDateTime after----------->>> "+returnDateTime);

		return returnDateTime.toString().trim();
	}
	
	/**
	 * SCH_E_170620081002
	 * 
	 * method: get24HoursFormatDateTime
	 * @param timestamp
	 * @return String
	 * 
	 * added by: Siddharth Singh
	 * date: Jun 17, 2008
	 * purpose: To get date time in 24 hours format
	 */
	@SuppressWarnings("deprecation")
	public static String get24HoursFormatDateTime(Timestamp timestamp)
	{
		StringBuffer returnDateTime=new StringBuffer();
		if(timestamp!=null)
		{
				returnDateTime.append(getDisplayDate(timestamp)).append(" ");
				int hours = timestamp.getHours();
				int min = timestamp.getMinutes();

				if (hours < 10)
				{
					returnDateTime.append("0");
				}
				returnDateTime.append(hours);
				returnDateTime.append(":");
				
				if (min < 10)
				{
					returnDateTime.append("0");
				}
				returnDateTime.append(min);
				
		}
		return returnDateTime.toString();

	}
	
	public static String getSimleDateChangeFromOfficebazarFormat(String str) {

		if(str == null){
			return "";
		}

		if(str.length()>9 && str.indexOf("/")!=-1){

			StringTokenizer st = new StringTokenizer(str , "/");

			String dd          = st.nextToken();
			String mm          = st.nextToken();
			String yy          = st.nextToken();

			str               =  yy+"-"+mm+"-"+dd;

		}
		return  str;
	}
	
	// P_int_B_41791 Starts
	/**
		 * @author	: Rakesh Verma
		 * param	: date1
		 * param	: format1
		 * param	: date2
		 * return	: minute difference between given dates.
	*/
	
	 public static long getMinutesFromDates(String date1,String format1, String date2,String format2) {
		if(date1 == null || date2 == null || date1.equals("") || date2.equals("")){
			return -1;
		}
        try {
			long millis1	= getMilliseconds(date1,format1);
			long millis2	 	= getMilliseconds(date2,format2);
			long dateDiff	= millis2 - millis1;
			long minuteDiff = dateDiff/60000;
			return minuteDiff;
        } catch (Exception e) {
			logger.error("Exception in getMinutesFromDates:" , e);
        }
        return -1;
    }
	// P_int_B_41791 Ends
	 @SuppressWarnings("deprecation")
	 public static String getDateInTopFormat(Calendar calendar){
		 BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			String topFormatDate = null;
			if(_baseConstants.isUSDateFormat){
				topFormatDate = LanguageUtil.getString(_baseConstants.dayNamesArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+ LanguageUtil.getString(_baseConstants.months[calendar.get(Calendar.MONTH)]) + " "+calendar.get(Calendar.DAY_OF_MONTH)+ ", "+calendar.get(Calendar.YEAR);	//P_SCH_CAL_DAY
			}else{
				topFormatDate = LanguageUtil.getString(_baseConstants.dayNamesArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+calendar.get(Calendar.DAY_OF_MONTH)+ " " + LanguageUtil.getString(_baseConstants.months[calendar.get(Calendar.MONTH)])+", "+calendar.get(Calendar.YEAR);		//P_SCH_CAL_DAY
			}
			return topFormatDate; 
		}
	 
	 @SuppressWarnings("deprecation")
	public static String getDateInFullFormat(Calendar calendar){
		 BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			String topFormatDate = null;
			if(_baseConstants.isUSDateFormat){
				topFormatDate = LanguageUtil.getString(_baseConstants.dayNamesArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+ LanguageUtil.getString(_baseConstants.months[calendar.get(Calendar.MONTH)]) + " "+calendar.get(Calendar.DAY_OF_MONTH)+ ", "+calendar.get(Calendar.YEAR);	//P_SCH_CAL_DAY
			}else{
				topFormatDate = LanguageUtil.getString(_baseConstants.dayNamesArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+calendar.get(Calendar.DAY_OF_MONTH)+ " " + LanguageUtil.getString(_baseConstants.months[calendar.get(Calendar.MONTH)])+", "+calendar.get(Calendar.YEAR);		//P_SCH_CAL_DAY
			}
			return topFormatDate; 
		}
	 @SuppressWarnings("deprecation")
	 public static String getDateInShortFormat(Calendar calendar){
		 BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			String topFormatDate = null;
			if(_baseConstants.isUSDateFormat){
				topFormatDate = LanguageUtil.getString(_baseConstants.dayArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+ LanguageUtil.getString(_baseConstants.monthSortNames[calendar.get(Calendar.MONTH)]) + " "+calendar.get(Calendar.DAY_OF_MONTH)+ ", "+calendar.get(Calendar.YEAR);	//P_SCH_CAL_DAY
			}else{
				topFormatDate = LanguageUtil.getString(_baseConstants.dayArray[calendar.getTime().getDay()-_baseConstants.CALENDAR_START_DAY_OFFSET])+", "+calendar.get(Calendar.DAY_OF_MONTH)+ " " + LanguageUtil.getString(_baseConstants.monthSortNames[calendar.get(Calendar.MONTH)])+", "+calendar.get(Calendar.YEAR);	//P_SCH_CAL_DAY
			}
			return topFormatDate; 
		}
	 @SuppressWarnings("deprecation")
		public static String getDateInTopFormat(Date date){
			Calendar cal = null;
			if(date != null){
				cal = Calendar.getInstance();
				cal.set(1900+date.getYear(), date.getMonth(), date.getDate());
			}
			return getDateInTopFormat(cal);
		}
	 @SuppressWarnings("deprecation")
		public static String getDateInFullFormat(Date date){
			Calendar cal = null;
			if(date != null){
				cal = Calendar.getInstance();
				cal.set(1900+date.getYear(), date.getMonth(), date.getDate());
			}
			return getDateInFullFormat(cal);
		}
	 @SuppressWarnings("deprecation")
		public static String getDateInShortFormat(Date date){
			Calendar cal = null;
			if(date != null){
				cal = Calendar.getInstance();
				cal.set(1900+date.getYear(), date.getMonth(), date.getDate());
			}
			return getDateInShortFormat(cal);
		}
	 	//ZCUB-20151124-202: To get Start Date and end on the basis of Date Format No
	    public static String[] getStartEndDate(String datestr, String formateNo, String curDateFormat, String requiredDateFormat){
	    	
	    	String [] returnDate=new String[2];
	    	try{
	    		
	    		DateFormat df = null;
	    		Calendar cal=null;
		    	if("40".equals(formateNo)){
		    		df = new SimpleDateFormat(curDateFormat);
		    		Date date=df.parse(datestr);
		    		df= new SimpleDateFormat(requiredDateFormat);
		    		returnDate[0] =df.format(date);
		    		returnDate[1] =df.format(date);
		    	}else if("41".equals(formateNo)){
		    		if(StringUtil.isValid(datestr)){
		    			String [] dateArr=datestr.split(" ");
		    			if(dateArr != null && dateArr.length>1){
		    				int yearWeekNo=Integer.parseInt(dateArr[0])+1;
		    				 cal=Calendar.getInstance();
		    				 cal.set(Calendar.YEAR, Integer.parseInt(dateArr[1]));
		    				 cal.set(Calendar.WEEK_OF_YEAR, yearWeekNo);
		    				 cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		    				 df= new SimpleDateFormat(requiredDateFormat);
		    				 returnDate[0] =df.format(cal.getTime());
		    				 cal.add(Calendar.DAY_OF_WEEK, 6);
		    				 returnDate[1] =df.format(cal.getTime());
		    			}
		    		}
		    	}else if("42".equals(formateNo)){
		    		
		    		 String[] monthArray = {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug","Sep", "Oct", "Nov", "Dec"};
		    		 if(StringUtil.isValid(datestr)){
			    		
		    			 cal=Calendar.getInstance();
		    			 String [] dateArr=datestr.split(" ");
		    			 for(int n=0; n<monthArray.length;n++){
		    				 if(dateArr[0].equals(monthArray[n])){
		    					 cal.set(Calendar.MONTH, n);
		    					 cal.set(Calendar.YEAR, Integer.parseInt(dateArr[1]));
		    					 cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		    					 df= new SimpleDateFormat(requiredDateFormat);
		    					 returnDate[0] =df.format(cal.getTime());
		    					 cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		    					 returnDate[1] =df.format(cal.getTime());
		    					 break;
		    				 }
		    			 }
		    		 }		
		    	}
	    	}catch(Exception e){
	    		logger.error("Exception in getStartEndDate:" , e);
	    	}
	    	return returnDate;
	    	
	    }
	    
	    public static String getMaxDate(String date1, String date2){
	    	
	    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	try{
	    		
	    		Date d1=df.parse(date1);
	    		Date d2=df.parse(date2);
	    		
	    		if(d1.after(d2)){
	    			return date1;
	    		}else{
	    			return date2;
	    		}
	    		
	    	}catch(Exception e){
	    		
	    		logger.error("Exception in getMaxDate:" , e);
	    	}
	    	
	    	return null;
	    }
	    
	    public static String getMinDate(String date1, String date2){
	    	
	    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	try{
	    		
	    		Date d1=df.parse(date1);
	    		Date d2=df.parse(date2);
	    		
	    		if(d1.before(d2)){
	    			return date1;
	    		}else{
	    			return date2;
	    		}
	    		
	    	}catch(Exception e){
	    		
	    		logger.error("Exception in getMaxDate:" , e);
	    	}
	    	
	    	return null;
	    }
	  //ZCUB-20151124-202 end
		/*
		 * added by anilT
		 * this method returns week format as
		 * 21 — 27 Nov 2011 if month are same
		 * 28 Nov — 4 Dec 2011 if month different and year same
		 * 26 Dec 2011 — 1 Jan 2012 if month and year both are different
		 */
	 	@SuppressWarnings("deprecation")
		public static String getDateInWeekFormat(Date startDate,Date endDate){
	 		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
			Calendar startCal = null;
			Calendar endCal = null;
			String weekStartDate = null;
			String weekEndDate = null;
			startCal = Calendar.getInstance();
			endCal = Calendar.getInstance();
			
			if(startDate != null){
				startCal.set(1900+startDate.getYear(), startDate.getMonth(), startDate.getDate());
			}
			if(endCal != null){
				endCal.set(1900+endDate.getYear(), endDate.getMonth(), endDate.getDate());
			}
			
			if(startDate.getMonth()==endDate.getMonth()){
				weekStartDate = String.valueOf(startCal.get(Calendar.DAY_OF_MONTH));
			}
			else if((startDate.getMonth()!=endDate.getMonth()) && (startDate.getYear()==endDate.getYear())){
				weekStartDate = startCal.get(Calendar.DAY_OF_MONTH)+" "+_baseConstants.monthSortNames[startCal.get(Calendar.MONTH)];
			}
			else{
				weekStartDate = startCal.get(Calendar.DAY_OF_MONTH)+ " " + _baseConstants.monthSortNames[startCal.get(Calendar.MONTH)]+", "+startCal.get(Calendar.YEAR);
			}
			
			weekEndDate = endCal.get(Calendar.DAY_OF_MONTH)+ " " + _baseConstants.monthSortNames[endCal.get(Calendar.MONTH)]+", "+endCal.get(Calendar.YEAR);
			
			return weekStartDate + "  &#8212; " + weekEndDate;
		}
		
		//TDG-MOBILE-20101221 starts
		public static String getDateInDatabaseFormat(String date) {


	        if (date != null && !date.equals("") && date.length() == 11) {

	            //System.out.println("date  >>> " + date + "lenght  " + date.length());
	            //System.out.println("day >> " + date.substring(0, 2));
	            //System.out.println("month >> " + date.substring(3, 6));
	            //System.out.println("year >> " + date.substring(7, 11));
	            String month = date.substring(3, 6);
	            String returnDate = "";
	            String returnMonth = "";

	            String[] monthArray = {"Jan", "Feb", "Mar", "Apr",
	                "May", "Jun", "Jul", "Aug",
	                "Sep", "Oct", "Nov", "Dec"};

	            int index = 0;
	            for (; index < monthArray.length; index++) {
	                if (monthArray[index].equals(month)) {
	                    break;                
	                }
	            }
	            index++;

	            if (index < 10) {
	                returnMonth = "0" + index;
	                
	            } else {
	                returnMonth = "" + index;
	            }
	            returnDate = date.substring(7, 11) + "-" + returnMonth + "-" + date.substring(0, 2);

	            return returnDate;
	        } else {
	            return "";
	        }
	    }
		//TDG-MOBILE-20101221 ends
}

