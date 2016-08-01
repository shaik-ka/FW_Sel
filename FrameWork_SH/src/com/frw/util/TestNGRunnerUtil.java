package com.frw.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.wrapper.TestNGRunnerWrapper;

public class TestNGRunnerUtil {
	private static String isTestXMl=Constants_FRMWRK.True;
	private static Xls_Reader suiteXlsReader=null;
	public static boolean isAllTestsParsed=true;
	
	
	
	/**
	 * Runner method for generating the xml for each suite marked as Y in the main suite file
	 * @author sahamed
	 * @Date Jun 11 2014
	 * @param pathofSuiteFiles
	 * @param suiteFilesExtension
	 * @param suiteFileName
	 * @param packagePattern
	 * @return
	 * @throws IOException
	 */
	public static LinkedHashMap <String,String> testNGXMLrunner(String pathofSuiteFiles,String suiteFilesExtension,String suiteFileName,String packagePattern,String suiteMode) throws IOException{
		String isTestXMLCreated=Constants_FRMWRK.True;
		String pathofSuitesFile;
		
		pathofSuitesFile=FileUtil.getfilePathUnderSearch(pathofSuiteFiles, suiteFileName, suiteFilesExtension);
		Xls_Reader suiteXlss=new Xls_Reader(pathofSuitesFile);
		
		LinkedHashMap <String,String>runnableSuites=new LinkedHashMap<String,String>();
		LinkedHashMap <String,String>runnableTests=new LinkedHashMap<String,String>();
		//runnableSuites=TestNGRunnerWrapper.fetch_SuitesRunnable(suiteXlss);
		runnableSuites=TestUtil.fetch_SuitesRunnable(suiteXlss,suiteMode);
		System.out.println("Number of runnableSuites are "+runnableSuites);
		WaitUtil.pause(1);
		
		for (String key : runnableSuites.keySet()) {
	      System.out.println("Runnable suite-"+ runnableSuites.get(key));
	       suiteFileName=FileUtil.getfilePathUnderSearch(pathofSuiteFiles, runnableSuites.get(key), suiteFilesExtension);
	       // suiteXlsReader=new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\nuview\\suite\\"+ runnableSuites.get(key)+" suite.xlsx");
	        suiteXlsReader=new Xls_Reader(suiteFileName);
	        
	        if(suiteXlsReader.getRowCount(Constants_FRMWRK.TC_SHEET_NAME)>1){
	        	//  runnableTests=TestNGRunnerWrapper.fetch_SuiteTestsRunnable(suiteXlsReader);
	        	runnableTests=TestUtil.fetch_SuiteTestsRunnable(suiteXlsReader,suiteMode);
	        	WaitUtil.pause(1);	    
	        	isTestXMLCreated=createTestSuiteXML(packagePattern,runnableTests,runnableSuites.get(key),key);
	        	if(isTestXMLCreated.equalsIgnoreCase(Constants_FRMWRK.False)){
	        		isAllTestsParsed=false;
	        	}
	        }
	    }
		
		
		return runnableSuites;	
	}
	/**
	 * Generates xml for each suite marked as Y in the main suite file and export the suite names into text file
	 * @author sahamed
	 * @Date Jun 11 2014
	 * @param pathofSuiteFiles
	 * @param suiteFilesExtension
	 * @param suiteFileName
	 * @param pathofTextFile
	 * @param packagePattern
	 * @return
	 * @throws IOException
	 */
	public static LinkedHashMap <String,String> testNGXMLrunner(String pathofSuiteFiles,String suiteFilesExtension,String suiteFileName,String pathofTextFile,String packagePattern,String testxmlfilePath,String suiteMode) throws IOException{
		String isTestXMLCreated=Constants_FRMWRK.True;
		String pathofSuitesFile;
		pathofSuitesFile=FileUtil.getfilePathUnderSearch(pathofSuiteFiles, suiteFileName, suiteFilesExtension);
		Xls_Reader suiteXlss=new Xls_Reader(pathofSuitesFile);
		
		LinkedHashMap <String,String>runnableSuites=new LinkedHashMap<String,String>();
		LinkedHashMap <String,String>runnableTests=new LinkedHashMap<String,String>();
		//runnableSuites=TestNGRunnerWrapper.fetch_SuitesRunnable(suiteXlss);
		runnableSuites=TestUtil.fetch_SuitesRunnable(suiteXlss, suiteMode);
		System.out.println("Runnable Suites are "+runnableSuites);
		
       FileUtil.createTextFile(pathofTextFile);
	
		for (String key : runnableSuites.keySet()) {	  
		//	 System.out.println("Runnable suite-"+ runnableSuites.get(key));
	        suiteFileName=FileUtil.getfilePathUnderSearch(pathofSuiteFiles, runnableSuites.get(key), suiteFilesExtension);
	        suiteXlsReader=new Xls_Reader(suiteFileName);
	       // runnableTests=TestNGRunnerWrapper.fetch_SuiteTestsRunnable(suiteXlsReader);
	        if(suiteXlsReader.getRowCount(Constants_FRMWRK.TC_SHEET_NAME)>1){
	        	runnableTests=TestUtil.fetch_SuiteTestsRunnable(suiteXlsReader,suiteMode);
	        	isTestXMLCreated=createTestSuiteXML_TextExport(packagePattern,runnableTests,runnableSuites.get(key),key,testxmlfilePath);
	        	if(isTestXMLCreated.equalsIgnoreCase(Constants_FRMWRK.False)){
	        		isAllTestsParsed=false;
	        	}
	        }
	    }
		
		
			FileUtil.closeTextFile();
		return runnableSuites;
		
	}
	/**
	 * Generates xml for each suite with its test classes marked as Y in the relevant suite file and export the suite names into text file
	 * @author sahamed
	 * @Date Jun 12 2014
	 * @param packagePattern
	 * @param runnableTests
	 * @param suite
	 * @param suiteName
	 * @return
	 */
	private static String createTestSuiteXML_TextExport(String packagePattern,LinkedHashMap <String,String> runnableTests,String suite,String suiteName,String testxmlfilePath){
		String testName="";		
				
		try{
			
			 XmlSuite suitexml = new XmlSuite();
			 suitexml.setName(suiteName);
			 for ( String key : runnableTests.keySet()) {
					 testName=key;
						 try{
							 XmlTest test = new XmlTest(suitexml);
							 List<XmlClass> classes = new ArrayList<XmlClass>();				
							 test.setName(testName);	
							 classes.add(new XmlClass(packagePattern+suite+"."+testName));
							 test.setXmlClasses(classes) ;							
						 }catch(Throwable t){
							 isTestXMl=Constants_FRMWRK.False;
							// log("Unable to create test xml-"+"com.nuview.suite"+entry_suite.getValue()+"."+value+" due to error-"+t);
							//log("Unable to create test xml-com.nuview.suite"+value+" due to error-"+t);
							 System.out.println("Unable to create test xml-"+packagePattern+suite+"."+testName+" due to error-"+t);
						 }
							
						}
				
					String XMLfileName=testxmlfilePath+"\\"+suiteName+".xml";
					//System.out.println("TestXML File path-"+XMLfileName);
					File file = new File(XMLfileName);
			       // System.out.println("file--"+file);
			
			        FileWriter writer = new FileWriter(file);
			        writer.write(suitexml.toXml());
			        writer.close(); 
			        FileUtil.writeTextFile(suiteName);				 
		}catch (Throwable t){
			//log("Unable to create suite xml "+entry_suite.getKey().toString()+" due to error-"+t);
			System.out.println("Unable to create suite xml "+suiteName+" due to error-"+t);
		}
		
		return isTestXMl;
	}
	/**
	 * Generates xml for each suite with its test classes marked as Y in the relevant suite file
	 * @param packagePattern
	 * @param runnableTests
	 * @param suite
	 * @param suiteName
	 * @return
	 */
	private static String createTestSuiteXML(String packagePattern,LinkedHashMap <String,String> runnableTests,String suite,String suiteName){
		String testName="";		
				
		try{
			
			 XmlSuite suitexml = new XmlSuite();
			 suitexml.setName(suiteName);
				 for ( String key : runnableTests.keySet()) {
					 testName=key;
						 try{
							 XmlTest test = new XmlTest(suitexml);
							 List<XmlClass> classes = new ArrayList<XmlClass>();				
							 test.setName(testName);	
							 classes.add(new XmlClass(packagePattern+suite+"."+testName));
							 test.setXmlClasses(classes) ;
						 }catch(Throwable t){
							 isTestXMl=Constants_FRMWRK.False;
							// log("Unable to create test xml-"+"com.nuview.suite"+entry_suite.getValue()+"."+value+" due to error-"+t);
							//log("Unable to create test xml-com.nuview.suite"+value+" due to error-"+t);
							 System.out.println("Unable to create test xml-"+packagePattern+suite+"."+testName+" due to error-"+t);
						 }
							
						}
				
				
					File file = new File(System.getProperty("user.dir")+"\\conf\\suites\\"+suiteName+".xml");
			      //  System.out.println("file--"+file);
			
			        FileWriter writer = new FileWriter(file);
			        writer.write(suitexml.toXml());
			        writer.close(); 
			      
			 
			 
		}catch (Throwable t){
			//log("Unable to create suite xml "+entry_suite.getKey().toString()+" due to error-"+t);
			System.out.println("Unable to create suite xml "+suiteName+" due to error-"+t);
		}
		
		return isTestXMl;
	}
	

}
