
package com.home.builderforms;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.Info;
import com.home.builderforms.FieldNames;
import org.apache.log4j.Logger;

import com.home.builderforms.BaseDAO;
//import com.appnetix.app.components.usermgr.manager.UserMgr;

/**
 * Class Name	- SnoozeManipulator
 * Location		- com.appnetix.app.portal.calendar
 *
 * <p>
 * @author		- Vikas Mittal
 * @version		- 1.1
 * date			- Tue 27,Sep 2005
 *
 * This class is used to store the userids and their timezones respectively
 */

public class UserTimezoneMap {
    
    static Logger logger = Logger.getLogger(UserTimezoneMap.class);
    private SequenceMap currentMap; // saves the user ids and their timezones
    
    //static object to implement singleton pattern
    private static UserTimezoneMap _instance = null;
    //default private constructor
    private UserTimezoneMap() {
    }
    //static method to get the instance
    public static UserTimezoneMap newInstance() {
        if (_instance == null) {
            _instance = new UserTimezoneMap();
        }
        return _instance;
    }
    
        /*
         *sets rhe time zone
         */
    public void setTimeZone(String userId) {
        //initializing the map
        if (currentMap == null) {
            currentMap = new SequenceMap();
        }
        
        String timezone = "";
        try {
            //if no user id
            if (userId != null
                    && !userId.equals("")
                    && !userId.equals("null")) {
                //	//getting timezone from the map
                //	timezone=(String)currentMap.get(userId);
                //if not found than fires the query
                //	if(!(timezone!=null&&!timezone.equals("")&&!timezone.equals("null"))){
                //user manager object
                //UserMgr userMgr = UserMgr.newInstance();
            	BaseDAO dao=new BaseDAO("users");   //For Product_Seperation_BL By Amar Singh.
                //getting record by calling base dao method
                //Info userDetailInfo =userMgr.getUserDAO().getDetailsInfo(new Integer(userId));
                Info userDetailInfo =dao.getDetailsInfo(new Integer(userId));   //For Product_Seperation_BL By Amar Singh.
                //taking care if record not found
                if (userDetailInfo != null) {
                    timezone =
                            (String) userDetailInfo.getString(FieldNames.TIMEZONE);
                    //setting timezone into map so that for the further use
                    if (timezone != null) {
                    	synchronized (currentMap) {
                    		currentMap.put(userId, timezone);							
						}
                    }
                }
            }
            //}
        } catch (Exception e) {
            logger.error("Exception while getting timezone", e);
        }
        
    }
        /*
         * @ GETS THE TIMEZONE STRING OF THE GIVEN USER ID
         */
    public String getTimezone(String userId) {
        //initializing the map
        if (currentMap == null) {
            currentMap = new SequenceMap();
        }
        
        String timezone = "";
        try {
            //if no user id
            if (userId != null
                    && !userId.equals("")
                    && !userId.equals("null")) {
                //getting timezone from the map
                timezone = (String) currentMap.get(userId);
                //if not found than fires the query
                if (!(timezone != null
                        && !timezone.equals("")
                        && !timezone.equals("null"))) {
                    //user manager object
                    //UserMgr userMgr = UserMgr.newInstance();
                	BaseDAO dao=new BaseDAO("users");   //For Product_Seperation_BL By Amar Singh.
                    //getting record by calling base dao method
                    //Info userDetailInfo =userMgr.getUserDAO().getDetailsInfo(new Integer(userId));
                    Info userDetailInfo =dao.getDetailsInfo(new Integer(userId));   //For Product_Seperation_BL By Amar Singh.
                    //taking care if record not found
                    if (userDetailInfo != null) {
                        timezone =
                                (String) userDetailInfo.getString(
                                FieldNames.TIMEZONE);
                        //setting timezone into map so that for the further use
                        if (timezone != null) {
                        	synchronized (currentMap) {
                        		currentMap.put(userId, timezone);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception while getting timezone", e);
        }
        return timezone;
        
    }
    
}
