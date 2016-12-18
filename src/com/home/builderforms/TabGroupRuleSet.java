package com.home.builderforms;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class TabGroupRuleSet extends RuleSetBase
{

    public TabGroupRuleSet()
    {
    }

    public void addRuleInstances(Digester digester)
    {
        digester.addObjectCreate("tabGroup/tabs", "com.appnetix.app.util.tabs.TabGroup");
		digester.addSetProperties("tabGroup/tabs");
		digester.addSetNext("tabGroup/tabs", "addTabGroup", "com.appnetix.app.util.tabs.TabGroup");
		digester.addSetProperties("tabGroup/tabs/row");
        digester.addObjectCreate("tabGroup/tabs/row/tab", "com.appnetix.app.util.tabs.Tab");
        digester.addSetProperties("tabGroup/tabs/row/tab");
		digester.addSetNext("tabGroup/tabs/row/tab", "addTabToMap", "com.appnetix.app.util.tabs.Tab");
	}
}