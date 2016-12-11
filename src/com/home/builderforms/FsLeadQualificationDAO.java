package com.appnetix.app.components.adminmgr.manager.dao;

import com.home.builderforms.*;
import com.appnetix.app.components.BaseDAO;
import com.home.builderforms.sqlqueries.*;
import com.home.builderforms.information.*;
import org.apache.log4j.Logger;
import java.util.*;
public class FsLeadQualificationDAO extends BaseDAO {
	static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(FsLeadQualificationDAO.class);
	public FsLeadQualificationDAO(){
		this.tableAnchor = TableAnchors.FS_LEAD_QUALIFICATION_CHECKLIST;

	}







	public SequenceMap getIdRatingInfo(Integer id) {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT QFN_CHECKLIST_ID,QFN_CHECKLIST_NAME FROM FS_LEAD_QUALIFICATION_CHECKLIST WHERE QFN_CHECKLIST_ID=  ? ";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query, new Object[]{id});
		Info info=null;
			while(result.next()){
				info.set(FieldNames.QFN_CHECKLIST_ID,result.getString("QFN_CHECKLIST_ID"));
				info.set(FieldNames.QFN_CHECKLIST_NAME,result.getString("QFN_CHECKLIST_NAME"));
				sMap.put(result.getString("QFN_CHECKLIST_ID"),result.getString("QFN_CHECKLIST_NAME"));
				
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}
	public SequenceMap getIdCheckListInfo() {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT QFN_CHECKLIST_ID,QFN_CHECKLIST_NAME,QFN_CHECKLIST_ORDER FROM FS_LEAD_QUALIFICATION_CHECKLIST ORDER BY QFN_CHECKLIST_ORDER";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
		Info info=null;
			while(result.next()){
				info =new Info();
				info.set(FieldNames.QFN_CHECKLIST_ORDER,result.getString("QFN_CHECKLIST_ORDER"));
				info.set(FieldNames.QFN_CHECKLIST_ID,result.getString("QFN_CHECKLIST_ID"));
				info.set(FieldNames.QFN_CHECKLIST_NAME,result.getString("QFN_CHECKLIST_NAME"));
				sMap.put(result.getString("QFN_CHECKLIST_ID"),info);
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}
	
	public SequenceMap getIdCheckListAssociatedInfo() {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT QFN_CHECKLIST_ID,QFN_CHECKLIST_NAME,QFN_CHECKLIST_ORDER FROM FS_LEAD_QUALIFICATION_CHECKLIST ORDER BY QFN_CHECKLIST_ORDER";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
		Info info=null;
			while(result.next()){
				info =new Info();
				info.set(FieldNames.QFN_CHECKLIST_ORDER,result.getString("QFN_CHECKLIST_ORDER"));
				info.set(FieldNames.QFN_CHECKLIST_ID,result.getString("QFN_CHECKLIST_ID"));
				boolean returnValueboolean = chkAssociatedCheckList(result.getString("QFN_CHECKLIST_ID"));
				if(returnValueboolean)
				{
					info.set(FieldNames.QFN_CHECKLIST_NAME,"*"+result.getString("QFN_CHECKLIST_NAME"));
					info.set("isAssociated","true");
				}else{
					info.set(FieldNames.QFN_CHECKLIST_NAME,result.getString("QFN_CHECKLIST_NAME"));
					info.set("isAssociated","false");
				}
				sMap.put(result.getString("QFN_CHECKLIST_ID"),info);
			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return sMap;
	}
    /*
	public int getLeadCount(String id)
	{
		int leadCount=0;
		System.out.println("iduuuuuuuuuu  "+id);
		String query="SELECT LEAD_ID FROM FS_LEAD_DETAILS WHERE QFN_CHECKLIST_ID=?";
		System.out.println("query"+query);
		
		try{
			      ResultSet result = QueryUtil.getResult(query,new Object[]{id});
			      if(result.next())
			      {
			    	  System.out.println("=========");
			    	  leadCount=1;
			    	  
			      }
		}
		catch(Exception e){
			}
		return leadCount;
		
	}
    */
	public String getCheckListName(String id){
		String ratingName = "";
		String query = "SELECT QFN_CHECKLIST_NAME FROM FS_LEAD_QUALIFICATION_CHECKLIST WHERE QFN_CHECKLIST_ID=?";
		System.out.println("query:::::"+query);
		System.out.println("id:::::"+id);
		ResultSet result=null;
		try{
				 result = QueryUtil.getResult(query, new Object[]{id});
				while(result.next())
				{
					ratingName=result.getString("QFN_CHECKLIST_NAME");
				}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return ratingName;
		
	}
	public String getMaxOrderNo() {
		String query = "SELECT MAX(QFN_CHECKLIST_ORDER) 'maxOrderNo' FROM  FS_LEAD_QUALIFICATION_CHECKLIST";
		String maxOrderNo=null;
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
			if(result.next()){
				maxOrderNo= result.getString("maxOrderNo");

			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return maxOrderNo;
	} 
	public String getCheckListOrder(String id) {
		String query = "SELECT QFN_CHECKLIST_ORDER  FROM  FS_LEAD_QUALIFICATION_CHECKLIST WHERE QFN_CHECKLIST_ID=?";
		String leadRatingOrder=null;
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query, new Object[]{id});
			if(result.next()){
				leadRatingOrder= result.getString("QFN_CHECKLIST_ORDER");

			}
		}
		catch(Exception e){
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return leadRatingOrder;
	}

    public SequenceMap getQualificationCheckListActivity(String leadQualificationDetailId){
        SequenceMap sMap = new SequenceMap();

        
        Info info = new Info();
        String query = "SELECT * FROM FS_LEAD_QUALIFICATION_CHECKLIST_ACTIVITY WHERE QFN_CHK_ACK_LEAD_QUALIFICATION_DETAIL_ID="+leadQualificationDetailId;
        ResultSet result=null;
        try{            
		 result			= QueryUtil.getResult(query,null);
		 if(result!=null){
			while(result.next()){
                info = new Info();
                info.set(FieldNames.QFN_CHK_ACK_ID,result.getString("QFN_CHK_ACK_ID"));
                info.set(FieldNames.QFN_CHK_ACK_CHECKLIST_ID,result.getString("QFN_CHK_ACK_CHECKLIST_ID"));
                info.set(FieldNames.QFN_CHK_ACK_LEAD_QUALIFICATION_DETAIL_ID,result.getString("QFN_CHK_ACK_LEAD_QUALIFICATION_DETAIL_ID"));
                info.set(FieldNames.QFN_CHK_ACK_VALUE,result.getString("QFN_CHK_ACK_VALUE"));
                info.set(FieldNames.QFN_CHK_ACK_COMPLETED,result.getString("QFN_CHK_ACK_COMPLETED"));
                info.set(FieldNames.QFN_CHK_ACK_COMPLETION_DATE,com.home.builderforms.DateUtil.getDisplayDate(result.getString("QFN_CHK_ACK_COMPLETION_DATE")));
                info.set(FieldNames.QFN_CHK_ACK_DOC_NAME,result.getString("QFN_CHK_ACK_DOC_NAME"));
                info.set(FieldNames.QFN_CHK_ACK_DOC_TYPE,result.getString("QFN_CHK_ACK_DOC_TYPE"));
                info.set(FieldNames.QFN_CHK_ACK_VERIFIED_BY,result.getString("QFN_CHK_ACK_VERIFIED_BY"));
                info.set(FieldNames.QFN_CHK_ACK_DATE,com.home.builderforms.DateUtil.getDisplayDate(result.getString("QFN_CHK_ACK_DATE")));
				sMap.put(result.getString("QFN_CHK_ACK_CHECKLIST_ID"), info);
			}
			}
		}
		catch(Exception e){
            e.printStackTrace();
		}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

        return sMap;
    }

    public boolean chkAssociatedCheckList(String chkListId) {
		return SQLUtil.isDataAvailable("FS_LEAD_QUALIFICATION_CHECKLIST_ACTIVITY", "QFN_CHK_ACK_CHECKLIST_ID='"+chkListId+"'");
	}

}