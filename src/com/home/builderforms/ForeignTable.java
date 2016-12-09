package com.home.builderforms;
import com.home.builderforms.*;

/**
 * @author Administrator
 *
 * Contains the foreign table elements of table descriptor xmls
 */
public class ForeignTable {
	private String msName;
	private SequenceMap mLinkFieldsMap;
	private boolean isExport = true;
	private boolean isImport = true;
	
	public ForeignTable(String psName, SequenceMap pLinkFieldsMap){
		msName			= psName;
		mLinkFieldsMap	= pLinkFieldsMap;
	}
	
	public ForeignTable(String psName, SequenceMap pLinkFieldsMap, String psExport){
		msName			= psName;
		mLinkFieldsMap	= pLinkFieldsMap;
		if(StringUtil.isValid(psExport) && "false".equals(psExport)) {
			isExport = false;
		} else {
			isExport = true;
		}
	}

	public ForeignTable(String psName, SequenceMap pLinkFieldsMap, String psExport, String psImport){
		msName			= psName;
		mLinkFieldsMap	= pLinkFieldsMap;
		if(StringUtil.isValid(psExport) && "false".equals(psExport)) {
			isExport = false;
		} else {
			isExport = true;
		}
		if(StringUtil.isValid(psImport) && "false".equals(psImport)) {
			isImport = false;
		} else {
			isImport = true;
		}
	}
	
	public String getName(){
		return msName;
	}
	public SequenceMap getLinkFieldsMap(){
		return mLinkFieldsMap;
	}
	public boolean isExportable() {
		return isExport;
	}
	public boolean isImportAllowed() {
		return isImport;
	}
}