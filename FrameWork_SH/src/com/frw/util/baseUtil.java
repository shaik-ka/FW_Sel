package com.frw.util;

import java.io.FileInputStream;
import java.util.Properties;

import com.frw.base.Base;

public class baseUtil extends Base{
	
	
	
	public static Properties loadPropertiesFile(String filePath){
		Properties propFile=	new Properties();
		FileInputStream ip;
		try {
			
			
			//ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\nuview\\config\\"+Constants.CONFIG_PROP_FILE);
			ip = new FileInputStream(filePath);
			propFile.load(ip);
			//APP_LOGS.debug("Loaded Properties file "+filePath);
			logsObj.log("Loaded Properties file "+filePath);
			
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			//APP_LOGS.debug("Unable to Loaded the Properties file "+filePath+" due to error-"+t);
			logsObj.logError("Unable to Loaded the Properties file "+filePath+" due to error-",t);
		} 
		
		return propFile;
		
	}
	
	
	

}
