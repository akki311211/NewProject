/**
*  @(#)StateHandlerSupport.java        1.00 25/01/2001
*  Copyright (c) Webrizon eSolutions Limited.
*  B-31 Sector-5, Noida 201 301,India.
*  All rights reserved.
*/

package com.home.builderforms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import com.home.builderforms.AdminMgr;

import org.apache.log4j.Logger;

import com.home.builderforms.BaseDAO;
import com.home.builderforms.TriggerMgr;
import com.home.builderforms.TriggerDAO;
import com.home.builderforms.BaseLibraryUtil;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.DateUtil;
import com.home.builderforms.DivisionUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.TableAnchors;
import com.home.builderforms.TriggerUtil;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.NumberFormatUtils;

/** P_FIM_E_DEFAULTMAILID_1, 04/12/2006, Sunilk, Enhancement, sendTriggerMail(),for getting default CC and BCC ids set from admin for FIM. 
P_FIM_B_69209          13 April2011      Prakash Jodha                               day prior and day after triggeres with or without selected users
P_MAIL_FORMAT  02/09/2011   Narotam Singh 
P_FIM_B_3937    30 jan 2012          prakash jodha                    changes done to remove null pointer exception
P_FIM_B_8020    11 April 2012        Megha Chitkara                   changed for implementing internationalization
 */


public class TriggerHelper {
    static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(TriggerHelper.class);

	/*
	 * Modified by Sunilk on 25 May 2006 for getting action type in field triggers
	 */
    

    public boolean sendFieldTriggers (  String pTriggerFormName,
							    Info infoToBeUpdated, 
							    Info infoBeforeUpdate,
							    Integer franchiseeNo,
							    Integer modifierUserNo,
								int actionType,Info convertedInfo,Info triggerInfoAfterUpdate) {
	try {
		
	    logger.info("Attempting to send field triggers");
	    logger.info("infoBeforeUpdate: " + infoBeforeUpdate);
	    logger.info("\n\n\ninfoToBeUpdate: " + infoToBeUpdated+"\n\n\n");
	    logger.info("\n\n\n: Trigger Info after update::::::::"+triggerInfoAfterUpdate);
	    TriggerDAO dao = TriggerMgr.newInstance().getTriggerDAO();
	    String tempTriggerFormName = pTriggerFormName;
	    if("Franchisee Location".equals(pTriggerFormName) || "Store".equals(pTriggerFormName) ){
	    	pTriggerFormName = SQLUtil.getColumnValue("TRIGGER_CONFIG","FORM_NAME", "INTERNAL_NAME","Center Info");
	    }
	    SequenceMap triggerFieldMap	= dao.getFieldDetail(pTriggerFormName);
	    ArrayList infoList = null;
	    int listCount;
	    if(infoBeforeUpdate==null){
	    	infoBeforeUpdate= new Info();
	    }
            if (triggerFieldMap == null) {
		return true;
	    }
            
            if(triggerInfoAfterUpdate!=null){
            	triggerInfoAfterUpdate.setAll(convertedInfo);//BB-20141006-161
            }
            else {
            	triggerInfoAfterUpdate = new Info();
            	triggerInfoAfterUpdate.setAll(convertedInfo);//BB-20141006-161
            }
            
            String tableName = infoToBeUpdated.get(FieldNames.TABLE_ANCHOR);
	    Iterator iter = infoToBeUpdated.getKeySetIterator();
            while (iter.hasNext()) {
            	listCount=0;
		String fieldName = (String) iter.next();
             if (FieldNames.TABLE_ANCHOR.equals(fieldName)) {
		    continue;
		}
             
             String triggerTable = tableName;
             if("Franchisee Location".equals(tempTriggerFormName) || "Store".equals(tempTriggerFormName)){
            	 triggerTable = TableAnchors.FRANCHISEES;
             }
             infoList=(ArrayList)triggerFieldMap.get(triggerTable + "." + fieldName);
             
             if(infoList!=null){
            	while (listCount<infoList.size()) {
					
             Info triggerInfo = (Info) infoList.get(listCount);
             logger.info("triggerInfo::: " + triggerInfo);
             listCount++;
             //triggerInfo.set("modifierUserNo",modifierUserNo);//P_E_FS_MAIl_HISTORY Nishant   //P_FIM_B_3937 
		if (triggerInfo != null) {
             triggerInfo.set("modifierUserNo",modifierUserNo);//P_E_FS_MAIl_HISTORY Nishant     //P_FIM_B_3937
		    Integer triggerOnOff = (Integer) triggerInfo.getObject(FieldNames.TRIGGER_ONOFF);
		    if (triggerOnOff.intValue() == 1) {
		    //P_FIM_B_69209 	      
		     if(actionType==0 && !"#DAYS-PRI#".equals(triggerInfo.get(FieldNames.EVENT))&& !"#DAYS-AFT#".equals(triggerInfo.get(FieldNames.EVENT)) && !"#EQA#".equals(triggerInfo.get(FieldNames.EVENT)))
		    	 triggerInfo.set(FieldNames.EVENT, "#ADD#");
		     triggerInfo.set("valueAfterUpdate",infoToBeUpdated.get(fieldName));
			String event = triggerInfo.get(FieldNames.EVENT);
                        logger.info("Event: " + event);
			if ("#MOD#".equals(event) || "#ADD#".equals(event)) {
			    String valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			    String valueAfterUpdate = infoToBeUpdated.get(fieldName);
			    String valueAfterconverted=convertedInfo.get(fieldName);
                            // Bug ID : 57843 By Nikhil Verma
                         
                            if("".equals(valueAfterUpdate)){
                                valueAfterUpdate= PortalUtils.getPrivateFieldFormat(valueAfterUpdate);
                                valueBeforeUpdate= PortalUtils.getPrivateFieldFormat(valueBeforeUpdate);
                            }
                            
                          //dki-20160912-591 Starts
                            if("#ADD#".equals(event)) {
                				if(valueBeforeUpdate == null && "-1".equals(valueAfterUpdate)) {
                					valueBeforeUpdate = "";
                				}
                			}
                            //dki-20160912-591 Ends
                            
                            //g6-20161006-255 Udai Agarwal Starts
                            if("Double".equals(triggerInfo.getString(FieldNames.ACTUAL_DATA_TYPE)))
                			{
                				if(StringUtil.isValid(valueBeforeUpdate))
                				{
                					valueBeforeUpdate = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueBeforeUpdate)));
                				}
                				if(StringUtil.isValid(valueAfterUpdate))
                				{
                					valueAfterUpdate = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueAfterUpdate)));
                				}
                				if(StringUtil.isValid(valueAfterconverted))
                				{
                					valueAfterconverted = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueAfterconverted)));
                				}
                			}
                            //g6-20161006-255 Udai Agarwal Starts
                            
                         if (valueAfterUpdate != null && valueAfterconverted!=null && !valueAfterUpdate.equals(valueBeforeUpdate) && !valueAfterconverted.equals(valueBeforeUpdate)) {
				logger.info("#MOD# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate+" afterconverted: "+convertedInfo.get(fieldName));
				triggerInfo.set("valueAfterUpdate", convertedInfo.get(fieldName));
				sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
			    }
			}
			if ("#ERA#".equals(event)) {
			    String valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			    String valueAfterUpdate = infoToBeUpdated.get(fieldName);
			    if (valueAfterUpdate != null && valueBeforeUpdate != null && !valueBeforeUpdate.equals("")) {
				logger.info("#ERA# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate);
				sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
			    }
			}
			if ("#EQA#".equals(event)) {
			    String valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			    String valueAfterUpdate = infoToBeUpdated.get(fieldName);
			    String valueAfterconverted=convertedInfo.get(fieldName);
			    if (valueAfterUpdate != null && valueAfterconverted!=null && valueAfterconverted!=null  && !valueAfterUpdate.equals(valueBeforeUpdate) && !valueAfterconverted.equals(valueBeforeUpdate) ) {
			    	
				logger.info("#EQA# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate+" afterconverted: "+convertedInfo.get(fieldName));
				String valueToCompare		= ","+triggerInfo.get(FieldNames.VALUE_TO_COMPARE)+",";
				logger.info("Value To Compare:::::::"+valueToCompare);
				if (valueToCompare.trim().contains(","+valueAfterUpdate.trim()+",") || valueToCompare.trim().contains(","+valueAfterconverted.trim()+",") ) {	
					triggerInfo.set("valueAfterUpdate", convertedInfo.get(fieldName));
				    sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
				}
			    }
			}
			if ("#EXC#".equals(event) || "#LTH#".equals(event) || "#GTH#".equals(event)) {
			
			    String valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			    String valueAfterUpdate = infoToBeUpdated.get(fieldName);
			    logger.info("#INT# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate);
			    if (valueAfterUpdate != null && !valueAfterUpdate.equals(valueBeforeUpdate)) {
			    
				int valueToCompare = Integer.parseInt(triggerInfo.get(FieldNames.VALUE_TO_COMPARE));
				logger.info("valueToCompare: " + valueToCompare);
			
				double nValueAfterUpdate		= Double.parseDouble(valueAfterUpdate);
			
				if ("#EXC#".equals(event) && (nValueAfterUpdate > valueToCompare)) {
				    sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
				}
				if ("#LTH#".equals(event) && (nValueAfterUpdate < valueToCompare)) {
				    sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
				}
				
				if ("#GTH#".equals(event) && (nValueAfterUpdate > valueToCompare)) {
				    sendTriggerMail(triggerInfo, franchiseeNo,triggerInfoAfterUpdate);//BB-20141006-161
				}
			    }
			}
		    }
		    
		    Integer auditingOnOff = (Integer) triggerInfo.getObject(FieldNames.AUDITING_ONOFF);
	            if (auditingOnOff.intValue() == 1) {
			String valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			String valueAfterUpdate	 = infoToBeUpdated.get(fieldName);
			String valueAfterconverted= convertedInfo.get(fieldName);
			
			//g6-20161006-255 Udai Agarwal Starts
			if("Double".equals(triggerInfo.getString(FieldNames.ACTUAL_DATA_TYPE)))
			{
				if(StringUtil.isValid(valueBeforeUpdate))
				{
					valueBeforeUpdate = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueBeforeUpdate)));
				}
				if(StringUtil.isValid(valueAfterUpdate))
				{
					valueAfterUpdate = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueAfterUpdate)));
				}
				if(StringUtil.isValid(valueAfterconverted))
				{
					valueAfterconverted = Double.toString(NumberFormatUtils.formatNumberToDouble(Double.parseDouble(valueAfterconverted)));
				}
			}
			//g6-20161006-255 Udai Agarwal Starts
			
                       if (StringUtil.isValidNew(valueAfterUpdate) && !valueAfterUpdate.equals(valueBeforeUpdate) &&  !valueAfterconverted.equals(valueBeforeUpdate)) {
			    logger.info("#MOD# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate+" afterconverted: "+convertedInfo.get(fieldName));
			    Info auditInfo		= new Info();
			    auditInfo.setIDField(new String[]{FieldNames.AUDIT_ID});
			    auditInfo.set(FieldNames.ENTITY_ID, franchiseeNo);
				/*
				* Modified by Sunilk on 25 May 2006 for getting action type in field triggers
				*/
			    if(actionType==0)
					auditInfo.set(FieldNames.EVENT, "#ADD#");
				else
					auditInfo.set(FieldNames.EVENT, "#MOD#");
			    auditInfo.set(FieldNames.DATE_TIME,
				    
			    new Timestamp(Calendar.getInstance().getTimeInMillis()));
			    auditInfo.set(FieldNames.USER_NO, modifierUserNo);
			    auditInfo.set(FieldNames.FORM_NAME, pTriggerFormName);
			    auditInfo.set(FieldNames.TABLE_NAME, tableName);
			    auditInfo.set(FieldNames.FIELD_NAME, fieldName);
			    auditInfo.set(FieldNames.VALUE_BEFORE_UPDATE, valueBeforeUpdate);
			    auditInfo.set(FieldNames.VALUE_AFTER_UPDATE, convertedInfo.get(fieldName));
			
			    BaseDAO auditDAO	= new BaseDAO(TableAnchors.AUDITING);
			    
			    // P_B_61364 STARTS
			    if(StringUtil.isValid(String.valueOf(franchiseeNo))){
			    	auditDAO.create(auditInfo);
			    }
			    // P_B_61364 ENDS
			    
			    
			    logger.info("auditInfo: " + auditInfo);
			}

			valueBeforeUpdate = infoBeforeUpdate.get(fieldName);
			valueAfterUpdate  = infoToBeUpdated.get(fieldName);
			
			/**
			 * Identity updated value as null and its previous data was valid then this case also applicable for Audit history
			 * If null value not converted to "" then below condition never become true when previously inputted data has valid input
			 * @author abhishek gupta
			 * @date 17 Aug 2012  
			 */
			if(!StringUtil.isValidNew(valueAfterUpdate)) {
				if(StringUtil.isValid(valueBeforeUpdate)) {
					valueAfterUpdate = "";
				}
			}
			if (valueAfterUpdate != null && valueAfterUpdate.trim().equals("") && valueBeforeUpdate != null && valueAfterconverted!=null && !valueBeforeUpdate.equals("") &&!valueAfterUpdate.equals(valueBeforeUpdate) &&  !valueAfterconverted.equals(valueBeforeUpdate) ) {
			    logger.info("#ERA# valueBeforeUpdate: " + valueBeforeUpdate + " valueAfterUpdate: " + valueAfterUpdate);
			    Info auditInfo		= new Info();
			    auditInfo.setIDField(new String[]{FieldNames.AUDIT_ID});
			    auditInfo.set(FieldNames.ENTITY_ID, franchiseeNo);
				/*
				* Modified by Sunilk on 25 May 2006 for getting action type in field triggers
				*/
			    if(actionType==0)
					auditInfo.set(FieldNames.EVENT, "#ADD#");
				else
					auditInfo.set(FieldNames.EVENT, "#MOD#");
			    auditInfo.set(FieldNames.DATE_TIME,
			    new Timestamp(Calendar.getInstance().getTimeInMillis()));
                            if(fieldName != null && !fieldName.trim().equals("") && !fieldName.equalsIgnoreCase("null") && (fieldName.equalsIgnoreCase("Taxpayer ID/FEIN") || fieldName.equalsIgnoreCase("Taxpayer ID")))
                            {
                                valueBeforeUpdate = PortalUtils.getPrivateFieldFormat(valueBeforeUpdate);
                                valueAfterUpdate = PortalUtils.getPrivateFieldFormat(valueAfterUpdate);
                            }    
                            auditInfo.set(FieldNames.USER_NO, modifierUserNo);
			    auditInfo.set(FieldNames.FORM_NAME, pTriggerFormName);
			    auditInfo.set(FieldNames.TABLE_NAME, tableName);
			    auditInfo.set(FieldNames.FIELD_NAME, fieldName);
                            auditInfo.set(FieldNames.VALUE_BEFORE_UPDATE, valueBeforeUpdate);
			    auditInfo.set(FieldNames.VALUE_AFTER_UPDATE, valueAfterUpdate);
			    BaseDAO auditDAO	= new BaseDAO(TableAnchors.AUDITING);
			    auditDAO.create(auditInfo);
			    logger.info("auditInfo: " + auditInfo);
			}
		    }
		}
             }
	    }
            }
	}
	catch (Exception e) {
	    logger.error("Exception: ", e);
	    e.printStackTrace();
	}
	return true;
    }
    
    public boolean sendFormTriggers(String pTriggerFormName, 
							int actionType, 
							Integer franchiseeNo, 
							Integer modifierUserNo) {
    	String tempTriggerFormName = pTriggerFormName;
	    if("Franchisee Location".equals(pTriggerFormName) || "Store".equals(pTriggerFormName)){
	    	pTriggerFormName = SQLUtil.getColumnValue("TRIGGER_CONFIG","FORM_NAME", "INTERNAL_NAME","Center Info");
	    }
	logger.info("Form triggers");
	logger.info("Event Type: " + actionType);
	logger.info("Form Name: " + pTriggerFormName);
	if (pTriggerFormName == null) {
	    return true;
	}
	logger.info("Attempting to send triggers");
	try {
	    TriggerDAO dao = TriggerMgr.newInstance().getTriggerDAO();
	    Info info = dao.getTriggerDetail(pTriggerFormName);
	    if(info != null && actionType  == 1 && StringUtil.isValid(info.get(FieldNames.EVENT)) && (info.get(FieldNames.EVENT).indexOf("#ADD#")!=-1 || info.get(FieldNames.EVENT).indexOf("#MOD#")!=-1)){//P_B_35814
	    	info.set(FieldNames.EVENT, "#MOD#");
	    }
	    logger.info("info: " + info);
	    if (info != null) {
	    	
		Integer triggerOnOff = (Integer) info.getObject(FieldNames.TRIGGER_ONOFF);
		if (triggerOnOff.intValue() == 1) {
		    String triggerEvent	= info.get(FieldNames.EVENT);
		    logger.info("triggerEvent: " + triggerEvent);
		    boolean sendTriggerMail = false;
		    if ((actionType ==  0 && triggerEvent.indexOf("#ADD#") != -1)||( actionType ==  1 && triggerEvent.indexOf("#MOD#") != -1) ) {
			sendTriggerMail	= true;
			logger.info("Create triggers");
		    }
		    if (actionType ==  2 && triggerEvent.indexOf("#DEL#") != -1) {
			sendTriggerMail = true;
			logger.info("Delete triggers");
		    }
		    if (sendTriggerMail) {
			boolean flag = sendTriggerMail(info, franchiseeNo,null);
		    }
		}
		//System.out.println("info:::::....:::..::..:://..."+info);
		Integer auditingOnOff = (Integer) info.getObject(FieldNames.AUDITING_ONOFF);
		
		
		
		
		if (auditingOnOff.intValue() == 1) {
			
		
			Info auditInfo = new Info();
		    auditInfo.setIDField(new String[]{FieldNames.AUDIT_ID});
		    auditInfo.set(FieldNames.ENTITY_ID, franchiseeNo);
		    if (actionType == 0) {
			auditInfo.set(FieldNames.EVENT, "#ADD#");
		    }
		    else if (actionType == 1) {
			auditInfo.set(FieldNames.EVENT, "#MOD#");
		    }
		    else {
			auditInfo.set(FieldNames.EVENT, "#DEL#");
		    }
		    auditInfo.set(FieldNames.DATE_TIME, new Timestamp(Calendar.getInstance().getTimeInMillis()));
		    auditInfo.set(FieldNames.USER_NO, modifierUserNo);
		    auditInfo.set(FieldNames.FORM_NAME, pTriggerFormName);
		    auditInfo.set(FieldNames.TABLE_NAME, "-1");
		    
		    BaseDAO auditDAO = new BaseDAO(TableAnchors.AUDITING);
		    
		    // P_B_61364 STARTS
		    if(StringUtil.isValid(String.valueOf(franchiseeNo))){
		    	auditDAO.create(auditInfo);
		    }
		    // P_B_61364 ENDS
		    
		    logger.info("auditInfo: " + auditInfo);
		    
		}
		
	   }
	    return true;
	}
	catch (Exception e) {
	    logger.error("Exception: ", e);
	    e.printStackTrace();
	    return false;
	}
    }
    
    public boolean sendTriggerMail(Info info, Integer franchiseeNo,Info triggerInfoAfterUpdate) throws com.home.builderforms.mail.MailingException{
    	
    	String userNo = info.getString(FieldNames.USER_NO);
	String modifierUserNo = info.getString("modifierUserNo");//P_E_FS_MAIl_HISTORY Nishant
	String triggerID=info.getString(FieldNames.TRIGGER_ID);
	String type=info.getString("type");
	HashMap alertMsgMap= TriggerDAO.getAlertTextAndSubject(triggerID, type);
	String alertMessage = StringUtil.isValid((String)alertMsgMap.get("mailText"))?(String)alertMsgMap.get("mailText"):"";
	String alertMsgSubject=StringUtil.isValid((String)alertMsgMap.get("mailSubject"))?(String)alertMsgMap.get("mailSubject"):"";
	String selectedFields=StringUtil.isValid((String)alertMsgMap.get("selectedFields"))?(String)alertMsgMap.get("selectedFields"):"";;
	String fromMailID = null;
	try {
		
		String fromData[]   = 	(String[])NewPortalUtils.getFromID();
		if(fromData!=null && fromData.length>0 && fromData[0]!=null && !fromData[0].equals(""))
		{
			fromMailID = fromData[1]; 
		}

		
	}catch(Exception e){
		logger.error("problem in getting configured email id for trigger sending");
		logger.info("Exception ::"+ e);
	}
	HashMap baseAdminDetailMap = TriggerUtil.getBaseAdminDetail();
	
	if (fromMailID == null || fromMailID.equals("")) {
		fromMailID = (String) baseAdminDetailMap.get("baseAdminName")+" <"+baseAdminDetailMap.get("emailId")+">";
	}
	BaseDAO baseDAO	 = new BaseDAO(TableAnchors.FRANCHISEE);
	String franchiseeName	 = "";
	try {
	    franchiseeName = (String) baseDAO.getFieldValue(FieldNames.FRANCHISEE_NAME, new Integer[]{franchiseeNo});
	}
	catch (Exception e) {
	    logger.error("Exception: ", e);
	}
	logger.info("	: " + franchiseeName);
	logger.info("baseAdminName: " + fromMailID);
	
	
	int i = 0;
	StringTokenizer st = new StringTokenizer(userNo.toString() ,",");
	String[] alertUsers = new String[st.countTokens()];
	Map<String,String> userMap = NewPortalUtils.getNewHashMapWithKeyValueType();

	SequenceMap map = new SequenceMap(); 
	String href=MultiTenancyUtil.getTenantConstants().HOST_NAME+"fimTriggerMail.jsp?franchiseeNo="+StringUtil.encodeORdecodeString(Integer.toString(franchiseeNo),"encode");
	String formName="<a href='"+href+"'>" +LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)) +"</a> ";
	//map=FIMMgr.newInstance().getFIMDAO().getCenterInfo(franchiseeNo.toString());
	map = BaseLibraryUtil.newInstance().getCenterInfo(franchiseeNo.toString());    // For Product_Seperation_BL By Amar Singh.
	String divisionId = DivisionUtil.getDivisionIdsForFranchisee(franchiseeNo.toString());
	
	String areaID=(String)map.get("AREA_ID");
	map.remove("AREA_ID");
	map.put("FORM_NAME", info.getString(FieldNames.FORM_NAME)!=null? LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)):FieldNames.EMPTY_STRING);
    map.put("FRANCHISE_ID", franchiseeName!=null? franchiseeName:FieldNames.EMPTY_STRING);
	userMap.put("FRANCHISE_ID", franchiseeName!=null? franchiseeName:FieldNames.EMPTY_STRING);
    userMap.put("CENTER_NAME", map.get("CENTER_NAME")!=null? (String)map.get("CENTER_NAME"):FieldNames.EMPTY_STRING);
    userMap.put("CONTACT_NAME", map.get("CONTACT_NAME")!=null? (String)map.get("CONTACT_NAME"):FieldNames.EMPTY_STRING);
    userMap.put("ADDRESS", map.get("ADDRESS")!=null? (String)map.get("ADDRESS"):FieldNames.EMPTY_STRING);
    userMap.put("CITY",  map.get("CITY")!=null? (String)map.get("CITY"):FieldNames.EMPTY_STRING);
    userMap.put("STATE", map.get("STATE")!=null? (String)map.get("STATE"):FieldNames.EMPTY_STRING);
    userMap.put("ZIPCODE", map.get("ZIPCODE")!=null? (String)map.get("ZIPCODE"):FieldNames.EMPTY_STRING);
    userMap.put("EMAIL_ID", map.get("EMAIL_ID")!=null? (String)map.get("EMAIL_ID"):FieldNames.EMPTY_STRING);
    userMap.put("PHONE1", map.get("PHONE1")!=null? (String)map.get("PHONE1"):FieldNames.EMPTY_STRING);
    userMap.put("MOBILE", map.get("MOBILE")!=null? (String)map.get("MOBILE"):FieldNames.EMPTY_STRING);
    userMap.put("COUNTRY", map.get("COUNTRY")!=null? (String)map.get("COUNTRY"):FieldNames.EMPTY_STRING);
    userMap.put("FAX", map.get("FAX")!=null? (String)map.get("FAX"):FieldNames.EMPTY_STRING);
    userMap.put("FIELD_NAME", info.get(FieldNames.DISPLAY_FIELD_NAME)!=null? (String)info.get(FieldNames.DISPLAY_FIELD_NAME):FieldNames.EMPTY_STRING);
    userMap.put("FIELD_VALUE", info.get("valueAfterUpdate")!=null? (String)info.get("valueAfterUpdate"):FieldNames.EMPTY_STRING);
    userMap.put("FORM_NAME", info.getString(FieldNames.FORM_NAME)!=null? LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)):FieldNames.EMPTY_STRING);
    String newAlertSubject=BaseLibraryUtil.newInstance().replaceKeywords(alertMsgSubject, userMap);    // For Product_Seperation_BL By Amar Singh.

    userMap.put("FORM_NAME", formName!=null? formName:FieldNames.EMPTY_STRING);
    
    String newAlertMessage=BaseLibraryUtil.newInstance().replaceKeywords(alertMessage, userMap);    // For Product_Seperation_BL By Amar Singh.
	while(st.hasMoreElements()){
	    alertUsers[i]=st.nextToken().trim();
	    i++;
	}
	try{
	String emailId	 = null;
	String mailsSentTo = "";
	String newMailsSentTo = "";
	boolean flag	 = false;

	
	logger.info("alertMessage: " + newAlertMessage);
	
	//System.out.println("info..... in triggerHelper..>"+info);
	String alertSubject = "";
	if("Log Call".equals(info.getString(FieldNames.FORM_NAME)) || "Log Task".equals(info.getString(FieldNames.FORM_NAME)))
		alertSubject = LanguageUtil.getString("FIM Trigger Alert",null,Constants.KEYVAL_FIM)+", " +LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)) + " " + LanguageUtil.getString("for") + " " + LanguageUtil.getString(Constants.FRANCHISE_LABEL)+": "+franchiseeName; //P_FIM_B_8020  
	else
		alertSubject = LanguageUtil.getString("FIM Trigger Alert",null,Constants.KEYVAL_FIM)+", " +LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)) +" " + LanguageUtil.getString("Tab for") + " " + LanguageUtil.getString(Constants.FRANCHISE_LABEL)+": "+franchiseeName;//P_FIM_B_8020  
	String header =  LanguageUtil.getString("FIM Trigger Alert",null,Constants.KEYVAL_FIM)+", <a href='"+href+"'>" +LanguageUtil.getString(info.getString(FieldNames.FORM_NAME)) +"</a> " + LanguageUtil.getString("Tab for") + " " + LanguageUtil.getString(Constants.FRANCHISE_LABEL)+": "+franchiseeName;//P_FIM_B_8020
	
//P_FIM_B_53248 starts	
	SequenceMap regionalUserMap=AdminMgr.newInstance().getUsersDAO().getRegionalUserByAreaID(areaID);
	SequenceMap<String, String> divisionUserMap = AdminMgr.newInstance().getUsersDAO().getDivisionalUserBasedOnDivisions(divisionId);
	
	SequenceMap sMap = getUserInfo(StringUtil.getCSVString(alertUsers));
	Iterator it = sMap.values().iterator();
	Info userInfo=null;
	while(it.hasNext()){
		userInfo=(Info)it.next(); 
		
		if(StringUtil.isValid(userInfo.getString("EMAIL_ID"))){
			if("0".equals(userInfo.get("USER_LEVEL")) 
					|| (Constants.USER_LEVEL_DIVISION.equals(userInfo.get("USER_LEVEL")) && divisionUserMap.containsKey(userInfo.getString("USER_NO"))) 
					|| ("2".equals(userInfo.get("USER_LEVEL")) && (regionalUserMap != null && regionalUserMap.size() > 0) && regionalUserMap.containsKey(userInfo.getString("USER_NO"))))	
				newMailsSentTo += userInfo.getString("EMAIL_ID") +", ";
		}
	}
	//P_FIM_B_53248 ends	
	/*for(i=0; i<alertUsers.length;i++ ) {
	    try {
		emailId	 = AdminMgr.newInstance().getUsersDAO().getEmailID(alertUsers[i].toString());
		logger.info("user emailId:: " + emailId);
		if (emailId!=null && !emailId.equals("")) {
			newMailsSentTo += emailId +", ";
		}
	    }	
	    catch (Exception e) {
		logger.error("Exception: ", e);
		e.printStackTrace();
	    }
	}*/
	emailId	 = info.get(FieldNames.EMAIL_ID);
	newMailsSentTo += emailId ;
	if (emailId==null || emailId.trim().equals("")) {
		// to avoid null pointer exception
		if(StringUtil.isValid(newMailsSentTo))
		newMailsSentTo = newMailsSentTo.substring(0,newMailsSentTo.length()-2);
	}
	newMailsSentTo=newMailsSentTo.replaceAll(";",",");
	
		com.home.builderforms.mail.MailSenderBase mailer = new com.home.builderforms.mail.MailSenderBase();
	
		 mailer.init();
                 
                String headerMsg = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"><style>\n"
                +"#intro {	clear: both; border-right: #bbb 1px dashed; padding-right: 12px; border-top: #bbb 1px dashed; padding-left: 12px; background: #FFFFFF; padding-bottom: 0px; margin: 0px 0px 10pt; border-left: #bbb 1px dashed; padding-top: 0px; border-bottom: #bbb 1px dashed; position: relative}\n"
                +"#outro {	clear: both; border-right: #bbb 1px dashed; padding-right: 12px; border-top: #bbb 1px dashed; padding-left: 12px; background: #FFFFFF; padding-bottom: 0px; margin: 0px 0px 10pt; border-left: #bbb 1px dashed; padding-top: 0px; border-bottom: #bbb 1px dashed; position: relative}\n"
                +"#intro a {	text-decoration: underline}\n"
                +"#outro a {	text-decoration: underline}\n"
                +"#intro p {	font-size: 10pt; margin: 10px 0px; line-height: 18px; font-family: Tahoma, Arial, Helvetica, sans-serif; text-align: justify}\n"
                +"#outro p {	font-size: 10pt; margin: 10px 0px; line-height: 18px; font-family: Tahoma, Arial, Helvetica, sans-serif; text-align: justify}\n"
                +".bold {	font-weight: bold} \n"
                +"div.content p {	margin: 8px 31px 10px; line-height: 21px; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif} \n"
                +"#printContent .content p {	margin: 8px 31px 10px; line-height: 21px; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif }\n"
                +".quantumBlue {	color: #1b468e}\n"
                +".quantumGreenBold {	font-weight: bold}\n"
                +"br {	height: 3px}\n"
                +"p a {	text-decoration: underline}\n"
                +"ul.introlist a {	text-decoration: underline}\n"
                +"div.content ol {	clear: both; margin: 0px 0px 10px 30px}\n"
                +"ul.introlist {	clear: both; margin: 0px 0px 10px 30px}\n"
                +"div.content ol li {	font-size: 10pt; line-height: 18px; font-family: Tahoma, Arial, Helvetica, sans-serif}\n"
                +"ul.introlist li {	font-size: 10pt; line-height: 18px; font-family: Tahoma, Arial, Helvetica, sans-serif} </style>\n"
                +"<meta content=\"MSHTML 6.00.2800.1106\" name=\"GENERATOR\"></head>\n"
                +"<body bgcolor=\"#FFFFFF\" text=\"#000000\" style=\"width:850px;\">\n"
                +"<div class=\"content\"  style=\"width:850px\">\n"
                +"<table id=\"intro\" style=\"border: 4px solid rgb(197, 216, 229); padding: 10pt;width:830px;\">\n"
                +"<tr><td>\n"
                +"<table border=\"0\" cellspacing=\"1\" cellpadding=\"2\" width=\"800\">\n"
                +"<tr><td style=\"font-family: Arial,helvetica,sans-serif; color:#008da8; font-size:15px; padding-bottom:5px; padding-top:12px; border-bottom: 2px solid #008da8\"><strong>"+LanguageUtil.getString("FIM Trigger Alert",null,Constants.KEYVAL_FIM)+"</strong></td></tr>\n";
		 
		 String nameValueList =
				"<tr><td><b style=\"font-weight: bold; font-size: 10pt; line-height: 18px;font-family: Tahoma, Arial, Helvetica, sans-serif;\">"

		        +header+"</b></td></tr>"
		        
		        // Blank Row
		        +"<tr valign=\"top\">\n<td colspan='2' style=\"FONT-WEIGHT: bold; FONT-SIZE: 11px; LINE-HEIGHT: 16px; FONT-FAMILY: Tahoma, Arial, Helvetica, sans-serif\" width=\"100%\" align=\"left\" valign=\"top\">"
		        + "</tr>"
                        +"<tr><td>\n"
                        +"<table border=\"1\" cellspacing=\"1\" cellpadding=\"4\" width=\"800\" style=\"border-collapse: collapse;\">\n";
		    		
		    		        if(info.getString(FieldNames.DISPLAY_FIELD_NAME)!=null && !"".equals(info.getString(FieldNames.DISPLAY_FIELD_NAME)))
		        {
		        	nameValueList=nameValueList+"<tr valign=\"top\" style=\"background-color:#ffffff;\"><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"right\" width=\"15%\" nowrap>"
			        + "<b>"+LanguageUtil.getString("Field Name")+":</b></td><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"left\" width=\"85%\">"	+ info.getString(FieldNames.DISPLAY_FIELD_NAME)
			        + "</td></tr>";
		        }
		        if(info.getString("valueAfterUpdate")!=null && !"".equals(info.getString("valueAfterUpdate")))
		        {
		        	nameValueList=nameValueList+"<tr valign=\"top\" style=\"background-color:#ffffff;\"><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"right\" width=\"15%\" nowrap>"
			        + "<b>"+LanguageUtil.getString("Field Value")+":</b></td><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"left\" width=\"85%\">"	+ info.getString("valueAfterUpdate")
			        + "</td></tr>";
		        }
		        
		        Map<String,String> displayFieldMap= BaseLibraryUtil.newInstance().getAdditionalTriggerFieldCombo();   // For Product_Seperation_BL By Amar Singh.
		        displayFieldMap.putAll(BaseLibraryUtil.getFormAdditionalTriggerFieldCombo(triggerID));//BB-20141006-161 
		        String token="";
		        String value="";
		        if(StringUtil.isValid(selectedFields)){
		        StringTokenizer strToken = new StringTokenizer(selectedFields, ",");
		        if(triggerInfoAfterUpdate!=null){
		        map.putAll(triggerInfoAfterUpdate.getSequenceMap());//BB-20141006-161
		        }
		        while(strToken.hasMoreElements()){
		        	value="";
		        	token=strToken.nextToken();
		        	if(StringUtil.isValid(token) &&  StringUtil.isValid(displayFieldMap.get(token)))
		        	{
		        		
		        		if(map.containsKey(token)){
		        	value=(String)map.get(token);
		        		}
		        		if(!StringUtil.isValid(value)){
		        			value="";
		        		}
		        	if("PHONE1".equals(token) || "MOBILE".equals(token))
		        		value=PortalUtils.formatPhoneNo((String)map.get(token),(String)map.get("COUNTRY"));
		        	
		        	nameValueList=nameValueList+"<tr valign=\"top\" style=\"background-color:#ffffff;\"><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"right\" width=\"15%\" nowrap>"
			        + "<b>"+LanguageUtil.getString(displayFieldMap.get(token))+":</b></td><td style=\"border-color:#C5D8E5;font-size: 10pt; line-height: 16px; font-family: Tahoma, Arial, Helvetica, sans-serif;\" valign=\"top\" align=\"left\" width=\"85%\">"	+ value
			        + "</td></tr>";
		        }
		        }
		        }
		        
                 String []to = newMailsSentTo.split(",");
                 Map<String,String> ccBccMap = NewPortalUtils.getCcBccMailID("fim");
         		 String cC = ccBccMap.get(FieldNames.MAIL_CC);
         		 String bCC = ccBccMap.get(FieldNames.MAIL_BCC);
                 alertMessage = newAlertMessage+"</br>"+nameValueList+ "</table></td></tr><tr><td><p>"+CommonUtil.getFooter()+"</p></td></tr></table></td></tr></table></div>\n</body>\n</html>";
                    mailer.sendMail(fromMailID,to,new String[]{cC},new String[]{bCC},newAlertSubject,alertMessage,"text/html");
                                 
                              
		 		if (mailer.getCurrentStatus() == 0) {
		 			flag = true;
			    }
			    else {
			    	logger.info("Mail could not be sent ::" + mailer.getCurrentMessage());
			    }
	
	
	
	//}
	/*if (flag) {
	    logTriggerCompletion(info, newMailsSentTo, franchiseeNo);
	}*/
	if(StringUtil.isValid(newMailsSentTo)){
		 		 logTriggerCompletion(info, newMailsSentTo, franchiseeNo);
	}
	return flag;
	}
	catch (Exception e) {
		// TODO: handle exception
		logger.error(e,e);
		logger.error("Exception in sendTriggerMail:::"+e.getMessage());
		return false;
	}
    }

    public void logTriggerCompletion(Info info, String mailsSentTo, Integer franchiseeNo) {
	try {
	    Info triggerInfo = new Info();
	    triggerInfo.setIDField(new String[]{FieldNames.COMPLETION_ID});
	    Calendar cal = Calendar.getInstance();
	    triggerInfo.set(FieldNames.EVENT_ID, (Integer) info.getObject(FieldNames.EVENT_ID));
	    triggerInfo.set(FieldNames.EVENT, info.get(FieldNames.EVENT));
	    triggerInfo.set(FieldNames.DATE_TIME, new Timestamp(cal.getTimeInMillis()));
	    triggerInfo.set(FieldNames.ALERT_MESSAGE, info.get(FieldNames.ALERT_MESSAGE));
	    triggerInfo.set(FieldNames.EMAIL_ID, mailsSentTo);
	    triggerInfo.set(FieldNames.FRANCHISEE_NO, franchiseeNo);
	    BaseDAO dao	 = new BaseDAO(TableAnchors.TRIGGER_EVENT_COMPLETION);
	    dao.create(triggerInfo);
	}
	catch (Exception e) {
	    logger.error("Exception: ", e);
	}
    }
    
  //P_FIM_B_53248 starts    
    public static SequenceMap getUserInfo(String userNo){
    	SequenceMap sMap = new SequenceMap();
    	if(StringUtil.isValid(userNo)){
    	Info info=null;
    	ResultSet rs = QueryUtil.getResult("SELECT USER_NO,USER_LEVEL,EMAIL_ID FROM FRANCHISEE F LEFT JOIN USERS U ON U.FRANCHISEE_NO=F.FRANCHISEE_NO WHERE U.IS_DELETED='N'  AND U.STATUS!=0 AND  U.USER_NO IN ("+userNo+")", null);
while (rs.next()) {
	info= new Info();
	info.set("USER_NO",rs.getString("USER_NO"));
	info.set("USER_LEVEL",rs.getString("USER_LEVEL"));
	info.set("EMAIL_ID",rs.getString("EMAIL_ID"));
	sMap.put(rs.getString("USER_NO"), info);
}
    	}
    	return sMap;
    }
  //P_FIM_B_53248 ends
}
