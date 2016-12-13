package com.home.builderforms;

import java.util.*;

import org.apache.log4j.Logger;

import com.home.builderforms.Info;

public class  CollectionUtil{
	static Logger logger = Logger.getLogger(CollectionUtil.class);
	public static ArrayList getArrayList(Object[] pArray){
		ArrayList list		= new ArrayList();
		if(pArray == null){
			return list;
		}
		for(int i=0; i<pArray.length; i++){
			list.add(pArray[i]);
		}
		return list;
	}

	public static String[] getStringArray(ArrayList pList){
		if(pList == null){
			return new String[0];
		}
		Iterator it		= pList.iterator();
		String[] sArray	= new String[pList.size()];
		for(int i=0; it.hasNext(); i++){
			Object obj	= it.next();
			sArray[i]	= obj.toString();
		}
		return sArray;
	}
	public static Integer[] getIntegerArray(ArrayList pList){
		if(pList == null){
			return new Integer[0];
		}
		Iterator it		= pList.iterator();
		Integer[] gArray	= new Integer[pList.size()];
		for(int i=0; it.hasNext(); i++){
			gArray[i]	= (Integer)it.next();
		}
		return gArray;
	}
	public static Integer[] getIntegerArrayFromString(String[] sArray){
		if(sArray == null){
			return null;
		}
		Integer[] gArray	= new Integer[sArray.length];
		for(int i=0; i<gArray.length; i++){
			gArray[i]		= new Integer(sArray[i]);
		}
		return gArray;
	}
	public static ArrayList convertMapToArrayList(HashMap map, boolean ofKeys){
		return convertMapToArrayList(new SequenceMap(map), ofKeys);
	}

	public static ArrayList convertMapToArrayList(SequenceMap map, boolean ofKeys){
		ArrayList list			= new ArrayList();
		if(map == null || map.isEmpty()){
			return list;
		}

		Iterator it				= null;
		if(ofKeys){
			it					= map.keys().iterator();
		}else{
			it					= map.values().iterator();
		}

		while(it != null && it.hasNext()){
			list.add(it.next());
		}
		return list;
	}


	public static SequenceMap extractSelectivelyFromMapExclude(
												SequenceMap map, String[] array, boolean useKeys)
	{
		return extractSelectivelyFromMapExclude(map, StringUtil.convertToArrayList(array), useKeys);
	}

	public static SequenceMap extractSelectivelyFromMapInclude(
												SequenceMap map, String[] array, boolean useKeys)
	{
		return extractSelectivelyFromMapInclude(map, StringUtil.convertToArrayList(array), useKeys);
	}

	public static SequenceMap extractSelectivelyFromMapExclude(
												SequenceMap map, ArrayList list, boolean useKeys)
	{
		SequenceMap returnMap	= new SequenceMap();
		if(map == null || map.isEmpty()){
			return returnMap;
		}
		if(list == null || list.isEmpty()){
			return map;
		}

		Iterator it				= null;
		if(useKeys){
			it					= map.keys().iterator();
		}else{
			it					= map.values().iterator();
		}

		while(it != null && it.hasNext()){
			String key			= (String) it.next();
			if(list.contains(key)){
				continue;
			}
			returnMap.put(key, map.get(key));
		}
		return returnMap;
	}
	
	public static SequenceMap extractSelectivelyFromMapInclude(
												SequenceMap map, ArrayList list, boolean useKeys)
	{
		SequenceMap returnMap	= new SequenceMap();
		if(map == null || map.isEmpty()){
			return returnMap;
		}
		if(list == null || list.isEmpty()){
			return map;
		}

		Iterator it				= null;
		if(useKeys){
			it					= map.keys().iterator();
		}else{
			it					= map.values().iterator();
		}

		while(it != null && it.hasNext()){
			String key			= (String) it.next();
			if(!list.contains(key)){
				continue;
			}
			returnMap.put(key, map.get(key));
		}
		return returnMap;
	}

	public static HashMap getMapFromSeparatedString(
								String querystring, String elementSeparator, String keyValueSeparator)
	{
		HashMap map									= new HashMap();
		if(StringUtil.isValid(querystring)){
			String[] stringArray					= StringUtil.convertToArray(
																		querystring, elementSeparator);
			if(stringArray == null){
				stringArray							= new String[0];
			}
			for(int i=0;i < stringArray.length;i++){
				String string						= stringArray[i];
				String[] array						= StringUtil.convertToArray(
																		string, keyValueSeparator);
				if(array == null){
					continue;
				}
				for(int j=0;j < array.length;j++){
					String key						= array[0];
					String value					= array[1];
					map.put(key, value);
				}
			}
		}
		return map;
	}
	public static int compareArrayListElements(ArrayList list1, ArrayList list2){
		int match					= 0;
		if(list1 == null || list2 == null || list1.isEmpty() || list2.isEmpty()){
			return match;
		}
		//contains is run on the smaller list
		ArrayList indexList			= list1;
		ArrayList list				= list2;
		if(list2.size() > list1.size()){
			indexList				= new ArrayList(list2);
			list					= new ArrayList(list1);
		}
		for(int i=0;i < indexList.size();i++){
			if(list.contains(indexList.get(i))){
				match++;
			}
		}
		return match;
	}
	public static SequenceMap getSequenceMap(Object[][] params){

		SequenceMap map				= new SequenceMap();

		for (int i = 0; i < params.length; i++) {

			map.put(params[i][0], params[i][1]);

		}

		return map;

	}

	public static SequenceMap sortMap(SequenceMap originalMap, String infoKey){
		return sortMap(originalMap, infoKey, null);
	}
//	This method will sort original map according to the value present in Info in map as a Double
	public static SequenceMap sortMap(SequenceMap originalMap, String infoKey, Comparator com){
		SequenceMap sortedMap = null;
		ArrayList valueList = null;
		ArrayList sortedKeysList = null;
		HashMap dataMap = null;
		Iterator it = null;
		Info dataInfo = null;
		String key = null;
		String value = null;
		Double doubleValue = null;
		logger.info("originalMap="+originalMap);
		logger.info("infoKey="+infoKey);
		logger.info("originalMap.size()="+originalMap.size());
		
		try{
		//First Prepare dataMap
		
		if(originalMap != null && StringUtil.isValidNew(infoKey)){
			valueList = new ArrayList();
			dataMap = new HashMap();
			it = originalMap.keys().iterator();
			while(it.hasNext()){
				key = (String)it.next();
				dataInfo = (Info)originalMap.get(key);
				dataMap.put(key, dataInfo.get(infoKey));
				valueList.add(Double.valueOf(dataInfo.get(infoKey)));
			}
			logger.info("dataMap="+dataMap);
			logger.info("dataMap.size()="+dataMap.size());
			logger.info("valueList="+valueList);
			if(com != null){
				Collections.sort(valueList, new DoubleComparator());	
			}else{
				Collections.sort(valueList);
			}
			
			logger.info("sorted valueList="+valueList);
			
			it = dataMap.keySet().iterator();
			sortedKeysList = new ArrayList();
			while(it.hasNext()){
				key = (String)it.next();
				value = (String) dataMap.get(key);
				doubleValue = Double.valueOf(value.toString());
				for(int i=0;i<valueList.size();i++){
					if(doubleValue.equals(valueList.get(i))){
						sortedKeysList.add(i, key);
					}
				}
			}
			logger.info("sorted sortedKeysList="+sortedKeysList);
			sortedMap = new SequenceMap();
			for(int i=0;i<sortedKeysList.size();i++){
				key = sortedKeysList.get(i).toString();
				sortedMap.put(key, originalMap.get(key));
			}
		}
		}catch(Exception e){
			
		}
		if(sortedMap == null){
			sortedMap = originalMap;
		}
		logger.info("sortedMap="+sortedMap);
		
		return sortedMap;
	}
    //Code optimization for Franchise Opener Start
	public static <K,V> Map<K,V> getHashMapFromString(K... args) {
		Map<K,V> map = null;
		if(args!=null) 
		{
			//map = NewPortalUtils.getNewHashMapWithKeyValueType();
			map = BaseUtils.getNewHashMapWithKeyValueType();      //For Product_Seperation_BL By Amar Singh.
			for(K str : args) 
			{
				map.put(str, null);
			}
		}
		return map;
	}
	//Code optimization for Franchise Opener End
}
