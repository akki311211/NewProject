/**
 * @author abhishek gupta
 * @date 18 nov, 2011
 * P_FS_Enh_BuilderForm		16Dec2013 		Naman Jain			FOrm Builder in Franchise sALES
 * TSTE-20150204-004	    19Dec2014 		Rohit Jain			In fim Real State tab data showing of fs Real State tab data
 *                                                              and when QA tab integration on then QA history section will not visible in form builder section.
 */
package com.home.builderforms;

import java.util.*;
import java.sql.*;
import java.io.*;
import com.appnetix.app.util.*;
import com.appnetix.app.util.DBConnectionManager;
import com.home.builderforms.Info;
import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;//TSTE-20150204-004
import com.home.BuilderFormFieldNames;

import org.apache.log4j.Logger;

public class TableBuilder extends BaseBuilder{
    static Logger logger	= 		com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(TableBuilder.class);
    
    protected String tmpDirectory;
    String menuName1 = "";

    public TableBuilder(HashMap cParams, HashMap tableParameters, String menuName) throws ConnectionException{
        super(cParams, tableParameters);
        tableParameters.put(BuilderConstants.TABLE_NAME, tableName);
        menuName1 = menuName;
        tableElements = new HashMap();
        createTableElements(cParams, tableParameters);
    }
    
    public boolean save(){
        return true;
    }
    
    public void createTableElements(HashMap cParams, HashMap tableParameters) throws ConnectionException{
        Connection con = null;
        con = DBConnectionManager.getInstance().getConnection(MultiTenancyUtil.getTenantName(),2000);
        createTable(con, (HashMap)cParams.get(BuilderConstants.FORM_TABLE), tableParameters);
        try{
            DBConnectionManager.getInstance().freeConnection(con);
        } catch(Exception e){}
    }
    
    public StringBuffer constructQuery(HashMap tableParams, ArrayList where){
        StringBuffer query = new StringBuffer();
        String select = (String)tableParams.get(BuilderConstants.SELECT);
        String from = (String)tableParams.get(BuilderConstants.FROM);
        String groupBy = (String)tableParams.get(BuilderConstants.GROUP_BY);
        String orderBy = (String)tableParams.get(BuilderConstants.ORDER_BY);
        String limit = (String)tableParams.get(BuilderConstants.LIMIT);
        
		query.append(BuilderConstants.SPACE).append(BuilderConstants.SELECT).append(BuilderConstants.SPACE).append(select).append(BuilderConstants.SPACE).append(BuilderConstants.FROM).append(BuilderConstants.SPACE).append(from);
        if(where != null && !"".equals(where))
        	for (int i = 0;i < where.size();++i){
        		query.append((i==0)?BuilderConstants.SPACE + BuilderConstants.WHERE + BuilderConstants.SPACE:BuilderConstants.SPACE);
        		query.append(where.get(i)).append((i + 1 != where.size())?BuilderConstants.SPACE + BuilderConstants.AND + BuilderConstants.SPACE:BuilderConstants.SPACE);
        	}
        if (groupBy != null && groupBy.length() != 0){
            query.append(BuilderConstants.SPACE).append(BuilderConstants.GROUP_BY_CLASS).append(BuilderConstants.SPACE);
            query.append(groupBy);
        }
        if (orderBy != null && orderBy.length() != 0){
            query.append(BuilderConstants.SPACE).append(BuilderConstants.ORDER_BY_CLASS).append(BuilderConstants.SPACE);
            query.append(orderBy);
        }
        if (limit != null && limit.length() != 0){
            query.append(BuilderConstants.SPACE).append(BuilderConstants.LIMIT_CLASS).append(BuilderConstants.SPACE);
            query.append(limit);
        }
        return query;
    }
    
     public void createTable(Connection con, HashMap tableParams, HashMap tableParameters) throws ConnectionException{
        ArrayList whereConditions = new ArrayList(0);
        String where = "";
        Statement stmt = null;
        ResultSet rs = null;
        String [] colNames = null;
        String [] colTypes = null;
        if (tableID == 1){
        	HashMap tablePara = (HashMap)tableParameters.get(BuilderConstants.PARAMETER_TABLE);
        	if(tablePara != null) {
        		HashMap tableMap = (HashMap)tablePara.get(BuilderConstants.TABLE);
        		
        		ArrayList whereConditions1 = new ArrayList(0);
        		if(tableMap.get(BuilderConstants.WHERE) != null) {
                	where = (String)tableMap.get(BuilderConstants.WHERE);
                	whereConditions1.add(where);
            	}
        		StringBuffer queryBuffer1 = constructQuery(tableMap, whereConditions1);
        		try{
	        		stmt = con.createStatement();
	            	rs = stmt.executeQuery(queryBuffer1.toString());
          			//ENH_MODULE_CUSTOM_TABS starts
	            	if(rs != null)
	            	{
		            	if(rs.next())
		            	{
		            		tableParameters.put("param1", rs.getString(1));
		                }
		            	if(rs.next())
		            	{
		            		tableParameters.put("param2", rs.getString(1));
		                }
	            	}
	            	//ENH_MODULE_CUSTOM_TABS ends
        		} catch(Exception e){
                    e.printStackTrace();
                } finally{
                    try{
                       whereConditions1 = null;
                       tablePara = null;
                       queryBuffer1 = null;
                       tableMap = null;
                       if(rs != null){ rs.close(); rs = null; }
                       if(stmt != null){ stmt.close(); stmt = null; } 
                    }catch(Exception e){}
                }
        	}
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	where = where.replace("$param1$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("param1") + BuilderConstants.SINGLE_QUOTE);
            	where = where.replace("$param2$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("param2") + BuilderConstants.SINGLE_QUOTE);//ENH_MODULE_CUSTOM_TABS
            //TSTE-20150204-004 starts
            	where = where.replace("$param3$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("builderModuleName") + BuilderConstants.SINGLE_QUOTE);

            	if(MultiTenancyUtil.getTenantConstants().QA_TAB_INTEGRATION && "fim".equals((String)tableParameters.get("builderModuleName")) && ModuleUtil.auditImplemented())
            		where = where.replace("$param4$", BuilderConstants.SINGLE_QUOTE + "19" + BuilderConstants.SINGLE_QUOTE);//codebase805_issue
            	else
            		where = where.replace("$param4$", BuilderConstants.SINGLE_QUOTE +FieldNames.EMPTY_STRING + BuilderConstants.SINGLE_QUOTE);//codebase805_issue
            //TSTE-20150204-004 ends
            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            colTypes = (String[])tableParams.get(BuilderConstants.COLUMN_TYPES);
            String mapCol = (String)tableParameters.get(BuilderConstants.SEQUENCE_MAP_KEY);
            try{
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                }
            	SequenceMap hashMap = new SequenceMap();
            	Info info = null;
                for (int i = 0;i < rowSize && rs.next(); ++i){
                	info = new Info();
                	String tempVal = "";
                	for (int j = 0;j < colNames.length ; j++){
                		if(mapCol != null && mapCol.equalsIgnoreCase(colNames[j])) {
                			tempVal = rs.getString(j+1);
                    		info.set(colNames[j], tempVal);
                		} else {
                			info.set(colNames[j], rs.getString(j+1));
                		}
                	}
                	hashMap.put(tempVal,info);
                }
                setTableMap(hashMap);
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        } else if (tableID == 2 || tableID == 7){
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	where = where + "=" + String.valueOf(tableParameters.get(BuilderConstants.FORM_ID));
            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            
            try{
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                }
            	SequenceMap hashMap = new SequenceMap();
            	Info info = null;
                for (int i = 0;i < rowSize && rs.next(); ++i){
                	info = new Info();
                	String tempVal = "";
                	for (int j = 0;j < colNames.length ; j++){
                    	info.set(colNames[j], rs.getString(j+1));
                	}
                	hashMap.put(info.get(colNames[0]),info);
                }
                setTableMap(hashMap);
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        } else if (tableID == 4){
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
    			if(StringUtil.isValidNew(String.valueOf(tableParameters.get(BuilderFormFieldNames.FIELD_ID)))) {
    				String id = String.valueOf(tableParameters.get(BuilderFormFieldNames.FIELD_ID));
    				where = " FIELD_ID='"+id+"'";
    				whereConditions.add(where);
    			} else {
    				String name = String.valueOf(tableParameters.get(BuilderFormFieldNames.FIELD_NAME));
    				if(StringUtil.isValidNew(name)) {
    	   				where = "FIELD_NAME='"+name+"'";
        				whereConditions.add(where);
    				}
    				name = String.valueOf(tableParameters.get(BuilderFormFieldNames.FORM_ID));
    				if(StringUtil.isValidNew(name)) {
        				where = " BUILDER_FORM_ID='"+name+"'";
        				whereConditions.add(where);
    				}
    			}
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            
            try {
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                  
                }
                String type = String.valueOf(tableParameters.get(BuilderConstants.MAP_TYPE));
            	if("info".equals(type)) {
                   	Info info = null;
                    if(rs.next()) {
                    	info = new Info();
                    	String tempVal = "";
                    	for (int j = 0;j < colNames.length ; j++) {
                        	info.set(colNames[j], rs.getString(j+1));
                    	}
                    }
                    setObject(info);
            	} else {
            		String mapCol = (String)tableParameters.get(BuilderConstants.SEQUENCE_MAP_KEY);
                   	Info info = null;
                    SequenceMap hashMap = new SequenceMap();
                    for (int i = 0;i < rowSize && rs.next(); ++i){
                    	info = new Info();
                    	if(StringUtil.isValidNew(mapCol)) {
                    		String tempVal = "";
                        	for (int j = 0;j < colNames.length ; j++){
                        		if(mapCol != null && mapCol.equalsIgnoreCase(colNames[j])) {
                        			tempVal = rs.getString(j+1);
                            		info.set(colNames[j], tempVal);
                        		} else {
                        			info.set(colNames[j], rs.getString(j+1));
                        		}
                        	}
                        	hashMap.put(tempVal,info);
                        } else {
                        	for (int j = 0;j < colNames.length ; j++){
                            	info.set(colNames[j], rs.getString(j+1));
                        	}
                        	hashMap.put(info.get(colNames[0]),info);
                    	}
                    }
                    setObject(hashMap);
            	}
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        } 
        //P_FS_Enh_BuilderForm
        else if(tableID == 10)
        {
       		tableParams.put(BuilderConstants.ORDER_BY,"BUILDER_FORM_ID"); //adding for order by clause in query
        	if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	if(StringUtil.isValidNew(String.valueOf(tableParameters.get(BuilderConstants.FORM_ID))))
            		where = where +  "AND BUILDER_FORM_ID  =" + String.valueOf(tableParameters.get(BuilderConstants.FORM_ID));
            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            
            try{
        		stmt = con.createStatement();
        		System.out.println("queryBuffer----------------"+queryBuffer);
        		rs = stmt.executeQuery(queryBuffer.toString());
        		int rowSize = 0;
        		if (rs.last()) {
        			rowSize = rs.getRow();
        			rs.beforeFirst();
        		}
        		SequenceMap hashMap = new SequenceMap();
        		Info info = null;
        		for (int i = 0;i < rowSize && rs.next(); ++i){
        			info = new Info();
        			for (int j = 0;j < colNames.length ; j++){
        				info.set(colNames[j], rs.getString(j+1));
        			}
        			hashMap.put(info.get(colNames[0]),info);
        		}
        		setObject(hashMap);
        		setTableMap(hashMap);
        	} catch(Exception e){
        		e.printStackTrace();
        	} finally{
        		try{
        			if(rs != null){ rs.close(); rs = null; }
        			if(stmt != null){ stmt.close(); stmt = null; } 
        		}catch(Exception e){}
       	}
        }
        //P_FS_Enh_BuilderForm
     	//ENH_MODULE_CUSTOM_TABS starts
        else if(tableID == 12)
        {
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	if((tableID == 12) && StringUtil.isValid((String)tableParameters.get(BuilderFormFieldNames.BUILDER_MODULE_NAME)))
            		where = where.replace("$param1$","'"+String.valueOf(tableParameters.get(BuilderFormFieldNames.BUILDER_MODULE_NAME))+"'");

            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            try{
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                }
            	SequenceMap hashMap = new SequenceMap();
            	Info info = null;
                for (int i = 0;i < rowSize && rs.next(); ++i){
                	info = new Info();
                	for (int j = 0;j < colNames.length ; j++){
                		info.set(colNames[j], rs.getString(j+1));
                	}
                	hashMap.put(info.get(colNames[0]),info);
                }
                setObject(hashMap);
                setTableMap(hashMap);
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        } else if (tableID == 23 || tableID == 21){//P_Enh_Mu-Entity_FormGenerator starts
        	HashMap tablePara = (HashMap)tableParameters.get(BuilderConstants.PARAMETER_TABLE);
        	if(tablePara != null) {
        		HashMap tableMap = (HashMap)tablePara.get(BuilderConstants.TABLE);
        		
        		ArrayList whereConditions1 = new ArrayList(0);
        		if(tableMap.get(BuilderConstants.WHERE) != null) {
                	where = (String)tableMap.get(BuilderConstants.WHERE);
                	whereConditions1.add(where);
            	}
        		StringBuffer queryBuffer1 = constructQuery(tableMap, whereConditions1);
        		try{
	        		stmt = con.createStatement();
	            	rs = stmt.executeQuery(queryBuffer1.toString());
          			//ENH_MODULE_CUSTOM_TABS starts
	            	if(rs != null)
	            	{
		            	if(rs.next())
		            	{
		            		tableParameters.put("param1", rs.getString(1));
		                }
		            	if(rs.next())
		            	{
		            		tableParameters.put("param2", rs.getString(1));
		                }
	            	}
	            	//ENH_MODULE_CUSTOM_TABS ends
        		} catch(Exception e){
                    e.printStackTrace();
                } finally{
                    try{
                       whereConditions1 = null;
                       tablePara = null;
                       queryBuffer1 = null;
                       tableMap = null;
                       if(rs != null){ rs.close(); rs = null; }
                       if(stmt != null){ stmt.close(); stmt = null; } 
                    }catch(Exception e){}
                }
        	}
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	where = where.replace("$param1$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("param1") + BuilderConstants.SINGLE_QUOTE);
            	where = where.replace("$param2$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("param2") + BuilderConstants.SINGLE_QUOTE);//ENH_MODULE_CUSTOM_TABS
            //TSTE-20150204-004 starts
            	where = where.replace("$param3$", BuilderConstants.SINGLE_QUOTE + (String)tableParameters.get("builderModuleName") + BuilderConstants.SINGLE_QUOTE);

            	if(MultiTenancyUtil.getTenantConstants().QA_TAB_INTEGRATION && "fim".equals((String)tableParameters.get("builderModuleName")) && ModuleUtil.auditImplemented())
            		where = where.replace("$param4$", BuilderConstants.SINGLE_QUOTE + "23" + BuilderConstants.SINGLE_QUOTE);//codebase805_issue
            	else
            		where = where.replace("$param4$", BuilderConstants.SINGLE_QUOTE +FieldNames.EMPTY_STRING + BuilderConstants.SINGLE_QUOTE);//codebase805_issue
            //TSTE-20150204-004 ends
            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            colTypes = (String[])tableParams.get(BuilderConstants.COLUMN_TYPES);
            String mapCol = (String)tableParameters.get(BuilderConstants.SEQUENCE_MAP_KEY);
            try{
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                }
            	SequenceMap hashMap = new SequenceMap();
            	Info info = null;
                for (int i = 0;i < rowSize && rs.next(); ++i){
                	info = new Info();
                	String tempVal = "";
                	for (int j = 0;j < colNames.length ; j++){
                		if(mapCol != null && mapCol.equalsIgnoreCase(colNames[j])) {
                			tempVal = rs.getString(j+1);
                    		info.set(colNames[j], tempVal);
                		} else {
                			info.set(colNames[j], rs.getString(j+1));
                		}
                	}
                	hashMap.put(tempVal,info);
                }
                setTableMap(hashMap);
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        }//P_Enh_Mu-Entity_FormGenerator ends
        else { 
//    		if(tableParams.get(BuilderConstants.WHERE) != null) {
//            	where = (String)tableParams.get(BuilderConstants.WHERE);
//            	whereConditions.add(where);
//        	}
    		if(tableParams.get(BuilderConstants.WHERE) != null) {
            	where = (String)tableParams.get(BuilderConstants.WHERE);
            	if(StringUtil.isValid((String)tableParameters.get(BuilderConstants.FORM_ID)))
            		where = where + "=" + String.valueOf(tableParameters.get(BuilderConstants.FORM_ID));
            	whereConditions.add(where);
        	}
            StringBuffer queryBuffer = constructQuery(tableParams, whereConditions);
            colNames = (String[])tableParams.get(BuilderConstants.COLUMN_NAMES);
            try{
            	stmt = con.createStatement();
            	rs = stmt.executeQuery(queryBuffer.toString());
                int rowSize = 0;
                int columnSize = 0;
                if (rs.last()) {
                    rowSize = rs.getRow();
                    rs.beforeFirst();
                }
            	SequenceMap hashMap = new SequenceMap();
            	Info info = null;
                for (int i = 0;i < rowSize && rs.next(); ++i){
                	info = new Info();
                	for (int j = 0;j < colNames.length ; j++){
                		info.set(colNames[j], rs.getString(j+1));
                	}
                	hashMap.put(info.get(colNames[0]),info);
                }
                setObject(hashMap);
                setTableMap(hashMap);
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                try{
                   if(rs != null){ rs.close(); rs = null; }
                   if(stmt != null){ stmt.close(); stmt = null; } 
                }catch(Exception e){}
            }
        }
    }
     
     private Object castValue(String type, String obj) {
    	 if(type.equals("String")) {
    		 return obj;
    	 } else if(type.equalsIgnoreCase("Integer")) {
    		 return Integer.parseInt(obj);
    	 } else {
    		 return obj;
    	 }
     }
    
    public void generateSummary(HashMap tableParams){
        return;
    }
   
	@Override
	public String generateXMLTable() {
		// TODO Auto-generated method stub
		return null;
	}
}
