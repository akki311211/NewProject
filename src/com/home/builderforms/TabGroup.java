/**

 * $Header: com.appnetix.app.util.toolbar
 * $Author: tomcat $
 * $Version: 1.1 $
 * $Date: 2014/07/14 10:10:18 $
 *
**/

package com.appnetix.app.util.tabs;

import com.appnetix.app.util.SequenceMap;
import java.util.ArrayList;

public class TabGroup
{
	private String rowid = "0";
	private String subModule = "";
	private SequenceMap tabsMap = new SequenceMap();

	public void setRowid(String id){
		rowid = id;
	}
	public void setSubModule(String val){
		subModule = val;
	}
	public String getSubModule(){
		return subModule;
	}

	//method for adding tabs to the Map
	public void addTabToMap(Tab tab){
		ArrayList rowTabs = (ArrayList) tabsMap.get(rowid);

		if (rowTabs == null){
			rowTabs = new ArrayList();
			tabsMap.put(rowid, rowTabs);
		}
		rowTabs.add(tab);
	}

	//Method to access tabsMap
	public SequenceMap getTabsMap(){
		return tabsMap;
	}
}