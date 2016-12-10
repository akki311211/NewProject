package com.home.builderforms;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.appnetix.app.components.builderformmgr.manager.BuilderFormsMgr;
import com.appnetix.app.struts.actions.AbstractAppAction;
import com.home.builderforms.FieldNames;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.Info;

/**
 * Purpose of this form is to display summary data
 * @author abhishek gupta
 * @version 1.1.1.1
 * @date 14/11/2011
 */
public class BuilderFormSummaryAction extends AbstractAppAction{
	//HttpServletRequest request;
	public String execute() throws Exception {
		Info info = null;
		SequenceMap sMap = getBuilderFormsMgr().getBuilderFormFieldsDAO().getFullData(null,null);
		Iterator itInfo = sMap.values().iterator();
		int count=1;
		StringBuffer methodBuffer				= new StringBuffer();

		while(itInfo.hasNext()){
			Info currentInfo=(Info)itInfo.next();
			// Adding Actions
			String goSingleAction	= "javascript:void(0);redirect(?,'"+currentInfo.get(FieldNames.BUILDER_FORM_FIELDS_ID)+"')";
			String actions = listOfActions();
			String links = listOfLinks();
			if(actions.length() > 4){
				methodBuffer.append("prepareActionsMenu2("+actions+","+ links+", "+actions+", ");
				methodBuffer.append(count + ", " + "\"" + goSingleAction + "\"" + ",'dynamicmenu');\n");
			}
			currentInfo.set(FieldNames.ACTION,actions.length() > 4 ? getMenuBar(count++,request): "--");
		}
		request.setAttribute("sMap",sMap);		
		request.setAttribute("menuBar",methodBuffer.toString());	
		return "success";
	}

	public BuilderFormsMgr getBuilderFormsMgr() {
			BuilderFormsMgr builderFormsMgr 		= null;
			builderFormsMgr = new BuilderFormsMgr();
			return builderFormsMgr;
	}

	public String getMenuBar(int count, HttpServletRequest request) {
		String menuBar = "<div id=\"menuBar\"><layer ID=\"Actions_dynamicmenu"+count+"Bar\">"
			+ " <a href=\"javascript:void(0);\" onMouseOver=\"openPulldownMenu('Actions_dynamicmenu"+count+"');\""
			+ " onMouseOut=\"closePulldownMenu('Actions_dynamicmenu"+count+"');\">"
			+ " <img src=\""+request.getContextPath()+"/images/edit.gif\" border=\"0\"></a></layer></div>";
			return menuBar;
	}

	private String listOfActions(){
		String actions="[";
		actions=actions+"'Modify','']";
	
		return actions;
	}

	private String listOfLinks(){
		String links="[";
		links=links+"'0','']";
	
		return links;
	} 
}
