/*
 
  * All Rights Reserved
 * @Created Aug, 2003
 *
 * @author  Er Jaideep Pujara
 * @modified 1 may 2006
 * @Author Vikas
-------------------------------------------------------------------------------------------------
Version No.			Date		By	   	Against					function changed
-------------------------------------------------------------------------------------------------
P_FORMATE_CHANGE	14/04/2008	Suchita	date formate change added day also	
P_B_ADMIN_7458		4th April 2012  Prakriti Sharma   Bug
P_FS_B_8127		22Jun2012		Vivek Maurya		Bug		
P_FORUM_B_12560			10 Sep 2012			Dravit Gupta	iternationalization of date was not working in forums					
------------------------------------------------------------------------------------------------ 
 */


package com.home.builderforms;
import com.home.builderforms.Info;
import com.home.builderforms.DBConnectionManager;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletContext;
import java.util.*;
import java.sql.*;
import java.text.*;

public class TimeZoneUtils implements java.io.Serializable{

//	public static TimeZone = null;
	public static final String DEFAULT_TIMEZONE	 	= "US/Eastern";
	public static final String DB_DATETIME 			= "yyyy-MM-dd HH:mm:ss";
	public static final String DB_DATETIME_12 			= "yyyy-MM-dd hh:mm a";
	public static final String DB_DATE	 			= "yyyy-MM-dd";
	public static final String STD_DATETIME_FORMAT	= "MMM-dd-yyyy hh:mm a z";
	public static final String STD_DATE_FORMAT		= "MMM-dd-yyyy";
	public static final String TZ_DATE_FORMAT		= "MMM-dd-yyyy z";
	// Added by Rakesh for new date- time  format
	public static final String STD_DATETIME_FORMAT2	= "MM/dd/yyyy hh:mm a";
	// Added by Suchita Agrawal for new date- time  format
	public static final String STD_DATETIME_FORMAT3	= "EEEE dd MMMM, yyyy";	
//    ganesh,OTRS: 2007091110000021,19-sep-2007
	public static final String DB_DATE_US_FORMAT	 			= "MM/dd/yyyy";
	//public static String DB_TIMEZONE;

/* Updated by Meena for Ticket#: 2005111810000114 */
	
	/**
	 * Returns time zone display string.
	 * @return String array
	 */
	public static String[] getTimeZoneDisplayStrings() {
		String [] timeZoneDisplayStrings =  new String [] {
			//"GMT -08:00 US/Canada/Pacific",
			//"GMT -07:00 US/Canada/Mountain",
			//"GMT -06:00 US/Canada/Central",
			//"GMT -05:00 US/Canada/Eastern",   //P_B_ADMIN_7458 started by Prakriti Sharma
			"GMT +00:00 Britain, Ireland, Portugal, Western Africa",
			"GMT +00:30 ",
			"GMT +01:00 Western Europe, Central Africa",
			"GMT +01:30 ",
			"GMT +02:00 Eastern Europe, Eastern Africa",
			"GMT +02:30 ",
			"GMT +03:00 Russia, Saudi Arabia",
			"GMT +03:30 ",
			"GMT +04:00 Arabian",
			"GMT +04:30 ",
			"GMT +05:00 West Asia, Pakistan",
			"GMT +05:30 India",
			"GMT +06:00 Central Asia",
			"GMT +06:30 ",
			"GMT +07:00 Bangkok, Hanoi, Jakarta",
			"GMT +07:30 ",
			"GMT +08:00 China, Singapore, Taiwan",
			"GMT +08:30 ",
			"GMT +09:00 Korea, Japan",
			"GMT +09:30 Central Australia",
			"GMT +10:00 Eastern Australia",
			"GMT +10:30 ",
			"GMT +11:00 Central Pacific",
			"GMT +11:30 ",
			"GMT +12:00 Fiji, New Zealand",
			"GMT -12:00 DATELINE ",
                        "GMT -11:30 ",
                        "GMT -11:00 Samoa",
			"GMT -10:30 ",
			"GMT -10:00 Hawaiian",
			"GMT -09:30 ",
			"GMT -09:00 Alaska/Pitcairn Islands",   //P_INT_B_52986 by Ankit Saini on 30/11/2009 start
                        "GMT -08:30 ",
                        "GMT -08:00 US/Canada/Pacific",               //8:00
                        "GMT -07:30 ",
                        "GMT -07:00 US/Canada/Mountain/Arizona", //P_E_CAL_TIMEZONE             //7:00
                        "GMT -06:30 ",
                        "GMT -06:00 US/Canada/Central",              //6:00
                        "GMT -05:30 ",
                        "GMT -05:00 US/Canada/Eastern",              //5:00
			"GMT -04:30 ",                                      //P_INT_B_52986 by Ankit Saini on 30/11/2009 end
			"GMT -04:00 Bolivia, Western Brazil, Chile, Atlantic",
			"GMT -03:30 Newfoundland",
			"GMT -03:00 Argentina, Eastern Brazil, Greenland",
			"GMT -02:30 ",
			"GMT -02:00 Mid-Atlantic",
			"GMT -01:30 ",
			"GMT -01:00 Azores/Eastern Atlantic",
			"GMT -00:30 "
		};       //P_B_ADMIN_7458 ended by Prakriti Sharma
		return timeZoneDisplayStrings;
	}
/****Above and Below retuns values are mapped in building timezone combo / displaying timezone ********/
/* Updated by Meena for Ticket#: 2005111810000114 */
	
	/**
	 * Retuns values are mapped in building timezone combo / displaying timezone
	 * @return String array
	 */
	public static String[] getTimeZoneIds() {
		//US/EASTERN //this is chnged to US/Eastern because jvm is not taking this key
		//changes done by vikas on Wed 8,Feb 2006
			String [] timeZoneIds =  new String [] {
			//"US/Pacific",
			//"US/Mountain",
			//"US/Central",
			//"US/Eastern",
			"GMT",
			"GMT+00:30",
			"GMT+01:00",
			"GMT+01:30",
			"GMT+02:00",
			"GMT+02:30",
			"GMT+03:00",
			"GMT+03:30",
			"GMT+04:00",
			"GMT+04:30",
			"GMT+05:00",
			"Asia/Kolkata",
			"GMT+06:00",
			"GMT+06:30",
			"GMT+07:00",
			"GMT+07:30",
			"GMT+08:00",
			"GMT+08:30",
			"GMT+09:00",
			"GMT+09:30",
			"GMT+10:00",
			"GMT+10:30",
			"GMT+11:00",
			"GMT+11:30",
			"GMT+12:00",
			"GMT-12:00",
			"GMT-11:30",
			"GMT-11:00",
			"GMT-10:30",
			"GMT-10:00",
			"GMT-09:30",
			"GMT-09:00",
			"GMT-08:30",
			"US/Pacific",//P_INT_B_52986 by Ankit Saini on 30/11/2009 start
			"GMT-07:30",
			"US/Mountain",
			"GMT-06:30",
			"US/Central",
			"GMT-05:30",
			"US/Eastern",//P_INT_B_52986 by Ankit Saini on 30/11/2009 end
			"GMT-04:30",
			"GMT-04:00",
			"GMT-03:30",
			"GMT-03:00",
			"GMT-02:30",
			"GMT-02:00",
			"GMT-01:30",
			"GMT-01:00",
			"GMT-00:30"
		};
		return timeZoneIds;
	}



	
	
	/**
	 * This function builds the html select for timezones.
	 * Used in adding/modifying any user type.
	 * @param features as an string parameter
	 * @param selectedTimeZone as an string parameter
	 * @return string as an value
	 */

	public static String getTimeZoneCombo(String features, String selectedTimeZone){
		StringBuffer sbTimeZoneCombo = null;
		try{
			String [] timeZoneDisplayStrings 	= getTimeZoneDisplayStrings();
			String [] timeZoneIds 				= getTimeZoneIds();
			sbTimeZoneCombo  = new StringBuffer("<Select ");
			sbTimeZoneCombo.append(features);
			sbTimeZoneCombo.append(" >");
//			Debug.println("selectedTimeZone-->"+selectedTimeZone);
			String sGMTValue 		= "";
			String sDisplayValue 	= "";
			int n					= timeZoneDisplayStrings.length;
			// Added on June 3,2005
			//sbTimeZoneCombo.append("<option value='-1' selected>Select</option>");
			sbTimeZoneCombo.append("<option value='-1'>"+LanguageUtil.getString("Select")+"</option>");
			for(int i = 0 ; i < n; i++){
				sDisplayValue 	= timeZoneDisplayStrings[i];
				sGMTValue 		= timeZoneIds[i];
				sbTimeZoneCombo.append("<option value='");
				sbTimeZoneCombo.append(sGMTValue);
				sbTimeZoneCombo.append("'");
				if(selectedTimeZone!=null && selectedTimeZone.trim().equals(sGMTValue.trim()))//P_FS_B_8127
				{
					sbTimeZoneCombo.append(" selected");
				}
				sbTimeZoneCombo.append(" >");
				sbTimeZoneCombo.append(sDisplayValue);
				sbTimeZoneCombo.append("</option>");
			}
			sbTimeZoneCombo.append("</select>");
		}catch(Exception exp){
			Debug.print(exp);
			Debug.println("Problem making timezone combo.");
			return BaseUtils.getEmptyCombo(features);   //For Product_Seperation_BL By Amar Singh.
			//return PortalUtils.getEmptyCombo(features);  
		}
		return sbTimeZoneCombo.toString();
	}

/*
	public static String getGMTString(String sDisplayValue){
		StringBuffer retVal = new StringBuffer(sDisplayValue.substring(0, 10));
		retVal.deleteCharAt(3);
//		Debug.println("Got : "+sDisplayValue);
//		Debug.println("Returning : '"+retVal+"'");
		return retVal.toString();
	}
*/

	
	
	/**
	 * Method is called from jsp for displaying the Timezone text of a user.
	 * Takes the timezon id and retuns text against it.
	 * Makes use of string arrays predefined in the class.
	 * @param sDBStyleValue String parameter
	 * @return String value
	 */
	public static String searchDisplayGMTString(String sDBStyleValue){
		String [] timeZoneDisplayStrings 	= getTimeZoneDisplayStrings();
		String [] timeZoneIds 				= getTimeZoneIds();
		//String sIndexValue = "";
		//boolean bGotDisplay = false;
		int n = timeZoneIds.length;
		for(int i = 0 ; i < n ;i++){
			//sIndexValue	= timeZoneIds[i];
			if(timeZoneIds[i].equals(sDBStyleValue)){
				//bGotDisplay = true;
				//Debug.println("Got : "+sDBStyleValue);
				//Debug.println("Returning : '"+sIndexValue+"'");
				return timeZoneDisplayStrings[i]; // Bothe the string arrays are matched.
			}
		}
		return "";
	}



	
	
	/**
	 * This method performs the time conversion according to the timezones.
	 *  Basically takes required/user timeZone id and date in given format.
	 *  Performs conversions with help of std java classes ie Calendar and SimpleDateFormat.
	 *  NOTE: Methd fails to perform adequate time conversion when given format is not std
	 mysql 'date' or 'datetime'. Failure is becoz of Calender class's behaviour
	 *  @param givenTZ as an string parameter
	 *  @param sUserTZ as an string parameter
	 *  @param date as an string parameter
	 *  @param givenFormat as an string parameter
	 *  @param desiredFormat as an string parameter
	 *  @return converted date according 2 the desired format.
	 */

	public static String performUTCConversion(
												String givenTZ,// Defaut is DB_TIMEZONE passed by overloaded methods
												String sUserTZ,
												String date,
												String givenFormat,
												String desiredFormat
											) {
		return performUTCConversion(givenTZ, sUserTZ, date, givenFormat, desiredFormat, null);
	}

//P_FORUM_B_12560 starts
	/**
	 * This is an overloaded method of teh above method called in case of Forums to get user Language
	 * This method performs the time conversion according to the timezones.
	 *  Basically takes required/user timeZone id and date in given format.
	 *  Performs conversions with help of std java classes ie Calendar and SimpleDateFormat.
	 *  NOTE: Methd fails to perform adequate time conversion when given format is not std
	 mysql 'date' or 'datetime'. Failure is becoz of Calender class's behaviour
	 *  @param givenTZ as an string parameter
	 *  @param sUserTZ as an string parameter
	 *  @param date as an string parameter
	 *  @param givenFormat as an string parameter
	 *  @param desiredFormat as an string parameter
	 *  @param	userNo as an String parameter
	 *  @return converted date according 2 the desired format.
	 */

	public static String performUTCConversion(
												String givenTZ,
												String sUserTZ,
												String date,
												String givenFormat,
												String desiredFormat,
												String userNo
											) {
		String convertedDate = "";
		try{
			SimpleDateFormat formatter;
			//EN_DAYLIGHT_SAVING_TIME
			String isDaylight="";
			if(userNo==null){
				HttpSession session = StrutsUtil.getHttpSession();
				userNo=(String)session.getAttribute("user_no");
			}
			if(userNo!=null){
				/*Map<String,Object> UserMap = CacheMgr.getUserCache().getUser(userNo);
				isDaylight=(String)UserMap.get("isDaylight");*/
			}
			
			//EN_DAYLIGHT_SAVING_TIME
			
			if(desiredFormat==null)
				desiredFormat = STD_DATETIME_FORMAT3;
			if(date==null)
				return "--Not Available--";
			
			if(userNo==null){
			formatter = new SimpleDateFormat(desiredFormat,LanguageUtil.getUserLocale());//Modified by Akhil Sharma for Changing date at top according to locale
			} else{
			formatter = new SimpleDateFormat(desiredFormat,LanguageUtil.getUserLocale(userNo));//Modified by Akhil Sharma for Changing date at top according to locale
			}
			
			GregorianCalendar dbCal = new GregorianCalendar();
			dbCal.setTimeZone(TimeZone.getTimeZone(givenTZ));
			
			
			if( givenFormat.equals(DB_DATETIME) && date != null && date.length() >= 19)
			{
				int [] brokenTimestamp = breakTimeStamp(date);
				dbCal.set(
							brokenTimestamp[0],
							brokenTimestamp[1],
							brokenTimestamp[2],
							brokenTimestamp[3],
							brokenTimestamp[4],
							brokenTimestamp[5]
						);
			}else if(givenFormat.equals(DB_DATE) && date != null && date.length() >= 10)
			{
				int [] brokenTimestamp = breakDate(date);
				dbCal.set(
							brokenTimestamp[0],
							brokenTimestamp[1],
							brokenTimestamp[2]
						);
			}else
			{
				dbCal.setTime(DateTime.getDateFromString(date, givenFormat));
			}
			java.util.Date calDate = dbCal.getTime();
			dbCal.setTimeZone(TimeZone.getTimeZone(sUserTZ));
			
			
			
			//EN_DAYLIGHT_SAVING_TIME
			if("N".equals(isDaylight))
	        dbCal.add(GregorianCalendar.MILLISECOND, -TimeZone.getTimeZone(sUserTZ).getDSTSavings());
			//EN_DAYLIGHT_SAVING_TIME
			
			formatter.setTimeZone(TimeZone.getTimeZone(sUserTZ));
			convertedDate = formatter.format(calDate);
		}catch(Exception exp){
			exp.printStackTrace();
			Debug.print("Exception while converting times between timezones :" + exp);

		}
		return convertedDate;
	}
//P_FORUM_B_12560 ends
	/*
		Overloaded versions performUTCConversion() above
	*/
	
	/**
	 * This method performs the time conversion according to the timezones.
	 *  Basically takes required/user timeZone id and date in given format.
	 *  Performs conversions with help of std java classes ie Calendar and SimpleDateFormat.
	 *  NOTE: Methd fails to perform adequate time conversion when given format is not std
	 mysql 'date' or 'datetime'. Failure is becoz of Calender class's behaviour
	 *  @param sUserTZ as an string parameter
	 *  @param date as an string parameter
	 *  @param givenFormat as an string parameter
	 *  @param desiredFormat as an string parameter
	 *  @return converted date according 2 the desired format.
	 */

	public static String performUTCConversion(
												String sUserTZ,
												String date,
												String givenFormat,
												String desiredFormat
											) {
		return performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS ,sUserTZ, date, givenFormat, desiredFormat);
	}

	public static String performUTCConversion(
												String sUserTZ,
												String date,
												String givenFormat  ) {
 		return performUTCConversion(sUserTZ, date, givenFormat, STD_DATE_FORMAT) ;
	}

	public static String performUTCConversion(String sUserTZ, String date) {
 		return performUTCConversion(sUserTZ, date, DB_DATE, STD_DATE_FORMAT) ;
	}

	public static String performUTCConversion(
												String sUserTZ,
												Timestamp timestamp,
												String formatString)  {
		String rawDate = DateTime.getDateTime(timestamp, DB_DATETIME);
		return performUTCConversion(sUserTZ, rawDate, DB_DATETIME, formatString);
	}

	public static String performUTCConversion(String sUserTZ, Timestamp timestamp)  {
		return performUTCConversion(sUserTZ, timestamp, STD_DATETIME_FORMAT) ;
	}

	
	
	/**
	 * Assisting method of performUTCConversion() for breaking mysql datetime into
	 * different date time components
	 * @param sTimeStamp as an string parameter
	 * @return integer array
	 */
	public static int [] breakTimeStamp(String sTimeStamp){
		//int nYear=0, nMonth=0, nDay=0, nHour=0, nMin=0, nSec=0;
		int []returnArray			=	new int[]{0,0,0,0,0,0};
		try{
			/*returnArray[0]nYear 	= Integer.parseInt(sTimeStamp.substring(0, 4));
			returnArray[1]nMonth 	= (Integer.parseInt(sTimeStamp.substring(5, 7)) -1);
			returnArray[2]nDay 	= Integer.parseInt(sTimeStamp.substring(8, 10));
			returnArray[3]nHour 	= Integer.parseInt(sTimeStamp.substring(11, 13));
			returnArray[4]nMin 	= Integer.parseInt(sTimeStamp.substring(14, 16));
			returnArray[5]nSec 	= Integer.parseInt(sTimeStamp.substring(17));
			*/

			returnArray[0]		= Integer.parseInt(sTimeStamp.substring(0, 4));
			returnArray[1] 		= (Integer.parseInt(sTimeStamp.substring(5, 7)) -1);
			returnArray[2] 		= Integer.parseInt(sTimeStamp.substring(8, 10));
			returnArray[3] 		= Integer.parseInt(sTimeStamp.substring(11, 13));
			returnArray[4] 		= Integer.parseInt(sTimeStamp.substring(14, 16));
			//changed by vikas bekause it is creating error when time stamp contain millisecond also
			returnArray[5] 		= Integer.parseInt(sTimeStamp.substring(17,19));
		}catch(Exception e){
			Debug.print(e);
		}
		//return new int[]{nYear, (nMonth-1), nDay, nHour, nMin, nSec};
		return returnArray;
	}

	
	/**
	 * Assisting method of performUTCConversion() for breaking mysql date into
	 * different date components
	 * @param sDate as an string parameter
	 * @return integer array
	 */
	public static int [] breakDate(String sDate){
		//int nYear=0, nMonth=0, nDay=0;
		int[] returnArray			=	new int[]{0,0,0};
		try{
			/*
			 	Year 	= Integer.parseInt(sDate.substring(0, 4));
				nMonth 	= (Integer.parseInt(sDate.substring(5, 7))-1);
				nDay 	= Integer.parseInt(sDate.substring(8));
			 */
			returnArray[0] 	= Integer.parseInt(sDate.substring(0, 4));
			returnArray[1] 	= (Integer.parseInt(sDate.substring(5, 7))-1);
		//changed by vikas bekause it is creating error when date contains time also
			returnArray[2] 	= Integer.parseInt(sDate.substring(8,10));
		}catch(Exception e){
			Debug.print(e);
		}
		//return new int[]{nYear, (nMonth-1), nDay};
		return returnArray;
	}


	
	/**
	 * This method in invoked from mainservlet at app startup
	* It retrieves the database timezone and set it into app for
	* time zone functionality.
	* @param context as an SrvletContext
	* @return 
	 */
	/*public static void setDataBaseTimeZone(ITenantContext context) throws ConnectionException{
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		//DB_SERVER_TIME_ZONE_ISSUE starts
	  if(StringUtil.isValid((String)context.getAttribute("dbTimeZone")))
		{
		  _baseConstants.DB_TIMEZONE_TIMEZONEUTILS=(String)context.getAttribute("dbTimeZone");
		}
	  else   //DB_SERVER_TIME_ZONE_ISSUE ends
		{
		Connection con = null;
		HashMap dBnJavaTZIdsMap = new HashMap();
		dBnJavaTZIdsMap.put("EST","US/Eastern");
		dBnJavaTZIdsMap.put("EDT","US/Eastern");
		dBnJavaTZIdsMap.put("CST","US/Central");
		dBnJavaTZIdsMap.put("CDT ","US/Central");
		dBnJavaTZIdsMap.put("MST","US/Mountain");
		dBnJavaTZIdsMap.put("MDT","US/Mountain");
		dBnJavaTZIdsMap.put("PST","US/Pacific");
		dBnJavaTZIdsMap.put("PDT","US/Pacific");
		//added by vikas for db server time zone in india
		dBnJavaTZIdsMap.put("IST","IST");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
			ps = con.prepareStatement("SHOW VARIABLES");
			rs = ps.executeQuery();
			String varName 	= "";
			String value	= "";
			while(rs.next()){
				varName = rs.getString("Variable_name");
				if(varName.equals("timezone")){
					value 	= rs.getString("Value");
					if(dBnJavaTZIdsMap.containsKey(value)){
						DB_TIMEZONE = (String)dBnJavaTZIdsMap.get(value);
						context.setAttribute("dbTimeZone", DB_TIMEZONE);
					}else{
						DB_TIMEZONE = value;
						context.setAttribute("dbTimeZone", DB_TIMEZONE);
					}
					break;
				}
			}

			String sysTZ    = null;
			String tz = null;
			String tzOld = null;
			while(rs.next()){
					varName = rs.getString("Variable_name");
					if(varName.equals("system_time_zone")){
							sysTZ = rs.getString("Value");
					}
					if(varName.equals("timezone")){
							tzOld = rs.getString("Value");
					}
					if(varName.equals("time_zone")){
							tz = rs.getString("Value");
					}
			}
			if (tzOld != null){
				if(dBnJavaTZIdsMap.containsKey(tzOld)){
					_baseConstants.DB_TIMEZONE_TIMEZONEUTILS = (String)dBnJavaTZIdsMap.get(tzOld);
					context.setAttribute("dbTimeZone", _baseConstants.DB_TIMEZONE_TIMEZONEUTILS);
				}else{
					_baseConstants.DB_TIMEZONE_TIMEZONEUTILS = (String)dBnJavaTZIdsMap.get("EDT");
					context.setAttribute("dbTimeZone", _baseConstants.DB_TIMEZONE_TIMEZONEUTILS);
				}
			}else{
				if(dBnJavaTZIdsMap.containsKey(tz)){
					_baseConstants.DB_TIMEZONE_TIMEZONEUTILS = (String)dBnJavaTZIdsMap.get(tz);
					context.setAttribute("dbTimeZone", _baseConstants.DB_TIMEZONE_TIMEZONEUTILS);
				}else if(dBnJavaTZIdsMap.containsKey(sysTZ)){
					_baseConstants.DB_TIMEZONE_TIMEZONEUTILS = (String)dBnJavaTZIdsMap.get(sysTZ);
					context.setAttribute("dbTimeZone", _baseConstants.DB_TIMEZONE_TIMEZONEUTILS);
				}else{
					_baseConstants.DB_TIMEZONE_TIMEZONEUTILS = (String)dBnJavaTZIdsMap.get("EDT");
					context.setAttribute("dbTimeZone", _baseConstants.DB_TIMEZONE_TIMEZONEUTILS);
				}
			}
		}catch(Exception e){
			Debug.print("Problem setting db time zome in application :" +e);

		}
		finally{
			try{
			if(ps!=null)
				ps.close();
			ps=null;
			if(rs!=null)
				rs.close();
			rs=null;

			}catch(Exception e){
								Debug.print(e);
			}
			DBConnectionManager.getInstance().freeConnection(con);
		}
	  }
	}
*/
	/**
	 * calToSring() is Debug friendly method used to print out the calendar object in
	 *	better readable/ understood format.
	 *@param calObj as an Calender object
	 *@return string value 
	 */
	public static String calToString(Calendar calObj){
		String [] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String date = months[calObj.get(Calendar.MONTH)] + "-" + calObj.get(Calendar.DATE) + "-" + calObj.get(Calendar.YEAR);
		String time = calObj.get(Calendar.HOUR_OF_DAY)+ ":"+calObj.get(Calendar.MINUTE)+ ":"+calObj.get(Calendar.SECOND);
		return  date + " " + time +" "+ calObj.getTimeZone().getDisplayName();
	}

	/**
		Calculates the current time in given time zone
		@param psRequiredTimeZone as an String parameter
		@param psRequiredFormat as an String parameter
		@return string value
	*/
	public static String getCurrentTime(String psRequiredTimeZone, String psRequiredFormat){
		String sDeafaultTZ 	= TimeZone.getDefault().getID();
		String sNowDateTime	= DateTime.getSqlTimestampString();
		return performUTCConversion(sDeafaultTZ, psRequiredTimeZone, sNowDateTime, TimeZoneUtils.DB_DATETIME, psRequiredFormat);
	}


	/*
	 * method to get time difference between two timezone in seconds
	*/
	/**
	 * method to get time difference between two timezone in seconds
	 * @param psDBTimeZone as an string parameter
	 * @param psUserTimeZone as an string parameter
	 * @return long value
	 */
	public static long measureTimeDifference(String psDBTimeZone, String psUserTimeZone){
		long lTimeDiff = 0;
		try{
			GregorianCalendar dbCal 	= new GregorianCalendar();
			GregorianCalendar userCal 	= new GregorianCalendar();
			String currentDatetime		= DateTime.getSqlTimestampString();
			int [] brokenTimestamp = breakTimeStamp(currentDatetime);
			dbCal.setTimeZone(TimeZone.getTimeZone(psDBTimeZone));
			dbCal.set(
							brokenTimestamp[0],
							brokenTimestamp[1],
							brokenTimestamp[2],
							brokenTimestamp[3],
							brokenTimestamp[4],
							brokenTimestamp[5]
						);

			userCal.setTimeZone(TimeZone.getTimeZone(psUserTimeZone));
			userCal.set(
							brokenTimestamp[0],
							brokenTimestamp[1],
							brokenTimestamp[2],
							brokenTimestamp[3],
							brokenTimestamp[4],
							brokenTimestamp[5]
						);

			lTimeDiff = (dbCal.getTimeInMillis()-userCal.getTimeInMillis());
			Debug.println("Difference in ms-->"+(lTimeDiff));
			lTimeDiff = lTimeDiff/1000;
		}catch(Exception e){
			Debug.print(e);
		}
		return lTimeDiff;
	}
	/*
	 * 
	*/
	/**
	 * method to get time difference between two timezone in milliseconds
	 * @param pTimeZone1 as an string parameter
	 * @param pTimeZone2 as an string parameter
	 * @return long value
	 */
	public static long measureTimeDifference(TimeZone pTimeZone1, TimeZone pTimeZone2){
		long lTimeDiff = DateTime.getCurrentMillis();
		try{
			int nMillisToAddinUTC1 = pTimeZone1.getOffset(lTimeDiff);
			Debug.println("IST offset: "+nMillisToAddinUTC1);
			int nMillisToAddinUTC2 = pTimeZone2.getOffset(lTimeDiff);
			Debug.println("EST offset: "+nMillisToAddinUTC2);

			lTimeDiff = nMillisToAddinUTC1 - nMillisToAddinUTC2;

//			Debug.println("Difference in mins-->"+((lTimeDiff/1000)/60));
//			lTimeDiff = lTimeDiff/1000;
		}catch(Exception e){
			Debug.print(e);
		}
		return lTimeDiff;
	}


//Added by santanu
	/**
	 * returns timezone information
	 * @return Info object
	 */
public  static Info getTimeZoneInfo(){
		Info info = null;
		try{
			String [] timeZoneDisplayStrings = getTimeZoneDisplayStrings();
			String [] timeZoneIds = getTimeZoneIds();

			String sGMTValue = "";
			String sDisplayValue = "";
			info = new Info();
			int n = timeZoneDisplayStrings.length;

			for(int i = 0 ; i < n; i++){

				sDisplayValue 	= timeZoneDisplayStrings[i];
				sGMTValue 		= timeZoneIds[i];
				info.set(sGMTValue,sDisplayValue);



			}

		}catch(Exception exp){
			Debug.print(exp);
		}
		
		return info;
	}


}






