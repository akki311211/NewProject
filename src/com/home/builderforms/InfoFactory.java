package com.home.builderforms;

import java.util.Map;

import com.home.builderforms.Info;

/**
 * Factory class with different factory methods for {@link Info}
 * @author Vivek Maurya
 * @date 13Aug2014
 *
 */
public class InfoFactory {
	
	/**
	 * Returns an empty Info 
	 */
	public static Info getNewInfo() {
		return new Info();
	}
	
	/**
	 * Returns a new Info object with data same as passed Info. 
	 */
	public static Info getCopyInfo(Info info) {
		return new Info(info);
	}
	
	/**
	 * Returns a new Info object with data same as passed Map. 
	 */
	public static <K,V> Info getInfoFromMap(Map<K,V> m) {
		return new Info(m);
	}
	
	/**
	 * Returns a new Info object from array of ID fields. 
	 */
	public static Info getIDsInfo(String[] idFields) {
		return new Info(idFields);
	}
	
	/**
	 * Returns a new Info object from a single ID field. 
	 */
	public static Info getSingleIDInfo(String psIDField) {
		return new Info(psIDField);
	}
}
