/**
 * @AUDIT_ENHANCEMENT_CHANGES
 * @author abhishek gupta
 * @Date 28 Jan 2013
 */

/*
--------------------------------------------------------------------------------------------------------
Version No.					Date			By				Against		Function Changed    Comments
PW_WITHOUT_PLAN				27 June 2013	Veerpal Singh		Removing concept of Visit Plans from Performance Wise (Now Visits will create directly with Audit Forms.)
PW_FORM_VERSION				15 July 2013	Veerpal Singh		Allow modification of Audit Forms and generating a new version of form when Visits submitted for previous versions.
----------------------------------------------------------------------------------------------------------------
*/

package com.appnetix.app.util.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.appnetix.app.util.SequenceMap;
import com.appnetix.app.util.StringUtil;
import com.appnetix.app.util.xmldao.TableXMLDAO;
import com.appnetix.app.util.xmldao.XMLUtil;

public class FormMetaData {
	private final static Logger logger			= com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(XMLUtil.class);
	
	public static final String ZERO = "0";
	public static final String ONE = "1";
	
	public static final class FormatType
	{
		public static final String F_ZERO = "Single Submit";
		public static final String F_ONE = "Multi Submit";
	}
	
	public static final class ViewType
	{
		public static final String V_ZERO = "Simple";
		public static final String V_ONE = "In Columns";
	}
	
	private SequenceMap msHeadersMap;
	private SequenceMap msTabsMap;
	private String name;
	private String formatType;
	private String viewType;
	private int responseCount;
	private String qHeader;
	private String aComments;
	private String asHeader;
	private String amsHeader;
	private boolean isOwnerRes = false;
	private boolean isPrivate = false;
	private boolean excludeNonResponse = false;
	private String frequency;//PW_WITHOUT_PLAN
	private SequenceMap versionMap = null;//PW_FORM_VERSION
	
	public FormMetaData(String name, String formatType, String viewType, String responseCount, String qHeader, String aComments, String asHeader, String amsHeader, SequenceMap msHeadersMap, SequenceMap msTabsMap, String isOwnerRes, String isPrivate, String excludeNonResponse, String frequency, SequenceMap verMap) {
		this.name 				= name;
		this.formatType			= formatType;
		this.viewType			= viewType;
		this.responseCount		= StringUtil.isvalidInteger(responseCount)?Integer.parseInt(responseCount):-1;
		this.qHeader			= StringUtil.isValid(qHeader)?qHeader:"";
		this.aComments			= StringUtil.isValid(aComments)?aComments:"";
		this.asHeader			= StringUtil.isValid(asHeader)?asHeader:"";
		this.amsHeader			= StringUtil.isValid(amsHeader)?amsHeader:"";
		this.msHeadersMap		= msHeadersMap;
		this.msTabsMap			= msTabsMap;
		this.isOwnerRes 		= StringUtil.isValid(isOwnerRes)?Boolean.parseBoolean(isOwnerRes):false;
		this.isPrivate 			= StringUtil.isValid(isPrivate)?Boolean.parseBoolean(isPrivate):false;
		this.excludeNonResponse = StringUtil.isValid(excludeNonResponse)?Boolean.parseBoolean(excludeNonResponse):false;
		this.frequency			= StringUtil.isValid(frequency)?frequency:"0";//PW_WITHOUT_PLAN
		this.versionMap			= verMap;//PW_FORM_VERSION
	}
	
	public String getFormMetaId() {
		return this.name;
	}
	
	public String getFormatType() {
		return this.formatType;
	}
	
	public String getViewType() {
		return this.viewType;
	}
	
	public int getResponseCount() {
		return this.responseCount;
	}
	
	public String getFormQHeader() {
		return this.qHeader;
	}

	public String getFormaComments() {
		return this.aComments;
	}
	
	public String getFormAsHeader() {
		return this.asHeader;
	}
	
	public String getFormAmsHeader() {
		return this.amsHeader;
	}

	public String getFormatTypeValue(String formatType) {
		if(StringUtil.isValid(formatType) && ZERO.equals(formatType)) {
			return FormatType.F_ZERO;
		} else if(StringUtil.isValid(formatType) && ONE.equals(formatType)) {
			return FormatType.F_ONE;
		} else {
			return "";
		}
	}
	
	public String getViewTypeValue(String viewType) {
		if(StringUtil.isValid(viewType) && ZERO.equals(viewType)) {
			return ViewType.V_ZERO;
		} else if(StringUtil.isValid(viewType) && ONE.equals(viewType)) {
			return ViewType.V_ONE;
		} else {
			return "";
		}
	}
	
	public HeaderColumn[] getAllHeaderColumns() {
		HeaderColumn[] hdrColumn = null;
		if(msHeadersMap == null){
			return null;
		}
		try {
			if(msHeadersMap.size() > 0) {
				hdrColumn = new HeaderColumn[msHeadersMap.size()];
				Iterator it = msHeadersMap.keys().iterator();
				int ii = 0;
				while(it.hasNext()){
					String key = (String)it.next();
					hdrColumn[ii] = (HeaderColumn)msHeadersMap.get(key);
					ii++;
				}
			}
			return hdrColumn;
		} catch(Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public FormTab[] getFormTabs() {
		FormTab[] formTab = null;
		if(msTabsMap == null){
			return null;
		}
		try {
			if(msTabsMap.size() > 0) {
				formTab = new FormTab[msTabsMap.size()];
				Iterator it = msTabsMap.keys().iterator();
				int ii = 0;
				while(it.hasNext()){
					String key = (String)it.next();
					formTab[ii] = (FormTab)msTabsMap.get(key);
					ii++;
				}
			}
			return formTab;
		} catch(Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * @PW_FORM_VERSION
	 * This will return Ordered Tab(s) of given version
	 */
	public FormTab[] getOrderedFormTabs() {
		return getOrderedFormTabs(null);
	}
	
	public FormTab[] getOrderedFormTabs(String formVersion) {
		FormTab[] formTab = null;
		Map treeMap = null;
		if(msTabsMap == null){
			return null;
		}
		try {
			if(msTabsMap.size() > 0) {
				treeMap = new TreeMap();
				Iterator it = msTabsMap.keys().iterator();
				while(it.hasNext()){
					String key = (String)it.next();
					FormTab formTabT = (FormTab)msTabsMap.get(key);
					//PW_FORM_VERSION Starts
					if(StringUtil.isValid(formVersion) && !formTabT.isValidVersion(formVersion)){
						continue;
					}
					//PW_FORM_VERSION Ends
					treeMap.put(formTabT.getOrder(), formTabT);
				}
				
				formTab = new FormTab[treeMap.size()];
				
				Iterator itkey = treeMap.keySet().iterator();
				int i = 0;
				while(itkey != null && itkey.hasNext()){
					Object key1 = itkey.next();
					formTab[i] = (FormTab)treeMap.get(key1);
					i++;
				}
			}
			return formTab;
		} catch(Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public boolean isOwnerResponse() {
		return isOwnerRes;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public boolean isExcludeNonResponse() {
		return excludeNonResponse;
	}
	
	//PW_WITHOUT_PLAN Starts
	public String getFrequency() {
		return frequency;
	}
	//PW_WITHOUT_PLAN Ends

	
	
	//PW_FORM_VERSION Starts
	public void setVersionMap(SequenceMap versionMap) {
		this.versionMap = versionMap;
	}
	
	public SequenceMap getVersionMap() {
		return versionMap;
	}
	
	public String getVersionKeyValue(String key) {
		return (String)versionMap.get(key);
	}
	
	public String getCurrentVersion() {
		return (String)versionMap.get(TableXMLDAO.CURRENT_VERSION);
	}
	//PW_FORM_VERSION Ends
	
}