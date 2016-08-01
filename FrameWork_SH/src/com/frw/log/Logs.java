package com.frw.log;

import org.apache.log4j.Logger;

public class Logs {
	
	private static Logs logsObject;
	private static Logger logger;
	private static boolean loggerInitialized=false;
	
	private Logs(){}
	
	/**
	 * Fetches the Logs class object
	 * @author khshaik
	 * @date Oct 24 2014
	 * @return
	 */
	public static Logs getLogsObjAndInitialize(String loggerName){
		if(logsObject==null){
			logsObject=new Logs();
			initializeLogger(loggerName);
		}
		return logsObject;
	}
	
	
	/**
	 * Initialises the logger 
	 * @author khshaik
	 * @date Oct 24 2014 
	 * @return
	 */
	@SuppressWarnings("null")
	private static Logger initializeLogger(String loggerName){
		Logger APPLOGS = null;
		if(loggerInitialized==false){
			APPLOGS=Logger.getLogger(loggerName);			
			APPLOGS.debug("Loaded Logger..");			
			logger=APPLOGS;
		}else{
			APPLOGS.debug("Already Logger Loaded..");
		}
		
		return APPLOGS;
	}
	
	/**
	 * Logs the message into the log file in debug mode
	 * @author khshaik
	 * @date Oct 24 2014
	 * @param message
	 */
	public void log(String message ){
		logger.debug(message);
	}
	
	/**
	 * Logs the message into the log file in info mode
	 * @author khshaik
	 * @date Oct 24 2014
	 * @param message
	 */
	public void logInfo(String message){
		logger.info(message);
	}
	
	/**
	 * Logs the error message and its stacktrace of the exception
	 * @author khshaik
	 * @date Oct 24 2014
	 * @param message
	 * @param t
	 */
	public void logError(String message,Throwable t){
		logger.error(message, t);
	}
	
	public static void nullifyLogsObj(){
		if(logsObject!=null){
			logsObject=null;		
		}	
	}
	

}
