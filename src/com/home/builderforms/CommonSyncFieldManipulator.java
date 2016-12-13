package com.home.builderforms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.home.builderforms.BuilderFormWebImpl;
import com.home.builderforms.AppException;
import com.home.builderforms.TriggerHelper;
import com.home.builderforms.Event;
import com.home.builderforms.EventType;
import com.home.builderforms.Manipulator;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.CommonUtil;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.MultipartRequest;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.ForeignTable;
import com.home.builderforms.LinkField;
import com.home.builderforms.SyncWithField;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.SQLUtil;


/**
 * This manipulator class is used to make fields sync accross the tabs / modules.
 * In this {@link Manipulator} update query will created to update the value in different tabs fields.
 * P_Enh_Sync_Fields
 * @author Naman Jain
 *
 */
public class CommonSyncFieldManipulator implements Manipulator {
	private static final Logger logger = Logger.getLogger(CommonSyncFieldManipulator.class);
	@SuppressWarnings("rawtypes")
	public void manipulate(SequenceMap eventMap, HttpServletRequest request) {
		try{
		String requestFrom	=	request.getParameter("requestFrom");//EXTERNAL_FORM_BUILDER
		String leadId = getParameterValue(request, FieldNames.LEAD_ID);	//P_ENH_SYNC_FIELD
		String entityId = getParameterValue(request, FieldNames.ENTITY_ID);	//P_ENH_SYNC_FIELD
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		String eventType = (String) (request.getParameter("eventType") == null ? "" : request.getParameter("eventType"));
		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;
		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		//EXTERNAL_FORM_BUILDER : START
		if(!StringUtil.isValidNew(leadId) && "ExtWebPage".equals(requestFrom)){
			leadId = (String)request.getAttribute(FieldNames.LEAD_ID);
		}
		//EXTERNAL_FORM_BUILDER : END
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
			entityId = leadFranId;
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}
		//P_ENH_SYNC_FIELD start
		String actualtableAnchor = getParameterValue(request, FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisees";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	=	"";
		if("ExtWebPage".equals(requestFrom)){
			eventType			=	(String)request.getAttribute("foreignEventType");
			actualtableAnchor	=	(String)request.getAttribute("actualTableAnchor");
			syncFieldPrefix		=	(String)request.getAttribute("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String[] syncFields = "yes".equals(request.getParameter("fromRestAPI"))?(String[])request.getAttribute("syncField"):request.getParameterValues(syncFieldPrefix+"syncField");
		String[] syncDatas = "yes".equals(request.getParameter("fromRestAPI"))?(String[])request.getAttribute("syncData"):request.getParameterValues(syncFieldPrefix+"syncData");
		String[] syncModules = "yes".equals(request.getParameter("fromRestAPI"))?(String[])request.getAttribute("syncModule"):request.getParameterValues(syncFieldPrefix+"syncModule");
		//P_ENH_SYNC_FIELD end
		List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs
		List<String> franchiseeColList = new ArrayList<String>(); //used to updated all the columns for the franchisee at Sync Side
		if(syncDatas != null && syncDatas.length > 0) {
			for(String syncData : syncDatas) {
				FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor);
				String otherField = syncData.split("##")[1];
				syncData = syncData.split("##")[0];
				String fieldValue = getParameterValue(request, syncFieldPrefix+syncData);	//P_ENH_SYNC_FIELD
				if(StringUtil.isValidNew(fieldValue) && DateUtil.checkDateValidationForDisplayFormat(fieldValue)) {
					fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
				}
				String readOnlyField="";
				/*if(StringUtil.isValidNew(actualtableAnchor)){
					readOnlyField=BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor).getField(syncData).getReadOnly();
				}*/
				Field field = fMapping.getField(syncData);
				if("true".equals(otherField)) {
					field = fMapping.getOtherTableField(syncData);
				}
				//RightatHome-20160509-951 starts
				if(StringUtil.isValidNew(actualtableAnchor) && field!=null){
					readOnlyField=field.getReadOnly();
				}//RightatHome-20160509-951 ends
				SequenceMap syncTotalaMap = field.getSyncTotalMap();
				if(!"yes".equals(readOnlyField) && (StringUtil.isValid(fieldValue)  || (fieldValue!=null && "".equals(fieldValue.trim())) )) {
					if(syncTotalaMap != null) {
						Iterator itr = syncTotalaMap.values().iterator();
						while(itr.hasNext()) {
							SyncWithField sync = (SyncWithField)itr.next();
							if(sync != null) {
								boolean isPiiEnabled = false;
								String syncModule = sync.getSyncModule();

								FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
								triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
								if(triggerInfo==null){
									triggerInfo=new Info();
								}
								if(syncFieldMapping != null) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null) {
										if(syncFieldMain.isPiiEnabled()) {
											isPiiEnabled = true;
										}
									}
								}

								if(StringUtil.isValidNew(leadId)) {
									if("within".equals(syncModule) || "fs".equals(syncModule)) {
										if(isPiiEnabled){
											queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
										}else{
											queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
										}
									} else if("fim".equals(syncModule)) { //for now it is FIM module.
										if(StringUtil.isValidNew(leadFranId)) {

											String primaryColumnValue = "ENTITY_ID";
											Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only
											if("FRANCHISEE".equals(sync.getTableName())) {
												primaryColumnValue = "FRANCHISEE_NO";
												if(syncFieldMain != null && syncFieldMain.isBuildField()) {
													if(isPiiEnabled) {
														franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
													} else {
														franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
													}
												}
												syncFieldMain = null;
											}

											if(isPiiEnabled) {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
											} else {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
											}

											dataInfo = new Info();
											dataInfo.set("fieldName", sync.getFieldName());
											dataInfo.set("fieldValue", fieldValue);
											dataInfo.set("columnName", sync.getColumnName());
											dataInfo.set("idColumn", primaryColumnValue);
											triggerInfo.put(sync.getFieldName(), dataInfo);
										}
									}
								} else if(StringUtil.isValidNew(entityId)) {

									if("within".equals(syncModule) || "fim".equals(syncModule)) {
										if("FRANCHISEE".equals(sync.getTableName())) {
											Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
											if(isPiiEnabled){
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
												if(syncFieldMain != null && syncFieldMain.isBuildField())
												{
													franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
												}
											}else{
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);

												if(syncFieldMain != null && syncFieldMain.isBuildField())
												{
													franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
												}
											}
											syncFieldMain=null;
											dataInfo =new Info();
											dataInfo.set("fieldName",sync.getFieldName());
											dataInfo.set("fieldValue",fieldValue);
											dataInfo.set("columnName",sync.getColumnName());
											dataInfo.set("idColumn","FRANCHISEE_NO");
											triggerInfo.put(sync.getFieldName(), dataInfo);
											//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
										} else {
											if(isPiiEnabled){
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
											}else{
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
											}
											dataInfo =new Info();
											dataInfo.set("fieldName",sync.getFieldName());
											dataInfo.set("fieldValue",fieldValue);
											dataInfo.set("columnName",sync.getColumnName());
											dataInfo.set("idColumn","ENTITY_ID");
											triggerInfo.put(sync.getFieldName(), dataInfo);
											//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
										}
									} else if("fs".equals(syncModule)) {
										if(StringUtil.isValidNew(leadFranId)) {
											if(isPiiEnabled) {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
											} else {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
											}
										}
									}
								}
								triggerMap.put(sync.getTableAnchor(), triggerInfo);

								if(syncFieldMapping != null) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null) {
										queryParentUpdate(syncFieldMain.getSyncTotalMap(), request, fieldValue,franchiseeColList, queryList);
									}
								}
							}
						}
					}
				}
			}
		}

		int fieldCounter = 0;
		if(syncFields != null && syncFields.length > 0) {
			try{
			for(String syncField : syncFields) {
				String tableAnchor = syncField.split("##")[0];
				String fieldName = syncField.split("##")[3];
				String otherField = syncField.split("##")[2];
				String syncFieldNew = syncField.split("##")[1];
				FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
				Field field = fMapping.getField(syncFieldNew);
				if("true".equals(otherField)) {
					field = fMapping.getOtherTableField(syncFieldNew);
				}
				String syncModule = syncModules[fieldCounter];
				queryUpdate(syncField, request, syncModule,franchiseeColList);
				String thisSyncField = field.getSyncWithField();
				if(StringUtil.isValidNew(thisSyncField)) {
					queryUpdate(thisSyncField+"##"+fieldName, request, field.getSyncModule(),franchiseeColList);
				}
				
				fieldCounter++;
			}
			}catch(Exception e){
				logger.error(e,e);
			}
		}
		if(!franchiseeColList.isEmpty())
		{
			try
			{
				StringBuilder updateQuery = new StringBuilder("UPDATE FRANCHISEE SET ");
				for(String franchiseeCol : franchiseeColList)
				{
					updateQuery.append(franchiseeCol).append(",");

				}
				String finalQuery=updateQuery.substring(0, updateQuery.length()-1);
				finalQuery+=" WHERE FRANCHISEE_NO="+entityId;
				HashMap<String,String> parameters = BaseUtils.getNewHashMapWithKeyValueType();
				parameters.put("syncQuery", finalQuery);
				parameters.put("franuserno", (String)request.getSession().getAttribute("user_no"));
				parameters.put("action", "syncFieldUpdate");
				
				parameters.put("franchiseeno", entityId);
				//NewPortalUtils.syncData("store", request, parameters);
			}
			catch (Exception e) 
			{
				logger.error(e,e);
			}
		}


		try {
			if(StringUtil.isValidNew(leadId)) {
				if(StringUtil.isValidNew(leadFranId)) {
					triggerUpdate(eventType, leadFranId, eventMap, request,triggerMap);
				}
			} else {
				triggerUpdate(eventType, entityId, eventMap, request,triggerMap);
			}
			if(queryList.size()>0){
			QueryUtil.batchUpdate(queryList, null);
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}catch(Exception e){
			logger.error(e,e);
		}
	}


	private void queryParentUpdate(SequenceMap syncTotalaMap, HttpServletRequest request, String fieldValue,List<String> franchiseeColList, List<String> queryList) {

		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;
		//P_ENH_SYNC_FIELD start
		String actualtableAnchor = getParameterValue(request, FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisees";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	=	"";
		String requestFrom	=	request.getParameter("requestFrom");
		if("ExtWebPage".equals(requestFrom)){
			actualtableAnchor	=	(String)request.getAttribute("actualTableAnchor");
			syncFieldPrefix		=	(String)request.getAttribute("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String leadId = getParameterValue(request, FieldNames.LEAD_ID);
		String entityId = getParameterValue(request, FieldNames.ENTITY_ID);
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		//P_ENH_SYNC_FIELD end
		//List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs

		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		//EXTERNAL_FORM_BUILDER : START
		if(!StringUtil.isValidNew(leadId) && "ExtWebPage".equals(requestFrom)){
			leadId = (String)request.getAttribute(FieldNames.LEAD_ID);
		}
		//EXTERNAL_FORM_BUILDER : END
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}

		if(syncTotalaMap != null) {
			Iterator itr = syncTotalaMap.values().iterator();
			while(itr.hasNext()) {
				SyncWithField sync = (SyncWithField)itr.next();
				if(sync != null) {
					boolean isPiiEnabled = false;
					String syncModule = sync.getSyncModule();
					FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
					triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
					if(triggerInfo==null){
						triggerInfo=new Info();
					}
					if(syncFieldMapping != null) {
						Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
						if(syncFieldMain != null) {
							if(syncFieldMain.isPiiEnabled()) {
								isPiiEnabled = true;
							}
						}
					}

					if(StringUtil.isValidNew(leadId)) {
						if("within".equals(syncModule) || "fs".equals(syncModule)) {
							if(isPiiEnabled){
								queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
							}else{
								queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
							}
						} else if("fim".equals(syncModule)) { //for now it is FIM module.
							if(StringUtil.isValidNew(leadFranId)) {
								String primaryColumnValue = "ENTITY_ID";
								Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only

								if("FRANCHISEE".equals(sync.getTableName())) {
									primaryColumnValue = "FRANCHISEE_NO";
									if(syncFieldMain != null && syncFieldMain.isBuildField()) {
										if(isPiiEnabled) {
											franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
										} else {
											franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
										}
									}
									syncFieldMain = null;
								}

								if(isPiiEnabled) {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
								} else {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
								}

								dataInfo = new Info();
								dataInfo.set("fieldName", sync.getFieldName());
								dataInfo.set("fieldValue", fieldValue);
								dataInfo.set("columnName", sync.getColumnName());
								dataInfo.set("idColumn", primaryColumnValue);
								triggerInfo.put(sync.getFieldName(), dataInfo);
							}
						}
					} else if(StringUtil.isValidNew(entityId)) {

						if("within".equals(syncModule) || "fim".equals(syncModule)) {
							if("FRANCHISEE".equals(sync.getTableName())) {
								Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
									if(syncFieldMain != null && syncFieldMain.isBuildField())
									{
										franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
									}
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);

									if(syncFieldMain != null && syncFieldMain.isBuildField())
									{
										franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
									}
								}
								syncFieldMain=null;
								dataInfo =new Info();
								dataInfo.set("fieldName",sync.getFieldName());
								dataInfo.set("fieldValue",fieldValue);
								dataInfo.set("columnName",sync.getColumnName());
								dataInfo.set("idColumn","FRANCHISEE_NO");
								triggerInfo.put(sync.getFieldName(), dataInfo);
								//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
							} else {
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
								}
								dataInfo =new Info();
								dataInfo.set("fieldName",sync.getFieldName());
								dataInfo.set("fieldValue",fieldValue);
								dataInfo.set("columnName",sync.getColumnName());
								dataInfo.set("idColumn","ENTITY_ID");
								triggerInfo.put(sync.getFieldName(), dataInfo);
								//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
							}
						} else if("fs".equals(syncModule)) {
							if(StringUtil.isValidNew(leadFranId)) {
								if(isPiiEnabled) {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
								} else {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
								}
							}
						}
					}
					triggerMap.put(sync.getTableAnchor(), triggerInfo);

					if(syncFieldMapping != null) {
						Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
						if(syncFieldMain != null) {
							queryParentUpdate(syncFieldMain.getSyncTotalMap(), request, fieldValue,franchiseeColList, queryList);
						}
					}
				}
			}
		}
	}


	@SuppressWarnings("rawtypes")
	public void manipulate(SequenceMap eventMap, MultipartRequest request) {
		// TODO Auto-generated method stub
		String requestFrom	=	request.getParameter("requestFrom");//EXTERNAL_FORM_BUILDER
		String leadId = request.getParameter(FieldNames.LEAD_ID);
		String entityId = request.getParameter(FieldNames.ENTITY_ID);
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		String eventType = (String) (request.getParameter("eventType") == null ? "" : request.getParameter("eventType"));
		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;
		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
			entityId = leadFranId;
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}

		String actualtableAnchor = request.getParameter(FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisee";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	=	"";
		if("ExtWebPage".equals(requestFrom)){
			eventType			=	request.getParameter("foreignEventType");
			actualtableAnchor	=	request.getParameter("actualTableAnchor");
			syncFieldPrefix		=	request.getParameter("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String[] syncFields = request.getParameterValues(syncFieldPrefix+"syncField");
		String[] syncModules = request.getParameterValues(syncFieldPrefix+"syncModule");
		String[] syncDatas = request.getParameterValues(syncFieldPrefix+"syncData");
		List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs
		List<String> franchiseeColList = new ArrayList<String>(); //used to updated all the columns for the franchisee at Sync Side
		if(syncDatas != null && syncDatas.length > 0) {
			for(String syncData : syncDatas) {
				FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor);
				String otherField = syncData.split("##")[1];
				syncData = syncData.split("##")[0];
				//String fieldValue = request.getParameter(syncData);
				String fieldValue =getParameterValue(request, syncFieldPrefix+syncData);	
				if(StringUtil.isValidNew(fieldValue) && DateUtil.checkDateValidationForDisplayFormat(fieldValue)) {
					fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
				}
				Field field = fMapping.getField(syncData);
				if("true".equals(otherField)) {
					field = fMapping.getOtherTableField(syncData);
				}
				String readOnlyField="";
				/*if(StringUtil.isValidNew(actualtableAnchor)){
					readOnlyField=BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor).getField(syncData).getReadOnly();
				}*/
				//RightatHome-20160509-951 starts
				if(StringUtil.isValidNew(actualtableAnchor) && field!=null){
					readOnlyField=field.getReadOnly();
				}//RightatHome-20160509-951 ends
				SequenceMap syncTotalaMap = field.getSyncTotalMap();
				if(!"yes".equals(readOnlyField) && (StringUtil.isValidNew(fieldValue) || (fieldValue!=null && "".equals(fieldValue.trim()) ))) {
					if(syncTotalaMap != null) {
						Iterator itr = syncTotalaMap.values().iterator();
						while(itr.hasNext()) {
							SyncWithField sync = (SyncWithField)itr.next();
							if(sync != null) {
								boolean isPiiEnabled = false;
								String syncModule = sync.getSyncModule();
								FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
								triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
								if(triggerInfo==null){
									triggerInfo=new Info();
								}
								if(syncFieldMapping != null) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null) {
										if(syncFieldMain.isPiiEnabled()) {
											isPiiEnabled = true;
										}
									}
								}

								if(StringUtil.isValidNew(leadId)) {
									if("within".equals(syncModule) || "fs".equals(syncModule)) {
										if(isPiiEnabled){
											queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
										}else{
											queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
										}
									} else if("fim".equals(syncModule)) { //for now it is FIM module.
										if(StringUtil.isValidNew(leadFranId)) {
											String primaryColumnValue = "ENTITY_ID";
											Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only

											if("FRANCHISEE".equals(sync.getTableName())) {
												primaryColumnValue = "FRANCHISEE_NO";
												if(syncFieldMain != null && syncFieldMain.isBuildField()) {
													if(isPiiEnabled) {
														franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
													} else {
														franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
													}
												}
												syncFieldMain = null;
											}

											if(isPiiEnabled) {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
											} else {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
											}

											dataInfo = new Info();
											dataInfo.set("fieldName", sync.getFieldName());
											dataInfo.set("fieldValue", fieldValue);
											dataInfo.set("columnName", sync.getColumnName());
											dataInfo.set("idColumn", primaryColumnValue);
											triggerInfo.put(sync.getFieldName(), dataInfo);
										}
									}
								} else if(StringUtil.isValidNew(entityId)) {

									if("within".equals(syncModule) || "fim".equals(syncModule)) {
										if("FRANCHISEE".equals(sync.getTableName())) {
											Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
											if(isPiiEnabled){
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
												if(syncFieldMain != null && syncFieldMain.isBuildField())
												{
													franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
												}
											}else{
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);

												if(syncFieldMain != null && syncFieldMain.isBuildField())
												{
													franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
												}
											}
											syncFieldMain=null;
											dataInfo =new Info();
											dataInfo.set("fieldName",sync.getFieldName());
											dataInfo.set("fieldValue",fieldValue);
											dataInfo.set("columnName",sync.getColumnName());
											dataInfo.set("idColumn","FRANCHISEE_NO");
											triggerInfo.put(sync.getFieldName(), dataInfo);
											//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
										} else {
											if(isPiiEnabled){
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
											}else{
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
											}
											dataInfo =new Info();
											dataInfo.set("fieldName",sync.getFieldName());
											dataInfo.set("fieldValue",fieldValue);
											dataInfo.set("columnName",sync.getColumnName());
											dataInfo.set("idColumn","ENTITY_ID");
											triggerInfo.put(sync.getFieldName(), dataInfo);
											//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
										}
									} else if("fs".equals(syncModule)) {
										if(StringUtil.isValidNew(leadFranId)) {
											if(isPiiEnabled) {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
											} else {
												queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
											}
										}
									}
								}
								triggerMap.put(sync.getTableAnchor(), triggerInfo);

								if(syncFieldMapping != null) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null) {
										multiQueryParentUpdate(syncFieldMain.getSyncTotalMap(), request, fieldValue,franchiseeColList, queryList);
									}
								}
							}
						}
					}
				}
			}
		}

		int fieldCounter = 0;
		if(syncFields != null && syncFields.length > 0) {
			for(String syncField : syncFields) {
				String tableAnchor = syncField.split("##")[0];
				String fieldName = syncField.split("##")[3];
				String otherField = syncField.split("##")[2];
				String syncFieldNew = syncField.split("##")[1];
				String syncModule = syncModules[fieldCounter];
				FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
				Field field = fMapping.getField(syncFieldNew);
				if("true".equals(otherField)) {
					field = fMapping.getOtherTableField(syncFieldNew);
				}
				multiQueryUpdate(syncField, request,franchiseeColList, syncModule);

				String thisSyncField = field.getSyncWithField();
				if(StringUtil.isValidNew(thisSyncField)) {
					multiQueryUpdate(thisSyncField+"##"+fieldName, request,franchiseeColList, field.getSyncModule());
				}

				fieldCounter++;
			}
		}
		if(!franchiseeColList.isEmpty())
		{
			try
			{
				StringBuilder updateQuery = new StringBuilder("UPDATE FRANCHISEE SET ");
				for(String franchiseeCol : franchiseeColList)
				{
					updateQuery.append(franchiseeCol).append(",");

				}
				String finalQuery=updateQuery.substring(0, updateQuery.length()-1);
				finalQuery+=" WHERE FRANCHISEE_NO="+entityId;
				HashMap<String,String> parameters = BaseUtils.getNewHashMapWithKeyValueType();
				parameters.put("syncQuery", finalQuery);
				parameters.put("action", "syncFieldUpdate");
				//NewPortalUtils.syncData("store", null, parameters,request);
			}
			catch (Exception e) 
			{
				logger.error(e,e);
			}
		}

		try {
			triggerUpdateMultiPart(eventType, entityId, eventMap, request,triggerMap);
			if(queryList.size()>0){
			QueryUtil.batchUpdate(queryList, null);
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void multiQueryParentUpdate(SequenceMap syncTotalaMap, MultipartRequest request, String fieldValue,List<String> franchiseeColList, List<String> queryList) {

		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;

		String actualtableAnchor = request.getParameter(FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisees";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	= "";
		String requestFrom	=	request.getParameter("requestFrom");
		if("ExtWebPage".equals(requestFrom)){
			actualtableAnchor	=	request.getParameter("actualTableAnchor");
			syncFieldPrefix		=	request.getParameter("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String leadId = request.getParameter(FieldNames.LEAD_ID);
		String entityId = request.getParameter(FieldNames.ENTITY_ID);
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		//List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs

		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
			entityId = leadFranId;
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}

		if(syncTotalaMap != null) {
			Iterator itr = syncTotalaMap.values().iterator();
			while(itr.hasNext()) {
				SyncWithField sync = (SyncWithField)itr.next();
				if(sync != null) {
					boolean isPiiEnabled = false;
					String syncModule = sync.getSyncModule();
					FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
					triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
					if(triggerInfo==null){
						triggerInfo=new Info();
					}
					if(syncFieldMapping != null) {
						Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
						if(syncFieldMain != null) {
							if(syncFieldMain.isPiiEnabled()) {
								isPiiEnabled = true;
							}
						}
					}

					if(StringUtil.isValidNew(leadId)) {
						if("within".equals(syncModule) || "fs".equals(syncModule)) {
							if(isPiiEnabled){
								queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
							}else{
								queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
							}
						} else if("fim".equals(syncModule)) { //for now it is FIM module.
							if(StringUtil.isValidNew(leadFranId)) {
								String primaryColumnValue = "ENTITY_ID";
								Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only

								if("FRANCHISEE".equals(sync.getTableName())) {
									primaryColumnValue = "FRANCHISEE_NO";
									if(syncFieldMain != null && syncFieldMain.isBuildField()) {
										if(isPiiEnabled) {
											franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
										} else {
											franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
										}
									}
									syncFieldMain = null;
								}

								if(isPiiEnabled) {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
								} else {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
								}

								dataInfo = new Info();
								dataInfo.set("fieldName", sync.getFieldName());
								dataInfo.set("fieldValue", fieldValue);
								dataInfo.set("columnName", sync.getColumnName());
								dataInfo.set("idColumn", primaryColumnValue);
								triggerInfo.put(sync.getFieldName(), dataInfo);
							}
						}
					} else if(StringUtil.isValidNew(entityId)) {

						if("within".equals(syncModule) || "fim".equals(syncModule)) {
							if("FRANCHISEE".equals(sync.getTableName())) {
								Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
									if(syncFieldMain != null && syncFieldMain.isBuildField())
									{
										franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
									}
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);

									if(syncFieldMain != null && syncFieldMain.isBuildField())
									{
										franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
									}
								}
								syncFieldMain=null;
								dataInfo =new Info();
								dataInfo.set("fieldName",sync.getFieldName());
								dataInfo.set("fieldValue",fieldValue);
								dataInfo.set("columnName",sync.getColumnName());
								dataInfo.set("idColumn","FRANCHISEE_NO");
								triggerInfo.put(sync.getFieldName(), dataInfo);
								//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
							} else {
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
								}
								dataInfo =new Info();
								dataInfo.set("fieldName",sync.getFieldName());
								dataInfo.set("fieldValue",fieldValue);
								dataInfo.set("columnName",sync.getColumnName());
								dataInfo.set("idColumn","ENTITY_ID");
								triggerInfo.put(sync.getFieldName(), dataInfo);
								//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
							}
						} else if("fs".equals(syncModule)) {
							if(StringUtil.isValidNew(leadFranId)) {
								if(isPiiEnabled) {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
								} else {
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
								}
							}
						}
					}
					triggerMap.put(sync.getTableAnchor(), triggerInfo);

					if(syncFieldMapping != null) {
						Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
						if(syncFieldMain != null) {
							multiQueryParentUpdate(syncFieldMain.getSyncTotalMap(), request, fieldValue,franchiseeColList, queryList);
						}
					}
				}
			}
		}
	}

	private void multiQueryUpdate(String thisSyncField, MultipartRequest request,List<String> franchiseeColList, String syncModuleKey) {

		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;

		String actualtableAnchor = request.getParameter(FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisees";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	=	"";
		String requestFrom	=	request.getParameter("requestFrom");
		if("ExtWebPage".equals(requestFrom)){
			actualtableAnchor	=	request.getParameter("actualTableAnchor");
			syncFieldPrefix		=	request.getParameter("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String leadId = request.getParameter(FieldNames.LEAD_ID);
		String entityId = request.getParameter(FieldNames.ENTITY_ID);
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs

		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
			entityId = leadFranId;
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}


		String tableAnchor = thisSyncField.split("##")[0];
		String fieldName = thisSyncField.split("##")[3];
		String otherField = thisSyncField.split("##")[2];
		thisSyncField = thisSyncField.split("##")[1];
		//String fieldValue = request.getParameter(fieldName);
		String fieldValue = getParameterValue(request, syncFieldPrefix+fieldName);	//P_ENH_SYNC_FIELD
		FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
		String readOnlyField="";
		if(StringUtil.isValidNew(actualtableAnchor)){
			readOnlyField=BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor).getField(fieldName).getReadOnly();
		}
		Field field = fMapping.getField(thisSyncField);
		if("true".equals(otherField)) {
			field = fMapping.getOtherTableField(thisSyncField);
		}
		String tableName = fMapping.getTableName();
		triggerInfo=(Info)triggerMap.get(tableAnchor);
		if(triggerInfo==null){
			triggerInfo=new Info();
		}
		if("Date".equals(field.getDisplayTypeField())) {
			if(StringUtil.isValidNew(fieldValue)) {
				fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
			}
		}

		if(!"yes".equals(readOnlyField) && (StringUtil.isValid(fieldValue) || (fieldValue!=null && "".equals(fieldValue.trim())))) {
			if("true".equals(otherField)) {
				String idFieldValue="";
				if("fim".equals(syncModuleKey) || "within".equals(syncModuleKey)){
					if(StringUtil.isValidNew(leadId)){
						idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), "ENTITY_ID", leadFranId);
					}else{
						idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), "ENTITY_ID", primaryId);
					}
				}else{
					idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), "LEAD_ID", primaryId);
				}
				field = fMapping.getOtherTableField(thisSyncField);
				String tbleNme = field.getTableAnchor();
				FieldMappings mappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tbleNme);
				tableName = mappings.getTableName();
				if("ADDRESS".equals(tableName)) {
					if(StringUtil.isValidNew(entityId) && StringUtil.isValidNew(idFieldValue)){
						String tableAlias = field.getTableName();
						String order = tableAlias.split("_")[1];
						if(field.isPiiEnabled()){
							queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE FOREIGN_ID="+idFieldValue+" AND ADDRESS_ORDER="+order); //to update in parent field.
						}else{
							queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FOREIGN_ID="+idFieldValue+" AND ADDRESS_ORDER="+order); //to update in parent field.	
						}
					}
				} else {
					if(StringUtil.isValidNew(leadId)){
					if(field.isPiiEnabled()){
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' ,'pvm@e20' ) WHERE LEAD_ID="+leadId); //to update in parent field.	
					}else{
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId); //to update in parent field.
					}
					}
				}
			} else {
				if(StringUtil.isValidNew(leadId)) {
					if("within".equals(syncModuleKey) || "fs".equals(syncModuleKey)) {
						if(field.isPiiEnabled()){
							queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE LEAD_ID="+leadId); //to update in parent field.	
						}else{
							queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId); //to update in parent field.
						}	
					} else if("fim".equals(syncModuleKey)) {
						if(StringUtil.isValidNew(leadFranId)) {
							String primaryColumnValue = "ENTITY_ID";

							if("FRANCHISEE".equals(fMapping.getTableName())) {
								primaryColumnValue = "FRANCHISEE_NO";

								if(field.isBuildField()) {
									if(field.isPiiEnabled()) {
										franchiseeColList.add(field.getDbField()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
									} else {
										franchiseeColList.add(field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
									}
								}
							}

							if(field.isPiiEnabled()) {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE "+primaryColumnValue+"="+leadFranId); //to update in parent field.
							} else {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId); //to update in parent field.
							}

							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn",primaryColumnValue);
							triggerInfo.put(field.getFieldName(), dataInfo);
						}
					}

				} else if(StringUtil.isValidNew(entityId)) {
					if("within".equals(syncModuleKey) || "fim".equals(syncModuleKey)) {
						if("FRANCHISEE".equals(fMapping.getTableName())) {
							if(field.isPiiEnabled()){
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE FRANCHISEE_NO="+entityId); //to update in parent field.
								if(field.isBuildField())
								{
									franchiseeColList.add(field.getDbField()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
								}
							}else{
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId); //to update in parent field.

								if(field.isBuildField())
								{
									franchiseeColList.add(field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
								}
							}
							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn","FRANCHISEE_NO");
							triggerInfo.put(field.getFieldName(), dataInfo);
							//triggerUpdateMultiPart(eventType, tableAnchor, entityId, eventMap, request,field.getFieldName(),fieldValue,field.getDbField(),"FRANCHISEE_NO");
						} else {
							if(field.isPiiEnabled()){
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' ,'pvm@e20' )  WHERE ENTITY_ID="+entityId); //to update in parent field.	
							}else{
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId); //to update in parent field.
							}
							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn","ENTITY_ID");
							triggerInfo.put(field.getFieldName(), dataInfo);
							//triggerUpdateMultiPart(eventType, tableAnchor, entityId, eventMap, request,field.getFieldName(),fieldValue,field.getDbField(),"ENTITY_ID");
						}						
					} else if("fs".equals(syncModuleKey)) {
						if(StringUtil.isValidNew(leadFranId)) {
							if(field.isPiiEnabled()) {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
							} else {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
							}
						}
					}
					triggerMap.put(tableAnchor, triggerInfo);
				}
			}



			SequenceMap syncTotalaMap = field.getSyncTotalMap();
			if(syncTotalaMap != null) {
				Iterator itr = syncTotalaMap.values().iterator();
				while(itr.hasNext()) {
					SyncWithField sync = (SyncWithField)itr.next();
					if(sync != null) {
						boolean isPiiEnabled = false;
						String syncModule = sync.getSyncModule();
						FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
						if(syncFieldMapping != null) {
							Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
							if(syncFieldMain != null) {
								if(syncFieldMain.isPiiEnabled()) {
									isPiiEnabled = true;
								}
							}
							//syncFieldMapping = null;
						}
						triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
						if(triggerInfo==null){
							triggerInfo=new Info();
						}
						if(actualtableAnchor.equals(sync.getTableAnchor())) { //skipping because for the same table data can be inserted / modified by the main query i.e. from Generic Handler
							continue;
						}

						if(StringUtil.isValidNew(fieldValue) && DateUtil.checkDateValidationForDisplayFormat(fieldValue)) {
							fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
						}
						if(StringUtil.isValidNew(leadId)) {
							if("within".equals(syncModule) || "fs".equals(syncModule)) {
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
								}
							} else if("fim".equals(syncModule)) { //for now it is FIM module.
								if(StringUtil.isValidNew(leadFranId)) {
									String primaryColumnValue = "ENTITY_ID";
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only

									if("FRANCHISEE".equals(sync.getTableName())) {
										primaryColumnValue = "FRANCHISEE_NO";
										if(syncFieldMain != null && syncFieldMain.isBuildField()) {
											if(isPiiEnabled) {
												franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
											} else {
												franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
											}
										}
										syncFieldMain = null;
									}

									if(isPiiEnabled) {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
									} else {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
									}

									dataInfo = new Info();
									dataInfo.set("fieldName", sync.getFieldName());
									dataInfo.set("fieldValue", fieldValue);
									dataInfo.set("columnName", sync.getColumnName());
									dataInfo.set("idColumn", primaryColumnValue);
									triggerInfo.put(sync.getFieldName(), dataInfo);
								}
							}
						} else if(StringUtil.isValidNew(entityId)) {
							if("within".equals(syncModule)  || "fim".equals(syncModule)) {
								if("FRANCHISEE".equals(sync.getTableName())) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null && syncFieldMain.isBuildField()) {
										if(isPiiEnabled) {
											franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
										} else {
											franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
										}
									}
									if(isPiiEnabled){
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
									}else{
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);
									}
									syncFieldMain=null;
									dataInfo =new Info();
									dataInfo.set("fieldName",sync.getFieldName());
									dataInfo.set("fieldValue",fieldValue);
									dataInfo.set("columnName",sync.getColumnName());
									dataInfo.set("idColumn","FRANCHISEE_NO");
									triggerInfo.put(sync.getFieldName(), dataInfo);
									//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
								} else {
									if(isPiiEnabled){
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
									}else{
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
									}
									dataInfo =new Info();
									dataInfo.set("fieldName",sync.getFieldName());
									dataInfo.set("fieldValue",fieldValue);
									dataInfo.set("columnName",sync.getColumnName());
									dataInfo.set("idColumn","ENTITY_ID");
									triggerInfo.put(sync.getFieldName(), dataInfo);
									//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
								}
							} else if("fs".equals(syncModule)) {
								if(StringUtil.isValidNew(leadFranId)) {
									if(isPiiEnabled) {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
									} else {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
									}
								}
							}
						}
						triggerMap.put(sync.getTableAnchor(), triggerInfo);
					}
				}
			}


			String eventType = (String) (request.getParameter("eventType") == null ? "" : request.getParameter("eventType"));

			try {
				triggerUpdateMultiPart(eventType, entityId, null, request,triggerMap);
				if(queryList.size()>0){
				QueryUtil.batchUpdate(queryList, null);
				}
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String syncField = field.getSyncWithField();
		if(StringUtil.isValidNew(syncField)) {
			multiQueryUpdate(syncField+"##"+fieldName, request,franchiseeColList, field.getSyncModule());
		}
	}

	public void queryUpdate(String thisSyncField, HttpServletRequest request, String syncModuleKey,List<String> franchiseeColList) {

		Map<String, Info> triggerMap=new HashMap<String, Info>();
		Info triggerInfo=null;
		Info dataInfo=null;
		//P_ENH_SYNC_FIELD start
		String actualtableAnchor = getParameterValue(request, FieldNames.TABLE_ANCHOR);
		if("franchisee".equals(actualtableAnchor)){
			actualtableAnchor="franchisees";
		}
		//EXTERNAL_FORM_BUILDER : START
		String syncFieldPrefix	=	"";
		String requestFrom	=	request.getParameter("requestFrom");
		if("ExtWebPage".equals(requestFrom)){
			actualtableAnchor	=	(String)request.getAttribute("actualTableAnchor");
			syncFieldPrefix		=	(String)request.getAttribute("syncFieldPrefix");
		}
		//EXTERNAL_FORM_BUILDER : END
		String leadId = getParameterValue(request, FieldNames.LEAD_ID);
		String entityId = getParameterValue(request, FieldNames.ENTITY_ID);
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		
		if("locLocationListing".equals(request.getParameter("fromWhere"))){
			entityId=getParameterValue(request, FieldNames.FRANCHISEE_NO);
		}
		//EXTERNAL_FORM_BUILDER : START
		if(!StringUtil.isValidNew(leadId) && "ExtWebPage".equals(requestFrom)){
			leadId = (String)request.getAttribute(FieldNames.LEAD_ID);
		}
		//EXTERNAL_FORM_BUILDER : END
		//P_ENH_SYNC_FIELD end
		List<String> queryList = new ArrayList<String>(); //used to updated all the columns for the tabs

		String mainColumn = "";
		String primaryId = "";
		String leadFranId = "";
		if(StringUtil.isValidNew(leadId)) {
			mainColumn = "LEAD_ID";
			primaryId = leadId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "FRANCHISEE_NO", "LEAD_ID", primaryId); //it is the franchise No that is associated to the lead
		} else if(StringUtil.isValidNew(entityId)) {
			mainColumn = "ENTITY_ID";
			primaryId = entityId;
			leadFranId = SQLUtil.getColumnValue("FS_LEAD_DETAILS", "LEAD_ID", "FRANCHISEE_NO", primaryId); //getting LeadId from from franchiseeNo.
		}

		String tableAnchor = thisSyncField.split("##")[0];
		String fieldName = thisSyncField.split("##")[3];
		String otherField = thisSyncField.split("##")[2];
		thisSyncField = thisSyncField.split("##")[1];
		String fieldValue = getParameterValue(request, syncFieldPrefix+fieldName);	//P_ENH_SYNC_FIELD
		FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
		Field field = fMapping.getField(thisSyncField);
		if("true".equals(otherField)) {
			field = fMapping.getOtherTableField(thisSyncField);
		}
		String tableName = fMapping.getTableName();
		triggerInfo=(Info)triggerMap.get(tableAnchor);
		if(triggerInfo==null){
			triggerInfo=new Info();
		}
		if("Date".equals(field.getDisplayTypeField())) {
			if(StringUtil.isValidNew(fieldValue)) {
				fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
			}
		}
		String readOnlyField="";
		if(StringUtil.isValidNew(actualtableAnchor)){
			readOnlyField=BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(actualtableAnchor).getField(fieldName).getReadOnly();
		}
		if(!"yes".equals(readOnlyField) && (StringUtil.isValid(fieldValue) ||  (fieldValue!=null && "".equals(fieldValue.trim()) ))) {
			if("true".equals(otherField)) {
				String idFieldValue = SQLUtil.getColumnValue(tableName, fMapping.getIdField()[0].getDbField(), mainColumn, primaryId);
				field = fMapping.getOtherTableField(thisSyncField);
				String tbleNme = field.getTableAnchor();
				FieldMappings mappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tbleNme);
				tableName = mappings.getTableName();
				if("ADDRESS".equals(tableName) && StringUtil.isValidNew(entityId) &&  StringUtil.isValidNew(idFieldValue)) {
					String tableAlias = field.getTableName();
					String order = tableAlias.split("_")[1];
					if(field.isPiiEnabled()){
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE FOREIGN_ID="+idFieldValue+" AND ADDRESS_ORDER="+order); //to update in parent field.
					}else{
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FOREIGN_ID="+idFieldValue+" AND ADDRESS_ORDER="+order); //to update in parent field.	
					}
				} else {
					if(StringUtil.isValidNew(leadId)){
					if(field.isPiiEnabled()){
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' ,'pvm@e20' ) WHERE LEAD_ID="+leadId); //to update in parent field.	
					}else{
						queryList.add("UPDATE "+tableName+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId); //to update in parent field.
					}
					}
				}
			} else {
				if(StringUtil.isValidNew(leadId)) {
					if("within".equals(syncModuleKey)  || "fs".equals(syncModuleKey)) {
						if(field.isPiiEnabled()){
							queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE LEAD_ID="+leadId); //to update in parent field.	
						}else{
							queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId); //to update in parent field.
						}	
					} else if("fim".equals(syncModuleKey)) {
						if(StringUtil.isValidNew(leadFranId)) {
							String primaryColumnValue = "ENTITY_ID";

							if("FRANCHISEE".equals(fMapping.getTableName())) {
								primaryColumnValue = "FRANCHISEE_NO";
								if(field.isBuildField()) {
									if(field.isPiiEnabled()) {
										franchiseeColList.add(field.getDbField()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
									} else {
										franchiseeColList.add(field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
									}
								}
							}

							if(field.isPiiEnabled()) {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE "+primaryColumnValue+"="+leadFranId); //to update in parent field.
							} else {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId); //to update in parent field.
							}

							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn",primaryColumnValue);
							triggerInfo.put(field.getFieldName(), dataInfo);
						}
					}

				} else if(StringUtil.isValidNew(entityId)) {
					if("within".equals(syncModuleKey) || "fim".equals(syncModuleKey)) {
						if("FRANCHISEE".equals(fMapping.getTableName())) {
							if(field.isBuildField()) {
								if(field.isPiiEnabled()) {
									franchiseeColList.add(field.getDbField()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
								} else {
									franchiseeColList.add(field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
								}
							}
							if(field.isPiiEnabled()){
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'  ,'pvm@e20' ) WHERE FRANCHISEE_NO="+entityId); //to update in parent field.
							}else{
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId); //to update in parent field.
							}
							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn","FRANCHISEE_NO");
							triggerInfo.put(field.getFieldName(), dataInfo);
							//triggerUpdateMultiPart(eventType, tableAnchor, entityId, eventMap, request,field.getFieldName(),fieldValue,field.getDbField(),"FRANCHISEE_NO");
						} else {
							if(field.isPiiEnabled()){
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' ,'pvm@e20' )  WHERE ENTITY_ID="+entityId); //to update in parent field.	
							}else{
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId); //to update in parent field.
							}
							dataInfo =new Info();
							dataInfo.set("fieldName",field.getFieldName());
							dataInfo.set("fieldValue",fieldValue);
							dataInfo.set("columnName",field.getDbField());
							dataInfo.set("idColumn","ENTITY_ID");
							triggerInfo.put(field.getFieldName(), dataInfo);
							//triggerUpdateMultiPart(eventType, tableAnchor, entityId, eventMap, request,field.getFieldName(),fieldValue,field.getDbField(),"ENTITY_ID");
						}						
					} else if("fs".equals(syncModuleKey)) {
						if(StringUtil.isValidNew(leadFranId)) {
							if(field.isPiiEnabled()) {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
							} else {
								queryList.add("UPDATE "+fMapping.getTableName()+" SET "+field.getDbField()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
							}
						}
					}
					triggerMap.put(tableAnchor, triggerInfo);
				}
			}



			SequenceMap syncTotalaMap = field.getSyncTotalMap();
			if(syncTotalaMap != null) {
				Iterator itr = syncTotalaMap.values().iterator();
				while(itr.hasNext()) {
					SyncWithField sync = (SyncWithField)itr.next();
					if(sync != null) {
						boolean isPiiEnabled = false;
						String syncModule = sync.getSyncModule();
						FieldMappings syncFieldMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(sync.getTableAnchor());
						if(syncFieldMapping != null) {
							Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
							if(syncFieldMain != null) {
								if(syncFieldMain.isPiiEnabled()) {
									isPiiEnabled = true;
								}
							}
//							syncFieldMapping = null;
						}
						triggerInfo=(Info)triggerMap.get(sync.getTableAnchor());
						if(triggerInfo==null){
							triggerInfo=new Info();
						}
						if(actualtableAnchor.equals(sync.getTableAnchor())) { //skipping because for the same table data can be inserted / modified by the main query i.e. from Generic Handler
							continue;
						}

						if(StringUtil.isValidNew(fieldValue) && DateUtil.checkDateValidationForDisplayFormat(fieldValue)) {
							fieldValue = DateUtil.formatDate(fieldValue, DateUtil.DB_FORMAT);
						}
						if(StringUtil.isValidNew(leadId)) {
							if("within".equals(syncModule) || "fs".equals(syncModule)) {
								if(isPiiEnabled){
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadId);	
								}else{
									queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadId);
								}
							} else if("fim".equals(syncModule)) { //for now it is FIM module.
								if(StringUtil.isValidNew(leadFranId)) {
									String primaryColumnValue = "ENTITY_ID";
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName()); //in case of FRANCHISEE table only

									if("FRANCHISEE".equals(sync.getTableName())) {
										primaryColumnValue = "FRANCHISEE_NO";
										if(syncFieldMain != null && syncFieldMain.isBuildField()) {
											if(isPiiEnabled) {
												franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
											} else {
												franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
											}
										}
										
										syncFieldMain = null;
									}

									if(isPiiEnabled) {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE "+primaryColumnValue+"="+leadFranId);
									} else {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE "+primaryColumnValue+"="+leadFranId);
									}

									dataInfo = new Info();
									dataInfo.set("fieldName", sync.getFieldName());
									dataInfo.set("fieldValue", fieldValue);
									dataInfo.set("columnName", sync.getColumnName());
									dataInfo.set("idColumn", primaryColumnValue);
									triggerInfo.put(sync.getFieldName(), dataInfo);
								}
							}
						} else if(StringUtil.isValidNew(entityId)) {

							if("within".equals(syncModule) || "fim".equals(syncModule)) {
								if("FRANCHISEE".equals(sync.getTableName())) {
									Field syncFieldMain = syncFieldMapping.getField(sync.getFieldName());
									if(syncFieldMain != null && syncFieldMain.isBuildField()) {
										if(isPiiEnabled) {
											franchiseeColList.add(sync.getColumnName()+" = AES_ENCRYPT ('"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )");
										} else {
											franchiseeColList.add(sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"'");
										}
									}
									
									if(isPiiEnabled){
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE FRANCHISEE_NO="+entityId);
									}else{
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE FRANCHISEE_NO="+entityId);
									}
									syncFieldMain=null;
									dataInfo =new Info();
									dataInfo.set("fieldName",sync.getFieldName());
									dataInfo.set("fieldValue",fieldValue);
									dataInfo.set("columnName",sync.getColumnName());
									dataInfo.set("idColumn","FRANCHISEE_NO");
									triggerInfo.put(sync.getFieldName(), dataInfo);
									//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"FRANCHISEE_NO");
								} else {
									if(isPiiEnabled){
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' ) WHERE ENTITY_ID="+entityId);
									}else{
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE ENTITY_ID="+entityId);
									}
									dataInfo =new Info();
									dataInfo.set("fieldName",sync.getFieldName());
									dataInfo.set("fieldValue",fieldValue);
									dataInfo.set("columnName",sync.getColumnName());
									dataInfo.set("idColumn","ENTITY_ID");
									triggerInfo.put(sync.getFieldName(), dataInfo);
									//triggerUpdateMultiPart(eventType, sync.getTableAnchor(), entityId, eventMap, request,sync.getFieldName(),fieldValue,sync.getColumnName(),"ENTITY_ID");
								}
							} else if("fs".equals(syncModule)) {
								if(StringUtil.isValidNew(leadFranId)) {
									if(isPiiEnabled) {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = AES_ENCRYPT ( '"+PortalUtils.forSpecialCharForDB(fieldValue)+"','pvm@e20' )  WHERE LEAD_ID="+leadFranId);	
									} else {
										queryList.add("UPDATE "+sync.getTableName()+" SET "+sync.getColumnName()+" = '"+PortalUtils.forSpecialCharForDB(fieldValue)+"' WHERE LEAD_ID="+leadFranId);
									}
								}
							}
						}
						triggerMap.put(sync.getTableAnchor(), triggerInfo);
					}
				}
			}


			String eventType = (String) (request.getParameter("eventType") == null ? "" : request.getParameter("eventType"));

			try {
				if(StringUtil.isValidNew(leadId)) {
					if(StringUtil.isValidNew(leadFranId)) {
						triggerUpdate(eventType, leadFranId, null, request,triggerMap);
					}
				} else {
					triggerUpdate(eventType, entityId, null, request,triggerMap);
				}
				if(queryList.size()>0){
				QueryUtil.batchUpdate(queryList, null);
				}
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String syncField = field.getSyncWithField();
		if(StringUtil.isValidNew(syncField)) {
			queryUpdate(syncField+"##"+fieldName, request, field.getSyncModule(),franchiseeColList);
		}

	}


	private void triggerUpdate(String eventType,String idField,SequenceMap eventMap, HttpServletRequest request,Map<String, Info> triggerMap){
		/*CommonUtil commonUtil=new CommonUtil();
		try{
			Info auditInfo=new Info();
			int gEventType = 0;
			if(eventType.equals(EventType.CREATE))gEventType = 0;
			else if(eventType.equals(EventType.MODIFY))gEventType = 1;
			else if(eventType.equals(EventType.DELETE))gEventType = 2;
			Info infoBeforeUpdate=new Info();
			Info triggerInfoAfterUpdate=null;
			TriggerHelper triggerHelper = new TriggerHelper();
			if (eventType != null && eventType.equals(EventType.CREATE) || eventType.equals(EventType.MODIFY)) {
				//EXTERNAL_FORM_BUILDER : START
				String modifierUserNo	=	(String)request.getSession().getAttribute("user_no");
				String requestFrom	=	request.getParameter("requestFrom");
				if("yes".equals(request.getParameter("fromRestAPI"))){
					modifierUserNo	=	"2";
				}else if("ExtWebPage".equals(requestFrom)){
					modifierUserNo	=	"7777777";
				}
				if(!StringUtil.isValidNew(modifierUserNo)){
					modifierUserNo	=	"1";
				}
				//EXTERNAL_FORM_BUILDER : END
				Iterator itr = triggerMap.entrySet().iterator();
				while(itr.hasNext()) {
					Entry e=(Entry)itr.next();
					String tableAnchor=(String)e.getKey();
					String newTriggerFormName=SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", new String[]{"MODULE","TABLE_ANCHOR"}, new String[]{"fim",tableAnchor});
					Info triggerInfo = (Info)e.getValue();
					Iterator it = triggerInfo.getValuesIterator();
					while(it.hasNext()) {
						Info dataInfo = (Info)it.next();
						auditInfo.set(dataInfo.getString("fieldName"),dataInfo.getString("fieldValue"));
						triggerInfoAfterUpdate=commonUtil.convertDateFields(commonUtil.convertIdFields(auditInfo, request,tableAnchor), tableAnchor);
						auditInfo =commonUtil.convertDateFields(auditInfo, tableAnchor);
						auditInfo.set(FieldNames.TABLE_ANCHOR,tableAnchor);
						if(eventType.equals(EventType.MODIFY)){
							FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
							String dbTable=fMapping.getTableName();
							String oldColumnValue=SQLUtil.getColumnValue(dbTable, dataInfo.getString("columnName"), new String[]{dataInfo.getString("idColumn")}, new String[]{idField});
							if(StringUtil.isValidNew(oldColumnValue)){
								infoBeforeUpdate.set(dataInfo.getString("fieldName"),oldColumnValue);	
							}
						}
						triggerHelper.sendFieldTriggers(newTriggerFormName, auditInfo, infoBeforeUpdate,Integer.parseInt(idField), Integer.parseInt(modifierUserNo), gEventType,triggerInfoAfterUpdate,triggerInfoAfterUpdate);	//P_ENH_SYNC_FIELD
					}
					triggerHelper.sendFormTriggers(newTriggerFormName, gEventType, Integer.parseInt(idField), Integer.parseInt(modifierUserNo));	//P_ENH_SYNC_FIELD
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	private void triggerUpdateMultiPart(String eventType,String idField,SequenceMap eventMap, MultipartRequest request,Map<String, Info> triggerMap){
		/*CommonUtil commonUtil=new CommonUtil();
		try{
			int gEventType = 0;
			if(eventType.equals(EventType.CREATE))gEventType = 0;
			else if(eventType.equals(EventType.MODIFY))gEventType = 1;
			else if(eventType.equals(EventType.DELETE))gEventType = 2;
			TriggerHelper triggerHelper = new TriggerHelper();
			if (eventType != null && eventType.equals(EventType.CREATE) || eventType.equals(EventType.MODIFY)) {
				//EXTERNAL_FORM_BUILDER : START
				String modifierUserNo	=	(String)request.getSession().getAttribute("user_no");
				String requestFrom	=	request.getParameter("requestFrom");
				if("yes".equals(request.getParameter("fromRestAPI"))){
					modifierUserNo	=	"2";
				}else if("ExtWebPage".equals(requestFrom)){
					modifierUserNo	=	"7777777";
				}
				if(!StringUtil.isValidNew(modifierUserNo)){
					modifierUserNo	=	"1";
				}
				//EXTERNAL_FORM_BUILDER : END
				Iterator itr = triggerMap.entrySet().iterator();
				while(itr.hasNext()) {
					Entry e=(Entry)itr.next();
					String tableAnchor=(String)e.getKey();
					String newTriggerFormName=SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", new String[]{"MODULE","TABLE_ANCHOR"}, new String[]{"fim",tableAnchor});
					Info triggerInfo = (Info)e.getValue();
					Iterator it = triggerInfo.getValuesIterator();
					while(it.hasNext()) {
						Info infoBeforeUpdate=new Info();
						Info triggerInfoAfterUpdate=null;
						Info auditInfo=new Info();
						Info dataInfo = (Info)it.next();
						auditInfo.set(dataInfo.getString("fieldName"),dataInfo.getString("fieldValue"));
						triggerInfoAfterUpdate=commonUtil.convertDateFields(commonUtil.convertIdFields(auditInfo, request.getHttpServletRequest(),tableAnchor), tableAnchor);
						auditInfo =commonUtil.convertDateFields(auditInfo, tableAnchor);
						auditInfo.set(FieldNames.TABLE_ANCHOR,tableAnchor);
						if(eventType.equals(EventType.MODIFY)){
							FieldMappings fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
							if(fMapping != null) {
								String dbTable=fMapping.getTableName();
								String oldColumnValue=SQLUtil.getColumnValue(dbTable, dataInfo.getString("columnName"), new String[]{dataInfo.getString("idColumn")}, new String[]{idField});
								if(StringUtil.isValidNew(oldColumnValue)){
									infoBeforeUpdate.set(dataInfo.getString("fieldName"),oldColumnValue);	
								}
							}
						}
						triggerHelper.sendFieldTriggers(newTriggerFormName, auditInfo, infoBeforeUpdate,Integer.parseInt(idField), Integer.parseInt(modifierUserNo), gEventType,triggerInfoAfterUpdate,triggerInfoAfterUpdate);
					}
					triggerHelper.sendFormTriggers(newTriggerFormName, gEventType, Integer.parseInt(idField), Integer.parseInt(modifierUserNo));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}

	//P_ENH_SYNC_FIELD start
	int index;
	public void setIndex(int index){
		this.index = index;
	}

	public String getParameterValue(HttpServletRequest request, String paramName){
		String paramValue = null;
		if("yes".equals(request.getParameter("fromRestAPI"))){
			if(request.getParameterValues(FieldNames.TABLE_ANCHOR)!=null){
				String tableAnchor = request.getParameterValues(FieldNames.TABLE_ANCHOR)[index];
				if(FieldNames.TABLE_ANCHOR.equals(paramName)){
					paramValue = tableAnchor.substring(0, tableAnchor.lastIndexOf("_"));
				}else if(FieldNames.ENTITY_ID.equals(paramName)){
					String linkFieldName = FieldNames.FRANCHISEE_NO;
					String actualTableAnchor = tableAnchor.substring(0, tableAnchor.lastIndexOf("_"));
					FieldMappings mappings = DBUtil.getInstance().getFieldMappings("franchisees");
					ForeignTable foreignTable = mappings.getForeignTable(actualTableAnchor);
					if(foreignTable!=null){
						SequenceMap linkFieldMap = foreignTable.getLinkFieldsMap();
						if(linkFieldMap!=null){
							LinkField linkField = (LinkField) linkFieldMap.get(0);
							linkFieldName = linkField.getForeignField();
						}
					}
					paramValue = request.getParameter(tableAnchor + linkFieldName);
				}else{
					paramValue = request.getParameter(tableAnchor + paramName);
				}
			}
		}else{
			paramValue = StringUtil.toCommaSeparated(request.getParameterValues(paramName));
		}
		return paramValue;
	}
	public String getParameterValue(MultipartRequest request, String paramName){
		String paramValue = null;
		if("yes".equals(request.getParameter("fromRestAPI"))){
			if(request.getParameterValues(FieldNames.TABLE_ANCHOR)!=null){
				String tableAnchor = request.getParameterValues(FieldNames.TABLE_ANCHOR)[index];
				if(FieldNames.TABLE_ANCHOR.equals(paramName)){
					paramValue = tableAnchor.substring(0, tableAnchor.lastIndexOf("_"));
				}else if(FieldNames.ENTITY_ID.equals(paramName)){
					String linkFieldName = FieldNames.FRANCHISEE_NO;
					String actualTableAnchor = tableAnchor.substring(0, tableAnchor.lastIndexOf("_"));
					FieldMappings mappings = DBUtil.getInstance().getFieldMappings("franchisees");
					ForeignTable foreignTable = mappings.getForeignTable(actualTableAnchor);
					if(foreignTable!=null){
						SequenceMap linkFieldMap = foreignTable.getLinkFieldsMap();
						if(linkFieldMap!=null){
							LinkField linkField = (LinkField) linkFieldMap.get(0);
							linkFieldName = linkField.getForeignField();
						}
					}
					paramValue = request.getParameter(tableAnchor + linkFieldName);
				}else{
					paramValue = request.getParameter(tableAnchor + paramName);
				}
			}
		}else{
			paramValue = StringUtil.toCommaSeparated(request.getParameterValues(paramName));
		}
		return paramValue;
	}
	//P_ENH_SYNC_FIELD end
}
