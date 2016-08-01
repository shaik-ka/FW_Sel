package com.frw.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;
import com.frw.util.ByLocator;

public class CopyOfExplicitWaitUtil extends Base{
/*
	*//**
	 *  Waits for an element to be invisible on the page/frame(ExpectedCondition method)
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 *//*

	public static  boolean waitUntilInvisibilityOfElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		boolean  flag=Constants_FRMWRK.FalseB;
		By Bylocator=null;

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			logsObj.log("waitUntilInvisibilityOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);

			try{
				logsObj.log("waitUntilInvisibilityOfElement:-Start.."+objectlocator);
				flag = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.invisibilityOfElementLocated(Bylocator));
				logsObj.log("waitUntilInvisibilityOfElement:-Able to wait for element to be invisible.."+objectlocator);
			}catch(Throwable t){
				logsObj.logError("waitUntilInvisibilityOfElement:-After binding By element , Unable to wait more for the invisiblity of  "+objectlocator+" due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitUntilInvisibilityOfElement:-Unable to bind the By element for "+objectlocator+" due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitUntilInvisibilityOfElement-Reverted back Implicit wait..");
		}

		return flag;
	} 

*/
	
}
