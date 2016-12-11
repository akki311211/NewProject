package com.appnetix.app.portal.role;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;

public class DefaultUrlMap implements java.io.Serializable
{
	public static final List<String> userLevels = Arrays.asList("0","1","2","6");
	
	private Map<String,SequenceMap<String,String>> defaultUrls;

	public static DefaultUrlMap getInstance()
	{
		return new DefaultUrlMap();
	}
	
	private DefaultUrlMap()
	{
		defaultUrls = new HashMap<String,SequenceMap<String,String>>();
	}
	
	public Map<String,SequenceMap<String,String>> getDefaultUrls()
	{
		return defaultUrls;
	}
	
	public void addUrl(String userLevel,String moduleCode,String url)
	{
		if(userLevels.contains(userLevel) && StringUtil.isValid(url))
		{
			SequenceMap<String,String> urlMap = defaultUrls.get(userLevel);
			if(urlMap==null)
			{
				urlMap = new SequenceMap<String, String>();
				defaultUrls.put(userLevel, urlMap);
				if("0".equals(userLevel)){
					defaultUrls.put("6", urlMap);
				}
			}
			urlMap.put(moduleCode, url);
		}
	}

	public SequenceMap<String,String> getDefaultUrls(String userlevel)
	{
		return defaultUrls.get(userlevel);
	}
}
