package com.frw.util;

import java.io.File;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLUtil {
	static Element root=null;
	//http://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/create-xml-file-in-java-using-dom-parser-example/
	/**
	 * Generates the testng runnable xml with executable suites xml path with listener class
	 * @author sahamed
	 * @Date Jun 14 2014
	 * @param xmlFilePath
	 * @param suiteFolder
	 * @param suites
	 * @param suiteName
	 */
	public static void createTestNGXML(String xmlFilePath,String suiteFolder,LinkedHashMap<String,String> suites,String suiteName){
		try{
			System.out.println("****************** Creating TestNG XML **************");
			DocumentBuilderFactory 	documentFactory = DocumentBuilderFactory.newInstance();
			 
		  	DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		  	Document document = documentBuilder.newDocument();
		  	root=createElementWithAttribute(document,"suite","name",suiteName);
		  	Element listenerparent =createElement(document, "listeners");
		  	createElementChildAttribute(document,listenerparent,"listener","class-name","com.proj.listener.TestsListenerAdapter");
		  	Element suitefiles =createElement(document, "suite-files");
		  	if(!suiteFolder.equalsIgnoreCase("")){
		  		suiteFolder=suiteFolder+"/";
		  	}
		  	for (String key :suites.keySet()){
		  		createElementChildAttribute(document,suitefiles,"suite-file","path","./"+suiteFolder+key+".xml");
		  	}
		  	
		  	// create the xml file

            //transform the DOM Object to an XML File

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(xmlFilePath)); 
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");

		 
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Creates an element with given attribute in the xml
	 * @author sahamed
	 * @Date Jun 14 2014
	 * @param document
	 * @param elementTagName
	 * @param attributTagName
	 * @param attributeValue
	 * @return
	 */
	private static Element createElementWithAttribute(Document document,String elementTagName,String attributTagName,String attributeValue){
		Element element = document.createElement(elementTagName);
	  	document.appendChild(element);
	  	Attr attr = document.createAttribute(attributTagName);
	  	attr.setValue(attributeValue);
	  	element.setAttributeNode(attr);
		return element;
	}
	
	/**
	 * Creates an element in the xml
	 * @author sahamed
	 * @Date Jun 14 2014
	 * @param document
	 * @param elementTagName
	 * @return
	 */
	private static Element createElement(Document document,String elementTagName){
		Element element = document.createElement(elementTagName);
	  	root.appendChild(element);
	  	return element;
	  	
	}
	 
	/**
	 * Creates an Child Element in the xml
	 * @author sahamed
	 * @Date Jun 14 2014
	 * @param document
	 * @param parentElement
	 * @param elementTagName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Element createElementChild(Document document,Element parentElement,String elementTagName){
		Element element = document.createElement(elementTagName);
	  	parentElement.appendChild(element);	  	
	  	return element;
	  	
	}
	/**
	 * Creates an Child element with attribute in the xml
	 * @author sahamed
	 * @Date Jun 14 2014
	 * @param document
	 * @param parentElement
	 * @param elementTagName
	 * @param attributTagName
	 * @param attributeValue
	 * @return
	 */
	private static Element createElementChildAttribute(Document document,Element parentElement,String elementTagName,String attributTagName,String attributeValue){
		Element element = document.createElement(elementTagName);
	  	parentElement.appendChild(element);	  	
	  	Attr attr = document.createAttribute(attributTagName);
	  	attr.setValue(attributeValue);
	  	element.setAttributeNode(attr);
	  	return element;
	  	
	}
	
}



