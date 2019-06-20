package com.frw.wait;

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

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;
import com.frw.enums.ExpCondition;
import com.frw.util.ByLocator;
import com.frw.util.WaitUtil;

public class ExplicitWaitUtil extends Base{
	
	/**
	 * Waits for the element to be visible for the required seconds
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 * @throws Throwable 							2018
	 */ 
	public static  WebElement waitForElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) throws Throwable {

		WebElement field=null;	
		field=waitForElement_logic(ExpCondition.VISIBILITY,driver,objectlocatorType,objectlocator, waitSeconds );
		if(field!=null){			
			try{
				field.getLocation();
			}catch(StaleElementReferenceException st){
				waitStalenessOfElement(driver, field, waitSeconds+20);
			}
			
		}

		//waitForStaleOf(field);
		/* commented on Jun 2 2014
	    log("Next verify stale..");
	    stal(field);*/

		return field;
	} 
	/**
	 * Waits for the element to be visible for the required seconds
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 * @throws Throwable 
	 *//*
	public static  WebElement waitForElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) throws Throwable {

		WebElement field=null;	
		field=waitForElement_logic(ExpCondition.VISIBILITY,driver,objectlocatorType,objectlocator, waitSeconds );
		//waitForStaleOf(field);
		 commented on Jun 2 2014
	    log("Next verify stale..");
	    stal(field);

		return field;
	} */

	/**
	 * Waits for the element to be present for the required seconds
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 * @throws Throwable 
	 */	
	public static  WebElement waitForPresenceOfElement(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) throws Throwable {
		WebElement field=null;	
		field=waitForElement_logic(ExpCondition.PRESENCE,driver,objectlocatorType,objectlocator, waitSeconds );
		return field;
	} 

	/**
	 * Fetches a an action performable element for sucess
	 * @author khshaik
	 * @date Feb 12 2015
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 * @throws Throwable 
	 */
	public static  WebElement waitForElementTobeActionable(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) throws Throwable {

		WebElement field=null;	
		field=waitForElement_logic(ExpCondition.CLICKABLE,driver,objectlocatorType,objectlocator, waitSeconds );
		return field;
	} 
	/**
	 * Waits for all elements to be present
	 * @author Shaik
	 * @date Mar 03 2016
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */
	public static List< WebElement> waitForPresenceOfElements(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {
		List< WebElement> elements=null;	
		elements=waitForElements_logic(ExpCondition.PRESENCE,driver,objectlocatorType,objectlocator, waitSeconds );
		return elements;
	} 
	/**
	 * Waits for all elements to be visible
	 * @author Shaik
	 * @date Mar 03 2016
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */
	public static List< WebElement> waitForVisibilityOfElements(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {
		List< WebElement> elements=null;	
		elements=waitForElements_logic(ExpCondition.VISIBILITY,driver,objectlocatorType,objectlocator, waitSeconds );
		return elements;
	} 


	/**
	 * Waits for the dropdown items to be loaded
	 * @author khshaik
	 * @date Jan 10 2014
	 * @param driver
	 * @param element
	 * @param waitForSeconds
	 * @return
	 */
	public static Select waitForDropdownItems(WebDriver driver,WebElement element, int waitForSeconds){
		List<WebElement>dropdownList=null;
		Select dropdown =null;
		int i=1;
		for (i=1 ;i<=waitForSeconds;i++){
			dropdown = new Select(element);
			dropdownList=dropdown.getOptions();
			/*commented on Jan 20 2015 as it is giving problem when page ajax is not loaded in 20 secs
			PageLoadWaitUtil.waitForAjax(driver);*/

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



	/**
	 *  Waits for an element to be invisible on the page/frame(ExpectedCondition method)
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */

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

	/*public static List<WebElement> expectedVisibleElements(WebDriver driver,List<WebElement> elements,Integer waitForSeconds){
		List<WebElement>elements_visible=null;
		Long wait =Long.valueOf(Integer.valueOf(waitForSeconds));
		logsObj.log("Into the expectedVisibleElements");

		try{
			logsObj.log("expectedVisibleElements-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);

			elements_visible = new WebDriverWait(driver, (long)(wait)).until(ExpectedConditions.visibilityOfAllElements(elements));
			logsObj.log("expectedVisibleElements:-Able to wait for elements to be invisible.."+elements_visible.size());
		}catch(Throwable t){
			logsObj.logError("expectedVisibleElements:-Unable to wait for all elements to be visible  due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("expectedVisibleElements-Reverted back Implicit wait..");
		}

		return elements_visible;
	}*/


	/**
	 * Waits for the element to be in <expectedCondition> for the required seconds
	 * @author sahamed
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return element 
	 * @Date 16 Aug 2013
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static  WebElement waitForElement_logic_mar2016(ExpCondition expectedCondition,WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		WebElement field=null;
		By by; //The by type can be name ,id ,class name - supported by the Selenium by class
		by = null;
		try{

			logsObj.log("waitForElement_old-Turned off Implicit wait..");
			ImplicitWaitUtil.updateImplicitWait(driver,waitSeconds);

			Class byClass=Class.forName(By.class.getName());
			Method byMethod=byClass.getMethod(objectlocatorType, String.class);
			By newById=(By) byMethod.invoke(by, objectlocator);


			logsObj.log("waitForElement_logic-Able to bind the By element for "+objectlocator);
			field=ByLocator.getExpectedConditionsLocator(expectedCondition,newById, driver,waitSeconds);
			logsObj.log("waitForElement_logic:-Able to locate and element is visible.."+objectlocator);


		}catch(Throwable t){
			logsObj.logError("waitForElement_logic-Unable to bind the By element or wait for the element visibilty for "+objectlocator+" due to error->",t);			
		}				
		finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_logic-Reverted back Implicit wait..");
		}
		return field;
	} 
	public static  WebElement waitForElement_logic(ExpCondition expectedCondition,WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) throws Throwable {

		WebElement element=null;
		By Bylocator=null;		

		try{
			logsObj.log("waitForElement_logic-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);

			try{
				logsObj.log("waitForElement_logic:-Start.."+objectlocator);
				element=ByLocator.getExpectedConditionsLocator(expectedCondition,Bylocator, driver,waitSeconds);
				logsObj.log("waitForElement_logic:-Able to wait for element to be invisible.."+objectlocator);
			}catch(Throwable t){
				logsObj.logError("waitForElement_logic:-After binding By element , Unable to wait more for the invisiblity of  "+objectlocator+" due to error->",t);
				throw t;
			}

		}catch(Throwable th){
			logsObj.logError("waitForElement_logic:-Unable to bind the By element for "+objectlocator+" due to error->",th);
			throw th;
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_logic-Reverted back Implicit wait..");
		}

		return element;
	} 


	/**
	 * Waits for the elements to be in <expectedCondition> for the required seconds
	 * @author Khaleel
	 * @date Feb 2016
	 * @param expectedCondition
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */	
	private static  List<WebElement> waitForElements_logic(ExpCondition expectedCondition,WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds ) {

		List<WebElement> elements=null;
		By Bylocator=null;		

		try{
			logsObj.log("waitForElement_logic-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);

			try{
				logsObj.log("waitForElement_logic:-Start.."+objectlocator);
				elements=ByLocator.getExpectedConditionsLocators(expectedCondition,Bylocator, driver,waitSeconds);
				logsObj.log("waitForElement_logic:-Able to wait for element to be invisible.."+objectlocator);
			}catch(Throwable t){
				logsObj.logError("waitForElement_logic:-After binding By element , Unable to wait more for the invisiblity of  "+objectlocator+" due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitForElement_logic:-Unable to bind the By element for "+objectlocator+" due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_logic-Reverted back Implicit wait..");
		}

		return elements;
	} 


	/**
	 * Retrives the visible elements from the given locator
	 * @author Khaleel
	 * @date Jan 2016
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */
	public static int getVisibleElementsSize(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds){
		int flag=0;
		List<WebElement>list=waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
		if(list!=null){
			flag=list.size();
		}
		return flag;
	}

	/**
	 * Find a Radio button from the Radio Group (By Visibility of Elements)
	 * @author sahamed
	 * @Date Feb 18 2014
	 * @param identifyBy
	 * @param value
	 * @param radioButton
	 * @return input webelement for success and null for failure
	 */

	public static WebElement fetchRadiobuttonFromGroup(WebDriver driver,String objectlocatorType, String objectlocator,String radioButton,int waitSeconds)
	{

		WebElement element=null;

		try 
		{

			List<WebElement> listElement= waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds);

			int size=listElement.size();
			if(size==0){
				return element;
			}
			else{

				for (WebElement ele :listElement){
					String str=ele.getAttribute("value");
					if(str.equalsIgnoreCase(radioButton)){
						element=ele;
						break;
					}

				}
			}

		}


		catch (ElementNotVisibleException env) 
		{
			logsObj.logError("FindRadiobutton:Element "+objectlocator+" is not visible..due to -->",env);

		}

		catch (Exception genException)
		{
			logsObj.logError("FindRadiobutton: General Exception error .."+"while fecth element "+objectlocator+" due to -->  ",genException);				        	
		}


		return element;
	}	
	/**
	 * Find a Radio button from the Radio Group with attribute(By Presence of Elements)
	 * @author Khaleel
	 * @date Aug 16 2016
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param radioButton
	 * @param attributeName
	 * @param waitSeconds
	 * @return
	 */

	public static WebElement fetchRadiobuttonFromGroup(WebDriver driver,String objectlocatorType, String objectlocator,String radioButton,ExpCondition expectedCondition,String attributeName,int waitSeconds)
	{
		List<WebElement> listElement = null;
		WebElement element=null;
		String str;

		try 
		{
			switch (expectedCondition){
			case VISIBILITY:
				listElement= waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
				break;
			case PRESENCE:
				listElement= waitForPresenceOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
				break;
			case CLICKABLE:
				listElement= waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
				break;
			default:
				break;
			}
			int size=listElement.size();
			if(size==0){
				return element;
			}
			else{

				for (WebElement ele :listElement){
					if(attributeName.equalsIgnoreCase("TEXT")){
						str=ele.getText();
					}else{
						str=ele.getAttribute(attributeName);
					}					
					if(str.equalsIgnoreCase(radioButton)){
						element=ele;
						break;
					}

				}
			}

		}


		catch (ElementNotVisibleException env) 
		{
			logsObj.logError("FindRadiobutton:Element "+objectlocator+" is not visible..due to -->",env);

		}

		catch (Exception genException)
		{
			logsObj.logError("FindRadiobutton: General Exception error .."+"while fecth element "+objectlocator+" due to -->  ",genException);				        	
		}


		return element;
	}	
	/**
	 * Returns the list of visible webelements of the given locator
	 * @author khshaik
	 * @date Jan 14 2014
	 * @param driver
	 * @param identifyBy
	 * @param locator
	 * @return
	 */
	public static List<WebElement> visibleElementsList(WebDriver driver, String objectlocatorType,String objectlocator,int waitSeconds){
		int count=0;

		List<WebElement>searchElements=waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
		List<WebElement> visible_elements=new ArrayList<WebElement>();


		if(searchElements.size()!=0){

			for(WebElement element: searchElements){
				try{
					if(element.isDisplayed()==true){
						visible_elements.add(element);					
					}
				}catch(StaleElementReferenceException stl){
					logsObj.log("visibleElementsList:Element "+objectlocator+" is stale so recovering again and counter-"+count);	
					WaitUtil.pause(1);
					count=count+1;
					if(count<=5){
						visible_elements=visibleElementsList(driver, objectlocatorType, objectlocator,waitSeconds);
					}
				}

			}
		}

		return visible_elements;
	}
	/**
	 * Get the list of enabled elements by excluding the elements with disabled attribute
	 * @param driver
	 * @param objectlocatorType
	 * @param objectlocator
	 * @param waitSeconds
	 * @return
	 */
	public static List<WebElement> getEnabledElementsList(WebDriver driver, String objectlocatorType,String objectlocator,int waitSeconds){
		int count=0;

		List<WebElement>searchElements=waitForPresenceOfElements(driver, objectlocatorType, objectlocator, waitSeconds);
		List<WebElement> enable_elements=new ArrayList<WebElement>();


		if(searchElements.size()!=0){

			for(WebElement element: searchElements){
				try{
					String isDisabled = element.getAttribute("disabled");
					if (isDisabled==null || !isDisabled.equals("true")|| isDisabled.isEmpty()){
						enable_elements.add(element);	
						System.out.println("Element: Enabled");
					}else{
						System.out.println("View link: Disabled");
					}					
				}catch(StaleElementReferenceException stl){
					logsObj.log("visibleElementsList:Element "+objectlocator+" is stale so recovering again and counter-"+count);	
					WaitUtil.pause(1);
					count=count+1;
					if(count<=5){
						enable_elements=getEnabledElementsList(driver, objectlocatorType, objectlocator,waitSeconds);
					}
				}

			}
		}

		return enable_elements;
	}
	
	
	//************************************
	/**
	 * @author ShaikK
	 * @date Aug 3 2017
	 * @param driver
	 * @param element
	 * @param waitSeconds
	 * @return
	 */
	public static  boolean waitStalenessOfElement(WebDriver driver,WebElement element, Integer waitSeconds ) {

		boolean  flag=Constants_FRMWRK.FalseB;
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				flag = waitdriver.until(ExpectedConditions.stalenessOf(element));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		return flag;
	} 


	public static  boolean waitTitle(WebDriver driver,Integer waitSeconds,String title ) {

		boolean  flag=Constants_FRMWRK.FalseB;
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				flag = waitdriver.until(ExpectedConditions.titleIs(title));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		return flag;
	} 

	public static  boolean waitTitleContains(WebDriver driver,Integer waitSeconds,String title ) {

		boolean  flag=Constants_FRMWRK.FalseB;
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				flag = waitdriver.until(ExpectedConditions.titleContains(title));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		return flag;
	} 
	
	public static List<WebElement> waitForVisibilityOfElements_2(WebDriver driver,String objectlocatorType,String objectlocator, Integer waitSeconds){
		int counter =1;
		List<WebElement> rows_previous =null;
		List<WebElement> rows_current = null;
		logsObj.log("Inside waitUntilAllVisible");

		while (counter <=10){			
			rows_previous = waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds); 
			WaitUtil.pause(1);
			rows_current = waitForVisibilityOfElements(driver, objectlocatorType, objectlocator, waitSeconds/2); 
			if(rows_previous==null || rows_current== null){
				return rows_current;
			}
			if(rows_previous.size()==rows_current.size()){
				logsObj.log("Visibility of elements Previous-Current:"+rows_previous.size()+"-"+rows_current.size());
				if(rows_current.size()!=0 ){
					break;
				}
				
			}
			counter++;
		}

		if(counter>10){
			logsObj.log("All elements are not Visibility  of elements yet after 10 secs Previous-Current"+rows_previous.size()+"-"+rows_current.size());
		}

		return rows_current;

	}
	
	
	public static  Integer waitfornumberOfElementsToBeMoreThan(WebDriver driver,String objectlocatorType,String objectlocator,Integer numberOfElementsToBeMoreThan, Integer waitSeconds ) {
		Integer  flag=0;
		By Bylocator=null;
		List< WebElement> elements=null;
		
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);
			
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				elements = waitdriver.until(ExpectedConditions.numberOfElementsToBeMoreThan(Bylocator, numberOfElementsToBeMoreThan));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		if(elements!=null){
			flag=elements.size();
		}
		return flag;
	} 
	
	
	public static  Integer waitfornumberOfElementsToBeLessThan(WebDriver driver,String objectlocatorType,String objectlocator,Integer numberOfElementsToBeMoreThan, Integer waitSeconds ) {
		Integer  flag=0;
		By Bylocator=null;
		List< WebElement> elements=null;
		
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);
			
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				elements = waitdriver.until(ExpectedConditions.numberOfElementsToBeLessThan(Bylocator, numberOfElementsToBeMoreThan));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		if(elements!=null){
			flag=elements.size();
		}
		return flag;
	} 
	
	public static  Integer waitfornumberOfElementsToBe(WebDriver driver,String objectlocatorType,String objectlocator,Integer numberOfElementsToBeMoreThan, Integer waitSeconds ) {
		Integer  flag=0;
		By Bylocator=null;
		List< WebElement> elements=null;
		
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Bylocator=ByLocator.fetch_ByLocator(driver, objectlocatorType, objectlocator);
			
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				elements = waitdriver.until(ExpectedConditions.numberOfElementsToBe(Bylocator, numberOfElementsToBeMoreThan));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		if(elements!=null){
			flag=elements.size();
		}
		return flag;
	} 
	
	
	public static  boolean waitforNumberOfWindows(WebDriver driver,Integer waitSeconds,int expectedNumberOfWindows ) {

		boolean  flag=Constants_FRMWRK.FalseB;
		Long wait =Long.valueOf(Integer.valueOf(waitSeconds));		
		WebDriverWait waitdriver=new WebDriverWait(driver, wait);

		try{
			logsObj.log("waitStalenessOfElement-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			try{
				logsObj.log("waitStalenessOfElement:-Start..");
				flag = waitdriver.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
				logsObj.log("waitStalenessOfElement:-Able to wait for element to be Staleness..");
			}catch(Throwable t){
				logsObj.logError("waitStalenessOfElement:-After binding By element , Unable to wait more for the Staleness due to error->",t);
			}

		}catch(Throwable t){
			logsObj.logError("waitStalenessOfElement:-Unable to bind the By element for due to error->",t);
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitStalenessOfElement-Reverted back Implicit wait..");
		}

		return flag;
	} 
}
