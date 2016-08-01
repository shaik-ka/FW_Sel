package com.frw.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;
import com.google.common.base.Throwables;




public class WorkaroundsSelenium extends Base{
	/**
	 * Send Control to the element under test
	 * @param parameter
	 * @param expectedParameter
	 * @param element
	 */
	//workaround for Viewport error [but unable to click due to error->org.openqa.selenium.ElementNotVisibleException: The point at which the driver is attempting to click on the element was not scrolled into the viewport.]
	public static void sendControlToElement(String parameter,String expectedParameter,WebElement element){
		try{
			if(parameter.equalsIgnoreCase(expectedParameter)){
				 logsObj.log("sendControlToElement:As this is "+parameter+" need to get the control");
			element.sendKeys(Keys.CONTROL);
				logsObj.log("sendControlToElement:As this is IE got the control");
			
			}
		}catch(Throwable t){
			 logsObj.logError("sendControlToElement:Unable to get the control for the element due to error-",t);
		}
		
		
	}
	
	/**
	 * get the element into viewable location in the screen 
	 * @author sahamed
	 * @Date Jan 2014
	 * @param element
	 * @return True for success and False for failure
	 */
	public static String getElementintoVisible(WebElement element){
		String flag=Constants_FRMWRK.False;
		int count =0;
		Point finalLocation = null;
		Point initialLocation = ((Locatable) element).getCoordinates().inViewPort();
		
		 while (count < 11) {
			 try {
					Thread.sleep(50);
					count=count+1;
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
	            finalLocation = ((Locatable) element).getCoordinates().inViewPort();
	            if(finalLocation.equals(initialLocation)){
	            	flag=Constants_FRMWRK.True;
	            	 logsObj.log("Successfuly made the element into viewable on the screen");
	            	return flag;
	            }
			 
		 }
		if(flag.equalsIgnoreCase(Constants_FRMWRK.False)){
			 logsObj.log("The current element is not viewable,after possible wait..");
		}
		 return flag;
		 
	}

	
	public static void killIEProcess(){
		try {
		    Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		    Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
		    logsObj.log("IE listed processes have been killed..");
		} catch (Exception e) {
		    e.printStackTrace();		    
		    logsObj.log("Unable to kill IE listed processes have been killed.. due to "+Throwables.getStackTraceAsString(e));
		}
	}
	
	
	public static void expectModalWindow(WebDriver driver){
		((JavascriptExecutor)driver).executeScript("window.showModalDialog=window.open");
	}
}
