package com.frw.util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frw.base.Base;

public class PopUpUtil extends Base {
	

	/**
	 * check for the alert pop-up and clicks on the required button 	
	 * @author sahamed
	 * @Date  09 Jul 2013
	 * @param buttonToClick
	 * @return returns message of the alert pop-up for success and for failure "No Popup exists due to error-->"+e;
	 */
	public static String checkDefaultPopup(WebDriver driver,String buttonToClick){
		String flag=null;
		
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 try{
		     wait.until(ExpectedConditions.alertIsPresent());
		     Alert alert = driver.switchTo().alert();
		     flag=alert.getText();			     
		     WaitUtil.pause(1000L);
		     if (buttonToClick.equalsIgnoreCase("OK")){
		    	 alert.accept();
		    	 logsObj.log("Clicked Ok in the Default popup");
		     }else{
		    	 alert.dismiss();
		    	 logsObj.log("Clicked Cancel in the Default popup");
		     }
		     
		 }
		 catch (Exception e){
		     //System.out.println("No alert"+e);
		     flag="No Popup exists due to error-->"+e;
		 }
		 
		 return flag;
	}
	
	 
	 
	/**
	 * check for the alert pop-up and clicks on the required button 
	 * @author sahamed
	 * @date  13 Aug 2013	 
	 * @param Step
	 * @param buttonToClick
	 * @return returns message of the alert pop-up for success and for failure "No Popup exists due to error-->"+e;
	 */
	public static String checkDefaultPopup(WebDriver driver,String Step,String buttonToClick){
				String flag=null;
				
				 WebDriverWait wait = new WebDriverWait(driver, 5);
				 try{
				     wait.until(ExpectedConditions.alertIsPresent());
				     Alert alert =driver.switchTo().alert();
				     flag=alert.getText();
				     WaitUtil.pause(1000L);
				     if (buttonToClick.equalsIgnoreCase("OK")){
				    	 alert.accept();
				    	// ReportExcel.Reporter(testcaseName, Step, "popup exists with a message "+flag+" & clicked "+buttonToClick+" button", FrameworkConstants.Pass);
				     }else{
				    	 alert.dismiss();
				    	// ReportExcel.Reporter(testcaseName, Step, "popup exists with a message "+flag+" & clicked "+buttonToClick+" button", FrameworkConstants.Pass);
				     }
				     	
				     	if(flag.contains("\n")){
				     		flag=flag.replaceAll("\n", "");
				     	}
				 }
				 catch (Throwable e){
				    // System.out.println("No alert"+e);
					 logsObj.log("No Popup exists due to error-->"+e);
				     flag="No Popup exists due to error-->"+e;					     
				 }
				 
				 return flag;
		}
	

}
