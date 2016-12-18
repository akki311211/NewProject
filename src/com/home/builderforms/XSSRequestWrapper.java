package com.home.builderforms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.home.builderforms.FieldNames;
import com.home.builderforms.StringUtil;

/**
 * This class is wrapper of {@link HttpServletRequestWrapper} for preventing XSS(Cross Site Scripting)
 * @author Naman Jain
 *
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {


	private static String[] keyWords = {"onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut",
		"onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onbounce", "oncellchange",
		"oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter",
		"ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress",
		"onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove",
		"onmoveend", "onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete",
		"onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload"};
	

	public static final List<String> skipFields = Arrays.asList("mailtext", "mailbody", "templatetext", "mailmessage", "emailcontent", "templatebody", "ta", "text", "embedcode", "completenews", "foldersummary", "docfile", "completestory", "htmlsummary", "htmltext", "scraptext","parametervalue","ckvalue","mailcontent", "xmlstring", "filterxml","querystring","subject","reportheading","message","previewmailtext","sbcontactidsquery","fromwhere","assign","link","excelQuery","formhtmldata","newfooter","disclaimertext","fromwhere","fromwhere1","returnpage","errorpage","jspname","from","currenturl","returningfrom","mailfrom","mailto","mailcc","mailbcc","newtemplatetext","userandemailcc","userandemailbcc","key","message");


	public static final List<String> skipStripXSSFields = Arrays.asList("cft", "nextUrl", "embedCode", "scrapText", "ta", "mailText", "fieldsJSONArray","innerHtmls","htmlDesign","link","tabContent","editsection","htmlText","viewsection","emailContent","ckvalue","newFooter","disclaimerText","fieldsdisplayname","formHtmlData","message", "originalContent", "mailContent");
	
	private static Pattern[] skipParameterPatterns = new Pattern[] {
		Pattern.compile("value*", Pattern.CASE_INSENSITIVE)
	};
	
	private static Pattern[] skipStripParameterPatterns = new Pattern[] {
		Pattern.compile("value*", Pattern.CASE_INSENSITIVE)
	};
	
	private static Pattern[] patterns = new Pattern[] {
		// Script fragments
		Pattern.compile(">(\"|\')><script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("/(\"|\')><script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("</TextArea><script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("(--|-)><script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("\"><script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),

		// src='...'
		Pattern.compile(">(\"\')><img src[\r\n]*=[\r\n]*\\\"(.*?)\\\">", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("<img src[\r\n]*=[\r\n]*\\\'(.*?)\\\'>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// lonely script tags
		Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// eval(...)
		Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// expression(...)
		Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// javascript:...
		Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
		// vbscript:...
		Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),

		Pattern.compile("onabort(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("HoverMe(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onmouseup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("onsubmit(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		// onload(...)=...
		Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}


	public String getRequestURI() {
		String value = super.getRequestURI();
		value = stripXSS(value);
		return value;
	}

	public StringBuffer getRequestURL() {
		StringBuffer value = super.getRequestURL();
		value = new StringBuffer(stripXSS(value.toString()));
		return value;
	}


	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		
		if(!skipStripXSSFields.contains(parameter) && !skipStripParameter(parameter)) {
			return stripXSS(value, parameter);
		} else {
			return value;
		}
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		if(!skipStripXSSFields.contains(parameter) && !skipStripParameter(parameter)) {
			int count = values.length;
			String[] encodedValues = new String[count];
			for (int i = 0; i < count; i++) {
				encodedValues[i] = stripXSS(values[i], parameter);
			}
			return encodedValues;
		} else {
			return values;
		}
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if("Content-type".equals(name) || "Content-Type".equals(name)) { //no need to change this.
			return value;
		} else {
			return stripXSS(value, name);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String[]> getParameterMap() {
		return getSafeParameterMap(super.getParameterMap());
	}

	public Object getAttribute(String parameter) {
		Object value = super.getAttribute(parameter);
		if (value instanceof String && !((String) value).isEmpty()) {
			if (parameter.equalsIgnoreCase("javax.servlet.forward.request_uri") || parameter.equalsIgnoreCase("javax.servlet.forward.query_string")) {
				value = stripXSS((String) value, parameter);
			}
		}
		return value;
	}


	public String stripXSS(String value) {
		return stripXSS(value, null);
	}
	
	public boolean skipStripParameter(String paramName) {
		boolean flag = false;
		for (Pattern scriptPattern : skipStripParameterPatterns) {
			if(scriptPattern.matcher(paramName).find()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean skipParameter(String paramName) {
		boolean flag = false;
		for (Pattern scriptPattern : skipParameterPatterns) {
			if(scriptPattern.matcher(paramName).find()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public String stripXSS(String value, String paramName) {
		if(true){
		if (value != null && !value.isEmpty()) {

			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				scriptPattern.matcher(value);
				value = scriptPattern.matcher(value).replaceAll("");
			}

			value = getSafeValue(value);

			if(StringUtil.isValidNew(paramName) && !skipFields.contains(paramName.toLowerCase()) && !skipParameter(paramName)) {
				//value = value.replaceAll("&", "&#38;");
				/*value = value.replaceAll("(", "&#40;");
				value = value.replaceAll(")", "&#41;");
				value = value.replaceAll("+", "&#43;");
				value = value.replaceAll("-", "&#45;");*/
				//value = value.replaceAll(";", "&#59;");
				//value = value.replaceAll("\"", "&#34;");
				//value = value.replaceAll("'", "&#39;");
				value = value.replaceAll("<", "&#60;");
				value = value.replaceAll(">", "&#62;");
				value = value.replaceAll("%", "&#37;");
				value = value.replaceAll("\\(", "&#40;");
				value = value.replaceAll("\\)", "&#41;");
				value = value.replaceAll("=", "&#61;");
			}
			if(StringUtil.isValidNew(value)){
			value = value.replaceAll("\0", "");
			value = value.replaceAll("\\.\\./", ""); //for preventing LFI(Local File Inclusion)
			}
		}
		}
		return value;
	}

	private Map<String, String[]> getSafeParameterMap(Map<String, String[]> parameterMap) {
		Map<String, String[]> newMap = new HashMap<String, String[]>();
		Iterator<String> iter = parameterMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String[] oldValues = (String[]) parameterMap.get(key);
			String[] newValues = new String[oldValues.length];

			for (int i = 0; i < oldValues.length; i++) {
				String newVal = stripXSS(oldValues[i]);
				newValues[i] = newVal;
			}
			newMap.put(key, newValues);
		}
		return newMap;
	}

	/**
	 * This function is used to tampered the keywords which is used for XSS.
	 * @param oldValue
	 * @return
	 */
	private String getSafeValue(String oldValue) {
		if (oldValue != null && !oldValue.isEmpty()) {
			StringBuffer sb = new StringBuffer(oldValue);
			String lowerCase = oldValue.toLowerCase();
			for (int i = 0; i < keyWords.length; i++) {
				int x = -1;
				while ((x = lowerCase.indexOf(keyWords[i])) >= 0) {
					if (keyWords[i].length() == 1) {
						sb.replace(x, x + 1, " ");
						lowerCase = sb.toString().toLowerCase();
						continue;
					}
					sb.deleteCharAt(x + 1);
					lowerCase = sb.toString().toLowerCase();
				}
			}

			return sb.toString();
		} else {
			return null;
		}
	}

}
