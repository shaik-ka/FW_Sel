package com.frw.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.base.Base;
import com.frw.wait.ImplicitWaitUtil;



public class CopyOfFetchWebElement extends Base{
/*
	*//**
	 * Waits for the element to be visible for the required seconds
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 *//*
	public static  WebElement waitForElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;	

		field=waitForElement_old(driver,objectlocatorType,objectlocator, waitSeconds );

		//waitForStaleOf(field);
		 commented on Jun 2 2014
	    log("Next verify stale..");
	    stal(field);

		return field;
	} 


	*//**
	 * Waits for the element to be present for the required seconds
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static  WebElement waitForPresenceOfElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		//Long wait = Long.valueOf(i.longValue());

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			Class byClass=Class.forName(By.class.getName());

			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);

			try{

				field = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.presenceOfElementLocated(newById));
				logsObj.log("Able to locate presence of element.."+objectlocator);
			}catch(Throwable t){
				logsObj.logError("After binding By element , Unable to wait for the presence of  "+objectlocator+" due to error->",t);
			}


		}catch(Throwable t){
			logsObj.logError("Unable to bind the By element for "+objectlocator+" due to error->",t);

		}		

		return field;
	} 


	*//**
	 * Waits for an element to be invisible on the page/frame(Customized method)
	 * @author khshaik
	 * @date Nov 20 2014
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return False if it is visible ,True for invisible
	 *//*
	public static  boolean waitForInvisibilityOfElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ){
		boolean flag=Constants_FRMWRK.FalseB;
		int counter=1;
		WebElement element=null;
		logsObj.log("Start..");
		element=waitForElement(driver, objectlocatorType, objectlocator, 1);		
		logsObj.log("End..");
		while (!(element ==null) && counter<=waitSeconds){
			logsObj.log("waitForInvisibilityOfElement:-Iteration-"+counter+" objectlocator-"+objectlocator+" is still visible");
			element=waitForElement(driver, objectlocatorType, objectlocator, 1);
			if(element==null){
				break;
			}
			
			counter++;
		}
		if(element==null){
			logsObj.log("waitForInvisibilityOfElement:-Iteration-"+counter+" objectlocator-"+objectlocator+" is invisible");
			flag=Constants_FRMWRK.TrueB;
		}else{
			logsObj.log("waitForInvisibilityOfElement:-Iteration-"+counter+" objectlocator-"+objectlocator+" is still invisible after complete wait");
		}

		return flag;
	}

	*//**
	 * Waits for the element to be visible for the required seconds
	 * @author sahamed
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return element 
	 * @Date 16 Aug 2013
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static  WebElement waitForElement_old(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

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
					field=waitForElementStale(driver,objectlocatorType, objectlocator, 2);
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


	*//**
	 * Check for Stale Element
	 * @author sahamed
	 * @Date May 2 2014
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return True for stale element otherwise false
	 *//*

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  WebElement waitForElementStale(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;		   
		//Long wait = Long.valueOf(i.longValue());

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);

			try{
				//field = new WebDriverWait(driver, (long)(waitSeconds)).until(ExpectedConditions.visibilityOfElementLocated(newById));
				field = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.visibilityOfElementLocated(newById));
				logsObj.log("waitForElementStale:-Able to locate and stale element is visible.."+objectlocator);
			}catch(StaleElementReferenceException t){
				field = null;
				logsObj.logError("waitForElementStale:-After binding By element , Unable to wait for the element visibilty of  "+objectlocator+" due to error->",t);
			}


		}catch(Throwable t){
			logsObj.logError("waitForElementStale:-Unable to bind the By element for "+objectlocator+" due to error->",t);

		}		
		

		return field;
	} 


	*//**
	 * Waits for the dropdown items to be loaded
	 * @author khshaik
	 * @date Jan 10 2014
	 * @param driver
	 * @param element
	 * @param waitForSeconds
	 * @return
	 *//*
	public static Select waitForDropdownItems(WebDriver driver,WebElement element, int waitForSeconds){
		List<WebElement>dropdownList=null;
		Select dropdown =null;
		int i=1;
		for (i=1 ;i<=waitForSeconds;i++){
			dropdown = new Select(element);
			dropdownList=dropdown.getOptions();
			commented on Jan 20 2015 as it is giving problem when page ajax is not loaded in 20 secs
			PageLoadWaitUtil.waitForAjax(driver);

			if(dropdownList.size()!=0){
				logsObj.log("waitForDropdownItems:-Able to load items of the dropdown..");
				break;
			}
			WaitUtil.pause(1);
		}

		if(i==waitForSeconds){
			logsObj.log("waitForDropdownItems:-Not Able to load items of the dropdown..");
		}
		return dropdown;
	}


	*//**
	 * Fetches the list of WebElements
	 * @author khshaik
	 * @date Jan 14 2014
	 * @param driver
	 * @param identifyBy
	 * @param locator
	 * @return
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<WebElement> FetchWebElements(WebDriver driver,String identifyBy, String locator)
	{
		By by; 
		by = null;
		List<WebElement> listElement = null;

		try 
		{


			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(identifyBy, String.class);
			By newById=(By) byMethod.invoke(by, locator);


			Class webDriverClass=Class.forName(driver.getClass().getName());


			Method getMethod = webDriverClass.getMethod("findElements", new Class[]{By.class});
			listElement= (List<WebElement>) getMethod.invoke(driver, newById);  			

			logsObj.log("FindElements:The number of located element with xpath-"+locator+" are"+listElement.size());

		}


		catch (ElementNotVisibleException env) 
		{
			logsObj.log("FindElements:Element "+locator+" is not visible..due to -->"+env);			
			throw env;
		}

		catch (Exception genException)
		{
			System.out.println("FindElements:Unable to locate Element "+locator+" due to general exception"+genException); 
			logsObj.log("FindElelements: General Exception error .."+"while fecth element "+locator+" due to -->  "+genException);

		}


		return listElement;
	}


	*//**
	 * Returns the list of visible webelements of the given locator
	 * @author khshaik
	 * @date Jan 14 2014
	 * @param driver
	 * @param identifyBy
	 * @param locator
	 * @return
	 *//*
	public static List<WebElement> visibleElementsList(WebDriver driver, String identifyBy,String locator){

		List<WebElement>searchElements=FetchWebElements(driver, identifyBy, locator);
		List<WebElement> visible_elements=new ArrayList<WebElement>();


		if(searchElements.size()!=0){

			for(WebElement element: searchElements){
				if(element.isDisplayed()==true){
					visible_elements.add(element);					
				}
			}
		}

		return visible_elements;
	}

	*//**
	 * Fetches a an action performable element for sucess
	 * @author khshaik
	 * @date Feb 12 2015
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 *//*
	public static  WebElement waitForElementTobeActionable(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;	
		logsObj.log("Into the waitForElementTobeActionable");
		field=waitForElement_Clickable(driver,objectlocatorType,objectlocator, waitSeconds );

		//waitForStaleOf(field);
		 commented on Jun 2 2014
	    log("Next verify stale..");
	    stal(field);

		return field;
	} 

	*//**
	 * Waits for element to be clickable
	 * @author khshaik
	 * @Date Feb 12 2015
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 *//*

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static  WebElement waitForElement_Clickable(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		int counter=0;

		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));

		try{
			logsObj.log("Into the waitForElement_Clickable");
			
			logsObj.log("waitForElement_Clickable-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			
			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);
			logsObj.log("Into the waitForElement_Clickable-And By Binded-Locator Type-"+objectlocatorType+" and locator-"+objectlocator);
			try{
				logsObj.log("Inside try...waitForElement_Clickable--");
				field = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.elementToBeClickable(newById));
				logsObj.log("waitForElement_Clickable:-Able to locate and element is visible.."+objectlocator);

			}

			catch(StaleElementReferenceException st){
				logsObj.logError("waitForElement_Clickable:- Wait for element is stale..need to recover the object-"+objectlocator,st);
				if (counter<=10 ){
					logsObj.log("waitForElement_Clickable:-Not able to locate and stale element "+objectlocator +" counter is "+counter);
					field=waitForElementStale(driver,objectlocatorType, objectlocator, 2);
					if (field!=null){
						logsObj.log("waitForElement_Clickable:-Able to recover the stale element "+objectlocator +" counter is "+counter);
						return field;
					}

					counter=counter+1;
				}

				if(counter>10){
					logsObj.log("waitForElement_Clickable:- Wait for element is still stale..after try to recover for the object-"+objectlocator);
				}
			}
			catch(Throwable t){
				logsObj.logError("waitForElement_Clickable:-After binding By element , Unable to wait for the element visibilty of  "+objectlocator+" due to error->",t);
			}



		}catch(Throwable t){
			logsObj.logError("waitForElement_Clickable:-Unable to bind the By element for "+objectlocator+" due to error->",t);			
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_Clickable-Reverted back Implicit wait..");
		}

		return field;
	} */

}
