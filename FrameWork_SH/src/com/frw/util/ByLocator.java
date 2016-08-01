package com.frw.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.base.Base;

public class ByLocator extends Base{
	
@SuppressWarnings({ "rawtypes", "unchecked" })
public static  By fetch_ByLocator(WebDriver driver,String objectlocatorType,String objectlocator ) {

		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		By newByLocator = null;
		

		try{
			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			newByLocator=(By) byMethod.invoke(by, objectlocator);
			logsObj.log("fetch_ByLocator:-Sucessfully fetched Bylocator for "+objectlocator);
		}catch(Throwable t){
			logsObj.logError("fetch_ByLocator:-Unable to bind the By element for "+objectlocator+" due to error->",t);			
		}	
		
		
		return newByLocator;
	}

//https://github.com/djangofan/WebDriverTestingTemplate/blob/master/commonlib/src/main/java/qa/webdriver/util/WebDriverUtils.java
	/**
	 * Retrieves a webelement for the given locator
	 * @author Khaleel
	 * @date 2015
	 * @param expectedCondition
	 * @param locator
	 * @param driver
	 * @param waitSeconds
	 * @return
	 */
	public static WebElement getExpectedConditionsLocator( String expectedCondition,final By locator ,WebDriver driver,int waitSeconds) {
		logsObj.log( "Get element by locator: " + locator.toString());	
		//Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		WebDriverWait syncWait = new WebDriverWait(driver, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(500, TimeUnit.MILLISECONDS);
		syncWait.ignoring( StaleElementReferenceException.class );
		syncWait.ignoring(NoSuchElementException.class);				
		WebElement we = null;
		boolean found=false;
		try {
			if(expectedCondition.equalsIgnoreCase("VISIBILITY")){
				we = syncWait.until( ExpectedConditions.visibilityOfElementLocated( locator ) );						
			}
			else if(expectedCondition.equalsIgnoreCase("PRESENCE")){
				we = syncWait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
			}
			else if(expectedCondition.equalsIgnoreCase("CLICKABLE")){
				we = syncWait.until( ExpectedConditions.elementToBeClickable( locator ) );
			}
			else{

			}

			found = true;					
		} catch ( StaleElementReferenceException e ) {						
			logsObj.log( "Stale element: \n" + e.getMessage() + "\n");
		}				
		if ( found ) {
			logsObj.log("Found element after waiting" );
		} else {
			logsObj.log( "Failed to find element after waiting");
		}
		return we;
	}
	


	/**
	 * Retrieves  webelements for the given locator
	 * @author Khaleel
	 * @date 2016
	 * @param expectedCondition
	 * @param locator
	 * @param driver
	 * @param waitSeconds
	 * @return
	 */
	public static List<WebElement> getExpectedConditionsLocators( String expectedCondition,final By locator ,WebDriver driver,int waitSeconds) {
		logsObj.log( "Get element by locator: " + locator.toString());	
		//Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		WebDriverWait syncWait = new WebDriverWait(driver, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(500, TimeUnit.MILLISECONDS);
		syncWait.ignoring( StaleElementReferenceException.class );
		syncWait.ignoring(NoSuchElementException.class);				
		List<WebElement> we = null;
		boolean found=false;
		try {
			if(expectedCondition.equalsIgnoreCase("VISIBILITY")){
				we = syncWait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));						
			}
			else if(expectedCondition.equalsIgnoreCase("PRESENCE")){
				we = syncWait.until( ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			}


			found = true;					
		} catch ( StaleElementReferenceException e ) {						
			logsObj.log( "Stale element: \n" + e.getMessage() + "\n");
		}				
		if ( found ) {
			logsObj.log("Found element after waiting" );
		} else {
			logsObj.log( "Failed to find element after waiting");
		}
		return we;
	}
	
	
	public static List<WebElement> getExpectedConditionsLocators_fluent( String expectedCondition,final By locator ,WebDriver driver,int waitSeconds) {
		logsObj.log( "Get element by locator: " + locator.toString());	
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		 FluentWait<WebDriver> syncWait = new FluentWait<WebDriver>(driver);
		syncWait.withTimeout(wait, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		syncWait.ignoring( StaleElementReferenceException.class );
		syncWait.ignoring(NoSuchElementException.class);				
	
		
		List<WebElement> we = null;
		boolean found=false;
		try {
			if(expectedCondition.equalsIgnoreCase("VISIBILITY")){
				we = syncWait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));						
			}
			else if(expectedCondition.equalsIgnoreCase("PRESENCE")){
				we = syncWait.until( ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			}

			found = true;					
		} catch ( StaleElementReferenceException e ) {						
			logsObj.log( "Stale element: \n" + e.getMessage() + "\n");
		}				
		if ( found ) {
			logsObj.log("Found element after waiting" );
		} else {
			logsObj.log( "Failed to find element after waiting");
		}
		return we;
	}
	
	public void waitUntilCountChanges(WebDriver driver,String objectlocatorType,String objectlocator,int waitSeconds) {
		final By bylocator=fetch_ByLocator(driver, objectlocatorType, objectlocator);
		Long waitsec =Long.valueOf(Integer.valueOf(waitSeconds));
		
		WebDriverWait wait = new WebDriverWait(driver, waitsec);
       
		wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
              //  int elementCount = driver.findElements(By.xpath("xxxx")).size();    
            	int elementCount = driver.findElements(bylocator).size();       
                if (elementCount > 1)
                    return true;
                else
                    return false;
            }
        });
    }
	
	
	private static void expt(WebDriver driver,String objectlocatorType,String objectlocator,int waitSeconds){
		Long waitsec =Long.valueOf(Integer.valueOf(waitSeconds));
		final By bylocator=fetch_ByLocator(driver, objectlocatorType, objectlocator);
	    ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
	      public Boolean apply(WebDriver driver) {
	        driver.findElement(bylocator);
	        return Boolean.valueOf(true);
	      }
	    };
	    
	    WebDriverWait w = new WebDriverWait(driver, waitsec);
	    w.until(e);
	}
	
	private static void expt_webelement(WebDriver driver,String objectlocatorType,String objectlocator,int waitSeconds){
		Long waitsec =Long.valueOf(Integer.valueOf(waitSeconds));
		final By bylocator=fetch_ByLocator(driver, objectlocatorType, objectlocator);
	    ExpectedCondition<WebElement> e = new ExpectedCondition<WebElement>() {
	      public WebElement apply(WebDriver driver) {
	        return driver.findElement(bylocator);
	       
	      }
	    };	    
	    WebDriverWait w = new WebDriverWait(driver, waitsec);
	    w.until(e);
	}
	
}
