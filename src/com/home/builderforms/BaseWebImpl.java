package com.appnetix.app.control.web.webimpl;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.SQLException;

import com.home.builderforms.*;
import com.home.builderforms.sqlqueries.ResultSet;
 
import com.home.builderforms.Info;
import com.home.builderforms.SequenceMap;

import com.home.builderforms.BaseDAO;


import javax.servlet.http.HttpSession;

public class BaseWebImpl implements java.io.Serializable {

	public BaseWebImpl() {
	}
	

	public void init(HttpSession session) {
		
	}
	public SequenceMap getSummary(	String psTableAnchor,
			SequenceMap pParamMap,
			String[] psArrFieldsToFetch,String pageSize,String page,String orderBy
			)
	{
		try{

			BaseDAO dao						= new BaseDAO(psTableAnchor);

			if(pParamMap == null && (psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1)){
				return dao.getSummaryCollection();
			}else if(pParamMap == null){
				return dao.getCollection(psArrFieldsToFetch, new SequenceMap(), pageSize, page,orderBy);
			}else if(psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1){
				return dao.getCollection(pParamMap, pageSize, page,orderBy);
			}else
				return dao.getCollection(psArrFieldsToFetch, pParamMap, pageSize, page,orderBy);

		}catch(Exception e){
		}
		return null;
	}
	public SequenceMap getSummary(	String psTableAnchor,
									SequenceMap pParamMap,
									String[] psArrFieldsToFetch
								)
	{
		try{
		
			BaseDAO dao						= new BaseDAO(psTableAnchor);
			
			if(pParamMap == null && (psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1)){
				return dao.getSummaryCollection();
			}else if(pParamMap == null){
				return dao.getCollection(psArrFieldsToFetch, new SequenceMap());
			//}else if(pParamMap != null){ - Modified by Anuj 25-09-2004
			}else if(psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1){
				return dao.getCollection(pParamMap);
			}else
				return dao.getCollection(psArrFieldsToFetch, pParamMap);

		}catch(Exception e){
		}
		return null;
	}

	public SequenceMap getSummary(	String psTableAnchor,
									SequenceMap pParamMap,
									String[] psArrFieldsToFetch,
									String orderBy
								  )
	{
		try{
			BaseDAO dao						= new BaseDAO(psTableAnchor);
			
			if(pParamMap == null && (psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1)){
				return dao.getSummaryCollection();
			}else if(pParamMap == null){
				return dao.getCollection(psArrFieldsToFetch, new SequenceMap(),orderBy);
			//}else if(pParamMap != null){ - Modified by Anuj 25-09-2004
			}else if(psArrFieldsToFetch == null || psArrFieldsToFetch.length < 1){
				return dao.getCollection(pParamMap,orderBy);
			}else
				return dao.getCollection(psArrFieldsToFetch, pParamMap,orderBy);

		}catch(Exception e){
		}
		return null;
	}
}