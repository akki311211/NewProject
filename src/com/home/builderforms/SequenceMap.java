package com.home.builderforms;

/*
 * ---------------------------------------------------------------------------------------------------
 * Version No. 			Date 			By 			Against 		Function 		Changed 		Comments
 * ----------------------------------------------------------------------------------------------------
 * P_E_FIN_UPGRADE	  28 Dec 2010	Vivek Maurya	Enh
 * 
 * ----------------------------------------------------------------------------------------------------
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import com.home.builderforms.DoubleComparator;
import org.apache.commons.collections.comparators.ReverseComparator;

import com.home.builderforms.BuilderFormFieldNames;
import com.home.builderforms.Info;



public class SequenceMap<K,V> implements java.io.Serializable
{
	private HashMap<K,V> keyValues;

	private ArrayList<K> keyList;


	public SequenceMap() {

		this.keyValues = new HashMap<K,V>();
		this.keyList = new ArrayList<K>();
	}

	public SequenceMap(HashMap<? extends K,? extends V> map){

		this.keyValues = new HashMap<K,V>();
		this.keyList = new ArrayList<K>();

		putAll(map);
	}

	public SequenceMap(SequenceMap<? extends K,? extends V> sMap) {
		this();
		putAll(sMap);
	}

	/**
	 * This function put [Key,Value] pair in an HashMap and
	 * Corresponding keys in ArrayList maintained separately but only  
	 * after checking for the duplicacy of that key in it.
	 * 
	 * @param key : key as an Object
	 * @param value : Value as an Object
	 */

	public void put(K key,V value) {

		keyValues.put(key,value);

		int index = keyList.indexOf(key);

		if (index == -1) {
			keyList.add(key);
		}
		else {
			keyList.set(index,key);
		}
	}

	/**
	 * This function put [Key,Value] pair in an HashMap and
	 * Corresponding keys in ArrayList at a particular index ,
	 * thus replacing any key at that index.
	 * @param pnIndex : as an Integer value
	 * @param key : as an Object
	 * @param value : as an Object
	 */
	public void insert(int pnIndex, K key, V value) {

		keyValues.put(key,value);
		keyList.add(pnIndex,key);
	}

	/**
	 * This function put all the key,value pairs in hashmap and keylist
	 * after checking their duplicacy but takes SequenceMap as an argument. 
	 * @param map : as an SequenceMap Object
	 */
	public void putAll(SequenceMap<? extends K,? extends V> map) {

		if (map == null) return;

		for(K key : map.keys()) {
			put(key, map.get(key));
		}

	}

	/**
	 * This function put all the key,value pairs in hashmap and keylist
	 * after checking their duplicacy but takes HashMap as an argument. 
	 * @param map : as an HashMap Object
	 */

	public void putAll(HashMap<? extends K,? extends V> map) {

		if (map == null) return;

		for(Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}

	}

	/**
	 * This function returns the value from HashMap corresponding to the given 
	 * key passed in the argument.
	 * @param key : as an Object
	 * @return HashMap
	 */

	public V get(Object key) {
		return keyValues.get(key);
	}

	/**
	 * Removes the record from HashMap by matching the given key.
	 * @param key : as an Object.
	 */

	public void remove(Object key) {

		int index = keyList.indexOf(key);

		if (index != -1) {
			keyList.remove(index);
			keyValues.remove(key);
		}
	}

	/**
	 * Removes the record from HashMap by matching the given index in the ArrayList.
	 * If the index is found then it removes that key from it and also corresponding
	 * Key,Value pair from HashMap.
	 * @param pnIndex : as an integer value
	 */

	public void remove(int pnIndex) {

		if(keyList.size() > pnIndex) {
			Object key	= keyList.get(pnIndex);
			keyValues.remove(key);
			keyList.remove(pnIndex);
		}
	}

	/**
	 * This function removes keys in the range 'pnIndexFrom' to 'pnIndexTo' 
	 * from the ArrayList and corresponding records in HashMap. 
	 * @param pnIndexFrom : as an integer parameter
	 * @param pnIndexTo : as an integer value
	 */

	public void remove(int pnIndexFrom, int pnIndexTo) {

		if(pnIndexFrom >= pnIndexTo || pnIndexTo >= keyValues.size()) {
			return;
		}

		for(int i = pnIndexFrom; i < pnIndexTo; i++) {
			Object key	= keyList.get(pnIndexFrom);
			keyValues.remove(key);
			keyList.remove(pnIndexFrom);
		}
	}

	/**
	 * This function clears all the records in both ArrayList and HashMap.
	 *
	 */

	public void clear() {

		keyValues.clear();
		keyList.clear();
	}


	/**
	 * Returns the value of the corresponding key by finding the 
	 * given index(as argument) in the ArrayList if found otherwise 
	 * returns null reference.  
	 * @param pnIndex : as an integer value.
	 * @return Object
	 */
	public V get(int pnIndex) {

		if(keyList.size() > pnIndex){
			Object key	= keyList.get(pnIndex);
			return keyValues.get(key);
		}

		return null;

	}

	/**
	 * Returns all the keys corresponding to a given value if found otherwise 
	 * returns null.
	 * @param pObj : as an Object
	 * @return key as an Object
	 */
	public K getKey(Object pObj) {

		for(Map.Entry<K, V> entry : keyValues.entrySet()) {
			if(entry.getValue().equals(pObj)) {
				return entry.getKey();
			}
		}
		return null;

	}
	/**
	 * Returns the key at a particular index.
	 * @param pnIndex : as an integer value.
	 * @return key as an Object
	 */

	public K getKey(int pnIndex) {

		if(keyList.size() > pnIndex) {
			return keyList.get(pnIndex);
		}

		return null;
	}

	/**
	 * Returns the first index ,if found, of the key.
	 * @param pObj : as an Object
	 * @return key as an Object
	 */
	public int getFirstIndex(Object pObj) {

		if(pObj == null) {
			return -1;
		}

		return keyList.indexOf(pObj);
	}

	/**
	 * Returns the last index ,if found, of the key.
	 * @param pObj
	 * @return
	 */
	public int getLastIndex(Object pObj) {

		if(pObj == null) {
			return -1;
		}

		return keyList.lastIndexOf(pObj);
	}


	/**
	 * Returns the Collection of values corresponding to all
	 * the keys in the HashMap. 
	 * @return as an ArrayList object
	 */
	public Collection<V> values() {

		List<V> list 	= new ArrayList<V>();

		for(K key : keyList) {
			list.add(keyValues.get(key));
		}

		return list;

	}


	/**
	 * Returns all the keys as an collection.
	 * @return as an ArrayList
	 */

	public Collection<K> keys() {
		return keyList;
	}


	/**
	 * Returns the number of keys in a collection.
	 * @return integer as an value
	 */

	public int size() {
		return keyList.size();
	}



	/**
	 * Returns the string representation of the collection
	 *  in 'Key = [ Value1,Value2......ValueN]'.That is all 
	 *  the values to a corresponding key.
	 *  @return as an String object
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer("(");
		boolean flag 	= true;

		for(K key : keyList) {
			Object value = get(key);

			if(flag) {
				flag = false;
			}
			else {
				sb.append(",");
			}
			//System.out.println("key   = = = "+key);
			if(key!=null)
			sb.append(key.toString()).append(" = ").append(toText(value));
		}
		sb.append(")");

		return sb.toString();

	}


	/**
	 * Returns the comma separated representation of the all 
	 *  the values to a corresponding key.
	 *  'Key = [ Value1,Value2......ValueN]'. 
	 * @param value as an Object
	 * @return String as an Object
	 */

	public String toText(Object value) {

		if (value == null) 
			return null;

		if (!value.getClass().isArray()) 
			return String.valueOf(value);

		return Arrays.toString((Object[])value);
	}



	/**
	 * Returns boolean value indicating whether
	 * a HashMap contains a given key or not.
	 * @param pObject as an Object.
	 * @return boolean value
	 */
	public boolean containsKey(Object pObject) {

		if(keyValues.containsKey(pObject)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns a clone of the current collection
	 * by creating a new collection and copying all 
	 * records in it.
	 * @return Object
	 */

	public SequenceMap<K,V> clone() {

		SequenceMap<K,V> cloneMap = new SequenceMap<K,V>();
		cloneMap.putAll(this);

		return cloneMap;
	}

	public boolean isEmpty() {
		return keyList.isEmpty();
	}


	/**
	 * Returns Hashmap representation of the collection.
	 * @return
	 */

	public HashMap<K,V> getHashMap() {
		return keyValues;
	}

	/**
	 * Returns the map in a sorted order.Sorted 
	 * either on keys or on values
	 * @param ascending : for order
	 * @param onKeys : to define sorting on keys
	 * @return SequenceMap object.
	 */

	public SequenceMap<K,V> sort(boolean ascending, boolean onKeys) {
		return sort(ascending, onKeys, false);
	}
	public SequenceMap<K,V> sort(boolean ascending, boolean onKeys, boolean isDoubleValueComparison) {
		return sort(ascending, onKeys, isDoubleValueComparison, false,null);
	}
	public SequenceMap<K,V> sort(boolean ascending, boolean onKeys, boolean isDoubleValueComparison, boolean isInfoComparison, String sortingByInfoField) {

		SequenceMap<K,V> map = new SequenceMap<K,V>();

		try{
			if(onKeys) {

				TreeMap<K,V> treeMap = new TreeMap<K,V>();

				Iterator<K> it = this.keys().iterator();

				while(it != null && it.hasNext()) {
					K key = it.next();
					V value	= this.get(key);

					treeMap.put(key, value);
				}

				for(Map.Entry<K, V> entry : treeMap.entrySet()) {
					map.put(entry.getKey(), entry.getValue());
				}

			} else {
				
				TreeMap<V,K> treeMap = null;
				
				if(isDoubleValueComparison){
					treeMap = new TreeMap<V,K>(new DoubleComparator());
				}else if(isInfoComparison){
					if(sortingByInfoField!=null && !"".equals(sortingByInfoField)){
						treeMap = new TreeMap<V,K>(new InfoComparatorSeq(sortingByInfoField, true));
					}else{
						treeMap = new TreeMap<V,K>();
					}
				}else{				
					treeMap = new TreeMap<V,K>();
				}

				for(V value : this.values()) {
					K key = this.getKey(value);
					if(key == null) {
						continue;
					}

					treeMap.put(value, key);
				}

				for(Map.Entry<V, K> entry : treeMap.entrySet()) {
					map.put(entry.getValue(), entry.getKey());
				}

			}
		} catch(Exception e) {
			return this;
		}

		return map;

	}

	/**
	 * Returns a copy of this SequenceMap Object even by cloning its container objects if they are of type Info.
	 * Use this method if you sure that the state of Info objects in this SequenceMap object will be changed in the future,
	 * and you want these changes not to be reflected in the cloned one.
	 * @param 
	 * @see Info.clone()
	 * @see HashMap.clone()
	 * @author Bhoopal
	 */
	public Object cloneInfo() {

		SequenceMap<K,V> CloneMap = null;

		try {
			CloneMap = (SequenceMap<K,V>)super.clone();
		} catch(CloneNotSupportedException e) {
			CloneMap = new SequenceMap<K,V>();
		}

		V valObj = null;
		for(K key : this.keys()) {
			valObj = this.keyValues.get(key);
			if(valObj.getClass() == Info.class) {
				CloneMap.put(key, (V)((Info)valObj).clone());
			} else {
				CloneMap.put(key, valObj);
			}
		}

		return CloneMap;
	}

	/** return the sequenceMap of difference of the given two sequenceMaps
	 * @param maphigh
	 * @param maplow
	 * @return SequenceMap
	 * @author Bhoopal
	 **/

	public static <K,V> SequenceMap<K,V> differSequenceMaps(SequenceMap<K,V> maphigh, 
			SequenceMap<K,V> maplow){

		SequenceMap<K,V> retmap = null;

		if(maphigh != null && maplow != null){
			retmap = (SequenceMap<K,V>)maphigh.clone();

			for(Object key : maplow.keys()){
				retmap.remove(key);
			}
		}

		return retmap;
	}

	//P_E_FIN_UPGRADE added by vivek maurya starts

	/**
	 * Returns the map in a sorted order.Sorted on valueObjKey, i.e if the Values in the Map are also Objects of Info or SequenceMap, and within
	 * these sub objects we need the sorting.
	 * @param ascending : for order
	 * @param valueObjKey : to define sorting key
	 * @return SequenceMap object.
	 */

	public SequenceMap sortOnValueObjectKey(String valueObjKey, boolean ascending) {

		SequenceMap map = new SequenceMap();

		try {
			TreeMap treeMap = new TreeMap();
			if(ascending)
				treeMap = new TreeMap();
			else
				treeMap = new TreeMap(new ReverseComparator());

			Iterator it = this.keys().iterator();
			int i=0;
			while(it != null && it.hasNext()){

				Object key = it.next();
				String newKey = new String();
				Object valueObj = this.get(key);

				if(valueObj.getClass().isInstance(new Info())){
					Info info = (Info) valueObj;
					info.put("originalKey", key);
					newKey = info.get(valueObjKey);
				} else if(valueObj.getClass().isInstance(new SequenceMap())) {
					SequenceMap subMap = (SequenceMap) valueObj;
					subMap.put("originalKey", key);
					newKey = (String) subMap.get(valueObjKey);
				}
				//System.out.println("i++ "+newKey+i++);
				treeMap.put(newKey+i++, valueObj);//modified for duplicate column value

			}

			it = treeMap.keySet().iterator();

			while(it != null && it.hasNext()){

				Object key = it.next();
				Object valueObj = treeMap.get(key);
				Object originalKey = new Object();

				if(valueObj.getClass().isInstance(new Info())){
					Info info = (Info) valueObj;
					originalKey = info.get("originalKey");
				} else if(valueObj.getClass().isInstance(new SequenceMap())) {
					SequenceMap subMap = (SequenceMap) valueObj;
					originalKey = subMap.get("originalKey");
				}

				map.put(originalKey, valueObj);

			}

		} catch(Exception e) {
			return this;
		}

		return map;

	}
	//P_TRAINING_SORTING:19-08-2011:Ankit Saini:START
	public SequenceMap sortOnValueStringKey(String valueObjKey, boolean ascending){

		SequenceMap map	= new SequenceMap();

		try{

			TreeMap treeMap = new TreeMap();
			if(ascending)
				treeMap = new TreeMap();
			else
				treeMap = new TreeMap(new ReverseComparator());

			Iterator it = this.keys().iterator();
			int i=0;
			while(it != null && it.hasNext()){

				Object key = it.next();
				String newKey = new String();
				Object valueObj = this.get(key);

				if(valueObj.getClass().isInstance(new Info())){
					Info info = (Info) valueObj;
					info.set("originalKey", key);
					newKey = info.get(valueObjKey).toLowerCase();
					//System.out.println("newKey---"+newKey);
				} 
				//System.out.println("i++ "+newKey+i++);
				treeMap.put(newKey+i++, valueObj);//modified for duplicate column value

			}

			it = treeMap.keySet().iterator();

						while(it != null && it.hasNext()){

							Object key = it.next();
							Object valueObj = treeMap.get(key);
							String originalKey = new String();

							if(valueObj.getClass().isInstance(new Info())){
								Info info = (Info) valueObj;
								originalKey = info.getString("originalKey");
							}

							map.put(originalKey, valueObj);

						}

		}catch(Exception e){
			e.printStackTrace();
			return this;

		}

		return map;

	}
	//P_TRAINING_SORTING:19-08-2011:Ankit Saini:END


	public SequenceMap<K,V> getSubMap(int startIndex, int recordsLimit) {

		SequenceMap<K,V> map = new SequenceMap<K,V>();

		if(startIndex < 0 || startIndex > this.size() || recordsLimit <=0)
			return null;

		for(int i=0; i<this.size(); i++) {
			if(i >= startIndex && i < (startIndex+recordsLimit)) {
				map.put(this.getKey(i), this.get(i));
			}
		}

		return map;
	}

	//P_E_FIN_UPGRADE added by vivek maurya ends


	public SequenceMap sortOnObjectKey(String valueObjKey, boolean ascending){
		SequenceMap map	= new SequenceMap();
		try{
			Map treeMap = null;
			if(ascending)
				treeMap = new TreeMap();
			else
				treeMap = new TreeMap(new ReverseComparator());

			Iterator it = this.keys().iterator();
			int i=0;
			while(it != null && it.hasNext()){
				Object key = it.next();
				Object newKey = new String();
				Object valueObj = this.get(key);
				if(valueObj.getClass().isInstance(new Info())){
					Info info = (Info) valueObj;
					newKey = info.get(valueObjKey);
				} else if(valueObj.getClass().isInstance(new SequenceMap())) {
					SequenceMap subMap = (SequenceMap) valueObj;
					newKey = subMap.get(valueObjKey);
				}
				if(valueObjKey.equals(BuilderFormFieldNames.ORDER_NO)) {
					treeMap.put(Integer.parseInt(newKey.toString()), valueObj);//modified for duplicate column value
				} else {
					treeMap.put(newKey, valueObj);//modified for duplicate column value
				}
			}

			it = treeMap.keySet().iterator();
							while(it != null && it.hasNext()){
								Object key = it.next();
								Object valueObj = treeMap.get(key);
								map.put(key, valueObj);
							}

		}catch(Exception e){
			return this;
		}
		return map;
	}
}

class InfoComparatorSeq implements Comparator

{

	private String fieldName;

	private boolean ascending;



	public InfoComparatorSeq(String fieldName,boolean ascending)

	{

		this.fieldName = fieldName;

		this.ascending = ascending;

	}



	public int compare(Object obj1,Object obj2)

	{

		Info info1 = (Info)obj1;

		Info info2 = (Info)obj2;

		int ret = 0;

		Object value1 = info1.get(fieldName);

		Object value2 = info2.get(fieldName);

		if (value1 == null && value2 == null)

		{

			ret = 0;

		}

		else if (value1 == null )

		{

			ret = 1;

		}

		else if (value2 == null )

		{

			ret= -1;

		}

		else if (!value1.getClass().getName().equals(value2.getClass().getName()))

		{

			throw new RuntimeException("fieldValues of different classes, cannot compare");

		}

		else if (value1 instanceof Comparable)

		{
			double b1 = Double.valueOf(value1.toString());

			double b2 = Double.valueOf(value2.toString());

			if(b1 == b2)
			{
				ret = 0;
			}
			else if(b1  > b2)
			{
				ret = 1;
			}
			else if(b1 < b2)
			{
				ret = -1;
			}
			
		}

		else if (value1 instanceof Boolean)

		{

			if (value1.equals(value2))

			{

				ret = 0;

			}

			else

			{

				if (value1.equals(Boolean.FALSE))

				{

					if (ascending)

					{

						ret = -1;

					}

					else

					{

						ret = 1;

					}

				}

				else

				{

					if (ascending)

					{

						ret = 1;

					}

					else

					{

						ret = -1;

					}

				}

			}

		}
		else if(value1 instanceof Double)
		{
			double b1 = Double.valueOf(value1.toString());

			double b2 = Double.valueOf(value2.toString());

			if(b1 == b2)
			{
				ret = 0;
			}
			else if(b1  > b2)
			{
				ret = 1;
			}
			else if(b1 < b2)
			{
				ret = -1;
			}			
		}
		
		return ret;

	}	
}
