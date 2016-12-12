
package com.home.builderforms;

import net.htmlparser.jericho.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
/**
 *   Class for String manipulation utilities
 *
 *@author     misam
 *@created    November 9, 2001
 */
/*
--------------------------------------------------------------------------------------------------------------------
Version No.       		Date            By		  Against   Function Changed          Comments
--------------------------------------------------------------------------------------------------------------------
P_CM_B_36245   			06-30-2008    Nikhil Verma      Bug         formatName()        Remove Spaces form name.
BB_CT_REPORT_E240908	24Sep2008		YogeshT		--			--						- added a method getNameSortingQuery();
BB_E_19Jan2009			19Jan2009		YogeshT		Enh			--						-added method removeRepeatChar(..)
 P_FRANONLINE_B_52432    25-NoV-2009      Saurabh Sinha     Bug Resolved
D_CM_B_70511        10/03/11        Vishal Lodha            For bug number 70511
platpp_20110223_017 03/06/2011 Narotam Singh
BB_Dashboard_REPORT	16Jun2011		Omindra Rana		--			--						- added a method isNull(String value, String dafaultValue)
P_FS_B_5851          12/04/2012             MOHIT SHARMA               To make the intials of the last name capital
P_CT_B_25920         05/08/2013             Teena Sharma               To resolve Special characters are being replaced with other characters while exporting as excel 
P_FIM_B_26965		 16 Aug 2013			Niranjan Kumar			   removeAmpersandSpecial() added
P_FIM_B_27538		 26 Aug 2013			Niranjan Kumar			   convertToArrayListWithTrimmedData() added
P_B_CR_35358		 13 march 2014			Ramu Agrawal			   Shows the dotted title if title too large
PROVEN_MATCH_INTEGRATION	28/11/2014		Amar Singh		Ens			A third party integration with Proven Match through REST-API for lead sync.
ZCUB-20150519-148        9 july 2015       Divanshu Verma     Ehn       Convert specified characters to upper case.
ZCUB-20150728-164        18 sept 2015      Swati Middha                 Adding Store Details in Onboarding Screen
 ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 //ZCUB-20151124-201     4 Desc 2015      Divanshu Verma                Double Value Check including Zero
---------------------------------------------------------------------------------------------------------------------------------------------------
*/
public class StringUtil extends Object implements java.io.Serializable {

	public static boolean boolValue(String string){
		if(badString(string)){
			return false;
		}
		if(string.equalsIgnoreCase("true")){
			return true;
		}
		return false;
	}

    /**
     * This method will return the string after replacing the charcter '%j' in the input string
     *   with the appropriate array character at index j   
     * @param str : str object
     * @param replace : replace object 
     * @return String : replacedString 
     */
    public static String replace(String str, String[] replace) {
        int position = 0;
        StringBuffer result = new StringBuffer(str);
        try {
            for (int i = 0; i < replace.length; i++) {
                int j = i + 1;
                String pattern = "%" + j;
                position = str.indexOf(pattern);
                if (position == -1) {
                    return str;
                }

                result.replace(position, position + 2, replace[i]);
                str = result.toString();
            }
        } catch (Exception ex) {
            return new String();
        }
        return str;
    }


   /**
    * This Method will replace the substring in the input string with the String provided as third parameter to this method
    * @param str : str object 
    * @param subString :  subString object
    * @param strReplace :  strReplace object
    * @return String : replace String
    */
    public static String replaceSubString(String str, String subString, String strReplace) {
        if (str == null || subString == null) {
            return str;
        }
        if (strReplace == null) {
            strReplace = "";
        }
        int replaceLength = subString.length();
        if (replaceLength < 1) {
            return str;
        }

        StringBuffer sb		= new StringBuffer(str);
		int replacedLength	= strReplace.length();
		int startIndex		= 0;
        try {
            for (; ; ) {
                str			= sb.toString();
                startIndex	= str.indexOf(subString, startIndex);

                if (startIndex == -1) {
                    break;
                }
                sb.replace(startIndex, startIndex + replaceLength, strReplace);
				startIndex	= startIndex + replacedLength;
				//System.out.println(sb.toString());
            }
        } catch (Exception e) {
            return str;
        }
        str = sb.toString();
        return str;
    }

	
    /**
     * This Method will replace the all the substring in the input string 
     * where the substrings are present in the hashmap 
     * @param str : str object 
     * @param map :  map object
     * @return String : replace String
     */
    public static String replaceSubString(String str, HashMap map) {
        if (str == null || str.trim().equals("")) {
            return str;
        }
        Iterator it = map.keySet().iterator();
        while (it != null && it.hasNext()) {
            String strReplace = (String) it.next();
            String strReplaceWith
                     = (String) map.get(strReplace);
            str = replaceSubString(str, strReplace, strReplaceWith);
        }
        return str;
    }

   /**
    * This method will return the integer value of the input string
    * @param s : s object 
    * @return boolean 
    */
    public static boolean isInt(String s) {
        boolean ret = false;
        if (s == null) {
            return ret;
        }
        try {
            Integer.parseInt(s);
            ret = true;
        } catch (NumberFormatException ne) {

        }
        return ret;
    }

   
    /**
     * This method will return the true if string is valid otherwise return false 
     * @param s :s object
     * @return boolean : true if string is valid
     * 					 false if string is not valid
     */
    public static boolean isValid(String s) {
        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null"));
    }
    
    public static boolean isValidNew(String s) {
        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1"));
    }
    
    /**
     * return dafaultValue if value is not valid 
     * @param value
     * @param dafaultValue
     * @return String
     */
    public static String isNull(String value, String dafaultValue) {
        if (isValid(value))
        	return value;
        return dafaultValue;
    }        

    //P_FS_B_5851
    /**
     * This method will return string with its intial capital
     * @param str : str is the given String 
     */  
    
    public static String initialCap(String str)
    {
		int index;
		String tmpStr="";
		String tmpChar;
		String preString;
		String postString;
		int strLen;
		tmpStr = str.toLowerCase();
		strLen = tmpStr.length();
		if (strLen > 0)  {
			for (index = 0; index < strLen; index++)  {
				if (index == 0)  {
				tmpChar = tmpStr.substring(0,1).toUpperCase();
				postString = tmpStr.substring(1,strLen);
				tmpStr = tmpChar + postString;
				}
				else {
					tmpChar = tmpStr.substring(index, index+1);
					if (" ".equals(tmpChar) || "-".equals(tmpChar))  {
						tmpChar = tmpStr.substring(index+1, index+2).toUpperCase();
						preString = tmpStr.substring(0, index+1);
						postString = tmpStr.substring(index+2,strLen);
						tmpStr = preString + tmpChar + postString;
					}
				}
		   	}
		}
		return tmpStr;
    }
    /**This method will return string n times comma seperated
     * BB-20150525-360
     * @author Akash Kumar
     * @param s
     * @param n
     * @param piiCheckMap
     * @param dbFields
     * @return String 
     */
    public static String toNtimesWithEncryption(String s, int n, Map<String, Boolean> piiCheckMap, String[] dbFields) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                sb.append(",");
            }
            if(piiCheckMap.get(dbFields[i])) {
                sb.append(" AES_ENCRYPT("+s+",'pvm@e20') ");
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

/**
 * This method will return string n times comma seperated
 * @param s : s object 
 * @param n : integer n
 * @return String 
 */
    public static String toNtimes(String s, int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }
  /**
   * This metthod will return string as a file
   * @param file : file object 
   * @return String 
   * @throws IOException
   */
  public static String toText(File file) throws IOException {
//      PH_Intranet_Messages_Optimized Start
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new FileReader(file));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (Exception e) {
                }
            }
            return sb.toString();
        }
//        PH_Intranet_Messages_Optimized End
    }
/**
 * This method will return array of values where each element in array is  break up of string by seperater comma  
 * @param str : str object
 * @return Array of value 
 */
	public static String[] toArray(String str){
		return convertToArray(str, ",");
	}
	/**
	 * P_B_Codebase_22112013
	 * @param str
	 * @return comma separated string
	 */
	public static String arrayToString(String str[]){
		if(str!=null){
			String returnStr =  ArrayUtils.toString(str);
			if(returnStr!=null && returnStr.length()>=2){
				returnStr = returnStr.substring(1, returnStr.length()-1);
			}
			return returnStr;
		}else{
			return null;
		}
	}
	/**
	 * This method will return array of values where each element in array is  break up of string by seperater semicolon
	 * @param str : str object
	 * @return Array
	 */
	public static String[] toArrayFromSemiColonSeparated(String str){
		return  convertToArray(str, ";");
	}
	/**
	 * This method will return arrayList of values where each element in arrayList is  break up of string by seperater comma
	 * @param str : str object 
	 * @return Arraylist
	 */
	public static ArrayList toArrayList(String str){
		return convertToArrayList(str, ",");
	}
	/**
	 * This method will return arrayList of values where each element in arrayList is  break up of string by seperater as input 
	 * @param str : str object 
	 * @param separator : seperator object
	 * @return Arraylist
	 */
    public static ArrayList convertToArrayList(String str, String separator) {
        if (str == null || separator == null) {
            return new ArrayList();
        }

		ArrayList list	=  new ArrayList();
		str				= str.trim();
		if(!str.endsWith(separator)){
			str += separator;
		}
		int index = str.indexOf(separator);
		int start = 0;
		while(index != -1){
			String substring = str.substring(start, index);
			if(!substring.trim().equals("")){
				list.add(substring);
			}
			start = start + substring.length() + separator.length();
			index = str.indexOf(separator, start);
		}
		list.trimToSize();
        return list;
    }
    //P_FIM_B_27538 : starts
    public static ArrayList convertToArrayListWithTrimmedData(String str, String separator) {
        if (str == null || separator == null) {
            return new ArrayList();
        }

		ArrayList list	=  new ArrayList();
		str				= str.trim();
		if(!str.endsWith(separator)){
			str += separator;
		}
		int index = str.indexOf(separator);
		int start = 0;
		while(index != -1){
			String substring = str.substring(start, index);
			if(!substring.trim().equals("")){
				list.add(substring.trim());
			}
			start = start + substring.length() + separator.length();
			index = str.indexOf(separator, start);
		}
		list.trimToSize();
        return list;
    }
 //P_FIM_B_27538 : ends
    
    /**
     * This Method will return the comma seperated String
     * @param elems : elems array
     * @return String 
     */
	public static String toCommaSeparated2(Object[] elems)
	{
		if (elems==null || elems.length==0)
		return "";
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append(", ");
			if(elems[i]==null)
				sb.append("null");
			else
				sb.append(elems[i].toString());
		}
		return sb.toString();
	}
	/**
	 * This Method will return the comma seperated String
	 * @param al : a1 object
	 * @return String
	 */
	public static String toCommaSeparated2(Collection al)
	{
		return toCommaSeparated2(al.toArray());
	}

	/**
	 * This method will return the String as a file
	 * @param fileName
	 * @return String
	 */
	public static String read(File fileName) {
			StringBuffer message = new StringBuffer();
			try {
				Debug.println(" Reading file : " + fileName, -1);
				FileReader fileReader = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fileReader);
				try{
					String messageTemp	= null;
					while ((messageTemp = br.readLine()) != null) {
						message.append(messageTemp);
						message.append("\n");
					}
				}finally{
					try{
						fileReader.close();
					}catch(Exception e){
						Debug.println("StringUtil: Exception in closing file reader " + fileName + e);
					}
					try{
						br.close();
					}catch(Exception e){
						Debug.println("StringUtil: Exception in closing buffered reader " + fileName + e);
					}
				}
			} catch (IOException ioe) {
				Debug.println("StringUtil: readingFile " + fileName + " " + ioe, 2);
			}
			return message.toString();
    }
/**
 * This method wll return a boolean value true if input string is null otherwise return false
 * @param str
 * @return boolean 
 */
    public static boolean badString(String str){
			if(str == null || ("").equals(str.trim()) || ("null").equals(str.trim())){
				return true;
			}
			return false;
	}

/**
 * 
 * @param array
 * @return
 */
 public static String[] convertNull(String[] array) {
		if (array == null) {
			return null;
		}


		for (int i = 0; i < array.length; i++) {
			if ("null".equals(array[i])) {
				array[i] = null;
			}
		}
		return array;

	}
/**
 * this method will return the string in which  null  is replace by blank
 * @param string : string object
 * @return String
 */
	public static String convertNull(String string) {
		if (string == null) {
			return "";
		}


		if ("null".equals(string)) {
			string = "";
		}
		return string;

	}
/**
 * This method will return the String in which  null  is replace by input String 
 * @param string : string object 
 * @param replaceWith : replaceWith object
 * @return String
 */
	public static String convertNull(String string, String replaceWith) {
		if (string == null) {
			return replaceWith;
		}


		if ("null".equals(string)) {
			string = replaceWith;
		}
		return string;

	}
/**
 * This method will return array of values where each element in array is  break up of string by seperater as input 
 * @param str : str object
 * @param separator : seperator object
 * @return Array of elements
 */
    
    /**ZCUB-20140401-032 :starts
     * Added By Akash Jain, This method will return the String as name in which first letter of first name and last name is in caps leaving rest of the letters as it is.
     * @param fName :fName object
     * @param lName :lName object
     * @return String
     */
     public static String formatNameOnFirstLetter(String fName, String lName) {

         StringBuffer nameBuffer = new StringBuffer();
         StringTokenizer tokenizer = null;
         try {
             if (!badString(fName)) {
                 tokenizer = new StringTokenizer(fName, " ");
                 while (tokenizer.hasMoreTokens()) {
                     String sWord = tokenizer.nextToken();
                     nameBuffer.append(sWord.substring(0, 1).toUpperCase() + sWord.substring(1) + " ");
                 }
             }
             
             if (!badString(lName)) {
                 tokenizer = new StringTokenizer(lName, " ");
                 while (tokenizer.hasMoreTokens()) {
                     String sWord = tokenizer.nextToken();
                     nameBuffer.append(sWord.substring(0, 1).toUpperCase() + sWord.substring(1) + " ");
                 }
             }
         } catch (Exception e) {
         }
         return nameBuffer.toString().trim();
     }
     //ZCUB-20140401-032 :ends
    public static String[] convertToArray(String str, String separator) {
        if (str == null || separator == null) {
            return new String[0];
        }

		ArrayList list	=  new ArrayList();
		str				= str.trim();
		if(!str.endsWith(separator)){
			str += separator;
		}
		int index = str.indexOf(separator);
		int start = 0;
		while(index != -1){
			String substring = str.substring(start, index);
			if(!substring.trim().equals("")){
				list.add(substring);
			}
			start = start + substring.length() + separator.length();
			index = str.indexOf(separator, start);
		}
		list.trimToSize();
		String[] array	= new String[list.size()];
		for(int i=0;i < array.length;i++){
			array[i]	= (String) list.get(i);
		}
        return array;
    }
    
    /**
     * This method validates comma separated multiple email id with the common checks like @ and . if not found between the separeters then it removed it. 
     * @param str : str object
     * @param separator : seperator object
     * 	@return Array of elements
     */
    public static String[] convertToValidEmailArray(String str, String separator) {
        if (str == null || separator == null) {
            return new String[0];
        }

		ArrayList list	=  new ArrayList();
		str				= str.trim();
		if(!str.endsWith(separator)){
			str += separator;
		}
		int index = str.indexOf(separator);
		int start = 0;
		while(index != -1){
			String substring = str.substring(start, index);
			if(org.apache.commons.validator.GenericValidator.isEmail(substring)){
			//if(!substring.trim().equals("") && substring.indexOf("@")!=-1 && substring.indexOf(".")!=-1){
				list.add(substring);
			}
			start = start + substring.length() + separator.length();
			index = str.indexOf(separator, start);
		}
		list.trimToSize();
		String[] array	= new String[list.size()];
		for(int i=0;i < array.length;i++){
			array[i]	= (String) list.get(i);
		}
        return array;
    }
    
    public static String[] convertToValidEmailArray1(String str, String separator) {
        if (str == null || separator == null) {
            return new String[0];
        }

		ArrayList list	=  new ArrayList();
		str				= str.trim();
		if(!str.endsWith(separator)){
			str += separator;
		}
		int index = str.indexOf(separator);
		int start = 0;
		while(index != -1){
			String substring = str.substring(start, index);
			if(isEmail(substring)){
			//if(!substring.trim().equals("") && substring.indexOf("@")!=-1 && substring.indexOf(".")!=-1){
				list.add(substring);
			}
			start = start + substring.length() + separator.length();
			index = str.indexOf(separator, start);
		}
		list.trimToSize();
		String[] array	= new String[list.size()];
		for(int i=0;i < array.length;i++){
			array[i]	= (String) list.get(i);
		}
        return array;
    }
    
    public static boolean isEmail(String email){
    	boolean validFlag=false;
    	if(email!=null && email.contains("@") &&  email.contains(".") && email.lastIndexOf(".")>email.indexOf("@"))
    		validFlag=true;
        return validFlag;
    }
/**
 * This method will  return the String with comma seperated single quotes
 * @param elems : elems object
 * @return String
 */
    public static String toCommaSeparatedWithSQuotes(Object[] elems){
			if(elems == null){
				return "";
			}
			StringBuffer sb = new StringBuffer();
			for (int i=0;i<elems.length;i++)
			{
				if(elems[i] == null || elems[i].equals("")){
					continue;
				}
				if (sb.length()>0) sb.append(", ");
				sb.append("'" + elems[i].toString() + "'");
			}
			return sb.toString();
		}
    /**
     * This Method will return the comma seperated String
     * @param elems : elems array
     * @return String 
     */
    public static String toCommaSeparated(Object[] elems)
		{
			if(elems==null || elems.length==0){
				return "";
			}
			StringBuffer sb = new StringBuffer();
			for (int i=0;i<elems.length;i++)
			{
				if (i!=0) sb.append(", ");
				if(elems[i] == null){
					continue;
				}
				sb.append(elems[i].toString());
			}
			return sb.toString();
		}
    public static String toCommaSeparatedSpaceLess(Object[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append(",");
			if(elems[i] == null){
				continue;
			}
			sb.append(elems[i].toString());
		}
		return sb.toString();
	}
    
    /**
     * Sourabh MultiSelect
     * Convert Array to colon seperated string
     * @param elems
     * @return
     */
    public static String toColonSeparatedSpaceLess(Object[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append(" : ");
			if(elems[i] == null){
				continue;
			}
			sb.append(elems[i].toString());
		}
		return sb.toString();
	}
    
    /**
     * Sourabh 
     * Convert Array to Specific Pattern seperated string
     * @param elems
     * @return
     */
    public static String toSpecificPatternSeparatedSpaceLess(Object[] elems, String pattern)
	{
		if(elems==null || elems.length==0 || !isValidNew(pattern)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append(pattern);
			if(elems[i] == null){
				continue;
			}
			sb.append(elems[i].toString());
		}
		return sb.toString();
	}
    
    /**
     * This Method will return the comma seperated String with quotes like 'a','b'
     * @param elems : elems array
     * @return String 
     */
    public static String toCommaSeparatedWithQuotes(Object[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer("'");
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append("','");
			if(elems[i] == null){
				continue;
			}
			sb.append(elems[i].toString());
		}
		sb.append("'");
		return sb.toString();
	}
    
    /**
     * This Method will return the comma seperated String
     * @param elems : elems array
     * @return String 
     */
	public static String toCommaSeparated3(Object[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer(",");
		for (int i=0;i<elems.length;i++)
		{
			if(elems[i] == null){
				continue;
			}
			sb.append(elems[i].toString()+",");
		}
		return sb.toString();
	}
	
	public static String[] getArrayWithEquals(String[] values)
		{
			String[] ret = new String[values.length];
			for (int i=0;i<values.length;i++)
			{
				ret[i]	=	values[i] + " = ? ";
			}
			return ret;
	}
	 // BB-20150525-360 starts
	public static String[] getArrayWithEqualsWithEncryption(String[] values,Map<String, Boolean> piiCheckMap)
	{
		String[] ret = new String[values.length];
		for (int i=0;i<values.length;i++)
		{
			if(piiCheckMap.get(values[i])){
				ret[i]	=	values[i] + " = AES_ENCRYPT(?,'pvm@e20') ";
			}else{
			ret[i]	=	values[i] + " = ? ";
			}
		}
		return ret;
} // BB-20150525-360 ends
/**
 * This method will return String in which substring replaced with the input replace string from the specified index 
 * @param str : str object
 * @param subString : subString object
 * @param strReplace : strReplace object 
 * @param nIndex : nIndex integer 
 * @return String 
 */
	public static String replaceSubString(String str, String subString, String strReplace, int nIndex){
			 if (str == null || subString == null) {
	            return str;
	        }
	        if (strReplace == null) {
	            strReplace = "";
	        }
	        int replaceLength = subString.length();
	        if (replaceLength < 1) {
	            return str;
	        }

	        StringBuffer sb		= new StringBuffer(str);
			int replacedLength	= strReplace.length();
			int startIndex		= 0;
			int counter			= 1;
	        try {
	            for (; ; ) {
	                str			= sb.toString();
	                startIndex	= str.indexOf(subString, startIndex);

	                if (startIndex == -1) {
	                    break;
	                }
					if(counter == nIndex){
						Debug.println("Replacing at " + counter);
						sb.replace(startIndex, startIndex + replaceLength, strReplace);
						return sb.toString();
					}else{
						startIndex	= startIndex + replaceLength;
						counter++;
					}
					//System.out.println(sb.toString());
	            }
	        } catch (Exception e) {
	            return str;
	        }
	        str = sb.toString();
	        return str;
	}
/**
 * This method will return the arraylist as input is an array 
 * @param array : array object
 * @return Arraylist
 */
	public static ArrayList convertToArrayList(String[] array){
		ArrayList arrayList			= new ArrayList();
		if(array == null){
			return arrayList;
		}
		for(int i=0;i < array.length;i++){
			arrayList.add(array[i]);
		}
		return arrayList;
	}
	
/**
 * This method will return the arraylist of  valid String 
 * @param array : array object
 * @return Arraylist
 * Added by NishantP
*/
	public static ArrayList convertToArrayList1(String[] array){
		ArrayList arrayList			= new ArrayList();
		if(array == null){
			return arrayList;
		}
		for(int i=0;i < array.length;i++){
			if(isValid(array[i]))
				arrayList.add(array[i]);
		}
		return arrayList;
	}
	
	
	public static ArrayList convertToArrayListForMenu(String[] array){
		ArrayList arrayList			= new ArrayList();
		if(array == null){
			return arrayList;
		}
		for(int i=0;i < array.length;i++){
			if(isValid(array[i]) && !"''".equals(array[i]))
				arrayList.add(array[i]);
		}
		return arrayList;
	}
	
    /**
     *  Adds a feature to the Arrays attribute of the StringUtil class
     *
     *@param  array1  The feature to be added to the Arrays attribute
     *@param  array2  The feature to be added to the Arrays attribute
     *@return          Return Value
     */
    public static String[] addArrays(String[] array1, String[] array2) {
        if (array1 == null) {
            return array2;
        }
        if (array2 == null) {
            return array1;
        }
        String[] finalArray = new String[0];
        try {
            ArrayList list = new ArrayList();
            for (int i = 0; i < array1.length; i++) {
                list.add(array1[i]);
            }
            for (int i = 0; i < array2.length; i++) {
                list.add(array2[i]);
            }
            list.trimToSize();
            finalArray = new String[list.size()];
            for (int i = 0; i < finalArray.length; i++) {
                finalArray[i] = (String) list.get(i);
            }
        } catch (Exception e) {
            Debug.println("Exception in StringUtil: addArrays() " + e, 2);
        }
        return finalArray;
    }


    /**
     * This method will return the array removing of duplicate values
     * @param array : array object
     * @param toRemove : toRemove object
     * @return Array
     */
    public static String[] remove(String[] array, String toRemove) {
        String[] finalArray = null;
        if(array == null || array.length == 0) {
            return finalArray;
        }
        if(toRemove == null) {
            return array;
        }
        array = removeDuplicate(array);
        if(array == null || array.length == 0) {
			return finalArray;
        }
        try{
			ArrayList list	= new ArrayList();
            for(int i=0; i < array.length; i++){
				if(!array[i].equalsIgnoreCase(toRemove)){
					list.add(array[i]);
				}
            }
			list.trimToSize();
			finalArray		= new String[list.size()];
			for(int i=0;i < finalArray.length;i++){
				finalArray[i]
							= (String) list.get(i);
			}
        }catch (Exception e) {
            Debug.println("Exception in StringUtil: remove() " + e, 2);
        }
        return finalArray;
    }

    /**
     * This method will return the array of string which are there in input array1 but not in array2
     * @param array :array object
     * @param toRemove : toRemove object
     * @return Array
     */
    public static String[] remove(String[] array, String[] toRemove) {
        String[] finalArray = null;
        if(array == null || array.length == 0) {
            return finalArray;
        }
        if(toRemove == null || toRemove.length == 0){
            return array;
        }
        array = removeDuplicate(array);
        if(array == null || array.length == 0) {
			return finalArray;
        }
		ArrayList except	= new ArrayList();
		for(int i=0; i < toRemove.length; i++){
			except.add(toRemove[i].toLowerCase());
		}

        try{
			ArrayList list	= new ArrayList();
            for(int i=0; i < array.length; i++){
				if(!except.contains(array[i].toLowerCase())){
					list.add(array[i]);
				}
            }
			list.trimToSize();
			finalArray		= new String[list.size()];
			for(int i=0;i < finalArray.length;i++){
				finalArray[i]
							= (String) list.get(i);
			}
        }catch (Exception e) {
            Debug.println("Exception in StringUtil: remove() " + e, 2);
        }
        return finalArray;
    }

    /**
     *  This method will return the array of string which is subset of input array1 of string and array 2 of string.
     * @param array : array object
     * @param toRetain : toRetain object
     * @return Array
     */   
        public static String[] retain(String[] array, String[] toRetain) {
        String[] finalArray = null;
        if(array == null || array.length == 0) {
            return finalArray;
        }
        if(toRetain == null || toRetain.length == 0){
            return array;
        }
        array = removeDuplicate(array);
        if(array == null || array.length == 0) {
			return finalArray;
        }
		ArrayList except	= new ArrayList();
		for(int i=0; i < toRetain.length; i++){
			except.add(toRetain[i].toLowerCase());
		}

        try{
			ArrayList list	= new ArrayList();
            for(int i=0; i < array.length; i++){
				if(except.contains(array[i].toLowerCase())){
					list.add(array[i]);
				}
            }
			list.trimToSize();
			finalArray		= new String[list.size()];
			for(int i=0;i < finalArray.length;i++){
				finalArray[i]
							= (String) list.get(i);
			}
        }catch (Exception e) {
            Debug.println("Exception in StringUtil: retain() " + e, 2);
        }
        return finalArray;
    }


    /**
     *  Adds a feature to the ToNoDuplicatesArray attribute of the StringUtil
     *  class
     *
     *@param  list     The feature to be added to the ToNoDuplicatesArray
     *      attribute
     *@param  element  The feature to be added to the ToNoDuplicatesArray
     *      attribute
     *@return           Return Value
     */
    public static String[] addToNoDuplicatesArray(String[] list, String element) {
        if (list == null || element == null || element.trim().equals("")) {
            return list;
        }
        String[] newList = new String[list.length + 1];
        boolean add = true;
        for (int k = 0; k < list.length; k++) {
            newList[k] = list[k];
            if (element.equals(list[k])) {
                add = false;
                break;
            }
        }
        if (add) {
            newList[newList.length - 1] = element;
            return newList;
        }
        return list;
    }
  /**
   * This method will return array removing of duplicate values
   * @param array : array object
   * @return array
   */
    public static String[] removeDuplicate(String[] array) {
        if (array == null) {
            return null;
        }

        ArrayList list	= new ArrayList();
        for (int i = 0; i < array.length; i++) {
			if(list.contains(array[i].trim())){
				continue;
			}
            list.add(array[i].trim());
        }
        return convertToArray(list);
    }
/**
 * This method will return array of string of comma seperated values of arrayList
 * @param list : list ArrayList
 * @return Array
 */
    public static String[] convertToArray(ArrayList list){
		String[] array	= new String[0];
		if(list == null){
			return null;
		}
		if(list.isEmpty()){
			return array;
		}
		list.trimToSize();
		String toCommaSep = toCommaSeparated(list);
		array = toArray(toCommaSep);
		return array;
	}
    /**
     * This Method will return String of comma seperated values 
     * @param list :list object
     * @return
     */
    public static String toCommaSeparated(ArrayList list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer(",");
        Iterator it = list.iterator();
		int i = 0;
        while (it != null && it.hasNext()) {
            String val = (String) it.next();
            sb.append(val.trim());
            sb.append(",");
        }
        return sb.toString();
    }
  //P_Enh_AddressBook starts
    /**
     * This Method will return String of comma seperated values 
     * @param list :list object
     * @return
     */
    public static String toCommaSeparatedWithValidCheck(ArrayList list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = list.iterator();
		int i = 0;
        while (it != null && it.hasNext()) {
            String val = (String) it.next();
            if(isValid(val)){
            	sb.append(val.trim());
            	sb.append(",");
            }
        }
        if(sb.toString().endsWith(","))
        {
        	return sb.toString().substring(0, sb.length()-1);
        }
        
        return sb.toString();
    }
  //P_Enh_AddressBook ends
    public static String toCommaSeparatedProper(ArrayList list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.toString().replace("[","").replace("]","");
    }
    public static String toCommaSeparatedProper(Set list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.toString().replace("[","").replace("]","");
    }
    /**
     * This method will return array of valid elements 
     * @param array : array object 
     * @return array
     */
    public static String[] cleanArray(String[] array) {
        if (array == null) {
            return null;
        }

        ArrayList tempArray = new ArrayList();

        for (int i = 0; i < array.length; i++) {
            if (isValid(array[i])) {
                tempArray.add(array[i]);
            }
        }

        tempArray.trimToSize();
        String[] newArray = new String[0];
        if (!tempArray.isEmpty()) {
            newArray = new String[tempArray.size()];
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = (String) tempArray.get(i);
            }
        }

        return newArray;
    }
    /**
     * This method will return string in the format of html
     * @param str : str object
     * @return String
     */
	public static String formatForHtml(String str) {
        String retStr = replaceChar(str, '\n', "<br>");
		Debug.println(retStr);
		retStr = replaceChar(retStr, '\t', "&nbsp;&nbsp;&nbsp;&nbsp");
        retStr = replaceSubString(retStr, "", "&quot;");
		retStr = replaceSubString(retStr, "&#8220;", "&quot;");
		retStr = replaceSubString(retStr, "&apos;", "&#39;");
//		retStr = replaceChar(retStr, '?', "&quot;");
//		System.out.println(retStr);
        retStr = replaceSubString(retStr, "&#8221;", "&quot;");
        return retStr;
    }
	 /**
	    * This Method will replace the Charactor in the input string with the String provided as third parameter to this method
	    * @param str : str object 
	    * @param charReplace :  charReplace char
	    * @param strReplace :  strReplace object
	    * @return String : replace String
	    */
	
	public static String replaceChar(String str, char charReplace, String strReplace) {
        return replaceSubString(str, String.valueOf(charReplace), strReplace);
    }
	/**
	 * This method will return string that is seperated from seperator as seperator is input by the string 
	 * @param array : array object
	 * @param separator : seperator oobject
	 * @return String
	 */
	public static String getCharacterSeparatedString2(String[] array, String separator){
		return getCharacterSeparatedString2(convertToArrayList(array), separator);
	}
/**
 * This method will return string that is seperated from seperator as seperator is input by the string 
 * @param list : list object
 * @param separator : seperator oobject
 * @return String
 */
	public static String getCharacterSeparatedString2(ArrayList list, String separator){
		if(list == null || list.isEmpty()){
			return "";
		}
		StringBuffer stringBuffer		= new StringBuffer();
		for(int i=0;i < list.size();i++){
			String string				= (String) list.get(i);
			stringBuffer.append(string);
			if(i < list.size()){
				stringBuffer.append(separator);
			}
		}
		return stringBuffer.toString();
	}
/**
 * Tis method will return the string seperated by seperator input as seperator
 * @param array : array object 
 * @param separator : seperator object 
 * @return String
 */
	public static String getCharacterSeparatedString(String[] array, String separator){
		return getCharacterSeparatedString(convertToArrayList(array), separator);
	}
	/**
	 * Tis method will return the string seperated by seperator input as seperator
	 * @param list : list object 
	 * @param separator : seperator object 
	 * @return String
	 */
	public static String getCharacterSeparatedString(ArrayList list, String separator){
		if(list == null || list.isEmpty()){
			return "";
		}
		StringBuffer stringBuffer		= new StringBuffer();
		for(int i=0;i < list.size();i++){
			String string				= (String) list.get(i);
			stringBuffer.append(string);
			if(i != list.size()){
				stringBuffer.append(separator);
			}
		}
		return stringBuffer.toString();
	}


	public static String getCSVString(String[] array){
		return getCSVString(convertToArrayList(array));
	}
	
/**
 * This method will return comma separated string of valid String 
 * @param array : array object
 * @return String
 * Added by NishantP
*/
	public static String getCSVString1(String[] array){
		return getCSVString(convertToArrayList1(array));
	}
	
/**
 * This method will return CSv String of array list
 * @param list : list object
 * @return String
 */
	public static String getCSVString(ArrayList list){
		if(list == null || list.isEmpty()){
			return "";
		}
		StringBuffer stringBuffer		= new StringBuffer();
		for(int i=0;i < list.size();i++){
			String string				= (String) list.get(i);
			stringBuffer.append(string+",");
		}
		if(stringBuffer.length()>0){
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}

	public static String getCalendarString(String str) {
        String returnString = str;

		if(str.length() <= 22){
			return returnString;
		}
		else{
			int index = str.indexOf(" ");
			if(index >= 17 && index <= 22){
				returnString = str.substring(0,index);
			}
			else{
				returnString = str.substring(0,22);
			}
		}
		return returnString + "...";

    }
	public static boolean hasPatterns(String string, String[] array){
		if(string == null || array == null){
			return false;
		}
		for(int i=0; i < array.length; i++){
			if(array[i].trim().indexOf(string.trim()) != -1){
				return true;
			}
		}

		return false;
	}
	
	public static boolean containsArrayPattern(String string, String[] array){
		if(string == null || array == null){
			return false;
		}
		for(int i=0; i < array.length; i++){
			if((array[i].trim().toString()).equals(string.trim().toString()) ){
				return true;
			}
		}

		return false;
	}
	/**
	 * This method will return the string empty if input string is null
	 * @param str : str String
	 * @return String
	 */ 
	public static String getEmptyStringIfNull(String str) {
        if (str == null) return "";
        return str;
    }
/**
 * This method will convert the -1 with the blank
 * @param value : value String
 * @return String
 */
	public static String convertMinusOne(String value){
		if(value == null){
			return "";
		}else if(value.equals("-1")){
			return "";
		}else{
			return value;
		}
	}

/**
 * This method will convert comma seperated string into SeqeunceMap
 * @param sValue : sValue object
 * @return Sequence map
 */
	/**
	
*/
	public static SequenceMap convertStringToSequenceMap(String sValue){
		SequenceMap smReturnMap		= null;
		if (sValue != null){
			smReturnMap					= new SequenceMap();
			StringTokenizer stValues	= new StringTokenizer(sValue , ",");
			String value				= null;
			while(stValues.hasMoreTokens()){
				value				= stValues.nextToken();
				smReturnMap.put(value , value);
			}//while
		}//if
		return smReturnMap;
	}

/**
 * 	This method returns the name to Title Case
 * @param name : name String
 * @return String
 */

	public static String toTitleCase(String name){
		StringBuffer nameBuffer = new StringBuffer();
		StringTokenizer tokenizer			= new StringTokenizer(name, " ");
		while (tokenizer.hasMoreTokens()) {
			String sWord				= tokenizer.nextToken();
			nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
		}
		return nameBuffer.toString();
	}
	
	public static String toBeanMethodName(String name){
		String methodName = null;
		if(StringUtil.isValid(name)){
			methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		}
		return methodName;
	}


   
    

    
	public static String formatName(String fName, String lName){
		return formatName(fName, null, lName);
	}
	
	public static String formatNameForSch(String fName, String lName){
		String fullName = null;
		fName = StringUtil.replaceSpecialCharacters(fName);
		lName = StringUtil.replaceSpecialCharacters(lName);
		
		fullName = formatName(fName, null, lName);
		
		return fullName;
	}
	
	public static String formatNameForSch(String fName, String mName, String lName){
		String fullName = null;
		fName = StringUtil.replaceSpecialCharacters(fName);
		mName = StringUtil.replaceSpecialCharacters(mName);
		lName = StringUtil.replaceSpecialCharacters(lName);
		
		fullName = formatName(fName, mName, lName);
		
		return fullName;
	}
	
/**
 * This method will return the String as name in which first letter is in caps of first name,
 * middle name and last name
 * @param fName :fName object
 * @param mName :mName object 
 * @param lName :lName object
 * @return String
 */
	

	public static String formatName(String fName, String mName, String lName){

		StringBuffer nameBuffer = new StringBuffer();
		StringTokenizer tokenizer = null;

		if(!badString(fName)){
			//P_CM_B_36245 By Nikhil Verma
		/*	tokenizer			= new StringTokenizer(fName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
			}
			nameBuffer = new StringBuffer(nameBuffer.toString().trim()).append(" ");*/
			nameBuffer.append(fName).append(" ");
		}

		if(!badString(mName)){
//			P_CM_B_36245 By Nikhil Verma
		/*	tokenizer			= new StringTokenizer(mName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
			}*/
			nameBuffer.append(mName).append(" ");
		}

		if(!badString(lName)){
//			P_CM_B_36245 By Nikhil Verma
		/*	tokenizer = new StringTokenizer(lName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord = tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase());
			}*/
			nameBuffer.append(lName);
		}
		return nameBuffer.toString().trim();
	}
	
	/**
	 * This method generates a part of query string used for sorting name
	 * composed of first name and last name.
	 * @author YogeshT
	 * @param fName		//column name for first name.
	 * @param lName		//column name for last name.
	 * @param sortOrder	//sorting oder to be applied on name.
	 * @date 24 Sep 2008
	 * @return String
	 */
	public static String getNameSortingQuery(String fName, String lName, String sortOrder){
		return getNameSortingQuery(fName, null, lName, sortOrder);
	}
	/**
	 * This method generates a part of query string used for sorting name
	 * composed of first name, middle name and last name.
	 * @author YogeshT
	 * @param fName		//column name for first name. 
	 * @param mName		//column name for middle name.
	 * @param lName		//column name for last name.
	 * @param sortOrder	//sorting oder to be applied on name.
	 * @date 24 Sep 2008
	 * @return String
	 */
	public static String getNameSortingQuery(String fName, String mName, String lName, String sortOrder){
		//BB_CT_REPORT_E240908
		StringBuffer sortingQuery = new StringBuffer("");
		if(!"ASC".equalsIgnoreCase(sortOrder.trim()) && !"DESC".equalsIgnoreCase(sortOrder.trim())){
			sortOrder = "ASC";
		}
		if(!badString(fName)){
			sortingQuery.append(fName).append(" ").append(sortOrder).append(", ");
		}
		if(!badString(mName)){
			sortingQuery.append(mName).append(" ").append(sortOrder).append(", ");
		}
		if(!badString(lName)){
			sortingQuery.append(lName).append(" ").append(sortOrder);
		}
		return sortingQuery.toString();
	}
	
       /**
        * This method will return the String as last name and first name without in capital letters
        * @param fName : fName String
        * @param lName : lName String
        * @return String ; Name
        */
        public static String formatNameWithoutCap(String fName,String lName){

		StringBuffer nameBuffer = new StringBuffer();
		StringTokenizer tokenizer = null;

		if(!badString(fName)){
			tokenizer			= new StringTokenizer(fName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord+ " ");
			}
			nameBuffer = new StringBuffer(nameBuffer.toString().trim()).append(" ");
		}

		if(!badString(lName)){
			tokenizer			= new StringTokenizer(lName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord+ " ");
			}
		}
		

		return nameBuffer.toString().trim();
	}
        
        
        
        //Added By Mukesh Singla
        public static String formatNameWithSpace(String fName, String lName){
		return formatNameWithSpace(fName, null, lName);
	}
        
       /**
* Added By mukesh Singla, This method will return the String as name in which first letter is in caps of first name,
* middle name and last name
* @param fName :fName object
* @param mName :mName object 
* @param lName :lName object
* @return String
*/
	public static String formatNameWithSpace(String fName, String mName, String lName){

		StringBuffer nameBuffer = new StringBuffer();
		StringTokenizer tokenizer = null;

		if(!badString(fName)){
			tokenizer			= new StringTokenizer(fName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
			}
		}

		if(!badString(mName)){
			tokenizer = new StringTokenizer(mName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord = tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase());
			}
		}

                if(!badString(lName)){
			tokenizer			= new StringTokenizer(lName, " ");
			while (tokenizer.hasMoreTokens()) {
				String sWord				= tokenizer.nextToken();
				nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
			}
			//nameBuffer = new StringBuffer(nameBuffer.toString().trim()).append(", ");
		}

		return nameBuffer.toString().trim();
	}
        
        
	public static String formatFieldNames(String value){
		StringBuffer returnString		= new StringBuffer("");
		StringTokenizer headerTokenizer		= new StringTokenizer(value);
		boolean flag			= true;

		while(headerTokenizer.hasMoreTokens()){

				returnString.append(headerTokenizer.nextToken());

		}

		return returnString.toString();

	 }
	/**
	 * This method return the string removing the single appostrophi from the input string
	 * @param val : val object
	 * @return String
	 */
	 public static String removeAppostrophi(String val){

    	 if(val!=null){
            if(val.indexOf("\"")!=-1)
                  val=val.replaceAll("\"","&#148;");
            if(val.indexOf("'")!=-1)
                  val=val.replaceAll("'","&#146;");
    	 }else{
    		 val="";
    	 }
            return val;
     }
     
         /**
     	 * This method return the string removing the single appostrophi from the input string
     	 * @param val : val object
     	 * @return String
     	 */
              public static String removeAppostrophiSpecial(String val){

             	 if(val!=null){
                     if(val.indexOf("\"")!=-1)
                           val=val.replaceAll("\"","@@DOUBLE@");
                     if(val.indexOf("'")!=-1)
                           val=val.replaceAll("'","@@SINGLE@");
             	 }else{
             		 val="";
             	 }
                     return val;
              }
              
           //P_FIM_B_26965 : starts
              /**
           	 * This method return the string removing the ampersand '&' from the input string
           	 * @param val : val object
           	 * @return String
           	 */
              public static String removeAmpersandSpecial(String val){
            	  
                	 if(val!=null){
                        if(val.indexOf("&")!=-1)
                              val=val.replaceAll("&","@@AMP@");
                	 }else{
                		 val="";
                	 }
                        return val;
                 }
            //P_FIM_B_26965 : ends  
              /**
               * This method return the string removing the '%' & '#' from the input string
           	   * @param val 
           	   * @return String
               */
              public static String removesPercentAndHash(String val){

             	 if(val!=null){
                      if(val.indexOf("%")!=-1)
                            val=val.replaceAll("%","@@PERC@");
                      if(val.indexOf("#")!=-1)
                            val=val.replaceAll("#","@@HASH@");
                      if(val.indexOf("\\?")!=-1)
                          val=val.replaceAll("\\?","@@QUES@");
                      if(val.indexOf("\\")!=-1)
                    	  val=val.replaceAll("\\\\","@@SLASH@");
              	 }else{
              		 val="";
              	 }
                      return val;
                } 
              /**
           	 * This method return the string removing the single appostrophi from the input string
           	 * @param val : val object
           	 * @return String
           	 */
                    public static String reversesAppostrophiSpecial(String val){

                   	 if(val!=null){
                           if(val.indexOf("@@DOUBLE@")!=-1)
                                 val=val.replaceAll("@@DOUBLE@","\"");
                           if(val.indexOf("@@SINGLE@")!=-1)
                                 val=val.replaceAll("@@SINGLE@","'");
                   	 }else{
                   		 val="";
                   	 }
                           return val;
                    }   
              
         
     	/**B_CM_B_68740 added by Ankit Chauhan
     	 * This method return the string removing the single  input string from the appostrophi
     	 * @param val : val object
     	 * @return String
     	 */  
         public static String revertAppostrophi(String val){

        	 if(val!=null){
                //if(val.indexOf("&#146;")!=-1) Added by vishal Lodha for bug number  D_CM_B_70511
                     if(val.indexOf("&#148;")!=-1)
                      val=val.replaceAll("&#148;","\"");
                if(val.indexOf("&#146;")!=-1)
                      val=val.replaceAll("&#146;","'");
                //Added by Teena Sharma starts
                if(val.indexOf("&#8221;")!=-1)
                    val=val.replaceAll("&#8221;","\"");
                if(val.indexOf("&#8217;")!=-1)
                    val=val.replaceAll("&#8217;","'");
                if(val.indexOf("&#65533;")!=-1)
                    val=val.replaceAll("&#65533;","'");
                if(val.indexOf("&#39;")!=-1)
                    val=val.replaceAll("&#39;","'");
              //Added by Teena Sharma ends
                if(val.indexOf("&#044;")!=-1)
                    val=val.replaceAll("&#044;","'");
        	 }else{
        		 val="";
        	 }
                return val;
         }

	 //Added by Abishek Singhal for replaceing the ' 24 May 2006 Start
    	 /**
    	  * This method return the string replacing the single appostrophi with html value from the input string
    	  * @param val : val object
    	  * @return String
    	 */
		 public static String replaceAppostrophiWithHTMLValue(String val){

				if(val== null) return "";

                if(val.indexOf("\"")!=-1)
                      val=val.replaceAll("\"","&#148;");
                if(val.indexOf("'")!=-1)
                      val=val.replaceAll("'","&#39;");
                return val;
         }
		 //Added by Abishek Singhal for replaceing the ' 24 May 2006 End
	 /**
	  * This method return the string replacing the single appostrophi with slash from the input string
	  * @param val : val object
	  * @return String
	 */
		 public static String replaceAppostrophiWithSlashes(String val){

				if(val== null) return "";
       
                      val=val.replaceAll("'","\\\\'");
                      val=val.replaceAll("'","\\\\'");
                           
                return val;
         }
		
		 public static String replaceAppostrophiWithSlashesForJS(String val){

				if(val== null) return "";
					// System.out.println("val 11111111 :"+val);
                      val=val.replaceAll("'","\\\\'");
                      val=val.replaceAll("\"","\\\\\"");
                //System.out.println("val 2222 :"+val);
                return val;
         }




//		added by saurabh as the HTML value for ""=="&#34; + '="&#39;" dated 2 March 2006
/**
 * This method return the string removing the single appostrophi from the input string
 * @param val : val object
 * @return String
 */

public static String removeAppostrophiCorrect(String val){
		//System.out.println("start  " + val);
                if(val.indexOf("\"")!=-1)
                      val=val.replaceAll("\"","&#34;");
                if(val.indexOf("\'")!=-1)
                      val=val.replaceAll("\'","&#39;");
		
		     // System.out.println(val);
                return val;
         }


/**
 * This method is used to retain " and ' in query values. 
 * @param val : val String
 * @return String
 */
	public static String byPassQuotesInQuery(String val){
        if(isValid(val)) {

            if(val.indexOf("\"")!=-1)
                val=val.replaceAll("\"","\\\\\"");
            if(val.indexOf("'")!=-1)
                val=val.replaceAll("'","\\\\'");
        }
        return  val;
    }
	public static String byPassQuotesInQueryByChecking(String val){
        if(isValid(val)) {
            if(val.indexOf("\\'")!=-1) {
                val=val.replaceAll("\\\\'","slahquots;");
            }
            if(val.indexOf("\"")!=-1)
                val=val.replaceAll("\"","\\\\\"");
            if(val.indexOf("'")!=-1)
                val=val.replaceAll("'","\\\\'");
            if(val.indexOf("slahquots;")!=-1) {
                val=val.replaceAll("slahquots;","\\\\'");
            }
        }
        return  val;
    }
	/**
	* @method removes the special characters like (")
	* @param  value  -- passes the string wchich may contains any of the special characters in the list
	* @return String 
	* created by vikas 12/28/2004
	*/
	
	public static String replaceSpecialCharacters(String value){
                                        if(isRequestParamValid(value)){
					StringTokenizer sToken                   = new StringTokenizer(value , "\"");
					String name                             = "";
					boolean flag                            = false;
					while(sToken.hasMoreTokens()){
						if(!flag){
							name            = sToken.nextToken();
							flag            = true;
						}else{
							name            = name + "\\\"" + sToken.nextToken();
						}
					}
                                        return name;
                                        }else return "";
					

		 }

/**
 * This method will return the String converting to html
 * @param text : text String
 * @return String
 */
	public static String textToHtml(String text) {
		return textToHtml(text, false);
	}
	public static String textToHtml(String text, boolean isSkipBR)
	 {
	String html = new String();
	int len = text.length();
	int count = 0;
	for (int i = 0; i <= (len-1) ; i++ )
	{
		char c = text.charAt(i);
		if (c == '\n')
		{
			html = html + "<BR>";
			count = 0;
		}
		if (count == 100)
		{
           int l = html.lastIndexOf(" ");
		   if ((l == 0)||(l == -1))
		   {
			   if(!isSkipBR) {
				   html = html + c + "<br>";
			   }
			   count = 0;
		   }
		   else
			{
		   String html2 = html.substring(0, l);
		   String html3 = html.substring(l+1);
		   if(!isSkipBR) {
			   html = html2 + "<BR>" + html3 + c;
		   }
		   count = html3.length();
			}
		}
		else
		{
			html = html + c;
			count ++;
		}
	}
	return html;
 }

/**
 * Use {@link PortalUtils filterValue} function to change invalid html.
 * @param value
 * @return
 * P_B_Codebase_22112013
 */
public static String filterInvalidHtml(String value){
	//boolean containsHTML = value.matches(".*<[^div.*|^a.*href.*|^span.*|^td.*|^tr.*|^table.*]+>.*|&nbsp;|<br>|");
	boolean containsHTML = value.matches(".*\\<[^>]+>.*|&nbsp;");
	if(!containsHTML){
		//value = PortalUtils.filterValue(value);
		value = BaseUtils.filterValue(value);     //For Product_Seperation_BL By Amar Singh.
	}
	return value;
}
   /**
		* @method creates the subject for the calendar events of length 25
		* @param  value  -- subject
		* created by vikas 8/24/2005
		*/

		public static String manipulateTitle(String subject){
			//no need of this lene bkz this has already been handled in calschedulesDAO
		//	if(subject!=null)subject=removeAppostrophi(subject);
			
			String modifiedSub="";
			try{
			if(subject==null)return modifiedSub;
			if(subject.length()<=25)return subject;
			StringTokenizer sToken                   = new StringTokenizer(subject , " ");
			while(sToken.hasMoreTokens()){
				if(modifiedSub.length()<25)
				modifiedSub+=" "+sToken.nextToken();
				else
					break;
					//return modifiedSub;

			}
			if(modifiedSub.length()>25){
				StringTokenizer sToken1                   = new StringTokenizer(modifiedSub , " ");
				int tokenCount=sToken1.countTokens();
				if(tokenCount>1){
				modifiedSub="";
				for(int i=0;i<tokenCount-1;i++){
					if(sToken1.hasMoreTokens())
					modifiedSub+=" "+sToken1.nextToken();

				}
				}
			}
						
			}catch(Exception e){
			}
			
return modifiedSub+"..";

		}
                
                // Added By Nikhil Verma on 29/10/2007
                
                public static String manipulateTitle(String subject,int stringSize){
			//no need of this lene bkz this has already been handled in calschedulesDAO
		//	if(subject!=null)subject=removeAppostrophi(subject);
			boolean flag = false;
			String modifiedSub="";
			try{
			if(subject==null)return modifiedSub;
			if(subject.length()<=stringSize)return subject;
			StringTokenizer sToken                   = new StringTokenizer(subject , " ");
			if(sToken.countTokens() >1) flag= true;
			while(sToken.hasMoreTokens()){

				if(modifiedSub.length()<stringSize)
				modifiedSub+=" "+sToken.nextToken();
				else
					break;
					//return modifiedSub;

			}
			if(modifiedSub.length()>stringSize){
				StringTokenizer sToken1                   = new StringTokenizer(modifiedSub , " ");
				int tokenCount=sToken1.countTokens();
				if(tokenCount>1){
				modifiedSub="";
				for(int i=0;i<tokenCount-1;i++){
					if(sToken1.hasMoreTokens())
					modifiedSub+=" "+sToken1.nextToken();

				}
				}
			}
						
			}catch(Exception e){
			}
			if(flag) modifiedSub = modifiedSub+"..";
return modifiedSub ;

		}
                
         /**
          * This method wii return the string removing comma find in the end of string
          * @param value1 : value1 object
          * @return String
          */       
                 public static StringBuffer removeLastComma(StringBuffer value1){
                    
                    StringBuffer  result = new StringBuffer();
                    String value         = "";        
                    int pos              = -1;
                    if(value1!= null && !value1.equals("")){
                        value         = value1.toString(); 
                        pos           = value.lastIndexOf(",");
                        if(pos != -1){
                            value     = value.substring(0,pos);
                        }
                        
                        
                    }
                    
                    return result.append(value);
                }
                 
                 public static String removeAllComma(String value){
                     
                     if(isValid(value)){
                    	 value = value.replaceAll(",", "");
                     }
                     return value;
                 }
                 
                 public static String replaceCommaToCommaSpace(String value){
                     
                     if(isValid(value)){
                    	 value = value.replaceAll(" ", "");
                    	 value = value.replaceAll(",", ", ");
                     }
                     return value;
                 }

                 
                 public static String[] SplitString(String str,String splitChar){
                                        
                     return str.split(splitChar);
                }

     /*** Arguments for following function : - 
                origionalStr - string which we want to be padded with some character or string 
                strToPad - contains String which is used for padding
                finalLength - how much lenght we want after padding the origional string(length in terms of chars)
                preFix - if this flag is false then this function will perform postFix padding otherwise preFix padding    
         following function will return a String after padding the passed string with desired padding string of specified finalLength   
         Added By : Navneet Arora.
         Date :  15th July 2006
     ***/    
        public static String getPaddedString(String origionalStr, String strToPad, int finalLength,boolean preFix){
                if(origionalStr == null || strToPad == null || finalLength <= 1 || (origionalStr.length()+strToPad.length()) > finalLength)
                        return origionalStr;

                StringBuffer finalString = new StringBuffer();
                int orgStrLength = origionalStr.length();
                int padStrLength = strToPad.length();

                if(!preFix){
                    finalString.append(origionalStr); 
                   while(finalString.length()+padStrLength <= finalLength){
                        finalString.append(strToPad);
                    }
                }else{
                    while(finalString.length()+padStrLength <= (finalLength-orgStrLength)){
                         finalString.append(strToPad); 
                    }
                    finalString.append(origionalStr);
                }
                return finalString.toString();
         }//      end of getPaddedString function

//sanjeev
public static String formatLongStringToPrint(String inputStr)
 	{
 			
 			return formatLongStringToPrint(inputStr,80);
 	}
 
    public static String formatLongStringToPrint(String inputStr,int charCountPerLine) {
		StringBuffer value ;
 		StringBuffer outputStr = new StringBuffer();
		int count = 0;
 		StringTokenizer stValues = new StringTokenizer(inputStr , " ");
 		while(stValues.hasMoreTokens()){
 			value=new StringBuffer(stValues.nextToken());
 			count = count+value.length()+1;
 			StringBuffer tempValue = new StringBuffer();
 			int vcount=0 ;
 			while(value.length()-vcount > charCountPerLine) {
 				tempValue.append(value.substring(vcount,vcount+charCountPerLine)) ;
 				tempValue.append("<br>") ;
 				vcount = vcount + charCountPerLine ;
 			}
 			tempValue.append(value.substring(vcount)) ;
 			
 			outputStr.append(" ");
 			if(vcount > 0) {
 				count=value.length()-vcount ;
 			}
 			System.out.println("count = "+count);
			System.out.println("value = "+tempValue);
 			if (count > charCountPerLine) {
 				System.out.println("count = "+count);
 				System.out.println("value = "+value);
 				count = value.length();
 				outputStr.append("<br>");
 			}
 			outputStr.append(tempValue);
 		}
 		//System.out.println("outputStr = "+outputStr);
 		String outputStr1 = removeNewLineChar(outputStr.toString());
 		//    System.out.println("outputStr111111111111111 = "+outputStr1);
 		return outputStr1;
 	}
 
 	public static String removeNewLineChar(String outputStr)
 	{
 		String retStr = null;
 		if (outputStr != null)
 		{
 			retStr = replaceChar(outputStr, '\n', "<br>");
 		}
 		//System.out.println("retStr====================================== == "+retStr);
 		return retStr;
 	}

        /**P_FRANONLINE_B_52432
         * Added By Saurabh Sinha
         * This function will eliminate enter keys presentin the beginning and at the end of lines
         * @param str -- Complete string of the sentence.
         * @param endEnterKeyrequired --- its for enabling the end enter key required or not
         * @return
         */
        public static String trimNewLineChar(String str, boolean endEnterKeyrequired) {
        String tempStr = str;
        for (int j = 0; j < str.length(); j++) {
            if (tempStr.startsWith("<br>")) {
                tempStr = tempStr.substring(4);
            } else {
                break;
            }
        }
        if (endEnterKeyrequired) {
            str = tempStr;
            for (int j = (str.length() - 1); j >= 0; j--) {
                if (tempStr.endsWith("<br>")) {
                    tempStr = tempStr.substring(0, (tempStr.length() - 4));
                } else {
                    break;
                }
            }
            str = tempStr;
        }
        return str;
    }
 	
 	/**
 	 * This method is used to remove given character repetition from inputString.
 	 * <p>Example: <tt>removeRepeatChar("Prrroducerrr", 'r');</tt>
 	 * <p>this method will <tt>return "Producer"</tt>;
 	 * @author YogeshT
 	 * @param inputStr	//input string
 	 * @param charToRemove	//character thats repetition is to be removed from inputString.
 	 * @return String
 	 * @since 19 Jan, 2009
 	 */
 	public static String removeRepeatChar(String inputStr, char charToRemove) {
 		//BB_E_19Jan2009
 		int repeatIndex = 1;
 		if (inputStr != null){
 			for(int i=0; i<inputStr.trim().length()-1; i++) {
 				if((inputStr.charAt(i))==charToRemove){
 					repeatIndex = 1;
 					while(inputStr.charAt(i+repeatIndex)==charToRemove) {
 						repeatIndex++;
 						if(i+repeatIndex == inputStr.trim().length()) {
 							break;
 						}
 					}
 					inputStr = inputStr.trim().substring(0, i+1)+inputStr.trim().substring(i+repeatIndex);
 				}
 			}
 		}else{
 			return null;
 		}
 		return inputStr.trim();
 	}


    	
//      Genral utility class to support Import Data.

        /**
         * 
         * @param lineString
         * @param fileType
         * @return
         */
     public static String [] splitAndGetFieldNames(String lineString ,String fileType)
    {
        if(lineString==null || lineString.trim().equals(""))
            return null;

        String pattern = "(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))";
        if(fileType.equals("tab"))
            pattern = "\t" + pattern;
        else
            pattern = ",(?=(?:[^\"]*\")*(?![^\"]*\"))";//P_B_15584

        String fieldNames[] = lineString.split(pattern);

        //remove starting and ending double-quotes (") if any. Also, if there is consecutive double-quotes in the data, replace by single double-quote.
        for(int i=0 ; i < fieldNames.length ; i++){
            if (fieldNames[i].startsWith("\""))
                fieldNames[i] = fieldNames[i].substring(1);
            if (fieldNames[i].endsWith("\"")){
	    	if (fieldNames[i].length() > 1)
                	fieldNames[i] = fieldNames[i].substring(0, fieldNames[i].length()-1);
		else
			fieldNames[i] = "";
	    }
            fieldNames[i] = fieldNames[i].replaceAll("\"\"","\"").trim();
        }
        return fieldNames;
    }
/**
 * This method will return true if string contain all digits otherwise false
 * @param checkString : checkString object
 * @return boolean
 */
    public static boolean isStringAllDigits(String checkString)
	{
		for(int i=0; i<checkString.length(); i++)
		{
			boolean check = Character.isDigit(checkString.charAt(i));
			if(!check)
			{
				return false;
			}
		}

		return true;
	}

	public static String chariterator(String string){

	StringBuffer stringBuffer =new StringBuffer();
	StringCharacterIterator iterator = new StringCharacterIterator(string);
            char character =  iterator.current();
            while (character != StringCharacterIterator.DONE ){
              
              if (character == '\r' || character == '\n' || character == '\''){

                 stringBuffer.append(" ");
              }else {
                 stringBuffer.append(character);
              }
			  character = iterator.next();
        }
		return stringBuffer.toString();
	}
        
        /**
         * Function to highlight the search string in source String using user specified style sheet
         * @Author : Suneel Soni & Kushagra Jindal
         * @param1 : sourceString is the target string
         * @param2 : searchString is the search String
         * @param3 : styleClass is the style class to be applied for highlighting.
         */
        public static String highLightSearchString(String sourceString,String searchString,String styleClass){
            if(sourceString==null) return "";
            if(searchString==null || (searchString.trim()).equals("")) return sourceString;
            if(styleClass==null  || (styleClass.trim()).equals("")) styleClass = "tb_data_orange";
            StringTokenizer fqST = new StringTokenizer(searchString);
            StringBuffer sourceStringBuffer = new StringBuffer(sourceString);
            Pattern p = null;
            Matcher m = null;
            String startSpan = "<span class='"+styleClass+"'>";
            String endSpan = "</span>";
            while(fqST.hasMoreTokens()){
                int count = 0;
                String nextToken = (String) fqST.nextToken();
                p = Pattern.compile("(?i:"+nextToken+")");
                m = p.matcher(sourceStringBuffer.toString()); // get a matcher object
                while(m.find()) {
                   sourceStringBuffer.insert(m.start()+count, startSpan);
                   count = count + startSpan.length();
                   sourceStringBuffer.insert(m.end()+count, endSpan);                   
                   count = count + endSpan.length() ;
                }
            }
            return sourceStringBuffer.toString();
        }
        
        
        //P_SUPP_BUG_14364 start
        
        /**
         * Function to highlight the search string in source String using user specified style sheet when multiple words are entered in search
         */
        
        public static String highLightSearchString1(String sourceString,String searchString,String styleClass){
            if(sourceString==null) return "";
            if(searchString==null || (searchString.trim()).equals("")) return sourceString;
            if(styleClass==null  || (styleClass.trim()).equals("")) styleClass = "tb_data_orange";
            StringTokenizer fqST = new StringTokenizer(searchString);
            StringBuffer sourceStringBuffer = new StringBuffer(sourceString);
            Pattern p = null;
            Matcher m = null;
            String startSpan = "<span class='"+styleClass+"'>";
            String endSpan = "</span>";
            while(fqST.hasMoreTokens()){
                int count = 0;
                int startCount=0;
                int endCount=0;
                String nextToken = (String) fqST.nextToken();
                p = Pattern.compile("(?i:"+forHTML(nextToken)+")"); // P_B_78159
                m = p.matcher(sourceStringBuffer.toString()); // get a matcher object
                while(m.find()) {
                	startCount=m.start()+count;
                	sourceStringBuffer.insert(startCount,"####");
                   count = count + 4;
                   endCount=m.end()+count;
                   sourceStringBuffer.insert(endCount, "###");                   
                   count = count + 3 ;
                }
            }
            sourceStringBuffer= new StringBuffer(sourceStringBuffer.toString().replaceAll("####", "<span class='"+styleClass+"'>"));
            sourceStringBuffer=new StringBuffer(sourceStringBuffer.toString().replaceAll("###", "</span>"));
            if(sourceStringBuffer.toString().indexOf("##")!= -1) {
           	 sourceStringBuffer=new StringBuffer(sourceStringBuffer.toString().replaceAll("##", ""));
            }
            return sourceStringBuffer.toString();
        }
        
         //P_SUPP_BUG_14364 end
        
        
         /**
       * Function to highlight the Exact Search string in source String using user specified style sheet 
       * @author Narotam Singh
       * @param sourceString
       * @param searchString
       * @param styleClass
       * @return String
       * @ against platpp_20110223_017
       */
        public static String highLightExactSearchString(String sourceString,String searchString,String styleClass){
            if(sourceString==null) return "";
            if(searchString==null || (searchString.trim()).equals("")) return sourceString;
            if(styleClass==null  || (styleClass.trim()).equals("")) styleClass = "tb_data_orange";
            StringBuffer sourceStringBuffer = new StringBuffer(sourceString);
            Pattern p = null;
            Matcher m = null;
            String startSpan = "<span class='"+styleClass+"'>";
            String endSpan = "</span>";
                int count = 0;
                p = Pattern.compile("(?i:"+searchString+")");
                m = p.matcher(sourceStringBuffer.toString()); // get a matcher object
                while(m.find()) {
                   sourceStringBuffer.insert(m.start()+count, startSpan);
                   count = count + startSpan.length();
                   sourceStringBuffer.insert(m.end()+count, endSpan);                   
                   count = count + endSpan.length() ;
                }
            
            return sourceStringBuffer.toString();
        }
        
        /**
         * Checks whether a parameter is exist in the request or not, and return true if exist
         * @param param the parameter name
         * @param request the HttpServletRequest
         * @return boolean
         * @author Bhoopal
         */
        public static final boolean isRequestParamExist(String param, HttpServletRequest request){
            boolean ret = false;
            if(request != null && param != null){
                if(request.getParameter(param) != null)                    
                    ret = true;
            }
            return ret;
        }
        
        /**
     * Checks whether a parameter value is valid or not, and returns true if valid
     * 
     * 
     * 
     * @author Bhoopal
     * @param paramvalue the parameter value
     * @return boolean
     * @see #isRequestParamValid(String param, HttpServletRequest request)
     */
        public static final boolean isRequestParamValid(String paramvalue){
            
            boolean ret = true;            
            
            if (paramvalue == null || paramvalue.trim().equalsIgnoreCase("") || paramvalue.trim().equalsIgnoreCase("null") || paramvalue.trim().equalsIgnoreCase("select") || paramvalue.trim().equalsIgnoreCase("-1") || paramvalue.trim().equalsIgnoreCase("Show All")){
                ret = false;
            }           
            
            return ret;
        }
        
        
        /**
     * Checks whether a parameter value in the http request is valid or not, and returns true if valid
     * 
     * 
     * 
     * 
     * @author Bhoopal
     * @param paramvalue the parameter value
     * @param request the HttpServletRequest
     * @return boolean
     * @see #isRequestParamValid(String paramvalue)
     * @see #isRequestParamExist(String param, HttpServletRequest request){
     */
         public static final boolean isRequestParamValid(String param, HttpServletRequest request){
            
            boolean ret = false;            
            ret = isRequestParamExist(param,request);
            if(ret){
                String paramvalue = request.getParameter(param);
                ret = isRequestParamValid(paramvalue);
            }
            
            return ret;
        }


		public static String replaceSpecialChars(String sourceString, Character [] specialChars){
			
			if(sourceString == null) return "";
			
			if(specialChars==null || specialChars.length == 0) return sourceString;

			char blank = ' ';
			
			StringBuffer sourceStringBuffer = new StringBuffer(sourceString);

			Pattern p = null;
			
			Matcher m = null;			

			for (int i=0; i<specialChars.length ; i++){				
				
				//String nextToken = (String) fqST.nextToken();

				int count = 0;
				
				p = Pattern.compile("(?i:\\"+specialChars[i]+")");
				
				m = p.matcher(sourceString); // get a matcher object
				
				sourceString = m.replaceAll(FieldNames.EMPTY_STRING);
			}
			return sourceString;
		}

		
		public static String formatName1(String fName, String lName){
			return formatName1(fName, null, lName);
		}

	/**
	 * This method will return the String as name in which first letter is in caps of first name,
	 * middle name and last name
	 * @param fName :fName object
	 * @param mName :mName object 
	 * @param lName :lName object
	 * @return String
	 */
		public static String formatName1(String fName, String mName, String lName){

			StringBuffer nameBuffer = new StringBuffer();
			StringTokenizer tokenizer = null;

			if(!badString(lName)){
				tokenizer			= new StringTokenizer(lName, " ");
				while (tokenizer.hasMoreTokens()) {
					String sWord				= tokenizer.nextToken();
					nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
				}
				
			}
			if(!badString(fName)   && !badString(lName)){
				nameBuffer = new StringBuffer(nameBuffer.toString().trim()).append(", ");
			}
			if(!badString(fName)){
			
				tokenizer			= new StringTokenizer(fName, " ");
				while (tokenizer.hasMoreTokens()) {
					String sWord				= tokenizer.nextToken();
					nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
				}
			}

			if(!badString(mName)){
				tokenizer = new StringTokenizer(mName, " ");
				while (tokenizer.hasMoreTokens()) {
					String sWord = tokenizer.nextToken();
					nameBuffer.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase());
				}
			}

			return nameBuffer.toString().trim();
		}
		
		/**
		 * used to get name in given format.
		 * @author YogeshT
		 * @param name_format
		 * @param fName
		 * @param lName
		 * @return formated name as string
		 * @since 21 Sep 2009
		 */
		public static String getFormatedName(String name_format, String fName, String lName){
			return getFormatedName(name_format, fName, null, lName);
		}
		
		/**
		 * used to get name in given format.
		 * @author YogeshT
		 * @param name_format
		 * @param fName
		 * @param mName
		 * @param lName
		 * @return formated name as string
		 * @since 21 Sep 2009
		 */
		public static String getFormatedName(String name_format, String fName, String mName, String lName){
			return getFormatedName(name_format, fName, mName, lName, null);
		}
		/**
		 * used to get name in given format.
		 * @author YogeshT
		 * @param name_format
		 * @param fName
		 * @param mName
		 * @param lName
		 * @param salutation
		 * @return formated name as string
		 * @since 21 Sep 2009
		 */
		public static String getFormatedName(String name_format, String fName, String mName, String lName, String salutation){

			StringBuffer nameBuffer = new StringBuffer("");
						
			StringBuffer f_name = new StringBuffer();
			StringBuffer m_name = new StringBuffer();
			StringBuffer l_name = new StringBuffer();
			
			StringTokenizer tokenizer = null;
			String sWord = null;
			if(badString(name_format)){
				name_format = Constants.NAME_FORMAT_L_F;//default format
			}
			
			//salutation
			if(!badString(salutation)){			
				nameBuffer.append(salutation).append(" ");
			}
			
			//first name
			if(!badString(fName)){			
				tokenizer = new StringTokenizer(fName, " ");
				while (tokenizer.hasMoreTokens()) {
					sWord = tokenizer.nextToken();
					f_name.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
				}
			}
			
			//middle name
			if(!badString(mName)){
				tokenizer = new StringTokenizer(mName, " ");
				while (tokenizer.hasMoreTokens()) {
					sWord = tokenizer.nextToken();
					m_name.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase());
				}
			}
			
			//last name
			if(!badString(lName)){
				tokenizer = new StringTokenizer(lName, " ");
				while (tokenizer.hasMoreTokens()) {
					sWord = tokenizer.nextToken();
					l_name.append(sWord.substring(0,1).toUpperCase() + sWord.substring(1).toLowerCase() + " ");
				}				
			}
									
			if(Constants.NAME_FORMAT_L_F.equalsIgnoreCase(name_format)){
				if(!badString(fName) && !badString(lName)){
					nameBuffer.append(lName.toString().trim()).append(" ").append(fName.toString().trim());
				}else if(!badString(fName)){
					nameBuffer.append(fName.toString().trim());
				}else if(!badString(lName)){
					nameBuffer.append(fName.toString().trim());
				}
			}else if(Constants.NAME_FORMAT_Lc_F.equalsIgnoreCase(name_format)){
				if(!badString(fName) && !badString(lName)){
					nameBuffer.append(lName.toString().trim())
						.append(" ")
						.append(fName.toString().trim());
				}else if(!badString(fName)){
					nameBuffer.append(fName.toString().trim());
				}else if(!badString(lName)){
					nameBuffer.append(fName.toString().trim());
				}
			}else if(Constants.NAME_FORMAT_F_L.equalsIgnoreCase(name_format)){
				if(!badString(fName) && !badString(lName)){
					nameBuffer.append(fName.toString().trim())
					.append(" ")
					.append(lName.toString().trim());
				}else if(!badString(fName)){
					nameBuffer.append(fName.toString().trim());
				}else if(!badString(lName)){
					nameBuffer.append(fName.toString().trim());
				}
			}else if(Constants.NAME_FORMAT_Fc_L.equalsIgnoreCase(name_format)){
				if(!badString(fName) && !badString(lName)){
					nameBuffer.append(fName.toString().trim())
						.append(" ")
						.append(lName.toString().trim());
				}else if(!badString(fName)){
					nameBuffer.append(fName.toString().trim());
				}else if(!badString(lName)){
					nameBuffer.append(fName.toString().trim());
				}
			}else if(Constants.NAME_FORMAT_F_M_L.equalsIgnoreCase(name_format)){
				if(!badString(fName) && !badString(mName) && !badString(lName)){
					nameBuffer.append(fName.toString().trim())
						.append(" ")
						.append(mName.toString().trim())
						.append(" ").append(lName.toString().trim());
				}else if(!badString(fName) && !badString(lName)){
					nameBuffer.append(fName.toString().trim())
						.append(" ")
						.append(lName.toString().trim());
				}else if(!badString(fName) && !badString(mName)){
					nameBuffer.append(fName.toString().trim())
						.append(" ")
						.append(mName.toString().trim());
				}else if(!badString(mName) && !badString(lName)){
					nameBuffer.append(mName.toString().trim())
						.append(" ")
						.append(lName.toString().trim());
				}
			}
			
			return nameBuffer.toString();
		}
		
		public static String removeAllAppostrophi(String value1){
            
            if(isValid(value1)){
           	 value1 = value1.replaceAll("'", "");
            }
            
            return value1;
        }
		
		/**
		 * 
		 * @param val numeric String String to replace with cahr
		 * @param enORdec encode to encode
		 * @return String
		 */
		 public static String encodeORdecodeString(String val,String enORdec){
                     if(isValid(val)){
                         if ("encode".equals(enORdec)) {
                             val = val.replaceAll("0", "z-");
                             val = val.replaceAll("1", "a");
                             val = val.replaceAll("2", "q-");
                             val = val.replaceAll("3", "r");
                             val = val.replaceAll("4", "n-");
                             val = val.replaceAll("5", "m");
                             val = val.replaceAll("6", "o");
                             val = val.replaceAll("7", "-l");
                             val = val.replaceAll("8", "b");
                             val = val.replaceAll("9", "-v");
                         } else {
                             val = val.replaceAll("z-", "0");
                             val = val.replaceAll("a", "1");
                             val = val.replaceAll("q-", "2");
                             val = val.replaceAll("r", "3");
                             val = val.replaceAll("n-", "4");
                             val = val.replaceAll("m", "5");
                             val = val.replaceAll("o", "6");
                             val = val.replaceAll("-l", "7");
                             val = val.replaceAll("b", "8");
                             val = val.replaceAll("-v", "9");
                         }
                     }else{
                         val ="";
                     }
		    	return val;
		    }
		 /**
		  * Ganesh
		  * @param intString
		  * @return true if given string is valid integer
		  */
		 public static boolean isvalidInteger(String intString){
			 boolean isThisANumber =false;
			 int tempInt=0;
			 try
			 {
				 tempInt= Integer.parseInt(intString);
				 isThisANumber =true;
			 }
			 catch (NumberFormatException nfe)
			 {
				 isThisANumber =false;
			 }
			 return isThisANumber;
		 }
		 
		 public static boolean isvalidDouble(String doubleString){
			 boolean isThisANumber =false;
			 double tempDouble=0.0;
			 try
			 {
				 tempDouble= Double.parseDouble(doubleString);
				 isThisANumber =true;
			 }
			 catch (NumberFormatException nfe)
			 {
				 isThisANumber =false;
			 }catch(Exception e){
				 
				 isThisANumber =false;
			 }
			 return isThisANumber;
		 }
		 
		 public static boolean isvalidDoubleNew(String s){//ENH_PW_SMART_QUESTIONS  
		        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1") && !s.trim().equals("-1.00"));

			 }
		 ////ZCUB-20151124-201 starts
		 public static boolean isvalidDoubleNewWithZero(String s,String zeroFormat){
		        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1") && !s.trim().equals("-1.00") && !s.trim().equals(zeroFormat));

			 }
		//ZCUB-20151124-201 ends
		 /**
		  * Ganesh
		  * @param string
		  * @return valid string
		  * Ex:
		  * Input String:"abc,sdf" (or) "'abc','sdf'"(or) "'abc,sdf"
		  * Output String:"'abc','sdf'"
		  */
		 public static String stringArrayToValidStringArrary(String st){
			 String returnString="";
			 try{
			 String[] result = st.split(",");
			 StringBuffer temp=new StringBuffer();
			 for (int x=0; x<result.length; x++){
		         if(isValid(result[x])){
		        	 temp.append("'").append(result[x].replaceAll("'","").trim()).append("'").append(",");
		         }
			 }
			 returnString=temp.substring(0,temp.toString().length()-1);
			 }catch(Exception ex){
				 returnString=st;
			 }
			 return returnString;
			 
		 }

     //Added by Praveen Gupta to display Special Characted regarding bug_42844
     private static void addCharEntity(Integer aIdx, StringBuilder aBuilder){
        String padding = "";
        if( aIdx <= 9 ){
            padding = "00";
        } else if( aIdx <= 99 ){
            padding = "0";
        } else {
            //no prefix
        }
        String number = padding + aIdx.toString();
        aBuilder.append("&#" + number + ";");
    }
    /**
     * @author manoj
     * @param sentence String to modify
     * @param maxLength maxlength to keep for a sentence.
     * @return Formatted String
     * @date Jan 21,2010 
     */ 
    public static String breakSentenceProperly(String sentence,int maxLength){
    	int lastindex =maxLength;
    	if(sentence!=null && sentence.length()>maxLength){
    		sentence=sentence.substring(0,maxLength);
    		lastindex = sentence.lastIndexOf(" ");
    		sentence = sentence.substring(0,lastindex);
            return sentence;
    	}else
    		return sentence;
    } 

    //Added by Praveen Gupta to display Special Characted regarding bug_42844
    public static String forHTML(String aText){
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character =  iterator.current();
        while (character != StringCharacterIterator.DONE ){
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\t') {
                addCharEntity(9, result);
            } else if (character == '!') {
                addCharEntity(33, result);
            } else if (character == '#') {
                addCharEntity(35, result);
            } else if (character == '$') {
                addCharEntity(36, result);
            } else if (character == '%') {
                addCharEntity(37, result);
            } else if (character == '\'') {
                addCharEntity(39, result);
            } else if (character == '(') {
                addCharEntity(40, result);
            } else if (character == ')') {
                addCharEntity(41, result);
            } else if (character == '*') {
                addCharEntity(42, result);
            } else if (character == '+') {
                addCharEntity(43, result);
            } else if (character == ',') {
                addCharEntity(44, result);
            } else if (character == '-') {
                addCharEntity(45, result);
            } else if (character == '.') {
                addCharEntity(46, result);
            } else if (character == '/') {
                addCharEntity(47, result);
            } else if (character == ':') {
                addCharEntity(58, result);
            } else if (character == ';') {
                addCharEntity(59, result);
            } else if (character == '=') {
                addCharEntity(61, result);
            } else if (character == '?') {
                addCharEntity(63, result);
            } else if (character == '@') {
                addCharEntity(64, result);
            } else if (character == '[') {
                addCharEntity(91, result);
            } else if (character == '\\') {
                addCharEntity(92, result);
            } else if (character == ']') {
                addCharEntity(93, result);
            } else if (character == '^') {
                addCharEntity(94, result);
            } else if (character == '_') {
                addCharEntity(95, result);
            } else if (character == '`') {
                addCharEntity(96, result);
            } else if (character == '{') {
                addCharEntity(123, result);
            } else if (character == '|') {
                addCharEntity(124, result);
            } else if (character == '}') {
                addCharEntity(125, result);
            } else if (character == '~') {
                addCharEntity(126, result);
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
              
    //added by Ankush Tanwar for service314 changes starts
    
    /**
	 * ek seedha tarika, ID se Appostrophi hatane ka
	 * @param val : val object
	 * @return String
	 */
	public static String removeAppostrophiSimple(String value){
		if(StringUtil.isValidNew(value)){
			value = value.replaceAll("'", "");
		}
		return value;
	}
        
       
    
    //added by Ankush Tanwar for service314 changes ends
    /**
     * This Method will return the comma seperated String enclosed in appostrophi
     * @param elems : elems array
     * @return String 
     */
    public static String toCommaSeparatedNew(Object[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if (i!=0) sb.append(", ");
			if(elems[i] == null){
				continue;
			}
			sb.append("'"+elems[i].toString()+"'");
		}
		return sb.toString();
	}
    
    public static boolean isValidWithZero(String s) {
        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1")&& !s.trim().equals("0"));
    }
    public static boolean isValidWithZeroAndOne(String s) {
        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1")&& !s.trim().equals("0") && !s.trim().equals("1"));
    }
    
    public static String formatForHtmlNew(String str) {
        String retStr = replaceChar(str, '\n', "<br>");
		Debug.println(retStr);
		retStr = replaceChar(retStr, '\t', "&nbsp;&nbsp;&nbsp;&nbsp");
		retStr = replaceChar(retStr, '\r', "&nbsp;");
        retStr = replaceSubString(retStr, "", "&quot;");
		retStr = replaceSubString(retStr, "&#8220;", "&quot;");
//		retStr = replaceChar(retStr, '?', "&quot;");
//		System.out.println(retStr);
        retStr = replaceSubString(retStr, "&#8221;", "&quot;");
        return retStr;
    }
    
    //Checking String array for Task Availability use, not for general purpose use.
         public static boolean isValidArray(String[] myArray){
      		boolean isValidArray = false;
      		String value = null;
      		if(myArray != null){
      			for(int i=0;i<myArray.length;i++){
      				value = myArray[i];
      				/*System.out.println("myArray["+i+"]="+value);
      				System.out.println("value.trim()!=\"111111111\"="+(value.trim().equals("111111111"));*/
      				if(StringUtil.isValidNew(value) && !value.trim().equals("111111111")){  //ZCUB-20150728-164
      					isValidArray = true;
      					break;
      				}
      			}
      		}
      		System.out.println("isValidArray="+isValidArray);
      		return isValidArray;
      	}
         /**
          * This method wii return the string removing last character find in the end of string
          * @param value1 : value1 object
          * @return String
          */       
                 public static String removeLastChar(String value1,String charValue){
                	value1 = value1.trim();
                    int pos              = -1;
                    if(value1!= null && !value1.equals("") && value1.endsWith(charValue)){
                        pos           = value1.lastIndexOf(charValue);
                        if(pos != -1){
                            value1     = value1.substring(0,pos);
                        }
                    }
                    
                    return value1;
                }
        /**
          * This method wii return the number of character in String last character find in the end of string
          * @param value1 : value1 object
          * @return String
          */
             public static int countOccurrences(String haystack, char needle){
                 char blanck = '\u0000';
                 return countOccurrences( haystack,  needle, blanck);
             }
             public static int countOccurrences(String haystack, char firstValue, char secondValue){
                int count = 0;
                 System.out.println("secondValue------------->>"+secondValue);
                 if(firstValue != '\u0000'){
                    for (int i=0; i < haystack.length(); i++){
                        if (haystack.charAt(i) == firstValue){
                            count++;
                        }
                    }
                }
                return count;
            }

             
/*
 * @author Banti Prajapati
 * @param Info info contains userno and user names
 */
   
             public static String toCommaSeparatedSpaceLess(Info elems)
         	{
            String temp="";	
         		if(elems==null || elems.size()==0){
         			return "";
         		}
         		StringBuffer sb = new StringBuffer();
         		for (int i=0;i<elems.size();i++)
         		{
         			if (i!=0) sb.append(",");
         			temp=(String)elems.getKey(i);
         			if(temp == null){
         				continue;
         			}
         			sb.append(temp);
         		}
         		return sb.toString();
         	}             
 
     /**
      * Method converts <b>passed array</b> to a string, separated by<br />
      * a defined <b>separator</b>.
      * 
      * @author Vivek Maurya
      * @date 06Aug2012         
      * @param passedArr : String array
      * @param separator : passed separator 
      */
     public static String getSeparatedString(String[] passedArr,String separator)
     {
    	 if(passedArr == null || passedArr.length == 0 || !isValid(separator))
    	 {
    		 return FieldNames.EMPTY_STRING;
    	 }
    	 
    	 StringBuffer str = new StringBuffer();
    	 int len = passedArr.length;
    	 
    	 for(int i=0; i<len; i++)
    	 {
    		 if(passedArr[i] == null)
    		 {
    			 continue;
    		 }
    		 if(i != 0)
    		 {
    			 str.append(separator);
    		 }
    		 str.append(passedArr[i]);
    	 }
    	 
    	 return str.toString();
     }
     
     /**
      * Count the occurrences of the substring in string s.
      * @param str string to search in. Return 0 if this is null.
      * @param sub string to search for. Return 0 if this is null.
      */
     public static int countOccurrencesOf(String str, String sub)
     {
         if (str == null || sub == null || str.length() == 0 || sub.length() == 0)
         {
             return 0;
         }
         int count = 0;
         int pos = 0;
         int idx;
         while ((idx = str.indexOf(sub, pos)) != -1)
         {
             ++count;
             pos = idx + sub.length();
         }
         return count;
     } 
     
     public static String encodeEscapeCharacter(String completeString,char tobehandled){

         StringBuffer completeFormattedString=new StringBuffer();
         int j=0;
         int count=0;
  		for (int i=0; i<completeString.length();i++){
                if(tobehandled==completeString.charAt(i)){
                    j=i-1;
                    count=0;
                    while(j>=0 && completeString.charAt(j)=='\\'){
                        j--;
                        count++;
                    }
                    for(int k=0;k<count;k++){
                        completeFormattedString.append("\\");
                    }
                    completeFormattedString.append("\\");
                    completeFormattedString.append(tobehandled);
                }else{
                    completeFormattedString.append(completeString.charAt(i));
                }
            }
  		return completeFormattedString.toString().trim();
  	}
     public static String decodeEscapeCharacter(String completeString,char tobehandled){

         StringBuffer completeFormattedString=new StringBuffer();
         int j=0;
         int count=0;
  		for (int i=0; i<completeString.length();i++){
                if(completeString.charAt(i)=='\\'){
                    j=i+1;
                    count=0;
                    while( j<completeString.length() && completeString.charAt(j)=='\\'){
                        j++;
                        count++;
                    }
                    if(completeString.length()>j && tobehandled==completeString.charAt(j)){
                        count=count--;
                        for(int k=0;k<count/2;k++){
                            completeFormattedString.append("\\");
                        }
                        i=j;
                    completeFormattedString.append(tobehandled);
                    }else{
                        completeFormattedString.append(completeString.charAt(i));
                    }
                }else{
                    if(completeString.charAt(i)!=tobehandled){
                        completeFormattedString.append(completeString.charAt(i));
                    }
                }
            }
  		return completeFormattedString.toString().trim();
  	}
     /*
      * @author Banti Prajapti
      */
public static String getBRSeperatedString(String toSplit,int splitIndex){
     	int l=toSplit.length();
     	String splitted ="";
     	if(l>splitIndex)
     		splitted=toSplit.substring(0,splitIndex)+"<br>"+getBRSeperatedString(toSplit.substring(splitIndex+1),splitIndex);
     	else
     		splitted = toSplit;
     	return splitted;
     }
/////////////SERVICE_MASTER_INT_ENH starts
public  static String getContentFromHtml(String htmlText)
{
	try
	{
		InputStream  in = new ByteArrayInputStream(htmlText.getBytes());
		Source source=new Source(in);
		htmlText=source.getRenderer().toString();
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	
	return htmlText;
	
}
/**
 * This method is used to change anchor text to plain text while displaying 
 * templates which are sent in mail with some particular link like Subscription link.
 * @author Dravit Gupta
 * @param content - Text from which anchor tag is to be reoved.
 * @param uniqueInAnchor - any unique keyword in href to select a particular anchor tag. 
 */
public static String replaceAnchorWithText(String content, String uniqueInAnchor) {
	if(content.indexOf(uniqueInAnchor) != -1) {
		if(!isValid(uniqueInAnchor)) {
			uniqueInAnchor = FieldNames.EMPTY_STRING;
		}
		Pattern p = Pattern.compile("<a.*"+uniqueInAnchor+".*\">");
		Pattern p1 = Pattern.compile("<a.*"+uniqueInAnchor+".*\">.*</a>");
		Matcher m = p.matcher(content);
		Matcher m1 = p1.matcher(content);
		String text = "";
		while(m.find()){
			text = m1.replaceAll(content.substring(m.end(), content.indexOf("</a>", m.end())));
		}
		return text;
	} else {
		return content;
	}
}

public static String replaceSpecialHtMlQuotes(String val){
	 if(val!=null){
       if(val.indexOf("&#8221;")!=-1)
           val=val.replaceAll("&#8221;","&#148;");
       if(val.indexOf("&#8217;")!=-1)
           val=val.replaceAll("&#8217;","&#146;");
     }else{
		 val="";
	 }
       return val;
}


public static String checkContentInString(String sourceString,String searchString,String searchRange)
{
	 StringTokenizer fqST = new StringTokenizer(searchString);
     StringBuffer sourceStringBuffer = new StringBuffer(sourceString);
     Pattern p = null;
     Matcher m = null;
     String count = "0";
     while(fqST.hasMoreTokens()){
         String nextToken = (String) fqST.nextToken();
         p = Pattern.compile("(?i:"+nextToken+")");
         m = p.matcher(sourceStringBuffer.toString()); // get a matcher object
         if(m.find()) {
        	 if("0".equals(searchRange))
        	 {
        		 count="1";
        		 return count;///in case of any key matched
        	 }
        	 count="3";/// when all keywords match
         }
         else
         {
        	 count="2";
    		 return count;///when some keys not matched
         }
     }
     return count;
}
////////////SERVICE_MASTER_INT_ENH ends
/**
 * @author Balvinder Mehla
 * to Transform Html  to Plain Text start if limit is  set to null then complete text will be returned
 * otherwise text will be trimmed.
 * content:Html Text
 * limit:number of Characters
 * 
 */
public static String htmlToSimpleText(String content,Integer limit)
{
	System.out.println("limit     \n\n\n\n\n\n    "+limit);
	if(StringUtil.isValid(content)){
		//script_code_in_view start
		  while(content.indexOf("<style")!=-1 && content.indexOf("</style>")!=-1 && content.indexOf("</style>",0)>content.indexOf("<style",0))
		  {
			  content=content.substring(0,content.indexOf("<style",0))+content.substring(content.indexOf("</style>",0)+8);
		  }
		  while(content.indexOf("<STYLE")!=-1 && content.indexOf("</STYLE>")!=-1 && content.indexOf("</STYLE>",0)>content.indexOf("<STYLE",0))
		  {
			  content=content.substring(0,content.indexOf("<STYLE",0))+content.substring(content.indexOf("</STYLE>",0)+8);
		  }
		  while(content.indexOf("<script")!=-1 && content.indexOf("</script>")!=-1 && content.indexOf("</script>",0)>content.indexOf("<script",0))
		  {
			  content=content.substring(0,content.indexOf("<script",0))+content.substring(content.indexOf("</script>",0)+8);
		  }
		  while(content.indexOf("<SCRIPT")!=-1 && content.indexOf("</SCRIPT>")!=-1 && content.indexOf("</SCRIPT>",0)>content.indexOf("<SCRIPT",0))
		  {
			  content=content.substring(0,content.indexOf("<SCRIPT",0))+content.substring(content.indexOf("</SCRIPT>",0)+8);
		  }
		//script_code_in_view end	 
		//docs_comment_in_view start
		  if(content.indexOf("<w:WordDocument>")!=-1)
			  {
				 content="";
			  }
		//docs_comment_in_view end
		 content = content.replaceAll("(?s)&lt;!--.*?--&gt;", "");//Franet-20131029-330 end
		 content =content.replaceAll("\r","");
		 content =content.replaceAll("\n","#####").trim();	  
         content = content.replaceAll("\\<.*?\\>", ""); 
         if(content.indexOf(">")!=-1)		//AWCAN-20160805-011	Udai Agarwal	08/08/2016
         {
        	 content = content.replaceAll("<(.|\n)*?>", "");
         }  
         content =content .replaceAll("&nbsp;","");
         content = content.replaceAll("(?s)<!--.*?-->", "");//Franet-20131029-330
         content =content.replaceAll("#####","<br>").trim();
     
     Pattern CRLF = Pattern.compile("(<br> )");
     Matcher m = CRLF.matcher(content);
     while(m.find())
     {
         content = m.replaceAll("<br>");
         m = CRLF.matcher(content);
     }
     
     CRLF = Pattern.compile("(<br><br><br>)");
     m = CRLF.matcher(content);
     while(m.find())
     {
         content = m.replaceAll("<br><br>");
         m = CRLF.matcher(content);
     }
   //P_FS_B_7305 starts
     content =content.replaceAll("<br>"," ");   
     content =content.replaceAll("	","");//tab replaced

     if(limit!=null &&content.length()>limit )
     {
          content=content.substring(0, limit-1);
          content=content+"...";
     }
	 }
	return content;
}
/**
 * This method removes<b> from the passed String
 * @param value
 * @return
 */
public static  String replaceBold(String value){
	String returnValue=FieldNames.EMPTY_STRING;
	if(StringUtil.isValid(value)){
		//returnValue=PortalUtils.replaceAll(value, "<b>","");
		//returnValue=PortalUtils.replaceAll(returnValue,"</b>","");
		returnValue=BaseUtils.replaceAll(value, "<b>","");    //For Product_Seperation_BL By Amar Singh.
		returnValue=BaseUtils.replaceAll(returnValue,"</b>","");   //For Product_Seperation_BL By Amar Singh.
	}
	return returnValue;
}
/**
 * Reverses percent and hash special notation
 */
public static String reversePercentAndHash(String val){

  	 if(val!=null){
           if(val.indexOf("@@PERC@")!=-1)
                 val=val.replaceAll("@@PERC@","%");
           if(val.indexOf("@@HASH@")!=-1)
                 val=val.replaceAll("@@HASH@","#");
           if(val.indexOf("@@QUES@")!=-1)
               val=val.replaceAll("@@QUES@","\\?");
           if(val.indexOf("@@SLASH@")!=-1)
               val=val.replaceAll("@@SLASH@","\\");
   	 }else{
   		 val="";
   	 }
           return val;
     } 
/**
 * Reverses "&" special notation
 * @param val
 * @return
 */
   public static String reverseAmpersandSpecial(String val){

     	 if(val!=null){
              if(val.indexOf("@@AMP@")!=-1)
                    val=val.replaceAll("@@AMP@","&");
               }else{
      		 val="";
      	 }
              return val;
        } 
   
   /**
    * Shows the dotted title if title too large 
    * @param str title
    *  @param lenStr subtitle length
    * @return dotted title
    * @author Ramu Agrawal
    */  
public static String showTitleName(String str, String lenStr){
	
	String str1=null;
	int len=Integer.parseInt(lenStr !=null ? lenStr : "0");
	if(isValid(str) && str.length()>len){
		str1=str.substring(0,len);
		str1+="...";
	}else {
		str1=str;
	}
	
	return str1;
}
 
/**
 * Shows the dotted title if title too large  End
 */
   

/**
 * This method returns the truncated string upto first space after specified limit
 * @param value: originalString,limit
 * @ returns: truncated string 
 */
public static String truncateString(String originalString,int limit)
{
	if(isValid(originalString))
	{
		try
		{
			if(originalString.length()>limit)
			{
				String requiredString=originalString.substring(0,limit);
				if(originalString.charAt(limit)!=' ')
				{
					String subOriginalStr = originalString.substring(limit,originalString.length());
					int value = subOriginalStr.indexOf(' ');
					if(value!=-1)
					{
						requiredString += originalString.substring(limit, value);	
					}
				}
				return requiredString;
			}
			else
			{
				return originalString;
			}
		}
		catch(Exception e)
		{
			return originalString;
		}
    }
	else
	{
           return FieldNames.EMPTY_STRING;        		
	}
} 
//PROVEN_MATCH_INTEGRATION : START
public static String covertDoubleToString(double d)
{
	   if(d == (int) d)
        return String.format("%d",(int)d);
    else
        return String.format("%s",d);
}

public static String convertArraylistToString(List<String> list)
{
	if(list == null){
		return "";
	}
	String listString = FieldNames.EMPTY_STRING;
	for (String s : list)
	{
	    listString += s.trim() + ",";
	}
	if(isValid(listString) && listString.endsWith(","))
	{
		listString = listString.substring(0, listString.length()-1);
	}
	return listString;
}
//PROVEN_MATCH_INTEGRATION : END
//CODEBASE_ISSUE IN_90010_13FEB15 STARTS
public static String isValidAttachment(String reportAttachment)
{
	String attachmentName="";
	String attachmentExt="";
	if(reportAttachment.lastIndexOf("_")!=-1)
	{
		attachmentName=reportAttachment.substring(0,reportAttachment.lastIndexOf("_"));
	}
	if(reportAttachment.lastIndexOf(".")!=-1)
	{
		attachmentExt=reportAttachment.substring(reportAttachment.lastIndexOf("."));
	}
	reportAttachment = attachmentName+attachmentExt;
	if(StringUtil.isValid(reportAttachment))
	{
		return reportAttachment;
	}
	else 
		return "";
 
}
//CODEBASE_ISSUE IN_90010_13FEB15 ENDS

/**
 * ZCUB-20150519-148
 * This method will return whole string having perticular characters in uppercase depending on the parameters passed.
 * @author Divanshu Verma
 * @param input
 * @param beginIndex
 * @param endIndex
 * @return String
 */
public static String capitalizeChars(String input,int beginIndex,int endIndex)
{
	String output=input.substring(beginIndex, endIndex).toUpperCase()+input.substring(endIndex);
	return output;
}
public static String toCommaSeparatedWithSQuotesWithoutSpace(Object[] elems){
	if(elems == null){
		return "";
	}
	StringBuffer sb = new StringBuffer();
	for (int i=0;i<elems.length;i++)
	{
		if(elems[i] == null || elems[i].equals("")){
			continue;
		}
		if (sb.length()>0) sb.append(", ");
		sb.append("'" + elems[i].toString().trim() + "'");
	}
	return sb.toString();
}

public static String revertBrackets(String val){

	 if(val!=null){
      
		 if(val.indexOf("&#40;")!=-1){
             val=val.replaceAll("&#40;","(");
		 }
		 if(val.indexOf("&#40")!=-1){
             val=val.replaceAll("&#40","(");
		 }
		 else if(val.indexOf("&#41;")!=-1){
             val=val.replaceAll("&#41;",")");
		 }
		 else if(val.indexOf("&#41")!=-1){
             val=val.replaceAll("&#41",")");
		 }
     }else{
		 val="";
	 }
       return val;
}


	/**
	 * This method will return the reverse value of StripXSS which is converted by XSSRequestWrapper.java .
	 */
	public static String reverseStripXSS(String value) {
		
		if (value != null && !value.isEmpty()) {
	
			value = value.replaceAll("&#38;", "&");
			value = value.replaceAll("&#60;", "<");
			value = value.replaceAll("&#62;", ">");
			value = value.replaceAll("&#37;", "%");
			value = value.replaceAll("&#40;", "\\(");
			value = value.replaceAll("&#41;", "\\)");
			value = value.replaceAll("&#61;", "=");
		}
		
		return value;
	}
	
	public static String string_to_slug(String str){
		str = str.toLowerCase();
		String from = "àáäâãåæèéëêìíïîòóöôõðøùúüûñçýÿºþ";
		String to   = "aaaaaaaeeeeiiiiooooooouuuuncyyop";
		for (int i=0, l=from.length() ; i<l ; i++) {
			str = str.replace(from.charAt(i),to.charAt(i));
		}
		return str;
	}
	//P_AB_24_05_2016 start
	public static String replaceSpecialCharactersWithOther(String sourceString, String replaceChar)
	{
		if(sourceString == null) return "";
		
		if(replaceChar == null ) return sourceString;
		
		int ascii;
		StringBuffer replacedString = new StringBuffer();
		
		if(isValid(sourceString)){
			char [] charArray = sourceString.toCharArray();
			
			for(char c : charArray)
			{
				ascii = (int) c;
				if((ascii > 64 && ascii < 91) || (ascii > 96 && ascii < 123) || (ascii > 47 && ascii < 58))
				{
					replacedString.append(c);
				}
				else
				{
					replacedString.append(replaceChar);
				}
			}
			return replacedString.toString();
		}
		else {
			return "";
		}
	}//P_AB_24_05_2016 ends
	/**
	 * This method returns the truncated string upto last space before specified limit and after that limit 3 dots will be appended
	 * @param value: originalString,limit
	 * @ returns: truncated string 
	 * @author Rohit Prajapati
	 */
	public static String truncateStringToLastSpaceAndAppendDots(String originalString,int limit)
	{
		if(isValid(originalString))
		{
			try
			{
				if(originalString.length()>limit)
				{
					String requiredString=originalString.substring(0,limit);
					if(originalString.charAt(limit)!=' ')
					{
						String subOriginalStr = originalString.substring(0,limit);
						int value = subOriginalStr.lastIndexOf(' ');
						if(value!=-1)
						{
							requiredString = originalString.substring(0, value);	
						}
					}
					return requiredString.trim()+"...";
				}
				else
				{
					return originalString;
				}
			}
			catch(Exception e)
			{
				return originalString;
			}
		}
		else
		{
			return FieldNames.EMPTY_STRING;        		
		}
	}
	
	public static String toCommaSeparatedSpaceLessWithValidData(String[] elems)
	{
		if(elems==null || elems.length==0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elems.length;i++)
		{
			if(!isValid(elems[i])){
				continue;
			}
			if (i!=0) sb.append(",");
			
			sb.append(elems[i].toString());
		}
		return sb.toString();
	}
	
	
	public static HashSet<String> getHastSetFromCommaSepStr(String commaSepStr){
		HashSet<String> set	= null;
		if (commaSepStr != null){
			set= new HashSet<String>();
			StringTokenizer stValues	= new StringTokenizer(commaSepStr,",");
			while(stValues.hasMoreTokens()){
				set.add(stValues.nextToken());
			}
		}
		return set;
	}
	
}

