package com.frw.base;

import java.util.Properties;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.log.Logs;
import com.frw.util.baseUtil;

public class Base {
	
	//public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static boolean isbaseInitiazed=false;
	public static String testcaseName;
	public static boolean isTestPass=true;
	public static boolean isTestSkip=false;
	public static boolean isSuiteSkip=false;
	public static String isTestRRU=Constants_FRMWRK.False;

	public static Logs logsObj;
	
	//public static Hashtable<String,String>dataFetchParamMasterSheetName=null;
	
	
	
	public static void baseInitialize(String loggerName,String configFile,String orFile) {
		if (!isbaseInitiazed)
		{
			//loggger initializer			
			/*APP_LOGS=Logger.getLogger("devpinoyLogger");			
			APP_LOGS.debug("Loaded Logger..");*/
			logsObj=Logs.getLogsObjAndInitialize(loggerName);
			//initializeLogger();
			
			//config initializer
			logsObj.log("Loading Config properties file");
			CONFIG=baseUtil.loadPropertiesFile(configFile);
			
			//OR.properties initializer
			logsObj.log("Loading OR properties file");
			OR=baseUtil.loadPropertiesFile(orFile);
			
			isbaseInitiazed=true;
			 
		}
	}
	
	
	/*public static Logger initializeLogger(){
		APP_LOGS=Logger.getLogger("devpinoyLogger");			
		APP_LOGS.debug("Loaded Logger..");
		
		return APP_LOGS;
	}
	
	public static void log(String message){
		APP_LOGS.debug(message);
	}
	public static void logInfo(String message){
		APP_LOGS.info(message);
	}
	*//**
	 * Logs the error message and its stacktrace of the exception
	 * @author khshaik
	 * @date Oct 24 2014
	 * @param message
	 * @param t
	 *//*
	public void logError(String message,Throwable t){
		APP_LOGS.error(message, t);
	}*/
	

}
