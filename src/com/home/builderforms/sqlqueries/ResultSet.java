package com.home.builderforms.sqlqueries;

import java.sql.SQLException;
import java.util.*;

import com.home.builderforms.DateUtil;
import com.home.builderforms.*;

/*
 * DATE_FORMAT_CHANGE          Ravindra Verma                         For internationalization of date form
 * SMC_FIN_OPTIMIZATION		   Balvinder Mehla						for getting column label when using AS 
 */
public class ResultSet
{

	private ArrayList resultList;

	private ListIterator  resultIt;

	private ArrayList currentRow;

	private HashMap columns;
	private int nColumnCount=0;
        //P_E_BottomBar by Ankit saini starts
        private java.sql.ResultSetMetaData metaData1;
        //P_E_BottomBar by Ankit saini ends
	public void ResultSet(){}

		
	public ResultSet(java.sql.ResultSet result) throws java.sql.SQLException

	{

		try{

 

			java.sql.ResultSetMetaData metaData		= result.getMetaData();
                        //P_E_BottomBar by Ankit saini starts
                        metaData1=metaData;
                        //P_E_BottomBar by Ankit saini ends

			nColumnCount					= metaData.getColumnCount();

 

			resultList							= new ArrayList();

			columns								= new HashMap(nColumnCount);

			ArrayList columnTypes				= new ArrayList(nColumnCount);

			for(int i=1; i<=nColumnCount; i++)

			{

				columns.put(metaData.getColumnLabel(i),new Integer(i));//SMC_FIN_OPTIMIZATION

 

				String dataType = metaData.getColumnClassName(i);

 

				dataType = dataType.substring(dataType.lastIndexOf('.')+1,dataType.length());

				if (dataType.equals("BigDecimal")) dataType = "Integer";

				if (dataType.equals("Timestamp")) dataType = "Date";

				if (dataType.equals("CLOB")) dataType = "Clob";

				if (dataType.equals("Long")) dataType = "Integer";

				if (dataType.equals("Double")) dataType = "Double";

				columnTypes.add(dataType);

			}

                        while (result.next())

			{

				ArrayList columnList			= new ArrayList(nColumnCount);

				Object obj 						= null;

				for(int i=1; i<=nColumnCount; i++)

				{

					String dataType = (String)columnTypes.get(i-1);

					if (Field.DataType.INTEGER.equals(dataType))

					{

						 obj =  new Integer(result.getInt(i));

					}

					else if (Field.DataType.DATE.equals(dataType))

					{

					//	obj =  result.getDate(i);
					
					//added by vikas to get the time stamp object
					//here obj will contain Date,TimeStamp etc
					//if getObject returns TimeStamp than it will again converted to sql.Date object in getString and getObject of this class
						obj =  result.getObject(i);

					}

					else if (Field.DataType.BOOLEAN.equals(dataType))

					{

						obj =  new Boolean(result.getBoolean(i));

					}

					else if (Field.DataType.CLOB.equals(dataType))

					{

						obj =  result.getClob(i);

					}

					else if (Field.DataType.DOUBLE.equals(dataType))

					{

						obj =  new Double(result.getDouble(i));

					}

					else

					{

						 obj = result.getString(i);

					}

					columnList.add(obj);

				}

				resultList.add(columnList);

			}
                        
		}finally{

			result.close();
//			//added by vikas to nullify the result set
			result=null;
			
			//com.home.builderforms.QueryUtil.releaseResultSet(result);

		}

	}

	/**
	 * @author Vivek Maurya
	 * @return true if cursor is on the first row. 
	 */
	public boolean isFirst() {
		boolean isFirstFlag = false;
		
		if(resultIt == null) {
			resultIt = resultList.listIterator();
		}
		
		int prevIndex = resultIt.previousIndex();
		if(prevIndex == -1) {
			isFirstFlag = true;
		}
		
		return isFirstFlag;
	}
	
	/**
	 * @author Vivek Maurya
	 * @return true if cursor is on the last row. 
	 */
	public boolean isLast() {
		boolean isLastFlag = false;
		
		if(resultIt == null) {
			resultIt = resultList.listIterator();
		}
		
		int nextIndex = resultIt.nextIndex();
		if(nextIndex == resultList.size()) {
			isLastFlag = true;
		}
		
		return isLastFlag;
	}
	
	/**
	 * @author Vivek Maurya
	 * @return moves the cursor to the first row. 
	 */
	public void first() {
		resultIt = null;
		resultIt = resultList.listIterator();
	}
	
	/**
	 * @author Vivek Maurya
	 * @return moves the cursor to the last row. 
	 */
	public void last() throws SQLException {
		if(resultIt == null) {
			throw new SQLException("ResultSet is null !!");
		}

		while(resultIt.hasNext()) {
			currentRow = (ArrayList)resultIt.next();
		}
	}
	

	public boolean next()

	{

		boolean ret = false;

		if (resultIt == null)

		{

			resultIt = resultList.listIterator();

		}

		ret = resultIt.hasNext();

		if (ret)

		{

			currentRow = (ArrayList)resultIt.next();

		}

		return ret;



	}
/**
 * 
 * @param i
 * @return This ,method return the database coumn value as it is
 * without converting in to date display format
 */
	public String getInString(int i){

		Object obj	= currentRow.get(i-1);

		if (obj != null)
		{
			return obj.toString();
		}

		return null;
	
	}
/**
 * 
 * @param columnName
 * @return This ,method return the database coumn value as it is
 * without converting in to date display format
 */
	
	public String getInString(String columnName)

	{

		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}

		return getInString(columnNo);

	}
	public String getString(int i)
	{
		Object obj	= currentRow.get(i-1);

		if (obj != null)
		{
			// added by vikas to get the time stamp object 
			// here current object may be Timestamp type
			// neglecting the effect on the whole build due to this timestamp object
			// which hasbeen chaged in the above lines
			// in above lines  onle getDate method was called so
			// here timestamp again converted to sql.Date
			// again toString method of Date will returns the date in db 10 digit format

			if (obj instanceof java.sql.Timestamp)
			{
				// DATE_FORMAT_CHANGE
				java.sql.Timestamp tStamp=(java.sql.Timestamp) obj;
				java.sql.Date sqlDate=new java.sql.Date(tStamp.getTime());
				return DateUtil.getDisplayDate(sqlDate.toString());
				// Added to return date directly in display format, So that no one has to 
				// write any additional code to make date in diplay format and DateUtil.getDisplayDate always returns the date in configurable date format
			} else if (obj instanceof java.sql.Date)
			{
				// DATE_FORMAT_CHANGE
				return DateUtil.getDisplayDate(obj.toString()); 
			} else
			{
				return obj.toString();
			}
		}

		return null;
	}



	public String getString(String columnName)

	{

		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}

		return getString(columnNo);

	}
	
	public String getDBDate(String columnName)

	{
		String rValue = null;
		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}
		rValue = getString(columnNo);
		try{
		if(rValue != null)
			rValue = DateUtil.getDbDate(rValue);
		}catch(Exception e){
			
		}
		return rValue;

	}

	public Object getObject(String columnName)

	{

		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}

		return getObject(columnNo);

	}



	public Object getObject(int i)

	{
			//added by vikas to get the time stamp object 
			//here current object may be Timestamp type
			//neglecting the effect on the whole build due to this timestamp object
			//which hasbeen chaged in the above lines
			//in above lines  onle getDate method was called so
			//here timestamp again converted to sql.Date
		 if(i != -1)
         {
         	
         	Object obj = currentRow.get(i-1);
         	
         	if (obj instanceof java.sql.Timestamp)
         	{
         		java.sql.Timestamp tStamp = (java.sql.Timestamp) obj;
         		java.sql.Date sqlDate = new java.sql.Date(tStamp.getTime()); 
         		return DateUtil.getDisplayDate(sqlDate.toString());
         	} else if (obj instanceof java.sql.Date)
         	{
         		return DateUtil.getDisplayDate(obj.toString());
         	} else
         	{
         		return obj;
         	}
         	
         }
         else 
         	return null;

			// return currentRow.get(i-1);

	}
        
        public Object getObjects(String columnName)

	{

		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}

		return getObjects(columnNo);

	}



	public Object getObjects(int i){
			//added by manoj to get the time stamp object 
			Object obj=currentRow.get(i-1);

			if (obj!=null && obj instanceof java.sql.Timestamp){
                                java.sql.Timestamp tStamp=(java.sql.Timestamp)obj;
                                return tStamp;
			}else{
				return obj;
			}
	}

	public Integer getInteger(int i){

		return (Integer)currentRow.get(i-1);

	}


/*
*@ Author Vikas
*@ Returns TimeStamp Object
*/
	public java.sql.Timestamp getTimestamp(String columnName)
	{
		Integer gInt	= (Integer)columns.get(columnName);
		int columnNo	= -1;

		if (gInt != null)
		{
			columnNo	= gInt.intValue();
		}

		return getTimestamp(columnNo);

	}

/*
*@ Author Vikas
*@ Returns TimeStamp Object
*/

	public java.sql.Timestamp getTimestamp(int i)
	{
		return (java.sql.Timestamp)currentRow.get(i-1);
	}

/*
*@ Author Parveen
*@ Returns Date Object
*/
	public java.sql.Date getDate(String columnName)

	{

		Integer gInt	= (Integer)columns.get(columnName);

		int columnNo	= -1;

		if(gInt != null){

			columnNo	= gInt.intValue();

		}

		return getDate(columnNo);

	}
	
	/*
	*@ Author Parveen
	*@ Returns Date Object
	*/

	public java.sql.Date getDate(int i){

			return (java.sql.Date)currentRow.get(i-1);

	}


/*
*@ Author Parveen
*@ Date 7/6/2005
*/

public int size() {

   	return resultList.size();

}
// ADDED BY BINU
public int  getnColumnCount()
{
return  nColumnCount;
}
//P_E_BottomBar by Ankit saini starts
public java.sql.ResultSetMetaData getMetaData(){
    return metaData1;
}
//P_E_BottomBar by Ankit saini ends

  /*  public void release() {
        if (currentRow != null) {
            currentRow.clear();
            currentRow = null;
        }
        if (resultIt != null) {
            resultIt = null;
        }
        if (resultList != null) {
            resultList.clear();
            resultList = null;
        }
        if (columns != null) {
            columns = null;
        }
    }*/


public void release() {
	try
	{
    if (currentRow != null) {
        currentRow.clear();
        currentRow = null;
    }
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	try
	{
    if (resultIt != null) {
        resultIt = null;
    }
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	try
	{
    if (resultList != null) {
        resultList.clear();
        resultList = null;
    }
	}
    catch (Exception e) {
		// TODO: handle exception
	}
    try
    {
    if (columns != null) {
        columns = null;
    }
    }
    catch (Exception e) {
		// TODO: handle exception
	}
}
}

