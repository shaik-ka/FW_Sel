package com.frw.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class RoboUtil {
	
	public static void captureScreenshot(String format,String fileName){
		
		try {
			Robot robo=new Robot();
			
			Rectangle screen=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage captureImage =robo.createScreenCapture(screen);
			ImageIO.write(captureImage, format, new File(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
