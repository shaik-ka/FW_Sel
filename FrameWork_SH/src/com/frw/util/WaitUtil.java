package com.frw.util;

import java.util.concurrent.TimeUnit;

public class WaitUtil {

	/**
	 * pause for the execution for certain time given(long)
	 * @author sahamed
	 * @param timeperiod in long
	 */

	public static void pause(long timePeriod){
		try {
			Thread.sleep(timePeriod);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	/**
	 * pause for the execution for certain time given(int)
	 * @author sahamed
	 * @Date Nov 10 2013
	 * @param timePeriodinSeconds
	 */
	public static void pause(int timePeriodinSeconds){
		try {
			TimeUnit.SECONDS.sleep(timePeriodinSeconds);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	

}
