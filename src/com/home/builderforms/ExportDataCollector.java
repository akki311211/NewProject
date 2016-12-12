package com.home.builderforms;

//Import statements

import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.home.builderforms.CommonMgr;
import com.home.builderforms.RegionMgr;
import com.home.builderforms.CustomFormWebImpl;
import com.home.builderforms.BuilderFormFieldNames;
import com.home.builderforms.CustomFormFieldNames;
import com.home.builderforms.BuilderCustomTab;
import com.home.builderforms.UserRoleMap;
import com.home.builderforms.AESencrypt;
import com.home.builderforms.Constants;
import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.DBUtil;
import com.home.builderforms.DateUtil;
import com.home.builderforms.FieldNames;
import com.home.builderforms.InfoFactory;
import com.home.builderforms.LanguageUtil;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.ModuleUtil.MODULE_DISPLAY;
import com.home.builderforms.ModuleUtil.MODULE_NAME;
import com.home.builderforms.NewPortalUtils;
import com.home.builderforms.NumberFormatUtils;
import com.home.builderforms.PortalUtils;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.SequenceMapFactory;
import com.home.builderforms.StringUtil;
import com.home.builderforms.StrutsUtil;
import com.home.builderforms.TableAnchors;
import com.home.builderforms.TimeZoneUtils;
import com.home.builderforms.BaseFieldNames;//CUSTOM_REPORT_SORTING_ISSUE
import com.home.builderforms.DependentTable;
import com.home.builderforms.DocumentMap;
import com.home.builderforms.Documents;
import com.home.builderforms.Field;
import com.home.builderforms.FieldMappings;
import com.home.builderforms.ForeignTable;
import com.home.builderforms.HeaderField;
import com.home.builderforms.HeaderMap;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import com.home.builderforms.sqlqueries.SQLUtil;



/**

 * $Header:  com.appnetix.app.portal.export.ExportDataCollector
 * $Author: priyak $AmitGokhru
 * $Version:  $1.1
 * $Date: 2016/11/15 12:38:50 $8 May 2004
 *	$Modified By : ManishS
 * ====================================================================
 * Basic Description/Data Flow of Bean
	This class execute the query given to it and return the data packed in the SequenceMap.
	Data in the SequenceMap will make a tree sturcture. The root of the tree will be maintable record
	and with the child having foreign table records.

 * Other's beans called with methods
	QueryUtil
		getResult()
	ExportDataManipulator

 * @auther	Abishek Singhal 
 * @modified 18 Sep 2006 to diplay  the total number of records regarding OTRS-2006090510000105 of D&Y
 * 
 * 
 * $Updated By :  Abhishek Gupta
 * $Updated Date: May 2012
 * Changes respect to Form Builder enhancement to make more generic solution of the problems
 * This becomes applicable after Both Import / Manipulation and Export utilities concept merged in Single XML file.
 * Also changes for transform Fields value 
 * 
 
------------------------------------------------------------------------------------------------------------
Version No.			Date		By				Against					Function Changed    Comments
------------------------------------------------------------------------------------------------------------
P_E_DATE_FORMAT   	30/05/2008  Nikhil Verma   	Date Format enhancement.
P_FIM_B_37183		16/07/2008	Ravindra Verma	Bug 37183					Sorting was not working in FIM export.
P_FS_B_36928		16/07/2008	Ravindra Verma	Bug 37183					Sorting was not working in FS export.
P_FS_E_EXPORT_DATE_NUM  10 Sep,2008  Sanjeev k   for makking excel seet cell as date type
P_FS_E_ADVNCSEARCH	Sep 18,2008		Manoj Kumar			Changes Implemented  to redirect the outcome to lead summary page.
P_E_FIM_58658     10/07/2009  Nikhil Verma   	Task Trigger FIM enhancement.  transform() 
P_E_FS_INTER     23/07/2009   Ram Avtar        enhancement                   for internationalization of FS Export
P_FS_B_55233		07/01/2010	Nikhil Verma	Bug 55233					Sorting default icon is not appearing
P_E_EXPORT_CURRENCY 14/01/2010 Ram Avtar		Bug 51379					Commented to remove Duplicate Curency in Export
P_ADM_E_7864            31 Dec 2010     Praveen Khare       added method to generate export status log
P_CRPT_E_28062011 28/06/2011    Vikram                                          to correct the number of records in custon reports
P_FIM_E_CUSTOM_TAB		24Oct2011		Devendra Kant Porwal		Enh			New Custom Tab in Fim
P_FS_B_15146    		15 Nov 2012		Dravit Gupta		bug added chk for excel
P_FS_B_15497			17 Dec 2012		Dravit Gupta		Check added to remove extra rowcount if data is not present for that row.
BBEH_FOR_SMC_OPTIMIZATION  10/04/2013  Rohit Jain    Optimization in Excel Sheet,Export CSV FILE, Export as XML for download large amount of data without Heap Space.
P_CM_B_26948        22/08/2013          prashant Malik        "Activity / Campaign Code" check if pilot is off
P_CM_B_36610		3/6/2014			Chinmay Pareek		Bug				Go to contact manager > report, create a custom report and in field to display we take company name and number of employees and create report so in number of employees it is showing junk values
P_CM_B_38410		3/6/2014			Chinmay Pareek		Bug				The date field (Added through the form generator) which is searched through the Custom report is not having correct date Format.
P_B_47214			11/09/2014		Naman Jain		When we Export or Advance Search Event Tab with Entity Details then 2nd Event is not displaying
P_OPT_CONNECTIONS	12/05/2015		Sanshey					Opt								Connection was not properly closed
P_B_MBO_EXPORT_77802	23/09/2016	Sanshey Sachdeva		Bug								excludeFieldsList is added in order to exclude fields on the basis of specific condition
------------------------------------------------------------------------------------------------------------
*/
public class ExportDataCollector{

/**
	alias Count to be appended in the table name to form its alias.
*/
private int aliasCount				= 1;	//count used to append to form alias of table

/**
	alias Count to be appended in the field name to form its alias
*/
private int aliasFieldCount			= 1;	//count used to append to form alias of field

/**
	Logger
*/
static Logger logger					= com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(ExportDataCollector.class);

/**
	request object
*/
private HttpServletRequest request		= null;
private String fileType		= null;
public static String exportType=null;//P_FIN_B_28862

//Added by Anuj 26-Nov-2004
SequenceMap selForeignTables = null;
SequenceMap selFranTables = null;
int franCount = 0;

//ZCUB-20151124-202 Start
String groupValue=null;
String groupColumnType=null;
String groupColumnName=null;
String groupTableName=null;
String groupDbTableName=null;
//ZCUB-20151124-202 End

SimpleDateFormat sdf=new SimpleDateFormat("dd MMM yyyy");//BBEH_FOR_SMC_OPTIMIZATION
//Date dateToFormat=null;
/**
	Method returns the data for the given query packed in the sequence map.
*/
    //P_CRPT_E_28062011 starts
    private int tableCount = 1 ;

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }
    //P_CRPT_E_28062011 ends
String menuName = null;
String fimFlag = null;
String userLevel = null;	//P_B_CM_33700 : 28/02/2014 : Veerpal Singh

private List<String> skippedFieldNames;//P_B_Codebase_22112013
//P_E_Fields-20130905-035 starts
private Map<String,Set<String>> dependentFieldsMap;
private Map<String,Set<String>> dependentMuFranFieldsMap;
private Info piiTablePrivInfo;
public void setDependentWhereFields(Map<String,Set<String>> selectedHeaderTables) {
	this.dependentFieldsMap = selectedHeaderTables;
}
public Map<String,Set<String>> getDependentWhereFields() {
	return dependentFieldsMap;
}
public Map<String,Set<String>> getDependentMuFranFieldsMap() {
	return dependentMuFranFieldsMap;
}

public void setDependentMuFranFieldsMap(Map<String,Set<String>> dependentMuFranFieldsMap) {
	this.dependentMuFranFieldsMap = dependentMuFranFieldsMap;
}

//P_E_Fields-20130905-035 ends

	private List<String> excludeFieldsList;	//P_B_MBO_EXPORT_77802
/**
 * set privilege yes-no setting for PII-Fields that are configured in user-role
 * 
 */
public void setPiiTablePrivInfo(Info piiTablePrivInfo) {
	this.piiTablePrivInfo = piiTablePrivInfo;
}

public Info getPiiTablePrivInfo() {
	return piiTablePrivInfo;
}
public void init(HttpServletRequest request, String sMainTable,String fileType){
	this.request = request;
	this.fileType = fileType;
	this.exportType=fileType;//P_FIN_B_28862
	logger.info("fileType:::"+this.fileType);
	
	menuName = (String)request.getSession().getAttribute("menuName");
	fimFlag = (String)request.getSession().getAttribute("fimFlag");
	userLevel	=	(String) request.getSession().getAttribute("user_level");//P_B_CM_46611 
	//Added by Anuj 26-Nov-2004
	//To export selected foreign tables as per user selection.
	String fromRestAPI = (String)request.getAttribute("fromRestAPI");//805_REST_API_CHANGES
	String tabArr[] = (String[]) request.getParameterValues("selTables");
	String tabArrFran[] = (String[]) request.getParameterValues("selTablesFran");
	FieldMappings mappings		= DBUtil.getInstance().getFieldMappings(sMainTable);
	if("true".equals(fromRestAPI)){//805_REST_API_CHANGES----START
		ExportDataManipulator.userTimeZoneFromRestAPI = DataServiceUtil.defaulUserTimeZone;
	}
	ExportDataManipulator.userTimeZone = (String)request.getSession().getAttribute("userTimeZone");
	//805_REST_API_CHANGES----END
	if(mappings != null){
		selForeignTables = new SequenceMap();
		SequenceMap smForeignTables		= mappings.getForeignTablesExportMap();
		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);

				if(tabArr != null) {
					for(int k = 0 ; k < tabArr.length ; k++){
						if (foreignTable.getName().equals(tabArr[k].trim())){
							selForeignTables.put(foreignTable.getName(), foreignTable);
						}
					}
				}
			}
		}
	}
	if(tabArrFran!=null) {
		FieldMappings franMappings = DBUtil.getInstance().getFieldMappings("franchisees");
		if (franMappings != null)
		{
			selFranTables = new SequenceMap();
			SequenceMap smForeignTables = franMappings.getForeignTablesExportMap();
			if (smForeignTables != null)
			{
				if (tabArrFran != null)
				{
					// Added for only primary table to be exported
					int sizeForTable = smForeignTables.size();

					for (int i = 0; i < sizeForTable; i++)
					{
						ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
						
						for (int k = 0; k < tabArrFran.length; k++)
						{
							if (foreignTable.getName().trim().equals(tabArrFran[k].trim()))
							{
								selFranTables.put(foreignTable.getName().trim(), foreignTable);
							}
						}
					}
				}
			}
		}
		
		
	}
	
	dependentFieldsMap = new HashMap<String, Set<String>>();//P_E_Fields-20130905-035
	if("cmContactDetails".equals(sMainTable))	//P_B_MBO_EXPORT_77802
	{
		boolean isMboIntegrationEnabled = (Boolean)ModuleBroker.getinstance().invoke("com.home.builderforms.thirdpartyintegration.mbo.MBOUtil", "isMboIntegrationEnabled", null);
    	if(!isMboIntegrationEnabled){
    		excludeFieldsList = new ArrayList<String>();
    		excludeFieldsList.add("mboCurrentClientId");
    		excludeFieldsList.add("mboEmailSubscription");
    		excludeFieldsList.add("mboMembershipStartDate");
    		excludeFieldsList.add("mboFirstPaymentDate");
    		excludeFieldsList.add("mboFirstVisitDate");
    	}
	}
}

	public void init(HttpServletRequest request, String sMainTable){
		init(request, sMainTable, null, null);
	}
	/**
	 * Override this method will to set values for dashboard 
	 * @updated by abhishek gupta
	 * @date 2 dec 2009
	 * @param request
	 * @param sMainTable
	 * @param fileType
	 * @param dashBoard
	 */
	public void init(HttpServletRequest request, String sMainTable, String fileType, String dashBoard){
		this.request = request;
	
		//Added by Anuj 26-Nov-2004
		//To export selected foreign tables as per user selection.
		String tabArr[] = null;
		if(dashBoard != null) {
			tabArr = (String[])request.getAttribute("selTables");
		} else {
			tabArr = (String[]) request.getParameterValues("selTables");
		}
		String tabArrFran[] = (String[]) request.getParameterValues("selTablesFran");
		menuName = (String)request.getSession().getAttribute("menuName");
		userLevel	=	(String) request.getSession().getAttribute("user_level");//P_B_CM_33700 : 28/02/2014 : Veerpal Singh
		ExportDataManipulator.userTimeZone = (String)request.getSession().getAttribute("userTimeZone");
		FieldMappings mappings		= DBUtil.getInstance().getFieldMappings(sMainTable);
		if(mappings != null){
			selForeignTables = new SequenceMap();
			/**
			 * Allow only exportable table in map to process 
			 */
			SequenceMap smForeignTables		= mappings.getForeignTablesExportMap();
	
			if(smForeignTables != null){
				int sizeForTable				= smForeignTables.size();
				for(int i = 0 ; i < sizeForTable ; i++){
					ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
					//P_E_FIM_58658 By Nikhil Verma
					if(tabArr!=null)
					{
						for(int k = 0 ; k < tabArr.length ; k++){
							if (foreignTable.getName().equals(tabArr[k].trim())){
								selForeignTables.put(foreignTable.getName(), foreignTable);
							}
						}
					}
				}
			}
		}
		if(tabArrFran!=null) {
			FieldMappings franMappings = DBUtil.getInstance().getFieldMappings("franchisees");
			if (franMappings != null)
			{
				selFranTables = new SequenceMap();
				SequenceMap smForeignTables = franMappings.getForeignTablesExportMap();
				if (smForeignTables != null)
				{
					if (tabArrFran != null)
					{
						// Added for only primary table to be exported
						int sizeForTable = smForeignTables.size();

						for (int i = 0; i < sizeForTable; i++)
						{
							ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
							
							for (int k = 0; k < tabArrFran.length; k++)
							{
								if (foreignTable.getName().trim().equals(tabArrFran[k].trim()))
								{
									selFranTables.put(foreignTable.getName().trim(), foreignTable);
								}
							}
						}
					}
				}
			}
			
			
		}
		dependentFieldsMap = new HashMap<String, Set<String>>();//P_E_Fields-20130905-035
		
		if("cmContactDetails".equals(sMainTable))	//P_B_MBO_EXPORT_77802
		{
			boolean isMboIntegrationEnabled = (Boolean)ModuleBroker.getinstance().invoke("com.home.builderforms.thirdpartyintegration.mbo.MBOUtil", "isMboIntegrationEnabled", null);
	    	if(!isMboIntegrationEnabled){
	    		excludeFieldsList = new ArrayList<String>();
	    		excludeFieldsList.add("mboCurrentClientId");
	    		excludeFieldsList.add("mboEmailSubscription");
	    		excludeFieldsList.add("mboMembershipStartDate");
	    		excludeFieldsList.add("mboFirstPaymentDate");
	    		excludeFieldsList.add("mboFirstVisitDate");
	    	}
		}
	}

	public SequenceMap getQueryData(String moduleName, String query) {
		return getQueryData(moduleName, query, false);
	}
    public SequenceMap getQueryData(String moduleName, String query,boolean isfran) {
        SequenceMap smReturnData = new SequenceMap();
        /*
         * get the java.sql.resultset for the query modified by manishS
         */
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
      //  java.sql.PreparedStatement pstmt = null; // PH_Intranet_Messages_Optimized  //BBEH_FOR_SMC_OPTIMIZATION
        java.sql.ResultSet result = null;       
        java.sql.PreparedStatement pstmt = null;	//P_OPT_CONNECTIONS       
        smReturnData.put(moduleName, new SequenceMap());
      //BBEH_FOR_SMC_OPTIMIZATION starts
        Object[] jdbcObject =null; 
        try {
            // PH_Intranet_Messages_Optimized Start
        	//Query_Optimization_DBA STARTS 
        	NewPortalUtils.setOptimizerSearchDepthToZero();
    		jdbcObject = QueryUtil.getResultJava(connection, query, new Object[]{});
            result = (java.sql.ResultSet) jdbcObject[0];
            pstmt = (java.sql.PreparedStatement) jdbcObject[1];	//P_OPT_CONNECTIONS
            jdbcObject=null;
            // PH_Intranet_Messages_Optimized End
        	//Query_Optimization_DBA STARTS
            NewPortalUtils.setOptimizerSearchDepthToOriginal();
        	//Query_Optimization_DBA ends
	        java.sql.ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            Info info=null;
            int i=0;
            int tempaliasCount = 1;
            int tempaliasFieldCount = 1;
            if(isfran) {
            	tempaliasCount= aliasCount;
            	tempaliasFieldCount = aliasFieldCount;
            }
            
            while (result.next()) {
                // reset the aliascounts
                aliasCount = tempaliasCount;
                aliasFieldCount = tempaliasFieldCount;
                info=new Info();
                info.setConvertTodisplaydate(false); // setting false value for not convert in display format
                i=columnsNumber;
                while(i>0){
                	if(rsmd.getColumnTypeName(i).equals("VARBINARY") || rsmd.getColumnTypeName(i).equals("BLOB"))								//BB-20150525-360 Starts//HomeHelpers-20131216-742
                		{
                			info.set(rsmd.getColumnLabel(i),result.getString(i));
                		}else
                		{
                			info.set(rsmd.getColumnLabel(i),result.getObject(i));
                		}																				//BB-20150525-360 Ends
                	i--;
                }
                // call the collectData method to put data in sequenceMap
                // Modified by Anuj 26-Nov-2004
                if(isfran) {
                	collectFranData(moduleName, info, smReturnData, true,FieldNames.EMPTY_STRING);
                } else
                collectData(moduleName, info, smReturnData, true,FieldNames.EMPTY_STRING);//P_E_Fields-20130905-035
                //BBEH_FOR_SMC_OPTIMIZATION ends
            }
        } catch (Exception e) {
            logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" , e);
        } // modified By ManishS
        // PH_Intranet_Messages_Optimized Start
        finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;	//P_OPT_CONNECTIONS
                }
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" + ex);
            }
            //P_OPT_CONNECTIONS starts
            try {
            	if (pstmt != null) {
            		pstmt.close();
            		pstmt = null;
            	}
            } catch (Exception ex) {
            	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" + ex);
            }
            //P_OPT_CONNECTIONS ends
            try {
                connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->: getQueryData(moduleName,query)", ex);
            }
            //added by rohit jain //BBEH_FOR_SMC_OPTIMIZATION starts
            if(jdbcObject!=null){
            	jdbcObject=null;
            	
            }
          //BBEH_FOR_SMC_OPTIMIZATION ends
        }
        // PH_Intranet_Messages_Optimized End
        return smReturnData;
    }
    
    
    public SequenceMap getMuEntityQueryData(String moduleName, String query, boolean isMU, SequenceMap<String,Info> smData) {
        if(smData == null) {
        	smData = new SequenceMap<String, Info>();
        }
        SequenceMap smReturnData = new SequenceMap();
        SequenceMap<String, Info> smfinalData = new SequenceMap<String, Info>();
        /*
         * get the java.sql.resultset for the query modified by manishS
         */
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
      //  java.sql.PreparedStatement pstmt = null; // PH_Intranet_Messages_Optimized  //BBEH_FOR_SMC_OPTIMIZATION
        java.sql.ResultSet result = null;       
        java.sql.PreparedStatement pstmt = null;	//P_OPT_CONNECTIONS       
        smReturnData.put(moduleName, new SequenceMap());
      //BBEH_FOR_SMC_OPTIMIZATION starts
        Object[] jdbcObject =null; 
        try {
            // PH_Intranet_Messages_Optimized Start
        	//Query_Optimization_DBA STARTS 
        	NewPortalUtils.setOptimizerSearchDepthToZero();
    		jdbcObject = QueryUtil.getResultJava(connection, query, new Object[]{});
    		if(jdbcObject!=null) {
            result = (java.sql.ResultSet) jdbcObject[0];
            pstmt = (java.sql.PreparedStatement) jdbcObject[1];	//P_OPT_CONNECTIONS
            jdbcObject=null;
            // PH_Intranet_Messages_Optimized End
        	//Query_Optimization_DBA STARTS
            NewPortalUtils.setOptimizerSearchDepthToOriginal();
        	//Query_Optimization_DBA ends
	        java.sql.ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            Info info=null;
            Info finalInfo = null;
            int i=0;
            
            boolean isFranRecord = false;
            ParamResolver.getResolver().put("isFranRecord", false);
            
            int j=0;
            while (result.next()) {
            	j++;
                // reset the aliascounts
                
            	
            	if(isMU) {
            		info= InfoFactory.getNewInfo();
            		info.setConvertTodisplaydate(false); // setting false value for not convert in display format
            	} else {
            		finalInfo= InfoFactory.getNewInfo();
            		finalInfo.setConvertTodisplaydate(false);
            	}
                i=1;
                String pkey = null;
                while(i<=columnsNumber){
                	String columnLabel = rsmd.getColumnLabel(i);
                	if(i == 1) {
                		pkey = result.getString(i);
                		if(!isMU && smData!=null) {
                			info = (Info)smData.get(pkey+j);
                			String temp = columnLabel.replaceAll("[^0-9]+", "");
                			int alCnt = 0;
                			if(StringUtil.isvalidInteger(temp)) {
                				alCnt = Integer.parseInt(temp);
                			}
                			alCnt = alCnt+1;
                			columnLabel = columnLabel.replaceAll("[0-9]+", "")+alCnt;
                		}
                	}
                	if(i == 2) {
                		
                		if(!isMU && smData!=null) {
                			String temp = columnLabel.replaceAll("[^0-9]+", "");
                			int alCnt = 0;
                			if(StringUtil.isvalidInteger(temp)) {
                				alCnt = Integer.parseInt(temp);
                			}
                			alCnt = alCnt-1;
                			columnLabel = columnLabel.replaceAll("[0-9]+", "")+alCnt;
                			
                			
                		}
                	}
                	//String columnLabel = str = str.replaceAll("[^0-9]+", " ");
                	if(isMU){
                		info.set(columnLabel, result.getObject(i));
                	} else {
                		if(i == 1) {
                		finalInfo.setAll(info);
                		}
                		finalInfo.set(columnLabel, result.getObject(i));
                	}
                	
                	i++;
                }
                if(isMU) {
                	smData.put(pkey+j, info);
                } else {
                	smfinalData.put(Integer.toString(j), finalInfo);
                }
                // call the collectData method to put data in sequenceMap
                // Modified by Anuj 26-Nov-2004
               
                //BBEH_FOR_SMC_OPTIMIZATION ends
            }
        } 
            if(!isMU ) {
            	if(smfinalData.size()==0) {
            		smfinalData = smData;
            	}
            	for(Info rowInfo: smfinalData.values()) {
            		aliasCount = 1;
                    aliasFieldCount = 1;
            		collectData(moduleName, rowInfo, smReturnData, true,FieldNames.EMPTY_STRING);//P_E_Fields-20130905-035
            	}
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" , e);
        } // modified By ManishS
        // PH_Intranet_Messages_Optimized Start
        finally {
            try {
                if (result != null) {
                    result.close();
                    result = null; //P_OPT_CONNECTIONS
                }
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" + ex);
            }
            //P_OPT_CONNECTIONS starts
            try {
            	if (pstmt != null) {
            		pstmt.close();
            		pstmt = null;
            	}
            } catch (Exception ex) {
            	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getQueryData(moduleName,query)" + ex);
            }
            //P_OPT_CONNECTIONS ends
            try {
                connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->: getQueryData(moduleName,query)", ex);
            }
            //added by rohit jain //BBEH_FOR_SMC_OPTIMIZATION starts
            if(jdbcObject!=null){
            	jdbcObject=null;
            	
            }
          //BBEH_FOR_SMC_OPTIMIZATION ends
        }
        // PH_Intranet_Messages_Optimized End
        if(isMU) {
        	return smData;
        }
        return smReturnData;
    }
    
    
    
    /**
     * @author abhishek gupta
     * @param moduleName
     * @param query
     * @return
     */
    public SequenceMap getSearchQueryData(String moduleName, String query)
    {
    	return getSearchQueryData(moduleName, query, null);
    }
    /**
     * P_CRPT_E_28062011 
     * @author vikram
     * @param moduleName
     * @param query
     * @param minLimit
     * @param maxLimit
     * @return
     */
     public SequenceMap getSearchQueryData(String moduleName, String query, int minLimit, int maxLimit) {
        SequenceMap smReturnData = new SequenceMap();
        int count = 0;
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        java.sql.ResultSet result = null;
        java.sql.PreparedStatement pstmt = null;	//P_OPT_CONNECTIONS
        int tableCount = getTableCount();
        smReturnData.put(moduleName, new SequenceMap());
        HttpSession session = StrutsUtil.getHttpSession();
        try {
            // PH_Intranet_Messages_Optimized Start
        	//Query_Optimization_DBA STARTS
        	NewPortalUtils.setOptimizerSearchDepthToZero();
        	//Query_Optimization_DBA ENDS
        	Object[] jdbcObject = QueryUtil.getResultJava(connection, query, new Object[]{});
            result = (java.sql.ResultSet) jdbcObject[0];
            pstmt = (java.sql.PreparedStatement) jdbcObject[1];	//P_OPT_CONNECTIONS
            //Query_Optimization_DBA STARTS
            NewPortalUtils.setOptimizerSearchDepthToOriginal();
        	//Query_Optimization_DBA ENDS
            // PH_Intranet_Messages_Optimized End
            ArrayList<String> previousPrimaryKey = new ArrayList<String>();//CUSTOM_REPORT_SORTING_ISSUE
            ArrayList<String> primaryKey = new ArrayList<String>();
            Set<String> currentPrimaryKey = new HashSet<String>();//RightatHome-20160617-982
            while (result.next()) {

                aliasCount = 1;
                aliasFieldCount = 1;
//P_B_FS_56326 starts
                    int count1 = 1;
                if (tableCount > 2) {
                    if("fim".equals(menuName)&& "0".equals((String) session.getAttribute("fimFlag"))) {
                        count1 = 3;
                    }
                    else {
                        count1 = 1;
                    }
                    /*if(StringUtil.isValidNew((String)session.getAttribute("relationShip1")))//Problem in Custom Report while do export starts
                    {
                    	if("1".equals((String)session.getAttribute("relationShip1")))
                    	{
                    		if (!primaryKey.contains(result.getString(count1))) {
                    		//primaryKey.add(result.getString(1));
                    		primaryKey.add(result.getString(count1));//P_B_FIM_58243
                    		++count;
                    		//RightatHome-20160617-982 starts
                    		if(count > minLimit && count <= maxLimit){
                    			currentPrimaryKey.add(result.getString(count1));
                    		}
                    		//RightatHome-20160617-982 ends
                    		}else if(primaryKey.contains(result.getString(count1))){
                    			request.setAttribute("isRelationRadioVisible","true");
                    		}
                    	}else  //P_B_FS_56326 ends
                    	{
                    		++count;
                    	}
                    }else
                    {*///Problem in Custom Report while do export ends
                    	if (!primaryKey.contains(result.getString(count1))) {
                    		//primaryKey.add(result.getString(1));
                    		primaryKey.add(result.getString(count1));//P_B_FIM_58243
                    		++count;
                    		//RightatHome-20160617-982 starts
                    		if(count > minLimit && count <= maxLimit){
                    			currentPrimaryKey.add(result.getString(count1));
                    		}
                    		//RightatHome-20160617-982 ends
                    		}else if(primaryKey.contains(result.getString(count1))){
                    			request.setAttribute("isRelationRadioVisible","true");
                    		}
                    //}//Problem in Custom Report while do export
                } else {
                    ++count;

                }
                //CUSTOM_REPORT_SORTING_ISSUE starts
                if(count==minLimit){
                	previousPrimaryKey=new ArrayList<String>(primaryKey);
                }
                if (!previousPrimaryKey.contains(result.getString(count1)) && count > minLimit && (count <= maxLimit || currentPrimaryKey.contains(result.getString(count1)))) {//CUSTOM_REPORT_SORTING_ISSUE ends//RightatHome-20160617-982
                    collectSearchData(moduleName, result, smReturnData, true);
                }
            }
            smReturnData.put("count", count);

        } catch (Exception e) {
            logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getSearchQueryData()" + e);
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null; //P_OPT_CONNECTIONS
                }
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getSearchQueryData()" + ex);
            }
            //P_OPT_CONNECTIONS starts
            try {
            	if (pstmt != null) {
            		pstmt.close();
            		pstmt = null;
            	}
            } catch (Exception ex) {
            	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getSearchQueryData()" + ex);
            }
            //P_OPT_CONNECTIONS ends
            // PH_Intranet_Messages_Optimized Start
           
            try {
                connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getSearchQueryData()" + ex);
            }
        }

        return smReturnData;
    }
     
     
    public SequenceMap getSearchQueryData(String moduleName, String query, String dashBoard) {
        SequenceMap smReturnData = new SequenceMap();
        /*
         * get the java.sql.resultset for the query modified by manishS
         */
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        java.sql.PreparedStatement pstmt = null; // PH_Intranet_Messages_Optimized 
        java.sql.ResultSet result = null;
        smReturnData.put(moduleName, new SequenceMap());

        try {
            // PH_Intranet_Messages_Optimized Start
            Object[] jdbcObjects = QueryUtil.getResultJava(connection, query, null);
            result = (java.sql.ResultSet)jdbcObjects[0];
            pstmt = (java.sql.PreparedStatement)jdbcObjects[1];
            // PH_Intranet_Messages_Optimized End
            while (result.next()) {
                // reset the aliascounts
                aliasCount = 1;
                aliasFieldCount = 1;

                // call the collectData method to put data in sequenceMap
                // Modified by Anuj 26-Nov-2004
                collectSearchData(moduleName, result, smReturnData, true, dashBoard);
                
                if(ModuleUtil.zcubatorImplemented() && "SG".equals(request.getParameter("reportType"))){
                	addLinkOnCount(smReturnData,moduleName);//ZCUB-20151124-202 Start
                }
            }
        } catch (Exception e) {
            logger.error("Error 1 in getSearchQueryData", e);
        } // modified By ManishS
        finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception ex) {
                logger.error("Error 1 in getSearchQueryData", ex);
            }
            // PH_Intranet_Messages_Optimized Start
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception ex) {
                logger.error("Error 2 in getSearchQueryData", ex);
            }
            // PH_Intranet_Messages_Optimized End
            try {
                connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:getSearchQueryData()" + ex);
            }
        }

        return smReturnData;
    }

    public int getSearchQueryCount(String query) {
        DBConnectionManager connectionManager = DBConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection(MultiTenancyUtil.getTenantName());
        java.sql.PreparedStatement pstmt = null; // PH_Intranet_Messages_Optimized 
        java.sql.ResultSet result = null;
        int count = 0;

        try {
            // PH_Intranet_Messages_Optimized Start
            Object[] jdbcObjects = QueryUtil.getResultJava(connection, query, null);
            result = (java.sql.ResultSet) jdbcObjects[0];
            pstmt = (java.sql.PreparedStatement) jdbcObjects[1];
            // PH_Intranet_Messages_Optimized End
            while (result.next()) {
                ++count;
            }
        } catch (Exception e) {
            logger.error("Error 0 in getSearchQueryCount", e);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception ex) {
                logger.error("Error 1 in getSearchQueryCount", ex);
            }
            // PH_Intranet_Messages_Optimized Start
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception ex) {
                logger.error("Error 2 in getSearchQueryCount", ex);
            }
            // PH_Intranet_Messages_Optimized End
            try {
                connectionManager.freeConnection(MultiTenancyUtil.getTenantName(), connection);
            } catch (Exception ex) {
                logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->: getSearchQueryCount(query)", ex);
            }
        }

        return count;
    }
    private void collectFranData(String moduleName,Info result , SequenceMap smDataMap, boolean bMain,String headerName) {
    	FieldMappings mappings = null;
    	SequenceMap smForeignTables = null;
    	SequenceMap smTableData = null;
    	SequenceMap smData =  null;
    	String sPrimaryKeyValue	= null;
    	boolean processHeaders = StringUtil.isValid(headerName);
        mappings = DBUtil.getInstance().getFieldMappings(moduleName);
        if (bMain) {
        	try {
        		if(processHeaders){
     		        smTableData	= (SequenceMap) smDataMap.get(headerName+moduleName);//P_E_Fields-20130905-035
     		    }else{
     		    	smTableData	= (SequenceMap) smDataMap.get(moduleName);//BBEH_FOR_SMC_OPTIMIZATION
     		    }
        		String primaryKey = null;
				
				boolean isFranRecord = false;
				try {
					if("franchiseeRecord".equals(result.getString("RECORD_TYPE"+(++aliasFieldCount)))) {
						isFranRecord = true;
						aliasFieldCount++;
					}
				} catch(Exception e) {
					e.printStackTrace();
					isFranRecord = false;
				}
				
				if(isFranRecord) {
					primaryKey = result.getString("FRANCHISEE_NO"+aliasFieldCount++);
	        		if ( !smTableData.containsKey(primaryKey))
	                {
			        		smData = new SequenceMap();
	                } else {
	                	smData = (SequenceMap) smTableData.get(primaryKey);
	                }
					HttpSession session = StrutsUtil.getHttpSession();
					SequenceMap tempsmTableData = new SequenceMap();
					SequenceMap tempsmdataMap = new SequenceMap();
					if(smData!=null && smData.containsKey("muFranchisee")) {
						tempsmTableData = (SequenceMap)smData.get("muFranchisee");
					} else {
						tempsmTableData = new SequenceMap();
					}
					//P_B_65267 STARTS
					String franDisplayName = NewPortalUtils.getFranchiseIdDisplayName(primaryKey,result.getString("FRANCHISEE_NAME"+aliasFieldCount++));
					tempsmdataMap.put("franchiseeName",franDisplayName);
					//P_B_65267 ENDS
					if ("fim".equals(menuName) && "2".equals((String) session.getAttribute("fimFlag"))){
						
					try {
						tempsmdataMap.put("ownerTitle",transform(mappings.getField("ownerTitle"), result.getString("OWNER_TITLE"+aliasFieldCount++)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tempsmdataMap.put("ownerFirstName",result.getString("OWNER_FIRST_NAME"+aliasFieldCount++));
					tempsmdataMap.put("ownerLastName",result.getString("OWNER_LAST_NAME"+aliasFieldCount++));
					} else if ("fim".equals(menuName) &&  "3".equals((String) session.getAttribute("fimFlag"))){
						tempsmdataMap.put("entityName",result.getString("FIM_TT_ENTITY_NAME"+aliasFieldCount++));
					}
					tempsmTableData.put(primaryKey, tempsmdataMap);
					smData.put("muFranchisee", tempsmTableData);
					
					
					
				}
				
				
				    smForeignTables	= selFranTables;
         
				
				
				if (smForeignTables != null)
				{
					boolean isFirstIteration = true;
				    int sizeForTable = smForeignTables.size();
				    //Problem in Custom Report while do export starts
				    String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
					Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
					//Problem in Custom Report while do export ends
				   for (int i = 0 ; i < sizeForTable ; i++)
				    {
				    	if(i>0){
				    		isFirstIteration = false;
				    	}
				        ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
				        //Problem in Custom Report while do export starts
				        if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
							continue;
						}
				        //Problem in Custom Report while do export ends
				        if (!smData.containsKey(foreignTable.getName()))
				        {
				           	smData.put(foreignTable.getName(), new SequenceMap());
				           	
				        }

				        // Modified by Anuj 26-Nov-2004
				        collectMuFranData(foreignTable.getName(), result, smData, false,FieldNames.EMPTY_STRING,isFirstIteration);
				       	//P_E_Fields-20130905-035 starts
				       	if(!processHeaders && dependentMuFranFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
				        	collectHeaderSearchData(foreignTable.getName(), null, smData, false, "",result,"collectData",true);
				        }
						//P_E_Fields-20130905-035 ends
				    }
				}
				smTableData.put(primaryKey , smData);
				
				 if(processHeaders){
				 	smDataMap.put(headerName+moduleName , smTableData);//P_E_Fields-20130905-035
				 }else{
				 	smDataMap.put(moduleName , smTableData);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
    
    private void collectMuFranData(String moduleName,Info result , SequenceMap smDataMap, boolean bMain,String headerName,boolean firstIteration)
    {
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	SequenceMap smTableData=null;
    	SequenceMap smData = null;
    	SequenceMap sArrTableFields = null;
    	SequenceMap sArrTableFieldsTemp = null;
    	SequenceMap docMap = null;
    	SequenceMap smForeignTables = null;
    	HeaderMap[] hMap =null;
    	boolean includeFran = false;
    	boolean processHeaders = StringUtil.isValid(headerName);//P_E_Fields-20130905-035
    	 try
        {
    		if(processHeaders){
 		        smTableData	= (SequenceMap) smDataMap.get(headerName+moduleName);//P_E_Fields-20130905-035
 		    }else{
 		    	smTableData	= (SequenceMap) smDataMap.get(moduleName);//BBEH_FOR_SMC_OPTIMIZATION
 		    }
	        ArrayList sArrSelectedFieldList	= null;
	        FieldMappings mappings = null;
	        mappings = DBUtil.getInstance().getFieldMappings(moduleName);
	        //P_E_FS_INTER start
	        boolean isFsMenuWithI18n=false;
			if (LanguageUtil.isI18nImplemented())
			{
				isFsMenuWithI18n=true;
			}
			//P_E_FS_INTER ends
            if (mappings != null)
            {
                String sPrimaryKeyValue	= null;
                String[] sArrIDFields = mappings.getIdFieldNames();
                String sDBTableName	= mappings.getTableName();
                String sTableAliasName = sDBTableName + aliasCount++;
                int count = aliasCount - 1;
                String sFieldDB	= null;
                Field field	= null;
                String sPiiField = null;
		        try
                {
			        if (sArrIDFields != null)
                    {
			        	
				        // use the first primary key
				        field = mappings.getField(sArrIDFields[0]);
				        
                        // here i get the db name of the primary key for the present table
				        sPiiField = field.getDbField();
				        sFieldDB = field.getDbField() + aliasFieldCount++;
                        // increase the count for aliasField for all other key field.
				        for (int i = 1 ; i < sArrIDFields.length ; i++)
                        {
					        aliasFieldCount++;
				        }
			        }
                    // here i get the value of primaryKey from the record set
			        sPrimaryKeyValue = result.getString(sFieldDB);
		        } catch(Exception e)
                {
		        	logger.error("\nException in ExportDataCollector class in collectData method :",e);
		        }
		        // process record
		        if ( !smTableData.containsKey(sPrimaryKeyValue))
                {
                    /*
                    the record with the primary key is not inculded in the tableData map hence this is new
                    record and we have to put this information in the map against the key(primary key).
                    */

			        // create new map and put the main table informtion
		        	
		        		smData = new SequenceMap();
		        	

                    // collect other data for the table
			        String sGetSelected	= request.getParameter("getSelectedField");
        			String [] sArrSelectedField	= null;
        			
        			
        			                    // get the selected field names if sGetSelected is not null
			        if (sGetSelected != null)
                    {
				        sArrSelectedField = request.getParameterValues(moduleName + count);
				        sArrSelectedFieldList	= new ArrayList();
				        for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
	        				sArrSelectedFieldList.add(sArrSelectedField[i]);
						}
			        }
			        if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(moduleName) && sGetSelected==null) {
                		includeFran = true;
                		
					}
			        sArrTableFields	= mappings.getAllFields(); ////BBEH_FOR_SMC_OPTIMIZATION 
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

			        if (sArrTableFields != null)
                    {
			        	String isFranchisee = FieldNames.EMPTY_STRING;
				        for (int k = 0; k < size ; k++)
                        {
					        field = (Field) sArrTableFields.get(k);
					        if(processHeaders){
					        	continue;
					        }
					        if (sGetSelected != null )
                            {
					        	if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
					        		continue;
					        	}
					        	if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(moduleName) && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
		                    		includeFran = true;
		                    		continue;
		    					}
						        field = mappings.getField(sArrSelectedField[k]);
					        }
					        if(!field.isActiveField()  || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
					        	continue;
					        }
					        //if("fim".equals(menuName) && "reportPeriodStartDate".equals(field.getFieldName()) && !ModuleUtil.financialsImplemented())
								//continue;
					        //START : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
							/*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(moduleName) || "cmContactStatusChangeInfoExport".equals(moduleName)))
							{
								if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
									continue;
								}
							}*/
							//END : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
                            
					        boolean bExportTable = field.isFieldExportable();
                            if (bExportTable)
                            {
						        // processing for the field value
						        String sFieldAliasName	= field.getDbField() + aliasFieldCount;
						        // get the data if coming from other table
                                if (field.getSrcTable() != null)
                                {
                                    FieldMappings srcMap = DBUtil.getInstance().getFieldMappings(field.getSrcTable());
                                    sFieldAliasName	= srcMap.getDbField(field.getSrcValue()) + aliasFieldCount;
                                }
						        String sFieldValue = result.getString(sFieldAliasName);
						        
						        if("isFranchisee".equals(field.getFieldName())) {
						        	isFranchisee = sFieldValue;
						        }
						        if("franchisees".equals(moduleName) && "isStore".equals(field.getFieldName()) && "N".equals(isFranchisee)) {
						        	if("Y".equals(sFieldValue))
						        		smData.put("status", _baseConstants.IN_DEVELOPMENT_LBL);
						        	
						        	continue;
						        }
						        if(sFieldAliasName.equals("CREDIT_CARD"+aliasFieldCount))
						        {
						        	if(sFieldValue!=null && sFieldValue.trim().equals("0"))
						        	{
						        		sFieldValue=new String("Credit Card");
						        	}else if(sFieldValue!=null && sFieldValue.trim().equals("1"))
						        	{
						        		sFieldValue=new String("Credit Line");
						        	}
						        }
						        String sbTransMethodActParam= "";
						        if(StringUtil.isValid(field.getTransformMethodExportParam())) {
						        	sbTransMethodActParam = field.getTransformMethodExportParam();
						        }  else {
						        	sbTransMethodActParam = field.getTransformMethodParam();
						        }
						        if(StringUtil.isValid(field.getTransformExportMethodParam())) {
						        	sbTransMethodActParam = field.getTransformExportMethodParam();
						        }
						        /** Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country*/
                                if (sbTransMethodActParam!=null)
                                {
                                	 sArrTableFieldsTemp = null;//BBEH_FOR_SMC_OPTIMIZATION 
							        int sizeTemp = sArrTableFields.size();
							        Field fieldTemp = null;
	    						    String sTransformMethodParamAlias = null;

                                    for (int l=0;l<sizeTemp ;l++ )
                                    {
                                        fieldTemp = (Field)sArrTableFields.get(l);

                                            if (fieldTemp.getDbField().equals(sbTransMethodActParam)) 
                                            {
                                                sTransformMethodParamAlias = sbTransMethodActParam +"_"+field.getDbField();
                                                sArrTableFieldsTemp = null;
                                                fieldTemp = null;
                                                break;
                                            }
                                    }
                                    field.setTableName(moduleName);
							        sFieldValue	= transform(field, sFieldValue, result.getString(sTransformMethodParamAlias), sbTransMethodActParam);
						        } else if(fileType == null || fileType.equals("") || fileType.equals(" "))
                                {
							        // transform field value
						        	field.setTableName(moduleName);
						        	if( !(sFieldValue).equals(_baseConstants.IN_DEVELOPMENT_LBL)) {
						        		sFieldValue = transform(field, sFieldValue);
						        	}
						        } else
                                {
							        // transform field value
						        	field.setTableName(moduleName);	//CUSTOM_FIELD_DATAEXPORT
							        sFieldValue = transformFileType(field, sFieldValue);
						        }
                                //P_B_Codebase_22112013
                                if("Double".equals(field.getValidationType()) && StringUtil.isValid(sFieldValue)) {
						        	sFieldValue = NumberFormatUtils.formatCommaNumber(sFieldValue);
                            	}else if("Integer".equals(field.getValidationType()) && ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))) ) {
									
                            		/**
									 * IF_INT_MINUS_ONE
									 * DO not set value if its -1
									 */
									if(!StringUtil.isValidNew(sFieldValue)) {
										sFieldValue = "";
									}
                            	}
                                /**
                                 * Will be called if main-table included for PII-conversion
                                 */
                                String sFieldName = field.getFieldName() ;
						        if (moduleName!=null && !moduleName.equals("") && (moduleName.startsWith("fim") || moduleName.startsWith("area") || moduleName.startsWith("franchisees") || moduleName.equals("franchiseeCall") || moduleName.startsWith("fs") || moduleName.startsWith("cm") || ("fim".equals(menuName) && ("tasks".equals(moduleName) || "outlookMails".equals(moduleName)))))//P_CM_B_47837  //P_FIM_B_50288
						        {
						        	if (field != null && sFieldValue != null && field.getDataType() != null && field.getDataType().equals("Date") && sFieldValue != null ) 
                                    {
						        		// P_E_DATE_FORMAT
						        		// P_FIM_B_37183
						        		if (sFieldValue.length()>10)
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue.substring(0, 10));
						        		} else
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue);
						        		}

                                    }
						        }
						        //P_CM_B_47837 :starts
						        if("Date".equalsIgnoreCase(field.getDataType()))    
						        {
                                	sFieldValue  = DateUtil.getDisplayDate(sFieldValue);
						        }
						        //P_CM_B_47837 :ends
						        if(field.isPiiEnabled())
						        {
						        	String moduleNameForPii = (String)request.getSession().getAttribute(FieldNames.MENU_NAME);
						        	if("fs".equalsIgnoreCase(moduleNameForPii) || "fim".equalsIgnoreCase(moduleNameForPii))
						        	{
						        		String mainTableName = FieldNames.EMPTY_STRING;
						        		String columnName = FieldNames.EMPTY_STRING;
						        		if("fs".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			mainTableName = "fsLeadDetails";
						        			columnName = "LEAD_ID";
						        		}else if("fim".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			mainTableName = "franchisee";
						        			columnName = "ENTITY_ID";
						        		}
						        		String tableAnchorForPii = FieldNames.EMPTY_STRING;
						        		String piiEntityId = FieldNames.EMPTY_STRING;
						        		SequenceMap customTabMap 	= BuilderCustomTab.newInstance().getCustomTab(moduleNameForPii, null);
						        		Info customTabInfo			= null;
						        		boolean isEntryNotDone = true;
						        		if(customTabMap != null)
						        		{
						        			Iterator cusTabIterator=customTabMap.keys().iterator();
						        			while(cusTabIterator.hasNext())
						        			{
						        				customTabInfo			= (Info)customTabMap.get(cusTabIterator.next());
						        				if(customTabInfo != null)
						        				{
						        					if(moduleName.equals(customTabInfo.get(BuilderFormFieldNames.TABLE_ANCHOR)));
						        					{
						        						if(StringUtil.isValid(columnName))					//P_Enh_FC-76
						        						{
						        							piiEntityId = SQLUtil.getColumnValue(customTabInfo.get(BuilderFormFieldNames.DB_TABLE), columnName, "ID_FIELD", sPrimaryKeyValue);
						        						}
						        						if(StringUtil.isValidNew(piiEntityId))
						        						{
						        							isEntryNotDone = false;
						        							if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        							}
						        							else if(StringUtil.isValidNew(request.getParameter("forExportData")) && "true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        							}
						        						}
						        					}
						        				}
						        			}
						        		}
						        		if(isEntryNotDone) {
						        			if(!mainTableName.equals(moduleName))
						        			{
						        				String primaryEntityId = SQLUtil.getColumnValue(sDBTableName, columnName, sPiiField, sPrimaryKeyValue);
						        				if(StringUtil.isValidNew(primaryEntityId))
						        				{
						        					if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, primaryEntityId);
						        					}
						        					else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, primaryEntityId);
						        					}
						        				}
						        			}else
						        			{
						        				if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        				}
						        				else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        				}
						        			}
						        		}
						        	}else
						        	{
						        		if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        		}
						        		else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        		}
						        	}
						        }/*
                                if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
                                	sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
                                }*/
						        // append aliasFieldCount by one
						        aliasFieldCount++;
                                /**
                                 * Modified by Sunilk on 27 Feb 2006 
                                 * For Formating the date fields for FIM in export/advance search
                                */
                                if (field.isCustomField() && fileType != null && fileType.equals("xml"))
                                {
                                    String fieldName = field.getDisplayName();

                                    if (sFieldName != null && sFieldName.indexOf("C") != -1)
                                    {
                                        String fieldNo = sFieldName.substring(sFieldName.lastIndexOf("C")+1, sFieldName.length());
                                        fieldName = getActualName(moduleName, fieldNo);

                                        if (fieldName != null && !fieldName.equals(""))
                                        {
                                            smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                        	
                                        }
                                    } else
                                    {
                                        smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                    }

                                } else
                                {	//P_B_EXPORT_61519 data not appearing whiile i18n is implemented
                                	
                                	if((!ModuleUtil.zcubatorImplemented() || !"Y".equals(_baseConstants.CM_PILOT_ENABLED)) && "marketingCodeId".equals(sFieldName) && ("cm".equals(menuName))){//P_CM_B_26948
                        				//Activity code field to be skipped when Pilot is not enabled.
                                		continue;
                                	}else if ("cm".equals(menuName) && "taskType".equals(field.getFieldName())){
                        				continue;
                        			}else{
	                                	if(StringUtil.isValid(field.getDisplayExportName())) {
							            	smData.put(formatFieldNames(field.getDisplayExportName(),field), sFieldValue);//P_B_Codebase_22112013
							            } else {
							            	smData.put(formatFieldNames(field.getDisplayName(),field), sFieldValue);//P_B_Codebase_22112013
							            }
                                	}
                                }
					        }
				        }
				        
				        
			        }
		        } else
                {
			        smData = (SequenceMap) smTableData.get(sPrimaryKeyValue);
			        // increament count processing
                    String [] sArrSelectedField	= null;
                    String sGetSelected	= request.getParameter("getSelectedField");

                    // get the selected field names if sGetSelected is not null
                    if (sGetSelected != null)
                    {
				        sArrSelectedField = request.getParameterValues(moduleName + count);
			        } else {
                    	if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(moduleName)) {
                    		includeFran = true;
                        }
                    }

                    sArrTableFields	= mappings.getAllFields();//BBEH_FOR_SMC_OPTIMIZATION 
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

                    if (sArrTableFields != null)
                    {
                        for (int k = 0; k < size ; k++)
                        {
                            field = (Field) sArrTableFields.get(k);

                            if (sGetSelected != null)
                            {
                                field = mappings.getField(sArrSelectedField[k]);
                                if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(moduleName) && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
                                	includeFran = true;
                                	continue;
                                }
                            }
                            if(!field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
                            	continue;
                            }
                            //ZC_CM_B_46268 :starts
                            /*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(moduleName) || "cmContactStatusChangeInfoExport".equals(moduleName)))
							{
								if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
									continue;
								}
							}*///ZC_CM_B_46268 :ends
                            //P_B_47214 starts
                          
                            //P_B_47214 ends
                            boolean bExportTable = field.isFieldExportable();

                            if (bExportTable)
                            {
                                aliasFieldCount++;
                            }
                        }
                    }
		        }
		        
		        
		        hMap = mappings.getHeaderMap();//BBEH_FOR_SMC_OPTIMIZATION 
		   		for(HeaderMap h:hMap) {
		   			
		   			DocumentMap[] docMaps = null;
		   			HeaderField hFld = h.getHeaderFields();
		   			Boolean isTabularSection=hFld.isTabularSection();
		   			if(isTabularSection){
		   				continue;
		   			}
					Documents[] docs =  hFld.getDocuments();
					
					
					if(docs != null && docs.length > 0) {
							docMaps = docs[0].getDocumentMaps();
							if(docMaps != null && docMaps.length > 0) {
								for(DocumentMap dMap : docMaps) {
									docMap  = dMap.getDocumentFieldMap(); //BBEH_FOR_SMC_OPTIMIZATION 
									String docOption = (String)docMap.get("doc-option");
									String docSub = (String)docMap.get("title-display");
									String docLab = (String)docMap.get("doc-display");
									if(docOption == null) {
										docOption = "0";
									}
									if(sArrSelectedFieldList !=null) {
										if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
											String data = CommonMgr.newInstance().getCommonCmDAO().getNewDocumentsDataForExport(sPrimaryKeyValue, (String)docMap.get("name"),docOption,docSub,docLab);
											smData.put(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"), data);//P_CM_B_57781
										}
									} else {
										String data = CommonMgr.newInstance().getCommonCmDAO().getNewDocumentsDataForExport(sPrimaryKeyValue, (String)docMap.get("name"),docOption,docSub,docLab,menuName);
										smData.put(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"), data);//P_CM_B_57781
									}
								}
							}
					}
		   		}
		        
		   		/*if(bMain && !processHeaders && dependentMuFranFieldsMap.containsKey(moduleName) && PortalUtils.hasDependentAddressTable(moduleName,request)){
                	collectHeaderSearchData(moduleName, null, smData, false, "",result,"collectData");
                }*/
		        // processing of the foreign table data
		        // Modified by Anuj 26-Nov-2004
		   		if (bMain)
                    smForeignTables	= selForeignTables;
                else
                	smForeignTables	= mappings.getForeignTablesExportMap();//smForeignTables	= mappings.getForeignTablesMap();
                
                
                if(includeFran) {
                	SequenceMap tempsmTableData = new SequenceMap();
                	SequenceMap tempsmdataMap = new SequenceMap();
                	String primaryKey = null;
                	if(smData!=null && smData.containsKey("franchisee")) {
                		tempsmTableData = (SequenceMap)smData.get("franchisee");
                	} else {
                		tempsmTableData = new SequenceMap();
                	}
                	primaryKey = result.getString("FRANCHISEE_NO"+aliasFieldCount++);
                	tempsmdataMap.put("franchiseeName",result.getString("FRANCHISEE_NAME"+aliasFieldCount++));
                	tempsmTableData.put(primaryKey, tempsmdataMap);
                	smData.put("franchisee", tempsmTableData);
                }
                if (smForeignTables != null)
                {
                	boolean isFirstIteration = true;
                    int sizeForTable = smForeignTables.size();
                  //Problem in Custom Report while do export starts
                    String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
    				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
    				//Problem in Custom Report while do export ends
                  
                    for (int i = 0 ; i < sizeForTable ; i++)
                    {
                    	if(i>0){
                    		isFirstIteration = false;
                    	}
                        ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
                      //Problem in Custom Report while do export starts
                        if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
    						continue;
    					}
                      //Problem in Custom Report while do export ends

                        if (!smData.containsKey(foreignTable.getName()))
                        {
                           	smData.put(foreignTable.getName(), new SequenceMap());
                           	
                        }

                        // Modified by Anuj 26-Nov-2004
                        collectMuFranData(foreignTable.getName(), result, smData, false,FieldNames.EMPTY_STRING,isFirstIteration);
                       	//P_E_Fields-20130905-035 starts
                       	if(!processHeaders && dependentMuFranFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
                        	collectHeaderSearchData(foreignTable.getName(), null, smData, false, "",result,"collectData",true);
                        }
        				//P_E_Fields-20130905-035 ends
                    }
                }
                
                
                smTableData.put(""+sPrimaryKeyValue , smData);
               
                 if(processHeaders){
                 	smDataMap.put(headerName+moduleName , smTableData);//P_E_Fields-20130905-035
                 }else{
                 	smDataMap.put(moduleName , smTableData);
                 }
                
	        }
           
	    } catch(Exception e)
        {
	    	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:collectData() " , e);
	    }
	  //BBEH_FOR_SMC_OPTIMIZATION  starts
	    finally{
	    	
	    	if(smTableData!=null)
	    	{
	    		smTableData=null;
	    	}
	    	
	    	if(smData!=null)
	    	{
	    		smData=null;
	    	}
	    	
	    	if(sArrTableFields!=null)
	    	{
	    		sArrTableFields=null;
	    	}
	    	
	    	if(sArrTableFieldsTemp!=null)
	    	{
	    		sArrTableFieldsTemp=null;
	    	}
	    	
	    	if(docMap!=null)
	    	{
	    		docMap=null;
	    	}
	    	
	    	if(smForeignTables!=null)
	    	{
	    		smForeignTables=null;
	    	}
	            
	    }
	  //BBEH_FOR_SMC_OPTIMIZATION ends
    }
/**
	This method will collect the data of the module and its submodule from the resultset into sequenceMap
*/
   // private void collectData(String moduleName, java.sql.ResultSet result , SequenceMap smDataMap, boolean bMain) //BBEH_FOR_SMC_OPTIMIZATION starts
    //P_E_Fields-20130905-035 changed arguments
    private void collectData(String moduleName,Info result , SequenceMap smDataMap, boolean bMain,String headerName){
    	collectData(moduleName, result, smDataMap, bMain, headerName, false);
    }
    private void collectData(String moduleName,Info result , SequenceMap smDataMap, boolean bMain,String headerName,boolean firstIteration)
    {
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	SequenceMap smTableData=null;
    	SequenceMap smData = null;
    	SequenceMap sArrTableFields = null;
    	SequenceMap sArrTableFieldsTemp = null;
    	SequenceMap docMap = null;
    	SequenceMap smForeignTables = null;
    	HeaderMap[] hMap =null;
    	boolean includeFran = false;
    	UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	    String user_level=(String)request.getSession().getAttribute("user_level");
    	boolean processHeaders = StringUtil.isValid(headerName);//P_E_Fields-20130905-035
    	 try
        {
    		if(processHeaders){
 		        smTableData	= (SequenceMap) smDataMap.get(headerName+moduleName);//P_E_Fields-20130905-035
 		    }else{
 		    	smTableData	= (SequenceMap) smDataMap.get(moduleName);//BBEH_FOR_SMC_OPTIMIZATION
 		    }
	        ArrayList sArrSelectedFieldList	= null;
	        FieldMappings mappings = null;
	        mappings = DBUtil.getInstance().getFieldMappings(moduleName);
	        //P_E_FS_INTER start
	        boolean isFsMenuWithI18n=false;
			if (LanguageUtil.isI18nImplemented())
			{
				isFsMenuWithI18n=true;
			}
			//P_E_FS_INTER ends
            if (mappings != null)
            {
                String sPrimaryKeyValue	= null;
                String[] sArrIDFields = mappings.getIdFieldNames();
                String sDBTableName	= mappings.getTableName();
                
                String sTableAliasName = sDBTableName + aliasCount++;
                int count = aliasCount - 1;
                String sFieldDB	= null;
                String sPiiField = null;
                Field field	= null;
		        try
                {
			        if (sArrIDFields != null)
                    {
			        	
				        // use the first primary key
				        field = mappings.getField(sArrIDFields[0]);
				        
                        // here i get the db name of the primary key for the present table
				        sPiiField = field.getDbField();
				        sFieldDB = field.getDbField() + aliasFieldCount++;
                        // increase the count for aliasField for all other key field.
				        for (int i = 1 ; i < sArrIDFields.length ; i++)
                        {
					        aliasFieldCount++;
				        }
			        }
                    // here i get the value of primaryKey from the record set
			        sPrimaryKeyValue = result.getString(sFieldDB);
		        } catch(Exception e)
                {
		        	logger.error("\nException in ExportDataCollector class in collectData method :",e);
		        }
		        // process record
		        if ( !smTableData.containsKey(sPrimaryKeyValue))
                {
                    /*
                    the record with the primary key is not inculded in the tableData map hence this is new
                    record and we have to put this information in the map against the key(primary key).
                    */

			        // create new map and put the main table informtion
		        	
		        		smData = new SequenceMap();
		        	

                    // collect other data for the table
			        String sGetSelected	= request.getParameter("getSelectedField");
        			String [] sArrSelectedField	= null;
        			
        			
        			                    // get the selected field names if sGetSelected is not null
			        if (sGetSelected != null)
                    {
				        sArrSelectedField = request.getParameterValues(moduleName + count);
				        sArrSelectedFieldList	= new ArrayList();
				        for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
	        				sArrSelectedFieldList.add(sArrSelectedField[i]);
						}
			        }
			        sArrTableFields	= mappings.getAllFields(); ////BBEH_FOR_SMC_OPTIMIZATION 
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

			        if (sArrTableFields != null)
                    {
			        	String isFranchisee = FieldNames.EMPTY_STRING;
				        for (int k = 0; k < size ; k++)
                        {
					        field = (Field) sArrTableFields.get(k);
					        /*if("fbc".equals(field.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
								continue;
							}*/			//Commented for FIM Export failsDKI
					        if(processHeaders){
					        	continue;
					        }
					        if (sGetSelected != null )
                            {
					        	if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
					        		continue;
					        	}
					        	if(("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")))  && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
		                    		includeFran = true;
		                    		continue;
		    					}
						        field = mappings.getField(sArrSelectedField[k]);
					        }
					        if(!field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
					        	continue;
					        }
					        
					        if("fbc".equals(field.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
								continue;
							}		//FIM Export failsDKI
					       
					        //START : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
							if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(moduleName) || "cmContactStatusChangeInfoExport".equals(moduleName)))
							{
								if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
									continue;
								}
							}
							//END : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
                            
					        boolean bExportTable = field.isFieldExportable();
                            if (bExportTable)
                            {
						        // processing for the field value
						        String sFieldAliasName	= field.getDbField() + aliasFieldCount;
						        // get the data if coming from other table
                                if (field.getSrcTable() != null)
                                {
                                    FieldMappings srcMap = DBUtil.getInstance().getFieldMappings(field.getSrcTable());
                                    sFieldAliasName	= srcMap.getDbField(field.getSrcValue()) + aliasFieldCount;
                                }
						        String sFieldValue = result.getString(sFieldAliasName);
						        sFieldValue=StringUtil.revertAppostrophi(sFieldValue);//P_CM_B_71332
						        sFieldValue=StringUtil.revertBrackets(sFieldValue);//P_CM_B_71332
						        if("isFranchisee".equals(field.getFieldName())) {
						        	isFranchisee = sFieldValue;
						        }
						        if("franchisees".equals(moduleName) && "isStore".equals(field.getFieldName()) && "N".equals(isFranchisee)) {
						        	if("Y".equals(sFieldValue))
						        		smData.put("status", _baseConstants.IN_DEVELOPMENT_LBL);
						        	aliasFieldCount++;//BBP-20150831-675
						        	continue;
						        }
						        if(sFieldAliasName.equals("CREDIT_CARD"+aliasFieldCount))
						        {
						        	if(sFieldValue!=null && sFieldValue.trim().equals("0"))
						        	{
						        		sFieldValue=new String("Credit Card");
						        	}else if(sFieldValue!=null && sFieldValue.trim().equals("1"))
						        	{
						        		sFieldValue=new String("Credit Line");
						        	}
						        }
						        String sbTransMethodActParam= "";
						        if(StringUtil.isValid(field.getTransformMethodExportParam())) {
						        	sbTransMethodActParam = field.getTransformMethodExportParam();
						        }  else {
						        	sbTransMethodActParam = field.getTransformMethodParam();
						        }
						        if(StringUtil.isValid(field.getTransformExportMethodParam())) {
						        	sbTransMethodActParam = field.getTransformExportMethodParam();
						        }
						        
						        if("cmCampaignEmailLog".equals(moduleName) && "mailRead".equals(field.getFieldName())){//P_CM_B_68434 Add MailReadField to Export
						        	sbTransMethodActParam=null;
						        }
						        
						        /** Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country*/
                                if (sbTransMethodActParam!=null)
                                {
                                	 sArrTableFieldsTemp = null;//BBEH_FOR_SMC_OPTIMIZATION 
							        int sizeTemp = sArrTableFields.size();
							        Field fieldTemp = null;
	    						    String sTransformMethodParamAlias = null;

                                    for (int l=0;l<sizeTemp ;l++ )
                                    {
                                        fieldTemp = (Field)sArrTableFields.get(l);

                                            if (fieldTemp.getDbField().equals(sbTransMethodActParam)) 
                                            {
                                                sTransformMethodParamAlias = sbTransMethodActParam +"_"+field.getDbField();
                                                sArrTableFieldsTemp = null;
                                                fieldTemp = null;
                                                break;
                                            }
                                    }
                                    field.setTableName(moduleName);
							        sFieldValue	= transform(field, sFieldValue, result.getString(sTransformMethodParamAlias), sbTransMethodActParam);
							        if("renewalOptions".equals(field.getFieldName()) && StringUtil.isValid(fileType))
							        {
							        	sFieldValue = sFieldValue.replaceAll("&nbsp;", " ");
							        	sFieldValue = sFieldValue.replaceAll("<b>", "");
							        	sFieldValue = sFieldValue.replaceAll("</b>", "");
							        }
						        } else if(fileType == null || fileType.equals("") || fileType.equals(" "))
                                {
							        // transform field value
						        	field.setTableName(moduleName);
						        	if( !(sFieldValue).equals(_baseConstants.IN_DEVELOPMENT_LBL)) {
						        		sFieldValue = transform(field, sFieldValue);
						        	}
						        	
						        	if("File".equals(field.getDisplayTypeField())) { //file type
						        		String fileName = sFieldValue;
						        		String documentEx = FieldNames.EMPTY_STRING;
						        		if(StringUtil.isValidNew(fileName)) {
											String documentTitle = fileName.substring(0, fileName.lastIndexOf("_"));
                                            if(fileName.indexOf(".") != -1) {
                                                documentEx = fileName.substring(fileName.indexOf("."));
                                            } else {
                                                documentEx = FieldNames.EMPTY_STRING;
                                            }
                                            sFieldValue = documentTitle + documentEx;
										}
						        	}
						        } else
                                {
							        // transform field value
						        	field.setTableName(moduleName);	//CUSTOM_FIELD_DATAEXPORT
							        sFieldValue = transformFileType(field, sFieldValue);
							        
							        if("File".equals(field.getDisplayTypeField())) { //file type
						        		String fileName = sFieldValue;
						        		String documentEx = FieldNames.EMPTY_STRING;
						        		if(StringUtil.isValidNew(fileName)) {
											String documentTitle = fileName.substring(0, fileName.lastIndexOf("_"));
                                            if(fileName.indexOf(".") != -1) {
                                                documentEx = fileName.substring(fileName.indexOf("."));
                                            } else {
                                                documentEx = FieldNames.EMPTY_STRING;
                                            }
                                            sFieldValue = documentTitle + documentEx;
										}
						        	}
						        }
                                //P_B_Codebase_22112013
                                if("Double".equals(field.getValidationType()) && StringUtil.isValid(sFieldValue)) {
                                	if("supplies".equals(menuName) && "itemCurrentPrice".equals(field.getFieldName())){
                                		 if(!StringUtil.isvalidDoubleNewWithZero(sFieldValue,"0.00")){
                                	        	 sFieldValue="NA";
                                	         }else{
                                	        	 sFieldValue = NumberFormatUtils.formatCommaNumber(sFieldValue);
                                	         }
                                	}else{
                                		sFieldValue = NumberFormatUtils.formatCommaNumber(sFieldValue);
                                	}
                            	}else if("Integer".equals(field.getValidationType()) && ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))) ) {
                            		/**
									 * IF_INT_MINUS_ONE
									 * DO not set value if its -1
									 */
									if(!StringUtil.isValidNew(sFieldValue)) {
										sFieldValue = "";
									}
                            	}
                                /**
                                 * Will be called if main-table included for PII-conversion
                                 */
                                String sFieldName = field.getFieldName() ;
						        if (moduleName!=null && !moduleName.equals("") && (moduleName.startsWith("fim") || moduleName.startsWith("area") || moduleName.startsWith("franchisees") || moduleName.equals("franchiseeCall") || moduleName.startsWith("fs") || moduleName.startsWith("cm") || ("fim".equals(menuName) && ("tasks".equals(moduleName) || "outlookMails".equals(moduleName)))))//P_CM_B_47837  //P_FIM_B_50288
						        {
						        	if (field != null && sFieldValue != null && field.getDataType() != null && field.getDataType().equals("Date") && sFieldValue != null ) 
                                    {
						        		// P_E_DATE_FORMAT
						        		// P_FIM_B_37183
						        		if (sFieldValue.length()>10)
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue.substring(0, 10));
						        		} else
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue);
						        		}
						        		

                                    }
						        }
						        //P_CM_B_47837 :starts
						        if("Date".equalsIgnoreCase(field.getDataType()))    
						        {
                                	sFieldValue  = DateUtil.getDisplayDate(sFieldValue);
						        }
						        //P_CM_B_47837 :ends
						        
						        if("unsubscribeDate".equals(field.getFieldName()) && !StringUtil.isValid(sFieldValue)){
				        			
				        			sFieldValue="--";
				        		}
						        
						        if(field.isPiiEnabled())
						        {
						        	String moduleNameForPii = (String)request.getSession().getAttribute(FieldNames.MENU_NAME);
						        	String tempfimFlag = (String)request.getSession().getAttribute("fimFlag");			//P_Enh_FC-76
						        	if("fs".equalsIgnoreCase(moduleNameForPii) || "fim".equalsIgnoreCase(moduleNameForPii))
						        	{
						        		String mainTableName = FieldNames.EMPTY_STRING;
						        		String columnName = FieldNames.EMPTY_STRING;
						        		if("fs".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			mainTableName = "FS_LEAD_DETAILS";
						        			columnName = "LEAD_ID";
						        		}else if("fim".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			if("0".equals(tempfimFlag))					//P_Enh_FC-76 starts
						        			{
						        				mainTableName = "FRANCHISEE";
						        				columnName = "ENTITY_ID";
						        			}else if("1".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "area";
						        				mainTableName = "AREAS";
						        				columnName = "AREA_ID";
						        			}else if("2".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "mu";
						        				mainTableName = "multiUnitOwnerExport";
						        				columnName = "FRANCHISE_OWNER_ID";
						        			}else if("3".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "mu";
						        				mainTableName = "entityDisplayDetail";
						        				columnName = "FIM_ENTITY_ID";
						        			}														//P_Enh_FC-76 ends
						        		}
						        		String tableAnchorForPii = FieldNames.EMPTY_STRING;
						        		String piiEntityId = FieldNames.EMPTY_STRING;
						        		SequenceMap customTabMap 	= BuilderCustomTab.newInstance().getCustomTab(moduleNameForPii, null);
						        		Info customTabInfo			= null;
						        		boolean isEntryNotDone = true;
						        		if(customTabMap != null)
						        		{
						        			Iterator cusTabIterator=customTabMap.keys().iterator();
						        			while(cusTabIterator.hasNext())
						        			{
						        				customTabInfo			= (Info)customTabMap.get(cusTabIterator.next());
						        				if(customTabInfo != null)
						        				{
						        					if(moduleName.equals(customTabInfo.get(BuilderFormFieldNames.TABLE_ANCHOR)));
						        					{
						        						if("1".equals(tempfimFlag))									//P_Enh_FC-76 starts
									        			{
									        				columnName = "AREA_INFO_ID";
									        			}else if("2".equals(tempfimFlag) || "3".equals(tempfimFlag)) 
									        			{
									        				columnName = "MU_ID";
									        			}
						        						if(StringUtil.isValidNew(columnName))
						        						{
						        							piiEntityId = SQLUtil.getColumnValue(customTabInfo.get(BuilderFormFieldNames.DB_TABLE), columnName, "ID_FIELD", sPrimaryKeyValue);
						        						}														//P_Enh_FC-76 ends
						        						if(StringUtil.isValidNew(piiEntityId))
						        						{
						        							isEntryNotDone = false;
						        							if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);

						        							} else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        							}
						        						}
						        					}
						        				}
						        			}
						        		}
						        		if(isEntryNotDone) {
						        			if(!mainTableName.equals(sDBTableName))
						        			{
						        				if("1".equals(tempfimFlag))				//P_Enh_FC-76 starts
							        			{
							        				columnName = "AREA_ID";
							        			}else if("2".equals(tempfimFlag) || "3".equals(tempfimFlag)) //Bug 82024
							        			{
							        				columnName = "MU_ID";
							        			}/*else if("2".equals(tempfimFlag))
							        			{
							        				columnName = "FRANCHISE_OWNER_ID";
							        			}else if("3".equals(tempfimFlag))
							        			{
							        				columnName = "FIM_ENTITY_ID";
							        			}	*/									//P_Enh_FC-76 ends
						        				String primaryEntityId = SQLUtil.getColumnValue(sDBTableName, columnName, sPiiField, sPrimaryKeyValue);
						        				if(StringUtil.isValidNew(primaryEntityId))
						        				{
						        					if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, primaryEntityId);

						        					}else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, primaryEntityId);
						        					}
						        				}
						        			}else
						        			{
						        				if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);

						        				}else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        				}
						        			}
						        		}
						        	}
						        	else
						        	{
						        		if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);

						        		}else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        		}
						        	}
						        }
						        // append aliasFieldCount by one
						        aliasFieldCount++;
                                /**
                                 * Modified by Sunilk on 27 Feb 2006 
                                 * For Formating the date fields for FIM in export/advance search
                                */
                                if (field.isCustomField() && fileType != null && fileType.equals("xml"))
                                {
                                    String fieldName = field.getDisplayName();

                                    if (sFieldName != null && sFieldName.indexOf("C") != -1)
                                    {
                                        String fieldNo = sFieldName.substring(sFieldName.lastIndexOf("C")+1, sFieldName.length());
                                        fieldName = getActualName(moduleName, fieldNo);

                                        if (fieldName != null && !fieldName.equals(""))
                                        {
                                            smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                        	
                                        }
                                    } else
                                    {
                                        smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                    }

                                } else
                                {	//P_B_EXPORT_61519 data not appearing whiile i18n is implemented
                                	
                                	if((!ModuleUtil.zcubatorImplemented() || !"Y".equals(_baseConstants.CM_PILOT_ENABLED)) && "marketingCodeId".equals(sFieldName) && ("cm".equals(menuName))){//P_CM_B_26948
                        				//Activity code field to be skipped when Pilot is not enabled.
                                		continue;
                                	}if ("cm".equals(menuName) && "taskType".equals(field.getFieldName())){
                        				continue;
                        			}else{
	                                	if(StringUtil.isValid(field.getDisplayExportName())) {
							            	smData.put(formatFieldNames(field.getDisplayExportName(),field), sFieldValue);//P_B_Codebase_22112013
							            } else {
							            	smData.put(formatFieldNames(field.getDisplayName(),field), sFieldValue);//P_B_Codebase_22112013
							            }
                                	}
                                }
					        }
				        }
				        
				        
			        }
		        } else
                {
			        smData = (SequenceMap) smTableData.get(sPrimaryKeyValue);
			        // increament count processing
                    String [] sArrSelectedField	= null;
                    String sGetSelected	= request.getParameter("getSelectedField");

                    // get the selected field names if sGetSelected is not null
                    if (sGetSelected != null)
                    {
				        sArrSelectedField = request.getParameterValues(moduleName + count);
			        } 

                    sArrTableFields	= mappings.getAllFields();//BBEH_FOR_SMC_OPTIMIZATION 
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

                    if (sArrTableFields != null)
                    {
                        for (int k = 0; k < size ; k++)
                        {
                            field = (Field) sArrTableFields.get(k);
                            if ("fim".equals(menuName) && ("3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))))) {
                            	if(processHeaders) {
                            		continue;
                            	}
                            }
                            if(processHeaders) { //P_B_78070  starts complain+ owner
                        		continue;
                        	}						//P_B_78070 ends
                            if (sGetSelected != null)
                            {
                                field = mappings.getField(sArrSelectedField[k]);
                                if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
					        		continue;
					        	}
                                if(("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")))  && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
		                    		includeFran = true;
		                    		continue;
		    					}
                            }
                            if(!field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
                            	continue;
                            }
                            //ZC_CM_B_46268 :starts
                            
					        if("fbc".equals(field.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
								continue;
							}		//FIM Export failsDKI			//P_B_78070
					        
                            /*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(moduleName) || "cmContactStatusChangeInfoExport".equals(moduleName)))
							{
								if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
									continue;
								}
							}*///ZC_CM_B_46268 :ends
                            //P_B_47214 starts
                           /* if("fim".equals(menuName) && "reportPeriodStartDate".equals(field.getFieldName()) && !ModuleUtil.financialsImplemented()) {
								continue;
                            }*/
                            //P_B_47214 ends
                            
                            boolean bExportTable = field.isFieldExportable();

                            if (bExportTable)
                            {
                                aliasFieldCount++;
                            }
                        }
                    }
		        }
		        
		        hMap = mappings.getHeaderMap();//BBEH_FOR_SMC_OPTIMIZATION 
		   		for(HeaderMap h:hMap) {
		   			
		   			DocumentMap[] docMaps = null;
		   			HeaderField hFld = h.getHeaderFields();
		   			Boolean isTabularSection=hFld.isTabularSection();
		   			if(isTabularSection){
		   				continue;
		   			}
					Documents[] docs =  hFld.getDocuments();
					
					
					if(docs != null && docs.length > 0) {
							docMaps = docs[0].getDocumentMaps();
							if(docMaps != null && docMaps.length > 0) {
								String docTableAnchor=docs[0].getDocumentTableAnchor();////P_B_80290
								for(DocumentMap dMap : docMaps) {
									docMap  = dMap.getDocumentFieldMap(); //BBEH_FOR_SMC_OPTIMIZATION 
									String docOption = (String)docMap.get("doc-option");
									String docSub = (String)docMap.get("title-display");
									String docLab = (String)docMap.get("doc-display");
									if(docOption == null) {
										docOption = "0";
									}
									//P_CM_B_57781 Start
									if(sArrSelectedFieldList !=null) {
										if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {
											String data = CommonMgr.newInstance().getCommonCmDAO().getNewDocumentsDataForExport(sPrimaryKeyValue, (String)docMap.get("name"),docOption,docSub,docLab,menuName,docTableAnchor);
											smData.put(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"), data);
										}
									} else {
										String data = CommonMgr.newInstance().getCommonCmDAO().getNewDocumentsDataForExport(sPrimaryKeyValue, (String)docMap.get("name"),docOption,docSub,docLab,menuName,docTableAnchor);
										smData.put(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"), data);
									}
                                                                        //P_CM_B_57781 End
								}
							}
					}
		   		}
		   		if (bMain && "fim".equals(menuName) && ("3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")))) {
			   		if(bMain && !processHeaders && dependentFieldsMap.containsKey(moduleName) && PortalUtils.hasDependentAddressTable(moduleName,request)){
	                	collectHeaderSearchData(moduleName, null, smData, false, "",result,"collectData");
	                }
		   		}
		        // processing of the foreign table data
		        // Modified by Anuj 26-Nov-2004
		   		if (bMain)
                    smForeignTables	= selForeignTables;
                else
                	smForeignTables	= mappings.getForeignTablesExportMap();//smForeignTables	= mappings.getForeignTablesMap();
                
                
                if(includeFran) {
                	SequenceMap tempsmTableData = new SequenceMap();
                	SequenceMap tempsmdataMap = new SequenceMap();
                	String primaryKey = null;
                	if(smData!=null && smData.containsKey("franchisee")) {
                		tempsmTableData = (SequenceMap)smData.get("franchisee");
                	} else {
                		tempsmTableData = new SequenceMap();
                	}
                	if("3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))){
                		aliasFieldCount--;
                	}
                	primaryKey = result.getString("FRANCHISEE_NO"+aliasFieldCount++);
                	tempsmdataMap.put("franchiseeName",result.getString("FRANCHISEE_NAME"+aliasFieldCount++));
                	tempsmTableData.put(primaryKey, tempsmdataMap);
                	smData.put("franchisee", tempsmTableData);
                	if("3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))){
                		aliasFieldCount++;
                	}
                }
                if (smForeignTables != null)
                {
                	boolean isFirstIteration = true;
                    int sizeForTable = smForeignTables.size();
                  //Problem in Custom Report while do export starts
                    String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
    				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
    				//Problem in Custom Report while do export ends
                    
                    
                   for (int i = 0 ; i < sizeForTable ; i++)
                    {
                    	if(i>0){
                    		isFirstIteration = false;
                    	}
                        ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
                      //Problem in Custom Report while do export starts
                        if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
    						continue;
    					}
                      //Problem in Custom Report while do export ends
                        if("fimEntityDetail".equals(moduleName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts
        					if("address".equals(foreignTable.getName()))
        						continue;
        					}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends
                        if (!smData.containsKey(foreignTable.getName()))
                        {
                           	smData.put(foreignTable.getName(), new SequenceMap());
                           	
                        }

                        // Modified by Anuj 26-Nov-2004
                        collectData(foreignTable.getName(), result, smData, false,FieldNames.EMPTY_STRING,isFirstIteration);
                       	//P_E_Fields-20130905-035 starts
                       	if(!processHeaders && dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
                        	collectHeaderSearchData(foreignTable.getName(), null, smData, false, "",result,"collectData");
                        }
        				//P_E_Fields-20130905-035 ends
                    }
                }
                
                smTableData.put(""+sPrimaryKeyValue , smData);
              
                if(processHeaders){
                	smDataMap.put(headerName+moduleName , smTableData);//P_E_Fields-20130905-035
                }else{
                	smDataMap.put(moduleName , smTableData);
                }
	        }
           
	    } catch(Exception e)
        {
	    	e.printStackTrace();
	    	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->:collectData() " , e);
	    }
	  //BBEH_FOR_SMC_OPTIMIZATION  starts
	    finally{
	    	
	    	if(smTableData!=null)
	    	{
	    		smTableData=null;
	    	}
	    	
	    	if(smData!=null)
	    	{
	    		smData=null;
	    	}
	    	
	    	if(sArrTableFields!=null)
	    	{
	    		sArrTableFields=null;
	    	}
	    	
	    	if(sArrTableFieldsTemp!=null)
	    	{
	    		sArrTableFieldsTemp=null;
	    	}
	    	
	    	if(docMap!=null)
	    	{
	    		docMap=null;
	    	}
	    	
	    	if(smForeignTables!=null)
	    	{
	    		smForeignTables=null;
	    	}
	            
	    }
	  //BBEH_FOR_SMC_OPTIMIZATION ends
    }
    /**
     * @author abhishek gupta
     * @date 27 aug 2009
     * @param moduleName
     * @param result
     * @param smDataMap
     * @param bMain
     */
    private void collectSearchData(String moduleName, java.sql.ResultSet result , SequenceMap smDataMap, boolean bMain) {
    	collectSearchData(moduleName, result , smDataMap, bMain, null);
    }
    private void collectSearchData(String moduleName, java.sql.ResultSet result , SequenceMap smDataMap, boolean bMain,String dashBoard) {
    	collectSearchData(moduleName, result , smDataMap, bMain,dashBoard,null,null,FieldNames.EMPTY_STRING);
    }
    //P_E_Fields-20130905-035 Changed arguments
    private void collectSearchData(String moduleName, java.sql.ResultSet result , SequenceMap smDataMap, boolean bMain, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName)
    {
    	UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	    try
        {
	    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
            SequenceMap smTableData	= (SequenceMap) smDataMap.get(moduleName);
            FieldMappings mappings = null;
	        mappings = DBUtil.getInstance().getFieldMappings(moduleName);
	        //P_E_FS_INTER start
	        boolean isFsMenuWithI18n=false;
	        boolean processHeaders = false;
	        if(hFld!=null){
	        	processHeaders = true;//P_E_Fields-20130905-035
	        	smTableData	= (SequenceMap) smDataMap.get(headerName+moduleName);//P_E_Fields-20130905-035
            }
	        if(dashBoard != null) {
	        	isFsMenuWithI18n=true;
	        } else {
				if (LanguageUtil.isI18nImplemented())
				{
					isFsMenuWithI18n=true;
				}
	        }
	        //P_E_FS_INTER ends
            if (mappings != null)
            {
                String sPrimaryKeyValue	= null;
                String[] sArrIDFields = mappings.getIdFieldNames();
                String sDBTableName	= mappings.getTableName();
                String sTableAliasName = sDBTableName + aliasCount++;
                int count = aliasCount - 1;
                String sFieldDB	= null;
                Field field	= null;
                String sPiiField = null;
		        try
                {
			        if (sArrIDFields != null)
                    {
			        	if("fim".equals(menuName) && "FRANCHISEE".equals(sDBTableName)) {
                    		aliasFieldCount = aliasFieldCount+2;
                    	}
				        // use the first primary key
				        field = mappings.getField(sArrIDFields[0]);
				        
                        // here i get the db name of the primary key for the present table
				        sPiiField = field.getDbField();
				        sFieldDB = field.getDbField() + aliasFieldCount++;
				        
                        // increase the count for aliasField for all other key field.
				        for (int i = 1 ; i < sArrIDFields.length ; i++)
                        {
					        aliasFieldCount++;
				        }
			        }
			        
                    // here i get the value of primaryKey from the record set
			        sPrimaryKeyValue = result.getString(sFieldDB);
			        System.out.println("\n\n"+result+"<<<resultresult>>"+sFieldDB);
		        } catch(Exception e)
                {
		        	logger.error("\nException in ExportDataCollector class in collectSearchData method :"+e.getMessage());

		        }

		        // process record
		        SequenceMap smData = null;

		        if (!smTableData.containsKey(sPrimaryKeyValue))
                {
                    /*
                    the record with the primary key is not inculded in the tableData map hence this is new
                    record and we have to put this information in the map against the key(primary key).
                    */

			        // create new map and put the main table informtion
			        smData = new SequenceMap();

                    // collect other data for the table
			        String sGetSelected	= request.getParameter("getSelectedField");
			        if(sGetSelected == null) {
			        	sGetSelected	= (String)request.getAttribute("getSelectedField");
			        }
        			String [] sArrSelectedField	= null;

                    // get the selected field names if sGetSelected is not null
			        if (sGetSelected != null)
                    {
			        	if(dashBoard != null) {
					        sArrSelectedField = (String [])request.getAttribute(moduleName + count);
			        	} else {
					        sArrSelectedField = request.getParameterValues(moduleName + count);
			        	}
			        }
			        SequenceMap sArrTableFields	= null;
		        	if(processHeaders){
		        		sArrTableFields = mappings.getDependentTblFields(hFld);//P_E_Fields-20130905-035
		        	}else{
		        		sArrTableFields = mappings.getAllFields();
		        	}
			        
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

			        if (sArrTableFields != null)
                    {
			        	String reportType = request.getParameter("reportType");
				        if(reportType == null) {
				        	reportType	= (String)request.getAttribute("reportType");
				        }
			        	boolean checkGroupBy = false;

			        	
			        	
			        	
				        for (int k = 0; k < size ; k++)
                        {
					        // Field field = mappings.getField(sArrTableFields[k]);
				        	if(sArrTableFields.get(k) == null) {
				        		continue;
				        	}
				        	String tmpFld = "";
				        	String tmpFldLbl = "";
					        field = (Field) sArrTableFields.get(k);

					        if (sGetSelected != null )
                            {
					        	tmpFld = sArrSelectedField[k].split(":")[0];
					        	tmpFldLbl = sArrSelectedField[k].split(":")[1];
					        	//P_E_Fields-20130905-035
					        	if(processHeaders){
					        		field = parentMappings.getOtherTableField(tmpFld.trim());
					        	}else{
					        		field = mappings.getField(tmpFld.trim());
					        	}
					        	//P_E_Fields-20130905-035
					        	if(field==null || !field.isActiveField())		//P_BUGS_FIM Points starts
			                    {
					        		continue;
			                    }	//P_BUGS_FIM Points ends
					        }
					        
					        if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
							{
								continue;
							}
					        boolean bExportTable = field.isFieldExportable();
                            if (bExportTable)
                            {
                            	checkGroupBy = true;
						        // processing for the field value
                            	
						        String sFieldAliasName	= field.getDbField() + aliasFieldCount;
						        // get the data if coming from other table
                                if (field.getSrcTable() != null)
                                {
                                    FieldMappings srcMap = DBUtil.getInstance().getFieldMappings(field.getSrcTable());
                                    sFieldAliasName	= srcMap.getDbField(field.getSrcValue()) + aliasFieldCount;
                                }						
						        String sFieldValue = result.getString(sFieldAliasName);
						        //ZCUB-20151124-202 Start
						        if(ModuleUtil.zcubatorImplemented()){
							        groupColumnName=field.getFieldName();
							        if (field.getSrcTable() != null && "SG".equals(reportType)){
							        	groupValue = result.getString(field.getDbField());
							        }else{
							        	groupValue = result.getString(sFieldAliasName);
							        }
							        groupColumnType=field.getDataType();
							        groupTableName=moduleName;
							        groupDbTableName=sDBTableName;
						        }
						        //ZCUB-20151124-202 End
						        if(sFieldValue == null || ("noOfEmployee".equals(field.toString()) && "-1".equals(sFieldValue))) {   //P_CM_B_36610 Modified
						        	sFieldValue = "";
						        }
						        if(sFieldAliasName.equals("CREDIT_CARD"+aliasFieldCount))
						        {
						        	if(sFieldValue!=null && sFieldValue.trim().equals("0"))
						        	{
						        		sFieldValue=new String("Credit Card");
						        	}else if(sFieldValue!=null && sFieldValue.trim().equals("1"))
						        	{
						        		sFieldValue=new String("Credit Line");
						        	}
						        }
						        /**
						         * Allow checking Export method param in Main XML processed Objects.
						         */
						        String tmpTransMethodParam = null;
						        if(field.getTransformMethodExportParam() != null) {
						        	tmpTransMethodParam = field.getTransformMethodExportParam();
						        } else {
						        	tmpTransMethodParam = field.getTransformMethodParam();
						        }
						        if(field.getTransformExportMethodParam() != null) {
						        	tmpTransMethodParam = field.getTransformExportMethodParam();
						        } 
                                if (tmpTransMethodParam!=null)
                                {
							        SequenceMap sArrTableFieldsTemp = mappings.getAllFields();
							        int sizeTemp = sArrTableFields.size();
							        Field fieldTemp = null;
	    						    String sTransformMethodParamAlias = null;

                                    for (int l=0;l<sizeTemp ;l++ )
                                    {
                                        fieldTemp = (Field)sArrTableFields.get(l);

                                    //    if (fieldTemp.isFieldExportable())                                        {	
                                            if (fieldTemp.getDbField().equals(tmpTransMethodParam)) 
                                            {
                                                sTransformMethodParamAlias = tmpTransMethodParam +"_"+field.getDbField();
                                                sArrTableFieldsTemp = null;
                                                fieldTemp = null;
                                                break;
                                            }
                                    }
                                    if("Date".equalsIgnoreCase(field.getDataType())) {
                                    	if(reportType != null && ("SG".equals(reportType) || "MG".equals(reportType))) {
                                        	String isEnquiryDate = request.getParameter(moduleName+":"+field.getFieldName()+":FORMAT");
                    				        if(isEnquiryDate == null) {
                    				        	isEnquiryDate	= (String)request.getAttribute(moduleName+":"+field.getFieldName()+":FORMAT");
                    				        }
                                        	if(isEnquiryDate != null && isEnquiryDate.split(":").length == 2) {
                                        		String dateType = isEnquiryDate.split(":")[0].trim();
                                        		if("41".equals(dateType)) {
                                        			String val = sFieldValue.trim();//DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy HH:mm:ss");
                                        			sFieldValue = ""+DateUtil.getAgeFromYearWithWeekToWeeks(val);
                                        		} else if("42".equals(dateType)) {
                                        			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                        			sFieldValue = ""+DateUtil.getAgeInTermsOfMonths(val);
                                        		} else if("43".equals(dateType)) {
                                        			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                        			sFieldValue = ""+DateUtil.getAgeInTermsOfYear(val);
                                        		} else {
                                        			sFieldValue = sFieldValue.trim();
                                        		}
                                        		
                                        	} else {
                                            	if(sFieldValue.length()<=8) {
                							        sFieldValue	= sFieldValue.trim();
                                            	} else {
                                            		field.setTableName(moduleName);
                							        sFieldValue	= transform(field, sFieldValue, result.getString(sTransformMethodParamAlias), tmpTransMethodParam);
                                            	}
                                        	}
                                    	} else {
                                        	if(sFieldValue.length()<=8) {
            							        sFieldValue	= sFieldValue.trim();
                                        	} else {
                                        		field.setTableName(moduleName);
            							        sFieldValue	= transform(field, sFieldValue, result.getString(sTransformMethodParamAlias), tmpTransMethodParam);
                                        	}
                                    	}
                                    } else {
                                    	field.setTableName(moduleName);
    							        sFieldValue	= transform(field, sFieldValue, result.getString(sTransformMethodParamAlias), tmpTransMethodParam);
                                    }
						        } else if(fileType == null || fileType.equals("") || fileType.equals(" "))
                                {
							        // transform field value
							        if("Date".equalsIgnoreCase(field.getDataType())) {
                                    	String isEnquiryDate = request.getParameter(moduleName+":"+field.getFieldName()+":FORMAT");
                				        if(isEnquiryDate == null) {
                				        	isEnquiryDate	= (String)request.getAttribute(moduleName+":"+field.getFieldName()+":FORMAT");
                				        }
                                    	if(isEnquiryDate != null && isEnquiryDate.split(":").length == 2) {
                                    		String dateType = isEnquiryDate.split(":")[0].trim();
                                    		if("41".equals(dateType)) {
                                    			String val = sFieldValue.trim();//DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy HH:mm:ss");
                                    			sFieldValue = ""+DateUtil.getAgeFromYearWithWeekToWeeks(val);
                                    		} else if("42".equals(dateType)) {
                                    			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                    			sFieldValue = ""+DateUtil.getAgeInTermsOfMonths(val);
                                    			
                                    		} else if("43".equals(dateType)) {
                                    			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                    			sFieldValue = ""+DateUtil.getAgeInTermsOfYear(val);
                                    		} else {
                                    			sFieldValue = sFieldValue.trim();
                                    		}
                                    	} else {
                                        	if(sFieldValue.length()<=8) {
            							        sFieldValue	= sFieldValue.trim();
                                        	} else {
                                        		field.setTableName(moduleName);
                                        		sFieldValue = transform(field, sFieldValue);
                                        	}
                                    	}
                                    		
						        	} else if("File".equals(field.getDisplayTypeField())) {
						        		field.setTableName(moduleName);
						        		String fileName = sFieldValue;
						        		String documentEx = FieldNames.EMPTY_STRING;
						        		if(StringUtil.isValidNew(fileName)) {
											String documentTitle = fileName.substring(0, fileName.lastIndexOf("_"));
                                            if(fileName.indexOf(".") != -1) {
                                                documentEx = fileName.substring(fileName.indexOf("."));
                                            } else {
                                                documentEx = FieldNames.EMPTY_STRING;
                                            }
                                            sFieldValue = documentTitle + documentEx;
										}
						        	}
						        	else {
						        		field.setTableName(moduleName);
						        		//sFieldValue = transform(field, sFieldValue);
						        		/*if("ufocSendSchedule".equals(moduleName) && "ufocSentBy".equals(field.getFieldName()) && "0".equals(sFieldValue)) {
						        			sFieldValue = "Web site User";
						        		} else {
						        			sFieldValue = transform(field, sFieldValue);
						        		}*/
						        		sFieldValue = transform(field, sFieldValue);
						        		if("Integer".equals(field.getValidationType()) && "fim".equals(menuName)) {
		                            		/**
											 * IF_INT_MINUS_ONE
											 * DO not set value if its -1
											 */
											if(!StringUtil.isValidNew(sFieldValue)) {
												sFieldValue = "";
											}
		                            	}
						        		if("fim".equals(menuName) && "FRANCHISEE".equals(sDBTableName) && "N".equals(result.getString("IS_FRANCHISEE1")) && "Y".equals(result.getString("IS_STORE2")) && "status".equals(field.getFieldName())){
						        			sFieldValue = MultiTenancyUtil.getTenantConstants().IN_DEVELOPMENT_LBL;
						        		}
						        		//P_CustomReport_Enh_BrokerName starts
						        		if("leadSource3ID".equals(field.getFieldName())) {
						        			String brokerId = result.getString("BROKER_ID");
						        			if(StringUtil.isValidNew(brokerId)) {
						        	    		ClassLoader classLoader = ExportDataCollector.class.getClassLoader();
						        	    		try {
													Class cl = classLoader.loadClass("com.appnetix.app.components.leadmgr.manager.dao.FsLeadDetailsDAO");
						                            Class clsArr[] = new Class[]{String.class};
						                            Object valArr[] = new Object[]{brokerId};
													Method m = cl.getDeclaredMethod("getBrokerName", clsArr);
													String brokerName = (String)m.invoke(cl.newInstance(), valArr);
													sFieldValue = sFieldValue + " (" + brokerName + ")";
												} catch(Exception e) {
													e.printStackTrace();
												}
						        			}
						        		}
						        		//P_CustomReport_Enh_BrokerName ends
						        	}
						        } else
                                {
							        // transform field value
						        	if("Date".equalsIgnoreCase(field.getDataType())) {
                                    	String isEnquiryDate = request.getParameter(moduleName+":"+field.getFieldName()+":FORMAT");
                				        if(isEnquiryDate == null) {
                				        	isEnquiryDate	= (String)request.getAttribute(moduleName+":"+field.getFieldName()+":FORMAT");
                				        }
                                    	if(isEnquiryDate != null && isEnquiryDate.split(":").length == 2) {
                                    		String dateType = isEnquiryDate.split(":")[0].trim();
                                    		if("41".equals(dateType)) {
                                    			String val = sFieldValue.trim();//DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy HH:mm:ss");
                                    			sFieldValue = ""+DateUtil.getAgeFromYearWithWeekToWeeks(val);
                                    		} else if("42".equals(dateType)) {
                                    			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                    			sFieldValue = ""+DateUtil.getAgeInTermsOfMonths(val);
                                    		} else if("43".equals(dateType)) {
                                    			String val = DateUtil.formatDate(sFieldValue.trim(), "MM/dd/yyyy");
                                    			sFieldValue = ""+DateUtil.getAgeInTermsOfYear(val);
                                    		} else {
                                    			sFieldValue = sFieldValue.trim();
                                    		}
                                    	} else {
                                        	if(sFieldValue.length()<=8) {
            							        sFieldValue	= sFieldValue.trim();
                                        	} else {
            							        sFieldValue = transformFileType(field, sFieldValue);
                                        	}
                                    	}
                                    	if (sFieldValue.length()>10)
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue.substring(0, 10));
						        		} else
						        		{
	                                    	if(sFieldValue.length()<=8) {
	        							        sFieldValue	= sFieldValue.trim();
	                                    	} else {
							        			sFieldValue = DateUtil.getDisplayDate(sFieldValue);
	                                    	}
						        		}
						        	} else if("File".equals(field.getDisplayTypeField())) {
						        		String fileName = sFieldValue;
						        		String documentEx = FieldNames.EMPTY_STRING;
						        		if(StringUtil.isValidNew(fileName)) {
											String documentTitle = fileName.substring(0, fileName.lastIndexOf("_"));
                                            if(fileName.indexOf(".") != -1) {
                                                documentEx = fileName.substring(fileName.indexOf("."));
                                            } else {
                                                documentEx = FieldNames.EMPTY_STRING;
                                            }
                                            sFieldValue = documentTitle + documentEx;
										}
						        	} else {
						        		sFieldValue = transformFileType(field, sFieldValue);
						        		if("Integer".equals(field.getValidationType()) && "fim".equals(menuName)) {
		                            		/**
											 * IF_INT_MINUS_ONE
											 * DO not set value if its -1
											 */
											if(!StringUtil.isValidNew(sFieldValue)) {
												sFieldValue = "";
											}
		                            	}
						        		if("fim".equals(menuName) && "FRANCHISEE".equals(sDBTableName) && "N".equals(result.getString("IS_FRANCHISEE1")) && "Y".equals(result.getString("IS_STORE2")) && "status".equals(field.getFieldName())){
						        			sFieldValue = MultiTenancyUtil.getTenantConstants().IN_DEVELOPMENT_LBL;
						        		}
						        		//P_CustomReport_Enh_BrokerName starts
						        		if("leadSource3ID".equals(field.getFieldName())) {
						        			String brokerId = result.getString("BROKER_ID");
						        			if(StringUtil.isValidNew(brokerId)) {
						        	    		ClassLoader classLoader = ExportDataCollector.class.getClassLoader();
						        	    		try {
													Class cl = classLoader.loadClass("com.appnetix.app.components.leadmgr.manager.dao.FsLeadDetailsDAO");
						                            Class clsArr[] = new Class[]{String.class};
						                            Object valArr[] = new Object[]{brokerId};
													Method m = cl.getDeclaredMethod("getBrokerName", clsArr);
													String brokerName = (String)m.invoke(cl.newInstance(), valArr);
													sFieldValue = sFieldValue + " (" + brokerName + ")";
												} catch(Exception e) {
													e.printStackTrace();
												}
						        			}
						        		}
						        		//P_CustomReport_Enh_BrokerName ends
						        	}
						        }
                                //P_B_Codebase_22112013
                                if("Double".equals(field.getValidationType()) && StringUtil.isValid(sFieldValue)) {
						        	sFieldValue = NumberFormatUtils.formatCommaNumber(sFieldValue);
                            	}
                                String sFieldName = field.getFieldName() ;
						        if (moduleName!=null && !moduleName.equals("") && (moduleName.startsWith("fim") || moduleName.startsWith("area") || moduleName.startsWith("franchisees") || moduleName.equals("franchiseeCall") || moduleName.equals("cm") || "outlookMails".equals(moduleName) || "muOutlookMailsExport".equals(moduleName)))//P_CM_B_47837 
						        {
						        	if (field != null && sFieldValue != null && field.getDataType() != null && field.getDataType().equals("Date") && sFieldValue != null ) 
                                    {
						        		// P_E_DATE_FORMAT
						        		// P_FIM_B_37183
						        		if (sFieldValue.length()>10)
						        		{
						        			sFieldValue = DateUtil.getDisplayDate(sFieldValue.substring(0, 10));
						        		} else
						        		{
	                                    	if(sFieldValue.length()<=8) {
	        							        sFieldValue	= sFieldValue.trim();
	                                    	} else {
							        			sFieldValue = DateUtil.getDisplayDate(sFieldValue);
	                                    	}
						        		}
						        		
                                    }
						        }
						        if("Date".equalsIgnoreCase(field.getDataType()))    //P_CM_B_38410 Added Starts
						        {
                                	sFieldValue  = DateUtil.getDisplayDate(sFieldValue);
						        }			//P_CM_B_38410 Added Ends
						        if(field.isPiiEnabled())
						        {
						        	String moduleNameForPii = (String)request.getSession().getAttribute(FieldNames.MENU_NAME);
						        	String tempfimFlag = (String)request.getSession().getAttribute("fimFlag");			//P_Enh_FC-76
						        	if("fs".equalsIgnoreCase(moduleNameForPii) || "fim".equalsIgnoreCase(moduleNameForPii))
						        	{
						        		String mainTableName = FieldNames.EMPTY_STRING;
						        		String columnName = FieldNames.EMPTY_STRING;
						        		if("fs".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			mainTableName = "fsLeadDetails";
						        			columnName = "LEAD_ID";
						        		}else if("fim".equalsIgnoreCase(moduleNameForPii))
						        		{
						        			if("0".equals(tempfimFlag))					//P_Enh_FC-76 starts
						        			{
						        				mainTableName = "FRANCHISEE";
						        				columnName = "ENTITY_ID";
						        			}else if("1".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "area";
						        				mainTableName = "AREAS";
						        				columnName = "AREA_ID";
						        			}else if("2".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "mu";
						        				mainTableName = "multiUnitOwnerExport";
						        				columnName = "FRANCHISE_OWNER_ID";
						        			}else if("3".equals(tempfimFlag))
						        			{
						        				moduleNameForPii = "mu";
						        				mainTableName = "entityDisplayDetail";
						        				columnName = "FIM_ENTITY_ID";
						        			}														//P_Enh_FC-76 ends
						        		}
						        		String tableAnchorForPii = FieldNames.EMPTY_STRING;
						        		String piiEntityId = FieldNames.EMPTY_STRING;
						        		SequenceMap customTabMap 	= BuilderCustomTab.newInstance().getCustomTab(moduleNameForPii, null);
						        		Info customTabInfo			= null;
						        		boolean isEntryNotDone = true;
						        		if(customTabMap != null)
						        		{
						        			Iterator cusTabIterator=customTabMap.keys().iterator();
						        			while(cusTabIterator.hasNext())
						        			{
						        				customTabInfo			= (Info)customTabMap.get(cusTabIterator.next());
						        				if(customTabInfo != null)
						        				{
						        					if(moduleName.equals(customTabInfo.get(BuilderFormFieldNames.TABLE_ANCHOR)));
						        					{
						        						if("1".equals(tempfimFlag))									//P_Enh_FC-76 starts
									        			{
									        				columnName = "AREA_INFO_ID";
									        			}else if("2".equals(tempfimFlag) || "3".equals(tempfimFlag)) 
									        			{
									        				columnName = "MU_ID";
									        			}
						        						piiEntityId = SQLUtil.getColumnValue(customTabInfo.get(BuilderFormFieldNames.DB_TABLE), columnName, "ID_FIELD", sPrimaryKeyValue);
						        						if(StringUtil.isValidNew(piiEntityId))
						        						{
						        							isEntryNotDone = false;
						        							if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        							}
						        							else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        								sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        							}
						        						}
						        					}
						        				}
						        			}
						        		}
						        		if(isEntryNotDone) {
						        			if(!mainTableName.equals(moduleName))
						        			{
						        				if("1".equals(tempfimFlag))				//P_Enh_FC-76 starts
							        			{
							        				columnName = "AREA_ID";
							        			}else if("2".equals(tempfimFlag))
							        			{
							        				columnName = "FRANCHISE_OWNER_ID";
							        			}else if("3".equals(tempfimFlag))
							        			{
							        				columnName = "FIM_ENTITY_ID";
							        			}										//P_Enh_FC-76 ends
						        				String primaryEntityId = SQLUtil.getColumnValue(sDBTableName, columnName, sPiiField, sPrimaryKeyValue);
						        				if(StringUtil.isValidNew(primaryEntityId))
						        				{
						        					if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, primaryEntityId);
						        					}

						        					else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        						sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, piiEntityId);
						        					}
						        				}
						        			}else
						        			{
						        				if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        				}
						        				else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        					sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        				}
						        			}
						        		}
						        	}else
						        	{
						        		if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        		}
						        		else if(StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))&&field.isPiiEnabled()&&!field.isBuildField()){//Bug 61177
						        			sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
						        		}
						        	}
						        }
						        /*
                                if(field.isPiiEnabled() && this.getPiiTablePrivInfo()!=null){
                                	sFieldValue = formatPIIFieldsData(sFieldValue,moduleName,field, sPrimaryKeyValue);
                                }*/
						        // append aliasFieldCount by one
						        aliasFieldCount++;
						        
                                if (field.isCustomField() && fileType != null && fileType.equals("xml"))
                                {
                                    String fieldName = null;
                                    if(field.getDisplayExportName() != null) {
                                    	fieldName = field.getDisplayExportName();
                                    } else {
                                    	fieldName = field.getDisplayName();
                                    }
                                    if (sFieldName != null && sFieldName.indexOf("C") != -1)
                                    {
                                        String fieldNo = sFieldName.substring(sFieldName.lastIndexOf("C")+1, sFieldName.length());
                                        fieldName = getActualName(moduleName, fieldNo);

                                        if (fieldName != null && !fieldName.equals(""))
                                        {
                                            smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                        	
                                        }
                                    } else
                                    {
                                        smData.put(formatFieldNames(fieldName,field), sFieldValue);//P_B_Codebase_22112013
                                    
                                    }
                                    if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
                                    	if(field.isCurrencyField()) {
                                    		smData.put(formatFieldNames(fieldName,field), "");//P_B_Codebase_22112013
                                    	}
                                    }

                                } else
                                {
                                	if("SG".equals(reportType) && !StringUtil.isValid(sFieldValue)){
                                		sFieldValue="Unknown";										//P_B_77969
                                	}
                                	if(isFsMenuWithI18n) {
						            	smData.put(formatFieldNames(tmpFldLbl,field), sFieldValue);//P_B_Codebase_22112013,//LaBoit-20110517-068
	                                    if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
	                                    	if(field.isCurrencyField()) {
	                                    		smData.put(formatFieldNames(tmpFldLbl,field), "");//P_B_Codebase_22112013,//LaBoit-20110517-068
	                                    	}
	                                    }
                                	} else {
                                		if(tmpFldLbl != null && !"".equals(tmpFldLbl)) {
                                			smData.put(formatFieldNames(tmpFldLbl,field), sFieldValue);//P_B_Codebase_22112013
    	                                    if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
    	                                    	if(field.isCurrencyField()) {
    	                                    		smData.put(formatFieldNames(tmpFldLbl,field), "");//P_B_Codebase_22112013
    	                                    	}
    	                                    }
                                		} else {
                                			if(StringUtil.isValid(field.getDisplayExportName())) {
                                				smData.put(formatFieldNames(field.getDisplayExportName(),field), sFieldValue);//P_B_Codebase_22112013
                                			} else {
                                				smData.put(formatFieldNames(field.getDisplayName(),field), sFieldValue);//P_B_Codebase_22112013
                                			}
    	                                    if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
    	                                    	if(field.isCurrencyField()) {
    	                                			if(StringUtil.isValid(field.getDisplayExportName())) {
    	                                				smData.put(formatFieldNames(field.getDisplayExportName(),field), "");//P_B_Codebase_22112013
    	                                			} else {
    	                                				smData.put(formatFieldNames(field.getDisplayName(),field), "");//P_B_Codebase_22112013
    	                                			}
    	                                    	}
    	                                    }

                                		}
                                	}
                                }
					        }
                            
				        }
				        
				        if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {

					        	String tempSummryVal = request.getParameter(moduleName+":COUNT:ALL");
        				        if(tempSummryVal == null) {
        				        	tempSummryVal	= (String)request.getAttribute(moduleName+":COUNT:ALL");
        				        }
					        	
			        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
			        				String sFieldCountValue = result.getString(mappings.getTableName()+"COUNT");
			        				//ZCUB-20151124-202 Start
			        				if(ModuleUtil.zcubatorImplemented()){
				        				if(_baseConstants.crSummaryTableList.size()==0){
				        					_baseConstants.addCrSummaryTableList();
				        				}
				        				if(StringUtil.isValidNew(sFieldCountValue) && !"0".equals(sFieldCountValue) && _baseConstants.crSummaryTableList.contains(mappings.getTableName())){
				        					String reportId = request.getParameter("reportID");
				        					if("CM_COMPANY_INFO".equals(mappings.getTableName()) && !userRoleMap.isPrivilegeInMap("/companySummary")) //P_CM_B_68658
				        						sFieldCountValue=new StringBuffer(sFieldCountValue).toString();
				        					else
				        						sFieldCountValue=new StringBuffer("<a href=\"javascript:void(0);\" onClick=\"showDetails('"+mappings.getTableName()+"', '###groupValue###','###groupColumnName###','###groupColumnType###','###groupTableName###','###groupDbTableName###')\">").append(sFieldCountValue).append("</a>").toString();
				        				}
			        				}
			        				//ZCUB-20151124-202 End
			        				if(!"".equals(tempSummryVal.trim())) {
			        					smData.put(tempSummryVal.trim(), sFieldCountValue);
			        				} else {
			        					smData.put("COUNT", sFieldCountValue);
			        				}
			        				
			        			}
			        			//P_E_Fields-20130905-035 starts
					        	Field[] fields	= null;//mappings.getSummaryFieldsArray();
					        	if(processHeaders){
					        		fields = mappings.getSectionTablesFieldsArray(hFld);
					        	}else{
					        		fields = mappings.getSummaryFieldsArray();
					        	}
					        	//P_E_Fields-20130905-035 ends
								for (int k = 0; k < fields.length; k++)
								{
									String sSearchField		= fields[k].getSearchField();
									// continue if field is not specified as search field
									if(sSearchField == null){
										continue;
									}
									tempSummryVal = request.getParameter(moduleName+":SUM:"+fields[k].getFieldName());
	        				        if(tempSummryVal == null) {
	        				        	tempSummryVal	= (String)request.getAttribute(moduleName+":SUM:"+fields[k].getFieldName());
	        				        }
									
				        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
				        				String sFieldCountValue = result.getString(mappings.getTableName()+"SUM"+fields[k].getDbField());
				        				if(sFieldCountValue != null && !"".equals(sFieldCountValue)) 
				        					sFieldCountValue = NumberFormatUtils.formatNumber(sFieldCountValue, 2, 2);
				        				
				        				if(!"".equals(tempSummryVal.trim())) {
				        					smData.put("SUM:"+tempSummryVal.trim(), sFieldCountValue);
				        				} else {
                                			if(StringUtil.isValid(field.getDisplayExportName())) {
                                				smData.put("SUM:"+fields[k].getDisplayExportName(), sFieldCountValue);
                                			} else {
                                				smData.put("SUM:"+fields[k].getDisplayName(), sFieldCountValue);
                                			}
				        				}
				        				
				        			}
									tempSummryVal = request.getParameter(moduleName+":AVG:"+fields[k].getFieldName());
	        				        if(tempSummryVal == null) {
	        				        	tempSummryVal	= (String)request.getAttribute(moduleName+":AVG:"+fields[k].getFieldName());
	        				        }
									
				        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
				        				String sFieldCountValue = result.getString(mappings.getTableName()+"AVG"+fields[k].getDbField());
				        				if(sFieldCountValue != null && !"".equals(sFieldCountValue)) 
				        					sFieldCountValue = NumberFormatUtils.formatNumber(sFieldCountValue, 2, 2);
				        				if(!"".equals(tempSummryVal.trim())) {
				        					smData.put("AVG:"+tempSummryVal.trim(), sFieldCountValue);
				        				} else {
                                			if(StringUtil.isValid(field.getDisplayExportName())) {
                                				smData.put("AVG:"+fields[k].getDisplayExportName(), sFieldCountValue);
                                			} else {
                                				smData.put("AVG:"+fields[k].getDisplayName(), sFieldCountValue);
                                			}
				        				}
				        				
				        			}
									tempSummryVal = request.getParameter(moduleName+":MAX:"+fields[k].getFieldName());
	        				        if(tempSummryVal == null) {
	        				        	tempSummryVal	= (String)request.getAttribute(moduleName+":MAX:"+fields[k].getFieldName());
	        				        }
									
				        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
				        				String sFieldCountValue = result.getString(mappings.getTableName()+"MAX"+fields[k].getDbField());
				        				if(sFieldCountValue != null && !"".equals(sFieldCountValue)) 
				        					sFieldCountValue = NumberFormatUtils.formatNumber(sFieldCountValue, 2, 2);
				        				if(!"".equals(tempSummryVal.trim())) {
				        					smData.put("MAX:"+tempSummryVal.trim(), sFieldCountValue);
				        				} else {
                                			if(StringUtil.isValid(field.getDisplayExportName())) {
                                				smData.put("MAX:"+fields[k].getDisplayExportName(), sFieldCountValue);
                                			} else {
                                				smData.put("MAX:"+fields[k].getDisplayName(), sFieldCountValue);
                                			}
				        				}
				        				
				        			}
									tempSummryVal = request.getParameter(moduleName+":MIN:"+fields[k].getFieldName());
	        				        if(tempSummryVal == null) {
	        				        	tempSummryVal	= (String)request.getAttribute(moduleName+":MIN:"+fields[k].getFieldName());
	        				        }
									
				        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
				        				String sFieldCountValue = result.getString(mappings.getTableName()+"MIN"+fields[k].getDbField());
				        				if(sFieldCountValue != null && !"".equals(sFieldCountValue)) 
				        					sFieldCountValue = NumberFormatUtils.formatNumber(sFieldCountValue, 2, 2);
				        				if(!"".equals(tempSummryVal.trim())) {
				        					smData.put("MIN:"+tempSummryVal.trim(), sFieldCountValue);
				        				} else {
                                			if(StringUtil.isValid(field.getDisplayExportName())) {
                                				smData.put("MIN:"+fields[k].getDisplayExportName(), sFieldCountValue);
                                			} else {
                                				smData.put("MIN:"+fields[k].getDisplayName(), sFieldCountValue);
                                			}
				        				}
				        				
				        			}

									
								}

			        			
			        			//checkGroupBy = false;
		        			

				        	
				        }
			        }
		        } else
                {
			        smData = (SequenceMap) smTableData.get(sPrimaryKeyValue);

			        // increament count processing
                    String [] sArrSelectedField	= null;
                    String sGetSelected	= request.getParameter("getSelectedField");
			        if(sGetSelected == null) {
			        	sGetSelected	= (String)request.getAttribute("getSelectedField");
			        }
                    // get the selected field names if sGetSelected is not null
                    if (sGetSelected != null)
                    {
				        sArrSelectedField = request.getParameterValues(moduleName + count);
				    }
                    SequenceMap sArrTableFields	= mappings.getAllFields();
                    int size = sArrTableFields.size();

                    if (sGetSelected != null )
                    {
                        if (sArrSelectedField == null)
                        {
                            size = 0 ;
                        } else
                        {
                            size = sArrSelectedField.length;
                        }
                    }

                    if (sArrTableFields != null)
                    {
                        for (int k = 0; k < size ; k++)
                        {
                            // Field field = mappings.getField(sArrTableFields[k]);
				        	String tmpFld = "";
				        	String tmpFldLbl = "";

                            field = (Field) sArrTableFields.get(k);

                            if (sGetSelected != null)
                            {
					        	tmpFld = sArrSelectedField[k].split(":")[0];
					        	tmpFldLbl = sArrSelectedField[k].split(":")[1];

					        	field = mappings.getField(tmpFld);
                            }
                            //P_CM_B_37878 :starts
                            if(field==null || !field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
                            	continue;
                            }
                            //P_CM_B_37878 :ends
                            boolean bExportTable = field.isFieldExportable();

                            if (bExportTable)
                            {
                                aliasFieldCount++;
                            }
                        }
                    }
		        }

		        SequenceMap smForeignTables	= null;
		        
                if (bMain)
                    smForeignTables	= selForeignTables;
                else
                    smForeignTables	= mappings.getForeignTablesExportMap();

                if (smForeignTables != null)
                {
                    int sizeForTable = smForeignTables.size();
                  //Problem in Custom Report while do export starts
                    String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
    				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
    				//Problem in Custom Report while do export ends
                    for (int i = 0 ; i < sizeForTable ; i++)
                    {
                        ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
                      //Problem in Custom Report while do export starts
                        if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
    						continue;
    					}
                      //Problem in Custom Report while do export ends
                        if (!smData.containsKey(foreignTable.getName()))
                        {
                            smData.put(foreignTable.getName(), new SequenceMap());
                        }
                        // Modified by Anuj 26-Nov-2004
                      //P_E_Fields-20130905-035
                        collectSearchData(foreignTable.getName(), result, smData, false, dashBoard);
                        if(!processHeaders && dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
                        	collectHeaderSearchData(foreignTable.getName(), result, smData, false, dashBoard,null,"collectSearchData");
                        }
                        //P_E_Fields-20130905-035
                    }
                }
                
                // put the sequencemap for the key into the table map
                smTableData.put(""+sPrimaryKeyValue , smData);
                if(processHeaders){
                	smDataMap.put(headerName+moduleName , smTableData);//P_E_Fields-20130905-035
                }else{
                	smDataMap.put(moduleName , smTableData);
                }
	        }
	    } catch(Exception e)
        {
		    logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->: collectSearchData()" , e);
	    }
    }
    /**
     * Will apply Lock format to fields,call if PII-enabled
     * 
     * @param sFieldValue
     * @param moduleName
     * @param sPrimaryKeyValue
     * @param request2
     * @return sFieldValue in locked format
     */
    private String formatPIIFieldsData(String sFieldValue,String moduleName,Field field,String sPrimaryKeyValue) {
    	StringBuilder returnStr = new StringBuilder(FieldNames.EMPTY_STRING);
    	try
    	{
    		String page = "Custom Reports";
    		if(StringUtil.isValidNew((String)request.getAttribute("fromPage")) && "Export".equals((String)request.getAttribute("fromPage")))
    		{
    			page = "Export";
    		}
		String divId = field.getFieldName()+sPrimaryKeyValue+"_"+moduleName;
		String displayName = field.getDisplayName();
		String forExportData=request.getParameter("forExportData");//Bug 61177
		String pageName = MODULE_NAME.NAME_FIM.equals(menuName)?MODULE_DISPLAY.DISPLAY_FIM:MODULE_NAME.NAME_FS.equals(menuName)?MODULE_DISPLAY.DISPLAY_FS:MODULE_NAME.NAME_CM.equals(menuName)?MODULE_DISPLAY.DISPLAY_FS:"Export";
		if(StringUtil.isValid(sFieldValue) && this.getPiiTablePrivInfo()!=null){
    		//TODO if table privilege are not present and PII-field exist
    		String unlockPrivilege = this.getPiiTablePrivInfo().getString(moduleName,FieldNames.YES);
    		boolean notShowInDiv = FieldNames.NO.equals(request.getParameter("giveUnlockOpt"));//For excel,print etc
    		if(FieldNames.YES.equals(unlockPrivilege)){
				String UnlockedFields	= request.getParameter("UnlockedFields");
				if(UnlockedFields != null && !UnlockedFields.trim().equals("") && !UnlockedFields.trim().equalsIgnoreCase("null") && UnlockedFields.contains(divId))
				{
					if(notShowInDiv){
						returnStr.append(sFieldValue);
					}else{
						String newsFieldValue = AESencrypt.encrypt(sFieldValue);
						newsFieldValue=newsFieldValue.replaceAll("\\+", "@@plus@@");
						newsFieldValue=newsFieldValue.replaceAll("\\/", "@@forwardslash@@");
						newsFieldValue=newsFieldValue.replaceAll("\\\n", "@@newline@@");
						returnStr.append("<div id='").append(divId).append("' position: absolute; display: block; overflow: auto >").append(StringEscapeUtils.escapeHtml(sFieldValue));
						returnStr.append("&nbsp;<a href='javascript:void(0)' onClick =\"javascript:lockedField('").append(divId).append("','locked','").append(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(PortalUtils.getPrivateFieldFormat(sFieldValue)))).append("','");
						returnStr.append(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(newsFieldValue))).append("','").append(pageName).append("','").append(page).append("','").append(sPrimaryKeyValue).append("','view','").append(displayName).append("','").append(moduleName).append("','").append(field.getFieldName()).append("')\"><img src='");			//P_Enh_FC-76
						returnStr.append(NewPortalUtils.getStaticPath(request)).append("/images/PrivateData/openLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a></div>");
					}
				}else{    
					if(notShowInDiv){
						returnStr.append(PortalUtils.getPrivateFieldFormat(sFieldValue));
					}else{
						String newsFieldValue = AESencrypt.encrypt(sFieldValue);
						newsFieldValue=newsFieldValue.replaceAll("\\+", "@@plus@@");
						newsFieldValue=newsFieldValue.replaceAll("\\/", "@@forwardslash@@");
						newsFieldValue=newsFieldValue.replaceAll("\\\n", "@@newline@@");
						returnStr.append("<div id='").append(divId).append("' position: absolute; display: block; overflow: auto >").append(StringEscapeUtils.escapeHtml(PortalUtils.getPrivateFieldFormat(sFieldValue)));
						returnStr.append("&nbsp;<a href='javascript:void(0)' onClick =\"javascript:unLockField('").append(divId).append("','unlocked','").append(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(PortalUtils.getPrivateFieldFormat(sFieldValue)))).append("','");
						returnStr.append(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(newsFieldValue))).append("','").append(pageName).append("','").append(page).append("','").append(sPrimaryKeyValue).append("','view','").append(displayName).append("','").append(moduleName).append("','").append(field.getFieldName()).append("')\"><img src='");				//P_Enh_FC-76
						returnStr.append(NewPortalUtils.getStaticPath(request)).append("/images/PrivateData/goldLock.gif' width='16' align = 'top' height='16'    border = '0'  ></a></div>");
					}
				}
			}else if(FieldNames.NO.equals(unlockPrivilege)){
				returnStr.append(PortalUtils.getPrivateFieldFormat(sFieldValue));
			}
		}else if(StringUtil.isValid(sFieldValue)&&StringUtil.isValidNew(request.getParameter("forExportData"))&&"true".equals(request.getParameter("forExportData"))){//Bug 61177
			String passwordFunctionality = com.appnetix.app.components.accesscontrolmgr.manager.AccessControlMgr.newInstance().getAccessControlDAO().getPasswordFunctionalityValue();
			if("on".equals(passwordFunctionality))
				returnStr.append(PortalUtils.getPrivateFieldFormat(sFieldValue));
			else 
				returnStr.append(sFieldValue);
		}
    	}catch(Exception e)
    	{
    		logger.error("Exception in formatPIIFieldsData() Method :::" +e.getMessage());
    	}
    	return returnStr.toString();
	}

	/**
	This method will call the specified method required for the data conversion.
	Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country
    */
//P_E_FIM_58658 By Nikhil Verma
    /**
     * Changes for form builder respect to transforming those values which are newly added Fields in system through Form Builder
     */
    public  String transform( Field pField, String psValue, String psDependentFieldValue)throws Exception{
    	return transform(pField, psValue, psDependentFieldValue, null);
    }
    /**
     * Override function to allow param field of transform method called.
     * This change to follow new pattern implemented through Form Builder
     * @param pField
     * @param psValue
     * @param psDependentFieldValue
     * @param psDependentFieldValueParam
     * @return
     * @throws Exception
     */
    public  String transform( Field pField,
								String psValue,
								String psDependentFieldValue, String psDependentFieldValueParam 
							)throws Exception{
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
		String sMethodName	= pField.getTransformMethod();
		String sMethodNameParam = pField.getTransformMethodParam();
		if(pField.getTransformExportMethod() != null) {
			sMethodName = pField.getTransformExportMethod();
		}
		if(psDependentFieldValueParam != null) {
			sMethodNameParam = psDependentFieldValueParam;
		}
		if(sMethodName == null && pField.isBuildField()){
			return psValue;
		} else {
			if(!pField.isBuildField()) {
				String dsFldType = pField.getDisplayTypeField(); 
    			if(!dsFldType.equals("Radio") && !dsFldType.equals("Combo") && !dsFldType.equals("Checkbox")) {
    				//BUG_ID-28505 added by varsha gupta for displaying -1 in report starts
    				String validationType=pField.getValidationType();
    				if("Numeric".equals(dsFldType) && "-1".equals(psValue))
    					return "";
    				else 
    					if("Double".equals(validationType)  || "Percentage".equals(validationType))
    						return	NumberFormatUtils.formatNumberField(psValue); //BUG_ID-28505 added ends
    					else if(!StringUtil.isValid(sMethodName)) //BOEFLY_INTEGRATION
    						return psValue;
    			} else {
    				if(!StringUtil.isValid(sMethodName)) 
    					sMethodName = "transformOptionValueForId";
    			}
			}
		}
    	if(!pField.isTransformRequiredInExport()) {
    		return psValue;
    	}
		if(StringUtil.isValid(sMethodName) && pField.getSrcTable() != null) {
			return psValue;
		}

		Class clsArr[] = new Class[]{String.class};
		Object valArr[] = new Object[]{psValue};
		
		/** Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country*/
		if (sMethodNameParam!= null) {
			clsArr = new Class[]{String.class, String.class};
			valArr = new Object[]{psValue, psDependentFieldValue};
		}
  
		// Added By Anuj 27- Niov- 04
		if (sMethodName.equals("transformAddress") || sMethodName.equals("transformDocumentsData") || sMethodName.equals("transformAreaDocumentsData") || sMethodName.equals("transformMuDocumentsData")  )
		{
			clsArr = new Class[]{String.class, String.class};
			valArr = new Object[]{psValue, pField.getFieldName()};
		} else if(sMethodName.equals("transformOptionValueForId")) {
			clsArr = new Class[]{String.class, String.class, String.class};
			valArr = new Object[]{pField.getFieldName(), pField.getTableName(), psValue};
		}

		ExportDataManipulator manipulator = ExportDataManipulator.getInstance();
		Method method = manipulator.getClass().getDeclaredMethod(sMethodName,clsArr);
		String sNewVal		= (String)method.invoke(null,valArr);
		//P_E_FIM_58658 By Nikhil Verma
		if(sNewVal!=null && !"".equals(sNewVal) && sNewVal.indexOf("Not Scheduled")==-1 && sNewVal.indexOf("No Time Scheduled")==-1 && sMethodName!=null && (sMethodName.equals("transformEndDateTimeForTask") || sMethodName.equals("transformDateTimeForTimeLessTask")))
		{
			String userTimeZone=(String)request.getSession().getAttribute("userTimeZone");
			if(!StringUtil.isValid(userTimeZone) && StringUtil.isValid(ExportDataManipulator.userTimeZoneFromRestAPI)){//805_REST_API_CHANGES----START
				sNewVal = TimeZoneUtils.performUTCConversion(_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, ExportDataManipulator.userTimeZoneFromRestAPI, sNewVal, _baseConstants.DISPLAY_FORMAT_HMA, _baseConstants.DISPLAY_FORMAT_HMA);
			}else{//805_REST_API_CHANGES----END
				sNewVal = TimeZoneUtils.performUTCConversion(_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, sNewVal, _baseConstants.DISPLAY_FORMAT_HMA, _baseConstants.DISPLAY_FORMAT_HMA);
			}
		}

		return sNewVal;
}
/**
	This method will call the specified method required for the data conversion.
*/
//P_E_FIM_58658 By Nikhil Verma
    /**
     * Changes for form builder respect to transforming those values which are newly added Fields in system through Form Builder
     */
    public  String transform( Field pField,	String psValue)throws Exception{
    	String sMethodName	= pField.getTransformMethod();
    	if(pField.getTransformExportMethod() != null) {
    		sMethodName = pField.getTransformExportMethod();
    	}
    	if(sMethodName == null && pField.isBuildField()){
    		return psValue;
    	} else {
    		if(!pField.isBuildField()) {
    			String dsFldType = pField.getDisplayTypeField(); 
    			if(!dsFldType.equals("Radio") && !dsFldType.equals("Combo") && !dsFldType.equals("Checkbox")) {
    				//BUG_ID-28505 added by varsha gupta for displaying -1 in report starts
    				String validationType=pField.getValidationType();
    				if("Numeric".equals(dsFldType) && "-1".equals(psValue))
    					return "";
    				else 
    					if("Double".equals(validationType)  || "Percentage".equals(validationType))
    						return	NumberFormatUtils.formatNumberField(psValue); //BUG_ID-28505 added ends
    					else if(!StringUtil.isValid(sMethodName)) //BOEFLY_INTEGRATION
    						return psValue;
    			} else {
    				if(!StringUtil.isValid(sMethodName)) 
    					sMethodName = "transformOptionValueForId";
    			}
    		}
    	}
    	if(!pField.isTransformRequiredInExport()) {
    		return psValue;
    	}
    	if(StringUtil.isValid(sMethodName) && pField.getSrcTable() != null) {
			return psValue;
		}
    	/**
    	 * P_B_Codebase_22112013
    	 * Done Against 32109
    	 * if not a valid value from result . No need to transform
    	 * 
    	 */
    	if(!StringUtil.isValid(psValue)){
    		return psValue;
    	}
    	try {
    		Class clsArr[] = new Class[]{String.class};
    		Object valArr[] = new Object[]{psValue};
    		// Added By Anuj 27- Niov- 04
    		if (sMethodName.equals("transformAddress") || sMethodName.equals("transformDocumentsData") || sMethodName.equals("transformAreaDocumentsData") || sMethodName.equals("transformMuDocumentsData")  )
    		{
    			clsArr = new Class[]{String.class, String.class};
    			valArr = new Object[]{psValue, pField.getFieldName()};
    		} else if(sMethodName.equals("transformOptionValueForId")) {
    			clsArr = new Class[]{String.class, String.class, String.class};
    			valArr = new Object[]{pField.getFieldName(), pField.getTableName(), psValue};
    		}
    		//BBEH_FOR_SMC_OPTIMIZATION STARTS
    		/*else if(sMethodName.equals("transformDate") && !psValue.contains("-"))
    		{
    			 try
    			 {
    				 dateToFormat=sdf.parse(psValue);
    			 }
    			 catch (ParseException e) {
					// TODO: handle exception
				}
    			 sdf.applyPattern(DateUtil.DB_FORMAT);
   	          System.out.println("\n\n\ndateToFormat\n\n\n"+psValue);
   	          valArr=new Object[]{sdf.format(dateToFormat)};
    		}*/
    		//BBEH_FOR_SMC_OPTIMIZATION ENDS
    		ExportDataManipulator manipulator = ExportDataManipulator.getInstance();
    		Method method = manipulator.getClass().getDeclaredMethod(sMethodName,clsArr);
    		String sNewVal		= (String)method.invoke(null,valArr);
    		return sNewVal;
    	} catch(Exception e) {
    		//e.printStackTrace();
        	logger.error("\nException in ExportDataCollector class in transform method :"+e.getMessage());

    		return psValue;
    	}
    }
    
    public  String transform1( Field pField, String psValue)throws Exception{

    	String sMethodName	= pField.getTransformMethod();
    	/**
    	 * No need this code while Input data
    	 * Bug 14471
    	 */
    	
    	if(sMethodName == null && pField.isBuildField()){
    		return psValue;
    	} else {
    		if(!pField.isBuildField()) {
    			String dsFldType = pField.getDisplayTypeField(); 
    			if(!dsFldType.equals("Radio") && !dsFldType.equals("Combo") && !dsFldType.equals("Checkbox")) {
    				//BUG_ID-28505 added by varsha gupta for displaying -1 in report starts
    				String validationType=pField.getValidationType();
    				if("Numeric".equals(dsFldType) && "-1".equals(psValue))
    					return "";
    				else 
    					if("Double".equals(validationType)  || "Percentage".equals(validationType))
    						return	NumberFormatUtils.formatNumberField(psValue);
    					else//BUG_ID-28505 added ends
    				return psValue;
    			} else {
    				if(!StringUtil.isValid(sMethodName)) 
    					sMethodName = "transformOptionValueForId";
    			}
    		}
    	}
    	try {
    		Class clsArr[] = new Class[]{String.class};
    		Object valArr[] = new Object[]{psValue};
    		// Added By Anuj 27- Niov- 04
    		if (sMethodName.equals("transformAddress") || sMethodName.equals("transformDocumentsData") || sMethodName.equals("transformAreaDocumentsData") || sMethodName.equals("transformMuDocumentsData")  )
    		{
    			clsArr = new Class[]{String.class, String.class};
    			valArr = new Object[]{psValue, pField.getFieldName()};
    		} else if(sMethodName.equals("transformOptionValueForId")) {
    			clsArr = new Class[]{String.class, String.class, String.class};
    			valArr = new Object[]{pField.getFieldName(), pField.getTableName(), psValue};
    		}

    		ExportDataManipulator manipulator = ExportDataManipulator.getInstance();
    		Method method = manipulator.getClass().getDeclaredMethod(sMethodName,clsArr);
    		String sNewVal		= (String)method.invoke(null,valArr);

    		return sNewVal;
    	} catch(Exception e) {
        	logger.error("\nException in ExportDataCollector class in transform1 method :"+e.getMessage());

    		return psValue;
    	}
    }
    public  String transform1( Field pField,
    		String psValue,
    		String psDependentFieldValue )throws Exception{
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	String sMethodName	= pField.getTransformMethod();
    	String sMethodNameParam = pField.getTransformMethodParam();
    	/**
    	 * No need this code while Input data
    	 * Bug 14471
    	 */

    	if(sMethodName == null && pField.isBuildField()){
    		return psValue;
    	} else {
    		if(!pField.isBuildField()) {
    			String dsFldType = pField.getDisplayTypeField(); 
    			if(!dsFldType.equals("Radio") && !dsFldType.equals("Combo") && !dsFldType.equals("Checkbox")) {
    				//BUG_ID-28505 added by varsha gupta for displaying -1 in report starts
    				String validationType=pField.getValidationType();
    				if("Numeric".equals(dsFldType) && "-1".equals(psValue))
    					return "";
    				else 
    					if("Double".equals(validationType)  || "Percentage".equals(validationType))
    						return	NumberFormatUtils.formatNumberField(psValue);
    					else//BUG_ID-28505 added ends
    				return psValue;
    			} else {
    				if(!StringUtil.isValid(sMethodName)) 
    					sMethodName = "transformOptionValueForId";
    			}
    		}
    	}

    	Class clsArr[] = new Class[]{String.class};
    	Object valArr[] = new Object[]{psValue};

    	/** Added by Sunilk on 15 Sep 2006 , for formating of phone, dependent on country*/
    	if (sMethodNameParam!= null) {
    		clsArr = new Class[]{String.class, String.class};
    		valArr = new Object[]{psValue, psDependentFieldValue};
    	}

    	// Added By Anuj 27- Niov- 04
    	if (sMethodName.equals("transformAddress") || sMethodName.equals("transformDocumentsData") || sMethodName.equals("transformAreaDocumentsData") || sMethodName.equals("transformMuDocumentsData")  )
    	{
    		clsArr = new Class[]{String.class, String.class};
    		valArr = new Object[]{psValue, pField.getFieldName()};
    	} else if(sMethodName.equals("transformOptionValueForId")) {
    		clsArr = new Class[]{String.class, String.class, String.class};
    		valArr = new Object[]{pField.getFieldName(), pField.getTableName(), psValue};
    	}

    	ExportDataManipulator manipulator = ExportDataManipulator.getInstance();
    	Method method = manipulator.getClass().getDeclaredMethod(sMethodName,clsArr);
    	String sNewVal		= (String)method.invoke(null,valArr);
    	//P_E_FIM_58658 By Nikhil Verma
    	if(sNewVal!=null && !"".equals(sNewVal) && sNewVal.indexOf("Not Scheduled")==-1 && sNewVal.indexOf("No Time Scheduled")==-1 && sMethodName!=null && (sMethodName.equals("transformEndDateTimeForTask") || sMethodName.equals("transformDateTimeForTimeLessTask")))
    	{
    		String userTimeZone=(String)request.getSession().getAttribute("userTimeZone");
    		sNewVal = TimeZoneUtils.performUTCConversion(_baseConstants.DB_TIMEZONE_TIMEZONEUTILS, userTimeZone, sNewVal, _baseConstants.DISPLAY_FORMAT_HMA, _baseConstants.DISPLAY_FORMAT_HMA);
    	}

    	return sNewVal;
    }
    public static String transformFileType( Field pField, String psValue)throws Exception{

		String sMethodName	= pField.getTransformMethod();
    	if(pField.getTransformExportMethod() != null) {
    		sMethodName = pField.getTransformExportMethod();
    	}
	//FS_CUSTOM_COL_ISSUE starts
    	if(StringUtil.isValid(psValue))
    	{
    	psValue=psValue.replaceAll( "<br/>", "\n");
    	}
    	//FS_CUSTOM_COL_ISSUE ends
    	if(sMethodName == null && pField.isBuildField()){
    		return psValue;
    	} else {
    		if(!pField.isBuildField()) {
    			String dsFldType = pField.getDisplayTypeField(); 
    			if(!dsFldType.equals("Radio") && !dsFldType.equals("Combo") && !dsFldType.equals("Checkbox")) {
    				//BUG_ID-28505 added by varsha gupta for displaying -1 in report starts
    				String validationType=pField.getValidationType();
    				if("Numeric".equals(dsFldType) && "-1".equals(psValue))
    					return "";
    				else 
    					if("Double".equals(validationType)  || "Percentage".equals(validationType))
    						return	NumberFormatUtils.formatNumberField(psValue);
    					else if(!StringUtil.isValid(sMethodName))//BUG_ID-28505 added ends //BOEFLY_INTEGRATION
    				return psValue;
    			} else {
    				if(!StringUtil.isValid(sMethodName)) 
    					sMethodName = "transformOptionValueForId";
    			}
    		}
    	}
    	if(!pField.isTransformRequiredInExport()) {
    		return psValue;
    	}
    	if(StringUtil.isValid(sMethodName) && pField.getSrcTable() != null) {
			return psValue;
		}
		Class clsArr[] = new Class[]{String.class};
		Object valArr[] = new Object[]{psValue};
		// Added By Anuj 27- Niov- 04
		if (sMethodName.equals("transformAddress") || sMethodName.equals("transformDocumentsData") || sMethodName.equals("transformAreaDocumentsData") || sMethodName.equals("transformMuDocumentsData")  )
		{
			clsArr = new Class[]{String.class, String.class};
			valArr = new Object[]{psValue, pField.getFieldName()};
		} else if(sMethodName.equals("transformOptionValueForId")) {
    		clsArr = new Class[]{String.class, String.class, String.class};
    		valArr = new Object[]{pField.getFieldName(), pField.getTableName(), psValue};
    	}
		ExportDataManipulator manipulator = ExportDataManipulator.getInstance();
		Method method =null;
		String sNewVal	 ="";
		try{
		method = manipulator.getClass().getDeclaredMethod(sMethodName+"fileType",clsArr);
		sNewVal	= (String)method.invoke(null,valArr);
		}catch(NoSuchMethodException e){

			try{
				method = manipulator.getClass().getDeclaredMethod(sMethodName,clsArr);
				sNewVal	= (String)method.invoke(null,valArr);
			}catch(NoSuchMethodException e1){
				return psValue; //P_B_REST_73044
			}
		}
		//P_FIN_B_28862
		if("xls".equals(exportType)&& StringUtil.isValid(sNewVal)){
			sNewVal=PortalUtils.replaceAll(sNewVal, "<br>", " ");
		}//P_FIN_B_28862
		return sNewVal;
	}

  //P_Enh_Excel_Numeric_Value
	public void  makeHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain) {
		makeHeader(alHeaderList, dataTypeList, sTableName, count, bMain, null);
	}

	public void  makeHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain, ArrayList displayTypeList) //P_Enh_Excel_Numeric_Value
    {
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	    aliasCount = count;
	    FieldMappings mappings;
	    String tableName;
	    //P_E_FS_INTER start
	    boolean isFsMenuWithI18n=false;
	    boolean includeFran = false;
		if (LanguageUtil.isI18nImplemented())
		{
			isFsMenuWithI18n=true;
		}
		//P_E_FS_INTER ends
        // get the FieldMapping object for the module's main table
		mappings = DBUtil.getInstance().getFieldMappings(sTableName);
		//FIM Export failsDKI starts
		UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	    String user_level=(String)request.getSession().getAttribute("user_level");
	    //FIM Export failsDKI ends
		ArrayList sArrSelectedFieldList	= null;
        if (mappings != null)
        {
		    SequenceMap sArrTableFields	= mappings.getAllFields();
            String sGetSelected	= request.getParameter("getSelectedField");
            /*if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(sTableName) && sGetSelected == null) {
    			includeFran = true;
    		}*/
            String [] sArrSelectedField = null;

            // get the selected field names if sGetSelected is not null
		    if (sGetSelected != null)
            {
		    	sArrSelectedFieldList = new ArrayList();
		    	sArrSelectedField = request.getParameterValues(sTableName + aliasCount++);
			    for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
			    	sArrSelectedFieldList.add(sArrSelectedField[i]);
				}
            }

            int size = sArrTableFields.size();

            if (sGetSelected != null)
            {
			    if (sArrSelectedField == null)
                {
					size = 0 ;
				} else
                {
					size = sArrSelectedField.length;
				}
		    }

            if (sArrTableFields != null)
            {
			    for (int k = 0; k < size ; k++)
                {
			    	Field field = (Field) sArrTableFields.get(k);

                    if (sGetSelected != null )
                    {
                    	
                    	if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 ||sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
    						continue;
    					}
                    	if(("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))) && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
                    		includeFran = true;
                    		continue;
    					}
					    field = mappings.getField(sArrSelectedField[k]);
				    }
                    if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
                    {
                    	continue;
                    }

				    boolean bExportTable = field.isFieldExportable();
				    boolean sortable = field.isSortable();
				    Info customFieldInfo = null;
				    tableName = mappings.getTableName();				    
				    //P_FIM_E_CUSTOM_TAB
				    Info customTabFieldInfo = null;
				    /**
				     * put the field in the query only if its exportable as specific in the xml
				     */
                    if (bExportTable)
                    {
                    	String exDisplayName = "";
                    	if(StringUtil.isValid(field.getDisplayExportName())) {
                    		exDisplayName = field.getDisplayExportName();
                    	} else {
                    		exDisplayName = field.getDisplayName();
                    	}
                    	if("fim".equals(menuName) && MultiTenancyUtil.getTenantConstants().ENABLE_IN_DEVELOPMENT  && "FRANCHISEE".equals(mappings.getTableName()) && "openingDate".equals(field.getFieldName())) {
                    		exDisplayName = "Expected Opening / "+exDisplayName;
                        }
                    	if("fim".equals(menuName) && ("isStore".equals(field.getFieldName()) || "isFranchisee".equals(field.getFieldName()))){
                    		continue;
                    	}
                    	//P_B_53670 starts
            			if ("cm".equals(menuName) && "taskType".equals(field.getFieldName()) && "tasksExport".equals(sTableName)){
            				continue;
            			}
            			//P_B_57374 ends
            			//FIM Export failsDKI starts
            			if("fbc".equals(field.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
							continue;
						}
            			//FIM Export failsDKI ends
                    	if((!ModuleUtil.zcubatorImplemented() || !"Y".equals(_baseConstants.CM_PILOT_ENABLED)) && exDisplayName.contains("Activity / Campaign Code") && ("cm".equals(menuName))){//P_CM_B_26948
                    		continue;
                    	}
                    	if(exDisplayName.contains("How did you hear about us")){
                    		if(TableAnchors.CM_LEAD_DETAILS.equals(sTableName)){
                    			exDisplayName="How did you hear about us ? (Lead Source)";
                    		}else{
                    			exDisplayName="How did you hear about us ? (Contact Source)"; //ZCUB-20150417-141
                    		}
    					}else if(exDisplayName.contains("Please Specify")){
    						if(TableAnchors.CM_LEAD_DETAILS.equals(sTableName)){
    							exDisplayName="Please Specify (Lead Source Details)"; 
    						}else{
    							exDisplayName="Please Specify (Contact Source Details)"; //ZCUB-20150417-141
    						}
    					}
                    	boolean sCustomField = field.isCustomField();

                        if (sCustomField == true)
                        {
                            CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
                            
                            customFieldInfo = customWebImpl.getCustomFieldsForExport(menuName, mappings.getTableName(), field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1),(String)request.getSession().getAttribute("fimFlag"));

                            if (customFieldInfo == null)
                            {
                                continue;
                            }
                        }
                        if(!field.isActiveField()) {
                        	continue;
                        }
                        
                        //START : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
						/*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(sTableName) || "cmContactStatusChangeInfoExport".equals(sTableName)))
						{
							if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
								continue;
							}
						}*/
						//END : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
                        
                        //P_FIM_E_CUSTOM_TAB
                        if (sTableName.equals("fimCustomTab"))
                        {
                            CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
                            customTabFieldInfo = customWebImpl.getCustomTabFieldsForExport(menuName, field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1));

                            if (customTabFieldInfo == null)
                            {
                                continue;
                            }
                        }
//						P_FIM_E_CUSTOM_TAB ends
                        if(isFsMenuWithI18n) {
                        	exDisplayName = LanguageUtil.getString(exDisplayName,request);
                        }
                        /*if("fim".equals(menuName) && "reportPeriodStartDate".equals(field.getFieldName()) && !ModuleUtil.financialsImplemented())
    						continue;*/
                    	//if(!field.isBuildField()) {
                        String sDisType= field.getDisplayTypeField();
                        if(sDisType != null && sDisType.equals("Numeric")){	
                    		if(field.getValidationType() != null && "Double".equals(field.getValidationType())) {
                    			exDisplayName = exDisplayName + "(" + _baseConstants.USER_CURRENCY+ ")";
							} else if(field.getValidationType() != null && "Percentage".equals(field.getValidationType())) {
								exDisplayName = exDisplayName + "(" + Constants.LBL_PERCENTAGE + ")";
							} 
                    	}
                        
                       /* if("1".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
	    					if("brandMapping".equals(sTableName) || "fimBrandMapping".equals(sTableName) || "fsLeadDivisionMapping".equals(sTableName) || "locationDivisionMapping".equals(sTableName)) {
	    						exDisplayName = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL + " Name";
	    					}
	    				} else {
	    					if(("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON))) {
	    						if("brandMapping".equals(sTableName) || "fimBrandMapping".equals(sTableName) || "fsLeadDivisionMapping".equals(sTableName) || "locationDivisionMapping".equals(sTableName)) {
	    							String brandLabel = "Brand";
	    							if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) {
	    								brandLabel = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL;
	    							}
	    							exDisplayName = brandLabel + " Name";
	    						}
	    					}
	    				}*/
                        
                        if("1".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
        					if("brandMapping".equals(sTableName) || "fimBrandMapping".equals(sTableName) || "fsLeadDivisionMapping".equals(sTableName) || "locationDivisionMapping".equals(sTableName)) {
        						exDisplayName = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL + " Name";
        					}
        				} else{
        					if("brandMapping".equals(sTableName) || "fimBrandMapping".equals(sTableName)) 
        					{
        						if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) || "0".equals(MultiTenancyUtil.getTenantConstants().IS_FDD_BRAND_CONFIGURED)) {
        							String brandLabel = "Brand";
        							if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "B".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON))  {
        								brandLabel = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL;
        							}
        							exDisplayName = brandLabel + " Name";
        						}
        					}else if("fsLeadDivisionMapping".equals(sTableName) || "locationDivisionMapping".equals(sTableName))
        					{
        						if("Y".equals(MultiTenancyUtil.getTenantConstants().IS_DIVISION_CONFIGURED) && "G".equals(MultiTenancyUtil.getTenantConstants().DIVISION_BASED_ON)) {
        							exDisplayName = MultiTenancyUtil.getTenantConstants().DIVISION_LABEL + " Name";
        						}
        					}
        				}
                        if (sortable && (request.getParameter("fileType") == null || request.getParameter("fileType").equals("") ||  request.getParameter("fileType").equalsIgnoreCase("view")))
                        {
                        	StringBuffer link;
                        	link = new StringBuffer();

                        	String sortCol;
                        	sortCol = request.getParameter("sortCol");

                        	String sortOrder;
                        	sortOrder = request.getParameter("sortOrder");

                        	// P_FS_B_36928
                        	if (sortCol != null && sortCol.equals(field.getSortColumn()))
                        	{
	                        String themeName =(String)request.getSession().getAttribute("userTheme");	
                                    
                                    if (sortOrder != null && sortOrder.equals("ASC"))
	                        	{
	                        		sortOrder = "DESC";
	                        	} else 
	                        	{
	                        		sortOrder = "ASC";
	                        	}

	                        	// javascript:void(0) creates problem in case of ie. It don't allow javascript to 
	                        	// change the location of page or submit a form.
	                        	// Alias is required in sorting as fields having same name can be in two tables. 
	                        	if (sortOrder != null && sortOrder.equals("ASC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{

	                        			link.append("<a href='#' onclick='sortBy(\"").append(field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("").append("<img src='").append(request.getContextPath()).append("/static"+Constants.STATIC_KEY+"/images/theme/"+themeName+"/descending.gif' border='0'></a>");
	                        	} else if (sortOrder != null && sortOrder.equals("DESC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{

	                        			link.append("<a href='#' onclick='sortBy(\"").append(field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("").append("<img src='").append(request.getContextPath()).append("/static"+Constants.STATIC_KEY+"/images/theme/"+themeName+"/ascending.gif' border='0'></a>");
	                        	} 
                        	} else
                        	{

                        			link.append("<a href='#' onclick='sortBy(\"").append(field.getSortColumn()).append("\", \"ASC\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("</a>");
                        	}

                        	alHeaderList.add(link.toString());
                        } else
                        {
                        	//P_E_EXPORT_CURRENCY start
                        	
                        	
                        	//P_E_EXPORT_CURRENCY ends
                        	{
                        		//P_FIM_E_CUSTOM_TAB
                        		if(customTabFieldInfo!=null){
                                    alHeaderList.add(customTabFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME));
                        }else{
                        	
                        			alHeaderList.add(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME));
                        }
                        	//P_FIM_E_CUSTOM_TAB ends
                        		//P_FS_E_EXPORT_DATE_NUM
                         		if(customFieldInfo==null)
                        		{
                        				dataTypeList.add(field.getDataType());
                        		}
                        		else
                        		{
                         				dataTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                        		}
                        		//P_FS_E_EXPORT_DATE_NUM end
                         		//P_FIM_E_CUSTOM_TAB 
                         		if(customTabFieldInfo!=null){
                                     dataTypeList.add(customTabFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                             }
                         		
                         		if(displayTypeList != null) { //P_Enh_Excel_Numeric_Value starts
									if(customFieldInfo == null) {
										displayTypeList.add(field.getDisplayTypeField());
									} else {
										displayTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
									}
								} //P_Enh_Excel_Numeric_Value ends
                         		
                        	}
                        }
                    }
			    }
			    
		    }
	    }
        HttpSession session = StrutsUtil.getHttpSession();
        if (bMain && "fim".equals(menuName) && ("3".equals(session.getAttribute("fimFlag")))) {
        if(bMain && PortalUtils.hasDependentAddressTable(sTableName,request)){
			FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(sTableName);
			HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
			DependentTable dTable = null;
			for(HeaderMap h:headerMapArr) {
				HeaderField hFld = h.getHeaderFields();
				DependentTable[] dependenTables = hFld.getDependentTables();
				if(dependenTables!=null && dependenTables.length>0){
					int dependantTableSize = dependenTables.length;
					for(int ds=0;ds<dependantTableSize;ds++){
						dTable =  dependenTables[ds];
						if(dTable!=null && "address".equals(dTable.getTableAnchor())){
							aliasCount = aliasCount+1;
						}
					}
				}
			}
		}
        }
        HeaderMap[] hMap = mappings.getHeaderMap();
   		for(HeaderMap h:hMap) {
   			
   			DocumentMap[] docMaps = null;
   			HeaderField hFld = h.getHeaderFields();
   			Boolean isTabularSection=hFld.isTabularSection();
   			if(isTabularSection){
   				continue;
   			}
			Documents[] docs =  hFld.getDocuments();
			
			
			if(docs != null && docs.length > 0) {
					docMaps = docs[0].getDocumentMaps();
					if(docMaps != null && docMaps.length > 0) {
						for(DocumentMap dMap : docMaps) {
							SequenceMap docMap  = dMap.getDocumentFieldMap();
							String docOption  = (String)docMap.get("doc-option");
							if(docOption == null) {
								docOption = "0";
							}
							String documentTitle = (String)docMap.get("title-display");
							String documentDis = (String)docMap.get("doc-display");
							if(sArrSelectedFieldList !=null) {
								if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
									if(docOption.equals("0") ) {
										alHeaderList.add(documentTitle);
										dataTypeList.add("String");
										if(displayTypeList!=null)
										displayTypeList.add("String");
									} else {
										alHeaderList.add(documentDis);
										dataTypeList.add("String");
										if(displayTypeList!=null)
										displayTypeList.add("String");
									}
								}
							} else {

								if(docOption.equals("0") ) {
									alHeaderList.add(documentTitle);
									dataTypeList.add("String");
									if(displayTypeList!=null)
									displayTypeList.add("String");
								} else {
									alHeaderList.add(documentDis);
									dataTypeList.add("String");
									if(displayTypeList!=null)
									displayTypeList.add("String");
								}
							}
						}
					}
			}
   		}
        
        
	    // foreignTable processing
	    // Modified by Anuj 26-Nov-2004
	    // SequenceMap smForeignTables = mappings.getForeignTablesMap();
	    SequenceMap smForeignTables;
        if (bMain)
            smForeignTables	= selForeignTables;
        else
        	smForeignTables	= mappings.getForeignTablesExportMap();//smForeignTables	= mappings.getForeignTablesMap();

        if(includeFran ) {
        	alHeaderList.add("Franchisee Name");
        	dataTypeList.add("String");
        	if(displayTypeList!=null)
				displayTypeList.add("String");
        }
	    if (smForeignTables != null)
        {
            int sizeForTable = smForeignTables.size();
          //Problem in Custom Report while do export starts
            String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
            for (int i = 0 ; i < sizeForTable ; i++)
            {
                ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
              //Problem in Custom Report while do export starts
                if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
              //Problem in Custom Report while do export ends
                if("fimEntityDetail".equals(sTableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts
					if("address".equals(foreignTable.getName())){
						continue;
					}
					}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends
               	makeHeader(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount++, false,displayTypeList);
              //P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									aliasCount = aliasCount+1;
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
            }
	    }
    }
    
    
	public void  makeMuEntityFranHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count) {
		makeMuEntityFranHeader(alHeaderList, dataTypeList, sTableName, count, null);
	}
	
	public void  makeMuEntityFranHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, ArrayList displayTypeList) //P_Enh_Excel_Numeric_Value
    {
    	BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
	    aliasCount = count;
	    FieldMappings mappings;
	    String tableName;
	    //P_E_FS_INTER start
	    boolean isFsMenuWithI18n=false;
	    boolean includeFran = false;
		if (LanguageUtil.isI18nImplemented())
		{
			isFsMenuWithI18n=true;
		}
		//P_E_FS_INTER ends
        // get the FieldMapping object for the module's main table
		mappings = DBUtil.getInstance().getFieldMappings(sTableName);
		ArrayList sArrSelectedFieldList	= null;
        if (mappings != null)
        {
		    SequenceMap sArrTableFields	= mappings.getAllFields();
            String sGetSelected	= request.getParameter("getSelectedField");
            if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(sTableName) && sGetSelected == null) {
    			includeFran = true;
    		}
            String [] sArrSelectedField = null;

            // get the selected field names if sGetSelected is not null
		    if (sGetSelected != null)
            {
		    	sArrSelectedFieldList = new ArrayList();
		    	
		    	sArrSelectedField = request.getParameterValues(sTableName + aliasCount++);
			    for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
			    	sArrSelectedFieldList.add(sArrSelectedField[i]);
				}
            }

            int size = sArrTableFields.size();

            if (sGetSelected != null)
            {
			    if (sArrSelectedField == null)
                {
					size = 0 ;
				} else
                {
					size = sArrSelectedField.length;
				}
		    }

            if (sArrTableFields != null)
            {
			    for (int k = 0; k < size ; k++)
                {
			    	Field field = (Field) sArrTableFields.get(k);

                    if (sGetSelected != null )
                    {
                    	
                    	if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
    						continue;
    					}
                    	if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) && "multiUnitOwnerExport".equals(sTableName) && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
                    		includeFran = true;
                    		continue;
    					}
					    field = mappings.getField(sArrSelectedField[k]);
				    }
                    if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
                    {
                    	continue;
                    }

				    boolean bExportTable = field.isFieldExportable();
				    boolean sortable = field.isSortable();
				    Info customFieldInfo = null;
				    tableName = mappings.getTableName();				    
				    //P_FIM_E_CUSTOM_TAB
				    Info customTabFieldInfo = null;
				    /**
				     * put the field in the query only if its exportable as specific in the xml
				     */
                    if (bExportTable)
                    {
                    	String exDisplayName = "";
                    	if(StringUtil.isValid(field.getDisplayExportName())) {
                    		exDisplayName = field.getDisplayExportName();
                    	} else {
                    		exDisplayName = field.getDisplayName();
                    	}
                    	if("fim".equals(menuName) && MultiTenancyUtil.getTenantConstants().ENABLE_IN_DEVELOPMENT  && "FRANCHISEE".equals(mappings.getTableName()) && "openingDate".equals(field.getFieldName())) {
                    		exDisplayName = "Expected Opening / "+exDisplayName;
                        }
                    	if("fim".equals(menuName) && ("isStore".equals(field.getFieldName()) || "isFranchisee".equals(field.getFieldName()))){
                    		continue;
                    	}
                    	//P_B_53670 starts
            			if ("cm".equals(menuName) && "taskType".equals(field.getFieldName()) && "tasksExport".equals(sTableName)){
            				continue;
            			}
            			//P_B_57374 ends
                    	if((!ModuleUtil.zcubatorImplemented() || !"Y".equals(_baseConstants.CM_PILOT_ENABLED)) && exDisplayName.contains("Activity / Campaign Code") && ("cm".equals(menuName))){//P_CM_B_26948
                    		continue;
                    	}
                    	if(exDisplayName.contains("How did you hear about us")){
    						exDisplayName="How did you hear about us ? (Contact Source)"; //ZCUB-20150417-141
    					}else if(exDisplayName.contains("Please Specify")){
    						exDisplayName="Please Specify (Contact Source Details)"; //ZCUB-20150417-141
    					}
                    	boolean sCustomField = field.isCustomField();

                        if (sCustomField == true)
                        {
                            CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
                            
                            customFieldInfo = customWebImpl.getCustomFieldsForExport(menuName, mappings.getTableName(), field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1),(String)request.getSession().getAttribute("fimFlag"));

                            if (customFieldInfo == null)
                            {
                                continue;
                            }
                        }
                        if(!field.isActiveField()) {
                        	continue;
                        }
                        
                        //START : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
						/*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(sTableName) || "cmContactStatusChangeInfoExport".equals(sTableName)))
						{
							if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
								continue;
							}
						}*/
						//END : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
                        
                        //P_FIM_E_CUSTOM_TAB
                        if (sTableName.equals("fimCustomTab"))
                        {
                            CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
                            customTabFieldInfo = customWebImpl.getCustomTabFieldsForExport(menuName, field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1));

                            if (customTabFieldInfo == null)
                            {
                                continue;
                            }
                        }
//						P_FIM_E_CUSTOM_TAB ends
                        if(isFsMenuWithI18n) {
                        	exDisplayName = LanguageUtil.getString(exDisplayName,request);
                        }
                        String sDisType= field.getDisplayTypeField();
                        if(sDisType != null && sDisType.equals("Numeric")){	
                    		if(field.getValidationType() != null && "Double".equals(field.getValidationType())) {
                    			exDisplayName = exDisplayName + "(" + _baseConstants.USER_CURRENCY+ ")";
							} else if(field.getValidationType() != null && "Percentage".equals(field.getValidationType())) {
								exDisplayName = exDisplayName + "(" + Constants.LBL_PERCENTAGE + ")";
							} 
                    	}
                        
                        if (sortable && (request.getParameter("fileType") == null || request.getParameter("fileType").equals("") ||  request.getParameter("fileType").equalsIgnoreCase("view")))
                        {
                        	StringBuffer link;
                        	link = new StringBuffer();

                        	String sortCol;
                        	sortCol = request.getParameter("muSortCol");

                        	String sortOrder;
                        	sortOrder = request.getParameter("muSortOrder");
                        	if (sortCol != null && sortCol.equals(field.getSortColumn()))
                        	{
	                        String themeName =(String)request.getSession().getAttribute("userTheme");	
                                    
                                    if (sortOrder != null && sortOrder.equals("ASC"))
	                        	{
	                        		sortOrder = "DESC";
	                        	} else 
	                        	{
	                        		sortOrder = "ASC";
	                        	}

	                        	// javascript:void(0) creates problem in case of ie. It don't allow javascript to 
	                        	// change the location of page or submit a form.
	                        	// Alias is required in sorting as fields having same name can be in two tables. 
	                        	if (sortOrder != null && sortOrder.equals("ASC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{

	                        			link.append("<a href='#' onclick='muSortBy(\"").append(field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("").append("<img src='").append(request.getContextPath()).append("/static"+Constants.STATIC_KEY+"/images/theme/"+themeName+"/descending.gif' border='0'></a>");
	                        	} else if (sortOrder != null && sortOrder.equals("DESC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{
	                        			link.append("<a href='#' onclick='muSortBy(\"").append(field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("").append("<img src='").append(request.getContextPath()).append("/static"+Constants.STATIC_KEY+"/images/theme/"+themeName+"/ascending.gif' border='0'></a>");
	                        	} 
                        	} else
                        	{
                        			link.append("<a href='#' onclick='muSortBy(\"").append(field.getSortColumn()).append("\", \"ASC\")'>").append(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME)).append("</a>");
                        	}

                        	alHeaderList.add(link.toString());
                        } else
                        {
                        	//P_E_EXPORT_CURRENCY start
                        	
                        	
                        	//P_E_EXPORT_CURRENCY ends
                        	{
                        		//P_FIM_E_CUSTOM_TAB
                        		if(customTabFieldInfo!=null){
                                    alHeaderList.add(customTabFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME));
                        }else{
                        	
                        			alHeaderList.add(customFieldInfo==null?exDisplayName:customFieldInfo.getString(CustomFormFieldNames.DISPLAY_NAME));
                        }
                        	//P_FIM_E_CUSTOM_TAB ends
                        		//P_FS_E_EXPORT_DATE_NUM
                         		if(customFieldInfo==null)
                        		{
                        				dataTypeList.add(field.getDataType());
                        		}
                        		else
                        		{
                         				dataTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                        		}
                        		//P_FS_E_EXPORT_DATE_NUM end
                         		//P_FIM_E_CUSTOM_TAB 
                         		if(customTabFieldInfo!=null){
                                     dataTypeList.add(customTabFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                             }
                         		
                         		//P_Enh_Excel_Numeric_Value starts
								if(displayTypeList != null) {
									if(customFieldInfo == null) {
										displayTypeList.add(field.getDisplayTypeField());
									} else {
										displayTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
									}
								}
								//P_Enh_Excel_Numeric_Value ends
                         		
                        	}
                        }
                    }
			    }
			    
		    }
	    }

        
        HeaderMap[] hMap = mappings.getHeaderMap();
   		for(HeaderMap h:hMap) {
   			
   			DocumentMap[] docMaps = null;
   			HeaderField hFld = h.getHeaderFields();
   			Boolean isTabularSection=hFld.isTabularSection();
   			if(isTabularSection){
   				continue;
   			}
			Documents[] docs =  hFld.getDocuments();
			
			
			if(docs != null && docs.length > 0) {
					docMaps = docs[0].getDocumentMaps();
					if(docMaps != null && docMaps.length > 0) {
						for(DocumentMap dMap : docMaps) {
							SequenceMap docMap  = dMap.getDocumentFieldMap();
							String docOption  = (String)docMap.get("doc-option");
							if(docOption == null) {
								docOption = "0";
							}
							String documentTitle = (String)docMap.get("title-display");
							String documentDis = (String)docMap.get("doc-display");
							if(sArrSelectedFieldList !=null) {
								if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
									if(docOption.equals("0") ) {
										alHeaderList.add(documentTitle);
										dataTypeList.add("String");
										if(displayTypeList!=null)
											displayTypeList.add("String");
									} else {
										alHeaderList.add(documentDis);
										dataTypeList.add("String");
										if(displayTypeList!=null)
											displayTypeList.add("String");
									}
								}
							} else {

								if(docOption.equals("0") ) {
									alHeaderList.add(documentTitle);
									dataTypeList.add("String");
									if(displayTypeList!=null)
										displayTypeList.add("String");
								} else {
									alHeaderList.add(documentDis);
									dataTypeList.add("String");
									if(displayTypeList!=null)
									displayTypeList.add("String");
								}
							}
						}
					}
			}
   		}
   		
   		
   		SequenceMap smForeignTables;
        
        	smForeignTables	= mappings.getForeignTablesExportMap();//smForeignTables	= mappings.getForeignTablesMap();

        
	    if (smForeignTables != null)
        {
            int sizeForTable = smForeignTables.size();
          //Problem in Custom Report while do export starts
            String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
            for (int i = 0 ; i < sizeForTable ; i++)
            {
                ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
              //Problem in Custom Report while do export starts
                if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
              //Problem in Custom Report while do export ends
                makeMuEntityFranHeader(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount++,displayTypeList);
              //P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									aliasCount = aliasCount+1;
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
            }
	    }
        
    }
    
    
	public  void  makeFranHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName) {
		makeFranHeader(alHeaderList, dataTypeList, sTableName, null);
	}
	
	public  void  makeFranHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, ArrayList displayTypeList) { //P_Enh_Excel_Numeric_Value
    
    	HttpSession session = StrutsUtil.getHttpSession();
    	SequenceMap smForeignTables;
    	String sGetSelected	= request.getParameter("getSelectedField");
    	String[] sArrSelectedField = null;
            smForeignTables	= selFranTables;
            boolean isFranFieldSelected = false;
			//int tableAliasCount = aliasCount;
			if (smForeignTables != null) {
			ArrayList<String> franTableList = (ArrayList<String>) smForeignTables.keys();
			// get the selected field names if sGetSelected is not null
			/*if (sGetSelected != null)
			{
				for(String franTab:franTableList) {
					
					if(request.getParameterValues(franTab + tableAliasCount)!=null) {
						isFranFieldSelected = true;
						break;
					}
					tableAliasCount++;
				}
			} else {
				isFranFieldSelected = true;
			}*/
    }
	    if (smForeignTables != null )
        {
	    	alHeaderList.add(LanguageUtil.getString("Franchisee Name"));
	    	dataTypeList.add("String");
	    	if("fim".equals(menuName) && "2".equals((String) session.getAttribute("fimFlag"))) {
	    		alHeaderList.add(LanguageUtil.getString("Owner Title"));
	    		dataTypeList.add("String");
				if(displayTypeList != null) { //for OnwerTitle //P_Enh_Excel_Numeric_Value
					displayTypeList.add("String");
				}
	    		alHeaderList.add(LanguageUtil.getString("First Name"));
	    		dataTypeList.add("String");
	    		alHeaderList.add(LanguageUtil.getString("Last Name"));
	    		dataTypeList.add("String");
				if(displayTypeList != null) { //for First Name //P_Enh_Excel_Numeric_Value
					displayTypeList.add("String");
				}
				if(displayTypeList != null) { //for Last //P_Enh_Excel_Numeric_Value
					displayTypeList.add("String");
				}
	    	}else if("fim".equals(menuName) && "3".equals((String) session.getAttribute("fimFlag"))) {
	    		alHeaderList.add(LanguageUtil.getString("Entity Name"));
	    		dataTypeList.add("String");
	    	}
            int sizeForTable = smForeignTables.size();
           
           // int franTableCount = aliasCount;
            for (int i = 0 ; i < sizeForTable ; i++)
            {
                ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
                //makeMuEntityFranHeader(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount++);
				makeMuEntityFranHeader(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount++, displayTypeList); //P_Enh_Excel_Numeric_Value

              //P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									aliasCount = aliasCount+1;
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
            }
	    }
	    
    }	
    
    
    
    /**
     * @author abhishek gupta
     * @date 26 Aug 2009
     * @param alHeaderList
     * @param dataTypeList
     * @param sTableName
     * @param count
     * @param bMain
     */
    public void  makeNewHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain) {
    	makeNewHeader(alHeaderList, dataTypeList, sTableName, count, bMain, null);
    }
    public void  makeNewHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain, String dashBoard){
		makeNewHeader(alHeaderList, dataTypeList, sTableName, count, bMain, dashBoard,null,null,FieldNames.EMPTY_STRING);
	}
	//P_E_Fields-20130905-035 changed params
	public void  makeNewHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName) {
		makeNewHeader(alHeaderList, dataTypeList, sTableName, count, bMain, dashBoard, hFld, parentMappings, headerName, null);
	}
	public void  makeNewHeader(ArrayList alHeaderList, ArrayList dataTypeList, String sTableName, int count, boolean bMain, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName, ArrayList displayTypeList) //P_Enh_Excel_Numeric_Value
	{
		BaseConstants _baseConstants=	MultiTenancyUtil.getTenantConstants();
    	logger.info("aliasCount when creating header === "+aliasCount);
    	logger.info("count when creating header === "+count);
	    aliasCount = count;
	    FieldMappings mappings;
	    String tableName;
	    boolean processHeaders = false;//P_E_Fields-20130905-035
	    boolean isFsMenuWithI18n=false;
	    if(dashBoard != null) {
			isFsMenuWithI18n=true;
	    } else {
			if (LanguageUtil.isI18nImplemented())
			{
				isFsMenuWithI18n=true;
			}
	    }
        // get the FieldMapping object for the module's main table
	    mappings = DBUtil.getInstance().getFieldMappings(sTableName);
	    String sortColTable=BaseFieldNames.EMPTY_STRING;//CUSTOM_REPORT_SORTING_ISSUE
        if (mappings != null)
        {
		    SequenceMap sArrTableFields	= null;//mappings.getAllFields();
		    if(processHeaders){
        		sArrTableFields = mappings.getDependentTblFields(hFld);
        	}else{
        		sArrTableFields = mappings.getAllFields();
        	}
		    String sGetSelected	= request.getParameter("getSelectedField");
            if(sGetSelected == null) {
            	sGetSelected	= (String)request.getAttribute("getSelectedField");
            }
            String [] sArrSelectedField = null;
            // get the selected field names if sGetSelected is not null
		    if (sGetSelected != null)
            {  
		    	if(dashBoard != null) {
                   	sArrSelectedField = (String [])request.getAttribute(sTableName + aliasCount++);
		    	}else if(processHeaders){ 
		    		count++;//P_E_Fields-20130905-035
		    		sArrSelectedField = request.getParameterValues(sTableName + aliasCount++);//P_E_Fields-20130905-035
		    	}else {
		    		sArrSelectedField = request.getParameterValues(sTableName + aliasCount++);
		    	}
		    }
            int size = sArrTableFields.size();

            if (sGetSelected != null)
            {
			    if (sArrSelectedField == null)
                {
					size = 0 ;
				} else
                {
					size = sArrSelectedField.length;
				}
		    }
            if (sArrTableFields != null)
            {
            	boolean checkGroupBy = false;
            	//P_Enh_CustomReport_Multi_Sorting starts
            	ArrayList<String> sortColArrayList = new ArrayList<String>();
            	int sortColumnOrder = 0;
            	String altText = LanguageUtil.getString("Sort Order");
            	if(StringUtil.isValid(request.getParameter("crSortBy"))) {
            		String temp = request.getParameter("crSortBy");
            		String tempArr[] = temp.split(",");
            		for(String temp1 : tempArr) {
            			if(StringUtil.isValid(temp1)){
                			if(temp1.indexOf("#####")!=-1) {
                				sortColArrayList.add(temp1.split("#####")[1]);
                			} else {
                				sortColArrayList.add(temp1);
                			}
            			}
            		}
            	}
            	String[] tempSortOrderArr=null;
            	String[] tempSortByArr=null;
            	if(StringUtil.isValid(request.getParameter("crSortBy"))) {
            		//if(request.getParameter("crOrderBy").indexOf(",")!=-1) {
            			tempSortOrderArr=(request.getParameter("crOrderBy")).split(",");
            		//}
            		//if(request.getParameter("crSortBy").indexOf(",")!=-1) {
            			tempSortByArr=(request.getParameter("crSortBy")).split(",");
            		//}
            	}
            	//P_Enh_CustomReport_Multi_Sorting ends
			    for (int k = 0; k < size ; k++)
                {
			    	if(sArrTableFields.get(k) == null) {
			    		continue;
			    	}
			    	Field field = (Field) sArrTableFields.get(k);
			    	String tempVal = "";
				    String tempVal1 = "";
                    if (sGetSelected != null )
                    {
                        tempVal = sArrSelectedField[k].split(":")[0];
                    	tempVal1 = sArrSelectedField[k].split(":")[1];
                    	/*if("fim".equals(menuName) && MultiTenancyUtil.getTenantConstants().ENABLE_IN_DEVELOPMENT && "openingDate".equals(tempVal)) {
                    		tempVal1 = "Expected / "+tempVal1;
                    	}*/
                    	if(processHeaders){
                    		field = parentMappings.getOtherTableField(tempVal);//P_E_Fields-20130905-035
                    	}else{
                    		field = mappings.getField(tempVal);
                    	}
                    	if(field==null || !field.isActiveField())		//P_BUGS_FIM Points starts
	                    {
							continue;
	                    }		//P_BUGS_FIM Points ends
				    }
                    if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
                    {
                    	continue;
                    }

                    boolean bExportTable = false;
                    if(field != null) {
                    	bExportTable = field.isFieldExportable();
                    }
				    
				    boolean sortable = field.isSortable();
				    Info customFieldInfo = null;
				    tableName = mappings.getTableName();				    

				    /**
				     * put the field in the query only if its exportable as specific in the xml
				     */
                    if (bExportTable)
                    {
                    	checkGroupBy = true;
                        boolean sCustomField = field.isCustomField();

                        if (sCustomField == true)
                        {
                            CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
                            
                            customFieldInfo = customWebImpl.getCustomFieldsForExport(menuName, mappings.getTableName(), field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1),(String)request.getSession().getAttribute("fimFlag"));
                            if (customFieldInfo != null)
    						{
    							String sSearchField = customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE);

    							if ("Text".equals(sSearchField) || "TextArea".equals(sSearchField))
    								sSearchField = "text";
    							else if ("Date".equals(sSearchField))
    								sSearchField = "dateRange";

    						}
                            
                            if (customFieldInfo == null)
                            {
                                continue;
                            }
                        }
                        if(isFsMenuWithI18n) {
                        	System.out.println("\n\n====tempVal1==========11="+tempVal1);
                        	tempVal1 = LanguageUtil.getString(tempVal1,request);
                        	System.out.println("\n\n====tempVal1========222==="+tempVal1);
                        }
//                        if(!field.isBuildField()) {
                        String sDisType= field.getDisplayTypeField();
                        if(sDisType != null && sDisType.equals("Numeric")){	
                    		if(field.getValidationType() != null && "Double".equals(field.getValidationType())) {
                    			tempVal1 = tempVal1 + "(" + _baseConstants.USER_CURRENCY+ ")";
        					} else if(field.getValidationType() != null && "Percentage".equals(field.getValidationType())) {
        						tempVal1 = tempVal1 + "(" + Constants.LBL_PERCENTAGE + ")";
        					} 
                    	}
                        //CUSTOM_REPORT_SORTING_ISSUE starts
                        sortColTable=BaseFieldNames.EMPTY_STRING;
                        if(!StringUtil.isValidNew(request.getParameter("sortCol")) && request.getParameter("crSortBy")!=null && request.getParameter("crSortBy").indexOf("#####")!=-1 && request.getParameter("crSortBy").split("#####")[0]!=null){
                        	if(tempSortByArr!=null){
    							// get table other Request Parameter.
                    			for(int tempCount=0; tempCount<tempSortByArr.length;tempCount++)
                    			{
                    				if((field.getDbField().equals(tempSortByArr[tempCount].split("#####")[1]))&&(tableName.equals(tempSortByArr[tempCount].split("#####")[0])))
                    					sortColTable=tempSortByArr[tempCount].split("#####")[0];
                    			}
    						}
                        }else{
                        	sortColTable=request.getParameter("sortColTable");
                        }
                        if(sortColTable==null){
                        	sortColTable=mappings.getTableName();
                        }
                        //CUSTOM_REPORT_SORTING_ISSUE ends
                        if (sortable && (request.getParameter("fileType") == null || request.getParameter("fileType").equals("") ||  request.getParameter("fileType").equalsIgnoreCase("view")))
                        {
                        	StringBuffer link;
                        	link = new StringBuffer();

                        	String sortCol;
                        	sortCol = request.getParameter("sortCol");

                        	String sortOrder;
                        	sortOrder = request.getParameter("sortOrder");
                        	
                        	if(sortCol!=null && "Yes".equals(request.getParameter("fromSortForm")) ){
                    			sortColArrayList.remove(sortCol);
                    		}
                        	//P_FS_B_55233 By Nikhil Verma
                        	/*if(sortOrder==null && request.getParameter("crOrderBy")!=null)
                        		sortOrder=request.getParameter("crOrderBy");
                        	if(sortCol==null && request.getParameter("crSortBy")!=null && request.getParameter("crSortBy").indexOf("#####")!=-1 && request.getParameter("crSortBy").split("#####")[1]!=null)
                        		sortCol=request.getParameter("crSortBy").split("#####")[1];*/
                        	
                        	if(!StringUtil.isValidNew(sortCol) && tempSortByArr!=null)
                        	{
        							// get Ordering & column other Request Parameter.
                        			for(int tempCount=0; tempCount<tempSortByArr.length;tempCount++)
                        			{
                        				if(field.getDbField().equals(tempSortByArr[tempCount].split("#####")[1])&&tableName.equals(tempSortByArr[tempCount].split("#####")[0])){
                        					sortOrder=tempSortOrderArr[tempCount];
                        					sortCol=tempSortByArr[tempCount].split("#####")[1];
                        				}
                        			}
                        	}
                        	String checkAge = request.getParameter(sTableName + ":" +field.getFieldName()+":FORMAT");
                    		if(checkAge == null) {
                    			checkAge = (String)request.getAttribute(sTableName + ":" +field.getFieldName()+":FORMAT");
                    		}
                    		String lastString = "";
                    		if(checkAge != null && checkAge.split(":").length == 2) {
                    			lastString = " (By Age)";
                    		}
                    		
                        	if (sortCol != null && sortCol.equals(field.getSortColumn()))
                        	{
	                        	if (sortOrder != null && sortOrder.equals("ASC"))
	                        	{
	                        		sortOrder = "DESC";
	                        	} else 
	                        	{
	                        		sortOrder = "ASC";
	                        	}

	                        	// javascript:void(0) creates problem in case of ie. It don't allow javascript to 
	                        	// change the location of page or submit a form.
	                        	// Alias is required in sorting as fields having same name can be in two tables. 
	                        	if (sortOrder != null && sortOrder.equals("ASC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{

	                    		    	if(dashBoard != null) {
		                        			link.append("").append(tempVal1).append("").append(lastString);
	                    		    	} else if(sortColTable.equals(mappings.getTableName())){//CUSTOM_REPORT_SORTING_ISSUE starts
	                    		    		link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("<img src='").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/decending.gif' border='0'></a>"+lastString);
	                    		    	}
	                    		    	else{
	                    		    		link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("</a>"+lastString);
	                    		    	}//CUSTOM_REPORT_SORTING_ISSUE ends

//	                        		}
	                        	} else if (sortOrder != null && sortOrder.equals("DESC") && sortCol != null && sortCol.equals(field.getSortColumn()))
	                        	{

	                    		    	if(dashBoard != null) {
		                        			link.append("").append(tempVal1).append("").append(lastString);
	                    		    	} else if(sortColTable.equals(mappings.getTableName())){//CUSTOM_REPORT_SORTING_ISSUE starts
	                    		    		link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("<img src='").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/ascending1.gif' border='0'></a>"+lastString);
	                    		    	} else {
	                    		    		link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("</a>"+lastString);
	                    		    	}//CUSTOM_REPORT_SORTING_ISSUE ends
	                        	} 
                        	} else
                        	{

                    		    	if(dashBoard != null) {
	                        			link.append("").append(tempVal1).append("").append(lastString);
                    		    	} else {
                    		    		link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"ASC\")'><u>").append(tempVal1).append("</u></a>"+lastString);////CUSTOM_REPORT_SORTING_ISSUE
                    		    	}
                        	}

                        	alHeaderList.add(link.toString());
                        } else
                        {
                    		//P_E_EXPORT_CURRENCY start
                        	
                        		//P_E_EXPORT_CURRENCY ends
                        	String sortCol = null;
                        	String sortOrder;
                        	StringBuffer link = null;
                        	sortCol = request.getParameter("sortCol");
                        	sortOrder = request.getParameter("sortOrder");
                        	String sortOthCol = null;
                        	String sortOthTable=null;
                        	if(sortCol != null && "Yes".equals(request.getParameter("fromSortForm")) ){
                    			sortColArrayList.remove(sortCol);
                    		}
                        	if(!StringUtil.isValidNew(sortCol) && StringUtil.isValid(request.getParameter("crSortBy")) && StringUtil.isValid(request.getParameter("crSortBy").split("#####")[1]) && (request.getParameter("fileType") == null || request.getParameter("fileType").equals("") ||  request.getParameter("fileType").equalsIgnoreCase("view"))) {
                        		//sortCol = request.getParameter("crSortBy").split("#####")[1];
                        		if(tempSortByArr!=null)
                            	{
            							// get column other Request Parameter.
                            			for(int tempCount=0; tempCount<tempSortByArr.length;tempCount++)
                            			{
                            				if(field.getDbField().equals(tempSortByArr[tempCount].split("#####")[1])&&tableName.equals(tempSortByArr[tempCount].split("#####")[0])){
                            					sortCol=tempSortByArr[tempCount].split("#####")[1];
                            					sortOrder=tempSortOrderArr[tempCount];
                            				}
                            			}
                            	}
                        	} else if(StringUtil.isValid(request.getParameter("crSortBy")) && StringUtil.isValid(request.getParameter("crSortBy").split("#####")[1])){
                        			//sortOthCol = request.getParameter("crSortBy").split("#####")[1];
                        			if(tempSortByArr!=null)
                                	{
                							// get column other Request Parameter.
                                			for(int tempCount=0; tempCount<tempSortByArr.length;tempCount++)
                                			{
                                				if(field.getDbField().equals(tempSortByArr[tempCount].split("#####")[1])&&tableName.equals(tempSortByArr[tempCount].split("#####")[0])){
                                					sortOthCol=tempSortByArr[tempCount].split("#####")[1];
                                					sortOthTable=tempSortByArr[tempCount].split("#####")[0];
                                				}
                                			}
                                	}
                        	}
                        	//if (StringUtil.isValid(sortCol) && field.getSortColumn().equals(sortCol.trim())) {
                        	if (sortCol != null && (sortCol.equals(field.getSortColumn())) && sortColTable.equals(mappings.getTableName())) {//CUSTOM_REPORT_SORTING_ISSUE
                        		//sortOrder = request.getParameter("sortOrder");
                        		link = new StringBuffer();
                            	if (sortOrder == null || (sortOrder != null && sortOrder.equals("ASC"))) {
	                        		sortOrder = "DESC";
	                        	} else {
	                        		sortOrder = "ASC";
	                        	}
                            	String checkAge = request.getParameter(sTableName + ":" +field.getFieldName()+":FORMAT");
                        		if(checkAge == null) {
                        			checkAge = (String)request.getAttribute(sTableName + ":" +field.getFieldName()+":FORMAT");
                        		}
                        		String lastString = "";
                        		if(checkAge != null && checkAge.split(":").length == 2) {
                        			lastString = " (By Age)";
                        		}
                        		
                        		if(dashBoard != null) {
                        			link.append("").append(LanguageUtil.getString(tempVal1,request)).append("").append(lastString);
                        		} else
                        			if (sortOrder != null && sortOrder.equals("ASC") && !(StringUtil.isValid(request.getParameter("isExcel")) &&  "yes".equals(request.getParameter("isExcel")))) { //P_FS_B_15146
                        				link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("<img src='").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/decending.gif' border='0'></a>"+lastString);//CUSTOM_REPORT_SORTING_ISSUE 
                        			} else if (sortOrder != null && sortOrder.equals("DESC") && !StringUtil.isValid(request.getParameter("isExcel"))) { //P_FS_B_15146
                        				link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"").append(sortOrder).append("\")'><u>").append(tempVal1).append("</u>").append("<img src='").append(request.getContextPath()).append("/static").append(Constants.STATIC_KEY).append("/images/ascending1.gif' border='0'></a>"+lastString);//CUSTOM_REPORT_SORTING_ISSUE
                        			} else { //P_FS_B_15146 starts
                        				link.append(tempVal1);
                        			} //P_FS_B_15146 ends
                        		alHeaderList.add(link.toString());
                        		//P_FS_B_15146 starts added datatype when sorting is done for coloumn
                        		if(customFieldInfo==null)
                        		{
                        				dataTypeList.add(field.getDataType());
                        				if(displayTypeList != null) { //P_Enh_Excel_Numeric_Value starts
    										displayTypeList.add(field.getDisplayTypeField());
    									} //P_Enh_Excel_Numeric_Value ends
                        		}
                        		else
                        		{
                        				dataTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                        				if(displayTypeList != null) { //P_Enh_Excel_Numeric_Value starts
    										displayTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
    									} //P_Enh_Excel_Numeric_Value ends
                        		}
                        		//P_FS_B_15146 ends
                        	} else {
                        		link = new StringBuffer();
                        		if(sortOthCol != null && field.getSortColumn().equals(sortOthCol.trim()) && !(StringUtil.isValid(request.getParameter("isExcel")) &&  "yes".equals(request.getParameter("isExcel")))) { //P_FS_B_15146
                        			//CUSTOM_REPORT_SORTING_ISSUE starts
                        			if(sortOthTable!=null && sortOthTable.equals(mappings.getTableName())){
                        				link.append("<a href='#' onclick='sortBy(\"").append(mappings.getTableName()+"#####"+field.getSortColumn()).append("\", \"ASC\")'><u>").append(tempVal1).append("</u></a>");
                        			}
                        			else{
                        				link.append(tempVal1);
                        			}
                        			//CUSTOM_REPORT_SORTING_ISSUE ends
                        			alHeaderList.add(link.toString());
                        		} else {
                        			System.out.println("\n\n====tempVal1=========="+tempVal1);
                        			alHeaderList.add(tempVal1);
                        		}
                         		if(customFieldInfo==null)
                        		{
                        				dataTypeList.add(field.getDataType());
                        				if(displayTypeList != null) { //P_Enh_Excel_Numeric_Value starts
    										displayTypeList.add(field.getDisplayTypeField());
    									}//P_Enh_Excel_Numeric_Value ends
                        		}
                        		else
                        		{
                         				dataTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
                         				if(displayTypeList != null) { //P_Enh_Excel_Numeric_Value starts
    										displayTypeList.add(customFieldInfo.getString(CustomFormFieldNames.DATA_TYPE));
    									} //P_Enh_Excel_Numeric_Value ends
                        		}
                        	}
                        }
                    }
			    }
			    String reportType = request.getParameter("reportType");
			    if(reportType == null) {
			    	reportType = (String)request.getAttribute("reportType");
			    }
		        if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
			      
			        //if(checkGroupBy) {
			        	String tempSummryVal = request.getParameter(sTableName+":COUNT:ALL");
			        	if(tempSummryVal == null) {
			        		tempSummryVal = (String)request.getAttribute(sTableName+":COUNT:ALL");
			        	}
	        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
	        				if(!"".equals(tempSummryVal.trim())) {
				        		alHeaderList.add(tempSummryVal.trim());
				        		dataTypeList.add("text");
	        				} else {
				        		alHeaderList.add("COUNT");
				        		dataTypeList.add("text");
	        				}
	        				
	        			}
	        			
			        	//Field[] fields	= mappings.getSummaryFieldsArray();
	        			Field[] fields	= null;
			        	if(processHeaders){
			        		fields = mappings.getSectionTablesFieldsArrayWithOrderBy(hFld, true);//P_E_Fields-20130905-035
			        	}else{
			        		fields = mappings.getSummaryFieldsArray();//P_E_Fields-20130905-035
			        	}
						for (int k = 0; k < fields.length; k++)
						{
							if(fields[k] == null) {
								continue;
							}
							String sSearchField		= fields[k].getSearchField();
							String sSrcDisplayName = "";
							// continue if field is not specified as search field
							if(processHeaders){
								sSearchField = "nonBuildFld";
							}
							if(sSearchField == null){
								continue;
							}
                			if(StringUtil.isValid(fields[k].getDisplayExportName())) {
                				sSrcDisplayName = fields[k].getDisplayExportName();
                			} else {
                				sSrcDisplayName = fields[k].getDisplayName();
                			}
							tempSummryVal = request.getParameter(sTableName+":SUM:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
				        		tempSummryVal = (String)request.getAttribute(sTableName+":SUM:"+fields[k].getFieldName());
				        	}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
					        		alHeaderList.add("SUM:"+tempSummryVal.trim()+" ("+_baseConstants.USER_CURRENCY+")");
					        		dataTypeList.add("text");
		        				} else {
                       				alHeaderList.add("SUM:"+sSrcDisplayName);
					        		dataTypeList.add("text");
		        				}
		        				
		        			}
							tempSummryVal = request.getParameter(sTableName+":AVG:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
				        		tempSummryVal = (String)request.getAttribute(sTableName+":AVG:"+fields[k].getFieldName());
				        	}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
					        		alHeaderList.add("AVG:"+tempSummryVal.trim()+" ("+_baseConstants.USER_CURRENCY+")");
					        		dataTypeList.add("text");
		        				} else {
                       				alHeaderList.add("AVG:"+sSrcDisplayName);
					        		dataTypeList.add("text");
		        				}
		        				
		        			}
							tempSummryVal = request.getParameter(sTableName+":MAX:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
				        		tempSummryVal = (String)request.getAttribute(sTableName+":MAX:"+fields[k].getFieldName());
				        	}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
					        		alHeaderList.add("MAX:"+tempSummryVal.trim()+" ("+_baseConstants.USER_CURRENCY+")");
					        		dataTypeList.add("text");
		        				} else {
					        		alHeaderList.add("MAX:"+sSrcDisplayName);
					        		dataTypeList.add("text");
		        				}
		        				
		        			}
							tempSummryVal = request.getParameter(sTableName+":MIN:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
				        		tempSummryVal = (String)request.getAttribute(sTableName+":MIN:"+fields[k].getFieldName());
				        	}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
					        		alHeaderList.add("MIN:"+tempSummryVal.trim()+" ("+_baseConstants.USER_CURRENCY+")");
					        		dataTypeList.add("text");
		        				} else {
					        		alHeaderList.add("MIN:"+sSrcDisplayName);
					        		dataTypeList.add("text");
		        				}
		        				
		        			}

							
						}

	        			
	        			//checkGroupBy = false;
        			//}
			        
		        }
		    }
	    }

	    SequenceMap smForeignTables;

        if (bMain)
            smForeignTables	= selForeignTables;
        else
            smForeignTables	= mappings.getForeignTablesExportMap();

	    if (smForeignTables != null)
        {
            int sizeForTable = smForeignTables.size();
          //Problem in Custom Report while do export starts
            String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends

            for (int i = 0 ; i < sizeForTable ; i++)
            {
                ForeignTable foreignTable = (ForeignTable) smForeignTables.get(i);
              //Problem in Custom Report while do export starts
                if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
              //Problem in Custom Report while do export ends
                //P_E_Fields-20130905-035
                makeNewHeader(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount++, false, dashBoard, null, null, FieldNames.EMPTY_STRING, displayTypeList);
                if(!processHeaders && dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
                	getHeaderForDependents(alHeaderList, dataTypeList, foreignTable.getName(), aliasCount, false, dashBoard,null,null,"makeNewHeader");
                }
              //P_E_Fields-20130905-035
            }
	    }
    }    
	/**
     * P_E_Fields-20130905-035
     * called for each dependent table
     * @param alHeaderList
     * @param dataTypeList
     * @param sTableName
     * @param aliasCount2
     * @param b
     * @param dashBoard
     * @param reportType
     * @param list
     * @param fromWhere
     */
    private void getHeaderForDependents(ArrayList alHeaderList,ArrayList dataTypeList, String sTableName, int aliasCount2,boolean b, String dashBoard, String reportType, StringBuffer list, String fromWhere) {
		FieldMappings tempMappings		= DBUtil.getInstance().getFieldMappings(sTableName);
		HeaderMap[] hMap = tempMappings.getHeaderMap();
		DependentTable dTable = null;
		HeaderField hFld = null;
		String headerName = "";
		for(HeaderMap h:hMap) {
			hFld = h.getHeaderFields();
			headerName = h.getName();
			DependentTable[] dependenTables = hFld.getDependentTables();
			Set<String> selectedAlias = new HashSet<String>();
			if(dependenTables!=null && dependenTables.length>0){
				int dependantTableSize = dependenTables.length;
				for(int i=0;i<dependantTableSize;i++){
					dTable =  dependenTables[i];
					selectedAlias = dependentFieldsMap.get(sTableName);
					if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
						if("makeNewHeader".equals(fromWhere)){
							makeNewHeader(alHeaderList, dataTypeList, dTable.getTableAnchor(), aliasCount++, false, dashBoard,hFld,tempMappings,headerName);
						}else if("getNewColumnSummryList".equals(fromWhere)){
							getNewColumnSummryList(list , dTable.getTableAnchor() , aliasCount2++, false, reportType, dashBoard,hFld,tempMappings,headerName);
						}
					}
				}
			}
		}
	}
    public int getBlankStackMap(String sTableName , SequenceMap returnMap , SequenceMap reverseMap , int count, boolean bMain)
    {
		returnMap.put(sTableName + count	, new Stack());
		reverseMap.put(sTableName + count	, new Stack());
		if("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) ) {
			//count++;
			returnMap.put("franchisee"	, new Stack());
			reverseMap.put("franchisee"	, new Stack());
		}

		count++;
		FieldMappings mappings		= null;
		//get the FieldMapping object for the module's main table
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);

		//Modified by Anuj 26-Nov-2004
		//SequenceMap smForeignTables		= mappings.getForeignTablesMap();
		HttpSession session = StrutsUtil.getHttpSession();
		if (bMain &&  "3".equals(session.getAttribute("fimFlag"))) {
			if(bMain && dependentFieldsMap.containsKey(sTableName) && PortalUtils.hasDependentAddressTable(sTableName,request)){
				count = getHeaderBlankStack(sTableName,returnMap,reverseMap,count);
			}
		}
		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();//smForeignTables		= mappings.getForeignTablesMap();
		
		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				if("fimEntityDetail".equals(sTableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts  //BBP-20150831-675 starts
					if("address".equals(foreignTable.getName()))
						continue;
					}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends  //BBP-20150831-675 ends
				count = getBlankStackMap(foreignTable.getName() , returnMap , reverseMap , count++, false);
				//P_E_Fields-20130905-035
				if(dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					count = getHeaderBlankStack(foreignTable.getName(),returnMap,reverseMap,count);
				}
				
				//P_E_Fields-20130905-035
				}//for loop
		}//end if null check
		
		if (bMain && "fim".equals(menuName) && ("2".equals(session.getAttribute("fimFlag")) || "3".equals(session.getAttribute("fimFlag")))) {
			/*returnMap.put("franMuOwner"	, new Stack());
			reverseMap.put("franMuOwner"	, new Stack());*/
				smForeignTables		= selFranTables;
			
			
			if(smForeignTables != null){
				returnMap.put("muFranchisee"	, new Stack());
				reverseMap.put("muFranchisee"	, new Stack());
				int sizeForTable				= smForeignTables.size();
				//Problem in Custom Report while do export starts
				String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
				//Problem in Custom Report while do export ends
				for(int i = 0 ; i < sizeForTable ; i++){
					ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
					//Problem in Custom Report while do export starts
					if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
						continue;
					}
					//Problem in Custom Report while do export ends
					if("fimEntityDetail".equals(sTableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts  //BBP-20150831-675 starts
						if("address".equals(foreignTable.getName()))
							continue;
						}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends  //BBP-20150831-675 ends
					count = getBlankStackMap(foreignTable.getName() , returnMap , reverseMap , count++, false);
					//P_E_Fields-20130905-035
					if(dependentMuFranFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
						count = getHeaderBlankStack(foreignTable.getName(),returnMap,reverseMap,count,true);
					}
					//P_E_Fields-20130905-035
					}//for loop
			}
			
		}
		
		
		return count;
}
    public SequenceMap getStackMap(SequenceMap resultData){
    	return getStackMap(resultData, null);
    }
    public SequenceMap getStackMap(SequenceMap resultData, String dashBoard){
    	return getStackMap(resultData, null,false);
    }
public SequenceMap getStackMap(SequenceMap resultData, String dashBoard,boolean isFran){

		SequenceMap reverseMap				= new SequenceMap();
		SequenceMap returnMap				= new SequenceMap();

		String sMainTable					= request.getParameter("exportModule");
		if(sMainTable == null) {
			sMainTable					= (String)request.getAttribute("exportModule");
			//BBEH_FOR_SMC_OPTIMIZATION starts
		}
		if(sMainTable == null) {
			sMainTable					=(String)request.getSession().getAttribute("exportModule");
			request.getSession().removeAttribute("exportModule");
		}
////BBEH_FOR_SMC_OPTIMIZATION ends
		SequenceMap data					= (SequenceMap)resultData.get(sMainTable);
		getRowSpan(data , sMainTable, true, dashBoard,isFran);
//      P_FIM_O_2008072310000465 change starts
        getReverseRowSpan(data,sMainTable,true,0,isFran);
//      P_FIM_O_2008072310000465 change ends




		//create the blank stack map
		getBlankStackMap(sMainTable , returnMap , reverseMap , 1, true);//(franchisees1 = [],fimEntityDetail2 = [],address3 = [])


		if(!isFran) {
			franCount = fillStack(returnMap , data , 1 , sMainTable , null, true, dashBoard);
		} else {
			franfillStack(returnMap , data , franCount , sMainTable , null, true, dashBoard,null,null,FieldNames.EMPTY_STRING);
		}

		Iterator iterReverseMap	= reverseMap.keys().iterator();
		while(iterReverseMap.hasNext()){
			Object key				= (Object)iterReverseMap.next();
			Stack forwardStack		= (Stack)returnMap.get(key);
			Stack reverseStack		= (Stack)reverseMap.get(key);
			//count = 0;
			while(!forwardStack.empty()){
				reverseStack.push(forwardStack.pop());

			}

		}//end while iterReverseMap

		return reverseMap;

	}

	private int fillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain)
	{
		return fillStack(returnMap, data , aliasCount , tableName , parentData, bMain, null);
	}
	private int fillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain,String dashBoard)
	{
		return fillStack(returnMap, data , aliasCount , tableName , parentData, bMain, dashBoard,null,null,FieldNames.EMPTY_STRING);
	}
	private int fillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain, String dashBoard,HeaderField headerField,FieldMappings parentMappings,String headerName){
		return fillStack(returnMap, data, aliasCount, tableName, parentData, bMain, dashBoard, headerField, parentMappings, headerName, false);
	}
	//P_E_Fields-20130905-035 changed parameters
	private int franfillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain, String dashBoard,HeaderField headerField,FieldMappings parentMappings,String headerName)
    {
		int count = aliasCount;
		SequenceMap smForeignTables		= null;
		Iterator iterData = data.keys().iterator();
		ArrayList blankTdList = null;
		boolean processHeaders = false;
		boolean includeFran = false;
		int totalRowSpan = 0;
		if(headerField!=null){
			processHeaders = true;
		}
		Stack stackMap = (Stack) returnMap.get(tableName + aliasCount);
		if (bMain && "fim".equals(menuName)) {
			smForeignTables		= selFranTables;
			while (iterData.hasNext())
	        {
				try
	            {
				blankTdList	= new ArrayList();
				SequenceMap dataListMap	= new SequenceMap();
				ArrayList dataList = new ArrayList();
				
	            // rework
				count = aliasCount;
				SequenceMap rowMap = (SequenceMap) data.get(iterData.next());
				String rowSpan = (String) rowMap.get("rowSpan");
				
                if (rowSpan == null || rowSpan.equals("null"))
                {
					rowSpan = "1";
				}
	            
		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
				if(rowMap.get("muFranchisee")!=null) {
					stackMap = (Stack) returnMap.get("muFranchisee");
				
					
					
					dataListMap	= new SequenceMap();
					dataList = new ArrayList();
				Iterator franIter = ((SequenceMap)((SequenceMap)rowMap.get("muFranchisee")).get(0)).values().iterator();
				while(franIter.hasNext()) {
					dataList.add(franIter.next());
					blankTdList.add("");
					
				}
				
				dataListMap.put("rowSpan" , rowSpan);
				dataListMap.put( "data" ,dataList );
				stackMap.push(dataListMap);
				totalRowSpan = totalRowSpan + Integer.parseInt(rowSpan);
				try{
				int dataSpan	= Integer.parseInt(rowSpan) -1;
				if(dataSpan >= 1){
					for(int k = 0 ; k < dataSpan; k++ ){
						SequenceMap rowSpanMap = new SequenceMap();
						rowSpanMap.put("rowSpan" , "true");
						rowSpanMap.put("data",blankTdList);
						stackMap.push(rowSpanMap);
					}
				}
				}catch(Exception e){
		        	logger.error("\nException in ExportDataCollector class in fillStack method :"+e.getMessage());

				}
				/*stackMap = (Stack) returnMap.get("franMuOwner");
				 franIter = ((SequenceMap)rowMap.get("franMuOwner")).values().iterator();
				while(franIter.hasNext()) {
					dataListMap	= new SequenceMap();
					
					dataListMap.put("rowSpan" , "1");
					dataListMap.put("data" , ((SequenceMap)franIter.next()).values());
					stackMap.push(dataListMap);
				}*/
				
				
				stackMap = (Stack) returnMap.get(tableName + (aliasCount-1));
				
			boolean isFirstIteration = true;
			for(int i = 0 ; i < sizeForTable ; i++){
				if(i>0) {
					isFirstIteration = false;
				}
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				SequenceMap forTabMap	= (SequenceMap)rowMap.get(foreignTable.getName());
				//P_E_Fields-20130905-035
				count = fillStack(returnMap , forTabMap , count++ , foreignTable.getName() , rowMap, false, dashBoard,null,null,FieldNames.EMPTY_STRING,isFirstIteration,true);
				if(!processHeaders && dependentMuFranFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					count =	fillFranStackHeader(returnMap , rowMap , count++ , foreignTable.getName() , rowMap, false, dashBoard,isFirstIteration);
				}
				//P_E_Fields-20130905-035
			}//for loop
				}
		}
	            }catch(Exception e){
	    			e.printStackTrace();
	            	logger.error("\nException in ExportDataCollector class in fillStack method 22:"+e.getMessage());

	    		}
	    		}
			try{
				if(parentData != null){
					String moreSpan = "0";
					moreSpan = (String)parentData.get("rowSpan");
					try{
					if(Integer.parseInt(moreSpan) > totalRowSpan){
						int diff = Integer.parseInt(moreSpan) - totalRowSpan;
						for(int z = 0; z < diff; z++ )
						{
							SequenceMap rowSpanMap = new SequenceMap();
							rowSpanMap.put("blankTD" , "true");
							rowSpanMap.put("data",blankTdList);
							stackMap.push(rowSpanMap);

						}
					}
					}catch(Exception e){
			        	logger.error("\nException in ExportDataCollector class in fillStack method 33:"+e.getMessage());
					}


				}
				}catch(Exception e){
					logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java --> fillStack(): 44", e);
				}
		}
		return count;
		
    }
	private int fillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain, String dashBoard,HeaderField headerField,FieldMappings parentMappings,String headerName,boolean firstIteration) {
		return fillStack(returnMap, data, aliasCount, tableName, parentData, bMain, dashBoard, headerField, parentMappings, headerName, firstIteration, false);
	}
    private int fillStack(SequenceMap returnMap, SequenceMap data , int aliasCount , String tableName , SequenceMap parentData, boolean bMain, String dashBoard,HeaderField headerField,FieldMappings parentMappings,String headerName,boolean firstIteration, boolean isMuFran)
    {
    	
		int count = aliasCount;
		Iterator iterData = data.keys().iterator();
		
		int totalRowSpan = 0;
		Stack stackMap = (Stack) returnMap.get(tableName + aliasCount);
		ArrayList blankTdList = null;
		FieldMappings mappings = null;
		boolean processHeaders = false;
		boolean includeFran = false;
		if(headerField!=null){
			processHeaders = true;
		}
        // get the FieldMapping object for the module's main table
		mappings = DBUtil.getInstance().getFieldMappings(tableName);
		UserRoleMap userRoleMap		= (UserRoleMap)request.getSession().getAttribute("userRoleMap");
	    String user_level=(String)request.getSession().getAttribute("user_level");
		SequenceMap sArrTableFields	= null;//mappings.getAllFields();
		if(processHeaders){
    		sArrTableFields = mappings.getDependentTblFields(headerField);
    	}else{
    		sArrTableFields = mappings.getAllFields();
    	}
		String sGetSelected	= request.getParameter("getSelectedField");
		if(sGetSelected == null) {
			sGetSelected					= (String)request.getAttribute("getSelectedField");
		}
		String relationShip= (String)request.getAttribute("relationShip");//P_ENH_CUS_RPT_RELATIONSHIP(one to one and one to many relationship option) 
		String [] sArrSelectedField	= null;
		ArrayList sArrSelectedFieldList	= null;
		// get the selected field names if sGetSelected is not null
		if (sGetSelected != null)
        {
			if(dashBoard != null) {
			    sArrSelectedField = (String [])request.getAttribute(tableName + aliasCount);
			} else {
			    sArrSelectedField = request.getParameterValues(tableName + aliasCount);
			}
			sArrSelectedFieldList = new ArrayList();
			for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
		    	sArrSelectedFieldList.add(sArrSelectedField[i]);
			}
		}
		int size = sArrTableFields.size();

		if (sGetSelected != null)
        {
			if (sArrSelectedField == null)
            {
			    size = 0 ;
            } else
            {
			    size = sArrSelectedField.length;
            }
		}

		aliasCount++;

		
		
		while (iterData.hasNext())
        {
			blankTdList	= new ArrayList();
			
            // rework
			count = aliasCount;
			SequenceMap rowMap = (SequenceMap) data.get(iterData.next());
            try
            {
				SequenceMap dataListMap	= new SequenceMap();
				ArrayList dataList = new ArrayList();
				String rowSpan = (String) rowMap.get("rowSpan");
				
                if (rowSpan == null || rowSpan.equals("null"))
                {
					rowSpan = "1";
				}
				
                //if (sArrTableFields != null && size > 0)
                if (sArrTableFields != null)
                {
                	boolean checkGroupBy = false;
                	String tmpDisplayName = "";
                	String tmpFld = "";
                	String tmpFldLbl = "";
                	
					for (int i = 0; i < size; i++)
                    {
						
						
						Field field	= (Field) sArrTableFields.get(i);
						if(StringUtil.isValid(field.getDisplayExportName())) {
							tmpDisplayName = field.getDisplayExportName();
						} else {
							tmpDisplayName = field.getDisplayName();
						}
						//dki-20161011-616 start
						/*if((!ModuleUtil.zcubatorImplemented() || !"Y".equals(MultiTenancyUtil.getTenantConstants().CM_PILOT_ENABLED)) && tmpDisplayName.contains("Activity / Campaign Code") && ("cm".equals(menuName))){//P_CM_B_26948
                    		continue;
                    	}*/
						//dki-20161011-616 End
						//FIM Export failsDKI starts
						/*if("fim".equals(menuName) && ("isStore".equals(field.getFieldName()) || "isFranchisee".equals(field.getFieldName()))){
							continue;
						}*/
						//FIM Export failsDKI ends
						//P_B_53670 starts
            			if ("cm".equals(menuName) && "taskType".equals(field.getFieldName())){
            				continue;
            			}
            			//P_B_57374 ends
						if (sGetSelected != null && sArrSelectedField != null)
                        {
							if(sArrSelectedField[i].indexOf("fimDocuments_")!=-1 || sArrSelectedField[i].indexOf("cmDocuments_")!=-1 || sArrSelectedField[i].indexOf("fsDocuments_")!=-1 || sArrSelectedField[i].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[i].indexOf("areaDocuments_")!=-1 || sArrSelectedField[i].indexOf("fimMuDocuments_")!=-1) {//P_CM_B_57781
								continue;
							}
							if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )  && sArrSelectedField[i].indexOf("franchiseeName") !=-1) {
								includeFran = true;
								continue;
							}
							//P_export_50044

							if(sArrSelectedField[i]!= null && (sArrSelectedField[i]).indexOf(":")!= -1)
							{
					        	tmpFld = sArrSelectedField[i].split(":")[0];
					        	tmpFldLbl = sArrSelectedField[i].split(":")[1];
					        	if(processHeaders){
					        		field = parentMappings.getOtherTableField(tmpFld.trim());//P_E_Fields-20130905-035
					        	}else{
					        		field = mappings.getField(tmpFld.trim());
					        	}
					        	if(StringUtil.isValid(field.getDisplayExportName())) {
									tmpDisplayName = field.getDisplayExportName();
								} else {
									tmpDisplayName = field.getDisplayName();
								}
							}
							else
							{
								//field = mappings.getField(sArrSelectedField[i]);
								if(processHeaders){
					        		field = parentMappings.getOtherTableField(sArrSelectedField[i]);//P_E_Fields-20130905-035
					        	}else{
					        		field = mappings.getField(sArrSelectedField[i]);
					        	}
								if(StringUtil.isValid(field.getDisplayExportName())) {
									tmpDisplayName = field.getDisplayExportName();
								} else {
									tmpDisplayName = field.getDisplayName();
								}
								tmpFldLbl=tmpDisplayName;
								
							}
							//P_export_50044 end
						}//P_export_50044
						else
						{
							tmpFldLbl=tmpDisplayName;
						}
						//P_export_50044 end
						
						if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
						{
							continue;
						}
						
						boolean bExportTable = field.isFieldExportable();
						Info customFieldInfo = null;
						
                        if (bExportTable)
                        {
                        	checkGroupBy = true;
							boolean sCustomField		= field.isCustomField();
							if (sCustomField == true){
								CustomFormWebImpl customWebImpl = CustomFormWebImpl.getInstance();
								
								customFieldInfo = customWebImpl.getCustomFieldsForExport(menuName, mappings.getTableName(), field.getFieldName().substring(field.getFieldName().lastIndexOf("C")+1),(String)request.getSession().getAttribute("fimFlag"));
								if (customFieldInfo == null)
									continue;	
							}
							if(!field.isActiveField()) {
								continue;
							}
							//FIM Export failsDKI starts
							if("fim".equals(menuName) && ("isStore".equals(field.getFieldName()) || "isFranchisee".equals(field.getFieldName()))){
								continue;
							}
							if("fbc".equals(field.getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
								continue;
							}
							//FIM Export failsDKI ends
							//START : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
							/*if ("cm".equals(menuName) && !"1".equals(userLevel) && ("cmContactDetails".equals(tableName) || "cmContactStatusChangeInfoExport".equals(tableName)))
							{
								if("cmLeadSubStatusID".equals(field.getFieldName()) || field.getFieldName().startsWith("subStatus")) {
									continue;
								}
							}*/
							//END : P_B_CM_33700 : 28/02/2014 : Veerpal Singh
							
							//Commeted by AmitG for renaming the display text in XML

							//new entry
							dataList.add(rowMap.get(formatFieldNames(tmpFldLbl,field)));//P_B_Codebase_22112013
							blankTdList.add("");
						}


					}// for loop end of filling cells of main table at the present stage
					
					
					HeaderMap[] hMap = mappings.getHeaderMap();
			   		for(HeaderMap h:hMap) {
			   			
			   			DocumentMap[] docMaps = null;
			   			HeaderField hFld = h.getHeaderFields();
			   			Boolean isTabularSection=hFld.isTabularSection();
			   			if(isTabularSection){
			   				continue;
			   			}
						Documents[] docs =  hFld.getDocuments();
						
						
						if(docs != null && docs.length > 0) {
								docMaps = docs[0].getDocumentMaps();
								if(docMaps != null && docMaps.length > 0) {
									for(DocumentMap dMap : docMaps) {
										SequenceMap docMap  = dMap.getDocumentFieldMap();
										
										if(sArrSelectedFieldList !=null) {
											if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
												dataList.add(rowMap.get(formatFieldNames(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"),null)));//P_B_Codebase_22112013 //P_CM_B_57781
												blankTdList.add("");
											}
										} else {
											dataList.add(rowMap.get(formatFieldNames(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"),null)));//P_B_Codebase_22112013 //P_CM_B_57781
											blankTdList.add("");
											
										}
									}
								}
						}
			   		}
					String reportType = request.getParameter("reportType");
					if(reportType == null) {
						reportType	= (String)request.getAttribute("reportType");
					}
					
			        if(reportType != null && ("MG".equals(reportType) || "SG".equals(reportType))) {
				       
			        	
			        	String tempSummryVal = request.getParameter(tableName+":COUNT:ALL");
						if(tempSummryVal == null) {
							tempSummryVal	= (String)request.getAttribute(tableName+":COUNT:ALL");
						}
			        	
	        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
	        				if(!"".equals(tempSummryVal.trim())) {
								dataList.add(rowMap.get(tempSummryVal.trim()));
								blankTdList.add("");
	        				} else {
	        					dataList.add("COUNT");
	        					blankTdList.add("");
	        				}
	        			}
	        			
	        			//P_E_Fields-20130905-035
	        			Field[] fields	= null;//mappings.getSummaryFieldsArray();
			        	if(processHeaders){
			        		fields	= mappings.getSectionTablesFieldsArrayWithOrderBy(headerField, true);
			        	}else{
			        		fields	= mappings.getSummaryFieldsArray();
			        	}
			        	//P_E_Fields-20130905-035
			        	String tempDisplayName = "";
						for (int k = 0; k < fields.length; k++)
						{
							String sSearchField		= fields[k].getSearchField();
							// continue if field is not specified as search field
							if(processHeaders){
								sSearchField = "nonBuildFld";//P_E_Fields-20130905-035
							}
							if(sSearchField == null){
								continue;
							}
							if("fbc".equals(fields[k].getFieldName()) && !(ModuleUtil.auditImplemented() && ModuleUtil.canAccessAudit(userRoleMap, user_level))){  //ENH_PW_FBC
								continue;
							}
							if(!fields[k].isActiveField() && processHeaders){
								continue;//P_E_Fields-20130905-035
							}
							if(StringUtil.isValid(fields[k].getDisplayExportName())) {
								tempDisplayName = fields[k].getDisplayExportName();
							} else {
								tempDisplayName = fields[k].getDisplayName();
							}
							tempSummryVal = request.getParameter(tableName+":SUM:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
								tempSummryVal	= (String)request.getAttribute(tableName+":SUM:"+fields[k].getFieldName());
							}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
		        					dataList.add(rowMap.get("SUM:"+tempSummryVal.trim()));
					        		blankTdList.add("");
		        				} else {
									dataList.add(rowMap.get("SUM:"+tempDisplayName));
					        		blankTdList.add("");
		        				}
		        			}
							tempSummryVal = request.getParameter(tableName+":AVG:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
								tempSummryVal	= (String)request.getAttribute(tableName+":AVG:"+fields[k].getFieldName());
							}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
		        					dataList.add(rowMap.get("AVG:"+tempSummryVal.trim()));
					        		blankTdList.add("");
		        				} else {
					        		dataList.add(rowMap.get("AVG:"+tempDisplayName));
					        		blankTdList.add("");
		        				}
		        			}
							tempSummryVal = request.getParameter(tableName+":MAX:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
								tempSummryVal	= (String)request.getAttribute(tableName+":MAX:"+fields[k].getFieldName());
							}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
		        					dataList.add(rowMap.get("MAX:"+tempSummryVal.trim()));
					        		blankTdList.add("");
		        				} else {
					        		dataList.add(rowMap.get("MAX:"+tempDisplayName));
					        		blankTdList.add("");
		        				}
		        			}
							tempSummryVal = request.getParameter(tableName+":MIN:"+fields[k].getFieldName());
							if(tempSummryVal == null) {
								tempSummryVal	= (String)request.getAttribute(tableName+":MIN:"+fields[k].getFieldName());
							}
		        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
		        				if(!"".equals(tempSummryVal.trim())) {
		        					dataList.add(rowMap.get("MIN:"+tempSummryVal.trim()));
					        		blankTdList.add("");
		        				} else {
					        		dataList.add(rowMap.get("MIN:"+tempDisplayName));
					        		blankTdList.add("");
		        				}
		        			}

							
						}
			        }
			        
				}
                if( Integer.parseInt(rowSpan)>1&&StringUtil.isValidNew(rowSpan))//relationship option visible or not
                {
                	request.setAttribute("isRelationRadioVisible","true");
                }
                if(StringUtil.isValidNew(relationShip)&&"2".equals(relationShip)){//P_ENH_CUS_RPT_RELATIONSHIP(one to one and one to many relationship option) starts
                dataListMap.put("rowSpan" , "1");	
                }
                else{//P_ENH_CUS_RPT_RELATIONSHIP(one to one and one to many relationship option) ends
				dataListMap.put("rowSpan" , rowSpan);
                }
				dataListMap.put("data" , dataList);
				
				
				stackMap.push(dataListMap);
				totalRowSpan = totalRowSpan + Integer.parseInt(rowSpan);
				try{
				int dataSpan	= Integer.parseInt(rowSpan) -1;
				if(dataSpan >= 1){
					for(int k = 0 ; k < dataSpan; k++ ){
						SequenceMap rowSpanMap = new SequenceMap();
						rowSpanMap.put("rowSpan" , "true");
						rowSpanMap.put("data",blankTdList);
						if(StringUtil.isValidNew(relationShip)&&"2".equals(relationShip)){//P_ENH_CUS_RPT_RELATIONSHIP(one to one and one to many relationship option) starts
						stackMap.push(dataListMap);
						}else{//P_ENH_CUS_RPT_RELATIONSHIP(one to one and one to many relationship option) ends
						stackMap.push(rowSpanMap);
						}
					}
				}
				}catch(Exception e){
		        	logger.error("\nException in ExportDataCollector class in fillStack method :"+e.getMessage());

				}
				
				HttpSession session = StrutsUtil.getHttpSession();
				if (bMain && "fim".equals(menuName) && ( "3".equals(session.getAttribute("fimFlag")))) {
					if(bMain && !processHeaders && dependentFieldsMap.containsKey(tableName) && PortalUtils.hasDependentAddressTable(tableName,request)){
						fillStackHeader(returnMap , rowMap , count++ , tableName , rowMap, false, dashBoard);
					}
				}
				//Modified by Anuj 26-Nov-2004
				SequenceMap smForeignTables		= null;
				if (bMain)
					smForeignTables		= selForeignTables;
				else
					smForeignTables		= mappings.getForeignTablesExportMap();//smForeignTables		= mappings.getForeignTablesMap();

				if(includeFran) {
					stackMap = (Stack) returnMap.get("franchisee");
					SequenceMap franRowMap = (SequenceMap)rowMap.get("franchisee");
					String franRowSpan = (String) franRowMap.get("rowSpan");
					
	                if (franRowSpan == null || franRowSpan.equals("null"))
	                {
	                	franRowSpan = "1";
					}
					Iterator franIter = ((SequenceMap)rowMap.get("franchisee")).values().iterator();
					while(franIter.hasNext()) {
						dataListMap	= new SequenceMap();
						 dataList = new ArrayList();
						 dataList.add(((SequenceMap)franIter.next()).get("franchiseeName"));
						dataListMap.put("rowSpan" , franRowSpan);
						dataListMap.put("data" , dataList);
						stackMap.push(dataListMap);
					}
					stackMap = (Stack) returnMap.get(tableName + (aliasCount-1));
				}
				if(smForeignTables != null){
					int sizeForTable				= smForeignTables.size();
					//Problem in Custom Report while do export starts
					String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
					Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
					//Problem in Custom Report while do export ends
					boolean isFirstIteration = true;
					for(int i = 0 ; i < sizeForTable ; i++){
						if(i>0) {
							isFirstIteration = false;
						}
						ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
						//Problem in Custom Report while do export starts
						if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
							continue;
						}
						//Problem in Custom Report while do export ends
						if("fimEntityDetail".equals(tableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts //BBP-20150831-675 starts
							if("address".equals(foreignTable.getName()))
								continue;
							}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends  //BBP-20150831-675 ends
						SequenceMap forTabMap	= (SequenceMap)rowMap.get(foreignTable.getName());
						//P_E_Fields-20130905-035
						count = fillStack(returnMap , forTabMap , count++ , foreignTable.getName() , rowMap, false, dashBoard,null,null,FieldNames.EMPTY_STRING,isFirstIteration,isMuFran);
						if(isMuFran) {
							if(!processHeaders && dependentMuFranFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
								fillFranStackHeader(returnMap , rowMap , count , foreignTable.getName() , rowMap, false, dashBoard,isFirstIteration);//Charleys-20150824-580 starts
								count+=dependentMuFranFieldsMap.get(foreignTable.getName()).size();//Charleys-20150824-580 ends
							}
						} else {
							if(!processHeaders && dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
								fillStackHeader(returnMap , rowMap , count , foreignTable.getName() , rowMap, false, dashBoard);//Charleys-20150824-580 starts
								count+=dependentFieldsMap.get(foreignTable.getName()).size();//Charleys-20150824-580 ends
							}
						}
						//P_E_Fields-20130905-035
					}//for loop

				}//end if null check


				
		}catch(Exception e){
			e.printStackTrace();
        	logger.error("\nException in ExportDataCollector class in fillStack method 22:"+e.getMessage());

		}
		}//end of while
			//appending blank td Start
				try{
				if(parentData != null){
					String moreSpan = "0";
					moreSpan = (String)parentData.get("rowSpan");
					try{
					if(Integer.parseInt(moreSpan) > totalRowSpan){
						int diff = Integer.parseInt(moreSpan) - totalRowSpan;
						for(int z = 0; z < diff; z++ )
						{
							SequenceMap rowSpanMap = new SequenceMap();
							if ("cm".equals(menuName)){
								rowSpanMap.put("rowSpan" , "1");
								rowSpanMap.put("data",blankTdList);
							}else if ("fim".equals(menuName) && StringUtil.isValidNew(relationShip) && "2".equals(relationShip)){ //Bug 61429
								rowSpanMap.put("rowSpan" , "1");
								rowSpanMap.put("data",blankTdList);
							}else {
								rowSpanMap.put("blankTD" , "true");
								rowSpanMap.put("data",blankTdList);
							}
							stackMap.push(rowSpanMap);

						}
					}
					}catch(Exception e){
			        	logger.error("\nException in ExportDataCollector class in fillStack method 33:"+e.getMessage());
					}


				}
				//Problem in Custom Report while do export starts
				if("owners".equals(tableName)){
					
					Stack fimOwnersMap = returnMap.get("fimOwners" + (aliasCount))!=null?(Stack) returnMap.get("fimOwners" + (aliasCount)):null;
					if(fimOwnersMap!=null){
						int fimOwnersMapSize = fimOwnersMap.size();
						int ownersMapSize = stackMap.size();
						int dataListSize = ((ArrayList)((SequenceMap)(fimOwnersMap.get(0))).get("data")).size();

						blankTdList = new ArrayList();
						for(int i=0;i<dataListSize;i++){
							blankTdList.add("");
						}

						int diff = ownersMapSize - fimOwnersMapSize;

						for(int i=0;i<diff;i++){
							SequenceMap rowSpanMap = new SequenceMap();
							rowSpanMap.put("blankTD" , "true");
							rowSpanMap.put("data",blankTdList);
							fimOwnersMap.push(rowSpanMap);
						}
					}
				}
				//Problem in Custom Report while do export ends
				
				}catch(Exception e){
					logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java --> fillStack(): 44", e);
				}

				// appending blank td ends

		return count;
	}
    
    /**
     * //P_E_Fields-20130905-035
     * @param returnMap
     * @param forTabMap
     * @param count
     * @param tableName
     * @param rowMap
     * @param b
     * @param dashBoard
     */
    private void fillStackHeader(SequenceMap returnMap, SequenceMap forTabMap,int count, String tableName, SequenceMap rowMap, boolean b, String dashBoard){
    	fillStackHeader(returnMap, forTabMap, count, tableName, rowMap, b, dashBoard, false);
    }
    private void fillStackHeader(SequenceMap returnMap, SequenceMap forTabMap,int count, String tableName, SequenceMap rowMap, boolean b, String dashBoard,boolean firstIteration) {
    	FieldMappings tempMappings		= DBUtil.getInstance().getFieldMappings(tableName);
		HeaderMap[] hMap = tempMappings.getHeaderMap();
		DependentTable dTable = null;
		HeaderField hFld = null;
		String headerName = FieldNames.EMPTY_STRING;
		Set<String> selectedAlias = new HashSet<String>();
		for(HeaderMap h:hMap) {
			hFld = h.getHeaderFields();
			headerName = h.getName();
			DependentTable[] dependenTables = hFld.getDependentTables();
			if(dependenTables!=null && dependenTables.length>0){
				int dependantTableSize = dependenTables.length;
				for(int i=0;i<dependantTableSize;i++){
					dTable =  dependenTables[i];
					selectedAlias = dependentFieldsMap.get(tableName);
					if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
						forTabMap	= (SequenceMap)rowMap.get(headerName+dTable.getTableAnchor());
						fillStack(returnMap, forTabMap, count, dTable.getTableAnchor(), rowMap, b,dashBoard,hFld,tempMappings,headerName,firstIteration);
					}
				}
			}
		}
	}
    
    
    private int fillFranStackHeader(SequenceMap returnMap, SequenceMap forTabMap,int count, String tableName, SequenceMap rowMap, boolean b, String dashBoard,boolean firstIteration) {
    	FieldMappings tempMappings		= DBUtil.getInstance().getFieldMappings(tableName);
		HeaderMap[] hMap = tempMappings.getHeaderMap();
		DependentTable dTable = null;
		HeaderField hFld = null;
		String headerName = FieldNames.EMPTY_STRING;
		Set<String> selectedAlias = new HashSet<String>();
		for(HeaderMap h:hMap) {
			hFld = h.getHeaderFields();
			headerName = h.getName();
			DependentTable[] dependenTables = hFld.getDependentTables();
			if(dependenTables!=null && dependenTables.length>0){
				int dependantTableSize = dependenTables.length;
				for(int i=0;i<dependantTableSize;i++){
					dTable =  dependenTables[i];
					selectedAlias = dependentMuFranFieldsMap.get(tableName);
					if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
						forTabMap	= (SequenceMap)rowMap.get(headerName+dTable.getTableAnchor());
						count = fillStack(returnMap, forTabMap, count, dTable.getTableAnchor(), rowMap, b,dashBoard,hFld,tempMappings,headerName,firstIteration,true);
					}
				}
			}
		}
		return count;
	}
    
    /**
     * P_E_Fields-20130905-035
     * @param sTableName
     * @param returnMap
     * @param reverseMap
     * @param count
     * @return
     */
    private int getHeaderBlankStack(String sTableName, SequenceMap returnMap, SequenceMap reverseMap, int count) {
    	return getHeaderBlankStack(sTableName, returnMap, reverseMap, count, false);
    }
    private int getHeaderBlankStack(String sTableName, SequenceMap returnMap, SequenceMap reverseMap, int count,boolean isMuFran) {
			Set<String> selectedAlias = dependentFieldsMap.get(sTableName);
			if(isMuFran) {
				selectedAlias = dependentMuFranFieldsMap.get(sTableName);
			} 
			FieldMappings dependentMappings = DBUtil.getInstance().getFieldMappings(sTableName);
			HeaderMap[] hMap = dependentMappings.getHeaderMap();
			DependentTable dTable = null;
			HeaderField hFld = null;
			for(HeaderMap h:hMap) {
				hFld = h.getHeaderFields();
				DependentTable[] dependenTables = hFld.getDependentTables();
				if(dependenTables!=null && dependenTables.length>0){
					int dependantTableSize = dependenTables.length;
					for(int i=0;i<dependantTableSize;i++){
						dTable =  dependenTables[i];
						if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
							returnMap.put(dTable.getTableAnchor() + count	, new Stack());
							reverseMap.put(dTable.getTableAnchor() + count	, new Stack());
							count++;
						}
					}
				}
			}
			return count;
	}
    
    private void getRowSpan( SequenceMap data , String tableName, boolean bMain) {
    	getRowSpan(data , tableName, bMain, null);
    }
    private void getRowSpan( SequenceMap data , String tableName, boolean bMain, String dashBoard) {
    	 getRowSpan(data, tableName, bMain, dashBoard,false);
    }
    
    private void getRowSpan( SequenceMap data , String tableName, boolean bMain, String dashBoard,boolean isFran) {
    	getRowSpan(data, tableName, bMain, dashBoard, isFran, true);
    }
    
    private void getRowSpan( SequenceMap data , String tableName, boolean bMain, String dashBoard,boolean isFran, boolean getForeigns)
    {
    	
    	
    	
	   
	    boolean includeFran = false;
		if(bMain && ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")))) {
			ArrayList sArrSelectedFieldList	= null;
        
            String sGetSelected	= request.getParameter("getSelectedField");
            String [] sArrSelectedField = null;

		    if (sGetSelected != null)
            {
		    	sArrSelectedFieldList = new ArrayList();
		    	sArrSelectedField = request.getParameterValues(tableName + 1);
			    for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
			    	sArrSelectedFieldList.add(sArrSelectedField[i]);
				}
            }

            int size = 0;

            if (sGetSelected != null)
            {
			    if (sArrSelectedField != null)
                {
					size = sArrSelectedField.length;
				}
		    }

            
			    for (int k = 0; k < size ; k++)
                {

                    if (sGetSelected != null )
                    {
                    	
                    	if(("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag"))) && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
                    		includeFran = true;
    					}
                    }
                    
                }
    	
    	
		}
    	
    	
    	
    	
    	
    	
    	
    	
    	
		Iterator iterRow = null;
		if(data != null) {
			iterRow = data.keys().iterator();
		}

		if(iterRow != null)
		while (iterRow.hasNext())
        {
			SequenceMap	row	= (SequenceMap) data.get(iterRow.next());
			FieldMappings mappings = null;
			mappings = DBUtil.getInstance().getFieldMappings(tableName);
			SequenceMap foreignTables = null;
			
            if (mappings != null)
            {
                // Modified by Anuj 26-Nov-2004
                if (bMain){
                	if(isFran) {
                		foreignTables = selFranTables;
                	} else {
                		foreignTables = SequenceMapFactory.getCopySequenceMap(selForeignTables);
                		if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )) {
                		getForeigns = false;
                		if(includeFran)
                		foreignTables.insert(0, "franchisee", new ForeignTable(null, null));
                	}
                	}
                }
                else if(getForeigns) {
                	foreignTables = mappings.getForeignTablesExportMap();//foreignTables = mappings.getForeignTablesMap();
                }
            }
			if (foreignTables != null && !foreignTables.isEmpty())
            {
			    Iterator it	= foreignTables.keys().iterator();
			  //Problem in Custom Report while do export starts
			    String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
				//Problem in Custom Report while do export ends
                while (it.hasNext())
                {
				    String sTAnchor	= (String) it.next();
				  //Problem in Custom Report while do export starts
				    if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(sTAnchor) && configuredTableAnchors.indexOf(sTAnchor)==-1){
						continue;
					}
				  //Problem in Custom Report while do export ends
				    if("fimEntityDetail".equals(tableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts
						if("address".equals(sTAnchor))
							continue;
						}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends
					//BOEFLY_INTEGRATION : START    //PROVEN_MATCH_INTEGRATION : START
					SequenceMap forTabMap = (SequenceMap) row.get(sTAnchor);
					if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )) {
						if("franchisee".equals(sTAnchor) && forTabMap==null) {
							continue;
						}
					}
					
					if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) ) && "franchisee".equals(sTAnchor)) {
						getRowSpan(forTabMap, sTAnchor, false, dashBoard, false, getForeigns);
					} else {
						getRowSpan(forTabMap, sTAnchor, false, dashBoard);
					}
                }
            }

            if (foreignTables == null || foreignTables.isEmpty())
            {
            	row.put("rowSpan" , "1");
            } else
            {
			    String rowSpan = "1";
				Iterator it			= foreignTables.keys().iterator();
				//Problem in Custom Report while do export starts
				String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
				//Problem in Custom Report while do export ends
                while (it.hasNext())
                {
				    String sTAnchor = (String) it.next();
				  //Problem in Custom Report while do export starts
				    if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(sTAnchor) && configuredTableAnchors.indexOf(sTAnchor)==-1){
				    	row.put("rowSpan" , "1");//Problem in Custom Report while do export
						continue;
					}
				  //Problem in Custom Report while do export ends
				    if("fimEntityDetail".equals(tableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere")) ){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts
						if("address".equals(sTAnchor)){
							continue;
						}
						}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends
					int rSpan   = 0;
					
					SequenceMap forMap = (SequenceMap)row.get(sTAnchor);
					if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )) {
						if("franchisee".equals(sTAnchor) && forMap==null) {
							continue;
						}
					}
					Iterator forMapIter = forMap.keys().iterator();
					
                    while (forMapIter.hasNext())
                    {
                    	String key = (String)forMapIter.next();
					    SequenceMap map = (SequenceMap) forMap.get(key);
						String span = (String) map.get("rowSpan");
						int s = map.keys().size();			//P_FS_B_15497 Starts
						if(s>1) {
							if(span!=null){
							rSpan = rSpan + Integer.parseInt(span);
							}
						}		//P_FS_B_15497 Ends
                    }
					if (rSpan > Integer.parseInt(rowSpan))
                    {
					    rowSpan = Integer.toString(rSpan);
                    }
					row.put("rowSpan" , rowSpan);
                }
            }
		}
	}

//  P_FIM_O_2008072310000465 change starts
    /** This methods chnge rowspan of different cell based on parent table
	 * @author Rajivs 
	 * @param SequenceMap data
	  * @param  String tableName
	  * @param  boolean bMain
	  * @param int rowSpanForMain
	  * @date 09/10/2008
	 */
    private void getReverseRowSpan (SequenceMap data , String tableName, boolean bMain,int rowSpanForMain){
    	getReverseRowSpan(data, tableName, bMain, rowSpanForMain, false);
    }
    private void getReverseRowSpan (SequenceMap data , String tableName, boolean bMain,int rowSpanForMain,boolean isFran){
    	getReverseRowSpan(data, tableName, bMain, rowSpanForMain, isFran, true);
    }
    private void getReverseRowSpan (SequenceMap data , String tableName, boolean bMain,int rowSpanForMain,boolean isFran, boolean getForeigns){
    	getReverseRowSpan(data, tableName, bMain, rowSpanForMain, isFran, getForeigns, -1);//Real-20150707-013 starts
    }
    private void getReverseRowSpan (SequenceMap data , String tableName, boolean bMain,int rowSpanForMain,boolean isFran, boolean getForeigns,int rowSpanForMainTable){//Real-20150707-013 ends
        Iterator iterRow			= null;
        if(data != null) {
        	iterRow			= data.keys().iterator();
        }
        int noOfRows=0;
        int rowcounter =0;
        if(data != null)
        	noOfRows=data.size();
        int orginalsapneformain=rowSpanForMain;
    
        if(iterRow != null )
        while(iterRow.hasNext()){
        	rowcounter++;
            SequenceMap	row					=(SequenceMap)data.get(iterRow.next());
            if(StringUtil.isValidNew((String) row.get("rowSpan"))){
            rowSpanForMain=Integer.parseInt((String) row.get("rowSpan"));
            }else{
            	rowSpanForMain=1;
            }
            
            FieldMappings mappings			= null;
            mappings						= DBUtil.getInstance().getFieldMappings(tableName);
            SequenceMap foreignTables		= null;
            if(mappings != null){
                
            	if (bMain){
            		rowSpanForMainTable = rowSpanForMain;//Real-20150707-013
                	if(isFran) {
                		foreignTables = selFranTables;
                	} else {
                		foreignTables = SequenceMapFactory.getCopySequenceMap(selForeignTables);
                		if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )) {
                		getForeigns = false;
                		foreignTables.insert(0, "franchisee", new ForeignTable(null, null));
                		}
                	}
                }
                else if(getForeigns) {
                	foreignTables = mappings.getForeignTablesExportMap();//foreignTables = mappings.getForeignTablesMap();
                }
            }
            
            if(foreignTables != null && !foreignTables.isEmpty()){
            	int calculatedRspan=rowSpanForMain;
            	if(noOfRows==rowcounter && rowcounter<orginalsapneformain && !"owners".equals(tableName)){//Problem in Custom Report while do export
                	 calculatedRspan=orginalsapneformain-rowcounter+1;
                	                	
                }
            	          	
                Iterator it			= foreignTables.keys().iterator();
              //Problem in Custom Report while do export starts
                String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
				Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
				//Problem in Custom Report while do export ends
                while(it.hasNext()){
                    String sTAnchor			= (String)it.next();
                  //Problem in Custom Report while do export starts
                    if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(sTAnchor) && configuredTableAnchors.indexOf(sTAnchor)==-1){
						continue;
					}
                  //Problem in Custom Report while do export ends
                    if("fimEntityDetail".equals(tableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere")) ){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts
						if("address".equals(sTAnchor))
							continue;
						}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends
                    SequenceMap forTabMap	= (SequenceMap)row.get(sTAnchor);
                    
                    if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )) {
                    	if("franchisee".equals(sTAnchor) && forTabMap==null) {
                    		continue;
                    	}
                    }
                    if(forTabMap.size()>1){
                    	rowcounter=rowcounter+forTabMap.size()-1;
                    	noOfRows=noOfRows+forTabMap.size()-1;
                    }
                    
                    
					
					if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) ) && "franchisee".equals(sTAnchor)) {
						getReverseRowSpan(forTabMap , sTAnchor, false,calculatedRspan,false,getForeigns);
					} else {
						getReverseRowSpan(forTabMap , sTAnchor, false,calculatedRspan,false,true,rowSpanForMainTable);//Real-20150707-013
					}
                    
                    
                    
                }//end for iterator while(foreign table) loop
            }//end if for checking foreign table = null
            
            //Real-20150707-013 starts
            if("fsLeadCompliance".equals(tableName) && rowSpanForMainTable != -1 && rowSpanForMain<rowSpanForMainTable){
            	row.put("rowSpan" , Integer.toString(rowSpanForMainTable));
            }
            //Real-20150707-013 ends
            if(foreignTables == null || foreignTables.isEmpty() || "cmCompanyInfo".equals(tableName) || "fimEntityDetail".equals(tableName)){//RightatHome-20160617-982
               
           
                if(noOfRows==rowcounter && rowcounter<orginalsapneformain){
                	int calculatedRspan=orginalsapneformain-rowcounter+1;
                	row.put("rowSpan" , Integer.toString(calculatedRspan));
                	
                }else{
                	 row.put("rowSpan" , "1");
                }
                
            }//end of if part
            else{
            //	do nothing
            }//end of else
            
        }//end of top iterator
    }//end of getRowSpan() method
  
//  P_FIM_O_2008072310000465 change ends  

//P_FIM_O_2008072310000465 change starts
// Rajivs changed on 8/7/2008
 	

	
	/** This methods returns Ordered list
	 * @author Rajivs 
	 * @param ArrayList list
	  * @param   String sTableName
	  * @param   int count
	  * @param  boolean bMain
	 */


	public int getOrderedList(ArrayList list , String sTableName , int count, boolean bMain){
		FieldMappings mappings		= null;
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		boolean includeFran = false;
		ArrayList sArrSelectedFieldList	= null;
		if(mappings != null){
			SequenceMap sArrTableFields		= mappings.getAllFields();
			String sGetSelected				= request.getParameter("getSelectedField");
			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				sArrSelectedField			= request.getParameterValues(sTableName + count);
				sArrSelectedFieldList = new ArrayList();
				for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
			    	sArrSelectedFieldList.add(sArrSelectedField[i]);
				}
			}
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
					}else{
						size						= sArrSelectedField.length;
					}
			}
			
	//added for Custom report data
		
		for(int k = 0; k < size ; k++){

			Field field				= (Field)sArrTableFields.get(k);
        	String tmpFld = "";
			if(sGetSelected != null ){
	        	
				if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1 ) {//P_CM_B_57781
					continue;
				}
				if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )  && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
					includeFran = true;
					continue;
				}
				if(sArrSelectedField[k]!= null && (sArrSelectedField[k]).indexOf(":")!= -1)
				{
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	logger.info("sArrSelectedField[i]====1sArrSelectedField[i]73==="+sArrSelectedField[k]);
		        	field = mappings.getField(tmpFld);
				}
				else
				{
					field = mappings.getField(sArrSelectedField[k]);
				}
				
				logger.info("sArrSelectedField[i]====1sArrSelectedField[i]73==="+sArrSelectedField[k]);
	        		}
			if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
			{
				continue;
			}
			boolean bExportTable	= field.isFieldExportable();
			if(bExportTable){
			incluedinlist=true;
			}
			
		}
		}
		
		HeaderMap[] hMap = mappings.getHeaderMap();
   		for(HeaderMap h:hMap) {
   			
   			DocumentMap[] docMaps = null;
   			HeaderField hFld = h.getHeaderFields();
   			Boolean isTabularSection=hFld.isTabularSection();
   			if(isTabularSection){
   				continue;
   			}
			Documents[] docs =  hFld.getDocuments();
			
			
			if(docs != null && docs.length > 0) {
					docMaps = docs[0].getDocumentMaps();
					if(docMaps != null && docMaps.length > 0) {
						for(DocumentMap dMap : docMaps) {
							SequenceMap docMap  = dMap.getDocumentFieldMap();
							
							if(sArrSelectedFieldList !=null) {
								if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
									incluedinlist=true;
								}
							} else {
								incluedinlist=true;
							}
						}
					}
			}
   		}
		
		if(incluedinlist && size!=0){
		list.add(sTableName+count);
		}
		
		count++;
		//get the FieldMapping object for the module's main table
		HttpSession session = StrutsUtil.getHttpSession();
		if (bMain && "fim".equals(menuName) && ( "3".equals(session.getAttribute("fimFlag")))) {
		if(bMain && PortalUtils.hasDependentAddressTable(sTableName,request)){
			FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(sTableName);
			HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
			DependentTable dTable = null;
			for(HeaderMap h:headerMapArr) {
				HeaderField hFld = h.getHeaderFields();
				DependentTable[] dependenTables = hFld.getDependentTables();
				if(dependenTables!=null && dependenTables.length>0){
					int dependantTableSize = dependenTables.length;
					for(int ds=0;ds<dependantTableSize;ds++){
						dTable =  dependenTables[ds];
						if(dTable!=null && "address".equals(dTable.getTableAnchor())){
							count = count+1;
						}
					}
				}
			}
		}
		}
		//Modified by Anuj 26-Nov-2004
		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();//smForeignTables		= mappings.getForeignTablesMap();

		if(includeFran ) {
			list.add("franchisee");
		}
		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				if("fimEntityDetail".equals(sTableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT starts //BBP-20150831-675 starts
					if("address".equals(foreignTable.getName()))
						continue;
					}//P_ENH_CUSTOM_REPORT_ADDRESS_SPLIT ends //BBP-20150831-675 ends
				if(!foreignTable.isExportable()) {
					continue;
				}
				count = getOrderedList(list , foreignTable.getName() , count++, false);
				//P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									count = count+1;
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
				}//for loop
		}//end if null check
		/*if (bMain && "fim".equals(menuName) && ("2".equals(session.getAttribute("fimFlag")) || "3".equals(session.getAttribute("fimFlag")))) {
			String sGetSelected	= request.getParameter("getSelectedField");
	    	String[] sArrSelectedField = null;
	            smForeignTables	= selFranTables;
	            boolean isFranFieldSelected = false;
				int tableAliasCount = count;
				if(smForeignTables != null) {
				ArrayList<String> franTableList = (ArrayList<String>) smForeignTables.keys();
				// get the selected field names if sGetSelected is not null
				if (sGetSelected != null)
				{
					for(String franTab:franTableList) {
						
						if(request.getParameterValues(franTab + tableAliasCount)!=null) {
							isFranFieldSelected = true;
							break;
						}
						tableAliasCount++;
					}
				} else {
					isFranFieldSelected = true;
				}
				}
		
		if(smForeignTables != null ){
			int sizeForTable				= smForeignTables.size();
			list.add("muFranchisee");
			//list.add("franMuOwner");
			int franCount = count;
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				if(!foreignTable.isExportable()) {
					continue;
				}
				count = getOrderedList(list , foreignTable.getName() , franCount++, false);
				//P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									count = count+1;
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
				}//for loop
		}
		}*/
		
		
		return count;
	}
	public void getFranList(ArrayList list , String sTableName , boolean bMain){
		int count = (Integer)ParamResolver.getResolver().get("franListCount");
		
		SequenceMap smForeignTables		= null;
		HttpSession session = StrutsUtil.getHttpSession();
		if (bMain && "fim".equals(menuName) && ("2".equals(session.getAttribute("fimFlag")) || "3".equals(session.getAttribute("fimFlag")))) {
			String sGetSelected	= request.getParameter("getSelectedField");
	    	String[] sArrSelectedField = null;
	            smForeignTables	= selFranTables;
	            
		
		if(smForeignTables != null ){
			int sizeForTable				= smForeignTables.size();
			list.add("muFranchisee");
			//list.add("franMuOwner");
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				if(!foreignTable.isExportable()) {
					continue;
				}
				getFranOrderedList(list , foreignTable.getName() ,false);
				count = (Integer)ParamResolver.getResolver().get("franListCount");
				//P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									count = count+1;
									ParamResolver.getResolver().put("franListCount",count);
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
				}//for loop
		}
		}
	}
	
	
	public void getFranOrderedList(ArrayList list , String sTableName , boolean bMain){
		int count = (Integer)ParamResolver.getResolver().get("franListCount");
		FieldMappings mappings		= null;
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		boolean includeFran = false;
		ArrayList sArrSelectedFieldList	= null;
		if(mappings != null){
			SequenceMap sArrTableFields		= mappings.getAllFields();
			String sGetSelected				= request.getParameter("getSelectedField");
			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				sArrSelectedField			= request.getParameterValues(sTableName + count);
				sArrSelectedFieldList = new ArrayList();
				for (int i = 0; sArrSelectedField !=null && i < sArrSelectedField.length; i++) {
			    	sArrSelectedFieldList.add(sArrSelectedField[i]);
				}
			}
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
					}else{
						size						= sArrSelectedField.length;
					}
			}
			
	//added for Custom report data
		
		for(int k = 0; k < size ; k++){

			Field field				= (Field)sArrTableFields.get(k);
        	String tmpFld = "";
			if(sGetSelected != null ){
	        	
				if(sArrSelectedField[k].indexOf("fimDocuments_")!=-1 || sArrSelectedField[k].indexOf("cmDocuments_")!=-1 || sArrSelectedField[k].indexOf("fsDocuments_")!=-1 || sArrSelectedField[k].indexOf("contactHistoryDocuments_")!=-1 || sArrSelectedField[k].indexOf("areaDocuments_")!=-1 || sArrSelectedField[k].indexOf("fimMuDocuments_")!=-1 ) {//P_CM_B_57781
					continue;
				}
				if( ("2".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) || "3".equals((String) StrutsUtil.getHttpSession().getAttribute("fimFlag")) )  && sArrSelectedField[k].indexOf("franchiseeName") !=-1) {
					includeFran = true;
					continue;
				}
				if(sArrSelectedField[k]!= null && (sArrSelectedField[k]).indexOf(":")!= -1)
				{
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	logger.info("sArrSelectedField[i]====1sArrSelectedField[i]73==="+sArrSelectedField[k]);
		        	field = mappings.getField(tmpFld);
				}
				else
				{
					field = mappings.getField(sArrSelectedField[k]);
				}
				
				logger.info("sArrSelectedField[i]====1sArrSelectedField[i]73==="+sArrSelectedField[k]);
	        		}
			if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
			{
				continue;
			}
			boolean bExportTable	= field.isFieldExportable();
			if(bExportTable){
			incluedinlist=true;
			}
			
		}
		}
		
		HeaderMap[] hMap = mappings.getHeaderMap();
   		for(HeaderMap h:hMap) {
   			
   			DocumentMap[] docMaps = null;
   			HeaderField hFld = h.getHeaderFields();
   			Boolean isTabularSection=hFld.isTabularSection();
   			if(isTabularSection){
   				continue;
   			}
			Documents[] docs =  hFld.getDocuments();
			
			
			if(docs != null && docs.length > 0) {
					docMaps = docs[0].getDocumentMaps();
					if(docMaps != null && docMaps.length > 0) {
						for(DocumentMap dMap : docMaps) {
							SequenceMap docMap  = dMap.getDocumentFieldMap();
							
							if(sArrSelectedFieldList !=null) {
								if(sArrSelectedFieldList.contains(docs[0].getDocumentTableAnchor()+"_"+docMap.get("name"))) {//P_CM_B_57781
									incluedinlist=true;
								}
							} else {
								incluedinlist=true;
							}
						}
					}
			}
   		}
		
		if(incluedinlist && size!=0){
		list.add(sTableName+count);
		}
		ParamResolver.getResolver().put("franListCount",++count);
		//get the FieldMapping object for the module's main table
		HttpSession session = StrutsUtil.getHttpSession();
		if (bMain && "fim".equals(menuName) && ( "3".equals(session.getAttribute("fimFlag")))) {
		if(bMain && PortalUtils.hasDependentAddressTable(sTableName,request)){
			FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(sTableName);
			HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
			DependentTable dTable = null;
			for(HeaderMap h:headerMapArr) {
				HeaderField hFld = h.getHeaderFields();
				DependentTable[] dependenTables = hFld.getDependentTables();
				if(dependenTables!=null && dependenTables.length>0){
					int dependantTableSize = dependenTables.length;
					for(int ds=0;ds<dependantTableSize;ds++){
						dTable =  dependenTables[ds];
						if(dTable!=null && "address".equals(dTable.getTableAnchor())){
							count = count+1;
							ParamResolver.getResolver().put("franListCount",count);
						}
					}
				}
			}
		}
		}
		//Modified by Anuj 26-Nov-2004
		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();//smForeignTables		= mappings.getForeignTablesMap();

		
		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				if(!foreignTable.isExportable()) {
					continue;
				}
				getFranOrderedList(list , foreignTable.getName() , false);
				count = (Integer)ParamResolver.getResolver().get("franListCount");
				//P_E_Fields-20130905-035 starts
				if(PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					FieldMappings tempMappings = DBUtil.getInstance().getFieldMappings(foreignTable.getName());
					HeaderMap[] headerMapArr = tempMappings.getHeaderMap();
					DependentTable dTable = null;
					for(HeaderMap h:headerMapArr) {
						HeaderField hFld = h.getHeaderFields();
						DependentTable[] dependenTables = hFld.getDependentTables();
						if(dependenTables!=null && dependenTables.length>0){
							int dependantTableSize = dependenTables.length;
							for(int ds=0;ds<dependantTableSize;ds++){
								dTable =  dependenTables[ds];
								if(dTable!=null && "address".equals(dTable.getTableAnchor())){
									count = count+1;
									ParamResolver.getResolver().put("franListCount",count);
								}
							}
						}
					}
				}
				//P_E_Fields-20130905-035 ends
				}//for loop
		}//end if null check
	}
	
	
	/**
	 * For Franchise custom report changes for CRM
	 * @author abhishek gupta
	 * @date 22 sep 2009
	 * @param list
	 * @param sTableName
	 * @param count
	 * @param bMain
	 * @return
	 */
	public int getNewOrderedList(ArrayList list , String sTableName , int count, boolean bMain){
		return getNewOrderedList(list , sTableName , count, bMain, null);
	}
	//ENH_MODULE_CUSTOM_TABS starts
	public int getNewOrderedList(ArrayList list , String sTableName , int count, boolean bMain, String dashBoard){
		return getNewOrderedList(list , sTableName , count, bMain, dashBoard, false);
	}
	public int getNewOrderedList(ArrayList list , String sTableName , int count, boolean bMain, String dashBoard, boolean fromReport){
		return getNewOrderedList(list , sTableName , count, bMain, dashBoard, fromReport,null,null,FieldNames.EMPTY_STRING);
	}
	//P_E_Fields-20130905-035 starts
	public int getNewOrderedList(ArrayList list , String sTableName , int count, boolean bMain, String dashBoard, boolean fromReport,HeaderField hFld,FieldMappings parentMappings,String headerName)
	{
	//ENH_MODULE_CUSTOM_TABS ends
		FieldMappings mappings		= null;
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		//P_E_Fields-20130905-035 starts
		boolean processHeaders = false;
		if(hFld!=null){
			processHeaders = true;
		}
		//P_E_Fields-20130905-035 ends
		if(mappings != null){
			SequenceMap sArrTableFields		= null;//mappings.getAllFields();
			if(processHeaders){
        		sArrTableFields = mappings.getDependentTblFields(hFld);
        	}else{
        		sArrTableFields = mappings.getAllFields();
        	}
			String sGetSelected				= request.getParameter("getSelectedField");
			if(sGetSelected == null) {
				sGetSelected				= (String)request.getAttribute("getSelectedField");
			}
			logger.info("sGetSelected=========="+sGetSelected);
			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				if(dashBoard != null) {
					sArrSelectedField			= (String [])request.getAttribute(sTableName + count);
					logger.info("sArrSelectedField=========="+sArrSelectedField);
				} else {
					sArrSelectedField			= request.getParameterValues(sTableName + count);
				}
			}
			//int size						= sArrTableFields.size();
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
					}else{
						size						= sArrSelectedField.length;
					}
			}
			logger.info("size=========="+size);
	//added for Custom report data
			for(int k = 0; k < size ; k++){
	
				Field field				= (Field)sArrTableFields.get(k);
	        	String tmpFld = "";
	        	String tmpFldLbl = "";
	
				if(sGetSelected != null ){
					logger.info("sArrSelectedField["+k+"]=========="+sArrSelectedField[k]);
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	tmpFldLbl = sArrSelectedField[k].split(":")[1];
		        	//field = mappings.getField(tmpFld);
		        	if(processHeaders){
		        		field = parentMappings.getOtherTableField(tmpFld.trim());//P_E_Fields-20130905-035
		        	}else{
		        		field = mappings.getField(tmpFld.trim());//P_E_Fields-20130905-035
		        	}
		        	if(field ==null || !field.isActiveField())		//P_BUGS_FIM Points starts
                    {
						continue;
                    }		//P_BUGS_FIM Points ends
				}
				if(excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))	//P_B_MBO_EXPORT_77802
				{
					continue;
				}
				boolean bExportTable	= field.isFieldExportable();
				if(bExportTable){
					incluedinlist=true;
				}
			}
			
			
			String reportType = request.getParameter("reportType");
			if(reportType == null) {
				reportType		= (String)request.getAttribute("reportType");
			}
			
			if(reportType != null && ("S".equals(reportType) || "SG".equals(reportType))) {
				String tempSummryVal = request.getParameter(sTableName+":COUNT:ALL");
				if(tempSummryVal == null) {
					tempSummryVal = (String)request.getAttribute(sTableName+":COUNT:ALL");
				}
				if(tempSummryVal != null && !"null".equals(tempSummryVal.trim())) {
					incluedinlist=true;
				}
				
				Field[] fields	= null;//mappings.getSummaryFieldsArray();
				if(processHeaders){
	        		fields = mappings.getSectionTablesFieldsArrayWithOrderBy(hFld, true);//P_E_Fields-20130905-035
	        	}else{
	        		fields = mappings.getSummaryFieldsArray();//P_E_Fields-20130905-035
	        	}
				for (int k = 0; k < fields.length; k++)
				{
					String sSearchField		= fields[k].getSearchField();
					
					tempSummryVal = request.getParameter(sTableName+":SUM:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":SUM:"+fields[k].getFieldName());
					}
					
					if(tempSummryVal != null && !"null".equals(tempSummryVal.trim())) {
						incluedinlist=true;
					}
					tempSummryVal = request.getParameter(sTableName+":AVG:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":AVG:"+fields[k].getFieldName());
					}
					
					if(tempSummryVal != null && !"null".equals(tempSummryVal.trim())) {
						incluedinlist=true;
					}
					tempSummryVal = request.getParameter(sTableName+":MAX:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MAX:"+fields[k].getFieldName());
					}
					
					if(tempSummryVal != null && !"null".equals(tempSummryVal.trim())) {
						incluedinlist=true;
					}
					tempSummryVal = request.getParameter(sTableName+":MIN:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MIN:"+fields[k].getFieldName());
					}
					
					if(tempSummryVal != null && !"null".equals(tempSummryVal.trim())) {
						incluedinlist=true;
					}

				}
			}
		}
				
	//		list.add(sTableName+count);
		
		//if(size!=0){
		//if(incluedinlist && size!=0){
		if(incluedinlist){
			if(fromReport)
			{
				list.add(sTableName+"-$$-"+count);//ENH_MODULE_CUSTOM_TABS
			}
			else
			{
				list.add(sTableName+count);
			}
		}
		count++;
		//get the FieldMapping object for the module's main table

		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();

		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				//tindru-20151119-007 starts 
				if("fimEntityDetail".equals(sTableName) && StringUtil.isValidNew((String)request.getAttribute("fromWhere")) && "export".equals((String)request.getAttribute("fromWhere"))){
 					if("address".equals(foreignTable.getName()))
 						continue;
 				}
				//tindru-20151119-007 ends
				//P_E_Fields-20130905-035
				count = getNewOrderedList(list , foreignTable.getName() , count++, false, dashBoard,fromReport);
				if(dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					includeHeaderOrderList(list,null,foreignTable.getName() , count++, false, dashBoard,fromReport,null,"getNewOrderedList");
				}
				//P_E_Fields-20130905-035
			}//for loop
		}//end if null check
		return count;
	}
	
	/**
	 * //P_E_Fields-20130905-035
	 * @param list
	 * @param listBuffer
	 * @param tableName
	 * @param count
	 * @param b
	 * @param dashBoard
	 * @param fromReport
	 * @param reportType
	 * @param fromWhere
	 */
	private void includeHeaderOrderList(ArrayList list,StringBuffer listBuffer,String tableName,int count, boolean b,String dashBoard,boolean fromReport,String reportType,String fromWhere) 
    {
    	FieldMappings mappings = DBUtil.getInstance().getFieldMappings(tableName);
    	HeaderMap[] hMap = mappings.getHeaderMap();
		DependentTable dTable = null;
		HeaderField hFld = null;
		String headerName = "";
		Set<String> selectedAlias = new HashSet<String>();
		for(HeaderMap h:hMap) {
			hFld = h.getHeaderFields();
			headerName = h.getName();
			DependentTable[] dependenTables = hFld.getDependentTables();
			if(dependenTables!=null && dependenTables.length>0){
				int dependantTableSize = dependenTables.length;
				for(int i=0;i<dependantTableSize;i++){
					selectedAlias = dependentFieldsMap.get(tableName);
					dTable =  dependenTables[i];
					if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
						if("getNewOrderedList".equals(fromWhere)){
							count = getNewOrderedList(list , dTable.getTableAnchor() , count++, false, dashBoard,fromReport,hFld,mappings,headerName);
						}else if("getNewMapItemForReportList".equals(fromWhere)){
							getNewMapItemForReportList(listBuffer, dTable.getTableAnchor(), count++, false, reportType,dashBoard,hFld,mappings,headerName);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * @author abhishek gupta
	 * @date 12 oct 2009
	 * @param list
	 * @param sTableName
	 * @param count
	 * @param bMain
	 */
	public void getNewColumnList(StringBuffer list , String sTableName , int count, boolean bMain){
		getNewColumnList(list , sTableName , count, bMain, null);
	}
	public void getNewColumnList(StringBuffer list , String sTableName , int count, boolean bMain, String dashBoard){
		getNewColumnList(list , sTableName , count, bMain, dashBoard,null,null,null);
	}
	//P_E_Fields-20130905-035
	public void getNewColumnList(StringBuffer list , String sTableName , int count, boolean bMain, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName){
		FieldMappings mappings		= null;
		
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		boolean processHeaders = false;
		if(hFld!=null){
			processHeaders = true;
		}
		if(mappings != null){
			SequenceMap sArrTableFields		= mappings.getAllFields();
			String sGetSelected				= request.getParameter("getSelectedField");
			if(sGetSelected == null) {
				sGetSelected		= (String)request.getAttribute("getSelectedField");
			}

			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				if(dashBoard != null) {
					sArrSelectedField			= (String [])request.getAttribute(sTableName + count);
				} else {
					sArrSelectedField			= request.getParameterValues(sTableName + count);
				}
			}
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
				}else{
						size						= sArrSelectedField.length;
				}
			}
		
			for(int k = 0; k < size ; k++){
	
				Field field				= (Field)sArrTableFields.get(k);
	        	String tmpFld = "";
	        	String tmpFldLbl = "";
	
				if(sGetSelected != null ){
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	tmpFldLbl = sArrSelectedField[k].split(":")[1];
		        	logger.info("tmpFldLbl======"+tmpFldLbl);
		        	if(processHeaders){
		        		field = parentMappings.getOtherTableField(tmpFld);//P_E_Fields-20130905-035
		        	}else{
		        		field = mappings.getField(tmpFld);//P_E_Fields-20130905-035
		        	}
				}
				//P_CM_B_37878 : starts
				if(field==null || !field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
                	continue;
                }
				//P_CM_B_37878 : ends
				boolean bExportTable	= field.isFieldExportable();
				if(bExportTable){
					
					
					if("Date".equalsIgnoreCase(field.getDataType())) {
						String sFormtFieldNew = request.getParameter(sTableName+":"+field.getFieldName()+":FORMAT");
						if(sFormtFieldNew == null) {
							sFormtFieldNew		= (String)request.getAttribute(sTableName+":"+field.getFieldName()+":FORMAT");
						}
						
						String sFormtField = "";
						String sAgeField = "";
						if(sFormtFieldNew != null && !"".equals(sFormtFieldNew.trim())) {
							if(sFormtFieldNew.split(":").length == 2) {
								sFormtField = sFormtFieldNew.split(":")[0].trim();
								sAgeField = sFormtFieldNew.split(":")[1].trim();
							} else {
								sFormtField = sFormtFieldNew.trim();
							}
						} 
						/**
						 * @author abhishek gupta
						 * @date 29 dec 2009
						 * Bug id : 54839
						 * For custom fields always get label name from parameter values
						 */
						if(sFormtField != null && !"".equals(sFormtField.trim())) {
							if("41".equals(sFormtField.trim())) {
								if(!"".equals(sAgeField.trim())) {
									list.append(", ").append(tmpFldLbl + "(Weekly by Age)");
								} else {
									list.append(", ").append(tmpFldLbl + "(Weekly)");
								}
							} else if("42".equals(sFormtField.trim())) {
								if(!"".equals(sAgeField.trim())) {
									list.append(", ").append(tmpFldLbl+ "(Monthly by Age)");
								} else {
									list.append(", ").append(tmpFldLbl+ "(Monthly)");
								}
							} else if("43".equals(sFormtField.trim())) {
								if(!"".equals(sAgeField.trim())) {
									list.append(", ").append(tmpFldLbl+ "(Yearly by Age)");
								} else {
									list.append(", ").append(tmpFldLbl+ "(Yearly)");
								}
							} else {
								if(!"".equals(sAgeField.trim())) {
									list.append(", ").append(tmpFldLbl+ "(Daily by Age)");
								} else {
									list.append(", ").append(tmpFldLbl+ "(Daily)");
								}
							}
						} else {
							list.append(", ").append(tmpFldLbl);
						}
					} else {
						list.append(", ").append(tmpFldLbl);
					}
				}
				
			}
		}
				
		count++;

		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();

		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do exportends
					getNewColumnList(list , foreignTable.getName() , count++, false, dashBoard);
			}//for loop
		}//end if null check
		return;
	}
	/**
	 * Purpose of adding this function is to retrieve all column used to display in custom report report
	 * This specially work for summation type report in which group by fields and summaries fields both  
	 * @author abhishek gupta
	 * @date 21 oct 2009
	 * @param list
	 * @param sTableName
	 * @param count
	 * @param bMain
	 * @param reportType
	 */
	public void getNewColumnSummryList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType){
		getNewColumnSummryList(list , sTableName , count, bMain, reportType, null);
	}
	public void getNewColumnSummryList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType,String dashBoard){
		getNewColumnSummryList(list , sTableName , count, bMain, reportType, dashBoard,null,null,FieldNames.EMPTY_STRING);
	}
	//P_E_Fields-20130905-035
	public void getNewColumnSummryList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName){
		FieldMappings mappings		= null;
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		boolean processHeaders = false;
		if(hFld!=null){
			processHeaders = true;//P_E_Fields-20130905-035
		}
		if(mappings != null){
			SequenceMap sArrTableFields		= null;//mappings.getAllFields();
			//P_E_Fields-20130905-035
        	if(processHeaders){
        		sArrTableFields = mappings.getDependentTblFields(hFld);
        	}else{
        		sArrTableFields = mappings.getAllFields();
        	}
        	//P_E_Fields-20130905-035
			String sGetSelected				= request.getParameter("getSelectedField");
			if(sGetSelected == null) {
				sGetSelected		= (String)request.getAttribute("getSelectedField");
			}

			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				if(dashBoard != null) {
					sArrSelectedField			= (String [])request.getAttribute(sTableName + count);
				} else {
					sArrSelectedField			= request.getParameterValues(sTableName + count);
				}
			}
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
				}else{
						size						= sArrSelectedField.length;
				}
			}
			String tmpDisplayName = "";
			for(int k = 0; k < size ; k++){
	
				Field field				= (Field)sArrTableFields.get(k);
	        	String tmpFld = "";
	        	String tmpFldLbl = "";
	
				if(sGetSelected != null ){
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	tmpFldLbl = sArrSelectedField[k].split(":")[1];
		        	if(processHeaders){
		        		field = parentMappings.getOtherTableField(tmpFld);//P_E_Fields-20130905-035
		        	}else{
		        		field = mappings.getField(tmpFld);
		        	}
				}
				if(field==null || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
					continue;
				}
				boolean bExportTable	= field.isFieldExportable();
				if(bExportTable){
					if(StringUtil.isValid(field.getDisplayExportName())) {
						tmpDisplayName = field.getDisplayExportName();
					} else {
						tmpDisplayName = field.getDisplayName();
					}
					String ageVal = request.getParameter(sTableName+":"+field.getFieldName()+":FORMAT");
					if(ageVal == null) {
						ageVal		= (String)request.getAttribute(sTableName+":"+field.getFieldName()+":FORMAT");
					}
					
					if(ageVal != null && ageVal.split(":").length == 2) {
						list.append(", ").append( LanguageUtil.getString(tmpDisplayName)+" (By Age)");
					} else {
						list.append(", ").append( LanguageUtil.getString(tmpDisplayName));
					}
				}
				
			}
			if(reportType != null && ("S".equals(reportType) || "SG".equals(reportType))) {
				String tempSummryVal = request.getParameter(sTableName+":COUNT:ALL");
				if(tempSummryVal == null) {
					tempSummryVal		= (String)request.getAttribute(sTableName+":COUNT:ALL");
				}
    			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
    				if(!"".equals(tempSummryVal.trim())) {
    					list.append(", ").append( LanguageUtil.getString(tempSummryVal.trim()));
    				} else {
    					list.append(", ").append("COUNT");
    				}
    				
    			}
    			String customDisplayName = "";
    			Field[] fields	= null;//mappings.getSummaryFieldsArray();
    			if(processHeaders){
    				fields = mappings.getSectionTablesFieldsArrayWithOrderBy(hFld, true);//P_E_Fields-20130905-035
    			}else{
    				fields = mappings.getSummaryFieldsArray();
    			}
				for (int k = 0; k < fields.length; k++)
				{
					String sSearchField		= fields[k].getSearchField();
					if(processHeaders){
						sSearchField = "nonBuildFld";//P_E_Fields-20130905-035
					}
					// continue if field is not specified as search field
					if(sSearchField == null){
						continue;
					}
					if(StringUtil.isValid(fields[k].getDisplayExportName())) {
						customDisplayName = fields[k].getDisplayExportName();
					} else {
						customDisplayName = fields[k].getDisplayName();
					}

					tempSummryVal = request.getParameter(sTableName+":SUM:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":SUM:"+fields[k].getFieldName());
					}
					
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("SUM:"+tempSummryVal.trim());
        				} else {
        					list.append(", ").append("SUM:"+customDisplayName);
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":AVG:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":AVG:"+fields[k].getFieldName());
					}
					
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("AVG:"+tempSummryVal.trim());
        				} else {
        					list.append(", ").append("AVG:"+customDisplayName);
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":MAX:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MAX:"+fields[k].getFieldName());
					}
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("MAX:"+tempSummryVal.trim());
        				} else {
        					list.append(", ").append("MAX:"+customDisplayName);
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":MIN:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MIN:"+fields[k].getFieldName());
					}
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("MIN:"+tempSummryVal.trim());
        				} else {
        					list.append(", ").append("MIN:"+customDisplayName);
        				}
        			}
				}
			}
			
		}
				
		count++;

		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();

		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				//P_E_Fields-20130905-035
				getNewColumnSummryList(list , foreignTable.getName() , count++, false, reportType, dashBoard);
				if(dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					getHeaderForDependents(null, null, foreignTable.getName(), count++, false, dashBoard,reportType,list,"getNewColumnSummryList");
				}
				//P_E_Fields-20130905-035
				}//for loop
		}//end if null check
		return;
	}
	/**
	 * Added to check column used for chart
	 * @author abhishek gupta
	 * @param list
	 * @param sTableName
	 * @param count
	 * @param bMain
	 * @param reportType
	 * 
	 * @date 24 Feb 2010
	 * Handle Matrix report generation with two group, then allow field name along group
	 * So that make difference between dependent / non dependent group of respective field in same table 
	 * or from different tables 
	 */
	public void getNewMapItemForReportList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType){
		getNewMapItemForReportList(list , sTableName , count, bMain, reportType, null);		
	}
	public void getNewMapItemForReportList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType, String dashBoard){
		getNewMapItemForReportList(list , sTableName , count, bMain, reportType, dashBoard,null,null,FieldNames.EMPTY_STRING);
	}
	//P_E_Fields-20130905-035
	public void getNewMapItemForReportList(StringBuffer list , String sTableName , int count, boolean bMain, String reportType, String dashBoard,HeaderField hFld,FieldMappings parentMappings,String headerName)
	{
		FieldMappings mappings		= null;
		mappings					= DBUtil.getInstance().getFieldMappings(sTableName);
		int size=0;
		boolean incluedinlist=false;
		boolean processHeaders = false;
		if(hFld!=null){
			processHeaders = true;//P_E_Fields-20130905-035
		}
		if(mappings != null){
			SequenceMap sArrTableFields		= null;//mappings.getAllFields();
			if(processHeaders){
        		sArrTableFields = mappings.getDependentTblFields(hFld);//P_E_Fields-20130905-035
        	}else{
        		sArrTableFields = mappings.getAllFields();
        	}
			String sGetSelected				= request.getParameter("getSelectedField");
			if(sGetSelected == null) {
				sGetSelected		= (String)request.getAttribute("getSelectedField");
			}

			String [] sArrSelectedField		= null;
			//get the selected field names if sGetSelected is not null
			if(sGetSelected != null){
				if(dashBoard != null) {
					sArrSelectedField			= (String [])request.getAttribute(sTableName + count);
				} else {
					sArrSelectedField			= request.getParameterValues(sTableName + count);
				}
			}
			size						= sArrTableFields.size();
			if(sGetSelected != null){
				if(sArrSelectedField == null){
						size = 0 ;
				}else{
						size						= sArrSelectedField.length;
				}
			}
		
			for(int k = 0; k < size ; k++){
	
				Field field				= (Field)sArrTableFields.get(k);
	        	String tmpFld = "";
	        	String tmpFldLbl = "";
	
				if(sGetSelected != null ){
		        	tmpFld = sArrSelectedField[k].split(":")[0];
		        	tmpFldLbl = sArrSelectedField[k].split(":")[1];
		        	if(processHeaders){
		        		field = parentMappings.getOtherTableField(tmpFld);//P_E_Fields-20130905-035
		        	}else{
		        		field = mappings.getField(tmpFld);
		        	}
				}
				//P_CM_B_37878 : starts
				if(field==null || !field.isActiveField() || (excludeFieldsList!=null && excludeFieldsList.contains(field.getFieldName()))){	//P_B_MBO_EXPORT_77802
					continue;
				}
				//P_CM_B_37878 : ends
				boolean bExportTable	= field.isFieldExportable();
				/**
				 * Handle matrix report generation with two group, then allow field name along group
				 * So that make difference between dependent / non dependent group of respective field in same table 
				 * or from different tables 
				 * 
				 * Report Type MG is identity for Matrix report
				 */
				if(bExportTable){
					if("MG".equals(reportType)) {
						list.append(", ").append("GROUP#").append(sTableName).append("#").append(field.getFieldName());
					} else {
						list.append(", ").append("GROUP");
					}
				}
				
			}
			/**
			 * Report Type MG is identity for Matrix report
			 */
			if(reportType != null && ("SG".equals(reportType) || "MG".equals(reportType))) {
				String tempSummryVal = request.getParameter(sTableName+":COUNT:ALL");
				if(tempSummryVal == null) {
					tempSummryVal		= (String)request.getAttribute(sTableName+":COUNT:ALL");
				}
				
    			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
    				if(!"".equals(tempSummryVal.trim())) {
    					list.append(", ").append("COUNT");
    				} else {
    					list.append(", ").append("COUNT");
    				}
    				
    			}
    			Field[] fields = null;
    			//P_E_Fields-20130905-035
	        	if(processHeaders){
	        		fields	= mappings.getSectionTablesFieldsArrayWithOrderBy(hFld, true);
	        	}else{
	        		fields	= mappings.getSummaryFieldsArray();
	        	}
	        	//P_E_Fields-20130905-035
				for (int k = 0; k < fields.length; k++)
				{
					String sSearchField		= fields[k].getSearchField();
					// continue if field is not specified as search field
					if(sSearchField == null){
						continue;
					}
					tempSummryVal = request.getParameter(sTableName+":SUM:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":SUM:"+fields[k].getFieldName());
					}
					
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("AMOUNT");
        				} else {
        					list.append(", ").append("AMOUNT");
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":AVG:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":AVG:"+fields[k].getFieldName());
					}
					
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("AMOUNT");
        				} else {
        					list.append(", ").append("AMOUNT");
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":MAX:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MAX:"+fields[k].getFieldName());
					}
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("AMOUNT");
        				} else {
        					list.append(", ").append("AMOUNT");
        				}
        			}
					tempSummryVal = request.getParameter(sTableName+":MIN:"+fields[k].getFieldName());
					if(tempSummryVal == null) {
						tempSummryVal		= (String)request.getAttribute(sTableName+":MIN:"+fields[k].getFieldName());
					}
					
        			if(tempSummryVal != null && !"null".equals(tempSummryVal)) {
        				if(!"".equals(tempSummryVal.trim())) {
        					list.append(", ").append("AMOUNT");
        				} else {
        					list.append(", ").append("AMOUNT");
        				}
        			}
				}
			}
			
		}
				
		count++;

		SequenceMap smForeignTables		= null;
		if (bMain)
			smForeignTables		= selForeignTables;
		else
			smForeignTables		= mappings.getForeignTablesExportMap();

		if(smForeignTables != null){
			int sizeForTable				= smForeignTables.size();
			//Problem in Custom Report while do export starts
			String configuredTableAnchors=request.getParameter("configuredTableAnchors")!=null?request.getParameter("configuredTableAnchors"):"";
			Set<String> tabularSectionSet = (HashSet)getTabularSectionSet(mappings);
			//Problem in Custom Report while do export ends
			for(int i = 0 ; i < sizeForTable ; i++){
				ForeignTable foreignTable		= (ForeignTable)smForeignTables.get(i);
				//Problem in Custom Report while do export starts
				if(!"export".equals((String)request.getAttribute("fromWhere")) && tabularSectionSet.contains(foreignTable.getName()) && configuredTableAnchors.indexOf(foreignTable.getName())==-1){
					continue;
				}
				//Problem in Custom Report while do export ends
				//P_E_Fields-20130905-035
				getNewMapItemForReportList(list , foreignTable.getName() , count++, false, reportType, dashBoard);
				if(dependentFieldsMap.containsKey(foreignTable.getName()) && PortalUtils.hasDependentAddressTable(foreignTable.getName(),request)){
					includeHeaderOrderList(null,list, foreignTable.getName(), count++, false, dashBoard, false, reportType,"getNewMapItemForReportList");
				}
				//P_E_Fields-20130905-035
				}//for loop
		}//end if null check
		return;
	}
	
    // method to format field names to get node values
	// method to get a map of site location
	public HashMap getLocationMap(String query)
    {
	    HashMap locationMap	=new HashMap();
        ResultSet result=null;
		
        try
        {
		    result	= QueryUtil.getResult(query,null);
			
            while (result.next())
            {
				locationMap.put(result.getString(1),result.getString(2)+" , "+RegionMgr.newInstance().getRegionsDAO().getStateName(result.getString(3))+" ("+StringUtil.convertNull(result.getString(4))+")");
			}
		} catch(Exception e)
        {
        	logger.error("\nException in ExportDataCollector class in getLocationMap method :"+e.getMessage());

		} finally
        {
            try 
            {
			    if (result!=null)
				{
				    result = null;
				}
            } catch (Exception ex) 
			{
            	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java --> getLocationMap()"+ex);
			    
            }
        }

	    return locationMap;
	}

/**
		Added By Abishek Singhal 18 Sep 2006 to diplay  the total number of records start regarding OTRS-2006090510000105 of D&Y
		*/

	public String getNumberOfRecords(String query)
    {
	    String count = null;
        ResultSet result	=null;

        try
        {
            result	= QueryUtil.getResult(query,null);

            if (result!= null && result.next())
            {
				count = result.getString(1);
			}
		} catch(Exception e)
        {
        	logger.error("\nException in ExportDataCollector class in getNumberOfRecords method :"+e.getMessage());
	
		}

        try 
        {
            if (result != null)
            {
                result = null;
            }
        } catch (Exception ex) 
        {
        	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java --> getNumberOfRecords:", ex);
        }

	    return count;
	}
	
	/**
     * //P_E_Fields-20130905-035
     * Called for each dependent table
     * @param tableName
     * @param result
     * @param smData
     * @param b
     * @param dashBoard
     * @param exportResult
     * @param fromWhere
     */
	private void collectHeaderSearchData(String tableName,java.sql.ResultSet result, SequenceMap smData, boolean b,String dashBoard,Info exportResult,String fromWhere) {
		collectHeaderSearchData(tableName, result, smData, b, dashBoard, exportResult, fromWhere, false);
	}
    private void collectHeaderSearchData(String tableName,java.sql.ResultSet result, SequenceMap smData, boolean b,String dashBoard,Info exportResult,String fromWhere, boolean isMuFran) 
    {
    	FieldMappings mappings = DBUtil.getInstance().getFieldMappings(tableName);
    	HeaderMap[] hMap = mappings.getHeaderMap();
		DependentTable dTable = null;
		HeaderField hFld = null;
		String headerName = "";
		Set<String> selectedAlias = new HashSet<String>();
		for(HeaderMap h:hMap) {
			hFld = h.getHeaderFields();
			headerName = h.getName();
			DependentTable[] dependenTables = hFld.getDependentTables();
			if(dependenTables!=null && dependenTables.length>0){
				int dependantTableSize = dependenTables.length;
				for(int i=0;i<dependantTableSize;i++){
					if(isMuFran) {
						selectedAlias = this.dependentMuFranFieldsMap.get(tableName);
					} else {
						selectedAlias = this.dependentFieldsMap.get(tableName);
					}
					dTable =  dependenTables[i];
					if(selectedAlias!=null && selectedAlias.contains(dTable.getTableAliasName())){
						if (!smData.containsKey(headerName+dTable.getTableAnchor()))
	                    {
	                        smData.put(headerName+dTable.getTableAnchor(), new SequenceMap());
	                    }
						if("collectData".equals(fromWhere)){
							collectData(dTable.getTableAnchor(), exportResult, smData, false, headerName);
						}else if("collectSearchData".equals(fromWhere)){
							if("fim".equals(menuName) && "fimEntityDetail".equals(tableName) && selectedAlias!=null && selectedAlias.contains("address_0")){//Bug 62581
								collectSearchData(dTable.getTableAnchor(), result, smData, false, dashBoard,hFld,mappings,headerName);
							}
							//collectSearchData(dTable.getTableAnchor(), result, smData, false, dashBoard,hFld,mappings,headerName);
						}
					}
				}
			}
		}
	}
    
    public String getActualName(String moduleName, String fieldNo)
    {
    	if (moduleName != null && moduleName.equalsIgnoreCase("fimFinancialIfFinancialsExport"))
        {
            moduleName = "fimFinancial";
        }

		if (moduleName != null && moduleName.indexOf("Export") != -1)
        {
            moduleName = moduleName.substring(0, moduleName.indexOf("Export"));
        }

        if (moduleName != null && moduleName.equalsIgnoreCase("franchisees"))
        {
            moduleName = "franchisee";
        }
        if (moduleName != null && moduleName.indexOf("fs")!=-1)
        {
        	moduleName = "fs";
        } 
        String query = null;

        if (moduleName != null && (moduleName.equals("franchisee") || moduleName.indexOf("fim") != -1))
        {
            query = "SELECT FCF.CUSTOM_FORM_ID, FCFLD.DISPLAY_NAME FROM FIM_CUSTOMIZATION_FORM FCF JOIN FIM_CUSTOMIZATION_FIELD FCFLD ON FCFLD.CUSTOM_FORM_ID=FCF.CUSTOM_FORM_ID WHERE FCFLD.FIELD_NO=? AND FCF.TABLE_ANCHOR=?";
        } else if (moduleName != null && moduleName.equalsIgnoreCase("cmContactDetails"))
        {
            query = "SELECT FCF.CUSTOM_FORM_ID, FCFLD.DISPLAY_NAME FROM CM_CUSTOMIZATION_FORM FCF JOIN CM_CUSTOMIZATION_FIELD FCFLD ON FCFLD.CUSTOM_FORM_ID=FCF.CUSTOM_FORM_ID WHERE FCFLD.FIELD_NO=? AND FCF.TABLE_ANCHOR=?";
        }
        else if (moduleName != null && moduleName.equalsIgnoreCase("fs"))
        {
        	query = "SELECT FCF.CUSTOM_FORM_ID, FCFLD.DISPLAY_NAME FROM FS_CUSTOMIZATION_FORM FCF JOIN  FS_CUSTOMIZATION_FIELD FCFLD ON FCFLD.CUSTOM_FORM_ID=FCF.CUSTOM_FORM_ID WHERE FCFLD.FIELD_NO=? AND FCF.TABLE_ANCHOR=?";
        }
        
        if(query!=null) // For Reported Bug by QA
        {
        ResultSet result = QueryUtil.getResult(query, new String[]{fieldNo, moduleName});

        if (result.next())
        {
            return result.getString("DISPLAY_NAME");
        } else
        {
            return null;
        }
        }else {
        	return null;
        }
    }
    // P_FS_E_ADVNCSEARCH
    /**
     * 
     * @param query contains the sql Query
     * @return List of lead ids
     */
    public String getListofLeadIDs(String query){
	    StringBuffer leadIDs = new StringBuffer();
        ResultSet result	=null;
        try{
            result	= QueryUtil.getResult(query,null);
            while(result!= null && result.next()){
            	leadIDs.append(result.getString(1)).append(",");
			}
		} catch(Exception e){
        	logger.error("\nException in ExportDataCollector class in getListofLeadIDs method :"+e.getMessage());

		}
        try{
            if (result != null){
                result = null;
            }
        } catch (Exception ex){
        	logger.error("\nException in com/appnetix/app/portal/export - ExportDataCollector.java -->getListofLeadIDs(query):", ex);
        }

	    return leadIDs.toString();
	}
  //End of   P_FS_E_ADVNCSEARCH
    /**
     * This Method will return field-name as in xml of that table 
     * except some fields
     * P_B_Codebase_22112013
     * 
     * If a particular table contains 2 fields having same display name 
     * than same records is displayed for all rows(whichever is last)
     * 
     * Changed the map key to fieldName which is unique within xml.
     * Some Fields for which checks are implemented on older key are skipped
     * by keeping in skippedFieldNames or passing a null field Object.
     * 
     * 
     */
    private String formatFieldNames(String fieldName,Field field){
    	if(skippedFieldNames==null){
    		String [] tempArray = new String[]{"Social Security Number","Spouse Social Security Number",
    				"Birth Date","Birth Month","Spouse Birth Date","Spouse Birth Month","Taxpayer ID/FEIN",
    				"Taxpayer ID","Name","Franchise ID"};
    		skippedFieldNames = Arrays.asList(tempArray);
    	}
    	
    	if(skippedFieldNames.contains(fieldName) || field == null){
    		return StringUtil.formatFieldNames(fieldName);
    	}else{
    		return field.getFieldName();
    	}
    }
    //P_ADM_E_7864 start 
    
  //ZCUB-20151124-202 Start: Create Link on Aggrigation Custom Report count
public void addLinkOnCount(SequenceMap dataMap, String moduleName){
    	
    	try{
    	SequenceMap innerMap=(SequenceMap)dataMap.get(moduleName);
    	if(innerMap != null && innerMap.size()>0){
    		int i=innerMap.size()-1;
    		SequenceMap tempMap=(SequenceMap)innerMap.get(innerMap.getKey(i));
    		if(tempMap != null && tempMap.size()>0){
    			for(int k=0;k<tempMap.size();k++){
    				if(tempMap.get(tempMap.getKey(k)) instanceof String){
    					String tempVal=(String)tempMap.get(tempMap.getKey(k));
	     				if(StringUtil.isValid(tempVal) && tempVal.indexOf("###")!= -1){
	     					if(!StringUtil.isValid(groupValue)){
	     						groupValue="-1";
	     					}
	     					tempVal=tempVal.replace("###groupValue###", groupValue);
	     					tempVal=tempVal.replace("###groupColumnName###", groupColumnName);
	     					tempVal=tempVal.replace("###groupColumnType###", groupColumnType);
	     					tempVal=tempVal.replace("###groupTableName###", groupTableName);
	     					tempVal=tempVal.replace("###groupDbTableName###", groupDbTableName);
	     					tempMap.put(tempMap.getKey(k),tempVal);
	     				}
    				}else if(tempMap.get(tempMap.getKey(k)) instanceof SequenceMap){
    					SequenceMap chieldtempMap=(SequenceMap)tempMap.get(tempMap.getKey(k));
    					if(chieldtempMap != null && chieldtempMap.size()>0){
    						for(int j=0;j<chieldtempMap.size();j++){
    							if(chieldtempMap.get(chieldtempMap.getKey(j)) instanceof SequenceMap ){
    							SequenceMap cTempMap=(SequenceMap)chieldtempMap.get(chieldtempMap.getKey(j));
    							for(int x=0;x<cTempMap.size();x++){
    							String tempVal=	(String)cTempMap.get(cTempMap.getKey(x));
    							if(StringUtil.isValid(tempVal) && tempVal.indexOf("###")!= -1){
    								if(!StringUtil.isValid(groupValue)){
    		     						groupValue="-1";
    		     					}
    		     					tempVal=tempVal.replace("###groupValue###", groupValue);
    		     					tempVal=tempVal.replace("###groupColumnName###", groupColumnName);
    		     					tempVal=tempVal.replace("###groupColumnType###", groupColumnType);
    		     					tempVal=tempVal.replace("###groupTableName###", groupTableName);
    		     					tempVal=tempVal.replace("###groupDbTableName###", groupDbTableName);
    		     					cTempMap.put(cTempMap.getKey(x), tempVal);
    		     					}
    							 }
    						  }	
    					   }
    					}
    				}
    			}
    		}
    	 }
      }catch(Exception e){
    	  logger.error("\n Exception in ExportDataCollector class in addLinkOnCount method :"+e.getMessage());
      }
    }  //ZCUB-20151124-202 End

    public void insertExportLogData(String user_no,String moduleName,String exportType,int no_of_records,String ipAddress)
    {
    	StringBuffer sbQuery=new StringBuffer();
    	String currentDateTime=DateUtil.getCurrentDateTimeDB();
    	sbQuery.append("insert into EXPORT_LOG (DATE_TIME,USER_NO,MODULE_NAME,EXPORT_TYPE,NO_OF_RECORDS,IP_ADDRESS) values('"+currentDateTime+"','"+user_no+"','"+moduleName+"','"+exportType+"',"+no_of_records+",'"+ipAddress+"')");
    	try {
    		int result=QueryUtil.update(sbQuery.toString(), new String[] {});
    	} catch (Exception e) {
        	logger.error("\n Exception in ExportDataCollector class in insertExportLogData method :"+e.getMessage());

    	}
    }
    //P_ADM_E_7864 end
    /**
     * Problem in Custom Report while do export
     * returns set contains all tables that are tabular section of particular section
     */
    public Set<String> getTabularSectionSet(FieldMappings mappings){
		 Set<String> tabularSectionSet = new HashSet<String>();
		 if(mappings!=null)
		 {
			HeaderMap[] hMap = mappings.getHeaderMap();
			for(HeaderMap h:hMap) {
				HeaderField hfld = h.getHeaderFields();
				boolean isTabularSection = hfld.isTabularSection();
				if(isTabularSection){
					tabularSectionSet.add(hfld.getTabularSectionTableAnchor());
				}
			}
		 }
			return tabularSectionSet;
	 }
};
