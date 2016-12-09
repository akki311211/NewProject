package com.home.builderforms;
/**
 *This class provides the definitions for all the tables data as stored in tables.xml.
 *@author abhishek gupta
 *@created  Nov 18, 2011
 */
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;

import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

import com.appnetix.app.util.Debug;
import com.home.builderforms.Info;

public class BuilderXMLDAO {
	public static HashMap loadTables(String location) {
        Element root = null;
        try {
        	root = loadDocument(location);
	        if (root == null) {
	            return null;
	        }
	        return getTables(root);
        } catch(Exception e) {
        	e.printStackTrace();
        	return null;
        } finally {
        	root = null;
		}
    }

    private static Element loadDocument(String location) {
        Document doc = null;
		InputStream inputStream = null;
		InputSource xmlInp = null;
		DocumentBuilderFactory docBuilderFactory =null; 
		DocumentBuilder parser = null;	
		Element root = null;
        try {
            URL url = new URL(location);
			inputStream = url.openStream();
            xmlInp = new InputSource(inputStream);
            if (xmlInp == null) {
                Debug.println("Unable not make reader");
            }
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            root = doc.getDocumentElement();

            return root;
        } catch (Exception pce) {
            Debug.println("BuilderXMLDAO error: " + pce);
        }finally {
			try {
				inputStream.close();
				inputStream = null;
				doc = null;
				xmlInp = null;
				docBuilderFactory =null; 
				parser = null;	
				root = null;
			}catch (IOException e) {
			}
		}
        return null;
    }
    /**
     *Gets the Tables attribute of the BuilderXMLDAO class
     *@param  root   Parameter
     *@return       The table value
     */
    public static HashMap getTables(Element root) {
		HashMap	tables	= new HashMap();
        NodeList nodeNameList = null;
        Node tableNode = null;
		NodeList elementNodeList	=	null;
		String nodeName = "";
		NodeList elementList = null;
		NodeList tableList = null;
		HashMap	tableElement	=	null;
		HashMap tableElements = null;
		HashMap table = null;
		Info info = null;
		
		try {
            tableList = root.getElementsByTagName(BuilderConstants.TABLE_DEFINITION);
 			
            for (int outerLoop = 0; outerLoop < tableList.getLength(); outerLoop++) {
                tableNode = tableList.item(outerLoop);
                info = getNodeAttributes(tableNode);
				String tableID = (String) info.get(BuilderConstants.TABLE_ID);
				
				tableElements = new HashMap();
				table = new HashMap();
				table.put(BuilderConstants.TABLE_ID, tableID);
				if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element tableEle = (Element) tableNode;
					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.TABLE_NAME);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.TABLE_NAME, nodeName);

					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.TABLE_DESCRIPTION);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.TABLE_DESCRIPTION, nodeName);
					
					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.PARAMETER_TABLE_ID);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.PARAMETER_TABLE_ID, nodeName);
					
					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.GROUPING_PARAMETER);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.GROUPING_PARAMETER, nodeName);
					
					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.SEQUENCE_MAP_KEY);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.SEQUENCE_MAP_KEY, nodeName);
					
					nodeNameList = tableEle.getElementsByTagName(BuilderConstants.TABLE_CLASS);
					nodeName = (String) getValue(nodeNameList, false);
					table.put(BuilderConstants.TABLE_CLASS, nodeName);

					elementList = tableEle.getElementsByTagName(BuilderConstants.TABLE_ELEMENT);
					for (int loop = 0; loop < elementList.getLength(); loop++) {
						Node elementNode = elementList.item(loop);
						tableElement	=	new HashMap();
						String elementType = "";
						Element element = null;
						if (elementNode instanceof Element) {
							element = ((Element)elementNode);
							elementType = element.getAttribute(BuilderConstants.TABLE_ELEMENT_TYPE);
							tableElement.put(BuilderConstants.TABLE_ELEMENT_TYPE, elementType);
						}
						if (element != null && elementType != null) {
							elementNodeList	=	element.getElementsByTagName(BuilderConstants.QUERY);
							setQuery(elementNodeList, tableElement);

							elementNodeList=  element.getElementsByTagName(BuilderConstants.COMPARE_COLUMNS);
							int[] comparisonColumns = getColumns(elementNodeList);
							tableElement.put(BuilderConstants.COMPARE_COLUMNS, comparisonColumns);

							elementNodeList=  element.getElementsByTagName(BuilderConstants.DIFFERENCE_COLUMNS);
							int[] differenceColumns = getColumns(elementNodeList);
							tableElement.put(BuilderConstants.DIFFERENCE_COLUMNS, differenceColumns);

							elementNodeList=  element.getElementsByTagName(BuilderConstants.COLUMN_CAPTIONS);
							String[] columnCaptions = getColumnCaptions(elementNodeList);
							tableElement.put(BuilderConstants.COLUMN_CAPTIONS, columnCaptions);
							
							elementNodeList=  element.getElementsByTagName(BuilderConstants.COLUMN_NAMES);
							String[] columnNames = getColumnCaptions(elementNodeList);
							tableElement.put(BuilderConstants.COLUMN_NAMES, columnNames);
							
							elementNodeList=  element.getElementsByTagName(BuilderConstants.COLUMN_TYPES);
							String[] columnName = getColumnCaptions(elementNodeList);
							tableElement.put(BuilderConstants.COLUMN_TYPES, columnName);
						}
						table.put(elementType, tableElement);
					}
					tables.put(tableID, table);
                }
            }
            return tables;
        } catch (Exception e) {
        	e.printStackTrace();
        } finally{
            try{
            	 tableNode = null;
                 tableList = null;
                 tableNode = null;
                 elementNodeList = null;
                 elementList = null;
                 tableElement = null;
         		 tableElements = null;
        		 table = null;
        		 info = null;
             }catch(Exception e){}
         }
        return new HashMap();
    }
    /**
     *  Gets the value attribute of the BuilderXmlDAO class
     *@param  list          Parameter
     *@param  tableArray   Parameter
     *@return              The value
     */
    public static Object getValue(NodeList list, boolean tableArray) {
        ArrayList returnList = new ArrayList();
        Node node = null;
        Node childNode = null;
        String value = null;
        try {
    		if (list != null) {
    			int len = list.getLength();
    			if (len > 1 && !tableArray) {
    				Debug.println("Warning : Element defined more than once in XML file.Using the first value");
    			}
    			for (int i = 0; i < list.getLength(); i++) {
    				node = list.item(i);
    				childNode = node.getFirstChild();
    				if (childNode != null) {
    					if (childNode.getNodeType() == Node.TEXT_NODE) {
    						value = childNode.getNodeValue();
    						if (value != null) {
    							if (value.equals("") || value.equals("\r")) {
    								continue;
    							} else {
    								returnList.add(value);
    							}
    						} else {
    							Debug.println("Value is null");
    						}
    					}
    				}
    			}
    			if (tableArray) {
    				return returnList;
    			} else {
    				if (returnList.size() != 0) {
    					return (String) returnList.get(0);
    				} else {
    					Debug.println("ERROR : Element not defined .");
    					return "";
    				}
    			}
    		} else {
    			return null;
    		}
        } catch(Exception e) {
        	return null;
        } finally {
        	childNode = null;
	    	node = null;
	    	value = null;
		}
    }
    /**
     *  Gets the attributes attribute of the BuilderXmlDAO class
     *@param  nodeList   Parameter
     *@return           The attributes value
     */
    public static ArrayList getAttributes(NodeList nodeList) {
        ArrayList returnList = new ArrayList();
        Node node = null;
        NamedNodeMap nodeMap = null;
        Attr attribute = null;
        Node tempNode = null;
        try {
            int len = nodeList.getLength();
            for (int i = 0; i < len; i++) {
                node = nodeList.item(i);
                nodeMap = node.getAttributes();
                int size = nodeMap.getLength();
                if (size == 0) {
                	Debug.println("Warning : No attribute defined. Atrribute Expected.");
                }
                if (size > 1) {
                    Debug.println("Warning : More than 1 attributes defined.Using first attribute value.");
                }
                tempNode = nodeMap.item(0);
                if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
                    attribute = (Attr) tempNode;
                    returnList.add(attribute.getValue());
                }
            }
        } catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	nodeMap = null;
	    	node = null;
	    	tempNode = null;
	    	attribute = null;
		}
        return returnList;
    }
    /**
     *Gets the attributes attribute of the BuilderXmlDAO class
     *@param  node   Parameter
     *@return       The attributes value
     */
    public static Info getNodeAttributes(Node node) {
    	Info returnList = new Info();
    	NamedNodeMap nodeMap = null;
    	Node tempNode = null;
    	Attr attribute = null;
    	try {
	        nodeMap = node.getAttributes();
	        int size = nodeMap.getLength();
	        if (size == 0) {
	        	Debug.println("Warning : No attribute defined. Atrribute Expected.");
	        	returnList = null;
	        }
	        for (int j = 0; j < nodeMap.getLength(); j++) {
	            tempNode = nodeMap.item(j);
	            if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
	                attribute = (Attr) tempNode;
	                returnList.set(attribute.getName(), attribute.getValue());
	            }
	        }
    	} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	nodeMap = null;
	    	tempNode = null;
	    	attribute = null;
		}
        return returnList;
    }
    public static ArrayList getAttributes(Node node) {
    	ArrayList returnList = new ArrayList();
    	NamedNodeMap nodeMap = null;
    	Node tempNode = null;
    	Attr attribute = null;
    	try {
	        nodeMap = node.getAttributes();
	        int size = nodeMap.getLength();
	        if (size == 0) {
	            Debug.println("Warning : No attribute defined. Atrribute Expected.");
	        	returnList = null;
	        }
	        for (int j = 0; j < nodeMap.getLength(); j++) {
	            tempNode = nodeMap.item(j);
	            if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
	                attribute = (Attr) tempNode;
	                returnList.add(attribute.getValue());
	            }
	        }
    	} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	nodeMap = null;
	    	tempNode = null;
	    	attribute = null;
		}
        return returnList;
    }

	public static int[] getColumns(NodeList compareList) {
		String compare = null;
		String str	= null;
		StringTokenizer strTokens = null;
		try {
			if (compareList.getLength() > 0) {
				compare = (String)getValue(compareList, false);
				if (compare != null || !compare.equals("")) {
					strTokens = new StringTokenizer(compare,",");
					int index = strTokens.countTokens();
					if (index == 0) {
						index = 1;
					}
					int[] compareColumns = new int[index];
					int i = 0;
					while (strTokens.hasMoreTokens()) {
						str	= strTokens.nextToken();
						if (str != null && !str.equals("")) {
								compareColumns[i] = Integer.parseInt(str);
						}
						i++;
					}
					return compareColumns;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
            try{
        		compare = null;
        		strTokens = null;
        		str			= null;
            }catch(Exception e){}
        }
		return null;
	}

	public static String[] getColumnCaptions(NodeList compareList) {
		String compare = null;
		StringTokenizer strTokens = null;
		String str			= null;
		try {
			if (compareList.getLength() > 0) {
				compare = (String)getValue(compareList, false);
				if (compare != null || !compare.equals("")) {
					strTokens = new StringTokenizer(compare,",");
					int index = strTokens.countTokens();
					if (index == 0) {
						index = 1;
					}
					String[] columnCaptions = new String[index];
					int i = 0;
					while (strTokens.hasMoreTokens()){
						str	= strTokens.nextToken();
						if (str != null && !str.equals("")) {
							columnCaptions[i] = str.trim();
						}
						i++;
					}
					return columnCaptions;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
            try{
        		compare = null;
        		strTokens = null;
        		str			= null;
            }catch(Exception e){}
        }
		return null;
	}

	public static void setQuery(NodeList queryList, HashMap tableElement) {
		Node	queryNode = null;
		NodeList childList = null;
		Node node = null;
		Node childNode = null;
		Element queryElement = null;
		String value = "";
		String name  = null;
		try {
			for (int k = 0; k < queryList.getLength(); k++) {
				queryNode = queryList.item(k);
				queryElement = (Element)queryNode;
	
				childList = queryElement.getChildNodes();
				for (int i = 0; i < childList.getLength(); i++) {
					node = childList.item(i);
					name  = node.getNodeName();
					childNode = node.getFirstChild();
					if (childNode != null) {
						value = childNode.getNodeValue();
						tableElement.put(name, value);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
            try{
        		value = "";
        		name  = null;
            	queryNode = null;
    			childList = null;
    			node = null;
    			childNode = null;
    			queryElement = null;
            }catch(Exception e){}
        }
	}
}