package com.home.builderforms;

/**
*
* Class Name	- AESencrp
* Location		- com.appnetix.app.portal.util.AESencrp
* @author		- Rohit Jain
* @version		- $1.1
* $Date			- 07/09/2013 
* for BBEH_FOR_SMC__OPTIMIZATION
This class convert the String into decrpted and encrypted form.
*/


import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;
import org.apache.log4j.Logger;

public class AESencrypt {
	
	static	Logger logger = Logger.getLogger(AESencrypt.class);
     private static final String ALGO = "AES";
     
     //This all are the key for generting the encrypted string.
     private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'N', 'e', 'w', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y','s' };
     

     /**
      * This function returns the value of String in encrypted form
      * by using Cipher. 
      * @author -Rohit Jain.
      * @param Data : as an String
      * @return String
      */
   public static String encrypt(String Data) throws Exception {
       
	   String encryptedValue="";
	   try{ 
	   Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
         //encryptedValue = new BASE64Encoder().encode(encVal);
       }catch(Exception e)
       {
    	   logger.info("Exception in AESencrpn in method encrypt when converting in encryptform:::"+e.getMessage());
       }
       
        return encryptedValue;
    }

   
   /**
    * This function returns the value of String in decrypted form
    * by using Cipher. 
    * @author -Rohit Jain.
    * @param Data : as an String
    * @return String
    */
    public static String decrypt(String encryptedData) throws Exception {
        
    	String decryptedValue  ="";
    try{	
    	Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
       /* byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
         decryptedValue = new String(decValue);*/
      }catch(Exception e){
   	   logger.info("Exception in AESencrpn in method decrypt when converting in decrypt form:::"+e.getMessage());
      }
    
        return decryptedValue;
    }
    
    
    /**
     * This function returns the key generting form SecretKeySpec.
     * by using Cipher. 
     * @author -Rohit Jain.
     * @return Key
     */
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
}

}

