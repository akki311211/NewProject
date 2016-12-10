package com.home.builderforms;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import com.home.builderforms.sqlqueries.SQLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.home.builderforms.*;
import com.appnetix.app.control.SystemVariableManager;
import com.appnetix.app.control.web.FCInitHandlerServlet;
import com.appnetix.app.control.web.ParamResolver;
import com.home.BuilderFormFieldNames;
import com.home.PropertiesIO;
import com.home.admin.WebCacheBypass;
import com.home.builderforms.BaseFormFactory;
import com.home.builderforms.BuilderConstants;
import com.home.builderforms.BuilderCustomTab;
import com.home.builderforms.BuilderFormTimerTask;
import com.home.i18n.Language;
import com.home.builderforms.BaseUtils;
import com.appnetix.app.portal.role.UserRoleMap;
import com.home.builderforms.BuilderFormUtil;
import com.home.builderforms.Constants;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateUtil;
import com.home.builderforms.Debug;
import com.home.builderforms.FieldNames;
import com.home.builderforms.FileUtil;
import com.home.builderforms.IDGenerator;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.StrutsUtil;
//import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.TableAnchors;
//import com.home.builderforms.base.BaseNewPortalUtils;
import com.home.builderforms.DependentTable;
import com.home.builderforms.DocumentMap;
import com.home.builderforms.Documents;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.LinkField;
import com.home.builderforms.TableField;
import com.home.builderforms.i18n.UserLanguageLocal;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.DBColumn;
import com.home.builderforms.sqlqueries.DBQuery;
import com.home.builderforms.sqlqueries.RecordNotFoundException;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLQueryGenerator;
import com.home.builderforms.TableXMLDAO;
import com.home.builderforms.XMLUtil;
import com.appnetix.app.control.web.multitenancy.resources.constants.BaseConstants;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.appnetix.app.control.web.webimpl.BuilderFormWebImpl;
import com.appnetix.app.exception.AppException;

/**
 * The DAO for the Builder Form Table 
 * @author abhishek gupta
 * @created Nov 1, 2011
 */
/**
 -----------------------------------------------------------------------------------------------------------------------------------------------
 Version No.				Date			By								Against									Function Changed	Comments
 -----------------------------------------------------------------------------------------------------------------------------------------------
 P_E_MoveToFim_AddlFDD		27Nov2012	  	Veerpal Singh					Patch
 PW_B_26809					23 Aug 2013		Anjali Pundir					basebuild>admin>manage form generator>document> If a document contains entry for any user. User could not modify that document type.
 FORM_BUILDER_ISSUE_1   	 28 Oct 2013     Mohit Sharma                    1. Adding field and getting displayed in reverse order. 2. Single checkbox when added was not visible at details page
 P_FS_Enh_BuilderForm		16Dec2013		Naman Jain						Form Builder in Franchise Sales 
 P_SCH_ENH_008				20 June 2014		Manik Malhotra		CR			Add Form Generator on JOBS Page
 BOEFLY_INTEGRATION			21/08/2014		Veerpal Singh					Enh										A third party integration with Boefly through REST-API for lead sync.
 BB-20141017-177  			5 Nov 2014     	Nazampreet Kaur                	Code changed for custom fields to show them up in summary display
 P_SCH_B_49982				14 Nov 2014		Manik Malhotra					Delete option not available for Newly added Scheduler custom tab.
 MT_PHASE_III_I18n_CHANGES	05 May 2015		Shraddha Seth			 		To Enable MultiTenant i18n Configurations.
 ENH_PW_SMART_QUESTIONS    11/08/2015      Rohit Jain   Add new functionality or Question type as SmartQuestion in Pwise
 ZCUB-20160210-230			18/03/2016     Madhusudan Singh    Add Field in CM Campaign Contact Filter section
 **/
public class BuilderFormDAO extends FormBaseDAO {
    /**
     * fields in heatMeterDependentFieldsArr are used in creating the heat meter, so here it is required to change the flag for corresponding.
     */
    private transient File file = null;
    private  DocumentBuilderFactory factory = null;
    private  DocumentBuilder docBuilder = null;
    private  Document doc = null;
    private  Document docModule = null;//ENH_MODULE_CUSTOM_TABS
    private  Element root = null;

    private  TransformerFactory transfac = null;
    private  Transformer trans = null;
    private  StreamResult result = null;
    private  StreamResult resultBackup = null;
    private  StreamResult originalFile = null;//ENH_MODULE_CUSTOM_TABS
    private  StreamResult newTabFile = null;//ENH_MODULE_CUSTOM_TABS
    private  DOMSource source = null;
    //	private OutputStream f0;
//	private byte buf[] = null;
    private  Node node = null;
    private boolean flag = false;

    public BuilderFormDAO() {
        this.tableAnchor = BuilderFormFieldNames.BUILDER_WEB_FORMS;//BB_Naming_Convention

    }
    public BuilderFormDAO(String tableAnchor) {
        this.tableAnchor = tableAnchor;
        init();
    }

    public void initBDaoInstance() {
        if(factory == null) {
            factory = DocumentBuilderFactory.newInstance();
        }
        if(docBuilder == null) {
            try {
                docBuilder = factory.newDocumentBuilder();
            } catch(Exception e) {
            }
        }
        if(transfac == null) {
            try {
                transfac = TransformerFactory.newInstance();
            } catch(Exception e) {
            }
        }
        if(trans == null) {
            try {
                trans = transfac.newTransformer();
            } catch(Exception e) {
            }
        }
    }

    public SequenceMap getBuilderFormList(final HttpServletRequest request) throws SQLException, RecordNotFoundException {
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            //ENH_MODULE_CUSTOM_TABS starts
            //request.setAttribute(BuilderConstants.TABLE_ID, "1");
            String tableId=(String)request.getAttribute(BuilderConstants.TABLE_ID);
            if(!StringUtil.isValid(tableId))
                tableId="1";
            request.setAttribute(BuilderConstants.TABLE_ID, tableId);
            SequenceMap resultMap = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());
            if("12".equals(tableId))
            {
                resultMap = BuilderCustomTab.newInstance().filterActiveTabs((String)request.getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME),resultMap);
            }
            return resultMap;
            //ENH_MODULE_CUSTOM_TABS ends
        } catch(Exception e) {
            return null;
        }
    }

    public synchronized void setBuilderFormAddOrUpdate(final HttpServletRequest request) throws SQLException, RecordNotFoundException { //synchrnized function
    	HashMap dataMap = new HashMap();
    	dataMap.put("optionName", request.getParameterValues("optionName"));
    	dataMap.put("optionValues", request.getParameterValues("optionNameTempValue"));//Alleg-20160615-349
    	dataMap.put("fieldDependentValueHidden", request.getParameterValues("fieldDependentValueHidden"));
    	dataMap.put("optionActivatedOrdered", request.getParameterValues("optionActivatedOrdered"));
    	Enumeration dataValues = request.getParameterNames();
		while (dataValues != null && dataValues.hasMoreElements()) {
			String keyParameter = (String)dataValues.nextElement();
			if("optionName".equals(keyParameter) || "fieldDependentValueHidden".equals(keyParameter) || "optionActivatedOrdered".equals(keyParameter)) {
				continue;
			}
			String valueParameter = request.getParameter(keyParameter);
			dataMap.put(keyParameter, valueParameter);
		}
		String moduleName = (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME));//ENH_MODULE_CUSTOM_TABS
		String action = getValueFromMap(dataMap, "action");
		if("modify".equals(action)) {
			dataMap.put("modifyFld","yes");
		}
		dataMap.put(BuilderFormFieldNames.BUILDER_MODULE_NAME, moduleName);
		
		WebCacheBypass webCacheIP = new WebCacheBypass(request);
		dataMap.put("userIP", webCacheIP.getRemoteAddr());
		
		SequenceMap baseBuilder1 = null;
		String userNo = (String)request.getSession().getAttribute("user_no");
		String tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);
		String formId = getRequestValue(request, "fimFormId");
		if(tableAnchor == null) {
            formId = getRequestValue(request, BuilderFormFieldNames.BUILDER_FORM_ID);
            if(StringUtil.isValidNew(formId)) {
                request.setAttribute(BuilderConstants.TABLE_ID,"2");
                dataMap.put(BuilderConstants.TABLE_ID,"2");
                request.setAttribute("form-id",formId);
                dataMap.put("form-id",formId);
                request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
                dataMap.put(BuilderFormFieldNames.IS_FIM_TABLE,"yes");
                baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());
            }
        }
		Thread thread = new Thread(new BuilderFormTimerTask(dataMap, userNo, tableAnchor, formId, request, baseBuilder1));
        thread.setName("FormGeneratorFieldAdd");
        MultiTenancyUtil.getTenantConstants().IS_THREAD_RUNNING = true;
        //request.getSession().setAttribute("formGeneratorFieldAddThread", thread);    // Commented For Tomcat Clustering Issue
   		thread.start();
    }

    public boolean setBuilderFormAddOrUpdateSection(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        Object baseBuilder = null;
        SequenceMap baseBuilder1;
        String key;
        Iterator it;
        Object ob;
        String formId = null;

        try {
            tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);//request.getParameter(BuilderFormFieldNames.TABLE_ANCHOR);

            if(tableAnchor == null) {
//				if(baseFormFactory == null) {
//					context = request.getSession().getServletContext();
//					super.init();
//				}
                baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());
                it = baseBuilder1.keys().iterator();
                if(it.hasNext()) {
                    key = (String)it.next();
                    ob = (Object)baseBuilder1.get(key);
                    if(ob instanceof Info) {
                        tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                        request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                    }
                }
                if(!StringUtil.isValid(tableAnchor)) {
                    return false;
                }
            }

            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location)) {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }

            //flag = updateActiveXmlData(request, tableAnchor);
            flag = addModifyXmlSectionData(request);
            if(flag) {
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        } finally {
            tableAnchor = null;
            loc		= null;
            location = null;
        }
        return true;
    }

    public boolean setBuilderFormAddOrUpdateDocument(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        Object baseBuilder = null;
        SequenceMap baseBuilder1;
        String key;
        Iterator it;
        Object ob;
        String formId = null;

        try {
            tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);//request.getParameter(BuilderFormFieldNames.TABLE_ANCHOR);
            if(tableAnchor == null) {
//				if(baseFormFactory == null) {
//					context = request.getSession().getServletContext();
//					super.init();
//				}
                baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
                it = baseBuilder1.keys().iterator();
                if(it.hasNext()) {
                    key = (String)it.next();
                    ob = (Object)baseBuilder1.get(key);
                    if(ob instanceof Info) {
                        tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                        request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                    }
                }
                if(!StringUtil.isValid(tableAnchor)) {
                    return false;
                }
            }
            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location)) {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }

            //flag = updateActiveXmlData(request, tableAnchor);
            flag = addModifyXmlDocumentData(request);
          //P_Enh_FormBuilder_Tabular_Section starts(This block adds document in tabular section xml)
            String isTabularSection=getRequestValue(request, BuilderFormFieldNames.IS_TABULAR_SECTION);
  			String tabularSectionTableDBName=getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_DB_NAME);
  			String tabularSectionTableName=getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_NAME);
  			if("true".equals(isTabularSection)){
  				String tableAnchorTabularSection=tabularSectionTableName;
  	            loc		= (String)getTableMappings().get(tableAnchorTabularSection);
  	            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
  	            if(StringUtil.isValidNew(location)) {
  	                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
  	            }
  	          addModifyXmlDocumentData(request);
  	        removeFieldMappings(tableAnchorTabularSection);
  			}//P_Enh_FormBuilder_Tabular_Section ends
            if(flag) {
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);

                /**
                 * Create logs of action
                 */
                Info logInfo = new Info();
                String[] idField = {"id"};
                logInfo.setIDField(idField);
                logInfo.set("id", "");
                logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
                logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                logInfo.set(BuilderFormFieldNames.FIELD_NAME, getRequestValue(request, BuilderFormFieldNames.FIELD_NAME));
                logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE));
                logInfo.set(BuilderFormFieldNames.DOCUMENT_LABEL, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_LABEL));
                logInfo.set(BuilderFormFieldNames.DOCUMENT_OPTION, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_OPTION));
                logInfo.set(BuilderFormFieldNames.ORDER_NO, getRequestValue(request, BuilderFormFieldNames.ORDER));
                logInfo.set(BuilderFormFieldNames.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION));
                String modify = getRequestValue(request, "action");
                if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {
                    logInfo.set(BuilderFormFieldNames.DISPLAY_NAME+"Old", getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE+"_OLD"));
                    logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Modify");
                } else {
                    logInfo.set(BuilderFormFieldNames.DISPLAY_NAME+"Old", "");
                    logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Create");
                }
                //logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
                WebCacheBypass webCacheIP = new WebCacheBypass(request);
                String userIP = webCacheIP.getRemoteAddr();
                logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
                logInfo.set(BuilderFormFieldNames.MODULE_NAME, (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME)));//ENH_MODULE_CUSTOM_TABS

                try {
                    create("formBuilderDocumentLogs", logInfo);
                } catch(Exception e) {
                    Debug.print(e);
                }
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        } finally {
            tableAnchor = null;
            loc		= null;
            location = null;
        }
        return true;
    }

    public boolean addModifyFimFormBuilderFieldsOrder(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        SequenceMap baseBuilder = null;
        Iterator it = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				//context = request.getServletContext();
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return false;
            }

            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location))
            {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }
            flag = addModifyFieldOrderData(request);

            if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                removeFieldMappings(TableAnchors.FRANCHISEE);
            }
            if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                removeFieldMappings(TableAnchors.FRANCHISEES);
            }
            removeFieldMappings(tableAnchor);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
        return flag;
    }
    //BB-20150203-259 (Section Re-ordering changes) starts
    public boolean addModifyFimFormBuilderSectionsOrder(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        SequenceMap baseBuilder = null;
        Iterator it = null;
        String key = null;
        Object ob = null;
        try {
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return false;
            }

            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location))
            {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }
            flag = addModifySectionOrderData(request);

            if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                removeFieldMappings(TableAnchors.FRANCHISEE);
            }
            if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                removeFieldMappings(TableAnchors.FRANCHISEES);
            }
            removeFieldMappings(tableAnchor);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
        return flag;
    }
    //BB-20150203-259 (Section Re-ordering changes) ends

    //SMC-20140213-378 Starts
    public Info getFimFormBuilderFieldsInfo(String fldName, String tableName) {
        return getFimFormBuilderFieldsInfo(fldName, tableName, null);
    }
    //BB-20150203-259 (Dynamic Response based on parent field response) starts
    public Info getFimFormBuilderFieldsInfo(String fldName, String tableName, String value) {
        return getFimFormBuilderFieldsInfo(fldName, tableName, value, null);
    }
    //BB-20150203-259 (Dynamic Response based on parent field response) ends
    public Info getFimFormBuilderFieldsInfo(String fldName, String tableName, String value, String parentValue) {
        try {
            return getRadioOrComboOptionsInfo(fldName,tableName,value, parentValue);//SMC-20140213-378 Ends //BB-20150203-259 (Dynamic Response based on parent field response)
        } catch(Exception e) {
            return null;
        }
    }

    public boolean removeBuilderFormField(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        Object baseBuilder = null;
        SequenceMap baseBuilder1;
        String key;
        Iterator it;
        Object ob;
        String formId = null;
      //P_Enh_FormBuilder_Tabular_Section starts
        String isTabularSection=getRequestValue(request, BuilderFormFieldNames.IS_TABULAR_SECTION);
        String tabularSectionTableName=getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_NAME);
        try {
            tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_NAME);//request.getParameter(BuilderFormFieldNames.TABLE_ANCHOR);
            if("true".equals(isTabularSection) && StringUtil.isValidNew(tabularSectionTableName)){
            	tableAnchor=tabularSectionTableName;
            }
          //P_Enh_FormBuilder_Tabular_Section ends
            if(!StringUtil.isValidNew(tableAnchor)) {
                formId = getRequestValue(request, "formID");

                if(StringUtil.isValidNew(formId)) {
                    request.setAttribute(BuilderConstants.TABLE_ID,"2");
                    request.setAttribute("form-id",formId);
                    request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");

//					if(baseFormFactory == null) {
//						context = request.getSession().getServletContext();
//						super.init();
//					}
                    baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
                    it = baseBuilder1.keys().iterator();
                    if(it.hasNext()) {
                        key = (String)it.next();
                        ob = (Object)baseBuilder1.get(key);
                        if(ob instanceof Info) {
                            tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                        }
                    }
                    if(!StringUtil.isValid(tableAnchor)) {
                        return false;
                    }
                }
            }
            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location)) {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }

            String formID = request.getParameter(BuilderFormFieldNames.FORM_ID);
            String fieldName = getRequestValue(request, "fieldName");

            fieldMappings = getFieldMappings(tableAnchor);
            String tableName = fieldMappings.getTableName();
            fld = fieldMappings.getField(fieldName);
            
            if(fld==null && (fieldName.indexOf("cmDocumentAttachment") !=-1 || fieldName.indexOf("opportunityDocumentAttachment") !=-1 || fieldName.indexOf("leadDocumentAttachment") !=-1 || fieldName.indexOf("accountDocumentAttachment") !=-1))//ZCUB-20150505-144 -CM_Document Field Changes End
            	fld = fieldMappings.getOtherTableField(fieldName);
            
            String dbFldName = fld.getDbField();
            boolean includeInCampaign = fld.getIncludeInCampaign(); //BB-20150319-268(FIM Campaign Center)
            flag = removeXmlData(request);
            if(flag) {
                deleteRadioOrComboOptions(fieldName, tableAnchor);
                dropTriggerEventData(tableName,dbFldName);
                try {
                    //dropTableData(tableName,dbFldName);
                    dropTableData(tableName,dbFldName,includeInCampaign);  //BB-20150319-268(FIM Campaign Center)
                    dropSummaryDisplayColumnData(fld.getFieldName(), fld.getDbField()); //used to delete the entry from table //BB-20141017-177
                    SQLUtil.deleteRecord("FIELD_LEVEL_PII_PASSWORD", "TABLE_ANCHOR = '"+tableAnchor+"' AND FIELD_NAME = '"+fld.getFieldName()+"'");			//P_Enh_FC-76
                    SQLUtil.deleteRows("FS_LEAD_PARSING_KEYWORD",new String[]{"TABLE_ANCHOR","FIELD_NAME"},new String[]{tableAnchor,fld.getFieldName()});  //P_Enh_Custom_Parsing_Keywords_FC-286
                    if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
                    Info tabInfo = new Info();
                    tabInfo.set("FIELD_NAME",fieldName);
                    tabInfo.set("TABLE_NAME",tabularSectionTableName);
                    SQLUtil.deleteTableData("TABULAR_SECTION_DISPLAY_COLUMN",tabInfo);
                    }//P_Enh_FormBuilder_Tabular_Section ends
                    //TAB_ARCHIVE_DATA, ENH_MODULE_CUSTOM_TABS starts
                    String archiveTable = (String)request.getAttribute("archiveTable");
                    if(StringUtil.isValid(archiveTable))
                    {
                        dropTableData(archiveTable,dbFldName);
                    }
                    //TAB_ARCHIVE_DATA, ENH_MODULE_CUSTOM_TABS ends
                    if("31".equals(formID) || "32".equals(formID) || "41".equals(formID) || "49".equals(formID)){//ZCUB-20160210-230 
						dropCmFilterColumnData(fld.getFieldName());
                    }
                } catch(Exception e) {
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);

                /**
                 * Create logs of action
                 */
                Info infoLogs = new Info();
                String[] idField = {BuilderFormFieldNames.FIELD_ID};
                infoLogs.setIDField(idField);
                infoLogs.set("fieldId", "");
                infoLogs.set(BuilderFormFieldNames.BUILDER_FORM_ID, formID);
                infoLogs.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                infoLogs.set(BuilderFormFieldNames.TABLE_NAME, fieldMappings.getTableName());
                infoLogs.set(BuilderFormFieldNames.FIELD_NAME, fld.getFieldName());
                infoLogs.set(BuilderFormFieldNames.DISPLAY_NAME, fld.getDisplayName());
                if(fld.getDisplayTypeField().equals("Text") && StringUtil.isValid(fld.getValidationType())) {
                    infoLogs.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, fld.getValidationType());
                }
                infoLogs.set(BuilderFormFieldNames.IS_BUILD_FIELD, fld.isBuildField()?"yes":"no");

                infoLogs.set(BuilderFormFieldNames.ORDER_NO, fld.getOrderBy());
                infoLogs.set(BuilderFormFieldNames.DB_COLUMN_TYPE, fld.getDisplayTypeField());

                infoLogs.set(BuilderFormFieldNames.IS_ACTIVE, String.valueOf(fld.isActiveField()));
                infoLogs.set(BuilderFormFieldNames.DB_COLUMN_NAME, fld.getDbField());
                if(fld.getDisplayTypeField().equals("Text") || fld.getDisplayTypeField().equals("Numeric"))
                    infoLogs.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, fld.getDBFieldLength());
                if(fld.getDisplayTypeField().equals("TextArea") && StringUtil.isValid(fld.getNoOfColsField())) {
                    infoLogs.set(BuilderFormFieldNames.NO_OF_COLUMN, fld.getNoOfColsField());
                    infoLogs.set(BuilderFormFieldNames.NO_OF_ROW, fld.getNoOfRowsField());
                }

//				if(fld.getDisplayTypeField().equals("Combo"))
//					infoLogs.set(BuilderFormFieldNames.DROPDOWN_OPTION, "");
                infoLogs.set(BuilderFormFieldNames.IS_MANDATORY, String.valueOf(fld.isMandatoryField()));
                infoLogs.set(BuilderFormFieldNames.SECTION, fld.getSectionField());
                infoLogs.set(BuilderFormFieldNames.EXPORTABLE, String.valueOf(fld.isFieldExportable()));
                infoLogs.set(BuilderFormFieldNames.MAIL_MERGE_KEYWORD, fld.getMailMergeKeyword());
                infoLogs.set(BuilderFormFieldNames.IS_PII_ENABLED, String.valueOf(fld.isPiiEnabled()));
                infoLogs.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));
                infoLogs.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));

                WebCacheBypass webCacheIP = new WebCacheBypass(request);
                String userIP = webCacheIP.getRemoteAddr();
                infoLogs.set(BuilderFormFieldNames.IP_ADDRESS, userIP);

                infoLogs.set(BuilderFormFieldNames.ACTION_TYPE, "Delete");
                infoLogs.set(BuilderFormFieldNames.MODULE_NAME, (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME)));//ENH_MODULE_CUSTOM_TABS
                create("formBuilderFieldLogs", infoLogs);
                //BB-20150319-268(FIM Campaign Center) starts
                Info emailInfo = new Info();
                String[] idFieldForEmail = {"fimCampaignEmailId"};
                emailInfo.setIDField(idFieldForEmail);
                emailInfo.set("tableName", fieldMappings.getTableName());
                emailInfo.set("emailColumnName", fld.getDbField());
                emailInfo.set("tableAnchor", tableAnchor);
                delete("fimCampaignEmailCampaign", emailInfo);
                //BB-20150319-268(FIM Campaign Center) ends
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        } finally {
            fieldMappings = null;
            fld = null;
//			loc		= null;
//			location = null;
        }
        return true;
    }

    public boolean removeBuilderFormSection(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        flag = false;
        Object baseBuilder = null;
        SequenceMap baseBuilder1;
        String key;
        Iterator it;
        Object ob;
        String formId = null;

        try {
            tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_NAME);//request.getParameter(BuilderFormFieldNames.TABLE_ANCHOR);
            if(!StringUtil.isValidNew(tableAnchor)) {
                formId = getRequestValue(request, "formID");
                if(StringUtil.isValidNew(formId)) {
                    request.setAttribute(BuilderConstants.TABLE_ID,"2");
                    request.setAttribute("form-id",formId);
                    request.setAttribute(BuilderFormFieldNames.IS_FIM_TABLE,"yes");

//					if(baseFormFactory == null) {
//						context = request.getSession().getServletContext();
//						super.init();
//					}
                    baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
                    it = baseBuilder1.keys().iterator();
                    if(it.hasNext()) {
                        key = (String)it.next();
                        ob = (Object)baseBuilder1.get(key);
                        if(ob instanceof Info) {
                            tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                            request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                        }
                    }
                    if(!StringUtil.isValid(tableAnchor)) {
                        return false;
                    }
                }
            }

            fieldMappings = getFieldMappings(tableAnchor);
            HeaderMap[] hMap = fieldMappings.getHeaderMap();
            String str1 = "";
            String str2= "";
            String str3= "";
            String str4= "";
            String str5= "";
            String docName = getRequestValue(request, "docName");

            for(HeaderMap h:hMap) {
                HeaderField hFld = h.getHeaderFields();
                String secName = getRequestValue(request, "secName");
                if(StringUtil.isValid(secName)){
                    if(hFld.getSectionField().equals(secName)) {
                        str1 =h.getName();
                        str2 =h.getValue();
                        str3 =String.valueOf(h.getOrder());
                        str4 = hFld.isBuildSection();
                        break;
                    }
                }
                if(StringUtil.isValid(docName)){
                    Documents[] docs =  hFld.getDocuments();
                    if(docs != null && docs.length > 0) {
                        DocumentMap[] docMaps = docs[0].getDocumentMaps();
                        if(docMaps != null && docMaps.length > 0) {
                            for(DocumentMap dMap : docMaps) {
                                if(dMap.getFieldName().equals(docName)) {
                                    SequenceMap docMap  = dMap.getDocumentFieldMap();
                                    str1 = dMap.getFieldName();
                                    str2 = (String)docMap.get("title-display");
                                    str3 = (String)docMap.get("count");
                                    str4 = hFld.getSectionField();
                                    break;
                                }
                            }
                        }
                    }

                }
            }
            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location)) {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }

            flag = removeXmlData(request);

            if(flag) {
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);

                /**
                 * Create logs of action
                 */
                Info logInfo = new Info();
                String[] idField = {"id"};
                logInfo.setIDField(idField);
                logInfo.set("id", "");
                logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
                logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                if(StringUtil.isValid(docName)) {
                    logInfo.set(BuilderFormFieldNames.FIELD_NAME, str1);
                    logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, str2);
                    logInfo.set(BuilderFormFieldNames.DISPLAY_NAME+"Old", "");
                    logInfo.set(BuilderFormFieldNames.ORDER_NO, str3);
                    logInfo.set(BuilderFormFieldNames.SECTION, str4);
                } else {
                    logInfo.set(BuilderFormFieldNames.SECTION_NAME, str1);
                    logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, str2);
                    logInfo.set(BuilderFormFieldNames.ORDER_NO, str3);
                    logInfo.set(BuilderFormFieldNames.SECTION, str1);
                    logInfo.set(BuilderFormFieldNames.IS_BUILD_SECTION, str4);
                }
                //logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
                WebCacheBypass webCacheIP = new WebCacheBypass(request);
                String userIP = webCacheIP.getRemoteAddr();
                logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
                logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Delete");
                logInfo.set(BuilderFormFieldNames.MODULE_NAME, (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME)));//ENH_MODULE_CUSTOM_TABS

                try {
                    if(StringUtil.isValid(docName)) {
                        create("formBuilderDocumentLogs", logInfo);
                    } else
                        create("formBuilderSectionLogs", logInfo);
                } catch(Exception e) {
                    Debug.print(e);
                }
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        } finally {
            fieldMappings = null;
//			tableAnchor = null;
//			loc		= null;
//			location = null;
        }
        return true;
    }

    //public boolean updateActiveXmlData(HttpServletRequest request, String tableAnchor) {
    public String updateActiveXmlData(HashMap dataMap, String tableAnchor) {
    	String fileLocBackup = "";
        try {
            String fileLoc = getValueFromMap(dataMap, BuilderFormFieldNames.FILE_LOCATION);
            String val = getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
            String isActive = getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE);
            String section = getValueFromMap(dataMap, BuilderFormFieldNames.SECTION);
            String modifyFld = getValueFromMap(dataMap, "modifyFld");
            String isOtherTableField = getValueFromMap(dataMap, BuilderFormFieldNames.IS_OTHER_TABLE_FIELD);
            String archiveTable	= null;//TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS
            boolean isOtherField = false;

            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
//    				factory = DocumentBuilderFactory.newInstance();
//    				docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS starts
                        if(doc!=null && doc.getFirstChild()!=null)
                        {
                            archiveTable = XMLUtil.getTagText(doc.getFirstChild(),TableXMLDAO.TABLE_ARCHIVE_NAME);
                            if(StringUtil.isValid(archiveTable))
                            {
                                //request.setAttribute("archiveTable", archiveTable);
                                dataMap.put("archiveTable", archiveTable);
                            }
                        }
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS ends
                        /**
                         * Take backup before update xml data
                         */
//    				transfac = TransformerFactory.newInstance();
//    				trans = transfac.newTransformer();
                        fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        resultBackup = new StreamResult(fileLocBackup +  "_copy.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        file = new File(fileLocBackup +  "_copy.xml");
                        if(file.isFile()) {
                        	factory = null;
                        	docBuilder = null;
                        	this.initBDaoInstance();
                        	doc = docBuilder.parse(file);
                        }

                    } catch(Exception e){
                        return "";
                    }
                } else {
                    return "";
                }
            }
            if(StringUtil.isValidNew(modifyFld)) {
                fieldMappings = getFieldMappings(tableAnchor);
//    			String[] arrayNames = fldMao.getMapFieldNamesWithOrderBy(section);
                Field[] flsObj = fieldMappings.getSectionTableAllActiveDeactiveFieldsWithOrderBy(section);

                Info arrLst = new Info();
                boolean flag = false;
                int count = 0;
                if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {
                    if("true".equals(isActive)) {
                        for(Field s:flsObj) {
                            if(s == null) {
                                continue;
                            }
                            if(val.equals(s.getFieldName().trim())) {
                                flag = true;
                            }
                            if(flag) {
                                ++count;
                                if(count ==1) {
                                    arrLst.set(s.getFieldName(), flsObj.length-1);
                                } else {
                                    arrLst.set(s.getFieldName(), s.getOrderBy()-1);
                                }
                            } else {
                                arrLst.set(s.getFieldName(), s.getOrderBy());
                            }
                        }
                    } else {
                        Field[] flsObjA = fieldMappings.getSectionTableFieldsWithOrderBy(section);
                        Field[] flsObjD = fieldMappings.getDeactiveFieldMap(section);
                        for(Field s:flsObjD) {
                            if(val.equals(s.getFieldName())) {
                                flag = true;
                            }

                            if(flag) {
                                ++count;
                                if(count ==1) {
                                    if(flsObjA == null)
                                        arrLst.set(s.getFieldName(), 0);
                                    else
                                        arrLst.set(s.getFieldName(), flsObjA.length);
                                } else {
//    	    						arrLst.set(s.getFieldName(), s.getOrderBy()-1);
                                }
                            } else {
                                arrLst.set(s.getFieldName(), s.getOrderBy()+1);
                            }
                        }
                    }
                }


                NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                int size = nodeList.getLength();
                if(StringUtil.isValidNew(isOtherTableField)) {
                    isOtherField = Boolean.parseBoolean(isOtherTableField);
                    if(isOtherField) {
                        String otherFldTable = getValueFromMap(dataMap, BuilderFormFieldNames.OTHER_TABLE_NAME);

                        NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                        Node headerTablesNode = hNodeList.item(0);
                        if (headerTablesNode != null) {
                            Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                            for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                                Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                                String tSectionVal = getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                                for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                                    String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
//									String tSectionVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.SECTION);
                                    if(tSectionVal == null || !section.equals(tSectionVal)) {
                                        continue;
                                    }
//									if(StringUtil.isValidNew(otherFldTable) && StringUtil.isValidNew(tNameVal) && tNameVal.equals(otherFldTable)) {
                                    Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                                    for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                                        String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
//											System.out.println("tFldNameVal  -- " + tFldNameVal + ", val  " +val);
                                        if(tFldNameVal.equals(val)) {
                                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {
                                                if("true".equals(isActive)) {
                                                    isActive = "no";
                                                } else {
                                                    isActive = "yes";
                                                }
                                                setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.IS_ACTIVE, isActive);
                                            }
                                        }

                                        if(arrLst.size() > 1) {
                                            if(!StringUtil.isValidNew(arrLst.get(tFldNameVal))) {
                                                continue;
                                            }
//						            			System.out.println(" Fld Name Val --- where "+tFldNameVal);
                                            setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, arrLst.get(tFldNameVal));
                                        } else {
                                            if(val.equals(tFldNameVal)) {
                                                setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, arrLst.get(tFldNameVal));
                                            }
                                        }

                                    }
//									}
                                }
                            }
                        }
                    } else {
                        NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                        Node headerTablesNode = hNodeList.item(0);
                        if (headerTablesNode != null) {
                            Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                            for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                                Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                                String tSectionVal = getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                                for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                                    String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
                                    if(tSectionVal == null || !section.equals(tSectionVal)) {
                                        continue;
                                    }
                                    Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                                    for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                                        String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                                        if(tFldNameVal.equals(val)) {
                                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {
                                                if("true".equals(isActive)) {
                                                    isActive = "no";
                                                } else {
                                                    isActive = "yes";
                                                }
                                                setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.IS_ACTIVE, isActive);
                                            }
                                        }
                                        if(arrLst.size() > 1) {
                                            if(!StringUtil.isValidNew(arrLst.get(tFldNameVal))) {
                                                continue;
                                            }
                                            setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, arrLst.get(tFldNameVal));
                                        } else {
                                            if(val.equals(tFldNameVal)) {
                                                setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, arrLst.get(tFldNameVal));
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                nodeList.hashCode();
                for(int i=0;i< size;i++) {
                    node = nodeList.item(i);
                    if(node == null) {
                        continue;
                    }
                    String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                    Node retSNode = getNodeInChildren(node,TableXMLDAO.SECTION);
                    if(retSNode == null) {
                        continue;
                    }
                    String secVal = getTagText(node, TableXMLDAO.SECTION);

                    if(!secVal.equals(section)) {
                        continue;
                    }
//    				System.out.println("nameVal,section  -- " + nameVal+", "+section);

                    if(val.equals(nameVal)) {
                        if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {
                            if("true".equals(isActive)) {
                                isActive = "no";
                                updateFieldValueForHeatMeter(nameVal, false);
                            } else {
                                isActive = "yes";
                                updateFieldValueForHeatMeter(nameVal, true);

                            }
                          //Martin-20160728-018 starts
                            NodeList tablelist = 	doc.getElementsByTagName(TableXMLDAO.TABLE_NAME);
                            Node tableNode = tablelist.item(0);
                            String tableNameDB = tableNode.getFirstChild().getNodeValue();
                          //Martin-20160728-018 ends
                            String isCenterInfoField = getValueFromMap(dataMap, "isCenterInfoField");
                            String fileToWriteLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get("centerInfoDisplay");
                            Node retNode = getNodeInChildren(node,TableXMLDAO.IS_ACTIVE);
                            if(retNode == null) {
                                Element activeEle = doc.createElement(TableXMLDAO.IS_ACTIVE);
                                activeEle.appendChild(doc.createTextNode(isActive));
                                node.appendChild(activeEle);
                                if(!"yes".equals(modifyFld) &&  StringUtil.isValidNew(isCenterInfoField)){
                                	setTagOfXml(fileToWriteLoc,isActive,null,val,modifyFld,tableNameDB,"");//Martin-20160728-018
                                }
                            } else {
                                setTagText(node, TableXMLDAO.IS_ACTIVE, isActive);
                                if(!"yes".equals(modifyFld) &&  StringUtil.isValidNew(isCenterInfoField)){
                                	setTagOfXml(fileToWriteLoc,isActive,null,val,modifyFld,tableNameDB,"");//Martin-20160728-018
                                }
                            }
                        }
//    					break;
                    }

                    if(arrLst.size() > 1) {
                        if(!StringUtil.isValidNew(arrLst.get(nameVal))) {
                            continue;
                        }
//        				System.out.println(nameVal + " -- order val -- "+arrLst.get(nameVal));
                        setTagText(node, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
                    } else {
//        				System.out.println(nameVal + " -- active val -- "+arrLst.get(nameVal));
                        if(val.equals(nameVal)) {
                            setTagText(node, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
//    						break;
                        }
                    }
                }

//        		System.out.println("   size -- "+size);
    		/*
        		for(int i=0;i< size;i++) {
        			try {
        				node = nodeList.item(i);
        				String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
        				Node retNode = getNodeInChildren(node,TableXMLDAO.SECTION);
        				if(retNode == null) {
        					continue;
        				}
        				String secVal = getTagText(node, TableXMLDAO.SECTION);
        				if(!secVal.equals(section)) {
        					continue;
        				}
        				if(arrLst.size() > 1) {
            				if(!StringUtil.isValidNew(arrLst.get(nameVal))) {
            					continue;
            				}
            				System.out.println(nameVal + " -- order val -- "+arrLst.get(nameVal));
            				setTagText(node, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
        				} else {
            				System.out.println(nameVal + " -- active val -- "+arrLst.get(nameVal));
        					if(val.equals(nameVal)) {
        						setTagText(node, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
        						break;
        					}
        				}
        			} catch(Exception e) {
        				e.printStackTrace();
        			}
        		}*/

            }
            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
            //create string from xml tree
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            result = new StreamResult(fileLocBackup +  "_copy.xml");

            source = new DOMSource(doc);
            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;
        } /*catch(SAXException e) {
			e.printStackTrace();	
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} */catch(TransformerConfigurationException e) {
            return "";
        } catch(TransformerException e) {
//			e.printStackTrace();
            return "";
        } catch(Exception e) {
//			e.printStackTrace();
            return "";
        } finally {
            fieldMappings = null;
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	trans.clearParameters();
            result = null;
            source = null;
        }
        return fileLocBackup +  "_copy.xml";
    }
    
    public String getValueFromMap(HashMap dataMap, String keyName) {
    	String value = "";
    	if(dataMap != null && dataMap.size() > 0) {
    		value = (String)dataMap.get(keyName);
    	}
    	return value;
    }
    public void copyXML(HashMap dataMap) {
		String fileLocBackup = "";
		try {
			String fileLoc = getValueFromMap(dataMap, "newPath");
			String tableAnchor = getValueFromMap(dataMap, "tableAnchorName");//ENH_MODULE_CUSTOM_TABS
			String fileLocExport=getValueFromMap(dataMap, "newPathExport");// P_B_73908
			if(StringUtil.isValidNew(fileLoc)) {
				file = new File(fileLoc);
				if(file.isFile()) {
					try {
						this.initBDaoInstance();
						doc = docBuilder.parse(file);
						fileLocBackup = fileLoc.substring(0, fileLoc.indexOf("_copy.xml"));
						resultBackup = new StreamResult(fileLocBackup +  ".xml");
						source = new DOMSource(doc);
						trans.transform(source, resultBackup);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}

			doc.normalize();
			if(StringUtil.isValidNew(fileLocExport)) {// P_B_73908
				file = new File(fileLocExport);
				if(file.isFile()) {
					try {
						this.initBDaoInstance();
						doc = docBuilder.parse(file);
						fileLocBackup = fileLocExport.substring(0, fileLocExport.indexOf("_copy.xml"));
						resultBackup = new StreamResult(fileLocBackup +  ".xml");
						source = new DOMSource(doc);
						trans.transform(source, resultBackup);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				doc.normalize();
			}


			//			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    public String addModifyXmlData(HashMap dataMap) {
    	String fileLocBackup = "";
        try {
            String fileLoc = getValueFromMap(dataMap, BuilderFormFieldNames.FILE_LOCATION);
            String val = getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
            String isActive = getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE);
            String isMandatory = getValueFromMap(dataMap, BuilderFormFieldNames.IS_MANDATORY);
            String dVal = getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME);
            String predVal = getValueFromMap(dataMap, BuilderFormFieldNames.PRE_DISPLAY_NAME);
            String dExport = getValueFromMap(dataMap, BuilderFormFieldNames.EXPORTABLE);
            String isPiiEnabled = getValueFromMap(dataMap, "isPiiEnabled");
            String isCenterInfoField = getValueFromMap(dataMap, "isCenterInfoField");
            String isOtherTableField = getValueFromMap(dataMap, BuilderFormFieldNames.IS_OTHER_TABLE_FIELD);
            //String combooption = request.getParameter(BuilderFormFieldNames.DROPDOWN_OPTION);
            String combooption = getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION);
            //BB-20150203-259 (Dynamic Response based on parent field response) starts
            //String radioOption = request.getParameter(BuilderFormFieldNames.RADIO_OPTION);
            String radioOption = getValueFromMap(dataMap, BuilderFormFieldNames.RADIO_OPTION);
            //String checkboxOption = request.getParameter(BuilderFormFieldNames.CHECKBOX_OPTION);
            String checkboxOption = getValueFromMap(dataMap, BuilderFormFieldNames.CHECKBOX_OPTION);
            String dependentField = getValueFromMap(dataMap, "dependentField");
            //BB-20150203-259 (Dynamic Response based on parent field response) ends
            String forExportTable = getValueFromMap(dataMap, "forExportTable");
            String keyword = getValueFromMap(dataMap, "mailmergekeyword");
            String keyword1 = getValueFromMap(dataMap, "mailmergekeyword1");
            String modifyFld = getValueFromMap(dataMap, "modifyFld");
            //BB-20150319-268(FIM Campaign Center) start
            String includeInCampaign = getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN);
            //BB-20150319-268(FIM Campaign Center) ends
            String tableAnchor = getValueFromMap(dataMap, "tableAnchorName");//ENH_MODULE_CUSTOM_TABS
            String archiveTable	= null;//TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS
            String moduleName=getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME);
            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    try {
                        this.initBDaoInstance();
//    					factory = DocumentBuilderFactory.newInstance();
////    				factory.setValidating(false);
////    				factory.setNamespaceAware(true);
//    					docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS starts
                        if(doc!=null && doc.getFirstChild()!=null)
                        {
                            archiveTable = XMLUtil.getTagText(doc.getFirstChild(),TableXMLDAO.TABLE_ARCHIVE_NAME);
                            if(StringUtil.isValid(archiveTable))
                            {
                                //request.setAttribute("archiveTable", archiveTable);
                                dataMap.put("archiveTable", archiveTable);
                            }
                        }
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS ends
                        /**
                         * Take backup before update xml data
                         */
//    					transfac = TransformerFactory.newInstance();
//    					trans = transfac.newTransformer();
                        fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        resultBackup = new StreamResult(fileLocBackup +  "_copy.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        file = new File(fileLocBackup +  "_copy.xml");
                        if(file.isFile()) {
                        	factory = null;
                        	docBuilder = null;
                        	this.initBDaoInstance();
                        	doc = docBuilder.parse(file);
                        }
                    } catch(Exception e) {
                    	e.printStackTrace();
                        return "";
                    }
                } else {
                    return "";
                }
            }
            loc		= (String)getTableMappings().get(tableAnchor);
            String fileToWriteLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get("centerInfoDisplay");
            if(StringUtil.isValidNew(modifyFld)) {
                boolean isOtherField = false;
                if(StringUtil.isValidNew(isOtherTableField) && Boolean.parseBoolean(isOtherTableField)) {
                    isOtherField = Boolean.parseBoolean(isOtherTableField);
                    if(isOtherField) {
                        String otherFldTable = getValueFromMap(dataMap, BuilderFormFieldNames.OTHER_TABLE_NAME);
                        NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                        Node headerTablesNode = hNodeList.item(0);
                        if (headerTablesNode != null) {
                            Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                            String name			= null;
                            String value		= null;
                            String order		= null;
                            for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                                Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                                for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                                    String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
                                    String dependentTableAnchor=getAttributeValue(dependentTableNodes[looptables], "tableAnchor");
                                    if(StringUtil.isValidNew(otherFldTable) && StringUtil.isValidNew(tNameVal) && tNameVal.equals(otherFldTable)) {
                                        Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                                        for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                                            String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                                            String dependentFieldName=getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                                            if(tFldNameVal.equals(val)) {
                                            	String depTableAnchor = "";
                                            	if(StringUtil.isValidNew(dVal)) {
                                            		depTableAnchor = getAttributeValue(dependentTableNodes[looptables], "tableAnchor");
                                            		setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.DISPLAY_NAME, dVal);
                                            		
                                            		//P_Enh_Sync_Fields starts
                                                    String fieldTableName = getValueFromMap(dataMap, "fieldNamesForTab");
                                                    if(StringUtil.isValidNew(fieldTableName)) {
                                                    	String[] name1 = fieldTableName.split("##");
                                                    	String parentTableName = name1[0];
                                                    	String parentFieldName = name1[1];
                                                    	String otherField = name1[2];
                                                    	FieldMappings parentMapping = getFieldMappings(parentTableName);
                                                    	if(parentMapping != null) {
                                                    		Field parentField = parentMapping.getField(parentFieldName);
                                                    		if("true".equals(otherField)) {
                                                    			parentField = parentMapping.getOtherTableField(parentFieldName);
                                                    		}
                                                    		if(parentField != null) {
                                                    			if(StringUtil.isValidNew(parentField.getSyncWithField())) {
                                                    				fieldTableName = parentField.getSyncWithField();
                                                    			}
                                                    		}
                                                    	}
                                                    	parentMapping = null;
                                                    }
                                                    
                                                    if(StringUtil.isValidNew(fieldTableName)) {
                                                    	setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.SYNC_WITH, fieldTableName);
                                                    }
                                                    
                                                    if(StringUtil.isValidNew(getValueFromMap(dataMap, "moduleSpecification"))) {
                                                    	setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.SYNC_MODULE, getValueFromMap(dataMap, "moduleSpecification"));
                                                    }
                                                    
                                                    if(StringUtil.isValidNew(getValueFromMap(dataMap, "canReadOnly"))) {
                                            			setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.READ_ONLY, getValueFromMap(dataMap, "canReadOnly"));
                                            		}
                                                    
                                                    if(StringUtil.isValidNew(fieldTableName)) {
                                                    	String[] name1 = fieldTableName.split("##");
                                                    	String parentTableName = name1[0];
                                                    	String parentFieldName = name1[1];
                                                    	String otherField = name1[2];
                                                    	FieldMappings parentMapping = getFieldMappings(parentTableName);
                                                    	if(parentMapping != null) {
                                                    		Field parentField = parentMapping.getField(parentFieldName);
                                                    		if("true".equals(otherField)) {
                                                    			parentField = parentMapping.getOtherTableField(parentFieldName);
                                                    		}

                                                    		if(parentField != null) {
                                                    			if(parentField.isMandatoryField()) {
                                                    				setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.IS_MANDATORY, "true");
                                                    			}

                                                    			if(parentField.isSourceField()) {
                                                    				setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.SOURCE_FIELD, "true");
                                                    			}
                                                    			
                                                    			if(StringUtil.isValidNew(parentField.getValidationType())) {
                                                    				setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.VALIDATION_TYPE, parentField.getValidationType());
                                                    			}
                                                    		}
                                                    	}
                                                    	parentMapping = null;
                                                    	updateExistingTableXml(parentTableName, parentFieldName, dataMap, tableAnchor, otherField, getAttributeValue(tablesFldNodes[looptables], TableXMLDAO.IS_MANDATORY), getAttributeValue(tablesFldNodes[looptables], TableXMLDAO.SOURCE_FIELD), "false");
                                                    }
                                                    //P_Enh_Sync_Fields ends
                                            		
                                            		
                                            		if("Email".equals(getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) { //BB-20150319-268(FIM Campaign Center) starts
                                            			if(StringUtil.isValidNew(includeInCampaign)) {
                                            				setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.INCLUDE_IN_CAMPAIGN, includeInCampaign);
                                            			}
                                            		} //BB-20150319-268(FIM Campaign Center) ends
                                            		break;
                                            	}
                                                if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isMandatory)) {
                                                    setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.IS_MANDATORY, isMandatory);
                                                    break;
                                                }
                                                if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isPiiEnabled)) {
                                                	if(!"yes".equals(forExportTable) && StringUtil.isValidNew(tableAnchor) && StringUtil.isValidNew(val)){// BB-20150525-360 ends
                                                		if(StringUtil.isValidNew(archiveTable)){
                                                			updateColumnDataType(dependentTableAnchor,dependentFieldName,moduleName,isPiiEnabled,archiveTable);	
                                                		}else{
                                                			updateColumnDataType(dependentTableAnchor,dependentFieldName,moduleName,isPiiEnabled,null);
                                                		}
                                                	}// BB-20150525-360 ends
                                                    setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.PII_ENABLED, ""+!Boolean.parseBoolean(isPiiEnabled));// BB-20150525-360
                                                    setTagTextVal(doc, node, TableXMLDAO.DEFAULT_PII_ENABLED,"true");// BB-20150525-360
                                                    break;
                                                }
                                                if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isCenterInfoField)) {
                                                    setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.CENTER_INFO_DISPLAY, ""+!Boolean.parseBoolean(isCenterInfoField));
                                                    String dependentLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get(getAttributeValue(dependentTableNodes[looptables], "tableAnchor"));
                                                    addDependentField(fileLoc, fileToWriteLoc, getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME),dependentLoc,tNameVal,depTableAnchor);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                    int size = nodeList.getLength();
                    nodeList.hashCode();
                    for(int i=0;i< size;i++) {
                        node = nodeList.item(i);
                        if(val.equals(getTagText(node, TableXMLDAO.FIELD_NAME))) {
                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {
                                if("true".equals(isActive)) {
                                    isActive = "no";
                                } else {
                                    isActive = "yes";
                                }
                                setTagTextVal(doc, node, TableXMLDAO.IS_ACTIVE, isActive);
                            }
                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isMandatory)) {
                                setTagTextVal(doc, node, TableXMLDAO.IS_MANDATORY, isMandatory);
                            }
                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isPiiEnabled)) {
                            	
                         		if(!"yes".equals(forExportTable) && StringUtil.isValidNew(tableAnchor) && StringUtil.isValidNew(val)){// BB-20150525-360 starts
                            		if(StringUtil.isValidNew(archiveTable)){
                            			updateColumnDataType(tableAnchor,val,moduleName,null,archiveTable);	
                            		}else{
                            			updateColumnDataType(tableAnchor,val,moduleName,null,null);
                                	}// BB-20150525-360 ends
                            	}
                                setTagTextVal(doc, node, TableXMLDAO.PII_ENABLED, ""+!Boolean.parseBoolean(isPiiEnabled));
                                setTagTextVal(doc, node, TableXMLDAO.DEFAULT_PII_ENABLED,"true");// BB-20150525-360
                            }
                          //Martin-20160728-018 starts
                            NodeList tablelist = 	doc.getElementsByTagName(TableXMLDAO.TABLE_NAME);
                            Node tableNode = tablelist.item(0);
                            String tableNameDB = tableNode.getFirstChild().getNodeValue();
                            if(!"yes".equals(modifyFld) &&  StringUtil.isValidNew(isCenterInfoField)){
                            	setTagOfXml(fileToWriteLoc,isActive,isPiiEnabled,val,null,tableNameDB,"");
                            }
                            if("yes".equals(modifyFld) && StringUtil.isValidNew(dVal) && "true".equals(getTagText(node, TableXMLDAO.CENTER_INFO_DISPLAY))){
                            	setTagOfXml(fileToWriteLoc,"","",val,null,tableNameDB,dVal);
                            }//Martin-20160728-018 ends
                            if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isCenterInfoField) && !(StringUtil.isValidNew(isPiiEnabled) || StringUtil.isValidNew(isActive))) {
                            	if("false".equals(isCenterInfoField)) {
                            		importNodeToXml(fileLoc,fileToWriteLoc,val);
                            	} else {
                            		removeNodeFromXml(fileToWriteLoc,val,fileLoc,tableNameDB);//Martin-20160728-018
                            	}
                                setTagTextVal(doc, node, TableXMLDAO.CENTER_INFO_DISPLAY, ""+!Boolean.parseBoolean(isCenterInfoField));
                                
                            }
                            if(StringUtil.isValidNew(dVal)) {
                                setTagTextVal(doc, node, TableXMLDAO.DISPLAY_NAME, dVal);
                                setTagTextVal(doc, node, TableXMLDAO.DISPLAY_EXPORT_NAME, dVal);
                            }
                            //P_Enh_Sync_Fields starts
                            String fieldTableName = getValueFromMap(dataMap, "fieldNamesForTab");
                            
                            if(StringUtil.isValidNew(fieldTableName)) {
                            	String[] name = fieldTableName.split("##");
                            	String parentTableName = name[0];
                            	String parentFieldName = name[1];
                            	String otherField = name[2];
                            	FieldMappings parentMapping = getFieldMappings(parentTableName);
                            	if(parentMapping != null) {
                            		Field parentField = parentMapping.getField(parentFieldName);
                            		if("true".equals(otherField)) {
                            			parentField = parentMapping.getOtherTableField(parentFieldName);
                            		}
                            		if(parentField != null) {
                            			if(StringUtil.isValidNew(parentField.getSyncWithField())) {
                            				fieldTableName = parentField.getSyncWithField();
                            			}
                            		}
                            	}
                            	parentMapping = null;
                            }
                            
                            if(StringUtil.isValidNew(fieldTableName)) {
                            	setTagTextVal(doc, node, TableXMLDAO.SYNC_WITH, fieldTableName);
                            	Node syncNode = getNodeInChildren(node, TableXMLDAO.SYNC_WITH);
                            	if(syncNode != null) {
                            		setTagAttr(syncNode, TableXMLDAO.SYNC_MODULE, getValueFromMap(dataMap, "moduleSpecification")); //adding module specification
                            	}
                            }
                            
                            if(StringUtil.isValidNew(getValueFromMap(dataMap, "canReadOnly"))) {
                    			setTagTextVal(doc, node, TableXMLDAO.READ_ONLY, getValueFromMap(dataMap, "canReadOnly"));
                    		}
                            
                            if(StringUtil.isValidNew(fieldTableName)) {
                            	String[] name = fieldTableName.split("##");
                            	String parentTableName = name[0];
                            	String parentFieldName = name[1];
                            	String otherField = name[2];
                            	FieldMappings parentMapping = getFieldMappings(parentTableName);
                            	if(parentMapping != null) {
                            		Field parentField = parentMapping.getField(parentFieldName);
                            		if("true".equals(otherField)) {
                            			parentField = parentMapping.getOtherTableField(parentFieldName);
                            		}

                            		if(parentField != null) {
                            			if(parentField.isMandatoryField()) {
                            				setTagTextVal(doc, node, TableXMLDAO.IS_MANDATORY, "true"); //updating the mandatory
                            			}

                            			if(parentField.isSourceField()) {
                            				setTagTextVal(doc, node, TableXMLDAO.SOURCE_FIELD, "true"); //updating the source Field
                            			}
                            			
                            			if(StringUtil.isValidNew(parentField.getValidationType())) {
                            				Node lenNodeV = getNodeInChildren(node,TableXMLDAO.VALIDATION);
                            				if(dataMap != null && dataMap.size() > 0) {
                        						dataMap.put(BuilderFormFieldNames.FLD_VALIDATION_TYPE, parentField.getValidationType());
                        					}
                            				
                            				if(lenNodeV == null) {
                            					Element activeEle = doc.createElement(TableXMLDAO.VALIDATION);
                            					activeEle.appendChild(getElementNode(doc, TableXMLDAO.VALIDATION_TYPE, parentField.getValidationType()));
                            					node.appendChild(activeEle);
                            				} else {
                            					setTagTextVal(doc, lenNodeV, TableXMLDAO.VALIDATION_TYPE, parentField.getValidationType()); //updating the source Field
                            				}
                            			}
                            		}
                            	}
                            	parentMapping = null;


                            	updateExistingTableXml(parentTableName, parentFieldName, dataMap, tableAnchor, otherField, getTagText(node, TableXMLDAO.IS_MANDATORY), getTagText(node, TableXMLDAO.SOURCE_FIELD), "false");
                            }
                            //P_Enh_Sync_Fields ends
                            
                            String dropdownOpt = getValueFromMap(dataMap, "dropdownOpt");
                            if("Email".equals(getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)) && StringUtil.isValidNew(includeInCampaign)) {       //BB-20150319-268(FIM Campaign Center) starts
                                Node lenNodeV = getNodeInChildren(node,TableXMLDAO.VALIDATION);
                                setTagTextVal(doc, lenNodeV, TableXMLDAO.INCLUDE_IN_CAMPAIGN, includeInCampaign);
                            } //BB-20150319-268(FIM Campaign Center) ends
                            if(StringUtil.isValidNew(getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)) && !Boolean.parseBoolean(getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)) && !StringUtil.isValid(dropdownOpt)) {
                                String checkValue = getValueFromMap(dataMap, "checkValue");
                                boolean isInputVal = Boolean.parseBoolean(checkValue);
                                if(!isInputVal) {
                                    Node retNode = getNodeInChildren(node,TableXMLDAO.DATA_TYPE);
                                    Node disNode = getNodeInChildren(node,TableXMLDAO.DISPLAY_TYPE);
                                    Node multiSelectNode = getNodeInChildren(node, TableXMLDAO.IS_MULTISELECT); //Multi_Select_Issue
                                    String dateType = getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE);
                                    if(!StringUtil.isValidNew(dateType)) {
                                    	dateType = getValueFromMap(dataMap, "dataTypeModify");
                                    }
                                    String colVal = getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN);
                                    String rowVal = getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW);
                                    String dataTypeVal="";
                                    String isMultiSelect="";//Multi_Select_Issue
                                    String dateVal = "";
                                    String disVal = "";
                                    if(StringUtil.isValidNew(dateType)) {
                                        if("varchar".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.TEXT_LBL;
                                        } else if("text".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.TEXTAREA;
                                        } else if("Date".equals(dateType)) {
                                            dateVal = BuilderConstants.DATE_LBL;
                                            disVal = BuilderConstants.DATE_LBL;
                                        } else if("int".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.NUMBER;
                                        } else if("combo".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.COMBO;
                                        } else if("radio".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.RADIO;
                                        } else if("double".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.NUMERIC;
                                        } else if("checkbox".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.CHECKBOX;
                                        } else if("numeric".equals(dateType)) {
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.NUMERIC;
                                        } else if("file".equals(dateType)) { //BB-20150203-259 (Add Document as field for positioning) starts
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.FILE_TYPE;
                                        } else if("multiselect".equals(dateType)) { //BB-20150203-259 (MultiSelect combo in add new field changes) starts
                                            dateVal = BuilderConstants.STRING;
                                            disVal = BuilderConstants.COMBO;
                                            isMultiSelect = BuilderConstants.IS_MULTI_SELECT; //Multi_Select_Issue ends
                                        } 
                                        
                                        //BB-20150203-259 (Add Document as field for positioning) ends
                                    }
                                    if(retNode == null) {
                                        Element activeEle = doc.createElement(TableXMLDAO.DATA_TYPE);
                                        activeEle.appendChild(doc.createTextNode(dateVal));
                                        node.appendChild(activeEle);
                                    } else {
                                        setTagText(node, TableXMLDAO.DATA_TYPE, dateVal);
                                    }
                                    if(disNode == null) {
                                        Element activeEle = doc.createElement(TableXMLDAO.DISPLAY_TYPE);
                                        activeEle.appendChild(doc.createTextNode(disVal));
                                        node.appendChild(activeEle);
                                    } else {
                                        dataTypeVal = getTagText(node, TableXMLDAO.DISPLAY_TYPE);
                                        //request.setAttribute("dataTypeVal", dataTypeVal);
                                        dataMap.put("dataTypeVal", dataTypeVal);

                                        setTagText(node, TableXMLDAO.DISPLAY_TYPE, disVal);
                                    }
                                    //Multi_Select_Issue starts
                                    if("multiselect".equals(dateType)) {
                                    	if(multiSelectNode == null) {
                                    		Element activeEle = doc.createElement(TableXMLDAO.IS_MULTISELECT);
                                    		activeEle.appendChild(doc.createTextNode(isMultiSelect));
                                    		node.appendChild(activeEle);
                                    	} else {
                                    		setTagText(node, TableXMLDAO.IS_MULTISELECT, isMultiSelect);
                                    	}
                                    } else {
                                    	removeNode(node, TableXMLDAO.IS_MULTISELECT);
                                    } //Multi_Select_Issue ends
                                    if(StringUtil.isValidNew(dateType) && ("varchar".equals(dateType) || "int".equals(dateType))) {
                                        Node lenNode = getNodeInChildren(node,TableXMLDAO.DATA_TYPE);
                                        String lenVal = getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH);
                                        if("TextArea".equals(dataTypeVal)) {
                                            removeNode(node, TableXMLDAO.NO_OF_COLUMN);
                                            removeNode(node, TableXMLDAO.NO_OF_ROW);
                                        }
                                        if(StringUtil.isValidNew(lenVal)) {
                                            setTagTextVal(doc, node, TableXMLDAO.DB_FIELD_LENGTH, lenVal);
                                        }
                                    }
                                    if(StringUtil.isValidNew(dateType) && ("numeric".equals(dateType) || "file".equals(dateType))) { //BB-20150203-259 (Add Document as field for positioning)
                                        Node lenNode = getNodeInChildren(node,TableXMLDAO.DATA_TYPE);
                                        String lenVal = getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH);
                                        if(BuilderConstants.TEXTAREA.equals(dataTypeVal)) {
                                            removeNode(node, TableXMLDAO.NO_OF_COLUMN);
                                            removeNode(node, TableXMLDAO.NO_OF_ROW);
                                        }
                                        if(BuilderConstants.TEXT_LBL.equals(dataTypeVal)) {
                                            removeNode(node, TableXMLDAO.DB_FIELD_LENGTH);
                                        }
                                    }
                                    /**
                                     * OPTION_VIEW_H_V
                                     * Start
                                     */
                                    if("radio".equals(dateType) || "checkbox".equals(dateType)) {
                                        setTagTextVal(doc, node, TableXMLDAO.FIELD_OPTION_VIEW, getValueFromMap(dataMap, BuilderFormFieldNames.OPTION_VIEW_TYPE)); //BB-20150203-259 (Dynamic Response based on parent field response) starts
                                        if("radio".equals(dateType)) {
                                            Node radioOptionNode = getNodeInChildren(node,TableXMLDAO.RADIO_OPTION);
                                            if(radioOptionNode == null) {
                                                if("1".equals(radioOption)) {
                                                    Element radioElement = doc.createElement(TableXMLDAO.RADIO_OPTION);
                                                    radioElement.appendChild(doc.createTextNode(radioOption));
                                                    node.appendChild(radioElement);

                                                    Element dependentFieldEle = doc.createElement(TableXMLDAO.DEPENDENT_PARENT);
                                                    dependentFieldEle.appendChild(doc.createTextNode(dependentField));
                                                    node.appendChild(dependentFieldEle);
                                                }
                                            } else {
                                                if("0".equals(radioOption)) {
                                                    removeNode(node, TableXMLDAO.RADIO_OPTION);
                                                    removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                                }
                                            }
                                        } else if("checkbox".equals(dateType)) {
                                            Node checkBoxOptionNode = getNodeInChildren(node,TableXMLDAO.CHECKBOX_OPTION);
                                            if(checkBoxOptionNode == null) {
                                                if("1".equals(checkboxOption)) {
                                                    Element chkboxElement = doc.createElement(TableXMLDAO.CHECKBOX_OPTION);
                                                    chkboxElement.appendChild(doc.createTextNode(checkboxOption));
                                                    node.appendChild(chkboxElement);

                                                    Element dependentFieldEle = doc.createElement(TableXMLDAO.DEPENDENT_PARENT);
                                                    dependentFieldEle.appendChild(doc.createTextNode(dependentField));
                                                    node.appendChild(dependentFieldEle);
                                                }
                                            } else {
                                                if("0".equals(checkboxOption)) {
                                                    removeNode(node, TableXMLDAO.CHECKBOX_OPTION);
                                                    removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                                }
                                            }
                                        } //BB-20150203-259 (Dynamic Response based on parent field response) ends
                                    } else {
                                        removeNode(node, TableXMLDAO.FIELD_OPTION_VIEW);
                                        if("combo".equals(dateType)) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
                                            Node comboOptionNode = getNodeInChildren(node,TableXMLDAO.DROPDOWN_OPTION);
                                            if(comboOptionNode == null) {
                                                if("3".equals(combooption)) {
                                                    Element comboOptionElement = doc.createElement(TableXMLDAO.DROPDOWN_OPTION);
                                                    comboOptionElement.appendChild(doc.createTextNode(combooption));
                                                    node.appendChild(comboOptionElement);

                                                    Element dependentFieldEle = doc.createElement(TableXMLDAO.DEPENDENT_PARENT);
                                                    dependentFieldEle.appendChild(doc.createTextNode(dependentField));
                                                    node.appendChild(dependentFieldEle);
                                                }
                                            } else {
                                                if("0".equals(combooption)) {
                                                    removeNode(node, TableXMLDAO.DROPDOWN_OPTION);
                                                    removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                                }
                                            }
                                        }
                                    } //BB-20150203-259 (Dynamic Response based on parent field response) ends
                                    /**
                                     * end
                                     */
                                    String dependentParentField = getTagText(node, TableXMLDAO.DEPENDENT_PARENT);
                                    if(StringUtil.isValidNew(dependentParentField)) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
                                        dependentParentField = dependentParentField.substring(0, dependentParentField.indexOf("##"));
                                    } //BB-20150203-259 (Dynamic Response based on parent field response) ends
                                    if("varchar".equals(dateType) || "numeric".equals(dateType)) {
                                        String validationVal = getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE);
                                        String includeCampaign = getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN);//BB-20150319-268(FIM Campaign Center)
                                        Node lenNodeV = getNodeInChildren(node,TableXMLDAO.VALIDATION);
                                        if(lenNodeV == null) {
                                            Element activeEle = doc.createElement(TableXMLDAO.VALIDATION);
                                            if("numeric".equals(dateType)) {
                                                activeEle.appendChild(getElementNode(doc, TableXMLDAO.VALIDATION_TYPE, getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1")));
                                            } else {
                                                activeEle.appendChild(getElementNode(doc, TableXMLDAO.VALIDATION_TYPE, getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)));
                                                //BB-20150319-268(FIM Campaign Center) starts
                                                if("Email".equals(getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)) && "0".equals(includeCampaign)) {
                                                    activeEle.appendChild(getElementNode(doc, TableXMLDAO.INCLUDE_IN_CAMPAIGN, includeCampaign));
                                                } else {
                                                    removeNode(lenNodeV, TableXMLDAO.INCLUDE_IN_CAMPAIGN);
                                                }
                                                //BB-20150319-268(FIM Campaign Center) ends
                                            }
                                            node.appendChild(activeEle);
                                        } else {
                                            if("numeric".equals(dateType)) {
                                                setTagTextVal(doc, lenNodeV, TableXMLDAO.VALIDATION_TYPE, getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"));
                                            } else {
                                                setTagTextVal(doc, lenNodeV, TableXMLDAO.VALIDATION_TYPE, validationVal);
                                                //BB-20150319-268(FIM Campaign Center) starts
                                                if("Email".equals(getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) {
                                                	if(StringUtil.isValidNew(includeCampaign)) {
                                                		setTagTextVal(doc, lenNodeV, TableXMLDAO.INCLUDE_IN_CAMPAIGN, includeCampaign);
                                                	}
                                                } else {
                                                    removeNode(lenNodeV, TableXMLDAO.INCLUDE_IN_CAMPAIGN);
                                                }
                                                //BB-20150319-268(FIM Campaign Center) ends
                                            }
                                        }
                                        removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                        for(int j=0;j< size;j++) { //removing for normal fields
                                            node = nodeList.item(j);
                                            if(node != null) {
                                                String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                                                if(nameVal.equals(dependentParentField)) {
                                                    Node dependentNode = getNodeInChildren(node,TableXMLDAO.DEPENDENT);
                                                    if(dependentNode == null) {
                                                        continue;
                                                    } else {
                                                        Node[] dependentChildNodes	=  XMLUtil.getNodesInChildren(dependentNode, TableXMLDAO.DEPENDENT_CHILD);
                                                        if(dependentChildNodes.length == 1) {
                                                            if(val.equals(getAttributeValue(dependentChildNodes[0], TableXMLDAO.NAME))) {
                                                                node.removeChild(dependentNode);
                                                                break;
                                                            }
                                                        } else {
                                                            for (int looptables=0; dependentChildNodes != null && looptables < dependentChildNodes.length; looptables++) {
                                                                if(val.equals(getAttributeValue(dependentChildNodes[looptables], TableXMLDAO.NAME))) {
                                                                    dependentNode.removeChild(dependentChildNodes[looptables]);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                    	if("varchar".equals(dataTypeVal)) {
                                    		removeNode(node, TableXMLDAO.DB_FIELD_LENGTH);
                                    		removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                    		for(int j=0;j< size;j++) { //removing for normal fields
                                    			node = nodeList.item(j);
                                    			if(node != null) {
                                    				String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                                    				if(nameVal.equals(dependentParentField)) {
                                    					Node dependentNode = getNodeInChildren(node,TableXMLDAO.DEPENDENT);
                                    					if(dependentNode == null) {
                                    						continue;
                                    					} else {
                                    						Node[] dependentChildNodes	=  XMLUtil.getNodesInChildren(dependentNode, TableXMLDAO.DEPENDENT_CHILD);
                                    						if(dependentChildNodes.length == 1) {
                                    							if(val.equals(getAttributeValue(dependentChildNodes[0], TableXMLDAO.NAME))) {
                                    								node.removeChild(dependentNode);
                                    								break;
                                    							}
                                    						} else {
                                    							for (int looptables=0; dependentChildNodes != null && looptables < dependentChildNodes.length; looptables++) {
                                    								if(val.equals(getAttributeValue(dependentChildNodes[looptables], TableXMLDAO.NAME))) {
                                    									dependentNode.removeChild(dependentChildNodes[looptables]);
                                    									break;
                                    								}
                                    							}
                                    						}
                                    					}
                                    				}
                                    			}
                                    		}
                                    	}
                                    	removeNode(node, TableXMLDAO.VALIDATION);
                                    }
                                    if(StringUtil.isValidNew(dateType) && "text".equals(dateType)) {
                                        Node colNode = getNodeInChildren(node,TableXMLDAO.NO_OF_COLUMN);
                                        Node rowNode = getNodeInChildren(node,TableXMLDAO.NO_OF_ROW);
                                        if(BuilderConstants.NUMBER.equals(dataTypeVal) || BuilderConstants.TEXT_LBL.equals(dataTypeVal)) {
                                            removeNode(node, TableXMLDAO.DB_FIELD_LENGTH);
                                        }
                                        if(BuilderConstants.NUMERIC.equals(dataTypeVal) || BuilderConstants.TEXT_LBL.equals(dataTypeVal)) {
                                            removeNode(node, TableXMLDAO.VALIDATION);
                                        }
                                        if(StringUtil.isValidNew(colVal))
                                        {
                                            setTagTextVal(doc, node, TableXMLDAO.NO_OF_COLUMN, colVal);
                                        }
                                        if(StringUtil.isValidNew(rowVal)) {
                                            setTagTextVal(doc, node, TableXMLDAO.NO_OF_ROW, rowVal);
                                        }
                                        removeNode(node, TableXMLDAO.DEPENDENT_PARENT);
                                        for(int j=0;j< size;j++) { //removing for normal fields
                                            node = nodeList.item(j);
                                            if(node != null) {
                                                String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                                                if(nameVal.equals(dependentParentField)) {
                                                    Node dependentNode = getNodeInChildren(node,TableXMLDAO.DEPENDENT);
                                                    if(dependentNode == null) {
                                                        continue;
                                                    } else {
                                                        Node[] dependentChildNodes	=  XMLUtil.getNodesInChildren(dependentNode, TableXMLDAO.DEPENDENT_CHILD);
                                                        if(dependentChildNodes.length == 1) {
                                                            if(val.equals(getAttributeValue(dependentChildNodes[0], TableXMLDAO.NAME))) {
                                                                node.removeChild(dependentNode);
                                                                break;
                                                            }
                                                        } else {
                                                            for (int looptables=0; dependentChildNodes != null && looptables < dependentChildNodes.length; looptables++) {
                                                                if(val.equals(getAttributeValue(dependentChildNodes[looptables], TableXMLDAO.NAME))) {
                                                                    dependentNode.removeChild(dependentChildNodes[looptables]);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if(StringUtil.isValidNew(dExport)) {
                                    if("0".equals(dExport)) {
                                        dExport = "true";
                                    } else {
                                        dExport = "false";
                                    }
                                    setTagTextVal(doc, node, TableXMLDAO.FIELD_EXPORT, dExport);
                                }
                            }
                            break;
                        }
                    }
                }

            } else {
                root = doc.getDocumentElement();
//    			Text lineBreak = doc.createTextNode("\n\t");
//    			element.appendChild(lineBreak);
                
                //ZCUB-20150505-144 -CM_Document Field Changes Start
                if(("cm".equals(getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME)) || "lead".equals(getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME)) || "account".equals(getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME)) || "opportunity".equals(getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME))) && "file".equals(getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE))){
                	Node headerTablesNode	= null;
                	NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                	int listLength = list.getLength();
                    if (listLength<1) {
                        return "";
                    }
                    else if (listLength>1) {
                        return "";
                    }
                    headerTablesNode =  list.item(0);
                	Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                	
                	for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        String fieldSection		= XMLUtil.getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                        String section = getValueFromMap(dataMap, BuilderFormFieldNames.SECTION);
                        if(section.equals(fieldSection)) {
                        	Element childElement = doc.createElement(TableXMLDAO.DEPENDENT_TABLE);
                        	String name=moduleName+"Documents_"+IDGenerator.getNextKey();
                        	
                        	dataMap.put(BuilderFormFieldNames.FIELD_NAME,name+moduleName+"DocumentAttachment");
                        	dataMap.put(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD, "true");
                        	childElement.setAttribute(TableXMLDAO.NAME, name);
                        	childElement.setAttribute("tableAnchor", moduleName+"Documents");
                        	childElement.setAttribute("tableWithalias", name);
                        	
                        	Element childchildElement = doc.createElement(TableXMLDAO.TABLE_FIELD);
                        	childchildElement.setAttribute(BuilderFormFieldNames.COLUMN_NAME, "DOCUMENT_"+moduleName.toUpperCase()+"_ATTACHMENT");
                        	childchildElement.setAttribute(TableXMLDAO.DISPLAY_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
                        	childchildElement.setAttribute(TableXMLDAO.IS_ACTIVE, getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE));
                        	childchildElement.setAttribute(TableXMLDAO.IS_BUILD_FIELD, BuilderFormFieldNames.NO);//P_CM_B_60109
                        	childchildElement.setAttribute(BuilderFormFieldNames.SECTION, section);
                        	childchildElement.setAttribute(TableXMLDAO.ORDER_BY, getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
                        	childchildElement.setAttribute(TableXMLDAO.NAME, moduleName+"DocumentAttachment");
                        	
                        	childElement.appendChild(childchildElement);
                        	headerNodes[loop].appendChild(childElement);
                        }     
                	}
                } else { //ZCUB-20150505-144 -CM_Document Field Changes End 

                	if("existing".equals(getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE))) { //P_Enh_Sync_Fields starts
                		String fieldTableName = getValueFromMap(dataMap, "fieldNamesForTab");
                		String moduleSpec = getValueFromMap(dataMap, "moduleSpecification");
                		
                		Node existingNode = existingFieldNode(getValueFromMap(dataMap, "fieldNamesForTab"), doc,getValueFromMap(dataMap, "canReadOnly"));

                		setTagTextVal(doc, existingNode, TableXMLDAO.FIELD_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
                		if(StringUtil.isValidNew(dExport)) {
                			if("0".equals(dExport)) {
                				dExport = "true";
                			} else {
                				dExport = "false";
                			}
                			setTagTextVal(doc, existingNode, TableXMLDAO.FIELD_EXPORT, dExport);
                		}
                		setTagTextVal(doc, existingNode, TableXMLDAO.DISPLAY_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
                		setTagTextVal(doc, existingNode, TableXMLDAO.DB_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
                		setTagTextVal(doc, existingNode, TableXMLDAO.SECTION, getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));
                		setTagTextVal(doc, existingNode, TableXMLDAO.IS_BUILD_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD));
                		if(StringUtil.isValidNew(getValueFromMap(dataMap, "canReadOnly"))) {
                			setTagTextVal(doc, existingNode, TableXMLDAO.READ_ONLY, getValueFromMap(dataMap, "canReadOnly"));
                		}

                		Node syncNode = getNodeInChildren(existingNode, TableXMLDAO.SYNC_WITH);
                		if(syncNode == null) {
                			Element syncEle = doc.createElement(TableXMLDAO.SYNC_WITH);
                			syncEle.appendChild(doc.createTextNode(fieldTableName));
                			syncEle.setAttribute(TableXMLDAO.SYNC_MODULE, moduleSpec);
                			existingNode.appendChild(syncEle);
                		} else {
                			fieldTableName = XMLUtil.getTagText(existingNode, TableXMLDAO.SYNC_WITH);
                			String syncModuleName=XMLUtil.getAttributeValue(syncNode, TableXMLDAO.SYNC_MODULE);
                			if("within".equals(syncModuleName) && !moduleSpec.equals(moduleName)){
                				setTagAttr(syncNode, TableXMLDAO.SYNC_MODULE, moduleSpec); //adding module specification
    						}
                		}
                		
                		Node sync = getNodeInChildren(existingNode,TableXMLDAO.SYNC);
                		if(sync != null) {
                			existingNode.removeChild(sync); //removing because this particular tag is not required in existing child.
                		}
                		
                		String existingDisplayType = XMLUtil.getTagText(existingNode, TableXMLDAO.DISPLAY_TYPE);
                		if("String".equals(existingDisplayType) || "Text".equals(existingDisplayType)) { //for adding the size
                			setTagTextVal(doc, existingNode, TableXMLDAO.DB_FIELD_LENGTH, "255");
                		}
                		
                		//updating parent existing value in XML
                		String[] name = fieldTableName.split("##");
                		String parentTableName = name[0];
                		String parentFieldName = name[1];
                		String otherField = name[2];
               			updateExistingTableXml(parentTableName, parentFieldName, dataMap, tableAnchor, otherField, null, null, XMLUtil.getTagText(existingNode, TableXMLDAO.PII_ENABLED));

                		Node mailMergeNode = getNodeInChildren(existingNode, TableXMLDAO.MAILMERGE);
                		if(StringUtil.isValid(keyword) && keyword.length()>39) {
                			keyword = keyword.substring(0,38);
                		}
                		setTagAttr(mailMergeNode, "keyword-name", "$"+keyword+"$");

                		if("no".equals(getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE))) {
                			setTagTextVal(doc, existingNode, TableXMLDAO.ORDER_BY, getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
                		}
                		else if("yes".equals(getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE))) {
                			FieldMappings fmappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
                			if(fmappings != null) {
                				Field deactiveFieldsArr[] = fmappings.getDeactiveFieldMap(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));
                				int deactiveLength = 0;
                				if(deactiveFieldsArr != null) {
                					deactiveLength = deactiveFieldsArr.length;
                				}

                				int fieldSize = Integer.parseInt(getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
                				int newOrder = fieldSize - deactiveLength;
                				setTagTextVal(doc, existingNode, TableXMLDAO.ORDER_BY, ""+newOrder);

                				Field deactiveField = null;
                				String deactFldName = null;
                				for(int fieldIndex=0; fieldIndex<deactiveLength; fieldIndex++) {
                					deactiveField = deactiveFieldsArr[fieldIndex];
                					deactFldName = deactiveField.getFieldName();
                					NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                					int size = nodeList.getLength();
                					String sectionName = getValueFromMap(dataMap, BuilderFormFieldNames.SECTION);
                					for(int i=0;i< size;i++) {
                						node = nodeList.item(i);
                						String sec = getTagText(node, TableXMLDAO.SECTION);
                						if(sec == null || !sec.equals(sectionName)) {
                							continue;
                						}
                						if(StringUtil.isValidNew(deactFldName) && deactFldName.equals(getTagText(node, TableXMLDAO.FIELD_NAME))) {
                							setTagTextVal(doc, node, TableXMLDAO.ORDER_BY, ""+(newOrder+fieldIndex+1));
                						}
                					}
                				}
                			}
                		}
                		root.appendChild(existingNode);
                		//P_Enh_Sync_Fields ends
                	} else {
                		Element childElement = doc.createElement(TableXMLDAO.FIELD);
                		Element childElement1 = null;

                		childElement.setAttribute("summary","true");
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.FIELD_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)));
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME)));
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.DB_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME)));

                		if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                			childElement1 = doc.createElement(TableXMLDAO.FIELD);
                			childElement1.setAttribute("summary","true");

                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.FIELD_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME+"1")));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_NAME, getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME+"1")));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.DB_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME+"1")));
                		}

                		String dateType = getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE);
                		String dateVal = "String";
                		String disVal = "Text";
                		String isMultiSelect="";//BB-20150203-259 (MultiSelect combo in add new field changes)
                		String functionName = ""; //BB-20150203-259 (Dynamic Response based on parent field response)
                		if(StringUtil.isValidNew(dateType)) {
                			if("varchar".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.TEXT_LBL;
                			} else if("text".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.TEXTAREA;
                			} else if("Date".equals(dateType)) {
                				dateVal = BuilderConstants.DATE_LBL;
                				disVal = BuilderConstants.DATE_LBL;
                			} else if("int".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.NUMBER;
                			} else if("combo".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.COMBO;
                				functionName = "getCustomCombo('dataCombo', this, '"+getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)+"', ''"; //BB-20150203-259 (Dynamic Response based on parent field response)
                			} else if("radio".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.RADIO;
                				functionName = "getRadio('dataRadio', this, '"+getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)+"', ''"; //BB-20150203-259 (Dynamic Response based on parent field response)
                			} else if("double".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.NUMERIC;
                			} else if("checkbox".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.CHECKBOX;
                				functionName = "getCheckBox('dataCheckBox', this, '"+getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)+"', ''"; //BB-20150203-259 (Dynamic Response based on parent field response)
                			} else if("numeric".equals(dateType)) {
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.NUMERIC;
                			} else if("file".equals(dateType)) { //BB-20150203-259 (Add Document as field for positioning) starts
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.FILE_TYPE;
                				//BB-20150203-259 (Add Document as field for positioning) ends
                			} else if("multiselect".equals(dateType)) { //BB-20150203-259 (MultiSelect combo in add new field changes) starts
                				dateVal = BuilderConstants.STRING;
                				disVal = BuilderConstants.COMBO;
                				isMultiSelect=BuilderConstants.IS_MULTI_SELECT;
                				functionName = "getCustomCombo('dataCombo', this, '"+getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)+"', ''"; //BB-20150203-259 (Dynamic Response based on parent field response)
                			}
                			//BB-20150203-259 (MultiSelect combo in add new field changes) ends
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DATA_TYPE, dateVal));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_TYPE, disVal));
                			//BB-20150203-259 (MultiSelect combo in add new field changes) starts
                			if("multiselect".equals(dateType))
                			{
                				childElement.appendChild(getElementNode(doc,TableXMLDAO.IS_MULTISELECT,isMultiSelect));
                			}
                			if(StringUtil.isValidNew(getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)) && !Boolean.parseBoolean(getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)) && ("radio".equals(dateType) || "Date".equals(dateType) || "combo".equals(dateType))) //711-20160226-198 custom fields not present for group by in custom reports
                			{
                				childElement.appendChild(getElementNode(doc,TableXMLDAO.GROUP_BY,"true"));
                			}
                			//BB-20150203-259 (MultiSelect combo in add new field changes) ends
                			if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.DATA_TYPE, dateVal));
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_TYPE, disVal));
                			}
                			if(StringUtil.isValidNew(dateType) && ("varchar".equals(dateType) || "int".equals(dateType))) {
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.DB_FIELD_LENGTH, getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH)));
                			}
                			if("text".equals(dateType)) {
                				/**COLSPAN_IN_XML_TAG**/
                				/*Attr attra = doc.createAttribute(TableXMLDAO.COLSPAN);
						attra.setValue("2");
						childElement.setAttributeNode(attra);*/
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.NO_OF_COLUMN, getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN)));
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.NO_OF_ROW, getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW)));
                			}
                			if("varchar".equals(dateType)) {
                				Element activeEle = doc.createElement(TableXMLDAO.VALIDATION);
                				activeEle.appendChild(getElementNode(doc, TableXMLDAO.VALIDATION_TYPE, getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)));
                				if("Email".equals(getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)) && "0".equals(getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN))) { //BB-20150319-268(FIM Campaign Center) starts
                					activeEle.appendChild(getElementNode(doc, TableXMLDAO.INCLUDE_IN_CAMPAIGN, getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN)));
                				} //BB-20150319-268(FIM Campaign Center) ends
                				childElement.appendChild(activeEle);
                			}
                			if("numeric".equals(dateType)) {
                				Element activeEle = doc.createElement(TableXMLDAO.VALIDATION);
                				activeEle.appendChild(getElementNode(doc, TableXMLDAO.VALIDATION_TYPE, getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1")));
                				childElement.appendChild(activeEle);
                			}
                			/**
                			 * OPTION_VIEW_H_V
                			 * Start
                			 */
                			if("radio".equals(dateType) || "checkbox".equals(dateType)) {
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.FIELD_OPTION_VIEW, getValueFromMap(dataMap, BuilderFormFieldNames.OPTION_VIEW_TYPE)));
                			}
                			/**
                			 * OPTION_VIEW_H_V
                			 * end
                			 */
                		} else {
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DATA_TYPE, dateVal));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_TYPE, disVal));
                			if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.DATA_TYPE, dateVal));
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_TYPE, disVal));
                			}
                		}
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.SECTION, getValueFromMap(dataMap, BuilderFormFieldNames.SECTION)));
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_ACTIVE, getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE)));//ENH_MODULE_CUSTOM_TABS ends
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_MANDATORY, "false"));
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_BUILD_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)));

                		if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.SECTION, getValueFromMap(dataMap, BuilderFormFieldNames.SECTION)));
                			//ENH_MODULE_CUSTOM_TABS starts
                			//childElement1.appendChild(getElementNode(doc, TableXMLDAO.IS_ACTIVE, "no"));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.IS_ACTIVE, getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE)));
                			//ENH_MODULE_CUSTOM_TABS ends
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.IS_MANDATORY, "false"));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.IS_BUILD_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD)));
                		}

                		if(StringUtil.isValidNew(dExport)) {
                			if("0".equals(dExport)) {
                				dExport = "true";
                			} else {
                				dExport = "false";
                			}
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.FIELD_EXPORT, dExport));
                			if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.FIELD_EXPORT, dExport));
                			}
                		}
                		//ENH_MODULE_CUSTOM_TABS starts
                		/*if field is added as deactive by default last order is assigned else if added as active by default order for deactive fields
    			is modified and new field's order is just before the first deactive field*/
                		if("no".equals(getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE)))
                		{
                			if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                				//START : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO)));
                				childElement1.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, ""+(Integer.parseInt(getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO)) + 1)));
                				//END : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                			} else
                				childElement.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO)));
                		}
                		else if("yes".equals(getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE)))
                		{
                			FieldMappings fmappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
                			if(fmappings!=null)
                			{
                				Field deactiveFieldsArr[] = fmappings.getDeactiveFieldMap(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));
                				int deactiveLength = 0;
                				if(deactiveFieldsArr!=null)
                				{
                					deactiveLength = deactiveFieldsArr.length;
                				}
                				int fieldSize = Integer.parseInt(getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
                				int newOrder = fieldSize - deactiveLength;
                				if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                					//START : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                					childElement.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, ""+newOrder));
                					childElement1.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, ""+(newOrder + 1)));
                					//END : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                				} else
                					childElement.appendChild(getElementNode(doc, TableXMLDAO.ORDER_BY, ""+newOrder));
                				Field deactiveField = null;
                				String deactFldName = null;
                				for(int fieldIndex=0;fieldIndex<deactiveLength;fieldIndex++)
                				{
                					deactiveField = deactiveFieldsArr[fieldIndex];
                					deactFldName = deactiveField.getFieldName();
                					NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                					int size = nodeList.getLength();
                					String sectionName = getValueFromMap(dataMap, BuilderFormFieldNames.SECTION);
                					for(int i=0;i< size;i++) {
                						node = nodeList.item(i);
                						boolean isFieldOfOtherTable=deactiveField.isFieldOfOtherTable();//Martin-20160727-017 starts
                						String sec = getTagText(node, TableXMLDAO.SECTION);
                						if(isFieldOfOtherTable){
                							NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                							Node headerTablesNode = hNodeList.item(0);
                							if (headerTablesNode != null) {
                								Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                								for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                									Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                									String tSectionVal = getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                									for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                										String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
                										if(tSectionVal == null || !sectionName.equals(tSectionVal)) {
                											continue;
                										}
                										Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                										for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                											String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                											if(deactFldName.equals(tFldNameVal)) {
                												setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, ""+(newOrder+fieldIndex+1));
                											}

                										}
                									}
                								}
                							}
                						}else{
                							if(sec == null || !sec.equals(sectionName)) {
                								continue;
                							}
                							if(StringUtil.isValidNew(deactFldName) && deactFldName.equals(getTagText(node, TableXMLDAO.FIELD_NAME))) {
                								setTagTextVal(doc, node, TableXMLDAO.ORDER_BY, ""+(newOrder+fieldIndex+1));
                							}
                						}//Martin-20160727-017 ends
                					}
                				}
                			}
                		}
                		//ENH_MODULE_CUSTOM_TABS ends

                		if(StringUtil.isValid(combooption) && "1".equals(combooption)) {
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DROPDOWN_OPTION, combooption));

                			Element eleN = getElementNode(doc, TableXMLDAO.COMBO, null);
                			eleN.appendChild(getElementNode(doc, TableXMLDAO.COMBO_SOURCE_VALUES_METHOD, BuilderConstants.COMBO_COUNTRY_METHOD));
                			childElement.appendChild(eleN);

                			childElement.appendChild(getElementNode(doc, TableXMLDAO.TRANSFORM_METHOD, BuilderConstants.TRANSFORM_COUNTRY_METHOD));
                			//CUSTOM_REPORT_SORTING_ISSUE starts
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_TABLE, TableAnchors.COUNTRIES));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_FIELD, FieldNames.COUNTRY_ID));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_VALUE, FieldNames.NAME));
                			//CUSTOM_REPORT_SORTING_ISSUE ends
                		} else if(StringUtil.isValid(combooption) && "2".equals(combooption)){
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DROPDOWN_OPTION, combooption));

                			Element eleN = getElementNode(doc, TableXMLDAO.COMBO, null);
                			eleN.appendChild(getElementNode(doc, TableXMLDAO.PARENT, MultiTenancyUtil.getTenantConstants().TRUE));
                			eleN.appendChild(getElementNode(doc, TableXMLDAO.DEPENDENT_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME+"1")));
                			eleN.appendChild(getElementNode(doc, TableXMLDAO.COMBO_SOURCE_VALUES_METHOD, BuilderConstants.COMBO_COUNTRY_METHOD));
                			childElement.appendChild(eleN);
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.TRANSFORM_METHOD, BuilderConstants.TRANSFORM_COUNTRY_METHOD));
                			//CUSTOM_REPORT_SORTING_ISSUE starts
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_TABLE, TableAnchors.COUNTRIES));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_FIELD, FieldNames.COUNTRY_ID));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.SRC_VALUE, FieldNames.NAME));
                			//CUSTOM_REPORT_SORTING_ISSUE ends

                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.DROPDOWN_OPTION, combooption));
                			//BB-20150203-259 (Dynamic Response based on parent field response) starts
                			Element eleN1 = getElementNode(doc, TableXMLDAO.COMBO, null);
                			eleN1.appendChild(getElementNode(doc, TableXMLDAO.PARENT, BuilderConstants.FALSE));
                			eleN1.appendChild(getElementNode(doc, TableXMLDAO.DEPENDENT_FIELD, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)));
                			eleN1.appendChild(getElementNode(doc, TableXMLDAO.COMBO_SOURCE_VALUES_METHOD, BuilderConstants.COMBO_STATE_METHOD));
                			eleN1.appendChild(getElementNode(doc, TableXMLDAO.COMBO_METHOD_PARAM, getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME)));
                			childElement1.appendChild(eleN1);
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.TRANSFORM_METHOD, BuilderConstants.TRANSFORM_STATE_METHOD));
                			//CUSTOM_REPORT_SORTING_ISSUE starts
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.SRC_TABLE, TableAnchors.REGIONS));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.SRC_FIELD, FieldNames.REGION_NO));
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.SRC_VALUE, FieldNames.REGION_NAME));
                			//CUSTOM_REPORT_SORTING_ISSUE ends
                		} else if(StringUtil.isValid(combooption) && "3".equals(combooption)) { //for dependent case
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DROPDOWN_OPTION, combooption));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DEPENDENT_PARENT, dependentField));
                		}

                		if("radio".equals(dateType) && "1".equals(radioOption)) {
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.RADIO_OPTION, radioOption));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DEPENDENT_PARENT, dependentField));
                		} else if("checkbox".equals(dateType) && "1".equals(checkboxOption)) {
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.CHECKBOX_OPTION, checkboxOption));
                			childElement.appendChild(getElementNode(doc, TableXMLDAO.DEPENDENT_PARENT, dependentField));
                		}

                		//adding dependent child node at parent field node
                		if(StringUtil.isValidNew(dependentField)) {
                			String parentField = dependentField.substring(0, dependentField.indexOf("##"));
                			String parentHeaderName="";//P_CM_B_60909
                			NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                			Node headerTablesNode = hNodeList.item(0);

                			boolean isOtherField = false; //to check whether field is of other table or nor
                			if (headerTablesNode != null) {
                				Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                				for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                					Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                					for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                						String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
                						Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                						for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                							String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                							if(parentField.equals(tFldNameVal)) {
                								isOtherField = true;
                								parentHeaderName=tNameVal;//P_CM_B_60909
                								break;
                							}
                						}
                					}
                				}
                			}

                			if(!isOtherField) { //normal fields
                				NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                				int size = nodeList.getLength();
                				nodeList.hashCode();
                				for(int i=0;i< size;i++) {
                					node = nodeList.item(i);
                					if(parentField.equals(getTagText(node, TableXMLDAO.FIELD_NAME))) {
                						String isMultiple = getTagText(node, TableXMLDAO.IS_MULTISELECT);
                						String checkBox = getTagText(node, TableXMLDAO.DISPLAY_TYPE);
                						if("true".equals(isMultiple)) {
                							functionName = functionName + ", 'multiple')";
                						} else if("Checkbox".equals(checkBox)) {
                							functionName = functionName + ", 'checkBox')";
                						} else {
                							functionName = functionName + ", 'normal')";
                						}
                						Node dependentNode = getNodeInChildren(node,TableXMLDAO.DEPENDENT);
                						if(dependentNode == null) { //if dependent node is not present
                							Element dependentElement = doc.createElement(TableXMLDAO.DEPENDENT);
                							Element dependentChildElement = doc.createElement(TableXMLDAO.DEPENDENT_CHILD);
                							dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
                							dependentChildElement.setAttribute("function", functionName);
                							dependentElement.appendChild(dependentChildElement);
                							node.appendChild(dependentElement);
                						} else { //if node is present and creating dependent-child node
                							Element dependentChildElement = doc.createElement(TableXMLDAO.DEPENDENT_CHILD);
                							dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
                							dependentChildElement.setAttribute("function", functionName);
                							dependentNode.appendChild(dependentChildElement);
                						}
                					}
                				}
                			} else { //other fields
                				if (headerTablesNode != null) {
                					Node[] headerNodes = XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                						for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                							String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
                							if(parentHeaderName != null && parentHeaderName.equals(tNameVal)){//P_CM_B_60909
                								Node dependentNode = getNodeInChildren(dependentTableNodes[looptables], TableXMLDAO.DEPENDENT);
                								functionName = functionName + ", 'normal')";
                								if(dependentNode == null) {
                									Element dependentElement = doc.createElement(TableXMLDAO.DEPENDENT);
                									Element dependentChildElement = doc.createElement(TableXMLDAO.DEPENDENT_CHILD);
                									dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
                									dependentChildElement.setAttribute("function", functionName);
                									dependentChildElement.setAttribute("parent-field", parentField);
                									dependentElement.appendChild(dependentChildElement);
                									dependentTableNodes[looptables].appendChild(dependentElement);
                								} else {
                									Element dependentChildElement = doc.createElement(TableXMLDAO.DEPENDENT_CHILD);
                									dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
                									dependentChildElement.setAttribute("function", functionName);
                									dependentChildElement.setAttribute("parent-field", parentField);
                									dependentNode.appendChild(dependentChildElement);
                								}
                							}
                						}
                					}
                				}
                			}
                		}
                		//BB-20150203-259 (Dynamic Response based on parent field response) ends
                		if(!("franchiseeCall".equals(tableAnchor) || "fimfranchiseeRemarks".equals(tableAnchor) || "fimTasks".equals(tableAnchor) || "fsleadRemarks".equals(tableAnchor) || "fsTasks".equals(tableAnchor) || "fsleadCall".equals(tableAnchor)))
                		{
                			Element activeEle = doc.createElement(TableXMLDAO.MAILMERGE);
                			activeEle.setAttribute("is-active", "true");
                			if(StringUtil.isValid(keyword) && keyword.length()>39)
                				keyword = keyword.substring(0,38);
                			activeEle.setAttribute("keyword-name","$"+keyword+"$");
                			childElement.appendChild(activeEle);
                			if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                				Element activeEle1 = doc.createElement(TableXMLDAO.MAILMERGE);
                				if(StringUtil.isValid(keyword1) && keyword1.length()>39)
                					keyword1 = keyword1.substring(0,38);
                				activeEle1.setAttribute("is-active", "true");
                				activeEle1.setAttribute("keyword-name","$"+keyword1+"$");
                				childElement1.appendChild(activeEle1);
                			}
                		}
                		
                		
                		childElement.appendChild(getElementNode(doc, TableXMLDAO.PII_ENABLED, "false"));
                		if(StringUtil.isValid(combooption) && "2".equals(combooption)) {
                			childElement1.appendChild(getElementNode(doc, TableXMLDAO.PII_ENABLED, "false"));
                		}

                		root.appendChild(childElement);

                		//START : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                		if(StringUtil.isValid(combooption) && "2".equals(combooption)){
                			root.appendChild(childElement1);
                		}
                		//END : P_ENH_RE-ORDER_COUNTRY/STATE : 26/03/2014 : Veerpal Singh
                	}
                }
        }

            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
            //create string from xml tree
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            result = new StreamResult(fileLocBackup +  "_copy.xml");
//	        transformer.transform(source, result);

            source = new DOMSource(doc);
            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;

            if(StringUtil.isValidNew(predVal)) {
                if(!predVal.equals(dVal)) {
                    addCustomKeyWordInPropertyFile(dVal);
                }
            } else {
                addCustomKeyWordInPropertyFile(dVal);
            }
        } /*catch(SAXException e) {
			e.printStackTrace();	
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} */catch(TransformerConfigurationException e) {
            return "";
        } catch(TransformerException e) {
//			e.printStackTrace();
            return "";
        } catch(Exception e) {
			e.printStackTrace();
            return "";
        } finally {
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	transfac = null;
//	    	trans = null;
//	    	trans.clearParameters();
            result = null;
            source = null;
        }
        return fileLocBackup +  "_copy.xml";
    }

    public boolean addModifyXmlSectionData(HttpServletRequest request) {
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            String formTabName = getRequestValue(request, "formTabName");
            String secName = getRequestValue(request, BuilderFormFieldNames.SECTION_NAME);
            String secVal = getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE);
            //String isBuildSection = getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION);
            String modify = getRequestValue(request, "action");
            String order = getRequestValue(request, BuilderFormFieldNames.ORDER);
            String dVal = getRequestValue(request, BuilderFormFieldNames.DISPLAY_NAME);
            String isTabularSection=getRequestValue(request, BuilderFormFieldNames.IS_TABULAR_SECTION);//P_Enh_FormBuilder_Tabular_Section
            Node headerTablesNode	= null;
            Info logInfo = new Info();

            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    try {
                        this.initBDaoInstance();
//    				factory = DocumentBuilderFactory.newInstance();
//    				docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        /**
                         * Take backup before update xml data
                         */
//    				transfac = TransformerFactory.newInstance();
//    				trans = transfac.newTransformer();
                        String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {
                root = doc.getDocumentElement();

                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                Node[] headerNodes	= getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                String name			= null;
                String value		= null;
                for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                    name		= getAttributeValue(headerNodes[loop], TableXMLDAO.NAME);
                    if(secName.equals(name)) {
                        //setTagText(headerNodes[loop], TableXMLDAO.VALUE, secVal);
                        setTagAttr(headerNodes[loop], TableXMLDAO.VALUE,secVal); //BB-20150203-259 (Section Rename changes)  
                        String isTabularSectionNode=getTagText(headerNodes[loop], TableXMLDAO.IS_TABULAR_SECTION);//P_Enh_FormBuilder_Tabular_Section starts
                        String tabularSectionTableAnchor=getTagText(headerNodes[loop], TableXMLDAO.TABULAR_SECTION_TABLE_ANCHOR);
                        if("yes".equals(isTabularSectionNode)){
                        	request.setAttribute("sectionFileName",tabularSectionTableAnchor+".xml");
                        	Info tabularSectionData=addModifyTabularSectionData(request);
                        }//P_Enh_FormBuilder_Tabular_Section ends
                        break;
                    }
                }

                //Modifying logs
                String[] idField = {"id"};
                logInfo.setIDField(idField);
                logInfo.set("id", "");
                logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
                logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR));
                logInfo.set(BuilderFormFieldNames.SECTION_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME));
                logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
                logInfo.set(BuilderFormFieldNames.ORDER_NO, getRequestValue(request, BuilderFormFieldNames.ORDER));
                logInfo.set(BuilderFormFieldNames.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION));
                logInfo.set(BuilderFormFieldNames.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION));
                logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));
                logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
                WebCacheBypass webCacheIP = new WebCacheBypass(request);
                String userIP = webCacheIP.getRemoteAddr();
                logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
                logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Modify");
                logInfo.set(BuilderFormFieldNames.MODULE_NAME, (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME)));
                
            } else {
                root = doc.getDocumentElement();
                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                String randomNo=IDGenerator.getNextKey();   //P_B_77002 
                Element childElement = doc.createElement(TableXMLDAO.HEADER);
                childElement.setAttribute(TableXMLDAO.NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME)+randomNo);  //P_B_77002
                childElement.setAttribute(TableXMLDAO.VALUE, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
                childElement.setAttribute(TableXMLDAO.ORDER, getRequestValue(request, BuilderFormFieldNames.ORDER));
                request.setAttribute("randomNoSection",randomNo);
                if(StringUtil.isValid(formTabName)){
                    childElement.appendChild(getElementNode(doc, "tab-name", getRequestValue(request, "formTabName")));
                }
                childElement.appendChild(getElementNode(doc, TableXMLDAO.TYPE, getRequestValue(request, BuilderFormFieldNames.TYPE)));
                childElement.appendChild(getElementNode(doc, TableXMLDAO.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION)+randomNo)); //P_B_77002
                childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION)));
                if("yes".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
                	Info tabularSectionData=addModifyTabularSectionData(request);
                	if(tabularSectionData!=null && tabularSectionData.size()>0){
                	childElement.appendChild(getElementNode(doc, TableXMLDAO.TABULAR_SECTION_TABLE_ANCHOR, tabularSectionData.getString("tableAnchor")));
                	childElement.appendChild(getElementNode(doc, TableXMLDAO.TABULAR_SECTION_DB_TABLE, tabularSectionData.getString("dbTable")));
                	childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_TABULAR_SECTION,"yes"));
                	}
                }//P_Enh_FormBuilder_Tabular_Section ends
 
                headerTablesNode.appendChild(childElement);
                //root.appendChild(childElement);
                /**
                 * Generating Logs 
                 */
                String[] idField = {"id"};
                logInfo.setIDField(idField);
                logInfo.set("id", "");
                logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
                logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR));
                logInfo.set(BuilderFormFieldNames.SECTION_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME)+randomNo);    //P_B_77002
                logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
                logInfo.set(BuilderFormFieldNames.ORDER_NO, getRequestValue(request, BuilderFormFieldNames.ORDER));
                logInfo.set(BuilderFormFieldNames.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION)+randomNo);               //P_B_77002
                logInfo.set(BuilderFormFieldNames.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION));
                //logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
                WebCacheBypass webCacheIP = new WebCacheBypass(request);
                String userIP = webCacheIP.getRemoteAddr();
                logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
                logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Create");
                logInfo.set(BuilderFormFieldNames.MODULE_NAME, (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME)));//ENH_MODULE_CUSTOM_TABS
            }

            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
            //create string from xml tree
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            result = new StreamResult(fileLoc);

            source = new DOMSource(doc);

//	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;
            addCustomKeyWordInPropertyFile(secVal);
            try {
                create("formBuilderSectionLogs", logInfo);
            } catch(Exception e) {
            }
        } /*catch(SAXException e) {
			e.printStackTrace();	
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} */catch(TransformerConfigurationException e) {
//			e.printStackTrace();
            return false;
        } catch(TransformerException e) {
//			e.printStackTrace();
            return false;
        } catch(Exception e) {
//			e.printStackTrace();
            return false;
        } finally {
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	transfac = null;
//	    	trans = null;
//	    	trans.clearParameters();
            result = null;
            source = null;
        }
        return true;
    }

    public boolean addModifyXmlDocumentData(HttpServletRequest request) {
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            String docTitle = getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE);
            String docLabel = getRequestValue(request, BuilderFormFieldNames.DOCUMENT_LABEL);
            String modify = getRequestValue(request, "action");
            String order = getRequestValue(request, BuilderFormFieldNames.ORDER);
            String section = getRequestValue(request, BuilderFormFieldNames.SECTION);
            //P_FS_Enh_BuilderForm starts
            String moduleShortName=getRequestValue(request, "moduleShortName");
            if(!StringUtil.isValid(moduleShortName))
                moduleShortName="fim";
            //P_FS_Enh_BuilderForm ends
            Node headerTablesNode	= null;

            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    try {
                        this.initBDaoInstance();
//    				factory = DocumentBuilderFactory.newInstance();
//    				docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        /**
                         * Take backup before update xml data
                         */
//    				transfac = TransformerFactory.newInstance();
//    				trans = transfac.newTransformer();
                        String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {

                String fieldName = getRequestValue(request, BuilderFormFieldNames.FIELD_NAME);
                String docOption =  getRequestValue(request, BuilderFormFieldNames.DOCUMENT_OPTION);
                root = doc.getDocumentElement();
                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                if (headerTablesNode != null) {
                    Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                    for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        String fieldSection		= XMLUtil.getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                        if(section.equals(fieldSection)) {
                            Node[] documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DOCUMENTS);

                            if(documentsNode == null) {

                            }

                            if("fimMu".equals(moduleShortName) || "area".equals(moduleShortName)){//P_Enh_Mu-Entity_FormGenerator
                            	moduleShortName="fim";
                            }
                            if(documentsNode != null) {
                                for (int loopdocs=0; documentsNode != null && loopdocs < documentsNode.length; loopdocs++) {
                                    Node[] docFldNodes	=  XMLUtil.getNodesInChildren(documentsNode[loopdocs], TableXMLDAO.DOCUMENT);
                                    for (int loopdocsfld=0; docFldNodes != null && loopdocsfld < docFldNodes.length; loopdocsfld++) {
                                        HashMap hMap = XMLUtil.getNodeAttributes(docFldNodes[loopdocsfld]);
                                        String count = (String)hMap.get(TableXMLDAO.COUNT);
                                        String name = (String)hMap.get(TableXMLDAO.NAME);
                                        if(name.equals(fieldName)) {
                                            setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_OPTION, docOption);
                                            if(docOption != null && "0".equals(docOption)) {
                                                String titleDisplay = (String)hMap.get(TableXMLDAO.TITLE_DISPLAY);
                                                //P_FS_Enh_BuilderForm starts
                                                if("fs".equals(moduleShortName))
                                                    setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_NAME, FieldNames.DOCUMENT_TITLE);
                                                else
                                                    setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_NAME, moduleShortName+"DocumentTitle");
                                                //setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_NAME, BuilderFormFieldNames.FIM_DOCUMENT_TITLE);
                                                //P_FS_Enh_BuilderForm ends
                                                setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_DISPLAY, docTitle);
                                                //P_FS_Enh_BuilderForm starts
                                                if("fs".equals(moduleShortName))
                                                    setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_COLUMN, "DOCUMENT_TITLE");
                                                else
                                                    setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_COLUMN, "DOCUMENT_"+moduleShortName.toUpperCase()+"_TITLE");
                                                //setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_COLUMN, "DOCUMENT_FIM_TITLE");
                                                //P_FS_Enh_BuilderForm ends
                                                if(StringUtil.isValid(titleDisplay))
                                                    request.setAttribute(BuilderFormFieldNames.DOCUMENT_TITLE+"_OLD", titleDisplay);
                                            } else {
//        										removeTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_NAME);
                                                removeTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_DISPLAY);
                                                removeTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.TITLE_COLUMN);
                                            }
    										/*setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_NAME, BuilderFormFieldNames.FIM_DOCUMENT_ATTACHMENT);
    										setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_DISPLAY, docLabel);
    										setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_COLUMN, "DOCUMENT_FIM_ATTACHMENT");*/
                                            setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_NAME, moduleShortName+"DocumentAttachment"); //P_FS_Enh_BuilderForm
                                            setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_DISPLAY, docLabel);
                                            setTagAttr(docFldNodes[loopdocsfld],  TableXMLDAO.DOC_COLUMN, "DOCUMENT_"+moduleShortName.toUpperCase()+"_ATTACHMENT"); //P_FS_Enh_BuilderForm
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            } else {

                root = doc.getDocumentElement();
                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                if (headerTablesNode != null) {
                    Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                    for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        String fieldSection		= XMLUtil.getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                        if(section.equals(fieldSection)) {
                            Node[] documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DOCUMENTS);
                            if(documentsNode==null || documentsNode.length==0) {
                                Element childElement = doc.createElement(TableXMLDAO.DOCUMENTS);
                                childElement.setAttribute(TableXMLDAO.NAME, moduleShortName+"Documents"+IDGenerator.getNextKey()); //P_FS_Enh_BuilderForm
                                //childElement.setAttribute(TableXMLDAO.NAME, "fimDocuments"+IDGenerator.getNextKey());
                                childElement.setAttribute("tableAnchor", moduleShortName+"Documents"); //P_FS_Enh_BuilderForm
                                //childElement.setAttribute("tableAnchor", "fimDocuments");
                                //childElement.setAttribute(TableXMLDAO.TAB_NAME, "FIM_DOCUMENTS");
                                if("fimMu".equals(moduleShortName)){//P_Enh_Mu-Entity_FormGenerator starts
                                childElement.setAttribute(TableXMLDAO.TAB_NAME,"DOCUMENT_FIM_MU"); 
                                }else if("area".equals(moduleShortName)){
                                childElement.setAttribute(TableXMLDAO.TAB_NAME,"DOCUMENT_FIM_AREA");
                                }else{//P_Enh_Mu-Entity_FormGenerator ends
                                childElement.setAttribute(TableXMLDAO.TAB_NAME,moduleShortName.toUpperCase()+"_DOCUMENTS"); //P_FS_Enh_BuilderForm
                                }
                                childElement.setAttribute(TableXMLDAO.SECTION, fieldSection);
                                headerNodes[loop].appendChild(childElement);
                            }
                            if("fimMu".equals(moduleShortName) || "area".equals(moduleShortName)){//P_Enh_Mu-Entity_FormGenerator
                            	moduleShortName="fim";
                            }
                            documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DOCUMENTS);

                            if(documentsNode != null) {
                                String docOption =  getRequestValue(request, BuilderFormFieldNames.DOCUMENT_OPTION);
                                for (int loopdocs=0;  loopdocs < documentsNode.length; loopdocs++) {
                                    String name = IDGenerator.getNextKey();
                                    if(StringUtil.isValidNew((String)request.getAttribute("tabularSectionDocName"))){
                                    name=(String)request.getAttribute("tabularSectionDocName");
                                    }
                                    if("true".equals(getRequestValue(request, BuilderFormFieldNames.IS_TABULAR_SECTION))){
                                    	request.setAttribute("tabularSectionDocName",name);
                                    }
                                    Element childElement = doc.createElement(TableXMLDAO.DOCUMENT);
                                    childElement.setAttribute(TableXMLDAO.COUNT, order);
                                    childElement.setAttribute(TableXMLDAO.NAME, name);
                                    childElement.setAttribute(TableXMLDAO.DOC_OPTION, docOption);
                                    //P_FS_Enh_BuilderForm starts
                                    if("fs".equals(moduleShortName))
                                        childElement.setAttribute(TableXMLDAO.TITLE_NAME, FieldNames.DOCUMENT_TITLE);
                                    else
                                        childElement.setAttribute(TableXMLDAO.TITLE_NAME, moduleShortName+"DocumentTitle");
                                    //P_FS_Enh_BuilderForm ends
                                    //childElement.setAttribute(TableXMLDAO.TITLE_NAME, BuilderFormFieldNames.FIM_DOCUMENT_TITLE);
                                    if(docOption != null && "0".equals(docOption)&& StringUtil.isValid(getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE))) {
                                        childElement.setAttribute(TableXMLDAO.TITLE_DISPLAY, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE));
                                        //P_FS_Enh_BuilderForm starts
                                        if("fs".equals(moduleShortName))
                                            childElement.setAttribute(TableXMLDAO.TITLE_COLUMN, "DOCUMENT_TITLE");
                                        else
                                            childElement.setAttribute(TableXMLDAO.TITLE_COLUMN,  "DOCUMENT_"+moduleShortName.toUpperCase()+"_TITLE");
                                        //P_FS_Enh_BuilderForm ends
                                        //childElement.setAttribute(TableXMLDAO.TITLE_COLUMN, "DOCUMENT_FIM_TITLE");
                                    }
            		    			/*childElement.setAttribute(TableXMLDAO.DOC_NAME, BuilderFormFieldNames.FIM_DOCUMENT_ATTACHMENT);
            		    			childElement.setAttribute(TableXMLDAO.DOC_DISPLAY, docLabel);
            		    			childElement.setAttribute(TableXMLDAO.DOC_COLUMN, "DOCUMENT_FIM_ATTACHMENT");*/
                                    childElement.setAttribute(TableXMLDAO.DOC_NAME, moduleShortName+"DocumentAttachment"); //P_FS_Enh_BuilderForm
                                    childElement.setAttribute(TableXMLDAO.DOC_DISPLAY, docLabel);
                                    childElement.setAttribute(TableXMLDAO.DOC_COLUMN, "DOCUMENT_"+moduleShortName.toUpperCase()+"_ATTACHMENT"); //P_FS_Enh_BuilderForm

                                    request.setAttribute(BuilderFormFieldNames.FIELD_NAME, name);
                                    documentsNode[0].appendChild(childElement);
                                    break;
                                }
                            }

                        }
                    }
                }
                //root.appendChild(childElement);
            }

            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
            //create string from xml tree
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            result = new StreamResult(fileLoc);

            source = new DOMSource(doc);
            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;
        } /*catch(SAXException e) {
			e.printStackTrace();	
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} */catch(TransformerConfigurationException e) {
            return false;
        } catch(TransformerException e) {
            return false;
        } catch(Exception e) {
            return false;
        } finally {
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	trans.clearParameters();
//	    	transfac = null;
//	    	trans = null;
            result = null;
            source = null;
        }
        return true;
    }

    public boolean addModifyFieldOrderData(HttpServletRequest request) {
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            int fieldNo = Integer.parseInt(getRequestValue(request, "fimFormSize"));
            String section = getRequestValue(request, BuilderFormFieldNames.SECTION);
            String val = "";
            String dateVal = "";
//			System.out.println("fimFormSize " + fieldNo);

            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
//    				factory = DocumentBuilderFactory.newInstance();
//    				docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        /**
                         * Take backup before update xml data
                         */
//    					transfac = TransformerFactory.newInstance();
//    					trans = transfac.newTransformer();
                        String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
            int size = nodeList.getLength();
            nodeList.hashCode();
            for(int i=0;i< size;i++) {
                node = nodeList.item(i);
                String sec = getTagText(node, TableXMLDAO.SECTION);
                if(sec == null || !sec.equals(section)) {
                    continue;
                }
                for(int j=0;j< fieldNo;j++) {
                    val = request.getParameter(FieldNames.FIELD_NAME+"_"+j);
                    if(StringUtil.isValidNew(val) && val.equals(getTagText(node, TableXMLDAO.FIELD_NAME))) {
                        dateVal = request.getParameter(FieldNames.FIELD_NAME+"_"+j + "_" + FieldNames.ORDER_NO);
                        setTagTextVal(doc, node, TableXMLDAO.ORDER_BY, dateVal);
                    }
                }
            }

            NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
            Node headerTablesNode = hNodeList.item(0);
            if (headerTablesNode != null) {
                Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                    String sec = getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                    if(sec == null || !sec.equals(section)) {
                        continue;
                    }

                    Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                    for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                        String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
//						System.out.println(" fimForm tNameVal " + tNameVal);
//						String tSectionVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.SECTION);
//						System.out.println(" fimForm tSectionVal " + tSectionVal);
//						if(tSectionVal == null || !tSectionVal.equals(section)) {
//							continue;
//						}
                        Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                        for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                            String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                            for(int j=0;j< fieldNo;j++) {
                                val = request.getParameter(FieldNames.FIELD_NAME+"_"+j);
//								System.out.print(", "+j + " fimForm val of dependent  " + val);
                                if(StringUtil.isValidNew(val) && val.equals(tFldNameVal)) {
                                    dateVal = request.getParameter(FieldNames.FIELD_NAME+"_"+j + "_" + FieldNames.ORDER_NO);
                                    setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, dateVal);
                                }
                            }
                        }
                    }
                }
            }
            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            result = new StreamResult(fileLoc);

            source = new DOMSource(doc);
            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;
        } /*catch(SAXException e) {
			e.printStackTrace();	
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} */catch(TransformerException e) {
            return false;
        } catch(Exception e) {
            return false;
        } finally {
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	transfac = null;
//	    	trans = null;
//	    	trans.clearParameters();
            result = null;
            source = null;
        }
        return true;
    }
    // BB-20150203-259 (Section Re-ordering changes) starts
    public boolean addModifySectionOrderData(HttpServletRequest request) {
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            int SectionCount = Integer.parseInt(getRequestValue(request, "fimFormSize"));
            String val = "";
            String dateVal = "";
            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
                        doc = docBuilder.parse(file);
                        /**
                         * Take backup before update xml data
                         */
                        String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(SectionCount>0){
                NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.HEADER);
                int size = nodeList.getLength();
                nodeList.hashCode();
                for(int i=0;i< size;i++) {
                    node = nodeList.item(i);
                    for(int j=0;j<=SectionCount;j++) {
                        val = request.getParameter(BuilderFormFieldNames.SECTION_NAME+"_"+j);
                        if(StringUtil.isValidNew(val) && val.equals(getAttributeValue(node, TableXMLDAO.NAME))) {
                            dateVal = request.getParameter(BuilderFormFieldNames.SECTION_NAME+"_"+j + "_" + BuilderFormFieldNames.SECTION);
                            setTagAttr(node, TableXMLDAO.ORDER, dateVal);
                        }
                    }
                }
            }
            doc.normalize();
            result = new StreamResult(fileLoc);
            source = new DOMSource(doc);
            trans.transform(source, result);
        } catch(TransformerException e) {
            return false;
        } catch(Exception e) {
            return false;
        } finally {
            file = null;
            doc = null;
            root = null;
            result = null;
            source = null;
        }
        return true;
    }
    // BB-20150203-259 (Section Re-ordering changes) ends
    // BB-20150203-259 (Tab Re-ordering changes) starts
    /**
     * This function is used to reposition the tabs.
     * BB-20150203-259
     * @param request
     * @return
     */
    public boolean addModifyTabOrderData(HttpServletRequest request) {
    	try {
    		String moduleName=getRequestValue(request, "module");
    		List<String> subModuleList=new ArrayList<String>();
    		String subModuleName=getRequestValue(request, "subModuleName");
    		if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName))
    		{
    			if("mu".equals(subModuleName)){
    				subModuleList.add("mu");
    				subModuleList.add("muEntity");
    			}else if("area".equals(subModuleName)){
    				subModuleList.add("area");
    			}else{
    				subModuleList.add("franchiseewithoutsc");
    			}
    		}else if(ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName)){
    			subModuleList.add("lead");
    		}
    		int tabCount = Integer.parseInt(getRequestValue(request, "fimFormSize"));
    		String isCustom="";
    		String fileLoc="";
    		String tabOrder="";
    		String tabName="";
    		String tempVal="";
    		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    		if(StringUtil.isValidNew(moduleName)){
    			for(int j=0;j<tabCount;j++){
    				fileLoc =_baseConstants.XML_DIRECTORY+"tabs/"+moduleName+"Tabs.xml";
    				isCustom=request.getParameter(BuilderFormFieldNames.TAB_DISPLAY_NAME+"_"+j + "_" + BuilderFormFieldNames.IS_CUSTOM);
    				tabOrder=request.getParameter(BuilderFormFieldNames.TAB_DISPLAY_NAME+"_"+j + "_" + "No");
    				tabName=request.getParameter(BuilderFormFieldNames.TAB_DISPLAY_NAME+"_"+j);
    				if(StringUtil.isValidNew(isCustom)&&"Y".equals(isCustom))
    				{
    					fileLoc =_baseConstants.XML_DIRECTORY+"tables/admin/"+BuilderConstants.TAB_MAPPING_XML;
    				}

    				if(StringUtil.isValidNew(fileLoc)) {
    					file = new File(fileLoc);
    					if(file.isFile()) {
    						this.initBDaoInstance();
    						try {
    							doc = docBuilder.parse(file);
    							/**
    							 * Take backup before update xml data
    							 */
    							 String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
    							 resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

    							 source = new DOMSource(doc);
    							 trans.transform(source, resultBackup);
    							 if(StringUtil.isValidNew(isCustom)&&"N".equals(isCustom)){
    								 NodeList hNodeList = doc.getElementsByTagName(BuilderFormFieldNames.TAB_GROUP);
    								 Node headerTablesNode = hNodeList.item(0);
    								 if (headerTablesNode != null) {
    									 Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, BuilderFormFieldNames.TABS);
    									 for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
    										 String xmlSubModule =getAttributeValue(headerNodes[loop], BuilderFormFieldNames.SUB_MODULE);
    										 if(subModuleList.contains(xmlSubModule)){
    											 Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], BuilderFormFieldNames.ROW);
    											 for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
    												 Node[] tabNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TAB);
    												 for (int looptab=0; tabNodes != null && looptab < tabNodes.length; looptab++) {
    													 tempVal=getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME);
    													 if("Entity Detail".equals(tempVal) && "mu".equals(subModuleName)){
    														 tempVal="Multi-Unit Info";
    													 }
    													 if(StringUtil.isValidNew(tempVal) && tempVal.equals(tabName)) {
    														 setTagAttr(tabNodes[looptab], BuilderFormFieldNames.TAB_ORDER, tabOrder);
    														 break;
    													 }
    												 }
    											 }
    										 }
    									 }
    								 }
    							 }
    							 else if(StringUtil.isValidNew(isCustom)&&"Y".equals(isCustom))
    							 {
    								 NodeList nodeList = doc.getElementsByTagName(BuilderFormFieldNames.MODULE_TAB);
    								 int size = nodeList.getLength();
    								 nodeList.hashCode();
    								 for(int i=0;i< size;i++) {
    									 node = nodeList.item(i);
    									 if(StringUtil.isValidNew(tabName) && tabName.equals(getAttributeValue(node, BuilderFormFieldNames.TAB_DISPLAY))) {
    										 setTagAttr(node, BuilderFormFieldNames.TAB_ORDER, tabOrder);

    									 }
    								 }

    							 }


    						} catch(Exception e) {
    							return false;
    						}
    					} else {
    						return false;
    					}
    				}
    				doc.normalize();
    				result = new StreamResult(fileLoc);
    				source = new DOMSource(doc);
    				trans.transform(source, result);
    			}
    		}
    	} catch(TransformerException e) {
    		return false;
    	} catch(Exception e) {
    		return false;
    	} finally {
    		file = null;
    		doc = null;
    		root = null;
    		result = null;
    		source = null;
    	}
    	return true;
    }
    // BB-20150203-259 (Tab Re-ordering changes) ends
    public boolean removeXmlData(HttpServletRequest request) {
    	String fileLocBackup = "";
    	HashMap dataMap = new HashMap();
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
        try {
        	Boolean deleteDocFromTabularSection=false;
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            String val = getRequestValue(request, BuilderFormFieldNames.FIELD_NAME);
            String secName = getRequestValue(request, "secName");
            String tName = getRequestValue(request, BuilderFormFieldNames.TABLE_NAME);
            String fileToWriteLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get("centerInfoDisplay");
            String dropdownOpt = getRequestValue(request, "dropdownOpt");
            String dependentFld = getRequestValue(request, "dependentFld");
            String docName = getRequestValue(request, "docName");
            String archiveTable	= null;//TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS

            if(StringUtil.isValidNew(fileLoc)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
//    					factory = DocumentBuilderFactory.newInstance();
//    					docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS starts
                        if(doc!=null && doc.getFirstChild()!=null)
                        {
                            archiveTable = XMLUtil.getTagText(doc.getFirstChild(),TableXMLDAO.TABLE_ARCHIVE_NAME);
                            if(StringUtil.isValid(archiveTable))
                            {
                                request.setAttribute("archiveTable", archiveTable);
                            }
                        }
                        fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        resultBackup = new StreamResult(fileLocBackup +  "_copy.xml");
                        dataMap.put("newPath", fileLocBackup +  "_copy.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                        
                        file = new File(fileLocBackup +  "_copy.xml");
                        if(file.isFile()) {
                        	factory = null;
                        	docBuilder = null;
                        	this.initBDaoInstance();
                        	doc = docBuilder.parse(file);
                        }
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            root = doc.getDocumentElement();
            if(StringUtil.isValid(docName)) {
                String section = getRequestValue(request, "section");
                String isTabularSection=getRequestValue(request, BuilderFormFieldNames.IS_TABULAR_SECTION);
                Node headerTablesNode = null;
                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                Node[] headerNodes	= getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                    String fieldSection		= XMLUtil.getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                    if(section.equals(fieldSection)) {
                        Node[] documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DOCUMENTS);
                        for (int loopdocs=0; documentsNode != null && loopdocs < documentsNode.length; loopdocs++) {
                            Node[] docFldNodes	=  XMLUtil.getNodesInChildren(documentsNode[loopdocs], TableXMLDAO.DOCUMENT);
                            for (int loopdocsfld=0; docFldNodes != null && loopdocsfld < docFldNodes.length; loopdocsfld++) {
                                String documentName = getAttributeValue( docFldNodes[loopdocsfld], TableXMLDAO.NAME);
                                if(documentName.equals(docName)) {
                                    documentsNode[loopdocs].removeChild(docFldNodes[loopdocsfld]);
                                    break;
                                }
                            }
                        }
                    }
                }
                if("true".equals(isTabularSection)){
                	deleteDocFromTabularSection=true;
                }
            } else if(StringUtil.isValid(secName)) {
            	Boolean isTabularSectionPresent=false;
            	String tabularSectionTableAnchor=null;
                Node headerTablesNode = null;
                NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                Node[] headerNodes	= getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                    String name		= getAttributeValue(headerNodes[loop], TableXMLDAO.NAME);
                    if(secName.equals(name)) {
                    	//P_Enh_FormBuilder_Tabular_Section starts
                    	String isTabularSection=getTagText(headerNodes[loop], TableXMLDAO.IS_TABULAR_SECTION);
                    	 tabularSectionTableAnchor=getTagText(headerNodes[loop], TableXMLDAO.TABULAR_SECTION_TABLE_ANCHOR);
                    	String tabularSectionDbTable=getTagText(headerNodes[loop], TableXMLDAO.TABULAR_SECTION_DB_TABLE);
                    	if("yes".equals(isTabularSection)){
                    		isTabularSectionPresent=true;
                    		if(StringUtil.isValidNew(tabularSectionTableAnchor)){
                                File tabularSectionFile 	= new File(_baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH+"/"+tabularSectionTableAnchor+".xml");
                                if(tabularSectionFile.exists())
                                {
                                	tabularSectionFile.delete();
                                }
                                refreshTabularSectionMappings();
                            }
	                    	if(StringUtil.isValidNew(tabularSectionDbTable)){
	                    		DBQuery deleteQuery	 = new DBQuery();
	                    		deleteQuery.setTableName(tabularSectionDbTable);
	                    		deleteQuery.setDDLType(SQLQueryGenerator.DROP);
	                    		QueryUtil.alterDBTable(SQLQueryGenerator.getDdlQuery(deleteQuery));
	                    	}
                    	}
                      //P_Enh_FormBuilder_Tabular_Section ends
                        headerTablesNode.removeChild(headerNodes[loop]);
                        break;
                    }
                }
                if(isTabularSectionPresent){
            	NodeList foreignList = doc.getElementsByTagName(TableXMLDAO.FOREIGN_TABLES);
				Node foreignTablesNode =  foreignList.item(0);
				Node[] foreignTableNodes	= getNodesInChildren(foreignTablesNode, TableXMLDAO.FOREIGN_TABLE);
                for (int loop=0; foreignTableNodes != null && loop < foreignTableNodes.length; loop++) {
                    String name		= getAttributeValue(foreignTableNodes[loop], TableXMLDAO.NAME);
                    if(tabularSectionTableAnchor.equals(name)) {
                    	foreignTablesNode.removeChild(foreignTableNodes[loop]);
                        break;
                    }
                }
                }
            } else {
                String section = getRequestValue(request, "section");
//    			System.out.println("section -- "+section);
//    			System.out.println("dropdownOpt -- "+dropdownOpt);
//    			System.out.println("dependentFld -- "+dependentFld);
                fieldMappings = getFieldMappings(tName);
//    			String[] arrayNames = fldMao.getMapFieldNamesWithOrderBy(section);
                Field[] flsObj = fieldMappings.getSectionTableAllActiveDeactiveFieldsWithOrderBy(section);

                Info arrLst = new Info();
                boolean flag = false;
                int count = 0;
                for(Field s:flsObj) {
                    if(val.equals(s.getFieldName())) {
                        flag = true;
                    }
                    if(flag) {
                        ++count;
                        if(count ==1) {
//        					arrLst.set(s.getFieldName(), flsObj.length-1);
                        } else {
                            arrLst.set(s.getFieldName(), s.getOrderBy()-1);
                        }
                    } else {
                        arrLst.set(s.getFieldName(), s.getOrderBy());
                    }
                }

                NodeList nodeList = doc.getElementsByTagName(TableXMLDAO.FIELD);
                int size = nodeList.getLength();
                nodeList.hashCode();
//        		System.out.println("field size-- "+size);
                String dependentParentField = ""; //BB-20150203-259 (Dynamic Response based on parent field response)
                String syncWithField = "";
                for(int i=0;i< size;i++) {
                    node = nodeList.item(i);
                    if(node == null) {
                        continue;
                    }
                    String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                    if(val.equals(nameVal)) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
                        dependentParentField = getTagText(node, TableXMLDAO.DEPENDENT_PARENT);
                        syncWithField = getTagText(node, TableXMLDAO.SYNC_WITH);
                        
                        if(StringUtil.isValidNew(dependentParentField)) {
                            dependentParentField = dependentParentField.substring(0, dependentParentField.indexOf("##"));
                        } //BB-20150203-259 (Dynamic Response based on parent field response) ends
                        NodeList tablelist = 	doc.getElementsByTagName(TableXMLDAO.TABLE_NAME);//Martin-20160728-018 starts
                        Node tableNode = tablelist.item(0);
                        String tableNameDB = tableNode.getFirstChild().getNodeValue();
                        removeNodeFromXml(fileToWriteLoc,val,fileLoc,tableNameDB);//Martin-20160728-018 ends
                        root.removeChild(node);
                        break;
                    }
                }

                nodeList.hashCode();
                for(int i=0;i< size;i++) {
                    node = nodeList.item(i);
                    if(node == null) {
                        continue;
                    }
                    String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                    if(arrLst.size() >= 1) {
                        if(!StringUtil.isValidNew(arrLst.get(nameVal))) {
                            continue;
                        }
//        				System.out.println("nameVal changes in node-- "+nameVal);
//        				System.out.println("nameVal changes order in node-- "+arrLst.get(nameVal));
                        setTagText(node, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
                    }
                }
                

                if(StringUtil.isValidNew(syncWithField)) {
                	String[] name = syncWithField.split("##");
                	String parentTableName = name[0];
                	String parentFieldName = name[1];
                	String otherField = name[2];
                	removeExistingTableXml(parentTableName, parentFieldName, dataMap, tableAnchor, otherField, null, null, val);
                }
                
                

                //BB-20150203-259 (Dynamic Response based on parent field response) starts
                for(int i=0;i< size;i++) { //removing for normal fields
                    node = nodeList.item(i);
                    if(node != null) {
                        String nameVal = getTagText(node, TableXMLDAO.FIELD_NAME);
                        if(nameVal.equals(dependentParentField)) {
                            Node dependentNode = getNodeInChildren(node,TableXMLDAO.DEPENDENT);
                            if(dependentNode == null) {
                                continue;
                            } else {
                                Node[] dependentChildNodes	=  XMLUtil.getNodesInChildren(dependentNode, TableXMLDAO.DEPENDENT_CHILD);
                                if(dependentChildNodes.length == 1) {
                                    if(val.equals(getAttributeValue(dependentChildNodes[0], TableXMLDAO.NAME))) {
                                        node.removeChild(dependentNode);
                                        break;
                                    }
                                } else {
                                    for (int looptables=0; dependentChildNodes != null && looptables < dependentChildNodes.length; looptables++) {
                                        if(val.equals(getAttributeValue(dependentChildNodes[looptables], TableXMLDAO.NAME))) {
                                            dependentNode.removeChild(dependentChildNodes[looptables]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //BB-20150203-259 (Dynamic Response based on parent field response) ends
                
                

                NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                Node headerTablesNode = hNodeList.item(0);

                //BB-20150203-259 (Dynamic Response based on parent field response) starts
                if (headerTablesNode != null) { //removing child fields for other table fields.
                    Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                    for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                        for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                            Node dependentNode = getNodeInChildren(dependentTableNodes[looptables], TableXMLDAO.DEPENDENT);
                            if(dependentNode == null) {
                                continue;
                            } else {
                                Node[] dependentChildNodes	=  XMLUtil.getNodesInChildren(dependentNode, TableXMLDAO.DEPENDENT_CHILD);
                                if(dependentChildNodes.length == 1) {
                                    if(val.equals(getAttributeValue(dependentChildNodes[0], TableXMLDAO.NAME))) {
                                        dependentTableNodes[looptables].removeChild(dependentNode);
                                        break;
                                    }
                                } else {
                                    for (int looptablesDependent=0; dependentChildNodes != null && looptablesDependent < dependentChildNodes.length; looptablesDependent++) {
                                        if(val.equals(getAttributeValue(dependentChildNodes[looptablesDependent], TableXMLDAO.NAME))) {
                                            dependentNode.removeChild(dependentChildNodes[looptablesDependent]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
              //ZCUB-20150505-144 -CM_Document Field Changes Start
                if ((val.indexOf("cmDocumentAttachment") !=-1 || val.indexOf("accountDocumentAttachment") !=-1 || val.indexOf("leadDocumentAttachment") !=-1 || val.indexOf("opportunityDocumentAttachment") !=-1) && headerTablesNode != null) { //removing Dependant child fields for other table fields.
                    Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                    val=val.replace("cmDocumentAttachment", "");
                    val=val.replace("leadDocumentAttachment", "");
                    val=val.replace("accountDocumentAttachment", "");
                    val=val.replace("opportunityDocumentAttachment", "");
                    for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                        for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                            if(dependentTableNodes[looptables] == null) {
                                continue;
                            } else {
                                    if(val.equals(getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME))) {
                                    	headerNodes[loop].removeChild(dependentTableNodes[looptables]);
                                        break;
                                    }
                            }
                        }
                    }
                }
              //ZCUB-20150505-144 -CM_Document Field Changes End
                //BB-20150203-259 (Dynamic Response based on parent field response) ends

                if (headerTablesNode != null) {
                    Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                    for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                        Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
                        for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
                            String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
//							String tSectionVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.SECTION);
//			    			System.out.println(section + " , tSectionVal -- "+tSectionVal);
//
//							if(tSectionVal == null || !section.equals(tSectionVal)) {
//								continue;
//							}
                            Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
                            for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                                String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
                                if(arrLst.size() > 1) {
                                    if(!StringUtil.isValidNew(arrLst.get(tFldNameVal))) {
                                        continue;
                                    }
                                    setTagAttr(tablesFldNodes[looptablesfld], TableXMLDAO.ORDER_BY, arrLst.get(tFldNameVal));
                                }
                            }
                        }
                    }
                }
            }
            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();
            //create string from xml tree
//	        sw = new StringWriter();
//	        result = new StreamResult(sw);

            //result = new StreamResult(fileLoc);
            result = new StreamResult(fileLocBackup +  "_copy.xml");

            source = new DOMSource(doc);
            trans.transform(source, result);
//	        String xmlString = sw.toString();
//	        
//			buf = xmlString.getBytes();
//			f0 = new FileOutputStream(fileLoc);
//			for(int i=0;i<buf.length;i++) {
//			   f0.write(buf[i]);
//			}
//			f0.close();
//			buf = null;
            if(deleteDocFromTabularSection){
            	String tabularSectionTableDBName=getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_DB_NAME);
            	String tabularSectionTableName=getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_NAME);
            	loc		= (String)getTableMappings().get(tabularSectionTableName);
            	location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            	if(StringUtil.isValidNew(location)) {
            		request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            	}
            	removeTabularSectionDocument(request);
            }
        } catch(Exception e) {
            return false;
        } /*catch(IOException e) {
			e.printStackTrace();
			return false;
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch(TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch(TransformerException e) {
			e.printStackTrace();
			return false;
		} */finally {
			copyXML(dataMap);
            file = null;
//	    	factory = null;
//	    	docBuilder = null;
            doc = null;
            root = null;

//	    	transfac = null;
//	    	trans.clearParameters();
            result = null;
            source = null;
        }
        return true;
    }

    public String getFimFormBuilderTable(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return tableAnchor;
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;

            it = null;
            tableAnchor = null;
        }
    }

    public SequenceMap getFimFormBuilderFields(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderFormData(tableAnchor, request);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }

    public SequenceMap getFimFormBuilderFieldsMap(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderFormDataMap(tableAnchor, request);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }

    public SequenceMap getFimFormBuilderFieldsAllMap(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderFormDataAllMap(tableAnchor, request);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }

    public List getConfiguredTab(String subModuleName)
    {
    	List list = new ArrayList();
    	String query = "";
    	ResultSet rs = null;
    	try{
    		if("mu".equals(subModuleName))
    		{
    			query = "SELECT NAME FROM USER_TAB_CONFIG WHERE MODULE = ? AND SUB_MODULE IN ('mu','muEntity') GROUP BY NAME HAVING COUNT(NAME) > 0";
    			rs = QueryUtil.getResult(query, new String[]{"fim"});
    			while(rs!=null && rs.next())
    			{
    				list.add(rs.getString("NAME"));
    			}
    		}else
    		{
    			if(!StringUtil.isValid(subModuleName))
    			{
    				subModuleName = "franchiseewithoutsc";
    			}
    			query = "SELECT NAME FROM USER_TAB_CONFIG WHERE MODULE = ? AND SUB_MODULE = ?";
    			rs = QueryUtil.getResult(query, new String[]{"fim", subModuleName});
    			while(rs!=null && rs.next())
    			{
    				list.add(rs.getString("NAME"));
    			}

    		}
    	}catch (Exception e) {
    	}
		return list;
    }
    
    //JIS-20160815-009 : START
    public List getConfiguredTabByInternalNames(String subModuleName)
    {
    	List list = new ArrayList();
    	String query = "";
    	ResultSet rs = null;
    	try{
    		if("mu".equals(subModuleName))
    		{
    			query = "SELECT INTERNAL_NAME FROM USER_TAB_CONFIG WHERE MODULE = ? AND SUB_MODULE IN ('mu','muEntity') GROUP BY INTERNAL_NAME HAVING COUNT(INTERNAL_NAME) > 0";
    			rs = QueryUtil.getResult(query, new String[]{"fim"});
    			while(rs!=null && rs.next())
    			{
    				list.add(rs.getString("INTERNAL_NAME"));
    			}
    		}else
    		{
    			if(!StringUtil.isValid(subModuleName))
    			{
    				subModuleName = "franchiseewithoutsc";
    			}
    			query = "SELECT INTERNAL_NAME FROM USER_TAB_CONFIG WHERE MODULE = ? AND SUB_MODULE = ?";
    			rs = QueryUtil.getResult(query, new String[]{"fim", subModuleName});
    			while(rs!=null && rs.next())
    			{
    				list.add(rs.getString("INTERNAL_NAME"));
    			}

    		}
    	}catch (Exception e) {
    	}
		return list;
    }
    //JIS-20160815-009 : END
    
	  public String renameKeywordName(String name) {
		  name = BaseUtils.replaceAll(name, "'","_");
		  name = BaseUtils.replaceAll(name, "#","_");
		  name = BaseUtils.replaceAll(name,"?","_");
		  name = BaseUtils.replaceAll(name,"$","_");
		  name = BaseUtils.replaceAll(name,"&","_");
		  name = BaseUtils.replaceAll(name,"\"","_");
		  name = BaseUtils.replaceAll(name,"%","_");
		  name = BaseUtils.replaceAll(name,":","_");
		  name = BaseUtils.replaceAll(name,",","_");
		  name = BaseUtils.replaceAll(name,";","_");
		  name = BaseUtils.replaceAll(name,"/","_");
		  name = BaseUtils.replaceAll(name,"\\","_");
		  name = BaseUtils.replaceAll(name,"+","_");
		  name = BaseUtils.replaceAll(name,"~","_");
		  name = BaseUtils.replaceAll(name,"`","_");
		  name = BaseUtils.replaceAll(name,"=","_");
		  name = BaseUtils.replaceAll(name,"[", "_");
		  name = BaseUtils.replaceAll(name,"(", "_");
		  name = BaseUtils.replaceAll(name,")", "_");
		  name = BaseUtils.replaceAll(name,"]", "_");
		  name = BaseUtils.replaceAll(name," ","_");
		  name = BaseUtils.replaceAll(name,"__", "_");
		  name = BaseUtils.replaceAll(name,"__", "_");
		  name = BaseUtils.replaceAll(name,"__", "_");
		  return name;
	  }
	  
	  /**
	   * P_Enh_Multiple_Input_Tabular_Summary
	   * @param request
	   * @param tableAnchor
	   * @return
	   * @throws SQLException
	   * @throws RecordNotFoundException
	   */
	  public Map getTabularSummaryFieldsMap(HttpServletRequest request, String tableAnchor) throws SQLException, RecordNotFoundException 
	  {
		  Iterator it = null;
		  String key = null;
		  Object ob = null;
		  Map tabularFieldsMap = new LinkedHashMap();
		  UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	      String user_level=(String)request.getSession().getAttribute("user_level");
	      
		  try 
		  {
			  if(!StringUtil.isValid(tableAnchor))
			  {
				  return null;
			  }
			  FieldMappings fieldMappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
			  HeaderMap[] hMap = fieldMappings.getHeaderMap();
			  for(HeaderMap h:hMap) {
                  HeaderField hFld = h.getHeaderFields();
                  SequenceMap attrMap = hFld.getHeaderAttrMap();
                  Field[] flds = fieldMappings.getSectionTablesFieldsArray(hFld);
                  if(attrMap.get("dependentModule") != null) {
                	  String dependentModule = (String)attrMap.get("dependentModule");
                	  String required = (String)attrMap.get("required");
                	  String displayOnly = (String)attrMap.get("displayOnly");
                	  if(StringUtil.isValidNew(required) && "true".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)) {
                		  continue;
                	  } else if(StringUtil.isValidNew(required) && "false".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) ) {
                		  continue;
                	  }
                  }
                  if(StringUtil.isValid((String)hFld.getHeaderAttrMap().get("name")) && ((String)hFld.getHeaderAttrMap().get("name")).indexOf("sms")!=-1) {
                	  continue;
                  }
                  if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
                	  continue;
                  }
                  if("userLevelMessage".equals(hFld.getHeaderAttrMap().get("name"))&& ("off".equalsIgnoreCase(MultiTenancyUtil.getTenantConstants().BOEFLY_INTEGRATION_STATUS)) ){//Bug 59836
                	  continue;
                  }
                  
                  if(flds != null){
                      for(Field field : flds) {
                          if(field == null) {
                              continue;
                          }
                          if(!field.isActiveField())
                          {
                        	  continue;
                          }
                          String fldName = field.getFieldName();
                          if("fbc".equals(fldName) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){   
  							continue;
                          }
                          tabularFieldsMap.put(fldName, field.getDisplayName());
                      }
                  }
			  }
			  return tabularFieldsMap;
		  } catch(Exception e) {
			  return null;
		  }
	  }
	  
	  public void insertTabularFields(HttpServletRequest request, String tableAnchor)
	  {
		  String query = "INSERT INTO CONFIGURE_TABULAR_FIELDS(TABLE_ANCHOR,FIELDS,ORDER_NO)";

		  String[] queryArray = {"INSERT INTO CONFIGURE_TABULAR_FIELDS(TABLE_ANCHOR,FIELDS,ORDER_NO,IS_ACTIVE) VALUES (?,?,?,?)"};
		  List<String[]> paramList = null;
		  String[] paramArray = null;
		  Map<String,List<String[]>> batchMap = new HashMap();

		  for(int i=1; i<=Integer.parseInt(request.getParameter("fieldsSize")); i++)
		  {
			  paramArray = new String[]{tableAnchor,request.getParameter("selectedField_"+i+"_FieldName"),request.getParameter("selectedField_"+i+"_OrderNo"),"Y"};
			  if(batchMap.containsKey(FieldNames.ZERO)) {
				  (batchMap.get(FieldNames.ZERO)).add(paramArray);
			  } else {
				  paramList = new ArrayList<String[]>();
				  paramList.add(paramArray);
				  batchMap.put(FieldNames.ZERO, paramList);
			  }
		  }        

		  try {
			  QueryUtil.preparedStatementsBatchUpdate(queryArray, batchMap);
		  } catch(Exception e) {
		  } finally {
			  queryArray = null;
			  paramArray = null;
			  paramList = null;
			  batchMap = null;
		  }
	  }
	  
    //BB-20150203-259 Starts (To make available all the fields of Form builder as keyword.)
    public String InsertkeywordField(String fieldName,String moduleId, String tableAnchor, String isOtherTableField, String formId,String fieldFormName)
    {
        int result = 0;
        String otherTableAnchor = "";
        String otherDBTableName = "";
        String displayName = "";
        String displayValue = "";
        String dbTableName = "";
        StringBuffer relationBetweenTables = new StringBuffer();
        String sectionID = "";
        String dbColumnName = "";
        String isActive = "N";
        FieldMappings mapping = DBUtil.getInstance().getFieldMappings(tableAnchor);
        dbTableName = mapping.getTableName();
        Field field = mapping.getField(fieldName); //if false
        if("true".equals(isOtherTableField)) {
            field = mapping.getOtherTableField(fieldName); //if true
        }
        if(field.isActiveField() == true)
        {
            isActive = "Y";
        }
        dbColumnName = field.getDbField();
        sectionID = field.getSectionField();
        displayName = field.getDisplayName();
        displayValue = field.getDisplayName();
        displayValue = renameKeywordName(displayValue);
        displayValue = "$"+displayValue.trim().replaceAll("'", "").toUpperCase()+"_"+formId+"_"+sectionID+"$";
        otherTableAnchor = field.getTableAnchor();
        if(otherTableAnchor != null)
        {
            ForeignTable foreignTable = mapping.getForeignTable(otherTableAnchor);
            FieldMappings foreignMapping = DBUtil.getInstance().getFieldMappings(otherTableAnchor);
            otherDBTableName = foreignMapping.getTableName();
            SequenceMap linkFieldMap = foreignTable.getLinkFieldsMap();
            Iterator it			= linkFieldMap.keys().iterator();
            while(it.hasNext())
            {
                String mainField		= (String)it.next();
                LinkField linkField 	= (LinkField)linkFieldMap.get(mainField);
                String thisField = linkField.getThisField();
                String foreignField = linkField.getForeignField();
                Field fld = mapping.getField(thisField); //this field
                Field foreignFld = foreignMapping.getField(foreignField);
                relationBetweenTables = relationBetweenTables.append(dbTableName).append(".").append(fld.getDbField()).append(" = ").append(otherDBTableName).append(".").append(foreignFld.getDbField()); //relation
                if(it.hasNext())
                {
                    relationBetweenTables = relationBetweenTables.append(" AND ");
                }
            }
        }
        //StringBuffer query = new StringBuffer("INSERT INTO FORM_BUILDER_KEYWORDS_CONFIGURATION (FORM_ID,SECTION_ID,TABLE_ANCHOR,FORM_NAME,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,DB_MAIN_TABLE,DB_CHILD_TABLE,MAIN_CHILD_TABLE_MAPPING,FIELD_NAME,DB_FIELD_NAME,IS_ACTIVE) VALUES(");
        //query = query.append("'"+formId+"','"+sectionID+"','"+tableAnchor+"','"+fieldFormName+"','"+displayName+"','"+displayValue+"','"+moduleId+"','"+dbTableName+"','"+otherDBTableName+"','"+relationBetweenTables.toString()+"','"+fieldName+"','"+dbColumnName+"','"+isActive+"')");
        // System.out.println("\n\n>>>>>>Insert query>>>>>"+query);

        String query = "INSERT INTO FORM_BUILDER_KEYWORDS_CONFIGURATION (FORM_ID,SECTION_ID,TABLE_ANCHOR,FORM_NAME,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,DB_MAIN_TABLE,DB_CHILD_TABLE,MAIN_CHILD_TABLE_MAPPING,FIELD_NAME,DB_FIELD_NAME,IS_ACTIVE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String params[] = new String[]{formId,sectionID,tableAnchor,fieldFormName,displayName,displayValue,moduleId,dbTableName,otherDBTableName,relationBetweenTables.toString(),fieldName,dbColumnName,isActive};

        try
        {
            //result = QueryUtil.update(query.toString(), new String[] {});
            result = QueryUtil.update(query.toString(), params);
        }
        catch(Exception e) {
        }
        if(result == 1)
            return "true";
        else
            return "false";
    }

    public HashMap<Integer,String> getKeywordsFieldNames(String formId) throws SQLException
    {
        HashMap<Integer,String> fieldMap =  new HashMap<Integer,String>();
        ResultSet result =null;
        int i=0;
        String query = "SELECT FIELD_NAME FROM FORM_BUILDER_KEYWORDS_CONFIGURATION WHERE FORM_ID=?";
        try
        {
            result = QueryUtil.getResult(query, new String[] {formId});
            while (result.next()) {
                fieldMap.put(i,result.getString("FIELD_NAME"));
                i++;
            }
        }
        catch (Exception e) {
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }
        return fieldMap;
    }
   
    public HashMap<Integer,String> getDeactiveKeywordsFieldNames(String formId) throws SQLException
    {
        HashMap<Integer,String> fieldMap =  new HashMap<Integer,String>();
        ResultSet result =null;
        int i=0;
        String query = "SELECT FIELD_NAME FROM FORM_BUILDER_KEYWORDS_CONFIGURATION WHERE FORM_ID=? AND IS_DISABLE='Y'";
        try
        {
            result = QueryUtil.getResult(query, new String[] {formId});
            while (result.next()) {
                fieldMap.put(i,result.getString("FIELD_NAME"));
                i++;
            }
        }
        catch (Exception e) {
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }
        return fieldMap;
    }
    


    public String deleteKeywordsField(String fieldName, String formId) throws SQLException
    {
        String query = "DELETE FROM FORM_BUILDER_KEYWORDS_CONFIGURATION WHERE FIELD_NAME=? AND FORM_ID=?";
        String params[] = new String[]{fieldName,formId};
        int	result = -1;
        try{
            result = QueryUtil.update(query,params);
        } catch(Exception e)
        {
        }
        if(result == 1)
            return "true";
        else
            return "false";
    }
    public Info getBuilderSectionCount(HttpServletRequest request) throws SQLException, RecordNotFoundException {
    	SequenceMap baseBuilder = null;
    	Iterator it = null;
    	tableAnchor = null;
    	String key = null;
    	Object ob = null;
    	try {
    		baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());
    		it = baseBuilder.keys().iterator();
    		if(it.hasNext()) {
    			key = (String)it.next();
    			ob = (Object)baseBuilder.get(key);
    			if(ob instanceof Info) {
    				tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
    			}
    		}
    		if(!StringUtil.isValid(tableAnchor)) {
    			return null;
    		}
    		return getBuilderRegularAndTabularSectionCount(tableAnchor);
    	} catch(Exception e) {
    		return null;
    	} finally {
    		key = null;
    		ob = null;
    		baseBuilder = null;
    		it = null;
    		tableAnchor = null;
    	}
    }
    /**
     *This method returns Regular Section and Tabular section count
     * @author Akash Kumar
     * @param tableAnchor
     * @return
     */
    public Info getBuilderRegularAndTabularSectionCount(String tableAnchor) {

    	Info info=new Info();
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	int regularSectionCount=0;
    	int tabularSectionCount=0;
    	try{
    		if(StringUtil.isValidNew(tableAnchor)){
    		fieldMappings = getFieldMappings(tableAnchor);
    		HeaderMap[] hMap = fieldMappings.getHeaderMap();
    		for(HeaderMap h:hMap) {
    			HeaderField hFld = h.getHeaderFields();
    			if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
    				continue;
    			}
    			if("userLevelMessage".equals(hFld.getHeaderAttrMap().get("name"))&& ("off".equalsIgnoreCase(_baseConstants.BOEFLY_INTEGRATION_STATUS)) ){
    				continue;
    			}
    			if(hFld.isTabularSection()){
    				tabularSectionCount++;
    			}else{
    				regularSectionCount++;
    			}
    		}
    		}
    		info.set("regularSectionCount", regularSectionCount);
    		info.set("tabularSectionCount", tabularSectionCount);
    		return info;
    	}catch(Exception e){
    		return null;
    	} finally {
    		//field = null;
    		fieldMappings = null;
    		info = null;
    	}


    }
    public SequenceMap getFimFormBuilderFieldsAllTablesMap(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderFormDataAllTablesMap(tableAnchor, request);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }
    public SequenceMap getFimFormBuilderSectionMap(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        String tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderSectionDataMap(tableAnchor, request);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }

    public FieldMappings getFimFormBuilderSectionMapping(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        SequenceMap baseBuilder = null;
        Iterator it = null;
        String tableAnchor = null;
        String key = null;
        Object ob = null;
        try {
//			if(baseFormFactory == null) {
//				context = request.getSession().getServletContext();
//				super.init();
//			}
            baseBuilder = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());//baseFormFactory.getBuilderTableData(request, context);
            it = baseBuilder.keys().iterator();
            if(it.hasNext()) {
                key = (String)it.next();
                ob = (Object)baseBuilder.get(key);
                if(ob instanceof Info) {
                    tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                }
            }
            if(!StringUtil.isValid(tableAnchor)) {
                return null;
            }
            return getBuilderSectionMapping(tableAnchor);
        } catch(Exception e) {
            return null;
        } finally {
            key = null;
            ob = null;
            baseBuilder = null;
            it = null;
            tableAnchor = null;
        }
    }

    private SequenceMap getBuilderFormData(String tableAnchor, HttpServletRequest request) {
        SequenceMap map = new SequenceMap();
        FieldMappings mappings = null;
        Field field = null;
        Info info = null;
        Vector vc = new Vector(0);
        boolean isSkip = false;
        boolean isOrder = false;
        try{
            if(request.getParameter(BuilderFormFieldNames.IS_FIM_TABLE) != null || request.getAttribute(BuilderFormFieldNames.IS_FIM_TABLE) != null) {
                mappings = getFieldMappings(tableAnchor);
                String sDBTableName = mappings.getTableName();
                String[] sIdName = mappings.getIdFieldNames();
                for(int i = 0; i < sIdName.length; i++) {
                    vc.add(sIdName[i]);
                }

                String[] sAllIDFields = mappings.getAllFieldNames();
                if (sAllIDFields != null) {
                    for (int n = 0; n < sAllIDFields.length; n++) {
                        field = mappings.getField(sAllIDFields[n]);
                        String fldName = field.getFieldName();
                        isSkip = checkForSkipValue(vc, sDBTableName, fldName);
                        if(isSkip)
                            continue;

                        if(!isOrder && field.getOrderBy() != -1) {
                            isOrder = true;
                        }
                        if(!isOrder) {
                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, n+1);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            map.put(fldName, info);
                        } else {

                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, field.getOrderBy()+1);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            map.put(fldName, info);
                        }
                    }

//					System.out.println("isOrder  after -- " + isOrder);

                    if(isOrder)
                        map = map.sortOnObjectKey(BuilderFormFieldNames.ORDER_NO, true);

//					System.out.println("map  after ordering -- " + map);
                }
                /**
                 * remove table data from JVM
                 */
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);
            }
            return map;
        }catch(Exception e){
            return null;
        } finally {
            vc = null;
            map = null;
            field = null;
            mappings = null;
            info = null;
        }
    }

    private SequenceMap getBuilderFormDataMap(String tableAnchor, HttpServletRequest request) {
        SequenceMap map = new SequenceMap();
//		FieldMappings mappings = null;
//		Field field = null;
        Info info = null;
        Vector vc = new Vector(0);
        boolean isSkip = false;
        boolean isOrder = false;
        try{
            if(request.getParameter(BuilderFormFieldNames.IS_FIM_TABLE) != null || request.getAttribute(BuilderFormFieldNames.IS_FIM_TABLE) != null) {
                fieldMappings = getFieldMappings(tableAnchor);
                String section = request.getParameter("section");
                String sDBTableName = fieldMappings.getTableName();
                String[] sIdName = fieldMappings.getIdFieldNames();
                HeaderMap[] hMap = fieldMappings.getHeaderMap();
                int n =0;

                for(HeaderMap h:hMap) {
                    HeaderField hFld = h.getHeaderFields();
                    if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
                        continue;
                    }
                    String[] strArr = fieldMappings.getMapFieldNamesWithOrderBy(hFld.getSectionField());
                    Field[] flds = fieldMappings.getSectionTableFieldsWithOrderBy(hFld.getSectionField());
                    if(StringUtil.isValidNew(section) && !hFld.getSectionField().equals(section)) {
                        continue;
                    }
                    if(flds != null) {
                        SequenceMap mapArr = new SequenceMap();
                        mapArr.put("mapName", h.getName());
                        mapArr.put("mapValue", h.getValue());
                        mapArr.put("mapOrder", h.getOrder());
                        mapArr.put("mapSection", hFld.getSectionField());
                        mapArr.put("tableAnchor", tableAnchor);

                        for(Field field : flds) {
                        	if(field == null) {
                        		continue;
                        	}
                            String fldName = field.getFieldName();

                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, field.getOrderBy()+1);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            info.set(BuilderFormFieldNames.SECTION, field.getSectionField());
                            info.set(BuilderFormFieldNames.DISPLAY_TYPE, field.getDisplayTypeField());
                            info.set(BuilderFormFieldNames.OTHER_TABLE_NAME, field.getTableName());
                            info.set(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD, field.isFieldOfOtherTable());

                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            mapArr.put(fldName, info);
                        }
                        map.put(n++, mapArr);
                    }else{//Martin-20160728-018 starts
                    	request.setAttribute("mapSection",hFld.getSectionField());
                    	request.setAttribute("tableAnchor", tableAnchor);
                    }//Martin-20160728-018 ends
                }
                /**
                 * remove table data from JVM
                 */
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);
            }
            return map;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } finally {
            vc = null;
            map = null;
//			field = null;
            fieldMappings = null;
            info = null;
        }
    }

    private SequenceMap getBuilderFormDataAllMap(String tableAnchor, HttpServletRequest request) {
        SequenceMap map = new SequenceMap();
//		FieldMappings mappings = null;
        Field field = null;
        Info info = null;
        Vector vc = new Vector(0);
        boolean isSkip = false;
        boolean isOrder = false;
        try{
            if(request.getParameter(BuilderFormFieldNames.IS_FIM_TABLE) != null || request.getAttribute(BuilderFormFieldNames.IS_FIM_TABLE) != null) {
                fieldMappings = getFieldMappings(tableAnchor);
                String sDBTableName = fieldMappings.getTableName();
                String[] sIdName = fieldMappings.getIdFieldNames();
                HeaderMap[] hMap = fieldMappings.getHeaderMap();
                int n =0;
                for(HeaderMap h:hMap) {
                    HeaderField hFld = h.getHeaderFields();
                    String[] strArr = fieldMappings.getAllMapFieldNamesWithOrderBy(hFld.getSectionField());

                    //if(strArr != null) {
                    SequenceMap mapArr = new SequenceMap();
                    mapArr.put("mapName", h.getName());
                    mapArr.put("mapValue", h.getValue());
                    mapArr.put("mapOrder", h.getOrder());
                    mapArr.put("mapSection", hFld.getSectionField());
                    mapArr.put("mapTableName", tableAnchor);
                    mapArr.put("mapTableDBName", sDBTableName);

                    int i = 0;
                    if(strArr != null)
                        for(String s : strArr) {
                            field = fieldMappings.getField(s);
                            String fldName = field.getFieldName();
                            //isSkip = checkForSkipValue(vc, sDBTableName, fldName);
                            //if(isSkip)
                            //continue;

                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, ++i);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            info.set(BuilderFormFieldNames.SECTION, field.getSectionField());
                            info.set(BuilderFormFieldNames.DISPLAY_TYPE, field.getDisplayTypeField());
                            info.set(BuilderFormFieldNames.NO_OF_COLUMN, field.getNoOfColsField());
                            info.set(BuilderFormFieldNames.NO_OF_ROW, field.getNoOfRowsField());
                            info.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, field.getDBFieldLength());
                            info.set(BuilderFormFieldNames.MAIL_MERGE_ACTIVE, field.isMailMergeActive());
                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            mapArr.put(fldName, info);
                        }
                    map.put(n++, mapArr);
                    //}
                }
                /**
                 * remove table data from JVM
                 */
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);
            }
            return map;
        }catch(Exception e){
            return null;
        } finally {
            vc = null;
            map = null;
            field = null;
            fieldMappings = null;
            info = null;
        }
    }

    private SequenceMap getBuilderFormDataAllTablesMap(String tableAnchor, HttpServletRequest request) {
        SequenceMap map = new SequenceMap();
        BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();//Bug 59836
        UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
        String user_level=(String)request.getSession().getAttribute("user_level");
//		FieldMappings mappings = null;
        FieldMappings     fieldTabularSectionMappings=null;
        //Field field = null;
        Info info = null;
        Vector vc = new Vector(0);
        boolean isSkip = false;
        boolean isOrder = false;
        try{
            if(isSkip && request.getParameter(BuilderFormFieldNames.IS_FIM_TABLE) != null || request.getAttribute(BuilderFormFieldNames.IS_FIM_TABLE) != null) {
                fieldMappings = getFieldMappings(tableAnchor);
                String sDBTableName = fieldMappings.getTableName();
                String[] sIdName = fieldMappings.getIdFieldNames();
                HeaderMap[] hMap = fieldMappings.getHeaderMap();
                int n =0;
                for(HeaderMap h:hMap) {
                    HeaderField hFld = h.getHeaderFields();
                    //String[] strArr = mappings.getAllMapFieldNamesWithOrderBy(hFld.getSectionField());
                    SequenceMap attrMap = hFld.getHeaderAttrMap();
                    if(attrMap.get("dependentModule") != null) {
                        String dependentModule = (String)attrMap.get("dependentModule");
                        String required = (String)attrMap.get("required");
                        String displayOnly = (String)attrMap.get("displayOnly");
                        if(StringUtil.isValidNew(required) && "true".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)) {
                            continue;
                        } else if(StringUtil.isValidNew(required) && "false".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) ) {
                            continue;
                        }else if(StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)){
                        	 continue;
                        }
                    }
                    if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
                        continue;
                    }
                    if("userLevelMessage".equals(hFld.getHeaderAttrMap().get("name"))&& ("off".equalsIgnoreCase(_baseConstants.BOEFLY_INTEGRATION_STATUS)) ){//Bug 59836
                    	continue;
                    }

                    Field[] flds = fieldMappings.getSectionTablesFieldsArray(hFld);
                    //if(strArr != null) {
                    SequenceMap mapArr = new SequenceMap();
                    mapArr.put("mapName", h.getName());
                    mapArr.put("isOptional", h.isOptional());	//P_E_MoveToFim_AddlFDD
                    mapArr.put("mapValue", h.getValue());
                    mapArr.put("mapOrder", h.getOrder());
                    mapArr.put("mapSection", hFld.getSectionField());
                    mapArr.put("mapTableName", tableAnchor);
                    mapArr.put("mapTableDBName", sDBTableName);
                    mapArr.put("isTabularSection", hFld.isTabularSection());//P_Enh_FormBuilder_Tabular_Section starts
                    if(hFld.isTabularSection()){
                    	 mapArr.put("tabularSectionTableName", hFld.getTabularSectionTableAnchor());
                         mapArr.put("tabularSectionTableDBName", hFld.getTabularSectionDbTable());
                         mapArr.put("mapTableName", hFld.getTabularSectionTableAnchor());
                         mapArr.put("mapTableDBName", hFld.getTabularSectionDbTable());
                         mapArr.put("parentTableName", tableAnchor);
                         fieldTabularSectionMappings = getFieldMappings(hFld.getTabularSectionTableAnchor());
                         HeaderMap[] thMap = fieldTabularSectionMappings.getHeaderMap();//P_CM_B_77631
                         if(thMap!=null && thMap.length>0){
                        	 flds = fieldTabularSectionMappings.getSectionTablesFieldsArray(thMap[0].getHeaderFields());
                         }else{
                        	 flds = fieldTabularSectionMappings.getSectionTablesFieldsArray(hFld);
                         }
                    }//P_Enh_FormBuilder_Tabular_Section ends
                    if(hFld.getDocuments() != null && hFld.getDocuments().length > 0)
                        mapArr.put("mapDocs", hFld.getDocuments()[0].getDocumentMaps());

                    int i = 0;
                    int ii = 0;
                    if(flds != null)
                        for(Field field : flds) {
                            //field = mappings.getField(s);

                            if(field == null) {
                                continue;
                            }
                            if(field.isActiveField()) {
                                ++ii;
                            }
                            String fldName = field.getFieldName();
                            if("fbc".equals(fldName) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){   //ENH_PW_FBC
    							continue;
    						}
                            //isSkip = checkForSkipValue(vc, sDBTableName, fldName);
                            //if(isSkip)
                            //continue;

                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, ++i);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            info.set(BuilderFormFieldNames.SECTION, field.getSectionField());
                            info.set(BuilderFormFieldNames.DISPLAY_TYPE, field.getDisplayTypeField());
                            info.set(BuilderFormFieldNames.IS_MULTISELECT, field.getisMultiSelect());//BB-20150203-259 (MultiSelect combo in add new field changes) 
                            info.set(BuilderFormFieldNames.NO_OF_COLUMN, field.getNoOfColsField());
                            info.set(BuilderFormFieldNames.NO_OF_ROW, field.getNoOfRowsField());
                            info.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, field.getDBFieldLength());
                            info.set(BuilderFormFieldNames.MAIL_MERGE_ACTIVE, field.isMailMergeActive());
                            info.set(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD, field.isFieldOfOtherTable());
                            info.set(BuilderFormFieldNames.OTHER_TABLE_NAME, field.getTableName());
                            info.set(BuilderFormFieldNames.IS_PII_ENABLED, field.isPiiEnabled());
                            info.set(BuilderFormFieldNames.IS_CENTERINFO_FIELD, field.isCenterInfoField());
                            info.set(BuilderFormFieldNames.DROPDOWN_OPTION, field.getFieldDropdownOpt());
                            info.set(BuilderFormFieldNames.IS_PARENT, field.isParent());
                            info.set(BuilderFormFieldNames.DEPENDENT_FLD, field.getDependentField());
                            info.set(BuilderFormFieldNames.IS_SOURCE_FIELD, field.isSourceField());
                            info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, field.getValidationType());
                            info.set(BuilderFormFieldNames.IS_DEFAULT_PII, field.isDefaultPii());
                            info.set("syncWith", field.getSyncWithField());
                            info.set(BuilderFormFieldNames.IS_PARSING_KEYWORD_CONFIGURED,field.getIsParsingKeywordConfigured());
                            info.set(BuilderFormFieldNames.SYNC_DATA,field.getSyncTotalMap().size()>0?"yes":"no");                            
                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            mapArr.put(fldName, info);
                        }
                    mapArr.put("mapActiveFldSize", ""+ii);

                    map.put(n++, mapArr);
                    //}
                }
                /**
                 * remove table data from JVM
                 */
                if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
                    removeFieldMappings(TableAnchors.FRANCHISEE);
                }
                if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
                    removeFieldMappings(TableAnchors.FRANCHISEES);
                }
                removeFieldMappings(tableAnchor);
            } else {

                fieldMappings = getFieldMappings(tableAnchor);
                String sDBTableName = fieldMappings.getTableName();
                String[] sIdName = fieldMappings.getIdFieldNames();
                HeaderMap[] hMap = fieldMappings.getHeaderMap();
                int n =0;
                for(HeaderMap h:hMap) {
                    HeaderField hFld = h.getHeaderFields();
                    //String[] strArr = mappings.getAllMapFieldNamesWithOrderBy(hFld.getSectionField());
                    SequenceMap attrMap = hFld.getHeaderAttrMap();
                    if(attrMap.get("dependentModule") != null) {
                        String dependentModule = (String)attrMap.get("dependentModule");
                        String required = (String)attrMap.get("required");
                        String displayOnly = (String)attrMap.get("displayOnly");
                        if(StringUtil.isValidNew(required) && "true".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)) {
                            continue;
                        } else if(StringUtil.isValidNew(required) && "false".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) ) {
                            continue;
                        }
                    }
                    Field[] flds = fieldMappings.getSectionTablesFieldsArray(hFld);
                    //if(strArr != null) {
                    SequenceMap mapArr = new SequenceMap();
                    mapArr.put("mapName", h.getName());
                    mapArr.put("isOptional", h.isOptional());	//P_E_MoveToFim_AddlFDD
                    mapArr.put("mapValue", h.getValue());
                    mapArr.put("mapOrder", h.getOrder());
                    mapArr.put("mapSection", hFld.getSectionField());
                    mapArr.put("mapTableName", tableAnchor);
                    mapArr.put("mapTableDBName", sDBTableName);
                    mapArr.put("isTabularSection", hFld.isTabularSection());//P_Enh_FormBuilder_Tabular_Section starts
                    if(hFld.isTabularSection()){
                   	 mapArr.put("tabularSectionTableName", hFld.getTabularSectionTableAnchor());
                     mapArr.put("tabularSectionTableDBName", hFld.getTabularSectionDbTable());
                     mapArr.put("mapTableName", hFld.getTabularSectionTableAnchor());
                     mapArr.put("mapTableDBName", hFld.getTabularSectionDbTable());
                     mapArr.put("parentTableName", tableAnchor);
                     fieldTabularSectionMappings = getFieldMappings(hFld.getTabularSectionTableAnchor());
                     flds = fieldTabularSectionMappings.getSectionTablesFieldsArray(hFld);
                   }//P_Enh_FormBuilder_Tabular_Section ends
                    if(hFld.getDocuments() != null && hFld.getDocuments().length > 0)
                        mapArr.put("mapDocs", hFld.getDocuments()[0].getDocumentMaps());

                    int i = 0;
                    int ii = 0;
                    if(flds != null)
                        for(Field field : flds) {
                            //field = mappings.getField(s);

                            if(field == null) {
                                continue;
                            }
                            if(field.isActiveField()) {
                                ++ii;
                            }
                            String fldName = field.getFieldName();
                            //isSkip = checkForSkipValue(vc, sDBTableName, fldName);
                            //if(isSkip)
                            //continue;

                            info = new Info();
                            info.set(BuilderFormFieldNames.FIELD_ID, ++i);
                            info.set(BuilderFormFieldNames.BUILDER_FORM_ID, 1);
                            info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
                            info.set(BuilderFormFieldNames.DISPLAY_NAME, field.getDisplayName());
                            info.set(BuilderFormFieldNames.DATA_TYPE, field.getDataType());
                            info.set(BuilderFormFieldNames.IS_ACTIVE, field.isActiveField());
                            info.set(BuilderFormFieldNames.ORDER_NO, field.getOrderBy());
                            info.set(BuilderFormFieldNames.IS_BUILD_FIELD, field.isBuildField());
                            info.set(BuilderFormFieldNames.IS_MANDATORY, field.isMandatoryField());
                            info.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                            info.set(BuilderFormFieldNames.DB_FIELD, field.getDbField());
                            info.set(BuilderFormFieldNames.COL_SPAN, field.getColspan());
                            info.set(BuilderFormFieldNames.SECTION, field.getSectionField());
                            info.set(BuilderFormFieldNames.DISPLAY_TYPE, field.getDisplayTypeField());
                            info.set(BuilderFormFieldNames.NO_OF_COLUMN, field.getNoOfColsField());
                            info.set(BuilderFormFieldNames.NO_OF_ROW, field.getNoOfRowsField());
                            info.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, field.getDBFieldLength());
                            info.set(BuilderFormFieldNames.MAIL_MERGE_ACTIVE, field.isMailMergeActive());
                            info.set(BuilderFormFieldNames.IS_OTHER_TABLE_FIELD, field.isFieldOfOtherTable());
                            info.set(BuilderFormFieldNames.OTHER_TABLE_NAME, field.getTableName());
                            info.set(BuilderFormFieldNames.IS_PII_ENABLED, field.isPiiEnabled());
                            info.set(BuilderFormFieldNames.DROPDOWN_OPTION, field.getFieldDropdownOpt());
                            info.set(BuilderFormFieldNames.IS_PARENT, field.isParent());
                            info.set(BuilderFormFieldNames.DEPENDENT_FLD, field.getDependentField());
                            info.set(BuilderFormFieldNames.IS_SOURCE_FIELD, field.isSourceField());
                            info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, field.getValidationType());
                            info.set("syncWith", field.getSyncWithField()); //P_Enh_Sync_Fields
                            //info.set(BuilderFormFieldNames.EXPORTABLE, "yes");
                            //info.set(BuilderFormFieldNames.SEARCHABLE, "yes");
                            mapArr.put(fldName, info);
                        }
                    mapArr.put("mapActiveFldSize", ""+ii);

                    map.put(n++, mapArr);
                    //}
                }
                removeFieldMappings(tableAnchor);
            }
            return map;
        }catch(Exception e){
            return null;
        } finally {
            vc = null;
            map = null;
            //field = null;
            fieldMappings = null;
            info = null;
        }
    }

    private SequenceMap getBuilderSectionDataMap(String tableAnchor, HttpServletRequest request) {
        SequenceMap map = new SequenceMap();
        BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();//P_CM_B_59392 
//		FieldMappings mappings = null;
        try{
            fieldMappings = getFieldMappings(tableAnchor);
            String sDBTableName = fieldMappings.getTableName();
            HeaderMap[] hMap = fieldMappings.getHeaderMap();
            int n =0;
            Info mapArr = null;
            for(HeaderMap h:hMap) {
            	if("4".equals(request.getParameter("moduleId")) && h.getName().indexOf("sms")!=-1 ){//P_CM_B_59392 
            		continue;
            	}
                HeaderField hFld = h.getHeaderFields();
                if("userLevelMessage".equals(hFld.getHeaderAttrMap().get("name"))&& ("off".equalsIgnoreCase(_baseConstants.BOEFLY_INTEGRATION_STATUS)) ){//Bug 59836
                	continue;
                }
                if("fsLeadDetails".equals(tableAnchor) && "callInformation".equals(h.getName())) {
                    continue;
                }
                
                if("fsLeadDetails".equals(tableAnchor) && "userLevelMessage".equals(h.getName())) { //Section of bQual will not come
   					continue;	
   				}
                
                mapArr = new Info();
                mapArr.set(BuilderFormFieldNames.SECTION_NAME, h.getName());
                mapArr.set(BuilderFormFieldNames.SECTION_VALUE, h.getValue());
                mapArr.set(BuilderFormFieldNames.ORDER, h.getOrder());
                mapArr.set(BuilderFormFieldNames.SECTION, hFld.getSectionField());
                mapArr.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                //mapArr.set(BuilderFormFieldNames.SECTION_NAME, sDBTableName);
                map.put(h.getName(), mapArr);
            }
            return map;
        }catch(Exception e){
            return null;
        } finally {
            map = null;
            fieldMappings = null;
        }
    }
    
    public Map getQuickFormField(String tableAnchor) {
    	
    	String tabName="cmLeadDetails".equals(tableAnchor)?"lead":"contact";
    	Map smap=new TreeMap();
    	Info info=null;
    	String xmlPath=MultiTenancyUtil.getTenantConstants().XML_DIRECTORY+"tables/cm/quickFormDetails.xml";
    	try{
    			
    		file = new File(xmlPath);
    		if(file.isFile()) {
    			this.initBDaoInstance();
				doc = docBuilder.parse(file);
				
				Node n=doc.getElementsByTagName("forms").item(0);
				Node chieldNode=n.getLastChild();
				NodeList hNodeList = doc.getElementsByTagName("forms");
				Node headerTablesNode = hNodeList.item(0);
				if (headerTablesNode != null) {
					Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, tabName);
					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], "field");
						for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
							info=new Info();
							info.set("fieldName",getAttributeValue(dependentTableNodes[looptables], "name"));
							info.set("orderBy",getAttributeValue(dependentTableNodes[looptables], "order-by"));
							info.set("colspan",getAttributeValue(dependentTableNodes[looptables], "colspan"));
							smap.put(Integer.parseInt(getAttributeValue(dependentTableNodes[looptables], "order-by")),info);
						}
					}
					
				}
    			
    		}
    	
    	}catch(Exception e){
            return null;
    	}finally{
    		file=null;
    		doc=null;
        }
    	//removeNode(fileLoc,"lead","phoneNumbers");
    	//addNode(fileLoc,"lead","phoneNumbers");
    	return smap;
    }
    
    
public boolean isQuickFormFieldExist(String tableAnchor, String fieldName) {
    	
    	String tabName="cmLeadDetails".equals(tableAnchor)?"lead":"contact";
    	String xmlPath=MultiTenancyUtil.getTenantConstants().XML_DIRECTORY+"tables/cm/quickFormDetails.xml";
    	boolean isExist=false;
    	try{
    			
    		file = new File(xmlPath);
    		if(file.isFile()) {
    			this.initBDaoInstance();
				doc = docBuilder.parse(file);
				
				Node n=doc.getElementsByTagName("forms").item(0);
				Node chieldNode=n.getLastChild();
				NodeList hNodeList = doc.getElementsByTagName("forms");
				Node headerTablesNode = hNodeList.item(0);
				if (headerTablesNode != null) {
					Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, tabName);
					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], "field");
						for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
							if(fieldName.equals(getAttributeValue(dependentTableNodes[looptables], "name"))){
								isExist=true;
								break;
							}
						}
					}
					
				}
    			
    		}
    	
    	}catch(Exception e){
            return false;
    	}finally{
    		file=null;
    		doc=null;
        }
    	//removeNode(fileLoc,"lead","phoneNumbers");
    	//addNode(fileLoc,"lead","phoneNumbers");
    	return isExist;
    }
    
 public void removeNode(String tableanchor, String fieldName) {
    	
    	String xmlPath=MultiTenancyUtil.getTenantConstants().XML_DIRECTORY+"tables/cm/quickFormDetails.xml";
    	String tabName="cmLeadDetails".equals(tableanchor)?"lead":"contact";
    	try{
    			
    		file = new File(xmlPath);
    		if(file.isFile()) {
    			this.initBDaoInstance();
				doc = docBuilder.parse(file);
				
				Node n=doc.getElementsByTagName("forms").item(0);
				Node chieldNode=n.getLastChild();
				NodeList hNodeList = doc.getElementsByTagName("forms");
				Node headerTablesNode = hNodeList.item(0);
				if (headerTablesNode != null) {
					Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, tabName);
					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], "field");
						for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
							if(StringUtil.isValid(fieldName) && fieldName.equals(getAttributeValue(dependentTableNodes[looptables], "name"))){
								dependentTableNodes[looptables].getParentNode().removeChild(dependentTableNodes[looptables]);
								break;
							}
						}
					}
					doc.normalize();
		    		result = new StreamResult(xmlPath);
		    		source = new DOMSource(doc);
		    		trans.transform(source, result);
				}
    		}
    	}catch(Exception e){
        }finally{
    		file=null;
    		doc=null;
    		source=null;
        }
    }
 
 public void addNode(String tableanchor, String fieldName, String colspan) {
 	
 	String xmlPath=MultiTenancyUtil.getTenantConstants().XML_DIRECTORY+"tables/cm/quickFormDetails.xml";
 	String tabName="cmLeadDetails".equals(tableanchor)?"lead":"contact";
 	try{
 		if(!StringUtil.isValid(colspan)){
 			colspan="2";
 		}
 		file = new File(xmlPath);
 		if(file.isFile() && StringUtil.isValid(fieldName)) {
 			this.initBDaoInstance();
				doc = docBuilder.parse(file);
				
				Node n=doc.getElementsByTagName("forms").item(0);
				Node chieldNode=n.getLastChild();
				NodeList hNodeList = doc.getElementsByTagName("forms");
				Node headerTablesNode = hNodeList.item(0);
				if (headerTablesNode != null) {
					Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, tabName);
					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], "field");
						int maxOrderNo=13;
						String maxOrder=getAttributeValue(dependentTableNodes[dependentTableNodes.length-1], "order-by");
						if(StringUtil.isValid(maxOrder)){
							maxOrderNo=(Integer.parseInt(maxOrder))+1;
						}
						Element childElement = doc.createElement(TableXMLDAO.FIELD);
						childElement.setAttribute("name",fieldName);
						childElement.setAttribute("build-field","false");
						childElement.setAttribute("colspan",colspan);
						childElement.setAttribute("order-by",String.valueOf(maxOrderNo));
						dependentTableNodes[dependentTableNodes.length-1].getParentNode().appendChild(childElement);
					}
					doc.normalize();
		    		result = new StreamResult(xmlPath);
		    		source = new DOMSource(doc);
		    		trans.transform(source, result);
				}
 		}
 	}catch(Exception e){
     }finally{
 		file=null;
 		doc=null;
 		source=null;
     }
 }
    
    /**
     * This function is used to reposition the tabs.
     * BB-20150203-259
     * @param request
     * @return
     */
    public SequenceMap getBuilderTabDataMapFromXMl(HttpServletRequest request) {
    	SequenceMap map = new SequenceMap();
    	String reqSubModuleName=getRequestValue(request, "subModuleName");//P_Enh_Mu-Entity_FormGenerator
    	SequenceMap sortedTabMap = new SequenceMap();
    	String tempVal="";
    	String fileLoc="";
    	String moduleName = getRequestValue(request, FieldNames.MODULE);
    	Info info=null;
    	int count=0;
    	String subModuleName="";
    	if("area".equals(moduleName) || "mu".equals(moduleName)){
    		moduleName=ModuleUtil.MODULE_NAME.NAME_FIM;
    	}
    	if(ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName))
    	{
    		subModuleName="lead";
    	}
    	else if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName))
    	{
    		subModuleName="franchiseewithoutsc";
    		//P_Enh_Mu-Entity_FormGenerator starts
    		if("mu".equals(reqSubModuleName)){
    			subModuleName="mu";
    		}else if("area".equals(reqSubModuleName)){
    			subModuleName="area";
    		}//P_Enh_Mu-Entity_FormGenerator ends
    	}else if(ModuleUtil.MODULE_NAME.NAME_CM.equals(moduleName))//FormBuilder changes
    	{
    		subModuleName="contact";
    	}else if(ModuleUtil.MODULE_NAME.NAME_ACCOUNT.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName)){
    		subModuleName=moduleName;
    	}
    
    	try{
    		if(StringUtil.isValidNew(moduleName)){
    			BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    			fileLoc =_baseConstants.XML_DIRECTORY+"tabs/"+moduleName+"Tabs.xml";
    			if(StringUtil.isValidNew(fileLoc)) {
    				file = new File(fileLoc);
    				if(file.isFile()) {
    					this.initBDaoInstance();
    					doc = docBuilder.parse(file);
    					NodeList hNodeList = doc.getElementsByTagName(BuilderFormFieldNames.TAB_GROUP);
    					Node headerTablesNode = hNodeList.item(0);
    					if (headerTablesNode != null) {
    						Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, BuilderFormFieldNames.TABS);
    						for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
    							String subModule=getAttributeValue(headerNodes[loop], BuilderFormFieldNames.SUB_MODULE);
    							if("captivate".equals(subModule)) {
    								continue;
    							}
    							
    							if(StringUtil.isValidNew(subModuleName)&&StringUtil.isValidNew(subModule)&&subModule.equals(subModuleName)){
    								Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], BuilderFormFieldNames.ROW);
    								for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
    									Node[] tabNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TAB);

    									for (int looptab=0; tabNodes != null && looptab < tabNodes.length; looptab++) {
    										info=new Info();
    										String tabDisplayName=getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.TAB_DISPLAY_NAME);
    										if(StringUtil.isValidNew(tabDisplayName)){
    											tempVal=tabDisplayName;
    										}else{
    											tempVal=getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME);
    										}

    										if("Qualification Center".equals(getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME))) { //skipping tabs
    											if(MultiTenancyUtil.getTenantContext().getAttribute("callCenter") == null||(MultiTenancyUtil.getTenantContext().getAttribute("callCenter") != null && ((String)MultiTenancyUtil.getTenantContext().getAttribute("callCenter")).equals("off"))){
    												continue;
    											}
    										}
    										
    										//if("bQual".equals(getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME))){ //bQual will not come
    										if(("bQual".equals(getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME)) && ("off".equalsIgnoreCase(_baseConstants.BOEFLY_INTEGRATION_STATUS)))
    												|| ("Account Info".equals(getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME)) && "contact".equals(subModule)) ) {
    											continue;
    										}

    										info.set(BuilderFormFieldNames.DISPLAY_NAME,tempVal);
    										info.set(BuilderFormFieldNames.TAB_DISPLAY_NAME,getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.DISPLAY_NAME));
    										String tabOrder  = "0";
    										if(StringUtil.isValidNew(getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.TAB_ORDER)))
    										{
    											tabOrder = getAttributeValue(tabNodes[looptab], BuilderFormFieldNames.TAB_ORDER);
    										}
    										info.set(BuilderFormFieldNames.TAB_ORDER, tabOrder);
    										info.set(BuilderFormFieldNames.IS_CUSTOM,"N");
    										map.put(count++,info);
    									}

    								}

    							}
    						}
    					}
    				}
    			}
    			
    			if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName))
		    	{
		    		subModuleName="franchisee";
		    		if("mu".equals((String)request.getAttribute("subModuleName")) || "area".equals((String)request.getAttribute("subModuleName"))){//P_Enh_Mu-Entity_FormGenerator
		    			subModuleName=(String)request.getAttribute("subModuleName");
		    		}
		    	}
    			fileLoc =_baseConstants.XML_DIRECTORY+"tables/admin/"+BuilderConstants.TAB_MAPPING_XML;
    			file = new File(fileLoc);
    			if(file.isFile()) {
    				this.initBDaoInstance();
    				doc = docBuilder.parse(file);
    				NodeList nodeList = doc.getElementsByTagName(BuilderFormFieldNames.MODULE_TAB);
    				int size = nodeList.getLength();
    				nodeList.hashCode();
    				for(int i=0;i< size;i++) {
    					node = nodeList.item(i);
    					String subModule=getAttributeValue(node, "submodule");
    					if("captivate".equals(subModule)) {
    						continue;
    					}
    					
    					//if("bQual".equals(getAttributeValue(node, BuilderFormFieldNames.TAB_DISPLAY))){
    					if("bQual".equals(getAttributeValue(node, BuilderFormFieldNames.TAB_DISPLAY)) && ("off".equalsIgnoreCase(_baseConstants.BOEFLY_INTEGRATION_STATUS)) ){
							continue;
						}
    					
    					if(StringUtil.isValidNew(subModuleName)&&StringUtil.isValidNew(subModule)&&subModule.equals(subModuleName)){
    						info=new Info();
    						info.set(BuilderFormFieldNames.DISPLAY_NAME,getAttributeValue(node, BuilderFormFieldNames.TAB_DISPLAY));
    						info.set(BuilderFormFieldNames.TAB_DISPLAY_NAME,getAttributeValue(node, BuilderFormFieldNames.TAB_DISPLAY));
    						String tabOrder  = "0";
    						if(StringUtil.isValidNew(getAttributeValue(node, BuilderFormFieldNames.TAB_ORDER)))
    						{
    							tabOrder = getAttributeValue(node, BuilderFormFieldNames.TAB_ORDER);
    						}
    						info.set(BuilderFormFieldNames.TAB_ORDER, tabOrder);
    						info.set(BuilderFormFieldNames.IS_CUSTOM,"Y");
    						info.set(BuilderFormFieldNames.TAB_IS_ACTIVE, getAttributeValue(node, BuilderFormFieldNames.TAB_IS_ACTIVE)); //set active or deactive tab
    						map.put(count++,info);

    					}
    				}
    			}
    			String tabOrder="";
    			if(map != null && map.size() > 0) {
    				Info[] tabsInfo = new Info[map.size()];
    				for(int i=0; i < map.size(); i++) {
    					Info currentInfo = (Info) map.get(i);
    					tabsInfo[i] = currentInfo;
    				}
    				int n = map.size();
    				Info swap = null;
    				for (int c = 0; c < n; c++) {
    					for (int d = 0; d < n - c - 1; d++) {
    						int order = Integer.parseInt(tabsInfo[d].getString(BuilderFormFieldNames.TAB_ORDER));
    						int orderNew = Integer.parseInt(tabsInfo[d+1].getString(BuilderFormFieldNames.TAB_ORDER));
    						if(order > orderNew) /* For ascending order sort according to section order < */
    						{
    							swap = new Info();
    							swap = tabsInfo[d];
    							tabsInfo[d] = tabsInfo[d+1];
    							tabsInfo[d+1] = swap;
    						}
    					}
    				}


    				for (int c = 0; c < n; c++)
    					sortedTabMap.put(c, tabsInfo[c]);
    			}
    		}
    		
    		System.out.println("sortedTabMap========>>"+sortedTabMap);
    		return sortedTabMap;

    	} catch(Exception e) {
    		return null;
    	} finally {
    		file = null;
    		doc = null;
    		root = null;
    		result = null;
    		source = null;
    	}
    }
    public FieldMappings getBuilderSectionMapping(String tableAnchor) {
        try{
            return getFieldMappings(tableAnchor);
        }catch(Exception e){
            return null;
        }
    }

    public boolean checkForSkipValue(Vector flds, String sDBTableName, String fldName) {
        if("FRANCHISEE".equals(sDBTableName)) {
            if(fldName.indexOf("franchiseeS") != -1 || fldName.indexOf("franchiseeC") != -1) {
                return true;
            }
        } else if("FIM_LICENSE_AGREEMENT".equals(sDBTableName)) {
            if(fldName.indexOf("fimLicenseAgreementC") != -1) {
                return true;
            }
        } else if("FIM_OWNERS".equals(sDBTableName)) {
            if(fldName.indexOf("fimOwnersC") != -1) {
                return true;
            }
        } else if("FIM_MARKETING".equals(sDBTableName)) {
            if(fldName.indexOf("fimMarketingC") != -1) {
                return true;
            }
        } else if("FIM_QA".equals(sDBTableName)) {
            if(fldName.indexOf("fimQaC") != -1) {
                return true;
            }
        } else if("FIM_EVENTS".equals(sDBTableName)) {
            if(fldName.indexOf("fimEventsC") != -1) {
                return true;
            }
        } else if("FIM_LEGAL_VIOLATION".equals(sDBTableName)) {
            if(fldName.indexOf("fimLegalViolationC") != -1) {
                return true;
            }
        }  else if("FIM_ENTITY_DETAIL".equals(sDBTableName)) {
            if(fldName.indexOf("fimEntityDetailC") != -1) {
                return true;
            }
        } else if("FIM_GUARANTOR".equals(sDBTableName)) {
            if(fldName.indexOf("fimGuarantorC") != -1) {
                return true;
            }
        } else if("FIM_LENDER".equals(sDBTableName)) {
            if(fldName.indexOf("fimLenderC") != -1) {
                return true;
            }
        } else if("FIM_RENEWAL".equals(sDBTableName)) {
            if(fldName.indexOf("fimRenewalC") != -1) {
                return true;
            }
        } else if("FIM_MYSTERY_SHOPPER".equals(sDBTableName)) {
            if(fldName.indexOf("fimMysteryShopperC") != -1) {
                return true;
            }
        } else if("FIM_REAL_ESTATE".equals(sDBTableName)) {
            if(fldName.indexOf("fimRealEstateC") != -1) {
                return true;
            }
        }  else if("FIM_CONTRACT".equals(sDBTableName)) {
            if(fldName.indexOf("fimContractC") != -1) {
                return true;
            }
        }  else if("FIM_FINANCIAL".equals(sDBTableName)) {
            if(fldName.indexOf("fimFinancialC") != -1) {
                return true;
            }
        }  else if("FIM_TERMINATION".equals(sDBTableName)) {
            if(fldName.indexOf("fimTerminationC") != -1) {
                return true;
            }
        }  else if("FIM_INSURANCE".equals(sDBTableName)) {
            if(fldName.indexOf("fimInsuranceC") != -1) {
                return true;
            }
        }  else if("FIM_COMPLAINT".equals(sDBTableName)) {
            if(fldName.indexOf("fimComplaintC") != -1) {
                return true;
            }
        }  else if("FIM_TRAINING".equals(sDBTableName)) {
            if(fldName.indexOf("fimTrainingC") != -1) {
                return true;
            }
        }  else if("FIM_ADDRESS".equals(sDBTableName)) {
            if(fldName.indexOf("fimAddressC") != -1) {
                return true;
            }
        }  else if("FIM_TERRITORY".equals(sDBTableName)) {
            if(fldName.indexOf("fimTerritoryC") != -1) {
                return true;
            }
        }

        if(flds.contains(fldName)) {
            return true;
        }

        return false;
    }

    public String changeFormat(Integer value) {
        if (value.intValue() == 0) {
            return BuilderFormFieldNames.NO;
        } else if (value.intValue() == 1) {
            return BuilderFormFieldNames.YES;
        } else {
            return BuilderFormFieldNames.EMPTY;
        }

    }

    public SequenceMap getRadioOrComboOptionsMap(String fieldName, String tableAnchor) {
        //SMC-20140213-378 Starts
    	if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}
        String query = "SELECT ID,FIELD_ID,FIELD_NAME,OPTION_ID,OPTION_VALUE,IS_ACTIVE,TABLE_ANCHOR FROM FIM_BUILDER_MASTER_DATA  WHERE FIELD_NAME = ? AND TABLE_ANCHOR = ? ORDER BY ORDER_NO";//FORM_BUILDER_ISSUE_1//P_Enh_Form_Builder_Option_Sequence

        ResultSet result =null;
        SequenceMap map = new SequenceMap();
        List<String> fieldData = getFieldData(tableAnchor, fieldName);
        Set<String> fieldData2 = new TreeSet<String>();
        Iterator<String> it = fieldData.iterator();
        while(it.hasNext())
        {
            String str = it.next();
            if(str != null){
                String[] s = str.split(",");
                for(int i=0;i<s.length;i++){
                    fieldData2.add(s[i].trim());
                }
            }
        }
        try
        {
            result=QueryUtil.getResult(query, new Object[] {fieldName, tableAnchor});
            while (result.next()) {
                Info info = new Info();
                info.set(BuilderFormFieldNames.FIELD_NAME,result.getString("FIELD_NAME"));
                info.set(BuilderFormFieldNames.OPTION_ID, result.getString("OPTION_ID"));
                info.set(BuilderFormFieldNames.OPTION_VALUE, result.getString("OPTION_VALUE"));
                info.set(BuilderFormFieldNames.OPTION_ACTIVATED, result.getString("IS_ACTIVE"));
                if(fieldData2.contains(result.getString("OPTION_ID")) || "N".equals(result.getString("IS_ACTIVE")))//SMC-20140213-378
                {
                    info.set(BuilderFormFieldNames.CAN_DELETE,"false");
                }
                else
                {
                    info.set(BuilderFormFieldNames.CAN_DELETE,"true");
                }
                map.put(result.getString("ID"), info);
            }
        }//SMC-20140213-378 Ends
        catch (Exception e) {
            // TODO: handle exception
        }
        finally
        {
            QueryUtil.releaseResultSet(result);
        }


        return map;
    }

    //SMC-20140213-378 Starts
    /**
     * Returns a List of Data regarding to a particular field in a Custom Form	
     * @param tableAnchor
     * @param fieldName
     * @return
     */
    private List<String> getFieldData(String tableAnchor,String fieldName)
    {
        List<String> fieldData = new ArrayList<String>();
        if(StringUtil.isValid(fieldName) && StringUtil.isValid(tableAnchor))
        {
            FieldMappings fieldMappings = getFieldMappings(tableAnchor);
            String tableName = fieldMappings.getTableName();
            String cloumnName = fieldMappings.getDbField(fieldName);
            String query = "SELECT DISTINCT " + cloumnName + " FROM " + tableName;
            ResultSet result = null;
            try
            {
                result = QueryUtil.getResult(query, new String[] {});
                if(result != null)
                {
                    while(result.next())
                    {
                        fieldData.add(result.getString(cloumnName));
                    }
                }
            }catch(Exception e)
            {
            }finally
            {
                result = null;
            }
        }
        return fieldData;
    }

    public Info getRadioOrComboOptionsInfo(String fieldName, String tableAnchor) {
        return getRadioOrComboOptionsInfo(fieldName, tableAnchor, null);
    }
    public Info getRadioOrComboOptionsInfo(String fieldName, String tableAnchor,String value) {
        return getRadioOrComboOptionsInfo(fieldName, tableAnchor, value, null); //BB-20150203-259 (Dynamic Response based on parent field response) starts
    }
    public Info getRadioOrComboOptionsInfo(String fieldName, String tableAnchor,String value, String parentValue) {
    	if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}
        StringBuffer query = new StringBuffer();
        query.append("SELECT OPTION_ID,OPTION_VALUE FROM FIM_BUILDER_MASTER_DATA  WHERE FIELD_NAME = ? AND TABLE_ANCHOR = ? ");
        if(StringUtil.isValid(parentValue)) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
            String newValue = "";
            if(parentValue.contains(",")) {
                String[] parentValueArray = parentValue.split(",");
                for(String pValue : parentValueArray) {
                    newValue += "'"+pValue.trim()+"'" + ",";//P_CM_B_60311
                }
            } else {
                newValue = "'"+parentValue+"'";
            }
            if(StringUtil.isValidNew(newValue) && newValue.endsWith(",")) {
            	newValue = newValue.substring(0, newValue.lastIndexOf(","));
            }
            query.append("AND DEPENDENT_VALUE IN ("+newValue+")");
        } //BB-20150203-259 (Dynamic Response based on parent field response) ends
        query.append("AND (IS_ACTIVE = 'Y'");
        if(StringUtil.isValid(value))
            query.append(" OR  OPTION_ID IN ("+ value +")");
        query.append(") ORDER BY ORDER_NO");//FORM_BUILDER_ISSUE_1//P_Enh_Form_Builder_Option_Sequence
        ResultSet result = QueryUtil.getResult(query.toString(), new Object[] {fieldName, tableAnchor}); //SMC-20140213-378
        Info info = new Info();
        try {
//    	System.out.println("fieldName, tableAnchor "+ fieldName + ", " + tableAnchor);
//    	System.out.println("query "+ query);
//    	System.out.println("result size :  "+ result.size());
            while (result.next()) {
//        	System.out.println( result.getString("OPTION_ID") + " :  "+ result.getString("OPTION_VALUE"));
                info.set(result.getString("OPTION_ID"), result.getString("OPTION_VALUE"));
            }
        } catch(Exception e) {
        } finally {
            QueryUtil.releaseResultSet(result);
        }
        return info;
    }
    public boolean deleteRadioOrComboOptions(String fieldName, String tableAnchor) throws Exception {
    	if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}
        boolean flag = false;
        String query = "SELECT * FROM FIM_BUILDER_MASTER_DATA WHERE FIELD_NAME =? AND TABLE_ANCHOR=? ";
        ResultSet result = QueryUtil.getResult(query, new Object[] {fieldName, tableAnchor});
        try {
            String query1 = "DELETE FROM FIM_BUILDER_MASTER_DATA WHERE FIELD_NAME =? AND TABLE_ANCHOR=? ";
            if(result.next()) {
                QueryUtil.update(query1, new String[]{fieldName,tableAnchor});
                flag = true;
            }

            return flag;
        } catch(Exception e) {
            return false;
        } finally {
            QueryUtil.releaseResultSet(result);
        }

    }

    //SMC-20140213-378 Starts 	
    public boolean insertComboOptionsMap(String fieldName,String [] optionName, String tableAnchor) {
        return insertComboOptionsMap(fieldName,optionName,tableAnchor,null);
    }
    public boolean insertComboOptionsMap(String fieldName,String [] optionName, String tableAnchor,String[] isActive) {
        return insertComboOptionsMap(fieldName, optionName, tableAnchor, isActive, null); //BB-20150203-259 (Dynamic Response based on parent field response) starts
    }
    public boolean insertComboOptionsMap(String fieldName,String [] optionName, String tableAnchor,String[] isActive, String[] dependentValue) {// Alleg-20160615-349 starts
    	return insertComboOptionsMap(fieldName, optionName, tableAnchor, isActive, dependentValue,null); 
    }
    public boolean insertComboOptionsMap(String fieldName,String [] optionName, String tableAnchor,String[] isActive, String[] dependentValue,String[] optionValues) {//Alleg-20160615-349 ends
        boolean flag = false;
        String[] queryArray = new String[1];
        if(StringUtil.isValidArray(dependentValue)) { //BB-20150203-259 (Dynamic Response based on parent field response) starts
            queryArray[0] = "INSERT INTO FIM_BUILDER_MASTER_DATA(ID,FIELD_ID,FIELD_NAME,OPTION_ID,OPTION_VALUE,IS_ACTIVE,TABLE_ANCHOR, DEPENDENT_VALUE, ORDER_NO) VALUES (?,?,?,?,?,?,?,?,?)";//P_Enh_Form_Builder_Option_Sequence
        } else {
            queryArray[0] = "INSERT INTO FIM_BUILDER_MASTER_DATA(ID,FIELD_ID,FIELD_NAME,OPTION_ID,OPTION_VALUE,IS_ACTIVE,TABLE_ANCHOR, ORDER_NO) VALUES (?,?,?,?,?,?,?,?)";//P_Enh_Form_Builder_Option_Sequence
        } //BB-20150203-259 (Dynamic Response based on parent field response) ends
        String[] params = null;
        //Map<String,List<String[]>> batchMap = NewPortalUtils.getNewHashMapWithKeyValueType();
        Map<String,List<String[]>> batchMap = BaseUtils.getNewHashMapWithKeyValueType();   //For Product_Seperation_BL By Amar Singh.
        ArrayList<String[]> paramList = new ArrayList<String[]>();
        String optionActiveVal = "Y";
        //String query = "INSERT INTO FIM_BUILDER_MASTER_DATA VALUES (?,?,?,?,?,?,?)";
        try {
        	int maxValue=0;//codebase Issue Option sequence issue
        	int  optionValue=0;// Alleg-20160615-349
            for(int i=0;i<optionName.length;i++) {
                optionActiveVal = (isActive!=null && i<(isActive.length))?isActive[i]:optionActiveVal; //BB-20150203-259 (Dynamic Response based on parent field response) starts
                if(optionValues!=null && i<optionValues.length)// Alleg-20160615-349 starts
                {
                	optionValue=Integer.parseInt(optionValues[i]);
                	if(maxValue<optionValue){//codebase Issue Option sequence issue starts
                		maxValue=optionValue;
                	}//codebase Issue Option sequence issue ends
                }else
                {
                	//codebase Issue Option sequence issue starts
                	maxValue++;
                	optionValue=maxValue;
                	//codebase Issue Option sequence issue ends
                }// Alleg-20160615-349 ends
                if(StringUtil.isValidArray(dependentValue)) {
                    params = new String[]{"","",fieldName,""+optionValue,optionName[i],optionActiveVal,tableAnchor, dependentValue[i],""+(i+1)}; //setting parent field name inside table//P_Enh_Form_Builder_Option_Sequence //Alleg-20160615-349
                } else {
                    params = new String[]{"","",fieldName,""+optionValue,optionName[i],optionActiveVal,tableAnchor,""+(i+1)};//P_Enh_Form_Builder_Option_Sequence //Alleg-20160615-349
                }
                paramList.add(params); //BB-20150203-259 (Dynamic Response based on parent field response) ends
                //QueryUtil.update(query, new String[]{"","",fieldName,""+(i+1),optionName[i], tableAnchor,isActive});
            }
            batchMap.put("0",paramList);
            QueryUtil.preparedStatementsBatchUpdate(queryArray, batchMap);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }//SMC-20140213-378 Ends

    public String getRadioOrComboOptionsValueForId(String fieldName, String tableAnchor, String id) {

    	if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}
    	
        String query = "SELECT OPTION_ID,OPTION_VALUE FROM FIM_BUILDER_MASTER_DATA  WHERE FIELD_NAME = ? AND TABLE_ANCHOR = ? AND OPTION_ID IN ( "+id+" ) ORDER BY ORDER_NO";//P_Enh_Form_Builder_Option_Sequence
        StringBuffer str=new StringBuffer();
        int count=0;
        ResultSet result = QueryUtil.getResult(query, new Object[] {fieldName, tableAnchor});
        try {
            Info info = new Info();

            while(result.next()) {
                str.append(result.getString("OPTION_VALUE"));
                str.append(",");
            }
            if(StringUtil.isValidNew(str.toString()))
            {
                return StringUtil.removeLastComma(str).toString();
            }
            else
            {
                return null;
            }
        } catch(Exception e) {
            return null;
        } finally {
            QueryUtil.releaseResultSet(result);
        }
    }

    public String getCheckboxOptionsValueForIds(String fieldName, String tableAnchor, String id) {
        String query = "";
        
        if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}
        
        //FORM_BUILDER_ISSUE_1 starts
        if(StringUtil.isValid(id))
        {
            String currentValue = id.trim();
            if(currentValue.startsWith(","))
                currentValue = currentValue.substring(1);
            while(currentValue.endsWith(","))
                currentValue = currentValue.substring(0,currentValue.length()-1);
            query = "SELECT OPTION_ID,OPTION_VALUE FROM FIM_BUILDER_MASTER_DATA  WHERE FIELD_NAME = ? AND TABLE_ANCHOR = ? AND OPTION_ID IN ( "+currentValue+" ) ORDER BY ORDER_NO ";//P_Enh_Form_Builder_Option_Sequence
        }
        //FORM_BUILDER_ISSUE_1 ends
        ResultSet result = QueryUtil.getResult(query, new Object[] {fieldName, tableAnchor});
        Info info = new Info();
        String val = "";
        try
        {
            while(result!=null && result.next()) {//FORM_BUILDER_ISSUE_1
                String optval = result.getString("OPTION_VALUE");
                if(val.equals("")){
                    val = optval;
                } else {
                    val = val + ", " + optval;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        finally
        {

            QueryUtil.releaseResultSet(result);
        }


        return val;
    }

    
	public void updateKeywordsStatusField(String fieldName,String tableAnchor,String isOtherTableField, String newPath) throws SQLException {
		String isActive = "N";
		//FieldMappings mapping = DBUtil.getInstance().getFieldMappings(tableAnchor);
		FieldMappings mapping  = new FormBaseDAO().getFieldMappingsByPath(newPath, tableAnchor+"_copy");
		Field field = mapping.getField(fieldName);
		if("true".equals(isOtherTableField)) {
			field = mapping.getOtherTableField(fieldName);
		}
		//if(field.isActiveField() == true) {
		if(field.isActiveField()) {
			isActive = "Y";
		}
		String query = "UPDATE FORM_BUILDER_KEYWORDS_CONFIGURATION SET IS_ACTIVE=? WHERE FIELD_NAME=? AND TABLE_ANCHOR = ?";
		String params[] = new String[]{isActive,fieldName,tableAnchor};
		try {
			QueryUtil.update(query,params);
		} catch(Exception e) {
		}
	}
	public void updateKeywordsStatus(String fieldName,String tableAnchor,String isOtherTableField, String newPath) throws SQLException 
	{
		String isActive = "N";
		FieldMappings mapping  = new FormBaseDAO().getFieldMappingsByPath(newPath, tableAnchor+"_copy");
		Field field = mapping.getField(fieldName);
		if("true".equals(isOtherTableField)) {
			field = mapping.getOtherTableField(fieldName);
		}
		if(field.isActiveField()) {
			isActive = "Y";
		}
		String query = "UPDATE CM_EMAIL_FIELD_MAPPING SET IS_ACTIVE=? WHERE FIELD_NAME=? ";
		String params[] = new String[]{isActive,fieldName};
		try {
			QueryUtil.update(query,params);
		} catch(Exception e) {
		}
	}
	public void updateLeadKeywordsStatus(String fieldName,String tableAnchor,String isOtherTableField, String newPath) throws SQLException 
	{
		String isActive = "N";
		FieldMappings mapping  = new FormBaseDAO().getFieldMappingsByPath(newPath, tableAnchor+"_copy");
		Field field = mapping.getField(fieldName);
		if("true".equals(isOtherTableField)) {
			field = mapping.getOtherTableField(fieldName);
		}
		if(field.isActiveField()) {
			isActive = "Y";
		}
		String query = "UPDATE CM_LEAD_EMAIL_FIELD_MAPPING SET IS_ACTIVE=? WHERE FIELD_NAME=? ";
		String params[] = new String[]{isActive,fieldName};
		try {
			QueryUtil.update(query,params);
		} catch(Exception e) {
		}
	}

	public void updateKeywordsDisplayNameField(String fieldName,String tableAnchor,String isOtherTableField, String newPath) throws SQLException {
		String displayName = "";
		//FieldMappings mapping = DBUtil.getInstance().getFieldMappings(tableAnchor);
		FieldMappings mapping  = new FormBaseDAO().getFieldMappingsByPath(newPath, tableAnchor+"_copy");
		Field field = mapping.getField(fieldName);
		if("true".equals(isOtherTableField)) {
			field = mapping.getOtherTableField(fieldName);
		}
		displayName = field.getDisplayName();
		String query = "UPDATE FORM_BUILDER_KEYWORDS_CONFIGURATION SET DISPLAY_NAME=? WHERE FIELD_NAME=? AND TABLE_ANCHOR = ?";
		String params[] = new String[]{displayName,fieldName,tableAnchor};
		try {
			QueryUtil.update(query,params);
		} catch(Exception e) {
		}
	}
	

	public synchronized void setBuilderFormAddOrUpdate(HashMap dataMap, HttpServletRequest request, String user_no, String tableAnchor, String formId, SequenceMap baseBuilder1) throws SQLException, RecordNotFoundException {
		System.out.println("dataMap=========>>"+dataMap);
		BuilderFormDAO builderFormDao = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO();
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		boolean flag = false;
		StringBuffer updateQuery1=null;
		Object baseBuilder = null;
		String key;
		Iterator it;
		Object ob;
		FieldMappings fieldMappings = null;
		String loc = null;
		String location = null;
		String locExport=null;// P_B_73908 starts
		String locationExport=null;
		String tableAnchorExport=null;// P_B_73908 ends
		Field fld = null;
		try {
			if(tableAnchor == null) {
                formId = getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_FORM_ID);
                if(StringUtil.isValidNew(formId)) {
                    it = baseBuilder1.keys().iterator();
                    if(it.hasNext()) {
                        key = (String)it.next();
                        ob = (Object)baseBuilder1.get(key);
                        if(ob instanceof Info) {
                            tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                        }
                    }
                    if(!StringUtil.isValid(tableAnchor)) {
                        flag = false;
                    }
                }
            }
			String mainTableAnchor=tableAnchor;
			loc	= (String)new FormBaseDAO().getTableMappings().get(tableAnchor);
			location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
			if(StringUtil.isValidNew(location)) {
				//request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
				dataMap.put(BuilderFormFieldNames.FILE_LOCATION, location);
			}
			String isCenterInfoField = getValueFromMap(dataMap, "isCenterInfoField");//Martin-20160727-017
			String modifyFld = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.MODIFY_FIELD);
			String asActive = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE);
			String includeInCampaign = builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN); //BB-20150319-268(FIM Campaign Center)
			//P_Enh_FormBuilder_Tabular_Section starts
			String isTabularSection=builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_TABULAR_SECTION);
			String tabularSectionTableDBName=builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.TABULAR_SECTION_TABLE_DB_NAME);
			String tabularSectionTableName=builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.TABULAR_SECTION_TABLE_NAME);
			String moduleName = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME);
			String isMandatoryVal = getValueFromMap(dataMap, BuilderFormFieldNames.IS_MANDATORY);
			if("true".equals(isTabularSection)){
				dataMap.put(BuilderFormFieldNames.FILE_LOCATION, _baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH+tabularSectionTableName+".xml");
				tableAnchor=tabularSectionTableName;
			}//P_Enh_FormBuilder_Tabular_Section ends
			String newPath = "";
			String newPathExport="";// P_B_73908
            FieldMappings fMap = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tableAnchor);
            int orderNo = 0;
            if(fMap != null &&  StringUtil.isValidNew(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION))) {
            	Field[] fldarr=fMap.getSectionTableAllActiveDeactiveFieldsWithOrderBy(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));//ZCUB-20150505-144 To Fix Null Pointer Exception
            	if(fldarr != null){
            		orderNo = fldarr.length;
            	}
                dataMap.put(BuilderFormFieldNames.ORDER_NO, String.valueOf(orderNo));
            }
            if("fimOwners".equals(tableAnchor)){// P_B_73908 starts
            	tableAnchorExport="multiUnitOwnerExport";
				locExport	= (String)new FormBaseDAO().getTableMappings().get(tableAnchorExport);
				locationExport = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + locExport;
				
			}else if("fimEntityDetail".equals(tableAnchor)){
				tableAnchorExport="entityDisplayDetail";
				locExport	= (String)new FormBaseDAO().getTableMappings().get(tableAnchorExport);
				locationExport = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + locExport;

			}// P_B_73908 ends
			if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld) && StringUtil.isValidNew(asActive)) {
				newPath = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().updateActiveXmlData(dataMap, tableAnchor);
				if(StringUtil.isValidNew(locationExport) ) {// P_B_73908
					dataMap.put(BuilderFormFieldNames.FILE_LOCATION, locationExport);
					dataMap.put("isCenterInfoField", null);						//P_B_77469
					newPathExport=BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().updateActiveXmlData(dataMap, tableAnchor);
					dataMap.put("newPathExport", newPathExport);
				}
				updateActiveSearchFields(fMap,dataMap,asActive);//HomeTeam-20160603-584 ends
				updateParsingKeywordStatus(dataMap,asActive);					//P_Enh_Custom_Parsing_Keywords_FC-286
				updateTabularFields(dataMap,asActive);
			} else {
				//request.setAttribute("tableAnchorName", tableAnchor);
				dataMap.put("tableAnchorName", tableAnchor);
				newPath = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().addModifyXmlData(dataMap);
				if(StringUtil.isValidNew(locationExport) ) {// P_B_73908
					dataMap.put("tableAnchorName", tableAnchorExport);
					dataMap.put("isCenterInfoField", null);									//P_B_77469
					dataMap.put(BuilderFormFieldNames.FILE_LOCATION, locationExport);
					dataMap.put("forExportTable", "yes");
					newPathExport=BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().addModifyXmlData(dataMap);
					dataMap.put("newPathExport", newPathExport);
				}
				
				if(("cm".equals(moduleName) || "lead".equals(moduleName)) && ("true".equals(isMandatoryVal) || "false".equals(isMandatoryVal))){
					boolean isExist=false;
					String fldName=builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
					if(!getConfiguredList().contains(fldName)){
					SequenceMap fieldMap= DBUtil.getInstance().getFieldMappings(tableAnchor).getAllFields();
					Field tempfld=(Field)fieldMap.get(fldName);
					String colspan=null;
					if(tempfld!=null){
						colspan=tempfld.getColspan();
					}
					if("true".equals(isMandatoryVal)){
						String pfieldName=tempfld.getDependentParentField();
						if(StringUtil.isValid(pfieldName) && pfieldName.indexOf("##") != -1){
							pfieldName=pfieldName.split("##")[0];
							if(StringUtil.isValid(pfieldName)){
								isExist=isQuickFormFieldExist(tableAnchor,pfieldName);
								if(!isExist){
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().addNode(tableAnchor, pfieldName, colspan);
								}
							}
						}
						isExist=isQuickFormFieldExist(tableAnchor,fldName);
						if(!isExist){
							BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().addNode(tableAnchor, fldName, colspan);
						}
					}else{
						SequenceMap chieldMap=tempfld.getDependentChildTotalMap();
						if(chieldMap != null && chieldMap.size()>0){
							for(int k=0;k<chieldMap.size();k++){
								String pfieldName=(String)chieldMap.getKey(k);
								if(StringUtil.isValid(pfieldName)){
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().removeNode(tableAnchor, pfieldName);
								}
							}
						}
						BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().removeNode(tableAnchor, fldName);
					}
				  }
				}
			}
			dataMap.put("newPath", newPath);

			if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
				//DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEE);
				DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEE+"_copy");
			}
			if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
				//DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEES);
				DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEES+"_copy");
			}
			//DBUtil.getInstance().removeFieldMappings(tableAnchor);
			DBUtil.getInstance().removeFieldMappings(tableAnchor+"_copy");

			fieldMappings = new FormBaseDAO().getFieldMappingsByPath(newPath, tableAnchor+"_copy");
			String tableName = fieldMappings.getTableName();
			String fldName = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
			//String moduleName = (String)(request.getSession().getAttribute(BuilderFormFieldNames.BUILDER_MODULE_NAME));//ENH_MODULE_CUSTOM_TABS
			

			String isOtherTableField = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_OTHER_TABLE_FIELD);
			boolean isOtherField = false;
			if(StringUtil.isValidNew(isOtherTableField)) {
				isOtherField = Boolean.parseBoolean(isOtherTableField);
			}
			if(isOtherField) {
				fld = fieldMappings.getOtherTableField(fldName);
			} else {
				fld = fieldMappings.getField(fldName);
			}

			if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld)) {
				updateKeywordsStatusField(fldName,tableAnchor,isOtherTableField, newPath);
				if("cm".equals(moduleName) ){
					updateKeywordsStatus(fldName,tableAnchor,isOtherTableField, newPath);
				}
				else if("lead".equals(moduleName) ){
					updateLeadKeywordsStatus(fldName,tableAnchor,isOtherTableField, newPath);
				}
			}else if(StringUtil.isValidNew(modifyFld) && "yes".equals(modifyFld)){
				updateKeywordsDisplayNameField(fldName,tableAnchor,isOtherTableField, newPath);
			}
			
			if("file".equals(getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE)) && "yes".equals(modifyFld)) { //in case of document type, keyword will be removed
				deleteKeywordsField(fldName, formId);
			}


			boolean isBuildFld = (Boolean)fld.isBuildField();
			dataMap.put("isBuildFld", String.valueOf(isBuildFld));
			boolean isActive = (Boolean)fld.isActiveField();
			dataMap.put("isActive", String.valueOf(isActive));
			boolean isMandatory = (Boolean)fld.isMandatoryField();
			dataMap.put("isMandatory", String.valueOf(isMandatory));

			String checkValue = builderFormDao.getValueFromMap(dataMap, "checkValue");
			boolean isInputVal = Boolean.parseBoolean(checkValue);

			String userIP 	= null;
			String sUserNo	= null;
			//WebCacheBypass webCacheIP = new WebCacheBypass(request);
			//userIP = webCacheIP.getRemoteAddr();
			userIP = builderFormDao.getValueFromMap(dataMap, "userIP");
			if(StringUtil.isValid(newPath)) {
				Info infoLogs = new Info();

				String dropdownOpt = builderFormDao.getValueFromMap(dataMap, "dropdownOpt");
				if(!isBuildFld && !StringUtil.isValid(dropdownOpt)) {
					Info info = new Info();
					if(StringUtil.isValidNew(modifyFld) && ("yes".equals(modifyFld) || "y".equals(modifyFld.trim()))) {
						String[] idField = {BuilderFormFieldNames.FIELD_ID};

						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME))) {
							info.set(BuilderFormFieldNames.DISPLAY_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO))) {
							info.set(BuilderFormFieldNames.ORDER_NO, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
						}
						if(isActive) {
							info.set(BuilderFormFieldNames.IS_ACTIVE, "yes");
						} else {
							info.set(BuilderFormFieldNames.IS_ACTIVE, "no");
						}
						info.set(BuilderFormFieldNames.IS_MANDATORY, isMandatory);

						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME))) {
							info.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_SEQ))) {
							info.set(BuilderFormFieldNames.DB_COLUMN_SEQ, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_SEQ));
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE))) {
							info.set(BuilderFormFieldNames.DB_COLUMN_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE));
						} else if(StringUtil.isValidNew(getValueFromMap(dataMap, "dataTypeModify"))) {
							info.set(BuilderFormFieldNames.DB_COLUMN_TYPE, getValueFromMap(dataMap, "dataTypeModify"));
						}
						if(fld.isPiiEnabled()){
							info.set(BuilderFormFieldNames.DB_COLUMN_TYPE, "blob");
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH))) {
							info.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH));
						}

						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN))) {
							info.set(BuilderFormFieldNames.NO_OF_COLUMN, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN));
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW))) {
							info.set(BuilderFormFieldNames.NO_OF_ROW, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW));
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) {
							info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE));
							info.set(FieldNames.INCLUDE_IN_CAMPAIGN, builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN));
							info.set("oldIncludeInCampaign", builderFormDao.getValueFromMap(dataMap, "oldIncludeInCampaign"));
							info.set("modifyFld", modifyFld);
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"))) {
							info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"));
						}

						Info archiveInfo = (Info)info.clone();
						//String archiveTable = (String)request.getAttribute("archiveTable");
						String archiveTable = builderFormDao.getValueFromMap(dataMap, "archiveTable");
						if(StringUtil.isValid(archiveTable))
						{
							archiveInfo.set(BuilderFormFieldNames.TABLE_NAME, archiveTable);
						}

						info.set(BuilderFormFieldNames.TABLE_NAME, tableName);
						if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld) && StringUtil.isValidNew(asActive)) {
							if(!"true".equals(isTabularSection)){
							BuilderFormDAO.updateSummaryDisplayIsSelected(asActive, fldName, fld.getDbField());
							if("31".equals(formId) || "32".equals(formId) || "41".equals(formId)){//ZCUB-20160210-230
								BuilderFormDAO.updateIsCmFilterColumnDataSelected(asActive, fldName);
							}
							}
							if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
							BuilderFormDAO.updateTabularSectionDisplayColumns(asActive, fldName, fld.getDbField(),null);
							}//P_Enh_FormBuilder_Tabular_Section ends
						}
						if("y".equals(modifyFld.trim()) || isInputVal) {
							if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME))) {
								if(!"true".equals(isTabularSection)){
								BuilderFormDAO.updateSummaryDisplayColumnName(fldName, fld.getDbField(), builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
								}
								if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
								BuilderFormDAO.updateTabularSectionDisplayColumns(null,fldName, fld.getDbField(), builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
								}//P_Enh_FormBuilder_Tabular_Section ends
							}
							if("0".equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN)) && "Text".equals(fld.getDisplayTypeField())&& "Email".equalsIgnoreCase(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)))				//FIM Campaign mail issue DKI starts 
							{
								if(builderFormDao.getValueFromMap(dataMap, "oldIncludeInCampaign").equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN))) {
									//do nothing
								} else {
									Info emailInfo = new Info();
									String[] idFieldForEmail = {"fimCampaignEmailId"};
									emailInfo.setIDField(idFieldForEmail);
									emailInfo.set("tableName", tableName);
									emailInfo.set("emailColumnName", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
									emailInfo.set("tableAnchor",tableAnchor);

									//emailInfo.set("isActive", "N");
									if(isActive) {
										emailInfo.set("isActive", "Y");
									} else {
										emailInfo.set("isActive", "N");
									}
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("fimCampaignEmailCampaign", emailInfo);
								}
							} else if("1".equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN))) {
								Info emailInfo = new Info();
								String[] idFieldForEmail = {"fimCampaignEmailId"};
								emailInfo.setIDField(idFieldForEmail);
								emailInfo.set("tableName", tableName);
								emailInfo.set("emailColumnName", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
								emailInfo.set("tableAnchor",tableAnchor);
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().delete("fimCampaignEmailCampaign", emailInfo);
							}																																																//FIM Campaign mail issue DKI ends
						} else {
							String dateType = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE);
							if(!StringUtil.isValidNew(dateType)) {
								dateType = builderFormDao.getValueFromMap(dataMap, "dataTypeModify");
							}
							String dataTypeVal = builderFormDao.getValueFromMap(dataMap, "dataTypeVal");
							if(StringUtil.isValidNew(dateType)) {
								boolean flagOption = false;
								if("radio".equals(dateType) && "Radio".equals(dataTypeVal)) {
									flagOption = true;
								}
								if("combo".equals(dateType) && "Combo".equals(dataTypeVal)) {
									flagOption = true;
								}
								if("checkbox".equals(dateType) && "Checkbox".equals(dataTypeVal)) {
									flagOption = true;
								}
								if(!"Radio".equals(dataTypeVal) && !"Combo".equals(dataTypeVal) && !"Checkbox".equals(dataTypeVal)) {
									if(!"radio".equals(dateType) && !"combo".equals(dateType) && !"checkbox".equals(dateType))
										flagOption = true;
								}
								if(!flagOption) {
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().deleteRadioOrComboOptions(fldName, tableAnchor);
								}
								if(StringUtil.isValidNew(tableName)){
							 	updateQuery1=new StringBuffer();
					        	updateQuery1.append("UPDATE  PII_FIELD_DATA_TYPE ").append(" ");
					        	updateQuery1.append(" SET FIELD_DATA_TYPE='").append(info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE).toUpperCase()).append("' ");
					        	if(StringUtil.isValidNew(info.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH))){
					        	updateQuery1.append(" , FIELD_SIZE='").append(info.getString(BuilderFormFieldNames.DB_COLUMN_LENGTH)).append("' ");
					        	}
					        	updateQuery1.append(" WHERE TABLE_NAME='").append(tableName).append("' ");
					        	updateQuery1.append(" AND FIELD_NAME='").append(info.getString(BuilderFormFieldNames.DB_COLUMN_NAME)).append("' ");
					        	QueryUtil.update(updateQuery1.toString(), null);
								}
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableModifyData(info);
								if(StringUtil.isValid(archiveTable)) {
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableModifyData(archiveInfo);
								}
							}
							boolean isDone = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(info);
							if(StringUtil.isValid(archiveTable)) {
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(archiveInfo);
							}
							infoLogs.setIDField(idField);
							infoLogs.set("fieldId", "");
							infoLogs.set(BuilderFormFieldNames.BUILDER_FORM_ID, formId);
							infoLogs.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
							infoLogs.set(BuilderFormFieldNames.TABLE_NAME, tableName);
							infoLogs.set(BuilderFormFieldNames.FIELD_NAME, fldName);
							infoLogs.set(BuilderFormFieldNames.DISPLAY_NAME, fld.getDisplayName());
							if(fld.getDisplayTypeField().equals("Text")) {
								infoLogs.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE));
							}
							if(fld.getDisplayTypeField().equals("Numeric")) {
								infoLogs.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"));
							}
							infoLogs.set(BuilderFormFieldNames.IS_BUILD_FIELD, fld.isBuildField()?"yes":"no");

							infoLogs.set(BuilderFormFieldNames.ORDER_NO, fld.getOrderBy());
							String displayType = fld.getDisplayTypeField();
							if("Combo".equals(displayType)) {
								displayType = "Drop-down";
								if("true".equals(fld.getisMultiSelect())) {
									displayType = "Multi Select Drop-down";
								}
								
								if("2".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION)) || "1".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION))) {
									displayType = "State/Country";
								}
							}
								
							infoLogs.set(BuilderFormFieldNames.DB_COLUMN_TYPE, displayType);

							infoLogs.set(BuilderFormFieldNames.IS_ACTIVE, String.valueOf(fld.isActiveField()));
							infoLogs.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
							if(fld.getDisplayTypeField().equals("Text"))
								infoLogs.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, fld.getDBFieldLength());
							if(fld.getDisplayTypeField().equals("TextArea")) {
								infoLogs.set(BuilderFormFieldNames.NO_OF_COLUMN, fld.getNoOfColsField());
								infoLogs.set(BuilderFormFieldNames.NO_OF_ROW, fld.getNoOfRowsField());
							}

							if(fld.getDisplayTypeField().equals("Combo")) {
								infoLogs.set(BuilderFormFieldNames.DROPDOWN_OPTION, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION));
							}
							infoLogs.set(BuilderFormFieldNames.IS_MANDATORY, String.valueOf(fld.isMandatoryField()));
							infoLogs.set(BuilderFormFieldNames.SECTION, fld.getSectionField());
							infoLogs.set(BuilderFormFieldNames.EXPORTABLE, String.valueOf(fld.isFieldExportable()));
							infoLogs.set(BuilderFormFieldNames.MAIL_MERGE_KEYWORD, fld.getMailMergeKeyword());
							infoLogs.set(BuilderFormFieldNames.IS_PII_ENABLED, String.valueOf(fld.isPiiEnabled()));
							infoLogs.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));
							infoLogs.set(BuilderFormFieldNames.USER_NO, user_no);
							infoLogs.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
							infoLogs.set(BuilderFormFieldNames.MODULE_NAME, moduleName);
							infoLogs.set(BuilderFormFieldNames.ACTION_TYPE, "Modify");

							BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("formBuilderFieldLogs", infoLogs);

							if("0".equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN)) && "Text".equals(fld.getDisplayTypeField())&& "Email".equalsIgnoreCase(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) 
							{
								if(builderFormDao.getValueFromMap(dataMap, "oldIncludeInCampaign").equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN))) {
									//do nothing
								} else {
									Info emailInfo = new Info();
									String[] idFieldForEmail = {"fimCampaignEmailId"};
									emailInfo.setIDField(idFieldForEmail);
									emailInfo.set("tableName", tableName);
									emailInfo.set("emailColumnName", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
									emailInfo.set("tableAnchor",tableAnchor);

									//emailInfo.set("isActive", "N");
									if(isActive) {
										emailInfo.set("isActive", "Y");
									} else {
										emailInfo.set("isActive", "N");
									}
									BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("fimCampaignEmailCampaign", emailInfo);
								}
							} else if("1".equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN))) {
								Info emailInfo = new Info();
								String[] idFieldForEmail = {"fimCampaignEmailId"};
								emailInfo.setIDField(idFieldForEmail);
								emailInfo.set("tableName", tableName);
								emailInfo.set("emailColumnName", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
								emailInfo.set("tableAnchor",tableAnchor);
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().delete("fimCampaignEmailCampaign", emailInfo);
							}
							if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME))) {
								if(!"true".equals(isTabularSection)){
								BuilderFormDAO.updateSummaryDisplayColumnName(fldName, fld.getDbField(), builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
								}
								if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
									BuilderFormDAO.updateTabularSectionDisplayColumns(null,fldName, fld.getDbField(), builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
									}//P_Enh_FormBuilder_Tabular_Section ends
							}
						}
						if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld) && StringUtil.isValidNew(asActive)) {
							SequenceMap paramMap = new SequenceMap();
							paramMap.put("emailColumnName", fld.getDbField());
							paramMap.put("tableName", tableName);
							paramMap.put("tableAnchor",tableAnchor);
							Info emailInfo = new Info();
							String[] idFieldForEmail = {"fimCampaignEmailId"};
							emailInfo.setIDField(idFieldForEmail);
							if(isActive) {
								emailInfo.set("isActive", "Y");
							} else {
								emailInfo.set("isActive", "N");
							}
							//BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().modify("fimCampaignEmailCampaign", emailInfo, paramMap);
							new FormBaseDAO("fimCampaignEmailCampaign").modify("fimCampaignEmailCampaign", emailInfo, paramMap);
						}
					} else {
						//P_Enh_Sync_Fields starts
						String[] idField = {"fieldId"};
						info.setIDField(idField);
						info.set("fieldId", "");
						info.set(BuilderFormFieldNames.BUILDER_FORM_ID, formId);
						info.set(BuilderFormFieldNames.FIELD_NAME, fldName);
						info.set(BuilderFormFieldNames.DISPLAY_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
						info.set(BuilderFormFieldNames.DATA_TYPE, "String");

						String syncField = fld.getSyncWithField();
						if(StringUtil.isValidNew(syncField)) {
							String table = syncField.split("##")[0];
							String otherField = syncField.split("##")[2];
							syncField = syncField.split("##")[1];
							FieldMappings fm = getFieldMappings(table);
							if(fm != null) {
								String tableNameP = fm.getTableName();
								Field fldP = fm.getField(syncField);
								if("true".equals(otherField)) {
									fldP = fm.getOtherTableField(syncField);
									FieldMappings fmOther = getFieldMappings(fldP.getTableAnchor());
									tableNameP = fmOther.getTableName();									
								}
								
								String dbFieldName = fldP.getDbField();
								String query = "SELECT "+dbFieldName+" FROM "+tableNameP+" LIMIT 0, 1";
								ResultSet result  = QueryUtil.getResult(query, null);
								ResultSetMetaData rsmd = result.getMetaData();
								info.set("existingColumnType", rsmd.getColumnTypeName(1));
								info.set("existingColumnSize", rsmd.getColumnDisplaySize(1));
								boolean b = rsmd.isSearchable(1);
							}
							//P_Enh_Sync_Fields ends	
						}
						info.set(BuilderFormFieldNames.IS_BUILD_FIELD, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD));
						info.set(BuilderFormFieldNames.ORDER_NO, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
						info.set(BuilderFormFieldNames.IS_ACTIVE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE));
						info.set(BuilderFormFieldNames.IS_MANDATORY, "false");
						info.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
						info.set(BuilderFormFieldNames.DB_COLUMN_SEQ, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_SEQ));
						info.set(BuilderFormFieldNames.DB_COLUMN_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE));
						info.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH));
						info.set(BuilderFormFieldNames.SECTION, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));
						info.set(BuilderFormFieldNames.SEARCHABLE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.SEARCHABLE));
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) {
							info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE));
							if("Email".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE))) {                           //BB-20150319-268(FIM Campaign Center) Starts
								info.set(FieldNames.INCLUDE_IN_CAMPAIGN, builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN));
							}
						}
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"))) {
							info.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"));
						}

						infoLogs.setIDField(idField);
						infoLogs.set("fieldId", "");
						infoLogs.set(BuilderFormFieldNames.BUILDER_FORM_ID, formId);
						infoLogs.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
						infoLogs.set(BuilderFormFieldNames.TABLE_NAME, tableName);
						String dateType = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE);

						String disVal = "Text";
						if(StringUtil.isValidNew(dateType)) {
							if("varchar".equals(dateType)) {
								disVal = BuilderConstants.TEXT_LBL;
							} else if("text".equals(dateType)) {
								disVal = BuilderConstants.TEXTAREA;
							} else if("Date".equals(dateType)) {
								disVal = BuilderConstants.DATE_LBL;
							} else if("int".equals(dateType)) {
								disVal = BuilderConstants.NUMBER;
							} else if("combo".equals(dateType)) {
								disVal = "Drop-down";
								if("2".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION)) || "1".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION))) {
									disVal = "State/Country";
								}
							} else if("radio".equals(dateType)) {
								disVal = BuilderConstants.RADIO;
							} else if("double".equals(dateType) || "numeric".equals(dateType)) {
								disVal = BuilderConstants.NUMERIC;
							} else if("checkbox".equals(dateType)) {
								disVal = BuilderConstants.CHECKBOX;
							} else if("file".equals(dateType)) {
								disVal = BuilderConstants.FILE_TYPE;
							} else if("multiselect".equals(dateType)) {
								disVal = "Multi Select Drop-down";
							}
						}
						infoLogs.set(BuilderFormFieldNames.DB_COLUMN_TYPE, disVal);
						infoLogs.set(BuilderFormFieldNames.FIELD_NAME, fldName);
						infoLogs.set(BuilderFormFieldNames.DISPLAY_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
						if(disVal.equals(BuilderConstants.TEXT_LBL)) {
							infoLogs.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE));
						} else if(disVal.equals(BuilderConstants.NUMERIC)) {
							infoLogs.set(BuilderFormFieldNames.FLD_VALIDATION_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE+"1"));
						}
						infoLogs.set(BuilderFormFieldNames.IS_BUILD_FIELD, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.IS_BUILD_FIELD));
						infoLogs.set(BuilderFormFieldNames.ORDER_NO, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO));
						infoLogs.set(BuilderFormFieldNames.IS_ACTIVE, "no");
						infoLogs.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
						if(StringUtil.isValidNew(dateType)) {
							if("varchar".equals(dateType)) {
								infoLogs.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH));
							} else if("text".equals(dateType)) {
								infoLogs.set(BuilderFormFieldNames.NO_OF_COLUMN, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN));
								infoLogs.set(BuilderFormFieldNames.NO_OF_ROW, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW));
							} else if("int".equals(dateType)) {
								infoLogs.set(BuilderFormFieldNames.DB_COLUMN_LENGTH, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_LENGTH));
							} else if("combo".equals(dateType)) {
								infoLogs.set(BuilderFormFieldNames.DROPDOWN_OPTION, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION));
							}
						}
						infoLogs.set(BuilderFormFieldNames.IS_MANDATORY, "false");
						infoLogs.set(BuilderFormFieldNames.SECTION, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.SECTION));
						infoLogs.set(BuilderFormFieldNames.EXPORTABLE, String.valueOf(fld.isFieldExportable()));
						infoLogs.set(BuilderFormFieldNames.MAIL_MERGE_KEYWORD, "$"+builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.MAIL_MERGE_KEYWORD)+"$");
						infoLogs.set(BuilderFormFieldNames.IS_PII_ENABLED, "false");
						infoLogs.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
						infoLogs.set(BuilderFormFieldNames.USER_NO, user_no);						infoLogs.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
						infoLogs.set(BuilderFormFieldNames.ACTION_TYPE, "Create");
						infoLogs.set(BuilderFormFieldNames.MODULE_NAME, moduleName);//ENH_MODULE_CUSTOM_TABS
						//String combooption = request.getParameter(BuilderFormFieldNames.DROPDOWN_OPTION);
						String combooption = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION);	

						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN)))
							info.set(BuilderFormFieldNames.NO_OF_COLUMN, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_COLUMN));
						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW)))
							info.set(BuilderFormFieldNames.NO_OF_ROW, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.NO_OF_ROW));
						info.set(BuilderFormFieldNames.TABLE_NAME, tableName);
						info.set("tableName1", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.TABLE_NAME));
						//String fileoption = request.getParameter(BuilderFormFieldNames.FILETYPE_OPTION);
						String fileoption = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FILETYPE_OPTION);
						int moduleID = 1;
						if("33".equals(formId)) {
							moduleID = 11;
						} else if("1".equals(formId)) {
							moduleID = 3;
						} else if("31".equals(formId)) {
							moduleID = 4;
						} else if("32".equals(formId)) {
							moduleID = 5;
						} else if("41".equals(formId)) {
							moduleID = 53;
						} else if("49".equals(formId) && MultiTenancyUtil.getTenantConstants().IS_LEAD_ENABLED) {
							moduleID = 54;
						}else if("64".equals(formId)) {			//Bug 73206 
							moduleID = 31;
						}else if("7".equals(formId)) {			 
							moduleID = 32;
						}else if("5".equals(formId)) {			 
							moduleID = 33;
						}
						
						String dVal = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME);
						String val = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME);

						String idquery="SELECT MAX(DISPLAY_ID) AS LAST_ID FROM SUMMARY_DISPLAY";
						ResultSet rs= null;
						rs = QueryUtil.getResult(idquery, null);
						int id1=0,orderSequence=0;//P_B_53807
						String query= "",id="";

						if(rs.next()) {
							String max_id=rs.getString("LAST_ID");
							id1=Integer.parseInt(max_id);
							id1++;
						}
						//P_Enh_FormBuilder_Tabular_Section starts
						int maxId=0,maxOrderSequence=0,configuredColumnCount=0,isSelected=0;
						if("true".equals(isTabularSection)){
						rs=null;
						idquery="SELECT MAX(DISPLAY_ID) AS LAST_ID FROM TABULAR_SECTION_DISPLAY_COLUMN";
						rs = QueryUtil.getResult(idquery, null);
						if(rs.next()) {
							String max_id=rs.getString("LAST_ID");
							maxId=Integer.parseInt(max_id);
							maxId++;
						}
						rs=null;
						idquery="SELECT MAX(ORDER_SEQUENCE) AS MAX_ORDER_SEQUENCE FROM TABULAR_SECTION_DISPLAY_COLUMN WHERE TABLE_NAME=?";
						rs = QueryUtil.getResult(idquery, new String[]{tableAnchor});
						if(rs.next()) {
							String max_id=rs.getString("MAX_ORDER_SEQUENCE");
							maxOrderSequence=Integer.parseInt(max_id);
							maxOrderSequence++;
						}
						
						rs=null;
						idquery="SELECT COUNT(DISPLAY_ID) AS CONFIGURED_COLUMN_COUNT FROM TABULAR_SECTION_DISPLAY_COLUMN WHERE TABLE_NAME=? AND IS_SELECTED=1 AND IS_ACTIVE='yes'";
						rs = QueryUtil.getResult(idquery, new String[]{tableAnchor});
						if(rs.next()) {
							String columnCount=rs.getString("CONFIGURED_COLUMN_COUNT");
							configuredColumnCount=Integer.parseInt(columnCount);
						}
						if(configuredColumnCount<6){
							isSelected=1;
						}
						}
						//P_Enh_FormBuilder_Tabular_Section ends
						
						StringBuffer orderQuery =  new StringBuffer();
						orderQuery.append("SELECT MAX(ORDER_SEQUENCE) AS MAX_ORDER_SEQUENCE FROM SUMMARY_DISPLAY WHERE MODULE_ID ='"+ moduleID+"'");
						rs=QueryUtil.getResult(orderQuery.toString(), null);
						if(rs.next()) {
							id=rs.getString("MAX_ORDER_SEQUENCE");
							orderSequence = Integer.parseInt(id);
							orderSequence++;
						}
						int updateResult =0 ;

						if(StringUtil.isValid(combooption) && ("1".equals(combooption) || "2".equals(combooption))) {
							info.set(BuilderFormFieldNames.DROPDOWN_OPTION, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DROPDOWN_OPTION));
							info.set(BuilderFormFieldNames.DB_COLUMN_NAME+"1", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME+"1"));
							info.set(BuilderFormFieldNames.DISPLAY_NAME+"1", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME+"1"));
							info.set(BuilderFormFieldNames.FIELD_NAME+"1", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME+"1"));
							String dVal1 = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME+"1");
							String val1 = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME+"1");
							String fldName1 = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME+"1");
							Info archiveInfo = (Info)info.clone();
							//String archiveTable = (String)request.getAttribute("archiveTable");
							String archiveTable = builderFormDao.getValueFromMap(dataMap, "archiveTable");
							if(StringUtil.isValid(archiveTable))
							{
								archiveInfo.set(BuilderFormFieldNames.TABLE_NAME, archiveTable);
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(archiveInfo);
							}
							boolean isDone = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(info);
							if(isDone) { //BB-20141017-177 startrs
								if("1".equals(combooption) && !("cm".equals(moduleName) && moduleID==1)) { //country only //P_CM_B_61925 
									if(!"true".equals(isTabularSection)){
									query = "INSERT INTO SUMMARY_DISPLAY VALUES("+id1+",?,?,'"+moduleID+"',0,'10%','No','false','','"+orderSequence +"',"+"'0',?,?,?,?,'false')"; // BB-20150525-360
									updateResult = QueryUtil.update(query, new String[]{dVal, val, fldName, dateType+"country", asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
									}
									if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
									}//P_Enh_FormBuilder_Tabular_Section ends
								} else if("2".equals(combooption) && !("cm".equals(moduleName) && moduleID==1)) { //country & state //P_CM_B_61925 
									if(!"true".equals(isTabularSection)){
										query = "INSERT INTO SUMMARY_DISPLAY VALUES("+id1+",?,?,'"+moduleID+"',0,'10%','No','false','','"+orderSequence +"',"+"'0',?,?,?, ?,'false')"; // BB-20150525-360
										updateResult = QueryUtil.update(query, new String[]{dVal, val, fldName, dateType+"country", asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
										query = "INSERT INTO SUMMARY_DISPLAY VALUES("+(id1+1)+",?,?,'"+moduleID+"',0,'10%','No','false','','"+(orderSequence+1)+"',"+"'0',?,?,?,?,'false')"; // BB-20150525-360
										updateResult = QueryUtil.update(query, new String[]{dVal1, val1, fldName1, dateType+"state", asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
									}
									
									if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+(maxId+1)+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal1, val1, asActive, fldName1}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
									}//P_Enh_FormBuilder_Tabular_Section ends
								}
							}
						}
						else {
							Info archiveInfo = (Info)info.clone();
							//String archiveTable = (String)request.getAttribute("archiveTable");
							String archiveTable = builderFormDao.getValueFromMap(dataMap, "archiveTable");
							if(StringUtil.isValid(archiveTable))
							{
								archiveInfo.set(BuilderFormFieldNames.TABLE_NAME, archiveTable);
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(archiveInfo);
							}
							boolean isDone = BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().alterTableData(info);
							if(StringUtil.isValid(moduleName) && "fs".equals(moduleName) && moduleID == 11) {
								if ("1".equals(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION)) || "2".equals(getValueFromMap(dataMap, BuilderFormFieldNames.SECTION))) {
									if (isDone) { //BB-20141017-177 starts
										if(!"true".equals(isTabularSection)){
										query = "INSERT INTO SUMMARY_DISPLAY VALUES(" + id1 + ",?,?,'" + moduleID + "',0,'10%','No','false','','" + orderSequence + "'," + "'0',?,?,?,?,'false')"; // BB-20150525-360
										updateResult = QueryUtil.update(query, new String[]{dVal, val, fldName, dateType, asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
										}
										if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
											query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
											updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
										}//P_Enh_FormBuilder_Tabular_Section ends
									}
								} else {
									if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
									}
								}
							} else {
								if(isDone && ( "cm".equals(moduleName) || "account".equals(moduleName) || ("opportunity".equals(moduleName) && MultiTenancyUtil.getTenantConstants().IS_OPPORTUNITY_ENABLED) || ("lead".equals(moduleName) && MultiTenancyUtil.getTenantConstants().IS_LEAD_ENABLED))){//P_CM_B_60533
									if(!"multiselect".equals(dateType) && !"checkbox".equals(dateType) && !"file".equals(dateType) && !"true".equals(isTabularSection) && ("31".equals(formId) || "32".equals(formId) || "41".equals(formId) || "49".equals(formId))){//P_CM_B_60918 
										
										query = "INSERT INTO SUMMARY_DISPLAY VALUES("+id1+",?,?,'"+moduleID+"',0,'10%','No','false','','"+orderSequence +"',"+"'0',?,?,?,?,'false')"; // BB-20150525-360
										updateResult = QueryUtil.update(query, new String[]{dVal, val, fldName, dateType, asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
									}
									if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
									}//P_Enh_FormBuilder_Tabular_Section ends
									if("cm".equals(moduleName) && "31".equals(formId) ){
										query = "INSERT INTO CM_EMAIL_FIELD_MAPPING ( FIELD_NAME,IS_CONFIGURED,IS_ACTIVE) VALUES ('"+fldName+"','Y','Y')"; 
										updateResult = QueryUtil.update(query, new String[]{});
									}
									if("lead".equals(moduleName) && "49".equals(formId) ){
										query = "INSERT INTO CM_LEAD_EMAIL_FIELD_MAPPING ( FIELD_NAME,IS_CONFIGURED,IS_ACTIVE) VALUES ('"+fldName+"','Y','Y')"; 
										updateResult = QueryUtil.update(query, new String[]{});
									}
								}else if(isDone){
									if(!"true".equals(isTabularSection)){
									query = "INSERT INTO SUMMARY_DISPLAY VALUES("+id1+",?,?,'"+moduleID+"',0,'10%','No','false','','"+orderSequence +"',"+"'0',?,?,?,?,'false')"; // BB-20150525-360
									updateResult = QueryUtil.update(query, new String[]{dVal, val, fldName, dateType, asActive, fldName}); //used to insert the record in SUMMARY_DISPLAY table
									}
									if("true".equals(isTabularSection)){//P_Enh_FormBuilder_Tabular_Section starts
										query = "INSERT INTO TABULAR_SECTION_DISPLAY_COLUMN VALUES("+maxId+",?,?,'"+moduleID+"',"+isSelected+",'"+maxOrderSequence +"',"+"'0',?,?,'false','"+tableAnchor+"','"+mainTableAnchor+"')"; 
										updateResult = QueryUtil.update(query, new String[]{dVal, val, asActive, fldName}); //used to insert the record in TABULAR_SECTION_DISPLAY_COLUMN table	
									}//P_Enh_FormBuilder_Tabular_Section ends
									if("cm".equals(moduleName) && "31".equals(formId) ){
										query = "INSERT INTO CM_EMAIL_FIELD_MAPPING ( FIELD_NAME,IS_CONFIGURED,IS_ACTIVE) VALUES ('"+fldName+"','Y','Y')"; 
										updateResult = QueryUtil.update(query, new String[]{});
										}
									if("lead".equals(moduleName) && "49".equals(formId) ){
										query = "INSERT INTO CM_LEAD_EMAIL_FIELD_MAPPING ( FIELD_NAME,IS_CONFIGURED,IS_ACTIVE) VALUES ('"+fldName+"','Y','Y')"; 
										updateResult = QueryUtil.update(query, new String[]{});
									}
								}
							}
						}
						//ZCUB-20160210-230 Start
						if("cm".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName) || "lead".equals(moduleName)){
							
							boolean isDependentField=StringUtil.isValid(fld.getDependentParentField());
							if(!"file".equals(dateType) && !isDependentField && ("31".equals(formId) || "32".equals(formId) || "41".equals(formId) || "49".equals(formId))){
								int idSeq=0;
								String transFormVal=null;
								if("radio".equals(dateType) || "checkbox".equals(dateType) || "combo".equals(dateType) || "multiselect".equals(dateType)){
									transFormVal="OPTION_VALUE###FIM_BUILDER_MASTER_DATA###OPTION_ID@@@FIELD_NAME="+fldName;
								}
								String disHeadrVal="31".equals(formId)?"Contacts":"32".equals(formId)?"Account":"41".equals(formId)?"Opportunities":"49".equals(formId)?"Leads":"";
								try{
									String filterQuery="SELECT MAX(CRITERIA_ID) AS LAST_ID FROM SMART_GROUP_CRITERIA_FIELD";
									ResultSet res= null;
									rs = QueryUtil.getResult(filterQuery, null);
									
									if(rs.next()) {
										String max_id=rs.getString("LAST_ID");
										idSeq=Integer.parseInt(max_id);
										idSeq++;
									}
								}catch(Exception error){
								}
								
								query = "INSERT INTO SMART_GROUP_CRITERIA_FIELD(CRITERIA_ID,MODULE_ID,FORM_ID,TABLE_ANCHOR,FORM_FIELD_NAME,HEADER_DISPLAY_NAME,IS_ACTIVE,ORDER_NO,FIELD_TRANSFORM_DETAILS) VALUES(?,?,?,?,?,?,?,?,?)";
								updateResult = QueryUtil.update(query, new String[]{String.valueOf(idSeq),"4",formId,info!=null?info.get("tableName1"):"",fldName,disHeadrVal,"Y",String.valueOf(idSeq),transFormVal});
							}
						}
						//ZCUB-20160210-230 Start End
						if("fim".equals(moduleName) && !"existing".equals(info.getString(BuilderFormFieldNames.DB_COLUMN_TYPE))){
							info.set("isTabularSection", isTabularSection);
							BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().insertIntoTriggerEvent(info);
						}

						BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("formBuilderFieldLogs", infoLogs);
						if(StringUtil.isValid(combooption) && ("2".equals(combooption))) {
							infoLogs.set(BuilderFormFieldNames.FIELD_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME+"1"));
							infoLogs.set(BuilderFormFieldNames.DISPLAY_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME+"1"));
							infoLogs.set(BuilderFormFieldNames.ORDER_NO, Integer.parseInt(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.ORDER_NO))+1);
							infoLogs.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME+"1"));
							infoLogs.set(BuilderFormFieldNames.MAIL_MERGE_KEYWORD, "$"+builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.MAIL_MERGE_KEYWORD+"1") + "$");
							infoLogs.set(BuilderFormFieldNames.MODULE_NAME, moduleName);//ENH_MODULE_CUSTOM_TABS
							BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("formBuilderFieldLogs", infoLogs);
						}

						if("0".equals(builderFormDao.getValueFromMap(dataMap, FieldNames.INCLUDE_IN_CAMPAIGN)) && "Text".equals(fld.getDisplayTypeField()) && "Email".equalsIgnoreCase(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)) )
						{
							Info emailInfo = new Info();
							String[] idFieldForEmail = {"fimCampaignEmailId"};
							emailInfo.setIDField(idFieldForEmail);
							emailInfo.set("tableName", tableName);
							emailInfo.set("emailColumnName", builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
							emailInfo.set("tableAnchor",tableAnchor);
							//emailInfo.set("isActive", "N");
							if(isActive) {
								emailInfo.set("isActive", "Y");
							} else {
								emailInfo.set("isActive", "N");
							}
							BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("fimCampaignEmailCampaign", emailInfo);
						}
					}
				} else { 
					if(isBuildFld) {
						String moduleID = "1";
						if("33".equals(formId)) {
							moduleID = "11";
						} else if("1".equals(formId)) {
							moduleID = "3";
						} else if("31".equals(formId)) {
							moduleID = "4";
						}else if("32".equals(formId)) {
							moduleID = "5";
						}else if("41".equals(formId)) {
							moduleID = "53";
						}else if("49".equals(formId)) {
							moduleID = "54";
						}else if("64".equals(formId)) {			//Bug 73206 
							moduleID = "31";
						}else if("7".equals(formId)) {			 
							moduleID = "32";
						}else if("5".equals(formId)) {			 
							moduleID = "33";
						}
						
						String[] idField = {"fieldId"};
						infoLogs.setIDField(idField);
						infoLogs.set("fieldId", "");
						infoLogs.set(BuilderFormFieldNames.BUILDER_FORM_ID, formId);
						infoLogs.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
						infoLogs.set(BuilderFormFieldNames.TABLE_NAME, tableName);
						infoLogs.set(BuilderFormFieldNames.FIELD_NAME, fldName);
						infoLogs.set(BuilderFormFieldNames.DISPLAY_NAME, fld.getDisplayName());
						infoLogs.set(BuilderFormFieldNames.IS_BUILD_FIELD, fld.isBuildField()?"yes":"no");
						infoLogs.set(BuilderFormFieldNames.ORDER_NO, fld.getOrderBy());
						infoLogs.set(BuilderFormFieldNames.DB_COLUMN_TYPE, fld.getDisplayTypeField());
						infoLogs.set(BuilderFormFieldNames.IS_ACTIVE, String.valueOf(fld.isActiveField()));
						infoLogs.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
						infoLogs.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
						infoLogs.set(BuilderFormFieldNames.MODULE_NAME, moduleName);
						infoLogs.set(BuilderFormFieldNames.ACTION_TYPE, "Modify");
						infoLogs.set(BuilderFormFieldNames.USER_NO, user_no);						infoLogs.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));
						infoLogs.set(BuilderFormFieldNames.EXPORTABLE, String.valueOf(fld.isFieldExportable()));
						BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("formBuilderFieldLogs", infoLogs);

						if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME)) && !FieldNames.CONTACT_FIRST_NAME.equals(fldName)) {//P_CM_B_71218
							BuilderFormDAO.updateSummaryDisplayColumnNameForBuildFields(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME), fldName, moduleID, asActive); //P_B_FIM_56998 //P_CM_B_58037
						}
						
						if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld) && StringUtil.isValidNew(asActive)) {
							if("franchisees".equals(tableAnchor) && ("centerName".equals(fldName) || "licenseNo".equals(fldName))) {
								SQLUtil.updateTableValue("MASTER_DATA", "DATA_VALUE", "N", "DATA_TYPE", "15206"); //updating the value of franchisee ID display format.
								MultiTenancyUtil.getTenantConstants().FRANCHISEE_ID_DISPLAY_NAME = "N";
							}
						}
						
					}
				}

				if(isBuildFld) {
					String sectionId = builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.SECTION);
					if(StringUtil.isValidNew(modifyFld) && !"yes".equals(modifyFld) && StringUtil.isValidNew(asActive)) {
						SequenceMap paramMap = new SequenceMap();
						if(isOtherField) {
							paramMap.put("emailColumnName", fld.getDbField()+"$"+sectionId);
						} else {
							paramMap.put("emailColumnName", fld.getDbField());
						}
						paramMap.put("tableName", tableName);
						paramMap.put("tableAnchor",tableAnchor);
						Info emailInfo = new Info();
						String[] idFieldForEmail = {"fimCampaignEmailId"};
						emailInfo.setIDField(idFieldForEmail);
						if(isActive) {
							emailInfo.set("isActive", "Y");
						} else {
							emailInfo.set("isActive", "N");
						}
						//BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().modify("fimCampaignEmailCampaign", emailInfo, paramMap);
						new FormBaseDAO("fimCampaignEmailCampaign").modify("fimCampaignEmailCampaign", emailInfo, paramMap);
					} else {
						if("Email".equals(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.FLD_VALIDATION_TYPE)) && "fim".equals(moduleName)) { //711-20160914-240							
							DBColumn[] dbColumn = null;
							Info emailInfo = new Info();
							String[] idFieldForEmail = {"fimCampaignEmailId"};
							emailInfo.setIDField(idFieldForEmail);
							emailInfo.set("tableName", tableName);
							dbColumn = new DBColumn[1];
							dbColumn[0] = new DBColumn();
							if(isOtherField) {
								Field[] flds1 = fieldMappings.getIdField();
								emailInfo.set("foreignIdMappingField",flds1[0].getDbField());
								String otherTableName = fld.getTableName();
								int emailOrder = Integer.parseInt(otherTableName.substring(otherTableName.indexOf("_")+1, otherTableName.length()));
								emailInfo.set("emailAddressOrder", emailOrder);
								dbColumn[0].setColDBName(fld.getDbField() + Constants.CAMPAIGN_TABLE_SUFFIX + "$" + sectionId);
								emailInfo.set("emailColumnName", fld.getDbField()+"$"+sectionId);
							} else {
								dbColumn[0].setColDBName(fld.getDbField() + Constants.CAMPAIGN_TABLE_SUFFIX);
								emailInfo.set("emailColumnName", fld.getDbField());
							}

							if(isActive) {
								emailInfo.set("isActive", "Y");
							} else {
								emailInfo.set("isActive", "N");
							}
							emailInfo.set("tableAnchor",tableAnchor);
							dbColumn[0].setAction(SQLQueryGenerator.MODIFY);
							dbColumn[0].setColType("varchar");
							dbColumn[0].setColTypeVal("255");
							if("1".equals(includeInCampaign)) {
								dbColumn[0].setColDefault("3");
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().delete("fimCampaignEmailCampaign", emailInfo);
							} else {
								dbColumn[0].setColDefault("0");
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().create("fimCampaignEmailCampaign", emailInfo);
							}
							DBQuery dbQuery = new DBQuery(SQLQueryGenerator.ALTER, tableName, dbColumn);
							String queryStr= SQLQueryGenerator.getDdlQuery(dbQuery);
							int count = QueryUtil.alterDBTable(queryStr);
						}

					}
				}
				if(StringUtil.isValidNew(modifyFld) && "yes".equals(modifyFld)) {
					Info modifyInfo = new Info();
					if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME))) {
						modifyInfo.set(BuilderFormFieldNames.DISPLAY_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
					}if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME))) {
						modifyInfo.set(BuilderFormFieldNames.DB_COLUMN_NAME, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
					}if(StringUtil.isValidNew(builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE))) {
						modifyInfo.set(BuilderFormFieldNames.DB_COLUMN_TYPE, builderFormDao.getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_TYPE));
					}
					modifyInfo.set(BuilderFormFieldNames.TABLE_NAME, tableName);

					if("fim".equals(moduleName)){
						BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().modifyIntoTriggerEvent(modifyInfo);
					}

					if("FIM_CONTRACT".equals(tableName)){
						HeaderMap[] hMap = fieldMappings.getHeaderMap();
						Info info1=new Info();
						String tableNamea = "";
						for(HeaderMap h1:hMap) {
							HeaderField hFld = h1.getHeaderFields();
							DependentTable[] dTables = hFld.getDependentTables();
							for(DependentTable dTable:dTables) {
								tableNamea =dTable.getTableName();
								TableField tableFields[] =dTable.getTableFields();
								for (int j = 0; j < tableFields.length; j++) {
									TableField tableField = tableFields[j];
									if(tableField.getFieldMap().get("main-dependent-field")!=null && !"null".equals(tableField.getFieldMap().get("main-dependent-field")))
										info1.set(tableField.getFieldMap().get("main-dependent-field"),tableNamea);
								}
							}
						}
						if((StringUtil.isValid(fldName) && StringUtil.isValidNew(info1.getString(fldName))))
							if(info1.get(fldName).indexOf("_")!=-1)
								BuilderFormWebImpl.getInstance().getBuilderFormMgr().getBuilderFormDAO().modifyDocumentFieldValue(modifyInfo.getString(BuilderFormFieldNames.DISPLAY_NAME),info1.get(fldName).substring(info1.get(fldName).indexOf("_")+1));
					}
				}
				flag = true;
			} else {
				flag = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			fieldMappings = null;
			fld =null;
			if(flag) {
				copyXML(dataMap);
				if(tableAnchor.equals(TableAnchors.FRANCHISEES)) {
					DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEE);
				}
				if(tableAnchor.equals(TableAnchors.FRANCHISEE)) {
					DBUtil.getInstance().removeFieldMappings(TableAnchors.FRANCHISEES);
				}
				if(StringUtil.isValidNew(tableAnchorExport)){// P_B_73908
					DBUtil.getInstance().removeFieldMappings(tableAnchorExport);
				}
				DBUtil.getInstance().removeFieldMappings(tableAnchor);
				String combooption = (String)dataMap.get("dropdownoption");
				boolean isBuildField = Boolean.parseBoolean((String)dataMap.get(BuilderFormFieldNames.IS_BUILD_FIELD));
				String action = getValueFromMap(dataMap, "action");
				
				String updateQuery = (String)ParamResolver.getResolver().get("duplicateUpdateQuery");
				if(StringUtil.isValidNew(updateQuery)) {
					try {
						QueryUtil.update(updateQuery, null);
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if("add".equals(action)) {
					if(!isBuildField) {
						String dateType = (String)dataMap.get(BuilderFormFieldNames.DB_COLUMN_TYPE);
						if(!StringUtil.isValidNew(dateType)) {
                        	dateType = getValueFromMap(dataMap, "dataTypeModify");
                        }
						if((dateType.equals("combo") && combooption != null && ("0".equals(combooption) || "3".equals(combooption))) || dateType.equals("radio") || dateType.equals("checkbox") || dateType.equals("multiselect")) { //BB-20150203-259 (Dynamic Response based on parent field response)
							String[] optionName = (String[])dataMap.get("optionName");
							String fieldName = (String)dataMap.get(BuilderFormFieldNames.FIELD_NAME);
							String[] dependentFieldValues = (String[])dataMap.get("fieldDependentValueHidden");
							insertComboOptionsMap(fieldName, optionName, tableAnchor, null, dependentFieldValues);
						}
					}
				} else if("modify".equals(action)){
        			String dateType = (String)dataMap.get(BuilderFormFieldNames.DB_COLUMN_TYPE);
        			if(!StringUtil.isValidNew(dateType)) {
                    	dateType = getValueFromMap(dataMap, "dataTypeModify");
                    }
        			if(dateType != null && (dateType.equals("combo") || dateType.equals("radio") || dateType.equals("checkbox") || "multiselect".equals(dateType))) { //711-20160914-240
        				String[] statusValue = (String[])dataMap.get("optionActivatedOrdered");
        				String[] optionName = (String[])dataMap.get("optionName");
        				String[] optionValues=(String[])dataMap.get("optionValues");//Alleg-20160615-349
						String fieldName = (String)dataMap.get(BuilderFormFieldNames.FIELD_NAME);
						String[] dependentFieldValues = (String[])dataMap.get("fieldDependentValueHidden");//P_CM_B_59522
        				try {
							boolean flagDelete = deleteRadioOrComboOptions(fieldName, tableAnchor);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				insertComboOptionsMap(fieldName,optionName, tableAnchor,statusValue, dependentFieldValues,optionValues);//P_CM_B_59522 //Alleg-20160615-349
        			}  
                }
			}
		}
	}
    
    //HomeTeam-20160603-584 starts
    private void updateActiveSearchFields(FieldMappings fMapping, HashMap dataMap,
			String asActive) {
		// TODO Auto-generated method stub
    	try
    	{
    	String tableName =fMapping.getTableName();
    	String val = getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
    	String dbField = "";
    	if(fMapping.getField(val)==null) 
    	{
    		dbField = fMapping.getOtherTableField(val).getDbField();
		}
    	else
    	{
    		dbField = fMapping.getField(val).getDbField();
    	}
    	String isActive = getValueFromMap(dataMap, BuilderFormFieldNames.IS_ACTIVE);
    	
    	
    	if("FS_LEAD_DETAILS".equals(tableName) ||  "FRANCHISEE".equals(tableName) || "FIM_OWNERS".equals(tableName) || "FIM_EMPLOYEES".equals(tableName) || "AREA_INFO".equals(tableName) || "FIM_ENTITY_DETAIL".equals(tableName))
    	{
    		QueryUtil.update("UPDATE ACTIVE_SEARCH_FIELDS SET IS_ACTIVE=? WHERE TABLE_NAME=? AND DB_FIELD_NAME = ?", new String []{"true".equals(isActive)?"N":"Y",tableName,dbField});
    		int updated = QueryUtil.update("UPDATE SMART_SEARCH SET IS_ACTIVE=? WHERE TABLE_NAME=? AND FIELDS = ?", new String []{"true".equals(isActive)?"no":"yes",tableName,dbField});
    		if(updated>0)
    		{
    			MultiTenancyUtil.getTenantConstants().setSearchFieldMap();
    		}
    		
    		
    	}
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
	}
  //HomeTeam-20160603-584 ends
	public boolean checkColumnValueInputInTable(String tableAnchor, String fieldName) {
        return checkColumnValueInputInTable(tableAnchor, fieldName, null);
    }
    public boolean checkColumnValueInputInTable(String tableAnchor, String fieldName, HashMap hMap) {
        return checkColumnValueInputInTable(tableAnchor, fieldName, hMap,-1);
    }
    public boolean checkColumnValueInputInTable(String tableAnchor, String fieldName, HashMap hMap,int orderNo) {
        String query = "SELECT " + fieldName+ " FROM "+tableAnchor+" ";
        if(hMap != null) {
            query = "SELECT " + hMap.get("tab-column") + " FROM "+hMap.get("doc-table")+" WHERE " + hMap.get("tab-column") + "='"+ hMap.get("tab-name")+"'" ;
            if(orderNo >=0)
            {
                query=query+" AND "+hMap.get("doc-order")+"="+orderNo+" AND ("+hMap.get("doc-column")+" IS NOT NULL AND "+hMap.get("doc-column")+"!='')";
            }

        }

        ResultSet rs = QueryUtil.getResult(query,null);
        boolean flag = false;
        try {
            int count = 0;
            if(rs != null){
                while(rs.next()) {
                    if(hMap != null) {
                        String strVal = rs.getString((String)hMap.get("tab-column"));
                        if(isValidWithZeroORDecimal(strVal)) { //CURRENCY_FIELD_ISSUE
                            flag = true;
                            break;
                        }
                    } else {
                        String strVal = rs.getString(fieldName);
                        if(isValidWithZeroORDecimal(strVal)) { //CURRENCY_FIELD_ISSUE
                            flag = true;
                            break;
                        }
                    }
                }
            }
            return flag;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            QueryUtil.releaseResultSet(rs);
        }
    }

    public boolean checkColumnValueForCMFileField(String fieldName){//P_CM_B_60109 
    	
    	boolean flag = false;
    	
    	if(StringUtil.isValid(fieldName)){
    		String prefixId=fieldName.replace("cmDocuments_", "");
    		String query="SELECT DOCUMENT_ID FROM CM_DOCUMENTS WHERE FIELD_PREFIX="+prefixId;
    		ResultSet rs = QueryUtil.getResult(query,null);
    		if(rs.next()){
    			flag=true;
    		}
    	}
    		
    	return 	flag;
    }
    public boolean checkColumnValueInputInAuditTable(String tableAnchor, String fieldName) {
        String query = "SELECT AUDIT_ID FROM AUDITING WHERE TABLE_NAME='"+tableAnchor+"' AND FIELD_NAME='" + fieldName +"'";

        ResultSet rs = QueryUtil.getResult(query,null);

//		DBConnectionManager connectionManager = null;
//        Connection connection = null;
//        PreparedStatement psmt = null;
//        java.sql.ResultSet rs = null;
//        
        boolean flag = false;

        try {
//        	connectionManager = DBConnectionManager.getInstance();
//            connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
//            psmt = connection.prepareStatement(query);
//            rs = psmt.executeQuery();
            int count = 0;
            while(rs.next()) {
                String strVal = rs.getString("AUDIT_ID");
                if(StringUtil.isValidWithZero(strVal)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
//                rs.close();
//                psmt.close();
//				connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
//				rs = null;
//				psmt = null;
                QueryUtil.releaseResultSet(rs);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //PW_B_26809 Starts
    public boolean isDataExistForFimDocument(String fieldPrefix, boolean checkForData) {
        boolean flag = false;
        StringBuffer query = new StringBuffer("SELECT DOCUMENT_FIM_TITLE,DOCUMENT_FIM_ATTACHMENT FROM FIM_DOCUMENTS WHERE FIELD_PREFIX = ? ");
        if(checkForData)
            query.append(" AND (NULLIF(DOCUMENT_FIM_TITLE, '') IS NOT NULL OR  NULLIF(DOCUMENT_FIM_ATTACHMENT,'') IS NOT NULL)");

        ResultSet result = QueryUtil.getResult(query.toString(), new String[] {fieldPrefix});

        if(result != null && result.next()) {
            flag = true;
        }
        QueryUtil.releaseResultSet(result);
        return flag;
    }
    //PW_B_26809 Ends

    /**
     * signal-20150529-009
     */
    public boolean isDataExistForFsDocument(String fieldPrefix) {
    	boolean flag = false;
    	StringBuffer queryBuffer = new StringBuffer("SELECT DOCUMENT_TITLE,DOCUMENT_FS_ATTACHMENT FROM FS_DOCUMENTS WHERE FIELD_PREFIX = ? ");
    	queryBuffer.append(" AND (NULLIF(DOCUMENT_TITLE, '') IS NOT NULL OR  NULLIF(DOCUMENT_FS_ATTACHMENT,'') IS NOT NULL)");
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(queryBuffer.toString(), new String[] {fieldPrefix});
    		if(result != null && result.next()) {
                flag = true;
            }
    	} catch (Exception e) {
			// TODO: handle exception
		} finally {
			QueryUtil.releaseResultSet(result);
		}
    	return flag;
    }
    
    // START : CM_B_37561 : 05/05/2014 : Veerpal Singh
    public boolean isDataExistForCmDocument(String fieldPrefix, boolean checkForData) {
        boolean flag = false;
        StringBuffer query = new StringBuffer("SELECT DOCUMENT_TITLE,DOCUMENT_CM_ATTACHMENT FROM CM_DOCUMENTS WHERE FIELD_PREFIX = ? ");
        if(checkForData)
            query.append(" AND (NULLIF(DOCUMENT_TITLE, '') IS NOT NULL OR  NULLIF(DOCUMENT_CM_ATTACHMENT,'') IS NOT NULL)");

        ResultSet result = QueryUtil.getResult(query.toString(), new String[] {fieldPrefix});

        if(result != null && result.next()) {
            flag = true;
        }
        QueryUtil.releaseResultSet(result);
        return flag;
    }
    // END : CM_B_37561 : 05/05/2014 : Veerpal Singh

    /**
     * To add custom keywords in property file
     * @param dVal
     */
    public void addCustomKeyWordInPropertyFile(String[] dVal) {


        if(LanguageUtil.isI18nImplemented()) {

            SystemVariableManager manager =  com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getSysVarMgr();
            String baseDir = manager.getSystemVariable("base_url");


            //Info languageInfo = NewPortalUtils.getLanguageDataInfo();
            Info languageInfo = BaseUtils.getLanguageDataInfo();      //For Product_Seperation_BL By Amar Singh.
            String language="";
            if(languageInfo!= null) {
                for(int i=0;i<languageInfo.size();i++) {
                    language+=languageInfo.getKey(i)+",";
                }
            }

            if(StringUtil.isValid(language)) {
                language=language.substring(0,language.length()-1);
                String allLanguage[]=language.split(",");
                for(int i=0;i<allLanguage.length;i++)
                {
                	//MT_PHASE_III_I18n_CHANGES Starts
                	try
                	{
	                	File file=new File(baseDir
	    						+ "/src/tenants/"+MultiTenancyUtil.getTenantName()+"/classes/i18n/" + allLanguage[i] + "/i18n_customFields.properties");
	    				if(!file.getParentFile().exists())
	    					FileUtil.mkDirs(file.getParentFile().getPath());
	    				if(!file.exists())
	    					file.createNewFile();
	    				
	                    PropertiesIO pio = new PropertiesIO(file.getPath());
	                    for (int j = 0; j < dVal.length; j++) {
	                        pio.setProperty(dVal[j], dVal[j]);
	                    }
	                    pio.writeProperty();
                	}
                	catch (Exception e) {
					}
                	
                }
            }

            String localLanguage = UserLanguageLocal.getUserLanguage();
            Language _language =(Language) MultiTenancyUtil.getTenantContext().getAttribute("_language");
            for (int j = 0; j < dVal.length; j++) {
            	 if(("en").equals(localLanguage))
                 	_language.enProperties.setProperty(dVal[j],dVal[j]);
                 else if(("de").equals(localLanguage))
                 	_language.deProperties.setProperty(dVal[j],dVal[j]);
                 else if(("es").equals(localLanguage))
                 	_language.esProperties.setProperty(dVal[j],dVal[j]);
                 else if(("it").equals(localLanguage))
                 	_language.itProperties.setProperty(dVal[j],dVal[j]);
                 else if(("fr").equals(localLanguage))
                 	_language.frProperties.setProperty(dVal[j],dVal[j]);
                 else if(("pt").equals(localLanguage))
                 	_language.ptProperties.setProperty(dVal[j],dVal[j]);
                 else if(("test").equals(localLanguage))
                 	_language.testProperties.setProperty(dVal[j],dVal[j]);
                 else
                 	_language.enProperties.setProperty(dVal[j],dVal[j]);
             }

        }//MT_PHASE_III_I18n_CHANGES Ends
    }

    public SequenceMap getBuilderTabs() {
        String query = "SELECT * FROM MODULE_TAB_CONFIG WHERE IS_ACTIVE='Y' ";
        ResultSet result = QueryUtil.getResult(query,null);
        SequenceMap allTabsMap = new SequenceMap();
        SequenceMap tabMap = null;

        int i=0;
        if(result != null){
            while (result.next()) {
                i++;
                Info info = new Info();
                info.set(FieldNames.FORM_ID,result.getString("MODULE_TAB_ID"));
                info.set(FieldNames.NAME, result.getString("NAME"));
                info.set(FieldNames.IS_ACTIVE, result.getString("IS_ACTIVE"));
                info.set(FieldNames.MODULE, result.getString("MODULE"));
                info.set(FieldNames.LABEL_NAME, i+"");
                tabMap = (SequenceMap)allTabsMap.get(result.getString("MODULE"));
                if(tabMap == null)
                    tabMap = new SequenceMap();

                tabMap.put(result.getString("MODULE_TAB_ID"), info);
                allTabsMap.put(result.getString("MODULE"), tabMap);
            }
            result.release();
        }


        return allTabsMap;
    }

    /**
     * To add custom keywords in property file
     * @param dVal
     */
    public void addCustomKeyWordInPropertyFile(String dVal) {

        if(LanguageUtil.isI18nImplemented()) {

            SystemVariableManager manager =  com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getSysVarMgr();
            String baseDir = manager.getSystemVariable("base_url");

            //Info languageInfo = NewPortalUtils.getLanguageDataInfo();
            Info languageInfo = BaseUtils.getLanguageDataInfo();   //For Product_Seperation_BL By Amar Singh.
            String language="";
            if(languageInfo!= null) {
                for(int i=0;i<languageInfo.size();i++) {
                    language+=languageInfo.getKey(i)+",";
                }
            }

            if(StringUtil.isValid(language)) {
                language=language.substring(0,language.length()-1);
                String allLanguage[]=language.split(",");
                for(int i=0;i<allLanguage.length;i++)
                {
                	//MT_PHASE_III_I18n_CHANGES Starts
                	try
                	{
	                	File file=new File(baseDir
	    						+ "/src/tenants/"+MultiTenancyUtil.getTenantName()+"/classes/i18n/" + allLanguage[i] + "/i18n_customFields.properties");
	    				if(!file.getParentFile().exists())
	    					FileUtil.mkDirs(file.getParentFile().getPath());
	    				if(!file.exists())
	    					file.createNewFile();
	    				
	                    PropertiesIO pio = new PropertiesIO(file.getPath());
	                    pio.setProperty(dVal, dVal);
	                    pio.writeProperty();
                	}catch (Exception e) {
					}
                	
                }
            }

            String localLanguage = UserLanguageLocal.getUserLanguage();
            Language _language =(Language) MultiTenancyUtil.getTenantContext().getAttribute("_language");
            if(StringUtil.isValidNew(dVal)) {
            	if(("en").equals(localLanguage))
            		_language.enProperties.setProperty(dVal,dVal);
            	else if(("de").equals(localLanguage))
            		_language.deProperties.setProperty(dVal,dVal);
            	else if(("es").equals(localLanguage))
            		_language.esProperties.setProperty(dVal,dVal);
            	else if(("it").equals(localLanguage))
            		_language.itProperties.setProperty(dVal,dVal);
            	else if(("fr").equals(localLanguage))
            		_language.frProperties.setProperty(dVal,dVal);
            	else if(("pt").equals(localLanguage))
            		_language.ptProperties.setProperty(dVal,dVal);
            	else if(("test").equals(localLanguage))
            		_language.testProperties.setProperty(dVal,dVal);
            	else
            		_language.enProperties.setProperty(dVal,dVal);
            }
        }
        	//MT_PHASE_III_I18n_CHANGES Ends
    }

    /**
     * This will allow Builder to Add/Modify Module's Custom Tab/Form
     * @param request
     * @return
     */
    public boolean addModifyXmlTabData(HttpServletRequest request) {
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
//    		String secName = getRequestValue(request, BuilderFormFieldNames.SECTION_NAME);
//    		String secVal = getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE);
            String modify = getRequestValue(request, "action");
//    		String order = getRequestValue(request, BuilderFormFieldNames.ORDER);
//    		String dVal = getRequestValue(request, BuilderFormFieldNames.DISPLAY_NAME);
            Node headerTablesNode	= null;
            Info logInfo = new Info();

            if(StringUtil.isValidNew(fileLoc) && StringUtil.isValidNew(modify) && "modify".equals(modify)) {
                file = new File(fileLoc);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
//    					factory = DocumentBuilderFactory.newInstance();
//    					docBuilder = factory.newDocumentBuilder();
                        doc = docBuilder.parse(file);

//    					transfac = TransformerFactory.newInstance();
//    					trans = transfac.newTransformer();
                        String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if(!StringUtil.isValidNew(modify) || !"modify".equals(modify)) {
                loc		= (String)getTableMappings().get(BuilderConstants.BUILDER_TEMPLATE_ANCHOR);
                location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;

                file = new File(location);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
                        doc = docBuilder.parse(file);
                    } catch(Exception e) {
                        return false;
                    }
//    				doc = docBuilder.parse(file);
                } else {
                    return false;
                }
            } else {
                return false;
            }

            if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {

            } else {
//    			root = doc.getDocumentElement();
//    			NodeList list = doc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
//    			int listLength = list.getLength();
//    			if (listLength<1) { 
//    				return false;
//    			}
//    			else if (listLength>1) {
//    				return false;
//    			}
//    			headerTablesNode =  list.item(0);
//    			
//    			Element childElement = doc.createElement(TableXMLDAO.HEADER);
//    			childElement.setAttribute(TableXMLDAO.NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME));
//    			childElement.setAttribute(TableXMLDAO.VALUE, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
//    			childElement.setAttribute(TableXMLDAO.ORDER, getRequestValue(request, BuilderFormFieldNames.ORDER));
//    			
//    			childElement.appendChild(getElementNode(doc, TableXMLDAO.TYPE, getRequestValue(request, BuilderFormFieldNames.TYPE)));
//    			childElement.appendChild(getElementNode(doc, TableXMLDAO.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION)));
//    			childElement.appendChild(getElementNode(doc, TableXMLDAO.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION)));
//    			
//    			headerTablesNode.appendChild(childElement);
                //root.appendChild(childElement);
                /**
                 * Generating Logs 
                 */
//    			String[] idField = {"id"};
//    			logInfo.setIDField(idField);
//    			logInfo.set("id", "");
//    			logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
//    			logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR));
//    			logInfo.set(BuilderFormFieldNames.SECTION_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME));
//    			logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
//    			logInfo.set(BuilderFormFieldNames.ORDER_NO, getRequestValue(request, BuilderFormFieldNames.ORDER));
//    			logInfo.set(BuilderFormFieldNames.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION));
//    			logInfo.set(BuilderFormFieldNames.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION));
//				logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
//				logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
//				WebCacheBypass webCacheIP = new WebCacheBypass(request);
//				String userIP = webCacheIP.getRemoteAddr();
//				logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
//				logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Create");
            }

            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();

            result = new StreamResult(fileLoc);
            source = new DOMSource(doc);
            trans.transform(source, result);
//			addCustomKeyWordInPropertyFile(secVal);
            try {
                create("formBuilderSectionLogs", logInfo);
            } catch(Exception e) {
                Debug.print(e);
            }
        } catch(Exception e) {
            return false;
        } finally {
            file = null; loc = null; location = null;
            doc = null; root = null;
//	    	trans.clearParameters();
            //transfac = null; trans = null;
            QueryUtil.releaseResultSet(result);
            source = null;
        }
        return true;
    }
    /**
     * This will allow New Tab/Form Add/Update processes 
     * @param request
     * @return
     * @throws SQLException
     * @throws RecordNotFoundException
     */
    public boolean setBuilderFormAddOrUpdateTab(HttpServletRequest request) throws SQLException, RecordNotFoundException {
        boolean flag = false;
        Object baseBuilder = null;
        SequenceMap baseBuilder1;
        String key;
        Iterator it;
        Object ob;
        String formId = null;

        try {
            tableAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);
            if(tableAnchor == null) {
//				if(baseFormFactory == null) {
//					context = request.getSession().getServletContext();
//					super.init();
//				}
                baseBuilder1 = BaseFormFactory.getBaseFormFactoryInstance().getBuilderTableData(request, request.getSession().getServletContext());
                it = baseBuilder1.keys().iterator();
                if(it.hasNext()) {
                    key = (String)it.next();
                    ob = (Object)baseBuilder1.get(key);
                    if(ob instanceof Info) {
                        tableAnchor = ((Info) ob).get(BuilderFormFieldNames.TABLE_ANCHOR);
                        request.setAttribute(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
                    }
                }
            }
            loc		= (String)getTableMappings().get(tableAnchor);
            location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            if(StringUtil.isValidNew(location)) {
                request.setAttribute(BuilderFormFieldNames.FILE_LOCATION, location);
            }

            flag = addModifyXmlTabData(request);
            if(flag) {
                removeFieldMappings(tableAnchor);
                /**
                 * Create logs of action
                 */
//				Info logInfo = new Info();
//				String[] idField = {"id"};
//    			logInfo.setIDField(idField);
//    			logInfo.set("id", "");
//    			logInfo.set(BuilderFormFieldNames.BUILDER_FORM_ID, getRequestValue(request, "formID"));
//    			logInfo.set(BuilderFormFieldNames.TABLE_ANCHOR, tableAnchor);
//    			logInfo.set(BuilderFormFieldNames.FIELD_NAME, getRequestValue(request, BuilderFormFieldNames.FIELD_NAME));
//    			logInfo.set(BuilderFormFieldNames.DISPLAY_NAME, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE));
//    			logInfo.set(BuilderFormFieldNames.DOCUMENT_LABEL, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_LABEL));
//    			logInfo.set(BuilderFormFieldNames.DOCUMENT_OPTION, getRequestValue(request, BuilderFormFieldNames.DOCUMENT_OPTION));
//    			logInfo.set(BuilderFormFieldNames.ORDER_NO, getRequestValue(request, BuilderFormFieldNames.ORDER));
//    			logInfo.set(BuilderFormFieldNames.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION));
//    			String modify = getRequestValue(request, "action");
//    			if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {
//    				logInfo.set(BuilderFormFieldNames.DISPLAY_NAME+"Old", getRequestValue(request, BuilderFormFieldNames.DOCUMENT_TITLE+"_OLD"));
//    				logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Modify");
//    			} else {
//    				logInfo.set(BuilderFormFieldNames.DISPLAY_NAME+"Old", "");
//    				logInfo.set(BuilderFormFieldNames.ACTION_TYPE, "Create");
//    			}
//				logInfo.set(BuilderFormFieldNames.CREATION_DATE, DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
//				logInfo.set(BuilderFormFieldNames.USER_NO, (String)request.getSession().getAttribute("user_no"));
//				WebCacheBypass webCacheIP = new WebCacheBypass(request);
//				String userIP = webCacheIP.getRemoteAddr();
//				logInfo.set(BuilderFormFieldNames.IP_ADDRESS, userIP);
//				
//				try {
//					create("formBuilderDocumentLogs", logInfo);
//				} catch(Exception e) {
//					Debug.print(e);
//				}
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        } finally {
            tableAnchor = null;
            loc		= null;
            location = null;
        }
        return true;
    }
    /**
     * Allow adding/deleting anchor with file location in builder mapping for newly added Tab/Form
     * @param request
     * @return
     */
    public boolean processBuilderMappingXmlData(HttpServletRequest request) {
        try {
            String modify = getRequestValue(request, "action");
            if(!StringUtil.isValidNew(modify) || !"modify".equals(modify)) {
                location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + BuilderConstants.BUILDER_MAPPING_XML;

                file = new File(location);
                if(file.isFile()) {
                    this.initBDaoInstance();
                    try {
                        doc = docBuilder.parse(file);
                    } catch(Exception e) {
                        return false;
                    }
//    				doc = docBuilder.parse(file);
                } else {
                    return false;
                }
            }

            if(StringUtil.isValidNew(modify) && "modify".equals(modify)) {
                return false;
            } else if(StringUtil.isValidNew(modify) && "delete".equals(modify)) {
                NodeList list = doc.getElementsByTagName(TableXMLDAO.BUILDER_TAB_MAPPINGS);
                Node node = list.item(0);
                NodeList nodel = node.getChildNodes();
                String tableAnchorReq = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);
                if(StringUtil.isValid(tableAnchorReq)) {
                    for (int i = 0;i<nodel.getLength();i++) {
                        Node nodeA			= nodel.item(i);
                        String tableAnchor 	= XMLUtil.getAttributeValue(nodeA, TableXMLDAO.TABLE_ANCHOR);
                        if(tableAnchorReq.equals(tableAnchor)) {
                            node.removeChild(nodeA);
                            break;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                NodeList list = doc.getElementsByTagName(TableXMLDAO.BUILDER_TAB_MAPPINGS);
                Node node = list.item(0);

                Element childElement = doc.createElement(TableXMLDAO.BUILDER_TAB_MAPPING);
                childElement.setAttribute(TableXMLDAO.TABLE_ANCHOR, getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR));
                childElement.setAttribute(TableXMLDAO.FILELOCATION, getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION));

                node.appendChild(childElement);
            }

            doc.normalize();

//			transfac = TransformerFactory.newInstance();
//			trans = transfac.newTransformer();

            result = new StreamResult(location);
            source = new DOMSource(doc);
            trans.transform(source, result);
        } catch(Exception e) {
            return false;
        }/* catch(IOException e) {
			return false;
		} catch(ParserConfigurationException e) {
			return false;
		} catch(TransformerConfigurationException e) {
			return false;
		} catch(TransformerException e) {
			return false;
		} */finally {
            file = null; location = null;
            doc = null; root = null;
//	    	trans.clearParameters();
//	    	transfac = null; trans = null;
            result = null; source = null;
        }
        return true;
    }

    //ENH_MODULE_CUSTOM_TABS starts
	/*
	 * This method is used to generate tabs for modules and deal with its creation, deletion,modification, activation and deactivation.  
	 */
    public boolean processFormGeneratorTabData(HttpServletRequest request)
    {
        ResultSet tabResultSet	 = null;
        BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
        try
        {    String tabCount			=getRequestValue(request, "tabCount");	//BB-20150203-259(Builder for tab repositioning ) changes
            String tabAction 		 	= getRequestValue(request, BuilderFormFieldNames.TAB_ACTION);
            String moduleName			= getRequestValue(request, FieldNames.MODULE);
            String subModuleName		= getRequestValue(request, "submoduleName");//ENH_MODULE_CUSTOM_TABS
            String[] canViewRoles=  request.getParameterValues(BuilderFormFieldNames.CAN_VIEW_ROLES);  //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
            String[] canWriteRoles=  request.getParameterValues(BuilderFormFieldNames.CAN_WRITE_ROLES); //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
            if(!StringUtil.isValidNew(subModuleName)) { //PP_changes starts
                subModuleName		= getRequestValue(request, "subModuleName");
            } //PP_changes ends
            String tabDisplay 			= getRequestValue(request, BuilderFormFieldNames.TAB_DISPLAY_NAME);
            String tabFileName 		 	= null;
            String tabName 				= null;
            String tabFileLocation   	= null;
            File   tabPath 			 	= new File(_baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH);
            String tabModuleLocation 	= _baseConstants.XML_DIRECTORY+"tables/admin/"+BuilderConstants.TAB_MAPPING_XML;
            String xmlPath 				= _baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH;//ENH_MODULE_CUSTOM_TABS
            String subModule			= null;

            DBQuery tabQuery		 = null;
            DBColumn tabColumns[]	 = null;
            String tabTableName		 = null;
            Info tabInfo   			 = null;
            String randomId			 = null;
            Info moduleInfo = (Info)BuilderFormUtil.getModuleMap().get(handleSubModule(moduleName,subModuleName));//ENH_MODULE_CUSTOM_TABS
            if(StringUtil.isValidNew(tabAction) && "create".equals(tabAction))
            {
                //ENH_MODULE_CUSTOM_TABS starts
                String tabAnchor 			= getRequestValue(request, "tabAnchor");
                String tableName 			= getRequestValue(request, "tableName");
                if(tabAnchor == null)
                {
                    return false;
                }

                SequenceMap moduleMap = new SequenceMap();
                //ENH_MODULE_CUSTOM_TABS ends
                location 		= _baseConstants.XML_DIRECTORY+"tables/admin/"+ BuilderConstants.TEMPLATE_TAB_XML;
                //P_SCH_ENH_008 Starts
                if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(getRequestValue(request, "module")))
                {
                    location = _baseConstants.XML_DIRECTORY+"tables/admin/"+ BuilderConstants.TEMPLATE_TAB_XML_SCHEDULER;
                }
                //P_SCH_ENH_008 Ends
                randomId 		= IDGenerator.getNextKey();
                //ENH_MODULE_CUSTOM_TABS starts
                //tabFileName 	= moduleName+randomId+".xml";
                tabFileName 	= tabAnchor+randomId+".xml";
                //ENH_MODULE_CUSTOM_TABS ends
                tabFileLocation = tabPath+"/"+tabFileName;
                file 			= new File(location);


                if(file.isFile())
                {
                    this.initBDaoInstance();
                    try
                    {
                        doc = docBuilder.parse(file);
                        //modify table-name,table display name and id-field in templatetab.xml
                        Node firstChild 		= doc.getFirstChild();
                        Node tableNameNode 		= getNodeInChildren(firstChild, TableXMLDAO.TABLE_NAME);
                        Node tableDisplayNode 	= getNodeInChildren(firstChild, TableXMLDAO.TABLE_DISPLAY_NAME);
                        Node idFieldNode 		= getNodeInChildren(firstChild, TableXMLDAO.ID_FIELD);
                        String isActive 		= getRequestValue(request, BuilderFormFieldNames.TAB_IS_ACTIVE);
                        String isExportable		= getRequestValue(request, BuilderFormFieldNames.TAB_IS_EXPORTABLE);
                        String addMore		= getRequestValue(request, BuilderFormFieldNames.ADD_MORE);
                        String sectionName  = getRequestValue(request, BuilderFormFieldNames.SECTION_NAME); //PP_changes

                        //ENH_MODULE_CUSTOM_TABS starts
                        //tabTableName 			= moduleName.toUpperCase()+"_"+randomId;
                        tabTableName 			= "_"+tableName+"_"+randomId;
                        //ENH_MODULE_CUSTOM_TABS ends

                        tableNameNode.appendChild(doc.createTextNode(tabTableName));
                        tableDisplayNode.appendChild(doc.createTextNode(tabDisplay));
                        idFieldNode.appendChild(doc.createTextNode("idField"));

                        newTabFile = new StreamResult(tabFileLocation);

                        if(!tabPath.exists())
                        {
                            tabPath.mkdirs();
                        }

                        //Create a new xml for a New tab    					
                        source = new DOMSource(doc);
                        trans.transform(source, newTabFile);
                        file = null;

                        file = new File(tabModuleLocation);

                        if(file.isFile())
                        {
                            tabName = tabFileName.substring(0, tabFileName.indexOf(".xml"));
                            doc 	= docBuilder.parse(file);
                            //create backup of tabModules.xml file

                            String fileLocBackup = tabModuleLocation.substring(0, tabModuleLocation.indexOf(".xml"));
                            resultBackup = new StreamResult(fileLocBackup+"_backup.xml");
                            source = new DOMSource(doc);
                            trans.transform(source, resultBackup);

                            firstChild = doc.getFirstChild();
                            Element newTab = doc.createElement(BuilderFormFieldNames.MODULE_TAB);
                            //create new module-tab node and set attributes
                            setTagAttr(newTab, BuilderFormFieldNames.TAB_NAME,tabName);
                            setTagAttr(newTab, BuilderFormFieldNames.TAB_DISPLAY,tabDisplay);
                            setTagAttr(newTab, BuilderFormFieldNames.TAB_IS_ACTIVE,isActive);
                            setTagAttr(newTab, BuilderFormFieldNames.ADD_MORE,addMore);
                            setTagAttr(newTab, BuilderFormFieldNames.TAB_IS_EXPORTABLE,isExportable);
                            setTagAttr(newTab, BuilderFormFieldNames.TAB_ROW,"1");
                            setTagAttr(newTab, FieldNames.MODULE,moduleName);
                            setTagAttr(newTab, FieldNames.SUB_MODULE,subModuleName);//ENH_MODULE_CUSTOM_TABS
                            setTagAttr(newTab, BuilderFormFieldNames.DB_TABLE,tabTableName);
                            setTagAttr(newTab, BuilderFormFieldNames.TABLE_ANCHOR,tabName);
                            setTagAttr(newTab, BuilderFormFieldNames.BUILDER_FORM_ID,randomId);
                            setTagAttr(newTab, BuilderFormFieldNames.FILE_LOCATION,BuilderConstants.BUILDER_TABLE_PATH+tabFileName);
                            //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB starts
                            if("fs".equals(moduleName) || "fim".equals(moduleName) || "cm".equals(moduleName) || "lead".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName)){
                            	if(canViewRoles != null && canViewRoles.length>0){
                            		setTagAttr(newTab, BuilderFormFieldNames.CAN_VIEW_ROLES,StringUtil.arrayToString(canViewRoles));
                            	}
                            	if(canWriteRoles !=null && canWriteRoles.length >0){
                            		setTagAttr(newTab, BuilderFormFieldNames.CAN_WRITE_ROLES,StringUtil.arrayToString(canWriteRoles));
                            	}
                            }
                          //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB ends
                            if(StringUtil.isValidNew(sectionName)) { //PP_changes starts
                                setTagAttr(newTab, BuilderFormFieldNames.SECTION_VALUE, sectionName); //adding section name in tabModules.xml
                            } //PP_changes ends
                            if(StringUtil.isValidNew(tabCount)) { //BB-20150203-259(Builder for tab repositioning ) changes
                            	if("fs".equals(moduleName)){
                            		if(Integer.parseInt(tabCount)<12){
                            			tabCount=String.valueOf(Integer.parseInt(tabCount)+((12-Integer.parseInt(tabCount))+1));
                            		}
                            	}
                                setTagAttr(newTab, BuilderFormFieldNames.TAB_ORDER, tabCount); //adding tab order in tabModules.xml
                            }
                            tabInfo = new Info();
                            tabInfo.set(BuilderFormFieldNames.TAB_NAME, tabName);
                            tabInfo.set(BuilderFormFieldNames.TAB_DISPLAY,tabDisplay);

                            addOtherAttributes(moduleName,newTab,tabInfo);//add attributes module wise to new tab

                            firstChild.appendChild(newTab);

                            originalFile = new StreamResult(tabModuleLocation);
                            source = new DOMSource(doc);
                            trans.transform(source, originalFile);
                            doc.normalize();
                            //ENH_MODULE_CUSTOM_TABS starts

                            //Code for manipulating the template xml(templateTab.xml)
                            file = new File(xmlPath+tabFileName);
                            doc = docBuilder.parse(file);
                            resultBackup = new StreamResult(tabName+"_backup.xml");
                            source = new DOMSource(doc);
                            trans.transform(source, resultBackup);

                            //Code for adding condition based tags in template xml
                            firstChild = doc.getFirstChild();

                            //create foreign table starts
                            Element parentNode = doc.createElement(TableXMLDAO.FOREIGN_TABLES);

                            newTab = doc.createElement(TableXMLDAO.FOREIGN_TABLE);

                            //setting attribute for foreign table
                            if("area".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                            setTagAttr(newTab, TableXMLDAO.NAME,"areaDocuments");	
                            }else if("mu".equals(subModuleName)){
                            setTagAttr(newTab, TableXMLDAO.NAME,"fimMuDocuments");	
                            }else{//P_Enh_Mu-Entity_FormGenerator ends
                            setTagAttr(newTab, TableXMLDAO.NAME,moduleName+"Documents");
                            }
                            setTagAttr(newTab, TableXMLDAO.TABLE_EXPORT,"false");

                            Element childTab = doc.createElement(TableXMLDAO.LINK_FIELD);

                            //create link fields starts

                            setTagAttr(childTab, TableXMLDAO.THIS_FIELD,"idField");
                            if("area".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                            	setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,"areaTabPrimaryId");	
                            }else if("mu".equals(subModuleName)){
                            	setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,"fimMuTabPrimaryID");	
                            }else{
                            	setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,"tabPrimaryId");
                            }//P_Enh_Mu-Entity_FormGenerator ends

                            newTab.appendChild(childTab); //link field added

                            if(moduleInfo!=null)
                            {
                                childTab = doc.createElement(TableXMLDAO.LINK_FIELD);
                                if("area".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                setTagAttr(childTab, TableXMLDAO.THIS_FIELD,ModuleUtil.MODULE_KEY.KEY_FIM_AREA);	
                                }else{//P_Enh_Mu-Entity_FormGenerator ends
                                setTagAttr(childTab, TableXMLDAO.THIS_FIELD,moduleInfo.get("keyField"));
                                }
                                setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,moduleInfo.get("keyField"));
                                newTab.appendChild(childTab); //link field added
                            }

                            parentNode.appendChild(newTab); //foreign table added
                            firstChild.appendChild(parentNode); //foreign tables added to root node <table>
                            //create link fields, create foreign table ends

                            if(moduleInfo!=null)
                            {
                                Element mainField = doc.createElement(TableXMLDAO.FIELD);
                                mainField.setAttribute(TableXMLDAO.SUMMARY,"true");
                                mainField.appendChild(getElementNode(doc, TableXMLDAO.FIELD_NAME, moduleInfo.get(TableXMLDAO.FIELD_NAME)));
                                mainField.appendChild(getElementNode(doc, TableXMLDAO.DISPLAY_NAME, moduleInfo.get(TableXMLDAO.DISPLAY_NAME)));
                                mainField.appendChild(getElementNode(doc, TableXMLDAO.DB_FIELD, moduleInfo.get(TableXMLDAO.DB_FIELD)));
                                mainField.appendChild(getElementNode(doc, TableXMLDAO.DATA_TYPE, moduleInfo.get(TableXMLDAO.DATA_TYPE)));
                                firstChild.appendChild(mainField);
                            }

                            originalFile = new StreamResult(xmlPath+tabFileName);
                            source = new DOMSource(doc);
                            trans.transform(source, originalFile);
                            doc.normalize();
                            //ENH_MODULE_CUSTOM_TABS ends
                            //DB code after xml creation
                            //////////////////////////////

                            tabQuery	 = new DBQuery();
                            //P_SCH_ENH_008 Starts
                            int size=2;
                            if("scheduler".equals(moduleName) || "fim".equals(moduleName) || "fs".equals(moduleName)){ //P_FIM_Newest Record At Top
                                size=3;
                            }
                            tabColumns       = new DBColumn[size];
                            //tabColumns	 = new DBColumn[2];
                            //P_SCH_ENH_008 Ends

                            tabColumns[0] = new DBColumn("ID_FIELD");
                            tabColumns[0].setColPrimary(true);
                            tabColumns[0].setColType(SQLQueryGenerator.INT);
                            tabColumns[0].setColTypeVal("11");

                            //ENH_MODULE_CUSTOM_TABS starts
                            if(moduleInfo!=null)
                            {
                                tabColumns[1] = new DBColumn(moduleInfo.get(TableXMLDAO.DB_FIELD));
                                tabColumns[1].setColType(SQLQueryGenerator.INT);
                                tabColumns[1].setColTypeVal("11");
                                //P_SCH_ENH_008 Starts
                                if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(moduleName)){
                                    tabColumns[2] = new DBColumn("CONTACT_ID");
                                    tabColumns[2].setColType(SQLQueryGenerator.INT);
                                    tabColumns[2].setColTypeVal("11");
                                }
                                if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName)){ //P_FIM_Newest Record At Top starts
                                    tabColumns[2] = new DBColumn("CREATION_DATE");
                                    tabColumns[2].setColType("TIMESTAMP");
                                    tabColumns[2].setColTypeVal("null");
                                    tabColumns[2].setColDefault("CURRENT_TIMESTAMP");
                                }//P_FIM_Newest Record At Top ends
                                //P_SCH_ENH_008 Ends
                            }
                            //ENH_MODULE_CUSTOM_TABS ends

                            tabQuery.setTableName(tabTableName);
                            tabQuery.setDBColumns(tabColumns);
                            tabQuery.setDDLType(SQLQueryGenerator.CREATE);
                            QueryUtil.alterDBTable(SQLQueryGenerator.getDdlQuery(tabQuery));

                            //ENH_MODULE_CUSTOM_TABS starts
                            ///////////////////////////////////////////
                            //For Form Builder
                            ///////////////////////////////////////////
                            tabInfo = new Info();
                            tabInfo.set("BUILDER_FORM_ID",randomId);
                            tabInfo.set("FORM_NAME", tabDisplay);
                            tabInfo.set("FORM_ID", tabName);
                            tabInfo.set("TABLE_ANCHOR", tabName);
                            tabInfo.set("BUILDER_FIELD_NO", "0");
                            setTagAttr(childTab, TableXMLDAO.THIS_FIELD,"idField");
                            if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                            	tabInfo.set("MODULE", subModuleName);
                            }else{
                            	tabInfo.set("MODULE", moduleName);
                            }//P_Enh_Mu-Entity_FormGenerator ends
                            //TABLE FOR HELPING LOGS
                            SQLUtil.insertTableData(tabInfo,"TAB_BUILDER_BACKUP");
                            tabInfo.set("DISPLAY_NAME", tabDisplay);//BB-20150203-259(Builder for tab repositioning ) changes
                            tabInfo.set("IS_CUSTOM", "Y");
                            tabInfo.set("ADD_MORE", addMore); //P_Enh_Sync_Fields
                            if("captivate".equals(subModuleName)) { //PP_changes starts
                                tabInfo.set("CAPTIVATE_SECTION_ID", request.getParameter("sectionName"));
                            } //PP_changes ends
                            //BaseNewPortalUtils.insertTableData(tabInfo,"BUILDER_WEB_FORMS");//BB_Naming_Convention
                            SQLUtil.insertTableData(tabInfo,"BUILDER_WEB_FORMS");//BB_Naming_Convention   //For Product_Seperation_BL By Amar Singh.


                            //ENH_MODULE_CUSTOM_TABS ends

                            //PP_changes starts
                            if("captivate".equals(subModuleName)) {
                                String randomNo = IDGenerator.getNextKey();
                                tabInfo = new Info();
                                tabInfo.set("ACTIVITY_ID", randomNo);
                                tabInfo.set("ACTIVITY_NAME", tabDisplay);
                                tabInfo.set("DESCRIPTION", tabDisplay);
                                tabInfo.set("ACTIVITY_TYPE", "FORM");
                                tabInfo.set("FORM_ID", randomId);
                                tabInfo.set("IS_ACTIVE", isActive);
                                tabInfo.set("SECTION_ID", sectionName);
                                SQLUtil.insertTableData(tabInfo,"FRANCHISE_GRANTING_ACTIVITY");

                                tabInfo = new Info();
                                tabInfo.set("FROM_EMAIL", "support@franconnect.com");
                                tabInfo.set("SUBJECT", tabDisplay);
                                tabInfo.set("EMAIL_CONTENT", "Please complete your activity");
                                tabInfo.set("ACTIVITY_ID", randomNo);
                                SQLUtil.insertTableData(tabInfo,"CONFIGURE_EMAIL_REMINDER");
                            }
                            //PP_changes ends
                            //TABLE FOR HELPING LOGS
                            //BaseNewPortalUtils.insertTableData(tabInfo,"TAB_BUILDER_BACKUP");
                            //BaseUtils.insertTableData(tabInfo,"TAB_BUILDER_BACKUP");     //For Product_Seperation_BL By Amar Singh.

                            if("fim".equals(moduleName))
                            {
                                tabInfo = new Info();
                                tabInfo.set("CUSTOM_FORM_ID",randomId);
                                tabInfo.set("FORM_NAME",tabDisplay);
                                tabInfo.set("FORM_ID",tabTableName);
                                tabInfo.set("TABLE_ANCHOR",tabName);
                                tabInfo.set("CUSTOM_FIELD_NO",0);

                                //BaseNewPortalUtils.insertTableData(tabInfo,"FIM_CUSTOMIZATION_FORM");
                                SQLUtil.insertTableData(tabInfo,"FIM_CUSTOMIZATION_FORM");    //For Product_Seperation_BL By Amar Singh.

                                ///////////////////////////////////////////

                                ///////////////////////////////////////////
                                //For Triggers
                                ///////////////////////////////////////////
                                tabInfo = new Info();
                                tabInfo.set("TRIGGER_ID", randomId);
                                tabInfo.set("FORM_ID", randomId);
                                tabInfo.set("FORM_NAME", tabDisplay);
                                tabInfo.set("INTERNAL_NAME", tabDisplay);
                                tabInfo.set("PATH", "/moduleCustomTab,/addModuleCustomTab");

                                //BaseNewPortalUtils.insertTableData(tabInfo,"TRIGGER_CONFIG");
                                SQLUtil.insertTableData(tabInfo,"TRIGGER_CONFIG");   //For Product_Seperation_BL By Amar Singh.

                                tabInfo = new Info();
                                tabInfo.set("EVENT_ID", randomId);
                                tabInfo.set("TRIGGER_ID", randomId);
                                tabInfo.set("TRIGGER_ONOFF", "0");
                                tabInfo.set("TABLE_NAME","-1");
                                tabInfo.set("FIELD_NAME","-1");
                                tabInfo.set("EVENT","");
                                tabInfo.set("EMAIL_ID","");
                                tabInfo.set("USER_NO","0");
                                tabInfo.set("ALERT_MESSAGE","");
                                tabInfo.set("DAYS_PRIOR","0");
                                tabInfo.set("VALUE_TO_COMPARE","");
                                tabInfo.set("ACTUAL_DATA_TYPE","String");

                                //BaseNewPortalUtils.insertTableData(tabInfo,"TRIGGER_EVENT");
                                SQLUtil.insertTableData(tabInfo,"TRIGGER_EVENT");   //For Product_Seperation_BL By Amar Singh.
                                ///////////////////////////////////////////
                            }
                            ///////////////////////////////////////////
                            //For logs
                            tabInfo = new Info();
                            tabInfo.set("BUILDER_FORM_ID", randomId);
                            tabInfo.set("TAB_ID", tabName);
                            tabInfo.set("TAB_NAME", tabDisplay);
                            if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                            	tabInfo.set("MODULE", subModuleName);									
                            }else{
                            	tabInfo.set("MODULE", moduleName);
                            }//P_Enh_Mu-Entity_FormGenerator ends
                            //tabInfo.set("MODULE", moduleName);
                            tabInfo.set("ACTION", "Created");
                            tabInfo.set("DESCRIPTION", LanguageUtil.getString("New Tab created"));
                            //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                            tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                            tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                            WebCacheBypass webCacheIP = new WebCacheBypass(request);
                            String userIP = webCacheIP.getRemoteAddr();

                            tabInfo.set("IP_ADDRESS", userIP);

                            //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");

                            SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");    //For Product_Seperation_BL By Amar Singh.

                            //P_HeplDesk_Export_CustomTabs starts
                            String keyTable = moduleInfo.get("keyTable");
                            if(keyTable.equals(TableAnchors.FRANCHISEES)) {
                                keyTable = TableAnchors.FRANCHISEES;
                            } else if(keyTable.equals(TableAnchors.FRANCHISEE)) {
                                keyTable = TableAnchors.FRANCHISEES;
                            }else if(keyTable.equals(TableAnchors.AREA_INFO)) {//P_Enh_Mu-Entity_FormGenerator
                                keyTable = TableAnchors.AREAS;
                            }
                            removeFieldMappings(keyTable);
                            //P_HeplDesk_Export_CustomTabs ends
                        }
                        refreshCustomTabMappings();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        return false;
                    }
                    finally
                    {
                        docBuilder = null;
                    }
                }
                else
                {
                    return false;
                }
            }
            else if(StringUtil.isValidNew(tabAction) && ("modify".equals(tabAction) || "active".equals(tabAction) || "deactive".equals(tabAction) || "delete".equals(tabAction)))
            {
                tabName = getRequestValue(request, BuilderFormFieldNames.TAB_NAME);
                if(tabName == null)
                {
                    tabName = getRequestValue(request, "tabName");
                }
                file = new File(tabModuleLocation);

                if(file.isFile())
                {
                    this.initBDaoInstance();
                    try
                    {
                        doc = docBuilder.parse(file);

                        //create backup of tabModules.xml file
                        String fileLocBackup = tabModuleLocation.substring(0, tabModuleLocation.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup+"_backup.xml");
                        source = new DOMSource(doc);
                        trans.transform(source, resultBackup);

                        Node firstChild = doc.getFirstChild();
                        NodeList childNodes = firstChild.getChildNodes();
                        Node childNode = null;
                        String tabNameAttr = null;
                        String moduleNameAttr = null;

                        //Fetching form id

                        String selectQuery 	= "SELECT BUILDER_FORM_ID FROM BUILDER_WEB_FORMS WHERE TABLE_ANCHOR = ?";//BB_Naming_Convention
                        tabResultSet 		= QueryUtil.getResult(selectQuery, new String[]{tabName});

                        if(tabResultSet != null && tabResultSet.next())
                        {
                            randomId = tabResultSet.getString("BUILDER_FORM_ID");
                        }

                        if("N".equals(request.getParameter(FieldNames.IS_CUSTOM)) && "modify".equals(tabAction))
                        {
                            tabDisplay 				= getRequestValue(request, BuilderFormFieldNames.TAB_DISPLAY_NAME);
                            String oldTabDisplayVal = getRequestValue(request, "oldTabDisplayVal");
                            String originalDisplayName = getRequestValue(request, "originalDisplayName");
                            if("fim".equals(moduleName) || "mu".equals(moduleName) || "area".equals(moduleName))
                            {
                                tabInfo = new Info();
                                tabInfo.set("NAME", tabDisplay);
                                //tabInfo.set("INTERNAL_NAME",tabDisplay);  //Bug 60347
                                //BaseNewPortalUtils.updateTableData("USER_TAB_CONFIG", "NAME", oldTabDisplayVal, tabInfo);
                                if("fim".equals(moduleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                	//SQLUtil.updateTableData("USER_TAB_CONFIG", "SUB_MODULE!='area' AND NAME", oldTabDisplayVal, tabInfo);    //For Product_Seperation_BL By Amar Singh.
                                	SQLUtil.updateTableData("USER_TAB_CONFIG", "SUB_MODULE!='area' AND SUB_MODULE NOT LIKE '%mu%' AND NAME", oldTabDisplayVal, tabInfo);    //For Product_Seperation_BL By Amar Singh.				//g6-20160823-241 Udai Agarwal
                                }else if("mu".equals(moduleName)){
                                	SQLUtil.updateTableData("USER_TAB_CONFIG", "SUB_MODULE LIKE '%"+moduleName+"%' AND NAME", oldTabDisplayVal, tabInfo);    //For Product_Seperation_BL By Amar Singh.
                                }else{
                                	SQLUtil.updateTableData("USER_TAB_CONFIG", "SUB_MODULE='area' AND NAME", oldTabDisplayVal, tabInfo);    //For Product_Seperation_BL By Amar Singh.
                                }//P_Enh_Mu-Entity_FormGenerator ends
                                tabInfo = new Info();
                                tabInfo.set("FORM_NAME", tabDisplay);
                                SQLUtil.updateTableData("AUDITING", "FORM_NAME", oldTabDisplayVal,tabInfo);
                                
                            }
                            
                            /*
                             * Updating MAIN_TABS table so that,
                             *  it will available at the time of module startup.
                             */
                            if("fs".equals(moduleName)) {
                            	tabInfo = new Info();
                                tabInfo.set("DISPLAY_NAME", tabDisplay);
                                SQLUtil.updateTableData("MAIN_TABS", "DISPLAY_NAME", oldTabDisplayVal, tabInfo);
                            }

                            tabInfo = new Info();
                            tabInfo.set("FORM_NAME", tabDisplay);
                            //BaseNewPortalUtils.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention
                            SQLUtil.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention    //For Product_Seperation_BL By Amar Singh.

                            //BaseNewPortalUtils.updateTableData("TAB_BUILDER_BACKUP", "BUILDER_FORM_ID", randomId, tabInfo);
                            SQLUtil.updateTableData("TAB_BUILDER_BACKUP", "BUILDER_FORM_ID", randomId, tabInfo);    //For Product_Seperation_BL By Amar Singh.
                            
                            SQLUtil.updateTableData("FORM_BUILDER_KEYWORDS_CONFIGURATION", "FORM_ID", randomId, tabInfo);

                            /////For Logs////////////

                            tabInfo = new Info();
                            tabInfo.set("BUILDER_FORM_ID", randomId);
                            tabInfo.set("TAB_ID", tabName);
                            tabInfo.set("TAB_NAME", oldTabDisplayVal);
                            //tabInfo.set("MODULE", moduleName);
                            if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                            	tabInfo.set("MODULE", subModuleName);									
                            }else{
                            	tabInfo.set("MODULE", moduleName);
                            }//P_Enh_Mu-Entity_FormGenerator ends
                            tabInfo.set("ACTION", "Modified");

                            StringBuffer message	= new StringBuffer(200);

                            if(oldTabDisplayVal != null && !oldTabDisplayVal.equals(tabDisplay))
                            {
                                message.append(LanguageUtil.getString("Name changed from")).append(BuilderFormFieldNames.SPACE).append(oldTabDisplayVal).append(" to ").append(tabDisplay);
                            }

                            tabInfo.set("DESCRIPTION",message.toString());

                            //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                            tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                            tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                            WebCacheBypass webCacheIP = new WebCacheBypass(request);
                            String userIP = webCacheIP.getRemoteAddr();

                            tabInfo.set("IP_ADDRESS", userIP);

                            //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");
                            SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");    //For Product_Seperation_BL By Amar Singh.
                            //BB-20150203-259 starts(Tab Rename changes)
                            List<String> fimSubModuleList =null;//P_Enh_Mu-Entity_FormGenerator 
                            if("modify".equals(tabAction)){
                                String moduleNameModifyTab=null;
                                String tabModuleLocationModifyTab=null;
                                String userTabName=null;
                                String tabNameModify=null;
                                String tabDisplayNameModify=null;
                                String tempVal=null;
                                moduleNameModifyTab=getRequestValue(request, FieldNames.MODULE);
                                if(StringUtil.isValidNew(moduleName))
                                {
                                	if("mu".equals(moduleName) || "area".equals(moduleName) || "fim".equals(moduleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                		fimSubModuleList=new ArrayList<String>();
                                		if("mu".equals(moduleName)){
                                			fimSubModuleList.add("mu");
                                			fimSubModuleList.add("muEntity");
                                		}else if("fim".equals(moduleName) && ("Owners".equals(originalDisplayName) || "Customer Complaints".equals(originalDisplayName) || "Training".equals(originalDisplayName) || "Employees".equals(originalDisplayName))){// Bug 72651 starts
                                			fimSubModuleList.add("franchiseewithoutsc");
                                			fimSubModuleList.add("mu");
                                			fimSubModuleList.add("muEntity");
                                		}else if("fim".equals(moduleName) && ("Entity Detail".equals(originalDisplayName))){
                                			fimSubModuleList.add("franchiseewithoutsc");
                                			fimSubModuleList.add("muEntity");
                                		}else if("area".equals(moduleName)){// Bug 72651 ends
                                			fimSubModuleList.add("area");
                                		}else{
                                			fimSubModuleList.add("franchiseewithoutsc");
                                			fimSubModuleList.add("inDev"); //BUG_78346
                                		}
                                		tabModuleLocationModifyTab=_baseConstants.XML_DIRECTORY+"tabs/fimTabs.xml";
                                	}else{
                                		tabModuleLocationModifyTab=_baseConstants.XML_DIRECTORY+"tabs/"+moduleName+"Tabs.xml";
                                	}
                                }//P_Enh_Mu-Entity_FormGenerator ends
                                file = new File(tabModuleLocationModifyTab);
                                if(file.isFile())
                                {
                                    this.initBDaoInstance();
                                    try
                                    {
                                        doc = docBuilder.parse(file);
                                        String fileLocBackupModify = tabModuleLocationModifyTab.substring(0, tabModuleLocationModifyTab.indexOf(".xml"));
                                        resultBackup = new StreamResult(fileLocBackupModify+"_backup.xml");
                                        source = new DOMSource(doc);
                                        trans.transform(source, resultBackup);
                                        NodeList hNodeList = doc.getElementsByTagName(TableXMLDAO.TAB_GROUP);
                                        Node headerTablesNode = hNodeList.item(0);
                                        if (headerTablesNode != null) {
                                            Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.TABS);
                                            for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                                            	String tabSubModuleName = getAttributeValue(headerNodes[loop], TableXMLDAO.TAB_SUB_MODULE);
                                          /*  	if("fim".equals(moduleName)&&"area".equals(tabSubModuleName)){
                                            		continue;
                                            	}else*/
                                            	//P_Enh_Mu-Entity_FormGenerator starts
                                            	if(fimSubModuleList!=null && !fimSubModuleList.contains(tabSubModuleName))
                                            	{
                                            		continue;
                                            	}//P_Enh_Mu-Entity_FormGenerator ends
                                            	if("fs".equals(moduleName)&&!"lead".equals(tabSubModuleName)){
                                            		continue;
                                            	}
                                                Node[] rowNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.ROW);
                                                for (int looptables=0; rowNodes != null && looptables < rowNodes.length; looptables++) {
                                                    Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(rowNodes[looptables], TableXMLDAO.TAB);
                                                    for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
                                                    	   tabNameModify = getAttributeValue(tablesFldNodes[looptablesfld], BuilderFormFieldNames.DISPLAY_NAME);
                                                           tabDisplayNameModify= getAttributeValue(tablesFldNodes[looptablesfld], BuilderFormFieldNames.TAB_DISPLAY_NAME);
                                                           
                                                           /*if(StringUtil.isValidNew(tabDisplayNameModify))
                                                               tempVal=tabDisplayNameModify;
                                                           else
                                                               tempVal=tabNameModify;*/
                                                           
                                                           //if(StringUtil.isValidNew(oldTabDisplayVal) && StringUtil.isValidNew(tabNameModify) && tempVal.equals(oldTabDisplayVal))
                                                           if(StringUtil.isValidNew(originalDisplayName) && StringUtil.isValidNew(tabNameModify) && tabNameModify.equals(originalDisplayName))
                                                           {
                                                               //if(tabDisplay != null )
                                                           	if(StringUtil.isValidNew(tabDisplay))
                                                               {
                                                                   setTagAttr(tablesFldNodes[looptablesfld], BuilderFormFieldNames.TAB_DISPLAY_NAME,tabDisplay);
                                                                 //P_FIM_B_60346 starts 
                                                                   tabInfo = new Info();
                                                                   tabInfo.set("FORM_NAME", tabDisplay);
                                                                   SQLUtil.updateTableData("TRIGGER_CONFIG","FORM_NAME",oldTabDisplayVal,tabInfo);
                                                                   //P_FIM_B_60346 ends
                                                               }
                                                           }
                                                    }
                                                }
                                            }
                                        }
                                        originalFile = new StreamResult(tabModuleLocationModifyTab);
                                        source = new DOMSource(doc);
                                        trans.transform(source, originalFile);
                                        doc.normalize();
                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                        return false;
                                    }
                                    finally
                                    {
                                        docBuilder = null;
                                    }
                                }
                            }
                            //BB-20150203-259 ends(Tab Rename changes)
                            return true;
                        }

                        moduleTabLoop:
                        for(int nodeIndex = 0;nodeIndex < childNodes.getLength();nodeIndex++)
                        {
                            childNode = childNodes.item(nodeIndex);
                            if(!BuilderFormFieldNames.MODULE_TAB.equals(childNode.getNodeName()))
                            {
                                continue;
                            }
                            tabNameAttr = getAttributeValue(childNode, BuilderFormFieldNames.TAB_NAME);
                            tabDisplay  = getAttributeValue(childNode, BuilderFormFieldNames.TAB_DISPLAY);
                            moduleNameAttr = getAttributeValue(childNode, FieldNames.MODULE);
                            if(tabName != null && moduleName != null && tabName.equals(tabNameAttr) && moduleName.equals(moduleNameAttr))
                            {
                                if("modify".equals(tabAction))
                                {
                                    tabDisplay 				= getRequestValue(request, BuilderFormFieldNames.TAB_DISPLAY_NAME);
                                    String isExportable		= getRequestValue(request, BuilderFormFieldNames.TAB_IS_EXPORTABLE);
                                    String oldTabDisplayVal	= getAttributeValue(childNode, BuilderFormFieldNames.TAB_DISPLAY);
                                    String oldTabExportVal	= getAttributeValue(childNode, BuilderFormFieldNames.TAB_IS_EXPORTABLE);
                                    if("fs".equals(moduleName) || "fim".equals(moduleName) || "cm".equals(moduleName) || "lead".equals(moduleName) || "account".equals(moduleName) || "opportunity".equals(moduleName)){
                                    	if(canViewRoles == null){
                                    		removeTagAttr(childNode, BuilderFormFieldNames.CAN_VIEW_ROLES);
                                    	}else{
                                    		setTagAttr(childNode, BuilderFormFieldNames.CAN_VIEW_ROLES,StringUtil.arrayToString(canViewRoles)); //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB starts
                                    	}
                                    	if(canWriteRoles ==null){
                                    		removeTagAttr(childNode, BuilderFormFieldNames.CAN_WRITE_ROLES);
                                    	}else{
                                    		setTagAttr(childNode, BuilderFormFieldNames.CAN_WRITE_ROLES,StringUtil.arrayToString(canWriteRoles));//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB ends

                                    	}
                                    }
                                    if(oldTabExportVal != null && !oldTabExportVal.equals(isExportable))
                                    {
                                        setTagAttr(childNode, BuilderFormFieldNames.TAB_IS_EXPORTABLE,isExportable);
                                    }

                                    if(oldTabDisplayVal != null && !oldTabDisplayVal.equals(tabDisplay))
                                    {
                                        setTagAttr(childNode, BuilderFormFieldNames.TAB_DISPLAY,tabDisplay);
                                        if("fim".equals(moduleName))
                                        {
                                            tabInfo = new Info();
                                            tabInfo.set("NAME", tabDisplay);
                                            tabInfo.set("INTERNAL_NAME",tabDisplay);
                                            //BaseNewPortalUtils.updateTableData("USER_TAB_CONFIG", "KEY_NAME", tabName, tabInfo); 
                                            SQLUtil.updateTableData("USER_TAB_CONFIG", "KEY_NAME", tabName, tabInfo);   //For Product_Seperation_BL By Amar Singh.

                                            tabInfo = new Info();
                                            tabInfo.set("FORM_NAME", tabDisplay);
                                            tabInfo.set("INTERNAL_NAME", tabDisplay);
                                            SQLUtil.updateTableData("TRIGGER_CONFIG","FORM_NAME",oldTabDisplayVal,tabInfo);

                                        }

                                        //if tab is deactive then no trigger will be fired
                                        if("N".equals(getAttributeValue(childNode, BuilderFormFieldNames.TAB_IS_ACTIVE)) && "fim".equals(moduleName))
                                        {
                                            tabInfo = new Info();
                                            tabInfo.set("FORM_NAME", tabDisplay);
                                            tabInfo.set("INTERNAL_NAME", tabDisplay);
                                            //BaseNewPortalUtils.updateTableData("TRIGGER_CONFIG", "FORM_ID", randomId, tabInfo);
                                            SQLUtil.updateTableData("TRIGGER_CONFIG", "FORM_ID", randomId, tabInfo);    //For Product_Seperation_BL By Amar Singh.
                                        }

                                        tabInfo = new Info();
                                        tabInfo.set("FORM_NAME", tabDisplay);
                                        SQLUtil.updateTableData("TAB_BUILDER_BACKUP", "BUILDER_FORM_ID", randomId, tabInfo);   //For Product_Seperation_BL By Amar Singh.

                                        //tabInfo.set("DISPLAY_NAME", tabDisplay);
                                        //BaseNewPortalUtils.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention
                                        SQLUtil.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention   //For Product_Seperation_BL By Amar Singh.

                                        //BaseNewPortalUtils.updateTableData("TAB_BUILDER_BACKUP", "BUILDER_FORM_ID", randomId, tabInfo);
                                        
                                        tabInfo = new Info();
                                        tabInfo.set("FORM_NAME", tabDisplay);
                                        SQLUtil.updateTableData("FORM_BUILDER_KEYWORDS_CONFIGURATION", "FORM_ID", randomId, tabInfo);

                                    }

                                    /////For Logs////////////
                                    tabInfo = new Info();
                                    tabInfo.set("BUILDER_FORM_ID", randomId);
                                    tabInfo.set("TAB_ID", tabName);
                                    tabInfo.set("TAB_NAME", tabDisplay);	//BUG_46185
                                  //tabInfo.set("MODULE", moduleName);
                                    if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                    	tabInfo.set("MODULE", subModuleName);									
                                    }else{
                                    	tabInfo.set("MODULE", moduleName);
                                    }//P_Enh_Mu-Entity_FormGenerator ends
                                    tabInfo.set("ACTION", "Modified");

                                    StringBuffer message	= new StringBuffer(200);

                                    if(oldTabDisplayVal != null && !oldTabDisplayVal.equals(tabDisplay))
                                    {
                                        message.append(LanguageUtil.getString("Name changed from")).append(BuilderFormFieldNames.SPACE).append(oldTabDisplayVal).append(" to ").append(tabDisplay);
                                    }

                                    if(oldTabExportVal != null && !oldTabExportVal.equals(isExportable))
                                    {
                                        if(message.length() > 0)
                                        {
                                            message.append(BuilderFormFieldNames.SPACE).append(LanguageUtil.getString("and")).append(BuilderFormFieldNames.SPACE);
                                        }
                                        message.append(LanguageUtil.getString("Exportable changed from")).append(BuilderFormFieldNames.SPACE).append(oldTabExportVal).append(BuilderFormFieldNames.SPACE).append("to").append(BuilderFormFieldNames.SPACE).append(isExportable);
                                    }

                                    tabInfo.set("DESCRIPTION",message.toString());

                                    //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                                    tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                                    tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                                    WebCacheBypass webCacheIP = new WebCacheBypass(request);
                                    String userIP = webCacheIP.getRemoteAddr();

                                    tabInfo.set("IP_ADDRESS", userIP);

                                    //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS"); 
                                    SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");   //For Product_Seperation_BL By Amar Singh.
                                    if("captivate".equals(subModuleName)) { //PP_changes starts(modify the activity name)
                                        Info tabInfoDisplayName = new Info();
                                        tabInfoDisplayName.set("ACTIVITY_NAME", tabDisplay);
                                        tabInfoDisplayName.set("DESCRIPTION", tabDisplay);
                                        SQLUtil.updateTableData("FRANCHISE_GRANTING_ACTIVITY", "FORM_ID", randomId, tabInfoDisplayName);
                                    } //PP_changes ends
                                }
                                else if("active".equals(tabAction))
                                {
                                    if("Y".equals(getAttributeValue(childNode, BuilderFormFieldNames.TAB_IS_ACTIVE)))
                                    {
                                        return false;
                                    }
                                    setTagAttr(childNode, BuilderFormFieldNames.TAB_IS_ACTIVE,"Y");

                                    tabInfo = new Info();
                                    tabInfo.set("IS_ACTIVE", "Y");
                                    //BaseNewPortalUtils.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);
                                    SQLUtil.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention  //For Product_Seperation_BL By Amar Singh.

                                    if("captivate".equals(subModuleName)) { //PP_changes starts
                                        tabInfo = new Info();
                                        tabInfo.set("IS_ACTIVE", "Y");
                                        SQLUtil.updateTableData("FRANCHISE_GRANTING_ACTIVITY", "FORM_ID", randomId, tabInfo);
                                    } //PP_changes ends

                                    if("fim".equals(moduleName))
                                    {
                                        ///////////////////////////////////////////
                                        //For Triggers
                                        ///////////////////////////////////////////

                                        tabInfo = new Info();
                                        tabInfo.set("TRIGGER_ID", randomId);
                                        tabInfo.set("FORM_ID", randomId);
                                        tabInfo.set("FORM_NAME", tabDisplay);
                                        tabInfo.set("PATH", "/moduleCustomTab,/addModuleCustomTab");

                                        //BaseNewPortalUtils.insertTableData(tabInfo,"TRIGGER_CONFIG"); 
                                        SQLUtil.insertTableData(tabInfo,"TRIGGER_CONFIG");   //For Product_Seperation_BL By Amar Singh.

                                        ///////////////////////////////////////////
                                    }

                                    //P_HeplDesk_Export_CustomTabs starts
                                    String keyTable = moduleInfo.get("keyTable");
                                    if(keyTable.equals(TableAnchors.FRANCHISEES)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    } else if(keyTable.equals(TableAnchors.FRANCHISEE)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    }else if(keyTable.equals(TableAnchors.AREA_INFO)) {//P_Enh_Mu-Entity_FormGenerator
                                        keyTable = TableAnchors.AREAS;
                                    }
                                    removeFieldMappings(keyTable);
                                    //P_HeplDesk_Export_CustomTabs ends

                                    refreshCustomTabMappings();

                                    ///////////////////////////////////////////
                                    //For logs

                                    tabInfo = new Info();
                                    tabInfo.set("BUILDER_FORM_ID", randomId);
                                    tabInfo.set("TAB_ID", tabName);
                                    tabInfo.set("TAB_NAME", tabDisplay);
                                    //tabInfo.set("MODULE", moduleName);
                                    if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                    	tabInfo.set("MODULE", subModuleName);									
                                    }else{
                                    	tabInfo.set("MODULE", moduleName);
                                    }//P_Enh_Mu-Entity_FormGenerator ends
                                    tabInfo.set("ACTION", "Activated");
                                    tabInfo.set("DESCRIPTION", LanguageUtil.getString("Tab activated"));
                                    //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                                    tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                                    tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                                    WebCacheBypass webCacheIP = new WebCacheBypass(request);
                                    String userIP = webCacheIP.getRemoteAddr();

                                    tabInfo.set("IP_ADDRESS", userIP);

                                    //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");   
                                    //Bug 53417 start
                                    if(!tabDisplay.equals("bQual")){
                                        SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");   //For Product_Seperation_BL By Amar Singh.
                                    }
                                    //Bug 53417 end
                                }
                                else if("deactive".equals(tabAction))
                                {
                                    if("N".equals(getAttributeValue(childNode, BuilderFormFieldNames.TAB_IS_ACTIVE)))
                                    {
                                        return false;
                                    }
                                    setTagAttr(childNode, BuilderFormFieldNames.TAB_IS_ACTIVE,"N");
                                    String tabContent = (String)request.getSession().getAttribute("tabContent");
                                    if(StringUtil.isValid(tabContent)){
                                        String tabContentDisplay = tabContent.substring(tabContent.indexOf("<span>")+6,tabContent.indexOf("</span>"));
                                        if(tabDisplay.equals(tabContentDisplay)){
                                            request.getSession().removeAttribute("tabContent");
                                        }
                                    }
                                    tabInfo = new Info();
                                    tabInfo.set("IS_ACTIVE", "N");
                                    //BaseNewPortalUtils.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention 
                                    SQLUtil.updateTableData("BUILDER_WEB_FORMS", "BUILDER_FORM_ID", randomId, tabInfo);//BB_Naming_Convention   //For Product_Seperation_BL By Amar Singh.

                                    if("captivate".equals(subModuleName)) { //PP_changes starts
                                        tabInfo = new Info();
                                        tabInfo.set("IS_ACTIVE", "N");
                                        SQLUtil.updateTableData("FRANCHISE_GRANTING_ACTIVITY", "FORM_ID", randomId, tabInfo);
                                    } //PP_changes ends

                                    if("fim".equals(moduleName))
                                    {
                                        //deletion for form generator data
                                        ///////////////////////////////////////////////

                                        tabInfo = new Info();
                                        tabInfo.set("FORM_ID", randomId);
                                        //BaseNewPortalUtils.deleteTableData("TRIGGER_CONFIG",tabInfo);
                                        SQLUtil.deleteTableData("TRIGGER_CONFIG",tabInfo);   //For Product_Seperation_BL By Amar Singh.
                                        /////////////////////////////////////////////// 
                                    }

                                    ///////////////////////////////////////////
                                    //For logs
                                    //P_HeplDesk_Export_CustomTabs starts
                                    String keyTable = moduleInfo.get("keyTable");
                                    if(keyTable.equals(TableAnchors.FRANCHISEES)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    } else if(keyTable.equals(TableAnchors.FRANCHISEE)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    }else if(keyTable.equals(TableAnchors.AREA_INFO)) {//P_Enh_Mu-Entity_FormGenerator
                                        keyTable = TableAnchors.AREAS;
                                    }
                                    removeFieldMappings(keyTable);
                                    //P_HeplDesk_Export_CustomTabs ends

                                    refreshCustomTabMappings();

                                    tabInfo = new Info();
                                    tabInfo.set("BUILDER_FORM_ID", randomId);
                                    tabInfo.set("TAB_NAME", tabDisplay);
                                    tabInfo.set("TAB_ID", tabName);
                                    //tabInfo.set("MODULE", moduleName);
                                    if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                    	tabInfo.set("MODULE", subModuleName);									
                                    }else{
                                    	tabInfo.set("MODULE", moduleName);
                                    }//P_Enh_Mu-Entity_FormGenerator ends
                                    tabInfo.set("ACTION", "Deactivated");
                                    tabInfo.set("DESCRIPTION", LanguageUtil.getString("Tab deactivated"));
                                    //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                                    tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                                    tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                                    WebCacheBypass webCacheIP = new WebCacheBypass(request);
                                    String userIP = webCacheIP.getRemoteAddr();

                                    tabInfo.set("IP_ADDRESS", userIP);

                                    //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");   
                                    //Bug 53417 Start
                                    if(!tabDisplay.equals("bQual")){
                                        SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");  //For Product_Seperation_BL By Amar Singh.
                                    }
                                    //Bug 53417 End
                                }
                                else if("delete".equals(tabAction))
                                {
                                    tabTableName	= getAttributeValue(childNode, BuilderFormFieldNames.DB_TABLE);
                                    tabFileLocation	= getAttributeValue(childNode, BuilderFormFieldNames.FILE_LOCATION);
                                    File tabFile 	= new File(_baseConstants.XML_DIRECTORY+"/"+tabFileLocation);
                                    if(tabFile.exists())
                                    {
                                        tabFile.delete();
                                    }

                                    firstChild.removeChild(childNode);

                                    //DB code after xml creation

                                    tabQuery	 = new DBQuery();

                                    tabQuery.setTableName(tabTableName);
                                    tabQuery.setDDLType(SQLQueryGenerator.DROP);

                                    QueryUtil.alterDBTable(SQLQueryGenerator.getDdlQuery(tabQuery));

                                    if("fim".equals(moduleName))
                                    {
                                        //deletion for form generator data
                                        ///////////////////////////////////////////////

                                        tabInfo = new Info();
                                        tabInfo.set("CUSTOM_FORM_ID", randomId);
                                        //BaseNewPortalUtils.deleteTableData("FIM_CUSTOMIZATION_FORM",tabInfo);   //For Product_Seperation_BL By Amar Singh.
                                        SQLUtil.deleteTableData("FIM_CUSTOMIZATION_FORM",tabInfo);

                                        tabInfo = new Info();
                                        tabInfo.set("FORM_ID", randomId);
                                        //BaseNewPortalUtils.deleteTableData("TRIGGER_CONFIG",tabInfo);
                                        SQLUtil.deleteTableData("TRIGGER_CONFIG",tabInfo);     //For Product_Seperation_BL By Amar Singh.
                                        tabInfo = new Info();
                                        tabInfo.set("TRIGGER_ID", randomId);
                                        //BaseNewPortalUtils.deleteTableData("TRIGGER_EVENT",tabInfo);
                                        SQLUtil.deleteTableData("TRIGGER_EVENT",tabInfo);  //For Product_Seperation_BL By Amar Singh.
                                    }
                                    tabInfo = new Info();
                                    tabInfo.set("FORM_ID", tabName);
                                    //BaseNewPortalUtils.deleteTableData("BUILDER_WEB_FORMS",tabInfo);//BB_Naming_Convention
                                    SQLUtil.deleteTableData("BUILDER_WEB_FORMS",tabInfo);//BB_Naming_Convention   //For Product_Seperation_BL By Amar Singh.

                                    if("captivate".equals(subModuleName)) { //PP_changes starts
                                        tabInfo = new Info();
                                        tabInfo.set("FORM_ID", randomId);
                                        SQLUtil.deleteTableData("FRANCHISE_GRANTING_ACTIVITY",tabInfo);
                                    } //PP_changes ends

                                    tabInfo = new Info();
                                    tabInfo.set("TABLE_ANCHOR", tabName);
                                    //BaseNewPortalUtils.deleteTableData("FIM_BUILDER_MASTER_DATA",tabInfo);
                                    SQLUtil.deleteTableData("FIM_BUILDER_MASTER_DATA",tabInfo);    //For Product_Seperation_BL By Amar Singh.
                                    ///////////////////////////////////////////////

                                    //P_HeplDesk_Export_CustomTabs starts
                                    String keyTable = moduleInfo.get("keyTable");
                                    if(keyTable.equals(TableAnchors.FRANCHISEES)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    } else if(keyTable.equals(TableAnchors.FRANCHISEE)) {
                                        keyTable = TableAnchors.FRANCHISEES;
                                    }else if(keyTable.equals(TableAnchors.AREA_INFO)) {//P_Enh_Mu-Entity_FormGenerator
                                        keyTable = TableAnchors.AREAS;
                                    }
                                    removeFieldMappings(keyTable);
                                    //P_HeplDesk_Export_CustomTabs ends
                                    refreshCustomTabMappings();

                                    ///////////////////////////////////////////
                                    //For logs

                                    tabInfo = new Info();
                                    tabInfo.set("BUILDER_FORM_ID", randomId);
                                    tabInfo.set("TAB_NAME", tabDisplay);
                                    tabInfo.set("TAB_ID", tabName);
                                    //tabInfo.set("MODULE", moduleName);
                                    if("area".equals(subModuleName) || "mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
                                    	tabInfo.set("MODULE", subModuleName);									
                                    }else{
                                    	tabInfo.set("MODULE", moduleName);
                                    }//P_Enh_Mu-Entity_FormGenerator ends
                                    tabInfo.set("ACTION", "Deleted");
                                    tabInfo.set("DESCRIPTION", LanguageUtil.getString("Tab deleted"));
                                    //tabInfo.set("CHANGE_DATE",  DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_DATETIME_FORMAT));
                                    tabInfo.set("CHANGE_DATE", DateUtil.formatDate(DateUtil.getDate(), DateUtil.DB_FORMAT_HMS));//BUG_48699
                                    tabInfo.set("USER_NO",  (String)request.getSession().getAttribute("user_no"));

                                    WebCacheBypass webCacheIP = new WebCacheBypass(request);
                                    String userIP = webCacheIP.getRemoteAddr();

                                    tabInfo.set("IP_ADDRESS", userIP);

                                    //BaseNewPortalUtils.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");
                                    SQLUtil.insertTableData(tabInfo,"FORM_BUILDER_TAB_LOGS");      //For Product_Seperation_BL By Amar Singh.

                                }

                                originalFile = new StreamResult(tabModuleLocation);
                                source = new DOMSource(doc);
                                trans.transform(source, originalFile);
                                doc.normalize();
                                break moduleTabLoop;


                            }
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        return false;
                    }
                    finally
                    {
                        docBuilder = null;
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            file = null; location = null;
            doc = null; root = null;
//	    	trans.clearParameters();
//	    	transfac = null; trans = null;
            QueryUtil.releaseResultSet(result);
            source = null;
            tabResultSet	 = null;
        }
        return true;
    }


    /**
     * This will process XML File with all its attribute
     * It required TableAnchor, XML Element and XML Key
     * TABLE ANCHOR FOR FILE TO PROCESS WHERE XML ELEMENT NAME USED FOR ELEMENT IN XML.
     * Input Key used to save all attributes with key attribute in Map with attribute key value as Map key  
     * @param requestInfo
     * @return
     */
    //BOEFLY_INTEGRATION : START
    public Object processBuilderXmlFileAttributes(Info requestInfo) {
        try {
            String tAnchor = requestInfo.get(BuilderFormFieldNames.TABLE_ANCHOR);
            String xmlElement = requestInfo.get(BuilderFormFieldNames.XML_ELEMENT);
            String xmlKey = requestInfo.get(BuilderFormFieldNames.XML_KEY);
            String loc		= (String)getTableMappings().get(tAnchor);
            String location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            //return new Object();
            return TableXMLDAO.getBuilderFileXMLAttr(location, xmlElement, xmlKey, requestInfo.get(BuilderFormFieldNames.KEY_TYPE));
        } catch(Exception e) {
            return null;
        }
    }
    //BOEFLY_INTEGRATION : END

    //ENH_MODULE_CUSTOM_TABS ends


    /**
     * This will process XML File with all its attribute
     * It required TableAnchor, XML Element and XML Key
     * TABLE ANCHOR FOR FILE TO PROCESS WHERE XML ELEMENT NAME USED FOR ELEMENT IN XML.
     * Input Key used to save all attributes with key attribute in Map with attribute key value as Map key  
     * @param request
     * @return
     */
    public Object processBuilderXmlFileAttributes(HttpServletRequest request) {
        try {
            String tAnchor = getRequestValue(request, BuilderFormFieldNames.TABLE_ANCHOR);
            String xmlElement = getRequestValue(request, BuilderFormFieldNames.XML_ELEMENT);
            String xmlKey = getRequestValue(request, BuilderFormFieldNames.XML_KEY);
            String loc		= (String)getTableMappings().get(tAnchor);
            String location = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + loc;
            //return new Object();
            return TableXMLDAO.getBuilderFileXMLAttr(location, xmlElement, xmlKey, getRequestValue(request, BuilderFormFieldNames.KEY_TYPE));
        } catch(Exception e) {
            return null;
        }
    }


    //ENH_MODULE_CUSTOM_TABS starts
	/*
	 * add attributes modulewise for custom tab
	 */
    public void addOtherAttributes(String module,Node node,Info tabInfo)
    {
        setTagAttr(node, BuilderFormFieldNames.HREF,"/moduleCustomTab");
        setTagAttr(node, BuilderFormFieldNames.PATH,"/moduleCustomTab,/addModuleCustomTab");
        setTagAttr(node, BuilderFormFieldNames.CONDITION,"dataPresent");
        setTagAttr(node, BuilderFormFieldNames.PRIVILEGE_URL,"/moduleCustomTab");
    }

    public List getAllExistingTabNames(String module)
    {
        ArrayList allNames = new ArrayList(20);
        StringBuffer moduleTabXmlLoc = new StringBuffer(MultiTenancyUtil.getTenantConstants().XML_DIRECTORY).append("/tabs/").append(module).append("Tabs.xml");
        this.initBDaoInstance();
        try
        {
            file = new File(moduleTabXmlLoc.toString());
            if(file.isFile())
            {
                doc = docBuilder.parse(file);

                //create backup of tabModules.xml file
                NodeList nodeList = doc.getElementsByTagName(BuilderFormFieldNames.TAB);
                for(int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++)
                {
                    allNames.add(getAttributeValue(nodeList.item(nodeIndex), BuilderFormFieldNames.DISPLAY_NAME));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return allNames;
    }

    public Map<String, String> getTabularFieldCountMap() {
    	return getTabularFieldCountMap("MAIN_TABLE_NAME");
    }
    public Map<String, String> getTabularFieldCountMap(String columnName) {
    	Map<String, String> tabularFieldCountMap = new HashMap<String, String>();
    	String query = "select "+columnName+", count(FIELD_NAME) as FIELD_COUNT from TABULAR_SECTION_DISPLAY_COLUMN group by "+columnName;
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(query, null);
    		while(result.next()) {
    			tabularFieldCountMap.put(result.getString(columnName), result.getString("FIELD_COUNT"));
    		}
    	} catch (Exception e) {
			// TODO: handle exception
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return tabularFieldCountMap;
    } 


    /*
	 * method to check whether the data is present in custom tab or not
	 */
    public boolean isTabDataPresent(String tableAnchor)
    {
        //P_SCH_B_49982 Starts
        return isTabDataPresent(tableAnchor, false);
    }
    public boolean isTabDataPresent(String tableAnchor,boolean isSchedulerCustomTab)
    {
        //P_SCH_B_49982 Ends
        boolean isPresent 		= false;

        FieldMappings fieldMappings	= getFieldMappings(tableAnchor);

        try
        {
            if(fieldMappings != null)
            {
                Field [] field		= fieldMappings.getAllFieldsArray();
                //P_SCH_B_49982 Starts
                int fieldLengthCheck=2;
                if(isSchedulerCustomTab){
                    fieldLengthCheck=3;
                }

                //if(field != null && field.length > 2)
                if(field != null && field.length >fieldLengthCheck)
                {
                    isPresent		= true;
                }
                //P_SCH_B_49982 Ends
            }
        }
        catch(Exception se)
        {
        }
        return isPresent;
    }

    public SequenceMap getAllTabs(String moduleName)
    {
        String query 			= null;
        ResultSet dataResult 	= null;
        SequenceMap tabMap		= null;
		UserRoleMap userRoleMap	= (UserRoleMap)StrutsUtil.getHttpServletRequest().getSession().getAttribute("userRoleMap");
    	if(moduleName.contains("fimarea")){
    		moduleName=moduleName.replaceAll("\\bfimarea\\b","area");
    	}
    	if(moduleName.contains("fimmu")){
    		moduleName=moduleName.replaceAll("\\bfimmu\\b","mu");
    	}
        moduleName = StringUtil.isValidNew(moduleName)?StringUtil.toCommaSeparatedWithSQuotes(moduleName.split(",")):"";
        try
        {
            query 		= "SELECT TBB.BUILDER_FORM_ID ID,TBB.FORM_NAME NAME,TBB.FORM_ID TAB_NAME,(CASE WHEN FBF.BUILDER_FORM_ID IS NULL THEN 'yes' ELSE 'no' END) AS IS_DELETED FROM TAB_BUILDER_BACKUP TBB  LEFT JOIN BUILDER_WEB_FORMS  FBF ON TBB.BUILDER_FORM_ID = FBF.BUILDER_FORM_ID ";//BB_Naming_Convention

            if(StringUtil.isValidNew(moduleName))
            {
                query 	+= " WHERE TBB.MODULE IN (" + moduleName + ")";   
            }
            else
            {
                moduleName="'scheduler'";
                if("yes".equals(MultiTenancyUtil.getTenantConstants().SITE_CLEARANCE_SETTING)) {
                	moduleName=moduleName+",'site'";
                }
                if(ModuleUtil.fimImplemented()){
                    moduleName=moduleName+",'fim'";
                }
                if(ModuleUtil.fsImplemented()){
                    moduleName=moduleName+",'fs'";
                }
                if(ModuleUtil.cmImplemented()){
                    moduleName=moduleName+",'cm'";
                  
                if(userRoleMap.isPrivilegeIDInMap("20881007") || userRoleMap.isPrivilegeIDInMap("20881008") || userRoleMap.isPrivilegeIDInMap("90881004"))
                    moduleName=moduleName+",'account'";
                
                if(MultiTenancyUtil.getTenantConstants().IS_LEAD_ENABLED) 
                	  moduleName=moduleName+",'lead'";
                
                if(MultiTenancyUtil.getTenantConstants().IS_OPPORTUNITY_ENABLED) 
              	  moduleName=moduleName+",'opportunity'";
              }
                
                
                query 	+= " WHERE TBB.MODULE IN (" + moduleName + ")";
            }


            dataResult 	= QueryUtil.getResult(query, null);

            Info info	= null;

            if(dataResult != null)
            {
                tabMap	= new SequenceMap();
                while(dataResult.next())
                {
                    info = new Info();
                    info.set("ID", dataResult.getString("ID"));
                    info.set("TAB_NAME", dataResult.getString("TAB_NAME"));
                    info.set("TAB_DISPLAY", dataResult.getString("NAME"));
                    info.set("IS_DELETED",dataResult.getString("IS_DELETED"));
                    tabMap.put(dataResult.getString("ID"),info);
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }

        return tabMap;
    }

    public SequenceMap getDeletedCustomTabs(String moduleName)
    {
        String query 			= null;
        ResultSet dataResult 	= null;
        SequenceMap tabMap		= null;
        if(moduleName.contains("fimarea")){
    		moduleName=moduleName.replaceAll("\\bfimarea\\b","area");
    	}
    	if(moduleName.contains("fimmu")){
    		moduleName=moduleName.replaceAll("\\bfimmu\\b","mu");
    	}
        moduleName=StringUtil.isValidNew(moduleName)?StringUtil.toCommaSeparatedWithSQuotes(moduleName.split(",")):"";
        try
        {
            query 		= "SELECT TBB.BUILDER_FORM_ID ID,TBB.FORM_NAME NAME,TBB.FORM_ID TAB_NAME FROM TAB_BUILDER_BACKUP TBB  LEFT JOIN BUILDER_WEB_FORMS  FBF ON TBB.BUILDER_FORM_ID = FBF.BUILDER_FORM_ID WHERE FBF.BUILDER_FORM_ID IS NULL";//BB_Naming_Convention

            if(StringUtil.isValidNew(moduleName))
            {
                query 	+= " AND TBB.MODULE IN (" + moduleName + ")";  
            }
            dataResult 	= QueryUtil.getResult(query, null);

            Info info	= null;

            if(dataResult != null)
            {
                tabMap	= new SequenceMap();
                while(dataResult.next())
                {
                    info = new Info();
                    info.set("TAB_NAME", dataResult.getString("TAB_NAME"));
                    info.set("TAB_DISPLAY", dataResult.getString("NAME"));
                    tabMap.put(dataResult.getString("ID"),info);
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }

        return tabMap;
    }
    public Info getSubmoduleList(String module)
    {
    	return getSubmoduleList(module,null);
    }
    public Info getSubmoduleList(String module,String subModuleName)
    {
        Info submoduleList = new Info();
        if(ModuleUtil.MODULE_NAME.NAME_FS.equals(module))
        {
            submoduleList.set("lead","Lead Management");
            //submoduleList.set("campaigns","Email Campaigns");
        }
        else if(ModuleUtil.MODULE_NAME.NAME_CM.equals(module))
        {
            submoduleList.set("cm","Contacts");
            //submoduleList.set("groups","Groups");
        }
        else if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(module))
        {
        	if("mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
        		submoduleList.set("mu","Multi-Unit");
        	}else if("area".equals(subModuleName)){
        		submoduleList.set("area","Regional");
        	}else{
        		submoduleList.set("franchisee","Franchisee");
        	}//P_Enh_Mu-Entity_FormGenerator ends
        }
        //P_SCH_ENH_008 Starts
        else if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(module))
        {
            submoduleList.set("jobs","Jobs");
        }//P_SCH_ENH_008 Ends
        else if("captivate".equals(module)) { ////PP_changes starts
            submoduleList.set("captivate","Virtual Brochure");
        } //PP_changes ends
        else if("account".equals(module)) { 
        	submoduleList.set("account","Account");
        }else if("opportunity".equals(module)) { 
        	submoduleList.set("opportunity","Opportunity");
        }else if("lead".equals(module)) { 
        	submoduleList.set("lead","Lead");
        }

        return submoduleList;
    }

    /**
     * This will give all the sections of captivate.
     * PP_changes
     * @author Naman Jain
     * @return
     */
    public Info getCaptivateSectionList() {
        Info sectionInfo = new Info();
        String query = "SELECT SECTION_NAME, SECTION_ID FROM FS_CAPTIVATE_SECTIONS WHERE SECTION_ID NOT IN (1,2) ORDER BY SECTION_ID ASC";
        ResultSet result = null;
        try {
            result = QueryUtil.getResult(query, null);
            while(result.next()) {
                sectionInfo.put(result.getString("SECTION_ID"), result.getString("SECTION_NAME"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            QueryUtil.releaseResultSet(result);
        }
        return sectionInfo;
    }

    public boolean isCustomTab(String tableAnchor)
    {
        boolean isCustom = false;
        ResultSet  dataResult = null;
        try
        {
            String query 		= "SELECT IS_CUSTOM FROM BUILDER_WEB_FORMS WHERE TABLE_ANCHOR=?";//BB_Naming_Convention

            dataResult 	= QueryUtil.getResult(query, new String[]{tableAnchor});

            if(dataResult != null && dataResult.next())
            {
                if("Y".equals(dataResult.getObject("IS_CUSTOM")))
                {
                    isCustom = true;
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }
        return isCustom;
    }

    public boolean isCustomForm(String formName,String moduleName)
    {
        boolean isCustom = false;
        ResultSet  dataResult = null;
        try
        {
            String query 		= "SELECT IS_CUSTOM FROM BUILDER_WEB_FORMS WHERE FORM_NAME=? AND MODULE=?";//BB_Naming_Convention

            dataResult 	= QueryUtil.getResult(query, new String[]{formName,moduleName});

            if(dataResult != null && dataResult.next())
            {
                if("Y".equals(dataResult.getObject("IS_CUSTOM")))
                {
                    isCustom = true;
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }
        return isCustom;
    }
    //BOEFLY_INTEGRATION : START
    public String handleSubModule(String moduleName,String subModuleName)
    {
        if("area".equals(subModuleName))
        {
            moduleName = moduleName + subModuleName;
        }else if("mu".equals(subModuleName) || "muEntity".equals(subModuleName))
        {
            moduleName = moduleName + "mu";
        }
        return moduleName;
    }
    //BOEFLY_INTEGRATION : END

    //ENH_MODULE_CUSTOM_TABS ends

    //CURRENCY_FIELD_ISSUE Strats
    public static boolean isValidWithZeroORDecimal(String s) {
        return (s != null && s.trim().length() != 0 && !s.trim().equalsIgnoreCase("null") && !s.trim().equals("-1") && !s.trim().equals("0") && !s.trim().equals("0.0") && !s.trim().equals("0.00"));
    }
    //CURRENCY_FIELD_ISSUE Strats

    /**
     * This Method will accept field name as parameter and return yes if the given field is used in custom report otherwise return no
     * @param String fieldName
     */

    public static String checkForFieldInSmartGroup(String fieldName, String moduleName) {
    	return checkForFieldInSmartGroup(fieldName,moduleName,null);//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE starts
    }
    public static String checkForFieldInSmartGroup(String fieldName, String moduleName,String stableName) {//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE ends
    	String isInSmartGrp = "no";
    	String tableName = "SMART_GROUP_CRITERIA_FIM";
    	if("fs".equals(moduleName) || "cm".equals(moduleName)) {
    		tableName = "SMART_GROUP_CRITERIA";
    	}
    	
    	String query = "SELECT COUNT(CRITERIA_ID) AS TOTAL_COUNT FROM "+tableName+" WHERE 1=1 AND FIELD_NAME=?";
    	//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE starts
    	if(StringUtil.isValidNew(stableName)){
    		query+=" AND TABLE_NAME ='"+stableName+"'";
    	}
    	//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE ends
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(query, new String[]{fieldName});
    		if(result != null && result.next()) {
    			String count = result.getString("TOTAL_COUNT");
    			if(StringUtil.isInt(count)) {
    				int countInt = Integer.parseInt(count);
    				if(countInt > 0) {
    					isInSmartGrp = "yes";
    				}
    			}
    		}
    	} catch (Exception e) {
			// TODO: handle exception
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return isInSmartGrp;
    }
    
    /*
     * To check whether filed is used in any workflow or not
     * */
    public static String checkForFieldInWorkFlow(String fieldName, String moduleName,String stableName)
    {
    	String isInWorkflow = "no";
    	String tableName = "CRM_WORK_FLOWS_CONDITIONS";
    	String query = "SELECT COUNT(CONDITION_ID) AS TOTAL_COUNT FROM "+tableName+" WHERE 1=1 AND FIELD_NAME=?";
    	if(StringUtil.isValidNew(stableName)){
    			query+=" AND TABLE_NAME ='"+stableName+"'";
    	}
    	ResultSet result = null;
    	try {
    		result = QueryUtil.getResult(query, new String[]{fieldName});
    		if(result != null && result.next()) {
    			String count = result.getString("TOTAL_COUNT");
    			if(StringUtil.isInt(count)) {
    				int countInt = Integer.parseInt(count);
    				if(countInt > 0) {
    						isInWorkflow = "yes";
    				}
    			}
    		}
    	} catch (Exception e) {
			// TODO: handle exception
		} finally {
			QueryUtil.releaseResultSet(result);
		}
		return isInWorkflow;
    }
    
    //BUG_42539_Starts
    public static String getFieldReportInfo(String comboName) {
        return getFieldReportInfo(comboName, null);
    }
    public static String getFieldReportInfo(String comboName, String moduleName)
    {
    	return getFieldReportInfo(comboName,moduleName, null);//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE starts
    }
    public static String getFieldReportInfo(String comboName, String moduleName,String tableName)
    {//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE ends
        String isInReport = "no";
        String associatedWithCustomReport = "no";
        String associatedWithSmartGroup = "no";
        StringBuffer query = new StringBuffer();
        ResultSet rs = null;//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
        try
        {
        	/*if(!StringUtil.isValid(tableName) || !"fs".equals(moduleName)){//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE		Commented for dki-20160906-582
            query = query.append("SELECT CUSTOM_REPORT_SELECT_FIELDS,CUSTOM_REPORT_WHERE_FIELDS FROM CUSTOM_REPORT ");
            rs = QueryUtil.getResult(query.toString(), new Object[]{});//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE
            List customReportSelectedFieldList=new ArrayList();
            List customReportWhereFieldAl=new ArrayList();
            while(rs.next())
            {
                String selectedFiledValue=rs.getString("CUSTOM_REPORT_SELECT_FIELDS");
                StringTokenizer st=new StringTokenizer(selectedFiledValue, "#####,");
                while(st.hasMoreTokens())
                    customReportSelectedFieldList.add(st.nextToken().trim());

                String selectedWhereValue=rs.getString("CUSTOM_REPORT_WHERE_FIELDS");
                st=new StringTokenizer(selectedWhereValue, "#####,");
                while(st.hasMoreTokens())
                    customReportWhereFieldAl.add(st.nextToken().trim());

                if(customReportSelectedFieldList.contains(comboName)) {
                    isInReport="yes";
                    associatedWithCustomReport= "yes";

                }
                if(customReportWhereFieldAl.contains(comboName)) {
                    isInReport="yes";
                    associatedWithCustomReport= "yes";
                }
            }
        	}else{//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE starts*/
        		query = query.append("SELECT CUSTOM_REPORT_SELECT_FIELDS_WITH_TABLES FROM CUSTOM_REPORT WHERE MENU_NAME='" + moduleName + "' AND CUSTOM_REPORT_SELECT_FIELDS_WITH_TABLES IS NOT NULL");		//dki-20160906-582 Udai Agarwal
        		rs = QueryUtil.getResult(query.toString(), new Object[]{});
        		Set customReportSelectedSet = new HashSet();

        		while(rs.next())
        		{
        			String selectedFiledValue=rs.getString("CUSTOM_REPORT_SELECT_FIELDS_WITH_TABLES");
        			StringTokenizer st=new StringTokenizer(selectedFiledValue, ",");
        			while(st.hasMoreTokens())
        				customReportSelectedSet.add(st.nextToken().trim());

        			if(customReportSelectedSet.contains(tableName+"#####"+comboName)) {
        				isInReport="yes";
        				associatedWithCustomReport= "yes";
        			}
        		}
        	//}//FORM_GENERATOR_FIELD_DEACTIVATE_ISSUE ends
            StringBuffer query1 = new StringBuffer();
            if("fs".equals(moduleName) && StringUtil.isValid(moduleName))
            query1 = query1.append("SELECT FIELD_NAME FROM SMART_GROUP_CRITERIA ");
            else if("fim".equals(moduleName) && StringUtil.isValid(moduleName))
                query1 = query1.append("SELECT FIELD_NAME FROM SMART_GROUP_CRITERIA_FIM ");
            rs = QueryUtil.getResult(query1.toString(), new Object[]{});
            List smartGroupAssociationList = new ArrayList();
            if(rs != null){
                while(rs.next()){
                    String fieldName = rs.getString("FIELD_NAME");
                    smartGroupAssociationList.add(fieldName);

                    if(smartGroupAssociationList.contains(comboName)){
                        isInReport = "yes";
                        associatedWithSmartGroup = "yes";
                    }
                }
            }
            //P_B_80437 Starts
            String isSmartQuestionField = getSmartQuestionField(comboName);
            if("yes".equals(isSmartQuestionField) && "yes".equals(associatedWithSmartGroup) && "yes".equals(associatedWithCustomReport)){
            	isInReport = "all";
            } else if("yes".equals(isSmartQuestionField) && "yes".equals(associatedWithSmartGroup)){
                isInReport = "smartQuestionGroup";
            } else if("yes".equals(isSmartQuestionField) && "yes".equals(associatedWithCustomReport)){
                isInReport = "smartQuestionReport";
            } else if("yes".equals(isSmartQuestionField)){
            	isInReport = "smartQuestion";
            } 
            //P_B_80437 Ends
            else if("yes".equals(associatedWithSmartGroup) && "yes".equals(associatedWithCustomReport)){
                isInReport = "both";
            }else if("yes".equals(associatedWithSmartGroup) && "yes".equals(isInReport)){
                isInReport = "true";
            } 
        }
        catch(Exception e)
        {
        }
        return isInReport;
    }

    //BUG_42539_Ends

    /**
     * This function is used to delete the custom field from SUMMARY_DISPLAY table
     * as these fields are deleted from the Admin section of the system.
     * BB-20141017-177
     * @author Naman Jain
     * @param fieldName
     * @param dbColumnName
     */
    public static void dropSummaryDisplayColumnData(String fieldName, String dbColumnName) {
        String deleteQuery = "DELETE FROM SUMMARY_DISPLAY WHERE CUSTOM_FIELD_NAME=? AND DISPLAY_VALUE=?";
        try {
            QueryUtil.update(deleteQuery, new String[]{fieldName,dbColumnName});
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * This function is used to modify the custom field Display Name from SUMMARY_DISPLAY table
     * as the field are modified from the Admin Section of the system.
     * BB-20141017-177
     * @author Naman Jain
     * @param fieldName
     * @param dbColumnName
     * @param displayName
     */
    public static void updateSummaryDisplayColumnName(String fieldName, String dbColumnName, String displayName) {
        String modifyQuery = "UPDATE SUMMARY_DISPLAY SET DISPLAY_NAME=? WHERE CUSTOM_FIELD_NAME=? AND DISPLAY_VALUE=?";
        try {
            QueryUtil.update(modifyQuery, new String[]{displayName, fieldName, dbColumnName});
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    //ZCUB-20160210-230 Satart
    public static void dropCmFilterColumnData(String fieldName) {
        String deleteQuery = "DELETE FROM SMART_GROUP_CRITERIA_FIELD WHERE FORM_FIELD_NAME=?";
        try {
            QueryUtil.update(deleteQuery, new String[]{fieldName});
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public static void updateIsCmFilterColumnDataSelected(String isActive, String fieldName) {
        String isActiveOrDeactive = "Y";
        if("true".equals(isActive)) {
            isActiveOrDeactive = "N";
        }
        String updateQuery = "UPDATE SMART_GROUP_CRITERIA_FIELD SET IS_ACTIVE=? WHERE FORM_FIELD_NAME=?";
        try {
            QueryUtil.update(updateQuery, new String[]{isActiveOrDeactive, fieldName});
        } catch (Exception e) {
        }
    }//ZCUB-20160210-230 End

    //P_B_FIM_56998
    public static void updateSummaryDisplayColumnNameForBuildFields(String displayName, String fieldName, String moduleID) {

        updateSummaryDisplayColumnNameForBuildFields(displayName, fieldName, moduleID, null);
    }
    public static void updateSummaryDisplayColumnNameForBuildFields(String displayName, String fieldName, String moduleID, String isActive) {//P_CM_B_58037 Method overloaded

        //P_CM_B_58037 Start
        String isActiveOrDeactive = null;
        String modifyQuery =null;

        
        if(StringUtil.isValid(isActive)){
            if("true".equals(isActive)) {
                isActiveOrDeactive = "no";
            }else{
                isActiveOrDeactive = "yes";
            }
            modifyQuery = "UPDATE SUMMARY_DISPLAY SET DISPLAY_NAME=?, IS_ACTIVE=? WHERE FIELD_NAME=? AND MODULE_ID=?";
            try {
                QueryUtil.update(modifyQuery, new String[]{displayName, isActiveOrDeactive, fieldName, moduleID});
            } catch (Exception e) {
                // TODO: handle exception
            }
        }else{
            modifyQuery = "UPDATE SUMMARY_DISPLAY SET DISPLAY_NAME=? WHERE FIELD_NAME=? AND MODULE_ID=?";
            try {
                QueryUtil.update(modifyQuery, new String[]{displayName, fieldName, moduleID});
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //P_CM_B_58037 Start End
    }
    
    /**
     * This function is used to update the selected status for the custom fields modified in the system.
     * BB-20141017-177
     * @author Naman Jain
     * @param isActive
     * @param fieldName
     * @param dbColumnName
     */
    public static void updateSummaryDisplayIsSelected(String isActive, String fieldName, String dbColumnName) {
        String isActiveOrDeactive = "yes";
        if("true".equals(isActive)) {
            isActiveOrDeactive = "no";
        }
        String updateQuery = "UPDATE SUMMARY_DISPLAY SET IS_ACTIVE=? WHERE CUSTOM_FIELD_NAME=? AND DISPLAY_VALUE=?";
        try {
            QueryUtil.update(updateQuery, new String[]{isActiveOrDeactive, fieldName, dbColumnName});
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    
    public void removeExistingTableXml(String tableName, String fieldName, HashMap dataMap, String childTableAnchor, String otherField, String isMand, String sourceField, String val) {
    	String parentFileLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get(tableName);
    	File parentFile = null;
    	Document parentDoc = null;
    	if(StringUtil.isValidNew(parentFileLoc)) {
    		parentFile = new File(parentFileLoc);
    		if(parentFile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				parentDoc = docBuilder.parse(parentFile);

    				String fileLocBackup = parentFileLoc.substring(0, parentFileLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");
    				source = new DOMSource(parentDoc); //this will create backup xml for parent table

    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    			}
    		}
    	}


    	try {
    		FieldMappings parentFieldMapping = getFieldMappings(childTableAnchor);
    		String parentTable = "";
    		if(parentFieldMapping != null) {
    			parentTable = parentFieldMapping.getTableName();
    		}

    		if("true".equals(otherField)) {
    			NodeList hNodeList = parentDoc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
    			Node headerTablesNode = hNodeList.item(0);


    			if (headerTablesNode != null) { //removing child fields for other table fields.
    				Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
    				for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
    					Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
    					for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
    						Node syncNode = getNodeInChildren(dependentTableNodes[looptables], TableXMLDAO.SYNC);
    						if(syncNode == null) {
    							continue;
    						} else {
    							Node[] syncChildNodes	=  XMLUtil.getNodesInChildren(syncNode, TableXMLDAO.SYNC_DATA);
    							if(syncChildNodes.length == 1) {
    								if(val.equals(getAttributeValue(syncChildNodes[0], TableXMLDAO.NAME))) {
    									dependentTableNodes[looptables].removeChild(syncNode);
    									break;
    								}
    							} else {
    								for (int looptablesDependent=0; syncChildNodes != null && looptablesDependent < syncChildNodes.length; looptablesDependent++) {
    									if(val.equals(getAttributeValue(syncChildNodes[looptablesDependent], TableXMLDAO.NAME))) {
    										syncNode.removeChild(syncChildNodes[looptablesDependent]);
    										break;
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		} else {
    			NodeList nodeList = parentDoc.getElementsByTagName(TableXMLDAO.FIELD);
    			for(int i=0; i<nodeList.getLength(); i++) {
    				Node tempNode = nodeList.item(i);
    				if(fieldName.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {
    					Node syncNode = getNodeInChildren(tempNode,TableXMLDAO.SYNC);
    					if(syncNode == null) {
    						continue;
    					} else {
    						Node[] syncChildNodes	=  XMLUtil.getNodesInChildren(syncNode, TableXMLDAO.SYNC_DATA);
    						if(syncChildNodes.length == 1) {
    							if(val.equals(getAttributeValue(syncChildNodes[0], TableXMLDAO.NAME))) {
    								tempNode.removeChild(syncNode);
    								break;
    							}
    						} else {
    							for (int looptables=0; syncChildNodes != null && looptables < syncChildNodes.length; looptables++) {
    								if(val.equals(getAttributeValue(syncChildNodes[looptables], TableXMLDAO.NAME))) {
    									syncNode.removeChild(syncChildNodes[looptables]);
    									break;
    								}
    							}
    						}
    					}
    				}
    			}
    		}

    		parentDoc.normalize();
    		result = new StreamResult(parentFileLoc);

    		source = new DOMSource(parentDoc);
    		trans.transform(source, result);

    	} catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
    	} finally {
    		removeFieldMappings(tableName);
    		parentDoc = null;
    		result = null;
    		source = null;
    	}	
    }
    
    /**
     * This function will update the XML for the parent field while creating a duplicate field across XML(s).
     * So that it data can be synced.
     * //P_Enh_Sync_Fields
     * @param tableName
     * @param fieldName
     * @param dataMap
     * @throws AppException 
     */
    public void updateExistingTableXml(String tableName, String fieldName, HashMap dataMap, String childTableAnchor, String otherField, String isMand, String sourceField, String childPii) throws AppException {
    	String parentFileLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get(tableName);
    	File parentFile = null;
    	Document parentDoc = null;
    	if(StringUtil.isValidNew(parentFileLoc)) {
    		parentFile = new File(parentFileLoc);
    		if(parentFile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				parentDoc = docBuilder.parse(parentFile);

    				String fileLocBackup = parentFileLoc.substring(0, parentFileLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");
    				source = new DOMSource(parentDoc); //this will create backup xml for parent table

    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    			}
    		}
    	}

    	String parentTable = "";
    	String moduleName = getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME);
    	try {
    		FieldMappings parentFieldMapping = getFieldMappings(childTableAnchor);
    		if(parentFieldMapping != null) {
    			parentTable = parentFieldMapping.getTableName();
    		}
    		
    		if("true".equals(otherField)) {
    			NodeList hNodeList = parentDoc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
    			Node headerTablesNode = hNodeList.item(0);

    			String parentHeaderName = "";
    			if (headerTablesNode != null) {
    				Node[] headerNodes	= XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
    				for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
    					Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
    					for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
    						String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
    						Node[] tablesFldNodes	=  XMLUtil.getNodesInChildren(dependentTableNodes[looptables], TableXMLDAO.TABLE_FIELD);
    						for (int looptablesfld=0; tablesFldNodes != null && looptablesfld < tablesFldNodes.length; looptablesfld++) {
    							String tFldNameVal = tNameVal + getAttributeValue(tablesFldNodes[looptablesfld], TableXMLDAO.NAME);
    							if(fieldName.equals(tFldNameVal)) {
    								parentHeaderName = tNameVal;
    								break;
    							}
    						}
    					}
    				}
    			}
    			
    			if (headerTablesNode != null) {
					Node[] headerNodes = XMLUtil.getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
					for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
						Node[] dependentTableNodes	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DEPENDENT_TABLE);
						for (int looptables=0; dependentTableNodes != null && looptables < dependentTableNodes.length; looptables++) {
							String tNameVal = getAttributeValue(dependentTableNodes[looptables], TableXMLDAO.NAME);
							if(parentHeaderName != null && parentHeaderName.equals(tNameVal)) {
								Node syncNode = getNodeInChildren(dependentTableNodes[looptables], TableXMLDAO.SYNC);
								if(syncNode == null) {
									Element dependentElement = parentDoc.createElement(TableXMLDAO.SYNC);
									Element dependentChildElement = parentDoc.createElement(TableXMLDAO.SYNC_DATA);
									
									dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
		    						dependentChildElement.setAttribute("table-name", parentTable);
		    						dependentChildElement.setAttribute("table-anchor", childTableAnchor);
		    						dependentChildElement.setAttribute("column-name", getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
		    						dependentChildElement.setAttribute("parent-field-name", fieldName);
		    						dependentChildElement.setAttribute("display-name", getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
		    						dependentChildElement.setAttribute(TableXMLDAO.SYNC_MODULE, moduleName);
		    						
									dependentElement.appendChild(dependentChildElement);
									dependentTableNodes[looptables].appendChild(dependentElement);
									
								} else {
									Element dependentChildElement = parentDoc.createElement(TableXMLDAO.SYNC_DATA);
									
									dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
		    						dependentChildElement.setAttribute("table-name", parentTable);
		    						dependentChildElement.setAttribute("table-anchor", childTableAnchor);
		    						dependentChildElement.setAttribute("column-name", getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
		    						dependentChildElement.setAttribute("parent-field-name", fieldName);
		    						dependentChildElement.setAttribute("display-name", getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
		    						dependentChildElement.setAttribute(TableXMLDAO.SYNC_MODULE, moduleName);
		    						
									syncNode.appendChild(dependentChildElement);
								}
							}
						}
					}
				}
    		} else {
    			NodeList nodeList = parentDoc.getElementsByTagName(TableXMLDAO.FIELD);
    			for(int i=0; i<nodeList.getLength(); i++) {
    				Node tempNode = nodeList.item(i);
    				if(fieldName.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {
    					if("true".equals(isMand))
    						setTagTextVal(parentDoc, tempNode, TableXMLDAO.IS_MANDATORY, "true");
    					
    					if("true".equals(sourceField))
    						setTagTextVal(parentDoc, tempNode, TableXMLDAO.SOURCE_FIELD, "true");
    					Node syncNode = getNodeInChildren(tempNode,TableXMLDAO.SYNC);

    					if(syncNode == null) { //if Sync node is not present
    						Element dependentElement = parentDoc.createElement(TableXMLDAO.SYNC);
    						Element dependentChildElement = parentDoc.createElement(TableXMLDAO.SYNC_DATA);
    						dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
    						dependentChildElement.setAttribute("table-name", parentTable);
    						dependentChildElement.setAttribute("table-anchor", childTableAnchor);
    						dependentChildElement.setAttribute("column-name", getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
    						dependentChildElement.setAttribute("parent-field-name", fieldName);
    						dependentChildElement.setAttribute("display-name", getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
    						dependentChildElement.setAttribute(TableXMLDAO.SYNC_MODULE, moduleName);
    						dependentElement.appendChild(dependentChildElement);
    						tempNode.appendChild(dependentElement);
    					} else { //if node is present and creating dependent-child node
    						Element dependentChildElement = parentDoc.createElement(TableXMLDAO.SYNC_DATA);
    						dependentChildElement.setAttribute("name", getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME));
    						dependentChildElement.setAttribute("table-name", parentTable);
    						dependentChildElement.setAttribute("table-anchor", childTableAnchor);
    						dependentChildElement.setAttribute("column-name", getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME));
    						dependentChildElement.setAttribute("parent-field-name", fieldName);
    						dependentChildElement.setAttribute("display-name", getValueFromMap(dataMap, BuilderFormFieldNames.DISPLAY_NAME));
    						dependentChildElement.setAttribute(TableXMLDAO.SYNC_MODULE, moduleName);
    						syncNode.appendChild(dependentChildElement);
    					}
    				}
    			}
    		}

    		parentDoc.normalize();
    		result = new StreamResult(parentFileLoc);

    		source = new DOMSource(parentDoc);
    		trans.transform(source, result);

    	} catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
    	} finally {
    		removeFieldMappings(tableName);
    		updateSyncColumnValues(tableName, fieldName, dataMap, childTableAnchor, otherField, parentTable, Boolean.parseBoolean(childPii));
    		
    		parentDoc = null;
    		result = null;
    		source = null;
    	}
    }
    
    public void updateSyncColumnValues(String tableName, String fieldName, HashMap dataMap, String childTableAnchor, String otherField, String childTable, boolean childPii) throws AppException {
    	String moduleName = getValueFromMap(dataMap, BuilderFormFieldNames.BUILDER_MODULE_NAME);
    	String childFieldName = getValueFromMap(dataMap, BuilderFormFieldNames.FIELD_NAME);
    	String moduleSpec = getValueFromMap(dataMap, "moduleSpecification");
    	String parentTableName = "";
    	FieldMappings parentFieldMappings = getFieldMappings(tableName);
    	if(parentFieldMappings != null) {
    		parentTableName = parentFieldMappings.getTableName();
    	}

    	String tableAlias = "P";
    	if(StringUtil.isValidNew(childTable) && StringUtil.isValidNew(parentTableName)) {
    		String childColumnName = getValueFromMap(dataMap, BuilderFormFieldNames.DB_COLUMN_NAME);
    		String parentColumnName = "";
    		boolean parentPiiEnabled = false;
    		boolean otherTableField = false; 
    		Field otherTableFieldObj = null;
    		String parentIdFieldDbColumn = "";
    		if(parentFieldMappings != null) {
    			HeaderMap[] hMap = parentFieldMappings.getHeaderMap();
    			parentIdFieldDbColumn = parentFieldMappings.getIdField()[0].getDbField();
    			for(HeaderMap h : hMap) {
    				HeaderField hFld = h.getHeaderFields();
    				if(hFld.isTabularSection()) {
    					continue;
    				}
    				if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
    					continue;
    				}

    				SequenceMap attrMap = hFld.getHeaderAttrMap();
    				if(hFld.getHeaderAttrMap().get("dependentModule") != null) {
    					String dependentModule = (String)attrMap.get("dependentModule");
    					String required = (String)attrMap.get("required");
    					String displayOnly = (String)attrMap.get("displayOnly");
    					if(StringUtil.isValidNew(required) && "true".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)) {
    						continue;
    					} else if(StringUtil.isValidNew(required) && "false".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) ) {
    						continue;
    					}
    				}

    				Field[] parentFieldArray = parentFieldMappings.getSectionTablesFieldsArray(hFld); //to get only active fields
    				if(parentFieldArray != null) {
    					for(Field parentField : parentFieldArray) {
    						if(parentField == null) {
    							continue;
    						}
    						if(fieldName.equals(parentField.getFieldName())) {
    							parentColumnName = parentField.getDbField();
    							parentPiiEnabled = parentField.isPiiEnabled();
    							otherTableField = parentField.isFieldOfOtherTable();
    							if(otherTableField) {
    								otherTableFieldObj = parentFieldMappings.getOtherTableField(parentField.getFieldName());
    							}
    							break;
    						}
    					}
    				}
    			}
    		}

    		String childIdFieldColumnName = "";
    		FieldMappings childFieldMappings = getFieldMappings(childTableAnchor);
    		if(childFieldMappings != null) {
    			childIdFieldColumnName = childFieldMappings.getIdField()[0].getDbField();
    		}

    		String updateQuery = "";
    		if("within".equals(moduleSpec)) { //withing same module
    			if("fs".equals(moduleName)) {
    				updateQuery = "UPDATE "+childTable+" C LEFT JOIN "+parentTableName+" P ON C.LEAD_ID = P.LEAD_ID";
    			} else {
    				if("FRANCHISEE".equals(childTable)) {
    					updateQuery = "UPDATE "+childTable+" C LEFT JOIN "+parentTableName+" P ON C.FRANCHISEE_NO = P.ENTITY_ID";
    				} else if("FRANCHISEE".equals(parentTableName)) {
    					updateQuery = "UPDATE "+childTable+" C LEFT JOIN "+parentTableName+" P ON C.ENTITY_ID = P.FRANCHISEE_NO";
    				} else {
    					updateQuery = "UPDATE "+childTable+" C LEFT JOIN "+parentTableName+" P ON C.ENTITY_ID = P.ENTITY_ID";
    				}

    				if(otherTableField) {
    					if(otherTableFieldObj != null) {
    						String tbleNme = otherTableFieldObj.getTableAnchor();
    						FieldMappings mappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tbleNme);
    						String otherTableName = mappings.getTableName();
    						if(StringUtil.isValidNew(otherTableName) && "ADDRESS".equals(otherTableName)) {
    							updateQuery = updateQuery + " LEFT JOIN "+otherTableName+" O ON O.FOREIGN_ID = P."+parentIdFieldDbColumn;
    							tableAlias = "O";
    						}
    					}
    				}
    			}
    		} else {
    			/*
    			 * When across module is FIM and main module is FS and vice versa.
    			 * In this case main primary key is LEAD_ID and it will map with FIM using franchiseeNo as mapping key and vice versa.
    			 */
    			if("fs".equals(moduleSpec)) {
    				if("FRANCHISEE".equals(childTable) && "FS_LEAD_DETAILS".equals(parentTableName)) { 
    					updateQuery = "UPDATE "+childTable+" C INNER JOIN "+parentTableName+" P ON C.FRANCHISEE_NO = P.FRANCHISEE_NO";
    				} else if("FRANCHISEE".equals(childTable)) {
    					updateQuery = "UPDATE "+childTable+" C INNER JOIN FS_LEAD_DETAILS A ON C.FRANCHISEE_NO = A.FRANCHISEE_NO INNER JOIN "+parentTableName+" P ON A.LEAD_ID = P.LEAD_ID";
    				} else {
    					updateQuery = "UPDATE "+childTable+" C INNER JOIN FRANCHISEE F ON C.ENTITY_ID = F.FRANCHISEE_NO INNER JOIN FS_LEAD_DETAILS A ON F.FRANCHISEE_NO = A.FRANCHISEE_NO INNER JOIN "+parentTableName+" P ON A.LEAD_ID = P.LEAD_ID";
    				}
    			} else if("fim".equals(moduleSpec)) {
    				if("FS_LEAD_DETAILS".equals(childTable) && "FRANCHISEE".equals(parentTableName)) {
    					updateQuery = "UPDATE "+childTable+" C INNER JOIN "+parentTableName+" P ON C.FRANCHISEE_NO = P.FRANCHISEE_NO";
    				} else if("FS_LEAD_DETAILS".equals(childTable)) {
    					updateQuery = "UPDATE "+childTable+" C INNER JOIN INNER JOIN "+parentTableName+" P ON C.FRANCHISEE_NO = P.ENTITY_ID";
    				} else {
    					if("FRANCHISEE".equals(parentTableName)) {
    						updateQuery = "UPDATE "+childTable+" C INNER JOIN FS_LEAD_DETAILS A ON C.LEAD_ID = A.LEAD_ID INNER JOIN "+parentTableName+" P ON A.FRANCHISEE_NO = P.FRANCHISEE_NO";
    					} else {
    						updateQuery = "UPDATE "+childTable+" C INNER JOIN FS_LEAD_DETAILS A ON C.LEAD_ID = A.LEAD_ID INNER JOIN "+parentTableName+" P ON A.FRANCHISEE_NO = P.ENTITY_ID";
    					}
    				}

    				//other table starts
    				if(otherTableField) {
    					if(otherTableFieldObj != null) {
    						String tbleNme = otherTableFieldObj.getTableAnchor();
    						FieldMappings mappings = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(tbleNme);
    						String otherTableName = mappings.getTableName();
    						if(StringUtil.isValidNew(otherTableName) && "ADDRESS".equals(otherTableName)) {
    							updateQuery = updateQuery + " INNER JOIN "+otherTableName+" O ON O.FOREIGN_ID = P."+parentIdFieldDbColumn;
    							tableAlias = "O";
    						}
    					}
    				}
    			}
    		}

    		if(StringUtil.isValidNew(updateQuery)) {
    			if(childPii && !parentPiiEnabled) {
    				updateQuery = updateQuery + " SET C."+childColumnName+" = AES_ENCRYPT("+tableAlias+"."+parentColumnName+",'pvm@e20')";
    			} else if(!childPii && parentPiiEnabled) {
    				updateQuery = updateQuery + " SET C."+childColumnName+" = AES_DECRYPT("+tableAlias+"."+parentColumnName+",'pvm@e20')";
    			} else {
    				updateQuery = updateQuery + " SET C."+childColumnName+" = "+tableAlias+"."+parentColumnName;
    			}
    		}

    		ParamResolver.getResolver().put("duplicateUpdateQuery", updateQuery);
    	}
    }
    
    
    /**
     * This function will provide the node object for the sync field.
     * P_Enh_Sync_Fields
     * @param parentTableName
     * @param parentFieldName
     * @param fileLoc
     * @param tableAnchor
     * @return
     */
    public Node existingFieldNode(String parentFieldName, Document doc) {
    	return existingFieldNode( parentFieldName,  doc,null);
    }
    public Node existingFieldNode(String parentFieldName, Document doc,String isReadOnlyField) {
    	Node existingFieldNode = null;
    	String[] name = parentFieldName.split("##");
    	String parentTableName = name[0];
    	String isOtherTableField = name[2];
    	parentFieldName = name[1];
    	
    	if("true".equals(isOtherTableField)) {
    		FieldMappings mapping = getFieldMappings(parentTableName);
    		Field f = mapping.getOtherTableField(parentFieldName);
    		String fieldName = f.getFieldName(); 
    		parentFieldName = fieldName.substring(fieldName.indexOf(f.getTableName())+f.getTableName().length());
    		parentTableName = f.getTableAnchor();
    		mapping = null;
    		
    	}
    	String parentFileLoc = MultiTenancyUtil.getTenantConstants().XML_DIRECTORY + (String)getTableMappings().get(parentTableName);
    	File parentFile = null;
    	Document parentDoc = null;
    	if(StringUtil.isValidNew(parentFileLoc)) {
    		parentFile = new File(parentFileLoc);
    		if(parentFile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				parentDoc = docBuilder.parse(parentFile);
    			} catch (Exception e) {
					// TODO: handle exception
    				e.printStackTrace();
				}
    		}
    	}
    	
    	NodeList nodeList = parentDoc.getElementsByTagName(TableXMLDAO.FIELD);
    	for(int i=0; i<nodeList.getLength(); i++) {
    		Node tempNode = nodeList.item(i);
			if(parentFieldName.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {
				Element element = (Element) nodeList.item(i);
				setTagAttr(element, "colspan-export", "1");// Bug 75343
				if("yes".equals(isReadOnlyField)){
					removeNode(element, TableXMLDAO.IS_MANDATORY);
				}
				existingFieldNode = doc.importNode(element, true);
				break;
			}
    	}
    	return existingFieldNode;
    }

    public boolean addDependentField(String fileFromWriteLoc, String fileToWriteLoc ,String fieldToWrite, String dependentLoc, String dependentTable, String dependentTableAnchor){

    	Document todoc = null;
    	File tofile = null;
    	Document fromDoc = null;
    	Document dependentDoc = null;
    	Node copiedNode = null;
    	if(StringUtil.isValidNew(fileToWriteLoc)) {
    		tofile = new File(fileToWriteLoc);
    		if(tofile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				todoc = docBuilder.parse(tofile);
    				/**
    				 * Take backup before update xml data
    				 */

    				String fileLocBackup = fileToWriteLoc.substring(0, fileToWriteLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

    				source = new DOMSource(todoc);
    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    				return false;
    			}
    		} else {
    			return false;
    		}

    	}

    	try {
    		if(StringUtil.isValidNew(fileFromWriteLoc)){
    			try {
    				fromDoc = docBuilder.parse(new File(fileFromWriteLoc));
    			} catch (SAXException e) {
    				// TODO Auto-generated catch block
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    			}
    			String tableName = FieldNames.EMPTY_STRING;
    			if(fromDoc!= null) {
    				NodeList tablelist = 	fromDoc.getElementsByTagName(TableXMLDAO.TABLE_NAME);
    				Node tableNode = tablelist.item(0);
    				tableName = tableNode.getFirstChild().getNodeValue();
    			}
    			try {
    				fromDoc = docBuilder.parse(new File(fileFromWriteLoc));
    			} catch (SAXException e) {
    				// TODO Auto-generated catch block
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    			}

    			if(StringUtil.isValidNew(dependentLoc)){
    				try {
    					dependentDoc = docBuilder.parse(new File(dependentLoc));
    				} catch (SAXException e) {
    					// TODO Auto-generated catch block
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    				}
    				NodeList nodeList = dependentDoc.getElementsByTagName(TableXMLDAO.FIELD);
    				int size = nodeList.getLength();
    				for(int i=0;i< size;i++) {
    					Node tempNode = nodeList.item(i);
    					if(fieldToWrite.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {
    						Element element = (Element) nodeList.item(i);
    						// Imports a node from another document to this document, without altering 
    						// or removing the source node from the original document
    						copiedNode = todoc.importNode(element, true);
    						break;
    					}
    				}

    			}
    			getChildTable(todoc, tableName).appendChild(copiedNode);
    			setTagTextVal(todoc, copiedNode, TableXMLDAO.ORDER_BY, String.valueOf(getMaxOrderBy(todoc)+1));
    			setTagTextVal(todoc,copiedNode, TableXMLDAO.SECTION, "1");
    			setTagTextVal(todoc,copiedNode, "dependent-table", dependentTable);
    			setTagTextVal(todoc,copiedNode, "dependent-table-anchor", dependentTableAnchor);
    			// updating the changes in xml
    			todoc.normalize();
    			result = new StreamResult(fileToWriteLoc);

    			source = new DOMSource(todoc);
    			try {
    				trans.transform(source, result);
    			} catch (TransformerException e) {
    				// TODO Auto-generated catch block
    			}

    		}
    	}catch(Exception e){
    	}
    	finally{
    		todoc = null;
    		result = null;
    		source = null;
    		tofile = null;
    		fromDoc = null;
    	}
    	return true;


    }
    public boolean setTagOfXml(String fileLoc,String isActive,String isPiiEnabled,String fldToUpdate,String modifyFld,String tableNameDb,String modifyDisplayName){//Martin-20160728-018

    	Document todoc = null;
    	File tofile = null;
    	if(StringUtil.isValidNew(fileLoc)) {
    		tofile = new File(fileLoc);
    		if(tofile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				todoc = docBuilder.parse(tofile);
    				/**
    				 * Take backup before update xml data
    				 */

    				String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

    				source = new DOMSource(todoc);
    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    				return false;
    			}
    		} else {
    			return false;
    		}

    	}
    	try {
    		if(todoc!=null) {
    			int maxOrderBy = getMaxOrderBy(todoc);//getting max order from centerInfoDisplay Xmls
    			fieldMappings = getFieldMappings("centerInfoDisplay");
    			Field[] flsObj = fieldMappings.getSectionTableAllActiveDeactiveFieldsWithOrderBy("1");
                Info arrLst = new Info();
                boolean flag = false;
                int count = 0;
    			if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)) {//creating list of order by of fields of xmls for updations in case of inactive fields
    				NodeList nodeList1 = todoc.getElementsByTagName(TableXMLDAO.MAX_ORDER_BY);
    	    		Node maxOrderNode = nodeList1.item(0);
    				if("no".equals(isActive)) {
                        for(Field s:flsObj) {
                            if(s == null) {
                                continue;
                            }
                            if((tableNameDb+"_"+fldToUpdate).equals(s.getFieldName().trim()) || fldToUpdate.equals(s.getFieldName().trim())) {//Martin-20160728-018
                                flag = true;
                            }
                            if(flag) {
                                ++count;
                                if(count ==1) {
                                    arrLst.set(s.getFieldName(), flsObj.length-1);
                                    maxOrderBy=maxOrderBy-1;//decrementing max-order by incase of inactive fields //Martin-20160727-017
                                } else {
                                    arrLst.set(s.getFieldName(), s.getOrderBy()-1);
                                }
                            } else {
                                arrLst.set(s.getFieldName(), s.getOrderBy());
                            }
                        }
                        //maxOrderBy=maxOrderBy-1;//decrementing max-order by incase of inactive fields  //Martin-20160727-017
                    } else {
                        Field[] flsObjA = fieldMappings.getSectionTableFieldsWithOrderBy("1");
                        Field[] flsObjD = fieldMappings.getDeactiveFieldMap("1");
                        for(Field s:flsObjD) {
                            if((tableNameDb+"_"+fldToUpdate).equals(s.getFieldName()) || fldToUpdate.equals(s.getFieldName())) {//Martin-20160728-018
                                flag = true;
                            }
                            if(flag) {
                                ++count;
                                if(count ==1) {//Martin-20160728-018 starts
                                    if(flsObjA == null){
                                        arrLst.set(s.getFieldName(), 0);
                                        maxOrderBy=0;//Martin-20160728-018
                                    }else{
                                        arrLst.set(s.getFieldName(), flsObjA.length);
                                        maxOrderBy=maxOrderBy+1;//incrementing max-order by in case inactive fields is activated again  //Martin-20160727-017
                                    }//Martin-20160728-018 ends
                                } else {
//    	    						arrLst.set(s.getFieldName(), s.getOrderBy()-1);
                                }
                            } else {
                                arrLst.set(s.getFieldName(), s.getOrderBy()+1);
                            }
                        }
                        //maxOrderBy=maxOrderBy+1;//incrementing max-order by in case inactive fields is activated again   //Martin-20160727-017
                    }
    				maxOrderNode.getFirstChild().setNodeValue(String.valueOf(maxOrderBy));
                }
    			NodeList nodeList = todoc.getElementsByTagName(TableXMLDAO.FIELD);
    			int size = nodeList.getLength();
    			for(int i=0;i< size;i++) {
    				Node tempNode = nodeList.item(i);
    				String nameVal=getTagText(tempNode, TableXMLDAO.FIELD_NAME);
    				if((tableNameDb+"_"+fldToUpdate).equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME)) || fldToUpdate.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {//Martin-20160728-018
    					if( StringUtil.isValidNew(isActive)) {

    						setTagTextVal(todoc, tempNode, TableXMLDAO.IS_ACTIVE, isActive);
    					}
    					if(StringUtil.isValidNew(isPiiEnabled)) {
    						setTagTextVal(todoc, tempNode, TableXMLDAO.PII_ENABLED, ""+!Boolean.parseBoolean(isPiiEnabled));
    					}
    					if(StringUtil.isValidNew(modifyDisplayName)) {//Martin-20160728-018 starts
    						setTagTextVal(todoc, tempNode, TableXMLDAO.DISPLAY_NAME, modifyDisplayName);
    					}//Martin-20160728-018 ends
    				}
    				if(!"yes".equals(modifyFld) && StringUtil.isValidNew(isActive)){
    				if(arrLst.size() > 1) {
                        if(!StringUtil.isValidNew(arrLst.get(nameVal))) {
                            continue;
                        }
                        setTagText(tempNode, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
                    } else {
                        if((tableNameDb+"_"+fldToUpdate).equals(nameVal) || fldToUpdate.equals(nameVal)) {//Martin-20160728-018
                            setTagText(tempNode, TableXMLDAO.ORDER_BY, arrLst.get(nameVal));
                        }
                    }
    				}
    			}
    			// updating the changes in xml
    			todoc.normalize();
    			result = new StreamResult(fileLoc);

    			source = new DOMSource(todoc);
    			DBUtil.getInstance().removeFieldMappings("centerInfoDisplay");
    			try {
    				trans.transform(source, result);
    			} catch (TransformerException e) {
    				// TODO Auto-generated catch block
    			} 
    		}
    	} catch(Exception e) {
    		return false;
    	}finally{
    		todoc = null;
    		result = null;
    		source = null;
    		tofile = null;
    		fieldMappings = null;

    	}

    	return true;

    }
    public boolean removeNodeFromXml(String fileLoc, String fldToRemove,String tabfileLoc,String tableNameDb){//Martin-20160728-018
    	Document todoc = null;
    	Boolean isDeactiveField=false; //Martin-20160728-018
    	File tofile = null;
    	Document fromDoc = null;
    	if(StringUtil.isValidNew(fileLoc)) {
    		tofile = new File(fileLoc);
    		if(tofile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				todoc = docBuilder.parse(tofile);
    				/**
    				 * Take backup before update xml data
    				 */

    				String fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

    				source = new DOMSource(todoc);
    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    				return false;
    			}
    		} else {
    			return false;
    		}
    	}
    	if(StringUtil.isValidNew(tabfileLoc)){

    		try {
    			fromDoc = docBuilder.parse(new File(tabfileLoc));
    		} catch (SAXException e) {
    			// TODO Auto-generated catch block
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    		}
    	}
    	try {
    		String tableName = FieldNames.EMPTY_STRING;
    		if(fromDoc!= null) {
    			NodeList tablelist = 	fromDoc.getElementsByTagName(TableXMLDAO.TABLE_NAME);
    			Node tableNode = tablelist.item(0);
    			tableName = tableNode.getFirstChild().getNodeValue();
    		}
    		// getting node to remove
    		if(todoc!=null) {
    			NodeList fldnodeList = todoc.getElementsByTagName(TableXMLDAO.FIELD);
    			int size = fldnodeList.getLength();
    			Node nodeToRemove = null;
    			Node tempNode = null;
    			int orderByToRemove = 0; 
    			for(int i=0;i< size;i++) {
    				 tempNode = fldnodeList.item(i);
    				if((tableNameDb+"_"+fldToRemove).equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME)) || fldToRemove.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {//Martin-20160728-018
    					if("no".equals(getTagText(tempNode, TableXMLDAO.IS_ACTIVE))){//Martin-20160728-018 starts
							isDeactiveField=true;
						}//Martin-20160728-018 ends
    					orderByToRemove = Integer.parseInt(getTagText(tempNode, TableXMLDAO.ORDER_BY));
    					nodeToRemove = tempNode;
    					break;
    				}

    			}
    			int orderBy = 0;
   			 
   		// updating orderby of other fields
   			for(int i=0;i< size;i++) {
   				 tempNode = fldnodeList.item(i);
   				if(StringUtil.isValid(getTagText(tempNode, TableXMLDAO.ORDER_BY))){
   					orderBy = Integer.parseInt(getTagText(tempNode, TableXMLDAO.ORDER_BY));
   					if(orderBy>orderByToRemove){
   						setTagTextVal(todoc, tempNode, TableXMLDAO.ORDER_BY, String.valueOf(orderBy-1));
   					}
   				}	

   			}
   			
   				int maxOrderBy = getMaxOrderBy(todoc);
	    		NodeList nodeList = todoc.getElementsByTagName("max-order-by");
	    		Node maxOrderNode = nodeList.item(0);
	    		if(!isDeactiveField){
				 maxOrderNode.getFirstChild().setNodeValue(String.valueOf(maxOrderBy-1));
	    		}
	    		
			
    			//removing node
    			 nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);
    			if(nodeList!=null) {
    				size = nodeList.getLength();
    				Element el =null;
    				for(int i=0;i< size;i++) {
    					 el = (Element) nodeList.item(i);
    					if(el.hasAttribute("name") && StringUtil.isValid(tableName) && tableName.equals(el.getAttribute("name"))) {
    						if(nodeToRemove!=null){
    							el.removeChild(nodeToRemove);
    						break;
    						}
    					}
    				}  
    			}
    			
    			 
    			// updating the changes in xml
    			todoc.normalize();
    			result = new StreamResult(fileLoc);

    			source = new DOMSource(todoc);
    			try {
    				trans.transform(source, result);
    			} catch (TransformerException e) {
    				// TODO Auto-generated catch block
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		// TODO Auto-generated catch block
    	} finally{
    		todoc = null;
    		result = null;
    		source = null;
    		tofile = null;

    	}


    	return true;
    }
    
    public boolean importNodeToXml(String fileFromWriteLoc, String fileToWriteLoc ,String fieldToWrite){
    	Boolean isDeactiveField=false; //Martin-20160728-018
    	Document todoc = null;
    	File tofile = null;
    	Document fromDoc = null;
    	if(StringUtil.isValidNew(fileToWriteLoc)) {
    		tofile = new File(fileToWriteLoc);
    		if(tofile.isFile()) {
    			try {
    				this.initBDaoInstance();
    				todoc = docBuilder.parse(tofile);
    				/**
    				 * Take backup before update xml data
    				 */

    				String fileLocBackup = fileToWriteLoc.substring(0, fileToWriteLoc.indexOf(".xml"));
    				resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

    				source = new DOMSource(todoc);
    				trans.transform(source, resultBackup);
    			} catch(Exception e) {
    				return false;
    			}
    		} else {
    			return false;
    		}

    	}

    	try {
    		if(StringUtil.isValidNew(fileFromWriteLoc)){

    			try {
    				fromDoc = docBuilder.parse(new File(fileFromWriteLoc));
    			} catch (SAXException e) {
    				// TODO Auto-generated catch block
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    			}
    			int maxOrderBy = getMaxOrderBy(todoc);
    			System.out.println("Before Copy...");
    			Node copiedNode = null;
    			String tableName = FieldNames.EMPTY_STRING;
    			String displayName = FieldNames.EMPTY_STRING;
    			if(fromDoc!= null) {
    				NodeList tablelist = 	fromDoc.getElementsByTagName(TableXMLDAO.TABLE_NAME);
    				Node tableNode = tablelist.item(0);
    				tableName = tableNode.getFirstChild().getNodeValue();
    				NodeList displayNameList = 	fromDoc.getElementsByTagName(TableXMLDAO.TABLE_DISPLAY_NAME);
    				Node displayNameNode = displayNameList.item(0);
    				displayName = displayNameNode.getFirstChild().getNodeValue();
    				//tableName = "xyzz";
    				NodeList nodeList = fromDoc.getElementsByTagName(TableXMLDAO.FIELD);
    				int size = nodeList.getLength();
    				
    				for(int i=0;i< size;i++) {
    					Node tempNode = nodeList.item(i);
    					if(fieldToWrite.equals(getTagText(tempNode, TableXMLDAO.FIELD_NAME))) {
    						if("no".equals(getTagText(tempNode, TableXMLDAO.IS_ACTIVE))){//Martin-20160728-018 starts
    							isDeactiveField=true;
    						}//Martin-20160728-018 ends
    						Element element = (Element) nodeList.item(i);
    						// Imports a node from another document to this document, without altering 
    						// or removing the source node from the original document
    						copiedNode = todoc.importNode(element, true);
    						setTagText(copiedNode, TableXMLDAO.FIELD_NAME, tableName+"_"+getTagText(tempNode, TableXMLDAO.FIELD_NAME));//Martin-20160728-018
    						setTagText(copiedNode, TableXMLDAO.ORDER_BY, String.valueOf(maxOrderBy+1));
    						setTagText(copiedNode, TableXMLDAO.SECTION, "1");
    						// Adds the node to the end of the list of children of this node

    						break;
    					}
    				}


    			}
    			if(todoc!=null) {
    				Info arrLstDeactiveField = new Info();
    				boolean fieldFlag=false;
    				boolean childTableExist = false;
    				NodeList nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);
    				if(nodeList.getLength()==0){
    					Element firstName = todoc.createElement(TableXMLDAO.CHILD_TABLE);
    					Attr nameAttr = todoc.createAttribute("name");
    					nameAttr.setValue(tableName);
    					firstName.setAttributeNode(nameAttr);
    					Attr displayAttr = todoc.createAttribute("display-name");
    					displayAttr.setValue(displayName);
    					firstName.setAttributeNode(displayAttr);
    					todoc.getDocumentElement().appendChild(firstName);
    					nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);
    				}
    				if(nodeList!=null) {
    					int size = nodeList.getLength();
    					for(int i=0;i< size;i++) {
    						Element el = (Element) nodeList.item(i);
    						if(el.hasAttribute("name") && StringUtil.isValid(tableName) && tableName.equals(el.getAttribute("name"))) {
    							if(copiedNode!=null)
    								el.appendChild(copiedNode);
    							fieldFlag=true;
    							childTableExist = true;
    							break;
    						}
    					}  
    				}
    				if(!childTableExist) {
    					Element firstName = todoc.createElement(TableXMLDAO.CHILD_TABLE);
    					Attr nameAttr = todoc.createAttribute("name");
    					nameAttr.setValue(tableName);
    					firstName.setAttributeNode(nameAttr);
    					Attr displayAttr = todoc.createAttribute("display-name");
    					displayAttr.setValue(displayName);
    					firstName.setAttributeNode(displayAttr);
    					todoc.getDocumentElement().appendChild(firstName);
    					nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);
    					if(nodeList!=null) {
    						int size = nodeList.getLength();
    						for(int i=0;i< size;i++) {
    							Element el = (Element) nodeList.item(i);
    							if(el.hasAttribute("name") && StringUtil.isValid(tableName) && tableName.equals(el.getAttribute("name"))) {
    								if(copiedNode!=null)
    									el.appendChild(copiedNode);
    								fieldFlag=true;
    								childTableExist = true;
    								break;
    							}
    						}  
    					}
    				}
    				fieldMappings = getFieldMappings("centerInfoDisplay");
    				Field[] flsObjD = fieldMappings.getDeactiveFieldMap("1");
    				if(flsObjD!=null)
    				 for(Field s:flsObjD) {
    					 arrLstDeactiveField.set(s.getFieldName(), s.getOrderBy());
    				 }
    				 DBUtil.getInstance().removeFieldMappings("centerInfoDisplay");
    	    			if(fieldFlag && arrLstDeactiveField.size()>0 ){
    	    				NodeList nodeList1 = todoc.getElementsByTagName(TableXMLDAO.FIELD);
    	        			int size = nodeList1.getLength();
    	        			for(int i=0;i< size;i++) {
    	        				Node tempNode = nodeList1.item(i);
    	        				String nameVal=getTagText(tempNode, TableXMLDAO.FIELD_NAME);
    	        				if(arrLstDeactiveField.containsKey(nameVal)) {
    	        					setTagText(tempNode, TableXMLDAO.ORDER_BY, String.valueOf(Integer.parseInt(arrLstDeactiveField.getString(nameVal))+1));
    	        				}
    	        			}
    	    			}
    			}
    		//	updating max-order-by 
    			if(todoc!=null && !isDeactiveField){//Martin-20160728-018
    	    		NodeList nodeList = todoc.getElementsByTagName("max-order-by");
    	    		Node maxOrderNode = nodeList.item(0);
    	    		
    				 maxOrderNode.getFirstChild().setNodeValue(String.valueOf(maxOrderBy+1));
    	    		
    			}	
    			// updating the changes in xml
    			todoc.normalize();
    			result = new StreamResult(fileToWriteLoc);

    			source = new DOMSource(todoc);
    			try {
    				trans.transform(source, result);
    			} catch (TransformerException e) {
    				// TODO Auto-generated catch block
    			}
    		}
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    	}finally{
    		todoc = null;
    		result = null;
    		source = null;
    		tofile = null;
    		fromDoc = null;
    		DBUtil.getInstance().removeFieldMappings("centerInfoDisplay");
    	}
    	return true;
    }
    
    public int getMaxOrderBy(Document todoc){
    	int maxOrderBy = 0;
    	if(todoc!=null){
    		NodeList nodeList = todoc.getElementsByTagName("max-order-by");
    		Node maxOrderNode = nodeList.item(0);
    		try {
			 maxOrderBy = Integer.parseInt(maxOrderNode.getFirstChild().getNodeValue());
    		} catch(NumberFormatException e){
    			maxOrderBy = 0;
			}
    		System.out.println("maxOrderBy-------------  "+maxOrderBy);
    		/*NodeList nodeList = todoc.getElementsByTagName(TableXMLDAO.FIELD);
    		int size = nodeList.getLength();
    		int orderBy=0;
    		for(int i=0;i< size;i++) {
    			try {
    				orderBy = Integer.parseInt(getTagText(nodeList.item(i), TableXMLDAO.ORDER_BY));
    			} catch(NumberFormatException e){
    				orderBy = 0;
    			}
    			if(orderBy>maxOrderBy) {
    				maxOrderBy = orderBy;
    			}
    		}*/
    	}

    	return maxOrderBy;

    }
    
    public Element getChildTable(Document todoc,String tableName){
    	Element childTable = null;
    	if(todoc!=null) {

    		boolean childTableExist = false;
    		NodeList nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);

    		if(nodeList.getLength()==0){
    			childTable = todoc.createElement(TableXMLDAO.CHILD_TABLE);
    			Attr nameAttr = todoc.createAttribute("name");
    			nameAttr.setValue(tableName);
    			childTable.setAttributeNode(nameAttr);
    			todoc.getDocumentElement().appendChild(childTable);
    			nodeList = todoc.getElementsByTagName(TableXMLDAO.CHILD_TABLE);
    		} else if(nodeList!=null) {
    			int size = nodeList.getLength();
    			for(int i=0;i< size;i++) {
    				Element el = (Element) nodeList.item(i);
    				if(el.hasAttribute("name") && StringUtil.isValid(tableName) && tableName.equals(el.getAttribute("name"))) {
    					childTable = el;
    					childTableExist = true;
    					break;
    				}
    			}  
    		}
    		if(!childTableExist) {
    			childTable = todoc.createElement(TableXMLDAO.CHILD_TABLE);
    			Attr nameAttr = todoc.createAttribute("name");
    			nameAttr.setValue(tableName);
    			childTable.setAttributeNode(nameAttr);
    			todoc.getDocumentElement().appendChild(childTable);

    		}

    	}

    	return childTable;


    }
    

    private void updateFieldValueForHeatMeter(String fieldName, boolean isActive) {
        if("francSignAgrDate".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().AGREEMENT_SIGNED_BY_FRANCHISEES = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "AGREEMENT_SIGNED_BY_FRANCHISEES");
        } else if("recByFrancDate1".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().FDD_RECEIVED_BY_FRANCIHSEE = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "FDD_RECEIVED_BY_FRANCIHSEE");
        } else if("visitdate".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().VISIT_DATE = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "VISIT_DATE");
        } else if("backgroundCheck".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().BACKGROUND_CHECK = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "BACKGROUND_CHECK");
        } else if("liquidCapitalMax".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().LIQUID_CAPITAL_MAX = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "LIQUID_CAPITAL_MAX");
        } else if("liquidCapitalMin".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().LIQUID_CAPITAL_MIN = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "LIQUID_CAPITAL_MIN");
        } else if("investTimeframe".equals(fieldName)) {
            MultiTenancyUtil.getTenantConstants().INVEST_TIMEFRAME = isActive;
            SQLUtil.updateTableValue("FS_HEAT_METER_CONFIGURATION", "IS_ACTIVE", String.valueOf(isActive), "FIELD_NAME", "INVEST_TIMEFRAME");
        }
    }

    public static String getHeatMeterValue(String colName) {
        String returnData = "no";
        String query = "SELECT "+colName+" FROM HEAT_ELEMENT";
        ResultSet result = null;
        try{
            result = QueryUtil.getResult(query, null);
            if(result.next()) {
                String data = result.getString(colName);
                if(!StringUtil.isValidNew(data) || "0".equals(data)) {
                    returnData = "no";
                } else {
                    returnData = "yes";
                }
            }
        } catch(Exception e) {
        }
        return returnData;
    }

    public static String getHeatMeterValueForQualificationDetails(String tableName) {
        String returnData = "no";
        String query = "SELECT SCORE FROM "+tableName;
        ResultSet result = null;
        try{
            result = QueryUtil.getResult(query, null);
            while(result.next()) {
                String data = result.getString("SCORE");
                if(!StringUtil.isValidNew(data) || "0".equals(data)) {
                    returnData = "no";
                } else {
                    returnData = "yes";
                    break;
                }
            }
        } catch(Exception e) {
        }
        return returnData;
    }
    
    /**
     * This function is used to check whether the option in parent field is associated with the options of child field.
     * @param fieldName
     * @param tableAnchor
     * @param option
     * @return
     */
    public String checkForParentField(String fieldName, String tableAnchor, String option, String otherTableField) {
    	if(TableAnchors.FRANCHISEE.equals(tableAnchor)){
        	tableAnchor=TableAnchors.FRANCHISEES;
		}

    	ResultSet result = null;
    	String conditionValue = "";
    	FieldMappings mappings = DBUtil.getInstance().getFieldMappings(tableAnchor);
    	Field fld = mappings.getField(fieldName);
    	if("true".equals(otherTableField)) {
    		fld = mappings.getOtherTableField(fieldName);
    	}
    	SequenceMap dependentChildTotalMap = fld.getDependentChildTotalMap();
    	String childFields = "";
    	if(dependentChildTotalMap != null && dependentChildTotalMap.size() > 0) {
    		Iterator<String> itr = dependentChildTotalMap.keys().iterator();
    		while(itr.hasNext()) {
    			String key = "'" + (String)itr.next() + "'";
    			childFields += key + ",";
    		}
    		if(StringUtil.isValidNew(childFields) && childFields.endsWith(",")) {
    			childFields = childFields.substring(0, childFields.lastIndexOf(","));
    		}
    		String query = "SELECT COUNT(*) AS COUNT FROM FIM_BUILDER_MASTER_DATA WHERE TABLE_ANCHOR='"+tableAnchor+"' AND FIELD_NAME IN ("+childFields+") AND DEPENDENT_VALUE='"+option+"'";
    		try {
    			result = QueryUtil.getResult(query, null);
    			if(result != null && result.next()) {
    				String count = result.getString("COUNT");
    				if("0".equals(count)) {
    					conditionValue = "false"; //can be deleted
    				} else {
    					conditionValue = "true"; //cannot be deleted
    				}
    			}
    		} catch (Exception e) {
				// TODO: handle exception
			} finally {
				QueryUtil.releaseResultSet(result);
			}
    	} else { 
    		conditionValue = "false"; //can be deleted
    	}
    	return conditionValue;
    }
    
/**This method updates tables corresponding to pii 
 * BB-20150525-360
 * @author Akash Kumar
 * @param tableAnchor
 * @param fieldName
 * @param ModuleName
 * @param isPii
 * @param archiveTable
 */
    public  void updateColumnDataType(String tableAnchor,String fieldName,String ModuleName,String isPii,String archiveTable) {
    	 boolean isPiiEnabled=false;
    	 String tableDataType="";
         Info tabInfo=null;
         StringBuffer updateQuery = null;
         StringBuffer alterQueryArchive=null;
         StringBuffer updateQueryArchive=null;
         ResultSet result = null;
         String selectQuery="";
         StringBuffer queryParam=null;
         StringBuffer alterQuery = null;
         Info info1=null;
    	try{
    	FieldMappings fieldMappings = getFieldMappings(tableAnchor);
        String tableName = fieldMappings.getTableName();
        fld = fieldMappings.getField(fieldName);
        String dbFldName = fld.getDbField();
        if(StringUtil.isValidNew(isPii)){
        	isPiiEnabled=Boolean.parseBoolean(isPii);
        }else{
        isPiiEnabled=fld.isPiiEnabled();
        }
        String dataType=fld.getDataType();
        Info info=(Info)QueryUtil.getColumnMetaData(tableName,dbFldName);
        String query2="INSERT INTO PII_FIELD_DATA_TYPE VALUES(?,?,?,?,?,?,?)";
        alterQuery = new StringBuffer();
        alterQuery.append("ALTER TABLE ").append(tableName).append(" ");
        alterQuery.append("MODIFY COLUMN ").append(dbFldName).append(" ");
        if(StringUtil.isValidNew(archiveTable)){
        	info1=(Info)QueryUtil.getColumnMetaData(archiveTable,dbFldName);
        	alterQueryArchive=new StringBuffer();
        	alterQueryArchive.append("ALTER TABLE ").append(archiveTable).append(" ");
        	alterQueryArchive.append("MODIFY COLUMN ").append(dbFldName).append(" ");
        }
        if(!isPiiEnabled)
        {
        	QueryUtil.update(query2, new String[]{IDGenerator.getNextKey(), ModuleName,tableName,dbFldName,(String)info.getString("DEFAULT_VALUE"),(String)info.getString("TYPE_NAME"),(String)info.getString("COLUMN_SIZE")});
        	if(StringUtil.isValidNew(archiveTable)){
        		QueryUtil.update(query2, new String[]{IDGenerator.getNextKey(), ModuleName,archiveTable,dbFldName,(String)info1.getString("DEFAULT_VALUE"),(String)info1.getString("TYPE_NAME"),(String)info1.getString("COLUMN_SIZE")});
        		updateQueryArchive=new StringBuffer();
        		updateQueryArchive.append("UPDATE  ").append(archiveTable).append(" ");
        		updateQueryArchive.append("SET ").append(dbFldName).append("=AES_ENCRYPT("+dbFldName+",'pvm@e20')");
        		alterQueryArchive.append("BLOB");
        	}
        	updateQuery=new StringBuffer();
        	updateQuery.append("UPDATE  ").append(tableName).append(" ");
        	updateQuery.append("SET ").append(dbFldName).append("=AES_ENCRYPT("+dbFldName+",'pvm@e20')");
        	alterQuery.append("BLOB");
        	if(!FieldNames.TAXPAYER_ID.equals(fieldName))
        	{
        		QueryUtil.alterDBTable(alterQuery.toString());
        		QueryUtil.update(updateQuery.toString(), null);
        		if(StringUtil.isValidNew(archiveTable)){
	        		QueryUtil.alterDBTable(alterQueryArchive.toString());
	        		QueryUtil.update(updateQueryArchive.toString(), null);
        		}
        	}
        }else{
        	updateQuery=new StringBuffer();
        	updateQuery.append("UPDATE  ").append(tableName).append(" ");
        	updateQuery.append("SET ").append(dbFldName).append("=AES_DECRYPT("+dbFldName+",'pvm@e20')");
        	if(StringUtil.isValidNew(archiveTable)){
        		updateQueryArchive=new StringBuffer();
        		updateQueryArchive.append("UPDATE  ").append(archiveTable).append(" ");
            	updateQueryArchive.append("SET ").append(dbFldName).append("=AES_DECRYPT("+dbFldName+",'pvm@e20')");
        	}
        	if(!FieldNames.TAXPAYER_ID.equals(fieldName))
        	{
        		QueryUtil.update(updateQuery.toString(), null);
        	}
            selectQuery="SELECT FIELD_DATA_TYPE,FIELD_SIZE,FIELD_DEFAULT_VALUE FROM PII_FIELD_DATA_TYPE WHERE FIELD_NAME=? AND TABLE_NAME=? AND MODULE_NAME=?";
            result = QueryUtil.getResult(selectQuery, new String[]{dbFldName, tableName, ModuleName});
            while(result.next()) {
            	queryParam=new StringBuffer();
            	 queryParam.append(result.getString("FIELD_DATA_TYPE"));
            			 if((!"DATE".equals(result.getString("FIELD_DATA_TYPE")))&&(!"DATETIME".equals(result.getString("FIELD_DATA_TYPE")))&&!"DOUBLE".equals(result.getString("FIELD_DATA_TYPE"))){
            				 queryParam.append("(").append(result.getString("FIELD_SIZE")).append(") ");
            			 }
            			if(StringUtil.isValidNew(result.getString("FIELD_DEFAULT_VALUE"))){
            				if("DATE".equals(result.getString("FIELD_DATA_TYPE"))||"DATETIME".equals(result.getString("FIELD_DATA_TYPE"))){
            					queryParam.append(" DEFAULT '").append(result.getString("FIELD_DEFAULT_VALUE")).append("'");
            				}
            				else{
            				queryParam.append(" DEFAULT ").append(result.getString("FIELD_DEFAULT_VALUE"));
            				}
            			}
            }
            alterQuery.append(queryParam);
            if(StringUtil.isValidNew(archiveTable)){
            	result=null;
            	result = QueryUtil.getResult(selectQuery, new String[]{dbFldName, archiveTable, ModuleName});
            	while(result.next()) {
                	queryParam=new StringBuffer();
                	 queryParam.append(result.getString("FIELD_DATA_TYPE"));
                			 if((!"DATE".equals(result.getString("FIELD_DATA_TYPE")))&&(!"DATETIME".equals(result.getString("FIELD_DATA_TYPE")))&&!"DOUBLE".equals(result.getString("FIELD_DATA_TYPE"))){
                				 queryParam.append("(").append(result.getString("FIELD_SIZE")).append(") ");
                			 }
                			if(StringUtil.isValid(result.getString("FIELD_DEFAULT_VALUE"))){
                				if("DATE".equals(result.getString("FIELD_DATA_TYPE"))||"DATETIME".equals(result.getString("FIELD_DATA_TYPE"))){
                					queryParam.append(" DEFAULT '").append(result.getString("FIELD_DEFAULT_VALUE")).append("'");
                				}
                				else{
                				queryParam.append(" DEFAULT ").append(result.getString("FIELD_DEFAULT_VALUE"));
                				}
                			}
                }
        	alterQueryArchive.append(queryParam);
            }
            if(!FieldNames.TAXPAYER_ID.equals(fieldName))
        	{
            	QueryUtil.alterDBTable(alterQuery.toString());
        	}
            tabInfo = new Info();
            tabInfo.set("FIELD_NAME",dbFldName);
            tabInfo.set("TABLE_NAME",tableName);
            tabInfo.set("MODULE_NAME",ModuleName);
            SQLUtil.deleteTableData("PII_FIELD_DATA_TYPE",tabInfo);
            if(StringUtil.isValidNew(archiveTable)){
            	if(!FieldNames.TAXPAYER_ID.equals(fieldName))
            	{
            		QueryUtil.update(updateQueryArchive.toString(), null);
            		QueryUtil.alterDBTable(alterQueryArchive.toString());
            	}
            	tabInfo = new Info();
                tabInfo.set("FIELD_NAME",dbFldName);
                tabInfo.set("TABLE_NAME",archiveTable);
                tabInfo.set("MODULE_NAME",ModuleName);
                SQLUtil.deleteTableData("PII_FIELD_DATA_TYPE",tabInfo);
            }
        }
        StringBuffer summaryDisplayString=new StringBuffer("UPDATE SUMMARY_DISPLAY SET IS_PII_ENABLED='");
        summaryDisplayString.append(!isPiiEnabled);
        summaryDisplayString.append("'  WHERE DISPLAY_VALUE='").append(fld.getDbField()).append("'");
        QueryUtil.update(summaryDisplayString.toString(), null);
        } catch(Exception e) {
        }
    	finally{
    		QueryUtil.releaseResultSet(result);
    	}
    }
    
    public String getSyncFieldDisplayName(String syncWith) {
    	String tableAnchor = syncWith.split("##")[0];
    	String fieldName = syncWith.split("##")[1];
    	String isOtherField = syncWith.split("##")[2];
    	String displayName = "";
    	String tabDisplayName = "";
    	if(StringUtil.isValidNew(tableAnchor)) {
    		tabDisplayName = SQLUtil.getColumnValue("BUILDER_WEB_FORMS", "FORM_NAME", "TABLE_ANCHOR",  tableAnchor);
    		FieldMappings fieldMappings = getFieldMappings(tableAnchor);
    		if(fieldMappings != null) {
    			Field field = fieldMappings.getField(fieldName);
    			if("true".equals(isOtherField)) {
    				field = fieldMappings.getOtherTableField(fieldName);
    			}
    			
    			if(field != null) {
    				displayName = field.getDisplayName();
    			}
    		}
    	}
    	return tabDisplayName + " > " + displayName;
    }
    
    /**
     * This function is used to get the fieldNames corresponding to the tableAnchor
     * P_Enh_Sync_Fields
     * @param tableAnchor
     * @param fieldNameTarget
     * @param selectedValue
     * @return
     */
    public StringBuffer getFieldNamesForTabs(String tableAnchor, String displayType, String actualTableAnchor, String validationType) {
    	
    	StringBuffer returnValue = new StringBuffer();
    	SequenceMap resultMap = new SequenceMap();
    	UserRoleMap fimLeftUserRoleMap = (UserRoleMap)StrutsUtil.getHttpSession().getAttribute("userRoleMap");
    	String user_level = (String)StrutsUtil.getHttpSession().getAttribute("user_level");

    	FieldMappings fieldMappings = getFieldMappings(tableAnchor);
    	HeaderMap[] hMap = fieldMappings.getHeaderMap();
    	for(HeaderMap h : hMap) {
    		HeaderField hFld = h.getHeaderFields();
    		if("additionalFranchiseLocationDetails".equals(hFld.getHeaderAttrMap().get("name")) || "additionalFDDDetails".equals(hFld.getHeaderAttrMap().get("name"))) {
    			continue;
    		}
    		
    		SequenceMap attrMap = hFld.getHeaderAttrMap();
    		if(hFld.getHeaderAttrMap().get("dependentModule") != null) {
				String dependentModule = (String)attrMap.get("dependentModule");
				String required = (String)attrMap.get("required");
				String displayOnly = (String)attrMap.get("displayOnly");
				if(StringUtil.isValidNew(required) && "true".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) && StringUtil.isValidNew(displayOnly) && "true".equals(displayOnly)) {
					continue;
				} else if(StringUtil.isValidNew(required) && "false".equals(required) && ModuleUtil.isModuleImplemented(dependentModule) ) {
					continue;
				}
			}
    		
    		if("userLevelMessage".equals(h.getName()) || "callInformation".equals(h.getName())) {
    			continue;
    		}
    		Field[] flds = fieldMappings.getSectionTablesFieldsArrayWithOrderBy(hFld); //to get only active fields
    		if(flds != null) {
    			for(Field fld : flds) {
    				if(fld == null) {
    					continue;
    				}
    				
    				if(("birthMonth".equals(fld.getFieldName()) || "birthDate".equals(fld.getFieldName()) || "spouseBirthDate".equals(fld.getFieldName()) || "spouseBirthMonth".equals(fld.getFieldName())) && ("fsLeadPersonalProfile".equals(tableAnchor) || "fsLeadQualificationDetail".equals(tableAnchor))){
    					continue;
    				}
    				if(("fimTtZip".equals(fld.getFieldName()) || "fimTtZipLocatorIdentical".equals(fld.getFieldName()) || "fimTtZipLocator".equals(fld.getFieldName()) || "syncCheckBox".equals(fld.getFieldName())) && "fimTerritory".equals(tableAnchor)){
    					continue;
    				}
    				if("fbc".equals(fld.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(fimLeftUserRoleMap, user_level))) {
						continue;
					}
    				//P_B_71549 starts
    				if(("leadSource2ID".equals(fld.getFieldName()) || "leadSource3ID".equals(fld.getFieldName())) && "fsLeadDetails".equals(tableAnchor)){
    					continue;
    				}
    				//P_B_71549 ends
    				if("File".equals(fld.getDisplayTypeField())) {
    					continue;
    				}
    				String syncWithField = fld.getSyncWithField();
    				boolean canContinue = false;
    				
    				if(StringUtil.isValidNew(syncWithField)) {
    					if(syncWithField.contains(actualTableAnchor)) {
    						continue;
    					} else {
    						try {
    							String parentAnchor = syncWithField.split("##")[0];
    							String otherFieldParent = syncWithField.split("##")[2];
    							String parentFieldName = syncWithField.split("##")[1];
    							FieldMappings fieldMappingsParent = getFieldMappings(parentAnchor);
    							Field parentField = null;
    							if("true".equals(otherFieldParent)) {
    								parentField = fieldMappingsParent.getOtherTableField(parentFieldName);
    							} else {
    								parentField = fieldMappingsParent.getField(parentFieldName);
    							}

    							canContinue = false;

    							SequenceMap syncTotalaMap = parentField.getSyncTotalMap();
    							if(syncTotalaMap != null) {
    								Iterator itr = syncTotalaMap.values().iterator();
    								while(itr.hasNext()) {
    									SyncWithField sync = (SyncWithField)itr.next();
    									if(sync != null) {
    										String tAnchor = sync.getTableAnchor();

    										if(actualTableAnchor.equals(tAnchor)) {
    											canContinue = true;
    											break;
    										}
    									}
    								}
    							}
    						}
    						catch (Exception e) {
    							// TODO: handle exception
    							e.printStackTrace();
    						}
    					}
    				}
    				
    				if(!canContinue) {
    					SequenceMap syncTotalaMap = fld.getSyncTotalMap();
    					if(syncTotalaMap != null) {
    						Iterator itr = syncTotalaMap.values().iterator();
    						while(itr.hasNext()) {
    							SyncWithField sync = (SyncWithField)itr.next();
    							if(sync != null) {
    								String tAnchor = sync.getTableAnchor();

    								if(tableAnchor.equals(tAnchor) || actualTableAnchor.equals(tAnchor)) {
    									canContinue = true;
    									break;
    								}
    							}
    						}
    					}
    				}
					
					if(canContinue) {
						continue;
					}
					
    				
    				String displayName = fld.getDisplayName();
    				/*if(displayName.length() > 45) {
    					displayName = displayName.substring(0, 45) + "...";
    				}*/
    				
    				if(StringUtil.isValidNew(validationType) && !validationType.equals(fld.getValidationType())) {
    					continue;
    				}
    				
    				if(StringUtil.isValidNew(displayType)) {
    					if(displayType.equals(fld.getDisplayTypeField())) {
    						returnValue.append("<option value='"+tableAnchor+"##"+fld.getFieldName()+"##"+fld.isFieldOfOtherTable()+"'>"+LanguageUtil.getString(displayName)+"</option>");
    					}
    				} else {
    					/*if(!fld.isBuildField() && ("Combo".equals(fld.getDisplayTypeField()) || "Radio".equals(fld.getDisplayTypeField()) || "Checkbox".equals(fld.getDisplayTypeField()))) {
        					continue;
        				}*/
    					returnValue.append("<option value='"+tableAnchor+"##"+fld.getFieldName()+"##"+fld.isFieldOfOtherTable()+"'>"+LanguageUtil.getString(displayName)+"</option>");
    				}
    			}
    		}
    	}
    	return returnValue;
    }
    
  //ENH_PW_SMART_QUESTIONS_STARTS
    public static String getSmartQuestionField(String comboName)
    {
        String isInSmartQuestion = "no";
        StringBuffer query = new StringBuffer();
        try
        {
            query = query.append("SELECT FIELD_NAME FROM AUDIT_FORM_MASTER_DATA WHERE FIELD_NAME ='"+comboName+"' UNION SELECT FIELD_NAME FROM AUDIT_MASTER_DATA WHERE FIELD_NAME ='"+comboName+"' UNION SELECT FIELD_NAME FROM AUDIT_QUESTIONS_INFORMATION WHERE FIELD_NAME ='"+comboName+"'");  
            ResultSet rs = QueryUtil.getResult(query.toString(), new Object[]{});
            if(rs != null && rs.next()){
            	isInSmartQuestion = "yes"; 
            }
        }
        catch(Exception e)
        {
        }
        return isInSmartQuestion;
    }
  //ENH_PW_SMART_QUESTIONS_ENDS
    
 //EXTERNAL_FORM_BUILDER : START
    public static String getTabUsedInWebForm(String comboName, String moduleName)
    {
        String isInWebForm = "no";
        String associatedWithWebForm = "no";
        StringBuffer query = new StringBuffer();
        try
        {
            query = query.append("SELECT TAB_USED FROM EXTERNAL_WEB_FORMS ");
            ResultSet rs = QueryUtil.getResult(query.toString(), new Object[]{});
            List webFormUsedTabList=new ArrayList();
            while(rs.next())
            {
                String selectedFiledValue=rs.getString("TAB_USED");
                if(StringUtil.isValid(selectedFiledValue)){
                	StringTokenizer st=new StringTokenizer(selectedFiledValue, "#####");
                	while(st.hasMoreTokens())
                		webFormUsedTabList.add(st.nextToken().trim());
                	
                	if(webFormUsedTabList.contains(comboName)) {
                		isInWebForm="yes";
                		associatedWithWebForm= "yes";
                		break;
                	}
                }
            }
        }
        catch(Exception e)
        {
        }
        return isInWebForm;
    }
 //EXTERNAL_FORM_BUILDER : END
    
    /**
     * P_Enh_Form_Builder_Option_Sequence
     * @author Sourabh Singh
     * @param fieldName
     * @param tableAnchor
     * @return Map
     */
    public Map<String, String> getOptionsOrderMap(String fieldName,String tableAnchor)
    {
    	Map<String, String> optionOrderMap = new LinkedHashMap();
    	String query="SELECT OPTION_ID, OPTION_VALUE FROM FIM_BUILDER_MASTER_DATA WHERE FIELD_NAME = ? AND TABLE_ANCHOR = ? ORDER BY ORDER_NO";
    	try
    	{
    		ResultSet rs=QueryUtil.getResult(query,new String[]{fieldName,tableAnchor}); 
    		while(rs!=null && rs.next())
    		{
    			optionOrderMap.put(rs.getString("OPTION_ID"), rs.getString("OPTION_VALUE"));
    		}
    	} catch (Exception e)
    	{
    	}
		return optionOrderMap;
    }
    
    /**
     * P_Enh_FC-76
     * @author Sourabh Singh
     * @param fieldName
     * @param tableAnchor
     * @param password
     * @return 
     */
    public void updatePiiFieldPassword(String fieldName,String tableAnchor, String passowrd, String formID)
    {
    	String deleteQuery = "DELETE FROM FIELD_LEVEL_PII_PASSWORD WHERE TABLE_ANCHOR = ? AND FIELD_NAME = ? AND FORM_ID = ?";
    	String updateQuery = "INSERT INTO FIELD_LEVEL_PII_PASSWORD (TABLE_ANCHOR, FIELD_NAME, FORM_ID, PASSWORD) VALUES(?,?,?,AES_ENCRYPT(?,'pvm@e20'))";
    	try
    	{
    		 QueryUtil.update(deleteQuery, new String[]{tableAnchor, fieldName, formID});
    		 if(StringUtil.isValid(passowrd))
    		 {
    			 QueryUtil.update(updateQuery, new String[]{tableAnchor, fieldName, formID, passowrd});
    		 }
    	} catch (Exception e)
    	{
    	}
    }
    
    public List getPiiFieldPasswordMap(String formID)
    {
    	List fieldList = new ArrayList();
    	String query = "SELECT FIELD_NAME FROM FIELD_LEVEL_PII_PASSWORD WHERE FORM_ID = ?";
    	try
    	{
    		ResultSet rs = QueryUtil.getResult(query,new String[]{formID}); 
    		while(rs!=null && rs.next())
    		{
    			fieldList.add(rs.getString("FIELD_NAME"));
    		}
    	} catch (Exception e)
    	{
    	}
    	return fieldList;
    }
    
    /**
     * P_Enh_Form_Builder_Option_Sequence
     * @author Sourabh Singh
     * @param orderMap
     * @param fieldName
     * @param tableAnchor
     * @return void
     */
    public void updateOptionsOrder(Map orderMap, String fieldName,String tableAnchor)
    {
    	String insertQuery = "UPDATE FIM_BUILDER_MASTER_DATA SET ORDER_NO = ? WHERE OPTION_ID = ? AND FIELD_NAME = ? AND TABLE_ANCHOR = ?";

    	String key = "";
    	String value = "";
    	String[] queryArray = {insertQuery};
    	List<String[]> paramList = null;
    	String[] paramArray = null;
    	Map<String,List<String[]>> batchMap = BaseUtils.getNewHashMapWithKeyValueType();

    	Set entrySet = orderMap.entrySet();
    	Iterator iterator = entrySet.iterator();
    	while (iterator != null && iterator.hasNext())
    	{
    		Map.Entry entry = (Map.Entry) iterator.next();
    		key = (String) entry.getKey();
    		value = (String) orderMap.get(key);
    		paramArray = new String[]{value,key,fieldName,tableAnchor};
    		
    		if(batchMap.containsKey(FieldNames.ZERO)) {
        		(batchMap.get(FieldNames.ZERO)).add(paramArray);
        	} else {
        		paramList = new ArrayList<String[]>();
        		paramList.add(paramArray);
        		batchMap.put(FieldNames.ZERO, paramList);
        	}
    	}
    	try {
        	QueryUtil.preparedStatementsBatchUpdate(queryArray, batchMap);
        } catch(Exception e) {
        	e.printStackTrace();
        } 
    }
    /**
     * This method creates new xml and table for tabular section corresponding to tab.
     * P_Enh_FormBuilder_Tabular_Section
     * @author Akash Kumar
     * @param request
     * @return Info of tableAnchor and Db Table for newly created xml and Table.
     */
    public Info  addModifyTabularSectionData(HttpServletRequest request){
    	Document tabulardoc = null;
    	File tabularfile = null;
    	Info returnInfo=null;
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	String action 		 	= getRequestValue(request, "action");
    	String moduleName			= getRequestValue(request, FieldNames.MODULE);
    	String subModuleName		= getRequestValue(request, "submoduleName");
    	String randomNoSection      =     getRequestValue(request,"randomNoSection"); //P_B_77002
    	if(!StringUtil.isValidNew(subModuleName)) { 
    		subModuleName		= getRequestValue(request, "subModuleName");
    	}
    	if("mu".equals(moduleName) || "area".equals(moduleName)){//P_Enh_Mu-Entity_FormGenerator
    		subModuleName=moduleName;
    		moduleName="fim";
    		
    	}
    	String secName = getRequestValue(request, BuilderFormFieldNames.SECTION_NAME);
    	String secVal = getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE);
    	String sectionFileName 		 	= null;
    	String tabularSectionName 				= null;
    	String tabularSectionFileLocation   	= null;
    	File   tabularSectionPath 			 	= new File(_baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH);
    	String xmlPath 				= _baseConstants.XML_DIRECTORY+BuilderConstants.BUILDER_TABLE_PATH;
    	String sectionMappingLocation 	= _baseConstants.XML_DIRECTORY+BuilderConstants.TABULAR_SECTION_MAPPING_XML;
    	String subModule			= null;
    	DBQuery tabularSectionQuery		 = null;
    	DBColumn tabularSectionColumns[]	 = null;
    	String sectionTableName		 = null;
    	String randomId			 = null;
    	Node headerTablesNode	= null;
    	try
    	{ 
    		Info moduleInfo = (Info)BuilderFormUtil.getModuleMap().get(handleSubModule(moduleName,subModuleName));
    		if(StringUtil.isValidNew(action) && "add".equals(action))
    		{
    			String sectionTableAnchor 			= getRequestValue(request, BuilderFormFieldNames.SECTION_NAME);
    			String tableName 			= getRequestValue(request, BuilderFormFieldNames.SECTION_NAME);
    			if(sectionTableAnchor == null)
    			{
    				return null;
    			}else{
    				sectionTableAnchor=sectionTableAnchor.substring(sectionTableAnchor.indexOf("_")+1);
    				tableName=tableName.substring(tableName.indexOf("_")+1);
    			}
    			SequenceMap moduleMap = new SequenceMap();
    			location 		= _baseConstants.XML_DIRECTORY+"tables/admin/"+ BuilderConstants.TEMPLATE_TAB_XML;
    			randomId 		= IDGenerator.getNextKey();
    			sectionFileName 	= sectionTableAnchor+randomId+".xml";
    			tabularSectionFileLocation = tabularSectionPath+"/"+sectionFileName;
    			tabularfile 			= new File(location);
    			if(tabularfile.isFile())
    			{
    				this.initBDaoInstance();
    				try
    				{
    					tabulardoc = docBuilder.parse(tabularfile);
    					Node firstChild 		= tabulardoc.getFirstChild();
    					Node tableNameNode 		= getNodeInChildren(firstChild, TableXMLDAO.TABLE_NAME);
    					Node tableDisplayNode 	= getNodeInChildren(firstChild, TableXMLDAO.TABLE_DISPLAY_NAME);
    					Node idFieldNode 		= getNodeInChildren(firstChild, TableXMLDAO.ID_FIELD);

    					String isActive 		= getRequestValue(request, BuilderFormFieldNames.TAB_IS_ACTIVE);
    					String isExportable		= getRequestValue(request, BuilderFormFieldNames.TAB_IS_EXPORTABLE);
    					String addMore		= getRequestValue(request, BuilderFormFieldNames.ADD_MORE);
    					String sectionName  = getRequestValue(request, BuilderFormFieldNames.SECTION_NAME); 
    					sectionTableName 			= "_"+tableName.toUpperCase()+"_"+randomId;
    					tableNameNode.appendChild(tabulardoc.createTextNode(sectionTableName));
    					tableDisplayNode.appendChild(tabulardoc.createTextNode(getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE)));
    					idFieldNode.appendChild(tabulardoc.createTextNode("idField"));
    					NodeList list = tabulardoc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
    					int listLength = list.getLength();
    					if (listLength<1) {
    						return null;
    					}
    					else if (listLength>1) {
    						return null;
    					}
    					headerTablesNode =  list.item(0);

    					Element childElement = tabulardoc.createElement(TableXMLDAO.HEADER);
    					childElement.setAttribute(TableXMLDAO.NAME, getRequestValue(request, BuilderFormFieldNames.SECTION_NAME)+randomNoSection);    //P_B_77002
    					childElement.setAttribute(TableXMLDAO.VALUE, getRequestValue(request, BuilderFormFieldNames.SECTION_VALUE));
    					childElement.setAttribute(TableXMLDAO.ORDER, getRequestValue(request, BuilderFormFieldNames.ORDER));

    					if(StringUtil.isValid(getRequestValue(request, "formTabName"))){
    						childElement.appendChild(getElementNode(tabulardoc, "tab-name", getRequestValue(request, "formTabName")));
    					}
    					childElement.appendChild(getElementNode(tabulardoc, TableXMLDAO.TYPE, getRequestValue(request, BuilderFormFieldNames.TYPE)));
    					childElement.appendChild(getElementNode(tabulardoc, TableXMLDAO.SECTION, getRequestValue(request, BuilderFormFieldNames.SECTION)+randomNoSection));  //P_B_77002
    					childElement.appendChild(getElementNode(tabulardoc, TableXMLDAO.IS_BUILD_SECTION, getRequestValue(request, BuilderFormFieldNames.IS_BUILD_SECTION)));
    					headerTablesNode.appendChild(childElement);
    					newTabFile = new StreamResult(tabularSectionFileLocation);
    					if(!tabularSectionPath.exists())
    					{
    						tabularSectionPath.mkdirs();
    					}
    					source = new DOMSource(tabulardoc);
    					trans.transform(source, newTabFile);
    					tabularfile = null;
    					tabularfile = new File(sectionMappingLocation);
    					if(tabularfile.isFile())
    					{
    						// tabName = tabularfile.substring(0, tabularfile.indexOf(".xml"));
    						tabulardoc 	= docBuilder.parse(tabularfile);
    						//create backup of tabModules.xml file

    						String fileLocBackup = sectionMappingLocation.substring(0, sectionMappingLocation.indexOf(".xml"));
    						resultBackup = new StreamResult(fileLocBackup+"_backup.xml");
    						source = new DOMSource(tabulardoc);
    						trans.transform(source, resultBackup);

    						firstChild = tabulardoc.getFirstChild();
    						Element newTab = tabulardoc.createElement(BuilderFormFieldNames.TABLE_MAPPING);
    						setTagAttr(newTab, TableXMLDAO.TABLE_ANCHOR,sectionFileName.substring(0, sectionFileName.indexOf(".xml")));
    						setTagAttr(newTab, TableXMLDAO.FILELOCATION,"tables/buildertabs/"+sectionFileName);
    						firstChild.appendChild(newTab);
    						originalFile = new StreamResult(sectionMappingLocation);
    						source = new DOMSource(tabulardoc);
    						trans.transform(source, originalFile);
    						tabulardoc.normalize();
    					}
    					tabularfile = null;
    					tabularfile = new File(xmlPath+sectionFileName);
    					if(tabularfile.isFile())
    					{
    						tabularSectionName = sectionFileName.substring(0, sectionFileName.indexOf(".xml"));
    						tabulardoc = docBuilder.parse(tabularfile);
    						resultBackup = new StreamResult(tabularSectionName+"_backup.xml");
    						source = new DOMSource(tabulardoc);
    						trans.transform(source, resultBackup);


    						firstChild = tabulardoc.getFirstChild();

    						//create foreign table starts
    						Element parentNode = tabulardoc.createElement(TableXMLDAO.FOREIGN_TABLES);

    						Element newTab = tabulardoc.createElement(TableXMLDAO.FOREIGN_TABLE);
    						//setting attribute for foreign table
    						//P_Enh_Mu-Entity_FormGenerator starts
    						if("mu".equals(subModuleName)){
    							setTagAttr(newTab, TableXMLDAO.NAME,"fimMuDocuments");
    						}else if("area".equals(subModuleName)){
    							setTagAttr(newTab, TableXMLDAO.NAME,"areaDocuments");
    						}else{
    							setTagAttr(newTab, TableXMLDAO.NAME,moduleName+"Documents");
    						}//P_Enh_Mu-Entity_FormGenerator ends
    						setTagAttr(newTab, TableXMLDAO.TABLE_EXPORT,"false");

    						Element childTab = tabulardoc.createElement(TableXMLDAO.LINK_FIELD);

    						//create link fields starts

    						setTagAttr(childTab, TableXMLDAO.THIS_FIELD,"idField");
    						if("mu".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
    							setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,FieldNames.FIM_MU_TAB_PRIMARY_ID);
    						}else if("area".equals(subModuleName)){
    							setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,FieldNames.AREA_TAB_PRIMARY_ID);
    						}else{
    						setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,"tabPrimaryId");
    						}//P_Enh_Mu-Entity_FormGenerator ends
    						newTab.appendChild(childTab); //link field added

    						if(moduleInfo!=null)
    						{
    							childTab = tabulardoc.createElement(TableXMLDAO.LINK_FIELD);
    							if("area".equals(subModuleName)){//P_Enh_Mu-Entity_FormGenerator starts
    								setTagAttr(childTab, TableXMLDAO.THIS_FIELD,ModuleUtil.MODULE_KEY.KEY_FIM_AREA);	
    							}else{//P_Enh_Mu-Entity_FormGenerator ends
    								setTagAttr(childTab, TableXMLDAO.THIS_FIELD,moduleInfo.get("keyField"));
    							}
    							//setTagAttr(childTab, TableXMLDAO.THIS_FIELD,moduleInfo.get("keyField"));
    							setTagAttr(childTab, TableXMLDAO.FOREIGN_FIELD,moduleInfo.get("keyField"));
    							newTab.appendChild(childTab); //link field added
    						}

    						parentNode.appendChild(newTab); //foreign table added
    						firstChild.appendChild(parentNode);
    						//creating foreign table in parent table  starts
    						NodeList foreignList = doc.getElementsByTagName(TableXMLDAO.FOREIGN_TABLES);
    						Node foreignNodeParent =  foreignList.item(0);
    						Element newTabParent = doc.createElement(TableXMLDAO.FOREIGN_TABLE);

    						//setting attribute for foreign table
    						setTagAttr(newTabParent, TableXMLDAO.NAME,tabularSectionName);
    						setTagAttr(newTabParent, TableXMLDAO.TABLE_EXPORT,"true");

    						Element childTabParent = doc.createElement(TableXMLDAO.LINK_FIELD);

    						//create link fields starts

    						setTagAttr(childTabParent, TableXMLDAO.THIS_FIELD,getFieldMappings(tableAnchor).getIdField()[0].getFieldName());
    						setTagAttr(childTabParent, TableXMLDAO.FOREIGN_FIELD,"tabPrimaryId");

    						newTabParent.appendChild(childTabParent); //link field added
    						childTabParent = doc.createElement(TableXMLDAO.LINK_FIELD);

    						//create link fields starts
    						if(moduleInfo!=null)
    						{
    							setTagAttr(childTabParent, TableXMLDAO.THIS_FIELD,moduleInfo.get("keyField"));
    							setTagAttr(childTabParent, TableXMLDAO.FOREIGN_FIELD,moduleInfo.get("keyField"));
    						}

    						newTabParent.appendChild(childTabParent); 
    						//foreignNodeParent.appendChild(newTabParent); 
    						foreignNodeParent.insertBefore(newTabParent, foreignNodeParent.getFirstChild().getNextSibling());
    						//creating foreign table in parent table  ends

    						if(moduleInfo!=null)
    						{
    							Element mainField = tabulardoc.createElement(TableXMLDAO.FIELD);
    							mainField.setAttribute(TableXMLDAO.SUMMARY,"true");
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.FIELD_NAME, moduleInfo.get(TableXMLDAO.FIELD_NAME)));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DISPLAY_NAME, moduleInfo.get(TableXMLDAO.DISPLAY_NAME)));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DB_FIELD, moduleInfo.get(TableXMLDAO.DB_FIELD)));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DATA_TYPE, moduleInfo.get(TableXMLDAO.DATA_TYPE)));
    							firstChild.appendChild(mainField);

    							mainField = tabulardoc.createElement(TableXMLDAO.FIELD);
    							mainField.setAttribute(TableXMLDAO.SUMMARY,"true");
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.FIELD_NAME, FieldNames.TAB_PRIMARY_ID));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DISPLAY_NAME, "Tab Primary Id"));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DB_FIELD, "TAB_PRIMARY_ID"));
    							mainField.appendChild(getElementNode(tabulardoc, TableXMLDAO.DATA_TYPE,"Integer"));
    							firstChild.appendChild(mainField);
    						}

    						tabularSectionQuery	 = new DBQuery();
    						int size=2;
    						if(ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName)){ 
    							size=4;
    						}else if("fim".equals(moduleName)){
    							size=5;
    						}else if(ModuleUtil.MODULE_NAME.NAME_CM.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_LEAD.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_ACCOUNT.equals(moduleName) || ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName)){ 
    							size=4;
    						}
    						tabularSectionColumns       = new DBColumn[size];

    						tabularSectionColumns[0] = new DBColumn("ID_FIELD");
    						tabularSectionColumns[0].setColPrimary(true);
    						tabularSectionColumns[0].setColType(SQLQueryGenerator.INT);
    						tabularSectionColumns[0].setColTypeVal("11");

    						if(moduleInfo!=null)
    						{
    							tabularSectionColumns[1] = new DBColumn(moduleInfo.get(TableXMLDAO.DB_FIELD));
    							tabularSectionColumns[1].setColType(SQLQueryGenerator.INT);
    							tabularSectionColumns[1].setColTypeVal("11");
    							tabularSectionColumns[1] = new DBColumn("TAB_PRIMARY_ID");
    							tabularSectionColumns[1].setColType(SQLQueryGenerator.INT);
    							tabularSectionColumns[1].setColTypeVal("100");
    							if(ModuleUtil.MODULE_NAME.NAME_SCHEDULER.equals(moduleName)){
    								tabularSectionColumns[2] = new DBColumn("CONTACT_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    							}
    							if(ModuleUtil.MODULE_NAME.NAME_FIM.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[2].setColType("TIMESTAMP");
    								tabularSectionColumns[2].setColTypeVal("null");
    								tabularSectionColumns[2].setColDefault("CURRENT_TIMESTAMP");
    								//P_Enh_Mu-Entity_FormGenerator starts
    								if("mu".equals(subModuleName)){
    									tabularSectionColumns[3] = new DBColumn("MU_ID");
    									tabularSectionColumns[3].setColType(SQLQueryGenerator.INT);
    									tabularSectionColumns[3].setColTypeVal("11");
    								}else if("area".equals(subModuleName)){
    									tabularSectionColumns[3] = new DBColumn("AREA_INFO_ID");
    									tabularSectionColumns[3].setColType(SQLQueryGenerator.INT);
    									tabularSectionColumns[3].setColTypeVal("11");
    								}else{
    									tabularSectionColumns[3] = new DBColumn("ENTITY_ID");
    									tabularSectionColumns[3].setColType(SQLQueryGenerator.INT);
    									tabularSectionColumns[3].setColTypeVal("11");
    								}//P_Enh_Mu-Entity_FormGenerator ends
    								tabularSectionColumns[4] = new DBColumn("FRANCHISEE_NO");
    								tabularSectionColumns[4].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[4].setColTypeVal("11");
    							}
    							if(ModuleUtil.MODULE_NAME.NAME_FS.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("LEAD_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    								tabularSectionColumns[3] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[3].setColType("TIMESTAMP");
    								tabularSectionColumns[3].setColTypeVal("null");
    								tabularSectionColumns[3].setColDefault("CURRENT_TIMESTAMP");
    							}if(ModuleUtil.MODULE_NAME.NAME_CM.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("CONTACT_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    								tabularSectionColumns[3] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[3].setColType("TIMESTAMP");
    								tabularSectionColumns[3].setColTypeVal("null");
    								tabularSectionColumns[3].setColDefault("CURRENT_TIMESTAMP");
    							}if(ModuleUtil.MODULE_NAME.NAME_LEAD.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("LEAD_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    								tabularSectionColumns[3] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[3].setColType("TIMESTAMP");
    								tabularSectionColumns[3].setColTypeVal("null");
    								tabularSectionColumns[3].setColDefault("CURRENT_TIMESTAMP");
    							}if(ModuleUtil.MODULE_NAME.NAME_ACCOUNT.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("COMPANY_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    								tabularSectionColumns[3] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[3].setColType("TIMESTAMP");
    								tabularSectionColumns[3].setColTypeVal("null");
    								tabularSectionColumns[3].setColDefault("CURRENT_TIMESTAMP");
    							}if(ModuleUtil.MODULE_NAME.NAME_OPPORTUNITY.equals(moduleName)){ 
    								tabularSectionColumns[2] = new DBColumn("OPPORTUNITY_ID");
    								tabularSectionColumns[2].setColType(SQLQueryGenerator.INT);
    								tabularSectionColumns[2].setColTypeVal("11");
    								tabularSectionColumns[3] = new DBColumn("CREATION_DATE");
    								tabularSectionColumns[3].setColType("TIMESTAMP");
    								tabularSectionColumns[3].setColTypeVal("null");
    								tabularSectionColumns[3].setColDefault("CURRENT_TIMESTAMP");
    							}
    						}

    						tabularSectionQuery.setTableName(sectionTableName);
    						tabularSectionQuery.setDBColumns(tabularSectionColumns);
    						tabularSectionQuery.setDDLType(SQLQueryGenerator.CREATE);
    						QueryUtil.alterDBTable(SQLQueryGenerator.getDdlQuery(tabularSectionQuery));
    						originalFile = new StreamResult(xmlPath+sectionFileName);
    						source = new DOMSource(tabulardoc);
    						trans.transform(source, originalFile);
    						tabulardoc.normalize();

    					}
    					refreshTabularSectionMappings();
    				}
    				catch(Exception e)
    				{
    					return null;
    				}
    			}
    			else
    			{
    				return null;
    			}
    		}else if(StringUtil.isValidNew(action) && "modify".equals(action)){
    			tabularfile=null;
    			sectionFileName=getRequestValue(request, "sectionFileName");
    			tabularfile = new File(xmlPath+sectionFileName);
    			if(tabularfile.isFile())
    			{
    				tabularSectionName = sectionFileName.substring(0, sectionFileName.indexOf(".xml"));
    				tabulardoc = docBuilder.parse(tabularfile);
    				resultBackup = new StreamResult(tabularSectionName+"_backup.xml");
    				source = new DOMSource(tabulardoc);
    				trans.transform(source, resultBackup);
    				NodeList displayNameList = 	tabulardoc.getElementsByTagName(TableXMLDAO.TABLE_DISPLAY_NAME);
    				Node displayNameNode = displayNameList.item(0);
    				displayNameNode.setTextContent(secVal);
    				NodeList list = tabulardoc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
    				int listLength = list.getLength();
    				if (listLength<1) {
    					return null;
    				}
    				else if (listLength>1) {
    					return null;
    				}
    				headerTablesNode =  list.item(0);
    				Node[] headerNodes	= getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
    				String name			= null;
    				String value		= null;
    				for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
    					name		= getAttributeValue(headerNodes[loop], TableXMLDAO.NAME);
    					if(secName.equals(name)) {
    						setTagAttr(headerNodes[loop], TableXMLDAO.VALUE,secVal);  
    						break;
    					}
    				}
    				originalFile = new StreamResult(xmlPath+sectionFileName);
    				source = new DOMSource(tabulardoc);
    				trans.transform(source, originalFile);
    				tabulardoc.normalize();

    			}
    			removeFieldMappings(tabularSectionName); //dki-20160824-563
    		}
    	}catch(Exception e){
    	}finally
		{
			docBuilder = null;
			tabulardoc=null;
			tabularfile=null;
		}
    	returnInfo=new Info();
    	returnInfo.set("tableAnchor",tabularSectionName);
    	returnInfo.set("dbTable",sectionTableName);
    	return returnInfo;

    }
    
    public static List getConfiguredList(){
    	
    	 String [] fieldArr=new String[]{"contactType","contactFirstName","contactLastName","address","city","country","zipcode","state","emailIds","phoneNumbers","mobileNumbers","cmSource1ID","cmSource2ID","cmSource3ID","marketingCodeId","ownerType","leadFirstName","leadLastName","comments"};
    	 
    	 return Arrays.asList(fieldArr);
    }

    /**
     * This function is used to update field of tabular sections for display.
     *P_Enh_FormBuilder_Tabular_Section
     * @author Akash Kumar
     * @param isActive
     * @param fieldName
     * @param dbColumnName
     */
    public static void updateTabularSectionDisplayColumns(String isActive, String fieldName, String dbColumnName,String displayName) {
    	String isActiveOrDeactive = "yes";
    	String updateQuery="";
    	List<String> paramList=new ArrayList<String>();
    	if(StringUtil.isValidNew(isActive)){
    		if("true".equals(isActive)) {
    			isActiveOrDeactive = "no";
    		}
    		updateQuery = "UPDATE TABULAR_SECTION_DISPLAY_COLUMN SET IS_ACTIVE=? WHERE FIELD_NAME=? AND DISPLAY_VALUE=?";
    		paramList.add(isActiveOrDeactive);
    	}else if(StringUtil.isValidNew(displayName)){
    		updateQuery = "UPDATE TABULAR_SECTION_DISPLAY_COLUMN SET DISPLAY_NAME=? WHERE FIELD_NAME=? AND DISPLAY_VALUE=?";
    		paramList.add(displayName);	
    	}
    	paramList.add(fieldName);
    	paramList.add(dbColumnName);
    	try {
    		QueryUtil.update(updateQuery, paramList.toArray(new String[paramList.size()]));
    	} catch (Exception e) {
    		// TODO: handle exception
    	}
    }
    /**
     * This method returns configured/not configered columns for tabular section
     * P_Enh_FormBuilder_Tabular_Section
     * @author Akash Kumar
     * @param tableAnchor
     * @param isSelected
     * @param isNotSelected
     * @return
     */
    public Map getConfiguredSummaryColumns(String tableAnchor,
    		String isSelected, String isNotSelected) {
    	Map<String, Map> criteriaMap = null;
    	ResultSet result = null;
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	Map finalMap= new HashMap();
    	Map <String,String> valueMap=null;
    	try {
    		criteriaMap = new LinkedHashMap<String, Map>();
    		StringBuffer query = new StringBuffer(
    				"SELECT DISPLAY_ID,DISPLAY_NAME,DISPLAY_VALUE,MODULE_ID,IS_SELECTED,ORDER_SEQUENCE,IS_PII_ENABLED,TABLE_NAME,FIELD_NAME FROM TABULAR_SECTION_DISPLAY_COLUMN WHERE IS_ACTIVE = 'yes' ");
    		if (StringUtil.isRequestParamValid(tableAnchor)) {
    			query.append(" AND TABLE_NAME='").append(tableAnchor).append("' ");
    		}
    		if (StringUtil.isRequestParamValid(isSelected)) {
    			query.append(" AND IS_SELECTED IN (").append(isSelected).append(") ");
    		}
    		if (StringUtil.isRequestParamValid(isNotSelected)) {
    			query.append(" AND DISPLAY_VALUE NOT IN ('").append(isNotSelected).append("') ");
    		}
    		query.append(" ORDER BY  ORDER_SEQUENCE ");
    		result = QueryUtil.getResult(query.toString(), null);
    		int count=0;
    		while (result.next()) {

    			valueMap = new HashMap<String, String>();
    			valueMap.put("displayId", result.getString("DISPLAY_ID"));
    			valueMap.put("displayName", result.getString("DISPLAY_NAME"));
    			valueMap.put("displayValue", result.getString("DISPLAY_VALUE"));
    			valueMap.put("isSelected", result.getString("IS_SELECTED"));
    			valueMap.put("orderSequence",
    					result.getString("ORDER_SEQUENCE"));
    			valueMap.put("fieldName", result.getString("FIELD_NAME"));
    			valueMap.put("moduleID", result.getString("MODULE_ID"));
    			valueMap.put("piiEnabled", result.getString("IS_PII_ENABLED")); 
    			if(finalMap.containsKey(result.getString("TABLE_NAME"))){
    				criteriaMap= (Map)finalMap.get(result.getString("TABLE_NAME"));
    				criteriaMap.put("" + (++count), valueMap);
    			}
    			else{
    				criteriaMap=new LinkedHashMap<String, Map>();
    				criteriaMap.put("" + (++count), valueMap);
    				finalMap.put(result.getString("TABLE_NAME"),criteriaMap);
    			}



    		}

    	} catch (Exception e) {
    	} finally {
    		if (result != null) {
    			result = null;
    		}
    	}
    	return finalMap;
    }	
    /**
     *
     * P_Enh_FormBuilder_Tabular_Section
     * @author Akash Kumar
     * @param tableAnchor
     * @return
     */
    public Boolean isTabularSection(String tableAnchor){

        boolean isTabular = false;
        ResultSet  dataResult = null;
        try
        {
            String query 		= "SELECT COUNT(DISPLAY_ID) AS TABULAR_SECTION_COUNT FROM TABULAR_SECTION_DISPLAY_COLUMN WHERE TABLE_NAME=? ";//BB_Naming_Convention

            dataResult 	= QueryUtil.getResult(query, new String[]{tableAnchor});

            if(dataResult != null && dataResult.next())
            {
                if(Integer.parseInt(dataResult.getString("TABULAR_SECTION_COUNT"))>0)
                {
                	isTabular = true;
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }
        return isTabular;
    
    }
    public Boolean removeTabularSectionDocument(HttpServletRequest request){
    	Document tabDoc = null;
    	File tabFile = null;
    	Element rootTab=null;	
    	String fileLocBackup = "";
    	HashMap dataMap = new HashMap();
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
        try {
            String fileLoc = getRequestValue(request, BuilderFormFieldNames.FILE_LOCATION);
            String val = getRequestValue(request, BuilderFormFieldNames.FIELD_NAME);
            String secName = getRequestValue(request, "secName");
            String tName = getRequestValue(request, BuilderFormFieldNames.TABLE_NAME);
            String docName = getRequestValue(request, "docName");
            String archiveTable	= null;//TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS

            if(StringUtil.isValidNew(fileLoc)) {
                tabFile = new File(fileLoc);
                if(tabFile.isFile()) {
                    this.initBDaoInstance();
                    try {
//    					factory = DocumentBuilderFactory.newInstance();
//    					docBuilder = factory.newDocumentBuilder();
                        tabDoc = docBuilder.parse(tabFile);
                        //TAB_ARCHIVE_DATA,ENH_MODULE_CUSTOM_TABS starts
                        if(tabDoc!=null && tabDoc.getFirstChild()!=null)
                        {
                            archiveTable = XMLUtil.getTagText(tabDoc.getFirstChild(),TableXMLDAO.TABLE_ARCHIVE_NAME);
                            if(StringUtil.isValid(archiveTable))
                            {
                                request.setAttribute("archiveTable", archiveTable);
                            }
                        }
                        fileLocBackup = fileLoc.substring(0, fileLoc.indexOf(".xml"));
                        resultBackup = new StreamResult(fileLocBackup +  "_backup.xml");

                        source = new DOMSource(tabDoc);
                        trans.transform(source, resultBackup);
                        resultBackup = new StreamResult(fileLocBackup +  "_copy.xml");
                        dataMap.put("newPath", fileLocBackup +  "_copy.xml");

                        source = new DOMSource(tabDoc);
                        trans.transform(source, resultBackup);
                        
                        tabFile = new File(fileLocBackup +  "_copy.xml");
                        if(tabFile.isFile()) {
                        	factory = null;
                        	docBuilder = null;
                        	this.initBDaoInstance();
                        	tabDoc = docBuilder.parse(tabFile);
                        }
                    } catch(Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            rootTab = tabDoc.getDocumentElement();
            if(StringUtil.isValid(docName)) {
                String section = getRequestValue(request, "section");

                Node headerTablesNode = null;
                NodeList list = tabDoc.getElementsByTagName(TableXMLDAO.TABLE_HEADER_MAP);
                int listLength = list.getLength();
                if (listLength<1) {
                    return false;
                }
                else if (listLength>1) {
                    return false;
                }
                headerTablesNode =  list.item(0);

                Node[] headerNodes	= getNodesInChildren(headerTablesNode, TableXMLDAO.HEADER);
                for (int loop=0; headerNodes != null && loop < headerNodes.length; loop++) {
                    String fieldSection		= XMLUtil.getTagText(headerNodes[loop], TableXMLDAO.SECTION);
                    if(section.equals(fieldSection)) {
                        Node[] documentsNode	=  XMLUtil.getNodesInChildren(headerNodes[loop], TableXMLDAO.DOCUMENTS);
                        for (int loopdocs=0; documentsNode != null && loopdocs < documentsNode.length; loopdocs++) {
                            Node[] docFldNodes	=  XMLUtil.getNodesInChildren(documentsNode[loopdocs], TableXMLDAO.DOCUMENT);
                            for (int loopdocsfld=0; docFldNodes != null && loopdocsfld < docFldNodes.length; loopdocsfld++) {
                                String documentName = getAttributeValue( docFldNodes[loopdocsfld], TableXMLDAO.NAME);
                                if(documentName.equals(docName)) {
                                    documentsNode[loopdocs].removeChild(docFldNodes[loopdocsfld]);
                                    break;
                                }
                            }
                        }
                    }
                }
            } 
            tabDoc.normalize();
            result = new StreamResult(fileLocBackup +  ".xml");
            source = new DOMSource(tabDoc);
            trans.transform(source, result);
            removeFieldMappings(getRequestValue(request, BuilderFormFieldNames.TABULAR_SECTION_TABLE_NAME));
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            tabFile = null;
            tabDoc = null;
            rootTab = null;
            result = null;
            source = null;
        }
        return true;
    
    }
    /**
     * This method is used for checking if custom tab belongs to Mu/Entity Tab
     * //P_Enh_Mu-Entity_FormGenerator
     * @author Akash Kumar
     * @param tableAnchor
     * @return true/false
     */
    public boolean isMuTab(String tableAnchor)
    {
        boolean isMuTab = false;
        ResultSet  dataResult = null;
        try
        {
            String query 		= "SELECT MODULE FROM BUILDER_WEB_FORMS WHERE IS_CUSTOM='Y' AND TABLE_ANCHOR=?";

            dataResult 	= QueryUtil.getResult(query, new String[]{tableAnchor});

            if(dataResult != null && dataResult.next())
            {
                if("mu".equals(dataResult.getObject("MODULE")))
                {
                    isMuTab = true;
                }
            }
        }
        catch(Exception se)
        {
        }
        finally
        {

            QueryUtil.releaseResultSet(dataResult);
        }
        return isMuTab;
    }
    //Issue with restoring Deleted Leads starts
    public static boolean updateDeletedLeadLogs(String fieldName,String tableName,String moduleName)
    {
    	boolean flag=false;
    	StringBuffer deleteQuery=null;
    	try
    	{
    		FieldMappings fMapping=DBUtil.getInstance().getFieldMappings(tableName);
    		if(fMapping!=null){
    			Field fld=fMapping.getField(fieldName);
    			tableName=fMapping.getTableName();
    			fieldName=fld.getDbField();
    			deleteQuery=new StringBuffer();
    			deleteQuery.append("UPDATE DELETED_RECORDS_LOGS SET FORM_BUILDER_DELETED_FIELDS=(CASE WHEN FORM_BUILDER_DELETED_FIELDS IS NULL THEN '"+tableName+"@@@"+fieldName+"' ELSE CONCAT(FORM_BUILDER_DELETED_FIELDS,',','"+tableName+"@@@"+fieldName+"') END ) WHERE COLUMN_NAME  LIKE '%"+fieldName+"%'  AND TABLE_NAME LIKE '%"+tableName+"%' AND MODULE_NAME='Franchise Sales'");
    			int updatedRecords=QueryUtil.update(deleteQuery.toString(), null);
    			if(updatedRecords>0){
    				flag=true;
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		flag=false;
    	}
    	return flag;
    }
    //Issue with restoring Deleted Leads ends
    
    
    public static String getAllParsingkeywords(String[] includedkeywords,String fldName,String tableName){     //P_Enh_Custom_Parsing_Keywords_FC-286 starts
    	
    	String query="select GROUP_CONCAT(KEYWORDS SEPARATOR ','),FIELD_DB_CLOUMN_NAME,FIELD_DB_TABLE_NAME,TABLE_ANCHOR,FIELD_NAME,KEYWORDS from FS_LEAD_PARSING_KEYWORD " +
    			"where KEYWORDS_TYPE='0'" +
    			"AND FIELD_DB_TABLE_NAME <>'"+tableName+"' AND FIELD_DB_CLOUMN_NAME<>'"+fldName+"'"+
    			" AND KEYWORDS IN ("+StringUtil.toCommaSeparatedWithSQuotesWithoutSpace(includedkeywords)+") " +
    				"GROUP BY FIELD_DB_CLOUMN_NAME;";
    	
    	StringBuilder dupicationAlert= new StringBuilder();    	
    		//for hardcoded fields keyword as given by priyank sir  	
    	String address=",Street,Addr,".toLowerCase();
    	String phone=",Phone,tel,contact number,contact no.,".toLowerCase();   //cell,mobile,
    	String homePhone=",home phone, eve phone, alternate phone number, alternate no.,".toLowerCase();
    	String bestTimeToContact= ",best time,contact time,call time,best contact,call at,".toLowerCase();
    	String liquidCap=",liquid,capital,initial,income,cash, investment available,money to invest,".toLowerCase();
    	String investTimeFrame =",open time,time frame,time range,start date,business time,Plan on starting business,".toLowerCase();

    	//for hardcoded fields keyword ends     	
		try {
			FieldMappings fMapping =null;
			fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping("fsLeadDetails");
			if(fMapping !=null){
			for (int i = 0; i < includedkeywords.length; i++) {
	    		
	    		if(address.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("address")+" has ").append(includedkeywords[i]+", ");    			
	    		}
	    		else if(phone.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("phone")+" has ").append(includedkeywords[i]+", ");    			
	    		}
	    		else if(homePhone.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("homePhone")+" has ").append(includedkeywords[i]+", ");    			
	    		}
	    		else if(bestTimeToContact.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("bestTimeToContact")+" has ").append(includedkeywords[i]+", ");    			
	    		}
	    		else  if(liquidCap.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("liquidCapitalMax")+" has ").append(includedkeywords[i]+", ");    			
	    		}
	    		else if(investTimeFrame.indexOf(","+includedkeywords[i].trim()+",") > -1){
	    			dupicationAlert.append(fMapping.getDisplayName("investTimeframe")+" has ").append(includedkeywords[i]+", ");    			
	    		}
			}
			
			ResultSet rset =QueryUtil.getResult(query,null);
			if (rset.next()) {
				 fMapping = BuilderFormWebImpl.getInstance().getBuilderFieldsMapping(rset.getString("TABLE_ANCHOR"));
				if("preferredLocation".equals(rset.getString("FIELD_NAME"))){
					dupicationAlert.append("Preferred Location " +" has ");
				}else{
					dupicationAlert.append(fMapping.getDisplayName(rset.getString("FIELD_NAME")) +" has ");
				}
				
				dupicationAlert.append(rset.getString("KEYWORDS"));

				while (rset.next()) {   // is there is a result then duplicate data is
										// present
					
					if("preferredLocation".equals(rset.getString("FIELD_NAME")))
					{	
						dupicationAlert.append(", Preferred Location " + " has ");
						dupicationAlert.append(rset.getString("KEYWORDS"));
					}
					if(StringUtil.isValid(fMapping.getDisplayName(rset.getString("FIELD_NAME")))){  //P_B_78447
					dupicationAlert.append(", "+fMapping.getDisplayName(rset.getString("FIELD_NAME")) +" has ");

					dupicationAlert.append(rset.getString("KEYWORDS"));
					}
				}
			}
			if(dupicationAlert.toString().endsWith(", ")){
				dupicationAlert= new StringBuilder(dupicationAlert.substring(0, dupicationAlert.length()-2));
			}
			}
			
		} catch (Exception e) {
		}

		return dupicationAlert.toString();
  	  
    }
    
public static void updateParsingKeywordStatus(HashMap dataMap,String asActive) {
	String tableAnchor=(String)dataMap.get("tableAnchor");
	String fieldName=(String)dataMap.get(BuilderFormFieldNames.FIELD_NAME);
	asActive="true".equals(asActive)?"N":"Y";
	String query="UPDATE FS_LEAD_PARSING_KEYWORD SET IS_ACTIVE=? WHERE TABLE_ANCHOR='"+tableAnchor+"' AND FIELD_NAME='"+fieldName+"'";
	try {
		QueryUtil.update(query, new String[]{asActive});
	} catch (Exception e) {
	}
	
}    																																//P_Enh_Custom_Parsing_Keywords_FC-286 ends

public static void updateTabularFields(HashMap dataMap,String asActive) {
	String tableAnchor=(String)dataMap.get("tableAnchor");
	String fieldName=(String)dataMap.get(BuilderFormFieldNames.FIELD_NAME);
	asActive="true".equals(asActive)?"N":"Y";
	String query="UPDATE CONFIGURE_TABULAR_FIELDS SET IS_ACTIVE=? WHERE TABLE_ANCHOR='"+tableAnchor+"' AND FIELDS='"+fieldName+"'";
	try {
		QueryUtil.update(query, new String[]{asActive});
	} catch (Exception e) {
	}
	
}   
    
    
}
