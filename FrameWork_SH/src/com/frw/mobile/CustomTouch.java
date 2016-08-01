package com.frw.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

public class CustomTouch {
	
	public static void tapElement(WebDriver driver,WebElement element){
    	
		TouchActions tap=new TouchActions(driver);									
		tap.singleTap(element).perform();
			
	}

}
