package com.home.builderforms;

import java.util.HashMap;

/**
 * Factory class with different factory methods for {@link SequenceMap}
 * @author Vivek Maurya
 * @date 13Aug2014
 */
public class SequenceMapFactory {
	
	/**
	 * Method returns an empty SequenceMap object.
	 */
	public static <K, V> SequenceMap<K, V> getNewSequenceMap() {
		return new SequenceMap<K, V>();
	}
	
	/**
	 * Method returns a new SequenceMap object with data same as passed HashMap.
	 */
	public static <K, V> SequenceMap<K,V> getSequenceMapFromHashMap(
			HashMap<? extends K, ? extends V> map) {
		return new SequenceMap<K, V>(map);
	}

	/**
	 * Method returns a new SequenceMap object with data same as passed SequenceMap.
	 */
	public static <K, V> SequenceMap<K,V> getCopySequenceMap(
			SequenceMap<? extends K, ? extends V> sMap) {
		return new SequenceMap<K, V>(sMap);
	}
}
