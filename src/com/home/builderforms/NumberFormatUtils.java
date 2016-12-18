/*
---------------------------------------------------------------------------------------------------------------------------------
Version No.				Date		By			Against						Function Changed          	Comments
---------------------------------------------------------------------------------------------------------------------------------
BB_FIN_EXCH_B_40808		11Nov2008	YogeshT		--							formatNumber(,,)			- replace all comma if number string have. 
BBEH-20110728-005               29/09/2011      Narotam Singh
P_FS_B_2842				01Feb2012		Vivek Maurya		method overloaded to round the number up to configured decimal places, if the next digit after decimal is zero
P_FS_E_HandleNFE		02March 2012    Nishant Prabhakar   handle number format exception 
P_FIN_BUG_7277         27 March 2012    Himanshi Gupta        handle comma values on modify page.
---------------------------------------------------------------------------------------------------------------------------------
*/
package com.home.builderforms;

import java.util.*;
import java.text.NumberFormat;


 public class NumberFormatUtils
 {
        /**
	 * formatNumber function rounds of number as string to 2 decimal digits and returns string
	 */
	 //P_FS_B_2842 starts
	public static String formatCommaNumber(String number)
	{ 
         return formatCommaNumber(number,true);
    }
	
	public static String formatNoCommaNumber(String number)
	{ 
         return StringUtil.removeAllComma(formatCommaNumber(number,true));
    }
	
	public static String formatCommaNumber(String number,boolean doRound)
	{ 
            int minDigits,maxDigits,maxRoundedDigits=2;
            String maxRoundDigit=Constants.MAX_ROUNDED_DIGITS;
            String decimalValue = "";
            if(number.indexOf(".")!=-1)
            {
                decimalValue = number.substring(number.lastIndexOf(".")+1);
            }else{
                decimalValue = "0";
            }
            
            // Commented by Veerpal Singh on 07 Feb 2012 against Bug#4565
            
//            if(doRound)
//            {
//	            if(!decimalValue.equals("0"))
//	            {
//	                if(StringUtil.isValid(maxRoundDigit))
//	                {
//	                    maxRoundedDigits=Integer.parseInt(maxRoundDigit);
//	                }
//	            }
//            }
//            else
//            {
            	if(StringUtil.isValid(maxRoundDigit))
                {
                    maxRoundedDigits=Integer.parseInt(maxRoundDigit);
                }
//            }
            
            minDigits=maxDigits=maxRoundedDigits;
            return formatCommaNumber(number,minDigits,maxDigits);
            //BBEH-20110728-005:ends
    }
	//P_FS_B_2842 ends
	
	public static String formatCommaNumberField(String number) { 
		return formatCommaNumberField(number,true);
	}
	
	public static String formatCommaNumberField(String number,boolean doRound) { 
		int minDigits,maxDigits,maxRoundedDigits=2;
		String maxRoundDigit=Constants.MAX_ROUNDED_DIGITS;
		String decimalValue = "";
		if(number.indexOf(".")!=-1) {
			decimalValue = number.substring(number.lastIndexOf(".")+1);
		}else {
			decimalValue = "0";
		}
		if(StringUtil.isValid(maxRoundDigit)) {
			maxRoundedDigits=Integer.parseInt(maxRoundDigit);
		}
		minDigits=maxDigits=maxRoundedDigits;
		return formatCommaNumberField(number,minDigits,maxDigits);
    }
	
	public static String formatCommaNumber(String number,String decimalPlace)
	{
            return formatCommaNumber(number, 2, Integer.parseInt(decimalPlace));
    }
 /**
 * formatNumber function rounds of number as string to 2 decimal digits and returns string
 * @param number : number String 
 * @param minDigits : minDigits integer
 * @param maxDigits : maxDigits integer
 * @return String 
 */
	public static String formatCommaNumber(String number, int minDigits, int maxDigits)
	{
            if (number == null || number.equals(""))
                return "0.00";
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(maxDigits);
            nf.setMinimumFractionDigits(minDigits);
            //P_FS_E_HandleNFE Nishant starts 
            
            try{
            	number = nf.format(Double.parseDouble(number));
            }
            catch(NumberFormatException e){
            	return number;
            }
            //P_FS_E_Nishant 
            if(number.equals("-0.00"))
                return "0.00";
            else
                return number;
	}
	
	public static String formatCommaNumberField(String number, int minDigits, int maxDigits)
	{
            if (number == null || number.equals(""))
                return number;
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(maxDigits);
            nf.setMinimumFractionDigits(minDigits);
            try{
            	number = nf.format(Double.parseDouble(number));
            }
            catch(NumberFormatException e){
            	return number;
            }
            if(number.equals("-0.00"))
                return "0.00";
            else
                return number;
	}
	
	//P_FIN_BUG_7277 starts
	
	public static String formatCommaNumberModify(String number)
	{ 
		if(number.indexOf(',')!= -1){
		number = number.replaceAll(",","");
		}
		number= formatCommaNumber(number);
		return number.replaceAll(",","");
		
    }
	
	//P_FIN_BUG_7277 ends
	
	public static String formatCommaNumber1(String number) throws ConnectionException
	{	
		if(number == null || number.equals(""))
			return "0.00";

		NumberFormat nf			= NumberFormat.getInstance(Locale.US);
		number					= nf.format(Double.parseDouble(number));
		//BigDecimal bg			= new BigDecimal(number);
			//number				= bg.toString();

		try
		{
			StringTokenizer strTokenizer = new StringTokenizer(number, ".");
			int exponent=0;
			int zeros=0;
			String decimal="";

		if(number.indexOf('E')>1){
			exponent=Integer.parseInt(number.substring(number.indexOf('E')+1));
		}

			if (strTokenizer.countTokens() > 1){
				
				String no1 = strTokenizer.nextToken();
				String no2 = strTokenizer.nextToken();
					if(exponent!=0){
						decimal=number.substring(number.indexOf('.')+1,number.indexOf('E'));
						zeros=exponent-number.indexOf('E')+number.indexOf('.')+1;
						for(int i=0;i<zeros;i++) decimal=decimal+"0";
						no1=no1+decimal;
						no2="";
						number=no1;
		//				System.out.println("NUMBER:"+number+" EXPONENT"+exponent+"=="+no1+no2);
					}

						if (no2.length() == 0){
							number = number + ".00";
						}else if (no2.length() == 1){
							number = no1 + "." + no2 + "0";
						}else if (no2.length() == 2){
							////System.out.println("no1"+no2.charAt(1));
							number=number;
						}
				else if (no2.length() > 2){
					
					//modified by ajayk 0n 17/10/05  to round off a number like .135 to .14

					if (no2.charAt(2) >= '5' && (!(no2.charAt(1) == '9'))){
						number = no1 + "." + (Integer.parseInt(no2.substring(0,1))) + (Integer.parseInt(no2.substring(1,2)) + 1);
					}else if(no2.charAt(2) >= '5' && no2.charAt(1) == '9') {

					/*-modified by ajayk 0n 17/10/05  to round off a number like .135 to .14-*/
							if(no2.charAt(0) == '9'){
								////System.out.println("no1::"+no2.charAt(1));
								number = Integer.parseInt(no1) + 1 + "." + "00";
							}
							else{
								number = no1  + "." + (Integer.parseInt(no2.substring(0,1)) + 1) + "0";
							}
					}
					
					else{
						////System.out.println("no is:" + number);
						number = no1 + "." + (Integer.parseInt(no2.substring(0,1))) + (Integer.parseInt(no2.substring(1,2)));
					}
				}
			}
			else
			{
				number = number + ".00";
				////System.out.println("number"+number);
			}
		}
		catch (Exception e)
		{
			System.out.println("error"+e);
	//		Debug.print(e);
			//return nf.format(Float.parseFloat(number));
			return number;
		}
		if (number.equals(".00")){
			number="-";
		}else{ 
			//number = nf.format(Float.parseFloat(number));
			
		}
		return number;
	}


  /**
   * formatNumber function rounds of number as string to 2 decimal digits and returns string
   * @param number : number String
   * @return String
   */
	public static String formatNumber(String number)
	{
            return formatNumber(number, 2, 2);
        }

	public static String formatNumberField(String number)
	{
		int maxRound=Integer.parseInt(Constants.MAX_ROUNDED_DIGITS);
		if(!StringUtil.isValidWithZero(number)) {
			//number="0";
			return "";
		}
		return formatNumberField(number, maxRound, maxRound);
	}
  /**
   * formatNumber function rounds of number as string to 2 decimal digits and returns string
   * @param number : number String 
   * @param minDigits : minDigits integer
   * @param maxDigits : maxDigits integer
   * @return String 
   */
  
	public static String formatNumber(String number, int minDigits, int maxDigits)
	{		
		number = number.replaceAll(",","");//BB_FIN_EXCH_B_40808
		number = formatCommaNumber(number, minDigits, maxDigits);
		return number.replaceAll(",","");
	}
	
	public static String formatNumberField(String number, int minDigits, int maxDigits)
	{		
		number = number.replaceAll(",","");
		number = formatCommaNumberField(number, minDigits, maxDigits);
		return number.replaceAll(",","");
	}

/**
 * formatNumber function rounds of number in float to 2 decimal digits and returns string
 * @param numberInDouble : numberInDouble object
 * @return String
 */
	
	public static String formatNumber(double numberInDouble)
	{
		String number = Double.toString(numberInDouble);
                number = formatNumber(number, 2, 2);
		return number;
	}
/**
 * formatNumber function rounds of number in float to 2 decimal digits and returns string
 * @param numberInFloat : numberInFloat object
 * @return String 
 */
	public static String formatNumber(float numberInFloat)
	{
		String number = Float.toString(numberInFloat);
                number = formatNumber(number, 2, 2);
		return number;
	}
	/**
	 * formatNumber function rounds of number in float to 2 decimal digits and returns float
	 * @param numberInFloat : numberInFloat  Float          
	 * @return float
	 */
	public static float formatNumberToFloat(float numberInFloat)
	{
		String number = Float.toString(numberInFloat);
                number = formatNumber(number, 2, 2);
		return Float.parseFloat(number);
	}

	/**
	 * formatNumber function rounds of number in float to 2 decimal digits and returns float
	 * @param numberInFloat  : numberInFloat object
	 * @return Double
	 */
	public static double formatNumberToDouble(double numberInFloat)
	{
		String number = Double.toString(numberInFloat);
                number = formatNumber(number, 2, 2);
		return Double.parseDouble(number);
	}
/**
 * this method add zeroes to the right side of string 
 * @param s : s object
 * @param j : j integer
 * @return String
 */
	public static String addZeroesRight(String s,int j)
        {
            int diff=j;
            if(diff>0){
                int i=0;
                while(i<diff){
                        s=s + "0";
                        i++;
                }
            }
            return s;
	}
/**
 * this method add zeroes to the left side of string 
 * @param s : s object
 * @param j : j integer
 * @return String
 */
	public static String addZeroesLeft(String s,int j)
        {
            int x=s.length();
            int diff=j-x;
            if(diff>0){
                int i=0;
                while(i<diff){
                        s="0"+s;
                        i++;
                }
            }
            return s;
	}
	
	//TDG-MOBILE-20101221 starts
	public static boolean isNumeric(String techID){
        String number="1234567890";
        int matchlength=techID.length();
        int count=0;
        char[] charseq=number.toCharArray();
        char[] checkvalue=techID.toCharArray();
        for (int i = 0; i < charseq.length; i++) {
            for (int j = 0; j < checkvalue.length; j++) {
                if(charseq[i] == checkvalue[j]){
                    count++;
                    continue;
                }
            }
        }
        if(count == matchlength){
            return true;
        }
                    
        return false;
        }
	//TDG-MOBILE-20101221 ends
	  /**
	   * formatNumber function rounds of number as string to 2 decimal digits and returns string
	   * @param number : number String
	   * @return String
	   */
		public static String formatNumberWithDecimal(String number)//P_FS_B_6132
		{
			int maxRound=Integer.parseInt(Constants.MAX_ROUNDED_DIGITS);
			if(!StringUtil.isValidWithZero(number))
			{
				number="0";
			}
				return formatNumber(number, maxRound, maxRound);
	    }
		public static String formatNumberWithDecimal(double number)//P_FS_B_6132
		{
			String tempNum=String.valueOf(number);
			return formatNumberWithDecimal(tempNum);
	    }
		/**
		   * formatNumberWithoutDecimal function formats number as string without decimal digits and returns string
		   * @param number : number String
		   * @return String
		   */
		public static String formatNumberWithoutDecimal(String number)
		{
			 NumberFormat nf = NumberFormat.getInstance(Locale.US);
	            
	            
	            try{
	            	number = nf.format(Double.parseDouble(number));
	            	}
	            catch(NumberFormatException e){
	            	return number;
	            }
	           return number;
		}
		
		
	public static String formatIntegerNumberForComma(String number)
	{
		if (number == null || number.equals(""))
			return number;
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		try{
			number = nf.format(Integer.parseInt(number));
		}
		catch(NumberFormatException e){
			return number;
		}
		return number;
	}		

 
 
 }
