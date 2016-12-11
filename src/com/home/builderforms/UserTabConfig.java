package com.home.builderforms;

import java.io.Serializable;

/**
 * @author omindra
 */
public class UserTabConfig implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String userTabId;
	private String rowNo;
	private String module;
	private String subModule;
	private String href;
	private String name;
	private String internalName;
	//ENH_MODULE_CUSTOM_TABS starts
	private String isCustom;
	private String keyName;
	//ENH_MODULE_CUSTOM_TABS ends
    private String tabDisplayName;
	public String getUserTabId() {
		return userTabId;
	}

	public void setUserTabId(String userTabId) {
		this.userTabId = userTabId;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	//ENH_MODULE_CUSTOM_TABS starts
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(String isCustom) {
		this.isCustom = isCustom;
	}
	//ENH_MODULE_CUSTOM_TABS ends
	
}
