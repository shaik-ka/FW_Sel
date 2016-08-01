package com.frw.wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.frw.base.Base;
import com.frw.util.WaitUtil;

public class ImplicitWaitUtil extends Base{
	
	private static int  maxtimeOut;
	/**
	 * Turns off the Implicit wait for the webdriver instance
	 * @author khshaik
	 * @date Feb 13 2015
	 * @param driver
	 */
	public static void turnOffImplicitWait(WebDriver driver){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		logsObj.log("Implicit Wait Turn Off..");		
	}
	
	public static void setImplicitWait(WebDriver driver,int timeInSeconds){
		maxtimeOut=timeInSeconds;
		driver.manage().timeouts().implicitlyWait(maxtimeOut, TimeUnit.SECONDS);
		logsObj.log("Implicit Wait set to  "+maxtimeOut);
	}
	public static void turnOnImplicitWait(WebDriver driver){
		driver.manage().timeouts().implicitlyWait(maxtimeOut, TimeUnit.SECONDS);
		logsObj.log("Implicit Wait Turn On and set to  "+maxtimeOut);
	}
	
	public static void updateImplicitWait(WebDriver driver,int timeInSeconds){
		Long wait =Long.valueOf(Integer.valueOf(timeInSeconds));
		driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
		logsObj.log("Implicit Wait Turn Off..");
	}
}
