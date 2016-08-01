package com.frw.util;



import java.util.Hashtable;
import java.util.LinkedHashMap;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;

public class TestUtil extends Base{
	
	private static String SuiteMode;
	
	static Hashtable <String,Hashtable<String,String>> ht=new Hashtable<String,Hashtable<String,String>>();
	static Hashtable <String,String> _objects_step=new Hashtable<String,String>();
	static Hashtable <String,String> _objects_locatorType=new Hashtable<String,String>();
	static Hashtable <String,String> _objects_objectType=new Hashtable<String,String>();
	static Hashtable <String,String> _objects_locator=new Hashtable<String,String>();
	

/* ***************************Find if the suite is Executable or not....*****************************************************************/
	 /**
	  * Check the suite is marked for execution
	  * @author khshaik
	  * @param xls
	  * @param suiteName
	  * @return
	  */
		public static boolean isSuiteRunnable(Xls_Reader xls,String suiteName,String suiteMode){
			isSuiteSkip=false;
			 boolean isExecutable=false;
			 String runmode=Constants_FRMWRK.RUNMODE_YES_FLAG;
			for (int i=2;i<=xls.getRowCount(Constants_FRMWRK.SUITE_SHEET_NAME);i++){
				String suite=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_TSID_COLUMN, i);
				
				initializeSuiteMode(suiteMode);
				logsObj.log("Suite Mode is "+SuiteMode);
				/*if(!CONFIG.getProperty("SuiteMode").equalsIgnoreCase("R")){*/
					runmode=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, SuiteMode+" "+Constants_FRMWRK.SUITE_SHEET_RUNMODE_COLUMN, i);
			//	}
				
				
			//	if (suite.equalsIgnoreCase(suiteName)){
					if (runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
						isExecutable= true;						
					}else{
						isSuiteSkip=true;
						isExecutable=false;
					}
			//	}
				
			}
			xls=null;
			return isExecutable;
		}
		
		/**
		 * Fetches the Test suites which are marked as Y
		 * @author sahamed
		 * @Date Jun 12 2014
		 * @param xls
		 * @return Lists of executable suites - Suite Name[TSID] with its Package Name
		 */
		
		public static LinkedHashMap<String,String> fetch_SuitesRunnable(Xls_Reader xls,String suiteMode){
			String runmode=Constants_FRMWRK.RUNMODE_YES_FLAG;
			LinkedHashMap<String,String> executableSuites=new LinkedHashMap<String,String>();
			initializeSuiteMode(suiteMode);
			for (int i=2;i<=xls.getRowCount(Constants_FRMWRK.SUITE_SHEET_NAME);i++){
				String suite=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_TSID_COLUMN, i);
			//	if(!SuiteMode.equalsIgnoreCase("Regression")){
					runmode=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME,SuiteMode+" "+ Constants_FRMWRK.SUITE_SHEET_RUNMODE_COLUMN, i);
			//	}
				
					if (runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
							executableSuites.put(suite, xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_PKG_COLUMN, i));				
					}
				}			
			
			return executableSuites;
		}
		
		/**
		 * Checks the test is marked for execution
		 * @author khshaik
		 * @param xls
		 * @param tc
		 * @return
		 */
		public static boolean isTestCaseRunnable(Xls_Reader xls,String tc){
			boolean isExecutable=false;
			String runmode=Constants_FRMWRK.RUNMODE_YES_FLAG;
			
			for(int i=2;i<=xls.getRowCount(Constants_FRMWRK.TC_SHEET_NAME);i++){
				
				String tcid=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, Constants_FRMWRK.TC_SHEET_TCID_COLUMN, i);
				if(!SuiteMode.equalsIgnoreCase("Regression")){
					runmode=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, SuiteMode+" "+Constants_FRMWRK.TC_SHEET_RUNMODE_COLUMN, i);
				}
				//System.out.println(tcid+"--"+runmode);
				
				if (tcid.equalsIgnoreCase(tc)){
					if(runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
						isExecutable=true;
					}
				}
			}
			
		
			return isExecutable;
		}	
	
		
		
		
		/**
		 * Fetches the Test suites which are marked as Y
		 * @author khshaik
		 * @param xls
		 * @return List of executable tests - Test Name[TCID] with its Run Mode
		 */
		public static LinkedHashMap<String,String> fetch_SuiteTestsRunnable(Xls_Reader xls,String suiteMode){
			String runmode=Constants_FRMWRK.RUNMODE_YES_FLAG;
			LinkedHashMap<String,String> executableTests=new LinkedHashMap<String,String>();
			initializeSuiteMode(suiteMode);
			for(int i=2;i<=xls.getRowCount(Constants_FRMWRK.TC_SHEET_NAME);i++){
				
				String tcid=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, Constants_FRMWRK.TC_SHEET_TCID_COLUMN, i);
				if(!SuiteMode.equalsIgnoreCase("Regression")){
					runmode=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, SuiteMode+" "+Constants_FRMWRK.TC_SHEET_RUNMODE_COLUMN, i);
				}
					if(runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
						executableTests.put(tcid, runmode);
					}
				
			}		
			System.out.println("Total tests"+executableTests);
			return executableTests;
		}	
		
		/**
		 * Initializes the suitemode flag
		 * @author khshaik
		 * @param suiteMode
		 */
		
		private static void initializeSuiteMode(String suiteMode){
			if(suiteMode.equalsIgnoreCase("P")){
				SuiteMode="Partial";
			}else if(suiteMode.equalsIgnoreCase("S")){
				SuiteMode="Sanity";
			}else{
				SuiteMode="Regression";
			}
		}
		
		/**
		 * Fetches the steps,locatorTypes,objectTypes and locators of the given excel sheet
		 * @author khshaik
		 * @date Jan 28 2015
		 * @param reader
		 * @param sheetName
		 * @return
		 */
		public static void getObjectsfromSheet(Xls_Reader reader,String sheetName){
			
			
			_objects_step=ExcelUtil.getParameters(reader,sheetName,"Step Name","Step Name");
			_objects_locatorType=ExcelUtil.getParameters(reader,sheetName,"Step Name","LocatorType");
			_objects_objectType=ExcelUtil.getParameters(reader,sheetName,"Step Name","ObjectType");
			_objects_locator=ExcelUtil.getParameters(reader,sheetName,"Step Name","ObjectLocator");
			
			/*ht.put("steps", _objects_step);
			ht.put("locatorTypes", _objects_locatorType);
			ht.put("objectTypes", _objects_objectType);
			ht.put("locators", _objects_locator);
			return ht;*/
		}
		
		/**
		 * Fetches the steps,locatorTypes,objectTypes and locators of the given excel sheet and return the stored hash
		 * @author khshaik
		 * @date Jan 28 2015
		 * @param reader
		 * @param sheetName
		 * @return
		 */
		public static Hashtable<String,Hashtable<String,String>> getObjectsfromSheetAndReturn(Xls_Reader reader,String sheetName){
			
			
			_objects_step=ExcelUtil.getParameters(reader,sheetName,"Step Name","Step Name");
			_objects_locatorType=ExcelUtil.getParameters(reader,sheetName,"Step Name","LocatorType");
			_objects_objectType=ExcelUtil.getParameters(reader,sheetName,"Step Name","ObjectType");
			_objects_locator=ExcelUtil.getParameters(reader,sheetName,"Step Name","ObjectLocator");
			
			ht.put("steps", _objects_step);
			ht.put("locatorTypes", _objects_locatorType);
			ht.put("objectTypes", _objects_objectType);
			ht.put("locators", _objects_locator);
			return ht;
		}
		
		/**
		 * Fetch the specific objects into Hashtable
		 * @author khshaik
		 * @date Jan 28 2015
		 * @param reader
		 * @param sheetName
		 * @param objectName
		 * @return
		 */
		public static Hashtable<String,String> getObjectsFromExcelSheet(Xls_Reader reader,String sheetName,String objectName){
			Hashtable<String,String> fetchingHash=new Hashtable<String,String>();
			Hashtable<String,Hashtable<String,String>> allHashes=getObjectsfromSheetAndReturn(reader, sheetName);
			fetchingHash=allHashes.get(objectName);
			return fetchingHash;
			
		}
		
		/**
		 * fetches all objects(steps,locatorTypes,objectTypes,locatorTypes,locators) from the sheet and initializes the required hashtables
		 * @author khshaik
		 * @date Feb 01 2015
		 * @param theClass
		 * @param reader
		 * @param sheetName
		 * @param initializeVariable
		 * @param objects_steps
		 * @param objects_LocatorTypes
		 * @param objects_objectTypes
		 * @param objects_locators
		 * @return 
		 * True for Success , and for failure details of not fetched.
		 */
		public static String getAllObjectsFromExcelSheet(Class<?> theClass,Xls_Reader reader,String sheetName,String initializeVariable){
			String flag=Constants_FRMWRK.False;
			/*String initializeVariablePattern_step="objects_step_";
			String initializeVariablePattern_locatorType="objects_locatorType_";
			String initializeVariablePattern_objectType="objects_objectType_";
			String initializeVariablePattern_objectlocator="objects_objectLocator_";*/
			
			String initializeVariablePattern_step=Constants_FRMWRK.objectsContainer_step;
			String initializeVariablePattern_locatorType=Constants_FRMWRK.objectsContaner_locatorType;
			String initializeVariablePattern_objectType=Constants_FRMWRK.objectsContainer_objectType;
			String initializeVariablePattern_objectlocator=Constants_FRMWRK.objectsContainer_locators;
			
			String result="";
			
			initializeVariablePattern_step=initializeVariablePattern_step+initializeVariable;
			WaitUtil.pause(1);
			getObjectsfromSheet(reader, sheetName);
			WaitUtil.pause(1);
			//String result1=InitializePrivateVariableUtil.initPrivateVariable(theClass, initializeVariablePattern_step, _objects_step);
			String result1=RefUtil.initPrivateVariableofClass(theClass, initializeVariablePattern_step, _objects_step);
			if(result1.equalsIgnoreCase(Constants_FRMWRK.False)){
				result=result+initializeVariablePattern_step;
			}
			WaitUtil.pause(100L);
			
			initializeVariablePattern_locatorType=initializeVariablePattern_locatorType+initializeVariable;
			//String result2=InitializePrivateVariableUtil.initPrivateVariable(theClass, initializeVariablePattern_locatorType, _objects_locatorType);
			String result2=RefUtil.initPrivateVariableofClass(theClass, initializeVariablePattern_locatorType, _objects_locatorType);
			if(result2.equalsIgnoreCase(Constants_FRMWRK.False)){
				result=result+initializeVariablePattern_locatorType;
			}
			WaitUtil.pause(100L);
			
			initializeVariablePattern_objectType=initializeVariablePattern_objectType+initializeVariable;
			//String result3=InitializePrivateVariableUtil.initPrivateVariable(theClass, initializeVariablePattern_objectType, _objects_objectType);
			String result3=RefUtil.initPrivateVariableofClass(theClass, initializeVariablePattern_objectType, _objects_objectType);
			if(result3.equalsIgnoreCase(Constants_FRMWRK.False)){
				result=result+initializeVariablePattern_objectType;
			}
			WaitUtil.pause(100L);
			
			initializeVariablePattern_objectlocator=initializeVariablePattern_objectlocator+initializeVariable;
			//String result4=InitializePrivateVariableUtil.initPrivateVariable(theClass, initializeVariablePattern_objectlocator, _objects_locator);
			String result4=RefUtil.initPrivateVariableofClass(theClass, initializeVariablePattern_objectlocator, _objects_locator);
			if(result4.equalsIgnoreCase(Constants_FRMWRK.False)){
				result=result+initializeVariablePattern_objectlocator;
			}
			WaitUtil.pause(100L);
			
			if(!result.equalsIgnoreCase("")){
				System.out.println("Objects not initalized from the sheet "+sheetName+" and they are "+result);
				flag="Error- All objects are not fetched from the sheet:-"+sheetName+" the objects not fetched are "+result;
			}else{
				flag=Constants_FRMWRK.True;
			}
			clearHashdata();
			return flag;
		}
		
		private static void clearHashdata(){
			_objects_step=null;
			_objects_locatorType=null;
			_objects_objectType=null;
			_objects_locator=null;
		}

}
