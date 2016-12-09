/**
 * @author abhishek gupta
 * @Date 2 May 2012
 */
package com.appnetix.app.util.database;

import java.util.Iterator;

import com.appnetix.app.util.SequenceMap;

public class Documents {
	private String msDocumentName		= null;
	private String msTableAliasName		= null;
	private String tableAnchor			= null;
	private String tableSection			= null;
	private String tabName				= null;
	private TableField pTableFields		= null;
	private SequenceMap docFldMap;
	
	public Documents(String msDocumentName, SequenceMap msBuildMap,String tableAnchor, String section, String tabName) {
		this.msDocumentName		= msDocumentName;
		this.tableAnchor		= tableAnchor;
		this.docFldMap			= msBuildMap;
		this.tableSection		= section;
		this.tabName 			= tabName;
	}

	public String getDocumentName() {
		return msDocumentName;
	}
	public String getDocumentSection() {
		return tableSection;
	}
	public String getDocumentTabName() {
		return tabName;
	}
	
	public DocumentMap[] getDocumentMaps(){
		if(docFldMap == null) {
			return null;
		}
		DocumentMap[] sTableFLds = new DocumentMap[docFldMap.size()];
		Iterator it = docFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			DocumentMap table = (DocumentMap)it.next();
			sTableFLds[i++] = table;
		}
		return sTableFLds;
	}
	public DocumentMap getDocumentMap(String name){
		if(docFldMap == null) {
			return null;
		}
		Iterator it = docFldMap.values().iterator();
		for (int i=0;it!=null && it.hasNext();) {
			DocumentMap table = (DocumentMap)it.next();
			if(table.getFieldName().equals(name)) {
				return table;
			}
		}
		return null;
	}

	public String getDocumentTableAnchor() {
		// TODO Auto-generated method stub
		return tableAnchor;
	}
	
	
}