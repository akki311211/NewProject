package com.home.builderforms;

import java.io.*;

import java.util.Random;
import java.util.Scanner;

import org.xml.sax.InputSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import org.w3c.dom.Document;

import org.w3c.dom.NodeList;

import org.w3c.dom.Node;

import org.w3c.dom.NamedNodeMap;

import org.jdom.output.XMLOutputter;

import org.xml.sax.SAXException;

import org.xml.sax.SAXParseException;

import org.apache.xml.serialize.*;
import com.home.builderforms.Debug;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.net.URL;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;


import com.home.builderforms.Constants;
import com.home.builderforms.FieldNames;
import com.home.builderforms.SequenceMap;
import com.home.builderforms.StringUtil;
import com.home.builderforms.Info;

public class XMLUtil
{


	public static Element loadDocument(String location)
	{
		Element root = null;
		try
		{
			root = getDocument(location).getDocumentElement();
		}catch(Exception ex)
		{
		}
		return root;
	}

	public static Document getDocument(String location)
	{
		Document doc = null;
		FileInputStream inputFiles = null;
		try
		{
			File file = new File(location);

			inputFiles = new FileInputStream(file);
			InputSource xmlInp = new InputSource(inputFiles);

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();

			doc = parser.parse(xmlInp);
		}catch(Exception ex)
		{
		}finally
		{
			try
			{
				inputFiles.close();
			}catch(Exception e)
			{
			}
		}
		return doc;
	}

	public static Element loadDocumentNew(String location)
	{
		Element root = null;
		Document doc = loadXmlDocument(location);
		if(doc != null)
		{
			root = doc.getDocumentElement();
		}
		return root;
	}

	public static Document loadXmlDocument(String location)
	{
		Document doc = null;
		InputStream in = null;
		URL url = null;

		try
		{
			url = new URL(location);
			in = url.openStream();
			InputSource xmlInp = new InputSource(in);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
			doc = parser.parse(xmlInp);
		}catch(SAXParseException err)
		{
		}catch(Exception ex)
		{
		}finally
		{
			if(in != null)
			{
				try
				{
					in.close();
					in = null;
				}catch(Exception e)
				{
				}
			}
		}
		return doc;
	}

	public static Node getNodeInChildren(Node node, String name)
	{
		Node retNode = null;
		NodeList children = node.getChildNodes();

		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);

			if(child.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}

			String nodeName = child.getNodeName();

			if(name.equals(nodeName))
			{
				retNode = child;
				break;
			}
		}
		return retNode;
	}

	public static Node[] getNodesInChildren(Node node, String name)
	{
		ArrayList list = new ArrayList();

		NodeList children = node.getChildNodes();

		for(int i = 0; i < children.getLength(); i++)
		{

			Node child = children.item(i);

			if(child.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}

			String nodeName = child.getNodeName();

			if(name.equals(nodeName))
			{
				list.add(child);
			}
		}

		Node[] nodes = new Node[list.size()];

		Iterator it = list.iterator();

		for(int i = 0; it.hasNext(); i++)
		{
			nodes[i] = (Node) it.next();
		}
		return nodes;
	}

	public static Node[] getNodesInChildren(Node node)
	{
		ArrayList list = new ArrayList();

		NodeList children = node.getChildNodes();

		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);

			if(child.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}
			list.add(child);
		}

		Node[] nodes = new Node[list.size()];

		Iterator it = list.iterator();

		for(int i = 0; it.hasNext(); i++)
		{
			nodes[i] = (Node) it.next();
		}
		return nodes;
	}

	public static String getAttributeValue(Node node, String name)
	{
		String ret = null;

		NamedNodeMap attributes = node.getAttributes();

		Node attrNode = attributes.getNamedItem(name);

		if(attrNode != null)
		{
			ret = attrNode.getNodeValue();
		}

		return ret;
	}

	public static Node getTagNode(Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);

		int listLength = list.getLength();

		if(listLength < 1)
		{
			return null;
		}

		else if(listLength > 1)
		{
		}

		return list.item(0);
	}

	public static String getTagText(Element element, String name)
	{
		Node node = getTagNode(element, name);

		String tagText = null;

		if(node != null)
		{
			tagText = node.getFirstChild().getNodeValue();
		}
		return tagText;
	}

	public static String getTagText(Node node, String name)
	{
		String tagText = null;

		Node retNode = getNodeInChildren(node, name);

		if(retNode != null)
		{

			tagText = retNode.getFirstChild().getNodeValue();

		}
		return tagText;
	}

	public static String getTagTextValue(Node node, String name)
	{
		String tagText = null;

		if(node != null)
		{
			tagText = node.getFirstChild().getNodeValue();
		}
		return tagText;
	}

	public static void createXML(String pLocation, org.jdom.Document pDoc) throws IOException
	{
		XMLOutputter out = new XMLOutputter();

		File file = new File(pLocation);

		if(file.exists())
		{

			file.delete();

			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file.toString());

		try
		{
			out.output(pDoc, fos);

			fos.flush();

		}finally
		{
			if(fos != null)
			{
				try
				{
					fos.close();
					fos = null;
				}catch(Exception e)
				{
				}
			}
		}
	}

	public static void writeDOM(Document doc, OutputStream os) throws IOException
	{
		OutputFormat of = new OutputFormat("XML", "UTF-8", false);

		of.setPreserveSpace(true);

		XMLSerializer xmlSerializer = new XMLSerializer(os, of);

		xmlSerializer.serialize(doc);

		os.flush();
	}

	public static void writeDOMToFile(Document doc, String fileName) throws IOException, FileNotFoundException
	{
		FileOutputStream fileOutStr = null;
		try
		{
			File file = new File(fileName);

			if(file.exists())
			{
				file.delete();
			}

			fileOutStr = new FileOutputStream(fileName);

			writeDOM(doc, fileOutStr);

		}catch(IOException e)
		{
			throw e;
		}finally
		{
			if(fileOutStr != null)
			{
				try
				{
					fileOutStr.close();
					fileOutStr = null;
				}catch(Exception e)
				{
				}
			}
		}
	}

	public static Info getNodeAttributesInfo(Node node)
	{
		return new Info(getNodeAttributesMap(node).getHashMap());
	}

	public static HashMap<String, String> getNodeAttributes(Node node)
	{
		return getNodeAttributesMap(node).getHashMap();
	}

	public static HashMap<String, String> getAttributesMap(Node node)
	{
		return getNodeAttributesMap(node).getHashMap();

	}

	public static Object getObjectNodeAttributes(Node node, String instance)
	{
		if("Info".equals(instance))
		{
			return getNodeAttributesInfo(node);
		}
		else
		{
			return getNodeAttributesMap(node);
		}
	}
	
	public static SequenceMap<String, String> getNodeAttributesMap(Node node)
	{
		SequenceMap<String, String> returnList = new SequenceMap<String, String>();
		NamedNodeMap nodeMap = null;
		Node tempNode = null;
		Attr attribute = null;
		try
		{
			nodeMap = node.getAttributes();
			int size = nodeMap.getLength();
			if(size == 0)
			{
				returnList = null;
			}
			for(int j = 0; j < nodeMap.getLength(); j++)
			{
				tempNode = nodeMap.item(j);
				if(tempNode.getNodeType() == Node.ATTRIBUTE_NODE)
				{
					attribute = (Attr) tempNode;
					returnList.put(attribute.getName(), attribute.getValue());
				}
			}
		}catch(Exception e)
		{

		}finally
		{
			nodeMap = null;
			tempNode = null;
			attribute = null;
		}
		return returnList;
	}

	public static String createXMLFromString(String inputXml, String fileName, String path)
	{
		try
		{
			String filePath = FieldNames.EMPTY_STRING;
			BufferedWriter output = null;
			String tempDirPath = MultiTenancyUtil.getTenantConstants().DOCUMENTS_DIRECTORY + File.separator + path;
			if(!(new File(tempDirPath)).exists())
			{
				boolean success = (new File(tempDirPath)).mkdirs();
				if(!success)
				{
					return "Error_creating File.";
				}
			}
			filePath = tempDirPath + File.separator + fileName;
			File file = new File(filePath);
			output = new BufferedWriter(new FileWriter(file));
			output.write(inputXml);
			output.close();
			return filePath;
		}catch(Exception e)
		{
			return FieldNames.EMPTY_STRING;
		}

	}

	public static boolean validateXML(String xsdFilePath, String xmlFilePath) throws SAXException, ParserConfigurationException, IOException
	{
		boolean flag = false;
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File schemaLocation = new File(xsdFilePath);
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlFilePath));
			flag = true;
		}catch(SAXException e)
		{
			flag = false;
			throw e;
		}catch(IOException e)
		{
			flag = false;
			throw e;
		}
		return flag;
	}

	/**
	 * This Method parse an XML string and returns a Map. 
	 * Returns blank Map in case of string is invalid or null.
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXmlStringtoMap(String xml) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		if(StringUtil.isValid(xml))
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document document = builder.parse(is);
			if(document != null && document.hasChildNodes())
			{
				String nodeValue;
				Element root = document.getDocumentElement();
				NodeList nodeList = root.getChildNodes();
				for(int i = 0; i < nodeList.getLength(); i++)
				{
					Node node = nodeList.item(i);
					nodeValue = node.getFirstChild().getNodeValue();
					if(StringUtil.isValid(nodeValue))
					{
						nodeValue = nodeValue.trim();
					}
					dataMap.put(node.getNodeName(), nodeValue);
				}
			}
		}
		return dataMap;
	}

}
