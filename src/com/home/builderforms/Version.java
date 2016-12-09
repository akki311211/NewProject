/**
 * @author abhishek gupta
 * @(#)Version.java	2013/04/04
 * Licensed Materials - Property of FranConnect
 * Copyright FranConnect Inc. 2013  All Rights Reserved
 */
package com.home.builderforms;

import java.util.StringTokenizer;

//import com.appnetix.app.control.web.ParamResolver;

public class Version {
	private static enum VersionConstant {
		VERSION_MIN(1), 
		VERSION_MAX(9), 
		VERSION_REL(0), 
		VER_MIN_DIGIT(2), 
		VER_MAX_DIGIT(4), 
		VER_NEXT_REL(1);
		private int value;
		private VersionConstant(int value) { this.value = value; }
	};
	
    public static final String  PROJECT_NAME = "FC ";
    public static final String  VERSION_INT  = "Initial v. ";
    public static final String  BUILD		 = "0.0";
    public static final String  EMPTY		 = "";
    public static final String  DOT			 = ".";
    public static final int  ZERO	 		 = 0;
    
  /*  public static String  FULL = PROJECT_NAME;*/

    
   /* public static String asString () {
    	String verStr = String.valueOf(getInitialVersionNum());
    	ParamResolver.getResolver().put("FULL", PROJECT_NAME);
    	String FULL = (String)ParamResolver.getResolver().get("FULL");
    	for(int i=0;i<getVerDigitMin()-1;i++) 
    		verStr = verStr + DOT + String.valueOf(getReleaseVersionNum());
    	FULL = FULL + VERSION_INT + verStr;
    	verStr = String.valueOf(getInitialVersionNum());
    	for(int i=0;i<getVerDigitMax()-1;i++) 
   			verStr = verStr + DOT + String.valueOf(getReleaseVersionNum());
    	FULL = FULL + " To " + verStr;

    	return FULL;
    }*/
    
    public static void main (String[] args) {
        //System.out.println((String)ParamResolver.getResolver().get("FULL"));
    }
    /**
     * Default 2 num version series generated i.e. 1.0.
     * @return
     */
    public static String getVersion()
    {
    	return getVersion(getVerDigitMin());  
    }
    
    /**
     * This will allow version format upto 4 digit if not configured through Program 
     * @param version
     * @param format
     * @return
     */
    public static String getVersion(int formatDidit) {
    	String verStr = String.valueOf(getInitialVersionNum());
    	if(formatDidit > getVerDigitMax()) {
    		for(int i=0;i<getVerDigitMax()-1;i++) 
    			verStr = verStr + DOT + String.valueOf(getReleaseVersionNum());
    	} else if(formatDidit < getVerDigitMin()) {
        		for(int i=0;i<getVerDigitMin()-1;i++) 
        			verStr = verStr + DOT + String.valueOf(getReleaseVersionNum());
    	} else {
    		for(int i=0;i<formatDidit-1;i++) 
    			verStr = verStr + DOT + String.valueOf(getReleaseVersionNum());
    	}
    	return verStr;
    }
    /**
     * Generate new version through current version num as parameter.
     * This will first check for version which is valid for current used versioning format.
     * Only valid Version will allowed for getting new version series.
     * @param version
     * @return
     */
    public static String getNextVersion(String version) {
    	int[] verTokens;
    	
    	if(version == null || EMPTY.equals(version) || version.indexOf(DOT) == -1) {
    		return EMPTY;
    	}

    	StringTokenizer stn = new StringTokenizer(version.trim(), DOT);
    	if(stn.countTokens() < getVerDigitMin() || stn.countTokens() > getVerDigitMax()) {
    		return EMPTY;
    	}
    	verTokens = new int[stn.countTokens()];
    	int i = 0;
    	while(stn.hasMoreTokens()) {
    		try {
    			verTokens[i] = Integer.parseInt(stn.nextToken());
    		} catch(Exception e) {
    			verTokens[i] = getReleaseVersionNum();
    		}
    		i++;
    	}
    	if(getInitialVersionNum() > verTokens[ZERO] || getMajorVersionNum() < verTokens[ZERO]) {
    		return EMPTY;
    	}
    	for(int vernum=1;vernum<verTokens.length;vernum++) {
    		if(verTokens[vernum] < getReleaseVersionNum() || verTokens[vernum] > getMajorVersionNum()) {
    			return EMPTY;	
    		}
    	}
    	return getLaunchedVersion(verTokens);  
    }
    /**
     * This will launch new version number through called version series.
     * Also care for max allowed series credential so that version num not exeeded the limit as defined.
     * @param versionTokens
     * @return
     */
    private static String getLaunchedVersion(int[] versionTokens) {
    	boolean flag = false;
    	String verVal = "";
    	for(int i=versionTokens.length-1;i>0;i--) {
    		if(i == versionTokens.length-1) {
    			if(versionTokens[versionTokens.length-1]+getNextReleaseNum() > getMajorVersionNum()) { 
    				versionTokens[i] = getReleaseVersionNum();
    				versionTokens[i-1] = versionTokens[i-1] + getNextReleaseNum();
    			} else {
    				versionTokens[i] = versionTokens[i] + getNextReleaseNum();
    			}
    		} else {
    			if(versionTokens[i] > getMajorVersionNum()) {
    				versionTokens[i] = getReleaseVersionNum();
    				versionTokens[i-1] = versionTokens[i-1] + getNextReleaseNum();
    			}
    		}
    		verVal = DOT + versionTokens[i] + verVal;
    	}
    	verVal = versionTokens[0] + verVal;
    	
        return verVal;
    }
    /**
     * This will check the valid version number in series. 
     * Also check for version which is valid for current used versioning format only.
     * @param version
     * @return
     */
    public static boolean validVersion(String version) {
    	int[] verTokens;
    	
    	if(version == null || EMPTY.equals(version) || version.indexOf(DOT) == -1) {
    		return false;
    	}

    	StringTokenizer stn = new StringTokenizer(version.trim(), DOT);
    	if(stn.countTokens() < getVerDigitMin() || stn.countTokens() > getVerDigitMax()) {
    		return false;
    	}
    	verTokens = new int[stn.countTokens()];
    	int i = 0;
    	while(stn.hasMoreTokens()) {
    		try {
    			verTokens[i] = Integer.parseInt(stn.nextToken());
    		} catch(Exception e) {
    			verTokens[i] = getReleaseVersionNum();
    		}
    		i++;
    	}
    	if(getInitialVersionNum() > verTokens[ZERO] || getMajorVersionNum() < verTokens[ZERO]) {
    		return false;
    	}
    	for(int vernum=1;vernum<verTokens.length;vernum++) {
    		if(verTokens[vernum] < getReleaseVersionNum() || verTokens[vernum] > getMajorVersionNum()) {
    			return false;	
    		}
    	}
    	return true;  
    }
    
    private static int getInitialVersionNum() {
        return VersionConstant.VERSION_MIN.value;
    } 
    public static void setInitialVersionNum(int val) {
        VersionConstant.VERSION_MIN.value = val;
    } 
    
    private static int getMajorVersionNum() {
      return VersionConstant.VERSION_MAX.value;
      
    }  
    public static void setMajorVersionNum(int val) {
        VersionConstant.VERSION_MAX.value = val;
    } 
    
    private static int getReleaseVersionNum() {
      return VersionConstant.VERSION_REL.value;
    }
    public static void setReleaseVersionNum(int val) {
        VersionConstant.VERSION_REL.value = val;
    } 
    
    private static int getNextReleaseNum() {
        return VersionConstant.VER_NEXT_REL.value;
    }
    public static void setNextReleaseNum(int val) {
        VersionConstant.VER_NEXT_REL.value = val;
    } 
    
    private static int getVerDigitMax() {
        return VersionConstant.VER_MAX_DIGIT.value;
    }
    public static void setVerDigitMax(int val) {
        VersionConstant.VER_MAX_DIGIT.value = val;
    } 
    private static int getVerDigitMin() {
        return VersionConstant.VER_MIN_DIGIT.value;
    }

    public static int getDevelopmentVersionNum() { 
    	try {    
    		if ((new String("")).length() == VersionConstant.VERSION_REL.value)
    			return VersionConstant.VERSION_REL.value;
    		else  
    			return Integer.parseInt("");
    	} catch (NumberFormatException nfe) {
    		return VersionConstant.VERSION_REL.value;
    	}    
    } 
    
    public static int getMaintenanceVersionNum() {
      return VersionConstant.VERSION_REL.value;
    }
}
