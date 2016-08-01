package com.frw.runner;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.testng.annotations.Test;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.util.FileUtil;
import com.frw.util.TestNGRunnerUtil;
import com.frw.util.XMLUtil;
import com.frw.util.baseUtil;


public class TestNGRunner {

	//**************** NOT USING THIS CLASS AS IT IS IMPLEMENTED IN THE PROJECT ********************************
	
	static FileUtil fileUtilObj;
	
	@Test
	public static void triggertestNGXMLrunner(){
		System.out.println("*********Trigger Test ***********");
		String packagePattern="com.nuview.suite";
		final String xmlFilePath =System.getProperty("user.dir")+"\\conf\\suites"+"\\testNG.xml";
		LinkedHashMap<String,String> runnableSuites=new LinkedHashMap<String,String>();

		try {
			fileUtilObj=FileUtil.getFileUtilObject();
			fileUtilObj.deleteFilesinFolder(System.getProperty("user.dir")+"\\conf\\suites");
			Properties Config=baseUtil.loadPropertiesFile(System.getProperty("user.dir")+"\\src\\com\\proj\\config\\config.properties");
			//runnableSuites=TestNGRunnerUtil.testNGXMLrunner(System.getProperty("user.dir")+"\\src\\com\\nuview\\suite","xlsx", Constants_FRMWRK.SUITE_FILE_NAME, System.getProperty("user.dir")+"\\conf\\suites\\suites_lists.txt", packagePattern,System.getProperty("user.dir")+"\\conf\\suites");
			runnableSuites=TestNGRunnerUtil.testNGXMLrunner(System.getProperty("user.dir")+"\\src\\com\\proj\\suite","xlsx", Constants_FRMWRK.SUITE_FILE_NAME, System.getProperty("user.dir")+"\\conf\\suites\\suites_lists.txt", packagePattern,System.getProperty("user.dir")+"\\conf\\suites",Config.getProperty("SuiteMode"));
			System.out.println("Package pattern is"+packagePattern);

			if(TestNGRunnerUtil.isAllTestsParsed==false){
				System.out.println("All Classes are not generated..please refer the logs");
			}else{
				System.out.println("All Classes under suites are generated..");
			}
			
			//	runnableSuites=TestNGRunnerUtil.TestNGXMLrunner_2(System.getProperty("user.dir")+"\\src\\com\\nuview\\suite","xlsx", Constants.SUITE_FILE_NAME, packagePattern);
			XMLUtil.createTestNGXML(xmlFilePath,"conf/suites",runnableSuites,"Selenium Automation Suite-MAT-1.0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
