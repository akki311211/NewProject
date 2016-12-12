/**
 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Version No.		    Date		    By	             Against       Function Changed               Comments
 P_SC_CR_009  	 8 Sep 2014 	  Ronak Maru	CR(Srv314-20140506-009)   Ability to have Tax (State And Local Tax / Tax) Functionality per each Product/Service (At Invoice and Product Level)
 ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */
package com.home.builderforms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.home.builderforms.DBConnectionManager;
import com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;





import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.appnetix.app.components.masterdatamgr.manager.MasterDataMgr;
import com.appnetix.app.exception.AppException;
import com.appnetix.app.portal.FormCustomization.BuilderFormFieldNames;
import com.appnetix.app.portal.role.UserRoleMap;
import com.home.builderforms.ModuleUtil.MODULE_NAME;
import com.home.builderforms.MultipartRequest;
import com.appnetix.app.portal.ws.WSDataHandler;
import com.home.builderforms.cache.CacheMgr;
import com.home.builderforms.cache.StateCache;
//import com.home.builderforms.cache.UserDataCache;
import com.home.builderforms.database.DependentTable;
import com.home.builderforms.database.Field;
import com.home.builderforms.database.FieldMappings;
import com.home.builderforms.database.HeaderField;
import com.home.builderforms.database.HeaderMap;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;


public class BaseUtils {
    static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(BaseUtils.class);
    public static final String USER_LEVEL_ZERO = "0";   //Copied from MenuUtils class.
    public static final String USER_LEVEL_ONE = "1";
    public static final String USER_LEVEL_TWO = "2";
    public static final String USER_LEVEL_THREE = "3";
    public static final String USER_LEVEL_SIX = "6";

    private final static Set<String> moduleSyncItems = new HashSet<String>();
    
	static
	{
		moduleSyncItems.add("systemLogos");
	    moduleSyncItems.add("taskDisplayCalendar");
	    moduleSyncItems.add("calCategory");
	    moduleSyncItems.add("calendarStartDay");
	    moduleSyncItems.add("dictionary");
	    moduleSyncItems.add("footerAndSign");
	    moduleSyncItems.add("fromReturnPath");
	    moduleSyncItems.add("supportEmail");
	    moduleSyncItems.add("callStatus");
	    moduleSyncItems.add("callType");
	    moduleSyncItems.add("taskTypeConfig");
	    moduleSyncItems.add("keyWords");
	    moduleSyncItems.add("userTheme");
	    moduleSyncItems.add("addModifySignature");
	    moduleSyncItems.add("piiConfiguration");
	    moduleSyncItems.add("decimalPlace");
	    moduleSyncItems.add("mashUpAccount");
	}
	
    private static final BaseUtils _instance = new BaseUtils();
    private BaseUtils() {
    }
  
    /*public static String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };*/     //Copied from BasePortalUtils class.
    //Copied from BaseNewPortalUtils class.
    //ENH_MODULE_CUSTOM_TABS starts //AUDIT_ENHANCEMENT_CHANGES starts
		/*
		 * This method is used for inserting a row in a table where we do not know number of columns
		 * @param
		 * #1-tabInfo contains 
		 * key as column name
		 * value as column value
		 * 
		 * #2-tableName is the name of the table
		 */
    public static HashMap getGoogleTiemZoneMap() {
        HashMap<String, String> timeZoneMap = new HashMap<String, String>();
        timeZoneMap.put("GMT+00:00","GMT");
        timeZoneMap.put("GMT+00:30","GMT+00:30");
        timeZoneMap.put("GMT+01:00","Africa/Algiers");
        timeZoneMap.put("GMT+01:30","GMT+01:30");
        timeZoneMap.put("GMT+02:00","Africa/Blantyre");
        timeZoneMap.put("GMT+02:30","GMT+02:30");
        timeZoneMap.put("GMT+03:00","Africa/Addis_Ababa");
        timeZoneMap.put("GMT+03:30","Asia/Tehran");
        timeZoneMap.put("GMT+04:00","Asia/Baku");
        timeZoneMap.put("GMT+04:30","Asia/Kabul");
        timeZoneMap.put("GMT+05:00","Antarctica/Mawson");
        timeZoneMap.put("GMT+05:30","Asia/Calcutta");
        timeZoneMap.put("GMT+06:00","Asia/Almaty");
        timeZoneMap.put("GMT+06:30","Asia/Rangoon");
        timeZoneMap.put("GMT+07:00","Asia/Bangkok");
        timeZoneMap.put("GMT+07:30","GMT+07:30");
        timeZoneMap.put("GMT+08:00","Asia/Brunei");
        timeZoneMap.put("GMT+08:30","GMT+08:30");
        timeZoneMap.put("GMT+09:00","Asia/TokyoJapan");
        timeZoneMap.put("GMT+09:30","Australia/Adelaide");
        timeZoneMap.put("GMT+10:00","Australia/Brisbane");
        timeZoneMap.put("GMT+10:30","Australia/Lord_Howe");
        timeZoneMap.put("GMT+11:00","Pacific/Efate");
        timeZoneMap.put("GMT+11:30","Pacific/Norfolk");
        timeZoneMap.put("GMT+12:00","Pacific/Fiji");
        timeZoneMap.put("GMT-12:00","Etc/GMT+12");
        timeZoneMap.put("GMT-11:30","GMT-11:30");
        timeZoneMap.put("GMT-11:00","Pacific/Samoa");
        timeZoneMap.put("GMT-10:30","GMT-10:30");
        timeZoneMap.put("GMT-10:00","US/Hawaii");
        timeZoneMap.put("GMT-09:30","Pacific/Marquesas");
        timeZoneMap.put("GMT-09:00","US/Alaska");
        timeZoneMap.put("GMT-08:30","GMT-08:30");
        timeZoneMap.put("GMT-08:00","US/Pacific");
        timeZoneMap.put("GMT-07:30","GMT-07:30");
        timeZoneMap.put("GMT-07:00","US/Mountain");
        timeZoneMap.put("GMT-06:30","GMT-06:30");
        timeZoneMap.put("GMT-06:00","US/Central");
        timeZoneMap.put("GMT-05:30","GMT-05:30");
        timeZoneMap.put("GMT-05:00","US/Eastern");
        timeZoneMap.put("GMT-04:30","America/Caracas");
        timeZoneMap.put("GMT-04:00","America/Anguilla");
        timeZoneMap.put("GMT-03:30","Canada/Newfoundland");
        timeZoneMap.put("GMT-03:00","America/Argentina/Buenos_Aires");
        timeZoneMap.put("GMT-02:30","GMT-02:30");
        timeZoneMap.put("GMT-02:00","Atlantic/South_Georgia");
        timeZoneMap.put("GMT-01:30","GMT-01:30");
        timeZoneMap.put("GMT-01:00","Atlantic/Azores");
        timeZoneMap.put("GMT-00:30","GMT-00:30");
        return timeZoneMap;
    }
    
    //Copied from BaseNewPortalUtils class.
    public static <K,V> HashMap<K,V> getNewHashMapWithKeyValueType() {
        return new HashMap<K,V>();
    }
    //Copied from BaseNewPortalUtils class.
    public static Info getLanguageDataInfo() {
        Info info = null;
        String query = null;
        ResultSet result = null;
        try {
            info = new Info();
            query = "SELECT LANGUAGE,LANGUAGE_NAME FROM LANGUAGE_DATA ORDER BY LANGUAGE_NAME";
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(result.getString("LANGUAGE"),
                        LanguageUtil.getString(result.getString("LANGUAGE_NAME")));   //P_B_ADMIN_7458
            }// end while
        } catch (Exception e) {
            logger.error("ERROR: exception in getLeadMarketingCode ::" + e);
        } finally {
            if (result != null) {
                result.release();
                result = null;
            }
        }
        return info;
    }

    //Copied from CommonUtil class.
    public static boolean getFilteredValue(String paramValue, String requiredValue, String trueloggerText, String falseloggerText){
        boolean isPresent = false;
        if(StringUtil.isValidNew(paramValue) && paramValue.trim().equalsIgnoreCase(requiredValue)){
            isPresent = true;
        }
        if(isPresent){
            logger.info(trueloggerText);
        }else{
            logger.info(falseloggerText);
        }
        return isPresent;
    }

    //Copied from BasePortalUtil class.
    public static String getAuditFormatDate(String date) {
        String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
        int i = 0;
        String returnDate = "";
        if (date == null || date.equals("")) {
            return returnDate;
        }
        StringTokenizer dateSTr = new StringTokenizer(date);
        String day = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
        String month = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
        String year = dateSTr.hasMoreTokens() ? dateSTr.nextToken() : "";
        for (; i < 12; i++)
            if (month.equals(months[i]))
                break;
        if (i <= 8)
            month = "0" + (i + 1);
        //P_E_FIM_58658 By Nikhil Verma
        if(date!=null && date.length() >= 10 && i==12 && date.indexOf("AM")==-1 && date.indexOf("PM")==-1 && date.indexOf("No Time Scheduled")==-1)
        {
            if(date.length()>10)
                date = date.substring(0,10).trim();

            month = date.substring(0,2);
            day = date.substring(3,5);
            year = date.substring(6);
        }
        else
        {
            month = new Integer((i + 1)).toString();
            //P_B_FIM_56578 By Nikhil Verma
            if(month!=null && month.length()==1)
                month="0"+month;
        }
        if(com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantConstants().DISPLAY_FORMAT.equals("MM/dd/yyyy"))
        {
            returnDate = month + "/" + day + "/" + year;
        }
        else
        {
            returnDate = day + "/" + month + "/" + year;
        }
        //P_E_FIM_58658 By Nikhil Verma
        if(date.indexOf("AM")!=-1 || date.indexOf("PM")!=-1 || date.indexOf("No Time Scheduled")!=-1)
            returnDate=date;
        return returnDate;
    }





    //Copied from BasePortalUtil class.
    public static String filterValue(String pValue) {

        if (pValue == null || pValue.length() == 0)
            return (pValue);

        //Return passed value if starts with "&#" and has ";" characters.(UTF-8 chars for chinese and european and other charsets)
        if (pValue.startsWith("&#") && pValue.contains(";"))
        {
            return pValue;
        }
        //end Return

        char[] content = pValue.toCharArray();
        int size = content.length;
        StringBuffer result = new StringBuffer(size + 50);
        int mark = 0;
        String replacement = null;
        boolean flag = false;//caribo-20140328-030
        for (int i = 0; i < size; i++) {
            //Juice-20131211-016 : starts
            if(flag || (i+7<size && "&".equals(content[i]+"") && "#".equals(content[i+1]+"") && "x".equals(content[i+2]+"") && ";".equals(content[i+7]+""))) //caribo-20140328-030
            {
                //caribo-20140328-030 starts
							/*if(specialCharList.contains(content[i+3]+""+content[i+4]+""+content[i+5]+""+content[i+6]+"")){
								i=i+7;*/
                flag = flag?false:true;
                continue;
                //}
            } //Juice-20131211-016 : ends
            //caribo-20140328-030 ends
            switch (content[i]) {
                case '<':
                    replacement = "&lt;";
                    break;
                case '>':
                    replacement = "&gt;";
                    break;
                case '&':
                    replacement = "&amp;";
                    break;
                case '"':
                    replacement = "&quot;";
                    break;
                case '\n':
                    replacement = "<br />";//AUDIT_ENHANCEMENT_CHANGES
                    break;
                case '\r':
                    replacement = FieldNames.EMPTY_STRING;
                    break;
                case '\f':
                    replacement = FieldNames.EMPTY_STRING;
                    break;
                case '?':
                    replacement = "&#63;";
                    break;
                case '\'':
                    replacement = "&#39;";
                    break;
                case '#':
                    replacement = "&#35;";
                    break;
                case '\\':
                    replacement = "&#92;";
                    break;
                case '/':
                    replacement = "&#47;";
                    break;
                case '%':
                    replacement = "&#37;";
                    break;
                case '+':
                    replacement = "&#43;";
                    break;
                case '=':
                    replacement = "&#61;";
                    break;
                case '[':
                    replacement = "&#91;";
                    break;
                case ']':
                    replacement = "&#93;";
                    break;
            }

            if (replacement != null) {
                if (mark < i) {
                    result.append(content, mark, i - mark);
                }
                result.append(replacement);
                replacement = null;
                mark = i + 1;
            }
        }
        if (mark < size) {
            result.append(content, mark, size - mark);
        }
        return (result.toString());

    }


    //Copied from BasePortalUtil class.
    /**
     * This method takes a phone number as a sring and returns the formatted
     * string in (xxx)yyy-zzzz format only if the country is set to USA
     *
     * @param phone-String
     *            PhoneNo.as an input Parameter
     * @param-string country as an input Parameter
     * @return -StringBuffer as formatted string in (xxx)yyy-zzzz format
     */
    public static String formatPhoneNo(String phone, String country) {
        if (phone == null)
            phone = "";
        StringBuffer sb = new StringBuffer(phone);
        try {
            if (country != null && country.equals("USA")) {
                String validChars = "0123456789";
                for (int count = 0; count < sb.length(); count++) {
                    if (validChars.indexOf(sb.charAt(count)) == -1) {
                        sb = sb.deleteCharAt(count);
                        count = -1;
                    }
                }

                //Sanjeev K F_Phone_formating
                /**	if (sb.length() > 10) {
                 int difference = sb.length() - 10;
                 sb = new StringBuffer(sb.substring(0, sb.length()
                 - difference));
                 }*/

                if(country != null && country.equals("USA")) {
                    if (sb.length() == 10) {
                        sb.insert(0, '(');
                        sb.insert(4, ')');
                        // Modified by Anuj 14-Nov-2004
                        // sb.insert(5,'-');
                        sb.insert(5, ' ');
                        sb.insert(9, '-');
                    }
                    else
                    {
                        //else part added by sekhar for bug#53323
                        sb = new StringBuffer(phone);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }
    //Copied from BaseNewPortalUtil class.
    public static String getDataValue(String dataType) {
        return SQLUtil.getColumnValue("MASTER_DATA", "DATA_VALUE", "DATA_TYPE", dataType);
    }

    //Copied from BasePortalUtil class.
    public static String formatPhoneNo(String phone) {
        if (phone == null)
            return "";
        StringBuffer sb = new StringBuffer(phone);
        try {
            String validChars = "0123456789";
            for (int count = 0; count < sb.length(); count++) {
                if (validChars.indexOf(sb.charAt(count)) == -1) {
                    sb = sb.deleteCharAt(count);
                    count = -1;
                }
            }

							/*
							 * if (sb.length() > 10){ int difference = sb.length()-10; sb = new
							 * StringBuffer(sb.substring(0,sb.length()-difference)); }
							 */
            if (sb.length() == 10) {
                sb.insert(0, '(');
                sb.insert(4, ')');
                // Modified by Anuj 14-Nov-2004
                // sb.insert(5,'-');
                sb.insert(5, ' ');
                sb.insert(9, '-');
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }
    //Copied from BasePortalUtil class.
    public static String reverseFilterValue(String pValue)
    {

        if (pValue == null || pValue.length() == 0)
            return (pValue);

        pValue=replaceAll(pValue,"&lt;", "<");
        pValue=replaceAll(pValue,"&gt;", ">");
        pValue=replaceAll(pValue,"&amp;", "&");
        pValue=replaceAll(pValue,"&quot;", "\"");
        pValue=replaceAll(pValue,"&#35;", "#");
        pValue=replaceAll(pValue,"<br>", "\n");
        pValue=replaceAll(pValue,"<br />", "\n");
        pValue=replaceAll(pValue,"&#63;", "?");
        pValue=replaceAll(pValue,"&#39;", "'");
        pValue=replaceAll(pValue,"&#92;", "\\");
        pValue=replaceAll(pValue,"&#37;", "%");
        pValue=replaceAll(pValue,"&#43;", "+");
        pValue=replaceAll(pValue,"&#61;", "=");
        pValue=replaceAll(pValue,"&apos;","'");
        pValue=replaceAll(pValue,"&nbsp;"," ");
        pValue=replaceAll(pValue,"&#91;","[");
        pValue=replaceAll(pValue,"&#93;","] ");
        pValue=replaceAll(pValue,"&#47;","/");
        pValue=replaceAll(pValue,"&#146;","'");
        pValue=replaceAll(pValue,"&#148;","\"");
        return pValue;
    }

    //Copied from BasePortalUtil class.
    /**
     * This method Replace all occurences of _old with _new in _string.
     */
    public static String replaceAll(String _string, String _old, String _new) {
        StringBuffer buffer = new StringBuffer(_string);
        int pos = _string.indexOf(_old);
        if(!StringUtil.isValidNew(_new))
        {
            _new="";
        }
        int newLen = _new.length();
        while (pos != -1) {
            // System.out.println(pos);
            buffer.replace(pos, pos + _old.length(), _new);
            pos = buffer.indexOf(_old,pos+newLen);
//							pos = buffer.toString().indexOf(_old);
        }
        // System.out.println(buffer);
        return buffer.toString();
    }

    //Copied from BasePortalUtil class.
    public static String getCurrentDateCombo(String dateName, String monthName,
                                             String yearName) {
        String date = "";
        String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };

        Calendar cal = Calendar.getInstance();

        int selectDate = cal.get(Calendar.DATE);
        int selectMonth = cal.get(Calendar.MONTH) + 1;
        int selectYear = cal.get(Calendar.YEAR);

        date += ("<select name='" + monthName + "' class = \"formcontrol\">");
        for (int count = 1; count < 13; count++) {
            if (selectMonth == count) {
                date += ("<option selected value = " + count + ">" + months[count - 1]);
            } else {
                date += ("<option value = " + count + ">" + months[count - 1]);
            }
        }
        date += "</select>";

        date += ("<select name='" + dateName + "' class = \"formcontrol\">");
        for (int count = 1; count < 32; count++) {
            if (selectDate == count) {
                date += ("<option selected value = " + count + ">" + count);
            } else {
                date += ("<option value = " + count + ">" + count);
            }
        }
        date += "</select>";
        date += ("<select name='" + yearName + "' class = \"formcontrol\">");
        for (int count = 2004; count < 2013; count++) {
            if (selectYear == count) {
                date += ("<option selected value = " + count + ">" + count);
            } else {
                date += ("<option value = " + count + ">" + count);
            }
        }
        date += "</select>";

        return date;
    }


    //Copied from BasePortalUtil class.
    public static String getModifyDateCombo(String dateName, String monthName,
                                            String yearName, int selectDate, int selectMonth, int selectYear) {
        String date = "";
        String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };

        date += ("<select name = " + monthName + " class = \"formcontrol\">");
        for (int count = 1; count < 13; count++) {
            if (selectMonth == count) {
                date += ("<option selected value = " + count + ">" + months[count - 1]);
            } else {
                date += ("<option value = " + count + ">" + months[count - 1]);
            }
        }
        date += "</select>";

        date += ("<select name = " + dateName + " class = \"formcontrol\">");
        for (int count = 1; count < 32; count++) {
            if (selectDate == count) {
                date += ("<option selected>" + count);
            } else {
                date += ("<option>" + count);
            }
        }
        date += "</select>";

        date += ("<select name = " + yearName + " class = \"formcontrol\">");
        for (int count = 1980; count < 2020; count++) {
            if (selectYear == count) {
                date += ("<option selected>" + count);
            } else {
                date += ("<option>" + count);
            }
        }
        date += "</select>";

        return date;
    }


    //Copied from BasePortalUtil class.
    public static String changeDateFormat(String inputDate) {
        try {
            if (inputDate != null && inputDate.length() > 19) {
                inputDate = inputDate.substring(0, 10);
            }
            if (inputDate != null && !inputDate.trim().equals("")) {
                String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                StringTokenizer strTokens = new StringTokenizer(inputDate, "-");
                String year = "";
                String month = "";
                String day = "";
                // adeed by Tarun
                while (strTokens.hasMoreTokens()) {
                    year = strTokens.nextToken();
                    month = strTokens.nextToken();
                    day = strTokens.nextToken();
                }
                // added by vikas
                if (Integer.parseInt(day) < 10 && day.length() < 2)
                    day = "0" + day;
                month = months[Integer.parseInt(month) - 1];
                return (month + "-" + day + "-" + year);
            }
        } catch (Exception e) {
            Debug.print(e);
        }

        return "";
    }

    //Copied from BasePortalUtil class.
    public static String getDateCombo(String dateName, String monthName,
                                      String yearName) {
        String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        StringBuffer date = new StringBuffer("");
        int count = 0;
        date.append("<select name = ").append(monthName).append(
                " class = \"formcontrol\">");
        date.append("<option selected value = \"00\">MM");
        for (; count < monthNames.length; count++)
            date.append("<option value = ").append(count + 1).append(">")
                    .append(monthNames[count]);
        date.append("</select>");

        date.append("<select name = ").append(dateName).append(
                " class = \"formcontrol\">");
        date.append("<option selected value = \"00\">DD");
        for (count = 1; count < 32; count++)
            date.append("<option value=").append(count).append(" >").append(
                    count);
        date.append("</select>");

        date.append("<select name = ").append(yearName).append(
                " class = \"formcontrol\">");
        date.append("<option selected value = \"0000\">YYYY");
        for (count = 1980; count < 2020; count++)
            date.append("<option value=").append(count).append(" >").append(
                    count);
        date.append("</select>");

        return date.toString();
    }
    //Copied from BasePortalUtil class.
    public static String getEmptyCombo(String features) {
        StringBuffer emptyCombo = new StringBuffer();
        emptyCombo.append("<Select " + features + ">");
        emptyCombo.append("<option value=''>None</option>");
        emptyCombo.append("</select>");
        return emptyCombo.toString();
    }
    //Copied from BasePortalUtil class.
    public static boolean isBadString(String str) {
        if ((str != null) && (str.length() > 0) && (!str.equals("null"))) {
            return false;
        } else {
            return true;
        }
    }

    //Copied from BasePortalUtil class.
    public static long getMaxIntKey(String tableName, String keyColumnName) {
        //BB_ENH_11Nov08
        long maxKey = 0;
        com.home.builderforms.sqlqueries.ResultSet result = null;
        String query = null;

        try {
            query = "SELECT IFNULL(MAX("+keyColumnName+"),0) AS MAX_KEY FROM "+tableName;
            result = QueryUtil.getResult(query.toString(), null);
            if(result.next()){
                maxKey = Long.parseLong(result.getString("MAX_KEY"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception Occured in PortalUtils.getMaxIntKey method...");
        }
        return maxKey;
    }
    //Copied from BaseNewPortalUtil class.
    public static String getStaticPath(HttpServletRequest request) {
        return (request.getContextPath()+"/static"+Constants.STATIC_KEY);
    }


    //Copied from ExportDataManipulator class.
    public static String transformDefaultIntegerValToBlank(String val)
    {
        try
        {
            if (val != null && val.equals(String.valueOf(IntConstants.DEFAULT_INT)))
                return "";
            else
                return val;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }
    //Copied from BaseNewPortalUtils class.
    public static <K,V> LinkedHashMap<K,V> getNewLinkedHashMapWithKeyValueType() {
        return new LinkedHashMap<K,V>();
    }

    //Copied from BasePortalUtils class.
    public static boolean checkArray(String[] argArray) {
        boolean check = false;
        if(argArray!=null && argArray.length>0) {
            check = true;
        }
        return check;
    }


				     /*-------------------------------------------------Generic Ends------------------------*/


    //Copied from BaseNewPortalUtils class.
    public static String getMasterDataValue(String masterDataId) {
        String query = "SELECT DATA_VALUE FROM MASTER_DATA  WHERE  MASTER_DATA_ID ='"+ masterDataId + "'";
        return SQLUtil.getQueryResult(query, "DATA_VALUE");
    }


    //Copied from AuditUtil class.
    public static HashMap getAllCustomTabMappings()
    {
        try
        {
            if(ModuleUtil.auditImplemented())
            {
                Class classObj = Class.forName("com.appnetix.app.portal.audit.AuditCustomTab");
                Method newInstance = classObj.getMethod("newInstance", null);
                Method getAllCustomTabMappings = classObj.getMethod("getAllCustomTabMappings", null);
                Object auditObject = newInstance.invoke(classObj, null);
                return (HashMap)getAllCustomTabMappings.invoke(auditObject, null);
            }
            else
            {
                return null;
            }
        }
        catch(Exception e)
        {
            logger.info("com.appnetix.app.portal.audit.AuditCustomTab not present");
        }
        return null;
    }

    //Copied from BasePortalUtil class.
    public static String getEntityType(String entitytypeid)
            throws ConnectionException {

        String value = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            pstmt = con.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

            // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
            // master_data_id=?");
            pstmt.setInt(1, Integer.parseInt(entitytypeid));
            // System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
            // master_data_id=?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                value = rs.getString(1);

            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            } catch (Exception e) {
                System.out.println("The exception  entiy is " + e);
            }

        } catch (SQLException e) {
            System.out.println("The sqlexception entity  is " + e);
            Debug.print(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println("The final1 entity exception is " + e);
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                System.out.println("The fianl2 entity  exception is " + e);
            }
            try {
                if (con != null)
                    DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }
        return value;
    }
    //Copied from BasePortalUtil class.
    public static String getStoreStatus(String storeid)
            throws ConnectionException {

        String value = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            pstmt = con
                    .prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

            // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
            // master_data_id=?");
            pstmt.setInt(1, Integer.parseInt(storeid));
            // System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
            // master_data_id=?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                value = rs.getString(1);

            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null)
                    pstmt.close();
                pstmt = null;
            } catch (Exception e) {
                System.out.println("The exception is " + e);
            }

        } catch (SQLException e) {
            System.out.println("The sqlexception store  is " + e);
            Debug.print(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println("The final1 store exception is " + e);
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                System.out.println("The fianl2 store  exception is " + e);
            }
            try {
                if (con != null)
                    DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }
        return value;
    }
    //Copied from BasePortalUtil class.
    public static String getTitle(String Titleid) throws ConnectionException {
        // System.out.println("JMK inside Titletype "+Titleid);
        String value = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            pstmt = con
                    .prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

            // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
            // master_data_id=?");
            pstmt.setInt(1, Integer.parseInt(Titleid));
            // System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
            // master_data_id=?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                value = rs.getString(1);

            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null) {
                    pstmt.close();  //Copied from BasePortalUtil class.
                    pstmt = null;
                }
            } catch (Exception e) {
                System.out.println("The exception  Title is " + e);
            }

        } catch (SQLException e) {
            System.out.println("The sqlexception Title  is " + e);
            Debug.print(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println("The final1 Title exception is " + e);
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                System.out.println("The fianl2 Title  exception is " + e);
            }
            try {
                if (con != null)
                    DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }
        return value;
    }
    //Copied from BasePortalUtil class.
    public static String getTransName(String transid)
            throws ConnectionException {

        String value = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            pstmt = con
                    .prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA WHERE master_data_id=?");

            // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
            // master_data_id=?");
            pstmt.setInt(1, Integer.parseInt(transid));
            // System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
            // master_data_id=?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                value = rs.getString(1);

            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null)
                    pstmt.close();
                pstmt = null;
            } catch (Exception e) {
                System.out.println("The exception is " + e);
            }

        } catch (SQLException e) {
            System.out.println("The sqlexception is " + e);
            Debug.print(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println("The final1 exception is " + e);
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                System.out.println("The fianl2 exception is " + e);
            }
            try {
                if (con != null)
                    DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }
        return value;
    }
    //Copied from BasePortalUtil class.
    public static String getStateFromRegion(String stateid)
            throws ConnectionException {
        // System.out.println("JMK inside state "+stateid);
        String value = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);

            // pstmt = con.prepareStatement("SELECT DATA_VALUE FROM MASTER_DATA
            // WHERE master_data_id=?");
            pstmt = con
                    .prepareStatement("SELECT REGION_NAME FROM REGIONS WHERE REGION_NO=?");

            // System.out.println("JBN SELECT DATA_VALUE FROM MASTER_TABLE WHERE
            // master_data_id=?");
            pstmt.setInt(1, Integer.parseInt(stateid));
            // System.out.println("J SELECT DATA_VALUE FROM MASTER_DATA WHERE
            // master_data_id=?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                value = rs.getString(1);

            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {//Copied from BasePortalUtil class.
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            } catch (Exception e) {
                logger.error("The exception is " + e);
            }

        } catch (SQLException e) {
            logger.error("The sqlexception state  is " + e);
            Debug.print(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                logger.error("The final1 state exception is " + e);
            }
            try {
                if (pstmt != null)
                    pstmt.close();//Copied from BasePortalUtil class.
            } catch (Exception e) {
                logger.error("The fianl2 state  exception is " + e);
            }
            try {
                if (con != null)
                    DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }
        return value;
    }
    //Copied from BaseNewPortalUtil class.
    public static Info getLeadBasicInfo(String leadId) {
        Info info = null;
        ResultSet result = null;

        try {
            info = new Info();
            StringBuffer query = new StringBuffer();
            // BB_FS_E_1905
            // query.append("SELECT FIRST_NAME, LAST_NAME, ADDRESS, EMAIL_ID, CITY, STATE_ID, ZIP, COUNTRY, PHONE, PHONE_EXT FROM FS_LEAD_DETAILS WHERE LEAD_ID=");
            query.append("SELECT FLD.PRIMARY_PHONE_TO_CALL, FLD.FIRST_NAME, FLD.LAST_NAME,FLD.HOME_PHONE,FLD.HOME_PHONE_EXT, FLD.ADDRESS, FLD.EMAIL_ID, FLD.CITY, FLD.STATE_ID, FLD.ZIP, FLD.COUNTRY, FLD.PHONE, FLD.PHONE_EXT,FLD.LIQUID_CAPITAL_MAX, RS.REGION_NAME,FLD.FAX,FLD.MOBILE FROM FS_LEAD_DETAILS FLD LEFT JOIN REGIONS RS ON FLD.STATE_ID = RS.REGION_NO WHERE FLD.LEAD_ID=");//P_Enh_ContactHistory_FormBuilder(Added FAX and MOBILE)

            if (leadId != null && leadId.trim().length() != 0
                    && !leadId.equals("null")) {
                query.append(leadId);
                result = QueryUtil.getResult(query.toString(), null);

                while (result.next()) {
                    info.set(FieldNames.FIRST_NAME,
                            result.getString("FIRST_NAME"));
                    info.set(FieldNames.LAST_NAME,
                            result.getString("LAST_NAME"));
                    info.set(FieldNames.ADDRESS, result.getString("ADDRESS"));
                    info.set(FieldNames.EMAIL_ID, result.getString("EMAIL_ID"));
                    info.set(FieldNames.CITY, result.getString("CITY"));
                    info.set(FieldNames.STATE_ID, result.getString("STATE_ID"));
                    info.set(FieldNames.ZIP, result.getString("ZIP"));
                    info.set(FieldNames.COUNTRY, result.getString("COUNTRY"));
                    info.set(FieldNames.PHONE, result.getString("PHONE"));
                    info.set(FieldNames.PHONE_EXT2,
                            result.getString("PHONE_EXT"));
                    // BB_59240
                    info.set(FieldNames.HOME_PHONE,
                            result.getString("HOME_PHONE"));
                    info.set(FieldNames.HOME_PHONE_EXT,
                            result.getString("HOME_PHONE_EXT"));
                    // BB_FS_E_1905
                    info.set(FieldNames.STATE_NAME,
                            result.getString("REGION_NAME"));
                    info.set(FieldNames.LIQUID_CAPITAL_MAX,
                            result.getString("LIQUID_CAPITAL_MAX"));
                    info.set(FieldNames.FAX, result.getString("FAX"));//P_Enh_ContactHistory_FormBuilder
                    info.set(FieldNames.MOBILE, result.getString("MOBILE"));//P_Enh_ContactHistory_FormBuilder
                    info.set(FieldNames.PRIMARY_PHONE_TO_CALL, result.getString("PRIMARY_PHONE_TO_CALL"));

                }
            }
        } catch (Exception e) {
            logger.info("ERROR: exception in Info getLeadBasicInfo ::", e);
        } finally {
            if (result != null) {
                result = null;
            }
        }

        return info;
    }
    //Copied from BasePortalUtil class.
    public static Map<String,String> getServerDetails(String module) {

        String[] columns = {"SERVER_ID","FTP_HOST_NAME","FTP_HOST_URL","FTP_USER_NAME","FTP_PASSWORD","AMAZON_USER_NAME","AMAZON_PASSWORD",
                "CONFIGURED","ACCESS_KEY","SECRET_KEY","SKY_USER_NAME","SKY_PASSWORD","SKY_ACCESS_KEY","SKY_REFRESH_KEY","SKY_CLIENT_ID","SKY_CLIENT_SECRET","CONNECTION_TYPE","DIRECTORY_PATH"};  //P_AB_ENH_AmazonSkyConfig

        String[] infoKeys = {FieldNames.SERVER_ID,FieldNames.FTP_HOST_NAME,FieldNames.FTP_HOST_URL,FieldNames.FTP_USER_NAME,FieldNames.FTP_PASSWORD,
                FieldNames.AMAZON_USER_NAME,FieldNames.AMAZON_PASSWORD,FieldNames.CONFIGURED,FieldNames.ACCESS_KEY,FieldNames.SECRET_KEY,FieldNames.SKY_USER_NAME,
                FieldNames.SKY_PASSWORD,FieldNames.SKY_ACCESS_KEY,FieldNames.SKY_REFRESH_KEY,FieldNames.SKY_CLIENT_ID,FieldNames.SKY_CLIENT_SECRET,FieldNames.CONNECTION_TYPE,FieldNames.DIRECTORY_PATH}; //P_AB_ENH_AmazonSkyConfig

        Map<String, String> serverMap = SQLUtil.getSingleRowMap("SERVER_CONFIGURATION", columns, infoKeys, "MODULE", module, false);
        if(serverMap!=null && serverMap.isEmpty()) {
            for(String key : infoKeys) {
                if(FieldNames.CONFIGURED.equals(key)) {
                    serverMap.put(key, "L");
                } else {
                    serverMap.put(key, FieldNames.EMPTY_STRING);
                }
            }
        }

        return serverMap;
    }

    //Copied from BasePortalUtil class.
    public static boolean isRoleExistForUser(String userNo,String roleID){
        boolean flag = false;
        String query = "SELECT * FROM USER_ROLES WHERE ROLE_ID=? AND USER_NO IN(?)";
        com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult(query.toString(), new Object[] {roleID,userNo});
        if(rs!=null && rs.next()){
            flag = true;
        }
        return flag;
    }
    //Copied from BaseNewPortalUtil class.
    public static boolean checkUploadLimit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String module = (String)session.getAttribute("menuName");

        boolean flag = false;
        String configured = FieldNames.EMPTY_STRING;
        if("admin".equals(module)) {
            module = (String)session.getAttribute("adminModule");
        }

        session.removeAttribute("adminModule");

        if(("training".equals(module) || "intranet".equals(module)) ) {

            configured =getServerDetails(module).get(FieldNames.CONFIGURED);
            if("L".equals(configured)) {
                flag = true;
            }
        } else {
            flag = true;
        }

        return flag;
    }
    //Copied from BaseNewPortalUtil class.
    public static Info getExportSettings() {
        Info info = new Info();
        String query = null;
        ResultSet result = null;
        try {

            query = "SELECT * FROM EXPORT_SETTINGS";
            result = QueryUtil.getResult(query,null);
            if (result.next()) {
                info.set(FieldNames.EXPORT_ID, result.getString("EXPORT_ID"));
                info.set(FieldNames.IS_EXPORT_CONTACT,
                        result.getString("IS_EXPORT_CONTACT"));
                info.set(FieldNames.IS_CONFIGURE_TAB,
                        result.getString("IS_CONFIGURE_TAB"));
                info.set(FieldNames.NUMBER_OF_TABS,
                        result.getString("NUMBER_OF_TABS"));
                info.set(FieldNames.IS_CONFIGURE_PERCENTAGE,
                        result.getString("IS_CONFIGURE_PERCENTAGE"));
                info.set(FieldNames.PERCENTAGE_OF_RECORDS,
                        result.getString("PERCENTAGE_OF_RECORDS"));
                info.set(FieldNames.NUMBER_OF_RECORDS,
                        result.getString("NUMBER_OF_RECORDS"));

            }// end while
        } catch (Exception e) {
            logger.error("ERROR: exception in getExportSettings ::" + e);
        } finally
        {
            QueryUtil.releaseResultSet(result);
        }

        return info;
    }
    //Copied from BaseNewPortalUtil class.
    public static Info getTransactionStartInfo() {
        Info info = new Info();
        ResultSet result = null;
        String query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=40 AND DATA_TYPE=4001";

        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("ESTIMATE_START", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=41 AND DATA_TYPE=6001";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("INVOICE_START", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ", e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=40 AND DATA_TYPE=4002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("ESTIMATE_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=41 AND DATA_TYPE=6002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("INVOICE_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=50 AND DATA_TYPE=11002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("PAYMENT_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5014";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.DEPLOYED_VERSION_NUMBER, result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=51 AND DATA_TYPE=12002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("DEPOSIT_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=50 AND DATA_TYPE=11001";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("PAYMENT_START", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) { //Copied from CommonUtil class.
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=51 AND DATA_TYPE=12001";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("DEPOSIT_START", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=30 AND DATA_TYPE=13001";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("TASK_START", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=30 AND DATA_TYPE=13002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("TASK_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5000";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.VEHICLE_NAME, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5001";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURABLE_TAB, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5002";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.DEFAULT_SCHEDULER_VIEW, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5003";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURABLE_TAB_NAME, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5004";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_VEHICLE, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: " ,e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5005";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_ASSIGN_TO_OTHER, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5020";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_CATEGORY, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: " ,e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5006";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_TAX, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        //P_SC_CR_009 Starts
        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=? AND DATA_TYPE=?";
        try {
            result = QueryUtil.getResult(query, new String[]{"15",MasterEntities.SHOW_STATE_LOCAL_TAX});
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_STATE_LOCAL_TAX, result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=? AND DATA_TYPE=?";
        try {
            result = QueryUtil.getResult(query, new String[]{"15",MasterEntities.SHOW_PRODUCT_INVOICE_LEVEL_TAX});
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_PRODUCT_LEVEL_TAX, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }
        //P_SC_CR_009 Ends
        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5007";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_DISCOUNT, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: " ,e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE PARENT_DATA_ID=15 AND DATA_TYPE=5008";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set(FieldNames.CONFIGURE_SKILL, result
                        .getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE   DATA_TYPE=5009";
        try {
            result = QueryUtil.getResult(query, null);
            if (result.next()) {
                info.set(FieldNames.ASSIGNED_TO, result.getString("DATA_VALUE"));
            } else {
                info.set(FieldNames.ASSIGNED_TO, "Assign To");
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE   DATA_TYPE=5999";
        try {
            result = QueryUtil.getResult(query, null);
            if (result.next()) {
                info
                        .set("AVAILABILITY_USERS", result
                                .getString("DATA_VALUE"));
            } else {
                info.set("AVAILABILITY_USERS", "Resources");
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: " ,e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE   DATA_TYPE=5010";
        try {
            result = QueryUtil.getResult(query, null);
            if (result.next()) {
                info.set(FieldNames.ASSIGN_TO_OTHERS, result
                        .getString("DATA_VALUE"));
            } else {
                info.set(FieldNames.ASSIGN_TO_OTHERS, "Assign To Others");
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ", e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=5013";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("CAREGIVER", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }


        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=5027";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("JOB_START_NO", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in getTransactionStartInfo: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        return info;
    }

    /**
     * This function is used to get the CSRF token for the per user login.
     * @return
     * @throws Exception
     * @author Naman Jain
     */
    public static String getEncryptedTokenValueValue() throws Exception {
    	long token = System.currentTimeMillis();
    	String randomToken = String.valueOf(token);
    	return AESencrypt.encrypt(randomToken);
    }
    
    //Copied from CommonUtil class.
    public static SequenceMap getInvoiceLabels() {
        StringBuffer selectQuery = new StringBuffer();
        selectQuery
                .append("SELECT LABEL_KEY_FIELD, LABEL_NAME, LABEL_VALUE FROM "+MultiTenancyUtil.getTenantConstants().CM_TMS+"TMS_INVOICE_LABELS WHERE IS_ENABLED='Y'");
        SequenceMap labelMap = new SequenceMap();
        ResultSet rs = null;
        try {

            rs = QueryUtil.getResult(selectQuery.toString(), null);

            Info info = new Info();
            while (rs.next()) {
                info = new Info();
                info.set(FieldNames.INV_LABEL_KEY_FIELD, rs
                        .getString("LABEL_KEY_FIELD"));
                info.set(FieldNames.INV_LABEL_NAME, rs.getString("LABEL_NAME"));
                info.set(FieldNames.INV_LABEL_VALUE, rs
                        .getString("LABEL_VALUE"));

                labelMap.put(rs.getString("LABEL_KEY_FIELD"), info);
            }
        } catch (Exception e) {
            logger.info("Exception " + e);
        }
        finally
        {
            QueryUtil.releaseResultSet(rs);
        }
        return labelMap;
    }

    //Copied from BaseNewPortalUtil class.

    public static Info getTransactionTabNameInfo() {
        Info info = new Info();
        ResultSet result = null;
        String query = "SELECT DATA_VALUE,PARENT_DATA_ID FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=4005 ORDER BY PARENT_DATA_ID";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                if (result.getString("PARENT_DATA_ID").equals("40")) {
                    info.set("TMS_FIRST_TAB_NAME", result
                            .getString("DATA_VALUE"));
                } else if (result.getString("PARENT_DATA_ID").equals("50")) {
                    info.set("TMS_FIRST_TAB_NAME_PL", result
                            .getString("DATA_VALUE"));
                } else if (result.getString("PARENT_DATA_ID").equals("60")) {
                    info.set("TMS_FIRST_TAB_NAME_P", result
                            .getString("DATA_VALUE"));
                }
            }
        } catch (Exception e) {
            logger.error("Exception in fetching a First Tab Name of Transaction: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE,PARENT_DATA_ID FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=4006 ORDER BY PARENT_DATA_ID";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                if (result.getString("PARENT_DATA_ID").equals("40")) {
                    info.set("TMS_SECOND_TAB_NAME", result
                            .getString("DATA_VALUE"));
                } else if (result.getString("PARENT_DATA_ID").equals("50")) {
                    info.set("TMS_SECOND_TAB_NAME_PL", result
                            .getString("DATA_VALUE"));
                } else if (result.getString("PARENT_DATA_ID").equals("60")) {
                    info.set("TMS_SECOND_TAB_NAME_P", result
                            .getString("DATA_VALUE"));
                }

            }
        } catch (Exception e) {
            logger.error("Exception in fetching in Second tab name of transaction module: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=4003";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("SHOW_ESTIMATE_CHECK", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in fetching in Show Estimate Check: ",e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        query = "SELECT DATA_VALUE FROM MASTER_DATA_FOR_SCHEDULER WHERE DATA_TYPE=5026";
        try {
            result = QueryUtil.getResult(query, null);
            while (result.next()) {
                info.set("JOB_PREFIX", result.getString("DATA_VALUE"));
            }
        } catch (Exception e) {
            logger.error("Exception in fetching in Show Estimate Check: " + e);
        } finally {
            if(result != null) {
                result.release();
                result = null;
            }
        }

        return info;
    }


    //Copied from LoginBean class.
    public static boolean isCorporateUser(String userNO) {
        Connection con = null;
        String status = "";
        Statement stmt = null;
        java.sql.ResultSet rs = null;

        try {
            con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
//					 	        } catch (ConnectionException ce) {
        } catch (Exception ce) {
            logger.error("Exception in isCorporateUser "+ ce.getMessage());
        }

        try {
            StringBuffer query = new StringBuffer("SELECT USER_IDENTITY_NO FROM USERS WHERE USER_NO =\"");
            query.append(userNO).append("\"");
            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
                status = rs.getString("USER_IDENTITY_NO");
            }
            if (status.equals("-1")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            logger.error("Exception 1 in isCorporateUser", sqle);
            return false;
        } finally {
            try {
//					 					if(stmt != null)
//					 					{
//					 						stmt.close();
//					 						stmt = null;
//					 					}
//					 					
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

            } catch (Exception e) {
                logger.error("Exception 2 in isCorporateUser"+ e.getMessage());
            }

            try {
                DBConnectionManager.getInstance().freeConnection(con);
            } catch (Exception e) {
                logger.error("Exception 3 in isCorporateUser"+ e.getMessage());
            }
        }
    }

    //Copied from MenuUtils class.
    public static boolean canAccessSmartConnect(UserRoleMap userRoleMap,String userLevel,String franchiseeNo) {
        //ENH_SMARTCONNECT_SEP starts
					 			/*return (ModuleUtil.fimImplemented() && "Yes".equalsIgnoreCase(MasterDataMgr.newInstance().getMasterDataDAO().getValueByType(MasterEntities.ENABLE_SMARTCONNECT))
					 					&& ((USER_LEVEL_ONE.equals(userLevel) &&  userRoleMap.isModuleInMap("22") && "Y".equals(NewPortalUtils.getColumnFromTable("FRANCHISEE","IS_FRANCHISEE","FRANCHISEE_NO",franchiseeNo))) || (MultiTenancyUtil.getTenantConstants().ENABLE_SMART_SWITCH)));*/
        //P_B_28946 starts CommonUtil.notAllFO method added
        return ("Yes".equalsIgnoreCase(getDataValue(MasterEntities.ENABLE_SMARTCONNECT)) && ((ModuleUtil.fimImplemented()) || (ModuleUtil.auditImplemented()))
                && ((USER_LEVEL_ONE.equals(userLevel) &&  userRoleMap.isModuleInMap("22") ) //P_E_FC-175
                || ((((USER_LEVEL_ZERO.equals(userLevel) || USER_LEVEL_SIX.equals(userLevel)) &&  userRoleMap.isModuleInMap("24")) || (USER_LEVEL_TWO.equals(userLevel) &&  userRoleMap.isModuleInMap("26"))) && MultiTenancyUtil.getTenantConstants().ENABLE_SMART_SWITCH )));
        //P_B_28946 ends
        //ENH_SMARTCONNECT_SEP ends
    }


    //Copied from MenuUtils class.
    public static boolean canAccessFIM(UserRoleMap userRoleMap,String userLevel) {
        return (ModuleUtil.fimImplemented() && (((USER_LEVEL_ZERO.equals(userLevel) || USER_LEVEL_SIX.equals(userLevel)) && userRoleMap.isModuleInMap("53")) ||
                (USER_LEVEL_TWO.equals(userLevel)  && userRoleMap.isModuleInMap("5000")) || (USER_LEVEL_THREE.equals(userLevel) && userRoleMap.isModuleInMap("5000"))));
    }
    //Copied from MenuUtils class.
    public static boolean canAccessAudit(UserRoleMap userRoleMap,String userLevel) {
        return ((USER_LEVEL_ZERO.equals(userLevel) || USER_LEVEL_SIX.equals(userLevel)) && userRoleMap.isModuleInMap("54") || USER_LEVEL_ONE.equals(userLevel) && userRoleMap.isModuleInMap("541") || (USER_LEVEL_TWO.equals(userLevel) && userRoleMap.isModuleInMap("542")));
    }
    //Copied from CommonUtil class.
    public static boolean notAllFO(String franchiseeNos)
    {
        StringBuilder query = new StringBuilder("SELECT COUNT(IS_FRANCHISEE) AS COUNT FROM FRANCHISEE WHERE FRANCHISEE_NO IN ("+franchiseeNos+") and IS_FRANCHISEE='Y'");
        ResultSet resultSet = null;
        try
        {
            resultSet = QueryUtil.getResult(query.toString(),null);

            if(resultSet!=null && resultSet.next()){
                String count = resultSet.getString("COUNT");
                if(StringUtil.isValid(count))
                {
                    if(Integer.parseInt(count) > 0)
                    {
                        return true;
                    }
                }
            }
        }  catch (Exception e) {
        	e.printStackTrace();
            return false;
        } finally {
            QueryUtil.releaseResultSet(resultSet);
        }

        return false;
    }
    
    
    
    public static boolean isAllFO(String franchiseeNos)
    {
        StringBuilder query = new StringBuilder("SELECT COUNT(IS_FRANCHISEE) AS COUNT FROM FRANCHISEE WHERE FRANCHISEE_NO IN ("+franchiseeNos+") and IS_FRANCHISEE='Y'");
        ResultSet resultSet = null;
        try
        {
            resultSet = QueryUtil.getResult(query.toString(),null);

            if(resultSet!=null && resultSet.next()){
                String count = resultSet.getString("COUNT");
                if(StringUtil.isValid(count))
                {
                    if(Integer.parseInt(count) == 0)
                    {
                        return true;
                    }
                }
            }
        }  catch (Exception e) {
        	e.printStackTrace();
            return false;
        } finally {
            QueryUtil.releaseResultSet(resultSet);
        }

        return false;
    }
    

    public static String getUserName(String userno)
    {
        String sName = FieldNames.EMPTY_STRING;
        Map<String,Object> userMap = CacheMgr.getUserCache().getUser(userno);
        if(userMap != null && StringUtil.isValid((String)userMap.get(FieldNames.USER_NAME)))
        {
            sName = (String)userMap.get(FieldNames.USER_NAME);
        }
        return sName;
    }
    
    public static String getUserFirstName(String userno)
    {
        String sName = FieldNames.EMPTY_STRING;
        Map<String,Object> userMap = CacheMgr.getUserCache().getUser(userno);
        if(userMap != null && StringUtil.isValid((String)userMap.get(FieldNames.USER_NAME)))
        {
            sName = (String)userMap.get(FieldNames.USER_FIRST_NAME);
        }
        return sName;
    }
    
    public static String getUserLastName(String userno)
    {
        String sName = FieldNames.EMPTY_STRING;
        Map<String,Object> userMap = CacheMgr.getUserCache().getUser(userno);
        if(userMap != null && StringUtil.isValid((String)userMap.get(FieldNames.USER_NAME)))
        {
            sName = (String)userMap.get(FieldNames.USER_LAST_NAME);
        }
        return sName;
    }


    public static String getUserName1(int userno)
    {
        return getUserName(userno+FieldNames.EMPTY_STRING);
    }

    public static void updateTableColumnWithCond(String tableName, String colToset,String colVal, String colFrom, String colFrmVal) {
        StringBuffer query = new StringBuffer("UPDATE "+tableName+" SET "+colToset+"='"+colVal+"' ");
        if(colFrom != null) {
            query.append("WHERE " +colFrom + "='" + colFrmVal + "' ");
        }

        try {
            int result = QueryUtil.update(query.toString(), new String[] {});
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
    //Copied from BaseNewPortalUtil class.
    public static void insertBuildConfigurationData(Info propertyInfo,
    		String type)throws AppException {

    	ResultSet rs = null;
    	try {
    		if (propertyInfo != null && propertyInfo.size() > 0) {

    			Iterator itInfo = propertyInfo.getKeySetIterator();
    			String gkeyName = "";
    			String gkeyValue = "";

    			//String gquery = "SELECT GROUP_CONCAT(KEY_NAME SEPARATOR '; ') AS KEY_NAME,GROUP_CONCAT(KEY_VALUE SEPARATOR '; ') AS KEY_VALUE FROM APPLICATION_CONFIGURATION_DATA WHERE KEY_type='"
    				//+ type + "' GROUP BY KEY_TYPE";
    			//rs = QueryUtil.getResult(gquery, null);
    			/*if (rs.next()) {
                    gkeyName = rs.getString("KEY_NAME");
                    gkeyValue = rs.getString("KEY_VALUE");

                    ArrayList oldKey = StringUtil.convertToArrayList(gkeyName, "; ");

                    ArrayList oldValue = StringUtil.convertToArrayList(
                            gkeyValue, "; ");


                    int count = 0;
                    while (itInfo.hasNext()) {
                        String key = (String) itInfo.next();


                        if (oldKey!=null && oldKey.indexOf(key) != -1) {
                            String curVal = propertyInfo.getString(key);
                            String oldVal = (String) oldValue.get(oldKey.indexOf(key));
                            if (curVal==null || !curVal.equals(oldVal)) {
                                // update new value for old value
                                StringBuffer upQuery = new StringBuffer(
                                        "UPDATE APPLICATION_CONFIGURATION_DATA SET KEY_VALUE= ? WHERE KEY_NAME=?");
                                String params[] = {curVal, key};
                                int result = QueryUtil.update(
                                        upQuery.toString(), params);
                            }
                        } else {
                            // insert
                            StringBuffer query = new StringBuffer(
                                    "INSERT INTO APPLICATION_CONFIGURATION_DATA(KEY_NAME,KEY_VALUE,KEY_TYPE) VALUES(?,?,?) ");
                            String params[] = {key,
                                    propertyInfo.getString(key), type};
                            int result = QueryUtil.update(query.toString(),
                                    params);
                        }
                        count++;
                    }
                } else {*/
    			
    			String deleteQuery = "DELETE FROM APPLICATION_CONFIGURATION_DATA";
    			QueryUtil.update(deleteQuery, null);
    			while (itInfo.hasNext()) {
    				String keyName = (String) itInfo.next();
    				String keyValue = propertyInfo.getString(keyName);
    				StringBuffer query = new StringBuffer(
    						"INSERT INTO APPLICATION_CONFIGURATION_DATA(KEY_NAME,KEY_VALUE,KEY_TYPE) VALUES(?,?,?) ");
    				String params[] = {keyName, keyValue, type};
    				int result = QueryUtil.update(query.toString(), params);
    			}
    		}
    		//}

    	} catch (Exception e) {
    		throw new AppException();
    	} finally {
    		if(rs != null) {
    			rs.release();
    			rs = null;
    		}
    	}
    }
    //Copied from CmLeadUtil class.
   
    public static String filterValueForMailText(String pValue) {

        if (pValue == null || pValue.length() == 0)
            return (pValue);

        //Return passed value if starts with "&#" and has ";" characters.(UTF-8 chars for chinese and european and other charsets)
        if (pValue.startsWith("&#") && pValue.contains(";"))
        {
            return pValue;
        }
        //end Return

        char[] content = pValue.toCharArray();
        int size = content.length;
        StringBuffer result = new StringBuffer(size + 50);
        int mark = 0;
        String replacement = null;
        boolean flag = false;//caribo-20140328-030
        for (int i = 0; i < size; i++) {
            //Juice-20131211-016 : starts
            if(flag || (i+7<size && "&".equals(content[i]+"") && "#".equals(content[i+1]+"") && "x".equals(content[i+2]+"") && ";".equals(content[i+7]+""))) //caribo-20140328-030
            {
                //caribo-20140328-030 starts
									/*if(specialCharList.contains(content[i+3]+""+content[i+4]+""+content[i+5]+""+content[i+6]+"")){
										i=i+7;*/
                flag = flag?false:true;
                continue;
                //}
            } //Juice-20131211-016 : ends
            //caribo-20140328-030 ends
            switch (content[i]) {
                case '<':
                    replacement = "&lt;";
                    break;
                case '>':
                    replacement = "&gt;";
                    break;
                case '&':
                    replacement = "&amp;";
                    break;
                case '"':
                    replacement = "&quot;";
                    break;
                case '\r':
                    replacement = FieldNames.EMPTY_STRING;
                    break;
                case '\f':
                    replacement = FieldNames.EMPTY_STRING;
                    break;
                case '?':
                    replacement = "&#63;";
                    break;
                case '\'':
                    replacement = "&#39;";
                    break;
                case '#':
                    replacement = "&#35;";
                    break;
                case '\\':
                    replacement = "&#92;";
                    break;
                case '/':
                    replacement = "&#47;";
                    break;
                case '%':
                    replacement = "&#37;";
                    break;
                case '+':
                    replacement = "&#43;";
                    break;
                case '=':
                    replacement = "&#61;";
                    break;
                case '[':
                    replacement = "&#91;";
                    break;
                case ']':
                    replacement = "&#93;";
                    break;
            }

            if (replacement != null) {
                if (mark < i) {
                    result.append(content, mark, i - mark);
                }
                result.append(replacement);
                replacement = null;
                mark = i + 1;
            }
        }
        if (mark < size) {
            result.append(content, mark, size - mark);
        }
        return (result.toString());

    }
    /**
     * Resturns System log file names with their path...
     * @return
     */
    public static String[] getLogFiles()
    {
        String logDir = Constants.LOG_DIR;
        return new String[]	{ logDir + "/franchise.log", logDir + "/franchise1.log", logDir + "/franchise2.log", logDir + "/franchise3.log", logDir + "/franchise4.log", logDir + "/exceptions.log", logDir + "/errorPageExceptions.log" };
    }


    public static String getCountryName(String countryId)
    {
        String countryName = "";
        Map<String,Object> countryMap = CacheMgr.getCountryCache().getCountry(countryId);
        if (countryMap != null)
        {
            countryName = (String)countryMap.get(FieldNames.COUNTRY_NAME);
        }
        return countryName;
    }
    public static boolean isFimDoc(String path) {
        if("fimCustomerComplaints".equals(path) || "fimContractSigning".equals(path) || "fimLenders".equals(path)
                ||"fimInsurance".equals(path) ||"fimEntityDetail".equals(path) ||"fimQAHistory".equals(path)
                ||"fimRealEstate".equals(path) ||"fimDefaultAndTermination".equals(path) ||"fimARInsurance".equals(path)
                ||"fimARContractSigning".equals(path) ||"fimARLenders".equals(path) ||"fimAREntityDetail".equals(path)
                ||"fimARQAHistory".equals(path) ||"fimARRealEstate".equals(path) ||"fimEntityDisplayDetail".equals(path)) {
            return true;
        } else{
            return false;
        }
    }
    //  GOOGLE_SYNC_API_CHANGE starts
    public static void setGoogleSyncKeys(){
        String query = "SELECT GOOGLE_SYNC_CLIENT_ID, GOOGLE_SYNC_CLIENT_SECRET, GOOGLE_SYNC_REDIRECT_URL FROM GOOGLE_SYNC_KEYS";
        ResultSet resultSet = QueryUtil.getResult(query, null);
        BaseConstants _baseConstants = MultiTenancyUtil.getTenantConstants();
        if(resultSet != null && resultSet.next()) {
            _baseConstants.GOOGLE_SYNC_CLIENT_ID = resultSet.getString("GOOGLE_SYNC_CLIENT_ID");
            _baseConstants.GOOGLE_SYNC_CLIENT_SECRET = resultSet.getString("GOOGLE_SYNC_CLIENT_SECRET");
            _baseConstants.GOOGLE_SYNC_REDIRECT_URL = resultSet.getString("GOOGLE_SYNC_REDIRECT_URL");
        }
    }
    //  GOOGLE_SYNC_API_CHANGE ends


    public static String getFranchiseeNos(HttpSession session)
    {
        String franNo = "";
        String userLevel = (String)session.getAttribute("user_level");
        boolean isAll = "All".equals((String)session.getAttribute("franchisee_all"));//MUID_CHANGES

        if("1".equals(userLevel))
        {
            if(isAll)
            {
                String []ownerArr = getFranchiseeAndOwner(session);
                if(ownerArr != null)
                {
                    franNo = ownerArr[0];
                }
            }
            else
            {
                franNo = (String)session.getAttribute("franchisee_no");
            }
        }
        return franNo;
    }
    
    public static Map<String,String> getGoogleDriveKeys(){
    	
    	String query = "SELECT  GOOGLE_ACCESS_TOKEN,GOOGLE_REFRESH_TOKEN,GOOGLE_CLIENT_ID,GOOGLE_CLIENT_SECRET FROM SERVER_CONFIGURATION WHERE (MODULE='intranet' OR MODULE='training') AND GOOGLE_ACCESS_TOKEN IS NOT NULL AND GOOGLE_ACCESS_TOKEN <>''";
    	ResultSet rs = null;
    	try{
    		rs = QueryUtil.getResult(query, null);
    		if(rs.next()){
    			 Map<String, String> map = BaseUtils.getNewHashMapWithKeyValueType();
    			 map.put("0", rs.getString("GOOGLE_ACCESS_TOKEN"));
    			 map.put("1", rs.getString("GOOGLE_REFRESH_TOKEN"));
    			 map.put("2", rs.getString("GOOGLE_CLIENT_ID"));
    			 map.put("3", rs.getString("GOOGLE_CLIENT_SECRET"));
    			 return map; 
    		}
    	
    	}catch(Exception ex){
    		logger.error("Exception in getGoogleDriveKeys", ex);
    	}
    	return Collections.<String, String>emptyMap();
    }

    /**
     * returns an array of franchiseeNo and userNo in case of franchisee User login and all option selected
     * @param session
     * @return String[]
     */
    public static String[] getFranchiseeAndOwner(HttpSession session)
    {
        String franchiseeOwner[]=new String[2];

        String menuName = (((String) session.getAttribute(FieldNames.MENU_NAME)) == null) ? MultiTenancyUtil.getTenantConstants().DEFAULT_MODULE : ((String) session.getAttribute(FieldNames.MENU_NAME));////P_B_26486
        ArrayList list=(ArrayList)session.getAttribute("FranchiseeNoList");

        if("ppc".equals(menuName)){
            list = (ArrayList)session.getAttribute("FranchiseeNoListAll");
        }

        String franchiseList="";
        try {
            if("All".equals((String)session.getAttribute("franchisee_all"))&&list!=null) {
                for(Object obj:list) {
                    if("All".equals(obj.toString())) {
                        continue;
                    }
                    else if("".equals(franchiseList)) {
                        franchiseList=obj.toString();
                    }
                    else {
                        franchiseList+=","+obj.toString();
                    }
                }

            }
        } catch(Exception e) {
            logger.error("Exception in getFranchiseeAndOwner", e);
        }

        franchiseeOwner[0] = franchiseList;
        franchiseeOwner[1] = (String)session.getAttribute("user_no");
        return franchiseeOwner;
    }

    public static boolean booleanValue(Boolean val)
    {
        return val == null ? false : val;
    }

    public static Map<String, Map<String,Object>> sortData(Map<String, Map<String,Object>> map , final String sortKey)
    {
        List<Map.Entry<String,Map<String,Object>>> list = new LinkedList<Map.Entry<String,Map<String,Object>>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String,Map<String,Object>>>(){

            public int compare(Map.Entry<String,Map<String,Object>> o1, Map.Entry<String,Map<String,Object>> o2)
            {
                Map<String,Object> ob1 = o1.getValue();
                Map<String,Object> ob2 = o2.getValue();

                return (String.valueOf(ob1.get(sortKey))).compareToIgnoreCase(String.valueOf(ob2.get(sortKey)));
            }
        });

        map.clear();
        for (Iterator<Map.Entry<String,Map<String,Object>>> itr = list.iterator();itr.hasNext();)
        {
            Map.Entry<String,Map<String,Object>> entry = itr.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;

    }
    /**
     * BB-20150203-259 (Tab Rename changes)
     * @param tableAnchor
     * @return Tab Name as String
     */
    public static String getTabDisplayNameFs(String tableAnchor)
    {
    	String tabDisplayName="";
    	ResultSet result= null;
    	String query = "SELECT FORM_NAME FROM BUILDER_WEB_FORMS WHERE TABLE_ANCHOR='"+tableAnchor+"'";			
    	try {
    		result	= QueryUtil.getResult(query, null);
    		if(result!=null && result.next())
    		{
    			tabDisplayName = result.getString("FORM_NAME") ;
    		}
    	}


    	catch(Exception e)
    	{
    		logger.error("Exception in getTabDisplayNameFs",e);
    	}
    	finally
    	{
    		query=null;
    		if(result != null) {
    			result.release();
    		}
    	}
    	return tabDisplayName;
    }
    
    /**
     * This function provides the {@link HashMap} which contains the updated Tab Name corresponding to Module.
     * @author Naman Jain
     * @param module
     * @return
     */
    public static HashMap<String, String> getModuleTabMap(String module) {
    	HashMap<String, String> moduleTabMap = new HashMap<String, String>();
    	String whereClause=" MODULE = '"+module+"' ";
        if("mu".equals(module)){
        	whereClause="( MODULE = '"+module+"' OR ( MODULE IN ('fim') AND TABLE_ANCHOR IN ('franchisees','fimTransfer','fimOwners','fimComplaint','fimEntityDetail','fimTraining','fimEmployees'))) ";
        }
    	String query = "SELECT DISPLAY_NAME, FORM_NAME FROM BUILDER_WEB_FORMS WHERE "+whereClause+" AND DISPLAY_NAME IS NOT NULL";
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(query, null);
    		while(result.next()) { //iterating result
    			moduleTabMap.put(result.getString("DISPLAY_NAME"), LanguageUtil.getString(result.getString("FORM_NAME")));
    		}
    	} catch (Exception e) {
			// TODO: handle exception
    		logger.info("Exception in getModuleTabMap=====>>"+e);
		} finally {
			QueryUtil.releaseResultSet(result);
		}
    	return moduleTabMap;
    }
    
    public static Info getModuleBuildTabInfoBytableAnchor(String module) {
    	Info tabInfo = new Info();
    	String moduleName = "";
        if(module.contains("fimarea")){//P_Enh_Mu-Entity_FormGenerator starts
    		module=module.replaceAll("\\bfimarea\\b","area");
    	}
    	if(module.contains("fimmu")){
    		module=module.replaceAll("\\bfimmu\\b","mu");
    	}//P_Enh_Mu-Entity_FormGenerator ends
        module = StringUtil.isValidNew(module)?StringUtil.toCommaSeparatedWithSQuotes(module.split(",")):"";
    	String query = "SELECT TABLE_ANCHOR, FORM_NAME FROM BUILDER_WEB_FORMS WHERE 1=1 AND DISPLAY_NAME IS NOT NULL AND IS_CUSTOM='N'";
    	if(!StringUtil.isValidNew(module)) {
    		if(ModuleUtil.fsImplemented()) {
    			moduleName = moduleName + "'fs'" + ",";
    		}
    		if(ModuleUtil.fimImplemented()) {
    			moduleName = moduleName + "'fim','mu','area'" + ",";//P_Enh_Mu-Entity_FormGenerator
    		}
    		if(ModuleUtil.cmImplemented()) {
    			moduleName = moduleName + "'cm'" + ",";
    		}
    		if(StringUtil.isValidNew(moduleName) && moduleName.endsWith(",")) {
    			moduleName = moduleName.substring(0, moduleName.lastIndexOf(","));
    		}
    	} else {
    		moduleName = module;   
    	}
    	if(StringUtil.isValidNew(moduleName)) {
    		query += " AND MODULE IN ("+moduleName+")";
    	}
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(query, null);
    		while(result.next()) { //iterating result
    			tabInfo.put(result.getString("TABLE_ANCHOR"), LanguageUtil.getString(result.getString("FORM_NAME")));
    		}
    	} catch (Exception e) {
    		// TODO: handle exception
    		logger.info("Exception in getModuleTabMap=====>>"+e);
    	} finally {
    		QueryUtil.releaseResultSet(result);
    	}
    	return tabInfo;
    }
    
    /**
	 * FIN_Import_Opti
	 * @author Vivek Maurya
	 * @date 21Jan2015
	 * returns array of column values
	 */
	public static String[] getResultInArray(String query, String requiredColumn) {
    	ResultSet result = null;
    	String[] resultArray = null;
    	try {
    		result = QueryUtil.getResult(query, null);
    		if(result!=null && result.size() > 0) {
    			resultArray = new String[result.size()];
    			int i = 0;
    			while(result.next()) {
    				resultArray[i++] = result.getString(requiredColumn);
    			}
    		}
    	}
    	catch(Exception e) {
    		logger.error("Exception in getResultInArray ::", e);
    		return null;
    	}
    	finally {
    		QueryUtil.releaseResultSet(result);
    	}
    	return resultArray;
    }
	
	// Struts1_To_Struts2_Migration starts
    /**
     * Filter the specified string for characters that are sensitive to
     * HTML interpreters, returning the string with these characters replaced
     * by the corresponding character entities.
     *
     * @param value The string to be filtered and returned
     */
    public static String filter(String value) {

        if (value == null || value.length() == 0) {
            return value;
        }

        StringBuffer result = null;
        String filtered = null;
        for (int i = 0; i < value.length(); i++) {
            filtered = null;
            switch (value.charAt(i)) {
                case '<':
                    filtered = "&lt;";
                    break;
                case '>':
                    filtered = "&gt;";
                    break;
                case '&':
                    filtered = "&amp;";
                    break;
                case '"':
                    filtered = "&quot;";
                    break;
                case '\'':
                    filtered = "&#39;";
                    break;
            }

            if (result == null) {
                if (filtered != null) {
                    result = new StringBuffer(value.length() + 50);
                    if (i > 0) {
                        result.append(value.substring(0, i));
                    }
                    result.append(filtered);
                }
            } else {
                if (filtered == null) {
                    result.append(value.charAt(i));
                } else {
                    result.append(filtered);
                }
            }
        }

        return result == null ? value : result.toString();
    }
    
    /**
     * Write the specified text as the response to the writer associated with
     * this page.  <strong>WARNING</strong> - If you are writing body content
     * from the <code>doAfterBody()</code> method of a custom tag class that
     * implements <code>BodyTag</code>, you should be calling
     * <code>writePrevious()</code> instead.
     *
     * @param pageContext The PageContext object for this page
     * @param text The text to be written
     *
     *
     */
	public static void write(PageContext pageContext, String text)
	{
		JspWriter writer = pageContext.getOut();
		try
		{
			writer.print(text);
			
		} catch (IOException e)
		{
			logger.error("Exception in BaseUtils.write :::::::: ", e);
		}
	}
	public static String getModuleKey(String val)
	{
		String keyVal=null;
		if(StringUtil.isValid(val))
		{
			try{
				Iterator it=Constants.moduleNameMap.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry thisEntry = (Map.Entry) it.next();
					String key = (String)thisEntry.getKey();
					String value = (String)thisEntry.getValue();
					if(val.contains(value))
					{
						keyVal= key;
						break;
					}
				}	
			}
			catch(Exception e)
			{
				logger.info("Error in getting module key in getModuleKey() :"+e.getMessage());
			}
		}
		return keyVal;
	}
	/**
	 * Sourabh MultiSelect
	 * This method is used to convert Broker Ids which contains B7_ 
	 * in case of different submenus of broker type
	 * ex :  B7_10982038 = 10982038
	 * it reomves B7_ from id ant gives the actual id.
	 * @param elems
	 * @return String
	 */
	public static String convertBrokerIds(String[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		for(int i=0; i<elems.length; i++)
		{
			elems[i] = elems[i].trim();
			if(elems[i].indexOf("B7_")!=-1)
			{
				//elems[i] = elems[i].substring(elems[i].indexOf("_")+1);
				elems[i] = "7";
			}
		}
		return StringUtil.toCommaSeparatedSpaceLess(elems);
	}
 	public static String generateFinChartTable(SequenceMap tableMap ,HttpServletRequest request){
 		StringBuilder chartTableHtml = new StringBuilder();
 		
 		if (tableMap != null && !tableMap.equals("null") && tableMap.size() >= 1) {
 			chartTableHtml.append("<table width=\"50%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" align=\"center\">"); 
 			chartTableHtml.append(" <tr><td bgcolor=\"#000000\"> <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#FFFFFF\">");
 			int count=1;
 			int dataCnt = 0;
 			int loopSize = tableMap.size() / 5;
 			for (int t = 0; t < loopSize; t++) {
 			chartTableHtml.append(" <tr class=\"small_txt\">");
 			chartTableHtml.append("<td nowrap>");
 			chartTableHtml.append("<img src='"+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/spacer.gif' width='3' height='1'/></td>");
 			for (int tt = 0; tt < 5; tt++) {
 				String tempVal = (String) tableMap.get("" + (dataCnt + 1));
 			
 			String count1 = StringUtil.getPaddedString((dataCnt + 1) + "", "0", 2, true);
 			chartTableHtml.append("<td nowrap><span style='font-family:verdana; color:#000000; text-align: center; font-size:9px; width:10px; height:10px; background-color:#CCFFFF'>"+count1+"</span>&nbsp;"+tempVal+"</td>");
 			dataCnt++;
 			}
 			chartTableHtml.append("<td nowrap>");
 			chartTableHtml.append("<img src='"+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/spacer.gif' width='3' height='1'/></td>");
 			chartTableHtml.append("</tr>" );
 			}
 			int finalloopsize = tableMap.size() % 5;
 			if (finalloopsize > 0) {
 			chartTableHtml.append("<tr class=\"small_txt\">");
 			chartTableHtml.append("<td nowrap>");
 			chartTableHtml.append("<img src='"+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/spacer.gif' width='3' height='1'/></td>");
 			for (int tt = 0; tt < finalloopsize; tt++) {
 				String tempVal = (String) tableMap.get("" + (dataCnt + 1));
				String count1 = StringUtil.getPaddedString((dataCnt + 1) + "", "0", 2, true);
				chartTableHtml.append("<td nowrap>").append("<span style=\"font-family:verdana; color:#000000; text-align: center; font-size:9px; width:10px; height:10px; background-color:#CCFFFF\">"+count1+"</span>&nbsp;"+tempVal+"</td>");
 			dataCnt++;
 			}
 			chartTableHtml.append("<td nowrap>");
 			chartTableHtml.append("<img src='"+request.getContextPath()+"/static"+Constants.STATIC_KEY+"/images/spacer.gif' width='3' height='1'/></td>");
 			chartTableHtml.append("</tr>" );
 					}
 			chartTableHtml.append("</table></td></tr></table>");
 		}
 		return chartTableHtml.toString();
 	}
 	/**
 	 * Branding Changes
 	 * @param request
 	 * @param moduleKeyValName
 	 * @param currentDisplayName
 	 * @return
 	 */
 	public static String getMenuDisplayName(HttpServletRequest request, String moduleKeyValName, String currentDisplayName) {
        String moduleName = FieldNames.EMPTY_STRING;
        if(StringUtil.isValidNew(moduleKeyValName)) {
            moduleName=  LanguageUtil.getString(MultiTenancyUtil.getTenantConstants().MODULE_DISPLAY_NAME_MAP.get(moduleKeyValName));
        } else if(request != null) {
            moduleName=  LanguageUtil.getString(MultiTenancyUtil.getTenantConstants().MODULE_DISPLAY_NAME_MAP.get(request.getSession().getAttribute(FieldNames.MENU_NAME)));
        }
        if(StringUtil.isValid(moduleName)) {
            return moduleName;
        } else {
        	return LanguageUtil.getString(currentDisplayName);
        }
    }
 	
	// Struts1_To_Struts2_Migration Ends
/**
 * @author priya kumari
 * //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
 */
 	public static boolean isRoleHasPrivilege(Info tabInfo,SequenceMap roleIdMap,String canViewData){
 		boolean breakFlag=false;
 		Set<String> roleSet= new HashSet<String>();
		if(StringUtil.isValid(tabInfo.getString(canViewData))){
			System.out.println("tabInfo.getString(canViewData)>>>>>>>>>>>>>"+tabInfo.getString(canViewData));
			roleSet = new HashSet<String>(Arrays.asList(tabInfo.getString(canViewData).split(",")));
		}
		
		if(roleSet!=null && roleSet.size()>0){
			Iterator itRole=roleSet.iterator();
			while(itRole.hasNext()){
				if(roleIdMap.containsKey(itRole.next())){
					breakFlag=true;
					break;
				}

			}
		}else
		{
			if( BuilderFormFieldNames.CAN_WRITE_ROLES.equals(canViewData)){
				if(!StringUtil.isValid(tabInfo.getString(BuilderFormFieldNames.CAN_VIEW_ROLES))){
					breakFlag=true;
				}
			}else{
				breakFlag=true;
			}
		}
 		
 		return breakFlag;
 	}
 	
 	
 	/**
	 * Sync Data Using Web Service
	 * @param eventType
	 * @param request
	 * @param parametres
	 */
    public static void syncData(String eventType, HttpServletRequest request, Map<String, String> parametres)
    {
       syncData(eventType, request, parametres,null);
   	}
    public static void syncData(String eventType, HttpServletRequest request, Map<String, String> parametres, MultipartRequest multipartRequest){//SYNC_REASSIGN_USER_ISSUE starets
    	syncData( eventType,  request,parametres,  multipartRequest,null,null);
    }
 	public static void syncData(String eventType, HttpServletRequest request, Map<String, String> parametres, MultipartRequest multipartRequest,String wsUrl,String clientKey)//SYNC_REASSIGN_USER_ISSUE ends
	{
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		try
		{
			if(!_baseConstants.WS_MODULES_SYNC && moduleSyncItems.contains(eventType))
			{
				return;
			}
				if(_baseConstants.WS_SYNC && !_baseConstants.IS_QA_DB)
				{
					WSDataHandler wsDataHandler = null;
					if(multipartRequest!=null)
					{
						wsDataHandler = new WSDataHandler(multipartRequest,eventType, parametres);
					}
					else
					{
						wsDataHandler = new WSDataHandler(request,eventType, parametres);
					}
					Map<String, String> dataMap = wsDataHandler.getWSData();
					//SYNC_REASSIGN_USER_ISSUE starts
					if(StringUtil.isValidNew(wsUrl) && StringUtil.isValidNew(clientKey)){
						wsDataHandler.invokeService(wsDataHandler.createWSDataXml(dataMap),wsUrl,clientKey);
					}
					else{
						wsDataHandler.invokeService(wsDataHandler.createWSDataXml(dataMap));
					}//SYNC_REASSIGN_USER_ISSUE ends
				}
			
		}catch(Exception e)
		{
			logger.error("Exception in Instantiating WSDataHandler", e);
		}
	}
}
