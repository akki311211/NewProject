/**

 * $Package: com.home.builderforms.toolbar
 * $Author: akashg $
 * $Version: 1.1 $
 * $Date: 2016/03/08 09:32:40 $
 * Modified By sandeep On 30 June 2006
 *
-------------------------------------------------------------------------------------------------------------
 Version No.	Date		By			   Function Changed                			Comments
-------------------------------------------------------------------------------------------------------------
P_FS_E_2121          10/12/07    Suchita Agrawal         Tab()           Added get, set method for onclick condition
 * FIN_E_PRIVILEGE   MAY27 2008   Bhoopal Reddy            set and getprivilegeid          provided privilegeid
 * FIM_TABS_PRIVILEGE_CHECK   10/05/2012         Sanjeev Kumar                       PrivilegeUrl Check Regarding To Different Tabs In FIM
-------------------------------------------------------------------------------------------------------------
**/

package com.home.builderforms;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tab
{
	@Override
	public String toString() {
		return "Tab [href=" + href + ", onClick=" + onClick + ", displayName="
				+ displayName + ", tabID=" + tabID + ", internalName="
				+ internalName + ", isCustom=" + isCustom +", tabDisplayName="+tabDisplayName+", tabOrder="+tabOrder+"]";
	}
	private  SequenceMap pathMap 	= new SequenceMap();
	private  String href 			= "";
    private  String onClick 		= "";//P_FS_E_2121
	private  String displayName 	= "";
	private  String dependsOn 		= null;
	private  String userLevel 		= null;
	private  String privileged 		= "false";
    private  String condition		= null;
    private  String privilegeid 	= null;
    private  String privilegeUrl 	= null;//FIM_TABS_PRIVILEGE_CHECK
	private  String tabID 			= null;    
    private  String internalName 	= null;
	private  String isCustom 		= "N";//ENH_MODULE_CUSTOM_TABS
	private String tabDisplayName=null;//BB-20150203-259(Tab Rename changes)
	private String tabOrder="0";//BB-20150203-259 (Tab Re-ordering changes) 
        //Added to Support Mobils Tabs
        private String mobileTab = null;
        private String tabular = null;
        private String mobileHref = null;
        private String viewRoles=null;//P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB
        //Mobile ends
        
	//Set and get Methods
	public SequenceMap getPathMap(){
		return pathMap;
	}
	public void setPath(String val){
		if (val.indexOf(",") != -1){
			StringTokenizer paths = new StringTokenizer(val, ",");
			String path = "";
			while (paths.hasMoreTokens()){
				path = paths.nextToken();
				pathMap.put(path,path);
			}
		}else
			pathMap.put(val, val);
	}

	public String getHref(){
		return href;
	}
	public void setHref(String val){
		href = val;
	}
        
        //P_FS_E_2121.. start
        public String getOnClick(){
            return onClick;
        }
	public void setonClick(String val){
		onClick = val;
	}
        //P_FS_E_2121.. end
        
        public String getDependsOn() {
            return dependsOn;
        }

        public void setDependsOn(String dependsOn) {
            this.dependsOn = dependsOn;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
	public String getDisplayName(){
		return displayName;
	}
	public void setDisplayName(String val){
		displayName = val;
	}
	public String getUserLevel(){
		return userLevel;
	}
	public void setUserLevel(String val){
		userLevel = val;
	}

	public String getPrivileged(){
		return privileged;
	}
	public void setPrivileged(String val){
		privileged = val;
	}        
        public String getCondition(){
		return condition;
	}

    
        /**
         * 
         * @return String privligeid
         */
    public String getPrivilegeid() {
        return privilegeid;
    }
    public String getPrivilegeUrl() {      //FIM_TABS_PRIVILEGE_CHECK
		return privilegeUrl;
	}
	
    /**
     * 
     * @param privilegeid
     */
    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid;
    }
    public void setPrivilegeUrl(String privilegeUrl) { //FIM_TABS_PRIVILEGE_CHECK
		this.privilegeUrl = privilegeUrl;
	}
    
    public String getTabID(){
		return tabID;
	}
	public void setTabID(String tabIDIn){
		tabID = tabIDIn;
	}
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}		
	
	//ENH_MODULE_CUSTOM_TABS starts
    public String getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(String isCustom) {
		this.isCustom = isCustom;
	}
	//ENH_MODULE_CUSTOM_TABS ends
	public String getTabDisplayName()
	{
		return tabDisplayName;
	}
	//BB-20150203-259 starts(Tab Rename changes)
	public void setTabDisplayName(String tabDisplayName)
	{
		if(StringUtil.isValidNew(tabDisplayName)){
		//this.displayName=tabDisplayName;
		this.tabDisplayName=tabDisplayName;
		}
	}
	//BB-20150203-259 ends(Tab Rename changes)
	//BB-20150203-259 (Tab Re-ordering changes) starts
	public String getTabOrder()
	{
		return tabOrder;
	}
	public void setTabOrder(String tabOrder)
	{
	
		this.tabOrder=tabOrder;
		
	}
	//BB-20150203-259 (Tab Re-ordering changes) ends

    /**
     * @return the mobileTab
     */
    public String getMobileTab() {
        return mobileTab;
    }

    /**
     * @param mobileTab the mobileTab to set
     */
    public void setMobileTab(String mobileTab) {
        this.mobileTab = mobileTab;
    }

    /**
     * @return the tabular
     */
    public String getTabular() {
        return tabular;
    }

    /**
     * @param tabular the tabular to set
     */
    public void setTabular(String tabular) {
        this.tabular = tabular;
    }

    /**
     * @return the mobileHref
     */
    public String getMobileHref() {
        return mobileHref;
    }

    /**
     * @param mobileHref the mobileHref to set
     */
    public void setMobileHref(String mobileHref) {
        this.mobileHref = mobileHref;
    }
  //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB starts
    public void setCanViewRoles(String viewRoles){
    	this.viewRoles=viewRoles;
    }
    public String getCanViewRoles(){
    	return viewRoles;
    }
  //P_ENH_VIEW_WRITE_PRIVILEGE_CUSTOM_TAB ends
}