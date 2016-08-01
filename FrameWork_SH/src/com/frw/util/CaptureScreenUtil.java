package com.frw.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


public class CaptureScreenUtil {
	private static CaptureScreenUtil capturescreenutilObj=null;
	
	private CaptureScreenUtil(){}
	
	public static CaptureScreenUtil getCaptureScreenUtil(){
		
		if(capturescreenutilObj==null){
			capturescreenutilObj= new CaptureScreenUtil();
		}		
		return capturescreenutilObj;
		
	}
	
	
	
	
	public void TakeScreenshotLink(WebDriver driver,String failureImageFileName,int errorStep) { 
		   try { 
			   		/*if(browserName.equalsIgnoreCase(Constants.browserie)){
			   			driver.switchTo().window("");
			   		}*/
			  /* 	String handle=driver.getWindowHandle();	
			   		WebDriver compatibleDriver = new Augmenter().augment(driver);
			   */
                RemoteWebDriver compatibleDriver = (RemoteWebDriver) driver;
			   
                 File screenshot = ((TakesScreenshot) compatibleDriver).getScreenshotAs(OutputType.FILE);  
                 FileUtils.copyFile(screenshot, new File(failureImageFileName)); 
                 WaitUtil.pause(200L);
               /*  
                 Reporter.log("<a href=file:///"+failureImageFileName+">"+errorStep+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
                     format(new GregorianCalendar().getTime())+"</a>");
             	Reporter.setCurrentTestResult(null); */
            // 	driver.switchTo().window(handle);
         } 
         catch (Exception e){ 
             e.printStackTrace(); 
         } 
     }
		
	/**
	 * Captures the WebBrowser screenshot when a remoteWebdriver instance is invoked.
	 * @author sahamed
	 * @Date May 8 2014
	 * @param failureImageFileName
	 * @param errorStep
	 */
	public void TakeScreenshotLinkforRemoteDriver(WebDriver driver,String failureImageFileName,int errorStep) { 
		try { 
			
			 WebDriver augmentedDriver = new Augmenter().augment(driver);
			
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);  
			FileUtils.copyFile(screenshot, new File(failureImageFileName)); 

			Reporter.log("<a href=file:///"+failureImageFileName+">"+errorStep+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
					format(new GregorianCalendar().getTime())+"</a>");
			Reporter.setCurrentTestResult(null); 
		} 
		catch (Exception e){ 
			e.printStackTrace(); 
		} 
	}

	public void TakeScreenshotLink_Ashot(WebDriver driver,String format,String failureImageFileName) { 
		try { 
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			Screenshot scr=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(augmentedDriver);
			ImageIO.write(scr.getImage(), format, new File(failureImageFileName));
			WaitUtil.pause(200L);			
		} 
		catch (Exception e){ 
			e.printStackTrace(); 
		} 
	}
	public void TakeScreenshotLink_robo(String format,String failureImageFileName) { 
		RoboUtil.captureScreenshot(format, failureImageFileName);			
	}
	
}
