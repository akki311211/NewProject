/*
 * LanguageUtil.java
 * @author  Parveen Vashishth
 * @Date   7/13/2009
 * For internationalization of Franconnect, 

	Needs to be taken care of 2 things while doing internationalization
   a) Minimal changes in exisiting code
   b) When releasing for US clients(internationalizationImplemented is off), then avoiding 
        overhead of getting converted Strings.

   -	Display Names in Action Files will be handled at Taglibs. So if internationalizationImplemented is off no conversion will be done
   -   All strings/text used in jsp will be replaced with <%=LanguageUtil.getString("text/string")%>, So if internationalizationImplemented is off same key will be return as value.
   -   For Js alerts need to use FCI.Alerts()/Messages();

Modified By Ram Avtar for balnk displayName and remove ":" from key
Modified By Shraddha Seth to enable tenant-wise i18n configurations.
-------------------------------------------------------------------------------------------------------------
Version No.	     			Date				By				Against		 		Function Changed         	 Comments
-------------------------------------------------------------------------------------------------------------
P_E_i18n            		27 Apr 2009   	 Ram Avtar        	Enhancement   	 	getString overloaded    	Internationalization based on UserLanguage
E_REQUEST_SESSION   		4 JUN 2010     	Bhoopal Reddy    	Enhancement    		getString overloaded   	 retrieve language based on request and session
E_userlanguageruntime   	11 JUN 2010     Bhoopal Reddy    	Enhancement   		getString         		 retrieve userlanguage dynamically at runtime
P_BUG_7245              	04 APRIL 2012   Megha Chitkara    	BUG_7425            created locale for spanish language
P_FORUM_B_12560				10 Sep 2012		Dravit Gupta		iternationalization of date was not working in forums
BBEH_CODE_OPTIMIZATION  	06 jun 2013	  	Rohit Jain   		Optimization        Code optimization
MT_PHASE_III_I18n_CHANGES	05 May 2015		Shraddha Seth		Enhancement			Methods Added/Removed/Modified	To Enable MultiTenant i18n Configurations.
P_E_Mobile_SPA_92                   Aug 24, 2015                    Desh Deepak Verma               getLanguageContentMobileSPA() created    To support Single Page application, all JS must be loaded on First request. 
------------------------------------------------------------------------------------------------------------------------- 
 */

package com.home.builderforms;

import java.util.Locale;

import com.appnetix.app.portal.admin.UTF8Properties;
import com.appnetix.app.portal.i18n.Language;
import com.appnetix.app.portal.i18n.LanguageHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.home.builderforms.i18n.UserLanguageLocal;
import java.util.List;
import com.home.builderforms.Constants;
import com.home.builderforms.BaseUtils;
public class LanguageUtil {

	private static boolean  i18n;
	private static String userLanguageKey = "userLanguage";
	private static String defalutLanguage = "en";
	

	public static String getString(String key)
    {
		if("Access Logs".equals(key) && (!MultiTenancyUtil.getTenantConstants().WS_MODULES_SYNC || (MultiTenancyUtil.getTenantConstants().WS_MODULES_SYNC && !ModuleUtil.zcubatorImplemented()))){
			key="Access Control";
		}
		if(isI18nImplemented() && StringUtil.isValid(key)){
			return getString(key, UserLanguageLocal.getUserLanguage());
		}else{
			return key;
		}
	}
	
	public static String getString(String key,String language){
		if("Access Logs".equals(key) && (!MultiTenancyUtil.getTenantConstants().WS_MODULES_SYNC || (MultiTenancyUtil.getTenantConstants().WS_MODULES_SYNC && !ModuleUtil.zcubatorImplemented()))){
			key="Access Control";
		}
		return getString(key, language,null);	//MT_PHASE_III_I18n_CHANGES //Branding Changes
	}
	//Branding Changes starts
	public static String getString(String key,String language,String moduleKeyValName){
		String returnValue=key;
		if(isI18nImplemented())
		{
			if(language==null){
				language = UserLanguageLocal.getUserLanguage();
			}
			returnValue=Language.getString(returnValue, language);	//MT_PHASE_III_I18n_CHANGES
		}
		if(moduleKeyValName!=null){
			if(Constants.KEYVAL_FINANCIALS.equals(moduleKeyValName)){
				returnValue = returnValue.replaceAll("(?i) financial "," "+BaseUtils.getMenuDisplayName(null,moduleKeyValName,Constants.moduleNameMap.get(moduleKeyValName))+" ");
			}else if(Constants.KEYVAL_MARKETING_ASSETS.equals(moduleKeyValName)){
				returnValue = returnValue.replaceAll("(?i)Marketing Assets",BaseUtils.getMenuDisplayName(null,Constants.KEYVAL_INTRANET,Constants.moduleNameMap.get(Constants.KEYVAL_INTRANET)));
			}else{
				returnValue = returnValue.replaceAll("(?i)"+Constants.moduleNameMap.get(moduleKeyValName),BaseUtils.getMenuDisplayName(null,moduleKeyValName,Constants.moduleNameMap.get(moduleKeyValName)));	
			}
		}
		return returnValue;
	}
	//Branding Changes ends
	/**
	 * New Method to get userLanguage for JS Functions
	 * param language
	 * return js key for js File to include
	 */
	public static String getUserLangForJs(String language){
            //Added by Narotam on 06/05/2011 for bug 74009 starts
            if(language==null || "null".equals(language))
            {
                language="en";
            }
            //Added by Narotam on 06/05/2011 for bug 74009 ends

		return language;
	}


	//E_REQUEST_SESSION
	public static String getString(String key, HttpServletRequest request){
		return getString(key, request.getSession());
	}

	public static String getString(String key, HttpSession session){

		return getString(key,(String ) session.getAttribute("userLanguage"));
	}

	public static String getLanguage( HttpServletRequest request){
		return getLanguage( request.getSession());
	}

	public static String getLanguage(HttpSession session){
		return  (String )session.getAttribute("userLanguage");
	}

	public static String getStaticString() {
		return "";
	}

	public static void  updateStaticKeys(){


	}

	public static String getUserLanguageKey() {
		return userLanguageKey;
	}

        /**
         * This method returns the UserLanguage from UserLocalLanguage
         * @return Language as a string
         */
        public static String getUserLanguage(){
        	return getUserLanguage(null);
        }
//P_FORUM_B_12560 starts
        /**
         * This is an overloaded method of the above method called in case of Forums to get user Language
         * This method returns the UserLanguage from UserLocalLanguage
         * @return Language as a string
         * @author Dravit Gupta
         */
        public static String getUserLanguage(String userNo){
        	if(userNo==null)
        	{
        		return UserLanguageLocal.getUserLanguage();
        	} else{
        		return UserLanguageLocal.getUserLanguage(userNo);	
        	}
            
        }
//P_FORUM_B_12560 ends
        /**
         * getUserLocale is used for returning Locale value according to the Language
         * @return Locale value 
         */
        public static Locale getUserLocale(){
             if("en".equals(getUserLanguage())){
                    return Locale.ENGLISH;
                }else if("de".equals(getUserLanguage())){
                    return Locale.GERMAN;
                }else if("fr".equals(getUserLanguage())){
                    return Locale.FRENCH;
                }else if("it".equals(getUserLanguage())){
                    return Locale.ITALIAN;
                }
                else if("pt".equals(getUserLanguage())){
                    return new Locale("pt", "PT");
                }
                else if("es".equals(getUserLanguage())){            //P_BUG_7245
                    return new Locale("es", "ES");
                }else{
                    return Locale.ENGLISH;
                }
        }
//P_FORUM_B_12560 starts        
        /**
         * This is an overloaded method of the above method, called in case of Forums to get user Language
         * getUserLocale is used for returning Locale value according to the Language
         * @return Locale value 
         * @author Dravit Gupta
         */
        public static Locale getUserLocale(String userNo){
        	if("en".equals(getUserLanguage(userNo))){
                    return Locale.ENGLISH;
                }else if("de".equals(getUserLanguage(userNo))){
                    return Locale.GERMAN;
                }else if("fr".equals(getUserLanguage(userNo))){
                    return Locale.FRENCH;
                }else if("it".equals(getUserLanguage(userNo))){
                    return Locale.ITALIAN;
                }else if("es".equals(getUserLanguage(userNo))){
                    return new Locale("es", "ES");
                }else{
                    return Locale.ENGLISH;
                }
        }
//P_FORUM_B_12560 ends        
	public static String getDefalutLanguage() {
		return defalutLanguage;
	}
	public static void setDefalutLanguage(String defalutLanguageTemp) {
		defalutLanguage = defalutLanguageTemp;
	}
	
	public static String getLangKeyValue(String key,String language){
		return	Language.getLangKeyValue(key, language);	//MT_PHASE_III_I18n_CHANGES 
	}

	public static String getLanguageContent(String lang,String menu,String tenantName){
		return	LanguageHandler.getInstance().getLanguageContent(lang, menu,tenantName);	//MT_PHASE_III_I18n_CHANGES 
	}
        
        //P_E_Mobile_SPA_92 starts By Deepak V.
        /**
         * 
         * @param lang
         * @param menu
         * @param tenantName
         * @return 
         */
        public static String getLanguageContentMobileSPA(String lang,List<String> moduleList,String tenantName){
		return	LanguageHandler.getInstance().getLanguageContentMobileSPA(lang, moduleList,tenantName);	//MT_PHASE_III_I18n_CHANGES 
	}
        //P_E_Mobile_SPA_92 ends By Deepak V.
	
    public static boolean isI18nImplemented()
    {
       return i18n && "on".equals(MultiTenancyUtil.getTenantContext().getAttribute("internationalization"));
    }

    public static void setI18n(boolean _i18n)
    {
        i18n = _i18n;
    }


}

