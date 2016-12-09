/*
 * @(#)Info.java        1.00 19/01/2001
 *
 * Copyright (c) Webrizon eSolutions Limited.
 * B-31 Sector-5, Noida 201 301,India.
 * All rights reserved.
 */

/**
 */

package com.home.builderforms;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//import com.appnetix.app.util.DataReport;
import com.home.builderforms.DateTime;
import com.home.builderforms.IntConstants;
import com.home.builderforms.SequenceMap;
 


public class Info implements java.io.Serializable
{
	private String[] 		idFields;
	private SequenceMap		fields;
	private HashMap linkMap ;
	//Starts by rohit jain for BBEH_FOR_SMC_OPTIMIZATION
    private boolean convertToDisplaydate=true;// by dafault set to true for displaying DisplayDateFormat 

   //starts by rohit jain for convert to display data format
	public boolean isConvertTodisplaydate() {
		return convertToDisplaydate;
	}
	   /**
		 * when convertTodisplaydate variable set false then Info will not
	     * convert date object to Display format.
		 * @author Rohit Jain
		 * @date 15 May 2013 
		 * @param convertTodisplaydate
		 * @return
		 */
	public void setConvertTodisplaydate(boolean convertToDisplaydate) {
		this.convertToDisplaydate = convertToDisplaydate;
	}
  // ends by rohit jain for BBEH_FOR_SMC_OPTIMIZATION
	public Info()
	{
		this.fields = new SequenceMap();
	}
	
	public Info(Info info)
	{
		this.convertToDisplaydate=info.convertToDisplaydate;
		this.fields=info.fields;
		this.idFields=info.idFields;
		this.linkMap=linkMap;
	}
	
	/**
	 * P_EnH_Print<br/>
	 * Constructs a new Info from given Map.
	 */
	public <K,V> Info(Map<K,V> m) {
		this();
		if(m != null && !m.isEmpty()) {
			for(Map.Entry<K, V> entry : m.entrySet()) {
				set(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public Info(String[] idFields)
	{
		this();
		this.idFields = idFields;
	}
	public Info(String psIDField)
	{
		this(new String[]{psIDField});	
	}
	public boolean isIdField(String fieldName)
	{
		for (int i=0; i<idFields.length; i++) {
			if (idFields[i].equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public void setIDField(String[] idFields)
	{
		this.idFields = idFields;
	}

	public String[] getIDFieldName()
	{
		return idFields;
	}

	public Integer[] getIDs()
	{
		Object[] obj = getIDObject();
		Integer[] id = new Integer[obj.length];
		for (int i=0; i<id.length; i++) {
			id[i] = (Integer) obj[i];
		}
		return id;
	}

	public Integer getID()
	{
		Integer[] ids = getIDs();
		StringBuffer idBuffer = new StringBuffer();
		for (int i=0; i<ids.length; i++) {
			idBuffer.append(ids[i]);
		}
		return new Integer(idBuffer.toString());
	}

	public Object[] getIDObject()
	{
		Object[] obj = new Object[idFields.length];
		for (int i=0; i<idFields.length; i++) {
			obj[i] = getObject(idFields[i]);
		}
		return obj;
	}

	public void setID(Integer[] id)
	{
		for (int i=0; i<idFields.length; i++) {
			set(idFields[i], id[i]);
		}
	}

	public Object getObject(String key)
	{
		return fields.get(key);
	}
	/**
	 * Get value for Object key
	 * @author abhishek gupta
	 * @date 31 july 2012  
	 * @param key
	 * @return
	 */
	public Object getObject(Object key)
	{
		return fields.get(key);
	}
	
	public HashMap getLinkMap()
	{
		if(this.linkMap == null){
			linkMap = new HashMap();
		}
		return this.linkMap;
	}

	public String get(String key)
	{
		Object object = getObject(key);
		String ret = null;

		if (object != null)
		{
			if (object instanceof Timestamp)
			{
				logger.info("date instance");
                if(convertToDisplaydate) //for check DisplayDateFormat BBEH_FOR_SMC_OPTIMIZATION starts
				  ret = DateTime.getDisplayDate((Timestamp)object);
                else{
                  ret = object.toString();	//when false it convert to string object BBEH_FOR_SMC_OPTIMIZATION ends
                }
				
			} else if (object instanceof Integer)
			{
				int nVal		= ((Integer)object).intValue();

				if (nVal == IntConstants.DEFAULT_INT)
				{
					ret = null;
				} else
				{
					ret = object.toString();
				}
			} else if (object instanceof Double)
			{
				double fVal		= ((Double)object).doubleValue();

				if (fVal == IntConstants.DEFAULT_DOUBLE)
				{
					ret = null;
				} else
				{
					ret = object.toString();
				}
			} else
			{
				ret = object.toString();
			}
		}

		return ret;
	}

	public String getString(String key){
		return getString(key, "");
	}

	public String getString(String key,String defaultString)
	{
		String ret = get(key);
		if (ret == null) ret = defaultString;
		return ret;
	}

	public Integer getInteger(String key){
		Object obj = fields.get(key);
		if(obj != null && obj instanceof Integer){
			return (Integer)obj;
		}
		return null;
	}
	public Boolean getBoolean(String key){
		Object obj = fields.get(key);
		if(obj != null && obj instanceof Boolean){
			return (Boolean)obj;
		}
		return null;
	}

	public Timestamp getTimestamp(String key){
		Object obj = fields.get(key);
		if(obj != null && obj instanceof Timestamp){
			return (Timestamp)obj;
		}
		return null;
	}


	public String getFormInteger(String key){
		Object obj = fields.get(key);

		if (obj != null)
		{
			if(obj instanceof Integer){
				int nVal		= ((Integer)obj).intValue();
				if(nVal == IntConstants.DEFAULT_INT){
					return "";
				}else{
					return obj.toString();
				}
			}
		}
		return "";
	}
	public String getFormField(String key)
	{
		Object obj = fields.get(key);

		if (obj!=null)
		{
			return obj.toString();
		}
		else
		{
			return ""; 
		}
	}
	public String getFormFieldNA(String key)
	{
		Object obj = fields.get(key);

		if (obj!=null && obj.toString().trim().length()>0)
		{
			return obj.toString();
		}
		else
		{			
			return "--NA--";
		}
	}
	public String getFormFieldNumeric(String key)
	{
		Object obj = fields.get(key);

		if (obj!=null && !obj.toString().equals("-1"))
		{
			return obj.toString();
		}
		else
		{			
			return "--NA--";
		}
	}
	
	public int getKeyIndex(String key) {
		int keyIndex = 0;
		if(fields!=null) {
			return fields.getFirstIndex(key);
		} else {
			return -1;
		}
	}

	public boolean containsKey(String key) {
		if(fields.containsKey(key)) {
			return true;
		}
		
		return false;
	}
	
	public String getKey(int index) {
		String key = "";
		if(fields!=null) {
			return (fields.getKey(index)).toString();
		} else {
			return "";
		}
	}


	public void setLinkMap(Object key,Object value){
		            if(this.linkMap == null){
							linkMap = new HashMap();
					}
					this.linkMap.put(key,value);
	}

	public void set(Object key,Object value){
		fields.put(key,value);
	}
	
	public void put(Object key,Object value){
		fields.put(key,value);
	}
	
	public SequenceMap getSequenceMap() {
		return this.fields;
	}
	
	public void setAll(Info info) {
		fields.putAll(info.getSequenceMap());
	}
	
	
	public void remove(Object key){
		fields.remove(key);
	}
	
	public void insert(int index, Object key, Object value){
		fields.insert(index, key, value);
	}
	public boolean isSet(Object key){
		return fields.containsKey(key);
	}
	// Takes in a n X 2 String[] organized as { {parameter1,value1} , {paramter2, value2}...}
    // and sets the values

    /**
     * Takes in a n X 2 String[] organized as { {parameter1,value1} , {paramter2, value2}...}
     * and sets the values
     *@param  tuples   key-value Tuples
     */
    public void set(Object[][] tuples) {
        if (tuples == null) {
            throw new IllegalArgumentException("Object[][] is null");
        }
        try {
            for (int i = 0; i < tuples.length; i++) {
                set(tuples[i][0], tuples[i][1]);
            }
        } catch (IndexOutOfBoundsException ie) {
            throw new IllegalArgumentException("Object[][] not in specified format");
        }
    }

	public Iterator getKeySetIterator()
	{
	  	return fields.keys().iterator();
	}

	public Iterator getValuesIterator()
	{
		return fields.values().iterator();
	}

	public String toString()
	{
		return fields.toString();
	}

	public String getContent()
	{
		StringBuffer sb 	= new StringBuffer();
		Iterator it 		= getValuesIterator();
		while (it.hasNext())
		{
			Object value = it.next();
			if (value !=null)
			{
				sb.append(value+" ");
			}
		}
		return sb.toString().toLowerCase();
	}

	public String toText()
	{
		Iterator it = getKeySetIterator();
		StringBuffer sb = new StringBuffer();
		while (it!=null && it.hasNext())
		{
			String key = (String)it.next();
			sb.append("\n"+key+ "\t=\t" + getString(key));
		}
		return sb.toString();
	}

	public int size()
	{
		return fields.size();
	}

	public void copyFields(String[] fieldNames,Info info)
	{
		for (int i=0;i<fieldNames.length;i++)
		{
			set(fieldNames[i],info.getObject(fieldNames[i]));
		}
	}

/**
         * Returns a copy of this Info Object
         * @param 
         * @see SequenceMap.clone()
         * @see HashMap.clone()
         * @author Bhoopal
         */
        public Object clone(){
            
            Info Infocl = null;
            
            try{
                Infocl = (Info) super.clone();
                
            }catch(CloneNotSupportedException e){
                Infocl = new Info();
            }
            
            if(this.idFields != null){
            String[] IdFields = this.idFields.clone();
            
            Infocl.setIDField(IdFields);
            }
            
            if(this.linkMap != null){
            HashMap LinkMap = (HashMap)this.linkMap.clone();
            
            Object key = null;
            for(Iterator it= LinkMap.keySet().iterator(); it.hasNext() ; ){
                key = it.next();
                Infocl.setLinkMap( key, LinkMap.get(key) );
            }
            }
            
            if(this.fields != null){
            SequenceMap fields = (SequenceMap)this.fields.clone();
            
            for(int s = 0; s < fields.size(); s++){
                Infocl.set(fields.getKey(s), fields.get(s));
            }
            }
            
            return Infocl;
        }


        public void setFields(SequenceMap fields) {
        	this.fields = fields;
        }
}
