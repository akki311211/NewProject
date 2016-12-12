// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.
package com.home.builderforms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.home.builderforms.CustomFileRenamePolicy;
import com.home.builderforms.FilePart;
import com.home.builderforms.FileRenamePolicy;
import com.home.builderforms.MultipartParser;
import com.home.builderforms.ParamPart;
import com.home.builderforms.Part;

/**
 * A utility class to handle
 * <code>multipart/form-data</code> requests, the kind of requests that support
 * file uploads. This class emulates the interface of
 * <code>HttpServletRequest</code>, making it familiar to use. It uses a "push"
 * model where any incoming files are read and saved directly to disk in the
 * constructor. If you wish to have more flexibility, e.g. write the files to a
 * database, use the "pull" model
 * <code>MultipartParser</code> instead. <p> This class can receive arbitrarily
 * large files (up to an artificial limit you can set), and fairly efficiently
 * too. It cannot handle nested data (multipart content within multipart
 * content). It <b>can</b> now with the latest release handle internationalized
 * content (such as non Latin-1 filenames). <p> To avoid collisions and have
 * fine control over file placement, there's a constructor variety that takes a
 * pluggable FileRenamePolicy implementation. A particular policy can choose to
 * rename or change the location of the file before it's written. <p> See the
 * included upload.war for an example of how to use this class. <p> The full
 * file upload specification is contained in experimental RFC 1867, available at
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">
 * http://www.ietf.org/rfc/rfc1867.txt</a>.
 *
 * @see MultipartParser
 *
 * @author Jason Hunter
 * @author Geoff Soutter
 * @version 1.8, 2002/04/30, added support for file renaming, thanks to
 * Changshin Lee
 * @version 1.7, 2002/04/30, added support for internationalization, thanks to
 * Changshin Lee
 * @version 1.7, 2001/02/07, made fields protected to increase user flexibility
 * @version 1.6, 2000/07/21, redid internals to use MultipartParser, thanks to
 * Geoff Soutter
 * @version 1.5, 2000/02/04, added auto MacBinary decoding for IE on Mac
 * @version 1.4, 2000/01/05, added getParameterValues(), WebSphere 2.x
 * getContentType() workaround, stopped writing empty "unknown" file
 * @version 1.3, 1999/12/28, IE4 on Win98 lastIndexOf("boundary=") workaround
 * @version 1.2, 1999/12/20, IE4 on Mac readNextPart() workaround
 * @version 1.1, 1999/01/15, JSDK readLine() bug workaround
 * @version 1.0, 1998/09/18 @auther modified by Abishek Singhal for setting
 * parameter in multipart request
 * 
 * BBEH_CLUSTER_ENVIRONMENT  15/07/2013      Rohit Jain    Class is serialized For cluster Environment.
 */
public class MultipartRequest implements java.io.Serializable {

    private static final int DEFAULT_MAX_POST_SIZE = 1024 * 5120;  // 5 Meg
    protected Hashtable parameters = new Hashtable();  // name - Vector of values
    protected Hashtable files = new Hashtable();       // name - UploadedFile
    private HttpSession session = null; //Added by Anuj 23-09-2004
    private HttpServletRequest request = null;
    private static String[] keyWords = {"onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut",
		"onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onbounce", "oncellchange",
		"oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter",
		"ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress",
		"onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove",
		"onmoveend", "onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete",
		"onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload"};

    public static final List<String> skipFields = Arrays.asList("mailtext", "mailbody", "templatetext", "mailmessage", "emailcontent", "templatebody", "ta", "text", "embedcode", "completenews", "foldersummary", "docfile", "completestory", "htmlsummary", "htmltext", "scraptext","parametervalue","ckvalue","mailcontent","message","formhtmldata","newfooter","disclaimertext","fromwhere","fromwhere1","returnpage","errorpage","jspname","from","currenturl","returningfrom","mailfrom","mailto","mailcc","mailbcc","newtemplatetext","userandemailcc","userandemailbcc");
    public static final List<String> skipStripXSSFields = Arrays.asList("cft", "nextUrl", "embedCode", "scrapText", "ta", "mailText","tabContent","newFooter","disclaimerText","emailContent","message","originalContent");
    
	/**
	 * {@link Pattern} Array for replacing different tags coming in the request parameters.
	 */
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
    
    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to 1 Megabyte. If the content is too large, an IOException is thrown.
     * This constructor actually parses the <tt>multipart/form-data</tt> and
     * throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @exception IOException if the uploaded content is larger than 1 Megabyte
     * or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory) throws IOException {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE, new CustomFileRenamePolicy());
    }

    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to the specified length. If the content is too large, an IOException is
     * thrown. This constructor actually parses the <tt>multipart/form-data</tt>
     * and throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @param maxPostSize the maximum size of the POST content.
     * @exception IOException if the uploaded content is larger than
     * <tt>maxPostSize</tt> or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            int maxPostSize) throws IOException {
        this(request, saveDirectory, maxPostSize, null, null);
    }

    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to the specified length. If the content is too large, an IOException is
     * thrown. This constructor actually parses the <tt>multipart/form-data</tt>
     * and throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @param encoding the encoding of the response, such as utf-8
     * @exception IOException if the uploaded content is larger than 1 Megabyte
     * or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            String encoding) throws IOException {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE, encoding, null);
    }

    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to the specified length. If the content is too large, an IOException is
     * thrown. This constructor actually parses the <tt>multipart/form-data</tt>
     * and throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @param maxPostSize the maximum size of the POST content.
     * @param encoding the encoding of the response, such as utf-8
     * @exception IOException if the uploaded content is larger than
     * <tt>maxPostSize</tt> or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            int maxPostSize,
            FileRenamePolicy policy) throws IOException {
        this(request, saveDirectory, maxPostSize, null, policy);
    }

    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to the specified length. If the content is too large, an IOException is
     * thrown. This constructor actually parses the <tt>multipart/form-data</tt>
     * and throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @param maxPostSize the maximum size of the POST content.
     * @param encoding the encoding of the response, such as utf-8
     * @exception IOException if the uploaded content is larger than
     * <tt>maxPostSize</tt> or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            int maxPostSize,
            String encoding) throws IOException {
        this(request, saveDirectory, maxPostSize, encoding, null);
    }

    /**
     * Constructs a new MultipartRequest to handle the specified request, saving
     * any uploaded files to the given directory, and limiting the upload size
     * to the specified length. If the content is too large, an IOException is
     * thrown. This constructor actually parses the <tt>multipart/form-data</tt>
     * and throws an IOException if there's any problem reading or parsing the
     * request.
     *
     * To avoid file collisions, this constructor takes an implementation of the
     * FileRenamePolicy interface to allow a pluggable rename policy.
     *
     * @param request the servlet request.
     * @param saveDirectory the directory in which to save any uploaded files.
     * @param maxPostSize the maximum size of the POST content.
     * @param encoding the encoding of the response, such as utf-8
     * @param policy a pluggable file rename policy
     * @exception IOException if the uploaded content is larger than
     * <tt>maxPostSize</tt> or there's a problem reading or parsing the request.
     */
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            int maxPostSize,
            String encoding,
            FileRenamePolicy policy) throws IOException {
        this(request, saveDirectory, maxPostSize, encoding, policy,null);
    }
    public MultipartRequest(HttpServletRequest request,
            String saveDirectory,
            int maxPostSize,
            String encoding,
            FileRenamePolicy policy,String fromWhere) throws IOException {
        // Sanity check values
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        if (saveDirectory == null) {
            throw new IllegalArgumentException("saveDirectory cannot be null");
        }
        if (maxPostSize <= 0) {
            throw new IllegalArgumentException("maxPostSize must be positive");
        }

        //Added by Anuj 23-09-2004
        //save the session
        this.session = request.getSession();
        this.request = request;
        // Save the dir
        File dir = new File(saveDirectory);
        //If the directory does not exist , create it
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Check saveDirectory is truly a directory
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + saveDirectory);
        }

        // Check saveDirectory is writable
        if (!dir.canWrite()) {
            throw new IllegalArgumentException("Not writable: " + saveDirectory);
        }

        // Parse the incoming multipart, storing files in the dir provided,
        // and populate the meta objects which describe what we found
        MultipartParser parser = new MultipartParser(request, maxPostSize);
        if (encoding != null) {
            parser.setEncoding(encoding);
        }

        Part part = null;
        FilePart filePart = null;
        ParamPart paramPart = null;
        String value = null;
        String name = null;
        Vector existingValues = null;
        String fileFrom="";
        try {
            while ((part = parser.readNextPart()) != null) {
                name = part.getName();
                if (part.isParam()) {
                    // It's a parameter part, add it to the vector of values
                    paramPart = (ParamPart) part;
                    value = paramPart.getStringValue();
                    existingValues = (Vector) parameters.get(name);
                    
                    if("fileFrom".equals(name)){
						fileFrom=value;
					}
                    
                    if (existingValues == null) {
                        existingValues = new Vector();
                        parameters.put(name, existingValues);
                    }
                    existingValues.addElement(value);
                } else if (part.isFile()) {
                	// It's a file part
                	filePart = (FilePart) part;
                	String fileName = filePart.getFileName();
                	String contentType = filePart.getContentType();
                
						if (fileName != null  )
						{
							if(Constants.SECURE && !checkValidContentType(fileName,contentType,fileFrom)) {
								System.out.println(">>>>>>>>>>>>>!!!Threat has been Detected!!!<<<<<<<<<<<<<<<<<<<<<\n"+fileName+"\n"+contentType);
								throw new IllegalArgumentException("Not writable: " + saveDirectory);
							} else {
								filePart.setRenamePolicy(policy);  
								filePart.writeTo(dir);
								files.put(name, new UploadedFile(dir.toString(),filePart.getFileName(),fileName,filePart.getContentType()));
							}
						}
						else
						{
							// The field did not contain a file
							files.put(name, new UploadedFile(null, null, null, null));
						}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePart != null) {
            filePart.close();
        }
        filePart = null;
        paramPart = null;
        if (parser != null) {
            parser.close();
        }
        

        String toUrl = request.getRequestURI();
        
        if(Constants.SECURE && !toUrl.contains("uploadckPicture.jsp")) {

        	String encryptToken = getParameter("cft") ;
        	String referer = request.getHeader("referer");
        	String XRequestedWith = request.getHeader("X-Requested-With");
        	System.out.println("encryptToken in multipart == "+encryptToken);
        	if(StringUtil.isValidNew(encryptToken) && encryptToken.contains("&#61;")) {
        		encryptToken = encryptToken.replaceAll("&#61;", "=");
        	}
        	System.out.println("encryptToken in session == "+session.getAttribute("csrfToken"));
        	try {
        		if(StringUtil.isValidNew(referer) && referer.indexOf(Constants.HOST_NAME) != -1 && "XMLHttpRequest".equals(XRequestedWith)) { //AJAX request
        			//do nothing
        		} 
        		else if(StringUtil.isValidNew(referer) && referer.indexOf(Constants.HOST_NAME) != -1) { //from inside the build only
        			/*if(!StringUtil.isValidNew(encryptToken)) { // P_B_63989
        				request.getSession().invalidate();
        				System.out.println("CSRF Exception: Token is not valid.");
        				return;
        			}
        			else */if(StringUtil.isValidNew(encryptToken) && !encryptToken.equals(session.getAttribute("csrfToken"))) {
        				request.getSession().invalidate();
        				System.out.println("CSRF Exception: Token doesnot match.");
        				return;
        			}
        		}else if("mailUploader".equals(fromWhere)){
        			//do nothing
        		}
        		else { //from clicking url or from outer source via AJAX or form submission
        			request.getSession().invalidate();
        			System.out.println("CSRF Exception: Outside Threat.");
        			return;
        		}
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
    }

    /**
     * Constructor with an old signature, kept for backward compatibility.
     * Without this constructor, a servlet compiled against a previous version
     * of this class (pre 1.4) would have to be recompiled to link with this
     * version. This constructor supports the linking via the old signature.
     * Callers must simply be careful to pass in an HttpServletRequest.
     *
     */
    public MultipartRequest(ServletRequest request,
            String saveDirectory) throws IOException {
        this((HttpServletRequest) request, saveDirectory);
    }

    /**
     * Constructor with an old signature, kept for backward compatibility.
     * Without this constructor, a servlet compiled against a previous version
     * of this class (pre 1.4) would have to be recompiled to link with this
     * version. This constructor supports the linking via the old signature.
     * Callers must simply be careful to pass in an HttpServletRequest.
     *
     */
    public MultipartRequest(ServletRequest request,
            String saveDirectory,
            int maxPostSize) throws IOException {
        this((HttpServletRequest) request, saveDirectory, maxPostSize);
    }

    /**
     * Returns the names of all the parameters as an Enumeration of Strings. It
     * returns an empty Enumeration if there are no parameters.
     *
     * @return the names of all the parameters as an Enumeration of Strings.
     */
    public Enumeration getParameterNames() {
        return parameters.keys();
    }

    /**
     * Returns the names of all the uploaded files as an Enumeration of Strings.
     * It returns an empty Enumeration if there are no uploaded files. Each file
     * name is the name specified by the form, not by the user.
     *
     * @return the names of all the uploaded files as an Enumeration of Strings.
     */
    public Enumeration getFileNames() {
        return files.keys();
    }

    /**
     * Returns the value of the named parameter as a String, or null if the
     * parameter was not sent or was sent without a value. The value is
     * guaranteed to be in its normal, decoded form. If the parameter has
     * multiple values, only the last one is returned (for backward
     * compatibility). For parameters with multiple values, it's possible the
     * last "value" may be null.
     *
     * @param name the parameter name.
     * @return the parameter value.
     */
    public String getParameter(String name) {
		try {
			Vector values = (Vector) parameters.get(name);
			if (values == null || values.size() == 0) {
				return null;
			}
			
			String value = (String) values.elementAt(values.size() - 1);
            if(!skipStripXSSFields.contains(name)) {
				return stripXSS(value, name);
			} else {
				return value;
			}
		} catch (Exception e) {
			return null;
		}
	}
// Added by Abishek Singhal for setting parameter in multipart request Start 4/24/2006

    /**
     * Replace the value of the named parameter if exists, or create the
     * parameter with the new value. The value is guaranteed to be in its
     * normal, decoded form. If the parameter has multiple values, only the last
     * one is modified (for backward compatibility). For parameters with
     * multiple values, it's possible the last "value" may be null.
     *
     * @param name the parameter name.
     */
    public void setParameter(String name, String val) {
    	setParameter(name, val, false);
    }
    public void setParameter(String name, String val, boolean createNew) {
        try {
            Vector values = (Vector) parameters.get(name);
            if (values == null || values.size() == 0) {
                values = new Vector();
                values.add("");
            }
            values.setElementAt(val, values.size() - 1);
            if(createNew){
            	parameters.put(name, values);
            }
        } catch (Exception e) {
            System.out.println(" Error in setParameter : " + e);
        }
    }
// Added by Abishek Singhal for setting parameter in multipart request End

    /**
     * Returns the values of the named parameter as a String array, or null if
     * the parameter was not sent. The array has one entry for each parameter
     * field sent. If any field was sent without a value that entry is stored in
     * the array as a null. The values are guaranteed to be in their normal,
     * decoded form. A single value is returned as a one-element array.
     *
     * @param name the parameter name.
     * @return the parameter values.
     */
    public String[] getParameterValues(String name) {
		try {
			Vector values = (Vector) parameters.get(name);
			if (values == null || values.size() == 0) {
				return null;
			}
			String[] valuesArray = new String[values.size()];
			Iterator itr = values.iterator();
			int i=0;
			while (itr.hasNext()){
				if(!skipStripXSSFields.contains(name)) {
					valuesArray[i] = stripXSS((String) itr.next(), name);
				} else {
					valuesArray[i] = (String) itr.next();
				}
				i++;
			}
			//values.copyInto(valuesArray);
			return valuesArray;
		} catch (Exception e) {
			return null;
		}
	}
 
    /**
     * Returns the filesystem name of the specified file, or null if the file
     * was not included in the upload. A filesystem name is the name specified
     * by the user. It is also the name under which the file is actually saved.
     *
     * @param name the file name.
     * @return the filesystem name of the file.
     */
    public String getFilesystemName(String name) {
        try {
            UploadedFile file = (UploadedFile) files.get(name);
            return file.getFilesystemName();  // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the original filesystem name of the specified file (before any
     * renaming policy was applied), or null if the file was not included in the
     * upload. A filesystem name is the name specified by the user.
     *
     * @param name the file name.
     * @return the original file name of the file.
     */
    public String getOriginalFileName(String name) {
        try {
            UploadedFile file = (UploadedFile) files.get(name);
            return file.getOriginalFileName();  // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the content type of the specified file (as supplied by the client
     * browser), or null if the file was not included in the upload.
     *
     * @param name the file name.
     * @return the content type of the file.
     */
    public String getContentType(String name) {
        try {
            UploadedFile file = (UploadedFile) files.get(name);
            return file.getContentType();  // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns a File object for the specified file saved on the server's
     * filesystem, or null if the file was not included in the upload.
     *
     * @param name the file name.
     * @return a File object for the named file.
     */
    public File getFile(String name) {
        try {
            UploadedFile file = (UploadedFile) files.get(name);
            return file.getFile();  // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the session object stored from request. Added by Anuj Paul
     * 23-09-2004
     */
    public HttpSession getSession() {
        return this.session;
    }
    
    /**
     * Returns the HttpServletRequest object.
     * 19-06-2013
     */
    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }
    
    public String stripXSS(String value, String paramName) {
		if (Constants.SECURE && value != null && !value.isEmpty()) {

			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				scriptPattern.matcher(value);
				value = scriptPattern.matcher(value).replaceAll("");
			}
			
			value = getSafeValue(value);
			if(StringUtil.isValidNew(paramName) && !skipFields.contains(paramName.toLowerCase())) {
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
			
			value = value.replaceAll("\0", "");
			value = value.replaceAll("\\../", ""); //for preventing LFI(Local File Inclusion)
			
		}
		return value;
	}
	
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
	
	public boolean checkValidContentType(String fileName,String contentType,String fileFrom)
	{
		/*ServletContext context = com.appnetix.app.control.web.handler.IModuleBroker.AppInfo.icontext;
		Properties appConfig =(Properties)context.getAttribute("ext_prop");
		Properties pageConfig =(Properties)context.getAttribute("page_prop");

		if(StringUtil.isValidNew(fileFrom)) {
			String ext=fileName.substring(fileName.lastIndexOf("."));
			String pageExt=pageConfig.getProperty(fileFrom);
			@SuppressWarnings("unchecked")
			ArrayList<String> al = StringUtil.convertToArrayList(pageExt,",");
			if(StringUtil.isValid(fileFrom) && StringUtil.isValid(pageExt) && !al.contains(ext)){
				return false;
			}
		}*/
		return true;
		/*boolean isValid=false;
		if(contentType.contains(ext)){
			isValid=true;
		}
		else if("image/jpeg".equals(contentType) && (".jpeg".equals(ext) || ".jpg".equals(ext) ) ){
			isValid=true;
		}
		else if("application/msword-template".equals(contentType) && (".dot".equals(ext) || ".dotx".equals(ext) ) ){
			isValid=true;
		}
		else if (ext.equals(appConfig.getProperty(contentType))){
			isValid=true;
		}
		else if("multipart/form-data".equals(contentType) || "text/plain".equals(contentType)) {
			isValid = true;
		}
		return isValid;*/
	}
}
