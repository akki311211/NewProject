/**
 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Version No.		    Date	 By	          Against                   Function Changed            Comments
 -----------------------------------------------------------------------------------------------------------------------------------------------------------
 ENH_Month_Combo         2007-jul-13-16  Bhoopal G        Month Combo Box           getMonthNameShort(), getYear()      New methods
 ENH 					  29-05-2008      Saurabh Sinha              							Null exception removed for format date & DISPLAY_FORMAT_HMS_AMPM var added
 P_	date_format 			9 june,2008     Sanjeev k         Add Method 
 ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 P_CC_ENHANCEMENT         Aug 22,2008 	  Manoj kumar	   			For call center Application
 P_FRANONLINE_E_70012     16-Jun-2009      Saurabh Sinha           Age cacluation function is added
 P_FIM_B_54638            29 Dec 2009      Vartika Joshi          //added hrs for time in fim task.
 *                                                                changed for the issue in TaskManipulator.java 
 P_FS_Enh_12Apr10        12 Apr 2010       Vikram Raj                added getCurrentTimeDB method to get the current time of the db

 P_E_FIN_UPGRADE			31 Dec	2010	Vivek Maurya		Enh			--added check for date
 BBEH_FOR_MERGING_DATEUTIL_FILES  22/02/2013      Rohit Jain    merger of All DateUtil classes into /util/DateUtil.java And apropriate changes has been done in related files .
 BBEH_INTRANET_SMC_OPTIMIZATION  25/07/2013      Rohit Jain    Code and Query optimization for Smc in Display Date.
 ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

/*
 * DateUtil.java
 * @author  Anuj Paul
 * @Date    June 17, 2006, 1:31 PM
 * @Modified By : ManishS
 * 
 * Updated By Abhishek Gupta 
 * For Setting and getting date in appropriate format
 * 
 *
 */

package com.home.builderforms;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DateUtil
{
    static Logger logger = Logger.getLogger(DateUtil.class);
    public static final String DB_FORMAT = "yyyy-MM-dd";
    public static final String DB_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static String DB_FORMAT_HMS = DB_FORMAT + " HH:mm:ss";
    public static final String DISPLAY_FORMAT_MMMDDYYYY = "MMM-dd-yyyy";
    public static final String DISPLAY_FORMAT_DDMMMYYYY = "dd-MMM-yyyy";
    public static final String TIME_HMS = "HH:mm:ss";
    public static final String SHORT_DISPLAY_FORMAT = "MM/dd/yy";
    public static final String[] monthSortNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final String[] DATEFORMATS = new String[]{"dd-MM-yyyy", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy-MM-dd"};
    public static final  String DISPLAY_FORMAT_MDY = "MMMM dd, yyyy";//Franbuzz Time Format change
    //Added to make date format configurable, it is called from FCInitHandlerServlet
    public static void loadFormats(String dateformat)
    {
        //com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants Constants = Constants;
        int i = getDateFormatIndex(dateformat);
        Constants.DISPLAY_FORMAT = DATEFORMATS[i];
        Constants.DISPLAY_FORMAT_HM = Constants.DISPLAY_FORMAT + " HH:mm";
        Constants.DISPLAY_FORMAT_HMS = Constants.DISPLAY_FORMAT + " HH:mm:ss";

        // Additional format added
        Constants.DISPLAY_FORMAT_HMS_AMPM = Constants.DISPLAY_FORMAT + " HH:mm:ss a";
        Constants.DISPLAY_FORMAT_HMA = Constants.DISPLAY_FORMAT + " hh:mm a";
        Constants.CALENDAR_INDEX = "calendar" + i;

        if (Constants.DISPLAY_FORMAT.equalsIgnoreCase("DD/MM/YYYY"))
        {
            Constants.LBL_DISPLAY_FORMAT = "DD/MM/YYYY";
            Constants.isUSDateFormat = false;
        } else if (Constants.DISPLAY_FORMAT.equalsIgnoreCase("mm/dd/yyyy"))
        {
            Constants.LBL_DISPLAY_FORMAT = "MM/DD/YYYY";
        }
        Constants.DISPLAY_FORMAT_DASH = Constants.DISPLAY_FORMAT.replaceAll("/", "-");
    }

    // Get the index of given format in avaibale formats, based on that it will decide which javascript to use.
    private static int getDateFormatIndex(String dateformat)
    {
        for (int i = 0; i < DATEFORMATS.length; i++)
        {
            if (DATEFORMATS[i].equals(dateformat))
            {
                return i;
            }
        }
        return 2;
    }

    // Returns the date in DB format
    public static String getDbDate(String date)
    {
        return formatDate(date, DB_FORMAT);
    }

    public static boolean checkDateSPSales(String date)
    {
        Date dt = getDate(date);
        if (dt == null)
            return false;
        else
        {
            String format = (date.indexOf("-") != -1) ? DB_FORMAT : Constants.DISPLAY_FORMAT;
            return (formatDate(dt, format).equals(date));
        }
    }

    // Returns the date in Display format
    public static String getDisplayDate(String date)
    {
        return formatDate(date, Constants.DISPLAY_FORMAT);
    }

    public static String getDisplayDate(String date, String format)
    {
        return formatDate(date, format);
    }

    // Returns the date in Display formatwith having date as parameter.
    public static String getDisplayDate(Date date)
    {
        return formatDate(date, Constants.DISPLAY_FORMAT);
    }


    public static Calendar getCalendar()
    {
        return getCalendar(null);
    }

    public static Calendar getCalendar(String date)
    {
        Calendar cal = Calendar.getInstance();

        if (!com.home.builderforms.StringUtil.badString(date))
        {
            /**
             *  Checks the passed String for non-numeric character at either start or end, and for any character other than 0-9,/,- at any position
             **/
            if (date.length() > 19)
                date = date.substring(0, 19);
            if (date.substring(0, 1).matches("[0-9]") && date.substring(date.length() - 1).matches("[0-9]") && date.split("[^0-9\\s:/-]").length == 1)
            {
                String timePart = "";
                if (date.indexOf(" ") != -1)
                {
                    timePart = date.split(" ")[1];
                    date = date.split(" ")[0];
                }
                String timeSplit[] = null;
                if (!timePart.equals(""))
                    timeSplit = timePart.split(":");

                String dateSplit[] = date.split("-");
                if (dateSplit.length == 3)
                {
                    if (Integer.parseInt(dateSplit[0]) > 29 && Integer.parseInt(dateSplit[0]) < 100)
                        dateSplit[0] = Integer.toString(Integer.parseInt(dateSplit[0]) + 1900);
                    else if (Integer.parseInt(dateSplit[0]) <= 29)
                        dateSplit[0] = Integer.toString(Integer.parseInt(dateSplit[0]) + 2000);
                    if (timePart.equals(""))
                        cal.set(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[2]));
                    else
                        cal.set(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[2]), Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]), Integer.parseInt(timeSplit[2]));
                } else
                {
                    dateSplit = date.split("/");
                    if (dateSplit.length == 3)
                    {
                        if (Integer.parseInt(dateSplit[2]) > 29 && Integer.parseInt(dateSplit[2]) < 100)
                            dateSplit[2] = Integer.toString(Integer.parseInt(dateSplit[2]) + 1900);
                        else if (Integer.parseInt(dateSplit[2]) <= 29)
                            dateSplit[2] = Integer.toString(Integer.parseInt(dateSplit[2]) + 2000);

                        int dateIndex = 1;
                        int monthIndex = 0;
                        if (!"calendar2".equals(Constants.CALENDAR_INDEX))
                        {
                            dateIndex = 0;
                            monthIndex = 1;

                        }

                        if (timePart.equals(""))
                            cal.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[monthIndex]) - 1, Integer.parseInt(dateSplit[dateIndex]));
                        else
                            cal.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[monthIndex]) - 1, Integer.parseInt(dateSplit[dateIndex]), Integer.parseInt(timeSplit[monthIndex]), Integer.parseInt(timeSplit[dateIndex]), Integer.parseInt(timeSplit[2]));
                    } else
                    {


                        if (date.length() == 8)
                            cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6)));
                        else
                            return null;
                    }
                }
            } else
                return null;
        }
        return cal;
    }

    public static Date getCurrentDate()
    {
        return getCalendar(null).getTime();
    }

    public static String getCurrentDateAsString()
    {
        return formatDate(getCalendar(null).getTime());
    }

    public static String getCurrentDateAsString(String format)
    {
        return formatDate(getCalendar(null).getTime(), format);
    }

    public static Date getDate()
    {
        return getCalendar(null).getTime();
    }

    public static Date getDate(String date)
    {
        // return (getCalendar(date) != null ? getCalendar(date).getTime() : null);
        //BBEH_INTRANET_SMC_OPTIMIZATION  Starts By Rohit Jain
        Calendar cal = getCalendar(date);
        return (cal != null ? cal.getTime() : null);
        //  return (getCalendar(date) != null ? getCalendar(date).getTime() : null);
        //BBEH_INTRANET_SMC_OPTIMIZATION ends By Rohit Jain
    }


    /**
     * <p>This method will return wether the date value is valid as per the current dispaly format</p>
     *
     * @param date Date String which has to be formatted.
     * @return boolean value wether date is valid or not
     * @author Akhil Gupta
     * P_FS_Import_DateFormat_Validation
     */
    public static boolean checkDateValidationForDisplayFormat(String date){//BASEBUILD80_REST_API----START
        return checkDateValidationForDisplayFormat(date, false, false, 0);
    }//BASEBUILD80_REST_API----END

    public static boolean checkDateValidationForDisplayFormat(String date, boolean lengthCheck, boolean yearCheck, int minYearLimit){//BASEBUILD80_REST_API
        boolean isDateValid = true;
        boolean isDateNumeric = true;
        String seperator = (date.indexOf("-") != -1) ? "-" : "/";
        String dateSplit[] = date.split(seperator);
        int i, noOfDaysInMonth = 0;
        try
        {
            if (dateSplit.length < 3)
            {
                isDateValid = false;
            } else
            {

                for (i = 0; i < dateSplit.length; i++)
                {
                    isDateNumeric = NumberFormatUtils.isNumeric(dateSplit[i]);
                    if (isDateNumeric == false)
                    {
                        isDateValid = false;
                    }
                }

                if (isDateValid)
                {
                    if ("mm/dd/yyyy".equalsIgnoreCase(Constants.DISPLAY_FORMAT))
                    {
                        if (!(Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 12))
                        {
                            isDateValid = false;
                        } else
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DISPLAY_FORMAT);
                            noOfDaysInMonth = getMonthDays(sdf.parse(date));
                            if (!(Integer.parseInt(dateSplit[1]) >= 1 && Integer.parseInt(dateSplit[1]) <= noOfDaysInMonth))
                            {
                                isDateValid = false;
                            }
                        }
                        if(lengthCheck){//BASEBUILD80_REST_API----START
                        	if(dateSplit[0].trim().length() != 2 || dateSplit[2].trim().length() != 4 || dateSplit[1].trim().length() != 2){
                        		isDateValid=false;
                        	}else if(yearCheck){
                        		if(Integer.parseInt(dateSplit[2])<minYearLimit){
                        			isDateValid=false;
                        		}
                        	}
                        }//BASEBUILD80_REST_API----END

                    } else if ("dd/mm/yyyy".equalsIgnoreCase(Constants.DISPLAY_FORMAT))
                    {

                        if (!(Integer.parseInt(dateSplit[1]) >= 1 && Integer.parseInt(dateSplit[1]) <= 12))
                        {
                            isDateValid = false;
                        } else
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DISPLAY_FORMAT);
                            noOfDaysInMonth = getMonthDays(sdf.parse(date));
                            if (!(Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= noOfDaysInMonth))
                            {
                                isDateValid = false;
                            }
                        }
                        if(lengthCheck){//BASEBUILD80_REST_API----START
                        	if(dateSplit[0].trim().length() != 2 || dateSplit[2].trim().length() != 4 || dateSplit[1].trim().length() != 2){
                        		isDateValid=false;
                        	}else if(yearCheck){
                        		if(Integer.parseInt(dateSplit[2])<minYearLimit){
                        			isDateValid=false;
                        		}
                        	}
                        }//BASEBUILD80_REST_API----END
                    }
                }

            }
        } catch (Exception e)
        {
            logger.error("\nException in com/appnetix/app/util/financials/DateUtil.java-->checkDateValidationForDisplayFormat(String date)", e);
            return false;//BASEBUILD80_REST_API
        }

        return isDateValid;
    }

    public static boolean checkDate(String date)
    {
        Date dt = getDate(date);
        if (dt == null)
            return false;
        else
        {
            String format = (date.indexOf("-") != -1) ? DB_FORMAT : Constants.DISPLAY_FORMAT;
            date = getPaddedDate(date);
            return (formatDate(dt, format).equals(date));
        }
    }

    @SuppressWarnings("unused")
    public static String getPaddedDate(String date)
    {
        String format = (date.indexOf("-") != -1) ? DB_FORMAT : Constants.DISPLAY_FORMAT;
        String sep = (date.indexOf("-") != -1) ? "-" : "/";
        String dateSplit[] = date.split(sep);
        if (Integer.parseInt(dateSplit[2]) > 29 && Integer.parseInt(dateSplit[2]) < 100)
            dateSplit[2] = Integer.toString(Integer.parseInt(dateSplit[2]) + 1900);
        else if (Integer.parseInt(dateSplit[2]) <= 29)
            dateSplit[2] = Integer.toString(Integer.parseInt(dateSplit[2]) + 2000);

        return StringUtil.getPaddedString(dateSplit[0], "0", 2, true) + sep + StringUtil.getPaddedString(dateSplit[1], "0", 2, true) + sep + dateSplit[2];
    }

    /**
     * This method is used for checking date for Exchange Rate submodule.
     *
     * @param date
     * @return boolean
     */
    public static boolean checkExchangeDate(String date)
    {
        Date dt = getDate(date);
        if (dt == null)
            return false;
        else
        {
            String format = (date.indexOf("-") != -1) ? DB_FORMAT : Constants.DISPLAY_FORMAT;
            if (formatDate(dt, format).equals(date))
                return true;
            else
                return false;
        }
    }

    public static int getMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    public static Date addDate(int iParam)
    {
        return addDate(getDate(), iParam, "d");
    }

    public static Date addDate(String date, int iParam)
    {
        return addDate(getDate(date), iParam, "d");
    }

    public static Date addDate(Date date, int iParam)
    {
        return addDate(date, iParam, "d");
    }

    public static Date addDate(String date, int iParam, String pType)
    {
        return addDate(getDate(date), iParam, pType);
    }

    public static Date addDate(Date date, int iParam, String pType)
    {
        if (date == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (pType.equals("d"))
            cal.add(Calendar.DATE, iParam);
        else if (pType.equals("m"))
            cal.add(Calendar.MONTH, iParam);
        else if (pType.equals("y"))
            cal.add(Calendar.YEAR, iParam);
            //added hrs for time in fim task.
        else if (pType.equals("Hrs"))
            cal.add(Calendar.HOUR, iParam);
            //P_CC_ENHANCEMENT starts added for minute
        else if (pType.equals("Min"))
            cal.add(Calendar.MINUTE, iParam);
        else if (pType.equals("Sec"))
            cal.add(Calendar.SECOND, iParam);//Zc_CM_B_ 40384
        //P_CC_ENHANCEMENT ends
        return cal.getTime();
    }

    public static String formatDate(String date)
    {
        if (!com.home.builderforms.StringUtil.badString(date))
        {
            return formatDate(getDate(date), null);
        }

        return "";
    }

    /**
     * Methods defined here which takes dateString as paramter won't work in all cases.
     * As we can't get the exact position of date and month in dateString given.
     * In that case using methods which takes date parameter instead of String will be handy.
     * This method takes to parameters dateString and format of dateString given which helps us to convert it
     * into date object.
     *
     * @param date        Date String which has to be formatted.
     * @param givenFormat format of the string specified.
     * @return date string in standard format defined in property file.
     * @throws ParseException
     */
    public static String formatDateGivenFormat(String date, String givenFormat) throws ParseException
    {
        if (!com.home.builderforms.StringUtil.badString(date))
        {
            SimpleDateFormat dateParser = new SimpleDateFormat(givenFormat);
            return formatDate(dateParser.parse(date), null);
        }

        return "";
    }

    public static String formatDate(String date, String format)
    {
        if (!com.home.builderforms.StringUtil.badString(date))
            return formatDate(getDate(date), format);
        return "";
    }

    public static String formatDate(Date date)
    {
        return formatDate(date, null);
    }

    public static String formatDate(Date date, String format)
    {
        if (format == null)
            format = Constants.DISPLAY_FORMAT;

        SimpleDateFormat sdf = new SimpleDateFormat(format, LanguageUtil.getUserLocale());
        //P_E_FIN_UPGRADE added by vivek maurya starts
        if (date != null)
        {
            return sdf.format(date);
        }
        return "";
        //P_E_FIN_UPGRADE added by vivek maurya ends
    }

    public static String getStartOfYear()
    {
        return getStartOfYear(null, null);
    }

    /**
     * @param String Date
     * @return String
     * <p/>
     * This method converts the given date format into the required format from MM/DD/YYYY to DD/MM/YYYY.
     * @author Rittika
     */
    public static String getDateFormatChanged(String Date)
    {
        String month = "";
        String year = "";
        String date = "";
        String format = Constants.DISPLAY_FORMAT;
        if (Date != null && !Date.equals("null") && !Date.equals(""))
        {
            if (format.equalsIgnoreCase("dd/MM/YYYY"))
            {
                date = Date.substring(0, Date.indexOf('/'));
                month = Date.substring(Date.indexOf('/') + 1, Date.lastIndexOf('/'));
                year = Date.substring(Date.lastIndexOf('/') + 1);
                if ((month.trim()).length() == 1)
                    month = "0" + month;
                if ((date.trim()).length() == 1)
                    date = "0" + date;
                Date = month + "/" + date + "/" + year;
            }
        }
        return Date;
    }

    /**
     * @param String Date
     * @return String
     * <p/>
     * This method converts the given date format into the required format from current Date format to required date format.
     * @author Rittika
     */
    @SuppressWarnings("unused")
    public static String getDateFormatChanged(String Date, String newformat, String currentFormat)
    {
        String month = "";
        String year = "";
        String date = "";
        String format = Constants.DISPLAY_FORMAT;
        if (Date != null && !Date.equals("null") && !Date.equals(""))
        {

            if (currentFormat.equalsIgnoreCase("dd/MM/YYYY"))
            {
                date = Date.substring(0, Date.indexOf('/'));
                month = Date.substring(Date.indexOf('/') + 1, Date.lastIndexOf('/'));
                year = Date.substring(Date.lastIndexOf('/') + 1);
                if ((month.trim()).length() == 1)
                    month = "0" + month;
                if ((date.trim()).length() == 1)
                    date = "0" + date;
            }
            if (currentFormat.equalsIgnoreCase("mm/dd/YYYY"))
            {
                month = Date.substring(0, Date.indexOf('/'));
                date = Date.substring(Date.indexOf('/') + 1, Date.lastIndexOf('/'));
                year = Date.substring(Date.lastIndexOf('/') + 1);
                if ((month.trim()).length() == 1)
                    month = "0" + month;
                if ((date.trim()).length() == 1)
                    date = "0" + date;
            }
            // Added by Nikhil Singh for bug  P_BUG_ID_11075 starts
            if (currentFormat.equalsIgnoreCase("yyyy/MM/dd"))
            {
                Date = Date.trim();
                year = Date.substring(0, Date.indexOf('/'));
                month = Date.substring(Date.indexOf('/') + 1, Date.lastIndexOf('/'));
                date = Date.substring(Date.lastIndexOf('/') + 1);

                if ((month.trim()).length() == 1)
                    month = "0" + month;
                if ((date.trim()).length() == 1)
                    date = "0" + date;
            }
            // Added by Nikhil Singh for bug  P_BUG_ID_11075 ends
            if (currentFormat.equalsIgnoreCase(DB_FORMAT))
            {
            	Date = Date.trim();//P_B_53209
                year = Date.substring(0, Date.indexOf('-'));
                month = Date.substring(Date.indexOf('-') + 1, Date.lastIndexOf('-'));
                date = Date.substring(Date.lastIndexOf('-') + 1);
                if ((month.trim()).length() == 1)
                    month = "0" + month;
                if ((date.trim()).length() == 1)
                    date = "0" + date;
            }
            if (StringUtil.isValidNew(newformat))
            {
                if (newformat.equalsIgnoreCase(DB_FORMAT))
                    Date = year + "-" + month + "-" + date;

                else if (newformat.equalsIgnoreCase("MM/dd/yyyy"))
                {
                    Date = month + "/" + date + "/" + year;
                } else if (newformat.equalsIgnoreCase("dd/MM/yyyy"))
                {
                    Date = date + "/" + month + "/" + year;
                }
            }
        }
        return Date;
    }

    public static String getStartOfYear(String format)
    {
        return getStartOfYear(null, format);
    }

    public static String getStartOfYear(String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null)
            return null;
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, 0);
        return formatDate(cal.getTime(), format);
    }

    public static String getNextDateForWeekday(int dayOfWeek)
    {
        return getNextDateForWeekday(dayOfWeek, null, null);
    }

    public static String getNextDateForWeekday(int dayOfWeek, String format)
    {
        return getNextDateForWeekday(dayOfWeek, null, format);
    }

    public static String getNextDateForWeekday(int dayOfWeek, String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        if (cal.get(Calendar.DAY_OF_WEEK) > dayOfWeek)
            cal.add(Calendar.DATE, 7 - (cal.get(Calendar.DAY_OF_WEEK) - dayOfWeek));
        else
            cal.add(Calendar.DATE, dayOfWeek - cal.get(Calendar.DAY_OF_WEEK));

        return formatDate(cal.getTime(), format);
    }

    public static String getStartOfWeek()
    {
        return getStartOfWeek(null, null);
    }

    public static String getStartOfWeek(String date)
    {
        return getStartOfWeek(date, null);
    }

    public static String getStartOfWeek(String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        if (cal.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY)
            cal.add(Calendar.DATE, Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK));
        else
            cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK)) - 7);

        return formatDate(cal.getTime(), format);
    }

    public static String getEndOfWeek()
    {
        return getNextDateForWeekday(Calendar.SUNDAY, null, null);
    }

    public static String getEndOfWeek(String date)
    {
        return getNextDateForWeekday(Calendar.SUNDAY, date, null);
    }

    public static String getEndOfWeek(String date, String format)
    {
        return getNextDateForWeekday(Calendar.SUNDAY, date, format);
    }

    public static String getNextDateForMonth(int dayOfMonth)
    {
        return getNextDateForMonth(dayOfMonth, null, null);
    }

    public static String getNextDateForMonth(int dayOfMonth, String format)
    {
        return getNextDateForMonth(dayOfMonth, null, format);
    }

    public static String getNextDateForMonth(int dayOfMonth, String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        if (cal.get(Calendar.DAY_OF_MONTH) > dayOfMonth)
        {
            cal.set(Calendar.DATE, dayOfMonth);
            cal.add(Calendar.MONTH, 1);
        } else
            cal.set(Calendar.DATE, dayOfMonth);

        return formatDate(cal.getTime(), format);
    }

    public static String getNextDateForQuarter(int dayOfMonth)
    {
        return getNextDateForQuarter(dayOfMonth, null, null);
    }

    public static String getNextDateForQuarter(int dayOfMonth, String format)
    {
        return getNextDateForQuarter(dayOfMonth, null, format);
    }

    public static String getNextDateForQuarter(int dayOfMonth, String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        if (cal.get(Calendar.MONTH) == 0 || cal.get(Calendar.MONTH) == 3 || cal.get(Calendar.MONTH) == 6 || cal.get(Calendar.MONTH) == 9)
        {
            if (cal.get(Calendar.DAY_OF_MONTH) > dayOfMonth)
            {
                cal.set(Calendar.DATE, dayOfMonth);
                cal.add(Calendar.MONTH, 3);
            } else
                cal.set(Calendar.DATE, dayOfMonth);
        } else if (cal.get(Calendar.MONTH) == 1 || cal.get(Calendar.MONTH) == 4 || cal.get(Calendar.MONTH) == 7 || cal.get(Calendar.MONTH) == 10)
        {
            cal.set(Calendar.DATE, dayOfMonth);
            cal.add(Calendar.MONTH, 2);

        } else if (cal.get(Calendar.MONTH) == 2 || cal.get(Calendar.MONTH) == 5 || cal.get(Calendar.MONTH) == 8 || cal.get(Calendar.MONTH) == 11)
        {
            cal.set(Calendar.DATE, dayOfMonth);
            cal.add(Calendar.MONTH, 1);

        }
        return formatDate(cal.getTime(), format);
    }

  //ENH_PW_SMART_QUESTIONS_STARTS
    public static String getPreviousDateForQuarter(int dayOfMonth, String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        if (cal.get(Calendar.MONTH) == 0 || cal.get(Calendar.MONTH) == 3 || cal.get(Calendar.MONTH) == 6 || cal.get(Calendar.MONTH) == 9)
        {
            if (cal.get(Calendar.DAY_OF_MONTH) > dayOfMonth)
            {
                cal.set(Calendar.DATE, dayOfMonth);
                cal.add(Calendar.MONTH, -0);
            } else
                cal.set(Calendar.DATE, dayOfMonth);
        } 
        else if (cal.get(Calendar.MONTH) == 1 || cal.get(Calendar.MONTH) == 4 || cal.get(Calendar.MONTH) == 7 || cal.get(Calendar.MONTH) == 10)
        {
            cal.set(Calendar.DATE, dayOfMonth);
            cal.add(Calendar.MONTH, -1);
        } 
        else if (cal.get(Calendar.MONTH) == 2 || cal.get(Calendar.MONTH) == 5 || cal.get(Calendar.MONTH) == 8 || cal.get(Calendar.MONTH) == 11)
        {
            cal.set(Calendar.DATE, dayOfMonth);
            cal.add(Calendar.MONTH, -2);
        }

        return formatDate(cal.getTime(), format);
    }
    
  //ENH_PW_SMART_QUESTIONS_ENDS
    
    
    public static String getStartOfMonth()
    {
        return getStartOfMonth(null, null);
    }

    public static String getStartOfMonth(String date)
    {
        return getStartOfMonth(date, null);
    }

    public static String getStartOfMonth(String date, String format)
    {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DATE, 1);
        return formatDate(cal.getTime(), format);
    }

    public static String getEndOfMonth()
    {
        return getEndOfMonth(null, null);
    }

    public static String getEndOfMonth(String date)
    {
        return getEndOfMonth(date, null);
    }

    public static String getEndOfMonth(String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return formatDate(cal.getTime(), format);
    }

    public static String getNextDateFor15Days(int dayOfMonth1, int dayOfMonth2)
    {
        return getNextDateFor15Days(dayOfMonth1, dayOfMonth2, null);
    }

    public static String getNextDateFor15Days(int dayOfMonth1, int dayOfMonth2, String format)
    {
        return getNextDateFor15Days(dayOfMonth1, dayOfMonth2, null, format);
    }

    public static String getNextDateFor15Days(int dayOfMonth1, int dayOfMonth2, String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        if (currDay < dayOfMonth1)
            cal.set(Calendar.DATE, dayOfMonth1);
        else if (currDay > dayOfMonth1 && currDay < dayOfMonth2)
            cal.set(Calendar.DATE, dayOfMonth2);
        else if (currDay > dayOfMonth2)
        {
            cal.set(Calendar.DATE, dayOfMonth1);
            cal.add(Calendar.MONTH, 1);
        }

        return formatDate(cal.getTime(), format);
    }

    public static String getStartOf15Days()
    {
        return getStartOf15Days(null, null);
    }

    public static String getStartOf15Days(String date)
    {
        return getStartOf15Days(date, null);
    }

    public static String getStartOf15Days(String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        if (currDay < 16)
            cal.set(Calendar.DATE, 1);
        else
            cal.set(Calendar.DATE, 16);

        return formatDate(cal.getTime(), format);
    }

    public static String getEndOf15Days()
    {
        return getEndOf15Days(null, null);
    }

    public static String getEndOf15Days(String date)
    {
        return getEndOf15Days(date, null);
    }

    public static String getEndOf15Days(String date, String format)
    {
        Calendar cal = getCalendar(date);
        if (cal == null) return null;

        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        if (currDay < 16)
        {
            cal.set(Calendar.DATE, 15);
            return formatDate(cal.getTime(), format);
        } else
            return getEndOfMonth(date);

    }

    //ENH_Month_Combo         2007-jul-13-16  Bhoopal G
    @SuppressWarnings("deprecation")
    public static int getYear(Date date)
    {
        return (date.getYear() + 1900);

    }

    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate
     * @param format   "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    public static final String getMonthAndYear(String date, String format)
    {
        return getMonthAndYear(DateUtil.getDate(date), format);
    }

    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate
     * @param format   "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    public static final String getMonthAndYear(Date date, String format)
    {
        String monthname = "";
        String value = "";
        int year = 0;
        boolean monyear = true;

        if (format.equalsIgnoreCase("y-m"))
        {
            monyear = false;
        }

        monthname = DateUtil.getMonthNameShort(date);
        year = DateUtil.getYear(date);

        if (monyear)
            value = monthname + " - " + year;
        else
            value = year + " - " + monthname;

        return value;
    }


    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate   in form
     * @param date
     * @param monthcount
     * @param direction  negative value = vaules in descending order
     * @param format     "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    @SuppressWarnings("rawtypes")
    public static SequenceMap getMonthsAndYear(String fromdate, String date, int monthcount, int direction, String format)
    {
        return getMonthsAndYear(DateUtil.getDate(fromdate), DateUtil.getDate(date), monthcount, direction, format);
    }

    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate   in form
     * @param date
     * @param monthcount
     * @param orderby    value "desc" gives vaules in descending order
     * @param format     "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    @SuppressWarnings("rawtypes")
    public static SequenceMap getMonthsAndYear(String fromdate, String date, int monthcount, String orderby, String format)
    {
        return getMonthsAndYear(DateUtil.getDate(fromdate), DateUtil.getDate(date), monthcount, orderby, format);
    }

    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate   in form
     * @param date
     * @param monthcount
     * @param orderby    value "desc" gives vaules in descending order
     * @param format     "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    @SuppressWarnings("rawtypes")
    public static SequenceMap getMonthsAndYear(Date fromdate, Date date, int monthcount, String orderby, String format)
    {
        int direction = 1;

        if (orderby.trim().equalsIgnoreCase("desc"))
            direction = -1;

        return getMonthsAndYear(fromdate, date, monthcount, direction, format);
    }

    /**
     * Prepare a map of Months & Year .
     *
     * @param fromdate
     * @param date
     * @param monthcount
     * @param direction  negative value = vaules in descending order
     * @param format     "m-y" tells values to be in month-year format
     * @author Bhoopal
     */

    @SuppressWarnings({"rawtypes", "deprecation", "unchecked"})
    public static SequenceMap getMonthsAndYear(Date fromdate, Date date, int monthcount, int direction, String format)
    {


        SequenceMap retmap = new SequenceMap();
        String key = "";
        String value = "";
        int year = 0;
        int iparam = 1;
        boolean monyear = true;
        String monthname = "";

        if (format.equalsIgnoreCase("y-m"))
        {
            monyear = false;
        }

        if (direction < 0)
        {
            iparam = -1;
        }

        if (monthcount <= 0)
        {

            monthcount = com.home.builderforms.DateTime.getNoOfMonth(fromdate, date);
        }


        if (fromdate != null && date != null)
        {


            for (int i = 0; i <= monthcount; i++)
            {

                if ((date.before(fromdate) && fromdate.getMonth() != date.getMonth()) ||
                        (date.before(fromdate) && fromdate.getMonth() == date.getMonth() && fromdate.getYear() != date.getYear()))
                {
                    break;
                }

                if (iparam < 0)
                {
                    key = DateUtil.formatDate(DateUtil.getStartOfMonth(DateUtil.formatDate(date)), DateUtil.DB_FORMAT);//  year+"-"+.getMonth(date);
                    monthname = DateUtil.getMonthNameShort(date);
                    year = DateUtil.getYear(date);
                    date = DateUtil.addDate(date, iparam, "m");
                } else
                {
                    key = DateUtil.formatDate(DateUtil.getStartOfMonth(DateUtil.formatDate(fromdate)), DateUtil.DB_FORMAT);//  year+"-"+.getMonth(date);
                    monthname = DateUtil.getMonthNameShort(fromdate);
                    year = DateUtil.getYear(fromdate);
                    fromdate = DateUtil.addDate(fromdate, iparam, "m");
                }


                if (monyear)
                    value = monthname + " - " + year;
                else
                    value = year + " - " + monthname;

                retmap.put(key, value);
            }
        }

        return retmap;
    }

    @SuppressWarnings("deprecation")
    public static String getMonthNameShort(Date date)
    {
        return LanguageUtil.getString(monthSortNames[date.getMonth()]);
    }

    public static String getJavascriptForNewCalendar(HttpServletRequest request, String formName, String textboxName, boolean year_scroll, boolean time_comp)
    {
        StringBuffer javascriptCode = new StringBuffer("");
        String sContextName = request.getContextPath();

        if (sContextName != null && sContextName.startsWith("/"))
        {
            sContextName = sContextName.substring(1);
        }

        javascriptCode.append("<script language='JavaScript'>\n");

        // abhishek gupta - replace hard coded calender name, use formated name for js file
        javascriptCode.append("var " + textboxName + "= new " + Constants.CALENDAR_INDEX + "(document.forms[\"" + formName + "\"].elements['" + textboxName + "']);\n");
        javascriptCode.append(textboxName + ".year_scroll = " + year_scroll + ";\n");
        javascriptCode.append(textboxName + ".time_comp = " + time_comp + ";\n");
        javascriptCode.append(textboxName + ".contextname = \"" + sContextName + "\";\n");
        javascriptCode.append("</script>");
        return javascriptCode.toString();
    }

    /**
     * @return Current formatted date + time.
     */
    public static String getCurrentDateTime()
    {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(Constants.DISPLAY_FORMAT_HMS);
        return sdf.format(getCalendar().getTime());
    }

    public static String getCurrentDateTimeDB()
    {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(DB_FORMAT_HMS);
        return sdf.format(getCalendar().getTime());
    }

    /**
     * P_FS_Enh_12Apr10
     * Created By Vikram Raj
     *
     * @return String with the current time
     * @desc method to get the current time of the db
     */
    public static String getCurrentTimeDB()
    {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(TIME_HMS);
        return sdf.format(getCalendar().getTime());
    }

    /**
     * @param stDate
     * @param enDate
     * @return
     * @author abhishek gupta on 13Nov 2008
     */
    public static String getWeekOfYear(String stDate, String enDate)
    {
        //Calendar cal = getCalendar(enDate);

        long days = DateTime.getDaysBetweenDates(stDate, enDate);
        int weekNo = (int) Math.round(days / 7);

        return String.valueOf(weekNo);
    }

    @SuppressWarnings("rawtypes")
    public static int getFiscalWeekNumber(String date)
    {
        int weekNumber = 0;

        //Getting Fiscal Year in which the date falls.
        String fiscalYear = getFiscalYear(date);

        //Getting all Week maps of the given fiscal year.
        SequenceMap weekMap = getFiscalWeeks(fiscalYear, Integer.toString(Integer.parseInt(fiscalYear) + 1));

        String monday = getStartOfWeek(date, DateUtil.DB_FORMAT);

        //Iterating over weekMap to find week number.
        for (int i = 0; i < weekMap.size(); i++)
        {
            if (monday.equals((String) weekMap.get(i)))
            {
                weekNumber = i + 1;
            }
        }
        return weekNumber;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static SequenceMap getFiscalWeeks(String startYear, String endYear)
    {

        //Getting Date of 14th Monday.
        String date1 = getFiscalWeekStart(startYear);
        String date2 = getFiscalWeekStart(endYear);

        SequenceMap weekMap = new SequenceMap();
        Calendar cal1 = getCalendar(date1);

        Calendar cal2 = getCalendar(date2);
        String tempDate = date1;

        int i = 0;
        //Populating SequenceMap with start date of each week in the fiscal year.
        while (cal1.compareTo(cal2) != 0 && cal1.compareTo(cal2) != 1)
        {
            weekMap.put(i, tempDate);
            tempDate = DateUtil.formatDate(DateUtil.addDate(tempDate, 7, "d"), DateUtil.DB_FORMAT);
            cal1.add(Calendar.DATE, 7);
            i++;
        }
        return weekMap;
    }

    @SuppressWarnings("unused")
    public static String getFiscalWeekStart(String year)
    {
        String date = year + "-01-01";
        Calendar cal = getCalendar(getStartOfYear(date, null));

        String firstWeekStart = getStartOfWeek(getStartOfYear(date, null));

        //Getting date of 14th Monday of the year.
        String firstFiscalWeekStart = DateUtil.formatDate(DateUtil.addDate(firstWeekStart, 13 * 7, "d"), DateUtil.DB_FORMAT);
        return firstFiscalWeekStart;

    }

    public static String getFiscalYear(String date)
    {
        Calendar cal = getCalendar(date);
        String fiscalStart = getFiscalWeekStart(Integer.toString(cal.get(Calendar.YEAR)));
        Calendar fiscal = getCalendar(fiscalStart);
        int currentYear = cal.get(Calendar.YEAR);
        String fiscalYear = null;

        //Checking whether the date falls in the fiscal year started in the current calendar year        
        if (cal.compareTo(fiscal) >= 0)
        {
            fiscalYear = Integer.toString(currentYear);
        } else
        {
            fiscalYear = Integer.toString(currentYear - 1);

        }
        return fiscalYear;
    }

    /**
     * @param String recvDate
     * @return boolean
     * <p/>
     * This method compares a date with current date.
     * @author Dheerendra
     */
    public static boolean compareTwoDate(String recvDate)
    {
        Date today = new Date();
        Date dt = DateUtil.getDate(recvDate);


        if (today.compareTo(dt) < 0)
            return true;


        return false;
    }


    /**
     * @param String date1,date2
     * @return Returns true if date1 is less than or equal to date2
     * @throws NullPointerException if second date is null
     *                              This method compares a date with current date.
     * @author YashuKant Tyagi
     */
    public static boolean compareTwoDates(String date1, String date2)
    {
        Date first = DateUtil.getDate(date1);
        Date second = DateUtil.getDate(date2);
        if (first.compareTo(second) < 0)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * @param String recvDate
     * @return boolean
     * <p/>
     * This method compares a date with current date.
     * @author Priyanka
     */
    public static boolean compareTwoDateFormat(String recvDate)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date today = new Date();
        String[] todayDate = simpleDateFormat.format(today).split("/");
        Date dt = DateUtil.getDate(recvDate);
        String[] dtDate = simpleDateFormat.format(dt).split("/");
        if (Integer.parseInt(todayDate[2]) < Integer.parseInt(dtDate[2]))
        {
            return true;
        } else if (Integer.parseInt(todayDate[2]) > Integer.parseInt(dtDate[2]))
        {
            return false;
        } else if (Integer.parseInt(todayDate[0]) < Integer.parseInt(dtDate[0]))
        {
            return true;
        } else if (Integer.parseInt(todayDate[0]) > Integer.parseInt(dtDate[0]))
        {
            return false;
        } else if (Integer.parseInt(todayDate[1]) < Integer.parseInt(dtDate[1]))
        {
            return true;
        }

        return false;
    }

    /**
     * P_FRANONLINE_E_70012
     * Added By Saurabh Sinha
     * Description - This method will return the age imn terms of integervalue
     *
     * @param dateOfBirthString
     * @return
     */
    public static int getAgeInTermsOfYear(String dateOfBirthString)
    {
        Date currentDate = null;
        Date dateOfBirth = null;
        int dateOfBirthYear = -1;
        int currentDateYear = -1;
        int year = -1;
        SimpleDateFormat simpleDateformatY = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleDateformatM = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateformatD = new SimpleDateFormat("dd");
        //if(!com.home.builderforms.PortalUtils.isBadString(dateOfBirthString)){
        if (!com.home.builderforms.BaseUtils.isBadString(dateOfBirthString))
        {     //For Product_Seperation_BL By Amar Singh.
            try
            {
                currentDate = DateUtil.getDate();
                dateOfBirth = DateUtil.getDate(dateOfBirthString);
                dateOfBirthYear = Integer.parseInt(simpleDateformatY.format(dateOfBirth));
                currentDateYear = Integer.parseInt(simpleDateformatY.format(currentDate));
                year = (currentDateYear - dateOfBirthYear);
                if (Integer.parseInt(simpleDateformatM.format(dateOfBirth)) > Integer.parseInt(simpleDateformatM.format(currentDate)))
                {
                    year--;
                } else if (Integer.parseInt(simpleDateformatM.format(dateOfBirth)) == Integer.parseInt(simpleDateformatM.format(currentDate)))
                {
                    if (Integer.parseInt(simpleDateformatD.format(dateOfBirth)) > Integer.parseInt(simpleDateformatD.format(currentDate)))
                    {
                        year--;
                    }
                }
            } catch (Exception e)
            {
                logger.error("Exceptation in DateUtil Class in getAgeInTermsOfYear() Method :::" + e.getMessage());
            }
        }
        return year;
    }

    /**
     * This method will manipulate age of given date from current in term of number of month
     *
     * @param dateFrom
     * @return
     * @author abhishek gupta
     * @date 20 nov 2009
     */
    @SuppressWarnings("deprecation")
    public static int getAgeInTermsOfMonths(String dateFrom)
    {
        double AVERAGE_MILLIS_PER_MONTH = 365.24 * 24 * 60 * 60 * 1000 / 12;
        Date currentDate = null;
        Date givenDate = null;
        int count = 0;
        try
        {
            currentDate = new Date();
            givenDate = new Date(dateFrom);
            Double value = ((currentDate.getTime() - givenDate.getTime()) / AVERAGE_MILLIS_PER_MONTH);
            count = value.intValue();
        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in getAgeInTermsOfMonths() Method :::" + e.getMessage());
        }

        return count;
    }

    /**
     * This method will return age of week of given date
     *
     * @param dateFrom
     * @return
     * @author abhishek gupta
     * @date 20 nov 2009
     */
    public static int getAgeInTermsOfWeeks(String dateFrom)
    {
        long MILLISECONDS_PER_WEEK = 1000 * 60 * 60 * 24 * 7;

        Date currentDate = null;
        Date givenDate = null;
        try
        {
            currentDate = DateUtil.getDate();
            givenDate = DateUtil.getDate(dateFrom);

            long time1 = currentDate.getTime();
            long week1 = time1 / MILLISECONDS_PER_WEEK;

            if (time1 % MILLISECONDS_PER_WEEK > 0)
            {
                ++week1;
            }

            long time2 = givenDate.getTime();
            long week2 = time2 / MILLISECONDS_PER_WEEK;

            if (time2 % MILLISECONDS_PER_WEEK > 0)
            {
                ++week2;
            }

            return (int) Math.abs(week1 - week2);
        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in getAgeInTermsOfWeeks() Method 11:::" + e.getMessage());
            return 0;
        }
    }

    /**
     * This method will process date have date format 'yyyy w' or 'w yyyy'where w is the week number of the year(yyyy)
     * Example as '2006 23' date is used as input in function, means 23rd week of year 2006
     *
     * @param dateFrom
     * @return
     * @author abhishek gupta
     * @date 21 nov 2009
     */
    public static int getAgeFromYearWithWeekToWeeks(String dateFrom)
    {
        long MILLISECONDS_PER_WEEK = 1000 * 60 * 60 * 24 * 7;

        Date currentDate = null;
        Date givenDate = null;
        try
        {
            currentDate = DateUtil.getDate();
            givenDate = DateUtil.getDate(dateFrom);
            /**
             * This will normalize date for week as Monday is the first day of week for given date
             */
            currentDate = normalize(currentDate);
            long time1 = currentDate.getTime();
            long week1 = time1 / MILLISECONDS_PER_WEEK;

            if (time1 % MILLISECONDS_PER_WEEK > 0)
            {
                ++week1;
            }
            /**
             * This will normalize date for week as Monday is the first day of week for given date
             * Also accept date format 'yyyy w' or 'w yyyy' only
             */
            givenDate = normalizeYearAndWeek(dateFrom);
            long time2 = givenDate.getTime();
            long week2 = time2 / MILLISECONDS_PER_WEEK;

            if (time2 % MILLISECONDS_PER_WEEK > 0)
            {
                ++week2;
            }

            return (int) Math.abs(week1 - week2);
        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in getAgeFromYearWithWeekToWeeks() Method :::" + e.getMessage());
            return 0;
        }
    }

    /**
     * This will normalize date for GregorianCalendar for standard date format
     *
     * @param date
     * @return
     * @author abhishek gupta
     * @date 20 nov 2009
     */
    public static Date normalize(Date date)
    {
        GregorianCalendar gregory = new GregorianCalendar();
        gregory.setTime(date);
        gregory.set(Calendar.HOUR, 12);
        gregory.set(Calendar.MINUTE, 0);
        gregory.set(Calendar.SECOND, 0);
        gregory.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return gregory.getTime();
    }

    /**
     * This will normalize date for week as Monday is the first day of week for given date
     * Also accept date format 'yyyy w' or 'w yyyy' only
     *
     * @param yearWithWeek
     * @return
     * @author abhishek gupta
     * @date 21 nov 2009
     */
    public static Date normalizeYearAndWeek(String yearWithWeek)
    {
        GregorianCalendar gregory = new GregorianCalendar();
        if (yearWithWeek != null && (yearWithWeek.split(" ")[0].trim().length() == 2))
        {
            gregory.set(Calendar.YEAR, Integer.parseInt(yearWithWeek.split(" ")[1].trim()));
            gregory.set(Calendar.HOUR, 12);
            gregory.set(Calendar.MINUTE, 0);
            gregory.set(Calendar.SECOND, 0);
            gregory.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(yearWithWeek.split(" ")[0].trim()));
            gregory.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (yearWithWeek != null && (yearWithWeek.split(" ")[0].trim().length() == 4))
        {
            gregory.set(Calendar.YEAR, Integer.parseInt(yearWithWeek.split(" ")[0].trim()));
            gregory.set(Calendar.HOUR, 12);
            gregory.set(Calendar.MINUTE, 0);
            gregory.set(Calendar.SECOND, 0);
            gregory.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(yearWithWeek.split(" ")[1].trim()));
            gregory.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }

        return gregory.getTime();
    }

    public static Calendar getCalendarByDate(String date)
    {
        Calendar calendar = null;
        int startDay = -1;
        int startMonth = -1;
        int startYear = -1;
        calendar = Calendar.getInstance();
        if (StringUtil.isValidNew(date))
        {
            if (Constants.isUSDateFormat)
            {
                startDay = Integer.parseInt(date.substring(3, 5));
                startMonth = Integer.parseInt(date.substring(0, 2));
                startYear = Integer.parseInt(date.substring(6));
            } else
            {
                startDay = Integer.parseInt(date.substring(0, 2));
                startMonth = Integer.parseInt(date.substring(3, 5));
                startYear = Integer.parseInt(date.substring(6));
            }
            calendar.set(startYear, startMonth - 1, startDay, 0, 0, 0);
        }
        return calendar;
    }

    public static Calendar getCalendarByDateTime(String currentUserTime)
    {
        Calendar calendar = null;
        int startDay = -1;
        int startMonth = -1;
        int startYear = -1;
        calendar = Calendar.getInstance();
        if (StringUtil.isValidNew(currentUserTime))
        {

            startDay = Integer.parseInt(currentUserTime.substring(8, 10));
            startMonth = Integer.parseInt(currentUserTime.substring(5, 7));
            startYear = Integer.parseInt(currentUserTime.substring(0, 4));

            calendar.set(startYear, startMonth - 1, startDay, 0, 0, 0);
        }
        return calendar;
    }


    public static String getAgeAnother(String dateOfBirthString)
    {
        String DateOfBirth = null;
        logger.info("dateOfBirthString=" + dateOfBirthString);
        if (StringUtil.isValidNew(dateOfBirthString))
        {
            DateOfBirth = getAgeAnother(DateUtil.getDate(dateOfBirthString));
        } else
        {
            DateOfBirth = "";
        }
        return DateOfBirth;
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static String getAgeAnother(Date dateOfBirth)
    {
        Date currentDate = null;
        Date currentDateMonthStart = null;
        Date tempBirthDate = null;

        int ageYear = -1;
        int ageMonth = -1;
        int ageDay = -1;

        int tempMonth = -1;
        int firstDays = 0;
        int lastDays = 0;
        logger.info("Input dateOfBirth=" + dateOfBirth);
        try
        {
            currentDate = DateUtil.getDate();
            currentDateMonthStart = getDate(getStartOfMonth(formatDate(currentDate)));
            if (dateOfBirth != null && dateOfBirth.before(currentDate))
            {

                if ((dateOfBirth.getYear() == currentDate.getYear()) && (dateOfBirth.getMonth() == currentDate.getMonth()))
                {
                    ageYear = 0;
                    ageMonth = 0;
                    ageDay = currentDate.getDate() - dateOfBirth.getDate();

                } else
                {
                    tempBirthDate = DateUtil.getDate(DateUtil.formatDate(dateOfBirth));
                    if (tempBirthDate.getDate() == 1)
                    {
                        firstDays = 0;
                    } else
                    {
                        firstDays = (getMonthDays(tempBirthDate) - tempBirthDate.getDate()) + 1;
                        tempBirthDate = getDate(getStartOfMonth(formatDate(addDate(tempBirthDate, 1, "m"))));
                    }

                    logger.info("firstDays=" + firstDays);
                    logger.info("First Of tempBirthDate=" + tempBirthDate);
                    logger.info("currentDateMonthStart=" + currentDateMonthStart);

                    //Calculate Year
                    while (!((tempBirthDate.getYear() == currentDateMonthStart.getYear()) && (tempBirthDate.getMonth() == currentDateMonthStart.getMonth()) && (tempBirthDate.getDate() == currentDateMonthStart.getDate())))
                    {
                        tempMonth++;
                        tempBirthDate = DateUtil.addDate(tempBirthDate, 1, "m");
                        logger.info("tempBirthDate=" + tempBirthDate);
                    }
                    tempBirthDate = DateUtil.addDate(tempBirthDate, -1, "m");
                    logger.info("Final tempMonth=" + tempMonth);
                    logger.info("Final tempBirthDate=" + tempBirthDate);
                    ageYear = tempMonth / 12;
                    ageMonth = (tempMonth % 12) + 1;

                    ageDay = firstDays + currentDate.getDate();

                    if (ageDay >= 30)
                    {
                        ageMonth += 1;
                        ageDay = ageDay - 30;
                    }
                    if (ageMonth >= 12)
                    {
                        ageYear += 1;
                        ageMonth = ageMonth - 12;
                    }

                }
            }
        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in getAgeAnother() Method :::" + e.getMessage());
        }
        return getAgeInFormat(ageYear, ageMonth, ageDay);
    }

    public static String getAgeInFormat(int ageYear, int ageMonth, int ageDay)
    {
        String age = null;
        String yearDisplay = null;
        String monthDisplay = null;
        String dayDisplay = null;
        logger.info("ageYear=" + ageYear);
        logger.info("ageMonth=" + ageMonth);
        logger.info("ageDay=" + ageDay);
        if (ageYear <= 1)
        {
            yearDisplay = "year";
        } else
        {
            yearDisplay = "years";
        }
        if (ageMonth <= 1)
        {
            monthDisplay = "month";
        } else
        {
            monthDisplay = "months";
        }
        if (ageDay <= 1)
        {
            dayDisplay = "day";
        } else
        {
            dayDisplay = "days";
        }
        if (ageYear != -1 && ageMonth != -1 && ageDay != -1)
        {
            age = ageYear + " " + yearDisplay + ", " + ageMonth + " " + monthDisplay + " and " + ageDay + " " + dayDisplay + ".";
        } else
        {
            age = "";
        }
        return age;
    }

    @SuppressWarnings("deprecation")
    public static int getMonthDays(Date gDate)
    {
        int monthDays = -1;
        int monthIndex = -1;
        if (gDate != null)
        {
            monthIndex = gDate.getMonth();
            if (isMonth31(monthIndex))
            {
                monthDays = 31;
            } else if (monthIndex == Calendar.FEBRUARY)
            {
                if (isLeapYear(gDate.getYear()))
                {
                    monthDays = 29;
                } else
                {
                    monthDays = 28;
                }
            } else
            {
                monthDays = 30;
            }
        }
        return monthDays;
    }

    public static boolean isMonth31(int monthIndex)
    {
        boolean isMonth31 = false;
        if ((monthIndex == Calendar.JANUARY) || (monthIndex == Calendar.MARCH) || (monthIndex == Calendar.MAY) || (monthIndex == Calendar.JULY) || (monthIndex == Calendar.AUGUST) || (monthIndex == Calendar.OCTOBER) || (monthIndex == Calendar.DECEMBER))
        {
            isMonth31 = true;
        }
        return isMonth31;
    }

    public static boolean isLeapYear(int year)
    {
        boolean isLeapYear = false;
        if ((year % 4) == 0)
        {
            isLeapYear = true;
        }
        return isLeapYear;
    }

    /**
     * <p>This method will return month name on the basis of passed month index</p>
     *
     * @param monthIndex month index.
     * @author Akhil Gupta
     * P_FS_ClosingReports_Chart
     */
    public static String getMonthName(String monthIndex)
    {

        String monthName = "";
        if ("01".equalsIgnoreCase(monthIndex))
        {
            monthName = "Jan";
        } else if ("02".equalsIgnoreCase(monthIndex))
        {
            monthName = "Feb";
        } else if ("03".equalsIgnoreCase(monthIndex))
        {
            monthName = "Mar";
        } else if ("04".equalsIgnoreCase(monthIndex))
        {
            monthName = "Apr";
        } else if ("05".equalsIgnoreCase(monthIndex))
        {
            monthName = "May";
        } else if ("06".equalsIgnoreCase(monthIndex))
        {
            monthName = "June";
        } else if ("07".equalsIgnoreCase(monthIndex))
        {
            monthName = "July";
        } else if ("08".equalsIgnoreCase(monthIndex))
        {
            monthName = "Aug";
        } else if ("09".equalsIgnoreCase(monthIndex))
        {
            monthName = "Sep";
        } else if ("10".equalsIgnoreCase(monthIndex))
        {
            monthName = "Oct";
        } else if ("11".equalsIgnoreCase(monthIndex))
        {
            monthName = "Nov";
        } else if ("12".equalsIgnoreCase(monthIndex))
        {
            monthName = "Dec";
        }

        return monthName;
    }


    public static String getDisplayDateFormat(int date, int month, int year)
    {
        try
        {
            month += 1;
            StringBuffer newDate = new StringBuffer();
            newDate.append(year);
            newDate.append("-");
            if (month < 10)
            {
                newDate.append("0");
            }
            newDate.append(month);
            newDate.append("-");
            if (date < 10)
            {
                newDate.append("0");
            }
            newDate.append(date);
            return getDisplayDate(newDate.toString());

        } catch (Exception e)
        {
            logger.error(e);
            return "";
        }

    }

    public static String getDateDifference(Date startDate, Date endDate)
    {
        long diff = 0;
        try
        {
            final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

            diff = (endDate.getTime() - startDate.getTime()) / MILLSECS_PER_DAY;
        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in getDateDifference() Method :::" + e.getMessage());
        }
        return String.valueOf(diff);
    }

    //Added By rohit jain For merging util/cm or tms/DateUtil.java in to util/DateUtil.java

    public static String addSubtractCurrentDate(String format, String type, int interval)
    {
        Calendar calReturn = Calendar.getInstance();
        int currentDate = Calendar.DATE;

        if (format == null)
            format = Constants.DISPLAY_FORMAT;
        DateFormat dateFormat = new SimpleDateFormat(format);

        if ("add".equals(type))
            calReturn.add(currentDate, interval);
        else
            calReturn.add(currentDate, -interval);

        return dateFormat.format(calReturn.getTime());

    }

    public static String formatAndConvert(String userNo, String format)
    {
        String currentDate = DateUtil.getCurrentDateAsString(DateUtil.DB_FORMAT_HMS);
        try
        {
            String userTimeZone = com.home.builderforms.UserTimezoneMap.newInstance().getTimezone(userNo);

            currentDate = TimeZoneUtils.performUTCConversion(Constants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, currentDate, DateUtil.DB_FORMAT_HMS, DateUtil.DB_FORMAT_HMS);
            currentDate = DateUtil.formatDate(currentDate, format);

        } catch (Exception e)
        {
            logger.error("Exceptation in DateUtil Class in formatAndConvert() Method :::" + e.getMessage());
        }
        return currentDate;
    }

    /**
     * method will return current date and time in an unbreaking string
     */

    static public String getCurrentDateString()
    {

        Calendar calendar = Calendar.getInstance();
        String tempDate, tempMonth, currentDate;
        int date = calendar.get(Calendar.DATE);
        tempDate = date + "";
        if (date < 10)
            tempDate = "0" + tempDate;
        int month = calendar.get(Calendar.MONTH) + 1;
        tempMonth = month + "";
        if (month < 10)
            tempMonth = "0" + tempMonth;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        currentDate = tempDate + "_" + tempMonth + "_" + year + "_" + hour + "_" + min + "_" + sec;
        calendar = null;
        return currentDate;

    }

    //Ended By rohit jain For merging util/cm or tms/DateUtil.java in to util/DateUtil.java
    //BOEFLY_INTEGRATION : START
//BASEBUILD80_REST_API----START
    public static String validateTime(String time, boolean timeFormat)
    {
        String formatType = (timeFormat) ? "HH:mm" : "HH:mm:ss";

        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        String validTime = null;

        try
        {
            Date dt = formatter.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, dt.getHours());
            cal.set(Calendar.MINUTE, dt.getMinutes());
            if (!timeFormat)
            {
                cal.set(Calendar.SECOND, dt.getSeconds());
            }
            if (cal.get(Calendar.HOUR_OF_DAY) < 10)
            {
                validTime = "0" + Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
            } else
            {
                validTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
            }
            if (cal.get(Calendar.MINUTE) < 10)
            {
                validTime = validTime.concat(":0" + Integer.toString(cal.get(Calendar.MINUTE)));
            } else
            {
                validTime = validTime.concat(":" + Integer.toString(cal.get(Calendar.MINUTE)));
            }
            if (!timeFormat)
            {
                if (cal.get(Calendar.SECOND) < 10)
                {
                    validTime = validTime.concat(":0" + Integer.toString(cal.get(Calendar.SECOND)));
                } else
                {
                    validTime = validTime.concat(":" + Integer.toString(cal.get(Calendar.SECOND)));
                }
            }
        } catch (Exception e)
        {
            logger.error("\nException in com/appnetix/app/util/DateUtil.java-->validateTime", e);
            return "";
        }
        return validTime;
    }

    public static String timeDifference(String time1, String time2, boolean format)
    {
        String formatType = (format) ? "HH:mm" : "HH:mm:ss";

        SimpleDateFormat timeFormat = new SimpleDateFormat(formatType);
        String diffTime = null;
        try
        {
            Date timeFirst = timeFormat.parse(time1);
            Date timeSecond = timeFormat.parse(time2);
            if (timeSecond.after(timeFirst))
            {
                int hours = 0, minutes = 0, seconds = 0;
                long diff = timeSecond.getTime() - timeFirst.getTime();

                int timeInSeconds = (int) diff / 1000;
                hours = timeInSeconds / 3600;
                timeInSeconds = timeInSeconds - (hours * 3600);
                minutes = timeInSeconds / 60;
                timeInSeconds = timeInSeconds - (minutes * 60);
                seconds = timeInSeconds;
                if (format)
                {
                    diffTime = hours + ":" + minutes;
                } else
                {
                    diffTime = hours + ":" + minutes + ":" + seconds;
                }
                diffTime = validateTime(diffTime, format);
            }
        } catch (Exception e)
        {
            logger.error("\nException in com/appnetix/app/util/DateUtil.java-->timeDifference", e);
            return "";
        }
        return diffTime;
    }

    public static String compareDate(String date1, String date2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DISPLAY_FORMAT);
        try
        {
            Date dateFirst = sdf.parse(date1);
            Date dateSecond = sdf.parse(date2);
            if (dateFirst.after(dateSecond))
            {
                return "1";
            } else if (dateFirst.before(dateSecond))
            {
                return "2";
            } else if (dateFirst.equals(dateSecond))
            {
                return "0";
            }
        } catch (Exception e)
        {
            logger.error("\nException in com/appnetix/app/util/DateUtil.java-->compareDate", e);
        }
        return "";
    }
   //BASEBUILD80_REST_API----END
   //BOEFLY_INTEGRATION : END

  //Franbuzz Time Format change : starts
    public static String getDateInFranbuzzFormat(String userTimezone,Timestamp currentTStampObj,Timestamp userTimeStamp,boolean forWidget) {
    	String time = FieldNames.EMPTY_STRING;
    	try {
    		if(forWidget) {
    			if(currentTStampObj.getDate() == userTimeStamp.getDate() && currentTStampObj.getMonth() == userTimeStamp.getMonth()) {
    				time = LanguageUtil.getString("at")+"&nbsp;"+formatDate(userTimeStamp.toString(), "hh:mm a");
    			} else {
    				time = LanguageUtil.getString("on")+"&nbsp;"+formatDate(userTimeStamp.toString(), "dd MMMM");
    			}
    		} else {
    			if(currentTStampObj.getYear() == userTimeStamp.getYear()) {
    				time = formatDate(userTimeStamp.toString(), "dd MMMM");
    				time += "&nbsp;"+LanguageUtil.getString("at")+"&nbsp;"+formatDate(userTimeStamp.toString(), "hh:mm a");
    			} else {
    				time = formatDate(userTimeStamp.toString(), "dd MMMM yyyy");
    			}
    		}
    	} catch (Exception e) {
    		logger.error("Error in changing time format==============",e);
    	}
    	return time;
    } //Franbuzz Time Format change : ends
}

