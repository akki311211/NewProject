/**
* Description: This is the form DAO which defines all the functions of Base Form to provide basic methods
* @author abhishek gupta 
* @version 1.1
* @created Nov 14, 2011
*/
package com.home.builderforms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.home.builderforms.AppException;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.IDGenerator;
import com.home.builderforms.IntConstants;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;
import com.home.builderforms.sqlqueries.SQLUtilHelper;

public class FormDAO{
	protected DBUtil		dbUtil;
	protected String 		tableAnchor;
	protected String 		tableName;
	protected FieldMappings     fieldMappings;

	public FormDAO(){
	}
	public FormDAO(String psTableAnchor){
		tableAnchor			= psTableAnchor;
		init();
	}
	public void init(){
		dbUtil 					= DBUtil.getInstance();
	}

	public static void beginTransaction(String connectionName){
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
		return dbUtil.getFieldMappings(tableAnchor);
	}

	public Object[] create(Info info) throws SQLException{
		return createHelper(info,0);
	}

	public Object[] createHelper(Info info,int count)throws SQLException
	{
		try
		{
			Integer[] id = new Integer[getFieldMappings().getIdField().length];
			for (int i=0; i<id.length; i++) {
				id[i] = new Integer(IDGenerator.getNextKey());
			}
			info.setID(id);
			SQLUtil.createRecord(getFieldMappings(),info,true);
			return info.getIDObject();
		}
		catch(com.mysql.jdbc.MysqlDataTruncation e)
		{		 
			return info.getIDObject();
		}
		catch(SQLException e)
		{
			if (count < 5)
			{
				createHelper(info,++count);
			}else{
				throw e;
			}
		}
		return null;
	}

	public void modify(Info info) {
		try{
			SQLUtil.modifyRecord(getFieldMappings(),info);
		}
		catch(Exception e){
		}
	}
	public void modify(Info info, SequenceMap paramMap) {
		try{
			setNullIntsToDefault(info, true);
			SQLUtil.modifyRecord(getFieldMappings(),info, paramMap);
		}
		catch(Exception e){
		}
	}
	public void replace(Info info) {
		try{
			setNullIntsToDefault(info);
			SQLUtil.replaceRecord(getFieldMappings(),info);
		}catch(Exception e){
		}

	}

	public void delete(Integer[] id) throws SQLException{
		SQLUtil.deleteRecord(getFieldMappings(),id);
	}

	public void delete(Info info) throws SQLException{
		SQLUtil.deleteRecord(getFieldMappings(),info);
	}

	public void deleteRecords(Integer[] ids) throws SQLException{
			SQLUtil.deleteRecords(getFieldMappings(),ids);
	}

	public void deleteRecords(SequenceMap map) throws SQLException{
			SQLUtil.deleteRecords(getFieldMappings(),map);
	}

	public Info getDetailsInfo(Integer id) {
		Info info = null;
		try{
			info = SQLUtil.getDetailsInfo(getFieldMappings(),id);
		} catch(RecordNotFoundException rnfe){
		} catch(Exception e){
		}
		return info ;
	}

	public Info getDetailsInfo(SequenceMap map) throws SQLException,RecordNotFoundException{
		Info info = null;
		try{
			info= SQLUtil.getDetailsInfo(getFieldMappings(), map);
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

	public Info getSummaryInfo(Integer[] id) throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryInfo(getFieldMappings(),id);
	}

	public SequenceMap getSummaryCollection() throws SQLException,RecordNotFoundException{
		return SQLUtil.getSummaryCollection(getFieldMappings());
	}

	public SequenceMap getCollection(String[] fieldNames,SequenceMap params, String orderBy) {
		try{
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
		try{
			return SQLUtil.getCollection(getFieldMappings(),fieldNames,params);
		} catch(RecordNotFoundException rnfe){
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
		FieldMappings mappings	= getFieldMappings();
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
//						if(fld.getTransformMethodParam()!=null && !"".equals(fld.getTransformMethodParam()))
//						{
//							newValue = exportDCol.transform(fld,infoToConvert.get(key),infoToConvert.getString(FieldNames.getVal(fld.getTransformMethodParam())));
//						}
//						else if(fld.getTransformMethod()!=null && !"".equals(fld.getTransformMethod()))
//						{
//							newValue = exportDCol.transform(fld,infoToConvert.get(key));
//						}else
//						{   
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

	private Info convertDateFields(Info infoToConvert){
		Info convertedInfo = new Info();
		try{
			if (infoToConvert != null){
				for (Iterator it = infoToConvert.getKeySetIterator(); it.hasNext();){
					String key = (String) it.next();
					Field fld = getFieldMappings().getField(key);
					if (fld != null && fld.getDataType() != null && fld.getDataType().equals("Date")){
						//String newValue = PortalUtils.getAuditFormatDate(infoToConvert.get(key));
						String newValue = BaseUtils.getAuditFormatDate(infoToConvert.get(key));    //For Product_Seperation_BL By Amar Singh.
						convertedInfo.set(key, newValue);
					}else{
						convertedInfo.set(key, infoToConvert.get(key));
					}
				}
			}
		}catch(Exception e){
		}
		return convertedInfo;
	}
         
    /**
     * This will process xml file with containing all nodes in object, later this will used for generating xml file as same
     * @param tableAnchor
     * @return
     * @throws AppException
     */
    public HashMap extractInfo(String tableAnchor) throws AppException
	{
		Info info			= null;
		HashMap tMappings = new HashMap();
		try{
			//get the fieldMappings for the table
			FieldMappings mappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
			String sDBTableName = mappings.getTableName();
			String[] sArrIDFields = mappings.getIdFieldNames();
			if (sArrIDFields != null)
			{
				for (int n = 0; n < sArrIDFields.length; n++)
				{
					info  = new Info();
					Field field = mappings.getField(sArrIDFields[n]);
					field.getDbField();
					field.toString();
					
				}
			}
			// get the fieldNames
		}catch(Exception e){
			throw new AppException("Exception in extractInfo");
		}
		return null;
	} 
    /**
     * This methos will process file for Map data to regenerate with modified data 
     * @param fileName
     * @param hp
     * @return
     */
    public boolean addModifyXmlData(String fileName, HashMap hp) {
    	
    	try {	
			File file = new File("/home/abhishek/cvs/codebase71072/src/config/xml/tables/admin/franchisees.xml");
			/**
			 * Create instance of DocumentBuilderFactory
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			/**
			 * Get the DocumentBuilder
			 */
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			/**
			 * Using existing XML Document
			 */
			Document doc = docBuilder.parse(file);
			/**
			 * create the root element
			 */
			Element root = doc.getDocumentElement();
			/**
			 * create child element
			 */
			Element childElement = doc.createElement("field");
			//Add the attribute to the child
			childElement.setAttribute("summary","true");
			Node newChild = doc.createElement("field-name");
			newChild.setNodeValue("fran");
			
			childElement.appendChild(newChild);
			
			root.appendChild(childElement);
			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
	        //create string from xml tree
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(doc);
	        trans.transform(source, result);
	        String xmlString = sw.toString();
	 
	        OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream("/home/abhishek/Desktop/franchisees.xml");
			for(int i=0;i<buf .length;i++) {
			   f0.write(buf[i]);
			}
			f0.close();
			buf = null;
		}
		catch(SAXException e) {
			e.printStackTrace();	
			return false;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		catch(TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		catch(TransformerException e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public static String getAttributeValue(Node node,String name)
	{
		String ret = null;
		NamedNodeMap attributes = node.getAttributes();
		Node attrNode = attributes.getNamedItem(name);
		if (attrNode!=null)
		{
			ret = attrNode.getNodeValue();
		}
		return ret;
	}
    
	public static String getTagText(Node node,String name)
	{
		String tagText = null;
		Node retNode = getNodeInChildren(node,name);
		if (retNode!=null)
		{
			tagText = retNode.getFirstChild().getNodeValue();
		}
		return tagText;
	}

	public static String setTagText(Node node,String name, String val)
	{
		String tagText = null;
		Node retNode = getNodeInChildren(node,name);
		if (retNode!=null)
		{
			retNode.getFirstChild().setNodeValue(val);
		}
		return tagText;
	}
	
	public static Node getNodeInChildren(Node node,String name)
	{
		Node retNode = null;
		NodeList children	= node.getChildNodes();
		for (int i=0;i<children.getLength();i++)
		{
			Node child = children.item(i);
			if (child.getNodeType()==Node.TEXT_NODE) continue;
			String nodeName = child.getNodeName();
			if (name.equals(nodeName))
			{
				retNode = child;
				break;
			}
		}
		return retNode;
	}
	
	public static Node getTagNode(Element element,String name)
	{
		NodeList list = element.getElementsByTagName(name);
		int listLength = list.getLength();
		if (listLength<1)
		{
			return null;
		}
		else if (listLength>1)
		{
		}
		return list.item(0);
	}
	
	public SequenceMap getFimFormBuilderFields(String tableName, String colName, String formId) throws SQLException, RecordNotFoundException {
		String query = "SELECT BUILDER_FORM_ID,TABLE_ANCHOR FROM BUILDER_WEB_FORMS where BUILDER_FORM_ID=" + Integer.parseInt(formId) + " ";//BB_Naming_Convention
		ResultSet result = SQLUtilHelper.getResultSet(query, new Object[] {}, Constants.TENANT_NAME);
		SequenceMap map = new SequenceMap();
		
		return map;
	}
}