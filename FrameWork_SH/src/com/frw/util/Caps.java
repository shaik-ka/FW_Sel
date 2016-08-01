package com.frw.util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Caps  {
	
	
	public static DesiredCapabilities cap_Andriod_RealDevice(){
		DesiredCapabilities cap = new DesiredCapabilities();
    	cap.setPlatform(Platform.ANDROID);
    	cap.setCapability("device", "Android");
    	cap.setCapability("app", "chrome");    	
    	cap.setCapability("newCommandTimeout", 240);
    	System.out.println("Chrome browser");
    	
    	return cap;
	}
	
	
	public static DesiredCapabilities cap_Andriod_Emulator(){
		
		DesiredCapabilities cap = new DesiredCapabilities();
		
    	cap.setPlatform(Platform.ANDROID);
    	cap.setCapability("platformVersion", "4.4");
    	cap.setCapability("deviceName", "Android Emulator");
    	System.out.println("device");	    	
    	cap.setCapability("browserName", "Browser");
    	cap.setCapability("newCommandTimeout", 240);
    	System.out.println("Andriod browser");
    	
    	return cap;
	}
	
	

}
