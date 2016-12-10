/**
* Description: This is the form DAO which defines all the functions of Form to provide basic methods to developer
* @author abhishek gupta 
* @version 1.1.1.1
* @created Nov 14, 2011
*/

/**
-----------------------------------------------------------------------------------------------------------------------------------------------
Version No.				Date			By								Against									Function Changed	Comments
-----------------------------------------------------------------------------------------------------------------------------------------------
PW_FORM_VERSION			15 July 2013	Veerpal Singh					Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
ecsc-20160603-007         09/06/2016      Kirti Kumar    Audit form got removed once question added(Special Character in case of PPT)
-----------------------------------------------------------------------------------------------------------------------------------------------
**/
package com.home.builderforms;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.exception.AppException;
import com.home.builderforms.BuilderFormFieldNames;
import com.home.builderforms.BaseFormFactory;
import com.home.builderforms.BuilderCustomTab;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.IDGenerator;
import com.home.builderforms.IntConstants;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.DBColumn;
import com.home.builderforms.sqlqueries.DBQuery;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLQueryGenerator;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.TableXMLDAO;


public class FormBaseDAO{
	
	protected static BaseFormFactory baseFormFactory = null;
	protected static ServletContext context = null;

//	protected DBUtil		dbUtil;
	protected String 		tableAnchor;
	protected String 		tableName;
	protected FieldMappings     fieldMappings;
	protected Field fld; 
	protected String loc;
	protected String location;

	public FormBaseDAO(){
	}
	public FormBaseDAO(String psTableAnchor){
		tableAnchor			= psTableAnchor;
		init();
	}
	public void init(){
//		dbUtil 					= DBUtil.getInstance();
		if(baseFormFactory == null)
			baseFormFactory 		= BaseFormFactory.getBaseFormFactoryInstance();
	}

//	public void processEvent(com.appnetix.app.struts.EventHandler.Event event, HttpServletRequest request) throws AppException{
//		HttpSession session 		= request.getSession();
//		Info infoBeforeUpdate		= null;
//		Info deleteInfo				= null;
//		Object id					= null;
//		Info info					= event.getInfo();
//		String eventType			= event.getEventType();
//		
//		if(event.getEventType().equals(EventType.CREATE) || event.getEventType().equals(EventType.CREATE_BATCH)){
//			try{
//				Object[] ids = (Object [])create(event.getInfo());
//				if (ids != null && ids.length != 0)
//					id =  ids[0];
//			}catch(Exception  e3){
//				throw new AppException("Exception in FormBaseDAO.processEvent()");
//			}
//			event.setID(id);
//		}else if(eventType.equals(EventType.MODIFY) || eventType.equals(EventType.MODIFY_BATCH)){
//			try{
//				id		= info.getIDObject()[0];
//				infoBeforeUpdate = getDetailsInfo(new Integer(id.toString()));
//				if(infoBeforeUpdate==null) id =  create(info)[0];
//				else modify(info);
//			}catch(Exception  e2){e2.printStackTrace();
//				throw new AppException("Exception in FormBaseDAO.processEvent()");
//			}
//			event.setID(id);
//		}else if(eventType.equals(EventType.DELETE) || eventType.equals(EventType.DELETE_BATCH)){
//			try{
//				id		= info.getIDObject()[0];
//				infoBeforeUpdate = getDetailsInfo((Integer)id);
//				delete(info);
//		         WebCacheBypass webCacheIP = new WebCacheBypass(request);
//		         String IPaddress = webCacheIP.getRemoteAddr();
//		         if(id != null && !id.toString().equals(""))
//		        	 logDeleteDetails(infoBeforeUpdate, id.toString(), (String)session.getAttribute("user_no"), IPaddress);
//			}catch(Exception  e1){
//				e1.printStackTrace();
//				throw new AppException("Exception in FormBaseDAO.processEvent()");
//			}
//		}
//
//		if(event.getTriggerFormName()!=null){
//			info.set(FieldNames.TABLE_ANCHOR,event.getTableName());
//	
//			int gEventType = 0;
//			if(eventType.equals(EventType.CREATE))gEventType = 0;
//			else if(eventType.equals(EventType.MODIFY))gEventType = 1;
//			else if(eventType.equals(EventType.DELETE))gEventType = 2;
//	              
//			Info auditInfo = convertDateFields(convertIdFields(info, request));
//			Info auditInfoBeforeUpdate = convertIdFields(infoBeforeUpdate, request);
//		}
//	}

	public static void beginTransaction(String connectionName) {
		try{
			SQLUtil.beginTransaction(connectionName);
		}catch(Exception  e){
		}
	}

	public static void rollbackTransaction(String connectionName){
		try{
			SQLUtil.rollbackTransaction(connectionName);
		}catch(Exception  e){
		}
	}

	public static void commitTransaction(String connectionName){
		try{
			SQLUtil.commitTransaction(connectionName);
		}catch(Exception  e){
		}
	}

	public FieldMappings getFieldMappings(){
		return DBUtil.getInstance().getFieldMappings(tableAnchor);
	}
	
	public FieldMappings getFieldMappings(String tableAnchor){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().getFieldMappings(tableAnchor);
	}
	
	public FieldMappings getFieldMappingsByPath(String xmlPath, String tabelAnchor){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().getFieldMappingsByPath(xmlPath, tabelAnchor);
	}
	
	public HashMap getFimTablesWithMergableFieldsData(String tableAnchor){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().getFimTablesWithMergableFieldsData(tableAnchor);
	}
	
	public HashMap getFimAllMergableKeyFieldsData(String tableAnchor){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().getFimAllMergableKeyFieldsData(tableAnchor);
	}

	public boolean removeFieldMappings(){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().removeFieldMappings(tableAnchor);
	}
	
	public boolean removeFieldMappings(String tableAnchor){
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().removeFieldMappings(tableAnchor);
	}

	public HashMap getTableMappings()
	{
//		if(dbUtil == null) 
//			dbUtil 	= DBUtil.getInstance();
		return DBUtil.getInstance().getTableMappings();
    }
	
	public Object[] create(Info info) throws SQLException{
		return createHelper(tableAnchor, info,0);
	}
	
	public Object[] create(String tableAnchor, Info info) throws SQLException{
		return createHelper(tableAnchor, info,0);
	}

	public Object[] createHelper(String tableAnchor, Info info,int count)throws SQLException {
		try {
			Integer[] id = new Integer[getFieldMappings(tableAnchor).getIdField().length];
			for (int i=0; i<id.length; i++) {
				id[i] = new Integer(IDGenerator.getNextKey());
			}
			info.setID(id);
			SQLUtil.createRecord(getFieldMappings(tableAnchor),info,true);
			return info.getIDObject();
		} catch(com.mysql.jdbc.MysqlDataTruncation e) {		 
			return info.getIDObject();
		} catch(SQLException e) {
			if (count < 5) {
				createHelper(tableAnchor, info,++count);
			}else{
				throw e;
			}
		}
		return null;
	}
	/**
	 * Multiple data insert for same table using info array
	 * @param tableAnchor
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public Object[] createData(String tableAnchor, Info[] info) throws SQLException{
		return createDataHelper(tableAnchor, info,0);
	}
	/**
	 * Multiple data insert for same table using array of info
	 * @param tableAnchor
	 * @param info
	 * @param count
	 * @return
	 * @throws SQLException
	 */
	public Object[] createDataHelper(String tableAnchor, Info[] info,int count)throws SQLException {
		try {
			Integer[] id = new Integer[getFieldMappings(tableAnchor).getIdField().length];
			for(int j = 0; j < info.length; j++) {
				for (int i=0; i<id.length; i++) {
					id[i] = new Integer(IDGenerator.getNextKey());
				}
				info[j].setID(id);
			}
			/**
			 * Passing info array after id generation for each info object
			 */
			SQLUtil.createDataRecord(getFieldMappings(tableAnchor),info,true);
		} catch(SQLException e) {
			if (count < 5) {
				createDataHelper(tableAnchor, info,++count);
			}else{
				throw e;
			}
		}
		return null;
	}

	public void modify(Info info) {
		modify(tableAnchor, info);
	}
	public void modify(String tableAnchor, Info info) {
		try{
			SQLUtil.modifyRecord(getFieldMappings(tableAnchor),info);
		} catch(Exception e){
		}
	}
	
	public void modify(Info info, SequenceMap paramMap) {
		modify(tableAnchor, info, paramMap);
	}
	public void modify(String tableAnchor, Info info, SequenceMap paramMap) {
		try{
			setNullIntsToDefault(info, true);
			SQLUtil.modifyRecord(getFieldMappings(tableAnchor),info, paramMap);
		} catch(Exception e){
		}
	}
	public void replace(Info info) {
		replace(tableAnchor, info);
	}
	
	public void replace(String tableAnchor, Info info) {
		try{
			setNullIntsToDefault(info);
			SQLUtil.replaceRecord(getFieldMappings(tableAnchor),info);
		} catch(Exception e) {
		}
	}

	public void delete(Integer[] id) throws SQLException{
		delete(tableAnchor, id);
	}
	public void delete(String tableAnchor, Integer[] id) throws SQLException{
		SQLUtil.deleteRecord(getFieldMappings(tableAnchor),id);
	}

	public void delete(Info info) throws SQLException{
		delete(tableAnchor, info);
	}
	public void delete(String tableAnchor, Info info) throws SQLException{
		SQLUtil.deleteRecord(getFieldMappings(tableAnchor),info);
	}

	public void deleteRecords(Integer[] ids) throws SQLException{
		SQLUtil.deleteRecords(getFieldMappings(),ids);
	}

	public void deleteRecords(SequenceMap map) throws SQLException{
		deleteRecords(tableAnchor,  map);
	}
	public void deleteRecords(String tableAnchor, SequenceMap map) throws SQLException{
		SQLUtil.deleteRecords(getFieldMappings(tableAnchor),map);
	}

	public Info getDetailsInfo(Integer id) {
		return getDetailsInfo(tableAnchor, id);
	}
	public Info getDetailsInfo(String tableAnchor, Integer id) {
		Info info = null;
		try{
			info = SQLUtil.getDetailsInfo(getFieldMappings(tableAnchor),id);
		} catch(RecordNotFoundException rnfe){
		} catch(Exception e){
		}
		return info ;
	}

	public Info getDetailsInfo(SequenceMap map) throws SQLException,RecordNotFoundException{
		return getDetailsInfo(tableAnchor, map);
	}
	public Info getDetailsInfo(String tableAnchor, SequenceMap map) throws SQLException,RecordNotFoundException{
		Info info = null;
		try {
			info= SQLUtil.getDetailsInfo(getFieldMappings(tableAnchor), map);
		} catch(RecordNotFoundException rnfe){
		} catch(Exception e){
		}
		return info ;
	}
	public int getCount(SequenceMap map) throws SQLException, RecordNotFoundException{
		return SQLUtil.getCount(getFieldMappings(), map);
	}
	public SequenceMap getSummaryCollection(SequenceMap map) throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryCollection(getFieldMappings(), map);
	}
	public SequenceMap getSummaryCollectionMap(String tableAnchor) throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryCollection(getFieldMappings(tableAnchor), null);
	}
	public SequenceMap getSummaryCollectionMap(String tableAnchor, SequenceMap map) throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryCollection(getFieldMappings(tableAnchor), map);
	}

	public Info getSummaryInfo(Integer[] id) throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryInfo(getFieldMappings(),id);
	}

	public SequenceMap getSummaryCollection() throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryCollection(getFieldMappings());
	}

	public SequenceMap getCollection(String[] fieldNames,SequenceMap params, String orderBy) {
		try {
			return SQLUtil.getCollection(getFieldMappings(),fieldNames,params,orderBy);
		} catch(RecordNotFoundException rnfe){
		} catch(Exception e){
		}
		return null;
	}
	public SequenceMap getCollection(SequenceMap params, String orderBy) {
		try{
			return getCollection(getFieldMappings().getAllFieldNames(),params,orderBy);
		} catch(Exception e){
		}
		return null;
	}

	public SequenceMap getCollection(String[] fieldNames,SequenceMap params) {
		try {
			return SQLUtil.getCollection(getFieldMappings(),fieldNames,params);
		} catch(RecordNotFoundException rnfe) {
		} catch(Exception e){
		}
		return null;
	}

	public SequenceMap getCollection(SequenceMap params) {
		return getCollection(getFieldMappings().getAllFieldNames(),params);
	}

	public String[] getIDField()
	{
		Field[] fields = getFieldMappings().getIdField();
		String[] fieldName = new String[fields.length];
		for(int i=0; i<fields.length; i++) {
			fieldName[i] = fields[i].getFieldName();
		}
		return fieldName;
	}

	public Info getSummaryInfo(String[] fieldNames,Integer[] id) throws SQLException,RecordNotFoundException
	{
		return SQLUtil.getSummaryInfo(getFieldMappings(),fieldNames,id);
	}

	public Object getFieldValue(String fieldName,Integer[] id) throws SQLException,RecordNotFoundException
	{
		return getSummaryInfo(new String[]{fieldName},id).get(fieldName);
	}

	public void setNullIntsToDefault(Info info)
	{
		setNullIntsToDefault(info, false);
	}
	public void setNullIntsToDefault(Info info, boolean setSetObjectsOnly){
		setNullIntsToDefault(tableAnchor, info, setSetObjectsOnly);
	}
	public void setNullIntsToDefault(String tableAnchor, Info info, boolean setSetObjectsOnly){
		FieldMappings mappings	= getFieldMappings(tableAnchor);
		Field[] idFields		= mappings.getIdField();
		String[] fieldNames = mappings.getAllFieldNames();
		for (int i=0;i<fieldNames.length;i++)
		{
			String fieldName = fieldNames[i];
			String dataType = mappings.getDataType(fieldName);
			if (
				Field.DataType.INTEGER.equals(dataType) && info.getObject(fieldName) == null
			){
				if(
					setSetObjectsOnly &&
					!info.isSet(fieldName) &&
					isIdField(fieldName, idFields)
					){
					continue;
				}
				info.set(fieldName,new Integer(IntConstants.DEFAULT_INT));
			}
		}
	}
	private boolean isIdField(String fieldName, Field[] idFields){
		if (idFields != null) {
			for (int i=0; i<idFields.length; i++){
				String idFieldName = idFields[i].getFieldName();
				if (idFieldName != null && idFieldName.equals(fieldName)){
					return true;
				}
			}
		}
		return false;
	}

//	private Info convertIdFields(Info infoToConvert,HttpServletRequest request){
//		Info convertedInfo = new Info();
//		try{
//			if (infoToConvert != null){			
//				for (Iterator it = infoToConvert.getKeySetIterator(); it.hasNext();){
//					String key = (String) it.next();
//                                        Field fld = getFieldMappings().getField(key);
//					if (fld != null){
//						ExportDataCollector exportDCol=  new ExportDataCollector();
//						exportDCol.init(request,tableAnchor);
//						String newValue ="";
//						if(fld.getTransformMethodParam()!=null && !"".equals(fld.getTransformMethodParam())) {
//							newValue = exportDCol.transform(fld,infoToConvert.get(key),infoToConvert.getString(FieldNames.getVal(fld.getTransformMethodParam())));
//						} else if(fld.getTransformMethod()!=null && !"".equals(fld.getTransformMethod())) {
//							newValue = exportDCol.transform(fld,infoToConvert.get(key));
//						}else {   
//							newValue=infoToConvert.get(key);
//						}
//						convertedInfo.set(key, newValue);
//					}else{
//						convertedInfo.set(key, infoToConvert.get(key));
//					}
//				}
//			}
//		}catch(Exception e){
//		}
//		return convertedInfo;
//	}

//	private Info convertDateFields(Info infoToConvert){
//		Info convertedInfo = new Info();
//		try{
//			if (infoToConvert != null){
//				for (Iterator it = infoToConvert.getKeySetIterator(); it.hasNext();){
//					String key = (String) it.next();
//					Field fld = getFieldMappings().getField(key);
//					if (fld != null && fld.getDataType() != null && fld.getDataType().equals("Date")){
//						//String newValue = PortalUtils.getAuditFormatDate(infoToConvert.get(key));
//						String newValue = BaseUtils.getAuditFormatDate(infoToConvert.get(key));    //For Product_Seperation_BL By Amar Singh.
//						convertedInfo.set(key, newValue);
//					}else{
//						convertedInfo.set(key, infoToConvert.get(key));
//					}
//				}
//			}
//		}catch(Exception e){
//		}
//		return convertedInfo;
//	}
        
//    private void logDeleteDetails(Info infoBeforeUpdate, String id, String UserNo, String IPaddress){
//       FieldMappings fls = getFieldMappings();
//       String fieldName   =   "";
//       String dbFieldName    =   "";
//       String value    =   "";
//       String   queryString ="";
//       StringBuffer insertQuery = new StringBuffer();
//       try{
//           if (fls!=null && infoBeforeUpdate!=null){
//               String tName = fls.getTableName();
//			   String fieldType = null;
//               Iterator it = infoBeforeUpdate.getKeySetIterator();
//               while (it.hasNext()){
//				   String tempValue = null;
//                   fieldName = (String)it.next();
//				   fieldType = fls.getDataType(fieldName);
//				   dbFieldName += fls.getDbField(fieldName)+", ";
//				   if(fieldType != null && fieldType.equals(Field.DataType.DATE)){
//					   tempValue = 	DateTime.getSimleDateChange(infoBeforeUpdate.getString(fieldName));
//				   }else 
//					   tempValue = infoBeforeUpdate.getString(fieldName); 
//                   value += "\""+tempValue+"\""+", ";
//               }												  
//               dbFieldName  =   dbFieldName.substring(0,dbFieldName.length()-2);
//               value  =   value.substring(0,value.length()-2);
//               queryString  =   "INSERT INTO "+tName+"("+dbFieldName+")"+"VALUES "+"("+value+")";
//                insertQuery.append(" INSERT INTO DELETE_LOG_DETAILS(TABLE_NAME, PRIMARY_KEY, USER_NO, USER_IP, DELETE_INFO) VALUES (?,?,?,?,?) ");
//			   String[] params = {tName,id,UserNo,IPaddress,queryString};
//               QueryUtil.executeInsert(insertQuery.toString(),params);
//           }
//        }catch(Exception e){
//        }
//	}
    /**
     * This will process xml file with containing all nodes in object, later this will used for generating xml file as same
     * @param tableAnchor
     * @return
     * @throws AppException
     */
    public Info extractInfo(String tableAnchor) throws AppException {
		Info info			= null;
		try {
			FieldMappings fieldMappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
			if(fieldMappings == null){
				return null;
			}
			String[] sArrFieldNames		= fieldMappings.getAllFieldNames();
			if(sArrFieldNames == null){
				return null;
			} else {
				
			}
		}catch(Exception e){
			throw new AppException("Exception in extractInfo");
		}
		return info;
	} 

    public static String getRequestValue(HttpServletRequest request, String colName) {
    	String val = null;
    	try {
    		val = request.getParameter(colName);
    		if(val == null || val.equals("null")) {
    			val = (String)request.getAttribute(colName);
    		}
    	} catch(Exception e) {
    		return val;
    	}
		return val;
	}
    
    public static String getAttributeValue(Node node,String name) {
		String ret = null;
		NamedNodeMap attributes = node.getAttributes();
		Node attrNode = attributes.getNamedItem(name);
		if (attrNode!=null)	{
			ret = attrNode.getNodeValue();
		}
		return ret;
	}
    
	public static String getTagText(Node node,String name) {
		String tagText = null;
		Node retNode = getNodeInChildren(node,name);
		if (retNode!=null) {
			tagText = retNode.getFirstChild().getNodeValue();
		}
		return tagText;
	}

	public static String setTagText(Node node,String name, String val) {
		String tagText = null;
		Node retNode = getNodeInChildren(node,name);
		if (retNode!=null) {
			retNode.getFirstChild().setNodeValue(val);
		}
		return tagText;
	}
	
	public static String setTagTextVal(Document doc, Node node, String name, String val) {
		String tagText = null;
		Node retNode = getNodeInChildren(node,name);
		if(retNode == null) {
			Element activeEle = doc.createElement(name);
			activeEle.appendChild(doc.createTextNode(val));
			node.appendChild(activeEle);
		}
		if (retNode!=null) {
			retNode.getFirstChild().setNodeValue(val);
		}
		return tagText;
	}
	
	public static String setTagAttr(Node retNode, String name, String val) {
		String tagText = null;
		boolean flag = false;
		if (retNode!=null) {
			NamedNodeMap attr = retNode.getAttributes();
			//attr.getNamedItem("").setNodeValue("");
			for (int j = 0; j < attr.getLength(); j++) {
				Node tempNode = attr.item(j);
	            if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
	                Attr attribute = (Attr) tempNode;
	                String attrName = attribute.getName();
	                if(attrName.equals(name)) {
	                	attribute.setNodeValue(val);
	                	flag = true;
	                	break;
	                }
	            }
	        }
			if(!flag) {
				Element e = (Element)retNode;
				e.setAttribute(name, val);
			}
		}
		return tagText;
	}
	
	/**
	 * @PW_FORM_VERSION
	 * This method will remove all attributes of a node.
	 */
	public static void removeTagAttr(Node retNode) {
		removeTagAttr(retNode, null);
	}
	
	public static void removeTagAttr(Node retNode, String name) {
		String tagText = null;
		boolean flag = false;
		if (retNode!=null) {
			NamedNodeMap attr = retNode.getAttributes();
			//attr.getNamedItem("").setNodeValue("");
			for (int j = 0; j < attr.getLength(); j++) {
				Node tempNode = attr.item(j);
	            if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
	                Attr attribute = (Attr) tempNode;
	                String attrName = attribute.getName();
	                if(!StringUtil.isValid(name)){ //PW_FORM_VERSION
	                	attr.removeNamedItem(attrName);
	                }
	                else if(attrName.equals(name)) {
	                	attr.removeNamedItem(name);
	                	break;
	                }
	            }
	        }
		}
	}
	
	public static Node getNodeInChildren(Node node,String name) {
		Node retNode = null;
		NodeList children	= node.getChildNodes();
		for (int i=0;i<children.getLength();i++) {
			Node child = children.item(i);
			if (child.getNodeType()==Node.TEXT_NODE) continue;
			String nodeName = child.getNodeName();
			if (name.equals(nodeName)) {
				retNode = child;
				break;
			}
		}
		return retNode;
	}
	
	public static boolean removeNode(Node node,String name) {
		try {
			Node colNode = getNodeInChildren(node, name);
			/**
			 * OPTION_VIEW_H_V
			 * Delete if child exist.
			 * This will prevent exception thrown while deleting child node when no Child Node there 
			 */
			if(colNode != null)
				node.removeChild(colNode);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeAllNodes(Node node,String name) {
		try {
			Node[] colNode = getNodesInChildren(node, name);
			for (int i=0;i<colNode.length;i++) {
				node.removeChild(colNode[i]);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Element getElementNode(Document doc,String fldName, String fldVal) {
		try {
			Element fldEle = null;
			fldEle = doc.createElement(fldName);
			if(StringUtil.isValidNew(fldVal)) {
				fldEle.appendChild(doc.createTextNode(fldVal));
			}
			return fldEle;
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean alterTableData(Info info) {
		try {
			String tableName = info.getString(BuilderFormFieldNames.TABLE_NAME);
			String colName = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME);
			String colName1 = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME+"1");
			String colType = info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE);
			String colSize = info.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH);
			String colValType = info.getString(BuilderFormFieldNames.FLD_VALIDATION_TYPE);
			String coloption = info.getString(BuilderFormFieldNames.DROPDOWN_OPTION);
			//P_Enh_Sync_Fields starts
			String existingColumnType = info.getString("existingColumnType");
			String existingColumnSize = info.getString("existingColumnSize");
			//P_Enh_Sync_Fields ends
			
			
			if("file".equals(colType) && ModuleUtil.cmImplemented()) {//P_CM_B_62188
				return true;
			}
			//BB-20150319-268(FIM Campaign Center) starts
            String includeInCampaign = info.getString(FieldNames.INCLUDE_IN_CAMPAIGN);
            String oldValue = info.getString("oldIncludeInCampaign");
            String modifyFld = info.getString("modifyFld");
            if("yes".equals(modifyFld) && !StringUtil.isValidNew(oldValue)) {
                oldValue = "1";
            }
            //BB-20150319-268(FIM Campaign Center) ends
			/**
			 * Allow Object based query generation to reduce code
			 */
			DBColumn[] dbColumn = null;
			if(StringUtil.isValidNew(coloption) && "2".equals(coloption)) {
				dbColumn = new DBColumn[2];
				dbColumn[0] = new DBColumn();
				dbColumn[1] = new DBColumn();
				
				dbColumn[0].setAction(SQLQueryGenerator.ADD);
				dbColumn[1].setAction(SQLQueryGenerator.ADD);
			} else if("varchar".equals(colType) && StringUtil.isValidNew(includeInCampaign)) { //BB-20150319-268(FIM Campaign Center) starts
                if(includeInCampaign.equals(oldValue)) {
                	//System.out.println("815===============when checkbox are not changed");
                    dbColumn = new DBColumn[1];
                    dbColumn[0] = new DBColumn();
                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
                } else if("yes".equals(modifyFld)) {
                	//System.out.println("815===============when changed");
                	dbColumn = new DBColumn[2];
                    dbColumn[0] = new DBColumn();
                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
                    dbColumn[1] = new DBColumn();
                }
                else if("0".equals(includeInCampaign)) {
                	//System.out.println("821===============add with include in campaign");
                    dbColumn = new DBColumn[2];
                    dbColumn[0] = new DBColumn();
                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
                    dbColumn[1] = new DBColumn();
                } else if("1".equals(includeInCampaign)) {
                	//System.out.println("827===============add without include in campaign");
                	dbColumn = new DBColumn[1];
                    dbColumn[0] = new DBColumn();
                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
                }
			} else {
				if(StringUtil.isValidNew(includeInCampaign)) {
					if(includeInCampaign.equals(oldValue)) {
						dbColumn = new DBColumn[1];
						dbColumn[0] = new DBColumn();
						dbColumn[0].setAction(SQLQueryGenerator.ADD);
					} else if("yes".equals(modifyFld)) {
	                	dbColumn = new DBColumn[2];
	                    dbColumn[0] = new DBColumn();
	                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
	                    dbColumn[1] = new DBColumn();
	                }
	                else if("0".equals(includeInCampaign)) {
	                    dbColumn = new DBColumn[2];
	                    dbColumn[0] = new DBColumn();
	                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
	                    dbColumn[1] = new DBColumn();
	                } else if("1".equals(includeInCampaign)) {
	                	dbColumn = new DBColumn[1];
	                    dbColumn[0] = new DBColumn();
	                    dbColumn[0].setAction(SQLQueryGenerator.ADD);
	                }
					//BB-20150319-268(FIM Campaign Center) ends
				} else {
					dbColumn = new DBColumn[1];
					dbColumn[0] = new DBColumn();
					dbColumn[0].setAction(SQLQueryGenerator.ADD);
				}
			}
			dbColumn[0].setColDBName(colName);
//			StringBuffer alertQuery  = new StringBuffer("ALTER TABLE ");
//			alertQuery.append(tableName).append(" ADD COLUMN ");
//			alertQuery.append(colName).append(" ");
			if(StringUtil.isValidNew(colSize) && ("varchar".equals(colType) || "int".equals(colType))) 
			{
				//formBuilder_issue starts
				//int varcharFieldCount = 0;
				int varcharFieldSize = 0;
				//formBuilder_issue ends
				if("varchar".equals(colType)) {
					boolean canAddVarchar = true;
					String selectQuery = "SELECT * FROM "+tableName+" LIMIT 0, 1";
					ResultSet result  = QueryUtil.getResult(selectQuery, null);
					java.sql.ResultSetMetaData rsmd = result.getMetaData();
					
					int colCount = rsmd.getColumnCount();
					for(int i = 1; i<=colCount; i++) {
						//formBuilder_issue starts
						int type = rsmd.getColumnType(i);
						if (type == Types.VARCHAR || type == Types.CHAR) {
							varcharFieldSize += rsmd.getColumnDisplaySize(i);
						}
						/*if("VARCHAR".equals(rsmd.getColumnTypeName(i))) { //check only for varchar
							varcharFieldCount++;
						}*/
						//formBuilder_issue ends
					}
				}
				//if(varcharFieldCount > 150) { //formBuilder_issue starts
				if(varcharFieldSize > 21000) { //formBuilder_issue ends
					dbColumn[0].setColType("TINYTEXT");
				} else {
					dbColumn[0].setColType(colType);
					dbColumn[0].setColTypeVal(colSize);
				}
			} else if("numeric".equals(colType) && "Integer".equals(colValType)) {
//				alertQuery.append("int").append("(11) DEFAULT -1");//
				dbColumn[0].setColType(SQLQueryGenerator.INT);
				dbColumn[0].setColTypeVal("11");
				dbColumn[0].setColDefault("-1");
			} else if("numeric".equals(colType) && "Double".equals(colValType)) {
//				alertQuery.append("DOUBLE").append(" ");//
				dbColumn[0].setColType(SQLQueryGenerator.DOUBLE);
			} else if("numeric".equals(colType) && "Percentage".equals(colValType)) {
//				alertQuery.append("varchar(10)").append(" ");//

				dbColumn[0].setColType(SQLQueryGenerator.DOUBLE);// Bug 62201
				//dbColumn[0].setColTypeVal("10");// Bug 62201
			} else if("combo".equals(colType) || "radio".equals(colType)) {
//				alertQuery.append("int(11) ");
				dbColumn[0].setColType(SQLQueryGenerator.INT);
				dbColumn[0].setColTypeVal("11");
			} else if("checkbox".equals(colType)) {
//				alertQuery.append("varchar(255)");
				dbColumn[0].setColType(SQLQueryGenerator.VARCHAR);
				dbColumn[0].setColTypeVal("255");
			} else if("multiselect".equals(colType)) {//BB-20150203-259 (MultiSelect combo in add new field changes) starts
//				alertQuery.append("varchar(255)");
				dbColumn[0].setColType(SQLQueryGenerator.TEXT);
			} //BB-20150203-259 (MultiSelect combo in add new field changes) ends
			else if("file".equals(colType)) { //BB-20150203-259 (Add Document as field for positioning) starts
				dbColumn[0].setColType(SQLQueryGenerator.VARCHAR);
				dbColumn[0].setColTypeVal("255");
			} //BB-20150203-259 (Add Document as field for positioning) ends
			else if("existing".equals(colType)) { //P_Enh_Sync_Fields starts
				if(StringUtil.isValidNew(existingColumnType)) {
					if(StringUtil.isValidNew(existingColumnSize) && Integer.parseInt(existingColumnSize) > 255 && !"BLOB".equals(existingColumnType)) {
						dbColumn[0].setColType(SQLQueryGenerator.TEXT);	
					} else {
						dbColumn[0].setColType(existingColumnType);
					}
				}
				
				if(!"DATE".equals(existingColumnType)) {
					if(StringUtil.isValidNew(existingColumnSize)) {
						dbColumn[0].setColTypeVal(existingColumnSize);
					}
				}
			} //P_Enh_Sync_Fields ends
			else {
//				alertQuery.append(colType);//
				dbColumn[0].setColType(colType);
			}
			
			if(StringUtil.isValidNew(coloption) && "2".equals(coloption)) {
//				alertQuery.append(", ADD COLUMN ");
//				alertQuery.append(colName1).append(" ");
//				alertQuery.append("int(11)");
				dbColumn[1].setColDBName(colName1);
				dbColumn[1].setColType(SQLQueryGenerator.INT);
				dbColumn[1].setColTypeVal("11");
			} 
			//BB-20150319-268(FIM Campaign Center) starts
			
			if(StringUtil.isValidNew(includeInCampaign)) {
				if("varchar".equals(colType)) {
					if("0".equals(includeInCampaign)) {
						if(includeInCampaign.equals(oldValue)) {
							//do nothing
						} else {
							dbColumn[1].setColDBName(colName + Constants.CAMPAIGN_TABLE_SUFFIX);
							dbColumn[1].setAction(SQLQueryGenerator.ADD);
							dbColumn[1].setColType(colType);
							dbColumn[1].setColTypeVal(colSize);
							dbColumn[1].setColDefault("0");
						}
					} else if("yes".equals(modifyFld) && (!includeInCampaign.equals(oldValue))) { //droping column at the time of modification
						dbColumn[1].setColDBName(colName + Constants.CAMPAIGN_TABLE_SUFFIX);
						dbColumn[1].setAction("DROP");
					}
				} else if(StringUtil.isValidNew(includeInCampaign) && !includeInCampaign.equals(oldValue)) {
					dbColumn[1] = new DBColumn();
					dbColumn[1].setColDBName(colName + Constants.CAMPAIGN_TABLE_SUFFIX);
					dbColumn[1].setAction("DROP");
				}
			}
            //BB-20150319-268(FIM Campaign Center) ends
			DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
			
			String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
			
			if ("yes".equalsIgnoreCase((String) MultiTenancyUtil.getTenantContext().getAttribute("isInnoDB"))) {
				queryStr = queryStr + ",algorithm=inplace, lock=none";
			}
			
			int count = QueryUtil.alterDBTable(queryStr);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean alterTableModifyData(Info info) {
		try {
			String tableName = info.getString(BuilderFormFieldNames.TABLE_NAME);
			String colName = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME);

//			StringBuffer alertQuery  = new StringBuffer("ALTER TABLE ");
//			alertQuery.append(tableName).append(" DROP COLUMN ");
//			alertQuery.append(colName).append(" ");
			DBColumn[] dbColumn = new DBColumn[1];
			dbColumn[0] = new DBColumn();
			dbColumn[0].setAction(SQLQueryGenerator.DROP);
			dbColumn[0].setColDBName(colName);
			
			DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
			String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
			
			if ("yes".equalsIgnoreCase((String) MultiTenancyUtil.getTenantContext().getAttribute("isInnoDB"))) {
				queryStr = queryStr + ",algorithm=inplace, lock=none";
			}
			
			int count = QueryUtil.alterDBTable(queryStr);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	public boolean dropTableData(String tableName, String colName) {
        return dropTableData(tableName, colName, false);
    }
	
	/*public boolean dropTableData(String tableName, String colName) {
		try {
//			StringBuffer alertQuery  = new StringBuffer("ALTER TABLE ");
//			alertQuery.append(tableName).append(" DROP COLUMN ");
//			alertQuery.append(colName).append(" ");

//			int count = QueryUtil.alterDBTable(alertQuery.toString());
			
			DBColumn[] dbColumn = new DBColumn[1];
			dbColumn[0] = new DBColumn();
			dbColumn[0].setAction(SQLQueryGenerator.DROP);
			dbColumn[0].setColDBName(colName);
			
			DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
			String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
			
			int count = QueryUtil.alterDBTable(queryStr);
		} catch(Exception e) {
			return false;
		}
		return true;
	}*/
	public boolean dropTableData(String tableName, String colName, boolean includeInCampaign) {
		try {
//			StringBuffer alertQuery  = new StringBuffer("ALTER TABLE ");
//			alertQuery.append(tableName).append(" DROP COLUMN ");
//			alertQuery.append(colName).append(" ");

//			int count = QueryUtil.alterDBTable(alertQuery.toString());
            DBColumn[] dbColumn = new DBColumn[1];
            //BB-20150319-268(FIM Campaign Center) starts
			if(includeInCampaign) {
                dbColumn = new DBColumn[2];
            }
            //BB-20150319-268(FIM Campaign Center) ends
			dbColumn[0] = new DBColumn();
			dbColumn[0].setAction(SQLQueryGenerator.DROP);
			dbColumn[0].setColDBName(colName);
            if(includeInCampaign) {//BB-20150319-268(FIM Campaign Center) start
                dbColumn[1] = new DBColumn();
                dbColumn[1].setAction(SQLQueryGenerator.DROP);
                dbColumn[1].setColDBName(colName + Constants.CAMPAIGN_TABLE_SUFFIX);
            }//BB-20150319-268(FIM Campaign Center) ends
			
			DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
			String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
			if ("yes".equalsIgnoreCase((String) MultiTenancyUtil.getTenantContext().getAttribute("isInnoDB"))) {
				queryStr = queryStr + ",algorithm=inplace, lock=none";
			}
            int count = QueryUtil.alterDBTable(queryStr);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
		
	public static Node[] getNodesInChildren(Node node,String name)

	{

		ArrayList list = new ArrayList();

		NodeList children	= node.getChildNodes();

		for (int i=0;i<children.getLength();i++)

		{

			Node child = children.item(i);

			if (child.getNodeType()==Node.TEXT_NODE) continue;

			String nodeName = child.getNodeName();

			if (name.equals(nodeName))

			{

				list.add(child);

			}

		}

		Node[] nodes = new Node[list.size()];

		Iterator it = list.iterator();

		for (int i=0;it.hasNext();i++)

		{

			nodes[i]=(Node)it.next();

		}

		return nodes;

	}
	
	/**
	 * Added By Veerpal Singh for Auditing and Trigger of New fields  
	 */
	public boolean insertIntoTriggerEvent(Info info) {
		ResultSet rs=null;
		try {
			String formID = info.getString(BuilderFormFieldNames.BUILDER_FORM_ID);
			String tableName = info.getString(BuilderFormFieldNames.TABLE_NAME);
			String tableName1 = info.getString("tableName1");
			String fieldName = info.getString(BuilderFormFieldNames.FIELD_NAME);
			String fieldName1 = info.getString(BuilderFormFieldNames.FIELD_NAME+"1");
			String dbFieldName = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME);
			String dbFieldName1 = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME+"1");
			String displayName = info.getString(BuilderFormFieldNames.DISPLAY_NAME);
			String displayName1 = info.getString(BuilderFormFieldNames.DISPLAY_NAME+"1");
			String dataType = info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE);
			String coloption = info.getString(BuilderFormFieldNames.DROPDOWN_OPTION);
			String isTabularSection = info.getString("isTabularSection");
			String tabularSectionValue = "N";
			if("true".equals(isTabularSection)) {
				tabularSectionValue = "Y";
			}
			if("franchiseeCall".equals(tableName1)) {
				formID = "28";
			} else if("fimTasks".equals(tableName1)) {
				tableName = "tasks";
				formID = "29";
			}
			
			displayName=StringUtil.encodeEscapeCharacter(displayName, '\'');
			displayName=StringUtil.encodeEscapeCharacter(displayName, '\"');
			 rs =QueryUtil.getResult("SELECT MAX(FIELD_ORDER) FROM TRIGGER_EVENT ", null);
			int max=999999;
			if(rs.next()){
			 max=rs.getInteger(1);
			max++;
			}
			if(StringUtil.isValid(dataType) && (dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("varchar") || dataType.equalsIgnoreCase("combo") || dataType.equalsIgnoreCase("radio") || dataType.equalsIgnoreCase("checkbox")))
				dataType = "String";
			else if("double".equalsIgnoreCase(dataType) || "numeric".equalsIgnoreCase(dataType))
				dataType = "Double";
			
			StringBuffer insertQuery  = new StringBuffer("INSERT INTO TRIGGER_EVENT (TRIGGER_ID, TRIGGER_ONOFF, AUDITING_ONOFF, TABLE_NAME, FIELD_NAME, EVENT, EMAIL_ID, USER_NO, ALERT_MESSAGE, DAYS_PRIOR, VALUE_TO_COMPARE, DB_TABLE_NAME, FIELD_ORDER, DB_FIELD_NAME, DISPLAY_FIELD_NAME, ACTUAL_DATA_TYPE, IS_TABULAR_SECTION) ");
			insertQuery.append("VALUES ("+formID+",0,1,'"+tableName1+"','"+fieldName+"','','','0','',0,'','"+tableName+"','"+max+"','"+dbFieldName+"','"+displayName+"','"+dataType+"', '"+tabularSectionValue+"')");
			if(StringUtil.isValidNew(coloption) && "2".equals(coloption)) {
				displayName1=StringUtil.encodeEscapeCharacter(displayName1, '\'');
				displayName1=StringUtil.encodeEscapeCharacter(displayName1, '\"');
				insertQuery.append(" , ("+formID+",0,1,'"+tableName1+"','"+fieldName1+"','','','0','',0,'','"+tableName+"','"+max+"','"+dbFieldName1+"','"+displayName1+"','"+dataType+"', '"+tabularSectionValue+"')");
			}
			
			QueryUtil.executeInsert(insertQuery.toString());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			QueryUtil.releaseResultSet(rs);
		}
		return true;
	}
	
	public boolean modifyIntoTriggerEvent(Info info) {
		try {
			String tableName = info.getString(BuilderFormFieldNames.TABLE_NAME);
			String dbFieldName = info.getString(BuilderFormFieldNames.DB_COLUMN_NAME);
			String displayName = info.getString(BuilderFormFieldNames.DISPLAY_NAME);
			String dataType = info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE);
			
			displayName=StringUtil.encodeEscapeCharacter(displayName, '\'');
			displayName=StringUtil.encodeEscapeCharacter(displayName, '\"');
			
			StringBuffer updateQuery  = new StringBuffer("UPDATE TRIGGER_EVENT SET DISPLAY_FIELD_NAME='" + displayName + "',ACTUAL_DATA_TYPE='" + dataType + "' ");
			updateQuery.append(" WHERE DB_TABLE_NAME='" + tableName + "' AND DB_FIELD_NAME='" + dbFieldName + "'");
			
			QueryUtil.executeInsert(updateQuery.toString());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*PATCH_ISSUE_DOCUMENT_TAB
	 * update field display name for already added document column name. 
	 */
	public boolean modifyDocumentFieldValue(String value,String key) {
		try {
			value=StringUtil.encodeEscapeCharacter(value, '\'');
			value=StringUtil.encodeEscapeCharacter(value, '\"');
			StringBuffer updateQuery  = new StringBuffer("UPDATE FIM_DOCUMENTS SET DOCUMENT_FIM_TITLE='").append(value).append("' WHERE FIELD_PREFIX=").append(key).append(" AND TAB_NAME='").append("Contract Signing'");	
			QueryUtil.executeInsert(updateQuery.toString());
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean dropTriggerEventData(String tableName, String colName) {
		try {
			StringBuffer deleteQuery  = new StringBuffer("DELETE FROM TRIGGER_EVENT ");
			deleteQuery.append(" WHERE DB_TABLE_NAME='" + tableName + "' AND DB_FIELD_NAME='" + colName + "'");
			QueryUtil.executeInsert(deleteQuery.toString());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//ENH_MODULE_CUSTOM_TABS starts
	/*
	*	This method refresh the mappings everytime a tab is added or deleted
	*/
	public void refreshCustomTabMappings()//AUDIT_ENHANCEMENT_CHANGES
	{
		HashMap customTabMappings	=	BuilderCustomTab.newInstance().getAllCustomTabMappings();
		HashMap tableMappings		= 	getTableMappings();
		
		if(customTabMappings != null)
		{
			tableMappings.putAll(customTabMappings);
		}
		
		//AUDIT_ENHANCEMENT_CHANGES starts
        //customTabMappings	=	AuditUtil.getAllCustomTabMappings();
		customTabMappings	=	BaseUtils.getAllCustomTabMappings();   //For Product_Seperation_BL By Amar Singh
	
		
		if(customTabMappings != null)
		{
			tableMappings.putAll(customTabMappings);
		}
		customTabMappings = null;
		//AUDIT_ENHANCEMENT_CHANGES ends
	}
	//ENH_MODULE_CUSTOM_TABS ends
	
	/*till here by Veerpal Singh*/

	//AUDIT_ENHANCEMENT_CHANGES Starts
	public boolean alterAuditTableData(String tableName, SequenceMap alterTableMap) {
		try {
			if(alterTableMap != null && alterTableMap.size()>0){
				DBColumn[] dbColumn = new DBColumn[alterTableMap.size()];
				Iterator itr = alterTableMap.keys().iterator();
				int colCount = 0;
				while(itr.hasNext()){
					dbColumn[colCount] = new DBColumn();
					String colName = (String)itr.next();
					Info info = (Info)alterTableMap.get(colName);
					String colType = info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE);
					String columnEvent = info.getString("columnEvent");
					String colSize = info.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH);
					String colValType = info.getString(BuilderFormFieldNames.FLD_VALIDATION_TYPE);
					String isSmartQuestion = info.getString(BuilderFormFieldNames.IS_SMART_QUESTION);//ENH_PW_SMART_QUESTIONS
					colValType = "Integer";
		
					/**
					 * Allow Object based query generation to reduce code
					 */
					dbColumn[colCount].setAction(columnEvent);
					dbColumn[colCount].setColDBName(colName);
					if(StringUtil.isValidNew(colSize) && ("varchar".equals(colType) || "int".equals(colType))) {
						// BushsC-20161019-010 starts
						//dbColumn[colCount].setColType(colType);
						//dbColumn[colCount].setColTypeVal(colSize);
						int varcharFieldSize = 0;
						if("varchar".equals(colType)) {
							boolean canAddVarchar = true;
							String selectQuery = "SELECT * FROM "+tableName+" LIMIT 0, 1";
							ResultSet result  = QueryUtil.getResult(selectQuery, null);
							java.sql.ResultSetMetaData rsmd = result.getMetaData();
							
							int colCount1 = rsmd.getColumnCount();
							for(int i = 1; i<=colCount1; i++) {
								//formBuilder_issue starts
								int type = rsmd.getColumnType(i);
								if (type == Types.VARCHAR || type == Types.CHAR) {
									varcharFieldSize += rsmd.getColumnDisplaySize(i);
								}
							}
						}
						if(varcharFieldSize > 21000) { 
							dbColumn[colCount].setColType("TINYTEXT");
						} else {
							dbColumn[colCount].setColType(colType);
							dbColumn[colCount].setColTypeVal(colSize);
						}
					//BushsC-20161019-010 ends
					} else if(("numeric".equals(colType) || "range".equals(colType) || "multiinput".equals(colType)) && "Integer".equals(colValType)) { //PW_ENH_QUE_NUMBER starts
						//ENH_PW_SMART_QUESTIONS_STARTS
					 /* dbColumn[colCount].setColType(SQLQueryGenerator.FLOAT);
						dbColumn[colCount].setColTypeVal("7,2");
						dbColumn[colCount].setColDefault("-1"); //PW_ENH_QUE_NUMBER ends
                     */
						dbColumn[colCount].setColType(SQLQueryGenerator.VARCHAR);
						dbColumn[colCount].setColTypeVal("255");
						//ENH_PW_SMART_QUESTIONS_ENDS
					} else if("numeric".equals(colType) && "Double".equals(colValType)) {
						dbColumn[colCount].setColType(SQLQueryGenerator.DOUBLE);
					} else if("numeric".equals(colType) && "Percentage".equals(colValType)) {
						dbColumn[colCount].setColType(SQLQueryGenerator.DOUBLE);// Bug 62201
						//dbColumn[colCount].setColTypeVal("10");// Bug 62201
					}else if("radio".equals(colType) && "Y".equals(isSmartQuestion)) {//ENH_PW_SMART_QUESTIONS_STARTS
						dbColumn[colCount].setColType(SQLQueryGenerator.VARCHAR);
						dbColumn[colCount].setColTypeVal("50");//ENH_PW_SMART_QUESTIONS_ENDS
					} else if("combo".equals(colType) || "radio".equals(colType)) {
						dbColumn[colCount].setColType(SQLQueryGenerator.INT);
						dbColumn[colCount].setColTypeVal("11");
					} else if("checkbox".equals(colType)) {
						dbColumn[colCount].setColType(SQLQueryGenerator.VARCHAR);
						dbColumn[colCount].setColTypeVal("255");
					} else {
						dbColumn[colCount].setColType(colType);
					}
					
					colCount++;
				}
				DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
				
				String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
				int count = QueryUtil.alterDBTable(queryStr);
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean dropAuditTableData(String tableName, Field[] fldArr) {
		try {
			if(fldArr != null && fldArr.length > 0){
				DBColumn[] dbColumn = new DBColumn[fldArr.length];
				int fldCount = 0;
				for(Field fld : fldArr){
					dbColumn[fldCount] = new DBColumn();
					dbColumn[fldCount].setAction(SQLQueryGenerator.DROP);
					dbColumn[fldCount].setColDBName(fld.getDbField());
					fldCount++;
				}
				DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
				String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
				int count = QueryUtil.alterDBTable(queryStr);
			}else{
				return false;
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	//AUDIT_ENHANCEMENT_CHANGES Ends
	/**
	 * This method refreshes the tabular section mappings whenever new tabular section is added or deleted.
	 * P_Enh_FormBuilder_Tabular_Section
	 * @author Akash Kumar
	 */
	public void refreshTabularSectionMappings()
	{
		String tabularSectionMappingsURL=MultiTenancyUtil.getTenantConstants().XML_DIRECTORY +"tabularSectionMappings.xml";
		HashMap tabularSectionMappings 				= TableXMLDAO.getTabularSectionMappings(tabularSectionMappingsURL);
		HashMap tableMappings		= 	getTableMappings();
		if(tabularSectionMappings != null)
		{
			tableMappings.putAll(tabularSectionMappings);
		}
		tabularSectionMappings = null;
	}
	
}