package com.appnetix.app.components.adminmgr.manager.dao;

import com.home.builderforms.*;
import com.appnetix.app.components.BaseDAO;
import com.home.builderforms.sqlqueries.*;
import com.home.builderforms.information.*;

import org.apache.log4j.Logger;
import java.util.*;
public class FsLeadRatingDAO extends BaseDAO {
	static Logger logger		=com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(FsLeadRatingDAO.class);
	public FsLeadRatingDAO(){
		this.tableAnchor = TableAnchors.FS_LEAD_RATING;

	}







	public SequenceMap getIdRatingInfo(Integer id) {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT LEAD_RATING_ID,LEAD_RATING_NAME FROM FS_LEAD_RATING WHERE LEAD_RATING_ID=  ? ";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query, new Object[]{id});
		Info info=null;
			while(result.next()){
				info.set(FieldNames.LEAD_RATING_ID,result.getString("LEAD_RATING_ID"));
				info.set(FieldNames.LEAD_RATING_NAME,result.getString("LEAD_RATING_NAME"));
				sMap.put(result.getString("LEAD_RATING_ID"),result.getString("LEAD_RATING_NAME"));
				
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
	public SequenceMap getIdRatingInfo() {

		SequenceMap sMap = new SequenceMap();
		String query = "SELECT LEAD_RATING_ID,LEAD_RATING_NAME FROM FS_LEAD_RATING ORDER BY LEAD_RATING_ORDER";
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query,null);
		Info info=null;
			while(result.next()){
				info =new Info();
				info.set("count",getLeadCount(result.getString("LEAD_RATING_ID")));
				info.set(FieldNames.LEAD_RATING_ID,result.getString("LEAD_RATING_ID"));
				info.set(FieldNames.LEAD_RATING_NAME,result.getString("LEAD_RATING_NAME"));
				sMap.put(result.getString("LEAD_RATING_ID"),info);
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
	public int getLeadCount(String id)
	{
		int leadCount=0;
		System.out.println("iduuuuuuuuuu  "+id);
		String query="SELECT LEAD_ID FROM FS_LEAD_DETAILS WHERE LEAD_RATING_ID=?";
		System.out.println("query"+query);
		ResultSet result=null;
		try{
			       result = QueryUtil.getResult(query,new Object[]{id});
			      if(result.next())
			      {
			    	  System.out.println("=========");
			    	  leadCount=1;
			    	  
			      }
		}
		catch(Exception e){
			}
		finally
		{
			QueryUtil.releaseResultSet(result);
		}

		return leadCount;
		
	}
	public String getRatingName(String id){
		String ratingName = "";
		String query = "SELECT LEAD_RATING_NAME FROM FS_LEAD_RATING WHERE LEAD_RATING_ID=?";
		System.out.println("query:::::"+query);
		System.out.println("id:::::"+id);
		ResultSet result=null;
		try{
				 result = QueryUtil.getResult(query, new Object[]{id});
				while(result.next())
				{
					ratingName=result.getString("LEAD_RATING_NAME");
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
		String query = "SELECT MAX(LEAD_RATING_ORDER) 'maxOrderNo' FROM  FS_LEAD_RATING";
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
	public String getRatingOrder(String id) {
		String query = "SELECT LEAD_RATING_ORDER  FROM  FS_LEAD_RATING WHERE LEAD_RATING_ID=?";
		String leadRatingOrder=null;
		ResultSet result=null;
		try{
		 result			= QueryUtil.getResult(query, new Object[]{id});
			if(result.next()){
				leadRatingOrder= result.getString("LEAD_RATING_ORDER");

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
	/*-------------------------------------------Moved From BaseNewPortalUtils-------------------------------------------------*/
    /**
     * This method returns the info object of All Lead Ratings
     */	 
	 public Info getLeadRating() {
	    	return SQLUtil.getInfoFromQuery("LEAD_RATING_ID", "LEAD_RATING_NAME","SELECT LEAD_RATING_ID,LEAD_RATING_NAME FROM FS_LEAD_RATING ORDER BY LEAD_RATING_ORDER",true);
	    }



}