package com.frw.util;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.base.Base;
import com.frw.wait.ImplicitWaitUtil;

public class PageLoadWaitUtil extends Base{

	/**
	 * Wait for all ajax queries executed
	 * @author sahamed
	 * @Date Jun 25 2014
	 * @param driver
	 */
	public static void waitForAjax(WebDriver driver ){
		// https://groups.google.com/forum/#!topic/selenium-users/-Lw8hR5UKbI
		try{
			logsObj.log("-------------");
			logsObj.log("AJAX WAIT Start..");
			WebDriverWait  wait = new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(20));	 
			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>(){
				public Boolean apply(WebDriver driver){
					//return (Boolean)(((JavascriptExecutor)driver).executeScript("return jQuery.active == 0"));
					return (Boolean)(executor.executeScript("return jQuery.active == 0"));
				}
			};
			wait.until(pageLoadCondition);
			logsObj.log("ALL AJAX calls are loaded..");
			logsObj.log("-------------");
		}
		catch(Throwable tm){
			logsObj.log("ALL AJAX calls are not loaded..");
		}
	}

	/**
	 * Waits untill the page is completely loaded 
	 * @author sahamed
	 * @Date Jun 23 2014
	 * @param driver
	 * @throws Throwable
	 */
	public static String waitForPageToLoad(WebDriver driver)throws Throwable{
		String flag="False";
		// http://www.sqaforums.com/printthread.php?Cat=0&Board=Selenium&main=686696&type=thread	
		logsObj.log("-------------");
		logsObj.log("Page Load Start..");
		// try{
		Object result = ((JavascriptExecutor) driver).executeScript(
				"function pageloadingtime()"+
						"{"+
						"return 'Page has completely loaded'"+
						"}"+
				"return (window.onload=pageloadingtime());");			 		
		logsObj.log("Page Load Status-"+result);			 		
		if(result.toString().contains("completely loaded")){						
			flag="True";						
		}else{
			flag="False";						
		}
		/* }catch(Throwable t){
			 logsObj.log("waitForPageToLoad :Error due to "+t+" and stacktrace is "+Arrays.toString(t.getStackTrace()));
		 }*/

		logsObj.log("-------------");		
		return flag;
	}

	/**
	 * Waits for page to be in complete state
	 * @author sahamed
	 * @Date Jul 26 2014
	 * @param driver
	 */
	//http://stackoverflow.com/questions/5574802/selenium-2-0b3-ie-webdriver-click-not-firing

	private synchronized void pageWaitLoad(WebDriver driver) {  
		String str = null;
		try {
			str = (String)((JavascriptExecutor)driver).executeScript("return document.readyState");
		}
		catch (Exception e) {
			// it's need when JS isn't worked
			pageWaitLoad(driver);
			return;
		}
		System.out.println("Page Load Status: " + str);
		while(!str.equals("complete")){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			str = (String)((JavascriptExecutor)driver).executeScript("return document.readyState");
		}
	}


	public static void waitForAjax_(WebDriver driver,int waitSeconds ){
		// https://groups.google.com/forum/#!topic/selenium-users/-Lw8hR5UKbI
		try{
			logsObj.log("waitForElement_old-Turned off Implicit wait..for Ajax load");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			logsObj.log("-------------");
			logsObj.log("AJAX WAIT Start..");

			WebDriverWait  wait = new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(waitSeconds));	 
			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>(){
				public Boolean apply(WebDriver driver){
					//return (Boolean)(((JavascriptExecutor)driver).executeScript("return jQuery.active == 0"));
					return (Boolean)(executor.executeScript("return jQuery.active == 0"));
				}
			};
			wait.until(pageLoadCondition);
			logsObj.log("ALL AJAX calls are loaded..");
			logsObj.log("-------------");
		}
		catch(Throwable tm){
			logsObj.log("ALL AJAX calls are not loaded..");
		}
		finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForElement_logic-Reverted back Implicit wait..");
		}
	}

	// http://stackoverflow.com/questions/10720325/selenium-webdriver-wait-for-complex-page-with-javascriptjs-to-load?rq=1
	/**
	 * @date May 04 2016
	 * @param driver
	 * @param waitSeconds
	 * @return
	 */
	public boolean waitForJStoLoad(WebDriver driver,int waitSeconds) {
		Long waitsec =Long.valueOf(Integer.valueOf(waitSeconds));
		WebDriverWait wait = new WebDriverWait(driver, waitsec);
		final JavascriptExecutor executor = (JavascriptExecutor) driver;

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long)executor.executeScript("return jQuery.active") == 0);
				}
				catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return executor.executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}



	/**
	 * @date May 4 2016
	 * @param counter
	 * @return
	 */
	public static ExpectedCondition<Boolean> documentNotActive(final int counter){ 		
		return new ExpectedCondition<Boolean>() {
			boolean resetCount=true;
			@Override
			public Boolean apply(WebDriver d) {

				if(resetCount){
					((JavascriptExecutor) d).executeScript(
							"   window.mssCount="+counter+";\r\n" + 
									"   window.mssJSDelay=function mssJSDelay(){\r\n" + 
									"       if((typeof jQuery != 'undefined') && (jQuery.active !== 0 || $(\":animated\").length !== 0))\r\n" + 
									"           window.mssCount="+counter+";\r\n" + 
									"       window.mssCount-->0 &&\r\n" + 
									"       setTimeout(window.mssJSDelay,window.mssCount+1);\r\n" + 
									"   }\r\n" + 
							"   window.mssJSDelay();");
					resetCount=false;
				}

				boolean ready=false;
				try{
					ready=-1==((Long) ((JavascriptExecutor) d).executeScript(
							"if(typeof window.mssJSDelay!=\"function\"){\r\n" + 
									"   window.mssCount="+counter+";\r\n" + 
									"   window.mssJSDelay=function mssJSDelay(){\r\n" + 
									"       if((typeof jQuery != 'undefined') && (jQuery.active !== 0 || $(\":animated\").length !== 0))\r\n" + 
									"           window.mssCount="+counter+";\r\n" + 
									"       window.mssCount-->0 &&\r\n" + 
									"       setTimeout(window.mssJSDelay,window.mssCount+1);\r\n" + 
									"   }\r\n" + 
									"   window.mssJSDelay();\r\n" + 
									"}\r\n" + 
							"return window.mssCount;"));
				}
				catch (NoSuchWindowException a){
					a.printStackTrace();
					return true;
				}
				catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return ready;
			}
			@Override
			public String toString() {
				return String.format("Timeout waiting for documentNotActive script");
			}
		};


	}

	/**
	 * Date May 4 2016
	 * @param driver
	 * @param waitSeconds
	 */
	public static void completePageLoad(WebDriver driver,int waitSeconds){
		Long waitsec =Long.valueOf(Integer.valueOf(waitSeconds));
		WebDriverWait wait = new WebDriverWait(driver, waitsec);
		wait.until(documentNotActive(50));
	}


	//http://stackoverflow.com/questions/20414729/webdriver-wait-for-jquery-to-finish?lq=1
	public static void waitForPageLoad(WebDriver driver,int waitForSeconds) {
		logsObj.log("-------------");
		logsObj.log("Page Load Start..");
		System.out.println("-------- Page Load Start --------------");
		(new WebDriverWait(driver, waitForSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				String readyState = js.executeScript("return document.readyState").toString();
				System.out.println("Ready State: " + readyState);
				return (Boolean) readyState.equals("complete");
			}
		});	    
		logsObj.log("-------------");		
		System.out.println("----------------------");
	}
	public static void waitForJQuery(WebDriver driver,int waitForSeconds) {
		logsObj.log("-------------");
		logsObj.log("AJAX WAIT Start..");
		System.out.println("-------- AJAX Load Start --------------");
		(new WebDriverWait(driver, waitForSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				boolean js_result= (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
				System.out.println("jquery result-"+js_result);
				return js_result;
			}
		});
		logsObj.log("-------------");
		System.out.println("----------------------");
	}
	
	
	public static void waitForPagetoLoadCompletely(WebDriver driver,Integer waitSeconds) {
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			logsObj.log("waitForPageLoaded-Turned off Implicit wait..");
			ImplicitWaitUtil.turnOffImplicitWait(driver);
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
			wait.until(expectation);
			System.out.println("Page Loaded Completely....");			
		} catch (Throwable error) {
			System.out.println("Timeout waiting for Page Load Request to complete.");
		}finally{
			ImplicitWaitUtil.turnOnImplicitWait(driver);
			logsObj.log("waitForPageLoaded-Reverted back Implicit wait..");
		}
	}
	
	
	private static String getCurrentPageLoadStatus(WebDriver driver){
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString();
	}

}


