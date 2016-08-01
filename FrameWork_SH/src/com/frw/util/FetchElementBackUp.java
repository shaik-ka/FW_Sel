package com.frw.util;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.base.Base;
import com.frw.wait.ImplicitWaitUtil;

public class FetchElementBackUp extends Base{
	
	public static  WebElement waitForElement_old_Mar182015(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		int counter=0;
		//Long wait = Long.valueOf(i.longValue());

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			/*commented on Mar 17 2015
			logsObj.log("waitForElement_old-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);*/

			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);


			logsObj.log("waitForElement_old-Able to bind the By element for "+objectlocator);
			//field = new WebDriverWait(driver, (long)(waitSeconds)).until(ExpectedConditions.visibilityOfElementLocated(newById));
			field = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.visibilityOfElementLocated(newById));
			logsObj.log("waitForElement_old:-Able to locate and element is visible.."+objectlocator);


		}catch(Throwable t){
			logsObj.logError("waitForElement_old-Unable to bind the By element or wait for the element visibilty for "+objectlocator+" due to error->",t);			
		}		
		
		/*commented on Mar 17 2015
		finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_old-Reverted back Implicit wait..");
		}*/
		return field;
	} 
	
	public static  WebElement waitForElement_old_mar172015(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		int counter=0;
		//Long wait = Long.valueOf(i.longValue());

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			
			logsObj.log("waitForElement_old-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			
			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);

			try{
				//field = new WebDriverWait(driver, (long)(waitSeconds)).until(ExpectedConditions.visibilityOfElementLocated(newById));
				field = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.visibilityOfElementLocated(newById));
				logsObj.log("waitForElement_old:-Able to locate and element is visible.."+objectlocator);

			}

			catch(StaleElementReferenceException st){
				logsObj.logError(" Wait for element is stale..need to recover the object-"+objectlocator,st);
				if (counter<=10 ){
					logsObj.log("Not able to locate and stale element "+objectlocator +" counter is "+counter);
/*commented on Mar 18 2015
Need to uncomment the below line before use
field=waitForElementStale(driver,objectlocatorType, objectlocator, 2);*/
					if (field!=null){
						logsObj.log("Able to recover the stale element "+objectlocator +" counter is "+counter);
						return field;
					}

					counter=counter+1;
				}

				if(counter>10){
					logsObj.log(" Wait for element is still stale..after try to recover for the object-"+objectlocator);
				}
			}
			catch(Throwable t){
				logsObj.logError("waitForElement_old-After binding By element , Unable to wait for the element visibilty of  "+objectlocator+" due to error->",t);
			}



		}catch(Throwable t){
			logsObj.logError("waitForElement_old-Unable to bind the By element for "+objectlocator+" due to error->",t);			
		}		
		finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_old-Reverted back Implicit wait..");
		}
		return field;
	} 

}
